/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.offers

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createOffersValidatingMockWebServer
import com.meniga.sdk.models.offers.redemptions.MenigaRedemptions
import com.meniga.sdk.providers.tasks.Task
import com.meniga.sdk.utils.mockResponse
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaOffersApiTest : Spek({

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

    describe("fetching offers") {
        lateinit var offers: MenigaOfferPage

        beforeEachTest {
            server.enqueue(mockResponse("offers_get.json"))
            val task = MenigaOffer.fetch(10, 10).task
            task.waitForCompletion()
            offers = task.result
        }

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers")
                    .hasParameter("skip", "10")
                    .hasParameter("take", "10")
                    .hasParameter("filter.states", "all")
                    .hasNoParameter("filter.offerIds")
                    .hasParameter("filter.expiredWithRedemptionsOnly", "false")
        }

        it("should retrieve proper data") {
            assertThat(offers).isNotNull
            assertThat(offers.size).isEqualTo(4)
            assertThat(offers.hasMorePages).isFalse()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("fetching offer by id") {
        lateinit var offer: MenigaOffer

        beforeEachTest {
            server.enqueue(mockResponse("offers_get_by_id.json"))
            val task = MenigaOffer.fetch(42).task
            task.waitForCompletion()
            offer = task.result
        }

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/42")
        }

        it("should retrieve proper data") {
            assertThat(offer).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("fetching offer by token") {
        lateinit var offer: MenigaOffer

        beforeEachTest {
            server.enqueue(mockResponse("offers_get_by_token.json"))
            val task = MenigaOffer.fetch("token").task
            task.waitForCompletion()
            offer = task.result
        }

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/token")
        }

        it("should retrieve proper data") {
            assertThat(offer).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("fetching merchant locations") {
        lateinit var merchants: MenigaOfferMerchantLocationPage
        beforeEachTest {
            server.enqueue(mockResponse("offers_get_merchant_locations.json"))
            val task = MenigaOffer.fetchNearbyMerchantLocationsById(42, 52.25, 21.0, 10.0, 5).task
            task.waitForCompletion()
            merchants = task.result
        }

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
            assertThat(merchants).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("fetching redemptions") {
        lateinit var redemptions: MenigaRedemptions
        beforeEachTest {
            server.enqueue(mockResponse("offers_get_redemptions.json"))
            val task = MenigaOffer.fetchRedemptionsById(42).task
            task.waitForCompletion()
            redemptions = task.result
        }

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/42/redemptions")
        }

        it("should retrieve proper data") {
            assertThat(redemptions).hasSize(1)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("setting offer as seen") {
        lateinit var task: Task<Void>

        beforeEachTest {
            server.enqueue(MockResponse().setResponseCode(204))
            task = MenigaOffer.seen(42).task
            task.waitForCompletion()
        }

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

    describe("activating offer by token") {
        lateinit var task: Task<Void>

        beforeEachTest {
            server.enqueue(mockResponse("offers_activate_by_token.json"))
            task = MenigaOffer.activateByToken("token").task
            task.waitForCompletion()
        }

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

    describe("fetching similar brand spending") {
        lateinit var spending: MenigaSimilarBrandSpendingDetails

        beforeEachTest {
            server.enqueue(mockResponse("offers_get_similar_brand_spending.json"))
            val task = MenigaOffer.fetchSimilarBrandSpendingDetailsById(42).task
            task.waitForCompletion()
            spending = task.result
        }

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/offers/42/similarBrandSpending")
        }

        it("should retrieve proper data") {
            assertThat(spending).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("an offer") {
        lateinit var offer: MenigaOffer

        beforeEachTest {
            server.enqueue(mockResponse("offers_get_by_id.json"))
            val task = MenigaOffer.fetch(42).task
            task.waitForCompletion()
            server.takeRequest()
            offer = task.result
        }

        describe("activating offer") {
            lateinit var task: Task<Void>

            beforeEachTest {
                server.enqueue(mockResponse("offers_activate_by_id.json"))
                task = offer.activate().task.apply { waitForCompletion() }
            }

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

        describe("declining offer") {
            lateinit var task: Task<Void>

            beforeEachTest {
                server.enqueue(mockResponse("offers_decline_by_id.json"))
                task = offer.decline().task.apply { waitForCompletion() }
            }

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
