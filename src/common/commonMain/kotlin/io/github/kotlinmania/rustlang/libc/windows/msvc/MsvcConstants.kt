package io.github.kotlinmania.rustlang.libc.windows.msvc

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.libc.*

// POSIX Supplement (from errno.h)
// This particular error code is only currently available in msvc toolchain
public val EOTHER: CInt = 131
