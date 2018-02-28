@file:JvmName("MenigaAccountFactory")
package com.meniga.sdk.models.accounts

import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.models.accounts.enums.AccountAuthorizationType
import com.meniga.sdk.models.accounts.enums.AccountCategory
import org.joda.time.DateTime

@JvmOverloads
fun createAccount(seed: Int = 0) =
        MenigaAccount(
                seed.toLong(),
                "accountIdentifier$seed",
                "realmIdentifier$seed",
                0,
                0,
                "AccountName$seed",
                AccountCategory.WALLET,
                MenigaDecimal(1),
                MenigaDecimal(233),
                MenigaDecimal(2),
                MenigaDecimal(3),
                MenigaDecimal(4),
                "accountClass",
                "organizationIdentifier",
                0L,
                AccountAuthorizationType.EXTERNAL,
                0,
                false,
                DateTime.parse("2018-03-08"),
                0L,
                "userEmail",
                DateTime.parse("2018-03-08"),
                false,
                DateTime.parse("2018-03-08"),
                false,
                false,
                emptyList()
        )
