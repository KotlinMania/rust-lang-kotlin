// port-lint: source x86/macros.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Utility macros.

internal const val CFG_TARGET_POINTER_WIDTH_64: Boolean = true

private const val STATIC_ASSERT_ROUND_MODE_TEMPLATE: String = "staticAssert(\$imm>= 0& \$imm< 5, \"Invalid IMM value\")"

private const val STATIC_ASSERT_ROUNDING_TEMPLATE: String = "staticAssert(\$imm== 4|| \$imm== 8|| \$imm== 9|| \$imm== 10|| \$imm== 11, \"Invalid IMM value\")"

private const val STATIC_ASSERT_SAE_TEMPLATE: String = "staticAssert(\$imm== 4|| \$imm== 8, \"Invalid IMM value\")"

private const val STATIC_ASSERT_EXTENDED_ROUNDING_TEMPLATE: String = "staticAssert((\$imm& 7)< 5&&(\$imm&! 15)== 0, \"Invalid IMM value\")"

private const val STATIC_ASSERT_MANTISSAS_SAE_TEMPLATE: String = "staticAssert(\$imm== 4|| \$imm== 8|| \$imm== 12, \"Invalid IMM value\")"

private const val STATIC_ASSERT_IMM8_SCALE_TEMPLATE: String = "staticAssert(\$imm== 1|| \$imm== 2|| \$imm== 4|| \$imm== 8, \"Invalid SCALE value\")"

private const val VPL_TEMPLATE: String = "concat(\$inst, \", [{p}]\")"

private const val VPS_TEMPLATE: String = "concat(\$inst1, \" [{p}]\", \$inst2)"

internal fun staticAssertRoundMode(imm: String = ""): String =
    STATIC_ASSERT_ROUND_MODE_TEMPLATE.replace("\$imm", imm)

internal fun staticAssertRounding(imm: String = ""): String =
    STATIC_ASSERT_ROUNDING_TEMPLATE.replace("\$imm", imm)

internal fun staticAssertSae(imm: String = ""): String =
    STATIC_ASSERT_SAE_TEMPLATE.replace("\$imm", imm)

internal fun staticAssertExtendedRounding(imm: String = ""): String =
    STATIC_ASSERT_EXTENDED_ROUNDING_TEMPLATE.replace("\$imm", imm)

internal fun staticAssertMantissasSae(imm: String = ""): String =
    STATIC_ASSERT_MANTISSAS_SAE_TEMPLATE.replace("\$imm", imm)

internal fun staticAssertImm8Scale(imm: String = ""): String =
    STATIC_ASSERT_IMM8_SCALE_TEMPLATE.replace("\$imm", imm)

internal fun vpl(inst: String = ""): String =
    if (CFG_TARGET_POINTER_WIDTH_64) {
        VPL_TEMPLATE.replace("\$inst", inst)
    } else {
        ""
    }

internal fun vps(inst1: String = "", inst2: String = ""): String =
    if (CFG_TARGET_POINTER_WIDTH_64) {
        arrayOf("\$inst1" to inst1, "\$inst2" to inst2).fold(VPS_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        ""
    }
