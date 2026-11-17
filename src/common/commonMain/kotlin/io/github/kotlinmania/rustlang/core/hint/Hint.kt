// port-lint: source hint.rs
package io.github.kotlinmania.rustlang.core.hint

import io.github.kotlinmania.rustlang.core.X8664
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

// Hints to compiler that affects how code should be emitted or optimized.
//
// Hints may be compile time or runtime.

/**
 * Informs the compiler that the site which is calling this function is not
 * reachable, possibly enabling further optimizations.
 *
 * # Safety
 *
 * Reaching this function is *Undefined Behavior*.
 *
 * As the compiler assumes that all forms of Undefined Behavior can never
 * happen, it will eliminate all branches in the surrounding code that it can
 * determine will invariably lead to a call to `unreachableUnchecked()`.
 *
 * If the assumptions embedded in using this function turn out to be wrong -
 * that is, if the site which is calling `unreachableUnchecked()` is actually
 * reachable at runtime - the compiler may have generated nonsensical machine
 * instructions for this situation, including in seemingly unrelated code,
 * causing difficult-to-debug problems.
 *
 * Use this function sparingly. Consider using the [unreachable] macro,
 * which may prevent some optimizations but will safely panic in case it is
 * actually reached at runtime. Benchmark your code to find out if using
 * `unreachableUnchecked()` comes with a performance benefit.
 *
 * # Examples
 *
 * `unreachableUnchecked()` can be used in situations where the compiler
 * can't prove invariants that were previously established. Such situations
 * have a higher chance of occurring if those invariants are upheld by
 * external code that the compiler can't analyze.
 * ```kotlin
 * fun prepareInputs(divisors: List<UInt>) {
 *     // Note to future-self when making changes: The invariant established
 *     // here is NOT checked in `doComputation()`; if this changes, you HAVE
 *     // to change `doComputation()`.
 *     divisors.retain({ divisor -> divisor != 0 })
 * }
 * / **
 *  * # Safety
 *  * All elements of `divisor` must be non-zero.
 *  * /
 * fun doComputation(i: UInt, divisors: List<UInt>): UInt {
 *     return divisors.fold(i, { acc, divisor ->
 *         // Convince the compiler that a division by zero can't happen here
 *         // and a check is not needed below.
 *         if (divisor == 0) {
 *             // Safety: `divisor` can't be zero because of `prepareInputs`,
 *             // but the compiler does not know about this. We *promise*
 *             // that we always call `prepareInputs`.
 *             unreachableUnchecked()
 *         }
 *         // The compiler would normally introduce a check here that prevents
 *         // a division by zero. However, if `divisor` was zero, the branch
 *         // above would reach what we explicitly marked as unreachable.
 *         // The compiler concludes that `divisor` can't be zero at this point
 *         // and removes the - now proven useless - check.
 *         acc / divisor
 *     })
 * }
 * var divisors = mutableListOf(2, 0, 4)
 * prepareInputs(divisors)
 * val result = doComputation(100u, divisors)
 * check(result == 12u)
 * ```
 *
 * While using `unreachableUnchecked()` is perfectly sound in the following
 * example, as the compiler is able to prove that a division by zero is not
 * possible, benchmarking reveals that `unreachableUnchecked()` provides
 * no benefit over using [unreachable], while the latter does not introduce
 * the possibility of Undefined Behavior.
 *
 * ```kotlin
 * import std.hint.unreachableUnchecked
 *
 * fun div1(a: UInt, b: UInt): UInt {
 *     // `b.saturatingAdd(1)` is always positive (not zero),
 *     // hence `checkedDiv` will never return `None`.
 *     // Therefore, the else branch is unreachable.
 *     return (a.checkedDiv(b.saturatingAdd(1u)) ?: unreachableUnchecked())
 * }
 * check(div1(7u, 0u) == 7u)
 * check(div1(9u, 1u) == 4u)
 * check(div1(11u, UInt.MAX_VALUE) == 0u)
 * ```
 */
public fun unreachableUnchecked(): Nothing {
    Unit
    // SAFETY: the safety contract for `intrinsics.unreachable` must
    // be upheld by the caller.
    error("Entered Rust intrinsics::unreachable")
}

