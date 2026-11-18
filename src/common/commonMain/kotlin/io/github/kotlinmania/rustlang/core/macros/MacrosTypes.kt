package io.github.kotlinmania.rustlang.core.macros

private const val COMPILE_ERROR_TEMPLATE: String = "{}"

private const val FORMAT_ARGS_TEMPLATE: String = "{}"

private const val FORMAT_ARGS_TEMPLATE_1: String = "{}"

private const val CONST_FORMAT_ARGS_TEMPLATE: String = "{}"

private const val CONST_FORMAT_ARGS_TEMPLATE_1: String = "{}"

private const val FORMAT_ARGS_NL_TEMPLATE: String = "{}"

private const val FORMAT_ARGS_NL_TEMPLATE_1: String = "{}"

private const val ENV_TEMPLATE: String = "{}"

private const val ENV_TEMPLATE_1: String = "{}"

private const val OPTION_ENV_TEMPLATE: String = "{}"

private const val CONCAT_BYTES_TEMPLATE: String = "{}"

private const val CONCAT_TEMPLATE: String = "{}"

private const val INCLUDE_STR_TEMPLATE: String = "{}"

private const val INCLUDE_BYTES_TEMPLATE: String = "{}"

private const val INCLUDE_TEMPLATE: String = "{}"

private const val ASSERT_TEMPLATE: String = "{}"

private const val ASSERT_TEMPLATE_1: String = "{}"

private const val TRACE_MACROS_TEMPLATE: String = "{}"

private const val TRACE_MACROS_TEMPLATE_1: String = "{}"

/**
 * Causes compilation to fail with the given error message when encountered.
 *
 * This macro should be used when a crate uses a conditional compilation strategy to provide
 * better error messages for erroneous conditions. It's the compiler-level form of [panic],
 * but emits an error during *compilation* rather than at *runtime*.
 *
 * # Examples
 *
 * Two such examples are macros and `@cfg` environments.
 *
 * Emit a better compiler error if a macro is passed invalid values. Without the final branch,
 * the compiler would still emit an error, but the error's message would not mention the two
 * valid values.
 *
 * ```compile_fail
 * macroRules giveMeFooOrBar {
 *     (foo) => {};
 *     (bar) => {};
 *     ($x:ident) => {
 *         compileError("This macro only accepts `foo` or `bar`");
 *     }
 * }
 *
 * giveMeFooOrBar(neither);
 * // ^ will fail at compile time with message "This macro only accepts `foo` or `bar`"
 * ```
 *
 * Emit a compiler error if one of a number of features isn't available.
 *
 * ```compile_fail
 * @cfg(not(any(feature = "foo", feature = "bar")))
 * compileError("Either feature \"foo\" or \"bar\" must be enabled for this crate.");
 * ```
 */

public fun compileError(msg: String = ""): String =
    if (msg.isEmpty()) {
        COMPILE_ERROR_TEMPLATE
    } else {
        COMPILE_ERROR_TEMPLATE
    }

/**
 * Constructs parameters for the other string-formatting macros.
 *
 * This macro functions by taking a formatting string literal containing
 * `()` for each additional argument passed. `formatArgs` prepares the
 * additional parameters to ensure the output can be interpreted as a string
 * and canonicalizes the arguments into a single type. Any value that implements
 * the [Display] trait can be passed to `formatArgs`, as can any
 * [Debug] implementation be passed to a `( : ? )` within the formatting string.
 *
 * This macro produces a value of type [fmt.Arguments]. This value can be
 * passed to the macros within [std.fmt] for performing useful redirection.
 * All other formatting macros ([format], [write], [println], etc) are
 * proxied through this one. `formatArgs`, unlike its derived macros, avoids
 * heap allocations.
 *
 * You can use the [fmt.Arguments] value that `formatArgs` returns
 * in `Debug` and `Display` contexts as seen below. The example also shows
 * that `Debug` and `Display` format to the same thing: the interpolated
 * format string in `formatArgs`.
 *
 * ```kotlin
 * val args = "${1} foo ${2}"
 * val debug = "$args"
 * val display = "$args"
 * check("1 foo 2" == display)
 * check(display == debug)
 * ```
 *
 * See [the formatting documentation in `std.fmt`](../std/fmt/index.html)
 * for details of the macro argument syntax, and further information.
 *
 * [std.fmt]: ../std/fmt/index.html
 * [format]: ../std/macro.format.html
 * [println]: ../std/macro.println.html
 *
 * # Examples
 *
 * ```kotlin
 * import std.fmt
 *
 * val s = fmt.format("hello " + ("world").toString())
 * check(s == "hello " + ("world").toString())
 * ```
 *
 * # Argument scopes
 *
 * Except when no formatting arguments are used,
 * the produced `fmt.Arguments` value borrows temporary values.
 * To allow it to be stored for later use, the arguments' scopes, as well as those of
 * temporaries they borrow, may be [extended] when `formatArgs` appears in the initializer
 * expression of a `val` statement. The syntactic rules used to determine when temporaries'
 * scopes are extended are documented in the [Reference].
 *
 * [extended]: ../reference/destructors.html#temporary-scope-extension
 * [Reference]: ../reference/destructors.html#extending-based-on-expressions
 */

