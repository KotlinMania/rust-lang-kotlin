// port-lint: source control/group/neon.rs
package io.github.kotlinmania.rustlang.hashbrown.control.group.neon

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroU64
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.hashbrown.control.bitmask.BitMask
import io.github.kotlinmania.rustlang.hashbrown.control.tag.Tag

public typealias BitMaskWord = ULong

public typealias NonZeroBitMaskWord = NonZeroU64

public val BITMASK_STRIDE: Int = 8

public val BITMASK_ITER_MASK: BitMaskWord = 0x8080808080808080uL

/**
 * Abstraction over a group of control tags which can be scanned in
 * parallel.
 *
 * This implementation uses a 64-bit NEON value.
 */
public open class Group(
    public var value: neon.Uint8x8T,
) {
    public var field0: neon.Uint8x8T
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Group = Group(value)

    /**
     * Stores the group of tags to the given address, which must be
     * aligned to `mem.alignOf<Group>()`.
     */
    public open fun storeAligned(ptr: MutPtr) {
        check(ptr.alignOffset(alignOf<Group>(typeLayoutT = ObjectSlotLayout<Group>())) == 0)
        run {
            neon.vst1U8(ptr, this.value)
        }
    }

    /**
     * Returns a `BitMask` indicating all tags in the group which *may*
     * have the given value.
     */
    public open fun matchTag(tag: Tag): BitMask =
        run {
            val cmp = neon.vceqU8(this.value, neon.vdupNU8(tag.value))
            BitMask(neon.vgetLaneU64(neon.vreinterpretU64U8(cmp), 0))
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
    public open fun matchEmptyOrDeleted(): BitMask =
        run {
            val cmp = neon.vcltzS8(neon.vreinterpretS8U8(this.value))
            BitMask(neon.vgetLaneU64(neon.vreinterpretU64U8(cmp), 0))
        }

    /**
     * Returns a `BitMask` indicating all tags in the group which are full.
     */
    public open fun matchFull(): BitMask =
        run {
            val cmp = neon.vcgezS8(neon.vreinterpretS8U8(this.value))
            BitMask(neon.vgetLaneU64(neon.vreinterpretU64U8(cmp), 0))
        }

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
            val special = neon.vcltzS8(neon.vreinterpretS8U8(this.value))
            Group(neon.vorrU8(special, neon.vdupNU8(0x80)))
        }
    }

    public companion object {
        /**
         * Number of bytes in the group.
         */
        public val WIDTH: Int = sizeOf<Group>(typeLayoutT = ObjectSlotLayout<Group>())

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
        public fun load(ptr: ConstPtr): Group = Group(neon.vld1U8(ptr))

        /**
         * Loads a group of tags starting at the given address, which must be
         * aligned to `mem.alignOf<Group>()`.
         */
        public fun loadAligned(ptr: ConstPtr): Group {
            check(ptr.alignOffset(alignOf<Group>(typeLayoutT = ObjectSlotLayout<Group>())) == 0)
            return Group(neon.vld1U8(ptr))
        }
    }
}
