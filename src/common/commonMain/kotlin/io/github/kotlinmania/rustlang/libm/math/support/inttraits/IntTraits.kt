// port-lint: source math/support/int_traits.rs
package io.github.kotlinmania.rustlang.libm.math.support.inttraits

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.convert.num.tryFrom
import io.github.kotlinmania.rustlang.core.num.absDiff
import io.github.kotlinmania.rustlang.core.num.borrowingSub
import io.github.kotlinmania.rustlang.core.num.carryingAdd
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.overflowingAdd
import io.github.kotlinmania.rustlang.core.num.overflowingSub
import io.github.kotlinmania.rustlang.core.num.wrappingMul
import io.github.kotlinmania.rustlang.core.num.wrappingShr
import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ops.arith.AddAssign
import io.github.kotlinmania.rustlang.core.ops.arith.Div
import io.github.kotlinmania.rustlang.core.ops.arith.DivAssign
import io.github.kotlinmania.rustlang.core.ops.arith.Mul
import io.github.kotlinmania.rustlang.core.ops.arith.MulAssign
import io.github.kotlinmania.rustlang.core.ops.arith.Rem
import io.github.kotlinmania.rustlang.core.ops.arith.RemAssign
import io.github.kotlinmania.rustlang.core.ops.arith.Sub
import io.github.kotlinmania.rustlang.core.ops.arith.SubAssign
import io.github.kotlinmania.rustlang.core.ops.bit.BitAnd
import io.github.kotlinmania.rustlang.core.ops.bit.BitAndAssign
import io.github.kotlinmania.rustlang.core.ops.bit.BitOr
import io.github.kotlinmania.rustlang.core.ops.bit.BitOrAssign
import io.github.kotlinmania.rustlang.core.ops.bit.BitXor
import io.github.kotlinmania.rustlang.core.ops.bit.BitXorAssign
import io.github.kotlinmania.rustlang.core.ops.bit.Not
import io.github.kotlinmania.rustlang.core.ops.bit.Shl
import io.github.kotlinmania.rustlang.core.ops.bit.ShlAssign
import io.github.kotlinmania.rustlang.core.ops.bit.Shr
import io.github.kotlinmania.rustlang.core.ops.bit.ShrAssign
import io.github.kotlinmania.rustlang.libm.math.support.hexfloat.DisplayHex
import kotlin.experimental.or
import kotlin.experimental.xor

// Some constants are only used with tests

/**
 * Minimal integer implementations needed on all integer types, including wide integers.
 */
public interface MinInt<T, out OtherSign, out Unsigned> : BitOr<T, T>, Not<T>, Shl<UInt, T> {
    /**
     * If `Self` is a signed integer
     */
    public val SIGNED: Boolean

    /**
     * The bitwidth of the int type
     */
    public val BITS: UInt

    public val ZERO: T

    public val ONE: T

    public val MIN: T

    public val MAX: T
}

/**
 * Trait for some basic operations on integers
 */
public interface Int<T, out OtherSign, Unsigned> : DisplayHex, AddAssign<T>, SubAssign<T>, MulAssign<T>, DivAssign<T>, RemAssign<T>, BitAndAssign<T>, BitOrAssign<T>, BitXorAssign<T>, ShlAssign<kotlin.Int>, ShrAssign<UInt>, Add<T, T>, Sub<T, T>, Mul<T, T>, Div<T, T>, Rem<T, T>, Shl<kotlin.Int, T>, Shr<kotlin.Int, T>, BitXor<T, T>, BitAnd<T, T>, CastFrom<kotlin.Int, T>, CastInto<kotlin.Int> {
    public fun signed(): OtherSign<Unsigned>

    public fun unsigned(): Unsigned

    public fun fromUnsigned(unsigned: Unsigned): T

    public fun abs(): T

    public fun unsignedAbs(): Unsigned

    public fun fromBool(b: Boolean): T

    /**
     * Prevents the need for excessive conversions between signed and unsigned
     */
    public fun logicalShr(other: UInt): T

    /**
     * Absolute difference between two integers.
     */
    public fun absDiff(other: T): Unsigned

    public fun isZero(): Boolean

    public fun checkedAdd(other: T): T?

    public fun checkedSub(other: T): T?

    public fun wrappingNeg(): T

    public fun wrappingAdd(other: T): T

    public fun wrappingMul(other: T): T

    public fun wrappingSub(other: T): T

    public fun wrappingShl(other: UInt): T

    public fun wrappingShr(other: UInt): T

    public fun rotateLeft(other: UInt): T

    public fun overflowingAdd(other: T): Pair<T, Boolean>

    public fun overflowingSub(other: T): Pair<T, Boolean>

    public fun carryingAdd(other: T, carry: Boolean): Pair<T, Boolean>

    public fun borrowingSub(other: T, borrow: Boolean): Pair<T, Boolean>

    public fun leadingZeros(): UInt

    public fun trailingZeros(): UInt

    public fun countOnes(): UInt

    public fun ilog2(): UInt
}

public val BITS: UInt = kotlin.Int.ZERO.countZeros()

public val SIGNED: Boolean = kotlin.Int.MIN != kotlin.Int.ZERO

public val ZERO: kotlin.Int = 0

public val ONE: kotlin.Int = 1

public val MIN: kotlin.Int = kotlin.Int.MIN

public val MAX: kotlin.Int = kotlin.Int.MAX

