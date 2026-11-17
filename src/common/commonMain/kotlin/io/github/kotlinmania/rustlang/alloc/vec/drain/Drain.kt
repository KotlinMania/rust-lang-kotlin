// port-lint: source vec/drain.rs
package io.github.kotlinmania.rustlang.alloc.vec.drain

import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.take
import io.github.kotlinmania.rustlang.core.ptr.copy
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

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
 * A draining iterator for `List<T>`.
 *
 * This `class` is created by [Vec.drain].
 * See its documentation for more.
 *
 * # Example
 *
 * ```kotlin
 * var v = mutableListOf(0, 1, 2)
 * val iter: Drain = v.drain(0 until 0)
 * ```
 */
public open class Drain<T, A : Allocator> internal constructor(
    /**
     * Index of tail to preserve
     */
    public var tailStart: Int,
    /**
     * Length of tail
     */
    public var tailLen: Int,
    /**
     * Current remaining range to remove
     */
    public var iter: ArrayDeque<T>,
    vecCtorArg: NonNull,
) : AsRef<List<T>>, TrustedLen, Iterable<T> {
    public var vec: NonNull = vecCtorArg
        internal set

    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Drain.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugTuple(f, "Drain").field(this.iter.toList()).finish(); Result.Ok<Unit, Error>(Unit) }

    /**
     * Returns the remaining items of this iterator as a slice.
     *
     * # Examples
     *
     * ```kotlin
     * var vec = mutableListOf('a', 'b', 'c')
     * var drain = vec.drain(0 until 0)
     * check(drain.asSlice() == listOf('a', 'b', 'c'))
     * drain.next()!!
     * check(drain.asSlice() == listOf('b', 'c'))
     * ```
     */
    public open fun asSlice(): List<T> = this.iter.toList()

    /**
     * Returns a reference to the underlying allocator.
     */
    public open fun allocator(): A {
        // SAFETY: `vec` is valid for reads.
        return this.vec.asRef().allocator()
    }

    /**
     * Keep unyielded elements in the source `List`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(drainKeepRest)
     * public fun doctestEntry() {
     *     var vec = mutableListOf('a', 'b', 'c')
     *     var drain = vec.drain(0 until 0)
     *     check(drain.next()!! == 'a')
     *     // This call keeps `b` and `c` in the vec.
     *     drain.keepRest()
     *     // If we wouldn't call `keepRest()`,
     *     // `vec` would be empty.
     *     check(vec == listOf('b', 'c'))
     * }
     * ```
     */
    public open fun keepRest(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        // At this moment layout looks like this:
        //
        // [head] [yielded by next] [unyielded] [yielded by nextBack] [tail]
        // ^-- start         \_________/-- unyieldedLen        \____/-- self.tailLen
        // ^-- unyieldedPtr                  ^-- tail
        //
        // Normally `Drop` impl would drop [unyielded] and then move [tail] to the `start`.
        // Here we want to
        // 1. Move [unyielded] to `start`
        // 2. Move [tail] to a new start at `start + len(unyielded)`
        // 3. Update length of the original vec to `len(head) + len(unyielded) + len(tail)`
        // a. In case of ZST, this is the only thing we want to do
        // 4. Do *not* drop self, as everything is put in a consistent state already, there is nothing to do
        val thisRef = this
        // ignore-tidy-undocumented-unchecked
        run {
            val sourceVec = thisRef.vec.asMut()
            val start = sourceVec.len()
            val tail = thisRef.tailStart
            val unyieldedLen = thisRef.iter.len()
            val unyieldedPtr = thisRef.iter.asSlice().asPtr()
            // ZSTs have no identity, so we don't need to move them around.
            if ((((typeLayoutT).sizeBytes == 0L)).inv()) {
                val startPtr = sourceVec.asMutPtr().add(start)
                // memmove back unyielded elements
                if (unyieldedPtr != startPtr) {
                    val src = unyieldedPtr
                    val dst = startPtr
                    copy(src, dst, unyieldedLen)
                }
                // memmove back untouched tail
                if (tail != (start + unyieldedLen)) {
                    val src1 = sourceVec.asPtr().add(tail)
                    val dst1 = startPtr.add(unyieldedLen)
                    copy(src1, dst1, thisRef.tailLen)
                }
            }
            sourceVec.setLen(start + unyieldedLen + thisRef.tailLen)
        }
    }

    public open fun asRef(): List<T> = this.asSlice()

    public open fun next(): T? {
        // ignore-tidy-undocumented-unchecked
        return this.iter.removeFirstOrNull()?.let({ elt -> read(constPtrFromRustCast(elt), typeLayoutT = UnresolvedTypeLayout<Any?>()) })
    }

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.iter.size; Pair(n, n) }

    public open fun nextBack(): T? {
        // ignore-tidy-undocumented-unchecked
        return this.iter.removeLastOrNull()?.let({ elt -> read(constPtrFromRustCast(elt), typeLayoutT = UnresolvedTypeLayout<Any?>()) })
    }

    public open fun drop(typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()) {
        /**
         * Moves back the un-`Drain`ed elements to restore the original `List`.
         */
        open class DropGuard<T, A : Allocator>(
            public var value: Drain<T, A>,
        ) {
            public var field0: Drain<T, A>
                get() = value
                set(v) {
                    value = v
                }

            public open fun drop() {
                if (this.value.tailLen > 0) {
                    // ignore-tidy-undocumented-unchecked
                    run {
                        val sourceVec = this.value.vec.asMut()
                        // memmove back untouched tail, update to new length
                        val start = sourceVec.len()
                        val tail = this.value.tailStart
                        if (tail != start) {
                            val src = sourceVec.asPtr().add(tail)
                            val dst = sourceVec.asMutPtr().add(start)
                            copy(src, dst, this.value.tailLen)
                        }
                        sourceVec.setLen(start + this.value.tailLen)
                    }
                }
            }
        }
        val iter = take(this.iter)
        val dropLen = iter.len()
        val vec = this.vec
        if (((typeLayoutT).sizeBytes == 0L)) {
            // ZSTs have no identity, so we don't need to move them around, we only need to drop the correct amount.
            // this can be achieved by manipulating the Vec length instead of moving values out from `iter`.
            // ignore-tidy-undocumented-unchecked
            run {
                val vec1 = vec.asMut()
                val oldLen = vec1.len()
                vec1.setLen(oldLen + dropLen + this.tailLen)
                vec1.truncate(oldLen + this.tailLen)
            }
            return
        }
        // ensure elements are moved back into their appropriate places, even when dropInPlace panics
        if (dropLen == 0) {
            return
        }
        // asSlice() must only be called when iter.len() is > 0 because
        // it also gets touched by vec.Splice which may turn it into a dangling pointer
        // which would make it and the vec pointer point to different allocations which would
        // lead to invalid pointer arithmetic below.
        val dropPtr = iter.asSlice().asPtr()
        // ignore-tidy-undocumented-unchecked
        run {
            // dropPtr comes from a slice.Iter which only gives us a &[T] but for dropInPlace
            // a pointer with mutable provenance is necessary. Therefore we must reconstruct
            // it from the original vec but also avoid creating a to the front since that could
            // invalidate raw pointers to it which some unchecked code might rely on.
            val vecPtr = vec.asMut().asMutPtr()
            val dropOffset = dropPtr.offsetFromUnsigned(vecPtr)
            val toDrop = vecPtr.add(dropOffset).castSlice(dropLen)
            dropInPlace(toDrop)
        }
    }

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public companion object {
        public operator fun <T, A : Allocator> invoke(
            tailStart: Int,
            tailLen: Int,
            iter: ArrayDeque<T>,
            vec: NonNull,
        ): Drain<T, A> = Drain<T, A>(
            tailStart = tailStart,
            tailLen = tailLen,
            iter = iter,
            vecCtorArg = vec,
        )
    }
}
