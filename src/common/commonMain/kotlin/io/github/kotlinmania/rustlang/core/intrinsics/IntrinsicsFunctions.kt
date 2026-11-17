package io.github.kotlinmania.rustlang.core.intrinsics

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.ffi.valist.VaList
import io.github.kotlinmania.rustlang.core.intrinsics.bounds.FloatPrimitive
import io.github.kotlinmania.rustlang.core.intrinsics.bounds.IntegerPrimitive
import io.github.kotlinmania.rustlang.core.intrinsics.fallback.CarryingMulAdd
import io.github.kotlinmania.rustlang.core.intrinsics.fallback.CarrylessMul
import io.github.kotlinmania.rustlang.core.intrinsics.fallback.DisjointBitOr
import io.github.kotlinmania.rustlang.core.intrinsics.fallback.FunnelShift
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.f16.isSignNegative
import io.github.kotlinmania.rustlang.core.num.f16.isSignPositive
import io.github.kotlinmania.rustlang.core.panicking.panicNounwind
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.swapNonoverlapping

/**
 * The `prefetch` intrinsic is a hint to the code generator to insert a prefetch instruction
 * for the given address if supported; otherwise, it is a no-op.
 * Prefetches have no effect on the behavior of the program but can change its performance
 * characteristics.
 *
 * The `LOCALITY` argument is a temporal locality specifier ranging from (0) - no locality,
 * to (3) - extremely local keep in cache.
 *
 * This intrinsic does not have a stable counterpart.
 */
public fun prefetchReadData(data: ConstPtr, locality: Int) {
    // This operation is a no-op, unless it is overridden by the backend.
}

/**
 * The `prefetch` intrinsic is a hint to the code generator to insert a prefetch instruction
 * for the given address if supported; otherwise, it is a no-op.
 * Prefetches have no effect on the behavior of the program but can change its performance
 * characteristics.
 *
 * The `LOCALITY` argument is a temporal locality specifier ranging from (0) - no locality,
 * to (3) - extremely local keep in cache.
 *
 * This intrinsic does not have a stable counterpart.
 */
public fun prefetchWriteData(data: ConstPtr, locality: Int) {
    // This operation is a no-op, unless it is overridden by the backend.
}

/**
 * The `prefetch` intrinsic is a hint to the code generator to insert a prefetch instruction
 * for the given address if supported; otherwise, it is a no-op.
 * Prefetches have no effect on the behavior of the program but can change its performance
 * characteristics.
 *
 * The `LOCALITY` argument is a temporal locality specifier ranging from (0) - no locality,
 * to (3) - extremely local keep in cache.
 *
 * This intrinsic does not have a stable counterpart.
 */
public fun prefetchReadInstruction(data: ConstPtr, locality: Int) {
    // This operation is a no-op, unless it is overridden by the backend.
}

/**
 * The `prefetch` intrinsic is a hint to the code generator to insert a prefetch instruction
 * for the given address if supported; otherwise, it is a no-op.
 * Prefetches have no effect on the behavior of the program but can change its performance
 * characteristics.
 *
 * The `LOCALITY` argument is a temporal locality specifier ranging from (0) - no locality,
 * to (3) - extremely local keep in cache.
 *
 * This intrinsic does not have a stable counterpart.
 */
public fun prefetchWriteInstruction(data: ConstPtr, locality: Int) {
    // This operation is a no-op, unless it is overridden by the backend.
}

/**
 * Executes a breakpoint trap, for inspection by a debugger. This is allowed to resume.
 *
 * The public version of this intrinsic is [core.process.resumableBreakpoint].
 */
public fun resumableBreakpoint() {}

/**
 * Emits a call to [`llvm.codeview.annotation`](https://llvm.org/docs/LangRef.html#llvm-codeview-annotation-intrinsic)
 * which results in [CodeViewAnnotationArgs.Args] being written to the
 * PDB as an `S_ANNOTATION` record.
 *
 * Works only with targets that use PDB debuginfo and with the LLVM backend.
 * Is a no-op on other targets and backends.
 */
public fun <T : CodeViewAnnotationArgs> codeviewAnnotation() {
    // const block to catch any errors in `T.ARGS`
    run {}
}

/**
 * Informs the optimizer that a condition is always true.
 * If the condition is false, the behavior is undefined.
 *
 * No code is generated for this intrinsic, but the optimizer will try
 * to preserve it (and its condition) between passes, which may interfere
 * with optimization of surrounding code and reduce performance. It should
 * not be used if the invariant can be discovered by the optimizer on its
 * own, or if it does not enable any significant optimizations.
 *
 * The stabilized version of this intrinsic is [core.hint.assertUnchecked].
 */
public fun assume(b: Boolean) {
    if (!b) {
        // SAFETY: the caller must guarantee the argument is never `false`
        error("Entered Rust intrinsics::unreachable")
    }
}

/**
 * Hints to the compiler that current code path is cold.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [core.hint.coldPath].
 */
public fun coldPath() {}

