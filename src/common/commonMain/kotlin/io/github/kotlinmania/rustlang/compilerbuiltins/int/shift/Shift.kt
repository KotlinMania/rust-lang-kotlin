// port-lint: source int/shift.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.shift

import io.github.kotlinmania.rustlang.compilerbuiltins.support.DInt
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Int
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint

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
    fun externAshlsi3(a1: UInt, b1: UInt): UInt = 0u
    return externAshlsi3(a, b)
}

public fun ashldi3(a: ULong, b: CUint): ULong {
    fun externAshldi3(a1: ULong, b1: CUint): ULong = 0uL
    return externAshldi3(a, b)
}

public fun ashlti3(a: U128, b: UInt): U128 = a.ashl(b)

public fun ashrsi3(a: Int, b: UInt): Int {
    fun externAshrsi3(a1: Int, b1: UInt): Int = 0
    return externAshrsi3(a, b)
}

public fun ashrdi3(a: Long, b: CUint): Long {
    fun externAshrdi3(a1: Long, b1: CUint): Long = 0L
    return externAshrdi3(a, b)
}

public fun ashrti3(a: I128, b: UInt): I128 = a.ashr(b)

public fun lshrsi3(a: UInt, b: UInt): UInt {
    fun externLshrsi3(a1: UInt, b1: UInt): UInt = 0u
    return externLshrsi3(a, b)
}

public fun lshrdi3(a: ULong, b: CUint): ULong {
    fun externLshrdi3(a1: ULong, b1: CUint): ULong = 0uL
    return externLshrdi3(a, b)
}

public fun lshrti3(a: U128, b: UInt): U128 = a.lshr(b)
