// port-lint: source iter/adapters/map_windows.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.mapwindows

import io.github.kotlinmania.rustlang.core.array.iterNextChunk
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.manuallydrop.ManuallyDrop
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.option.Item
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * An iterator over the mapped windows of another iterator.
 *
 * This `class` is created by the [Iterator.mapWindows]. See its
 * documentation for more information.
 */
public open class MapWindows<I, F, Item> internal constructor(
    public var f: F,
    innerCtorArg: MapWindowsInner<I, Item>,
) {
    public var inner: MapWindowsInner<I, Item> = innerCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "MapWindows").field("iter", this.inner.iter).finish(); Result.Ok<Unit, Unit>(Unit) }

    public open fun clone(): MapWindows<I, F, Item> = MapWindows(f = this.f.clone(), inner = this.inner.clone())

    public companion object {
        public operator fun <I, F, Item> invoke(
            f: F,
            inner: MapWindowsInner<I, Item>,
        ): MapWindows<I, F, Item> = MapWindows<I, F, Item>(
            f = f,
            innerCtorArg = inner,
        )

        public fun <I, F> new(iter: I, f: F, n: Int, typeLayoutI: TypeLayout<I> = UnresolvedTypeLayout()): MapWindows<I, F, Item> {
            check(n != 0) { "array in `Iterator::map_windows` must contain more than 0 elements" }
            // Only ZST arrays' length can be so large.
            if (((UnresolvedTypeLayout<Item>()).sizeBytes == 0L)) {
                check(n.checkedMul(2) != null) { "array size of `Iterator::map_windows` is too large" }
            }
            return MapWindows(inner = MapWindowsInner.new(iter, n), f = f)
        }
    }
}

public open class MapWindowsInner<I, Item> internal constructor(
    public var iter: I,
    bufferCtorArg: Buffer<Item>?,
) {
    // Since iterators are assumed lazy, i.e. it only yields an item when
    // `Iterator.next()` is called, and `MapWindows` is not an exception.
    //
    // Before the first iteration, we keep the buffer `None`. When the user
    // first call `next` or other methods that makes the iterator advance,
    // we collect the first `N` items yielded from the inner iterator and
    // put it into the buffer.
    //
    // When the inner iterator has returned a `None`, we take
    // away this `buffer` and leave it `None` to reclaim its resources.
    //
    // Note: should we shrink the size of `buffer` using niche optimization?
    public var buffer: Buffer<Item>? = bufferCtorArg
        internal set

    public open fun nextWindow(): List<Item>? {
        when (val matchVal = this.buffer) {
            // It is the first time to advance. We collect
            // the first `N` items from `self.iter` to initialize `self.buffer`.
            null -> this.buffer = Buffer.tryFromIter(this.iter, n)
            else -> {
                val buffer = matchVal
                when (val matchVal1 = this.iter.next()) {
                    null -> {
                        this.buffer.also { this.buffer = null }
                    }
                    // Advance the iterator. We first call `next` before changing our buffer
                    // at all. This means that if `next` panics, our invariant is upheld and
                    // our `Drop` impl drops the correct elements.
                    else -> {
                        val item = matchVal1
                        buffer.push(item)
                    }
                }
            }
        }
        return this.buffer?.let({ it.asArrayRef() })
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (lo, hi) = this.iter.sizeHint()
        return if (this.buffer != null) {
            // If the first `N` items are already yielded by the inner iterator,
            // the size hint is then equal to the that of the inner iterator's.
            Pair(lo, hi)
        } else {
            // If the first `N` items are not yet yielded by the inner iterator,
            // the first `N` elements should be counted as one window, so both bounds
            // should subtract `N - 1`.
            Pair(lo.saturatingSub(N - 1), hi.map({ hi1 -> hi1.saturatingSub(N - 1) }))
        }
    }

    public open fun clone(): MapWindowsInner<I, Item> = MapWindowsInner(iter = this.iter.clone(), buffer = this.buffer)

    public companion object {
        public operator fun <I, Item> invoke(
            iter: I,
            buffer: Buffer<Item>?,
        ): MapWindowsInner<I, Item> = MapWindowsInner<I, Item>(
            iter = iter,
            bufferCtorArg = buffer,
        )

        public fun <I> new(iter: I, n: Int): MapWindowsInner<I, Item> = MapWindowsInner(iter = iter, buffer = null)
    }
}

/**
 * `Buffer<T, N>` is semantically `[MaybeUninit<T>; 2 * N]`. This helps
 * reduce moves while iterating. However, due
 * to limitations of const generics, we use this different type. Note that
 * it has the same underlying memory layout.
 *
 * # Safety invariant
 *
 * `self.buffer[self.start .. self.start + N]` must be initialized,
 * with all other elements being uninitialized. This also
 * implies that `self.start <= N`.
 */
