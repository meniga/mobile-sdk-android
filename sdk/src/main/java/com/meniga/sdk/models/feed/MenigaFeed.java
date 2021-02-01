package com.meniga.sdk.models.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.PaginationUtils;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.feed.operators.MenigaFeedOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaFeed extends ArrayList<MenigaFeedItem> implements Parcelable, Serializable, Cloneable {
	public static final Parcelable.Creator<MenigaFeed> CREATOR = new Parcelable.Creator<MenigaFeed>() {
		public MenigaFeed createFromParcel(Parcel source) {
			return new MenigaFeed(source);
		}

		public MenigaFeed[] newArray(int size) {
			return new MenigaFeed[size];
		}
	};

	protected static MenigaFeedOperations apiOperator;

	protected boolean hasMoreData = true;
	protected Integer totalCount;
	protected DateTime actualEndDate;
	protected DateTime from;
	protected DateTime to;
	protected int page;
	protected int itemsPerPage;

	public MenigaFeed() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected MenigaFeed(Parcel in) {
		super();
		hasMoreData = in.readByte() != 0;
		totalCount = (Integer) in.readValue(Integer.class.getClassLoader());
		long actual = in.readLong();
		actualEndDate = actual == 0 ? null : new DateTime(actual);
		long fromLong = in.readLong();
		from = fromLong == 0 ? null : new DateTime(fromLong);
		long toLong = in.readLong();
		to = toLong == 0 ? null : new DateTime(toLong);
		page = in.readInt();
		itemsPerPage = in.readInt();
		addAll(in.readArrayList(getClass().getClassLoader()));
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(hasMoreData ? (byte) 1 : (byte) 0);
		dest.writeValue(totalCount);
		dest.writeLong(actualEndDate != null ? actualEndDate.getMillis() : 0L);
		dest.writeLong(from != null ? from.getMillis() : 0L);
		dest.writeLong(to != null ? to.getMillis() : 0L);
		dest.writeInt(page);
		dest.writeInt(itemsPerPage);
		dest.writeList(this);
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaFeedOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaFeedOperations operator) {
		MenigaFeed.apiOperator = operator;
	}

	private void setActualEndDate(DateTime actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	/**
	 * @return The start of the period for the feed
	 */
	public DateTime getFrom() {
		return from;
	}

	/**
	 * @return End of the period for the feed
	 */
	public DateTime getTo() {
		return to;
	}

	/**
	 * @return The current page the feed is on within the given time period - if pagination was enabled
	 * using fetch with page and itemsPerPage
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return Number of items per page - if pagination was enabled using fetch with page and itemsPerPage
	 */
	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public DateTime getActualEndDate() {
		return actualEndDate;
	}

	@Override
	public MenigaFeed clone() {
		return (MenigaFeed) super.clone();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * @return Whether or not there are more items for the user. Not the same as atLastPage, hasMoreData
	 * is for all time whereas atLastPage only applies to pagination within a time period
	 */
	public boolean hasMoreData() {
		return hasMoreData;
	}

	/**
	 * @return Whether or not the feed is at the last page within the time period requested.
	 */
	public boolean hasMorePages() {
		return totalCount != null && PaginationUtils.hasMoreData(totalCount, page, itemsPerPage);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MenigaFeed that = (MenigaFeed) o;

        if (page != that.page) return false;
        if (itemsPerPage != that.itemsPerPage) return false;
        if (totalCount != null ? !totalCount.equals(that.totalCount) : that.totalCount != null)
            return false;
        if (actualEndDate != null ? !actualEndDate.equals(that.actualEndDate) : that.actualEndDate != null)
            return false;
        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        return to != null ? to.equals(that.to) : that.to == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (totalCount != null ? totalCount.hashCode() : 0);
        result = 31 * result + (actualEndDate != null ? actualEndDate.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + page;
        result = 31 * result + itemsPerPage;
        return result;
    }

    @Override
    @Nonnull
	public MenigaFeed subList(int fromIndex, int toIndex) {
		List<MenigaFeedItem> items = super.subList(fromIndex, toIndex);
		MenigaFeed clone = clone();
		clone.clear();
		clone.addAll(items);
		return clone;
	}

	/*
     * API calls
     */

	/**
	 * Fetches the feed within the given period, ignoring pagination (all entries are returned within the range)
	 *
	 * @param from The start date for the feed, no item in the feed will be older than this
	 * @param to   The end date for the feed, no item in the feed will be younger than this
	 * @return A feed object containing transactions, user events etc.
	 */
	public static Result<MenigaFeed> fetch(final DateTime from, final DateTime to) {
		FeedFilter feedFilter = new FeedFilter.Builder().from(from).to(to).build();
		return fetch(feedFilter);
	}

	/**
	 * Fetches the feed within the given period, ignoring pagination (all entries are returned within the range)
	 *
	 * @param from         The start date for the feed, no item in the feed will be older than this
	 * @param to           The end date for the feed, no item in the feed will be younger than this
	 * @param page         The page index to get
	 * @param itemsPerPage Number of results per page
	 * @return A feed object containing transactions, user events etc.
	 */
	public static Result<MenigaFeed> fetch(final DateTime from, final DateTime to, final int page, final int itemsPerPage) {
		FeedFilter feedFilter = new FeedFilter.Builder()
				.from(from)
				.to(to)
				.page(page)
				.itemsPerPage(itemsPerPage)
				.build();
		return fetch(feedFilter);
	}

	/**
	 * Fetches the feed according to the provided {@link FeedFilter}
	 *
	 * @param feedFilter The filter to use
	 * @return A feed object containing transactions, user events etc.
	 */
	public static Result<MenigaFeed> fetch(@Nonnull final FeedFilter feedFilter) {
		Result<MenigaFeed> task = MenigaFeed.apiOperator.getFeed(feedFilter);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaFeed>() {
			@Override
			public void onFinished(MenigaFeed result, boolean failed) {
				if (!failed && result != null) {
					result.from = feedFilter.getFrom();
					result.to = feedFilter.getTo();
					if (feedFilter.getPage() != null) {
						result.page = feedFilter.getPage();
					}
					if (feedFilter.getItemsPerPage() != null) {
						result.itemsPerPage = feedFilter.getItemsPerPage();
					}
				}
			}
		});
	}

	/**
	 * Fetches and merges items from the feed into this feed object by going numDays into the past
	 *
	 * @param numDays Number of days into the past, beyond the current from date, to fetch new feed data
	 * @return A feed object containing transactions, user events etc.
	 */
	public Result<MenigaFeed> appendDays(final int numDays) {
		FeedFilter.Builder builder = new FeedFilter.Builder()
				.from(from.minusDays(numDays))
				.to(from.minusMillis(1));
		if (itemsPerPage > 0) {
			page = 0;
			builder.page(page).itemsPerPage(itemsPerPage);
		}
		Result<MenigaFeed> task = MenigaFeed.apiOperator.getFeed(builder.build());
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaFeed>() {
			@Override
			public void onFinished(MenigaFeed result, boolean failed) {
				if (!failed && result != null) {
					addAll(result);
					totalCount = result.totalCount;
					actualEndDate = result.actualEndDate;

					MenigaFeed.this.from = MenigaFeed.this.from.minusDays(numDays);
				}
			}
		});
	}

	/**
	 * Appends all the transactions from the next page. Use hasMorePages to find out if you are on the last page
	 *
	 * @return A feed object containing a next page
	 */
	public Result<MenigaFeed> appendNextPage() {
		FeedFilter feedFilter = new FeedFilter.Builder()
				.from(from)
				.to(to)
				.page(page + 1)
				.itemsPerPage(itemsPerPage)
				.build();
		Result<MenigaFeed> task = MenigaFeed.apiOperator.getFeed(feedFilter);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaFeed>() {
			@Override
			public void onFinished(MenigaFeed result, boolean failed) {
				if (!failed && result != null) {
					addAll(result);
					totalCount = result.totalCount;
					actualEndDate = result.actualEndDate;
					page++;
				}
			}
		});
	}

	/**
	 * Replaces all the items with the items from the next page. Use hasMorePages to find out if you are on the last page
	 *
	 * @return A feed object containing a next page
	 */
	public Result<MenigaFeed> nextPage() {
		FeedFilter feedFilter = new FeedFilter.Builder()
				.from(from)
				.to(to)
				.page(page + 1)
				.itemsPerPage(itemsPerPage)
				.build();
		Result<MenigaFeed> task = MenigaFeed.apiOperator.getFeed(feedFilter);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaFeed>() {
			@Override
			public void onFinished(MenigaFeed result, boolean failed) {
				if (!failed && result != null) {
					clear();
					addAll(result);
					totalCount = result.totalCount;
					actualEndDate = result.actualEndDate;
					page++;
				}
			}
		});
	}

	/**
	 * Replaces all the items with the items from the previous page. Use getPage to find out if you are on the first page
	 *
	 * @return A feed object containing a previous page
	 */
	public Result<MenigaFeed> prevPage() {
		FeedFilter feedFilter = new FeedFilter.Builder()
				.from(from)
				.to(to)
				.page(page - 1)
				.itemsPerPage(itemsPerPage)
				.build();
		Result<MenigaFeed> task = MenigaFeed.apiOperator.getFeed(feedFilter);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaFeed>() {
			@Override
			public void onFinished(MenigaFeed result, boolean failed) {
				if (!failed && result != null) {
					clear();
					addAll(result);
					totalCount = result.totalCount;
					setActualEndDate(result.actualEndDate);
					page--;
				}
			}
		});
	}

	public static Result<MenigaScheduledEvent> fetchScheduledEvent(long id) {
		return MenigaFeed.apiOperator.getScheduledEvent(id);
	}
}
