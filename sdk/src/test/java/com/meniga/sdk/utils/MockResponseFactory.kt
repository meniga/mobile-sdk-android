@file:JvmName("MockResponseFactory")

package com.meniga.sdk.utils

import okhttp3.mockwebserver.MockResponse

fun mockResponse(path: String): MockResponse =
        MockResponse().setBody(FileImporter.getJsonFileFromRaw(path))
