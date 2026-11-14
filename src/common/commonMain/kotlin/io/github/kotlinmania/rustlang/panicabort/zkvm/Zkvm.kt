// port-lint: source zkvm.rs
package io.github.kotlinmania.rustlang.panicabort.zkvm

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

// Forward the abort message to zkVM's sysPanic. This is implemented by RISC Zero's
// platform crate which exposes system calls specifically for the zkVM.
internal fun zkvmSetAbortMessage(payload: PanicPayload) {
    fun sysPanic(msgPtr: ConstPtr, len: ULong): Nothing = null as Nothing
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
    run {
        sysPanic(msg2.asPtr(), msg2.len())
    }
}
