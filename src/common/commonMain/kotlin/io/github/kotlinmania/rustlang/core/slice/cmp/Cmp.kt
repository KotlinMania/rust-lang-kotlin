// port-lint: source slice/cmp.rs
package io.github.kotlinmania.rustlang.core.slice.cmp

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.cmp.PartialOrd
import io.github.kotlinmania.rustlang.core.cmp.bytewise.BytewiseEq
import io.github.kotlinmania.rustlang.core.intrinsics.compareBytes
import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.kmPartialCompareValues
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.transmuteCopy
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.chunksExact
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr

// Comparison traits for `[T]`.

public fun <T, U> List<T>.eq(other: List<U>): Boolean {
    val len = this.size
    return if (len == other.size) {
        // SAFETY: Just checked that they're the same length, and the pointers
        // come from references-to-slices so they're guaranteed readable.
        SlicePartialEq.equalSameLength(this.asPtr(), other.asPtr(), len)
    } else {
        false
    }
}

/**
 * Implements comparison of slices [lexicographically](Ord#lexicographical-comparison).
 */
public fun <T> List<T>.cmp(other: List<T>): Ordering = SliceOrd.compare(this, other)

public fun asUnderlying(x: ControlFlow<Boolean, Unit>): UByte {
    // SAFETY: This will only compile if `Boolean` and `ControlFlow<Boolean>` have the same
    // size (which isn't guaranteed but this is libcore). Because they have the same
    // size, it's a niched implementation, which in one byte means there can't be
    // any uninitialized memory. The callers then only check for `0` or `1` from this,
    // which must necessarily match the `Break` variant, and we're fine no matter
    // what ends up getting picked as the value representing `Continue(())`.
    return transmuteWithLayouts<ControlFlow<Boolean, Unit>, UByte>(x, UnresolvedTypeLayout<ControlFlow<Boolean, Unit>>(), PrimitiveLayouts.U8)
}

/**
 * Implements comparison of slices [lexicographically](Ord#lexicographical-comparison).
 */
public fun <T> List<T>.partialCmp(other: List<T>): Ordering? = SlicePartialOrd.partialCompare(this, other)

public fun <T> List<T>.lt(other: List<T>): Boolean {
    // This is certainly not the obvious way to implement these methods.
    // Unfortunately, using anything that looks at the discriminant means that
    // LLVM sees a check for `2` (aka `ControlFlow<Boolean>.Continue(())`) and
    // gets very distracted by that, ending up generating extraneous code.
    // This should be changed to something simpler once either LLVM is smarter,
    // see <https://github.com/llvm/llvm-project/issues/132678>, or we generate
    // niche discriminant checks in a way that doesn't trigger it.
    return asUnderlying(this.chainingLess(other)) == 1u.toUByte()
}

public fun <T> List<T>.le(other: List<T>): Boolean = asUnderlying(this.chainingLess(other)) != 0u.toUByte()

public fun <T> List<T>.gt(other: List<T>): Boolean = asUnderlying(this.chainingGreater(other)) == 1u.toUByte()

public fun <T> List<T>.ge(other: List<T>): Boolean = asUnderlying(this.chainingGreater(other)) != 0u.toUByte()

public fun <T> List<T>.chainingLess(other: List<T>): ControlFlow<Boolean, Unit> = SliceChain.chainingLess(this, other)

public fun <T> List<T>.chainingGreater(other: List<T>): ControlFlow<Boolean, Unit> = SliceChain.chainingGreater(this, other)

// intermediate trait for specialization of slice's PartialEq
public interface SlicePartialEq<B, T> {
    /**
     * # Safety
     * `lhs` and `rhs` are both readable for `len` elements
     */
    public fun equalSameLength(lhs: ConstPtr, rhs: ConstPtr, len: Int): Boolean
}

