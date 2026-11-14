@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X86XsaveJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX86XsaveXsave(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXrstor(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXsetbv(v: Int, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXgetbv(v: Int): Long

    @JvmStatic
    external fun corearchX86XsaveXsaveopt(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXsavec(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXsaves(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveXrstors(p: Long, hi: Int, lo: Int)
}

public actual fun xsave(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsave(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXrstor(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsetbv(v: UInt, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsetbv(v.toInt(), hi.toInt(), lo.toInt())
}

public actual fun xgetbv(v: UInt): Long =
    X86XsaveJniBridge.corearchX86XsaveXgetbv(v.toInt())

public actual fun xsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsaveopt(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsavec(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsavec(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsaves(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXsaves(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveXrstors(p.rawAddress, hi.toInt(), lo.toInt())
}