/**
 * Hints to the compiler that branch condition is likely to be true.
 * Returns the value passed to it.
 *
 * Any use other than with `if` statements will probably not have an effect.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * This intrinsic does not have a stable counterpart.
 */
public fun likely(b: Boolean): Boolean =
    if (b) {
        true
    } else {
        false
    }

/**
 * Hints to the compiler that branch condition is likely to be false.
 * Returns the value passed to it.
 *
 * Any use other than with `if` statements will probably not have an effect.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * This intrinsic does not have a stable counterpart.
 */
public fun unlikely(b: Boolean): Boolean =
    if (b) {
        true
    } else {
        false
    }

/**
 * Returns either `trueVal` or `falseVal` depending on condition `b` with a
 * hint to the compiler that this condition is unlikely to be correctly
 * predicted by a CPU's branch predictor (e.g. a binary search).
 *
 * This is otherwise functionally equivalent to `if b ( trueVal ) else ( falseVal )`.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The public form of this intrinsic is [core.hint.selectUnpredictable].
 * However unlike the public form, the intrinsic will not drop the value that
 * is not selected.
 */
public fun <T> selectUnpredictable(b: Boolean, trueVal: T, falseVal: T): T =
    if (b) {
        trueVal
    } else {
        falseVal
    }

/**
 * Returns the square root of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `sqrt` method. For example, [`f32.sqrt`](../../std/primitive.f32.html#method.sqrt).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> sqrt(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Raises a floating-point value to an integer power.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `powi` method. For example, [`f32.powi`](../../std/primitive.f32.html#method.powi).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> powi(a: T, x: Int, dispatchTFn: (T) -> T): T = dispatchTFn(a, x)

/**
 * Returns the sine of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `sin` method. For example, [`f32.sin`](../../std/primitive.f32.html#method.sin).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> sin(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns the cosine of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `cos` method. For example, [`f32.cos`](../../std/primitive.f32.html#method.cos).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> cos(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Raises a floating-point value to a power of the same type.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `powf` method. For example, [`f32.powf`](../../std/primitive.f32.html#method.powf).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> powf(a: T, x: T, dispatchTFn: (T) -> T): T = dispatchTFn(a, x)

/**
 * Returns the exponential of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `exp` method. For example, [`f32.exp`](../../std/primitive.f32.html#method.exp).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> exp(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns 2 raised to the power of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `exp2` method. For example, [`f32.exp2`](../../std/primitive.f32.html#method.exp2).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> exp2(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns the natural logarithm of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `ln` method. For example, [`f32.ln`](../../std/primitive.f32.html#method.ln).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> log(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns the base 10 logarithm of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `log10` method. For example, [`f32.log10`](../../std/primitive.f32.html#method.log10).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> log10(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns the base 2 logarithm of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `log2` method. For example, [`f32.log2`](../../std/primitive.f32.html#method.log2).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> log2(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns `a * b + c` without rounding the intermediate result.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `mulAdd` method. For example,
 * [`f32.mulAdd`](../../std/primitive.f32.html#method.mulAdd).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> fma(a: T, b: T, c: T, dispatchTFn: (T) -> T): T = dispatchTFn(a, b, c)

/**
 * Returns `a * b + c`, non-deterministically executing either a fused multiply-add or two
 * operations with rounding of the intermediate result.
 *
 * The operation is fused if the code generator determines that target
 * instruction set has support for a fused operation, and that the fused
 * operation is more efficient than the equivalent, separate pair of mul
 * and add instructions. It is unspecified whether or not a fused operation
 * is selected, and that may depend on optimization level and context, for
 * example.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `mulAddRelaxed` method. For example,
 * [`f32.mulAddRelaxed`](../../std/primitive.f32.html#method.mulAddRelaxed).
 */
public fun <UInt, T : FloatPrimitive<T, UInt>> fmuladd(a: T, b: T, c: T): T = a * b + c

/**
 * Returns the largest integer less than or equal to a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `floor` method. For example, [`f32.floor`](../../std/primitive.f32.html#method.floor).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> floor(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns the smallest integer greater than or equal to a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `ceil` method. For example, [`f32.ceil`](../../std/primitive.f32.html#method.ceil).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> ceil(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns the integer part of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `trunc` method. For example, [`f32.trunc`](../../std/primitive.f32.html#method.trunc).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> trunc(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns the nearest integer to a floating-point value. Rounds half-way cases to the number
 * with an even least significant digit.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `roundTiesEven` method. For example,
 * [`f32.roundTiesEven`](../../std/primitive.f32.html#method.roundTiesEven).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> roundTiesEven(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Returns the nearest integer to a floating-point value. Rounds half-way cases away from
 * zero.
 *
 * The stabilized versions of this intrinsic are available on the float primitives via the
 * `round` method. For example, [`f32.round`](../../std/primitive.f32.html#method.round).
 */
public fun <UInt, T : Dispatch<T, T, UInt>> round(x: T, dispatchTFn: (T) -> T): T = dispatchTFn(x)

