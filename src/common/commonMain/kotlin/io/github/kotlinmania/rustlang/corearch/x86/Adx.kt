// port-lint: source x86/adx.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

public expect fun llvmAddcarryU32(a: UByte, b: UInt, c: UInt): Pair<UByte, UInt>

public expect fun llvmSubborrowU32(a: UByte, b: UInt, c: UInt): Pair<UByte, UInt>

/**
 * Adds unsigned 32-bit integers `a` and `b` with unsigned 8-bit carry-in `cIn`
 * (carry or overflow flag), and store the unsigned 32-bit result in `out`, and the carry-out
 * is returned (carry or overflow flag).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_addcarry_u32)
 */
public fun addcarryU32(cIn: UByte, a: UInt, b: UInt, out: RustMutableReference<UInt>): UByte {
    val out1 = out
    val (a1, b1) = llvmAddcarryU32(cIn, a, b)
    (out1).write(b1)
    return a1
}

/**
 * Adds unsigned 32-bit integers `a` and `b` with unsigned 8-bit carry-in `cIn`
 * (carry or overflow flag), and store the unsigned 32-bit result in `out`, and
 * the carry-out is returned (carry or overflow flag).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_addcarryx_u32)
 */
public fun addcarryxU32(cIn: UByte, a: UInt, b: UInt, out: RustMutableReference<UInt>): UByte = addcarryU32(cIn, a, b, out)

/**
 * Adds unsigned 32-bit integers `a` and `b` with unsigned 8-bit carry-in `cIn`
 * (carry or overflow flag), and store the unsigned 32-bit result in `out`, and
 * the carry-out is returned (carry or overflow flag).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_subborrow_u32)
 */
public fun subborrowU32(cIn: UByte, a: UInt, b: UInt, out: RustMutableReference<UInt>): UByte {
    val out1 = out
    val (a1, b1) = llvmSubborrowU32(cIn, a, b)
    (out1).write(b1)
    return a1
}