/**
 * Makes a *soundness* promise to the compiler that `cond` holds.
 *
 * This may allow the optimizer to simplify things, but it might also make the generated code
 * slower. Either way, calling it will most likely make compilation take longer.
 *
 * You may know this from other places as
 * [`llvm.assume`](https://llvm.org/docs/LangRef.html#llvm-assume-intrinsic) or, in C,
 * [`builtinAssume`](https://clang.llvm.org/docs/LanguageExtensions.html#builtin-assume).
 *
 * This promotes a correctness requirement to a soundness requirement. Don't do that without
 * very good reason.
 *
 * # Usage
 *
 * This is a situational tool for micro-optimization, and is allowed to do nothing. Any use
 * should come with a repeatable benchmark to show the value, with the expectation to drop it
 * later should the optimizer get smarter and no longer need it.
 *
 * The more complicated the condition, the less likely this is to be useful. For example,
 * `assertUnchecked(foo.isSorted())` is a complex enough value that the compiler is unlikely
 * to be able to take advantage of it.
 *
 * There's also no need to `assertUnchecked` basic properties of things.  For example, the
 * compiler already knows the range of `countOnes`, so there is no benefit to
 * `val n = UInt.countOnes(x); assertUnchecked(n <= UInt.BITS);`.
 *
 * `assertUnchecked` is logically equivalent to `ifBranch cond ( unreachableUnchecked(); )`. If
 * ever you are tempted to write `assertUnchecked(false)`, you should instead use
 * [unreachableUnchecked] directly.
 *
 * # Safety
 *
 * `cond` must be `true`. It is immediate UB to call this with `false`.
 *
 * # Example
 *
 * ```kotlin
 * import core.hint
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * / **
 *  * # Safety
 *  *
 *  * `p` must be nonnull and valid
 *  * /
 * fun nextValue(p: ConstPtr): Int {
 *     // SAFETY: caller invariants guarantee that `p` is not null
 *     hint.assertUnchecked(!((p).rawAddress == 0L))
 *     return if (((p).rawAddress == 0L)) {
 *         return -1
 *     } else {
 *         // SAFETY: caller invariants guarantee that `p` is valid
 *         (PrimitiveLayouts.I32).readNative((p).rawAddress) + 1
 *     }
 * }
 * ```
 *
 * Without the `assertUnchecked`, the above function produces the following with optimizations
 * enabled:
 *
 * ```kotlin
 * nextValue: test rdi, rdi je.LBB0_1 mov eax, dword ptr[rdi] inc eax ret.LBB0_1: mov eax, - 1 ret
 * ```
 *
 * Adding the assertion allows the optimizer to remove the extra check:
 *
 * ```kotlin
 * nextValue: mov eax, dword ptr[rdi] inc eax ret
 * ```
 *
 * This example is quite unlike anything that would be used in the real world: it is redundant
 * to put an assertion right next to code that checks the same thing, and dereferencing a
 * pointer already has the builtin assumption that it is nonnull. However, it illustrates the
 * kind of changes the optimizer can make even when the behavior is less obviously related.
 */
public fun assertUnchecked(cond: Boolean) {
    // SAFETY: The caller promised `cond` is true.
    run {
        Unit
    }
}

