// port-lint: source int/sdiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv.divsi3

// The `@armAeabiAlias= __aeabi_idiv` attribute cannot be made to work with `intrinsics` in macros
// LLVM does not currently have a `__divmodti4` function, but GCC does

/**
 * Returns `n / d`
 */
public fun divsi3(a: Int, b: Int): Int =
    io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv
        .divsi3(a, b)