public fun formatArgs(fmt: String = "", args: String = ""): String =
    when {
        fmt.isNotEmpty() && args.isNotEmpty() -> FORMAT_ARGS_TEMPLATE_1
        fmt.isNotEmpty() -> FORMAT_ARGS_TEMPLATE
        else -> ""
    }

/**
 * Same as [formatArgs], but can be used in some const contexts.
 *
 * This macro is used by the panic macros for the `constPanic` feature.
 *
 * This macro will be removed once `formatArgs` is allowed in const contexts.
 */

public fun constFormatArgs(fmt: String = "", args: String = ""): String =
    when {
        fmt.isNotEmpty() && args.isNotEmpty() -> CONST_FORMAT_ARGS_TEMPLATE_1
        fmt.isNotEmpty() -> CONST_FORMAT_ARGS_TEMPLATE
        else -> ""
    }

/**
 * Same as [formatArgs], but adds a newline in the end.
 */

public fun formatArgsNl(fmt: String = "", args: String = ""): String =
    when {
        fmt.isNotEmpty() && args.isNotEmpty() -> FORMAT_ARGS_NL_TEMPLATE_1
        fmt.isNotEmpty() -> FORMAT_ARGS_NL_TEMPLATE
        else -> ""
    }

/**
 * Inspects an environment variable at compile time.
 *
 * This macro will expand to the value of the named environment variable at
 * compile time, yielding an expression of type `String`. Use
 * [std.env.variable] instead if you want to read the value at runtime.
 *
 * [std.env.variable]: ../std/env/fn.var.html
 *
 * If the environment variable is not defined, then a compilation error
 * will be emitted. To not emit a compile error, use the [optionEnv]
 * macro instead. A compilation error will also be emitted if the
 * environment variable is not a valid Unicode string.
 *
 * # Examples
 *
 * ```kotlin
 * val path: String = env("PATH")
 * println("the \$PATH variable at the time of compiling was: $path")
 * ```
 *
 * You can customize the error message by passing a string as the second
 * parameter:
 *
 * ```compile_fail
 * let doc: &static str = env("documentation", "what's that?!");
 * ```
 *
 * If the `documentation` environment variable is not defined, you'll get
 * the following error:
 *
 * ```text
 * error: what's that?!
 * ```
 */

public fun env(name: String = "", errorMsg: String = ""): String =
    when {
        name.isNotEmpty() && errorMsg.isNotEmpty() -> ENV_TEMPLATE_1
        name.isNotEmpty() -> ENV_TEMPLATE
        else -> ""
    }

/**
 * Optionally inspects an environment variable at compile time.
 *
 * If the named environment variable is present at compile time, this will
 * expand into an expression of type `Option<String>` whose value is
 * `Some` of the value of the environment variable (a compilation error
 * will be emitted if the environment variable is not a valid Unicode
 * string). If the environment variable is not present, then this will
 * expand to `None`. See [`Option.T`][Option] for more information on this
 * type.  Use [std.env.variable] instead if you want to read the value at
 * runtime.
 *
 * [std.env.variable]: ../std/env/fn.var.html
 *
 * A compile time error is only emitted when using this macro if the
 * environment variable exists and is not a valid Unicode string. To also
 * emit a compile error if the environment variable is not present, use the
 * [env] macro instead.
 *
 * # Examples
 *
 * ```kotlin
 * val key: String? = optionEnv("SECRET_KEY")
 * println("the secret key might be: $key")
 * ```
 */

public fun optionEnv(name: String = ""): String =
    if (name.isEmpty()) {
        OPTION_ENV_TEMPLATE
    } else {
        OPTION_ENV_TEMPLATE
    }

