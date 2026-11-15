@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.glibc.signal

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.glibc.signal.cinterop.rustlangLibcNewGlibcSignalSigaction
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt =
    rustlangLibcNewGlibcSignalSigaction(signum, act.rawAddress.toCPointer<ByteVar>(), oldact.rawAddress.toCPointer<ByteVar>())
