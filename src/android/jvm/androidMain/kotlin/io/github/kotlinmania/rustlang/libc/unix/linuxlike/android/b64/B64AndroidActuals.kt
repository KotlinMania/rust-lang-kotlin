@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object LibcUnixLinuxlikeAndroidB64JniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangLibcUnixLinuxlikeAndroidB64SystemPropertyWait(pi: Long, oldSerial: Int, newSerialPtr: Long, relativeTimeout: Long): Boolean
}

public actual fun systemPropertyWait(pi: ConstPtr, oldSerial: UInt, newSerialPtr: MutPtr, relativeTimeout: ConstPtr): Boolean =
    LibcUnixLinuxlikeAndroidB64JniBridge.rustlangLibcUnixLinuxlikeAndroidB64SystemPropertyWait(pi.rawAddress, oldSerial.toInt(), newSerialPtr.rawAddress, relativeTimeout.rawAddress)
