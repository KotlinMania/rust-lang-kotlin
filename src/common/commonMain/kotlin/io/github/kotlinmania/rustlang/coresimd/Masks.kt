// port-lint: source masks.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.coresimd

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.transmuteCopy
import io.github.kotlinmania.rustlang.core.num.reverseBits
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

// Types and traits associated with masking elements of vectors.
// Types representing

public interface FixEndianness<T> {
    public fun fixEndianness(): T
}

public fun UByte.fixEndianness(): UByte =
    if (false) {
        this.reverseBits()
    } else {
        this
    }

public fun UShort.fixEndianness(): UShort =
    if (false) {
        this.reverseBits()
    } else {
        this
    }

public fun UInt.fixEndianness(): UInt =
    if (false) {
        this.reverseBits()
    } else {
        this
    }

public fun ULong.fixEndianness(): ULong =
    if (false) {
        this.reverseBits()
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
    public fun <Mask> valid(values: Simd<T>, n: Int): Boolean

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
public interface MaskElement<T, out Mask, out Unsigned> : SimdElement<T>, SimdCast<Mask>

public fun privateMethodsValidByte(value: Simd<Byte>, n: Int): Boolean =
    run {
        val falses: Simd<Byte> = run { val _simdArg0 = value; val _simdArg1 = Simd.splat(((0 as Any?) as Boolean)); _simdArg0 }
        val trues: Simd<Byte> = run { val _simdArg0 = value; val _simdArg1 = Simd.splat(((-1 as Any?) as Boolean)); _simdArg0 }
        val valid: Simd<Byte> = run { val _simdArg0 = falses; val _simdArg1 = trues; _simdArg0 }
        run { val _simdArg0 = valid; false }
    }

public fun Byte.eq(other: Byte): Boolean = this == other

public fun Byte.toUsize(): Int = this.toInt()

public fun privateMethodsMaxUnsignedByte(): ULong = UByte.MAX_VALUE.toULong()

public val TRUE: Byte = (-1).toByte()

public val FALSE: Byte = 0

public fun privateMethodsValidShort(value: Simd<Short>, n: Int): Boolean =
    run {
        val falses: Simd<Short> = run { val _simdArg0 = value; val _simdArg1 = Simd.splat(((0 as Any?) as Boolean)); _simdArg0 }
        val trues: Simd<Short> = run { val _simdArg0 = value; val _simdArg1 = Simd.splat(((-1 as Any?) as Boolean)); _simdArg0 }
        val valid: Simd<Short> = run { val _simdArg0 = falses; val _simdArg1 = trues; _simdArg0 }
        run { val _simdArg0 = valid; false }
    }

public fun Short.eq(other: Short): Boolean = this == other

public fun Short.toUsize(): Int = this.toInt()

public fun privateMethodsMaxUnsignedShort(): ULong = UShort.MAX_VALUE.toULong()

public val TRUE1: Short = (-1).toShort()

public val FALSE1: Short = 0

public fun privateMethodsValidInt(value: Simd<Int>, n: Int): Boolean =
    run {
        val falses: Simd<Int> = run { val _simdArg0 = value; val _simdArg1 = Simd.splat(((0 as Any?) as Boolean)); _simdArg0 }
        val trues: Simd<Int> = run { val _simdArg0 = value; val _simdArg1 = Simd.splat(((-1 as Any?) as Boolean)); _simdArg0 }
        val valid: Simd<Int> = run { val _simdArg0 = falses; val _simdArg1 = trues; _simdArg0 }
        run { val _simdArg0 = valid; false }
    }

public fun Int.eq(other: Int): Boolean = this == other

public fun Int.toUsize(): Int = this

public fun privateMethodsMaxUnsignedInt(): ULong = UInt.MAX_VALUE.toULong()

public val TRUE2: Int = -1

public val FALSE2: Int = 0

public fun privateMethodsValidLong(value: Simd<Long>, n: Int): Boolean =
    run {
        val falses: Simd<Long> = run { val _simdArg0 = value; val _simdArg1 = Simd.splat(((0 as Any?) as Boolean)); _simdArg0 }
        val trues: Simd<Long> = run { val _simdArg0 = value; val _simdArg1 = Simd.splat(((-1 as Any?) as Boolean)); _simdArg0 }
        val valid: Simd<Long> = run { val _simdArg0 = falses; val _simdArg1 = trues; _simdArg0 }
        run { val _simdArg0 = valid; false }
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
    public val fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout(),
) {
    public var field0: Simd<T>
        get() = value
        set(v) {
            value = v
        }

    public var value: Simd<T>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Simd<T>, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Mask<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Mask<T>(ptr, layout) }, { it.value })

    public companion object {
        public operator fun <T> invoke(value: Simd<T>, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout()): Mask<T> = Mask<T>(value, fieldLayout)

        public fun <T> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout()): Mask<T> = Mask<T>(fieldStorage, fieldLayout)
    }
}

