/*
 * Copyright 2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.models.offers.reimbursmentaccounts

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.meniga.sdk.MenigaSDK
import com.meniga.sdk.MenigaSettings
import com.meniga.sdk.models.createOffersValidatingMockWebServer
import com.meniga.sdk.models.offers.reimbursementaccounts.MenigaReimbursementAccountType
import com.meniga.sdk.utils.mockResponse
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.net.URI

@RunWith(JUnitPlatform::class)
object MenigaReimbursementAccountsTypesApiTest : Spek({

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

    on("fetching reimbursement accounts types") {
        server.enqueue(mockResponse("reimbursement_accounts_types_get.json"))

        val task = MenigaReimbursementAccountType.fetch(0, 5).task
        task.waitForCompletion()

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/reimbursementAccounts/types")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "5")
        }

        it("should retrieve proper data") {
            val reimbursementAccountTypes = task.result
            assertThat(reimbursementAccountTypes).hasSize(1)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
