/*
 * Copyright 2020 Meniga Iceland Inc.
 */
package com.meniga.sdk.webservices.requests

class AddComments(
        val transactionIds: List<Long>,
        val comment: String
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()
}
