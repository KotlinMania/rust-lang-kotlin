@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object SysPalWindowsCWindowssysWindowsSysJniBridge {
    init {
        loadNativeLibrary()
    }

    private fun loadNativeLibrary() {
        val osRaw = System.getProperty("os.name", "").lowercase()
        val archRaw = System.getProperty("os.arch", "").lowercase()
        val hostPlatform =
            when {
                osRaw.contains("win") -> "windows"
                osRaw.contains("mac") || osRaw.contains("darwin") -> "macos"
                else -> "linux"
            }
        val hostArch =
            when {
                archRaw == "aarch64" || archRaw == "arm64" -> "aarch64"
                archRaw == "amd64" || archRaw == "x86_64" -> "x86_64"
                else -> archRaw
            }
        val libFileName =
            when (hostPlatform) {
                "windows" -> "kmcore_jni.dll"
                "macos" -> "libkmcore_jni.dylib"
                else -> "libkmcore_jni.so"
            }
        val pathSep = System.getProperty("path.separator", ":")
        val libPathProp = System.getProperty("java.library.path", "")
        for (dirEntry in libPathProp.split(pathSep)) {
            if (dirEntry.isEmpty()) continue
            val candidate = java.io.File(dirEntry, libFileName)
            if (candidate.isFile) {
                System.load(candidate.absolutePath)
                return
            }
        }
        val resourcePath = "io/github/kotlinmania/rustlang/std/native/$hostPlatform-$hostArch/$libFileName"
        val stream = SysPalWindowsCWindowssysWindowsSysJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
        if (stream != null) {
            val suffix = "." + libFileName.substringAfterLast('.')
            val extracted = java.nio.file.Files.createTempFile("kmcore_jni-", suffix).toFile()
            extracted.deleteOnExit()
            stream.use { input ->
                extracted.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            System.load(extracted.absolutePath)
            return
        }
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first: Int, handler: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1: Long, cchcount1: Int, lpstring2: Long, cchcount2: Int, bignorecase: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename: Long, lpnewfilename: Long, lpprogressroutine: Long, lpdata: Long, pbcancel: Long, dwcopyflags: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname: Long, lpsecurityattributes: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes: Long, bmanualreset: Int, binitialstate: Int, lpname: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename: Long, dwdesiredaccess: Int, dwsharemode: Int, lpsecurityattributes: Long, dwcreationdisposition: Int, dwflagsandattributes: Int, htemplatefile: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename: Long, lpexistingfilename: Long, lpsecurityattributes: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname: Long, dwopenmode: Int, dwpipemode: Int, nmaxinstances: Int, noutbuffersize: Int, ninbuffersize: Int, ndefaulttimeout: Int, lpsecurityattributes: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe: Long, hwritepipe: Long, lppipeattributes: Long, nsize: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname: Long, lpcommandline: Long, lpprocessattributes: Long, lpthreadattributes: Long, binherithandles: Int, dwcreationflags: Int, lpenvironment: Long, lpcurrentdirectory: Long, lpstartupinfo: Long, lpprocessinformation: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename: Long, lptargetfilename: Long, dwflags: Int): Byte

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes: Long, dwstacksize: Long, lpstartaddress: Long, lpparameter: Long, dwcreationflags: Int, lpthreadid: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes: Long, lptimername: Long, dwflags: Int, dwdesiredaccess: Int): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice: Long, dwiocontrolcode: Int, lpinbuffer: Long, ninbuffersize: Int, lpoutbuffer: Long, noutbuffersize: Int, lpbytesreturned: Long, lpoverlapped: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle: Long, hsourcehandle: Long, htargetprocesshandle: Long, lptargethandle: Long, dwdesiredaccess: Int, binherithandle: Int, dwoptions: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode: Int)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename: Long, finfolevelid: Int, lpfindfiledata: Long, fsearchop: Int, lpsearchfilter: Long, dwadditionalflags: Int): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile: Long, lpfindfiledata: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFlsAlloc(lpcallback: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex: Int): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex: Int, lpflsdata: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags: Int, lpsource: Long, dwmessageid: Int, dwlanguageid: Int, lpbuffer: Long, nsize: Int, arguments: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber: Short): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW(): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle: Long, lpmode: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength: Int, lpbuffer: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess(): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread(): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW(): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname: Long, lpbuffer: Long, nsize: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess: Long, lpexitcode: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile: Long, lpfileinformation: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile: Long, fileinformationclass: Int, lpfileinformation: Long, dwbuffersize: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile: Long, lpfilesize: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile: Long, lpszfilepath: Long, cchfilepath: Int, dwflags: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename: Long, nbufferlength: Int, lpbuffer: Long, lpfilepart: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name: Long, namelen: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetLastError(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule: Long, lpfilename: Long, nsize: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags: Int, lpmodulename: Long, phmodule: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile: Long, lpoverlapped: Long, lpnumberofbytestransferred: Long, bwait: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetProcAddress(hmodule: Long, lpprocname: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetProcessId(process: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle: Int): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer: Long, usize: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength: Int, lpbuffer: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken: Long, lpprofiledir: Long, lpcchsize: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer: Long, usize: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce: Long, dwflags: Int, fpending: Long, lpcontext: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce: Long, dwflags: Int, lpcontext: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist: Long, dwattributecount: Int, dwflags: Int, lpsize: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename: Long, hfile: Long, dwflags: Int): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile: Long, dwflags: Int, dwreserved: Int, nnumberofbytestolocklow: Int, nnumberofbytestolockhigh: Int, lpoverlapped: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename: Long, lpnewfilename: Long, dwflags: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage: Int, dwflags: Int, lpmultibytestr: Long, cbmultibyte: Int, lpwidecharstr: Long, cchwidechar: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle: Long, desiredaccess: Int, objectattributes: Long, iostatusblock: Long, allocationsize: Long, fileattributes: Int, shareaccess: Int, createdisposition: Int, createoptions: Int, eabuffer: Long, ealength: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle: Long, desiredaccess: Int, objectattributes: Long, iostatusblock: Long, shareaccess: Int, openoptions: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle: Long, iostatusblock: Long, fsinformation: Long, length: Int, fsinformationclass: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle: Long, event: Long, apcroutine: Long, apccontext: Long, iostatusblock: Long, buffer: Long, length: Int, byteoffset: Long, key: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle: Long, iostatusblock: Long, fileinformation: Long, length: Int, fileinformationclass: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle: Long, event: Long, apcroutine: Long, apccontext: Long, iostatusblock: Long, buffer: Long, length: Int, byteoffset: Long, key: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle: Long, desiredaccess: Int, tokenhandle: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename: Long, lptargetpath: Long, ucchmax: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput: Long, lpbuffer: Long, nnumberofcharstoread: Int, lpnumberofcharsread: Long, pinputcontrol: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysReadFile(hfile: Long, lpbuffer: Long, nnumberofbytestoread: Int, lpnumberofbytesread: Long, lpoverlapped: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile: Long, lpbuffer: Long, nnumberofbytestoread: Int, lpoverlapped: Long, lpcompletionroutine: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer: Long, randombufferlength: Int): Byte

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname: Long, lpvalue: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename: Long, dwfileattributes: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile: Long, fileinformationclass: Int, lpfileinformation: Long, dwbuffersize: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile: Long, lidistancetomove: Long, lpnewfilepointer: Long, dwmovemethod: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile: Long, lpcreationtime: Long, lplastaccesstime: Long, lplastwritetime: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject: Long, dwmask: Int, dwflags: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode: Int)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle: Int, hhandle: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer: Long, lpduetime: Long, lperiod: Int, pfncompletionroutine: Long, lpargtocompletionroutine: Long, fresume: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds: Int)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable: Long, srwlock: Long, dwmilliseconds: Int, flags: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds: Int, balertable: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSwitchToThread(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess: Long, uexitcode: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysTlsAlloc(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex: Int): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex: Int, lptlsvalue: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock: Long): Byte

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock: Long): Byte

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile: Long, dwfileoffsetlow: Int, dwfileoffsethigh: Int, nnumberofbytestounlocklow: Int, nnumberofbytestounlockhigh: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist: Long, dwflags: Int, attribute: Long, lpvalue: Long, cbsize: Long, lppreviousvalue: Long, lpreturnsize: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWSACleanup(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s: Long, dwprocessid: Int, lpprotocolinfo: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWSARecv(s: Long, lpbuffers: Long, dwbuffercount: Int, lpnumberofbytesrecvd: Long, lpflags: Long, lpoverlapped: Long, lpcompletionroutine: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWSASend(s: Long, lpbuffers: Long, dwbuffercount: Int, lpnumberofbytessent: Long, dwflags: Int, lpoverlapped: Long, lpcompletionroutine: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: Long, g: Int, dwflags: Int): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested: Short, lpwsadata: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount: Int, lphandles: Long, bwaitall: Int, dwmilliseconds: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle: Long, dwmilliseconds: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address: Long, compareaddress: Long, addresssize: Long, dwmilliseconds: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage: Int, dwflags: Int, lpwidecharstr: Long, cchwidechar: Int, lpmultibytestr: Long, cbmultibyte: Int, lpdefaultchar: Long, lpuseddefaultchar: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput: Long, lpbuffer: Long, nnumberofcharstowrite: Int, lpnumberofcharswritten: Long, lpreserved: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile: Long, lpbuffer: Long, nnumberofbytestowrite: Int, lpoverlapped: Long, lpcompletionroutine: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysAccept(s: Long, addr: Long, addrlen: Long): Long

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysBind(s: Long, name: Long, namelen: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysClosesocket(s: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysConnect(s: Long, name: Long, namelen: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo: Long)

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename: Long, pservicename: Long, phints: Long, ppresult: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetpeername(s: Long, name: Long, namelen: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetsockname(s: Long, name: Long, namelen: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysGetsockopt(s: Long, level: Int, optname: Int, optval: Long, optlen: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s: Long, cmd: Int, argp: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysListen(s: Long, backlog: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysRecv(s: Long, buf: Long, len: Int, flags: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysRecvfrom(s: Long, buf: Long, len: Int, flags: Int, from: Long, fromlen: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSelect(nfds: Int, readfds: Long, writefds: Long, exceptfds: Long, timeout: Long): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSend(s: Long, buf: Long, len: Int, flags: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSendto(s: Long, buf: Long, len: Int, flags: Int, to: Long, tolen: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysSetsockopt(s: Long, level: Int, optname: Int, optval: Long, optlen: Int): Int

    @JvmStatic
    external fun stdSysPalWindowsCWindowssysWindowsSysShutdown(s: Long, how: Int): Int
}

public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock.rawAddress)
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock.rawAddress)
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first.toInt(), 0L))

public actual fun cancelIo(hfile: Handle): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile.rawAddress)

public actual fun closeHandle(hobject: Handle): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject.rawAddress)

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1.rawAddress, cchcount1, lpstring2.rawAddress, cchcount2, bignorecase)

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename.rawAddress, lpnewfilename.rawAddress, 0L, lpdata.rawAddress, pbcancel.rawAddress, dwcopyflags.toInt())

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname.rawAddress, lpsecurityattributes.rawAddress)

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes.rawAddress, bmanualreset, binitialstate, lpname.rawAddress))

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename.rawAddress, dwdesiredaccess.toInt(), dwsharemode.toInt(), lpsecurityattributes.rawAddress, dwcreationdisposition.toInt(), dwflagsandattributes.toInt(), htemplatefile.rawAddress))

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename.rawAddress, lpexistingfilename.rawAddress, lpsecurityattributes.rawAddress)

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname.rawAddress, dwopenmode.toInt(), dwpipemode.toInt(), nmaxinstances.toInt(), noutbuffersize.toInt(), ninbuffersize.toInt(), ndefaulttimeout.toInt(), lpsecurityattributes.rawAddress))

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe.rawAddress, hwritepipe.rawAddress, lppipeattributes.rawAddress, nsize.toInt())

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname.rawAddress, lpcommandline.rawAddress, lpprocessattributes.rawAddress, lpthreadattributes.rawAddress, binherithandles, dwcreationflags.toInt(), lpenvironment.rawAddress, lpcurrentdirectory.rawAddress, lpstartupinfo.rawAddress, lpprocessinformation.rawAddress)

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename.rawAddress, lptargetfilename.rawAddress, dwflags.toInt()).toUByte()

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes.rawAddress, dwstacksize.toLong(), 0L, lpparameter.rawAddress, dwcreationflags.toInt(), lpthreadid.rawAddress))

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes.rawAddress, lptimername.rawAddress, dwflags.toInt(), dwdesiredaccess.toInt()))

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename.rawAddress)

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist.rawAddress)
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice.rawAddress, dwiocontrolcode.toInt(), lpinbuffer.rawAddress, ninbuffersize.toInt(), lpoutbuffer.rawAddress, noutbuffersize.toInt(), lpbytesreturned.rawAddress, lpoverlapped.rawAddress)

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle.rawAddress, hsourcehandle.rawAddress, htargetprocesshandle.rawAddress, lptargethandle.rawAddress, dwdesiredaccess.toInt(), binherithandle, dwoptions.toInt())

public actual fun exitProcess(uexitcode: UInt): Nothing {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode.toInt())
    error("Unreachable noreturn C function exitProcess")
}

public actual fun findClose(hfindfile: Handle): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile.rawAddress)

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename.rawAddress, finfolevelid, lpfindfiledata.rawAddress, fsearchop, lpsearchfilter.rawAddress, dwadditionalflags.toInt()))

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile.rawAddress, lpfindfiledata.rawAddress)

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFlsAlloc(0L).toUInt()

public actual fun flsFree(dwflsindex: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex.toInt())

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex.toInt()))

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex.toInt(), lpflsdata.rawAddress)

public actual fun flushFileBuffers(hfile: Handle): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile.rawAddress)

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags.toInt(), lpsource.rawAddress, dwmessageid.toInt(), dwlanguageid.toInt(), lpbuffer.rawAddress, nsize.toInt(), arguments.rawAddress).toUInt()

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv.rawAddress)

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber.toShort()).toUInt()

public actual fun getCommandLineW(): Pwstr =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW())

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle.rawAddress, lpmode.rawAddress)

public actual fun getConsoleOutputCP(): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP().toUInt()

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength.toInt(), lpbuffer.rawAddress).toUInt()

public actual fun getCurrentProcess(): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess())

