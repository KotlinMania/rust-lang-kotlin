@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.windows

import io.github.kotlinmania.rustlang.core.ffi.CDouble
import io.github.kotlinmania.rustlang.core.ffi.CFloat
import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ffi.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initWindowsNapiAddon(): Boolean

private val windowsNapiAvailable: Boolean = initWindowsNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsalnum(p0)")
private external fun wasmLibcWindowsIsalnum(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsalpha(p0)")
private external fun wasmLibcWindowsIsalpha(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIscntrl(p0)")
private external fun wasmLibcWindowsIscntrl(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsdigit(p0)")
private external fun wasmLibcWindowsIsdigit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsgraph(p0)")
private external fun wasmLibcWindowsIsgraph(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIslower(p0)")
private external fun wasmLibcWindowsIslower(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsprint(p0)")
private external fun wasmLibcWindowsIsprint(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIspunct(p0)")
private external fun wasmLibcWindowsIspunct(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsspace(p0)")
private external fun wasmLibcWindowsIsspace(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsupper(p0)")
private external fun wasmLibcWindowsIsupper(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsxdigit(p0)")
private external fun wasmLibcWindowsIsxdigit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsblank(p0)")
private external fun wasmLibcWindowsIsblank(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsTolower(p0)")
private external fun wasmLibcWindowsTolower(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsToupper(p0)")
private external fun wasmLibcWindowsToupper(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcWindowsQsort(p0, p1, p2, p3)")
private external fun wasmLibcWindowsQsort(p0: String, p1: String, p2: String, p3: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcWindowsQsortS(p0, p1, p2, p3, p4)")
private external fun wasmLibcWindowsQsortS(p0: String, p1: String, p2: String, p3: String, p4: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsFopen(p0, p1)")
private external fun wasmLibcWindowsFopen(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsFreopen(p0, p1, p2)")
private external fun wasmLibcWindowsFreopen(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsFflush(p0)")
private external fun wasmLibcWindowsFflush(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsFclose(p0)")
private external fun wasmLibcWindowsFclose(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsRemove(p0)")
private external fun wasmLibcWindowsRemove(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsRename(p0, p1)")
private external fun wasmLibcWindowsRename(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcWindowsTmpfile()")
private external fun wasmLibcWindowsTmpfile(): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcWindowsSetvbuf(p0, p1, p2, p3)")
private external fun wasmLibcWindowsSetvbuf(p0: String, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsSetbuf(p0, p1)")
private external fun wasmLibcWindowsSetbuf(p0: String, p1: String)

@JsFun("() => globalThis.__km_napi_kmcore.libcWindowsGetchar()")
private external fun wasmLibcWindowsGetchar(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsPutchar(p0)")
private external fun wasmLibcWindowsPutchar(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsFgetc(p0)")
private external fun wasmLibcWindowsFgetc(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsFgets(p0, p1, p2)")
private external fun wasmLibcWindowsFgets(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsFputc(p0, p1)")
private external fun wasmLibcWindowsFputc(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsFputs(p0, p1)")
private external fun wasmLibcWindowsFputs(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsPuts(p0)")
private external fun wasmLibcWindowsPuts(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsUngetc(p0, p1)")
private external fun wasmLibcWindowsUngetc(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcWindowsFread(p0, p1, p2, p3)")
private external fun wasmLibcWindowsFread(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcWindowsFwrite(p0, p1, p2, p3)")
private external fun wasmLibcWindowsFwrite(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsFseek(p0, p1, p2)")
private external fun wasmLibcWindowsFseek(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsFtell(p0)")
private external fun wasmLibcWindowsFtell(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsRewind(p0)")
private external fun wasmLibcWindowsRewind(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsFgetpos(p0, p1)")
private external fun wasmLibcWindowsFgetpos(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsFsetpos(p0, p1)")
private external fun wasmLibcWindowsFsetpos(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsFeof(p0)")
private external fun wasmLibcWindowsFeof(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsFerror(p0)")
private external fun wasmLibcWindowsFerror(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsPerror(p0)")
private external fun wasmLibcWindowsPerror(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsAtof(p0)")
private external fun wasmLibcWindowsAtof(p0: String): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsAtoi(p0)")
private external fun wasmLibcWindowsAtoi(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsAtol(p0)")
private external fun wasmLibcWindowsAtol(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsAtoll(p0)")
private external fun wasmLibcWindowsAtoll(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrtod(p0, p1)")
private external fun wasmLibcWindowsStrtod(p0: String, p1: String): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrtof(p0, p1)")
private external fun wasmLibcWindowsStrtof(p0: String, p1: String): Double

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsStrtol(p0, p1, p2)")
private external fun wasmLibcWindowsStrtol(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsStrtoll(p0, p1, p2)")
private external fun wasmLibcWindowsStrtoll(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsStrtoul(p0, p1, p2)")
private external fun wasmLibcWindowsStrtoul(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsStrtoull(p0, p1, p2)")
private external fun wasmLibcWindowsStrtoull(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsCalloc(p0, p1)")
private external fun wasmLibcWindowsCalloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsMalloc(p0)")
private external fun wasmLibcWindowsMalloc(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsMsize(p0)")
private external fun wasmLibcWindowsMsize(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsRealloc(p0, p1)")
private external fun wasmLibcWindowsRealloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsFree(p0)")
private external fun wasmLibcWindowsFree(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.libcWindowsAbort()")
private external fun wasmLibcWindowsAbort()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsExit(p0)")
private external fun wasmLibcWindowsExit(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsAtexit(p0)")
private external fun wasmLibcWindowsAtexit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsSystem(p0)")
private external fun wasmLibcWindowsSystem(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsGetenv(p0)")
private external fun wasmLibcWindowsGetenv(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrcpy(p0, p1)")
private external fun wasmLibcWindowsStrcpy(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsStrncpy(p0, p1, p2)")
private external fun wasmLibcWindowsStrncpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrcat(p0, p1)")
private external fun wasmLibcWindowsStrcat(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsStrncat(p0, p1, p2)")
private external fun wasmLibcWindowsStrncat(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrcmp(p0, p1)")
private external fun wasmLibcWindowsStrcmp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsStrncmp(p0, p1, p2)")
private external fun wasmLibcWindowsStrncmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrcoll(p0, p1)")
private external fun wasmLibcWindowsStrcoll(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrchr(p0, p1)")
private external fun wasmLibcWindowsStrchr(p0: String, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrrchr(p0, p1)")
private external fun wasmLibcWindowsStrrchr(p0: String, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrspn(p0, p1)")
private external fun wasmLibcWindowsStrspn(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrcspn(p0, p1)")
private external fun wasmLibcWindowsStrcspn(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsStrdup(p0)")
private external fun wasmLibcWindowsStrdup(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrpbrk(p0, p1)")
private external fun wasmLibcWindowsStrpbrk(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrstr(p0, p1)")
private external fun wasmLibcWindowsStrstr(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsStrlen(p0)")
private external fun wasmLibcWindowsStrlen(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrnlen(p0, p1)")
private external fun wasmLibcWindowsStrnlen(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsStrerror(p0)")
private external fun wasmLibcWindowsStrerror(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStrtok(p0, p1)")
private external fun wasmLibcWindowsStrtok(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsStrxfrm(p0, p1, p2)")
private external fun wasmLibcWindowsStrxfrm(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsWcslen(p0)")
private external fun wasmLibcWindowsWcslen(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsWcsnlen(p0, p1)")
private external fun wasmLibcWindowsWcsnlen(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsWcstombs(p0, p1, p2)")
private external fun wasmLibcWindowsWcstombs(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsMemchr(p0, p1, p2)")
private external fun wasmLibcWindowsMemchr(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsMemcmp(p0, p1, p2)")
private external fun wasmLibcWindowsMemcmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsMemcpy(p0, p1, p2)")
private external fun wasmLibcWindowsMemcpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsMemmove(p0, p1, p2)")
private external fun wasmLibcWindowsMemmove(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsMemset(p0, p1, p2)")
private external fun wasmLibcWindowsMemset(p0: String, p1: Int, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsAbs(p0)")
private external fun wasmLibcWindowsAbs(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsLabs(p0)")
private external fun wasmLibcWindowsLabs(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.libcWindowsRand()")
private external fun wasmLibcWindowsRand(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsSrand(p0)")
private external fun wasmLibcWindowsSrand(p0: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsSignal(p0, p1)")
private external fun wasmLibcWindowsSignal(p0: Int, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsRaise(p0)")
private external fun wasmLibcWindowsRaise(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcWindowsClock()")
private external fun wasmLibcWindowsClock(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsGetDaylight(p0)")
private external fun wasmLibcWindowsGetDaylight(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsGetDstbias(p0)")
private external fun wasmLibcWindowsGetDstbias(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsGetTimezone(p0)")
private external fun wasmLibcWindowsGetTimezone(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcWindowsGetTzname(p0, p1, p2, p3)")
private external fun wasmLibcWindowsGetTzname(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.libcWindowsTzset()")
private external fun wasmLibcWindowsTzset()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsChmod(p0, p1)")
private external fun wasmLibcWindowsChmod(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsWchmod(p0, p1)")
private external fun wasmLibcWindowsWchmod(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsMkdir(p0)")
private external fun wasmLibcWindowsMkdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsWrmdir(p0)")
private external fun wasmLibcWindowsWrmdir(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsFstat(p0, p1)")
private external fun wasmLibcWindowsFstat(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsStat(p0, p1)")
private external fun wasmLibcWindowsStat(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsWstat(p0, p1)")
private external fun wasmLibcWindowsWstat(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsWutime(p0, p1)")
private external fun wasmLibcWindowsWutime(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsPopen(p0, p1)")
private external fun wasmLibcWindowsPopen(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsPclose(p0)")
private external fun wasmLibcWindowsPclose(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsFdopen(p0, p1)")
private external fun wasmLibcWindowsFdopen(p0: Int, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsFileno(p0)")
private external fun wasmLibcWindowsFileno(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsWopen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsWopen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsCreat(p0, p1)")
private external fun wasmLibcWindowsCreat(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsAccess(p0, p1)")
private external fun wasmLibcWindowsAccess(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsChdir(p0)")
private external fun wasmLibcWindowsChdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsClose(p0)")
private external fun wasmLibcWindowsClose(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsDup(p0)")
private external fun wasmLibcWindowsDup(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsDup2(p0, p1)")
private external fun wasmLibcWindowsDup2(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsExecl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsExecl(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsWexecl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsWexecl(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsExecle(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsExecle(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsWexecle(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsWexecle(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsExeclp(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsExeclp(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsWexeclp(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsWexeclp(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsExeclpe(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsExeclpe(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcWindowsWexeclpe(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcWindowsWexeclpe(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsExecv(p0, p1)")
private external fun wasmLibcWindowsExecv(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsExecve(p0, p1, p2)")
private external fun wasmLibcWindowsExecve(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsExecvp(p0, p1)")
private external fun wasmLibcWindowsExecvp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsExecvpe(p0, p1, p2)")
private external fun wasmLibcWindowsExecvpe(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsWexecv(p0, p1)")
private external fun wasmLibcWindowsWexecv(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsWexecve(p0, p1, p2)")
private external fun wasmLibcWindowsWexecve(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsWexecvp(p0, p1)")
private external fun wasmLibcWindowsWexecvp(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsWexecvpe(p0, p1, p2)")
private external fun wasmLibcWindowsWexecvpe(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsGetcwd(p0, p1)")
private external fun wasmLibcWindowsGetcwd(p0: String, p1: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.libcWindowsGetpid()")
private external fun wasmLibcWindowsGetpid(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsIsatty(p0)")
private external fun wasmLibcWindowsIsatty(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsLseek(p0, p1, p2)")
private external fun wasmLibcWindowsLseek(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsLseek64(p0, p1, p2)")
private external fun wasmLibcWindowsLseek64(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsPipe(p0, p1, p2)")
private external fun wasmLibcWindowsPipe(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsRead(p0, p1, p2)")
private external fun wasmLibcWindowsRead(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsRmdir(p0)")
private external fun wasmLibcWindowsRmdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsUnlink(p0)")
private external fun wasmLibcWindowsUnlink(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsWrite(p0, p1, p2)")
private external fun wasmLibcWindowsWrite(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsCommit(p0)")
private external fun wasmLibcWindowsCommit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsGetOsfhandle(p0)")
private external fun wasmLibcWindowsGetOsfhandle(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsOpenOsfhandle(p0, p1)")
private external fun wasmLibcWindowsOpenOsfhandle(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsSetlocale(p0, p1)")
private external fun wasmLibcWindowsSetlocale(p0: Int, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsWsetlocale(p0, p1)")
private external fun wasmLibcWindowsWsetlocale(p0: Int, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsAlignedMalloc(p0, p1)")
private external fun wasmLibcWindowsAlignedMalloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsAlignedFree(p0)")
private external fun wasmLibcWindowsAlignedFree(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsAlignedRealloc(p0, p1, p2)")
private external fun wasmLibcWindowsAlignedRealloc(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsPutenv(p0)")
private external fun wasmLibcWindowsPutenv(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcWindowsWputenv(p0)")
private external fun wasmLibcWindowsWputenv(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsPutenvS(p0, p1)")
private external fun wasmLibcWindowsPutenvS(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsWputenvS(p0, p1)")
private external fun wasmLibcWindowsWputenvS(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.libcWindowsListen(p0, p1)")
private external fun wasmLibcWindowsListen(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsAccept(p0, p1, p2)")
private external fun wasmLibcWindowsAccept(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsBind(p0, p1, p2)")
private external fun wasmLibcWindowsBind(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsConnect(p0, p1, p2)")
private external fun wasmLibcWindowsConnect(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsGetpeername(p0, p1, p2)")
private external fun wasmLibcWindowsGetpeername(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsGetsockname(p0, p1, p2)")
private external fun wasmLibcWindowsGetsockname(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcWindowsGetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmLibcWindowsGetsockopt(p0: String, p1: Int, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcWindowsRecvfrom(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcWindowsRecvfrom(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.libcWindowsSendto(p0, p1, p2, p3, p4, p5)")
private external fun wasmLibcWindowsSendto(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcWindowsSetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmLibcWindowsSetsockopt(p0: String, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcWindowsSocket(p0, p1, p2)")
private external fun wasmLibcWindowsSocket(p0: Int, p1: Int, p2: Int): String

public actual fun isalnum(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsalnum(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalpha(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsalpha(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iscntrl(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIscntrl(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isdigit(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsdigit(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isgraph(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsgraph(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun islower(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIslower(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isprint(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsprint(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ispunct(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIspunct(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isspace(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsspace(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isupper(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsupper(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isxdigit(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsxdigit(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isblank(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsblank(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tolower(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsTolower(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun toupper(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsToupper(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    if (windowsNapiAvailable) {
        wasmLibcWindowsQsort(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun qsortS(base: MutPtr, num: SizeT, size: SizeT, compar: QsortSComparCallback?, arg: MutPtr) {
    if (windowsNapiAvailable) {
        wasmLibcWindowsQsortS(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString(), arg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsFopen(filename.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsFreopen(filename.rawAddress.toString(), mode.rawAddress.toString(), file.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(file: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFflush(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(file: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFclose(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remove(filename: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsRemove(filename.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsRename(oldname.rawAddress.toString(), newname.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpfile(): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsTmpfile().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsSetvbuf(stream.rawAddress.toString(), buffer.rawAddress.toString(), mode, size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    if (windowsNapiAvailable) {
        wasmLibcWindowsSetbuf(stream.rawAddress.toString(), buf.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getchar(): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsGetchar() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putchar(c: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsPutchar(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetc(stream: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFgetc(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsFgets(buf.rawAddress.toString(), n, stream.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFputc(c, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFputs(s.rawAddress.toString(), stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun puts(s: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsPuts(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsUngetc(c, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsFread(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsFwrite(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFseek(stream.rawAddress.toString(), offset.toString(), whence) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(stream: MutPtr): CLong =
    if (windowsNapiAvailable) wasmLibcWindowsFtell(stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewind(stream: MutPtr) {
    if (windowsNapiAvailable) {
        wasmLibcWindowsRewind(stream.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFgetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFsetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun feof(stream: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFeof(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ferror(stream: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFerror(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun perror(s: ConstPtr) {
    if (windowsNapiAvailable) {
        wasmLibcWindowsPerror(s.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun atof(s: ConstPtr): CDouble =
    if (windowsNapiAvailable) wasmLibcWindowsAtof(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoi(s: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsAtoi(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atol(s: ConstPtr): CLong =
    if (windowsNapiAvailable) wasmLibcWindowsAtol(s.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoll(s: ConstPtr): CLonglong =
    if (windowsNapiAvailable) wasmLibcWindowsAtoll(s.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    if (windowsNapiAvailable) wasmLibcWindowsStrtod(s.rawAddress.toString(), endp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    if (windowsNapiAvailable) wasmLibcWindowsStrtof(s.rawAddress.toString(), endp.rawAddress.toString()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    if (windowsNapiAvailable) wasmLibcWindowsStrtol(s.rawAddress.toString(), endp.rawAddress.toString(), base).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    if (windowsNapiAvailable) wasmLibcWindowsStrtoll(s.rawAddress.toString(), endp.rawAddress.toString(), base).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    if (windowsNapiAvailable) wasmLibcWindowsStrtoul(s.rawAddress.toString(), endp.rawAddress.toString(), base).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    if (windowsNapiAvailable) wasmLibcWindowsStrtoull(s.rawAddress.toString(), endp.rawAddress.toString(), base).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsCalloc(nobj.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(size: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsMalloc(size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msize(p: MutPtr): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsMsize(p.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsRealloc(p.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(p: MutPtr) {
    if (windowsNapiAvailable) {
        wasmLibcWindowsFree(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun abort(): Nothing {
    if (windowsNapiAvailable) {
        wasmLibcWindowsAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    if (windowsNapiAvailable) {
        wasmLibcWindowsExit(status)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun atexit(cb: AtexitCbCallback): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsAtexit(0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun system(s: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsSystem(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getenv(s: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsGetenv(s.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrcpy(dst.rawAddress.toString(), src.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrcat(s.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrncat(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsStrcmp(cs.rawAddress.toString(), ct.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsStrncmp(cs.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsStrcoll(cs.rawAddress.toString(), ct.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrchr(cs.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrrchr(cs.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsStrspn(cs.rawAddress.toString(), ct.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsStrcspn(cs.rawAddress.toString(), ct.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strdup(cs: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrdup(cs.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrpbrk(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrstr(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(cs: ConstPtr): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsStrlen(cs.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsStrnlen(cs.rawAddress.toString(), maxlen.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerror(n: CInt): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrerror(n).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsStrtok(s.rawAddress.toString(), t.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsStrxfrm(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcslen(buf: ConstPtr): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsWcslen(buf.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcsnlen(str: ConstPtr, numberOfElements: SizeT): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsWcsnlen(str.rawAddress.toString(), numberOfElements.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    if (windowsNapiAvailable) wasmLibcWindowsWcstombs(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsMemchr(cx.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsMemcmp(cx.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsMemset(dest.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun abs(i: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsAbs(i) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun labs(i: CLong): CLong =
    if (windowsNapiAvailable) wasmLibcWindowsLabs(i.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsRand() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand(seed: CUint) {
    if (windowsNapiAvailable) {
        wasmLibcWindowsSrand(seed.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    if (windowsNapiAvailable) wasmLibcWindowsSignal(signum, handler.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun raise(signum: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsRaise(signum) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clock(): ClockT =
    if (windowsNapiAvailable) wasmLibcWindowsClock() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getDaylight(hours: MutPtr): ErrnoT =
    if (windowsNapiAvailable) wasmLibcWindowsGetDaylight(hours.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getDstbias(seconds: MutPtr): ErrnoT =
    if (windowsNapiAvailable) wasmLibcWindowsGetDstbias(seconds.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTimezone(seconds: MutPtr): ErrnoT =
    if (windowsNapiAvailable) wasmLibcWindowsGetTimezone(seconds.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTzname(pReturnValue: MutPtr, timeZoneName: MutPtr, sizeInBytes: SizeT, index: CInt): ErrnoT =
    if (windowsNapiAvailable) wasmLibcWindowsGetTzname(pReturnValue.rawAddress.toString(), timeZoneName.rawAddress.toString(), sizeInBytes.toString(), index) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tzset() {
    if (windowsNapiAvailable) {
        wasmLibcWindowsTzset()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun chmod(path: ConstPtr, mode: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsChmod(path.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wchmod(path: ConstPtr, mode: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsWchmod(path.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdir(path: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsMkdir(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wrmdir(path: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsWrmdir(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFstat(fildes, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsStat(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wstat(path: ConstPtr, buf: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsWstat(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wutime(file: ConstPtr, buf: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsWutime(file.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsPopen(command.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pclose(stream: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsPclose(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsFdopen(fd, mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsFileno(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsOpen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wopen(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsWopen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat(path: ConstPtr, mode: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsCreat(path.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsAccess(path.rawAddress.toString(), amode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(dir: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsChdir(dir.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun close(fd: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsClose(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup(fd: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsDup(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup2(src: CInt, dst: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsDup2(src, dst) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsExecl(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsWexecl(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsExecle(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsWexecle(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execlp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsExeclp(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexeclp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsWexeclp(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execlpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsExeclpe(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexeclpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsWexeclpe(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsExecv(prog.rawAddress.toString(), argv.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsExecve(prog.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsExecvp(c.rawAddress.toString(), argv.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsExecvpe(c.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsWexecv(prog.rawAddress.toString(), argv.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsWexecve(prog.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecvp(c: ConstPtr, argv: ConstPtr): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsWexecvp(c.rawAddress.toString(), argv.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsWexecvpe(c.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: CInt): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsGetcwd(buf.rawAddress.toString(), size).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpid(): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsGetpid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsIsatty(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek(fd: CInt, offset: CLong, origin: CInt): CLong =
    if (windowsNapiAvailable) wasmLibcWindowsLseek(fd, offset.toString(), origin).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek64(fd: CInt, offset: CLonglong, origin: CInt): CLonglong =
    if (windowsNapiAvailable) wasmLibcWindowsLseek64(fd, offset.toString(), origin).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pipe(fds: MutPtr, psize: CUint, textmode: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsPipe(fds.rawAddress.toString(), psize.toInt(), textmode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun read(fd: CInt, buf: MutPtr, count: CUint): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsRead(fd, buf.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rmdir(path: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsRmdir(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlink(c: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsUnlink(c.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun write(fd: CInt, buf: ConstPtr, count: CUint): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsWrite(fd, buf.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun commit(fd: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsCommit(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getOsfhandle(fd: CInt): IntptrT =
    if (windowsNapiAvailable) wasmLibcWindowsGetOsfhandle(fd).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openOsfhandle(osfhandle: IntptrT, flags: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsOpenOsfhandle(osfhandle.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsSetlocale(category, locale.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wsetlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsWsetlocale(category, locale.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alignedMalloc(size: SizeT, alignment: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsAlignedMalloc(size.toString(), alignment.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alignedFree(ptr: MutPtr) {
    if (windowsNapiAvailable) {
        wasmLibcWindowsAlignedFree(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun alignedRealloc(memblock: MutPtr, size: SizeT, alignment: SizeT): MutPtr =
    if (windowsNapiAvailable) MutPtr(wasmLibcWindowsAlignedRealloc(memblock.rawAddress.toString(), size.toString(), alignment.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putenv(envstring: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsPutenv(envstring.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wputenv(envstring: ConstPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsWputenv(envstring.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.libc.ErrnoT =
    if (windowsNapiAvailable) wasmLibcWindowsPutenvS(envstring.rawAddress.toString(), valueString.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wputenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.libc.ErrnoT =
    if (windowsNapiAvailable) wasmLibcWindowsWputenvS(envstring.rawAddress.toString(), valueString.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(s: Socket, backlog: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsListen(s.toString(), backlog) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    if (windowsNapiAvailable) wasmLibcWindowsAccept(s.toString(), addr.rawAddress.toString(), addrlen.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsBind(s.toString(), name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsConnect(s.toString(), name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsGetpeername(s.toString(), name.rawAddress.toString(), nameln.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsGetsockname(s.toString(), name.rawAddress.toString(), nameln.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(s: Socket, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsGetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: CInt, flags: CInt, from: MutPtr, fromlen: MutPtr): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsRecvfrom(s.toString(), buf.rawAddress.toString(), len, flags, from.rawAddress.toString(), fromlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(s: Socket, buf: ConstPtr, len: CInt, flags: CInt, to: ConstPtr, tolen: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsSendto(s.toString(), buf.rawAddress.toString(), len, flags, to.rawAddress.toString(), tolen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(s: Socket, level: CInt, optname: CInt, optval: ConstPtr, optlen: CInt): CInt =
    if (windowsNapiAvailable) wasmLibcWindowsSetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun socket(af: CInt, socketType: CInt, protocol: CInt): Socket =
    if (windowsNapiAvailable) wasmLibcWindowsSocket(af, socketType, protocol).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")
