// port-lint: source powerpc/macros.rs
package io.github.kotlinmania.rustlang.corearch.powerpc.macros

private const val TEST_IMPL_TEMPLATE: String = "public unsafe fun \$fn(\$(\$v: \$ty),*)-> \$r {\$call(\$(\$v),*)}"

private const val TEST_IMPL_TEMPLATE_1: String = "testImpl {\$fn(\$(\$v: \$ty),*)-> \$r[\$call, \$instrAltivec/ \$instrVsx/ \$instrVsx]}"

private const val TEST_IMPL_TEMPLATE_2: String = "public unsafe fun \$fn(\$(\$v: \$ty),*)-> \$r {\$call(\$(\$v),*)}"

private const val IMPL_VEC_TRAIT_TEMPLATE: String = "class \$trait forLoop \$a {unsafe fun \$m(this)-> Self {\$fn(transmute(this))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_1: String = "class \$trait forLoop \$a {type Result= \$r; unsafe fun \$m(this)-> Self.Result {\$fn(transmute(this))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_2: String = "class \$trait forLoop \$a {type Result= \$r; unsafe fun \$m(this)-> Self.Result {transmute(\$fn(transmute(this)))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_3: String = "implVecTrait {[\$trait \$m] \$ub(vectorUnsignedChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m] \$sb(vectorSignedChar)-> vectorSignedChar} implVecTrait {[\$trait \$m] \$uh(vectorUnsignedShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m] \$sh(vectorSignedShort)-> vectorSignedShort} implVecTrait {[\$trait \$m] \$uw(vectorUnsignedInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m] \$sw(vectorSignedInt)-> vectorSignedInt} implVecTrait {[\$trait \$m] \$sf(vectorFloat)-> vectorFloat}"

private const val IMPL_VEC_TRAIT_TEMPLATE_4: String = "class \$trait< \$b> forLoop \$a {type Result= \$r; unsafe fun \$m(this, b: \$b)-> Self.Result {\$fn(transmute(this), transmute(b))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_5: String = "class \$trait< \$b> forLoop \$a {type Result= \$r; unsafe fun \$m(this, b: \$b)-> Self.Result {transmute(\$fn(transmute(this), transmute(b)))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_6: String = "implVecTrait {[\$trait \$m] \$fn(\$a, \$a)-> \$r} implVecTrait {[\$trait \$m] \$fn(\$a, \$b)-> \$r} implVecTrait {[\$trait \$m] \$fn(\$b, \$a)-> \$r}"

private const val IMPL_VEC_TRAIT_TEMPLATE_7: String = "implVecTrait {[\$trait \$m] \$ub(vectorUnsignedChar, ~ vectorBoolChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m] \$sb(vectorSignedChar, ~ vectorBoolChar)-> vectorSignedChar} implVecTrait {[\$trait \$m] \$uh(vectorUnsignedShort, ~ vectorBoolShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m] \$sh(vectorSignedShort, ~ vectorBoolShort)-> vectorSignedShort} implVecTrait {[\$trait \$m] \$uw(vectorUnsignedInt, ~ vectorBoolInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m] \$sw(vectorSignedInt, ~ vectorBoolInt)-> vectorSignedInt}"

private const val IMPL_VEC_TRAIT_TEMPLATE_8: String = "implVecTrait {[\$trait \$m]~(\$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg)}"

private const val IMPL_VEC_TRAIT_TEMPLATE_9: String = "implVecTrait {[\$trait \$m] \$ub(vectorUnsignedChar, vectorUnsignedChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m] \$sb(vectorSignedChar, vectorSignedChar)-> vectorSignedChar} implVecTrait {[\$trait \$m] \$uh(vectorUnsignedShort, vectorUnsignedShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m] \$sh(vectorSignedShort, vectorSignedShort)-> vectorSignedShort} implVecTrait {[\$trait \$m] \$uw(vectorUnsignedInt, vectorUnsignedInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m] \$sw(vectorSignedInt, vectorSignedInt)-> vectorSignedInt}"

private const val IMPL_VEC_TRAIT_TEMPLATE_10: String = "implVecTrait {[\$trait \$m](\$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg)}"

private const val IMPL_VEC_TRAIT_TEMPLATE_11: String = "implVecTrait {[\$trait \$m]+ \$b(vectorBoolChar, vectorBoolChar)-> vectorBoolChar} implVecTrait {[\$trait \$m]+ \$b(vectorUnsignedChar, vectorUnsignedChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m]+ \$b(vectorSignedChar, vectorSignedChar)-> vectorSignedChar} implVecTrait {[\$trait \$m]+ \$h(vectorBoolShort, vectorBoolShort)-> vectorBoolShort} implVecTrait {[\$trait \$m]+ \$h(vectorUnsignedShort, vectorUnsignedShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m]+ \$h(vectorSignedShort, vectorSignedShort)-> vectorSignedShort} implVecTrait {[\$trait \$m]+ \$w(vectorBoolInt, vectorBoolInt)-> vectorBoolInt} implVecTrait {[\$trait \$m]+ \$w(vectorUnsignedInt, vectorUnsignedInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m]+ \$w(vectorSignedInt, vectorSignedInt)-> vectorSignedInt}"

private const val IMPL_VEC_TRAIT_TEMPLATE_12: String = "implVecTrait {[\$trait \$m]+ 2b(\$fnArg, \$fnArg, \$fnArg)}"

private const val S_T_L_TEMPLATE: String = "vectorSignedInt"

private const val S_T_L_TEMPLATE_1: String = "vectorSignedShort"

private const val S_T_L_TEMPLATE_2: String = "vectorSignedChar"

private const val S_T_L_TEMPLATE_3: String = "vectorUnsignedInt"

private const val S_T_L_TEMPLATE_4: String = "vectorUnsignedShort"

private const val S_T_L_TEMPLATE_5: String = "vectorUnsignedChar"

private const val S_T_L_TEMPLATE_6: String = "vectorFloat"

private const val T_T_L_TEMPLATE: String = "vectorSignedInt"

private const val T_T_L_TEMPLATE_1: String = "vectorSignedShort"

private const val T_T_L_TEMPLATE_2: String = "vectorSignedChar"

private const val T_T_L_TEMPLATE_3: String = "vectorUnsignedInt"

private const val T_T_L_TEMPLATE_4: String = "vectorUnsignedShort"

private const val T_T_L_TEMPLATE_5: String = "vectorUnsignedChar"

private const val T_T_L_TEMPLATE_6: String = "vectorFloat"

private const val T_T_S_TEMPLATE: String = "i32x4"

private const val T_T_S_TEMPLATE_1: String = "i16x8"

private const val T_T_S_TEMPLATE_2: String = "i8x16"

private const val T_T_S_TEMPLATE_3: String = "u32x4"

private const val T_T_S_TEMPLATE_4: String = "u16x8"

private const val T_T_S_TEMPLATE_5: String = "u8x16"

private const val T_T_S_TEMPLATE_6: String = "f32x4"

private const val T_U_TEMPLATE: String = "vectorUnsignedChar"

private const val T_U_TEMPLATE_1: String = "vectorUnsignedShort"

private const val T_U_TEMPLATE_2: String = "vectorUnsignedInt"

private const val T_U_TEMPLATE_3: String = "vectorUnsignedChar"

private const val T_U_TEMPLATE_4: String = "vectorUnsignedShort"

private const val T_U_TEMPLATE_5: String = "vectorUnsignedInt"

private const val T_U_TEMPLATE_6: String = "vectorUnsignedChar"

private const val T_U_TEMPLATE_7: String = "vectorUnsignedShort"

private const val T_U_TEMPLATE_8: String = "vectorUnsignedInt"

private const val T_U_TEMPLATE_9: String = "vectorUnsignedInt"

private const val T_B_TEMPLATE: String = "vectorBoolChar"

private const val T_B_TEMPLATE_1: String = "vectorBoolShort"

private const val T_B_TEMPLATE_2: String = "vectorBoolInt"

private const val T_B_TEMPLATE_3: String = "vectorBoolChar"

private const val T_B_TEMPLATE_4: String = "vectorBoolShort"

private const val T_B_TEMPLATE_5: String = "vectorBoolInt"

private const val T_B_TEMPLATE_6: String = "vectorBoolChar"

private const val T_B_TEMPLATE_7: String = "vectorBoolShort"

private const val T_B_TEMPLATE_8: String = "vectorBoolInt"

private const val T_B_TEMPLATE_9: String = "vectorBoolInt"

internal fun testImpl(fn: String = "", v: String = "", ty: String = "", r: String = "", call: String = "", instr: String = "", instrAltivec: String = "", instrVsx: String = "", instrPwr9: String = "", clause: String = ""): String =
    when {
        clause == "- > / /" && fn.isNotEmpty() && v.isNotEmpty() && ty.isNotEmpty() && r.isNotEmpty() && call.isNotEmpty() && instrAltivec.isNotEmpty() && instrVsx.isNotEmpty() && instrPwr9.isNotEmpty() -> arrayOf("\$fn" to fn, "\$v" to v, "\$ty" to ty, "\$r" to r, "\$call" to call).fold(TEST_IMPL_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- > /" && fn.isNotEmpty() && v.isNotEmpty() && ty.isNotEmpty() && r.isNotEmpty() && call.isNotEmpty() && instrAltivec.isNotEmpty() && instrVsx.isNotEmpty() -> arrayOf("\$fn" to fn, "\$v" to v, "\$ty" to ty, "\$r" to r, "\$call" to call, "\$instrAltivec" to instrAltivec, "\$instrVsx" to instrVsx).fold(TEST_IMPL_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- >" && fn.isNotEmpty() && v.isNotEmpty() && ty.isNotEmpty() && r.isNotEmpty() && call.isNotEmpty() && instr.isNotEmpty() -> arrayOf("\$fn" to fn, "\$v" to v, "\$ty" to ty, "\$r" to r, "\$call" to call).fold(TEST_IMPL_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

internal fun implVecTrait(trait: String = "", m: String = "", fn: String = "", a: String = "", r: String = "", ub: String = "", sb: String = "", uh: String = "", sh: String = "", uw: String = "", sw: String = "", sf: String = "", b: String = "", fnArg: String = "", h: String = "", w: String = "", clause: String = ""): String =
    when {
        clause == "v1" && trait.isNotEmpty() && m.isNotEmpty() && ub.isNotEmpty() && sb.isNotEmpty() && uh.isNotEmpty() && sh.isNotEmpty() && uw.isNotEmpty() && sw.isNotEmpty() && sf.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$ub" to ub, "\$sb" to sb, "\$uh" to uh, "\$sh" to sh, "\$uw" to uw, "\$sw" to sw, "\$sf" to sf).fold(IMPL_VEC_TRAIT_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "~" && trait.isNotEmpty() && m.isNotEmpty() && ub.isNotEmpty() && sb.isNotEmpty() && uh.isNotEmpty() && sh.isNotEmpty() && uw.isNotEmpty() && sw.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$ub" to ub, "\$sb" to sb, "\$uh" to uh, "\$sh" to sh, "\$uw" to uw, "\$sw" to sw).fold(IMPL_VEC_TRAIT_TEMPLATE_7) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "v2" && trait.isNotEmpty() && m.isNotEmpty() && ub.isNotEmpty() && sb.isNotEmpty() && uh.isNotEmpty() && sh.isNotEmpty() && uw.isNotEmpty() && sw.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$ub" to ub, "\$sb" to sb, "\$uh" to uh, "\$sh" to sh, "\$uw" to uw, "\$sw" to sw).fold(IMPL_VEC_TRAIT_TEMPLATE_9) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && b.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$b" to b, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ - >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && b.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$b" to b, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_5) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "~ - >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && b.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$b" to b, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_6) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ - >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ v2b" && trait.isNotEmpty() && m.isNotEmpty() && b.isNotEmpty() && h.isNotEmpty() && w.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$b" to b, "\$h" to h, "\$w" to w).fold(IMPL_VEC_TRAIT_TEMPLATE_11) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "~" && trait.isNotEmpty() && m.isNotEmpty() && fnArg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fnArg" to fnArg).fold(IMPL_VEC_TRAIT_TEMPLATE_8) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "v2" && trait.isNotEmpty() && m.isNotEmpty() && fnArg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fnArg" to fnArg).fold(IMPL_VEC_TRAIT_TEMPLATE_10) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ v2b" && trait.isNotEmpty() && m.isNotEmpty() && fnArg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fnArg" to fnArg).fold(IMPL_VEC_TRAIT_TEMPLATE_12) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a).fold(IMPL_VEC_TRAIT_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

internal fun sTL(clause: String = ""): String =
    when {
        clause == "i32x4" -> S_T_L_TEMPLATE
        clause == "i16x8" -> S_T_L_TEMPLATE_1
        clause == "i8x16" -> S_T_L_TEMPLATE_2
        clause == "u32x4" -> S_T_L_TEMPLATE_3
        clause == "u16x8" -> S_T_L_TEMPLATE_4
        clause == "u8x16" -> S_T_L_TEMPLATE_5
        clause == "f32x4" -> S_T_L_TEMPLATE_6
        else -> ""
    }

internal fun tTL(clause: String = ""): String =
    when {
        clause == "i32" -> T_T_L_TEMPLATE
        clause == "i16" -> T_T_L_TEMPLATE_1
        clause == "i8" -> T_T_L_TEMPLATE_2
        clause == "u32" -> T_T_L_TEMPLATE_3
        clause == "u16" -> T_T_L_TEMPLATE_4
        clause == "u8" -> T_T_L_TEMPLATE_5
        clause == "f32" -> T_T_L_TEMPLATE_6
        else -> ""
    }

internal fun tTS(clause: String = ""): String =
    when {
        clause == "i32" -> T_T_S_TEMPLATE
        clause == "i16" -> T_T_S_TEMPLATE_1
        clause == "i8" -> T_T_S_TEMPLATE_2
        clause == "u32" -> T_T_S_TEMPLATE_3
        clause == "u16" -> T_T_S_TEMPLATE_4
        clause == "u8" -> T_T_S_TEMPLATE_5
        clause == "f32" -> T_T_S_TEMPLATE_6
        else -> ""
    }

internal fun tU(clause: String = ""): String =
    when {
        clause == "vectorBoolChar" -> T_U_TEMPLATE
        clause == "vectorBoolShort" -> T_U_TEMPLATE_1
        clause == "vectorBoolInt" -> T_U_TEMPLATE_2
        clause == "vectorUnsignedChar" -> T_U_TEMPLATE_3
        clause == "vectorUnsignedShort" -> T_U_TEMPLATE_4
        clause == "vectorUnsignedInt" -> T_U_TEMPLATE_5
        clause == "vectorSignedChar" -> T_U_TEMPLATE_6
        clause == "vectorSignedShort" -> T_U_TEMPLATE_7
        clause == "vectorSignedInt" -> T_U_TEMPLATE_8
        clause == "vectorFloat" -> T_U_TEMPLATE_9
        else -> ""
    }

internal fun tB(clause: String = ""): String =
    when {
        clause == "vectorBoolChar" -> T_B_TEMPLATE
        clause == "vectorBoolShort" -> T_B_TEMPLATE_1
        clause == "vectorBoolInt" -> T_B_TEMPLATE_2
        clause == "vectorSignedChar" -> T_B_TEMPLATE_3
        clause == "vectorSignedShort" -> T_B_TEMPLATE_4
        clause == "vectorSignedInt" -> T_B_TEMPLATE_5
        clause == "vectorUnsignedChar" -> T_B_TEMPLATE_6
        clause == "vectorUnsignedShort" -> T_B_TEMPLATE_7
        clause == "vectorUnsignedInt" -> T_B_TEMPLATE_8
        clause == "vectorFloat" -> T_B_TEMPLATE_9
        else -> ""
    }
