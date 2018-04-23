/*
 * Copyright 2017 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.budget;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.helpers.Result;
import com.meniga.sdk.models.budget.enums.BudgetPeriod;
import com.meniga.sdk.models.budget.enums.BudgetType;
import com.meniga.sdk.models.budget.operators.MenigaBudgetOperations;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

public class MenigaBudget implements Parcelable, Serializable {
	protected static MenigaBudgetOperations apiOperator;

	protected long id;
	protected BudgetType type;
	protected String name;
	protected String description;
	protected List<Long> accountIds;
	protected BudgetPeriod period;
	protected int offset;
	protected DateTime created;

	protected MenigaBudget() {
	}

	public long getId() {
		return id;
	}

	public BudgetType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<Long> getAccountIds() {
		return accountIds;
	}

	public BudgetPeriod getPeriod() {
		return period;
	}

	public int getOffset() {
		return offset;
	}

	public DateTime getCreated() {
		return created;
	}

	/**
	 * Sets the api operator for doing api calls
	 *
	 * @param operator An object that implements the MenigaBudgetOperations interface for carrying out api operations on this class.
	 */
	public static void setOperator(MenigaBudgetOperations operator) {
		MenigaBudget.apiOperator = operator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MenigaBudget that = (MenigaBudget) o;

		if (id != that.id) {
			return false;
		}
		if (offset != that.offset) {
			return false;
		}
		if (type != that.type) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (description != null ? !description.equals(that.description) : that.description != null) {
			return false;
		}
		if (accountIds != null ? !accountIds.equals(that.accountIds) : that.accountIds != null) {
			return false;
		}
		if (period != that.period) {
			return false;
		}
		return created != null ? created.equals(that.created) : that.created == null;

	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
		result = 31 * result + (period != null ? period.hashCode() : 0);
		result = 31 * result + offset;
		result = 31 * result + (created != null ? created.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeInt(this.type == null ? -1 : this.type.ordinal());
		dest.writeString(this.name);
		dest.writeString(this.description);
		dest.writeList(this.accountIds);
		dest.writeInt(this.period == null ? -1 : this.period.ordinal());
		dest.writeInt(this.offset);
		dest.writeSerializable(this.created);
	}

	protected MenigaBudget(Parcel in) {
		this.id = in.readLong();
		int tmpType = in.readInt();
		this.type = tmpType == -1 ? null : BudgetType.values()[tmpType];
		this.name = in.readString();
		this.description = in.readString();
		this.accountIds = new ArrayList<>();
		in.readList(this.accountIds, Long.class.getClassLoader());
		int tmpPeriod = in.readInt();
		this.period = tmpPeriod == -1 ? null : BudgetPeriod.values()[tmpPeriod];
		this.offset = in.readInt();
		this.created = (DateTime) in.readSerializable();
	}

	public static final Creator<MenigaBudget> CREATOR = new Creator<MenigaBudget>() {
		@Override
		public MenigaBudget createFromParcel(Parcel source) {
			return new MenigaBudget(source);
		}

		@Override
		public MenigaBudget[] newArray(int size) {
			return new MenigaBudget[size];
		}
	};

	/*
	 * API Calls
	 */

	/**
	 * Use {@link #fetch(FetchBudgetsFilter)} instead.
	 */
	@Deprecated
	public static Result<List<MenigaBudget>> fetch() {
		FetchBudgetsFilter parameters = new FetchBudgetsFilter();
		parameters.setType(BudgetType.PLANNING);
		return fetch(parameters);
	}

	public static Result<List<MenigaBudget>> fetch(FetchBudgetsFilter filter) {
		return apiOperator.getBudgets(FetchBudgetsFilterExtensions.toGetBudgets(filter));
	}

	public static Result<MenigaBudget> fetch(FetchBudgetFilter filter) {
		return apiOperator.getBudget(FetchBudgetFilterExtensions.toGetBudget(filter));
	}

	/**
	 * Use {@link #create(NewBudget)} or {@link #create(NewPlanningBudget)} instead.
	 */
	@Deprecated
	public static Result<MenigaBudget> create(
			@Nonnull BudgetType type,
			@Nonnull String name,
			@Nullable String description,
			@Nullable List<Long> accountIds,
			@Nullable BudgetPeriod period) {
		return create(type, name, description, accountIds, period, null);
	}

	/**
	 * Use {@link #create(NewBudget)} or {@link #create(NewPlanningBudget)}instead.
	 */
	@Deprecated
	public static Result<MenigaBudget> create(
			@Nonnull BudgetType type,
			@Nonnull String name,
			@Nullable String description,
			@Nullable List<Long> accountIds,
			@Nullable BudgetPeriod period,
			@Nullable Integer periodOffset) {
		if (type == BudgetType.BUDGET) {
			NewBudget budget = new NewBudget(name);
			budget.setDescription(description);
			budget.setAccountIds(accountIds);
			return create(budget);
		} else {
			NewPlanningBudget budget = new NewPlanningBudget(name);
			budget.setDescription(description);
			budget.setAccountIds(accountIds);
			budget.setPeriod(period);
			budget.setPeriodOffset(periodOffset);
			return create(budget);
		}
	}

	public static Result<MenigaBudget> create(@Nonnull NewBudget budget) {
		requireNonNull(budget);
		return apiOperator.createBudget(NewBudgetExtensions.toCreateBudget(budget));
	}

	public static Result<MenigaBudget> create(@Nonnull NewPlanningBudget budget) {
		requireNonNull(budget);
		return apiOperator.createBudget(NewPlanningBudgetExtensions.toCreateBudget(budget));
	}

	public Result<Void> delete() {
		return apiOperator.deleteBudget(id);
	}

	public Result<Void> reset() {
		return apiOperator.resetBudget(id);
	}

	public Result<MenigaBudget> update(BudgetUpdate parameters) {
		return apiOperator.updateBudget(id, BudgetUpdateExtensions.toUpdateBudget(parameters));
	}
}
