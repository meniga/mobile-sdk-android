package com.meniga.sdk.models.offers.reimbursementaccounts

import android.annotation.SuppressLint
import android.os.Parcelable
import com.meniga.sdk.helpers.GsonProvider
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@SuppressLint("ParcelCreator")
@Parcelize
data class MenigaOfferAccountInfoUK(
        val bankName: String?,
        val accountName: String?,
        val sortcode: String?,
        val bankAccountNumber: String?
) : MenigaOfferAccountInfo, Parcelable, Serializable {

    override fun toJson(): String = GsonProvider.gson.toJson(this)
}
