// port-lint: source control/bitmask.rs
package io.github.kotlinmania.rustlang.hashbrown.control.bitmask

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.trailingZeros
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.hashbrown.control.group.generic.BITMASK_ITER_MASK
import io.github.kotlinmania.rustlang.hashbrown.control.group.generic.BITMASK_STRIDE
import io.github.kotlinmania.rustlang.hashbrown.control.group.generic.BitMaskWord
import io.github.kotlinmania.rustlang.hashbrown.control.group.generic.NonZeroBitMaskWord

/**
 * A bit mask which contains the result of a `Match` operation on a `Group` and
 * allows iterating through them.
 *
 * The bit mask is arranged so that low-order bits represent lower memory
 * addresses for group match results.
 *
 * For implementation reasons, the bits in the set may be sparsely packed with
 * groups of 8 bits representing one element. If any of these bits are non-zero
 * then this element is considered to true in the mask. If this is the
 * case, `BITMASK_STRIDE` will be 8 to indicate a divide-by-8 should be
 * performed on counts/indices to normalize this difference. `BITMASK_MASK` is
 * similarly a mask of all the actually-used bits.
 *
 * To iterate over a bit mask, it must be converted to a form where only 1 bit
 * is set per element. This is done by applying `BITMASK_ITER_MASK` on the
 * mask bits.
 */
public open class BitMask(
    public var value: BitMaskWord,
) : Iterable<Int> {
    public var field0: BitMaskWord
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Int> = this.intoIter().iterator()

    public open fun clone(): BitMask = BitMask(value)

    /**
     * Returns a new `BitMask` with the lowest bit removed.
     */
    public open fun removeLowestBit(): BitMask = BitMask(this.value and (this.value - 1u))

    /**
     * Returns whether the `BitMask` has at least one set bit.
     */
    public open fun anyBitSet(): Boolean = this.value != 0u.toUShort()

    /**
     * Returns the first set bit in the `BitMask`, if there is one.
     */
    public open fun lowestSetBit(): Int? =
        when (val matchVal = NonZeroBitMaskWord.new(this.value)) {
            null -> {
                null
            }
            else -> {
                val nonzero = matchVal
                BitMask.nonzeroTrailingZeros(nonzero)
            }
        }

    /**
     * Returns the number of trailing zeroes in the `BitMask`.
     */
    public open fun trailingZeros(): Int {
        // ARM doesn't have a trailingZeroes instruction, and instead uses
        // reverseBits (RBIT) + leadingZeroes (CLZ). However older ARM
        // versions (pre-ARMv7) don't have RBIT and need to emulate it
        // instead. Since we only have 1 bit set in each byte on ARM, we can
        // use swapBytes (REV) + leadingZeroes instead.
        return if (false && BITMASK_STRIDE % 8 == 0) {
            (run { val swapVal0 = (this.value); (((((swapVal0).toUInt() and 0xFFu) shl 8) or (((swapVal0).toUInt() shr 8) and 0xFFu)).toUShort()) }).leadingZeros().toInt() / BITMASK_STRIDE
        } else {
            this.value.trailingZeros().toInt() / BITMASK_STRIDE
        }
    }

    /**
     * Returns the number of leading zeroes in the `BitMask`.
     */
    public open fun leadingZeros(): Int = this.value.leadingZeros().toInt() / BITMASK_STRIDE

    public open fun intoIter(): BitMaskIter {
        // A BitMask only requires each element (group of bits) to be non-zero.
        // However for iteration we need each element to only contain 1 bit.
        return BitMaskIter(BitMask(this.value and BITMASK_ITER_MASK))
    }

    public companion object {
        public fun typeLayout(): TypeLayout<BitMask> =
            object : TypeLayout<BitMask> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): BitMask =
                    BitMask(
                        (PrimitiveLayouts.U16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: BitMask) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BitMask =
                    BitMask(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BitMask) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Same as above but takes a `NonZeroBitMaskWord`.
         */
        public fun nonzeroTrailingZeros(nonzero: NonZeroBitMaskWord): Int =
            if (false && BITMASK_STRIDE % 8 == 0) {
                // SAFETY: A byte-swapped non-zero value is still non-zero.
                val swapped = NonZeroBitMaskWord.newUnchecked(nonzero.get().swapBytes())
                swapped.leadingZeros().toInt() / BITMASK_STRIDE
            } else {
                nonzero.trailingZeros().toInt() / BITMASK_STRIDE
            }
    }
}

/**
 * Iterator over the contents of a `BitMask`, returning the indices of set
 * bits.
 */
public open class BitMaskIter(
    public var value: BitMask,
) : Iterable<Int> {
    public var field0: BitMask
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
        override fun computeNext() {
            when (val v = this@BitMaskIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): BitMaskIter = BitMaskIter(value)

    public open fun next(): Int? {
        val bit = (this.value.lowestSetBit() ?: return null)
        this.value = this.value.removeLowestBit()
        return bit
    }

    public companion object {
        public fun typeLayout(): TypeLayout<BitMaskIter> =
            object : TypeLayout<BitMaskIter> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): BitMaskIter =
                    BitMaskIter(
                        (BitMask.typeLayout()).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: BitMaskIter) {
                    (BitMask.typeLayout()).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BitMaskIter =
                    BitMaskIter(
                        (BitMask.typeLayout()).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BitMaskIter) {
                    (BitMask.typeLayout()).writeBytes(buf, offset, value.value)
                }
            }
    }
}
