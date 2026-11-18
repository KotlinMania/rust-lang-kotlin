// port-lint: source array/iter/iter_inner.rs
package io.github.kotlinmania.rustlang.core.array.iter.iterinner

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.zip
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.indexrange.IndexRange
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

// Defines the `IntoIter` owned iterator for arrays.

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

public interface PartialDrop {
    /**
     * # Safety
     * `self[alive]` are all initialized before the call,
     * then are never used (without reinitializing them) after it.
     */
    public fun partialDrop(alive: IndexRange)
}

public fun <T> List<MaybeUninit<T>>.partialDrop(alive: IndexRange) {
    // SAFETY: We know that all elements within `alive` are properly initialized.
    this.getUncheckedMut(alive).assumeInitDrop()
}

public fun <T> List<MaybeUninit<T>>.partialDropFromMaybeUninitTArrayn(alive: IndexRange, n: Int = 0) {
    val slice: MutableList<MaybeUninit<T>> = this
    // SAFETY: Initialized elements in the array are also initialized in the slice.
    slice.partialDrop(alive)
}

/**
 * The internals of a by-value array iterator.
 *
 * The real `array.IntoIter<T, N>` stores a `PolymorphicIter<[MaybeUninit<T>, N]>`
 * which it unsizes to `PolymorphicIter<[MaybeUninit<T>]>` to iterate.
 */
public open class PolymorphicIter<Data>(
    /**
     * The elements in `data` that have not been yielded yet.
     *
     * Invariants:
     * - `alive.end <= N`
     *
     * (And the `IndexRange` type requires `alive.start <= alive.end`.)
     */
    public var alive: IndexRange,
    /**
     * This is the array we are iterating over.
     *
     * Elements with index `i` where `alive.start <= i<alive.end` have not
     * been yielded yet and are valid array entries. Elements with indices `i
     * < alive.start`or`i >= alive.end` have been yielded already and must
     * not be accessed anymore! Those dead elements might even be in a
     * completely uninitialized state!
     *
     * So the invariants are:
     * - `data[alive]` is alive (i.e. contains valid elements)
     * - `data[.. alive.start]` and `data[alive.end ..]` are dead (i.e. the
     *   elements were already read and must not be touched anymore!)
     */
    public var data: Data,
) where Data : PartialDrop {
    public open fun len(): Int = this.alive.len()

    public open fun drop() {
        // SAFETY: by our type invariant `self.alive` is exactly the initialized
        // items, and this is drop so nothing can use the items afterwards.
        this.data.partialDrop(this.alive.clone())
    }

    public companion object
}

public fun <T> PolymorphicIter.Companion.empty(n: Int = 0): PolymorphicIter<List<MaybeUninit<T>>> = PolymorphicIter(alive = IndexRange.zeroTo(0), data = List(n) { MaybeUninit.uninit(PrimitiveLayouts.U8) })

/**
 * # Safety
 * `data[alive]` are all initialized.
 */
public fun <T> PolymorphicIter.Companion.newUnchecked(alive: IndexRange, data: List<MaybeUninit<T>>, n: Int = 0): PolymorphicIter<List<MaybeUninit<T>>> = PolymorphicIter(alive = alive, data = data)

public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.clone(n: Int = 0): PolymorphicIter<List<MaybeUninit<T>>> = SpecPolymorphicIterClone.specClone(this)

public interface SpecPolymorphicIterClone<T> {
    public fun specClone(thisRef: PolymorphicIter<List<MaybeUninit<T>>>): PolymorphicIter<List<MaybeUninit<T>>>
}

public fun <T> specPolymorphicIterCloneSpecCloneT(thisRef: PolymorphicIter<List<MaybeUninit<T>>>, n: Int = 0): PolymorphicIter<List<MaybeUninit<T>>> {
    fun <U> cloneIntoNew(source: PolymorphicIter<List<MaybeUninit<U>>>, target: PolymorphicIter<List<MaybeUninit<U>>>) {
        // Clone all alive elements.
        for ((src, dst) in zip(source.asSlice(), target.data)) {
            // Write a clone into the new array, then update its alive range.
            // If cloning panics, we'll correctly drop the previous items.
            dst.write(src.clone())
            // This addition cannot overflow as we're iterating a slice,
            // the length of which always fits in Int.
            target.alive = IndexRange.zeroTo(target.alive.end() + 1)
        }
    }
    // Note, we don't really need to match the exact same alive range, so
    // we can just clone into offset 0 regardless of where `self` is.
    val new = PolymorphicIter.empty(n)
    cloneIntoNew(thisRef, new)
    return new
}

public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.asSlice(): List<T> {
    // SAFETY: We know that all elements within `alive` are properly initialized.
    return run {
        val slice = this.data.getUnchecked(this.alive.clone())
        slice.assumeInitRef()
    }
}

public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.asMutSlice(): MutableList<T> {
    // SAFETY: We know that all elements within `alive` are properly initialized.
    return run {
        val slice = this.data.getUncheckedMut(this.alive.clone())
        slice.assumeInitMut()
    }
}

