@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.stacknp

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.apple.libpthread.pthread.stacknp.cinterop.rustlangLibcNewAppleLibpthreadPthreadStacknpStackNpPthreadStackFrameDecodeNp
import io.github.kotlinmania.rustlang.unix.UintptrT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun pthreadStackFrameDecodeNp(frameAddr: UintptrT, returnAddr: MutPtr): UintptrT =
    rustlangLibcNewAppleLibpthreadPthreadStacknpStackNpPthreadStackFrameDecodeNp(frameAddr, returnAddr.rawAddress.toCPointer<ByteVar>())