/**
 * Emits a machine instruction to signal the processor that it is running in
 * a busy-wait spin-loop ("spin lock").
 *
 * Upon receiving the spin-loop signal the processor can optimize its behavior by,
 * for example, saving power or switching hyper-threads.
 *
 * This function is different from [thread.yieldNow] which directly
 * yields to the system's scheduler, whereas `spinLoop` does not interact
 * with the operating system.
 *
 * A common use case for `spinLoop` is implementing bounded optimistic
 * spinning in a CAS loop in synchronization primitives. To avoid problems
 * like priority inversion, it is strongly recommended that the spin loop is
 * terminated after a finite amount of iterations and an appropriate blocking
 * syscall is made.
 *
 * **Note**: On platforms that do not support receiving spin-loop hints this
 * function does not do anything at all.
 *
 * # Examples
 *
 * ```ignore-wasm
 * use std.sync.atomic::{AtomicBool, Ordering};
 * use std.sync.Arc;
 * use std::{hint, thread};
 *
 * // A shared atomic value that threads will use to coordinate
 * let live = Arc.new(AtomicBool.new(false));
 *
 * // In a background thread we'll eventually set the value
 * let bgWork = {
 *     let live = live.clone();
 *     thread.spawn(move || {
 *         // Do some work, then make the value live
 *         doSomeWork();
 *         live.store(true, Ordering.Release);
 *     })
 * };
 *
 * // Back on our current thread, we wait for the value to be set
 * while !live.load(Ordering.Acquire) {
 *     // The spin loop is a hint to the CPU that we're waiting, but probably
 *     // not for very long
 *     hint.spinLoop();
 * }
 *
 * // The value is now set
 * # fn doSomeWork() {}
 * doSomeWork();
 * bgWork.join()?;
 * # Ok::<(), Holder<dynamic core.any.Any + Send + static>>(())
 * ```
 *
 * [thread.yieldNow]: ../../std/thread/fn.yieldNow.html
 */
public fun spinLoop() {
    // SAFETY: the `cfg` attr ensures that we only execute this on x8664 targets.
    X8664.mmPause()
}

