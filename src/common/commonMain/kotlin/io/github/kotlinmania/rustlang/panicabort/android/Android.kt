// port-lint: source android.rs
package io.github.kotlinmania.rustlang.panicabort.android

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.libc.dlsym
import io.github.kotlinmania.rustlang.libc.malloc
import io.github.kotlinmania.rustlang.libc.unix.bsd.RTLD_DEFAULT

public val ANDROID_SET_ABORT_MESSAGE: List<UByte> = "android_set_abort_message\u0000".encodeToByteArray().map { it.toUByte() }

public fun interface SetAbortMessageType {
    public operator fun invoke(arg0: ConstPtr)
}

// Forward the abort message to libc's androidSetAbortMessage. We try our best to populate the
// message but as this function may already be called as part of a failed allocation, it might not be
// possible to do so.
//
// Some methods of core are on purpose avoided (such as tryReserve) as these rely on the correct
// resolution of rustEhPersonality which is loosely defined in panicAbort.
//
// Weakly resolve the symbol for androidSetAbortMessage. This function is only available
// for API >= 21.
public fun androidSetAbortMessage(payload: PanicPayload) {
    val funcAddr = dlsym(RTLD_DEFAULT, (ANDROID_SET_ABORT_MESSAGE.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).toInt()
    if (funcAddr == 0) {
        return
    }
    val payload1 = payload.get()
    val msg2 =
        when (val matchVal = payload1.downcastRef<String>()) {
            null -> when (val matchVal1 = payload1.downcastRef<String>()) {
                null -> listOf()
                else -> {
                    val msg = matchVal1
                    msg.encodeToByteArray().map { it.toUByte() }
                }
            }
            else -> {
                val msg1 = matchVal
                msg1.encodeToByteArray().map { it.toUByte() }
            }
        }
    if (msg2.isEmpty()) {
        return
    }
    // Allocate a new buffer to append the null byte.
    val size = msg2.len() + 1
    val buf = mutPtrFromRustCast(malloc(size))
    if (((buf).rawAddress == 0L)) {
        return
        // allocation failure
    }
    run {
        copyNonoverlapping(msg2.asPtr(), MutPtr(buf.rawAddress, buf.rawMeta), msg2.len())
        (UnresolvedTypeLayout<CChar>()).writeNative((MutPtr((buf).rawAddress + ((msg2.len()).toLong() * (UnresolvedTypeLayout<CChar>()).strideBytes))).rawAddress, 0)
        val func = transmuteWithLayouts<Int, SetAbortMessageType>(funcAddr, PrimitiveLayouts.USIZE64, UnresolvedTypeLayout<SetAbortMessageType>())
        func(buf)
    }
}
