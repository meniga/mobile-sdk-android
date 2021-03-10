package com.meniga.sdk.helpers

import org.assertj.core.api.Assertions.assertThat
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertFailsWith

@RunWith(JUnitPlatform::class)
object PaginationUtilsTest : Spek({

    arrayOf(
            listOf(0, 0, 0, 0),
            listOf(1, 0, 0, 0),
            listOf(0, 10, 0, 10),
            listOf(1, 10, 10, 10),
            listOf(2, 10, 20, 10)
    ).forEach { (page, itemsPerPage, expectedSkip, expectedTake) ->
        describe("converting page=$page and itemsPerPage=$itemsPerPage to skip and take") {
            it("should return skip=$expectedSkip and take=$expectedTake") {
                assertThat(toSkipAndTake(page, itemsPerPage)).isEqualTo(Pair(expectedSkip, expectedTake))
            }
        }
    }

    arrayOf(
            Triple(-1, 0, IllegalArgumentException::class),
            Triple(0, -1, IllegalArgumentException::class)
    ).forEach { (page, itemsPerPage, exceptionClass) ->
        describe("converting page=$page and itemsPerPage=$itemsPerPage to skip and take") {
            it("should throw $exceptionClass") {
                assertFailsWith(exceptionClass) {
                    toSkipAndTake(page, itemsPerPage)
                }
            }
        }
    }

    data class TestData(
            val totalCount: Int,
            val page: Int,
            val itemsPerPage: Int
    )

    arrayOf(
            Pair(TestData(0, 0, 0), false),
            Pair(TestData(10, 0, 10), false),
            Pair(TestData(11, 0, 10), true),
            Pair(TestData(11, 1, 10), false)
    ).forEach { (data, expected) ->
        describe("checking if there is more data for $data") {
            it("should return $expected") {
                assertThat(hasMoreData(data.totalCount, data.page, data.itemsPerPage)).isEqualTo(expected)
            }
        }
    }

    arrayOf(
            Pair(TestData(-1, 0, 0), IllegalArgumentException::class),
            Pair(TestData(0, -1, 0), IllegalArgumentException::class),
            Pair(TestData(0, 0, -1), IllegalArgumentException::class)
    ).forEach { (data, exceptionClass) ->
        describe("checking if there is more data for $data") {
            it("should throw $exceptionClass") {
                assertFailsWith(exceptionClass) {
                    hasMoreData(data.totalCount, data.page, data.itemsPerPage)
                }
            }
        }
    }
})
