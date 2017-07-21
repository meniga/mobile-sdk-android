package com.meniga.sdk.models.user;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.operators.MenigaSyncOperations;

/**
 * Represents the status of a sync procedure.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaSync implements Serializable, Parcelable, Cloneable {
	public static final Creator<MenigaSync> CREATOR = new Creator<MenigaSync>() {
		@Override
		public MenigaSync createFromParcel(Parcel source) {
			return new MenigaSync(source);
		}

		@Override
		public MenigaSync[] newArray(int size) {
			return new MenigaSync[size];
		}
	};

	protected static MenigaSyncOperations apiOperator;

	protected long syncHistoryId;
	protected boolean isSyncDone;
	protected DateTime syncSessionStartTime;
	protected List<RealmSyncResponse> realmSyncResponses;

	protected MenigaSync() {
	}

	protected MenigaSync(Parcel in) {
		this.syncHistoryId = in.readLong();
		this.isSyncDone = in.readByte() != 0;
		this.syncSessionStartTime = (DateTime) in.readSerializable();
		this.realmSyncResponses = in.createTypedArrayList(RealmSyncResponse.CREATOR);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	protected MenigaSync clone() throws CloneNotSupportedException {
		return (MenigaSync) super.clone();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.syncHistoryId);
		dest.writeByte(isSyncDone ? (byte) 1 : (byte) 0);
		dest.writeSerializable(this.syncSessionStartTime);
		dest.writeTypedList(realmSyncResponses);
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaSyncOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaSyncOperations operator) {
		MenigaSync.apiOperator = operator;
	}

	/**
	 * @return Gets the sync history id.
	 */
	public long getSyncHistoryId() {
		return syncHistoryId;
	}

	/**
	 * @return Gets or sets the sync session start time.
	 */
	public DateTime getSyncSessionStartTime() {
		return syncSessionStartTime;
	}

	/**
	 * @return Gets the realm sync responses.
	 */
	public List<RealmSyncResponse> getRealmSyncResponses() {
		return realmSyncResponses;
	}

	/**
	 * @return Gets a value indicating whether this sync session is done or is still running in the background
	 */
	public boolean getIsSyncDone() {
		return this.isSyncDone;
	}

	private boolean hasNewData() {
		if (this.realmSyncResponses == null || this.realmSyncResponses.size() == 0) {
			return false;
		}
		for (RealmSyncResponse resp : this.realmSyncResponses) {
			for (AccountSyncStatus stat : resp.getAccountSyncStatuses()) {
				if (stat.getTransactionsProcessed() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return Number of new transactions that came into the system as a result of the sync.
	 */
	public int getNumNewTransactions() {
		if (this.realmSyncResponses == null || this.realmSyncResponses.size() == 0) {
			return 0;
		}
		int cnt = 0;
		for (RealmSyncResponse resp : this.realmSyncResponses) {
			for (AccountSyncStatus stat : resp.getAccountSyncStatuses()) {
				cnt += stat.getTransactionsProcessed();
			}
		}
		return cnt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaSync that = (MenigaSync) o;

		if (syncHistoryId != that.syncHistoryId) {
			return false;
		}
		if (isSyncDone != that.isSyncDone) {
			return false;
		}
		if (syncSessionStartTime != null ? !syncSessionStartTime.equals(that.syncSessionStartTime) : that.syncSessionStartTime != null) {
			return false;
		}
		return realmSyncResponses != null ? realmSyncResponses.equals(that.realmSyncResponses) : that.realmSyncResponses == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (syncHistoryId ^ (syncHistoryId >>> 32));
		result = 31 * result + (isSyncDone ? 1 : 0);
		result = 31 * result + (syncSessionStartTime != null ? syncSessionStartTime.hashCode() : 0);
		result = 31 * result + (realmSyncResponses != null ? realmSyncResponses.hashCode() : 0);
		return result;
	}

	/**
	 * A class used as a callback. It will be called when the sync precedure is done or if it fails or times out.
	 * <p>
	 * Copyright 2017 Meniga Iceland Inc.
	 */
	public static abstract class PostSyncCallback {
		protected boolean hasNewData;
		protected Long timeStamp;
		protected int numNewTransactions;

		public boolean getHasNewData() {
			return this.hasNewData;
		}

		public abstract void onFailure(Exception exception);

		public abstract void onFinished();

		public abstract void onTimedOut();

		/**
		 * @param millis Sets the start time stamp.
		 */
		public void setStartTimeStamp(long millis) {
			this.timeStamp = millis;
		}

		/**
		 * @return The start time stamp.
		 */
		public Long getStartTimeStamp() {
			return this.timeStamp;
		}

		/**
		 * @return Number of new transactions that came into the system as a result of the sync.
		 */
		public int getNumNewTransactions() {
			return this.numNewTransactions;
		}
	}

	/**
	 * Represents a response for all the accounts in a specific realm. A realm can be e.g. a bank institute.
	 * <p>
	 * Copyright 2017 Meniga Iceland Inc.
	 */
	public static class RealmSyncResponse implements Serializable, Parcelable {
		public static final Creator<RealmSyncResponse> CREATOR = new Creator<RealmSyncResponse>() {
			@Override
			public RealmSyncResponse createFromParcel(Parcel source) {
				return new RealmSyncResponse(source);
			}

			@Override
			public RealmSyncResponse[] newArray(int size) {
				return new RealmSyncResponse[size];
			}
		};

		List<AccountSyncStatus> accountSyncStatuses;

		protected RealmSyncResponse(Parcel in) {
			this.accountSyncStatuses = in.createTypedArrayList(AccountSyncStatus.CREATOR);
		}

		/**
		 * @return An array of sync statuses for each of the accounts the user has in this realm when using particular credentials.
		 */
		public List<AccountSyncStatus> getAccountSyncStatuses() {
			return this.accountSyncStatuses;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			RealmSyncResponse that = (RealmSyncResponse) o;

			return accountSyncStatuses != null ? accountSyncStatuses.equals(that.accountSyncStatuses) : that.accountSyncStatuses == null;
		}

		@Override
		public int hashCode() {
			return accountSyncStatuses != null ? accountSyncStatuses.hashCode() : 0;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeTypedList(accountSyncStatuses);
		}
	}

	/**
	 * Contains further details about the sync status of specific accounts.
	 * <p>
	 * Copyright 2017 Meniga Iceland Inc.
	 */
	public static class AccountSyncStatus implements Serializable, Parcelable {
		public static final Creator<AccountSyncStatus> CREATOR = new Creator<AccountSyncStatus>() {
			@Override
			public AccountSyncStatus createFromParcel(Parcel source) {
				return new AccountSyncStatus(source);
			}

			@Override
			public AccountSyncStatus[] newArray(int size) {
				return new AccountSyncStatus[size];
			}
		};

		private int transactionsProcessed;

		protected AccountSyncStatus(Parcel in) {
			this.transactionsProcessed = in.readInt();
		}

		/**
		 * @return Total number of transactions that has already been processed during this synchronization.
		 */
		public int getTransactionsProcessed() {
			return this.transactionsProcessed;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			AccountSyncStatus that = (AccountSyncStatus) o;

			return transactionsProcessed == that.transactionsProcessed;

		}

		@Override
		public int hashCode() {
			return transactionsProcessed;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(this.transactionsProcessed);
		}
	}

	/**
	 * Contains info on wheather or not the sync process has finished.
	 * <p>
	 * Copyright 2017 Meniga Iceland Inc.
	 */
	public static class MenigaSyncStatus implements Parcelable {
		public static final Creator<MenigaSyncStatus> CREATOR = new Creator<MenigaSyncStatus>() {
			@Override
			public MenigaSyncStatus createFromParcel(Parcel source) {
				return new MenigaSyncStatus(source);
			}

			@Override
			public MenigaSyncStatus[] newArray(int size) {
				return new MenigaSyncStatus[size];
			}
		};

		protected boolean hasCompletedSyncSession;

		protected MenigaSyncStatus(Parcel in) {
			this.hasCompletedSyncSession = in.readByte() != 0;
		}

		/**
		 * @return True if user has a completed synchronization session.
		 */
		public boolean getHasCompletedSyncSession() {
			return hasCompletedSyncSession;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeByte(hasCompletedSyncSession ? (byte) 1 : (byte) 0);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			MenigaSyncStatus that = (MenigaSyncStatus) o;

			return hasCompletedSyncSession == that.hasCompletedSyncSession;

		}

		@Override
		public int hashCode() {
			return (hasCompletedSyncSession ? 1 : 0);
		}
	}

    /*
	--- API calls below ---
	 */

	private void checkSync(final PostSyncCallback postSync, final long timeout, final long interval) {
		if (postSync.getStartTimeStamp() == null) {
			postSync.setStartTimeStamp(DateTime.now().getMillis());
		}
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Result<MenigaSyncStatus> task = isSyncDone();
				MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaSyncStatus>() {
					@Override
					public void onFinished(MenigaSyncStatus result, boolean failed) {
						long delta = DateTime.now().getMillis() - postSync.getStartTimeStamp();
						if (failed || result == null) {
							postSync.onFailure(new Exception("Failure syncing"));
						} else if (result.getHasCompletedSyncSession()) {
							Result<MenigaSync> subTask = MenigaSync.fetch(MenigaSync.this.syncHistoryId);
							MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(subTask, new Interceptor<MenigaSync>() {
								@Override
								public void onFinished(MenigaSync result, boolean failed) {
									if (failed || result == null) {
										postSync.onFailure(new Exception("Failure syncing"));
									}
									postSync.hasNewData = result != null && result.hasNewData();
									postSync.numNewTransactions = result != null ? result.getNumNewTransactions() : 0;
									postSync.onFinished();
								}
							});
						} else if (delta > timeout) {
							postSync.onTimedOut();
						} else {
							checkSync(postSync, timeout, interval);
						}
					}
				});
			}
		}, interval);
	}

	/**
	 * Gets a sync object that was created by MenigaSync.start
	 *
	 * @param syncHistoryId The id of the sync object
	 * @return The sync object
	 */
	public static Result<MenigaSync> fetch(long syncHistoryId) {
		return MenigaSync.apiOperator.getSync(syncHistoryId);
	}

	/**
	 * Starts the accounts synchronization process and also returns a MenigaSync object
	 * that contains further details.
	 * @param timeout Timeout for the sync procedure, the actuall response callback might take longer
	 * @return A new sync object created by starting the sync procedure
	 */
	public static Result<MenigaSync> start(long timeout) {
		return MenigaSync.start(timeout, 1000, null);
	}

	/**
	 * Starts the accounts synchronization process and also returns a MenigaSync object
	 * that contains further details.
	 * @param timeout  The amount of time the background process should try to check the syn result before terminating.
	 * @param interval The amount of time between checks for sync completion in the background
	 * @return A new sync object.
	 */
	public static Result<MenigaSync> start(final long timeout, final long interval, final Interceptor<MenigaSync> onDone) {
		Result<MenigaSync> task = MenigaSync.apiOperator.startSync(timeout);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaSync>() {
			@Override
			public void onFinished(final MenigaSync result, boolean failed) {
				result.checkSync(new PostSyncCallback() {
					@Override
					public void onFailure(Exception excp) {
						if (onDone != null) {
							onDone.onFinished(null, true);
						}
					}

					@Override
					public void onFinished() {
						if (onDone != null) {
							onDone.onFinished(result, false);
						}
					}

					@Override
					public void onTimedOut() {
						if (onDone != null) {
							onDone.onFinished(null, true);
						}
					}
				}, timeout, interval);
			}
		});
	}

	/**
	 * Checks to see if the synchronization operation has finished
	 *
	 * @return A Task containing a boolean indicating if the synchronization is done or not
	 */
	public Result<MenigaSyncStatus> isSyncDone() {
		return MenigaSync.apiOperator.getSyncStatus();
	}
}
