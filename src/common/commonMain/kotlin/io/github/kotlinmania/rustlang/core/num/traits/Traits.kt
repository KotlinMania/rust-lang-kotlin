// port-lint: source num/traits.rs
package io.github.kotlinmania.rustlang.core.num.traits

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128

// Implementation based on `numConv` by jhpratt, under (MIT OR Apache-2.0).

/**
 * Definitions of traits for numeric types
 * Trait for types that this type can be truncated to
 */
public interface TruncateTarget<Target> {
    public fun internalTruncate(): Target

    public fun internalSaturatingTruncate(): Target

    public fun internalCheckedTruncate(): Target?
}

/**
 * Trait for types that this type can be widened to
 */
public interface WidenTarget<Target> {
    public fun internalWiden(): Target
}

public fun UByte.internalTruncate(): UByte = this

public fun UByte.internalSaturatingTruncate(): UByte =
    if (this > UByte.MAX_VALUE) {
        UByte.MAX_VALUE
    } else if (this < UByte.MIN_VALUE) {
        UByte.MIN_VALUE
    } else {
        this
    }

public fun UByte.internalCheckedTruncate(): UByte? =
    if (this > UByte.MAX_VALUE || this < UByte.MIN_VALUE) {
        null
    } else {
        this
    }

public fun UShort.internalTruncate(): UShort = this

public fun UShort.internalSaturatingTruncate(): UShort =
    if (this > UShort.MAX_VALUE) {
        UShort.MAX_VALUE
    } else if (this < UShort.MIN_VALUE) {
        UShort.MIN_VALUE
    } else {
        this
    }

public fun UShort.internalCheckedTruncate(): UShort? =
    if (this > UShort.MAX_VALUE || this < UShort.MIN_VALUE) {
        null
    } else {
        this
    }

public fun UShort.internalTruncateUByte(): UByte = this.toUByte()

public fun UShort.internalSaturatingTruncateUByte(): UByte =
    if (this > UByte.MAX_VALUE.toUShort()) {
        UByte.MAX_VALUE
    } else if (this < UByte.MIN_VALUE.toUShort()) {
        UByte.MIN_VALUE
    } else {
        this.toUByte()
    }

public fun UShort.internalCheckedTruncateUByte(): UByte? =
    if (this > UByte.MAX_VALUE.toUShort() || this < UByte.MIN_VALUE.toUShort()) {
        null
    } else {
        this.toUByte()
    }

public fun UInt.internalTruncate(): UInt = this

public fun UInt.internalSaturatingTruncate(): UInt =
    if (this > UInt.MAX_VALUE) {
        UInt.MAX_VALUE
    } else if (this < UInt.MIN_VALUE) {
        UInt.MIN_VALUE
    } else {
        this
    }

public fun UInt.internalCheckedTruncate(): UInt? =
    if (this > UInt.MAX_VALUE || this < UInt.MIN_VALUE) {
        null
    } else {
        this
    }

public fun UInt.internalTruncateUShort(): UShort = this.toUShort()

public fun UInt.internalSaturatingTruncateUShort(): UShort =
    if (this > UShort.MAX_VALUE.toUInt()) {
        UShort.MAX_VALUE
    } else if (this < UShort.MIN_VALUE.toUInt()) {
        UShort.MIN_VALUE
    } else {
        this.toUShort()
    }

public fun UInt.internalCheckedTruncateUShort(): UShort? =
    if (this > UShort.MAX_VALUE.toUInt() || this < UShort.MIN_VALUE.toUInt()) {
        null
    } else {
        this.toUShort()
    }

public fun UInt.internalTruncateUByte(): UByte = this.toUByte()

public fun UInt.internalSaturatingTruncateUByte(): UByte =
    if (this > UByte.MAX_VALUE.toUInt()) {
        UByte.MAX_VALUE
    } else if (this < UByte.MIN_VALUE.toUInt()) {
        UByte.MIN_VALUE
    } else {
        this.toUByte()
    }

public fun UInt.internalCheckedTruncateUByte(): UByte? =
    if (this > UByte.MAX_VALUE.toUInt() || this < UByte.MIN_VALUE.toUInt()) {
        null
    } else {
        this.toUByte()
    }

public fun ULong.internalTruncate(): ULong = this

