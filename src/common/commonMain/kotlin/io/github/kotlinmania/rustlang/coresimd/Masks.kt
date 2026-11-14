// port-lint: source masks.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.coresimd

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.num.rustReverseBits
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.coresimd.simd.SimdCast
import io.github.kotlinmania.rustlang.coresimd.simd.SimdElement

// Types and traits associated with masking elements of vectors.
// Types representing

internal interface FixEndianness<T> {
    public fun fixEndianness(): T
}

public fun UByte.fixEndianness(): UByte =
    if (false) {
        rustReverseBits(this)
    } else {
        this
    }

public fun UShort.fixEndianness(): UShort =
    if (false) {
        rustReverseBits(this)
    } else {
        this
    }

public fun UInt.fixEndianness(): UInt =
    if (false) {
        rustReverseBits(this)
    } else {
        this
    }

public fun ULong.fixEndianness(): ULong =
    if (false) {
        rustReverseBits(this)
    } else {
        this
    }

/**
 * Not only does this seal the `MaskElement` trait, but these functions prevent other traits
 * from bleeding into the parent bounds.
 *
 * For example, `eq` could be provided by requiring `MaskElement: PartialEq`, but that would
 * prevent us from ever removing that bound, or from implementing `MaskElement` on
 * non-`PartialEq` types in the future.
 */
public interface PrivateMethods<T, out Unsigned> {
    public fun <Mask> valid(values: Simd<T, N>, n: Int): Boolean

    public fun eq(other: T): Boolean

    public fun toUsize(): Int

    public fun maxUnsigned(): ULong

    public val TRUE: T

    public val FALSE: T
}

/**
 * Marker trait for types that may be used as SIMD mask elements.
 *
 * # Safety
 * Type must be a signed integer.
 */
public interface MaskElement<T> : SimdElement<T>, SimdCast

public fun privateMethodsValidByte(value: Simd<Byte, N>, n: Int): Boolean =
    run {
        val falses: Simd<Byte> = Simd.simdEq(value, Simd.splat(((0 as Any?) as Boolean)))
        val trues: Simd<Byte> = Simd.simdEq(value, Simd.splat(((-1 as Any?) as Boolean)))
        val valid: Simd<Byte> = Simd.simdOr(falses, trues)
        Simd.simdReduceAll(valid)
    }

public fun Byte.eq(other: Byte): Boolean = this == other

public fun Byte.toUsize(): Int = this.toInt()

public fun privateMethodsMaxUnsignedByte(): ULong = UByte.MAX_VALUE.toULong()

public val TRUE: Byte = (-1).toByte()

public val FALSE: Byte = 0

public fun privateMethodsValidShort(value: Simd<Short, N>, n: Int): Boolean =
    run {
        val falses: Simd<Short> = Simd.simdEq(value, Simd.splat(((0 as Any?) as Boolean)))
        val trues: Simd<Short> = Simd.simdEq(value, Simd.splat(((-1 as Any?) as Boolean)))
        val valid: Simd<Short> = Simd.simdOr(falses, trues)
        Simd.simdReduceAll(valid)
    }

public fun Short.eq(other: Short): Boolean = this == other

public fun Short.toUsize(): Int = this.toInt()

public fun privateMethodsMaxUnsignedShort(): ULong = UShort.MAX_VALUE.toULong()

public val TRUE1: Short = (-1).toShort()

public val FALSE1: Short = 0

public fun privateMethodsValidInt(value: Simd<Int, N>, n: Int): Boolean =
    run {
        val falses: Simd<Int> = Simd.simdEq(value, Simd.splat(((0 as Any?) as Boolean)))
        val trues: Simd<Int> = Simd.simdEq(value, Simd.splat(((-1 as Any?) as Boolean)))
        val valid: Simd<Int> = Simd.simdOr(falses, trues)
        Simd.simdReduceAll(valid)
    }

public fun Int.eq(other: Int): Boolean = this == other

public fun Int.toUsize(): Int = this

public fun privateMethodsMaxUnsignedInt(): ULong = UInt.MAX_VALUE.toULong()

public val TRUE2: Int = -1

public val FALSE2: Int = 0

public fun privateMethodsValidLong(value: Simd<Long, N>, n: Int): Boolean =
    run {
        val falses: Simd<Long> = Simd.simdEq(value, Simd.splat(((0 as Any?) as Boolean)))
        val trues: Simd<Long> = Simd.simdEq(value, Simd.splat(((-1 as Any?) as Boolean)))
        val valid: Simd<Long> = Simd.simdOr(falses, trues)
        Simd.simdReduceAll(valid)
    }

