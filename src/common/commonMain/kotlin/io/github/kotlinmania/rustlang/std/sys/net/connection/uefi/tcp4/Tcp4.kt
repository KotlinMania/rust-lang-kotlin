// port-lint: source sys/net/connection/uefi/tcp4.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.net.connection.uefi.tcp4

import io.github.kotlinmania.refi.efi
import io.github.kotlinmania.refi.efi.Event
import io.github.kotlinmania.refi.efi.status
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV4
import io.github.kotlinmania.rustlang.core.num.rustCheckedAdd
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.OwnedEvent
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.ServiceProtocol
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.UefiBox
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.ipv4ToREfi
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.openProtocol
import io.github.kotlinmania.rustlang.std.sys.thread.sgx.waitnotify.new
import io.github.kotlinmania.rustlang.std.time.Instant

public val TYPE_OF_SERVICE: UByte = 8u

public val TIME_TO_LIVE: UByte = 255u

internal open class Tcp4(
    public var handle: NonNull,
    public var protocol: NonNull,
    public var flag: AtomicBool,
    public var serviceBinding: ServiceProtocol,
) {
    internal open fun configure(active: Boolean, remoteAddress: SocketAddrV4?, stationAddress: SocketAddrV4?): Result<Unit> {
        val protocol = this.protocol.asPtr()
        val (remoteAddress1, remotePort) =
            if (remoteAddress != null) {
                val x = remoteAddress
                Pair(ipv4ToREfi(x.ip()), x.port())
            } else {
                Pair(DEFAULT_ADDR, 0)
            }
        val useDefaultAddress: io.github.kotlinmania.refi.efi.Boolean = stationAddress.isNoneOr({ addr -> addr.ip().isUnspecified() }).into()
        val (stationAddress1, stationPort) =
            if (stationAddress != null) {
                val x1 = stationAddress
                Pair(ipv4ToREfi(x1.ip()), x1.port())
            } else {
                Pair(DEFAULT_ADDR, 0)
            }
        val subnetMask = ipv4ToREfi(new(255, 255, 255, 0))
        val configData = Tcp4.ConfigData(typeOfService = TYPE_OF_SERVICE, timeToLive = TIME_TO_LIVE, accessPoint = Tcp4.AccessPoint(useDefaultAddress = useDefaultAddress, remoteAddress = remoteAddress1, remotePort = remotePort, activeFlag = active.into(), stationAddress = stationAddress1, stationPort = stationPort, subnetMask = subnetMask), controlOption = MutPtr(0L))
        val r = (protocol.configure)(protocol, configData)
        return if (r.isError()) {
            Result.Err(Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    public open fun getModeDataInto(configData: ConfigData): Result<Unit> {
        val protocol = this.protocol.asPtr()
        val r = (protocol.getModeData)(protocol, MutPtr(0L), configData, MutPtr(0L), MutPtr(0L), MutPtr(0L))
        return if (r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    internal open fun getModeData(): Result<ConfigData> {
        val configData = Tcp4.ConfigData.default()
        when (val tryResult = this.getModeDataInto(configData)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(configData)
    }

    internal open fun getControlOption(): Result<Option> {
        var controlOption = DEFAULT_CONTROL_OPTION
        val configData = Default.default().copy(controlOption = controlOption)
        when (val tryResult = this.getModeDataInto(configData)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(controlOption)
    }

    internal open fun accept(): Result<Tcp4> {
        val evt = when (val tryResult = this.createEvt()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val completionToken = Tcp4.CompletionToken(event = evt.asPtr(), status = Status.SUCCESS)
        val listenToken = Tcp4.ListenToken(completionToken = completionToken, newChildHandle = MutPtr(0L))
        val protocol = this.protocol.asPtr()
        val r = (protocol.accept)(protocol, listenToken)
        if (r.isError()) {
            return Result.Err(io.Error.fromRawOsError(r.asUsize()))
        }
        when (val tryResult = this.waitOrCancel(null, listenToken.completionToken)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return if (completionToken.status.isError()) {
            Result.Err(io.Error.fromRawOsError(completionToken.status.asUsize()))
        } else {
            // EDK2 internals seem to assume a single ServiceBinding Protocol for TCP4 and TCP6, and
            // thus does not use any service binding protocol data in destroying child sockets. It
            // does seem to suggest that we need to cleanup even the protocols created by accept. To
            // be on the safe side with other implementations, we will be using the same service
            // binding protocol as the parent TCP4 handle.
            //
            // https://github.com/tianocore/edk2/blob/f80580f56b267c96f16f985dbf707b2f96947da4/NetworkPkg/TcpDxe/TcpDriver.c#L938
            val handle = NonNull.new(listenToken.newChildHandle).unwrap()
            val protocol1 = when (val tryResult = openProtocol(handle, Tcp4.PROTOCOL_GUID)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            Result.Ok(Tcp4(handle = handle, serviceBinding = this.serviceBinding, protocol = protocol1, flag = AtomicBool.new(false)))
        }
    }

    internal open fun connect(timeout: Duration?): Result<Unit> {
        val evt = when (val tryResult = this.createEvt()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val completionToken = Tcp4.CompletionToken(event = evt.asPtr(), status = Status.SUCCESS)
        val protocol = this.protocol.asPtr()
        val connToken = Tcp4.ConnectionToken(completionToken = completionToken)
        val r = (protocol.connect)(protocol, connToken)
        if (r.isError()) {
            return Result.Err(io.Error.fromRawOsError(r.asUsize()))
        }
        when (val tryResult = this.waitOrCancel(timeout, connToken.completionToken)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return if (completionToken.status.isError()) {
            Result.Err(io.Error.fromRawOsError(completionToken.status.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    internal open fun write(buf: List<UByte>, timeout: Duration?): Result<Int> {
        val dataLen = UInt.tryFrom(buf.size).unwrapOr(UInt.MAX_VALUE)
        val fragment = Tcp4.FragmentData(fragmentLength = dataLen, fragmentBuffer = MutPtr((ConstPtr((buf.asPtr()).rawAddress)).rawAddress))
        val localStorageL189C8 = AddressableLocal(Tcp4.TransmitData(push = io.github.kotlinmania.refi.efi.Boolean.FALSE, urgent = io.github.kotlinmania.refi.efi.Boolean.FALSE, dataLength = dataLen, fragmentCount = 1, fragmentTable = listOf(fragment)), ObjectSlotLayout<TransmitData>())
        return this.writeInner((localStorageL189C8.asMutPtr()), timeout).map({ dataLen.toInt() })
    }

    internal open fun writeVectored(buf: List<IoSlice>, timeout: Duration?): Result<Int> {
        var dataLength = 0u
        var fragmentCount = 0u
        // Calculate how many IoSlice in buf can be transmitted.
        for (i in buf) {
            // IoSlice length is always <= u32.Max in UEFI.
            when (val matchVal = rustCheckedAdd(dataLength, UInt.tryFrom(i.asSlice().len()).expect("value is stored as a u32").toUInt())) {
                null -> { break }
                else -> {
                    val x = matchVal
                    dataLength = x
                }
            }
            fragmentCount += 1u
        }
        val txDataSize = sizeOf<TransmitData>(typeLayoutT = ObjectSlotLayout<TransmitData>()) + sizeOf<FragmentData>(typeLayoutT = ObjectSlotLayout<FragmentData>()) * (fragmentCount.toInt())
        val txData = when (val tryResult = UefiBox.new(txDataSize)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        txData.write(Tcp4.TransmitData(push = io.github.kotlinmania.refi.efi.Boolean.FALSE, urgent = io.github.kotlinmania.refi.efi.Boolean.FALSE, dataLength = dataLength, fragmentCount = fragmentCount, fragmentTable = listOf()))
        run {
            // SAFETY: IoSlice and FragmentData are guaranteed to have same layout.
            copyNonoverlapping(buf.asPtr(), txData.asMutPtr().fragmentTable.asMutPtr(), fragmentCount.toInt())
        }
        return this.writeInner(txData.asMutPtr(), timeout).map({ dataLength.toInt() })
    }

    public open fun writeInner(txData: MutPtr, timeout: Duration?): Result<Unit> {
        val evt = when (val tryResult = this.createEvt()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val completionToken = Tcp4.CompletionToken(event = evt.asPtr(), status = Status.SUCCESS)
        val protocol = this.protocol.asPtr()
        val token = Tcp4.IoToken(completionToken = completionToken, packet = Tcp4.IoTokenPacket(txData = txData))
        val r = (protocol.transmit)(protocol, token)
        if (r.isError()) {
            return Result.Err(io.Error.fromRawOsError(r.asUsize()))
        }
        when (val tryResult = this.waitOrCancel(timeout, token.completionToken)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return if (completionToken.status.isError()) {
            Result.Err(io.Error.fromRawOsError(completionToken.status.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    internal open fun read(buf: MutableList<UByte>, timeout: Duration?): Result<Int> {
        val dataLen = UInt.tryFrom(buf.size).unwrapOr(UInt.MAX_VALUE)
        val fragment = Tcp4.FragmentData(fragmentLength = dataLen, fragmentBuffer = MutPtr((buf.asMutPtr()).rawAddress))
        val localStorageL275C8 = AddressableLocal(Tcp4.ReceiveData(urgentFlag = io.github.kotlinmania.refi.efi.Boolean.FALSE, dataLength = dataLen, fragmentCount = 1, fragmentTable = listOf(fragment)), ObjectSlotLayout<ReceiveData>())
        return this.readInner((localStorageL275C8.asMutPtr()), timeout)
    }

    internal open fun readVectored(buf: List<IoSliceMut>, timeout: Duration?): Result<Int> {
        var dataLength = 0u
        var fragmentCount = 0u
        // Calculate how many IoSlice in buf can be transmitted.
        for (i in buf) {
            // IoSlice length is always <= u32.Max in UEFI.
            when (val matchVal = rustCheckedAdd(dataLength, UInt.tryFrom(i.len()).expect("value is stored as a u32").toUInt())) {
                null -> { break }
                else -> {
                    val x = matchVal
                    dataLength = x
                }
            }
            fragmentCount += 1u
        }
        val rxDataSize = sizeOf<ReceiveData>(typeLayoutT = ObjectSlotLayout<ReceiveData>()) + sizeOf<FragmentData>(typeLayoutT = ObjectSlotLayout<FragmentData>()) * (fragmentCount.toInt())
        val rxData = when (val tryResult = UefiBox.new(rxDataSize)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        rxData.write(Tcp4.ReceiveData(urgentFlag = io.github.kotlinmania.refi.efi.Boolean.FALSE, dataLength = dataLength, fragmentCount = fragmentCount, fragmentTable = listOf()))
        run {
            // SAFETY: IoSlice and FragmentData are guaranteed to have same layout.
            copyNonoverlapping(buf.asPtr(), rxData.asMutPtr().fragmentTable.asMutPtr(), fragmentCount.toInt())
        }
        return this.readInner(rxData.asMutPtr(), timeout)
    }

    internal open fun readInner(rxData: MutPtr, timeout: Duration?): Result<Int> {
        val evt = when (val tryResult = this.createEvt()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val completionToken = Tcp4.CompletionToken(event = evt.asPtr(), status = Status.SUCCESS)
        val protocol = this.protocol.asPtr()
        val token = Tcp4.IoToken(completionToken = completionToken, packet = Tcp4.IoTokenPacket(rxData = rxData))
        val r = (protocol.receive)(protocol, token)
        if (r.isError()) {
            return Result.Err(io.Error.fromRawOsError(r.asUsize()))
        }
        when (val tryResult = this.waitOrCancel(timeout, token.completionToken)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return if (completionToken.status.isError()) {
            Result.Err(io.Error.fromRawOsError(completionToken.status.asUsize()))
        } else {
            val dataLength = (ObjectSlotLayout<ReceiveData>()).readNative((rxData).rawAddress).dataLength
            Result.Ok(dataLength.toInt())
        }
    }

    /**
     * Wait for an event to finish. This is checked by an atomic boolean that is supposed to be set
     * to true in the event callback.
     *
     * Optionally, allow specifying a timeout.
     *
     * If a timeout is provided, the operation (specified by its `EFI_TCP4_COMPLETION_TOKEN`) is
     * canceled and Error of kind TimedOut is returned.
     *
     * # SAFETY
     *
     * Pointer to a valid `EFI_TCP4_COMPLETION_TOKEN`
     */
    public open fun waitOrCancel(timeout: Duration?, token: MutPtr): Result<Unit> {
        if (!this.waitForFlag(timeout)) {
            this.cancel(token)
            return Result.Err(io.Error.new(io.ErrorKind.TimedOut, "Operation Timed out"))
        }
        return Result.Ok(Unit)
    }

    /**
     * Abort an asynchronous connection, listen, transmission or receive request.
     *
     * If token is NULL, then all pending tokens issued by EFI_TCP4_PROTOCOL.Connect(),
     * EFI_TCP4_PROTOCOL.Accept(), EFI_TCP4_PROTOCOL.Transmit() or EFI_TCP4_PROTOCOL.Receive() are
     * aborted.
     *
     * # SAFETY
     *
     * Pointer to a valid `EFI_TCP4_COMPLETION_TOKEN` or NULL
     */
    public open fun cancel(token: MutPtr): Result<Unit> {
        val protocol = this.protocol.asPtr()
        val r = (protocol.cancel)(protocol, token)
        return if (r.isError()) {
            return Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    public open fun createEvt(): Result<OwnedEvent> {
        this.flag.store(false, Ordering.Relaxed)
        return OwnedEvent.new(efi.EVT_NOTIFY_SIGNAL, efi.TPL_CALLBACK, ::toggleAtomicFlag, NonNull.newUnchecked(this.flag.asPtr().cast()))
    }

    public open fun waitForFlag(timeout: Duration?): Boolean {
        val start = Instant.now()
        while (!this.flag.load(Ordering.Relaxed)) {
            this.poll()
            if (timeout != null) {
                val t = timeout
                if (Instant.now().durationSince(start) >= t) {
                    return false
                }
            }
        }
        return true
    }

    public open fun poll(): Result<Unit> {
        val protocol = this.protocol.asPtr()
        val r = (protocol.poll)(protocol)
        return if (r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    public open fun drop() {
        this.serviceBinding.destroyChild(this.handle)
    }

    public companion object {
        internal fun new(): Result<Tcp4> {
            val (serviceBinding, handle) = when (val tryResult = ServiceProtocol.open(Tcp4.SERVICE_BINDING_PROTOCOL_GUID)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val protocol = when (val tryResult = openProtocol(handle, Tcp4.PROTOCOL_GUID)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(Tcp4(serviceBinding = serviceBinding, handle = handle, protocol = protocol, flag = AtomicBool.new(false)))
        }
    }
}

public val DEFAULT_ADDR: efi.Ipv4Address = efi.Ipv4Address(addr = UByteArray(4) { 0u.toUByte() })

public val DEFAULT_CONTROL_OPTION: Option = Tcp4.Option(receiveBufferSize = 0, sendBufferSize = 0, maxSynBackLog = 0, connectionTimeout = 0, dataRetries = 0, finTimeout = 0, timeWaitTimeout = 0, keepAliveProbes = 0, keepAliveTime = 0, keepAliveInterval = 0, enableNagle = efi.Boolean.FALSE, enableTimeStamp = efi.Boolean.FALSE, enableWindowScaling = efi.Boolean.FALSE, enableSelectiveAck = efi.Boolean.FALSE, enablePathMtuDiscovery = efi.Boolean.FALSE)

public fun toggleAtomicFlag(arg0: Event, ctx: MutPtr) {
    val flag = AtomicBool.fromPtr(ctx)
    flag.store(true, Ordering.Relaxed)
}
