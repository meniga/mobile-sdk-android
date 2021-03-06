/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.offers.reimbursmentaccounts

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.jayway.jsonassert.JsonAssert
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createOffersValidatingMockWebServer
import com.meniga.sdk.models.offers.reimbursementaccounts.*
import com.meniga.sdk.utils.mockResponse
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaReimbursementAccountsApiTest : Spek({

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

    describe("fetching reimbursement accounts") {
        lateinit var result: MenigaReimbursementAccountPage

        beforeEachTest {
            server.enqueue(mockResponse("reimbursement_accounts_get.json"))
            val task = MenigaReimbursementAccount.fetch().task
            task.waitForCompletion()
            result = task.result
        }

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/reimbursementAccounts")
                    .hasParameter("includeInactive", "false")
        }

        it("should retrieve proper data") {
            assertThat(result).hasSize(1)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("creating reimbursement Iceland") {
        lateinit var result: MenigaReimbursementAccount

        val menigaOfferAccountInfo = MenigaOfferAccountInfoIceland(
                "1234",
                "12",
                "123",
                "0000000000"
        )
        JsonAssert.with(menigaOfferAccountInfo.toJson())
                .assertThat("$.bankNumber", equalTo("1234"))
                .assertThat("$.ledger", equalTo("12"))
                .assertThat("$.bankAccountNumber", equalTo("123"))
                .assertThat("$.socialSecurityNumber", equalTo("0000000000"))

        beforeEachTest {
            server.enqueue(mockResponse("reimbursement_accounts_iceland_post.json").setResponseCode(201))
            val task = MenigaReimbursementAccount.create(
                    "Reimbursement account",
                    "Bank account ISL",
                    menigaOfferAccountInfo).task
            task.waitForCompletion()
            result = task.result
        }

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("POST")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/reimbursementAccounts")
            JsonAssert.with(recordedRequest.body.readUtf8())
                    .assertThat("$.name", equalTo("Reimbursement account"))
                    .assertThat("$.accountType", equalTo("Bank account ISL"))
                    // TODO Move from stringly-typed to strictly-typed
                    .assertThat("$.accountInfo", equalTo("""{"bankNumber":"1234","ledger":"12","bankAccountNumber":"123","socialSecurityNumber":"0000000000"}"""))
        }

        it("should retrieve proper data") {
            assertThat(result).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("creating reimbursement UK") {
        lateinit var result: MenigaReimbursementAccount

        val menigaOfferAccountInfo = MenigaOfferAccountInfoUK(
                "NatWest",
                "Karlsson",
                "112233",
                "12345678"
        )
        JsonAssert.with(menigaOfferAccountInfo.toJson())
                .assertThat("$.bankName", equalTo("NatWest"))
                .assertThat("$.accountName", equalTo("Karlsson"))
                .assertThat("$.sortcode", equalTo("112233"))
                .assertThat("$.bankAccountNumber", equalTo("12345678"))

        beforeEachTest {
            server.enqueue(mockResponse("reimbursement_accounts_uk_post.json").setResponseCode(201))
            val task = MenigaReimbursementAccount.create(
                    "Reimbursement account",
                    "Bank account UK",
                    menigaOfferAccountInfo).task
            task.waitForCompletion()
            result = task.result
        }

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("POST")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/reimbursementAccounts")
            JsonAssert.with(recordedRequest.body.readUtf8())
                    .assertThat("$.name", equalTo("Reimbursement account"))
                    .assertThat("$.accountType", equalTo("Bank account UK"))
                    .assertThat("$.accountInfo", equalTo("""{"bankName":"NatWest","accountName":"Karlsson","sortcode":"112233","bankAccountNumber":"12345678"}"""))
        }

        it("should retrieve proper data") {
            assertThat(result).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    describe("fetching reimbursement account by id") {
        lateinit var result: MenigaReimbursementAccount

        beforeEachTest {
            server.enqueue(mockResponse("reimbursement_accounts_get_by_id.json"))
            val task = MenigaReimbursementAccount.fetch(69).task
            task.waitForCompletion()
            result = task.result
        }

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/reimbursementAccounts/69")
        }

        it("should retrieve proper data") {
            result.run {
                assertThat(id).isEqualTo(69)
                assertThat(isActive).isTrue
                assertThat(isVerified).isTrue
                assertThat(name).isEqualTo("Reimbursement account")
                assertThat(accountType).isEqualTo("Bank account ISL")
                JsonAssert.with(accountInfo)
                        .assertThat("$.bankNumber", equalTo("1234"))
                        .assertThat("$.ledger", equalTo("12"))
                        .assertThat("$.bankAccountNumber", equalTo("123"))
                        .assertThat("$.socialSecurityNumber", equalTo("0000000000"))
            }
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
