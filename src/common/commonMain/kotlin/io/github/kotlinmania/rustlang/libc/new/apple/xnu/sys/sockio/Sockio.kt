// port-lint: source new/apple/xnu/sys/sockio.rs
package io.github.kotlinmania.rustlang.libc.new.apple.xnu.sys.sockio

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.IOC_IN
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.IOC_INOUT
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.ioc
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.ior
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.iow
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.iowr
import io.github.kotlinmania.rustlang.unix.bsd.apple.Ifaliasreq
import io.github.kotlinmania.rustlang.unix.bsd.apple.Ifconf
import io.github.kotlinmania.rustlang.unix.bsd.apple.Ifreq

// Header: `sys / sockio.h`
//
// <https://github.com/apple-oss-distributions/xnu/blob/main/bsd/sys/sockio.h>

public val SIOCSIFVLAN: CUlong = iow<Ifreq>('i'.code.toULong(), 126uL)

public val SIOCSETVLAN: CUlong = SIOCSIFVLAN

public val SIOCGIFVLAN: CUlong = iowr<Ifreq>('i'.code.toULong(), 127uL)

public val SIOCGETVLAN: CUlong = SIOCGIFVLAN

public val SIOCSHIWAT: CUlong = iow<CInt>('s'.code.toULong(), 0uL)

public val SIOCGHIWAT: CUlong = ior<CInt>('s'.code.toULong(), 1uL)

public val SIOCSLOWAT: CUlong = iow<CInt>('s'.code.toULong(), 2uL)

public val SIOCGLOWAT: CUlong = ior<CInt>('s'.code.toULong(), 3uL)

public val SIOCATMARK: CUlong = ior<CInt>('s'.code.toULong(), 7uL)

public val SIOCSPGRP: CUlong = iow<CInt>('s'.code.toULong(), 8uL)

public val SIOCGPGRP: CUlong = ior<CInt>('s'.code.toULong(), 9uL)

public val SIOCSIFADDR: CUlong = iow<Ifreq>('i'.code.toULong(), 12uL)

public val SIOCSIFDSTADDR: CUlong = iow<Ifreq>('i'.code.toULong(), 14uL)

public val SIOCSIFFLAGS: CUlong = iow<Ifreq>('i'.code.toULong(), 16uL)

public val SIOCGIFFLAGS: CUlong = iowr<Ifreq>('i'.code.toULong(), 17uL)

public val SIOCSIFBRDADDR: CUlong = iow<Ifreq>('i'.code.toULong(), 19uL)

public val SIOCSIFNETMASK: CUlong = iow<Ifreq>('i'.code.toULong(), 22uL)

public val SIOCGIFMETRIC: CUlong = iowr<Ifreq>('i'.code.toULong(), 23uL)

public val SIOCSIFMETRIC: CUlong = iow<Ifreq>('i'.code.toULong(), 24uL)

public val SIOCDIFADDR: CUlong = iow<Ifreq>('i'.code.toULong(), 25uL)

public val SIOCAIFADDR: CUlong = iow<Ifaliasreq>('i'.code.toULong(), 26uL)

public val SIOCGIFDSTADDR: CUlong = iowr<Ifreq>('i'.code.toULong(), 34uL)

public val SIOCGIFBRDADDR: CUlong = iowr<Ifreq>('i'.code.toULong(), 35uL)

public val SIOCGIFCONF: CUlong = iowr<Ifconf>('i'.code.toULong(), 36uL)

public val SIOCGIFNETMASK: CUlong = iowr<Ifreq>('i'.code.toULong(), 37uL)

public val SIOCAUTOADDR: CUlong = iowr<Ifreq>('i'.code.toULong(), 38uL)

public val SIOCAUTONETMASK: CUlong = iow<Ifreq>('i'.code.toULong(), 39uL)

public val SIOCARPIPLL: CUlong = iowr<Ifreq>('i'.code.toULong(), 40uL)

public val SIOCADDMULTI: CUlong = iow<Ifreq>('i'.code.toULong(), 49uL)

public val SIOCDELMULTI: CUlong = iow<Ifreq>('i'.code.toULong(), 50uL)

public val SIOCGIFMTU: CUlong = iowr<Ifreq>('i'.code.toULong(), 51uL)

public val SIOCSIFMTU: CUlong = iow<Ifreq>('i'.code.toULong(), 52uL)

public val SIOCGIFPHYS: CUlong = iowr<Ifreq>('i'.code.toULong(), 53uL)

public val SIOCSIFPHYS: CUlong = iow<Ifreq>('i'.code.toULong(), 54uL)

public val SIOCSIFMEDIA: CUlong = iowr<Ifreq>('i'.code.toULong(), 55uL)

