// port-lint: source sync/sync_view.rs
package io.github.kotlinmania.rustlang.core.sync.syncview

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Less
import io.github.kotlinmania.rustlang.core.convert.AsMut
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.future.future.Future
import io.github.kotlinmania.rustlang.core.hash.hash
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.kmPartialCompareValues
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ops.asyncfunction.AsyncFn
import io.github.kotlinmania.rustlang.core.ops.asyncfunction.AsyncFnMut
import io.github.kotlinmania.rustlang.core.ops.asyncfunction.AsyncFnOnce
import io.github.kotlinmania.rustlang.core.ops.coroutine.Coroutine
import io.github.kotlinmania.rustlang.core.ops.coroutine.CoroutineState
import io.github.kotlinmania.rustlang.core.ops.function.Fn
import io.github.kotlinmania.rustlang.core.ops.function.FnMut
import io.github.kotlinmania.rustlang.core.ops.function.FnOnce
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.fromMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.task.poll.Poll
import io.github.kotlinmania.rustlang.core.task.wake.Context

// Defines [SyncView].

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public typealias CallOnceFuture<F> = F.CallOnceFuture

public typealias CallRefFuture<F> = F.CallRefFuture

public typealias Yield<G> = G.Yield

public typealias Return<G> = G.Return

// `SyncView` can't have derived `PartialOrd`, `Clone`, etc. impls as they would
// use  access to the inner value, violating the `Sync` impl's safety
// requirements.

/**
 * `SyncView` provides _mutable_ access, also referred to as _exclusive_
 * access to the underlying value. However, it only permits _immutable_, or _shared_
 * access to the underlying value when that value is [Sync].
 *
 * While this may seem not very useful, it allows `SyncView` to _unconditionally_
 * implement `Sync`. Indeed, the safety requirements of `Sync` state that for `SyncView`
 * to be `Sync`, it must be sound to _share_ across threads, that is, it must be sound
 * for `SyncView` to cross thread boundaries. By design, a `SyncView<T>` for non-`Sync`
 * `T` has no API whatsoever, making it useless, thus harmless, thus memory safe.
 *
 * Certain constructs like [Future]s can only be used with _exclusive_ access,
 * and are often [Send] but not `Sync`, so `SyncView` can be used as hint to the
 * Rust compiler that something is `Sync` in practice.
 *
 * ## Examples
 *
 * A non-`Sync` field prevents the wrapping struct from being `Sync`:
 *
 * ```compile_fail,E0277
 * use std.sync.mpsc::{self, Receiver};
 *
 * struct Inbox {
 *     name: &static str,
 *     receiver: Receiver<u32>,
 * }
 *
 * fn requireSend<T: Send>() {}
 * fn requireSendSync<T: ThreadSafe>() {}
 *
 * requireSend::<Inbox>();      // compiled
 * requireSendSync::<Inbox>(); // compile-failed
 * ```
 *
 * `SyncView` makes the value `Sync` without stripping the struct of its
 * functionality:
 *
 * ```ignore-wasm
 * use std.sync.SyncView;
 * use std.sync.mpsc::{self, Receiver};
 * use std.thread;
 *
 * struct Inbox {
 *     name: &static str,
 *     receiver: SyncView<Receiver<u32>>,
 * }
 *
 * impl Inbox {
 *     fn name(&self) -> &static str {
 *         self.name
 *     }
 *
 *     fn recv(self) -> u32 {
 *         self.receiver.asMut().recv().unwrap()
 *     }
 * }
 *
 * let (sender, receiver) = mpsc.channel();
 * let mut inbox = Inbox { name: "jobs", receiver: SyncView.new(receiver) };
 * sender.send(42).unwrap();
 * drop(sender);
 *
 * thread.scope(|scope| {
 *     let reader = scope.spawn(|| inbox.name());
 *     assertEq(inbox.name(), "jobs");
 *     assertEq(reader.join().unwrap(), "jobs");
 * });
 *
 * let message = thread.spawn(move || inbox.recv()).join().unwrap();
 * assertEq(message, 42);
 * println("Shared Inbox across threads, then moved it to a worker and received 42");
 * ```
 *
 * ## Parallels with a mutex
 *
 * In some sense, `SyncView` can be thought of as a _compile-time_ version of
 * a mutex, as the borrow-checker guarantees that only one  can exist
 * for any value. This is a parallel with the fact that
 *  and  references together can be thought of as a _compile-time_
 * version of a read-write lock.
 */
