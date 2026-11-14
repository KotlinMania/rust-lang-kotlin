@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86FxsrFxrstor
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86FxsrFxsave
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun fxsave(p: MutPtr) {
    corearchX86FxsrFxsave(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun fxrstor(p: ConstPtr) {
    corearchX86FxsrFxrstor(p.rawAddress.toCPointer<ByteVar>())
}
