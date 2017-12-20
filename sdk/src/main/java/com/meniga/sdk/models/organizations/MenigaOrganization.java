package com.meniga.sdk.models.organizations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.organizations.operators.MenigaOrganizationOperations;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaOrganization implements Serializable, Parcelable {
	private static MenigaOrganizationOperations apiOperator;

	protected long id;
	protected String name;
	protected String altName;
	protected String identifier;
	protected int orderIndex;
	protected String iconFilename;
	protected long imageDataId;
	protected String imageData;
	protected List<MenigaRealm> realms;

	protected MenigaOrganization() {
	}

	protected MenigaOrganization(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
		this.altName = in.readString();
		this.identifier = in.readString();
		this.orderIndex = in.readInt();
		this.iconFilename = in.readString();
		this.imageDataId = in.readLong();
		this.imageData = in.readString();
		this.realms = in.createTypedArrayList(MenigaRealm.CREATOR);
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaOrganizationOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaOrganizationOperations operator) {
		MenigaOrganization.apiOperator = operator;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAltName() {
		return altName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public String getIconFilename() {
		return iconFilename;
	}

	public long getImageDataId() {
		return imageDataId;
	}

	public Bitmap getImageData() throws UnsupportedEncodingException {
		if (imageData == null) {
			return null;
		}
		byte[] bytes = Base64.decode(imageData, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	public List<MenigaRealm> getRealms() {
		return realms;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaOrganization that = (MenigaOrganization) o;

		if (id != that.id) {
			return false;
		}
		if (orderIndex != that.orderIndex) {
			return false;
		}
		if (imageDataId != that.imageDataId) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (altName != null ? !altName.equals(that.altName) : that.altName != null) {
			return false;
		}
		if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) {
			return false;
		}
		if (iconFilename != null ? !iconFilename.equals(that.iconFilename) : that.iconFilename != null) {
			return false;
		}
		if (imageData != null ? !imageData.equals(that.imageData) : that.imageData != null) {
			return false;
		}
		return realms != null ? realms.equals(that.realms) : that.realms == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (altName != null ? altName.hashCode() : 0);
		result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
		result = 31 * result + orderIndex;
		result = 31 * result + (iconFilename != null ? iconFilename.hashCode() : 0);
		result = 31 * result + (int) (imageDataId ^ (imageDataId >>> 32));
		result = 31 * result + (imageData != null ? imageData.hashCode() : 0);
		result = 31 * result + (realms != null ? realms.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeString(this.altName);
		dest.writeString(this.identifier);
		dest.writeInt(this.orderIndex);
		dest.writeString(this.iconFilename);
		dest.writeLong(this.imageDataId);
		dest.writeString(this.imageData);
		dest.writeTypedList(this.realms);
	}

	public static final Parcelable.Creator<MenigaOrganization> CREATOR = new Parcelable.Creator<MenigaOrganization>() {
		@Override
		public MenigaOrganization createFromParcel(Parcel source) {
			return new MenigaOrganization(source);
		}

		@Override
		public MenigaOrganization[] newArray(int size) {
			return new MenigaOrganization[size];
		}
	};

    /*
    API operations below
     */

	/**
	 * Get all organizations defined on this server
	 *
	 * @return A Result containing a list of all the organizations defined on the server
	 */
	public static Result<List<MenigaOrganization>> fetch() {
		return MenigaOrganization.apiOperator.getOrganizations();
	}
}