public fun <Mask, Unsigned, T> Mask<T>.clone(n: Int): Mask<T> = Mask(this.value)

/**
 * Constructs a mask by setting all elements to the given value.
 */
public fun <Mask, Unsigned, T> splat(value: Boolean, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout(), n: Int): Mask<T> =
    Mask(Simd.splat(if (value) {
        T.TRUE
    } else {
        T.FALSE
    }), fieldLayout = fieldLayout)

/**
 * Converts an array of bools to a SIMD mask.
 */
public fun <Mask, Unsigned, T> fromArray(array: BooleanArray, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout(), n: Int): Mask<T> {
    // SAFETY: Rust's bool has a layout of 1 byte (u8) with a value of
    // true:    0b00000001
    // false:   0b00000000
    // Thus, an array of bools is also a valid array of bytes: [u8; N]
    // This would be hypothetically valid as an "in-place" reinterpret,
    // but these are "dependently-sized" types, so copy elision it is!
    return run {
        val bytes: UByteArray = transmuteCopy(array)
        val bools: Simd<Byte> = run { val _simdArg0 = Simd.fromArray(bytes); val _simdArg1 = Simd.splat(0u.toUByte()); _simdArg0 }
        Mask.fromSimdUnchecked(run { val _simdArg0 = bools; _simdArg0 }, fieldLayout = fieldLayout)
    }
}

/**
 * Converts a SIMD mask to an array of bools.
 */
public fun <Mask, Unsigned, T> Mask<T>.toArray(n: Int): BooleanArray {
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
        var bytes: Simd<Byte> = run { val _simdArg0 = this.toSimd(); _simdArg0 }
        bytes = bytes and Simd.splat(1)
        transmuteCopy(bytes)
    }
}

/**
 * Converts a vector of integers to a mask, where 0 represents `false` and -1
 * represents `true`.
 *
 * # Safety
 * All elements must be either 0 or -1.
 */
public fun <Mask, Unsigned, T> fromSimdUnchecked(value: Simd<T>, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout(), n: Int, validTFn: (Simd<T>) -> Boolean): Mask<T> {
    // Safety: the caller must confirm this invariant
    run {}
    return Mask(value, fieldLayout = fieldLayout)
}

/**
 * Converts a vector of integers to a mask, where 0 represents `false` and -1
 * represents `true`.
 *
 * # Panics
 * Panics if any element is not 0 or -1.
 */
public fun <Mask, Unsigned, T> fromSimd(value: Simd<T>, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout(), n: Int): Mask<T> {
    check(T.valid(value)) { "all values must be either 0 or -1" }
    // Safety: the validity has been checked
    return Mask.fromSimdUnchecked(value, fieldLayout = fieldLayout)
}

/**
 * Converts the mask to a vector of integers, where 0 represents `false` and -1
 * represents `true`.
 */
public fun <Mask, Unsigned, T> Mask<T>.toSimd(n: Int): Simd<T> = this.value

/**
 * Converts the mask to a mask of any other element size.
 */
public fun <Mask, Unsigned, T, U> Mask<T>.cast(n: Int): Mask<U> {
    // Safety: mask elements are integers
    return Mask(run { val _simdArg0 = this.value; _simdArg0 }, fieldLayout = this.fieldLayout)
}

/**
 * Tests the value of the specified element.
 *
 * # Safety
 * `index` must be less than `self.len()`.
 */
public fun <Mask, Unsigned, T> Mask<T>.testUnchecked(index: Int, n: Int, eqTFn: (T, T) -> Boolean): Boolean {
    // Safety: the caller must confirm this invariant
    return eqTFn(this.value.asArray().getUnchecked(index), T.TRUE)
}

