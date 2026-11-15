@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive")
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared")
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler")

public actual fun cancelIo(hfile: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo")

public actual fun closeHandle(hobject: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle")

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal")

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW")

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW")

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW")

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW")

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW")

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW")

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe")

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW")

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW")

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread")

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW")

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW")

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList")
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl")

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode: Int): Unit

public actual fun exitProcess(uexitcode: UInt): Nothing {
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode.toInt())
    error("C noreturn function rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess returned")
}

public actual fun findClose(hfindfile: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose")

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW")

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW")

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex: Int): Int

public actual fun flsFree(dwflsindex: UInt): Bool =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex.toInt()).toInt()

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue")

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue")

public actual fun flushFileBuffers(hfile: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers")

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW")

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber: Int): Int

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber.toInt()).toUInt()

public actual fun getCommandLineW(): Pwstr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW")

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(): Int

public actual fun getConsoleOutputCP(): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP().toUInt()

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW")

public actual fun getCurrentProcess(): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(): Int

public actual fun getCurrentProcessId(): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId().toUInt()

public actual fun getCurrentThread(): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(): Int

public actual fun getCurrentThreadId(): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId().toUInt()

public actual fun getEnvironmentStringsW(): Lpwch =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW")

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW")

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess")

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW")

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle")

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx")

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx")

public actual fun getFileType(hfile: Handle): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType")

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW")

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW")

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError(): Int

public actual fun getLastError(): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError().toUInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(): Int

public actual fun getLogicalDrives(): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives().toUInt()

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW")

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA")

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW")

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW")

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcAddress")

public actual fun getProcessId(process: Handle): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId")

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle")

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW")

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo")
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime")
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime")
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW")

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW")

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW")

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize")

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete")

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(): Int

public actual fun isThreadAFiber(): Bool =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber().toInt()

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA")

public actual fun localFree(hmem: Hlocal): Hlocal =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree")

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx")

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW")

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar")

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile")

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile")

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile")

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile")

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile")

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile")

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken")

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW")

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter")

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency")

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW")

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile")

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx")

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive")
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared")
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW")

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status: Int): Int

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status).toUInt()

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW")

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW")

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW")

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle")

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx")

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime")

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode: Int): Unit

public actual fun setLastError(dwerrcode: UInt): Unit =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode.toInt())

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle")

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee")

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysSleep")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds: Int): Unit

public actual fun sleep(dwmilliseconds: UInt): Unit =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds.toInt())

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds: Int, balertable: Int): Int

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds.toInt(), balertable).toUInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread(): Int

public actual fun switchToThread(): Bool =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread().toInt()

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc(): Int

public actual fun tlsAlloc(): UInt =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc().toUInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex: Int): Int

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex.toInt()).toInt()

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue")

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue")

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive")

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared")

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile")

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup(): Int

public actual fun wSACleanup(): Int =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup().toInt()

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(): Int

public actual fun wSAGetLastError(): Int =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError().toInt()

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv")

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend")

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW")

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup")

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects")

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject")

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress")

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable")
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll")
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle")
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable")
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte")

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW")

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysAccept")

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysBind")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(s: Long): Int

public actual fun closesocket(s: Socket): Int =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(s.toLong()).toInt()

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysConnect")

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo")
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo")

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername")

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname")

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt")

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysListen")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysListen(s: Long, backlog: Int): Int

public actual fun listen(s: Socket, backlog: Int): Int =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysListen(s.toLong(), backlog).toInt()

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW")

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysRecv")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom")

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSelect")

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSend")

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSendto")

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt")

@kotlin.wasm.WasmImport("kmcore", "rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown")
private external fun wasiRustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(s: Long, how: Int): Int

public actual fun shutdown(s: Socket, how: Int): Int =
    wasiRustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(s.toLong(), how).toInt()
