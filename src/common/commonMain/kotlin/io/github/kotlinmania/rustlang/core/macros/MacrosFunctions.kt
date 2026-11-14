package io.github.kotlinmania.rustlang.core.macros

private const val ASSERT_EQ_TEMPLATE: String = "{when(\$left, \$right) {(leftVal, rightVal)=> {ifBranch(* leftVal==* rightVal) {val kind= panicking.AssertKind.Eq; panicking.assertFailed(kind, &* leftVal, &* rightVal, option.Option.None); }}}}"

private const val ASSERT_EQ_TEMPLATE_1: String = "{when(\$left, \$right) {(leftVal, rightVal)=> {ifBranch(* leftVal==* rightVal) {val kind= panicking.AssertKind.Eq; panicking.assertFailed(kind, &* leftVal, &* rightVal, option.Option.Some(formatArgs(\$(\$arg)+))); }}}}"

private const val ASSERT_NE_TEMPLATE: String = "{when(\$left, \$right) {(leftVal, rightVal)=> {ifBranch* leftVal==* rightVal {val kind= panicking.AssertKind.Ne; panicking.assertFailed(kind, &* leftVal, &* rightVal, option.Option.None); }}}}"

private const val ASSERT_NE_TEMPLATE_1: String = "{when(&(\$left), &(\$right)) {(leftVal, rightVal)=> {ifBranch* leftVal==* rightVal {val kind= panicking.AssertKind.Ne; panicking.assertFailed(kind, &* leftVal, &* rightVal, option.Option.Some(formatArgs(\$(\$arg)+))); }}}}"

private const val DEBUG_ASSERT_TEMPLATE: String = "ifBranch cfg(debugAssertions) {assert(\$(\$arg)*); }"

private const val DEBUG_ASSERT_EQ_TEMPLATE: String = "ifBranch cfg(debugAssertions) {assertEq(\$(\$arg)*); }"

private const val DEBUG_ASSERT_NE_TEMPLATE: String = "ifBranch cfg(debugAssertions) {assertNe(\$(\$arg)*); }"

private const val MATCHES_TEMPLATE: String = "when \$expression {\$pattern\$(ifBranch \$guard)?=> trueVal, unused=> falseVal}"

private const val TRY_TEMPLATE: String = "when \$expr {result.Result.Ok(value)=> value, result.Result.Err(err)=> {ret result.Result.Err(convert.From.from(err)); }}"

private const val WRITE_TEMPLATE: String = "\$dst.writeFmt(formatArgs(\$(\$arg)*))"

private const val WRITE_TEMPLATE_1: String = "compileError(\"requires a destination and format arguments, like `write!(dest, \\\"format string\\\", args...)`\")"

private const val WRITELN_TEMPLATE: String = "write(\$dst, \"\\n\")"

private const val WRITELN_TEMPLATE_1: String = "\$dst.writeFmt(formatArgsNl(\$(\$arg)*))"

private const val WRITELN_TEMPLATE_2: String = "compileError(\"requires a destination and format arguments, like `writeln!(dest, \\\"format string\\\", args...)`\")"

private const val UNSUPPORTED_BRANCH_TEMPLATE: String = "panicking.unimplementedDefaultMsg()"

private const val UNSUPPORTED_BRANCH_TEMPLATE_1: String = "panic(\"not implemented: {}\", formatArgs(\$(\$arg)+))"

private const val PENDING_TASK_TEMPLATE: String = "panicking.todoDefaultMsg()"

private const val PENDING_TASK_TEMPLATE_1: String = "panic(\"not yet implemented: {}\", formatArgs(\$(\$arg)+))"

public fun panic(arg: String = ""): String =
    if (arg.isEmpty()) {
        ""
    } else {
        ""
    }

/**
 * Asserts that two expressions are equal to each other (using [PartialEq]).
 *
 * Assertions are always checked in both debug and release builds, and cannot
 * be disabled. See [debugAssertEq] for assertions that are disabled in
 * release builds by default.
 *
 * On panic, this macro will print the values of the expressions with their
 * debug representations.
 *
 * Like [assert], this macro has a second form, where a custom
 * panic message can be provided.
 *
 * # Examples
 *
 * ```kotlin
 * val a = 3
 * val b = 1 + 2
 * check(a == b)
 * check(a == b)
 * ```
 */

