@file:JvmName("SwaggerJsonExtensions")

package com.meniga.sdk.models

import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer
import com.atlassian.oai.validator.mockwebserver.ValidatingMockWebServer.Companion.create
import com.atlassian.oai.validator.whitelist.ValidationErrorsWhitelist
import com.atlassian.oai.validator.whitelist.rule.WhitelistRules.allOf
import com.atlassian.oai.validator.whitelist.rule.WhitelistRules.anyOf
import com.atlassian.oai.validator.whitelist.rule.WhitelistRules.messageContains
import com.atlassian.oai.validator.whitelist.rule.WhitelistRules.pathContains

fun createValidatingMockWebServer(): ValidatingMockWebServer =
        create(
                "/api/swagger.json",
                "/v1",
                ValidationErrorsWhitelist.create()
                        .withRule("BACKEND-2972 Ignore date-format violations", messageContains("is invalid against requested date format"))
                        .withRule("BACKEND-3025 Ignore ChallengeMetadataModel empty properties",
                                anyOf(
                                        allOf(
                                                pathContains("/challenges"),
                                                messageContains("/data/.*/typeData")
                                        ),
                                        allOf(
                                                pathContains("/challenges"),
                                                messageContains("/typeData")
                                        )
                                )
                        )
                        .withRule("BACKEND-3024 Ignore FeedItemModel properties missing from schema",
                                allOf(
                                        pathContains("/feed"),
                                        messageContains("Object instance has properties which are not allowed by the schema"),
                                        anyOf(
                                                messageContains("actionText"),
                                                messageContains("eventTypeIdentifier"),
                                                messageContains("id"),
                                                messageContains("messageData")
                                        )
                                )
                        )
                        .withRule("BACKEND-3017 Ignore Transactions parsedData being arrays instead of objects",
                                allOf(
                                        pathContains("/transactions"),
                                        messageContains("does not match any allowed primitive type"),
                                        messageContains("parsedData")
                                )
                        )
        )
