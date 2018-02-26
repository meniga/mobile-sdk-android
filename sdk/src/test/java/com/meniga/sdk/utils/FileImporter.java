package com.meniga.sdk.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class FileImporter {

    public static String getJsonFileFromRaw(String fileName) throws IOException {
        try (InputStream inputStream = getInputStreamFromRaw(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        }
    }

    public static InputStream getInputStreamFromRaw(String fileName) throws FileNotFoundException {
        ClassLoader classLoader = FileImporter.class.getClassLoader();
        URL resource = classLoader.getResource("raw/" + fileName);
        File file = new File(resource.getPath());
        return new FileInputStream(file);
    }
}
