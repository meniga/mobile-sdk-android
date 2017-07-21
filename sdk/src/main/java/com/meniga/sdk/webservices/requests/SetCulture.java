package com.meniga.sdk.webservices.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class SetCulture extends QueryRequestObject {
    public transient String culture;

    @Override
    public long getValueHash() {
        return culture.hashCode();
    }

    @Override
    public Map<String, String> toQueryMap() {
        Map<String, String> map = new HashMap<>();

        map.put("culture", culture);

        return map;
    }
}
