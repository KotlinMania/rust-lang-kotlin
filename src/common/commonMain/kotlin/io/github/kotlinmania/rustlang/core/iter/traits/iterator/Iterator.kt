// port-lint: source iter/traits/iterator.rs
package io.github.kotlinmania.rustlang.core.iter.traits.iterator

import io.github.kotlinmania.rustlang.core.array.iter.IntoIter
import io.github.kotlinmania.rustlang.core.array.iterNextChunk
import io.github.kotlinmania.rustlang.core.cmp.KeyAndValue
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.iter.adapters.arraychunks.ArrayChunks
import io.github.kotlinmania.rustlang.core.iter.adapters.byrefsized.ByRefSized
import io.github.kotlinmania.rustlang.core.iter.adapters.chain.Chain
import io.github.kotlinmania.rustlang.core.iter.adapters.cloned.Cloned
import io.github.kotlinmania.rustlang.core.iter.adapters.copied.Copied
import io.github.kotlinmania.rustlang.core.iter.adapters.cycle.Cycle
import io.github.kotlinmania.rustlang.core.iter.adapters.enumerate.Enumerate
import io.github.kotlinmania.rustlang.core.iter.adapters.filter.Filter
import io.github.kotlinmania.rustlang.core.iter.adapters.filtermap.FilterMap
import io.github.kotlinmania.rustlang.core.iter.adapters.flatten.FlatMap
import io.github.kotlinmania.rustlang.core.iter.adapters.flatten.Flatten
import io.github.kotlinmania.rustlang.core.iter.adapters.fuse.Fuse
import io.github.kotlinmania.rustlang.core.iter.adapters.inspect.Inspect
import io.github.kotlinmania.rustlang.core.iter.adapters.intersperse.Intersperse
import io.github.kotlinmania.rustlang.core.iter.adapters.intersperse.IntersperseWith
import io.github.kotlinmania.rustlang.core.iter.adapters.map.Map
import io.github.kotlinmania.rustlang.core.iter.adapters.mapwhile.MapWhile
import io.github.kotlinmania.rustlang.core.iter.adapters.mapwindows.MapWindows
import io.github.kotlinmania.rustlang.core.iter.adapters.peekable.Peekable
import io.github.kotlinmania.rustlang.core.iter.adapters.rev.Rev
import io.github.kotlinmania.rustlang.core.iter.adapters.scan.Scan
import io.github.kotlinmania.rustlang.core.iter.adapters.skip.Skip
import io.github.kotlinmania.rustlang.core.iter.adapters.skipwhile.SkipWhile
import io.github.kotlinmania.rustlang.core.iter.adapters.stepby.StepBy
import io.github.kotlinmania.rustlang.core.iter.adapters.take.Take
import io.github.kotlinmania.rustlang.core.iter.adapters.takewhile.TakeWhile
import io.github.kotlinmania.rustlang.core.iter.adapters.tryProcess
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.Zip
import io.github.kotlinmania.rustlang.core.iter.traits.accum.Product
import io.github.kotlinmania.rustlang.core.iter.traits.accum.Sum
import io.github.kotlinmania.rustlang.core.iter.traits.accum.product
import io.github.kotlinmania.rustlang.core.iter.traits.accum.sum
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.swap
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Break
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ops.trytrait.ChangeOutputType
import io.github.kotlinmania.rustlang.core.ops.trytrait.NeverShortCircuit
import io.github.kotlinmania.rustlang.core.ops.trytrait.Residual
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.result.Result

public fun assertIsDynCompatible(arg0: Iterator<Unit>) {}

/**
 * A trait for dealing with iterators.
 *
 * This is the main iterator trait. For more about the concept of iterators
 * generally, please see the [module-level documentation]. In particular, you
 * may want to know how to [implement `Iterator`][impl].
 */
public interface Iterator<SelfType, Item> {
    /**
     * Advances the iterator and returns the next value.
     *
     * Returns [None] when iteration is finished. Individual iterator
     * implementations may choose to resume iteration, and so calling `next()`
     * again may or may not eventually start returning [Some] again at some
     * point.
     *
     * [Some]: Some
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter()
     * // A call to next() returns the next value...
     * check(1 == iter.next())
     * check(2 == iter.next())
     * check(3 == iter.next())
     * // ... and then None once it's over.
     * check(null == iter.next())
     * // More calls may or may not return `None`. Here, they always will.
     * check(null == iter.next())
     * check(null == iter.next())
     * ```
     */
    public fun next(): Item?

    /**
     * Advances the iterator and returns an array containing the next `N` values.
     *
     * If there are not enough elements to fill the array then `Err` is returned
     * containing an iterator over the remaining elements.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(iterNextChunk)
     * public fun doctestEntry() {
     *     var iter = "lorem".chars()
     *     check(iter.nextChunk().unwrap() == listOf('l', 'o'))
     *     // N is inferred as 2
     *     check(iter.nextChunk().unwrap() == listOf('r', 'e', 'm'))
     *     // N is inferred as 3
     *     check(iter.nextChunk().unwrapErr().asSlice() == listOf())
     *     // N is explicitly 4
     * }
     * ```
     *
     * Split a string and get the first three items.
     *
     * ```kotlin
     * @feature(iterNextChunk)
     * public fun doctestEntry() {
     *     val quote = "not all those who wander are lost"
     *     val (first, second, third) = quote.splitWhitespace().nextChunk().unwrap()
     *     check(first == "not")
     *     check(second == "all")
     *     check(third == "those")
     * }
     * ```
     */
    public fun nextChunk(n: Int): Result<List<Item>, IntoIter<Item>> = iterNextChunk(this, n)

    /**
     * Returns the bounds on the remaining length of the iterator.
     *
     * Specifically, `sizeHint()` returns a tuple where the first element
     * is the lower bound, and the second element is the upper bound.
     *
     * The second half of the tuple that is returned is an <code>[Option]<[Int]></code>.
     * A [None] here means that either there is no known upper bound, or the
     * upper bound is larger than [usize].
     *
     * # Implementation notes
     *
     * It is not enforced that an iterator implementation yields the declared
     * number of elements. A buggy iterator may yield less than the lower bound
     * or more than the upper bound of elements.
     *
     * `sizeHint()` is primarily intended to be used for optimizations such as
     * reserving space for the elements of the iterator, but must not be
     * trusted to e.g., omit bounds checks in unchecked code. An incorrect
     * implementation of `sizeHint()` should not lead to memory safety
     * violations.
     *
     * That said, the implementation should provide a correct estimation,
     * because otherwise it would be a violation of the trait's protocol.
     *
     * The default implementation returns <code>(0, [None])</code> which is correct for any
     * iterator.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.iter()
     * check(Pair(3, 3) == iter.sizeHint())
     * iter.next()
     * check(Pair(2, 2) == iter.sizeHint())
     * ```
     *
     * A more complex example:
     *
     * ```kotlin
     * // The even numbers in the range of zero to nine.
     * val iter = (0 until 10).filter({ x -> x % 2 == 0 })
     * // We might iterate from zero to ten times. Knowing that it's five
     * // exactly wouldn't be possible without executing filter().
     * check(Pair(0, 10) == iter.sizeHint())
     * // Let's add five more numbers with chain()
     * val iter1 = (0 until 10).filter({ x -> x % 2 == 0 }).chain(15 until 20)
     * // now both bounds are increased by five
     * check(Pair(5, 15) == iter1.sizeHint())
     * ```
     *
     * Returning `None` for an upper bound:
     *
     * ```kotlin
     * // an infinite iterator has no upper bound
     * // and the maximum possible lower bound
     * val iter = 0 until Int.MAX_VALUE
     * check(Pair(Int.MAX_VALUE, null) == iter.sizeHint())
     * ```
     */
    public fun sizeHint(): Pair<Int, Int?> = Pair(0, null)

    /**
     * Consumes the iterator, counting the number of iterations and returning it.
     *
     * This method will call [next] repeatedly until [None] is encountered,
     * returning the number of times it saw [Some]. Note that [next] has to be
     * called at least once even if the iterator does not have any elements.
     *
     * [next]: Iterator.next
     *
     * # Overflow Behavior
     *
     * The method does no guarding against overflows, so counting elements of
     * an iterator with more than [usize.Max] elements either produces the
     * wrong result or panics. If overflow checks are enabled, a panic is
     * guaranteed.
     *
     * # Panics
     *
     * This function might panic if the iterator has more than [usize.Max]
     * elements.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.iter().count() == 3)
     * val a1 = listOf(1, 2, 3, 4, 5)
     * check(a1.iter().count() == 5)
     * ```
     */
    public fun count(): Int = this.fold(0, { accum, elem -> accum + 1 })

    /**
     * Consumes the iterator, returning the last element.
     *
     * This method will evaluate the iterator until it returns [None]. While
     * doing so, it keeps track of the current element. After [None] is
     * returned, `last()` will then return the last element it saw.
     *
     * # Panics
     *
     * This function might panic if the iterator is infinite.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().last() == 3)
     * val a1 = listOf(1, 2, 3, 4, 5)
     * check(a1.intoIter().last() == 5)
     * ```
     */
    public fun last(): Item? {
        fun <T> some(arg0: T?, x: T): T? = x
        return this.fold(null, ::some)
    }

    /**
     * Advances the iterator by `n` elements.
     *
     * This method will eagerly skip `n` elements by calling [next] up to `n`
     * times until [None] is encountered.
     *
     * `advanceBy(n)` will return `Ok(())` if the iterator successfully advances by
     * `n` elements, or a `Err(NonZero<Int>)` with value `k` if [None] is encountered,
     * where `k` is remaining number of steps that could not be advanced because the iterator ran out.
     * If `self` is empty and `n` is non-zero, then this returns `Err(n)`.
     * Otherwise, `k` is always less than `n`.
     *
     * Calling `advanceBy(0)` can do meaningful work, for example [Flatten]
     * can advance its outer iterator until it finds an inner iterator that is not empty, which
     * then often allows it to return a more accurate `sizeHint()` than in its initial state.
     *
     * [next]: Iterator.next
     *
     * # Examples
     *
     * ```kotlin
     * import std.num.NonZero
     *
     * @feature(iterAdvanceBy)
     * public fun doctestEntry() {
     *     val a = listOf(1, 2, 3, 4)
     *     var iter = a.intoIter()
     *     check(iter.advanceBy(2) == Unit)
     *     check(iter.next() == 3)
     *     check(iter.advanceBy(0) == Unit)
     *     check(iter.advanceBy(100) == Result.failure(NonZero.new(99).unwrap()))
     *     // only `4` was skipped
     * }
     * ```
     */
    public fun advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        /**
         * Helper trait to specialize `advanceBy` via `tryFold` for `Sized` iterators.
         */
        interface SpecAdvanceBy {
            public fun specAdvanceBy(n1: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>>
        }
        fun <Item, I : Iterator<I, Item>> I.specAdvanceBy(n1: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
            for (i in 0 until n1) {
                if (this.next() == null) {
                    // SAFETY: `i` is always less than `n`.
                    return Result.Err<Unit, NonZero<Int, NonZeroUsizeInner>>(NonZero.newUnchecked<NonZeroUsizeInner, Int>(n1 - i))
                }
            }
            return Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit)
        }
        return this.specAdvanceBy(n)
    }

    /**
     * Returns the `n`th element of the iterator.
     *
     * Like most indexing operations, the count starts from zero, so `nth(0)`
     * returns the first value, `nth(1)` the second, and so on.
     *
     * Note that all preceding elements, as well as the returned element, will be
     * consumed from the iterator. That means that the preceding elements will be
     * discarded, and also that calling `nth(0)` multiple times on the same iterator
     * will return different elements.
     *
     * `nth()` will return [None] if `n` is greater than or equal to the length of the
     * iterator.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().nth(1) == 2)
     * ```
     *
     * Calling `nth()` multiple times doesn't rewind the iterator:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter()
     * check(iter.nth(1) == 2)
     * check(iter.nth(1) == null)
     * ```
     *
     * Returning `None` if there are less than `n + 1` elements:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().nth(10) == null)
     * ```
     */
    public fun nth(n: Int): Item? {
        (this.advanceBy(n).ok() ?: return null)
        return this.next()
    }

    /**
     * Creates an iterator starting at the same point, but stepping by
     * the given amount at each iteration.
     *
     * Note 1: The first element of the iterator will always be returned,
     * regardless of the step given.
     *
     * Note 2: The time at which ignored elements are pulled is not fixed.
     * `StepBy` behaves like the sequence `self.next()`, `self.nth(step - 1)`,
     * `self.nth(step - 1)`, …, but is also free to behave like the sequence
     * `advanceNAndReturnFirst(self, step)`,
     * `advanceNAndReturnFirst(self, step)`, …
     * Which way is used may change for some iterators for performance reasons.
     * The second way will advance the iterator earlier and may consume more items.
     *
     * `advanceNAndReturnFirst` is the equivalent of:
     * ```kotlin
     * fun <I> advanceNAndReturnFirst(iter: I, n: Int): Item? {
     *     val next = iter.next()
     *     if (n > 1) {
     *         iter.nth(n - 2)
     *     }
     *     return next
     * }
     * ```
     *
     * # Panics
     *
     * The method will panic if the given step is `0`.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(0, 1, 2, 3, 4, 5)
     * var iter = a.intoIter().stepBy(2)
     * check(iter.next() == 0)
     * check(iter.next() == 2)
     * check(iter.next() == 4)
     * check(iter.next() == null)
     * ```
     */
    public fun stepBy(step: Int): StepBy<SelfType> = StepBy.new(this, step)

    /**
     * Takes two iterators and creates a new iterator over both in sequence.
     *
     * `chain()` will return a new iterator which will first iterate over
     * values from the first iterator and then over values from the second
     * iterator.
     *
     * In other words, it links two iterators together, in a chain. 🔗
     *
     * [once] is commonly used to adapt a single value into a chain of
     * other kinds of iteration.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val s1 = "abc".chars()
     * val s2 = "def".chars()
     * var iter = s1.chain(s2)
     * check(iter.next() == 'a')
     * check(iter.next() == 'b')
     * check(iter.next() == 'c')
     * check(iter.next() == 'd')
     * check(iter.next() == 'e')
     * check(iter.next() == 'f')
     * check(iter.next() == null)
     * ```
     *
     * Since the argument to `chain()` uses [IntoIterator], we can pass
     * anything that can be converted into an [Iterator], not just an
     * [Iterator] itself. For example, arrays (`[T]`) implement
     * [IntoIterator], and so can be passed to `chain()` directly:
     *
     * ```kotlin
     * val a1 = listOf(1, 2, 3)
     * val a2 = listOf(4, 5, 6)
     * var iter = a1.intoIter().chain(a2)
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * check(iter.next() == 3)
     * check(iter.next() == 4)
     * check(iter.next() == 5)
     * check(iter.next() == 6)
     * check(iter.next() == null)
     * ```
     *
     * If you work with Windows API, you may wish to convert [OsStr] to `List<UShort>`:
     *
     * ```kotlin
     * import std.os.windows.ffi.OsStrExt
     *
     * fun osStrToUtf16(s: OsStr): List<UShort> {
     *     return s.encodeWide().chain(listOf(0)).toMutableList()
     * }
     * ```
     *
     * [OsStr]: ../../std/ffi/struct.OsStr.html
     */
    public fun <U> chain(other: U): Chain<SelfType, IntoIter> where U : Iterable<Item> = Chain.new(this, other.iterator())

    /**
     * Zips up' two iterators into a single iterator of pairs.
     *
     * `zip()` returns a new iterator that will iterate over two other
     * iterators, returning a tuple where the first element comes from the
     * first iterator, and the second element comes from the second iterator.
     *
     * In other words, it zips two iterators together, into a single one.
     *
     * If either iterator returns [None], [next] from the zipped iterator
     * will return [None].
     * If the zipped iterator has no more elements to return then each further attempt to advance
     * it will first try to advance the first iterator at most one time and if it still yielded an item
     * try to advance the second iterator at most one time.
     *
     * To `undo` the result of zipping up two iterators, see [unzip].
     *
     * [unzip]: Iterator.unzip
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val s1 = "abc".chars()
     * val s2 = "def".chars()
     * var iter = (s1.zip(s2))
     * check(iter.next() == Pair('a', 'd'))
     * check(iter.next() == Pair('b', 'e'))
     * check(iter.next() == Pair('c', 'f'))
     * check(iter.next() == null)
     * ```
     *
     * Since the argument to `zip()` uses [IntoIterator], we can pass
     * anything that can be converted into an [Iterator], not just an
     * [Iterator] itself. For example, arrays (`[T]`) implement
     * [IntoIterator], and so can be passed to `zip()` directly:
     *
     * ```kotlin
     * val a1 = listOf(1, 2, 3)
     * val a2 = listOf(4, 5, 6)
     * var iter = (a1.intoIter().zip(a2))
     * check(iter.next() == Pair(1, 4))
     * check(iter.next() == Pair(2, 5))
     * check(iter.next() == Pair(3, 6))
     * check(iter.next() == null)
     * ```
     *
     * `zip()` is often used to zip an infinite iterator to a finite one.
     * This works because the finite iterator will eventually return [None],
     * ending the zipper. Zipping with `(0 ..)` can look a lot like [enumerate]:
     *
     * ```kotlin
     * val enumerate: Vec = "foo".chars().mapIndexed { index, item -> Pair(index, item) }.collect()
     * val zipper: Vec = ((0 until Int.MAX_VALUE).zip("foo".chars())).collect()
     * check(Pair(0, 'f') == enumerate[0])
     * check(Pair(0, 'f') == zipper[0])
     * check(Pair(1, 'o') == enumerate[1])
     * check(Pair(1, 'o') == zipper[1])
     * check(Pair(2, 'o') == enumerate[2])
     * check(Pair(2, 'o') == zipper[2])
     * ```
     *
     * If both iterators have roughly equivalent syntax, it may be more readable to use [zip]:
     *
     * ```kotlin
     * import std.iter.zip
     *
     * val a = listOf(1, 2, 3)
     * val b = listOf(2, 3, 4)
     * var zipped = zip(a.intoIter().map({ x -> x * 2 }).skip(1), b.intoIter().map({ x -> x * 2 }).skip(1))
     * check(zipped.next() == Pair(4, 6))
     * check(zipped.next() == Pair(6, 8))
     * check(zipped.next() == null)
     * ```
     *
     * compared to:
     *
     * ```kotlin
     * var zipped = (a.intoIter().map({ x -> x * 2 }).skip(1).zip(b.intoIter().map({ x -> x * 2 }).skip(1)))
     * ```
     *
     * [enumerate]: Iterator.enumerate
     * [next]: Iterator.next
     */
    public fun <U> zip(other: U): Zip<SelfType, IntoIter> = Zip.new(this, other.iterator())

    /**
     * Creates a new iterator which places a copy of `separator` between items
     * of the original iterator.
     *
     * Specifically on fused iterators, it is guaranteed that the new iterator
     * places a copy of `separator` between *adjacent* `Some(_)` items. For non-fused iterators,
     * it is guaranteed that [intersperse] will create a new iterator that places a copy
     * of `separator` between `Some(_)` items, particularly just right before the subsequent
     * `Some(_)` item.
     *
     * For example, consider the following non-fused iterator:
     *
     * ```text
     * Some(1) -> Some(2) -> None -> Some(3) -> Some(4) -> ...
     * ```
     *
     * If this non-fused iterator were to be interspersed with `0`,
     * then the interspersed iterator will produce:
     *
     * ```text
     * Some(1) -> Some(0) -> Some(2) -> None -> Some(0) -> Some(3) -> Some(0) ->
     * Some(4) -> ...
     * ```
     *
     * In case `separator` does not implement [Clone] or needs to be
     * computed every time, use [intersperseWith].
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(iterIntersperse)
     * public fun doctestEntry() {
     *     var a = listOf(0, 1, 2).intoIter().intersperse(100)
     *     check(a.next() == 0)
     *     // The first element from `a`.
     *     check(a.next() == 100)
     *     // The separator.
     *     check(a.next() == 1)
     *     // The next element from `a`.
     *     check(a.next() == 100)
     *     // The separator.
     *     check(a.next() == 2)
     *     // The last element from `a`.
     *     check(a.next() == null)
     *     // The iterator is finished.
     * }
     * ```
     *
     * `intersperse` can be very useful to join an iterator's items using a common element:
     * ```kotlin
     * @feature(iterIntersperse)
     * public fun doctestEntry() {
     *     val words = listOf("Hello", "World", "!")
     *     val hello: String = words.intoIter().intersperse(" ").joinToString("")
     *     check(hello == "Hello World !")
     * }
     * ```
     *
     * [intersperse]: Iterator.intersperse
     * [intersperseWith]: Iterator.intersperseWith
     */
    public fun intersperse(separator: Item): Intersperse<SelfType> = Intersperse.new(this, separator)

    /**
     * Creates a new iterator which places an item generated by `separator`
     * between items of the original iterator.
     *
     * Specifically on fused iterators, it is guaranteed that the new iterator
     * places an item generated by `separator` between adjacent `Some(_)` items.
     * For non-fused iterators, it is guaranteed that [intersperseWith] will
     * create a new iterator that places an item generated by `separator` between `Some(_)`
     * items, particularly just right before the subsequent `Some(_)` item.
     *
     * For example, consider the following non-fused iterator:
     *
     * ```text
     * Some(1) -> Some(2) -> None -> Some(3) -> Some(4) -> ...
     * ```
     *
     * If this non-fused iterator were to be interspersed with a `separator` closure
     * that returns `0` repeatedly, the interspersed iterator will produce:
     *
     * ```text
     * Some(1) -> Some(0) -> Some(2) -> None -> Some(0) -> Some(3) -> Some(0) ->
     * Some(4) -> ...
     * ```
     *
     * The `separator` closure will be called exactly once each time an item
     * is placed between two adjacent items from the underlying iterator;
     * specifically, the closure is not called if the underlying iterator yields
     * less than two items and after the last item is yielded.
     *
     * If the iterator's item implements [Clone], it may be easier to use
     * [intersperse].
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(iterIntersperse)
     * public fun doctestEntry() {
     *     open class NotClone(public var value: Int) {
     *         public var field0: Int
     *             get() = value
     *             set(v) {
     *                 value = v
     *             }
     *     }
     *     val v = listOf(NotClone(0), NotClone(1), NotClone(2))
     *     var it = v.intoIter().intersperseWith({ NotClone(99) })
     *     check(it.next() == NotClone(0))
     *     // The first element from `v`.
     *     check(it.next() == NotClone(99))
     *     // The separator.
     *     check(it.next() == NotClone(1))
     *     // The next element from `v`.
     *     check(it.next() == NotClone(99))
     *     // The separator.
     *     check(it.next() == NotClone(2))
     *     // The last element from `v`.
     *     check(it.next() == null)
     *     // The iterator is finished.
     * }
     * ```
     *
     * `intersperseWith` can be used in situations where the separator needs
     * to be computed:
     * ```kotlin
     * @feature(iterIntersperse)
     * public fun doctestEntry() {
     *     val src = listOf("Hello", "to", "all", "people", "!!").iter().copied()
     *     // The closure mutably borrows its context to generate an item.
     *     var happyEmojis = listOf(" ❤️ ", " 😀 ").intoIter()
     *     val separator = { (happyEmojis.next() ?: " 🦀 ") }
     *     val result = src.intersperseWith(separator).joinToString("")
     *     check(result == "Hello ❤️ to 😀 all 🦀 people 🦀 !!")
     * }
     * ```
     * [intersperse]: Iterator.intersperse
     * [intersperseWith]: Iterator.intersperseWith
     */
    public fun intersperseWith(separator: () -> Item): IntersperseWith<SelfType, () -> Item> = IntersperseWith.new(this, separator)

    /**
     * Takes a closure and creates an iterator which calls that closure on each
     * element.
     *
     * `map()` transforms one iterator into another, by means of its argument:
     * something that implements [FnMut]. It produces a new iterator which
     * calls this closure on each element of the original iterator.
     *
     * If you are good at thinking in types, you can think of `map()` like this:
     * If you have an iterator that gives you elements of some type `A`, and
     * you want an iterator of some other type `B`, you can use `map()`,
     * passing a closure that takes an `A` and returns a `B`.
     *
     * `map()` is conceptually similar to a [forLoop] loop. However, as `map()` is
     * lazy, it is best used when you're already working with other iterators.
     * If you're doing some sort of looping for a side effect, it's considered
     * more idiomatic to use [forLoop] than `map()`.
     *
     * [forLoop]: ../../book/ch03-05-control-flow.html#looping-through-a-collection-with-for
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.iter().map({ x -> 2 * x })
     * check(iter.next() == 2)
     * check(iter.next() == 4)
     * check(iter.next() == 6)
     * check(iter.next() == null)
     * ```
     *
     * If you're doing some sort of side effect, prefer [forLoop] to `map()`:
     *
     * ```kotlin
     * // don't do this:
     * (0 until 5).map({ x -> println("$x") })
     * // it won't even execute, as it is lazy. Rust will warn you about this.
     * // Instead, use a for-loop:
     * for (x in 0 until 5) {
     *     println("$x")
     * }
     * ```
     */
    public fun <B> map(f: (Item) -> B): Map<SelfType, (Item) -> B> = Map.new(this, f)

    /**
     * Calls a closure on each element of an iterator.
     *
     * This is equivalent to using a [forLoop] loop on the iterator, although
     * `break` and `continue` are not possible from a closure. It's generally
     * more idiomatic to use a `for` loop, but `forEach` may be more legible
     * when processing items at the end of longer iterator chains. In some
     * cases `forEach` may also be faster than a loop, because it will use
     * internal iteration on adapters like `Chain`.
     *
     * [forLoop]: ../../book/ch03-05-control-flow.html#looping-through-a-collection-with-for
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import std.sync.mpsc.channel
     *
     * val (tx, rx) = channel()
     * (0 until 5).map({ x -> x * 2 + 1 }).forEach({ x -> tx.send(x).unwrap() })
     * val v: Vec = rx.iter().collect()
     * check(v == mutableListOf(1, 3, 5, 7, 9))
     * ```
     *
     * For such a small example, a `for` loop may be cleaner, but `forEach`
     * might be preferable to keep a functional style with longer iterators:
     *
     * ```kotlin
     * (0 until 5).flatMap({ x -> (x * 100) until (x * 110) }).mapIndexed { index, item -> Pair(index, item) }.filter({ (i, x) -> (i + x) % 3 == 0 }).forEach({ (i, x) -> println("$i:$x") })
     * ```
     */
    public fun forEach(f: (Item) -> Unit) {
        fun <T> call(f1: (T) -> Unit): (Unit, T) -> Unit = { item -> f1(item) }
        this.fold(Unit, call(f))
    }

    /**
     * Creates an iterator which uses a closure to determine if an element
     * should be yielded.
     *
     * Given an element the closure must return `true` or `false`. The returned
     * iterator will yield only the elements for which the closure returns
     * `true`.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = intArrayOf(0, 1, 2)
     * var iter = a.filter({ x -> x.isPositive() })
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * ```
     *
     * Because the closure passed to `filter()` takes a reference, and many
     * iterators iterate over references, this leads to a possibly confusing
     * situation, where the type of the closure is a double reference:
     *
     * ```kotlin
     * val s = listOf(0, 1, 2)
     * var iter = s.filter({ x -> x > 1 })
     * // needs two *s!
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * ```
     *
     * It's common to instead use destructuring on the argument to strip away one:
     *
     * ```kotlin
     * val s = listOf(0, 1, 2)
     * var iter = s.filter({ x -> x > 1 })
     * // both & and *
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * ```
     *
     * or both:
     *
     * ```kotlin
     * val s = listOf(0, 1, 2)
     * var iter = s.filter({ x -> x > 1 })
     * // two &s
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * ```
     *
     * of these layers.
     *
     * Note that `iter.filter(f).next()` is equivalent to `iter.find(f)`.
     */
    public fun filter(predicate: (Item) -> Boolean): Filter<SelfType, (Item) -> Boolean> = Filter.new(this, predicate)

    /**
     * Creates an iterator that both filters and maps.
     *
     * The returned iterator yields only the `value`s for which the supplied
     * closure returns `Some(value)`.
     *
     * `filterMap` can be used to make chains of [filter] and [map] more
     * concise. The example below shows how a `map().filter().map()` can be
     * shortened to a single call to `filterMap`.
     *
     * [filter]: Iterator.filter
     * [map]: Iterator.map
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf("1", "two", "NaN", "four", "5")
     * var iter = a.iter().mapNotNull({ s -> s.parse().ok() })
     * check(iter.next() == 1)
     * check(iter.next() == 5)
     * check(iter.next() == null)
     * ```
     *
     * Here's the same example, but with [filter] and [map]:
     *
     * ```kotlin
     * val a = listOf("1", "two", "NaN", "four", "5")
     * var iter = a.iter().map({ s -> s.parse() }).filter({ s -> s.isOk() }).map({ s -> s.unwrap() })
     * check(iter.next() == 1)
     * check(iter.next() == 5)
     * check(iter.next() == null)
     * ```
     */
    public fun <B> filterMap(f: (Item) -> B?): FilterMap<SelfType, (Item) -> B?> = FilterMap.new(this, f)

    /**
     * Creates an iterator which gives the current iteration count as well as
     * the next value.
     *
     * The iterator returned yields pairs `(i, val)`, where `i` is the
     * current index of iteration and `val` is the value returned by the
     * iterator.
     *
     * `enumerate()` keeps its count as a [usize]. If you want to count by a
     * different sized integer, the [zip] function provides similar
     * functionality.
     *
     * # Overflow Behavior
     *
     * The method does no guarding against overflows, so enumerating more than
     * [usize.Max] elements either produces the wrong result or panics. If
     * overflow checks are enabled, a panic is guaranteed.
     *
     * # Panics
     *
     * The returned iterator might panic if the to-be-returned index would
     * overflow a [usize].
     *
     * [zip]: Iterator.zip
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf('a', 'b', 'c')
     * var iter = a.intoIter().mapIndexed { index, item -> Pair(index, item) }
     * check(iter.next() == Pair(0, 'a'))
     * check(iter.next() == Pair(1, 'b'))
     * check(iter.next() == Pair(2, 'c'))
     * check(iter.next() == null)
     * ```
     */
    public fun enumerate(): Enumerate<SelfType> = Enumerate.new(this)

    /**
     * Creates an iterator which can use the [peek] and [peekMut] methods
     * to look at the next element of the iterator without consuming it. See
     * their documentation for more information.
     *
     * Note that the underlying iterator is still advanced when [peek] or
     * [peekMut] are called for the first time: In order to retrieve the
     * next element, [next] is called on the underlying iterator, hence any
     * side effects (i.e. anything other than fetching the next value) of
     * the [next] method will occur.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val xs = listOf(1, 2, 3)
     * var iter = xs.intoIter().peekable()
     * // peek() lets us see into the future
     * check(iter.peek() == 1)
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * // we can peek() multiple times, the iterator won't advance
     * check(iter.peek() == 3)
     * check(iter.peek() == 3)
     * check(iter.next() == 3)
     * // after the iterator is finished, so is peek()
     * check(iter.peek() == null)
     * check(iter.next() == null)
     * ```
     *
     * Using [peekMut] to mutate the next item without advancing the
     * iterator:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
     *
     * val xs = listOf(1, 2, 3)
     * var iter = xs.intoIter().peekable()
     * // `peekMut()` lets us see into the future
     * check(iter.peekMut() == RustMutableReference(allocateNativeCell(1, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
     * check(iter.peekMut() == RustMutableReference(allocateNativeCell(1, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
     * check(iter.next() == 1)
     * when (val matchVal = iter.peekMut()) {
     *     null -> {}
     *     else -> {
     *         val p = matchVal
     *         check(p == 2)
     *         // put a value into the iterator
     *         p = 1000
     *     }
     * }
     * // The value reappears as the iterator continues
     * check(iter.collect<Vec>() == mutableListOf(1000, 3))
     * ```
     * [peek]: Peekable.peek
     * [peekMut]: Peekable.peekMut
     * [next]: Iterator.next
     */
    public fun peekable(): Peekable<SelfType> = Peekable.new(this)

    /**
     * Creates an iterator that [skip]s elements based on a predicate.
     *
     * [skip]: Iterator.skip
     *
     * `skipWhile()` takes a closure as an argument. It will call this
     * closure on each element of the iterator, and ignore elements
     * until it returns `false`.
     *
     * After `false` is returned, `skipWhile()`s job is over, and the
     * rest of the elements are yielded.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = intArrayOf(-1, 0, 1)
     * var iter = ArrayDeque((a).toList()).skipWhile({ x -> x.isNegative() })
     * check(iter.removeFirstOrNull() == 0)
     * check(iter.removeFirstOrNull() == 1)
     * check(iter.removeFirstOrNull() == null)
     * ```
     *
     * Because the closure passed to `skipWhile()` takes a reference, and many
     * iterators iterate over references, this leads to a possibly confusing
     * situation, where the type of the closure argument is a double reference:
     *
     * ```kotlin
     * val s = listOf(-1, 0, 1)
     * var iter = s.iter().skipWhile({ x -> x < 0 })
     * // need two *s!
     * check(iter.next() == 0)
     * check(iter.next() == 1)
     * check(iter.next() == null)
     * ```
     *
     * Stopping after an initial `false`:
     *
     * ```kotlin
     * val a = listOf(-1, 0, 1, -2)
     * var iter = a.intoIter().skipWhile({ x -> x < 0 })
     * check(iter.next() == 0)
     * check(iter.next() == 1)
     * // while this would have been false, since we already got a false,
     * // skipWhile() isn't used any more
     * check(iter.next() == -2)
     * check(iter.next() == null)
     * ```
     */
    public fun skipWhile(predicate: (Item) -> Boolean): SkipWhile<SelfType, (Item) -> Boolean> = SkipWhile.new(this, predicate)

    /**
     * Creates an iterator that yields elements based on a predicate.
     *
     * `takeWhile()` takes a closure as an argument. It will call this
     * closure on each element of the iterator, and yield elements
     * while it returns `true`.
     *
     * After `false` is returned, `takeWhile()`s job is over, and the
     * rest of the elements are ignored.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = intArrayOf(-1, 0, 1)
     * var iter = ArrayDeque((a).toList()).takeWhile({ x -> x.isNegative() })
     * check(iter.removeFirstOrNull() == -1)
     * check(iter.removeFirstOrNull() == null)
     * ```
     *
     * Because the closure passed to `takeWhile()` takes a reference, and many
     * iterators iterate over references, this leads to a possibly confusing
     * situation, where the type of the closure is a double reference:
     *
     * ```kotlin
     * val s = listOf(-1, 0, 1)
     * var iter = s.iter().takeWhile({ x -> x < 0 })
     * // need two *s!
     * check(iter.next() == -1)
     * check(iter.next() == null)
     * ```
     *
     * Stopping after an initial `false`:
     *
     * ```kotlin
     * val a = listOf(-1, 0, 1, -2)
     * var iter = a.intoIter().takeWhile({ x -> x < 0 })
     * check(iter.next() == -1)
     * // We have more elements that are less than zero, but since we already
     * // got a false, takeWhile() ignores the remaining elements.
     * check(iter.next() == null)
     * ```
     *
     * Because `takeWhile()` needs to look at the value in order to see if it
     * should be included or not, consuming iterators will see that it is
     * removed:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3, 4)
     * var iter = a.intoIter()
     * val result: List<Int> = iter.byRef().takeWhile({ n -> n != 3 }).toMutableList()
     * check(result == listOf(1, 2))
     * val result1: List<Int> = iter.toMutableList()
     * check(result1 == listOf(4))
     * ```
     *
     * The `3` is no longer there, because it was consumed in order to see if
     * the iteration should stop, but wasn't placed back into the iterator.
     */
    public fun takeWhile(predicate: (Item) -> Boolean): TakeWhile<SelfType, (Item) -> Boolean> = TakeWhile.new(this, predicate)

    /**
     * Creates an iterator that both yields elements based on a predicate and maps.
     *
     * `mapWhile()` takes a closure as an argument. It will call this
     * closure on each element of the iterator, and yield elements
     * while it returns [`Some`][Some].
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = intArrayOf(-1, 4, 0, 1)
     * var iter = ArrayDeque((a).toList()).mapWhile({ x -> 16.checkedDiv(x) })
     * check(iter.removeFirstOrNull() == -16)
     * check(iter.removeFirstOrNull() == 4)
     * check(iter.removeFirstOrNull() == null)
     * ```
     *
     * Here's the same example, but with [takeWhile] and [map]:
     *
     * [takeWhile]: Iterator.takeWhile
     * [map]: Iterator.map
     *
     * ```kotlin
     * val a = intArrayOf(-1, 4, 0, 1)
     * var iter = a.map({ x -> 16.checkedDiv(x) }).takeWhile({ x -> x != null }).map({ x -> x.unwrap() })
     * check(iter.next() == -16)
     * check(iter.next() == 4)
     * check(iter.next() == null)
     * ```
     *
     * Stopping after an initial [None]:
     *
     * ```kotlin
     * val a = listOf(0, 1, 2, -3, 4, 5, -6)
     * val iter = a.intoIter().mapWhile({ x -> UInt.tryFrom(x).ok() })
     * val vec: Vec = iter.collect()
     * // We have more elements that could fit in u32 (such as 4, 5), but `mapWhile` returned `None` for `- 3`
     * // (as the `predicate` returned `None`) and `collect` stops at the first `None` encountered.
     * check(vec == listOf(0, 1, 2))
     * ```
     *
     * Because `mapWhile()` needs to look at the value in order to see if it
     * should be included or not, consuming iterators will see that it is
     * removed:
     *
     * ```kotlin
     * val a = listOf(1, 2, -3, 4)
     * var iter = a.intoIter()
     * val result: List<UInt> = iter.byRef().mapWhile({ n -> UInt.tryFrom(n).ok() }).toMutableList()
     * check(result == listOf(1u, 2u))
     * val result1: List<Int> = iter.toMutableList()
     * check(result1 == listOf(4))
     * ```
     *
     * The `- 3` is no longer there, because it was consumed in order to see if
     * the iteration should stop, but wasn't placed back into the iterator.
     *
     * Note that unlike [takeWhile] this iterator is **not** fused.
     * It is also not specified what this iterator returns after the first [None] is returned.
     * If you need a fused iterator, use [fuse].
     *
     * [fuse]: Iterator.fuse
     */
    public fun <B> mapWhile(predicate: (Item) -> B?): MapWhile<SelfType, (Item) -> B?> = MapWhile.new(this, predicate)

    /**
     * Creates an iterator that skips the first `n` elements.
     *
     * `skip(n)` skips elements until `n` elements are skipped or the end of the
     * iterator is reached (whichever happens first). After that, all the remaining
     * elements are yielded. In particular, if the original iterator is too short,
     * then the returned iterator is empty.
     *
     * Rather than overriding this method directly, instead override the `nth` method.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter().skip(2)
     * check(iter.next() == 3)
     * check(iter.next() == null)
     * ```
     */
    public fun skip(n: Int): Skip<SelfType> = Skip.new(this, n)

    /**
     * Creates an iterator that yields the first `n` elements, or fewer
     * if the underlying iterator ends sooner.
     *
     * `take(n)` yields elements until `n` elements are yielded or the end of
     * the iterator is reached (whichever happens first).
     * The returned iterator is a prefix of length `n` if the original iterator
     * contains at least `n` elements, otherwise it contains all of the
     * (fewer than `n`) elements of the original iterator.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter().take(2)
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * ```
     *
     * `take()` is often used with an infinite iterator, to make it finite:
     *
     * ```kotlin
     * var iter = (0 until Int.MAX_VALUE).take(3)
     * check(iter.next() == 0)
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * ```
     *
     * If less than `n` elements are available,
     * `take` will limit itself to the size of the underlying iterator:
     *
     * ```kotlin
     * val v = listOf(1, 2)
     * var iter = v.intoIter().take(5)
     * check(iter.next() == 1)
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * ```
     *
     * Use [byRef] to take from the iterator without consuming it, and then
     * continue using the original iterator:
     *
     * ```kotlin
     * var words = listOf("hello", "world", "of", "Rust").intoIter()
     * // Take the first two words.
     * val helloWorld: Vec = words.byRef().take(2).collect()
     * check(helloWorld == mutableListOf("hello", "world"))
     * // Collect the rest of the words.
     * // We can only do this because we used `byRef` earlier.
     * val ofRust: Vec = words.collect()
     * check(ofRust == mutableListOf("of", "Rust"))
     * ```
     *
     * [byRef]: Iterator.byRef
     */
    public fun take(n: Int): Take<SelfType> = Take.new(this, n)

    /**
     * An iterator adapter which, like [fold], holds internal state, but
     * unlike [fold], produces a new iterator.
     *
     * [fold]: Iterator.fold
     *
     * `scan()` takes two arguments: an initial value which seeds the internal
     * state, and a closure with two arguments, the first being a mutable
     * reference to the internal state and the second an iterator element.
     * The closure can assign to the internal state to share state between
     * iterations.
     *
     * On iteration, the closure will be applied to each element of the
     * iterator and the return value from the closure, an [Option], is
     * returned by the `next` method. Thus the closure can return
     * `Some(value)` to yield `value`, or `None` to end the iteration.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3, 4)
     * var iter = a.intoIter().scan(1, closure@{ state, x ->
     *     // each iteration, we'll multiply the state by the element ...
     *     state = state * x
     *     // ... and terminate if the state exceeds 6
     *     if (state > 6) {
     *         return@closure null
     *     }
     *     // ... else yield the negation of the state
     *     -state
     * })
     * check(iter.next() == -1)
     * check(iter.next() == -2)
     * check(iter.next() == -6)
     * check(iter.next() == null)
     * ```
     */
    public fun <St, B> scan(initialState: St, f: (St, Item) -> B?): Scan<SelfType, St, (St, Item) -> B?> = Scan.new(this, initialState, f)

    /**
     * Creates an iterator that works like map, but flattens nested structure.
     *
     * The [map] adapter is very useful, but only when the closure
     * argument produces values. If it produces an iterator instead, there's
     * an extra layer of indirection. `flatMap()` will remove this extra layer
     * on its own.
     *
     * You can think of `flatMap(f)` as the semantic equivalent
     * of [map]ping, and then [flatten]ing as in `map(f).flatten()`.
     *
     * Another way of thinking about `flatMap()`: [map]s closure returns
     * one item for each element, and `flatMap()`s closure returns an
     * iterator for each element.
     *
     * [map]: Iterator.map
     * [flatten]: Iterator.flatten
     *
     * # Examples
     *
     * ```kotlin
     * val words = listOf("alpha", "beta", "gamma")
     * // chars() returns an iterator
     * val merged: String = words.iter().flatMap({ s -> s.chars() }).joinToString("")
     * check(merged == "alphabetagamma")
     * ```
     */
    public fun <U> flatMap(f: (Item) -> U): FlatMap<SelfType, U, (Item) -> U> = FlatMap.new(this, f)

    /**
     * Creates an iterator that flattens nested structure.
     *
     * This is useful when you have an iterator of iterators or an iterator of
     * things that can be turned into iterators and you want to remove one
     * level of indirection.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val data = mutableListOf(mutableListOf(1, 2, 3, 4), mutableListOf(5, 6))
     * val flattened: Vec = ArrayDeque(data).flatten().collect()
     * check(flattened == listOf(1, 2, 3, 4, 5, 6))
     * ```
     *
     * Mapping and then flattening:
     *
     * ```kotlin
     * val words = listOf("alpha", "beta", "gamma")
     * // chars() returns an iterator
     * val merged: String = words.iter().map({ s -> s.chars() }).flatten().joinToString("")
     * check(merged == "alphabetagamma")
     * ```
     *
     * You can also rewrite this in terms of [flatMap], which is preferable
     * in this case since it conveys intent more clearly:
     *
     * ```kotlin
     * val words = listOf("alpha", "beta", "gamma")
     * // chars() returns an iterator
     * val merged: String = words.iter().flatMap({ s -> s.chars() }).joinToString("")
     * check(merged == "alphabetagamma")
     * ```
     *
     * Flattening works on any `IntoIterator` type, including `Option` and `Result`:
     *
     * ```kotlin
     * val options = mutableListOf(123, 321, null, 231)
     * val flattenedOptions: Vec = ArrayDeque(options).flatten().collect()
     * check(flattenedOptions == listOf(123, 321, 231))
     * val results = mutableListOf(Result.success(123), Result.success(321), Result.failure(456), Result.success(231))
     * val flattenedResults: Vec = ArrayDeque(results).flatten().collect()
     * check(flattenedResults == listOf(123, 321, 231))
     * ```
     *
     * Flattening only removes one level of nesting at a time:
     *
     * ```kotlin
     * val d3 = listOf(listOf(listOf(1, 2), listOf(3, 4)), listOf(listOf(5, 6), listOf(7, 8)))
     * val d2: Vec = d3.intoIter().flatten().collect()
     * check(d2 == listOf(listOf(1, 2), listOf(3, 4), listOf(5, 6), listOf(7, 8)))
     * val d1: Vec = d3.intoIter().flatten().flatten().collect()
     * check(d1 == listOf(1, 2, 3, 4, 5, 6, 7, 8))
     * ```
     *
     * Here we see that `flatten()` does not perform a "deep" flatten.
     * Instead, only one level of nesting is removed. That is, if you
     * `flatten()` a three-dimensional array, the result will be
     * two-dimensional and not one-dimensional. To get a one-dimensional
     * structure, you have to `flatten()` again.
     *
     * [flatMap]: Iterator.flatMap
     */
    public fun flatten(): Flatten<SelfType> = Flatten.new(this)

    /**
     * Calls the given function `f` for each contiguous window of size `N` over
     * `self` and returns an iterator over the outputs of `f`. Like [slice.windows],
     * the windows during mapping overlap as well.
     *
     * In the following example, the closure is called three times with the
     * arguments `[`a`, `b`]`, `[`b`, `c`]` and `[`c`, `d`]` respectively.
     *
     * ```kotlin
     * @feature(iterMapWindows)
     * public fun doctestEntry() {
     *     val strings = "abcd".chars().mapWindows({ param0 -> "$x+$y" }).toMutableList()
     *     check(strings == mutableListOf("a+b", "b+c", "c+d"))
     * }
     * ```
     *
     * Note that the const parameter `N` is usually inferred by the
     * destructured argument in the closure.
     *
     * The returned iterator yields 𝑘 − `N` + 1 items (where 𝑘 is the number of
     * items yielded by `self`). If 𝑘 is less than `N`, this method yields an
     * empty iterator.
     *
     * [slice.windows]: slice.windows
     *
     * # Panics
     *
     * Panics if `N` is zero.
     *
     * # Examples
     *
     * Building the sums of neighboring numbers.
     *
     * ```kotlin
     * @feature(iterMapWindows)
     * public fun doctestEntry() {
     *     var it = listOf(1, 3, 8, 1).iter().mapWindows({ param0 -> a + b })
     *     check(it.next() == 4)
     *     // 1 + 3
     *     check(it.next() == 11)
     *     // 3 + 8
     *     check(it.next() == 9)
     *     // 8 + 1
     *     check(it.next() == null)
     * }
     * ```
     *
     * Since the elements in the following example implement `Copy`, we can
     * just copy the array and get an iterator over the windows.
     *
     * ```kotlin
     * @feature(iterMapWindows)
     * public fun doctestEntry() {
     *     var it = "ferris".chars().mapWindows({ w: List<Any> -> w })
     *     check(it.next() == listOf('f', 'e', 'r'))
     *     check(it.next() == listOf('e', 'r', 'r'))
     *     check(it.next() == listOf('r', 'r', 'i'))
     *     check(it.next() == listOf('r', 'i', 's'))
     *     check(it.next() == null)
     * }
     * ```
     *
     * You can also use this function to check the sortedness of an iterator.
     * For the simple case, rather use [Iterator.isSorted].
     *
     * ```kotlin
     * @feature(iterMapWindows)
     * public fun doctestEntry() {
     *     var it = floatArrayOf(0.5f, 1.0f, 3.5f, 3.0f, 8.5f, 8.5f, Float.NaN).iter().mapWindows({ param0 -> a <= b })
     *     check(it.next() == true)
     *     // 0.5 <= 1.0
     *     check(it.next() == true)
     *     // 1.0 <= 3.5
     *     check(it.next() == false)
     *     // 3.5 <= 3.0
     *     check(it.next() == true)
     *     // 3.0 <= 8.5
     *     check(it.next() == true)
     *     // 8.5 <= 8.5
     *     check(it.next() == false)
     *     // 8.5 <= NAN
     *     check(it.next() == null)
     * }
     * ```
     *
     * For non-fused iterators, the window is reset after `None` is yielded.
     *
     * ```kotlin
     * @feature(iterMapWindows)
     * public fun doctestEntry() {
     *     open class NonFusedIterator(public var state: Int) : Iterable<Int> {
     *         override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
     *             override fun computeNext() {
     *                 when (val v = this@NonFusedIterator.next()) {
     *                     null -> done()
     *                     else -> setNext(v)
     *                 }
     *             }
     *         }
     *
     *         public open fun next(): Int? {
     *             val value = this.state
     *             this.state = this.state + 1
     *             // Skip every 5th number
     *             return if ((value + 1) % 5 == 0) {
     *                 null
     *             } else {
     *                 value
     *             }
     *         }
     *     }
     *     var iter = NonFusedIterator.default()
     *     check(iter.next() == 0)
     *     check(iter.next() == 1)
     *     check(iter.next() == 2)
     *     check(iter.next() == 3)
     *     check(iter.next() == null)
     *     check(iter.next() == 5)
     *     check(iter.next() == 6)
     *     check(iter.next() == 7)
     *     check(iter.next() == 8)
     *     check(iter.next() == null)
     *     check(iter.next() == 10)
     *     check(iter.next() == 11)
     *     var iter1 = NonFusedIterator.default().mapWindows({ arr: List<Any> -> arr })
     *     check(iter1.next() == listOf(0, 1))
     *     check(iter1.next() == listOf(1, 2))
     *     check(iter1.next() == listOf(2, 3))
     *     check(iter1.next() == null)
     *     check(iter1.next() == listOf(5, 6))
     *     check(iter1.next() == listOf(6, 7))
     *     check(iter1.next() == listOf(7, 8))
     *     check(iter1.next() == null)
     *     check(iter1.next() == listOf(10, 11))
     *     check(iter1.next() == listOf(11, 12))
     *     check(iter1.next() == listOf(12, 13))
     *     check(iter1.next() == null)
     * }
     * ```
     */
    public fun <R> mapWindows(f: (List<Item>) -> R, n: Int): MapWindows<SelfType, (List<Item>) -> R> = MapWindows.new(this, f, n)

    /**
     * Creates an iterator which ends after the first [None].
     *
     * After an iterator returns [None], future calls may or may not yield
     * [Some] again. `fuse()` adapts an iterator, ensuring that after a
     * [None] is given, it will always return [None] forever.
     *
     * Note that the [Fuse] wrapper is a no-op on iterators that implement
     * the [FusedIterator] trait. `fuse()` may therefore behave incorrectly
     * if the [FusedIterator] trait is improperly implemented.
     *
     * [Some]: Some
     *
     * # Examples
     *
     * ```kotlin
     * // an iterator which alternates between Some and None
     * open class Alternate(public var state: Int) : Iterable<Int> {
     *     override fun iterator(): Iterator<Int> = object : AbstractIterator<Int>() {
     *         override fun computeNext() {
     *             when (val v = this@Alternate.next()) {
     *                 null -> done()
     *                 else -> setNext(v)
     *             }
     *         }
     *     }
     *
     *     public open fun next(): Int? {
     *         val value = this.state
     *         this.state = this.state + 1
     *         // if it's even, Some(i32), else None
     *         return (value % 2 == 0).thenSome(value)
     *     }
     * }
     * var iter = Alternate(state = 0)
     * // we can see our iterator going back and forth
     * check(iter.next() == 0)
     * check(iter.next() == null)
     * check(iter.next() == 2)
     * check(iter.next() == null)
     * // however, once we fuse it...
     * var iter1 = iter.fuse()
     * check(iter1.next() == 4)
     * check(iter1.next() == null)
     * // it will always return `None` after the first time.
     * check(iter1.next() == null)
     * check(iter1.next() == null)
     * check(iter1.next() == null)
     * ```
     */
    public fun fuse(): Fuse<SelfType> = Fuse.new(this)

    /**
     * Does something with each element of an iterator, passing the value on.
     *
     * When using iterators, you'll often chain several of them together.
     * While working on such code, you might want to check out what's
     * happening at various parts in the pipeline. To do that, insert
     * a call to `inspect()`.
     *
     * It's more common for `inspect()` to be used as a debugging tool than to
     * exist in your final code, but applications may find it useful in certain
     * situations when errors need to be logged before being discarded.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 4, 2, 3)
     * // this iterator sequence is complex.
     * val sum = a.iter().cloned().filter({ x -> x % 2 == 0 }).fold(0, { sum, i -> sum + i })
     * println("$sum")
     * // let's add some inspect() calls to investigate what's happening
     * val sum1 = a.iter().cloned().inspect({ x -> println("about to filter: $x") }).filter({ x -> x % 2 == 0 }).inspect({ x -> println("made it through filter: $x") }).fold(0, { sum1, i -> sum1 + i })
     * println("$sum")
     * ```
     *
     * This will print:
     *
     * ```text
     * 6
     * about to filter: 1
     * about to filter: 4
     * made it through filter: 4
     * about to filter: 2
     * made it through filter: 2
     * about to filter: 3
     * 6
     * ```
     *
     * Logging errors before discarding them:
     *
     * ```kotlin
     * val lines = listOf("1", "2", "a")
     * val sum: Int = lines.iter().map({ line -> (line.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))) }).inspect({ num ->
     *     when (val matchVal = num) {
     *         is Err -> {
     *             val (e) = matchVal
     *             println("Parsing error: $e")
     *         }
     *         else -> {}
     *     }
     * }).mapNotNull(Result.ok).sum()
     * println("Sum: $sum")
     * ```
     *
     * This will print:
     *
     * ```text
     * Parsing error: invalid digit found in string
     * Sum: 3
     * ```
     */
    public fun inspect(f: (Item) -> Unit): Inspect<SelfType, (Item) -> Unit> = Inspect.new(this, f)

    /**
     * Creates a "by reference" adapter for this instance of `Iterator`.
     *
     * Consuming method calls (direct or indirect calls to `next`)
     * on the "by reference" adapter will consume the original iterator,
     * but ownership-taking methods (those with a `self` parameter)
     * only take ownership of the "by reference" iterator.
     *
     * This is useful for applying ownership-taking methods
     * (such as `take` in the example below)
     * without giving up ownership of the original iterator,
     * so you can use the original iterator afterwards.
     *
     * Uses [`impl.I.Iterator.Sized.Iterator.forLoop.mut.I`](Iterator#impl-Iterator-for-%26mut+I).
     *
     * # Examples
     *
     * ```kotlin
     * var words = listOf("hello", "world", "of", "Rust").intoIter()
     * // Take the first two words.
     * val helloWorld: Vec = words.byRef().take(2).collect()
     * check(helloWorld == mutableListOf("hello", "world"))
     * // Collect the rest of the words.
     * // We can only do this because we used `byRef` earlier.
     * val ofRust: Vec = words.collect()
     * check(ofRust == mutableListOf("of", "Rust"))
     * ```
     */
    public fun byRef(): SelfType = this

    /**
     * Transforms an iterator into a collection.
     *
     * `collect()` takes ownership of an iterator and produces whichever
     * collection type you request. The iterator itself carries no knowledge of
     * the eventual container; the target collection is chosen entirely by the
     * type you ask `collect()` to return. This makes `collect()` one of the
     * more powerful methods in the standard library, and it shows up in a wide
     * variety of contexts.
     *
     * The most basic pattern in which `collect()` is used is to turn one
     * collection into another. You take a collection, call [iter] on it,
     * do a bunch of transformations, and then `collect()` at the end.
     *
     * `collect()` can also create instances of types that are not typical
     * collections. For example, a [String] can be built from [char]s,
     * and an iterator of [`Result.T.E`][Result] items can be collected
     * into `Result<Collection<T>, E>`. See the examples below for more.
     *
     * Because `collect()` is so general, it can cause problems with type
     * inference. As such, `collect()` is one of the few times you'll see
     * the syntax affectionately known as the `turbofish`: `<>`. This
     * helps the inference algorithm understand specifically which collection
     * you're trying to collect into.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * val doubled: List<Int> = a.iter().map({ x -> x * 2 }).toMutableList()
     * check(mutableListOf(2, 4, 6) == doubled)
     * ```
     *
     * Note that we needed the `: List<Int>` on the left-hand side. This is because
     * we could collect into, for example, a [VecDeque.T] instead:
     *
     * [VecDeque.T]: ../../std/collections/struct.VecDeque.html
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * val doubled: VecDeque<Int> = a.iter().map({ x -> x * 2 }).collect()
     * check(2 == doubled[0])
     * check(4 == doubled[1])
     * check(6 == doubled[2])
     * ```
     *
     * Using the `turbofish` instead of annotating `doubled`:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * val doubled = a.iter().map({ x -> x * 2 }).toMutableList()
     * check(mutableListOf(2, 4, 6) == doubled)
     * ```
     *
     * Because `collect()` only cares about what you're collecting into, you can
     * still use a partial type hint, `_`, with the turbofish:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * val doubled = a.iter().map({ x -> x * 2 }).collect<Vec>()
     * check(mutableListOf(2, 4, 6) == doubled)
     * ```
     *
     * Using `collect()` to make a [String]:
     *
     * ```kotlin
     * val chars = listOf('g', 'd', 'k', 'k', 'n')
     * val hello: String = chars.intoIter().map({ x -> x.toUByte() }).map({ x -> (x + 1).toInt().toChar() }).joinToString("")
     * check("hello" == hello)
     * ```
     *
     * If you have a list of [`Result.T.E`][Result]s, you can use `collect()` to
     * see if any of them failed:
     *
     * ```kotlin
     * val results = listOf(Result.success(1), Result.failure("nope"), Result.success(3), Result.failure("bad"))
     * val result: Result<Vec> = results.intoIter().collect()
     * // gives us the first error
     * check(Result.failure("nope") == result)
     * val results1 = listOf(Result.success(1), Result.success(3))
     * val result1: Result<Vec> = results1.intoIter().collect()
     * // gives us the list of answers
     * check(Result.success(mutableListOf(1, 3)) == result1)
     * ```
     *
     * [iter]: Iterator.next
     * [String]: ../../std/string/struct.String.html
     * [char]: type@char
     */
    public fun <B> collect(): B {
        // This is too aggressive to turn on for everything all the time, but PR#137908
        // accidentally noticed that some rustc iterators had malformed `sizeHint`s,
        // so this will help catch such things in debug-assertions-std runners,
        // even if users won't actually ever see it.
        if (false) {
            val hint = this.sizeHint()
            check(hint.second.isNoneOr({ high -> high >= hint.first })) { "Malformed size_hint $hint" }
        }
        return FromIterator.fromIter(this)
    }

    /**
     * Fallibly transforms an iterator into a collection, short circuiting if
     * a failure is encountered.
     *
     * `tryCollect()` is a variation of [`collect`][collect] that allows fallible
     * conversions during collection. Its main use case is simplifying conversions from
     * iterators yielding [`Option.T`][Option] into `Option<Collection<T>>`, or similarly for other [Try]
     * types (e.g. [Result]).
     *
     * Importantly, `tryCollect()` doesn't require that the outer [Try] type also implements [FromIterator];
     * only the inner type produced on `Try.Output` must implement it. Concretely,
     * this means that collecting into `ControlFlow<_, List<Int>>` is valid because `List<Int>` implements
     * [FromIterator], even though [ControlFlow] doesn't.
     *
     * Also, if a failure is encountered during `tryCollect()`, the iterator is still valid and
     * may continue to be used, in which case it will continue iterating starting after the element that
     * triggered the failure. See the last example below for an example of how this works.
     *
     * # Examples
     * Successfully collecting an iterator of `Option<Int>` into `Option<List<Int>>`:
     * ```kotlin
     * @feature(iteratorTryCollect)
     * public fun doctestEntry() {
     *     val u = mutableListOf(1, 2, 3)
     *     val v = ArrayDeque(u).tryCollect<List<Int>>()
     *     check(v == mutableListOf(1, 2, 3))
     * }
     * ```
     *
     * Failing to collect in the same way:
     * ```kotlin
     * @feature(iteratorTryCollect)
     * public fun doctestEntry() {
     *     val u = mutableListOf(1, 2, null, 3)
     *     val v = ArrayDeque(u).tryCollect<List<Int>>()
     *     check(v == null)
     * }
     * ```
     *
     * A similar example, but with `Result`:
     * ```kotlin
     * @feature(iteratorTryCollect)
     * public fun doctestEntry() {
     *     val u: List<Result<Int>> = mutableListOf(Result.success(1), Result.success(2), Result.success(3))
     *     val v = ArrayDeque(u).tryCollect<List<Int>>()
     *     check(v == Result.success(mutableListOf(1, 2, 3)))
     *     val u1 = mutableListOf(Result.success(1), Result.success(2), Result.failure(Unit), Result.success(3))
     *     val v1 = ArrayDeque(u1).tryCollect<List<Int>>()
     *     check(v1 == Result.failure(Unit))
     * }
     * ```
     *
     * Finally, even [ControlFlow] works, despite the fact that it
     * doesn't implement [FromIterator]. Note also that the iterator can
     * continue to be used, even if a failure is encountered:
     *
     * ```kotlin
     * import core.ops.controlflow.Break
     * import core.ops.controlflow.Continue
     *
     * @feature(iteratorTryCollect)
     * public fun doctestEntry() {
     *     val u = listOf(Continue(1), Continue(2), Break(3), Continue(4), Continue(5))
     *     var it = u.intoIter()
     *     val v = it.tryCollect<Vec>()
     *     check(v == Break(3))
     *     val v1 = it.tryCollect<Vec>()
     *     check(v1 == Continue(mutableListOf(4, 5)))
     * }
     * ```
     *
     * [collect]: Iterator.collect
     */
    public fun <Output, Residual, B> tryCollect(): ChangeOutputType<Item, B> = tryProcess(ByRefSized(this), { i -> i.collect() })

    /**
     * Collects all the items from an iterator into a collection.
     *
     * This method consumes the iterator and adds all its items to the
     * passed collection. The collection is then returned, so the call chain
     * can be continued.
     *
     * This is useful when you already have a collection and want to add
     * the iterator items to it.
     *
     * This method is a convenience method to call [Extend.extend](trait.Extend.html),
     * but instead of being called on a collection, it's called on an iterator.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(iterCollectInto)
     * public fun doctestEntry() {
     *     val a = listOf(1, 2, 3)
     *     var vec: List<Int> = mutableListOf(0, 1)
     *     a.iter().map({ x -> x * 2 }).collectInto(vec)
     *     a.iter().map({ x -> x * 10 }).collectInto(vec)
     *     check(vec == mutableListOf(0, 1, 2, 4, 6, 10, 20, 30))
     * }
     * ```
     *
     * `List` can have a manual set capacity to avoid reallocating it:
     *
     * ```kotlin
     * @feature(iterCollectInto)
     * public fun doctestEntry() {
     *     val a = listOf(1, 2, 3)
     *     var vec: List<Int> = ArrayList(6)
     *     a.iter().map({ x -> x * 2 }).collectInto(vec)
     *     a.iter().map({ x -> x * 10 }).collectInto(vec)
     *     check(6 == vec.capacity())
     *     check(vec == mutableListOf(2, 4, 6, 10, 20, 30))
     * }
     * ```
     *
     * The returned mutable reference can be used to continue the call chain:
     *
     * ```kotlin
     * @feature(iterCollectInto)
     * public fun doctestEntry() {
     *     val a = listOf(1, 2, 3)
     *     var vec: List<Int> = ArrayList(6)
     *     val count = a.iter().collectInto(vec).iter().count()
     *     check(count == vec.size)
     *     check(vec == mutableListOf(1, 2, 3))
     *     val count1 = a.iter().collectInto(vec).iter().count()
     *     check(count1 == vec.size)
     *     check(vec == mutableListOf(1, 2, 3, 1, 2, 3))
     * }
     * ```
     */
    public fun <E> collectInto(collection: E): E {
        collection.extend(this)
        return collection
    }

    /**
     * Consumes an iterator, creating two collections from it.
     *
     * The predicate passed to `partition()` can return `true`, or `false`.
     * `partition()` returns a pair, all of the elements for which it returned
     * `true`, and all of the elements for which it returned `false`.
     *
     * See also [isPartitioned] and [partitionInPlace].
     *
     * [isPartitioned]: Iterator.isPartitioned
     * [partitionInPlace]: Iterator.partitionInPlace
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * val (even: Vec, odd: Vec) = a.intoIter().partition({ n -> n % 2 == 0 })
     * check(even == listOf(2))
     * check(odd == listOf(1, 3))
     * ```
     */
    public fun <B> partition(f: (Item) -> Boolean, defaultBFn: () -> B): Pair<B, B>

    /**
     * Reorders the elements of this iterator *in-place* according to the given predicate,
     * such that all those that return `true` precede all those that return `false`.
     * Returns the number of `true` elements found.
     *
     * The relative order of partitioned items is not maintained.
     *
     * # Current implementation
     *
     * The current algorithm tries to find the first element for which the predicate evaluates
     * to false and the last element for which it evaluates to true, and repeatedly swaps them.
     *
     * Time complexity: *O*(*n*)
     *
     * See also [isPartitioned] and [partition].
     *
     * [isPartitioned]: Iterator.isPartitioned
     * [partition]: Iterator.partition
     *
     * # Examples
     *
     * ```kotlin
     * @feature(iterPartitionInPlace)
     * public fun doctestEntry() {
     *     var a = mutableListOf(1, 2, 3, 4, 5, 6, 7)
     *     // Partition in-place between evens and odds
     *     val i = ArrayDeque(a).partitionInPlace({ n -> n % 2 == 0 })
     *     check(i == 3)
     *     check(a[0 until i].all({ n -> n % 2 == 0 }))
     *     // evens
     *     check(a[i until a.size].all({ n -> n % 2 == 1 }))
     *     // odds
     * }
     * ```
     */
    public fun <T> partitionInPlace(predicate: (T) -> Boolean): Int {
        // Note: should we worry about the count overflowing? The only way to have more than
        // `Int.MAX` mutable references is with ZSTs, which aren't useful to partition...
        // These closure "factory" functions exist to avoid genericity in `Self`.
        fun <T> isFalse(predicate1: (T) -> Boolean, trueCount: RustMutableReference<Int>): (T) -> Boolean {
            val trueCount1 = trueCount
            return { x ->
                val p = predicate1(x)
                (trueCount1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (p.toInt()); targetPlace.write(currentValue) }
                !p
            }
        }
        fun <T> isTrue(predicate1: (T) -> Boolean): (T) -> Boolean = { x -> predicate1(x) }
        // Repeatedly find the first `false` and swap it with the last `true`.
        val localStorageL2372C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        var trueCount by localStorageL2372C8
        while (true) {
            val matchVal = this.indexOf(isFalse(predicate, RustMutableReference(localStorageL2372C8.asMutPtr(), localStorageL2372C8.layout))).takeIf { it >= 0 }
            if (matchVal == null) break
            val head = matchVal
            when (val matchVal1 = this.rfind(isTrue(predicate))) {
                null -> {
                    break
                }
                else -> {
                    val tail = matchVal1
                    swap(head, tail)
                    trueCount += 1
                }
            }
        }
        return trueCount
    }

    /**
     * Checks if the elements of this iterator are partitioned according to the given predicate,
     * such that all those that return `true` precede all those that return `false`.
     *
     * See also [partition] and [partitionInPlace].
     *
     * [partition]: Iterator.partition
     * [partitionInPlace]: Iterator.partitionInPlace
     *
     * # Examples
     *
     * ```kotlin
     * @feature(iterIsPartitioned)
     * public fun doctestEntry() {
     *     check("Iterator".chars().isPartitioned(Char.isUppercase))
     *     check(!"IntoIterator".chars().isPartitioned(Char.isUppercase))
     * }
     * ```
     */
    public fun isPartitioned(predicate: (Item) -> Boolean): Boolean

    /**
     * An iterator method that applies a function as long as it returns
     * successfully, producing a single, final value.
     *
     * `tryFold()` takes two arguments: an initial value, and a closure with
     * two arguments: an `accumulator`, and an element. The closure either
     * returns successfully, with the value that the accumulator should have
     * for the next iteration, or it returns failure, with an error value that
     * is propagated back to the caller immediately (short-circuiting).
     *
     * The initial value is the value the accumulator will have on the first
     * call. If applying the closure succeeded against every element of the
     * iterator, `tryFold()` returns the final accumulator as success.
     *
     * Folding is useful whenever you have a collection of something, and want
     * to produce a single value from it.
     *
     * # Note to Implementors
     *
     * Several of the other (forward) methods have default implementations in
     * terms of this one, so try to implement this explicitly if it can
     * do something better than the default `for` loop implementation.
     *
     * In particular, try to have this call `tryFold()` on the internal parts
     * from which this iterator is composed. If multiple calls are needed,
     * the `?` operator may be convenient for chaining the accumulator value
     * along, but beware any invariants that need to be upheld before those
     * early returns. This is a `self` method, so iteration needs to be
     * resumable after hitting an error here.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * // the checked sum of all of the elements of the array
     * val sum = a.intoIter().tryFold(0, { acc, x -> acc.checkedAdd(x) })
     * check(sum == 6)
     * ```
     *
     * Short-circuiting:
     *
     * ```kotlin
     * val a = listOf(10, 20, 30, 100, 40, 50)
     * var iter = a.intoIter()
     * // This sum overflows when adding the 100 element
     * val sum = iter.tryFold(0, { acc, x -> acc.checkedAdd(x) })
     * check(sum == null)
     * // Because it short-circuited, the remaining elements are still
     * // available through the iterator.
     * check(iter.len() == 2)
     * check(iter.next() == 40)
     * ```
     *
     * While you cannot `break` from a closure, the [ControlFlow] type allows
     * a similar idea:
     *
     * ```kotlin
     * import std.ops.ControlFlow
     *
     * val triangular = (1 until 30).tryFold(0, { prev, x ->
     *     when (val matchVal = prev.checkedAdd(x)) {
     *         null -> {
     *             ControlFlow.Break(prev)
     *         }
     *         else -> {
     *             val next = matchVal
     *             ControlFlow.Continue(next)
     *         }
     *     }
     * })
     * check(triangular == ControlFlow.Break(120))
     * val triangular1 = (1 until 30).tryFold(0uL, { prev, x ->
     *     when (val matchVal = prev.checkedAdd(x)) {
     *         null -> {
     *             ControlFlow.Break(prev)
     *         }
     *         else -> {
     *             val next = matchVal
     *             ControlFlow.Continue(next)
     *         }
     *     }
     * })
     * check(triangular1 == ControlFlow.Continue(435))
     * ```
     */
    public fun <Residual, B, R> tryFold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual> {
        var accum = init
        while (true) {
            val matchVal = this.next()
            if (matchVal == null) break
            val x = matchVal
            accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
        }
        return accum
    }

    /**
     * An iterator method that applies a fallible function to each item in the
     * iterator, stopping at the first error and returning that error.
     *
     * This can also be thought of as the fallible form of [forEach]
     * or as the stateless version of [tryFold].
     *
     * [forEach]: Iterator.forEach
     * [tryFold]: Iterator.tryFold
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.rename
     * import std.io.Write
     * import std.io.stdout
     * import std.path.Path
     *
     * val data = listOf("noTea.txt", "staleBread.json", "torrentialRain.png")
     * val res = data.iter().tryForEach({ x -> stdout().appendLine("$x") })
     * check(res.isOk())
     * var it = data.iter().cloned()
     * val res1 = it.tryForEach({ x -> rename(x, Path.new(x).withExtension("old")) })
     * check(res1.isErr())
     * // It short-circuited, so the remaining items are still in the iterator:
     * check(it.next() == "staleBread.json")
     * ```
     *
     * The [ControlFlow] type can be used with this method for the situations
     * in which you'd use `break` and `continue` in a normal loop:
     *
     * ```kotlin
     * import std.ops.ControlFlow
     *
     * val r = (2 until 100).tryForEach(closure@{ x ->
     *     if (323 % x == 0) {
     *         return@closure ControlFlow.Break(x)
     *     }
     *     ControlFlow.Continue(Unit)
     * })
     * check(r == ControlFlow.Break(17))
     * ```
     */
    public fun <Residual, R> tryForEach(f: (Item) -> R): R where R : Try<R, Unit, Residual> {
        fun <T, R> call(f1: (T) -> R): (Unit, T) -> R = { x -> f1(x) }
        return this.tryFold(Unit, call(f))
    }

    /**
     * Folds every element into an accumulator by applying an operation,
     * returning the final result.
     *
     * `fold()` takes two arguments: an initial value, and a closure with two
     * arguments: an `accumulator`, and an element. The closure returns the value that
     * the accumulator should have for the next iteration.
     *
     * The initial value is the value the accumulator will have on the first
     * call.
     *
     * After applying this closure to every element of the iterator, `fold()`
     * returns the accumulator.
     *
     * This operation is sometimes called `reduce` or `inject`.
     *
     * Folding is useful whenever you have a collection of something, and want
     * to produce a single value from it.
     *
     * Note: `fold()`, and similar methods that traverse the entire iterator,
     * might not terminate for infinite iterators, even on traits for which a
     * result is determinable in finite time.
     *
     * Note: [reduce] can be used to use the first element as the initial
     * value, if the accumulator type and item type is the same.
     *
     * Note: `fold()` combines elements in a *left-associative* fashion. For associative
     * operators like `+`, the order the elements are combined in is not important, but for non-associative
     * operators like `-` the order will affect the final result.
     * For a *right-associative* version of `fold()`, see [DoubleEndedIterator.rfold].
     *
     * # Note to Implementors
     *
     * Several of the other (forward) methods have default implementations in
     * terms of this one, so try to implement this explicitly if it can
     * do something better than the default `for` loop implementation.
     *
     * In particular, try to have this call `fold()` on the internal parts
     * from which this iterator is composed.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * // the sum of all of the elements of the array
     * val sum = a.iter().fold(0, { acc, x -> acc + x })
     * check(sum == 6)
     * ```
     *
     * Let's walk through each step of the iteration here:
     *
     * | element | acc | x | result |
     * |---------|-----|---|--------|
     * |         | 0   |   |        |
     * | 1       | 0   | 1 | 1      |
     * | 2       | 1   | 2 | 3      |
     * | 3       | 3   | 3 | 6      |
     *
     * And so, our final result, `6`.
     *
     * This example demonstrates the left-associative nature of `fold()`:
     * it builds a string, starting with an initial value
     * and continuing with each element from the front until the back:
     *
     * ```kotlin
     * val numbers = listOf(1, 2, 3, 4, 5)
     * val zero = "0".toString()
     * val result = numbers.iter().fold(zero, { acc, x ->
     *     "($acc + $x)"
     * })
     * check(result == "(((((0 + 1) + 2) + 3) + 4) + 5)")
     * ```
     * It's common for people who haven't used iterators a lot to
     * use a `for` loop with a list of things to build up a result. Those
     * can be turned into `fold()`s:
     *
     * [forLoop]: ../../book/ch03-05-control-flow.html#looping-through-a-collection-with-for
     *
     * ```kotlin
     * val numbers = listOf(1, 2, 3, 4, 5)
     * var result = 0
     * // for loop:
     * for (i in numbers) {
     *     result = result + i
     * }
     * // fold:
     * val result2 = numbers.iter().fold(0, { acc, x -> acc + x })
     * // they're the same
     * check(result == result2)
     * ```
     *
     * [reduce]: Iterator.reduce
     */
    public fun <B> fold(init: B, f: (B, Item) -> B): B {
        var accum = init
        while (true) {
            val matchVal = this.next()
            if (matchVal == null) break
            val x = matchVal
            accum = f(accum, x)
        }
        return accum
    }

    /**
     * Reduces the elements to a single one, by repeatedly applying a reducing
     * operation.
     *
     * If the iterator is empty, returns [None]; otherwise, returns the
     * result of the reduction.
     *
     * The reducing function is a closure with two arguments: an `accumulator`, and an element.
     * For iterators with at least one element, this is the same as [fold]
     * with the first element of the iterator as the initial accumulator value, folding
     * every subsequent element into it.
     *
     * [fold]: Iterator.fold
     *
     * # Example
     *
     * ```kotlin
     * val reduced: Int = (1 until 10).reduce({ acc, e -> acc + e }).unwrapOr(0)
     * check(reduced == 45)
     * // Which is equivalent to doing it with `fold`:
     * val folded: Int = (1 until 10).fold(0, { acc, e -> acc + e })
     * check(reduced == folded)
     * ```
     */
    public fun reduce(f: (Item, Item) -> Item): Item? {
        val first = (this.next() ?: return null)
        return this.fold(first, f)
    }

    /**
     * Reduces the elements to a single one by repeatedly applying a reducing operation. If the
     * closure returns a failure, the failure is propagated back to the caller immediately.
     *
     * The return type of this method depends on the return type of the closure. If the closure
     * returns `Result<Self.Item, E>`, then this function will return `Result<Option<Self.Item>,
     * E>`.If the closure returns`Option<Self.Item>`, then this function will return
     * `Option<Option<Self.Item>>`.
     *
     * When called on an empty iterator, this function will return either `Some(None)` or
     * `Ok(None)` depending on the type of the provided closure.
     *
     * For iterators with at least one element, this is essentially the same as calling
     * [tryFold] with the first element of the iterator as the initial accumulator value.
     *
     * [tryFold]: Iterator.tryFold
     *
     * # Examples
     *
     * Safely calculate the sum of a series of numbers:
     *
     * ```kotlin
     * @feature(iteratorTryReduce)
     * public fun doctestEntry() {
     *     val numbers: List<Int> = mutableListOf(10, 20, 5, 23, 0)
     *     val sum = ArrayDeque(numbers).tryReduce({ x, y -> x.checkedAdd(y) })
     *     check(sum == 58)
     * }
     * ```
     *
     * Determine when a reduction short circuited:
     *
     * ```kotlin
     * @feature(iteratorTryReduce)
     * public fun doctestEntry() {
     *     val numbers = mutableListOf(1, 2, 3, Int.MAX_VALUE, 4, 5)
     *     val sum = ArrayDeque(numbers).tryReduce({ x, y -> x.checkedAdd(y) })
     *     check(sum == null)
     * }
     * ```
     *
     * Determine when a reduction was not performed because there are no elements:
     *
     * ```kotlin
     * @feature(iteratorTryReduce)
     * public fun doctestEntry() {
     *     val numbers: List<Int> = mutableListOf()
     *     val sum = ArrayDeque(numbers).tryReduce({ x, y -> x.checkedAdd(y) })
     *     check(sum == null)
     * }
     * ```
     *
     * Use a [Result] instead of an [Option]:
     *
     * ```kotlin
     * @feature(iteratorTryReduce)
     * public fun doctestEntry(): Result<Unit> {
     *     val numbers = mutableListOf("1", "2", "3", "4", "5")
     *     val max: Result<Option> = ArrayDeque(numbers).tryReduce(closure@{ x, y ->
     *         if ((x.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))).getOrElse { return@closure Result.failure(it) } > (y.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))).getOrElse { return@closure Result.failure(it) }) {
     *             Result.success(x)
     *         } else {
     *             Result.success(y)
     *         }
     *     })
     *     check(max == Result.success("5"))
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun <Residual, R> tryReduce(f: (Item, Item) -> R): ChangeOutputType<R, Output?> where R : Try<R, Item, Residual>

    /**
     * Tests if every element of the iterator matches a predicate.
     *
     * `all()` takes a closure that returns `true` or `false`. It applies
     * this closure to each element of the iterator, and if they all return
     * `true`, then so does `all()`. If any of them return `false`, it
     * returns `false`.
     *
     * `all()` is short-circuiting; in other words, it will stop processing
     * as soon as it finds a `false`, given that no matter what else happens,
     * the result will also be `false`.
     *
     * An empty iterator returns `true`.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().all({ x -> x > 0 }))
     * check(!a.intoIter().all({ x -> x > 2 }))
     * ```
     *
     * Stopping at the first `false`:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter()
     * check(!iter.all({ x -> x != 2 }))
     * // we can still use `iter`, as there are more elements.
     * check(iter.next() == 3)
     * ```
     */
    public fun all(f: (Item) -> Boolean): Boolean {
        fun <T> check(f1: (T) -> Boolean): (Unit, T) -> ControlFlow<Unit, Unit> =
            { x ->
                if (f1(x)) {
                    ControlFlow.Continue(Unit)
                } else {
                    ControlFlow.Break(Unit)
                }
            }
        return this.tryFold(Unit, check(f)) == ControlFlow.Continue(Unit)
    }

    /**
     * Tests if any element of the iterator matches a predicate.
     *
     * `any()` takes a closure that returns `true` or `false`. It applies
     * this closure to each element of the iterator, and if any of them return
     * `true`, then so does `any()`. If they all return `false`, it
     * returns `false`.
     *
     * `any()` is short-circuiting; in other words, it will stop processing
     * as soon as it finds a `true`, given that no matter what else happens,
     * the result will also be `true`.
     *
     * An empty iterator returns `false`.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().any({ x -> x > 0 }))
     * check(!a.intoIter().any({ x -> x > 5 }))
     * ```
     *
     * Stopping at the first `true`:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter()
     * check(iter.any({ x -> x != 2 }))
     * // we can still use `iter`, as there are more elements.
     * check(iter.next() == 2)
     * ```
     */
    public fun any(f: (Item) -> Boolean): Boolean {
        fun <T> check(f1: (T) -> Boolean): (Unit, T) -> ControlFlow<Unit, Unit> =
            { x ->
                if (f1(x)) {
                    ControlFlow.Break(Unit)
                } else {
                    ControlFlow.Continue(Unit)
                }
            }
        return this.tryFold(Unit, check(f)) == ControlFlow.Break(Unit)
    }

    /**
     * Searches for an element of an iterator that satisfies a predicate.
     *
     * `find()` takes a closure that returns `true` or `false`. It applies
     * this closure to each element of the iterator, and if any of them return
     * `true`, then `find()` returns [Some]. If they all return
     * `false`, it returns [None].
     *
     * `find()` is short-circuiting; in other words, it will stop processing
     * as soon as the closure returns `true`.
     *
     * Because `find()` takes a reference, and many iterators iterate over
     * references, this leads to a possibly confusing situation where the
     * argument is a double reference. You can see this effect in the
     * examples below, with `&& x`.
     *
     * If you need the index of the element, see [position].
     *
     * [Some]: Some
     * [position]: Iterator.position
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().indexOf({ x -> x == 2 }).takeIf { it >= 0 } == 2)
     * check(a.intoIter().indexOf({ x -> x == 5 }).takeIf { it >= 0 } == null)
     * ```
     *
     * Iterating over references:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * // `iter()` yields references i.e. `Int` and `find()` takes a
     * // reference to each element.
     * check(a.iter().indexOf({ x -> x == 2 }).takeIf { it >= 0 } == 2)
     * check(a.iter().indexOf({ x -> x == 5 }).takeIf { it >= 0 } == null)
     * ```
     *
     * Stopping at the first `true`:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter()
     * check(iter.indexOf({ x -> x == 2 }).takeIf { it >= 0 } == 2)
     * // we can still use `iter`, as there are more elements.
     * check(iter.next() == 3)
     * ```
     *
     * Note that `iter.find(f)` is equivalent to `iter.filter(f).next()`.
     */
    public fun find(predicate: (Item) -> Boolean): Item? {
        fun <T> check(predicate1: (T) -> Boolean): (Unit, T) -> ControlFlow<T, Unit> =
            { x ->
                if (predicate1(x)) {
                    ControlFlow.Break(x)
                } else {
                    ControlFlow.Continue(Unit)
                }
            }
        return this.tryFold(Unit, check(predicate)).breakValue()
    }

    /**
     * Applies function to the elements of iterator and returns
     * the first non-none result.
     *
     * `iter.findMap(f)` is equivalent to `iter.filterMap(f).next()`.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf("lol", "NaN", "2", "5")
     * val firstNumber = a.iter().findMap({ s -> s.parse().ok() })
     * check(firstNumber == 2)
     * ```
     */
    public fun <B> findMap(f: (Item) -> B?): B? {
        fun <T, B> check(f1: (T) -> B?): (Unit, T) -> ControlFlow<B, Unit> =
            { x -> when (val matchVal = f1(x)) {
                null -> ControlFlow.Continue(Unit)
                else -> {
                    val x1 = matchVal
                    ControlFlow.Break(x1)
                }
            } }
        return this.tryFold(Unit, check(f)).breakValue()
    }

    /**
     * Applies function to the elements of iterator and returns
     * the first true result or the first error.
     *
     * The return type of this method depends on the return type of the closure.
     * If you return `Result<Boolean, E>` from the closure, you'll get a `Result<Option<Self.Item>, E>`.
     * If you return `Option<Boolean>` from the closure, you'll get an `Option<Option<Self.Item>>`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(tryFind)
     * public fun doctestEntry(): Result<Unit> {
     *     val a = listOf("1", "2", "lol", "NaN", "5")
     *     val isMyNum = closure@{ s: String, search: Int ->
     *         Result.success((s.toIntOrNull()?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("failed to parse Int"))).getOrElse { return@closure Result.failure(it) } == search)
     *     }
     *     val result = a.intoIter().tryFind({ s -> isMyNum(s, 2) })
     *     check(result == Result.success("2"))
     *     val result1 = a.intoIter().tryFind({ s -> isMyNum(s, 5) })
     *     check(result1.isErr())
     *     return Result.success(Unit)
     * }
     * ```
     *
     * This also supports other types which implement [Try], not just [Result].
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import std.num.NonZero
     *
     * @feature(tryFind)
     * public fun doctestEntry() {
     *     val a = uintArrayOf(3u, 5u, 7u, 4u, 9u, 0u, 11u)
     *     val result = ArrayDeque((a).toList()).tryFind({ x -> NonZero.new(x).map({ y -> y.isPowerOfTwo() }) })
     *     check(result == 4)
     *     val result1 = ArrayDeque((a).toList()).take(3).tryFind({ x -> NonZero.new(x).map({ y -> y.isPowerOfTwo() }) })
     *     check(result1 == null)
     *     val result2 = ArrayDeque((a).toList()).rev().tryFind({ x -> NonZero.new(x).map({ y -> y.isPowerOfTwo() }) })
     *     check(result2 == null)
     * }
     * ```
     */
    public fun <Residual, R> tryFind(f: (Item) -> R): ChangeOutputType<R, Item?> where R : Try<R, Boolean, Residual>

    /**
     * Searches for an element in an iterator, returning its index.
     *
     * `position()` takes a closure that returns `true` or `false`. It applies
     * this closure to each element of the iterator, and if one of them
     * returns `true`, then `position()` returns [Some]. If all of
     * them return `false`, it returns [None].
     *
     * `position()` is short-circuiting; in other words, it will stop
     * processing as soon as it finds a `true`.
     *
     * # Overflow Behavior
     *
     * The method does no guarding against overflows, so if there are more
     * than [usize.Max] non-matching elements, it either produces the wrong
     * result or panics. If overflow checks are enabled, a panic is
     * guaranteed.
     *
     * # Panics
     *
     * This function might panic if the iterator has more than `Int.MAX`
     * non-matching elements.
     *
     * [Some]: Some
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().indexOfFirst({ x -> x == 2 }).takeIf { it >= 0 } == 1)
     * check(a.intoIter().indexOfFirst({ x -> x == 5 }).takeIf { it >= 0 } == null)
     * ```
     *
     * Stopping at the first `true`:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3, 4)
     * var iter = a.intoIter()
     * check(iter.indexOfFirst({ x -> x >= 2 }).takeIf { it >= 0 } == 1)
     * // we can still use `iter`, as there are more elements.
     * check(iter.next() == 3)
     * // The returned index depends on iterator state
     * check(iter.indexOfFirst({ x -> x == 4 }).takeIf { it >= 0 } == 0)
     * ```
     */
    public fun position(predicate: (Item) -> Boolean): Int? {
        fun <T> check(predicate1: (T) -> Boolean, acc: RustMutableReference<Int>): (Unit, T) -> ControlFlow<Int, Unit> {
            val acc1 = acc
            return { x ->
                if (predicate1(x)) {
                    ControlFlow.Break((acc1).read())
                } else {
                    (acc1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (1); targetPlace.write(currentValue) }
                    ControlFlow.Continue(Unit)
                }
            }
        }
        val localStorageL3167C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        return this.tryFold(Unit, check(predicate, RustMutableReference(localStorageL3167C8.asMutPtr(), localStorageL3167C8.layout))).breakValue()
    }

    /**
     * Searches for an element in an iterator from the right, returning its
     * index.
     *
     * `rposition()` takes a closure that returns `true` or `false`. It applies
     * this closure to each element of the iterator, starting from the end,
     * and if one of them returns `true`, then `rposition()` returns
     * [Some]. If all of them return `false`, it returns [None].
     *
     * `rposition()` is short-circuiting; in other words, it will stop
     * processing as soon as it finds a `true`.
     *
     * [Some]: Some
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * check(a.intoIter().indexOfLast({ x -> x == 3 }).takeIf { it >= 0 } == 2)
     * check(a.intoIter().indexOfLast({ x -> x == 5 }).takeIf { it >= 0 } == null)
     * ```
     *
     * Stopping at the first `true`:
     *
     * ```kotlin
     * val a = listOf(-1, 2, 3, 4)
     * var iter = a.intoIter()
     * check(iter.indexOfLast({ x -> x >= 2 }).takeIf { it >= 0 } == 3)
     * // we can still use `iter`, as there are more elements.
     * check(iter.next() == -1)
     * check(iter.nextBack() == 3)
     * ```
     */
    public fun rposition(predicate: (Item) -> Boolean): Int? {
        // No need for an overflow check here, because `ExactSizeIterator`
        // implies that the number of elements fits into a `Int`.
        fun <T> check(predicate1: (T) -> Boolean): (Int, T) -> ControlFlow<Int, Int> =
            { i, x ->
                val i1 = i - 1
                if (predicate1(x)) {
                    ControlFlow.Break(i1)
                } else {
                    ControlFlow.Continue(i1)
                }
            }
        val n = this.len()
        return this.tryRfold(n, check(predicate)).breakValue()
    }

    /**
     * Returns the maximum element of an iterator.
     *
     * If several elements are equally maximum, the last element is
     * returned. If the iterator is empty, [None] is returned.
     *
     * Note that [f32]/[f64] doesn't implement [Ord] due to NaN being
     * incomparable. You can work around this by using [Iterator.reduce]:
     * ```kotlin
     * check(ArrayDeque((floatArrayOf(2.4f, Float.NaN, 1.3f)).toList()).reduce(Float.max).unwrapOr(0.0) == 2.4)
     * ```
     *
     * # Examples
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * public fun doctestEntry() {
     *     val a = listOf(1, 2, 3)
     *     val b: UIntArray = uintArrayOf()
     *     check(a.intoIter().max() == 3)
     *     check(ArrayDeque((b).toList()).max() == null)
     * }
     * ```
     */
    public fun max(): Item? = this.reduce({ Ord.max(it) })

    /**
     * Returns the minimum element of an iterator.
     *
     * If several elements are equally minimum, the first element is returned.
     * If the iterator is empty, [None] is returned.
     *
     * Note that [f32]/[f64] doesn't implement [Ord] due to NaN being
     * incomparable. You can work around this by using [Iterator.reduce]:
     * ```kotlin
     * check(ArrayDeque((floatArrayOf(2.4f, Float.NaN, 1.3f)).toList()).reduce(Float.min).unwrapOr(0.0) == 1.3)
     * ```
     *
     * # Examples
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * public fun doctestEntry() {
     *     val a = listOf(1, 2, 3)
     *     val b: UIntArray = uintArrayOf()
     *     check(a.intoIter().min() == 1)
     *     check(ArrayDeque((b).toList()).min() == null)
     * }
     * ```
     */
    public fun min(): Item? = this.reduce({ Ord.min(it) })

    /**
     * Returns the element that gives the maximum value from the
     * specified function.
     *
     * If several elements are equally maximum, the last element is
     * returned. If the iterator is empty, [None] is returned.
     *
     * # Examples
     *
     * ```kotlin
     * val a = intArrayOf(-3, 0, 1, 5, -10)
     * check(ArrayDeque((a).toList()).maxByKey({ x -> x.abs() }).unwrap() == -10)
     * ```
     */
    public fun <B> maxByKey(f: (Item) -> B): Item? {
        // If we implemented this via `maxBy` that would force it to use `B.cmp`.
        // By using `max` over `KeyAndValue`, it instead ends up calling `B.lt`
        // (via `KeyAndValue.max`), which is often overridden more efficiently.
        fun <T, B> key(f1: (T) -> B): (T) -> KeyAndValue<B, T> = { value -> KeyAndValue(key = f1(value), value = value) }
        val structVal = (this.map(key(f)).max() ?: return null)
        val value = structVal.value
        return value
    }

    /**
     * Returns the element that gives the maximum value with respect to the
     * specified comparison function.
     *
     * If several elements are equally maximum, the last element is
     * returned. If the iterator is empty, [None] is returned.
     *
     * # Examples
     *
     * ```kotlin
     * val a = intArrayOf(-3, 0, 1, 5, -10)
     * check(ArrayDeque((a).toList()).maxBy({ x, y -> x.cmp(y) }).unwrap() == 5)
     * ```
     */
    public fun maxBy(compare: (Item, Item) -> Ordering): Item? {
        fun <T> fold(compare1: (T, T) -> Ordering): (T, T) -> T = { x, y -> cmp.maxBy(x, y, compare1) }
        return this.reduce(fold(compare))
    }

    /**
     * Returns the element that gives the minimum value from the
     * specified function.
     *
     * If several elements are equally minimum, the first element is
     * returned. If the iterator is empty, [None] is returned.
     *
     * # Examples
     *
     * ```kotlin
     * val a = intArrayOf(-3, 0, 1, 5, -10)
     * check(ArrayDeque((a).toList()).minByKey({ x -> x.abs() }).unwrap() == 0)
     * ```
     */
    public fun <B> minByKey(f: (Item) -> B): Item? {
        // If we implemented this via `minBy` that would force it to use `B.cmp`.
        // By using `min` over `KeyAndValue`, it instead ends up calling `B.lt`
        // (via `KeyAndValue.min`), which is often overridden more efficiently.
        fun <T, B> key(f1: (T) -> B): (T) -> KeyAndValue<B, T> = { value -> KeyAndValue(key = f1(value), value = value) }
        val structVal = (this.map(key(f)).min() ?: return null)
        val value = structVal.value
        return value
    }

    /**
     * Returns the element that gives the minimum value with respect to the
     * specified comparison function.
     *
     * If several elements are equally minimum, the first element is
     * returned. If the iterator is empty, [None] is returned.
     *
     * # Examples
     *
     * ```kotlin
     * val a = intArrayOf(-3, 0, 1, 5, -10)
     * check(ArrayDeque((a).toList()).minBy({ x, y -> x.cmp(y) }).unwrap() == -10)
     * ```
     */
    public fun minBy(compare: (Item, Item) -> Ordering): Item? {
        fun <T> fold(compare1: (T, T) -> Ordering): (T, T) -> T = { x, y -> cmp.minBy(x, y, compare1) }
        return this.reduce(fold(compare))
    }

    /**
     * Reverses an iterator's direction.
     *
     * Usually, iterators iterate from left to right. After using `rev()`,
     * an iterator will instead iterate from right to left.
     *
     * This is only possible if the iterator has an end, so `rev()` only
     * works on [DoubleEndedIterator]s.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter().rev()
     * check(iter.next() == 3)
     * check(iter.next() == 2)
     * check(iter.next() == 1)
     * check(iter.next() == null)
     * ```
     */
    public fun rev(): Rev<SelfType> = Rev.new(this)

    /**
     * Converts an iterator of pairs into a pair of containers.
     *
     * `unzip()` consumes an entire iterator of pairs, producing two
     * collections: one from the left elements of the pairs, and one
     * from the right elements.
     *
     * This function is, in some sense, the opposite of [zip].
     *
     * [zip]: Iterator.zip
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(Pair(1, 2), Pair(3, 4), Pair(5, 6))
     * val (left: Vec, right: Vec) = a.intoIter().unzip()
     * check(left == listOf(1, 3, 5))
     * check(right == listOf(2, 4, 6))
     * // you can also unzip multiple nested tuples at once
     * val a1 = listOf(Pair(1, Pair(2, 3)), Pair(4, Pair(5, 6)))
     * val (x, y, z) = a1.intoIter().unzip()
     * check(x == listOf(1, 4))
     * check(y == listOf(2, 5))
     * check(z == listOf(3, 6))
     * ```
     */
    public fun <A, B, FromA, FromB> unzip(defaultFromAFn: () -> FromA, defaultFromBFn: () -> FromB): Pair<FromA, FromB>

    /**
     * Creates an iterator which copies all of its elements.
     *
     * This is useful when you have an iterator over `T`, but you need an
     * iterator over `T`.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * val vCopied: Vec = a.iter().copied().collect()
     * // copied is the same as .map(|&x| x)
     * val vMap: Vec = a.iter().collect()
     * check(vCopied == listOf(1, 2, 3))
     * check(vMap == listOf(1, 2, 3))
     * ```
     */
    public fun <T> copied(): Copied<SelfType> = Copied.new(this)

    /**
     * Creates an iterator which [clone]s all of its elements.
     *
     * This is useful when you have an iterator over `T`, but you need an
     * iterator over `T`.
     *
     * There is no guarantee whatsoever about the `clone` method actually
     * being called *or* optimized away. So code should not depend on
     * either.
     *
     * [clone]: Clone.clone
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * val vCloned: Vec = a.iter().cloned().collect()
     * // cloned is the same as .map(|&x| x), for integers
     * val vMap: Vec = a.iter().collect()
     * check(vCloned == listOf(1, 2, 3))
     * check(vMap == listOf(1, 2, 3))
     * ```
     *
     * To get the best performance, try to clone late:
     *
     * ```kotlin
     * val a = listOf(mutableListOf(0u.toUByte(), 1, 2), mutableListOf(3, 4), mutableListOf(23))
     * // don't do this:
     * val slower: Vec = a.iter().cloned().filter({ s -> s.len() == 1 }).collect()
     * check(listOf(mutableListOf(23)) == slower)
     * // instead call `cloned` late
     * val faster: Vec = a.iter().filter({ s -> s.len() == 1 }).cloned().collect()
     * check(listOf(mutableListOf(23)) == faster)
     * ```
     */
    public fun <T> cloned(): Cloned<SelfType> = Cloned.new(this)

    /**
     * Repeats an iterator endlessly.
     *
     * Instead of stopping at [None], the iterator will instead start again,
     * from the beginning. After iterating again, it will start at the
     * beginning again. And again. And again. Forever. Note that in case the
     * original iterator is empty, the resulting iterator will also be empty.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * var iter = a.intoIter().cycle()
     * while (true) {
     *     check(iter.next() == 1)
     *     check(iter.next() == 2)
     *     check(iter.next() == 3)
     * }
     * ```
     */
    public fun cycle(): Cycle<SelfType> = Cycle.new(this)

    /**
     * Returns an iterator over `N` elements of the iterator at a time.
     *
     * The chunks do not overlap. If `N` does not divide the length of the
     * iterator, then the last up to `N - 1` elements will be omitted and can be
     * retrieved from the [`intoRemainder`][ArrayChunks.intoRemainder]
     * function of the iterator.
     *
     * # Panics
     *
     * Panics if `N` is zero.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * @feature(iterArrayChunks)
     * public fun doctestEntry() {
     *     var iter = "lorem".chars().arrayChunks()
     *     check(iter.next() == listOf('l', 'o'))
     *     check(iter.next() == listOf('r', 'e'))
     *     check(iter.next() == null)
     *     check(iter.intoRemainder().asSlice() == listOf('m'))
     * }
     * ```
     *
     * ```kotlin
     * @feature(iterArrayChunks)
     * public fun doctestEntry() {
     *     val data = listOf(1, 1, 2, -2, 6, 0, 3, 1)
     *     // ^-----^  ^------^
     *     for ((x, y, z) in data.iter().arrayChunks()) {
     *         check(x + y + z == 4)
     *     }
     * }
     * ```
     */
    public fun arrayChunks(n: Int): ArrayChunks<SelfType> = ArrayChunks.new(this, n)

    /**
     * Sums the elements of an iterator.
     *
     * Takes each element, adds them together, and returns the result.
     *
     * An empty iterator returns the *additive identity* ("zero") of the type,
     * which is `0` for integers and `- 0.0` for floats.
     *
     * `sum()` can be used to sum any type implementing [`Sum`][core.iter.Sum],
     * including [`Option`][Option.sum] and [`Result`][Result.sum].
     *
     * # Panics
     *
     * When calling `sum()` and a primitive integer type is being returned, this
     * method will panic if the computation overflows and overflow checks are
     * enabled.
     *
     * # Examples
     *
     * ```kotlin
     * val a = listOf(1, 2, 3)
     * val sum: Int = a.iter().sum()
     * check(sum == 6)
     * val b: List<Float> = mutableListOf()
     * val sum1: Float = b.sum()
     * check(sum1 == -0.0f)
     * ```
     */
    public fun <S> sum(): S where S : Sum<Item, S> = Sum.sum(this)

    /**
     * Iterates over the entire iterator, multiplying all the elements.
     *
     * An empty iterator returns the one value of the type.
     *
     * `product()` can be used to multiply any type implementing [`Product`][core.iter.Product],
     * including [`Option`][Option.product] and [`Result`][Result.product].
     *
     * # Panics
     *
     * When calling `product()` and a primitive integer type is being returned,
     * method will panic if the computation overflows and overflow checks are
     * enabled.
     *
     * # Examples
     *
     * ```kotlin
     * fun factorial(n: UInt): UInt {
     *     return (1u..n).product()
     * }
     * check(factorial(0u) == 1u)
     * check(factorial(1u) == 1u)
     * check(factorial(5u) == 120u)
     * ```
     */
    public fun <P> product(): P where P : Product<Item, P> = Product.product(this)

    /**
     * [Lexicographically](Ord#lexicographical-comparison) compares the elements of this [Iterator] with those
     * of another.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(listOf(1).iter().cmp(listOf(1).iter()) == Ordering.Equal)
     * check(listOf(1).iter().cmp(listOf(1, 2).iter()) == Ordering.Less)
     * check(listOf(1, 2).iter().cmp(listOf(1).iter()) == Ordering.Greater)
     * ```
     */
    public fun <I> cmp(other: I): Ordering where I : Iterable<Item> = this.cmpBy(other, { x, y -> x.cmp(y) })

    /**
     * [Lexicographically](Ord#lexicographical-comparison) compares the elements of this [Iterator] with those
     * of another with respect to the specified comparison function.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * @feature(iterOrderBy)
     * public fun doctestEntry() {
     *     val xs = listOf(1, 2, 3, 4)
     *     val ys = listOf(1, 4, 9, 16)
     *     check(xs.intoIter().cmpBy(ys, { x, y -> x.cmp(y) }) == Ordering.Less)
     *     check(xs.intoIter().cmpBy(ys, { x, y -> (x * x).cmp(y) }) == Ordering.Equal)
     *     check(xs.intoIter().cmpBy(ys, { x, y -> (2 * x).cmp(y) }) == Ordering.Greater)
     * }
     * ```
     */
    public fun <I> cmpBy(other: I, cmp: (Item, I.Item) -> Ordering): Ordering {
        fun <X, Y> compare(cmp1: (Item, I.Item) -> Ordering): (X, Y) -> ControlFlow<Ordering, Unit> =
            { x, y -> when (val matchVal = cmp1(x, y)) {
                Ordering.Equal -> ControlFlow.Continue(Unit)
                else -> {
                    val nonEq = matchVal
                    ControlFlow.Break(nonEq)
                }
            } }
        return when (val matchVal = iterCompare(this, other.iterator(), compare(cmp))) {
            is ControlFlow.Continue<*, *> -> {
                val ord = matchVal.cfContinueValue()
                ord
            }
            is ControlFlow.Break<*, *> -> {
                val ord1 = matchVal.cfBreakValue()
                ord1
            }
        }
    }

    /**
     * [Lexicographically](Ord#lexicographical-comparison) compares the [PartialOrd] elements of
     * this [Iterator] with those of another. The comparison works like short-circuit
     * evaluation, returning a result without comparing the remaining elements.
     * As soon as an order can be determined, the evaluation stops and a result is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(listOf(1.0).iter().partialCmp(listOf(1.0).iter()) == Ordering.Equal)
     * check(listOf(1.0).iter().partialCmp(listOf(1.0, 2.0).iter()) == Ordering.Less)
     * check(listOf(1.0, 2.0).iter().partialCmp(listOf(1.0).iter()) == Ordering.Greater)
     * ```
     *
     * For floating-point numbers, NaN does not have a total order and will result
     * in `None` when compared:
     *
     * ```kotlin
     * check(doubleArrayOf(Double.NaN).iter().partialCmp(listOf(1.0).iter()) == null)
     * ```
     *
     * The results are determined by the order of evaluation.
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * check(doubleArrayOf(1.0, Double.NaN).iter().partialCmp(doubleArrayOf(2.0, Double.NaN).iter()) == Ordering.Less)
     * check(doubleArrayOf(2.0, Double.NaN).iter().partialCmp(doubleArrayOf(1.0, Double.NaN).iter()) == Ordering.Greater)
     * check(doubleArrayOf(Double.NaN, 1.0).iter().partialCmp(doubleArrayOf(Double.NaN, 2.0).iter()) == null)
     * ```
     */
    public fun <I> partialCmp(other: I): Ordering? = this.partialCmpBy(other, { x, y -> x.partialCmp(y) })

    /**
     * [Lexicographically](Ord#lexicographical-comparison) compares the elements of this [Iterator] with those
     * of another with respect to the specified comparison function.
     *
     * # Examples
     *
     * ```kotlin
     * import std.cmp.Ordering
     *
     * @feature(iterOrderBy)
     * public fun doctestEntry() {
     *     val xs = listOf(1.0, 2.0, 3.0, 4.0)
     *     val ys = listOf(1.0, 4.0, 9.0, 16.0)
     *     check(xs.iter().partialCmpBy(ys, { x, y -> x.partialCmp(y) }) == Ordering.Less)
     *     check(xs.iter().partialCmpBy(ys, { x, y -> (x * x).partialCmp(y) }) == Ordering.Equal)
     *     check(xs.iter().partialCmpBy(ys, { x, y -> (2.0 * x).partialCmp(y) }) == Ordering.Greater)
     * }
     * ```
     */
    public fun <I> partialCmpBy(other: I, partialCmp: (Item, I.Item) -> Ordering?): Ordering? {
        fun <X, Y> compare(partialCmp1: (Item, I.Item) -> Ordering?): (X, Y) -> ControlFlow<Ordering?, Unit> =
            { x, y -> when (val matchVal = partialCmp1(x, y)) {
                Ordering.Equal -> ControlFlow.Continue(Unit)
                else -> {
                    val nonEq = matchVal
                    ControlFlow.Break(nonEq)
                }
            } }
        return when (val matchVal = iterCompare(this, other.iterator(), compare(partialCmp))) {
            is ControlFlow.Continue<*, *> -> {
                val ord = matchVal.cfContinueValue()
                ord
            }
            is ControlFlow.Break<*, *> -> {
                val ord1 = matchVal.cfBreakValue()
                ord1
            }
        }
    }

    /**
     * Determines if the elements of this [Iterator] are equal to those of
     * another.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1).iter().eq(listOf(1).iter()) == true)
     * check(listOf(1).iter().eq(listOf(1, 2).iter()) == false)
     * ```
     */
    public fun <I> eq(other: I): Boolean = this.eqBy(other, { x, y -> x == y })

    /**
     * Determines if the elements of this [Iterator] are equal to those of
     * another with respect to the specified equality function.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(iterOrderBy)
     * public fun doctestEntry() {
     *     val xs = listOf(1, 2, 3, 4)
     *     val ys = listOf(1, 4, 9, 16)
     *     check(xs.iter().eqBy(ys, { x, y -> x * x == y }))
     * }
     * ```
     */
    public fun <I> eqBy(other: I, eq: (Item, I.Item) -> Boolean): Boolean {
        fun <X, Y> compare(eq1: (Item, I.Item) -> Boolean): (X, Y) -> ControlFlow<Unit, Unit> =
            { x, y ->
                if (eq1(x, y)) {
                    ControlFlow.Continue(Unit)
                } else {
                    ControlFlow.Break(Unit)
                }
            }
        return SpecIterEq.SpecIterEq(this, other.iterator(), compare(eq))
    }

    /**
     * Determines if the elements of this [Iterator] are not equal to those of
     * another.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1).iter().ne(listOf(1).iter()) == false)
     * check(listOf(1).iter().ne(listOf(1, 2).iter()) == true)
     * ```
     */
    public fun <I> ne(other: I): Boolean

    /**
     * Determines if the elements of this [Iterator] are [lexicographically](Ord#lexicographical-comparison)
     * less than those of another.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1).iter().lt(listOf(1).iter()) == false)
     * check(listOf(1).iter().lt(listOf(1, 2).iter()) == true)
     * check(listOf(1, 2).iter().lt(listOf(1).iter()) == false)
     * check(listOf(1, 2).iter().lt(listOf(1, 2).iter()) == false)
     * ```
     */
    public fun <I> lt(other: I): Boolean = this.partialCmp(other) == Ordering.Less

    /**
     * Determines if the elements of this [Iterator] are [lexicographically](Ord#lexicographical-comparison)
     * less or equal to those of another.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1).iter().le(listOf(1).iter()) == true)
     * check(listOf(1).iter().le(listOf(1, 2).iter()) == true)
     * check(listOf(1, 2).iter().le(listOf(1).iter()) == false)
     * check(listOf(1, 2).iter().le(listOf(1, 2).iter()) == true)
     * ```
     */
    public fun <I> le(other: I): Boolean =
        when (val matchVal = this.partialCmp(other)) {
            Ordering.Less, Ordering.Equal -> true
            else -> false
        }

    /**
     * Determines if the elements of this [Iterator] are [lexicographically](Ord#lexicographical-comparison)
     * greater than those of another.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1).iter().gt(listOf(1).iter()) == false)
     * check(listOf(1).iter().gt(listOf(1, 2).iter()) == false)
     * check(listOf(1, 2).iter().gt(listOf(1).iter()) == true)
     * check(listOf(1, 2).iter().gt(listOf(1, 2).iter()) == false)
     * ```
     */
    public fun <I> gt(other: I): Boolean = this.partialCmp(other) == Ordering.Greater

    /**
     * Determines if the elements of this [Iterator] are [lexicographically](Ord#lexicographical-comparison)
     * greater than or equal to those of another.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1).iter().ge(listOf(1).iter()) == true)
     * check(listOf(1).iter().ge(listOf(1, 2).iter()) == false)
     * check(listOf(1, 2).iter().ge(listOf(1).iter()) == true)
     * check(listOf(1, 2).iter().ge(listOf(1, 2).iter()) == true)
     * ```
     */
    public fun <I> ge(other: I): Boolean =
        when (val matchVal = this.partialCmp(other)) {
            Ordering.Greater, Ordering.Equal -> true
            else -> false
        }

    /**
     * Checks if the elements of this iterator are sorted.
     *
     * That is, for each element `a` and its following element `b`, `a <= b` must hold. If the
     * iterator yields exactly zero or one element, `true` is returned.
     *
     * Note that if `Self.Item` is only `PartialOrd`, but not `Ord`, the above definition
     * implies that this function returns `false` if any two consecutive items are not
     * comparable.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1, 2, 2, 9).iter().isSorted())
     * check(!listOf(1, 3, 2, 4).iter().isSorted())
     * check(listOf(0).iter().isSorted())
     * check(empty<Int>().isSorted())
     * check(!floatArrayOf(0.0f, 1.0f, Float.NaN).iter().isSorted())
     * ```
     */
    public fun isSorted(): Boolean = this.isSortedBy({ a, b -> a <= b })

    /**
     * Checks if the elements of this iterator are sorted using the given comparator function.
     *
     * Instead of using `PartialOrd.partialCmp`, this function uses the given `compare`
     * function to determine whether two elements are to be considered in sorted order.
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf(1, 2, 2, 9).iter().isSortedBy({ a, b -> a <= b }))
     * check(!listOf(1, 2, 2, 9).iter().isSortedBy({ a, b -> a < b }))
     * check(listOf(0).iter().isSortedBy({ a, b -> true }))
     * check(listOf(0).iter().isSortedBy({ a, b -> false }))
     * check(empty<Int>().isSortedBy({ a, b -> false }))
     * check(empty<Int>().isSortedBy({ a, b -> true }))
     * ```
     */
    public fun isSortedBy(compare: (Item, Item) -> Boolean): Boolean {
        fun <T> check(last: T, compare1: (T, T) -> Boolean): (T) -> Boolean {
            var last1 = last
            return closure@{ curr ->
                if (!compare1(last1, curr)) {
                    return@closure false
                }
                last1 = curr
                true
            }
        }
        var last =
            when (val matchVal = this.next()) {
                null -> { return true }
                else -> {
                    val e = matchVal
                    e
                }
            }
        return this.all(check(last, compare))
    }

    /**
     * Checks if the elements of this iterator are sorted using the given key extraction
     * function.
     *
     * Instead of comparing the iterator's elements directly, this function compares the keys of
     * the elements, as determined by `f`. Apart from that, it's equivalent to [isSorted]; see
     * its documentation for more information.
     *
     * [isSorted]: Iterator.isSorted
     *
     * # Examples
     *
     * ```kotlin
     * check(listOf("c", "bb", "aaa").iter().isSortedByKey({ s -> s.len() }))
     * check(!intArrayOf(-2, -1, 0, 3).iter().isSortedByKey({ n -> n.abs() }))
     * ```
     */
    public fun <K> isSortedByKey(f: (Item) -> K): Boolean = this.map(f).isSorted()

    /**
     * See [TrustedRandomAccess][TrustedRandomAccess]
     */
    public fun iteratorGetUnchecked(idx: Int): Item {
        // Always specialized
        error("unreachable")
    }
}

public interface SpecIterEq<B : Iterator<B, Item>, Item, T, Item> : Iterator<T, Item> {
    public fun specIterEq(b: B, f: (Item, Item) -> ControlFlow<Unit, Unit>): Boolean
}

public fun <Item, A : Iterator<A, Item>, B : Iterator<B, Item>> A.specIterEq(b: B, f: (Item, Item) -> ControlFlow<Unit, Unit>): Boolean = iterEq(this, b, f)

public fun <Item, A, B> A.specIterEqB(b: B, f: (Item, Item) -> ControlFlow<Unit, Unit>): Boolean where A : Iterator<A, Item>, A : TrustedLen<Item>, B : Iterator<B, Item>, B : TrustedLen<Item> {
    // we *can't* short-circuit if:
    run {
        val matchVal = Pair(this.sizeHint(), b.sizeHint())
        when {
            // ... both iterators have the same length
            matchVal.first.second != null && matchVal.second.second != null && matchVal.first.second == b -> {
                val a = matchVal.first.second
                val b1 = matchVal.second.second
            }
            // ... or both of them are longer than `Int.MAX` (i.e. have an unknown length).
            matchVal.first.second == null && matchVal.second.second == null -> {}
            // otherwise, we can ascertain that they are unequal without actually comparing items
            else -> { return false }
        }
    }
    return iterEq(this, b, f)
}

