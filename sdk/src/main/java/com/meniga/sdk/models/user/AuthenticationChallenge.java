package com.meniga.sdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.organizations.MenigaRealmAuthParameter;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class AuthenticationChallenge implements Parcelable, Serializable {
	protected ChallengeContentType contentType;
	protected String textChallenge;
	protected String errorMessage;
	protected String errorMessageCode;
	protected String userIdentifier;
	protected boolean canSave;
	protected String loginHelp;
	protected List<MenigaRealmAuthParameter> requiredParameters;

	/**
	 * @return Parameters that the end-user needs to enter
	 */
	public List<MenigaRealmAuthParameter> getRequiredParameters() {
		return requiredParameters;
	}

	/**
	 * @return Content type of the challenge
	 */
	public ChallengeContentType getContentType() {
		return contentType;
	}

	/**
	 * @return Text challenge to be displayed to the end-user.
	 */
	public String getTextChallenge() {
		return textChallenge;
	}

	/**
	 * @return An error message to be displayed to the end-user
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return The nature of the error
	 */
	public String getErrorMessageCode() {
		return errorMessageCode;
	}

	/**
	 * @return Identifier of the end user in the current realm's namespace
	 */
	public String getUserIdentifier() {
		return userIdentifier;
	}

	/**
	 * @return : Indicates if parameters can be saved by the system
	 */
	public boolean isCanSave() {
		return canSave;
	}

	/**
	 * @return A help content to display to the end user
	 */
	public String getLoginHelp() {
		return loginHelp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AuthenticationChallenge that = (AuthenticationChallenge) o;

		if (canSave != that.canSave) return false;
		if (contentType != that.contentType) return false;
		if (textChallenge != null ? !textChallenge.equals(that.textChallenge) : that.textChallenge != null)
			return false;
		if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null)
			return false;
		if (errorMessageCode != null ? !errorMessageCode.equals(that.errorMessageCode) : that.errorMessageCode != null)
			return false;
		if (userIdentifier != null ? !userIdentifier.equals(that.userIdentifier) : that.userIdentifier != null)
			return false;
		if (loginHelp != null ? !loginHelp.equals(that.loginHelp) : that.loginHelp != null)
			return false;
		return requiredParameters != null ? requiredParameters.equals(that.requiredParameters) : that.requiredParameters == null;

	}

	@Override
	public int hashCode() {
		int result = contentType != null ? contentType.hashCode() : 0;
		result = 31 * result + (textChallenge != null ? textChallenge.hashCode() : 0);
		result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
		result = 31 * result + (errorMessageCode != null ? errorMessageCode.hashCode() : 0);
		result = 31 * result + (userIdentifier != null ? userIdentifier.hashCode() : 0);
		result = 31 * result + (canSave ? 1 : 0);
		result = 31 * result + (loginHelp != null ? loginHelp.hashCode() : 0);
		result = 31 * result + (requiredParameters != null ? requiredParameters.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.contentType == null ? -1 : this.contentType.ordinal());
		dest.writeString(this.textChallenge);
		dest.writeString(this.errorMessage);
		dest.writeString(this.errorMessageCode);
		dest.writeString(this.userIdentifier);
		dest.writeByte(this.canSave ? (byte) 1 : (byte) 0);
		dest.writeString(this.loginHelp);
		dest.writeTypedList(this.requiredParameters);
	}

	public AuthenticationChallenge() {
	}

	protected AuthenticationChallenge(Parcel in) {
		int tmpContentType = in.readInt();
		this.contentType = tmpContentType == -1 ? null : ChallengeContentType.values()[tmpContentType];
		this.textChallenge = in.readString();
		this.errorMessage = in.readString();
		this.errorMessageCode = in.readString();
		this.userIdentifier = in.readString();
		this.canSave = in.readByte() != 0;
		this.loginHelp = in.readString();
		this.requiredParameters = in.createTypedArrayList(MenigaRealmAuthParameter.CREATOR);
	}

	public static final Creator<AuthenticationChallenge> CREATOR = new Creator<AuthenticationChallenge>() {
		@Override
		public AuthenticationChallenge createFromParcel(Parcel source) {
			return new AuthenticationChallenge(source);
		}

		@Override
		public AuthenticationChallenge[] newArray(int size) {
			return new AuthenticationChallenge[size];
		}
	};
}
