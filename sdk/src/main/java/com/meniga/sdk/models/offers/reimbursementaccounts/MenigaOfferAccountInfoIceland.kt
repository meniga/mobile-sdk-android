package com.meniga.sdk.models.offers.reimbursementaccounts

import android.annotation.SuppressLint
import android.os.Parcelable
import com.meniga.sdk.helpers.GsonProvider
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@SuppressLint("ParcelCreator")
@Parcelize
data class MenigaOfferAccountInfoIceland(
        val bankNumber: String?,
        val ledger: String?,
        val bankAccountNumber: String?,
        val socialSecurityNumber: String?
) : MenigaOfferAccountInfo, Parcelable, Serializable {

    override fun toJson(): String = GsonProvider.gson.toJson(this)
}
