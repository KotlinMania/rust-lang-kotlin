package io.github.kotlinmania.rustlang.core.intrinsics

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.intrinsics.bounds.FloatPrimitive
import io.github.kotlinmania.rustlang.core.num.imp.libm.fmaf
import io.github.kotlinmania.rustlang.core.num.imp.libm.fmaf128
import io.github.kotlinmania.rustlang.core.num.imp.libm.fmaf16
import io.github.kotlinmania.rustlang.core.num.imp.libm.likelyavailable.pow
import io.github.kotlinmania.rustlang.core.num.imp.libm.maybeavailable.powf128
import io.github.kotlinmania.rustlang.core.num.imp.libm.powidf2
import io.github.kotlinmania.rustlang.core.num.imp.libm.powisf2
import io.github.kotlinmania.rustlang.core.num.imp.libm.powitf2
import io.github.kotlinmania.rustlang.core.num.imp.libm.sqrtf
import io.github.kotlinmania.rustlang.core.num.imp.libm.sqrtf128
import io.github.kotlinmania.rustlang.core.num.imp.libm.sqrtf16

public typealias T = F16

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

public interface Dispatch<T, SelfType, UInt> : FloatPrimitive<SelfType, UInt> {
    public fun dispatch(x: T): T
}

public typealias T = F16

public fun dispatchF16(x: T): T = sqrtf16(x)

public fun dispatchFloat(x: T): T = sqrtf(x)

public fun dispatchDouble(x: T): T = io.github.kotlinmania.rustlang.core.num.imp.libm.sqrt(x)

public fun dispatchF128(x: T): T = sqrtf128(x)

public fun dispatchF16(a: T, x: Int): T = F16.fromFloat(kmPowif32(a.toFloat(), x))

public fun dispatchFloat(a: T, x: Int): T = powisf2(a, x)

public fun dispatchDouble(a: T, x: Int): T = powidf2(a, x)

public fun dispatchF128(a: T, x: Int): T = powitf2(a, x)

public fun dispatchF16(a: T, x: T): T = F16.fromFloat(kmPowf32(a.toFloat(), x.toFloat()))

public fun dispatchFloat(a: T, x: T): T = io.github.kotlinmania.rustlang.core.num.imp.libm.likelyavailable.powf(a, x)

public fun dispatchDouble(a: T, x: T): T = pow(a, x)

public fun dispatchF128(a: T, x: T): T = powf128(a, x)

public fun dispatchF16(a: T, b: T, c: T): T = fmaf16(a, b, c)

public fun dispatchFloat(a: T, b: T, c: T): T = fmaf(a, b, c)

public fun dispatchDouble(a: T, b: T, c: T): T = io.github.kotlinmania.rustlang.core.num.imp.libm.fma(a, b, c)

public fun dispatchF128(a: T, b: T, c: T): T = fmaf128(a, b, c)
