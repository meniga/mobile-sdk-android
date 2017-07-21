package com.meniga.sdk.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for merging one object's fields with the values from another object of the same type.
 * <p>
 * Copyright 2017 Meniga Iceland Inc.
 */
public class Merge {
	/**
	 * Overwrites all private, protected and public fields of object1 with those from object 2
	 *
	 * @param object1 Object who's fields will be replaced with the ones from object2
	 * @param object2 The source of data, the fields from this object will be copied to object1
	 * @throws IllegalAccessException
	 */
	public static <E> void merge(E object1, E object2) throws IllegalAccessException {
		for (Field field : Merge.getAllFields(object1.getClass())) {
			if ((field.getModifiers() & java.lang.reflect.Modifier.FINAL) == java.lang.reflect.Modifier.FINAL) {
				continue;
			}
			try {
				field.setAccessible(true);
				field.set(object1, field.get(object2));
				field.setAccessible(false);
			} catch (IllegalAccessException ex) {
				// No need to log errors here, we know that some fields can not be merged
			}
		}
	}

	private static List<Field> getAllFields(Class type) {
		List<Field> fields = new ArrayList<>();
		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			fields.addAll(Arrays.asList(type.getSuperclass().getDeclaredFields()));
		}

		return fields;
	}
}