public fun Long.eq(other: Long): Boolean = this == other

public fun Long.toUsize(): Int = this.toInt()

public fun privateMethodsMaxUnsignedLong(): ULong = ULong.MAX_VALUE

public val TRUE3: Long = -1L

public val FALSE3: Long = 0L

public val TRUE4: Int = -1

public val FALSE4: Int = 0

/**
 * A SIMD vector mask for `N` elements of width specified by `Element`.
 *
 * Masks represent boolean inclusion/exclusion on a per-element basis.
 *
 * The layout of this type is unspecified, and may change between platforms
 * and/or Rust versions, and code should not assume that it is equivalent to
 * `[T; N]`.
 *
 * `N` cannot be 0 and may be at most 64. This limit may be increased in
 * the future.
 */
public open class Mask<T> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Simd<T, N>>,
) where T : MaskElement<T> {
    public var field0: Simd<T, N>
        get() = value
        set(v) {
            value = v
        }

    public var value: Simd<T, N>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Simd<T, N>, fieldLayout: TypeLayout<Simd<T, N>>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Mask<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Mask<T>(ptr, layout) }, { it.value })

    public companion object {
        public operator fun <T> invoke(value: Simd<T, N>, fieldLayout: TypeLayout<Simd<T, N>>): Mask<T> = Mask<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Simd<T, N>>): Mask<T> = Mask<T>(fieldStorage, fieldLayout)
    }
}

public fun <T> Mask<T, N>.clone(n: Int): Mask<T, N> where T : MaskElement<T> = Mask(this.value)

/**
 * Constructs a mask by setting all elements to the given value.
 */
public fun <T> Mask.Companion.splat(value: Boolean, fieldLayout: TypeLayout<Simd<T, N>>, n: Int): Mask<T, N> where T : MaskElement<T> =
    Mask(Simd.splat(if (value) {
        T.TRUE
    } else {
        T.FALSE
    }), fieldLayout = ObjectSlotLayout<Simd<T>>())

/**
 * Converts an array of bools to a SIMD mask.
 */
public fun <T> Mask.Companion.fromArray(array: BooleanArray, fieldLayout: TypeLayout<Simd<T, N>>, n: Int): Mask<T, N> where T : MaskElement<T> {
    // SAFETY: Rust's bool has a layout of 1 byte (u8) with a value of
    // true:    0b00000001
    // false:   0b00000000
    // Thus, an array of bools is also a valid array of bytes: [u8; N]
    // This would be hypothetically valid as an "in-place" reinterpret,
    // but these are "dependently-sized" types, so copy elision it is!
    return run {
        val bytes: UByteArray = mem.transmuteCopy(array)
        val bools: Simd<Byte> = Simd.simdNe(Simd.fromArray(bytes), Simd.splat(0u.toUByte()))
        Mask.fromSimdUnchecked(Simd.simdCast(bools), fieldLayout = ObjectSlotLayout<Simd<T>>())
    }
}

/**
 * Converts a SIMD mask to an array of bools.
 */
public fun <T> Mask<T, N>.toArray(n: Int): BooleanArray where T : MaskElement<T> {
    // This follows mostly the same logic as fromArray.
    // SAFETY: Rust's bool has a layout of 1 byte (u8) with a value of
    // true:    0b00000001
    // false:   0b00000000
    // Thus, an array of bools is also a valid array of bytes: [u8; N]
    // Since our masks are equal to integers where all bits are set,
    // we can simply convert them to i8s, and then bitand them by the
    // bitpattern for Rust's "true" bool.
    // This would be hypothetically valid as an "in-place" reinterpret,
    // but these are "dependently-sized" types, so copy elision it is!
    return run {
        var bytes: Simd<Byte> = Simd.simdCast(this.toSimd())
        bytes = bytes and Simd.splat(1)
        mem.transmuteCopy(bytes)
    }
}

/**
 * Converts a vector of integers to a mask, where 0 represents `false` and -1
 * represents `true`.
 *
 * # Safety
 * All elements must be either 0 or -1.
 */
public fun <T> Mask.Companion.fromSimdUnchecked(value: Simd<T, N>, fieldLayout: TypeLayout<Simd<T, N>>, n: Int, validTFn: (Simd<T, N>) -> Boolean): Mask<T, N> where T : MaskElement<T> {
    // Safety: the caller must confirm this invariant
    run {}
    return Mask(value, fieldLayout = ObjectSlotLayout<Simd<T>>())
}

/**
 * Converts a vector of integers to a mask, where 0 represents `false` and -1
 * represents `true`.
 *
 * # Panics
 * Panics if any element is not 0 or -1.
 */
