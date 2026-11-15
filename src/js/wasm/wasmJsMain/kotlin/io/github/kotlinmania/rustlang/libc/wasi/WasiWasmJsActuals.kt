@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.wasi

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

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLibcWasiNapiAddon(): Boolean

private val libcWasiNapiAvailable: Boolean = initLibcWasiNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiGetENVIRON()")
private external fun wasmRustlangLibcWasiGetENVIRON(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiSetENVIRON(p0)")
private external fun wasmRustlangLibcWasiSetENVIRON(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiGetCLOCKMONOTONIC1()")
private external fun wasmRustlangLibcWasiGetCLOCKMONOTONIC1(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiGetCLOCKREALTIME1()")
private external fun wasmRustlangLibcWasiGetCLOCKREALTIME1(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiExit(p0)")
private external fun wasmRustlangLibcWasiExit(p0: Int)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiAbort()")
private external fun wasmRustlangLibcWasiAbort()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiAlignedAlloc(p0, p1)")
private external fun wasmRustlangLibcWasiAlignedAlloc(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiCalloc(p0, p1)")
private external fun wasmRustlangLibcWasiCalloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFree(p0)")
private external fun wasmRustlangLibcWasiFree(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiGetenv(p0)")
private external fun wasmRustlangLibcWasiGetenv(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiMalloc(p0)")
private external fun wasmRustlangLibcWasiMalloc(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiMallocUsableSize(p0)")
private external fun wasmRustlangLibcWasiMallocUsableSize(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiSbrk(p0)")
private external fun wasmRustlangLibcWasiSbrk(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiRand()")
private external fun wasmRustlangLibcWasiRand(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiRead(p0, p1, p2)")
private external fun wasmRustlangLibcWasiRead(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiRealloc(p0, p1)")
private external fun wasmRustlangLibcWasiRealloc(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiSetenv(p0, p1, p2)")
private external fun wasmRustlangLibcWasiSetenv(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiUnsetenv(p0)")
private external fun wasmRustlangLibcWasiUnsetenv(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiClearenv()")
private external fun wasmRustlangLibcWasiClearenv(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWrite(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWrite(p0: Int, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFopen(p0, p1)")
private external fun wasmRustlangLibcWasiFopen(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiFreopen(p0, p1, p2)")
private external fun wasmRustlangLibcWasiFreopen(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFclose(p0)")
private external fun wasmRustlangLibcWasiFclose(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiRemove(p0)")
private external fun wasmRustlangLibcWasiRemove(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiRename(p0, p1)")
private external fun wasmRustlangLibcWasiRename(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFeof(p0)")
private external fun wasmRustlangLibcWasiFeof(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFerror(p0)")
private external fun wasmRustlangLibcWasiFerror(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFflush(p0)")
private external fun wasmRustlangLibcWasiFflush(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiClearerr(p0)")
private external fun wasmRustlangLibcWasiClearerr(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiFseek(p0, p1, p2)")
private external fun wasmRustlangLibcWasiFseek(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFtell(p0)")
private external fun wasmRustlangLibcWasiFtell(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiRewind(p0)")
private external fun wasmRustlangLibcWasiRewind(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFgetpos(p0, p1)")
private external fun wasmRustlangLibcWasiFgetpos(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFsetpos(p0, p1)")
private external fun wasmRustlangLibcWasiFsetpos(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiFread(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiFread(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiFwrite(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiFwrite(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFgetc(p0)")
private external fun wasmRustlangLibcWasiFgetc(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiGetc(p0)")
private external fun wasmRustlangLibcWasiGetc(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiGetchar()")
private external fun wasmRustlangLibcWasiGetchar(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiUngetc(p0, p1)")
private external fun wasmRustlangLibcWasiUngetc(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFputc(p0, p1)")
private external fun wasmRustlangLibcWasiFputc(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPutc(p0, p1)")
private external fun wasmRustlangLibcWasiPutc(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPutchar(p0)")
private external fun wasmRustlangLibcWasiPutchar(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFputs(p0, p1)")
private external fun wasmRustlangLibcWasiFputs(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPuts(p0)")
private external fun wasmRustlangLibcWasiPuts(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPerror(p0)")
private external fun wasmRustlangLibcWasiPerror(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiSrand(p0)")
private external fun wasmRustlangLibcWasiSrand(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiAtexit(p0)")
private external fun wasmRustlangLibcWasiAtexit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiAtQuickExit(p0)")
private external fun wasmRustlangLibcWasiAtQuickExit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiQuickExit(p0)")
private external fun wasmRustlangLibcWasiQuickExit(p0: Int)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiPosixMemalign(p0, p1, p2)")
private external fun wasmRustlangLibcWasiPosixMemalign(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiRandR(p0)")
private external fun wasmRustlangLibcWasiRandR(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiRandom()")
private external fun wasmRustlangLibcWasiRandom(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiSrandom(p0)")
private external fun wasmRustlangLibcWasiSrandom(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPutenv(p0)")
private external fun wasmRustlangLibcWasiPutenv(p0: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiClock()")
private external fun wasmRustlangLibcWasiClock(): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiTime(p0)")
private external fun wasmRustlangLibcWasiTime(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiDifftime(p0, p1)")
private external fun wasmRustlangLibcWasiDifftime(p0: String, p1: String): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiMktime(p0)")
private external fun wasmRustlangLibcWasiMktime(p0: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrftime(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiStrftime(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiGmtime(p0)")
private external fun wasmRustlangLibcWasiGmtime(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiGmtimeR(p0, p1)")
private external fun wasmRustlangLibcWasiGmtimeR(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiLocaltime(p0)")
private external fun wasmRustlangLibcWasiLocaltime(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiLocaltimeR(p0, p1)")
private external fun wasmRustlangLibcWasiLocaltimeR(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiAsctimeR(p0, p1)")
private external fun wasmRustlangLibcWasiAsctimeR(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiCtimeR(p0, p1)")
private external fun wasmRustlangLibcWasiCtimeR(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiNanosleep(p0, p1)")
private external fun wasmRustlangLibcWasiNanosleep(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsalnum(p0)")
private external fun wasmRustlangLibcWasiIsalnum(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsalpha(p0)")
private external fun wasmRustlangLibcWasiIsalpha(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIscntrl(p0)")
private external fun wasmRustlangLibcWasiIscntrl(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsdigit(p0)")
private external fun wasmRustlangLibcWasiIsdigit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsgraph(p0)")
private external fun wasmRustlangLibcWasiIsgraph(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIslower(p0)")
private external fun wasmRustlangLibcWasiIslower(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsprint(p0)")
private external fun wasmRustlangLibcWasiIsprint(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIspunct(p0)")
private external fun wasmRustlangLibcWasiIspunct(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsspace(p0)")
private external fun wasmRustlangLibcWasiIsspace(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsupper(p0)")
private external fun wasmRustlangLibcWasiIsupper(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsxdigit(p0)")
private external fun wasmRustlangLibcWasiIsxdigit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsblank(p0)")
private external fun wasmRustlangLibcWasiIsblank(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiTolower(p0)")
private external fun wasmRustlangLibcWasiTolower(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiToupper(p0)")
private external fun wasmRustlangLibcWasiToupper(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiSetvbuf(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiSetvbuf(p0: String, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiSetbuf(p0, p1)")
private external fun wasmRustlangLibcWasiSetbuf(p0: String, p1: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiFgets(p0, p1, p2)")
private external fun wasmRustlangLibcWasiFgets(p0: String, p1: Int, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiAtof(p0)")
private external fun wasmRustlangLibcWasiAtof(p0: String): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiAtoi(p0)")
private external fun wasmRustlangLibcWasiAtoi(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiAtol(p0)")
private external fun wasmRustlangLibcWasiAtol(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiAtoll(p0)")
private external fun wasmRustlangLibcWasiAtoll(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrtod(p0, p1)")
private external fun wasmRustlangLibcWasiStrtod(p0: String, p1: String): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrtof(p0, p1)")
private external fun wasmRustlangLibcWasiStrtof(p0: String, p1: String): Double

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrtol(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrtol(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrtoll(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrtoll(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrtoul(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrtoul(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrtoull(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrtoull(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrcpy(p0, p1)")
private external fun wasmRustlangLibcWasiStrcpy(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrncpy(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrncpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrcat(p0, p1)")
private external fun wasmRustlangLibcWasiStrcat(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrncat(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrncat(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrcmp(p0, p1)")
private external fun wasmRustlangLibcWasiStrcmp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrncmp(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrncmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrcoll(p0, p1)")
private external fun wasmRustlangLibcWasiStrcoll(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrchr(p0, p1)")
private external fun wasmRustlangLibcWasiStrchr(p0: String, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrrchr(p0, p1)")
private external fun wasmRustlangLibcWasiStrrchr(p0: String, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrspn(p0, p1)")
private external fun wasmRustlangLibcWasiStrspn(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrcspn(p0, p1)")
private external fun wasmRustlangLibcWasiStrcspn(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrdup(p0)")
private external fun wasmRustlangLibcWasiStrdup(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrndup(p0, p1)")
private external fun wasmRustlangLibcWasiStrndup(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrpbrk(p0, p1)")
private external fun wasmRustlangLibcWasiStrpbrk(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrstr(p0, p1)")
private external fun wasmRustlangLibcWasiStrstr(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrcasecmp(p0, p1)")
private external fun wasmRustlangLibcWasiStrcasecmp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrncasecmp(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrncasecmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrlen(p0)")
private external fun wasmRustlangLibcWasiStrlen(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrnlen(p0, p1)")
private external fun wasmRustlangLibcWasiStrnlen(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrerror(p0)")
private external fun wasmRustlangLibcWasiStrerror(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrtok(p0, p1)")
private external fun wasmRustlangLibcWasiStrtok(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrxfrm(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrxfrm(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiMemchr(p0, p1, p2)")
private external fun wasmRustlangLibcWasiMemchr(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiMemcmp(p0, p1, p2)")
private external fun wasmRustlangLibcWasiMemcmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiMemcpy(p0, p1, p2)")
private external fun wasmRustlangLibcWasiMemcpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiMemmove(p0, p1, p2)")
private external fun wasmRustlangLibcWasiMemmove(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiMemset(p0, p1, p2)")
private external fun wasmRustlangLibcWasiMemset(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWasiFprintf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWasiFprintf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcWasiPrintf(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcWasiPrintf(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcWasiSnprintf(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcWasiSnprintf(p0: String, p1: String, p2: String, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWasiSprintf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWasiSprintf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWasiFscanf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWasiFscanf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcWasiScanf(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcWasiScanf(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWasiSscanf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWasiSscanf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiGetcharUnlocked()")
private external fun wasmRustlangLibcWasiGetcharUnlocked(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPutcharUnlocked(p0)")
private external fun wasmRustlangLibcWasiPutcharUnlocked(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiShutdown(p0, p1)")
private external fun wasmRustlangLibcWasiShutdown(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFstat(p0, p1)")
private external fun wasmRustlangLibcWasiFstat(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiMkdir(p0, p1)")
private external fun wasmRustlangLibcWasiMkdir(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStat(p0, p1)")
private external fun wasmRustlangLibcWasiStat(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFdopen(p0, p1)")
private external fun wasmRustlangLibcWasiFdopen(p0: Int, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFileno(p0)")
private external fun wasmRustlangLibcWasiFileno(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWasiOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWasiOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiCreat(p0, p1)")
private external fun wasmRustlangLibcWasiCreat(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWasiFcntl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWasiFcntl(p0: Int, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiOpendir(p0)")
private external fun wasmRustlangLibcWasiOpendir(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFdopendir(p0)")
private external fun wasmRustlangLibcWasiFdopendir(p0: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiReaddir(p0)")
private external fun wasmRustlangLibcWasiReaddir(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiClosedir(p0)")
private external fun wasmRustlangLibcWasiClosedir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiRewinddir(p0)")
private external fun wasmRustlangLibcWasiRewinddir(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiDirfd(p0)")
private external fun wasmRustlangLibcWasiDirfd(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiSeekdir(p0, p1)")
private external fun wasmRustlangLibcWasiSeekdir(p0: String, p1: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiTelldir(p0)")
private external fun wasmRustlangLibcWasiTelldir(p0: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcWasiOpenat(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcWasiOpenat(p0: Int, p1: String, p2: Int, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiFstatat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiFstatat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcWasiLinkat(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcWasiLinkat(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiMkdirat(p0, p1, p2)")
private external fun wasmRustlangLibcWasiMkdirat(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiReadlinkat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiReadlinkat(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiRenameat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiRenameat(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiSymlinkat(p0, p1, p2)")
private external fun wasmRustlangLibcWasiSymlinkat(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiUnlinkat(p0, p1, p2)")
private external fun wasmRustlangLibcWasiUnlinkat(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiAccess(p0, p1)")
private external fun wasmRustlangLibcWasiAccess(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiClose(p0)")
private external fun wasmRustlangLibcWasiClose(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFpathconf(p0, p1)")
private external fun wasmRustlangLibcWasiFpathconf(p0: Int, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiGetopt(p0, p1, p2)")
private external fun wasmRustlangLibcWasiGetopt(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiIsatty(p0)")
private external fun wasmRustlangLibcWasiIsatty(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiLink(p0, p1)")
private external fun wasmRustlangLibcWasiLink(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiLseek(p0, p1, p2)")
private external fun wasmRustlangLibcWasiLseek(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPathconf(p0, p1)")
private external fun wasmRustlangLibcWasiPathconf(p0: String, p1: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiRmdir(p0)")
private external fun wasmRustlangLibcWasiRmdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiSleep(p0)")
private external fun wasmRustlangLibcWasiSleep(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiUnlink(p0)")
private external fun wasmRustlangLibcWasiUnlink(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiPread(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiPread(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiPwrite(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiPwrite(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiLstat(p0, p1)")
private external fun wasmRustlangLibcWasiLstat(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFsync(p0)")
private external fun wasmRustlangLibcWasiFsync(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFdatasync(p0)")
private external fun wasmRustlangLibcWasiFdatasync(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiSymlink(p0, p1)")
private external fun wasmRustlangLibcWasiSymlink(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiTruncate(p0, p1)")
private external fun wasmRustlangLibcWasiTruncate(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFtruncate(p0, p1)")
private external fun wasmRustlangLibcWasiFtruncate(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiGetrusage(p0, p1)")
private external fun wasmRustlangLibcWasiGetrusage(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiGettimeofday(p0, p1)")
private external fun wasmRustlangLibcWasiGettimeofday(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiTimes(p0)")
private external fun wasmRustlangLibcWasiTimes(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrerrorR(p0, p1, p2)")
private external fun wasmRustlangLibcWasiStrerrorR(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiUsleep(p0)")
private external fun wasmRustlangLibcWasiUsleep(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiSend(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiSend(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiRecv(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiRecv(p0: Int, p1: String, p2: String, p3: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiPoll(p0, p1, p2)")
private external fun wasmRustlangLibcWasiPoll(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiSetlocale(p0, p1)")
private external fun wasmRustlangLibcWasiSetlocale(p0: Int, p1: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiLocaleconv()")
private external fun wasmRustlangLibcWasiLocaleconv(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiReadlink(p0, p1, p2)")
private external fun wasmRustlangLibcWasiReadlink(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiTimegm(p0)")
private external fun wasmRustlangLibcWasiTimegm(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiSysconf(p0)")
private external fun wasmRustlangLibcWasiSysconf(p0: Int): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWasiIoctl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWasiIoctl(p0: Int, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiFseeko(p0, p1, p2)")
private external fun wasmRustlangLibcWasiFseeko(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFtello(p0)")
private external fun wasmRustlangLibcWasiFtello(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiPosixFallocate(p0, p1, p2)")
private external fun wasmRustlangLibcWasiPosixFallocate(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiStrcasestr(p0, p1)")
private external fun wasmRustlangLibcWasiStrcasestr(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiGetline(p0, p1, p2)")
private external fun wasmRustlangLibcWasiGetline(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiFaccessat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiFaccessat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWritev(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWritev(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiReadv(p0, p1, p2)")
private external fun wasmRustlangLibcWasiReadv(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiPwritev(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiPwritev(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiPreadv(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiPreadv(p0: Int, p1: String, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiPosixFadvise(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiPosixFadvise(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFutimens(p0, p1)")
private external fun wasmRustlangLibcWasiFutimens(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiUtimensat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiUtimensat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiGetentropy(p0, p1)")
private external fun wasmRustlangLibcWasiGetentropy(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiMemrchr(p0, p1, p2)")
private external fun wasmRustlangLibcWasiMemrchr(p0: String, p1: Int, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiAbs(p0)")
private external fun wasmRustlangLibcWasiAbs(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiLabs(p0)")
private external fun wasmRustlangLibcWasiLabs(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiDuplocale(p0)")
private external fun wasmRustlangLibcWasiDuplocale(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiFreelocale(p0)")
private external fun wasmRustlangLibcWasiFreelocale(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiNewlocale(p0, p1, p2)")
private external fun wasmRustlangLibcWasiNewlocale(p0: Int, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiUselocale(p0)")
private external fun wasmRustlangLibcWasiUselocale(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiSchedYield()")
private external fun wasmRustlangLibcWasiSchedYield(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiGetcwd(p0, p1)")
private external fun wasmRustlangLibcWasiGetcwd(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiChdir(p0)")
private external fun wasmRustlangLibcWasiChdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiNlLanginfo(p0)")
private external fun wasmRustlangLibcWasiNlLanginfo(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiNlLanginfoL(p0, p1)")
private external fun wasmRustlangLibcWasiNlLanginfoL(p0: Int, p1: String): String

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcWasiSelect(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcWasiSelect(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcRegisterPreopenedFd(p0, p1)")
private external fun wasmRustlangLibcWasiWasilibcRegisterPreopenedFd(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcFdRenumber(p0, p1)")
private external fun wasmRustlangLibcWasiWasilibcFdRenumber(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcUnlinkat(p0, p1)")
private external fun wasmRustlangLibcWasiWasilibcUnlinkat(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcRmdirat(p0, p1)")
private external fun wasmRustlangLibcWasiWasilibcRmdirat(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcFindRelpath(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiWasilibcFindRelpath(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcTell(p0)")
private external fun wasmRustlangLibcWasiWasilibcTell(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdWasilibcUnlinkat(p0, p1)")
private external fun wasmRustlangLibcWasiWasilibcNocwdWasilibcUnlinkat(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdWasilibcRmdirat(p0, p1)")
private external fun wasmRustlangLibcWasiWasilibcNocwdWasilibcRmdirat(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdLinkat(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcWasiWasilibcNocwdLinkat(p0: Int, p1: String, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdSymlinkat(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWasilibcNocwdSymlinkat(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdReadlinkat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiWasilibcNocwdReadlinkat(p0: Int, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdFaccessat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiWasilibcNocwdFaccessat(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdRenameat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiWasilibcNocwdRenameat(p0: Int, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdOpenatNomode(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWasilibcNocwdOpenatNomode(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdFstatat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiWasilibcNocwdFstatat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdMkdiratNomode(p0, p1)")
private external fun wasmRustlangLibcWasiWasilibcNocwdMkdiratNomode(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdUtimensat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiWasilibcNocwdUtimensat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcNocwdOpendirat(p0, p1)")
private external fun wasmRustlangLibcWasiWasilibcNocwdOpendirat(p0: Int, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcAccess(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWasilibcAccess(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcStat(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWasilibcStat(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcUtimens(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWasilibcUtimens(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcLink(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWasilibcLink(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcLinkOldat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiWasilibcLinkOldat(p0: Int, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcLinkNewat(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiWasilibcLinkNewat(p0: String, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcRenameOldat(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWasilibcRenameOldat(p0: Int, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiWasilibcRenameNewat(p0, p1, p2)")
private external fun wasmRustlangLibcWasiWasilibcRenameNewat(p0: String, p1: Int, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiArc4random()")
private external fun wasmRustlangLibcWasiArc4random(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiArc4randomBuf(p0, p1)")
private external fun wasmRustlangLibcWasiArc4randomBuf(p0: String, p1: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiArc4randomUniform(p0)")
private external fun wasmRustlangLibcWasiArc4randomUniform(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiErrnoLocation()")
private external fun wasmRustlangLibcWasiErrnoLocation(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiChmod(p0, p1)")
private external fun wasmRustlangLibcWasiChmod(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiFchmod(p0, p1)")
private external fun wasmRustlangLibcWasiFchmod(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiRealpath(p0, p1)")
private external fun wasmRustlangLibcWasiRealpath(p0: String, p1: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSelf()")
private external fun wasmRustlangLibcWasiPthreadSelf(): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCreate(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWasiPthreadCreate(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadEqual(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadEqual(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadJoin(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadJoin(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrInit(p0)")
private external fun wasmRustlangLibcWasiPthreadAttrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadAttrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrGetstacksize(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadAttrGetstacksize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrSetstacksize(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadAttrSetstacksize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrGetdetachstate(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadAttrGetdetachstate(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrSetdetachstate(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadAttrSetdetachstate(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrGetguardsize(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadAttrGetguardsize(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrSetguardsize(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadAttrSetguardsize(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrGetstack(p0, p1, p2)")
private external fun wasmRustlangLibcWasiPthreadAttrGetstack(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadAttrSetstack(p0, p1, p2)")
private external fun wasmRustlangLibcWasiPthreadAttrSetstack(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadDetach(p0)")
private external fun wasmRustlangLibcWasiPthreadDetach(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSetcancelstate(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadSetcancelstate(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSetcanceltype(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadSetcanceltype(p0: Int, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadTestcancel()")
private external fun wasmRustlangLibcWasiPthreadTestcancel()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadOnce(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadOnce(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadKeyCreate(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadKeyCreate(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadKeyDelete(p0)")
private external fun wasmRustlangLibcWasiPthreadKeyDelete(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadGetspecific(p0)")
private external fun wasmRustlangLibcWasiPthreadGetspecific(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSetspecific(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadSetspecific(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexInit(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexInit(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadMutexDestroy(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexLock(p0)")
private external fun wasmRustlangLibcWasiPthreadMutexLock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexTrylock(p0)")
private external fun wasmRustlangLibcWasiPthreadMutexTrylock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexUnlock(p0)")
private external fun wasmRustlangLibcWasiPthreadMutexUnlock(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexTimedlock(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexTimedlock(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexConsistent(p0)")
private external fun wasmRustlangLibcWasiPthreadMutexConsistent(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexGetprioceiling(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexGetprioceiling(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrInit(p0)")
private external fun wasmRustlangLibcWasiPthreadMutexattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadMutexattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrSettype(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexattrSettype(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrGetprotocol(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexattrGetprotocol(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrGetpshared(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrGetrobust(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexattrGetrobust(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrGettype(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexattrGettype(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrSetprotocol(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexattrSetprotocol(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrSetpshared(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadMutexattrSetrobust(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadMutexattrSetrobust(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondInit(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadCondInit(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondWait(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadCondWait(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondTimedwait(p0, p1, p2)")
private external fun wasmRustlangLibcWasiPthreadCondTimedwait(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondSignal(p0)")
private external fun wasmRustlangLibcWasiPthreadCondSignal(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondBroadcast(p0)")
private external fun wasmRustlangLibcWasiPthreadCondBroadcast(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadCondDestroy(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondattrInit(p0)")
private external fun wasmRustlangLibcWasiPthreadCondattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondattrDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadCondattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondattrGetclock(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadCondattrGetclock(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondattrSetpshared(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadCondattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadCondattrGetpshared(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadCondattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockInit(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadRwlockInit(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadRwlockDestroy(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockRdlock(p0)")
private external fun wasmRustlangLibcWasiPthreadRwlockRdlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockTryrdlock(p0)")
private external fun wasmRustlangLibcWasiPthreadRwlockTryrdlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockWrlock(p0)")
private external fun wasmRustlangLibcWasiPthreadRwlockWrlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockTrywrlock(p0)")
private external fun wasmRustlangLibcWasiPthreadRwlockTrywrlock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockUnlock(p0)")
private external fun wasmRustlangLibcWasiPthreadRwlockUnlock(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockTimedrdlock(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadRwlockTimedrdlock(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockTimedwrlock(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadRwlockTimedwrlock(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockattrInit(p0)")
private external fun wasmRustlangLibcWasiPthreadRwlockattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockattrDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadRwlockattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockattrSetpshared(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadRwlockattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadRwlockattrGetpshared(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadRwlockattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSpinInit(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadSpinInit(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSpinDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadSpinDestroy(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSpinLock(p0)")
private external fun wasmRustlangLibcWasiPthreadSpinLock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSpinTrylock(p0)")
private external fun wasmRustlangLibcWasiPthreadSpinTrylock(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadSpinUnlock(p0)")
private external fun wasmRustlangLibcWasiPthreadSpinUnlock(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadBarrierInit(p0, p1, p2)")
private external fun wasmRustlangLibcWasiPthreadBarrierInit(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadBarrierDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadBarrierDestroy(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadBarrierWait(p0)")
private external fun wasmRustlangLibcWasiPthreadBarrierWait(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadBarrierattrInit(p0)")
private external fun wasmRustlangLibcWasiPthreadBarrierattrInit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadBarrierattrDestroy(p0)")
private external fun wasmRustlangLibcWasiPthreadBarrierattrDestroy(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadBarrierattrSetpshared(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadBarrierattrSetpshared(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadBarrierattrGetpshared(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadBarrierattrGetpshared(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWasiPthreadGetattrNp(p0, p1)")
private external fun wasmRustlangLibcWasiPthreadGetattrNp(p0: String, p1: String): Int

public actual var ENVIRON: MutPtr
    get() = if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiGetENVIRON().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")
    set(value) {
        if (libcWasiNapiAvailable) {
            wasmRustlangLibcWasiSetENVIRON(value.rawAddress.toString())
        } else {
            throw UnsupportedOperationException("Native FFI addon is not available")
        }
    }

public actual val CLOCK_MONOTONIC1: UByte
    get() = if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetCLOCKMONOTONIC1().toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual val CLOCK_REALTIME1: UByte
    get() = if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetCLOCKREALTIME1().toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exit(code: CInt): Nothing {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiExit(code)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun abort(): Nothing {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun alignedAlloc(a: SizeT, b: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiAlignedAlloc(a.toString(), b.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(amt: SizeT, amt2: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiCalloc(amt.toString(), amt2.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(ptr: MutPtr) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiFree(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getenv(s: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiGetenv(s.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(amt: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiMalloc(amt.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiMallocUsableSize(ptr.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sbrk(increment: IntptrT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiSbrk(increment.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRand() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun read(fd: CInt, ptr: MutPtr, size: SizeT): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRead(fd, ptr.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(ptr: MutPtr, amt: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiRealloc(ptr.rawAddress.toString(), amt.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setenv(k: ConstPtr, v: ConstPtr, a: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSetenv(k.rawAddress.toString(), v.rawAddress.toString(), a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unsetenv(k: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiUnsetenv(k.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clearenv(): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiClearenv() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun write(fd: CInt, ptr: ConstPtr, size: SizeT): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWrite(fd, ptr.rawAddress.toString(), size.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fopen(a: ConstPtr, b: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiFopen(a.rawAddress.toString(), b.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freopen(a: ConstPtr, b: ConstPtr, f: MutPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiFreopen(a.rawAddress.toString(), b.rawAddress.toString(), f.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFclose(f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remove(a: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRemove(a.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rename(a: ConstPtr, b: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRename(a.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun feof(f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFeof(f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ferror(f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFerror(f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFflush(f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clearerr(f: MutPtr) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiClearerr(f.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fseek(f: MutPtr, b: CLong, c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFseek(f.rawAddress.toString(), b.toString(), c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(f: MutPtr): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFtell(f.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewind(f: MutPtr) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiRewind(f.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgetpos(f: MutPtr, pos: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFgetpos(f.rawAddress.toString(), pos.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetpos(f: MutPtr, pos: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFsetpos(f.rawAddress.toString(), pos.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(buf: MutPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFread(buf.rawAddress.toString(), a.toString(), b.toString(), f.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(buf: ConstPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFwrite(buf.rawAddress.toString(), a.toString(), b.toString(), f.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetc(f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFgetc(f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getc(f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetc(f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getchar(): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetchar() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ungetc(a: CInt, f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiUngetc(a, f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(a: CInt, f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFputc(a, f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putc(a: CInt, f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPutc(a, f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putchar(a: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPutchar(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(a: ConstPtr, f: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFputs(a.rawAddress.toString(), f.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun puts(a: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPuts(a.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun perror(a: ConstPtr) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiPerror(a.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun srand(a: CUint) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiSrand(a.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun atexit(a: AtexitACallback): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiAtexit(0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atQuickExit(a: AtexitACallback): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiAtQuickExit(0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun quickExit(a: CInt): Nothing {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiQuickExit(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function quickExit")
}

public actual fun posixMemalign(a: MutPtr, b: SizeT, c: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPosixMemalign(a.rawAddress.toString(), b.toString(), c.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun randR(a: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRandR(a.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun random(): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRandom().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srandom(a: CUint) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiSrandom(a.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun putenv(a: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPutenv(a.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clock(): ClockT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiClock().toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun time(a: MutPtr): TimeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiTime(a.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun difftime(a: TimeT, b: TimeT): CDouble =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiDifftime(a.toString(), b.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mktime(a: MutPtr): TimeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiMktime(a.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strftime(a: MutPtr, b: SizeT, c: ConstPtr, d: ConstPtr): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrftime(a.rawAddress.toString(), b.toString(), c.rawAddress.toString(), d.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmtime(a: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiGmtime(a.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gmtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiGmtimeR(a.rawAddress.toString(), b.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localtime(a: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiLocaltime(a.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiLocaltimeR(a.rawAddress.toString(), b.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun asctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiAsctimeR(a.rawAddress.toString(), b.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiCtimeR(a.rawAddress.toString(), b.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nanosleep(a: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiNanosleep(a.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGetres(a: ClockidT, b: MutPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockGettime(a: ClockidT, b: MutPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clockNanosleep(a: ClockidT, a2: CInt, b: ConstPtr, c: MutPtr): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalnum(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsalnum(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalpha(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsalpha(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iscntrl(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIscntrl(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isdigit(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsdigit(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isgraph(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsgraph(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun islower(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIslower(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isprint(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsprint(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ispunct(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIspunct(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isspace(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsspace(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isupper(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsupper(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isxdigit(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsxdigit(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isblank(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsblank(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tolower(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiTolower(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun toupper(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiToupper(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSetvbuf(stream.rawAddress.toString(), buffer.rawAddress.toString(), mode, size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiSetbuf(stream.rawAddress.toString(), buf.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiFgets(buf.rawAddress.toString(), n, stream.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atof(s: ConstPtr): CDouble =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiAtof(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoi(s: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiAtoi(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atol(s: ConstPtr): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiAtol(s.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoll(s: ConstPtr): CLonglong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiAtoll(s.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrtod(s.rawAddress.toString(), endp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrtof(s.rawAddress.toString(), endp.rawAddress.toString()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrtol(s.rawAddress.toString(), endp.rawAddress.toString(), base).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrtoll(s.rawAddress.toString(), endp.rawAddress.toString(), base).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrtoul(s.rawAddress.toString(), endp.rawAddress.toString(), base).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrtoull(s.rawAddress.toString(), endp.rawAddress.toString(), base).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrcpy(dst.rawAddress.toString(), src.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrcat(s.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrncat(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrcmp(cs.rawAddress.toString(), ct.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrncmp(cs.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrcoll(cs.rawAddress.toString(), ct.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrchr(cs.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrrchr(cs.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrspn(cs.rawAddress.toString(), ct.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrcspn(cs.rawAddress.toString(), ct.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strdup(cs: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrdup(cs.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrndup(cs.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrpbrk(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrstr(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrcasecmp(s1.rawAddress.toString(), s2.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrncasecmp(s1.rawAddress.toString(), s2.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(cs: ConstPtr): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrlen(cs.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrnlen(cs.rawAddress.toString(), maxlen.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerror(n: CInt): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrerror(n).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrtok(s.rawAddress.toString(), t.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrxfrm(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiMemchr(cx.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiMemcmp(cx.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiMemset(dest.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFprintf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSnprintf(s.rawAddress.toString(), n.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSprintf(s.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFscanf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiScanf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSscanf(s.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcharUnlocked(): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetcharUnlocked() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putcharUnlocked(c: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPutcharUnlocked(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiShutdown(socket, how) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFstat(fildes, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiMkdir(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStat(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiFdopen(fd, mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFileno(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiOpen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiCreat(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFcntl(fd, cmd, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun opendir(dirname: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiOpendir(dirname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopendir(fd: CInt): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiFdopendir(fd).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readdir(dirp: MutPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiReaddir(dirp.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closedir(dirp: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiClosedir(dirp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewinddir(dirp: MutPtr) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiRewinddir(dirp.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun dirfd(dirp: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiDirfd(dirp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiSeekdir(dirp.rawAddress.toString(), loc.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun telldir(dirp: MutPtr): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiTelldir(dirp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiOpenat(dirfd, pathname.rawAddress.toString(), flags, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFstatat(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiLinkat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiMkdirat(dirfd, pathname.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiReadlinkat(dirfd, pathname.rawAddress.toString(), buf.rawAddress.toString(), bufsiz.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRenameat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSymlinkat(target.rawAddress.toString(), newdirfd, linkpath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiUnlinkat(dirfd, pathname.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiAccess(path.rawAddress.toString(), amode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun close(fd: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiClose(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFpathconf(filedes, name).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetopt(argc, argv.rawAddress.toString(), optstr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIsatty(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiLink(src.rawAddress.toString(), dst.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiLseek(fd, offset.toString(), whence).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPathconf(path.rawAddress.toString(), name).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rmdir(path: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRmdir(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleep(secs: CUint): CUint =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSleep(secs.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlink(c: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiUnlink(c.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPread(fd, buf.rawAddress.toString(), count.toString(), offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPwrite(fd, buf.rawAddress.toString(), count.toString(), offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiLstat(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsync(fd: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFsync(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdatasync(fd: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFdatasync(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSymlink(path1.rawAddress.toString(), path2.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiTruncate(path.rawAddress.toString(), length.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFtruncate(fd, length.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetrusage(resource, usage.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiGettimeofday(tp.rawAddress.toString(), tz.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun times(buf: MutPtr): io.github.kotlinmania.rustlang.wasi.ClockT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiTimes(buf.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiStrerrorR(errnum, buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun usleep(secs: CUint): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiUsleep(secs.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSend(socket, buf.rawAddress.toString(), len.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiRecv(socket, buf.rawAddress.toString(), len.toString(), flags).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPoll(fds.rawAddress.toString(), nfds.toString(), timeout) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiSetlocale(category, locale.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localeconv(): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiLocaleconv().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiReadlink(path.rawAddress.toString(), buf.rawAddress.toString(), bufsz.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun timegm(tm: MutPtr): TimeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiTimegm(tm.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sysconf(name: CInt): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSysconf(name).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioctl(fd: CInt, request: CInt, vararg variadicArgs: Long): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiIoctl(fd, request, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFseeko(stream.rawAddress.toString(), offset.toString(), whence) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftello(stream: MutPtr): OffT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFtello(stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPosixFallocate(fd, offset.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiStrcasestr(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetline(lineptr.rawAddress.toString(), n.rawAddress.toString(), stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFaccessat(dirfd, pathname.rawAddress.toString(), mode, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWritev(fd, iov.rawAddress.toString(), iovcnt).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiReadv(fd, iov.rawAddress.toString(), iovcnt).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPwritev(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPreadv(fd, iov.rawAddress.toString(), iovcnt, offset.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPosixFadvise(fd, offset.toString(), len.toString(), advise) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFutimens(fd, times.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flag) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiGetentropy(buf.rawAddress.toString(), buflen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiMemrchr(cx.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun abs(i: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiAbs(i) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun labs(i: CLong): CLong =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiLabs(i.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplocale(base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiDuplocale(base.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freelocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiFreelocale(loc.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiNewlocale(mask, locale.rawAddress.toString(), base.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun uselocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiUselocale(loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun schedYield(): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSchedYield() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiGetcwd(buf.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(dir: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiChdir(dir.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nlLanginfo(item: io.github.kotlinmania.rustlang.wasi.NlItem): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiNlLanginfo(item).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nlLanginfoL(item: io.github.kotlinmania.rustlang.wasi.NlItem, loc: io.github.kotlinmania.rustlang.wasi.LocaleT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiNlLanginfoL(item, loc.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiSelect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), errorfds.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcRegisterPreopenedFd(fd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcRegisterPreopenedFd(fd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcFdRenumber(fd: CInt, newfd: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcFdRenumber(fd, newfd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcUnlinkat(fd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcUnlinkat(fd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcRmdirat(fd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcRmdirat(fd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcFindRelpath(path: ConstPtr, absPrefix: MutPtr, relativePath: MutPtr, relativePathLen: ULong): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcFindRelpath(path.rawAddress.toString(), absPrefix.rawAddress.toString(), relativePath.rawAddress.toString(), relativePathLen.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcTell(fd: CInt): OffT =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcTell(fd).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdWasilibcUnlinkat(dirfd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdWasilibcUnlinkat(dirfd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdWasilibcRmdirat(dirfd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdWasilibcRmdirat(dirfd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdLinkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdLinkat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdSymlinkat(target: ConstPtr, dirfd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdSymlinkat(target.rawAddress.toString(), dirfd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdReadlinkat(dirfd: CInt, path: ConstPtr, buf: MutPtr, bufsize: ULong): Long =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdReadlinkat(dirfd, path.rawAddress.toString(), buf.rawAddress.toString(), bufsize.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdFaccessat(dirfd: CInt, path: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdFaccessat(dirfd, path.rawAddress.toString(), mode, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdRenameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdRenameat(olddirfd, oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdOpenatNomode(dirfd: CInt, path: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdOpenatNomode(dirfd, path.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdFstatat(dirfd: CInt, path: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdFstatat(dirfd, path.rawAddress.toString(), buf.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdMkdiratNomode(dirfd: CInt, path: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdMkdiratNomode(dirfd, path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdUtimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcNocwdUtimensat(dirfd, path.rawAddress.toString(), times.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcNocwdOpendirat(dirfd: CInt, path: ConstPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiWasilibcNocwdOpendirat(dirfd, path.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcAccess(pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcAccess(pathname.rawAddress.toString(), mode, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcStat(pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcStat(pathname.rawAddress.toString(), buf.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcUtimens(pathname: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcUtimens(pathname.rawAddress.toString(), times.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcLink(oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcLink(oldpath.rawAddress.toString(), newpath.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcLinkOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcLinkOldat(olddirfd, oldpath.rawAddress.toString(), newpath.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcLinkNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcLinkNewat(oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcRenameOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcRenameOldat(olddirfd, oldpath.rawAddress.toString(), newpath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wasilibcRenameNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiWasilibcRenameNewat(oldpath.rawAddress.toString(), newdirfd, newpath.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4random(): UInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiArc4random().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun arc4randomBuf(a: MutPtr, b: SizeT) {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiArc4randomBuf(a.rawAddress.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun arc4randomUniform(a: UInt): UInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiArc4randomUniform(a.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun errnoLocation(): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiErrnoLocation().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiChmod(path.rawAddress.toString(), mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiFchmod(fd, mode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiRealpath(pathname.rawAddress.toString(), resolved.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSelf(): PthreadT =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiPthreadSelf().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCreate(native.rawAddress.toString(), attr.rawAddress.toString(), 0L.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadEqual(t1.rawAddress.toString(), t2.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadJoin(native.rawAddress.toString(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrGetstacksize(attr.rawAddress.toString(), stacksize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrSetstacksize(attr.rawAddress.toString(), stackSize.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, state: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrGetdetachstate(attr.rawAddress.toString(), state.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrSetdetachstate(attr.rawAddress.toString(), state) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrGetguardsize(attr.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetguardsize(attr: MutPtr, b: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrSetguardsize(attr.rawAddress.toString(), b.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrGetstack(attr: ConstPtr, b: MutPtr, c: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrGetstack(attr.rawAddress.toString(), b.rawAddress.toString(), c.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadAttrSetstack(attr: MutPtr, b: MutPtr, c: SizeT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadAttrSetstack(attr.rawAddress.toString(), b.rawAddress.toString(), c.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadDetach(thread: PthreadT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadDetach(thread.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetcancelstate(a: CInt, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadSetcancelstate(a, b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetcanceltype(a: CInt, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadSetcanceltype(a, b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadTestcancel() {
    if (libcWasiNapiAvailable) {
        wasmRustlangLibcWasiPthreadTestcancel()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun pthreadOnce(a: MutPtr, b: AtexitACallback): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadOnce(a.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadKeyCreate(key.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadKeyDelete(key.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    if (libcWasiNapiAvailable) MutPtr(wasmRustlangLibcWasiPthreadGetspecific(key.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadSetspecific(key.toInt(), value.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexInit(lock.rawAddress.toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexDestroy(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexLock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexTrylock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexUnlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexTimedlock(lock: MutPtr, ts: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexTimedlock(lock.rawAddress.toString(), ts.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexConsistent(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexConsistent(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexGetprioceiling(lock: MutPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexGetprioceiling(lock.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrSettype(attr.rawAddress.toString(), type) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrGetprotocol(attr.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrGetpshared(attr.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrGetrobust(attr.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrGettype(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrGettype(attr.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetprotocol(attr: ConstPtr, b: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrSetprotocol(attr.rawAddress.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetpshared(attr: ConstPtr, b: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrSetpshared(attr.rawAddress.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadMutexattrSetrobust(attr: ConstPtr, b: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadMutexattrSetrobust(attr.rawAddress.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondInit(cond.rawAddress.toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondWait(cond.rawAddress.toString(), lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondTimedwait(cond.rawAddress.toString(), lock.rawAddress.toString(), abstime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondSignal(cond.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondBroadcast(cond.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondDestroy(cond.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondattrGetclock(attr.rawAddress.toString(), clockId.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrSetpshared(attr: MutPtr, b: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondattrSetpshared(attr.rawAddress.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadCondattrGetpshared(attr.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockInit(lock.rawAddress.toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockDestroy(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockRdlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockTryrdlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockWrlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockTrywrlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockUnlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTimedrdlock(lock: MutPtr, at: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockTimedrdlock(lock.rawAddress.toString(), at.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockTimedwrlock(lock: MutPtr, at: ConstPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockTimedwrlock(lock.rawAddress.toString(), at.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, b: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockattrSetpshared(attr.rawAddress.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadRwlockattrGetpshared(attr.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinInit(lock: MutPtr, b: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadSpinInit(lock.rawAddress.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadSpinDestroy(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadSpinLock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadSpinTrylock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadSpinUnlock(lock.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadBarrierInit(barrier.rawAddress.toString(), attr.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadBarrierDestroy(barrier.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadBarrierWait(barrier.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadBarrierattrInit(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadBarrierattrDestroy(attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, b: CInt): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadBarrierattrSetpshared(attr.rawAddress.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadBarrierattrGetpshared(attr.rawAddress.toString(), b.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pthreadGetattrNp(thread: PthreadT, attr: MutPtr): CInt =
    if (libcWasiNapiAvailable) wasmRustlangLibcWasiPthreadGetattrNp(thread.rawAddress.toString(), attr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
