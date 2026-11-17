package io.github.kotlinmania.rustlang.core.fmt

/**
 * Possible alignments returned by `Formatter.align`
 */
public sealed class Alignment {
    /**
     * Indication that contents should be left-aligned.
     */
    public data object Left : Alignment()
    /**
     * Indication that contents should be right-aligned.
     */
    public data object Right : Alignment()
    /**
     * Indication that contents should be center-aligned.
     */
    public data object Center : Alignment()

    public fun rustEquals(other: Alignment): Boolean = this == other
}

/**
 * The type returned by formatter methods.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Triangle(
 *     public var a: Float,
 *     public var b: Float,
 *     public var c: Float,
 * ) {
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         return run { f.append("(${this.a}, ${this.b}, ${this.c})"); Result.success(Unit) }
 *     }
 * }
 * val pythagoreanTriple = Triangle(a = 3.0f, b = 4.0f, c = 5.0f)
 * check("$pythagoreanTriple" == "(3, 4, 5)")
 * ```
 */
public typealias Result = io.github.kotlinmania.rustlang.core.result.Result<Unit, Error>

/**
 * A trait for writing or formatting into Unicode-accepting buffers or streams.
 *
 * This trait only accepts UTF-8–encoded data and is not [flushable]. If you only
 * want to accept Unicode and you don't need flushing, you should implement this trait;
 * otherwise you should implement [std.io.Write].
 *
 * [std.io.Write]: ../../std/io/trait.Write.html
 * [flushable]: ../../std/io/trait.Write.html#tymethod.flush
 */
public interface Write<T> {
    /**
     * Writes a string slice into this writer, returning whether the write
     * succeeded.
     *
     * This method can only succeed if the entire string slice was successfully
     * written, and this method will not return until all data has been
     * written or an error occurs.
     *
     * # Errors
     *
     * This function will return an instance of [`std.fmt.Error`][Error] on error.
     *
     * The purpose of that error is to abort the formatting operation when the underlying
     * destination encounters some error preventing it from accepting more text;
     * in particular, it does not communicate any information about *what* error occurred.
     * It should generally be propagated rather than handled, at least when implementing
     * formatting traits.
     *
     * # Examples
     *
     * ```kotlin
     * fun <W : Write> writer(f: W, s: String): Result<Unit, Error> {
     *     return run { f.append(s); Result.success(Unit) }
     * }
     * var buf = ""
     * writer(buf, "hola").getOrElse { return Result.failure(it) }
     * check(buf == "hola")
     * ```
     */
    public fun writeStr(s: String): Result

    /**
     * Writes a [char] into this writer, returning whether the write succeeded.
     *
     * A single [char] may be encoded as more than one byte.
     * This method can only succeed if the entire byte sequence was successfully
     * written, and this method will not return until all data has been
     * written or an error occurs.
     *
     * # Errors
     *
     * This function will return an instance of [Error] on error.
     *
     * # Examples
     *
     * ```kotlin
     * fun <W : Write> writer(f: W, c: Char): Result<Unit, Error> {
     *     return run { f.append(c); Result.success(Unit) }
     * }
     * var buf = ""
     * writer(buf, 'a').getOrElse { return Result.failure(it) }
     * writer(buf, 'b').getOrElse { return Result.failure(it) }
     * check(buf == "ab")
     * ```
     */
    public fun writeChar(c: Char): Result = this.append(c.encodeUtf8(List((Char.MAX_LEN_UTF8).toInt()) { 0 }))

    /**
     * Glue for usage of the [write] macro with implementors of this trait.
     *
     * This method should generally not be invoked manually, but rather through
     * the [write] macro itself.
     *
     * # Errors
     *
     * This function will return an instance of [Error] on error. Please see
     * [writeStr](Write.writeStr) for details.
     *
     * # Examples
     *
     * ```kotlin
     * fun <W : Write> writer(f: W, s: String): Result<Unit, Error> {
     *     return f.writeFmt("$s")
     * }
     * var buf = ""
     * writer(buf, "world").getOrElse { return Result.failure(it) }
     * check(buf == "world")
     * ```
     */
    public fun writeFmt(args: Arguments): Result {
        // We use a specialization for `Sized` types to avoid an indirection
        // through `self`
        interface SpecWriteFmt {
            public fun specWriteFmt(args1: Arguments): Result
        }
        fun <W : Write<W>> W.specWriteFmt(args1: Arguments): Result =
            when (val matchVal = args1.asStaticallyKnownStr()) {
                null -> {
                    write(this, args1)
                }
                else -> {
                    val s = matchVal
                    this.append(s)
                }
            }
        return this.specWriteFmt(args)
    }
}

