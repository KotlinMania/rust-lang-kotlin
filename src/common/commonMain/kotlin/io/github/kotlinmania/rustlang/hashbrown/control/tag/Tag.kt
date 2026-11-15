// port-lint: source control/tag.rs
package io.github.kotlinmania.rustlang.hashbrown.control.tag

import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * Single tag in a control group.
 */
public open class Tag internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte>,
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Tag> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Tag(ptr, layout) }, { it.value })

    public open fun clone(): Tag = Tag(value, fieldLayout)

    /**
     * Checks whether a control tag represents a full bucket (top bit is clear).
     */
    public open fun isFull(): Boolean = this.value and 0x80u.toUByte() == 0u.toUByte()

    /**
     * Checks whether a control tag represents a special value (top bit is set).
     */
    public open fun isSpecial(): Boolean = this.value and 0x80u.toUByte() != 0u.toUByte()

    /**
     * Checks whether a special control value is EMPTY (just check 1 bit).
     */
    public open fun specialIsEmpty(): Boolean {
        check(this.isSpecial())
        return this.value and 0x01u.toUByte() != 0u.toUByte()
    }

    public open fun fmt(f: StringBuilder) {
        if (this.isSpecial()) {
            if (this.specialIsEmpty()) {
                f.append("EMPTY")
            } else {
                f.append("DELETED")
            }
        } else {
            DebugTuple(f, "full").field((this.value and 0x7Fu.toUByte())).finish()
        }
    }

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte>): Tag = Tag(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte>): Tag = Tag(fieldStorage, fieldLayout)

        /**
         * Control tag value for an empty bucket.
         */
        public val EMPTY: Tag = Tag(0xFF, fieldLayout = PrimitiveLayouts.U8)

        /**
         * Control tag value for a deleted bucket.
         */
        public val DELETED: Tag = Tag(0x80, fieldLayout = PrimitiveLayouts.U8)

        /**
         * Creates a control tag representing a full bucket with the given hash.
         */
        public fun full(hash: ULong, fieldLayout: TypeLayout<UByte>): Tag {
            // Constant for function that grabs the top 7 bits of the hash.
            val MIN_HASH_LEN: Int =
                if (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) < sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)) {
                    sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)
                } else {
                    sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)
                }
            // Grab the top 7 bits of the hash. While the hash is normally a full 64-bit
            // value, some hash functions (such as FxHash) produce a Int result
            // instead, which means that the top 32 bits are 0 on 32-bit platforms.
            // So we use MIN_HASH_LEN constant to handle this.
            val top7 = hash shr (MIN_HASH_LEN * 8 - 7).toInt()
            return Tag((top7 and 0x7fuL).toUByte(), fieldLayout = fieldLayout)
            // truncation
        }
    }
}

/**
 * Extension trait for slices of tags.
 */
public interface TagSliceExt {
    /**
     * Fills the control with the given tag.
     */
    public fun fillTag(tag: Tag)

    /**
     * Clears out the control.
     */
    public fun fillEmpty() {
        this.fillTag(Tag.EMPTY)
    }
}

public fun List<MaybeUninit<Tag>>.fillTag(tag: Tag) {
    // SAFETY: We have access to the entire slice, so, we can write to the entire slice.
    kmWriteBytes((this.asMutPtr()).rawAddress, (tag.value).toUByte(), (this.size).toLong() * (UnresolvedTypeLayout<MaybeUninit<Tag>>()).sizeBytes)
}
