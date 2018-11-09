/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.offers

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createOffersValidatingMockWebServer
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaOffersSettingsApiTest : Spek({

    lateinit var server: ValidatingMockWebServer

    beforeEachTest {
        server = createOffersValidatingMockWebServer()
        server.start()
        val settings = MenigaSettings.Builder().endpoint(server.baseUrl()).build()
        MenigaSDK.init(settings)
    }

    afterEachTest {
        server.stop()
    }

    on("enabling offers") {
        server.enqueue(MockResponse().setResponseCode(204))

        val task = MenigaOffersSettings.enable().task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("POST")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/enable")
        }

        it("should retrieve proper data") {
            assertThat(task.isFaulted).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("disabling offers") {
        server.enqueue(MockResponse().setResponseCode(204))

        val task = MenigaOffersSettings.disable().task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("DELETE")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/enable")
        }

        it("should retrieve proper data") {
            assertThat(task.isFaulted).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("accepting terms and conditions") {
        server.enqueue(MockResponse().setResponseCode(204))

        val task = MenigaOffersSettings.acceptTermsAndConditions().task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("POST")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/acceptTermsAndConditions")
        }

        it("should retrieve proper data") {
            assertThat(task.isFaulted).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
