/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.feed

import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.utils.mockResponse
import okhttp3.mockwebserver.MockWebServer
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

    lateinit var server: MockWebServer
    lateinit var dateFrom: DateTime
    lateinit var dateTo: DateTime

    beforeEachTest {
        server = MockWebServer()
        server.start()
        val baseUrl = server.url("/v1")
        val settings = MenigaSettings.Builder().endpoint(baseUrl).build()
        MenigaSDK.init(settings)
        dateFrom = DateTime.parse("2018-01-01")
        dateTo = DateTime.parse("2018-02-01")
    }

    afterEachTest {
        server.shutdown()
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
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000")
        }

        it("should retrieve proper data") {
            val feed = task.result
            assertThat(feed).isNotNull
            assertThat(feed.size).isEqualTo(12)
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
                    .hasParameter("dateFrom", "2018-01-01T00:00:00.000")
                    .hasParameter("dateTo", "2018-02-01T00:00:00.000")
        }

        it("should retrieve data with hasMorePages=$expectedHasMorePages") {
            assertThat(result.hasMorePages()).isEqualTo(expectedHasMorePages)
        }
        it("should have more data") {
            assertThat(result.hasMoreData()).isTrue()
        }
    }

    on("appending days") {
        server.enqueue(mockResponse("feed.json"))
        val task = MenigaFeed.fetch(dateFrom, dateTo, 1, 10).task
        task.waitForCompletion()
        server.takeRequest()
        server.enqueue(mockResponse("feed.json"))

        val appendTask = task.result.appendDays(1).task
        appendTask.waitForCompletion()

        it("should make a proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/feed")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "10")
                    .hasParameter("dateFrom", "2017-12-31T00:00:00.000")
                    .hasParameter("dateTo", "2017-12-31T23:59:59.999")
        }

        it("should retrieve proper data") {
            val result = task.result
            assertThat(result.hasMoreData()).isTrue()
        }
    }
})
