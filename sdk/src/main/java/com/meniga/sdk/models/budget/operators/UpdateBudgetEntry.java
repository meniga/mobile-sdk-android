package com.meniga.sdk.models.budget.operators;

import com.meniga.sdk.helpers.MenigaDecimal;
import com.meniga.sdk.webservices.requests.QueryRequestObject;

import org.joda.time.DateTime;

import java.util.List;

public class UpdateBudgetEntry extends QueryRequestObject {

    public MenigaDecimal targetAmount;
    public MenigaDecimal spentAmount;
    public DateTime startDate;
    public DateTime endDate;
    public List<Long> categoryIds;
    public Integer generationType;

    @Override
    public long getValueHash() {
        int result = targetAmount != null ? targetAmount.hashCode() : 0;
        result = 31 * result + (spentAmount != null ? spentAmount.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
        result = 31 * result + (generationType != null ? generationType.hashCode() : 0);
        return result;
    }
}