// struct ifmediareq
public val SIOCGIFMEDIA: CUlong = ioc(IOC_INOUT, 'i'.code.toULong(), 56uL, 44uL)

public val SIOCSIFGENERIC: CUlong = iow<Ifreq>('i'.code.toULong(), 57uL)

public val SIOCGIFGENERIC: CUlong = iowr<Ifreq>('i'.code.toULong(), 58uL)

// struct rslvmultiReq
public val SIOCRSLVMULTI: CUlong = ioc(IOC_INOUT, 'i'.code.toULong(), 59uL, 16uL)

public val SIOCSIFLLADDR: CUlong = iow<Ifreq>('i'.code.toULong(), 60uL)

// struct ifstat
public val SIOCGIFSTATUS: CUlong = ioc(IOC_INOUT, 'i'.code.toULong(), 61uL, 817uL)

public val SIOCSIFPHYADDR: CUlong = iow<Ifaliasreq>('i'.code.toULong(), 62uL)

public val SIOCGIFPSRCADDR: CUlong = iowr<Ifreq>('i'.code.toULong(), 63uL)

public val SIOCGIFPDSTADDR: CUlong = iowr<Ifreq>('i'.code.toULong(), 64uL)

public val SIOCDIFPHYADDR: CUlong = iow<Ifreq>('i'.code.toULong(), 65uL)

public val SIOCGIFDEVMTU: CUlong = iowr<Ifreq>('i'.code.toULong(), 68uL)

public val SIOCSIFALTMTU: CUlong = iow<Ifreq>('i'.code.toULong(), 69uL)

public val SIOCGIFALTMTU: CUlong = iowr<Ifreq>('i'.code.toULong(), 72uL)

public val SIOCSIFBOND: CUlong = iow<Ifreq>('i'.code.toULong(), 70uL)

public val SIOCGIFBOND: CUlong = iowr<Ifreq>('i'.code.toULong(), 71uL)

// struct ifmediareq
public val SIOCGIFXMEDIA: CUlong = ioc(IOC_INOUT, 'i'.code.toULong(), 72uL, 44uL)

public val SIOCSIFCAP: CUlong = iow<Ifreq>('i'.code.toULong(), 90uL)

public val SIOCGIFCAP: CUlong = iowr<Ifreq>('i'.code.toULong(), 91uL)

public val SIOCSIFMANAGEMENT: CUlong = iowr<Ifreq>('i'.code.toULong(), 92uL)

public val SIOCIFCREATE: CUlong = iowr<Ifreq>('i'.code.toULong(), 120uL)

public val SIOCIFDESTROY: CUlong = iow<Ifreq>('i'.code.toULong(), 121uL)

public val SIOCIFCREATE2: CUlong = iowr<Ifreq>('i'.code.toULong(), 122uL)

// struct ifdrv
public val SIOCSDRVSPEC: CUlong = ioc(IOC_IN, 'i'.code.toULong(), 123uL, 40uL)

// struct ifdrv
public val SIOCGDRVSPEC: CUlong = ioc(IOC_INOUT, 'i'.code.toULong(), 123uL, 40uL)

// struct ifClonereq
public val SIOCIFGCLONERS: CUlong = ioc(IOC_INOUT, 'i'.code.toULong(), 129uL, 16uL)

public val SIOCGIFASYNCMAP: CUlong = iowr<Ifreq>('i'.code.toULong(), 124uL)

public val SIOCSIFASYNCMAP: CUlong = iow<Ifreq>('i'.code.toULong(), 125uL)

public val SIOCGIFMAC: CUlong = iowr<Ifreq>('i'.code.toULong(), 130uL)

public val SIOCSIFMAC: CUlong = iow<Ifreq>('i'.code.toULong(), 131uL)

public val SIOCSIFKPI: CUlong = iow<Ifreq>('i'.code.toULong(), 134uL)

public val SIOCGIFKPI: CUlong = iowr<Ifreq>('i'.code.toULong(), 135uL)

public val SIOCGIFWAKEFLAGS: CUlong = iowr<Ifreq>('i'.code.toULong(), 136uL)

public val SIOCGIFFUNCTIONALTYPE: CUlong = iowr<Ifreq>('i'.code.toULong(), 173uL)

public val SIOCSIF6LOWPAN: CUlong = iow<Ifreq>('i'.code.toULong(), 196uL)

public val SIOCGIF6LOWPAN: CUlong = iowr<Ifreq>('i'.code.toULong(), 197uL)

public val SIOCGIFDIRECTLINK: CUlong = iowr<Ifreq>('i'.code.toULong(), 222uL)
