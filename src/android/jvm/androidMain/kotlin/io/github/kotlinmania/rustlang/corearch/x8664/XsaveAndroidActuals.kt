@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object CorearchX8664XsaveJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchX8664XsaveXsave64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX8664XsaveXrstor64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX8664XsaveXsaveopt64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX8664XsaveXsavec64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX8664XsaveXsaves64(p: Long, hi: Int, lo: Int)

    @JvmStatic
    external fun rustlangCorearchX8664XsaveXrstors64(p: Long, hi: Int, lo: Int)
}

public actual fun xsave64(p: MutPtr, hi: UInt, lo: UInt) {
    CorearchX8664XsaveJniBridge.rustlangCorearchX8664XsaveXsave64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xrstor64(p: ConstPtr, hi: UInt, lo: UInt) {
    CorearchX8664XsaveJniBridge.rustlangCorearchX8664XsaveXrstor64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsaveopt64(p: MutPtr, hi: UInt, lo: UInt) {
    CorearchX8664XsaveJniBridge.rustlangCorearchX8664XsaveXsaveopt64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsavec64(p: MutPtr, hi: UInt, lo: UInt) {
    CorearchX8664XsaveJniBridge.rustlangCorearchX8664XsaveXsavec64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xsaves64(p: MutPtr, hi: UInt, lo: UInt) {
    CorearchX8664XsaveJniBridge.rustlangCorearchX8664XsaveXsaves64(p.rawAddress, hi.toInt(), lo.toInt())
}

public actual fun xrstors64(p: ConstPtr, hi: UInt, lo: UInt) {
    CorearchX8664XsaveJniBridge.rustlangCorearchX8664XsaveXrstors64(p.rawAddress, hi.toInt(), lo.toInt())
}
