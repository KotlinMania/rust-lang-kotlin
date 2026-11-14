@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object Aarch64PrefetchJniBridge {
    init {
        System.loadLibrary("corearch_jni")
    }

    @JvmStatic
    external fun corearchAarch64PrefetchPrefetch(p: Long, rw: Int, loc: Int, ty: Int)
}

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    Aarch64PrefetchJniBridge.corearchAarch64PrefetchPrefetch(p.rawAddress, rw, loc, ty)
}