/**
 * Concatenates literals into a byte slice.
 *
 * This macro takes any number of comma-separated literals, and concatenates them all into
 * one, yielding an expression of type `[UByte; _]`, which represents all of the literals
 * concatenated left-to-right. The literals passed can be any combination of:
 *
 * - byte literals (`b'r'`)
 * - byte strings (`b"Rust"`)
 * - arrays of bytes/numbers (`[b'A', 66, b'C']`)
 *
 * # Examples
 *
 * ```kotlin
 * @file:OptIn(ExperimentalUnsignedTypes.cls)
 *
 * @feature(concatBytes)
 * public fun doctestEntry() {
 *     val s: UByteArray = concatBytes(65u.toUByte(), "BC".encodeToByteArray().toUByteArray(), ubyteArrayOf(68u.toUByte(), 69u.toUByte(), 70u.toUByte()))
 *     check(s == "ABCDEF".encodeToByteArray())
 * }
 * ```
 */

public fun concatBytes(e: String = ""): String =
    if (e.isEmpty()) {
        CONCAT_BYTES_TEMPLATE
    } else {
        CONCAT_BYTES_TEMPLATE
    }

/**
 * Concatenates literals into a static string slice.
 *
 * This macro takes any number of comma-separated literals, yielding an
 * expression of type `String` which represents all of the literals
 * concatenated left-to-right.
 *
 * Integer and floating point literals are [stringified](core.stringify) in order to be
 * concatenated.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "test10btrue"
 * check(s == "test10btrue")
 * ```
 */

public fun concat(e: String = ""): String =
    if (e.isEmpty()) {
        CONCAT_TEMPLATE
    } else {
        CONCAT_TEMPLATE
    }

/**
 * Expands to the line number on which it was invoked.
 *
 * With [column] and [file], these macros provide debugging information for
 * developers about the location within the source.
 *
 * The expanded expression has type `UInt` and is 1-based, so the first line
 * in each file evaluates to 1, the second to 2, etc. This is consistent
 * with error messages by common compilers or popular editors.
 * The returned line is *not necessarily* the line of the `line` invocation itself,
 * but rather the first macro invocation leading up to the invocation
 * of the `line` macro.
 *
 * # Examples
 *
 * ```kotlin
 * val currentLine = 3u
 * println("defined on line: $currentLine")
 * ```
 */

public const val LINE: String = ""

/**
 * Expands to the column number at which it was invoked.
 *
 * With [line] and [file], these macros provide debugging information for
 * developers about the location within the source.
 *
 * The expanded expression has type `UInt` and is 1-based, so the first column
 * in each line evaluates to 1, the second to 2, etc. This is consistent
 * with error messages by common compilers or popular editors.
 * The returned column is *not necessarily* the line of the `column` invocation itself,
 * but rather the first macro invocation leading up to the invocation
 * of the `column` macro.
 *
 * # Examples
 *
 * ```kotlin
 * val currentCol = 19u
 * println("defined on column: $currentCol")
 * ```
 *
 * `column` counts Unicode code points, not bytes or graphemes. As a result, the first two
 * invocations return the same value, but the third does not.
 *
 * ```kotlin
 * val a = Pair("foobar", 20u).second
 * val b = Pair("人之初性本善", 20u).second
 * val c = Pair("f̅o̅o̅b̅a̅r̅", 26u).second
 * // Uses combining overline (U+0305)
 * check(a == b)
 * check(b != c)
 * ```
 */

public const val COLUMN: String = ""

/**
 * Expands to the file name in which it was invoked.
 *
 * With [line] and [column], these macros provide debugging information for
 * developers about the location within the source.
 *
 * The expanded expression has type `String`, and the returned file
 * is not the invocation of the `file` macro itself, but rather the
 * first macro invocation leading up to the invocation of the `file`
 * macro.
 *
 * The file name is derived from the crate root's source path passed to the Rust compiler
 * and the sequence the compiler takes to get from the crate root to the
 * module containing `file`, modified by any flags passed to the Rust compiler (e.g.
 * `- - remap - path - prefix`).  If the crate's source path is relative, the initial base
 * directory will be the working directory of the Rust compiler.  For example, if the source
 * path passed to the compiler is `./ src / lib.rs` which has a `mod foo;` with a source path of
 * `src / foo / mod.rs`, then calling `file` inside `mod foo;` will return `./ src / foo / mod.rs`.
 *
 * Future compiler options might make further changes to the behavior of `file`,
 * including potentially making it entirely empty. Code (e.g. test libraries)
 * relying on `file` producing an openable file path would be incompatible
 * with such options, and might wish to recommend not using those options.
 *
 * # Examples
 *
 * ```kotlin
 * val thisFile = ""
 * println("defined in file: $thisFile")
 * ```
 */

public const val FILE: String = ""

