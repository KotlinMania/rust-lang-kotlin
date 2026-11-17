// port-lint: source ops/try_trait.rs
package io.github.kotlinmania.rustlang.core.ops.trytrait

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow
import io.github.kotlinmania.rustlang.core.ops.controlflow.ControlFlow.Continue
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

public typealias TryType<T> = NeverShortCircuit<T>

/**
 * The `?` operator and `try ()` blocks.
 *
 * `try_ *` methods typically involve a type implementing this trait.  For
 * example, the closures passed to [Iterator.tryFold] and
 * [Iterator.tryForEach] must return such a type.
 *
 * `Try` types are typically those containing two or more categories of values,
 * some subset of which are so commonly handled via early returns that it's
 * worth providing a terse (but still visible) syntax to make that easy.
 *
 * This is most often seen for error handling with [Result] and [Option].
 * The quintessential implementation of this trait is on [ControlFlow].
 *
 * # Using `Try` in Generic Code
 *
 * `Iterator.tryFold` was stabilized to call back in Rust 1.27, but
 * this trait is much newer.  To illustrate the various associated types and
 * methods, let's implement our own version.
 *
 * As a reminder, an infallible version of a fold looks something like this:
 * ```kotlin
 * fun <A, T> simpleFold(iter: Any, accum: A, f: (A, T) -> A): A {
 *     var accum = accum
 *     for (x in iter) {
 *         accum = f(accum, x)
 *     }
 *     return accum
 * }
 * ```
 *
 * So instead of `f` returning just an `A`, we'll need it to return some other
 * type that produces an `A` in the "don't short circuit" path.  Conveniently,
 * that's also the type we need to return from the function.
 *
 * Let's add a new generic parameter `R` for that type, and bound it to the
 * output type that we want:
 * ```kotlin
 * fun <Residual, A, T, R : Try<A, Residual>> simpleTryFold1(iter: Any, accum: A, f: (A, T) -> R): R {
 *     return error("unsupported")
 * }
 * ```
 *
 * If we get through the entire iterator, we need to wrap up the accumulator
 * into the return type using [Try.fromOutput]:
 * ```kotlin
 * fun <Residual, A, T, R : Try<A, Residual>> simpleTryFold2(iter: Any, accum: A, f: (A, T) -> R, fromOutputRFn: (A) -> R): R {
 *     var accum = accum
 *     for (x in iter) {
 *         val cf = f(accum, x).branch()
 *         when (cf) {
 *             is ControlFlow.Continue -> {
 *                 val (a) = cf
 *                 accum = a
 *             }
 *             is ControlFlow.Break -> error("unsupported")
 *         }
 *     }
 *     return fromOutputRFn(accum)
 * }
 * ```
 *
 * We'll also need [FromResidual.fromResidual] to turn the residual back
 * into the original type.  But because it's a supertrait of `Try`, we don't
 * need to mention it in the bounds.  All types which implement `Try` can be
 * recreated from their corresponding residual, so we'll just call it:
 * ```kotlin
 * fun <Residual, A, T, R : Try<A, Residual>> simpleTryFold3(iter: Any, accum: A, f: (A, T) -> R, fromResidualRFn: (Any?) -> R, fromOutputRFn: (A) -> R): R {
 *     var accum = accum
 *     for (x in iter) {
 *         val cf = f(accum, x).branch()
 *         when (cf) {
 *             is ControlFlow.Continue -> {
 *                 val (a) = cf
 *                 accum = a
 *             }
 *             is ControlFlow.Break -> {
 *                 val (r) = cf
 *                 return fromResidualRFn(r)
 *             }
 *         }
 *     }
 *     return fromOutputRFn(accum)
 * }
 * ```
 *
 * But this "call `branch`, then `when` on it, and `return` if it was a
 * `Break`" is exactly what happens inside the `?` operator.  So rather than
 * do all this manually, we can just use `?` instead:
 * ```kotlin
 * fun <Residual, A, T, R : Try<A, Residual>> simpleTryFold(iter: Any, accum: A, f: (A, T) -> R, fromOutputRFn: (A) -> R): R {
 *     var accum = accum
 *     for (x in iter) {
 *         accum = f(accum, x).getOrElse { return Result.failure(it) }
 *     }
 *     return fromOutputRFn(accum)
 * }
 * ```
 */
public interface Try<T, Output, Residual> : FromResidual<Residual, T> {
    /**
     * Constructs the type from its `Output` type.
     *
     * This should be implemented consistently with the `branch` method
     * such that applying the `?` operator will get back the original value:
     * `Try.fromOutput(x).branch() - -> ControlFlow.Continue(x)`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.Try
     *
     * @feature(tryTraitV2)
     * public fun doctestEntry(): Result<Unit> {
     *     check(Result.fromOutput(3) == Result.success(3))
     *     check(Option.fromOutput(4) == 4)
     *     check(ControlFlow.fromOutput(5) == ControlFlow.Continue(5))
     *     check(Option.fromOutput(4).getOrElse { return Result.failure(it) } == 4)
     *     // This is used, for example, on the accumulator in `tryFold`:
     *     val r = ArrayDeque().tryFold(4, {
     *         error("unreachable")
     *     })
     *     check(r == 4)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun fromOutput(output: Output): T

    /**
     * Used in `?` to decide whether the operator should produce a value
     * (because this returned [ControlFlow.Continue])
     * or propagate a value back to the caller
     * (because this returned [ControlFlow.Break]).
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.ControlFlow
     * import std.ops.Try
     *
     * @feature(tryTraitV2)
     * public fun doctestEntry() {
     *     check(Result.success(3).branch() == ControlFlow.Continue(3))
     *     check(Result.failure(3).branch() == ControlFlow.Break(Result.failure(3)))
     *     check(3.branch() == ControlFlow.Continue(3))
     *     check(null.branch() == ControlFlow.Break(null))
     *     check(ControlFlow.Continue(3).branch() == ControlFlow.Continue(3))
     *     check(ControlFlow.Break(3).branch() == ControlFlow.Break(ControlFlow.Break(3)))
     * }
     * ```
     */
    public fun branch(): ControlFlow<Residual, Output>
}

/**
 * Used to specify which residuals can be converted into which [ops.Try] types.
 *
 * Every `Try` type needs to be recreatable from its own associated
 * `Residual` type, but can also have additional `FromResidual` implementations
 * to support interconversion with other `Try` types.
 */
public interface FromResidual<R, T> {
    /**
     * Constructs the type from a compatible `Residual` type.
     *
     * This should be implemented consistently with the `branch` method such
     * that applying the `?` operator will get back an equivalent residual:
     * `FromResidual.fromResidual(r).branch() - -> ControlFlow.Break(r)`.
     * (The residual is not mandated to be *identical* when interconversion is involved.)
     *
     * # Examples
     *
     * ```kotlin
     * import std.ops.ControlFlow
     * import std.ops.FromResidual
     *
     * @feature(tryTraitV2)
     * public fun doctestEntry() {
     *     check(Result.fromResidual(Result.failure(3u.toUByte())) == Result.failure(3))
     *     check(Option.fromResidual(null) == null)
     *     check(ControlFlow.fromResidual(ControlFlow.Break(5)) == ControlFlow.Break(5))
     * }
     * ```
     */
    public fun fromResidual(residual: R): T
}

// because `Result.fromResidual` has it
// not-exposed but still used via lang-item
public fun <T, Y> fromYeet(yeeted: Y): T where T : FromResidual<Yeet<Y>, T> = FromResidual.FromResidual(Yeet(yeeted))

/**
 * Allows retrieving the canonical type implementing [Try] that has this type
 * as its residual and allows it to hold an `O` as its output.
 *
 * If you think of the `Try` trait as splitting a type into its [Try.Output]
 * and [Try.Residual] components, this allows putting them back together.
 *
 * For example,
 * `Result<T, E>: Try<Output = T, Residual = Result<!, E>>`,
 * and in the other direction,
 * `<Result<!, E> as Residual<T>>.TryType = Result<T, E>`.
 */
public interface Residual<O, out TryType>

// needs to be `public` to avoid `private type` errors
// Note: force would be nice, but fails -- see #148915

/**
 * Used in `try ()` blocks so the type produced in the `?` desugaring
 * depends on the residual type `R` and the output type of the block `O`,
 * but importantly not on the contextual type the way it would be if
 * we called `<_ as FromResidual>.fromResidual(r)` directly.
 */
public fun <TryType, R : Residual<O, TryType>, O> residualIntoTryType(r: R): TryType = FromResidual.FromResidual(r)

public typealias ChangeOutputType<T : Try<T, Any?, Any?>, V, Output, Residual> = TryType

/**
 * An adapter for implementing non-try methods via the `Try` implementation.
 *
 * Conceptually the same as `Result<T, !>`, but requiring less work in trait
 * solving and inhabited-ness checking and such, by being an obvious newtype
 * and not having `From` bounds lying around.
 *
 * Not currently planned to be exposed publicly, so just `public(crate)`.
 */
public open class NeverShortCircuit<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) : Try<NeverShortCircuit<T>, T, NeverShortCircuitResidual>, FromResidual<Residual, NeverShortCircuit<T>> {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<NeverShortCircuit<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> NeverShortCircuit<T>(ptr, layout) }, { it.value })

    public override fun branch(): ControlFlow<NeverShortCircuitResidual, T> = ControlFlow.Continue(this.value)

    public companion object {
        /**
         * Wraps a unary function to produce one that wraps the output into a `NeverShortCircuit`.
         *
         * This is useful for implementing infallible functions in terms of the `try_` ones,
         * without accidentally capturing extra generic parameters in a closure.
         */
        public fun <T, A> wrapMut1(f: (A) -> T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): (A) -> NeverShortCircuit<T> = { a -> NeverShortCircuit(f(a), fieldLayout = fieldLayout) }

        public fun <T, A, B> wrapMut2(f: (A, B) -> T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): (A, B) -> NeverShortCircuit<T> = { a, b -> NeverShortCircuit(f(a, b), fieldLayout = fieldLayout) }

        public fun <T> fromOutput(x: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): NeverShortCircuit<T> = NeverShortCircuit(x, fieldLayout = fieldLayout)

        public fun <T> fromResidual(never: NeverShortCircuitResidual): NeverShortCircuit<T> = when (never) {}
    }
}

public open class NeverShortCircuitResidual(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

/**
 * Implement `FromResidual<Yeet<T>>` on your type to enable
 * `do yeet expr` syntax in functions returning your type.
 */
public open class Yeet<T>(
    public var value: T,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Yeet<T>> =
            object : TypeLayout<Yeet<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Yeet<T> =
                    Yeet(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Yeet<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Yeet<T> =
                    Yeet(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Yeet<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }
    }
}
