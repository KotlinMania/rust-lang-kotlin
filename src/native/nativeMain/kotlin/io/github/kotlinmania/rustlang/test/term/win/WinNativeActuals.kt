@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.test.term.win.cinterop.rustlangTestTermWinGetConsoleScreenBufferInfo
import io.github.kotlinmania.rustlang.test.term.win.cinterop.rustlangTestTermWinGetStdHandle
import io.github.kotlinmania.rustlang.test.term.win.cinterop.rustlangTestTermWinSetConsoleTextAttribute
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool =
    rustlangTestTermWinSetConsoleTextAttribute(handle.rawAddress.toCPointer<ByteVar>(), attr)

public actual fun getStdHandle(which: Dword): Handle =
    MutPtr(rustlangTestTermWinGetStdHandle(which)?.toLong() ?: 0L)

public actual fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool =
    rustlangTestTermWinGetConsoleScreenBufferInfo(handle.rawAddress.toCPointer<ByteVar>(), info.rawAddress.toCPointer<ByteVar>())
