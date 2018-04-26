@file:JvmName("TaskAssertions")

package com.meniga.sdk.providers.tasks

import org.assertj.core.api.AbstractAssert
import org.assertj.core.internal.Booleans
import org.assertj.core.internal.Objects
import java.util.concurrent.TimeUnit

fun <T> assertThat(actual: Task<T>) = TaskAssert(actual)

class TaskAssert<T>(
        actual: Task<T>,
        private val booleans: Booleans = Booleans.instance(),
        private val objects: Objects = Objects.instance()
) : AbstractAssert<TaskAssert<T>, Task<T>>(actual, TaskAssert::class.java) {

    fun isSuccessful() = apply {
        booleans.assertEqual(info, actual.waitForCompletion(910, TimeUnit.SECONDS), true)
        objects.assertNull(info, actual.error)
    }
}
