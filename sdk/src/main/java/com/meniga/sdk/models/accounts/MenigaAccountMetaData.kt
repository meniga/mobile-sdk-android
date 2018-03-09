package com.meniga.sdk.models.accounts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenigaAccountMetaData(
        val name: String,
        val value: String? = null) : Parcelable
