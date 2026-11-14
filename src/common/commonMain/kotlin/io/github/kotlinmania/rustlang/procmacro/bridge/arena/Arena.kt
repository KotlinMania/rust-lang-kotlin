// port-lint: source bridge/arena.rs
package io.github.kotlinmania.rustlang.procmacro.bridge.arena

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cell.RefCell
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// A minimal arena allocator inspired by `rustcArena.DroplessArena`.
//
// This is unfortunately a minimal re-implementation rather than a dependency
// as it is difficult to depend on crates from within `procMacro`, due to it
// being built at the same time as `std`.

/**
 * A minimal arena allocator inspired by `rustcArena.DroplessArena`.
 *
 * This is unfortunately a complete re-implementation rather than a dependency
 * as it is difficult to depend on crates from within `procMacro`, due to it
 * being built at the same time as `std`.
 *
 * This arena doesn't have support for allocating anything other than byte
 * slices, as that is all that is necessary.
 */
public open class Arena internal constructor(
    startCtorArg: Cell<MutPtr>,
    endCtorArg: Cell<MutPtr>,
    chunksCtorArg: RefCell<List<List<MaybeUninit<UByte>>>>,
) {
    public var start: Cell<MutPtr> = startCtorArg
        internal set

    public var end: Cell<MutPtr> = endCtorArg
        internal set

    public var chunks: RefCell<List<List<MaybeUninit<UByte>>>> = chunksCtorArg
        internal set

    /**
     * Add a new chunk with at least `additional` free bytes.
     */
    public open fun grow(additional: Int) {
        val HUGE_PAGE: Int = 2 * 1024 * 1024
        // The arenas start with PAGE-sized chunks, and then each new chunk is twice as
        // big as its predecessor, up until we reach HUGE_PAGE-sized chunks, whereupon
        // we stop growing. This scales well, from arenas that are barely used up to
        // arenas that are used for 100s of MiBs. Note also that the chosen sizes match
        // the usual sizes of pages and huge pages on Linux.
        val PAGE: Int = 4096
        this.chunks.borrowMut().use { chunks ->
            var newCap: Any?
            when (val matchVal = chunks.lastMut()) {
                null -> {
                    newCap = PAGE
                }
                else -> {
                    val lastChunk = matchVal
                    // If the previous chunk's len is less than HUGE_PAGE
                    // bytes, then this chunk will be least double the previous
                    // chunk's size.
                    newCap = lastChunk.len().min(HUGE_PAGE / 2)
                    newCap *= 2
                }
            }
            // Also ensure that this chunk can fit `additional`.
            newCap = max(additional, newCap)
            val chunk = chunks.pushMut(Box.newUninitSlice(newCap))
            val structVal = chunk.asMutPtrRange()
            val start = structVal.start
            val end = structVal.end
            this.start.set(start)
            this.end.set(end)
        }
    }

    /**
     * Allocates a byte slice with specified size from the current memory
     * chunk. Returns `None` if there is no free space left to satisfy the
     * request.
     */
    public open fun allocRawWithoutGrow(bytes: Int): MutableList<MaybeUninit<UByte>>? {
        val start = this.start.get().addr()
        val oldEnd = this.end.get()
        val end = oldEnd.addr()
        val newEnd = (end.checkedSub(bytes) ?: return null)
        return if (start <= newEnd) {
            val newEnd1 = oldEnd.withAddr(newEnd)
            this.end.set(newEnd1)
            // SAFETY: `bytes` bytes starting at `newEnd` were just reserved.
            Slice.fromRawPartsMut(newEnd1, bytes)
        } else {
            null
        }
    }

    public open fun allocRaw(bytes: Int): MutableList<MaybeUninit<UByte>> {
        if (bytes == 0) {
            return listOf()
        }
        when (val matchVal = this.allocRawWithoutGrow(bytes)) {
            null -> {}
            else -> {
                val a = matchVal
                return a
            }
        }
        // No free space left. Allocate a new chunk to satisfy the request.
        // On failure the grow will panic or abort.
        this.grow(bytes)
        return this.allocRawWithoutGrow(bytes)!!
    }

    // arena allocator
    public open fun allocStr(string: String): String {
        val alloc = this.allocRaw(string.length)
        val bytes = alloc.writeCopyOfSlice(string.encodeToByteArray().map { it.toUByte() })
        // SAFETY: we convert from `String` to `[UByte]`, clone it into the arena,
        // and immediately convert the clone back to `String`.
        return String.fromUtf8UncheckedMut(bytes)
    }

    public companion object {
        public operator fun invoke(
            start: Cell<MutPtr>,
            end: Cell<MutPtr>,
            chunks: RefCell<List<List<MaybeUninit<UByte>>>>,
        ): Arena = Arena(
            startCtorArg = start,
            endCtorArg = end,
            chunksCtorArg = chunks,
        )

        public fun new(): Arena = Arena(start = Cell.new(MutPtr(0L)), end = Cell.new(MutPtr(0L)), chunks = RefCell.new(mutableListOf()))
    }
}
