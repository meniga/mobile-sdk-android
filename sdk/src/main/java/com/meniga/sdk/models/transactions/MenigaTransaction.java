package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.MTask;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.Merge;
import com.meniga.sdk.models.accounts.MenigaAccount;
import com.meniga.sdk.models.categories.MenigaCategoryScore;
import com.meniga.sdk.models.feed.MenigaFeedItem;
import com.meniga.sdk.models.merchants.MenigaMerchant;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionOperations;
import com.meniga.sdk.providers.tasks.Continuation;
import com.meniga.sdk.providers.tasks.Task;
import com.meniga.sdk.providers.tasks.TaskCompletionSource;
import com.meniga.sdk.webservices.requests.UpdateSplits;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a transaction in the Meniga system. Transactions belong to accounts.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransaction implements Serializable, MenigaFeedItem, Cloneable, Parcelable {
	protected static MenigaTransactionOperations apiOperator;

	protected String parentIdentifier;
	protected long id;
	protected MenigaDecimal amount;
	protected List<String> tags;
	protected List<MenigaComment> comments = new ArrayList<>();
	protected Long categoryId;
	protected DateTime date;
	protected String text;
	protected DateTime originalDate;
	protected String originalText;
	protected MenigaDecimal originalAmount;
	protected Boolean isRead;
	protected Boolean isFlagged;
	protected boolean hasUncertainCategorization;
	protected Long accountId;
	protected MenigaAccount account;
	protected Integer mcc;
	protected List<MenigaCategoryScore> detectedCategories;
	protected String currency;
	protected MenigaDecimal amountInCurrency;
	protected Integer dataFormat;
	protected Long merchantId;
	protected MenigaMerchant merchant;
	protected List<MenigaMerchant> merchants;
	protected String bankId;
	protected DateTime insertTime;
	protected Boolean hasUserClearedCategoryUncertainty;
	protected Boolean isUncleared;
	protected MenigaDecimal balance;
	protected DateTime categoryChangedTime;
	protected Long changedByRule;
	protected DateTime changedByRuleTime;
	protected String counterpartyAccountIdentifier;
	protected DateTime dueDate;
	protected DateTime lastModifiedTime;
	protected DateTime timestamp;
	protected List<ParsedData> parsedData;
	protected String data;
	protected MenigaDecimal redeemed;
	protected MenigaDecimal toRedeem;
	protected MenigaDecimal remainingToSpend;
	protected Boolean belongsToExpiredOffer;
	protected String reason;
	protected Boolean isSplitChild;
	protected long timeStamp;
	protected String userData;

	protected String eventTypeIdentifier;
	protected String topicName;

	protected MenigaTransaction() {
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaTransactionOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaTransactionOperations operator) {
		MenigaTransaction.apiOperator = operator;
	}

	/**
	 * @return The ID of the transaction.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return The ID of the parent transaction, null if the transactions isn't a child.
	 */
	public String getParentIdentifier() {
		return parentIdentifier;
	}

	/**
	 * @return The text in the transaction.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return The amount of the transaction.
	 */
	public MenigaDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount The amount of the transaction. This field can only be persisted in the api for user-created transactions
	 */
	public void setAmount(MenigaDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return The ID of the transaction's category.
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId Sets a new category for the transaction.
	 */
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return The comments in the transaction.
	 */
	public List<MenigaComment> getComments() {
		if (comments == null) {
			comments = new ArrayList<>();
		}
		return comments;
	}

	/**
	 * @return The date of the transaction.
	 */
	public DateTime getDate() {
		return date;
	}

	/**
	 * @param date The new date for the transaction.
	 */
	public void setDate(DateTime date) {
		this.date = date;
	}

	/**
	 * @return Weather or not the transaction has been read.
	 */
	public Boolean getIsRead() {
		return isRead;
	}

	/**
	 * @param isRead Sets the transaction to be read or not.
	 */
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	public void setIsReadAndUpdate() {
		setIsRead(true);
		update();
	}

	/**
	 * @return Whether or not the transaction has been flagged.
	 */
	public Boolean getIsFlagged() {
		return isFlagged;
	}

	/**
	 * @param isFlagged If you want the transaction to be flagged or not.
	 */
	public void setIsFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	/**
	 * @return Whether or not the transaction has uncertain categorization.
	 */
	public boolean getHasUncertainCategorization() {
		return hasUncertainCategorization;
	}

	/**
	 * @param uncertain Sets the transaction to have uncertain categorization or not.
	 */
	public void setHasUncertainCategorization(boolean uncertain) {
		this.hasUncertainCategorization = uncertain;
	}

	/**
	 * @return The tags in the transaction.
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @return The original date if the transaction "date" is different.
	 */
	public DateTime getOriginalDate() {
		return originalDate;
	}

	@Override
	public String getEventTypeIdentifier() {
		return eventTypeIdentifier;
	}

	@Override
	public String getTopicName() {
		return topicName;
	}

	/**
	 * @return The original text if the transaction "text" is different.
	 */
	public String getOriginalText() {
		return originalText;
	}

	/**
	 * @return The original amount of this transaction. Sum of split transactions result in the OriginalAmount.
	 */
	public MenigaDecimal getOriginalAmount() {
		return originalAmount;
	}

	/**
	 * @return The id of the account this transaction belongs to.
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @return The account this transaction belongs to.
	 */
	public MenigaAccount getAccount() {
		return account;
	}

	/**
	 * @return The merchant category code mapping used when detecting categories.
	 */
	public Integer getMcc() {
		return mcc;
	}

	/**
	 * @return A list of detected categories.
	 */
	public List<MenigaCategoryScore> getDetectedCategories() {
		return detectedCategories;
	}

	/**
	 * @return The currency of the transaction.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @return The amount in currency of the transaction.
	 */
	public MenigaDecimal getAmountInCurrency() {
		return amountInCurrency;
	}

	/**
	 * @return Id of the Meniga.Core.Extensions.ITransactionDataFormatParser used for this transaction.
	 */
	public Integer getDataFormat() {
		return dataFormat;
	}

	/**
	 * @return Id of a Merchant if this transaction was linked to a merchant.
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * @return Merchant if this transaction was linked to a merchant.
	 */
	public MenigaMerchant getMerchant() {
		return merchant;
	}

	/**
	 * @return List of merchants if this transaction was linked to a merchant(s).
	 */
	public List<MenigaMerchant> getMerchants() {
		return merchants;
	}

	/**
	 * @return The bank's unique identifier for the transaction (transaction identifier).
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * @return The insert time of the transaction into the Meniga system.
	 */
	public DateTime getInsertTime() {
		return insertTime;
	}

	/**
	 * @return Weather or not the user has cleared the uncertain flag or not
	 */
	public Boolean getHasUserClearedCategoryUncertainty() {
		return hasUserClearedCategoryUncertainty;
	}

	/**
	 * @return True if the transaction is uncleared.
	 */
	public Boolean getIsUncleared() {
		return isUncleared;
	}

	/**
	 * @return Balance of the account after this transaction.
	 */
	public MenigaDecimal getBalance() {
		return balance;
	}

	/**
	 * @return The time when the category was last changed, or null if the category has never been changed by the end user.
	 */
	public DateTime getCategoryChangedTime() {
		return categoryChangedTime;
	}

	/**
	 * @return Contains Id of a rule that changed this transaction, or null if this transaction has not been modified by a rule.
	 */
	public Long getChangedByRule() {
		return changedByRule;
	}

	/**
	 * @return The time when the category was last changed, or null if the category has never been changed by the end user.
	 */
	public DateTime getChangedByRuleTime() {
		return changedByRuleTime;
	}

	/**
	 * @return Identifier of a counterpart account in the same realm that was transferred from/to during this transaction.
	 */
	public String getCounterpartyAccountIdentifier() {
		return counterpartyAccountIdentifier;
	}

	/**
	 * @return The due date when the user needs to pay for this transaction, e.g. when the credit card bill has to be paid for credit card transactions.
	 */
	public DateTime getDueDate() {
		return dueDate;
	}

	/**
	 * @return Time when this transaction was last modified.
	 */
	public DateTime getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * @return The transaction timestamp.
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return Extra fields for this transaction having field names as keys.
	 */
	public List<ParsedData> getParsedData() {
		return parsedData;
	}

	/**
	 * @return The raw data that comes with the transaction from the financial data realm. Parsed data is normally more useful.
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return Amount of monetary reward amount this transaction generated as a part of a rewards scheme. Only has meaning in relation to the Offers system.
	 */
	public MenigaDecimal getRedeemed() {
		return redeemed;
	}

	/**
	 * @return Money that has yet to be redeemed for this transaction. Only has meaning in relation to the Offers system.
	 */
	public MenigaDecimal getToRedeem() {
		return toRedeem;
	}

	// -- Setters end --

	/**
	 * @return Amount you need to spend at this transactions merchant to be able to activate an Offer. Only has meaning in relation to the Offers system.
	 */
	public MenigaDecimal getRemainingToSpend() {
		return remainingToSpend;
	}

	/**
	 * @return True if this transaction was used in an Offer. Only has meaning in relation to the Offers system.
	 */
	public Boolean getBelongsToExpiredOffer() {
		return belongsToExpiredOffer;
	}

	/**
	 * @return Type of redemption type for the transaction. Only has meaning in relation to the Offers system.
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @return True if a transaction is a split child.
	 */
	public boolean getIsSplitChild() {
		if (isSplitChild == null) {
			return false;
		}
		return isSplitChild;
	}

	/**
	 * @return True if a transaction is a split parent.
	 */
	public boolean getIsSplitParent() {
		if (isSplitChild == null) {
			return false;
		}
		return !isSplitChild;
	}

	/**
	 * @param splitParent Sets a flag that indicates that this transaction is a split parent and contains split children
	 */
	public void setIsSplitParent(boolean splitParent) {
		if (!splitParent) {
			// This can really only mean that this is not a split transaction any more
			isSplitChild = null;
			return;
		}
		// You can only be a split parent if isSplitChild is false
		isSplitChild = false;
	}

	/**
	 * @return True if a transaction is split, either parent or child.
	 */
	public boolean getIsSplit() {
		return isSplitChild != null;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.parentIdentifier);
		dest.writeLong(this.id);
		dest.writeSerializable(this.amount);
		dest.writeStringList(this.tags);
		dest.writeTypedList(this.comments);
		dest.writeValue(this.categoryId);
		dest.writeSerializable(this.date);
		dest.writeString(this.text);
		dest.writeSerializable(this.originalDate);
		dest.writeString(this.originalText);
		dest.writeSerializable(this.originalAmount);
		dest.writeValue(this.isRead);
		dest.writeValue(this.isFlagged);
		dest.writeByte(this.hasUncertainCategorization ? (byte) 1 : (byte) 0);
		dest.writeValue(this.accountId);
		dest.writeParcelable(this.account, flags);
		dest.writeValue(this.mcc);
		dest.writeTypedList(this.detectedCategories);
		dest.writeString(this.currency);
		dest.writeSerializable(this.amountInCurrency);
		dest.writeValue(this.dataFormat);
		dest.writeValue(this.merchantId);
		dest.writeParcelable(this.merchant, flags);
		dest.writeTypedList(this.merchants);
		dest.writeString(this.bankId);
		dest.writeSerializable(this.insertTime);
		dest.writeValue(this.hasUserClearedCategoryUncertainty);
		dest.writeValue(this.isUncleared);
		dest.writeSerializable(this.balance);
		dest.writeSerializable(this.categoryChangedTime);
		dest.writeValue(this.changedByRule);
		dest.writeSerializable(this.changedByRuleTime);
		dest.writeString(this.counterpartyAccountIdentifier);
		dest.writeSerializable(this.dueDate);
		dest.writeSerializable(this.lastModifiedTime);
		dest.writeSerializable(this.timestamp);
		dest.writeTypedList(this.parsedData);
		dest.writeString(this.data);
		dest.writeSerializable(this.redeemed);
		dest.writeSerializable(this.toRedeem);
		dest.writeSerializable(this.remainingToSpend);
		dest.writeValue(this.belongsToExpiredOffer);
		dest.writeString(this.reason);
		dest.writeValue(this.isSplitChild);
		dest.writeLong(this.timeStamp);
		dest.writeString(this.userData);
		dest.writeString(this.eventTypeIdentifier);
		dest.writeString(this.topicName);
	}

	protected MenigaTransaction(Parcel in) {
		this.parentIdentifier = in.readString();
		this.id = in.readLong();
		this.amount = (MenigaDecimal) in.readSerializable();
		this.tags = in.createStringArrayList();
		this.comments = in.createTypedArrayList(MenigaComment.CREATOR);
		this.categoryId = (Long) in.readValue(Long.class.getClassLoader());
		this.date = (DateTime) in.readSerializable();
		this.text = in.readString();
		this.originalDate = (DateTime) in.readSerializable();
		this.originalText = in.readString();
		this.originalAmount = (MenigaDecimal) in.readSerializable();
		this.isRead = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.isFlagged = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.hasUncertainCategorization = in.readByte() != 0;
		this.accountId = (Long) in.readValue(Long.class.getClassLoader());
		this.account = in.readParcelable(MenigaAccount.class.getClassLoader());
		this.mcc = (Integer) in.readValue(Integer.class.getClassLoader());
		this.detectedCategories = in.createTypedArrayList(MenigaCategoryScore.CREATOR);
		this.currency = in.readString();
		this.amountInCurrency = (MenigaDecimal) in.readSerializable();
		this.dataFormat = (Integer) in.readValue(Integer.class.getClassLoader());
		this.merchantId = (Long) in.readValue(Long.class.getClassLoader());
		this.merchant = in.readParcelable(MenigaMerchant.class.getClassLoader());
		this.merchants = in.createTypedArrayList(MenigaMerchant.CREATOR);
		this.bankId = in.readString();
		this.insertTime = (DateTime) in.readSerializable();
		this.hasUserClearedCategoryUncertainty = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.isUncleared = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.balance = (MenigaDecimal) in.readSerializable();
		this.categoryChangedTime = (DateTime) in.readSerializable();
		this.changedByRule = (Long) in.readValue(Long.class.getClassLoader());
		this.changedByRuleTime = (DateTime) in.readSerializable();
		this.counterpartyAccountIdentifier = in.readString();
		this.dueDate = (DateTime) in.readSerializable();
		this.lastModifiedTime = (DateTime) in.readSerializable();
		this.timestamp = (DateTime) in.readSerializable();
		this.parsedData = in.createTypedArrayList(ParsedData.CREATOR);
		this.data = in.readString();
		this.redeemed = (MenigaDecimal) in.readSerializable();
		this.toRedeem = (MenigaDecimal) in.readSerializable();
		this.remainingToSpend = (MenigaDecimal) in.readSerializable();
		this.belongsToExpiredOffer = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.reason = in.readString();
		this.isSplitChild = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.timeStamp = in.readLong();
		this.userData = in.readString();
		this.eventTypeIdentifier = in.readString();
		this.topicName = in.readString();
	}

	public static final Creator<MenigaTransaction> CREATOR = new Creator<MenigaTransaction>() {
		@Override
		public MenigaTransaction createFromParcel(Parcel source) {
			return new MenigaTransaction(source);
		}

		@Override
		public MenigaTransaction[] newArray(int size) {
			return new MenigaTransaction[size];
		}
	};

	@Override
	public MenigaTransaction clone() throws CloneNotSupportedException {
		return (MenigaTransaction) super.clone();
	}

	public String getUserData() {
		return userData;
	}

	public void setUserData(String data) {
		userData = data;
	}

	@Override
	public String toString() {
		return Long.toString(id) + ": " + amount.doubleValue() + ": " + text + " (" + date.toString() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MenigaTransaction that = (MenigaTransaction) o;
		return id == that.id &&
				hasUncertainCategorization == that.hasUncertainCategorization &&
				timeStamp == that.timeStamp &&
				Objects.equals(parentIdentifier, that.parentIdentifier) &&
				Objects.equals(amount, that.amount) &&
				Objects.equals(tags, that.tags) &&
				Objects.equals(comments, that.comments) &&
				Objects.equals(categoryId, that.categoryId) &&
				Objects.equals(date, that.date) &&
				Objects.equals(text, that.text) &&
				Objects.equals(originalDate, that.originalDate) &&
				Objects.equals(originalText, that.originalText) &&
				Objects.equals(originalAmount, that.originalAmount) &&
				Objects.equals(isRead, that.isRead) &&
				Objects.equals(isFlagged, that.isFlagged) &&
				Objects.equals(accountId, that.accountId) &&
				Objects.equals(account, that.account) &&
				Objects.equals(mcc, that.mcc) &&
				Objects.equals(detectedCategories, that.detectedCategories) &&
				Objects.equals(currency, that.currency) &&
				Objects.equals(amountInCurrency, that.amountInCurrency) &&
				Objects.equals(dataFormat, that.dataFormat) &&
				Objects.equals(merchantId, that.merchantId) &&
				Objects.equals(merchant, that.merchant) &&
				Objects.equals(merchants, that.merchants) &&
				Objects.equals(bankId, that.bankId) &&
				Objects.equals(insertTime, that.insertTime) &&
				Objects.equals(hasUserClearedCategoryUncertainty, that.hasUserClearedCategoryUncertainty) &&
				Objects.equals(isUncleared, that.isUncleared) &&
				Objects.equals(balance, that.balance) &&
				Objects.equals(categoryChangedTime, that.categoryChangedTime) &&
				Objects.equals(changedByRule, that.changedByRule) &&
				Objects.equals(changedByRuleTime, that.changedByRuleTime) &&
				Objects.equals(counterpartyAccountIdentifier, that.counterpartyAccountIdentifier) &&
				Objects.equals(dueDate, that.dueDate) &&
				Objects.equals(lastModifiedTime, that.lastModifiedTime) &&
				Objects.equals(timestamp, that.timestamp) &&
				Objects.equals(parsedData, that.parsedData) &&
				Objects.equals(data, that.data) &&
				Objects.equals(redeemed, that.redeemed) &&
				Objects.equals(toRedeem, that.toRedeem) &&
				Objects.equals(remainingToSpend, that.remainingToSpend) &&
				Objects.equals(belongsToExpiredOffer, that.belongsToExpiredOffer) &&
				Objects.equals(reason, that.reason) &&
				Objects.equals(isSplitChild, that.isSplitChild) &&
				Objects.equals(userData, that.userData) &&
				Objects.equals(eventTypeIdentifier, that.eventTypeIdentifier) &&
				Objects.equals(topicName, that.topicName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parentIdentifier, id, amount, tags, comments, categoryId, date, text, originalDate,
				originalText, originalAmount, isRead, isFlagged, hasUncertainCategorization, accountId, account,
				mcc, detectedCategories, currency, amountInCurrency, dataFormat, merchantId, merchant, merchants,
				bankId, insertTime, hasUserClearedCategoryUncertainty, isUncleared, balance, categoryChangedTime,
				changedByRule, changedByRuleTime, counterpartyAccountIdentifier, dueDate, lastModifiedTime,
				timestamp, parsedData, data, redeemed, toRedeem, remainingToSpend, belongsToExpiredOffer, reason,
				isSplitChild, timeStamp, userData, eventTypeIdentifier, topicName);
	}

	/*
	 * API operations below
	 */

	/**
	 * Updates the user created transaction
	 *
	 * @return A list of other transactions that could have the same category as the updated transaction.
	 */
	public Result<MenigaTransactionUpdate> update() {
		return MenigaTransaction.apiOperator.updateTransaction(this);
	}

	/**
	 * Deletes a user created transaction
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> delete() {
		return MenigaTransaction.apiOperator.deleteTransaction(id);
	}

	/**
	 * Gets all the splitTransaction children of this transaction. If this transaction is a splitTransaction child, this function will return
	 * an empty list. If this transaction is not splitTransaction, it will also return an empty list.
	 *
	 * @return List of all child split transaction of this parent transaction
	 */
	public Result<List<MenigaTransaction>> fetchSplitChildren() {
		Result<List<MenigaTransaction>> task = MenigaTransaction.apiOperator.fetchSplitTransactions(this);
		if (isSplitChild != null && isSplitChild) {
			return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<List<MenigaTransaction>>() {
				@Override
				public void onFinished(List<MenigaTransaction> result, boolean failed) {
					if (!failed && result != null) {
						result.clear();
					}
				}
			});
		}
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<List<MenigaTransaction>>() {
			@Override
			public void onFinished(List<MenigaTransaction> result, boolean failed) {
				if (failed || result == null) {
					return;
				}
				List<MenigaTransaction> toReturn = new ArrayList<>();
				for (MenigaTransaction trans : result) {
					if (trans.getId() == getId()) {
						continue;
					}
					toReturn.add(trans);
				}
				Collections.sort(toReturn, new Comparator<MenigaTransaction>() {
					@Override
					public int compare(MenigaTransaction lhs, MenigaTransaction rhs) {
						return lhs.date.compareTo(rhs.date);
					}
				});
				result.clear();
				result.addAll(toReturn);
			}
		});
	}

	/**
	 * Return the parent of this splitTransaction child transaction. If this is not a splitTransaction child, then the result will be null.
	 *
	 * @return The transaction's split parent
	 */
	public Result<List<MenigaTransaction>> fetchSplitParent() {
		Result<List<MenigaTransaction>> task = MenigaTransaction.apiOperator.fetchSplitTransactions(this);
		if (isSplitChild != null && !isSplitChild) {
			return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<List<MenigaTransaction>>() {
				@Override
				public void onFinished(List<MenigaTransaction> result, boolean failed) {
					if (!failed && result != null) {
						result.clear();
					}
				}
			});
		}

		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<List<MenigaTransaction>>() {
			@Override
			public void onFinished(List<MenigaTransaction> result, boolean failed) {
				if (!failed && result != null) {
					MenigaTransaction parent = null;
					for (MenigaTransaction trans : result) {
						if (trans.isSplitChild != null && !trans.isSplitChild) {
							parent = trans;
							break;
						}
					}
					result.clear();
					result.add(parent);
				}
			}
		});
	}

	/**
	 * Returns all split children and parent regardless of who is calling (a split transaction or the parent transaction)
	 *
	 * @return List of split children and also the split parent
	 */
	public Result<List<MenigaTransaction>> fetchSplitAndParent() {
		Result<List<MenigaTransaction>> task = MenigaTransaction.apiOperator.fetchSplitTransactions(this);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<List<MenigaTransaction>>() {
			@Override
			public void onFinished(List<MenigaTransaction> result, boolean failed) {
				if (failed || result == null) {
					return;
				}

				List<MenigaTransaction> sortedResult = new ArrayList<>();
				MenigaTransaction parent = null;
				for (MenigaTransaction trans : result) {
					if (trans.isSplitChild != null && trans.isSplitChild) {
						sortedResult.add(trans);
					} else {
						parent = trans;
					}
				}
				Collections.sort(sortedResult, new Comparator<MenigaTransaction>() {
					@Override
					public int compare(MenigaTransaction lhs, MenigaTransaction rhs) {
						return lhs.date.compareTo(rhs.date);
					}
				});
				sortedResult.add(0, parent);
				result.clear();
				result.addAll(sortedResult);
			}
		});
	}

	/**
	 * Splits a transaction, creating a new connected, splitTransaction transaction, deducting the amount from this (parent) transaction
	 *
	 * @param amount     The amount that the splitTransaction transaction should have, this amount will be deducted from this (parent) transaction
	 * @param text       The text for the split transaction
	 * @param categoryId The category id of the new transaction
	 * @param isFlagged  Setwether the split transaction should be flagged or not
	 * @return A list with a parent transaction and its children.
	 */
	public Result<List<MenigaTransaction>> split(final MenigaDecimal amount, String text, long categoryId, boolean isFlagged) {
		Result<List<MenigaTransaction>> task = MenigaTransaction.apiOperator.splitTransaction(id, amount, text, categoryId, isFlagged);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<List<MenigaTransaction>>() {
			@Override
			public void onFinished(List<MenigaTransaction> result, boolean failed) {
				if (failed || result == null) {
					return;
				}
				isSplitChild = false;
			}
		});
	}

	/**
	 * Updates split transactions for a particular parent transaction by deleting current split child transactions and creating new splits.
	 *
	 * @param update The new data for all the transaction's split children
	 * @return List of updated split transactions
	 */
	public Result<List<MenigaTransaction>> updateSplits(List<UpdateSplits> update) {
		return MenigaTransaction.apiOperator.updateSplits(id, update);
	}

	/**
	 * Fetches the server version of this object and updates all fields in this object with the server values, essentially syncing it with the server
	 *
	 * @return A Task of type void, the task will have an error and be marked as failed if it is not successful
	 */
	public Result<MenigaTransaction> refresh() {
		Result<MenigaTransaction> task = MenigaTransaction.fetch(id);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaTransaction>() {
			@Override
			public void onFinished(MenigaTransaction result, boolean failed) {
				if (failed || result == null) {
					return;
				}
				try {
					Merge.merge(MenigaTransaction.this, result);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Updates a list of transactions.
	 *
	 * @param transToUpdate              The IDs of the transactions that should be updated.
	 * @param amount                     The updated amount of the transactions. If null, amount will not be updated.
	 * @param categoryId                 The updated category ID for the transactions. If null, category ID will not be updated.
	 * @param hasUncertainCategorization Whether or not the transaction has uncertain categorization.
	 * @param useSubTextInRecat          True if automatic categorization should be matched against subtext otherwise it will by default be matched against text.
	 * @param text                       The updated text of the transaction. If null or empty, text will not be updated.
	 * @param date                       The updated date of the transactions. If null, date will not be updated.
	 * @param isRead                     Set whether a transaction has been read or not.
	 * @param isFlagged                  Set whether a transaction should be flagged or not.
	 * @param userData                   Holds extra custom data that is not parsed or read via the transaction data format parser
	 * @return List of the updated transactions
	 */
	public static Result<MenigaTransactionUpdate> update(
			List<Long> transToUpdate,
			MenigaDecimal amount,
			Long categoryId,
			boolean hasUncertainCategorization,
			Boolean useSubTextInRecat,
			String text,
			DateTime date,
			Boolean isRead,
			Boolean isFlagged,
			String userData) {
		return MenigaTransaction.apiOperator.updateTransactions(
				transToUpdate,
				amount,
				categoryId,
				hasUncertainCategorization,
				useSubTextInRecat,
				text,
				date,
				isRead,
				isFlagged,
				userData
		);
	}

	/**
	 * Combines multiple calls to the transactions endpoint with multiple filters
	 *
	 * @param filters A list containing all the filters. Each filter will be used for a call. The results will be combined, sorted by date, and returned
	 * @return A list of the transactions that match the filters (individually - does not combine criteria - may contain duplicate transactions)
	 */
	public static Result<MenigaTransactionPage> fetch(List<TransactionsFilter> filters) {
	    if (filters == null || filters.size() == 0) {
	    	return fetch(new TransactionsFilter.Builder().build());
		} else if (filters.size() == 1) {
	    	return fetch(filters.get(0));
		}
		final TaskCompletionSource<MenigaTransactionPage> src = new TaskCompletionSource<>();
		final Task<MenigaTransactionPage> task = src.getTask();

		List<Task<MenigaTransactionPage>> transactionTasks = new ArrayList<>();

		for (TransactionsFilter filter : filters) {
			transactionTasks.add(fetch(filter).getTask());
		}

		Task.whenAllResult(transactionTasks)
				.continueWith(new Continuation<List<MenigaTransactionPage>, Void>() {
					@Override
					public Void then(Task<List<MenigaTransactionPage>> task) throws Exception {
						final MenigaTransactionPage page = new MenigaTransactionPage();
						if (!task.isFaulted()) {
							for (MenigaTransactionPage subPage : task.getResult()) {
								for (MenigaTransaction sub : subPage) {
									boolean exists = false;
									for (MenigaTransaction item : page) {
										if (item.getId() == sub.getId()) {
											exists = true;
											break;
										}
									}
									if (!exists) {
										page.add(sub);
									}
								}
							}
							Collections.sort(page, new Comparator<MenigaTransaction>() {
								@Override
								public int compare(MenigaTransaction left, MenigaTransaction right) {
									if (left == null || left.getDate() == null) {
										return 1;
									} else if (right == null || right.getDate() == null) {
										return -1;
									}
									return right.getDate().compareTo(left.getDate());
								}
							});
						}
						src.setResult(page);
						return null;
					}
				});
		return new MTask<>(task, src);
	}

	/**
	 * Returns all transactions that meet the filter criteria in transFilter
	 *
	 * @param transFilter A filter object for selecting the transactions
	 * @return A list of the transactions that match the filter
	 */
	public static Result<MenigaTransactionPage> fetch(final TransactionsFilter transFilter) {
		Result<MenigaTransactionPage> task = MenigaTransaction.apiOperator.getTransactions(transFilter);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaTransactionPage>() {
			@Override
			public void onFinished(MenigaTransactionPage result, boolean failed) {
				if (failed || result == null) {
					return;
				}
				if (transFilter.getNumItemsPerPage() != null) {
					result.setNumItemsPerPage(transFilter.getNumItemsPerPage());
				}
				if (transFilter.getPage() != null) {
					result.setPage(transFilter.getPage());
				}
			}
		});
	}

	/**
	 * Retrieves a specific transaction with a specific transaction id
	 *
	 * @param transactionId The id of the transaction to retrieve
	 * @return The transaction object
	 */
	public static Result<MenigaTransaction> fetch(long transactionId) {
		return MenigaTransaction.apiOperator.getTransaction(transactionId);
	}

	/**
	 * Creates a transaction in the user wallet and returns the new transaction.
	 *
	 * @param date       Date of the transaction.
	 * @param text       The title of the transaction
	 * @param amount     The amount of the transaction
	 * @param categoryId The category of the new transaction
	 * @return The new transaction object
	 */
	public static Result<MenigaTransaction> create(
			DateTime date,
			String text,
			MenigaDecimal amount,
			long categoryId) {

		return MenigaTransaction.apiOperator.createTransaction(date, text, amount, categoryId);
	}

	/**
	 * Deletes a list of user created transactions
	 *
	 * @param transactionIds The ids of the user transactions to deleteUpcoming
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public static Result<Void> deleteTransactions(List<Long> transactionIds) {
		return MenigaTransaction.apiOperator.deleteTransactions(transactionIds);
	}

	/**
	 * Recategorize transactions by text(s). If categoryId is set, the matching transactions will be recategorized as the category specified by categoryId.
	 *
	 * @param transactionTexts       The texts used to find transactions to recategorize
	 * @param recategorizeUnreadOnly Recategorize only unread transactions if true, otherwise recategorize unread or read transactions
	 * @param useSubTextInRecat      Whether to include the subText field when searching for transactions to recategorize
	 * @param markAsRead             Mark recategorized transactions as read or not
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public static Result<Void> recategorize(List<String> transactionTexts, Boolean recategorizeUnreadOnly, Boolean useSubTextInRecat, Boolean markAsRead) {
		return MenigaTransaction.apiOperator.recategorize(transactionTexts, recategorizeUnreadOnly, useSubTextInRecat, markAsRead);
	}
}