public fun ULong.internalSaturatingTruncate(): ULong =
    if (this > ULong.MAX_VALUE) {
        ULong.MAX_VALUE
    } else if (this < ULong.MIN_VALUE) {
        ULong.MIN_VALUE
    } else {
        this
    }

public fun ULong.internalCheckedTruncate(): ULong? =
    if (this > ULong.MAX_VALUE || this < ULong.MIN_VALUE) {
        null
    } else {
        this
    }

public fun ULong.internalTruncateUInt(): UInt = this.toUInt()

public fun ULong.internalSaturatingTruncateUInt(): UInt =
    if (this > UInt.MAX_VALUE.toULong()) {
        UInt.MAX_VALUE
    } else if (this < UInt.MIN_VALUE.toULong()) {
        UInt.MIN_VALUE
    } else {
        this.toUInt()
    }

public fun ULong.internalCheckedTruncateUInt(): UInt? =
    if (this > UInt.MAX_VALUE.toULong() || this < UInt.MIN_VALUE.toULong()) {
        null
    } else {
        this.toUInt()
    }

public fun ULong.internalTruncateUShort(): UShort = this.toUShort()

public fun ULong.internalSaturatingTruncateUShort(): UShort =
    if (this > UShort.MAX_VALUE.toULong()) {
        UShort.MAX_VALUE
    } else if (this < UShort.MIN_VALUE.toULong()) {
        UShort.MIN_VALUE
    } else {
        this.toUShort()
    }

public fun ULong.internalCheckedTruncateUShort(): UShort? =
    if (this > UShort.MAX_VALUE.toULong() || this < UShort.MIN_VALUE.toULong()) {
        null
    } else {
        this.toUShort()
    }

public fun ULong.internalTruncateUByte(): UByte = this.toUByte()

public fun ULong.internalSaturatingTruncateUByte(): UByte =
    if (this > UByte.MAX_VALUE.toULong()) {
        UByte.MAX_VALUE
    } else if (this < UByte.MIN_VALUE.toULong()) {
        UByte.MIN_VALUE
    } else {
        this.toUByte()
    }

public fun ULong.internalCheckedTruncateUByte(): UByte? =
    if (this > UByte.MAX_VALUE.toULong() || this < UByte.MIN_VALUE.toULong()) {
        null
    } else {
        this.toUByte()
    }

public fun U128.internalTruncate(): U128 = this

public fun U128.internalSaturatingTruncate(): U128 =
    if (this > U128.MAX_VALUE) {
        U128.MAX_VALUE
    } else if (this < U128.MIN_VALUE) {
        U128.MIN_VALUE
    } else {
        this
    }

public fun U128.internalCheckedTruncate(): U128? =
    if (this > U128.MAX_VALUE || this < U128.MIN_VALUE) {
        null
    } else {
        this
    }

public fun U128.internalTruncateULong(): ULong = this.toULong()

public fun U128.internalSaturatingTruncateULong(): ULong =
    if (this > U128.fromULong(ULong.MAX_VALUE)) {
        ULong.MAX_VALUE
    } else if (this < U128.fromULong(ULong.MIN_VALUE)) {
        ULong.MIN_VALUE
    } else {
        this.toULong()
    }

public fun U128.internalCheckedTruncateULong(): ULong? =
    if (this > U128.fromULong(ULong.MAX_VALUE) || this < U128.fromULong(ULong.MIN_VALUE)) {
        null
    } else {
        this.toULong()
    }

public fun U128.internalTruncateUInt(): UInt = this.toUInt()

public fun U128.internalSaturatingTruncateUInt(): UInt =
    if (this > U128.fromUInt(UInt.MAX_VALUE)) {
        UInt.MAX_VALUE
    } else if (this < U128.fromUInt(UInt.MIN_VALUE)) {
        UInt.MIN_VALUE
    } else {
        this.toUInt()
    }

public fun U128.internalCheckedTruncateUInt(): UInt? =
    if (this > U128.fromUInt(UInt.MAX_VALUE) || this < U128.fromUInt(UInt.MIN_VALUE)) {
        null
    } else {
        this.toUInt()
    }

public fun U128.internalTruncateUShort(): UShort = this.toUShort()

public fun U128.internalSaturatingTruncateUShort(): UShort =
    if (this > U128.fromUShort(UShort.MAX_VALUE)) {
        UShort.MAX_VALUE
    } else if (this < U128.fromUShort(UShort.MIN_VALUE)) {
        UShort.MIN_VALUE
    } else {
        this.toUShort()
    }

