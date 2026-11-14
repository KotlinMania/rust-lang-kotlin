// port-lint: source new/helenos/inet/dnsr.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.inet.dnsr

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.addr.InetAddrT
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.addr.IpVerT

// HelenOS DNS resolver API
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/inet/include/inet/dnsr.h>

public class DnsrHostinfoT internal constructor(
    cnameCtorArg: MutPtr,
    public var addr: InetAddrT,
) {
    public var cname: MutPtr = cnameCtorArg
        internal set

    public constructor() : this(
        cnameCtorArg = MutPtr(0L),
        addr = InetAddrT(),
    )

    public operator fun component1(): MutPtr = this.cname

    public operator fun component2(): InetAddrT = this.addr

    public fun copy(cname: MutPtr = this.cname, addr: InetAddrT = this.addr): DnsrHostinfoT = DnsrHostinfoT(cnameCtorArg = cname, addr = addr)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DnsrHostinfoT) return false
        if (this.cname != other.cname) return false
        if (this.addr != other.addr) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cname.hashCode()
        result = 31 * result + this.addr.hashCode()
        return result
    }

    public fun clone(): DnsrHostinfoT = DnsrHostinfoT(cname, addr)

    public companion object {
        public operator fun invoke(
            cname: MutPtr = MutPtr(0L),
            addr: InetAddrT = InetAddrT(),
        ): DnsrHostinfoT = DnsrHostinfoT(
            cnameCtorArg = cname,
            addr = addr,
        )
    }
}

public expect fun dnsrName2host(name: ConstPtr, info: MutPtr, ipver: IpVerT): ErrnoT

public expect fun dnsrHostinfoDestroy(info: MutPtr)
