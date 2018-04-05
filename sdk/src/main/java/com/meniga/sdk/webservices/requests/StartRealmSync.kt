package com.meniga.sdk.webservices.requests

/**
 * Copyright 2017 Meniga Iceland Inc.
 * Created by agustk on 5.4.2018.
 */
data class StartRealmSync(
        var realmUserId: Long = 0,
        var waitForCompleteMilliseconds: Long = 0
) : QueryRequestObject() {
    override fun getValueHash(): Long = hashCode().toLong()
}
