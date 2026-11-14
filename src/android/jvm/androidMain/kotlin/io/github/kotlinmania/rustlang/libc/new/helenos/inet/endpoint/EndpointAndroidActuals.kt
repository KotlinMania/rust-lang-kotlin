@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.endpoint

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object NewHelenosInetEndpointJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosInetEndpointInetEpInit(ep: Long)

    @JvmStatic
    external fun libcNewHelenosInetEndpointInetEp2Init(epp: Long)
}

public actual fun inetEpInit(ep: MutPtr) {
    NewHelenosInetEndpointJniBridge.libcNewHelenosInetEndpointInetEpInit(ep.rawAddress)
}

public actual fun inetEp2Init(epp: MutPtr) {
    NewHelenosInetEndpointJniBridge.libcNewHelenosInetEndpointInetEp2Init(epp.rawAddress)
}
