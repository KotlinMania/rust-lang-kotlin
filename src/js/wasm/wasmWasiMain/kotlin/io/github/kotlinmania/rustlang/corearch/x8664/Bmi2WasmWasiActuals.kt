@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX8664Bmi2X86Bmi2Bzhi64")
private external fun wasiRustlangCorearchX8664Bmi2X86Bmi2Bzhi64(x: Long, y: Long): Long

public actual fun x86Bmi2Bzhi64(x: ULong, y: ULong): ULong =
    wasiRustlangCorearchX8664Bmi2X86Bmi2Bzhi64(x.toLong(), y.toLong()).toULong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX8664Bmi2X86Bmi2Pdep64")
private external fun wasiRustlangCorearchX8664Bmi2X86Bmi2Pdep64(x: Long, y: Long): Long

public actual fun x86Bmi2Pdep64(x: ULong, y: ULong): ULong =
    wasiRustlangCorearchX8664Bmi2X86Bmi2Pdep64(x.toLong(), y.toLong()).toULong()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX8664Bmi2X86Bmi2Pext64")
private external fun wasiRustlangCorearchX8664Bmi2X86Bmi2Pext64(x: Long, y: Long): Long

public actual fun x86Bmi2Pext64(x: ULong, y: ULong): ULong =
    wasiRustlangCorearchX8664Bmi2X86Bmi2Pext64(x.toLong(), y.toLong()).toULong()
