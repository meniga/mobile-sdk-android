package com.meniga.sdk.helpers

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertFailsWith

@RunWith(JUnitPlatform::class)
object PaginationUtilsTest : Spek({

    val skipAndTakeData = arrayOf(
            data(0, 0, 0, 0),
            data(1, 0, 0, 0),
            data(0, 10, 0, 10),
            data(1, 10, 10, 10))

    on("converting page=%s and itemsPerPage=%s to skip and take", with = *skipAndTakeData) { page, itemsPerPage, expectedSkip, expectedTake ->
        it("should return skip=$expectedSkip and take=$expectedTake") {
            assertThat(toSkipAndTake(page, itemsPerPage)).isEqualTo(Pair(expectedSkip, expectedTake))
        }
    }

    val failingSkipAndTakeData = arrayOf(
            data(-1, 0, IllegalArgumentException::class),
            data(0, -1, IllegalArgumentException::class))

    on("converting page=%s and itemsPerPage=%s to skip and take", with = *failingSkipAndTakeData) { page, itemsPerPage, exceptionClass ->
        it("should throw $exceptionClass") {
            assertFailsWith(exceptionClass) {
                toSkipAndTake(page, itemsPerPage)
            }
        }
    }

    val hasMoreData = arrayOf(
            data(0, 0, 0, false),
            data(10, 0, 10, false),
            data(11, 0, 10, true),
            data(11, 1, 10, false))

    on("checking if there is more data for totalCount=%s, page=%s and itemsPerPage=%s", with = *hasMoreData) { totalCount, page, itemsPerPage, expected ->
        it("should return $expected") {
            assertThat(hasMoreData(totalCount, page, itemsPerPage)).isEqualTo(expected)
        }
    }

    val failingHasMoreData = arrayOf(
            data(-1, 0, 0, IllegalArgumentException::class),
            data(0, -1, 0, IllegalArgumentException::class),
            data(0, 0, -1, IllegalArgumentException::class))

    on("checking if there is more data for totalCount=%s, page=%s and itemsPerPage=%s", with = *failingHasMoreData) { totalCount, page, itemsPerPage, exceptionClass ->
        it("should throw $exceptionClass") {
            assertFailsWith(exceptionClass) {
                hasMoreData(totalCount, page, itemsPerPage)
            }
        }
    }
})
