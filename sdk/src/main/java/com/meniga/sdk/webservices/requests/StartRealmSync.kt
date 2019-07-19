package com.meniga.sdk.webservices.requests

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 5.4.2018.
 */
data class StartRealmSync(
        @JvmField @Transient var realmUserId: Long = 0,
        @JvmField var waitForCompleteMilliseconds: Long = 0,
        @JvmField var sessionToken: String? = null
) : QueryRequestObject() {
    override fun getValueHash(): Long = hashCode().toLong()
}