public fun <T> Mask.Companion.fromSimd(value: Simd<T, N>, fieldLayout: TypeLayout<Simd<T, N>>, n: Int): Mask<T, N> where T : MaskElement<T> {
    check(T.valid(value)) { "all values must be either 0 or -1" }
    // Safety: the validity has been checked
    return Mask.fromSimdUnchecked(value, fieldLayout = ObjectSlotLayout<Simd<T>>())
}

/**
 * Converts the mask to a vector of integers, where 0 represents `false` and -1
 * represents `true`.
 */
public fun <T> Mask<T, N>.toSimd(n: Int): Simd<T> where T : MaskElement<T> = this.value

/**
 * Converts the mask to a mask of any other element size.
 */
public fun <T, U : MaskElement<U>> Mask<T, N>.cast(n: Int): Mask<U> where T : MaskElement<T> {
    // Safety: mask elements are integers
    return Mask(Simd.simdAs(this.value), fieldLayout = this.fieldLayout)
}

/**
 * Tests the value of the specified element.
 *
 * # Safety
 * `index` must be less than `self.len()`.
 */
public fun <T> Mask<T, N>.testUnchecked(index: Int, n: Int, eqTFn: (T, T) -> Boolean): Boolean where T : MaskElement<T> {
    // Safety: the caller must confirm this invariant
    return eqTFn(this.value.asArray().getUnchecked(index), T.TRUE)
}

/**
 * Tests the value of the specified element.
 *
 * # Panics
 * Panics if `index` is greater than or equal to the number of elements in the vector.
 */
public fun <T> Mask<T, N>.test(index: Int, n: Int, eqTFn: (T, T) -> Boolean): Boolean where T : MaskElement<T> = eqTFn(this.value[index], T.TRUE)

/**
 * Sets the value of the specified element.
 *
 * # Safety
 * `index` must be less than `self.len()`.
 */
public fun <T> Mask<T, N>.setUnchecked(index: Int, value: Boolean, n: Int) where T : MaskElement<T> {
    // Safety: the caller must confirm this invariant
    this.value.asMutArray().getUncheckedMut(index) = if (value) {
        T.TRUE
    } else {
        T.FALSE
    }
}

/**
 * Sets the value of the specified element.
 *
 * # Panics
 * Panics if `index` is greater than or equal to the number of elements in the vector.
 */
public fun <T> Mask<T, N>.set(index: Int, value: Boolean, n: Int) where T : MaskElement<T> {
    this.value[index] = if (value) {
        T.TRUE
    } else {
        T.FALSE
    }
}

/**
 * Returns true if any element is set, or false otherwise.
 */
public fun <T> Mask<T, N>.any(n: Int): Boolean where T : MaskElement<T> {
    // Safety: `self` is a mask vector
    return Simd.simdReduceAny(this.value)
}

/**
 * Returns true if all elements are set, or false otherwise.
 */
public fun <T> Mask<T, N>.all(n: Int): Boolean where T : MaskElement<T> {
    // Safety: `self` is a mask vector
    return Simd.simdReduceAll(this.value)
}

/**
 * Creates a bitmask from a mask.
 *
 * Each bit is set if the corresponding element in the mask is `true`.
 */
public fun <T> Mask<T, N>.toBitmask(n: Int): ULong where T : MaskElement<T> {
    fun <T, U : FixEndianness<U>> toBitmaskImpl(mask: Mask<T>, m: Int, n: Int): U where T : MaskElement<T> {
        val resized = mask.resize(false)
        // Safety: `resized` is an integer vector with length M, which must match T
        val bitmask: U = Simd.simdBitmask(resized.first)
        // LLVM assumes bit order should match endianness
        return bitmask.fixEndianness()
    }
    run {
        check(n <= 64) { "number of elements can't be greater than 64" }
    }
    // Note modify simdBitmask to zero-extend output, making this unnecessary
    return if (n <= 8) {
        // Safety: bitmask matches length
        (toBitmaskImpl<T, UByte>(this, 8, n)).toULong()
    } else if (n <= 16) {
        // Safety: bitmask matches length
        (toBitmaskImpl<T, UShort>(this, 16, n)).toULong()
    } else if (n <= 32) {
        // Safety: bitmask matches length
        (toBitmaskImpl<T, UInt>(this, 32, n)).toULong()
    } else {
        // Safety: bitmask matches length
        toBitmaskImpl<T, ULong>(this, 64uL, n)
    }
}

/**
 * Creates a mask from a bitmask.
 *
 * For each bit, if it is set, the corresponding element in the mask is set to `true`.
 * If the mask contains more than 64 elements, the remainder are set to `false`.
 */
public fun <T> Mask.Companion.fromBitmask(bitmask: ULong, fieldLayout: TypeLayout<Simd<T, N>>, n: Int): Mask<T, N> where T : MaskElement<T> = Mask(bitmask.select(Simd.splat(T.TRUE), Simd.splat(T.FALSE)), fieldLayout = ObjectSlotLayout<Simd<T>>())

/**
 * Finds the index of the first set element.
 *
 * ```kotlin
 * check(splat(false).firstSet() == null)
 * check(splat(true).firstSet() == 0)
 * val mask = fromArray(booleanArrayOf(false, true, false, false, true, false, false, true))
 * check(mask.firstSet() == 1)
 * ```
 */
public fun <T> Mask<T, N>.firstSet(n: Int): Int? where T : MaskElement<T> {
    // If bitmasks are efficient, using them is better
    if (false && n <= 64) {
        return this.toBitmask().lowestOne().map({ i -> i.toInt() })
    }
    // To find the first set index:
    // * create a vector 0..N
    // * replace unset mask elements in that vector with -1
    // * perform _unsigned_ reduce-min
    // * check if the result is -1 or an index
    val index1: Simd<T> =
        run {
            val index = MutableList(n) { 0 }
            var i = 0
            while (i < n) {
                index[i] = i
                i += 1
            }
            // Safety: the input and output are integer vectors
            Simd.simdCast(Simd.fromArray(index))
        }
    // Safety: the input and output are integer vectors
    val maskedIndex: Simd<T> = Simd.simdOr((!this).toSimd(), index1)
    // Safety: the input and output are integer vectors
    val maskedIndex1: Simd<Unsigned> = Simd.simdCast(maskedIndex)
    // Safety: the input is an integer vector
    val minIndex: Unsigned = Simd.simdReduceMin(maskedIndex1)
    // Safety: the return value is the unsigned version of T
    val minIndex1: T = mem.transmuteCopy(minIndex)
    return if (minIndex1.eq(T.TRUE)) {
        null
    } else {
        val minIndex2 = minIndex1.toUsize()
        // Allow eliminating bounds checks when using the index
        // Safety: the index can't exceed the number of elements in the vector
        run {
            assertUnchecked(minIndex2 < n)
        }
        minIndex2
    }
}

/**
 * Finds the index of the last set element.
 *
 * ```kotlin
 * check(splat(false).lastSet() == null)
 * check(splat(true).lastSet() == 7)
 * val mask = fromArray(booleanArrayOf(false, true, false, false, true, false, true, false))
 * check(mask.lastSet() == 6)
 * ```
 */
public fun <T> Mask<T, N>.lastSet(n: Int): Int? where T : MaskElement<T> {
    // If bitmasks are efficient, using them is better
    if (false && n <= 64) {
        return this.toBitmask().highestOne().map({ i -> i.toInt() })
    }
    // To find the first set index:
    // * create a vector 0..N
    // * replace unset mask elements in that vector with -1
    // * perform _signed_ reduce-max
    // * check if the result is -1 or an index
    val index1: Simd<T> =
        run {
            val index = MutableList(n) { 0 }
            var i = 0
            while (i < n) {
                index[i] = i
                i += 1
            }
            // Safety: the input and output are integer vectors
            Simd.simdCast(Simd.fromArray(index))
        }
    // Safety: the input and output are integer vectors
    val maskedIndex: Simd<T> = Simd.simdOr((!this).toSimd(), index1)
    // Safety: the input is an integer vector
    val maxIndex: T = Simd.simdReduceMax(maskedIndex)
    return if (maxIndex.eq(T.TRUE)) {
        null
    } else {
        val maxIndex1 = maxIndex.toUsize()
        // Allow eliminating bounds checks when using the index
        // Safety: the index can't exceed the number of elements in the vector
        run {
            assertUnchecked(maxIndex1 < n)
        }
        maxIndex1
    }
}

// vector/array conversion
public fun <T> fromMaskTN(array: BooleanArray, fieldLayout: TypeLayout<Simd<T, N>>, n: Int): Mask<T, N> where T : MaskElement<T> = Mask.fromArray(array, fieldLayout = ObjectSlotLayout<Simd<T>>())

public fun <T> fromBooleanArrayn(vector: Mask<T, N>, n: Int): BooleanArray where T : MaskElement<T> = vector.toArray()

public fun <T> defaultMaskTN(fieldLayout: TypeLayout<Simd<T, N>>, n: Int): Mask<T, N> where T : MaskElement<T> = Mask.splat(false, fieldLayout = ObjectSlotLayout<Simd<T>>())

public fun <T> Mask<T, N>.eq(other: Mask<T, N>, n: Int): Boolean where T : MaskElement<T> = this.value == other.value

public fun <T> Mask<T, N>.partialCmp(other: Mask<T, N>, n: Int): Ordering? where T : MaskElement<T> = this.value.partialCmp(other.value)

public fun <T> Mask<T, N>.fmt(f: StringBuilder, n: Int) where T : MaskElement<T> {
    object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries((0 until n).map({ i -> this.test(i) })).finish()
}

public fun <T> Mask<T, N>.bitand(rhs: Mask<T, N>, n: Int): Mask<T, N> where T : MaskElement<T> {
    // Safety: `self` is an integer vector
    return Mask(Simd.simdAnd(this.value, rhs.value), fieldLayout = this.fieldLayout)
}

public fun <T> Mask<T, N>.bitand(rhs: Boolean, n: Int): Mask<T, N> where T : MaskElement<T> = this and Mask.splat(rhs, fieldLayout = this.fieldLayout)

public fun <T> Boolean.bitand(rhs: Mask<T, N>, n: Int): Mask<T> where T : MaskElement<T> = Mask.splat(this, fieldLayout = ObjectSlotLayout<Simd<T>>()) and rhs

public fun <T> Mask<T, N>.bitor(rhs: Mask<T, N>, n: Int): Mask<T, N> where T : MaskElement<T> {
    // Safety: `self` is an integer vector
    return Mask(Simd.simdOr(this.value, rhs.value), fieldLayout = this.fieldLayout)
}

public fun <T> Mask<T, N>.bitor(rhs: Boolean, n: Int): Mask<T, N> where T : MaskElement<T> = this or Mask.splat(rhs, fieldLayout = this.fieldLayout)

public fun <T> Boolean.bitor(rhs: Mask<T, N>, n: Int): Mask<T> where T : MaskElement<T> = Mask.splat(this, fieldLayout = ObjectSlotLayout<Simd<T>>()) or rhs

public fun <T> Mask<T, N>.bitxor(rhs: Mask<T, N>, n: Int): Mask<T, N> where T : MaskElement<T> {
    // Safety: `self` is an integer vector
    return Mask(Simd.simdXor(this.value, rhs.value), fieldLayout = this.fieldLayout)
}

public fun <T> Mask<T, N>.bitxor(rhs: Boolean, n: Int): Mask<T, N> where T : MaskElement<T> = this xor Mask.splat(rhs, fieldLayout = this.fieldLayout)

public fun <T> Boolean.bitxor(rhs: Mask<T, N>, n: Int): Mask<T> where T : MaskElement<T> = Mask.splat(this, fieldLayout = ObjectSlotLayout<Simd<T>>()) xor rhs

public fun <T> Mask<T, N>.not(n: Int): Mask<T> where T : MaskElement<T> = Mask.splat(true, fieldLayout = this.fieldLayout) xor this

public fun <T> Mask<T, N>.bitandAssign(rhs: Mask<T, N>, n: Int) where T : MaskElement<T> {
    this = this and rhs
}

public fun <T> Mask<T, N>.bitandAssign(rhs: Boolean, n: Int) where T : MaskElement<T> {
    this = this and Mask.splat(rhs, fieldLayout = this.fieldLayout)
}

public fun <T> Mask<T, N>.bitorAssign(rhs: Mask<T, N>, n: Int) where T : MaskElement<T> {
    this = this or rhs
}

public fun <T> Mask<T, N>.bitorAssign(rhs: Boolean, n: Int) where T : MaskElement<T> {
    this = this or Mask.splat(rhs, fieldLayout = this.fieldLayout)
}

public fun <T> Mask<T, N>.bitxorAssign(rhs: Mask<T, N>, n: Int) where T : MaskElement<T> {
    this = this xor rhs
}

public fun <T> Mask<T, N>.bitxorAssign(rhs: Boolean, n: Int) where T : MaskElement<T> {
    this = this xor Mask.splat(rhs, fieldLayout = this.fieldLayout)
}

public fun fromMaskShortN(value: Mask<Byte, N>, n: Int): Mask<Short, N> = value.cast()

public fun fromMaskIntN(value: Mask<Byte, N>, n: Int): Mask<Int, N> = value.cast()

public fun fromMaskLongN(value: Mask<Byte, N>, n: Int): Mask<Long, N> = value.cast()

public fun fromMaskByteN(value: Mask<Short, N>, n: Int): Mask<Byte, N> = value.cast()
