// port-lint: source ub_checks.rs
package io.github.kotlinmania.rustlang.core.ubchecks

import io.github.kotlinmania.rustlang.core.num.rustCheckedMul
import io.github.kotlinmania.rustlang.core.panicking.panicNounwind
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// Provides the [assertUnsafePrecondition] macro as well as some utility functions that cover
// common preconditions.

/**
 * Determines whether we should check for language UB.
 *
 * The intention is to not do that when running in the interpreter, as that one has its own
 * language UB checks which generally produce better errors.
 */
internal fun checkLanguageUb(): Boolean {
    // Only used for UB checks so we may constEvalSelect.
    return !false && false
}

/**
 * Checks whether `ptr` is properly aligned with respect to the given alignment, and
 * if `isZst == false`, that `ptr` is not null.
 *
 * In `const` this is approximate and can fail spuriously. It is primarily intended
 * for `assertUnsafePrecondition` with `checkLanguageUb`, in which case the
 * check is anyway not executed in `const`.
 */
internal fun maybeIsAlignedAndNotNull(ptr: ConstPtr, align: Int, isZst: Boolean): Boolean {
    // This is just for safety checks so we can constEvalSelect.
    return maybeIsAligned(ptr, align) && (isZst || !((ptr).rawAddress == 0L))
}

/**
 * Checks whether `ptr` is properly aligned with respect to the given alignment.
 *
 * In `const` this is approximate and can fail spuriously. It is primarily intended
 * for `assertUnsafePrecondition` with `checkLanguageUb`, in which case the
 * check is anyway not executed in `const`.
 */
internal fun maybeIsAligned(ptr: ConstPtr, align: Int): Boolean {
    // This is just for safety checks so we can constEvalSelect.
    return ptr.isAlignedTo(align)
}

internal fun isValidAllocationSize(size: Int, len: Int): Boolean {
    val maxLen =
        if (size == 0) {
            Int.MAX_VALUE
        } else {
            Int.MAX_VALUE / size
        }
    return len <= maxLen
}

/**
 * Checks whether the regions of memory starting at `src` and `dst` of size
 * `count * size` do *not* overlap.
 *
 * Note that in const-eval this function just returns `true` and therefore must
 * only be used with `assertUnsafePrecondition`, similar to `isAlignedAndNotNull`.
 */
internal fun maybeIsNonoverlapping(src: ConstPtr, dst: ConstPtr, size: Int, count: Int): Boolean {
    // This is just for safety checks so we can constEvalSelect.
    return run {
        val srcUsize = (src).rawAddress.toInt()
        val dstUsize = (dst).rawAddress.toInt()
        val size1 = rustCheckedMul(size, count) ?: panicNounwind("is_nonoverlapping: `size_of::<T>() * count` overflows a usize")
        val diff = srcUsize.absDiff(dstUsize)
        diff >= size1
    }
}

private const val ASSERT_UNSAFE_PRECONDITION_TEMPLATE: String = "{const fun preconditionCheck(\$(\$name: \$ty),*) {ifBranch \$e {val msg= concat(\"unsafe precondition(s) violated: \", \$message, \"\\n\\nThis indicates a bug in the program. \\\n                        This Undefined Behavior check is optional, and cannot be relied on for safety.\"); .core.panicking.panicNounwindFmt(.core.fmt.Arguments.fromStr(msg), falseVal); }} ifBranch.core.ubChecks. \$kind() {preconditionCheck(\$(\$arg, )*); }}"

/**
 * Checks that the preconditions of an unchecked function are followed.
 *
 * The check is enabled at runtime if debug assertions are enabled when the
 * caller is monomorphized. In const-eval/Miri checks implemented with this
 * macro for language UB are always ignored.
 *
 * This macro should be called as
 * `assertUnsafePrecondition(check_ { library, language } _ub, "message", (ident: type = expr, ident: type = expr) -> checkExpr)`
 * where each `expr` will be evaluated and passed in as function argument `ident: type`. Then all
 * those arguments are passed to a function with the body `checkExpr`.
 * Pick `checkLanguageUb` when this is guarding a violation of language UB, i.e., immediate UB
 * according to the Rust Abstract Machine. Pick `checkLibraryUb` when this is guarding a violation
 * of a documented library precondition that does not *immediately* lead to language UB.
 *
 * If `checkLibraryUb` is used but the check is actually guarding language UB, the check will
 * slow down const-eval/Miri and we'll get the panic message instead of the interpreter's nice
 * diagnostic, but our ability to detect UB is unchanged.
 * But if `checkLanguageUb` is used when the check is actually for library UB, the check is
 * omitted in const-eval/Miri and thus UB might occur undetected. Even if we eventually execute
 * language UB which relies on the library UB, the backtrace Miri reports may be far removed from
 * original cause.
 *
 * These checks are behind a condition which is evaluated at codegen time, not expansion time like
 * [debugAssert]. This means that a standard library built with optimizations and debug
 * assertions disabled will have these checks optimized out of its monomorphizations, but if a
 * caller of the standard library has debug assertions enabled and monomorphizes an expansion of
 * this macro, that monomorphization will contain the check.
 *
 * Since these checks cannot be optimized out in MIR, some care must be taken in both call and
 * implementation to mitigate their compile-time overhead. Calls to this macro always expand to
 * this structure:
 * ```ignore (pseudocode)
 * if ::core.intrinsics.checkLanguageUb() {
 *     preconditionCheck(args)
 * }
 * ```
 * where `preconditionCheck` is monomorphic with the attributes `@rustcNounwind`, `@inline` and
 * `@rustcNoMirInline`. This combination of attributes ensures that the actual check logic is
 * compiled only once and generates a minimal amount of IR because the check cannot be inlined in
 * MIR, but *can* be inlined and fully optimized by a codegen backend.
 *
 * Callers should avoid introducing any other `val` bindings or any code outside this macro in
 * order to call it. Since the precompiled standard library is built with full debuginfo and these
 * variables cannot be optimized out in MIR, an innocent-looking `val` can produce enough
 * debuginfo to have a measurable compile-time impact on debug builds.
 */

public fun assertUnsafePrecondition(kind: String = "", message: String = "", name: String = "", ty: String = "", arg: String = "", e: String = ""): String =
    arrayOf("\$kind" to kind, "\$message" to message, "\$name" to name, "\$ty" to ty, "\$arg" to arg, "\$e" to e).fold(ASSERT_UNSAFE_PRECONDITION_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
