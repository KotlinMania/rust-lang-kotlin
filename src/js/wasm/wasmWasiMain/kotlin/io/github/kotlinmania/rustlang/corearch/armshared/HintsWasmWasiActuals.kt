@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.armshared

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchArmsharedHintsHint")
private external fun wasiRustlangCorearchArmsharedHintsHint(arg0: Int): Unit

public actual fun hint(arg0: Int): Unit =
    wasiRustlangCorearchArmsharedHintsHint(arg0)
