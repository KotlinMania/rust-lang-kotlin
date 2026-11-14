package io.github.kotlinmania.rustlang.corearch.powerpc

/**
 * Generates the trap instruction `TRAP`
 */
public fun trap(): Nothing = abortImmediate()
