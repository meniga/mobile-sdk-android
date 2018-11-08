/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.offers.redemptions

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createOffersValidatingMockWebServer
import com.meniga.sdk.utils.mockResponse
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.joda.time.DateTime
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaRedemptionsApiTest : Spek({

    lateinit var server: ValidatingMockWebServer
    lateinit var dateFrom: DateTime
    lateinit var dateTo: DateTime

    beforeEachTest {
        server = createOffersValidatingMockWebServer()
        server.start()
        val settings = MenigaSettings.Builder().endpoint(server.baseUrl()).build()
        MenigaSDK.init(settings)
        dateFrom = DateTime.parse("2018-01-01")
        dateTo = DateTime.parse("2018-02-01")
    }

    afterEachTest {
        server.stop()
    }

    on("fetching redemptions") {
        server.enqueue(mockResponse("redemptions_get.json"))

        val task = MenigaRedemptions.fetch(0, 5, dateFrom, dateTo).task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/redemptions")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "5")
        }

        it("should retrieve proper data") {
            assertThat(task.result).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
