// port-lint: source sys/thread_local/key/racy.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.racy

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.libc.PTHREAD_KEYS_MAX
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.Key
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.create
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.destroy

// A `LazyKey` implementation using racy initialization.
//
// Unfortunately, none of the platforms currently supported by `std` allows
// creating TLS keys at compile-time. Thus we need a way to lazily create keys.
// Instead of blocking API like `OnceLock`, we use racy initialization, which
// should be more lightweight and avoids circular dependencies with the rest of
// `std`.

public fun interface LazyKeyDtorCallback {
    public operator fun invoke(arg0: MutPtr)
}

/**
 * A type for TLS keys that are statically allocated.
 *
 * This is basically a `LazyLock<Key>`, but avoids blocking and circular
 * dependencies with the rest of `std`.
 */
public open class LazyKey internal constructor(
    keyCtorArg: Atomic<Int, Align8<Int>>,
    /**
     * Destructor for the TLS value.
     */
    public var dtor: LazyKeyDtorCallback?,
) {
    /**
     * Inner static TLS key (internals).
     */
    public var key: Atomic<Int, Align8<Int>> = keyCtorArg
        internal set

    public open fun force(): Key =
        when (val matchVal = this.key.load(Ordering.Acquire)) {
            KEY_SENTVAL -> this.lazyInit().toUInt()
            else -> {
                val n = matchVal
                n.toUInt()
            }
        }

    public open fun lazyInit(): Int {
        // POSIX allows the key created here to be KEY_SENTVAL, but the compareExchange
        // below relies on using KEY_SENTVAL as a sentinel value to check who won the
        // race to set the shared TLS key. As far as I know, there is no
        // guaranteed value that cannot be returned as a posixKeyCreate key,
        // so there is no value we can initialize the inner key with to
        // prove that it has not yet been set. As such, we'll continue using a
        // value of KEY_SENTVAL, but with some gyrations to make sure we have a non-KEY_SENTVAL
        // value returned from the creation routine.
        // Note: this is clearly a Note, and should be cleaned up.
        val key1 = create(this.dtor)
        val key =
            if (key1.toInt() != KEY_SENTVAL) {
                key1
            } else {
                val key2 = create(this.dtor)
                run {
                    destroy(key1)
                }
                key2
            }
        if ((if (key.inv()) 1 else 0) != KEY_SENTVAL) {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("assertion failed: key as usize != KEY_SENTVAL").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("assertion failed: key as usize != KEY_SENTVAL").toString() + ", aborting\n"
                }
                abort()
            }
        }
        return run {
            val matchVal1 = this.key.compareExchange(KEY_SENTVAL, key.toInt(), Ordering.Release, Ordering.Acquire)
            when (matchVal1) { is Result.Ok<*, *> -> {
                key.toInt()
            }; is Result.Err<*, *> -> {
                val n = matchVal1.errValue()
                run {
                    destroy(key)
                    n
                }
            } }
        }
    }

    public companion object {
        public operator fun invoke(
            key: Atomic<Int, Align8<Int>>,
            dtor: LazyKeyDtorCallback?,
        ): LazyKey = LazyKey(
            keyCtorArg = key,
            dtor = dtor,
        )

        public fun new(dtor: LazyKeyDtorCallback?): LazyKey = LazyKey(key = AtomicUsize.new(KEY_SENTVAL), dtor = dtor)
    }
}

// Define a sentinel value that is likely not to be returned
// as a TLS key.
public val KEY_SENTVAL: Int = 0

// On QNX SDP, 0 is always returned when currently not in use.
// Using 0 would mean to always create two keys and remove the first
// one (with value of 0) immediately afterwards.
public val KEY_SENTVAL1: Int = PTHREAD_KEYS_MAX + 1
