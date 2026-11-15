// port-lint: source new/glibc/bits/signum_generic.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.bits.signumgeneric

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.libc.*

// Header: `bits / signum - generic.h`

public val SIGINT: CInt = 2

public val SIGILL: CInt = 4

public val SIGABRT: CInt = 6

public val SIGFPE: CInt = 8

public val SIGSEGV: CInt = 11

public val SIGTERM: CInt = 15

public val SIGHUP: CInt = 1

public val SIGQUIT: CInt = 3

public val SIGTRAP: CInt = 5

public val SIGKILL: CInt = 9

public val SIGPIPE: CInt = 13

public val SIGALRM: CInt = 14
