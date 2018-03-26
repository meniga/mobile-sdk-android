/*
 * Copyright 2017-2018 Meniga Iceland Inc.
 */
package com.meniga.sdk.helpers

import com.meniga.sdk.providers.tasks.Task

interface Result<T> {

    val task: Task<T>

    fun cancel()

    fun <R> map(mapper: (T) -> R): Result<R>
}
