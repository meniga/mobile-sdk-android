package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class RelevanceHookDisplay implements Parcelable, Serializable {
	public static final Creator<RelevanceHookDisplay> CREATOR = new Creator<RelevanceHookDisplay>() {
		@Override
		public RelevanceHookDisplay createFromParcel(Parcel source) {
			return new RelevanceHookDisplay(source);
		}

		@Override
		public RelevanceHookDisplay[] newArray(int size) {
			return new RelevanceHookDisplay[size];
		}
	};

	protected String relevanceHookDetails;
	protected String text;

	protected RelevanceHookDisplay() {
	}

	protected RelevanceHookDisplay(Parcel in) {
		this.relevanceHookDetails = in.readString();
		this.text = in.readString();
	}

	public String getRelevanceHookDetails() {
		return relevanceHookDetails;
	}

	public String getText() {
		return text;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.relevanceHookDetails);
		dest.writeString(this.text);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RelevanceHookDisplay that = (RelevanceHookDisplay) o;

		if (relevanceHookDetails != null ? !relevanceHookDetails.equals(that.relevanceHookDetails) : that.relevanceHookDetails != null) {
			return false;
		}
		return text != null ? text.equals(that.text) : that.text == null;
	}

	@Override
	public int hashCode() {
		int result = relevanceHookDetails != null ? relevanceHookDetails.hashCode() : 0;
		result = 31 * result + (text != null ? text.hashCode() : 0);
		return result;
	}
}