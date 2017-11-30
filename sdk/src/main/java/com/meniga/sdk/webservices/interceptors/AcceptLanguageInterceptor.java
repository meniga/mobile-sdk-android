package com.meniga.sdk.webservices.interceptors;

import com.meniga.sdk.MenigaSDK;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class AcceptLanguageInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;

        String culture = MenigaSDK.getMenigaSettings().getCulture();
        if (culture != null) {
            newRequest = request.newBuilder()
                    .addHeader("Accept-Language", culture)
                    .build();
            return chain.proceed(newRequest);
        } else {
            newRequest = request.newBuilder().build();
            return chain.proceed(newRequest);
        }
    }
}
