/*
 * Copyright 2017-2019 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.user

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.jayway.jsonassert.JsonAssert
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createValidatingMockWebServer
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaUserApiTest : Spek({

    lateinit var server: ValidatingMockWebServer

    beforeEachTest {
        server = createValidatingMockWebServer()
        server.start()
        val settings = MenigaSettings.Builder().endpoint(server.baseUrl()).build()
        MenigaSDK.init(settings)
    }

    afterEachTest {
        server.stop()
    }

    on("updating user email") {
        server.enqueue(MockResponse().setResponseCode(204))
        val newEmail = "mario@super.com"
        val password = "password"

        val task = MenigaUser.updateEmail(newEmail, password).task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("PUT")
            assertThat(URI(recordedRequest.path)).hasPath("/v1/me/email")
            JsonAssert.with(recordedRequest.body.readUtf8())
                    .assertThat<String>("$.newEmail", equalTo("mario@super.com"))
                    .assertThat<String>("$.password", equalTo("password"))
        }

        it("should succeed") {
            assertThat(task.isFaulted).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("changing user password") {
        server.enqueue(MockResponse().setResponseCode(204))
        val currentPassword = "old-password"
        val newPassword = "new-password"

        val task = MenigaUser.changePassword(currentPassword, newPassword).task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("PUT")
            assertThat(URI(recordedRequest.path)).hasPath("/v1/me/password")
            JsonAssert.with(recordedRequest.body.readUtf8())
                    .assertThat<String>("$.currentPassword", equalTo("old-password"))
                    .assertThat<String>("$.newPassword", equalTo("new-password"))
        }

        it("should succeed") {
            assertThat(task.isFaulted).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("deleting user") {
        server.enqueue(MockResponse().setResponseCode(204))

        val task = MenigaUser.delete().task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("DELETE")
            assertThat(URI(recordedRequest.path)).hasPath("/v1/me")
        }

        it("should succeed") {
            assertThat(task.isFaulted).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
