@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664FxsrFxrstor64
import io.github.kotlinmania.rustlang.corearch.x8664.cinterop.corearchX8664FxsrFxsave64
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun fxsave64(p: MutPtr) {
    corearchX8664FxsrFxsave64(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun fxrstor64(p: ConstPtr) {
    corearchX8664FxsrFxrstor64(p.rawAddress.toCPointer<ByteVar>())
}
