package com.meniga.sdk.models.sync

import com.google.gson.annotations.SerializedName

enum class AccountSyncResult {
    @SerializedName("Success")
    SUCCESS,

    @SerializedName("ConsentNotFound")
    CONSENT_NOT_FOUND,

    @SerializedName("AccountNotFound")
    ACCOUNT_NOT_FOUND,

    @SerializedName("TooManyRequests")
    TOO_MANY_REQUESTS,

    @SerializedName("ProviderNotFound")
    PROVIDER_NOT_FOUND,

    @SerializedName("ProviderDisabled")
    PROVIDER_DISABLED,

    @SerializedName("SyncFailed")
    SYNC_FAILED,
}
