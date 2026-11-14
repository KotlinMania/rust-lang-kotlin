// port-lint: source num/nonzero.rs
package io.github.kotlinmania.rustlang.core.num.nonzero

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.clone.TrivialClone
import io.github.kotlinmania.rustlang.core.clone.UseCloned
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.fmt.LowerExp
import io.github.kotlinmania.rustlang.core.fmt.UpperExp
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtlzU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCtpopU64
import io.github.kotlinmania.rustlang.core.intrinsics.kmCttzU64
import io.github.kotlinmania.rustlang.core.intrinsics.transmuteUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.ubChecks
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedDiv
import io.github.kotlinmania.rustlang.core.intrinsics.uncheckedRem
import io.github.kotlinmania.rustlang.core.marker.Freeze
import io.github.kotlinmania.rustlang.core.marker.StructuralPartialEq
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.error.IntErrorKind
import io.github.kotlinmania.rustlang.core.num.error.ParseIntError
import io.github.kotlinmania.rustlang.core.num.error.TryFromIntError
import io.github.kotlinmania.rustlang.core.num.imp.intlog10.u8
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroCharInner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI128Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI16Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI32Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI64Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI8Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroIsizeInner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU128Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU16Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU32Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU64Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU8Inner
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.rustCheckedAbs
import io.github.kotlinmania.rustlang.core.num.rustCheckedAdd
import io.github.kotlinmania.rustlang.core.num.rustCheckedMul
import io.github.kotlinmania.rustlang.core.num.rustCheckedNeg
import io.github.kotlinmania.rustlang.core.num.rustCheckedPow
import io.github.kotlinmania.rustlang.core.num.rustCountOnes
import io.github.kotlinmania.rustlang.core.num.rustOverflowingAbs
import io.github.kotlinmania.rustlang.core.num.rustOverflowingNeg
import io.github.kotlinmania.rustlang.core.num.rustReverseBits
import io.github.kotlinmania.rustlang.core.num.rustRotateLeft
import io.github.kotlinmania.rustlang.core.num.rustRotateRight
import io.github.kotlinmania.rustlang.core.num.rustSaturatingAbs
import io.github.kotlinmania.rustlang.core.num.rustSaturatingAdd
import io.github.kotlinmania.rustlang.core.num.rustSaturatingMul
import io.github.kotlinmania.rustlang.core.num.rustWrappingAbs
import io.github.kotlinmania.rustlang.core.num.rustWrappingNeg
import io.github.kotlinmania.rustlang.core.ops.bit.BitOr
import io.github.kotlinmania.rustlang.core.ops.bit.BitOrAssign
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.fromRef
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.core.ubchecks.checkLanguageUb

// Definitions of integer that is known not to equal zero.

/**
 * A marker trait for primitive types which can be zero.
 *
 * This is an implementation detail for <code>[NonZero]\<T></code> which may disappear or be replaced at any time.
 *
 * # Safety
 *
 * Types implementing this trait must be primitives that are valid when zeroed.
 *
 * The associated `Self.NonZeroInner` type must have the same size+align as `Self`,
 * but with a niche and bit validity making it so the following `transmutes` are sound:
 *
 * - `Self.NonZeroInner` to `Option<Self.NonZeroInner>`
 * - `Option<Self.NonZeroInner>` to `Self`
 *
 * (And, consequently, `Self.NonZeroInner` to `Self`.)
 */
public interface ZeroablePrimitive<T, out NonZeroInner>

// Implement auto-traits manually based on `T` to avoid docs exposing
// the `ZeroablePrimitive.NonZeroInner` implementation detail.

/**
 * A value that is known not to equal zero.
 *
 * This enables some memory layout optimization.
 * For example, `Option<NonZero<UInt>>` is the same size as `UInt`:
 *
 * ```kotlin
 * import core.num.NonZero
 * import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 *
 * check(sizeOf<NonZero<UInt>?>(typeLayoutT = ObjectSlotLayout<NonZero<UInt>?>()) == sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32))
 * ```
 *
 * # Layout
 *
 * `NonZero<T>` is guaranteed to have the same layout and bit validity as `T`
 * with the exception that the all-zero bit pattern is invalid.
 * `Option<NonZero<T>>` is guaranteed to be ABI-compatible with `T`, including in
 * FFI.
 *
 * Thanks to the [null pointer optimization], `NonZero<T>` and
 * `Option<NonZero<T>>` are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
 * import std.num.NonZero
 *
 * check(sizeOf<NonZero<UInt>>(typeLayoutT = ObjectSlotLayout<NonZero<UInt>>()) == sizeOf<NonZero<UInt>?>(typeLayoutT = ObjectSlotLayout<NonZero<UInt>?>()))
 * check(alignOf<NonZero<UInt>>(typeLayoutT = ObjectSlotLayout<NonZero<UInt>>()) == alignOf<NonZero<UInt>?>(typeLayoutT = ObjectSlotLayout<NonZero<UInt>?>()))
 * ```
 *
 * # Note on generic usage
 *
 * `NonZero<T>` can only be used with some standard library primitive types
 * (such as `UByte`, `Int`, and etc.). The type parameter `T` must implement the
 * internal trait [ZeroablePrimitive], which is currently permanently unstable
 * and cannot be implemented by users. Therefore, you cannot use `NonZero<T>`
 * with your own types, nor can you implement traits for all `NonZero<T>`,
 * only for concrete types.
 */
