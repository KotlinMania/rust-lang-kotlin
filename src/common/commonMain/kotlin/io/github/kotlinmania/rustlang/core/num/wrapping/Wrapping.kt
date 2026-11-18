// port-lint: source num/wrapping.rs
package io.github.kotlinmania.rustlang.core.num.wrapping

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.sub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingAdd
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingSub
import io.github.kotlinmania.rustlang.core.result.Result
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

// Definitions of `Wrapping<T>`.

/**
 * Provides intentionally-wrapped arithmetic on `T`.
 *
 * Operations like `+` on `UInt` values are intended to never overflow,
 * and in some debug configurations overflow is detected and results
 * in a panic. While most arithmetic falls into this category, some
 * code explicitly expects and relies upon modular arithmetic (e.g.,
 * hashing).
 *
 * Wrapping arithmetic can be achieved either through methods like
 * `wrappingAdd`, or through the `Wrapping<T>` type, which says that
 * all standard arithmetic operations on the underlying value are
 * intended to have wrapping semantics.
 *
 * The underlying value can be retrieved through the `.0` index of the
 * `Wrapping` tuple.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * val zero = Wrapping(0u)
 * val one = Wrapping(1u)
 * check(UInt.MAX_VALUE == (zero - one).first)
 * ```
 *
 * # Layout
 *
 * `Wrapping<T>` is guaranteed to have the same layout and ABI as `T`.
 */
public open class Wrapping<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Wrapping<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Wrapping<T>(ptr, layout) }, { it.value })

    public open fun clone(): Wrapping<T> = Wrapping<T>(value, fieldLayout)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtBinary(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtOctal(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtLowerHex(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtUpperHex(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public companion object
}

public infix fun Wrapping<UByte>.shl(other: Int): Wrapping<UByte> = Wrapping(this.value.wrappingShl(other.toUInt()), fieldLayout = PrimitiveLayouts.U8)

public fun Wrapping<UByte>.shlAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut shl other
}

public infix fun Wrapping<UByte>.shr(other: Int): Wrapping<UByte> = Wrapping(this.value.wrappingShr(other.toUInt()), fieldLayout = PrimitiveLayouts.U8)

public fun Wrapping<UByte>.shrAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut shr other
}

public fun Wrapping<UShort>.shlInt(other: Int): Wrapping<UShort> = Wrapping(this.value.wrappingShl(other.toUInt()), fieldLayout = PrimitiveLayouts.U16)

public fun Wrapping<UShort>.shlAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut shl other
}

public fun Wrapping<UShort>.shrInt(other: Int): Wrapping<UShort> = Wrapping(this.value.wrappingShr(other.toUInt()), fieldLayout = PrimitiveLayouts.U16)

public fun Wrapping<UShort>.shrAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut shr other
}

