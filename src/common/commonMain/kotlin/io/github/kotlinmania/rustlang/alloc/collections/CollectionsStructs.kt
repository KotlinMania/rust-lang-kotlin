package io.github.kotlinmania.rustlang.alloc.collections

import io.github.kotlinmania.rustlang.core.result.Result

/**
 * The error type for `tryReserve` methods.
 */
public open class TryReserveError(
    public var kind: TryReserveErrorKind,
) {
    public open fun clone(): TryReserveError = TryReserveError(kind)

    /**
     * Details about the allocation that caused the error
     */
    public open fun kind(): TryReserveErrorKind = this.kind.clone()

    public open fun fmt(fmt: StringBuilder): Result<Unit, Error> {
        fmt.append("memory allocation failed")
        val reason =
            when (this.kind) {
                TryReserveErrorKind.CapacityOverflow -> {
                    " because the computed capacity exceeded the collection's maximum"
                }
                is TryReserveErrorKind.AllocError -> {
                    " because the memory allocator returned an error"
                }
            }
        return run { fmt.append(reason); Result.Ok(Unit) }
    }

    public companion object {
        public fun from(kind: TryReserveErrorKind): TryReserveError = TryReserveError(kind = kind)
    }
}
