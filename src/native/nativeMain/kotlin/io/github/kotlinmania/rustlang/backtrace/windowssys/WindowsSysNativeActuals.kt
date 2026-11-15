@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysCloseHandle
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysCreateFileMappingA
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysCreateMutexA
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysCreateToolhelp32Snapshot
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysEnumerateLoadedModulesW64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysGetCurrentProcess
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysGetCurrentProcessId
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysGetCurrentThread
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysLoadLibraryA
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysLstrlenW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysModule32FirstW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysModule32NextW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysReleaseMutex
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysRtlCaptureContext
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysRtlLookupFunctionEntry
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysStackWalk64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysStackWalkEx
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymAddrIncludeInlineTrace
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymFromAddrW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymFromInlineContextW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymFunctionTableAccess64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymGetLineFromAddrW64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymGetLineFromInlineContextW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymGetModuleBase64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymGetOptions
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymGetSearchPathW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymInitializeW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymQueryInlineTrace
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymSetOptions
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysSymSetSearchPathW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysWaitForSingleObjectEx
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.rustlangWindowsSysWideCharToMultiByte
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool =
    rustlangWindowsSysEnumerateLoadedModulesW64(hprocess.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), usercontext.rawAddress.toCPointer<ByteVar>())

public actual fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool =
    rustlangWindowsSysStackWalk64(machinetype, hprocess.rawAddress.toCPointer<ByteVar>(), hthread.rawAddress.toCPointer<ByteVar>(), stackframe.rawAddress.toCPointer<ByteVar>(), contextrecord.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool =
    rustlangWindowsSysStackWalkEx(machinetype, hprocess.rawAddress.toCPointer<ByteVar>(), hthread.rawAddress.toCPointer<ByteVar>(), stackframe.rawAddress.toCPointer<ByteVar>(), contextrecord.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), flags)

public actual fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt =
    rustlangWindowsSysSymAddrIncludeInlineTrace(hprocess.rawAddress.toCPointer<ByteVar>(), address)

public actual fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool =
    rustlangWindowsSysSymFromAddrW(hprocess.rawAddress.toCPointer<ByteVar>(), address, displacement.rawAddress.toCPointer<ByteVar>(), symbol.rawAddress.toCPointer<ByteVar>())

public actual fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool =
    rustlangWindowsSysSymFromInlineContextW(hprocess.rawAddress.toCPointer<ByteVar>(), address, inlinecontext, displacement.rawAddress.toCPointer<ByteVar>(), symbol.rawAddress.toCPointer<ByteVar>())

public actual fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr =
    MutPtr(rustlangWindowsSysSymFunctionTableAccess64(hprocess.rawAddress.toCPointer<ByteVar>(), addrbase)?.toLong() ?: 0L)

public actual fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    rustlangWindowsSysSymGetLineFromAddrW64(hprocess.rawAddress.toCPointer<ByteVar>(), dwaddr, pdwdisplacement.rawAddress.toCPointer<ByteVar>(), line.rawAddress.toCPointer<ByteVar>())

public actual fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    rustlangWindowsSysSymGetLineFromInlineContextW(hprocess.rawAddress.toCPointer<ByteVar>(), dwaddr, inlinecontext, qwmodulebaseaddress, pdwdisplacement.rawAddress.toCPointer<ByteVar>(), line.rawAddress.toCPointer<ByteVar>())

public actual fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong =
    rustlangWindowsSysSymGetModuleBase64(hprocess.rawAddress.toCPointer<ByteVar>(), qwaddr)

public actual fun symGetOptions(): UInt =
    rustlangWindowsSysSymGetOptions()

public actual fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool =
    rustlangWindowsSysSymGetSearchPathW(hprocess.rawAddress.toCPointer<ByteVar>(), searchpatha.rawAddress.toCPointer<ByteVar>(), searchpathlength)

