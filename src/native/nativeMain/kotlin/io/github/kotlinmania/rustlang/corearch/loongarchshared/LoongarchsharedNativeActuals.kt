@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedBreakTarget
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedCpucfg
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedDbar
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedFrecipeD
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedFrecipeS
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedFrsqrteD
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedFrsqrteS
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedIbar
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedIocsrrdB
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedIocsrrdH
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedIocsrrdW
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedIocsrwrB
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedIocsrwrH
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedIocsrwrW
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedMovfcsr2gr
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedMovgr2fcsr
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedSyscall
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun dbar(a: Int) {
    corearchLoongarchsharedDbar(a)
}

public actual fun ibar(a: Int) {
    corearchLoongarchsharedIbar(a)
}

public actual fun movgr2fcsr(a: Int, b: Int) {
    corearchLoongarchsharedMovgr2fcsr(a, b)
}

public actual fun movfcsr2gr(a: Int): Int =
    corearchLoongarchsharedMovfcsr2gr(a)

public actual fun iocsrrdB(a: Int): Int =
    corearchLoongarchsharedIocsrrdB(a)

public actual fun iocsrrdH(a: Int): Int =
    corearchLoongarchsharedIocsrrdH(a)

public actual fun iocsrrdW(a: Int): Int =
    corearchLoongarchsharedIocsrrdW(a)

public actual fun iocsrwrB(a: Int, b: Int) {
    corearchLoongarchsharedIocsrwrB(a, b)
}

public actual fun iocsrwrH(a: Int, b: Int) {
    corearchLoongarchsharedIocsrwrH(a, b)
}

public actual fun iocsrwrW(a: Int, b: Int) {
    corearchLoongarchsharedIocsrwrW(a, b)
}

public actual fun breakTarget(a: Int) {
    corearchLoongarchsharedBreakTarget(a)
}

public actual fun cpucfg(a: Int): Int =
    corearchLoongarchsharedCpucfg(a)

public actual fun syscall(a: Int) {
    corearchLoongarchsharedSyscall(a)
}

public actual fun frecipeS(a: Float): Float =
    corearchLoongarchsharedFrecipeS(a)

public actual fun frecipeD(a: Double): Double =
    corearchLoongarchsharedFrecipeD(a)

public actual fun frsqrteS(a: Float): Float =
    corearchLoongarchsharedFrsqrteS(a)

public actual fun frsqrteD(a: Double): Double =
    corearchLoongarchsharedFrsqrteD(a)
