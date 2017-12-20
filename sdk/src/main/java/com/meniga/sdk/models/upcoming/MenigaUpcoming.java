package com.meniga.sdk.models.upcoming;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.StateObject;
import com.meniga.sdk.models.upcoming.enums.PaymentStatus;
import com.meniga.sdk.models.upcoming.operators.MenigaUpcomingOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUpcoming extends StateObject implements Serializable, Parcelable, Cloneable {
	protected long id;
	protected String bankReference;
	protected String text;
	protected MenigaDecimal amount;
	protected MenigaDecimal amountInCurrency;
	protected String currencyCode;
	protected DateTime date;
	protected PaymentStatus paymentStatus;
	protected Boolean isFlagged;
	protected Boolean isWatched;
	protected Long accountId;
	protected Long transactionId;
	protected Long invoiceId;
	protected Long scheduledPaymentId;
	protected Long categoryId;
	protected MenigaUpcomingRecurringPattern recurringPattern;
	protected List<MenigaUpcomingComment> comments;
	protected List<MenigaUpcomingReconcileScore> reconcileScores;
	protected MenigaUpcomingDetails details;

	private static MenigaUpcomingOperations apiOperator;

	protected MenigaUpcoming() {
	}

	protected MenigaUpcoming(Parcel in) {
		this.id = in.readLong();
		this.bankReference = in.readString();
		this.text = in.readString();
		this.amount = (MenigaDecimal) in.readSerializable();
		this.amountInCurrency = (MenigaDecimal) in.readSerializable();
		this.currencyCode = in.readString();
		this.date = (DateTime) in.readSerializable();
		int tmpPaymentStatus = in.readInt();
		this.paymentStatus = tmpPaymentStatus == -1 ? null : PaymentStatus.values()[tmpPaymentStatus];
		this.isFlagged = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.isWatched = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.accountId = (Long) in.readValue(Long.class.getClassLoader());
		this.transactionId = (Long) in.readValue(Long.class.getClassLoader());
		this.invoiceId = (Long) in.readValue(Long.class.getClassLoader());
		this.scheduledPaymentId = (Long) in.readValue(Long.class.getClassLoader());
		this.categoryId = (Long) in.readValue(Long.class.getClassLoader());
		this.recurringPattern = in.readParcelable(MenigaUpcomingRecurringPattern.class.getClassLoader());
		this.comments = in.createTypedArrayList(MenigaUpcomingComment.CREATOR);
		this.reconcileScores = in.createTypedArrayList(MenigaUpcomingReconcileScore.CREATOR);
		this.details = in.readParcelable(MenigaUpcomingDetails.class.getClassLoader());
	}

	@Override
	public String toString() {
		return text + " (" + id + "): " + amount.doubleValue() + " (" + paymentStatus + ")";
	}

	/**
	 * @return The id of the upcoming transaction
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return An identifier that connects invoices to scheduled payments to transactions in the external system
	 */
	public String getBankReference() {
		return bankReference;
	}

	/**
	 * @return A human readable text that is displayed to the end user as the title or subject of the upcoming transaction
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return The amount of the upcoming transaction in system/user currency
	 */
	public MenigaDecimal getAmount() {
		return amount;
	}

	/**
	 * @return The amount for the upcoming transaction in the currency of the "CurrencyCode"
	 */
	public MenigaDecimal getAmountInCurrency() {
		return amountInCurrency;
	}

	/**
	 * @return The ISO 4217 currency code of the "AmountInCurrency"
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return The expected booking/payment date of the upcoming transaction
	 */
	public DateTime getDate() {
		return date;
	}

	/**
	 * @return The payment status of the upcoming PaymentStatusEnumModel = ['Open', 'Paid', 'OnHold']
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @return True if the upcoming transaction is flagged by the user
	 */
	public Boolean getFlagged() {
		return isFlagged;
	}

	/**
	 * @return True if the upcoming transaction added to the watched list
	 */
	public Boolean getWatched() {
		return isWatched;
	}

	/**
	 * @return The id of the account that the upcoming transaction is expected to be booked from
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @return The id of the actual transaction once booked
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @return The id of the invoice this upcoming transaction is based on
	 */
	public Long getInvoiceId() {
		return invoiceId;
	}

	/**
	 * @return The id of the scheduled payment this upcoming transaction is based on
	 */
	public Long getScheduledPaymentId() {
		return scheduledPaymentId;
	}

	/**
	 * @return The id of the category this upcoming transaction has been categorized as
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @return The recurring pattern this upcoming transaction was created from
	 */
	public MenigaUpcomingRecurringPattern getRecurringPattern() {
		return recurringPattern;
	}

	/**
	 * @return A list of comments related to the upcoming transaction
	 */
	public List<MenigaUpcomingComment> getComments() {
		return comments;
	}

	/**
	 * @return A list of possible reconcile matches
	 */
	public List<MenigaUpcomingReconcileScore> getReconcileScores() {
		return reconcileScores;
	}

	/**
	 * @return Details of the upcoming transaction. Only set when explicitly requested for
	 */
	public MenigaUpcomingDetails getDetails() {
		return details;
	}

	public void setText(String text) {
		changed();
		this.text = text;
	}

	public void setAmountInCurrency(MenigaDecimal amountInCurrency) {
		changed();
		this.amountInCurrency = amountInCurrency;
	}

	public void setCurrencyCode(String currencyCode) {
		changed();
		this.currencyCode = currencyCode;
	}

	public void setDate(DateTime date) {
		changed();
		this.date = date;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		changed();
		this.paymentStatus = paymentStatus;
	}

	public void setAccountId(long accountId) {
		changed();
		this.accountId = accountId;
	}

	public void setCategoryId(long categoryId) {
		changed();
		this.categoryId = categoryId;
	}

	public void setTransactionId(long transactionId) {
		changed();
		this.transactionId = transactionId;
	}

	public void setFlagged(boolean isFlagged) {
		changed();
		this.isFlagged = isFlagged;
	}

	public void setIsWatched(boolean isWatched) {
		changed();
		this.isWatched = isWatched;
	}

	public void setRecurringPattern(MenigaUpcomingRecurringPattern recurringPattern) {
		changed();
		this.recurringPattern = recurringPattern;
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaUpcomingOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaUpcomingOperations operator) {
		MenigaUpcoming.apiOperator = operator;
	}

	@Override
	protected void revertToRevision(StateObject lastRevision) {
		if (!(lastRevision instanceof MenigaUpcoming)) {
			return;
		}

		MenigaUpcoming last = (MenigaUpcoming) lastRevision;
		text = last.text;
		amountInCurrency = last.amountInCurrency;
		currencyCode = last.currencyCode;
		date = last.date;
		paymentStatus = last.paymentStatus;
		accountId = last.accountId;
		categoryId = last.categoryId;
		transactionId = last.transactionId;
		isFlagged = last.isFlagged;
		isWatched = last.isWatched;
		recurringPattern = last.recurringPattern;
	}

	@Override
	public MenigaUpcoming clone() throws CloneNotSupportedException {
		return (MenigaUpcoming) super.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaUpcoming that = (MenigaUpcoming) o;

		if (id != that.id) {
			return false;
		}
		if (bankReference != null ? !bankReference.equals(that.bankReference) : that.bankReference != null) {
			return false;
		}
		if (text != null ? !text.equals(that.text) : that.text != null) {
			return false;
		}
		if (amount != null ? !amount.equals(that.amount) : that.amount != null) {
			return false;
		}
		if (amountInCurrency != null ? !amountInCurrency.equals(that.amountInCurrency) : that.amountInCurrency != null) {
			return false;
		}
		if (currencyCode != null ? !currencyCode.equals(that.currencyCode) : that.currencyCode != null) {
			return false;
		}
		if (date != null ? !date.equals(that.date) : that.date != null) {
			return false;
		}
		if (paymentStatus != that.paymentStatus) {
			return false;
		}
		if (isFlagged != null ? !isFlagged.equals(that.isFlagged) : that.isFlagged != null) {
			return false;
		}
		if (isWatched != null ? !isWatched.equals(that.isWatched) : that.isWatched != null) {
			return false;
		}
		if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) {
			return false;
		}
		if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null) {
			return false;
		}
		if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) {
			return false;
		}
		if (scheduledPaymentId != null ? !scheduledPaymentId.equals(that.scheduledPaymentId) : that.scheduledPaymentId != null) {
			return false;
		}
		if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) {
			return false;
		}
		if (recurringPattern != null ? !recurringPattern.equals(that.recurringPattern) : that.recurringPattern != null) {
			return false;
		}
		if (comments != null ? !comments.equals(that.comments) : that.comments != null) {
			return false;
		}
		if (reconcileScores != null ? !reconcileScores.equals(that.reconcileScores) : that.reconcileScores != null) {
			return false;
		}
		return details != null ? details.equals(that.details) : that.details == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (bankReference != null ? bankReference.hashCode() : 0);
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (amountInCurrency != null ? amountInCurrency.hashCode() : 0);
		result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (paymentStatus != null ? paymentStatus.hashCode() : 0);
		result = 31 * result + (isFlagged != null ? isFlagged.hashCode() : 0);
		result = 31 * result + (isWatched != null ? isWatched.hashCode() : 0);
		result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
		result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
		result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
		result = 31 * result + (scheduledPaymentId != null ? scheduledPaymentId.hashCode() : 0);
		result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
		result = 31 * result + (recurringPattern != null ? recurringPattern.hashCode() : 0);
		result = 31 * result + (comments != null ? comments.hashCode() : 0);
		result = 31 * result + (reconcileScores != null ? reconcileScores.hashCode() : 0);
		result = 31 * result + (details != null ? details.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.bankReference);
		dest.writeString(this.text);
		dest.writeSerializable(this.amount);
		dest.writeSerializable(this.amountInCurrency);
		dest.writeString(this.currencyCode);
		dest.writeSerializable(this.date);
		dest.writeInt(this.paymentStatus == null ? -1 : this.paymentStatus.ordinal());
		dest.writeValue(this.isFlagged);
		dest.writeValue(this.isWatched);
		dest.writeValue(this.accountId);
		dest.writeValue(this.transactionId);
		dest.writeValue(this.invoiceId);
		dest.writeValue(this.scheduledPaymentId);
		dest.writeValue(this.categoryId);
		dest.writeParcelable(this.recurringPattern, flags);
		dest.writeTypedList(this.comments);
		dest.writeTypedList(this.reconcileScores);
		dest.writeParcelable(this.details, flags);
	}

	public static final Creator<MenigaUpcoming> CREATOR = new Creator<MenigaUpcoming>() {
		@Override
		public MenigaUpcoming createFromParcel(Parcel source) {
			return new MenigaUpcoming(source);
		}

		@Override
		public MenigaUpcoming[] newArray(int size) {
			return new MenigaUpcoming[size];
		}
	};

	/*
	--- API calls below ---
	 */

	/**
	 * Fetches all upcoming items between the date range
	 *
	 * @param from Get all upcoming items after this date
	 * @param to   Get all upcoming items before this date
	 * @return Meniga task containing the list of all upcoming items that meet the time range constraints
	 */
	public static Result<List<MenigaUpcoming>> fetch(DateTime from, DateTime to) {
		return MenigaUpcoming.apiOperator.getUpcoming(from, to);
	}

	/**
	 * Fetches a specific upcoming item
	 *
	 * @param id The id of the upcoming to fetch
	 * @return Meniga task containing the upcoming item
	 */
	public static Result<MenigaUpcoming> fetch(long id) {
		return MenigaUpcoming.apiOperator.getUpcoming(id);
	}

	/**
	 * Creates a new instance of an upcoming series
	 *
	 * @param text             A human readable text that is displayed to the end user as the title or subject of the upcoming transaction
	 * @param amountInCurrency An amount in the currency specified.
	 * @param currencyCode     The amount for the upcoming transaction in the currency of the "CurrencyCode"
	 * @param date             The expected booking/payment date of the upcoming transaction
	 * @param accountId        The id of the account that the upcoming transaction is expected to be booked from
	 * @param categoryId       The id of the category this upcoming transaction has been categorized as
	 * @param isFlagged        True if the upcoming transaction is flagged by the user
	 * @param isWatched        True if the upcoming transaction added to the watched list
	 * @param recurringPattern The recurring pattern that will be used to generate all the upcoming items
	 * @return The upcoming series, all upcoming that were created
	 */
	public static Result<List<MenigaUpcoming>> create(String text, MenigaDecimal amountInCurrency,
	                                                  String currencyCode, DateTime date, Long accountId,
	                                                  Long categoryId, Boolean isFlagged, Boolean isWatched,
	                                                  MenigaUpcomingRecurringPattern recurringPattern) {
		return MenigaUpcoming.apiOperator.createUpcoming(
				text,
				amountInCurrency,
				currencyCode,
				date,
				accountId,
				categoryId,
				isFlagged,
				isWatched,
				recurringPattern
		);
	}

	/**
	 * Saves change made to this upcoming item
	 *
	 * @param updateWholeSeries Updates the whole series, this means that a new series of upcoming
	 *                          items will be created, all having paymentStatus=Open
	 * @return A task of type Void. The task will indicate if the update was successful or not
	 */
	public Result<Void> update(boolean updateWholeSeries) {
		Result<Void> task = MenigaUpcoming.apiOperator.updateUpcoming(this, updateWholeSeries);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<Void>() {
			@Override
			public void onFinished(Void result, boolean failed) {
				if (failed) {
					return;
				}
				Result<MenigaUpcoming> task = fetch(id);
				MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaUpcoming>() {
					@Override
					public void onFinished(MenigaUpcoming result, boolean failed) {
						if (!failed && result != null) {
							amount = result.amount;
							amountInCurrency = result.amountInCurrency;
						}
					}
				});
			}
		});
	}

	/**
	 * Deletes this upcoming itom from the server
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> delete(boolean deleteSeries) {
		return MenigaUpcoming.apiOperator.deleteUpcoming(this, deleteSeries);
	}
}
