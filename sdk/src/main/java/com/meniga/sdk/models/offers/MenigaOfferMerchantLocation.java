package com.meniga.sdk.models.offers;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOfferMerchantLocation implements Serializable, Parcelable {

	protected long id;
	protected String identifier;
	protected Double latitude;
	protected Double longitude;
	protected String name;
	protected String address;
	protected String webpage;
	protected Double distanceKm;

	protected MenigaOfferMerchantLocation() {
	}

	public long getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getWebpage() {
		return webpage;
	}

	public Double getDistanceKm() {
		return distanceKm;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.identifier);
		dest.writeValue(this.latitude);
		dest.writeValue(this.longitude);
		dest.writeString(this.name);
		dest.writeString(this.address);
		dest.writeString(this.webpage);
		dest.writeValue(this.distanceKm);
	}

	protected MenigaOfferMerchantLocation(Parcel in) {
		this.id = in.readLong();
		this.identifier = in.readString();
		this.latitude = (Double) in.readValue(Double.class.getClassLoader());
		this.longitude = (Double) in.readValue(Double.class.getClassLoader());
		this.name = in.readString();
		this.address = in.readString();
		this.webpage = in.readString();
		this.distanceKm = (Double) in.readValue(Double.class.getClassLoader());
	}

	public static final Creator<MenigaOfferMerchantLocation> CREATOR = new Creator<MenigaOfferMerchantLocation>() {
		@Override
		public MenigaOfferMerchantLocation createFromParcel(Parcel source) {
			return new MenigaOfferMerchantLocation(source);
		}

		@Override
		public MenigaOfferMerchantLocation[] newArray(int size) {
			return new MenigaOfferMerchantLocation[size];
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

		MenigaOfferMerchantLocation that = (MenigaOfferMerchantLocation) o;

		if (id != that.id) {
			return false;
		}
		if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) {
			return false;
		}
		if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) {
			return false;
		}
		if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (address != null ? !address.equals(that.address) : that.address != null) {
			return false;
		}
		if (webpage != null ? !webpage.equals(that.webpage) : that.webpage != null) {
			return false;
		}
		return distanceKm != null ? distanceKm.equals(that.distanceKm) : that.distanceKm == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
		result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
		result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (webpage != null ? webpage.hashCode() : 0);
		result = 31 * result + (distanceKm != null ? distanceKm.hashCode() : 0);
		return result;
	}
}
