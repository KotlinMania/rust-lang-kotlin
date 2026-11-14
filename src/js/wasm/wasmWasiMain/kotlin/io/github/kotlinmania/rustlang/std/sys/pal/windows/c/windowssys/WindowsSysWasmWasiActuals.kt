@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module std.
public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive")
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared")
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler")

public actual fun cancelIo(hfile: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCancelIo")

public actual fun closeHandle(hobject: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCloseHandle")

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal")

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCopyFileExW")

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW")

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateEventW")

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateFileW")

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW")

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW")

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreatePipe")

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateProcessW")

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW")

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateThread")

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW")

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysDeleteFileW")

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList")
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl")

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysExitProcess")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode: Int): Unit

public actual fun exitProcess(uexitcode: UInt): Nothing {
    wasiStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode.toInt())
    error("C noreturn function stdSysPalWindowsCWindowssysWindowsSysExitProcess returned")
}

public actual fun findClose(hfindfile: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFindClose")

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW")

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFindNextFileW")

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFlsAlloc")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysFlsFree")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex: Int): Int

public actual fun flsFree(dwflsindex: UInt): Bool =
    wasiStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex.toInt()).toInt()

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFlsGetValue")

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFlsSetValue")

public actual fun flushFileBuffers(hfile: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers")

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFormatMessageW")

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber: Int): Int

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber.toInt()).toUInt()

public actual fun getCommandLineW(): Pwstr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW")

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(): Int

public actual fun getConsoleOutputCP(): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP().toUInt()

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW")

public actual fun getCurrentProcess(): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(): Int

public actual fun getCurrentProcessId(): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId().toUInt()

public actual fun getCurrentThread(): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(): Int

public actual fun getCurrentThreadId(): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId().toUInt()

public actual fun getEnvironmentStringsW(): Lpwch =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW")

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW")

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess")

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW")

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle")

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx")

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx")

public actual fun getFileType(hfile: Handle): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetFileType")

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW")

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW")

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetHostNameW")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysGetLastError")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysGetLastError(): Int

public actual fun getLastError(): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysGetLastError().toUInt()

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(): Int

public actual fun getLogicalDrives(): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives().toUInt()

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW")

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA")

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW")

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW")

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetProcAddress")

public actual fun getProcessId(process: Handle): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetProcessId")

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetStdHandle")

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW")

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo")
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime")
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime")
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetTempPathW")

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW")

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW")

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize")

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete")

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(): Int

public actual fun isThreadAFiber(): Bool =
    wasiStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber().toInt()

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA")

public actual fun localFree(hmem: Hlocal): Hlocal =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysLocalFree")

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysLockFileEx")

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysMoveFileExW")

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar")

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysNtCreateFile")

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysNtOpenFile")

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile")

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysNtReadFile")

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile")

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysNtWriteFile")

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken")

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW")

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter")

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency")

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysReadConsoleW")

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysReadFile")

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysReadFileEx")

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive")
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared")
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW")

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status: Int): Int

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status).toUInt()

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW")

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW")

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW")

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle")

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx")

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetFileTime")

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysSetLastError")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode: Int): Unit

public actual fun setLastError(dwerrcode: UInt): Unit =
    wasiStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode.toInt())

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetStdHandle")

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee")

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysSleep")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds: Int): Unit

public actual fun sleep(dwmilliseconds: UInt): Unit =
    wasiStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds.toInt())

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysSleepEx")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds: Int, balertable: Int): Int

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds.toInt(), balertable).toUInt()

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysSwitchToThread")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysSwitchToThread(): Int

public actual fun switchToThread(): Bool =
    wasiStdSysPalWindowsCWindowssysWindowsSysSwitchToThread().toInt()

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysTerminateProcess")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysTlsAlloc")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysTlsAlloc(): Int

public actual fun tlsAlloc(): UInt =
    wasiStdSysPalWindowsCWindowssysWindowsSysTlsAlloc().toUInt()

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysTlsFree")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex: Int): Int

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    wasiStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex.toInt()).toInt()

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysTlsGetValue")

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysTlsSetValue")

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive")

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared")

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysUnlockFile")

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysWSACleanup")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysWSACleanup(): Int

public actual fun wSACleanup(): Int =
    wasiStdSysPalWindowsCWindowssysWindowsSysWSACleanup().toInt()

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(): Int

public actual fun wSAGetLastError(): Int =
    wasiStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError().toInt()

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWSARecv")

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWSASend")

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWSASocketW")

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWSAStartup")

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects")

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject")

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress")

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable")
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll")
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle")
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable")
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte")

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW")

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysWriteFileEx")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysAccept")

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysBind")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysClosesocket")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysClosesocket(s: Long): Int

public actual fun closesocket(s: Socket): Int =
    wasiStdSysPalWindowsCWindowssysWindowsSysClosesocket(s.toLong()).toInt()

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysConnect")

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo")
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo")

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetpeername")

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetsockname")

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysGetsockopt")

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysIoctlsocket")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysListen")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysListen(s: Long, backlog: Int): Int

public actual fun listen(s: Socket, backlog: Int): Int =
    wasiStdSysPalWindowsCWindowssysWindowsSysListen(s.toLong(), backlog).toInt()

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysLstrlenW")

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysRecv")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysRecvfrom")

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSelect")

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSend")

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSendto")

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalWindowsCWindowssysWindowsSysSetsockopt")

@kotlin.wasm.WasmImport("std", "stdSysPalWindowsCWindowssysWindowsSysShutdown")
private external fun wasiStdSysPalWindowsCWindowssysWindowsSysShutdown(s: Long, how: Int): Int

public actual fun shutdown(s: Socket, how: Int): Int =
    wasiStdSysPalWindowsCWindowssysWindowsSysShutdown(s.toLong(), how).toInt()
