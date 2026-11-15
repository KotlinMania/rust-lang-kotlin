@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchX86Bmi2X86Bmi2Bzhi32")
private external fun wasiCorearchX86Bmi2X86Bmi2Bzhi32(x: Int, y: Int): Int

public actual fun x86Bmi2Bzhi32(x: UInt, y: UInt): UInt =
    wasiCorearchX86Bmi2X86Bmi2Bzhi32(x.toInt(), y.toInt()).toUInt()

@kotlin.wasm.WasmImport("kmcore", "corearchX86Bmi2X86Bmi2Pdep32")
private external fun wasiCorearchX86Bmi2X86Bmi2Pdep32(x: Int, y: Int): Int

public actual fun x86Bmi2Pdep32(x: UInt, y: UInt): UInt =
    wasiCorearchX86Bmi2X86Bmi2Pdep32(x.toInt(), y.toInt()).toUInt()

@kotlin.wasm.WasmImport("kmcore", "corearchX86Bmi2X86Bmi2Pext32")
private external fun wasiCorearchX86Bmi2X86Bmi2Pext32(x: Int, y: Int): Int

public actual fun x86Bmi2Pext32(x: UInt, y: UInt): UInt =
    wasiCorearchX86Bmi2X86Bmi2Pext32(x.toInt(), y.toInt()).toUInt()
