@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.exit

import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.sys.exit.cinterop.stdSysExitExit
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun exit(status: CInt): Nothing {
    stdSysExitExit(status)
    error("Unreachable noreturn C function exit")
}
