package com.meniga.sdk.models.offers.reimbursementaccounts

import android.os.Parcelable
import com.meniga.sdk.helpers.GsonProvider
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaOfferAccountInfo
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class MenigaOfferAccountInfoIceland(
        val bankNumber: String?,
        val ledger: String?,
        val bankAccountNumber: String?,
        val socialSecurityNumber: String?
) : MenigaOfferAccountInfo, Parcelable, Serializable {

    override fun toJson(): String = GsonProvider.gson.toJson(this)
}
