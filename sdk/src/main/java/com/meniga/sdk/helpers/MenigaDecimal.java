package com.meniga.sdk.helpers;

import com.meniga.sdk.ErrorHandler;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaDecimal implements Serializable, Comparable {

	public static MenigaDecimal ZERO = new MenigaDecimal(0);

	private BigDecimal number;

	/**
	 * Expects US locale for decimal format. Decimal symbol is '.'
	 *
	 * @param str decimal number represented in us local fx. 5.4
	 */
	public MenigaDecimal(String str) {
		this.number = new BigDecimal(str);
	}

	public MenigaDecimal(int num) {
		try {
			this.number = new BigDecimal(num);
		} catch (NumberFormatException ex) {
			this.number = new BigDecimal(0);
		}
	}

	public MenigaDecimal(Double dbl) {
		try {
			this.number = new BigDecimal(dbl);
		} catch (NumberFormatException ex) {
			this.number = new BigDecimal(0);
		}
	}

	public MenigaDecimal(MenigaDecimal md) {
		this.number = new BigDecimal(md.number.toString());
	}

	public MenigaDecimal(BigDecimal md) {
		this.number = md;
	}

	public MenigaDecimal(float fl) {
		this.number = new BigDecimal(fl);
	}


	public boolean lessThanOrEqual(Double comparator) {
		return lessThanOrEqual(new MenigaDecimal(comparator));
	}

	public boolean lessThanOrEqual(MenigaDecimal comparator) {
		return this.number.compareTo(comparator.number) <= 0;
	}

	public boolean greaterThanOrEqual(Double comparator) {
		return greaterThanOrEqual(new MenigaDecimal(comparator));
	}

	public boolean greaterThanOrEqual(MenigaDecimal comparator) {
		return this.number.compareTo(comparator.number) >= 0;
	}

	public boolean equals(Double comparator) {
		return equals(new MenigaDecimal(comparator));
	}

	public boolean equals(MenigaDecimal comparator) {
		return this.number.compareTo(comparator.number) == 0;
	}

	public boolean lessThan(Double comparator) {
		return lessThan(new MenigaDecimal(comparator));
	}

	public boolean lessThan(MenigaDecimal comparator) {
		return this.number.compareTo(comparator.number) == -1;
	}

	public boolean greaterThan(Double comparator) {
		return greaterThan(new MenigaDecimal(comparator));
	}

	public boolean greaterThan(MenigaDecimal comparator) {
		return this.number.compareTo(comparator.number) == 1;
	}

	public MenigaDecimal add(MenigaDecimal argument) {
		return new MenigaDecimal(this.number.add(argument.number, MathContext.DECIMAL64));
	}

	public MenigaDecimal abs() {
		return new MenigaDecimal(this.number.abs());
	}

	public MenigaDecimal subtract(MenigaDecimal argument) {
		return new MenigaDecimal(this.number.subtract(argument.number, MathContext.DECIMAL64));
	}

	public MenigaDecimal multiply(MenigaDecimal argument) {
		return new MenigaDecimal(this.number.multiply(argument.number, MathContext.DECIMAL64));
	}

	public MenigaDecimal multiply(int argument) {
		BigDecimal intVal = new BigDecimal(argument);
		return new MenigaDecimal(this.number.multiply(intVal, MathContext.DECIMAL64));
	}

	public MenigaDecimal multiply(double argument) {
		BigDecimal doubleVal = new BigDecimal(argument);
		return new MenigaDecimal(this.number.multiply(doubleVal, MathContext.DECIMAL64));
	}

	public MenigaDecimal negate() {
		return new MenigaDecimal(this.number.negate());
	}

	public MenigaDecimal round(MathContext mc) {
		return new MenigaDecimal(this.number.round(mc));
	}

	public MenigaDecimal round(int precicion) {
		MathContext mc = new MathContext(precicion, RoundingMode.HALF_UP);

		return new MenigaDecimal(this.number.round(mc));
	}

	public MenigaDecimal divide(double val) {
		MenigaDecimal dblVal = new MenigaDecimal(val);
		return this.divide(dblVal);
	}

	public MenigaDecimal divide(MenigaDecimal argument) {
		return this.divide(argument, 20, RoundingMode.HALF_UP);
	}

	public MenigaDecimal divide(MenigaDecimal argument, int precision, RoundingMode mode) {
		try {
			if (argument == null || this.number == null) {
				ErrorHandler.reportAndHandle(new Throwable("Division by null in MenigaDecimal"));
				// Preventing crashes even though this is arithmetically incorrect
				return new MenigaDecimal(0);
			}
			return new MenigaDecimal(this.number.divide(argument.number, precision, mode));
		} catch (ArithmeticException ex) {
			return new MenigaDecimal(0);
		}
	}

	public int compareTo(MenigaDecimal comp) {
		return this.number.compareTo(comp.number);
	}

	public float floatValue() {
		return this.number.floatValue();
	}

	public int intValue() {
		return this.number.intValue();
	}

	public double doubleValue() {
		return this.number.doubleValue();
	}

	public BigDecimal getBigDecimal() {
		return number;
	}

	public MenigaDecimal setScale(int scale, int roundingMode) {
		return new MenigaDecimal(this.number.setScale(scale, roundingMode));
	}

	public String toPlainString() {
		return this.number.toPlainString();
	}

	@Override
	public int compareTo(Object another) {
		if (!(another instanceof MenigaDecimal))
			return 0;

		return this.number.compareTo(((MenigaDecimal) another).number);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MenigaDecimal that = (MenigaDecimal) o;

		return number.equals(that.number);

	}

	@Override
	public int hashCode() {
		return number.hashCode();
	}

	@Override
	public String toString() {
		return getBigDecimal().toString();
	}
}