// Generic slice equality
// It's not worth trying to inline the loops underneath here *in MIR*,
// and preventing it encourages more useful inlining upstream,
// such as in `<String as PartialEq>.eq`.
// The codegen backend can still inline it later if needed.
public fun <A, B> slicePartialEqEqualSameLengthA(lhs: ConstPtr, rhs: ConstPtr, len: Int, typeLayoutB: TypeLayout<B> = UnresolvedTypeLayout()): Boolean {
    // Implemented as explicit indexing rather
    // than zipped iterators for performance reasons.
    // See PR https://github.com/rust-lang/rust/pull/116846
    for (idx in 0 until len) {
        // SAFETY: idx < len, so both are in-bounds and readable
        if ((UnresolvedTypeLayout<A>()).readNative((ConstPtr((lhs).rawAddress + ((idx).toLong() * (UnresolvedTypeLayout<A>()).strideBytes))).rawAddress) != (typeLayoutB).readNative((ConstPtr((rhs).rawAddress + ((idx).toLong() * (typeLayoutB).strideBytes))).rawAddress)) {
            return false
        }
    }
    return true
}

// When each element can be compared byte-wise, we can compare all the bytes
// from the whole size in one call to the intrinsics.
public fun <A, B> slicePartialEqEqualSameLengthA(lhs: ConstPtr, rhs: ConstPtr, len: Int): Boolean where A : BytewiseEq<B> {
    // SAFETY: by our precondition, `lhs` and `rhs` are guaranteed to be valid
    // for reading `len` values, which also means the size is guaranteed
    // not to overflow because it exists in memory;
    return run {
        val size = ((len) * (A.SIZE))
        compareBytes(lhs, rhs, size) == 0
    }
}

public interface SlicePartialOrd<T> {
    public fun partialCompare(left: List<T>, right: List<T>): Ordering?
}

public interface SliceChain<T> {
    public fun chainingLess(left: List<T>, right: List<T>): ControlFlow<Boolean, Unit>

    public fun chainingGreater(left: List<T>, right: List<T>): ControlFlow<Boolean, Unit>
}

public typealias AlwaysBreak<B> = ControlFlow<B, Nothing>

public fun <A> slicePartialOrdPartialCompareA(left: List<A>, right: List<A>): Ordering? {
    val elemChain =
        { a, b -> when (val matchVal = kmPartialCompareValues(a, b)?.let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Ordering.Equal -> ControlFlow.Continue(Unit)
            else -> {
                val nonEq = matchVal
                ControlFlow.Break(nonEq)
            }
        } }
    val lenChain = { a: Any?, b: Any? -> ControlFlow.Break(Int.partialCmp(a, b)) }
    val tupleStructVal = chainingImpl(left, right, elemChain, lenChain)
    val b = tupleStructVal.value
    return b
}

public fun <A> sliceChainChainingLessA(left: List<A>, right: List<A>): ControlFlow<Boolean, Unit> = chainingImpl(left, right, PartialOrd.chainingLess, Int.chainingLess)

public fun <A> sliceChainChainingGreaterA(left: List<A>, right: List<A>): ControlFlow<Boolean, Unit> = chainingImpl(left, right, PartialOrd.chainingGreater, Int.chainingGreater)

public fun <A, B, C> chainingImpl(left: List<A>, right: List<A>, elemChain: (A, A) -> ControlFlow<B, Unit>, lenChain: (Int, Int) -> ControlFlow<B, C>): ControlFlow<B, C> {
    val l = minOf(left.size, right.size)
    // Slice to the loop iteration range to enable bound check
    // elimination in the compiler
    val lhs = ((left).subList(0, l))
    val rhs = ((right).subList(0, l))
    for (i in 0 until l) {
        when (val tryResult = elemChain(lhs[i], rhs[i])) { is ControlFlow.Continue<*, *> -> tryResult.cfContinueValue(); is ControlFlow.Break<*, *> -> return ControlFlow.Break<B, C>(tryResult.cfBreakValue()) }
    }
    return lenChain(left.size, right.size)
}

