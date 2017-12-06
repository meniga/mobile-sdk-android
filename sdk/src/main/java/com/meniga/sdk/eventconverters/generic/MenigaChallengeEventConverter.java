package com.meniga.sdk.eventconverters.generic;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.meniga.sdk.eventconverters.EventBaseConverter;
import com.meniga.sdk.helpers.GsonProvider;
import com.meniga.sdk.models.feed.MenigaAccountEvent;
import com.meniga.sdk.models.feed.MenigaChallengeEvent;
import com.meniga.sdk.models.feed.MenigaChallengeEventData;
import com.meniga.sdk.models.feed.MenigaDialogEvent;
import com.meniga.sdk.models.feed.MenigaDialogEventData;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 6.12.2017.
 */
public class MenigaChallengeEventConverter implements EventBaseConverter<MenigaChallengeEvent> {
    private final String MESSAGE_DATA = "messageData";

    @Override
    public MenigaChallengeEvent eventConverter(JsonElement element) {
        Gson gson = GsonProvider.getGsonBuilder();
        JsonElement je = element.getAsJsonObject().get(MESSAGE_DATA);

        if (!je.isJsonObject()) {
            element.getAsJsonObject().remove(MESSAGE_DATA);
            element.getAsJsonObject().add(MESSAGE_DATA, convertToObject(je.getAsString()));
        }

        MenigaChallengeEventData data = gson.fromJson(element.getAsJsonObject().get(MESSAGE_DATA), MenigaChallengeEventData.class);

        MenigaChallengeEvent event = gson.fromJson(element, MenigaChallengeEvent.class);
        event.setMessageData(data);
        return event;
    }

    @Override
    public String eventName() {
        return "challenge_started";
    }

    private JsonElement convertToObject(String stringified) {
        stringified = stringified.replace("\\", "");
        if (stringified.startsWith("\"")) {
            stringified = stringified.substring(1);
        }
        if (stringified.endsWith("\"")) {
            stringified = stringified.substring(0, stringified.length() - 1);
        }
        JsonParser parser = new JsonParser();
        return parser.parse(stringified);
    }
}
