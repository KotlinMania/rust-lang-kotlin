@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls.cinterop.stdSysPalSgxAbiTlsGetTlsPtr
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls.cinterop.stdSysPalSgxAbiTlsSetTlsPtr
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun getTlsPtr(): ConstPtr =
    ConstPtr(stdSysPalSgxAbiTlsGetTlsPtr()?.toLong() ?: 0L)

public actual fun setTlsPtr(tls: ConstPtr) {
    stdSysPalSgxAbiTlsSetTlsPtr(tls.rawAddress.toCPointer<ByteVar>())
}
