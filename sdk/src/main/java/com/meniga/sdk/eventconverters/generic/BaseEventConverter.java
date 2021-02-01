package com.meniga.sdk.eventconverters.generic;

import com.google.gson.JsonElement;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.models.feed.MenigaFeedItem;

import java.util.Collections;
import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 6.12.2017.
 */
public class BaseEventConverter implements EventBaseConverter<MenigaFeedItem> {
    @Override
    public MenigaFeedItem eventConverter(JsonElement el) {
        return new BaseMenigaFeedItem(el.toString());
    }

    @Override
    public List<String> eventNames() {
        return Collections.singletonList("any");
    }
}
