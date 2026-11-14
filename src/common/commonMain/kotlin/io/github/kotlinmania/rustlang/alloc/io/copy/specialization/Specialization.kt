// port-lint: source io/copy/specialization.rs
package io.github.kotlinmania.rustlang.alloc.io.copy.specialization

import io.github.kotlinmania.rustlang.alloc.io.copy.CopyState
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.error.Result

// Provides specialization for `io.copy`.

internal interface SpecCopyInner {
    /**
     * The implementation of `io.copy` that can rely on platform specific specialization
     * provided by `libstd`.
     */
    public fun <W : Write> copy(writer: W): Result<CopyState>
}

public fun <R : Read<R>, W : Write> R.copy(writer: W): Result<CopyState> = Result.Ok(CopyState.Fallback(0))

public fun <R : SpecCopy<R>, W : Write> R.copy(writer: W, copyRFn: (R, W) -> Result<CopyState>): Result<CopyState> = copyRFn(this, writer)

public interface SpecCopy<T> : Read<T> {
    /**
     * Attempt to copy from this reader to the provided writer using a specialized
     * process.
     *
     * Note that this function does _not_ take `self` as a parameter, and instead
     * is passed a generic `Read` type `R`.
     * This allows the `Self` type to provide specialized implementations for
     * any combination of `Read` and `Write` types.
     * However, in practice `Self` and types wrapping `Self` will be passed as
     * the `reader` argument.
     *
     * As of time of writing, `R`, `Take<R>`, and `BufReader<R>` will
     * forward to `R` for a specialized copy implementation.
     */
    public fun <R : Read<R>, W : Write> copy(reader: R, writer: W): Result<CopyState>
}

public fun <T : SpecCopy<T>, R : Read<R>, W : Write> specCopyCopyTakeT(reader: R, writer: W, copyTFn: (R, W) -> Result<CopyState>): Result<CopyState> = copyTFn(reader, writer)

public fun <T : SpecCopy<T>, R : Read<R>, W : Write> specCopyCopyBufReaderT(reader: R, writer: W, copyTFn: (R, W) -> Result<CopyState>): Result<CopyState> = copyTFn(reader, writer)
