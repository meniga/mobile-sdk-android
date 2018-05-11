package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.meniga.sdk.ErrorHandler;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.interfaces.ValueHashable;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.categories.MenigaCategory;
import com.meniga.sdk.models.categories.enums.CategoryType;
import com.meniga.sdk.models.transactions.enums.SeriesOrderBy;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A filter that can be passed to a Meniga endpoint and returns given transactions based on that filter.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 *
*/
public class TransactionsFilter implements Serializable, Parcelable, Cloneable, ValueHashable {
	public static final Parcelable.Creator<TransactionsFilter> CREATOR = new Parcelable.Creator<TransactionsFilter>() {
		@Override
		public TransactionsFilter createFromParcel(Parcel source) {
			return new TransactionsFilter(source);
		}

		@Override
		public TransactionsFilter[] newArray(int size) {
			return new TransactionsFilter[size];
		}
	};

	protected final String type;
	protected final String orderBy;
	protected final transient Integer skip;
	protected final transient Integer take;
	protected final MenigaDecimal amountTo;
	protected final MenigaDecimal amountFrom;
	protected final List<Long> categoryIds;
	protected final List<Long> accountIds;
	protected final List<Long> accountTypeIds;
	protected final List<String> accountIdentifiers;
	protected final List<Long> merchantIds;
	protected final List<String> merchantTexts;
	protected final String searchText;
	protected final List<String> tags;
	protected final DateTime periodTo;
	protected final DateTime periodFrom;
	protected final List<Long> ids;
	protected final Boolean onlyUnread;
	protected final Boolean onlyUncertain;
	protected final Boolean onlyFlagged;
	protected final Boolean useExactMerchantTexts;
	protected final Boolean uncertainOrFlagged;
	protected final Boolean useAbsoluteAmountSearch;
	protected final Boolean useAccentInsensitiveSearch;
	protected final Boolean useAmountInCurrencySearch;
	protected final Boolean useAndSearchForTags;
	protected final Boolean useEqualsSearchForBankId;
	protected final Boolean useExactDescription;
	protected final Boolean useParentMerchantIds;
	protected final Boolean onlyUncategorized;
	protected final Boolean ascendingOrder;
	protected final Boolean hideExcluded;
	protected final List<String> bankIds;
	protected final List<CategoryType> categoryTypes;
	protected final String comment;
	protected final List<String> counterpartyAccountIdentifiers;
	protected final String description;
	protected final List<Integer> excludeMerchantIds;
	protected final List<String> excludeMerchantTexts;
	protected final List<String> fields;
	protected final DateTime insertedBefore;
	protected final DateTime originalPeriodFrom;
	protected final DateTime originalPeriodTo;
	protected final String parsedData;
	protected final List<String> parsedDataExactKeys;
	protected final String parsedDataNameToOrderBy;

	protected transient boolean includeAccounts = true;
	protected transient boolean includeMerchants = true;

	protected final transient boolean isFiltering;

	protected TransactionsFilter(Parcel in) {
		type = in.readString();
		orderBy = in.readString();
		take = (Integer) in.readValue(Integer.class.getClassLoader());
		skip = (Integer) in.readValue(Integer.class.getClassLoader());
		amountTo = (MenigaDecimal) in.readSerializable();
		amountFrom = (MenigaDecimal) in.readSerializable();
		categoryIds = new ArrayList<>();
		in.readList(categoryIds, Long.class.getClassLoader());
		accountIds = new ArrayList<>();
		in.readList(accountIds, Long.class.getClassLoader());
		accountTypeIds = new ArrayList<>();
		in.readList(accountTypeIds, Long.class.getClassLoader());
		accountIdentifiers = in.createStringArrayList();
		merchantIds = new ArrayList<>();
		in.readList(merchantIds, Long.class.getClassLoader());
		merchantTexts = in.createStringArrayList();
		searchText = in.readString();
		tags = in.createStringArrayList();
		periodTo = (DateTime) in.readSerializable();
		periodFrom = (DateTime) in.readSerializable();
		ids = new ArrayList<>();
		in.readList(ids, Long.class.getClassLoader());
		onlyUnread = (Boolean) in.readValue(Boolean.class.getClassLoader());
		onlyUncertain = (Boolean) in.readValue(Boolean.class.getClassLoader());
		onlyFlagged = (Boolean) in.readValue(Boolean.class.getClassLoader());
		useExactMerchantTexts = (Boolean) in.readValue(Boolean.class.getClassLoader());
		uncertainOrFlagged = (Boolean) in.readValue(Boolean.class.getClassLoader());
		useAbsoluteAmountSearch = (Boolean) in.readValue(Boolean.class.getClassLoader());
		useAccentInsensitiveSearch = (Boolean) in.readValue(Boolean.class.getClassLoader());
		useAmountInCurrencySearch = (Boolean) in.readValue(Boolean.class.getClassLoader());
		useAndSearchForTags = (Boolean) in.readValue(Boolean.class.getClassLoader());
		useEqualsSearchForBankId = (Boolean) in.readValue(Boolean.class.getClassLoader());
		useExactDescription = (Boolean) in.readValue(Boolean.class.getClassLoader());
		useParentMerchantIds = (Boolean) in.readValue(Boolean.class.getClassLoader());
		onlyUncategorized = (Boolean) in.readValue(Boolean.class.getClassLoader());
		ascendingOrder = (Boolean) in.readValue(Boolean.class.getClassLoader());
		hideExcluded = (Boolean) in.readValue(Boolean.class.getClassLoader());
		bankIds = in.createStringArrayList();
		categoryTypes = new ArrayList<>();
		in.readList(categoryTypes, CategoryType.class.getClassLoader());
		comment = in.readString();
		counterpartyAccountIdentifiers = in.createStringArrayList();
		description = in.readString();
		excludeMerchantIds = new ArrayList<>();
		in.readList(excludeMerchantIds, Integer.class.getClassLoader());
		excludeMerchantTexts = in.createStringArrayList();
		fields = in.createStringArrayList();
		insertedBefore = (DateTime) in.readSerializable();
		originalPeriodFrom = (DateTime) in.readSerializable();
		originalPeriodTo = (DateTime) in.readSerializable();
		parsedData = in.readString();
		parsedDataExactKeys = in.createStringArrayList();
		parsedDataNameToOrderBy = in.readString();
		includeAccounts = (in.readInt() == 1);
		includeMerchants = (in.readInt() == 1);

		isFiltering = in.readInt() == 1;
	}

