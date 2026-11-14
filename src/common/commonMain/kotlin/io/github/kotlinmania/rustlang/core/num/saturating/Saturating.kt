// port-lint: source num/saturating.rs
package io.github.kotlinmania.rustlang.core.num.saturating

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

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
    public val fieldLayout: TypeLayout<T>,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Saturating<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Saturating<T>(ptr, layout) }, { it.value })

    public open fun clone(): Saturating<T> = Saturating<T>(value, fieldLayout)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtBinary(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtOctal(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtLowerHex(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtUpperHex(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)
}

public fun Saturating<Int>.add(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.saturatingAdd(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.addSaturatingInt(other: Saturating<Int>): Output = this.add(other)

public fun Saturating<Int>.addAssign(other: Saturating<Int>) {
    this = this + other
}

public fun Saturating<Int>.addAssignSaturatingInt(other: Saturating<Int>) {
    this.addAssign(other)
}

public fun Saturating<Int>.addAssign(other: Int) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.addAssignInt(other: Int) {
    this.addAssign(other)
}

public fun Saturating<Int>.sub(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.saturatingSub(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.subSaturatingInt(other: Saturating<Int>): Output = this.sub(other)

public fun Saturating<Int>.subAssign(other: Saturating<Int>) {
    this = this - other
}

public fun Saturating<Int>.subAssignSaturatingInt(other: Saturating<Int>) {
    this.subAssign(other)
}

public fun Saturating<Int>.subAssign(other: Int) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.subAssignInt(other: Int) {
    this.subAssign(other)
}

public fun Saturating<Int>.mul(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.saturatingMul(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.mulSaturatingInt(other: Saturating<Int>): Output = this.mul(other)

public fun Saturating<Int>.mulAssign(other: Saturating<Int>) {
    this = this * other
}

public fun Saturating<Int>.mulAssignSaturatingInt(other: Saturating<Int>) {
    this.mulAssign(other)
}

public fun Saturating<Int>.mulAssign(other: Int) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.mulAssignInt(other: Int) {
    this.mulAssign(other)
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

public fun Saturating<Int>.divSaturatingInt(other: Saturating<Int>): Output = this.div(other)

public fun Saturating<Int>.divAssign(other: Saturating<Int>) {
    this = this / other
}

public fun Saturating<Int>.divAssignSaturatingInt(other: Saturating<Int>) {
    this.divAssign(other)
}

public fun Saturating<Int>.divAssign(other: Int) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.divAssignInt(other: Int) {
    this.divAssign(other)
}

public fun Saturating<Int>.rem(other: Saturating<Int>): Saturating<Int> = Saturating(this.value.rem(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.remSaturatingInt(other: Saturating<Int>): Output = this.rem(other)

public fun Saturating<Int>.remAssign(other: Saturating<Int>) {
    this = this % other
}

public fun Saturating<Int>.remAssignSaturatingInt(other: Saturating<Int>) {
    this.remAssign(other)
}

public fun Saturating<Int>.remAssign(other: Int) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.remAssignInt(other: Int) {
    this.remAssign(other)
}

public fun Saturating<Int>.not(): Saturating<Int> = Saturating(!this.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.bitxor(other: Saturating<Int>): Saturating<Int> = Saturating(this.value xor other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.bitxorSaturatingInt(other: Saturating<Int>): Output = this.bitxor(other)

public fun Saturating<Int>.bitxorAssign(other: Saturating<Int>) {
    this = this xor other
}

public fun Saturating<Int>.bitxorAssignSaturatingInt(other: Saturating<Int>) {
    this.bitxorAssign(other)
}

public fun Saturating<Int>.bitxorAssign(other: Int) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.bitxorAssignInt(other: Int) {
    this.bitxorAssign(other)
}

public fun Saturating<Int>.bitor(other: Saturating<Int>): Saturating<Int> = Saturating(this.value or other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.bitorSaturatingInt(other: Saturating<Int>): Output = this.bitor(other)

public fun Saturating<Int>.bitorAssign(other: Saturating<Int>) {
    this = this or other
}

public fun Saturating<Int>.bitorAssignSaturatingInt(other: Saturating<Int>) {
    this.bitorAssign(other)
}

public fun Saturating<Int>.bitorAssign(other: Int) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.bitorAssignInt(other: Int) {
    this.bitorAssign(other)
}

public fun Saturating<Int>.bitand(other: Saturating<Int>): Saturating<Int> = Saturating(this.value and other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Saturating<Int>.bitandSaturatingInt(other: Saturating<Int>): Output = this.bitand(other)

public fun Saturating<Int>.bitandAssign(other: Saturating<Int>) {
    this = this and other
}

public fun Saturating<Int>.bitandAssignSaturatingInt(other: Saturating<Int>) {
    this.bitandAssign(other)
}

public fun Saturating<Int>.bitandAssign(other: Int) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Saturating<Int>.bitandAssignInt(other: Int) {
    this.bitandAssign(other)
}

public fun Saturating<UByte>.addSaturatingUByte(other: Saturating<UByte>): Output = this.add(other)

public fun Saturating<UByte>.addAssignSaturatingUByte(other: Saturating<UByte>) {
    this.addAssign(other)
}

public fun Saturating<UByte>.addAssign(other: UByte) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.addAssignUByte(other: UByte) {
    this.addAssign(other)
}

public fun Saturating<UByte>.subSaturatingUByte(other: Saturating<UByte>): Output = this.sub(other)

public fun Saturating<UByte>.subAssignSaturatingUByte(other: Saturating<UByte>) {
    this.subAssign(other)
}

public fun Saturating<UByte>.subAssign(other: UByte) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.subAssignUByte(other: UByte) {
    this.subAssign(other)
}

public fun Saturating<UByte>.mulSaturatingUByte(other: Saturating<UByte>): Output = this.mul(other)

public fun Saturating<UByte>.mulAssignSaturatingUByte(other: Saturating<UByte>) {
    this.mulAssign(other)
}

public fun Saturating<UByte>.mulAssign(other: UByte) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.mulAssignUByte(other: UByte) {
    this.mulAssign(other)
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

public fun Saturating<UByte>.divSaturatingUByte(other: Saturating<UByte>): Output = this.div(other)

public fun Saturating<UByte>.divAssignSaturatingUByte(other: Saturating<UByte>) {
    this.divAssign(other)
}

public fun Saturating<UByte>.divAssign(other: UByte) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.divAssignUByte(other: UByte) {
    this.divAssign(other)
}

public fun Saturating<UByte>.remSaturatingUByte(other: Saturating<UByte>): Output = this.rem(other)

public fun Saturating<UByte>.remAssignSaturatingUByte(other: Saturating<UByte>) {
    this.remAssign(other)
}

public fun Saturating<UByte>.remAssign(other: UByte) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.remAssignUByte(other: UByte) {
    this.remAssign(other)
}

public fun Saturating<UByte>.bitxorSaturatingUByte(other: Saturating<UByte>): Output = this.bitxor(other)

public fun Saturating<UByte>.bitxorAssignSaturatingUByte(other: Saturating<UByte>) {
    this.bitxorAssign(other)
}

public fun Saturating<UByte>.bitxorAssign(other: UByte) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.bitxorAssignUByte(other: UByte) {
    this.bitxorAssign(other)
}

public fun Saturating<UByte>.bitorSaturatingUByte(other: Saturating<UByte>): Output = this.bitor(other)

public fun Saturating<UByte>.bitorAssignSaturatingUByte(other: Saturating<UByte>) {
    this.bitorAssign(other)
}

public fun Saturating<UByte>.bitorAssign(other: UByte) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.bitorAssignUByte(other: UByte) {
    this.bitorAssign(other)
}

public fun Saturating<UByte>.bitandSaturatingUByte(other: Saturating<UByte>): Output = this.bitand(other)

public fun Saturating<UByte>.bitandAssignSaturatingUByte(other: Saturating<UByte>) {
    this.bitandAssign(other)
}

public fun Saturating<UByte>.bitandAssign(other: UByte) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Saturating<UByte>.bitandAssignUByte(other: UByte) {
    this.bitandAssign(other)
}

public fun Saturating<UShort>.addSaturatingUShort(other: Saturating<UShort>): Output = this.add(other)

public fun Saturating<UShort>.addAssignSaturatingUShort(other: Saturating<UShort>) {
    this.addAssign(other)
}

public fun Saturating<UShort>.addAssign(other: UShort) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.addAssignUShort(other: UShort) {
    this.addAssign(other)
}

public fun Saturating<UShort>.subSaturatingUShort(other: Saturating<UShort>): Output = this.sub(other)

public fun Saturating<UShort>.subAssignSaturatingUShort(other: Saturating<UShort>) {
    this.subAssign(other)
}

public fun Saturating<UShort>.subAssign(other: UShort) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.subAssignUShort(other: UShort) {
    this.subAssign(other)
}

public fun Saturating<UShort>.mulSaturatingUShort(other: Saturating<UShort>): Output = this.mul(other)

public fun Saturating<UShort>.mulAssignSaturatingUShort(other: Saturating<UShort>) {
    this.mulAssign(other)
}

public fun Saturating<UShort>.mulAssign(other: UShort) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.mulAssignUShort(other: UShort) {
    this.mulAssign(other)
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

public fun Saturating<UShort>.divSaturatingUShort(other: Saturating<UShort>): Output = this.div(other)

public fun Saturating<UShort>.divAssignSaturatingUShort(other: Saturating<UShort>) {
    this.divAssign(other)
}

public fun Saturating<UShort>.divAssign(other: UShort) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.divAssignUShort(other: UShort) {
    this.divAssign(other)
}

public fun Saturating<UShort>.remSaturatingUShort(other: Saturating<UShort>): Output = this.rem(other)

public fun Saturating<UShort>.remAssignSaturatingUShort(other: Saturating<UShort>) {
    this.remAssign(other)
}

public fun Saturating<UShort>.remAssign(other: UShort) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.remAssignUShort(other: UShort) {
    this.remAssign(other)
}

public fun Saturating<UShort>.bitxorSaturatingUShort(other: Saturating<UShort>): Output = this.bitxor(other)

public fun Saturating<UShort>.bitxorAssignSaturatingUShort(other: Saturating<UShort>) {
    this.bitxorAssign(other)
}

public fun Saturating<UShort>.bitxorAssign(other: UShort) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.bitxorAssignUShort(other: UShort) {
    this.bitxorAssign(other)
}

public fun Saturating<UShort>.bitorSaturatingUShort(other: Saturating<UShort>): Output = this.bitor(other)

public fun Saturating<UShort>.bitorAssignSaturatingUShort(other: Saturating<UShort>) {
    this.bitorAssign(other)
}

public fun Saturating<UShort>.bitorAssign(other: UShort) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.bitorAssignUShort(other: UShort) {
    this.bitorAssign(other)
}

public fun Saturating<UShort>.bitandSaturatingUShort(other: Saturating<UShort>): Output = this.bitand(other)

public fun Saturating<UShort>.bitandAssignSaturatingUShort(other: Saturating<UShort>) {
    this.bitandAssign(other)
}

public fun Saturating<UShort>.bitandAssign(other: UShort) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Saturating<UShort>.bitandAssignUShort(other: UShort) {
    this.bitandAssign(other)
}

public fun Saturating<UInt>.addSaturatingUInt(other: Saturating<UInt>): Output = this.add(other)

public fun Saturating<UInt>.addAssignSaturatingUInt(other: Saturating<UInt>) {
    this.addAssign(other)
}

public fun Saturating<UInt>.addAssign(other: UInt) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.addAssignUInt(other: UInt) {
    this.addAssign(other)
}

public fun Saturating<UInt>.subSaturatingUInt(other: Saturating<UInt>): Output = this.sub(other)

public fun Saturating<UInt>.subAssignSaturatingUInt(other: Saturating<UInt>) {
    this.subAssign(other)
}

public fun Saturating<UInt>.subAssign(other: UInt) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.subAssignUInt(other: UInt) {
    this.subAssign(other)
}

public fun Saturating<UInt>.mulSaturatingUInt(other: Saturating<UInt>): Output = this.mul(other)

public fun Saturating<UInt>.mulAssignSaturatingUInt(other: Saturating<UInt>) {
    this.mulAssign(other)
}

public fun Saturating<UInt>.mulAssign(other: UInt) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.mulAssignUInt(other: UInt) {
    this.mulAssign(other)
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

public fun Saturating<UInt>.divSaturatingUInt(other: Saturating<UInt>): Output = this.div(other)

public fun Saturating<UInt>.divAssignSaturatingUInt(other: Saturating<UInt>) {
    this.divAssign(other)
}

public fun Saturating<UInt>.divAssign(other: UInt) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.divAssignUInt(other: UInt) {
    this.divAssign(other)
}

public fun Saturating<UInt>.remSaturatingUInt(other: Saturating<UInt>): Output = this.rem(other)

public fun Saturating<UInt>.remAssignSaturatingUInt(other: Saturating<UInt>) {
    this.remAssign(other)
}

public fun Saturating<UInt>.remAssign(other: UInt) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.remAssignUInt(other: UInt) {
    this.remAssign(other)
}

public fun Saturating<UInt>.bitxorSaturatingUInt(other: Saturating<UInt>): Output = this.bitxor(other)

public fun Saturating<UInt>.bitxorAssignSaturatingUInt(other: Saturating<UInt>) {
    this.bitxorAssign(other)
}

public fun Saturating<UInt>.bitxorAssign(other: UInt) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.bitxorAssignUInt(other: UInt) {
    this.bitxorAssign(other)
}

public fun Saturating<UInt>.bitorSaturatingUInt(other: Saturating<UInt>): Output = this.bitor(other)

public fun Saturating<UInt>.bitorAssignSaturatingUInt(other: Saturating<UInt>) {
    this.bitorAssign(other)
}

public fun Saturating<UInt>.bitorAssign(other: UInt) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.bitorAssignUInt(other: UInt) {
    this.bitorAssign(other)
}

public fun Saturating<UInt>.bitandSaturatingUInt(other: Saturating<UInt>): Output = this.bitand(other)

public fun Saturating<UInt>.bitandAssignSaturatingUInt(other: Saturating<UInt>) {
    this.bitandAssign(other)
}

public fun Saturating<UInt>.bitandAssign(other: UInt) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Saturating<UInt>.bitandAssignUInt(other: UInt) {
    this.bitandAssign(other)
}

public fun Saturating<ULong>.addSaturatingULong(other: Saturating<ULong>): Output = this.add(other)

public fun Saturating<ULong>.addAssignSaturatingULong(other: Saturating<ULong>) {
    this.addAssign(other)
}

public fun Saturating<ULong>.addAssign(other: ULong) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.addAssignULong(other: ULong) {
    this.addAssign(other)
}

public fun Saturating<ULong>.subSaturatingULong(other: Saturating<ULong>): Output = this.sub(other)

public fun Saturating<ULong>.subAssignSaturatingULong(other: Saturating<ULong>) {
    this.subAssign(other)
}

public fun Saturating<ULong>.subAssign(other: ULong) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.subAssignULong(other: ULong) {
    this.subAssign(other)
}

public fun Saturating<ULong>.mulSaturatingULong(other: Saturating<ULong>): Output = this.mul(other)

public fun Saturating<ULong>.mulAssignSaturatingULong(other: Saturating<ULong>) {
    this.mulAssign(other)
}

public fun Saturating<ULong>.mulAssign(other: ULong) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.mulAssignULong(other: ULong) {
    this.mulAssign(other)
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

public fun Saturating<ULong>.divSaturatingULong(other: Saturating<ULong>): Output = this.div(other)

public fun Saturating<ULong>.divAssignSaturatingULong(other: Saturating<ULong>) {
    this.divAssign(other)
}

public fun Saturating<ULong>.divAssign(other: ULong) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.divAssignULong(other: ULong) {
    this.divAssign(other)
}

public fun Saturating<ULong>.remSaturatingULong(other: Saturating<ULong>): Output = this.rem(other)

public fun Saturating<ULong>.remAssignSaturatingULong(other: Saturating<ULong>) {
    this.remAssign(other)
}

public fun Saturating<ULong>.remAssign(other: ULong) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.remAssignULong(other: ULong) {
    this.remAssign(other)
}

public fun Saturating<ULong>.bitxorSaturatingULong(other: Saturating<ULong>): Output = this.bitxor(other)

public fun Saturating<ULong>.bitxorAssignSaturatingULong(other: Saturating<ULong>) {
    this.bitxorAssign(other)
}

public fun Saturating<ULong>.bitxorAssign(other: ULong) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.bitxorAssignULong(other: ULong) {
    this.bitxorAssign(other)
}

public fun Saturating<ULong>.bitorSaturatingULong(other: Saturating<ULong>): Output = this.bitor(other)

public fun Saturating<ULong>.bitorAssignSaturatingULong(other: Saturating<ULong>) {
    this.bitorAssign(other)
}

public fun Saturating<ULong>.bitorAssign(other: ULong) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.bitorAssignULong(other: ULong) {
    this.bitorAssign(other)
}

public fun Saturating<ULong>.bitandSaturatingULong(other: Saturating<ULong>): Output = this.bitand(other)

public fun Saturating<ULong>.bitandAssignSaturatingULong(other: Saturating<ULong>) {
    this.bitandAssign(other)
}

public fun Saturating<ULong>.bitandAssign(other: ULong) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Saturating<ULong>.bitandAssignULong(other: ULong) {
    this.bitandAssign(other)
}

public fun Saturating<U128>.addSaturatingU128(other: Saturating<U128>): Output = this.add(other)

public fun Saturating<U128>.addAssignSaturatingU128(other: Saturating<U128>) {
    this.addAssign(other)
}

public fun Saturating<U128>.addAssign(other: U128) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.addAssignU128(other: U128) {
    this.addAssign(other)
}

public fun Saturating<U128>.subSaturatingU128(other: Saturating<U128>): Output = this.sub(other)

public fun Saturating<U128>.subAssignSaturatingU128(other: Saturating<U128>) {
    this.subAssign(other)
}

public fun Saturating<U128>.subAssign(other: U128) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.subAssignU128(other: U128) {
    this.subAssign(other)
}

public fun Saturating<U128>.mulSaturatingU128(other: Saturating<U128>): Output = this.mul(other)

public fun Saturating<U128>.mulAssignSaturatingU128(other: Saturating<U128>) {
    this.mulAssign(other)
}

public fun Saturating<U128>.mulAssign(other: U128) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.mulAssignU128(other: U128) {
    this.mulAssign(other)
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

public fun Saturating<U128>.divSaturatingU128(other: Saturating<U128>): Output = this.div(other)

public fun Saturating<U128>.divAssignSaturatingU128(other: Saturating<U128>) {
    this.divAssign(other)
}

public fun Saturating<U128>.divAssign(other: U128) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.divAssignU128(other: U128) {
    this.divAssign(other)
}

public fun Saturating<U128>.remSaturatingU128(other: Saturating<U128>): Output = this.rem(other)

public fun Saturating<U128>.remAssignSaturatingU128(other: Saturating<U128>) {
    this.remAssign(other)
}

public fun Saturating<U128>.remAssign(other: U128) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.remAssignU128(other: U128) {
    this.remAssign(other)
}

public fun Saturating<U128>.bitxorSaturatingU128(other: Saturating<U128>): Output = this.bitxor(other)

public fun Saturating<U128>.bitxorAssignSaturatingU128(other: Saturating<U128>) {
    this.bitxorAssign(other)
}

public fun Saturating<U128>.bitxorAssign(other: U128) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.bitxorAssignU128(other: U128) {
    this.bitxorAssign(other)
}

public fun Saturating<U128>.bitorSaturatingU128(other: Saturating<U128>): Output = this.bitor(other)

public fun Saturating<U128>.bitorAssignSaturatingU128(other: Saturating<U128>) {
    this.bitorAssign(other)
}

public fun Saturating<U128>.bitorAssign(other: U128) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.bitorAssignU128(other: U128) {
    this.bitorAssign(other)
}

public fun Saturating<U128>.bitandSaturatingU128(other: Saturating<U128>): Output = this.bitand(other)

public fun Saturating<U128>.bitandAssignSaturatingU128(other: Saturating<U128>) {
    this.bitandAssign(other)
}

public fun Saturating<U128>.bitandAssign(other: U128) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Saturating<U128>.bitandAssignU128(other: U128) {
    this.bitandAssign(other)
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

public fun Saturating<Byte>.addSaturatingByte(other: Saturating<Byte>): Output = this.add(other)

public fun Saturating<Byte>.addAssignSaturatingByte(other: Saturating<Byte>) {
    this.addAssign(other)
}

public fun Saturating<Byte>.addAssign(other: Byte) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.addAssignByte(other: Byte) {
    this.addAssign(other)
}

public fun Saturating<Byte>.subSaturatingByte(other: Saturating<Byte>): Output = this.sub(other)

public fun Saturating<Byte>.subAssignSaturatingByte(other: Saturating<Byte>) {
    this.subAssign(other)
}

public fun Saturating<Byte>.subAssign(other: Byte) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.subAssignByte(other: Byte) {
    this.subAssign(other)
}

public fun Saturating<Byte>.mulSaturatingByte(other: Saturating<Byte>): Output = this.mul(other)

public fun Saturating<Byte>.mulAssignSaturatingByte(other: Saturating<Byte>) {
    this.mulAssign(other)
}

public fun Saturating<Byte>.mulAssign(other: Byte) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.mulAssignByte(other: Byte) {
    this.mulAssign(other)
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

public fun Saturating<Byte>.divSaturatingByte(other: Saturating<Byte>): Output = this.div(other)

public fun Saturating<Byte>.divAssignSaturatingByte(other: Saturating<Byte>) {
    this.divAssign(other)
}

public fun Saturating<Byte>.divAssign(other: Byte) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.divAssignByte(other: Byte) {
    this.divAssign(other)
}

public fun Saturating<Byte>.remSaturatingByte(other: Saturating<Byte>): Output = this.rem(other)

public fun Saturating<Byte>.remAssignSaturatingByte(other: Saturating<Byte>) {
    this.remAssign(other)
}

public fun Saturating<Byte>.remAssign(other: Byte) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.remAssignByte(other: Byte) {
    this.remAssign(other)
}

public fun Saturating<Byte>.bitxorSaturatingByte(other: Saturating<Byte>): Output = this.bitxor(other)

public fun Saturating<Byte>.bitxorAssignSaturatingByte(other: Saturating<Byte>) {
    this.bitxorAssign(other)
}

public fun Saturating<Byte>.bitxorAssign(other: Byte) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.bitxorAssignByte(other: Byte) {
    this.bitxorAssign(other)
}

public fun Saturating<Byte>.bitorSaturatingByte(other: Saturating<Byte>): Output = this.bitor(other)

public fun Saturating<Byte>.bitorAssignSaturatingByte(other: Saturating<Byte>) {
    this.bitorAssign(other)
}

public fun Saturating<Byte>.bitorAssign(other: Byte) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.bitorAssignByte(other: Byte) {
    this.bitorAssign(other)
}

public fun Saturating<Byte>.bitandSaturatingByte(other: Saturating<Byte>): Output = this.bitand(other)

public fun Saturating<Byte>.bitandAssignSaturatingByte(other: Saturating<Byte>) {
    this.bitandAssign(other)
}

public fun Saturating<Byte>.bitandAssign(other: Byte) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Saturating<Byte>.bitandAssignByte(other: Byte) {
    this.bitandAssign(other)
}

public fun Saturating<Short>.addSaturatingShort(other: Saturating<Short>): Output = this.add(other)

public fun Saturating<Short>.addAssignSaturatingShort(other: Saturating<Short>) {
    this.addAssign(other)
}

public fun Saturating<Short>.addAssign(other: Short) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.addAssignShort(other: Short) {
    this.addAssign(other)
}

public fun Saturating<Short>.subSaturatingShort(other: Saturating<Short>): Output = this.sub(other)

public fun Saturating<Short>.subAssignSaturatingShort(other: Saturating<Short>) {
    this.subAssign(other)
}

public fun Saturating<Short>.subAssign(other: Short) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.subAssignShort(other: Short) {
    this.subAssign(other)
}

public fun Saturating<Short>.mulSaturatingShort(other: Saturating<Short>): Output = this.mul(other)

public fun Saturating<Short>.mulAssignSaturatingShort(other: Saturating<Short>) {
    this.mulAssign(other)
}

public fun Saturating<Short>.mulAssign(other: Short) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.mulAssignShort(other: Short) {
    this.mulAssign(other)
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

public fun Saturating<Short>.divSaturatingShort(other: Saturating<Short>): Output = this.div(other)

public fun Saturating<Short>.divAssignSaturatingShort(other: Saturating<Short>) {
    this.divAssign(other)
}

public fun Saturating<Short>.divAssign(other: Short) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.divAssignShort(other: Short) {
    this.divAssign(other)
}

public fun Saturating<Short>.remSaturatingShort(other: Saturating<Short>): Output = this.rem(other)

public fun Saturating<Short>.remAssignSaturatingShort(other: Saturating<Short>) {
    this.remAssign(other)
}

public fun Saturating<Short>.remAssign(other: Short) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.remAssignShort(other: Short) {
    this.remAssign(other)
}

public fun Saturating<Short>.bitxorSaturatingShort(other: Saturating<Short>): Output = this.bitxor(other)

public fun Saturating<Short>.bitxorAssignSaturatingShort(other: Saturating<Short>) {
    this.bitxorAssign(other)
}

public fun Saturating<Short>.bitxorAssign(other: Short) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.bitxorAssignShort(other: Short) {
    this.bitxorAssign(other)
}

public fun Saturating<Short>.bitorSaturatingShort(other: Saturating<Short>): Output = this.bitor(other)

public fun Saturating<Short>.bitorAssignSaturatingShort(other: Saturating<Short>) {
    this.bitorAssign(other)
}

public fun Saturating<Short>.bitorAssign(other: Short) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.bitorAssignShort(other: Short) {
    this.bitorAssign(other)
}

public fun Saturating<Short>.bitandSaturatingShort(other: Saturating<Short>): Output = this.bitand(other)

public fun Saturating<Short>.bitandAssignSaturatingShort(other: Saturating<Short>) {
    this.bitandAssign(other)
}

public fun Saturating<Short>.bitandAssign(other: Short) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Saturating<Short>.bitandAssignShort(other: Short) {
    this.bitandAssign(other)
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

public fun Saturating<Long>.addSaturatingLong(other: Saturating<Long>): Output = this.add(other)

public fun Saturating<Long>.addAssignSaturatingLong(other: Saturating<Long>) {
    this.addAssign(other)
}

public fun Saturating<Long>.addAssign(other: Long) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.addAssignLong(other: Long) {
    this.addAssign(other)
}

public fun Saturating<Long>.subSaturatingLong(other: Saturating<Long>): Output = this.sub(other)

public fun Saturating<Long>.subAssignSaturatingLong(other: Saturating<Long>) {
    this.subAssign(other)
}

public fun Saturating<Long>.subAssign(other: Long) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.subAssignLong(other: Long) {
    this.subAssign(other)
}

public fun Saturating<Long>.mulSaturatingLong(other: Saturating<Long>): Output = this.mul(other)

public fun Saturating<Long>.mulAssignSaturatingLong(other: Saturating<Long>) {
    this.mulAssign(other)
}

public fun Saturating<Long>.mulAssign(other: Long) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.mulAssignLong(other: Long) {
    this.mulAssign(other)
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

public fun Saturating<Long>.divSaturatingLong(other: Saturating<Long>): Output = this.div(other)

public fun Saturating<Long>.divAssignSaturatingLong(other: Saturating<Long>) {
    this.divAssign(other)
}

public fun Saturating<Long>.divAssign(other: Long) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.divAssignLong(other: Long) {
    this.divAssign(other)
}

public fun Saturating<Long>.remSaturatingLong(other: Saturating<Long>): Output = this.rem(other)

public fun Saturating<Long>.remAssignSaturatingLong(other: Saturating<Long>) {
    this.remAssign(other)
}

public fun Saturating<Long>.remAssign(other: Long) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.remAssignLong(other: Long) {
    this.remAssign(other)
}

public fun Saturating<Long>.bitxorSaturatingLong(other: Saturating<Long>): Output = this.bitxor(other)

public fun Saturating<Long>.bitxorAssignSaturatingLong(other: Saturating<Long>) {
    this.bitxorAssign(other)
}

public fun Saturating<Long>.bitxorAssign(other: Long) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.bitxorAssignLong(other: Long) {
    this.bitxorAssign(other)
}

public fun Saturating<Long>.bitorSaturatingLong(other: Saturating<Long>): Output = this.bitor(other)

public fun Saturating<Long>.bitorAssignSaturatingLong(other: Saturating<Long>) {
    this.bitorAssign(other)
}

public fun Saturating<Long>.bitorAssign(other: Long) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.bitorAssignLong(other: Long) {
    this.bitorAssign(other)
}

public fun Saturating<Long>.bitandSaturatingLong(other: Saturating<Long>): Output = this.bitand(other)

public fun Saturating<Long>.bitandAssignSaturatingLong(other: Saturating<Long>) {
    this.bitandAssign(other)
}

public fun Saturating<Long>.bitandAssign(other: Long) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Saturating<Long>.bitandAssignLong(other: Long) {
    this.bitandAssign(other)
}

public fun Saturating<I128>.addSaturatingI128(other: Saturating<I128>): Output = this.add(other)

public fun Saturating<I128>.addAssignSaturatingI128(other: Saturating<I128>) {
    this.addAssign(other)
}

public fun Saturating<I128>.addAssign(other: I128) {
    this = this + Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.addAssignI128(other: I128) {
    this.addAssign(other)
}

public fun Saturating<I128>.subSaturatingI128(other: Saturating<I128>): Output = this.sub(other)

public fun Saturating<I128>.subAssignSaturatingI128(other: Saturating<I128>) {
    this.subAssign(other)
}

public fun Saturating<I128>.subAssign(other: I128) {
    this = this - Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.subAssignI128(other: I128) {
    this.subAssign(other)
}

public fun Saturating<I128>.mulSaturatingI128(other: Saturating<I128>): Output = this.mul(other)

public fun Saturating<I128>.mulAssignSaturatingI128(other: Saturating<I128>) {
    this.mulAssign(other)
}

public fun Saturating<I128>.mulAssign(other: I128) {
    this = this * Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.mulAssignI128(other: I128) {
    this.mulAssign(other)
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

public fun Saturating<I128>.divSaturatingI128(other: Saturating<I128>): Output = this.div(other)

public fun Saturating<I128>.divAssignSaturatingI128(other: Saturating<I128>) {
    this.divAssign(other)
}

public fun Saturating<I128>.divAssign(other: I128) {
    this = this / Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.divAssignI128(other: I128) {
    this.divAssign(other)
}

public fun Saturating<I128>.remSaturatingI128(other: Saturating<I128>): Output = this.rem(other)

public fun Saturating<I128>.remAssignSaturatingI128(other: Saturating<I128>) {
    this.remAssign(other)
}

public fun Saturating<I128>.remAssign(other: I128) {
    this = this % Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.remAssignI128(other: I128) {
    this.remAssign(other)
}

public fun Saturating<I128>.bitxorSaturatingI128(other: Saturating<I128>): Output = this.bitxor(other)

public fun Saturating<I128>.bitxorAssignSaturatingI128(other: Saturating<I128>) {
    this.bitxorAssign(other)
}

public fun Saturating<I128>.bitxorAssign(other: I128) {
    this = this xor Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.bitxorAssignI128(other: I128) {
    this.bitxorAssign(other)
}

public fun Saturating<I128>.bitorSaturatingI128(other: Saturating<I128>): Output = this.bitor(other)

public fun Saturating<I128>.bitorAssignSaturatingI128(other: Saturating<I128>) {
    this.bitorAssign(other)
}

public fun Saturating<I128>.bitorAssign(other: I128) {
    this = this or Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.bitorAssignI128(other: I128) {
    this.bitorAssign(other)
}

public fun Saturating<I128>.bitandSaturatingI128(other: Saturating<I128>): Output = this.bitand(other)

public fun Saturating<I128>.bitandAssignSaturatingI128(other: Saturating<I128>) {
    this.bitandAssign(other)
}

public fun Saturating<I128>.bitandAssign(other: I128) {
    this = this and Saturating(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Saturating<I128>.bitandAssignI128(other: I128) {
    this.bitandAssign(other)
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
public fun Saturating.Companion.fromBe(x: Saturating<Int>, fieldLayout: TypeLayout<Int>): Saturating<Int> = Saturating(run { val swapVal0 = (x.value); ((((swapVal0) shl 24)) or (((swapVal0) and 0x0000FF00) shl 8) or (((swapVal0) ushr 8) and 0x0000FF00) or ((swapVal0) ushr 24)) }, fieldLayout = fieldLayout)

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
public fun Saturating.Companion.fromLe(x: Saturating<Int>, fieldLayout: TypeLayout<Int>): Saturating<Int> = Saturating(x.value, fieldLayout = fieldLayout)

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
