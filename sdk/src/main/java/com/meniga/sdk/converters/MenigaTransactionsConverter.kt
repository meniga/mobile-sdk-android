/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.converters

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.meniga.sdk.helpers.GsonProvider
import com.meniga.sdk.helpers.get
import com.meniga.sdk.helpers.type
import com.meniga.sdk.models.accounts.MenigaAccount
import com.meniga.sdk.models.merchants.MenigaMerchant
import com.meniga.sdk.models.transactions.MenigaTransaction
import com.meniga.sdk.models.transactions.MenigaTransactionPage
import com.meniga.sdk.models.transactions.setAccount
import com.meniga.sdk.models.transactions.setMerchant
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class MenigaTransactionsConverter : MenigaConverter() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        val gson = GsonProvider.getGsonBuilder()
        when (type) {
            type<List<MenigaTransaction>>() -> return Converter<ResponseBody, Any> { responseBody ->
                val (data, _, included) = MenigaConverter.getAsArrayApiResponse(responseBody.byteStream())
                val (transactions, accounts, merchants) = gson.getAccountsMerchantsAndTransactions(data, included)
                transactions
                        .onEach {
                            it.setAccount(accounts.find { account -> account.id == it.accountId })
                            it.setMerchant(merchants.find { merchant -> merchant.id == it.merchantId })
                            it.updateCommentsTransactionId()
                        }
            }
            type<MenigaTransaction>() -> return Converter<ResponseBody, Any> { responseBody ->
                val (data, _, included) = MenigaConverter.getAsObjectApiResponse(responseBody.byteStream())
                val transaction = gson.fromJson(data, MenigaTransaction::class.java)
                included?.let {
                    transaction.setAccount(gson.get(it, "account"))
                    transaction.setMerchant(gson.get(it, "merchant"))
                }
                transaction.apply {
                    updateCommentsTransactionId()
                }
            }

            type<MenigaTransactionPage>() -> return Converter<ResponseBody, Any> { responseBody ->
                val (data, meta, included) = MenigaConverter.getAsArrayApiResponse(responseBody.byteStream())
                val transactionPage = MenigaTransactionPage()
                val (transactions, accounts, merchants) = gson.getAccountsMerchantsAndTransactions(data, included)
                transactions.forEach { transaction ->
                    transaction.setAccount(accounts.find { it.id == transaction.accountId })
                    transaction.setMerchant(merchants.find { it.id == transaction.merchantId })
                    transaction.updateCommentsTransactionId()
                }
                transactionPage.apply {
                    addAll(transactions)
                    totalNumTransactions = meta?.get("totalCount")?.asInt ?: 0
                }
            }
            else -> return null
        }
    }

    private fun Gson.getAccountsMerchantsAndTransactions(data: JsonArray, included: JsonObject?)
            : Triple<List<MenigaTransaction>, List<MenigaAccount>, List<MenigaMerchant>> =
            Triple(get<List<MenigaTransaction>>(data).orEmpty(),
                    included?.let { get<List<MenigaAccount?>>(it, "accounts") }
                            .orEmpty()
                            .filterNotNull(),
                    included?.let { get<List<MenigaMerchant?>>(it, "merchants") }
                            .orEmpty()
                            .filterNotNull())

    private fun MenigaTransaction.updateCommentsTransactionId() {
        comments?.forEach { it.transactionId = id }
    }
}
