package com.meniga.sdk.helpers

import com.meniga.sdk.providers.tasks.Task

internal class ResultMapper<T, R>(
        private val result: Result<T>,
        private val mapper: (T) -> R) : Result<R> {
    override val task: Task<R>
        get() = result.task.continueWith { continuationTask -> mapper.invoke(continuationTask.result) }

    override fun cancel() {
        result.cancel()
    }

    override fun <E> map(mapper: (R) -> E): Result<E> {
        return ResultMapper(this, mapper)
    }
}
