// port-lint: source num/saturating.rs
package io.github.kotlinmania.rustlang.core.num.saturating

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.num.saturatingMul
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.sub
import io.github.kotlinmania.rustlang.core.result.Result
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

// Definitions of `Saturating<T>`.

/**
 * Provides intentionally-saturating arithmetic on `T`.
 *
 * Operations like `+` on `UInt` values are intended to never overflow,
 * and in some debug configurations overflow is detected and results
 * in a panic. While most arithmetic falls into this category, some
 * code explicitly expects and relies upon saturating arithmetic.
 *
 * Saturating arithmetic can be achieved either through methods like
 * `saturatingAdd`, or through the `Saturating<T>` type, which says that
 * all standard arithmetic operations on the underlying value are
 * intended to have saturating semantics.
 *
 * The underlying value can be retrieved through the `.0` index of the
 * `Saturating` tuple.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * val max = Saturating(UInt.MAX_VALUE)
 * val one = Saturating(1u)
 * check(UInt.MAX_VALUE == (max + one).first)
 * ```
 *
 * # Layout
 *
 * `Saturating<T>` is guaranteed to have the same layout and ABI as `T`.
 */
public open class Saturating<T>(
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

    public fun pointerLayout(): TypeLayout<Saturating<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Saturating<T>(ptr, layout) }, { it.value })

    public open fun clone(): Saturating<T> = Saturating<T>(value, fieldLayout)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtBinary(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtOctal(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtLowerHex(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtUpperHex(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public companion object
}

public fun Saturating<Int>.add(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.saturatingAdd(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.addAssign(other: Saturating<Int>) {
    var selfMut = this
    selfMut = selfMut + other
}

public fun Saturating<Int>.addAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.sub(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.saturatingSub(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.subAssign(other: Saturating<Int>) {
    var selfMut = this
    selfMut = selfMut - other
}

public fun Saturating<Int>.subAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.mul(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.saturatingMul(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.mulAssign(other: Saturating<Int>) {
    var selfMut = this
    selfMut = selfMut * other
}

public fun Saturating<Int>.mulAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public fun Saturating<Int>.div(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.saturatingDiv(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.divAssign(other: Saturating<Int>) {
    var selfMut = this
    selfMut = selfMut / other
}

public fun Saturating<Int>.divAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.rem(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.rem(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.remAssign(other: Saturating<Int>) {
    var selfMut = this
    selfMut = selfMut % other
}

public fun Saturating<Int>.remAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public operator fun Saturating<Int>.not(): Saturating<Int> = Saturating(!this.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.bitxor(other: Saturating<Int>): Saturating<Int> = Saturating(this.value xor other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.bitxorAssign(other: Saturating<Int>) {
    var selfMut = this
    selfMut = selfMut xor other
}

public fun Saturating<Int>.bitxorAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.bitor(other: Saturating<Int>): Saturating<Int> = Saturating(this.value or other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.bitorAssign(other: Saturating<Int>) {
    var selfMut = this
    selfMut = selfMut or other
}

public fun Saturating<Int>.bitorAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.bitand(other: Saturating<Int>): Saturating<Int> = Saturating(this.value and other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.bitandAssign(other: Saturating<Int>) {
    var selfMut = this
    selfMut = selfMut and other
}

public fun Saturating<Int>.bitandAssign(other: Int) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<UByte>.addAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.subAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.mulAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<UByte>.divAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.remAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.bitxorAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.bitorAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.bitandAssign(other: UByte) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UShort>.addAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.subAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.mulAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<UShort>.divAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.remAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.bitxorAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.bitorAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.bitandAssign(other: UShort) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UInt>.addAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.subAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.mulAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<UInt>.divAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.remAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.bitxorAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.bitorAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.bitandAssign(other: UInt) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<ULong>.addAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.subAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.mulAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<ULong>.divAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.remAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.bitxorAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.bitorAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.bitandAssign(other: ULong) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<U128>.addAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.subAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.mulAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<U128>.divAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.remAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.bitxorAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.bitorAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.bitandAssign(other: U128) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<Int>.addAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Saturating<Int>.subAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Saturating<Int>.mulAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<Int>.divAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Saturating<Int>.remAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Saturating<Int>.bitxorAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Saturating<Int>.bitorAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Saturating<Int>.bitandAssignInt(other: Int) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.ISIZE64)
}

public fun Saturating<Byte>.addAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.subAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.mulAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<Byte>.divAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.remAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.bitxorAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.bitorAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.bitandAssign(other: Byte) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Short>.addAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.subAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.mulAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<Short>.divAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.remAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.bitxorAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.bitorAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.bitandAssign(other: Short) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<Long>.addAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.subAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.mulAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<Long>.divAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.remAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.bitxorAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.bitorAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.bitandAssign(other: Long) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<I128>.addAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut + Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.subAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut - Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.mulAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut * Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

/**
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */

public fun Saturating<I128>.divAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut / Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.remAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut % Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.bitxorAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut xor Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.bitorAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut or Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.bitandAssign(other: I128) {
    var selfMut = this
    selfMut = selfMut and Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN: Saturating<Int> = Saturating(0, fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX: Saturating<Int> = Saturating(Int.MAX_VALUE, fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS: UInt = 32u

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * check(n.countOnes() == 3)
 * ```
 */
public fun Saturating<Int>.countOnes(): UInt = this.value.countOnes()

/**
 * Returns the number of zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public fun Saturating<Int>.countZeros(): UInt = this.value.countZeros()

/**
 * Returns the number of trailing zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * check(n.trailingZeros() == 3)
 * ```
 */
public fun Saturating<Int>.trailingZeros(): UInt = this.value.trailingZeros()

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * saturating the truncated bits to the end of the resulting
 * integer.
 *
 * Please note this isn't the same operation as the `<<` shifting
 * operator!
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * val n: Saturating<Long> = Saturating(0x0123456789ABCDEF)
 * val m: Saturating<Long> = Saturating(-0x76543210FEDCBA99)
 * check(n.rotateLeft(32) == m)
 * ```
 */
public fun Saturating<Int>.rotateLeft(n: UInt): Saturating<Int> = Saturating(this.value.rotateLeft(n), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * saturating the truncated bits to the beginning of the resulting
 * integer.
 *
 * Please note this isn't the same operation as the `>>` shifting
 * operator!
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * val n: Saturating<Long> = Saturating(0x0123456789ABCDEF)
 * val m: Saturating<Long> = Saturating(-0xFEDCBA987654322)
 * check(n.rotateRight(4) == m)
 * ```
 */
public fun Saturating<Int>.rotateRight(n: UInt): Saturating<Int> = Saturating(this.value.rotateRight(n), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * val n: Saturating<Short> = Saturating(0x55)
 * check(n == Saturating(85))
 * val m = n.swapBytes()
 * check(m == Saturating(0x5500))
 * check(m == Saturating(21760))
 * ```
 */
public fun Saturating<Int>.swapBytes(): Saturating<Int> = Saturating(this.value.swapBytes(), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Reverses the bit pattern of the integer.
 *
 * # Examples
 *
 * Please note that this example is shared among integer types, which is why `Short`
 * is used.
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * val n = Saturating(0x55)
 * check(n == Saturating(85))
 * val m = n.reverseBits()
 * check(m.first.toUShort() == 0xAA00u.toUShort())
 * check(m == Saturating(-22016))
 * ```
 */
public fun Saturating<Int>.reverseBits(): Saturating<Int> = Saturating(this.value.reverseBits(), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating; if cfg(targetEndian = "big") {} else {}
 * ```
 */
public fun Saturating.Companion.fromBe(x: Saturating<Int>, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): Saturating<Int> = Saturating(run { val swapVal0 = (x.value); ((((swapVal0) shl 24)) or (((swapVal0) and 0x0000FF00) shl 8) or (((swapVal0) ushr 8) and 0x0000FF00) or ((swapVal0) ushr 24)) }, fieldLayout = fieldLayout)

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating; if cfg(targetEndian = "little") {} else {}
 * ```
 */
public fun Saturating.Companion.fromLe(x: Saturating<Int>, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): Saturating<Int> = Saturating(x.value, fieldLayout = fieldLayout)

/**
 * Converts `self` to big endian from the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * if (false) {
 *     check(n.toBe() == n)
 * } else {
 *     check(n.toBe() == n.swapBytes())
 * }
 * ```
 */
public fun Saturating<Int>.toBe(): Saturating<Int> = Saturating(this.value.toBe(), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Converts `self` to little endian from the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * if (true) {
 *     check(n.toLe() == n)
 * } else {
 *     check(n.toLe() == n.swapBytes())
 * }
 * ```
 */
public fun Saturating<Int>.toLe(): Saturating<Int> = Saturating(this.value.toLe(), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Raises self to the power of `exp`, using exponentiation by squaring.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 *
 * Results that are too large are saturated:
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * check(Saturating(3).pow(5) == Saturating(127))
 * check(Saturating(3).pow(6) == Saturating(127))
 * ```
 */
public fun Saturating<Int>.pow(exp: UInt): Saturating<Int> = Saturating(this.value.saturatingPow(exp), fieldLayout = PrimitiveLayouts.USIZE64)

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN1: Saturating<UByte> = Saturating(UByte.MIN_VALUE, fieldLayout = PrimitiveLayouts.U8)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX1: Saturating<UByte> = Saturating(UByte.MAX_VALUE, fieldLayout = PrimitiveLayouts.U8)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS1: UInt = 8u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN2: Saturating<UShort> = Saturating(UShort.MIN_VALUE, fieldLayout = PrimitiveLayouts.U16)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX2: Saturating<UShort> = Saturating(UShort.MAX_VALUE, fieldLayout = PrimitiveLayouts.U16)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS2: UInt = 16u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN3: Saturating<UInt> = Saturating(UInt.MIN_VALUE, fieldLayout = PrimitiveLayouts.U32)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX3: Saturating<UInt> = Saturating(UInt.MAX_VALUE, fieldLayout = PrimitiveLayouts.U32)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS3: UInt = 32u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN4: Saturating<ULong> = Saturating(ULong.MIN_VALUE, fieldLayout = PrimitiveLayouts.U64)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX4: Saturating<ULong> = Saturating(ULong.MAX_VALUE, fieldLayout = PrimitiveLayouts.U64)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS4: UInt = 64u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN5: Saturating<U128> = Saturating(U128.MIN_VALUE, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX5: Saturating<U128> = Saturating(U128.MAX_VALUE, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS5: UInt = 128u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN6: Saturating<Int> = Saturating(Int.MIN_VALUE, fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX6: Saturating<Int> = Saturating(Int.MAX_VALUE, fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS6: UInt = 32u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN7: Saturating<Byte> = Saturating(Byte.MIN_VALUE, fieldLayout = PrimitiveLayouts.I8)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX7: Saturating<Byte> = Saturating(Byte.MAX_VALUE, fieldLayout = PrimitiveLayouts.I8)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS7: UInt = 8u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN8: Saturating<Short> = Saturating(Short.MIN_VALUE, fieldLayout = PrimitiveLayouts.I16)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX8: Saturating<Short> = Saturating(Short.MAX_VALUE, fieldLayout = PrimitiveLayouts.I16)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS8: UInt = 16u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN9: Saturating<Int> = Saturating(Int.MIN_VALUE, fieldLayout = PrimitiveLayouts.I32)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX9: Saturating<Int> = Saturating(Int.MAX_VALUE, fieldLayout = PrimitiveLayouts.I32)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS9: UInt = 32u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN10: Saturating<Long> = Saturating(Long.MIN_VALUE, fieldLayout = PrimitiveLayouts.I64)

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX10: Saturating<Long> = Saturating(Long.MAX_VALUE, fieldLayout = PrimitiveLayouts.I64)

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS10: UInt = 64u

/**
 * Returns the smallest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MIN11: Saturating<I128> = Saturating(I128.MIN_VALUE, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Returns the largest value that can be represented by this integer type.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val MAX11: Saturating<I128> = Saturating(I128.MAX_VALUE, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))

/**
 * Returns the size of this integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public val BITS11: UInt = 128u

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating
 *
 * check(n.leadingZeros() == 3)
 * ```
 */
public fun Saturating<Int>.leadingZeros(): UInt = this.value.leadingZeros()

/**
 * Saturating absolute value. Computes `self.abs()`, returning `MAX` if `self == MIN`
 * instead of overflowing.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public fun Saturating<Int>.abs(): Saturating<Int> = Saturating(this.value.saturatingAbs(), fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns a number representing sign of `self`.
 *
 *  - `0` if the number is zero
 *  - `1` if the number is positive
 *  - `- 1` if the number is negative
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public fun Saturating<Int>.signum(): Saturating<Int> = Saturating(this.value.signum(), fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns `true` if `self` is positive and `false` if the number is zero or
 * negative.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public fun Saturating<Int>.isPositive(): Boolean = this.value.isPositive()

/**
 * Returns `true` if `self` is negative and `false` if the number is zero or
 * positive.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public fun Saturating<Int>.isNegative(): Boolean = this.value.isNegative()

public fun Saturating<Int>.neg(): Saturating<Int> = Saturating(this.value.saturatingNeg(), fieldLayout = PrimitiveLayouts.ISIZE64)

/**
 * Returns `true` if and only if `self == 2 ^ k` for some `k`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.Saturating;
 * ```
 */
public fun Saturating<Int>.isPowerOfTwo(): Boolean = this.value.isPowerOfTwo()
