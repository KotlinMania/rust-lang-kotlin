package io.github.kotlinmania.rustlang.corearch.mips

import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort

/**
 * Generates the trap instruction `BREAK`
 */
public fun breakTarget(): Nothing = kmAbort()
