@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object TestTermWinJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangTestTermWinSetConsoleTextAttribute(handle: Long, attr: Short): Int

    @JvmStatic
    external fun rustlangTestTermWinGetStdHandle(which: Int): Long

    @JvmStatic
    external fun rustlangTestTermWinGetConsoleScreenBufferInfo(handle: Long, info: Long): Int
}

public actual fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool =
    TestTermWinJniBridge.rustlangTestTermWinSetConsoleTextAttribute(handle.rawAddress, attr.toShort())

public actual fun getStdHandle(which: Dword): Handle =
    MutPtr(TestTermWinJniBridge.rustlangTestTermWinGetStdHandle(which.toInt()))

public actual fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool =
    TestTermWinJniBridge.rustlangTestTermWinGetConsoleScreenBufferInfo(handle.rawAddress, info.rawAddress)