public open class SyncView<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) : AsRef<T>, AsMut<T> {
    public var inner: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(inner: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<SyncView<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> SyncView<T>(ptr, layout) }, { it.inner })

    // See `SyncView`s docs for justification.

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "SyncView").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    /**
     * Unwrap the value contained in the `SyncView`
     */
    public open fun intoInner(): T = this.inner

    /**
     * Gets pinned exclusive access to the underlying value.
     *
     * `SyncView` is considered to _structurally pin_ the underlying
     * value, which means _unpinned_ `SyncView`s can produce _unpinned_
     * access to the underlying value, but _pinned_ `SyncView`s only
     * produce _pinned_ access to the underlying value.
     */
    public open fun asPinMut(): Pin<T> {
        // SAFETY: `SyncView` can only produce `T` if itself is unpinned
        // `Pin.mapUncheckedMut` is not const, so we do this conversion manually
        return Pin.newUnchecked(this.getUncheckedMut().inner)
    }

    /**
     * Gets pinned shared access to the underlying value.
     *
     * `SyncView` is considered to _structurally pin_ the underlying
     * value, which means _unpinned_ `SyncView`s can produce _unpinned_
     * access to the underlying value, but _pinned_ `SyncView`s only
     * produce _pinned_ access to the underlying value.
     */
    public open fun asPinRef(): Pin<T> {
        // SAFETY: `SyncView` can only produce `T` if itself is unpinned
        // `Pin.mapUnchecked` is not const, so we do this conversion manually
        return Pin.newUnchecked(this.getRef().inner)
    }

    /**
     * Gets shared access to the underlying value.
     */
    public override fun asRef(): T = this.inner

    /**
     * Gets exclusive access to the underlying value.
     */
    public override fun asMut(): T = this.inner

    public open fun clone(): SyncView<T> = SyncView(inner = this.inner.clone(), fieldLayout = this.fieldLayout)

    public override fun hashCode(): Int {
        var state = 0
        this.inner.hash(state)
        return state
    }

    public open fun cmp(other: SyncView<T>): Ordering = kmCompareValues(this.inner, other.inner).let { cmpSign -> when { cmpSign < 0 -> io.github.kotlinmania.rustlang.core.cmp.Ordering.Less; cmpSign > 0 -> io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater; else -> io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal } }

    public companion object {
        public fun <T> default(fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(), defaultTFn: () -> T): SyncView<T> = SyncView(inner = defaultTFn(), fieldLayout = fieldLayout)

        /**
         * Wrap a value in an `SyncView`
         */
        public fun <T> new(t: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): SyncView<T> = SyncView(inner = t, fieldLayout = fieldLayout)

        /**
         * Build a _mutable_ reference to an `SyncView<T>` from
         * a _mutable_ reference to a `T`. This allows you to skip
         * building an `SyncView` with [SyncView.new].
         */
        public fun <T> fromMut(r: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): RustMutableReference<SyncView<T>> {
            // SAFETY: repr is ≥ C, so refs have the same layout; and `SyncView` properties are -agnostic
            return RustMutableReference(MutPtr((((mutPtrFromRustCast(r)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(typeLayoutT, { ptr, layout -> SyncView<T>(ptr, layout) }, { it.inner }))
        }

        /**
         * Build a _pinned mutable_ reference to an `SyncView<T>` from
         * a _pinned mutable_ reference to a `T`. This allows you to skip
         * building an `SyncView` with [SyncView.new].
         */
        public fun <T> fromPinMut(r: Pin<T>): Pin<RustMutableReference<SyncView<T>>> {
            // SAFETY: `SyncView` can only produce `T` if itself is unpinned
            // `Pin.mapUncheckedMut` is not const, so we do this conversion manually
            return Pin.newUnchecked(SyncView.fromMut(r.getUncheckedMut()))
        }

        public fun <T> from(t: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): SyncView<T> = SyncView.new(t, fieldLayout = fieldLayout)
    }
}

public fun <Output, F, Args> SyncView<F>.callOnce(args: Args): Output where F : FnOnce<Args, Output> = this.intoInner().callOnce(args)

public fun <Output, F, Args> SyncView<F>.callMut(args: Args): Output where F : FnMut<Args, Output> = this.asMut().callMut(args)

public fun <Output, F, Args> SyncView<F>.call(args: Args): Output where F : Fn<Args, Output> = this.asRef().call(args)

public fun <CallOnceFuture, Output, F, Args> SyncView<F>.asyncCallOnce(args: Args): CallOnceFuture where F : AsyncFnOnce<Args, CallOnceFuture, Output> = this.intoInner().asyncCallOnce(args)

public fun <CallRefFuture, CallOnceFuture, Output, F, Args> SyncView<F>.asyncCallMut(args: Args): CallRefFuture where F : AsyncFnMut<Args, CallRefFuture, CallOnceFuture, Output> = this.asMut().asyncCallMut(args)

public fun <CallRefFuture, CallOnceFuture, Output, F, Args> SyncView<F>.asyncCall(args: Args): CallRefFuture where F : AsyncFn<Args, CallRefFuture, CallOnceFuture, Output> = this.asRef().asyncCall(args)

public fun <Output, T> SyncView<T>.poll(cx: Context): Poll<Output> where T : Future<Output> = this.asPinMut().poll(cx)

// also #98407
public fun <Yield, Return, R, G> SyncView<G>.resume(arg: R, resumeGFn: (G, R) -> CoroutineState<Yield, Return>): CoroutineState<Yield, Return> where G : Coroutine<R, Yield, Return> = resumeGFn(this.asPinMut(), arg)

public fun <T, U> SyncView<T>.eq(other: SyncView<U>): Boolean = this.inner == other.inner

public fun <T, U> SyncView<T>.partialCmp(other: SyncView<U>): Ordering? = kmPartialCompareValues(this.inner, other.inner)?.let { cmpSign -> when { cmpSign < 0 -> io.github.kotlinmania.rustlang.core.cmp.Ordering.Less; cmpSign > 0 -> io.github.kotlinmania.rustlang.core.cmp.Ordering.Greater; else -> io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal } }
