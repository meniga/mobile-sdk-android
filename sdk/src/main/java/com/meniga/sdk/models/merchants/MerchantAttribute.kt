package com.meniga.sdk.models.merchants

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 30.4.2018.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class MerchantAttribute(
        val facebook: List<String>? = null,
        val twitter: List<String>? = null,
        val instagram: List<String>? = null,
        val youTube: List<String>? = null,
        val snapchat: List<String>? = null,
        val vimeo: List<String>? = null,
        val mcc: List<Int>? = null
) : Serializable, Parcelable, Cloneable