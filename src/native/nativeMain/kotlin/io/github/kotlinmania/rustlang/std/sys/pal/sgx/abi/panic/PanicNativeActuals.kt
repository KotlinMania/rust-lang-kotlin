@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.panic

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.panic.cinterop.stdSysPalSgxAbiPanicGetDEBUG
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.panic.cinterop.stdSysPalSgxAbiPanicTakeDebugPanicBufPtr
import kotlinx.cinterop.toLong

public actual val DEBUG: UByte
    get() = stdSysPalSgxAbiPanicGetDEBUG()

public actual fun takeDebugPanicBufPtr(): MutPtr =
    MutPtr(stdSysPalSgxAbiPanicTakeDebugPanicBufPtr()?.toLong() ?: 0L)
