package io.github.kotlinmania.rustlang.core.intrinsics

/**
 * A type for atomic ordering parameters for intrinsics. This is a separate type from
 * `atomic.Ordering` so that we can make it `ConstParamTy` and fix the values used here without a
 * risk of leaking that to stable code.
 */
public sealed class AtomicOrdering(
    public val value: Int,
) {
    // These values must match the compiler's `AtomicOrdering` defined in
    // `rustcMiddle / src / ty / consts / int.rs`!
    public data object Relaxed : AtomicOrdering(0)
    public data object Release : AtomicOrdering(1)
    public data object Acquire : AtomicOrdering(2)
    public data object AcqRel : AtomicOrdering(3)
    public data object SeqCst : AtomicOrdering(4)

    public fun rustEquals(other: AtomicOrdering): Boolean = this == other
}

/**
 * A container for the string arguments passed to [codeviewAnnotation].
 */
public interface CodeViewAnnotationArgs {
    /**
     * The string arguments to `codeviewAnnotation`.
     */
    public val ARGS: List<String>
}
