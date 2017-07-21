package com.meniga.sdk.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class FileImporter {

	public static String getJsonFileFromRaw(String fileName) throws IOException {

		ClassLoader classLoader = FileImporter.class.getClassLoader();
		URL resource = classLoader.getResource("raw/" + fileName);
		File file = new File(resource.getPath());

		FileInputStream fin = new FileInputStream(file);

		//Make sure you close all streams.
		BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		String result = sb.toString();
		reader.close();
		fin.close();

		return result;
	}
}
