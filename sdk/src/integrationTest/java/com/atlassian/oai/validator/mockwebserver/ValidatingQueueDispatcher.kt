package com.atlassian.oai.validator.mockwebserver

import com.atlassian.oai.validator.SwaggerRequestResponseValidator
import com.atlassian.oai.validator.report.ValidationReport
import com.atlassian.oai.validator.whitelist.ValidationErrorsWhitelist
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import okhttp3.mockwebserver.RecordedRequest

internal class ValidatingQueueDispatcher(
        swaggerJsonUrl: String,
        overriddenBasePath: String,
        whitelist: ValidationErrorsWhitelist,
        validatorsCache: ValidatorsCache = StaticValidatorsCache) : QueueDispatcher() {

    private val validator: SwaggerRequestResponseValidator by lazy {
        validatorsCache.getOrPut(
                swaggerJsonUrl,
                overriddenBasePath,
                {
                    SwaggerRequestResponseValidator.createFor(swaggerJsonUrl)
                            .withBasePathOverride(overriddenBasePath)
                            .withWhitelist(whitelist)
                            .build()
                })
    }

    var validationReports: List<ValidationReport> = emptyList()
        private set

    override fun dispatch(request: RecordedRequest): MockResponse =
            super.dispatch(request).also { response ->
                validationReports += validator.validate(MockWebServerRequest(request), MockWebServerResponse(response))
            }

    fun cleanValidations() {
        validationReports = emptyList()
    }
}