	private TransactionsFilter(TransactionsFilter.Builder builder) {
		type = builder.type;
		orderBy = builder.orderBy;
		take = builder.take;
		skip = builder.skip;
		searchText = builder.searchText;
		amountFrom = builder.amountFrom;
		amountTo = builder.amountTo;
		categoryIds = builder.categoryIds;
		accountIds = builder.accountIds;
		accountTypeIds = builder.accountTypeIds;
		merchantIds = builder.merchantIds;
		merchantTexts = builder.merchantTexts;
		tags = builder.tags;
		periodFrom = builder.periodFrom;
		periodTo = builder.periodTo;
		ids = builder.ids;
		onlyUnread = builder.onlyUnread;
		onlyUncertain = builder.onlyUncertain;
		onlyFlagged = builder.onlyFlagged;
		useExactMerchantTexts = builder.useExactMerchantTexts;
		ascendingOrder = builder.ascendingOrder;
		accountIdentifiers = builder.accountIdentifiers;
		bankIds = builder.bankIds;
		categoryTypes = builder.categoryTypes;
		comment = builder.comment;
		counterpartyAccountIdentifiers = builder.counterpartyAccountIdentifiers;
		description = builder.description;
		excludeMerchantIds = builder.excludeMerchantIds;
		excludeMerchantTexts = builder.excludeMerchantTexts;
		fields = builder.fields;
		hideExcluded = builder.hideExcluded;
		uncertainOrFlagged = builder.uncertainOrFlagged;
		onlyUncategorized = builder.onlyUncategorized;
		useAbsoluteAmountSearch = builder.useAbsoluteAmountSearch;
		useAccentInsensitiveSearch = builder.useAccentInsensitiveSearch;
		useAmountInCurrencySearch = builder.useAmountInCurrencySearch;
		useAndSearchForTags = builder.useAndSearchForTags;
		useEqualsSearchForBankId = builder.useEqualsSearchForBankId;
		useExactDescription = builder.useExactDescription;
		useParentMerchantIds = builder.useParentMerchantIds;
		insertedBefore = builder.insertedBefore;
		originalPeriodFrom = builder.originalPeriodFrom;
		originalPeriodTo = builder.originalPeriodTo;
		parsedData = builder.parsedData;
		parsedDataExactKeys = builder.parsedDataExactKeys;
		parsedDataNameToOrderBy = builder.parsedDataNameToOrderBy;
		includeAccounts = builder.includeAccounts;
		includeMerchants = builder.includeMerchants;

		isFiltering = builder.isFiltering;
	}

	/**
	 * @return The number of items to return per page. Pagination will be used if the number of
	 * transactions returned from the query exceed the number of items per page.
	 */
	public Integer getNumItemsPerPage() {
		return take;
	}

	/**
	 * @return The current page to take out of the paginated result.
	 */
	public Integer getPage() {
		return skip;
	}

	@Override
	protected TransactionsFilter clone() throws CloneNotSupportedException {
		return (TransactionsFilter) super.clone();
	}

	public Map<String, String> toQueryMap() {
		List<Field> mappableFields = new ArrayList<>();
		Field[] allFields = TransactionsFilter.class.getDeclaredFields();
		for (Field field : allFields) {
			if (Modifier.isPrivate(field.getModifiers()) || Modifier.isProtected(field.getModifiers())) {
				mappableFields.add(field);
			}
		}

		Map<String, String> map = new HashMap<>();
		Field skipField = null;
		Field takeField = null;
		for (Field member : mappableFields) {
			if (member.getName().equals("isFiltering") || member.getName().equals("includeMerchants")) {
				continue;
			}
			if (member.getName().equals("includeAccounts") && (includeMerchants || includeAccounts)) {
				map.put("include", constructInclude());
				continue;
			}
			if (member.getName().equals("skip")) {
				skipField = member;
				continue;
			} else if (member.getName().equals("take")) {
				takeField = member;
				continue;
			}
			addToMap(member, map);
		}
		if (skipField != null) {
			addToMap(skipField, map);
		}
		if (takeField != null) {
			addToMap(takeField, map);
		}
		return map;
	}

	private String constructInclude() {
		String include = "";
		if (includeAccounts) {
			include += "Account";
		}
		if (includeMerchants) {
			if (include.length() > 0) {
				include += ",";
			}
			include += "Merchant";
		}
		return include;
	}

	private void addToMap(Field member, Map<String, String> map ) {
		try {
			Object value = member.get(this);
			if (value != null && !member.getName().startsWith("$")) {
				if (value instanceof ArrayList && ((ArrayList) value).size() == 0) {
					return;
				}
				map.put(member.getName(), fieldTypeToString(value));
			}
		} catch (IllegalAccessException ex) {
			ErrorHandler.reportAndHandle(ex);
		}
	}

	private String fieldTypeToString(Object fieldValue) {
		if (fieldValue instanceof MenigaDecimal) {
			return Double.toString(((MenigaDecimal) fieldValue).doubleValue());
		} else if (fieldValue instanceof List) {
			StringBuilder bld = new StringBuilder();
			for (int i = 0; i < ((List) fieldValue).size(); i++) {
				if (i > 0) {
					bld.append(",");
				}
				Object item = ((List) fieldValue).get(i);
				bld.append(item);
			}
			return bld.toString();
		} else if (fieldValue instanceof DateTime) {
			DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
			return fmt.print((DateTime) fieldValue);
		} else if (fieldValue instanceof Boolean) {
			return Boolean.toString((Boolean) fieldValue);
		}

		return fieldValue.toString();
	}

