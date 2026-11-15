package io.github.kotlinmania.rustlang.core.future

import io.github.kotlinmania.rustlang.ptr.nonnull.NonNull

/**
 * This type is needed because:
 *
 * a) Coroutines cannot implement `for<> Coroutine<Context<>>`, so we need to pass
 *    a raw pointer (see <https://github.com/rust-lang/rust/issues/68923>).
 * b) Raw pointers and `NonNull` aren't `Send` or `Sync`, so that would make every single future
 *    non-Send/Sync as well, and we don't want that.
 *
 * It also simplifies the HIR lowering of `.await`.
 */
public open class ResumeTy internal constructor(
    valueCtorArg: NonNull,
) {
    public var value: NonNull = valueCtorArg
        internal set

    public var field0: NonNull
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ResumeTy = ResumeTy(value)

    public companion object {
        public operator fun invoke(
            value: NonNull,
        ): ResumeTy = ResumeTy(
            valueCtorArg = value,
        )
    }
}
