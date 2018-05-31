package com.meniga.sdk.models.eventtracking;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.categories.operators.MenigaCategoryOperations;
import com.meniga.sdk.models.eventtracking.operators.MenigaEventTrackingOperations;

import java.io.Serializable;

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 31.5.2018.
 */
public class MenigaEventTracking implements Parcelable, Serializable {
	private String trackingType;
	private String trackingState;
	private long trackerId;
	private String media;

	protected static MenigaEventTrackingOperations apiOperator;

	protected MenigaEventTracking(String trackingType, String trackingState, long trackerId, String media) {
		this.trackingType = trackingType;
		this.trackingState = trackingState;
		this.trackerId = trackerId;
		this.media = media;
	}

	protected MenigaEventTracking(Parcel in) {
		this.trackingType = in.readString();
		this.trackingState = in.readString();
		this.trackerId = in.readLong();
		this.media = in.readString();
	}

	public static MenigaEventTracking build(String trackingType, String trackingState, long trackerId, String media) {
		return new MenigaEventTracking(trackingType, trackingState, trackerId, media);
	}

	public static void setOperator(MenigaEventTrackingOperations operator) {
		apiOperator = operator;
	}

	public String getTrackingType() {
		return trackingType;
	}

	public String getTrackingState() {
		return trackingState;
	}

	public long getTrackerId() {
		return trackerId;
	}

	public String getMedia() {
		return media;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.trackingType);
		dest.writeString(this.trackingState);
		dest.writeLong(this.trackerId);
		dest.writeString(this.media);
	}

	public static final Creator<MenigaEventTracking> CREATOR = new Creator<MenigaEventTracking>() {
		@Override
		public MenigaEventTracking createFromParcel(Parcel source) {
			return new MenigaEventTracking(source);
		}

		@Override
		public MenigaEventTracking[] newArray(int size) {
			return new MenigaEventTracking[size];
		}
	};

	/*
	 * API Calls
	 */

	public Result<Void> track() {
		return apiOperator.track(this);
	}
}
