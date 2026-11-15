@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object LibcUnixBsdAppleB64JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixBsdAppleB64Exchangedata(path1: Long, path2: Long, options: Int): Int
}

public actual fun exchangedata(path1: ConstPtr, path2: ConstPtr, options: CUint): CInt =
    LibcUnixBsdAppleB64JniBridge.rustlangLibcUnixBsdAppleB64Exchangedata(path1.rawAddress, path2.rawAddress, options.toInt())
