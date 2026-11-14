@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.endpoint

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.endpoint.cinterop.libcNewHelenosInetEndpointInetEp2Init
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.endpoint.cinterop.libcNewHelenosInetEndpointInetEpInit
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun inetEpInit(ep: MutPtr) {
    libcNewHelenosInetEndpointInetEpInit(ep.rawAddress.toCPointer<ByteVar>())
}

public actual fun inetEp2Init(epp: MutPtr) {
    libcNewHelenosInetEndpointInetEp2Init(epp.rawAddress.toCPointer<ByteVar>())
}
