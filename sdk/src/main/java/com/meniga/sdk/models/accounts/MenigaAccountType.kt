/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.accounts

import android.os.Parcelable
import com.meniga.sdk.models.accounts.enums.AccountCategory
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Represents further data on the type of account.
 *
 * @param id The unique Id for the account type.
 * @param name The name of the account type.
 * @param description The description of the account type.
 * @param accountCategory he account category for this account type.
 * @param organizationId  The Id of the organization associated with this account type.
 * @param realmId The Id of the realm used to synchronize transactions.
 * @param accountCategoryDetails Extra information about the account category, for example "visa" or "amex" for the "Credit" account category.
 * @param isCashbackEnabled True if cashback can be calculated for the account.
 */
@Parcelize
data class MenigaAccountType(
        val id: Long,
        val name: String?,
        val description: String?,
        val accountCategory: AccountCategory?,
        val organizationId: Long?,
        val realmId: Long?,
        val accountCategoryDetails: String?,
        val isCashbackEnabled: Boolean
) : Parcelable, Serializable
