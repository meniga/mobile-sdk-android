package com.meniga.sdk.helpers;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

@SuppressWarnings("unchecked")
public class KeyVal<A, B> implements Comparable<KeyVal<A, B>>, Parcelable {

	public static final Creator<KeyVal> CREATOR = new Creator<KeyVal>() {
		@Override
		public KeyVal createFromParcel(Parcel in) {
			return new KeyVal(in);
		}

		@Override
		public KeyVal[] newArray(int size) {
			return new KeyVal[size];
		}
	};
	private A key;
	private B value;

	public KeyVal(A aKey, B aVal) {
		this.key = aKey;
		this.value = aVal;
	}

	protected KeyVal(Parcel in) {
	}

	public A getKey() {
		return this.key;
	}

	public B getValue() {
		return this.value;
	}

	@Override
	public int compareTo(KeyVal<A, B> another) {
		if (this.key instanceof Comparable)
			return ((Comparable) this.key).compareTo(another.key);
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		KeyVal<?, ?> keyVal = (KeyVal<?, ?>) o;

		if (key != null ? !key.equals(keyVal.key) : keyVal.key != null)
			return false;
		return !(value != null ? !value.equals(keyVal.value) : keyVal.value != null);

	}

	@Override
	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeSerializable((Serializable) key);
		parcel.writeSerializable((Serializable) value);
	}
}
