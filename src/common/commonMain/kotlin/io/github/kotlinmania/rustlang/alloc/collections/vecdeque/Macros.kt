// port-lint: source collections/vec_deque/macros.rs
package io.github.kotlinmania.rustlang.alloc.collections.vecdeque

private const val IMPL_SLICE_EQ1_TEMPLATE: String = "class< T, U, A: Allocator, \$(\$vars)*> PartialEq< \$rhs> forLoop \$lhs where T: PartialEq< U>, \$(\$constraints)* {fun eq(& this, other: \$rhs)-> bool {ifBranch this.len()!= other.len() {ret falseVal; } val(sa, sb)= this.asSlices(); val(oa, ob)= other[..].splitAt(sa.len()); sa== oa&& sb== ob}}"

internal fun implSliceEq1(vars: String = "", lhs: String = "", rhs: String = "", constraints: String = ""): String =
    arrayOf("\$vars" to vars, "\$lhs" to lhs, "\$rhs" to rhs, "\$constraints" to constraints).fold(IMPL_SLICE_EQ1_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
