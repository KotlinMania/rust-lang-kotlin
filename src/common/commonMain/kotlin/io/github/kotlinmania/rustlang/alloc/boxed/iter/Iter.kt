// port-lint: source boxed/iter.rs
package io.github.kotlinmania.rustlang.alloc.boxed.iter

import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.asynciter.asynciter.AsyncIterator
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context
import io.github.kotlinmania.rustlang.vec.Vec

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

public fun <Item, I, A : Allocator> I.next(): Item? = this.next()

public fun <Item, I, A : Allocator> I.sizeHint(): Pair<Int, Int?> = this.sizeHint()

public fun <Item, I, A : Allocator> I.nth(n: Int): Item? = this.nth(n)

public fun <Item, I, A : Allocator> I.last(): Item? = this.last()

public interface BoxIter<out Item> {
    public fun last(): Item?
}

/**
 * Specialization for sized `I`s that uses `I`s implementation of `last()`
 * instead of the default.
 */

public fun <I, A : Allocator> I.nextBack(): Item? = this.nextBack()

public fun <I, A : Allocator> I.nthBack(n: Int): Item? = this.nthBack(n)

public fun <I, A : Allocator> I.len(): Int = this.len()

public fun <I, A : Allocator> I.isEmpty(): Boolean = this.isEmpty()

public fun <S : AsyncIterator> S.pollNext(cx: Context): Poll<Item?> = Pin.new(this).pollNext(cx)

public fun <S : AsyncIterator> S.sizeHint(): Pair<Int, Int?> = this.sizeHint()

/**
 * This implementation is required to make sure that the `Holder<[T]>: IntoIterator`
 * implementation doesn't overlap with `IntoIterator for T where T: Iterator` blanket.
 */

/**
 * This implementation is required to make sure that the `Holder<[T]>: IntoIterator`
 * implementation doesn't overlap with `IntoIterator for T where T: Iterator` blanket.
 */

/**
 * This implementation is required to make sure that the `Holder<[T]>: IntoIterator`
 * implementation doesn't overlap with `IntoIterator for T where T: Iterator` blanket.
 */

// Note: the `@rustcSkipDuringMethodDispatch(boxedSlice)` on `interface IntoIterator`
// hides this implementation from explicit `.intoIter()` calls on editions < 2024,
// so those calls will still resolve to the slice implementation, by reference.
public fun <T, A : Allocator> List<T>.intoIter(): ArrayDeque<T> = this.intoVec().intoIter()

public fun <T, I : Iterable<T>> fromIteratorFromIterBoxTSlice(iter: I): List<T> = iter.iterator().collect<Vec>().intoBoxedSlice()

public fun <I : Iterable<Char>> fromIteratorFromIterBoxString(iter: I): String = String.fromIter(iter).intoBoxedStr()

/**
 * This implementation is required to make sure that the `Holder<[I; N]>: IntoIterator`
 * implementation doesn't overlap with `IntoIterator for T where T: Iterator` blanket.
 */

/**
 * This implementation is required to make sure that the `Holder<[I; N]>: IntoIterator`
 * implementation doesn't overlap with `IntoIterator for T where T: Iterator` blanket.
 */

/**
 * This implementation is required to make sure that the `Holder<[I; N]>: IntoIterator`
 * implementation doesn't overlap with `IntoIterator for T where T: Iterator` blanket.
 */

public fun <T, A : Allocator> List<T>.intoIter(n: Int): ArrayDeque<T> = this.iter()

/**
 * A by-value `Holder<[T; N]>` iterator.
 */
public open class BoxedArrayIntoIter<T, A : Allocator>(
    // Note: make a more efficient implementation (without the need to store capacity)
    public var inner: ArrayDeque<T>,
)

/**
 * Returns an immutable slice of all elements that have not been yielded
 * yet.
 */
public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.asSlice(n: Int): List<T> = this.inner.toList()

/**
 * Returns a mutable slice of all elements that have not been yielded yet.
 */
public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.asMutSlice(n: Int): MutableList<T> = this.inner.asMutSlice()

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.next(n: Int): T? = this.inner.removeFirstOrNull()

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.sizeHint(n: Int): Pair<Int, Int?> {
    val len = this.len()
    return Pair(len, len)
}

public fun <T, A : Allocator, Acc> BoxedArrayIntoIter<T, N, A>.fold(init: Acc, fold: (Acc, T) -> Acc, n: Int): Acc = this.inner.fold(init, fold)

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.count(n: Int): Int = this.len()

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.last(n: Int): T? = this.nextBack()

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.advanceBy(n: Int): Result<Unit, NonZero<Int>> = this.inner.advanceBy(n)

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.nextBack(n: Int): Item? = this.inner.removeLastOrNull()

public fun <T, A : Allocator, Acc> BoxedArrayIntoIter<T, N, A>.rfold(init: Acc, rfold: (Acc, Item) -> Acc, n: Int): Acc = this.inner.rfold(init, rfold)

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.advanceBackBy(n: Int): Result<Unit, NonZero<Int>> = this.inner.advanceBackBy(n)

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.len(n: Int): Int = this.inner.size

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.isEmpty(n: Int): Boolean = this.inner.isEmpty()

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.clone(n: Int): BoxedArrayIntoIter<T, N, A> = BoxedArrayIntoIter(inner = ArrayDeque(this.inner))

public fun <T, A : Allocator> BoxedArrayIntoIter<T, N, A>.fmt(f: StringBuilder, n: Int) {
    // Only print the elements that were not yielded yet: we cannot
    // access the yielded elements anymore.
    DebugTuple(f, "BoxedArrayIntoIter").field(this.asSlice()).finish()
}
