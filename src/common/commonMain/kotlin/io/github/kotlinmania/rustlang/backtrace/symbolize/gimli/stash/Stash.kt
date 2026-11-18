// port-lint: source symbolize/gimli/stash.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.stash

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmapfake.Mmap
import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

/**
 * A simple arena allocator for byte buffers.
 */
public open class Stash internal constructor(
    buffersCtorArg: UnsafeCell<List<List<UByte>>>,
    mmapsCtorArg: UnsafeCell<List<Mmap>>,
) {
    public var buffers: UnsafeCell<List<List<UByte>>> = buffersCtorArg
        internal set

    public var mmaps: UnsafeCell<List<Mmap>> = mmapsCtorArg
        internal set

    /**
     * Allocates a buffer of the specified size and returns a mutable reference
     * to it.
     */
    public open fun allocate(size: Int): MutableList<UByte> {
        // SAFETY: this is the only function that ever constructs a mutable
        // reference to `self.buffers`.
        val buffers = this.buffers.get()
        val i = buffers.len()
        buffers.push(mutableListOf(0, size))
        // SAFETY: we never remove elements from `self.buffers`, so a reference
        // to the data inside any buffer will live as long as `self` does.
        return buffers[i]
    }

    /**
     * Stores a `Mmap` for the scope of this `Stash`, returning a pointer
     * which is scoped to just this scope.
     */
    public open fun cacheMmap(map: Mmap): List<UByte> {
        // SAFETY: this is the only location for a mutable pointer to
        // `mmaps`, and this structure isn't threadsafe to shared across
        // threads either. We also never remove elements from `self.mmaps`,
        // so a reference to the data inside the map will live as long as
        // `self` does.
        return run {
            val mmaps = this.mmaps.get()
            mmaps.push(map)
            mmaps.last()!!
        }
    }

    public companion object {
        public operator fun invoke(
            buffers: UnsafeCell<List<List<UByte>>>,
            mmaps: UnsafeCell<List<Mmap>>,
        ): Stash = Stash(
            buffersCtorArg = buffers,
            mmapsCtorArg = mmaps,
        )

        public fun new(): Stash = Stash(buffers = UnsafeCell.new(mutableListOf()), mmaps = UnsafeCell.new(mutableListOf()))
    }
}
