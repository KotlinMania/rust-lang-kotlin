// port-lint: source iter/sources/once.rs
package io.github.kotlinmania.rustlang.core.iter.sources.once

import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.option.IntoIter

/**
 * Creates an iterator that yields an element exactly once.
 *
 * This is commonly used to adapt a single value into a [chain] of other
 * kinds of iteration. Maybe you have an iterator that covers almost
 * everything, but you need an extra special case. Maybe you have a function
 * which works on iterators, but you only need to process one value.
 *
 * [chain]: Iterator.chain
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * import std.iter
 *
 * // one is the loneliest number
 * var one = listOf(1)
 * check(1 == one.next())
 * // just one, that's all we get
 * check(null == one.next())
 * ```
 *
 * Chaining together with another iterator. Let's say that we want to iterate
 * over each file of the `.foo` directory, but also a configuration file,
 * `.foorc`:
 *
 * ```kotlin
 * import std.fs
 * import std.iter
 * import std.path.PathBuf
 *
 * val dirs = readDir(".foo").getOrElse { return Result.failure(it) }
 * // we need to convert from an iterator of DirEntry-s to an iterator of
 * // PathBufs, so we use map
 * val dirs1 = dirs.map({ file -> file.unwrap().path() })
 * // now, our iterator just for our config file
 * val config = listOf(PathBuf.from(".foorc"))
 * // chain the two iterators together into one big iterator
 * val files = dirs1.chain(config)
 * // this will give us all of the files in .foo as well as .foorc
 * for (f in files) {
 *     println("$f")
 * }
 * ```
 */
public fun <T> once(value: T): Once<T> = Once(inner = value.intoIter())

/**
 * An iterator that yields an element exactly once.
 *
 * This `class` is created by the [once] function. See its documentation for more.
 */
public open class Once<T>(
    public var inner: IntoIter<T>,
) : TrustedLen<Any>, Iterable<T> {
    override fun iterator(): Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@Once.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Once<T> = Once(inner)

    public open fun next(): T? = this.inner.removeFirstOrNull()

    public open fun sizeHint(): Pair<Int, Int?> = run { val n = this.inner.size; Pair(n, n) }

    public open fun nextBack(): T? = this.inner.removeLastOrNull()

    public open fun len(): Int = this.inner.size
}
