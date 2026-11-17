// port-lint: source macros.rs
package io.github.kotlinmania.rustlang.alloc

public const val CFG_NO_GLOBAL_OOM_HANDLING: Boolean = true

private const val VEC_TEMPLATE: String = "vec.Vec.new()"

private const val VEC_TEMPLATE_1: String = "vec.fromElemGlobal(\$elem, \$n)"

private const val VEC_TEMPLATE_2: String = "boxed.boxAssumeInitIntoVecUnsafe(intrinsics.writeBoxViaMove(boxed.Box.newUninit(), [\$(\$x),+]))"

private const val FORMAT_TEMPLATE: String = "export.mustUse( {fmt.format(export.formatArgs(\$(\$arg)*))})"

/**
 * Creates a [Vec] containing the arguments.
 *
 * `vec` allows `List`s to be defined with the same syntax as array expressions.
 * There are two forms of this macro:
 *
 * - Create a [Vec] containing a given list of elements:
 *
 * ```kotlin
 * val v = mutableListOf(1, 2, 3)
 * check(v[0] == 1)
 * check(v[1] == 2)
 * check(v[2] == 3)
 * ```
 *
 * - Create a [Vec] from a given element and size:
 *
 * ```kotlin
 * val v = mutableListOf(1, 3)
 * check(v == listOf(1, 1, 1))
 * ```
 *
 * Note that unlike array expressions this syntax supports all elements
 * which implement [Clone] and the number of elements doesn't have to be
 * a constant.
 *
 * This will use `clone` to duplicate an expression, so one should be careful
 * using this with types having a nonstandard `Clone` implementation. For
 * example, `listOf(Rc.new(1); 5)` will create a vector of five references
 * to the same boxed integer value, not five references pointing to independently
 * boxed integers.
 *
 * Also, note that `listOf(expr; 0)` is allowed, and produces an empty vector.
 * This will still evaluate `expr`, however, and immediately drop the resulting value, so
 * be mindful of side effects.
 */

public fun vec(elem: String = "", n: String = "", x: String = ""): String =
    if (!CFG_NO_GLOBAL_OOM_HANDLING) {
        when {
            elem.isNotEmpty() && n.isNotEmpty() -> arrayOf("\$elem" to elem, "\$n" to n).fold(VEC_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
            x.isNotEmpty() -> VEC_TEMPLATE_2.replace("\$x", x)
            else -> VEC_TEMPLATE
        }
    } else {
        ""
    }

/**
 * Creates a `String` using interpolation of runtime expressions.
 *
 * The first argument `format` receives is a format string. This must be a string
 * literal. The power of the formatting string is in the `()`s contained.
 * Additional parameters passed to `format` replace the `()`s within the
 * formatting string in the order given unless named or positional parameters
 * are used.
 *
 * See [the formatting syntax documentation in `std.fmt`](../std/fmt/index.html)
 * for details.
 *
 * A common use for `format` is concatenation and interpolation of strings.
 * The same convention is used with [print] and [write] macros,
 * depending on the intended destination of the string; all these macros internally use [formatArgs].
 *
 * To convert a single value to a string, use the [toString] method. This
 * will use the [Display] formatting trait.
 *
 * To concatenate literals into a `String`, use the [concat] macro.
 *
 * [print]: ../std/macro.print.html
 *
 * # Panics
 *
 * `format` panics if a formatting trait implementation returns an error.
 * This indicates an incorrect implementation
 * since `fmt.Write for String` never returns an error itself.
 *
 * # Examples
 *
 * ```kotlin
 * "test"
 * // => "test"
 * "hello " + ("world!").toString()
 * // => "hello world!"
 * "x = ${10}, y = ${30}"
 * // => "x = 10, y = 30"
 * val (x, y) = Pair(1, 2)
 * "$x + $y = 3"
 * // => "1 + 2 = 3"
 * ```
 */

public fun format(arg: String = ""): String =
    FORMAT_TEMPLATE.replace("\$arg", arg)
