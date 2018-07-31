package com.meniga.sdk.webservices.user

import com.meniga.sdk.webservices.requests.QueryRequestObject

data class BeginRegistration(val email: String) : QueryRequestObject() {
    override fun getValueHash(): Long = hashCode().toLong()
}
