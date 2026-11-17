@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.armshared.barrier

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchArmsharedBarrierExternDmb")
private external fun wasiCorearchArmsharedBarrierExternDmb(arg0: Int): Unit

public actual fun externDmb(arg0: Int): Unit =
    wasiCorearchArmsharedBarrierExternDmb(arg0)

@kotlin.wasm.WasmImport("kmcore", "corearchArmsharedBarrierExternDsb")
private external fun wasiCorearchArmsharedBarrierExternDsb(arg0: Int): Unit

public actual fun externDsb(arg0: Int): Unit =
    wasiCorearchArmsharedBarrierExternDsb(arg0)

@kotlin.wasm.WasmImport("kmcore", "corearchArmsharedBarrierExternIsb")
private external fun wasiCorearchArmsharedBarrierExternIsb(arg0: Int): Unit

public actual fun externIsb(arg0: Int): Unit =
    wasiCorearchArmsharedBarrierExternIsb(arg0)
