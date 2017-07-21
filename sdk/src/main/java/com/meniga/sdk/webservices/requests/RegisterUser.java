package com.meniga.sdk.webservices.requests;

/**
 * Copyright 2017 Meniga Iceland Inc.
 */
public class RegisterUser extends QueryRequestObject {

    public String email;
    public String password;
    public String culture;

    @Override
    public long getValueHash() {
        int val = 0;
        val += email == null ? 1 : email.hashCode();
        val += password == null ? 1 : password.hashCode();
        val += culture == null ? 1 : culture.hashCode();
        return val;
    }
}
