package com.meniga.sdk.webservices.requests;

import com.meniga.sdk.models.organizations.MenigaRealmAuthParameter;

import java.util.List;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class GetRealmAuthMethod extends QueryRequestObject {

    public transient long id;
    public List<MenigaRealmAuthParameter.SimpleAuthParameter> parameters;
    public boolean saveDetails;
    public String realmUserIdentifier;

    @Override
    public long getValueHash() {
        return id;
    }
}