public actual fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool =
    rustlangWindowsSysSymInitializeW(hprocess.rawAddress.toCPointer<ByteVar>(), usersearchpath.rawAddress.toCPointer<ByteVar>(), finvadeprocess)

public actual fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool =
    rustlangWindowsSysSymQueryInlineTrace(hprocess.rawAddress.toCPointer<ByteVar>(), startaddress, startcontext, startretaddress, curaddress, curcontext.rawAddress.toCPointer<ByteVar>(), curframeindex.rawAddress.toCPointer<ByteVar>())

public actual fun symSetOptions(symoptions: UInt): UInt =
    rustlangWindowsSysSymSetOptions(symoptions)

public actual fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool =
    rustlangWindowsSysSymSetSearchPathW(hprocess.rawAddress.toCPointer<ByteVar>(), searchpatha.rawAddress.toCPointer<ByteVar>())

public actual fun closeHandle(hobject: Handle): Bool =
    rustlangWindowsSysCloseHandle(hobject.rawAddress.toCPointer<ByteVar>())

public actual fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle =
    MutPtr(rustlangWindowsSysCreateFileMappingA(hfile.rawAddress.toCPointer<ByteVar>(), lpfilemappingattributes.rawAddress.toCPointer<ByteVar>(), flprotect, dwmaximumsizehigh, dwmaximumsizelow, lpname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle =
    MutPtr(rustlangWindowsSysCreateMutexA(lpmutexattributes.rawAddress.toCPointer<ByteVar>(), binitialowner, lpname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle =
    MutPtr(rustlangWindowsSysCreateToolhelp32Snapshot(dwflags, th32processid)?.toLong() ?: 0L)

public actual fun getCurrentProcess(): Handle =
    MutPtr(rustlangWindowsSysGetCurrentProcess()?.toLong() ?: 0L)

public actual fun getCurrentProcessId(): UInt =
    rustlangWindowsSysGetCurrentProcessId()

public actual fun getCurrentThread(): Handle =
    MutPtr(rustlangWindowsSysGetCurrentThread()?.toLong() ?: 0L)

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    null

public actual fun loadLibraryA(lplibfilename: Pcstr): Hmodule =
    MutPtr(rustlangWindowsSysLoadLibraryA(lplibfilename.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress =
    MemoryMappedViewAddress()

public actual fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool =
    rustlangWindowsSysModule32FirstW(hsnapshot.rawAddress.toCPointer<ByteVar>(), lpme.rawAddress.toCPointer<ByteVar>())

public actual fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool =
    rustlangWindowsSysModule32NextW(hsnapshot.rawAddress.toCPointer<ByteVar>(), lpme.rawAddress.toCPointer<ByteVar>())

public actual fun releaseMutex(hmutex: Handle): Bool =
    rustlangWindowsSysReleaseMutex(hmutex.rawAddress.toCPointer<ByteVar>())

public actual fun rtlCaptureContext(contextrecord: MutPtr) {
    rustlangWindowsSysRtlCaptureContext(contextrecord.rawAddress.toCPointer<ByteVar>())
}

public actual fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr =
    MutPtr(rustlangWindowsSysRtlLookupFunctionEntry(controlpc, imagebase.rawAddress.toCPointer<ByteVar>(), historytable.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine =
    null

public actual fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool =
    0

public actual fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent =
    rustlangWindowsSysWaitForSingleObjectEx(hhandle.rawAddress.toCPointer<ByteVar>(), dwmilliseconds, balertable)

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int =
    rustlangWindowsSysWideCharToMultiByte(codepage, dwflags, lpwidecharstr.rawAddress.toCPointer<ByteVar>(), cchwidechar, lpmultibytestr.rawAddress.toCPointer<ByteVar>(), cbmultibyte, lpdefaultchar.rawAddress.toCPointer<ByteVar>(), lpuseddefaultchar.rawAddress.toCPointer<ByteVar>())

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    rustlangWindowsSysLstrlenW(lpstring.rawAddress.toCPointer<ByteVar>())
