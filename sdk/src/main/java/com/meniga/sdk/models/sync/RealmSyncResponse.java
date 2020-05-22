package com.meniga.sdk.models.sync;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.user.AuthenticationChallenge;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a response for all the accounts in a specific realm. A realm can be e.g. a bank institute.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class RealmSyncResponse implements Serializable, Parcelable {
	protected long realmCredentialsId;
	protected String realmCredentialsDisplayName;
	protected long organizationId;
	protected String organizationName;
	protected String organizationBankCode;
	protected List<AccountSyncStatus> accountSyncStatuses;
	protected AuthenticationChallenge authenticationChallenge;
	protected boolean isSyncDone;
	protected long realmId;
	protected DateTime realmSyncStartDate;
	protected DateTime realmSyncEndDate;
	protected RealmSyncResult status;

	protected RealmSyncResponse() {
	}

	protected RealmSyncResponse(Parcel in) {
		this.realmCredentialsId = in.readLong();
		this.realmCredentialsDisplayName = in.readString();
		this.organizationId = in.readLong();
		this.organizationName = in.readString();
		this.organizationBankCode = in.readString();
		this.accountSyncStatuses = in.createTypedArrayList(AccountSyncStatus.CREATOR);
		this.authenticationChallenge = in.readParcelable(AuthenticationChallenge.class.getClassLoader());
		this.isSyncDone = in.readByte() != 0;
		this.realmId = in.readLong();
		this.realmSyncStartDate = (DateTime) in.readSerializable();
		this.realmSyncEndDate = (DateTime) in.readSerializable();
		int tmpStatus = in.readInt();
		this.status = tmpStatus == -1 ? null : RealmSyncResult.values()[tmpStatus];
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.realmCredentialsId);
		dest.writeString(this.realmCredentialsDisplayName);
		dest.writeLong(this.organizationId);
		dest.writeString(this.organizationName);
		dest.writeString(this.organizationBankCode);
		dest.writeTypedList(this.accountSyncStatuses);
		dest.writeParcelable(this.authenticationChallenge, flags);
		dest.writeByte(this.isSyncDone ? (byte) 1 : (byte) 0);
		dest.writeLong(this.realmId);
		dest.writeSerializable(this.realmSyncStartDate);
		dest.writeSerializable(this.realmSyncEndDate);
		dest.writeInt(this.status == null ? -1 : this.status.ordinal());
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

	public long getRealmCredentialsId() {
		return realmCredentialsId;
	}

	public String getRealmCredentialsDisplayName() {
		return realmCredentialsDisplayName;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getOrganizationBankCode() {
		return organizationBankCode;
	}

	public boolean isSyncDone() {
		return isSyncDone;
	}

	public List<AccountSyncStatus> getAccountSyncStatuses() {
		return accountSyncStatuses;
	}

	public AuthenticationChallenge getAuthenticationChallenge() {
		return authenticationChallenge;
	}

	/**
	 * @return Id of the Realm
	 */
	public long getRealmId() {
		return realmId;
	}

	/**
	 * @return The start time of the synchronization
	 */
	public DateTime getRealmSyncStartDate() {
		return realmSyncStartDate;
	}

	/**
	 * @return The end time of the synchronization
	 */
	public DateTime getRealmSyncEndDate() {
		return realmSyncEndDate;
	}

	/**
	 * @return The status of the synchronization
	 */
	public RealmSyncResult getStatus() {
		return status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RealmSyncResponse that = (RealmSyncResponse) o;

		if (realmCredentialsId != that.realmCredentialsId) return false;
		if (organizationId != that.organizationId) return false;
		if (isSyncDone != that.isSyncDone) return false;
		if (realmId != that.realmId) return false;
		if (realmCredentialsDisplayName != null ? !realmCredentialsDisplayName.equals(that.realmCredentialsDisplayName) : that.realmCredentialsDisplayName != null)
			return false;
		if (organizationName != null ? !organizationName.equals(that.organizationName) : that.organizationName != null) return false;
		if (organizationBankCode != null ? !organizationBankCode.equals(that.organizationBankCode) : that.organizationBankCode != null) return false;
		if (accountSyncStatuses != null ? !accountSyncStatuses.equals(that.accountSyncStatuses) : that.accountSyncStatuses != null) return false;
		if (authenticationChallenge != null ? !authenticationChallenge.equals(that.authenticationChallenge) : that.authenticationChallenge != null)
			return false;
		if (realmSyncStartDate != null ? !realmSyncStartDate.equals(that.realmSyncStartDate) : that.realmSyncStartDate != null) return false;
		if (realmSyncEndDate != null ? !realmSyncEndDate.equals(that.realmSyncEndDate) : that.realmSyncEndDate != null) return false;
		return status == that.status;
	}

	@Override
	public int hashCode() {
		int result = (int) (realmCredentialsId ^ (realmCredentialsId >>> 32));
		result = 31 * result + (realmCredentialsDisplayName != null ? realmCredentialsDisplayName.hashCode() : 0);
		result = 31 * result + (int) (organizationId ^ (organizationId >>> 32));
		result = 31 * result + (organizationName != null ? organizationName.hashCode() : 0);
		result = 31 * result + (organizationBankCode != null ? organizationBankCode.hashCode() : 0);
		result = 31 * result + (accountSyncStatuses != null ? accountSyncStatuses.hashCode() : 0);
		result = 31 * result + (authenticationChallenge != null ? authenticationChallenge.hashCode() : 0);
		result = 31 * result + (isSyncDone ? 1 : 0);
		result = 31 * result + (int) (realmId ^ (realmId >>> 32));
		result = 31 * result + (realmSyncStartDate != null ? realmSyncStartDate.hashCode() : 0);
		result = 31 * result + (realmSyncEndDate != null ? realmSyncEndDate.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		return result;
	}
}