public open class Buffer<T>(
    public var buffer: List<List<MaybeUninit<T>>>,
    public var start: Int,
) {
    public open fun bufferPtr(): ConstPtr = ConstPtr((this.buffer.asPtr()).rawAddress)

    public open fun bufferMutPtr(): MutPtr = MutPtr((this.buffer.asMutPtr()).rawAddress)

    public open fun asArrayRef(): List<T> {
        if (false) {
            check(this.start + N <= 2 * N)
        }
        // SAFETY: our invariant guarantees these elements are initialized.
        return this.bufferPtr().add(this.start).cast()
    }

    public open fun asUninitArrayMut(): MaybeUninit<List<T>> {
        if (false) {
            check(this.start + N <= 2 * N)
        }
        // SAFETY: our invariant guarantees these elements are in bounds.
        return this.bufferMutPtr().add(this.start).cast()
    }

    /**
     * Pushes a new item `next` to the back, and pops the front-most one.
     *
     * All the elements will be shifted to the front end when pushing reaches
     * the back end.
     */
    public open fun push(next: T) {
        val bufferMutPtr = this.bufferMutPtr()
        if (false) {
            check(this.start + N <= 2 * N)
        }
        val toDrop2 =
            if (this.start == N) {
                // We have reached the end of our buffer and have to copy
                // everything to the start. Example layout for N = 3.
                //
                // 0   1   2   3   4   5            0   1   2   3   4   5
                // ┌───┬───┬───┬───┬───┬───┐        ┌───┬───┬───┬───┬───┬───┐
                // │ - │ - │ - │ a │ b │ c │   ->   │ b │ c │ n │ - │ - │ - │
                // └───┴───┴───┴───┴───┴───┘        └───┴───┴───┴───┴───┴───┘
                // ↑                    ↑
                // start                start
                // SAFETY: the two pointers are valid for reads/writes of N -1
                // elements because our array's size is semantically 2 * N. The
                // regions also don't overlap for the same reason.
                //
                // We leave the old elements in place. As soon as `start` is set
                // to 0, we treat them as uninitialized and treat their copies
                // as initialized.
                val toDrop =
                    run {
                        copyNonoverlapping(bufferMutPtr.add(this.start + 1), bufferMutPtr, N - 1)
                        bufferMutPtr.add(N - 1).write(next)
                        bufferMutPtr.add(this.start)
                    }
                this.start = 0
                toDrop
            } else {
                // SAFETY: `self.start` is < N as guaranteed by the invariant
                // plus the check above. Even if the drop at the end panics,
                // the invariant is upheld.
                //
                // Example layout for N = 3:
                //
                // 0   1   2   3   4   5            0   1   2   3   4   5
                // ┌───┬───┬───┬───┬───┬───┐        ┌───┬───┬───┬───┬───┬───┐
                // │ - │ a │ b │ c │ - │ - │   ->   │ - │ - │ b │ c │ n │ - │
                // └───┴───┴───┴───┴───┴───┘        └───┴───┴───┴───┴───┴───┘
                // ↑                                    ↑
                // start                                start
                //
                val toDrop1 =
                    run {
                        bufferMutPtr.add(this.start + N).write(next)
                        bufferMutPtr.add(this.start)
                    }
                this.start += 1
                toDrop1
            }
        // SAFETY: the index is valid and this is element `a` in the
        // diagram above and has not been dropped yet.
        dropInPlace(toDrop2.castInit())
    }

    public open fun clone(): Buffer<T> {
        // Use `ManuallyDrop` until buffer is fully written to avoid dropping uninitialized elements on panic.
        // (See `Buffer` rustdoc for safety invariant)
        val buffer = Buffer(buffer = listOf(List((N).toInt()) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) }, List((N).toInt()) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) }), start = this.start)
        // `clone()` could panic; `ManuallyDrop` guards against that.
        buffer.asUninitArrayMut().write(this.asArrayRef().clone())
        // We initialized the buffer above, so we are good now
        return ManuallyDrop.intoInner(buffer)
    }

    public open fun drop() {
        // SAFETY: our invariant guarantees that N elements starting from
        // `self.start` are initialized. We drop them here.
        run {
            val initializedPart: MutPtr = this.bufferMutPtr().add(this.start).cast().castSlice(N)
            dropInPlace(initializedPart)
        }
    }

    public companion object {
        public fun <T> tryFromIter(iter: Any, n: Int): Buffer<T>? {
            val firstHalf = (iterNextChunk(iter, n).ok() ?: return null)
            val buffer = listOf(MaybeUninit.new(firstHalf, PrimitiveLayouts.U8).transpose(), List(n) { MaybeUninit.uninit(PrimitiveLayouts.U8) })
            return Buffer(buffer = buffer, start = 0)
        }
    }
}

public fun <I, R> MapWindows<I, F, Item>.next(n: Int): R? {
    val window = (this.inner.nextWindow() ?: return null)
    val out = (this.f)(window)
    return out
}

public fun <I, R> MapWindows<I, F, Item>.sizeHint(n: Int): Pair<Int, Int?> = this.inner.sizeHint()
