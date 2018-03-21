package com.atlassian.oai.validator.mockwebserver

import com.atlassian.oai.validator.report.ValidationReport
import com.atlassian.oai.validator.report.ValidationReportFormatter
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertFalse
import org.junit.rules.ExternalResource

class ValidatingMockWebServer(
        swaggerJsonUrl: String,
        private val overriddenBasePath: String) : ExternalResource() {

    private val validatingQueueDispatcher: ValidatingQueueDispatcher = ValidatingQueueDispatcher(swaggerJsonUrl, overriddenBasePath)
    private val mockWebServer = MockWebServer().apply {
        setDispatcher(validatingQueueDispatcher)
    }

    fun enqueue(response: MockResponse) = mockWebServer.enqueue(response)

    fun takeRequest(): RecordedRequest = mockWebServer.takeRequest()

    fun baseUrl(): HttpUrl = mockWebServer.url(overriddenBasePath)

    override fun before() {
        mockWebServer.start()
    }

    override fun after() {
        assertNoValidations()
        mockWebServer.shutdown()
        validatingQueueDispatcher.cleanValidations()
    }

    private fun assertNoValidations() {
        validatingQueueDispatcher.validationReports
                .takeIf { it.isNotEmpty() }
                ?.reduce { accumulator, validationReport -> accumulator.merge(validationReport) }
                ?.also {
                    it.messages
                            .filter { it.level != ValidationReport.Level.ERROR }
                            .forEach { System.err.println(it.message) }
                    assertFalse(ValidationReportFormatter.format(it), it.hasErrors())
                }
    }

    companion object {
        @JvmStatic
        fun create(swaggerJsonUrl: String, overriddenBasePath: String = ""): ValidatingMockWebServer =
                ValidatingMockWebServer(swaggerJsonUrl, overriddenBasePath)
    }
}
