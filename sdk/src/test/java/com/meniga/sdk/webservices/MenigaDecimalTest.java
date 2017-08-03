package com.meniga.sdk.webservices;

import com.meniga.sdk.helpers.MenigaDecimal;

import junit.framework.Assert;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class MenigaDecimalTest {

	@Test
	public void menigaDecimalDoubleConstructorTest() {
		new MenigaDecimal((double) 50);
	}

	@Test
	public void menigaDecimalMenigaDecimalConstructorTest() {
		new MenigaDecimal(new MenigaDecimal(10));
	}

	@Test
	public void menigaDecimalBigDecimalConstructorTest() {
		new MenigaDecimal(new BigDecimal(50));
	}

	@Test
	public void menigaDecimalFloatConstructorTest() {
		new MenigaDecimal(50f);
	}

	@Test
	public void lessThenOrEqualTest() {
		MenigaDecimal less = new MenigaDecimal(0);
		MenigaDecimal great = new MenigaDecimal(50);
		Assert.assertEquals(true, less.lessThanOrEqual(great));
		Assert.assertEquals(true, less.lessThanOrEqual(less));
	}

	@Test
	public void greaterThanOrEqualTest() {
		MenigaDecimal less = new MenigaDecimal(0);
		MenigaDecimal great = new MenigaDecimal(50);
		Assert.assertEquals(true, great.greaterThanOrEqual(less));
		Assert.assertEquals(true, great.greaterThanOrEqual(great));
	}

	@Test
	public void equalsTest() {
		MenigaDecimal equal = new MenigaDecimal(50);
		Assert.assertEquals(true, equal.equals(equal));
	}

	@Test
	public void lessThanTest() {
		MenigaDecimal less = new MenigaDecimal(0);
		MenigaDecimal great = new MenigaDecimal(50);
		Assert.assertEquals(true, less.lessThan(great));
	}

	@Test
	public void greaterThanTest() {
		MenigaDecimal less = new MenigaDecimal(0);
		MenigaDecimal great = new MenigaDecimal(50);
		Assert.assertEquals(true, great.greaterThan(less));
	}

	@Test
	public void addTest() {
		MenigaDecimal one = new MenigaDecimal(1);
		MenigaDecimal two = new MenigaDecimal(2);
		assertThat(one.add(two).getBigDecimal()).isEqualTo(new MenigaDecimal(3).getBigDecimal());
	}

	@Test
	public void absTest() {
		MenigaDecimal neg = new MenigaDecimal(-1);
		assertThat(neg.abs().getBigDecimal()).isEqualTo(new MenigaDecimal(1).getBigDecimal());
	}

	@Test
	public void subtractTest() {
		MenigaDecimal one = new MenigaDecimal(1);
		MenigaDecimal two = new MenigaDecimal(2);
		assertThat(two.subtract(one).getBigDecimal()).isEqualTo(new MenigaDecimal(1).getBigDecimal());
	}

	@Test
	public void multiplyTest() {
		MenigaDecimal two = new MenigaDecimal(2);
		assertThat(two.multiply(two).getBigDecimal()).isEqualTo(new MenigaDecimal(4).getBigDecimal());
	}

	@Test
	public void negateTest() {
		MenigaDecimal one = new MenigaDecimal(1);
		assertThat(one.negate().getBigDecimal()).isEqualTo(new MenigaDecimal(-1).getBigDecimal());
	}

	@Test
	public void roundTest() {
		MenigaDecimal test = new MenigaDecimal(1.5);
		assertThat(test.round(1).getBigDecimal()).isEqualTo(new MenigaDecimal(2).getBigDecimal());
	}

	@Test
	public void divideTest() {
		MenigaDecimal test = new MenigaDecimal(2);
		assertThat(test.divide(test).round(1).getBigDecimal()).isEqualTo(new MenigaDecimal(1).getBigDecimal());
	}

	@Test
	public void compareToTest() {
		MenigaDecimal test = new MenigaDecimal(2);
		MenigaDecimal test2 = new MenigaDecimal(2);
		assertThat(test.equals(test2)).isTrue();
	}

	@Test
	public void floatValueTest() {
		MenigaDecimal test = new MenigaDecimal(3.14);
		assertThat(test.floatValue()).isEqualTo(3.14f);
	}

	@Test
	public void doubleValueTest() {
		MenigaDecimal test = new MenigaDecimal(3.14);
		assertThat(test.doubleValue()).isEqualTo(Double.valueOf(3.14));
	}

	@Test
	public void setScaleTest() {
		MenigaDecimal test = new MenigaDecimal(3.14);
		assertThat(test.setScale(0, RoundingMode.DOWN.ordinal()).getBigDecimal()).isEqualTo(new MenigaDecimal(3).getBigDecimal());
	}

	@Test
	public void intValueTest() {
		MenigaDecimal test = new MenigaDecimal(3);
		assertThat(test.intValue()).isEqualTo(3);
	}

	@Test
	public void toPlainStringTest() {
		MenigaDecimal test = new MenigaDecimal(5000);
		assertThat(test.toPlainString()).isEqualTo("5000");
	}
}
