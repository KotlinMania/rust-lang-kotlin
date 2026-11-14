@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem

private object SysPalSgxAbiMemJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysPalSgxAbiMemGetENCLAVESIZE(): Long

    @JvmStatic
    external fun stdSysPalSgxAbiMemGetHEAPBASE(): Long

    @JvmStatic
    external fun stdSysPalSgxAbiMemGetHEAPSIZE(): Long
}

public actual val ENCLAVE_SIZE: ULong
    get() = SysPalSgxAbiMemJniBridge.stdSysPalSgxAbiMemGetENCLAVESIZE().toULong()

public actual val HEAP_BASE: ULong
    get() = SysPalSgxAbiMemJniBridge.stdSysPalSgxAbiMemGetHEAPBASE().toULong()

public actual val HEAP_SIZE: ULong
    get() = SysPalSgxAbiMemJniBridge.stdSysPalSgxAbiMemGetHEAPSIZE().toULong()
