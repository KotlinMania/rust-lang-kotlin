@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchArmsharedBarrierDmb")
private external fun wasiRustlangCorearchArmsharedBarrierDmb(arg0: Int): Unit

public actual fun dmb(arg0: Int): Unit =
    wasiRustlangCorearchArmsharedBarrierDmb(arg0)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchArmsharedBarrierDsb")
private external fun wasiRustlangCorearchArmsharedBarrierDsb(arg0: Int): Unit

public actual fun dsb(arg0: Int): Unit =
    wasiRustlangCorearchArmsharedBarrierDsb(arg0)

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchArmsharedBarrierIsb")
private external fun wasiRustlangCorearchArmsharedBarrierIsb(arg0: Int): Unit

public actual fun isb(arg0: Int): Unit =
    wasiRustlangCorearchArmsharedBarrierIsb(arg0)
