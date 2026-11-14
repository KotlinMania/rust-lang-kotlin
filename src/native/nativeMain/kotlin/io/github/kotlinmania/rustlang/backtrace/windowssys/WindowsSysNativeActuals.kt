@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysCloseHandle
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysCreateFileMappingA
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysCreateMutexA
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysCreateToolhelp32Snapshot
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysEnumerateLoadedModulesW64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysGetCurrentProcess
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysGetCurrentProcessId
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysGetCurrentThread
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysLoadLibraryA
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysLstrlenW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysModule32FirstW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysModule32NextW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysReleaseMutex
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysRtlCaptureContext
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysRtlLookupFunctionEntry
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysStackWalk64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysStackWalkEx
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymAddrIncludeInlineTrace
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymFromAddrW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymFromInlineContextW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymFunctionTableAccess64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymGetLineFromAddrW64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymGetLineFromInlineContextW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymGetModuleBase64
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymGetOptions
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymGetSearchPathW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymInitializeW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymQueryInlineTrace
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymSetOptions
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysSymSetSearchPathW
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysWaitForSingleObjectEx
import io.github.kotlinmania.rustlang.backtrace.windowssys.cinterop.backtraceWindowsSysWideCharToMultiByte
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool =
    backtraceWindowsSysEnumerateLoadedModulesW64(hprocess.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), usercontext.rawAddress.toCPointer<ByteVar>())

public actual fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool =
    backtraceWindowsSysStackWalk64(machinetype, hprocess.rawAddress.toCPointer<ByteVar>(), hthread.rawAddress.toCPointer<ByteVar>(), stackframe.rawAddress.toCPointer<ByteVar>(), contextrecord.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool =
    backtraceWindowsSysStackWalkEx(machinetype, hprocess.rawAddress.toCPointer<ByteVar>(), hthread.rawAddress.toCPointer<ByteVar>(), stackframe.rawAddress.toCPointer<ByteVar>(), contextrecord.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), flags)

public actual fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt =
    backtraceWindowsSysSymAddrIncludeInlineTrace(hprocess.rawAddress.toCPointer<ByteVar>(), address)

public actual fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool =
    backtraceWindowsSysSymFromAddrW(hprocess.rawAddress.toCPointer<ByteVar>(), address, displacement.rawAddress.toCPointer<ByteVar>(), symbol.rawAddress.toCPointer<ByteVar>())

public actual fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool =
    backtraceWindowsSysSymFromInlineContextW(hprocess.rawAddress.toCPointer<ByteVar>(), address, inlinecontext, displacement.rawAddress.toCPointer<ByteVar>(), symbol.rawAddress.toCPointer<ByteVar>())

public actual fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr =
    MutPtr(backtraceWindowsSysSymFunctionTableAccess64(hprocess.rawAddress.toCPointer<ByteVar>(), addrbase)?.toLong() ?: 0L)

public actual fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    backtraceWindowsSysSymGetLineFromAddrW64(hprocess.rawAddress.toCPointer<ByteVar>(), dwaddr, pdwdisplacement.rawAddress.toCPointer<ByteVar>(), line.rawAddress.toCPointer<ByteVar>())

public actual fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    backtraceWindowsSysSymGetLineFromInlineContextW(hprocess.rawAddress.toCPointer<ByteVar>(), dwaddr, inlinecontext, qwmodulebaseaddress, pdwdisplacement.rawAddress.toCPointer<ByteVar>(), line.rawAddress.toCPointer<ByteVar>())

public actual fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong =
    backtraceWindowsSysSymGetModuleBase64(hprocess.rawAddress.toCPointer<ByteVar>(), qwaddr)

public actual fun symGetOptions(): UInt =
    backtraceWindowsSysSymGetOptions()

public actual fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool =
    backtraceWindowsSysSymGetSearchPathW(hprocess.rawAddress.toCPointer<ByteVar>(), searchpatha.rawAddress.toCPointer<ByteVar>(), searchpathlength)

