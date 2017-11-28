package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 26.11.2017.
 */
public class GetScheduledEvent extends QueryRequestObject {
    public transient long id;
    public transient String type;

    @Override
    public long getValueHash() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
