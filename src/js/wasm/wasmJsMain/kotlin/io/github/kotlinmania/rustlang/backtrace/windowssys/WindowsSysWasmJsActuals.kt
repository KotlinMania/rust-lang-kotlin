@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_backtrace !== 'undefined') return globalThis.__km_napi_backtrace !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_backtrace = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_backtrace = null; return false; } try { globalThis.__km_napi_backtrace = req('@kotlinmania/backtrace-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['backtrace_native.node', 'build/node-ffi/backtrace_native.node', 'build/node-ffi/release/backtrace_native.node', 'build/node-ffi/debug/backtrace_native.node', 'src/js/node-backtrace/build/Release/backtrace_native.node', 'src/js/node-backtrace/build/Debug/backtrace_native.node', 'src/js/node-backtrace/dist/build/Release/backtrace_native.node', 'src/js/node-backtrace/dist/build/Debug/backtrace_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_backtrace = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_backtrace = null; return false; }")
private external fun initWindowsSysNapiAddon(): Boolean

private val windowsSysNapiAvailable: Boolean = initWindowsSysNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_backtrace.backtraceWindowsSysEnumerateLoadedModulesW64(p0, p1, p2)")
private external fun wasmBacktraceWindowsSysEnumerateLoadedModulesW64(p0: String, p1: String, p2: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8) => globalThis.__km_napi_backtrace.backtraceWindowsSysStackWalk64(p0, p1, p2, p3, p4, p5, p6, p7, p8)")
private external fun wasmBacktraceWindowsSysStackWalk64(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String, p7: String, p8: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9) => globalThis.__km_napi_backtrace.backtraceWindowsSysStackWalkEx(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9)")
private external fun wasmBacktraceWindowsSysStackWalkEx(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String, p7: String, p8: String, p9: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymAddrIncludeInlineTrace(p0, p1)")
private external fun wasmBacktraceWindowsSysSymAddrIncludeInlineTrace(p0: String, p1: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymFromAddrW(p0, p1, p2, p3)")
private external fun wasmBacktraceWindowsSysSymFromAddrW(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymFromInlineContextW(p0, p1, p2, p3, p4)")
private external fun wasmBacktraceWindowsSysSymFromInlineContextW(p0: String, p1: String, p2: Int, p3: String, p4: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymFunctionTableAccess64(p0, p1)")
private external fun wasmBacktraceWindowsSysSymFunctionTableAccess64(p0: String, p1: String): String

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymGetLineFromAddrW64(p0, p1, p2, p3)")
private external fun wasmBacktraceWindowsSysSymGetLineFromAddrW64(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymGetLineFromInlineContextW(p0, p1, p2, p3, p4, p5)")
private external fun wasmBacktraceWindowsSysSymGetLineFromInlineContextW(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymGetModuleBase64(p0, p1)")
private external fun wasmBacktraceWindowsSysSymGetModuleBase64(p0: String, p1: String): String

@JsFun("() => globalThis.__km_napi_backtrace.backtraceWindowsSysSymGetOptions()")
private external fun wasmBacktraceWindowsSysSymGetOptions(): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymGetSearchPathW(p0, p1, p2)")
private external fun wasmBacktraceWindowsSysSymGetSearchPathW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymInitializeW(p0, p1, p2)")
private external fun wasmBacktraceWindowsSysSymInitializeW(p0: String, p1: String, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymQueryInlineTrace(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmBacktraceWindowsSysSymQueryInlineTrace(p0: String, p1: String, p2: Int, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymSetOptions(p0)")
private external fun wasmBacktraceWindowsSysSymSetOptions(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_backtrace.backtraceWindowsSysSymSetSearchPathW(p0, p1)")
private external fun wasmBacktraceWindowsSysSymSetSearchPathW(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceWindowsSysCloseHandle(p0)")
private external fun wasmBacktraceWindowsSysCloseHandle(p0: String): Int

@JsFun("(p0, p1, p2, p3, p4, p5) => globalThis.__km_napi_backtrace.backtraceWindowsSysCreateFileMappingA(p0, p1, p2, p3, p4, p5)")
private external fun wasmBacktraceWindowsSysCreateFileMappingA(p0: String, p1: String, p2: Int, p3: Int, p4: Int, p5: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_backtrace.backtraceWindowsSysCreateMutexA(p0, p1, p2)")
private external fun wasmBacktraceWindowsSysCreateMutexA(p0: String, p1: Int, p2: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_backtrace.backtraceWindowsSysCreateToolhelp32Snapshot(p0, p1)")
private external fun wasmBacktraceWindowsSysCreateToolhelp32Snapshot(p0: Int, p1: Int): String

@JsFun("() => globalThis.__km_napi_backtrace.backtraceWindowsSysGetCurrentProcess()")
private external fun wasmBacktraceWindowsSysGetCurrentProcess(): String

@JsFun("() => globalThis.__km_napi_backtrace.backtraceWindowsSysGetCurrentProcessId()")
private external fun wasmBacktraceWindowsSysGetCurrentProcessId(): Int

@JsFun("() => globalThis.__km_napi_backtrace.backtraceWindowsSysGetCurrentThread()")
private external fun wasmBacktraceWindowsSysGetCurrentThread(): String

@JsFun("(p0, p1) => globalThis.__km_napi_backtrace.backtraceWindowsSysGetProcAddress(p0, p1)")
private external fun wasmBacktraceWindowsSysGetProcAddress(p0: String, p1: String): String

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceWindowsSysLoadLibraryA(p0)")
private external fun wasmBacktraceWindowsSysLoadLibraryA(p0: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_backtrace.backtraceWindowsSysModule32FirstW(p0, p1)")
private external fun wasmBacktraceWindowsSysModule32FirstW(p0: String, p1: String): Int

@JsFun("(p0, p1) => globalThis.__km_napi_backtrace.backtraceWindowsSysModule32NextW(p0, p1)")
private external fun wasmBacktraceWindowsSysModule32NextW(p0: String, p1: String): Int

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceWindowsSysReleaseMutex(p0)")
private external fun wasmBacktraceWindowsSysReleaseMutex(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceWindowsSysRtlCaptureContext(p0)")
private external fun wasmBacktraceWindowsSysRtlCaptureContext(p0: String)

@JsFun("(p0, p1, p2) => globalThis.__km_napi_backtrace.backtraceWindowsSysRtlLookupFunctionEntry(p0, p1, p2)")
private external fun wasmBacktraceWindowsSysRtlLookupFunctionEntry(p0: String, p1: String, p2: String): String

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_backtrace.backtraceWindowsSysRtlVirtualUnwind(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmBacktraceWindowsSysRtlVirtualUnwind(p0: Int, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String, p7: String): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_backtrace.backtraceWindowsSysWaitForSingleObjectEx(p0, p1, p2)")
private external fun wasmBacktraceWindowsSysWaitForSingleObjectEx(p0: String, p1: Int, p2: Int): Int

@JsFun("(p0, p1, p2, p3, p4, p5, p6, p7) => globalThis.__km_napi_backtrace.backtraceWindowsSysWideCharToMultiByte(p0, p1, p2, p3, p4, p5, p6, p7)")
private external fun wasmBacktraceWindowsSysWideCharToMultiByte(p0: Int, p1: Int, p2: String, p3: Int, p4: String, p5: Int, p6: String, p7: String): Int

@JsFun("(p0) => globalThis.__km_napi_backtrace.backtraceWindowsSysLstrlenW(p0)")
private external fun wasmBacktraceWindowsSysLstrlenW(p0: String): Int

public actual fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysEnumerateLoadedModulesW64(hprocess.rawAddress.toString(), 0L.toString(), usercontext.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysStackWalk64(machinetype.toInt(), hprocess.rawAddress.toString(), hthread.rawAddress.toString(), stackframe.rawAddress.toString(), contextrecord.rawAddress.toString(), 0L.toString(), 0L.toString(), 0L.toString(), 0L.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysStackWalkEx(machinetype.toInt(), hprocess.rawAddress.toString(), hthread.rawAddress.toString(), stackframe.rawAddress.toString(), contextrecord.rawAddress.toString(), 0L.toString(), 0L.toString(), 0L.toString(), 0L.toString(), flags.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymAddrIncludeInlineTrace(hprocess.rawAddress.toString(), address.toString()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymFromAddrW(hprocess.rawAddress.toString(), address.toString(), displacement.rawAddress.toString(), symbol.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymFromInlineContextW(hprocess.rawAddress.toString(), address.toString(), inlinecontext.toInt(), displacement.rawAddress.toString(), symbol.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr =
    if (windowsSysNapiAvailable) MutPtr(wasmBacktraceWindowsSysSymFunctionTableAccess64(hprocess.rawAddress.toString(), addrbase.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymGetLineFromAddrW64(hprocess.rawAddress.toString(), dwaddr.toString(), pdwdisplacement.rawAddress.toString(), line.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymGetLineFromInlineContextW(hprocess.rawAddress.toString(), dwaddr.toString(), inlinecontext.toInt(), qwmodulebaseaddress.toString(), pdwdisplacement.rawAddress.toString(), line.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymGetModuleBase64(hprocess.rawAddress.toString(), qwaddr.toString()).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetOptions(): UInt =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymGetOptions().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymGetSearchPathW(hprocess.rawAddress.toString(), searchpatha.rawAddress.toString(), searchpathlength.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymInitializeW(hprocess.rawAddress.toString(), usersearchpath.rawAddress.toString(), finvadeprocess) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymQueryInlineTrace(hprocess.rawAddress.toString(), startaddress.toString(), startcontext.toInt(), startretaddress.toString(), curaddress.toString(), curcontext.rawAddress.toString(), curframeindex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symSetOptions(symoptions: UInt): UInt =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymSetOptions(symoptions.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysSymSetSearchPathW(hprocess.rawAddress.toString(), searchpatha.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeHandle(hobject: Handle): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysCloseHandle(hobject.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmBacktraceWindowsSysCreateFileMappingA(hfile.rawAddress.toString(), lpfilemappingattributes.rawAddress.toString(), flprotect.toInt(), dwmaximumsizehigh.toInt(), dwmaximumsizelow.toInt(), lpname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmBacktraceWindowsSysCreateMutexA(lpmutexattributes.rawAddress.toString(), binitialowner, lpname.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmBacktraceWindowsSysCreateToolhelp32Snapshot(dwflags.toInt(), th32processid.toInt()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcess(): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmBacktraceWindowsSysGetCurrentProcess().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcessId(): UInt =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysGetCurrentProcessId().toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThread(): Handle =
    if (windowsSysNapiAvailable) MutPtr(wasmBacktraceWindowsSysGetCurrentThread().toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    if (windowsSysNapiAvailable) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loadLibraryA(lplibfilename: Pcstr): Hmodule =
    if (windowsSysNapiAvailable) MutPtr(wasmBacktraceWindowsSysLoadLibraryA(lplibfilename.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysModule32FirstW(hsnapshot.rawAddress.toString(), lpme.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysModule32NextW(hsnapshot.rawAddress.toString(), lpme.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun releaseMutex(hmutex: Handle): Bool =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysReleaseMutex(hmutex.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlCaptureContext(contextrecord: MutPtr) {
    if (windowsSysNapiAvailable) {
        wasmBacktraceWindowsSysRtlCaptureContext(contextrecord.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr =
    if (windowsSysNapiAvailable) MutPtr(wasmBacktraceWindowsSysRtlLookupFunctionEntry(controlpc.toString(), imagebase.rawAddress.toString(), historytable.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine =
    if (windowsSysNapiAvailable) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysWaitForSingleObjectEx(hhandle.rawAddress.toString(), dwmilliseconds.toInt(), balertable).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress.toString(), cchwidechar, lpmultibytestr.rawAddress.toString(), cbmultibyte, lpdefaultchar.rawAddress.toString(), lpuseddefaultchar.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    if (windowsSysNapiAvailable) wasmBacktraceWindowsSysLstrlenW(lpstring.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
