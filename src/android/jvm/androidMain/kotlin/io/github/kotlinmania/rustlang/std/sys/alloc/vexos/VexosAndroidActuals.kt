@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.vexos

private object SysAllocVexosJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysAllocVexosGetHEAPSTART(): Byte

    @JvmStatic
    external fun stdSysAllocVexosSetHEAPSTART(value: Byte)

    @JvmStatic
    external fun stdSysAllocVexosGetHEAPEND(): Byte

    @JvmStatic
    external fun stdSysAllocVexosSetHEAPEND(value: Byte)
}

public actual var HEAP_START: UByte
    get() = SysAllocVexosJniBridge.stdSysAllocVexosGetHEAPSTART().toUByte()
    set(value) {
        SysAllocVexosJniBridge.stdSysAllocVexosSetHEAPSTART(value.toByte())
    }

public actual var HEAP_END: UByte
    get() = SysAllocVexosJniBridge.stdSysAllocVexosGetHEAPEND().toUByte()
    set(value) {
        SysAllocVexosJniBridge.stdSysAllocVexosSetHEAPEND(value.toByte())
    }
