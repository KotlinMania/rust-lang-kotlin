@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

private object CorearchAarch64PrefetchJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangCorearchAarch64PrefetchPrefetch(p: Long, rw: Int, loc: Int, ty: Int)
}

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    CorearchAarch64PrefetchJniBridge.rustlangCorearchAarch64PrefetchPrefetch(p.rawAddress, rw, loc, ty)
}
