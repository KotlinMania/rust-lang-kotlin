// port-lint: source num/wrapping.rs
package io.github.kotlinmania.rustlang.core.num.wrapping

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

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
    public val fieldLayout: TypeLayout<T>,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) {
            fieldLayout.writeNative(fieldStorage.rawAddress, value)
        }

    public constructor(value: T, fieldLayout: TypeLayout<T>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Wrapping<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Wrapping<T>(ptr, layout) }, { it.value })

    public open fun clone(): Wrapping<T> = Wrapping<T>(value, fieldLayout)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtBinary(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtOctal(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtLowerHex(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)

    public open fun fmtUpperHex(f: StringBuilder): Result<Unit, Unit> = this.value.fmt(f)
}

public fun Wrapping<UByte>.shl(other: Int): Wrapping<UByte> = Wrapping(this.value.wrappingShl(other.toUInt()), fieldLayout = PrimitiveLayouts.U8)

public fun Wrapping<UByte>.shlInt(other: Int): Output = this.shl(other)

public fun Wrapping<UByte>.shlAssign(other: Int) {
    this = this shl other
}

public fun Wrapping<UByte>.shlAssignInt(other: Int) {
    this.shlAssign(other)
}

public fun Wrapping<UByte>.shr(other: Int): Wrapping<UByte> = Wrapping(this.value.wrappingShr(other.toUInt()), fieldLayout = PrimitiveLayouts.U8)

public fun Wrapping<UByte>.shrInt(other: Int): Output = this.shr(other)

public fun Wrapping<UByte>.shrAssign(other: Int) {
    this = this shr other
}

public fun Wrapping<UByte>.shrAssignInt(other: Int) {
    this.shrAssign(other)
}

public fun Wrapping<Int>.add(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingAdd(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.addWrappingInt(other: Wrapping<Int>): Output = this.add(other)

public fun Wrapping<Int>.addAssign(other: Wrapping<Int>) {
    this = this + other
}

public fun Wrapping<Int>.addAssignWrappingInt(other: Wrapping<Int>) {
    this.addAssign(other)
}

public fun Wrapping<Int>.addAssign(other: Int) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.addAssignInt(other: Int) {
    this.addAssign(other)
}

public fun Wrapping<Int>.sub(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingSub(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.subWrappingInt(other: Wrapping<Int>): Output = this.sub(other)

public fun Wrapping<Int>.subAssign(other: Wrapping<Int>) {
    this = this - other
}

public fun Wrapping<Int>.subAssignWrappingInt(other: Wrapping<Int>) {
    this.subAssign(other)
}

public fun Wrapping<Int>.subAssign(other: Int) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.subAssignInt(other: Int) {
    this.subAssign(other)
}

public fun Wrapping<Int>.mul(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingMul(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.mulWrappingInt(other: Wrapping<Int>): Output = this.mul(other)

public fun Wrapping<Int>.mulAssign(other: Wrapping<Int>) {
    this = this * other
}

public fun Wrapping<Int>.mulAssignWrappingInt(other: Wrapping<Int>) {
    this.mulAssign(other)
}

public fun Wrapping<Int>.mulAssign(other: Int) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.mulAssignInt(other: Int) {
    this.mulAssign(other)
}

public fun Wrapping<Int>.div(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingDiv(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.divWrappingInt(other: Wrapping<Int>): Output = this.div(other)

public fun Wrapping<Int>.divAssign(other: Wrapping<Int>) {
    this = this / other
}

public fun Wrapping<Int>.divAssignWrappingInt(other: Wrapping<Int>) {
    this.divAssign(other)
}

public fun Wrapping<Int>.divAssign(other: Int) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.divAssignInt(other: Int) {
    this.divAssign(other)
}

public fun Wrapping<Int>.rem(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value.wrappingRem(other.value), fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.remWrappingInt(other: Wrapping<Int>): Output = this.rem(other)

public fun Wrapping<Int>.remAssign(other: Wrapping<Int>) {
    this = this % other
}

public fun Wrapping<Int>.remAssignWrappingInt(other: Wrapping<Int>) {
    this.remAssign(other)
}

public fun Wrapping<Int>.remAssign(other: Int) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.remAssignInt(other: Int) {
    this.remAssign(other)
}

public fun Wrapping<Int>.not(): Wrapping<Int> = Wrapping(!this.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.bitxor(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value xor other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.bitxorWrappingInt(other: Wrapping<Int>): Output = this.bitxor(other)

public fun Wrapping<Int>.bitxorAssign(other: Wrapping<Int>) {
    this = this xor other
}

public fun Wrapping<Int>.bitxorAssignWrappingInt(other: Wrapping<Int>) {
    this.bitxorAssign(other)
}

public fun Wrapping<Int>.bitxorAssign(other: Int) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.bitxorAssignInt(other: Int) {
    this.bitxorAssign(other)
}

public fun Wrapping<Int>.bitor(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value or other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.bitorWrappingInt(other: Wrapping<Int>): Output = this.bitor(other)

public fun Wrapping<Int>.bitorAssign(other: Wrapping<Int>) {
    this = this or other
}

public fun Wrapping<Int>.bitorAssignWrappingInt(other: Wrapping<Int>) {
    this.bitorAssign(other)
}

public fun Wrapping<Int>.bitorAssign(other: Int) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.bitorAssignInt(other: Int) {
    this.bitorAssign(other)
}

public fun Wrapping<Int>.bitand(other: Wrapping<Int>): Wrapping<Int> = Wrapping(this.value and other.value, fieldLayout = PrimitiveLayouts.USIZE64)

public fun Wrapping<Int>.bitandWrappingInt(other: Wrapping<Int>): Output = this.bitand(other)

public fun Wrapping<Int>.bitandAssign(other: Wrapping<Int>) {
    this = this and other
}

public fun Wrapping<Int>.bitandAssignWrappingInt(other: Wrapping<Int>) {
    this.bitandAssign(other)
}

public fun Wrapping<Int>.bitandAssign(other: Int) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.USIZE64)
}

public fun Wrapping<Int>.bitandAssignInt(other: Int) {
    this.bitandAssign(other)
}

public fun Wrapping<Int>.neg(): Wrapping<Int> = Wrapping(0, fieldLayout = PrimitiveLayouts.USIZE64) - this

public fun Wrapping<UByte>.addWrappingUByte(other: Wrapping<UByte>): Output = this.add(other)

public fun Wrapping<UByte>.addAssignWrappingUByte(other: Wrapping<UByte>) {
    this.addAssign(other)
}

public fun Wrapping<UByte>.addAssign(other: UByte) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.addAssignUByte(other: UByte) {
    this.addAssign(other)
}

public fun Wrapping<UByte>.subWrappingUByte(other: Wrapping<UByte>): Output = this.sub(other)

public fun Wrapping<UByte>.subAssignWrappingUByte(other: Wrapping<UByte>) {
    this.subAssign(other)
}

public fun Wrapping<UByte>.subAssign(other: UByte) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.subAssignUByte(other: UByte) {
    this.subAssign(other)
}

public fun Wrapping<UByte>.mulWrappingUByte(other: Wrapping<UByte>): Output = this.mul(other)

public fun Wrapping<UByte>.mulAssignWrappingUByte(other: Wrapping<UByte>) {
    this.mulAssign(other)
}

public fun Wrapping<UByte>.mulAssign(other: UByte) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.mulAssignUByte(other: UByte) {
    this.mulAssign(other)
}

public fun Wrapping<UByte>.divWrappingUByte(other: Wrapping<UByte>): Output = this.div(other)

public fun Wrapping<UByte>.divAssignWrappingUByte(other: Wrapping<UByte>) {
    this.divAssign(other)
}

public fun Wrapping<UByte>.divAssign(other: UByte) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.divAssignUByte(other: UByte) {
    this.divAssign(other)
}

public fun Wrapping<UByte>.remWrappingUByte(other: Wrapping<UByte>): Output = this.rem(other)

public fun Wrapping<UByte>.remAssignWrappingUByte(other: Wrapping<UByte>) {
    this.remAssign(other)
}

public fun Wrapping<UByte>.remAssign(other: UByte) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.remAssignUByte(other: UByte) {
    this.remAssign(other)
}

public fun Wrapping<UByte>.bitxorWrappingUByte(other: Wrapping<UByte>): Output = this.bitxor(other)

public fun Wrapping<UByte>.bitxorAssignWrappingUByte(other: Wrapping<UByte>) {
    this.bitxorAssign(other)
}

public fun Wrapping<UByte>.bitxorAssign(other: UByte) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.bitxorAssignUByte(other: UByte) {
    this.bitxorAssign(other)
}

public fun Wrapping<UByte>.bitorWrappingUByte(other: Wrapping<UByte>): Output = this.bitor(other)

public fun Wrapping<UByte>.bitorAssignWrappingUByte(other: Wrapping<UByte>) {
    this.bitorAssign(other)
}

public fun Wrapping<UByte>.bitorAssign(other: UByte) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.bitorAssignUByte(other: UByte) {
    this.bitorAssign(other)
}

public fun Wrapping<UByte>.bitandWrappingUByte(other: Wrapping<UByte>): Output = this.bitand(other)

public fun Wrapping<UByte>.bitandAssignWrappingUByte(other: Wrapping<UByte>) {
    this.bitandAssign(other)
}

public fun Wrapping<UByte>.bitandAssign(other: UByte) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.U8)
}

public fun Wrapping<UByte>.bitandAssignUByte(other: UByte) {
    this.bitandAssign(other)
}

public fun Wrapping<UShort>.addWrappingUShort(other: Wrapping<UShort>): Output = this.add(other)

public fun Wrapping<UShort>.addAssignWrappingUShort(other: Wrapping<UShort>) {
    this.addAssign(other)
}

public fun Wrapping<UShort>.addAssign(other: UShort) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.addAssignUShort(other: UShort) {
    this.addAssign(other)
}

public fun Wrapping<UShort>.subWrappingUShort(other: Wrapping<UShort>): Output = this.sub(other)

public fun Wrapping<UShort>.subAssignWrappingUShort(other: Wrapping<UShort>) {
    this.subAssign(other)
}

public fun Wrapping<UShort>.subAssign(other: UShort) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.subAssignUShort(other: UShort) {
    this.subAssign(other)
}

public fun Wrapping<UShort>.mulWrappingUShort(other: Wrapping<UShort>): Output = this.mul(other)

public fun Wrapping<UShort>.mulAssignWrappingUShort(other: Wrapping<UShort>) {
    this.mulAssign(other)
}

public fun Wrapping<UShort>.mulAssign(other: UShort) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.mulAssignUShort(other: UShort) {
    this.mulAssign(other)
}

public fun Wrapping<UShort>.divWrappingUShort(other: Wrapping<UShort>): Output = this.div(other)

public fun Wrapping<UShort>.divAssignWrappingUShort(other: Wrapping<UShort>) {
    this.divAssign(other)
}

public fun Wrapping<UShort>.divAssign(other: UShort) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.divAssignUShort(other: UShort) {
    this.divAssign(other)
}

public fun Wrapping<UShort>.remWrappingUShort(other: Wrapping<UShort>): Output = this.rem(other)

public fun Wrapping<UShort>.remAssignWrappingUShort(other: Wrapping<UShort>) {
    this.remAssign(other)
}

public fun Wrapping<UShort>.remAssign(other: UShort) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.remAssignUShort(other: UShort) {
    this.remAssign(other)
}

public fun Wrapping<UShort>.bitxorWrappingUShort(other: Wrapping<UShort>): Output = this.bitxor(other)

public fun Wrapping<UShort>.bitxorAssignWrappingUShort(other: Wrapping<UShort>) {
    this.bitxorAssign(other)
}

public fun Wrapping<UShort>.bitxorAssign(other: UShort) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.bitxorAssignUShort(other: UShort) {
    this.bitxorAssign(other)
}

public fun Wrapping<UShort>.bitorWrappingUShort(other: Wrapping<UShort>): Output = this.bitor(other)

public fun Wrapping<UShort>.bitorAssignWrappingUShort(other: Wrapping<UShort>) {
    this.bitorAssign(other)
}

public fun Wrapping<UShort>.bitorAssign(other: UShort) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.bitorAssignUShort(other: UShort) {
    this.bitorAssign(other)
}

public fun Wrapping<UShort>.bitandWrappingUShort(other: Wrapping<UShort>): Output = this.bitand(other)

public fun Wrapping<UShort>.bitandAssignWrappingUShort(other: Wrapping<UShort>) {
    this.bitandAssign(other)
}

public fun Wrapping<UShort>.bitandAssign(other: UShort) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.U16)
}

public fun Wrapping<UShort>.bitandAssignUShort(other: UShort) {
    this.bitandAssign(other)
}

public fun Wrapping<UInt>.addWrappingUInt(other: Wrapping<UInt>): Output = this.add(other)

public fun Wrapping<UInt>.addAssignWrappingUInt(other: Wrapping<UInt>) {
    this.addAssign(other)
}

public fun Wrapping<UInt>.addAssign(other: UInt) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.addAssignUInt(other: UInt) {
    this.addAssign(other)
}

public fun Wrapping<UInt>.subWrappingUInt(other: Wrapping<UInt>): Output = this.sub(other)

public fun Wrapping<UInt>.subAssignWrappingUInt(other: Wrapping<UInt>) {
    this.subAssign(other)
}

public fun Wrapping<UInt>.subAssign(other: UInt) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.subAssignUInt(other: UInt) {
    this.subAssign(other)
}

public fun Wrapping<UInt>.mulWrappingUInt(other: Wrapping<UInt>): Output = this.mul(other)

public fun Wrapping<UInt>.mulAssignWrappingUInt(other: Wrapping<UInt>) {
    this.mulAssign(other)
}

public fun Wrapping<UInt>.mulAssign(other: UInt) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.mulAssignUInt(other: UInt) {
    this.mulAssign(other)
}

public fun Wrapping<UInt>.divWrappingUInt(other: Wrapping<UInt>): Output = this.div(other)

public fun Wrapping<UInt>.divAssignWrappingUInt(other: Wrapping<UInt>) {
    this.divAssign(other)
}

public fun Wrapping<UInt>.divAssign(other: UInt) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.divAssignUInt(other: UInt) {
    this.divAssign(other)
}

public fun Wrapping<UInt>.remWrappingUInt(other: Wrapping<UInt>): Output = this.rem(other)

public fun Wrapping<UInt>.remAssignWrappingUInt(other: Wrapping<UInt>) {
    this.remAssign(other)
}

public fun Wrapping<UInt>.remAssign(other: UInt) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.remAssignUInt(other: UInt) {
    this.remAssign(other)
}

public fun Wrapping<UInt>.bitxorWrappingUInt(other: Wrapping<UInt>): Output = this.bitxor(other)

public fun Wrapping<UInt>.bitxorAssignWrappingUInt(other: Wrapping<UInt>) {
    this.bitxorAssign(other)
}

public fun Wrapping<UInt>.bitxorAssign(other: UInt) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.bitxorAssignUInt(other: UInt) {
    this.bitxorAssign(other)
}

public fun Wrapping<UInt>.bitorWrappingUInt(other: Wrapping<UInt>): Output = this.bitor(other)

public fun Wrapping<UInt>.bitorAssignWrappingUInt(other: Wrapping<UInt>) {
    this.bitorAssign(other)
}

public fun Wrapping<UInt>.bitorAssign(other: UInt) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.bitorAssignUInt(other: UInt) {
    this.bitorAssign(other)
}

public fun Wrapping<UInt>.bitandWrappingUInt(other: Wrapping<UInt>): Output = this.bitand(other)

public fun Wrapping<UInt>.bitandAssignWrappingUInt(other: Wrapping<UInt>) {
    this.bitandAssign(other)
}

public fun Wrapping<UInt>.bitandAssign(other: UInt) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.U32)
}

public fun Wrapping<UInt>.bitandAssignUInt(other: UInt) {
    this.bitandAssign(other)
}

public fun Wrapping<ULong>.addWrappingULong(other: Wrapping<ULong>): Output = this.add(other)

public fun Wrapping<ULong>.addAssignWrappingULong(other: Wrapping<ULong>) {
    this.addAssign(other)
}

public fun Wrapping<ULong>.addAssign(other: ULong) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.addAssignULong(other: ULong) {
    this.addAssign(other)
}

public fun Wrapping<ULong>.subWrappingULong(other: Wrapping<ULong>): Output = this.sub(other)

public fun Wrapping<ULong>.subAssignWrappingULong(other: Wrapping<ULong>) {
    this.subAssign(other)
}

public fun Wrapping<ULong>.subAssign(other: ULong) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.subAssignULong(other: ULong) {
    this.subAssign(other)
}

public fun Wrapping<ULong>.mulWrappingULong(other: Wrapping<ULong>): Output = this.mul(other)

public fun Wrapping<ULong>.mulAssignWrappingULong(other: Wrapping<ULong>) {
    this.mulAssign(other)
}

public fun Wrapping<ULong>.mulAssign(other: ULong) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.mulAssignULong(other: ULong) {
    this.mulAssign(other)
}

public fun Wrapping<ULong>.divWrappingULong(other: Wrapping<ULong>): Output = this.div(other)

public fun Wrapping<ULong>.divAssignWrappingULong(other: Wrapping<ULong>) {
    this.divAssign(other)
}

public fun Wrapping<ULong>.divAssign(other: ULong) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.divAssignULong(other: ULong) {
    this.divAssign(other)
}

public fun Wrapping<ULong>.remWrappingULong(other: Wrapping<ULong>): Output = this.rem(other)

public fun Wrapping<ULong>.remAssignWrappingULong(other: Wrapping<ULong>) {
    this.remAssign(other)
}

public fun Wrapping<ULong>.remAssign(other: ULong) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.remAssignULong(other: ULong) {
    this.remAssign(other)
}

public fun Wrapping<ULong>.bitxorWrappingULong(other: Wrapping<ULong>): Output = this.bitxor(other)

public fun Wrapping<ULong>.bitxorAssignWrappingULong(other: Wrapping<ULong>) {
    this.bitxorAssign(other)
}

public fun Wrapping<ULong>.bitxorAssign(other: ULong) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.bitxorAssignULong(other: ULong) {
    this.bitxorAssign(other)
}

public fun Wrapping<ULong>.bitorWrappingULong(other: Wrapping<ULong>): Output = this.bitor(other)

public fun Wrapping<ULong>.bitorAssignWrappingULong(other: Wrapping<ULong>) {
    this.bitorAssign(other)
}

public fun Wrapping<ULong>.bitorAssign(other: ULong) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.bitorAssignULong(other: ULong) {
    this.bitorAssign(other)
}

public fun Wrapping<ULong>.bitandWrappingULong(other: Wrapping<ULong>): Output = this.bitand(other)

public fun Wrapping<ULong>.bitandAssignWrappingULong(other: Wrapping<ULong>) {
    this.bitandAssign(other)
}

public fun Wrapping<ULong>.bitandAssign(other: ULong) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.U64)
}

public fun Wrapping<ULong>.bitandAssignULong(other: ULong) {
    this.bitandAssign(other)
}

public fun Wrapping<U128>.addWrappingU128(other: Wrapping<U128>): Output = this.add(other)

public fun Wrapping<U128>.addAssignWrappingU128(other: Wrapping<U128>) {
    this.addAssign(other)
}

public fun Wrapping<U128>.addAssign(other: U128) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.addAssignU128(other: U128) {
    this.addAssign(other)
}

public fun Wrapping<U128>.subWrappingU128(other: Wrapping<U128>): Output = this.sub(other)

public fun Wrapping<U128>.subAssignWrappingU128(other: Wrapping<U128>) {
    this.subAssign(other)
}

public fun Wrapping<U128>.subAssign(other: U128) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.subAssignU128(other: U128) {
    this.subAssign(other)
}

public fun Wrapping<U128>.mulWrappingU128(other: Wrapping<U128>): Output = this.mul(other)

public fun Wrapping<U128>.mulAssignWrappingU128(other: Wrapping<U128>) {
    this.mulAssign(other)
}

public fun Wrapping<U128>.mulAssign(other: U128) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.mulAssignU128(other: U128) {
    this.mulAssign(other)
}

public fun Wrapping<U128>.divWrappingU128(other: Wrapping<U128>): Output = this.div(other)

public fun Wrapping<U128>.divAssignWrappingU128(other: Wrapping<U128>) {
    this.divAssign(other)
}

public fun Wrapping<U128>.divAssign(other: U128) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.divAssignU128(other: U128) {
    this.divAssign(other)
}

public fun Wrapping<U128>.remWrappingU128(other: Wrapping<U128>): Output = this.rem(other)

public fun Wrapping<U128>.remAssignWrappingU128(other: Wrapping<U128>) {
    this.remAssign(other)
}

public fun Wrapping<U128>.remAssign(other: U128) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.remAssignU128(other: U128) {
    this.remAssign(other)
}

public fun Wrapping<U128>.bitxorWrappingU128(other: Wrapping<U128>): Output = this.bitxor(other)

public fun Wrapping<U128>.bitxorAssignWrappingU128(other: Wrapping<U128>) {
    this.bitxorAssign(other)
}

public fun Wrapping<U128>.bitxorAssign(other: U128) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.bitxorAssignU128(other: U128) {
    this.bitxorAssign(other)
}

public fun Wrapping<U128>.bitorWrappingU128(other: Wrapping<U128>): Output = this.bitor(other)

public fun Wrapping<U128>.bitorAssignWrappingU128(other: Wrapping<U128>) {
    this.bitorAssign(other)
}

public fun Wrapping<U128>.bitorAssign(other: U128) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.bitorAssignU128(other: U128) {
    this.bitorAssign(other)
}

public fun Wrapping<U128>.bitandWrappingU128(other: Wrapping<U128>): Output = this.bitand(other)

public fun Wrapping<U128>.bitandAssignWrappingU128(other: Wrapping<U128>) {
    this.bitandAssign(other)
}

public fun Wrapping<U128>.bitandAssign(other: U128) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low }))
}