/**
 * Stringifies its arguments.
 *
 * This macro will yield an expression of type `String` which is the
 * stringification of all the tokens passed to the macro. No restrictions
 * are placed on the syntax of the macro invocation itself.
 *
 * Note that the expanded results of the input tokens may change in the
 * future. You should be careful if you rely on the output.
 *
 * # Examples
 *
 * ```kotlin
 * val onePlusOne = "1 + 1"
 * check(onePlusOne == "1 + 1")
 * ```
 */

public fun stringify(t: String = ""): String =
    if (t.isEmpty()) {
        ""
    } else {
        ""
    }

/**
 * Includes a UTF-8 encoded file as a string.
 *
 * The file is located relative to the current file (similarly to how
 * modules are found). The provided path is interpreted in a platform-specific
 * way at compile time. So, for instance, an invocation with a Windows path
 * containing backslashes `\` would not compile correctly on Unix.
 *
 * This macro will yield an expression of type `String` which is the
 * contents of the file.
 *
 * # Examples
 *
 * Assume there are two files in the same directory with the following
 * contents:
 *
 * File spanish.in':
 *
 * ```text
 * adiós
 * ```
 *
 * File main.rs':
 *
 * ```ignore (cannot-doctest-external-file-dependency)
 * fn main() {
 *     let myStr = includeStr("spanish.in");
 *     assertEq(myStr, "adiós\n");
 *     print("{myStr}");
 * }
 * ```
 *
 * Compiling main.rs' and running the resulting binary will print "adiós".
 */

public fun includeStr(file: String = ""): String =
    if (file.isEmpty()) {
        INCLUDE_STR_TEMPLATE
    } else {
        INCLUDE_STR_TEMPLATE
    }

/**
 * Includes a file as a reference to a byte array.
 *
 * The file is located relative to the current file (similarly to how
 * modules are found). The provided path is interpreted in a platform-specific
 * way at compile time. So, for instance, an invocation with a Windows path
 * containing backslashes `\` would not compile correctly on Unix.
 *
 * This macro will yield an expression of type `[UByte; N]` which is
 * the contents of the file.
 *
 * # Examples
 *
 * Assume there are two files in the same directory with the following
 * contents:
 *
 * File spanish.in':
 *
 * ```text
 * adiós
 * ```
 *
 * File main.rs':
 *
 * ```ignore (cannot-doctest-external-file-dependency)
 * fn main() {
 *     let bytes = includeBytes("spanish.in");
 *     assertEq(bytes, b"adi\xc3\xb3s\n");
 *     print("{}", String.fromUtf8Lossy(bytes));
 * }
 * ```
 *
 * Compiling main.rs' and running the resulting binary will print "adiós".
 */

public fun includeBytes(file: String = ""): String =
    if (file.isEmpty()) {
        INCLUDE_BYTES_TEMPLATE
    } else {
        INCLUDE_BYTES_TEMPLATE
    }

/**
 * Expands to a string that represents the current module path.
 *
 * The current module path can be thought of as the hierarchy of modules
 * leading back up to the crate root. The first component of the path
 * returned is the name of the crate currently being compiled.
 *
 * # Examples
 *
 * ```kotlin
 * fun foo() {
 *     check(modulePath().endsWith("test"))
 * }
 * foo()
 * ```
 */

public const val MODULE_PATH: String = ""

/**
 * Evaluates boolean combinations of configuration flags at compile-time.
 *
 * In addition to the `@cfg` attribute, this macro is provided to allow
 * boolean expression evaluation of configuration flags. This frequently
 * leads to less duplicated code.
 *
 * The syntax given to this macro is the same syntax as the [cfg]
 * attribute.
 *
 * `cfg`, unlike `@cfg`, does not remove any code and only evaluates to true or false. For
 * example, all blocks in an if/else expression need to be valid when `cfg` is used for
 * the condition, regardless of what `cfg` is evaluating.
 *
 * [cfg]: ../reference/conditional-compilation.html#the-cfg-attribute
 *
 * # Examples
 *
 * ```kotlin
 * val myDirectory = if cfg(windows) { "windows-specific-directory" } else { "unix-directory" };
 * ```
 */

public fun cfg(cfg: String = ""): String =
    if (cfg.isEmpty()) {
        ""
    } else {
        ""
    }

