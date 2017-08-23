package com.meniga.sdk.webservices.requests;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */

public class UpdateBudget extends QueryRequestObject {

	public long id;
    public String name;
    public String description;
    public List<Long> accountIds;
    public List<UpdateBudgetEntry> entries;
    public boolean isDefault;
	public int offset;

    @Override
    public long getValueHash() {
	    int result = (int) (id ^ (id >>> 32));
	    result = 31 * result + (name != null ? name.hashCode() : 0);
	    result = 31 * result + (description != null ? description.hashCode() : 0);
	    result = 31 * result + (accountIds != null ? accountIds.hashCode() : 0);
	    result = 31 * result + (entries != null ? entries.hashCode() : 0);
	    result = 31 * result + (isDefault ? 1 : 0);
	    result = 31 * result + offset;
	    return result;
    }


}
