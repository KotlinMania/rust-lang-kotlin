@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86TbmBextriU32")
private external fun wasiRustlangCorearchX86TbmBextriU32(a: Int, control: Int): Int

public actual fun bextriU32(a: UInt, control: UInt): UInt =
    wasiRustlangCorearchX86TbmBextriU32(a.toInt(), control.toInt()).toUInt()