public fun Wrapping<U128>.bitandAssignU128(other: U128) {
    this.bitandAssign(other)
}

public fun Wrapping<Byte>.addWrappingByte(other: Wrapping<Byte>): Output = this.add(other)

public fun Wrapping<Byte>.addAssignWrappingByte(other: Wrapping<Byte>) {
    this.addAssign(other)
}

public fun Wrapping<Byte>.addAssign(other: Byte) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.addAssignByte(other: Byte) {
    this.addAssign(other)
}

public fun Wrapping<Byte>.subWrappingByte(other: Wrapping<Byte>): Output = this.sub(other)

public fun Wrapping<Byte>.subAssignWrappingByte(other: Wrapping<Byte>) {
    this.subAssign(other)
}

public fun Wrapping<Byte>.subAssign(other: Byte) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.subAssignByte(other: Byte) {
    this.subAssign(other)
}

public fun Wrapping<Byte>.mulWrappingByte(other: Wrapping<Byte>): Output = this.mul(other)

public fun Wrapping<Byte>.mulAssignWrappingByte(other: Wrapping<Byte>) {
    this.mulAssign(other)
}

public fun Wrapping<Byte>.mulAssign(other: Byte) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.mulAssignByte(other: Byte) {
    this.mulAssign(other)
}

public fun Wrapping<Byte>.divWrappingByte(other: Wrapping<Byte>): Output = this.div(other)

