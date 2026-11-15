@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object CorearchX86XsaveJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX86XsaveXsave(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX86XsaveXrstor(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX86XsaveXsetbv(v: Int, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX86XsaveXgetbv(v: Int): Long

    @JvmStatic
    external fun rustlangCorearchX86XsaveXsaveopt(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX86XsaveXsavec(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX86XsaveXsaves(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX86XsaveXrstors(p: Long, hi: Int, lo: Int)
}

public actual fun xsave(p: MutPtr, hi: UInt, lo: UInt) {
    CorearchX86XsaveJniBridge.rustlangCorearchX86XsaveXsave(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    CorearchX86XsaveJniBridge.rustlangCorearchX86XsaveXrstor(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsetbv(v: UInt, hi: UInt, lo: UInt) {
    CorearchX86XsaveJniBridge.rustlangCorearchX86XsaveXsetbv(v.toInt(), hi.toInt(), lo.toInt())
}

public actual fun xgetbv(v: UInt): Long =
    CorearchX86XsaveJniBridge.rustlangCorearchX86XsaveXgetbv(v.toInt())

public actual fun xsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    CorearchX86XsaveJniBridge.rustlangCorearchX86XsaveXsaveopt(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsavec(p: MutPtr, hi: UInt, lo: UInt) {
    CorearchX86XsaveJniBridge.rustlangCorearchX86XsaveXsavec(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsaves(p: MutPtr, hi: UInt, lo: UInt) {
    CorearchX86XsaveJniBridge.rustlangCorearchX86XsaveXsaves(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    CorearchX86XsaveJniBridge.rustlangCorearchX86XsaveXrstors(p.rawAddress, hi.toInt(), lo.toInt())
}
