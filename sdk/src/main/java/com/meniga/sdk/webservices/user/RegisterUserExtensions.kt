@file:JvmName("RegisterUserExtensions")

package com.meniga.sdk.webservices.user

import com.meniga.sdk.models.user.Registration

fun from(registration: Registration) =
        RegisterUser().apply {
            email = registration.email
            password = registration.password
            culture = registration.culture
            signupToken = registration.signupToken
        }
