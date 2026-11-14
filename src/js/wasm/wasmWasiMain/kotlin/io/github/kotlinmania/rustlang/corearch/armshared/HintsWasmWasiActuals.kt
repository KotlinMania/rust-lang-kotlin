@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.armshared

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchArmsharedHintsHint")
private external fun wasiCorearchArmsharedHintsHint(arg0: Int): Unit

public actual fun hint(arg0: Int): Unit =
    wasiCorearchArmsharedHintsHint(arg0)
