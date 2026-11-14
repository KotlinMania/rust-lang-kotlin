package io.github.kotlinmania.rustlang.libm.math.generic

// Generic implementations that are shared by multiple types.
//
// Implementation and usage notes:
//
// * Generic functions are marked `@inline` because, even though generic functions are
//   typically inlined, we seem to occasionally run into exceptions.
// * Tests usually live wherever the functions are consumed (e.g. `src / ceil`) so they can be
//   reused to test arch-specific implementations.

