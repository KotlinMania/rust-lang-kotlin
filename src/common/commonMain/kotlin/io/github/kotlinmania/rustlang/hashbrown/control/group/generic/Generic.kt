// port-lint: source control/group/generic.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.hashbrown.control.group.generic

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.alignOffset
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.ptr.write
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.hashbrown.control.bitmask.BitMask
import io.github.kotlinmania.rustlang.hashbrown.control.tag.Tag

// Use the native word size as the group size. Using a 64-bit group size on
// a 32-bit architecture will just end up being more expensive because
// shifts and multiplies will need to be emulated.
public typealias GroupWord = ULong

public typealias NonZeroGroupWord = NonZeroU64

public typealias BitMaskWord = GroupWord

public typealias NonZeroBitMaskWord = NonZeroGroupWord

public val BITMASK_STRIDE: Int = 8

public val BITMASK_ITER_MASK: BitMaskWord = 0uL.inv()

// We only care about the highest bit of each tag for the mask.
public val BITMASK_MASK: BitMaskWord = run { val bytesTmp0 = UByteArray(8) { Tag.DELETED.first.toUByte() }; ((bytesTmp0[7].toULong() shl 56) or (bytesTmp0[6].toULong() shl 48) or (bytesTmp0[5].toULong() shl 40) or (bytesTmp0[4].toULong() shl 32) or (bytesTmp0[3].toULong() shl 24) or (bytesTmp0[2].toULong() shl 16) or (bytesTmp0[1].toULong() shl 8) or bytesTmp0[0].toULong()) }

/**
 * Helper function to replicate a tag across a `GroupWord`.
 */
public fun repeat(tag: Tag): GroupWord = ULong.fromNeBytes(UByteArray((Group.WIDTH).toInt()) { tag.value })

/**
 * Abstraction over a group of control tags which can be scanned in
 * parallel.
 *
 * This implementation uses a word-sized integer.
 */
public open class Group(
    public var value: GroupWord,
) {
    public var field0: GroupWord
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Group = Group(value)

    // We perform all operations in the native endianness, and convert to
    // little-endian just before creating a BitMask. The can potentially
    // enable the compiler to eliminate unnecessary byte swaps if we are
    // only checking whether a BitMask is empty.

    /**
     * Stores the group of tags to the given address, which must be
     * aligned to `mem.alignOf<Group>()`.
     */
    public open fun storeAligned(ptr: MutPtr) {
        check(ptr.alignOffset(alignOf<Group>(typeLayoutT = Group.typeLayout())) == 0)
        run {
            write(ptr, this.value, typeLayoutT = UnresolvedTypeLayout<Tag>())
        }
    }

    /**
     * Returns a `BitMask` indicating all tags in the group which *may*
     * have the given value.
     *
     * This function may return a false positive in certain cases where
     * the tag in the group differs from the searched value only in its
     * lowest bit. This is fine because:
     * - This never happens for `EMPTY` and `DELETED`, only full entries.
     * - The check for key equality will catch these.
     * - This only happens if there is at least 1 true match.
     * - The chance of this happening is very low (< 1% chance per tag).
     */
    public open fun matchTag(tag: Tag): BitMask {
        // This algorithm is derived from
        // https://graphics.stanford.edu/~seander/bithacks.html##ValueInWord
        val cmp = this.value xor repeat(tag)
        return BitMask(((cmp - repeat(Tag(0x01)).toULong()) and cmp.inv() and repeat(Tag.DELETED)))
    }

    /**
     * Returns a `BitMask` indicating all tags in the group which are
     * `EMPTY`.
     */
    public open fun matchEmpty(): BitMask {
        // If the high bit is set, then the tag must be either:
        // 1111_1111 (EMPTY) or 1000_0000 (DELETED).
        // So we can just check if the top two bits are 1 by ANDing them.
        return BitMask((this.value and (this.value shl 1) and repeat(Tag.DELETED)))
    }

    /**
     * Returns a `BitMask` indicating all tags in the group which are
     * `EMPTY` or `DELETED`.
     */
    public open fun matchEmptyOrDeleted(): BitMask {
        // A tag is EMPTY or DELETED iff the high bit is set
        return BitMask((this.value and repeat(Tag.DELETED)))
    }

    /**
     * Returns a `BitMask` indicating all tags in the group which are full.
     */
    public open fun matchFull(): BitMask = BitMask(this.matchEmptyOrDeleted().value xor BITMASK_MASK)

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
        // let full = 1000_0000 (true) or 0000_0000 (false)
        // !1000_0000 + 1 = 0111_1111 + 1 = 1000_0000 (no carry)
        // !0000_0000 + 0 = 1111_1111 + 0 = 1111_1111 (no carry)
        val full = this.value.inv() and repeat(Tag.DELETED)
        return Group(full.inv() + (full shr 7))
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Group> =
            object : TypeLayout<Group> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Group =
                    Group(
                        (PrimitiveLayouts.U64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Group) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Group =
                    Group(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Group) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Number of bytes in the group.
         */
        public val WIDTH: Int = sizeOf<Group>(typeLayoutT = Group.typeLayout())

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
        public fun load(ptr: ConstPtr): Group = Group(readUnaligned(ptr, typeLayoutT = UnresolvedTypeLayout<Tag>()))

        /**
         * Loads a group of tags starting at the given address, which must be
         * aligned to `mem.alignOf<Group>()`.
         */
        public fun loadAligned(ptr: ConstPtr): Group {
            check(ptr.alignOffset(alignOf<Group>(typeLayoutT = Group.typeLayout())) == 0)
            return Group(read(ptr, typeLayoutT = UnresolvedTypeLayout<Tag>()))
        }
    }
}
