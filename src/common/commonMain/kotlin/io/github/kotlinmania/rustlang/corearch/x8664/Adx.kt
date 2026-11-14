// port-lint: source x86_64/adx.rs
package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

public expect fun llvmAddcarryU64(a: UByte, b: ULong, c: ULong): Pair<UByte, ULong>

public expect fun llvmSubborrowU64(a: UByte, b: ULong, c: ULong): Pair<UByte, ULong>

/**
 * Adds unsigned 64-bit integers `a` and `b` with unsigned 8-bit carry-in `cIn`
 * (carry or overflow flag), and store the unsigned 64-bit result in `out`, and the carry-out
 * is returned (carry or overflow flag).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_addcarry_u64)
 */
public fun addcarryU64(cIn: UByte, a: ULong, b: ULong, out: RustMutableReference<ULong>): UByte {
    val out1 = out
    val (a1, b1) = llvmAddcarryU64(cIn, a, b)
    (out1).write(b1)
    return a1
}

/**
 * Adds unsigned 64-bit integers `a` and `b` with unsigned 8-bit carry-in `cIn`
 * (carry or overflow flag), and store the unsigned 64-bit result in `out`, and
 * the carry-out is returned (carry or overflow flag).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_addcarryx_u64)
 */
public fun addcarryxU64(cIn: UByte, a: ULong, b: ULong, out: RustMutableReference<ULong>): UByte = addcarryU64(cIn, a, b, out)

/**
 * Adds unsigned 64-bit integers `a` and `b` with unsigned 8-bit carry-in `cIn`.
 * (carry or overflow flag), and store the unsigned 64-bit result in `out`, and
 * the carry-out is returned (carry or overflow flag).
 *
 * [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/#text=_subborrow_u64)
 */
public fun subborrowU64(cIn: UByte, a: ULong, b: ULong, out: RustMutableReference<ULong>): UByte {
    val out1 = out
    val (a1, b1) = llvmSubborrowU64(cIn, a, b)
    (out1).write(b1)
    return a1
}
