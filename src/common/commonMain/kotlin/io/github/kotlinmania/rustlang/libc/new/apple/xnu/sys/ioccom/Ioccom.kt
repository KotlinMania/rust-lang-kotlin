// port-lint: source new/apple/xnu/sys/ioccom.rs
package io.github.kotlinmania.rustlang.libc.new.apple.xnu.sys.ioccom

import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.libc.*

// Header: `sys / ioccom.h`
//
// <https://github.com/apple-oss-distributions/xnu/blob/main/bsd/sys/ioccom.h>

public val IOCPARM_MASK: CUlong = 0x1fffuL

public val IOCPARM_MAX: CUlong = IOCPARM_MASK + 1uL

// These are u32 in source but would probably be more practical as cUlong if we ever need
// to make them public.
public val IOC_VOID: UInt = 0x20000000u

public val IOC_OUT: UInt = 0x40000000u

public val IOC_IN: UInt = 0x80000000u

public val IOC_INOUT: UInt = IOC_IN or IOC_OUT

public val IOC_DIRMASK: UInt = 0xe0000000u

// Only pub(crate) for the above reason.
public fun ioc(inout: UInt, group: CUlong, num: CUlong, len: CUlong): CUlong {
    check(inout <= IOC_DIRMASK)
    check(group <= 0xffuL)
    check(num <= 0xffuL)
    check(len <= IOCPARM_MAX)
    // Sanity check the cast
    check(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= sizeOf<CUlong>(typeLayoutT = UnresolvedTypeLayout<CUlong>()))
    return (inout.toULong()) or ((len and IOCPARM_MASK) shl 16) or (group shl 8) or num
}

public fun io(g: CUlong, n: CUlong): CUlong = ioc(IOC_VOID, g, n, 0uL)

/**
 * Build an ioctl number for an read-only ioctl.
 */
public fun <T> ior(g: CUlong, n: CUlong, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): CUlong = ioc(IOC_OUT, g, n, sizeOf<T>(typeLayoutT = typeLayoutT).toULong())

/**
 * Build an ioctl number for an write-only ioctl.
 */
public fun <T> iow(g: CUlong, n: CUlong, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): CUlong = ioc(IOC_IN, g, n, sizeOf<T>(typeLayoutT = typeLayoutT).toULong())

/**
 * Build an ioctl number for a read-write ioctl.
 */
public fun <T> iowr(g: CUlong, n: CUlong, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): CUlong = ioc(IOC_INOUT, g, n, sizeOf<T>(typeLayoutT = typeLayoutT).toULong())
