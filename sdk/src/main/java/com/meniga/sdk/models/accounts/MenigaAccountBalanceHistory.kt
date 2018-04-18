/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts

import android.annotation.SuppressLint
import android.os.Parcelable
import com.meniga.sdk.helpers.MenigaDecimal
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import java.io.Serializable

/**
 * A balance entry for a specific account at a certain point in time.
 *
 * @property id The ID of the account balance history.
 * @property accountId The Meniga accountID of the account which this balance belongs to.
 * @property balance Balance at the time when it was updated.
 * @property balanceInUserCurrency Balance at the time when it was updated in the currency of the user.
 * @property balanceDate The time at which the balance was recorded.
 * @property isDefault Indicates if the entry has been generated with default values. This happens when there is missing months (in the database) between the start and end date ranges sent in by the client.
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class MenigaAccountBalanceHistory(
        val id: Long = 0,
        val accountId: Long = 0,
        val balance: MenigaDecimal? = null,
        val balanceInUserCurrency: MenigaDecimal? = null,
        val balanceDate: DateTime? = null,
        val isDefault: Boolean = false

) : Parcelable, Serializable {

    @Deprecated("Use isDefault() instead.", ReplaceWith("isDefault()"))
    fun getIsDefault(): Boolean = isDefault
}
