@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.corearch.aarch64.cinterop.corearchAarch64PrefetchExternPrefetch
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun externPrefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    corearchAarch64PrefetchExternPrefetch(p.rawAddress.toCPointer<ByteVar>(), rw, loc, ty)
}
