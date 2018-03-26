package com.meniga.sdk.webservices.account

import com.google.gson.annotations.SerializedName

enum class AccountAuthorizationTypeName {
    @SerializedName("None") NONE,
    @SerializedName("External") EXTERNAL,
    @SerializedName("Internal") INTERNAL,
    @SerializedName("ExternalMultifactor") EXTERNAL_MULTIFACTOR
}
