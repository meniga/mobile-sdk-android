/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.feed

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createValidatingMockWebServer
import com.meniga.sdk.utils.mockResponse
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import org.joda.time.DateTime
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaFeedApiTest : Spek({

    lateinit var server: ValidatingMockWebServer
    lateinit var dateFrom: DateTime
    lateinit var dateTo: DateTime

    beforeEachTest {
        server = createValidatingMockWebServer()
        server.start()
        val settings = MenigaSettings.Builder().endpoint(server.baseUrl()).build()
        MenigaSDK.init(settings)
        dateFrom = DateTime.parse("2018-01-01")
        dateTo = DateTime.parse("2018-02-01")
    }

    afterEachTest {
        server.stop()
    }

    fun fetchFeed(): MenigaFeed {
        server.enqueue(mockResponse("feed.json"))
        val task = MenigaFeed.fetch(dateFrom, dateTo, 1, 10).task
        task.waitForCompletion()
        server.takeRequest()
        return task.result
    }

    on("fetching feed in date range") {
        server.enqueue(mockResponse("feed.json"))

        val task = MenigaFeed.fetch(dateFrom, dateTo).task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasNoParameter("take")
                    .hasNoParameter("skip")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
        }

        it("should retrieve proper data") {
            val feed = task.result
            assertThat(feed).isNotNull
            assertThat(feed.size).isEqualTo(10)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    val pageData = arrayOf(
            data(0, true),
            data(1, false)
    )

    on("fetching a feed in date range and page %s", with = *pageData) { page, expectedHasMorePages ->
        server.enqueue(mockResponse("feed.json"))

        val task = MenigaFeed.fetch(dateFrom, dateTo, page, 10).task
        task.waitForCompletion()

        val result = task.result

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", (page * 10).toString())
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
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

    on("appending days") {
        val feed = fetchFeed()
        server.enqueue(mockResponse("feed.json"))

        val appendTask = feed.appendDays(1).task
        appendTask.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2017-12-31T00:00:00.000Z")
                    .hasParameter("dateTo", "2017-12-31T23:59:59.999Z")
        }

        it("should retrieve proper data") {
            assertThat(appendTask.result.hasMoreData()).isTrue()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("appending next page") {
        val feed = fetchFeed()
        server.enqueue(mockResponse("feed.json"))

        val task = feed.appendNextPage().task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "20")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
        }
        it("should append to current feed") {
            assertThat(feed.size).isGreaterThan(task.result.size)
            assertThat(feed.page).isEqualTo(2)
        }
        it("should return next page") {
            assertThat(task.result.size).isEqualTo(10)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("fetching next page") {
        val feed = fetchFeed()
        server.enqueue(mockResponse("feed.json"))

        val task = feed.nextPage().task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "20")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
        }
        it("should not append to current feed") {
            assertThat(feed.size).isEqualTo(10)
            assertThat(feed.page).isEqualTo(2)
        }
        it("should return next page") {
            assertThat(task.result.size).isEqualTo(10)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("fetching previous page") {
        val feed = fetchFeed()
        server.enqueue(mockResponse("feed.json"))

        val task = feed.prevPage().task
        task.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000Z")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000Z")
        }
        it("should not append to current feed") {
            assertThat(feed.size).isEqualTo(10)
            assertThat(feed.page).isEqualTo(0)
        }
        it("should return next page") {
            assertThat(task.result.size).isEqualTo(10)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
