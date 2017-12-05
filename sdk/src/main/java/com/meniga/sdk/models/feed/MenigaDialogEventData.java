package com.meniga.sdk.models.feed;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaDialogEventData implements Parcelable, Serializable {
	private String imageUrl;
	private String mobileUrl;
	private String webUrl;
	private long dialogId;

	protected MenigaDialogEventData() {
	}

	protected MenigaDialogEventData(Parcel in) {
		this.imageUrl = in.readString();
		this.mobileUrl = in.readString();
		this.webUrl = in.readString();
		this.dialogId = in.readLong();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getMobileUrl() {
		return mobileUrl;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public long getDialogId() {
		return dialogId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.imageUrl);
		dest.writeString(this.mobileUrl);
		dest.writeString(this.webUrl);
		dest.writeLong(this.dialogId);
	}

	public static final Creator<MenigaDialogEventData> CREATOR = new Creator<MenigaDialogEventData>() {
		@Override
		public MenigaDialogEventData createFromParcel(Parcel source) {
			return new MenigaDialogEventData(source);
		}

		@Override
		public MenigaDialogEventData[] newArray(int size) {
			return new MenigaDialogEventData[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaDialogEventData that = (MenigaDialogEventData) o;

		if (dialogId != that.dialogId) {
			return false;
		}
		if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) {
			return false;
		}
		if (mobileUrl != null ? !mobileUrl.equals(that.mobileUrl) : that.mobileUrl != null) {
			return false;
		}
		return webUrl != null ? webUrl.equals(that.webUrl) : that.webUrl == null;
	}

	@Override
	public int hashCode() {
		int result = imageUrl != null ? imageUrl.hashCode() : 0;
		result = 31 * result + (mobileUrl != null ? mobileUrl.hashCode() : 0);
		result = 31 * result + (webUrl != null ? webUrl.hashCode() : 0);
		result = 31 * result + (int) (dialogId ^ (dialogId >>> 32));
		return result;
	}
}
