@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86FxsrExternFxrstor
import io.github.kotlinmania.rustlang.corearch.x86.cinterop.corearchX86FxsrExternFxsave
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun externFxsave(p: MutPtr) {
    corearchX86FxsrExternFxsave(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun externFxrstor(p: ConstPtr) {
    corearchX86FxsrExternFxrstor(p.rawAddress.toCPointer<ByteVar>())
}
