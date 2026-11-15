@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val windowsSysNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysEnumerateLoadedModulesW64(hprocess.rawAddress.toString(), 0L.toString(), usercontext.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysStackWalk64(machinetype.toInt(), hprocess.rawAddress.toString(), hthread.rawAddress.toString(), stackframe.rawAddress.toString(), contextrecord.rawAddress.toString(), 0L.toString(), 0L.toString(), 0L.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysStackWalkEx(machinetype.toInt(), hprocess.rawAddress.toString(), hthread.rawAddress.toString(), stackframe.rawAddress.toString(), contextrecord.rawAddress.toString(), 0L.toString(), 0L.toString(), 0L.toString(), 0L.toString(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymAddrIncludeInlineTrace(hprocess.rawAddress.toString(), address.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymFromAddrW(hprocess.rawAddress.toString(), address.toString(), displacement.rawAddress.toString(), symbol.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymFromInlineContextW(hprocess.rawAddress.toString(), address.toString(), inlinecontext.toInt(), displacement.rawAddress.toString(), symbol.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr =
    if (windowsSysNapiAddon != null) MutPtr((windowsSysNapiAddon.rustlangWindowsSysSymFunctionTableAccess64(hprocess.rawAddress.toString(), addrbase.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymGetLineFromAddrW64(hprocess.rawAddress.toString(), dwaddr.toString(), pdwdisplacement.rawAddress.toString(), line.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymGetLineFromInlineContextW(hprocess.rawAddress.toString(), dwaddr.toString(), inlinecontext.toInt(), qwmodulebaseaddress.toString(), pdwdisplacement.rawAddress.toString(), line.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymGetModuleBase64(hprocess.rawAddress.toString(), qwaddr.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetOptions(): UInt =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymGetOptions() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymGetSearchPathW(hprocess.rawAddress.toString(), searchpatha.rawAddress.toString(), searchpathlength.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymInitializeW(hprocess.rawAddress.toString(), usersearchpath.rawAddress.toString(), finvadeprocess) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymQueryInlineTrace(hprocess.rawAddress.toString(), startaddress.toString(), startcontext.toInt(), startretaddress.toString(), curaddress.toString(), curcontext.rawAddress.toString(), curframeindex.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symSetOptions(symoptions: UInt): UInt =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymSetOptions(symoptions.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysSymSetSearchPathW(hprocess.rawAddress.toString(), searchpatha.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeHandle(hobject: Handle): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysCloseHandle(hobject.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle =
    if (windowsSysNapiAddon != null) MutPtr((windowsSysNapiAddon.rustlangWindowsSysCreateFileMappingA(hfile.rawAddress.toString(), lpfilemappingattributes.rawAddress.toString(), flprotect.toInt(), dwmaximumsizehigh.toInt(), dwmaximumsizelow.toInt(), lpname.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle =
    if (windowsSysNapiAddon != null) MutPtr((windowsSysNapiAddon.rustlangWindowsSysCreateMutexA(lpmutexattributes.rawAddress.toString(), binitialowner, lpname.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle =
    if (windowsSysNapiAddon != null) MutPtr((windowsSysNapiAddon.rustlangWindowsSysCreateToolhelp32Snapshot(dwflags.toInt(), th32processid.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcess(): Handle =
    if (windowsSysNapiAddon != null) MutPtr((windowsSysNapiAddon.rustlangWindowsSysGetCurrentProcess() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcessId(): UInt =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysGetCurrentProcessId() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThread(): Handle =
    if (windowsSysNapiAddon != null) MutPtr((windowsSysNapiAddon.rustlangWindowsSysGetCurrentThread() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    if (windowsSysNapiAddon != null) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loadLibraryA(lplibfilename: Pcstr): Hmodule =
    if (windowsSysNapiAddon != null) MutPtr((windowsSysNapiAddon.rustlangWindowsSysLoadLibraryA(lplibfilename.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysModule32FirstW(hsnapshot.rawAddress.toString(), lpme.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysModule32NextW(hsnapshot.rawAddress.toString(), lpme.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun releaseMutex(hmutex: Handle): Bool =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysReleaseMutex(hmutex.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlCaptureContext(contextrecord: MutPtr) {
    if (windowsSysNapiAddon != null) {
        windowsSysNapiAddon.rustlangWindowsSysRtlCaptureContext(contextrecord.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr =
    if (windowsSysNapiAddon != null) MutPtr((windowsSysNapiAddon.rustlangWindowsSysRtlLookupFunctionEntry(controlpc.toString(), imagebase.rawAddress.toString(), historytable.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine =
    if (windowsSysNapiAddon != null) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysWaitForSingleObjectEx(hhandle.rawAddress.toString(), dwmilliseconds.toInt(), balertable) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress.toString(), cchwidechar, lpmultibytestr.rawAddress.toString(), cbmultibyte, lpdefaultchar.rawAddress.toString(), lpuseddefaultchar.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    if (windowsSysNapiAddon != null) (windowsSysNapiAddon.rustlangWindowsSysLstrlenW(lpstring.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
