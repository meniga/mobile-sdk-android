package com.meniga.sdk.models.sync

import com.google.gson.annotations.SerializedName

enum class RealmSyncResult {
    @SerializedName("Success")
    SUCCESS,

    @SerializedName("SyncFailed")
    SYNC_FAILED,
}
