// port-lint: source int/sdiv.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.sdiv.divmodti4

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// The `@armAeabiAlias= __aeabi_idiv` attribute cannot be made to work with `intrinsics` in macros
// LLVM does not currently have a `__divmodti4` function, but GCC does

/**
 * Returns `n / d` and sets `* rem = n % d`
 */
public fun divmodti4(a: I128, b: I128, rem: RustMutableReference<I128>): I128 = io.github.kotlinmania.rustlang.int.sdiv.divmodti4(a, b, rem)
