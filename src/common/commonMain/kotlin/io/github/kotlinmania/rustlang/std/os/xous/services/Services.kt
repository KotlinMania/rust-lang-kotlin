// port-lint: source os/xous/services.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.os.xous.services

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Connection
import io.github.kotlinmania.rustlang.std.os.xous.ffi.lendMut

public val NAME_MAX_LENGTH: Int = 64

// By making this repr(C), the layout of this struct becomes well-defined
// and no longer shifts around.
// By marking it as `align(4096)` we define that it will be page-aligned,
// meaning it can be sent between processes. We make sure to pad out the
// entire struct so that memory isn't leaked to the name server.
public open class ConnectRequest internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var data: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(data: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 4096L)), AlignedTypeLayout(fieldLayout, 4096L))

    public fun pointerLayout(): TypeLayout<ConnectRequest> = TransparentTypeLayout(fieldLayout, { ptr, layout -> ConnectRequest(ptr, layout) }, { it.data })

    public companion object {
        public operator fun invoke(data: UByteArray, fieldLayout: TypeLayout<UByteArray>): ConnectRequest = ConnectRequest(data, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): ConnectRequest = ConnectRequest(fieldStorage, fieldLayout)

        public fun new(name: String, fieldLayout: TypeLayout<UByteArray>): ConnectRequest {
            val cr = ConnectRequest(data = UByteArray(4096) { 0u.toUByte() }, fieldLayout = PrimitiveArrayLayouts.uByteArray((4096).toInt()))
            val nameBytes = name.encodeToByteArray().map { it.toUByte() }
            // Copy the string into our backing store.
            for ((srcByte, destByte) in run { val subSlice0 = cr.data.sliceArray(0 until NAME_MAX_LENGTH); val subCall0 = (nameBytes.zip(subSlice0)); subSlice0.copyInto(cr.data, destinationOffset = 0); subCall0 }) {
                destByte = srcByte
            }
            // Set the string length to the length of the passed-in String,
            // or the maximum possible length. Which ever is smaller.
            for ((srcByte1, destByte1) in run { val subSlice0 = cr.data.sliceArray(NAME_MAX_LENGTH until cr.data.size); val subCall0 = (run { val rawBits = (name.length.min(NAME_MAX_LENGTH).toUInt()); ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) }.zip(subSlice0)); subSlice0.copyInto(cr.data, destinationOffset = NAME_MAX_LENGTH); subCall0 }) {
                destByte1 = srcByte1
            }
            return cr
        }
    }
}

public fun connectWithNameImpl(name: String, blocking: Boolean): Connection? {
    val request = ConnectRequest.new(name)
    val opcode =
        if (blocking) {
            6
            // BlockingConnect
        } else {
            7
            // TryConnect
        }
    val cid =
        if (blocking) {
            nameServer()
        } else {
            (tryNameServer() ?: return null)
        }
    lendMut(cid, opcode, request.data, 0, name.length.min(NAME_MAX_LENGTH)).expect("unable to perform lookup")
    // Read the result code back from the nameserver
    val result = run { val bytesTmp0 = ((request.data).sliceArray(0 until 4)); ((bytesTmp0[3].toUInt() shl 24) or (bytesTmp0[2].toUInt() shl 16) or (bytesTmp0[1].toUInt() shl 8) or bytesTmp0[0].toUInt()) }
    return if (result == 0u) {
        // If the result was successful, then the CID is stored in the next 4 bytes
        run { val bytesTmp1 = ((request.data).sliceArray(4 until 8)); ((bytesTmp1[3].toUInt() shl 24) or (bytesTmp1[2].toUInt() shl 16) or (bytesTmp1[1].toUInt() shl 8) or bytesTmp1[0].toUInt()) }.into()
    } else {
        null
    }
}

public fun connectWithName(name: String): Connection? = connectWithNameImpl(name, true)

public fun tryConnectWithName(name: String): Connection? = connectWithNameImpl(name, false)

/**
 * Attempts to connect to a server by name. If the server does not exist, this will
 * block until the server is created.
 *
 * Note that this is different from connecting to a server by address. Server
 * addresses are always 16 bytes long, whereas server names are arbitrary-length
 * strings up to 64 bytes in length.
 */
public fun connect(name: String): Connection? = connectWithName(name)

/**
 * Attempts to connect to a server by name. If the server does not exist, this will
 * immediately return `None`.
 *
 * Note that this is different from connecting to a server by address. Server
 * addresses are always 16 bytes long, whereas server names are arbitrary-length
 * strings.
 */
public fun tryConnect(name: String): Connection? = tryConnectWithName(name)

public var NAME_SERVER_CONNECTION: Atomic<UInt> = AtomicU32.new(0)

/**
 * Returns a `Connection` to the name server. If the name server has not been started,
 * then this call will block until the name server has been started. The `Connection`
 * will be shared among all connections in a process, so it is safe to call this
 * multiple times.
 */
internal fun nameServer(): Connection {
    val cid = NAME_SERVER_CONNECTION.load(Ordering.Relaxed)
    if (cid != 0) {
        return cid.into()
    }
    val cid1 = io.github.kotlinmania.rustlang.std.os.xous.ffi.connect("xous-name-server".tryInto().unwrap()).unwrap()
    NAME_SERVER_CONNECTION.store(cid1.into(), Ordering.Relaxed)
    return cid1
}

public fun tryNameServer(): Connection? {
    val cid = NAME_SERVER_CONNECTION.load(Ordering.Relaxed)
    if (cid != 0) {
        return cid.into()
    }
    return run {
        val matchVal = io.github.kotlinmania.rustlang.std.os.xous.ffi.tryConnect("xous-name-server".tryInto().unwrap())
        if (matchVal is Result.Ok<*> && matchVal.value != null) {
            NAME_SERVER_CONNECTION.store(cid.into(), Ordering.Relaxed)
            cid
        } else {
            null
        }
    }
}
