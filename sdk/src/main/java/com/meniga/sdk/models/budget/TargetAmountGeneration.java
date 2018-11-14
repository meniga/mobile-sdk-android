package com.meniga.sdk.models.budget;

import android.os.Parcel;
import android.os.Parcelable;

import com.meniga.sdk.models.budget.enums.GenerationType;

import java.io.Serializable;

import static com.meniga.sdk.helpers.Objects.checkArgument;

public final class TargetAmountGeneration implements Parcelable, Serializable {

	private final int value;

	public static TargetAmountGeneration create(GenerationType type, int value) {
		checkArgument(value >= 0, "The value must be >= 0");

		switch (type) {
			case MANUAL:
				return new TargetAmountGeneration(0);
			case SAME_AS_MONTH:
				return new TargetAmountGeneration(-value);
			case AVERAGE_MONTHS:
				return new TargetAmountGeneration(value);
			default:
				throw new IllegalArgumentException("Unhandled generation type " + type + " provided");
		}
	}

	public static TargetAmountGeneration create(int value) {
		return new TargetAmountGeneration(value);
	}

	public static TargetAmountGeneration manual() {
		return create(GenerationType.MANUAL, 0);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TargetAmountGeneration that = (TargetAmountGeneration) o;

		return value == that.value;
	}

	@Override
	public int hashCode() {
		return value;
	}

	private TargetAmountGeneration(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public int getMonths() {
		return Math.abs(value);
	}

	public GenerationType getType() {
		if (value == 0) {
			return GenerationType.MANUAL;
		} else if (value < 0) {
			return GenerationType.SAME_AS_MONTH;
		} else {
			return GenerationType.AVERAGE_MONTHS;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.value);
	}

	TargetAmountGeneration(Parcel in) {
		this.value = in.readInt();
	}

	public static final Creator<TargetAmountGeneration> CREATOR = new Creator<TargetAmountGeneration>() {
		@Override
		public TargetAmountGeneration createFromParcel(Parcel source) {
			return new TargetAmountGeneration(source);
		}

		@Override
		public TargetAmountGeneration[] newArray(int size) {
			return new TargetAmountGeneration[size];
		}
	};
}
