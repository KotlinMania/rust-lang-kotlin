@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.cinterop.rustlangLibcUnixLinuxlikeAndroidB64SystemPropertyWait
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun systemPropertyWait(pi: ConstPtr, oldSerial: UInt, newSerialPtr: MutPtr, relativeTimeout: ConstPtr): Boolean =
    rustlangLibcUnixLinuxlikeAndroidB64SystemPropertyWait(pi.rawAddress.toCPointer<ByteVar>(), oldSerial, newSerialPtr.rawAddress.toCPointer<ByteVar>(), relativeTimeout.rawAddress.toCPointer<ByteVar>())
