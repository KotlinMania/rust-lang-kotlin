// port-lint: source os/xous/services/systime.rs
package io.github.kotlinmania.rustlang.std.os.xous.services.systime

import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.os.xous.ffi.connect
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Connection

internal sealed class SystimeScalar {
    public data object GetUtcTimeMs : SystimeScalar()

    public open fun into(): IntArray =
        when (this) {
            SystimeScalar.GetUtcTimeMs -> listOf(3, 0, 0, 0, 0)
        }
}

/**
 * Returns a `Connection` to the systime server. This server is used for reporting the
 * realtime clock.
 */
internal fun systimeServer(): Connection {
    val SYSTIME_SERVER_CONNECTION: Atomic<UInt>  = AtomicU32.new(0)
    val cid = SYSTIME_SERVER_CONNECTION.load(Ordering.Relaxed)
    if (cid != 0) {
        return cid.into()
    }
    val cid1 = connect("timeserverpublic".tryInto().unwrap()).unwrap()
    SYSTIME_SERVER_CONNECTION.store(cid1.into(), Ordering.Relaxed)
    return cid1
}
