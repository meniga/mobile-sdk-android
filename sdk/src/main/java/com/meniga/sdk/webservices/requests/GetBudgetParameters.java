package com.meniga.sdk.webservices.requests;

import com.annimon.stream.Optional;
import com.meniga.sdk.helpers.DateTimeUtils;
import com.meniga.sdk.helpers.Joiner;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetBudgetParameters extends QueryRequestObject {

    public Long id;
    public List<Long> categoryIds;
    public DateTime startDate;
    public DateTime endDate;
    public Boolean allowOverlappingEntries;
    public Boolean includeEntries;
    public Boolean includeOptionalHistoricalData;

    @Override
    public long getValueHash() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (allowOverlappingEntries != null ? allowOverlappingEntries.hashCode() : 0);
        result = 31 * result + (includeEntries != null ? includeEntries.hashCode() : 0);
        result = 31 * result + (includeOptionalHistoricalData != null ? includeOptionalHistoricalData.hashCode() : 0);
        return result;
    }

    @Override
    public Map<String, String> toQueryMap() {
        Map<String, String> queryMap = new HashMap<>();
        Optional.ofNullable(id)
                .map(Object::toString)
                .executeIfPresent(id -> queryMap.put("id", id));

        Optional.ofNullable(categoryIds)
                .executeIfPresent(categoryIds ->
                        queryMap.put("categoryIds", Joiner.join(categoryIds, ",")));

        Optional.ofNullable(startDate)
                .map(DateTimeUtils::toString)
                .executeIfPresent(dateTime -> queryMap.put("startDate", dateTime));

        Optional.ofNullable(endDate)
                .map(DateTimeUtils::toString)
                .executeIfPresent(dateTime -> queryMap.put("endDate", dateTime));

        Optional.ofNullable(allowOverlappingEntries)
                .map(Object::toString)
                .executeIfPresent(value -> queryMap.put("allowOverlappingEntries", value));

        return queryMap;
    }
}