/**
 * The signedness of a [Formatter] (or of a [FormattingOptions]).
 */
public sealed class Sign {
    /**
     * Represents the `+` flag.
     */
    public data object Plus : Sign()
    /**
     * Represents the `-` flag.
     */
    public data object Minus : Sign()

    public fun rustEquals(other: Sign): Boolean = this == other
}

/**
 * Specifies whether the [Debug] trait should use lower-/upper-case
 * hexadecimal or normal integers.
 */
public sealed class DebugAsHex {
    /**
     * Use lower-case hexadecimal integers for the `Debug` trait (like [the `x` type](../../std/fmt/index.html#formatting-traits)).
     */
    public data object Lower : DebugAsHex()
    /**
     * Use upper-case hexadecimal integers for the `Debug` trait (like [the `X` type](../../std/fmt/index.html#formatting-traits)).
     */
    public data object Upper : DebugAsHex()

    public fun rustEquals(other: DebugAsHex): Boolean = this == other
}

public val SIGN_PLUS_FLAG: UInt = 1u shl 21

public val SIGN_MINUS_FLAG: UInt = 1u shl 22

public val ALTERNATE_FLAG: UInt = 1u shl 23

public val SIGN_AWARE_ZERO_PAD_FLAG: UInt = 1u shl 24

public val DEBUG_LOWER_HEX_FLAG: UInt = 1u shl 25

public val DEBUG_UPPER_HEX_FLAG: UInt = 1u shl 26

public val WIDTH_FLAG: UInt = 1u shl 27

public val PRECISION_FLAG: UInt = 1u shl 28

public val ALIGN_BITS: UInt = 0x3u shl 29

public val ALIGN_LEFT: UInt = 0u shl 29

public val ALIGN_RIGHT: UInt = 1u shl 29

public val ALIGN_CENTER: UInt = 2u shl 29

public val ALIGN_UNKNOWN: UInt = 3u shl 29

/**
 * `?` formatting.
 *
 * `Debug` should format the output in a programmer-facing, debugging context.
 *
 * Generally speaking, you should just `derive` a `Debug` implementation.
 *
 * When used with the alternate format specifier `# ?`, the output is pretty-printed.
 *
 * For more information on formatters, see [the module-level documentation][module].
 *
 * [module]: ../../std/fmt/index.html
 *
 * This trait can be used with `@derive` if all fields implement `Debug`. When
 * `derive`d for structs, it will use the name of the `class`, then `(`, then a
 * comma-separated list of each field's name and `Debug` value, then `)`. For
 * `sealed class`s, it will use the name of the variant and, if applicable, `(`, then the
 * `Debug` values of the fields, then `)`.
 *
 * # Stability
 *
 * Derived `Debug` formats are not stable, and so may change with future Rust
 * versions. Additionally, `Debug` implementations of types provided by the
 * standard library (`std`, `core`, `alloc`, etc.) are not stable, and
 * may also change with future Rust versions.
 *
 * # Examples
 *
 * Deriving an implementation:
 *
 * ```kotlin
 * open class Point(public var x: Int, public var y: Int)
 * val origin = Point(x = 0, y = 0)
 * check("The origin is: $origin" == "The origin is: Point { x: 0, y: 0 }")
 * ```
 *
 * Manually implementing:
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Point(public var x: Int, public var y: Int) {
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         return DebugStruct(f, "Point").field("x", this.x).field("y", this.y).finish()
 *     }
 * }
 * val origin = Point(x = 0, y = 0)
 * check("The origin is: $origin" == "The origin is: Point { x: 0, y: 0 }")
 * ```
 *
 * There are a number of helper methods on the [Formatter] struct to help you with manual
 * implementations, such as [debugStruct].
 *
 * [debugStruct]: Formatter.debugStruct
 *
 * Types that do not wish to use the standard suite of debug representations
 * provided by the `Formatter` trait (`debugStruct`, `debugTuple`,
 * `debugList`, `debugSet`, `debugMap`) can do something totally custom by
 * manually writing an arbitrary representation to the `Formatter`.
 *
 * ```kotlin
 * fun Point.fmt(f: fmt.Formatter): Result<Unit> {
 *     return run { f.append("Point [${this.x} ${this.y}]"); Result.success(Unit) }
 * }
 * ```
 *
 * `Debug` implementations using either `derive` or the debug builder API
 * on [Formatter] support pretty-printing using the alternate flag: `( : # ? )`.
 *
 * Pretty-printing with `# ?`:
 *
 * ```kotlin
 * open class Point(public var x: Int, public var y: Int)
 * val origin = Point(x = 0, y = 0)
 * val expected = "The origin is: Point {\n    x: 0,\n    y: 0,\n}"
 * check("The origin is: $origin" == expected)
 * ```
 */
