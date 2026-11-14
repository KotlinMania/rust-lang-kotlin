@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock
import io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch.cinterop.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer

public actual fun fibrilMutexLock(mutex: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock(mutex.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilMutexUnlock(mutex: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock(mutex.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilMutexTrylock(mutex: MutPtr): Boolean =
    libcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock(mutex.rawAddress.toCPointer<ByteVar>())

public actual fun fibrilMutexIsLocked(mutex: MutPtr): Boolean =
    libcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked(mutex.rawAddress.toCPointer<ByteVar>())

public actual fun fibrilRwlockInitialize(rwlock: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize(rwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilRwlockReadLock(rwlock: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock(rwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilRwlockWriteLock(rwlock: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock(rwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilRwlockReadUnlock(rwlock: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock(rwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilRwlockWriteUnlock(rwlock: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock(rwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilRwlockIsReadLocked(rwlock: MutPtr): Boolean =
    libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked(rwlock.rawAddress.toCPointer<ByteVar>())

public actual fun fibrilRwlockIsWriteLocked(rwlock: MutPtr): Boolean =
    libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked(rwlock.rawAddress.toCPointer<ByteVar>())

public actual fun fibrilRwlockIsLocked(rwlock: MutPtr): Boolean =
    libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked(rwlock.rawAddress.toCPointer<ByteVar>())

public actual fun fibrilCondvarInitialize(condvar: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize(condvar.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilCondvarWait(condvar: MutPtr, mutex: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait(condvar.rawAddress.toCPointer<ByteVar>(), mutex.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilCondvarWaitTimeout(condvar: MutPtr, mutex: MutPtr, timeout: UsecT): ErrnoT =
    libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout(condvar.rawAddress.toCPointer<ByteVar>(), mutex.rawAddress.toCPointer<ByteVar>(), timeout)

public actual fun fibrilCondvarSignal(condvar: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal(condvar.rawAddress.toCPointer<ByteVar>())
}

public actual fun fibrilCondvarBroadcast(condvar: MutPtr) {
    libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast(condvar.rawAddress.toCPointer<ByteVar>())
}
