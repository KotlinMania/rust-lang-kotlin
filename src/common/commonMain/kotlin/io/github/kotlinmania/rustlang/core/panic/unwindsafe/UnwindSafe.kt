// port-lint: source panic/unwind_safe.rs
package io.github.kotlinmania.rustlang.core.panic.unwindsafe

import io.github.kotlinmania.rustlang.core.asynciter.asynciter.AsyncIterator
import io.github.kotlinmania.rustlang.core.future.future.Future
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

public typealias Item<S> = S.Item

/**
 * A marker trait which represents "panic safe" types in Rust.
 *
 * This trait is implemented by default for many types and behaves similarly in
 * terms of inference of implementation to the [Send] and [Sync] traits. The
 * purpose of this trait is to encode what types are safe to cross a [catchUnwind]
 * boundary with no fear of unwind safety.
 *
 * [catchUnwind]: ../../std/panic/fn.catchUnwind.html
 *
 * ## What is unwind safety?
 *
 * In Rust a function can "return" early if it either panics or calls a
 * function which transitively panics. This sort of control flow is not always
 * anticipated, and has the possibility of causing subtle bugs through a
 * combination of two critical components:
 *
 * 1. A data structure is in a temporarily invalid state when the thread
 *    panics.
 * 2. This broken invariant is then later observed.
 *
 * Typically in Rust, it is difficult to perform step (2) because catching a
 * panic involves either spawning a thread (which in turn makes it difficult
 * to later witness broken invariants) or using the `catchUnwind` function in this
 * module. Additionally, even if an invariant is witnessed, it typically isn't a
 * problem in Rust because there are no uninitialized values (like in C or C++).
 *
 * It is possible, however, for **logical** invariants to be broken in Rust,
 * which can end up causing behavioral bugs. Another key aspect of unwind safety
 * in Rust is that, in the absence of `unchecked` code, a panic cannot lead to
 * memory unsafety.
 *
 * That was a bit of a whirlwind tour of unwind safety, but for more information
 * about unwind safety and how it applies to Rust, see an [associated RFC][rfc].
 *
 * [rfc]: https://rust-lang.github.io/rfcs/1236-stabilize-catch-panic.html
 *
 * ## What is `UnwindSafe`?
 *
 * Now that we've got an idea of what unwind safety is in Rust, it's also
 * important to understand what this trait represents. As mentioned above, one
 * way to witness broken invariants is through the `catchUnwind` function in this
 * module as it allows catching a panic and then re-using the environment of
 * the closure.
 *
 * Simply put, a type `T` implements `UnwindSafe` if it cannot easily allow
 * witnessing a broken invariant through the use of `catchUnwind` (catching a
 * panic). This trait is an auto trait, so it is automatically implemented for
 * many types, and it is also structurally composed (e.g., a struct is unwind
 * safe if all of its components are unwind safe).
 *
 * Note, however, that this is not an unchecked trait, so there is not a succinct
 * contract that this trait is providing. Instead it is intended as more of a
 * "speed bump" to alert users of `catchUnwind` that broken invariants may be
 * witnessed and may need to be accounted for.
 *
 * ## Who implements `UnwindSafe`?
 *
 * Types such as `T` and `RefCell<T>` are examples which are **not**
 * unwind safe. The general idea is that any mutable state which can be shared
 * across `catchUnwind` is not unwind safe by default. This is because it is very
 * easy to witness a broken invariant outside of `catchUnwind` as the data is
 * simply accessed as usual.
 *
 * Types like `Mutex<T>`, however, are unwind safe because they implement
 * poisoning by default. They still allow witnessing a broken invariant, but
 * they already provide their own "speed bumps" to do so.
 *
 * ## When should `UnwindSafe` be used?
 *
 * It is not intended that most types or functions need to worry about this trait.
 * It is only used as a bound on the `catchUnwind` function and as mentioned
 * above, the lack of `unchecked` means it is mostly an advisory. The
 * [AssertUnwindSafe] wrapper struct can be used to force this trait to be
 * implemented for any closed over variables passed to `catchUnwind`.
 */
public interface UnwindSafe

/**
 * A marker trait representing types where a shared reference is considered
 * unwind safe.
 *
 * This trait is namely not implemented by [UnsafeCell], the root of all
 * interior mutability.
 *
 * This is a "helper marker trait" used to provide impl blocks for the
 * [UnwindSafe] trait, for more information see that documentation.
 */
public interface RefUnwindSafe

