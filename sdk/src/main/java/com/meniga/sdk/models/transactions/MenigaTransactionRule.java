package com.meniga.sdk.models.transactions;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.MenigaSDK;
import com.meniga.sdk.helpers.Interceptor;
import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.Merge;
import com.meniga.sdk.models.StateObject;
import com.meniga.sdk.models.transactions.enums.AmountLimitSignCriteria;
import com.meniga.sdk.models.transactions.enums.AmountLimitTypeCriteria;
import com.meniga.sdk.models.transactions.enums.DateMatchTypeCriteria;
import com.meniga.sdk.models.transactions.enums.TransactionRuleTextCriteriaOperatorType;
import com.meniga.sdk.models.transactions.operators.MenigaTransactionRuleOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user generated rule in the Meniga system that automatically executes some
 * transformation on data for the user that created the rule when certain criteria defined in the rule are met.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaTransactionRule extends StateObject implements Parcelable, Serializable, Cloneable {
	public static final Creator<MenigaTransactionRule> CREATOR = new Creator<MenigaTransactionRule>() {
		@Override
		public MenigaTransactionRule createFromParcel(Parcel source) {
			return new MenigaTransactionRule(source);
		}

		@Override
		public MenigaTransactionRule[] newArray(int size) {
			return new MenigaTransactionRule[size];
		}
	};

	protected static MenigaTransactionRuleOperations apiOperator;

	protected long id;
	protected long userId;
	protected DateTime createdDate;
	protected DateTime modifiedDate;

	protected String name;
	protected String textCriteria;
	protected TransactionRuleTextCriteriaOperatorType textCriteriaOperatorType;
	protected DateMatchTypeCriteria dateMatchTypeCriteria;
	protected Integer daysLimitCriteria;
	protected AmountLimitTypeCriteria amountLimitTypeCriteria;
	protected AmountLimitSignCriteria amountLimitSignCriteria;
	protected MenigaDecimal amountCriteria;
	protected String accountCategoryCriteria;
	protected Boolean acceptAction;
	protected Integer monthShiftAction;
	protected Boolean removeAction;
	protected String textAction;
	protected String commentAction;
	protected Long categoryIdAction;
	protected List<SplitAction> splitActions;
	protected Boolean flagAction;
	protected String tagAction;

	protected MenigaTransactionRule() {
	}

	protected MenigaTransactionRule(Parcel in) {
		this.id = in.readLong();
		this.userId = in.readLong();
		this.createdDate = (DateTime) in.readSerializable();
		this.modifiedDate = (DateTime) in.readSerializable();
		this.name = in.readString();
		this.textCriteria = in.readString();
		int tmpTextCriteriaOperatorType = in.readInt();
		this.textCriteriaOperatorType = tmpTextCriteriaOperatorType == -1 ? null : TransactionRuleTextCriteriaOperatorType.values()[tmpTextCriteriaOperatorType];
		int tmpDateMatchTypeCriteria = in.readInt();
		this.dateMatchTypeCriteria = tmpDateMatchTypeCriteria == -1 ? null : DateMatchTypeCriteria.values()[tmpDateMatchTypeCriteria];
		this.daysLimitCriteria = (Integer) in.readValue(Integer.class.getClassLoader());
		int tmpAmountLimitTypeCriteria = in.readInt();
		this.amountLimitTypeCriteria = tmpAmountLimitTypeCriteria == -1 ? null : AmountLimitTypeCriteria.values()[tmpAmountLimitTypeCriteria];
		int tmpAmountLimitSignCriteria = in.readInt();
		this.amountLimitSignCriteria = tmpAmountLimitSignCriteria == -1 ? null : AmountLimitSignCriteria.values()[tmpAmountLimitSignCriteria];
		this.amountCriteria = (MenigaDecimal) in.readSerializable();
		this.accountCategoryCriteria = in.readString();
		this.acceptAction = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.monthShiftAction = (Integer) in.readValue(Integer.class.getClassLoader());
		this.removeAction = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.textAction = in.readString();
		this.commentAction = in.readString();
		this.categoryIdAction = (Long) in.readValue(Long.class.getClassLoader());
		this.splitActions = new ArrayList<>();
		in.readList(this.splitActions, SplitAction.class.getClassLoader());
		this.flagAction = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.tagAction = in.readString();
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaTransactionRuleOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaTransactionRuleOperations operator) {
		MenigaTransactionRule.apiOperator = operator;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.userId);
		dest.writeSerializable(this.createdDate);
		dest.writeSerializable(this.modifiedDate);
		dest.writeString(this.name);
		dest.writeString(this.textCriteria);
		dest.writeInt(this.textCriteriaOperatorType == null ? -1 : this.textCriteriaOperatorType.ordinal());
		dest.writeInt(this.dateMatchTypeCriteria == null ? -1 : this.dateMatchTypeCriteria.ordinal());
		dest.writeValue(this.daysLimitCriteria);
		dest.writeInt(this.amountLimitTypeCriteria == null ? -1 : this.amountLimitTypeCriteria.ordinal());
		dest.writeInt(this.amountLimitSignCriteria == null ? -1 : this.amountLimitSignCriteria.ordinal());
		dest.writeSerializable(this.amountCriteria);
		dest.writeString(this.accountCategoryCriteria);
		dest.writeValue(this.acceptAction);
		dest.writeValue(this.monthShiftAction);
		dest.writeValue(this.removeAction);
		dest.writeString(this.textAction);
		dest.writeString(this.commentAction);
		dest.writeValue(this.categoryIdAction);
		dest.writeList(this.splitActions);
		dest.writeValue(this.flagAction);
		dest.writeString(this.tagAction);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public MenigaTransactionRule clone() throws CloneNotSupportedException {
		return (MenigaTransactionRule) super.clone();
	}

	@Override
	protected void revertToRevision(StateObject lastRevision) {
		if (!(lastRevision instanceof MenigaTransactionRule)) {
			return;
		}
		MenigaTransactionRule other = (MenigaTransactionRule) lastRevision;
		this.textCriteria = other.textCriteria;
		this.textCriteriaOperatorType = other.textCriteriaOperatorType;
		this.dateMatchTypeCriteria = other.dateMatchTypeCriteria;
		this.daysLimitCriteria = other.daysLimitCriteria;
		this.amountLimitTypeCriteria = other.amountLimitTypeCriteria;
		this.amountLimitSignCriteria = other.amountLimitSignCriteria;
		this.amountCriteria = other.amountCriteria;
		this.accountCategoryCriteria = other.accountCategoryCriteria;
		this.acceptAction = other.acceptAction;
		this.monthShiftAction = other.monthShiftAction;
		this.removeAction = other.removeAction;
		this.textAction = other.textAction;
		this.commentAction = other.commentAction;
		this.categoryIdAction = other.categoryIdAction;
		this.splitActions = other.splitActions;
		this.flagAction = other.flagAction;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaTransactionRule that = (MenigaTransactionRule) o;

		if (id != that.id) {
			return false;
		}
		if (userId != that.userId) {
			return false;
		}
		if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) {
			return false;
		}
		if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (textCriteria != null ? !textCriteria.equals(that.textCriteria) : that.textCriteria != null) {
			return false;
		}
		if (textCriteriaOperatorType != that.textCriteriaOperatorType) {
			return false;
		}
		if (dateMatchTypeCriteria != that.dateMatchTypeCriteria) {
			return false;
		}
		if (daysLimitCriteria != null ? !daysLimitCriteria.equals(that.daysLimitCriteria) : that.daysLimitCriteria != null) {
			return false;
		}
		if (amountLimitTypeCriteria != that.amountLimitTypeCriteria) {
			return false;
		}
		if (amountLimitSignCriteria != that.amountLimitSignCriteria) {
			return false;
		}
		if (amountCriteria != null ? !amountCriteria.equals(that.amountCriteria) : that.amountCriteria != null) {
			return false;
		}
		if (accountCategoryCriteria != null ? !accountCategoryCriteria.equals(that.accountCategoryCriteria) : that.accountCategoryCriteria != null) {
			return false;
		}
		if (acceptAction != null ? !acceptAction.equals(that.acceptAction) : that.acceptAction != null) {
			return false;
		}
		if (monthShiftAction != null ? !monthShiftAction.equals(that.monthShiftAction) : that.monthShiftAction != null) {
			return false;
		}
		if (removeAction != null ? !removeAction.equals(that.removeAction) : that.removeAction != null) {
			return false;
		}
		if (textAction != null ? !textAction.equals(that.textAction) : that.textAction != null) {
			return false;
		}
		if (commentAction != null ? !commentAction.equals(that.commentAction) : that.commentAction != null) {
			return false;
		}
		if (categoryIdAction != null ? !categoryIdAction.equals(that.categoryIdAction) : that.categoryIdAction != null) {
			return false;
		}
		if (splitActions != null ? !splitActions.equals(that.splitActions) : that.splitActions != null) {
			return false;
		}
		if (flagAction != null ? !flagAction.equals(that.flagAction) : that.flagAction != null) {
			return false;
		}
		return tagAction != null ? tagAction.equals(that.tagAction) : that.tagAction == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (userId ^ (userId >>> 32));
		result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
		result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (textCriteria != null ? textCriteria.hashCode() : 0);
		result = 31 * result + (textCriteriaOperatorType != null ? textCriteriaOperatorType.hashCode() : 0);
		result = 31 * result + (dateMatchTypeCriteria != null ? dateMatchTypeCriteria.hashCode() : 0);
		result = 31 * result + (daysLimitCriteria != null ? daysLimitCriteria.hashCode() : 0);
		result = 31 * result + (amountLimitTypeCriteria != null ? amountLimitTypeCriteria.hashCode() : 0);
		result = 31 * result + (amountLimitSignCriteria != null ? amountLimitSignCriteria.hashCode() : 0);
		result = 31 * result + (amountCriteria != null ? amountCriteria.hashCode() : 0);
		result = 31 * result + (accountCategoryCriteria != null ? accountCategoryCriteria.hashCode() : 0);
		result = 31 * result + (acceptAction != null ? acceptAction.hashCode() : 0);
		result = 31 * result + (monthShiftAction != null ? monthShiftAction.hashCode() : 0);
		result = 31 * result + (removeAction != null ? removeAction.hashCode() : 0);
		result = 31 * result + (textAction != null ? textAction.hashCode() : 0);
		result = 31 * result + (commentAction != null ? commentAction.hashCode() : 0);
		result = 31 * result + (categoryIdAction != null ? categoryIdAction.hashCode() : 0);
		result = 31 * result + (splitActions != null ? splitActions.hashCode() : 0);
		result = 31 * result + (flagAction != null ? flagAction.hashCode() : 0);
		result = 31 * result + (tagAction != null ? tagAction.hashCode() : 0);
		return result;
	}

	/**
	 * @return ID of the transaction rule.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return ID of the user owning this rule.
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @return Accept action for this rule.
	 */
	public boolean getIsAcceptAction() {
		return this.acceptAction;
	}

	/**
	 * @return Remove action for this rule.
	 */
	public boolean getIsRemoveAction() {
		return removeAction;
	}

	/**
	 * @return Creation date of this rule.
	 */
	public DateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return Last modification date of this rule.
	 */
	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @return User-defined name of the rule.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name set a new name for the rule.
	 */
	public void setName(String name) {
		if (hasChanged(this.name, name)) {
			changed();
			this.name = name;
		}
	}

	/**
	 * @return A text criteria to match by this rule, or null if this rule has no text criteria.
	 */
	public String getTextCriteria() {
		return textCriteria;
	}

	/**
	 * @param textCriteria set a text criteria to be matched by this rule, or null if no text criteria is wanted.
	 */
	public void setTextCriteria(String textCriteria) {
		if (hasChanged(this.textCriteria, textCriteria)) {
			changed();
			this.textCriteria = textCriteria;
		}
	}

	/**
	 * @return The type of operator to use when evaluating the TextCriteria. The possible values are found in TransactionRuleTextCriteriaOperatorTypeEnum.
	 */
	public TransactionRuleTextCriteriaOperatorType getTextCriteriaOperatorType() {
		return textCriteriaOperatorType;
	}

	/**
	 * @param textCriteriaOperatorType set a new text criteria operator type for the rule.
	 */
	public void setTextCriteriaOperatorType(TransactionRuleTextCriteriaOperatorType textCriteriaOperatorType) {
		if (this.textCriteriaOperatorType == textCriteriaOperatorType) {
			return;
		}
		this.changed();
		this.textCriteriaOperatorType = textCriteriaOperatorType;
	}

	/**
	 * @return A date match criteria for this rule (0 = First DaysLimitCriteria days in a month, 1 = Last DaysLimitCriteria days in a month).
	 */
	public DateMatchTypeCriteria getDateMatchTypeCriteria() {
		return dateMatchTypeCriteria;
	}

	/**
	 * @param dateMatchTypeCriteria set a new date to match type criteria.
	 */
	public void setDateMatchTypeCriteria(DateMatchTypeCriteria dateMatchTypeCriteria) {
		if (this.dateMatchTypeCriteria == dateMatchTypeCriteria) {
			return;
		}
		this.changed();
		this.dateMatchTypeCriteria = dateMatchTypeCriteria;
	}

	/**
	 * @return The number of days to use when using DateMatchTypeCriteria criteria for this rule.
	 */
	public Integer getDaysLimitCriteria() {
		return daysLimitCriteria;
	}

	/**
	 * @param daysLimitCriteria update the day limit for the rule.
	 */
	public void setDaysLimitCriteria(int daysLimitCriteria) {
		if (hasChanged(this.daysLimitCriteria, daysLimitCriteria)) {
			changed();
			this.daysLimitCriteria = daysLimitCriteria;
		}
	}

	/**
	 * @return Amount criteria for this rule (AmountUnder = 0, AmountOver = 1, AmountEqual = 2).
	 */
	public AmountLimitTypeCriteria getAmountLimitTypeCriteria() {
		return amountLimitTypeCriteria;
	}

	/**
	 * @param amountLimitTypeCriteria set a new amount type for the rule.
	 */
	public void setAmountLimitTypeCriteria(AmountLimitTypeCriteria amountLimitTypeCriteria) {
		if (this.amountLimitTypeCriteria == amountLimitTypeCriteria) {
			return;
		}
		this.changed();
		this.amountLimitTypeCriteria = amountLimitTypeCriteria;
	}

	/**
	 * @return Amount limit sign criteria for this rule (AmountIncomeOrExpense = 0, AmountExpense = 1, AmountIncome = 2).
	 */
	public AmountLimitSignCriteria getAmountLimitSignCriteria() {
		return amountLimitSignCriteria;
	}

	/**
	 * @param amountLimitSignCriteria set a new amount limit for the rule.
	 */
	public void setAmountLimitSignCriteria(AmountLimitSignCriteria amountLimitSignCriteria) {
		if (this.amountLimitSignCriteria == amountLimitSignCriteria) {
			return;
		}
		this.changed();
		this.amountLimitSignCriteria = amountLimitSignCriteria;
	}

	/**
	 * @return Amount criteria for this rule.
	 */
	public MenigaDecimal getAmountCriteria() {
		return amountCriteria;
	}

	/**
	 * @param amountCriteria set a new amount criteria.
	 */
	public void setAmountCriteria(MenigaDecimal amountCriteria) {
		if (hasChanged(this.amountCriteria, amountCriteria)) {
			changed();
			this.amountCriteria = amountCriteria;
		}
	}

	/**
	 * @return comma seperated list of account category integers that should be matched before applying actions The meaning of the
	 * integers are as follows AccountCategoryEnum: 1 = Current, 2 = Credit, 3 = Savings Example: AccountCategoryCriteria="1,2" means
	 * that this rule should only apply to Current and Credit accounts, not Savings.
	 */
	public String getAccountCategoryCriteria() {
		return accountCategoryCriteria;
	}

	/**
	 * @param accountCategoryCriteria set a new account category criteria.
	 */
	public void setAccountCategoryCriteria(String accountCategoryCriteria) {
		if (hasChanged(this.accountCategoryCriteria, accountCategoryCriteria)) {
			changed();
			this.accountCategoryCriteria = accountCategoryCriteria;
		}
	}

	/**
	 * @return Accept action for this rule.
	 */
	public Boolean getAcceptAction() {
		return acceptAction;
	}

	/**
	 * @param acceptAction set accepted action.
	 */
	public void setAcceptAction(boolean acceptAction) {
		if (hasChanged(this.acceptAction, acceptAction)) {
			changed();
			this.acceptAction = acceptAction;
		}
	}

	/**
	 * @return Month shift action for this rule.
	 */
	public Integer getMonthShiftAction() {
		return monthShiftAction;
	}

	/**
	 * @param monthShiftAction set number of months to shift transactions matched by this rule.
	 */
	public void setMonthShiftAction(int monthShiftAction) {
		if (hasChanged(this.monthShiftAction, monthShiftAction)) {
			changed();
			this.monthShiftAction = monthShiftAction;
		}
	}

	/**
	 * @return Remove action for this rule.
	 */
	public Boolean getRemoveAction() {
		return removeAction;
	}

	/**
	 * @param removeAction set remove actions.
	 */
	public void setRemoveAction(boolean removeAction) {
		if (hasChanged(this.removeAction, removeAction)) {
			changed();
			this.removeAction = removeAction;
		}
	}

	/**
	 * @return Text action for this rule.
	 */
	public String getTextAction() {
		return textAction;
	}

	/**
	 * @param textAction set text action.
	 */
	public void setTextAction(String textAction) {
		if (hasChanged(this.textAction, textAction)) {
			changed();
			this.textAction = textAction;
		}
	}

	/**
	 * @return Comment action for this rule.
	 */
	public String getCommentAction() {
		return commentAction;
	}

	/**
	 * @param commentAction set comment action.
	 */
	public void setCommentAction(String commentAction) {
		if (hasChanged(this.commentAction, commentAction)) {
			changed();
			this.commentAction = commentAction;
		}
	}

	/**
	 * @return Categorization to apply by this rule.
	 */
	public Long getCategoryIdAction() {
		return categoryIdAction;
	}

	/**
	 * @param categoryIdAction set category ID action.
	 */
	public void setCategoryIdAction(long categoryIdAction) {
		if (hasChanged(this.categoryIdAction, categoryIdAction)) {
			changed();
			this.categoryIdAction = categoryIdAction;
		}
	}

	/**
	 * @return Split action to perform by this rule.
	 */
	public List<SplitAction> getSplitActions() {
		return splitActions;
	}

	/**
	 * @param splitActions set split action.
	 */
	public void setSplitActions(List<SplitAction> splitActions) {
		if (this.splitActions == null && splitActions == null || this.splitActions.size() == 0 && splitActions.size() == 0) {
			return;
		}
		boolean hasNew = false;
		if (this.splitActions.size() != splitActions.size()) {
			hasNew = true;
		}
		if (!hasNew) {
			for (SplitAction action : this.splitActions) {
				for (SplitAction action2 : splitActions) {
					if (!action2.equals(action)) {
						hasNew = true;
						break;
					}
				}
			}
		}
		if (!hasNew) {
			return;
		}
		this.changed();
		this.splitActions = splitActions;
	}

	/**
	 * @return Flag or not flag transactions matching this rule.
	 */
	public Boolean getFlagAction() {
		return flagAction;
	}

	/**
	 * @param flagAction set flag action.
	 */
	public void setFlagAction(Boolean flagAction) {
		if (hasChanged(this.flagAction, flagAction)) {
			changed();
			this.flagAction = flagAction;
		}
	}

	/**
	 * @return Tag to apply by this rule.
	 */
	public String getTagAction() {
		return tagAction;
	}

	/**
	 * Represents details on a split action to be executed by a rule when criteria are met.
	 * <p>
	 * Copyright 2017 Meniga Iceland Inc.
	 */
	public static class SplitAction implements Serializable, Parcelable {

		public static final Creator<SplitAction> CREATOR = new Creator<SplitAction>() {
			@Override
			public SplitAction createFromParcel(Parcel source) {
				return new SplitAction(source);
			}

			@Override
			public SplitAction[] newArray(int size) {
				return new SplitAction[size];
			}
		};

		public long id;
		public long transactionRuleId;
		public MenigaDecimal ratio;
		public MenigaDecimal amount;
		public long categoryId;

		protected SplitAction() {
		}

		protected SplitAction(Parcel in) {
			this.id = in.readLong();
			this.transactionRuleId = in.readLong();
			this.ratio = (MenigaDecimal) in.readSerializable();
			this.amount = (MenigaDecimal) in.readSerializable();
			this.categoryId = in.readLong();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			SplitAction that = (SplitAction) o;

			if (id != that.id) {
				return false;
			}
			if (transactionRuleId != that.transactionRuleId) {
				return false;
			}
			if (categoryId != that.categoryId) {
				return false;
			}
			if (ratio != null ? !ratio.equals(that.ratio) : that.ratio != null) {
				return false;
			}
			return amount != null ? amount.equals(that.amount) : that.amount == null;
		}

		@Override
		public int hashCode() {
			int result = (int) (id ^ (id >>> 32));
			result = 31 * result + (int) (transactionRuleId ^ (transactionRuleId >>> 32));
			result = 31 * result + (ratio != null ? ratio.hashCode() : 0);
			result = 31 * result + (amount != null ? amount.hashCode() : 0);
			result = 31 * result + (int) (categoryId ^ (categoryId >>> 32));
			return result;
		}

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeLong(this.id);
			dest.writeLong(this.transactionRuleId);
			dest.writeSerializable(this.ratio);
			dest.writeSerializable(this.amount);
			dest.writeLong(this.categoryId);
		}
	}

	/*
	 * API operations below
	 */

	/**
	 * Delets this transaction rule
	 *
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> delete() {
		return MenigaTransactionRule.apiOperator.deleteTransactionRule(this.id);
	}

	/**
	 * Updates a transaction rule by id
	 *
	 * @param applyOnExisting Should this updated rule be applied to existing transactions.
	 * @return A task of type Void. The task will indicate if the deleteUpcoming was successful or not
	 */
	public Result<Void> update(boolean applyOnExisting) {
		return MenigaTransactionRule.apiOperator.updateTransactionRule(
				this.id,
				this.name,
				this.textCriteria,
				this.textCriteriaOperatorType,
				this.dateMatchTypeCriteria,
				this.daysLimitCriteria,
				this.amountLimitTypeCriteria,
				this.amountLimitSignCriteria,
				this.amountCriteria,
				this.accountCategoryCriteria,
				this.acceptAction,
				this.monthShiftAction,
				this.removeAction,
				this.textAction,
				this.commentAction,
				this.categoryIdAction,
				this.splitActions,
				this.flagAction,
				applyOnExisting
		);
	}

	/**
	 * Fetches the server version of this object and updates all fields in this object with the server values, essentially syncing it with the server
	 *
	 * @return A Task of type void, the task will have an error and be marked as failed if it is not successful
	 */
	public Result<MenigaTransactionRule> refresh() {
		Result<MenigaTransactionRule> task = MenigaTransactionRule.fetch(this.id);
		return MenigaSDK.getMenigaSettings().getTaskAdapter().intercept(task, new Interceptor<MenigaTransactionRule>() {
			@Override
			public void onFinished(MenigaTransactionRule result, boolean failed) {
				if (failed || result == null) {
					return;
				}
				try {
					Merge.merge(MenigaTransactionRule.this, result);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Fetches a list of all transaction rules for the current user
	 *
	 * @return A list of all transaction rules
	 */
	public static Result<List<MenigaTransactionRule>> fetch() {
		return MenigaTransactionRule.apiOperator.getTransactionRules();
	}

	/**
	 * Creates a new transaction rule
	 *
	 * @param name                     User-defined name of the rule
	 * @param textCriteria             A text criteria to match by this rule, or null if this rule has no text criteria
	 * @param textCriteriaOperatorType The type of operator to use when evaluating the TextCriteria
	 * @param dateMatchTypeCriteria    A date match criteria for this rule
	 * @param daysLimitCriteria        The number of days to use when using DateMatchTypeCriteria criteria for this rule
	 * @param amountLimitTypeCriteria  Amount criteria for this rule
	 * @param amountLimitSignCriteria  Amount limit sign criteria for this rule
	 * @param amountCriteria           Amount criteria for this rule
	 * @param accountCategoryCriteria  A comma seperated list of account category integers that should be matched before applying actions The meaning of the integers are as follows AccountCategoryEnum: 1 = Current, 2 = Credit, 3 = Savings Example: AccountCategoryCriteria="1,2" means that this rule should only apply to Current and Credit accounts, not Savings
	 * @param acceptAction             Accept action for this rule
	 * @param monthShiftAction         Month shift action for this rule
	 * @param removeAction             Remove action for this rule
	 * @param textAction               Text action for this rule
	 * @param commentAction            Comment action for this rule
	 * @param categoryIdAction         Categorization to apply by this rule
	 * @param splitActions             Split action to perform by this rule
	 * @param flagAction               Flag or not flag transactions matching this rule
	 * @return The newly created rule
	 */
	public static Result<MenigaTransactionRule> create(
			String name,
			String textCriteria,
			TransactionRuleTextCriteriaOperatorType textCriteriaOperatorType,
			DateMatchTypeCriteria dateMatchTypeCriteria,
			Integer daysLimitCriteria,
			AmountLimitTypeCriteria amountLimitTypeCriteria,
			AmountLimitSignCriteria amountLimitSignCriteria,
			MenigaDecimal amountCriteria,
			String accountCategoryCriteria,
			Boolean acceptAction,
			Integer monthShiftAction,
			Boolean removeAction,
			String textAction,
			String commentAction,
			Long categoryIdAction,
			List<SplitAction> splitActions,
			Boolean flagAction) {

		return MenigaTransactionRule.apiOperator.createTransactionRule(
				name,
				textCriteria,
				textCriteriaOperatorType,
				dateMatchTypeCriteria,
				daysLimitCriteria,
				amountLimitTypeCriteria,
				amountLimitSignCriteria,
				amountCriteria,
				accountCategoryCriteria,
				acceptAction,
				monthShiftAction,
				removeAction,
				textAction,
				commentAction,
				categoryIdAction,
				splitActions,
				flagAction
		);
	}

	/**
	 * Retrieves a transaction ryle by id
	 *
	 * @param id The id of the rule to retrieve
	 * @return The transaction rule object
	 */
	public static Result<MenigaTransactionRule> fetch(long id) {
		return MenigaTransactionRule.apiOperator.getTransactionRule(id);
	}
}
