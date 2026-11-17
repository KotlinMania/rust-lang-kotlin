@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternAsrtgt
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternAsrtle
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCacop
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrcWBW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrcWDW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrcWHW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrcWWW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrccWBW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrccWDW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrccWHW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrccWWW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCsrrd
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCsrwr
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCsrxchg
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternIocsrrdD
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternIocsrwrD
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternLddir
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternLdpte
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun externCrcWBW(a: Int, b: Int): Int =
    corearchLoongarch64ExternCrcWBW(a, b)

public actual fun externCrcWHW(a: Int, b: Int): Int =
    corearchLoongarch64ExternCrcWHW(a, b)

public actual fun externCrcWWW(a: Int, b: Int): Int =
    corearchLoongarch64ExternCrcWWW(a, b)

public actual fun externCrcWDW(a: Long, b: Int): Int =
    corearchLoongarch64ExternCrcWDW(a, b)

public actual fun externCrccWBW(a: Int, b: Int): Int =
    corearchLoongarch64ExternCrccWBW(a, b)

public actual fun externCrccWHW(a: Int, b: Int): Int =
    corearchLoongarch64ExternCrccWHW(a, b)

public actual fun externCrccWWW(a: Int, b: Int): Int =
    corearchLoongarch64ExternCrccWWW(a, b)

public actual fun externCrccWDW(a: Long, b: Int): Int =
    corearchLoongarch64ExternCrccWDW(a, b)

public actual fun externCacop(a: Long, b: Long, c: Long) {
    corearchLoongarch64ExternCacop(a, b, c)
}

public actual fun externCsrrd(a: Int): Long =
    corearchLoongarch64ExternCsrrd(a)

public actual fun externCsrwr(a: Long, b: Int): Long =
    corearchLoongarch64ExternCsrwr(a, b)

public actual fun externCsrxchg(a: Long, b: Long, c: Int): Long =
    corearchLoongarch64ExternCsrxchg(a, b, c)

public actual fun externIocsrrdD(a: Int): Long =
    corearchLoongarch64ExternIocsrrdD(a)

public actual fun externIocsrwrD(a: Long, b: Int) {
    corearchLoongarch64ExternIocsrwrD(a, b)
}

public actual fun externAsrtle(a: Long, b: Long) {
    corearchLoongarch64ExternAsrtle(a, b)
}

public actual fun externAsrtgt(a: Long, b: Long) {
    corearchLoongarch64ExternAsrtgt(a, b)
}

public actual fun externLddir(a: Long, b: Long): Long =
    corearchLoongarch64ExternLddir(a, b)

public actual fun externLdpte(a: Long, b: Long) {
    corearchLoongarch64ExternLdpte(a, b)
}
