// port-lint: source new/glibc/sysdeps/unix/linux/net/route.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.net.route

import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.bsd.Sockaddr

// Header: `net / route.h`
//
// Source header: `sysdeps / unix / sysv / linux / net / route.h`
// <https://github.com/sailfishos-mirror/glibc/blob/master/sysdeps/unix/sysv/linux/net/route.h>

public open class Rtentry internal constructor(
    public var rtPad1: CUlong,
    public var rtDst: Sockaddr,
    public var rtGateway: Sockaddr,
    public var rtGenmask: Sockaddr,
    public var rtFlags: CUshort,
    public var rtPad2: CShort,
    public var rtPad3: CUlong,
    public var rtTos: CUchar,
    public var rtClass: CUchar,
    // Note(1.0): private padding fields
    public var rtPad4: ShortArray,
    public var rtMetric: CShort,
    rtDevCtorArg: MutPtr,
    public var rtMtu: CUlong,
    public var rtWindow: CUlong,
    public var rtIrtt: CUshort,
) {
    public var rtDev: MutPtr = rtDevCtorArg
        internal set

    public open fun clone(): Rtentry = Rtentry(rtPad1, rtDst, rtGateway, rtGenmask, rtFlags, rtPad2, rtPad3, rtTos, rtClass, rtPad4, rtMetric, rtDev, rtMtu, rtWindow, rtIrtt)

    public companion object {
        public operator fun invoke(
            rtPad1: CUlong,
            rtDst: Sockaddr,
            rtGateway: Sockaddr,
            rtGenmask: Sockaddr,
            rtFlags: CUshort,
            rtPad2: CShort,
            rtPad3: CUlong,
            rtTos: CUchar,
            rtClass: CUchar,
            rtPad4: ShortArray,
            rtMetric: CShort,
            rtDev: MutPtr,
            rtMtu: CUlong,
            rtWindow: CUlong,
            rtIrtt: CUshort,
        ): Rtentry = Rtentry(
            rtPad1 = rtPad1,
            rtDst = rtDst,
            rtGateway = rtGateway,
            rtGenmask = rtGenmask,
            rtFlags = rtFlags,
            rtPad2 = rtPad2,
            rtPad3 = rtPad3,
            rtTos = rtTos,
            rtClass = rtClass,
            rtPad4 = rtPad4,
            rtMetric = rtMetric,
            rtDevCtorArg = rtDev,
            rtMtu = rtMtu,
            rtWindow = rtWindow,
            rtIrtt = rtIrtt,
        )
    }
}
