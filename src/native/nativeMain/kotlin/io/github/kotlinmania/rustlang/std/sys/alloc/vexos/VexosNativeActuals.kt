@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.alloc.vexos

import io.github.kotlinmania.rustlang.std.sys.alloc.vexos.cinterop.stdSysAllocVexosGetHEAPEND
import io.github.kotlinmania.rustlang.std.sys.alloc.vexos.cinterop.stdSysAllocVexosGetHEAPSTART
import io.github.kotlinmania.rustlang.std.sys.alloc.vexos.cinterop.stdSysAllocVexosSetHEAPEND
import io.github.kotlinmania.rustlang.std.sys.alloc.vexos.cinterop.stdSysAllocVexosSetHEAPSTART

public actual var HEAP_START: UByte
    get() = stdSysAllocVexosGetHEAPSTART()
    set(value) {
        stdSysAllocVexosSetHEAPSTART(value)
    }

public actual var HEAP_END: UByte
    get() = stdSysAllocVexosGetHEAPEND()
    set(value) {
        stdSysAllocVexosSetHEAPEND(value)
    }