public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.fmt(f: StringBuilder): Result<Unit, Unit> {
    // Only print the elements that were not yielded yet: we cannot
    // access the yielded elements anymore.
    return run { DebugTuple(f, "IntoIter").field(this.asSlice()).finish(); Result.Ok<Unit, Unit>(Unit) }
}

/**
 * Iterator-equivalent methods.
 *
 * We don't implement the actual iterator traits because we want to implement
 * things like `tryFold` that require `Self: Sized` (which we're not).
 */
public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.next(): T? {
    // Get the next index from the front.
    //
    // Increasing `alive.start` by 1 maintains the invariant regarding
    // `alive`. However, due to this change, for a short time, the alive
    // zone is not `data[alive]` anymore, but `data[idx .. alive.end]`.
    return this.alive.next()?.let({ idx ->
        // Read the element from the array.
        // SAFETY: `idx` is an index into the former "alive" region of the
        // array. Reading this element means that `data[idx]` is regarded as
        // dead now (i.e. do not touch). As `idx` was the start of the
        // alive-zone, the alive zone is now `data[alive]` again, restoring
        // all invariants.
        this.data.getUnchecked(idx).assumeInitRead()
    })
}

public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.sizeHint(): Pair<Int, Int?> {
    val len = this.len()
    return Pair(len, len)
}

public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
    // This also moves the start, which marks them as conceptually "dropped",
    // so if anything goes bad then our drop impl won't double-free them.
    val rangeToDrop = this.alive.takePrefix(n)
    val remaining = n - rangeToDrop.len()
    // SAFETY: These elements are currently initialized, so it's fine to drop them.
    run {
        val slice = this.data.getUncheckedMut(rangeToDrop)
        slice.assumeInitDrop()
    }
    return ((NonZero.new<NonZeroUsizeInner, Int>(remaining))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
}

public fun <T, B> PolymorphicIter<List<MaybeUninit<T>>>.fold(init: B, f: (B, T) -> B): B = this.tryFold(init, NeverShortCircuit.wrapMut2(f)).first

public fun <Residual, T, B, R> PolymorphicIter<List<MaybeUninit<T>>>.tryFold(init: B, f: (B, T) -> R): R where R : Try<R, B, Residual> {
    // `alive` is an `IndexRange`, not an arbitrary iterator, so we can
    // trust that its `tryFold` isn't going to do something weird like
    // call the fold-er multiple times for the same index.
    val data = this.data
    return this.alive.tryFold(init, { accum, idx ->
        // SAFETY: `idx` has been removed from the alive range, so we're not
        // going to drop it (even if `f` panics) and thus its ok to give
        // out ownership of that item to `f` to handle.
        val elem = data.getUnchecked(idx).assumeInitRead()
        f(accum, elem)
    })
}

public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.nextBack(): T? {
    // Get the next index from the back.
    //
    // Decreasing `alive.end` by 1 maintains the invariant regarding
    // `alive`. However, due to this change, for a short time, the alive
    // zone is not `data[alive]` anymore, but `data[alive.start ..= idx]`.
    return this.alive.nextBack()?.let({ idx ->
        // Read the element from the array.
        // SAFETY: `idx` is an index into the former "alive" region of the
        // array. Reading this element means that `data[idx]` is regarded as
        // dead now (i.e. do not touch). As `idx` was the end of the
        // alive-zone, the alive zone is now `data[alive]` again, restoring
        // all invariants.
        this.data.getUnchecked(idx).assumeInitRead()
    })
}

public fun <T> PolymorphicIter<List<MaybeUninit<T>>>.advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
    // This also moves the end, which marks them as conceptually "dropped",
    // so if anything goes bad then our drop impl won't double-free them.
    val rangeToDrop = this.alive.takeSuffix(n)
    val remaining = n - rangeToDrop.len()
    // SAFETY: These elements are currently initialized, so it's fine to drop them.
    run {
        val slice = this.data.getUncheckedMut(rangeToDrop)
        slice.assumeInitDrop()
    }
    return ((NonZero.new<NonZeroUsizeInner, Int>(remaining))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
}

public fun <T, B> PolymorphicIter<List<MaybeUninit<T>>>.rfold(init: B, f: (B, T) -> B): B = this.tryRfold(init, NeverShortCircuit.wrapMut2(f)).first

public fun <Residual, T, B, R> PolymorphicIter<List<MaybeUninit<T>>>.tryRfold(init: B, f: (B, T) -> R): R where R : Try<R, B, Residual> {
    // `alive` is an `IndexRange`, not an arbitrary iterator, so we can
    // trust that its `tryRfold` isn't going to do something weird like
    // call the fold-er multiple times for the same index.
    val data = this.data
    return this.alive.tryRfold(init, { accum, idx ->
        // SAFETY: `idx` has been removed from the alive range, so we're not
        // going to drop it (even if `f` panics) and thus its ok to give
        // out ownership of that item to `f` to handle.
        val elem = data.getUnchecked(idx).assumeInitRead()
        f(accum, elem)
    })
}
