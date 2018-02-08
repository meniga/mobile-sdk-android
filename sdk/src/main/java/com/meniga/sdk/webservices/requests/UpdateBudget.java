package com.meniga.sdk.webservices.requests;

import java.util.List;

public class UpdateBudget extends QueryRequestObject {

    public String name;
    public String description;
    public List<Long> accountIds;

    @Override
    public long getValueHash() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
        return result;
    }
}
