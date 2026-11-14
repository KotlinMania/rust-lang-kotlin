@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.dnsr

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.addr.IpVerT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun dnsrName2host(name: ConstPtr, info: MutPtr, ipver: IpVerT): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetDnsrDnsrName2host")

public actual fun dnsrHostinfoDestroy(info: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetDnsrDnsrHostinfoDestroy")
}
