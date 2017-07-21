package com.meniga.sdk.helpers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class KeyValTest {

	@Test
	public void testCompareTo() {
		KeyVal<String, Integer> kv1 = new KeyVal<>("one", 1);
		KeyVal<String, Integer> kv2 = new KeyVal<>("one", 1);
		KeyVal<String, Integer> kv3 = new KeyVal<>("two", 2);

		assertThat(kv1.compareTo(kv2)).isEqualTo(0);
		assertThat(kv1.compareTo(kv3)).isNegative();
		assertThat(kv3.compareTo(kv1)).isPositive();
	}

	@Test
	public void testEquals() {
		KeyVal<String, Integer> kv1 = new KeyVal<>("one", 1);
		KeyVal<String, Integer> kv2 = new KeyVal<>("one", 1);
		KeyVal<String, Integer> kv3 = new KeyVal<>("two", 2);

		assertThat(kv1.equals(kv2)).isTrue();
		assertThat(kv1.equals(kv3)).isFalse();
	}

	@Test
	public void testHasCode() {
		KeyVal<String, Integer> kv1 = new KeyVal<>("one", 1);
		KeyVal<String, Integer> kv2 = new KeyVal<>("two", 1);

		assertThat(kv1.hashCode()).isNotEqualTo(kv2.hashCode());
		assertThat(kv1.hashCode()).isEqualTo(kv1.hashCode());
	}
}
