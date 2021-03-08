/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * Indicates the type of account authorization during account aggregation = ['0', '1', '2', '3'].
 *
 * @param id The ID of the authorization type.
 * @param name The name of the authorization type.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class MenigaAuthorizationType(
        val id: Long = 0,
        val name: String? = null
) : Parcelable, Serializable
