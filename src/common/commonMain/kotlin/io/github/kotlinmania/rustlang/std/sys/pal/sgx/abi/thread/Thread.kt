// port-lint: source sys/pal/sgx/abi/thread.rs
package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.thread

import io.github.kotlinmania.fortanixsgxabi.Tcs
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.stdio.motor.panicOutput

/**
 * Gets the ID for the current thread. The ID is guaranteed to be unique among
 * all currently running threads in the enclave, and it is guaranteed to be
 * constant for the scope of the thread. More specifically for SGX, there
 * is a one-to-one correspondence of the ID to the address of the TCS.
 */
public fun current(): Tcs {
    fun getTcsAddr(): MutPtr = MutPtr(0L)
    val addr = getTcsAddr()
    return when (val matchVal = Tcs.new(addr)) {
        null -> {
            when (val matchVal1 = panicOutput()) {
                null -> {}
                else -> {
                    val out = matchVal1
                    Write.writeFmt(out, "fatal runtime error: " + ("TCS must not be placed at address zero (this is a linker error)").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("TCS must not be placed at address zero (this is a linker error)").toString() + ", aborting\n"
            }
            abort()
        }
        else -> {
            val tcs = matchVal
            tcs
        }
    }
}
