/*
 * Copyright 2019 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.user

import com.meniga.sdk.webservices.requests.QueryRequestObject

data class ChangePassword(
        @JvmField var currentPassword: String,
        @JvmField var newPassword: String
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
