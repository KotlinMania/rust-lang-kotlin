// port-lint: source profiling.rs
package io.github.kotlinmania.rustlang.core.profiling

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Profiling markers for compiler instrumentation.

/**
 * Profiling marker for move operations.
 *
 * This function is never called at runtime. When `- Z annotate - moves` is enabled,
 * the compiler creates synthetic debug info that makes move operations appear as
 * calls to this function in profilers.
 *
 * The `SIZE` parameter encodes the size of the type being copied. It's the same as
 * `sizeOf<T>()`, and is only present for convenience.
 */
public fun compilerMove(src: ConstPtr, dst: MutPtr, size: Int) {
    error("compiler_move marks where the compiler-generated a memcpy for moves. It is never actually called.")
}

/**
 * Profiling marker for copy operations.
 *
 * This function is never called at runtime. When `- Z annotate - moves` is enabled,
 * the compiler creates synthetic debug info that makes copy operations appear as
 * calls to this function in profilers.
 *
 * The `SIZE` parameter encodes the size of the type being copied. It's the same as
 * `sizeOf<T>()`, and is only present for convenience.
 */
public fun compilerCopy(src: ConstPtr, dst: MutPtr, size: Int) {
    error("compiler_copy marks where the compiler-generated a memcpy for Copies. It is never actually called.")
}
