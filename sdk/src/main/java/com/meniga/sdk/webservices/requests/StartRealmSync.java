package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 4.4.2018.
 */
public class StartRealmSync extends QueryRequestObject {
    public transient long realmUserId;
    public long waitForCompleteMilliseconds;

    @Override
    public long getValueHash() {
        int hash = (int) (waitForCompleteMilliseconds ^ (waitForCompleteMilliseconds >>> 32));
        hash += (int) (realmUserId ^ (realmUserId >>> 32));
        return hash;
    }

    @Override
    public Map<String, String> toQueryMap() {
        Map<String, String> query = new HashMap<>();
        query.put("realmUserId", Long.toString(realmUserId));
        return query;
    }
}
