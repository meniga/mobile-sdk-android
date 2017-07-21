package com.meniga.sdk.models.merchants;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.categories.MenigaCategoryScore;
import com.meniga.sdk.models.merchants.operators.MenigaMerchantOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a merchant in the Meniga system.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaMerchant implements Serializable, Parcelable, Cloneable {
	public static final Parcelable.Creator<MenigaMerchant> CREATOR = new Parcelable.Creator<MenigaMerchant>() {
		@Override
		public MenigaMerchant createFromParcel(Parcel source) {
			return new MenigaMerchant(source);
		}

		@Override
		public MenigaMerchant[] newArray(int size) {
			return new MenigaMerchant[size];
		}
	};

	protected static MenigaMerchantOperations apiOperations;

	protected long id;
	protected long parentId;
	protected MenigaMerchant parentMerchant;
	protected String identifier;
	protected String masterIdentifier;
	protected String merchantCategoryIdentifier;
	protected String publicIdentifier;
	protected String name;
	protected String parentName;
	protected MenigaMerchantLocation address;
	protected List<MenigaCategoryScore> categoryScores;
	protected List<MenigaMerchant> childMerchants;
	protected MenigaCategoryScore detectedCategory;
	protected String directoryLink;
	protected String email;
	protected String offersLink;
	protected String telephone;
	protected String webpage;

	protected MenigaMerchant() {
	}

	protected MenigaMerchant(Parcel in) {
		this.id = in.readLong();
		this.parentId = in.readLong();
		this.parentMerchant = (MenigaMerchant) in.readSerializable();
		this.identifier = in.readString();
		this.masterIdentifier = in.readString();
		this.merchantCategoryIdentifier = in.readString();
		this.publicIdentifier = in.readString();
		this.name = in.readString();
		this.parentName = in.readString();
		this.address = in.readParcelable(MenigaMerchantLocation.class.getClassLoader());
		this.categoryScores = in.createTypedArrayList(MenigaCategoryScore.CREATOR);
		this.childMerchants = new ArrayList<>();
		byte childsNull = in.readByte();
		in.readList(this.childMerchants, MenigaMerchant.class.getClassLoader());
		if (childsNull == 1) {
			this.childMerchants = null;
		}
		this.detectedCategory = in.readParcelable(MenigaCategoryScore.class.getClassLoader());
		this.directoryLink = in.readString();
		this.email = in.readString();
		this.offersLink = in.readString();
		this.telephone = in.readString();
		this.webpage = in.readString();
	}

	@Override
	protected MenigaMerchant clone() throws CloneNotSupportedException {
		return (MenigaMerchant) super.clone();
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaMerchantOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaMerchantOperations operator) {
		MenigaMerchant.apiOperations = operator;
	}

	/**
	 * @return The ID of a merchant.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return The ID of the parent merchant.
	 */
	public long getParentId() {
		return parentId;
	}

	/**
	 * @return The parent merchant.
	 */
	public MenigaMerchant getParentMerchant() {
		return parentMerchant;
	}

	/**
	 * @return Identifier for merchant.
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return The master ID.
	 */
	public String getMasterIdentifier() {
		return masterIdentifier;
	}

	/**
	 * @return The id for the category of the merchant.
	 */
	public String getMerchantCategoryIdentifier() {
		return merchantCategoryIdentifier;
	}

	/**
	 * @return The public identifier for the merchant.
	 */
	public String getPublicIdentifier() {
		return publicIdentifier;
	}

	/**
	 * @return The name of the merchant.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The name of the parent merchant.
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @return The merchant's address.
	 */
	public MenigaMerchantLocation getAddress() {
		return address;
	}

	/**
	 * @return The category score.
	 */
	public List<MenigaCategoryScore> getCategoryScores() {
		return categoryScores;
	}

	/**
	 * @return A list of all the child merchants for this merchant.
	 */
	public List<MenigaMerchant> getChildMerchants() {
		return childMerchants;
	}

	/**
	 * @return The detected category for this merchant.
	 */
	public MenigaCategoryScore getDetectedCategory() {
		return detectedCategory;
	}

	/**
	 * @return The directory link.
	 */
	public String getDirectoryLink() {
		return directoryLink;
	}

	/**
	 * @return The merchant's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return A link to an offer related to this merchant. Only has meaning in relation to the Offers system.
	 */
	public String getOffersLink() {
		return offersLink;
	}

	/**
	 * @return The merchant's telephone number.
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @return The merchant's web page.
	 */
	public String getWebpage() {
		return webpage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaMerchant that = (MenigaMerchant) o;

		if (id != that.id) {
			return false;
		}
		if (parentId != that.parentId) {
			return false;
		}
		if (parentMerchant != null ? !parentMerchant.equals(that.parentMerchant) : that.parentMerchant != null) {
			return false;
		}
		if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) {
			return false;
		}
		if (masterIdentifier != null ? !masterIdentifier.equals(that.masterIdentifier) : that.masterIdentifier != null) {
			return false;
		}
		if (merchantCategoryIdentifier != null ? !merchantCategoryIdentifier.equals(that.merchantCategoryIdentifier) : that.merchantCategoryIdentifier != null) {
			return false;
		}
		if (publicIdentifier != null ? !publicIdentifier.equals(that.publicIdentifier) : that.publicIdentifier != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (parentName != null ? !parentName.equals(that.parentName) : that.parentName != null) {
			return false;
		}
		if (address != null ? !address.equals(that.address) : that.address != null) {
			return false;
		}
		if (categoryScores != null ? !categoryScores.equals(that.categoryScores) : that.categoryScores != null) {
			return false;
		}
		if (childMerchants != null ? !childMerchants.equals(that.childMerchants) : that.childMerchants != null) {
			return false;
		}
		if (detectedCategory != null ? !detectedCategory.equals(that.detectedCategory) : that.detectedCategory != null) {
			return false;
		}
		if (directoryLink != null ? !directoryLink.equals(that.directoryLink) : that.directoryLink != null) {
			return false;
		}
		if (email != null ? !email.equals(that.email) : that.email != null) {
			return false;
		}
		if (offersLink != null ? !offersLink.equals(that.offersLink) : that.offersLink != null) {
			return false;
		}
		if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) {
			return false;
		}
		return webpage != null ? webpage.equals(that.webpage) : that.webpage == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (parentId ^ (parentId >>> 32));
		result = 31 * result + (parentMerchant != null ? parentMerchant.hashCode() : 0);
		result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
		result = 31 * result + (masterIdentifier != null ? masterIdentifier.hashCode() : 0);
		result = 31 * result + (merchantCategoryIdentifier != null ? merchantCategoryIdentifier.hashCode() : 0);
		result = 31 * result + (publicIdentifier != null ? publicIdentifier.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (categoryScores != null ? categoryScores.hashCode() : 0);
		result = 31 * result + (childMerchants != null ? childMerchants.hashCode() : 0);
		result = 31 * result + (detectedCategory != null ? detectedCategory.hashCode() : 0);
		result = 31 * result + (directoryLink != null ? directoryLink.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (offersLink != null ? offersLink.hashCode() : 0);
		result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
		result = 31 * result + (webpage != null ? webpage.hashCode() : 0);
		return result;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.parentId);
		dest.writeSerializable(this.parentMerchant);
		dest.writeString(this.identifier);
		dest.writeString(this.masterIdentifier);
		dest.writeString(this.merchantCategoryIdentifier);
		dest.writeString(this.publicIdentifier);
		dest.writeString(this.name);
		dest.writeString(this.parentName);
		dest.writeParcelable(this.address, flags);
		dest.writeTypedList(categoryScores);
		dest.writeByte(this.childMerchants == null ? (byte) 1 : (byte) 0);
		dest.writeList(this.childMerchants);
		dest.writeParcelable(this.detectedCategory, flags);
		dest.writeString(this.directoryLink);
		dest.writeString(this.email);
		dest.writeString(this.offersLink);
		dest.writeString(this.telephone);
		dest.writeString(this.webpage);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 API operations below
	 */

	/**
	 * Gets a merchant by id
	 *
	 * @param id The id of the merchant to retrieve
	 * @return The merchant object
	 */
	public static Result<MenigaMerchant> fetch(long id) {
		return MenigaMerchant.apiOperations.getMerchant(id);
	}

	/**
	 * Gets a merchant by ids
	 *
	 * @param ids List of ids of the merchants to retrieve
	 * @return List of merchant objects
	 */
	public static Result<List<MenigaMerchant>> fetch(List<Long> ids) {
		return MenigaMerchant.apiOperations.getMerchants(ids);
	}
}
