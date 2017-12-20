package com.meniga.sdk.models.organizations;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.organizations.enums.AuthorizationType;
import com.meniga.sdk.models.organizations.operators.MenigaRealmOperations;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaRealm implements Parcelable, Serializable {
	private static MenigaRealmOperations apiOperations;

	protected long id;
	protected String description;
	protected String identifier;
	protected AuthorizationType authorizationType;
	protected String contentPageId;
	protected String externalRegistrationUrl;
	protected boolean showDuringSignup;

	protected MenigaRealm() {
	}

	public static void setOperations(MenigaRealmOperations operations) {
		apiOperations = operations;
	}

	protected MenigaRealm(Parcel in) {
		this.id = in.readLong();
		this.description = in.readString();
		this.identifier = in.readString();
		int tmpAuthorizationType = in.readInt();
		this.authorizationType = tmpAuthorizationType == -1 ? null : AuthorizationType.values()[tmpAuthorizationType];
		this.contentPageId = in.readString();
		this.externalRegistrationUrl = in.readString();
		this.showDuringSignup = in.readByte() != 0;
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getIdentifier() {
		return identifier;
	}

	public AuthorizationType getAuthorizationType() {
		return authorizationType;
	}

	public String getContentPageId() {
		return contentPageId;
	}

	public String getExternalRegistrationUrl() {
		return externalRegistrationUrl;
	}

	public boolean showDuringSignup() {
		return showDuringSignup;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaRealm that = (MenigaRealm) o;

		if (id != that.id) {
			return false;
		}
		if (showDuringSignup != that.showDuringSignup) {
			return false;
		}
		if (description != null ? !description.equals(that.description) : that.description != null) {
			return false;
		}
		if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) {
			return false;
		}
		if (authorizationType != that.authorizationType) {
			return false;
		}
		if (contentPageId != null ? !contentPageId.equals(that.contentPageId) : that.contentPageId != null) {
			return false;
		}
		return externalRegistrationUrl != null ? externalRegistrationUrl.equals(that.externalRegistrationUrl) : that.externalRegistrationUrl == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
		result = 31 * result + (authorizationType != null ? authorizationType.hashCode() : 0);
		result = 31 * result + (contentPageId != null ? contentPageId.hashCode() : 0);
		result = 31 * result + (externalRegistrationUrl != null ? externalRegistrationUrl.hashCode() : 0);
		result = 31 * result + (showDuringSignup ? 1 : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.description);
		dest.writeString(this.identifier);
		dest.writeInt(this.authorizationType == null ? -1 : this.authorizationType.ordinal());
		dest.writeString(this.contentPageId);
		dest.writeString(this.externalRegistrationUrl);
		dest.writeByte(this.showDuringSignup ? (byte) 1 : (byte) 0);
	}

	public static final Parcelable.Creator<MenigaRealm> CREATOR = new Parcelable.Creator<MenigaRealm>() {
		@Override
		public MenigaRealm createFromParcel(Parcel source) {
			return new MenigaRealm(source);
		}

		@Override
		public MenigaRealm[] newArray(int size) {
			return new MenigaRealm[size];
		}
	};

    /*
    API methods below
     */

	public static Result<List<MenigaRealmAccount>> getRealmAccounts(long realmUserId, String sessionToken) {
		return apiOperations.getRealmAccounts(realmUserId, sessionToken);
	}

	public static Result<List<MenigaRealmAccount>> addRealmAccountsToMeniga(long realmUserId, List<MenigaRealmAccount> accounts, String sessionToken) {
		return apiOperations.addRealmAccountsToMeniga(realmUserId, accounts, sessionToken);
	}

	public Result<MenigaRealmAuthResponse> getInitialRealmAuthenticationSteps() {
		return apiOperations.performBankAuthenticationStep(id, null, "", null);
	}

	public Result<MenigaRealmAuthResponse> performBankAuthenticationStep(List<MenigaRealmAuthParameter> authPars, String userId, String sessionToken) {
		return apiOperations.performBankAuthenticationStep(id, authPars, userId, sessionToken);
	}
}
