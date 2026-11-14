// port-lint: source num/bfloat.rs
package io.github.kotlinmania.rustlang.core.num.bfloat

import io.github.kotlinmania.rustlang.core.fmt.LowerExp
import io.github.kotlinmania.rustlang.core.fmt.UpperExp
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

// The 16-bit brain floating-point type.

// what hardware often names it
// LLVM's name
// Wikipedia's name
// The C++ `stdfloat` name

/**
 * A 16-bit brain floating-point value.
 *
 * This type stores values using the bfloat16 encoding. It deliberately
 * exposes only raw-bit construction, comparison, formatting, and lossless
 * widening to [f32].
 *
 * The 16-bit brain floating-point intends to preserve the dynamic range of
 * a 32-bit floating-point value while using half the storage. It does
 * this by using 8 bits for the exponent, the same as `Float`, but only
 * using 7 bits for the mantissa. See [Wikipedia on bfloat16][wikipedia] for
 * more information.
 *
 * [wikipedia]: https://en.wikipedia.org/wiki/Bfloat16Floating-pointFormat
 */
public open class F16b internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UShort>,
) : LowerExp, UpperExp {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public var value: UShort
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UShort, fieldLayout: TypeLayout<UShort>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<F16b> = TransparentTypeLayout(fieldLayout, { ptr, layout -> F16b(ptr, layout) }, { it.value })

    /**
     * Raw transmutation to `UShort`.
     *
     * This is currently identical to `reinterpret<f16b, UShort>(self)` on all platforms.
     *
     * See [`fromBits`](#method.fromBits) for some discussion of the
     * portability of this operation (there are almost no issues).
     *
     * Note that this function is distinct from `as` casting, which attempts to
     * preserve the *numeric* value, and not the bitwise value.
     *
     * ```kotlin
     * import core.num.f16b
     *
     * @feature(f16b)
     * public fun doctestEntry() {
     *     check(f16b.fromBits(0x4148).toBits() == 0x4148uL)
     * }
     * ```
     */
    public open fun toBits(): UShort {
        // SAFETY: `f16b` and `UShort` have the same size, and every bit pattern is valid.
        return transmute(this)
    }

    public open fun clone(): F16b = F16b(this.value)

    public open fun eq(other: F16b): Boolean = widen(this).eq(widen(other))

    public open fun partialCmp(other: F16b): Ordering? = widen(this).partialCmp(widen(other))

    public override fun fmt(formatter: StringBuilder): Result<Unit, Unit> = run { formatter.append((widen(this)).toString()); Result.Ok(Unit) }

    public open fun fmtLowerExp(formatter: StringBuilder): Result<Unit, Unit> = widen(this).fmt(formatter)

    public open fun fmtUpperExp(formatter: StringBuilder): Result<Unit, Unit> = widen(this).fmt(formatter)

    public companion object {
        public operator fun invoke(value: UShort, fieldLayout: TypeLayout<UShort>): F16b = F16b(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UShort>): F16b = F16b(fieldStorage, fieldLayout)

        /**
         * Raw transmutation from `UShort`.
         *
         * This is currently identical to `reinterpret<UShort, f16b>(v)` on all platforms.
         * It turns out this is incredibly portable, for two reasons:
         *
         * * Floats and Ints have the same endianness on all supported platforms.
         * * IEEE 754 very precisely specifies the bit layout of floats.
         *
         * However there is one caveat: prior to the 2008 version of IEEE 754, how
         * to interpret the NaN signaling bit wasn't actually specified. Most platforms
         * (notably x86 and ARM) picked the interpretation that was ultimately
         * standardized in 2008, but some didn't (notably MIPS). As a result, all
         * signaling NaNs on MIPS are quiet NaNs on x86, and vice-versa.
         *
         * Rather than trying to preserve signaling-ness cross-platform, this
         * implementation favors preserving the exact bits. This means that
         * any payloads encoded in NaNs will be preserved even if the result of
         * this method is sent over the network from an x86 machine to a MIPS one.
         *
         * If the results of this method are only manipulated by the same
         * architecture that produced them, then there is no portability concern.
         *
         * If the input isn't NaN, then there is no portability concern.
         *
         * If you don't care about signalingness (very likely), then there is no
         * portability concern.
         *
         * Note that this function is distinct from `as` casting, which attempts to
         * preserve the *numeric* value, and not the bitwise value.
         *
         * ```kotlin
         * import core.num.f16b
         *
         * @feature(f16b)
         * public fun doctestEntry() {
         *     val v = f16b.fromBits(0x4148)
         *     check(Float.from(v) == 12.5)
         * }
         * ```
         */
        public fun fromBits(bits: UShort): F16b {
            // SAFETY: `f16b` and `UShort` have the same size, and every bit pattern is valid.
            return transmuteWithLayouts(bits, PrimitiveLayouts.U16, TransparentTypeLayout(PrimitiveLayouts.U16, { ptr, layout -> F16b(ptr, layout) }, { it.value }))
        }

        public fun default(): F16b = F16b.fromBits(0u)
    }
}

// Note(f16b) - This should be a `@rustcIntrinsic` using LLVM's `fpext`
// with this implementation constituting the fallback.
public fun widen(value: F16b): kotlin.Float = kotlin.Float.fromBits(((value.toBits().toUInt()) shl 16).toInt())

public fun fromFloat(value: F16b): kotlin.Float = widen(value)