public fun Wrapping<Byte>.divAssignWrappingByte(other: Wrapping<Byte>) {
    this.divAssign(other)
}

public fun Wrapping<Byte>.divAssign(other: Byte) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.divAssignByte(other: Byte) {
    this.divAssign(other)
}

public fun Wrapping<Byte>.remWrappingByte(other: Wrapping<Byte>): Output = this.rem(other)

public fun Wrapping<Byte>.remAssignWrappingByte(other: Wrapping<Byte>) {
    this.remAssign(other)
}

public fun Wrapping<Byte>.remAssign(other: Byte) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.remAssignByte(other: Byte) {
    this.remAssign(other)
}

public fun Wrapping<Byte>.bitxorWrappingByte(other: Wrapping<Byte>): Output = this.bitxor(other)

public fun Wrapping<Byte>.bitxorAssignWrappingByte(other: Wrapping<Byte>) {
    this.bitxorAssign(other)
}

public fun Wrapping<Byte>.bitxorAssign(other: Byte) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.bitxorAssignByte(other: Byte) {
    this.bitxorAssign(other)
}

public fun Wrapping<Byte>.bitorWrappingByte(other: Wrapping<Byte>): Output = this.bitor(other)

public fun Wrapping<Byte>.bitorAssignWrappingByte(other: Wrapping<Byte>) {
    this.bitorAssign(other)
}

