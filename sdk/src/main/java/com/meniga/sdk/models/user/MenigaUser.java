package com.meniga.sdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.user.operators.MenigaUserOperations;
import com.meniga.sdk.webservices.MenigaWebException;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaUser implements Serializable, Parcelable, Cloneable {
	public static final Parcelable.Creator<MenigaUser> CREATOR = new Parcelable.Creator<MenigaUser>() {
		@Override
		public MenigaUser createFromParcel(Parcel source) {
			return new MenigaUser(source);
		}

		@Override
		public MenigaUser[] newArray(int size) {
			return new MenigaUser[size];
		}
	};

	protected static MenigaUserOperations apiOperator;

	public MenigaWebException ex;

	protected DateTime created;
	protected String culture;
	protected String email;
	protected DateTime lastLoginDate;
	protected String firstName;
	protected String lastName;
	protected long personId;
	protected String realmUserIdentity;
	protected DateTime termsAndConditionsAcceptDate;
	protected Integer termsAndConditionsId;
	protected long userId;
	protected MenigaUserProfile profile;

	protected MenigaUser() {
	}

	protected MenigaUser(Parcel in) {
		this.created = (DateTime) in.readSerializable();
		this.culture = in.readString();
		this.email = in.readString();
		this.lastLoginDate = (DateTime) in.readSerializable();
		this.firstName = in.readString();
		this.lastName = in.readString();
		this.personId = in.readLong();
		this.realmUserIdentity = in.readString();
		this.termsAndConditionsAcceptDate = (DateTime) in.readSerializable();
		this.termsAndConditionsId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userId = in.readLong();
		this.profile = in.readParcelable(MenigaUserProfile.class.getClassLoader());
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaUserOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaUserOperations operator) {
		apiOperator = operator;
	}

	@Override
	protected MenigaUser clone() throws CloneNotSupportedException {
		return (MenigaUser) super.clone();
	}

	/**
	 * @return The id of the person currently associated with this user.
	 */
	public long getPersonId() {
		return this.personId;
	}

	/**
	 * @return The first name of the person associated with this user
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @return The last name of the person associated with this user
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @return The profile of the person associated with this user
	 */
	public MenigaUserProfile getProfile() {
		return this.profile;
	}

	/**
	 * @return Creation time of the user
	 */
	public DateTime getCreated() {
		return created;
	}

	/**
	 * @return Culture of the user
	 */
	public String getCulture() {
		return culture;
	}

	/**
	 * @return Get email of user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return Last login date of user
	 */
	public DateTime getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @return The realm user identity
	 */
	public String getRealmUserIdentity() {
		return realmUserIdentity;
	}

	/**
	 * @return Date of the user accepting T&lt;C
	 */
	public DateTime getTermsAndConditionsAcceptDate() {
		return termsAndConditionsAcceptDate;
	}

	/**
	 * @return Id of accepted T&lt;C
	 */
	public Integer getTermsAndConditionsId() {
		return termsAndConditionsId;
	}

	/**
	 * @return Get the user id
	 */
	public long getUserId() {
		return userId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this.created);
		dest.writeString(this.culture);
		dest.writeString(this.email);
		dest.writeSerializable(this.lastLoginDate);
		dest.writeString(this.firstName);
		dest.writeString(this.lastName);
		dest.writeLong(this.personId);
		dest.writeString(this.realmUserIdentity);
		dest.writeSerializable(this.termsAndConditionsAcceptDate);
		dest.writeValue(this.termsAndConditionsId);
		dest.writeLong(this.userId);
		dest.writeParcelable(this.profile, flags);
	}

	/*
	 * API operations below
	 */

	/**
	 * Fetches the current user as well as all connected users
	 *
	 * @return A task containing a list of the current user as well as all conntected users
	 */
	public static Result<List<MenigaUser>> fetch() {
		return apiOperator.getUsers();
	}

	public static Result<Void> setCulture(String culture) {
		return apiOperator.setCulture(culture);
	}

	public static Result<MenigaUser> create(String email, String password, String culture) {
		return apiOperator.registerUser(email, password, culture);
	}

	public static Result<Void> forgotPassword(String email) {
		return apiOperator.forgotPassword(email);
	}

	/**
	 * Returns an array of the currently logged in user's meta data
	 * @return A list of all user metadata
	 */
	public static Result<List<MenigaUserMetaData>> fetchMetaData() {
		return apiOperator.getUserMetaData(new ArrayList<String>());
	}

	/**
	 * Returns an array of the currently logged in user's meta data that match the keys provided
	 * @param filter Keys of meta data to filter by
	 * @return A list of filtered user metadata
	 */
	public static Result<List<MenigaUserMetaData>> fetchMetaData(List<String> filter) {
		return apiOperator.getUserMetaData(filter);
	}

	public static Result<MenigaUserMetaData> saveMetaData(String key, String value) {
		return apiOperator.saveMetaData(key, value);
	}

	public static Result<Void> resetPassword(String resetPasswordToken, String email, String newPassword) {
		return apiOperator.resetPassword(resetPasswordToken, email, newPassword);
	}

	/**
	 * Updates the email address of the user.
	 *
	 * @param newEmail user's new email
	 * @param password user's password
	 */
	@NotNull
	public static Result<Void> updateEmail(@NotNull String newEmail, @NotNull String password) {
		return apiOperator.updateEmail(newEmail, password);
	}

	/**
	 * Changes the password of the user.
	 *
	 * @param currentPassword user's old password
	 * @param newPassword the new password
	 */
	@NotNull
	public static Result<Void> changePassword(@NotNull String currentPassword, @NotNull String newPassword) {
		return apiOperator.changePassword(currentPassword, newPassword);
	}

	/**
	 * Delete the given person and remove transactions and accounts
	 * (belonging to the person, not the household), realm user information, alerts etc.
	 */
	@NotNull
	public static Result<Void> delete() {
		return apiOperator.delete();
	}
}
