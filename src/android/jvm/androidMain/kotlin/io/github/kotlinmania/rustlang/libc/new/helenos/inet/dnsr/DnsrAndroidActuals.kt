@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.dnsr

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.addr.IpVerT

private object NewHelenosInetDnsrJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosInetDnsrDnsrName2host(name: Long, info: Long, ipver: Int): Int

    @JvmStatic
    external fun libcNewHelenosInetDnsrDnsrHostinfoDestroy(info: Long)
}

public actual fun dnsrName2host(name: ConstPtr, info: MutPtr, ipver: IpVerT): ErrnoT =
    NewHelenosInetDnsrJniBridge.libcNewHelenosInetDnsrDnsrName2host(name.rawAddress, info.rawAddress, ipver.toInt())

public actual fun dnsrHostinfoDestroy(info: MutPtr) {
    NewHelenosInetDnsrJniBridge.libcNewHelenosInetDnsrDnsrHostinfoDestroy(info.rawAddress)
}