public fun Wrapping<Byte>.bitorAssign(other: Byte) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.bitorAssignByte(other: Byte) {
    this.bitorAssign(other)
}

public fun Wrapping<Byte>.bitandWrappingByte(other: Wrapping<Byte>): Output = this.bitand(other)

public fun Wrapping<Byte>.bitandAssignWrappingByte(other: Wrapping<Byte>) {
    this.bitandAssign(other)
}

public fun Wrapping<Byte>.bitandAssign(other: Byte) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.I8)
}

public fun Wrapping<Byte>.bitandAssignByte(other: Byte) {
    this.bitandAssign(other)
}

public fun Wrapping<Short>.addWrappingShort(other: Wrapping<Short>): Output = this.add(other)

public fun Wrapping<Short>.addAssignWrappingShort(other: Wrapping<Short>) {
    this.addAssign(other)
}

public fun Wrapping<Short>.addAssign(other: Short) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.addAssignShort(other: Short) {
    this.addAssign(other)
}

public fun Wrapping<Short>.subWrappingShort(other: Wrapping<Short>): Output = this.sub(other)

public fun Wrapping<Short>.subAssignWrappingShort(other: Wrapping<Short>) {
    this.subAssign(other)
}

public fun Wrapping<Short>.subAssign(other: Short) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.subAssignShort(other: Short) {
    this.subAssign(other)
}

