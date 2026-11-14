@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.test.term.win

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object TermWinJniBridge {
    init {
        System.loadLibrary("test_jni")
    }

    @JvmStatic
    external fun testTermWinSetConsoleTextAttribute(handle: Long, attr: Short): Int

    @JvmStatic
    external fun testTermWinGetStdHandle(which: Int): Long

    @JvmStatic
    external fun testTermWinGetConsoleScreenBufferInfo(handle: Long, info: Long): Int
}

public actual fun setConsoleTextAttribute(handle: Handle, attr: Word): Bool =
    TermWinJniBridge.testTermWinSetConsoleTextAttribute(handle.rawAddress, attr.toShort())

public actual fun getStdHandle(which: Dword): Handle =
    MutPtr(TermWinJniBridge.testTermWinGetStdHandle(which.toInt()))

public actual fun getConsoleScreenBufferInfo(handle: Handle, info: MutPtr): Bool =
    TermWinJniBridge.testTermWinGetConsoleScreenBufferInfo(handle.rawAddress, info.rawAddress)
