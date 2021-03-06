package com.meniga.sdk.models.organizations;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaRealmAuthResponse implements Parcelable, Serializable {
	protected boolean authenticationDone;
	protected List<MenigaRealmAuthParameter> requiredParameters;
	protected String contentType;
	protected String textChallenge;
	protected String binaryChallenge;
	protected String errorMessage;
	protected String userIdentifier;
	protected long realmUserId;
	protected boolean canSave;
	protected String loginHelp;
	protected String sessionToken;
	protected String errorMessageCode;
	protected String realmUserPersonEmail;

	protected MenigaRealmAuthResponse() {
	}

	protected MenigaRealmAuthResponse(Parcel in) {
		this.authenticationDone = in.readByte() != 0;
		this.requiredParameters = in.createTypedArrayList(MenigaRealmAuthParameter.CREATOR);
		this.contentType = in.readString();
		this.textChallenge = in.readString();
		this.binaryChallenge = in.readString();
		this.errorMessage = in.readString();
		this.userIdentifier = in.readString();
		this.realmUserId = in.readLong();
		this.canSave = in.readByte() != 0;
		this.loginHelp = in.readString();
		this.sessionToken = in.readString();
		this.errorMessageCode = in.readString();
		this.realmUserPersonEmail = in.readString();
	}

	public boolean isAuthenticationDone() {
		return authenticationDone;
	}

	public List<MenigaRealmAuthParameter> getRequiredParameters() {
		return requiredParameters;
	}

	public String getContentType() {
		return contentType;
	}

	public String getTextChallenge() {
		return textChallenge;
	}

	public String getBinaryChallenge() {
		return binaryChallenge;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getUserIdentifier() {
		return userIdentifier;
	}

	public long getRealmUserId() {
		return realmUserId;
	}

	public boolean isCanSave() {
		return canSave;
	}

	public String getLoginHelp() {
		return loginHelp;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public String getErrorMessageCode() {
		return errorMessageCode;
	}

	public String getRealmUserPersonEmail() {
		return realmUserPersonEmail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaRealmAuthResponse that = (MenigaRealmAuthResponse) o;

		if (authenticationDone != that.authenticationDone) {
			return false;
		}
		if (realmUserId != that.realmUserId) {
			return false;
		}
		if (canSave != that.canSave) {
			return false;
		}
		if (requiredParameters != null ? !requiredParameters.equals(that.requiredParameters) : that.requiredParameters != null) {
			return false;
		}
		if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) {
			return false;
		}
		if (textChallenge != null ? !textChallenge.equals(that.textChallenge) : that.textChallenge != null) {
			return false;
		}
		if (binaryChallenge != null ? !binaryChallenge.equals(that.binaryChallenge) : that.binaryChallenge != null) {
			return false;
		}
		if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) {
			return false;
		}
		if (userIdentifier != null ? !userIdentifier.equals(that.userIdentifier) : that.userIdentifier != null) {
			return false;
		}
		if (sessionToken != null ? !sessionToken.equals(that.sessionToken) : that.sessionToken != null) {
			return false;
		}
		if (errorMessageCode != null ? !errorMessageCode.equals(that.errorMessageCode) : that.errorMessageCode != null) {
			return false;
		}
		if (realmUserPersonEmail != null ? !realmUserPersonEmail.equals(that.realmUserPersonEmail) : that.realmUserPersonEmail != null) {
			return false;
		}
		return loginHelp != null ? loginHelp.equals(that.loginHelp) : that.loginHelp == null;
	}

	@Override
	public int hashCode() {
		int result = (authenticationDone ? 1 : 0);
		result = 31 * result + (requiredParameters != null ? requiredParameters.hashCode() : 0);
		result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
		result = 31 * result + (textChallenge != null ? textChallenge.hashCode() : 0);
		result = 31 * result + (binaryChallenge != null ? binaryChallenge.hashCode() : 0);
		result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
		result = 31 * result + (userIdentifier != null ? userIdentifier.hashCode() : 0);
		result = 31 * result + (int) (realmUserId ^ (realmUserId >>> 32));
		result = 31 * result + (canSave ? 1 : 0);
		result = 31 * result + (loginHelp != null ? loginHelp.hashCode() : 0);
		result = 31 * result + (sessionToken != null ? sessionToken.hashCode() : 0);
		result = 31 * result + (errorMessageCode != null ? errorMessageCode.hashCode() : 0);
		result = 31 * result + (realmUserPersonEmail != null ? realmUserPersonEmail.hashCode() : 0);

		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(this.authenticationDone ? (byte) 1 : (byte) 0);
		dest.writeTypedList(this.requiredParameters);
		dest.writeString(this.contentType);
		dest.writeString(this.textChallenge);
		dest.writeString(this.binaryChallenge);
		dest.writeString(this.errorMessage);
		dest.writeString(this.userIdentifier);
		dest.writeLong(this.realmUserId);
		dest.writeByte(this.canSave ? (byte) 1 : (byte) 0);
		dest.writeString(this.loginHelp);
		dest.writeString(this.sessionToken);
		dest.writeString(this.errorMessageCode);
		dest.writeString(this.realmUserPersonEmail);
	}

	public static final Parcelable.Creator<MenigaRealmAuthResponse> CREATOR = new Parcelable.Creator<MenigaRealmAuthResponse>() {
		@Override
		public MenigaRealmAuthResponse createFromParcel(Parcel source) {
			return new MenigaRealmAuthResponse(source);
		}

		@Override
		public MenigaRealmAuthResponse[] newArray(int size) {
			return new MenigaRealmAuthResponse[size];
		}
	};
}
