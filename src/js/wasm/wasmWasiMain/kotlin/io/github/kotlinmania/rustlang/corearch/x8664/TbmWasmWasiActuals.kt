@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX8664TbmBextriU64")
private external fun wasiRustlangCorearchX8664TbmBextriU64(a: Long, control: Long): Long

public actual fun bextriU64(a: ULong, control: ULong): ULong =
    wasiRustlangCorearchX8664TbmBextriU64(a.toLong(), control.toLong()).toULong()
