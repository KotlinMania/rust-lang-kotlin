@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchX8664BmiX86BmiBextr64")
private external fun wasiCorearchX8664BmiX86BmiBextr64(x: Long, y: Long): Long

public actual fun x86BmiBextr64(x: ULong, y: ULong): ULong =
    wasiCorearchX8664BmiX86BmiBextr64(x.toLong(), y.toLong()).toULong()
