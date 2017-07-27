package com.meniga.sdk.models.user;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.io.Serializable;

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

	protected static MenigaSyncOperations apiOperator;

	protected boolean hasCompletedSyncSession;
	protected boolean isSynchronizationNeeded;
	protected SynchronizationStatus synchronizationStatus;

	/**
	 * @return True if user has a completed synchronization session
	 */
	public boolean isHasCompletedSyncSession() {
		return hasCompletedSyncSession;
	}

	/**
	 * @return True if synchronization is needed for the user
	 */
	public boolean isSynchronizationNeeded() {
		return isSynchronizationNeeded;
	}

	/**
	 * @return Current synchronization status
	 */
	public SynchronizationStatus getSynchronizationStatus() {
		return synchronizationStatus;
	}

	@Override
	public int hashCode() {
		int result = (hasCompletedSyncSession ? 1 : 0);
		result = 31 * result + (isSynchronizationNeeded ? 1 : 0);
		result = 31 * result + (synchronizationStatus != null ? synchronizationStatus.hashCode() : 0);
		return result;
	}

	@Override
	protected MenigaSync clone() throws CloneNotSupportedException {
		return (MenigaSync) super.clone();
	}


	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaSyncOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaSyncOperations operator) {
		MenigaSync.apiOperator = operator;
	}



	private boolean hasNewData() {
		if (this.synchronizationStatus.realmSyncResponses == null || this.synchronizationStatus.realmSyncResponses.size() == 0) {
			return false;
		}
		for (RealmSyncResponse resp : this.synchronizationStatus.realmSyncResponses) {
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
		if (this.synchronizationStatus.realmSyncResponses == null || this.synchronizationStatus.realmSyncResponses.size() == 0) {
			return 0;
		}
		int cnt = 0;
		for (RealmSyncResponse resp : this.synchronizationStatus.realmSyncResponses) {
			for (AccountSyncStatus stat : resp.getAccountSyncStatuses()) {
				cnt += stat.getTransactionsProcessed();
			}
		}
		return cnt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaSync that = (MenigaSync) o;

		if (hasCompletedSyncSession != that.hasCompletedSyncSession) return false;
		if (isSynchronizationNeeded != that.isSynchronizationNeeded) return false;
		return synchronizationStatus != null ? synchronizationStatus.equals(that.synchronizationStatus) : that.synchronizationStatus == null;

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
							Result<MenigaSync> subTask = MenigaSync.fetch(MenigaSync.this.synchronizationStatus.syncHistoryId);
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(this.hasCompletedSyncSession ? (byte) 1 : (byte) 0);
		dest.writeByte(this.isSynchronizationNeeded ? (byte) 1 : (byte) 0);
		dest.writeParcelable(this.synchronizationStatus, flags);
	}

	public MenigaSync() {
	}

	protected MenigaSync(Parcel in) {
		this.hasCompletedSyncSession = in.readByte() != 0;
		this.isSynchronizationNeeded = in.readByte() != 0;
		this.synchronizationStatus = in.readParcelable(SynchronizationStatus.class.getClassLoader());
	}

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
}
