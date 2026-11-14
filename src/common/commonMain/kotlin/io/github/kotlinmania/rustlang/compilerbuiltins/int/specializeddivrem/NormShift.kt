// port-lint: source int/specialized_div_rem/norm_shift.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.specializeddivrem

private const val IMPL_NORMALIZATION_SHIFT_TEMPLATE: String = "\$()* fun \$name(duo: \$uX, div: \$uX, fullNormalization: bool)-> usize {val var shl: usize; ifBranch \$useLz {shl=(div.leadingZeros()- duo.leadingZeros()) asTarget usize; ifBranch fullNormalization {ifBranch duo<(div<< shl) {shl-= 1; }}} elseBranch {val var test= duo; shl= 0usize; val var lvl= \$n>> 1; loop {val tmp= test>> lvl; ifBranch div<= tmp {test= tmp; shl+= lvl; } lvl>>= 1; ifBranch lvl== 0 {breakTarget}}} shl}"

/**
 * Creates a function used by some division algorithms to compute the "normalization shift".
 */

internal fun implNormalizationShift(name: String = "", useLz: String = "", n: String = "", uX: String = "", iX: String = "", unsignedAttr: String = ""): String =
    if (iX.isEmpty() && unsignedAttr.isEmpty()) {
        arrayOf("\$name" to name, "\$useLz" to useLz, "\$n" to n, "\$uX" to uX).fold(IMPL_NORMALIZATION_SHIFT_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$name" to name, "\$useLz" to useLz, "\$n" to n, "\$uX" to uX).fold(IMPL_NORMALIZATION_SHIFT_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }
