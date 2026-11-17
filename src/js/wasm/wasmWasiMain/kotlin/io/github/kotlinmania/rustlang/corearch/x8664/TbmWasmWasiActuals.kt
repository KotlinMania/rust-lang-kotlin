@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchX8664TbmExternBextriU64")
private external fun wasiCorearchX8664TbmExternBextriU64(a: Long, control: Long): Long

public actual fun externBextriU64(a: ULong, control: ULong): ULong =
    wasiCorearchX8664TbmExternBextriU64(a.toLong(), control.toLong()).toULong()
