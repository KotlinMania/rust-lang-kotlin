// port-lint: source int/sdiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv.divti3

import io.github.kotlinmania.rustlang.core.I128

// The `@armAeabiAlias= __aeabi_idiv` attribute cannot be made to work with `intrinsics` in macros
// LLVM does not currently have a `__divmodti4` function, but GCC does

/**
 * Returns `n / d`
 */
public fun divti3(a: I128, b: I128): I128 =
    io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv
        .divti3(a, b)
