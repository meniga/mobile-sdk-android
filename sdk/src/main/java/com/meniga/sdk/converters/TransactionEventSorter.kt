package com.meniga.sdk.converters

import com.meniga.sdk.models.feed.MenigaFeed
import com.meniga.sdk.models.feed.MenigaTransactionEvent
import com.meniga.sdk.models.transactions.MenigaTransaction

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 16.11.2018.
 */
class TransactionEventSorter {

    fun moveTransactionEventsToTransaction(feed : MenigaFeed) {
        val transactionEvents = (0 until feed.size).map { feed[it] }
                .filterIsInstance(MenigaTransactionEvent::class.java)
                .reversed()

        feed.removeAll { it is MenigaTransactionEvent }

        transactionEvents.forEach { event ->
            val topicId = event.topicId;
            val subList = feed.filter { it is MenigaTransaction && it.id == topicId }

            if (subList.isEmpty()) {
                feed.find { it.date.millis > event.date.millis }.let {
                    if (it == null) {
                        feed.add(event)
                    } else {
                        feed.add(feed.indexOf(it), event)
                    }
                }
            } else {
                feed.add(feed.indexOf(subList.last()) + 1, event)
                event.setDate(subList.last().getDate())
            }
        }
    }
}
