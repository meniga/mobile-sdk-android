package com.meniga.sdk.models.accounts

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@SuppressLint("ParcelCreator")
data class MenigaAccountMetaData(
        val name: String,
        val value: String? = null) : Parcelable, Serializable
