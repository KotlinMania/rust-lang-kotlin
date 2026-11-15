@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.rustlangCorearchLoongarch32Cacop
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.rustlangCorearchLoongarch32Csrrd
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.rustlangCorearchLoongarch32Csrwr
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.rustlangCorearchLoongarch32Csrxchg
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun cacop(a: Int, b: Int, c: Int) {
    rustlangCorearchLoongarch32Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Int =
    rustlangCorearchLoongarch32Csrrd(a)

public actual fun csrwr(a: Int, b: Int): Int =
    rustlangCorearchLoongarch32Csrwr(a, b)

public actual fun csrxchg(a: Int, b: Int, c: Int): Int =
    rustlangCorearchLoongarch32Csrxchg(a, b, c)
