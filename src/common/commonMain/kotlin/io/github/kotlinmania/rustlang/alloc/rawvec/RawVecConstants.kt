package io.github.kotlinmania.rustlang.alloc.rawvec

import io.github.kotlinmania.rustlang.alloc.collections.TryReserveErrorKind.*

// SAFETY: 0 *definitely* is less than isize.Max.
public val ZERO_CAP: Cap = UsizeNoHighBit.newUnchecked(0)
