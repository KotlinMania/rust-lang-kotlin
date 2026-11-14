// port-lint: source vendor/arm.rs
package io.github.kotlinmania.rustlang.coresimd.vendor.arm

private const val TUPLE_TEMPLATE: String = "fromTransmute {unsafe Simd< \$scalar, {sizeOf.< \$tuple>()/ sizeOf.< \$scalar>()}>=> \$tuple}"

private const val TUPLE_TEMPLATE_1: String = "\$(tuple {\$scalar, \$tuples})*"

/**
 * Transmute between `Simd` and ACLE tuple types.
 */

internal fun tuple(scalar: String = "", tuple: String = "", tuples: String = ""): String =
    when {
        scalar.isNotEmpty() && tuple.isNotEmpty() -> arrayOf("\$scalar" to scalar, "\$tuple" to tuple).fold(TUPLE_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        scalar.isNotEmpty() && tuples.isNotEmpty() -> arrayOf("\$scalar" to scalar, "\$tuples" to tuples).fold(TUPLE_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }
