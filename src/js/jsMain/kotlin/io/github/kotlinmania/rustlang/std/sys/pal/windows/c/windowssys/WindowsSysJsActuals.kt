@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val stdSysPalWindowsCWindowssysWindowsSysNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first.toInt(), 0L.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cancelIo(hfile: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closeHandle(hobject: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1.rawAddress.toString(), cchcount1, lpstring2.rawAddress.toString(), cchcount2, bignorecase) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename.rawAddress.toString(), lpnewfilename.rawAddress.toString(), 0L.toString(), lpdata.rawAddress.toString(), pbcancel.rawAddress.toString(), dwcopyflags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname.rawAddress.toString(), lpsecurityattributes.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes.rawAddress.toString(), bmanualreset, binitialstate, lpname.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename.rawAddress.toString(), dwdesiredaccess.toInt(), dwsharemode.toInt(), lpsecurityattributes.rawAddress.toString(), dwcreationdisposition.toInt(), dwflagsandattributes.toInt(), htemplatefile.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename.rawAddress.toString(), lpexistingfilename.rawAddress.toString(), lpsecurityattributes.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname.rawAddress.toString(), dwopenmode.toInt(), dwpipemode.toInt(), nmaxinstances.toInt(), noutbuffersize.toInt(), ninbuffersize.toInt(), ndefaulttimeout.toInt(), lpsecurityattributes.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe.rawAddress.toString(), hwritepipe.rawAddress.toString(), lppipeattributes.rawAddress.toString(), nsize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname.rawAddress.toString(), lpcommandline.rawAddress.toString(), lpprocessattributes.rawAddress.toString(), lpthreadattributes.rawAddress.toString(), binherithandles, dwcreationflags.toInt(), lpenvironment.rawAddress.toString(), lpcurrentdirectory.rawAddress.toString(), lpstartupinfo.rawAddress.toString(), lpprocessinformation.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename.rawAddress.toString(), lptargetfilename.rawAddress.toString(), dwflags.toInt()) as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes.rawAddress.toString(), dwstacksize.toString(), 0L.toString(), lpparameter.rawAddress.toString(), dwcreationflags.toInt(), lpthreadid.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes.rawAddress.toString(), lptimername.rawAddress.toString(), dwflags.toInt(), dwdesiredaccess.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice.rawAddress.toString(), dwiocontrolcode.toInt(), lpinbuffer.rawAddress.toString(), ninbuffersize.toInt(), lpoutbuffer.rawAddress.toString(), noutbuffersize.toInt(), lpbytesreturned.rawAddress.toString(), lpoverlapped.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle.rawAddress.toString(), hsourcehandle.rawAddress.toString(), htargetprocesshandle.rawAddress.toString(), lptargethandle.rawAddress.toString(), dwdesiredaccess.toInt(), binherithandle, dwoptions.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun exitProcess(uexitcode: UInt): Nothing {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
    error("Unreachable noreturn C function exitProcess")
}

