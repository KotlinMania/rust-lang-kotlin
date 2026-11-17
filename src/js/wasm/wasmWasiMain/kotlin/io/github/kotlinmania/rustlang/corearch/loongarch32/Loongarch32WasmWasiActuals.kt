@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch32ExternCacop")
private external fun wasiCorearchLoongarch32ExternCacop(a: Int, b: Int, c: Int): Unit

public actual fun externCacop(a: Int, b: Int, c: Int): Unit =
    wasiCorearchLoongarch32ExternCacop(a, b, c)

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch32ExternCsrrd")
private external fun wasiCorearchLoongarch32ExternCsrrd(a: Int): Int

public actual fun externCsrrd(a: Int): Int =
    wasiCorearchLoongarch32ExternCsrrd(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch32ExternCsrwr")
private external fun wasiCorearchLoongarch32ExternCsrwr(a: Int, b: Int): Int

public actual fun externCsrwr(a: Int, b: Int): Int =
    wasiCorearchLoongarch32ExternCsrwr(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "corearchLoongarch32ExternCsrxchg")
private external fun wasiCorearchLoongarch32ExternCsrxchg(a: Int, b: Int, c: Int): Int

public actual fun externCsrxchg(a: Int, b: Int, c: Int): Int =
    wasiCorearchLoongarch32ExternCsrxchg(a, b, c).toInt()
