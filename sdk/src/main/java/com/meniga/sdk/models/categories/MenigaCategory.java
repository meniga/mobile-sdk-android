package com.meniga.sdk.models.categories;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.categories.enums.CategoryRequest;
import com.meniga.sdk.models.categories.enums.CategoryType;
import com.meniga.sdk.models.categories.operators.MenigaCategoryOperations;
import com.meniga.sdk.providers.tasks.Continuation;
import com.meniga.sdk.providers.tasks.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Category information object. Every transaction includes a category that is mapped within the Meniga system.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaCategory implements Parcelable, Serializable, Cloneable {
	public static final Creator<MenigaCategory> CREATOR = new Creator<MenigaCategory>() {
		public MenigaCategory createFromParcel(Parcel source) {
			return new MenigaCategory(source);
		}

		public MenigaCategory[] newArray(int size) {
			return new MenigaCategory[size];
		}
	};

	protected static MenigaCategoryOperations apiOperator;

	protected long id;
	protected String name;
	protected String otherCategoryName;
	protected String categoryDisplay;
	protected Long parentCategoryId;
	protected Boolean isPublic;
	protected Boolean isFixedExpenses;
	protected CategoryType categoryType;
	protected String categoryRank;
	protected Integer budgetGenerationType;
	protected List<MenigaCategory> children = new ArrayList<>();
	protected MenigaCategory parent;
	protected Integer categoryContextId;
	protected Integer orderId;
	protected String displayData;
	protected String fixedIcon;

	protected MenigaCategory() {
	}

	protected MenigaCategory(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
		this.otherCategoryName = in.readString();
		this.categoryDisplay = in.readString();
		this.parentCategoryId = (Long) in.readValue(Long.class.getClassLoader());
		this.isPublic = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.isFixedExpenses = (Boolean) in.readValue(Boolean.class.getClassLoader());
		int tmpCategoryType = in.readInt();
		this.categoryType = tmpCategoryType == -1 ? null : CategoryType.values()[tmpCategoryType];
		this.categoryRank = in.readString();
		this.budgetGenerationType = (Integer) in.readValue(Integer.class.getClassLoader());
		this.children = in.createTypedArrayList(MenigaCategory.CREATOR);
		this.categoryContextId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.orderId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.displayData = in.readString();
		this.fixedIcon = in.readString();

		if (children != null) {
			for (MenigaCategory child : children) {
				child.parent = this;
			}
		}
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaCategoryOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaCategoryOperations operator) {
		MenigaCategory.apiOperator = operator;
	}

	@Override
	protected MenigaCategory clone() throws CloneNotSupportedException {
		return (MenigaCategory) super.clone();
	}

	/**
	 * @return The id of the category.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return The name of the category.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return Default name of parent category when presented as "other" category.
	 */
	public String getOtherCategoryName() {
		return this.otherCategoryName;
	}

	/**
	 * @return The text to display for categories.
	 */
	public String getCategoryDisplay() {
		return this.categoryDisplay;
	}

	/**
	 * @return The ID of the parent category if this category is a child category, otherwise null.
	 */
	public Long getParentCategoryId() {
		return this.parentCategoryId;
	}

	/**
	 * @return True if the category is a public category. If it's a user category, it will be false.
	 */
	public Boolean getIsPublic() {
		return this.isPublic;
	}

	/**
	 * @return True if the category is a fixed expenses category, otherwise false.
	 */
	public Boolean getIsFixedExpenses() {
		return this.isFixedExpenses;
	}

	/**
	 * @return The type of the category = ['0', '1', '2', '3'].
	 */
	public CategoryType getCategoryType() {
		return this.categoryType;
	}

	/**
	 * @return The rank of the category = ['0', '1', '2', '3'].
	 */
	public String getCategoryRank() {
		return this.categoryRank;
	}

	/**
	 * @return The budget generation type for the category
	 */
	public Integer getBudgetGenerationType() {
		return this.budgetGenerationType;
	}

	/**
	 * @return A list of child categories.
	 */
	public List<MenigaCategory> getChildren() {
		return this.children;
	}

	/**
	 * @return The parent of a child category.
	 */
	public MenigaCategory getParent() {
		return this.parent;
	}

	/**
	 * @return Indicates what context the category belongs to; normal or small business user.
	 */
	public Integer getCategoryContextId() {
		return this.categoryContextId;
	}

	/**
	 * @return The ascending order of the category.
	 */
	public Integer getOrderId() {
		return this.orderId;
	}

	/**
	 * @return Additional data for displaying the category.
	 */
	public String getDisplayData() {
		return this.displayData;
	}

	/**
	 * @return Gets the cross platform icon character, used for looking up a character in a special icon font set.
	 */
	public String getFixedIcon() {
		return this.fixedIcon;
	}

	@Override
	public String toString() {
		if (this.name == null) {
			return "null";
		}
		return this.id + ": " + this.name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaCategory that = (MenigaCategory) o;

		if (id != that.id) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (otherCategoryName != null ? !otherCategoryName.equals(that.otherCategoryName) : that.otherCategoryName != null) {
			return false;
		}
		if (categoryDisplay != null ? !categoryDisplay.equals(that.categoryDisplay) : that.categoryDisplay != null) {
			return false;
		}
		if (parentCategoryId != null ? !parentCategoryId.equals(that.parentCategoryId) : that.parentCategoryId != null) {
			return false;
		}
		if (isPublic != null ? !isPublic.equals(that.isPublic) : that.isPublic != null) {
			return false;
		}
		if (isFixedExpenses != null ? !isFixedExpenses.equals(that.isFixedExpenses) : that.isFixedExpenses != null) {
			return false;
		}
		if (categoryType != that.categoryType) {
			return false;
		}
		if (categoryRank != null ? !categoryRank.equals(that.categoryRank) : that.categoryRank != null) {
			return false;
		}
		if (budgetGenerationType != null ? !budgetGenerationType.equals(that.budgetGenerationType) : that.budgetGenerationType != null) {
			return false;
		}
		if (children != null ? !children.equals(that.children) : that.children != null) {
			return false;
		}
		if (parent == null && that.parent != null || parent != null && that.parent == null) {
			return false;
		}
		if (parent != null && parent.getId() != that.parent.getId()) {
			return false;
		}
		if (categoryContextId != null ? !categoryContextId.equals(that.categoryContextId) : that.categoryContextId != null) {
			return false;
		}
		if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) {
			return false;
		}
		if (displayData != null ? !displayData.equals(that.displayData) : that.displayData != null) {
			return false;
		}
		return fixedIcon != null ? fixedIcon.equals(that.fixedIcon) : that.fixedIcon == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (otherCategoryName != null ? otherCategoryName.hashCode() : 0);
		result = 31 * result + (categoryDisplay != null ? categoryDisplay.hashCode() : 0);
		result = 31 * result + (parentCategoryId != null ? parentCategoryId.hashCode() : 0);
		result = 31 * result + (isPublic != null ? isPublic.hashCode() : 0);
		result = 31 * result + (isFixedExpenses != null ? isFixedExpenses.hashCode() : 0);
		result = 31 * result + (categoryType != null ? categoryType.hashCode() : 0);
		result = 31 * result + (categoryRank != null ? categoryRank.hashCode() : 0);
		result = 31 * result + (budgetGenerationType != null ? budgetGenerationType.hashCode() : 0);
		result = 31 * result + (children != null ? children.hashCode() : 0);
		result = 31 * result + (parent != null ? parent.hashCode() : 0);
		result = 31 * result + (categoryContextId != null ? categoryContextId.hashCode() : 0);
		result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
		result = 31 * result + (displayData != null ? displayData.hashCode() : 0);
		result = 31 * result + (fixedIcon != null ? fixedIcon.hashCode() : 0);
		return result;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeString(this.otherCategoryName);
		dest.writeString(this.categoryDisplay);
		dest.writeValue(this.parentCategoryId);
		dest.writeValue(this.isPublic);
		dest.writeValue(this.isFixedExpenses);
		dest.writeInt(this.categoryType == null ? -1 : this.categoryType.ordinal());
		dest.writeString(this.categoryRank);
		dest.writeValue(this.budgetGenerationType);
		if (children != null) {
			MenigaCategory self = null;
			for (MenigaCategory cat : children) {
				if (cat.getId() == id) {
					self = cat;
					break;
				}
			}
			if (self != null) {
				children.remove(self);
			}
		}
		dest.writeTypedList(children);
		dest.writeValue(this.categoryContextId);
		dest.writeValue(this.orderId);
		dest.writeString(this.displayData);
		dest.writeString(this.fixedIcon);
	}

    /*
     * API Calls
     */

	/**
	 * Retrieves all categories including user created categories in a specified culture (language).
	 * Some of the entries in the list might be of type MenigaUserCategory
	 *
	 * @return A list of all categories
	 */
	public static Result<List<MenigaCategory>> fetch() {
		return MenigaCategory.apiOperator.getCategories(false, MenigaSDK.getMenigaSettings().getCulture());
	}

	/**
	 * Retrieves all categories including user created categories in a tree structure with parent categories
	 * containing their children in this.children. Some of the entries in the list might be
	 * of type MenigaUserCategory
	 *
	 * @param type Get only public categories or both public and user created at the same time
	 * @return A list of root categories
	 */
	public static Result<List<MenigaCategory>> fetchTree(final CategoryRequest type) {
		Boolean publicOnly = false;
		if (type == CategoryRequest.ONLY_SYSTEM_CATEGORIES) {
			publicOnly = true;
		}
		Result<List<MenigaCategory>> task = MenigaCategory.apiOperator.getCategoriesTree(publicOnly, MenigaSDK.getMenigaSettings().getCulture());
		task.getTask().continueWith(new Continuation<List<MenigaCategory>, Object>() {
			@Override
			public Object then(Task<List<MenigaCategory>> task) throws Exception {
				if (!task.isFaulted() && task.getResult() != null) {
					List<MenigaCategory> roots = new ArrayList<>();
					for (MenigaCategory cat : task.getResult()) {
						if (cat.parentCategoryId == null) {
							roots.add(cat);
						}
					}
					for (MenigaCategory root : roots) {
						root.getChildren().clear();
						Map<Long, Boolean> duplicateCheck = new HashMap<>();
						for (MenigaCategory child : task.getResult()) {
							if (child.parentCategoryId != null && child.parentCategoryId == root.id) {
								if (duplicateCheck.containsKey(child.getId())) {
									continue;
								}
								duplicateCheck.put(child.getId(), true);

								root.getChildren().add(child);
								child.parent = root;
							}
						}
						Collections.sort(root.getChildren(), new Comparator<MenigaCategory>() {
							@Override
							public int compare(MenigaCategory lhs, MenigaCategory rhs) {
								return lhs.getName().compareTo(rhs.getName());
							}
						});
					}

					task.getResult().clear();
					for (MenigaCategory root : roots) {
						task.getResult().add(root);
					}
				}
				return task.getResult();
			}
		});
		return task;
	}

	/**
	 * Retrieves all public categories and optionally user created categories. Some of the entries in the list might be
	 * of type MenigaUserCategory
	 *
	 * @param type Get only public categories or both public and user created at the same time
	 * @return All categorues of the sort specified by type
	 */
	public static Result<List<MenigaCategory>> fetch(CategoryRequest type) {
		boolean publicOnly = true;
		if (type == CategoryRequest.ALL) {
			publicOnly = false;
		}
		return MenigaCategory.apiOperator.getCategories(publicOnly, MenigaSDK.getMenigaSettings().getCulture());
	}

	/**
	 * Returns a category by id. If this is a user created category, the actual return type will be MenigaUserCategory
	 *
	 * @param id id of the category to fetch
	 * @return The category object
	 */
	public static Result<MenigaCategory> fetch(long id) {
		return MenigaCategory.apiOperator.getCategoryById(id, MenigaSDK.getMenigaSettings().getCulture());
	}
}
