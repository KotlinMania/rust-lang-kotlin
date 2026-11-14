@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.vexos

// The host must provide the C companion wrapper exports in import module std.
@kotlin.wasm.WasmImport("std", "stdSysAllocVexosGetHEAPSTART")
private external fun wasiStdSysAllocVexosGetHEAPSTART(): Int

@kotlin.wasm.WasmImport("std", "stdSysAllocVexosSetHEAPSTART")
private external fun wasiStdSysAllocVexosSetHEAPSTART(value: Int): Unit

public actual var HEAP_START: UByte
    get() = wasiStdSysAllocVexosGetHEAPSTART().toUByte()
    set(value) {
        wasiStdSysAllocVexosSetHEAPSTART(value.toInt())
    }

@kotlin.wasm.WasmImport("std", "stdSysAllocVexosGetHEAPEND")
private external fun wasiStdSysAllocVexosGetHEAPEND(): Int

@kotlin.wasm.WasmImport("std", "stdSysAllocVexosSetHEAPEND")
private external fun wasiStdSysAllocVexosSetHEAPEND(value: Int): Unit

public actual var HEAP_END: UByte
    get() = wasiStdSysAllocVexosGetHEAPEND().toUByte()
    set(value) {
        wasiStdSysAllocVexosSetHEAPEND(value.toInt())
    }
