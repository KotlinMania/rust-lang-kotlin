@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initWindowsSysNapiAddon(): Boolean

private val windowsSysNapiAvailable: Boolean = initWindowsSysNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangWindowsSysEnumerateLoadedModulesW64(p0, p1, p2)")
private external fun wasmRustlangWindowsSysEnumerateLoadedModulesW64(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_kmcore.rustlangWindowsSysStackWalk64(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmRustlangWindowsSysStackWalk64(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String, p7: String, p8: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9) => globalThis.__km_napi_kmcore.rustlangWindowsSysStackWalkEx(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9)")
private external fun wasmRustlangWindowsSysStackWalkEx(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String, p7: String, p8: String, p9: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymAddrIncludeInlineTrace(p0, p1)")
private external fun wasmRustlangWindowsSysSymAddrIncludeInlineTrace(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymFromAddrW(p0, p1, p2, p3)")
private external fun wasmRustlangWindowsSysSymFromAddrW(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymFromInlineContextW(p0, p1, p2, p3, p4)")
private external fun wasmRustlangWindowsSysSymFromInlineContextW(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymFunctionTableAccess64(p0, p1)")
private external fun wasmRustlangWindowsSysSymFunctionTableAccess64(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymGetLineFromAddrW64(p0, p1, p2, p3)")
private external fun wasmRustlangWindowsSysSymGetLineFromAddrW64(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymGetLineFromInlineContextW(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangWindowsSysSymGetLineFromInlineContextW(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymGetModuleBase64(p0, p1)")
private external fun wasmRustlangWindowsSysSymGetModuleBase64(p0: String, p1: String): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangWindowsSysSymGetOptions()")
private external fun wasmRustlangWindowsSysSymGetOptions(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymGetSearchPathW(p0, p1, p2)")
private external fun wasmRustlangWindowsSysSymGetSearchPathW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymInitializeW(p0, p1, p2)")
private external fun wasmRustlangWindowsSysSymInitializeW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymQueryInlineTrace(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmRustlangWindowsSysSymQueryInlineTrace(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymSetOptions(p0)")
private external fun wasmRustlangWindowsSysSymSetOptions(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangWindowsSysSymSetSearchPathW(p0, p1)")
private external fun wasmRustlangWindowsSysSymSetSearchPathW(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangWindowsSysCloseHandle(p0)")
private external fun wasmRustlangWindowsSysCloseHandle(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_kmcore.rustlangWindowsSysCreateFileMappingA(p0, p1, p2, p3, p4, p5)")
private external fun wasmRustlangWindowsSysCreateFileMappingA(p0: String, p1: String, p2: Int, p3: Int, p4: Int, p5: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangWindowsSysCreateMutexA(p0, p1, p2)")
private external fun wasmRustlangWindowsSysCreateMutexA(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangWindowsSysCreateToolhelp32Snapshot(p0, p1)")
private external fun wasmRustlangWindowsSysCreateToolhelp32Snapshot(p0: Int, p1: Int): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangWindowsSysGetCurrentProcess()")
private external fun wasmRustlangWindowsSysGetCurrentProcess(): String

@JsFun("() => globalThis.__km_napi_kmcore.rustlangWindowsSysGetCurrentProcessId()")
private external fun wasmRustlangWindowsSysGetCurrentProcessId(): Int

@JsFun("() => globalThis.__km_napi_kmcore.rustlangWindowsSysGetCurrentThread()")
private external fun wasmRustlangWindowsSysGetCurrentThread(): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangWindowsSysGetProcAddress(p0, p1)")
private external fun wasmRustlangWindowsSysGetProcAddress(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangWindowsSysLoadLibraryA(p0)")
private external fun wasmRustlangWindowsSysLoadLibraryA(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangWindowsSysModule32FirstW(p0, p1)")
private external fun wasmRustlangWindowsSysModule32FirstW(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangWindowsSysModule32NextW(p0, p1)")
private external fun wasmRustlangWindowsSysModule32NextW(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangWindowsSysReleaseMutex(p0)")
private external fun wasmRustlangWindowsSysReleaseMutex(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangWindowsSysRtlCaptureContext(p0)")
private external fun wasmRustlangWindowsSysRtlCaptureContext(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangWindowsSysRtlLookupFunctionEntry(p0, p1, p2)")
private external fun wasmRustlangWindowsSysRtlLookupFunctionEntry(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangWindowsSysRtlVirtualUnwind(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangWindowsSysRtlVirtualUnwind(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String, p7: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangWindowsSysWaitForSingleObjectEx(p0, p1, p2)")
private external fun wasmRustlangWindowsSysWaitForSingleObjectEx(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_kmcore.rustlangWindowsSysWideCharToMultiByte(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmRustlangWindowsSysWideCharToMultiByte(p0: Int, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: String, p7: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangWindowsSysLstrlenW(p0)")
private external fun wasmRustlangWindowsSysLstrlenW(p0: String): Int

public actual fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysEnumerateLoadedModulesW64(hprocess.rawAddress.toString(), 0L.toString(), usercontext.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysStackWalk64(machinetype.toInt(), hprocess.rawAddress.toString(), hthread.rawAddress.toString(), stackframe.rawAddress.toString(), contextrecord.rawAddress.toString(), 0L.toString(), 0L.toString(), 0L.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysStackWalkEx(machinetype.toInt(), hprocess.rawAddress.toString(), hthread.rawAddress.toString(), stackframe.rawAddress.toString(), contextrecord.rawAddress.toString(), 0L.toString(), 0L.toString(), 0L.toString(), 0L.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymAddrIncludeInlineTrace(hprocess.rawAddress.toString(), address.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymFromAddrW(hprocess.rawAddress.toString(), address.toString(), displacement.rawAddress.toString(), symbol.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymFromInlineContextW(hprocess.rawAddress.toString(), address.toString(), inlinecontext.toInt(), displacement.rawAddress.toString(), symbol.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr =
    if (windowsSysNapiAvailable) MutPtr(wasmRustlangWindowsSysSymFunctionTableAccess64(hprocess.rawAddress.toString(), addrbase.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymGetLineFromAddrW64(hprocess.rawAddress.toString(), dwaddr.toString(), pdwdisplacement.rawAddress.toString(), line.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymGetLineFromInlineContextW(hprocess.rawAddress.toString(), dwaddr.toString(), inlinecontext.toInt(), qwmodulebaseaddress.toString(), pdwdisplacement.rawAddress.toString(), line.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymGetModuleBase64(hprocess.rawAddress.toString(), qwaddr.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetOptions(): UInt =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymGetOptions().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymGetSearchPathW(hprocess.rawAddress.toString(), searchpatha.rawAddress.toString(), searchpathlength.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymInitializeW(hprocess.rawAddress.toString(), usersearchpath.rawAddress.toString(), finvadeprocess) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymQueryInlineTrace(hprocess.rawAddress.toString(), startaddress.toString(), startcontext.toInt(), startretaddress.toString(), curaddress.toString(), curcontext.rawAddress.toString(), curframeindex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symSetOptions(symoptions: UInt): UInt =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymSetOptions(symoptions.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysSymSetSearchPathW(hprocess.rawAddress.toString(), searchpatha.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeHandle(hobject: Handle): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysCloseHandle(hobject.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmRustlangWindowsSysCreateFileMappingA(hfile.rawAddress.toString(), lpfilemappingattributes.rawAddress.toString(), flprotect.toInt(), dwmaximumsizehigh.toInt(), dwmaximumsizelow.toInt(), lpname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmRustlangWindowsSysCreateMutexA(lpmutexattributes.rawAddress.toString(), binitialowner, lpname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmRustlangWindowsSysCreateToolhelp32Snapshot(dwflags.toInt(), th32processid.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcess(): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmRustlangWindowsSysGetCurrentProcess().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcessId(): UInt =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysGetCurrentProcessId().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThread(): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmRustlangWindowsSysGetCurrentThread().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    if (windowsSysNapiAvailable) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loadLibraryA(lplibfilename: Pcstr): Hmodule =
    if (windowsSysNapiAvailable) MutPtr(wasmRustlangWindowsSysLoadLibraryA(lplibfilename.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysModule32FirstW(hsnapshot.rawAddress.toString(), lpme.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysModule32NextW(hsnapshot.rawAddress.toString(), lpme.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun releaseMutex(hmutex: Handle): Bool =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysReleaseMutex(hmutex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlCaptureContext(contextrecord: MutPtr) {
    if (windowsSysNapiAvailable) {
        wasmRustlangWindowsSysRtlCaptureContext(contextrecord.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr =
    if (windowsSysNapiAvailable) MutPtr(wasmRustlangWindowsSysRtlLookupFunctionEntry(controlpc.toString(), imagebase.rawAddress.toString(), historytable.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine =
    if (windowsSysNapiAvailable) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysWaitForSingleObjectEx(hhandle.rawAddress.toString(), dwmilliseconds.toInt(), balertable).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress.toString(), cchwidechar, lpmultibytestr.rawAddress.toString(), cbmultibyte, lpdefaultchar.rawAddress.toString(), lpuseddefaultchar.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    if (windowsSysNapiAvailable) wasmRustlangWindowsSysLstrlenW(lpstring.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
