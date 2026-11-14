package io.github.kotlinmania.rustlang.corearch.mips

/**
 * Generates the trap instruction `BREAK`
 */
public fun breakTarget(): Nothing = abortImmediate()
