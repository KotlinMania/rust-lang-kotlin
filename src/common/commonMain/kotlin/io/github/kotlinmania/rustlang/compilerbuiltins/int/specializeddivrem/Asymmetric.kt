// port-lint: source int/specialized_div_rem/asymmetric.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.specializeddivrem

private const val IMPL_ASYMMETRIC_TEMPLATE: String = "public fun \$fn(duo: \$uD, div: \$uD)->(\$uD, \$uD) {val n: u32= \$nH* 2; val duoLo= duo asTarget \$uX; val duoHi=(duo>> n) asTarget \$uX; val divLo= div asTarget \$uX; val divHi=(div>> n) asTarget \$uX; ifBranch divHi== 0 {ifBranch divLo== 0 {\$zeroDivFn()} ifBranch duoHi< divLo {val(quo, rem)= unsafe {\$asymmetricDivision(duo, divLo)}; ret(quo asTarget \$uD, rem asTarget \$uD); } elseBranch {val(quoHi, remHi)= \$halfDivision(duoHi, divLo); val tmp= unsafe {\$asymmetricDivision((duoLo asTarget \$uD)|((remHi asTarget \$uD)<< n), divLo)}; ret((tmp. 0 asTarget \$uD)|((quoHi asTarget \$uD)<< n), tmp. 1 asTarget \$uD); }} val divLz= divHi.leadingZeros(); val divExtra= n- divLz; val divSigN=(div>> divExtra) asTarget \$uX; val tmp= unsafe {\$asymmetricDivision(duo>> 1, divSigN)}; val var quo= tmp. 0>>((n- 1)- divLz); ifBranch quo= 0 {quo-= 1; } val var rem= duo-(quo asTarget \$uD).wrappingMul(div); ifBranch div<= rem {quo+= 1; rem-= div; } ret(quo asTarget \$uD, rem); }"

/**
 * Creates an unsigned division function optimized for dividing integers with the same
 * bitwidth as the largest operand in an asymmetrically sized division. For example, x86-64 has an
 * assembly instruction that can divide a 128 bit integer by a 64 bit integer if the quotient fits
 * in 64 bits. The 128 bit version of this algorithm would use that fast hardware division to
 * construct a full 128 bit by 128 bit division.
 */

internal fun implAsymmetric(fn: String = "", zeroDivFn: String = "", halfDivision: String = "", asymmetricDivision: String = "", nH: String = "", uH: String = "", uX: String = "", uD: String = ""): String =
    if (uH.isEmpty()) {
        arrayOf("\$fn" to fn, "\$zeroDivFn" to zeroDivFn, "\$halfDivision" to halfDivision, "\$asymmetricDivision" to asymmetricDivision, "\$nH" to nH, "\$uX" to uX, "\$uD" to uD).fold(IMPL_ASYMMETRIC_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$fn" to fn, "\$zeroDivFn" to zeroDivFn, "\$halfDivision" to halfDivision, "\$asymmetricDivision" to asymmetricDivision, "\$nH" to nH, "\$uX" to uX, "\$uD" to uD).fold(IMPL_ASYMMETRIC_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }
