// port-lint: source byte_str/traits.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.bytestr.traits

import io.github.kotlinmania.rustlang.core.bytestr.ByteStr
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ops.indexrange.IndexRange
import io.github.kotlinmania.rustlang.core.ops.range.Bound
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.core.ops.range.RangeFull
import io.github.kotlinmania.rustlang.core.ops.range.RangeInclusive
import io.github.kotlinmania.rustlang.core.ops.range.RangeTo
import io.github.kotlinmania.rustlang.core.ops.range.RangeToInclusive
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.SliceIndex

// Trait implementations for `ByteStr`.

public fun ByteStr.cmp(other: ByteStr): Ordering = this.value.cmp(other.first)

public fun ByteStr.partialCmp(other: ByteStr): Ordering? = this.value.partialCmp(other.first)

public fun ByteStr.eq(other: ByteStr): Boolean = this.value == other.first

public fun <H> ByteStr.hash(state: H) {
    this.value.hash(state)
}

// PartialOrd with `[UByte]` omitted to avoid inference failures
// PartialOrd with `[UByte]` omitted to avoid inference failures
// PartialOrd with `String` omitted to avoid inference failures
// PartialOrd with `String` omitted to avoid inference failures
// PartialOrd with `[UByte; N]` omitted to avoid inference failures
// PartialOrd with `[UByte; N]` omitted to avoid inference failures
public fun ByteStr.eq(other: List<UByte>): Boolean {
    val other1: List<UByte> = other
    return this.asBytes().eq(other1)
}

public fun List<UByte>.eq(other: ByteStr): Boolean {
    val thisRef: List<UByte> = this
    return thisRef.eq(other.asBytes())
}

public fun ByteStr.eqUByteSlice(other: List<UByte>): Boolean {
    val other1: List<UByte> = other
    return this.asBytes().eq(other1)
}

public fun List<UByte>.eqByteStr(other: ByteStr): Boolean {
    val thisRef: List<UByte> = this
    return thisRef.eq(other.asBytes())
}

public fun ByteStr.eq(other: String): Boolean {
    val other1: List<UByte> = other
    return this.asBytes().eq(other1)
}

public fun String.eq(other: ByteStr): Boolean {
    val thisRef: List<UByte> = this
    return thisRef.eq(other.asBytes())
}

public fun ByteStr.eqString(other: String): Boolean {
    val other1: List<UByte> = other
    return this.asBytes().eq(other1)
}

public fun String.eqByteStr(other: ByteStr): Boolean {
    val thisRef: List<UByte> = this
    return thisRef.eq(other.asBytes())
}

public fun ByteStr.eq(other: UByteArray, n: Int): Boolean {
    val other1: List<UByte> = other.asRef()
    return this.asBytes().eq(other1)
}

public fun UByteArray.eqByteStrFromUByteArrayn(other: ByteStr, n: Int): Boolean {
    val thisRef: List<UByte> = this.asRef()
    return thisRef.eq(other.asBytes())
}

public fun ByteStr.eqUByteArrayn(other: UByteArray, n: Int): Boolean {
    val other1: List<UByte> = other.asRef()
    return this.asBytes().eq(other1)
}

public fun UByteArray.eq(other: ByteStr, n: Int): Boolean {
    val thisRef: List<UByte> = this.asRef()
    return thisRef.eq(other.asBytes())
}

public fun <I> ByteStr.index(index: I): Output where I : SliceIndex<ByteStr, Any?> = index.index(this)

public fun <I> ByteStr.indexMut(index: I): Output where I : SliceIndex<ByteStr, Any?> = index.indexMut(this)

public fun RangeFull.get(slice: ByteStr): ByteStr? = slice

public fun RangeFull.getMut(slice: ByteStr): ByteStr? = slice

public fun RangeFull.getUnchecked(slice: ConstPtr): ConstPtr = slice

public fun RangeFull.getUncheckedMut(slice: MutPtr): MutPtr = slice

public fun RangeFull.index(slice: ByteStr): ByteStr = slice

public fun RangeFull.indexMut(slice: ByteStr): ByteStr = slice

public fun Int.getSliceIndex(slice: ByteStr): UByte? = this.get(slice.asBytes())

public fun Int.getMutSliceIndex(slice: ByteStr): UByte? = this.getMut(slice.asBytesMut())

