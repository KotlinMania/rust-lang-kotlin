@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.corearchLoongarch32Cacop
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.corearchLoongarch32Csrrd
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.corearchLoongarch32Csrwr
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.corearchLoongarch32Csrxchg

public actual fun cacop(a: Int, b: Int, c: Int) {
    corearchLoongarch32Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Int =
    corearchLoongarch32Csrrd(a)

public actual fun csrwr(a: Int, b: Int): Int =
    corearchLoongarch32Csrwr(a, b)

public actual fun csrxchg(a: Int, b: Int, c: Int): Int =
    corearchLoongarch32Csrxchg(a, b, c)
