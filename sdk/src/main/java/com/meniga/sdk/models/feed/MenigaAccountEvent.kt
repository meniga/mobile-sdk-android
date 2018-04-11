package com.meniga.sdk.models.feed

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.meniga.sdk.models.userevents.enums.UserEventType
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.io.Serializable

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 11.4.2018.
 */
@Parcelize
data class MenigaAccountEvent(
        @JvmField val id: Long,
        @SerializedName("topicId") @JvmField val accountId: Long,
        @JvmField val actionText: String,
        @JvmField var messageData: MenigaAccountEventData? = null,
        @JvmField val date: DateTime,
        @JvmField val title: String,
        @JvmField val body: String,
        @JvmField val eventTypeIdentifier: UserEventType? = null,
        @JvmField val topicName: String) : Parcelable, Serializable, MenigaFeedItem {

    override fun clone(): MenigaFeedItem {
        return MenigaAccountEvent(id, accountId, actionText, messageData, date, title, body, eventTypeIdentifier, topicName)
    }

    override fun getDate(): DateTime {
        return date
    }

    override fun getEventTypeIdentifier(): String {
        if(eventTypeIdentifier == null) {
            return ""
        }
        return eventTypeIdentifier.toString()
    }

    override fun getTopicName(): String {
        return topicName
    }
}
