package com.meniga.sdk.converters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.transactions.MenigaComment;
import com.meniga.sdk.models.transactions.MenigaTransaction;
import com.meniga.sdk.models.transactions.MenigaTransactionPage;
import com.meniga.sdk.models.transactions.MenigaTransactionUpdate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 16.2.2018.
 */
public class MenigaTransactionUpdateConverter extends MenigaConverter {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, final Retrofit retrofit) {
        Type typeOfTransactionUpdate = new TypeToken<MenigaTransactionUpdate>() {}.getType();

        if (typeOfTransactionUpdate.equals(type)) {
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody resBody) throws IOException {
                    Gson gson = GsonProvider.getGsonBuilder();

                    InputStreamReader isr = null;
                    try {
                        isr = new InputStreamReader(resBody.byteStream());
                        JsonElement element = new JsonParser().parse(isr);
                        isr.close();
                        JsonObject json = element.getAsJsonObject();
                        List<MenigaTransaction> updatedTransactions = new ArrayList<>();
                        if (json.get("data").isJsonArray()) {
                            updatedTransactions = gson.fromJson(json.getAsJsonArray("data"), new TypeToken<List<MenigaTransaction>>() {}.getType());
                        } else {
                            MenigaTransaction updated = gson.fromJson(json.getAsJsonObject("data"), new TypeToken<MenigaTransaction>() {}.getType());
                            updatedTransactions.add(updated);
                        }

                        List<MenigaTransaction> transactions = new ArrayList<>();
                        if (json.has("meta")) {
                            transactions = gson.fromJson(json.getAsJsonArray("meta"), new TypeToken<List<MenigaTransaction>>() {}.getType());
                        }
                        return MenigaTransactionUpdate.fromList(updatedTransactions, transactions);
                    } finally {
                        if (isr != null) {
                            isr.close();
                        }
                    }
                }
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return null;
    }
}
