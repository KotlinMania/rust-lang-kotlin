@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.unix

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CFloat
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.SpeedT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.NfdsT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.PthreadKeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.ClockT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.TimeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.aarch64.WcharT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcUnixNapiAddon(): Boolean

private val libcUnixNapiAvailable: Boolean = initLibcUnixNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsalnum(p0)")
private external fun wasmRustlangLibcUnixIsalnum(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsalpha(p0)")
private external fun wasmRustlangLibcUnixIsalpha(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIscntrl(p0)")
private external fun wasmRustlangLibcUnixIscntrl(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsdigit(p0)")
private external fun wasmRustlangLibcUnixIsdigit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsgraph(p0)")
private external fun wasmRustlangLibcUnixIsgraph(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIslower(p0)")
private external fun wasmRustlangLibcUnixIslower(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsprint(p0)")
private external fun wasmRustlangLibcUnixIsprint(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIspunct(p0)")
private external fun wasmRustlangLibcUnixIspunct(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsspace(p0)")
private external fun wasmRustlangLibcUnixIsspace(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsupper(p0)")
private external fun wasmRustlangLibcUnixIsupper(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsxdigit(p0)")
private external fun wasmRustlangLibcUnixIsxdigit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsblank(p0)")
private external fun wasmRustlangLibcUnixIsblank(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTolower(p0)")
private external fun wasmRustlangLibcUnixTolower(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixToupper(p0)")
private external fun wasmRustlangLibcUnixToupper(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixQsort(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixQsort(p0: String, p1: String, p2: String, p3: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixBsearch(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixBsearch(p0: String, p1: String, p2: String, p3: String, p4: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFopen(p0, p1)")
private external fun wasmRustlangLibcUnixFopen(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixFreopen(p0, p1, p2)")
private external fun wasmRustlangLibcUnixFreopen(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFflush(p0)")
private external fun wasmRustlangLibcUnixFflush(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFclose(p0)")
private external fun wasmRustlangLibcUnixFclose(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixRemove(p0)")
private external fun wasmRustlangLibcUnixRemove(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixRename(p0, p1)")
private external fun wasmRustlangLibcUnixRename(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixTmpfile()")
private external fun wasmRustlangLibcUnixTmpfile(): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetvbuf(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixSetvbuf(p0: String, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetbuf(p0, p1)")
private external fun wasmRustlangLibcUnixSetbuf(p0: String, p1: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetchar()")
private external fun wasmRustlangLibcUnixGetchar(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPutchar(p0)")
private external fun wasmRustlangLibcUnixPutchar(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFgetc(p0)")
private external fun wasmRustlangLibcUnixFgetc(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixFgets(p0, p1, p2)")
private external fun wasmRustlangLibcUnixFgets(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFputc(p0, p1)")
private external fun wasmRustlangLibcUnixFputc(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFputs(p0, p1)")
private external fun wasmRustlangLibcUnixFputs(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPuts(p0)")
private external fun wasmRustlangLibcUnixPuts(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixUngetc(p0, p1)")
private external fun wasmRustlangLibcUnixUngetc(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixFread(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixFread(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixFwrite(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixFwrite(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixFseek(p0, p1, p2)")
private external fun wasmRustlangLibcUnixFseek(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFtell(p0)")
private external fun wasmRustlangLibcUnixFtell(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixRewind(p0)")
private external fun wasmRustlangLibcUnixRewind(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFgetpos(p0, p1)")
private external fun wasmRustlangLibcUnixFgetpos(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFsetpos(p0, p1)")
private external fun wasmRustlangLibcUnixFsetpos(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFeof(p0)")
private external fun wasmRustlangLibcUnixFeof(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFerror(p0)")
private external fun wasmRustlangLibcUnixFerror(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixClearerr(p0)")
private external fun wasmRustlangLibcUnixClearerr(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPerror(p0)")
private external fun wasmRustlangLibcUnixPerror(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixAtof(p0)")
private external fun wasmRustlangLibcUnixAtof(p0: String): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixAtoi(p0)")
private external fun wasmRustlangLibcUnixAtoi(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixAtol(p0)")
private external fun wasmRustlangLibcUnixAtol(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixAtoll(p0)")
private external fun wasmRustlangLibcUnixAtoll(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrtod(p0, p1)")
private external fun wasmRustlangLibcUnixStrtod(p0: String, p1: String): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrtof(p0, p1)")
private external fun wasmRustlangLibcUnixStrtof(p0: String, p1: String): Double

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrtol(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrtol(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrtoll(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrtoll(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrtoul(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrtoul(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrtoull(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrtoull(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixCalloc(p0, p1)")
private external fun wasmRustlangLibcUnixCalloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixMalloc(p0)")
private external fun wasmRustlangLibcUnixMalloc(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixRealloc(p0, p1)")
private external fun wasmRustlangLibcUnixRealloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFree(p0)")
private external fun wasmRustlangLibcUnixFree(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixAbort()")
private external fun wasmRustlangLibcUnixAbort()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixExit(p0)")
private external fun wasmRustlangLibcUnixExit(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSystem(p0)")
private external fun wasmRustlangLibcUnixSystem(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetenv(p0)")
private external fun wasmRustlangLibcUnixGetenv(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrcpy(p0, p1)")
private external fun wasmRustlangLibcUnixStrcpy(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrncpy(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrncpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStpcpy(p0, p1)")
private external fun wasmRustlangLibcUnixStpcpy(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrcat(p0, p1)")
private external fun wasmRustlangLibcUnixStrcat(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrncat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrncat(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrcmp(p0, p1)")
private external fun wasmRustlangLibcUnixStrcmp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrncmp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrncmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrcoll(p0, p1)")
private external fun wasmRustlangLibcUnixStrcoll(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrchr(p0, p1)")
private external fun wasmRustlangLibcUnixStrchr(p0: String, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrrchr(p0, p1)")
private external fun wasmRustlangLibcUnixStrrchr(p0: String, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrspn(p0, p1)")
private external fun wasmRustlangLibcUnixStrspn(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrcspn(p0, p1)")
private external fun wasmRustlangLibcUnixStrcspn(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrdup(p0)")
private external fun wasmRustlangLibcUnixStrdup(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrndup(p0, p1)")
private external fun wasmRustlangLibcUnixStrndup(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrpbrk(p0, p1)")
private external fun wasmRustlangLibcUnixStrpbrk(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrstr(p0, p1)")
private external fun wasmRustlangLibcUnixStrstr(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrcasecmp(p0, p1)")
private external fun wasmRustlangLibcUnixStrcasecmp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrncasecmp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrncasecmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrlen(p0)")
private external fun wasmRustlangLibcUnixStrlen(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrnlen(p0, p1)")
private external fun wasmRustlangLibcUnixStrnlen(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrerror(p0)")
private external fun wasmRustlangLibcUnixStrerror(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrtok(p0, p1)")
private external fun wasmRustlangLibcUnixStrtok(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrtokR(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrtokR(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrxfrm(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStrxfrm(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrsignal(p0)")
private external fun wasmRustlangLibcUnixStrsignal(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixWcslen(p0)")
private external fun wasmRustlangLibcUnixWcslen(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixWcstombs(p0, p1, p2)")
private external fun wasmRustlangLibcUnixWcstombs(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixMemchr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixMemchr(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixWmemchr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixWmemchr(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixMemcmp(p0, p1, p2)")
private external fun wasmRustlangLibcUnixMemcmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixMemcpy(p0, p1, p2)")
private external fun wasmRustlangLibcUnixMemcpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixMemmove(p0, p1, p2)")
private external fun wasmRustlangLibcUnixMemmove(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixMemset(p0, p1, p2)")
private external fun wasmRustlangLibcUnixMemset(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixMemccpy(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixMemccpy(p0: String, p1: String, p2: Int, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetpwnam(p0)")
private external fun wasmRustlangLibcUnixGetpwnam(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetpwuid(p0)")
private external fun wasmRustlangLibcUnixGetpwuid(p0: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixFprintf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixFprintf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixPrintf(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixPrintf(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcUnixSnprintf(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcUnixSnprintf(p0: String, p1: String, p2: String, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixSprintf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixSprintf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixFscanf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixFscanf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixScanf(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixScanf(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixSscanf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixSscanf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetcharUnlocked()")
private external fun wasmRustlangLibcUnixGetcharUnlocked(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPutcharUnlocked(p0)")
private external fun wasmRustlangLibcUnixPutcharUnlocked(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixSocket(p0, p1, p2)")
private external fun wasmRustlangLibcUnixSocket(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixConnect(p0, p1, p2)")
private external fun wasmRustlangLibcUnixConnect(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixListen(p0, p1)")
private external fun wasmRustlangLibcUnixListen(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixAccept(p0, p1, p2)")
private external fun wasmRustlangLibcUnixAccept(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetpeername(p0, p1, p2)")
private external fun wasmRustlangLibcUnixGetpeername(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetsockname(p0, p1, p2)")
private external fun wasmRustlangLibcUnixGetsockname(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixSetsockopt(p0: Int, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixSocketpair(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixSocketpair(p0: Int, p1: Int, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixSendto(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixSendto(p0: Int, p1: String, p2: String, p3: Int, p4: String, p5: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixShutdown(p0, p1)")
private external fun wasmRustlangLibcUnixShutdown(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixChmod(p0, p1)")
private external fun wasmRustlangLibcUnixChmod(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFchmod(p0, p1)")
private external fun wasmRustlangLibcUnixFchmod(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFstat(p0, p1)")
private external fun wasmRustlangLibcUnixFstat(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixMkdir(p0, p1)")
private external fun wasmRustlangLibcUnixMkdir(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStat(p0, p1)")
private external fun wasmRustlangLibcUnixStat(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPclose(p0)")
private external fun wasmRustlangLibcUnixPclose(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFdopen(p0, p1)")
private external fun wasmRustlangLibcUnixFdopen(p0: Int, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFileno(p0)")
private external fun wasmRustlangLibcUnixFileno(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixCreat(p0, p1)")
private external fun wasmRustlangLibcUnixCreat(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixFcntl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixFcntl(p0: Int, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixOpendir(p0)")
private external fun wasmRustlangLibcUnixOpendir(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFdopendir(p0)")
private external fun wasmRustlangLibcUnixFdopendir(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixReaddir(p0)")
private external fun wasmRustlangLibcUnixReaddir(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixClosedir(p0)")
private external fun wasmRustlangLibcUnixClosedir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixRewinddir(p0)")
private external fun wasmRustlangLibcUnixRewinddir(p0: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixFchmodat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixFchmodat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixFchown(p0, p1, p2)")
private external fun wasmRustlangLibcUnixFchown(p0: Int, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixFchownat(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixFchownat(p0: Int, p1: String, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcUnixOpenat(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcUnixOpenat(p0: Int, p1: String, p2: Int, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixFstatat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixFstatat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixLinkat(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixLinkat(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixMkdirat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixMkdirat(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixRenameat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixRenameat(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixSymlinkat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixSymlinkat(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixUnlinkat(p0, p1, p2)")
private external fun wasmRustlangLibcUnixUnlinkat(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixAccess(p0, p1)")
private external fun wasmRustlangLibcUnixAccess(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixAlarm(p0)")
private external fun wasmRustlangLibcUnixAlarm(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixChdir(p0)")
private external fun wasmRustlangLibcUnixChdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFchdir(p0)")
private external fun wasmRustlangLibcUnixFchdir(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixChown(p0, p1, p2)")
private external fun wasmRustlangLibcUnixChown(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLchown(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLchown(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixClose(p0)")
private external fun wasmRustlangLibcUnixClose(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixDup(p0)")
private external fun wasmRustlangLibcUnixDup(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixDup2(p0, p1)")
private external fun wasmRustlangLibcUnixDup2(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixExecl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixExecl(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixExecle(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixExecle(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixExeclp(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixExeclp(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixExecv(p0, p1)")
private external fun wasmRustlangLibcUnixExecv(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixExecve(p0, p1, p2)")
private external fun wasmRustlangLibcUnixExecve(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixExecvp(p0, p1)")
private external fun wasmRustlangLibcUnixExecvp(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixFork()")
private external fun wasmRustlangLibcUnixFork(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFpathconf(p0, p1)")
private external fun wasmRustlangLibcUnixFpathconf(p0: Int, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetcwd(p0, p1)")
private external fun wasmRustlangLibcUnixGetcwd(p0: String, p1: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetegid()")
private external fun wasmRustlangLibcUnixGetegid(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGeteuid()")
private external fun wasmRustlangLibcUnixGeteuid(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetgid()")
private external fun wasmRustlangLibcUnixGetgid(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetgroups(p0, p1)")
private external fun wasmRustlangLibcUnixGetgroups(p0: Int, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetlogin()")
private external fun wasmRustlangLibcUnixGetlogin(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetopt(p0, p1, p2)")
private external fun wasmRustlangLibcUnixGetopt(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetpgid(p0)")
private external fun wasmRustlangLibcUnixGetpgid(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetpgrp()")
private external fun wasmRustlangLibcUnixGetpgrp(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetpid()")
private external fun wasmRustlangLibcUnixGetpid(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetppid()")
private external fun wasmRustlangLibcUnixGetppid(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetuid()")
private external fun wasmRustlangLibcUnixGetuid(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIsatty(p0)")
private external fun wasmRustlangLibcUnixIsatty(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLink(p0, p1)")
private external fun wasmRustlangLibcUnixLink(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLseek(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLseek(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPathconf(p0, p1)")
private external fun wasmRustlangLibcUnixPathconf(p0: String, p1: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixPause()")
private external fun wasmRustlangLibcUnixPause(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPipe(p0)")
private external fun wasmRustlangLibcUnixPipe(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixPosixMemalign(p0, p1, p2)")
private external fun wasmRustlangLibcUnixPosixMemalign(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixAlignedAlloc(p0, p1)")
private external fun wasmRustlangLibcUnixAlignedAlloc(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixRead(p0, p1, p2)")
private external fun wasmRustlangLibcUnixRead(p0: Int, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixRmdir(p0)")
private external fun wasmRustlangLibcUnixRmdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSeteuid(p0)")
private external fun wasmRustlangLibcUnixSeteuid(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetegid(p0)")
private external fun wasmRustlangLibcUnixSetegid(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetgid(p0)")
private external fun wasmRustlangLibcUnixSetgid(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetpgid(p0, p1)")
private external fun wasmRustlangLibcUnixSetpgid(p0: Int, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixSetsid()")
private external fun wasmRustlangLibcUnixSetsid(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetuid(p0)")
private external fun wasmRustlangLibcUnixSetuid(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetreuid(p0, p1)")
private external fun wasmRustlangLibcUnixSetreuid(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetregid(p0, p1)")
private external fun wasmRustlangLibcUnixSetregid(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSleep(p0)")
private external fun wasmRustlangLibcUnixSleep(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixNanosleep(p0, p1)")
private external fun wasmRustlangLibcUnixNanosleep(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcgetpgrp(p0)")
private external fun wasmRustlangLibcUnixTcgetpgrp(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcsetpgrp(p0, p1)")
private external fun wasmRustlangLibcUnixTcsetpgrp(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTtyname(p0)")
private external fun wasmRustlangLibcUnixTtyname(p0: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixTtynameR(p0, p1, p2)")
private external fun wasmRustlangLibcUnixTtynameR(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixUnlink(p0)")
private external fun wasmRustlangLibcUnixUnlink(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixWait(p0)")
private external fun wasmRustlangLibcUnixWait(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixWaitpid(p0, p1, p2)")
private external fun wasmRustlangLibcUnixWaitpid(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixWrite(p0, p1, p2)")
private external fun wasmRustlangLibcUnixWrite(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixPread(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixPread(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixPwrite(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixPwrite(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixUmask(p0)")
private external fun wasmRustlangLibcUnixUmask(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixUtime(p0, p1)")
private external fun wasmRustlangLibcUnixUtime(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixKill(p0, p1)")
private external fun wasmRustlangLibcUnixKill(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixKillpg(p0, p1)")
private external fun wasmRustlangLibcUnixKillpg(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixMlock(p0, p1)")
private external fun wasmRustlangLibcUnixMlock(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixMunlock(p0, p1)")
private external fun wasmRustlangLibcUnixMunlock(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixMlockall(p0)")
private external fun wasmRustlangLibcUnixMlockall(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixMunlockall()")
private external fun wasmRustlangLibcUnixMunlockall(): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixMmap(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixMmap(p0: String, p1: String, p2: Int, p3: Int, p4: Int, p5: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixMunmap(p0, p1)")
private external fun wasmRustlangLibcUnixMunmap(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixIfNametoindex(p0)")
private external fun wasmRustlangLibcUnixIfNametoindex(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixIfIndextoname(p0, p1)")
private external fun wasmRustlangLibcUnixIfIndextoname(p0: Int, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLstat(p0, p1)")
private external fun wasmRustlangLibcUnixLstat(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFsync(p0)")
private external fun wasmRustlangLibcUnixFsync(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetenv(p0, p1, p2)")
private external fun wasmRustlangLibcUnixSetenv(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixUnsetenv(p0)")
private external fun wasmRustlangLibcUnixUnsetenv(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSymlink(p0, p1)")
private external fun wasmRustlangLibcUnixSymlink(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixTruncate(p0, p1)")
private external fun wasmRustlangLibcUnixTruncate(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFtruncate(p0, p1)")
private external fun wasmRustlangLibcUnixFtruncate(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSignal(p0, p1)")
private external fun wasmRustlangLibcUnixSignal(p0: Int, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetrusage(p0, p1)")
private external fun wasmRustlangLibcUnixGetrusage(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixRealpath(p0, p1)")
private external fun wasmRustlangLibcUnixRealpath(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTimes(p0)")
private external fun wasmRustlangLibcUnixTimes(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadSelf()")
private external fun wasmRustlangLibcUnixPthreadSelf(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadEqual(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadEqual(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadJoin(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadJoin(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadExit(p0)")
private external fun wasmRustlangLibcUnixPthreadExit(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadAttrInit(p0)")
private external fun wasmRustlangLibcUnixPthreadAttrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadAttrDestroy(p0)")
private external fun wasmRustlangLibcUnixPthreadAttrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadAttrGetstacksize(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadAttrGetstacksize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadAttrSetstacksize(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadAttrSetstacksize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadAttrSetdetachstate(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadAttrSetdetachstate(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadDetach(p0)")
private external fun wasmRustlangLibcUnixPthreadDetach(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixSchedYield()")
private external fun wasmRustlangLibcUnixSchedYield(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadKeyCreate(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadKeyCreate(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadKeyDelete(p0)")
private external fun wasmRustlangLibcUnixPthreadKeyDelete(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadGetspecific(p0)")
private external fun wasmRustlangLibcUnixPthreadGetspecific(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadSetspecific(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadSetspecific(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadMutexInit(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadMutexInit(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadMutexDestroy(p0)")
private external fun wasmRustlangLibcUnixPthreadMutexDestroy(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadMutexLock(p0)")
private external fun wasmRustlangLibcUnixPthreadMutexLock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadMutexTrylock(p0)")
private external fun wasmRustlangLibcUnixPthreadMutexTrylock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadMutexUnlock(p0)")
private external fun wasmRustlangLibcUnixPthreadMutexUnlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadMutexattrInit(p0)")
private external fun wasmRustlangLibcUnixPthreadMutexattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadMutexattrDestroy(p0)")
private external fun wasmRustlangLibcUnixPthreadMutexattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadMutexattrSettype(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadMutexattrSettype(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadCondInit(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadCondInit(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadCondWait(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadCondWait(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadCondTimedwait(p0, p1, p2)")
private external fun wasmRustlangLibcUnixPthreadCondTimedwait(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadCondSignal(p0)")
private external fun wasmRustlangLibcUnixPthreadCondSignal(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadCondBroadcast(p0)")
private external fun wasmRustlangLibcUnixPthreadCondBroadcast(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadCondDestroy(p0)")
private external fun wasmRustlangLibcUnixPthreadCondDestroy(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadCondattrInit(p0)")
private external fun wasmRustlangLibcUnixPthreadCondattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadCondattrDestroy(p0)")
private external fun wasmRustlangLibcUnixPthreadCondattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockInit(p0, p1)")
private external fun wasmRustlangLibcUnixPthreadRwlockInit(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockDestroy(p0)")
private external fun wasmRustlangLibcUnixPthreadRwlockDestroy(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockRdlock(p0)")
private external fun wasmRustlangLibcUnixPthreadRwlockRdlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockTryrdlock(p0)")
private external fun wasmRustlangLibcUnixPthreadRwlockTryrdlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockWrlock(p0)")
private external fun wasmRustlangLibcUnixPthreadRwlockWrlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockTrywrlock(p0)")
private external fun wasmRustlangLibcUnixPthreadRwlockTrywrlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockUnlock(p0)")
private external fun wasmRustlangLibcUnixPthreadRwlockUnlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockattrInit(p0)")
private external fun wasmRustlangLibcUnixPthreadRwlockattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPthreadRwlockattrDestroy(p0)")
private external fun wasmRustlangLibcUnixPthreadRwlockattrDestroy(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixGetsockopt(p0: Int, p1: Int, p2: Int, p3: String, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixRaise(p0)")
private external fun wasmRustlangLibcUnixRaise(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixUtimes(p0, p1)")
private external fun wasmRustlangLibcUnixUtimes(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixDlopen(p0, p1)")
private external fun wasmRustlangLibcUnixDlopen(p0: String, p1: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixDlerror()")
private external fun wasmRustlangLibcUnixDlerror(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixDlsym(p0, p1)")
private external fun wasmRustlangLibcUnixDlsym(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixDlclose(p0)")
private external fun wasmRustlangLibcUnixDlclose(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetaddrinfo(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixGetaddrinfo(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFreeaddrinfo(p0)")
private external fun wasmRustlangLibcUnixFreeaddrinfo(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixHstrerror(p0)")
private external fun wasmRustlangLibcUnixHstrerror(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGaiStrerror(p0)")
private external fun wasmRustlangLibcUnixGaiStrerror(p0: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixResInit()")
private external fun wasmRustlangLibcUnixResInit(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixGmtimeR(p0, p1)")
private external fun wasmRustlangLibcUnixGmtimeR(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixLocaltimeR(p0, p1)")
private external fun wasmRustlangLibcUnixLocaltimeR(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixMktime(p0)")
private external fun wasmRustlangLibcUnixMktime(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTime(p0)")
private external fun wasmRustlangLibcUnixTime(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGmtime(p0)")
private external fun wasmRustlangLibcUnixGmtime(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixLocaltime(p0)")
private external fun wasmRustlangLibcUnixLocaltime(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixDifftime(p0, p1)")
private external fun wasmRustlangLibcUnixDifftime(p0: String, p1: String): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTimegm(p0)")
private external fun wasmRustlangLibcUnixTimegm(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixMknod(p0, p1, p2)")
private external fun wasmRustlangLibcUnixMknod(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixGethostname(p0, p1)")
private external fun wasmRustlangLibcUnixGethostname(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixEndservent()")
private external fun wasmRustlangLibcUnixEndservent()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetservbyname(p0, p1)")
private external fun wasmRustlangLibcUnixGetservbyname(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetservbyport(p0, p1)")
private external fun wasmRustlangLibcUnixGetservbyport(p0: Int, p1: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixGetservent()")
private external fun wasmRustlangLibcUnixGetservent(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetservent(p0)")
private external fun wasmRustlangLibcUnixSetservent(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetprotobyname(p0)")
private external fun wasmRustlangLibcUnixGetprotobyname(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetprotobynumber(p0)")
private external fun wasmRustlangLibcUnixGetprotobynumber(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixChroot(p0)")
private external fun wasmRustlangLibcUnixChroot(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixUsleep(p0)")
private external fun wasmRustlangLibcUnixUsleep(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixSend(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixSend(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixRecv(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixRecv(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPutenv(p0)")
private external fun wasmRustlangLibcUnixPutenv(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixPoll(p0, p1, p2)")
private external fun wasmRustlangLibcUnixPoll(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcUnixSelect(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcUnixSelect(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetlocale(p0, p1)")
private external fun wasmRustlangLibcUnixSetlocale(p0: Int, p1: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixLocaleconv()")
private external fun wasmRustlangLibcUnixLocaleconv(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSemWait(p0)")
private external fun wasmRustlangLibcUnixSemWait(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSemTrywait(p0)")
private external fun wasmRustlangLibcUnixSemTrywait(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSemPost(p0)")
private external fun wasmRustlangLibcUnixSemPost(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStatvfs(p0, p1)")
private external fun wasmRustlangLibcUnixStatvfs(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFstatvfs(p0, p1)")
private external fun wasmRustlangLibcUnixFstatvfs(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSigemptyset(p0)")
private external fun wasmRustlangLibcUnixSigemptyset(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSigaddset(p0, p1)")
private external fun wasmRustlangLibcUnixSigaddset(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSigfillset(p0)")
private external fun wasmRustlangLibcUnixSigfillset(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSigdelset(p0, p1)")
private external fun wasmRustlangLibcUnixSigdelset(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixSigismember(p0, p1)")
private external fun wasmRustlangLibcUnixSigismember(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixSigprocmask(p0, p1, p2)")
private external fun wasmRustlangLibcUnixSigprocmask(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSigpending(p0)")
private external fun wasmRustlangLibcUnixSigpending(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSysconf(p0)")
private external fun wasmRustlangLibcUnixSysconf(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixMkfifo(p0, p1)")
private external fun wasmRustlangLibcUnixMkfifo(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixFseeko(p0, p1, p2)")
private external fun wasmRustlangLibcUnixFseeko(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixFtello(p0)")
private external fun wasmRustlangLibcUnixFtello(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcdrain(p0)")
private external fun wasmRustlangLibcUnixTcdrain(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixCfgetispeed(p0)")
private external fun wasmRustlangLibcUnixCfgetispeed(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixCfgetospeed(p0)")
private external fun wasmRustlangLibcUnixCfgetospeed(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixCfsetispeed(p0, p1)")
private external fun wasmRustlangLibcUnixCfsetispeed(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixCfsetospeed(p0, p1)")
private external fun wasmRustlangLibcUnixCfsetospeed(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcgetattr(p0, p1)")
private external fun wasmRustlangLibcUnixTcgetattr(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcsetattr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixTcsetattr(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcflow(p0, p1)")
private external fun wasmRustlangLibcUnixTcflow(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcflush(p0, p1)")
private external fun wasmRustlangLibcUnixTcflush(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcgetsid(p0)")
private external fun wasmRustlangLibcUnixTcgetsid(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixTcsendbreak(p0, p1)")
private external fun wasmRustlangLibcUnixTcsendbreak(p0: Int, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixMkstemp(p0)")
private external fun wasmRustlangLibcUnixMkstemp(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixMkdtemp(p0)")
private external fun wasmRustlangLibcUnixMkdtemp(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixTmpnam(p0)")
private external fun wasmRustlangLibcUnixTmpnam(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixOpenlog(p0, p1, p2)")
private external fun wasmRustlangLibcUnixOpenlog(p0: String, p1: Int, p2: Int)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcUnixCloselog()")
private external fun wasmRustlangLibcUnixCloselog()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixSetlogmask(p0)")
private external fun wasmRustlangLibcUnixSetlogmask(p0: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcUnixSyslog(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcUnixSyslog(p0: Int, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixNice(p0)")
private external fun wasmRustlangLibcUnixNice(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGrantpt(p0)")
private external fun wasmRustlangLibcUnixGrantpt(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPosixOpenpt(p0)")
private external fun wasmRustlangLibcUnixPosixOpenpt(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixPtsname(p0)")
private external fun wasmRustlangLibcUnixPtsname(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixUnlockpt(p0)")
private external fun wasmRustlangLibcUnixUnlockpt(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixStrcasestr(p0, p1)")
private external fun wasmRustlangLibcUnixStrcasestr(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetline(p0, p1, p2)")
private external fun wasmRustlangLibcUnixGetline(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixLockf(p0, p1, p2)")
private external fun wasmRustlangLibcUnixLockf(p0: Int, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixAdjtime(p0, p1)")
private external fun wasmRustlangLibcUnixAdjtime(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixStpncpy(p0, p1, p2)")
private external fun wasmRustlangLibcUnixStpncpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixConfstr(p0, p1, p2)")
private external fun wasmRustlangLibcUnixConfstr(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixDladdr(p0, p1)")
private external fun wasmRustlangLibcUnixDladdr(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixFlock(p0, p1)")
private external fun wasmRustlangLibcUnixFlock(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixOpenWmemstream(p0, p1)")
private external fun wasmRustlangLibcUnixOpenWmemstream(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixGetsid(p0)")
private external fun wasmRustlangLibcUnixGetsid(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixReaddirR(p0, p1, p2)")
private external fun wasmRustlangLibcUnixReaddirR(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcUnixReadlinkat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcUnixReadlinkat(p0: Int, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixReadlink(p0, p1, p2)")
private external fun wasmRustlangLibcUnixReadlink(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcUnixPselect(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcUnixPselect(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixSigaction(p0, p1, p2)")
private external fun wasmRustlangLibcUnixSigaction(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixFmemopen(p0, p1, p2)")
private external fun wasmRustlangLibcUnixFmemopen(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixOpenMemstream(p0, p1)")
private external fun wasmRustlangLibcUnixOpenMemstream(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixAtexit(p0)")
private external fun wasmRustlangLibcUnixAtexit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcUnixCfmakeraw(p0)")
private external fun wasmRustlangLibcUnixCfmakeraw(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcUnixCfsetspeed(p0, p1)")
private external fun wasmRustlangLibcUnixCfsetspeed(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcUnixFnmatch(p0, p1, p2)")
private external fun wasmRustlangLibcUnixFnmatch(p0: String, p1: String, p2: Int): Int

public actual val IN6ADDR_LOOPBACK: In6Addr
    get() = In6Addr()

public actual val IN6ADDR_ANY: In6Addr
    get() = In6Addr()

public actual fun isalnum(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsalnum(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalpha(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsalpha(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iscntrl(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIscntrl(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isdigit(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsdigit(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isgraph(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsgraph(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun islower(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIslower(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isprint(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsprint(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ispunct(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIspunct(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isspace(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsspace(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isupper(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsupper(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isxdigit(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsxdigit(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isblank(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsblank(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tolower(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTolower(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun toupper(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixToupper(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixQsort(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun bsearch(key: ConstPtr, base: ConstPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixBsearch(key.rawAddress.toString(), base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixFopen(filename.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixFreopen(filename.rawAddress.toString(), mode.rawAddress.toString(), file.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(file: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFflush(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(file: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFclose(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remove(filename: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixRemove(filename.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixRename(oldname.rawAddress.toString(), newname.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpfile(): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixTmpfile().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetvbuf(stream.rawAddress.toString(), buffer.rawAddress.toString(), mode, size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixSetbuf(stream.rawAddress.toString(), buf.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getchar(): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetchar() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putchar(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPutchar(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetc(stream: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFgetc(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixFgets(buf.rawAddress.toString(), n, stream.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFputc(c, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFputs(s.rawAddress.toString(), stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun puts(s: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPuts(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUngetc(c, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFread(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFwrite(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFseek(stream.rawAddress.toString(), offset.toString(), whence) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(stream: MutPtr): CLong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFtell(stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewind(stream: MutPtr) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixRewind(stream.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFgetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFsetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun feof(stream: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFeof(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ferror(stream: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFerror(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clearerr(stream: MutPtr) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixClearerr(stream.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun perror(s: ConstPtr) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixPerror(s.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun atof(s: ConstPtr): CDouble =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAtof(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoi(s: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAtoi(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atol(s: ConstPtr): CLong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAtol(s.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoll(s: ConstPtr): CLonglong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAtoll(s.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrtod(s.rawAddress.toString(), endp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrtof(s.rawAddress.toString(), endp.rawAddress.toString()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrtol(s.rawAddress.toString(), endp.rawAddress.toString(), base).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrtoll(s.rawAddress.toString(), endp.rawAddress.toString(), base).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrtoul(s.rawAddress.toString(), endp.rawAddress.toString(), base).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrtoull(s.rawAddress.toString(), endp.rawAddress.toString(), base).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixCalloc(nobj.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(size: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixMalloc(size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixRealloc(p.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(p: MutPtr) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixFree(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun abort(): Nothing {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixExit(status)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun system(s: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSystem(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getenv(s: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetenv(s.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrcpy(dst.rawAddress.toString(), src.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stpcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStpcpy(dst.rawAddress.toString(), src.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrcat(s.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrncat(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrcmp(cs.rawAddress.toString(), ct.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrncmp(cs.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrcoll(cs.rawAddress.toString(), ct.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrchr(cs.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrrchr(cs.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrspn(cs.rawAddress.toString(), ct.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrcspn(cs.rawAddress.toString(), ct.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strdup(cs: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrdup(cs.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrndup(cs.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrpbrk(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrstr(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrcasecmp(s1.rawAddress.toString(), s2.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrncasecmp(s1.rawAddress.toString(), s2.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(cs: ConstPtr): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrlen(cs.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrnlen(cs.rawAddress.toString(), maxlen.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerror(n: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrerror(n).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrtok(s.rawAddress.toString(), t.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtokR(s: MutPtr, t: ConstPtr, p: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrtokR(s.rawAddress.toString(), t.rawAddress.toString(), p.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStrxfrm(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strsignal(sig: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrsignal(sig).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcslen(buf: ConstPtr): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixWcslen(buf.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixWcstombs(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixMemchr(cx.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixWmemchr(cx.rawAddress.toString(), c.toInt(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMemcmp(cx.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixMemset(dest.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memccpy(dest: MutPtr, src: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixMemccpy(dest.rawAddress.toString(), src.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwnam(name: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetpwnam(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpwuid(uid: io.github.kotlinmania.rustlang.unix.UidT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetpwuid(uid).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFprintf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSnprintf(s.rawAddress.toString(), n.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSprintf(s.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFscanf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixScanf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSscanf(s.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcharUnlocked(): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetcharUnlocked() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putcharUnlocked(c: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPutcharUnlocked(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun socket(domain: CInt, ty: CInt, protocol: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSocket(domain, ty, protocol) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(socket: CInt, address: ConstPtr, len: SocklenT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixConnect(socket, address.rawAddress.toString(), len.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(socket: CInt, backlog: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixListen(socket, backlog) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAccept(socket, address.rawAddress.toString(), addressLen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetpeername(socket, address.rawAddress.toString(), addressLen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetsockname(socket, address.rawAddress.toString(), addressLen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(socket: CInt, level: CInt, name: CInt, value: ConstPtr, optionLen: SocklenT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetsockopt(socket, level, name, value.rawAddress.toString(), optionLen.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun socketpair(domain: CInt, type: CInt, protocol: CInt, socketVector: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSocketpair(domain, type, protocol, socketVector.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt, addr: ConstPtr, addrlen: SocklenT): SsizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSendto(socket, buf.rawAddress.toString(), len.toString(), flags, addr.rawAddress.toString(), addrlen.toInt()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixShutdown(socket, how) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixChmod(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFchmod(fd, mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFstat(fildes, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMkdir(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStat(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pclose(stream: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPclose(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixFdopen(fd, mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFileno(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixOpen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixCreat(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFcntl(fd, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun opendir(dirname: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixOpendir(dirname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopendir(fd: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixFdopendir(fd).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir(dirp: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixReaddir(dirp.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closedir(dirp: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixClosedir(dirp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewinddir(dirp: MutPtr) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixRewinddir(dirp.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fchmodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, flags: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFchmodat(dirfd, pathname.rawAddress.toString(), mode.toInt(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchown(fd: CInt, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFchown(fd, owner, group) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchownat(dirfd: CInt, pathname: ConstPtr, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT, flags: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFchownat(dirfd, pathname.rawAddress.toString(), owner, group, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixOpenat(dirfd, pathname.rawAddress.toString(), flags, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFstatat(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixLinkat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMkdirat(dirfd, pathname.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixRenameat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSymlinkat(target.rawAddress.toString(), newdirfd, linkpath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUnlinkat(dirfd, pathname.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAccess(path.rawAddress.toString(), amode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alarm(seconds: CUint): CUint =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAlarm(seconds.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(dir: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixChdir(dir.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchdir(dirfd: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFchdir(dirfd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixChown(path.rawAddress.toString(), uid, gid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lchown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixLchown(path.rawAddress.toString(), uid, gid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun close(fd: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixClose(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup(fd: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixDup(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup2(src: CInt, dst: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixDup2(src, dst) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixExecl(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixExecle(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execlp(file: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixExeclp(file.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execv(prog: ConstPtr, argv: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixExecv(prog.rawAddress.toString(), argv.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixExecve(prog.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixExecvp(c.rawAddress.toString(), argv.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fork(): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFork() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFpathconf(filedes, name).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetcwd(buf.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getegid(): GidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetegid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun geteuid(): UidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGeteuid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgid(): GidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetgid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getgroups(ngroupsMax: CInt, groups: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetgroups(ngroupsMax, groups.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getlogin(): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetlogin().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetopt(argc, argv.rawAddress.toString(), optstr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpgid(pid: PidT): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetpgid(pid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpgrp(): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetpgrp() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpid(): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetpid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getppid(): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetppid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getuid(): UidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetuid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIsatty(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixLink(src.rawAddress.toString(), dst.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixLseek(fd, offset.toString(), whence).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPathconf(path.rawAddress.toString(), name).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pause(): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPause() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pipe(fds: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPipe(fds.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixMemalign(memptr: MutPtr, align: SizeT, size: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPosixMemalign(memptr.rawAddress.toString(), align.toString(), size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alignedAlloc(alignment: SizeT, size: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixAlignedAlloc(alignment.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun read(fd: CInt, buf: MutPtr, count: SizeT): SsizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixRead(fd, buf.rawAddress.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rmdir(path: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixRmdir(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seteuid(uid: UidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSeteuid(uid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setegid(gid: GidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetegid(gid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setgid(gid: GidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetgid(gid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setpgid(pid: PidT, pgid: PidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetpgid(pid, pgid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsid(): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetsid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setuid(uid: UidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetuid(uid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setreuid(ruid: UidT, euid: UidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetreuid(ruid, euid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setregid(rgid: GidT, egid: GidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetregid(rgid, egid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleep(secs: CUint): CUint =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSleep(secs.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nanosleep(rqtp: ConstPtr, rmtp: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixNanosleep(rqtp.rawAddress.toString(), rmtp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcgetpgrp(fd: CInt): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcgetpgrp(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcsetpgrp(fd: CInt, pgrp: io.github.kotlinmania.rustlang.unix.PidT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcsetpgrp(fd, pgrp) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ttyname(fd: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixTtyname(fd).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ttynameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTtynameR(fd, buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlink(c: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUnlink(c.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wait(status: MutPtr): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixWait(status.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitpid(pid: PidT, status: MutPtr, options: CInt): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixWaitpid(pid, status.rawAddress.toString(), options) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun write(fd: CInt, buf: ConstPtr, count: SizeT): SsizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixWrite(fd, buf.rawAddress.toString(), count.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPread(fd, buf.rawAddress.toString(), count.toString(), offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPwrite(fd, buf.rawAddress.toString(), count.toString(), offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun umask(mask: ModeT): ModeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUmask(mask.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utime(file: ConstPtr, buf: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUtime(file.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun kill(pid: PidT, sig: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixKill(pid, sig) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun killpg(pgrp: PidT, sig: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixKillpg(pgrp, sig) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlock(addr: ConstPtr, len: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMlock(addr.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun munlock(addr: ConstPtr, len: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMunlock(addr.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mlockall(flags: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMlockall(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun munlockall(): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMunlockall() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mmap(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: OffT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixMmap(addr.rawAddress.toString(), len.toString(), prot, flags, fd, offset.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun munmap(addr: MutPtr, len: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMunmap(addr.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifNametoindex(ifname: ConstPtr): CUint =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixIfNametoindex(ifname.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ifIndextoname(ifindex: CUint, ifname: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixIfIndextoname(ifindex.toInt(), ifname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixLstat(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsync(fd: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFsync(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setenv(name: ConstPtr, value: ConstPtr, overwrite: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetenv(name.rawAddress.toString(), value.rawAddress.toString(), overwrite) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unsetenv(name: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUnsetenv(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSymlink(path1.rawAddress.toString(), path2.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTruncate(path.rawAddress.toString(), length.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFtruncate(fd, length.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSignal(signum, handler.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetrusage(resource, usage.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixRealpath(pathname.rawAddress.toString(), resolved.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun times(buf: MutPtr): ClockT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTimes(buf.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSelf(): PthreadT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadSelf().toLong().toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadEqual(t1.toLong().toString(), t2.toLong().toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadJoin(native.toLong().toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadExit(value: MutPtr): Nothing {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixPthreadExit(value.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function pthreadExit")
}

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadAttrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadAttrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadAttrGetstacksize(attr.rawAddress.toString(), stacksize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadAttrSetstacksize(attr.rawAddress.toString(), stackSize.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadAttrSetdetachstate(attr.rawAddress.toString(), state) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadDetach(thread: PthreadT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadDetach(thread.toLong().toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedYield(): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSchedYield() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadKeyCreate(key.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadKeyDelete(key.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixPthreadGetspecific(key.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadSetspecific(key.toInt(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadMutexInit(lock.rawAddress.toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadMutexDestroy(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadMutexLock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadMutexTrylock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadMutexUnlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadMutexattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadMutexattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadMutexattrSettype(attr.rawAddress.toString(), type) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadCondInit(cond.rawAddress.toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadCondWait(cond.rawAddress.toString(), lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadCondTimedwait(cond.rawAddress.toString(), lock.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadCondSignal(cond.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadCondBroadcast(cond.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadCondDestroy(cond.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadCondattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadCondattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockInit(lock.rawAddress.toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockDestroy(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockRdlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockTryrdlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockWrlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockTrywrlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockUnlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPthreadRwlockattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(sockfd: CInt, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetsockopt(sockfd, level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun raise(signum: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixRaise(signum) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimes(filename: ConstPtr, times: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUtimes(filename.rawAddress.toString(), times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlopen(filename: ConstPtr, flag: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixDlopen(filename.rawAddress.toString(), flag).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlerror(): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixDlerror().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlsym(handle: MutPtr, symbol: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixDlsym(handle.rawAddress.toString(), symbol.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dlclose(handle: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixDlclose(handle.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getaddrinfo(node: ConstPtr, service: ConstPtr, hints: ConstPtr, res: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetaddrinfo(node.rawAddress.toString(), service.rawAddress.toString(), hints.rawAddress.toString(), res.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeaddrinfo(res: MutPtr) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixFreeaddrinfo(res.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun hstrerror(errcode: CInt): ConstPtr =
    if (libcUnixNapiAvailable) ConstPtr(wasmRustlangLibcUnixHstrerror(errcode).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gaiStrerror(errcode: CInt): ConstPtr =
    if (libcUnixNapiAvailable) ConstPtr(wasmRustlangLibcUnixGaiStrerror(errcode).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun resInit(): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixResInit() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGmtimeR(timeP.rawAddress.toString(), result.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixLocaltimeR(timeP.rawAddress.toString(), result.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mktime(tm: MutPtr): TimeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMktime(tm.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun time(time: MutPtr): TimeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTime(time.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmtime(timeP: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGmtime(timeP.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localtime(timeP: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixLocaltime(timeP.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun difftime(time1: TimeT, time0: TimeT): CDouble =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixDifftime(time1.toString(), time0.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timegm(tm: MutPtr): TimeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTimegm(tm.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mknod(pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMknod(pathname.rawAddress.toString(), mode.toInt(), dev) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gethostname(name: MutPtr, len: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGethostname(name.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun endservent() {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixEndservent()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getservbyname(name: ConstPtr, proto: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetservbyname(name.rawAddress.toString(), proto.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getservbyport(port: CInt, proto: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetservbyport(port, proto.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getservent(): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetservent().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setservent(stayopen: CInt) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixSetservent(stayopen)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getprotobyname(name: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetprotobyname(name.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getprotobynumber(proto: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixGetprotobynumber(proto).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chroot(name: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixChroot(name.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun usleep(secs: CUint): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUsleep(secs.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSend(socket, buf.rawAddress.toString(), len.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixRecv(socket, buf.rawAddress.toString(), len.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putenv(string: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPutenv(string.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPoll(fds.rawAddress.toString(), nfds.toString(), timeout) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSelect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), errorfds.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixSetlocale(category, locale.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localeconv(): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixLocaleconv().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semWait(sem: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSemWait(sem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semTrywait(sem: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSemTrywait(sem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun semPost(sem: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSemPost(sem.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixStatvfs(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFstatvfs(fd, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigemptyset(set: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSigemptyset(set.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaddset(set: MutPtr, signum: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSigaddset(set.rawAddress.toString(), signum) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigfillset(set: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSigfillset(set.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigdelset(set: MutPtr, signum: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSigdelset(set.rawAddress.toString(), signum) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigismember(set: ConstPtr, signum: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSigismember(set.rawAddress.toString(), signum) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigprocmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSigprocmask(how, set.rawAddress.toString(), oldset.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigpending(set: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSigpending(set.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysconf(name: CInt): CLong =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSysconf(name).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkfifo(path: ConstPtr, mode: ModeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMkfifo(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFseeko(stream.rawAddress.toString(), offset.toString(), whence) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftello(stream: MutPtr): OffT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFtello(stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcdrain(fd: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcdrain(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfgetispeed(termios: ConstPtr): SpeedT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixCfgetispeed(termios.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfgetospeed(termios: ConstPtr): SpeedT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixCfgetospeed(termios.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfsetispeed(termios: MutPtr, speed: SpeedT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixCfsetispeed(termios.rawAddress.toString(), speed.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfsetospeed(termios: MutPtr, speed: SpeedT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixCfsetospeed(termios.rawAddress.toString(), speed.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcgetattr(fd: CInt, termios: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcgetattr(fd, termios.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcsetattr(fd: CInt, optionalActions: CInt, termios: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcsetattr(fd, optionalActions, termios.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcflow(fd: CInt, action: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcflow(fd, action) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcflush(fd: CInt, action: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcflush(fd, action) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcgetsid(fd: CInt): io.github.kotlinmania.rustlang.unix.PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcgetsid(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcsendbreak(fd: CInt, duration: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixTcsendbreak(fd, duration) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkstemp(template: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixMkstemp(template.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdtemp(template: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixMkdtemp(template.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpnam(ptr: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixTmpnam(ptr.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openlog(ident: ConstPtr, logopt: CInt, facility: CInt) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixOpenlog(ident.rawAddress.toString(), logopt, facility)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun closelog() {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixCloselog()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setlogmask(maskpri: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSetlogmask(maskpri) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syslog(priority: CInt, message: ConstPtr, vararg variadicArgs: Long) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixSyslog(priority, message.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun nice(incr: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixNice(incr) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun grantpt(fd: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGrantpt(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixOpenpt(flags: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPosixOpenpt(flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ptsname(fd: CInt): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixPtsname(fd).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlockpt(fd: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixUnlockpt(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStrcasestr(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetline(lineptr.rawAddress.toString(), n.rawAddress.toString(), stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lockf(fd: CInt, cmd: CInt, len: OffT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixLockf(fd, cmd, len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun adjtime(delta: ConstPtr, olddelta: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAdjtime(delta.rawAddress.toString(), olddelta.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stpncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixStpncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigqueue(pid: PidT, sig: CInt, value: io.github.kotlinmania.rustlang.unix.Sigval): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun confstr(name: CInt, buf: MutPtr, len: SizeT): SizeT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixConfstr(name, buf.rawAddress.toString(), len.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dladdr(addr: ConstPtr, info: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixDladdr(addr.rawAddress.toString(), info.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flock(fd: CInt, operation: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFlock(fd, operation) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openWmemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixOpenWmemstream(ptr.rawAddress.toString(), sizeloc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsid(pid: PidT): PidT =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixGetsid(pid) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdirR(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixReaddirR(dirp.rawAddress.toString(), entry.rawAddress.toString(), result.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixReadlinkat(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), bufsiz.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixReadlink(path.rawAddress.toString(), buf.rawAddress.toString(), bufsz.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pselect(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr, sigmask: ConstPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixPselect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), errorfds.rawAddress.toString(), timeout.rawAddress.toString(), sigmask.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixSigaction(signum, act.rawAddress.toString(), oldact.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fmemopen(buf: MutPtr, size: SizeT, mode: ConstPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixFmemopen(buf.rawAddress.toString(), size.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openMemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    if (libcUnixNapiAvailable) MutPtr(wasmRustlangLibcUnixOpenMemstream(ptr.rawAddress.toString(), sizeloc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atexit(cb: AtexitCbCallback): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixAtexit(0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cfmakeraw(termios: MutPtr) {
    if (libcUnixNapiAvailable) {
        wasmRustlangLibcUnixCfmakeraw(termios.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cfsetspeed(termios: MutPtr, speed: SpeedT): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixCfsetspeed(termios.rawAddress.toString(), speed.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fnmatch(pattern: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    if (libcUnixNapiAvailable) wasmRustlangLibcUnixFnmatch(pattern.rawAddress.toString(), name.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")
