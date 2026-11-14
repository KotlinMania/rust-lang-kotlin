// port-lint: source num/shells/legacy_int_modules.rs
package io.github.kotlinmania.rustlang.core.num.shells.legacyintmodules

private const val LEGACY_INT_MODULE_TEMPLATE: String = "legacyIntModule(\$t, );"

private const val LEGACY_INT_MODULE_TEMPLATE_1: String = "public mod \$t {public const Min: \$t= \$t.Min; public const Max: \$t= \$t.Max; }"

internal fun legacyIntModule(t: String = "", attr: String = "", clause: String = ""): String =
    when {
        clause == "#" && t.isNotEmpty() && attr.isNotEmpty() -> LEGACY_INT_MODULE_TEMPLATE_1.replace("\$t", t)
        t.isNotEmpty() -> LEGACY_INT_MODULE_TEMPLATE.replace("\$t", t)
        else -> ""
    }
