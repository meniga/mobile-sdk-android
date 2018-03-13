@file:JvmName("MenigaTransactionPageAssertions")

package com.meniga.sdk.models.transactions

import org.assertj.core.api.AbstractAssert
import org.assertj.core.internal.Objects

fun assertThat(actual: MenigaTransactionPage) = MenigaTransactionPageAssert(actual)

class MenigaTransactionPageAssert(
        actual: MenigaTransactionPage,
        private val objects: Objects = Objects.instance()
) : AbstractAssert<MenigaTransactionPageAssert, MenigaTransactionPage>(actual, MenigaTransactionPageAssert::class.java) {

    fun hasAccountsIncluded() = apply {
        actual.forEach {
            it.account?.let { account ->
                objects.assertEqual(info, account.id, it.accountId)
            }
        }
    }

    fun hasMerchantsIncluded() = apply {
        actual.forEach {
            it.merchant?.let { merchant ->
                objects.assertEqual(info, merchant.id, it.merchantId)
            }
        }
    }

    fun hasPageIndex(expected: Int) = apply {
        objects.assertEqual(info, actual.getPage(), expected)
    }
}
