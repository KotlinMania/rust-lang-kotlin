@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.std.sys.exit.cinterop.rustlangStdSysExitExit
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun exit(status: CInt): Nothing {
    rustlangStdSysExitExit(status)
    error("Unreachable noreturn C function exit")
}
