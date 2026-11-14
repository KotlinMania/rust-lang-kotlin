@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.print.fuchsia

public actual fun dlIteratePhdr(f: DlIteratePhdrFCallback, data: DsoPrinter): Int =
    0