// This is the impl that we would like to have. Unfortunately it's not sound.
// See `partialOrdSlice.rs`.
//
// impl<A> SlicePartialOrd for A
// where
// A: Ord,
// (
// default fn partialCompare(left: &[A], right: &[A]) -> Option<Ordering> (
// Some(SliceOrd.compare(left, right))
// )
// )
//

public interface AlwaysApplicableOrd<T> : SliceOrd<T>

public interface SliceOrd<T> {
    public fun compare(left: List<T>, right: List<T>): Ordering
}

public fun <A> sliceOrdCompareA(left: List<A>, right: List<A>): Ordering {
    val elemChain =
        { a, b -> when (val matchVal = kmCompareValues(a, b).let { cmpSign -> when { cmpSign < 0 -> Ordering.Less; cmpSign > 0 -> Ordering.Greater; else -> Ordering.Equal } }) {
            Ordering.Equal -> ControlFlow.Continue(Unit)
            else -> {
                val nonEq = matchVal
                ControlFlow.Break(nonEq)
            }
        } }
    val lenChain = { a: Any?, b: Any? -> ControlFlow.Break(Int.cmp(a, b)) }
    val tupleStructVal = chainingImpl(left, right, elemChain, lenChain)
    val b = tupleStructVal.value
    return b
}

/**
 * Marks that a type should be treated as an unsigned byte for comparisons.
 *
 * # Safety
 * * The type must be readable as an `UByte`, meaning it has to have the same
 *   layout as `UByte` and always be initialized.
 * * For every `x` and `y` of this type, `Ord(x, y)` must return the same
 *   value as `Ord.cmp(reinterpret<_, UByte>(x), reinterpret<_, UByte>(y))`.
 */
public interface UnsignedBytewiseOrd

// `compareBytes` compares a sequence of unsigned bytes lexicographically, so
// use it if the requirements for `UnsignedBytewiseOrd` are fulfilled.

// Don't generate our own chaining loops for `memcmp`-able things either.

public interface SliceContains<T> {
    public fun sliceContains(x: List<T>): Boolean
}

public fun <T> T.sliceContains(x: List<T>): Boolean = x.any({ y -> y == this })

public fun <T : BytewiseEq<T>> T.sliceContains(x: List<T>, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Boolean =
    if (sizeOf<T>(typeLayoutT = typeLayoutT) == 1) {
        // SAFETY: `BytewiseEq` guarantees that values have no padding or provenance and
        // compare like their underlying bytes. Since `T` is one byte, both the value and
        // slice can be read as `UByte`s.
        val (byte, bytes) = Pair(transmuteCopy<T, UByte>(this), linearSliceView(ConstPtr((x.asPtr()).rawAddress), x.size, PrimitiveLayouts.U8))
        memchr.memchr(byte, bytes) != null
    } else {
        x.any({ y -> y == this })
    }

public fun UShort.sliceContains(arr: List<UShort>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}

public fun UInt.sliceContains(arr: List<UInt>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}

public fun ULong.sliceContains(arr: List<ULong>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}

public fun Short.sliceContains(arr: List<Short>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}

public fun Int.sliceContains(arr: List<Int>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}

public fun Long.sliceContains(arr: List<Long>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}

public fun kotlin.Float.sliceContains(arr: List<kotlin.Float>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<kotlin.Float>(typeLayoutT = PrimitiveLayouts.F32) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}

public fun Double.sliceContains(arr: List<Double>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<Double>(typeLayoutT = PrimitiveLayouts.F64) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}

public fun Char.sliceContainsChar(arr: List<Char>): Boolean {
    val LANE_COUNT: Int = 4 * (128 / (sizeOf<Char>(typeLayoutT = PrimitiveLayouts.CHAR) * 8))
    val chunks = arr.chunksExact(LANE_COUNT)
    for (chunk in chunks) {
        if (chunk.iter().fold(false, { acc, x -> acc or (x == this) })) {
            return true
        }
    }
    return chunks.remainder().iter().any({ x -> x == this })
}
