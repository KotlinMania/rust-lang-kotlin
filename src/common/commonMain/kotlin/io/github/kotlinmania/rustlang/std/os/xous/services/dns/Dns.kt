// port-lint: source os/xous/services/dns.rs
package io.github.kotlinmania.rustlang.std.os.xous.services.dns

import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Connection
import io.github.kotlinmania.rustlang.std.os.xous.services.connect

internal sealed class DnsLendMut(
    public val value: Int,
) {
    public data object RawLookup : DnsLendMut(6)

    public open fun into(): Int = this.toInt()
}

/**
 * Returns a `Connection` to the DNS lookup server. This server is used for
 * querying domain name values.
 */
internal fun dnsServer(): Connection {
    val DNS_CONNECTION: Atomic<UInt>  = AtomicU32.new(0)
    val cid = DNS_CONNECTION.load(Ordering.Relaxed)
    if (cid != 0) {
        return cid.into()
    }
    val cid1 = connect("_DNS Resolver Middleware_").unwrap()
    DNS_CONNECTION.store(cid1.into(), Ordering.Relaxed)
    return cid1
}
