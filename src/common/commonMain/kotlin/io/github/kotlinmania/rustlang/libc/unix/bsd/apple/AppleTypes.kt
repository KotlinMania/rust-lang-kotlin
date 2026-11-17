@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.UintptrT

public fun interface FnCallback {
    public operator fun invoke(ctx: MutPtr): CInt
}

public typealias WcharT = Int

public typealias ClockT = CUlong

public typealias TimeT = CLong

public typealias SusecondsT = Int

public typealias DevT = Int

public typealias InoT = ULong

public typealias ModeT = UShort

public typealias NlinkT = UShort

public typealias BlksizeT = Int

public typealias RlimT = ULong

public typealias SigsetT = UInt

public typealias ClockidT = CUint

public typealias FsblkcntT = CUint

public typealias FsfilcntT = CUint

public typealias SpeedT = CUlong

public typealias TcflagT = CUlong

public typealias NlItem = CInt

public typealias IdT = CUint

public typealias SemT = CInt

public typealias IdtypeT = CUint

public typealias IntegerT = CInt

public typealias CpuTypeT = IntegerT

public typealias CpuSubtypeT = IntegerT

public typealias NaturalT = UInt

public typealias MachMsgTypeNumberT = NaturalT

public typealias KernReturnT = CInt

public typealias UuidT = UByteArray

public typealias TaskInfoT = MutPtr

public typealias HostInfoT = MutPtr

public typealias TaskFlavorT = NaturalT

public typealias RusageInfoT = MutPtr

public typealias VmOffsetT = UintptrT

public typealias VmSizeT = UintptrT

public typealias VmAddressT = VmOffsetT

public typealias QuadT = Long

public typealias UQuadT = ULong

public typealias PosixSpawnattrT = MutPtr

public typealias PosixSpawnFileActionsT = MutPtr

public typealias KeyT = CInt

public typealias ShmattT = CUshort

public typealias SaeAssocidT = UInt

public typealias SaeConnidT = UInt

public typealias MachPortT = CUint

public typealias HostT = CUint

public typealias HostFlavorT = IntegerT

public typealias HostInfo64T = MutPtr

public typealias ProcessorFlavorT = CInt

public typealias ThreadFlavorT = NaturalT

public typealias ThreadInspectT = MachPortT

public typealias ThreadActT = MachPortT

public typealias ThreadActArrayT = MutPtr

public typealias PolicyT = CInt

public typealias MachErrorT = KernReturnT

public typealias MachVmAddressT = ULong

public typealias MachVmOffsetT = ULong

public typealias MachVmSizeT = ULong

public typealias VmMapT = MachPortT

public typealias MemEntryNamePortT = MachPortT

public typealias MemoryObjectT = MachPortT

public typealias MemoryObjectOffsetT = CUlonglong

public typealias VmInheritT = CUint

public typealias VmProtT = CInt

public typealias LedgerT = MachPortT

public typealias LedgerArrayT = MutPtr

public typealias IconvT = MutPtr

// mach/hostInfo.h
public typealias HostCpuLoadInfoT = MutPtr

public typealias HostCpuLoadInfoDataT = HostCpuLoadInfo

// mach/processorInfo.h
public typealias ProcessorCpuLoadInfoT = MutPtr

public typealias ProcessorCpuLoadInfoDataT = ProcessorCpuLoadInfo

public typealias ProcessorBasicInfoT = MutPtr

public typealias ProcessorBasicInfoDataT = ProcessorBasicInfo

public typealias ProcessorSetBasicInfoDataT = ProcessorSetBasicInfo

public typealias ProcessorSetBasicInfoT = MutPtr

public typealias ProcessorSetLoadInfoDataT = ProcessorSetLoadInfo

public typealias ProcessorSetLoadInfoT = MutPtr

public typealias ProcessorInfoT = MutPtr

public typealias ProcessorInfoArrayT = MutPtr

public typealias MachTaskBasicInfoDataT = MachTaskBasicInfo