/**
 * Integer `min`imum, signed or unsigned depending on `T`.
 *
 * Allowed only on `uN`, `iN`, `Int`, and `Long`.
 * (Not on `Boolean` nor on `char`.)
 *
 * Stabilized as [u16.min] and [i64.min] and similar.
 */
public fun <T : IntegerPrimitive> integerMin(a: T, b: T): T =
    if ((kmCompareValues(a, b) < 0)) {
        a
    } else {
        b
    }

/**
 * Integer `max`imum, signed or unsigned depending on `T`.
 *
 * Allowed only on `uN`, `iN`, `Int`, and `Long`.
 * (Not on `Boolean` nor on `char`.)
 *
 * Stabilized as [u16.max] and [i64.max] and similar.
 */
public fun <T : IntegerPrimitive> integerMax(a: T, b: T): T =
    if ((kmCompareValues(a, b) < 0)) {
        b
    } else {
        a
    }

// the fallbacks all `assume` to tell Miri

/**
 * Combine two values which have no bits in common.
 *
 * This allows the backend to implement it as `a + b` *or* `a | b`,
 * depending which is easier to implement on a specific target.
 *
 * # Safety
 *
 * Requires that `(a & b) == 0`, or equivalently that `(a | b) == (a + b)`.
 *
 * Otherwise it's immediate UB.
 */
public fun <T : DisjointBitOr<T>> disjointBitor(a: T, b: T): T {
    // SAFETY: same preconditions as this function.
    return a.disjointBitor(b)
}

/**
 * Performs full-width multiplication and addition with a carry:
 * `multiplier * multiplicand + addend + carry`.
 *
 * This is possible without any overflow.  For `uN`:
 *    MAX * MAX + MAX + MAX
 * => (2ⁿ-1) × (2ⁿ-1) + (2ⁿ-1) + (2ⁿ-1)
 * => (2²ⁿ - 2ⁿ⁺¹ + 1) + (2ⁿ⁺¹ - 2)
 * => 2²ⁿ - 1
 *
 * For `iN`, the upper bound is MIN * MIN + MAX + MAX => 2²ⁿ⁻² + 2ⁿ - 2,
 * and the lower bound is MAX * MIN + MIN + MIN => -2²ⁿ⁻² - 2ⁿ + 2ⁿ⁺¹.
 *
 * This currently supports unsigned integers *only*, no signed ones.
 * The stabilized versions of this intrinsic are available on integers.
 */
public fun <T : CarryingMulAdd<T, U>, U> carryingMulAdd(multiplier: T, multiplicand: T, addend: T, carry: T): Pair<U, T> = multiplier.carryingMulAdd(multiplicand, addend, carry)

/**
 * Performs rotate left.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized versions of this intrinsic are available on the integer
 * primitives via the `rotateLeft` method. For example,
 * [u32.rotateLeft]
 */
public fun <T : FunnelShift<T>> rotateLeft(x: T, shift: UInt, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // Make sure to call the intrinsic for `funnelShl`, not the fallback impl.
    // SAFETY: we modulo `shift` so that the result is definitely less than the size of
    // `T` in bits.
    return uncheckedFunnelShl(x, x, shift % (sizeOf<T>(typeLayoutT = typeLayoutT).toUInt() * 8u))
}

/**
 * Performs rotate right.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized versions of this intrinsic are available on the integer
 * primitives via the `rotateRight` method. For example,
 * [u32.rotateRight]
 */
public fun <T : FunnelShift<T>> rotateRight(x: T, shift: UInt, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): T {
    // Make sure to call the intrinsic for `funnelShr`, not the fallback impl.
    // SAFETY: we modulo `shift` so that the result is definitely less than the size of
    // `T` in bits.
    return uncheckedFunnelShr(x, x, shift % (sizeOf<T>(typeLayoutT = typeLayoutT).toUInt() * 8u))
}

// Use built-in impl to avoid const-checks in the fallback body.

/**
 * Funnel Shift left.
 *
 * Concatenates `a` and `b` (with `a` in the most significant half),
 * creating an integer twice as wide. Then shift this integer left
 * by `shift`), and extract the most significant half. If `a` and `b`
 * are the same, this is equivalent to a rotate left operation.
 *
 * It is undefined behavior if `shift` is greater than or equal to the
 * bit size of `T`.
 *
 * Safe versions of this intrinsic are available on the integer primitives
 * via the `funnelShl` method. For example, [u32.funnelShl].
 */
public fun <T : FunnelShift<T>> uncheckedFunnelShl(a: T, b: T, shift: UInt): T {
    // SAFETY: caller ensures that `shift` is in-range
    return a.uncheckedFunnelShl(b, shift)
}

// Ise built-in impl to avoid const-checks in the fallback body.