/**
 * An identity function that *__hints__* to the compiler to be maximally pessimistic about what
 * `blackBox` could do.
 *
 * Unlike [std.convert.identity], a Rust compiler is encouraged to assume that `blackBox` can
 * use `dummy` in any possible valid way that Rust code is allowed to without introducing undefined
 * behavior in the calling code. This property makes `blackBox` useful for writing code in which
 * certain optimizations are not desired, such as benchmarks.
 *
 * <div class="warning">
 *
 * Note however, that `blackBox` is only (and can only be) provided on a "best-effort" basis. The
 * extent to which it can block optimisations may vary depending upon the platform and code-gen
 * backend used. Programs cannot rely on `blackBox` for *correctness*, beyond it behaving as the
 * identity function. As such, it **must not be relied upon to control critical program behavior.**
 * This also means that this function does not offer any guarantees for cryptographic or security
 * purposes.
 *
 * This limitation is not specific to `blackBox`; there is no mechanism in the entire Rust
 * language that can provide the guarantees required for constant-time cryptography.
 * (There is also no such mechanism in LLVM, so the same is true for every other LLVM-based compiler.)
 *
 * </div>
 *
 * # When is this useful?
 *
 * While not suitable in those mission-critical cases, `blackBox`s functionality can generally be
 * relied upon for benchmarking, and should be used there. It will try to ensure that the
 * compiler doesn't optimize away part of the intended test code based on context. For
 * example:
 *
 * ```kotlin
 * fun contains(haystack: List<String>, needle: String): Boolean {
 *     return haystack.any({ x -> x == needle })
 * }
 * fun benchmark() {
 *     val haystack = mutableListOf("abc", "def", "ghi", "jkl", "mno")
 *     val needle = "ghi"
 *     for (unused in 0 until 10) {
 *         contains(haystack, needle)
 *     }
 * }
 * ```
 *
 * The compiler could theoretically make optimizations like the following:
 *
 * - The `needle` and `haystack` do not change, move the call to `contains` outside the loop and
 *   delete the loop
 * - Inline `contains`
 * - `needle` and `haystack` have values known at compile time, `contains` is always true. Remove
 *   the call and replace with `true`
 * - Nothing is done with the result of `contains`: delete this function call entirely
 * - `benchmark` now has no purpose: delete this function
 *
 * It is not likely that all of the above happens, but the compiler is definitely able to make some
 * optimizations that could result in a very inaccurate benchmark. This is where `blackBox` comes
 * in:
 *
 * ```kotlin
 * import std.hint.blackBox
 *
 * // Same `contains` function.
 * fun contains(haystack: List<String>, needle: String): Boolean {
 *     return haystack.any({ x -> x == needle })
 * }
 * fun benchmark() {
 *     val haystack = mutableListOf("abc", "def", "ghi", "jkl", "mno")
 *     val needle = "ghi"
 *     for (unused in 0 until 10) {
 *         // Force the compiler to run `contains`, even though it is a pure function whose
 *         // results are unused.
 *         blackBox(contains(blackBox(haystack), blackBox(needle)))
 *     }
 * }
 * ```
 *
 * This essentially tells the compiler to block optimizations across any calls to `blackBox`. So,
 * it now:
 *
 * - Treats both arguments to `contains` as unpredictable: the body of `contains` can no longer be
 *   optimized based on argument values
 * - Treats the call to `contains` and its result as volatile: the body of `benchmark` cannot
 *   optimize this away
 *
 * This makes our benchmark much more realistic to how the function would actually be used, where
 * arguments are usually not known at compile time and the result is used in some way.
 *
 * # How to use this
 *
 * In practice, `blackBox` serves two purposes:
 *
 * 1. It prevents the compiler from making optimizations related to the value returned by `blackBox`
 * 2. It forces the value passed to `blackBox` to be calculated, even if the return value of `blackBox` is unused
 *
 * ```kotlin
 * import std.hint.blackBox
 *
 * val zero = 0
 * val five = 5
 * // The compiler will see this and remove the `* five` call, because it knows that multiplying
 * // any integer by 0 will result in 0.
 * val c = zero * five
 * // Adding `blackBox` here disables the compiler's ability to reason about the first operand in the multiplication.
 * // It is forced to assume that it can be any possible number, so it cannot remove the `* five`
 * // operation.
 * val c1 = blackBox(zero) * five
 * ```
 *
 * While most cases will not be as clear-cut as the above example, it still illustrates how
 * `blackBox` can be used. When benchmarking a function, you usually want to wrap its inputs in
 * `blackBox` so the compiler cannot make optimizations that would be unrealistic in real-life
 * use.
 *
 * ```kotlin
 * import std.hint.blackBox
 *
 * // This is a simple function that increments its input by 1. Note that it is pure, meaning it
 * // has no side-effects. This function has no effect if its result is unused. (An example of a
 * // function *with* side-effects is `println()`.)
 * fun increment(x: UByte): UByte {
 *     return (x + 1u).toUByte()
 * }
 * // Here, we call `increment` but discard its result. The compiler, seeing this and knowing that
 * // `increment` is pure, will eliminate this function call entirely. This may not be desired,
 * // though, especially if we're trying to track how much time `increment` takes to execute.
 * increment(blackBox(5))
 * // Here, we force `increment` to be executed. This is because the compiler treats `blackBox`
 * // as if it has side-effects, and thus must compute its input.
 * blackBox(increment(blackBox(5)))
 * ```
 *
 * There may be additional situations where you want to wrap the result of a function in
 * `blackBox` to force its execution. This is situational though, and may not have any effect
 * (such as when the function returns a zero-sized type such as [`()` unit][unit]).
 *
 * Note that `blackBox` has no effect on how its input is treated, only its output. As such,
 * expressions passed to `blackBox` may still be optimized:
 *
 * ```kotlin
 * import std.hint.blackBox
 *
 * // The compiler sees this...
 * val y = blackBox(5 * 10)
 * // ...as this. As such, it will likely simplify `5 * 10` to just `50`.
 * val v0 = 5 * 10
 * val y1 = blackBox(v0)
 * ```
 *
 * In the above example, the `5 * 10` expression is considered distinct from the `blackBox` call,
 * and thus is still optimized by the compiler. You can prevent this by moving the multiplication
 * operation outside of `blackBox`:
 *
 * ```kotlin
 * import std.hint.blackBox
 *
 * // No assumptions can be made about either operand, so the multiplication is not optimized out.
 * val y = blackBox(5) * blackBox(10)
 * ```
 *
 * During constant evaluation, `blackBox` is treated as a no-op.
 */
public fun <T> blackBox(dummy: T): T = dummy

// <-- :)