public actual fun getCurrentProcessId(): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId().toUInt()

public actual fun getCurrentThread(): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread())

public actual fun getCurrentThreadId(): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId().toUInt()

public actual fun getEnvironmentStringsW(): Lpwch =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW())

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname.rawAddress, lpbuffer.rawAddress, nsize.toInt()).toUInt()

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess.rawAddress, lpexitcode.rawAddress)

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename.rawAddress).toUInt()

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile.rawAddress, lpfileinformation.rawAddress)

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile.rawAddress, fileinformationclass, lpfileinformation.rawAddress, dwbuffersize.toInt())

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile.rawAddress, lpfilesize.rawAddress)

public actual fun getFileType(hfile: Handle): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile.rawAddress).toUInt()

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile.rawAddress, lpszfilepath.rawAddress, cchfilepath.toInt(), dwflags.toInt()).toUInt()

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename.rawAddress, nbufferlength.toInt(), lpbuffer.rawAddress, lpfilepart.rawAddress).toUInt()

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name.rawAddress, namelen)

public actual fun getLastError(): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetLastError().toUInt()

public actual fun getLogicalDrives(): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives().toUInt()

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule.rawAddress, lpfilename.rawAddress, nsize.toInt()).toUInt()

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename.rawAddress))

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags.toInt(), lpmodulename.rawAddress, phmodule.rawAddress)

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename.rawAddress))

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile.rawAddress, lpoverlapped.rawAddress, lpnumberofbytestransferred.rawAddress, bwait)

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    null

