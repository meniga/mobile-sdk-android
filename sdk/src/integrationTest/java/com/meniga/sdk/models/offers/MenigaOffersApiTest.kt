/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.offers

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createOffersValidatingMockWebServer
import com.meniga.sdk.utils.mockResponse
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaOffersApiTest : Spek({

    lateinit var server: ValidatingMockWebServer

    beforeGroup {
        server = createOffersValidatingMockWebServer()
        server.start()
        val settings = MenigaSettings.Builder().endpoint(server.baseUrl()).build()
        MenigaSDK.init(settings)
    }

    afterGroup {
        server.stop()
    }

    on("fetching offers") {
        server.enqueue(mockResponse("offers_get.json"))

        val task = MenigaOffer.fetch(0, 10).task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "10")
                    .hasParameter("filter.states", "all")
                    .hasParameter("filter.expiredWithCashBackOnly", "false")
        }

        it("should retrieve proper data") {
            val offers = task.result
            assertThat(offers).isNotNull
            assertThat(offers.size).isEqualTo(4)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("fetching offer by id") {
        server.enqueue(mockResponse("offers_get_by_id.json"))

        val task = MenigaOffer.fetch(42).task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/42")
        }

        it("should retrieve proper data") {
            val offer = task.result
            assertThat(offer).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("fetching offer by token") {
        server.enqueue(mockResponse("offers_get_by_token.json"))

        val task = MenigaOffer.fetch("token").task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/token")
        }

        it("should retrieve proper data") {
            val offer = task.result
            assertThat(offer).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("fetching merchant locations") {
        server.enqueue(mockResponse("offers_get_merchant_locations.json"))

        val task = MenigaOffer.fetchNearbyMerchantLocationsById(42, 52.25, 21.0, 10.0, 5).task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/42/merchantLocations")
                    .hasParameter("latitude", "52.25")
                    .hasParameter("longitude", "21.0")
                    .hasParameter("radiusKm", "10.0")
                    .hasParameter("limitLocations", "5")
        }

        it("should retrieve proper data") {
            val merchants = task.result
            assertThat(merchants).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("fetching redemptions") {
        server.enqueue(mockResponse("offers_get_redemptions.json"))

        val task = MenigaOffer.fetchRedemptionsById(42).task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/42/redemptions")
        }

        it("should retrieve proper data") {
            val redemptions = task.result
            assertThat(redemptions).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("setting offer as seen") {
        server.enqueue(MockResponse().setResponseCode(204))

        val task = MenigaOffer.seen(42).task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("POST")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/42/seen")
        }

        it("should retrieve proper data") {
            assertThat(task.isFaulted).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("activating offer by token") {
        server.enqueue(mockResponse("offers_activate_by_token.json"))

        val task = MenigaOffer.activateByToken("token").task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("POST")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/token/activate")
        }

        it("should retrieve proper data") {
            assertThat(task.isFaulted).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("fetching similar brand spending") {
        server.enqueue(mockResponse("offers_get_similar_brand_spending.json"))

        val task = MenigaOffer.fetchSimilarBrandSpendingDetailsById(42).task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/42/similarBrandSpending")
        }

        it("should retrieve proper data") {
            val spending = task.result
            assertThat(spending).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    given("an offer") {

        lateinit var offer: MenigaOffer

        beforeGroup {
            server.enqueue(mockResponse("offers_get_by_id.json"))
            val task = MenigaOffer.fetch(42).task
            task.waitForCompletion()
            server.takeRequest()
            offer = task.result
        }

        on("activating offer") {
            server.enqueue(mockResponse("offers_activate_by_id.json"))
            val task = offer.activate().task.apply { waitForCompletion() }

            it("should make proper request") {
                val recordedRequest = server.takeRequest()
                assertThat(recordedRequest.method).isEqualTo("POST")
                assertThat(URI(recordedRequest.path))
                        .hasPath("/v1/offers/30/activate")
            }

            it("should retrieve proper data") {
                assertThat(task.isFaulted).isFalse()
            }
        }

        on("declining offer") {
            server.enqueue(mockResponse("offers_decline_by_id.json"))
            val task = offer.decline().task.apply { waitForCompletion() }

            it("should make proper request") {
                val recordedRequest = server.takeRequest()
                assertThat(recordedRequest.method).isEqualTo("DELETE")
                assertThat(URI(recordedRequest.path))
                        .hasPath("/v1/offers/30/activate")
            }

            it("should retrieve proper data") {
                assertThat(task.isFaulted).isFalse()
            }
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