public fun Wrapping<Int>.add(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingAdd(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.addAssign(other: Wrapping<Int>) {
    var selfMut = this
    selfMut = selfMut + other
}

public fun Wrapping<Int>.addAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.sub(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingSub(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.subAssign(other: Wrapping<Int>) {
    var selfMut = this
    selfMut = selfMut - other
}

public fun Wrapping<Int>.subAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.mul(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingMul(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.mulAssign(other: Wrapping<Int>) {
    var selfMut = this
    selfMut = selfMut * other
}

public fun Wrapping<Int>.mulAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.div(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingDiv(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.divAssign(other: Wrapping<Int>) {
    var selfMut = this
    selfMut = selfMut / other
}

public fun Wrapping<Int>.divAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.rem(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingRem(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.remAssign(other: Wrapping<Int>) {
    var selfMut = this
    selfMut = selfMut % other
}

public fun Wrapping<Int>.remAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public operator fun Wrapping<Int>.not(): Wrapping<Int> = Wrapping(!this.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.bitxor(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value xor other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.bitxorAssign(other: Wrapping<Int>) {
    var selfMut = this
    selfMut = selfMut xor other
}

public fun Wrapping<Int>.bitxorAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.bitor(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value or other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.bitorAssign(other: Wrapping<Int>) {
    var selfMut = this
    selfMut = selfMut or other
}

public fun Wrapping<Int>.bitorAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.bitand(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value and other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.bitandAssign(other: Wrapping<Int>) {
    var selfMut = this
    selfMut = selfMut and other
}

public fun Wrapping<Int>.bitandAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.neg(): Wrapping<Int> = Wrapping(0, fieldLayout = PrimitiveLayouts.USIZE64) - this

public fun Wrapping<UByte>.addAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.subAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.mulAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.divAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.remAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.bitxorAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.bitorAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.bitandAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UShort>.addAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.subAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.mulAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.divAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.remAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.bitxorAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.bitorAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.bitandAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UInt>.addAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.subAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.mulAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.divAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.remAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.bitxorAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.bitorAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.bitandAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<ULong>.addAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.subAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.mulAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.divAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.remAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.bitxorAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.bitorAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.bitandAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<U128>.addAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.subAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.mulAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.divAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.remAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.bitxorAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.bitorAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.bitandAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<Int>.addAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Wrapping<Int>.subAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Wrapping<Int>.mulAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Wrapping<Int>.divAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Wrapping<Int>.remAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Wrapping<Int>.bitxorAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Wrapping<Int>.bitorAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Wrapping<Int>.bitandAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Wrapping<Byte>.addAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.subAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.mulAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.divAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.remAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.bitxorAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.bitorAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.bitandAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Short>.addAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.subAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.mulAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.divAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.remAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.bitxorAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.bitorAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.bitandAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Long>.addAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.subAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.mulAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.divAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.remAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.bitxorAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.bitorAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.bitandAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<I128>.addAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut + Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.subAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut - Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.mulAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut * Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.divAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut / Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.remAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut % Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.bitxorAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut xor Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.bitorAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut or Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.bitandAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut and Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN: Wrapping<Int> = Wrapping(0, fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX: Wrapping<Int> = Wrapping(Int.MAX_VALUE, fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS: UInt = 32u

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     check(n.countOnes() == 3)
 * }
 * ```
 */
public fun Wrapping<Int>.countOnes(): UInt = this.value.countOnes()

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public fun Wrapping<Int>.countZeros(): UInt = this.value.countZeros()

/**
 * Returns the number of trailing zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     check(n.trailingZeros() == 3)
 * }
 * ```
 */
public fun Wrapping<Int>.trailingZeros(): UInt = this.value.trailingZeros()

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting
 * integer.
 *
 * Please note this isn't the same operation as the `<<` shifting
 * operator!
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     val n: Wrapping<Long> = Wrapping(0x0123456789ABCDEF)
 *     val m: Wrapping<Long> = Wrapping(-0x76543210FEDCBA99)
 *     check(n.rotateLeft(32) == m)
 * }
 * ```
 */
public fun Wrapping<Int>.rotateLeft(n: UInt): Wrapping<Int> = Wrapping(this.value.rotateLeft(n), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * Please note this isn't the same operation as the `>>` shifting
 * operator!
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     val n: Wrapping<Long> = Wrapping(0x0123456789ABCDEF)
 *     val m: Wrapping<Long> = Wrapping(-0xFEDCBA987654322)
 *     check(n.rotateRight(4) == m)
 * }
 * ```
 */
public fun Wrapping<Int>.rotateRight(n: UInt): Wrapping<Int> = Wrapping(this.value.rotateRight(n), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     val n: Wrapping<Short> = Wrapping(0x55)
 *     check(n == Wrapping(85))
 *     val m = n.swapBytes()
 *     check(m == Wrapping(0x5500))
 *     check(m == Wrapping(21760))
 * }
 * ```
 */
public fun Wrapping<Int>.swapBytes(): Wrapping<Int> = Wrapping(this.value.swapBytes(), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Reverses the bit pattern of the integer.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `Short`
 * is used.
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * val n = Wrapping(0x55)
 * check(n == Wrapping(85))
 * val m = n.reverseBits()
 * check(m.first.toUShort() == 0xAA00u.toUShort())
 * check(m == Wrapping(-22016))
 * ```
 */
public fun Wrapping<Int>.reverseBits(): Wrapping<Int> = Wrapping(this.value.reverseBits(), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @file:feature(wrappingIntImpl) import std.num.Wrapping; if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun Wrapping.Companion.fromBe(x: Wrapping<Int>, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): Wrapping<Int> = Wrapping(run { val swapVal0 = (x.value); ((((swapVal0) shl 24)) or (((swapVal0) and 0x0000FF00) shl 8) or (((swapVal0) ushr 8) and 0x0000FF00) or ((swapVal0) ushr 24)) }, fieldLayout = fieldLayout)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @file:feature(wrappingIntImpl) import std.num.Wrapping; if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun Wrapping.Companion.fromLe(x: Wrapping<Int>, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): Wrapping<Int> = Wrapping(x.value, fieldLayout = fieldLayout)

/**
 * Converts `self` to big endian from the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     if (false) {
 *         check(n.toBe() == n)
 *     } else {
 *         check(n.toBe() == n.swapBytes())
 *     }
 * }
 * ```
 */
public fun Wrapping<Int>.toBe(): Wrapping<Int> = Wrapping(this.value.toBe(), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Converts `self` to little endian from the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     if (true) {
 *         check(n.toLe() == n)
 *     } else {
 *         check(n.toLe() == n.swapBytes())
 *     }
 * }
 * ```
 */
public fun Wrapping<Int>.toLe(): Wrapping<Int> = Wrapping(this.value.toLe(), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 *
 * Results that are too large are wrapped:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     check(Wrapping(3).pow(5) == Wrapping(-13))
 *     check(Wrapping(3).pow(6) == Wrapping(-39))
 * }
 * ```
 */
public fun Wrapping<Int>.pow(exp: UInt): Wrapping<Int> = Wrapping(this.value.wrappingPow(exp), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN1: Wrapping<UByte> = Wrapping(UByte.MIN_VALUE, fieldLayout = PrimitiveLayouts.U8)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX1: Wrapping<UByte> = Wrapping(UByte.MAX_VALUE, fieldLayout = PrimitiveLayouts.U8)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS1: UInt = 8u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN2: Wrapping<UShort> = Wrapping(UShort.MIN_VALUE, fieldLayout = PrimitiveLayouts.U16)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX2: Wrapping<UShort> = Wrapping(UShort.MAX_VALUE, fieldLayout = PrimitiveLayouts.U16)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS2: UInt = 16u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN3: Wrapping<UInt> = Wrapping(UInt.MIN_VALUE, fieldLayout = PrimitiveLayouts.U32)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX3: Wrapping<UInt> = Wrapping(UInt.MAX_VALUE, fieldLayout = PrimitiveLayouts.U32)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS3: UInt = 32u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN4: Wrapping<ULong> = Wrapping(ULong.MIN_VALUE, fieldLayout = PrimitiveLayouts.U64)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX4: Wrapping<ULong> = Wrapping(ULong.MAX_VALUE, fieldLayout = PrimitiveLayouts.U64)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS4: UInt = 64u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN5: Wrapping<U128> = Wrapping(U128.MIN_VALUE, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX5: Wrapping<U128> = Wrapping(U128.MAX_VALUE, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS5: UInt = 128u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN6: Wrapping<Int> = Wrapping(Int.MIN_VALUE, fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX6: Wrapping<Int> = Wrapping(Int.MAX_VALUE, fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS6: UInt = 32u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN7: Wrapping<Byte> = Wrapping(Byte.MIN_VALUE, fieldLayout = PrimitiveLayouts.I8)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX7: Wrapping<Byte> = Wrapping(Byte.MAX_VALUE, fieldLayout = PrimitiveLayouts.I8)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS7: UInt = 8u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN8: Wrapping<Short> = Wrapping(Short.MIN_VALUE, fieldLayout = PrimitiveLayouts.I16)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX8: Wrapping<Short> = Wrapping(Short.MAX_VALUE, fieldLayout = PrimitiveLayouts.I16)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS8: UInt = 16u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN9: Wrapping<Int> = Wrapping(Int.MIN_VALUE, fieldLayout = PrimitiveLayouts.I32)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX9: Wrapping<Int> = Wrapping(Int.MAX_VALUE, fieldLayout = PrimitiveLayouts.I32)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS9: UInt = 32u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN10: Wrapping<Long> = Wrapping(Long.MIN_VALUE, fieldLayout = PrimitiveLayouts.I64)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX10: Wrapping<Long> = Wrapping(Long.MAX_VALUE, fieldLayout = PrimitiveLayouts.I64)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS10: UInt = 64u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MIN11: Wrapping<I128> = Wrapping(I128.MIN_VALUE, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val MAX11: Wrapping<I128> = Wrapping(I128.MAX_VALUE, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public val BITS11: UInt = 128u

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     check(n.leadingZeros() == 3)
 * }
 * ```
 */
public fun Wrapping<Int>.leadingZeros(): UInt = this.value.leadingZeros()

/**
 * Computes the absolute value of `self`, wrapping around at
 * the boundary of the type.
 *
 * The only case where such wrapping can occur is when one takes the absolute value of the negative
 * minimal value for the type this is a positive value that is too large to represent in the type. In
 * such a case, this function returns `MIN` itself.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {
 *     check((Wrapping(-128.toByte()).abs().first).toUByte() == 128u.toUByte())
 * }
 * ```
 */
public fun Wrapping<Int>.abs(): Wrapping<Int> = Wrapping(this.value.wrappingAbs(), fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns a number representing sign of `self`.
 *
 *  - `0` if the number is zero
 *  - `1` if the number is positive
 *  - `- 1` if the number is negative
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public fun Wrapping<Int>.signum(): Wrapping<Int> = Wrapping(this.value.signum(), fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns `true` if `self` is positive and `false` if the number is zero or
 * negative.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public fun Wrapping<Int>.isPositive(): Boolean = this.value.isPositive()

/**
 * Returns `true` if `self` is negative and `false` if the number is zero or
 * positive.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public fun Wrapping<Int>.isNegative(): Boolean = this.value.isNegative()

/**
 * Returns `true` if and only if `self == 2 ^ k` for some `k`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingIntImpl)
 * public fun doctestEntry() {}
 * ```
 */
public fun Wrapping<Int>.isPowerOfTwo(): Boolean = this.value.isPowerOfTwo()

/**
 * Returns the smallest power of two greater than or equal to `self`.
 *
 * When return value overflows (i.e., `self>(1<<(N - 1))` for type
 * `uN`), overflows to `2 ^ N = 0`.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.num.Wrapping
 *
 * @feature(wrappingNextPowerOfTwo)
 * public fun doctestEntry() {}
 * ```
 */
public fun Wrapping<Int>.nextPowerOfTwo(): Wrapping<Int> = Wrapping(this.value.wrappingNextPowerOfTwo(), fieldLayout = PrimitiveLayouts.USIZE64)
