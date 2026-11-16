// port-lint: source sys/pal/unix/stack_overflow/handler_signal.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.handlersignal

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.libc.CInt
import io.github.kotlinmania.rustlang.libc.CVoid
import io.github.kotlinmania.rustlang.libc.getauxval
import io.github.kotlinmania.rustlang.libc.mprotect
import io.github.kotlinmania.rustlang.libc.munmap
import io.github.kotlinmania.rustlang.libc.new.musl.signal.SiginfoT
import io.github.kotlinmania.rustlang.libc.pthreadGetStackaddrNp
import io.github.kotlinmania.rustlang.libc.pthreadGetStacksizeNp
import io.github.kotlinmania.rustlang.libc.pthreadSelf
import io.github.kotlinmania.rustlang.libc.sigaltstack
import io.github.kotlinmania.rustlang.libc.unix.SIG_DFL
import io.github.kotlinmania.rustlang.libc.unix.SighandlerT
import io.github.kotlinmania.rustlang.libc.unix.bsd.SA_ONSTACK
import io.github.kotlinmania.rustlang.libc.unix.bsd.SA_SIGINFO
import io.github.kotlinmania.rustlang.libc.unix.bsd.SIGBUS
import io.github.kotlinmania.rustlang.libc.unix.bsd.SS_DISABLE
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.MAP_ANON
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SIGSTKSZ
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.Sigaction
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.StackT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.AT_MINSIGSTKSZ
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_FAILED
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_FIXED
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_PRIVATE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.PROT_NONE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.PROT_READ
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.PROT_WRITE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.MAP_STACK
import io.github.kotlinmania.rustlang.libc.windows.SIGSEGV
import io.github.kotlinmania.rustlang.std.io.Error
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.pal.unix.conf.pageSize
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.Handler
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.threadinfo.deleteCurrentInfo
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.threadinfo.setCurrentInfo
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.threadinfo.withCurrentInfo
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.libc.mmap as mmap64

public fun interface FnCallback {
    public operator fun invoke(arg0: Int, arg1: MutPtr, arg2: MutPtr)
}

/**
 * Signal handler for the SIGSEGV and SIGBUS handlers.
 *
 * We've got guard pages (unmapped pages) at the end of every thread's
 * stack, so if a thread ends up running into the guard page it'll trigger
 * this handler. We want to detect these cases and print out a helpful error
 * saying that the stack has overflowed. All other signals, however, should
 * go back to what they were originally supposed to do.
 *
 * This handler currently exists purely to print an informative message
 * whenever a thread overflows its stack. We then abort to exit and
 * indicate a crash, but to avoid a misleading SIGSEGV that might lead
 * users to believe that unchecked code has accessed an invalid pointer; the
 * SIGSEGV encountered when overflowing the stack is expected and
 * well-defined.
 *
 * If this is not a stack overflow, the handler un-registers itself and
 * then returns (to allow the original signal to be delivered again).
 * Returning from this kind of signal handler is technically not defined
 * to work when reading the POSIX spec strictly, but in practice it turns
 * out many large systems and all implementations allow returning from a
 * signal handler to work. For a more detailed explanation see the
 * comments on #26458.
 *
 * # Safety
 * Rust doesn't call this, it *gets called* by the kernel, which we expect
 * to provide valid parameters. Apart from that, this function does not
 * have any other preconditions.
 */
