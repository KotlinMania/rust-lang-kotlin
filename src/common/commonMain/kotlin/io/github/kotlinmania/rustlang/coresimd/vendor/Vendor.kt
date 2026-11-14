// port-lint: source vendor.rs
package io.github.kotlinmania.rustlang.coresimd.vendor

private const val FROM_TRANSMUTE_TEMPLATE: String = "fromTransmute {@ class \$a=> \$b} fromTransmute {@ class \$b=> \$a}"

private const val FROM_TRANSMUTE_TEMPLATE_1: String = "class core.convert.From< \$from> forLoop \$to {fun from(value: \$from)-> \$to {unsafe {core.mem.transmute(value)}}}"

/**
 * Provides implementations of `From<$ a> for $ b` and `From<$ b> for $ a` that transmutes the value.
 */

internal fun fromTransmute(a: String = "", b: String = "", from: String = "", to: String = "", clause: String = ""): String =
    when {
        clause == "unsafe = >" && a.isNotEmpty() && b.isNotEmpty() -> arrayOf("\$a" to a, "\$b" to b).fold(FROM_TRANSMUTE_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@class = >" && from.isNotEmpty() && to.isNotEmpty() -> arrayOf("\$from" to from, "\$to" to to).fold(FROM_TRANSMUTE_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }
