package com.meniga.sdk.webservices.account

internal data class AccountType(
        val id: Long? = null,
        val name: String? = null,
        val parentId: Long = 0,
        val parentName: String? = null)
