@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_std !== 'undefined') return globalThis.__km_napi_std !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_std = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_std = null; return false; } try { globalThis.__km_napi_std = req('@kotlinmania/std-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['std_native.node', 'build/node-ffi/std_native.node', 'build/node-ffi/release/std_native.node', 'build/node-ffi/debug/std_native.node', 'src/js/node-std/build/Release/std_native.node', 'src/js/node-std/build/Debug/std_native.node', 'src/js/node-std/dist/build/Release/std_native.node', 'src/js/node-std/dist/build/Debug/std_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_std = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_std = null; return false; }")
private external fun initSysPalWindowsCWindowssysWindowsSysNapiAddon(): Boolean

private val sysPalWindowsCWindowssysWindowsSysNapiAvailable: Boolean = initSysPalWindowsCWindowssysWindowsSysNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(p0: String)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(p0: Int, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCancelIo(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCancelIo(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCloseHandle(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCloseHandle(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(p0: String, p1: Int, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCopyFileExW(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(p0: String, p1: String, p2: String, p3: String, p4: String, p5: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateEventW(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateEventW(p0: String, p1: Int, p2: Int, p3: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateFileW(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateFileW(p0: String, p1: Int, p2: Int, p3: String, p4: Int, p5: Int, p6: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(p0: String, p1: Int, p2: Int, p3: Int, p4: Int, p5: Int, p6: Int, p7: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreatePipe(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreatePipe(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateProcessW(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: Int, p6: String, p7: String, p8: String, p9: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateThread(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateThread(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(p0: String, p1: String, p2: Int, p3: Int): String

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysDeleteFileW(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(p0: String)

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(p0: String, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(p0: String, p1: String, p2: String, p3: String, p4: Int, p5: Int, p6: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysExitProcess(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysExitProcess(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFindClose(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFindClose(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(p0: String, p1: Int, p2: String, p3: Int, p4: String, p5: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFindNextFileW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFlsAlloc(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFlsFree(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFlsFree(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFlsGetValue(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFlsSetValue(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFormatMessageW(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(p0: Int, p1: String, p2: Int, p3: Int, p4: String, p5: Int, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(p0: Int): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW(): String

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(p0: String, p1: String): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(p0: Int, p1: String): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess(): String

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread(): String

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW(): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(p0: String, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetFileType(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetFileType(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(p0: String, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetHostNameW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetLastError()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetLastError(): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(p0: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(p0: Int, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(p0: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetProcAddress(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetProcAddress(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetProcessId(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetProcessId(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetStdHandle(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(p0: String)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(p0: String)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(p0: String)

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetTempPathW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(p0: String, p1: Int, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(p0: String, p1: Int, p2: Int, p3: String): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(p0: String, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysLocalFree(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysLocalFree(p0: String): String

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysLockFileEx(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysLockFileEx(p0: String, p1: Int, p2: Int, p3: Int, p4: Int, p5: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysMoveFileExW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(p0: Int, p1: Int, p2: String, p3: Int, p4: String, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysNtCreateFile(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: Int, p6: Int, p7: Int, p8: Int, p9: String, p10: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysNtOpenFile(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(p0: String, p1: Int, p2: String, p3: String, p4: Int, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(p0: String, p1: String, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysNtReadFile(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysNtReadFile(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String, p6: Int, p7: String, p8: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(p0: String, p1: String, p2: String, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysNtWriteFile(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String, p6: Int, p7: String, p8: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysReadConsoleW(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysReadFile(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysReadFile(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysReadFileEx(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysReadFileEx(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(p0: String)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(p0: String)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(p0: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(p0: String, p1: Int, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetFileTime(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetFileTime(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetLastError(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetLastError(p0: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetStdHandle(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSleep(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSleep(p0: Int)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(p0: String, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSleepEx(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSleepEx(p0: Int, p1: Int): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSwitchToThread()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSwitchToThread(): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysTerminateProcess(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(p0: String, p1: Int): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysTlsAlloc()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysTlsAlloc(): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysTlsFree(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysTlsFree(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysTlsGetValue(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysTlsSetValue(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(p0: Int, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysUnlockFile(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysUnlockFile(p0: String, p1: Int, p2: Int, p3: Int, p4: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(p0: String, p1: Int, p2: String, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWSACleanup()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWSACleanup(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(p0: String, p1: Int, p2: String): Int

@JsFun("() => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError()")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWSARecv(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWSARecv(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWSASend(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWSASend(p0: String, p1: String, p2: Int, p3: String, p4: Int, p5: String, p6: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWSASocketW(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWSASocketW(p0: Int, p1: Int, p2: Int, p3: String, p4: Int, p5: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWSAStartup(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWSAStartup(p0: Int, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(p0: Int, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(p0: String, p1: String, p2: String, p3: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(p0: String)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(p0: String)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(p0: String)

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(p0: String)

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(p0: Int, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: String, p7: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysWriteFileEx(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysAccept(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysAccept(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysBind(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysBind(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysClosesocket(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysClosesocket(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysConnect(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysConnect(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(p0: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetpeername(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetpeername(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetsockname(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetsockname(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysGetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysGetsockopt(p0: String, p1: Int, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysIoctlsocket(p0, p1, p2)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(p0: String, p1: Int, p2: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysListen(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysListen(p0: String, p1: Int): Int

@JsFun("(p0) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysLstrlenW(p0)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysLstrlenW(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysRecv(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysRecv(p0: String, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysRecvfrom(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysRecvfrom(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSelect(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSelect(p0: Int, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSend(p0, p1, p2, p3)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSend(p0: String, p1: String, p2: Int, p3: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSendto(p0, p1, p2, p3, p4, p5)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSendto(p0: String, p1: String, p2: Int, p3: Int, p4: String, p5: Int): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysSetsockopt(p0, p1, p2, p3, p4)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysSetsockopt(p0: String, p1: Int, p2: Int, p3: String, p4: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_std.stdSysPalWindowsCWindowssysWindowsSysShutdown(p0, p1)")
private external fun wasmStdSysPalWindowsCWindowssysWindowsSysShutdown(p0: String, p1: Int): Int

public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first.toInt(), 0L.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cancelIo(hfile: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeHandle(hobject: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1.rawAddress.toString(), cchcount1, lpstring2.rawAddress.toString(), cchcount2, bignorecase) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename.rawAddress.toString(), lpnewfilename.rawAddress.toString(), 0L.toString(), lpdata.rawAddress.toString(), pbcancel.rawAddress.toString(), dwcopyflags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname.rawAddress.toString(), lpsecurityattributes.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes.rawAddress.toString(), bmanualreset, binitialstate, lpname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename.rawAddress.toString(), dwdesiredaccess.toInt(), dwsharemode.toInt(), lpsecurityattributes.rawAddress.toString(), dwcreationdisposition.toInt(), dwflagsandattributes.toInt(), htemplatefile.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename.rawAddress.toString(), lpexistingfilename.rawAddress.toString(), lpsecurityattributes.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname.rawAddress.toString(), dwopenmode.toInt(), dwpipemode.toInt(), nmaxinstances.toInt(), noutbuffersize.toInt(), ninbuffersize.toInt(), ndefaulttimeout.toInt(), lpsecurityattributes.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe.rawAddress.toString(), hwritepipe.rawAddress.toString(), lppipeattributes.rawAddress.toString(), nsize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname.rawAddress.toString(), lpcommandline.rawAddress.toString(), lpprocessattributes.rawAddress.toString(), lpthreadattributes.rawAddress.toString(), binherithandles, dwcreationflags.toInt(), lpenvironment.rawAddress.toString(), lpcurrentdirectory.rawAddress.toString(), lpstartupinfo.rawAddress.toString(), lpprocessinformation.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename.rawAddress.toString(), lptargetfilename.rawAddress.toString(), dwflags.toInt()).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes.rawAddress.toString(), dwstacksize.toString(), 0L.toString(), lpparameter.rawAddress.toString(), dwcreationflags.toInt(), lpthreadid.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes.rawAddress.toString(), lptimername.rawAddress.toString(), dwflags.toInt(), dwdesiredaccess.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice.rawAddress.toString(), dwiocontrolcode.toInt(), lpinbuffer.rawAddress.toString(), ninbuffersize.toInt(), lpoutbuffer.rawAddress.toString(), noutbuffersize.toInt(), lpbytesreturned.rawAddress.toString(), lpoverlapped.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle.rawAddress.toString(), hsourcehandle.rawAddress.toString(), htargetprocesshandle.rawAddress.toString(), lptargethandle.rawAddress.toString(), dwdesiredaccess.toInt(), binherithandle, dwoptions.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exitProcess(uexitcode: UInt): Nothing {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exitProcess")
}

public actual fun findClose(hfindfile: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename.rawAddress.toString(), finfolevelid, lpfindfiledata.rawAddress.toString(), fsearchop, lpsearchfilter.rawAddress.toString(), dwadditionalflags.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile.rawAddress.toString(), lpfindfiledata.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(0L.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsFree(dwflsindex: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex.toInt(), lpflsdata.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flushFileBuffers(hfile: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags.toInt(), lpsource.rawAddress.toString(), dwmessageid.toInt(), dwlanguageid.toInt(), lpbuffer.rawAddress.toString(), nsize.toInt(), arguments.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCommandLineW(): Pwstr =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle.rawAddress.toString(), lpmode.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleOutputCP(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength.toInt(), lpbuffer.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcess(): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcessId(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThread(): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThreadId(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getEnvironmentStringsW(): Lpwch =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname.rawAddress.toString(), lpbuffer.rawAddress.toString(), nsize.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess.rawAddress.toString(), lpexitcode.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile.rawAddress.toString(), lpfileinformation.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile.rawAddress.toString(), fileinformationclass, lpfileinformation.rawAddress.toString(), dwbuffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile.rawAddress.toString(), lpfilesize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileType(hfile: Handle): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile.rawAddress.toString(), lpszfilepath.rawAddress.toString(), cchfilepath.toInt(), dwflags.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename.rawAddress.toString(), nbufferlength.toInt(), lpbuffer.rawAddress.toString(), lpfilepart.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getLastError(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetLastError().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getLogicalDrives(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule.rawAddress.toString(), lpfilename.rawAddress.toString(), nsize.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags.toInt(), lpmodulename.rawAddress.toString(), phmodule.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile.rawAddress.toString(), lpoverlapped.rawAddress.toString(), lpnumberofbytestransferred.rawAddress.toString(), bwait) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcessId(process: Handle): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetProcessId(process.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer.rawAddress.toString(), usize.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength.toInt(), lpbuffer.rawAddress.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken.rawAddress.toString(), lpprofiledir.rawAddress.toString(), lpcchsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer.rawAddress.toString(), usize.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce.rawAddress.toString(), dwflags.toInt(), fpending.rawAddress.toString(), lpcontext.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce.rawAddress.toString(), dwflags.toInt(), lpcontext.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist.rawAddress.toString(), dwattributecount.toInt(), dwflags.toInt(), lpsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isThreadAFiber(): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename.rawAddress.toString(), hfile.rawAddress.toString(), dwflags.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localFree(hmem: Hlocal): Hlocal =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile.rawAddress.toString(), dwflags.toInt(), dwreserved.toInt(), nnumberofbytestolocklow.toInt(), nnumberofbytestolockhigh.toInt(), lpoverlapped.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename.rawAddress.toString(), lpnewfilename.rawAddress.toString(), dwflags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage.toInt(), dwflags.toInt(), lpmultibytestr.rawAddress.toString(), cbmultibyte, lpwidecharstr.rawAddress.toString(), cchwidechar) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), allocationsize.rawAddress.toString(), fileattributes.toInt(), shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), eabuffer.rawAddress.toString(), ealength.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), shareaccess.toInt(), openoptions.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle.rawAddress.toString(), iostatusblock.rawAddress.toString(), fsinformation.rawAddress.toString(), length.toInt(), fsinformationclass) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle.rawAddress.toString(), event.rawAddress.toString(), 0L.toString(), apccontext.rawAddress.toString(), iostatusblock.rawAddress.toString(), buffer.rawAddress.toString(), length.toInt(), byteoffset.rawAddress.toString(), key.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle.rawAddress.toString(), iostatusblock.rawAddress.toString(), fileinformation.rawAddress.toString(), length.toInt(), fileinformationclass) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle.rawAddress.toString(), event.rawAddress.toString(), 0L.toString(), apccontext.rawAddress.toString(), iostatusblock.rawAddress.toString(), buffer.rawAddress.toString(), length.toInt(), byteoffset.rawAddress.toString(), key.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle.rawAddress.toString(), desiredaccess.toInt(), tokenhandle.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename.rawAddress.toString(), lptargetpath.rawAddress.toString(), ucchmax.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofcharstoread.toInt(), lpnumberofcharsread.rawAddress.toString(), pinputcontrol.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysReadFile(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestoread.toInt(), lpnumberofbytesread.rawAddress.toString(), lpoverlapped.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestoread.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer.rawAddress.toString(), randombufferlength.toInt()).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname.rawAddress.toString(), lpvalue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename.rawAddress.toString(), dwfileattributes.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile.rawAddress.toString(), fileinformationclass, lpfileinformation.rawAddress.toString(), dwbuffersize.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile.rawAddress.toString(), lidistancetomove.toString(), lpnewfilepointer.rawAddress.toString(), dwmovemethod.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile.rawAddress.toString(), lpcreationtime.rawAddress.toString(), lplastaccesstime.rawAddress.toString(), lplastwritetime.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject.rawAddress.toString(), dwmask.toInt(), dwflags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setLastError(dwerrcode: UInt) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle.toInt(), hhandle.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer.rawAddress.toString(), lpduetime.rawAddress.toString(), lperiod, 0L.toString(), lpargtocompletionroutine.rawAddress.toString(), fresume) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleep(dwmilliseconds: UInt) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable.rawAddress.toString(), srwlock.rawAddress.toString(), dwmilliseconds.toInt(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds.toInt(), balertable).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun switchToThread(): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSwitchToThread() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess.rawAddress.toString(), uexitcode.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsAlloc(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysTlsAlloc().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) MutPtr(wasmStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex.toInt(), lptlsvalue.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock.rawAddress.toString()).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock.rawAddress.toString()).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile.rawAddress.toString(), dwfileoffsetlow.toInt(), dwfileoffsethigh.toInt(), nnumberofbytestounlocklow.toInt(), nnumberofbytestounlockhigh.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist.rawAddress.toString(), dwflags.toInt(), attribute.toString(), lpvalue.rawAddress.toString(), cbsize.toString(), lppreviousvalue.rawAddress.toString(), lpreturnsize.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSACleanup(): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWSACleanup() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s.toString(), dwprocessid.toInt(), lpprotocolinfo.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSAGetLastError(): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWSARecv(s.toString(), lpbuffers.rawAddress.toString(), dwbuffercount.toInt(), lpnumberofbytesrecvd.rawAddress.toString(), lpflags.rawAddress.toString(), lpoverlapped.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWSASend(s.toString(), lpbuffers.rawAddress.toString(), dwbuffercount.toInt(), lpnumberofbytessent.rawAddress.toString(), dwflags.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWSASocketW(af, type, protocol, lpprotocolinfo.rawAddress.toString(), g.toInt(), dwflags.toInt()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested.toInt(), lpwsadata.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount.toInt(), lphandles.rawAddress.toString(), bwaitall, dwmilliseconds.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle.rawAddress.toString(), dwmilliseconds.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address.rawAddress.toString(), compareaddress.rawAddress.toString(), addresssize.toString(), dwmilliseconds.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress.toString(), cchwidechar, lpmultibytestr.rawAddress.toString(), cbmultibyte, lpdefaultchar.rawAddress.toString(), lpuseddefaultchar.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofcharstowrite.toInt(), lpnumberofcharswritten.rawAddress.toString(), lpreserved.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestowrite.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysAccept(s.toString(), addr.rawAddress.toString(), addrlen.rawAddress.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysBind(s.toString(), name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closesocket(s: Socket): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysClosesocket(s.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysConnect(s.toString(), name.rawAddress.toString(), namelen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) {
        wasmStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename.rawAddress.toString(), pservicename.rawAddress.toString(), phints.rawAddress.toString(), ppresult.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetpeername(s.toString(), name.rawAddress.toString(), namelen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetsockname(s.toString(), name.rawAddress.toString(), namelen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysGetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s.toString(), cmd, argp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(s: Socket, backlog: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysListen(s.toString(), backlog) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysRecv(s.toString(), buf.rawAddress.toString(), len, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysRecvfrom(s.toString(), buf.rawAddress.toString(), len, flags, from.rawAddress.toString(), fromlen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSelect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), exceptfds.rawAddress.toString(), timeout.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSend(s.toString(), buf.rawAddress.toString(), len, flags) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSendto(s.toString(), buf.rawAddress.toString(), len, flags, to.rawAddress.toString(), tolen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysSetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shutdown(s: Socket, how: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAvailable) wasmStdSysPalWindowsCWindowssysWindowsSysShutdown(s.toString(), how) else throw UnsupportedOperationException("Native FFI addon is not available")
