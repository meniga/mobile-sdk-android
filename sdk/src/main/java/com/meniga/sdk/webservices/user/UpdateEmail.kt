/*
 * Copyright 2019 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.user

import com.meniga.sdk.webservices.requests.QueryRequestObject

internal data class UpdateEmail(
        @JvmField var newEmail: String,
        @JvmField var password: String
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
