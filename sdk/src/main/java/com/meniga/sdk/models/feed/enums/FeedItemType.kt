/*
 * Copyright 2021 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.feed.enums

import com.google.gson.annotations.SerializedName

enum class FeedItemType {
    @SerializedName("transactions")
    TRANSACTIONS,
    @SerializedName("userevents")
    USER_EVENTS,
    @SerializedName("cashback")
    CASHBACK
}
