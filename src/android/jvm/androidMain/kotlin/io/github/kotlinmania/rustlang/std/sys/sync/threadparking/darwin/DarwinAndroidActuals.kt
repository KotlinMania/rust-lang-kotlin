@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object SysSyncThreadparkingDarwinJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun stdSysSyncThreadparkingDarwinDispatchTime(whenBranch: Long, delta: Long): Long

    @JvmStatic
    external fun stdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value: Long): Long

    @JvmStatic
    external fun stdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema: Long, timeout: Long): Long

    @JvmStatic
    external fun stdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema: Long): Long

    @JvmStatic
    external fun stdSysSyncThreadparkingDarwinDispatchRelease(obj: Long)
}

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    SysSyncThreadparkingDarwinJniBridge.stdSysSyncThreadparkingDarwinDispatchTime(whenBranch.toLong(), delta).toULong()

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    MutPtr(SysSyncThreadparkingDarwinJniBridge.stdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value))

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    SysSyncThreadparkingDarwinJniBridge.stdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema.rawAddress, timeout.toLong())

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    SysSyncThreadparkingDarwinJniBridge.stdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema.rawAddress)

public actual fun dispatchRelease(obj: MutPtr) {
    SysSyncThreadparkingDarwinJniBridge.stdSysSyncThreadparkingDarwinDispatchRelease(obj.rawAddress)
}
