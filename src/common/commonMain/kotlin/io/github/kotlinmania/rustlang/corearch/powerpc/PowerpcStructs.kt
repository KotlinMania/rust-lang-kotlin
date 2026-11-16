@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Note(ppcf128) improve the docs.

/**
 * The IBM extended-precision (double-double) floating-point type.
 */
public open class Ppcf128 internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Ppcf128> = UnresolvedTypeLayout(),
) {
    public var field0: Ppcf128
        get() = value
        set(v) {
            value = v
        }

    public var value: Ppcf128
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Ppcf128, fieldLayout: TypeLayout<Ppcf128> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Ppcf128> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Ppcf128(ptr, layout) }, { it.value })

    public open fun clone(): Ppcf128 = Ppcf128(value, fieldLayout)

    public open fun fmt(f: Formatter): Result {
        val (hi, lo) = this.toComponents()
        return f.append("ppcf128($hi, $lo)")
    }

    /**
     * Returns the memory representation of this floating point number as a byte array in
     * native byte order.
     */
    public open fun toNeBytes(): UByteArray {
        // SAFETY: every bit pattern of a `ppcf128` is a valid `[UByte; 16]`.
        return transmuteWithLayouts(this, this.fieldLayout, PrimitiveArrayLayouts.uByteArray((16).toInt()))
    }

    /**
     * Returns the large and small component of this floating point number.
     */
    public open fun toComponents(): Pair<Double, Double> {
        // SAFETY: every bit pattern of a `ppcf128` is a valid `[Double; 2]`.
        val (large, small) = transmuteWithLayouts(this, this.fieldLayout, PrimitiveArrayLayouts.doubleArray((2).toInt()))
        return Pair(large, small)
    }

    public companion object {
        public operator fun invoke(value: Ppcf128, fieldLayout: TypeLayout<Ppcf128> = UnresolvedTypeLayout()): Ppcf128 = Ppcf128(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Ppcf128> = UnresolvedTypeLayout()): Ppcf128 = Ppcf128(fieldStorage, fieldLayout)
    }
}