public actual fun getProcessId(process: Handle): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetProcessId(process.rawAddress).toUInt()

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle.toInt()))

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer.rawAddress, usize.toInt()).toUInt()

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo.rawAddress)
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime.rawAddress)
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime.rawAddress)
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength.toInt(), lpbuffer.rawAddress).toUInt()

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken.rawAddress, lpprofiledir.rawAddress, lpcchsize.rawAddress)

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer.rawAddress, usize.toInt()).toUInt()

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce.rawAddress, dwflags.toInt(), fpending.rawAddress, lpcontext.rawAddress)

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce.rawAddress, dwflags.toInt(), lpcontext.rawAddress)

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist.rawAddress, dwattributecount.toInt(), dwflags.toInt(), lpsize.rawAddress)

public actual fun isThreadAFiber(): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber()

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename.rawAddress, hfile.rawAddress, dwflags.toInt()))

public actual fun localFree(hmem: Hlocal): Hlocal =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem.rawAddress))

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile.rawAddress, dwflags.toInt(), dwreserved.toInt(), nnumberofbytestolocklow.toInt(), nnumberofbytestolockhigh.toInt(), lpoverlapped.rawAddress)

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename.rawAddress, lpnewfilename.rawAddress, dwflags.toInt())

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage.toInt(), dwflags.toInt(), lpmultibytestr.rawAddress, cbmultibyte, lpwidecharstr.rawAddress, cchwidechar)

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle.rawAddress, desiredaccess.toInt(), objectattributes.rawAddress, iostatusblock.rawAddress, allocationsize.rawAddress, fileattributes.toInt(), shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), eabuffer.rawAddress, ealength.toInt())

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle.rawAddress, desiredaccess.toInt(), objectattributes.rawAddress, iostatusblock.rawAddress, shareaccess.toInt(), openoptions.toInt())

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle.rawAddress, iostatusblock.rawAddress, fsinformation.rawAddress, length.toInt(), fsinformationclass)

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle.rawAddress, event.rawAddress, 0L, apccontext.rawAddress, iostatusblock.rawAddress, buffer.rawAddress, length.toInt(), byteoffset.rawAddress, key.rawAddress)

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle.rawAddress, iostatusblock.rawAddress, fileinformation.rawAddress, length.toInt(), fileinformationclass)

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle.rawAddress, event.rawAddress, 0L, apccontext.rawAddress, iostatusblock.rawAddress, buffer.rawAddress, length.toInt(), byteoffset.rawAddress, key.rawAddress)

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle.rawAddress, desiredaccess.toInt(), tokenhandle.rawAddress)

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename.rawAddress, lptargetpath.rawAddress, ucchmax.toInt()).toUInt()

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount.rawAddress)

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency.rawAddress)

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput.rawAddress, lpbuffer.rawAddress, nnumberofcharstoread.toInt(), lpnumberofcharsread.rawAddress, pinputcontrol.rawAddress)

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysReadFile(hfile.rawAddress, lpbuffer.rawAddress, nnumberofbytestoread.toInt(), lpnumberofbytesread.rawAddress, lpoverlapped.rawAddress)

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile.rawAddress, lpbuffer.rawAddress, nnumberofbytestoread.toInt(), lpoverlapped.rawAddress, 0L)

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock.rawAddress)
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock.rawAddress)
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname.rawAddress)

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer.rawAddress, randombufferlength.toInt()).toUByte()

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status).toUInt()

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname.rawAddress)

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname.rawAddress, lpvalue.rawAddress)

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename.rawAddress, dwfileattributes.toInt())

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile.rawAddress, fileinformationclass, lpfileinformation.rawAddress, dwbuffersize.toInt())

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile.rawAddress, lidistancetomove, lpnewfilepointer.rawAddress, dwmovemethod.toInt())

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile.rawAddress, lpcreationtime.rawAddress, lplastaccesstime.rawAddress, lplastwritetime.rawAddress)

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject.rawAddress, dwmask.toInt(), dwflags.toInt())