public open class NonZero<T, NonZeroInner> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<NonZeroInner>,
) : LowerExp, UpperExp, Freeze, UseCloned<NonZero<T, NonZeroInner>>, TrivialClone<NonZero<T, NonZeroInner>>, StructuralPartialEq, BitOr<NonZero<T, NonZeroInner>, Any?, NonZero<T, NonZeroInner>>, BitOrAssign<NonZero<T, NonZeroInner>> {
    public var field0: T.NonZeroInner
        get() = value
        set(v) {
            value = v
        }

    public var value: NonZeroInner
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: NonZeroInner, fieldLayout: TypeLayout<NonZeroInner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NonZero<T, NonZeroInner>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NonZero<T, NonZeroInner>(ptr, layout) }, { it.value })

    public override fun fmt(f: StringBuilder): Result<Unit, Unit> = this.get().fmt(f)

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> = this.get().fmt(f)

    public open fun fmtBinary(f: StringBuilder): Result<Unit, Unit> = this.get().fmt(f)

    public open fun fmtOctal(f: StringBuilder): Result<Unit, Unit> = this.get().fmt(f)

    public open fun fmtLowerHex(f: StringBuilder): Result<Unit, Unit> = this.get().fmt(f)

    public open fun fmtUpperHex(f: StringBuilder): Result<Unit, Unit> = this.get().fmt(f)

    public open fun fmtLowerExp(f: StringBuilder): Result<Unit, Unit> = this.get().fmt(f)

    public open fun fmtUpperExp(f: StringBuilder): Result<Unit, Unit> = this.get().fmt(f)

    public override fun clone(): NonZero<T, NonZeroInner> = NonZero(this.value)

    public open fun eq(other: NonZero<T, NonZeroInner>): Boolean = this.get() == other.get()

    public open fun ne(other: NonZero<T, NonZeroInner>): Boolean = this.get() != other.get()

    public open fun partialCmp(other: NonZero<T, NonZeroInner>): Ordering? = this.get().partialCmp(other.get())

    public open fun lt(other: NonZero<T, NonZeroInner>): Boolean = this.get() < other.get()

    public open fun le(other: NonZero<T, NonZeroInner>): Boolean = this.get() <= other.get()

    public open fun gt(other: NonZero<T, NonZeroInner>): Boolean = this.get() > other.get()

    public open fun ge(other: NonZero<T, NonZeroInner>): Boolean = this.get() >= other.get()

    // Note(constHack): the T: ~const Destruct should be inferred from the Self: ~const Destruct.
    // See https://github.com/rust-lang/rust/issues/144207
    public open fun cmp(other: NonZero<T, NonZeroInner>): Ordering = this.get().cmp(other.get())

    public open fun max(other: NonZero<T, NonZeroInner>): NonZero<T, NonZeroInner> {
        // SAFETY: The maximum of two non-zero values is still non-zero.
        return NonZero.newUnchecked(this.get().max(other.get()))
    }

    public open fun min(other: NonZero<T, NonZeroInner>): NonZero<T, NonZeroInner> {
        // SAFETY: The minimum of two non-zero values is still non-zero.
        return NonZero.newUnchecked(this.get().min(other.get()))
    }

    public open fun clamp(min: NonZero<T, NonZeroInner>, max: NonZero<T, NonZeroInner>): NonZero<T, NonZeroInner> {
        // SAFETY: A non-zero value clamped between two non-zero values is still non-zero.
        return NonZero.newUnchecked(this.get().clamp(min.get(), max.get()))
    }

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.get()).hashCode()
        return state
    }

    public override fun bitor(rhs: NonZero<T, NonZeroInner>): NonZero<T, NonZeroInner> {
        // SAFETY: Bitwise OR of two non-zero values is still non-zero.
        return NonZero.newUnchecked(this.get() or rhs.get())
    }

    public override fun bitor(rhs: T): NonZero<T, NonZeroInner> {
        // SAFETY: Bitwise OR of a non-zero value with anything is still non-zero.
        return NonZero.newUnchecked(this.get() or rhs)
    }

    public override fun bitorAssign(rhs: NonZero<T, NonZeroInner>) {
        this = this or rhs
    }

    public override fun bitorAssign(rhs: T) {
        this = this or rhs
    }

    /**
     * Implementation of `From<NonZero<T>> for & T`.
     */
    public open fun asRef(): T {
        // SAFETY: `repr(transparent)` ensures that `NonZero<T>` has same layout as `T`
        return (ObjectSlotLayout<T>()).readNative(((this)).rawAddress)
    }

    /**
     * Returns the contained value as a primitive type.
     */
    public open fun get(): T {
        // Rustc can set range metadata only if it loads `self` from
        // memory somewhere. If the value of `self` was from by-value argument
        // of some not-inlined function, LLVM don't have range metadata
        // to understand that the value cannot be zero.
        //
        // Using the reinterpret `assume`s the range at runtime.
        //
        // Even once LLVM supports `!range` metadata for function arguments
        // (see <https://github.com/llvm/llvm-project/issues/76628>), this can't
        // be `.0` because MCP#807 bans field-projecting into `scalarValidRange`
        // types, and it arguably wouldn't want to be anyway because if this is
        // MIR-inlined, there's no opportunity to put that argument metadata anywhere.
        //
        // The good answer here will eventually be pattern types, which will hopefully
        // allow it to go back to `.0`, maybe with a cast of some sort.
        //
        // SAFETY: `ZeroablePrimitive` guarantees that the size and bit validity
        // of `.0` is such that this reinterpret is sound.
        return transmuteUnchecked(this)
    }

    public companion object {
        public operator fun <T, NonZeroInner> invoke(value: NonZeroInner, fieldLayout: TypeLayout<NonZeroInner>): NonZero<T, NonZeroInner> = NonZero<T, NonZeroInner>(value, fieldLayout)

        public fun <T, NonZeroInner> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroInner>): NonZero<T, NonZeroInner> = NonZero<T, NonZeroInner>(fieldStorage, fieldLayout)

        /**
         * Creates a non-zero if the given value is not zero.
         */
        public fun <NonZeroInner, T> new(n: T, typeLayoutT: TypeLayout<T>): NonZero<T, NonZeroInner>? {
            // SAFETY: Memory layout optimization guarantees that `Option<NonZero<T>>` has
            // the same layout and size as `T`, with `0` representing `None`.
            return transmuteWithLayouts(n, typeLayoutT, ObjectSlotLayout<NonZero<T, NonZeroInner>?>())
        }

        /**
         * Creates a non-zero without checking whether the value is non-zero.
         * This results in undefined behavior if the value is zero.
         *
         * # Safety
         *
         * The value must not be zero.
         */
        public fun <NonZeroInner, T> newUnchecked(n: T): NonZero<T, NonZeroInner> =
            when (val matchVal = NonZero.new(n)) {
                null -> {
                    // SAFETY: The caller guarantees that `n` is non-zero, so this is unreachable.
                    run {
                        ubChecks.assertUnsafePrecondition(checkLanguageUb, "NonZero::new_unchecked requires the argument to be non-zero", false)
                        error("Entered Rust intrinsics::unreachable")
                    }
                }
                else -> {
                    val n1 = matchVal
                    n1
                }
            }

        /**
         * Converts a reference to a non-zero mutable reference
         * if the referenced value is not zero.
         */
        public fun <NonZeroInner, T> fromMut(n: T): RustMutableReference<NonZero<T, NonZeroInner>>? {
            // SAFETY: Memory layout optimization guarantees that `Option<NonZero<T>>` has
            // the same layout and size as `T`, with `0` representing `None`.
            val optN = RustMutableReference(MutPtr(((MutPtr((n).rawAddress))).rawAddress), ObjectSlotLayout<NonZero<T, NonZeroInner>?>())
            return optN
        }

        /**
         * Converts a mutable reference to a non-zero mutable reference
         * without checking whether the referenced value is non-zero.
         * This results in undefined behavior if the referenced value is zero.
         *
         * # Safety
         *
         * The referenced value must not be zero.
         */
        public fun <NonZeroInner, T> fromMutUnchecked(n: T): RustMutableReference<NonZero<T, NonZeroInner>> =
            when (val matchVal = NonZero.fromMut(n)) {
                null -> {
                    // SAFETY: The caller guarantees that `n` references a value that is non-zero, so this is unreachable.
                    run {
                        ubChecks.assertUnsafePrecondition(checkLibraryUb, "NonZero::from_mut_unchecked requires the argument to dereference as non-zero", false)
                        error("Entered Rust intrinsics::unreachable")
                    }
                }
                else -> {
                    val n1 = matchVal
                    n1
                }
            }

        /**
         * Implementation of `TryFrom<T> for & NonZero<T>`.
         */
        public fun <NonZeroInner, T> fromRef(n: T): NonZero<T, NonZeroInner>? {
            // SAFETY: Memory layout optimization guarantees that `Option<NonZero<T>>` has
            // the same layout and size as `T`, with `0` representing `None`.
            val optN = (ObjectSlotLayout<NonZero<T, NonZeroInner>?>()).readNative(((ConstPtr((n).rawAddress))).rawAddress)
            return optN
        }

        /**
         * Implementation of `TryFrom<[T]> for &[NonZero<T>]`.
         */
        public fun <NonZeroInner, T> fromSlice(n: List<T>): List<NonZero<T, NonZeroInner>>? =
            if (n.all({ x -> NonZero.new(x) != null })) {
                // SAFETY: We explicitly checked that all elements are nonzero, and because of `repr(transparent)`
                // the layout remains unchanged
                rawSliceList(ConstPtr((n.asPtr()).rawAddress), n.size, ObjectSlotLayout<NonZero<T, NonZeroInner>>())
            } else {
                null
            }

        /**
         * Implementation of `TryFrom<[T; N]> for [NonZero<T>; N]`.
         */
        public fun <NonZeroInner, T> fromArray(n: List<T>): List<NonZero<T, NonZeroInner>>? = n.tryMap(NonZero::new)
    }
}

public fun <NonZeroInner, T> fromT(nonzero: NonZero<T, NonZeroInner>): T {
    // Call `get` method to keep range information.
    return nonzero.get()
}