public fun U128.internalCheckedTruncateUShort(): UShort? =
    if (this > U128.fromUShort(UShort.MAX_VALUE) || this < U128.fromUShort(UShort.MIN_VALUE)) {
        null
    } else {
        this.toUShort()
    }

public fun U128.internalTruncateUByte(): UByte = this.toUByte()

public fun U128.internalSaturatingTruncateUByte(): UByte =
    if (this > U128.fromUByte(UByte.MAX_VALUE)) {
        UByte.MAX_VALUE
    } else if (this < U128.fromUByte(UByte.MIN_VALUE)) {
        UByte.MIN_VALUE
    } else {
        this.toUByte()
    }

public fun U128.internalCheckedTruncateUByte(): UByte? =
    if (this > U128.fromUByte(UByte.MAX_VALUE) || this < U128.fromUByte(UByte.MIN_VALUE)) {
        null
    } else {
        this.toUByte()
    }

public fun Int.internalTruncate(): Int = this

public fun Int.internalSaturatingTruncate(): Int =
    if (this > Int.MAX_VALUE) {
        Int.MAX_VALUE
    } else if (this < 0) {
        0
    } else {
        this
    }

public fun Int.internalCheckedTruncate(): Int? =
    if (this > Int.MAX_VALUE || this < 0) {
        null
    } else {
        this
    }

public fun Int.internalTruncateUShort(): UShort = this.toUShort()

public fun Int.internalSaturatingTruncateUShort(): UShort =
    if (this > UShort.MAX_VALUE.toInt()) {
        UShort.MAX_VALUE
    } else if (this < UShort.MIN_VALUE.toInt()) {
        UShort.MIN_VALUE
    } else {
        this.toUShort()
    }

public fun Int.internalCheckedTruncateUShort(): UShort? =
    if (this > UShort.MAX_VALUE.toInt() || this < UShort.MIN_VALUE.toInt()) {
        null
    } else {
        this.toUShort()
    }

public fun Int.internalTruncateUByte(): UByte = this.toUByte()

public fun Int.internalSaturatingTruncateUByte(): UByte =
    if (this > UByte.MAX_VALUE.toInt()) {
        UByte.MAX_VALUE
    } else if (this < UByte.MIN_VALUE.toInt()) {
        UByte.MIN_VALUE
    } else {
        this.toUByte()
    }

public fun Int.internalCheckedTruncateUByte(): UByte? =
    if (this > UByte.MAX_VALUE.toInt() || this < UByte.MIN_VALUE.toInt()) {
        null
    } else {
        this.toUByte()
    }

public fun Byte.internalTruncate(): Byte = this

public fun Byte.internalSaturatingTruncate(): Byte =
    if (this > Byte.MAX_VALUE) {
        Byte.MAX_VALUE
    } else if (this < Byte.MIN_VALUE) {
        Byte.MIN_VALUE
    } else {
        this
    }

public fun Byte.internalCheckedTruncate(): Byte? =
    if (this > Byte.MAX_VALUE || this < Byte.MIN_VALUE) {
        null
    } else {
        this
    }

public fun Short.internalTruncate(): Short = this

public fun Short.internalSaturatingTruncate(): Short =
    if (this > Short.MAX_VALUE) {
        Short.MAX_VALUE
    } else if (this < Short.MIN_VALUE) {
        Short.MIN_VALUE
    } else {
        this
    }

public fun Short.internalCheckedTruncate(): Short? =
    if (this > Short.MAX_VALUE || this < Short.MIN_VALUE) {
        null
    } else {
        this
    }

public fun Short.internalTruncateByte(): Byte = this.toByte()

public fun Short.internalSaturatingTruncateByte(): Byte =
    if (this > Byte.MAX_VALUE.toShort()) {
        Byte.MAX_VALUE
    } else if (this < Byte.MIN_VALUE.toShort()) {
        Byte.MIN_VALUE
    } else {
        this.toByte()
    }

public fun Short.internalCheckedTruncateByte(): Byte? =
    if (this > Byte.MAX_VALUE.toShort() || this < Byte.MIN_VALUE.toShort()) {
        null
    } else {
        this.toByte()
    }

public fun Int.internalTruncateInt(): Int = this

public fun Int.internalSaturatingTruncateInt(): Int =
    if (this > Int.MAX_VALUE) {
        Int.MAX_VALUE
    } else if (this < Int.MIN_VALUE) {
        Int.MIN_VALUE
    } else {
        this
    }

