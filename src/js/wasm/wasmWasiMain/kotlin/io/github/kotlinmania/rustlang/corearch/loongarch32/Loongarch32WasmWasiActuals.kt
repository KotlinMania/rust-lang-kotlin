@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch32Cacop")
private external fun wasiRustlangCorearchLoongarch32Cacop(a: Int, b: Int, c: Int): Unit

public actual fun cacop(a: Int, b: Int, c: Int): Unit =
    wasiRustlangCorearchLoongarch32Cacop(a, b, c)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch32Csrrd")
private external fun wasiRustlangCorearchLoongarch32Csrrd(a: Int): Int

public actual fun csrrd(a: Int): Int =
    wasiRustlangCorearchLoongarch32Csrrd(a).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch32Csrwr")
private external fun wasiRustlangCorearchLoongarch32Csrwr(a: Int, b: Int): Int

public actual fun csrwr(a: Int, b: Int): Int =
    wasiRustlangCorearchLoongarch32Csrwr(a, b).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchLoongarch32Csrxchg")
private external fun wasiRustlangCorearchLoongarch32Csrxchg(a: Int, b: Int, c: Int): Int

public actual fun csrxchg(a: Int, b: Int, c: Int): Int =
    wasiRustlangCorearchLoongarch32Csrxchg(a, b, c).toInt()
