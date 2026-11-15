// port-lint: source iter/sources/from_fn.rs
package io.github.kotlinmania.rustlang.core.iter.sources.fromfn

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.result.Result

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
 * Creates an iterator with the provided closure
 * `F: FnMut() -> Option<T>` as its [`next`](Iterator.next) method.
 *
 * The iterator will yield the `T`s returned from the closure.
 *
 * This allows creating a custom iterator with any behavior
 * without using the more verbose syntax of creating a dedicated type
 * and implementing the [Iterator] trait for it.
 *
 * Note that the `FromFn` iterator doesn’t make assumptions about the behavior of the closure,
 * and therefore conservatively does not implement [FusedIterator],
 * or override [Iterator.sizeHint] from its default `(0, None)`.
 *
 * The closure can use captures and its environment to track state across iterations. Depending on
 * how the iterator is used, this may require specifying the [move] keyword on the closure.
 *
 * [move]: ../../std/keyword.move.html
 *
 * # Examples
 *
 * Let’s re-implement the counter iterator from [module-level documentation]:
 *
 * ```kotlin
 * var count = 0
 * val counter = fromFn({
 *     // Increment our count. This is why we started at zero.
 *     count += 1
 *     // Check to see if we've finished counting or not.
 *     if (count < 6) {
 *         count
 *     } else {
 *         null
 *     }
 * })
 * check(counter.collect<Vec>() == listOf(1, 2, 3, 4, 5))
 * ```
 */
public fun <T> fromFn(f: () -> T?): FromFn<() -> T?> = FromFn(f)

/**
 * An iterator where each iteration calls the provided closure `F: FnMut() -> Option<T>`.
 *
 * This `class` is created by the [iter.fromFn] function.
 * See its documentation for more.
 *
 * [iter.fromFn]: fromFn
 */
public open class FromFn<F>(
    public var value: F,
) : Iterable<T> {
    public var field0: F
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<T> =
        object : AbstractIterator<T>() {
            override fun computeNext() {
                when (val v = this@FromFn.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun clone(): FromFn<F> = FromFn(value)

    public open fun next(): T? = (this.value)()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "FromFn").finish()

    public companion object {
        public fun <F> typeLayout(typeLayoutF: TypeLayout<F>): TypeLayout<FromFn<F>> =
            object : TypeLayout<FromFn<F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutF.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutF.sizeBytes, alignBytes)
                override fun readNative(addr: Long): FromFn<F> =
                    FromFn(
                        (typeLayoutF).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FromFn<F>) {
                    (typeLayoutF).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FromFn<F> =
                    FromFn(
                        (typeLayoutF).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FromFn<F>) {
                    (typeLayoutF).writeBytes(buf, offset, value.value)
                }
            }
    }
}
