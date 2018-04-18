package com.meniga.sdk.models.accounts

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class MenigaAccountMetaData(
        val name: String,
        val value: String? = null) : Parcelable
