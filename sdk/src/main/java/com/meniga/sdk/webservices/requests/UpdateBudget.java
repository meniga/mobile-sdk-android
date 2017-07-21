package com.meniga.sdk.webservices.requests;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class UpdateBudget extends QueryRequestObject {
    public String name;
    public String description;
    public List<Long> accountIds;
    public List<UpdateBudgetEntry> entries;
    @Override
    public long getValueHash() {
        return 0;
    }
}