public interface Debug {
    /**
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * public class DebugTuple(
     *     private val out: StringBuilder,
     *     private val name: String,
     * ) {
     *     private val fields: MutableList<String> = mutableListOf()
     *
     *     public fun field(value: Any?): DebugTuple =
     *         apply {
     *             fields.add(value.toString())
     *         }
     *
     *     public fun finish() {
     *         if (fields.isEmpty()) {
     *             out.append(name)
     *         } else if (name.isEmpty()) {
     *             out.append("(${fields.joinToString(", ")})")
     *         } else {
     *             out.append("$name(${fields.joinToString(", ")})")
     *         }
     *     }
     * }
     *
     * public fun doctestEntry() {
     *     open class Position(public var longitude: Float, public var latitude: Float) {
     *         public open fun fmt(f: StringBuilder): Result<Unit> {
     *             return DebugTuple(f, "").field(this.longitude).field(this.latitude).finish()
     *         }
     *     }
     *     val position = Position(longitude = 1.987f, latitude = 2.983f)
     *     check("$position" == "(1.987, 2.983)")
     *     check("$position" == "(\n    1.987,\n    2.983,\n)")
     * }
     * ```
     */
    public fun fmt(f: Formatter): Result
}

/**
 * Format trait for an empty format, `()`.
 *
 * Implementing this trait for a type will automatically implement the
 * [`ToString`][tostring] trait for the type, allowing the usage
 * of the [`toString`][tostringFunction] method. Prefer implementing
 * the `Display` trait for a type, rather than [`ToString`][tostring].
 *
 * `Display` is similar to [Debug], but `Display` is for user-facing
 * output, and so cannot be derived.
 *
 * For more information on formatters, see [the module-level documentation][module].
 *
 * [module]: ../../std/fmt/index.html
 * [tostring]: ../../std/string/trait.ToString.html
 * [tostringFunction]: ../../std/string/trait.ToString.html#tymethod.toString
 *
 * # Completeness and parseability
 *
 * `Display` for a type might not necessarily be a lossless or complete representation of the type.
 * It may omit internal state, precision, or other information the type does not consider important
 * for user-facing output, as determined by the type. As such, the output of `Display` might not be
 * possible to parse, and even if it is, the result of parsing might not exactly match the original
 * value.
 *
 * However, if a type has a lossless `Display` implementation whose output is meant to be
 * conveniently machine-parseable and not just meant for human consumption, then the type may wish
 * to accept the same format in `FromStr`, and document that usage. Having both `Display` and
 * `FromStr` implementations where the result of `Display` cannot be parsed with `FromStr` may
 * surprise users.
 *
 * # Internationalization
 *
 * Because a type can only have one `Display` implementation, it is often preferable
 * to only implement `Display` when there is a single most "obvious" way that
 * values can be formatted as text. This could mean formatting according to the
 * "invariant" culture and "undefined" locale, or it could mean that the type
 * display is designed for a specific culture/locale, such as developer logs.
 *
 * If not all values have a justifiably canonical textual format or if you want
 * to support alternative formats not covered by the standard set of possible
 * [formatting traits], the most flexible approach is display adapters: methods
 * like [str.escapeDefault] or [Path.display] which create a wrapper
 * implementing `Display` to output the specific display format.
 *
 * [formatting traits]: ../../std/fmt/index.html#formatting-traits
 * [Path.display]: ../../std/path/struct.Path.html#method.display
 *
 * # Examples
 *
 * Implementing `Display` on a type:
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Point(public var x: Int, public var y: Int) {
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         return run { f.append("(${this.x}, ${this.y})"); Result.success(Unit) }
 *     }
 * }
 * val origin = Point(x = 0, y = 0)
 * check("The origin is: $origin" == "The origin is: (0, 0)")
 * ```
 */
public interface Display {
    /**
     * # Examples
     *
     * ```kotlin
     * import std.fmt
     *
     * open class Position(public var longitude: Float, public var latitude: Float) {
     *     public open fun fmt(f: StringBuilder): Result<Unit> {
     *         return run { f.append("(${this.longitude}, ${this.latitude})"); Result.success(Unit) }
     *     }
     * }
     * check("(1.987, 2.983)" == "${Position(longitude = 1.987f, latitude = 2.983f)}")
     * ```
     */
    public fun fmt(f: Formatter): Result
}

