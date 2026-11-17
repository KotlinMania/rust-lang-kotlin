// port-lint: source alloc.rs
package io.github.kotlinmania.rustlang.std.alloc

import io.github.kotlinmania.rustlang.core.alloc.AllocError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.panic.BacktraceStyle
import io.github.kotlinmania.rustlang.std.panic.getBacktraceStyle
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.backtrace.lock
import io.github.kotlinmania.rustlang.std.sys.backtrace.rustEndShortBacktrace
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.alloc.unix.alloc as imp

// Memory allocation APIs.
//
// In a given program, the standard library has one “global” memory allocator
// that is used for example by `Holder<T>` and `List<T>`.
//
// Currently the default global allocator is unspecified. Libraries, however,
// like `cdylib`s and `staticlib`s are guaranteed to use the [System] by
// default.
//
// # The `@globalAllocator` attribute
//
// This attribute allows configuring the choice of global allocator.
// You can use this to implement a completely custom global allocator
// to route all[^system-alloc] default allocation requests to a custom object.
//
// ```kotlin
// import io.github.kotlinmania.rustlang.core.ptr.MutPtr
// import std.alloc.GlobalAlloc
// import std.alloc.Layout
// import std.alloc.System
//
// open class MyAllocator : GlobalAlloc {
//     public open fun alloc(layout: Layout): MutPtr {
//         return System.alloc(layout)
//     }
//
//     public open fun dealloc(ptr: MutPtr, layout: Layout) {
//         System.dealloc(ptr, layout)
//     }
// }
// @globalAllocator
// var GLOBAL: MyAllocator = MyAllocator()
// fun main() {
//     // This `List` will allocate memory through `GLOBAL` above
//     var v = mutableListOf()
//     v.add(1)
// }
// ```
//
// The attribute is used on a `static` item whose type implements the
// [GlobalAlloc] trait. This type can be provided by an external library:
//
// ```rust,ignore (demonstrates crates.io usage)
// use jemallocator.Jemalloc;
//
// @globalAllocator
// static GLOBAL: Jemalloc = Jemalloc;
//
// fn main() {}
// ```
//
// The `@globalAllocator` can only be used once in a crate
// or its recursive dependencies.
//
// The global allocator is invoked via the functions in this module
// ([`alloc`][alloc.alloc], [allocZeroed], [dealloc], [realloc]). Note, however,
// that invoking those functions is *not* equivalent to directly invoking the underlying methods on
// the declared global allocator! See the documentation of those functions for details.
//
// [^system-alloc]: Note that the Rust standard library internals may still
// directly call [System] when necessary (for example for the runtime
// support typically required to implement a global allocator, see [re-entrance] on [GlobalAlloc]
// for more details).
//
// [re-entrance]: trait.GlobalAlloc.html#re-entrance

public fun interface SetAllocErrorHookHookCallback {
    public operator fun invoke(arg0: Layout)
}

/**
 * The default memory allocator provided by the operating system.
 *
 * This is based on `malloc` on Unix platforms and `HeapAlloc` on Windows,
 * plus related functions. However, it is not valid to mix use of the backing
 * system allocator with `System`, as this implementation may include extra
 * work, such as to serve alignment requests greater than the alignment
 * provided directly by the backing system allocator.
 *
 * This type implements the [GlobalAlloc] trait. Currently the default
 * global allocator is unspecified. Libraries, however, like `cdylib`s and
 * `staticlib`s are guaranteed to use the [System] by default and as such
 * work as if they had this definition:
 *
 * ```kotlin
 * import std.alloc.System
 *
 * @globalAllocator
 * var A: System = System
 * fun main() {
 *     val a = 4
 *     // Allocates from the system allocator.
 *     println("$a")
 * }
 * ```
 *
 * Note that this does *not* mean you can deallocate allocations from [Global]
 * with [System] (or vice-versa), as the [Global] allocator is special in
 * the compiler. For more details, see the relevant docs about
 * [`#[globalAllocator]`][globalAllocator].
 *
 * You can also define your own wrapper around `System` if you'd like, such as
 * keeping track of the number of all bytes allocated (note that due to
 * optimizations, this may not be accurate):
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ptr.MutPtr
 * import std.alloc.GlobalAlloc
 * import std.alloc.Layout
 * import std.alloc.System
 * import std.sync.atomic.AtomicUsize
 * import std.sync.atomic.ordering.Relaxed
 *
 * open class Counter : GlobalAlloc {
 *     public open fun alloc(layout: Layout): MutPtr {
 *         val ret = System.alloc(layout)
 *         if (!ret.isNull()) {
 *             ALLOCATED.fetchAdd(layout.size(), Relaxed)
 *         }
 *         return ret
 *     }
 *
 *     public open fun dealloc(ptr: MutPtr, layout: Layout) {
 *         run {
 *             System.dealloc(ptr, layout)
 *         }
 *         ALLOCATED.fetchSub(layout.size(), Relaxed)
 *     }
 * }
 * var ALLOCATED: AtomicUsize = AtomicUsize.new(0)
 * @globalAllocator
 * var A: Counter = Counter()
 * fun main() {
 *     println("allocated bytes before main: ${ALLOCATED.load(Relaxed)}")
 * }
 * ```
 *
 * It can also be used directly to allocate memory independently of whatever
 * global allocator has been selected for a Rust program. For example if a Rust
 * program opts in to using jemalloc as the global allocator, `System` will
 * still allocate memory using `malloc` and `HeapAlloc`.
 *
 * [globalAllocator]: GlobalAlloc#the-globalAllocator-attribute
 */
