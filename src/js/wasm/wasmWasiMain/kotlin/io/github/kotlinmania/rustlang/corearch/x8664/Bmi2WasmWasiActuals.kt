@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchX8664Bmi2X86Bmi2Bzhi64")
private external fun wasiCorearchX8664Bmi2X86Bmi2Bzhi64(x: Long, y: Long): Long

public actual fun x86Bmi2Bzhi64(x: ULong, y: ULong): ULong =
    wasiCorearchX8664Bmi2X86Bmi2Bzhi64(x.toLong(), y.toLong()).toULong()

@kotlin.wasm.WasmImport("corearch", "corearchX8664Bmi2X86Bmi2Pdep64")
private external fun wasiCorearchX8664Bmi2X86Bmi2Pdep64(x: Long, y: Long): Long

public actual fun x86Bmi2Pdep64(x: ULong, y: ULong): ULong =
    wasiCorearchX8664Bmi2X86Bmi2Pdep64(x.toLong(), y.toLong()).toULong()

@kotlin.wasm.WasmImport("corearch", "corearchX8664Bmi2X86Bmi2Pext64")
private external fun wasiCorearchX8664Bmi2X86Bmi2Pext64(x: Long, y: Long): Long

public actual fun x86Bmi2Pext64(x: ULong, y: ULong): ULong =
    wasiCorearchX8664Bmi2X86Bmi2Pext64(x.toLong(), y.toLong()).toULong()
