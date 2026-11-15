// port-lint: source int/sdiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv.moddi3

// The `@armAeabiAlias= __aeabi_idiv` attribute cannot be made to work with `intrinsics` in macros
// LLVM does not currently have a `__divmodti4` function, but GCC does

/**
 * Returns `n % d`
 */
public fun moddi3(a: Long, b: Long): Long = io.github.kotlinmania.rustlang.int.sdiv.moddi3(a, b)
