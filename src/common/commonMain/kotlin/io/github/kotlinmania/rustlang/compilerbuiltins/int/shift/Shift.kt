// port-lint: source int/shift.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.shift

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.support.DInt
import io.github.kotlinmania.rustlang.support.Int

public interface Ashl<T> : DInt {
    /**
     * Returns `a<<b`, requires `b<Self.BITS`
     */
    public fun ashl(shl: UInt): T
}

public interface Ashr<T> : DInt {
    /**
     * Returns arithmetic `a>> b`, requires `b<Self.BITS`
     */
    public fun ashr(shr: UInt): T
}

public interface Lshr<T> : DInt {
    /**
     * Returns logical `a>> b`, requires `b<Self.BITS`
     */
    public fun lshr(shr: UInt): T
}

public fun ashlsi3(a: UInt, b: UInt): UInt {
    fun ashlsi3(a1: UInt, b1: UInt): UInt = 0u
    return ashlsi3(a, b)
}

public fun ashldi3(a: ULong, b: CUint): ULong {
    fun ashldi3(a1: ULong, b1: CUint): ULong = 0uL
    return ashldi3(a, b)
}

public fun ashlti3(a: U128, b: UInt): U128 = a.ashl(b)

public fun ashrsi3(a: Int, b: UInt): Int {
    fun ashrsi3(a1: Int, b1: UInt): Int = 0
    return ashrsi3(a, b)
}

public fun ashrdi3(a: Long, b: CUint): Long {
    fun ashrdi3(a1: Long, b1: CUint): Long = 0L
    return ashrdi3(a, b)
}

public fun ashrti3(a: I128, b: UInt): I128 = a.ashr(b)

public fun lshrsi3(a: UInt, b: UInt): UInt {
    fun lshrsi3(a1: UInt, b1: UInt): UInt = 0u
    return lshrsi3(a, b)
}

public fun lshrdi3(a: ULong, b: CUint): ULong {
    fun lshrdi3(a1: ULong, b1: CUint): ULong = 0uL
    return lshrdi3(a, b)
}

public fun lshrti3(a: U128, b: UInt): U128 = a.lshr(b)
