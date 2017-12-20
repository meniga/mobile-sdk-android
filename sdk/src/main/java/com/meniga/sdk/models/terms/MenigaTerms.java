package com.meniga.sdk.models.terms;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.terms.operators.MenigaTermsOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTerms implements Parcelable, Serializable, Cloneable {
	protected static MenigaTermsOperations apiOperator;

	protected DateTime creationDate;
	protected String content;
	protected String culture;
	protected Boolean acceptanceRequired;
	protected TermsType termsAndConditionsType;
	protected TermsState termsAndConditionsState;
	protected DateTime modifiedAt;

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getContent() {
		return content;
	}

	public String getCulture() {
		return culture;
	}

	public Boolean getAcceptanceRequired() {
		return acceptanceRequired;
	}

	public TermsType getTermsAndConditionsType() {
		return termsAndConditionsType;
	}

	public TermsState getTermsAndConditionsState() {
		return termsAndConditionsState;
	}

	public DateTime getModifiedAt() {
		return modifiedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MenigaTerms that = (MenigaTerms) o;

		if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null)
			return false;
		if (content != null ? !content.equals(that.content) : that.content != null) return false;
		if (culture != null ? !culture.equals(that.culture) : that.culture != null) return false;
		if (acceptanceRequired != null ? !acceptanceRequired.equals(that.acceptanceRequired) : that.acceptanceRequired != null)
			return false;
		if (termsAndConditionsType != null ? !termsAndConditionsType.equals(that.termsAndConditionsType) : that.termsAndConditionsType != null)
			return false;
		if (termsAndConditionsState != that.termsAndConditionsState) return false;
		return modifiedAt != null ? modifiedAt.equals(that.modifiedAt) : that.modifiedAt == null;

	}

	@Override
	public int hashCode() {
		int result = creationDate != null ? creationDate.hashCode() : 0;
		result = 31 * result + (content != null ? content.hashCode() : 0);
		result = 31 * result + (culture != null ? culture.hashCode() : 0);
		result = 31 * result + (acceptanceRequired != null ? acceptanceRequired.hashCode() : 0);
		result = 31 * result + (termsAndConditionsType != null ? termsAndConditionsType.hashCode() : 0);
		result = 31 * result + (termsAndConditionsState != null ? termsAndConditionsState.hashCode() : 0);
		result = 31 * result + (modifiedAt != null ? modifiedAt.hashCode() : 0);
		return result;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.creationDate);
		dest.writeString(this.content);
		dest.writeString(this.culture);
		dest.writeValue(this.acceptanceRequired);
		dest.writeParcelable(this.termsAndConditionsType, flags);
		dest.writeInt(this.termsAndConditionsState == null ? -1 : this.termsAndConditionsState.ordinal());
		dest.writeSerializable(this.modifiedAt);
	}

	public MenigaTerms() {
	}

	protected MenigaTerms(Parcel in) {
		this.creationDate = (DateTime) in.readSerializable();
		this.content = in.readString();
		this.culture = in.readString();
		this.acceptanceRequired = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.termsAndConditionsType = in.readParcelable(TermsType.class.getClassLoader());
		int tmpTermsAndConditionsState = in.readInt();
		this.termsAndConditionsState = tmpTermsAndConditionsState == -1 ? null : TermsState.values()[tmpTermsAndConditionsState];
		this.modifiedAt = (DateTime) in.readSerializable();
	}

	public static final Creator<MenigaTerms> CREATOR = new Creator<MenigaTerms>() {
		@Override
		public MenigaTerms createFromParcel(Parcel source) {
			return new MenigaTerms(source);
		}

		@Override
		public MenigaTerms[] newArray(int size) {
			return new MenigaTerms[size];
		}
	};

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaTermsOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaTermsOperations operator) {
		MenigaTerms.apiOperator = operator;
	}

	/*
	API CALLS
	 */

	/**
	 * Returns the public system settings.
	 *
	 * @return Public system settings.
	 */
	public static Result<List<MenigaTerms>> fetch() {
		return MenigaTerms.apiOperator.getTerms(MenigaSDK.getMenigaSettings().getCulture());
	}

	public static Result<MenigaTerms> fetch(long typeId) {
		return MenigaTerms.apiOperator.getTerm(MenigaSDK.getMenigaSettings().getCulture(), typeId);
	}

	public static Result<List<MenigaTermType>> fetchTypes() {
		return MenigaTerms.apiOperator.getTermTypes(MenigaSDK.getMenigaSettings().getCulture());
	}

	public static Result<Void> accept(long typeId) {
		return MenigaTerms.apiOperator.acceptTerms(typeId);
	}

	public static Result<Void> decline(long typeId) {
		return MenigaTerms.apiOperator.declineTerms(typeId);
	}
}
