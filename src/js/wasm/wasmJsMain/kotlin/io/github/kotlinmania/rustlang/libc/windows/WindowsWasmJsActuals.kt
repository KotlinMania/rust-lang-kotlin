@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.windows

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
private external fun initLibcWindowsNapiAddon(): Boolean

private val libcWindowsNapiAvailable: Boolean = initLibcWindowsNapiAddon()

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPrintf(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcWindowsPrintf(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFprintf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsFprintf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSnprintf(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangLibcWindowsSnprintf(p0: String, p1: String, p2: String, p3: Int, p4: String, p5: String, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSprintf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsSprintf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcWindowsScanf(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcWindowsScanf(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSscanf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsSscanf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFscanf(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsFscanf(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsalnum(p0)")
private external fun wasmRustlangLibcWindowsIsalnum(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsalpha(p0)")
private external fun wasmRustlangLibcWindowsIsalpha(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIscntrl(p0)")
private external fun wasmRustlangLibcWindowsIscntrl(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsdigit(p0)")
private external fun wasmRustlangLibcWindowsIsdigit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsgraph(p0)")
private external fun wasmRustlangLibcWindowsIsgraph(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIslower(p0)")
private external fun wasmRustlangLibcWindowsIslower(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsprint(p0)")
private external fun wasmRustlangLibcWindowsIsprint(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIspunct(p0)")
private external fun wasmRustlangLibcWindowsIspunct(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsspace(p0)")
private external fun wasmRustlangLibcWindowsIsspace(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsupper(p0)")
private external fun wasmRustlangLibcWindowsIsupper(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsxdigit(p0)")
private external fun wasmRustlangLibcWindowsIsxdigit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsblank(p0)")
private external fun wasmRustlangLibcWindowsIsblank(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsTolower(p0)")
private external fun wasmRustlangLibcWindowsTolower(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsToupper(p0)")
private external fun wasmRustlangLibcWindowsToupper(p0: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWindowsQsort(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWindowsQsort(p0: String, p1: String, p2: String, p3: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcWindowsQsortS(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcWindowsQsortS(p0: String, p1: String, p2: String, p3: String, p4: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFopen(p0, p1)")
private external fun wasmRustlangLibcWindowsFopen(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFreopen(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsFreopen(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFflush(p0)")
private external fun wasmRustlangLibcWindowsFflush(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFclose(p0)")
private external fun wasmRustlangLibcWindowsFclose(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsRemove(p0)")
private external fun wasmRustlangLibcWindowsRemove(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsRename(p0, p1)")
private external fun wasmRustlangLibcWindowsRename(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWindowsTmpfile()")
private external fun wasmRustlangLibcWindowsTmpfile(): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSetvbuf(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWindowsSetvbuf(p0: String, p1: String, p2: Int, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSetbuf(p0, p1)")
private external fun wasmRustlangLibcWindowsSetbuf(p0: String, p1: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetchar()")
private external fun wasmRustlangLibcWindowsGetchar(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPutchar(p0)")
private external fun wasmRustlangLibcWindowsPutchar(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFgetc(p0)")
private external fun wasmRustlangLibcWindowsFgetc(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFgets(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsFgets(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFputc(p0, p1)")
private external fun wasmRustlangLibcWindowsFputc(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFputs(p0, p1)")
private external fun wasmRustlangLibcWindowsFputs(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPuts(p0)")
private external fun wasmRustlangLibcWindowsPuts(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsUngetc(p0, p1)")
private external fun wasmRustlangLibcWindowsUngetc(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFread(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWindowsFread(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFwrite(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWindowsFwrite(p0: String, p1: String, p2: String, p3: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFseek(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsFseek(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFtell(p0)")
private external fun wasmRustlangLibcWindowsFtell(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsRewind(p0)")
private external fun wasmRustlangLibcWindowsRewind(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFgetpos(p0, p1)")
private external fun wasmRustlangLibcWindowsFgetpos(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFsetpos(p0, p1)")
private external fun wasmRustlangLibcWindowsFsetpos(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFeof(p0)")
private external fun wasmRustlangLibcWindowsFeof(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFerror(p0)")
private external fun wasmRustlangLibcWindowsFerror(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPerror(p0)")
private external fun wasmRustlangLibcWindowsPerror(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAtof(p0)")
private external fun wasmRustlangLibcWindowsAtof(p0: String): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAtoi(p0)")
private external fun wasmRustlangLibcWindowsAtoi(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAtol(p0)")
private external fun wasmRustlangLibcWindowsAtol(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAtoll(p0)")
private external fun wasmRustlangLibcWindowsAtoll(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrtod(p0, p1)")
private external fun wasmRustlangLibcWindowsStrtod(p0: String, p1: String): Double

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrtof(p0, p1)")
private external fun wasmRustlangLibcWindowsStrtof(p0: String, p1: String): Double

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrtol(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsStrtol(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrtoll(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsStrtoll(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrtoul(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsStrtoul(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrtoull(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsStrtoull(p0: String, p1: String, p2: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsCalloc(p0, p1)")
private external fun wasmRustlangLibcWindowsCalloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsMalloc(p0)")
private external fun wasmRustlangLibcWindowsMalloc(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsMsize(p0)")
private external fun wasmRustlangLibcWindowsMsize(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsRealloc(p0, p1)")
private external fun wasmRustlangLibcWindowsRealloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFree(p0)")
private external fun wasmRustlangLibcWindowsFree(p0: String)

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWindowsAbort()")
private external fun wasmRustlangLibcWindowsAbort()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExit(p0)")
private external fun wasmRustlangLibcWindowsExit(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAtexit(p0)")
private external fun wasmRustlangLibcWindowsAtexit(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSystem(p0)")
private external fun wasmRustlangLibcWindowsSystem(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetenv(p0)")
private external fun wasmRustlangLibcWindowsGetenv(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrcpy(p0, p1)")
private external fun wasmRustlangLibcWindowsStrcpy(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrncpy(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsStrncpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrcat(p0, p1)")
private external fun wasmRustlangLibcWindowsStrcat(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrncat(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsStrncat(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrcmp(p0, p1)")
private external fun wasmRustlangLibcWindowsStrcmp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrncmp(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsStrncmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrcoll(p0, p1)")
private external fun wasmRustlangLibcWindowsStrcoll(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrchr(p0, p1)")
private external fun wasmRustlangLibcWindowsStrchr(p0: String, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrrchr(p0, p1)")
private external fun wasmRustlangLibcWindowsStrrchr(p0: String, p1: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrspn(p0, p1)")
private external fun wasmRustlangLibcWindowsStrspn(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrcspn(p0, p1)")
private external fun wasmRustlangLibcWindowsStrcspn(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrdup(p0)")
private external fun wasmRustlangLibcWindowsStrdup(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrpbrk(p0, p1)")
private external fun wasmRustlangLibcWindowsStrpbrk(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrstr(p0, p1)")
private external fun wasmRustlangLibcWindowsStrstr(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrlen(p0)")
private external fun wasmRustlangLibcWindowsStrlen(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrnlen(p0, p1)")
private external fun wasmRustlangLibcWindowsStrnlen(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrerror(p0)")
private external fun wasmRustlangLibcWindowsStrerror(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrtok(p0, p1)")
private external fun wasmRustlangLibcWindowsStrtok(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStrxfrm(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsStrxfrm(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWcslen(p0)")
private external fun wasmRustlangLibcWindowsWcslen(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWcsnlen(p0, p1)")
private external fun wasmRustlangLibcWindowsWcsnlen(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWcstombs(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsWcstombs(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsMemchr(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsMemchr(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsMemcmp(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsMemcmp(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsMemcpy(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsMemcpy(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsMemmove(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsMemmove(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsMemset(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsMemset(p0: String, p1: Int, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAbs(p0)")
private external fun wasmRustlangLibcWindowsAbs(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsLabs(p0)")
private external fun wasmRustlangLibcWindowsLabs(p0: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWindowsRand()")
private external fun wasmRustlangLibcWindowsRand(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSrand(p0)")
private external fun wasmRustlangLibcWindowsSrand(p0: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSignal(p0, p1)")
private external fun wasmRustlangLibcWindowsSignal(p0: Int, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsRaise(p0)")
private external fun wasmRustlangLibcWindowsRaise(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWindowsClock()")
private external fun wasmRustlangLibcWindowsClock(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetDaylight(p0)")
private external fun wasmRustlangLibcWindowsGetDaylight(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetDstbias(p0)")
private external fun wasmRustlangLibcWindowsGetDstbias(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetTimezone(p0)")
private external fun wasmRustlangLibcWindowsGetTimezone(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetTzname(p0, p1, p2, p3)")
private external fun wasmRustlangLibcWindowsGetTzname(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWindowsTzset()")
private external fun wasmRustlangLibcWindowsTzset()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsChmod(p0, p1)")
private external fun wasmRustlangLibcWindowsChmod(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWchmod(p0, p1)")
private external fun wasmRustlangLibcWindowsWchmod(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsMkdir(p0)")
private external fun wasmRustlangLibcWindowsMkdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWrmdir(p0)")
private external fun wasmRustlangLibcWindowsWrmdir(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFstat(p0, p1)")
private external fun wasmRustlangLibcWindowsFstat(p0: Int, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsStat(p0, p1)")
private external fun wasmRustlangLibcWindowsStat(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWstat(p0, p1)")
private external fun wasmRustlangLibcWindowsWstat(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWutime(p0, p1)")
private external fun wasmRustlangLibcWindowsWutime(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPopen(p0, p1)")
private external fun wasmRustlangLibcWindowsPopen(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPclose(p0)")
private external fun wasmRustlangLibcWindowsPclose(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFdopen(p0, p1)")
private external fun wasmRustlangLibcWindowsFdopen(p0: Int, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsFileno(p0)")
private external fun wasmRustlangLibcWindowsFileno(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsOpen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsOpen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWopen(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsWopen(p0: String, p1: Int, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsCreat(p0, p1)")
private external fun wasmRustlangLibcWindowsCreat(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAccess(p0, p1)")
private external fun wasmRustlangLibcWindowsAccess(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsChdir(p0)")
private external fun wasmRustlangLibcWindowsChdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsClose(p0)")
private external fun wasmRustlangLibcWindowsClose(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsDup(p0)")
private external fun wasmRustlangLibcWindowsDup(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsDup2(p0, p1)")
private external fun wasmRustlangLibcWindowsDup2(p0: Int, p1: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExecl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsExecl(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWexecl(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsWexecl(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExecle(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsExecle(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWexecle(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsWexecle(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExeclp(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsExeclp(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWexeclp(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsWexeclp(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExeclpe(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsExeclpe(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWexeclpe(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangLibcWindowsWexeclpe(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExecv(p0, p1)")
private external fun wasmRustlangLibcWindowsExecv(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExecve(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsExecve(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExecvp(p0, p1)")
private external fun wasmRustlangLibcWindowsExecvp(p0: String, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsExecvpe(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsExecvpe(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWexecv(p0, p1)")
private external fun wasmRustlangLibcWindowsWexecv(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWexecve(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsWexecve(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWexecvp(p0, p1)")
private external fun wasmRustlangLibcWindowsWexecvp(p0: String, p1: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWexecvpe(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsWexecvpe(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetcwd(p0, p1)")
private external fun wasmRustlangLibcWindowsGetcwd(p0: String, p1: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetpid()")
private external fun wasmRustlangLibcWindowsGetpid(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsIsatty(p0)")
private external fun wasmRustlangLibcWindowsIsatty(p0: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsLseek(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsLseek(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsLseek64(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsLseek64(p0: Int, p1: String, p2: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPipe(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsPipe(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsRead(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsRead(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsRmdir(p0)")
private external fun wasmRustlangLibcWindowsRmdir(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsUnlink(p0)")
private external fun wasmRustlangLibcWindowsUnlink(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWrite(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsWrite(p0: Int, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsCommit(p0)")
private external fun wasmRustlangLibcWindowsCommit(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetOsfhandle(p0)")
private external fun wasmRustlangLibcWindowsGetOsfhandle(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsOpenOsfhandle(p0, p1)")
private external fun wasmRustlangLibcWindowsOpenOsfhandle(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSetlocale(p0, p1)")
private external fun wasmRustlangLibcWindowsSetlocale(p0: Int, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWsetlocale(p0, p1)")
private external fun wasmRustlangLibcWindowsWsetlocale(p0: Int, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAlignedMalloc(p0, p1)")
private external fun wasmRustlangLibcWindowsAlignedMalloc(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAlignedFree(p0)")
private external fun wasmRustlangLibcWindowsAlignedFree(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAlignedRealloc(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsAlignedRealloc(p0: String, p1: String, p2: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPutenv(p0)")
private external fun wasmRustlangLibcWindowsPutenv(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWputenv(p0)")
private external fun wasmRustlangLibcWindowsWputenv(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsPutenvS(p0, p1)")
private external fun wasmRustlangLibcWindowsPutenvS(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsWputenvS(p0, p1)")
private external fun wasmRustlangLibcWindowsWputenvS(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangLibcWindowsListen(p0, p1)")
private external fun wasmRustlangLibcWindowsListen(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsAccept(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsAccept(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsBind(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsBind(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsConnect(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsConnect(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetpeername(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsGetpeername(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetsockname(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsGetsockname(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcWindowsGetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcWindowsGetsockopt(p0: String, p1: Int, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcWindowsRecvfrom(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcWindowsRecvfrom(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSendto(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangLibcWindowsSendto(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmRustlangLibcWindowsSetsockopt(p0: String, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangLibcWindowsSocket(p0, p1, p2)")
private external fun wasmRustlangLibcWindowsSocket(p0: Int, p1: Int, p2: Int): String

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsPrintf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFprintf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun snprintf(buffer: MutPtr, count: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSnprintf(buffer.rawAddress.toString(), count.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sprintf(buffer: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSprintf(buffer.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsScanf(format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sscanf(buffer: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSscanf(buffer.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFscanf(stream.rawAddress.toString(), format.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalnum(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsalnum(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isalpha(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsalpha(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iscntrl(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIscntrl(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isdigit(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsdigit(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isgraph(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsgraph(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun islower(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIslower(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isprint(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsprint(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ispunct(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIspunct(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isspace(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsspace(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isupper(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsupper(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isxdigit(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsxdigit(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isblank(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsblank(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tolower(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsTolower(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun toupper(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsToupper(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsQsort(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun qsortS(base: MutPtr, num: SizeT, size: SizeT, compar: QsortSComparCallback?, arg: MutPtr) {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsQsortS(base.rawAddress.toString(), num.toString(), size.toString(), 0L.toString(), arg.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsFopen(filename.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsFreopen(filename.rawAddress.toString(), mode.rawAddress.toString(), file.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fflush(file: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFflush(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fclose(file: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFclose(file.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun remove(filename: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsRemove(filename.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsRename(oldname.rawAddress.toString(), newname.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tmpfile(): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsTmpfile().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSetvbuf(stream.rawAddress.toString(), buffer.rawAddress.toString(), mode, size.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsSetbuf(stream.rawAddress.toString(), buf.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getchar(): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetchar() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putchar(c: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsPutchar(c) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgetc(stream: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFgetc(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsFgets(buf.rawAddress.toString(), n, stream.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFputc(c, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFputs(s.rawAddress.toString(), stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun puts(s: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsPuts(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsUngetc(c, stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFread(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFwrite(ptr.rawAddress.toString(), size.toString(), nobj.toString(), stream.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFseek(stream.rawAddress.toString(), offset.toString(), whence) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ftell(stream: MutPtr): CLong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFtell(stream.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rewind(stream: MutPtr) {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsRewind(stream.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFgetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFsetpos(stream.rawAddress.toString(), ptr.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun feof(stream: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFeof(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ferror(stream: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFerror(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun perror(s: ConstPtr) {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsPerror(s.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun atof(s: ConstPtr): CDouble =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsAtof(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoi(s: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsAtoi(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atol(s: ConstPtr): CLong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsAtol(s.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun atoll(s: ConstPtr): CLonglong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsAtoll(s.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrtod(s.rawAddress.toString(), endp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrtof(s.rawAddress.toString(), endp.rawAddress.toString()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrtol(s.rawAddress.toString(), endp.rawAddress.toString(), base).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrtoll(s.rawAddress.toString(), endp.rawAddress.toString(), base).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrtoul(s.rawAddress.toString(), endp.rawAddress.toString(), base).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrtoull(s.rawAddress.toString(), endp.rawAddress.toString(), base).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsCalloc(nobj.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun malloc(size: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsMalloc(size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msize(p: MutPtr): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsMsize(p.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsRealloc(p.rawAddress.toString(), size.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun free(p: MutPtr) {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsFree(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun abort(): Nothing {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsAbort()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsExit(status)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exit")
}

public actual fun atexit(cb: AtexitCbCallback): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsAtexit(0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun system(s: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSystem(s.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getenv(s: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsGetenv(s.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrcpy(dst.rawAddress.toString(), src.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrncpy(dst.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrcat(s.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrncat(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrcmp(cs.rawAddress.toString(), ct.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrncmp(cs.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrcoll(cs.rawAddress.toString(), ct.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrchr(cs.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrrchr(cs.rawAddress.toString(), c).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrspn(cs.rawAddress.toString(), ct.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrcspn(cs.rawAddress.toString(), ct.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strdup(cs: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrdup(cs.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrpbrk(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrstr(cs.rawAddress.toString(), ct.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strlen(cs: ConstPtr): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrlen(cs.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrnlen(cs.rawAddress.toString(), maxlen.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strerror(n: CInt): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrerror(n).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsStrtok(s.rawAddress.toString(), t.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStrxfrm(s.rawAddress.toString(), ct.rawAddress.toString(), n.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcslen(buf: ConstPtr): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWcslen(buf.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcsnlen(str: ConstPtr, numberOfElements: SizeT): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWcsnlen(str.rawAddress.toString(), numberOfElements.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWcstombs(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsMemchr(cx.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsMemcmp(cx.rawAddress.toString(), ct.rawAddress.toString(), n.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsMemcpy(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsMemmove(dest.rawAddress.toString(), src.rawAddress.toString(), n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsMemset(dest.rawAddress.toString(), c, n.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun abs(i: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsAbs(i) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun labs(i: CLong): CLong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsLabs(i.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rand(): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsRand() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun srand(seed: CUint) {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsSrand(seed.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSignal(signum, handler.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun raise(signum: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsRaise(signum) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun clock(): ClockT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsClock() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getDaylight(hours: MutPtr): ErrnoT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetDaylight(hours.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getDstbias(seconds: MutPtr): ErrnoT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetDstbias(seconds.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTimezone(seconds: MutPtr): ErrnoT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetTimezone(seconds.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getTzname(pReturnValue: MutPtr, timeZoneName: MutPtr, sizeInBytes: SizeT, index: CInt): ErrnoT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetTzname(pReturnValue.rawAddress.toString(), timeZoneName.rawAddress.toString(), sizeInBytes.toString(), index) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tzset() {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsTzset()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun chmod(path: ConstPtr, mode: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsChmod(path.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wchmod(path: ConstPtr, mode: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWchmod(path.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mkdir(path: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsMkdir(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wrmdir(path: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWrmdir(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFstat(fildes, buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsStat(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wstat(path: ConstPtr, buf: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWstat(path.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wutime(file: ConstPtr, buf: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWutime(file.rawAddress.toString(), buf.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsPopen(command.rawAddress.toString(), mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pclose(stream: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsPclose(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsFdopen(fd, mode.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun fileno(stream: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsFileno(stream.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsOpen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wopen(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWopen(path.rawAddress.toString(), oflag, variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun creat(path: ConstPtr, mode: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsCreat(path.rawAddress.toString(), mode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsAccess(path.rawAddress.toString(), amode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun chdir(dir: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsChdir(dir.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun close(fd: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsClose(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup(fd: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsDup(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dup2(src: CInt, dst: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsDup2(src, dst) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsExecl(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWexecl(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsExecle(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWexecle(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execlp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsExeclp(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexeclp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWexeclp(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execlpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsExeclpe(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexeclpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWexeclpe(path.rawAddress.toString(), arg0.rawAddress.toString(), variadicArgs.size.coerceAtMost(4), (if (variadicArgs.size > 0) variadicArgs[0] else 0L).toString(), (if (variadicArgs.size > 1) variadicArgs[1] else 0L).toString(), (if (variadicArgs.size > 2) variadicArgs[2] else 0L).toString(), (if (variadicArgs.size > 3) variadicArgs[3] else 0L).toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsExecv(prog.rawAddress.toString(), argv.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsExecve(prog.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsExecvp(c.rawAddress.toString(), argv.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun execvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsExecvpe(c.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWexecv(prog.rawAddress.toString(), argv.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWexecve(prog.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecvp(c: ConstPtr, argv: ConstPtr): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWexecvp(c.rawAddress.toString(), argv.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wexecvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWexecvpe(c.rawAddress.toString(), argv.rawAddress.toString(), envp.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getcwd(buf: MutPtr, size: CInt): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsGetcwd(buf.rawAddress.toString(), size).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpid(): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetpid() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isatty(fd: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsIsatty(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek(fd: CInt, offset: CLong, origin: CInt): CLong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsLseek(fd, offset.toString(), origin).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lseek64(fd: CInt, offset: CLonglong, origin: CInt): CLonglong =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsLseek64(fd, offset.toString(), origin).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pipe(fds: MutPtr, psize: CUint, textmode: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsPipe(fds.rawAddress.toString(), psize.toInt(), textmode) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun read(fd: CInt, buf: MutPtr, count: CUint): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsRead(fd, buf.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rmdir(path: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsRmdir(path.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlink(c: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsUnlink(c.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun write(fd: CInt, buf: ConstPtr, count: CUint): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWrite(fd, buf.rawAddress.toString(), count.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun commit(fd: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsCommit(fd) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getOsfhandle(fd: CInt): IntptrT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetOsfhandle(fd).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openOsfhandle(osfhandle: IntptrT, flags: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsOpenOsfhandle(osfhandle.toString(), flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsSetlocale(category, locale.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wsetlocale(category: CInt, locale: ConstPtr): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsWsetlocale(category, locale.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alignedMalloc(size: SizeT, alignment: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsAlignedMalloc(size.toString(), alignment.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun alignedFree(ptr: MutPtr) {
    if (libcWindowsNapiAvailable) {
        wasmRustlangLibcWindowsAlignedFree(ptr.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun alignedRealloc(memblock: MutPtr, size: SizeT, alignment: SizeT): MutPtr =
    if (libcWindowsNapiAvailable) MutPtr(wasmRustlangLibcWindowsAlignedRealloc(memblock.rawAddress.toString(), size.toString(), alignment.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putenv(envstring: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsPutenv(envstring.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wputenv(envstring: ConstPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWputenv(envstring.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun putenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsPutenvS(envstring.rawAddress.toString(), valueString.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wputenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsWputenvS(envstring.rawAddress.toString(), valueString.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(s: Socket, backlog: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsListen(s.toString(), backlog) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsAccept(s.toString(), addr.rawAddress.toString(), addrlen.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsBind(s.toString(), name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsConnect(s.toString(), name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetpeername(s.toString(), name.rawAddress.toString(), nameln.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetsockname(s.toString(), name.rawAddress.toString(), nameln.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(s: Socket, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsGetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: CInt, flags: CInt, from: MutPtr, fromlen: MutPtr): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsRecvfrom(s.toString(), buf.rawAddress.toString(), len, flags, from.rawAddress.toString(), fromlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(s: Socket, buf: ConstPtr, len: CInt, flags: CInt, to: ConstPtr, tolen: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSendto(s.toString(), buf.rawAddress.toString(), len, flags, to.rawAddress.toString(), tolen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(s: Socket, level: CInt, optname: CInt, optval: ConstPtr, optlen: CInt): CInt =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun socket(af: CInt, socketType: CInt, protocol: CInt): Socket =
    if (libcWindowsNapiAvailable) wasmRustlangLibcWindowsSocket(af, socketType, protocol).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")