public fun signalHandler(signum: CInt, info: MutPtr, data: MutPtr) {
    // SAFETY: this pointer is provided by the system and will always point to a valid `siginfoT`.
    val faultAddr = (UnresolvedTypeLayout<SiginfoT>()).readNative((info).rawAddress).siAddr().addr()
    // `withCurrentInfo` expects that the process aborts after it is
    // called. If the signal was not caused by a memory access, this might
    // not be true. We detect this by noticing that the `siAddr` field is
    // zero if the signal is synthetic.
    if (faultAddr != 0) {
        withCurrentInfo({ threadInfo ->
            // If the faulting address is within the guard page, then we print a
            // message saying so and abort.
            if (threadInfo != null && threadInfo.guardPageRange.contains(faultAddr)) {
                // Hey you! Yes, you modifying the stack overflow message!
                // Please make sure that all functions called here are
                // actually async-signal-safe. If they're not, try retrieving
                // the information beforehand and storing it in `ThreadInfo`.
                // Thank you!
                // - says Jonas after having had to watch his carefully
                // written code get made unsound again.
                val tid = threadInfo.tid
                val name = threadInfo.name.asDeref().unwrapOr("<unknown>")
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "\nthread '$name' ($tid) has overflowed its stack\n")
                    }
                }
                run {
                    "\nthread '$name' ($tid) has overflowed its stack\n"
                }
                run {
                    when (val matchVal1 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out1 = matchVal1
                            Write.writeFmt(out1, "fatal runtime error: " + ("stack overflow").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("stack overflow").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
        })
    }
    // Unregister ourselves by reverting back to the default behavior.
    // SAFETY: assuming all platforms define struct sigaction as "zero-initializable"
    val action: Sigaction  = MaybeUninit.zeroed(UnresolvedTypeLayout<Sigaction>()).assumeInit()
    action.saSigaction = SIG_DFL
    // SAFETY: pray this is a well-behaved POSIX implementation of fn sigaction
    Sigaction(signum, action, MutPtr(0L))
    // See comment above for why this function returns.
}

public var PAGE_SIZE: Atomic<Int, Align8<Int>> = AtomicUsize.new(0)

// Store a pointer to the allocation for the main thread's altstack so that
// tools like valgrind don't complain about a leaked unreachable allocation.
//
// If the main thread exits, the process will terminate so there's no use in
// freeing resources. It also means that the altstack is still installed
// while TLS destructors are run on the main thread (c.f. #111272).
public var MAIN_ALTSTACK: Atomic<MutPtr, Align8<MutPtr>> = AtomicPtr.new(MutPtr(0L))

public var NEED_ALTSTACK: Atomic<Boolean, Align1<UByte>> = AtomicBool.new(false)

/**
 * # Safety
 * Must be called only once, on the main thread, during program startup.
 */
public fun init() {
    PAGE_SIZE.store(pageSize(), Ordering.Relaxed)
    // SAFETY:
    // This is only called on the main thread, and since it is still early
    // in the programs scope there is (almost) certainly enough stack
    // space left to install the guard page.
    var guardPageRange = installMainGuard()
    // Even for panic=immediate-abort, installing the guard pages is important for soundness.
    // That said, we do not care about giving nice stackoverflow messages via our custom
    // signal handler, just exit early and let the user enjoy the segfault.
    if (false) {
        return
    }
    // SAFETY: C structures are always zero-initializable.
    val action: Sigaction  = MaybeUninit.zeroed(UnresolvedTypeLayout<Sigaction>()).assumeInit()
    for (signal in listOf(SIGSEGV, SIGBUS)) {
        // SAFETY: just fetches the current signal handler into action
        Sigaction(signal, MutPtr(0L), action)
        // We assume that overriding the signal handler is always safe,
        // which might conflict with certain libraries that rely on a
        // specific signal behaviour. To prevent problems, we only
        // override the handler if it has not been set yet.
        if (action.saSigaction == SIG_DFL) {
            if (!NEED_ALTSTACK.load(Ordering.Relaxed)) {
                // haven't set up our sigaltstack yet
                NEED_ALTSTACK.store(true, Ordering.Release)
                val handler = makeHandler(true)
                MAIN_ALTSTACK.store(handler.data, Ordering.Relaxed)
                forget(handler)
                when (val matchVal = guardPageRange.also { guardPageRange = null }) {
                    null -> {}
                    else -> {
                        val guardPageRange1 = matchVal
                        setCurrentInfo(guardPageRange1)
                    }
                }
            }
            action.saFlags = SA_SIGINFO or SA_ONSTACK
            action.saSigaction = ((((::signalHandler as Any?) as FnCallback) as Any?) as SighandlerT)
            // SAFETY:
            // `action` describes a valid `sigaction` and `signalHandler`
            // is safe to use as a signal handler for `SIGSEGV` and `SIGBUS`.
            Sigaction(signal, action, MutPtr(0L))
        }
    }
}

public fun getStack(): StackT {
    // OpenBSD requires this flag for stack mapping
    // otherwise the said mapping will fail as a no-op on most systems
    // and has a different meaning on FreeBSD
    val flags = MAP_PRIVATE or MAP_ANON or MAP_STACK
    val sigstackSize = sigstackSize()
    val pageSize = PAGE_SIZE.load(Ordering.Relaxed)
    // SAFETY: this does not unmap any existing pages.
    val stackp = mmap64(MutPtr(0L), sigstackSize + pageSize, PROT_READ or PROT_WRITE, flags, -1, 0)
    if (stackp == MAP_FAILED) {
        error("failed to allocate an alternative stack: ${Error.lastOsError()}")
    }
    // SAFETY: this only affects the memory we just allocated.
    val guardResult = mprotect(stackp, pageSize, PROT_NONE)
    if (guardResult != 0) {
        error("failed to set up alternative stack guard page: ${Error.lastOsError()}")
    }
    // SAFETY:
    // The region was allocated with a larger size than `pageSize`, so this
    // addition is within bounds.
    val stackp1 = stackp.add(pageSize)
    return StackT(ssSp = stackp1, ssFlags = 0, ssSize = sigstackSize)
}

public fun makeHandler(mainThread: Boolean): Handler {
    if (false || !NEED_ALTSTACK.load(Ordering.Acquire)) {
        return ConstPtr(0L)
    }
    if (!mainThread) {
        when (val matchVal = currentGuard()) {
            null -> {}
            else -> {
                val guardPageRange = matchVal
                setCurrentInfo(guardPageRange)
            }
        }
    }
    // Load the current alternate signal stack to see if we need to install
    // our own.
    //
    // SAFETY: C structures are always zero-initializable.
    val stack = Handler()
    // SAFETY: `stack` is valid for writing a `stackT`.
    sigaltstack(ConstPtr(0L), stack)
    // Configure alternate signal stack, if one is not already set.
    return if (stack.ssFlags and SS_DISABLE != 0) {
        val stack1 = getStack()
        // SAFETY:
        // `stackT` is a freshly allocated stack that's not used anywhere
        // else. It contains a guard page, so stack overflows in signal
        // handlers will not cause undefined behaviour. We must make the
        // fundamental runtime assumption that it is safe to install an
        // alternate signal stack if there is none currently installed.
        // This might conflict with foreign libraries that use the existence
        // of an alternate signal stack as indication that certain runtime
        // initialisation by the library has been performed (e.g. old
        // versions of `std` assumed that certain thread-locals were already
        // accessed and thus initialized in the thread if the stack overflow
        // signal was successfully delivered). Such assumptions in other
        // libraries are fundamentally flawed, so we pay no regard to them.
        sigaltstack(stack1, MutPtr(0L))
        Handler(data = mutPtrFromRustCast(stack1.ssSp))
    } else {
        ConstPtr(0L)
    }
}

/**
 * # Safety
 * Must only be called with a pointer returned by `makeHandler`, and only
 * once per `Handler`.
 */
public fun dropHandler(data: MutPtr) {
    if (!((data).rawAddress == 0L)) {
        val sigstackSize = sigstackSize()
        val pageSize = PAGE_SIZE.load(Ordering.Relaxed)
        val disablingStack = StackT(ssSp = MutPtr(0L), ssFlags = SS_DISABLE, ssSize = sigstackSize)
        // SAFETY:
        // We assume that disabling the alternate signal stack is always
        // sound, even if the current alternate signal stack is not the one
        // we installed in `makeHandler`. Any stack overflows from this
        // point on will abort the program when the kernel tries to write
        // the signal information to the guard page.
        //
        // Note: detect if the stack has changed, and only uninstall if it hasn't.
        sigaltstack(disablingStack, MutPtr(0L))
        // The stack returned by `getStack` is part of a mapping that
        // started one page earlier, so walk back a page and unmap from
        // there.
        //
        // SAFETY:
        // This allocation was created by us in `getStack` and, as the
        // alternate signal stack is now disabled, is no longer in use.
        munmap(MutPtr((data).rawAddress - ((pageSize).toLong() * (UnresolvedTypeLayout<CVoid>()).strideBytes)), sigstackSize + pageSize)
    }
    deleteCurrentInfo()
}

/**
 * Modern kernels on modern hardware can have dynamic signal stack sizes.
 */
public fun sigstackSize(): Int {
    // SAFETY: `getauxval` is always safe to call.
    val dynamicSigstksz = getauxval(AT_MINSIGSTKSZ)
    // If getauxval couldn't find the entry, it returns 0,
    // so take the higher of the "constant" and auxval.
    // This transparently supports older kernels which don't provide AT_MINSIGSTKSZ
    return SIGSTKSZ.max(dynamicSigstksz.toInt())
}

public fun getStackStart(): MutPtr? {
    // SAFETY: always safe to call.
    val th = pthreadSelf()
    // SAFETY: `th` is a valid `pthreadT`.
    return run {
        val stackptr = pthreadGetStackaddrNp(th)
        val stacksize = pthreadGetStacksizeNp(th)
        stackptr.mapAddr({ addr -> addr - stacksize })
    }
}

public fun stackStartAligned(pageSize: Int): MutPtr? {
    val stackptr = (getStackStart() ?: return null)
    val stackaddr = (stackptr).rawAddress.toInt()
    // Ensure stackaddr is page aligned! A parent process might
    // have reset RLIMIT_STACK to be non-page aligned. The
    // pthreadAttrGetstack() reports the usable stack area
    // stackaddr < stackaddr + stacksize, so if stackaddr is not
    // page-aligned, calculate the fix such that stackaddr <
    // newPageAlignedStackaddr < stackaddr + stacksize
    val remainder = stackaddr % pageSize
    return if (remainder == 0) {
        stackptr
    } else {
        MutPtr((stackaddr + pageSize - remainder).toLong())
    }
}

/**
 * # Safety
 * This function must only be called from the main thread, and there must
 * be sufficient stack space remaining to place a stack guard.
 */
public fun installMainGuard(): IntRange? {
    val pageSize = PAGE_SIZE.load(Ordering.Relaxed)
    // this way someone on any unix-y OS can check that all these compile
    return if (false) {
        installMainGuardLinux(pageSize)
    } else if (false) {
        installMainGuardLinuxMusl(pageSize)
    } else if (false) {
        return null
    } else if (false) {
        installMainGuardBsds(pageSize)
    } else {
        // SAFETY: guaranteed by caller.
        installMainGuardDefault(pageSize)
    }
}

public fun installMainGuardLinux(pageSize: Int): IntRange? {
    // See the corresponding conditional in init().
    // Avoid stackStartAligned, which makes slow syscalls to read /proc/self/maps
    if (false) {
        return null
    }
    // Linux doesn't allocate the whole stack right away, and
    // the kernel has its own stack-guard mechanism to fault
    // when growing too close to an existing mapping. If we map
    // our own guard, then the kernel starts enforcing a rather
    // large gap above that, rendering much of the possible
    // stack space useless. See #43052.
    //
    // Instead, we'll just note where we expect rlimit to start
    // faulting, so our handler can report "stack overflow", and
    // trust that the kernel's own stack guard will work.
    val stackptr = (stackStartAligned(pageSize) ?: return null)
    val stackaddr = (stackptr).rawAddress.toInt()
    return stackaddr - pageSize until stackaddr
}

public fun installMainGuardLinuxMusl(pageSize: Int): IntRange? {
    // For the main thread, the musl's pthreadAttrGetstack
    // returns the current stack size, rather than maximum size
    // it can eventually grow to. It cannot be used to determine
    // the position of kernel's stack guard.
    return null
}

public fun installMainGuardBsds(pageSize: Int): IntRange? {
    // See the corresponding conditional in installMainGuardLinux().
    if (false) {
        return null
    }
    // OpenBSD stack already includes a guard page, and stack is
    // immutable.
    // NetBSD stack includes the guard page.
    //
    // We'll just note where we expect rlimit to start
    // faulting, so our handler can report "stack overflow", and
    // trust that the kernel's own stack guard will work.
    val stackptr = (stackStartAligned(pageSize) ?: return null)
    val stackaddr = (stackptr).rawAddress.toInt()
    return stackaddr - pageSize until stackaddr
}

/**
 * # Safety
 * This function must only be called from the main thread, and there must
 * be sufficient stack space remaining to place a stack guard.
 */
public fun installMainGuardDefault(pageSize: Int): IntRange? {
    // Reallocate the last page of the stack.
    // This ensures SIGBUS will be raised on
    // stack overflow.
    // Systems which enforce strict PAX MPROTECT do not allow
    // to mprotect() a mapping with less restrictive permissions
    // than the initial mmap() used, so we mmap() here with
    // read/write permissions and only then mprotect() it to
    // no permissions at all. See issue #50313.
    val stackptr = (stackStartAligned(pageSize) ?: return null)
    // SAFETY:
    // The memory region from `stackptr .. stackptr + pageSize` belongs to
    // the current thread's stack, and the caller has asserted that there
    // is sufficient stack space, which means that this will not overwrite
    // any existing allocations.
    val result = mmap64(stackptr, pageSize, PROT_READ or PROT_WRITE, MAP_PRIVATE or MAP_ANON or MAP_FIXED, -1, 0)
    if (((result).rawAddress.toULong() != (stackptr).rawAddress.toULong()) || result == MAP_FAILED) {
        error("failed to allocate a guard page: ${Error.lastOsError()}")
    }
    // SAFETY:
    // Since this function is only called on the main thread, the stack will
    // not be reused until program exit, so the runtime will never observe
    // that part of the stack has been made unusable in this way.
    val result1 = mprotect(stackptr, pageSize, PROT_NONE)
    if (result1 != 0) {
        error("failed to protect the guard page: ${Error.lastOsError()}")
    }
    val guardaddr = (stackptr).rawAddress.toInt()
    return guardaddr until guardaddr + pageSize
}

public fun currentGuard(): IntRange? {
    val stackptr = (getStackStart() ?: return null)
    val stackaddr = (stackptr).rawAddress.toInt()
    return stackaddr - PAGE_SIZE.load(Ordering.Relaxed) until stackaddr
}
