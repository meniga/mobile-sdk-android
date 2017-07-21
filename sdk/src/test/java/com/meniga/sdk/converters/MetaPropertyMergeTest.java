package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.meniga.sdk.BuildConfig;
import com.meniga.sdk.annotations.MetaProperty;
import com.meniga.sdk.helpers.GsonProvider;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */


public class MetaPropertyMergeTest extends MenigaConverter {

	private static class SubProperty {
		public String nestedPropString;
		public Integer nestedPropNumber;
	}

	private static class TestClass {
		@MetaProperty
		Boolean propBoolean;
		@MetaProperty
		String propString;
		@MetaProperty
		Integer propNumber;
		@MetaProperty
		Float propFloat;
		@MetaProperty
		String propNull;
		@MetaProperty
		SubProperty propObject;

		@MetaProperty(fromProperty = "propObject2")
		SubProperty renamedComposite;
		@MetaProperty(fromProperty = "propNumber2")
		Integer renamedPrimitive;
	}

	@Test
	public void testMerge() throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource("raw/metapropertymerge.json");
		File file = new File(resource.getPath());

		FileInputStream fin = new FileInputStream(file);
		String body = this.convertStreamToString(fin);

		Gson gson = GsonProvider.getGsonBuilder().create();
		JsonElement jelement = new JsonParser().parse(body);
		JsonObject jobject = jelement.getAsJsonObject();

		TestClass testClass = new TestClass();

		MenigaConverter.mergeMeta(gson, testClass, jobject.getAsJsonObject("meta"));
		Assert.assertEquals((boolean)testClass.propBoolean,true);
		Assert.assertEquals(testClass.propString,"string");
		Assert.assertEquals(testClass.propFloat,1.0F);
		Assert.assertEquals((int)testClass.propNumber,1);
		Assert.assertNull(testClass.propNull);

		Assert.assertNotNull(testClass.renamedComposite);
		Assert.assertEquals((int)testClass.renamedComposite.nestedPropNumber,1);
		Assert.assertEquals(testClass.renamedComposite.nestedPropString,"string");

		Assert.assertNotNull(testClass.propObject);
		Assert.assertEquals((int)testClass.propObject.nestedPropNumber,1);
		Assert.assertEquals(testClass.propObject.nestedPropString,"string");

		Assert.assertEquals((int)testClass.renamedPrimitive,1);

		// This is just for debugging
		String s = MenigaConverter.metaAsString(testClass);
	}
}
