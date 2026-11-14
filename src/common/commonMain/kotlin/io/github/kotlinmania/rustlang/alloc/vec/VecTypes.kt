package io.github.kotlinmania.rustlang.alloc.vec

/**
 * Denotes that an allocation of `From` can be recycled into an allocation of `Self`.
 *
 * # Safety
 *
 * `Self` is `Recyclable<From>` if `Layout.new<Self>() == Layout.new<From>()`.
 */
public interface Recyclable<From>

public interface ExtendFromWithinSpec {
    /**
     * # Safety
     *
     * - `src` needs to be valid index
     * - `self.capacity() - self.len()` must be `>= src.len()`
     */
    public fun specExtendFromWithin(src: IntRange)
}
