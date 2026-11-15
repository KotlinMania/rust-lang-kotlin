@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.IntptrT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.b64.aarch64.BooleanT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixBsdAppleNapiAddon(): Boolean

private val libcUnixBsdAppleNapiAvailable: Boolean = initLibcUnixBsdAppleNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetMACHTASKSELF()")
private external fun wasmRustlangLibcUnixBsdAppleGetMACHTASKSELF(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSetMACHTASKSELF(p0)")
private external fun wasmRustlangLibcUnixBsdAppleSetMACHTASKSELF(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetVMPAGESIZE()")
private external fun wasmRustlangLibcUnixBsdAppleGetVMPAGESIZE(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSetgrent()")
private external fun wasmRustlangLibcUnixBsdAppleSetgrent()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAioRead(p0)")
private external fun wasmRustlangLibcUnixBsdAppleAioRead(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAioWrite(p0)")
private external fun wasmRustlangLibcUnixBsdAppleAioWrite(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAioFsync(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleAioFsync(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAioError(p0)")
private external fun wasmRustlangLibcUnixBsdAppleAioError(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAioReturn(p0)")
private external fun wasmRustlangLibcUnixBsdAppleAioReturn(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAioSuspend(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleAioSuspend(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAioCancel(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleAioCancel(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleChflags(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleChflags(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFchflags(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleFchflags(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleLchflags(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleLchflags(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleLchmod(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleLchmod(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleClockGetres(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleClockGetres(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleClockGettime(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleClockGettime(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleLioListio(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleLioListio(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleDirfd(p0)")
private external fun wasmRustlangLibcUnixBsdAppleDirfd(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleLutimes(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleLutimes(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGettimeofday(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleGettimeofday(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetutxent()")
private external fun wasmRustlangLibcUnixBsdAppleGetutxent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetutxid(p0)")
private external fun wasmRustlangLibcUnixBsdAppleGetutxid(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetutxline(p0)")
private external fun wasmRustlangLibcUnixBsdAppleGetutxline(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePututxline(p0)")
private external fun wasmRustlangLibcUnixBsdApplePututxline(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSetutxent()")
private external fun wasmRustlangLibcUnixBsdAppleSetutxent()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleEndutxent()")
private external fun wasmRustlangLibcUnixBsdAppleEndutxent()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleUtmpxname(p0)")
private external fun wasmRustlangLibcUnixBsdAppleUtmpxname(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAsctime(p0)")
private external fun wasmRustlangLibcUnixBsdAppleAsctime(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleCtime(p0)")
private external fun wasmRustlangLibcUnixBsdAppleCtime(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetdate(p0)")
private external fun wasmRustlangLibcUnixBsdAppleGetdate(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleStrptime(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleStrptime(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleAsctimeR(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleAsctimeR(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleCtimeR(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleCtimeR(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetnameinfo(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixBsdAppleGetnameinfo(p0: String, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMincore(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleMincore(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSysctlnametomib(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleSysctlnametomib(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMprotect(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleMprotect(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSemget(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleSemget(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSemctl(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcUnixBsdAppleSemctl(p0: Int, p1: Int, p2: Int, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSemop(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleSemop(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleShmOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixBsdAppleShmOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFtok(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleFtok(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleShmat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleShmat(p0: Int, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleShmdt(p0)")
private external fun wasmRustlangLibcUnixBsdAppleShmdt(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleShmctl(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleShmctl(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleShmget(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleShmget(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSysctl(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleSysctl(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSysctlbyname(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleSysctlbyname(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMachAbsoluteTime()")
private external fun wasmRustlangLibcUnixBsdAppleMachAbsoluteTime(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMachTimebaseInfo(p0)")
private external fun wasmRustlangLibcUnixBsdAppleMachTimebaseInfo(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMachHostSelf()")
private external fun wasmRustlangLibcUnixBsdAppleMachHostSelf(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMachThreadSelf()")
private external fun wasmRustlangLibcUnixBsdAppleMachThreadSelf(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadAttrGetscope(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePthreadAttrGetscope(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadAttrGetstackaddr(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePthreadAttrGetstackaddr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadAttrGetdetachstate(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePthreadAttrGetdetachstate(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadAttrSetscope(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePthreadAttrSetscope(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadAttrSetstackaddr(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePthreadAttrSetstackaddr(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadSetnameNp(p0)")
private external fun wasmRustlangLibcUnixBsdApplePthreadSetnameNp(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadGetnameNp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdApplePthreadGetnameNp(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadMachThreadNp(p0)")
private external fun wasmRustlangLibcUnixBsdApplePthreadMachThreadNp(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadFromMachThreadNp(p0)")
private external fun wasmRustlangLibcUnixBsdApplePthreadFromMachThreadNp(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadGetStackaddrNp(p0)")
private external fun wasmRustlangLibcUnixBsdApplePthreadGetStackaddrNp(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadGetStacksizeNp(p0)")
private external fun wasmRustlangLibcUnixBsdApplePthreadGetStacksizeNp(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadMainNp()")
private external fun wasmRustlangLibcUnixBsdApplePthreadMainNp(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadThreadidNp(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePthreadThreadidNp(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadJitWriteProtectNp(p0)")
private external fun wasmRustlangLibcUnixBsdApplePthreadJitWriteProtectNp(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp()")
private external fun wasmRustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()")
private external fun wasmRustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePthreadCpuNumberNp(p0)")
private external fun wasmRustlangLibcUnixBsdApplePthreadCpuNumberNp(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsSyncWaitOnAddress(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleOsSyncWaitOnAddress(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(p0: String, p1: String, p2: String, p3: Int, p4: Int, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(p0: String, p1: String, p2: String, p3: Int, p4: Int, p5: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsSyncWakeByAddressAny(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleOsSyncWakeByAddressAny(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsSyncWakeByAddressAll(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleOsSyncWakeByAddressAll(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsUnfairLockLock(p0)")
private external fun wasmRustlangLibcUnixBsdAppleOsUnfairLockLock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsUnfairLockTrylock(p0)")
private external fun wasmRustlangLibcUnixBsdAppleOsUnfairLockTrylock(p0: String): Boolean

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsUnfairLockUnlock(p0)")
private external fun wasmRustlangLibcUnixBsdAppleOsUnfairLockUnlock(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsUnfairLockAssertOwner(p0)")
private external fun wasmRustlangLibcUnixBsdAppleOsUnfairLockAssertOwner(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner(p0)")
private external fun wasmRustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsLogCreate(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleOsLogCreate(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsLogTypeEnabled(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleOsLogTypeEnabled(p0: String, p1: Int): Boolean

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsSignpostIdGenerate(p0)")
private external fun wasmRustlangLibcUnixBsdAppleOsSignpostIdGenerate(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOsSignpostEnabled(p0)")
private external fun wasmRustlangLibcUnixBsdAppleOsSignpostEnabled(p0: String): Boolean

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleThreadPolicySet(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleThreadPolicySet(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleThreadPolicyGet(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleThreadPolicyGet(p0: Int, p1: Int, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleThreadInfo(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleThreadInfo(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleError()")
private external fun wasmRustlangLibcUnixBsdAppleError(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleBacktrace(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleBacktrace(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleBacktraceSymbols(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleBacktraceSymbols(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleBacktraceSymbolsFd(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleBacktraceSymbolsFd(p0: String, p1: Int, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleBacktraceFromFp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleBacktraceFromFp(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleBacktraceImageOffsets(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleBacktraceImageOffsets(p0: String, p1: String, p2: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleBacktraceAsync(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleBacktraceAsync(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleStatfs(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleStatfs(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFstatfs(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleFstatfs(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleKevent(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleKevent(p0: Int, p1: String, p2: Int, p3: String, p4: Int, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleKevent64(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixBsdAppleKevent64(p0: Int, p1: String, p2: Int, p3: String, p4: Int, p5: Int, p6: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMount(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleMount(p0: String, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFmount(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleFmount(p0: String, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePtrace(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdApplePtrace(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleQuotactl(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleQuotactl(p0: String, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSethostname(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleSethostname(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSendfile(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleSendfile(p0: Int, p1: Int, p2: String, p3: String, p4: String, p5: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFutimens(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleFutimens(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleUtimensat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleUtimensat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleOpenpty(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleOpenpty(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleForkpty(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleForkpty(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleLoginTty(p0)")
private external fun wasmRustlangLibcUnixBsdAppleLoginTty(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleDuplocale(p0)")
private external fun wasmRustlangLibcUnixBsdAppleDuplocale(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFreelocale(p0)")
private external fun wasmRustlangLibcUnixBsdAppleFreelocale(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleLocaleconvL(p0)")
private external fun wasmRustlangLibcUnixBsdAppleLocaleconvL(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleNewlocale(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleNewlocale(p0: Int, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleUselocale(p0)")
private external fun wasmRustlangLibcUnixBsdAppleUselocale(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleQuerylocale(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleQuerylocale(p0: Int, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetpriority(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleGetpriority(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSetpriority(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleSetpriority(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetdomainname(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleGetdomainname(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSetdomainname(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleSetdomainname(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePreadv(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdApplePreadv(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePwritev(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdApplePwritev(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetxattr(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleGetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFgetxattr(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleFgetxattr(p0: Int, p1: String, p2: String, p3: String, p4: Int, p5: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSetxattr(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleSetxattr(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFsetxattr(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleFsetxattr(p0: Int, p1: String, p2: String, p3: String, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleListxattr(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleListxattr(p0: String, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFlistxattr(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleFlistxattr(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleRemovexattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleRemovexattr(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleRenamexNp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleRenamexNp(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleRenameatxNp(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleRenameatxNp(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFremovexattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleFremovexattr(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetgrouplist(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleGetgrouplist(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleInitgroups(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleInitgroups(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleWaitid(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleWaitid(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleBrk(p0)")
private external fun wasmRustlangLibcUnixBsdAppleBrk(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSbrk(p0)")
private external fun wasmRustlangLibcUnixBsdAppleSbrk(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSettimeofday(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleSettimeofday(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleDyldImageCount()")
private external fun wasmRustlangLibcUnixBsdAppleDyldImageCount(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleDyldGetImageHeader(p0)")
private external fun wasmRustlangLibcUnixBsdAppleDyldGetImageHeader(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(p0)")
private external fun wasmRustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleDyldGetImageName(p0)")
private external fun wasmRustlangLibcUnixBsdAppleDyldGetImageName(p0: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawn(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawn(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnp(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnp(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrInit(p0)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrDestroy(p0)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrGetsigmask(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrGetsigmask(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrSetsigmask(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrSetsigmask(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrGetflags(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrGetflags(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrSetflags(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrSetflags(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrGetpgroup(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrGetpgroup(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrSetpgroup(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrSetpgroup(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsInit(p0)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy(p0)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen(p0: String, p1: Int, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(p0, p1)")
private external fun wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleUname(p0)")
private external fun wasmRustlangLibcUnixBsdAppleUname(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleConnectx(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcUnixBsdAppleConnectx(p0: Int, p1: String, p2: Int, p3: Int, p4: String, p5: Int, p6: String, p7: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleDisconnectx(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleDisconnectx(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleNtpAdjtime(p0)")
private external fun wasmRustlangLibcUnixBsdAppleNtpAdjtime(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleNtpGettime(p0)")
private external fun wasmRustlangLibcUnixBsdAppleNtpGettime(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetmntinfo(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleGetmntinfo(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetfsstat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleGetfsstat(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleClonefile(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleClonefile(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleClonefileat(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleClonefileat(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFclonefileat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleFclonefileat(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleCopyfile(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleCopyfile(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFcopyfile(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleFcopyfile(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleCopyfileStateFree(p0)")
private external fun wasmRustlangLibcUnixBsdAppleCopyfileStateFree(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleCopyfileStateAlloc()")
private external fun wasmRustlangLibcUnixBsdAppleCopyfileStateAlloc(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleCopyfileStateGet(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleCopyfileStateGet(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleCopyfileStateSet(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleCopyfileStateSet(p0: String, p1: Int, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMachErrorString(p0)")
private external fun wasmRustlangLibcUnixBsdAppleMachErrorString(p0: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMemsetS(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleMemsetS(p0: String, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMemsetPattern4(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleMemsetPattern4(p0: String, p1: String, p2: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMemsetPattern8(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleMemsetPattern8(p0: String, p1: String, p2: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMemsetPattern16(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleMemsetPattern16(p0: String, p1: String, p2: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleStrtonum(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleStrtonum(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocPrintf(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleMallocPrintf(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneCheck(p0)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneCheck(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZonePrint(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZonePrint(p0: String, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneStatistics(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneStatistics(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneLog(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneLog(p0: String, p1: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZonePrintPtrInfo(p0)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZonePrintPtrInfo(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocDefaultZone()")
private external fun wasmRustlangLibcUnixBsdAppleMallocDefaultZone(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneFromPtr(p0)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneFromPtr(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneMalloc(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneMalloc(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneValloc(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneValloc(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneCalloc(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneCalloc(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneRealloc(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneRealloc(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocZoneFree(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleMallocZoneFree(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGethostuuid(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleGethostuuid(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGethostid()")
private external fun wasmRustlangLibcUnixBsdAppleGethostid(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSethostid(p0)")
private external fun wasmRustlangLibcUnixBsdAppleSethostid(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleCCRandomGenerateBytes(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleCCRandomGenerateBytes(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetentropy(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleGetentropy(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleNsgetExecutablePath(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleNsgetExecutablePath(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleNsgetArgv()")
private external fun wasmRustlangLibcUnixBsdAppleNsgetArgv(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleNsgetArgc()")
private external fun wasmRustlangLibcUnixBsdAppleNsgetArgc(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleNsgetEnviron()")
private external fun wasmRustlangLibcUnixBsdAppleNsgetEnviron(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleNsgetProgname()")
private external fun wasmRustlangLibcUnixBsdAppleNsgetProgname(): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMachVmMap(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)")
private external fun wasmRustlangLibcUnixBsdAppleMachVmMap(p0: Int, p1: String, p2: String, p3: String, p4: Int, p5: Int, p6: String, p7: Int, p8: Int, p9: Int, p10: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleVmAllocate(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleVmAllocate(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleVmDeallocate(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleVmDeallocate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleHostStatistics64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleHostStatistics64(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleHostProcessorInfo(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleHostProcessorInfo(p0: Int, p1: Int, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleTaskForPid(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleTaskForPid(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleTaskInfo(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleTaskInfo(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleTaskCreate(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleTaskCreate(p0: Int, p1: String, p2: Int, p3: Int, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleTaskTerminate(p0)")
private external fun wasmRustlangLibcUnixBsdAppleTaskTerminate(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleTaskThreads(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleTaskThreads(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleHostStatistics(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleHostStatistics(p0: Int, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetattrlist(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleGetattrlist(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFgetattrlist(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleFgetattrlist(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetattrlistat(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleGetattrlistat(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSetattrlist(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleSetattrlist(p0: String, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFsetattrlist(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleFsetattrlist(p0: Int, p1: String, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleSetattrlistat(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixBsdAppleSetattrlistat(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleGetattrlistbulk(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleGetattrlistbulk(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocSize(p0)")
private external fun wasmRustlangLibcUnixBsdAppleMallocSize(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMallocGoodSize(p0)")
private external fun wasmRustlangLibcUnixBsdAppleMallocGoodSize(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleDirname(p0)")
private external fun wasmRustlangLibcUnixBsdAppleDirname(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleBasename(p0)")
private external fun wasmRustlangLibcUnixBsdAppleBasename(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMkfifoat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleMkfifoat(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMknodat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleMknodat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleFreadlink(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleFreadlink(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleExecvP(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleExecvP(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleQsortR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleQsortR(p0: String, p1: String, p2: String, p3: String, p4: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleClockSettime(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleClockSettime(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcListpids(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleProcListpids(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcListallpids(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleProcListallpids(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcListpgrppids(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleProcListpgrppids(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcListchildpids(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleProcListchildpids(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcPidinfo(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleProcPidinfo(p0: Int, p1: Int, p2: String, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcPidfdinfo(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleProcPidfdinfo(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcPidfileportinfo(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleProcPidfileportinfo(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcPidpath(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleProcPidpath(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcName(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleProcName(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcRegionfilename(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleProcRegionfilename(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcKmsgbuf(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleProcKmsgbuf(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcLibversion(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleProcLibversion(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcPidRusage(p0, p1, p2)")
private external fun wasmRustlangLibcUnixBsdAppleProcPidRusage(p0: Int, p1: Int, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcSetNoSmt()")
private external fun wasmRustlangLibcUnixBsdAppleProcSetNoSmt(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcSetthreadNoSmt()")
private external fun wasmRustlangLibcUnixBsdAppleProcSetthreadNoSmt(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcSetCsm(p0)")
private external fun wasmRustlangLibcUnixBsdAppleProcSetCsm(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleProcSetthreadCsm(p0)")
private external fun wasmRustlangLibcUnixBsdAppleProcSetthreadCsm(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleMemmem(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleMemmem(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleTaskSetInfo(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixBsdAppleTaskSetInfo(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleIconvOpen(p0, p1)")
private external fun wasmRustlangLibcUnixBsdAppleIconvOpen(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleIconv(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsdAppleIconv(p0: String, p1: String, p2: String, p3: String, p4: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsdAppleIconvClose(p0)")
private external fun wasmRustlangLibcUnixBsdAppleIconvClose(p0: String): Int

public actual var MACH_TASK_SELF: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT
    get() = if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetMACHTASKSELF().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (libcUnixBsdAppleNapiAvailable) {
            wasmRustlangLibcUnixBsdAppleSetMACHTASKSELF(value.toInt())
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual val VM_PAGE_SIZE: VmSizeT
    get() = if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetVMPAGESIZE().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgrent() {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleSetgrent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun aioRead(aiocbp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleAioRead(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleAioWrite(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleAioFsync(op, aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioError(aiocbp: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleAioError(aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleAioReturn(aiocbp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleAioSuspend(aiocbList.rawAddress.toString(), nitems, timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleAioCancel(fd, aiocbp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chflags(path: ConstPtr, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleChflags(path.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchflags(fd: CInt, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFchflags(fd, flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lchflags(path: ConstPtr, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleLchflags(path.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lchmod(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleLchmod(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleClockGetres(clkId.toInt(), tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleClockGettime(clkId.toInt(), tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleLioListio(mode, aiocbList.rawAddress.toString(), nitems, sevp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirfd(dirp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleDirfd(dirp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleLutimes(file.rawAddress.toString(), times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxent(): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleGetutxent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxid(ut: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleGetutxid(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getutxline(ut: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleGetutxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pututxline(ut: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdApplePututxline(ut.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setutxent() {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleSetutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun endutxent() {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleEndutxent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun utmpxname(file: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleUtmpxname(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctime(tm: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleAsctime(tm.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctime(clock: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleCtime(clock.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdate(datestr: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleGetdate(datestr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strptime(buf: ConstPtr, format: ConstPtr, timeptr: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleStrptime(buf.rawAddress.toString(), format.rawAddress.toString(), timeptr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(tm: ConstPtr, result: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleAsctimeR(tm.rawAddress.toString(), result.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctimeR(clock: ConstPtr, result: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleCtimeR(clock.rawAddress.toString(), result.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetnameinfo(sa.rawAddress.toString(), salen.toInt(), host.rawAddress.toString(), hostlen.toInt(), serv.rawAddress.toString(), servlen.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mincore(addr: ConstPtr, len: SizeT, vec: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMincore(addr.rawAddress.toString(), len.toString(), vec.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctlnametomib(name: ConstPtr, mibp: MutPtr, sizep: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSysctlnametomib(name.rawAddress.toString(), mibp.rawAddress.toString(), sizep.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMprotect(addr.rawAddress.toString(), len.toString(), prot) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semget(key: KeyT, nsems: CInt, semflg: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSemget(key, nsems, semflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSemop(semid, sops.rawAddress.toString(), nsops.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleShmOpen(name.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFtok(pathname.rawAddress.toString(), projId) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleShmat(shmid, shmaddr.rawAddress.toString(), shmflg).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleShmdt(shmaddr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleShmctl(shmid, cmd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleShmget(key, size.toString(), shmflg) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctl(name: MutPtr, namelen: CUint, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSysctl(name.rawAddress.toString(), namelen.toInt(), oldp.rawAddress.toString(), oldlenp.rawAddress.toString(), newp.rawAddress.toString(), newlen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysctlbyname(name: ConstPtr, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSysctlbyname(name.rawAddress.toString(), oldp.rawAddress.toString(), oldlenp.rawAddress.toString(), newp.rawAddress.toString(), newlen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machAbsoluteTime(): ULong =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMachAbsoluteTime().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machTimebaseInfo(info: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMachTimebaseInfo(info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machHostSelf(): MachPortT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMachHostSelf().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machThreadSelf(): MachPortT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMachThreadSelf().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondTimedwaitRelativeNp(cond: MutPtr, lock: MutPtr, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(cond.rawAddress.toString(), lock.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetscope(attr: ConstPtr, contentionscope: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadAttrGetscope(attr.rawAddress.toString(), contentionscope.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstackaddr(attr: ConstPtr, stackaddr: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadAttrGetstackaddr(attr.rawAddress.toString(), stackaddr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, detachstate: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadAttrGetdetachstate(attr.rawAddress.toString(), detachstate.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetscope(attr: MutPtr, contentionscope: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadAttrSetscope(attr.rawAddress.toString(), contentionscope) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstackaddr(attr: MutPtr, stackaddr: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadAttrSetstackaddr(attr.rawAddress.toString(), stackaddr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetnameNp(name: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadSetnameNp(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadGetnameNp(thread.toLong().toString(), name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMachThreadNp(thread: PthreadT): io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadMachThreadNp(thread.toLong().toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadFromMachThreadNp(port: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT): PthreadT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadFromMachThreadNp(port.toInt()).toLong().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetStackaddrNp(thread: PthreadT): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdApplePthreadGetStackaddrNp(thread.toLong().toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetStacksizeNp(thread: PthreadT): SizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadGetStacksizeNp(thread.toLong().toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMainNp(): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadMainNp() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadThreadidNp(thread: PthreadT, threadId: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadThreadidNp(thread.toLong().toString(), threadId.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteProtectNp(enabled: CInt) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdApplePthreadJitWriteProtectNp(enabled)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pthreadJitWriteProtectSupportedNp(): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteWithCallbackNp(callback: io.github.kotlinmania.rustlang.unix.bsd.apple.PthreadJitWriteCallbackT, ctx: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp(0L.toString(), ctx.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJitWriteFreezeCallbacksNp() {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pthreadCpuNumberNp(cpuNumberOut: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePthreadCpuNumberNp(cpuNumberOut.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddress(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsSyncWaitOnAddress(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddressWithDeadline(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, deadline: ULong): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt(), clockid.toInt(), deadline.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWaitOnAddressWithTimeout(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, timeoutNs: ULong): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(addr.rawAddress.toString(), value.toString(), size.toString(), flags.toInt(), clockid.toInt(), timeoutNs.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWakeByAddressAny(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsSyncWakeByAddressAny(addr.rawAddress.toString(), size.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSyncWakeByAddressAll(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsSyncWakeByAddressAll(addr.rawAddress.toString(), size.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osUnfairLockLock(lock: OsUnfairLockT) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleOsUnfairLockLock(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockTrylock(lock: OsUnfairLockT): Boolean =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsUnfairLockTrylock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osUnfairLockUnlock(lock: OsUnfairLockT) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleOsUnfairLockUnlock(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockAssertOwner(lock: OsUnfairLockT) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleOsUnfairLockAssertOwner(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osUnfairLockAssertNotOwner(lock: OsUnfairLockT) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner(lock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun osLogCreate(subsystem: ConstPtr, category: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleOsLogCreate(subsystem.rawAddress.toString(), category.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osLogTypeEnabled(oslog: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, tpe: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogTypeT): Boolean =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsLogTypeEnabled(oslog.rawAddress.toString(), tpe.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostIdMakeWithPointer(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, ptr: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer(log.rawAddress.toString(), ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostIdGenerate(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsSignpostIdGenerate(log.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun osSignpostEnabled(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): Boolean =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOsSignpostEnabled(log.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadPolicySet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MachMsgTypeNumberT): KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleThreadPolicySet(thread.toInt(), flavor.toInt(), policyInfo.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadPolicyGet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MutPtr, getDefault: MutPtr): KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleThreadPolicyGet(thread.toInt(), flavor.toInt(), policyInfo.rawAddress.toString(), count.rawAddress.toString(), getDefault.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun threadInfo(targetAct: ThreadInspectT, flavor: ThreadFlavorT, threadInfoOut: ThreadInfoT, threadInfoOutCnt: MutPtr): KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleThreadInfo(targetAct.toInt(), flavor.toInt(), threadInfoOut.rawAddress.toString(), threadInfoOutCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun error(): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleError().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleBacktrace(buf.rawAddress.toString(), sz) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbols(addrs: ConstPtr, sz: CInt): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleBacktraceSymbols(addrs.rawAddress.toString(), sz).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceSymbolsFd(addrs: ConstPtr, sz: CInt, fd: CInt) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleBacktraceSymbolsFd(addrs.rawAddress.toString(), sz, fd)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun backtraceFromFp(startfp: MutPtr, array: MutPtr, size: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleBacktraceFromFp(startfp.rawAddress.toString(), array.rawAddress.toString(), size) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun backtraceImageOffsets(array: ConstPtr, imageOffsets: MutPtr, size: CInt) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleBacktraceImageOffsets(array.rawAddress.toString(), imageOffsets.rawAddress.toString(), size)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun backtraceAsync(array: MutPtr, length: SizeT, taskId: MutPtr): SizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleBacktraceAsync(array.rawAddress.toString(), length.toString(), taskId.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleStatfs(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFstatfs(fd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kevent(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleKevent(kq, changelist.rawAddress.toString(), nchanges, eventlist.rawAddress.toString(), nevents, timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kevent64(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, flags: CUint, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleKevent64(kq, changelist.rawAddress.toString(), nchanges, eventlist.rawAddress.toString(), nevents, flags.toInt(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mount(src: ConstPtr, target: ConstPtr, flags: CInt, data: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMount(src.rawAddress.toString(), target.rawAddress.toString(), flags, data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmount(src: ConstPtr, fd: CInt, flags: CInt, data: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFmount(src.rawAddress.toString(), fd, flags, data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptrace(request: CInt, pid: PidT, addr: MutPtr, data: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePtrace(request, pid, addr.rawAddress.toString(), data) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun quotactl(special: ConstPtr, cmd: CInt, id: CInt, data: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleQuotactl(special.rawAddress.toString(), cmd, id, data.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostname(name: ConstPtr, len: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSethostname(name.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendfile(fd: CInt, s: CInt, offset: OffT, len: MutPtr, hdtr: MutPtr, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSendfile(fd, s, offset.toString(), len.rawAddress.toString(), hdtr.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFutimens(fd, times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flag) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleOpenpty(amaster.rawAddress.toString(), aslave.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): PidT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleForkpty(amaster.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loginTty(fd: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleLoginTty(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplocale(base: LocaleT): LocaleT =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleDuplocale(base.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freelocale(loc: LocaleT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFreelocale(loc.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localeconvL(loc: LocaleT): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleLocaleconvL(loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleNewlocale(mask, locale.rawAddress.toString(), base.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uselocale(loc: LocaleT): LocaleT =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleUselocale(loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun querylocale(mask: CInt, loc: LocaleT): ConstPtr =
    if (libcUnixBsdAppleNapiAvailable) ConstPtr(wasmRustlangLibcUnixBsdAppleQuerylocale(mask, loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetpriority(which, who.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT, prio: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSetpriority(which, who.toInt(), prio) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdomainname(name: MutPtr, len: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetdomainname(name.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setdomainname(name: ConstPtr, len: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSetdomainname(name.rawAddress.toString(), len) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePreadv(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePwritev(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFgetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSetxattr(path.rawAddress.toString(), name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFsetxattr(filedes, name.rawAddress.toString(), value.rawAddress.toString(), size.toString(), position.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleListxattr(path.rawAddress.toString(), list.rawAddress.toString(), size.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFlistxattr(filedes, list.rawAddress.toString(), size.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun removexattr(path: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleRemovexattr(path.rawAddress.toString(), name.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renamexNp(from: ConstPtr, to: ConstPtr, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleRenamexNp(from.rawAddress.toString(), to.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameatxNp(fromfd: CInt, from: ConstPtr, tofd: CInt, to: ConstPtr, flags: CUint): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleRenameatxNp(fromfd, from.rawAddress.toString(), tofd, to.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fremovexattr(filedes: CInt, name: ConstPtr, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFremovexattr(filedes, name.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgrouplist(name: ConstPtr, basegid: CInt, groups: MutPtr, ngroups: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetgrouplist(name.rawAddress.toString(), basegid, groups.rawAddress.toString(), ngroups.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initgroups(user: ConstPtr, basegroup: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleInitgroups(user.rawAddress.toString(), basegroup) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleWaitid(idtype.toInt(), id.toInt(), infop.rawAddress.toString(), options) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun brk(addr: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleBrk(addr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sbrk(increment: CInt): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleSbrk(increment).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSettimeofday(tv.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldImageCount(): UInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleDyldImageCount().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageHeader(imageIndex: UInt): ConstPtr =
    if (libcUnixBsdAppleNapiAvailable) ConstPtr(wasmRustlangLibcUnixBsdAppleDyldGetImageHeader(imageIndex.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageVmaddrSlide(imageIndex: UInt): IntptrT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(imageIndex.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dyldGetImageName(imageIndex: UInt): ConstPtr =
    if (libcUnixBsdAppleNapiAvailable) ConstPtr(wasmRustlangLibcUnixBsdAppleDyldGetImageName(imageIndex.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawn(pid.rawAddress.toString(), path.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnp(pid.rawAddress.toString(), file.rawAddress.toString(), fileActions.rawAddress.toString(), attrp.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrGetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrSetsigmask(attr.rawAddress.toString(), default.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrGetflags(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrSetflags(attr.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrGetpgroup(attr.rawAddress.toString(), flags.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrSetpgroup(attr.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetarchprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), subpref.rawAddress.toString(), ocount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetarchprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), subpref.rawAddress.toString(), ocount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrGetbinprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), ocount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnattrSetbinprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp(attr.rawAddress.toString(), count.toString(), pref.rawAddress.toString(), ocount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsInit(actions.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy(actions.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen(actions.rawAddress.toString(), fd, path.rawAddress.toString(), oflag, mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2(actions.rawAddress.toString(), fd, newfd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(actions.rawAddress.toString(), path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddchdir(actions: MutPtr, path: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir(actions.rawAddress.toString(), path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixSpawnFileActionsAddfchdir(actions: MutPtr, fd: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(actions.rawAddress.toString(), fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uname(buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleUname(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connectx(socket: CInt, endpoints: ConstPtr, associd: SaeAssocidT, flags: CUint, iov: ConstPtr, iovcnt: CUint, len: MutPtr, connid: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleConnectx(socket, endpoints.rawAddress.toString(), associd.toInt(), flags.toInt(), iov.rawAddress.toString(), iovcnt.toInt(), len.rawAddress.toString(), connid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun disconnectx(socket: CInt, associd: SaeAssocidT, connid: SaeConnidT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleDisconnectx(socket, associd.toInt(), connid.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleNtpAdjtime(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntpGettime(buf: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleNtpGettime(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getmntinfo(mntbufp: MutPtr, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetmntinfo(mntbufp.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getfsstat(mntbufp: MutPtr, bufsize: CInt, flags: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetfsstat(mntbufp.rawAddress.toString(), bufsize, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clonefile(src: ConstPtr, dst: ConstPtr, flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleClonefile(src.rawAddress.toString(), dst.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clonefileat(srcDirfd: CInt, src: ConstPtr, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleClonefileat(srcDirfd, src.rawAddress.toString(), dstDirfd, dst.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclonefileat(srcfd: CInt, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFclonefileat(srcfd, dstDirfd, dst.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfile(from: ConstPtr, to: ConstPtr, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleCopyfile(from.rawAddress.toString(), to.rawAddress.toString(), state.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fcopyfile(from: CInt, to: CInt, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFcopyfile(from, to, state.rawAddress.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateFree(s: CopyfileStateT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleCopyfileStateFree(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateAlloc(): CopyfileStateT =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleCopyfileStateAlloc().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateGet(s: CopyfileStateT, flags: UInt, dst: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleCopyfileStateGet(s.rawAddress.toString(), flags.toInt(), dst.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyfileStateSet(s: CopyfileStateT, flags: UInt, src: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleCopyfileStateSet(s.rawAddress.toString(), flags.toInt(), src.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machErrorString(errorValue: io.github.kotlinmania.rustlang.unix.bsd.apple.MachErrorT): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleMachErrorString(errorValue).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memsetS(s: MutPtr, smax: SizeT, c: CInt, n: SizeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMemsetS(s.rawAddress.toString(), smax.toString(), c, n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memsetPattern4(b: MutPtr, pattern4: ConstPtr, len: SizeT) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMemsetPattern4(b.rawAddress.toString(), pattern4.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun memsetPattern8(b: MutPtr, pattern8: ConstPtr, len: SizeT) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMemsetPattern8(b.rawAddress.toString(), pattern8.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun memsetPattern16(b: MutPtr, pattern16: ConstPtr, len: SizeT) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMemsetPattern16(b.rawAddress.toString(), pattern16.rawAddress.toString(), len.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun strtonum(numstr: ConstPtr, minval: CLonglong, maxval: CLonglong, errstrp: MutPtr): CLonglong =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleStrtonum(numstr.rawAddress.toString(), minval.toString(), maxval.toString(), errstrp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mstats(): Mstats =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocPrintf(format: ConstPtr, vararg variadicArgs: Long) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMallocPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneCheck(zone: MutPtr): BooleanT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMallocZoneCheck(zone.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZonePrint(zone: MutPtr, verbose: BooleanT) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMallocZonePrint(zone.rawAddress.toString(), verbose)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneStatistics(zone: MutPtr, stats: MutPtr) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMallocZoneStatistics(zone.rawAddress.toString(), stats.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZoneLog(zone: MutPtr, address: MutPtr) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMallocZoneLog(zone.rawAddress.toString(), address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocZonePrintPtrInfo(ptr: MutPtr) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMallocZonePrintPtrInfo(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun mallocDefaultZone(): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleMallocDefaultZone().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneFromPtr(ptr: ConstPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleMallocZoneFromPtr(ptr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneMalloc(zone: MutPtr, size: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleMallocZoneMalloc(zone.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneValloc(zone: MutPtr, size: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleMallocZoneValloc(zone.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneCalloc(zone: MutPtr, numItems: SizeT, size: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleMallocZoneCalloc(zone.rawAddress.toString(), numItems.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneRealloc(zone: MutPtr, ptr: MutPtr, size: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleMallocZoneRealloc(zone.rawAddress.toString(), ptr.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocZoneFree(zone: MutPtr, ptr: MutPtr) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleMallocZoneFree(zone.rawAddress.toString(), ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun gethostuuid(id: MutPtr, timeout: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGethostuuid(id.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gethostid(): CLong =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGethostid().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sethostid(hostid: CLong) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleSethostid(hostid.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cCRandomGenerateBytes(bytes: MutPtr, size: SizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.CCRNGStatus =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleCCRandomGenerateBytes(bytes.rawAddress.toString(), size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetentropy(buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetExecutablePath(buf: MutPtr, bufsize: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleNsgetExecutablePath(buf.rawAddress.toString(), bufsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetArgv(): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleNsgetArgv().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetArgc(): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleNsgetArgc().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetEnviron(): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleNsgetEnviron().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nsgetProgname(): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleNsgetProgname().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun machVmMap(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.VmMapT, address: MutPtr, size: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmSizeT, mask: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmOffsetT, flags: CInt, obj: io.github.kotlinmania.rustlang.unix.bsd.apple.MemEntryNamePortT, offset: io.github.kotlinmania.rustlang.unix.bsd.apple.MemoryObjectOffsetT, copy: BooleanT, curProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, maxProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, inheritance: io.github.kotlinmania.rustlang.unix.bsd.apple.VmInheritT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMachVmMap(targetTask.toInt(), address.rawAddress.toString(), size.toString(), mask.toString(), flags, obj.toInt(), offset.toString(), copy, curProtection, maxProtection, inheritance.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmAllocate(targetTask: VmMapT, address: MutPtr, size: VmSizeT, flags: CInt): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleVmAllocate(targetTask.toInt(), address.rawAddress.toString(), size.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmDeallocate(targetTask: VmMapT, address: VmAddressT, size: VmSizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleVmDeallocate(targetTask.toInt(), address.toString(), size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostStatistics64(hostPriv: HostT, flavor: HostFlavorT, hostInfo64Out: HostInfo64T, hostInfo64OutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleHostStatistics64(hostPriv.toInt(), flavor, hostInfo64Out.rawAddress.toString(), hostInfo64OutCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostProcessorInfo(host: HostT, flavor: ProcessorFlavorT, outProcessorCount: MutPtr, outProcessorInfo: MutPtr, outProcessorInfoCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleHostProcessorInfo(host.toInt(), flavor, outProcessorCount.rawAddress.toString(), outProcessorInfo.rawAddress.toString(), outProcessorInfoCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskForPid(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, pid: PidT, task: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleTaskForPid(host.toInt(), pid, task.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskInfo(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, flavor: TaskFlavorT, taskInfoOut: TaskInfoT, taskInfoCount: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleTaskInfo(host.toInt(), flavor.toInt(), taskInfoOut.rawAddress.toString(), taskInfoCount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskCreate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, ledgers: io.github.kotlinmania.rustlang.unix.bsd.apple.LedgerArrayT, ledgersCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT, inheritMemory: BooleanT, childTask: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleTaskCreate(targetTask.toInt(), ledgers.rawAddress.toString(), ledgersCnt.toInt(), inheritMemory, childTask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskTerminate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleTaskTerminate(targetTask.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskThreads(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInspectT, actList: MutPtr, actListCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleTaskThreads(targetTask.toInt(), actList.rawAddress.toString(), actListCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun hostStatistics(hostPriv: HostT, flavor: HostFlavorT, hostInfoOut: HostInfoT, hostInfoOutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleHostStatistics(hostPriv.toInt(), flavor, hostInfoOut.rawAddress.toString(), hostInfoOutCnt.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysdirStartSearchPathEnumeration(dir: SysdirSearchPathDirectoryT, domainMask: SysdirSearchPathDomainMaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysdirGetNextSearchPathEnumeration(state: io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState, path: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(state.toInt(), path.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetattrlist(path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFgetattrlist(fd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlistat(fd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: CUlong): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetattrlistat(fd, path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSetattrlist(path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFsetattrlist(fd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setattrlistat(dirFd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleSetattrlistat(dirFd, path.rawAddress.toString(), attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getattrlistbulk(dirfd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: ULong): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleGetattrlistbulk(dirfd, attrList.rawAddress.toString(), attrBuf.rawAddress.toString(), attrBufSize.toString(), options.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocSize(ptr: ConstPtr): SizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMallocSize(ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocGoodSize(size: SizeT): SizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMallocGoodSize(size.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirname(path: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleDirname(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun basename(path: MutPtr): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleBasename(path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMkfifoat(dirfd, pathname.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleMknodat(dirfd, pathname.rawAddress.toString(), mode.toInt(), dev) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freadlink(fd: CInt, buf: MutPtr, size: SizeT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleFreadlink(fd, buf.rawAddress.toString(), size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvP(file: ConstPtr, searchPath: ConstPtr, argv: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleExecvP(file.rawAddress.toString(), searchPath.rawAddress.toString(), argv.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, arg: MutPtr, compar: QsortRComparCallback?) {
    if (libcUnixBsdAppleNapiAvailable) {
        wasmRustlangLibcUnixBsdAppleQsortR(base.rawAddress.toString(), num.toString(), size.toString(), arg.rawAddress.toString(), 0L.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun clockSettime(clockId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleClockSettime(clockId.toInt(), tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListpids(t: UInt, typeinfo: UInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcListpids(t.toInt(), typeinfo.toInt(), buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListallpids(buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcListallpids(buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListpgrppids(pgrpid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcListpgrppids(pgrpid, buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procListchildpids(ppid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcListchildpids(ppid, buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidinfo(pid: CInt, flavor: CInt, arg: ULong, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcPidinfo(pid, flavor, arg.toString(), buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidfdinfo(pid: CInt, fd: CInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcPidfdinfo(pid, fd, flavor, buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidfileportinfo(pid: CInt, fileport: UInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcPidfileportinfo(pid, fileport.toInt(), flavor, buffer.rawAddress.toString(), buffersize) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidpath(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcPidpath(pid, buffer.rawAddress.toString(), buffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procName(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcName(pid, buffer.rawAddress.toString(), buffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procRegionfilename(pid: CInt, address: ULong, buffer: MutPtr, buffersize: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcRegionfilename(pid, address.toString(), buffer.rawAddress.toString(), buffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procKmsgbuf(buffer: MutPtr, buffersize: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcKmsgbuf(buffer.rawAddress.toString(), buffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procLibversion(major: MutPtr, minor: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcLibversion(major.rawAddress.toString(), minor.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procPidRusage(pid: CInt, flavor: CInt, buffer: MutPtr): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcPidRusage(pid, flavor, buffer.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetNoSmt(): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcSetNoSmt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetthreadNoSmt(): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcSetthreadNoSmt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetCsm(flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcSetCsm(flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun procSetthreadCsm(flags: UInt): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleProcSetthreadCsm(flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleMemmem(haystack.rawAddress.toString(), haystacklen.toString(), needle.rawAddress.toString(), needlelen.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun taskSetInfo(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, flavor: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskFlavorT, taskInfoIn: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInfoT, taskInfoInCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleTaskSetInfo(targetTask.toInt(), flavor.toInt(), taskInfoIn.rawAddress.toString(), taskInfoInCnt.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    if (libcUnixBsdAppleNapiAvailable) MutPtr(wasmRustlangLibcUnixBsdAppleIconvOpen(tocode.rawAddress.toString(), fromcode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleIconv(cd.rawAddress.toString(), inbuf.rawAddress.toString(), inbytesleft.rawAddress.toString(), outbuf.rawAddress.toString(), outbytesleft.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iconvClose(cd: IconvT): CInt =
    if (libcUnixBsdAppleNapiAvailable) wasmRustlangLibcUnixBsdAppleIconvClose(cd.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
