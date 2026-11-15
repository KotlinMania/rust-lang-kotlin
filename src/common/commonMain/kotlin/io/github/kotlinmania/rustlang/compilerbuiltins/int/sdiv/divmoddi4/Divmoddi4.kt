// port-lint: source int/sdiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv.divmoddi4

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// The `@armAeabiAlias= __aeabi_idiv` attribute cannot be made to work with `intrinsics` in macros
// LLVM does not currently have a `__divmodti4` function, but GCC does

/**
 * Returns `n / d` and sets `* rem = n % d`
 */
public fun divmoddi4(a: Long, b: Long, rem: RustMutableReference<Long>): Long =
    io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv
        .divmoddi4(a, b, rem)
