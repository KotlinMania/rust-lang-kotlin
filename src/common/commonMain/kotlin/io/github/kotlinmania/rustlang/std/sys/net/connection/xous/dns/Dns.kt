// port-lint: source sys/net/connection/xous/dns.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.net.connection.xous.dns

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV4
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV6
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.os.xous.ffi.lendMut
import io.github.kotlinmania.rustlang.std.os.xous.services.dns.DnsLendMut
import io.github.kotlinmania.rustlang.std.os.xous.services.dns.dnsServer

public open class LookupHostQuery internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var field0: UByteArray
        get() = value
        set(v) {
            value = v
        }

    public var value: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 4096L)), AlignedTypeLayout(fieldLayout, 4096L))

    public fun pointerLayout(): TypeLayout<LookupHostQuery> = TransparentTypeLayout(fieldLayout, { ptr, layout -> LookupHostQuery(ptr, layout) }, { it.value })

    public companion object {
        public operator fun invoke(value: UByteArray, fieldLayout: TypeLayout<UByteArray>): LookupHostQuery = LookupHostQuery(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): LookupHostQuery = LookupHostQuery(fieldStorage, fieldLayout)
    }
}

public open class LookupHost(
    public var data: LookupHostQuery,
    public var port: UShort,
    public var offset: Int,
    public var count: Int,
) : Iterable<SocketAddr> {
    override fun iterator(): kotlin.collections.Iterator<SocketAddr> = object : AbstractIterator<SocketAddr>() {
        override fun computeNext() {
            when (val v = this@LookupHost.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): SocketAddr? {
        if (this.offset >= this.data.value.size) {
            return null
        }
        return when (val matchVal = this.data.value.get(this.offset)) {
            4 -> {
                this.offset += 1
                if (this.offset + 4 > this.data.value.size) {
                    return null
                }
                val result = SocketAddr.v4(SocketAddrV4.new(Ipv4Addr.new(this.data.value[this.offset], this.data.value[this.offset + 1], this.data.value[this.offset + 2], this.data.value[this.offset + 3]), this.port))
                this.offset += 4
                result
            }
            6 -> {
                this.offset += 1
                if (this.offset + 16 > this.data.value.size) {
                    return null
                }
                val newAddr = UByteArray(16) { 0u.toUByte() }
                for ((src, octet) in (((this.data.value).sliceArray(this.offset until (this.offset + 16))).zip(newAddr))) {
                    octet = src
                }
                val result1 = SocketAddr.v6(SocketAddrV6.new(newAddr.into(), this.port, 0, 0))
                this.offset += 16
                result1
            }
            else -> null
        }
    }
}

public fun lookupHost(query: String, port: UShort): Result<LookupHost> {
    val result = LookupHost(data = LookupHostQuery(UByteArray(4096) { 0u.toUByte() }, fieldLayout = PrimitiveArrayLayouts.uByteArray((4096).toInt())), offset = 0, count = 0, port = port)
    // Copy the query into the message that gets sent to the DNS server
    for ((queryByte, resultByte) in (query.encodeToByteArray().map { it.toUByte() }.zip(result.data.value))) {
        resultByte = queryByte
    }
    lendMut(dnsServer(), DnsLendMut.RawLookup.into(), result.data.value, 0, (query.encodeToByteArray().map { it.toUByte() }).size).unwrap()
    if (result.data.value[0] != 0u.toUByte()) {
        return Result.Err(io.constError(io.ErrorKind.InvalidInput, "DNS failure"))
    }
    check(result.offset == 0)
    result.count = result.data.value[1].toInt()
    // Advance the offset to the first record
    result.offset = 2
    return Result.Ok(result)
}
