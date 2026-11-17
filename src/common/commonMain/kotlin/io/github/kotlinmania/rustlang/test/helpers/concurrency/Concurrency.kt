// port-lint: source helpers/concurrency.rs
package io.github.kotlinmania.rustlang.test.helpers.concurrency

import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr

// Helper module which helps to determine amount of threads to be used
// during tests execution.

public fun getConcurrency(): Int =
    when (val matchVal = Env.variable("RUST_TEST_THREADS")) {
        is Result.Ok<*, *> -> {
            val value = matchVal.value
            run {
                val matchVal1 = value.parse<NonZero<Int, NonZeroUsizeInner>>().ok()
                when {
                    matchVal1 != null -> {
                        val n = matchVal1
                        n.get()
                    }
                    else -> error("RUST_TEST_THREADS is `$value`, should be a positive integer.")
                }
            }
        }
        else -> {
            Thread.availableParallelism().map({ n -> n.get() }).unwrapOr(1)
        }
    }
