package io.github.kotlinmania.rustlang.core.hash

import io.github.kotlinmania.rustlang.core.result.Result

private class DebugStruct(
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
 * Used to create a default [BuildHasher] instance for types that implement
 * [Hasher] and [Default].
 *
 * `BuildHasherDefault<H>` can be used when a type `H` implements [Hasher] and
 * [Default], and you need a corresponding [BuildHasher] instance, but none is
 * defined.
 *
 * Any `BuildHasherDefault` is [zero-sized]. It can be created with
 * [`default`][method.default]. When using `BuildHasherDefault` with [HashMap] or
 * [HashSet], this doesn't need to be done, since they implement appropriate
 * [Default] instances themselves.
 *
 * # Examples
 *
 * Using `BuildHasherDefault` to specify a custom [BuildHasher] for
 * [HashMap]:
 *
 * ```kotlin
 * public typealias MyBuildHasher = BuildHasherDefault<MyHasher>
 *
 * public fun doctestEntry() {
 *     open class MyHasher {
 *         public open fun write(bytes: List<UByte>) {
 *             // Your hashing algorithm goes here!
 *             return error("unsupported")
 *         }
 *
 *         public open fun finish(): ULong {
 *             // Your hashing algorithm goes here!
 *             return error("unsupported")
 *         }
 *     }
 *     val hashMap = HashMap.default()
 * }
 * ```
 *
 * [method.default]: BuildHasherDefault.default
 * [HashMap]: ../../std/collections/struct.HashMap.html
 * [HashSet]: ../../std/collections/struct.HashSet.html
 * [zero-sized]: https://doc.rust-lang.org/nomicon/exotic-sizes.html#zero-sized-types-zsts
 */
public open class BuildHasherDefault<H>(
    public var value: Unit,
) : BuildHasher<H> {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugStruct(f, "BuildHasherDefault").finish(); Result.Ok<Unit, Unit>(Unit) }

    public override fun buildHasher(defaultHFn: () -> H): H = defaultHFn()

    public open fun clone(): BuildHasherDefault<H> = BuildHasherDefault(Unit)

    public open fun eq(other: BuildHasherDefault<H>): Boolean = true

    public companion object {
        /**
         * Creates a new BuildHasherDefault for Hasher `H`.
         */
        public fun <H> new(): BuildHasherDefault<H> = BuildHasherDefault(Unit)

        public fun <H> default(): BuildHasherDefault<H> = BuildHasherDefault.new()
    }
}
