@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.backtrace.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object WindowsSysJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun rustlangWindowsSysEnumerateLoadedModulesW64(hprocess: Long, enumloadedmodulescallback: Long, usercontext: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysStackWalk64(machinetype: Int, hprocess: Long, hthread: Long, stackframe: Long, contextrecord: Long, readmemoryroutine: Long, functiontableaccessroutine: Long, getmodulebaseroutine: Long, translateaddress: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysStackWalkEx(machinetype: Int, hprocess: Long, hthread: Long, stackframe: Long, contextrecord: Long, readmemoryroutine: Long, functiontableaccessroutine: Long, getmodulebaseroutine: Long, translateaddress: Long, flags: Int): Int

    @JvmStatic
    external fun rustlangWindowsSysSymAddrIncludeInlineTrace(hprocess: Long, address: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysSymFromAddrW(hprocess: Long, address: Long, displacement: Long, symbol: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysSymFromInlineContextW(hprocess: Long, address: Long, inlinecontext: Int, displacement: Long, symbol: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysSymFunctionTableAccess64(hprocess: Long, addrbase: Long): Long

    @JvmStatic
    external fun rustlangWindowsSysSymGetLineFromAddrW64(hprocess: Long, dwaddr: Long, pdwdisplacement: Long, line: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysSymGetLineFromInlineContextW(hprocess: Long, dwaddr: Long, inlinecontext: Int, qwmodulebaseaddress: Long, pdwdisplacement: Long, line: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysSymGetModuleBase64(hprocess: Long, qwaddr: Long): Long

    @JvmStatic
    external fun rustlangWindowsSysSymGetOptions(): Int

    @JvmStatic
    external fun rustlangWindowsSysSymGetSearchPathW(hprocess: Long, searchpatha: Long, searchpathlength: Int): Int

    @JvmStatic
    external fun rustlangWindowsSysSymInitializeW(hprocess: Long, usersearchpath: Long, finvadeprocess: Int): Int

    @JvmStatic
    external fun rustlangWindowsSysSymQueryInlineTrace(hprocess: Long, startaddress: Long, startcontext: Int, startretaddress: Long, curaddress: Long, curcontext: Long, curframeindex: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysSymSetOptions(symoptions: Int): Int

    @JvmStatic
    external fun rustlangWindowsSysSymSetSearchPathW(hprocess: Long, searchpatha: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysCloseHandle(hobject: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysCreateFileMappingA(hfile: Long, lpfilemappingattributes: Long, flprotect: Int, dwmaximumsizehigh: Int, dwmaximumsizelow: Int, lpname: Long): Long

    @JvmStatic
    external fun rustlangWindowsSysCreateMutexA(lpmutexattributes: Long, binitialowner: Int, lpname: Long): Long

    @JvmStatic
    external fun rustlangWindowsSysCreateToolhelp32Snapshot(dwflags: Int, th32processid: Int): Long

    @JvmStatic
    external fun rustlangWindowsSysGetCurrentProcess(): Long

    @JvmStatic
    external fun rustlangWindowsSysGetCurrentProcessId(): Int

    @JvmStatic
    external fun rustlangWindowsSysGetCurrentThread(): Long

    @JvmStatic
    external fun rustlangWindowsSysGetProcAddress(hmodule: Long, lpprocname: Long): Long

    @JvmStatic
    external fun rustlangWindowsSysLoadLibraryA(lplibfilename: Long): Long

    @JvmStatic
    external fun rustlangWindowsSysModule32FirstW(hsnapshot: Long, lpme: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysModule32NextW(hsnapshot: Long, lpme: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysReleaseMutex(hmutex: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysRtlCaptureContext(contextrecord: Long)

    @JvmStatic
    external fun rustlangWindowsSysRtlLookupFunctionEntry(controlpc: Long, imagebase: Long, historytable: Long): Long

    @JvmStatic
    external fun rustlangWindowsSysRtlVirtualUnwind(handlertype: Int, imagebase: Long, controlpc: Long, functionentry: Long, contextrecord: Long, handlerdata: Long, establisherframe: Long, contextpointers: Long): Long

    @JvmStatic
    external fun rustlangWindowsSysWaitForSingleObjectEx(hhandle: Long, dwmilliseconds: Int, balertable: Int): Int

    @JvmStatic
    external fun rustlangWindowsSysWideCharToMultiByte(codepage: Int, dwflags: Int, lpwidecharstr: Long, cchwidechar: Int, lpmultibytestr: Long, cbmultibyte: Int, lpdefaultchar: Long, lpuseddefaultchar: Long): Int

    @JvmStatic
    external fun rustlangWindowsSysLstrlenW(lpstring: Long): Int
}

public actual fun enumerateLoadedModulesW64(hprocess: Handle, enumloadedmodulescallback: PenumloadedModulesCallbackw64, usercontext: ConstPtr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysEnumerateLoadedModulesW64(hprocess.rawAddress, 0L, usercontext.rawAddress)

public actual fun stackWalk64(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64): Bool =
    WindowsSysJniBridge.rustlangWindowsSysStackWalk64(machinetype.toInt(), hprocess.rawAddress, hthread.rawAddress, stackframe.rawAddress, contextrecord.rawAddress, 0L, 0L, 0L, 0L)

public actual fun stackWalkEx(machinetype: UInt, hprocess: Handle, hthread: Handle, stackframe: MutPtr, contextrecord: MutPtr, readmemoryroutine: PreadProcessMemoryRoutine64, functiontableaccessroutine: PfunctionTableAccessRoutine64, getmodulebaseroutine: PgetModuleBaseRoutine64, translateaddress: PtranslateAddressRoutine64, flags: UInt): Bool =
    WindowsSysJniBridge.rustlangWindowsSysStackWalkEx(machinetype.toInt(), hprocess.rawAddress, hthread.rawAddress, stackframe.rawAddress, contextrecord.rawAddress, 0L, 0L, 0L, 0L, flags.toInt())

public actual fun symAddrIncludeInlineTrace(hprocess: Handle, address: ULong): UInt =
    WindowsSysJniBridge.rustlangWindowsSysSymAddrIncludeInlineTrace(hprocess.rawAddress, address.toLong()).toUInt()

public actual fun symFromAddrW(hprocess: Handle, address: ULong, displacement: MutPtr, symbol: MutPtr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysSymFromAddrW(hprocess.rawAddress, address.toLong(), displacement.rawAddress, symbol.rawAddress)

public actual fun symFromInlineContextW(hprocess: Handle, address: ULong, inlinecontext: UInt, displacement: MutPtr, symbol: MutPtr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysSymFromInlineContextW(hprocess.rawAddress, address.toLong(), inlinecontext.toInt(), displacement.rawAddress, symbol.rawAddress)

public actual fun symFunctionTableAccess64(hprocess: Handle, addrbase: ULong): MutPtr =
    MutPtr(WindowsSysJniBridge.rustlangWindowsSysSymFunctionTableAccess64(hprocess.rawAddress, addrbase.toLong()))

public actual fun symGetLineFromAddrW64(hprocess: Handle, dwaddr: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysSymGetLineFromAddrW64(hprocess.rawAddress, dwaddr.toLong(), pdwdisplacement.rawAddress, line.rawAddress)

public actual fun symGetLineFromInlineContextW(hprocess: Handle, dwaddr: ULong, inlinecontext: UInt, qwmodulebaseaddress: ULong, pdwdisplacement: MutPtr, line: MutPtr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysSymGetLineFromInlineContextW(hprocess.rawAddress, dwaddr.toLong(), inlinecontext.toInt(), qwmodulebaseaddress.toLong(), pdwdisplacement.rawAddress, line.rawAddress)

public actual fun symGetModuleBase64(hprocess: Handle, qwaddr: ULong): ULong =
    WindowsSysJniBridge.rustlangWindowsSysSymGetModuleBase64(hprocess.rawAddress, qwaddr.toLong()).toULong()

public actual fun symGetOptions(): UInt =
    WindowsSysJniBridge.rustlangWindowsSysSymGetOptions().toUInt()

public actual fun symGetSearchPathW(hprocess: Handle, searchpatha: Pwstr, searchpathlength: UInt): Bool =
    WindowsSysJniBridge.rustlangWindowsSysSymGetSearchPathW(hprocess.rawAddress, searchpatha.rawAddress, searchpathlength.toInt())

public actual fun symInitializeW(hprocess: Handle, usersearchpath: Pcwstr, finvadeprocess: Bool): Bool =
    WindowsSysJniBridge.rustlangWindowsSysSymInitializeW(hprocess.rawAddress, usersearchpath.rawAddress, finvadeprocess)

public actual fun symQueryInlineTrace(hprocess: Handle, startaddress: ULong, startcontext: UInt, startretaddress: ULong, curaddress: ULong, curcontext: MutPtr, curframeindex: MutPtr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysSymQueryInlineTrace(hprocess.rawAddress, startaddress.toLong(), startcontext.toInt(), startretaddress.toLong(), curaddress.toLong(), curcontext.rawAddress, curframeindex.rawAddress)

public actual fun symSetOptions(symoptions: UInt): UInt =
    WindowsSysJniBridge.rustlangWindowsSysSymSetOptions(symoptions.toInt()).toUInt()

public actual fun symSetSearchPathW(hprocess: Handle, searchpatha: Pcwstr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysSymSetSearchPathW(hprocess.rawAddress, searchpatha.rawAddress)

public actual fun closeHandle(hobject: Handle): Bool =
    WindowsSysJniBridge.rustlangWindowsSysCloseHandle(hobject.rawAddress)

public actual fun createFileMappingA(hfile: Handle, lpfilemappingattributes: ConstPtr, flprotect: PageProtectionFlags, dwmaximumsizehigh: UInt, dwmaximumsizelow: UInt, lpname: Pcstr): Handle =
    MutPtr(WindowsSysJniBridge.rustlangWindowsSysCreateFileMappingA(hfile.rawAddress, lpfilemappingattributes.rawAddress, flprotect.toInt(), dwmaximumsizehigh.toInt(), dwmaximumsizelow.toInt(), lpname.rawAddress))

public actual fun createMutexA(lpmutexattributes: ConstPtr, binitialowner: Bool, lpname: Pcstr): Handle =
    MutPtr(WindowsSysJniBridge.rustlangWindowsSysCreateMutexA(lpmutexattributes.rawAddress, binitialowner, lpname.rawAddress))

public actual fun createToolhelp32Snapshot(dwflags: CreateToolhelpSnapshotFlags, th32processid: UInt): Handle =
    MutPtr(WindowsSysJniBridge.rustlangWindowsSysCreateToolhelp32Snapshot(dwflags.toInt(), th32processid.toInt()))

public actual fun getCurrentProcess(): Handle =
    MutPtr(WindowsSysJniBridge.rustlangWindowsSysGetCurrentProcess())

public actual fun getCurrentProcessId(): UInt =
    WindowsSysJniBridge.rustlangWindowsSysGetCurrentProcessId().toUInt()

public actual fun getCurrentThread(): Handle =
    MutPtr(WindowsSysJniBridge.rustlangWindowsSysGetCurrentThread())

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    null

public actual fun loadLibraryA(lplibfilename: Pcstr): Hmodule =
    MutPtr(WindowsSysJniBridge.rustlangWindowsSysLoadLibraryA(lplibfilename.rawAddress))

public actual fun mapViewOfFile(hfilemappingobject: Handle, dwdesiredaccess: FileMap, dwfileoffsethigh: UInt, dwfileoffsetlow: UInt, dwnumberofbytestomap: ULong): MemoryMappedViewAddress =
    MemoryMappedViewAddress()

public actual fun module32FirstW(hsnapshot: Handle, lpme: MutPtr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysModule32FirstW(hsnapshot.rawAddress, lpme.rawAddress)

public actual fun module32NextW(hsnapshot: Handle, lpme: MutPtr): Bool =
    WindowsSysJniBridge.rustlangWindowsSysModule32NextW(hsnapshot.rawAddress, lpme.rawAddress)

public actual fun releaseMutex(hmutex: Handle): Bool =
    WindowsSysJniBridge.rustlangWindowsSysReleaseMutex(hmutex.rawAddress)

public actual fun rtlCaptureContext(contextrecord: MutPtr) {
    WindowsSysJniBridge.rustlangWindowsSysRtlCaptureContext(contextrecord.rawAddress)
}

public actual fun rtlLookupFunctionEntry(controlpc: ULong, imagebase: MutPtr, historytable: MutPtr): MutPtr =
    MutPtr(WindowsSysJniBridge.rustlangWindowsSysRtlLookupFunctionEntry(controlpc.toLong(), imagebase.rawAddress, historytable.rawAddress))

public actual fun rtlVirtualUnwind(handlertype: RtlVirtualUnwindHandlerType, imagebase: ULong, controlpc: ULong, functionentry: ConstPtr, contextrecord: MutPtr, handlerdata: MutPtr, establisherframe: MutPtr, contextpointers: MutPtr): ExceptionRoutine =
    null

public actual fun unmapViewOfFile(lpbaseaddress: MemoryMappedViewAddress): Bool =
    0

public actual fun waitForSingleObjectEx(hhandle: Handle, dwmilliseconds: UInt, balertable: Bool): WaitEvent =
    WindowsSysJniBridge.rustlangWindowsSysWaitForSingleObjectEx(hhandle.rawAddress, dwmilliseconds.toInt(), balertable).toUInt()

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: Pcwstr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: Pcstr, lpuseddefaultchar: MutPtr): Int =
    WindowsSysJniBridge.rustlangWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress, cchwidechar, lpmultibytestr.rawAddress, cbmultibyte, lpdefaultchar.rawAddress, lpuseddefaultchar.rawAddress)

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    WindowsSysJniBridge.rustlangWindowsSysLstrlenW(lpstring.rawAddress)
