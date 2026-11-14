// port-lint: source sys/thread_local/key/xous.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.key.xous

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.rawMutSliceList
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.std.alloc.System
import io.github.kotlinmania.rustlang.std.os.xous.ffi.mapMemory
import io.github.kotlinmania.rustlang.std.os.xous.ffi.unmapMemory
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.rt.threadCleanup
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput

// Thread Local Storage
//
// Currently, we are limited to 1023 TLS entries. The entries
// live in a page of memory that's unique per-process, and is
// stored in the `$ tp` register. If this register is 0, then
// TLS has not been initialized and thread cleanup can be skipped.
//
// The index into this register is the `key`. This key is identical
// between all threads, but indexes a different offset within this
// pointer.
//
// # Dtor registration (stolen from Windows)
//
// Xous has no native support for running destructors so we manage our own
// list of destructors to keep track of how to destroy keys. When a thread
// or the process exits, `runDtors` is called, which will iterate through
// the list and run the destructors.
//
// Currently unregistration from this list is not supported. A destructor can be
// registered but cannot be unregistered. There's various simplifying reasons
// for doing this, the big ones being:
//
// 1. Currently we don't even support deallocating TLS keys, so normal operation
//    doesn't need to deallocate a destructor.
// 2. There is no point in time where we know we can unregister a destructor
//    because it could always be getting run by some remote thread.
//
// Typically processes have a statically known set of TLS keys which is pretty
// small, and we'd want to keep this memory alive for the whole process anyway
// really.
//
// Perhaps one day we can fold the `Holder` here into a static allocation,
// expanding the `LazyKey` structure to contain not only a slot for the TLS
// key but also a slot for the destructor queue on windows. An optimization for
// another day!

public fun interface FnCallback {
    public operator fun invoke(arg0: MutPtr)
}

public typealias Key = ULong

public fun interface Dtor {
    public operator fun invoke(arg0: MutPtr)
}

public val TLS_MEMORY_SIZE: Int = 4096

/**
 * TLS keys start at `1`. Index `0` is unused
 */
public var TLS_KEY_INDEX: Atomic<Int> = AtomicUsize.new(1)

public var DTORS: Atomic<MutPtr> = AtomicPtr.new(MutPtr(0L))

public fun tlsPtrAddr(): MutPtr {
    val tp: MutPtr
    run {
        asm("mv {}, tp", out, reg, tp)
    }
    return tp
}

/**
 * Creates an area of memory that's unique per thread. This area will
 * contain all thread local pointers.
 */
public fun tlsTable(): MutableList<MutPtr> {
    val tp = tlsPtrAddr()
    return if (!((tp).rawAddress == 0L)) {
        rawMutSliceList(tp, (TLS_MEMORY_SIZE / sizeOf<MutPtr>(typeLayoutT = PrimitiveLayouts.mutPtrLayout(64L))).toInt(), PrimitiveLayouts.mutPtrLayout(64L))
    } else {
        tlsTableSlow()
    }
}

public fun tlsTableSlow(): MutableList<MutPtr> {
    // If the TP register is `0`, then this thread hasn't initialized
    // its TLS yet. Allocate a new page to store this memory.
    val tp: MutableList<MutPtr> =
        mapMemory(null, null, TLS_MEMORY_SIZE / sizeOf<MutPtr>(typeLayoutT = PrimitiveLayouts.mutPtrLayout(64L)), MemoryFlags.R or MemoryFlags.W).unwrapOrElse({
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("Unable to allocate memory for thread local storage").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("Unable to allocate memory for thread local storage").toString() + ", aborting\n"
            }
            abort()
        })
    for (value in ArrayDeque(tp)) {
        if (!value.isNull()) {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("assertion failed: (* val) . is_null ()").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("assertion failed: (* val) . is_null ()").toString() + ", aborting\n"
                }
                abort()
            }
        }
    }
    run {
        // Set the thread's `$ tp` register
        asm("mv tp, {}", reg, tp, asMutPtr)
    }
    return tp
}

public fun create(dtor: Dtor?): Key {
    // Allocate a new TLS key. These keys are shared among all threads.
    val key = TLS_KEY_INDEX.fetchAdd(1, Relaxed)
    if (dtor != null) {
        val f = dtor
        registerDtor(key, f)
    }
    return key
}

public fun set(key: Key, value: MutPtr) {
    if (!(key < 1022uL) && (key >= 1uL)) {
        run {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("assertion failed: (key < 1022) && (key >= 1)").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("assertion failed: (key < 1022) && (key >= 1)").toString() + ", aborting\n"
            }
            abort()
        }
    }
    val table = tlsTable()
    run {
        val matchVal1 = table.getMut(key)
        when {
            matchVal1 != null -> {
                val v = matchVal1
                v
            }
            else -> {
                val err = matchVal1
                val err1 = err.asRef().map({ Unit })
                run {
                    when (val matchVal2 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out1 = matchVal2
                            Write.writeFmt(out1, "fatal runtime error: " + ("unwrap failed: table . get_mut (key) = $err1").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("unwrap failed: table . get_mut (key) = $err1").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
        }
    } = value
}

public fun get(key: Key): MutPtr {
    if (!(key < 1022uL) && (key >= 1uL)) {
        run {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("assertion failed: (key < 1022) && (key >= 1)").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("assertion failed: (key < 1022) && (key >= 1)").toString() + ", aborting\n"
            }
            abort()
        }
    }
    val table = tlsTable()
    return run {
        val matchVal1 = table.get(key)
        when {
            matchVal1 != null -> {
                val v = matchVal1
                v
            }
            else -> {
                val err = matchVal1
                val err1 = err.asRef().map({ Unit })
                run {
                    when (val matchVal2 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out1 = matchVal2
                            Write.writeFmt(out1, "fatal runtime error: " + ("unwrap failed: table . get (key) = $err1").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("unwrap failed: table . get (key) = $err1").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
        }
    }
}

public fun destroy(key: Key) {
    // Just leak the key. Probably not great on long-running systems that create
    // lots of TLS variables, but in practice that's not an issue.
}

public open class Node internal constructor(
    public var dtor: Dtor,
    public var key: Key,
    nextCtorArg: MutPtr,
) {
    public var next: MutPtr = nextCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            dtor: Dtor,
            key: Key,
            next: MutPtr,
        ): Node = Node(
            dtor = dtor,
            key = key,
            nextCtorArg = next,
        )
    }
}

public fun registerDtor(key: Key, dtor: Dtor) {
    // We use the System allocator here to avoid interfering with a potential
    // Global allocator using thread-local storage.
    val node = Box.newIn(Node(key = key, dtor = dtor, next = MutPtr(0L)), System)
    var head = DTORS.load(Acquire)
    while (true) {
        node.next = head
        run {
            val matchVal = DTORS.compareExchange(head, node, Release, Acquire)
            when (matchVal) { is Result.Ok<*> -> {
                return
            }; is Result.Err<*> -> {
                val cur = matchVal.error
                head = cur
            } }
        }
    }
}

public fun destroyTls() {
    val tp = tlsPtrAddr()
    // If the pointer address is 0, then this thread has no TLS.
    if (((tp).rawAddress == 0L)) {
        return
    }
    runDtors()
    // Finally, free the TLS array
    val result = unmapMemory(rawMutSliceList(tp, (TLS_MEMORY_SIZE / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)).toInt(), PrimitiveLayouts.mutPtrLayout(64L)))
    when (result) {
        is Result.Ok<*> -> {
            val v = result.value
            v
        }
        else -> {
            val err = result
            val err1 = err.asRef().map({ Unit })
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("unwrap failed: result = $err1").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("unwrap failed: result = $err1").toString() + ", aborting\n"
                }
                abort()
            }
        }
    }
}

// This is marked inline(never) to prevent dealloc calls from being reordered
// to after the TLS has been destroyed.
// See https://github.com/rust-lang/rust/pull/144465#pullrequestreview-3289729950
// for more context.
public fun runDtors() {
    var anyRun = true
    // Run the destructor "some" number of times. This is 5x on Windows,
    // so we copy it here. This allows TLS variables to create new
    // TLS variables upon destruction that will also get destroyed.
    // Keep going until we run out of tries or until we have nothing
    // left to destroy.
    for (unused in 0 until 5) {
        if (!anyRun) {
            break
        }
        anyRun = false
        var cur = DTORS.load(Acquire)
        while (!cur.isNull()) {
            val ptr = get(cur.key)
            if (!((ptr).rawAddress == 0L)) {
                set(cur.key, MutPtr(0L))
                (cur.dtor)(ptr)
                anyRun = true
            }
            cur = cur.next
        }
    }
    threadCleanup()
}
