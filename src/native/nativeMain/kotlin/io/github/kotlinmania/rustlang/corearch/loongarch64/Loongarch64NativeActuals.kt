@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64Asrtgt
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64Asrtle
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64Cacop
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64CrcWBW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64CrcWDW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64CrcWHW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64CrcWWW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64CrccWBW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64CrccWDW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64CrccWHW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64CrccWWW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64Csrrd
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64Csrwr
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64Csrxchg
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64IocsrrdD
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64IocsrwrD
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64Lddir
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.rustlangCorearchLoongarch64Ldpte
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun crcWBW(a: Int, b: Int): Int =
    rustlangCorearchLoongarch64CrcWBW(a, b)

public actual fun crcWHW(a: Int, b: Int): Int =
    rustlangCorearchLoongarch64CrcWHW(a, b)

public actual fun crcWWW(a: Int, b: Int): Int =
    rustlangCorearchLoongarch64CrcWWW(a, b)

public actual fun crcWDW(a: Long, b: Int): Int =
    rustlangCorearchLoongarch64CrcWDW(a, b)

public actual fun crccWBW(a: Int, b: Int): Int =
    rustlangCorearchLoongarch64CrccWBW(a, b)

public actual fun crccWHW(a: Int, b: Int): Int =
    rustlangCorearchLoongarch64CrccWHW(a, b)

public actual fun crccWWW(a: Int, b: Int): Int =
    rustlangCorearchLoongarch64CrccWWW(a, b)

public actual fun crccWDW(a: Long, b: Int): Int =
    rustlangCorearchLoongarch64CrccWDW(a, b)

public actual fun cacop(a: Long, b: Long, c: Long) {
    rustlangCorearchLoongarch64Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Long =
    rustlangCorearchLoongarch64Csrrd(a)

public actual fun csrwr(a: Long, b: Int): Long =
    rustlangCorearchLoongarch64Csrwr(a, b)

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    rustlangCorearchLoongarch64Csrxchg(a, b, c)

public actual fun iocsrrdD(a: Int): Long =
    rustlangCorearchLoongarch64IocsrrdD(a)

public actual fun iocsrwrD(a: Long, b: Int) {
    rustlangCorearchLoongarch64IocsrwrD(a, b)
}

public actual fun asrtle(a: Long, b: Long) {
    rustlangCorearchLoongarch64Asrtle(a, b)
}

public actual fun asrtgt(a: Long, b: Long) {
    rustlangCorearchLoongarch64Asrtgt(a, b)
}

public actual fun lddir(a: Long, b: Long): Long =
    rustlangCorearchLoongarch64Lddir(a, b)

public actual fun ldpte(a: Long, b: Long) {
    rustlangCorearchLoongarch64Ldpte(a, b)
}
