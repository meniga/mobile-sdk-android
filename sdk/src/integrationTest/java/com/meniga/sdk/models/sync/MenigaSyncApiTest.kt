/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.sync

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.jayway.jsonassert.JsonAssert
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.helpers.MenigaDecimal
import com.meniga.sdk.models.createValidatingMockWebServer
import com.meniga.sdk.models.user.ChallengeContentType
import com.meniga.sdk.utils.mockResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.joda.time.DateTime
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
            sync.realmSyncResponses!![0].run {
                assertThat(realmCredentialsId).isEqualTo(1550004040444)
                assertThat(realmCredentialsDisplayName).isEqualTo("string")
                assertThat(organizationId).isEqualTo(144)
                assertThat(organizationName).isEqualTo("ExampleBank")
                assertThat(organizationBankCode).isEqualTo("EB")
                assertThat(accountSyncStatuses).hasSize(1)
                accountSyncStatuses[0].run {
                    assertThat(accountId).isEqualTo(15567)
                    assertThat(balance).isEqualTo(MenigaDecimal(50000))
                    assertThat(limit).isEqualTo(MenigaDecimal(2000))
                    assertThat(transactionsProcessed).isEqualTo(230)
                    assertThat(totalTransactions).isEqualTo(5180)
                    assertThat(startDate).isEqualTo(DateTime.parse("2017-07-27T12:23:34.000Z"))
                    assertThat(endDate).isEqualTo(DateTime.parse("2017-07-27T12:23:34.000Z"))
                    assertThat(accountStatus).isEqualTo("Success")
                    assertThat(status).isEqualTo(AccountSyncResult.SUCCESS)
                }
                assertThat(authenticationChallenge.contentType).isEqualTo(ChallengeContentType.TEXT)
                assertThat(isSyncDone).isTrue()
                assertThat(realmId).isEqualTo(4)
                assertThat(realmSyncStartDate).isEqualTo(DateTime.parse("2020-03-12T15:17:51.000Z"))
                assertThat(realmSyncEndDate).isEqualTo(DateTime.parse("2020-03-13T15:17:51.000Z"))
                assertThat(status).isEqualTo(RealmSyncResult.SUCCESS)
            }
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

    on("syncing realm by realm user id") {
        val realmUserId = 44L
        server.enqueue(mockResponse("syncstatus.json"))
        server.enqueue(mockResponse("syncresponse.json"))

        val task = MenigaSync.syncRealm(realmUserId, 1000, null).task
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

    on("syncing realm by realm user id and a session token") {
        val sessionToken = "whatSessionTokenIsThis"
        server.enqueue(mockResponse("syncstatus.json"))
        server.enqueue(mockResponse("syncresponse.json"))

        val task = MenigaSync.syncRealm(0, sessionToken, 1000, null).task
        task.waitForCompletion()

        it("should make request to get sync status") {
            assertThatSyncStatusWasChecked()
        }

        it("Should include session token in request") {
            val recordedRequest = server.takeRequest()
            JsonAssert.with(recordedRequest.body.readUtf8())
                    .assertThat("$.sessionToken", equalTo(sessionToken))
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