public typealias MachTaskBasicInfoT = MutPtr

public typealias TaskThreadTimesInfoDataT = TaskThreadTimesInfo

public typealias TaskThreadTimesInfoT = MutPtr

public typealias ThreadInfoT = MutPtr

public typealias ThreadBasicInfoT = MutPtr

public typealias ThreadBasicInfoDataT = ThreadBasicInfo

public typealias ThreadIdentifierInfoT = MutPtr

public typealias ThreadIdentifierInfoDataT = ThreadIdentifierInfo

public typealias ThreadExtendedInfoT = MutPtr

public typealias ThreadExtendedInfoDataT = ThreadExtendedInfo

public typealias ThreadT = MachPortT

public typealias ThreadPolicyFlavorT = NaturalT

public typealias ThreadPolicyT = MutPtr

public typealias ThreadLatencyQosT = IntegerT

public typealias ThreadThroughputQosT = IntegerT

public typealias ThreadStandardPolicyDataT = ThreadStandardPolicy

public typealias ThreadStandardPolicyT = MutPtr

public typealias ThreadExtendedPolicyDataT = ThreadExtendedPolicy

public typealias ThreadExtendedPolicyT = MutPtr

public typealias ThreadTimeConstraintPolicyDataT = ThreadTimeConstraintPolicy

public typealias ThreadTimeConstraintPolicyT = MutPtr

public typealias ThreadPrecedencePolicyDataT = ThreadPrecedencePolicy

public typealias ThreadPrecedencePolicyT = MutPtr

public typealias ThreadAffinityPolicyDataT = ThreadAffinityPolicy

public typealias ThreadAffinityPolicyT = MutPtr

public typealias ThreadBackgroundPolicyDataT = ThreadBackgroundPolicy

public typealias ThreadBackgroundPolicyT = MutPtr

public typealias ThreadLatencyQosPolicyDataT = ThreadLatencyQosPolicy

public typealias ThreadLatencyQosPolicyT = MutPtr

public typealias ThreadThroughputQosPolicyDataT = ThreadThroughputQosPolicy

public typealias ThreadThroughputQosPolicyT = MutPtr

public fun interface PthreadJitWriteCallbackTFn {
    public operator fun invoke(ctx: MutPtr): CInt
}

public typealias PthreadJitWriteCallbackT = PthreadJitWriteCallbackTFn?

public typealias OsClockidT = UInt

public typealias OsSyncWaitOnAddressFlagsT = UInt

public typealias OsSyncWakeByAddressFlagsT = UInt

public typealias OsUnfairLock = OsUnfairLockS

public typealias OsUnfairLockT = MutPtr

public typealias OsLogT = MutPtr

public typealias OsLogTypeT = UByte

public typealias OsSignpostIdT = ULong

public typealias OsSignpostTypeT = UByte

public typealias VmStatisticsT = MutPtr

public typealias VmStatisticsDataT = VmStatistics

public typealias VmStatistics64T = MutPtr

public typealias VmStatistics64DataT = VmStatistics64

public typealias TaskT = MachPortT

public typealias TaskInspectT = MachPortT

public typealias SysdirSearchPathEnumerationState = CUint

public typealias CCStatus = Int

public typealias CCCryptorStatus = Int

public typealias CCRNGStatus = CCCryptorStatus

public typealias CopyfileStateT = MutPtr

public typealias CopyfileFlagsT = UInt

public fun interface CopyfileCallbackTFn {
    public operator fun invoke(arg0: CInt, arg1: CInt, arg2: CopyfileStateT, arg3: ConstPtr, arg4: ConstPtr, arg5: MutPtr): CInt
}

public typealias CopyfileCallbackT = CopyfileCallbackTFn?

public typealias AttrgroupT = UInt

public typealias VolCapabilitiesSetT = UIntArray

public typealias MachTimebaseInfoDataT = MachTimebaseInfo

