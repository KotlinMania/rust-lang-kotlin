@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.libc.*

private object UnixBsdAppleB64JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcUnixBsdAppleB64Exchangedata(path1: Long, path2: Long, options: Int): Int
}

public actual fun exchangedata(path1: ConstPtr, path2: ConstPtr, options: CUint): CInt =
    UnixBsdAppleB64JniBridge.libcUnixBsdAppleB64Exchangedata(path1.rawAddress, path2.rawAddress, options.toInt())