/**
 * Funnel Shift right.
 *
 * Concatenates `a` and `b` (with `a` in the most significant half),
 * creating an integer twice as wide. Then shift this integer right
 * by `shift` (taken modulo the bit size of `T`), and extract the
 * least significant half. If `a` and `b` are the same, this is equivalent
 * to a rotate right operation.
 *
 * It is undefined behavior if `shift` is greater than or equal to the
 * bit size of `T`.
 *
 * Safer versions of this intrinsic are available on the integer primitives
 * via the `funnelShr` method. For example, [u32.funnelShr]
 */
public fun <T : FunnelShift<T>> uncheckedFunnelShr(a: T, b: T, shift: UInt): T {
    // SAFETY: caller ensures that `shift` is in-range
    return a.uncheckedFunnelShr(b, shift)
}

/**
 * Carryless multiply.
 *
 * Safe versions of this intrinsic are available on the integer primitives
 * via the `carrylessMul` method. For example, [u32.carrylessMul].
 */
public fun <T : CarrylessMul<T>> carrylessMul(a: T, b: T): T = a.carrylessMul(b)

/**
 * See documentation of `< * const T>.guaranteedEq` for details.
 * Returns `2` if the result is unknown.
 * Returns `1` if the pointers are guaranteed equal.
 * Returns `0` if the pointers are guaranteed inequal.
 */
public fun ptrGuaranteedCmp(ptr: ConstPtr, other: ConstPtr): UByte = (if (((ptr).rawAddress.toULong() == (other).rawAddress.toULong())) 1u else 0u).toUByte()

/**
 * Returns whether the argument's value is statically known at
 * compile-time.
 *
 * This is useful when there is a way of writing the code that will
 * be *faster* when some variables have known values, but *slower*
 * in the general case: an `if isValStaticallyKnown(var)` can be used
 * to select between these two variants. The `if` will be optimized away
 * and only the desired branch remains.
 *
 * Formally speaking, this function non-deterministically returns `true`
 * or `false`, and the caller has to ensure sound behavior for both cases.
 * In other words, the following code has *Undefined Behavior*:
 *
 * ```kotlin
 * import std.hint.unreachableUnchecked
 *
 * @feature(coreIntrinsics)
 * public fun doctestEntry() {
 *     if (!isValStaticallyKnown(0)) {
 *         run {
 *             unreachableUnchecked()
 *         }
 *     }
 * }
 * ```
 *
 * This also means that the following code's behavior is unspecified; it
 * may panic, or it may not:
 *
 * ```kotlin
 * @feature(coreIntrinsics)
 * public fun doctestEntry() {
 *     check(isValStaticallyKnown(0) == isValStaticallyKnown(0))
 * }
 * ```
 *
 * Unsafe code may not rely on `isValStaticallyKnown` returning any
 * particular value, ever. However, the compiler will generally make it
 * return `true` only if the value of the argument is actually known.
 *
 * # Type Requirements
 *
 * `T` must be either a `Boolean`, a `char`, a primitive numeric type (e.g. `Float`,
 * but not `NonZeroISize`), or any thin pointer (e.g. `* String`).
 * Any other argument types *may* cause a compiler error.
 *
 * ## Pointers
 *
 * When the input is a pointer, only the pointer itself is
 * ever considered. The pointee has no effect. Currently, these functions
 * behave identically:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * @feature(coreIntrinsics)
 * public fun doctestEntry() {
 *     fun foo(x: Int): Boolean {
 *         return isValStaticallyKnown(x)
 *     }
 *     fun bar(x: Int): Boolean {
 *         return isValStaticallyKnown(((ConstPtr(x.toLong()))).rawAddress.toInt())
 *     }
 * }
 * ```
 */
public fun <T> isValStaticallyKnown(arg: T): Boolean = false

/**
 * Non-overlapping *typed* swap of a single value.
 *
 * The codegen backends will replace this with a better implementation when
 * `T` is a simple type that can be loaded and stored as an immediate.
 *
 * The stabilized form of this intrinsic is [mem.swap].
 *
 * # Safety
 * Behavior is undefined if any of the following conditions are violated:
 *
 * * Both `x` and `y` must be [valid] for both reads and writes.
 *
 * * Both `x` and `y` must be properly aligned.
 *
 * * The region of memory beginning at `x` must *not* overlap with the region of memory
 *   beginning at `y`.
 *
 * * The memory pointed by `x` and `y` must both contain values of type `T`.
 */
public fun typedSwapNonoverlapping(x: MutPtr, y: MutPtr) {
    // SAFETY: The caller provided single non-overlapping items behind
    // pointers, so swapping them with `count: 1` is fine.
    swapNonoverlapping(x, y, 1)
}

