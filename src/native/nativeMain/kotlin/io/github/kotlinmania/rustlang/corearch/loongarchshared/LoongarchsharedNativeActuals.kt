@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedBreakTarget
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternCpucfg
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternDbar
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternFrecipeD
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternFrecipeS
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternFrsqrteD
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternFrsqrteS
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIbar
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrrdB
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrrdH
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrrdW
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrwrB
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrwrH
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternIocsrwrW
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternMovfcsr2gr
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternMovgr2fcsr
import io.github.kotlinmania.rustlang.corearch.loongarchshared.cinterop.corearchLoongarchsharedExternSyscall
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun externDbar(a: Int) {
    corearchLoongarchsharedExternDbar(a)
}

public actual fun externIbar(a: Int) {
    corearchLoongarchsharedExternIbar(a)
}

public actual fun externMovgr2fcsr(a: Int, b: Int) {
    corearchLoongarchsharedExternMovgr2fcsr(a, b)
}

public actual fun externMovfcsr2gr(a: Int): Int =
    corearchLoongarchsharedExternMovfcsr2gr(a)

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

public actual fun externSyscall(a: Int) {
    corearchLoongarchsharedExternSyscall(a)
}

public actual fun externFrecipeS(a: Float): Float =
    corearchLoongarchsharedExternFrecipeS(a)

public actual fun externFrecipeD(a: Double): Double =
    corearchLoongarchsharedExternFrecipeD(a)

public actual fun externFrsqrteS(a: Float): Float =
    corearchLoongarchsharedExternFrsqrteS(a)

public actual fun externFrsqrteD(a: Double): Double =
    corearchLoongarchsharedExternFrsqrteD(a)