public actual fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool =
    backtraceWindowsSysSymInitializeW(hprocess.rawAddress.toCPointer<ByteVar>(), usersearchpath.rawAddress.toCPointer<ByteVar>(), finvadeprocess)

public actual fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool =
    backtraceWindowsSysSymQueryInlineTrace(hprocess.rawAddress.toCPointer<ByteVar>(), startaddress, startcontext, startretaddress, curaddress, curcontext.rawAddress.toCPointer<ByteVar>(), curframeindex.rawAddress.toCPointer<ByteVar>())

public actual fun symSetOptions(symoptions: UInt): UInt =
    backtraceWindowsSysSymSetOptions(symoptions)

public actual fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool =
    backtraceWindowsSysSymSetSearchPathW(hprocess.rawAddress.toCPointer<ByteVar>(), searchpatha.rawAddress.toCPointer<ByteVar>())

public actual fun closeHandle(hobject: Handle): Bool =
    backtraceWindowsSysCloseHandle(hobject.rawAddress.toCPointer<ByteVar>())

public actual fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle =
    MutPtr(backtraceWindowsSysCreateFileMappingA(hfile.rawAddress.toCPointer<ByteVar>(), lpfilemappingattributes.rawAddress.toCPointer<ByteVar>(), flprotect, dwmaximumsizehigh, dwmaximumsizelow, lpname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle =
    MutPtr(backtraceWindowsSysCreateMutexA(lpmutexattributes.rawAddress.toCPointer<ByteVar>(), binitialowner, lpname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle =
    MutPtr(backtraceWindowsSysCreateToolhelp32Snapshot(dwflags, th32processid)?.toLong() ?: 0L)

public actual fun getCurrentProcess(): Handle =
    MutPtr(backtraceWindowsSysGetCurrentProcess()?.toLong() ?: 0L)

public actual fun getCurrentProcessId(): UInt =
    backtraceWindowsSysGetCurrentProcessId()

public actual fun getCurrentThread(): Handle =
    MutPtr(backtraceWindowsSysGetCurrentThread()?.toLong() ?: 0L)

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    null

public actual fun loadLibraryA(lplibfilename: Pcstr): Hmodule =
    MutPtr(backtraceWindowsSysLoadLibraryA(lplibfilename.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress =
    MemoryMappedViewAddress()

public actual fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool =
    backtraceWindowsSysModule32FirstW(hsnapshot.rawAddress.toCPointer<ByteVar>(), lpme.rawAddress.toCPointer<ByteVar>())

public actual fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool =
    backtraceWindowsSysModule32NextW(hsnapshot.rawAddress.toCPointer<ByteVar>(), lpme.rawAddress.toCPointer<ByteVar>())

public actual fun releaseMutex(hmutex: Handle): Bool =
    backtraceWindowsSysReleaseMutex(hmutex.rawAddress.toCPointer<ByteVar>())

public actual fun rtlCaptureContext(contextrecord: MutPtr) {
    backtraceWindowsSysRtlCaptureContext(contextrecord.rawAddress.toCPointer<ByteVar>())
}

public actual fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr =
    MutPtr(backtraceWindowsSysRtlLookupFunctionEntry(controlpc, imagebase.rawAddress.toCPointer<ByteVar>(), historytable.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine =
    null

public actual fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool =
    0

public actual fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent =
    backtraceWindowsSysWaitForSingleObjectEx(hhandle.rawAddress.toCPointer<ByteVar>(), dwmilliseconds, balertable)

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int =
    backtraceWindowsSysWideCharToMultiByte(codepage, dwflags, lpwidecharstr.rawAddress.toCPointer<ByteVar>(), cchwidechar, lpmultibytestr.rawAddress.toCPointer<ByteVar>(), cbmultibyte, lpdefaultchar.rawAddress.toCPointer<ByteVar>(), lpuseddefaultchar.rawAddress.toCPointer<ByteVar>())

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    backtraceWindowsSysLstrlenW(lpstring.rawAddress.toCPointer<ByteVar>())
