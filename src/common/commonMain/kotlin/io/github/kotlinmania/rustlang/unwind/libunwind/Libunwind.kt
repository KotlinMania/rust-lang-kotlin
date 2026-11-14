// port-lint: source libunwind.rs
package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode

public typealias UnwindPtr = ConstPtr

public open class UnwindContext(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

// Note: The `@link` attributes on `extern "C"` block marks those symbols declared in
// the block are reexported in dylib build of std. This is needed when build rustc with
// feature `llvm - libunwind`, as no other cdylib will provided those _Unwind_* symbols.
// However the `link` attribute is duplicated multiple times and does not just export symbol,
// a better way to manually export symbol would be another attribute like `@export`.
// See the logic in function rustcCodegenSsa.src.back.exportedSymbols, module
// rustcCodegenSsa.src.back.symbolExport, rustcMiddle.middle.exportedSymbols
// and RFC 2841
public expect fun unwindResume(exception: MutPtr): Nothing

public expect fun unwindDeleteException(exception: MutPtr)

public expect fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr

public expect fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr

public expect fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr

public expect fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr

// 32-bit ARM Apple (except for watchOS armv7k specifically) uses SjLj
public expect fun unwindRaiseException(exception: MutPtr): UnwindReasonCode