public fun assertEq(left: String = "", right: String = "", arg: String = ""): String =
    when {
        left.isNotEmpty() && right.isNotEmpty() && arg.isNotEmpty() -> arrayOf("\$left" to left, "\$right" to right, "\$arg" to arg).fold(ASSERT_EQ_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        left.isNotEmpty() && right.isNotEmpty() -> arrayOf("\$left" to left, "\$right" to right).fold(ASSERT_EQ_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Asserts that two expressions are not equal to each other (using [PartialEq]).
 *
 * Assertions are always checked in both debug and release builds, and cannot
 * be disabled. See [debugAssertNe] for assertions that are disabled in
 * release builds by default.
 *
 * On panic, this macro will print the values of the expressions with their
 * debug representations.
 *
 * Like [assert], this macro has a second form, where a custom
 * panic message can be provided.
 *
 * # Examples
 *
 * ```kotlin
 * val a = 3
 * val b = 2
 * check(a != b)
 * check(a != b)
 * ```
 */

public fun assertNe(left: String = "", right: String = "", arg: String = ""): String =
    when {
        left.isNotEmpty() && right.isNotEmpty() && arg.isNotEmpty() -> arrayOf("\$left" to left, "\$right" to right, "\$arg" to arg).fold(ASSERT_NE_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        left.isNotEmpty() && right.isNotEmpty() -> arrayOf("\$left" to left, "\$right" to right).fold(ASSERT_NE_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

/**
 * Asserts that a boolean expression is `true` at runtime.
 *
 * This will invoke the [panic] macro if the provided expression cannot be
 * evaluated to `true` at runtime.
 *
 * Like [assert], this macro also has a second version, where a custom panic
 * message can be provided.
 *
 * # Uses
 *
 * Unlike [assert], `debugAssert` statements are only enabled in non
 * optimized builds by default. An optimized build will not execute
 * `debugAssert` statements unless `- C debug - assertions` is passed to the
 * compiler. This makes `debugAssert` useful for checks that are too
 * expensive to be present in a release build but may be helpful during
 * development. The result of expanding `debugAssert` is always type checked.
 *
 * An unchecked assertion allows a program in an inconsistent state to keep
 * running, which might have unexpected consequences but does not introduce
 * unsafety as long as this only happens in safe code. The performance cost
 * of assertions, however, is not measurable in general. Replacing [assert]
 * with `debugAssert` is thus only encouraged after thorough profiling, and
 * more importantly, only in safe code!
 *
 * # Examples
 *
 * ```kotlin
 * fun someExpensiveComputation(): Boolean {
 *     // Some expensive computation here
 *     return true
 * }
 * // the panic message for these assertions is the stringified value of the
 * // expression given.
 * check(true)
 * check(someExpensiveComputation())
 * // assert with a custom message
 * val x = true
 * check(x) { "x wasn't true!" }
 * val a = 3
 * val b = 27
 * check(a + b == 30) { "a = $a, b = $b" }
 * ```
 */

public fun debugAssert(arg: String = ""): String =
    DEBUG_ASSERT_TEMPLATE.replace("\$arg", arg)

/**
 * Asserts that two expressions are equal to each other.
 *
 * On panic, this macro will print the values of the expressions with their
 * debug representations.
 *
 * Unlike [assertEq], `debugAssertEq` statements are only enabled in non
 * optimized builds by default. An optimized build will not execute
 * `debugAssertEq` statements unless `- C debug - assertions` is passed to the
 * compiler. This makes `debugAssertEq` useful for checks that are too
 * expensive to be present in a release build but may be helpful during
 * development. The result of expanding `debugAssertEq` is always type checked.
 *
 * # Examples
 *
 * ```kotlin
 * val a = 3
 * val b = 1 + 2
 * check(a == b)
 * ```
 */

public fun debugAssertEq(arg: String = ""): String =
    DEBUG_ASSERT_EQ_TEMPLATE.replace("\$arg", arg)

/**
 * Asserts that two expressions are not equal to each other.
 *
 * On panic, this macro will print the values of the expressions with their
 * debug representations.
 *
 * Unlike [assertNe], `debugAssertNe` statements are only enabled in non
 * optimized builds by default. An optimized build will not execute
 * `debugAssertNe` statements unless `- C debug - assertions` is passed to the
 * compiler. This makes `debugAssertNe` useful for checks that are too
 * expensive to be present in a release build but may be helpful during
 * development. The result of expanding `debugAssertNe` is always type checked.
 *
 * # Examples
 *
 * ```kotlin
 * val a = 3
 * val b = 2
 * check(a != b)
 * ```
 */

public fun debugAssertNe(arg: String = ""): String =
    DEBUG_ASSERT_NE_TEMPLATE.replace("\$arg", arg)

/**
 * Returns whether the given expression matches the provided pattern.
 *
 * The pattern syntax is exactly the same as found in a match arm. The optional if guard can be
 * used to add additional checks that must be true for the matched value, otherwise this macro will
 * return `false`.
 *
 * When testing that a value matches a pattern, it's generally preferable to use
 * [assertMatches] as it will print the debug representation of the value if the assertion
 * fails.
 *
 * # Examples
 *
 * ```kotlin
 * val foo = 'f'
 * check(when (foo) {
 *     in 'A'..'Z', in 'a'..'z' -> true
 *     else -> false
 * })
 * val bar = 4
 * check(when {
 *     bar != null && bar > 2 -> {
 *         val x = bar
 *         true
 *     }
 *     else -> false
 * })
 * ```
 */

public fun matches(expression: String = "", pattern: String = "", guard: String = ""): String =
    arrayOf("\$expression" to expression, "\$pattern" to pattern, "\$guard" to guard).fold(MATCHES_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }

/**
 * Unwraps a result or propagates its error.
 *
 * The [`?` operator][propagating-errors] was added to replace `tryBlock`
 * and should be used instead. Furthermore, `try` is a reserved word
 * in Rust 2018, so if you must use it, you will need to use the
 * [raw-identifier syntax][ris]: `try`.
 *
 * [propagating-errors]: https://doc.rust-lang.org/book/ch09-02-recoverable-errors-with-result.html#a-shortcut-for-propagating-errors-the--operator
 * [ris]: ../rust-by-example/compatibility/rawIdentifiers.html
 *
 * `tryBlock` matches the given [Result]. In case of the `Ok` variant, the
 * expression has the value of the wrapped value.
 *
 * In case of the `Err` variant, it retrieves the inner error. `tryBlock` then
 * performs conversion using `From`. This provides automatic conversion
 * between specialized errors and more general ones. The resulting
 * error is then immediately returned.
 *
 * Because of the early return, `tryBlock` can only be used in functions that
 * return [Result].
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs.File
 * import std.io
 * import std.io.prelude.*
 *
 * sealed class MyError {
 *     public data object FileWriteError : MyError()
 *
 *     public open fun from(e: io.Error): MyError {
 *         return MyError.FileWriteError
 *     }
 * }
 * // The preferred method of quick returning Errors
 * fun writeToFileQuestion(): Result<Unit> {
 *     var file = File.create("myBestFriends.txt").getOrElse { return Result.failure(it) }
 *     file.writeAll("This is a list of my best friends.".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * // The previous method of quick returning Errors
 * fun writeToFileUsingTry(): Result<Unit> {
 *     var file = tryBlock(File.create("myBestFriends.txt"))
 *     tryBlock(file.writeAll("This is a list of my best friends.".encodeToByteArray()))
 *     return Result.success(Unit)
 * }
 * // This is equivalent to:
 * fun writeToFileUsingMatch(): Result<Unit> {
 *     var file = tryBlock(File.create("myBestFriends.txt"))
 *     run {
 *         val matchVal = file.writeAll("This is a list of my best friends.".encodeToByteArray())
 *         if (matchVal.isSuccess) {
 *             val v = matchVal.getOrThrow()
 *             v
 *         } else {
 *             val e = matchVal.exceptionOrNull()!!
 *             return Result.failure(From.from(e))
 *         }
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 */

public fun tryBlock(expr: String = ""): String =
    TRY_TEMPLATE.replace("\$expr", expr)

/**
 * Writes formatted data into a buffer.
 *
 * This macro accepts a 'writer', a format string, and a list of arguments. Arguments will be
 * formatted according to the specified format string and the result will be passed to the writer.
 * The writer may be any value with a `writeFmt` method; generally this comes from an
 * implementation of either the [fmt.Write] or the [io.Write] trait. The macro
 * returns whatever the `writeFmt` method returns; commonly a [fmt.Result], or an
 * [io.Result].
 *
 * See [std.fmt] for more information on the format string syntax.
 *
 * [std.fmt]: ../std/fmt/index.html
 * [io.Write]: ../std/io/trait.Write.html
 * [io.Result]: ../std/io/type.Result.html
 *
 * # Examples
 *
 * ```kotlin
 * fun main(): Result<Unit> {
 *     var w = mutableListOf()
 *     w.append("test")
 *     w.append("formatted " + ("arguments").toString())
 *     check(w == "testformatted arguments".encodeToByteArray())
 *     return Result.success(Unit)
 * }
 * ```
 *
 * A module can import both `std.fmt.Write` and `std.io.Write` and call `write` on objects
 * implementing either, as objects do not typically implement both. However, the module must
 * avoid conflict between the trait names, such as by importing them as `_` or otherwise renaming
 * them:
 *
 * ```kotlin
 * fun main(): Result<Unit> {
 *     var s = ""
 *     var v = mutableListOf()
 *     s.append(("abc").toString() + " " + (123).toString())
 *     // uses fmt.Write.writeFmt
 *     v.append("s = $s")
 *     // uses io.Write.writeFmt
 *     check(v == "s = \"abc 123\"".encodeToByteArray())
 *     return Result.success(Unit)
 * }
 * ```
 *
 * If you also need the trait names themselves, such as to implement one or both on your types,
 * import the containing module and then name them with a prefix:
 *
 * ```kotlin
 * open class Example : Appendable {
 *     public open fun writeStr(s: String) {
 *         error("unsupported")
 *     }
 * }
 * ```
 *
 * Note: This macro can be used in `noStd` setups as well.
 * In a `noStd` setup you are responsible for the implementation details of the components.
 *
 * ```kotlin
 * import core.fmt.Write
 *
 * open class Example : Write {
 *     public open fun writeStr(s: String) {
 *         error("unsupported")
 *     }
 * }
 * var m = Example()
 * m.append("Hello World").expect("Writing to `Example` should succeed")
 * ```
 */

public fun write(dst: String = "", arg: String = ""): String =
    when {
        dst.isNotEmpty() && arg.isNotEmpty() -> arrayOf("\$dst" to dst, "\$arg" to arg).fold(WRITE_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        arg.isNotEmpty() -> WRITE_TEMPLATE_1
        else -> ""
    }

/**
 * Writes formatted data into a buffer, with a newline appended.
 *
 * On all platforms, the newline is the LINE FEED character (`\n`/`U + 000A`) alone
 * (no additional CARRIAGE RETURN (`\r`/`U + 000D`).
 *
 * For more information, see [write]. For information on the format string syntax, see
 * [std.fmt].
 *
 * [std.fmt]: ../std/fmt/index.html
 *
 * # Examples
 *
 * ```kotlin
 * fun main(): Result<Unit> {
 *     var w = mutableListOf()
 *     w.appendLine()
 *     w.appendLine("test")
 *     w.appendLine("formatted " + ("arguments").toString())
 *     check(w == ("\ntest\nformatted arguments\n").encodeToByteArray().map { it.toUByte() })
 *     return Result.success(Unit)
 * }
 * ```
 */

public fun writeln(dst: String = "", arg: String = ""): String =
    when {
        dst.isNotEmpty() && arg.isNotEmpty() -> arrayOf("\$dst" to dst, "\$arg" to arg).fold(WRITELN_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        dst.isNotEmpty() -> WRITELN_TEMPLATE.replace("\$dst", dst)
        arg.isNotEmpty() -> WRITELN_TEMPLATE_2
        else -> ""
    }

/**
 * Indicates unreachable code.
 *
 * This is useful any time that the compiler can't determine that some code is unreachable. For
 * example:
 *
 * * Match arms with guard conditions.
 * * Loops that dynamically terminate.
 * * Iterators that dynamically terminate.
 *
 * If the determination that the code is unreachable proves incorrect, the
 * program immediately terminates with a [panic].
 *
 * The unchecked counterpart of this macro is the [unreachableUnchecked] function, which
 * will cause undefined behavior if the code is reached.
 *
 * # Panics
 *
 * This will always [panic] because `unreachable` is just a shorthand for `panic` with a
 * fixed, specific message.
 *
 * Like `panic`, this macro has a second form for displaying custom values.
 *
 * # Examples
 *
 * Match arms:
 *
 * ```kotlin
 * fun foo(x: Int?) {
 *     when {
 *         x != null && x >= 0 -> {
 *             val n = x
 *             println("Some(Non-negative)")
 *         }
 *         x != null && x < 0 -> {
 *             val n1 = x
 *             println("Some(Negative)")
 *         }
 *         x != null -> error("unreachable")
 *         // compile error if commented out
 *         else -> println("None")
 *     }
 * }
 * ```
 *
 * Iterators:
 *
 * ```kotlin
 * fun divideByThree(x: UInt): UInt {
 *     // one of the poorest implementations of x/3
 *     for (i in 0 until Int.MAX_VALUE) {
 *         if (3 * i < i) {
 *             error("u32 overflow")
 *         }
 *         if (x < 3u * i) {
 *             return i - 1u
 *         }
 *     }
 *     error("The loop should always return")
 * }
 * ```
 */

public fun unreachable(arg: String = ""): String =
    if (arg.isEmpty()) {
        ""
    } else {
        ""
    }

/**
 * Indicates Note code by panicking with a message of "not implemented".
 *
 * This allows your code to type-check, which is useful if you are prototyping or
 * implementing a trait that requires multiple methods which you don't plan to use all of.
 *
 * The difference between `unsupportedBranch` and [pendingTask] is that while `pendingTask`
 * conveys an intent of implementing the functionality later and the message is "not yet
 * implemented", `unsupportedBranch` makes no such claims. Its message is "not implemented".
 *
 * Also, some IDEs will mark `pendingTask`s.
 *
 * # Panics
 *
 * This will always [panic] because `unsupportedBranch` is just a shorthand for `panic` with a
 * fixed, specific message.
 *
 * Like `panic`, this macro has a second form for displaying custom values.
 *
 * # Examples
 *
 * Say we have a trait `Foo`:
 *
 * ```kotlin
 * interface Foo {
 *     public fun bar(): UByte
 *
 *     public fun baz()
 *
 *     public fun qux(): Result<ULong>
 * }
 * ```
 *
 * We want to implement `Foo` for 'MyStruct', but for some reason it only makes sense
 * to implement the `bar()` function. `baz()` and `qux()` will still need to be defined
 * in our implementation of `Foo`, but we can use `unsupportedBranch` in their definitions
 * to allow our code to compile.
 *
 * We still want to have our program stop running if the Note methods are
 * reached.
 *
 * ```kotlin
 * open class MyStruct : Foo {
 *     public open fun bar(): UByte {
 *         return (1u + 1u).toUByte()
 *     }
 *
 *     public open fun baz() {
 *         // It makes no sense to `baz` a `MyStruct`, so we have no logic here
 *         // at all.
 *         // This will display "thread 'main' panicked at not implemented'".
 *         error("unsupported")
 *     }
 *
 *     public open fun qux(): Result<ULong> {
 *         // We have some logic here,
 *         // We can add a message to unsupportedBranch to display our omission.
 *         // This will display:
 *         // "thread 'main' panicked at not implemented: MyStruct isn't quxable'".
 *         error("MyStruct isn't quxable")
 *     }
 * }
 * fun main() {
 *     val s = MyStruct()
 *     s.bar()
 * }
 * ```
 */

public fun unsupportedBranch(arg: String = ""): String =
    when {
        arg.isNotEmpty() -> UNSUPPORTED_BRANCH_TEMPLATE_1.replace("\$arg", arg)
        else -> UNSUPPORTED_BRANCH_TEMPLATE
    }

/**
 * Indicates unfinished code.
 *
 * This can be useful if you are prototyping and just
 * want a Note to let your code pass type analysis.
 *
 * The difference between [unsupportedBranch] and `pendingTask` is that while `pendingTask` conveys
 * an intent of implementing the functionality later and the message is "not yet
 * implemented", `unsupportedBranch` makes no such claims. Its message is "not implemented".
 *
 * Also, some IDEs will mark `pendingTask`s.
 *
 * # Panics
 *
 * This will always [panic] because `pendingTask` is just a shorthand for `panic` with a
 * fixed, specific message.
 *
 * Like `panic`, this macro has a second form for displaying custom values.
 *
 * # Examples
 *
 * Here's an example of some in-progress code. We have a trait `Foo`:
 *
 * ```kotlin
 * interface Foo {
 *     public fun bar(): UByte
 *
 *     public fun baz()
 *
 *     public fun qux(): Result<ULong>
 * }
 * ```
 *
 * We want to implement `Foo` on one of our types, but we also want to work on
 * just `bar()` first. In order for our code to compile, we need to implement
 * `baz()` and `qux()`, so we can use `pendingTask`:
 *
 * ```kotlin
 * open class MyStruct : Foo {
 *     public open fun bar(): UByte {
 *         return (1u + 1u).toUByte()
 *     }
 *
 *     public open fun baz() {
 *         // Let's not worry about implementing baz() for now
 *         error("unsupported")
 *     }
 *
 *     public open fun qux(): Result<ULong> {
 *         // We can add a message to pendingTask to display our omission.
 *         // This will display:
 *         // "thread 'main' panicked at not yet implemented: MyStruct is not yet quxable'".
 *         error("MyStruct is not yet quxable")
 *     }
 * }
 * fun main() {
 *     val s = MyStruct()
 *     s.bar()
 *     // We aren't even using baz() or qux(), so this is fine.
 * }
 * ```
 */

public fun pendingTask(arg: String = ""): String =
    when {
        arg.isNotEmpty() -> PENDING_TASK_TEMPLATE_1.replace("\$arg", arg)
        else -> PENDING_TASK_TEMPLATE
    }
