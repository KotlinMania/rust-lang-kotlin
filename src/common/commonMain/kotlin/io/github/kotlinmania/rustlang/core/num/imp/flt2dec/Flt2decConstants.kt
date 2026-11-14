package io.github.kotlinmania.rustlang.core.num.imp.flt2dec

/**
 * The minimum size of buffer necessary for the shortest mode.
 *
 * It is a bit non-trivial to derive, but this is one plus the maximal number of
 * significant decimal digits from formatting algorithms with the shortest result.
 * The exact formula is `ceil(# bits in mantissa * log10 2 + 1)`.
 */
public val MAX_SIG_DIGITS: Int = 17
