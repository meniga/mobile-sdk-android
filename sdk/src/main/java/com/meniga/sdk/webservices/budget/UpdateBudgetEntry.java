package com.meniga.sdk.webservices.budget;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.webservices.requests.QueryRequestObject;

import org.joda.time.DateTime;

import java.util.List;

public class UpdateBudgetEntry extends QueryRequestObject {

    public MenigaDecimal targetAmount;
    public DateTime startDate;
    public DateTime endDate;
    public List<Long> categoryIds;

    @Override
    public long getValueHash() {
        int result = targetAmount != null ? targetAmount.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
        return result;
    }
}
