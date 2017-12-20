package com.meniga.sdk.models.terms;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class TermsType implements Parcelable, Serializable, Cloneable {
	protected int id;
	protected String name;
	protected String description;

	public TermsType() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TermsType termsType = (TermsType) o;

		if (id != termsType.id) return false;
		if (name != null ? !name.equals(termsType.name) : termsType.name != null) return false;
		return description != null ? description.equals(termsType.description) : termsType.description == null;

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.description);
	}

	protected TermsType(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.description = in.readString();
	}

	public static final Parcelable.Creator<TermsType> CREATOR = new Parcelable.Creator<TermsType>() {
		@Override
		public TermsType createFromParcel(Parcel source) {
			return new TermsType(source);
		}

		@Override
		public TermsType[] newArray(int size) {
			return new TermsType[size];
		}
	};
}
