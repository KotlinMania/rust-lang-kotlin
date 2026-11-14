package io.github.kotlinmania.rustlang.test

import io.github.kotlinmania.rustlang.core.result.Result

// Support code for rustc's built in unit-test and micro-benchmarking
// framework.
//
// Almost all user code will only be interested in `Bencher` and
// `blackBox`. All other interactions (such as writing tests and
// benchmarks themselves) should be done via the `@Test` and
// `@bench` attributes.
//
// See the [Testing Chapter](../book/ch11-00-testing.html) of the book for more
// details.