// Note: see library/std/tests/slice-from-array-issue-113238.rs
//
// @stable(feature = "moreFromNonzero", since = "1.101.0")
// impl<a, T> From<&a [NonZero<T>]> for &a [T]
// where
// T: ZeroablePrimitive,
// {
// @inline
// fn from(nonzero: &a [NonZero<T>]) -> &a [T] {
// nonzero.asZeroable()
// }
// }
// impl<T> [NonZero<T>]
// where
// T: ZeroablePrimitive,
// {
// /// Implementation of `From<[NonZero<T>]> for &[T]`.
// @mustUse
// @inline
// fn asZeroable(&self) -> &[T] {
// // SAFETY: `repr(transparent)` ensures that `NonZero<T>` has same layout as `T`, and thus
// //   `[NonZero<T>]` has same layout as `[T]`
// unchecked { &*(slice.fromRawParts(self.asPtr().cast::<T>(), self.len())) }
// }
// }
//
public fun <NonZeroInner, T> fromTArrayn(nonzero: List<NonZero<T, NonZeroInner>>, n: Int): List<T> = nonzero.intoZeroable()

public fun tryFromNonZeroUByte(zeroable: UByte): Result<NonZero<UByte, NonZeroU8Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroUShort(zeroable: UShort): Result<NonZero<UShort, NonZeroU16Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroUInt(zeroable: UInt): Result<NonZero<UInt, NonZeroU32Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroULong(zeroable: ULong): Result<NonZero<ULong, NonZeroU64Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroU128(zeroable: U128): Result<NonZero<U128, NonZeroU128Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroInt(zeroable: Int): Result<NonZero<Int, NonZeroUsizeInner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroByte(zeroable: Byte): Result<NonZero<Byte, NonZeroI8Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroShort(zeroable: Short): Result<NonZero<Short, NonZeroI16Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroLong(zeroable: Long): Result<NonZero<Long, NonZeroI64Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroI128(zeroable: I128): Result<NonZero<I128, NonZeroI128Inner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun tryFromNonZeroChar(zeroable: Char): Result<NonZero<Char, NonZeroCharInner>, TryFromIntError> = (NonZero.fromRef(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun <NonZeroInner, T> tryFromNonZeroTSlice(zeroable: List<T>): Result<List<NonZero<T, NonZeroInner>>, TryFromIntError> = (NonZero.fromSlice(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

public fun <NonZeroInner, T> tryFromNonZeroTArrayn(zeroable: List<T>, n: Int): Result<List<NonZero<T, NonZeroInner>>, TryFromIntError> = (NonZero.fromArray(zeroable)?.let { Result.Ok(it) } ?: Result.Err(TryFromIntError(IntErrorKind.Zero)))

/**
 * Implementation of `From<[NonZero<T>; N]> for [T; N]`.
 */
public fun <NonZeroInner, T> List<NonZero<T, NonZeroInner>>.intoZeroable(n: Int): List<T> = this.map({ it.get() })

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroU8(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroU8Inner>) : NonZero<UByte, NonZeroU8Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroU8Inner, fieldLayout: TypeLayout<NonZeroU8Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS: UInt = 8u

/**
 * Returns the number of leading zeros in the binary representation of `self`.
 *
 * On many architectures, this function can perform better than `leadingZeros()` on the underlying integer type, as special handling of zero can be avoided.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.leadingZeros() == 0)
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.leadingZeros(): UInt = kmCtlzU64((this.get()).toULong())

/**
 * Returns the number of trailing zeros in the binary representation
 * of `self`.
 *
 * On many architectures, this function can perform better than `trailingZeros()` on the underlying integer type, as special handling of zero can be avoided.
 *
 * # Examples
 *
 * ```kotlin
 * check(n.trailingZeros() == 3)
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.trailingZeros(): UInt = kmCttzU64((this.get()).toULong())

/**
 * Returns `self` with only the most significant bit set.
 *
 * # Example
 *
 * ```kotlin
 * check(a.isolateHighestOne() == b)
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.isolateHighestOne(): NonZero<UByte, NonZeroU8Inner> =
    run {
        val bit = ((((1.toUByte()).toUInt() shl ((8u - 1u).toInt())).toUByte()).uncheckedShr(this.leadingZeros()))
        NonZero.newUnchecked(bit.toUByte())
    }

/**
 * Returns `self` with only the least significant bit set.
 *
 * # Example
 *
 * ```kotlin
 * check(a.isolateLowestOne() == b)
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.isolateLowestOne(): NonZero<UByte, NonZeroU8Inner> {
    val n = this.get()
    val n1 = n and rustWrappingNeg(n)
    return NonZero.newUnchecked(n1)
}

/**
 * Returns the index of the highest bit set to one in `self`.
 *
 * Note that this is equivalent to [`ilog2`](Self.ilog2).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.highestOne(): UInt = NonZero.BITS - 1u - this.leadingZeros()

/**
 * Returns the index of the lowest bit set to one in `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.lowestOne(): UInt = this.trailingZeros()

/**
 * Returns the number of ones in the binary representation of `self`.
 *
 * # Examples
 *
 * ```kotlin
 * check(a.countOnes() == NonZero.new(1).getOrElse { return Result.failure(it) })
 * check(b.countOnes() == NonZero.new(3).getOrElse { return Result.failure(it) })
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.countOnes(): NonZero<UInt, NonZeroU32Inner> = NonZero.newUnchecked(rustCountOnes(this.get()))

/**
 * Shifts the bits to the left by a specified amount, `n`,
 * wrapping the truncated bits to the end of the resulting integer.
 *
 * Please note this isn't the same operation as the `<<` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroBitwise)
 * public fun doctestEntry() {}
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.rotateLeft(n: UInt): NonZero<UByte, NonZeroU8Inner> {
    val result = rustRotateLeft(this.get(), n)
    return NonZero.newUnchecked(result)
}

/**
 * Shifts the bits to the right by a specified amount, `n`,
 * wrapping the truncated bits to the beginning of the resulting
 * integer.
 *
 * Please note this isn't the same operation as the `>>` shifting operator!
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroBitwise)
 * public fun doctestEntry() {}
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.rotateRight(n: UInt): NonZero<UByte, NonZeroU8Inner> {
    val result = rustRotateRight(this.get(), n)
    return NonZero.newUnchecked(result)
}

/**
 * Reverses the byte order of the integer.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroBitwise)
 * public fun doctestEntry() {
 *     val m = n.swapBytes()
 * }
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.swapBytes(): NonZero<UByte, NonZeroU8Inner> {
    val result = this.get()
    return NonZero.newUnchecked(result)
}

/**
 * Reverses the order of bits in the integer. The least significant bit becomes the most significant bit,
 * second least-significant bit becomes second most-significant bit, etc.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroBitwise)
 * public fun doctestEntry() {
 *     val m = n.reverseBits()
 * }
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.reverseBits(): NonZero<UByte, NonZeroU8Inner> {
    val result = rustReverseBits(this.get())
    return NonZero.newUnchecked(result)
}

/**
 * Converts an integer from big endian to the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero
 *
 * @feature(nonzeroBitwise)
 * public fun doctestEntry() {
 *     fun test(): Unit? {
 *         if (false) {} else {}
 *         return Unit
 *     }
 *     fun main() {
 *         test()!!
 *     }
 * }
 * ```
 */
public fun NonZero.Companion.fromBe(x: NonZero<UByte, NonZeroU8Inner>): NonZero<UByte, NonZeroU8Inner> {
    val result = x.get()
    return NonZero.newUnchecked(result)
}

/**
 * Converts an integer from little endian to the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero
 *
 * @feature(nonzeroBitwise)
 * public fun doctestEntry() {
 *     fun test(): Unit? {
 *         if (true) {} else {}
 *         return Unit
 *     }
 *     fun main() {
 *         test()!!
 *     }
 * }
 * ```
 */
public fun NonZero.Companion.fromLe(x: NonZero<UByte, NonZeroU8Inner>): NonZero<UByte, NonZeroU8Inner> {
    val result = x.get()
    return NonZero.newUnchecked(result)
}

/**
 * Converts `self` to big endian from the target's endianness.
 *
 * On big endian this is a no-op. On little endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroBitwise)
 * public fun doctestEntry() {
 *     if (false) {
 *         check(n.toBe() == n)
 *     } else {
 *         check(n.toBe() == n.swapBytes())
 *     }
 * }
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.toBe(): NonZero<UByte, NonZeroU8Inner> {
    val result = this.get()
    return NonZero.newUnchecked(result)
}

/**
 * Converts `self` to little endian from the target's endianness.
 *
 * On little endian this is a no-op. On big endian the bytes are
 * swapped.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroBitwise)
 * public fun doctestEntry() {
 *     if (true) {
 *         check(n.toLe() == n)
 *     } else {
 *         check(n.toLe() == n.swapBytes())
 *     }
 * }
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.toLe(): NonZero<UByte, NonZeroU8Inner> {
    val result = this.get()
    return NonZero.newUnchecked(result)
}

/**
 * The smallest value that can be represented by this non-zero
 * integer type, 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN: NonZero<UByte, NonZeroU8Inner> = NonZero.new(1)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX: NonZero<UByte, NonZeroU8Inner> = NonZero.new(UByte.MAX_VALUE)!!

/**
 * Adds an unsigned integer to a non-zero value.
 * Checks for overflow and returns [None] on overflow.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.checkedAdd(1))
 * check(null == max.checkedAdd(1))
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.checkedAdd(other: UByte): NonZero<UByte, NonZeroU8Inner>? =
    when (val matchVal = rustCheckedAdd(this.get(), other)) {
        null -> {
            null
        }
        else -> {
            val result = matchVal
            NonZero.newUnchecked(result)
        }
    }

/**
 * Adds an unsigned integer to a non-zero value, panicking
 * if overflow occurs.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(three == two.strictAdd(1))
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     max.strictAdd(1)
 * }
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.strictAdd(other: UByte): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().strictAdd(other))

/**
 * Adds an unsigned integer to a non-zero value.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.saturatingAdd(1))
 * check(max == max.saturatingAdd(1))
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.saturatingAdd(other: UByte): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(rustSaturatingAdd(this.get(), other))

/**
 * Adds an unsigned integer to a non-zero value,
 * assuming overflow cannot occur.
 * Overflow is unchecked, and it is undefined behavior to overflow
 * *even if the result would wrap to a non-zero value*.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroOps)
 * public fun doctestEntry() {
 *     check(two == one.uncheckedAdd(1))
 * }
 * ```
 *
 * [checkedAdd]: Self.checkedAdd
 */
public fun NonZero<UByte, NonZeroU8Inner>.uncheckedAdd(other: UByte): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().uncheckedAdd(other))

/**
 * Calculates the quotient of `self` and `rhs`, rounding the result towards positive infinity.
 *
 * The result is guaranteed to be non-zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(one.divCeil(max) == one)
 * check(three.divCeil(two) == two)
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.divCeil(rhs: NonZero<UByte, NonZeroU8Inner>): NonZero<UByte, NonZeroU8Inner> {
    val v = (this.get() - 1u) / rhs.get() + 1u
    return NonZero.newUnchecked(v)
}

/**
 * Returns the smallest power of two greater than or equal to `self`.
 * Checks for overflow and returns [None]
 * if the next power of two is greater than the type’s maximum value.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == two.checkedNextPowerOfTwo())
 * check(four == three.checkedNextPowerOfTwo())
 * check(null == max.checkedNextPowerOfTwo())
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.checkedNextPowerOfTwo(): NonZero<UByte, NonZeroU8Inner>? =
    when (val matchVal = this.get().checkedNextPowerOfTwo()) {
        null -> {
            null
        }
        else -> {
            val nz = matchVal
            NonZero.newUnchecked(nz)
        }
    }

/**
 * Returns the base 2 logarithm of the number, rounded down.
 *
 * This is the same operation as
 * except that it has no failure cases to worry about
 * since this value can never be zero.
 *
 * Note that this is equivalent to [`highestOne`](Self.highestOne).
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.ilog2(): UInt = NonZero.BITS - 1u - this.leadingZeros()

/**
 * Returns the base 10 logarithm of the number, rounded down.
 *
 * This is the same operation as
 * except that it has no failure cases to worry about
 * since this value can never be zero.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.ilog10(): UInt = u8(this).toUInt()

/**
 * Calculates the midpoint (average) between `self` and `rhs`.
 *
 * `midpoint(a, b)` is `(a + b)>> 1` as if it were performed in a
 * sufficiently-large signed integral type. This implies that the result is
 * always rounded towards negative infinity and that no overflow will ever occur.
 *
 * # Examples
 *
 * ```kotlin
 * check(one.midpoint(four) == two)
 * check(four.midpoint(one) == two)
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.midpoint(rhs: NonZero<UByte, NonZeroU8Inner>): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().midpoint(rhs.get()))

/**
 * Returns `true` if and only if `self == (1<<k)` for some `k`.
 *
 * On many architectures, this function can perform better than `isPowerOfTwo()`
 * on the underlying integer type, as special handling of zero can be avoided.
 *
 * # Examples
 *
 * ```kotlin
 * check(eight.isPowerOfTwo())
 * check(!ten.isPowerOfTwo())
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.isPowerOfTwo(): Boolean = kmCtpopU64((this.get()).toULong()) < 2u

/**
 * Returns the square root of the number, rounded down.
 *
 * # Examples
 *
 * ```kotlin
 * check(ten.isqrt() == three)
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.isqrt(): NonZero<UByte, NonZeroU8Inner> {
    val result = this.get().isqrt()
    return NonZero.newUnchecked(result)
}

/**
 * Returns the bit pattern of `self` reinterpreted as a signed integer of the same size.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.castSigned(): NonZero<Byte, NonZeroI8Inner> = NonZero.newUnchecked(this.get().castSigned())

/**
 * Returns the minimum number of bits required to represent `self`.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.bitWidth(): NonZero<UInt, NonZeroU32Inner> = NonZero.newUnchecked(NonZero.BITS - this.leadingZeros())

/**
 * Multiplies two non-zero integers together.
 * Checks for overflow and returns [None] on overflow.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(four == two.checkedMul(two))
 * check(null == max.checkedMul(two))
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.checkedMul(other: NonZero<UByte, NonZeroU8Inner>): NonZero<UByte, NonZeroU8Inner>? =
    when (val matchVal = rustCheckedMul(this.get(), other.get().toUByte())) {
        null -> {
            null
        }
        else -> {
            val result = matchVal
            NonZero.newUnchecked(result)
        }
    }

/**
 * Multiplies two non-zero integers together, panicking if
 * overflow occurs.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(nine == three.strictMul(three))
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     max.strictMul(two)
 * }
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.strictMul(other: NonZero<UByte, NonZeroU8Inner>): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().strictMul(other.get()))

/**
 * Multiplies two non-zero integers together.
 *
 * # Examples
 *
 * ```kotlin
 * check(four == two.saturatingMul(two))
 * check(max == four.saturatingMul(max))
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.saturatingMul(other: NonZero<UByte, NonZeroU8Inner>): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(rustSaturatingMul(this.get(), other.get().toUByte()))

/**
 * Multiplies two non-zero integers together,
 * assuming overflow cannot occur.
 * Overflow is unchecked, and it is undefined behavior to overflow
 * *even if the result would wrap to a non-zero value*.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e.
 * when [checkedMul] would return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroOps)
 * public fun doctestEntry() {
 *     check(four == two.uncheckedMul(two))
 * }
 * ```
 *
 * [checkedMul]: Self.checkedMul
 */
public fun NonZero<UByte, NonZeroU8Inner>.uncheckedMul(other: NonZero<UByte, NonZeroU8Inner>): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().uncheckedMul(other.get()))

/**
 * Raises non-zero value to an integer power.
 * Checks for overflow and returns [None] on overflow.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(twentySeven == three.checkedPow(3))
 * check(null == halfMax.checkedPow(3))
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.checkedPow(other: UInt): NonZero<UByte, NonZeroU8Inner>? =
    when (val matchVal = rustCheckedPow(this.get(), other)) {
        null -> {
            null
        }
        else -> {
            val result = matchVal
            NonZero.newUnchecked(result)
        }
    }

/**
 * Raises a non-zero value to an integer power, panicking if
 * overflow occurs.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(sixteen == two.strictPow(4))
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     halfMax.strictPow(2)
 * }
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.strictPow(exp: UInt): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().strictPow(exp))

/**
 * Raise non-zero value to an integer power.
 *
 * # Examples
 *
 * ```kotlin
 * check(twentySeven == three.saturatingPow(3))
 * check(max == max.saturatingPow(3))
 * ```
 */
public fun NonZero<UByte, NonZeroU8Inner>.saturatingPow(other: UInt): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().saturatingPow(other))

/**
 * Parses a non-zero integer from an ASCII-byte slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 */
public fun NonZero.Companion.fromAsciiBytes(src: List<UByte>): Result<NonZero<UByte, NonZeroU8Inner>, ParseIntError> = NonZero.fromAsciiBytesRadixImpl(src, 10)

/**
 * Parses a non-zero integer from an ASCII-byte slice with digits in a given base.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 *
 * - `0 - 9`
 * - `a - z`
 * - `A - Z`
 *
 * # Panics
 *
 * This method panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * @feature(intFromAscii)
 * public fun doctestEntry() {}
 * ```
 */
public fun NonZero.Companion.fromAsciiBytesRadix(src: List<UByte>, radix: UInt): Result<NonZero<UByte, NonZeroU8Inner>, ParseIntError> = NonZero.fromAsciiBytesRadixImpl(src, radix)

public fun NonZero.Companion.fromAsciiBytesRadixImpl(src: List<UByte>, radix: UInt): Result<NonZero<UByte, NonZeroU8Inner>, ParseIntError> {
    val n1 =
        run {
            val matchVal = UByte.fromAsciiBytesRadixImpl(src, radix)
            when (matchVal) { is Result.Ok<*> -> {
                val n = matchVal.value
                n
            }; is Result.Err<*> -> {
                val err = matchVal.error
                return Result.Err(err)
            } }
        }
    return when (val matchVal1 = NonZero.new(n1)) {
        null -> {
            Result.Err(ParseIntError(kind = IntErrorKind.Zero))
        }
        else -> {
            val n2 = matchVal1
            Result.Ok(n2)
        }
    }
}

/**
 * Parses a non-zero integer from a string slice with digits in a given base.
 *
 * The string is expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * Digits are a subset of these characters, depending on `radix`:
 *
 * - `0 - 9`
 * - `a - z`
 * - `A - Z`
 *
 * # Panics
 *
 * This method panics if `radix` is not in the range from 2 to 36.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * ```
 */
public fun NonZero.Companion.fromStrRadix(src: String, radix: UInt): Result<NonZero<UByte, NonZeroU8Inner>, ParseIntError> = NonZero.fromAsciiBytesRadixImpl(src.encodeToByteArray().map { it.toUByte() }, radix)

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroUByte(src: String): Result<NonZero<UByte, NonZeroU8Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * Same as `self / other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun UByte.div(other: NonZero<UByte, NonZeroU8Inner>): UByte = uncheckedDiv(this, other.get())

/**
 * Same as `self /= other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun UByte.divAssign(other: NonZero<UByte, NonZeroU8Inner>) {
    this = (this / other).toUByte()
}

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun UByte.rem(other: NonZero<UByte, NonZeroU8Inner>): UByte = uncheckedRem(this, other.get())

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun UByte.remAssign(other: NonZero<UByte, NonZeroU8Inner>) {
    this = (this % other).toUByte()
}

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroU16(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroU16Inner>) : NonZero<UShort, NonZeroU16Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroU16Inner, fieldLayout: TypeLayout<NonZeroU16Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS1: UInt = 16u

/**
 * The smallest value that can be represented by this non-zero
 * integer type, 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN1: NonZero<UShort, NonZeroU16Inner> = NonZero.new(1)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX1: NonZero<UShort, NonZeroU16Inner> = NonZero.new(UShort.MAX_VALUE)!!

/**
 * Adds an unsigned integer to a non-zero value.
 * Checks for overflow and returns [None] on overflow.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.checkedAdd(1))
 * check(null == max.checkedAdd(1))
 * ```
 */
public fun NonZero<UShort, NonZeroU16Inner>.checkedAdd(other: UShort): NonZero<UShort, NonZeroU16Inner>? =
    when (val matchVal = rustCheckedAdd(this.get(), other)) {
        null -> {
            null
        }
        else -> {
            val result = matchVal
            NonZero.newUnchecked(result)
        }
    }

/**
 * Adds an unsigned integer to a non-zero value, panicking
 * if overflow occurs.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(three == two.strictAdd(1))
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     max.strictAdd(1)
 * }
 * ```
 */
public fun NonZero<UShort, NonZeroU16Inner>.strictAdd(other: UShort): NonZero<UShort, NonZeroU16Inner> = NonZero.newUnchecked(this.get().strictAdd(other))

/**
 * Adds an unsigned integer to a non-zero value.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.saturatingAdd(1))
 * check(max == max.saturatingAdd(1))
 * ```
 */
public fun NonZero<UShort, NonZeroU16Inner>.saturatingAdd(other: UShort): NonZero<UShort, NonZeroU16Inner> = NonZero.newUnchecked(rustSaturatingAdd(this.get(), other))

/**
 * Adds an unsigned integer to a non-zero value,
 * assuming overflow cannot occur.
 * Overflow is unchecked, and it is undefined behavior to overflow
 * *even if the result would wrap to a non-zero value*.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroOps)
 * public fun doctestEntry() {
 *     check(two == one.uncheckedAdd(1))
 * }
 * ```
 *
 * [checkedAdd]: Self.checkedAdd
 */
public fun NonZero<UShort, NonZeroU16Inner>.uncheckedAdd(other: UShort): NonZero<UShort, NonZeroU16Inner> = NonZero.newUnchecked(this.get().uncheckedAdd(other))

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroUShort(src: String): Result<NonZero<UShort, NonZeroU16Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * Same as `self / other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun UShort.div(other: NonZero<UShort, NonZeroU16Inner>): UShort = uncheckedDiv(this, other.get())

/**
 * Same as `self /= other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun UShort.divAssign(other: NonZero<UShort, NonZeroU16Inner>) {
    this = (this / other).toUShort()
}

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun UShort.rem(other: NonZero<UShort, NonZeroU16Inner>): UShort = uncheckedRem(this, other.get())

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun UShort.remAssign(other: NonZero<UShort, NonZeroU16Inner>) {
    this = (this % other).toUShort()
}

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroU32(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroU32Inner>) : NonZero<UInt, NonZeroU32Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroU32Inner, fieldLayout: TypeLayout<NonZeroU32Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS2: UInt = 32u

/**
 * The smallest value that can be represented by this non-zero
 * integer type, 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN2: NonZero<UInt, NonZeroU32Inner> = NonZero.new(1)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX2: NonZero<UInt, NonZeroU32Inner> = NonZero.new(UInt.MAX_VALUE)!!

/**
 * Adds an unsigned integer to a non-zero value.
 * Checks for overflow and returns [None] on overflow.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.checkedAdd(1))
 * check(null == max.checkedAdd(1))
 * ```
 */
public fun NonZero<UInt, NonZeroU32Inner>.checkedAdd(other: UInt): NonZero<UInt, NonZeroU32Inner>? =
    when (val matchVal = rustCheckedAdd(this.get(), other)) {
        null -> {
            null
        }
        else -> {
            val result = matchVal
            NonZero.newUnchecked(result)
        }
    }

/**
 * Adds an unsigned integer to a non-zero value, panicking
 * if overflow occurs.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(three == two.strictAdd(1))
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     max.strictAdd(1)
 * }
 * ```
 */
public fun NonZero<UInt, NonZeroU32Inner>.strictAdd(other: UInt): NonZero<UInt, NonZeroU32Inner> = NonZero.newUnchecked(this.get().strictAdd(other))

/**
 * Adds an unsigned integer to a non-zero value.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.saturatingAdd(1))
 * check(max == max.saturatingAdd(1))
 * ```
 */
public fun NonZero<UInt, NonZeroU32Inner>.saturatingAdd(other: UInt): NonZero<UInt, NonZeroU32Inner> = NonZero.newUnchecked(rustSaturatingAdd(this.get(), other))

/**
 * Adds an unsigned integer to a non-zero value,
 * assuming overflow cannot occur.
 * Overflow is unchecked, and it is undefined behavior to overflow
 * *even if the result would wrap to a non-zero value*.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroOps)
 * public fun doctestEntry() {
 *     check(two == one.uncheckedAdd(1))
 * }
 * ```
 *
 * [checkedAdd]: Self.checkedAdd
 */
public fun NonZero<UInt, NonZeroU32Inner>.uncheckedAdd(other: UInt): NonZero<UInt, NonZeroU32Inner> = NonZero.newUnchecked(this.get().uncheckedAdd(other))

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroUInt(src: String): Result<NonZero<UInt, NonZeroU32Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * Same as `self / other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun UInt.div(other: NonZero<UInt, NonZeroU32Inner>): UInt = uncheckedDiv(this, other.get())

/**
 * Same as `self /= other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun UInt.divAssign(other: NonZero<UInt, NonZeroU32Inner>) {
    this = this / other
}

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun UInt.rem(other: NonZero<UInt, NonZeroU32Inner>): UInt = uncheckedRem(this, other.get())

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun UInt.remAssign(other: NonZero<UInt, NonZeroU32Inner>) {
    this = this % other
}

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroU64(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroU64Inner>) : NonZero<ULong, NonZeroU64Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroU64Inner, fieldLayout: TypeLayout<NonZeroU64Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS3: UInt = 64u

/**
 * The smallest value that can be represented by this non-zero
 * integer type, 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN3: NonZero<ULong, NonZeroU64Inner> = NonZero.new(1)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX3: NonZero<ULong, NonZeroU64Inner> = NonZero.new(ULong.MAX_VALUE)!!

/**
 * Adds an unsigned integer to a non-zero value.
 * Checks for overflow and returns [None] on overflow.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.checkedAdd(1))
 * check(null == max.checkedAdd(1))
 * ```
 */
public fun NonZero<ULong, NonZeroU64Inner>.checkedAdd(other: ULong): NonZero<ULong, NonZeroU64Inner>? =
    when (val matchVal = rustCheckedAdd(this.get(), other)) {
        null -> {
            null
        }
        else -> {
            val result = matchVal
            NonZero.newUnchecked(result)
        }
    }

/**
 * Adds an unsigned integer to a non-zero value, panicking
 * if overflow occurs.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(three == two.strictAdd(1))
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     max.strictAdd(1)
 * }
 * ```
 */
public fun NonZero<ULong, NonZeroU64Inner>.strictAdd(other: ULong): NonZero<ULong, NonZeroU64Inner> = NonZero.newUnchecked(this.get().strictAdd(other))

/**
 * Adds an unsigned integer to a non-zero value.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.saturatingAdd(1))
 * check(max == max.saturatingAdd(1))
 * ```
 */
public fun NonZero<ULong, NonZeroU64Inner>.saturatingAdd(other: ULong): NonZero<ULong, NonZeroU64Inner> = NonZero.newUnchecked(rustSaturatingAdd(this.get(), other))

/**
 * Adds an unsigned integer to a non-zero value,
 * assuming overflow cannot occur.
 * Overflow is unchecked, and it is undefined behavior to overflow
 * *even if the result would wrap to a non-zero value*.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroOps)
 * public fun doctestEntry() {
 *     check(two == one.uncheckedAdd(1))
 * }
 * ```
 *
 * [checkedAdd]: Self.checkedAdd
 */
public fun NonZero<ULong, NonZeroU64Inner>.uncheckedAdd(other: ULong): NonZero<ULong, NonZeroU64Inner> = NonZero.newUnchecked(this.get().uncheckedAdd(other))

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroULong(src: String): Result<NonZero<ULong, NonZeroU64Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * Same as `self / other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun ULong.div(other: NonZero<ULong, NonZeroU64Inner>): ULong = uncheckedDiv(this, other.get())

/**
 * Same as `self /= other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun ULong.divAssign(other: NonZero<ULong, NonZeroU64Inner>) {
    this = this / other
}

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun ULong.rem(other: NonZero<ULong, NonZeroU64Inner>): ULong = uncheckedRem(this, other.get())

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun ULong.remAssign(other: NonZero<ULong, NonZeroU64Inner>) {
    this = this % other
}

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroU128(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroU128Inner>) : NonZero<U128, NonZeroU128Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroU128Inner, fieldLayout: TypeLayout<NonZeroU128Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS4: UInt = 128u

/**
 * The smallest value that can be represented by this non-zero
 * integer type, 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN4: NonZero<U128, NonZeroU128Inner> = NonZero.new(1)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX4: NonZero<U128, NonZeroU128Inner> = NonZero.new(U128.MAX_VALUE)!!

/**
 * Adds an unsigned integer to a non-zero value.
 * Checks for overflow and returns [None] on overflow.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.checkedAdd(1))
 * check(null == max.checkedAdd(1))
 * ```
 */
public fun NonZero<U128, NonZeroU128Inner>.checkedAdd(other: U128): NonZero<U128, NonZeroU128Inner>? =
    when (val matchVal = rustCheckedAdd(this.get(), other)) {
        null -> {
            null
        }
        else -> {
            val result = matchVal
            NonZero.newUnchecked(result)
        }
    }

/**
 * Adds an unsigned integer to a non-zero value, panicking
 * if overflow occurs.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(three == two.strictAdd(1))
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     max.strictAdd(1)
 * }
 * ```
 */
public fun NonZero<U128, NonZeroU128Inner>.strictAdd(other: U128): NonZero<U128, NonZeroU128Inner> = NonZero.newUnchecked(this.get().strictAdd(other))

/**
 * Adds an unsigned integer to a non-zero value.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.saturatingAdd(1))
 * check(max == max.saturatingAdd(1))
 * ```
 */
public fun NonZero<U128, NonZeroU128Inner>.saturatingAdd(other: U128): NonZero<U128, NonZeroU128Inner> = NonZero.newUnchecked(rustSaturatingAdd(this.get(), other))

/**
 * Adds an unsigned integer to a non-zero value,
 * assuming overflow cannot occur.
 * Overflow is unchecked, and it is undefined behavior to overflow
 * *even if the result would wrap to a non-zero value*.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroOps)
 * public fun doctestEntry() {
 *     check(two == one.uncheckedAdd(1))
 * }
 * ```
 *
 * [checkedAdd]: Self.checkedAdd
 */
public fun NonZero<U128, NonZeroU128Inner>.uncheckedAdd(other: U128): NonZero<U128, NonZeroU128Inner> = NonZero.newUnchecked(this.get().uncheckedAdd(other))

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroU128(src: String): Result<NonZero<U128, NonZeroU128Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * Same as `self / other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun U128.div(other: NonZero<U128, NonZeroU128Inner>): U128 = uncheckedDiv(this, other.get())

/**
 * Same as `self /= other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun U128.divAssign(other: NonZero<U128, NonZeroU128Inner>) {
    this = this / other
}

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun U128.rem(other: NonZero<U128, NonZeroU128Inner>): U128 = uncheckedRem(this, other.get())

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun U128.remAssign(other: NonZero<U128, NonZeroU128Inner>) {
    this = this % other
}

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroUsize(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroUsizeInner>) : NonZero<Int, NonZeroUsizeInner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroUsizeInner, fieldLayout: TypeLayout<NonZeroUsizeInner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS5: UInt = 32u

/**
 * The smallest value that can be represented by this non-zero
 * integer type, 1.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN5: NonZero<Int, NonZeroUsizeInner> = NonZero.new(1)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX5: NonZero<Int, NonZeroUsizeInner> = NonZero.new(Int.MAX_VALUE)!!

/**
 * Adds an unsigned integer to a non-zero value.
 * Checks for overflow and returns [None] on overflow.
 * As a consequence, the result cannot wrap to zero.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.checkedAdd(1))
 * check(null == max.checkedAdd(1))
 * ```
 */
public fun NonZero<Int, NonZeroUsizeInner>.checkedAdd(other: Int): NonZero<Int, NonZeroUsizeInner>? =
    when (val matchVal = rustCheckedAdd(this.get(), other)) {
        null -> {
            null
        }
        else -> {
            val result = matchVal
            NonZero.newUnchecked(result)
        }
    }

/**
 * Adds an unsigned integer to a non-zero value, panicking
 * if overflow occurs.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(three == two.strictAdd(1))
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     max.strictAdd(1)
 * }
 * ```
 */
public fun NonZero<Int, NonZeroUsizeInner>.strictAdd(other: Int): NonZero<Int, NonZeroUsizeInner> = NonZero.newUnchecked(this.get().strictAdd(other))

/**
 * Adds an unsigned integer to a non-zero value.
 *
 * # Examples
 *
 * ```kotlin
 * check(two == one.saturatingAdd(1))
 * check(max == max.saturatingAdd(1))
 * ```
 */
public fun NonZero<Int, NonZeroUsizeInner>.saturatingAdd(other: Int): NonZero<Int, NonZeroUsizeInner> = NonZero.newUnchecked(rustSaturatingAdd(this.get(), other))

/**
 * Adds an unsigned integer to a non-zero value,
 * assuming overflow cannot occur.
 * Overflow is unchecked, and it is undefined behavior to overflow
 * *even if the result would wrap to a non-zero value*.
 *
 * # Safety
 *
 * This results in undefined behavior when overflow occurs, i.e. when
 * [checkedAdd] would return `None`.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroOps)
 * public fun doctestEntry() {
 *     check(two == one.uncheckedAdd(1))
 * }
 * ```
 *
 * [checkedAdd]: Self.checkedAdd
 */
public fun NonZero<Int, NonZeroUsizeInner>.uncheckedAdd(other: Int): NonZero<Int, NonZeroUsizeInner> = NonZero.newUnchecked(this.get().uncheckedAdd(other))

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroInt(src: String): Result<NonZero<Int, NonZeroUsizeInner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * Same as `self / other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun Int.div(other: NonZero<Int, NonZeroUsizeInner>): Int = uncheckedDiv(this, other.get())

/**
 * Same as `self /= other.get()`, but because `other` is a `NonZero<_>`,
 * there's never a runtime check for division-by-zero.
 *
 * This operation rounds towards zero, truncating any fractional
 * part of the exact result, and cannot panic.
 */
public fun Int.divAssign(other: NonZero<Int, NonZeroUsizeInner>) {
    this = this / other
}

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun Int.rem(other: NonZero<Int, NonZeroUsizeInner>): Int = uncheckedRem(this, other.get())

/**
 * This operation satisfies `n % d == n - (n / d) * d`, and cannot panic.
 */
public fun Int.remAssign(other: NonZero<Int, NonZeroUsizeInner>) {
    this = this % other
}

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroI8(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroI8Inner>) : NonZero<Byte, NonZeroI8Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroI8Inner, fieldLayout: TypeLayout<NonZeroI8Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS6: UInt = 8u

/**
 * The smallest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN6: NonZero<Byte, NonZeroI8Inner> = NonZero.new(Byte.MIN_VALUE)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX6: NonZero<Byte, NonZeroI8Inner> = NonZero.new(Byte.MAX_VALUE)!!

/**
 * Computes the absolute value of self.
 * for documentation on overflow behavior.
 *
 * # Example
 *
 * ```kotlin
 * check(pos == pos.abs())
 * check(pos == neg.abs())
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.abs(): NonZero<Byte, NonZeroI8Inner> = NonZero.newUnchecked(this.get().abs())

/**
 * Checked absolute value.
 * Checks for overflow and returns [None] if
 * The result cannot be zero.
 *
 * # Example
 *
 * ```kotlin
 * check(pos == neg.checkedAbs())
 * check(null == min.checkedAbs())
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.checkedAbs(): NonZero<Byte, NonZeroI8Inner>? =
    when (val matchVal = rustCheckedAbs(this.get())) {
        null -> {
            null
        }
        else -> {
            val nz = matchVal
            NonZero.newUnchecked(nz)
        }
    }

/**
 * Computes the absolute value of self,
 * with overflow information, see
 *
 * # Example
 *
 * ```kotlin
 * check(Pair(pos, false) == pos.overflowingAbs())
 * check(Pair(pos, false) == neg.overflowingAbs())
 * check(Pair(min, true) == min.overflowingAbs())
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.overflowingAbs(): Pair<NonZero<Byte, NonZeroI8Inner>, Boolean> {
    val (nz, flag) = rustOverflowingAbs(this.get())
    return Pair(NonZero.newUnchecked(nz), flag)
}

/**
 * Saturating absolute value, see
 *
 * # Example
 *
 * ```kotlin
 * check(pos == pos.saturatingAbs())
 * check(pos == neg.saturatingAbs())
 * check(max == min.saturatingAbs())
 * check(max == minPlus.saturatingAbs())
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.saturatingAbs(): NonZero<Byte, NonZeroI8Inner> = NonZero.newUnchecked(rustSaturatingAbs(this.get()))

/**
 * Wrapping absolute value, see
 *
 * # Example
 *
 * ```kotlin
 * check(pos == pos.wrappingAbs())
 * check(pos == neg.wrappingAbs())
 * check(min == min.wrappingAbs())
 * check(max == (-max).wrappingAbs())
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.wrappingAbs(): NonZero<Byte, NonZeroI8Inner> = NonZero.newUnchecked(rustWrappingAbs(this.get()))

/**
 * Computes the absolute value of self
 * without any wrapping or panicking.
 *
 * # Example
 *
 * ```kotlin
 * check(uPos == iPos.unsignedAbs())
 * check(uPos == iNeg.unsignedAbs())
 * check(uMax == iMin.unsignedAbs())
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.unsignedAbs(): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().unsignedAbs())

/**
 * Returns `true` if `self` is positive and `false` if the
 * number is negative.
 *
 * # Example
 *
 * ```kotlin
 * check(posFive.isPositive())
 * check(!negFive.isPositive())
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.isPositive(): Boolean = this.get().isPositive()

/**
 * Returns `true` if `self` is negative and `false` if the
 * number is positive.
 *
 * # Example
 *
 * ```kotlin
 * check(negFive.isNegative())
 * check(!posFive.isNegative())
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.isNegative(): Boolean = this.get().isNegative()

/**
 * Checked negation. Computes `- self`,
 *
 * # Example
 *
 * ```kotlin
 * check(posFive.checkedNeg() == negFive)
 * check(min.checkedNeg() == null)
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.checkedNeg(): NonZero<Byte, NonZeroI8Inner>? {
    when (val matchVal = rustCheckedNeg(this.get())) {
        null -> {}
        else -> {
            val result = matchVal
            return NonZero.newUnchecked(result)
        }
    }
    return null
}

/**
 * Checked negation. Computes `- self`, panicking if `self == MIN`.
 *
 * # Panics
 *
 * ## Overflow behavior
 *
 * This function always panics on overflow, regardless of whether
 * overflow checks are enabled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     check(posSix.strictNeg() == negSix)
 * }
 * ```
 *
 * The following panics because of overflow:
 *
 * ```kotlin
 * @feature(nonzeroStrictOps)
 * public fun doctestEntry() {
 *     min.strictNeg()
 * }
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.strictNeg(): NonZero<Byte, NonZeroI8Inner> = NonZero.newUnchecked(this.get().strictNeg())

/**
 * Negates self, overflowing if this is equal to the minimum value.
 *
 * for documentation on overflow behavior.
 *
 * # Example
 *
 * ```kotlin
 * check(posFive.overflowingNeg() == Pair(negFive, false))
 * check(min.overflowingNeg() == Pair(min, true))
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.overflowingNeg(): Pair<NonZero<Byte, NonZeroI8Inner>, Boolean> {
    val (result, overflow) = rustOverflowingNeg(this.get())
    return Pair((NonZero.newUnchecked(result)), overflow)
}

/**
 * Saturating negation. Computes `- self`,
 * instead of overflowing.
 *
 * # Example
 *
 * ```kotlin
 * check(posFive.saturatingNeg() == negFive)
 * check(min.saturatingNeg() == max)
 * check(max.saturatingNeg() == minPlusOne)
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.saturatingNeg(): NonZero<Byte, NonZeroI8Inner> {
    when (val matchVal = this.checkedNeg()) {
        null -> {}
        else -> {
            val result = matchVal
            return result
        }
    }
    return NonZero.MAX
}

/**
 * Wrapping (modular) negation. Computes `- self`, wrapping around at the boundary
 * of the type.
 *
 * for documentation on overflow behavior.
 *
 * # Example
 *
 * ```kotlin
 * check(posFive.wrappingNeg() == negFive)
 * check(min.wrappingNeg() == min)
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.wrappingNeg(): NonZero<Byte, NonZeroI8Inner> {
    val result = rustWrappingNeg(this.get())
    return NonZero.newUnchecked(result)
}

/**
 * Returns the bit pattern of `self` reinterpreted as an unsigned integer of the same size.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.castUnsigned(): NonZero<UByte, NonZeroU8Inner> = NonZero.newUnchecked(this.get().castUnsigned())

/**
 * Clamps this number to a symmetric range centred around zero.
 *
 * The method clamps the number's magnitude (absolute value) to be at most `limit`.
 *
 * This is functionally equivalent to `self.clamp(- limit, limit)`, but is more
 * explicit about the intent.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(clampMagnitude)
 * public fun doctestEntry() {
 *     check(true)
 *     check(true)
 *     check(true)
 *     check(true)
 * }
 * ```
 */
public fun NonZero<Byte, NonZeroI8Inner>.clampMagnitude(limit: NonZero<UByte, NonZeroU8Inner>): NonZero<Byte, NonZeroI8Inner> = NonZero.newUnchecked(this.get().clampMagnitude(limit.get()))

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroByte(src: String): Result<NonZero<Byte, NonZeroI8Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

public fun NonZero<Byte, NonZeroI8Inner>.neg(): NonZero<Byte, NonZeroI8Inner> = NonZero.newUnchecked(this.get().neg())

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroI16(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroI16Inner>) : NonZero<Short, NonZeroI16Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroI16Inner, fieldLayout: TypeLayout<NonZeroI16Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS7: UInt = 16u

/**
 * The smallest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN7: NonZero<Short, NonZeroI16Inner> = NonZero.new(Short.MIN_VALUE)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX7: NonZero<Short, NonZeroI16Inner> = NonZero.new(Short.MAX_VALUE)!!

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroShort(src: String): Result<NonZero<Short, NonZeroI16Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroI32(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroI32Inner>) : NonZero<Int, NonZeroI32Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroI32Inner, fieldLayout: TypeLayout<NonZeroI32Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS8: UInt = 32u

/**
 * The smallest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN8: NonZero<Int, NonZeroI32Inner> = NonZero.new(Int.MIN_VALUE)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX8: NonZero<Int, NonZeroI32Inner> = NonZero.new(Int.MAX_VALUE)!!

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroI64(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroI64Inner>) : NonZero<Long, NonZeroI64Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroI64Inner, fieldLayout: TypeLayout<NonZeroI64Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS9: UInt = 64u

/**
 * The smallest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN9: NonZero<Long, NonZeroI64Inner> = NonZero.new(Long.MIN_VALUE)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX9: NonZero<Long, NonZeroI64Inner> = NonZero.new(Long.MAX_VALUE)!!

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroLong(src: String): Result<NonZero<Long, NonZeroI64Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroI128(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroI128Inner>) : NonZero<I128, NonZeroI128Inner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroI128Inner, fieldLayout: TypeLayout<NonZeroI128Inner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS10: UInt = 128u

/**
 * The smallest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN10: NonZero<I128, NonZeroI128Inner> = NonZero.new(I128.MIN_VALUE)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX10: NonZero<I128, NonZeroI128Inner> = NonZero.new(I128.MAX_VALUE)!!

/**
 * Parses a non-zero integer from a string slice with decimal digits.
 *
 * The characters are expected to be an optional
 * `+` or `-`
 * sign followed by only digits. Leading and trailing non-digit characters (including
 * whitespace) represent an error. Underscores (which are accepted in Rust literals)
 * also represent an error.
 *
 * # Examples
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 *
 * Trailing space returns error:
 *
 * ```kotlin
 * import std.num.NonZero; import std.String.FromStr;
 * ```
 */
public fun fromStrNonZeroI128(src: String): Result<NonZero<I128, NonZeroI128Inner>, ParseIntError> = NonZero.fromStrRadix(src, 10)

/**
 * This enables some memory layout optimization.
 *
 * ```kotlin
 * ```
 *
 * # Layout
 *
 * with the exception that `0` is not a valid instance.
 * including in FFI.
 *
 * Thanks to the [null pointer optimization],
 * are guaranteed to have the same size and alignment:
 *
 * ```kotlin
 * ```
 *
 * # Compile-time creation
 *
 * Since both [Option.unwrap] and [Option.expect] are `const`, it is possible to
 * define a new
 * at compile time via:
 * ```kotlin
 * ```
 */
public class NonZeroIsize(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonZeroIsizeInner>) : NonZero<Int, NonZeroIsizeInner>(fieldStorage, fieldLayout) {
    public constructor(value: NonZeroIsizeInner, fieldLayout: TypeLayout<NonZeroIsizeInner>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)
    public companion object
}

/**
 * The size of this non-zero integer type in bits.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val BITS11: UInt = 32u

/**
 * The smallest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MIN11: NonZero<Int, NonZeroIsizeInner> = NonZero.new(Int.MIN_VALUE)!!

/**
 * The largest value that can be represented by this non-zero
 * integer type,
 *
 * Note: While most integer types are defined for every whole
 * number between `MIN` and `MAX`, signed non-zero integers are
 * a special case. They have a "gap" at 0.
 *
 * # Examples
 *
 * ```kotlin
 * ```
 */
public val MAX11: NonZero<Int, NonZeroIsizeInner> = NonZero.new(Int.MAX_VALUE)!!
