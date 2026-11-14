@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdlib

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibAbort
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibCalloc
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibExit
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibFree
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibGetenv
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibMalloc
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibMemalign
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibRand
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibRealloc
import io.github.kotlinmania.rustlang.libc.new.helenos.stdlib.cinterop.libcNewHelenosStdlibSrand
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun malloc(size: ULong): MutPtr =
    MutPtr(libcNewHelenosStdlibMalloc(size)?.toLong() ?: 0L)

public actual fun calloc(nmemb: ULong, size: ULong): MutPtr =
    MutPtr(libcNewHelenosStdlibCalloc(nmemb, size)?.toLong() ?: 0L)

public actual fun realloc(addr: MutPtr, size: ULong): MutPtr =
    MutPtr(libcNewHelenosStdlibRealloc(addr.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun free(addr: MutPtr) {
    libcNewHelenosStdlibFree(addr.rawAddress.toCPointer<ByteVar>())
}

public actual fun memalign(align: ULong, size: ULong): MutPtr =
    MutPtr(libcNewHelenosStdlibMemalign(align, size)?.toLong() ?: 0L)

public actual fun rand(): CInt =
    libcNewHelenosStdlibRand()

public actual fun srand(seed: CUint) {
    libcNewHelenosStdlibSrand(seed)
}

public actual fun exit(code: CInt): Nothing {
    libcNewHelenosStdlibExit(code)
    error("Unreachable noreturn C function exit")
}

public actual fun abort(): Nothing {
    libcNewHelenosStdlibAbort()
    error("Unreachable noreturn C function abort")
}

public actual fun getenv(env: ConstPtr): MutPtr =
    MutPtr(libcNewHelenosStdlibGetenv(env.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)
