@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedBreakTarget
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedDbar
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternCpucfg
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternFrecipeD
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternFrecipeS
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternFrsqrteD
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternFrsqrteS
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrrdB
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrrdH
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrrdW
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrwrB
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrwrH
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrwrW
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedIbar
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

public actual fun externIocsrrdB(a: Int): Int =
    corearchLoongarchsharedExternIocsrrdB(a)

public actual fun externIocsrrdH(a: Int): Int =
    corearchLoongarchsharedExternIocsrrdH(a)

public actual fun externIocsrrdW(a: Int): Int =
    corearchLoongarchsharedExternIocsrrdW(a)

public actual fun externIocsrwrB(a: Int, b: Int) {
    corearchLoongarchsharedExternIocsrwrB(a, b)
}

public actual fun externIocsrwrH(a: Int, b: Int) {
    corearchLoongarchsharedExternIocsrwrH(a, b)
}

public actual fun externIocsrwrW(a: Int, b: Int) {
    corearchLoongarchsharedExternIocsrwrW(a, b)
}

public actual fun breakTarget(a: Int) {
    corearchLoongarchsharedBreakTarget(a)
}

public actual fun externCpucfg(a: Int): Int =
    corearchLoongarchsharedExternCpucfg(a)

public actual fun syscall(a: Int) {
    corearchLoongarchsharedSyscall(a)
}

public actual fun externFrecipeS(a: Float): Float =
    corearchLoongarchsharedExternFrecipeS(a)

public actual fun externFrecipeD(a: Double): Double =
    corearchLoongarchsharedExternFrecipeD(a)

public actual fun externFrsqrteS(a: Float): Float =
    corearchLoongarchsharedExternFrsqrteS(a)

public actual fun externFrsqrteD(a: Double): Double =
    corearchLoongarchsharedExternFrsqrteD(a)
