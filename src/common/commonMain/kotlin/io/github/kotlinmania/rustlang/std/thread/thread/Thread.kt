// port-lint: source thread/thread.rs
package io.github.kotlinmania.rustlang.std.thread.thread

import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.alloc.System
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.Parker
import io.github.kotlinmania.rustlang.std.sys.thread.unix.currentOsId
import io.github.kotlinmania.rustlang.std.thread.mainthread.get

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

/**
 * Like a `String` it's guaranteed UTF-8 and like a `CString` it's null terminated.
 */
public open class ThreadNameString(
    public var inner: CString,
) {
    public open fun asCstr(): CStr = this.inner

    public open fun asStr(): String {
        // SAFETY: `ThreadNameString` is guaranteed to be UTF-8.
        return String.fromUtf8Unchecked(this.inner.toBytes())
    }

    public companion object {
        public fun from(s: String): ThreadNameString = ThreadNameString(inner = CString.new(s).expect("thread name may not contain interior null bytes"))
    }
}

/**
 * The internal representation of a `Thread` handle
 *
 * We explicitly set the alignment for our guarantee in Thread.intoRaw. This
 * allows applications to stuff extra metadata bits into the alignment, which
 * can be rather useful when working with atomics.
 */
public open class Inner internal constructor(
    public var name: ThreadNameString?,
    public var id: Any,
    osIdCtorArg: OnceLock<ULong>,
    public var parker: Parker,
) {
    public var osId: OnceLock<ULong> = osIdCtorArg
        internal set

    public open fun parker(): Pin<Parker> = Pin.mapUnchecked(this, { inner -> inner.parker })

    public companion object {
        public operator fun invoke(
            name: ThreadNameString?,
            id: Any,
            osId: OnceLock<ULong>,
            parker: Parker,
        ): Inner = Inner(
            name = name,
            id = id,
            osIdCtorArg = osId,
            parker = parker,
        )
    }
}

/**
 * A handle to a thread.
 *
 * Threads are represented via the `Thread` type, which you can get in one of
 * two ways:
 *
 * * By spawning a new thread, e.g., using the [thread.spawn]
 *   function, and calling [thread] on the [JoinHandle].
 * * By requesting the current thread, using the [thread.current] function.
 *
 * The [thread.current] function is available even for threads not spawned
 * by the APIs of this module.
 *
 * There is usually no need to create a `Thread` struct yourself, one
 * should instead use a function like `spawn` to create new threads, see the
 * docs of [Builder] and [spawn] for more details.
 */
public open class Thread internal constructor(
    innerCtorArg: Pin<Inner>,
) {
    // We use the System allocator such that creating or dropping this handle
    // does not interfere with a potential Global allocator using thread-local
    // storage.
    public var inner: Pin<Inner> = innerCtorArg
        internal set

    public open fun clone(): Thread = Thread(inner)

    /**
     * Records the calling thread's OS id, as reported by
     * `imp.currentOsId`, in this handle.
     *
     * May only be called from the thread to which this handle belongs. A
     * spawned thread does this itself once it starts running, since its handle
     * already exists by then.
     *
     * `imp.currentOsId` must not allocate with the global allocator or call
     * `thread.current`.
     */
    public open fun setOsIdToCurrent() {
        when (val matchVal = currentOsId()) {
            null -> {}
            else -> {
                val osId = matchVal
                if (this.inner.osId.set(osId).isErr()) {
                    run {
                        when (val matchVal1 = panicOutput()) {
                            null -> {}
                            else -> {
                                val out = matchVal1
                                Write.writeFmt(out, "fatal runtime error: " + ("thread OS id already set").toString() + ", aborting\n")
                            }
                        }
                        run {
                            "fatal runtime error: " + ("thread OS id already set").toString() + ", aborting\n"
                        }
                        abort()
                    }
                }
            }
        }
    }

    /**
     * Like the public [park], but callable on any handle. This is used to
     * allow parking in TLS destructors.
     *
     * # Safety
     * May only be called from the thread to which this handle belongs.
     */
    public open fun park() {
        this.inner.asRef().parker().park()
    }

    /**
     * Like the public [parkTimeout], but callable on any handle. This is
     * used to allow parking in TLS destructors.
     *
     * # Safety
     * May only be called from the thread to which this handle belongs.
     */
    public open fun parkTimeout(dur: Duration) {
        this.inner.asRef().parker().parkTimeout(dur)
    }

    /**
     * Atomically makes the handle's token available if it is not already.
     *
     * Every thread is equipped with some basic low-level blocking support, via
     * the [park] function and the `unpark()` method. These can be used as a
     * more CPU-efficient implementation of a spinlock.
     *
     * See the [park documentation] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.intrinsics.kmSpinLoopHint
     * import std.sync.atomic.AtomicBool
     * import std.sync.atomic.Ordering
     * import std.thread
     * import std.time.Duration
     *
     * var QUEUED: AtomicBool = AtomicBool.new(false)
     * val parkedThread = thread.Builder.new().spawn({
     *     println("Parking thread")
     *     QUEUED.store(true, Ordering.Release)
     *     thread.park()
     *     println("Thread unparked")
     * }).unwrap()
     * // Let some time pass for the thread to be spawned.
     * thread.sleep(Duration.fromMillis(10))
     * // Wait until the other thread is queued.
     * // This is crucial! It guarantees that the `unpark` below is not consumed
     * // by some other code in the parked thread (e.g. inside `println`).
     * while (!QUEUED.load(Ordering.Acquire)) {
     *     // Spinning is of course inefficient; in practice, this would more likely be
     *     // a dequeue where we have no work to do if there's nobody queued.
     *     kmSpinLoopHint()
     * }
     * println("Unpark the thread")
     * parkedThread.thread().unpark()
     * parkedThread.join().unwrap()
     * ```
     */
    public open fun unpark() {
        this.inner.asRef().parker().unpark()
    }

    /**
     * Gets the thread's unique identifier.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * internal expect fun currentThreadBoundToken(): Any
     *
     * public fun doctestEntry() {
     *     val otherThread = thread.spawn({
     *         currentThreadBoundToken()
     *     })
     *     val otherThreadId = otherThread.join().unwrap()
     *     check(currentThreadBoundToken() != otherThreadId)
     * }
     * ```
     */
    public open fun id(): Any = this.inner.id

    /**
     * Gets the id the operating system gave this thread, if it has one that can
     * be read.
     *
     * This is the id that shows up in tools like `ps` and `top`, debuggers and
     * crash logs, unlike [ThreadId], which has no guaranteed relationship to
     * it. On a platform with no OS-visible thread id, such as SGX, the value
     * may be some other per-thread value (there, the thread's address), which
     * such tools will not recognize. `None` means no id could be recorded: the
     * thread has not started running yet, or the platform has no way to read
     * one.
     *
     * The operating system may reuse the id of a thread that has exited, and a
     * `Thread` handle can outlive the thread it refers to. After a `fork`, the
     * id recorded in the child process still refers to the parent's thread; it
     * is not re-read. Use the id only where a reused or stale id is harmless,
     * such as logging.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread
     *
     * @feature(threadOsId)
     * public fun doctestEntry() {
     *     val spawned = thread.spawn({ thread.current().osId() }).join().unwrap()
     *     if (spawned != null) {
     *         check(spawned != thread.current().osId())
     *     }
     * }
     * ```
     */
    public open fun osId(): ULong? = this.inner.osId.get()

    /**
     * Gets the thread's name.
     *
     * For more information about named threads, see
     * [this module-level documentation][naming-threads].
     *
     * # Examples
     *
     * Threads by default have no name specified:
     *
     * ```kotlin
     * import std.thread
     *
     * val builder = thread.Builder.new()
     * val handler = builder.spawn({
     *     check(thread.current().name() == null)
     * }).unwrap()
     * handler.join().unwrap()
     * ```
     *
     * Thread with a specified name:
     *
     * ```kotlin
     * import std.thread
     *
     * val builder = thread.Builder.new().name("foo".into())
     * val handler = builder.spawn({
     *     check(thread.current().name() == "foo")
     * }).unwrap()
     * handler.join().unwrap()
     * ```
     *
     * [naming-threads]: ./index.html#naming-threads
     */
    public open fun name(): String? =
        when (val matchVal = this.inner.name) {
            null -> {
                if (get() == this.inner.id) {
                    "main"
                } else {
                    null
                }
            }
            else -> {
                val name = matchVal
                name.asStr()
            }
        }

    /**
     * Consumes the `Thread`, returning a raw pointer.
     *
     * To avoid a memory leak the pointer must be converted
     * back into a `Thread` using [Thread.fromRaw]. The pointer is
     * guaranteed to be aligned to at least 8 bytes.
     *
     * # Examples
     *
     * ```kotlin
     * import std.thread.Thread
     *
     * @feature(threadRaw)
     * public fun doctestEntry() {
     *     val thread = thread.current()
     *     val id = thread.id()
     *     val ptr = Thread.intoRaw(thread)
     *     run {
     *         check(Thread.fromRaw(ptr).id() == id)
     *     }
     * }
     * ```
     */
    public open fun intoRaw(): ConstPtr {
        // Safety: We only expose an opaque pointer, which maintains the `Pin` invariant.
        val inner = Pin.intoInnerUnchecked(this.inner)
        return constPtrFromRustCast(Arc.intoRawWithAllocator(inner).first)
    }

    public open fun cname(): CStr? =
        when (val matchVal = this.inner.name) {
            null -> {
                if (get() == this.inner.id) {
                    "main"
                } else {
                    null
                }
            }
            else -> {
                val name = matchVal
                name.asCstr()
            }
        }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "Thread").field("id", this.id()).field("name", this.name()).finishNonExhaustive()
    }

    public companion object {
        public operator fun invoke(
            inner: Pin<Inner>,
        ): Thread = Thread(
            innerCtorArg = inner,
        )

        public fun new(id: Any, name: String?): Thread {
            val name1 = name?.let(ThreadNameString::from)
            // We have to use `unchecked` here to construct the `Parker` in-place,
            // which is required for the UNIX implementation.
            //
            // SAFETY: We pin the Arc immediately after creation, so its address never
            // changes.
            val inner =
                run {
                    val arc = Arc.newUninitIn(System)
                    val ptr = Arc.getMutUnchecked(arc).asMutPtr()
                    (addrOfMut(ptr.name)).write(name1)
                    (addrOfMut(ptr.id)).write(id)
                    (addrOfMut(ptr.osId)).write(OnceLock.new())
                    Parker.newInPlace(addrOfMut(ptr.parker))
                    Pin.newUnchecked(arc.assumeInit())
                }
            return Thread(inner = inner)
        }

        /**
         * Creates a handle for the calling thread, recording its OS id.
         *
         * `id` must be the `ThreadId` of the calling thread.
         *
         * Takes no name because passing one into `Thread.new` allocates with the
         * global allocator, which `thread.current` is documented never to use.
         */
        public fun newCurrent(id: Any): Thread {
            val thread = Thread.new(id, null)
            thread.setOsIdToCurrent()
            return thread
        }

        /**
         * Constructs a `Thread` from a raw pointer.
         *
         * The raw pointer must have been previously returned
         * by a call to [Thread.intoRaw].
         *
         * # Safety
         *
         * This function is unchecked because improper use may lead
         * to memory unsafety, even if the returned `Thread` is never
         * accessed.
         *
         * Creating a `Thread` from a pointer other than one returned
         * from [Thread.intoRaw] is **undefined behavior**.
         *
         * Calling this function twice on the same raw pointer can lead
         * to a double-free if both `Thread` instances are dropped.
         */
        public fun fromRaw(ptr: ConstPtr): Thread {
            // Safety: Upheld by caller.
            return Thread(inner = Pin.newUnchecked(Arc.fromRawIn(ConstPtr(ptr.rawAddress, ptr.rawMeta), System)))
        }
    }
}
