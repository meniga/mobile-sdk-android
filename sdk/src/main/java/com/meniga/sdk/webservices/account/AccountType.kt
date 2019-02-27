package com.meniga.sdk.webservices.account

data class AccountType(
        val id: Long? = null,
        val name: String? = null,
        val description: String? = null,
        val accountCategory: AccountCategory? = null,
        val accountClass: String? = null,
        val organizationId: Long? = 0,
        val realmId: Long? = 0,
        val accountCategoryDetails: String? = null,
        val enableCashback: Boolean? = null)
