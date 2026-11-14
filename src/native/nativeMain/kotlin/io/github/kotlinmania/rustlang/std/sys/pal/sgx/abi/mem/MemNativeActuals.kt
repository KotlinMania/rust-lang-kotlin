@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem

import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem.cinterop.stdSysPalSgxAbiMemGetENCLAVESIZE
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem.cinterop.stdSysPalSgxAbiMemGetHEAPBASE
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem.cinterop.stdSysPalSgxAbiMemGetHEAPSIZE

public actual val ENCLAVE_SIZE: ULong
    get() = stdSysPalSgxAbiMemGetENCLAVESIZE()

public actual val HEAP_BASE: ULong
    get() = stdSysPalSgxAbiMemGetHEAPBASE()

public actual val HEAP_SIZE: ULong
    get() = stdSysPalSgxAbiMemGetHEAPSIZE()
