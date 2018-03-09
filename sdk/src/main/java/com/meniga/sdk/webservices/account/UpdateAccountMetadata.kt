/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.account

import com.meniga.sdk.webservices.requests.QueryRequestObject

data class UpdateAccountMetadata(
        var name: String? = null,
        var value: String? = null
) : QueryRequestObject() {
    override fun getValueHash() = hashCode().toLong()
}