/**
 * Parses a file as an expression or an item according to the context.
 *
 * **Warning**: For multi-file Rust projects, the `include` macro is probably not what you
 * are looking for. Usually, multi-file Rust projects use
 * [modules](https://doc.rust-lang.org/reference/items/modules.html). Multi-file projects and
 * modules are explained in the Rust-by-Example book
 * [here](https://doc.rust-lang.org/rust-by-example/mod/split.html) and the module system is
 * explained in the Rust Book
 * [here](https://doc.rust-lang.org/book/ch07-02-defining-modules-to-control-scope-and-privacy.html).
 *
 * The included file is placed in the surrounding code
 * [unhygienically](https://doc.rust-lang.org/reference/macros-by-example.html#hygiene). If
 * the included file is parsed as an expression and variables or functions share names across
 * both files, it could result in variables or functions being different from what the
 * included file expected.
 *
 * The included file is located relative to the current file (similarly to how modules are
 * found). The provided path is interpreted in a platform-specific way at compile time. So,
 * for instance, an invocation with a Windows path containing backslashes `\` would not
 * compile correctly on Unix.
 *
 * # Uses
 *
 * The `include` macro is primarily used for two purposes. It is used to include
 * documentation that is written in a separate file and it is used to include [build artifacts
 * usually as a result from the `build.rs`
 * script](https://doc.rust-lang.org/cargo/reference/build-scripts.html#outputs-of-the-build-script).
 *
 * When using the `include` macro to include stretches of documentation, remember that the
 * included file still needs to be a valid Rust syntax. It is also possible to
 * use the [includeStr] macro as `@file:doc= includeStr("...")` (at the module level) or
 * `@doc= includeStr("...")` (at the item level) to include documentation from a plain
 * text or markdown file.
 *
 * # Examples
 *
 * Assume there are two files in the same directory with the following contents:
 *
 * File monkeys.in':
 *
 * ```ignore (only-for-syntax-highlight)
 * ['🙈', '🙊', '🙉']
 *     .iter()
 *     .cycle()
 *     .take(6)
 *     .collect::<String>()
 * ```
 *
 * File main.rs':
 *
 * ```ignore (cannot-doctest-external-file-dependency)
 * fn main() {
 *     let myString = include("monkeys.in");
 *     assertEq("🙈🙊🙉🙈🙊🙉", myString);
 *     println("{myString}");
 * }
 * ```
 *
 * Compiling main.rs' and running the resulting binary will print
 * "🙈🙊🙉🙈🙊🙉".
 */

public fun include(file: String = ""): String =
    if (file.isEmpty()) {
        INCLUDE_TEMPLATE
    } else {
        INCLUDE_TEMPLATE
    }

/**
 * Asserts that a boolean expression is `true` at runtime.
 *
 * This will invoke the [panic] macro if the provided expression cannot be
 * evaluated to `true` at runtime.
 *
 * # Uses
 *
 * Assertions are always checked in both debug and release builds, and cannot
 * be disabled. See [debugAssert] for assertions that are not enabled in
 * release builds by default.
 *
 * Unsafe code may rely on `assert` to enforce run-time invariants that, if
 * violated could lead to unsafety.
 *
 * Other use-cases of `assert` include testing and enforcing run-time
 * invariants in safe code (whose violation cannot result in unsafety).
 *
 * # Custom Messages
 *
 * This macro has a second form, where a custom panic message can
 * be provided with or without arguments for formatting. See [std.fmt]
 * for syntax for this form. Expressions used as format arguments will only
 * be evaluated if the assertion fails.
 *
 * [std.fmt]: ../std/fmt/index.html
 *
 * # Examples
 *
 * ```kotlin
 * fun someComputation(): Boolean {
 *     // Some expensive computation here
 *     return true
 * }
 * // the panic message for these assertions is the stringified value of the
 * // expression given.
 * check(true)
 * check(someComputation())
 * // assert with a custom message
 * val x = true
 * check(x) { "x wasn't true!" }
 * val a = 3
 * val b = 27
 * check(a + b == 30) { "a = $a, b = $b" }
 * ```
 */

public fun assert(cond: String = "", arg: String = ""): String =
    when {
        cond.isNotEmpty() && arg.isNotEmpty() -> ASSERT_TEMPLATE_1
        cond.isNotEmpty() -> ASSERT_TEMPLATE
        else -> ""
    }

/**
 * Prints passed tokens into the standard output.
 */

public fun logSyntax(arg: String = ""): String =
    if (arg.isEmpty()) {
        ""
    } else {
        ""
    }

/**
 * Enables or disables tracing functionality used for debugging other macros.
 */

public fun traceMacros(clause: String = ""): String =
    when {
        clause == "trueVal" -> TRACE_MACROS_TEMPLATE
        clause == "falseVal" -> TRACE_MACROS_TEMPLATE_1
        else -> ""
    }
