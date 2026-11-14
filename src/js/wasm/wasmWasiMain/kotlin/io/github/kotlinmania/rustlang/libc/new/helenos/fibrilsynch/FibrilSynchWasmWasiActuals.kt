@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun fibrilMutexLock(mutex: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock")
}

public actual fun fibrilMutexUnlock(mutex: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock")
}

public actual fun fibrilMutexTrylock(mutex: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock")

public actual fun fibrilMutexIsLocked(mutex: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked")

public actual fun fibrilRwlockInitialize(rwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize")
}

public actual fun fibrilRwlockReadLock(rwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock")
}

public actual fun fibrilRwlockWriteLock(rwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock")
}

public actual fun fibrilRwlockReadUnlock(rwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock")
}

public actual fun fibrilRwlockWriteUnlock(rwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock")
}

public actual fun fibrilRwlockIsReadLocked(rwlock: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked")

public actual fun fibrilRwlockIsWriteLocked(rwlock: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked")

public actual fun fibrilRwlockIsLocked(rwlock: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked")

public actual fun fibrilCondvarInitialize(condvar: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize")
}

public actual fun fibrilCondvarWait(condvar: MutPtr, mutex: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait")
}

public actual fun fibrilCondvarWaitTimeout(condvar: MutPtr, mutex: MutPtr, timeout: UsecT): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout")

public actual fun fibrilCondvarSignal(condvar: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal")
}

public actual fun fibrilCondvarBroadcast(condvar: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast")
}