/**
 * Returns whether we should perform some UB-checking at runtime. This eventually evaluates to
 * `cfg(ubChecks)`, but behaves different from `cfg` when mixing crates built with different
 * flags: if the crate has UB checks enabled or carries the `@rustcPreserveUbChecks`
 * attribute, evaluation is delayed until monomorphization (or until the call gets inlined into
 * a crate that does not delay evaluation further); otherwise it can happen any time.
 *
 * The common case here is a user program built with ubChecks linked against the distributed
 * sysroot which is built without ubChecks but with `@rustcPreserveUbChecks`.
 * For code that gets monomorphized in the user crate (i.e., generic functions and functions with
 * `@inline`), gating assertions on `ubChecks()` rather than `cfg(ubChecks)` means that
 * assertions are enabled whenever the *user crate* has UB checks enabled. However, if the
 * user has UB checks disabled, the checks will still get optimized out. This intrinsic is
 * primarily used by [ubChecks.assertUnsafePrecondition].
 *
 * # Consteval
 *
 * In consteval, this function currently returns `true`. This is because the value of the `ubChecks`
 * configuration can differ across crates, but we need this function to always return the same
 * value in consteval in order to avoid unsoundness.
 */
public fun ubChecks(): Boolean = false

/**
 * Returns whether we should perform some overflow-checking at runtime. This eventually evaluates to
 * `cfg(overflowChecks)`, but behaves different from `cfg` when mixing crates built with different
 * flags: if the crate has overflow checks enabled or carries the `@rustcInheritOverflowChecks`
 * attribute, evaluation is delayed until monomorphization (or until the call gets inlined into
 * a crate that does not delay evaluation further); otherwise it can happen any time.
 *
 * The common case here is a user program built with overflowChecks linked against the distributed
 * sysroot which is built without overflowChecks but with `@rustcInheritOverflowChecks`.
 * For code that gets monomorphized in the user crate (i.e., generic functions and functions with
 * `@inline`), gating assertions on `overflowChecks()` rather than `cfg(overflowChecks)` means that
 * assertions are enabled whenever the *user crate* has overflow checks enabled. However if the
 * user has overflow checks disabled, the checks will still get optimized out.
 *
 * # Consteval
 *
 * In consteval, this function currently returns `true`. This is because the value of the `overflowChecks`
 * configuration can differ across crates, but we need this function to always return the same
 * value in consteval in order to avoid unsoundness.
 */
public fun overflowChecks(): Boolean = false

/**
 * Allocates a block of memory at compile time.
 * At runtime, just returns a null pointer.
 *
 * # Safety
 *
 * - The `align` argument must be a power of two.
 *    - At compile time, a compile error occurs if this constraint is violated.
 *    - At runtime, it is not checked.
 */
public fun constAllocate(size: Int, align: Int): MutPtr {
    // const eval overrides this function, but runtime code for now just returns null pointers.
    // See <https://github.com/rust-lang/rust/issues/93935>.
    return MutPtr(0L)
}

/**
 * Deallocates a memory which allocated by `intrinsics.constAllocate` at compile time.
 * At runtime, it does nothing.
 *
 * # Safety
 *
 * - The `align` argument must be a power of two.
 *    - At compile time, a compile error occurs if this constraint is violated.
 *    - At runtime, it is not checked.
 * - If the `ptr` is created in an another const, this intrinsic doesn't deallocate it.
 * - If the `ptr` is pointing to a local variable, this intrinsic doesn't deallocate it.
 */
public fun constDeallocate(ptr: MutPtr, size: Int, align: Int) {
    // Runtime NOP
}

/**
 * Convert the allocation this pointer points to into immutable global memory.
 * The pointer must point to the beginning of a heap allocation.
 * This operation only makes sense during compile time. At runtime, it does nothing.
 */
public fun constMakeGlobal(ptr: MutPtr): ConstPtr {
    // const eval overrides this function; at runtime, it is a NOP.
    return ptr
}

// Calls to this function get inserted by an AST expansion pass, which uses the equivalent of
// `@allowInternalUnstable` to allow using `contractsInternals` functions. Const-checking
// doesn't honor `@allowInternalUnstable`, so for the const feature gate we use the user-facing
// `contracts` feature rather than the perma-unstable `contractsInternals`

/**
 * Check if the pre-condition `cond` has been met.
 *
 * By default, if `contractChecks` is enabled, this will panic with no unwind if the condition
 * returns false.
 *
 * Note that this function is a no-op during constant evaluation.
 */
public fun contractCheckRequires(cond: () -> Boolean) {
    run {
        fun runtime(cond1: () -> Boolean) {
            if (!cond1()) {
                // Emit no unwind panic in case this was a safety requirement.
                panicNounwind("failed requires check")
            }
        }
        fun compiletime(cond1: () -> Boolean) {}
        constEvalSelect(cond, ::compiletime, ::runtime)
    }
}

// Similar to `contractCheckRequires`, we need to use the user-facing
// `contracts` feature rather than the perma-unstable `contractsInternals`.
// Const-checking doesn't honor allowInternalUnstable logic used by contract expansion.

/**
 * Check if the post-condition `cond` has been met.
 *
 * By default, if `contractChecks` is enabled, this will panic with no unwind if the condition
 * returns false.
 *
 * If `cond` is `None`, then no postcondition checking is performed.
 *
 * Note that this function is a no-op during constant evaluation.
 */
public fun <Ret> contractCheckEnsures(cond: (Ret) -> Boolean?, ret: Ret): Ret =
    run {
        if (cond != null && !cond(ret)) {
            panicNounwind("failed ensures check")
        }
        ret
    }

