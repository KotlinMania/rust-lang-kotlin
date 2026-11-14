// port-lint: source cmp/bytewise.rs
package io.github.kotlinmania.rustlang.core.cmp.bytewise

/**
 * Types where `==` & `!=` are equivalent to comparing their underlying bytes.
 *
 * Importantly, this means no floating-point types, as those have different
 * byte representations (like `- 0` and `+ 0`) which compare as the same.
 * Since byte arrays are `Eq`, that implies that these types are probably also
 * `Eq`, but that's not technically required to use this trait.
 *
 * `Rhs` is *de facto* always `Self`, but the separate parameter is important
 * to avoid the `specializing implementation repeats parameter` error when consuming this.
 *
 * # Safety
 *
 * - `Self` and `Rhs` have no padding.
 * - `Self` and `Rhs` have the same layout (size and alignment).
 * - Neither `Self` nor `Rhs` have provenance, so integer comparisons are correct.
 * - `<Self as PartialEq<Rhs>>.{ eq, ne }` are equivalent to comparing the bytes.
 */
public interface BytewiseEq<Rhs>

// SAFETY: All the ordinary integer types have no padding, and are not pointers.
// SAFETY: These have *niches*, but no *padding* and no *provenance*,
// so we can compare them directly.
// SAFETY: Similarly, the `NonZero` type has a niche, but no undef and no pointers,
// and they compare like their underlying numeric type.
// SAFETY: The `NonZero` type has the "null" optimization guaranteed, and thus
// are also safe to equality-compare bitwise inside an `Option`.
// The way `PartialOrd` is defined for `Option` means that this wouldn't work
// for `<` or `>` on the signed types, but since we only do `==` it's fine.
// Frustratingly, this can't be made const-generic as it gets
// error: specializing impl repeats parameter `N`
// so just do it for a couple of plausibly-common ones.