public sealed class SysdirSearchPathDirectoryT(
    public val value: UInt,
) {
    public data object SysdirDirectoryApplication : SysdirSearchPathDirectoryT(1u)
    public data object SysdirDirectoryDemoApplication : SysdirSearchPathDirectoryT(2u)
    public data object SysdirDirectoryDeveloperApplication : SysdirSearchPathDirectoryT(3u)
    public data object SysdirDirectoryAdminApplication : SysdirSearchPathDirectoryT(4u)
    public data object SysdirDirectoryLibrary : SysdirSearchPathDirectoryT(5u)
    public data object SysdirDirectoryDeveloper : SysdirSearchPathDirectoryT(6u)
    public data object SysdirDirectoryUser : SysdirSearchPathDirectoryT(7u)
    public data object SysdirDirectoryDocumentation : SysdirSearchPathDirectoryT(8u)
    public data object SysdirDirectoryDocument : SysdirSearchPathDirectoryT(9u)
    public data object SysdirDirectoryCoreservice : SysdirSearchPathDirectoryT(10u)
    public data object SysdirDirectoryAutosavedInformation : SysdirSearchPathDirectoryT(11u)
    public data object SysdirDirectoryDesktop : SysdirSearchPathDirectoryT(12u)
    public data object SysdirDirectoryCaches : SysdirSearchPathDirectoryT(13u)
    public data object SysdirDirectoryApplicationSupport : SysdirSearchPathDirectoryT(14u)
    public data object SysdirDirectoryDownloads : SysdirSearchPathDirectoryT(15u)
    public data object SysdirDirectoryInputMethods : SysdirSearchPathDirectoryT(16u)
    public data object SysdirDirectoryMovies : SysdirSearchPathDirectoryT(17u)
    public data object SysdirDirectoryMusic : SysdirSearchPathDirectoryT(18u)
    public data object SysdirDirectoryPictures : SysdirSearchPathDirectoryT(19u)
    public data object SysdirDirectoryPrinterDescription : SysdirSearchPathDirectoryT(20u)
    public data object SysdirDirectorySharedPublic : SysdirSearchPathDirectoryT(21u)
    public data object SysdirDirectoryPreferencePanes : SysdirSearchPathDirectoryT(22u)
    public data object SysdirDirectoryAllApplications : SysdirSearchPathDirectoryT(100u)
    public data object SysdirDirectoryAllLibraries : SysdirSearchPathDirectoryT(101u)

    public fun toByte(): Byte = value.toByte()

    public fun toUByte(): UByte = value.toUByte()

    public fun toShort(): Short = value.toShort()

    public fun toUShort(): UShort = value.toUShort()

    public fun toInt(): Int = value.toInt()

    public fun toUInt(): UInt = value

    public fun toLong(): Long = value.toLong()

    public fun toULong(): ULong = value.toULong()

    public open fun clone(): SysdirSearchPathDirectoryT = this
}

public sealed class SysdirSearchPathDomainMaskT(
    public val value: UInt,
) {
    public data object SysdirDomainMaskUser : SysdirSearchPathDomainMaskT((1u shl 0))
    public data object SysdirDomainMaskLocal : SysdirSearchPathDomainMaskT((1u shl 1))
    public data object SysdirDomainMaskNetwork : SysdirSearchPathDomainMaskT((1u shl 2))
    public data object SysdirDomainMaskSystem : SysdirSearchPathDomainMaskT((1u shl 3))
    public data object SysdirDomainMaskAll : SysdirSearchPathDomainMaskT(0x0ffffu)

    public fun toByte(): Byte = value.toByte()

    public fun toUByte(): UByte = value.toUByte()

    public fun toShort(): Short = value.toShort()

    public fun toUShort(): UShort = value.toUShort()

    public fun toInt(): Int = value.toInt()

    public fun toUInt(): UInt = value

    public fun toLong(): Long = value.toLong()

    public fun toULong(): ULong = value.toULong()

    public open fun clone(): SysdirSearchPathDomainMaskT = this
}