public fun Wrapping<Short>.mulWrappingShort(other: Wrapping<Short>): Output = this.mul(other)

public fun Wrapping<Short>.mulAssignWrappingShort(other: Wrapping<Short>) {
    this.mulAssign(other)
}

public fun Wrapping<Short>.mulAssign(other: Short) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.mulAssignShort(other: Short) {
    this.mulAssign(other)
}

public fun Wrapping<Short>.divWrappingShort(other: Wrapping<Short>): Output = this.div(other)

public fun Wrapping<Short>.divAssignWrappingShort(other: Wrapping<Short>) {
    this.divAssign(other)
}

public fun Wrapping<Short>.divAssign(other: Short) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.divAssignShort(other: Short) {
    this.divAssign(other)
}

public fun Wrapping<Short>.remWrappingShort(other: Wrapping<Short>): Output = this.rem(other)

public fun Wrapping<Short>.remAssignWrappingShort(other: Wrapping<Short>) {
    this.remAssign(other)
}

public fun Wrapping<Short>.remAssign(other: Short) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.remAssignShort(other: Short) {
    this.remAssign(other)
}

public fun Wrapping<Short>.bitxorWrappingShort(other: Wrapping<Short>): Output = this.bitxor(other)

public fun Wrapping<Short>.bitxorAssignWrappingShort(other: Wrapping<Short>) {
    this.bitxorAssign(other)
}

