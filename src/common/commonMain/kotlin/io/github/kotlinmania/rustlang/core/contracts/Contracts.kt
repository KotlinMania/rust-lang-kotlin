// port-lint: source contracts.rs
package io.github.kotlinmania.rustlang.core.contracts

// Unstable module containing the unstable contracts lang items and attribute macros.

// Similar to `contractCheckRequires`, we need to use the user-facing
// `contracts` feature rather than the perma-unstable `contractsInternals`.
// Const-checking doesn't honor allowInternalUnstable logic used by contract expansion.

/**
 * This is an identity function used as part of the desugaring of the `@ensures` attribute.
 *
 * This is an existing Note to allow users to omit the type of the return value in their ensures
 * attribute.
 *
 * Ideally, rustc should be able to generate the type annotation.
 * The existing lowering logic makes it rather hard to add the explicit type annotation,
 * while the function call is fairly straight forward.
 */
public fun <Ret> buildCheckEnsures(cond: (Ret) -> Boolean): (Ret) -> Boolean = cond
