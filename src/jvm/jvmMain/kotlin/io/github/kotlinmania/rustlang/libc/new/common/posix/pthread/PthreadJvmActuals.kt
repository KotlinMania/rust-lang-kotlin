@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.common.posix.pthread

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT

private object LibcNewCommonPosixPthreadJniBridge {
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
        val resourcePath = "io/github/kotlinmania/rustlang/native/$hostPlatform-$hostArch/$libFileName"
        val stream = LibcNewCommonPosixPthreadJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangLibcNewCommonPosixPthreadPthreadAtfork(prepare: Long, parent: Long, child: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize(attr: Long, guardsize: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(attr: Long, inheritsched: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam(attr: Long, param: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(attr: Long, policy: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrGetstack(attr: Long, stackaddr: Long, stacksize: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize(attr: Long, guardsize: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(attr: Long, inheritsched: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam(attr: Long, param: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(attr: Long, policy: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadAttrSetstack(attr: Long, stackaddr: Long, stacksize: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy(barrier: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadBarrierInit(barrier: Long, attr: Long, count: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadBarrierWait(barrier: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(attr: Long, shared: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit(attr: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(attr: Long, shared: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadCancel(thread: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock(attr: Long, clockId: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared(attr: Long, pshared: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock(attr: Long, clockId: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared(attr: Long, pshared: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadCreate(native: Long, attr: Long, f: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid(thread: Long, clkId: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadGetschedparam(native: Long, policy: Long, param: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadKill(thread: Long, sig: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadMutexConsistent(mutex: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock(lock: Long, abstime: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(attr: Long, protocol: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(attr: Long, pshared: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(attr: Long, robustness: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(attr: Long, protocol: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(attr: Long, pshared: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(attr: Long, robustness: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(attr: Long, value: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(attr: Long, value: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadOnce(control: Long, routine: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadSetschedparam(native: Long, policy: Int, param: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadSetschedprio(native: Long, priority: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadSigmask(how: Int, set: Long, oldset: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadSpinDestroy(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadSpinInit(lock: Long, pshared: Int): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadSpinLock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadSpinTrylock(lock: Long): Int

    @JvmStatic
    external fun rustlangLibcNewCommonPosixPthreadPthreadSpinUnlock(lock: Long): Int
}

public actual fun pthreadAtfork(prepare: PthreadAtforkPrepareCallback?, parent: PthreadAtforkPrepareCallback?, child: PthreadAtforkPrepareCallback?): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAtfork(0L, 0L, 0L)

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, guardsize: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrGetguardsize(attr.rawAddress, guardsize.rawAddress)

public actual fun pthreadAttrGetinheritsched(attr: ConstPtr, inheritsched: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrGetinheritsched(attr.rawAddress, inheritsched.rawAddress)

public actual fun pthreadAttrGetschedparam(attr: ConstPtr, param: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedparam(attr.rawAddress, param.rawAddress)

public actual fun pthreadAttrGetschedpolicy(attr: ConstPtr, policy: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrGetschedpolicy(attr.rawAddress, policy.rawAddress)

public actual fun pthreadAttrGetstack(attr: ConstPtr, stackaddr: MutPtr, stacksize: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrGetstack(attr.rawAddress, stackaddr.rawAddress, stacksize.rawAddress)

public actual fun pthreadAttrSetguardsize(attr: MutPtr, guardsize: SizeT): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrSetguardsize(attr.rawAddress, guardsize.toLong())

public actual fun pthreadAttrSetinheritsched(attr: MutPtr, inheritsched: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrSetinheritsched(attr.rawAddress, inheritsched)

public actual fun pthreadAttrSetschedparam(attr: MutPtr, param: ConstPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedparam(attr.rawAddress, param.rawAddress)

public actual fun pthreadAttrSetschedpolicy(attr: MutPtr, policy: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrSetschedpolicy(attr.rawAddress, policy)

public actual fun pthreadAttrSetstack(attr: MutPtr, stackaddr: MutPtr, stacksize: SizeT): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadAttrSetstack(attr.rawAddress, stackaddr.rawAddress, stacksize.toLong())

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadBarrierDestroy(barrier.rawAddress)

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadBarrierInit(barrier.rawAddress, attr.rawAddress, count.toInt())

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadBarrierWait(barrier.rawAddress)

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrDestroy(attr.rawAddress)

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, shared: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrGetpshared(attr.rawAddress, shared.rawAddress)

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrInit(attr.rawAddress)

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, shared: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadBarrierattrSetpshared(attr.rawAddress, shared)

public actual fun pthreadCancel(thread: PthreadT): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadCancel(thread.toLong())

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadCondattrGetclock(attr.rawAddress, clockId.rawAddress)

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadCondattrGetpshared(attr.rawAddress, pshared.rawAddress)

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadCondattrSetclock(attr.rawAddress, clockId.toInt())

public actual fun pthreadCondattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadCondattrSetpshared(attr.rawAddress, pshared)

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadCreate(native.rawAddress, attr.rawAddress, 0L, value.rawAddress)

public actual fun pthreadGetcpuclockid(thread: PthreadT, clkId: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadGetcpuclockid(thread.toLong(), clkId.rawAddress)

public actual fun pthreadGetschedparam(native: PthreadT, policy: MutPtr, param: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadGetschedparam(native.toLong(), policy.rawAddress, param.rawAddress)

public actual fun pthreadKill(thread: PthreadT, sig: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadKill(thread.toLong(), sig)

public actual fun pthreadMutexConsistent(mutex: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadMutexConsistent(mutex.rawAddress)

public actual fun pthreadMutexTimedlock(lock: MutPtr, abstime: ConstPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadMutexTimedlock(lock.rawAddress, abstime.rawAddress)

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, protocol: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetprotocol(attr.rawAddress, protocol.rawAddress)

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, pshared: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetpshared(attr.rawAddress, pshared.rawAddress)

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, robustness: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadMutexattrGetrobust(attr.rawAddress, robustness.rawAddress)

public actual fun pthreadMutexattrSetprotocol(attr: MutPtr, protocol: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetprotocol(attr.rawAddress, protocol)

public actual fun pthreadMutexattrSetpshared(attr: MutPtr, pshared: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetpshared(attr.rawAddress, pshared)

public actual fun pthreadMutexattrSetrobust(attr: MutPtr, robustness: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadMutexattrSetrobust(attr.rawAddress, robustness)

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, value: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadRwlockattrGetpshared(attr.rawAddress, value.rawAddress)

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, value: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadRwlockattrSetpshared(attr.rawAddress, value)

public actual fun pthreadOnce(control: MutPtr, routine: PthreadAtforkPrepareCallback): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadOnce(control.rawAddress, 0L)

public actual fun pthreadSetschedparam(native: PthreadT, policy: CInt, param: ConstPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadSetschedparam(native.toLong(), policy, param.rawAddress)

public actual fun pthreadSetschedprio(native: PthreadT, priority: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadSetschedprio(native.toLong(), priority)

public actual fun pthreadSigmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadSigmask(how, set.rawAddress, oldset.rawAddress)

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadSpinDestroy(lock.rawAddress)

public actual fun pthreadSpinInit(lock: MutPtr, pshared: CInt): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadSpinInit(lock.rawAddress, pshared)

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadSpinLock(lock.rawAddress)

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadSpinTrylock(lock.rawAddress)

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    LibcNewCommonPosixPthreadJniBridge.rustlangLibcNewCommonPosixPthreadPthreadSpinUnlock(lock.rawAddress)
