@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object WindowsSysJniBridge {
    init {
        System.loadLibrary("backtrace_jni")
    }

    @JvmStatic
    external fun backtraceWindowsSysEnumerateLoadedModulesW64(hprocess: Long, enumloadedmodulescallback: Long, usercontext: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysStackWalk64(machinetype: Int, hprocess: Long, hthread: Long, stackframe: Long, contextrecord: Long, readmemoryroutine: Long, functiontableaccessroutine: Long, getmodulebaseroutine: Long, translateaddress: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysStackWalkEx(machinetype: Int, hprocess: Long, hthread: Long, stackframe: Long, contextrecord: Long, readmemoryroutine: Long, functiontableaccessroutine: Long, getmodulebaseroutine: Long, translateaddress: Long, flags: Int): Int

    @JvmStatic
    external fun backtraceWindowsSysSymAddrIncludeInlineTrace(hprocess: Long, address: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysSymFromAddrW(hprocess: Long, address: Long, displacement: Long, symbol: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysSymFromInlineContextW(hprocess: Long, address: Long, inlinecontext: Int, displacement: Long, symbol: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysSymFunctionTableAccess64(hprocess: Long, addrbase: Long): Long

    @JvmStatic
    external fun backtraceWindowsSysSymGetLineFromAddrW64(hprocess: Long, dwaddr: Long, pdwdisplacement: Long, line: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysSymGetLineFromInlineContextW(hprocess: Long, dwaddr: Long, inlinecontext: Int, qwmodulebaseaddress: Long, pdwdisplacement: Long, line: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysSymGetModuleBase64(hprocess: Long, qwaddr: Long): Long

    @JvmStatic
    external fun backtraceWindowsSysSymGetOptions(): Int

    @JvmStatic
    external fun backtraceWindowsSysSymGetSearchPathW(hprocess: Long, searchpatha: Long, searchpathlength: Int): Int

    @JvmStatic
    external fun backtraceWindowsSysSymInitializeW(hprocess: Long, usersearchpath: Long, finvadeprocess: Int): Int

    @JvmStatic
    external fun backtraceWindowsSysSymQueryInlineTrace(hprocess: Long, startaddress: Long, startcontext: Int, startretaddress: Long, curaddress: Long, curcontext: Long, curframeindex: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysSymSetOptions(symoptions: Int): Int

    @JvmStatic
    external fun backtraceWindowsSysSymSetSearchPathW(hprocess: Long, searchpatha: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysCloseHandle(hobject: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysCreateFileMappingA(hfile: Long, lpfilemappingattributes: Long, flprotect: Int, dwmaximumsizehigh: Int, dwmaximumsizelow: Int, lpname: Long): Long

    @JvmStatic
    external fun backtraceWindowsSysCreateMutexA(lpmutexattributes: Long, binitialowner: Int, lpname: Long): Long

    @JvmStatic
    external fun backtraceWindowsSysCreateToolhelp32Snapshot(dwflags: Int, th32processid: Int): Long

    @JvmStatic
    external fun backtraceWindowsSysGetCurrentProcess(): Long

    @JvmStatic
    external fun backtraceWindowsSysGetCurrentProcessId(): Int

    @JvmStatic
    external fun backtraceWindowsSysGetCurrentThread(): Long

    @JvmStatic
    external fun backtraceWindowsSysGetProcAddress(hmodule: Long, lpprocname: Long): Long

    @JvmStatic
    external fun backtraceWindowsSysLoadLibraryA(lplibfilename: Long): Long

    @JvmStatic
    external fun backtraceWindowsSysModule32FirstW(hsnapshot: Long, lpme: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysModule32NextW(hsnapshot: Long, lpme: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysReleaseMutex(hmutex: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysRtlCaptureContext(contextrecord: Long)

    @JvmStatic
    external fun backtraceWindowsSysRtlLookupFunctionEntry(controlpc: Long, imagebase: Long, historytable: Long): Long

    @JvmStatic
    external fun backtraceWindowsSysRtlVirtualUnwind(handlertype: Int, imagebase: Long, controlpc: Long, functionentry: Long, contextrecord: Long, handlerdata: Long, establisherframe: Long, contextpointers: Long): Long

    @JvmStatic
    external fun backtraceWindowsSysWaitForSingleObjectEx(hhandle: Long, dwmilliseconds: Int, balertable: Int): Int

    @JvmStatic
    external fun backtraceWindowsSysWideCharToMultiByte(codepage: Int, dwflags: Int, lpwidecharstr: Long, cchwidechar: Int, lpmultibytestr: Long, cbmultibyte: Int, lpdefaultchar: Long, lpuseddefaultchar: Long): Int

    @JvmStatic
    external fun backtraceWindowsSysLstrlenW(lpstring: Long): Int
}

public actual fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysEnumerateLoadedModulesW64(hprocess.rawAddress, 0L, usercontext.rawAddress)

public actual fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool =
    WindowsSysJniBridge.backtraceWindowsSysStackWalk64(machinetype.toInt(), hprocess.rawAddress, hthread.rawAddress, stackframe.rawAddress, contextrecord.rawAddress, 0L, 0L, 0L, 0L)

public actual fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool =
    WindowsSysJniBridge.backtraceWindowsSysStackWalkEx(machinetype.toInt(), hprocess.rawAddress, hthread.rawAddress, stackframe.rawAddress, contextrecord.rawAddress, 0L, 0L, 0L, 0L, flags.toInt())

public actual fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt =
    WindowsSysJniBridge.backtraceWindowsSysSymAddrIncludeInlineTrace(hprocess.rawAddress, address.toLong()).toUInt()

public actual fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysSymFromAddrW(hprocess.rawAddress, address.toLong(), displacement.rawAddress, symbol.rawAddress)

public actual fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysSymFromInlineContextW(hprocess.rawAddress, address.toLong(), inlinecontext.toInt(), displacement.rawAddress, symbol.rawAddress)

public actual fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr =
    MutPtr(WindowsSysJniBridge.backtraceWindowsSysSymFunctionTableAccess64(hprocess.rawAddress, addrbase.toLong()))

public actual fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysSymGetLineFromAddrW64(hprocess.rawAddress, dwaddr.toLong(), pdwdisplacement.rawAddress, line.rawAddress)

public actual fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysSymGetLineFromInlineContextW(hprocess.rawAddress, dwaddr.toLong(), inlinecontext.toInt(), qwmodulebaseaddress.toLong(), pdwdisplacement.rawAddress, line.rawAddress)

public actual fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong =
    WindowsSysJniBridge.backtraceWindowsSysSymGetModuleBase64(hprocess.rawAddress, qwaddr.toLong()).toULong()

public actual fun symGetOptions(): UInt =
    WindowsSysJniBridge.backtraceWindowsSysSymGetOptions().toUInt()

public actual fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool =
    WindowsSysJniBridge.backtraceWindowsSysSymGetSearchPathW(hprocess.rawAddress, searchpatha.rawAddress, searchpathlength.toInt())

public actual fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool =
    WindowsSysJniBridge.backtraceWindowsSysSymInitializeW(hprocess.rawAddress, usersearchpath.rawAddress, finvadeprocess)

public actual fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysSymQueryInlineTrace(hprocess.rawAddress, startaddress.toLong(), startcontext.toInt(), startretaddress.toLong(), curaddress.toLong(), curcontext.rawAddress, curframeindex.rawAddress)

public actual fun symSetOptions(symoptions: UInt): UInt =
    WindowsSysJniBridge.backtraceWindowsSysSymSetOptions(symoptions.toInt()).toUInt()

public actual fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysSymSetSearchPathW(hprocess.rawAddress, searchpatha.rawAddress)

public actual fun closeHandle(hobject: Handle): Bool =
    WindowsSysJniBridge.backtraceWindowsSysCloseHandle(hobject.rawAddress)

public actual fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle =
    MutPtr(WindowsSysJniBridge.backtraceWindowsSysCreateFileMappingA(hfile.rawAddress, lpfilemappingattributes.rawAddress, flprotect.toInt(), dwmaximumsizehigh.toInt(), dwmaximumsizelow.toInt(), lpname.rawAddress))

public actual fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle =
    MutPtr(WindowsSysJniBridge.backtraceWindowsSysCreateMutexA(lpmutexattributes.rawAddress, binitialowner, lpname.rawAddress))

public actual fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle =
    MutPtr(WindowsSysJniBridge.backtraceWindowsSysCreateToolhelp32Snapshot(dwflags.toInt(), th32processid.toInt()))

public actual fun getCurrentProcess(): Handle =
    MutPtr(WindowsSysJniBridge.backtraceWindowsSysGetCurrentProcess())

public actual fun getCurrentProcessId(): UInt =
    WindowsSysJniBridge.backtraceWindowsSysGetCurrentProcessId().toUInt()

public actual fun getCurrentThread(): Handle =
    MutPtr(WindowsSysJniBridge.backtraceWindowsSysGetCurrentThread())

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    null

public actual fun loadLibraryA(lplibfilename: Pcstr): Hmodule =
    MutPtr(WindowsSysJniBridge.backtraceWindowsSysLoadLibraryA(lplibfilename.rawAddress))

public actual fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress =
    MemoryMappedViewAddress()

public actual fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysModule32FirstW(hsnapshot.rawAddress, lpme.rawAddress)

public actual fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool =
    WindowsSysJniBridge.backtraceWindowsSysModule32NextW(hsnapshot.rawAddress, lpme.rawAddress)

public actual fun releaseMutex(hmutex: Handle): Bool =
    WindowsSysJniBridge.backtraceWindowsSysReleaseMutex(hmutex.rawAddress)

public actual fun rtlCaptureContext(contextrecord: MutPtr) {
    WindowsSysJniBridge.backtraceWindowsSysRtlCaptureContext(contextrecord.rawAddress)
}

public actual fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr =
    MutPtr(WindowsSysJniBridge.backtraceWindowsSysRtlLookupFunctionEntry(controlpc.toLong(), imagebase.rawAddress, historytable.rawAddress))

public actual fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine =
    null

public actual fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool =
    0

public actual fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent =
    WindowsSysJniBridge.backtraceWindowsSysWaitForSingleObjectEx(hhandle.rawAddress, dwmilliseconds.toInt(), balertable).toUInt()

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int =
    WindowsSysJniBridge.backtraceWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress, cchwidechar, lpmultibytestr.rawAddress, cbmultibyte, lpdefaultchar.rawAddress, lpuseddefaultchar.rawAddress)

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    WindowsSysJniBridge.backtraceWindowsSysLstrlenW(lpstring.rawAddress)
