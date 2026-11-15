// port-lint: source sys/thread_local/destructors/linux_like.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.destructors.linuxlike

import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.os.raw.CInt

// Destructor registration for Linux-like systems.
//
// Since what appears to be version 2.18, glibc has shipped the
// `__cxa_thread_atexit_impl` symbol which GCC and clang both use to invoke
// destructors in C++ threadLocal globals. This function does exactly what
// we want: it schedules a callback which will be run at thread exit with the
// provided argument.
//
// Unfortunately, our minimum supported glibc version (at the time of writing)
// is 2.17, so we can only link this symbol weakly and need to use the
// [`list`](list) destructor implementation as fallback.

public fun interface RegisterDtorCallback {
    public operator fun invoke(arg0: MutPtr)
}

public fun interface FnCallback {
    public operator fun invoke(arg0: MutPtr)
}

public fun interface FnCallback1 {
    public operator fun invoke(arg0: FnCallback, arg1: MutPtr, arg2: MutPtr): CInt
}

public fun register(t: MutPtr, dtor: RegisterDtorCallback) {
    open class CInt(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<CInt>,
    ) {
        public var field0: CInt
            get() = value
            set(v) {
                value = v
            }

        public var value: CInt
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(value: CInt, fieldLayout: TypeLayout<CInt>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

        public fun pointerLayout(): TypeLayout<CInt> = TransparentTypeLayout(fieldLayout, { ptr, layout -> CInt(ptr, layout) }, { it.value })
    }
    val DSO_HANDLE: MutPtr = MutPtr(0L)

    val CXA_THREAD_ATEXIT_IMPL: FnCallback1? = null
    if (CXA_THREAD_ATEXIT_IMPL != null && DSO_HANDLE != null && !dso.isNull()) {
        run {
            f(transmute(dtor), t, mutPtrFromRustCast(dso))
        }
    } else {
        run {
            io.github.kotlinmania.rustlang.sys.threadlocal.destructors.linuxlike.register(t, dtor)
        }
    }
}
