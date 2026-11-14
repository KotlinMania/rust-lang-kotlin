// port-lint: source macros.rs
package io.github.kotlinmania.rustlang.std

// Standard library macros
//
// This module contains a set of macros which are exported from the standard
// library. Each macro is available for use when linking against the standard
// library.

private const val PRINT_TEMPLATE: String = "{io.print(formatArgs(\$(\$arg)*)); }"

private const val PRINTLN_TEMPLATE: String = "print(\"\\n\")"

private const val PRINTLN_TEMPLATE_1: String = "{io.print(formatArgsNl(\$(\$arg)*)); }"

private const val EPRINT_TEMPLATE: String = "{io.eprint(formatArgs(\$(\$arg)*)); }"

private const val EPRINTLN_TEMPLATE: String = "eprint(\"\\n\")"

private const val EPRINTLN_TEMPLATE_1: String = "{io.eprint(formatArgsNl(\$(\$arg)*)); }"

private const val DBG_TEMPLATE: String = "eprintln(\"[{}:{}:{}]\", file(), line(), column())"

private const val DBG_TEMPLATE_1: String = "when \$value {tmp=> {eprintln(\"[{}:{}:{}] {} = {:#?}\", file(), line(), column(), stringify(\$value), && tmp asTarget& dyn fmt.Debug, ); tmp}}"

private const val REPETITION_UTILS_TEMPLATE: String = "{[\$(repetitionUtils(@ replace \$tokens=>())),*].len()}"

private const val REPETITION_UTILS_TEMPLATE_1: String = "\$y"

private const val HASH_MAP_TEMPLATE: String = "{collections.HashMap.new()}"

private const val HASH_MAP_TEMPLATE_1: String = "{val var map= collections.HashMap.withCapacity(const {repetitionUtils(@ count\$(\$key),*)}); \$(map.insert(\$key, \$value); )* map}"

public fun panic(arg: String = ""): String =
    if (arg.isEmpty()) {
        ""
    } else {
        ""
    }

/**
 * Prints to the standard output.
 *
 * Equivalent to the [println] macro except that a newline is not printed at
 * the end of the message.
 *
 * Note that stdout is frequently line-buffered by default so it may be
 * necessary to use [`io.stdout.flush`][flush] to ensure the output is emitted
 * immediately.
 *
 * The `print` macro will lock the standard output on each call. If you call
 * `print` within a hot loop, this behavior may be the bottleneck of the loop.
 * To avoid this, lock stdout with [`io.stdout.lock`][lock]:
 * ```kotlin
 * import std.io.Write
 * import std.io.stdout
 *
 * var lock = stdout().lock()
 * lock.append("hello world").unwrap()
 * ```
 *
 * Use `print` only for the primary output of your program. Use
 * [eprint] instead to print error and progress messages.
 *
 * See the formatting documentation in [`std.fmt`](fmt)
 * for details of the macro argument syntax.
 *
 * # Panics
 *
 * Panics if writing to `io.stdout()` fails.
 *
 * Writing to non-blocking stdout can cause an error, which will lead
 * this macro to panic.
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.Write
 *
 * print("this ")
 * print("will ")
 * print("be ")
 * print("on ")
 * print("the ")
 * print("same ")
 * print("line ")
 * io.stdout().flush().unwrap()
 * print("this string has a newline, why not choose println instead?\n")
 * io.stdout().flush().unwrap()
 * ```
 */

public fun print(arg: String = ""): String =
    PRINT_TEMPLATE.replace("\$arg", arg)

/**
 * Prints to the standard output, with a newline.
 *
 * On all platforms, the newline is the LINE FEED character (`\n`/`U + 000A`) alone
 * (no additional CARRIAGE RETURN (`\r`/`U + 000D`)).
 *
 * This macro uses the same syntax as [format], but writes to the standard output instead.
 * See [std.fmt] for more information.
 *
 * The `println` macro will lock the standard output on each call. If you call
 * `println` within a hot loop, this behavior may be the bottleneck of the loop.
 * To avoid this, lock stdout with [`io.stdout.lock`][lock]:
 * ```kotlin
 * import std.io.Write
 * import std.io.stdout
 *
 * var lock = stdout().lock()
 * lock.appendLine("hello world").unwrap()
 * ```
 *
 * Use `println` only for the primary output of your program. Use
 * [eprintln] instead to print error and progress messages.
 *
 * See the formatting documentation in [`std.fmt`](fmt)
 * for details of the macro argument syntax.
 *
 * # Panics
 *
 * Panics if writing to [io.stdout] fails.
 *
 * Writing to non-blocking stdout can cause an error, which will lead
 * this macro to panic.
 *
 * # Examples
 *
 * ```kotlin
 * println("")
 * // prints just a newline
 * println("hello there!")
 * println("format " + ("some").toString() + " arguments")
 * val localVariable = "some"
 * println("format $localVariable arguments")
 * ```
 */

