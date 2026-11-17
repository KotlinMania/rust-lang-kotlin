@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.corearchLoongarch32ExternCacop
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.corearchLoongarch32ExternCsrrd
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.corearchLoongarch32ExternCsrwr
import io.github.kotlinmania.rustlang.corearch.loongarch32.cinterop.corearchLoongarch32ExternCsrxchg
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun externCacop(a: Int, b: Int, c: Int) {
    corearchLoongarch32ExternCacop(a, b, c)
}

public actual fun externCsrrd(a: Int): Int =
    corearchLoongarch32ExternCsrrd(a)

public actual fun externCsrwr(a: Int, b: Int): Int =
    corearchLoongarch32ExternCsrwr(a, b)

public actual fun externCsrxchg(a: Int, b: Int, c: Int): Int =
    corearchLoongarch32ExternCsrxchg(a, b, c)
