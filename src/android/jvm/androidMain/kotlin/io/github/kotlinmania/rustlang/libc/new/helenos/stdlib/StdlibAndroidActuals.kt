@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.stdlib

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

private object NewHelenosStdlibJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosStdlibMalloc(size: Long): Long

    @JvmStatic
    external fun libcNewHelenosStdlibCalloc(nmemb: Long, size: Long): Long

    @JvmStatic
    external fun libcNewHelenosStdlibRealloc(addr: Long, size: Long): Long

    @JvmStatic
    external fun libcNewHelenosStdlibFree(addr: Long)

    @JvmStatic
    external fun libcNewHelenosStdlibMemalign(align: Long, size: Long): Long

    @JvmStatic
    external fun libcNewHelenosStdlibRand(): Int

    @JvmStatic
    external fun libcNewHelenosStdlibSrand(seed: Int)

    @JvmStatic
    external fun libcNewHelenosStdlibExit(code: Int)

    @JvmStatic
    external fun libcNewHelenosStdlibAbort()

    @JvmStatic
    external fun libcNewHelenosStdlibGetenv(env: Long): Long
}

public actual fun malloc(size: ULong): MutPtr =
    MutPtr(NewHelenosStdlibJniBridge.libcNewHelenosStdlibMalloc(size.toLong()))

public actual fun calloc(nmemb: ULong, size: ULong): MutPtr =
    MutPtr(NewHelenosStdlibJniBridge.libcNewHelenosStdlibCalloc(nmemb.toLong(), size.toLong()))

public actual fun realloc(addr: MutPtr, size: ULong): MutPtr =
    MutPtr(NewHelenosStdlibJniBridge.libcNewHelenosStdlibRealloc(addr.rawAddress, size.toLong()))

public actual fun free(addr: MutPtr) {
    NewHelenosStdlibJniBridge.libcNewHelenosStdlibFree(addr.rawAddress)
}

public actual fun memalign(align: ULong, size: ULong): MutPtr =
    MutPtr(NewHelenosStdlibJniBridge.libcNewHelenosStdlibMemalign(align.toLong(), size.toLong()))

public actual fun rand(): CInt =
    NewHelenosStdlibJniBridge.libcNewHelenosStdlibRand()

public actual fun srand(seed: CUint) {
    NewHelenosStdlibJniBridge.libcNewHelenosStdlibSrand(seed.toInt())
}

public actual fun exit(code: CInt): Nothing {
    NewHelenosStdlibJniBridge.libcNewHelenosStdlibExit(code)
    error("Unreachable noreturn C function exit")
}

public actual fun abort(): Nothing {
    NewHelenosStdlibJniBridge.libcNewHelenosStdlibAbort()
    error("Unreachable noreturn C function abort")
}

public actual fun getenv(env: ConstPtr): MutPtr =
    MutPtr(NewHelenosStdlibJniBridge.libcNewHelenosStdlibGetenv(env.rawAddress))