public fun println(arg: String = ""): String =
    when {
        arg.isNotEmpty() -> PRINTLN_TEMPLATE_1.replace("\$arg", arg)
        else -> PRINTLN_TEMPLATE
    }

/**
 * Prints to the standard error.
 *
 * Equivalent to the [print] macro, except that output goes to
 * [io.stderr] instead of [io.stdout]. See [print] for
 * example usage.
 *
 * Use `eprint` only for error and progress messages. Use `print`
 * instead for the primary output of your program.
 *
 * See the formatting documentation in [`std.fmt`](fmt)
 * for details of the macro argument syntax.
 *
 * # Panics
 *
 * Panics if writing to `io.stderr` fails.
 *
 * Writing to non-blocking stderr can cause an error, which will lead
 * this macro to panic.
 *
 * # Examples
 *
 * ```kotlin
 * print("Error: Could not complete task")
 * ```
 */

public fun eprint(arg: String = ""): String =
    EPRINT_TEMPLATE.replace("\$arg", arg)

/**
 * Prints to the standard error, with a newline.
 *
 * Equivalent to the [println] macro, except that output goes to
 * [io.stderr] instead of [io.stdout]. See [println] for
 * example usage.
 *
 * Use `eprintln` only for error and progress messages. Use `println`
 * instead for the primary output of your program.
 *
 * See the formatting documentation in [`std.fmt`](fmt)
 * for details of the macro argument syntax.
 *
 * # Panics
 *
 * Panics if writing to `io.stderr` fails.
 *
 * Writing to non-blocking stderr can cause an error, which will lead
 * this macro to panic.
 *
 * # Examples
 *
 * ```kotlin
 * println("Error: Could not complete task")
 * ```
 */

public fun eprintln(arg: String = ""): String =
    when {
        arg.isNotEmpty() -> EPRINTLN_TEMPLATE_1.replace("\$arg", arg)
        else -> EPRINTLN_TEMPLATE
    }

