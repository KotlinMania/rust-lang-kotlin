package io.github.kotlinmania.rustlang.test

import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Process exit code to be used to indicate test failures.
 */
public val ERROR_EXIT_CODE: UByte = 101u.toUByte()

public val SECONDARY_TEST_INVOKER_VAR: String = "__RUST_TEST_INVOKE"

public val SECONDARY_TEST_BENCH_BENCHMARKS_VAR: String = "__RUST_TEST_BENCH_BENCHMARKS"
