@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object StdSysSyncThreadparkingDarwinJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchTime(whenBranch: Long, delta: Long): Long

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value: Long): Long

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema: Long, timeout: Long): Long

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema: Long): Long

    @JvmStatic
    external fun rustlangStdSysSyncThreadparkingDarwinDispatchRelease(obj: Long)
}

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchTime(whenBranch.toLong(), delta).toULong()

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    MutPtr(StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value))

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema.rawAddress, timeout.toLong())

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema.rawAddress)

public actual fun dispatchRelease(obj: MutPtr) {
    StdSysSyncThreadparkingDarwinJniBridge.rustlangStdSysSyncThreadparkingDarwinDispatchRelease(obj.rawAddress)
}