public actual fun findClose(hfindfile: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename.rawAddress.toString(), finfolevelid, lpfindfiledata.rawAddress.toString(), fsearchop, lpsearchfilter.rawAddress.toString(), dwadditionalflags.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile.rawAddress.toString(), lpfindfiledata.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(0L.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsFree(dwflsindex: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex.toInt(), lpflsdata.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun flushFileBuffers(hfile: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags.toInt(), lpsource.rawAddress.toString(), dwmessageid.toInt(), dwlanguageid.toInt(), lpbuffer.rawAddress.toString(), nsize.toInt(), arguments.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCommandLineW(): Pwstr =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle.rawAddress.toString(), lpmode.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getConsoleOutputCP(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength.toInt(), lpbuffer.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcess(): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentProcessId(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThread(): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getCurrentThreadId(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getEnvironmentStringsW(): Lpwch =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW() as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname.rawAddress.toString(), lpbuffer.rawAddress.toString(), nsize.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess.rawAddress.toString(), lpexitcode.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile.rawAddress.toString(), lpfileinformation.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile.rawAddress.toString(), fileinformationclass, lpfileinformation.rawAddress.toString(), dwbuffersize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile.rawAddress.toString(), lpfilesize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFileType(hfile: Handle): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile.rawAddress.toString(), lpszfilepath.rawAddress.toString(), cchfilepath.toInt(), dwflags.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename.rawAddress.toString(), nbufferlength.toInt(), lpbuffer.rawAddress.toString(), lpfilepart.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name.rawAddress.toString(), namelen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getLastError(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getLogicalDrives(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule.rawAddress.toString(), lpfilename.rawAddress.toString(), nsize.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags.toInt(), lpmodulename.rawAddress.toString(), phmodule.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile.rawAddress.toString(), lpoverlapped.rawAddress.toString(), lpnumberofbytestransferred.rawAddress.toString(), bwait) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) null else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getProcessId(process: Handle): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId(process.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer.rawAddress.toString(), usize.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength.toInt(), lpbuffer.rawAddress.toString()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken.rawAddress.toString(), lpprofiledir.rawAddress.toString(), lpcchsize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer.rawAddress.toString(), usize.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce.rawAddress.toString(), dwflags.toInt(), fpending.rawAddress.toString(), lpcontext.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce.rawAddress.toString(), dwflags.toInt(), lpcontext.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist.rawAddress.toString(), dwattributecount.toInt(), dwflags.toInt(), lpsize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun isThreadAFiber(): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename.rawAddress.toString(), hfile.rawAddress.toString(), dwflags.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun localFree(hmem: Hlocal): Hlocal =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile.rawAddress.toString(), dwflags.toInt(), dwreserved.toInt(), nnumberofbytestolocklow.toInt(), nnumberofbytestolockhigh.toInt(), lpoverlapped.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename.rawAddress.toString(), lpnewfilename.rawAddress.toString(), dwflags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage.toInt(), dwflags.toInt(), lpmultibytestr.rawAddress.toString(), cbmultibyte, lpwidecharstr.rawAddress.toString(), cchwidechar) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), allocationsize.rawAddress.toString(), fileattributes.toInt(), shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), eabuffer.rawAddress.toString(), ealength.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle.rawAddress.toString(), desiredaccess.toInt(), objectattributes.rawAddress.toString(), iostatusblock.rawAddress.toString(), shareaccess.toInt(), openoptions.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle.rawAddress.toString(), iostatusblock.rawAddress.toString(), fsinformation.rawAddress.toString(), length.toInt(), fsinformationclass) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle.rawAddress.toString(), event.rawAddress.toString(), 0L.toString(), apccontext.rawAddress.toString(), iostatusblock.rawAddress.toString(), buffer.rawAddress.toString(), length.toInt(), byteoffset.rawAddress.toString(), key.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle.rawAddress.toString(), iostatusblock.rawAddress.toString(), fileinformation.rawAddress.toString(), length.toInt(), fileinformationclass) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle.rawAddress.toString(), event.rawAddress.toString(), 0L.toString(), apccontext.rawAddress.toString(), iostatusblock.rawAddress.toString(), buffer.rawAddress.toString(), length.toInt(), byteoffset.rawAddress.toString(), key.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle.rawAddress.toString(), desiredaccess.toInt(), tokenhandle.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename.rawAddress.toString(), lptargetpath.rawAddress.toString(), ucchmax.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofcharstoread.toInt(), lpnumberofcharsread.rawAddress.toString(), pinputcontrol.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestoread.toInt(), lpnumberofbytesread.rawAddress.toString(), lpoverlapped.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestoread.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer.rawAddress.toString(), randombufferlength.toInt()) as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname.rawAddress.toString(), lpvalue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename.rawAddress.toString(), dwfileattributes.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile.rawAddress.toString(), fileinformationclass, lpfileinformation.rawAddress.toString(), dwbuffersize.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile.rawAddress.toString(), lidistancetomove.toString(), lpnewfilepointer.rawAddress.toString(), dwmovemethod.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile.rawAddress.toString(), lpcreationtime.rawAddress.toString(), lplastaccesstime.rawAddress.toString(), lplastwritetime.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject.rawAddress.toString(), dwmask.toInt(), dwflags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setLastError(dwerrcode: UInt) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle.toInt(), hhandle.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer.rawAddress.toString(), lpduetime.rawAddress.toString(), lperiod, 0L.toString(), lpargtocompletionroutine.rawAddress.toString(), fresume) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleep(dwmilliseconds: UInt) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds.toInt())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable.rawAddress.toString(), srwlock.rawAddress.toString(), dwmilliseconds.toInt(), flags.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds.toInt(), balertable) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun switchToThread(): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess.rawAddress.toString(), uexitcode.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsAlloc(): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc() as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) MutPtr((stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex.toInt()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex.toInt(), lptlsvalue.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock.rawAddress.toString()) as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock.rawAddress.toString()) as Int).toUByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile.rawAddress.toString(), dwfileoffsetlow.toInt(), dwfileoffsethigh.toInt(), nnumberofbytestounlocklow.toInt(), nnumberofbytestounlockhigh.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist.rawAddress.toString(), dwflags.toInt(), attribute.toString(), lpvalue.rawAddress.toString(), cbsize.toString(), lppreviousvalue.rawAddress.toString(), lpreturnsize.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSACleanup(): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s.toString(), dwprocessid.toInt(), lpprotocolinfo.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSAGetLastError(): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError() as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv(s.toString(), lpbuffers.rawAddress.toString(), dwbuffercount.toInt(), lpnumberofbytesrecvd.rawAddress.toString(), lpflags.rawAddress.toString(), lpoverlapped.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend(s.toString(), lpbuffers.rawAddress.toString(), dwbuffercount.toInt(), lpnumberofbytessent.rawAddress.toString(), dwflags.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW(af, type, protocol, lpprotocolinfo.rawAddress.toString(), g.toInt(), dwflags.toInt()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested.toInt(), lpwsadata.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount.toInt(), lphandles.rawAddress.toString(), bwaitall, dwmilliseconds.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle.rawAddress.toString(), dwmilliseconds.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address.rawAddress.toString(), compareaddress.rawAddress.toString(), addresssize.toString(), dwmilliseconds.toInt()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress.toString(), cchwidechar, lpmultibytestr.rawAddress.toString(), cbmultibyte, lpdefaultchar.rawAddress.toString(), lpuseddefaultchar.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofcharstowrite.toInt(), lpnumberofcharswritten.rawAddress.toString(), lpreserved.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile.rawAddress.toString(), lpbuffer.rawAddress.toString(), nnumberofbytestowrite.toInt(), lpoverlapped.rawAddress.toString(), 0L.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysAccept(s.toString(), addr.rawAddress.toString(), addrlen.rawAddress.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysBind(s.toString(), name.rawAddress.toString(), namelen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun closesocket(s: Socket): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(s.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysConnect(s.toString(), name.rawAddress.toString(), namelen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) {
        stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename.rawAddress.toString(), pservicename.rawAddress.toString(), phints.rawAddress.toString(), ppresult.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername(s.toString(), name.rawAddress.toString(), namelen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname(s.toString(), name.rawAddress.toString(), namelen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s.toString(), cmd, argp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun listen(s: Socket, backlog: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysListen(s.toString(), backlog) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysRecv(s.toString(), buf.rawAddress.toString(), len, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom(s.toString(), buf.rawAddress.toString(), len, flags, from.rawAddress.toString(), fromlen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSelect(nfds, readfds.rawAddress.toString(), writefds.rawAddress.toString(), exceptfds.rawAddress.toString(), timeout.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSend(s.toString(), buf.rawAddress.toString(), len, flags) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSendto(s.toString(), buf.rawAddress.toString(), len, flags, to.rawAddress.toString(), tolen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt(s.toString(), level, optname, optval.rawAddress.toString(), optlen) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun shutdown(s: Socket, how: Int): Int =
    if (stdSysPalWindowsCWindowssysWindowsSysNapiAddon != null) (stdSysPalWindowsCWindowssysWindowsSysNapiAddon.rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(s.toString(), how) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
