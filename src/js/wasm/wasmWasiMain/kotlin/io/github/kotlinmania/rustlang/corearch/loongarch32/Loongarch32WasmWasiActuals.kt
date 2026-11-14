@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch32

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchLoongarch32Cacop")
private external fun wasiCorearchLoongarch32Cacop(a: Int, b: Int, c: Int): Unit

public actual fun cacop(a: Int, b: Int, c: Int): Unit =
    wasiCorearchLoongarch32Cacop(a, b, c)

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch32Csrrd")
private external fun wasiCorearchLoongarch32Csrrd(a: Int): Int

public actual fun csrrd(a: Int): Int =
    wasiCorearchLoongarch32Csrrd(a).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch32Csrwr")
private external fun wasiCorearchLoongarch32Csrwr(a: Int, b: Int): Int

public actual fun csrwr(a: Int, b: Int): Int =
    wasiCorearchLoongarch32Csrwr(a, b).toInt()

@kotlin.wasm.WasmImport("corearch", "corearchLoongarch32Csrxchg")
private external fun wasiCorearchLoongarch32Csrxchg(a: Int, b: Int, c: Int): Int

public actual fun csrxchg(a: Int, b: Int, c: Int): Int =
    wasiCorearchLoongarch32Csrxchg(a, b, c).toInt()
