package com.meniga.sdk.helpers

import com.meniga.sdk.providers.tasks.Task

/**
 * A special Result implementation that performs mapping from type T to type R using the mapper function.
 *
 * @property result Wrapped Result<T> instance.
 * @property mapper Function that maps its T parameter into R.
 */
internal class ResultMapper<T, R>(
        private val result: Result<T>,
        private val mapper: (T) -> R) : Result<R> {
    override val task: Task<R>
        get() = result.task.onSuccess { continuationTask -> mapper.invoke(continuationTask.result) }

    override fun cancel() {
        result.cancel()
    }

    override fun <E> map(mapper: (R) -> E): Result<E> {
        return ResultMapper(this, mapper)
    }
}
