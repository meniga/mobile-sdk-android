package com.meniga.sdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a response for all the accounts in a specific realm. A realm can be e.g. a bank institute.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class RealmSyncResponse implements Serializable, Parcelable {

	protected List<AccountSyncStatus> accountSyncStatuses;
	protected AuthenticationChallenge authenticationChallenge;

	/**
	 * @return An array of sync statuses for each of the accounts the user has in this realm when using particular credentials.
	 */
	public List<AccountSyncStatus> getAccountSyncStatuses() {
		return this.accountSyncStatuses;
	}

	/**
	 * @return A challenge to be responded to by the end user, or null if no user response is needed
	 */
	public AuthenticationChallenge getAuthenticationChallenge() {
		return authenticationChallenge;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RealmSyncResponse that = (RealmSyncResponse) o;

		if (accountSyncStatuses != null ? !accountSyncStatuses.equals(that.accountSyncStatuses) : that.accountSyncStatuses != null)
			return false;
		return authenticationChallenge != null ? authenticationChallenge.equals(that.authenticationChallenge) : that.authenticationChallenge == null;

	}

	@Override
	public int hashCode() {
		int result = accountSyncStatuses != null ? accountSyncStatuses.hashCode() : 0;
		result = 31 * result + (authenticationChallenge != null ? authenticationChallenge.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(this.accountSyncStatuses);
		dest.writeParcelable(this.authenticationChallenge, flags);
	}

	public RealmSyncResponse() {
	}

	protected RealmSyncResponse(Parcel in) {
		this.accountSyncStatuses = in.createTypedArrayList(AccountSyncStatus.CREATOR);
		this.authenticationChallenge = in.readParcelable(AuthenticationChallenge.class.getClassLoader());
	}

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
}
