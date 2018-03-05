package com.meniga.sdk.converters

import com.google.gson.JsonArray
import com.google.gson.JsonObject

sealed class MenigaApiResponse(
        open val meta: JsonObject?,
        open val included: JsonObject?) {

    data class Array(
            val data: JsonArray,
            override val meta: JsonObject?,
            override val included: JsonObject?
    ) : MenigaApiResponse(meta, included)

    data class Object(
            val data: JsonObject,
            override val meta: JsonObject?,
            override val included: JsonObject?
    ) : MenigaApiResponse(meta, included)

}
