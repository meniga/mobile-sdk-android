/*
 * Copyright 2017-2021 Meniga Iceland Inc.
 */
package com.meniga.sdk.helpers

import com.google.gson.JsonObject
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.eventconverters.EventBaseConverter
import com.meniga.sdk.eventconverters.generic.BaseEventConverter
import com.meniga.sdk.helpers.GsonProvider.gson
import com.meniga.sdk.models.accounts.MenigaAccount
import com.meniga.sdk.models.feed.MenigaFeedItem
import com.meniga.sdk.models.feed.MenigaOfferEvent
import com.meniga.sdk.models.feed.MenigaScheduledEvent
import com.meniga.sdk.models.merchants.MenigaMerchant
import com.meniga.sdk.models.transactions.MenigaTransaction
import com.meniga.sdk.models.transactions.setAccount
import com.meniga.sdk.models.transactions.setMerchant
import org.joda.time.DateTime

open class FeedItemFactory {
    fun getMenigaFeedItem(
            element: JsonObject,
            accounts: List<MenigaAccount> = listOf(),
            merchants: List<MenigaMerchant> = listOf()
    ): MenigaFeedItem {
        val gson = gson
        return when (element["typeName"].asString) {
            "TransactionFeedItemModel" -> {
                gson.fromJson(element, MenigaTransaction::class.java).apply {
                    setAccount(accounts.find { it.id == accountId })
                    setMerchant(merchants.find { it.id == merchantId })
                }
            }
            "UserEventFeedItemModel" -> {
                val eventTypeIdentifier = element["eventTypeIdentifier"].asString
                val converter = resolveConverter(eventTypeIdentifier)
                converter.eventConverter(element)
            }
            "ScheduledFeedItemModel" -> gson.fromJson(element, MenigaScheduledEvent::class.java)
            "OfferFeedItem" -> gson.fromJson(element, MenigaOfferEvent::class.java)
            else -> object : MenigaFeedItem {
                override fun clone(): MenigaFeedItem = this

                override fun getDate(): DateTime = DateTime.now()

                override fun getEventTypeIdentifier(): String = element["eventTypeIdentifier"].asString

                override fun getTopicName(): String = element["topicName"].asString
            }
        }
    }

    protected open val userEventFeedConverters: List<EventBaseConverter<*>>
        get() = MenigaSDK.getMenigaSettings().userEventFeedConverters

    private fun resolveConverter(eventName: String): EventBaseConverter<*> {
        return userEventFeedConverters.firstOrNull { it.eventNames().contains(eventName) } ?: BaseEventConverter()
    }
}
