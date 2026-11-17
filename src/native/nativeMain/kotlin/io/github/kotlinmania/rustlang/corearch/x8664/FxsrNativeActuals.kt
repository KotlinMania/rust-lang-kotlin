@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664FxsrExternFxrstor64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664FxsrExternFxsave64
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun externFxsave64(p: MutPtr) {
    corearchX8664FxsrExternFxsave64(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun externFxrstor64(p: ConstPtr) {
    corearchX8664FxsrExternFxrstor64(p.rawAddress.toCPointer<ByteVar>())
}
