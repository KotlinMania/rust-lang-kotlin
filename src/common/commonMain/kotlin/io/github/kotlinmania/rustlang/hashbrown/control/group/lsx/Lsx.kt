// port-lint: source control/group/lsx.rs
package io.github.kotlinmania.rustlang.hashbrown.control.group.lsx

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroU16
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
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
 * This implementation uses a 128-bit LSX value.
 */
public open class Group(
    public var value: M128i,
) {
    public var field0: M128i
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
            lsxVst(this.value, ptr, 0)
        }
    }

    /**
     * Returns a `BitMask` indicating all tags in the group which have
     * the given value.
     */
    public open fun matchTag(tag: Tag): BitMask =
        run {
            val cmp = lsxVseqB(this.value, lsxVreplgr2vrB(tag.value.toInt()))
            BitMask(lsxVpickve2grHu(lsxVmskltzB(cmp), 0).toUShort())
        }

    /**
     * Returns a `BitMask` indicating all tags in the group which are
     * `EMPTY`.
     */
    public open fun matchEmpty(): BitMask =
        run {
            val cmp = lsxVseqiB(this.value, (Tag.EMPTY.first.toByte()).toInt())
            BitMask(lsxVpickve2grHu(lsxVmskltzB(cmp), 0).toUShort())
        }

    /**
     * Returns a `BitMask` indicating all tags in the group which are
     * `EMPTY` or `DELETED`.
     */
    public open fun matchEmptyOrDeleted(): BitMask = BitMask(lsxVpickve2grHu(lsxVmskltzB(this.value), 0).toUShort())

    /**
     * Returns a `BitMask` indicating all tags in the group which are full.
     */
    public open fun matchFull(): BitMask = BitMask(lsxVpickve2grHu(lsxVmskgezB(this.value), 0).toUShort())

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
            val special = lsxVsltiB(this.value, 0)
            Group(lsxVoriB(special, Tag.DELETED.first.toUInt()))
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
        public fun load(ptr: ConstPtr): Group = Group(lsxVld(ptr, 0))

        /**
         * Loads a group of tags starting at the given address, which must be
         * aligned to `mem.alignOf<Group>()`.
         */
        public fun loadAligned(ptr: ConstPtr): Group {
            check(ptr.alignOffset(alignOf<Group>(typeLayoutT = UnresolvedTypeLayout<Group>())) == 0)
            return Group(lsxVld(ptr, 0))
        }
    }
}
