// port-lint: source ops/async_function.rs
package io.github.kotlinmania.rustlang.core.ops.asyncfunction

/**
 * An async-aware version of the [`Fn`](ops.Fn) trait.
 *
 * All `async fun` and functions returning futures implement this trait.
 */
public interface AsyncFn<Args, out CallRefFuture, out CallOnceFuture, Output> : AsyncFnMut<Args, CallRefFuture, CallOnceFuture, Output> {
    /**
     * Call the [AsyncFn], returning a future which may borrow from the called closure.
     */
    public fun asyncCall(args: Args): CallRefFuture
}

/**
 * An async-aware version of the [`FnMut`](ops.FnMut) trait.
 *
 * All `async fun` and functions returning futures implement this trait.
 */
public interface AsyncFnMut<Args, out CallRefFuture, out CallOnceFuture, Output> : AsyncFnOnce<Args, CallOnceFuture, Output> {
    /**
     * Call the [AsyncFnMut], returning a future which may borrow from the called closure.
     */
    public fun asyncCallMut(args: Args): CallRefFuture
}

/**
 * An async-aware version of the [`FnOnce`](ops.FnOnce) trait.
 *
 * All `async fun` and functions returning futures implement this trait.
 */
public interface AsyncFnOnce<Args, out CallOnceFuture, Output> {
    /**
     * Call the [AsyncFnOnce], returning a future which may move out of the called closure.
     */
    public fun asyncCallOnce(args: Args): CallOnceFuture
}

public fun <CallRefFuture, CallOnceFuture, Output, A, F> F.asyncCallMut(args: A, asyncCallFFn: (F, Args) -> CallRefFuture): CallRefFuture where F : AsyncFn<A, CallRefFuture, CallOnceFuture, Output> = asyncCallFFn(this, args)

public fun <CallRefFuture, CallOnceFuture, Output, A, F> F.asyncCallOnce(args: A, asyncCallFFn: (F, Args) -> CallRefFuture): CallRefFuture where F : AsyncFn<A, CallRefFuture, CallOnceFuture, Output> = asyncCallFFn(this, args)

public fun <CallRefFuture, CallOnceFuture, Output, A, F> F.asyncCallOnceA(args: A, asyncCallMutFFn: (F, Args) -> CallRefFuture): CallRefFuture where F : AsyncFnMut<A, CallRefFuture, CallOnceFuture, Output> = asyncCallMutFFn(this, args)

/**
 * A helper trait that is used to enforce that the `ClosureKind` of a goal
 * is within the capabilities of a `CoroutineClosure`, and which allows us
 * to delay the projection of the tupled upvar types until after upvar
 * analysis is complete.
 *
 * The `Self` type is expected to be the `kindTy` of the coroutine-closure,
 * and thus either `? 0` or `Byte`/`Short`/`Int` (see docs for `ClosureKind`
 * for an explanation of that). The `GoalKind` is also the same type, but
 * representing the kind of the trait that the closure is being called with.
 */
public interface AsyncFnKindHelper<GoalKind, out Upvars>