/**
 * Returns the minimum of two `f16` values, ignoring NaN.
 *
 * This behaves like IEEE 754-2019 minimumNumber, *except* that it does not order signed
 * zeros deterministically. In particular:
 * If one of the arguments is NaN (quiet or signaling), then the other argument is returned. If
 * both arguments are NaN, returns NaN. If the inputs compare equal (such as for the case of `+ 0.0`
 * and `- 0.0`), either input may be returned non-deterministically.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [f16.min].
 */
public fun minimumNumberNszF16(x: F16, y: F16): F16 =
    if (x.isNaN() || y <= x) {
        y
    } else {
        // Either y > x or y is a NaN.
        x
    }

/**
 * Returns the minimum of two `Float` values, ignoring NaN.
 *
 * This behaves like IEEE 754-2019 minimumNumber, *except* that it does not order signed
 * zeros deterministically. In particular:
 * If one of the arguments is NaN (quiet or signaling), then the other argument is returned. If
 * both arguments are NaN, returns NaN. If the inputs compare equal (such as for the case of `+ 0.0`
 * and `- 0.0`), either input may be returned non-deterministically.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [f32.min].
 */
public fun minimumNumberNszF32(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (x.isNaN() || y <= x) {
        y
    } else {
        // Either y > x or y is a NaN.
        x
    }

/**
 * Returns the minimum of two `Double` values, ignoring NaN.
 *
 * This behaves like IEEE 754-2019 minimumNumber, *except* that it does not order signed
 * zeros deterministically. In particular:
 * If one of the arguments is NaN (quiet or signaling), then the other argument is returned. If
 * both arguments are NaN, returns NaN. If the inputs compare equal (such as for the case of `+ 0.0`
 * and `- 0.0`), either input may be returned non-deterministically.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [f64.min].
 */
public fun minimumNumberNszF64(x: Double, y: Double): Double =
    if (x.isNaN() || y <= x) {
        y
    } else {
        // Either y > x or y is a NaN.
        x
    }

/**
 * Returns the minimum of two `f128` values, ignoring NaN.
 *
 * This behaves like IEEE 754-2019 minimumNumber, *except* that it does not order signed
 * zeros deterministically. In particular:
 * If one of the arguments is NaN (quiet or signaling), then the other argument is returned. If
 * both arguments are NaN, returns NaN. If the inputs compare equal (such as for the case of `+ 0.0`
 * and `- 0.0`), either input may be returned non-deterministically.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [f128.min].
 */
public fun minimumNumberNszF128(x: F128, y: F128): F128 =
    if (x.isNaN() || y <= x) {
        y
    } else {
        // Either y > x or y is a NaN.
        x
    }

/**
 * Returns the minimum of two `f16` values, propagating NaN.
 *
 * This behaves like IEEE 754-2019 minimum. In particular:
 * If one of the arguments is NaN, then a NaN is returned using the usual NaN propagation rules.
 * For this operation, -0.0 is considered to be strictly less than +0.0.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 */
public fun minimumf16(x: F16, y: F16): F16 =
    if (x < y) {
        x
    } else if (y < x) {
        y
    } else if (x == y) {
        if (x.isSignNegative() && y.isSignPositive()) {
            x
        } else {
            y
        }
    } else {
        // At least one input is NaN. Use `+` to perform NaN propagation and quieting.
        x + y
    }

/**
 * Returns the minimum of two `Float` values, propagating NaN.
 *
 * This behaves like IEEE 754-2019 minimum. In particular:
 * If one of the arguments is NaN, then a NaN is returned using the usual NaN propagation rules.
 * For this operation, -0.0 is considered to be strictly less than +0.0.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 */
public fun minimumf32(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (x < y) {
        x
    } else if (y < x) {
        y
    } else if (x == y) {
        if (x.isSignNegative() && y.isSignPositive()) {
            x
        } else {
            y
        }
    } else {
        // At least one input is NaN. Use `+` to perform NaN propagation and quieting.
        x + y
    }

/**
 * Returns the minimum of two `Double` values, propagating NaN.
 *
 * This behaves like IEEE 754-2019 minimum. In particular:
 * If one of the arguments is NaN, then a NaN is returned using the usual NaN propagation rules.
 * For this operation, -0.0 is considered to be strictly less than +0.0.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 */
public fun minimumf64(x: Double, y: Double): Double =
    if (x < y) {
        x
    } else if (y < x) {
        y
    } else if (x == y) {
        if (x.isSignNegative() && y.isSignPositive()) {
            x
        } else {
            y
        }
    } else {
        // At least one input is NaN. Use `+` to perform NaN propagation and quieting.
        x + y
    }

/**
 * Returns the minimum of two `f128` values, propagating NaN.
 *
 * This behaves like IEEE 754-2019 minimum. In particular:
 * If one of the arguments is NaN, then a NaN is returned using the usual NaN propagation rules.
 * For this operation, -0.0 is considered to be strictly less than +0.0.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 */
public fun minimumf128(x: F128, y: F128): F128 =
    if (x < y) {
        x
    } else if (y < x) {
        y
    } else if (x == y) {
        if (x.isSignNegative() && y.isSignPositive()) {
            x
        } else {
            y
        }
    } else {
        // At least one input is NaN. Use `+` to perform NaN propagation and quieting.
        x + y
    }

/**
 * Returns the maximum of two `f16` values, ignoring NaN.
 *
 * This behaves like IEEE 754-2019 maximumNumber, *except* that it does not order signed
 * zeros deterministically. In particular:
 * If one of the arguments is NaN (quiet or signaling), then the other argument is returned. If
 * both arguments are NaN, returns NaN. If the inputs compare equal (such as for the case of `+ 0.0`
 * and `- 0.0`), either input may be returned non-deterministically.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [f16.max].
 */
public fun maximumNumberNszF16(x: F16, y: F16): F16 =
    if (x.isNaN() || y >= x) {
        y
    } else {
        // Either y < x or y is a NaN.
        x
    }

/**
 * Returns the maximum of two `Float` values, ignoring NaN.
 *
 * This behaves like IEEE 754-2019 maximumNumber, *except* that it does not order signed
 * zeros deterministically. In particular:
 * If one of the arguments is NaN (quiet or signaling), then the other argument is returned. If
 * both arguments are NaN, returns NaN. If the inputs compare equal (such as for the case of `+ 0.0`
 * and `- 0.0`), either input may be returned non-deterministically.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [f32.max].
 */
public fun maximumNumberNszF32(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (x.isNaN() || y >= x) {
        y
    } else {
        // Either y < x or y is a NaN.
        x
    }

/**
 * Returns the maximum of two `Double` values, ignoring NaN.
 *
 * This behaves like IEEE 754-2019 maximumNumber, *except* that it does not order signed
 * zeros deterministically. In particular:
 * If one of the arguments is NaN (quiet or signaling), then the other argument is returned. If
 * both arguments are NaN, returns NaN. If the inputs compare equal (such as for the case of `+ 0.0`
 * and `- 0.0`), either input may be returned non-deterministically.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [f64.max].
 */
public fun maximumNumberNszF64(x: Double, y: Double): Double =
    if (x.isNaN() || y >= x) {
        y
    } else {
        // Either y < x or y is a NaN.
        x
    }

/**
 * Returns the maximum of two `f128` values, ignoring NaN.
 *
 * This behaves like IEEE 754-2019 maximumNumber, *except* that it does not order signed
 * zeros deterministically. In particular:
 * If one of the arguments is NaN (quiet or signaling), then the other argument is returned. If
 * both arguments are NaN, returns NaN. If the inputs compare equal (such as for the case of `+ 0.0`
 * and `- 0.0`), either input may be returned non-deterministically.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 *
 * The stabilized version of this intrinsic is [f128.max].
 */
public fun maximumNumberNszF128(x: F128, y: F128): F128 =
    if (x.isNaN() || y >= x) {
        y
    } else {
        // Either y < x or y is a NaN.
        x
    }

/**
 * Returns the maximum of two `f16` values, propagating NaN.
 *
 * This behaves like IEEE 754-2019 maximum. In particular:
 * If one of the arguments is NaN, then a NaN is returned using the usual NaN propagation rules.
 * For this operation, -0.0 is considered to be strictly less than +0.0.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 */
public fun maximumf16(x: F16, y: F16): F16 =
    if (x > y) {
        x
    } else if (y > x) {
        y
    } else if (x == y) {
        if (x.isSignPositive() && y.isSignNegative()) {
            x
        } else {
            y
        }
    } else {
        x + y
    }

/**
 * Returns the maximum of two `Float` values, propagating NaN.
 *
 * This behaves like IEEE 754-2019 maximum. In particular:
 * If one of the arguments is NaN, then a NaN is returned using the usual NaN propagation rules.
 * For this operation, -0.0 is considered to be strictly less than +0.0.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 */
public fun maximumf32(x: kotlin.Float, y: kotlin.Float): kotlin.Float =
    if (x > y) {
        x
    } else if (y > x) {
        y
    } else if (x == y) {
        if (x.isSignPositive() && y.isSignNegative()) {
            x
        } else {
            y
        }
    } else {
        x + y
    }

/**
 * Returns the maximum of two `Double` values, propagating NaN.
 *
 * This behaves like IEEE 754-2019 maximum. In particular:
 * If one of the arguments is NaN, then a NaN is returned using the usual NaN propagation rules.
 * For this operation, -0.0 is considered to be strictly less than +0.0.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 */
public fun maximumf64(x: Double, y: Double): Double =
    if (x > y) {
        x
    } else if (y > x) {
        y
    } else if (x == y) {
        if (x.isSignPositive() && y.isSignNegative()) {
            x
        } else {
            y
        }
    } else {
        x + y
    }

/**
 * Returns the maximum of two `f128` values, propagating NaN.
 *
 * This behaves like IEEE 754-2019 maximum. In particular:
 * If one of the arguments is NaN, then a NaN is returned using the usual NaN propagation rules.
 * For this operation, -0.0 is considered to be strictly less than +0.0.
 *
 * Note that, unlike most intrinsics, this is safe to call;
 * it does not require an `unchecked` block.
 * Therefore, implementations must not require the user to uphold
 * any safety invariants.
 */
public fun maximumf128(x: F128, y: F128): F128 =
    if (x > y) {
        x
    } else if (y > x) {
        y
    } else if (x == y) {
        if (x.isSignPositive() && y.isSignNegative()) {
            x
        } else {
            y
        }
    } else {
        x + y
    }

// Use built-in impl to avoid const-checks in the fallback body.

/**
 * Returns the absolute value of a floating-point value.
 *
 * The stabilized versions of this intrinsic are available on the float
 * primitives via the `abs` method. For example, [f32.abs].
 */
public fun <UInt, T : FloatPrimitive<T, UInt>> fabs(x: T, fromBitsTFn: (UInt) -> T): T = fromBitsTFn(x.toBits() and T.SIGN_MASK.inv())

/**
 * Copies the sign from `y` to `x` for `f16` values.
 *
 * The stabilized version of this intrinsic is
 * [`f16.copysign`](../../std/primitive.f16.html#method.copysign)
 */
public fun copysignf16(x: F16, y: F16): F16 = F16.fromBits((x.toBits() and F16.SIGN_MASK.inv()) or (y.toBits() and F16.SIGN_MASK.toUShort()))

/**
 * Copies the sign from `y` to `x` for `Float` values.
 *
 * The stabilized version of this intrinsic is
 * [`f32.copysign`](../../std/primitive.f32.html#method.copysign)
 */
public fun copysignf32(x: kotlin.Float, y: kotlin.Float): kotlin.Float = kotlin.Float.fromBits(((x.toRawBits().toUInt() and kotlin.Float.SIGN_MASK.inv()) or (y.toRawBits().toUInt() and kotlin.Float.SIGN_MASK.toUInt())).toInt())

/**
 * Copies the sign from `y` to `x` for `Double` values.
 *
 * The stabilized version of this intrinsic is
 * [`f64.copysign`](../../std/primitive.f64.html#method.copysign)
 */
public fun copysignf64(x: Double, y: Double): Double = Double.fromBits(((x.toRawBits().toULong() and Double.SIGN_MASK.inv()) or (y.toRawBits().toULong() and Double.SIGN_MASK.toULong())).toLong())

/**
 * Copies the sign from `y` to `x` for `f128` values.
 *
 * The stabilized version of this intrinsic is
 * [`f128.copysign`](../../std/primitive.f128.html#method.copysign)
 */
public fun copysignf128(x: F128, y: F128): F128 = F128.fromBits((x.toBits() and F128.SIGN_MASK.inv()) or (y.toBits() and F128.SIGN_MASK))

/**
 * Duplicates a variable argument list. The returned list is initially at the same position as
 * the one in `src`, but can be advanced independently.
 *
 * Codegen backends should not have custom behavior for this intrinsic, they should always use
 * this fallback implementation. This intrinsic *does not* map to the LLVM `vaCopy` intrinsic.
 *
 * This intrinsic exists only as a hook for Miri and constant evaluation, and is used to detect UB
 * when a variable argument list is used incorrectly.
 */
public fun vaCopy(src: VaList): VaList {
    // This fallback body exploits the fact that our codegen backends all just use
    // a plain memcpy to duplicate VaList. This assumption is wrong for Miri.
    check(!false) { "fallback body is incorrect under Miri" }
    return src.duplicate()
}

/**
 * Destroy the variable argument list `ap` after initialization with `vaStart` (part of the
 * desugaring of `...`) or `vaCopy`.
 *
 * Code generation backends should not provide a custom implementation for this intrinsic. This
 * intrinsic *does not* map to the LLVM `vaEnd` intrinsic.
 *
 * This function is a no-op on all current targets, but used as a hook for const evaluation to
 * detect UB when a variable argument list is used incorrectly.
 *
 * # Safety
 *
 * `ap` must not be used to access variable arguments after this call.
 */
public fun vaEnd(ap: VaList) {
    // deliberately does nothing
}

/**
 * Returns the return address of the caller function (after inlining) in a best-effort manner or a null pointer if it is not supported on the current backend.
 * Returning an accurate value is a quality-of-implementation concern, but no hard guarantees are
 * made about the return value: formally, the intrinsic non-deterministically returns
 * an arbitrary pointer without provenance.
 *
 * Note that unlike most intrinsics, this is safe to call. This is because it only finds the return address of the immediate caller, which is guaranteed to be possible.
 * Other forms of the corresponding gcc or llvm intrinsic (which can have wildly unpredictable results or even crash at runtime) are not exposed.
 */
public fun returnAddress(): ConstPtr = ConstPtr(0L)
