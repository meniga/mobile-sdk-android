package com.atlassian.oai.validator.mockwebserver

import com.atlassian.oai.validator.SwaggerRequestResponseValidator

interface ValidatorsCache {
    fun getOrPut(swaggerJsonUrl: String,
                 overriddenBasePath: String,
                 validatorFactory: () -> SwaggerRequestResponseValidator): SwaggerRequestResponseValidator
}

internal object StaticValidatorsCache : ValidatorsCache {
    private val validators: MutableMap<String, SwaggerRequestResponseValidator> = mutableMapOf()

    override fun getOrPut(swaggerJsonUrl: String,
                          overriddenBasePath: String,
                          validatorFactory: () -> SwaggerRequestResponseValidator): SwaggerRequestResponseValidator {
        return validators.getOrPut(swaggerJsonUrl + overriddenBasePath, validatorFactory)
    }
}
