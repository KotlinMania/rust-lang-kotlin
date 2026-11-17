@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object Aarch64PrefetchJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun corearchAarch64PrefetchExternPrefetch(p: Long, rw: Int, loc: Int, ty: Int)
}

public actual fun externPrefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    Aarch64PrefetchJniBridge.corearchAarch64PrefetchExternPrefetch(p.rawAddress, rw, loc, ty)
}