/**
 * An identity function that causes an `unusedMustUse` warning to be
 * triggered if the given value is not used (returned, stored in a variable,
 * etc) by the caller.
 *
 * This is primarily intended for use in macro-generated code, in which a
 * [`@mustUse` attribute][mustUse] either on a type or a function would not
 * be convenient.
 *
 * [mustUse]: https://doc.rust-lang.org/reference/attributes/diagnostics.html#the-mustUse-attribute
 *
 * # Example
 *
 * ```kotlin
 * import core.fmt
 * import fmt.Arguments
 *
 * @feature(hintMustUse)
 * public fun doctestEntry() {
 *     open class Error
 *     // Implementation detail of makeError macro.
 *     fun makeError(args: fmt.Arguments): Error {
 *         return Error()
 *     }
 *     fun demo(): Error? {
 *         if (true) {
 *             // Oops, meant to write `return Some(makeError("..."));`
 *             mustUse(makeError("..."))
 *         }
 *         return null
 *     }
 * }
 * ```
 *
 * In the above example, we'd like an `unusedMustUse` lint to apply to the
 * value created by `makeError`. However, neither `@mustUse` on a struct
 * nor `@mustUse` on a function is appropriate here, so the macro expands
 * using `core.hint.mustUse` instead.
 *
 * - We wouldn't want `@mustUse` on the `class Error` because that would
 *   make the following unproblematic code trigger a warning:
 *
 *   ```kotlin
 *   open class Error
 *   fun f(arg: String): Result<Unit> {
 *       return Result.success(Unit)
 *   }
 *   ```
 *
 * - Using `@mustUse` on `fun makeError` can't help because the return value
 *   *is* used, as the right-hand side of a `val` statement. The `val`
 *   statement looks useless but is in fact necessary for ensuring that
 *   temporaries within the `formatArgs` expansion are not kept alive past the
 *   creation of the `Error`, as keeping them alive past that point can cause
 *   autotrait issues in async code:
 *
 *   ```kotlin
 *   import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 *   fun log(error: Error) {
 *       // ...
 *   }
 *   // Returns something without a Sync impl.
 *   fun unsync(): ConstPtr {
 *       return ConstPtr(0L)
 *   }
 *   fun f() {
 *       // Using `val` inside the makeError expansion causes temporaries like
 *       // `unsync()` to drop at the semicolon of that `val` statement, which
 *       // is prior to the await point. They would otherwise stay around until
 *       // the semicolon on *this* statement, which is after the await point,
 *       // and the enclosing Future would not implement Send.
 *       log(makeError("look: {:p}", unsync()))
 *   }
 *   ```
 */
public fun <T> mustUse(value: T): T = value

/**
 * Hints to the compiler that a branch condition is likely to be true.
 * Returns the value passed to it.
 *
 * It can be used with `if` or boolean `when` expressions.
 *
 * When used outside of a branch condition, it may still influence a nearby branch, but
 * probably will not have any effect.
 *
 * It can also be applied to parts of expressions, such as `likely(a) && unlikely(b)`, or to
 * compound expressions, such as `likely(a && b)`. When applied to compound expressions, it has
 * the following effect:
 * ```text
 *     likely(!a) => !unlikely(a)
 *     likely(a && b) => likely(a) && likely(b)
 *     likely(a || b) => a || likely(b)
 * ```
 *
 * See also the function [coldPath] which may be more appropriate for idiomatic Rust code.
 *
 * # Examples
 *
 * ```kotlin
 * import core.hint.likely
 *
 * @feature(likelyUnlikely)
 * public fun doctestEntry() {
 *     fun foo(x: Int) {
 *         if (likely(x > 0)) {
 *             println("this branch is likely to be taken")
 *         } else {
 *             println("this branch is unlikely to be taken")
 *         }
 *         when (likely(x > 0)) {
 *             true -> println("this branch is likely to be taken")
 *             false -> println("this branch is unlikely to be taken")
 *         }
 *         // Use outside of a branch condition may still influence a nearby branch
 *         val cond = likely(x != 0)
 *         if (cond) {
 *             println("this branch is likely to be taken")
 *         }
 *     }
 * }
 * ```
 */
public fun likely(b: Boolean): Boolean = b

