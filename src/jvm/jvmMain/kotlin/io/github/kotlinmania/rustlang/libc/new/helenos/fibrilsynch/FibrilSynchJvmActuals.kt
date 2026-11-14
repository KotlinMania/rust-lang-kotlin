@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.fibrilsynch

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.time.UsecT

private object NewHelenosFibrilsynchFibrilSynchJniBridge {
    init {
        loadNativeLibrary()
    }

    private fun loadNativeLibrary() {
        val osRaw = System.getProperty("os.name", "").lowercase()
        val archRaw = System.getProperty("os.arch", "").lowercase()
        val hostPlatform =
            when {
                osRaw.contains("win") -> "windows"
                osRaw.contains("mac") || osRaw.contains("darwin") -> "macos"
                else -> "linux"
            }
        val hostArch =
            when {
                archRaw == "aarch64" || archRaw == "arm64" -> "aarch64"
                archRaw == "amd64" || archRaw == "x86_64" -> "x86_64"
                else -> archRaw
            }
        val libFileName =
            when (hostPlatform) {
                "windows" -> "kmcore_jni.dll"
                "macos" -> "libkmcore_jni.dylib"
                else -> "libkmcore_jni.so"
            }
        val pathSep = System.getProperty("path.separator", ":")
        val libPathProp = System.getProperty("java.library.path", "")
        for (dirEntry in libPathProp.split(pathSep)) {
            if (dirEntry.isEmpty()) continue
            val candidate = java.io.File(dirEntry, libFileName)
            if (candidate.isFile) {
                System.load(candidate.absolutePath)
                return
            }
        }
        val resourcePath = "io/github/kotlinmania/rustlang/libc/native/$hostPlatform-$hostArch/$libFileName"
        val stream = NewHelenosFibrilsynchFibrilSynchJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("kmcore_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock(mutex: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock(mutex: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock(mutex: Long): Boolean

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked(mutex: Long): Boolean

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize(rwlock: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock(rwlock: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock(rwlock: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock(rwlock: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock(rwlock: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked(rwlock: Long): Boolean

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked(rwlock: Long): Boolean

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked(rwlock: Long): Boolean

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize(condvar: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait(condvar: Long, mutex: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout(condvar: Long, mutex: Long, timeout: Long): Int

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal(condvar: Long)

    @JvmStatic
    external fun libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast(condvar: Long)
}

public actual fun fibrilMutexLock(mutex: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexLock(mutex.rawAddress)
}

public actual fun fibrilMutexUnlock(mutex: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexUnlock(mutex.rawAddress)
}

public actual fun fibrilMutexTrylock(mutex: MutPtr): Boolean =
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexTrylock(mutex.rawAddress)

public actual fun fibrilMutexIsLocked(mutex: MutPtr): Boolean =
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilMutexIsLocked(mutex.rawAddress)

public actual fun fibrilRwlockInitialize(rwlock: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockInitialize(rwlock.rawAddress)
}

public actual fun fibrilRwlockReadLock(rwlock: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadLock(rwlock.rawAddress)
}

public actual fun fibrilRwlockWriteLock(rwlock: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteLock(rwlock.rawAddress)
}

public actual fun fibrilRwlockReadUnlock(rwlock: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockReadUnlock(rwlock.rawAddress)
}

public actual fun fibrilRwlockWriteUnlock(rwlock: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockWriteUnlock(rwlock.rawAddress)
}

public actual fun fibrilRwlockIsReadLocked(rwlock: MutPtr): Boolean =
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsReadLocked(rwlock.rawAddress)

public actual fun fibrilRwlockIsWriteLocked(rwlock: MutPtr): Boolean =
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsWriteLocked(rwlock.rawAddress)

public actual fun fibrilRwlockIsLocked(rwlock: MutPtr): Boolean =
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilRwlockIsLocked(rwlock.rawAddress)

public actual fun fibrilCondvarInitialize(condvar: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarInitialize(condvar.rawAddress)
}

public actual fun fibrilCondvarWait(condvar: MutPtr, mutex: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWait(condvar.rawAddress, mutex.rawAddress)
}

public actual fun fibrilCondvarWaitTimeout(condvar: MutPtr, mutex: MutPtr, timeout: UsecT): ErrnoT =
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarWaitTimeout(condvar.rawAddress, mutex.rawAddress, timeout)

public actual fun fibrilCondvarSignal(condvar: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarSignal(condvar.rawAddress)
}

public actual fun fibrilCondvarBroadcast(condvar: MutPtr) {
    NewHelenosFibrilsynchFibrilSynchJniBridge.libcNewHelenosFibrilsynchFibrilSynchFibrilCondvarBroadcast(condvar.rawAddress)
}
