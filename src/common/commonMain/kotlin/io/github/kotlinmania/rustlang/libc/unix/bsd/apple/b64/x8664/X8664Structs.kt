@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple.b64.x8664

import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.SizeT

public fun interface MallocZoneTSizeCallback {
    public operator fun invoke(zone: MutPtr, ptr: ConstPtr): SizeT
}

public fun interface MallocZoneTMallocCallback {
    public operator fun invoke(zone: MutPtr, size: SizeT): MutPtr
}

public fun interface MallocZoneTCallocCallback {
    public operator fun invoke(zone: MutPtr, numItems: SizeT, size: SizeT): MutPtr
}

public fun interface MallocZoneTFreeCallback {
    public operator fun invoke(zone: MutPtr, ptr: MutPtr)
}

public fun interface MallocZoneTReallocCallback {
    public operator fun invoke(zone: MutPtr, ptr: MutPtr, size: SizeT): MutPtr
}

public fun interface MallocZoneTDestroyCallback {
    public operator fun invoke(zone: MutPtr)
}

public fun interface MallocZoneTBatchMallocCallback {
    public operator fun invoke(zone: MutPtr, size: SizeT, results: MutPtr, numRequested: CUint): CUint
}

public fun interface MallocZoneTBatchFreeCallback {
    public operator fun invoke(zone: MutPtr, toBeFreed: MutPtr, numToBeFreed: CUint)
}

public fun interface MallocZoneTFreeDefiniteSizeCallback {
    public operator fun invoke(zone: MutPtr, ptr: MutPtr, size: SizeT)
}

public fun interface MallocZoneTPressureReliefCallback {
    public operator fun invoke(zone: MutPtr, goal: SizeT): SizeT
}

public fun interface MallocZoneTClaimedAddressCallback {
    public operator fun invoke(zone: MutPtr, ptr: MutPtr): BooleanT
}

public open class MallocIntrospectionT(
    public var private: ULongArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): MallocIntrospectionT = MallocIntrospectionT(private, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<MallocIntrospectionT> =
            object : TypeLayout<MallocIntrospectionT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): MallocIntrospectionT =
                    MallocIntrospectionT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: MallocIntrospectionT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeNative(addr, value.private)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 128L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MallocIntrospectionT =
                    MallocIntrospectionT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MallocIntrospectionT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeBytes(buf, offset, value.private)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 128, value.nonExhaustive)
                }
            }

        public fun default(): MallocIntrospectionT = MallocIntrospectionT(private = Default.default(), nonExhaustive = Unit)
    }
}

public open class MallocZoneT internal constructor(
    reserved1CtorArg: MutPtr,
    reserved2CtorArg: MutPtr,
    public var size: MallocZoneTSizeCallback?,
    public var malloc: MallocZoneTMallocCallback?,
    public var calloc: MallocZoneTCallocCallback?,
    public var valloc: MallocZoneTMallocCallback?,
    public var free: MallocZoneTFreeCallback?,
    public var realloc: MallocZoneTReallocCallback?,
    public var destroy: MallocZoneTDestroyCallback?,
    zoneNameCtorArg: ConstPtr,
    public var batchMalloc: MallocZoneTBatchMallocCallback?,
    public var batchFree: MallocZoneTBatchFreeCallback?,
    introspectCtorArg: MutPtr,
    public var version: CUint,
    public var memalign: MallocZoneTCallocCallback?,
    public var freeDefiniteSize: MallocZoneTFreeDefiniteSizeCallback?,
    public var pressureRelief: MallocZoneTPressureReliefCallback?,
    public var claimedAddress: MallocZoneTClaimedAddressCallback?,
    public var nonExhaustive: Unit,
) {
    public var reserved1: MutPtr = reserved1CtorArg
        internal set

    public var reserved2: MutPtr = reserved2CtorArg
        internal set

    public var zoneName: ConstPtr = zoneNameCtorArg
        internal set

    public var introspect: MutPtr = introspectCtorArg
        internal set

    public open fun clone(): MallocZoneT = MallocZoneT(reserved1, reserved2, size, malloc, calloc, valloc, free, realloc, destroy, zoneName, batchMalloc, batchFree, introspect, version, memalign, freeDefiniteSize, pressureRelief, claimedAddress, nonExhaustive)

    public companion object {
        public operator fun invoke(
            reserved1: MutPtr,
            reserved2: MutPtr,
            size: MallocZoneTSizeCallback?,
            malloc: MallocZoneTMallocCallback?,
            calloc: MallocZoneTCallocCallback?,
            valloc: MallocZoneTMallocCallback?,
            free: MallocZoneTFreeCallback?,
            realloc: MallocZoneTReallocCallback?,
            destroy: MallocZoneTDestroyCallback?,
            zoneName: ConstPtr,
            batchMalloc: MallocZoneTBatchMallocCallback?,
            batchFree: MallocZoneTBatchFreeCallback?,
            introspect: MutPtr,
            version: CUint,
            memalign: MallocZoneTCallocCallback?,
            freeDefiniteSize: MallocZoneTFreeDefiniteSizeCallback?,
            pressureRelief: MallocZoneTPressureReliefCallback?,
            claimedAddress: MallocZoneTClaimedAddressCallback?,
            nonExhaustive: Unit,
        ): MallocZoneT = MallocZoneT(
            reserved1CtorArg = reserved1,
            reserved2CtorArg = reserved2,
            size = size,
            malloc = malloc,
            calloc = calloc,
            valloc = valloc,
            free = free,
            realloc = realloc,
            destroy = destroy,
            zoneNameCtorArg = zoneName,
            batchMalloc = batchMalloc,
            batchFree = batchFree,
            introspectCtorArg = introspect,
            version = version,
            memalign = memalign,
            freeDefiniteSize = freeDefiniteSize,
            pressureRelief = pressureRelief,
            claimedAddress = claimedAddress,
            nonExhaustive = nonExhaustive,
        )

        public fun default(): MallocZoneT = MallocZoneT(reserved1 = MutPtr(0L), reserved2 = MutPtr(0L), size = MallocZoneTSizeCallback.default(), malloc = MallocZoneTMallocCallback.default(), calloc = MallocZoneTCallocCallback.default(), valloc = MallocZoneTMallocCallback.default(), free = MallocZoneTFreeCallback.default(), realloc = MallocZoneTReallocCallback.default(), destroy = MallocZoneTDestroyCallback.default(), zoneName = MutPtr(0L), batchMalloc = MallocZoneTBatchMallocCallback.default(), batchFree = MallocZoneTBatchFreeCallback.default(), introspect = MutPtr(0L), version = 0u, memalign = MallocZoneTCallocCallback.default(), freeDefiniteSize = MallocZoneTFreeDefiniteSizeCallback.default(), pressureRelief = MallocZoneTPressureReliefCallback.default(), claimedAddress = MallocZoneTClaimedAddressCallback.default(), nonExhaustive = Unit)
    }
}

public open class MaxAlignT(
    public var priv: DoubleArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): MaxAlignT = MaxAlignT(priv, nonExhaustive)

    public companion object {
        public fun typeLayout(): TypeLayout<MaxAlignT> =
            object : TypeLayout<MaxAlignT> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.doubleArray(2)).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeNative(addr, value.priv)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 16L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaxAlignT =
                    MaxAlignT(
                        (PrimitiveArrayLayouts.doubleArray(2)).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaxAlignT) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeBytes(buf, offset, value.priv)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 16, value.nonExhaustive)
                }
            }

        public fun default(): MaxAlignT = MaxAlignT(priv = Default.default(), nonExhaustive = Unit)
    }
}