public fun Wrapping<Short>.bitxorAssign(other: Short) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.bitxorAssignShort(other: Short) {
    this.bitxorAssign(other)
}

public fun Wrapping<Short>.bitorWrappingShort(other: Wrapping<Short>): Output = this.bitor(other)

public fun Wrapping<Short>.bitorAssignWrappingShort(other: Wrapping<Short>) {
    this.bitorAssign(other)
}

public fun Wrapping<Short>.bitorAssign(other: Short) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.bitorAssignShort(other: Short) {
    this.bitorAssign(other)
}

public fun Wrapping<Short>.bitandWrappingShort(other: Wrapping<Short>): Output = this.bitand(other)

public fun Wrapping<Short>.bitandAssignWrappingShort(other: Wrapping<Short>) {
    this.bitandAssign(other)
}

public fun Wrapping<Short>.bitandAssign(other: Short) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.I16)
}

public fun Wrapping<Short>.bitandAssignShort(other: Short) {
    this.bitandAssign(other)
}

public fun Wrapping<Long>.addWrappingLong(other: Wrapping<Long>): Output = this.add(other)

public fun Wrapping<Long>.addAssignWrappingLong(other: Wrapping<Long>) {
    this.addAssign(other)
}

public fun Wrapping<Long>.addAssign(other: Long) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.addAssignLong(other: Long) {
    this.addAssign(other)
}

