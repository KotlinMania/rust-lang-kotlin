// port-lint: source sys/sync/futex/windows.rs
package io.github.kotlinmania.rustlang.std.sys.sync.futex.windows

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.getLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.INFINITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.TRUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.dur2timeout

/**
 * An atomic for use as a futex that is at least 32-bits but may be larger
 */
public typealias Futex = Atomic<Primitive>

/**
 * Must be the underlying type of Futex
 */
public typealias Primitive = UInt

/**
 * An atomic for use as a futex that is at least 8-bits but may be larger.
 */
public typealias SmallFutex = Atomic<SmallPrimitive>

/**
 * Must be the underlying type of SmallFutex
 */
public typealias SmallPrimitive = UByte

public interface Futexable

public interface Waitable<out Futex>

public fun <Futex, W> waitOnAddress(address: Futex, compare: W, timeout: Duration?, typeLayoutW: TypeLayout<W>): Boolean =
    run {
        val addr = address.cast()
        val size = sizeOf<W>(typeLayoutT = typeLayoutW)
        val compareAddr = ConstPtr(((addrOf(compare))).rawAddress)
        val timeout1 = (timeout?.let(dur2timeout) ?: INFINITE)
        waitOnAddress(addr, compareAddr, size, timeout1) == TRUE
    }

public fun <T : Futexable> wakeByAddressSingle(address: T) {
    run {
        val addr = address.cast()
        wakeByAddressSingle(addr)
    }
}

public fun <T : Futexable> wakeByAddressAll(address: T) {
    run {
        val addr = address.cast()
        wakeByAddressAll(addr)
    }
}

public fun <Futex, W> futexWait(futex: Futex, expected: W, timeout: Duration?): Boolean {
    // return false only on timeout
    return (waitOnAddress(futex, expected, timeout, typeLayoutW = ObjectSlotLayout<W>()) || getLastError() != WinError.TIMEOUT).toUByte()
}

public fun <T : Futexable> futexWake(futex: T): Boolean {
    wakeByAddressSingle(futex)
    return false
}

public fun <T : Futexable> futexWakeAll(futex: T) {
    wakeByAddressAll(futex)
}