/**
 * Compares two iterators element-wise using the given function.
 *
 * If `ControlFlow.Continue(())` is returned from the function, the comparison moves on to the next
 * elements of both iterators. Returning `ControlFlow.Break(x)` short-circuits the iteration and
 * returns `ControlFlow.Break(x)`. If one of the iterators runs out of elements,
 * `ControlFlow.Continue(ord)` is returned where `ord` is the result of comparing the lengths of
 * the iterators.
 *
 * Isolates the logic shared by [`cmpBy`](Iterator.cmpBy),
 * [`partialCmpBy`](Iterator.partialCmpBy), and [`eqBy`](Iterator.eqBy).
 */
public fun <Item, A, B, T> iterCompare(a: A, b: B, f: (A.Item, B.Item) -> ControlFlow<T, Unit>): ControlFlow<T, Ordering> where A : Iterator<A, Item>, B : Iterator<B, Item> {
    fun <Item, B, X, T> compare(b1: B, f1: (X, Item) -> ControlFlow<T, Unit>): (X) -> ControlFlow<ControlFlow<T, Ordering>, Unit> where B : Iterator<B, Item> =
        { x -> when (val matchVal = b1.next()) {
            null -> ControlFlow.Break(ControlFlow.Continue(Ordering.Greater))
            else -> {
                val y = matchVal
                f1(x, y).mapBreak(ControlFlow.Break)
            }
        } }
    return when (val matchVal = a.tryForEach(compare(b, f))) {
        is ControlFlow.Continue<*, *> -> {
            val cfContinueValueVal = matchVal.cfContinueValue()
            ControlFlow.Continue(when (val matchVal1 = b.next()) {
                null -> Ordering.Equal
                else -> Ordering.Less
            })
        }
        is ControlFlow.Break<*, *> -> {
            val x = matchVal.cfBreakValue()
            x
        }
    }
}

