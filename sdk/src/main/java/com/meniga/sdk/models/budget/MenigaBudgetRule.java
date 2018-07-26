package com.meniga.sdk.models.budget;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.operators.MenigaBudgetOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MenigaBudgetRule implements Parcelable, Serializable {

	private static MenigaBudgetOperations apiOperator;

	protected long id;
	protected MenigaDecimal targetAmount;
	protected DateTime startDate;
	protected DateTime endDate;
	protected DateTime updatedAt;
	protected long budgetId;
	protected int generationType;
	protected List<Long> categoryIds;

	public long getId() {
		return id;
	}

	@Nonnull
	public MenigaDecimal getTargetAmount() {
		return targetAmount;
	}

	@Nonnull
	public DateTime getStartDate() {
		return startDate;
	}

	@Nullable
	public DateTime getEndDate() {
		return endDate;
	}

	@Nonnull
	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public long getBudgetId() {
		return budgetId;
	}

	public TargetAmountGeneration getTargetAmountGeneration() {
		return TargetAmountGeneration.create(generationType);
	}

	@Nonnull
	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public static void setOperator(MenigaBudgetOperations operator) {
		MenigaBudgetRule.apiOperator = operator;
	}

	public Result<Void> delete() {
		return apiOperator.deleteBudgetRule(budgetId, id);
	}

	public static Result<List<MenigaBudgetRule>> fetch(FetchBudgetRulesFilter filter) {
		return apiOperator.getBudgetRules(filter.toGetBudgetRules());
	}

	public static Result<List<MenigaBudgetRule>> create(NewBudgetRules budgetRules) {
		return apiOperator.createBudgetRules(budgetRules.getBudgetId(), budgetRules.toCreateBudgetRules());
	}

	private MenigaBudgetRule() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeSerializable(this.targetAmount);
		dest.writeSerializable(this.startDate);
		dest.writeSerializable(this.endDate);
		dest.writeSerializable(this.updatedAt);
		dest.writeLong(this.budgetId);
		dest.writeInt(this.generationType);
		dest.writeList(this.categoryIds);
	}

	protected MenigaBudgetRule(Parcel in) {
		this.id = in.readLong();
		this.targetAmount = (MenigaDecimal) in.readSerializable();
		this.startDate = (DateTime) in.readSerializable();
		this.endDate = (DateTime) in.readSerializable();
		this.updatedAt = (DateTime) in.readSerializable();
		this.budgetId = in.readLong();
		this.generationType = in.readInt();
		this.categoryIds = new ArrayList<Long>();
		in.readList(this.categoryIds, Long.class.getClassLoader());
	}

	public static final Creator<MenigaBudgetRule> CREATOR = new Creator<MenigaBudgetRule>() {
		@Override
		public MenigaBudgetRule createFromParcel(Parcel source) {
			return new MenigaBudgetRule(source);
		}

		@Override
		public MenigaBudgetRule[] newArray(int size) {
			return new MenigaBudgetRule[size];
		}
	};
}
