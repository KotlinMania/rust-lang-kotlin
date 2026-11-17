// port-lint: source control/group/sse2.rs
package io.github.kotlinmania.rustlang.hashbrown.control.group.sse2

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroU16
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.alignOffset
import io.github.kotlinmania.rustlang.hashbrown.control.bitmask.BitMask
import io.github.kotlinmania.rustlang.hashbrown.control.tag.Tag

public typealias BitMaskWord = UShort

public typealias NonZeroBitMaskWord = NonZeroU16

public val BITMASK_STRIDE: Int = 1

public val BITMASK_ITER_MASK: BitMaskWord = (0u.inv()).toUShort()

/**
 * Abstraction over a group of control tags which can be scanned in
 * parallel.
 *
 * This implementation uses a 128-bit SSE value.
 */
public open class Group(
    public var value: x86.M128i,
) {
    public var field0: x86.M128i
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Group = Group(value)

    // Note: https://github.com/rust-lang/rust-clippy/issues/3859

    /**
     * Stores the group of tags to the given address, which must be
     * aligned to `mem.alignOf<Group>()`.
     */
    public open fun storeAligned(ptr: MutPtr) {
        check(ptr.alignOffset(alignOf<Group>(typeLayoutT = UnresolvedTypeLayout<Group>())) == 0)
        run {
            x86.mmStoreSi128(ptr, this.value)
        }
    }

    /**
     * Returns a `BitMask` indicating all tags in the group which have
     * the given value.
     */
    public open fun matchTag(tag: Tag): BitMask =
        run {
            val cmp = x86.mmCmpeqEpi8(this.value, x86.mmSet1Epi8(tag.value.toByte()))
            BitMask(x86.mmMovemaskEpi8(cmp).toUShort())
        }

    /**
     * Returns a `BitMask` indicating all tags in the group which are
     * `EMPTY`.
     */
    public open fun matchEmpty(): BitMask = this.matchTag(Tag.EMPTY)

    /**
     * Returns a `BitMask` indicating all tags in the group which are
     * `EMPTY` or `DELETED`.
     */
    public open fun matchEmptyOrDeleted(): BitMask = BitMask(x86.mmMovemaskEpi8(this.value).toUShort())

    /**
     * Returns a `BitMask` indicating all tags in the group which are full.
     */
    public open fun matchFull(): BitMask = BitMask((this.matchEmptyOrDeleted().value).inv())

    /**
     * Performs the following transformation on all tags in the group:
     * - `EMPTY -> EMPTY`
     * - `DELETED -> EMPTY`
     * - `FULL -> DELETED`
     */
    public open fun convertSpecialToEmptyAndFullToDeleted(): Group {
        // Map highBit = 1 (EMPTY or DELETED) to 1111_1111
        // and highBit = 0 (FULL) to 1000_0000
        //
        // Here's this logic expanded to concrete values:
        // let special = 0 > tag = 1111_1111 (true) or 0000_0000 (false)
        // 1111_1111 | 1000_0000 = 1111_1111
        // 0000_0000 | 1000_0000 = 1000_0000
        return run {
            val zero = x86.mmSetzeroSi128()
            val special = x86.mmCmpgtEpi8(zero, this.value)
            Group(x86.mmOrSi128(special, x86.mmSet1Epi8(Tag.DELETED.first.toByte())))
        }
    }

    public companion object {
        /**
         * Number of bytes in the group.
         */
        public val WIDTH: Int = sizeOf<Group>(typeLayoutT = UnresolvedTypeLayout<Group>())

        /**
         * Returns a full group of empty tags, suitable for use as the initial
         * value for an empty hash table.
         *
         * This is guaranteed to be aligned to the group size.
         */
        public fun staticEmpty(): List<Tag> {
            open class AlignedTags(
                public var align: List<Group>,
                public var tags: List<Tag>,
            )
            val ALIGNED_TAGS: AlignedTags = AlignedTags(align = listOf(), tags = List((Group.WIDTH).toInt()) { Tag.EMPTY })
            return ALIGNED_TAGS.tags
        }

        /**
         * Loads a group of tags starting at the given address.
         */
        public fun load(ptr: ConstPtr): Group = Group(x86.mmLoaduSi128(ptr))

        /**
         * Loads a group of tags starting at the given address, which must be
         * aligned to `mem.alignOf<Group>()`.
         */
        public fun loadAligned(ptr: ConstPtr): Group {
            check(ptr.alignOffset(alignOf<Group>(typeLayoutT = UnresolvedTypeLayout<Group>())) == 0)
            return Group(x86.mmLoadSi128(ptr))
        }
    }
}
