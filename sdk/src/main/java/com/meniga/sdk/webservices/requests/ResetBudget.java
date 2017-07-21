package com.meniga.sdk.webservices.requests;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class ResetBudget extends QueryRequestObject {
    public long id;
    public List<Long> categoryIds;
    public boolean resetManualEntries;
    @Override
    public long getValueHash() {
        return hashCode();
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
        result = 31 * result + (resetManualEntries ? 1 : 0);
        return result;
    }
}