/**
 * A simple wrapper around a type to assert that it is unwind safe.
 *
 * When using [catchUnwind] it may be the case that some of the closed over
 * variables are not unwind safe. For example if `T` is captured the
 * compiler will generate a warning indicating that it is not unwind safe. It
 * might not be the case, however, that this is actually a problem due to the
 * specific usage of [catchUnwind] if unwind safety is specifically taken into
 * account. This wrapper struct is useful for a quick and lightweight
 * annotation that a variable is indeed unwind safe.
 *
 * [catchUnwind]: ../../std/panic/fn.catchUnwind.html
 *
 * # Examples
 *
 * One way to use `AssertUnwindSafe` is to assert that the entire closure
 * itself is unwind safe, bypassing all checks for all variables:
 *
 * ```kotlin
 * import std.panic.AssertUnwindSafe
 *
 * var variable = 4
 * // This code will not compile because the closure captures `variable`
 * // which is not considered unwind safe by default.
 * // panic.catchUnwind(|| {
 * // variable += 3;
 * // });
 * // This, however, will compile due to the `AssertUnwindSafe` wrapper
 * val result = panic.catchUnwind(AssertUnwindSafe({
 *     variable += 3
 * }))
 * // ...
 * ```
 *
 * Wrapping the entire closure amounts to a blanket assertion that all captured
 * variables are unwind safe. This has the downside that if new captures are
 * added in the future, they will also be considered unwind safe. Therefore,
 * you may prefer to just wrap individual captures, as shown below. This is
 * more annotation, but it ensures that if a new capture is added which is not
 * unwind safe, you will get a compilation error at that time, which will
 * allow you to consider whether that new capture in fact represent a bug or
 * not.
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
 * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
 * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
 * import std.panic.AssertUnwindSafe
 *
 * val localStorageL5C0 = AddressableLocal(4, PrimitiveLayouts.I32)
 * val otherCapture = 3
 * val result = run {
 *     var wrapper = AssertUnwindSafe(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
 *     panic.catchUnwind({
 *         wrapper += otherCapture
 *     })
 * }
 * // ...
 * ```
 */
public open class AssertUnwindSafe<T>(
    public var value: T,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public open fun deref(): T = this.value

    public open fun derefMut(): T = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "AssertUnwindSafe").field(this.value).finish()

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<AssertUnwindSafe<T>> =
            object : TypeLayout<AssertUnwindSafe<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): AssertUnwindSafe<T> =
                    AssertUnwindSafe(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: AssertUnwindSafe<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): AssertUnwindSafe<T> =
                    AssertUnwindSafe(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AssertUnwindSafe<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }

        public fun <T> default(defaultTFn: () -> T): AssertUnwindSafe<T> = AssertUnwindSafe(defaultTFn())

        /**
         * If a value's type is already `UnwindSafe`,
         * wrapping it in `AssertUnwindSafe` is never incorrect.
         */
        public fun <T> from(value: T): AssertUnwindSafe<T> = AssertUnwindSafe(value)
    }
}

// Implementations of the `UnwindSafe` trait:
//
// * By default everything is unwind safe
// * pointers T contains mutability of some form are not unwind safe
// * Types like Mutex/RwLock which are explicitly poisoned are unwind safe
// * Our custom AssertUnwindSafe wrapper is indeed unwind safe

// Pretty simple implementations for the `RefUnwindSafe` marker trait,
// basically just saying that `UnsafeCell` is the
// only thing which doesn't implement it (which then transitively applies to
// everything else).

public fun <R> AssertUnwindSafe<F>.callOnce(args: Unit): R = (this.value)()

public fun <Output, F : Future<Any?>> AssertUnwindSafe<F>.poll(cx: Context, pollFFn: (F, Context) -> Poll<Output>): Poll<Output> {
    // SAFETY: pin projection. AssertUnwindSafe follows structural pinning.
    val pinnedField = Pin.mapUncheckedMut(this, { x -> x.value })
    return pollFFn(pinnedField, cx)
}

public fun <Item, S : AsyncIterator<Any?>> AssertUnwindSafe<S>.pollNext(cx: Context): Poll<Item?> {
    // SAFETY: pin projection. AssertUnwindSafe follows structural pinning.
    return this.mapUncheckedMut({ x -> x.value }).pollNext(cx)
}

public fun <Item, S : AsyncIterator<Any?>> AssertUnwindSafe<S>.sizeHint(): Pair<Int, Int?> = this.value.sizeHint()
