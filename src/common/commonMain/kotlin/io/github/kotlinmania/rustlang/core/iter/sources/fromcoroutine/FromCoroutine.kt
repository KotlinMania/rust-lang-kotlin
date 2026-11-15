// port-lint: source iter/sources/from_coroutine.rs
package io.github.kotlinmania.rustlang.core.iter.sources.fromcoroutine

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ops.coroutine.Coroutine
import io.github.kotlinmania.rustlang.core.ops.coroutine.CoroutineState
import io.github.kotlinmania.rustlang.core.pin.Pin
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
 * Creates a new iterator where each iteration calls the provided coroutine.
 *
 * Similar to [iter.fromFn].
 *
 * # Examples
 *
 * ```kotlin
 * @feature(coroutines)
 * @feature(iterFromCoroutine)
 * public fun doctestEntry() {
 *     val it = fromCoroutine({
 *         yield(1)
 *         yield(2)
 *         yield(3)
 *     })
 *     val v: Vec = it.collect()
 *     check(v == listOf(1, 2, 3))
 * }
 * ```
 */
public fun <G : Coroutine<Unit, Any?, Unit>> fromCoroutine(coroutine: G): FromCoroutine<G> = FromCoroutine(coroutine)

/**
 * An iterator over the values yielded by an underlying coroutine.
 *
 * This `class` is created by the [iter.fromCoroutine] function. See its documentation for
 * more.
 *
 * [iter.fromCoroutine]: fromCoroutine
 */
public open class FromCoroutine<G>(
    public var value: G,
) : Iterable<Yield> {
    public var field0: G
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Yield> =
        object : AbstractIterator<Yield>() {
            override fun computeNext() {
                when (val v = this@FromCoroutine.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun clone(): FromCoroutine<G> = FromCoroutine(value)

    public open fun next(): Yield? =
        when (val matchVal = Pin.new(this.value).resume()) {
            is CoroutineState.Yielded -> {
                val (n) = matchVal
                n
            }
            is CoroutineState.Complete -> null
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "FromCoroutine").finish()

    public companion object {
        public fun <G> typeLayout(typeLayoutG: TypeLayout<G>): TypeLayout<FromCoroutine<G>> =
            object : TypeLayout<FromCoroutine<G>> {
                override val alignBytes: Long = listOf(1L, typeLayoutG.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutG.sizeBytes, alignBytes)
                override fun readNative(addr: Long): FromCoroutine<G> =
                    FromCoroutine(
                        (typeLayoutG).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FromCoroutine<G>) {
                    (typeLayoutG).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FromCoroutine<G> =
                    FromCoroutine(
                        (typeLayoutG).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FromCoroutine<G>) {
                    (typeLayoutG).writeBytes(buf, offset, value.value)
                }
            }
    }
}
