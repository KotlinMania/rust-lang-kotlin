// port-lint: source math/support/macros.rs
package io.github.kotlinmania.rustlang.libm.math.support.macros

internal const val CFG_F16_ENABLED: Boolean = true

internal const val CFG_F128_ENABLED: Boolean = true

private const val CFG_SELECT_NOFMT_TEMPLATE: String = "{cfgSelectNofmt {\$(\$tt)*}}"

private const val CFG_SELECT_NOFMT_TEMPLATE_1: String = "\$(\$output)*"

private const val CFG_SELECT_NOFMT_TEMPLATE_2: String = "cfgSelectNofmt {unused=> \$output}\$(cfgSelectNofmt {\$(\$rest)+})?"

private const val SELECT_IMPLEMENTATION_TEMPLATE: String = "selectImplementation {@ cfg\$(\$useArchRequired)?; ifBranch trueVal {ret superRef.arch. \$fnName(\$(\$arg),+); }} selectImplementation {@ cfg\$(\$useArch)?; ifBranch trueVal {ret superRef.arch. \$fnName(\$(\$arg),+); }}"

private const val SELECT_IMPLEMENTATION_TEMPLATE_2: String = "\$ex"

private const val HF16_TEMPLATE: String = "{const X: f16= support.hf16(\$s); X}"

private const val HF32_TEMPLATE: String = "{const X: f32= support.hf32(\$s); X}"

private const val HF64_TEMPLATE: String = "{const X: f64= support.hf64(\$s); X}"

private const val HF128_TEMPLATE: String = "{const X: f128= support.hf128(\$s); X}"

private const val ASSERT_BITEQ_TEMPLATE: String = "{val l= \$left; val r= \$right; val bits= support.Int.leadingZeros(l.toBits()- l.toBits()); assert(support.Float.biteq(l, r), \"{}\\nl: {l:?} ({lb:#0width\$x} {lh})\\nr: {r:?} ({rb:#0width\$x} {rh})\", formatArgs(\$(\$tt)*), lb= l.toBits(), lh= support.Hex(l), rb= r.toBits(), rh= support.Hex(r), width=((bits/ 4)+ 2) asTarget usize, ); }"

private const val ASSERT_BITEQ_TEMPLATE_1: String = "assertBiteq(\$left, \$right, \"\")"

internal fun cfgSelectNofmt(tt: String = "", output: String = "", cfg: String = "", rest: String = "", clause: String = ""): String =
    when {
        clause == "= >" && cfg.isNotEmpty() && output.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$output" to output, "\$rest" to rest).fold(CFG_SELECT_NOFMT_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "unused = >" && output.isNotEmpty() -> CFG_SELECT_NOFMT_TEMPLATE_1.replace("\$output", output)
        tt.isNotEmpty() -> CFG_SELECT_NOFMT_TEMPLATE.replace("\$tt", tt)
        else -> ""
    }

/**
 * Choose between using an arch-specific implementation and the function body. Returns directly
 * if the arch implementation is used, otherwise continue with the rest of the function.
 *
 * Specify a `useArch` meta field if an architecture-specific implementation is provided.
 * These live in the `math.arch.someTargetArch` module.
 *
 * Specify a `useArchRequired` meta field if something architecture-specific must be used
 * regardless of feature configuration (`arch`).
 *
 * The passed meta options do not need to account for the `arch` target feature.
 */

internal fun selectImplementation(fnName: String = "", useArch: String = "", useArchRequired: String = "", arg: String = "", ex: String = "", provided: String = "", clause: String = ""): String =
    when {
        clause == "name useArch useArchRequired args" && fnName.isNotEmpty() && useArch.isNotEmpty() && useArchRequired.isNotEmpty() && arg.isNotEmpty() -> arrayOf("\$fnName" to fnName, "\$useArch" to useArch, "\$useArchRequired" to useArchRequired, "\$arg" to arg).fold(SELECT_IMPLEMENTATION_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@cfg" && provided.isNotEmpty() && ex.isNotEmpty() -> SELECT_IMPLEMENTATION_TEMPLATE_2.replace("\$ex", ex)
        clause == "@cfg" && ex.isNotEmpty() -> ""
        else -> ""
    }

/**
 * Construct a 16-bit float from hex float representation (C-style), guaranteed to
 * evaluate at compile time.
 */

internal fun hf16(s: String = ""): String =
    if (CFG_F16_ENABLED) {
        HF16_TEMPLATE.replace("\$s", s)
    } else {
        ""
    }

/**
 * Construct a 32-bit float from hex float representation (C-style), guaranteed to
 * evaluate at compile time.
 */

internal fun hf32(s: String = ""): String =
    HF32_TEMPLATE.replace("\$s", s)

/**
 * Construct a 64-bit float from hex float representation (C-style), guaranteed to
 * evaluate at compile time.
 */

internal fun hf64(s: String = ""): String =
    HF64_TEMPLATE.replace("\$s", s)

/**
 * Construct a 128-bit float from hex float representation (C-style), guaranteed to
 * evaluate at compile time.
 */

internal fun hf128(s: String = ""): String =
    if (CFG_F128_ENABLED) {
        HF128_TEMPLATE.replace("\$s", s)
    } else {
        ""
    }

/**
 * Assert `F.biteq` with better messages.
 */

internal fun assertBiteq(left: String = "", right: String = "", tt: String = ""): String =
    when {
        left.isNotEmpty() && right.isNotEmpty() && tt.isNotEmpty() -> arrayOf("\$left" to left, "\$right" to right, "\$tt" to tt).fold(ASSERT_BITEQ_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        left.isNotEmpty() && right.isNotEmpty() -> arrayOf("\$left" to left, "\$right" to right).fold(ASSERT_BITEQ_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }
