// port-lint: source new/helenos/inet/endpoint.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.inet.endpoint

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.addr.InetAddrT
import io.github.kotlinmania.rustlang.libc.new.helenos.ipc.loc.ServiceIdT

// HelenOS internet endpoint types
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/inet/include/inet/endpoint.h>

public data class InetEp2T(
    public var localLink: ServiceIdT = 0uL,
    public var local: InetEpT = InetEpT(),
    public var remote: InetEpT = InetEpT(),
)

public data class InetEpT(
    public var addr: InetAddrT = InetAddrT(),
    public var port: UShort = 0u,
)

public expect fun inetEpInit(ep: MutPtr)

public expect fun inetEp2Init(epp: MutPtr)
