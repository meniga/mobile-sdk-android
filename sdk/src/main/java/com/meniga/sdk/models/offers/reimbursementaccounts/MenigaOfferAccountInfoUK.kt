package com.meniga.sdk.models.offers.reimbursementaccounts

import android.os.Parcelable
import com.meniga.sdk.helpers.GsonProvider
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class MenigaOfferAccountInfoUK(
        val bankName: String?,
        val accountName: String?,
        val sortcode: String?,
        val bankAccountNumber: String?
) : MenigaOfferAccountInfo, Parcelable, Serializable {

    override fun toJson(): String = GsonProvider.gson.toJson(this)
}
