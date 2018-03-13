package com.meniga.sdk.webservices.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 12.3.2018.
 */
public class GetUserMetaData extends QueryRequestObject {
    public List<String> names = new ArrayList<>();

    @Override
    public long getValueHash() {
        return 0;
    }

    @Override
    public Map<String, String> toQueryMap() {
        if (names.size() == 0) {
            return new HashMap<>();
        }
        StringBuilder bld = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) {
                bld.append(",");
            }
            bld.append(names.get(i));
        }
        Map<String, String> query = new HashMap<>();
        query.put("names", bld.toString());
        return query;
    }
}
