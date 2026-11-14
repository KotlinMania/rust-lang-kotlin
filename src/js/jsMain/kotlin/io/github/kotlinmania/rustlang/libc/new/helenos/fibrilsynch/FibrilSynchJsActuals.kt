@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

private val newHelenosFibrilsynchFibrilSynchNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun fibrilMutexLock(mutex: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock(mutex.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilMutexUnlock(mutex: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock(mutex.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilMutexTrylock(mutex: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) (newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock(mutex.rawAddress.toString()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilMutexIsLocked(mutex: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) (newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked(mutex.rawAddress.toString()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilRwlockInitialize(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockReadLock(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockWriteLock(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockReadUnlock(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockWriteUnlock(rwlock: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock(rwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilRwlockIsReadLocked(rwlock: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) (newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked(rwlock.rawAddress.toString()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilRwlockIsWriteLocked(rwlock: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) (newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked(rwlock.rawAddress.toString()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilRwlockIsLocked(rwlock: MutPtr): Boolean =
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) (newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked(rwlock.rawAddress.toString()) as Boolean) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilCondvarInitialize(condvar: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize(condvar.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilCondvarWait(condvar: MutPtr, mutex: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait(condvar.rawAddress.toString(), mutex.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilCondvarWaitTimeout(condvar: MutPtr, mutex: MutPtr, timeout: UsecT): ErrnoT =
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) (newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout(condvar.rawAddress.toString(), mutex.rawAddress.toString(), timeout.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fibrilCondvarSignal(condvar: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal(condvar.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fibrilCondvarBroadcast(condvar: MutPtr) {
    if (newHelenosFibrilsynchFibrilSynchNapiAddon != null) {
        newHelenosFibrilsynchFibrilSynchNapiAddon.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast(condvar.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
