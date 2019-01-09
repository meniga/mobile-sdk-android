package com.meniga.sdk.models.merchants;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Represents further details about the location and address of a merchant.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaMerchantLocation implements Serializable, Parcelable {
	protected String city;
	protected String country;
	protected String countryCode;
	protected Object latitude;
	protected Object longitude;
	protected String postalCode;
	protected String streetLine1;
	protected String streetLine2;

	protected MenigaMerchantLocation() {
	}

	/**
	 * @return The merchant's city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return The merchant's country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return The merchant's country code.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @return The merchant's latitude.
	 */
	public Double getLatitude() {
		if (latitude == null) {
			return null;
		}
		try {
			return Double.parseDouble(latitude.toString());
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @return The merchant's longitude.
	 */
	public Double getLongitude() {
		if (longitude == null) {
			return null;
		}
		try {
			return Double.parseDouble(longitude.toString());
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @return The merchant's postal code.
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @return The merchant's street line 1.
	 */
	public String getStreetLine1() {
		return streetLine1;
	}

	/**
	 * @return The merchant's street line 2.
	 */
	public String getStreetLine2() {
		return streetLine2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaMerchantLocation that = (MenigaMerchantLocation) o;

		if (city != null ? !city.equals(that.city) : that.city != null) {
			return false;
		}
		if (country != null ? !country.equals(that.country) : that.country != null) {
			return false;
		}
		if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) {
			return false;
		}
		if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) {
			return false;
		}
		if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) {
			return false;
		}
		if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) {
			return false;
		}
		if (streetLine1 != null ? !streetLine1.equals(that.streetLine1) : that.streetLine1 != null) {
			return false;
		}
		return streetLine2 != null ? streetLine2.equals(that.streetLine2) : that.streetLine2 == null;
	}

	@Override
	public int hashCode() {
		int result = city != null ? city.hashCode() : 0;
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
		result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
		result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
		result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
		result = 31 * result + (streetLine1 != null ? streetLine1.hashCode() : 0);
		result = 31 * result + (streetLine2 != null ? streetLine2.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.city);
		dest.writeString(this.country);
		dest.writeString(this.countryCode);
		dest.writeString(this.latitude == null ? null : this.latitude.toString());
		dest.writeString(this.longitude == null ? null : this.longitude.toString());
		dest.writeString(this.postalCode);
		dest.writeString(this.streetLine1);
		dest.writeString(this.streetLine2);
	}

	private MenigaMerchantLocation(Parcel in) {
		this.city = in.readString();
		this.country = in.readString();
		this.countryCode = in.readString();
		this.latitude = in.readString();
		this.longitude = in.readString();
		this.postalCode = in.readString();
		this.streetLine1 = in.readString();
		this.streetLine2 = in.readString();
	}

	public static final Parcelable.Creator<MenigaMerchantLocation> CREATOR = new Parcelable.Creator<MenigaMerchantLocation>() {
		@Override
		public MenigaMerchantLocation createFromParcel(Parcel source) {
			return new MenigaMerchantLocation(source);
		}

		@Override
		public MenigaMerchantLocation[] newArray(int size) {
			return new MenigaMerchantLocation[size];
		}
	};
}