public fun Wrapping<Long>.subWrappingLong(other: Wrapping<Long>): Output = this.sub(other)

public fun Wrapping<Long>.subAssignWrappingLong(other: Wrapping<Long>) {
    this.subAssign(other)
}

public fun Wrapping<Long>.subAssign(other: Long) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.subAssignLong(other: Long) {
    this.subAssign(other)
}

public fun Wrapping<Long>.mulWrappingLong(other: Wrapping<Long>): Output = this.mul(other)

public fun Wrapping<Long>.mulAssignWrappingLong(other: Wrapping<Long>) {
    this.mulAssign(other)
}

public fun Wrapping<Long>.mulAssign(other: Long) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.mulAssignLong(other: Long) {
    this.mulAssign(other)
}

public fun Wrapping<Long>.divWrappingLong(other: Wrapping<Long>): Output = this.div(other)

public fun Wrapping<Long>.divAssignWrappingLong(other: Wrapping<Long>) {
    this.divAssign(other)
}

public fun Wrapping<Long>.divAssign(other: Long) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.divAssignLong(other: Long) {
    this.divAssign(other)
}

public fun Wrapping<Long>.remWrappingLong(other: Wrapping<Long>): Output = this.rem(other)

public fun Wrapping<Long>.remAssignWrappingLong(other: Wrapping<Long>) {
    this.remAssign(other)
}

public fun Wrapping<Long>.remAssign(other: Long) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.remAssignLong(other: Long) {
    this.remAssign(other)
}

public fun Wrapping<Long>.bitxorWrappingLong(other: Wrapping<Long>): Output = this.bitxor(other)

public fun Wrapping<Long>.bitxorAssignWrappingLong(other: Wrapping<Long>) {
    this.bitxorAssign(other)
}

public fun Wrapping<Long>.bitxorAssign(other: Long) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.bitxorAssignLong(other: Long) {
    this.bitxorAssign(other)
}

public fun Wrapping<Long>.bitorWrappingLong(other: Wrapping<Long>): Output = this.bitor(other)

public fun Wrapping<Long>.bitorAssignWrappingLong(other: Wrapping<Long>) {
    this.bitorAssign(other)
}

public fun Wrapping<Long>.bitorAssign(other: Long) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.bitorAssignLong(other: Long) {
    this.bitorAssign(other)
}

