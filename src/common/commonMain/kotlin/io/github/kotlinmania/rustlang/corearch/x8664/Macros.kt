// port-lint: source x86_64/macros.rs
package io.github.kotlinmania.rustlang.corearch.x8664

// Utility macros.

internal const val CFG_TARGET_POINTER_WIDTH_64: Boolean = true

private const val STATIC_ASSERT_ROUNDING_TEMPLATE: String = "staticAssert(\$imm== 4|| \$imm== 8|| \$imm== 9|| \$imm== 10|| \$imm== 11, \"Invalid IMM value\")"

private const val STATIC_ASSERT_SAE_TEMPLATE: String = "staticAssert(\$imm== 4|| \$imm== 8, \"Invalid IMM value\")"

private const val VPS_TEMPLATE: String = "concat(\$inst1, \" [{p}]\", \$inst2)"

internal fun staticAssertRounding(imm: String = ""): String =
    STATIC_ASSERT_ROUNDING_TEMPLATE.replace("\$imm", imm)

internal fun staticAssertSae(imm: String = ""): String =
    STATIC_ASSERT_SAE_TEMPLATE.replace("\$imm", imm)

internal fun vps(inst1: String = "", inst2: String = ""): String =
    if (CFG_TARGET_POINTER_WIDTH_64) {
        arrayOf("\$inst1" to inst1, "\$inst2" to inst2).fold(VPS_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        ""
    }
