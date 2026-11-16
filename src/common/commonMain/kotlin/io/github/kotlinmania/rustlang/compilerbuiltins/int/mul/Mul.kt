// port-lint: source int/mul.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.mul

import io.github.kotlinmania.rustlang.compilerbuiltins.support.DInt
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Int
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.num.wrappingNeg

public interface Mul<T> : DInt, Int {
    public fun mul(rhs: T): T
}

public interface UMulo<T> : DInt, Int {
    public fun mulo(rhs: T): Pair<T, Boolean> =
        run {
            val matchVal = Pair(this.hi().isZero(), rhs.hi().isZero())
            when {
                // overflow is guaranteed
                matchVal.first == false && matchVal.second == false -> Pair(this.wrappingMul(rhs), true)
                matchVal.first == true && matchVal.second == false -> {
                    val mulLo = this.lo().widenMul(rhs.lo())
                    val mulHi = this.lo().widenMul(rhs.hi())
                    val (mul, o) = mulLo.overflowingAdd(mulHi.lo().widenHi())
                    Pair(mul, o || !mulHi.hi().isZero())
                }
                matchVal.first == false && matchVal.second == true -> {
                    val mulLo1 = rhs.lo().widenMul(this.lo())
                    val mulHi1 = rhs.lo().widenMul(this.hi())
                    val (mul1, o1) = mulLo1.overflowingAdd(mulHi1.lo().widenHi())
                    Pair(mul1, o1 || !mulHi1.hi().isZero())
                }
                // overflow is guaranteed to not happen, and use a smaller widening multiplication
                else -> Pair(this.lo().widenMul(rhs.lo()), false)
            }
        }
}

public fun i32OverflowingMul(lhs: Int, rhs: Int): Pair<Int, Boolean> {
    val lhs1 = lhs
    val rhs1 = rhs
    var lhs2 = lhs1
    var rhs2 = rhs1
    if (lhs2 == 0 || rhs2 == 0) {
        return Pair(0, false)
    }
    val lhsNeg = lhs2 < 0
    val rhsNeg = rhs2 < 0
    if (lhsNeg) {
        lhs2 = lhs2.wrappingNeg()
    }
    if (rhsNeg) {
        rhs2 = rhs2.wrappingNeg()
    }
    val mulNeg = lhsNeg != rhsNeg
    val (mul, o) = (lhs2.toUInt()).mulo(rhs2.toUInt())
    var mul1 = mul.toInt()
    if (mulNeg) {
        mul1 = mul1.wrappingNeg()
    }
    return if ((mul1 < 0) != mulNeg) {
        Pair(mul1, true)
    } else {
        Pair(mul1, o)
    }
}

public fun i64OverflowingMul(lhs: Long, rhs: Long): Pair<Long, Boolean> {
    val lhs1 = lhs
    val rhs1 = rhs
    var lhs2 = lhs1
    var rhs2 = rhs1
    if (lhs2 == 0 || rhs2 == 0) {
        return Pair(0, false)
    }
    val lhsNeg = lhs2 < 0
    val rhsNeg = rhs2 < 0
    if (lhsNeg) {
        lhs2 = lhs2.wrappingNeg()
    }
    if (rhsNeg) {
        rhs2 = rhs2.wrappingNeg()
    }
    val mulNeg = lhsNeg != rhsNeg
    val (mul, o) = (lhs2.toULong()).mulo(rhs2.toULong())
    var mul1 = mul.toLong()
    if (mulNeg) {
        mul1 = mul1.wrappingNeg()
    }
    return if ((mul1 < 0L) != mulNeg) {
        Pair(mul1, true)
    } else {
        Pair(mul1, o)
    }
}

public fun i128OverflowingMul(lhs: I128, rhs: I128): Pair<I128, Boolean> {
    val lhs1 = lhs
    val rhs1 = rhs
    var lhs2 = lhs1
    var rhs2 = rhs1
    if (lhs2 == 0 || rhs2 == 0) {
        return Pair(0, false)
    }
    val lhsNeg = lhs2 < 0
    val rhsNeg = rhs2 < 0
    if (lhsNeg) {
        lhs2 = lhs2.wrappingNeg()
    }
    if (rhsNeg) {
        rhs2 = rhs2.wrappingNeg()
    }
    val mulNeg = lhsNeg != rhsNeg
    val (mul, o) = (U128.fromLong(lhs2.toLong())).mulo(U128.fromLong(rhs2.toLong()))
    var mul1 = I128.fromLong(mul.toLong())
    if (mulNeg) {
        mul1 = mul1.wrappingNeg()
    }
    return if ((mul1 < I128(0x0L, 0x0uL)) != mulNeg) {
        Pair(mul1, true)
    } else {
        Pair(mul1, o)
    }
}

public fun mulsi3(a: UInt, b: UInt): UInt {
    val a1 = a
    val b1 = b
    var (a2, b2) = Pair(a1, b1)
    var r: UInt = 0u
    while (a2 > 0) {
        if (a2 and 1 > 0) {
            r = (r + (b2.toUInt()))
        }
        a2 = a2 shr 1
        b2 = b2 shl 1
    }
    return r
}

public fun muldi3(a: ULong, b: ULong): ULong {
    fun muldi3(a1: ULong, b1: ULong): ULong = 0uL
    return muldi3(a, b)
}

public fun multi3(a: I128, b: I128): I128 = a.mul(b)

public fun mulosi4(a: Int, b: Int, oflow: RustMutableReference<Int>): Int {
    val oflow1 = oflow
    val (mul, o) = i32OverflowingMul(a, b)
    (oflow1).write(o.toInt())
    return mul
}

public fun mulodi4(a: Long, b: Long, oflow: RustMutableReference<Int>): Long {
    val oflow1 = oflow
    val (mul, o) = i64OverflowingMul(a, b)
    (oflow1).write(o.toInt())
    return mul
}

public fun muloti4(a: I128, b: I128, oflow: RustMutableReference<Int>): I128 {
    val oflow1 = oflow
    val (mul, o) = i128OverflowingMul(a, b)
    (oflow1).write(o.toInt())
    return mul
}

public fun rustI128Mulo(a: I128, b: I128, oflow: RustMutableReference<Int>): I128 {
    val oflow1 = oflow
    val (mul, o) = i128OverflowingMul(a, b)
    (oflow1).write(o.into())
    return mul
}

public fun rustU128Mulo(a: U128, b: U128, oflow: RustMutableReference<Int>): U128 {
    val oflow1 = oflow
    val (mul, o) = a.mulo(b)
    (oflow1).write(o.into())
    return mul
}
