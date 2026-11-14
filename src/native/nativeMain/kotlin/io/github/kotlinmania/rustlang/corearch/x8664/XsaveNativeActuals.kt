@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveXrstor64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveXrstors64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveXsave64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveXsavec64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveXsaveopt64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveXsaves64
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun xsave64(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveXsave64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xrstor64(p: ConstPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveXrstor64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xsaveopt64(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveXsaveopt64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xsavec64(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveXsavec64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xsaves64(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveXsaves64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun xrstors64(p: ConstPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveXrstors64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}
