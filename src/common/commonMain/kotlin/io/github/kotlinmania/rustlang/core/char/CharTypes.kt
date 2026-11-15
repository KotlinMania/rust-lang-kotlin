package io.github.kotlinmania.rustlang.core.char

/**
 * The case of a cased character,
 * as returned by [char.case].
 *
 * Titlecase characters conceptually are composed of an uppercase portion
 * followed by a lowercase portion.
 * The variant discriminants represent this:
 * the most significant bit represents whether the case
 * conceptually starts as uppercase, while the least significant bit
 * represents whether it conceptually ends as uppercase.
 */
public sealed class CharCase(
    public val value: Int,
) {
    /**
     * Lowercase. Corresponds to the `Lowercase` Unicode property.
     */
    public data object Lower : CharCase(0x0)

    /**
     * Titlecase. Corresponds to the `TitlecaseLetter` Unicode general category.
     */
    public data object Title : CharCase(0x2)

    /**
     * Uppercase. Corresponds to the `Uppercase` Unicode property.
     */
    public data object Upper : CharCase(0x3)

    public fun rustEquals(other: CharCase): Boolean = this == other
}