/**
 * Prints and returns the value of a given expression for quick and dirty
 * debugging.
 *
 * An example:
 *
 * ```kotlin
 * val a = 2
 * val b = dbg(a * 2) + 1
 * // ^-- prints: [src/main.rs:2:9] a * 2 = 4
 * check(b == 5)
 * ```
 *
 * The macro works by using the `Debug` implementation of the type of
 * the given expression to print the value to [stderr] along with the
 * source location of the macro invocation as well as the source code
 * of the expression.
 *
 * Invoking the macro on an expression moves and takes ownership of it
 * before returning the evaluated expression unchanged. If the type
 * of the expression does not implement `Copy` and you don't want
 * to give up ownership, you can instead borrow with `dbg(expr)`
 * for some expression `expr`.
 *
 * The `dbg` macro works exactly the same in release builds.
 * This is useful when debugging issues that only occur in release
 * builds or when debugging in release mode is significantly faster.
 *
 * Note that the macro is intended as a debugging tool and therefore you
 * should avoid having uses of it in version control for long periods
 * (other than in tests and similar).
 * Debug output from production code is better done with other facilities
 * such as the [debug] macro from the [log] crate.
 *
 * # Stability
 *
 * The exact output printed by this macro should not be relied upon
 * and is subject to future changes.
 *
 * # Panics
 *
 * Panics if writing to `io.stderr` fails.
 *
 * # Further examples
 *
 * With a method call:
 *
 * ```kotlin
 * fun foo(n: Int) {
 *     when (val matchVal = dbg(((n).toLong() - (4).toLong()).takeIf { it in Int.MIN_VALUE..Int.MAX_VALUE }?.toInt())) {
 *         null -> {}
 *         else -> {
 *             // ...
 *         }
 *     }
 * }
 * foo(3)
 * ```
 *
 * This prints to [stderr]:
 *
 * ```text,ignore
 * [src/main.rs:2:22] n.checkedSub(4) = None
 * ```
 *
 * Naive factorial implementation:
 *
 * ```kotlin
 * fun factorial(n: UInt): UInt {
 *     return if (dbg(n <= 1u)) {
 *         dbg(1u)
 *     } else {
 *         dbg(n * factorial(n - 1u))
 *     }
 * }
 * dbg(factorial(4u))
 * ```
 *
 * This prints to [stderr]:
 *
 * ```text,ignore
 * [src/main.rs:2:8] n <= 1 = false
 * [src/main.rs:2:8] n <= 1 = false
 * [src/main.rs:2:8] n <= 1 = false
 * [src/main.rs:2:8] n <= 1 = true
 * [src/main.rs:3:9] 1 = 1
 * [src/main.rs:7:9] n * factorial(n - 1) = 2
 * [src/main.rs:7:9] n * factorial(n - 1) = 6
 * [src/main.rs:7:9] n * factorial(n - 1) = 24
 * [src/main.rs:9:1] factorial(4) = 24
 * ```
 *
 * The `dbg(..)` macro moves the input:
 *
 * ```kotlin
 * / **
 *  * A wrapper around `Int` which importantly is not Copyable.
 *  * /
 * open class NoCopy(public var value: Int) {
 *     public var field0: Int
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 * }
 * val a = NoCopy(42)
 * dbg(a)
 * // <-- `a` is moved here.
 * dbg(a)
 * // <-- `a` is moved again; error!
 * ```
 *
 * You can also use `dbg()` without a value to just print the
 * file and line whenever it's reached.
 *
 * Finally, if you want to `dbg(..)` multiple values, it will treat them as
 * a tuple (and return it, too):
 *
 * ```kotlin
 * check(dbg(1, 2u) == Pair(1, 2))
 * ```
 *
 * However, a single argument with a trailing comma will still not be treated
 * as a tuple, following the convention of ignoring trailing commas in macro
 * invocations. You can use a 1-tuple directly if you need one:
 *
 * ```kotlin
 * check(1 == dbg(1u))
 * // trailing comma ignored
 * check(1 == dbg(1u))
 * // 1-tuple
 * ```
 *
 * [stderr]: https://en.wikipedia.org/wiki/StandardStreams#Standard_error_(stderr)
 * [debug]: https://docs.rs/log/ * /log/macro.debug.html
 * [log]: https://crates.io/crates/log
 */

public fun dbg(value: String = ""): String =
    when {
        value.isNotEmpty() -> DBG_TEMPLATE_1.replace("\$value", value)
        else -> DBG_TEMPLATE
    }

public fun repetitionUtils(tokens: String = "", x: String = "", y: String = "", clause: String = ""): String =
    when {
        clause == "@replace = >" && x.isNotEmpty() && y.isNotEmpty() -> REPETITION_UTILS_TEMPLATE_1.replace("\$y", y)
        clause == "@count" && tokens.isNotEmpty() -> REPETITION_UTILS_TEMPLATE.replace("\$tokens", tokens)
        else -> ""
    }

/**
 * Creates a [HashMap] containing the arguments.
 *
 * `hashMap` allows specifying the entries that make
 * up the [HashMap] where the key and value are separated by a `->`.
 *
 * The entries are separated by commas with a trailing comma being allowed.
 *
 * It is semantically equivalent to using repeated [HashMap.insert]
 * on a newly created hashmap.
 *
 * `hashMap` will attempt to avoid repeated reallocations by
 * using [HashMap.withCapacity].
 *
 * # Examples
 *
 * ```kotlin
 * @feature(hashMapMacro)
 * public fun doctestEntry() {
 *     val map = hashMap("key", "value", "key1", "value1")
 *     check(map.get("key") == "value")
 *     check(map.get("key1") == "value1")
 *     check(map.get("brrrrrrooooommm") == null)
 * }
 * ```
 *
 * And with a trailing comma
 *
 * ```kotlin
 * @feature(hashMapMacro)
 * public fun doctestEntry() {
 *     val map = hashMap("key", "value")
 *     check(map.get("key") == "value")
 * }
 * ```
 *
 * The key and value are moved into the HashMap.
 */

public fun hashMap(key: String = "", value: String = "", clause: String = ""): String =
    when {
        clause == "= >" && key.isNotEmpty() && value.isNotEmpty() -> arrayOf("\$key" to key, "\$value" to value).fold(HASH_MAP_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> HASH_MAP_TEMPLATE
    }
