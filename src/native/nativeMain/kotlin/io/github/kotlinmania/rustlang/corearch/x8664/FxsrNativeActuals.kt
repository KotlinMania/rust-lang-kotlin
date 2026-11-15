@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664FxsrFxrstor64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.rustlangCorearchX8664FxsrFxsave64
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun fxsave64(p: MutPtr) {
    rustlangCorearchX8664FxsrFxsave64(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun fxrstor64(p: ConstPtr) {
    rustlangCorearchX8664FxsrFxrstor64(p.rawAddress.toCPointer<ByteVar>())
}
