/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.offers.reimbursmentaccounts

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.jayway.jsonassert.JsonAssert
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createOffersValidatingMockWebServer
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaOfferAccountInfo
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaOfferAccountInfoIceland
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccount
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

    on("fetching reimbursement accounts") {
        server.enqueue(mockResponse("reimbursement_accounts_get.json"))

        val task = MenigaReimbursementAccount.fetch().task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/reimbursementAccounts")
                    .hasParameter("includeInactive", "false")
        }

        it("should retrieve proper data") {
            assertThat(task.result).hasSize(1)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("creating reimbursement") {
        server.enqueue(mockResponse("reimbursement_accounts_post.json").setResponseCode(201))

        val menigaOfferAccountInfo = MenigaOfferAccountInfoIceland(
                "1234",
                "12",
                "123",
                "0000000000"
        )
        val task = MenigaReimbursementAccount.create(
                "Reimbursement account",
                "Bank account ISL",
                menigaOfferAccountInfo).task
        task.waitForCompletion()

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
            val reimbursementAccount = task.result
            assertThat(reimbursementAccount).isNotNull
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }

    on("fetching reimbursement account by id") {
        server.enqueue(mockResponse("reimbursement_accounts_get_by_id.json"))

        val task = MenigaReimbursementAccount.fetch(69).task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/reimbursementAccounts/69")
        }

        it("should retrieve proper data") {
            task.result.run {
                assertThat(id).isEqualTo(69)
                assertThat(isActive).isTrue()
                assertThat(isVerified).isTrue()
                assertThat(name).isEqualTo("Reimbursement account")
                assertThat(accountType).isEqualTo("Bank account ISL")
                assertThat(accountInfo).isEqualTo("")
            }
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
