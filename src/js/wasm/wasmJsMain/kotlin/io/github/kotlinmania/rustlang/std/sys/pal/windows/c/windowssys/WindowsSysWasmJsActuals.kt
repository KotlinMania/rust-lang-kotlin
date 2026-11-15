@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initStdSysPalWindowsCWindowssysWindowsSysNapiAddon(): Boolean

private val stdSysPalWindowsCWindowssysWindowsSysNapiAvailable: Boolean = initStdSysPalWindowsCWindowssysWindowsSysNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(p0: Int, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(p0: String, p1: Int, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(p0: String, p1: String, p2: String, p3: String, p4: String, p5: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW(p0: String, p1: Int, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW(p0: String, p1: Int, p2: Int, p3: String, p4: Int, p5: Int, p6: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(p0: String, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int, p7: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: Int, p6: String, p7: String, p8: String, p9: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(p0: String, p1: String, p2: Int, p3: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(p0: String)

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(p0: String, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: Int, p6: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFindClose(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(p0: String, p1: Int, p2: String, p3: Int, p4: String, p5: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(p0: Int, p1: String, p2: Int, p3: Int, p4: String, p5: Int, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(p0: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(p0: Int, p1: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(p0: String, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(p0: String, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(p0: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcAddress(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetProcAddress(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(p0: String, p1: Int, p2: Int, p3: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(p0: String, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree(p0: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx(p0: String, p1: Int, p2: Int, p3: Int, p4: Int, p5: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(p0: Int, p1: Int, p2: String, p3: Int, p4: String, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: Int, p6: Int, p7: Int, p8: Int, p9: String, p10: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(p0: String, p1: Int, p2: String, p3: String, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(p0: String, p1: String, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String, p6: Int, p7: String, p8: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(p0: String, p1: String, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String, p6: Int, p7: String, p8: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReadFile(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(p0: String, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(p0: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSleep(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSleep(p0: Int)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(p0: String, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(p0: Int, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc(): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile(p0: String, p1: Int, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(p0: String, p1: Int, p2: String): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError()")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSASend(p0: String, p1: String, p2: Int, p3: String, p4: Int, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW(p0: Int, p1: Int, p2: Int, p3: String, p4: Int, p5: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(p0: String)

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(p0: Int, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysAccept(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysAccept(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysBind(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysBind(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysConnect(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysConnect(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(p0: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt(p0: String, p1: Int, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(p0, p1, p2)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysListen(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysListen(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW(p0)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysRecv(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRecv(p0: String, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSelect(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSelect(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSend(p0, p1, p2, p3)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSend(p0: String, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSendto(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSendto(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt(p0: String, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(p0, p1)")
private external fun wasmRustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(p0: String, p1: Int): Int

public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first.toInt(), 0L.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cancelIo(hfile: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeHandle(hobject: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1.rawAddress.toString(), cchcount1, lpstring2.rawAddress.toString(), cchcount2, bignorecase) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename.rawAddress.toString(), lpnewfilename.rawAddress.toString(), 0L.toString(), lpdata.rawAddress.toString(), pbcancel.rawAddress.toString(), dwcopyflags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname.rawAddress.toString(), lpsecurityattributes.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes.rawAddress.toString(), bmanualreset, binitialstate, lpname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename.rawAddress.toString(), dwdesiredaccess.toInt(), dwsharemode.toInt(), lpsecurityattributes.rawAddress.toString(), dwcreationdisposition.toInt(), dwflagsandattributes.toInt(), htemplatefile.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename.rawAddress.toString(), lpexistingfilename.rawAddress.toString(), lpsecurityattributes.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname.rawAddress.toString(), dwopenmode.toInt(), dwpipemode.toInt(), nmaxinstances.toInt(), noutbuffersize.toInt(), ninbuffersize.toInt(), ndefaulttimeout.toInt(), lpsecurityattributes.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe.rawAddress.toString(), hwritepipe.rawAddress.toString(), lppipeattributes.rawAddress.toString(), nsize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname.rawAddress.toString(), lpcommandline.rawAddress.toString(), lpprocessattributes.rawAddress.toString(), lpthreadattributes.rawAddress.toString(), binherithandles, dwcreationflags.toInt(), lpenvironment.rawAddress.toString(), lpcurrentdirectory.rawAddress.toString(), lpstartupinfo.rawAddress.toString(), lpprocessinformation.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename.rawAddress.toString(), lptargetfilename.rawAddress.toString(), dwflags.toInt()).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes.rawAddress.toString(), dwstacksize.toString(), 0L.toString(), lpparameter.rawAddress.toString(), dwcreationflags.toInt(), lpthreadid.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes.rawAddress.toString(), lptimername.rawAddress.toString(), dwflags.toInt(), dwdesiredaccess.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice.rawAddress.toString(), dwiocontrolcode.toInt(), lpinbuffer.rawAddress.toString(), ninbuffersize.toInt(), lpoutbuffer.rawAddress.toString(), noutbuffersize.toInt(), lpbytesreturned.rawAddress.toString(), lpoverlapped.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle.rawAddress.toString(), hsourcehandle.rawAddress.toString(), htargetprocesshandle.rawAddress.toString(), lptargethandle.rawAddress.toString(), dwdesiredaccess.toInt(), binherithandle, dwoptions.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exitProcess(uexitcode: UInt): Nothing {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exitProcess")
}

public actual fun findClose(hfindfile: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename.rawAddress.toString(), finfolevelid, lpfindfiledata.rawAddress.toString(), fsearchop, lpsearchfilter.rawAddress.toString(), dwadditionalflags.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile.rawAddress.toString(), lpfindfiledata.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(0L.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsFree(dwflsindex: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex.toInt(), lpflsdata.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flushFileBuffers(hfile: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags.toInt(), lpsource.rawAddress.toString(), dwmessageid.toInt(), dwlanguageid.toInt(), lpbuffer.rawAddress.toString(), nsize.toInt(), arguments.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCommandLineW(): Pwstr =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle.rawAddress.toString(), lpmode.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleOutputCP(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength.toInt(), lpbuffer.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcess(): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcessId(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThread(): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThreadId(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getEnvironmentStringsW(): Lpwch =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname.rawAddress.toString(), lpbuffer.rawAddress.toString(), nsize.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess.rawAddress.toString(), lpexitcode.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile.rawAddress.toString(), lpfileinformation.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile.rawAddress.toString(), fileinformationclass, lpfileinformation.rawAddress.toString(), dwbuffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile.rawAddress.toString(), lpfilesize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileType(hfile: Handle): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile.rawAddress.toString(), lpszfilepath.rawAddress.toString(), cchfilepath.toInt(), dwflags.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename.rawAddress.toString(), nbufferlength.toInt(), lpbuffer.rawAddress.toString(), lpfilepart.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getLastError(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getLogicalDrives(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule.rawAddress.toString(), lpfilename.rawAddress.toString(), nsize.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags.toInt(), lpmodulename.rawAddress.toString(), phmodule.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile.rawAddress.toString(), lpoverlapped.rawAddress.toString(), lpnumberofbytestransferred.rawAddress.toString(), bwait) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcessId(process: Handle): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId(process.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer.rawAddress.toString(), usize.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength.toInt(), lpbuffer.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken.rawAddress.toString(), lpprofiledir.rawAddress.toString(), lpcchsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer.rawAddress.toString(), usize.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce.rawAddress.toString(), dwflags.toInt(), fpending.rawAddress.toString(), lpcontext.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce.rawAddress.toString(), dwflags.toInt(), lpcontext.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist.rawAddress.toString(), dwattributecount.toInt(), dwflags.toInt(), lpsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isThreadAFiber(): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename.rawAddress.toString(), hfile.rawAddress.toString(), dwflags.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localFree(hmem: Hlocal): Hlocal =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile.rawAddress.toString(), dwflags.toInt(), dwreserved.toInt(), nnumberofbytestolocklow.toInt(), nnumberofbytestolockhigh.toInt(), lpoverlapped.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename.rawAddress.toString(), lpnewfilename.rawAddress.toString(), dwflags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage.toInt(), dwflags.toInt(), lpmultibytestr.rawAddress.toString(), cbmultibyte, lpwidecharstr.rawAddress.toString(), cchwidechar) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), allocationsize.rawAddress.toString(), fileattributes.toInt(), shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), eabuffer.rawAddress.toString(), ealength.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), shareaccess.toInt(), openoptions.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle.rawAddress.toString(), iostatusblock.rawAddress.toString(), fsinformation.rawAddress.toString(), length.toInt(), fsinformationclass) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle.rawAddress.toString(), event.rawAddress.toString(), 0L.toString(), apccontext.rawAddress.toString(), iostatusblock.rawAddress.toString(), buffer.rawAddress.toString(), length.toInt(), byteoffset.rawAddress.toString(), key.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle.rawAddress.toString(), iostatusblock.rawAddress.toString(), fileinformation.rawAddress.toString(), length.toInt(), fileinformationclass) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle.rawAddress.toString(), event.rawAddress.toString(), 0L.toString(), apccontext.rawAddress.toString(), iostatusblock.rawAddress.toString(), buffer.rawAddress.toString(), length.toInt(), byteoffset.rawAddress.toString(), key.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle.rawAddress.toString(), desiredaccess.toInt(), tokenhandle.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename.rawAddress.toString(), lptargetpath.rawAddress.toString(), ucchmax.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofcharstoread.toInt(), lpnumberofcharsread.rawAddress.toString(), pinputcontrol.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReadFile(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestoread.toInt(), lpnumberofbytesread.rawAddress.toString(), lpoverlapped.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestoread.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer.rawAddress.toString(), randombufferlength.toInt()).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname.rawAddress.toString(), lpvalue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename.rawAddress.toString(), dwfileattributes.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile.rawAddress.toString(), fileinformationclass, lpfileinformation.rawAddress.toString(), dwbuffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile.rawAddress.toString(), lidistancetomove.toString(), lpnewfilepointer.rawAddress.toString(), dwmovemethod.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile.rawAddress.toString(), lpcreationtime.rawAddress.toString(), lplastaccesstime.rawAddress.toString(), lplastwritetime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject.rawAddress.toString(), dwmask.toInt(), dwflags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setLastError(dwerrcode: UInt) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle.toInt(), hhandle.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer.rawAddress.toString(), lpduetime.rawAddress.toString(), lperiod, 0L.toString(), lpargtocompletionroutine.rawAddress.toString(), fresume) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleep(dwmilliseconds: UInt) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable.rawAddress.toString(), srwlock.rawAddress.toString(), dwmilliseconds.toInt(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds.toInt(), balertable).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun switchToThread(): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess.rawAddress.toString(), uexitcode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsAlloc(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex.toInt(), lptlsvalue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock.rawAddress.toString()).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock.rawAddress.toString()).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile.rawAddress.toString(), dwfileoffsetlow.toInt(), dwfileoffsethigh.toInt(), nnumberofbytestounlocklow.toInt(), nnumberofbytestounlockhigh.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist.rawAddress.toString(), dwflags.toInt(), attribute.toString(), lpvalue.rawAddress.toString(), cbsize.toString(), lppreviousvalue.rawAddress.toString(), lpreturnsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSACleanup(): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s.toString(), dwprocessid.toInt(), lpprotocolinfo.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSAGetLastError(): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv(s.toString(), lpbuffers.rawAddress.toString(), dwbuffercount.toInt(), lpnumberofbytesrecvd.rawAddress.toString(), lpflags.rawAddress.toString(), lpoverlapped.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSASend(s.toString(), lpbuffers.rawAddress.toString(), dwbuffercount.toInt(), lpnumberofbytessent.rawAddress.toString(), dwflags.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW(af, type, protocol, lpprotocolinfo.rawAddress.toString(), g.toInt(), dwflags.toInt()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested.toInt(), lpwsadata.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount.toInt(), lphandles.rawAddress.toString(), bwaitall, dwmilliseconds.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle.rawAddress.toString(), dwmilliseconds.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address.rawAddress.toString(), compareaddress.rawAddress.toString(), addresssize.toString(), dwmilliseconds.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress.toString(), cchwidechar, lpmultibytestr.rawAddress.toString(), cbmultibyte, lpdefaultchar.rawAddress.toString(), lpuseddefaultchar.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofcharstowrite.toInt(), lpnumberofcharswritten.rawAddress.toString(), lpreserved.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestowrite.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysAccept(s.toString(), addr.rawAddress.toString(), addrlen.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysBind(s.toString(), name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closesocket(s: Socket): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(s.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysConnect(s.toString(), name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmRustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename.rawAddress.toString(), pservicename.rawAddress.toString(), phints.rawAddress.toString(), ppresult.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername(s.toString(), name.rawAddress.toString(), namelen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname(s.toString(), name.rawAddress.toString(), namelen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s.toString(), cmd, argp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(s: Socket, backlog: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysListen(s.toString(), backlog) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRecv(s.toString(), buf.rawAddress.toString(), len, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom(s.toString(), buf.rawAddress.toString(), len, flags, from.rawAddress.toString(), fromlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSelect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), exceptfds.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSend(s.toString(), buf.rawAddress.toString(), len, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSendto(s.toString(), buf.rawAddress.toString(), len, flags, to.rawAddress.toString(), tolen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shutdown(s: Socket, how: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmRustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(s.toString(), how) else throw UnsupportedOperationException("Native FFI addon is not available")