public actual fun setLastError(dwerrcode: UInt) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode.toInt())
}

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle.toInt(), hhandle.rawAddress)

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes.rawAddress)

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer.rawAddress, lpduetime.rawAddress, lperiod, 0L, lpargtocompletionroutine.rawAddress, fresume)

public actual fun sleep(dwmilliseconds: UInt) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds.toInt())
}

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable.rawAddress, srwlock.rawAddress, dwmilliseconds.toInt(), flags.toInt())

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds.toInt(), balertable).toUInt()

public actual fun switchToThread(): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSwitchToThread()

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess.rawAddress, uexitcode.toInt())

public actual fun tlsAlloc(): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysTlsAlloc().toUInt()

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex.toInt())

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    MutPtr(SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex.toInt()))

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex.toInt(), lptlsvalue.rawAddress)

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock.rawAddress).toUByte()

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock.rawAddress).toUByte()

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile.rawAddress, dwfileoffsetlow.toInt(), dwfileoffsethigh.toInt(), nnumberofbytestounlocklow.toInt(), nnumberofbytestounlockhigh.toInt())

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist.rawAddress, dwflags.toInt(), attribute.toLong(), lpvalue.rawAddress, cbsize.toLong(), lppreviousvalue.rawAddress, lpreturnsize.rawAddress)

