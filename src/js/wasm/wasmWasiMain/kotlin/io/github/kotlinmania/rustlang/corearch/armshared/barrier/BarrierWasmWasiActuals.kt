@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchArmsharedBarrierDmb")
private external fun wasiCorearchArmsharedBarrierDmb(arg0: Int): Unit

public actual fun dmb(arg0: Int): Unit =
    wasiCorearchArmsharedBarrierDmb(arg0)

@kotlin.wasm.WasmImport("kmcore", "corearchArmsharedBarrierDsb")
private external fun wasiCorearchArmsharedBarrierDsb(arg0: Int): Unit

public actual fun dsb(arg0: Int): Unit =
    wasiCorearchArmsharedBarrierDsb(arg0)

@kotlin.wasm.WasmImport("kmcore", "corearchArmsharedBarrierIsb")
private external fun wasiCorearchArmsharedBarrierIsb(arg0: Int): Unit

public actual fun isb(arg0: Int): Unit =
    wasiCorearchArmsharedBarrierIsb(arg0)
