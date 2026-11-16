// port-lint: source iter/adapters/map_windows.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.mapwindows

import io.github.kotlinmania.rustlang.core.array.iterNextChunk
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.manuallydrop.ManuallyDrop
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.checkedMul
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
public open class MapWindows<I, F> internal constructor(
    public var f: F,
    innerCtorArg: MapWindowsInner<I>,
) {
    public var inner: MapWindowsInner<I> = innerCtorArg
        internal set

    public companion object {
        public operator fun <I, F> invoke(
            f: F,
            inner: MapWindowsInner<I>,
        ): MapWindows<I, F> = MapWindows<I, F>(
            f = f,
            innerCtorArg = inner,
        )
    }
}

public open class MapWindowsInner<I> internal constructor(
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

    public companion object {
        public operator fun <I> invoke(
            iter: I,
            buffer: Buffer<Item>?,
        ): MapWindowsInner<I> = MapWindowsInner<I>(
            iter = iter,
            bufferCtorArg = buffer,
        )
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
)

public fun <Item, I, F> MapWindows.Companion.new(iter: I, f: F, n: Int, typeLayoutI: TypeLayout<I> = UnresolvedTypeLayout()): MapWindows<I, F> {
    check(n != 0) { "array in `Iterator::map_windows` must contain more than 0 elements" }
    // Only ZST arrays' length can be so large.
    if (((UnresolvedTypeLayout<Item>()).sizeBytes == 0L)) {
        check(n.checkedMul(2) != null) { "array size of `Iterator::map_windows` is too large" }
    }
    return MapWindows(inner = MapWindowsInner.new(iter), f = f)
}

public fun <Item, I> MapWindowsInner.Companion.new(iter: I, n: Int): MapWindowsInner<I> = MapWindowsInner(iter = iter, buffer = null)

public fun <Item, I> MapWindowsInner<I>.nextWindow(n: Int): List<Item>? {
    when (val matchVal = this.buffer) {
        // It is the first time to advance. We collect
        // the first `N` items from `self.iter` to initialize `self.buffer`.
        null -> this.buffer = Buffer.tryFromIter(this.iter)
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

public fun <Item, I> MapWindowsInner<I>.sizeHint(n: Int): Pair<Int, Int?> {
    val (lo, hi) = this.iter.sizeHint()
    return if (this.buffer != null) {
        // If the first `N` items are already yielded by the inner iterator,
        // the size hint is then equal to the that of the inner iterator's.
        Pair(lo, hi)
    } else {
        // If the first `N` items are not yet yielded by the inner iterator,
        // the first `N` elements should be counted as one window, so both bounds
        // should subtract `N - 1`.
        Pair(lo.saturatingSub(n - 1), hi.map({ hi1 -> hi1.saturatingSub(n - 1) }))
    }
}

public fun <T> Buffer.Companion.tryFromIter(iter: Any, n: Int): Buffer<T>? {
    val firstHalf = (iterNextChunk(iter).ok() ?: return null)
    val buffer = listOf(MaybeUninit.new(firstHalf, PrimitiveLayouts.U8).transpose(), List(n) { MaybeUninit.uninit(PrimitiveLayouts.U8) })
    return Buffer(buffer = buffer, start = 0)
}

public fun <T> Buffer<T>.bufferPtr(n: Int): ConstPtr = ConstPtr((this.buffer.asPtr()).rawAddress)

public fun <T> Buffer<T>.bufferMutPtr(n: Int): MutPtr = MutPtr((this.buffer.asMutPtr()).rawAddress)

public fun <T> Buffer<T>.asArrayRef(n: Int): List<T> {
    if (false) {
        check(this.start + n <= 2 * n)
    }
    // SAFETY: our invariant guarantees these elements are initialized.
    return ConstPtr((ConstPtr((this.bufferPtr()).rawAddress + ((this.start).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).rawAddress)
}

public fun <T> Buffer<T>.asUninitArrayMut(n: Int): MaybeUninit<List<T>> {
    if (false) {
        check(this.start + n <= 2 * n)
    }
    // SAFETY: our invariant guarantees these elements are in bounds.
    return MutPtr((MutPtr((this.bufferMutPtr()).rawAddress + ((this.start).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).rawAddress)
}

/**
 * Pushes a new item `next` to the back, and pops the front-most one.
 *
 * All the elements will be shifted to the front end when pushing reaches
 * the back end.
 */
public fun <T> Buffer<T>.push(next: T, n: Int) {
    val bufferMutPtr = this.bufferMutPtr()
    if (false) {
        check(this.start + n <= 2 * n)
    }
    val toDrop2 =
        if (this.start == n) {
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
                    copyNonoverlapping((MutPtr((bufferMutPtr).rawAddress + ((this.start + 1).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).asConstPtr(), bufferMutPtr, n - 1, typeLayoutT = UnresolvedTypeLayout<MaybeUninit<T>>())
                    (UnresolvedTypeLayout<MaybeUninit<T>>()).readNative((MutPtr((bufferMutPtr).rawAddress + ((n - 1).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).rawAddress).write(next)
                    MutPtr((bufferMutPtr).rawAddress + ((this.start).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))
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
                    (UnresolvedTypeLayout<MaybeUninit<T>>()).readNative((MutPtr((bufferMutPtr).rawAddress + ((this.start + n).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).rawAddress).write(next)
                    MutPtr((bufferMutPtr).rawAddress + ((this.start).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))
                }
            this.start += 1
            toDrop1
        }
    // SAFETY: the index is valid and this is element `a` in the
    // diagram above and has not been dropped yet.
    dropInPlace(toDrop2.castInit())
}

public fun <T> Buffer<T>.clone(n: Int): Buffer<T> {
    // Use `ManuallyDrop` until buffer is fully written to avoid dropping uninitialized elements on panic.
    // (See `Buffer` rustdoc for safety invariant)
    val buffer = Buffer(buffer = listOf(List(n) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) }, List(n) { MaybeUninit.uninit(UnresolvedTypeLayout<T>()) }), start = this.start)
    // `clone()` could panic; `ManuallyDrop` guards against that.
    buffer.asUninitArrayMut().write(this.asArrayRef().clone())
    // We initialized the buffer above, so we are good now
    return ManuallyDrop.intoInner(buffer)
}

public fun <Item, I> MapWindowsInner<I>.clone(n: Int): MapWindowsInner<I> = MapWindowsInner(iter = this.iter.clone(), buffer = this.buffer)

public fun <T> Buffer<T>.drop(n: Int) {
    // SAFETY: our invariant guarantees that N elements starting from
    // `self.start` are initialized. We drop them here.
    run {
        val initializedPart: MutPtr = MutPtr((MutPtr((this.bufferMutPtr()).rawAddress + ((this.start).toLong() * (UnresolvedTypeLayout<MaybeUninit<T>>()).strideBytes))).rawAddress).castSlice(n)
        dropInPlace(initializedPart)
    }
}

public fun <Item, I, R> MapWindows<I, F>.next(n: Int): R? {
    val window = (this.inner.nextWindow() ?: return null)
    val out = (this.f)(window)
    return out
}

public fun <Item, I, R> MapWindows<I, F>.sizeHint(n: Int): Pair<Int, Int?> = this.inner.sizeHint()

public fun <Item, I, F> MapWindows<I, F>.fmt(f: StringBuilder, n: Int): Result<Unit, Unit> = DebugStruct(f, "MapWindows").field("iter", this.inner.iter).finish()

public fun <Item, I, F> MapWindows<I, F>.clone(n: Int): MapWindows<I, F> = MapWindows(f = this.f.clone(), inner = this.inner.clone())
