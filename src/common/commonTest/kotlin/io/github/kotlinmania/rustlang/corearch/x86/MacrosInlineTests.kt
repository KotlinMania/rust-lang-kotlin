// port-lint: tests x86/macros.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Utility macros.

internal const val CFG_TEST: Boolean = false

private const val ASSERT_APPROX_EQ_TEMPLATE: String = "{val(a, b)=(\$a, \$b); assert((* a-* b).abs()< \$eps, \"assertion failed: `(left !== right)` \\\n             (left: `{:?}`, right: `{:?}`, expect diff: `{:?}`, real diff: `{:?}`)\", * a, * b, \$eps, (* a-* b).abs()); }"

internal fun assertApproxEq(a: String = "", b: String = "", eps: String = ""): String =
    if (CFG_TEST) {
        arrayOf("\$a" to a, "\$b" to b, "\$eps" to eps).fold(ASSERT_APPROX_EQ_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        ""
    }