public fun <Item, A, B> iterEq(a: A, b: B, f: (A.Item, B.Item) -> ControlFlow<Unit, Unit>): Boolean where A : Iterator<A, Item>, B : Iterator<B, Item> = iterCompare(a, b, f).continueValue().isSomeAnd({ ord -> ord == Ordering.Equal })

/**
 * Helper trait to specialize `fold` and `tryFold` for `I where I: Sized`
 */
public interface IteratorRefSpec<T, Item> : Iterator<T, Item> {
    public fun <B> specFold(init: B, f: (B, Item) -> B): B

    public fun <Residual, B, R> specTryFold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual>
}

public fun <Item, I : Iterator<I, Item>, B> I.specFold(init: B, f: (B, Item) -> B): B {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = f(accum, x)
    }
    return accum
}

public fun <Item, Residual, I : Iterator<I, Item>, B, R> I.specTryFold(init: B, f: (B, Item) -> R): R where R : Try<R, B, Residual> {
    var accum = init
    while (true) {
        val matchVal = this.next()
        if (matchVal == null) break
        val x = matchVal
        accum = when (val tryResult = f(accum, x)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    }
    return accum
}

public fun <Item, I : Iterator<I, Item>, Aaa> I.specFold(init: Aaa, fold: (Aaa, Item) -> Aaa): Aaa = this.specTryFold(init, NeverShortCircuit.wrapMut2(fold)).first
