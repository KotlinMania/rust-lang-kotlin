@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.armshared

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchArmsharedHintsHint")
private external fun wasiCorearchArmsharedHintsHint(arg0: Int): Unit

public actual fun hint(arg0: Int): Unit =
    wasiCorearchArmsharedHintsHint(arg0)
