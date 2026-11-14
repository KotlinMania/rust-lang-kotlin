// port-lint: source iter/sources/once_with.rs
package io.github.kotlinmania.rustlang.core.iter.sources.oncewith

import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Creates an iterator that lazily generates a value exactly once by invoking
 * the provided closure.
 *
 * This is commonly used to adapt a single value coroutine into a [chain] of
 * other kinds of iteration. Maybe you have an iterator that covers almost
 * everything, but you need an extra special case. Maybe you have a function
 * which works on iterators, but you only need to process one value.
 *
 * Unlike [once], this function will lazily generate the value on request.
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
 * var one = iter.onceWith({ 1 })
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
 * val dirs = readDir(".foo").unwrap()
 * // we need to convert from an iterator of DirEntry-s to an iterator of
 * // PathBufs, so we use map
 * val dirs1 = dirs.map({ file -> file.unwrap().path() })
 * // now, our iterator just for our config file
 * val config = iter.onceWith({ PathBuf.from(".foorc") })
 * // chain the two iterators together into one big iterator
 * val files = dirs1.chain(config)
 * // this will give us all of the files in .foo as well as .foorc
 * for (f in files) {
 *     println("$f")
 * }
 * ```
 */
public fun <A> onceWith(make: () -> A): OnceWith<() -> A> = OnceWith(make = make)

/**
 * An iterator that yields a single element of type `A` by
 * applying the provided closure `F: FnOnce() -> A`.
 *
 * This `class` is created by the [onceWith] function.
 * See its documentation for more.
 */
public open class OnceWith<F>(
    public var make: F?,
) : TrustedLen<OnceWith<F>>, Iterable<A> {
    override fun iterator(): Iterator<A> = object : AbstractIterator<A>() {
        override fun computeNext() {
            when (val v = this@OnceWith.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): OnceWith<F> = OnceWith(make)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        if (this.make != null) {
            run { f.append("OnceWith(Some(_))"); Result.Ok(Unit) }
        } else {
            run { f.append("OnceWith(None)"); Result.Ok(Unit) }
        }

    public override fun next(): A? {
        val f = (this.make.also { this.make = null } ?: return null)
        return f()
    }

    public override fun sizeHint(): Pair<Int, Int?> = this.make.iter().sizeHint()

    public open fun nextBack(): A? = this.next()

    public open fun len(): Int = this.make.iter().len()
}
