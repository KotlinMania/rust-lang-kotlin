// port-lint: source default.rs
package io.github.kotlinmania.rustlang.core.default

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ascii.Char as AsciiChar

// The `Default` trait for types with a default value.

/**
 * A trait for giving a type a useful default value.
 *
 * Sometimes, you want to fall back to some kind of default value, and
 * don't particularly care what it is. This comes up often with `class`s
 * that define a set of options:
 *
 * ```kotlin
 * open class SomeOptions(public var foo: Int, public var bar: Float)
 * ```
 *
 * How can we define some default values? You can use `Default`:
 *
 * ```kotlin
 * open class SomeOptions(public var foo: Int, public var bar: Float)
 * fun main() {
 *     val options: SomeOptions = Default.default()
 * }
 * ```
 *
 * Now, you get all of the default values. Rust implements `Default` for various primitive types.
 *
 * If you want to override a particular option, but still retain the other defaults:
 *
 * ```kotlin
 * fun main() {
 *     val options = Default.default().copy(foo = 42)
 * }
 * ```
 *
 * ## Derivable
 *
 * This trait can be used with `@derive` if all of the type's fields implement
 * `Default`. When `derive`d, it will use the default value for each field's type.
 *
 * ### `sealed class`s
 *
 * When using `@derive(Default)` on an `sealed class`, you need to choose which unit variant will be
 * default. You do this by placing the `@default` attribute on the variant.
 *
 * ```kotlin
 * sealed class Kind {
 *     @default
 *     public data object A : Kind()
 *     public data object B : Kind()
 *     public data object C : Kind()
 * }
 * ```
 *
 * You cannot use the `@default` attribute on non-unit or non-exhaustive variants.
 *
 * The `@default` attribute was stabilized in Rust 1.62.0.
 *
 * ## How can I implement `Default`?
 *
 * Provide an implementation for the `default()` method that returns the value of
 * your type that should be the default:
 *
 * ```kotlin
 * sealed class Kind {
 *     public data object A : Kind()
 *     public data object B : Kind()
 *     public data object C : Kind()
 *
 *     public open fun default(): Kind {
 *         return Kind.A
 *     }
 * }
 * ```
 *
 * # Examples
 *
 * ```kotlin
 * open class SomeOptions(public var foo: Int, public var bar: Float)
 * ```
 */
public interface Default<T> {
    /**
     * Returns the "default value" for a type.
     *
     * Default values are often some kind of initial value, identity value, or anything else that
     * may make sense as a default.
     *
     * # Examples
     *
     * Using built-in default values:
     *
     * ```kotlin
     * val i: Byte = Default.default()
     * val (x: String?, y: Double) = Default.default()
     * val (a, b, c, d) = Default.default()
     * ```
     *
     * Making your own:
     *
     * ```kotlin
     * sealed class Kind {
     *     public data object A : Kind()
     *     public data object B : Kind()
     *     public data object C : Kind()
     *
     *     public open fun default(): Kind {
     *         return Kind.A
     *     }
     * }
     * ```
     */
    public fun default(): T
}

/**
 * Returns the default value of `()`
 */
public fun default() {}

/**
 * Returns the default value of `false`
 */
public fun defaultBoolean(): Boolean = false

/**
 * Returns the default value of `\x00`
 */
public fun defaultChar(): Char = '\u0000'

/**
 * Returns the default value of `Null`
 */
public fun defaultAsciiChar(): AsciiChar = AsciiChar.Null

/**
 * Returns the default value of `0`
 */
public fun defaultInt(): Int = 0

/**
 * Returns the default value of `0`
 */
public fun defaultUByte(): UByte = 0u

/**
 * Returns the default value of `0`
 */
public fun defaultUShort(): UShort = 0u

/**
 * Returns the default value of `0`
 */
public fun defaultUInt(): UInt = 0u

/**
 * Returns the default value of `0`
 */
public fun defaultULong(): ULong = 0uL

/**
 * Returns the default value of `0`
 */
public fun defaultU128(): U128 = U128(0x0uL, 0x0uL)

/**
 * Returns the default value of `0`
 */
public fun defaultByte(): Byte = 0

/**
 * Returns the default value of `0`
 */
public fun defaultShort(): Short = 0

/**
 * Returns the default value of `0`
 */
public fun defaultLong(): Long = 0L

/**
 * Returns the default value of `0`
 */
public fun defaultI128(): I128 = I128(0x0L, 0x0uL)

/**
 * Returns the default value of `0.0`
 */
public fun defaultF16(): F16 = 0.0

/**
 * Returns the default value of `0.0`
 */
public fun defaultFloat(): kotlin.Float = 0.0f

/**
 * Returns the default value of `0.0`
 */
public fun defaultDouble(): Double = 0.0

/**
 * Returns the default value of `0.0`
 */
public fun defaultF128(): F128 = 0.0