public fun Int.internalCheckedTruncateInt(): Int? =
    if (this > Int.MAX_VALUE || this < Int.MIN_VALUE) {
        null
    } else {
        this
    }

public fun Int.internalTruncateShort(): Short = this.toShort()

public fun Int.internalSaturatingTruncateShort(): Short =
    if (this > Short.MAX_VALUE.toInt()) {
        Short.MAX_VALUE
    } else if (this < Short.MIN_VALUE.toInt()) {
        Short.MIN_VALUE
    } else {
        this.toShort()
    }

public fun Int.internalCheckedTruncateShort(): Short? =
    if (this > Short.MAX_VALUE.toInt() || this < Short.MIN_VALUE.toInt()) {
        null
    } else {
        this.toShort()
    }

public fun Int.internalTruncateByte(): Byte = this.toByte()

public fun Int.internalSaturatingTruncateByte(): Byte =
    if (this > Byte.MAX_VALUE.toInt()) {
        Byte.MAX_VALUE
    } else if (this < Byte.MIN_VALUE.toInt()) {
        Byte.MIN_VALUE
    } else {
        this.toByte()
    }

public fun Int.internalCheckedTruncateByte(): Byte? =
    if (this > Byte.MAX_VALUE.toInt() || this < Byte.MIN_VALUE.toInt()) {
        null
    } else {
        this.toByte()
    }

public fun Long.internalTruncate(): Long = this

public fun Long.internalSaturatingTruncate(): Long =
    if (this > Long.MAX_VALUE) {
        Long.MAX_VALUE
    } else if (this < Long.MIN_VALUE) {
        Long.MIN_VALUE
    } else {
        this
    }

public fun Long.internalCheckedTruncate(): Long? =
    if (this > Long.MAX_VALUE || this < Long.MIN_VALUE) {
        null
    } else {
        this
    }

public fun Long.internalTruncateInt(): Int = this.toInt()

public fun Long.internalSaturatingTruncateInt(): Int =
    if (this > Int.MAX_VALUE.toLong()) {
        Int.MAX_VALUE
    } else if (this < Int.MIN_VALUE.toLong()) {
        Int.MIN_VALUE
    } else {
        this.toInt()
    }

public fun Long.internalCheckedTruncateInt(): Int? =
    if (this > Int.MAX_VALUE.toLong() || this < Int.MIN_VALUE.toLong()) {
        null
    } else {
        this.toInt()
    }

public fun Long.internalTruncateShort(): Short = this.toShort()

public fun Long.internalSaturatingTruncateShort(): Short =
    if (this > Short.MAX_VALUE.toLong()) {
        Short.MAX_VALUE
    } else if (this < Short.MIN_VALUE.toLong()) {
        Short.MIN_VALUE
    } else {
        this.toShort()
    }

public fun Long.internalCheckedTruncateShort(): Short? =
    if (this > Short.MAX_VALUE.toLong() || this < Short.MIN_VALUE.toLong()) {
        null
    } else {
        this.toShort()
    }

public fun Long.internalTruncateByte(): Byte = this.toByte()

public fun Long.internalSaturatingTruncateByte(): Byte =
    if (this > Byte.MAX_VALUE.toLong()) {
        Byte.MAX_VALUE
    } else if (this < Byte.MIN_VALUE.toLong()) {
        Byte.MIN_VALUE
    } else {
        this.toByte()
    }

public fun Long.internalCheckedTruncateByte(): Byte? =
    if (this > Byte.MAX_VALUE.toLong() || this < Byte.MIN_VALUE.toLong()) {
        null
    } else {
        this.toByte()
    }

public fun I128.internalTruncate(): I128 = this

public fun I128.internalSaturatingTruncate(): I128 =
    if (this > I128.MAX_VALUE) {
        I128.MAX_VALUE
    } else if (this < I128.MIN_VALUE) {
        I128.MIN_VALUE
    } else {
        this
    }

public fun I128.internalCheckedTruncate(): I128? =
    if (this > I128.MAX_VALUE || this < I128.MIN_VALUE) {
        null
    } else {
        this
    }

public fun I128.internalTruncateLong(): Long = this.toLong()

