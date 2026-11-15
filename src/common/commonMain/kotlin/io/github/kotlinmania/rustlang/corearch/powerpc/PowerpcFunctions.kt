package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort

/**
 * Generates the trap instruction `TRAP`
 */
public fun trap(): Nothing = kmAbort()
