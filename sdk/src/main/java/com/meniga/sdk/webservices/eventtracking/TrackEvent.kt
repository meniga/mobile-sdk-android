package com.meniga.sdk.webservices.eventtracking

import com.meniga.sdk.webservices.requests.QueryRequestObject

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 31.5.2018.
 */
internal data class TrackEvent(
        @JvmField var trackingType: String,
        @JvmField var trackingState: String,
        @JvmField var trackerId: Long,
        @JvmField var media: String? = null
) : QueryRequestObject() {

    override fun getValueHash(): Long = hashCode().toLong()

    internal data class TrackEventTypeData(
            var trackingType: String? = null,
            var trackingState: String? = null,
            var trackerId: Long? = null,
            var media: String? = null
    )
}
