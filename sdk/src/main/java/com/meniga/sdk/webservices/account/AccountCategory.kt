package com.meniga.sdk.webservices.account

import com.google.gson.annotations.SerializedName

internal enum class AccountCategory {
    @SerializedName("Wallet")
    WALLET,
    @SerializedName("Credit")
    CREDIT,
    @SerializedName("Current")
    CURRENT,
    @SerializedName("Savings")
    SAVINGS,
    @SerializedName("Manual")
    MANUAL,
    @SerializedName("Loan")
    LOAN,
    @SerializedName("Asset")
    ASSET,
    @SerializedName("Unknown")
    UNKNOWN;
}
