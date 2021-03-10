/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.feed

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createValidatingMockWebServer
import com.meniga.sdk.models.transactions.MenigaTransaction
import com.meniga.sdk.utils.mockResponse
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Root
import org.spekframework.spek2.style.specification.describe
import java.net.URI

fun Root.setupMockWebServer() {
    val dateFrom by memoized { DateTime.parse("2018-01-01") }
    val dateTo by memoized { DateTime.parse("2018-02-01") }
    val server by memoized(
            factory = {
                createValidatingMockWebServer().apply {
                    start()
                    val settings = MenigaSettings.Builder().endpoint(baseUrl()).build()
                    MenigaSDK.init(settings)
                }
            },
            destructor = { it.stop() }
    )

    val feed by memoized(
            factory = {
                server.enqueue(mockResponse("feed.json"))
                val task = MenigaFeed.fetch(dateFrom, dateTo, 1, 10).task
                task.waitForCompletion()
                server.takeRequest()
                task.result
            }
    )
}

@RunWith(JUnitPlatform::class)
object MenigaFeedApiTest : Spek({
    setupMockWebServer()

    val dateFrom: DateTime by memoized()
    val dateTo: DateTime by memoized()
    val server: ValidatingMockWebServer by memoized()

    describe("fetching feed in date range") {
        val task by memoized { MenigaFeed.fetch(dateFrom, dateTo).task }

        beforeEachTest {
            server.enqueue(mockResponse("feed.json"))
            task.waitForCompletion()
        }

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasNoParameter("take")
                    .hasNoParameter("skip")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
                    .hasParameter("include", "Account,Merchant")
        }

        it("should retrieve proper data") {
            val feed = task.result
            assertThat(feed).isNotNull
            assertThat(feed.size).isEqualTo(10)
        }

        it("should contain accounts and merchant models inlined") {
            val transactions = task.result.filterIsInstance(MenigaTransaction::class.java)
            assertThat(transactions.any { it.account != null })
            assertThat(transactions.any { it.merchant != null })
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    arrayOf(
            Pair(0, true),
            Pair(1, false)
    ).forEach { (page, expectedHasMorePages) ->
        describe("fetching a feed in date range and page %s") {
            lateinit var result: MenigaFeed

            beforeEachTest {
                server.enqueue(mockResponse("feed.json"))
                val task = MenigaFeed.fetch(dateFrom, dateTo, page, 10).task
                task.waitForCompletion()
                result = task.result
            }

            it("should make a proper request") {
                val recordedRequest = server.takeRequest()
                assertThat(recordedRequest.method).isEqualTo("GET")
                assertThat(URI(recordedRequest.path))
                        .hasPath("/v1/feed")
                        .hasParameter("skip", (page * 10).toString())
                        .hasParameter("take", "10")
                        .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                        .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
                        .hasParameter("include", "Account,Merchant")
            }

            it("should retrieve data with hasMorePages=$expectedHasMorePages") {
                assertThat(result.hasMorePages()).isEqualTo(expectedHasMorePages)
            }
            it("should have more data") {
                assertThat(result.hasMoreData()).isTrue()
            }

            it("should validate against the spec") {
                server.assertNoValidations()
            }
        }
    }

    describe("appending days") {
        val feed: MenigaFeed by memoized()
        lateinit var result: MenigaFeed

        beforeEachTest {
            server.enqueue(mockResponse("feed.json"))
            val appendTask = feed.appendDays(1).task
            appendTask.waitForCompletion()
            result = appendTask.result
        }

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2017-12-31T00:00:00.000Z")
                    .hasParameter("dateTo", "2017-12-31T23:59:59.999Z")
                    .hasParameter("include", "Account,Merchant")
        }

        it("should retrieve proper data") {
            assertThat(result.hasMoreData()).isTrue()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("appending next page") {
        val feed: MenigaFeed by memoized()
        lateinit var result: MenigaFeed

        beforeEachTest {
            server.enqueue(mockResponse("feed.json"))
            val task = feed.appendNextPage().task
            task.waitForCompletion()
            result = task.result
        }

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "20")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
                    .hasParameter("include", "Account,Merchant")
        }
        it("should append to current feed") {
            assertThat(feed.size).isGreaterThan(result.size)
            assertThat(feed.page).isEqualTo(2)
        }
        it("should return next page") {
            assertThat(result.size).isEqualTo(10)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("fetching next page") {
        val feed: MenigaFeed by memoized()
        lateinit var result: MenigaFeed

        beforeEachTest {
            server.enqueue(mockResponse("feed.json"))
            val task = feed.nextPage().task
            task.waitForCompletion()
            result = task.result
        }

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "20")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
                    .hasParameter("include", "Account,Merchant")
        }
        it("should not append to current feed") {
            assertThat(feed.size).isEqualTo(10)
            assertThat(feed.page).isEqualTo(2)
        }
        it("should return next page") {
            assertThat(result.size).isEqualTo(10)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("fetching previous page") {
        val feed: MenigaFeed by memoized()
        lateinit var result: MenigaFeed

        beforeEachTest {
            server.enqueue(mockResponse("feed.json"))
            val task = feed.prevPage().task
            task.waitForCompletion()
            result = task.result
        }

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
                    .hasParameter("include", "Account,Merchant")
        }
        it("should not append to current feed") {
            assertThat(feed.size).isEqualTo(10)
            assertThat(feed.page).isEqualTo(0)
        }
        it("should return next page") {
            assertThat(result.size).isEqualTo(10)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