public fun kotlin.Int.signed(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun kotlin.Int.unsigned(): kotlin.Int = this

public fun kotlin.Int.abs(): kotlin.Int {
    error("unsupported")
}

public fun kotlin.Int.unsignedAbs(): kotlin.Int {
    error("unsupported")
}

public fun intFromUnsignedInt(me: kotlin.Int): kotlin.Int = me

public fun kotlin.Int.absDiffInt(other: kotlin.Int): kotlin.Int = this.absDiff(other)

public fun intFromBoolInt(b: Boolean): kotlin.Int = ((b as Any?) as kotlin.Int)

public fun kotlin.Int.logicalShr(other: UInt): kotlin.Int = kotlin.Int.fromUnsigned(this.unsigned().wrappingShr(other))

public fun kotlin.Int.isZero(): Boolean = this == kotlin.Int.ZERO

public fun kotlin.Int.checkedAddInt(other: kotlin.Int): kotlin.Int? = this.checkedAdd(other)

public fun kotlin.Int.checkedSubInt(other: kotlin.Int): kotlin.Int? = this.checkedSub(other)

public fun kotlin.Int.wrappingNeg(): kotlin.Int = this.wrappingNeg()

public fun kotlin.Int.wrappingAdd(other: kotlin.Int): kotlin.Int = this.wrappingAdd(other)

public fun kotlin.Int.wrappingMul(other: kotlin.Int): kotlin.Int = this.wrappingMul(other)

public fun kotlin.Int.wrappingSub(other: kotlin.Int): kotlin.Int = this.wrappingSub(other)

public fun kotlin.Int.wrappingShl(other: UInt): kotlin.Int = this.wrappingShl(other)

public fun kotlin.Int.wrappingShr(other: UInt): kotlin.Int = this.wrappingShr(other)

public fun kotlin.Int.rotateLeft(other: UInt): kotlin.Int = this.rotateLeft(other)

public fun kotlin.Int.overflowingAdd(other: kotlin.Int): Pair<kotlin.Int, Boolean> = this.overflowingAdd(other)

public fun kotlin.Int.overflowingSub(other: kotlin.Int): Pair<kotlin.Int, Boolean> = this.overflowingSub(other)

public fun kotlin.Int.leadingZeros(): UInt = this.leadingZeros()

public fun kotlin.Int.trailingZeros(): UInt = this.trailingZeros()

public fun kotlin.Int.countOnes(): UInt = this.countOnes()

public fun kotlin.Int.ilog2(): UInt = this.ilog2()

public fun kotlin.Int.carryingAdd(other: kotlin.Int, carry: Boolean): Pair<kotlin.Int, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(kotlin.Int.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun kotlin.Int.borrowingSub(other: kotlin.Int, borrow: Boolean): Pair<kotlin.Int, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(kotlin.Int.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS1: UInt = kotlin.Int.ZERO.countZeros()

public val SIGNED1: Boolean = kotlin.Int.MIN != kotlin.Int.ZERO

public val ZERO1: kotlin.Int = 0

public val ONE1: kotlin.Int = 1

public val MIN1: kotlin.Int = kotlin.Int.MIN

public val MAX1: kotlin.Int = kotlin.Int.MAX

public fun kotlin.Int.absInt(): kotlin.Int = this.abs()

public fun kotlin.Int.unsignedAbsInt(): kotlin.Int = this.unsignedAbs()

public val BITS2: UInt = UByte.ZERO.countZeros()

public val SIGNED2: Boolean = UByte.MIN_VALUE != UByte.ZERO

public val ZERO2: UByte = 0u

public val ONE2: UByte = 1u

public val MIN2: UByte = UByte.MIN

public val MAX2: UByte = UByte.MAX

public fun UByte.signed(): Byte = this.toByte()

public fun UByte.unsigned(): UByte = this

public fun UByte.abs(): UByte {
    error("unsupported")
}

public fun UByte.unsignedAbs(): UByte {
    error("unsupported")
}

public fun intFromUnsignedUByte(me: UByte): UByte = me

public fun UByte.absDiffInt(other: UByte): UByte = this.absDiff(other)

public fun intFromBoolUByte(b: Boolean): UByte = b.toUByte()

public fun UByte.logicalShr(other: UInt): UByte = UByte.fromUnsigned(this.unsigned().wrappingShr(other))

public fun UByte.isZero(): Boolean = this == UByte.ZERO

public fun UByte.checkedAddInt(other: UByte): UByte? = this.checkedAdd(other)

public fun UByte.checkedSubInt(other: UByte): UByte? = this.checkedSub(other)

public fun UByte.wrappingNeg(): UByte = UByte.wrappingNeg(this)

public fun UByte.wrappingAdd(other: UByte): UByte = UByte.wrappingAdd(this, other)

public fun UByte.wrappingMul(other: UByte): UByte = UByte.wrappingMul(this, other)

public fun UByte.wrappingSub(other: UByte): UByte = UByte.wrappingSub(this, other)

public fun UByte.wrappingShl(other: UInt): UByte = UByte.wrappingShl(this, other)

public fun UByte.wrappingShr(other: UInt): UByte = UByte.wrappingShr(this, other)

public fun UByte.rotateLeft(other: UInt): UByte = UByte.rotateLeft(this, other)

public fun UByte.overflowingAdd(other: UByte): Pair<UByte, Boolean> = UByte.overflowingAdd(this, other)

public fun UByte.overflowingSub(other: UByte): Pair<UByte, Boolean> = UByte.overflowingSub(this, other)

public fun UByte.leadingZeros(): UInt = UByte.leadingZeros(this)

public fun UByte.trailingZeros(): UInt = UByte.trailingZeros(this)

public fun UByte.countOnes(): UInt = UByte.countOnes(this)

public fun UByte.ilog2(): UInt = UByte.ilog2(this)

public fun UByte.carryingAdd(other: UByte, carry: Boolean): Pair<UByte, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(UByte.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun UByte.borrowingSub(other: UByte, borrow: Boolean): Pair<UByte, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(UByte.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS3: UInt = Byte.ZERO.countZeros()

public val SIGNED3: Boolean = Byte.MIN_VALUE != Byte.ZERO

public val ZERO3: Byte = 0

public val ONE3: Byte = 1

public val MIN3: Byte = Byte.MIN

public val MAX3: Byte = Byte.MAX

public fun Byte.signed(): Byte = this

public fun Byte.unsigned(): UByte = this.toUByte()

public fun Byte.absInt(): Byte = this.abs()

public fun Byte.unsignedAbsInt(): UByte = this.unsignedAbs()

public fun intFromUnsignedByte(me: UByte): Byte = me.toByte()

public fun Byte.absDiffInt(other: Byte): UByte = this.absDiff(other)

public fun intFromBoolByte(b: Boolean): Byte = b.toByte()

public fun Byte.logicalShr(other: UInt): Byte = Byte.fromUnsigned(this.unsigned().wrappingShr(other))

public fun Byte.isZero(): Boolean = this == Byte.ZERO

public fun Byte.checkedAddInt(other: Byte): Byte? = this.checkedAdd(other)

public fun Byte.checkedSubInt(other: Byte): Byte? = this.checkedSub(other)

public fun Byte.wrappingNeg(): Byte = Byte.wrappingNeg(this)

public fun Byte.wrappingAdd(other: Byte): Byte = Byte.wrappingAdd(this, other)

public fun Byte.wrappingMul(other: Byte): Byte = Byte.wrappingMul(this, other)

public fun Byte.wrappingSub(other: Byte): Byte = Byte.wrappingSub(this, other)

public fun Byte.wrappingShl(other: UInt): Byte = Byte.wrappingShl(this, other)

public fun Byte.wrappingShr(other: UInt): Byte = Byte.wrappingShr(this, other)

public fun Byte.rotateLeft(other: UInt): Byte = Byte.rotateLeft(this, other)

public fun Byte.overflowingAdd(other: Byte): Pair<Byte, Boolean> = Byte.overflowingAdd(this, other)

public fun Byte.overflowingSub(other: Byte): Pair<Byte, Boolean> = Byte.overflowingSub(this, other)

public fun Byte.leadingZeros(): UInt = Byte.leadingZeros(this)

public fun Byte.trailingZeros(): UInt = Byte.trailingZeros(this)

public fun Byte.countOnes(): UInt = Byte.countOnes(this)

public fun Byte.ilog2(): UInt = Byte.ilog2(this)

public fun Byte.carryingAdd(other: Byte, carry: Boolean): Pair<Byte, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(Byte.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun Byte.borrowingSub(other: Byte, borrow: Boolean): Pair<Byte, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(Byte.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS4: UInt = UShort.ZERO.countZeros()

public val SIGNED4: Boolean = UShort.MIN_VALUE != UShort.ZERO

public val ZERO4: UShort = 0u

public val ONE4: UShort = 1u

public val MIN4: UShort = UShort.MIN

public val MAX4: UShort = UShort.MAX

public fun UShort.signed(): Short = this.toShort()

public fun UShort.unsigned(): UShort = this

public fun UShort.abs(): UShort {
    error("unsupported")
}

public fun UShort.unsignedAbs(): UShort {
    error("unsupported")
}

public fun intFromUnsignedUShort(me: UShort): UShort = me

public fun UShort.absDiffInt(other: UShort): UShort = this.absDiff(other)

public fun intFromBoolUShort(b: Boolean): UShort = b.toUShort()

public fun UShort.logicalShr(other: UInt): UShort = UShort.fromUnsigned(this.unsigned().wrappingShr(other))

public fun UShort.isZero(): Boolean = this == UShort.ZERO

public fun UShort.checkedAddInt(other: UShort): UShort? = this.checkedAdd(other)

public fun UShort.checkedSubInt(other: UShort): UShort? = this.checkedSub(other)

public fun UShort.wrappingNeg(): UShort = UShort.wrappingNeg(this)

public fun UShort.wrappingAdd(other: UShort): UShort = UShort.wrappingAdd(this, other)

public fun UShort.wrappingMul(other: UShort): UShort = UShort.wrappingMul(this, other)

public fun UShort.wrappingSub(other: UShort): UShort = UShort.wrappingSub(this, other)

public fun UShort.wrappingShl(other: UInt): UShort = UShort.wrappingShl(this, other)

public fun UShort.wrappingShr(other: UInt): UShort = UShort.wrappingShr(this, other)

public fun UShort.rotateLeft(other: UInt): UShort = UShort.rotateLeft(this, other)

public fun UShort.overflowingAdd(other: UShort): Pair<UShort, Boolean> = UShort.overflowingAdd(this, other)

public fun UShort.overflowingSub(other: UShort): Pair<UShort, Boolean> = UShort.overflowingSub(this, other)

public fun UShort.leadingZeros(): UInt = UShort.leadingZeros(this)

public fun UShort.trailingZeros(): UInt = UShort.trailingZeros(this)

public fun UShort.countOnes(): UInt = UShort.countOnes(this)

public fun UShort.ilog2(): UInt = UShort.ilog2(this)

public fun UShort.carryingAdd(other: UShort, carry: Boolean): Pair<UShort, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(UShort.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun UShort.borrowingSub(other: UShort, borrow: Boolean): Pair<UShort, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(UShort.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS5: UInt = Short.ZERO.countZeros()

public val SIGNED5: Boolean = Short.MIN_VALUE != Short.ZERO

public val ZERO5: Short = 0

public val ONE5: Short = 1

public val MIN5: Short = Short.MIN

public val MAX5: Short = Short.MAX

public fun Short.signed(): Short = this

public fun Short.unsigned(): UShort = this.toUShort()

public fun Short.absInt(): Short = this.abs()

public fun Short.unsignedAbsInt(): UShort = this.unsignedAbs()

public fun intFromUnsignedShort(me: UShort): Short = me.toShort()

public fun Short.absDiffInt(other: Short): UShort = this.absDiff(other)

public fun intFromBoolShort(b: Boolean): Short = b.toShort()

public fun Short.logicalShr(other: UInt): Short = Short.fromUnsigned(this.unsigned().wrappingShr(other))

public fun Short.isZero(): Boolean = this == Short.ZERO

public fun Short.checkedAddInt(other: Short): Short? = this.checkedAdd(other)

public fun Short.checkedSubInt(other: Short): Short? = this.checkedSub(other)

public fun Short.wrappingNeg(): Short = Short.wrappingNeg(this)

public fun Short.wrappingAdd(other: Short): Short = Short.wrappingAdd(this, other)

public fun Short.wrappingMul(other: Short): Short = Short.wrappingMul(this, other)

public fun Short.wrappingSub(other: Short): Short = Short.wrappingSub(this, other)

public fun Short.wrappingShl(other: UInt): Short = Short.wrappingShl(this, other)

public fun Short.wrappingShr(other: UInt): Short = Short.wrappingShr(this, other)

public fun Short.rotateLeft(other: UInt): Short = Short.rotateLeft(this, other)

public fun Short.overflowingAdd(other: Short): Pair<Short, Boolean> = Short.overflowingAdd(this, other)

public fun Short.overflowingSub(other: Short): Pair<Short, Boolean> = Short.overflowingSub(this, other)

public fun Short.leadingZeros(): UInt = Short.leadingZeros(this)

public fun Short.trailingZeros(): UInt = Short.trailingZeros(this)

public fun Short.countOnes(): UInt = Short.countOnes(this)

public fun Short.ilog2(): UInt = Short.ilog2(this)

public fun Short.carryingAdd(other: Short, carry: Boolean): Pair<Short, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(Short.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun Short.borrowingSub(other: Short, borrow: Boolean): Pair<Short, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(Short.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS6: UInt = UInt.ZERO.countZeros()

public val SIGNED6: Boolean = UInt.MIN_VALUE != UInt.ZERO

public val ZERO6: UInt = 0u

public val ONE6: UInt = 1u

public val MIN6: UInt = UInt.MIN

public val MAX6: UInt = UInt.MAX

public fun UInt.signed(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun UInt.unsigned(): UInt = this

public fun UInt.abs(): UInt {
    error("unsupported")
}

public fun UInt.unsignedAbs(): UInt {
    error("unsupported")
}

public fun intFromUnsignedUInt(me: UInt): UInt = me

public fun UInt.absDiffInt(other: UInt): UInt = this.absDiff(other)

public fun intFromBoolUInt(b: Boolean): UInt = b.toUInt()

public fun UInt.logicalShr(other: UInt): UInt = UInt.fromUnsigned(this.unsigned().wrappingShr(other))

public fun UInt.isZero(): Boolean = this == UInt.ZERO

public fun UInt.checkedAddInt(other: UInt): UInt? = this.checkedAdd(other)

public fun UInt.checkedSubInt(other: UInt): UInt? = this.checkedSub(other)

public fun UInt.wrappingNeg(): UInt = UInt.wrappingNeg(this)

public fun UInt.wrappingAdd(other: UInt): UInt = UInt.wrappingAdd(this, other)

public fun UInt.wrappingMul(other: UInt): UInt = UInt.wrappingMul(this, other)

public fun UInt.wrappingSub(other: UInt): UInt = UInt.wrappingSub(this, other)

public fun UInt.wrappingShl(other: UInt): UInt = UInt.wrappingShl(this, other)

public fun UInt.wrappingShr(other: UInt): UInt = UInt.wrappingShr(this, other)

public fun UInt.rotateLeft(other: UInt): UInt = UInt.rotateLeft(this, other)

public fun UInt.overflowingAdd(other: UInt): Pair<UInt, Boolean> = UInt.overflowingAdd(this, other)

public fun UInt.overflowingSub(other: UInt): Pair<UInt, Boolean> = UInt.overflowingSub(this, other)

public fun UInt.leadingZeros(): UInt = UInt.leadingZeros(this)

public fun UInt.trailingZeros(): UInt = UInt.trailingZeros(this)

public fun UInt.countOnes(): UInt = UInt.countOnes(this)

public fun UInt.ilog2(): UInt = UInt.ilog2(this)

public fun UInt.carryingAdd(other: UInt, carry: Boolean): Pair<UInt, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(UInt.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun UInt.borrowingSub(other: UInt, borrow: Boolean): Pair<UInt, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(UInt.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS7: UInt = kotlin.Int.ZERO.countZeros()

public val SIGNED7: Boolean = kotlin.Int.MIN != kotlin.Int.ZERO

public val ZERO7: kotlin.Int = 0

public val ONE7: kotlin.Int = 1

public val MIN7: kotlin.Int = kotlin.Int.MIN

public val MAX7: kotlin.Int = kotlin.Int.MAX

public fun intFromUnsignedInt(me: UInt): kotlin.Int = ((me as Any?) as kotlin.Int)

public val BITS8: UInt = ULong.ZERO.countZeros()

public val SIGNED8: Boolean = ULong.MIN_VALUE != ULong.ZERO

public val ZERO8: ULong = 0uL

public val ONE8: ULong = 1uL

public val MIN8: ULong = ULong.MIN

public val MAX8: ULong = ULong.MAX

public fun ULong.signed(): Long = this.toLong()

public fun ULong.unsigned(): ULong = this

public fun ULong.abs(): ULong {
    error("unsupported")
}

public fun ULong.unsignedAbs(): ULong {
    error("unsupported")
}

public fun intFromUnsignedULong(me: ULong): ULong = me

public fun ULong.absDiffInt(other: ULong): ULong = this.absDiff(other)

public fun intFromBoolULong(b: Boolean): ULong = b.toULong()

public fun ULong.logicalShr(other: UInt): ULong = ULong.fromUnsigned(this.unsigned().wrappingShr(other))

public fun ULong.isZero(): Boolean = this == ULong.ZERO

public fun ULong.checkedAddInt(other: ULong): ULong? = this.checkedAdd(other)

public fun ULong.checkedSubInt(other: ULong): ULong? = this.checkedSub(other)

public fun ULong.wrappingNeg(): ULong = ULong.wrappingNeg(this)

public fun ULong.wrappingAdd(other: ULong): ULong = ULong.wrappingAdd(this, other)

public fun ULong.wrappingMul(other: ULong): ULong = ULong.wrappingMul(this, other)

public fun ULong.wrappingSub(other: ULong): ULong = ULong.wrappingSub(this, other)

public fun ULong.wrappingShl(other: UInt): ULong = ULong.wrappingShl(this, other)

public fun ULong.wrappingShr(other: UInt): ULong = ULong.wrappingShr(this, other)

public fun ULong.rotateLeft(other: UInt): ULong = ULong.rotateLeft(this, other)

public fun ULong.overflowingAdd(other: ULong): Pair<ULong, Boolean> = ULong.overflowingAdd(this, other)

public fun ULong.overflowingSub(other: ULong): Pair<ULong, Boolean> = ULong.overflowingSub(this, other)

public fun ULong.leadingZeros(): UInt = ULong.leadingZeros(this)

public fun ULong.trailingZeros(): UInt = ULong.trailingZeros(this)

public fun ULong.countOnes(): UInt = ULong.countOnes(this)

public fun ULong.ilog2(): UInt = ULong.ilog2(this)

public fun ULong.carryingAdd(other: ULong, carry: Boolean): Pair<ULong, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(ULong.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun ULong.borrowingSub(other: ULong, borrow: Boolean): Pair<ULong, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(ULong.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS9: UInt = Long.ZERO.countZeros()

public val SIGNED9: Boolean = Long.MIN_VALUE != Long.ZERO

public val ZERO9: Long = 0L

public val ONE9: Long = 1L

public val MIN9: Long = Long.MIN

public val MAX9: Long = Long.MAX

public fun Long.signed(): Long = this

public fun Long.unsigned(): ULong = this.toULong()

public fun Long.absInt(): Long = this.abs()

public fun Long.unsignedAbsInt(): ULong = this.unsignedAbs()

public fun intFromUnsignedLong(me: ULong): Long = me.toLong()

public fun Long.absDiffInt(other: Long): ULong = this.absDiff(other)

public fun intFromBoolLong(b: Boolean): Long = b.toLong()

public fun Long.logicalShr(other: UInt): Long = Long.fromUnsigned(this.unsigned().wrappingShr(other))

public fun Long.isZero(): Boolean = this == Long.ZERO

public fun Long.checkedAddInt(other: Long): Long? = this.checkedAdd(other)

public fun Long.checkedSubInt(other: Long): Long? = this.checkedSub(other)

public fun Long.wrappingNeg(): Long = Long.wrappingNeg(this)

public fun Long.wrappingAdd(other: Long): Long = Long.wrappingAdd(this, other)

public fun Long.wrappingMul(other: Long): Long = Long.wrappingMul(this, other)

public fun Long.wrappingSub(other: Long): Long = Long.wrappingSub(this, other)

public fun Long.wrappingShl(other: UInt): Long = Long.wrappingShl(this, other)

public fun Long.wrappingShr(other: UInt): Long = Long.wrappingShr(this, other)

public fun Long.rotateLeft(other: UInt): Long = Long.rotateLeft(this, other)

public fun Long.overflowingAdd(other: Long): Pair<Long, Boolean> = Long.overflowingAdd(this, other)

public fun Long.overflowingSub(other: Long): Pair<Long, Boolean> = Long.overflowingSub(this, other)

public fun Long.leadingZeros(): UInt = Long.leadingZeros(this)

public fun Long.trailingZeros(): UInt = Long.trailingZeros(this)

public fun Long.countOnes(): UInt = Long.countOnes(this)

public fun Long.ilog2(): UInt = Long.ilog2(this)

public fun Long.carryingAdd(other: Long, carry: Boolean): Pair<Long, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(Long.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun Long.borrowingSub(other: Long, borrow: Boolean): Pair<Long, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(Long.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS10: UInt = U128.ZERO.countZeros()

public val SIGNED10: Boolean = U128.MIN_VALUE != U128.ZERO

public val ZERO10: U128 = U128(0x0uL, 0x0uL)

public val ONE10: U128 = U128(0x0uL, 0x1uL)

public val MIN10: U128 = U128.MIN

public val MAX10: U128 = U128.MAX

public fun U128.signed(): I128 = this.toI128()

public fun U128.unsigned(): U128 = this

public fun U128.abs(): U128 {
    error("unsupported")
}

public fun U128.unsignedAbs(): U128 {
    error("unsupported")
}

public fun intFromUnsignedU128(me: U128): U128 = me

public fun U128.absDiffInt(other: U128): U128 = this.absDiff(other)

public fun intFromBoolU128(b: Boolean): U128 = U128.fromLong(b.toLong())

public fun U128.logicalShr(other: UInt): U128 = U128.fromUnsigned(this.unsigned().wrappingShr(other))

public fun U128.isZero(): Boolean = this == U128.ZERO

public fun U128.checkedAddInt(other: U128): U128? = this.checkedAdd(other)

public fun U128.checkedSubInt(other: U128): U128? = this.checkedSub(other)

public fun U128.wrappingNeg(): U128 = U128.wrappingNeg(this)

public fun U128.wrappingAdd(other: U128): U128 = U128.wrappingAdd(this, other)

public fun U128.wrappingMul(other: U128): U128 = U128.wrappingMul(this, other)

public fun U128.wrappingSub(other: U128): U128 = U128.wrappingSub(this, other)

public fun U128.wrappingShl(other: UInt): U128 = U128.wrappingShl(this, other)

public fun U128.wrappingShr(other: UInt): U128 = U128.wrappingShr(this, other)

public fun U128.rotateLeft(other: UInt): U128 = U128.rotateLeft(this, other)

public fun U128.overflowingAdd(other: U128): Pair<U128, Boolean> = U128.overflowingAdd(this, other)

public fun U128.overflowingSub(other: U128): Pair<U128, Boolean> = U128.overflowingSub(this, other)

public fun U128.leadingZeros(): UInt = U128.leadingZeros(this)

public fun U128.trailingZeros(): UInt = U128.trailingZeros(this)

public fun U128.countOnes(): UInt = U128.countOnes(this)

public fun U128.ilog2(): UInt = U128.ilog2(this)

public fun U128.carryingAdd(other: U128, carry: Boolean): Pair<U128, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(U128.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun U128.borrowingSub(other: U128, borrow: Boolean): Pair<U128, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(U128.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

public val BITS11: UInt = I128.ZERO.countZeros()

public val SIGNED11: Boolean = I128.MIN_VALUE != I128.ZERO

public val ZERO11: I128 = I128(0x0L, 0x0uL)

public val ONE11: I128 = I128(0x0L, 0x1uL)

public val MIN11: I128 = I128.MIN

public val MAX11: I128 = I128.MAX

public fun I128.signed(): I128 = this

public fun I128.unsigned(): U128 = this.toU128()

public fun I128.absInt(): I128 = this.abs()

public fun I128.unsignedAbsInt(): U128 = this.unsignedAbs()

public fun intFromUnsignedI128(me: U128): I128 = me.toI128()

public fun I128.absDiffInt(other: I128): U128 = this.absDiff(other)

public fun intFromBoolI128(b: Boolean): I128 = I128.fromLong(b.toLong())

public fun I128.logicalShr(other: UInt): I128 = I128.fromUnsigned(this.unsigned().wrappingShr(other))

public fun I128.isZero(): Boolean = this == I128.ZERO

public fun I128.checkedAddInt(other: I128): I128? = this.checkedAdd(other)

public fun I128.checkedSubInt(other: I128): I128? = this.checkedSub(other)

public fun I128.wrappingNeg(): I128 = I128.wrappingNeg(this)

public fun I128.wrappingAdd(other: I128): I128 = I128.wrappingAdd(this, other)

public fun I128.wrappingMul(other: I128): I128 = I128.wrappingMul(this, other)

public fun I128.wrappingSub(other: I128): I128 = I128.wrappingSub(this, other)

public fun I128.wrappingShl(other: UInt): I128 = I128.wrappingShl(this, other)

public fun I128.wrappingShr(other: UInt): I128 = I128.wrappingShr(this, other)

public fun I128.rotateLeft(other: UInt): I128 = I128.rotateLeft(this, other)

public fun I128.overflowingAdd(other: I128): Pair<I128, Boolean> = I128.overflowingAdd(this, other)

public fun I128.overflowingSub(other: I128): Pair<I128, Boolean> = I128.overflowingSub(this, other)

public fun I128.leadingZeros(): UInt = I128.leadingZeros(this)

public fun I128.trailingZeros(): UInt = I128.trailingZeros(this)

public fun I128.countOnes(): UInt = I128.countOnes(this)

public fun I128.ilog2(): UInt = I128.ilog2(this)

public fun I128.carryingAdd(other: I128, carry: Boolean): Pair<I128, Boolean> {
    val (ab, of1) = this.overflowingAdd(other)
    val (abc, of2) = ab.overflowingAdd(I128.fromBool(carry))
    return Pair(abc, of1 xor of2)
}

public fun I128.borrowingSub(other: I128, borrow: Boolean): Pair<I128, Boolean> {
    val (ab, of1) = this.overflowingSub(other)
    val (abc, of2) = ab.overflowingSub(I128.fromBool(borrow))
    return Pair(abc, of1 xor of2)
}

/**
 * Trait for integers twice the bit width of another integer. This is implemented for all
 * primitives except for `UByte`, because there is not a smaller primitive.
 */
public interface DInt<T, H, out OtherSign, out Unsigned> : Add<T, T>, Sub<T, T>, Shl<UInt, T>, Shr<UInt, T> {
    /**
     * Returns the low half of `self`
     */
    public fun lo(): H

    /**
     * Returns the high half of `self`
     */
    public fun hi(): H

    /**
     * Returns the low and high halves of `self` as a tuple
     */
    public fun loHi(): Pair<H, H> = Pair(this.lo(), this.hi())

    /**
     * Constructs an integer using lower and higher half parts
     */
    public fun fromLoHi(lo: H, hi: H): T = lo.zeroWiden() or hi.widenHi()
}

/**
 * Trait for integers half the bit width of another integer. This is implemented for all
 * primitives except for `ULong`, because it there is not a larger primitive.
 */
public interface HInt<T, out D, out OtherSign, Unsigned> {
    /**
     * Widens (using default extension) the integer to have double bit width
     */
    public fun widen(): D

    /**
     * Widens (zero extension only) the integer to have double bit width. This is needed to get
     * around problems with associated type bounds (such as `Int<Othersign: DInt>`) being unstable
     */
    public fun zeroWiden(): D

    /**
     * Widens the integer to have double bit width and shifts the integer into the higher bits
     */
    public fun widenHi(): D

    /**
     * Widening multiplication with zero widening. This cannot overflow.
     */
    public fun zeroWidenMul(rhs: T): D

    /**
     * Widening multiplication. This cannot overflow.
     */
    public fun widenMul(rhs: T): D
}

public fun UShort.lo(): UByte = this.toUByte()

public fun UShort.hi(): UByte = ((this.toUInt() shr (minIntBitsUByte.toInt())).toUShort()).toUByte()

public fun UInt.lo(): UShort = this.toUShort()

public fun UInt.hi(): UShort = (this shr minIntBitsUShort.toInt()).toUShort()

public fun ULong.lo(): UInt = this.toUInt()

public fun ULong.hi(): UInt = (this shr minIntBitsUInt.toInt()).toUInt()

public fun U128.lo(): ULong = this.toULong()

public fun U128.hi(): ULong = (this shr minIntBitsULong.toInt()).toULong()

public fun Short.lo(): Byte = this.toByte()

public fun Short.hi(): Byte = ((this.toInt() shr (minIntBitsByte.toInt())).toShort()).toByte()

public fun kotlin.Int.lo(): Short = this.toShort()

public fun kotlin.Int.hi(): Short = (this shr minIntBitsShort.toInt()).toShort()

public fun Long.lo(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Long.hi(): kotlin.Int = (((this shr minIntBitsInt.toInt()) as Any?) as kotlin.Int)

public fun I128.lo(): Long = this.toLong()

public fun I128.hi(): Long = (this shr minIntBitsLong.toInt()).toLong()

public fun UByte.widen(): UShort = this.toUShort()

public fun UByte.zeroWiden(): UShort = (this).toUShort()

public fun UByte.zeroWidenMul(rhs: UByte): UShort = (this.zeroWiden() * rhs.zeroWiden().toUShort()).toUShort()

public fun UByte.widenMul(rhs: UByte): UShort = (this.widen() * rhs.widen().toUShort()).toUShort()

public fun UByte.widenHi(): UShort = ((this.toUShort()).toUInt() shl (UByte.BITS.toInt())).toUShort()

public fun UShort.widen(): UInt = this.toUInt()

public fun UShort.zeroWiden(): UInt = (this).toUInt()

public fun UShort.zeroWidenMul(rhs: UShort): UInt = (this.zeroWiden() * rhs.zeroWiden().toUInt())

public fun UShort.widenMul(rhs: UShort): UInt = (this.widen() * rhs.widen().toUInt())

public fun UShort.widenHi(): UInt = (this.toUInt()) shl UShort.BITS.toInt()

public fun UInt.widen(): ULong = this.toULong()

public fun UInt.zeroWiden(): ULong = (this).toULong()

public fun UInt.zeroWidenMul(rhs: UInt): ULong = (this.zeroWiden() * rhs.zeroWiden().toULong())

public fun UInt.widenMul(rhs: UInt): ULong = (this.widen() * rhs.widen().toULong())

public fun UInt.widenHi(): ULong = (this.toULong()) shl UInt.BITS.toInt()

public fun ULong.widen(): U128 = U128.fromULong(this)

public fun ULong.zeroWiden(): U128 = U128.fromULong((this))

public fun ULong.zeroWidenMul(rhs: ULong): U128 = this.zeroWiden().wrappingMul(rhs.zeroWiden())

public fun ULong.widenMul(rhs: ULong): U128 = this.widen().wrappingMul(rhs.widen())

public fun ULong.widenHi(): U128 = (U128.fromULong(this)) shl ULong.BITS.toInt()

public fun Byte.widen(): Short = this.toShort()

public fun Byte.zeroWiden(): Short = (this.toUByte()).toShort()

public fun Byte.zeroWidenMul(rhs: Byte): Short = (this.zeroWiden() * rhs.zeroWiden().toShort()).toShort()

public fun Byte.widenMul(rhs: Byte): Short = (this.widen() * rhs.widen().toShort()).toShort()

public fun Byte.widenHi(): Short = ((this.toShort()).toInt() shl (Byte.BITS.toInt())).toShort()

public fun Short.widen(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Short.zeroWiden(): kotlin.Int = (((this.toUShort()) as Any?) as kotlin.Int)

public fun Short.zeroWidenMul(rhs: Short): kotlin.Int = this.zeroWiden().wrappingMul(rhs.zeroWiden())

public fun Short.widenMul(rhs: Short): kotlin.Int = this.widen().wrappingMul(rhs.widen())

public fun Short.widenHi(): kotlin.Int = (((this as Any?) as kotlin.Int)) shl Short.BITS.toInt()

public fun kotlin.Int.widen(): Long = this.toLong()

public fun kotlin.Int.zeroWiden(): Long = (this.toUInt()).toLong()

public fun kotlin.Int.zeroWidenMul(rhs: kotlin.Int): Long = (this.zeroWiden() * rhs.zeroWiden().toLong())

public fun kotlin.Int.widenMul(rhs: kotlin.Int): Long = (this.widen() * rhs.widen().toLong())

public fun kotlin.Int.widenHi(): Long = (this.toLong()) shl kotlin.Int.BITS.toInt()

public fun Long.widen(): I128 = I128.fromLong(this)

public fun Long.zeroWiden(): I128 = I128.fromULong((this.toULong()))

public fun Long.zeroWidenMul(rhs: Long): I128 = this.zeroWiden().wrappingMul(rhs.zeroWiden())

public fun Long.widenMul(rhs: Long): I128 = this.widen().wrappingMul(rhs.widen())

public fun Long.widenHi(): I128 = (I128.fromLong(this)) shl Long.BITS.toInt()

/**
 * Trait to express (possibly lossy) casting of integers
 */
public interface CastInto<T> {
    /**
     * By default, casts should be exact.
     */
    public fun cast(): T

    /**
     * Call for casts that are expected to truncate.
     *
     * In practice, this is exactly the same as `cast`; the main difference is to document intent
     * in code. `cast` may panic in debug mode.
     */
    public fun castLossy(): T
}

public interface CastFrom<T, SelfType> {
    /**
     * By default, casts should be exact.
     */
    public fun castFrom(value: T): SelfType

    /**
     * Call for casts that are expected to truncate.
     */
    public fun castFromLossy(value: T): SelfType
}

public fun <T, U : CastInto<T>> castFromT(value: U): T = value.cast()

public fun <T, U : CastInto<T>> castFromCastFromLossyT(value: U): T = value.castLossy()

public fun kotlin.Int.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun kotlin.Int.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun kotlin.Int.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun kotlin.Int.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun kotlin.Int.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun kotlin.Int.castLossyUByte(): UByte = this.toUByte()

public fun kotlin.Int.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun kotlin.Int.castLossyByte(): Byte = this.toByte()

public fun kotlin.Int.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun kotlin.Int.castLossyUShort(): UShort = this.toUShort()

public fun kotlin.Int.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun kotlin.Int.castLossyShort(): Short = this.toShort()

public fun kotlin.Int.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun kotlin.Int.castLossyUInt(): UInt = this.toUInt()

public fun kotlin.Int.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun kotlin.Int.castLossyULong(): ULong = this.toULong()

public fun kotlin.Int.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun kotlin.Int.castLossyLong(): Long = this.toLong()

public fun kotlin.Int.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return U128.fromLong(this.toLong())
}

public fun kotlin.Int.castLossyU128(): U128 = U128.fromLong(this.toLong())

public fun kotlin.Int.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return I128.fromLong(this.toLong())
}

public fun kotlin.Int.castLossyI128(): I128 = I128.fromLong(this.toLong())

public fun UByte.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun UByte.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun UByte.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun UByte.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun UByte.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun UByte.castLossyUByte(): UByte = this

public fun UByte.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun UByte.castLossyByte(): Byte = this.toByte()

public fun UByte.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun UByte.castLossyUShort(): UShort = this.toUShort()

public fun UByte.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun UByte.castLossyShort(): Short = this.toShort()

public fun UByte.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun UByte.castLossyUInt(): UInt = this.toUInt()

public fun UByte.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun UByte.castLossyULong(): ULong = this.toULong()

public fun UByte.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun UByte.castLossyLong(): Long = this.toLong()

public fun UByte.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return U128.fromUByte(this)
}

public fun UByte.castLossyU128(): U128 = U128.fromUByte(this)

public fun UByte.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return I128.fromUByte(this)
}

public fun UByte.castLossyI128(): I128 = I128.fromUByte(this)

public fun Byte.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun Byte.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Byte.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun Byte.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Byte.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun Byte.castLossyUByte(): UByte = this.toUByte()

public fun Byte.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun Byte.castLossyByte(): Byte = this

public fun Byte.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun Byte.castLossyUShort(): UShort = this.toUShort()

public fun Byte.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun Byte.castLossyShort(): Short = this.toShort()

public fun Byte.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun Byte.castLossyUInt(): UInt = this.toUInt()

public fun Byte.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun Byte.castLossyULong(): ULong = this.toULong()

public fun Byte.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun Byte.castLossyLong(): Long = this.toLong()

public fun Byte.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return U128.fromByte(this)
}

public fun Byte.castLossyU128(): U128 = U128.fromByte(this)

public fun Byte.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return I128.fromByte(this)
}

public fun Byte.castLossyI128(): I128 = I128.fromByte(this)

public fun UShort.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun UShort.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun UShort.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun UShort.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun UShort.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun UShort.castLossyUByte(): UByte = this.toUByte()

public fun UShort.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun UShort.castLossyByte(): Byte = this.toByte()

public fun UShort.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun UShort.castLossyUShort(): UShort = this

public fun UShort.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun UShort.castLossyShort(): Short = this.toShort()

public fun UShort.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun UShort.castLossyUInt(): UInt = this.toUInt()

public fun UShort.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun UShort.castLossyULong(): ULong = this.toULong()

public fun UShort.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun UShort.castLossyLong(): Long = this.toLong()

public fun UShort.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return U128.fromUShort(this)
}

public fun UShort.castLossyU128(): U128 = U128.fromUShort(this)

public fun UShort.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return I128.fromUShort(this)
}

public fun UShort.castLossyI128(): I128 = I128.fromUShort(this)

public fun Short.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun Short.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Short.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun Short.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Short.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun Short.castLossyUByte(): UByte = this.toUByte()

public fun Short.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun Short.castLossyByte(): Byte = this.toByte()

public fun Short.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun Short.castLossyUShort(): UShort = this.toUShort()

public fun Short.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun Short.castLossyShort(): Short = this

public fun Short.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun Short.castLossyUInt(): UInt = this.toUInt()

public fun Short.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun Short.castLossyULong(): ULong = this.toULong()

public fun Short.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun Short.castLossyLong(): Long = this.toLong()

public fun Short.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return U128.fromShort(this)
}

public fun Short.castLossyU128(): U128 = U128.fromShort(this)

public fun Short.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return I128.fromShort(this)
}

public fun Short.castLossyI128(): I128 = I128.fromShort(this)

public fun UInt.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun UInt.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun UInt.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun UInt.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun UInt.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun UInt.castLossyUByte(): UByte = this.toUByte()

public fun UInt.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun UInt.castLossyByte(): Byte = this.toByte()

public fun UInt.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun UInt.castLossyUShort(): UShort = this.toUShort()

public fun UInt.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun UInt.castLossyShort(): Short = this.toShort()

public fun UInt.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun UInt.castLossyUInt(): UInt = this

public fun UInt.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun UInt.castLossyULong(): ULong = this.toULong()

public fun UInt.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun UInt.castLossyLong(): Long = this.toLong()

public fun UInt.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return U128.fromUInt(this)
}

public fun UInt.castLossyU128(): U128 = U128.fromUInt(this)

public fun UInt.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return I128.fromUInt(this)
}

public fun UInt.castLossyI128(): I128 = I128.fromUInt(this)

public fun ULong.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun ULong.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun ULong.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun ULong.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun ULong.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun ULong.castLossyUByte(): UByte = this.toUByte()

public fun ULong.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun ULong.castLossyByte(): Byte = this.toByte()

public fun ULong.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun ULong.castLossyUShort(): UShort = this.toUShort()

public fun ULong.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun ULong.castLossyShort(): Short = this.toShort()

public fun ULong.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun ULong.castLossyUInt(): UInt = this.toUInt()

public fun ULong.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun ULong.castLossyULong(): ULong = this

public fun ULong.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun ULong.castLossyLong(): Long = this.toLong()

public fun ULong.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return U128.fromULong(this)
}

public fun ULong.castLossyU128(): U128 = U128.fromULong(this)

public fun ULong.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return I128.fromULong(this)
}

public fun ULong.castLossyI128(): I128 = I128.fromULong(this)

public fun Long.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun Long.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Long.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun Long.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Long.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun Long.castLossyUByte(): UByte = this.toUByte()

public fun Long.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun Long.castLossyByte(): Byte = this.toByte()

public fun Long.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun Long.castLossyUShort(): UShort = this.toUShort()

public fun Long.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun Long.castLossyShort(): Short = this.toShort()

public fun Long.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun Long.castLossyUInt(): UInt = this.toUInt()

public fun Long.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun Long.castLossyULong(): ULong = this.toULong()

public fun Long.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun Long.castLossyLong(): Long = this

public fun Long.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return U128.fromLong(this)
}

public fun Long.castLossyU128(): U128 = U128.fromLong(this)

public fun Long.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return I128.fromLong(this)
}

public fun Long.castLossyI128(): I128 = I128.fromLong(this)

public fun U128.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun U128.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun U128.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun U128.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun U128.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun U128.castLossyUByte(): UByte = this.toUByte()

public fun U128.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun U128.castLossyByte(): Byte = this.toByte()

public fun U128.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun U128.castLossyUShort(): UShort = this.toUShort()

public fun U128.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun U128.castLossyShort(): Short = this.toShort()

public fun U128.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun U128.castLossyUInt(): UInt = this.toUInt()

public fun U128.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun U128.castLossyULong(): ULong = this.toULong()

public fun U128.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun U128.castLossyLong(): Long = this.toLong()

public fun U128.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun U128.castLossyU128(): U128 = this

public fun U128.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toI128()
}

public fun U128.castLossyI128(): I128 = this.toI128()

public fun I128.cast(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun I128.castLossy(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun I128.castInt(): kotlin.Int {
    check(kotlin.Int.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return ((this as Any?) as kotlin.Int)
}

public fun I128.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun I128.castUByte(): UByte {
    check(UByte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUByte()
}

public fun I128.castLossyUByte(): UByte = this.toUByte()

public fun I128.castByte(): Byte {
    check(Byte.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toByte()
}

public fun I128.castLossyByte(): Byte = this.toByte()

public fun I128.castUShort(): UShort {
    check(UShort.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUShort()
}

public fun I128.castLossyUShort(): UShort = this.toUShort()

public fun I128.castShort(): Short {
    check(Short.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toShort()
}

public fun I128.castLossyShort(): Short = this.toShort()

public fun I128.castUInt(): UInt {
    check(UInt.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toUInt()
}

public fun I128.castLossyUInt(): UInt = this.toUInt()

public fun I128.castULong(): ULong {
    check(ULong.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toULong()
}

public fun I128.castLossyULong(): ULong = this.toULong()

public fun I128.castLong(): Long {
    check(Long.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toLong()
}

public fun I128.castLossyLong(): Long = this.toLong()

public fun I128.castU128(): U128 {
    check(U128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this.toU128()
}

public fun I128.castLossyU128(): U128 = this.toU128()

public fun I128.castI128(): I128 {
    check(I128.tryFrom(this).isOk()) { "failed cast from ${this}" }
    return this
}

public fun I128.castLossyI128(): I128 = this

public fun Byte.castF16(): F16 {
    check((F16.fromByte(this)).toByte() == this)
    return F16.fromByte(this)
}

public fun Byte.castLossyF16(): F16 = F16.fromByte(this)

public fun Byte.castFloat(): kotlin.Float {
    check((this.toFloat()).toByte() == this)
    return this.toFloat()
}

public fun Byte.castLossyFloat(): kotlin.Float = this.toFloat()

public fun Byte.castDouble(): Double {
    check((this.toDouble()).toByte() == this)
    return this.toDouble()
}

public fun Byte.castLossyDouble(): Double = this.toDouble()

public fun Byte.castF128(): F128 {
    check((F128.fromByte(this)).toByte() == this)
    return F128.fromByte(this)
}

public fun Byte.castLossyF128(): F128 = F128.fromByte(this)

public fun Short.castF16(): F16 {
    check((F16.fromShort(this)).toShort() == this)
    return F16.fromShort(this)
}

public fun Short.castLossyF16(): F16 = F16.fromShort(this)

public fun Short.castFloat(): kotlin.Float {
    check((this.toFloat()).toShort() == this)
    return this.toFloat()
}

public fun Short.castLossyFloat(): kotlin.Float = this.toFloat()

public fun Short.castDouble(): Double {
    check((this.toDouble()).toShort() == this)
    return this.toDouble()
}

public fun Short.castLossyDouble(): Double = this.toDouble()

public fun Short.castF128(): F128 {
    check((F128.fromShort(this)).toShort() == this)
    return F128.fromShort(this)
}

public fun Short.castLossyF128(): F128 = F128.fromShort(this)

public fun kotlin.Int.castF16(): F16 {
    check(((F16.fromDouble(this.toDouble()) as Any?) as kotlin.Int) == this)
    return F16.fromDouble(this.toDouble())
}

public fun kotlin.Int.castLossyF16(): F16 = F16.fromDouble(this.toDouble())

public fun kotlin.Int.castFloat(): kotlin.Float {
    check(((this.toFloat() as Any?) as kotlin.Int) == this)
    return this.toFloat()
}

public fun kotlin.Int.castLossyFloat(): kotlin.Float = this.toFloat()

public fun kotlin.Int.castDouble(): Double {
    check(((this.toDouble() as Any?) as kotlin.Int) == this)
    return this.toDouble()
}

public fun kotlin.Int.castLossyDouble(): Double = this.toDouble()

public fun kotlin.Int.castF128(): F128 {
    check(((F128.fromDouble(this.toDouble()) as Any?) as kotlin.Int) == this)
    return F128.fromDouble(this.toDouble())
}

public fun kotlin.Int.castLossyF128(): F128 = F128.fromDouble(this.toDouble())

public fun Long.castF16(): F16 {
    check((F16.fromLong(this)).toLong() == this)
    return F16.fromLong(this)
}

public fun Long.castLossyF16(): F16 = F16.fromLong(this)

public fun Long.castFloat(): kotlin.Float {
    check((this.toFloat()).toLong() == this)
    return this.toFloat()
}

public fun Long.castLossyFloat(): kotlin.Float = this.toFloat()

public fun Long.castDouble(): Double {
    check((this.toDouble()).toLong() == this)
    return this.toDouble()
}

public fun Long.castLossyDouble(): Double = this.toDouble()

public fun Long.castF128(): F128 {
    check((F128.fromLong(this)).toLong() == this)
    return F128.fromLong(this)
}

public fun Long.castLossyF128(): F128 = F128.fromLong(this)

public fun I128.castF16(): F16 {
    check(I128.fromLong((F16.fromDouble(this.toDouble())).toLong()) == this)
    return F16.fromDouble(this.toDouble())
}

public fun I128.castLossyF16(): F16 = F16.fromDouble(this.toDouble())

public fun I128.castFloat(): kotlin.Float {
    check(I128.fromLong((this.toFloat()).toLong()) == this)
    return this.toFloat()
}

public fun I128.castLossyFloat(): kotlin.Float = this.toFloat()

public fun I128.castDouble(): Double {
    check(I128.fromLong((this.toDouble()).toLong()) == this)
    return this.toDouble()
}

public fun I128.castLossyDouble(): Double = this.toDouble()

public fun I128.castF128(): F128 {
    check(I128.fromLong((F128.fromDouble(this.toDouble())).toLong()) == this)
    return F128.fromDouble(this.toDouble())
}

public fun I128.castLossyF128(): F128 = F128.fromDouble(this.toDouble())

public fun ULong.castDouble(): Double {
    check((this.toDouble()).toULong() == this)
    return this.toDouble()
}

public fun ULong.castLossyDouble(): Double = this.toDouble()

public fun Double.cast(): kotlin.Float {
    check((this.toFloat()).toDouble() == this)
    return this.toFloat()
}

public fun Double.castLossy(): kotlin.Float = this.toFloat()

public fun Double.castInt(): kotlin.Int {
    check((((this as Any?) as kotlin.Int)).toDouble() == this)
    return ((this as Any?) as kotlin.Int)
}

public fun Double.castLossyInt(): kotlin.Int = ((this as Any?) as kotlin.Int)

public fun Double.castLong(): Long {
    check((this.toLong()).toDouble() == this)
    return this.toLong()
}

public fun Double.castLossyLong(): Long = this.toLong()