public fun Int.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUnchecked`.
    return this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))
}

public fun Int.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr {
    // SAFETY: the caller has to uphold the safety contract for `getUncheckedMut`.
    return this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))
}

public fun Int.indexSliceIndex(slice: ByteStr): UByte = this.index(slice.asBytes())

public fun Int.indexMutSliceIndex(slice: ByteStr): UByte = this.indexMut(slice.asBytesMut())

public fun IndexRange.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun IndexRange.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun IndexRange.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun IndexRange.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun IndexRange.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun IndexRange.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun Range<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun Range<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun Range<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun Range<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun Range<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun Range<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun io.github.kotlinmania.rustlang.core.range.Range<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun io.github.kotlinmania.rustlang.core.range.Range<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun io.github.kotlinmania.rustlang.core.range.Range<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun io.github.kotlinmania.rustlang.core.range.Range<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun io.github.kotlinmania.rustlang.core.range.Range<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun io.github.kotlinmania.rustlang.core.range.Range<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun RangeTo<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun RangeTo<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun RangeTo<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun RangeTo<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun RangeTo<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun RangeTo<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun RangeFrom<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun RangeFrom<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun RangeFrom<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun RangeFrom<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun RangeFrom<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun RangeFrom<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun io.github.kotlinmania.rustlang.core.range.RangeFrom<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun io.github.kotlinmania.rustlang.core.range.RangeFrom<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun io.github.kotlinmania.rustlang.core.range.RangeFrom<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun io.github.kotlinmania.rustlang.core.range.RangeFrom<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun io.github.kotlinmania.rustlang.core.range.RangeFrom<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun io.github.kotlinmania.rustlang.core.range.RangeFrom<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun RangeInclusive<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun RangeInclusive<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun RangeInclusive<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun RangeInclusive<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun RangeInclusive<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun RangeInclusive<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun io.github.kotlinmania.rustlang.core.range.RangeInclusive<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun io.github.kotlinmania.rustlang.core.range.RangeInclusive<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun io.github.kotlinmania.rustlang.core.range.RangeInclusive<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun io.github.kotlinmania.rustlang.core.range.RangeInclusive<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun io.github.kotlinmania.rustlang.core.range.RangeInclusive<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun io.github.kotlinmania.rustlang.core.range.RangeInclusive<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun RangeToInclusive<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun RangeToInclusive<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun RangeToInclusive<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun RangeToInclusive<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun RangeToInclusive<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun RangeToInclusive<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun io.github.kotlinmania.rustlang.core.range.RangeToInclusive<Int>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun io.github.kotlinmania.rustlang.core.range.RangeToInclusive<Int>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun io.github.kotlinmania.rustlang.core.range.RangeToInclusive<Int>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta))

public fun io.github.kotlinmania.rustlang.core.range.RangeToInclusive<Int>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta))

public fun io.github.kotlinmania.rustlang.core.range.RangeToInclusive<Int>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun io.github.kotlinmania.rustlang.core.range.RangeToInclusive<Int>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

public fun Pair<Bound<Int>, Bound<Int>>.getSliceIndex(slice: ByteStr): ByteStr? = this.get(slice.asBytes())?.let(ByteStr::fromBytes)

public fun Pair<Bound<Int>, Bound<Int>>.getMutSliceIndex(slice: ByteStr): ByteStr? = this.getMut(slice.asBytesMut())?.let(ByteStr::fromBytesMut)

public fun Pair<Bound<Int>, Bound<Int>>.getUncheckedSliceIndex(slice: ConstPtr): ConstPtr = constPtrFromRustCast(this.getUnchecked(ConstPtr(slice.rawAddress, slice.rawMeta)))

public fun Pair<Bound<Int>, Bound<Int>>.getUncheckedMutSliceIndex(slice: MutPtr): MutPtr = mutPtrFromRustCast(this.getUncheckedMut(MutPtr(slice.rawAddress, slice.rawMeta)))

public fun Pair<Bound<Int>, Bound<Int>>.indexSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytes(this.index(slice.asBytes()))

public fun Pair<Bound<Int>, Bound<Int>>.indexMutSliceIndex(slice: ByteStr): ByteStr = ByteStr.fromBytesMut(this.indexMut(slice.asBytesMut()))

private const val IMPL_PARTIAL_EQ_TEMPLATE: String = "class PartialEq< \$rhs> forLoop \$lhs {fun eq(& this, other: \$rhs)-> bool {val other:&[u8]= other.asRef(); PartialEq.eq(this.asBytes(), other)}} class PartialEq< \$lhs> forLoop \$rhs {fun eq(& this, other: \$lhs)-> bool {val thisRef:&[u8]= this.asRef(); PartialEq.eq(thisRef, other.asBytes())}}"

private const val IMPL_PARTIAL_EQ_ORD_TEMPLATE: String = "byteStr.implPartialEq(\$lhs, \$rhs); class PartialOrd< \$rhs> forLoop \$lhs {fun partialCmp(& this, other: \$rhs)-> Option< Ordering> {val other:&[u8]= other.asRef(); PartialOrd.partialCmp(this.asBytes(), other)}} class PartialOrd< \$lhs> forLoop \$rhs {fun partialCmp(& this, other: \$lhs)-> Option< Ordering> {val thisRef:&[u8]= this.asRef(); PartialOrd.partialCmp(thisRef, other.asBytes())}}"

private const val IMPL_PARTIAL_EQ_N_TEMPLATE: String = "class< const N: usize> PartialEq< \$rhs> forLoop \$lhs {fun eq(& this, other: \$rhs)-> bool {val other:&[u8]= other.asRef(); PartialEq.eq(this.asBytes(), other)}} class< const N: usize> PartialEq< \$lhs> forLoop \$rhs {fun eq(& this, other: \$lhs)-> bool {val thisRef:&[u8]= this.asRef(); PartialEq.eq(thisRef, other.asBytes())}}"

public fun implPartialEq(lhs: String = "", rhs: String = ""): String =
    arrayOf("\$lhs" to lhs, "\$rhs" to rhs).fold(IMPL_PARTIAL_EQ_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }

public fun implPartialEqOrd(lhs: String = "", rhs: String = ""): String =
    arrayOf("\$lhs" to lhs, "\$rhs" to rhs).fold(IMPL_PARTIAL_EQ_ORD_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }

public fun implPartialEqN(lhs: String = "", rhs: String = ""): String =
    arrayOf("\$lhs" to lhs, "\$rhs" to rhs).fold(IMPL_PARTIAL_EQ_N_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
