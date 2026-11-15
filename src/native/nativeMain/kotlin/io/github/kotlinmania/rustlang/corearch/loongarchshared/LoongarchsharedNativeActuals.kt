@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedBreakTarget
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedCpucfg
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedDbar
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedFrecipeD
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedFrecipeS
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedFrsqrteD
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedFrsqrteS
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedIbar
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedIocsrrdB
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedIocsrrdH
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedIocsrrdW
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedIocsrwrB
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedIocsrwrH
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedIocsrwrW
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedMovfcsr2gr
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedMovgr2fcsr
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.rustlangCorearchLoongarchsharedSyscall
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun dbar(a: Int) {
    rustlangCorearchLoongarchsharedDbar(a)
}

public actual fun ibar(a: Int) {
    rustlangCorearchLoongarchsharedIbar(a)
}

public actual fun movgr2fcsr(a: Int, b: Int) {
    rustlangCorearchLoongarchsharedMovgr2fcsr(a, b)
}

public actual fun movfcsr2gr(a: Int): Int =
    rustlangCorearchLoongarchsharedMovfcsr2gr(a)

public actual fun iocsrrdB(a: Int): Int =
    rustlangCorearchLoongarchsharedIocsrrdB(a)

public actual fun iocsrrdH(a: Int): Int =
    rustlangCorearchLoongarchsharedIocsrrdH(a)

public actual fun iocsrrdW(a: Int): Int =
    rustlangCorearchLoongarchsharedIocsrrdW(a)

public actual fun iocsrwrB(a: Int, b: Int) {
    rustlangCorearchLoongarchsharedIocsrwrB(a, b)
}

public actual fun iocsrwrH(a: Int, b: Int) {
    rustlangCorearchLoongarchsharedIocsrwrH(a, b)
}

public actual fun iocsrwrW(a: Int, b: Int) {
    rustlangCorearchLoongarchsharedIocsrwrW(a, b)
}

public actual fun breakTarget(a: Int) {
    rustlangCorearchLoongarchsharedBreakTarget(a)
}

public actual fun cpucfg(a: Int): Int =
    rustlangCorearchLoongarchsharedCpucfg(a)

public actual fun syscall(a: Int) {
    rustlangCorearchLoongarchsharedSyscall(a)
}

public actual fun frecipeS(a: Float): Float =
    rustlangCorearchLoongarchsharedFrecipeS(a)

public actual fun frecipeD(a: Double): Double =
    rustlangCorearchLoongarchsharedFrecipeD(a)

public actual fun frsqrteS(a: Float): Float =
    rustlangCorearchLoongarchsharedFrsqrteS(a)

public actual fun frsqrteD(a: Double): Double =
    rustlangCorearchLoongarchsharedFrsqrteD(a)
