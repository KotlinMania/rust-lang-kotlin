@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val sysPalWindowsCWindowssysWindowsSysNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first.toInt(), 0L.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cancelIo(hfile: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeHandle(hobject: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1.rawAddress.toString(), cchcount1, lpstring2.rawAddress.toString(), cchcount2, bignorecase) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename.rawAddress.toString(), lpnewfilename.rawAddress.toString(), 0L.toString(), lpdata.rawAddress.toString(), pbcancel.rawAddress.toString(), dwcopyflags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname.rawAddress.toString(), lpsecurityattributes.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes.rawAddress.toString(), bmanualreset, binitialstate, lpname.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename.rawAddress.toString(), dwdesiredaccess.toInt(), dwsharemode.toInt(), lpsecurityattributes.rawAddress.toString(), dwcreationdisposition.toInt(), dwflagsandattributes.toInt(), htemplatefile.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename.rawAddress.toString(), lpexistingfilename.rawAddress.toString(), lpsecurityattributes.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname.rawAddress.toString(), dwopenmode.toInt(), dwpipemode.toInt(), nmaxinstances.toInt(), noutbuffersize.toInt(), ninbuffersize.toInt(), ndefaulttimeout.toInt(), lpsecurityattributes.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe.rawAddress.toString(), hwritepipe.rawAddress.toString(), lppipeattributes.rawAddress.toString(), nsize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname.rawAddress.toString(), lpcommandline.rawAddress.toString(), lpprocessattributes.rawAddress.toString(), lpthreadattributes.rawAddress.toString(), binherithandles, dwcreationflags.toInt(), lpenvironment.rawAddress.toString(), lpcurrentdirectory.rawAddress.toString(), lpstartupinfo.rawAddress.toString(), lpprocessinformation.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename.rawAddress.toString(), lptargetfilename.rawAddress.toString(), dwflags.toInt()) as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes.rawAddress.toString(), dwstacksize.toString(), 0L.toString(), lpparameter.rawAddress.toString(), dwcreationflags.toInt(), lpthreadid.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes.rawAddress.toString(), lptimername.rawAddress.toString(), dwflags.toInt(), dwdesiredaccess.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice.rawAddress.toString(), dwiocontrolcode.toInt(), lpinbuffer.rawAddress.toString(), ninbuffersize.toInt(), lpoutbuffer.rawAddress.toString(), noutbuffersize.toInt(), lpbytesreturned.rawAddress.toString(), lpoverlapped.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle.rawAddress.toString(), hsourcehandle.rawAddress.toString(), htargetprocesshandle.rawAddress.toString(), lptargethandle.rawAddress.toString(), dwdesiredaccess.toInt(), binherithandle, dwoptions.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exitProcess(uexitcode: UInt): Nothing {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exitProcess")
}