public fun Wrapping<Long>.bitandWrappingLong(other: Wrapping<Long>): Output = this.bitand(other)

public fun Wrapping<Long>.bitandAssignWrappingLong(other: Wrapping<Long>) {
    this.bitandAssign(other)
}

public fun Wrapping<Long>.bitandAssign(other: Long) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.I64)
}

public fun Wrapping<Long>.bitandAssignLong(other: Long) {
    this.bitandAssign(other)
}

public fun Wrapping<I128>.addWrappingI128(other: Wrapping<I128>): Output = this.add(other)

public fun Wrapping<I128>.addAssignWrappingI128(other: Wrapping<I128>) {
    this.addAssign(other)
}

public fun Wrapping<I128>.addAssign(other: I128) {
    this = this + Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.addAssignI128(other: I128) {
    this.addAssign(other)
}

public fun Wrapping<I128>.subWrappingI128(other: Wrapping<I128>): Output = this.sub(other)

public fun Wrapping<I128>.subAssignWrappingI128(other: Wrapping<I128>) {
    this.subAssign(other)
}

public fun Wrapping<I128>.subAssign(other: I128) {
    this = this - Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.subAssignI128(other: I128) {
    this.subAssign(other)
}

public fun Wrapping<I128>.mulWrappingI128(other: Wrapping<I128>): Output = this.mul(other)

public fun Wrapping<I128>.mulAssignWrappingI128(other: Wrapping<I128>) {
    this.mulAssign(other)
}

public fun Wrapping<I128>.mulAssign(other: I128) {
    this = this * Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.mulAssignI128(other: I128) {
    this.mulAssign(other)
}

public fun Wrapping<I128>.divWrappingI128(other: Wrapping<I128>): Output = this.div(other)

public fun Wrapping<I128>.divAssignWrappingI128(other: Wrapping<I128>) {
    this.divAssign(other)
}

public fun Wrapping<I128>.divAssign(other: I128) {
    this = this / Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.divAssignI128(other: I128) {
    this.divAssign(other)
}

public fun Wrapping<I128>.remWrappingI128(other: Wrapping<I128>): Output = this.rem(other)

public fun Wrapping<I128>.remAssignWrappingI128(other: Wrapping<I128>) {
    this.remAssign(other)
}

public fun Wrapping<I128>.remAssign(other: I128) {
    this = this % Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.remAssignI128(other: I128) {
    this.remAssign(other)
}

public fun Wrapping<I128>.bitxorWrappingI128(other: Wrapping<I128>): Output = this.bitxor(other)

public fun Wrapping<I128>.bitxorAssignWrappingI128(other: Wrapping<I128>) {
    this.bitxorAssign(other)
}

public fun Wrapping<I128>.bitxorAssign(other: I128) {
    this = this xor Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.bitxorAssignI128(other: I128) {
    this.bitxorAssign(other)
}

public fun Wrapping<I128>.bitorWrappingI128(other: Wrapping<I128>): Output = this.bitor(other)

public fun Wrapping<I128>.bitorAssignWrappingI128(other: Wrapping<I128>) {
    this.bitorAssign(other)
}

public fun Wrapping<I128>.bitorAssign(other: I128) {
    this = this or Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.bitorAssignI128(other: I128) {
    this.bitorAssign(other)
}

public fun Wrapping<I128>.bitandWrappingI128(other: Wrapping<I128>): Output = this.bitand(other)

public fun Wrapping<I128>.bitandAssignWrappingI128(other: Wrapping<I128>) {
    this.bitandAssign(other)
}

public fun Wrapping<I128>.bitandAssign(other: I128) {
    this = this and Wrapping(other, fieldLayout = PrimitiveLayouts.i128Layout(::I128, { it.high }, { it.low }))
}

public fun Wrapping<I128>.bitandAssignI128(other: I128) {
    this.bitandAssign(other)
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
public fun Wrapping.Companion.fromBe(x: Wrapping<Int>, fieldLayout: TypeLayout<Int>): Wrapping<Int> =
    Wrapping(
        run {
            val swapVal0 = (x.value)
            ((((swapVal0) shl 24)) or (((swapVal0) and 0x0000FF00) shl 8) or (((swapVal0) ushr 8) and 0x0000FF00) or ((swapVal0) ushr 24))
        },
        fieldLayout = fieldLayout,
    )

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
public fun Wrapping.Companion.fromLe(x: Wrapping<Int>, fieldLayout: TypeLayout<Int>): Wrapping<Int> = Wrapping(x.value, fieldLayout = fieldLayout)

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
