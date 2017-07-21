package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class RelevanceHook implements Parcelable, Serializable, Cloneable {

	protected String text;
	protected String detailsUrl;

	protected RelevanceHook() {
	}

	protected RelevanceHook(Parcel in) {
		this.text = in.readString();
		this.detailsUrl = in.readString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RelevanceHook that = (RelevanceHook) o;

		if (text != null ? !text.equals(that.text) : that.text != null) {
			return false;
		}
		return detailsUrl != null ? detailsUrl.equals(that.detailsUrl) : that.detailsUrl == null;
	}

	@Override
	public int hashCode() {
		int result = text != null ? text.hashCode() : 0;
		result = 31 * result + (detailsUrl != null ? detailsUrl.hashCode() : 0);
		return result;
	}

	public String getText() {
		return text;
	}

	public String getDetailsUrl() {
		return detailsUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.text);
		dest.writeString(this.detailsUrl);
	}

	public static final Creator<RelevanceHook> CREATOR = new Creator<RelevanceHook>() {
		@Override
		public RelevanceHook createFromParcel(Parcel source) {
			return new RelevanceHook(source);
		}

		@Override
		public RelevanceHook[] newArray(int size) {
			return new RelevanceHook[size];
		}
	};
}
