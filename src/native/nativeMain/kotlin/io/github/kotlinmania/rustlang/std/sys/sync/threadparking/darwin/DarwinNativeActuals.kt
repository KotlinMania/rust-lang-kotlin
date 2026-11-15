@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.rustlangStdSysSyncThreadparkingDarwinDispatchRelease
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreWait
import io.github.kotlinmania.rustlang.std.sys.sync.threadparking.darwin.cinterop.rustlangStdSysSyncThreadparkingDarwinDispatchTime
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun dispatchTime(whenBranch: DispatchTimeT, delta: Long): DispatchTimeT =
    rustlangStdSysSyncThreadparkingDarwinDispatchTime(whenBranch, delta)

public actual fun dispatchSemaphoreCreate(value: Long): DispatchSemaphoreT =
    MutPtr(rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreCreate(value)?.toLong() ?: 0L)

public actual fun dispatchSemaphoreWait(dsema: DispatchSemaphoreT, timeout: DispatchTimeT): Long =
    rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreWait(dsema.rawAddress.toCPointer<ByteVar>(), timeout)

public actual fun dispatchSemaphoreSignal(dsema: DispatchSemaphoreT): Long =
    rustlangStdSysSyncThreadparkingDarwinDispatchSemaphoreSignal(dsema.rawAddress.toCPointer<ByteVar>())

public actual fun dispatchRelease(obj: MutPtr) {
    rustlangStdSysSyncThreadparkingDarwinDispatchRelease(obj.rawAddress.toCPointer<ByteVar>())
}