/**
 * `o` formatting.
 *
 * The `Octal` trait should format its output as a number in base-8.
 *
 * For primitive signed integers (`Byte` to `Long`, and `Long`),
 * negative values are formatted as the two’s complement representation.
 *
 * The alternate flag, `#`, adds a `0o` in front of the output.
 *
 * For more information on formatters, see [the module-level documentation][module].
 *
 * [module]: ../../std/fmt/index.html
 *
 * # Examples
 *
 * Basic usage with `Int`:
 *
 * ```kotlin
 * val x = 42
 * // 42 is '52' in octal
 * check("$x" == "52")
 * check("$x" == "0o52")
 * check("${-16}" == "37777777760")
 * ```
 *
 * Implementing `Octal` on a type:
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Length(public var value: Int) {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         val value = this.value
 *         return run { f.append((value).toString()); Result.success(Unit) }
 *         // delegate to i32's implementation
 *     }
 * }
 * val l = Length(9)
 * check("l as octal is: $l" == "l as octal is: 11")
 * check("l as octal is: $l" == "l as octal is: 0o0011")
 * ```
 */
public interface Octal {
    public fun fmt(f: Formatter): Result
}

/**
 * `b` formatting.
 *
 * The `Binary` trait should format its output as a number in binary.
 *
 * For primitive signed integers ([i8] to [i128], and [isize]),
 * negative values are formatted as the two’s complement representation.
 *
 * The alternate flag, `#`, adds a `0b` in front of the output.
 *
 * For more information on formatters, see [the module-level documentation][module].
 *
 * [module]: ../../std/fmt/index.html
 *
 * # Examples
 *
 * Basic usage with [i32]:
 *
 * ```kotlin
 * val x = 42
 * // 42 is '101010' in binary
 * check("$x" == "101010")
 * check("$x" == "0b101010")
 * check("${-16}" == "11111111111111111111111111110000")
 * ```
 *
 * Implementing `Binary` on a type:
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Length(public var value: Int) {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         val value = this.value
 *         return run { f.append((value).toString()); Result.success(Unit) }
 *         // delegate to i32's implementation
 *     }
 * }
 * val l = Length(107)
 * check("l as binary is: $l" == "l as binary is: 1101011")
 * check("l as binary is: $l" == "l as binary is: 0b00000000000000000000000001101011")
 * ```
 */
public interface Binary {
    public fun fmt(f: Formatter): Result
}

/**
 * `x` formatting.
 *
 * The `LowerHex` trait should format its output as a number in hexadecimal, with `a` through `f`
 * in lower case.
 *
 * For primitive signed integers (`Byte` to `Long`, and `Long`),
 * negative values are formatted as the two’s complement representation.
 *
 * The alternate flag, `#`, adds a `0x` in front of the output.
 *
 * For more information on formatters, see [the module-level documentation][module].
 *
 * [module]: ../../std/fmt/index.html
 *
 * # Examples
 *
 * Basic usage with `Int`:
 *
 * ```kotlin
 * val y = 42
 * // 42 is '2a' in hex
 * check("$y" == "2a")
 * check("$y" == "0x2a")
 * check("${-16}" == "fffffff0")
 * ```
 *
 * Implementing `LowerHex` on a type:
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Length(public var value: Int) {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         val value = this.value
 *         return run { f.append((value).toString()); Result.success(Unit) }
 *         // delegate to i32's implementation
 *     }
 * }
 * val l = Length(9)
 * check("l as hex is: $l" == "l as hex is: 9")
 * check("l as hex is: $l" == "l as hex is: 0x00000009")
 * ```
 */
public interface LowerHex {
    public fun fmt(f: Formatter): Result
}