/**
 * Hints to the compiler that a branch condition is unlikely to be true.
 * Returns the value passed to it.
 *
 * It can be used with `if` or boolean `when` expressions.
 *
 * When used outside of a branch condition, it may still influence a nearby branch, but
 * probably will not have any effect.
 *
 * It can also be applied to parts of expressions, such as `likely(a) && unlikely(b)`, or to
 * compound expressions, such as `unlikely(a && b)`. When applied to compound expressions, it has
 * the following effect:
 * ```text
 *     unlikely(!a) => !likely(a)
 *     unlikely(a && b) => a && unlikely(b)
 *     unlikely(a || b) => unlikely(a) || unlikely(b)
 * ```
 *
 * See also the function [coldPath] which may be more appropriate for idiomatic Rust code.
 *
 * # Examples
 *
 * ```kotlin
 * import core.hint.unlikely
 *
 * @feature(likelyUnlikely)
 * public fun doctestEntry() {
 *     fun foo(x: Int) {
 *         if (unlikely(x > 0)) {
 *             println("this branch is unlikely to be taken")
 *         } else {
 *             println("this branch is likely to be taken")
 *         }
 *         when (unlikely(x > 0)) {
 *             true -> println("this branch is unlikely to be taken")
 *             false -> println("this branch is likely to be taken")
 *         }
 *         // Use outside of a branch condition may still influence a nearby branch
 *         val cond = unlikely(x != 0)
 *         if (cond) {
 *             println("this branch is unlikely to be taken")
 *         }
 *     }
 * }
 * ```
 */
public fun unlikely(b: Boolean): Boolean = b

// Even if for some reason the coldPath intrinsic is not visible to codegen, the coldness will
// ensure that branches this is in are still known to be cold.

/**
 * Hints to the compiler that given path is cold, i.e., unlikely to be taken. The compiler may
 * choose to optimize paths that are not cold at the expense of paths that are cold.
 *
 * Note that like all hints, the exact effect to codegen is not guaranteed. Using `coldPath`
 * can actually *decrease* performance if the branch is called more than expected. It is advisable
 * to perform benchmarks to tell if this function is useful.
 *
 * # Examples
 *
 * ```kotlin
 * import core.hint.coldPath
 *
 * fun foo(x: List<Int>) {
 *     when (val matchVal = x.get(0)) {
 *         null -> {
 *             // this path is unlikely
 *             coldPath()
 *         }
 *         else -> {
 *             val first = matchVal
 *             // this is the fast path
 *         }
 *     }
 * }
 * fun bar(x: Int): Int {
 *     return when (x) {
 *         1 -> 10
 *         2 -> 100
 *         3 -> {
 *             coldPath()
 *             1000
 *         }
 *         // this branch is unlikely
 *         else -> {
 *             coldPath()
 *             10000
 *         }
 *     }
 * }
 * ```
 *
 * This can also be used to implement `likely` and `unlikely` helpers to hint the condition rather
 * than the branch:
 *
 * ```kotlin
 * import core.hint.coldPath
 *
 * fun likely(b: Boolean): Boolean {
 *     if (!b) {
 *         coldPath()
 *     }
 *     return b
 * }
 * fun unlikely(b: Boolean): Boolean {
 *     if (b) {
 *         coldPath()
 *     }
 *     return b
 * }
 * fun foo(x: Int) {
 *     if (likely(x > 0)) {
 *         println("this branch is likely to be taken")
 *     } else {
 *         println("this branch is unlikely to be taken")
 *     }
 * }
 * ```
 */
public fun coldPath() {}

