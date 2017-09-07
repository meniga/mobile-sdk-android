package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class ForgotPassword extends QueryRequestObject {
    public String email;

    @Override
    public long getValueHash() {
        if (email == null) {
            return hashCode();
        }
        return email.hashCode();
    }
}
