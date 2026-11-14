package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.corearch.Intrinsics

/**
 * Generates the trap instruction `TRAP`
 */
public fun trap(): Nothing = Intrinsics.abortImmediate()
