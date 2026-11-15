@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysEnumerateLoadedModulesW64")

public actual fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysStackWalk64")

public actual fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysStackWalkEx")

public actual fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymAddrIncludeInlineTrace")

public actual fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymFromAddrW")

public actual fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymFromInlineContextW")

public actual fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymFunctionTableAccess64")

public actual fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymGetLineFromAddrW64")

public actual fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymGetLineFromInlineContextW")

public actual fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymGetModuleBase64")

@kotlin.wasm.WasmImport("kmcore", "rustlangWindowsSysSymGetOptions")
private external fun wasiRustlangWindowsSysSymGetOptions(): Int

public actual fun symGetOptions(): UInt =
    wasiRustlangWindowsSysSymGetOptions().toUInt()

public actual fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymGetSearchPathW")

public actual fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymInitializeW")

public actual fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymQueryInlineTrace")

@kotlin.wasm.WasmImport("kmcore", "rustlangWindowsSysSymSetOptions")
private external fun wasiRustlangWindowsSysSymSetOptions(symoptions: Int): Int

public actual fun symSetOptions(symoptions: UInt): UInt =
    wasiRustlangWindowsSysSymSetOptions(symoptions.toInt()).toUInt()

public actual fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysSymSetSearchPathW")

public actual fun closeHandle(hobject: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysCloseHandle")

public actual fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysCreateFileMappingA")

public actual fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysCreateMutexA")

public actual fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysCreateToolhelp32Snapshot")

public actual fun getCurrentProcess(): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysGetCurrentProcess")

@kotlin.wasm.WasmImport("kmcore", "rustlangWindowsSysGetCurrentProcessId")
private external fun wasiRustlangWindowsSysGetCurrentProcessId(): Int

public actual fun getCurrentProcessId(): UInt =
    wasiRustlangWindowsSysGetCurrentProcessId().toUInt()

public actual fun getCurrentThread(): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysGetCurrentThread")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysGetProcAddress")

public actual fun loadLibraryA(lplibfilename: Pcstr): Hmodule =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysLoadLibraryA")

public actual fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysMapViewOfFile")

public actual fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysModule32FirstW")

public actual fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysModule32NextW")

public actual fun releaseMutex(hmutex: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysReleaseMutex")

public actual fun rtlCaptureContext(contextrecord: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysRtlCaptureContext")
}

public actual fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysRtlLookupFunctionEntry")

public actual fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysRtlVirtualUnwind")

public actual fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysUnmapViewOfFile")

public actual fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysWaitForSingleObjectEx")

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysWideCharToMultiByte")

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangWindowsSysLstrlenW")
