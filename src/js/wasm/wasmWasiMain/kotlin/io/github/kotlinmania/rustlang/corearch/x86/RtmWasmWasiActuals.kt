@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86RtmX86Xbegin")
private external fun wasiRustlangCorearchX86RtmX86Xbegin(): Int

public actual fun x86Xbegin(): Int =
    wasiRustlangCorearchX86RtmX86Xbegin().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86RtmX86Xend")
private external fun wasiRustlangCorearchX86RtmX86Xend(): Unit

public actual fun x86Xend(): Unit =
    wasiRustlangCorearchX86RtmX86Xend()

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86RtmX86Xabort")
private external fun wasiRustlangCorearchX86RtmX86Xabort(imm8: Int): Unit

public actual fun x86Xabort(imm8: Byte): Unit =
    wasiRustlangCorearchX86RtmX86Xabort(imm8.toInt())

@kotlin.wasm.WasmImport("kmcore", "rustlangCorearchX86RtmX86Xtest")
private external fun wasiRustlangCorearchX86RtmX86Xtest(): Int

public actual fun x86Xtest(): Int =
    wasiRustlangCorearchX86RtmX86Xtest().toInt()
