package io.github.kotlinmania.rustlang.wasip1

import io.github.kotlinmania.rustlang.Dircookie
import io.github.kotlinmania.rustlang.Fd

/**
 * Special `Dircookie` value indicating the start of a directory.
 */
public val DIRCOOKIE_START: Dircookie = 0uL

/**
 * The "standard input" descriptor number.
 */
public val FD_STDIN: Fd = 0u

/**
 * The "standard output" descriptor number.
 */
public val FD_STDOUT: Fd = 1u

/**
 * The "standard error" descriptor number.
 */
public val FD_STDERR: Fd = 2u
