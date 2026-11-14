@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewHelenosFibrilsynchFibrilSynchNapiAddon(): Boolean

private val newHelenosFibrilsynchFibrilSynchNapiAvailable: Boolean = initNewHelenosFibrilsynchFibrilSynchNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock(p0: String): Boolean

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked(p0: String): Boolean

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked(p0: String): Boolean

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked(p0: String): Boolean

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked(p0: String): Boolean

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait(p0, p1)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait(p0: String, p1: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout(p0, p1, p2)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast(p0)")
private external fun wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast(p0: String)

public actual fun fibrilMutexLock(mutex: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock(mutex.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilMutexUnlock(mutex: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock(mutex.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilMutexTrylock(mutex: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock(mutex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilMutexIsLocked(mutex: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked(mutex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilRwlockInitialize(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockReadLock(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockWriteLock(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockReadUnlock(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockWriteUnlock(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockIsReadLocked(rwlock: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked(rwlock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilRwlockIsWriteLocked(rwlock: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked(rwlock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilRwlockIsLocked(rwlock: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked(rwlock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilCondvarInitialize(condvar: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize(condvar.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilCondvarWait(condvar: MutPtr, mutex: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait(condvar.rawAddress.toString(), mutex.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilCondvarWaitTimeout(condvar: MutPtr, mutex: MutPtr, timeout: UsecT): ErrnoT =
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout(condvar.rawAddress.toString(), mutex.rawAddress.toString(), timeout.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilCondvarSignal(condvar: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal(condvar.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilCondvarBroadcast(condvar: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAvailable) {
        wasmLibcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast(condvar.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
