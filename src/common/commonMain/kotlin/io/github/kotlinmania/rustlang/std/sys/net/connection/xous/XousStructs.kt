@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.net.connection.xous

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public open class ConnectRequest internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var raw: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(raw: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(raw, AlignedTypeLayout(fieldLayout, 4096L)), AlignedTypeLayout(fieldLayout, 4096L))

    public fun pointerLayout(): TypeLayout<ConnectRequest> = TransparentTypeLayout(fieldLayout, { ptr, layout -> ConnectRequest(ptr, layout) }, { it.raw })

    public companion object {
        public operator fun invoke(raw: UByteArray, fieldLayout: TypeLayout<UByteArray>): ConnectRequest = ConnectRequest(raw, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): ConnectRequest = ConnectRequest(fieldStorage, fieldLayout)
    }
}

public open class SendData internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var raw: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(raw: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(raw, AlignedTypeLayout(fieldLayout, 4096L)), AlignedTypeLayout(fieldLayout, 4096L))

    public fun pointerLayout(): TypeLayout<SendData> = TransparentTypeLayout(fieldLayout, { ptr, layout -> SendData(ptr, layout) }, { it.raw })

    public companion object {
        public operator fun invoke(raw: UByteArray, fieldLayout: TypeLayout<UByteArray>): SendData = SendData(raw, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): SendData = SendData(fieldStorage, fieldLayout)
    }
}

public open class ReceiveData internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var raw: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(raw: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(raw, AlignedTypeLayout(fieldLayout, 4096L)), AlignedTypeLayout(fieldLayout, 4096L))

    public fun pointerLayout(): TypeLayout<ReceiveData> = TransparentTypeLayout(fieldLayout, { ptr, layout -> ReceiveData(ptr, layout) }, { it.raw })

    public companion object {
        public operator fun invoke(raw: UByteArray, fieldLayout: TypeLayout<UByteArray>): ReceiveData = ReceiveData(raw, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): ReceiveData = ReceiveData(fieldStorage, fieldLayout)
    }
}

public open class GetAddress internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var raw: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(raw: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(raw, AlignedTypeLayout(fieldLayout, 4096L)), AlignedTypeLayout(fieldLayout, 4096L))

    public fun pointerLayout(): TypeLayout<GetAddress> = TransparentTypeLayout(fieldLayout, { ptr, layout -> GetAddress(ptr, layout) }, { it.raw })

    public companion object {
        public operator fun invoke(raw: UByteArray, fieldLayout: TypeLayout<UByteArray>): GetAddress = GetAddress(raw, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): GetAddress = GetAddress(fieldStorage, fieldLayout)
    }
}