	@Override
	public long getValueHash() {
		Gson gson = new Gson();
		String json = gson.toJson(this);

		return json.hashCode();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(type);
		dest.writeString(orderBy);
		dest.writeValue(take);
		dest.writeValue(skip);
		dest.writeSerializable(amountTo);
		dest.writeSerializable(amountFrom);
		dest.writeList(categoryIds);
		dest.writeList(accountIds);
		dest.writeList(accountTypeIds);
		dest.writeStringList(accountIdentifiers);
		dest.writeList(merchantIds);
		dest.writeStringList(merchantTexts);
		dest.writeString(searchText);
		dest.writeStringList(tags);
		dest.writeSerializable(periodTo);
		dest.writeSerializable(periodFrom);
		dest.writeList(ids);
		dest.writeValue(onlyUnread);
		dest.writeValue(onlyUncertain);
		dest.writeValue(onlyFlagged);
		dest.writeValue(useExactMerchantTexts);
		dest.writeValue(uncertainOrFlagged);
		dest.writeValue(useAbsoluteAmountSearch);
		dest.writeValue(useAccentInsensitiveSearch);
		dest.writeValue(useAmountInCurrencySearch);
		dest.writeValue(useAndSearchForTags);
		dest.writeValue(useEqualsSearchForBankId);
		dest.writeValue(useExactDescription);
		dest.writeValue(useParentMerchantIds);
		dest.writeValue(onlyUncategorized);
		dest.writeValue(ascendingOrder);
		dest.writeValue(hideExcluded);
		dest.writeStringList(bankIds);
		dest.writeList(categoryTypes);
		dest.writeString(comment);
		dest.writeStringList(counterpartyAccountIdentifiers);
		dest.writeString(description);
		dest.writeList(excludeMerchantIds);
		dest.writeStringList(excludeMerchantTexts);
		dest.writeStringList(fields);
		dest.writeSerializable(insertedBefore);
		dest.writeSerializable(originalPeriodFrom);
		dest.writeSerializable(originalPeriodTo);
		dest.writeString(parsedData);
		dest.writeStringList(parsedDataExactKeys);
		dest.writeString(parsedDataNameToOrderBy);
		dest.writeInt(includeAccounts ? 1 : 0);
		dest.writeInt(includeMerchants ? 1 : 0);

		dest.writeInt(isFiltering ? 1 : 0);
	}

