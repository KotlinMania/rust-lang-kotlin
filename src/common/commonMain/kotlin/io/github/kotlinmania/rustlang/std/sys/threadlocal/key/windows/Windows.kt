// port-lint: source sys/thread_local/key/windows.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.windows

import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.castMut
import io.github.kotlinmania.rustlang.core.ptr.constptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.constptr.wrappingSub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.AcqRel
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Acquire
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Relaxed
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering.Release
import io.github.kotlinmania.rustlang.core.sync.atomic.compareExchange
import io.github.kotlinmania.rustlang.core.sync.atomic.compareExchangeWeak
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.INIT_ONCE_STATIC_INIT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FALSE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.InitOnce
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.TLS_OUT_OF_INDEXES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.TRUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.initOnceBeginInitialize
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.initOnceComplete
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.tlsAlloc
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.tlsFree
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.tlsGetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.tlsSetValue
import io.github.kotlinmania.rustlang.std.sys.stdio.windows.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.windows.enable

// Implementation of `LazyKey` for Windows.
//
// Windows has no native support for running destructors so we manage our own
// list of destructors to keep track of how to destroy keys. We then install a
// callback later to get invoked whenever a thread exits, running all
// appropriate destructors (see the [`guard`](guard) module documentation).
//
// This will likely need to be improved over time, but this module attempts a
// "poor man's" destructor callback system. Once we've got a list of what to
// run, we iterate over all keys, check their values, and then run destructors
// if the values turn out to be non null (setting them to null just beforehand).
// We do this a few times in a loop to basically match Unix semantics. If we
// don't reach a fixed point after a short while then we just inevitably leak
// something.
//
// The list is implemented as an atomic single-linked list of `LazyKey`s and
// does not support unregistration. Unfortunately, this means that we cannot
// use racy initialization for creating the keys in `LazyKey`, as that could
// result in destructors being missed. Hence, we synchronize the creation of
// keys with destructors through [`InitOnce`](c.InitOnce) (`std`s
// [`Once`](sync.Once) cannot be used since it might use TLS itself).
// For keys without destructors, racy initialization suffices.

public typealias Key = UInt

public fun interface Dtor {
    public operator fun invoke(arg0: MutPtr)
}

