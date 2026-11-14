package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi

import io.github.kotlinmania.rustlang.core.intrinsics.kmSpinLoopHint
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.alloc.System
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.mpmc.waker.Entry
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.panic.SgxPanicOutput
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.reloc.relocateElfRela
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls.Tls
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.exit
import io.github.kotlinmania.rustlang.std.sys.pal.xous.ccompat.main
import io.github.kotlinmania.rustlang.std.sys.stdio.motor.panicOutput
import io.github.kotlinmania.rustlang.std.thread.current.BUSY

public fun tcsInit(secondary: Boolean) {
    // Be very careful when changing this code: it runs before the binary has been
    // relocated. Any indirect accesses to symbols will likely fail.
    val UNINIT: Int = 0
    val BUSY: Int = 1
    val DONE: Int = 2
    // Three-state spin-lock
    val RELOC_STATE: Atomic<Int>  = AtomicUsize.new(UNINIT)
    if (secondary && RELOC_STATE.load(Ordering.Relaxed) != DONE) {
        run {
            when (val matchVal = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal
                    Write.writeFmt(out, "fatal runtime error: " + ("Entered secondary TCS before main TCS!").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("Entered secondary TCS before main TCS!").toString() + ", aborting\n"
            }
            abort()
        }
    }
    // Try to atomically swap UNINIT with BUSY. The returned state can be:
    run {
        val matchVal1 = RELOC_STATE.compareExchange(UNINIT, BUSY, Ordering.Acquire, Ordering.Acquire)
        when {
            // This thread just obtained the lock and other threads will observe BUSY
            matchVal1 is Result.Ok<*> -> {
                relocateElfRela()
                RELOC_STATE.store(DONE, Ordering.Release)
            }
            // We need to wait until the initialization is done.
            matchVal1 is Result.Err<*> && matchVal1.error == BUSY -> {
                val Busy = (matchVal1 as Result.Err<*>).error
                while (RELOC_STATE.load(Ordering.Acquire) == BUSY) {
                    kmSpinLoopHint()
                }
            }
            // Initialization is done.
            matchVal1 is Result.Err<*> && matchVal1.error == DONE -> {
                val Done = (matchVal1 as Result.Err<*>).error
            }
            else -> error("unreachable")
        }
    }
}

// Note: this item should only exist if this is linked into an executable
// (main function exists). If this is a library, the crate author should be
// able to specify this
public fun entry(p1: ULong, p2: ULong, p3: ULong, secondary: Boolean, p4: ULong, p5: ULong): EntryReturn {
    // Note: how to support TLS in library mode?
    // We use the System allocator here such that the global allocator may use
    // thread-locals.
    val tls = Box.newIn(Tls.new(), System)
    val tlsGuard = tls.activate()
    return if (secondary) {
        val joinNotifier = Entry()
        drop(tlsGuard)
        drop(joinNotifier)
        EntryReturn(0, 0)
    } else {
        fun main(argc: Long, argv: ConstPtr): Long = 0L
        if (!p3 == 0) {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("assertion failed: p3 == 0").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("assertion failed: p3 == 0").toString() + ", aborting\n"
                }
                abort()
            }
        }
        if (!p4 == 0) {
            run {
                when (val matchVal1 = panicOutput()) {
                    null -> {}
                    else -> {
                        val out1 = matchVal1
                        Write.writeFmt(out1, "fatal runtime error: " + ("assertion failed: p4 == 0").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("assertion failed: p4 == 0").toString() + ", aborting\n"
                }
                abort()
            }
        }
        if (!p5 == 0) {
            run {
                when (val matchVal2 = panicOutput()) {
                    null -> {}
                    else -> {
                        val out2 = matchVal2
                        Write.writeFmt(out2, "fatal runtime error: " + ("assertion failed: p5 == 0").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("assertion failed: p5 == 0").toString() + ", aborting\n"
                }
                abort()
            }
        }
        run {
            // The actual types of these arguments are `p1: *const Arg, p2:
            // Int`. We can't currently customize the argument list of Rust's
            // main function, so we pass these in as the standard pointer-sized
            // values in `argc` and `argv`.
            val ret = main(((p2 as Any?) as EntryReturn), ((p1 as Any?) as EntryReturn))
            exitWithCode(ret.toInt())
        }
    }
}

public fun exitWithCode(code: Int): Nothing {
    if (code != 0) {
        when (val matchVal = SgxPanicOutput.new()) {
            null -> {}
            else -> {
                val out = matchVal
                out.append("Exited with status code $code")
            }
        }
    }
    exit(code != 0)
}

public fun abortReentry(): Nothing = exit(false)
