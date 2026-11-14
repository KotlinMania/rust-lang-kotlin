@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc64

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.s390x.vector.VectorSignedInt

public actual fun lxvl(a: ConstPtr, l: ULong): VectorSignedInt =
    VectorSignedInt()

public actual fun stxvl(v: VectorSignedInt, a: MutPtr, l: ULong) {
}
