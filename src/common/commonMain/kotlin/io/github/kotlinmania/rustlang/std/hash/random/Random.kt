// port-lint: source hash/random.rs
package io.github.kotlinmania.rustlang.std.hash.random

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.hash.BuildHasher
import io.github.kotlinmania.rustlang.std.hash.SipHasher13
import io.github.kotlinmania.rustlang.std.sys.random.hashmapRandomKeys
import io.github.kotlinmania.rustlang.std.thread.LocalKey
import io.github.kotlinmania.rustlang.std.thread.localimpl.threadLocalInner

// This module exists to isolate [RandomState] and [DefaultHasher] outside of the
// [collections] module without actually publicly exporting them, so that parts of that
// implementation can more easily be moved to the [alloc] crate.
//
// Although its items are public and contain stability attributes, they can't actually be accessed
// outside this crate.

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
 * `RandomState` is the default state for [HashMap] types.
 *
 * A particular instance `RandomState` will create the same instances of
 * [Hasher], but the hashers created by two different `RandomState`
 * instances are unlikely to produce the same result for the same values.
 *
 * # Examples
 *
 * ```kotlin
 * import std.hash.RandomState
 *
 * val s = RandomState.new()
 * var map = HashMap.withHasher(s)
 * map.insert(1, 2)
 * ```
 */
public open class RandomState(
    public var k0: ULong,
    public var k1: ULong,
) : BuildHasher {
    public open fun clone(): RandomState = RandomState(k0, k1)

    public open fun buildHasher(): DefaultHasher = DefaultHasher(SipHasher13.newWithKeys(this.k0, this.k1))

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "RandomState").finishNonExhaustive()

    public companion object {
        public fun typeLayout(): TypeLayout<RandomState> =
            object : TypeLayout<RandomState> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): RandomState =
                    RandomState(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: RandomState) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.k0)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.k1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RandomState =
                    RandomState(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RandomState) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.k0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.k1)
                }
            }

        // rand

        /**
         * Constructs a new `RandomState` that is initialized with random keys.
         *
         * # Examples
         *
         * ```kotlin
         * import std.hash.RandomState
         *
         * val s = RandomState.new()
         * ```
         */
        public fun new(): RandomState {
            val KEYS: LocalKey<Cell<Pair<ULong, ULong>>> = threadLocalInner(key, Cell, Cell.new(hashmapRandomKeys()))
            return KEYS.with({ keys ->
                val (k0, k1) = keys.get()
                keys.set(Pair(k0.wrappingAdd(1), k1))
                RandomState(k0 = k0, k1 = k1)
            })
        }

        /**
         * Constructs a new `RandomState`.
         */
        public fun default(): RandomState = RandomState.new()
    }
}

/**
 * The default [Hasher] used by [RandomState].
 *
 * The internal algorithm is not specified, and so it and its hashes should
 * not be relied upon over releases.
 */
public open class DefaultHasher(
    public var value: SipHasher13,
) {
    public var field0: SipHasher13
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): DefaultHasher = DefaultHasher(value)

    // The underlying `SipHasher13` doesn't override the other
    // `write_ *` methods, so it's ok not to forward them here.
    public open fun write(msg: List<UByte>) {
        this.value.write(msg)
    }

    public open fun writeStr(s: String) {
        this.value.append(s)
    }

    public open fun finish(): ULong = this.value.finish()

    public companion object {
        /**
         * Creates a new `DefaultHasher`.
         *
         * This hasher is not guaranteed to be the same as all other
         * `DefaultHasher` instances, but is the same as all other `DefaultHasher`
         * instances created through `new` or `default`.
         */
        public fun new(): DefaultHasher = DefaultHasher(SipHasher13.newWithKeys(0, 0))

        /**
         * Creates a new `DefaultHasher` using [new].
         * See its documentation for more.
         *
         * [new]: DefaultHasher.new
         */
        public fun default(): DefaultHasher = DefaultHasher.new()
    }
}
