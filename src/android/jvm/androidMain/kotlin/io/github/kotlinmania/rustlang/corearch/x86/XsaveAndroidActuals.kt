@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X86XsaveJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX86XsaveExternXsave(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXrstor(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXsetbv(v: Int, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXgetbv(v: Int): Long

    @JvmStatic
    external fun corearchX86XsaveExternXsaveopt(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXsavec(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXsaves(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX86XsaveExternXrstors(p: Long, hi: Int, lo: Int)
}

public actual fun externXsave(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsave(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXrstor(p: ConstPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXrstor(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsetbv(v: UInt, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsetbv(v.toInt(), hi.toInt(), lo.toInt())
}

public actual fun externXgetbv(v: UInt): Long =
    X86XsaveJniBridge.corearchX86XsaveExternXgetbv(v.toInt())

public actual fun externXsaveopt(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsaveopt(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsavec(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsavec(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsaves(p: MutPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXsaves(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXrstors(p: ConstPtr, hi: UInt, lo: UInt) {
    X86XsaveJniBridge.corearchX86XsaveExternXrstors(p.rawAddress, hi.toInt(), lo.toInt())
}
