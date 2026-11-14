@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.BooleanT
import io.github.kotlinmania.rustlang.libc.LocaleT
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.PthreadT
import io.github.kotlinmania.rustlang.libc.SocklenT
import io.github.kotlinmania.rustlang.libc.unix.IntptrT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.bsd.OffT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initUnixBsdAppleNapiAddon(): Boolean

private val unixBsdAppleNapiAvailable: Boolean = initUnixBsdAppleNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetMACHTASKSELF()")
private external fun wasmLibcUnixBsdAppleGetMACHTASKSELF(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSetMACHTASKSELF(p0)")
private external fun wasmLibcUnixBsdAppleSetMACHTASKSELF(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetVMPAGESIZE()")
private external fun wasmLibcUnixBsdAppleGetVMPAGESIZE(): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleSetgrent()")
private external fun wasmLibcUnixBsdAppleSetgrent()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAioRead(p0)")
private external fun wasmLibcUnixBsdAppleAioRead(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAioWrite(p0)")
private external fun wasmLibcUnixBsdAppleAioWrite(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAioFsync(p0, p1)")
private external fun wasmLibcUnixBsdAppleAioFsync(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAioError(p0)")
private external fun wasmLibcUnixBsdAppleAioError(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAioReturn(p0)")
private external fun wasmLibcUnixBsdAppleAioReturn(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAioSuspend(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleAioSuspend(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAioCancel(p0, p1)")
private external fun wasmLibcUnixBsdAppleAioCancel(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleChflags(p0, p1)")
private external fun wasmLibcUnixBsdAppleChflags(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFchflags(p0, p1)")
private external fun wasmLibcUnixBsdAppleFchflags(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleLchflags(p0, p1)")
private external fun wasmLibcUnixBsdAppleLchflags(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleLchmod(p0, p1)")
private external fun wasmLibcUnixBsdAppleLchmod(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleLioListio(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleLioListio(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleDirfd(p0)")
private external fun wasmLibcUnixBsdAppleDirfd(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleLutimes(p0, p1)")
private external fun wasmLibcUnixBsdAppleLutimes(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGettimeofday(p0, p1)")
private external fun wasmLibcUnixBsdAppleGettimeofday(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetutxent()")
private external fun wasmLibcUnixBsdAppleGetutxent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetutxid(p0)")
private external fun wasmLibcUnixBsdAppleGetutxid(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetutxline(p0)")
private external fun wasmLibcUnixBsdAppleGetutxline(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePututxline(p0)")
private external fun wasmLibcUnixBsdApplePututxline(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleSetutxent()")
private external fun wasmLibcUnixBsdAppleSetutxent()

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleEndutxent()")
private external fun wasmLibcUnixBsdAppleEndutxent()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleUtmpxname(p0)")
private external fun wasmLibcUnixBsdAppleUtmpxname(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAsctime(p0)")
private external fun wasmLibcUnixBsdAppleAsctime(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleCtime(p0)")
private external fun wasmLibcUnixBsdAppleCtime(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetdate(p0)")
private external fun wasmLibcUnixBsdAppleGetdate(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleStrptime(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleStrptime(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleAsctimeR(p0, p1)")
private external fun wasmLibcUnixBsdAppleAsctimeR(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleCtimeR(p0, p1)")
private external fun wasmLibcUnixBsdAppleCtimeR(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetnameinfo(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcUnixBsdAppleGetnameinfo(p0: String, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMincore(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleMincore(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSysctlnametomib(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleSysctlnametomib(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMprotect(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleMprotect(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSemget(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleSemget(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSemctl(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmLibcUnixBsdAppleSemctl(p0: Int, p1: Int, p2: Int, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSemop(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleSemop(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcUnixBsdAppleShmOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcUnixBsdAppleShmOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFtok(p0, p1)")
private external fun wasmLibcUnixBsdAppleFtok(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleShmat(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleShmat(p0: Int, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleShmdt(p0)")
private external fun wasmLibcUnixBsdAppleShmdt(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleShmctl(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleShmctl(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleShmget(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleShmget(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSysctl(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleSysctl(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSysctlbyname(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleSysctlbyname(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleMachAbsoluteTime()")
private external fun wasmLibcUnixBsdAppleMachAbsoluteTime(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMachTimebaseInfo(p0)")
private external fun wasmLibcUnixBsdAppleMachTimebaseInfo(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleMachHostSelf()")
private external fun wasmLibcUnixBsdAppleMachHostSelf(): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleMachThreadSelf()")
private external fun wasmLibcUnixBsdAppleMachThreadSelf(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadCondTimedwaitRelativeNp(p0, p1, p2)")
private external fun wasmLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadAttrGetscope(p0, p1)")
private external fun wasmLibcUnixBsdApplePthreadAttrGetscope(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadAttrGetstackaddr(p0, p1)")
private external fun wasmLibcUnixBsdApplePthreadAttrGetstackaddr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadAttrGetdetachstate(p0, p1)")
private external fun wasmLibcUnixBsdApplePthreadAttrGetdetachstate(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadAttrSetscope(p0, p1)")
private external fun wasmLibcUnixBsdApplePthreadAttrSetscope(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadAttrSetstackaddr(p0, p1)")
private external fun wasmLibcUnixBsdApplePthreadAttrSetstackaddr(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadSetnameNp(p0)")
private external fun wasmLibcUnixBsdApplePthreadSetnameNp(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadGetnameNp(p0, p1, p2)")
private external fun wasmLibcUnixBsdApplePthreadGetnameNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadMachThreadNp(p0)")
private external fun wasmLibcUnixBsdApplePthreadMachThreadNp(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadFromMachThreadNp(p0)")
private external fun wasmLibcUnixBsdApplePthreadFromMachThreadNp(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadGetStackaddrNp(p0)")
private external fun wasmLibcUnixBsdApplePthreadGetStackaddrNp(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadGetStacksizeNp(p0)")
private external fun wasmLibcUnixBsdApplePthreadGetStacksizeNp(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadMainNp()")
private external fun wasmLibcUnixBsdApplePthreadMainNp(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadThreadidNp(p0, p1)")
private external fun wasmLibcUnixBsdApplePthreadThreadidNp(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadJitWriteProtectNp(p0)")
private external fun wasmLibcUnixBsdApplePthreadJitWriteProtectNp(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadJitWriteProtectSupportedNp()")
private external fun wasmLibcUnixBsdApplePthreadJitWriteProtectSupportedNp(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadJitWriteWithCallbackNp(p0, p1)")
private external fun wasmLibcUnixBsdApplePthreadJitWriteWithCallbackNp(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()")
private external fun wasmLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePthreadCpuNumberNp(p0)")
private external fun wasmLibcUnixBsdApplePthreadCpuNumberNp(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsSyncWaitOnAddress(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleOsSyncWaitOnAddress(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(p0: String, p1: String, p2: String, p3: Int, p4: Int, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(p0: String, p1: String, p2: String, p3: Int, p4: Int, p5: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsSyncWakeByAddressAny(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleOsSyncWakeByAddressAny(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsSyncWakeByAddressAll(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleOsSyncWakeByAddressAll(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsUnfairLockLock(p0)")
private external fun wasmLibcUnixBsdAppleOsUnfairLockLock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsUnfairLockTrylock(p0)")
private external fun wasmLibcUnixBsdAppleOsUnfairLockTrylock(p0: String): Boolean

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsUnfairLockUnlock(p0)")
private external fun wasmLibcUnixBsdAppleOsUnfairLockUnlock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsUnfairLockAssertOwner(p0)")
private external fun wasmLibcUnixBsdAppleOsUnfairLockAssertOwner(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsUnfairLockAssertNotOwner(p0)")
private external fun wasmLibcUnixBsdAppleOsUnfairLockAssertNotOwner(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsLogCreate(p0, p1)")
private external fun wasmLibcUnixBsdAppleOsLogCreate(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsLogTypeEnabled(p0, p1)")
private external fun wasmLibcUnixBsdAppleOsLogTypeEnabled(p0: String, p1: Int): Boolean

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsSignpostIdMakeWithPointer(p0, p1)")
private external fun wasmLibcUnixBsdAppleOsSignpostIdMakeWithPointer(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsSignpostIdGenerate(p0)")
private external fun wasmLibcUnixBsdAppleOsSignpostIdGenerate(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOsSignpostEnabled(p0)")
private external fun wasmLibcUnixBsdAppleOsSignpostEnabled(p0: String): Boolean

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleThreadPolicySet(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleThreadPolicySet(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleThreadPolicyGet(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleThreadPolicyGet(p0: Int, p1: Int, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleThreadInfo(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleThreadInfo(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleError()")
private external fun wasmLibcUnixBsdAppleError(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleBacktrace(p0, p1)")
private external fun wasmLibcUnixBsdAppleBacktrace(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleBacktraceSymbols(p0, p1)")
private external fun wasmLibcUnixBsdAppleBacktraceSymbols(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleBacktraceSymbolsFd(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleBacktraceSymbolsFd(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleBacktraceFromFp(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleBacktraceFromFp(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleBacktraceImageOffsets(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleBacktraceImageOffsets(p0: String, p1: String, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleBacktraceAsync(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleBacktraceAsync(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleStatfs(p0, p1)")
private external fun wasmLibcUnixBsdAppleStatfs(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFstatfs(p0, p1)")
private external fun wasmLibcUnixBsdAppleFstatfs(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleKevent(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleKevent(p0: Int, p1: String, p2: Int, p3: String, p4: Int, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcUnixBsdAppleKevent64(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcUnixBsdAppleKevent64(p0: Int, p1: String, p2: Int, p3: String, p4: Int, p5: Int, p6: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMount(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleMount(p0: String, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFmount(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleFmount(p0: String, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdApplePtrace(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdApplePtrace(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleQuotactl(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleQuotactl(p0: String, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSethostname(p0, p1)")
private external fun wasmLibcUnixBsdAppleSethostname(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSendfile(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleSendfile(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFutimens(p0, p1)")
private external fun wasmLibcUnixBsdAppleFutimens(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleUtimensat(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleUtimensat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleOpenpty(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleOpenpty(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleForkpty(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleForkpty(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleLoginTty(p0)")
private external fun wasmLibcUnixBsdAppleLoginTty(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleDuplocale(p0)")
private external fun wasmLibcUnixBsdAppleDuplocale(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFreelocale(p0)")
private external fun wasmLibcUnixBsdAppleFreelocale(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleLocaleconvL(p0)")
private external fun wasmLibcUnixBsdAppleLocaleconvL(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleNewlocale(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleNewlocale(p0: Int, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleUselocale(p0)")
private external fun wasmLibcUnixBsdAppleUselocale(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleQuerylocale(p0, p1)")
private external fun wasmLibcUnixBsdAppleQuerylocale(p0: Int, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetpriority(p0, p1)")
private external fun wasmLibcUnixBsdAppleGetpriority(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSetpriority(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleSetpriority(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetdomainname(p0, p1)")
private external fun wasmLibcUnixBsdAppleGetdomainname(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSetdomainname(p0, p1)")
private external fun wasmLibcUnixBsdAppleSetdomainname(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdApplePreadv(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdApplePreadv(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdApplePwritev(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdApplePwritev(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetxattr(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleGetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFgetxattr(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleFgetxattr(p0: Int, p1: String, p2: String, p3: String, p4: Int, p5: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSetxattr(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleSetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFsetxattr(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleFsetxattr(p0: Int, p1: String, p2: String, p3: String, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleListxattr(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleListxattr(p0: String, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFlistxattr(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleFlistxattr(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleRemovexattr(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleRemovexattr(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleRenamexNp(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleRenamexNp(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleRenameatxNp(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleRenameatxNp(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFremovexattr(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleFremovexattr(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetgrouplist(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleGetgrouplist(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleInitgroups(p0, p1)")
private external fun wasmLibcUnixBsdAppleInitgroups(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleWaitid(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleWaitid(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleBrk(p0)")
private external fun wasmLibcUnixBsdAppleBrk(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSbrk(p0)")
private external fun wasmLibcUnixBsdAppleSbrk(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSettimeofday(p0, p1)")
private external fun wasmLibcUnixBsdAppleSettimeofday(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleDyldImageCount()")
private external fun wasmLibcUnixBsdAppleDyldImageCount(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleDyldGetImageHeader(p0)")
private external fun wasmLibcUnixBsdAppleDyldGetImageHeader(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleDyldGetImageVmaddrSlide(p0)")
private external fun wasmLibcUnixBsdAppleDyldGetImageVmaddrSlide(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleDyldGetImageName(p0)")
private external fun wasmLibcUnixBsdAppleDyldGetImageName(p0: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawn(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdApplePosixSpawn(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnp(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdApplePosixSpawnp(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrInit(p0)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrDestroy(p0)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrGetsigdefault(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrGetsigdefault(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrSetsigdefault(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrSetsigdefault(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrGetsigmask(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrGetsigmask(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrSetsigmask(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrSetsigmask(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrGetflags(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrGetflags(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrSetflags(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrSetflags(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrGetpgroup(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrGetpgroup(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrSetpgroup(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrSetpgroup(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrSetarchprefNp(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrSetarchprefNp(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrGetarchprefNp(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrGetarchprefNp(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrGetbinprefNp(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrGetbinprefNp(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnattrSetbinprefNp(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdApplePosixSpawnattrSetbinprefNp(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsInit(p0)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsDestroy(p0)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsDestroy(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsAddopen(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsAddopen(p0: String, p1: Int, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsAddclose(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsAddclose(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsAdddup2(p0, p1, p2)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsAdddup2(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsAddchdirNp(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsAddchdir(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsAddchdir(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdApplePosixSpawnFileActionsAddfchdir(p0, p1)")
private external fun wasmLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleUname(p0)")
private external fun wasmLibcUnixBsdAppleUname(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.libcUnixBsdAppleConnectx(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmLibcUnixBsdAppleConnectx(p0: Int, p1: String, p2: Int, p3: Int, p4: String, p5: Int, p6: String, p7: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleDisconnectx(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleDisconnectx(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleNtpAdjtime(p0)")
private external fun wasmLibcUnixBsdAppleNtpAdjtime(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleNtpGettime(p0)")
private external fun wasmLibcUnixBsdAppleNtpGettime(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetmntinfo(p0, p1)")
private external fun wasmLibcUnixBsdAppleGetmntinfo(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetfsstat(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleGetfsstat(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleClonefile(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleClonefile(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleClonefileat(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleClonefileat(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFclonefileat(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleFclonefileat(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleCopyfile(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleCopyfile(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFcopyfile(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleFcopyfile(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleCopyfileStateFree(p0)")
private external fun wasmLibcUnixBsdAppleCopyfileStateFree(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleCopyfileStateAlloc()")
private external fun wasmLibcUnixBsdAppleCopyfileStateAlloc(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleCopyfileStateGet(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleCopyfileStateGet(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleCopyfileStateSet(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleCopyfileStateSet(p0: String, p1: Int, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMachErrorString(p0)")
private external fun wasmLibcUnixBsdAppleMachErrorString(p0: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMemsetS(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleMemsetS(p0: String, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMemsetPattern4(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleMemsetPattern4(p0: String, p1: String, p2: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMemsetPattern8(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleMemsetPattern8(p0: String, p1: String, p2: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMemsetPattern16(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleMemsetPattern16(p0: String, p1: String, p2: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleStrtonum(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleStrtonum(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocPrintf(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleMallocPrintf(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneCheck(p0)")
private external fun wasmLibcUnixBsdAppleMallocZoneCheck(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZonePrint(p0, p1)")
private external fun wasmLibcUnixBsdAppleMallocZonePrint(p0: String, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneStatistics(p0, p1)")
private external fun wasmLibcUnixBsdAppleMallocZoneStatistics(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneLog(p0, p1)")
private external fun wasmLibcUnixBsdAppleMallocZoneLog(p0: String, p1: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZonePrintPtrInfo(p0)")
private external fun wasmLibcUnixBsdAppleMallocZonePrintPtrInfo(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocDefaultZone()")
private external fun wasmLibcUnixBsdAppleMallocDefaultZone(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneFromPtr(p0)")
private external fun wasmLibcUnixBsdAppleMallocZoneFromPtr(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneMalloc(p0, p1)")
private external fun wasmLibcUnixBsdAppleMallocZoneMalloc(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneValloc(p0, p1)")
private external fun wasmLibcUnixBsdAppleMallocZoneValloc(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneCalloc(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleMallocZoneCalloc(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneRealloc(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleMallocZoneRealloc(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocZoneFree(p0, p1)")
private external fun wasmLibcUnixBsdAppleMallocZoneFree(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGethostuuid(p0, p1)")
private external fun wasmLibcUnixBsdAppleGethostuuid(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleGethostid()")
private external fun wasmLibcUnixBsdAppleGethostid(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSethostid(p0)")
private external fun wasmLibcUnixBsdAppleSethostid(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleCCRandomGenerateBytes(p0, p1)")
private external fun wasmLibcUnixBsdAppleCCRandomGenerateBytes(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetentropy(p0, p1)")
private external fun wasmLibcUnixBsdAppleGetentropy(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleNsgetExecutablePath(p0, p1)")
private external fun wasmLibcUnixBsdAppleNsgetExecutablePath(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleNsgetArgv()")
private external fun wasmLibcUnixBsdAppleNsgetArgv(): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleNsgetArgc()")
private external fun wasmLibcUnixBsdAppleNsgetArgc(): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleNsgetEnviron()")
private external fun wasmLibcUnixBsdAppleNsgetEnviron(): String

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleNsgetProgname()")
private external fun wasmLibcUnixBsdAppleNsgetProgname(): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMachVmMap(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)")
private external fun wasmLibcUnixBsdAppleMachVmMap(p0: Int, p1: String, p2: String, p3: String, p4: Int, p5: Int, p6: String, p7: Int, p8: Int, p9: Int, p10: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleVmAllocate(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleVmAllocate(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleVmDeallocate(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleVmDeallocate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleHostStatistics64(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleHostStatistics64(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleHostProcessorInfo(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleHostProcessorInfo(p0: Int, p1: Int, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleTaskForPid(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleTaskForPid(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleTaskInfo(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleTaskInfo(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleTaskCreate(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleTaskCreate(p0: Int, p1: String, p2: Int, p3: Int, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleTaskTerminate(p0)")
private external fun wasmLibcUnixBsdAppleTaskTerminate(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleTaskThreads(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleTaskThreads(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleHostStatistics(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleHostStatistics(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSysdirGetNextSearchPathEnumeration(p0, p1)")
private external fun wasmLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetattrlist(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleGetattrlist(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFgetattrlist(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleFgetattrlist(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetattrlistat(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleGetattrlistat(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSetattrlist(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleSetattrlist(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFsetattrlist(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleFsetattrlist(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcUnixBsdAppleSetattrlistat(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcUnixBsdAppleSetattrlistat(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleGetattrlistbulk(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleGetattrlistbulk(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocSize(p0)")
private external fun wasmLibcUnixBsdAppleMallocSize(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMallocGoodSize(p0)")
private external fun wasmLibcUnixBsdAppleMallocGoodSize(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleDirname(p0)")
private external fun wasmLibcUnixBsdAppleDirname(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleBasename(p0)")
private external fun wasmLibcUnixBsdAppleBasename(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMkfifoat(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleMkfifoat(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMknodat(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleMknodat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleFreadlink(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleFreadlink(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleExecvP(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleExecvP(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleQsortR(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleQsortR(p0: String, p1: String, p2: String, p3: String, p4: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcListpids(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleProcListpids(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcListallpids(p0, p1)")
private external fun wasmLibcUnixBsdAppleProcListallpids(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcListpgrppids(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleProcListpgrppids(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcListchildpids(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleProcListchildpids(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcPidinfo(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleProcPidinfo(p0: Int, p1: Int, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcPidfdinfo(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleProcPidfdinfo(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcPidfileportinfo(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleProcPidfileportinfo(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcPidpath(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleProcPidpath(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcName(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleProcName(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcRegionfilename(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleProcRegionfilename(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcKmsgbuf(p0, p1)")
private external fun wasmLibcUnixBsdAppleProcKmsgbuf(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcLibversion(p0, p1)")
private external fun wasmLibcUnixBsdAppleProcLibversion(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcPidRusage(p0, p1, p2)")
private external fun wasmLibcUnixBsdAppleProcPidRusage(p0: Int, p1: Int, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcSetNoSmt()")
private external fun wasmLibcUnixBsdAppleProcSetNoSmt(): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcSetthreadNoSmt()")
private external fun wasmLibcUnixBsdAppleProcSetthreadNoSmt(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcSetCsm(p0)")
private external fun wasmLibcUnixBsdAppleProcSetCsm(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleProcSetthreadCsm(p0)")
private external fun wasmLibcUnixBsdAppleProcSetthreadCsm(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleMemmem(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleMemmem(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcUnixBsdAppleTaskSetInfo(p0, p1, p2, p3)")
private external fun wasmLibcUnixBsdAppleTaskSetInfo(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcUnixBsdAppleIconvOpen(p0, p1)")
private external fun wasmLibcUnixBsdAppleIconvOpen(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcUnixBsdAppleIconv(p0, p1, p2, p3, p4)")
private external fun wasmLibcUnixBsdAppleIconv(p0: String, p1: String, p2: String, p3: String, p4: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcUnixBsdAppleIconvClose(p0)")
private external fun wasmLibcUnixBsdAppleIconvClose(p0: String): Int

public actual var MACH_TASK_SELF: io.github.kotlinmania.rustlang.libc.MachPortT
    get() = if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetMACHTASKSELF().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (unixBsdAppleNapiAvailable) {
            wasmLibcUnixBsdAppleSetMACHTASKSELF(value.toInt())
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual val VM_PAGE_SIZE: VmSizeT
    get() = if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetVMPAGESIZE().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgrent() {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleSetgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun aioRead(aiocbp: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleAioRead(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleAioWrite(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleAioFsync(op, aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioError(aiocbp: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleAioError(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleAioReturn(aiocbp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleAioSuspend(aiocbList.rawAddress.toString(), nitems, timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleAioCancel(fd, aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chflags(path: ConstPtr, flags: CUint): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleChflags(path.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchflags(fd: CInt, flags: CUint): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFchflags(fd, flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lchflags(path: ConstPtr, flags: CUint): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleLchflags(path.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lchmod(path: ConstPtr, mode: ModeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleLchmod(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.libc.ClockidT, tp: MutPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.libc.ClockidT, tp: MutPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleLioListio(mode, aiocbList.rawAddress.toString(), nitems, sevp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirfd(dirp: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleDirfd(dirp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleLutimes(file.rawAddress.toString(), times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxent(): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleGetutxent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleGetutxid(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleGetutxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdApplePututxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutxent() {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleSetutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endutxent() {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleEndutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun utmpxname(file: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleUtmpxname(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctime(tm: ConstPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleAsctime(tm.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctime(clock: ConstPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleCtime(clock.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdate(datestr: ConstPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleGetdate(datestr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strptime(buf: ConstPtr, format: ConstPtr, timeptr: MutPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleStrptime(buf.rawAddress.toString(), format.rawAddress.toString(), timeptr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(tm: ConstPtr, result: MutPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleAsctimeR(tm.rawAddress.toString(), result.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctimeR(clock: ConstPtr, result: MutPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleCtimeR(clock.rawAddress.toString(), result.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetnameinfo(sa.rawAddress.toString(), salen.toInt(), host.rawAddress.toString(), hostlen.toInt(), serv.rawAddress.toString(), servlen.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mincore(addr: ConstPtr, len: SizeT, vec: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMincore(addr.rawAddress.toString(), len.toString(), vec.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctlnametomib(name: ConstPtr, mibp: MutPtr, sizep: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSysctlnametomib(name.rawAddress.toString(), mibp.rawAddress.toString(), sizep.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMprotect(addr.rawAddress.toString(), len.toString(), prot) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semget(key: KeyT, nsems: CInt, semflg: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSemget(key, nsems, semflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSemop(semid, sops.rawAddress.toString(), nsops.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleShmOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFtok(pathname.rawAddress.toString(), projId) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleShmat(shmid, shmaddr.rawAddress.toString(), shmflg).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleShmdt(shmaddr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleShmctl(shmid, cmd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleShmget(key, size.toString(), shmflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctl(name: MutPtr, namelen: CUint, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSysctl(name.rawAddress.toString(), namelen.toInt(), oldp.rawAddress.toString(), oldlenp.rawAddress.toString(), newp.rawAddress.toString(), newlen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctlbyname(name: ConstPtr, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSysctlbyname(name.rawAddress.toString(), oldp.rawAddress.toString(), oldlenp.rawAddress.toString(), newp.rawAddress.toString(), newlen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machAbsoluteTime(): ULong =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMachAbsoluteTime().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machTimebaseInfo(info: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMachTimebaseInfo(info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machHostSelf(): MachPortT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMachHostSelf().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machThreadSelf(): MachPortT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMachThreadSelf().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondTimedwaitRelativeNp(cond: MutPtr, lock: MutPtr, timeout: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(cond.rawAddress.toString(), lock.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetscope(attr: ConstPtr, contentionscope: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadAttrGetscope(attr.rawAddress.toString(), contentionscope.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstackaddr(attr: ConstPtr, stackaddr: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadAttrGetstackaddr(attr.rawAddress.toString(), stackaddr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, detachstate: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadAttrGetdetachstate(attr.rawAddress.toString(), detachstate.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetscope(attr: MutPtr, contentionscope: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadAttrSetscope(attr.rawAddress.toString(), contentionscope) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstackaddr(attr: MutPtr, stackaddr: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadAttrSetstackaddr(attr.rawAddress.toString(), stackaddr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetnameNp(name: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadSetnameNp(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadGetnameNp(thread.rawAddress.toString(), name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMachThreadNp(thread: PthreadT): io.github.kotlinmania.rustlang.libc.MachPortT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadMachThreadNp(thread.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadFromMachThreadNp(port: io.github.kotlinmania.rustlang.libc.MachPortT): PthreadT =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdApplePthreadFromMachThreadNp(port.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetStackaddrNp(thread: PthreadT): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdApplePthreadGetStackaddrNp(thread.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetStacksizeNp(thread: PthreadT): SizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadGetStacksizeNp(thread.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMainNp(): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadMainNp() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadThreadidNp(thread: PthreadT, threadId: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadThreadidNp(thread.rawAddress.toString(), threadId.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteProtectNp(enabled: CInt) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdApplePthreadJitWriteProtectNp(enabled)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pthreadJitWriteProtectSupportedNp(): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadJitWriteProtectSupportedNp() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteWithCallbackNp(callback: io.github.kotlinmania.rustlang.libc.PthreadJitWriteCallbackT, ctx: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadJitWriteWithCallbackNp(0L.toString(), ctx.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteFreezeCallbacksNp() {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pthreadCpuNumberNp(cpuNumberOut: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePthreadCpuNumberNp(cpuNumberOut.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddress(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsSyncWaitOnAddress(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddressWithDeadline(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, deadline: ULong): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt(), clockid.toInt(), deadline.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddressWithTimeout(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, timeoutNs: ULong): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt(), clockid.toInt(), timeoutNs.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWakeByAddressAny(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsSyncWakeByAddressAny(addr.rawAddress.toString(), size.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWakeByAddressAll(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsSyncWakeByAddressAll(addr.rawAddress.toString(), size.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osUnfairLockLock(lock: OsUnfairLockT) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleOsUnfairLockLock(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockTrylock(lock: OsUnfairLockT): Boolean =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsUnfairLockTrylock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osUnfairLockUnlock(lock: OsUnfairLockT) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleOsUnfairLockUnlock(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockAssertOwner(lock: OsUnfairLockT) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleOsUnfairLockAssertOwner(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockAssertNotOwner(lock: OsUnfairLockT) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleOsUnfairLockAssertNotOwner(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osLogCreate(subsystem: ConstPtr, category: ConstPtr): io.github.kotlinmania.rustlang.libc.OsLogT =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleOsLogCreate(subsystem.rawAddress.toString(), category.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osLogTypeEnabled(oslog: io.github.kotlinmania.rustlang.libc.OsLogT, tpe: io.github.kotlinmania.rustlang.libc.OsLogTypeT): Boolean =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsLogTypeEnabled(oslog.rawAddress.toString(), tpe.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostIdMakeWithPointer(log: io.github.kotlinmania.rustlang.libc.OsLogT, ptr: ConstPtr): io.github.kotlinmania.rustlang.libc.OsSignpostIdT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsSignpostIdMakeWithPointer(log.rawAddress.toString(), ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostIdGenerate(log: io.github.kotlinmania.rustlang.libc.OsLogT): io.github.kotlinmania.rustlang.libc.OsSignpostIdT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsSignpostIdGenerate(log.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostEnabled(log: io.github.kotlinmania.rustlang.libc.OsLogT): Boolean =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOsSignpostEnabled(log.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadPolicySet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MachMsgTypeNumberT): KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleThreadPolicySet(thread.toInt(), flavor.toInt(), policyInfo.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadPolicyGet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MutPtr, getDefault: MutPtr): KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleThreadPolicyGet(thread.toInt(), flavor.toInt(), policyInfo.rawAddress.toString(), count.rawAddress.toString(), getDefault.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadInfo(targetAct: ThreadInspectT, flavor: ThreadFlavorT, threadInfoOut: ThreadInfoT, threadInfoOutCnt: MutPtr): KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleThreadInfo(targetAct.toInt(), flavor.toInt(), threadInfoOut.rawAddress.toString(), threadInfoOutCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun error(): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleError().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleBacktrace(buf.rawAddress.toString(), sz) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbols(addrs: ConstPtr, sz: CInt): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleBacktraceSymbols(addrs.rawAddress.toString(), sz).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbolsFd(addrs: ConstPtr, sz: CInt, fd: CInt) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleBacktraceSymbolsFd(addrs.rawAddress.toString(), sz, fd)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun backtraceFromFp(startfp: MutPtr, array: MutPtr, size: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleBacktraceFromFp(startfp.rawAddress.toString(), array.rawAddress.toString(), size) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceImageOffsets(array: ConstPtr, imageOffsets: MutPtr, size: CInt) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleBacktraceImageOffsets(array.rawAddress.toString(), imageOffsets.rawAddress.toString(), size)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun backtraceAsync(array: MutPtr, length: SizeT, taskId: MutPtr): SizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleBacktraceAsync(array.rawAddress.toString(), length.toString(), taskId.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleStatfs(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFstatfs(fd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kevent(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, timeout: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleKevent(kq, changelist.rawAddress.toString(), nchanges, eventlist.rawAddress.toString(), nevents, timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kevent64(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, flags: CUint, timeout: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleKevent64(kq, changelist.rawAddress.toString(), nchanges, eventlist.rawAddress.toString(), nevents, flags.toInt(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mount(src: ConstPtr, target: ConstPtr, flags: CInt, data: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMount(src.rawAddress.toString(), target.rawAddress.toString(), flags, data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmount(src: ConstPtr, fd: CInt, flags: CInt, data: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFmount(src.rawAddress.toString(), fd, flags, data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptrace(request: CInt, pid: PidT, addr: MutPtr, data: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePtrace(request, pid, addr.rawAddress.toString(), data) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun quotactl(special: ConstPtr, cmd: CInt, id: CInt, data: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleQuotactl(special.rawAddress.toString(), cmd, id, data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostname(name: ConstPtr, len: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSethostname(name.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile(fd: CInt, s: CInt, offset: OffT, len: MutPtr, hdtr: MutPtr, flags: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSendfile(fd, s, offset.toString(), len.rawAddress.toString(), hdtr.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFutimens(fd, times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flag) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleOpenpty(amaster.rawAddress.toString(), aslave.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): PidT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleForkpty(amaster.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loginTty(fd: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleLoginTty(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplocale(base: LocaleT): LocaleT =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleDuplocale(base.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freelocale(loc: LocaleT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFreelocale(loc.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localeconvL(loc: LocaleT): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleLocaleconvL(loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleNewlocale(mask, locale.rawAddress.toString(), base.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uselocale(loc: LocaleT): LocaleT =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleUselocale(loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun querylocale(mask: CInt, loc: LocaleT): ConstPtr =
    if (unixBsdAppleNapiAvailable) ConstPtr(wasmLibcUnixBsdAppleQuerylocale(mask, loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: CInt, who: io.github.kotlinmania.rustlang.libc.IdT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetpriority(which, who.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: CInt, who: io.github.kotlinmania.rustlang.libc.IdT, prio: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSetpriority(which, who.toInt(), prio) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdomainname(name: MutPtr, len: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetdomainname(name.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setdomainname(name: ConstPtr, len: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSetdomainname(name.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePreadv(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePwritev(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFgetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFsetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleListxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFlistxattr(filedes, list.rawAddress.toString(), size.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun removexattr(path: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleRemovexattr(path.rawAddress.toString(), name.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renamexNp(from: ConstPtr, to: ConstPtr, flags: CUint): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleRenamexNp(from.rawAddress.toString(), to.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameatxNp(fromfd: CInt, from: ConstPtr, tofd: CInt, to: ConstPtr, flags: CUint): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleRenameatxNp(fromfd, from.rawAddress.toString(), tofd, to.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr, flags: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFremovexattr(filedes, name.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrouplist(name: ConstPtr, basegid: CInt, groups: MutPtr, ngroups: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetgrouplist(name.rawAddress.toString(), basegid, groups.rawAddress.toString(), ngroups.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initgroups(user: ConstPtr, basegroup: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleInitgroups(user.rawAddress.toString(), basegroup) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleWaitid(idtype.toInt(), id.toInt(), infop.rawAddress.toString(), options) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun brk(addr: ConstPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleBrk(addr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sbrk(increment: CInt): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleSbrk(increment).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSettimeofday(tv.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldImageCount(): UInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleDyldImageCount().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageHeader(imageIndex: UInt): ConstPtr =
    if (unixBsdAppleNapiAvailable) ConstPtr(wasmLibcUnixBsdAppleDyldGetImageHeader(imageIndex.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageVmaddrSlide(imageIndex: UInt): IntptrT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleDyldGetImageVmaddrSlide(imageIndex.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageName(imageIndex: UInt): ConstPtr =
    if (unixBsdAppleNapiAvailable) ConstPtr(wasmLibcUnixBsdAppleDyldGetImageName(imageIndex.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawn(pid.rawAddress.toString(), path.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnp(pid.rawAddress.toString(), file.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrGetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrSetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrGetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrSetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrGetflags(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrSetflags(attr.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrGetpgroup(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrSetpgroup(attr.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetarchprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrSetarchprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), subpref.rawAddress.toString(), ocount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetarchprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrGetarchprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), subpref.rawAddress.toString(), ocount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetbinprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrGetbinprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), ocount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetbinprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnattrSetbinprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), ocount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsInit(actions.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsDestroy(actions.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsAddopen(actions.rawAddress.toString(), fd, path.rawAddress.toString(), oflag, mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsAddclose(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsAdddup2(actions.rawAddress.toString(), fd, newfd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(actions.rawAddress.toString(), path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdir(actions: MutPtr, path: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsAddchdir(actions.rawAddress.toString(), path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdir(actions: MutPtr, fd: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uname(buf: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleUname(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connectx(socket: CInt, endpoints: ConstPtr, associd: SaeAssocidT, flags: CUint, iov: ConstPtr, iovcnt: CUint, len: MutPtr, connid: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleConnectx(socket, endpoints.rawAddress.toString(), associd.toInt(), flags.toInt(), iov.rawAddress.toString(), iovcnt.toInt(), len.rawAddress.toString(), connid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun disconnectx(socket: CInt, associd: SaeAssocidT, connid: SaeConnidT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleDisconnectx(socket, associd.toInt(), connid.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleNtpAdjtime(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpGettime(buf: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleNtpGettime(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getmntinfo(mntbufp: MutPtr, flags: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetmntinfo(mntbufp.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getfsstat(mntbufp: MutPtr, bufsize: CInt, flags: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetfsstat(mntbufp.rawAddress.toString(), bufsize, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clonefile(src: ConstPtr, dst: ConstPtr, flags: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleClonefile(src.rawAddress.toString(), dst.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clonefileat(srcDirfd: CInt, src: ConstPtr, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleClonefileat(srcDirfd, src.rawAddress.toString(), dstDirfd, dst.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclonefileat(srcfd: CInt, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFclonefileat(srcfd, dstDirfd, dst.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfile(from: ConstPtr, to: ConstPtr, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleCopyfile(from.rawAddress.toString(), to.rawAddress.toString(), state.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fcopyfile(from: CInt, to: CInt, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFcopyfile(from, to, state.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateFree(s: CopyfileStateT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleCopyfileStateFree(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateAlloc(): CopyfileStateT =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleCopyfileStateAlloc().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateGet(s: CopyfileStateT, flags: UInt, dst: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleCopyfileStateGet(s.rawAddress.toString(), flags.toInt(), dst.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateSet(s: CopyfileStateT, flags: UInt, src: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleCopyfileStateSet(s.rawAddress.toString(), flags.toInt(), src.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machErrorString(errorValue: io.github.kotlinmania.rustlang.libc.MachErrorT): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleMachErrorString(errorValue).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memsetS(s: MutPtr, smax: SizeT, c: CInt, n: SizeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMemsetS(s.rawAddress.toString(), smax.toString(), c, n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memsetPattern4(b: MutPtr, pattern4: ConstPtr, len: SizeT) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMemsetPattern4(b.rawAddress.toString(), pattern4.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun memsetPattern8(b: MutPtr, pattern8: ConstPtr, len: SizeT) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMemsetPattern8(b.rawAddress.toString(), pattern8.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun memsetPattern16(b: MutPtr, pattern16: ConstPtr, len: SizeT) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMemsetPattern16(b.rawAddress.toString(), pattern16.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun strtonum(numstr: ConstPtr, minval: CLonglong, maxval: CLonglong, errstrp: MutPtr): CLonglong =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleStrtonum(numstr.rawAddress.toString(), minval.toString(), maxval.toString(), errstrp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mstats(): Mstats =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocPrintf(format: ConstPtr, vararg variadicArgs: Long) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMallocPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneCheck(zone: MutPtr): BooleanT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMallocZoneCheck(zone.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZonePrint(zone: MutPtr, verbose: BooleanT) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMallocZonePrint(zone.rawAddress.toString(), verbose)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneStatistics(zone: MutPtr, stats: MutPtr) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMallocZoneStatistics(zone.rawAddress.toString(), stats.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneLog(zone: MutPtr, address: MutPtr) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMallocZoneLog(zone.rawAddress.toString(), address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZonePrintPtrInfo(ptr: MutPtr) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMallocZonePrintPtrInfo(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocDefaultZone(): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleMallocDefaultZone().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneFromPtr(ptr: ConstPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleMallocZoneFromPtr(ptr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneMalloc(zone: MutPtr, size: SizeT): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleMallocZoneMalloc(zone.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneValloc(zone: MutPtr, size: SizeT): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleMallocZoneValloc(zone.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneCalloc(zone: MutPtr, numItems: SizeT, size: SizeT): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleMallocZoneCalloc(zone.rawAddress.toString(), numItems.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneRealloc(zone: MutPtr, ptr: MutPtr, size: SizeT): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleMallocZoneRealloc(zone.rawAddress.toString(), ptr.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneFree(zone: MutPtr, ptr: MutPtr) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleMallocZoneFree(zone.rawAddress.toString(), ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun gethostuuid(id: MutPtr, timeout: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGethostuuid(id.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gethostid(): CLong =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGethostid().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostid(hostid: CLong) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleSethostid(hostid.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cCRandomGenerateBytes(bytes: MutPtr, size: SizeT): CCRNGStatus =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleCCRandomGenerateBytes(bytes.rawAddress.toString(), size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetentropy(buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetExecutablePath(buf: MutPtr, bufsize: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleNsgetExecutablePath(buf.rawAddress.toString(), bufsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetArgv(): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleNsgetArgv().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetArgc(): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleNsgetArgc().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetEnviron(): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleNsgetEnviron().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetProgname(): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleNsgetProgname().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machVmMap(targetTask: io.github.kotlinmania.rustlang.libc.VmMapT, address: MutPtr, size: io.github.kotlinmania.rustlang.libc.MachVmSizeT, mask: io.github.kotlinmania.rustlang.libc.MachVmOffsetT, flags: CInt, obj: io.github.kotlinmania.rustlang.libc.MemEntryNamePortT, offset: io.github.kotlinmania.rustlang.libc.MemoryObjectOffsetT, copy: BooleanT, curProtection: io.github.kotlinmania.rustlang.libc.VmProtT, maxProtection: io.github.kotlinmania.rustlang.libc.VmProtT, inheritance: io.github.kotlinmania.rustlang.libc.VmInheritT): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMachVmMap(targetTask.toInt(), address.rawAddress.toString(), size.toString(), mask.toString(), flags, obj.toInt(), offset.toString(), copy, curProtection, maxProtection, inheritance.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmAllocate(targetTask: VmMapT, address: MutPtr, size: VmSizeT, flags: CInt): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleVmAllocate(targetTask.toInt(), address.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmDeallocate(targetTask: VmMapT, address: VmAddressT, size: VmSizeT): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleVmDeallocate(targetTask.toInt(), address.toString(), size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostStatistics64(hostPriv: HostT, flavor: HostFlavorT, hostInfo64Out: HostInfo64T, hostInfo64OutCnt: MutPtr): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleHostStatistics64(hostPriv.toInt(), flavor, hostInfo64Out.rawAddress.toString(), hostInfo64OutCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostProcessorInfo(host: HostT, flavor: ProcessorFlavorT, outProcessorCount: MutPtr, outProcessorInfo: MutPtr, outProcessorInfoCnt: MutPtr): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleHostProcessorInfo(host.toInt(), flavor, outProcessorCount.rawAddress.toString(), outProcessorInfo.rawAddress.toString(), outProcessorInfoCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskForPid(host: io.github.kotlinmania.rustlang.libc.MachPortT, pid: PidT, task: MutPtr): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleTaskForPid(host.toInt(), pid, task.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskInfo(host: io.github.kotlinmania.rustlang.libc.MachPortT, flavor: TaskFlavorT, taskInfoOut: TaskInfoT, taskInfoCount: MutPtr): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleTaskInfo(host.toInt(), flavor.toInt(), taskInfoOut.rawAddress.toString(), taskInfoCount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskCreate(targetTask: io.github.kotlinmania.rustlang.libc.TaskT, ledgers: io.github.kotlinmania.rustlang.libc.LedgerArrayT, ledgersCnt: io.github.kotlinmania.rustlang.libc.MachMsgTypeNumberT, inheritMemory: BooleanT, childTask: MutPtr): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleTaskCreate(targetTask.toInt(), ledgers.rawAddress.toString(), ledgersCnt.toInt(), inheritMemory, childTask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskTerminate(targetTask: io.github.kotlinmania.rustlang.libc.TaskT): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleTaskTerminate(targetTask.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskThreads(targetTask: io.github.kotlinmania.rustlang.libc.TaskInspectT, actList: MutPtr, actListCnt: MutPtr): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleTaskThreads(targetTask.toInt(), actList.rawAddress.toString(), actListCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostStatistics(hostPriv: HostT, flavor: HostFlavorT, hostInfoOut: HostInfoT, hostInfoOutCnt: MutPtr): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleHostStatistics(hostPriv.toInt(), flavor, hostInfoOut.rawAddress.toString(), hostInfoOutCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysdirStartSearchPathEnumeration(dir: SysdirSearchPathDirectoryT, domainMask: SysdirSearchPathDomainMaskT): io.github.kotlinmania.rustlang.libc.SysdirSearchPathEnumerationState =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysdirGetNextSearchPathEnumeration(state: io.github.kotlinmania.rustlang.libc.SysdirSearchPathEnumerationState, path: MutPtr): io.github.kotlinmania.rustlang.libc.SysdirSearchPathEnumerationState =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(state.toInt(), path.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetattrlist(path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFgetattrlist(fd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlistat(fd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: CUlong): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetattrlistat(fd, path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSetattrlist(path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFsetattrlist(fd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setattrlistat(dirFd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleSetattrlistat(dirFd, path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlistbulk(dirfd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: ULong): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleGetattrlistbulk(dirfd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocSize(ptr: ConstPtr): SizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMallocSize(ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocGoodSize(size: SizeT): SizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMallocGoodSize(size.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: MutPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleDirname(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun basename(path: MutPtr): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleBasename(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMkfifoat(dirfd, pathname.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleMknodat(dirfd, pathname.rawAddress.toString(), mode.toInt(), dev) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freadlink(fd: CInt, buf: MutPtr, size: SizeT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleFreadlink(fd, buf.rawAddress.toString(), size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvP(file: ConstPtr, searchPath: ConstPtr, argv: ConstPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleExecvP(file.rawAddress.toString(), searchPath.rawAddress.toString(), argv.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, arg: MutPtr, compar: QsortRComparCallback?) {
    if (unixBsdAppleNapiAvailable) {
        wasmLibcUnixBsdAppleQsortR(base.rawAddress.toString(), num.toString(), size.toString(), arg.rawAddress.toString(), 0L.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun clockSettime(clockId: io.github.kotlinmania.rustlang.libc.ClockidT, tp: ConstPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListpids(t: UInt, typeinfo: UInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcListpids(t.toInt(), typeinfo.toInt(), buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListallpids(buffer: MutPtr, buffersize: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcListallpids(buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListpgrppids(pgrpid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcListpgrppids(pgrpid, buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListchildpids(ppid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcListchildpids(ppid, buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidinfo(pid: CInt, flavor: CInt, arg: ULong, buffer: MutPtr, buffersize: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcPidinfo(pid, flavor, arg.toString(), buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidfdinfo(pid: CInt, fd: CInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcPidfdinfo(pid, fd, flavor, buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidfileportinfo(pid: CInt, fileport: UInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcPidfileportinfo(pid, fileport.toInt(), flavor, buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidpath(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcPidpath(pid, buffer.rawAddress.toString(), buffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procName(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcName(pid, buffer.rawAddress.toString(), buffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procRegionfilename(pid: CInt, address: ULong, buffer: MutPtr, buffersize: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcRegionfilename(pid, address.toString(), buffer.rawAddress.toString(), buffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procKmsgbuf(buffer: MutPtr, buffersize: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcKmsgbuf(buffer.rawAddress.toString(), buffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procLibversion(major: MutPtr, minor: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcLibversion(major.rawAddress.toString(), minor.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidRusage(pid: CInt, flavor: CInt, buffer: MutPtr): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcPidRusage(pid, flavor, buffer.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetNoSmt(): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcSetNoSmt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetthreadNoSmt(): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcSetthreadNoSmt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetCsm(flags: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcSetCsm(flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetthreadCsm(flags: UInt): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleProcSetthreadCsm(flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleMemmem(haystack.rawAddress.toString(), haystacklen.toString(), needle.rawAddress.toString(), needlelen.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskSetInfo(targetTask: io.github.kotlinmania.rustlang.libc.TaskT, flavor: io.github.kotlinmania.rustlang.libc.TaskFlavorT, taskInfoIn: io.github.kotlinmania.rustlang.libc.TaskInfoT, taskInfoInCnt: io.github.kotlinmania.rustlang.libc.MachMsgTypeNumberT): io.github.kotlinmania.rustlang.libc.KernReturnT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleTaskSetInfo(targetTask.toInt(), flavor.toInt(), taskInfoIn.rawAddress.toString(), taskInfoInCnt.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    if (unixBsdAppleNapiAvailable) MutPtr(wasmLibcUnixBsdAppleIconvOpen(tocode.rawAddress.toString(), fromcode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleIconv(cd.rawAddress.toString(), inbuf.rawAddress.toString(), inbytesleft.rawAddress.toString(), outbuf.rawAddress.toString(), outbytesleft.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconvClose(cd: IconvT): CInt =
    if (unixBsdAppleNapiAvailable) wasmLibcUnixBsdAppleIconvClose(cd.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
