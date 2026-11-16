// port-lint: source int/sdiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv

import io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv.udivti3
import io.github.kotlinmania.rustlang.compilerbuiltins.int.udiv.umodti3
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.num.wrappingNeg

// The `@armAeabiAlias= __aeabi_idiv` attribute cannot be made to work with `intrinsics` in macros
// LLVM does not currently have a `__divmodti4` function, but GCC does
public fun divmodsi4(a: Int, b: Int, rem: RustMutableReference<Int>): Int {
    fun divmodsi4(a1: Int, b1: Int, rem1: RustMutableReference<Int>): Int = 0
    return divmodsi4(a, b, rem)
}

public fun divsi3(a: Int, b: Int): Int {
    fun divsi3(a1: Int, b1: Int): Int = 0
    return divsi3(a, b)
}

public fun modsi3(a: Int, b: Int): Int {
    fun modsi3(a1: Int, b1: Int): Int = 0
    return modsi3(a, b)
}

public fun divmoddi4(a: Long, b: Long, rem: RustMutableReference<Long>): Long {
    fun divmoddi4(a1: Long, b1: Long, rem1: RustMutableReference<Long>): Long = 0L
    return divmoddi4(a, b, rem)
}

public fun divdi3(a: Long, b: Long): Long {
    fun divdi3(a1: Long, b1: Long): Long = 0L
    return divdi3(a, b)
}

public fun moddi3(a: Long, b: Long): Long {
    fun moddi3(a1: Long, b1: Long): Long = 0L
    return moddi3(a, b)
}

public fun divmodti4(a: I128, b: I128, rem: RustMutableReference<I128>): I128 {
    fun divmodti4(a1: I128, b1: I128, rem1: RustMutableReference<I128>): I128 = I128()
    return divmodti4(a, b, rem)
}

/**
 * Returns `n / d`
 */
public fun divti3(a: I128, b: I128): I128 {
    val a1 = a
    val b1 = b
    val aNeg = a1 < I128(0x0L, 0x0uL)
    val bNeg = b1 < I128(0x0L, 0x0uL)
    var a2 = a1
    var b2 = b1
    if (aNeg) {
        a2 = a2.wrappingNeg()
    }
    if (bNeg) {
        b2 = b2.wrappingNeg()
    }
    val t = udivti3(U128.fromLong(a2.toLong()), U128.fromLong(b2.toLong())).toI128()
    return if (aNeg != bNeg) {
        t.wrappingNeg()
    } else {
        t
    }
}

/**
 * Returns `n % d`
 */
public fun modti3(a: I128, b: I128): I128 {
    val a1 = a
    val b1 = b
    val aNeg = a1 < I128(0x0L, 0x0uL)
    val bNeg = b1 < I128(0x0L, 0x0uL)
    var a2 = a1
    var b2 = b1
    if (aNeg) {
        a2 = a2.wrappingNeg()
    }
    if (bNeg) {
        b2 = b2.wrappingNeg()
    }
    val r = umodti3(U128.fromLong(a2.toLong()), U128.fromLong(b2.toLong())).toI128()
    return if (aNeg) {
        r.wrappingNeg()
    } else {
        r
    }
}
