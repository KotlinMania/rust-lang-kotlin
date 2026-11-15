// port-lint: source macros.rs
package io.github.kotlinmania.rustlang.hashbrown

internal const val CFG_FEATURE_NIGHTLY: Boolean = true

private const val CFG_IF_TEMPLATE: String = "cfgIf {@ items(); \$(((\$(\$meta),*)(\$(\$it)*)), )*(()(\$(\$it2)*)), }"

private const val CFG_IF_TEMPLATE_1: String = "cfgIf {@ items(); ((\$(\$iMet),*)(\$(\$iIt)*)), \$(((\$(\$eMet),*)(\$(\$eIt)*)), )*(()()), }"

private const val CFG_IF_TEMPLATE_3: String = "cfgIf {@ apply cfg(all(\$(\$m, )* not(any(\$(\$not),*)))), \$(\$it)*} cfgIf {@ items(\$(\$not, )*\$(\$m, )*); \$(\$rest)*}"

private const val CFG_IF_TEMPLATE_4: String = "\$(\$it)*"

private const val DEFAULT_FN_TEMPLATE: String = "default\$(\$tt)*"

private const val DEFAULT_FN_TEMPLATE_1: String = "\$(\$tt)*"

internal fun cfgIf(meta: String = "", it: String = "", it2: String = "", iMet: String = "", iIt: String = "", eMet: String = "", eIt: String = "", not: String = "", m: String = "", rest: String = "", clause: String = ""): String =
    when {
        clause == "ifBranch # cfg elseBranch ifBranch # cfg" && iMet.isNotEmpty() && iIt.isNotEmpty() && eMet.isNotEmpty() && eIt.isNotEmpty() -> arrayOf("\$iMet" to iMet, "\$iIt" to iIt, "\$eMet" to eMet, "\$eIt" to eIt).fold(CFG_IF_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@items" && not.isNotEmpty() && m.isNotEmpty() && it.isNotEmpty() && rest.isNotEmpty() -> arrayOf("\$not" to not, "\$m" to m, "\$it" to it, "\$rest" to rest).fold(CFG_IF_TEMPLATE_3) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "ifBranch # cfg elseBranch" && meta.isNotEmpty() && it.isNotEmpty() && it2.isNotEmpty() -> arrayOf("\$meta" to meta, "\$it" to it, "\$it2" to it2).fold(CFG_IF_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "@apply" && m.isNotEmpty() && it.isNotEmpty() -> CFG_IF_TEMPLATE_4.replace("\$it", it)
        clause == "@items" && not.isNotEmpty() -> ""
        else -> ""
    }

internal fun defaultFn(a: String = "", tt: String = ""): String =
    if (a.isEmpty()) {
        if (CFG_FEATURE_NIGHTLY) {
            DEFAULT_FN_TEMPLATE.replace("\$tt", tt)
        } else {
            DEFAULT_FN_TEMPLATE_1.replace("\$tt", tt)
        }
    } else {
        if (CFG_FEATURE_NIGHTLY) {
            DEFAULT_FN_TEMPLATE.replace("\$tt", tt)
        } else {
            DEFAULT_FN_TEMPLATE_1.replace("\$tt", tt)
        }
    }
