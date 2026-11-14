// port-lint: source internal_macros.rs
package io.github.kotlinmania.rustlang.core

private const val FORWARD_REF_UNOP_TEMPLATE: String = "\$()+ const class \$imp forLoop \$t {type Output=< \$t asTarget \$imp>.Output; fun \$method(this)->< \$t asTarget \$imp>.Output {\$imp. \$method(* this)}}"

private const val FORWARD_REF_BINOP_TEMPLATE: String = "\$()+ const class \$imp< \$u> forLoop \$t {type Output=< \$t asTarget \$imp< \$u>>.Output; fun \$method(this, other: \$u)->< \$t asTarget \$imp< \$u>>.Output {\$imp. \$method(* this, other)}}\$()+ const class \$imp< \$u> forLoop \$t {type Output=< \$t asTarget \$imp< \$u>>.Output; fun \$method(this, other: \$u)->< \$t asTarget \$imp< \$u>>.Output {\$imp. \$method(this, * other)}}\$()+ const class \$imp< \$u> forLoop \$t {type Output=< \$t asTarget \$imp< \$u>>.Output; fun \$method(this, other: \$u)->< \$t asTarget \$imp< \$u>>.Output {\$imp. \$method(* this, * other)}}"

private const val FORWARD_REF_OP_ASSIGN_TEMPLATE: String = "\$()+ const class \$imp< \$u> forLoop \$t {fun \$method(& var this, other: \$u) {\$imp. \$method(this, * other); }}"

private const val IMPL_FN_FOR_ZST_TEMPLATE: String = "\$(\$()* \$vis class \$name; class\$(<\$(\$lifetime),+>)? Fn<(\$(\$argTy, )*)> forLoop \$name {extern \"rust-call\" fun call(& this, (\$(\$arg, )*):(\$(\$argTy, )*))-> \$returnTy {\$body}} class\$(<\$(\$lifetime),+>)? FnMut<(\$(\$argTy, )*)> forLoop \$name {extern \"rust-call\" fun callMut(& var this, (\$(\$arg, )*):(\$(\$argTy, )*))-> \$returnTy {Fn.call(&* this, (\$(\$arg, )*))}} class\$(<\$(\$lifetime),+>)? FnOnce<(\$(\$argTy, )*)> forLoop \$name {type Output= \$returnTy; extern \"rust-call\" fun callOnce(this, (\$(\$arg, )*):(\$(\$argTy, )*))-> \$returnTy {Fn.call(& this, (\$(\$arg, )*))}})+"

internal fun forwardRefUnop(imp: String = "", method: String = "", t: String = "", attr: String = ""): String =
    if (attr.isEmpty()) {
        arrayOf("\$imp" to imp, "\$method" to method, "\$t" to t).fold(FORWARD_REF_UNOP_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$imp" to imp, "\$method" to method, "\$t" to t).fold(FORWARD_REF_UNOP_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }

internal fun forwardRefBinop(imp: String = "", method: String = "", t: String = "", u: String = "", attr: String = ""): String =
    if (attr.isEmpty()) {
        arrayOf("\$imp" to imp, "\$method" to method, "\$t" to t, "\$u" to u).fold(FORWARD_REF_BINOP_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$imp" to imp, "\$method" to method, "\$t" to t, "\$u" to u).fold(FORWARD_REF_BINOP_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }

internal fun forwardRefOpAssign(imp: String = "", method: String = "", t: String = "", u: String = "", attr: String = ""): String =
    if (attr.isEmpty()) {
        arrayOf("\$imp" to imp, "\$method" to method, "\$t" to t, "\$u" to u).fold(FORWARD_REF_OP_ASSIGN_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$imp" to imp, "\$method" to method, "\$t" to t, "\$u" to u).fold(FORWARD_REF_OP_ASSIGN_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }

/**
 * Creates a zero-size type similar to a closure type, but named.
 */

internal fun implFnForZst(attr: String = "", vis: String = "", name: String = "", lifetime: String = "", arg: String = "", argTy: String = "", returnTy: String = "", body: String = ""): String =
    if (attr.isEmpty()) {
        arrayOf("\$vis" to vis, "\$name" to name, "\$lifetime" to lifetime, "\$arg" to arg, "\$argTy" to argTy, "\$returnTy" to returnTy, "\$body" to body).fold(IMPL_FN_FOR_ZST_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$vis" to vis, "\$name" to name, "\$lifetime" to lifetime, "\$arg" to arg, "\$argTy" to argTy, "\$returnTy" to returnTy, "\$body" to body).fold(IMPL_FN_FOR_ZST_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }

/**
 * Permanently unstable. Only used in internal rustc tests. Do not use.
 */

public fun testBinderConstraints(arg: String = ""): String =
    if (arg.isEmpty()) {
        ""
    } else {
        ""
    }