/**
 * Tests the value of the specified element.
 *
 * # Panics
 * Panics if `index` is greater than or equal to the number of elements in the vector.
 */
public fun <Mask, Unsigned, T> Mask<T>.test(index: Int, n: Int, eqTFn: (T, T) -> Boolean): Boolean = eqTFn(this.value[index], T.TRUE)

/**
 * Sets the value of the specified element.
 *
 * # Safety
 * `index` must be less than `self.len()`.
 */
public fun <Mask, Unsigned, T> Mask<T>.setUnchecked(index: Int, value: Boolean, n: Int) {
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
public fun <Mask, Unsigned, T> Mask<T>.set(index: Int, value: Boolean, n: Int) {
    this.value[index] = if (value) {
        T.TRUE
    } else {
        T.FALSE
    }
}

/**
 * Returns true if any element is set, or false otherwise.
 */
public fun <Mask, Unsigned, T> Mask<T>.any(n: Int): Boolean {
    // Safety: `self` is a mask vector
    return run { val _simdArg0 = this.value; false }
}

/**
 * Returns true if all elements are set, or false otherwise.
 */
public fun <Mask, Unsigned, T> Mask<T>.all(n: Int): Boolean {
    // Safety: `self` is a mask vector
    return run { val _simdArg0 = this.value; false }
}

/**
 * Creates a bitmask from a mask.
 *
 * Each bit is set if the corresponding element in the mask is `true`.
 */
public fun <Mask, Unsigned, T> Mask<T>.toBitmask(n: Int): ULong {
    fun <Mask, Unsigned, T, U : FixEndianness<U>> toBitmaskImpl(mask: Mask<T>, m: Int, n: Int): U {
        val resized = mask.resize(false)
        // Safety: `resized` is an integer vector with length M, which must match T
        val bitmask: U = run { val _simdArg0 = resized.first; _simdArg0 }
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
public fun <Mask, Unsigned, T> fromBitmask(bitmask: ULong, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout(), n: Int): Mask<T> = Mask(bitmask.select(Simd.splat(T.TRUE), Simd.splat(T.FALSE)), fieldLayout = fieldLayout)

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
public fun <Mask, Unsigned, T> Mask<T>.firstSet(n: Int): Int? {
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
            run { val _simdArg0 = Simd.fromArray(index); _simdArg0 }
        }
    // Safety: the input and output are integer vectors
    val maskedIndex: Simd<T> = run { val _simdArg0 = (!this).toSimd(); val _simdArg1 = index1; _simdArg0 }
    // Safety: the input and output are integer vectors
    val maskedIndex1: Simd<Unsigned> = run { val _simdArg0 = maskedIndex; _simdArg0 }
    // Safety: the input is an integer vector
    val minIndex: Unsigned = run { val _simdArg0 = maskedIndex1; _simdArg0 }
    // Safety: the return value is the unsigned version of T
    val minIndex1: T = transmuteCopy(minIndex)
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
public fun <Mask, Unsigned, T> Mask<T>.lastSet(n: Int): Int? {
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
            run { val _simdArg0 = Simd.fromArray(index); _simdArg0 }
        }
    // Safety: the input and output are integer vectors
    val maskedIndex: Simd<T> = run { val _simdArg0 = (!this).toSimd(); val _simdArg1 = index1; _simdArg0 }
    // Safety: the input is an integer vector
    val maxIndex: T = run { val _simdArg0 = maskedIndex; _simdArg0 }
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
public fun <Mask, Unsigned, T> fromMaskTN(array: BooleanArray, fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout(), n: Int): Mask<T> = Mask.fromArray(array, fieldLayout = fieldLayout)

public fun <Mask, Unsigned, T> fromBooleanArrayn(vector: Mask<T>, n: Int): BooleanArray = vector.toArray()

public fun <Mask, Unsigned, T> defaultMaskTN(fieldLayout: TypeLayout<Simd<T>> = UnresolvedTypeLayout(), n: Int): Mask<T> = Mask.splat(false, fieldLayout = fieldLayout)

public fun <Mask, Unsigned, T> Mask<T>.eq(other: Mask<T>, n: Int): Boolean = this.value == other.value

public fun <Mask, Unsigned, T> Mask<T>.partialCmp(other: Mask<T>, n: Int): Ordering? = this.value.partialCmp(other.value)

public fun <Mask, Unsigned, T> Mask<T>.fmt(f: StringBuilder, n: Int): Result<Unit, Error> = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries((0 until n).map({ i -> this.test(i) })).finish()

public fun <Mask, Unsigned, T> Mask<T>.bitand(rhs: Mask<T>, n: Int): Mask<T> {
    // Safety: `self` is an integer vector
    return Mask(run { val _simdArg0 = this.value; val _simdArg1 = rhs.value; _simdArg0 }, fieldLayout = this.fieldLayout)
}

public fun <Mask, Unsigned, T> Mask<T>.bitand(rhs: Boolean, n: Int): Mask<T> = this and Mask.splat(rhs, fieldLayout = this.fieldLayout)

public fun <Mask, Unsigned, T> Boolean.bitand(rhs: Mask<T>, n: Int): Mask<T> = Mask.splat(this, fieldLayout = UnresolvedTypeLayout<Simd<T>>()) and rhs

public fun <Mask, Unsigned, T> Mask<T>.bitor(rhs: Mask<T>, n: Int): Mask<T> {
    // Safety: `self` is an integer vector
    return Mask(run { val _simdArg0 = this.value; val _simdArg1 = rhs.value; _simdArg0 }, fieldLayout = this.fieldLayout)
}

public fun <Mask, Unsigned, T> Mask<T>.bitor(rhs: Boolean, n: Int): Mask<T> = this or Mask.splat(rhs, fieldLayout = this.fieldLayout)

public fun <Mask, Unsigned, T> Boolean.bitor(rhs: Mask<T>, n: Int): Mask<T> = Mask.splat(this, fieldLayout = UnresolvedTypeLayout<Simd<T>>()) or rhs

public fun <Mask, Unsigned, T> Mask<T>.bitxor(rhs: Mask<T>, n: Int): Mask<T> {
    // Safety: `self` is an integer vector
    return Mask(run { val _simdArg0 = this.value; val _simdArg1 = rhs.value; _simdArg0 }, fieldLayout = this.fieldLayout)
}

public fun <Mask, Unsigned, T> Mask<T>.bitxor(rhs: Boolean, n: Int): Mask<T> = this xor Mask.splat(rhs, fieldLayout = this.fieldLayout)

public fun <Mask, Unsigned, T> Boolean.bitxor(rhs: Mask<T>, n: Int): Mask<T> = Mask.splat(this, fieldLayout = UnresolvedTypeLayout<Simd<T>>()) xor rhs

public fun <Mask, Unsigned, T> Mask<T>.not(n: Int): Mask<T> = Mask.splat(true, fieldLayout = this.fieldLayout) xor this

public fun <Mask, Unsigned, T> Mask<T>.bitandAssign(rhs: Mask<T>, n: Int) {
    this = this and rhs
}

public fun <Mask, Unsigned, T> Mask<T>.bitandAssign(rhs: Boolean, n: Int) {
    this = this and Mask.splat(rhs, fieldLayout = this.fieldLayout)
}

public fun <Mask, Unsigned, T> Mask<T>.bitorAssign(rhs: Mask<T>, n: Int) {
    this = this or rhs
}

public fun <Mask, Unsigned, T> Mask<T>.bitorAssign(rhs: Boolean, n: Int) {
    this = this or Mask.splat(rhs, fieldLayout = this.fieldLayout)
}

public fun <Mask, Unsigned, T> Mask<T>.bitxorAssign(rhs: Mask<T>, n: Int) {
    this = this xor rhs
}

public fun <Mask, Unsigned, T> Mask<T>.bitxorAssign(rhs: Boolean, n: Int) {
    this = this xor Mask.splat(rhs, fieldLayout = this.fieldLayout)
}

public fun fromMaskShortN(value: Mask<Byte>, n: Int): Mask<Short> = value.cast()

public fun fromMaskIntN(value: Mask<Byte>, n: Int): Mask<Int> = value.cast()

public fun fromMaskLongN(value: Mask<Byte>, n: Int): Mask<Long> = value.cast()

public fun fromMaskByteN(value: Mask<Short>, n: Int): Mask<Byte> = value.cast()