	public String getType() {
		return type;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Integer getSkip() {
		return skip;
	}

	public Integer getTake() {
		return take;
	}

	public MenigaDecimal getAmountTo() {
		return amountTo;
	}

	public MenigaDecimal getAmountFrom() {
		return amountFrom;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public List<Long> getAccountIds() {
		return accountIds;
	}

	public List<Long> getAccountTypeIds() {
		return accountTypeIds;
	}

	public List<String> getAccountIdentifiers() {
		return accountIdentifiers;
	}

	public List<Long> getMerchantIds() {
		return merchantIds;
	}

	public List<String> getMerchantTexts() {
		return merchantTexts;
	}

	public String getSearchText() {
		return searchText;
	}

	public List<String> getTags() {
		return tags;
	}

	public DateTime getPeriodTo() {
		return periodTo;
	}

	public DateTime getPeriodFrom() {
		return periodFrom;
	}

	public List<Long> getIds() {
		return ids;
	}

	public Boolean getOnlyUnread() {
		return onlyUnread;
	}

	public Boolean getOnlyUncertain() {
		return onlyUncertain;
	}

	public Boolean getOnlyFlagged() {
		return onlyFlagged;
	}

	public Boolean getUseExactMerchantTexts() {
		return useExactMerchantTexts;
	}

	public Boolean getUncertainOrFlagged() {
		return uncertainOrFlagged;
	}

	public Boolean getUseAbsoluteAmountSearch() {
		return useAbsoluteAmountSearch;
	}

	public Boolean getUseAccentInsensitiveSearch() {
		return useAccentInsensitiveSearch;
	}

	public Boolean getUseAmountInCurrencySearch() {
		return useAmountInCurrencySearch;
	}

	public Boolean getUseAndSearchForTags() {
		return useAndSearchForTags;
	}

	public Boolean getUseEqualsSearchForBankId() {
		return useEqualsSearchForBankId;
	}

	public Boolean getUseExactDescription() {
		return useExactDescription;
	}

	public Boolean getUseParentMerchantIds() {
		return useParentMerchantIds;
	}

	public Boolean getOnlyUncategorized() {
		return onlyUncategorized;
	}

	public Boolean getAscendingOrder() {
		return ascendingOrder;
	}

	public Boolean getHideExcluded() {
		return hideExcluded;
	}

	public List<String> getBankIds() {
		return bankIds;
	}

	public List<CategoryType> getCategoryTypes() {
		return categoryTypes;
	}

	public String getComment() {
		return comment;
	}

	public List<String> getCounterpartyAccountIdentifiers() {
		return counterpartyAccountIdentifiers;
	}

	public String getDescription() {
		return description;
	}

	public List<Integer> getExcludeMerchantIds() {
		return excludeMerchantIds;
	}

	public List<String> getExcludeMerchantTexts() {
		return excludeMerchantTexts;
	}

	public List<String> getFields() {
		return fields;
	}

	public DateTime getInsertedBefore() {
		return insertedBefore;
	}

	public DateTime getOriginalPeriodFrom() {
		return originalPeriodFrom;
	}

	public DateTime getOriginalPeriodTo() {
		return originalPeriodTo;
	}

	public String getParsedData() {
		return parsedData;
	}

	public List<String> getParsedDataExactKeys() {
		return parsedDataExactKeys;
	}

	public String getParsedDataNameToOrderBy() {
		return parsedDataNameToOrderBy;
	}

	public boolean getIncludeAccounts() {
		return includeAccounts;
	}

	public boolean getIncludeMerchants() {
		return includeMerchants;
	}

	public boolean getIsFiltering() {
		return isFiltering;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TransactionsFilter that = (TransactionsFilter) o;

		if (isFiltering != that.isFiltering) return false;
		if (type != null ? !type.equals(that.type) : that.type != null) return false;
		if (orderBy != null ? !orderBy.equals(that.orderBy) : that.orderBy != null) return false;
		if (skip != null ? !skip.equals(that.skip) : that.skip != null) return false;
		if (take != null ? !take.equals(that.take) : that.take != null) return false;
		if (amountTo != null ? !amountTo.equals(that.amountTo) : that.amountTo != null)
			return false;
		if (amountFrom != null ? !amountFrom.equals(that.amountFrom) : that.amountFrom != null)
			return false;
		if (categoryIds != null ? !categoryIds.equals(that.categoryIds) : that.categoryIds != null)
			return false;
		if (accountIds != null ? !accountIds.equals(that.accountIds) : that.accountIds != null)
			return false;
		if (accountTypeIds != null ? !accountTypeIds.equals(that.accountTypeIds) : that.accountTypeIds != null)
			return false;
		if (accountIdentifiers != null ? !accountIdentifiers.equals(that.accountIdentifiers) : that.accountIdentifiers != null)
			return false;
		if (merchantIds != null ? !merchantIds.equals(that.merchantIds) : that.merchantIds != null)
			return false;
		if (merchantTexts != null ? !merchantTexts.equals(that.merchantTexts) : that.merchantTexts != null)
			return false;
		if (searchText != null ? !searchText.equals(that.searchText) : that.searchText != null)
			return false;
		if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
		if (periodTo != null ? !periodTo.equals(that.periodTo) : that.periodTo != null)
			return false;
		if (periodFrom != null ? !periodFrom.equals(that.periodFrom) : that.periodFrom != null)
			return false;
		if (ids != null ? !ids.equals(that.ids) : that.ids != null) return false;
		if (onlyUnread != null ? !onlyUnread.equals(that.onlyUnread) : that.onlyUnread != null)
			return false;
		if (onlyUncertain != null ? !onlyUncertain.equals(that.onlyUncertain) : that.onlyUncertain != null)
			return false;
		if (onlyFlagged != null ? !onlyFlagged.equals(that.onlyFlagged) : that.onlyFlagged != null)
			return false;
		if (useExactMerchantTexts != null ? !useExactMerchantTexts.equals(that.useExactMerchantTexts) : that.useExactMerchantTexts != null)
			return false;
		if (uncertainOrFlagged != null ? !uncertainOrFlagged.equals(that.uncertainOrFlagged) : that.uncertainOrFlagged != null)
			return false;
		if (useAbsoluteAmountSearch != null ? !useAbsoluteAmountSearch.equals(that.useAbsoluteAmountSearch) : that.useAbsoluteAmountSearch != null)
			return false;
		if (useAccentInsensitiveSearch != null ? !useAccentInsensitiveSearch.equals(that.useAccentInsensitiveSearch) : that.useAccentInsensitiveSearch != null)
			return false;
		if (useAmountInCurrencySearch != null ? !useAmountInCurrencySearch.equals(that.useAmountInCurrencySearch) : that.useAmountInCurrencySearch != null)
			return false;
		if (useAndSearchForTags != null ? !useAndSearchForTags.equals(that.useAndSearchForTags) : that.useAndSearchForTags != null)
			return false;
		if (useEqualsSearchForBankId != null ? !useEqualsSearchForBankId.equals(that.useEqualsSearchForBankId) : that.useEqualsSearchForBankId != null)
			return false;
		if (useExactDescription != null ? !useExactDescription.equals(that.useExactDescription) : that.useExactDescription != null)
			return false;
		if (useParentMerchantIds != null ? !useParentMerchantIds.equals(that.useParentMerchantIds) : that.useParentMerchantIds != null)
			return false;
		if (onlyUncategorized != null ? !onlyUncategorized.equals(that.onlyUncategorized) : that.onlyUncategorized != null)
			return false;
		if (ascendingOrder != null ? !ascendingOrder.equals(that.ascendingOrder) : that.ascendingOrder != null)
			return false;
		if (hideExcluded != null ? !hideExcluded.equals(that.hideExcluded) : that.hideExcluded != null)
			return false;
		if (bankIds != null ? !bankIds.equals(that.bankIds) : that.bankIds != null) return false;
		if (categoryTypes != null ? !categoryTypes.equals(that.categoryTypes) : that.categoryTypes != null)
			return false;
		if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
		if (counterpartyAccountIdentifiers != null ? !counterpartyAccountIdentifiers.equals(that.counterpartyAccountIdentifiers) : that.counterpartyAccountIdentifiers != null)
			return false;
		if (description != null ? !description.equals(that.description) : that.description != null)
			return false;
		if (excludeMerchantIds != null ? !excludeMerchantIds.equals(that.excludeMerchantIds) : that.excludeMerchantIds != null)
			return false;
		if (excludeMerchantTexts != null ? !excludeMerchantTexts.equals(that.excludeMerchantTexts) : that.excludeMerchantTexts != null)
			return false;
		if (fields != null ? !fields.equals(that.fields) : that.fields != null) return false;
		if (insertedBefore != null ? !insertedBefore.equals(that.insertedBefore) : that.insertedBefore != null)
			return false;
		if (originalPeriodFrom != null ? !originalPeriodFrom.equals(that.originalPeriodFrom) : that.originalPeriodFrom != null)
			return false;
		if (originalPeriodTo != null ? !originalPeriodTo.equals(that.originalPeriodTo) : that.originalPeriodTo != null)
			return false;
		if (parsedData != null ? !parsedData.equals(that.parsedData) : that.parsedData != null)
			return false;
		if (parsedDataExactKeys != null ? !parsedDataExactKeys.equals(that.parsedDataExactKeys) : that.parsedDataExactKeys != null)
			return false;
		if (parsedDataNameToOrderBy != null ? !parsedDataNameToOrderBy.equals(that.parsedDataNameToOrderBy) : that.parsedDataNameToOrderBy != null)
			return false;
		if (includeAccounts != that.includeAccounts)
			return false;
		if (includeMerchants != that.includeMerchants)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = type != null ? type.hashCode() : 0;
		result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
		result = 31 * result + (skip != null ? skip.hashCode() : 0);
		result = 31 * result + (take != null ? take.hashCode() : 0);
		result = 31 * result + (amountTo != null ? amountTo.hashCode() : 0);
		result = 31 * result + (amountFrom != null ? amountFrom.hashCode() : 0);
		result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
		result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
		result = 31 * result + (accountTypeIds != null ? accountTypeIds.hashCode() : 0);
		result = 31 * result + (accountIdentifiers != null ? accountIdentifiers.hashCode() : 0);
		result = 31 * result + (merchantIds != null ? merchantIds.hashCode() : 0);
		result = 31 * result + (merchantTexts != null ? merchantTexts.hashCode() : 0);
		result = 31 * result + (searchText != null ? searchText.hashCode() : 0);
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		DateTime tmpPeriodTo = periodTo != null ? new DateTime(periodTo.getYear(), periodTo.getMonthOfYear(), periodTo.getDayOfMonth(), 0, 0) : null;
		result = 31 * result + (tmpPeriodTo != null ? tmpPeriodTo.hashCode() : 0);
		DateTime tmpPeriodFrom = periodFrom != null ? new DateTime(periodFrom.getYear(), periodFrom.getMonthOfYear(), periodFrom.getDayOfMonth(), 0, 0) : null;
		result = 31 * result + (tmpPeriodFrom != null ? tmpPeriodFrom.hashCode() : 0);
		result = 31 * result + (ids != null ? ids.hashCode() : 0);
		result = 31 * result + (onlyUnread != null ? onlyUnread.hashCode() : 0);
		result = 31 * result + (onlyUncertain != null ? onlyUncertain.hashCode() : 0);
		result = 31 * result + (onlyFlagged != null ? onlyFlagged.hashCode() : 0);
		result = 31 * result + (useExactMerchantTexts != null ? useExactMerchantTexts.hashCode() : 0);
		result = 31 * result + (uncertainOrFlagged != null ? uncertainOrFlagged.hashCode() : 0);
		result = 31 * result + (useAbsoluteAmountSearch != null ? useAbsoluteAmountSearch.hashCode() : 0);
		result = 31 * result + (useAccentInsensitiveSearch != null ? useAccentInsensitiveSearch.hashCode() : 0);
		result = 31 * result + (useAmountInCurrencySearch != null ? useAmountInCurrencySearch.hashCode() : 0);
		result = 31 * result + (useAndSearchForTags != null ? useAndSearchForTags.hashCode() : 0);
		result = 31 * result + (useEqualsSearchForBankId != null ? useEqualsSearchForBankId.hashCode() : 0);
		result = 31 * result + (useExactDescription != null ? useExactDescription.hashCode() : 0);
		result = 31 * result + (useParentMerchantIds != null ? useParentMerchantIds.hashCode() : 0);
		result = 31 * result + (onlyUncategorized != null ? onlyUncategorized.hashCode() : 0);
		result = 31 * result + (ascendingOrder != null ? ascendingOrder.hashCode() : 0);
		result = 31 * result + (hideExcluded != null ? hideExcluded.hashCode() : 0);
		result = 31 * result + (bankIds != null ? bankIds.hashCode() : 0);
		result = 31 * result + (categoryTypes != null ? categoryTypes.hashCode() : 0);
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		result = 31 * result + (counterpartyAccountIdentifiers != null ? counterpartyAccountIdentifiers.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (excludeMerchantIds != null ? excludeMerchantIds.hashCode() : 0);
		result = 31 * result + (excludeMerchantTexts != null ? excludeMerchantTexts.hashCode() : 0);
		result = 31 * result + (fields != null ? fields.hashCode() : 0);
		result = 31 * result + (insertedBefore != null ? insertedBefore.hashCode() : 0);
		DateTime tmpOriginalPeriodFrom = originalPeriodFrom != null ? new DateTime(originalPeriodFrom.getYear(), originalPeriodFrom.getMonthOfYear(), originalPeriodFrom.getDayOfMonth(), 0, 0) : null;
		result = 31 * result + (tmpOriginalPeriodFrom != null ? tmpOriginalPeriodFrom.hashCode() : 0);
		DateTime tmpOriginalPeriodTo = originalPeriodTo != null ? new DateTime(originalPeriodTo.getYear(), originalPeriodTo.getMonthOfYear(), originalPeriodTo.getDayOfMonth(), 0, 0) : null;
		result = 31 * result + (tmpOriginalPeriodTo != null ? tmpOriginalPeriodTo.hashCode() : 0);
		result = 31 * result + (parsedData != null ? parsedData.hashCode() : 0);
		result = 31 * result + (parsedDataExactKeys != null ? parsedDataExactKeys.hashCode() : 0);
		result = 31 * result + (parsedDataNameToOrderBy != null ? parsedDataNameToOrderBy.hashCode() : 0);
		result = 31 * result + (includeAccounts ? 1 : 0);
		result = 31 * result + (includeMerchants ? 1 : 0);

		result = 31 * result + (isFiltering ? 1 : 0);
		return result;
	}

	/**
	 * A builder class for constructing a filter object. Follows the builder pattern.
	 * <p>
	 * Copyright 2017 Meniga Iceland Inc.
	 */
	public static class Builder {
		private String type;
		private String orderBy;
		private Integer take = 50;
		private Integer skip = 0;
		private MenigaDecimal amountTo;
		private MenigaDecimal amountFrom;
		private List<Long> categoryIds;
		private List<Long> accountIds;
		private List<Long> accountTypeIds;
		private List<String> accountIdentifiers;
		private List<Long> merchantIds;
		private List<String> merchantTexts;
		private String searchText;
		private List<String> tags;
		private DateTime periodTo;
		private DateTime periodFrom;
		private List<Long> ids;
		private Boolean onlyUnread;
		private Boolean onlyUncertain;
		private Boolean onlyFlagged;
		private Boolean useExactMerchantTexts;
		private Boolean uncertainOrFlagged;
		private Boolean useAbsoluteAmountSearch;
		private Boolean useAccentInsensitiveSearch;
		private Boolean useAmountInCurrencySearch;
		private Boolean useAndSearchForTags;
		private Boolean useEqualsSearchForBankId;
		private Boolean useExactDescription;
		private Boolean useParentMerchantIds;
		private Boolean onlyUncategorized;
		private Boolean ascendingOrder;
		private Boolean hideExcluded;
		private List<String> bankIds;
		private List<CategoryType> categoryTypes;
		private String comment;
		private List<String> counterpartyAccountIdentifiers;
		private String description;
		private List<Integer> excludeMerchantIds;
		private List<String> excludeMerchantTexts;
		private List<String> fields;
		private DateTime insertedBefore;
		private DateTime originalPeriodFrom;
		private DateTime originalPeriodTo;
		private String parsedData;
		private List<String> parsedDataExactKeys;
		private String parsedDataNameToOrderBy;
		private boolean includeAccounts = true;
		private boolean includeMerchants = true;

		private boolean isFiltering;

		public Builder() {
		}

		public Builder(TransactionsFilter filter) {
			consumeExistingFilter(filter);
		}

		/**
		 * Takes in an TransactionFilter and copies its properties to a new builder
		 *
		 * @param filterToCopy existing transactionFilter
		 * @return Builder with set properties of existing transaction filter.
		 */
		public static Builder createBuilderFromExistingFilter(TransactionsFilter filterToCopy) {
			return new Builder(filterToCopy);
		}

		private void consumeExistingFilter(TransactionsFilter filter) {
			if (filter != null) {
				type = filter.type;
				orderBy = filter.orderBy;
				take = filter.take;
				skip = filter.skip;
				searchText = preserveNonNull(filter.searchText, searchText);
				amountFrom = preserveNonNull(filter.amountFrom, amountFrom);
				amountTo = preserveNonNull(filter.amountTo, amountTo);
				categoryIds = preserveNonNull(filter.categoryIds, categoryIds);
				accountIds = preserveNonNull(filter.accountIds, accountIds);
				accountTypeIds = preserveNonNull(filter.accountTypeIds, accountTypeIds);
				merchantIds = preserveNonNull(filter.merchantIds, merchantIds);
				merchantTexts = preserveNonNull(filter.merchantTexts, merchantTexts);
				tags = preserveNonNull(filter.tags, tags);
				periodFrom = preserveNonNull(filter.periodFrom, periodFrom);
				periodTo = preserveNonNull(filter.periodTo, periodTo);
				ids = preserveNonNull(filter.ids, ids);
				onlyUnread = preserveNonNull(filter.onlyUnread, onlyUnread);
				onlyUncertain = preserveNonNull(filter.onlyUncertain, onlyUncertain);
				onlyFlagged = preserveNonNull(filter.onlyFlagged, onlyFlagged);
				useExactMerchantTexts = preserveNonNull(filter.useExactMerchantTexts, useExactMerchantTexts);
				ascendingOrder = preserveNonNull(filter.ascendingOrder, ascendingOrder);
				accountIdentifiers = preserveNonNull(filter.accountIdentifiers, accountIdentifiers);
				bankIds = preserveNonNull(filter.bankIds, bankIds);
				categoryTypes = preserveNonNull(filter.categoryTypes, categoryTypes);
				comment = preserveNonNull(filter.comment, comment);
				counterpartyAccountIdentifiers = preserveNonNull(filter.counterpartyAccountIdentifiers, counterpartyAccountIdentifiers);
				description = preserveNonNull(filter.description, description);
				excludeMerchantIds = preserveNonNull(filter.excludeMerchantIds, excludeMerchantIds);
				excludeMerchantTexts = preserveNonNull(filter.excludeMerchantTexts, excludeMerchantTexts);
				fields = preserveNonNull(filter.fields, fields);
				hideExcluded = preserveNonNull(filter.hideExcluded, hideExcluded);
				uncertainOrFlagged = preserveNonNull(filter.uncertainOrFlagged, uncertainOrFlagged);
				onlyUncategorized = preserveNonNull(filter.onlyUncategorized, onlyUncategorized);
				useAbsoluteAmountSearch = preserveNonNull(filter.useAbsoluteAmountSearch, useAbsoluteAmountSearch);
				useAccentInsensitiveSearch = preserveNonNull(filter.useAccentInsensitiveSearch, useAccentInsensitiveSearch);
				useAmountInCurrencySearch = preserveNonNull(filter.useAmountInCurrencySearch, useAmountInCurrencySearch);
				useAndSearchForTags = preserveNonNull(filter.useAndSearchForTags, useAndSearchForTags);
				useEqualsSearchForBankId = preserveNonNull(filter.useEqualsSearchForBankId, useEqualsSearchForBankId);
				useExactDescription = preserveNonNull(filter.useExactDescription, useExactDescription);
				useParentMerchantIds = preserveNonNull(filter.useParentMerchantIds, useParentMerchantIds);
				insertedBefore = preserveNonNull(filter.insertedBefore, insertedBefore);
				originalPeriodFrom = preserveNonNull(filter.originalPeriodFrom, originalPeriodFrom);
				originalPeriodTo = preserveNonNull(filter.originalPeriodTo, originalPeriodTo);
				parsedData = preserveNonNull(filter.parsedData, parsedData);
				parsedDataExactKeys = preserveNonNull(filter.parsedDataExactKeys, parsedDataExactKeys);
				parsedDataNameToOrderBy = preserveNonNull(filter.parsedDataNameToOrderBy, parsedDataNameToOrderBy);
				includeAccounts = preserveNonNull(filter.includeAccounts, includeAccounts);
				includeMerchants = preserveNonNull(filter.includeMerchants, includeMerchants);

				isFiltering = preserveNonNull(filter.isFiltering, isFiltering);
			}
		}

		private <T> T preserveNonNull(T otherField, T myField) {
			return (otherField == null && myField != null) ? myField : otherField;
		}

		public Builder type(String type) {
			this.type = type;
			isFiltering = true;
			return this;
		}

		public Builder orderBy(SeriesOrderBy order) {
			this.orderBy = order.toString();
			isFiltering = true;
			return this;
		}

		public Builder ascendingOrder(boolean ascendingOrder) {
			this.ascendingOrder = ascendingOrder;
			isFiltering = true;
			return this;
		}

		/**
		 * How many results should be fetched in a page. Default value is 50
		 *
		 * @param numPerPage The number of items in the result
		 * @param pageIndex  The index of the page to retrieve
		 * @return builder object
		 */
		public Builder page(int numPerPage, int pageIndex) {
			this.take = numPerPage;
			this.skip = numPerPage * pageIndex;
			isFiltering = true;
			return this;
		}

		/**
		 * A free-form text to filter by that searches through transaction description, merchant name, category name, currency, tags comments and transaction data.
		 *
		 * @return builder object
		 */
		public Builder searchText(String searchText) {
			this.searchText = searchText;
			isFiltering = true;
			return this;
		}

		public Builder hideExcluded(boolean hideExcluded) {
			this.hideExcluded = hideExcluded;
			isFiltering = true;
			return this;
		}

		/**
		 * A flag telling the selector to use exact merchant texts
		 *
		 * @param useExactMerchantTexts Boolean to use exact merchant texts
		 * @return builder object
		 */
		public Builder useExactMerchantTexts(boolean useExactMerchantTexts) {
			this.useExactMerchantTexts = useExactMerchantTexts;
			isFiltering = true;
			return this;
		}

		/**
		 * Whether or not to get uncertain categorization or flagged transactions
		 *
		 * @return builder object
		 */
		public Builder uncertainOrFlagged(boolean uncertainOrFlagged) {
			this.uncertainOrFlagged = uncertainOrFlagged;
			isFiltering = true;
			return this;
		}

		/**
		 * Whether or not to use absolute amount search.
		 *
		 * @return builder object
		 */
		public Builder useAbsoluteAmountSearch(boolean useAbsoluteAmountSearch) {
			this.useAbsoluteAmountSearch = useAbsoluteAmountSearch;
			isFiltering = true;
			return this;
		}

		/**
		 * Whether the search performed is accent insensitive or not.
		 *
		 * @return builder object
		 */
		public Builder useAccentInsensitiveSearch(boolean useAccentInsensitiveSearch) {
			this.useAccentInsensitiveSearch = useAccentInsensitiveSearch;
			isFiltering = true;
			return this;
		}

		/**
		 * Whether or not to search for amount in currency instead of amount.
		 *
		 * @return builder object
		 */
		public Builder useAmountInCurrencySearch(boolean useAmountInCurrencySearch) {
			this.useAmountInCurrencySearch = useAmountInCurrencySearch;
			isFiltering = true;
			return this;
		}

		/**
		 * Whether or not tags are searched with AND or OR.
		 *
		 * @return builder object
		 */
		public Builder useAndSearchForTags(boolean useAndSearchForTags) {
			this.useAndSearchForTags = useAndSearchForTags;
			isFiltering = true;
			return this;
		}

		/**
		 * Whether or not BankId is searched with EQUALS or LIKE. If true, exact matches are found, otherwise matches that contain the search string for BankId.
		 *
		 * @return builder object
		 */
		public Builder useEqualsSearchForBankId(boolean useEqualsSearchForBankId) {
			this.useEqualsSearchForBankId = useEqualsSearchForBankId;
			isFiltering = true;
			return this;
		}

		/**
		 * If true, filters by exact (equals) description as opposed to contains (which is the default).
		 *
		 * @return builder object
		 */
		public Builder useExactDescription(boolean useExactDescription) {
			this.useExactDescription = useExactDescription;
			isFiltering = true;
			return this;
		}

		/**
		 * If set to true, parent and the parents children will be found for each merchant id provided in MerchantIds and in ExcludeMerchantIds and theyincluded in the filter.
		 * If the merchant has no parent then its children, if any, are added.
		 *
		 * @return builder
		 */
		public Builder useParentMerchantIds(boolean useParentMerchantIds) {
			this.useParentMerchantIds = useParentMerchantIds;
			isFiltering = true;
			return this;
		}

		/**
		 * Whether or not to only get uncategorized transactions.
		 *
		 * @return builder
		 */
		public Builder onlyUncategorized(boolean onlyUncategorized) {
			this.onlyUncategorized = onlyUncategorized;
			isFiltering = true;
			return this;
		}

		/**
		 * Amounts to search from and to.
		 *
		 * @return builder object
		 */
		public Builder amounts(MenigaDecimal from, MenigaDecimal to) {
			this.amountFrom = from;
			this.amountTo = to;
			isFiltering = true;
			return this;
		}

		/**
		 * A list of category ids to filter by.
		 *
		 * @return builder object
		 */
		public Builder categories(List<Long> categoryIds) {
			this.categoryIds = categoryIds;
			isFiltering = true;
			return this;
		}

		/**
		 * A list of categories to filter by (will be converted to list of ids).
		 *
		 * @return builder object
		 */
		public Builder categories(ArrayList<MenigaCategory> categories) {
			List<Long> categoryIds = new ArrayList<>();
			for (MenigaCategory category : categories) {
				categoryIds.add(category.getId());
			}
			this.categoryIds = categoryIds;
			isFiltering = true;
			return this;
		}

		/**
		 * A category id to filter by.
		 *
		 * @return builder object
		 */
		public Builder category(long categoryId) {
			this.categoryIds = new ArrayList<>();
			this.categoryIds.add(categoryId);
			isFiltering = true;
			return this;
		}

		/**
		 * List of bankid's to filter by.
		 *
		 * @return builder object
		 */
		public Builder bankIds(List<String> bankIds) {
			this.bankIds = bankIds;
			isFiltering = true;
			return this;
		}

		/**
		 * The category types to filter by.
		 *
		 * @return builder object
		 */
		public Builder categoryTypes(List<CategoryType> categoryTypes) {
			this.categoryTypes = categoryTypes;
			isFiltering = true;
			return this;
		}

		/**
		 * The category type to filter by.
		 *
		 * @return builder object
		 */
		public Builder categoryType(CategoryType categoryType) {
			List<CategoryType> list = new ArrayList<>();
			list.add(categoryType);
			this.categoryTypes = list;
			isFiltering = true;
			return this;
		}

		/**
		 * The transaction comment to filter by. If it's null, it will be ignored. But it is possible to search for empty comments.
		 *
		 * @return builder object
		 */
		public Builder comment(String comment) {
			this.comment = comment;
			isFiltering = true;
			return this;
		}

		/**
		 * The counterparty account identifers to filter by.
		 *
		 * @return builder object
		 */
		public Builder counterpartyAccountIdentifiers(List<String> counterpartyAccountIdentifiers) {
			this.counterpartyAccountIdentifiers = counterpartyAccountIdentifiers;
			isFiltering = true;
			return this;
		}

		/**
		 * The transaction description to filter by. If it's null, it will be ignored. But it is possible to search for empty descriptions.
		 *
		 * @return builder object
		 */
		public Builder description(String description) {
			this.description = description;
			isFiltering = true;
			return this;
		}

		/**
		 * The merchant IDs to exclude.
		 *
		 * @return builder object
		 */
		public Builder excludeMerchantIds(List<Integer> excludeMerchantIds) {
			this.excludeMerchantIds = excludeMerchantIds;
			isFiltering = true;
			return this;
		}

		/**
		 * The merchant texts to exclude.
		 *
		 * @return builder object
		 */
		public Builder excludeMerchantTexts(List<String> excludeMerchantTexts) {
			this.excludeMerchantTexts = excludeMerchantTexts;
			isFiltering = true;
			return this;
		}

		/**
		 * A comma seperated list of what fields should be returned.
		 *
		 * @return builder object
		 */
		public Builder fields(List<String> fields) {
			this.fields = fields;
			isFiltering = true;
			return this;
		}

		/**
		 * If set, will only return transactions that have insert time before the supplied value.
		 *
		 * @return builder object
		 */
		public Builder insertedBefore(DateTime insertedBefore) {
			this.insertedBefore = insertedBefore;
			isFiltering = true;
			return this;
		}

		/**
		 * A list of account ids to filter by.
		 *
		 * @return builder object
		 */
		public Builder accountIds(List<Long> accountIds) {
			this.accountIds = accountIds;
			isFiltering = true;
			return this;
		}

		/**
		 * A list of account types to filter by (current accounts, credit cards, saving accounts etc.)
		 *
		 * @return builder object
		 */
		public Builder accountTypeIds(List<Long> accountTypeIds) {
			this.accountTypeIds = accountTypeIds;
			isFiltering = true;
			return this;
		}

		/**
		 * A list of accounts to filter by.
		 *
		 * @return builder object
		 */
		public Builder accounts(List<MenigaAccount> accounts) {
			this.accountIds = new ArrayList<>();
			for (MenigaAccount acc : accounts) {
				this.accountIds.add(acc.getId());
			}
			isFiltering = true;
			return this;
		}

		/**
		 * A single account id to filter by. Same as calling accounts with a singleton list
		 *
		 * @return builder object
		 */
		public Builder account(long accountId) {
			this.accountIds = new ArrayList<>();
			this.accountIds.add(accountId);
			isFiltering = true;
			return this;
		}

		/**
		 * The account identifiers to filter by.
		 *
		 * @return builder object
		 */
		public Builder accountIdentifiers(List<String> accountIdentifiers) {
			this.accountIdentifiers = accountIdentifiers;
			isFiltering = true;
			return this;
		}

		/**
		 * A list of merchant ids to filter by.
		 *
		 * @return builder object
		 */
		public Builder merchantIds(List<Long> merchantIds) {
			this.merchantIds = merchantIds;
			isFiltering = true;
			return this;
		}

		/**
		 * A single merchant id to filter by.
		 *
		 * @return builder object
		 */
		public Builder merchantId(long merchantId) {
			this.merchantIds = new ArrayList<>();
			this.merchantIds.add(merchantId);
			isFiltering = true;
			return this;
		}

		/**
		 * A list of merchant texts to filter by
		 *
		 * @return builder object
		 */
		public Builder merchantTexts(List<String> merchantTexts) {
			this.merchantTexts = merchantTexts;
			isFiltering = true;
			return this;
		}

		/**
		 * @return A method that disables including skip and take (pagination). Per default
		 * a skip and take of 0, 50 is provided.
		 */
		public Builder disableSkipTake() {
			this.skip = null;
			this.take = null;
			isFiltering = true;
			return this;
		}

		/**
		 * A single merchant text to filter by.
		 * Shorthand for using the merchantTexts method passing a list with a single entry.
		 *
		 * @return builder object
		 */
		public Builder merchantText(String merchantText) {
			List<String> list = new ArrayList<>();
			list.add(merchantText);
			this.merchantTexts = list;
			isFiltering = true;
			return this;
		}

		/**
		 * A list of tag strings to filter by.
		 *
		 * @return builder object
		 */
		public Builder tags(List<String> tags) {
			this.tags = tags;
			isFiltering = true;
			return this;
		}

		/**
		 * A single tag to filter by
		 *
		 * @return builder object
		 */
		public Builder tag(String tag) {
			this.tags = new ArrayList<>();
			this.tags.add(tag);
			isFiltering = true;
			return this;
		}

		/**
		 * A period from and to, to filter by.
		 *
		 * @return builder object
		 */
		public Builder period(DateTime periodFrom, DateTime periodTo) {
			this.periodFrom = periodFrom;
			this.periodTo = periodTo;
			isFiltering = true;
			return this;
		}

		/**
		 * A period from to filter by.
		 *
		 * @return builder object
		 */
		public Builder periodFrom(DateTime periodFrom) {
			this.periodFrom = periodFrom;
			isFiltering = true;
			return this;
		}

		/**
		 * A period to, to filter by.
		 *
		 * @return builder object
		 */
		public Builder periodTo(DateTime periodTo) {
			this.periodTo = periodTo;
			isFiltering = true;
			return this;
		}

		/**
		 * An original period from and to, to filter by.
		 *
		 * @return builder object
		 */
		public Builder originalPeriod(DateTime originalPeriodFrom, DateTime originalPeriodTo) {
			this.originalPeriodFrom = originalPeriodFrom;
			this.originalPeriodTo = originalPeriodTo;
			isFiltering = true;
			return this;
		}

		/**
		 * The transaction data to filter by.
		 *
		 * @return builder object
		 */
		public Builder parsedData(String parsedData) {
			this.parsedData = parsedData;
			isFiltering = true;
			return this;
		}

		/**
		 * List of keys in parsed data that should only return a match if the value is exactly the same as supplied in ParsedData.
		 *
		 * @return builder object
		 */
		public Builder parsedDataExactKeys(List<String> parsedDataExactKeys) {
			this.parsedDataExactKeys = parsedDataExactKeys;
			isFiltering = true;
			return this;
		}

		/**
		 * The parsed data parameter to order by when orderBy = ByParsedData.
		 *
		 * @return builder object
		 */
		public Builder parsedDataNameToOrderBy(String parsedDataNameToOrderBy) {
			this.parsedDataNameToOrderBy = parsedDataNameToOrderBy;
			isFiltering = true;
			return this;
		}

		/**
		 * Flag for the option of getting the transaction accounts as a separate meta field
		 *
		 * @return builder object
		 */
		public Builder includeAccounts(boolean includeAccounts) {
			this.includeAccounts = includeAccounts;
			return this;
		}

		/**
		 * Flag for the option of getting the transaction merchants as a separate meta field
		 *
		 * @return builder object
		 */
		public Builder includeMerchants(boolean includeMerchants) {
			this.includeMerchants = includeMerchants;
			return this;
		}

		/**
		 * A list of transaction ids to filter by.
		 *
		 * @return builder object
		 */
		public Builder transactions(List<Long> transactionIds) {
			this.ids = transactionIds;
			isFiltering = true;
			return this;
		}

		/**
		 * Filters transactions to only those that have been seen.
		 *
		 * @return builder object
		 */
		public Builder onlyUnread(boolean onlyUnread) {
			this.onlyUnread = onlyUnread;
			isFiltering = true;
			return this;
		}

		/**
		 * Filters transactions to only those that have been flagged.
		 *
		 * @return builder object
		 */
		public Builder onlyFlagged(boolean onlyFlagged) {
			this.onlyFlagged = onlyFlagged;
			isFiltering = true;
			return this;
		}

		/**
		 * Filters transactions to only those that are marked with uncertain categorization.
		 *
		 * @return builder object
		 */
		public Builder onlyUncertain(boolean onlyUncertain) {
			this.onlyUncertain = onlyUncertain;
			isFiltering = true;
			return this;
		}

		/**
		 * Merges all the non null fields of two existing filters into the builder
		 *
		 * @param filter1 Filter to merge non null fields number 1
		 * @param filter2 Filter to merge non null fields number 2
		 * @return TransactionFilter
		 */
		public Builder mergeFilters(TransactionsFilter filter1, TransactionsFilter filter2) {
			this.consumeExistingFilter(filter1);
			this.consumeExistingFilter(filter2);
			return this;
		}

		/**
		 * Builds the transaction filter.
		 *
		 * @return TransactionFilter
		 */
		public TransactionsFilter build() {
			return new TransactionsFilter(this);
		}
	}
}
