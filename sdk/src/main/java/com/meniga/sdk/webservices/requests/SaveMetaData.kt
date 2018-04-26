package com.meniga.sdk.webservices.requests

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 25.4.2018.
 */
data class SaveMetaData(
        @JvmField var name: String,
        @JvmField var value: String
) : QueryRequestObject() {
    override fun getValueHash(): Long = hashCode().toLong()
}
