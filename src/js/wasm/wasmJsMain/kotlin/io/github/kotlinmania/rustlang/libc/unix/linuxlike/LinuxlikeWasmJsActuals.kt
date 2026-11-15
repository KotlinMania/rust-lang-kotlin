@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.GidT
import io.github.kotlinmania.rustlang.unix.IntptrT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.UidT
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.IdtypeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixLinuxlikeNapiAddon(): Boolean

private val libcUnixLinuxlikeNapiAvailable: Boolean = initLibcUnixLinuxlikeNapiAddon()

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeIoctl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixLinuxlikeIoctl(p0: Int, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLibcCurrentSigrtmax()")
private external fun wasmRustlangLibcUnixLinuxlikeLibcCurrentSigrtmax(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLibcCurrentSigrtmin()")
private external fun wasmRustlangLibcUnixLinuxlikeLibcCurrentSigrtmin(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSemDestroy(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeSemDestroy(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSemInit(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeSemInit(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFdatasync(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeFdatasync(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeMincore(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeMincore(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeClockGetres(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeClockGetres(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeClockGettime(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeClockGettime(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeClockSettime(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeClockSettime(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeClockGetcpuclockid(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeClockGetcpuclockid(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeGetitimer(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeGetitimer(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSetitimer(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeSetitimer(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeDirfd(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeDirfd(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeMemalign(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeMemalign(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSetgroups(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeSetgroups(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikePipe2(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikePipe2(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStatfs(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeStatfs(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFstatfs(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeFstatfs(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeMemrchr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeMemrchr(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikePosixFadvise(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikePosixFadvise(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFutimens(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeFutimens(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeUtimensat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeUtimensat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeDuplocale(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeDuplocale(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFreelocale(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeFreelocale(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeNewlocale(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeNewlocale(p0: Int, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeUselocale(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeUselocale(p0: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeMknodat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeMknodat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikePtsnameR(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikePtsnameR(p0: Int, p1: String, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeClearenv()")
private external fun wasmRustlangLibcUnixLinuxlikeClearenv(): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeWaitid(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeWaitid(p0: Int, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeGetresuid(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeGetresuid(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeGetresgid(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeGetresgid(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeAcct(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeAcct(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeBrk(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeBrk(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSbrk(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeSbrk(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeVfork()")
private external fun wasmRustlangLibcUnixLinuxlikeVfork(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSetresgid(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeSetresgid(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSetresuid(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeSetresuid(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeWait4(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeWait4(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLoginTty(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeLoginTty(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeExecvpe(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeExecvpe(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFexecve(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeFexecve(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeGetifaddrs(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeGetifaddrs(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFreeifaddrs(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeFreeifaddrs(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeBind(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeBind(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeWritev(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeWritev(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeReadv(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeReadv(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSendmsg(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeSendmsg(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeRecvmsg(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeRecvmsg(p0: Int, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeUname(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeUname(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStrchrnul(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeStrchrnul(p0: String, p1: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStrftime(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeStrftime(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStrftimeL(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeStrftimeL(p0: String, p1: String, p2: String, p3: String, p4: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStrptime(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeStrptime(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeMkostemp(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeMkostemp(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeMkostemps(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeMkostemps(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeGetdomainname(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeGetdomainname(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeSetdomainname(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeSetdomainname(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeIfNameindex()")
private external fun wasmRustlangLibcUnixLinuxlikeIfNameindex(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeIfFreenameindex(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeIfFreenameindex(p0: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeGetpwnamR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeGetpwnamR(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeGetpwuidR(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeGetpwuidR(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFstatfs64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeFstatfs64(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStatvfs64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeStatvfs64(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFstatvfs64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeFstatvfs64(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStatfs64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeStatfs64(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeCreat64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeCreat64(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFstat64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeFstat64(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFstatat64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeFstatat64(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeFtruncate64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeFtruncate64(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLseek64(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeLseek64(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeLstat64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeLstat64(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeMmap64(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixLinuxlikeMmap64(p0: String, p1: String, p2: Int, p3: Int, p4: Int, p5: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeOpen64(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixLinuxlikeOpen64(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeOpenat64(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcUnixLinuxlikeOpenat64(p0: Int, p1: String, p2: Int, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikePosixFadvise64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikePosixFadvise64(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikePread64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikePread64(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikePwrite64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikePwrite64(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeReaddir64(p0)")
private external fun wasmRustlangLibcUnixLinuxlikeReaddir64(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeReaddir64R(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLinuxlikeReaddir64R(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStat64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeStat64(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeTruncate64(p0, p1)")
private external fun wasmRustlangLibcUnixLinuxlikeTruncate64(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikePreadv64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikePreadv64(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikePwritev64(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikePwritev64(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeForkpty(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixLinuxlikeForkpty(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeOpenpty(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeOpenpty(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinuxlikeStatx(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinuxlikeStatx(p0: Int, p1: String, p2: Int, p3: Int, p4: String): Int

public actual fun ioctl(fd: CInt, request: Ioctl, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeIoctl(fd, request.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun libcCurrentSigrtmax(): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeLibcCurrentSigrtmax() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun libcCurrentSigrtmin(): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeLibcCurrentSigrtmin() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semDestroy(sem: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeSemDestroy(sem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semInit(sem: MutPtr, pshared: CInt, value: CUint): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeSemInit(sem.rawAddress.toString(), pshared, value.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdatasync(fd: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFdatasync(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mincore(addr: MutPtr, len: SizeT, vec: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeMincore(addr.rawAddress.toString(), len.toString(), vec.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeClockGetres(clkId, tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeClockGettime(clkId, tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockSettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeClockSettime(clkId, tp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetcpuclockid(pid: PidT, clkId: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeClockGetcpuclockid(pid, clkId.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getitimer(which: CInt, currValue: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeGetitimer(which, currValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setitimer(which: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeSetitimer(which, newValue.rawAddress.toString(), oldValue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dirfd(dirp: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeDirfd(dirp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memalign(align: SizeT, size: SizeT): MutPtr =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeMemalign(align.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgroups(ngroups: SizeT, ptr: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeSetgroups(ngroups.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pipe2(fds: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikePipe2(fds.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeStatfs(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFstatfs(fd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeMemrchr(cx.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikePosixFadvise(fd, offset.toString(), len.toString(), advise) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFutimens(fd, times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flag) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplocale(base: LocaleT): LocaleT =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeDuplocale(base.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freelocale(loc: LocaleT) {
    if (libcUnixLinuxlikeNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeFreelocale(loc.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeNewlocale(mask, locale.rawAddress.toString(), base.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uselocale(loc: LocaleT): LocaleT =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeUselocale(loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeMknodat(dirfd, pathname.rawAddress.toString(), mode.toInt(), dev) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptsnameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikePtsnameR(fd, buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clearenv(): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeClearenv() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeWaitid(idtype.toInt(), id.toInt(), infop.rawAddress.toString(), options) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getresuid(ruid: MutPtr, euid: MutPtr, suid: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeGetresuid(ruid.rawAddress.toString(), euid.rawAddress.toString(), suid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getresgid(rgid: MutPtr, egid: MutPtr, sgid: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeGetresgid(rgid.rawAddress.toString(), egid.rawAddress.toString(), sgid.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun acct(filename: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeAcct(filename.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun brk(addr: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeBrk(addr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sbrk(increment: IntptrT): MutPtr =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeSbrk(increment.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfork(): PidT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeVfork() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setresgid(rgid: GidT, egid: GidT, sgid: GidT): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeSetresgid(rgid, egid, sgid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setresuid(ruid: UidT, euid: UidT, suid: UidT): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeSetresuid(ruid, euid, suid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wait4(pid: PidT, status: MutPtr, options: CInt, rusage: MutPtr): PidT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeWait4(pid, status.rawAddress.toString(), options, rusage.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loginTty(fd: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeLoginTty(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvpe(file: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeExecvpe(file.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fexecve(fd: CInt, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFexecve(fd, argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getifaddrs(ifap: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeGetifaddrs(ifap.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeifaddrs(ifa: MutPtr) {
    if (libcUnixLinuxlikeNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeFreeifaddrs(ifa.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun bind(socket: CInt, address: ConstPtr, addressLen: SocklenT): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeBind(socket, address.rawAddress.toString(), addressLen.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeWritev(fd, iov.rawAddress.toString(), iovcnt).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeReadv(fd, iov.rawAddress.toString(), iovcnt).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendmsg(fd: CInt, msg: ConstPtr, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeSendmsg(fd, msg.rawAddress.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvmsg(fd: CInt, msg: MutPtr, flags: CInt): SsizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeRecvmsg(fd, msg.rawAddress.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uname(buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeUname(buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchrnul(s: ConstPtr, c: CInt): MutPtr =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeStrchrnul(s.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftime(s: MutPtr, max: SizeT, format: ConstPtr, tm: ConstPtr): SizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeStrftime(s.rawAddress.toString(), max.toString(), format.rawAddress.toString(), tm.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftimeL(s: MutPtr, max: SizeT, format: ConstPtr, tm: ConstPtr, locale: LocaleT): SizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeStrftimeL(s.rawAddress.toString(), max.toString(), format.rawAddress.toString(), tm.rawAddress.toString(), locale.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strptime(s: ConstPtr, format: ConstPtr, tm: MutPtr): MutPtr =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeStrptime(s.rawAddress.toString(), format.rawAddress.toString(), tm.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkostemp(template: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeMkostemp(template.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkostemps(template: MutPtr, suffixlen: CInt, flags: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeMkostemps(template.rawAddress.toString(), suffixlen, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getdomainname(name: MutPtr, len: SizeT): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeGetdomainname(name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setdomainname(name: ConstPtr, len: SizeT): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeSetdomainname(name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifNameindex(): MutPtr =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeIfNameindex().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifFreenameindex(ptr: MutPtr) {
    if (libcUnixLinuxlikeNapiAvailable) {
        wasmRustlangLibcUnixLinuxlikeIfFreenameindex(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getpwnamR(name: ConstPtr, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeGetpwnamR(name.rawAddress.toString(), pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwuidR(uid: UidT, pwd: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeGetpwuidR(uid, pwd.rawAddress.toString(), buf.rawAddress.toString(), buflen.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatfs64(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFstatfs64(fd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statvfs64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeStatvfs64(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatvfs64(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFstatvfs64(fd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statfs64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeStatfs64(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat64(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeCreat64(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat64(fildes: CInt, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFstat64(fildes, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatat64(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFstatat64(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftruncate64(fd: CInt, length: Off64T): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeFtruncate64(fd, length.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek64(fd: CInt, offset: Off64T, whence: CInt): Off64T =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeLseek64(fd, offset.toString(), whence).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstat64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeLstat64(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mmap64(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: Off64T): MutPtr =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeMmap64(addr.rawAddress.toString(), len.toString(), prot, flags, fd, offset.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open64(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeOpen64(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openat64(fd: CInt, path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeOpenat64(fd, path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFadvise64(fd: CInt, offset: Off64T, len: Off64T, advise: CInt): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikePosixFadvise64(fd, offset.toString(), len.toString(), advise) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pread64(fd: CInt, buf: MutPtr, count: SizeT, offset: Off64T): SsizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikePread64(fd, buf.rawAddress.toString(), count.toString(), offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwrite64(fd: CInt, buf: ConstPtr, count: SizeT, offset: Off64T): SsizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikePwrite64(fd, buf.rawAddress.toString(), count.toString(), offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir64(dirp: MutPtr): MutPtr =
    if (libcUnixLinuxlikeNapiAvailable) MutPtr(wasmRustlangLibcUnixLinuxlikeReaddir64(dirp.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir64R(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeReaddir64R(dirp.rawAddress.toString(), entry.rawAddress.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat64(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeStat64(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncate64(path: ConstPtr, length: Off64T): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeTruncate64(path.rawAddress.toString(), length.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikePreadv64(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev64(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: Off64T): SsizeT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikePwritev64(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: ConstPtr, winp: ConstPtr): PidT =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeForkpty(amaster.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: ConstPtr, winp: ConstPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeOpenpty(amaster.rawAddress.toString(), aslave.rawAddress.toString(), name.rawAddress.toString(), termp.rawAddress.toString(), winp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statx(dirfd: CInt, pathname: ConstPtr, flags: CInt, mask: CUint, statxbuf: MutPtr): CInt =
    if (libcUnixLinuxlikeNapiAvailable) wasmRustlangLibcUnixLinuxlikeStatx(dirfd, pathname.rawAddress.toString(), flags, mask.toInt(), statxbuf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
