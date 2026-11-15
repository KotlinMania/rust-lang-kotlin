@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.test.term.win.cinterop.testTermWinGetConsoleScreenBufferInfo
import io.github.kotlinmania.rustlang.test.term.win.cinterop.testTermWinGetStdHandle
import io.github.kotlinmania.rustlang.test.term.win.cinterop.testTermWinSetConsoleTextAttribute
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool =
    testTermWinSetConsoleTextAttribute(handle.rawAddress.toCPointer<ByteVar>(), attr)

public actual fun getStdHandle(which: Dword): Handle =
    MutPtr(testTermWinGetStdHandle(which)?.toLong() ?: 0L)

public actual fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool =
    testTermWinGetConsoleScreenBufferInfo(handle.rawAddress.toCPointer<ByteVar>(), info.rawAddress.toCPointer<ByteVar>())
