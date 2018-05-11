/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.sync

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.jayway.jsonassert.JsonAssert
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createValidatingMockWebServer
import com.meniga.sdk.models.user.ChallengeContentType
import com.meniga.sdk.utils.mockResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaSyncApiTest : Spek({

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

    fun assertThatSyncStatusWasChecked() {
        val recordedRequest = server.takeRequest()
        assertThat(recordedRequest.method).isEqualTo("GET")
        assertThat(URI(recordedRequest.path))
                .hasPath("/v1/sync")
                .hasNoParameters()
    }

    on("starting sync with timeout") {
        server.enqueue(mockResponse("syncstatus.json"))
        server.enqueue(mockResponse("syncresponse.json"))

        val task = MenigaSync.start(1000).task
        task.waitForCompletion()

        it("should check status first") {
            assertThatSyncStatusWasChecked()
        }

        it("should start sync with timeout") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("POST")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/sync")
                    .hasNoParameters()
            JsonAssert.with(recordedRequest.body.readUtf8())
                    .assertThat("$.waitForCompleteMilliseconds", equalTo(1000))
        }

        it("should return a proper object") {
            val sync = task.result
            assertThat(sync.numNewTransactions).isEqualTo(230)
            val realmSyncResponse = sync.realmSyncResponses!![0]
            assertThat(realmSyncResponse.accountSyncStatuses).hasSize(1)
            assertThat(realmSyncResponse.authenticationChallenge.contentType).isEqualTo(ChallengeContentType.TEXT)
            assertThat(realmSyncResponse.isSyncDone).isTrue()
            assertThat(realmSyncResponse.organizationBankCode).isEqualTo("EB")
            assertThat(realmSyncResponse.organizationId).isEqualTo(144)
            assertThat(realmSyncResponse.organizationName).isEqualTo("ExampleBank")
            assertThat(realmSyncResponse.realmCredentialsDisplayName).isEqualTo("string")
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("syncing all realms while still in progress") {
        server.enqueue(mockResponse("syncstatusinprogress.json"))
        server.enqueue(mockResponse("syncresponse.json"))

        val task = MenigaSync.syncRealms(1000, null).task
        task.waitForCompletion()

        it("should make request to get sync status") {
            assertThatSyncStatusWasChecked()
        }

        it("should make request to get an ongoing sync") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/sync/0")
                    .hasNoParameters()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("syncing realm by user id") {
        val userId = 44L
        server.enqueue(mockResponse("syncstatus.json"))
        server.enqueue(mockResponse("syncresponse.json"))

        val task = MenigaSync.syncRealm(userId, 1000, null).task
        task.waitForCompletion()

        it("should make request to get sync status") {
            assertThatSyncStatusWasChecked()
        }

        it("should make request to sync realm by user id") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("POST")
            assertThat(URI(recordedRequest.path)).hasPath("/v1/sync/realm/44")
            JsonAssert.with(recordedRequest.body.readUtf8())
                    .assertThat("$.waitForCompleteMilliseconds", equalTo(1000))
        }

        it("should return proper data") {
            assertThat(task.result).isNotNull()
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("checking sync status") {
        server.enqueue(mockResponse("syncstatus.json"))

        val task = MenigaSync.getSyncStatus().task
        task.waitForCompletion()

        it("should make request to get sync status") {
            assertThatSyncStatusWasChecked()
        }

        it("should return proper sync status") {
            task.result.hasCompletedSyncSession
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
