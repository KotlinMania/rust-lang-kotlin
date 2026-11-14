@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.stdSysSyncThreadparkingDarwinDispatchRelease
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.stdSysSyncThreadparkingDarwinDispatchSemaphoreCreate
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.stdSysSyncThreadparkingDarwinDispatchSemaphoreSignal
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.stdSysSyncThreadparkingDarwinDispatchSemaphoreWait
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.stdSysSyncThreadparkingDarwinDispatchTime
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    stdSysSyncThreadparkingDarwinDispatchTime(whenBranch, delta)

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    MutPtr(stdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value)?.toLong() ?: 0L)

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    stdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema.rawAddress.toCPointer<ByteVar>(), timeout)

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    stdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema.rawAddress.toCPointer<ByteVar>())

public actual fun dispatchRelease(obj: MutPtr) {
    stdSysSyncThreadparkingDarwinDispatchRelease(obj.rawAddress.toCPointer<ByteVar>())
}
