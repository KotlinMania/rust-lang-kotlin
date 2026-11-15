@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86FxsrFxrstor
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.rustlangCorearchX86FxsrFxsave
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun fxsave(p: MutPtr) {
    rustlangCorearchX86FxsrFxsave(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun fxrstor(p: ConstPtr) {
    rustlangCorearchX86FxsrFxrstor(p.rawAddress.toCPointer<ByteVar>())
}
