package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 26.11.2017.
 */
public class GetScheduledEvent extends QueryRequestObject {
    public transient long id;

    @Override
    public long getValueHash() {
        return id;
    }
}
