// port-lint: source wasm.rs
package io.github.kotlinmania.rustlang.unwind.wasm

import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.types.UnwindException
import io.github.kotlinmania.rustlang.types.UnwindReasonCode

// A shim for libunwind implemented in terms of the native wasm `throw` instruction.

public fun unwindDeleteException(exception: MutPtr) {
    when (val matchVal = (ObjectSlotLayout<UnwindException>()).readNative((exception).rawAddress).exceptionCleanup) {
        null -> {}
        else -> {
            val exceptionCleanup = matchVal
            exceptionCleanup(URC_FOREIGN_EXCEPTION_CAUGHT, exception)
        }
    }
}

public fun unwindRaiseException(exception: MutPtr): UnwindReasonCode {
    // It's important that this intrinsic is defined here rather than in `core`. Since it
    // unwinds, invoking it from Rust code compiled with `- C panic = unwind` immediately
    // forces `panicUnwind` as the required panic runtime.
    //
    // We ship unwinding `core` on Emscripten, so making this intrinsic part of `core` would
    // prevent linking precompiled `core` into `- C panic = abort` binaries. Unlike `core`,
    // this particular module is never precompiled with `- C panic = unwind` because it's only
    // used for bare-metal targets, so an error can only arise if the user both manually
    // recompiles `std` with `- C panic = unwind` and manually compiles the binary crate with
    // `- C panic = abort`, which we don't care to support.
    //
    // See https://github.com/rust-lang/rust/issues/148246.

    /**
     * LLVM lowers this intrinsic to the `throw` instruction.
     */
    fun wasmThrow(tag: Int, ptr: MutPtr): Nothing = run { val ptr = kmAllocZeroed(maxOf(1L, (ObjectSlotLayout<Nothing>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<Nothing>()).alignBytes)); val res = (ObjectSlotLayout<Nothing>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (ObjectSlotLayout<Nothing>()).sizeBytes), maxOf(1L, (ObjectSlotLayout<Nothing>()).alignBytes)); res }
    // The wasm `throw` instruction takes a "tag", which differentiates certain types of
    // exceptions from others. LLVM currently just identifies these via integers, with 0
    // corresponding to C++ exceptions and 1 to C setjmp()/longjmp(). Ideally, we'd be able
    // to choose something unique for Rust, but for now, we pretend to be C++ and implement
    // the Itanium exception-handling ABI.
    // corresponds with llvm.WebAssembly.Tag.CppException
    // in llvm-project/llvm/include/llvm/CodeGen/WasmEHFuncInfo.h
    val CPP_EXCEPTION_TAG: Int = 0
    return wasmThrow(CPP_EXCEPTION_TAG, exception)
}