/**
 * `X` formatting.
 *
 * The `UpperHex` trait should format its output as a number in hexadecimal, with `A` through `F`
 * in upper case.
 *
 * For primitive signed integers (`Byte` to `Long`, and `Long`),
 * negative values are formatted as the two’s complement representation.
 *
 * The alternate flag, `#`, adds a `0x` in front of the output.
 *
 * For more information on formatters, see [the module-level documentation][module].
 *
 * [module]: ../../std/fmt/index.html
 *
 * # Examples
 *
 * Basic usage with `Int`:
 *
 * ```kotlin
 * val y = 42
 * // 42 is '2A' in hex
 * check("$y" == "2A")
 * check("$y" == "0x2A")
 * check("${-16}" == "FFFFFFF0")
 * ```
 *
 * Implementing `UpperHex` on a type:
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Length(public var value: Int) {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         val value = this.value
 *         return run { f.append((value).toString()); Result.success(Unit) }
 *         // delegate to i32's implementation
 *     }
 * }
 * val l = Length(Int.MAX_VALUE)
 * check("l as hex is: $l" == "l as hex is: 7FFFFFFF")
 * check("l as hex is: $l" == "l as hex is: 0x7FFFFFFF")
 * ```
 */
public interface UpperHex {
    public fun fmt(f: Formatter): Result
}

/**
 * `p` formatting.
 *
 * The `Pointer` trait should format its output as a memory location. This is commonly presented
 * as hexadecimal. For more information on formatters, see [the module-level documentation][module].
 *
 * Printing of pointers is not a reliable way to discover how Rust programs are implemented.
 * The act of reading an address changes the program itself, and may change how the data is represented
 * in memory, and may affect which optimizations are applied to the code.
 *
 * The printed pointer values are not guaranteed to be stable nor unique identifiers of objects.
 * Rust allows moving values to different memory locations, and may reuse the same memory locations
 * for different purposes.
 *
 * There is no guarantee that the printed value can be converted back to a pointer.
 *
 * [module]: ../../std/fmt/index.html
 *
 * # Examples
 *
 * Basic usage with `Int`:
 *
 * ```kotlin
 * val x = 42
 * val address = "$x"
 * // this produces something like '0x7f06092ac6d0'
 * ```
 *
 * Implementing `Pointer` on a type:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
 * import io.github.kotlinmania.rustlang.core.mem.get
 * import std.fmt
 *
 * open class Length(public var value: Int) {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         // use `as` to convert to a `* const T`, which implements Pointer, which we can use
 *         val ptr = constPtrFromRustCast(this)
 *         return run { f.append((ptr).toString()); Result.success(Unit) }
 *     }
 * }
 * val l = Length(42)
 * println("l is in memory here: $l")
 * val lPtr = "$l"
 * check(lPtr.len() == 18)
 * check(lPtr[0 until 2] == "0x")
 * ```
 */
public interface Pointer {
    public fun fmt(f: Formatter): Result
}

/**
 * `e` formatting.
 *
 * The `LowerExp` trait should format its output in scientific notation with a lower-case `e`.
 *
 * For more information on formatters, see [the module-level documentation][module].
 *
 * [module]: ../../std/fmt/index.html
 *
 * # Examples
 *
 * Basic usage with `Double`:
 *
 * ```kotlin
 * val x = 42.0
 * // 42.0 is '4.2e1' in scientific notation
 * check("$x" == "4.2e1")
 * ```
 *
 * Implementing `LowerExp` on a type:
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Length(public var value: Int) {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         val value = Double.from(this.value)
 *         return run { f.append((value).toString()); Result.success(Unit) }
 *         // delegate to f64's implementation
 *     }
 * }
 * val l = Length(100)
 * check("l in scientific notation is: $l" == "l in scientific notation is: 1e2")
 * check("l in scientific notation is: $l" == "l in scientific notation is: 001e2")
 * ```
 */
public interface LowerExp {
    public fun fmt(f: Formatter): Result
}

/**
 * `E` formatting.
 *
 * The `UpperExp` trait should format its output in scientific notation with an upper-case `E`.
 *
 * For more information on formatters, see [the module-level documentation][module].
 *
 * [module]: ../../std/fmt/index.html
 *
 * # Examples
 *
 * Basic usage with `Double`:
 *
 * ```kotlin
 * val x = 42.0
 * // 42.0 is '4.2E1' in scientific notation
 * check("$x" == "4.2E1")
 * ```
 *
 * Implementing `UpperExp` on a type:
 *
 * ```kotlin
 * import std.fmt
 *
 * open class Length(public var value: Int) {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     public open fun fmt(f: StringBuilder): Result<Unit> {
 *         val value = Double.from(this.value)
 *         return run { f.append((value).toString()); Result.success(Unit) }
 *         // delegate to f64's implementation
 *     }
 * }
 * val l = Length(100)
 * check("l in scientific notation is: $l" == "l in scientific notation is: 1E2")
 * check("l in scientific notation is: $l" == "l in scientific notation is: 001E2")
 * ```
 */
public interface UpperExp {
    public fun fmt(f: Formatter): Result
}
