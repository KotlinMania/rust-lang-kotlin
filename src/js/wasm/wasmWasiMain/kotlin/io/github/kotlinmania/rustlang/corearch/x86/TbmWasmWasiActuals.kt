@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "corearchX86TbmExternBextriU32")
private external fun wasiCorearchX86TbmExternBextriU32(a: Int, control: Int): Int

public actual fun externBextriU32(a: UInt, control: UInt): UInt =
    wasiCorearchX86TbmExternBextriU32(a.toInt(), control.toInt()).toUInt()
