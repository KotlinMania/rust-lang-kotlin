@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Asrtgt
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Asrtle
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Cacop
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64CrcWBW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64CrcWDW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64CrcWHW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64CrcWWW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64CrccWBW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64CrccWDW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64CrccWHW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64CrccWWW
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Csrrd
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Csrwr
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Csrxchg
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64IocsrrdD
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64IocsrwrD
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Lddir
import io.github.kotlinmania.rustlang.corearch.loongarch64.cinterop.corearchLoongarch64Ldpte

public actual fun crcWBW(a: Int, b: Int): Int =
    corearchLoongarch64CrcWBW(a, b)

public actual fun crcWHW(a: Int, b: Int): Int =
    corearchLoongarch64CrcWHW(a, b)

public actual fun crcWWW(a: Int, b: Int): Int =
    corearchLoongarch64CrcWWW(a, b)

public actual fun crcWDW(a: Long, b: Int): Int =
    corearchLoongarch64CrcWDW(a, b)

public actual fun crccWBW(a: Int, b: Int): Int =
    corearchLoongarch64CrccWBW(a, b)

public actual fun crccWHW(a: Int, b: Int): Int =
    corearchLoongarch64CrccWHW(a, b)

public actual fun crccWWW(a: Int, b: Int): Int =
    corearchLoongarch64CrccWWW(a, b)

public actual fun crccWDW(a: Long, b: Int): Int =
    corearchLoongarch64CrccWDW(a, b)

public actual fun cacop(a: Long, b: Long, c: Long) {
    corearchLoongarch64Cacop(a, b, c)
}

public actual fun csrrd(a: Int): Long =
    corearchLoongarch64Csrrd(a)

public actual fun csrwr(a: Long, b: Int): Long =
    corearchLoongarch64Csrwr(a, b)

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    corearchLoongarch64Csrxchg(a, b, c)

public actual fun iocsrrdD(a: Int): Long =
    corearchLoongarch64IocsrrdD(a)

public actual fun iocsrwrD(a: Long, b: Int) {
    corearchLoongarch64IocsrwrD(a, b)
}

public actual fun asrtle(a: Long, b: Long) {
    corearchLoongarch64Asrtle(a, b)
}

public actual fun asrtgt(a: Long, b: Long) {
    corearchLoongarch64Asrtgt(a, b)
}

public actual fun lddir(a: Long, b: Long): Long =
    corearchLoongarch64Lddir(a, b)

public actual fun ldpte(a: Long, b: Long) {
    corearchLoongarch64Ldpte(a, b)
}
