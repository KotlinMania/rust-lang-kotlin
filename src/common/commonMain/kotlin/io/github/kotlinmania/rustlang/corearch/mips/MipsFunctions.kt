package io.github.kotlinmania.rustlang.corearch.mips

import io.github.kotlinmania.rustlang.corearch.Intrinsics

/**
 * Generates the trap instruction `BREAK`
 */
public fun breakTarget(): Nothing = Intrinsics.abortImmediate()