public actual fun findClose(hfindfile: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename.rawAddress.toString(), finfolevelid, lpfindfiledata.rawAddress.toString(), fsearchop, lpsearchfilter.rawAddress.toString(), dwadditionalflags.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile.rawAddress.toString(), lpfindfiledata.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFlsAlloc(0L.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsFree(dwflsindex: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex.toInt(), lpflsdata.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flushFileBuffers(hfile: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags.toInt(), lpsource.rawAddress.toString(), dwmessageid.toInt(), dwlanguageid.toInt(), lpbuffer.rawAddress.toString(), nsize.toInt(), arguments.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCommandLineW(): Pwstr =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle.rawAddress.toString(), lpmode.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleOutputCP(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength.toInt(), lpbuffer.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcess(): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcessId(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThread(): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThreadId(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getEnvironmentStringsW(): Lpwch =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname.rawAddress.toString(), lpbuffer.rawAddress.toString(), nsize.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess.rawAddress.toString(), lpexitcode.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile.rawAddress.toString(), lpfileinformation.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile.rawAddress.toString(), fileinformationclass, lpfileinformation.rawAddress.toString(), dwbuffersize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile.rawAddress.toString(), lpfilesize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileType(hfile: Handle): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile.rawAddress.toString(), lpszfilepath.rawAddress.toString(), cchfilepath.toInt(), dwflags.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename.rawAddress.toString(), nbufferlength.toInt(), lpbuffer.rawAddress.toString(), lpfilepart.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name.rawAddress.toString(), namelen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getLastError(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetLastError() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getLogicalDrives(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule.rawAddress.toString(), lpfilename.rawAddress.toString(), nsize.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags.toInt(), lpmodulename.rawAddress.toString(), phmodule.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile.rawAddress.toString(), lpoverlapped.rawAddress.toString(), lpnumberofbytestransferred.rawAddress.toString(), bwait) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcessId(process: Handle): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetProcessId(process.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer.rawAddress.toString(), usize.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength.toInt(), lpbuffer.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken.rawAddress.toString(), lpprofiledir.rawAddress.toString(), lpcchsize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer.rawAddress.toString(), usize.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce.rawAddress.toString(), dwflags.toInt(), fpending.rawAddress.toString(), lpcontext.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce.rawAddress.toString(), dwflags.toInt(), lpcontext.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist.rawAddress.toString(), dwattributecount.toInt(), dwflags.toInt(), lpsize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isThreadAFiber(): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename.rawAddress.toString(), hfile.rawAddress.toString(), dwflags.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localFree(hmem: Hlocal): Hlocal =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile.rawAddress.toString(), dwflags.toInt(), dwreserved.toInt(), nnumberofbytestolocklow.toInt(), nnumberofbytestolockhigh.toInt(), lpoverlapped.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename.rawAddress.toString(), lpnewfilename.rawAddress.toString(), dwflags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage.toInt(), dwflags.toInt(), lpmultibytestr.rawAddress.toString(), cbmultibyte, lpwidecharstr.rawAddress.toString(), cchwidechar) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), allocationsize.rawAddress.toString(), fileattributes.toInt(), shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), eabuffer.rawAddress.toString(), ealength.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), shareaccess.toInt(), openoptions.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle.rawAddress.toString(), iostatusblock.rawAddress.toString(), fsinformation.rawAddress.toString(), length.toInt(), fsinformationclass) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle.rawAddress.toString(), event.rawAddress.toString(), 0L.toString(), apccontext.rawAddress.toString(), iostatusblock.rawAddress.toString(), buffer.rawAddress.toString(), length.toInt(), byteoffset.rawAddress.toString(), key.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle.rawAddress.toString(), iostatusblock.rawAddress.toString(), fileinformation.rawAddress.toString(), length.toInt(), fileinformationclass) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle.rawAddress.toString(), event.rawAddress.toString(), 0L.toString(), apccontext.rawAddress.toString(), iostatusblock.rawAddress.toString(), buffer.rawAddress.toString(), length.toInt(), byteoffset.rawAddress.toString(), key.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle.rawAddress.toString(), desiredaccess.toInt(), tokenhandle.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename.rawAddress.toString(), lptargetpath.rawAddress.toString(), ucchmax.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofcharstoread.toInt(), lpnumberofcharsread.rawAddress.toString(), pinputcontrol.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysReadFile(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestoread.toInt(), lpnumberofbytesread.rawAddress.toString(), lpoverlapped.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestoread.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer.rawAddress.toString(), randombufferlength.toInt()) as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname.rawAddress.toString(), lpvalue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename.rawAddress.toString(), dwfileattributes.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile.rawAddress.toString(), fileinformationclass, lpfileinformation.rawAddress.toString(), dwbuffersize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile.rawAddress.toString(), lidistancetomove.toString(), lpnewfilepointer.rawAddress.toString(), dwmovemethod.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile.rawAddress.toString(), lpcreationtime.rawAddress.toString(), lplastaccesstime.rawAddress.toString(), lplastwritetime.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject.rawAddress.toString(), dwmask.toInt(), dwflags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setLastError(dwerrcode: UInt) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle.toInt(), hhandle.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer.rawAddress.toString(), lpduetime.rawAddress.toString(), lperiod, 0L.toString(), lpargtocompletionroutine.rawAddress.toString(), fresume) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleep(dwmilliseconds: UInt) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable.rawAddress.toString(), srwlock.rawAddress.toString(), dwmilliseconds.toInt(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds.toInt(), balertable) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun switchToThread(): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSwitchToThread() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess.rawAddress.toString(), uexitcode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsAlloc(): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysTlsAlloc() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex.toInt(), lptlsvalue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock.rawAddress.toString()) as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock.rawAddress.toString()) as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile.rawAddress.toString(), dwfileoffsetlow.toInt(), dwfileoffsethigh.toInt(), nnumberofbytestounlocklow.toInt(), nnumberofbytestounlockhigh.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist.rawAddress.toString(), dwflags.toInt(), attribute.toString(), lpvalue.rawAddress.toString(), cbsize.toString(), lppreviousvalue.rawAddress.toString(), lpreturnsize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSACleanup(): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWSACleanup() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s.toString(), dwprocessid.toInt(), lpprotocolinfo.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSAGetLastError(): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWSARecv(s.toString(), lpbuffers.rawAddress.toString(), dwbuffercount.toInt(), lpnumberofbytesrecvd.rawAddress.toString(), lpflags.rawAddress.toString(), lpoverlapped.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWSASend(s.toString(), lpbuffers.rawAddress.toString(), dwbuffercount.toInt(), lpnumberofbytessent.rawAddress.toString(), dwflags.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWSASocketW(af, type, protocol, lpprotocolinfo.rawAddress.toString(), g.toInt(), dwflags.toInt()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested.toInt(), lpwsadata.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount.toInt(), lphandles.rawAddress.toString(), bwaitall, dwmilliseconds.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle.rawAddress.toString(), dwmilliseconds.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address.rawAddress.toString(), compareaddress.rawAddress.toString(), addresssize.toString(), dwmilliseconds.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress.toString(), cchwidechar, lpmultibytestr.rawAddress.toString(), cbmultibyte, lpdefaultchar.rawAddress.toString(), lpuseddefaultchar.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofcharstowrite.toInt(), lpnumberofcharswritten.rawAddress.toString(), lpreserved.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestowrite.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysAccept(s.toString(), addr.rawAddress.toString(), addrlen.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysBind(s.toString(), name.rawAddress.toString(), namelen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closesocket(s: Socket): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysClosesocket(s.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysConnect(s.toString(), name.rawAddress.toString(), namelen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename.rawAddress.toString(), pservicename.rawAddress.toString(), phints.rawAddress.toString(), ppresult.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetpeername(s.toString(), name.rawAddress.toString(), namelen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetsockname(s.toString(), name.rawAddress.toString(), namelen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysGetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s.toString(), cmd, argp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(s: Socket, backlog: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysListen(s.toString(), backlog) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysRecv(s.toString(), buf.rawAddress.toString(), len, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysRecvfrom(s.toString(), buf.rawAddress.toString(), len, flags, from.rawAddress.toString(), fromlen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSelect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), exceptfds.rawAddress.toString(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSend(s.toString(), buf.rawAddress.toString(), len, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSendto(s.toString(), buf.rawAddress.toString(), len, flags, to.rawAddress.toString(), tolen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysSetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shutdown(s: Socket, how: Int): Int =
    if (sysPalWindowsCWindowssysWindowsSysNapiAddon != null) (sysPalWindowsCWindowssysWindowsSysNapiAddon.stdSysPalWindowsCWindowssysWindowsSysShutdown(s.toString(), how) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
