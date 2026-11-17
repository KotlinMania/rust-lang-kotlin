package io.github.kotlinmania.rustlang.alloc.collections.vecdeque

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ops.arith.Add
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

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
public fun wrapIndex(logicalIndex: Int, capacity: Int): WrappedIndex {
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
public open class WrappedIndex internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout(),
) : Add<Int, Int> {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public var value: Int
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<WrappedIndex> = TransparentTypeLayout(fieldLayout, { ptr, layout -> WrappedIndex(ptr, layout) }, { it.value })

    public open fun clone(): WrappedIndex = WrappedIndex(value, fieldLayout)

    /**
     * Safety invariant: the newly constructed index must still be in-bounds for the VecDeque
     */
    public open fun add(offset: Int): WrappedIndex = WrappedIndex(this.value + offset, fieldLayout = PrimitiveLayouts.USIZE64)

    /**
     * Safety invariant: the newly constructed index must still be in-bounds for the VecDeque
     */
    public open fun sub(offset: Int): WrappedIndex {
        check(this.value >= offset)
        return WrappedIndex(this.value - offset, fieldLayout = PrimitiveLayouts.USIZE64)
    }

    public open fun absDiff(other: WrappedIndex): Int = this.value.absDiff(other.value)

    public open fun asIndex(): Int = this.value

    public open fun isZero(): Boolean = this.value == 0

    public open fun addInt(rhs: Int): Int = this.value + rhs

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.value.fmt(f)

    public open fun eq(other: Int): Boolean = this.value.eq(other)

    public open fun partialCmp(other: Int): Ordering? = this.value.partialCmp(other)

    public companion object {
        public operator fun invoke(value: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): WrappedIndex = WrappedIndex(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): WrappedIndex = WrappedIndex(fieldStorage, fieldLayout)

        /**
         * The newly constructed index has to be in-bounds for the VecDeque
         * that uses the index.
         */
        public fun fromArbitraryNumber(index: Int, fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): WrappedIndex = WrappedIndex(index, fieldLayout = fieldLayout)

        public fun zero(fieldLayout: TypeLayout<Int> = UnresolvedTypeLayout()): WrappedIndex = WrappedIndex(0, fieldLayout = fieldLayout)
    }
}
