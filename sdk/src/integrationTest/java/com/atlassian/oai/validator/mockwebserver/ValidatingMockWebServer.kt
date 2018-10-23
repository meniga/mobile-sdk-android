package com.atlassian.oai.validator.mockwebserver

import com.atlassian.oai.validator.report.ValidationReport
import com.atlassian.oai.validator.whitelist.ValidationErrorsWhitelist
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertFalse
import org.junit.rules.ExternalResource

class ValidatingMockWebServer(
        swaggerJsonUrl: String,
        private val overriddenBasePath: String,
        whitelist: ValidationErrorsWhitelist) : ExternalResource() {

    private val validatingQueueDispatcher: ValidatingQueueDispatcher = ValidatingQueueDispatcher(
            swaggerJsonUrl,
            overriddenBasePath,
            whitelist)
    private val mockWebServer = MockWebServer().apply {
        setDispatcher(validatingQueueDispatcher)
    }

    fun enqueue(response: MockResponse) = mockWebServer.enqueue(response)

    fun takeRequest(): RecordedRequest = mockWebServer.takeRequest()

    fun baseUrl(): HttpUrl = mockWebServer.url(overriddenBasePath)

    fun start() {
        mockWebServer.start()
    }

    fun stop() {
        mockWebServer.shutdown()
        validatingQueueDispatcher.cleanValidations()
    }

    override fun before() = start()

    override fun after() {
        assertNoValidations()
        stop()
    }

    fun assertNoValidations() {
        validatingQueueDispatcher.validationReports
                .takeIf { it.isNotEmpty() }
                ?.reduce { accumulator, validationReport -> accumulator.merge(validationReport) }
                ?.also {
                    it.messages
                            .filter { message -> message.level != ValidationReport.Level.ERROR }
                            .forEach { message -> System.err.println("[${message.level}] ${message.message}") }
                    assertFalse(format(it), it.hasErrors())
                }
    }

    private fun format(report: ValidationReport): String =
            """
                |Validation failed.
                |${report.messages
                    .filter { it.level == ValidationReport.Level.ERROR }
                    .joinToString(separator = "") { formatMessage(it) }}
            """.trimMargin()

    private fun formatMessage(msg: ValidationReport.Message): String =
            """
                |[${msg.level}] ${msg.message.replace("\n", "\n\t")}
                |${msg.additionalInfo
                    .filterNotNull()
                    .joinToString(separator = "\t* ") { it.replace("\n", "\n\t\t") }}
            """.trimMargin()

    companion object {
        @JvmStatic
        fun create(
                swaggerJsonUrl: String,
                overriddenBasePath: String = "",
                whitelist: ValidationErrorsWhitelist): ValidatingMockWebServer =
                ValidatingMockWebServer(swaggerJsonUrl, overriddenBasePath, whitelist)
    }
}
