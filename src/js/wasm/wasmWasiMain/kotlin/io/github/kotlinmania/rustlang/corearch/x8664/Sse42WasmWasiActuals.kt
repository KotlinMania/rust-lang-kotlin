@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

// The host must provide the C companion wrapper exports in import module corearch.
@kotlin.wasm.WasmImport("corearch", "corearchX8664Sse42Crc326464")
private external fun wasiCorearchX8664Sse42Crc326464(crc: Long, v: Long): Long

public actual fun crc326464(crc: ULong, v: ULong): ULong =
    wasiCorearchX8664Sse42Crc326464(crc.toLong(), v.toLong()).toULong()
