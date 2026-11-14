// port-lint: source sys/thread/wasm.rs
package io.github.kotlinmania.rustlang.std.sys.thread.wasm

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.time.Duration

public fun sleep(dur: Duration) {
    // Use an atomic wait to block the current thread artificially with a
    // timeout listed. Note that we should never be notified (return value
    // of 0) or our comparison should never fail (return value of 1) so we
    // should always only resume execution through a timeout (return value
    // 2).
    var nanos = dur.asNanos()
    while (nanos > 0) {
        val amt = minOf(U128.fromLong(Long.MAX_VALUE), nanos)
        val localStorageL18C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        val value = wasm.memoryAtomicWait32(RustMutableReference(localStorageL18C8.asMutPtr(), localStorageL18C8.layout), 0, amt.toLong())
        check(value == 2)
        nanos -= amt
    }
}