public actual fun wSACleanup(): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWSACleanup()

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s.toLong(), dwprocessid.toInt(), lpprotocolinfo.rawAddress)

public actual fun wSAGetLastError(): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError()

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWSARecv(s.toLong(), lpbuffers.rawAddress, dwbuffercount.toInt(), lpnumberofbytesrecvd.rawAddress, lpflags.rawAddress, lpoverlapped.rawAddress, 0L)

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWSASend(s.toLong(), lpbuffers.rawAddress, dwbuffercount.toInt(), lpnumberofbytessent.rawAddress, dwflags.toInt(), lpoverlapped.rawAddress, 0L)

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWSASocketW(af, type, protocol, lpprotocolinfo.rawAddress, g.toInt(), dwflags.toInt()).toULong()

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested.toShort(), lpwsadata.rawAddress)

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount.toInt(), lphandles.rawAddress, bwaitall, dwmilliseconds.toInt()).toUInt()

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle.rawAddress, dwmilliseconds.toInt()).toUInt()

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address.rawAddress, compareaddress.rawAddress, addresssize.toLong(), dwmilliseconds.toInt())

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable.rawAddress)
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address.rawAddress)
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address.rawAddress)
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable.rawAddress)
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress, cchwidechar, lpmultibytestr.rawAddress, cbmultibyte, lpdefaultchar.rawAddress, lpuseddefaultchar.rawAddress)

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput.rawAddress, lpbuffer.rawAddress, nnumberofcharstowrite.toInt(), lpnumberofcharswritten.rawAddress, lpreserved.rawAddress)

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile.rawAddress, lpbuffer.rawAddress, nnumberofbytestowrite.toInt(), lpoverlapped.rawAddress, 0L)

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysAccept(s.toLong(), addr.rawAddress, addrlen.rawAddress).toULong()

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysBind(s.toLong(), name.rawAddress, namelen)

public actual fun closesocket(s: Socket): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysClosesocket(s.toLong())

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysConnect(s.toLong(), name.rawAddress, namelen)

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo.rawAddress)
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename.rawAddress, pservicename.rawAddress, phints.rawAddress, ppresult.rawAddress)

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetpeername(s.toLong(), name.rawAddress, namelen.rawAddress)

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetsockname(s.toLong(), name.rawAddress, namelen.rawAddress)

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysGetsockopt(s.toLong(), level, optname, optval.rawAddress, optlen.rawAddress)

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s.toLong(), cmd, argp.rawAddress)

public actual fun listen(s: Socket, backlog: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysListen(s.toLong(), backlog)

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring.rawAddress)

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysRecv(s.toLong(), buf.rawAddress, len, flags)

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysRecvfrom(s.toLong(), buf.rawAddress, len, flags, from.rawAddress, fromlen.rawAddress)

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSelect(nfds, readfds.rawAddress, writefds.rawAddress, exceptfds.rawAddress, timeout.rawAddress)

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSend(s.toLong(), buf.rawAddress, len, flags)

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSendto(s.toLong(), buf.rawAddress, len, flags, to.rawAddress, tolen)

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysSetsockopt(s.toLong(), level, optname, optval.rawAddress, optlen)

public actual fun shutdown(s: Socket, how: Int): Int =
    SysPalWindowsCWindowssysWindowsSysJniBridge.stdSysPalWindowsCWindowssysWindowsSysShutdown(s.toLong(), how)
