@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

private object UnixLinuxlikeAndroidB64JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcUnixLinuxlikeAndroidB64SystemPropertyWait(pi: Long, oldSerial: Int, newSerialPtr: Long, relativeTimeout: Long): Boolean
}

public actual fun systemPropertyWait(pi: ConstPtr, oldSerial: UInt, newSerialPtr: MutPtr, relativeTimeout: ConstPtr): Boolean =
    UnixLinuxlikeAndroidB64JniBridge.libcUnixLinuxlikeAndroidB64SystemPropertyWait(pi.rawAddress, oldSerial.toInt(), newSerialPtr.rawAddress, relativeTimeout.rawAddress)