public fun I128.internalSaturatingTruncateLong(): Long =
    if (this > I128.fromLong(Long.MAX_VALUE)) {
        Long.MAX_VALUE
    } else if (this < I128.fromLong(Long.MIN_VALUE)) {
        Long.MIN_VALUE
    } else {
        this.toLong()
    }

public fun I128.internalCheckedTruncateLong(): Long? =
    if (this > I128.fromLong(Long.MAX_VALUE) || this < I128.fromLong(Long.MIN_VALUE)) {
        null
    } else {
        this.toLong()
    }

public fun I128.internalTruncateInt(): Int = this.toInt()

public fun I128.internalSaturatingTruncateInt(): Int =
    if (this > I128.fromInt(Int.MAX_VALUE)) {
        Int.MAX_VALUE
    } else if (this < I128.fromInt(Int.MIN_VALUE)) {
        Int.MIN_VALUE
    } else {
        this.toInt()
    }

public fun I128.internalCheckedTruncateInt(): Int? =
    if (this > I128.fromInt(Int.MAX_VALUE) || this < I128.fromInt(Int.MIN_VALUE)) {
        null
    } else {
        this.toInt()
    }

public fun I128.internalTruncateShort(): Short = this.toShort()

public fun I128.internalSaturatingTruncateShort(): Short =
    if (this > I128.fromShort(Short.MAX_VALUE)) {
        Short.MAX_VALUE
    } else if (this < I128.fromShort(Short.MIN_VALUE)) {
        Short.MIN_VALUE
    } else {
        this.toShort()
    }

public fun I128.internalCheckedTruncateShort(): Short? =
    if (this > I128.fromShort(Short.MAX_VALUE) || this < I128.fromShort(Short.MIN_VALUE)) {
        null
    } else {
        this.toShort()
    }

public fun I128.internalTruncateByte(): Byte = this.toByte()

public fun I128.internalSaturatingTruncateByte(): Byte =
    if (this > I128.fromByte(Byte.MAX_VALUE)) {
        Byte.MAX_VALUE
    } else if (this < I128.fromByte(Byte.MIN_VALUE)) {
        Byte.MIN_VALUE
    } else {
        this.toByte()
    }

public fun I128.internalCheckedTruncateByte(): Byte? =
    if (this > I128.fromByte(Byte.MAX_VALUE) || this < I128.fromByte(Byte.MIN_VALUE)) {
        null
    } else {
        this.toByte()
    }

public fun UByte.internalWiden(): UByte = this

public fun UByte.internalWidenUShort(): UShort = this.toUShort()

public fun UByte.internalWidenUInt(): UInt = this.toUInt()

public fun UByte.internalWidenULong(): ULong = this.toULong()

public fun UByte.internalWidenU128(): U128 = U128.fromUByte(this)

public fun UByte.internalWidenInt(): Int = this.toInt()

public fun UShort.internalWiden(): UShort = this

public fun UShort.internalWidenUInt(): UInt = this.toUInt()

public fun UShort.internalWidenULong(): ULong = this.toULong()

public fun UShort.internalWidenU128(): U128 = U128.fromUShort(this)

public fun UShort.internalWidenInt(): Int = this.toInt()

public fun UInt.internalWiden(): UInt = this

public fun UInt.internalWidenULong(): ULong = this.toULong()

public fun UInt.internalWidenU128(): U128 = U128.fromUInt(this)

public fun ULong.internalWiden(): ULong = this

public fun ULong.internalWidenU128(): U128 = U128.fromULong(this)

public fun U128.internalWiden(): U128 = this

public fun Int.internalWiden(): Int = this

public fun Byte.internalWiden(): Byte = this

public fun Byte.internalWidenShort(): Short = this.toShort()

public fun Byte.internalWidenInt(): Int = this.toInt()

public fun Byte.internalWidenLong(): Long = this.toLong()

public fun Byte.internalWidenI128(): I128 = I128.fromByte(this)

public fun Short.internalWiden(): Short = this

public fun Short.internalWidenInt(): Int = this.toInt()

public fun Short.internalWidenLong(): Long = this.toLong()

public fun Short.internalWidenI128(): I128 = I128.fromShort(this)

public fun Int.internalWidenInt(): Int = this

public fun Int.internalWidenLong(): Long = this.toLong()

public fun Int.internalWidenI128(): I128 = I128.fromInt(this)

public fun Long.internalWiden(): Long = this

public fun Long.internalWidenI128(): I128 = I128.fromLong(this)

public fun I128.internalWiden(): I128 = this
