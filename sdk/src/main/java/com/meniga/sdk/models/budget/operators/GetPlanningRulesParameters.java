package com.meniga.sdk.models.budget.operators;

import com.annimon.stream.Optional;
import com.meniga.sdk.helpers.DateTimeUtils;
import com.meniga.sdk.helpers.Joiner;
import com.meniga.sdk.webservices.requests.QueryRequestObject;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetPlanningRulesParameters extends QueryRequestObject {

    public List<Long> categoryIds;
    public DateTime startDate;
    public DateTime endDate;
    public Boolean allowOverlappingRules;

    @Override
    public long getValueHash() {
        int result = categoryIds != null ? categoryIds.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (allowOverlappingRules != null ? allowOverlappingRules.hashCode() : 0);
        return result;
    }

    @Override
    public Map<String, String> toQueryMap() {
        Map<String, String> queryMap = new HashMap<>();

        Optional.ofNullable(categoryIds)
                .map(longs -> Joiner.join(longs, ","))
                .ifPresent(categoryIds -> queryMap.put("categoryIds", categoryIds));

        Optional.ofNullable(startDate)
                .map(DateTimeUtils::toString)
                .executeIfPresent(dateTime -> queryMap.put("startDate", dateTime));

        Optional.ofNullable(endDate)
                .map(DateTimeUtils::toString)
                .executeIfPresent(dateTime -> queryMap.put("endDate", dateTime));

        Optional.ofNullable(allowOverlappingRules)
                .executeIfPresent(allowOverlappingRules -> queryMap.put("allowOverlappingRules", allowOverlappingRules.toString()));

        return queryMap;
    }
}