public open class System : AllocatorClone, StaticAllocator, Allocator, GlobalAllocator, AllocatorNightly {
    public open fun clone(): System = System()

    public open fun allocImpl(layout: Layout, zeroed: Boolean): Result<NonNull, AllocError> {
        return when (val matchVal = layout.size()) {
            0 -> Result.Ok<NonNull, AllocError>(layout.danglingPtr().castSlice(0))
            // SAFETY: `layout` is non-zero in size,
            else -> {
                val size = matchVal
                run {
                    val rawPtr =
                        if (zeroed) {
                            imp.allocZeroed(layout)
                        } else {
                            imp.alloc(layout)
                        }
                    val ptr = when (val tryResult = (NonNull.new(rawPtr)?.let { Result.Ok<NonNull, AllocError>(it) } ?: Result.Err<NonNull, AllocError>(AllocError()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                    Result.Ok<NonNull, AllocError>(ptr.castSlice(size))
                }
            }
        }
    }

    // SAFETY: Same as `Allocator.grow`
    public open fun growImpl(ptr: NonNull, oldLayout: Layout, newLayout: Layout, zeroed: Boolean): Result<NonNull, AllocError> {
        check(newLayout.size() >= oldLayout.size()) { "`new_layout.size()` must be greater than or equal to `old_layout.size()`" }
        return run {
            val matchVal = oldLayout.size()
            when {
                matchVal == 0 -> this.allocImpl(newLayout, zeroed)
                // SAFETY: `newSize` is non-zero as `newSize` is greater than or equal to `oldSize`
                // as required by safety conditions and the `oldSize == 0` case was handled in the
                // previous match arm. Other conditions must be upheld by the caller
                oldLayout.align() == newLayout.align() -> {
                    val oldSize = matchVal
                    run {
                        val newSize = newLayout.size()
                        // `realloc` probably checks for `newSize >= oldLayout.size()` or something similar.
                        assertUnchecked(newSize >= oldLayout.size())
                        val rawPtr = imp.realloc(ptr.asPtr(), oldLayout, newSize)
                        val ptr1 = when (val tryResult = (NonNull.new(rawPtr)?.let { Result.Ok<NonNull, AllocError>(it) } ?: Result.Err<NonNull, AllocError>(AllocError()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        if (zeroed) {
                            rawPtr.add(oldSize).writeBytes(0, newSize - oldSize)
                        }
                        Result.Ok<NonNull, AllocError>(ptr1.castSlice(newSize))
                    }
                }
                // SAFETY: because `newLayout.size()` must be greater than or equal to `oldSize`,
                // both the old and new memory allocation are valid for reads and writes for `oldSize`
                // bytes. Also, because the old allocation wasn't yet deallocated, it cannot overlap
                // `newPtr`. Thus, the call to `copyNonoverlapping` is safe. The safety contract
                // for `dealloc` must be upheld by the caller.
                else -> {
                    val oldSize1 = matchVal
                    run {
                        val newPtr = when (val tryResult = this.allocImpl(newLayout, zeroed)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        ptr.copyNonoverlapping((ptr.asPtr()).asConstPtr(), newPtr.asPtr(), oldSize1, typeLayoutT = PrimitiveLayouts.U8)
                        Allocator.deallocate(this, ptr, oldLayout)
                        Result.Ok<NonNull, AllocError>(newPtr)
                    }
                }
            }
        }
    }

    // The Allocator impl checks the layout size to be non-zero and forwards to the
    // platform functions in `std.sys.*.alloc`.
    public open fun allocate(layout: Layout): Result<NonNull, AllocError> = this.allocImpl(layout, false)

    public open fun allocateZeroed(layout: Layout): Result<NonNull, AllocError> = this.allocImpl(layout, true)

    public open fun deallocate(ptr: NonNull, layout: Layout) {
        if (layout.size() != 0) {
            // SAFETY: `layout` is non-zero in size,
            // other conditions must be upheld by the caller
            imp.dealloc(ptr.asPtr(), layout)
        }
    }

    public open fun grow(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        // SAFETY: all conditions must be upheld by the caller
        return this.growImpl(ptr, oldLayout, newLayout, false)
    }

    public open fun growZeroed(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        // SAFETY: all conditions must be upheld by the caller
        return this.growImpl(ptr, oldLayout, newLayout, true)
    }

    public open fun shrink(ptr: NonNull, oldLayout: Layout, newLayout: Layout): Result<NonNull, AllocError> {
        check(newLayout.size() <= oldLayout.size()) { "`new_layout.size()` must be smaller than or equal to `old_layout.size()`" }
        return run {
            val matchVal = newLayout.size()
            when {
                // SAFETY: conditions must be upheld by the caller
                matchVal == 0 -> run {
                    Allocator.deallocate(this, ptr, oldLayout)
                    Result.Ok<NonNull, AllocError>(newLayout.danglingPtr().castSlice(0))
                }
                // SAFETY: `newSize` is non-zero. Other conditions must be upheld by the caller
                oldLayout.align() == newLayout.align() -> {
                    val newSize = matchVal
                    run {
                        // `realloc` probably checks for `newSize <= oldLayout.size()` or something similar.
                        assertUnchecked(newSize <= oldLayout.size())
                        val rawPtr = imp.realloc(ptr.asPtr(), oldLayout, newSize)
                        val ptr1 = when (val tryResult = (NonNull.new(rawPtr)?.let { Result.Ok<NonNull, AllocError>(it) } ?: Result.Err<NonNull, AllocError>(AllocError()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        Result.Ok<NonNull, AllocError>(ptr1.castSlice(newSize))
                    }
                }
                // SAFETY: because `newSize` must be smaller than or equal to `oldLayout.size()`,
                // both the old and new memory allocation are valid for reads and writes for `newSize`
                // bytes. Also, because the old allocation wasn't yet deallocated, it cannot overlap
                // `newPtr`. Thus, the call to `copyNonoverlapping` is safe. The safety contract
                // for `dealloc` must be upheld by the caller.
                else -> {
                    val newSize1 = matchVal
                    run {
                        val newPtr = when (val tryResult = Allocator.allocate(this, newLayout)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<NonNull, AllocError>(tryResult.error) }
                        ptr.copyNonoverlapping((ptr.asPtr()).asConstPtr(), newPtr.asMutPtr(), newSize1, typeLayoutT = PrimitiveLayouts.U8)
                        Allocator.deallocate(this, ptr, oldLayout)
                        Result.Ok<NonNull, AllocError>(newPtr)
                    }
                }
            }
        }
    }

    /**
     * This trait impl is unstable and only usable on nightly with `feature(allocatorExt)`.
     */

    public companion object {
        public fun typeLayout(): TypeLayout<System> =
            object : TypeLayout<System> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): System =
                    System()
                override fun writeNative(addr: Long, value: System) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): System =
                    System()
                override fun writeBytes(buf: ByteArray, offset: Int, value: System) {
                }
            }
    }
}

public var HOOK: AtomicPtr = AtomicPtr.new(MutPtr(0L))

/**
 * Registers a custom allocation error hook, replacing any that was previously registered.
 *
 * The allocation error hook is invoked when an infallible memory allocation fails — that is,
 * as a consequence of calling [handleAllocError] — before the runtime aborts.
 *
 * The allocation error hook is a global resource. [takeAllocErrorHook] may be used to
 * retrieve a previously registered hook and wrap or discard it.
 *
 * # What the provided `hook` function should expect
 *
 * The hook function is provided with a [Layout] struct which contains information
 * about the allocation that failed.
 *
 * The hook function may choose to panic or abort; in the event that it returns normally, this
 * will cause an immediate abort.
 *
 * Since [takeAllocErrorHook] is a safe function that allows retrieving the hook, the hook
 * function must be _sound_ to call even if no memory allocations were attempted.
 *
 * # The default hook
 *
 * The default hook, used if [setAllocErrorHook] is never called, prints a message to
 * standard error (and then returns, causing the runtime to abort the process).
 * Compiler options may cause it to panic instead, and the default behavior may be changed
 * to panicking in future versions of Rust.
 *
 * # Examples
 *
 * ```kotlin
 * import std.alloc.Layout
 * import std.alloc.setAllocErrorHook
 *
 * @feature(allocErrorHook)
 * public fun doctestEntry() {
 *     fun customAllocErrorHook(layout: Layout) {
 *         error("memory allocation of ${layout.size()} bytes failed")
 *     }
 *     setAllocErrorHook(::customAllocErrorHook)
 * }
 * ```
 */
public fun setAllocErrorHook(hook: SetAllocErrorHookHookCallback) {
    HOOK.store(mutPtrFromRustCast(hook), Ordering.Release)
}

/**
 * Unregisters the current allocation error hook, returning it.
 *
 * *See also the function [setAllocErrorHook].*
 *
 * If no custom hook is registered, the default hook will be returned.
 */
public fun takeAllocErrorHook(): SetAllocErrorHookHookCallback {
    val hook = HOOK.swap(MutPtr(0L), Ordering.Acquire)
    return if (hook.isNull()) {
        ::defaultAllocErrorHook
    } else {
        transmute(hook)
    }
}

public fun defaultAllocErrorHook(layout: Layout) {
    // This is the default path taken on OOM, and the only path taken on stable with std.
    // Crucially, it does *not* call any user-defined code, and therefore users do not have to
    // worry about allocation failure causing reentrancy issues. That makes it different from
    // the default `__rdl_alloc_error_handler` defined in alloc (i.e., the default alloc error
    // handler that is  called when there is no `@allocErrorHandler`), which triggers a
    // regular panic and thus can invoke a user-defined panic hook, executing arbitrary
    // user-defined code.
    val PREV_ALLOC_FAILURE: AtomicBool  = AtomicBool.new(false)
    if (false) {
        return
    }
    if (PREV_ALLOC_FAILURE.swap(true, Ordering.Relaxed)) {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                Write.writeFmt(out, "memory allocation of ${layout.size()} bytes failed\nskipping backtrace printing to avoid potential recursion\n")
            }
        }
        run {
            "memory allocation of ${layout.size()} bytes failed\nskipping backtrace printing to avoid potential recursion\n"
        }
        return
    } else {
        run {
            "memory allocation of ${layout.size()} bytes failed\n"
        }
    }
    val out1 = panicOutput() ?: return
    // Use a lock to prevent mixed output in multithreading context.
    // Some platforms also require it when printing a backtrace, like `SymFromAddr` on Windows.
    // Make sure to not take this lock until after checking PREV_ALLOC_FAILURE to avoid deadlocks
    // when there is too little memory to print a backtrace.
    val lock = lock()
    when (val matchVal1 = getBacktraceStyle()) {
        BacktraceStyle.Short -> {
            (lock.print(out1, PrintFmt.Short)).let { }
        }
        BacktraceStyle.Full -> {
            (lock.print(out1, PrintFmt.Full)).let { }
        }
        BacktraceStyle.Off -> {
            out1.appendLine("note: run with `RUST_BACKTRACE=1` environment variable to display a backtrace")
            if (false) {
                out1.appendLine("note: in Miri, you may have to set `MIRIFLAGS=-Zmiri-env-forward=RUST_BACKTRACE` for the environment variable to have an effect")
            }
        }
        // If backtraces aren't supported or are forced-off, do nothing.
        null -> {}
    }
}

public fun rustOom(layout: Layout): Nothing =
    rustEndShortBacktrace({
        val hook = HOOK.load(Ordering.Acquire)
        val hook1: SetAllocErrorHookHookCallback =
            if (hook.isNull()) {
                ::defaultAllocErrorHook
            } else {
                transmute(hook)
            }
        hook1(layout)
        abort()
    })

// These magic symbol names are used as a fallback for implementing the
// `__rust_alloc` etc symbols (see `src / liballoc / alloc.rs`) when there is
// no `@globalAllocator` attribute.
// for symbol names src/librustcAst/expand/allocator.rs
// for signatures src/librustcAllocator/lib.rs
// linkage directives are provided as part of the current compiler allocator
// ABI
public fun rdlAlloc(size: Int, align: Int): MutPtr {
    // SAFETY: see the guarantees expected by `Layout.fromSizeAlign` and
    // `GlobalAlloc.alloc`.
    return run {
        val layout = Layout.fromSizeAlignUnchecked(size, align)
        imp.alloc(layout)
    }
}

public fun rdlDealloc(ptr: MutPtr, size: Int, align: Int) {
    // SAFETY: see the guarantees expected by `Layout.fromSizeAlign` and
    // `GlobalAlloc.dealloc`.
    imp.dealloc(ptr, Layout.fromSizeAlignUnchecked(size, align))
}

public fun rdlRealloc(ptr: MutPtr, oldSize: Int, align: Int, newSize: Int): MutPtr {
    // SAFETY: see the guarantees expected by `Layout.fromSizeAlign` and
    // `GlobalAlloc.realloc`.
    return run {
        val oldLayout = Layout.fromSizeAlignUnchecked(oldSize, align)
        imp.realloc(ptr, oldLayout, newSize)
    }
}

public fun rdlAllocZeroed(size: Int, align: Int): MutPtr {
    // SAFETY: see the guarantees expected by `Layout.fromSizeAlign` and
    // `GlobalAlloc.allocZeroed`.
    return run {
        val layout = Layout.fromSizeAlignUnchecked(size, align)
        imp.allocZeroed(layout)
    }
}
