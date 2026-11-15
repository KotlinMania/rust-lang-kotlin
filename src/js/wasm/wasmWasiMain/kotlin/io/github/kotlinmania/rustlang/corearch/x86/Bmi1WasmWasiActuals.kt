@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86Bmi1X86BmiBextr32")
private external fun wasiRustlangCorearchX86Bmi1X86BmiBextr32(x: Int, y: Int): Int

public actual fun x86BmiBextr32(x: UInt, y: UInt): UInt =
    wasiRustlangCorearchX86Bmi1X86BmiBextr32(x.toInt(), y.toInt()).toUInt()
