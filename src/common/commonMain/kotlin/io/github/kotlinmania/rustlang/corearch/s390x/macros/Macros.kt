// port-lint: source s390x/macros.rs
package io.github.kotlinmania.rustlang.corearch.s390x.macros

private const val TEST_IMPL_TEMPLATE: String = "public unsafe fun \$fn(\$(\$v: \$ty),*)-> \$r {\$call(\$(\$v),*)}"

private const val TEST_IMPL_TEMPLATE_1: String = "public unsafe fun \$fn(\$(\$v: \$ty),*)-> \$r {transmute(\$call(\$(\$v),*))}"

private const val TEST_IMPL_TEMPLATE_2: String = "public unsafe fun \$fn(\$(\$v: \$ty),*)-> \$r {transmute(\$call(\$(\$v),*))}"

private const val TEST_IMPL_TEMPLATE_3: String = "public unsafe fun \$fn(\$(\$v: \$ty),*)-> \$r {\$call(\$(\$v),*)}"

private const val TEST_IMPL_TEMPLATE_4: String = "public unsafe fun \$fn(\$(\$v: \$ty),*)-> \$r {\$call(\$(\$v),*)}"

private const val IMPL_VEC_TRAIT_TEMPLATE: String = "class \$trait forLoop \$a {unsafe fun \$m(this)-> Self {\$fn(transmute(this))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_1: String = "class \$trait forLoop \$a {unsafe fun \$m(this)-> Self {transmute(\$fn(transmute(this)))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_2: String = "class \$trait forLoop \$a {type Result= \$r; unsafe fun \$m(this)-> Self.Result {\$fn(transmute(this))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_3: String = "class \$trait forLoop \$a {type Result= \$r; unsafe fun \$m(this)-> Self.Result {transmute(\$fn(transmute(this)))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_4: String = "implVecTrait {[\$trait \$m] \$ub(vectorUnsignedChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m] \$sb(vectorSignedChar)-> vectorSignedChar} implVecTrait {[\$trait \$m] \$uh(vectorUnsignedShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m] \$sh(vectorSignedShort)-> vectorSignedShort} implVecTrait {[\$trait \$m] \$uw(vectorUnsignedInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m] \$sw(vectorSignedInt)-> vectorSignedInt} implVecTrait {[\$trait \$m] \$uw(vectorUnsignedLongLong)-> vectorUnsignedLongLong} implVecTrait {[\$trait \$m] \$sw(vectorSignedLongLong)-> vectorSignedLongLong} implVecTrait {[\$trait \$m] \$sf(vectorFloat)-> vectorFloat} implVecTrait {[\$trait \$m] \$sf(vectorDouble)-> vectorDouble}"

private const val IMPL_VEC_TRAIT_TEMPLATE_5: String = "class \$trait< \$b> forLoop \$a {type Result= \$r; unsafe fun \$m(this, b: \$b)-> Self.Result {\$fn(transmute(this), transmute(b))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_6: String = "class \$trait< \$b> forLoop \$a {type Result= \$r; unsafe fun \$m(this, b: \$b)-> Self.Result {transmute(\$fn(transmute(this), transmute(b)))}}"

private const val IMPL_VEC_TRAIT_TEMPLATE_7: String = "implVecTrait {[\$trait \$m] \$fn(\$a, \$a)-> \$r} implVecTrait {[\$trait \$m] \$fn(\$a, \$b)-> \$r} implVecTrait {[\$trait \$m] \$fn(\$b, \$a)-> \$r}"

private const val IMPL_VEC_TRAIT_TEMPLATE_8: String = "implVecTrait {[\$trait \$m] \$ub(vectorUnsignedChar, ~ vectorBoolChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m] \$sb(vectorSignedChar, ~ vectorBoolChar)-> vectorSignedChar} implVecTrait {[\$trait \$m] \$uh(vectorUnsignedShort, ~ vectorBoolShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m] \$sh(vectorSignedShort, ~ vectorBoolShort)-> vectorSignedShort} implVecTrait {[\$trait \$m] \$uw(vectorUnsignedInt, ~ vectorBoolInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m] \$sw(vectorSignedInt, ~ vectorBoolInt)-> vectorSignedInt} implVecTrait {[\$trait \$m] \$ug(vectorUnsignedLongLong, ~ vectorBoolLongLong)-> vectorUnsignedLongLong} implVecTrait {[\$trait \$m] \$sg(vectorSignedLongLong, ~ vectorBoolLongLong)-> vectorSignedLongLong}"

private const val IMPL_VEC_TRAIT_TEMPLATE_9: String = "implVecTrait {[\$trait \$m]~(\$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg)}"

private const val IMPL_VEC_TRAIT_TEMPLATE_10: String = "implVecTrait {[\$trait \$m] \$ub(vectorUnsignedChar, vectorUnsignedChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m] \$sb(vectorSignedChar, vectorSignedChar)-> vectorSignedChar} implVecTrait {[\$trait \$m] \$uh(vectorUnsignedShort, vectorUnsignedShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m] \$sh(vectorSignedShort, vectorSignedShort)-> vectorSignedShort} implVecTrait {[\$trait \$m] \$uw(vectorUnsignedInt, vectorUnsignedInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m] \$sw(vectorSignedInt, vectorSignedInt)-> vectorSignedInt} implVecTrait {[\$trait \$m] \$ug(vectorUnsignedLongLong, vectorUnsignedLongLong)-> vectorUnsignedLongLong} implVecTrait {[\$trait \$m] \$sg(vectorSignedLongLong, vectorSignedLongLong)-> vectorSignedLongLong}"

private const val IMPL_VEC_TRAIT_TEMPLATE_11: String = "implVecTrait {[\$trait \$m](\$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg)}"

private const val IMPL_VEC_TRAIT_TEMPLATE_12: String = "implVecTrait {[\$trait \$m]+ \$b(vectorBoolChar, vectorBoolChar)-> vectorBoolChar} implVecTrait {[\$trait \$m]+ \$b(vectorUnsignedChar, vectorUnsignedChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m]+ \$b(vectorSignedChar, vectorSignedChar)-> vectorSignedChar} implVecTrait {[\$trait \$m]+ \$h(vectorBoolShort, vectorBoolShort)-> vectorBoolShort} implVecTrait {[\$trait \$m]+ \$h(vectorUnsignedShort, vectorUnsignedShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m]+ \$h(vectorSignedShort, vectorSignedShort)-> vectorSignedShort} implVecTrait {[\$trait \$m]+ \$w(vectorBoolInt, vectorBoolInt)-> vectorBoolInt} implVecTrait {[\$trait \$m]+ \$w(vectorUnsignedInt, vectorUnsignedInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m]+ \$w(vectorSignedInt, vectorSignedInt)-> vectorSignedInt} implVecTrait {[\$trait \$m]+ \$g(vectorUnsignedLongLong, vectorUnsignedLongLong)-> vectorUnsignedLongLong} implVecTrait {[\$trait \$m]+ \$g(vectorSignedLongLong, vectorSignedLongLong)-> vectorSignedLongLong}"

private const val IMPL_VEC_TRAIT_TEMPLATE_13: String = "implVecTrait {[\$trait \$m]+ 2b(\$fnArg, \$fnArg, \$fnArg, \$fnArg)}"

private const val IMPL_VEC_TRAIT_TEMPLATE_14: String = "implVecTrait {[\$trait \$m]+ \$b(vectorBoolChar, vectorBoolChar)-> vectorBoolChar} implVecTrait {[\$trait \$m]+ \$b(vectorUnsignedChar, vectorUnsignedChar)-> vectorUnsignedChar} implVecTrait {[\$trait \$m]+ \$b(vectorSignedChar, vectorSignedChar)-> vectorSignedChar} implVecTrait {[\$trait \$m]+ \$h(vectorBoolShort, vectorBoolShort)-> vectorBoolShort} implVecTrait {[\$trait \$m]+ \$h(vectorUnsignedShort, vectorUnsignedShort)-> vectorUnsignedShort} implVecTrait {[\$trait \$m]+ \$h(vectorSignedShort, vectorSignedShort)-> vectorSignedShort} implVecTrait {[\$trait \$m]+ \$w(vectorBoolInt, vectorBoolInt)-> vectorBoolInt} implVecTrait {[\$trait \$m]+ \$w(vectorUnsignedInt, vectorUnsignedInt)-> vectorUnsignedInt} implVecTrait {[\$trait \$m]+ \$w(vectorSignedInt, vectorSignedInt)-> vectorSignedInt} implVecTrait {[\$trait \$m]+ \$g(vectorUnsignedLongLong, vectorUnsignedLongLong)-> vectorUnsignedLongLong} implVecTrait {[\$trait \$m]+ \$g(vectorSignedLongLong, vectorSignedLongLong)-> vectorSignedLongLong} implVecTrait {[\$trait \$m]+ \$s(vectorFloat, vectorFloat)-> vectorFloat} implVecTrait {[\$trait \$m]+ \$d(vectorDouble, vectorDouble)-> vectorDouble}"

private const val IMPL_VEC_TRAIT_TEMPLATE_15: String = "implVecTrait {[\$trait \$m]+ 2c(\$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg, \$fnArg)}"

private const val S_T_L_TEMPLATE: String = "vectorSignedLongLong"

private const val S_T_L_TEMPLATE_1: String = "vectorSignedInt"

private const val S_T_L_TEMPLATE_2: String = "vectorSignedShort"

private const val S_T_L_TEMPLATE_3: String = "vectorSignedChar"

private const val S_T_L_TEMPLATE_4: String = "vectorUnsignedLongLong"

private const val S_T_L_TEMPLATE_5: String = "vectorUnsignedInt"

private const val S_T_L_TEMPLATE_6: String = "vectorUnsignedShort"

private const val S_T_L_TEMPLATE_7: String = "vectorUnsignedChar"

private const val S_T_L_TEMPLATE_8: String = "vectorFloat"

private const val S_T_L_TEMPLATE_9: String = "vectorDouble"

private const val L_T_T_TEMPLATE: String = "i64"

private const val L_T_T_TEMPLATE_1: String = "i32"

private const val L_T_T_TEMPLATE_2: String = "i16"

private const val L_T_T_TEMPLATE_3: String = "i8"

private const val L_T_T_TEMPLATE_4: String = "u64"

private const val L_T_T_TEMPLATE_5: String = "u32"

private const val L_T_T_TEMPLATE_6: String = "u16"

private const val L_T_T_TEMPLATE_7: String = "u8"

private const val L_T_T_TEMPLATE_8: String = "u64"

private const val L_T_T_TEMPLATE_9: String = "u32"

private const val L_T_T_TEMPLATE_10: String = "u16"

private const val L_T_T_TEMPLATE_11: String = "u8"

private const val L_T_T_TEMPLATE_12: String = "f32"

private const val L_T_T_TEMPLATE_13: String = "f64"

private const val T_T_L_TEMPLATE: String = "vectorSignedLongLong"

private const val T_T_L_TEMPLATE_1: String = "vectorSignedInt"

private const val T_T_L_TEMPLATE_2: String = "vectorSignedShort"

private const val T_T_L_TEMPLATE_3: String = "vectorSignedChar"

private const val T_T_L_TEMPLATE_4: String = "vectorUnsignedLongLong"

private const val T_T_L_TEMPLATE_5: String = "vectorUnsignedInt"

private const val T_T_L_TEMPLATE_6: String = "vectorUnsignedShort"

private const val T_T_L_TEMPLATE_7: String = "vectorUnsignedChar"

private const val T_T_L_TEMPLATE_8: String = "vectorFloat"

private const val T_T_L_TEMPLATE_9: String = "vectorDouble"

private const val T_T_S_TEMPLATE: String = "i64x2"

private const val T_T_S_TEMPLATE_1: String = "i32x4"

private const val T_T_S_TEMPLATE_2: String = "i16x8"

private const val T_T_S_TEMPLATE_3: String = "i8x16"

private const val T_T_S_TEMPLATE_4: String = "u64x2"

private const val T_T_S_TEMPLATE_5: String = "u32x4"

private const val T_T_S_TEMPLATE_6: String = "u16x8"

private const val T_T_S_TEMPLATE_7: String = "u8x16"

private const val T_T_S_TEMPLATE_8: String = "f32x4"

private const val T_T_S_TEMPLATE_9: String = "f64x2"

private const val T_U_TEMPLATE: String = "vectorUnsignedChar"

private const val T_U_TEMPLATE_1: String = "vectorUnsignedShort"

private const val T_U_TEMPLATE_2: String = "vectorUnsignedInt"

private const val T_U_TEMPLATE_3: String = "vectorUnsignedLongLong"

private const val T_U_TEMPLATE_4: String = "vectorUnsignedChar"

private const val T_U_TEMPLATE_5: String = "vectorUnsignedShort"

private const val T_U_TEMPLATE_6: String = "vectorUnsignedInt"

private const val T_U_TEMPLATE_7: String = "vectorUnsignedLongLong"

private const val T_U_TEMPLATE_8: String = "vectorUnsignedChar"

private const val T_U_TEMPLATE_9: String = "vectorUnsignedShort"

private const val T_U_TEMPLATE_10: String = "vectorUnsignedInt"

private const val T_U_TEMPLATE_11: String = "vectorUnsignedLongLong"

private const val T_U_TEMPLATE_12: String = "vectorUnsignedInt"

private const val T_U_TEMPLATE_13: String = "vectorUnsignedLongLong"

private const val T_B_TEMPLATE: String = "vectorBoolChar"

private const val T_B_TEMPLATE_1: String = "vectorBoolShort"

private const val T_B_TEMPLATE_2: String = "vectorBoolInt"

private const val T_B_TEMPLATE_3: String = "vectorBoolLongLong"

private const val T_B_TEMPLATE_4: String = "vectorBoolChar"

private const val T_B_TEMPLATE_5: String = "vectorBoolShort"

private const val T_B_TEMPLATE_6: String = "vectorBoolInt"

private const val T_B_TEMPLATE_7: String = "vectorBoolLongLong"

private const val T_B_TEMPLATE_8: String = "vectorBoolChar"

private const val T_B_TEMPLATE_9: String = "vectorBoolShort"

private const val T_B_TEMPLATE_10: String = "vectorBoolInt"

private const val T_B_TEMPLATE_11: String = "vectorBoolLongLong"

private const val T_B_TEMPLATE_12: String = "vectorBoolInt"

private const val T_B_TEMPLATE_13: String = "vectorBoolLongLong"

internal fun testImpl(fn: String = "", v: String = "", ty: String = "", r: String = "", call: String = "", instr: String = "", tf: String = "", clause: String = ""): String =
    when {
        clause == "+ - >" && fn.isNotEmpty() && v.isNotEmpty() && ty.isNotEmpty() && r.isNotEmpty() && call.isNotEmpty() && tf.isNotEmpty() && instr.isNotEmpty() -> arrayOf("\$fn" to fn, "\$v" to v, "\$ty" to ty, "\$r" to r, "\$call" to call).fold(TEST_IMPL_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- >" && fn.isNotEmpty() && v.isNotEmpty() && ty.isNotEmpty() && r.isNotEmpty() && call.isNotEmpty() && tf.isNotEmpty() && instr.isNotEmpty() -> arrayOf("\$fn" to fn, "\$v" to v, "\$ty" to ty, "\$r" to r, "\$call" to call).fold(TEST_IMPL_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ - >" && fn.isNotEmpty() && v.isNotEmpty() && ty.isNotEmpty() && r.isNotEmpty() && call.isNotEmpty() && instr.isNotEmpty() -> arrayOf("\$fn" to fn, "\$v" to v, "\$ty" to ty, "\$r" to r, "\$call" to call).fold(TEST_IMPL_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- >" && fn.isNotEmpty() && v.isNotEmpty() && ty.isNotEmpty() && r.isNotEmpty() && call.isNotEmpty() && instr.isNotEmpty() -> arrayOf("\$fn" to fn, "\$v" to v, "\$ty" to ty, "\$r" to r, "\$call" to call).fold(TEST_IMPL_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- > unused" && fn.isNotEmpty() && v.isNotEmpty() && ty.isNotEmpty() && r.isNotEmpty() && call.isNotEmpty() -> arrayOf("\$fn" to fn, "\$v" to v, "\$ty" to ty, "\$r" to r, "\$call" to call).fold(TEST_IMPL_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

internal fun implVecTrait(trait: String = "", m: String = "", fn: String = "", a: String = "", r: String = "", ub: String = "", sb: String = "", uh: String = "", sh: String = "", uw: String = "", sw: String = "", sf: String = "", b: String = "", ug: String = "", sg: String = "", fnArg: String = "", h: String = "", w: String = "", g: String = "", s: String = "", d: String = "", clause: String = ""): String =
    when {
        clause == "~" && trait.isNotEmpty() && m.isNotEmpty() && ub.isNotEmpty() && sb.isNotEmpty() && uh.isNotEmpty() && sh.isNotEmpty() && uw.isNotEmpty() && sw.isNotEmpty() && ug.isNotEmpty() && sg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$ub" to ub, "\$sb" to sb, "\$uh" to uh, "\$sh" to sh, "\$uw" to uw, "\$sw" to sw, "\$ug" to ug, "\$sg" to sg).fold(IMPL_VEC_TRAIT_TEMPLATE_8) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "v2" && trait.isNotEmpty() && m.isNotEmpty() && ub.isNotEmpty() && sb.isNotEmpty() && uh.isNotEmpty() && sh.isNotEmpty() && uw.isNotEmpty() && sw.isNotEmpty() && ug.isNotEmpty() && sg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$ub" to ub, "\$sb" to sb, "\$uh" to uh, "\$sh" to sh, "\$uw" to uw, "\$sw" to sw, "\$ug" to ug, "\$sg" to sg).fold(IMPL_VEC_TRAIT_TEMPLATE_10) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "v1" && trait.isNotEmpty() && m.isNotEmpty() && ub.isNotEmpty() && sb.isNotEmpty() && uh.isNotEmpty() && sh.isNotEmpty() && uw.isNotEmpty() && sw.isNotEmpty() && sf.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$ub" to ub, "\$sb" to sb, "\$uh" to uh, "\$sh" to sh, "\$uw" to uw, "\$sw" to sw, "\$sf" to sf).fold(IMPL_VEC_TRAIT_TEMPLATE_4) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ v2c" && trait.isNotEmpty() && m.isNotEmpty() && b.isNotEmpty() && h.isNotEmpty() && w.isNotEmpty() && g.isNotEmpty() && s.isNotEmpty() && d.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$b" to b, "\$h" to h, "\$w" to w, "\$g" to g, "\$s" to s, "\$d" to d).fold(IMPL_VEC_TRAIT_TEMPLATE_14) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && b.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$b" to b, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_5) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ - >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && b.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$b" to b, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_6) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "~ - >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && b.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$b" to b, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_7) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ v2b" && trait.isNotEmpty() && m.isNotEmpty() && b.isNotEmpty() && h.isNotEmpty() && w.isNotEmpty() && g.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$b" to b, "\$h" to h, "\$w" to w, "\$g" to g).fold(IMPL_VEC_TRAIT_TEMPLATE_12) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "- >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_2) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ - >" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() && r.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a, "\$r" to r).fold(IMPL_VEC_TRAIT_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+" && trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a).fold(IMPL_VEC_TRAIT_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "~" && trait.isNotEmpty() && m.isNotEmpty() && fnArg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fnArg" to fnArg).fold(IMPL_VEC_TRAIT_TEMPLATE_9) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "v2" && trait.isNotEmpty() && m.isNotEmpty() && fnArg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fnArg" to fnArg).fold(IMPL_VEC_TRAIT_TEMPLATE_11) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ v2b" && trait.isNotEmpty() && m.isNotEmpty() && fnArg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fnArg" to fnArg).fold(IMPL_VEC_TRAIT_TEMPLATE_13) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "+ v2c" && trait.isNotEmpty() && m.isNotEmpty() && fnArg.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fnArg" to fnArg).fold(IMPL_VEC_TRAIT_TEMPLATE_15) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        trait.isNotEmpty() && m.isNotEmpty() && fn.isNotEmpty() && a.isNotEmpty() -> arrayOf("\$trait" to trait, "\$m" to m, "\$fn" to fn, "\$a" to a).fold(IMPL_VEC_TRAIT_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

internal fun sTL(clause: String = ""): String =
    when {
        clause == "i64x2" -> S_T_L_TEMPLATE
        clause == "i32x4" -> S_T_L_TEMPLATE_1
        clause == "i16x8" -> S_T_L_TEMPLATE_2
        clause == "i8x16" -> S_T_L_TEMPLATE_3
        clause == "u64x2" -> S_T_L_TEMPLATE_4
        clause == "u32x4" -> S_T_L_TEMPLATE_5
        clause == "u16x8" -> S_T_L_TEMPLATE_6
        clause == "u8x16" -> S_T_L_TEMPLATE_7
        clause == "f32x4" -> S_T_L_TEMPLATE_8
        clause == "f64x2" -> S_T_L_TEMPLATE_9
        else -> ""
    }

internal fun lTT(clause: String = ""): String =
    when {
        clause == "vectorSignedLongLong" -> L_T_T_TEMPLATE
        clause == "vectorSignedInt" -> L_T_T_TEMPLATE_1
        clause == "vectorSignedShort" -> L_T_T_TEMPLATE_2
        clause == "vectorSignedChar" -> L_T_T_TEMPLATE_3
        clause == "vectorUnsignedLongLong" -> L_T_T_TEMPLATE_4
        clause == "vectorUnsignedInt" -> L_T_T_TEMPLATE_5
        clause == "vectorUnsignedShort" -> L_T_T_TEMPLATE_6
        clause == "vectorUnsignedChar" -> L_T_T_TEMPLATE_7
        clause == "vectorBoolLongLong" -> L_T_T_TEMPLATE_8
        clause == "vectorBoolInt" -> L_T_T_TEMPLATE_9
        clause == "vectorBoolShort" -> L_T_T_TEMPLATE_10
        clause == "vectorBoolChar" -> L_T_T_TEMPLATE_11
        clause == "vectorFloat" -> L_T_T_TEMPLATE_12
        clause == "vectorDouble" -> L_T_T_TEMPLATE_13
        else -> ""
    }

internal fun tTL(clause: String = ""): String =
    when {
        clause == "i64" -> T_T_L_TEMPLATE
        clause == "i32" -> T_T_L_TEMPLATE_1
        clause == "i16" -> T_T_L_TEMPLATE_2
        clause == "i8" -> T_T_L_TEMPLATE_3
        clause == "u64" -> T_T_L_TEMPLATE_4
        clause == "u32" -> T_T_L_TEMPLATE_5
        clause == "u16" -> T_T_L_TEMPLATE_6
        clause == "u8" -> T_T_L_TEMPLATE_7
        clause == "f32" -> T_T_L_TEMPLATE_8
        clause == "f64" -> T_T_L_TEMPLATE_9
        else -> ""
    }

internal fun tTS(clause: String = ""): String =
    when {
        clause == "i64" -> T_T_S_TEMPLATE
        clause == "i32" -> T_T_S_TEMPLATE_1
        clause == "i16" -> T_T_S_TEMPLATE_2
        clause == "i8" -> T_T_S_TEMPLATE_3
        clause == "u64" -> T_T_S_TEMPLATE_4
        clause == "u32" -> T_T_S_TEMPLATE_5
        clause == "u16" -> T_T_S_TEMPLATE_6
        clause == "u8" -> T_T_S_TEMPLATE_7
        clause == "f32" -> T_T_S_TEMPLATE_8
        clause == "f64" -> T_T_S_TEMPLATE_9
        else -> ""
    }

internal fun tU(clause: String = ""): String =
    when {
        clause == "vectorBoolChar" -> T_U_TEMPLATE
        clause == "vectorBoolShort" -> T_U_TEMPLATE_1
        clause == "vectorBoolInt" -> T_U_TEMPLATE_2
        clause == "vectorBoolLongLong" -> T_U_TEMPLATE_3
        clause == "vectorUnsignedChar" -> T_U_TEMPLATE_4
        clause == "vectorUnsignedShort" -> T_U_TEMPLATE_5
        clause == "vectorUnsignedInt" -> T_U_TEMPLATE_6
        clause == "vectorUnsignedLongLong" -> T_U_TEMPLATE_7
        clause == "vectorSignedChar" -> T_U_TEMPLATE_8
        clause == "vectorSignedShort" -> T_U_TEMPLATE_9
        clause == "vectorSignedInt" -> T_U_TEMPLATE_10
        clause == "vectorSignedLongLong" -> T_U_TEMPLATE_11
        clause == "vectorFloat" -> T_U_TEMPLATE_12
        clause == "vectorDouble" -> T_U_TEMPLATE_13
        else -> ""
    }

internal fun tB(clause: String = ""): String =
    when {
        clause == "vectorBoolChar" -> T_B_TEMPLATE
        clause == "vectorBoolShort" -> T_B_TEMPLATE_1
        clause == "vectorBoolInt" -> T_B_TEMPLATE_2
        clause == "vectorBoolLongLong" -> T_B_TEMPLATE_3
        clause == "vectorSignedChar" -> T_B_TEMPLATE_4
        clause == "vectorSignedShort" -> T_B_TEMPLATE_5
        clause == "vectorSignedInt" -> T_B_TEMPLATE_6
        clause == "vectorSignedLongLong" -> T_B_TEMPLATE_7
        clause == "vectorUnsignedChar" -> T_B_TEMPLATE_8
        clause == "vectorUnsignedShort" -> T_B_TEMPLATE_9
        clause == "vectorUnsignedInt" -> T_B_TEMPLATE_10
        clause == "vectorUnsignedLongLong" -> T_B_TEMPLATE_11
        clause == "vectorFloat" -> T_B_TEMPLATE_12
        clause == "vectorDouble" -> T_B_TEMPLATE_13
        else -> ""
    }
