// port-lint: source backtrace/win64.rs
package io.github.kotlinmania.rustlang.backtrace.backtrace.win64

import io.github.kotlinmania.rustlang.backtrace.windowssys.Context
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Backtrace strategy for Windows `x8664` and `aarch64` platforms.
//
// This module contains the ability to capture a backtrace on Windows using
//  `RtlVirtualUnwind` to walk the stack one frame at a time. This function is much faster than using
// `dbghelp StackWalk *` because it does not load debug info to report inlined frames.
// We still report inlined frames during symbolization by consulting the appropriate
// `dbghelp` functions.

public open class Frame internal constructor(
    baseAddressCtorArg: MutPtr,
    ipCtorArg: MutPtr,
    spCtorArg: MutPtr,
    public var inlineContext: UInt?,
) {
    public var baseAddress: MutPtr = baseAddressCtorArg
        internal set

    public var ip: MutPtr = ipCtorArg
        internal set

    public var sp: MutPtr = spCtorArg
        internal set

    public open fun clone(): Frame = Frame(baseAddress, ip, sp, inlineContext)

    // we're just sending around raw pointers and reading them, never interpreting
    // them so this should be safe to both send and share across threads.

    public open fun ip(): MutPtr = this.ip

    public open fun sp(): MutPtr = this.sp

    public open fun symbolAddress(): MutPtr = this.ip

    public open fun moduleBaseAddress(): MutPtr? = this.baseAddress

    public open fun inlineContext(): UInt? = this.inlineContext

    public companion object {
        public operator fun invoke(
            baseAddress: MutPtr,
            ip: MutPtr,
            sp: MutPtr,
            inlineContext: UInt?,
        ): Frame = Frame(
            baseAddressCtorArg = baseAddress,
            ipCtorArg = ip,
            spCtorArg = sp,
            inlineContext = inlineContext,
        )
    }
}

// required by `CONTEXT`, is a Note in windows metadata right now
public open class MyContext internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Context> = UnresolvedTypeLayout(),
) {
    public var field0: Context
        get() = value
        set(v) {
            value = v
        }

    public var value: Context
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: Context, fieldLayout: TypeLayout<Context> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<MyContext> = TransparentTypeLayout(fieldLayout, { ptr, layout -> MyContext(ptr, layout) }, { it.value })

    public open fun ip(): ULong = this.value.rip

    public open fun sp(): ULong = this.value.rsp

    public companion object {
        public operator fun invoke(value: Context, fieldLayout: TypeLayout<Context> = UnresolvedTypeLayout()): MyContext = MyContext(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Context> = UnresolvedTypeLayout()): MyContext = MyContext(fieldStorage, fieldLayout)
    }
}

public fun trace(cb: (io.github.kotlinmania.rustlang.backtrace.backtrace.Frame) -> Boolean) {
    // Capture the initial context to start walking from.
    // Note: shouldn't this have a Default impl?
    val context = null
    RtlCaptureContext(context.first)
    while (true) {
        val ip = context.ip()
        // The base address of the module containing the function will be stored here
        // when RtlLookupFunctionEntry returns successfully.
        val localStorageL92C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        val base by localStorageL92C8
        // We use the `RtlLookupFunctionEntry` function in kernel32 which allows
        // us to backtrace through JIT frames.
        // Note that `RtlLookupFunctionEntry` only works for in-process backtraces,
        // but that's all we support anyway, so it all lines up well.
        val fnEntry = RtlLookupFunctionEntry(ip, RustMutableReference(localStorageL92C8.asMutPtr(), localStorageL92C8.layout), MutPtr(0L))
        if (fnEntry.isNull()) {
            // No function entry could be found - this may indicate a corrupt
            // stack or that a binary was unloaded (amongst other issues). Stop
            // walking and don't call the callback as we can't be confident in
            // this frame or the rest of the stack.
            break
        }
        val frame = io.github.kotlinmania.rustlang.backtrace.backtrace.Frame(inner = Frame(baseAddress = MutPtr(base.toLong()), ip = mutPtrFromRustCast(ip), sp = mutPtrFromRustCast(context.sp()), inlineContext = null))
        // We've loaded all the info about the current frame, so now call the
        // callback.
        if (!cb(frame)) {
            // Callback told us to stop, so we're done.
            break
        }
        // Unwind to the next frame.
        val previousIp = ip
        val previousSp = context.sp()
        val handlerData = 0
        val localStorageL127C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        run {
            RtlVirtualUnwind(0, base, ip, fnEntry, context.first, addrOfMut(handlerData).cast(), RustMutableReference(localStorageL127C8.asMutPtr(), localStorageL127C8.layout), MutPtr(0L))
        }
        // RtlVirtualUnwind indicates the end of the stack in two different ways:
        // * On x64, it sets the instruction pointer to 0.
        // * On ARM64, it leaves the context unchanged (easiest way to check is
        // to see if the instruction and stack pointers are the same).
        // If we detect either of these, then unwinding is completed.
        val ip1 = context.ip()
        if (ip1 == 0 || (ip1 == previousIp && context.sp() == previousSp)) {
            break
        }
    }
}
