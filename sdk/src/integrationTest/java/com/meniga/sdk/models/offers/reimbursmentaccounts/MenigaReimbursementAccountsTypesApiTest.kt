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
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
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

    describe("fetching reimbursement accounts types") {
        lateinit var result: List<MenigaReimbursementAccountType>

        beforeEachTest {
            server.enqueue(mockResponse("reimbursement_accounts_types_get.json"))
            val task = MenigaReimbursementAccountType.fetch(0, 5).task
            task.waitForCompletion()
            result = task.result
        }

        it("should make proper request") {
            val recordedRequest = server.takeRequest()
            assertThat(recordedRequest.method).isEqualTo("GET")
            assertThat(URI(recordedRequest.path))
                    .hasPath("/v1/reimbursementAccounts/types")
                    .hasParameter("skip", "0")
                    .hasParameter("take", "5")
        }

        it("should retrieve proper data") {
            assertThat(result).hasSize(1)
        }

        it("should validate against the spec") {
            server.assertNoValidations()
        }
    }
})
