@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchX86RtmX86Xbegin")
private external fun wasiCorearchX86RtmX86Xbegin(): Int

public actual fun x86Xbegin(): Int =
    wasiCorearchX86RtmX86Xbegin().toInt()

@kotlin.wasm.WasmImport("corearch", "corearchX86RtmX86Xend")
private external fun wasiCorearchX86RtmX86Xend(): Unit

public actual fun x86Xend(): Unit =
    wasiCorearchX86RtmX86Xend()

@kotlin.wasm.WasmImport("corearch", "corearchX86RtmX86Xabort")
private external fun wasiCorearchX86RtmX86Xabort(imm8: Int): Unit

public actual fun x86Xabort(imm8: Byte): Unit =
    wasiCorearchX86RtmX86Xabort(imm8.toInt())

@kotlin.wasm.WasmImport("corearch", "corearchX86RtmX86Xtest")
private external fun wasiCorearchX86RtmX86Xtest(): Int

public actual fun x86Xtest(): Int =
    wasiCorearchX86RtmX86Xtest().toInt()