/**
 * Returns either `trueVal` or `falseVal` depending on the value of
 * `condition`, with a hint to the compiler that `condition` is unlikely to be
 * correctly predicted by a CPU’s branch predictor.
 *
 * This method is functionally equivalent to
 * ```ignore (this is just for illustrative purposes)
 * fn selectUnpredictable<T>(b: bool, trueVal: T, falseVal: T) -> T {
 *     if b { trueVal } else { falseVal }
 * }
 * ```
 * but might generate different assembly. In particular, on platforms with
 * a conditional move or select instruction (like `cmov` on x86 or `csel`
 * on ARM) the optimizer might use these instructions to avoid branches,
 * which can benefit performance if the branch predictor is struggling
 * with predicting `condition`, such as in an implementation of binary
 * search.
 *
 * Note however that this lowering is not guaranteed (on any platform) and
 * should not be relied upon when trying to write cryptographic constant-time
 * code. Also be aware that this lowering might *decrease* performance if
 * `condition` is well-predictable. It is advisable to perform benchmarks to
 * tell if this function is useful.
 *
 * # Examples
 *
 * Distribute values evenly between two buckets:
 * ```kotlin
 * import std.hash.BuildHasher
 * import std.hint
 *
 * fun <H : BuildHasher> append(hasher: H, v: Int, bucketOne: List<Int>, bucketTwo: List<Int>) {
 *     val hash = hasher.hashOne(v)
 *     val bucket = hint.selectUnpredictable(hash % 2 == 0, bucketOne, bucketTwo)
 *     bucket.push(v)
 * }
 * ```
 */
public fun <T> selectUnpredictable(condition: Boolean, trueVal: T, falseVal: T): T {
    open class DropOnPanic<T>(
        // Invariant: valid pointer and points to an initialized value that is not further used,
        // i.e. it can be dropped by this guard.
        public var inner: MutPtr,
    ) {
        public open fun drop() {
            // SAFETY: Must be guaranteed on construction of local type `DropOnPanic`.
            this.inner.dropInPlace()
        }
    }
    // Note(https://github.com/rust-lang/unchecked-code-guidelines/issues/245):
    // Change this to use ManuallyDrop instead.
    val trueVal1 = MaybeUninit.new(trueVal, UnresolvedTypeLayout<T>())
    val falseVal1 = MaybeUninit.new(falseVal, UnresolvedTypeLayout<T>())
    val truePtr = trueVal1.asMutPtr()
    val falsePtr = falseVal1.asMutPtr()
    // SAFETY: The value that is not selected is dropped, and the selected one
    // is returned. This is necessary because the intrinsic doesn't drop the
    // value that is  not selected.
    return run {
        // Extract the selected value first, ensure it is dropped as well if dropping the unselected
        // value panics. We construct a temporary by-pointer guard around the selected value while
        // dropping the unselected value. Arguments overlap here, so we can not use mutable
        // reference for these arguments.
        val guard = if (condition) truePtr else falsePtr
        val drop = if (condition) falsePtr else truePtr
        // SAFETY: both pointers are well-aligned and point to initialized values inside a
        // `MaybeUninit` each. In both possible values for `condition` the pointer `guard` and
        // `drop` do not alias (even though the two argument pairs we have selected from did alias
        // each other).
        val guard1 = DropOnPanic(inner = guard)
        drop.dropInPlace()
        forget(guard1)
        // Note that it is important to use the values here. Reading from the pointer we got makes
        // LLVM forget the !unpredictable annotation sometimes (in tests, integer sized values in
        // particular seemed to confuse it, also observed in llvm/llvm-project #82340).
        if (condition) trueVal1 else falseVal1.assumeInit()
    }
}

/**
 * The expected temporal locality of a memory prefetch operation.
 *
 * Locality expresses how likely the prefetched data is to be reused soon,
 * and therefore which level of cache it should be brought into.
 *
 * The locality is just a hint, and may be ignored on some targets or by the hardware.
 *
 * Used with functions like [prefetchRead] and [prefetchWrite].
 */
public sealed class Locality {
    /**
     * Data is expected to be reused eventually.
     *
     * Typically prefetches into L3 cache (if the CPU supports it).
     */
    public data object L3 : Locality()
    /**
     * Data is expected to be reused in the near future.
     *
     * Typically prefetches into L2 cache.
     */
    public data object L2 : Locality()
    /**
     * Data is expected to be reused very soon.
     *
     * Typically prefetches into L1 cache.
     */
    public data object L1 : Locality()

    public fun rustEquals(other: Locality): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        L3 -> 0
        L2 -> 1
        L1 -> 2
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    /**
     * Convert to the constant that LLVM associates with a locality.
     */
    public open fun toLlvm(): Int =
        when (this) {
            Locality.L3 -> 1
            Locality.L2 -> 2
            Locality.L1 -> 3
        }
}

