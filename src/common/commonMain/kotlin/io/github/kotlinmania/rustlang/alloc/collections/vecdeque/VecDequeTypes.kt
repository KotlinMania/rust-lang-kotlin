package io.github.kotlinmania.rustlang.alloc.collections.vecdeque

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.index
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ops.Add
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public typealias Output = Int

/**
 * Associated functions have the following preconditions:
 *
 * - `src` needs to be a valid range: `src.start <= src.end <= self.len()`.
 * - The buffer must have enough spare capacity: `self.capacity() - self.len() >= src.len()`.
 */
public interface SpecExtendFromWithin {
    public fun specExtendFromWithin(src: IntRange)

    public fun specPrependFromWithin(src: IntRange)
}

/**
 * Returns the index in the underlying buffer for a given logical element index.
 */
internal fun wrapIndex(logicalIndex: Int, capacity: Int): WrappedIndex {
    check((logicalIndex == 0 && capacity == 0) || logicalIndex < capacity || (logicalIndex - capacity) < capacity)
    return if (logicalIndex >= capacity) {
        WrappedIndex(logicalIndex - capacity, fieldLayout = PrimitiveLayouts.USIZE64)
    } else {
        WrappedIndex(logicalIndex, fieldLayout = PrimitiveLayouts.USIZE64)
    }
}

/**
 * Represents an index that can be safely used to index the VecDeque buffer.
 * It exists as a separate type to avoid passing logical (unwrapped) indices to various
 * VecDeque functions by accident.
 *
 * The invariant of this index is that it is always < VecDeque capacity, unless the VecDeque
 * is empty (in that case the index can be 0 when the capacity is 0).
 */
internal open class WrappedIndex(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int>,
) : Add<Int, Any?, Int> {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: Int, fieldLayout: TypeLayout<Int>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<WrappedIndex> = TransparentTypeLayout(fieldLayout, { ptr, layout -> WrappedIndex(ptr, layout) }, { it.value })

    /**
     * Safety invariant: the newly constructed index must still be in-bounds for the VecDeque
     */
    internal open fun add(offset: Int): WrappedIndex = WrappedIndex(this.value + offset, fieldLayout = PrimitiveLayouts.USIZE64)

    /**
     * Safety invariant: the newly constructed index must still be in-bounds for the VecDeque
     */
    internal open fun sub(offset: Int): WrappedIndex {
        check(this.value >= offset)
        return WrappedIndex(this.value - offset, fieldLayout = PrimitiveLayouts.USIZE64)
    }

    internal open fun absDiff(other: WrappedIndex): Int = this.value.absDiff(other.value)

    internal open fun asIndex(): Int = this.value

    internal open fun isZero(): Boolean = this.value == 0

    public open fun addInt(rhs: Int): Int = this.value + rhs

    public open fun fmt(f: StringBuilder) {
        this.value.fmt(f)
    }

    public open fun eq(other: Int): Boolean = this.value.eq(other)

    public open fun partialCmp(other: Int): Ordering? = this.value.partialCmp(other)

    public companion object {
        /**
         * The newly constructed index has to be in-bounds for the VecDeque
         * that uses the index.
         */
        internal fun fromArbitraryNumber(index: Int, fieldLayout: TypeLayout<Int>): WrappedIndex = WrappedIndex(index, fieldLayout = fieldLayout)

        internal fun zero(fieldLayout: TypeLayout<Int>): WrappedIndex = WrappedIndex(0, fieldLayout = fieldLayout)
    }
}
