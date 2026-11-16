// port-lint: source os/windows.rs
package io.github.kotlinmania.rustlang.core.os.windows

import io.github.kotlinmania.rustlang.core.intrinsics.CodeViewAnnotationArgs

// Platform-specific extensions to `core` for Windows platforms.

/**
 * Writes [`T.Args`](CodeViewAnnotationArgs.Args) to the PDB as an `S_ANNOTATION` record using
 * [`llvm.codeview.annotation`](https://llvm.org/docs/LangRef.html#llvm-codeview-annotation-intrinsic).
 *
 * This function has an effect only on the `msvc` environment and the LLVM backend. It is a no-op on other
 * environments and backends.
 *
 * # Examples
 *
 * To call `codeviewAnnotation`, the caller must declare a type
 * implementing [CodeViewAnnotationArgs] and pass it as the type
 * parameter to `codeviewAnnotation`. The string arguments must be
 * specified in `CodeViewAnnotationArgs.ARGS`.
 *
 * @file:feature(codeviewAnnotation, coreOs)
 * use core.os.windows::{codeviewAnnotation, CodeViewAnnotationArgs};
 *
 * struct Args;
 *
 * impl CodeViewAnnotationArgs for Args {
 *     const ARGS: &[&str] = &["Hello", "World"];
 * }
 *
 * codeviewAnnotation::<Args>();
 * ```kotlin
 */
public fun <T : CodeViewAnnotationArgs> codeviewAnnotation() {
    io.github.kotlinmania.rustlang.core.intrinsics.codeviewAnnotation<T>()
}