/**
 * Prefetch the cache line containing `ptr` for a future read.
 *
 * A strategically placed prefetch can reduce cache miss latency if the data is accessed
 * soon after, but may also increase bandwidth usage or evict other cache lines.
 *
 * A prefetch is a *hint*, and may be ignored on certain targets or by the hardware.
 *
 * Passing a dangling or invalid pointer is permitted: the memory will not
 * actually be dereferenced, and no faults are raised.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 * import std.hint.Locality
 * import std.hint.prefetchRead
 * import std.mem.sizeOfVal
 *
 * @feature(hintPrefetch)
 * public fun doctestEntry() {
 *     // Prefetch all of `slice` into the L1 cache.
 *     fun <T> prefetchSlice(slice: List<T>) {
 *         // On most systems the cache line size is 64 bytes.
 *         for (offset in (0 until sizeOfVal(slice, typeLayoutT = UnresolvedTypeLayout<List<T>>())).stepBy(64)) {
 *             prefetchRead(ConstPtr((slice.asPtr()).rawAddress + ((offset).toLong() * (UnresolvedTypeLayout<T>()).strideBytes)), Locality.L1)
 *         }
 *     }
 * }
 * ```
 */
public fun prefetchRead(ptr: ConstPtr, locality: Locality) {
    when (locality) {
        Locality.L3 -> Unit
        Locality.L2 -> Unit
        Locality.L1 -> Unit
    }
}

/**
 * Prefetch the cache line containing `ptr` for a single future read, but attempt to avoid
 * polluting the cache.
 *
 * A strategically placed prefetch can reduce cache miss latency if the data is accessed
 * soon after, but may also increase bandwidth usage or evict other cache lines.
 *
 * A prefetch is a *hint*, and may be ignored on certain targets or by the hardware.
 *
 * Passing a dangling or invalid pointer is permitted: the memory will not
 * actually be dereferenced, and no faults are raised.
 */
public fun prefetchReadNonTemporal(ptr: ConstPtr, locality: Locality) {
    // The LLVM intrinsic does not currently support specifying the locality.
}

/**
 * Prefetch the cache line containing `ptr` for a future write.
 *
 * A strategically placed prefetch can reduce cache miss latency if the data is accessed
 * soon after, but may also increase bandwidth usage or evict other cache lines.
 *
 * A prefetch is a *hint*, and may be ignored on certain targets or by the hardware.
 *
 * Passing a dangling or invalid pointer is permitted: the memory will not
 * actually be dereferenced, and no faults are raised.
 */
public fun prefetchWrite(ptr: MutPtr, locality: Locality) {
    when (locality) {
        Locality.L3 -> Unit
        Locality.L2 -> Unit
        Locality.L1 -> Unit
    }
}

/**
 * Prefetch the cache line containing `ptr` for a single future write, but attempt to avoid
 * polluting the cache.
 *
 * A strategically placed prefetch can reduce cache miss latency if the data is accessed
 * soon after, but may also increase bandwidth usage or evict other cache lines.
 *
 * A prefetch is a *hint*, and may be ignored on certain targets or by the hardware.
 *
 * Passing a dangling or invalid pointer is permitted: the memory will not
 * actually be dereferenced, and no faults are raised.
 */
public fun prefetchWriteNonTemporal(ptr: ConstPtr, locality: Locality) {
    // The LLVM intrinsic does not currently support specifying the locality.
}

/**
 * Prefetch the cache line containing `ptr` into the instruction cache for a future read.
 *
 * A strategically placed prefetch can reduce cache miss latency if the instructions are
 * accessed soon after, but may also increase bandwidth usage or evict other cache lines.
 *
 * A prefetch is a *hint*, and may be ignored on certain targets or by the hardware.
 *
 * Passing a dangling or invalid pointer is permitted: the memory will not
 * actually be dereferenced, and no faults are raised.
 */
public fun prefetchReadInstruction(ptr: ConstPtr, locality: Locality) {
    when (locality) {
        Locality.L3 -> Unit
        Locality.L2 -> Unit
        Locality.L1 -> Unit
    }
}
