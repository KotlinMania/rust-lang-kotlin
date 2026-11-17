@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object X8664XsaveJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchX8664XsaveExternXsave64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXrstor64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXsaveopt64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXsavec64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXsaves64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun corearchX8664XsaveExternXrstors64(p: Long, hi: Int, lo: Int)
}

public actual fun externXsave64(p: MutPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXsave64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXrstor64(p: ConstPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXrstor64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsaveopt64(p: MutPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXsaveopt64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsavec64(p: MutPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXsavec64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXsaves64(p: MutPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXsaves64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun externXrstors64(p: ConstPtr, hi: UInt, lo: UInt) {
    X8664XsaveJniBridge.corearchX8664XsaveExternXrstors64(p.rawAddress, hi.toInt(), lo.toInt())
}
