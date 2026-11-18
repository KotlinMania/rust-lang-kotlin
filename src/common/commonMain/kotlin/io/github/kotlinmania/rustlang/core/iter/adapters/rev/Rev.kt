// port-lint: source iter/adapters/rev.rs
package io.github.kotlinmania.rustlang.core.iter.adapters.rev

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

/**
 * A double-ended iterator with the direction inverted.
 *
 * This `class` is created by the [rev] method on [Iterator]. See its
 * documentation for more.
 *
 * [rev]: Iterator.rev
 * [Iterator]: trait.Iterator.html
 */
public open class Rev<T>(
    public var iter: T,
) {
    public open fun clone(): Rev<T> = Rev(iter)

    /**
     * Consumes the `Rev`, returning the inner iterator.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(revIntoInner)
     * public fun doctestEntry() {
     *     val s = "foobar"
     *     var rev = s.chars().rev()
     *     check(rev.next() == 'r')
     *     check(rev.next() == 'a')
     *     check(rev.next() == 'b')
     *     check(rev.intoInner().joinToString("") == "foo")
     * }
     * ```
     */
    public open fun intoInner(): T = this.iter

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Rev<T>> =
            object : TypeLayout<Rev<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Rev<T> =
                    Rev(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Rev<T>) {
                    (typeLayoutT).writeNative(addr, value.iter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Rev<T> =
                    Rev(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Rev<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.iter)
                }
            }

        public fun <T> new(iter: T): Rev<T> = Rev(iter = iter)
    }
}

public fun <Item, I> Rev<I>.next(): Item? where I : Iterator<Item> = this.iter.nextBack()

public fun <Item, I> Rev<I>.sizeHint(): Pair<Int, Int?> where I : Iterator<Item> = this.iter.sizeHint()

public fun <Item, I> Rev<I>.advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> where I : Iterator<Item> = this.iter.advanceBackBy(n)

public fun <Item, I> Rev<I>.nth(n: Int): Item? where I : Iterator<Item> = this.iter.nthBack(n)

public fun <Item, Residual, I, B, R> Rev<I>.tryFold(init: B, f: (B, Item) -> R): R where I : Iterator<Item>, R : Try<R, B, Residual> = this.iter.tryRfold(init, f)

public fun <Item, I, Acc> Rev<I>.fold(init: Acc, f: (Acc, Item) -> Acc): Acc where I : Iterator<Item> = this.iter.rfold(init, f)

public fun <Item, I> Rev<I>.find(predicate: (Item) -> Boolean): Item? where I : Iterator<Item> = this.iter.rfind(predicate)

public fun <Item, I> Rev<I>.nextBack(): Item? where I : Iterator<Item> = this.iter.next()

public fun <Item, I> Rev<I>.advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> where I : Iterator<Item> = this.iter.advanceBy(n)

public fun <Item, I> Rev<I>.nthBack(n: Int): Item? where I : Iterator<Item> = this.iter.nth(n)

public fun <Item, Residual, I, B, R> Rev<I>.tryRfold(init: B, f: (B, Item) -> R): R where I : Iterator<Item>, R : Try<R, B, Residual> = this.iter.tryFold(init, f)

public fun <Item, I, Acc> Rev<I>.rfold(init: Acc, f: (Acc, Item) -> Acc): Acc where I : Iterator<Item> = this.iter.fold(init, f)

public fun <Item, I> Rev<I>.rfind(predicate: (Item) -> Boolean): Item? where I : Iterator<Item> = this.iter.indexOf(predicate).takeIf { it >= 0 }

public fun <Item, I> Rev<I>.len(): Int where I : Iterator<Item> = this.iter.len()

public fun <Item, I> Rev<I>.isEmpty(): Boolean where I : Iterator<Item> = this.iter.isEmpty()

/**
 * Creates a `Rev` iterator from the default value of `I`
 * ```kotlin
 * val iter: Rev<ArrayDeque<UByte>> = Default.default()
 * check(iter.len() == 0)
 * ```
 */
public fun <I> defaultRevI(defaultIFn: () -> I): Rev<I> = Rev.new(defaultIFn())
