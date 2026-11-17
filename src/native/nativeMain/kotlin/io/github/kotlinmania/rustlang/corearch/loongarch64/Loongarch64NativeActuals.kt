@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Cacop
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Csrrd
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Csrwr
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Csrxchg
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternAsrtgt
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternAsrtle
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrcWBW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrcWDW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrcWHW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrcWWW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrccWBW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrccWDW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrccWHW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternCrccWWW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternIocsrrdD
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64ExternIocsrwrD
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Lddir
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Ldpte
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

public actual fun cacop(a: Long, b: Long, c: Long) {
    corearchLoongarch64Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Long =
    corearchLoongarch64Csrrd(a)

public actual fun csrwr(a: Long, b: Int): Long =
    corearchLoongarch64Csrwr(a, b)

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    corearchLoongarch64Csrxchg(a, b, c)

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

public actual fun lddir(a: Long, b: Long): Long =
    corearchLoongarch64Lddir(a, b)

public actual fun ldpte(a: Long, b: Long) {
    corearchLoongarch64Ldpte(a, b)
}
