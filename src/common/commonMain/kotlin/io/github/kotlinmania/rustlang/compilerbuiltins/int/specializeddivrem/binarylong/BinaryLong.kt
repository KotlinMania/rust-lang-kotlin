// port-lint: source int/specialized_div_rem/binary_long.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.specializeddivrem.binarylong

private const val IMPL_BINARY_LONG_TEMPLATE: String = "\$()* public fun \$fn(duo: \$uX, div: \$uX)->(\$uX, \$uX) {val var duo= duo; ifBranch div== 0 {\$zeroDivFn()} ifBranch duo< div {ret(0, duo); } val divOriginal= div; val var shl= \$normalizationShift(duo, div, trueVal); val var div: \$uX=(div<< shl); duo= duo.wrappingSub(div); val var quo: \$uX= 1<< shl; ifBranch duo< divOriginal {ret(quo, duo); } val mask: \$uX; ifBranch(div asTarget \$iX)< 0 {div>>= 1; shl-= 1; val tmp= 1<< shl; mask= tmp- 1; val sub= duo.wrappingSub(div); ifBranch(sub asTarget \$iX)>= 0 {duo= sub; quo|= tmp; } ifBranch duo< divOriginal {ret(quo, duo); }} elseBranch {mask= quo- 1; } div= div.wrappingSub(1); val var i= shl; loop {ifBranch i== 0 {breakTarget; } i-= 1; duo= duo.wrappingShl(1).wrappingSub(div); val mask=(duo asTarget \$iX).wrappingShr(\$n- 1) asTarget \$uX; duo= duo.wrappingAdd(div& mask); } ret((duo& mask)| quo, duo>> shl); }"

/**
 * Creates an unsigned division function that uses binary long division, designed for
 * computer architectures without division instructions. These functions have good performance for
 * microarchitectures with large branch miss penalties and architectures without the ability to
 * predicate instructions. For architectures with predicated instructions, one of the algorithms
 * described in the documentation of these functions probably has higher performance, and a custom
 * assembly routine should be used instead.
 */

internal fun implBinaryLong(fn: String = "", zeroDivFn: String = "", normalizationShift: String = "", n: String = "", uX: String = "", iX: String = "", funAttr: String = ""): String =
    if (funAttr.isEmpty()) {
        arrayOf("\$fn" to fn, "\$zeroDivFn" to zeroDivFn, "\$normalizationShift" to normalizationShift, "\$n" to n, "\$uX" to uX, "\$iX" to iX).fold(IMPL_BINARY_LONG_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$fn" to fn, "\$zeroDivFn" to zeroDivFn, "\$normalizationShift" to normalizationShift, "\$n" to n, "\$uX" to uX, "\$iX" to iX).fold(IMPL_BINARY_LONG_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }
