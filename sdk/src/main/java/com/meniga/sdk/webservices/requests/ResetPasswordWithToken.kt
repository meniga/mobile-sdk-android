package com.meniga.sdk.webservices.requests

/**
 * Copyright 2018 Meniga Iceland Inc.
 * Created by agustk on 16.8.2018.
 */
data class ResetPasswordWithToken(
        @JvmField var resetPasswordToken: String,
        @JvmField var email: String,
        @JvmField var password: String
) : QueryRequestObject() {
    override fun getValueHash(): Long = hashCode().toLong()
}
