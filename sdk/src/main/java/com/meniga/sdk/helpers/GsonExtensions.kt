@file:JvmName("GsonExtensions")

package com.meniga.sdk.helpers

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

internal inline fun <reified T> Gson.get(jsonObject: JsonObject): T? =
        fromJson(jsonObject, type<T>())

internal inline fun <reified T> Gson.get(jsonArray: JsonArray): T? =
        fromJson(jsonArray, type<T>())

internal inline fun <reified T> Gson.get(jsonObject: JsonObject, memberName: String): T? =
        fromJson(jsonObject.get(memberName), type<T>())

internal inline fun <reified T> type(): Type = object : TypeToken<T>() {}.type
