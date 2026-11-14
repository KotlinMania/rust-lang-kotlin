@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.dnsr

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.addr.IpVerT
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.dnsr.cinterop.libcNewHelenosInetDnsrDnsrHostinfoDestroy
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.dnsr.cinterop.libcNewHelenosInetDnsrDnsrName2host
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun dnsrName2host(name: ConstPtr, info: MutPtr, ipver: IpVerT): ErrnoT =
    libcNewHelenosInetDnsrDnsrName2host(name.rawAddress.toCPointer<ByteVar>(), info.rawAddress.toCPointer<ByteVar>(), ipver)

public actual fun dnsrHostinfoDestroy(info: MutPtr) {
    libcNewHelenosInetDnsrDnsrHostinfoDestroy(info.rawAddress.toCPointer<ByteVar>())
}