public open class LazyKey internal constructor(
    keyCtorArg: Atomic<Key>,
    public var dtor: Dtor?,
    nextCtorArg: Atomic<MutPtr, Align8<MutPtr>>,
    onceCtorArg: UnsafeCell<InitOnce>,
) {
    /**
     * The key value shifted up by one. Since TLS_OUT_OF_INDEXES == u32.Max
     * is not a valid key value, this allows us to use zero as sentinel value
     * without risking overflow.
     */
    public var key: Atomic<Key> = keyCtorArg
        internal set

    public var next: Atomic<MutPtr, Align8<MutPtr>> = nextCtorArg
        internal set

    /**
     * Currently, destructors cannot be unregistered, so we cannot use racy
     * initialization for keys. Instead, we need synchronize initialization.
     * Use the Windows-provided `Once` since it does not require TLS.
     */
    public var once: UnsafeCell<InitOnce> = onceCtorArg
        internal set

    public open fun force(): Key {
        if (this.dtor != null) {
            // Needs to be called on all threads where the key might have a non-null value!
            // Otherwise, `runDtors` might not be called on this thread.
            enable()
        }
        return when (val matchVal = this.key.load(Acquire).checkedSub(1)) {
            null -> this.init()
            else -> {
                val dec = matchVal
                dec
            }
        }
    }

    public open fun init(): Key =
        if (this.dtor != null) {
            val pending = FALSE
            val r = initOnceBeginInitialize(this.once.get(), 0u, pending, MutPtr(0L))
            if (r != TRUE) {
                fail()
            }
            if (pending == FALSE) {
                // Some other thread initialized the key, load it.
                this.key.load(Relaxed).wrappingSub(1)
            } else {
                val key = tlsAlloc()
                if (key == TLS_OUT_OF_INDEXES) {
                    run {
                        when (val matchVal = panicOutput()) {
                            null -> {}
                            else -> {
                                val out = matchVal
                                out.writeFmt("fatal runtime error: " + ("out of TLS indexes").toString() + ", aborting\n")
                            }
                        }
                        run {
                            "fatal runtime error: " + ("out of TLS indexes").toString() + ", aborting\n"
                        }
                        abort()
                    }
                }
                run {
                    // Add ourselves to the `DTORS` list, so that when `runDtors` gets called,
                    // our dtor is invoked.
                    registerDtor(this)
                }
                // Release-storing the key needs to be the last thing we do.
                // This is because in `fun key()`, other threads will do an acquire load of the key,
                // and if that sees this write then it will entirely bypass the `InitOnce`. We thus
                // need to establish synchronization through `key`. In particular that acquire load
                // must happen-after the registerDtor above, to ensure the dtor actually runs!
                this.key.store((key + 1u), Release)
                val r1 = initOnceComplete(this.once.get(), 0u, MutPtr(0L))
                if (r1 != TRUE) {
                    fail()
                }
                key
            }
        } else {
            // If there is no destructor to clean up, we can use racy initialization.
            val key1 = tlsAlloc()
            if (key1 == TLS_OUT_OF_INDEXES) {
                run {
                    when (val matchVal1 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out1 = matchVal1
                            out1.writeFmt("fatal runtime error: " + ("out of TLS indexes").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("out of TLS indexes").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
            run {
                val matchVal2 = this.key.compareExchange(0, (key1 + 1u), AcqRel, Acquire)
                when (matchVal2) { is Result.Ok<*, *> -> {
                    key1
                }; is Result.Err<*, *> -> {
                    val new = matchVal2.errValue()
                    run {
                        // Some other thread completed initialization first, so destroy
                        // our key and use theirs.
                        val r2 = tlsFree(key1)
                        if (r2 != TRUE) {
                            fail()
                        }
                        new.wrappingSub(1)
                    }
                } }
            }
        }

    public companion object {
        public operator fun invoke(
            key: Atomic<Key>,
            dtor: Dtor?,
            next: Atomic<MutPtr, Align8<MutPtr>>,
            once: UnsafeCell<InitOnce>,
        ): LazyKey = LazyKey(
            keyCtorArg = key,
            dtor = dtor,
            nextCtorArg = next,
            onceCtorArg = once,
        )

        public fun new(dtor: Dtor?): LazyKey = LazyKey(key = AtomicU32.new(0), dtor = dtor, next = AtomicPtr.new(MutPtr(0L)), once = UnsafeCell.new(INIT_ONCE_STATIC_INIT))
    }
}

public fun fail(): Nothing =
    run {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                out.writeFmt("fatal runtime error: " + ("Unexpected TLS failure").toString() + ", aborting\n")
            }
        }
        run {
            "fatal runtime error: " + ("Unexpected TLS failure").toString() + ", aborting\n"
        }
        abort()
    }

public fun set(key: Key, value: MutPtr) {
    val r = tlsSetValue(key, value)
    // According to MS documentation, `TlsSetValue` returns zero "if it fails"
    if (r != TRUE) {
        fail()
    }
}

public fun get(key: Key): MutPtr = MutPtr((tlsGetValue(key)).rawAddress)

public var DTORS: Atomic<MutPtr, Align8<MutPtr>> = AtomicPtr.new(MutPtr(0L))

/**
 * Should only be called once per key, otherwise loops or breaks may occur in
 * the linked list.
 */
public fun registerDtor(key: LazyKey) {
    val thisRef = ConstPtr.castMut(key)
    // Use acquire ordering to pass along the changes done by the previously
    // registered keys when we store the new head with release ordering.
    var head = DTORS.load(Acquire)
    while (true) {
        key.next.store(head, Relaxed)
        run {
            val matchVal = DTORS.compareExchangeWeak(head, thisRef, Release, Acquire)
            when (matchVal) { is Result.Ok<*, *> -> {
                break
            }; is Result.Err<*, *> -> {
                val new = matchVal.errValue()
                head = new
            } }
        }
    }
}

/**
 * This will and must only be run by the destructor callback in [guard].
 */
public fun runDtors() {
    for (unused in 0 until 5) {
        var anyRun = false
        // Use acquire ordering to observe key initialization.
        var cur = DTORS.load(Acquire)
        while (!cur.isNull()) {
            val preKey = cur.key.load(Acquire)
            val dtor =
                run {
                    val matchVal = cur.dtor
                    when {
                        matchVal != null -> {
                            val v = matchVal
                            v
                        }
                        else -> {
                            val err = matchVal
                            val err1 = err.asRef().map({ drop(it) })
                            run {
                                when (val matchVal1 = panicOutput()) {
                                    null -> {}
                                    else -> {
                                        val out = matchVal1
                                        out.writeFmt("fatal runtime error: " + ("unwrap failed: (* cur) . dtor = $err1").toString() + ", aborting\n")
                                    }
                                }
                                run {
                                    "fatal runtime error: " + ("unwrap failed: (* cur) . dtor = $err1").toString() + ", aborting\n"
                                }
                                abort()
                            }
                        }
                    }
                }
            cur = cur.next.load(Relaxed)
            // In LazyKey.init, we register the dtor before setting `key`.
            // So if one thread's `runDtors` races with another thread executing `init` on the same
            // `LazyKey`, we can encounter a key of 0 here. That means this key was never
            // initialized in this thread so we can safely skip it.
            val key = preKey.checkedSub(1) ?: continue
            // If this is non-zero, then via the `Acquire` load above we synchronized with
            // everything relevant for this key. (It's not clear that this is needed, since the
            // release-acquire pair on DTORS also establishes synchronization, but better safe than
            // sorry.)
            val ptr = tlsGetValue(key)
            if (!((ptr).rawAddress == 0L)) {
                run {
                    tlsSetValue(key, MutPtr(0L))
                    dtor(ptr)
                    anyRun = true
                }
            }
        }
        if (!anyRun) {
            break
        }
    }
}
