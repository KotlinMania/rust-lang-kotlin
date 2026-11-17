@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveExternXrstor64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveExternXrstors64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveExternXsave64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveExternXsavec64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveExternXsaveopt64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664XsaveExternXsaves64
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun externXsave64(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveExternXsave64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXrstor64(p: ConstPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveExternXrstor64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXsaveopt64(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveExternXsaveopt64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXsavec64(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveExternXsavec64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXsaves64(p: MutPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveExternXsaves64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}

public actual fun externXrstors64(p: ConstPtr, hi: UInt, lo: UInt) {
    corearchX8664XsaveExternXrstors64(p.rawAddress.toCPointer<ByteVar>(), hi, lo)
}
