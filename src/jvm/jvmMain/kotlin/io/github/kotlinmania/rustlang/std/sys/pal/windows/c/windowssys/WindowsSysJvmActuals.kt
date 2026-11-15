@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object StdSysPalWindowsCWindowssysWindowsSysJniBridge {
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
        val resourcePath = "io/github/kotlinmania/rustlang/native/$hostPlatform-$hostArch/$libFileName"
        val stream = StdSysPalWindowsCWindowssysWindowsSysJniBridge::class.java.classLoader?.getResourceAsStream(resourcePath)
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
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first: Int, handler: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1: Long, cchcount1: Int, lpstring2: Long, cchcount2: Int, bignorecase: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename: Long, lpnewfilename: Long, lpprogressroutine: Long, lpdata: Long, pbcancel: Long, dwcopyflags: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname: Long, lpsecurityattributes: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes: Long, bmanualreset: Int, binitialstate: Int, lpname: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename: Long, dwdesiredaccess: Int, dwsharemode: Int, lpsecurityattributes: Long, dwcreationdisposition: Int, dwflagsandattributes: Int, htemplatefile: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename: Long, lpexistingfilename: Long, lpsecurityattributes: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname: Long, dwopenmode: Int, dwpipemode: Int, nmaxinstances: Int, noutbuffersize: Int, ninbuffersize: Int, ndefaulttimeout: Int, lpsecurityattributes: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe: Long, hwritepipe: Long, lppipeattributes: Long, nsize: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname: Long, lpcommandline: Long, lpprocessattributes: Long, lpthreadattributes: Long, binherithandles: Int, dwcreationflags: Int, lpenvironment: Long, lpcurrentdirectory: Long, lpstartupinfo: Long, lpprocessinformation: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename: Long, lptargetfilename: Long, dwflags: Int): Byte

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes: Long, dwstacksize: Long, lpstartaddress: Long, lpparameter: Long, dwcreationflags: Int, lpthreadid: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes: Long, lptimername: Long, dwflags: Int, dwdesiredaccess: Int): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice: Long, dwiocontrolcode: Int, lpinbuffer: Long, ninbuffersize: Int, lpoutbuffer: Long, noutbuffersize: Int, lpbytesreturned: Long, lpoverlapped: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle: Long, hsourcehandle: Long, htargetprocesshandle: Long, lptargethandle: Long, dwdesiredaccess: Int, binherithandle: Int, dwoptions: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode: Int)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename: Long, finfolevelid: Int, lpfindfiledata: Long, fsearchop: Int, lpsearchfilter: Long, dwadditionalflags: Int): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile: Long, lpfindfiledata: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(lpcallback: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex: Int): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex: Int, lpflsdata: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags: Int, lpsource: Long, dwmessageid: Int, dwlanguageid: Int, lpbuffer: Long, nsize: Int, arguments: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber: Short): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW(): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle: Long, lpmode: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength: Int, lpbuffer: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess(): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread(): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW(): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname: Long, lpbuffer: Long, nsize: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess: Long, lpexitcode: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile: Long, lpfileinformation: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile: Long, fileinformationclass: Int, lpfileinformation: Long, dwbuffersize: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile: Long, lpfilesize: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile: Long, lpszfilepath: Long, cchfilepath: Int, dwflags: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename: Long, nbufferlength: Int, lpbuffer: Long, lpfilepart: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name: Long, namelen: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule: Long, lpfilename: Long, nsize: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags: Int, lpmodulename: Long, phmodule: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile: Long, lpoverlapped: Long, lpnumberofbytestransferred: Long, bwait: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcAddress(hmodule: Long, lpprocname: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId(process: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle: Int): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer: Long, usize: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength: Int, lpbuffer: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken: Long, lpprofiledir: Long, lpcchsize: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer: Long, usize: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce: Long, dwflags: Int, fpending: Long, lpcontext: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce: Long, dwflags: Int, lpcontext: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist: Long, dwattributecount: Int, dwflags: Int, lpsize: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename: Long, hfile: Long, dwflags: Int): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile: Long, dwflags: Int, dwreserved: Int, nnumberofbytestolocklow: Int, nnumberofbytestolockhigh: Int, lpoverlapped: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename: Long, lpnewfilename: Long, dwflags: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage: Int, dwflags: Int, lpmultibytestr: Long, cbmultibyte: Int, lpwidecharstr: Long, cchwidechar: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle: Long, desiredaccess: Int, objectattributes: Long, iostatusblock: Long, allocationsize: Long, fileattributes: Int, shareaccess: Int, createdisposition: Int, createoptions: Int, eabuffer: Long, ealength: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle: Long, desiredaccess: Int, objectattributes: Long, iostatusblock: Long, shareaccess: Int, openoptions: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle: Long, iostatusblock: Long, fsinformation: Long, length: Int, fsinformationclass: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle: Long, event: Long, apcroutine: Long, apccontext: Long, iostatusblock: Long, buffer: Long, length: Int, byteoffset: Long, key: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle: Long, iostatusblock: Long, fileinformation: Long, length: Int, fileinformationclass: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle: Long, event: Long, apcroutine: Long, apccontext: Long, iostatusblock: Long, buffer: Long, length: Int, byteoffset: Long, key: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle: Long, desiredaccess: Int, tokenhandle: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename: Long, lptargetpath: Long, ucchmax: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput: Long, lpbuffer: Long, nnumberofcharstoread: Int, lpnumberofcharsread: Long, pinputcontrol: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile(hfile: Long, lpbuffer: Long, nnumberofbytestoread: Int, lpnumberofbytesread: Long, lpoverlapped: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile: Long, lpbuffer: Long, nnumberofbytestoread: Int, lpoverlapped: Long, lpcompletionroutine: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer: Long, randombufferlength: Int): Byte

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname: Long, lpvalue: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename: Long, dwfileattributes: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile: Long, fileinformationclass: Int, lpfileinformation: Long, dwbuffersize: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile: Long, lidistancetomove: Long, lpnewfilepointer: Long, dwmovemethod: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile: Long, lpcreationtime: Long, lplastaccesstime: Long, lplastwritetime: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject: Long, dwmask: Int, dwflags: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode: Int)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle: Int, hhandle: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer: Long, lpduetime: Long, lperiod: Int, pfncompletionroutine: Long, lpargtocompletionroutine: Long, fresume: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds: Int)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable: Long, srwlock: Long, dwmilliseconds: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds: Int, balertable: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess: Long, uexitcode: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex: Int): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex: Int, lptlsvalue: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock: Long): Byte

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock: Long): Byte

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile: Long, dwfileoffsetlow: Int, dwfileoffsethigh: Int, nnumberofbytestounlocklow: Int, nnumberofbytestounlockhigh: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist: Long, dwflags: Int, attribute: Long, lpvalue: Long, cbsize: Long, lppreviousvalue: Long, lpreturnsize: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s: Long, dwprocessid: Int, lpprotocolinfo: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError(): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv(s: Long, lpbuffers: Long, dwbuffercount: Int, lpnumberofbytesrecvd: Long, lpflags: Long, lpoverlapped: Long, lpcompletionroutine: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend(s: Long, lpbuffers: Long, dwbuffercount: Int, lpnumberofbytessent: Long, dwflags: Int, lpoverlapped: Long, lpcompletionroutine: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: Long, g: Int, dwflags: Int): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested: Short, lpwsadata: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount: Int, lphandles: Long, bwaitall: Int, dwmilliseconds: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle: Long, dwmilliseconds: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address: Long, compareaddress: Long, addresssize: Long, dwmilliseconds: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage: Int, dwflags: Int, lpwidecharstr: Long, cchwidechar: Int, lpmultibytestr: Long, cbmultibyte: Int, lpdefaultchar: Long, lpuseddefaultchar: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput: Long, lpbuffer: Long, nnumberofcharstowrite: Int, lpnumberofcharswritten: Long, lpreserved: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile: Long, lpbuffer: Long, nnumberofbytestowrite: Int, lpoverlapped: Long, lpcompletionroutine: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysAccept(s: Long, addr: Long, addrlen: Long): Long

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysBind(s: Long, name: Long, namelen: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(s: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysConnect(s: Long, name: Long, namelen: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo: Long)

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename: Long, pservicename: Long, phints: Long, ppresult: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername(s: Long, name: Long, namelen: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname(s: Long, name: Long, namelen: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt(s: Long, level: Int, optname: Int, optval: Long, optlen: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s: Long, cmd: Int, argp: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysListen(s: Long, backlog: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysRecv(s: Long, buf: Long, len: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom(s: Long, buf: Long, len: Int, flags: Int, from: Long, fromlen: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSelect(nfds: Int, readfds: Long, writefds: Long, exceptfds: Long, timeout: Long): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSend(s: Long, buf: Long, len: Int, flags: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSendto(s: Long, buf: Long, len: Int, flags: Int, to: Long, tolen: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt(s: Long, level: Int, optname: Int, optval: Long, optlen: Int): Int

    @JvmStatic
    external fun rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(s: Long, how: Int): Int
}

public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock.rawAddress)
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock.rawAddress)
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first.toInt(), 0L))

public actual fun cancelIo(hfile: Handle): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile.rawAddress)

public actual fun closeHandle(hobject: Handle): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject.rawAddress)

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1.rawAddress, cchcount1, lpstring2.rawAddress, cchcount2, bignorecase)

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename.rawAddress, lpnewfilename.rawAddress, 0L, lpdata.rawAddress, pbcancel.rawAddress, dwcopyflags.toInt())

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname.rawAddress, lpsecurityattributes.rawAddress)

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes.rawAddress, bmanualreset, binitialstate, lpname.rawAddress))

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename.rawAddress, dwdesiredaccess.toInt(), dwsharemode.toInt(), lpsecurityattributes.rawAddress, dwcreationdisposition.toInt(), dwflagsandattributes.toInt(), htemplatefile.rawAddress))

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename.rawAddress, lpexistingfilename.rawAddress, lpsecurityattributes.rawAddress)

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname.rawAddress, dwopenmode.toInt(), dwpipemode.toInt(), nmaxinstances.toInt(), noutbuffersize.toInt(), ninbuffersize.toInt(), ndefaulttimeout.toInt(), lpsecurityattributes.rawAddress))

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe.rawAddress, hwritepipe.rawAddress, lppipeattributes.rawAddress, nsize.toInt())

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname.rawAddress, lpcommandline.rawAddress, lpprocessattributes.rawAddress, lpthreadattributes.rawAddress, binherithandles, dwcreationflags.toInt(), lpenvironment.rawAddress, lpcurrentdirectory.rawAddress, lpstartupinfo.rawAddress, lpprocessinformation.rawAddress)

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename.rawAddress, lptargetfilename.rawAddress, dwflags.toInt()).toUByte()

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes.rawAddress, dwstacksize.toLong(), 0L, lpparameter.rawAddress, dwcreationflags.toInt(), lpthreadid.rawAddress))

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes.rawAddress, lptimername.rawAddress, dwflags.toInt(), dwdesiredaccess.toInt()))

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename.rawAddress)

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist.rawAddress)
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice.rawAddress, dwiocontrolcode.toInt(), lpinbuffer.rawAddress, ninbuffersize.toInt(), lpoutbuffer.rawAddress, noutbuffersize.toInt(), lpbytesreturned.rawAddress, lpoverlapped.rawAddress)

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle.rawAddress, hsourcehandle.rawAddress, htargetprocesshandle.rawAddress, lptargethandle.rawAddress, dwdesiredaccess.toInt(), binherithandle, dwoptions.toInt())

public actual fun exitProcess(uexitcode: UInt): Nothing {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode.toInt())
    error("Unreachable noreturn C function exitProcess")
}

public actual fun findClose(hfindfile: Handle): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile.rawAddress)

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename.rawAddress, finfolevelid, lpfindfiledata.rawAddress, fsearchop, lpsearchfilter.rawAddress, dwadditionalflags.toInt()))

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile.rawAddress, lpfindfiledata.rawAddress)

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(0L).toUInt()

public actual fun flsFree(dwflsindex: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex.toInt())

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex.toInt()))

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex.toInt(), lpflsdata.rawAddress)

public actual fun flushFileBuffers(hfile: Handle): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile.rawAddress)

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags.toInt(), lpsource.rawAddress, dwmessageid.toInt(), dwlanguageid.toInt(), lpbuffer.rawAddress, nsize.toInt(), arguments.rawAddress).toUInt()

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv.rawAddress)

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber.toShort()).toUInt()

public actual fun getCommandLineW(): Pwstr =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW())

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle.rawAddress, lpmode.rawAddress)

public actual fun getConsoleOutputCP(): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP().toUInt()

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength.toInt(), lpbuffer.rawAddress).toUInt()

public actual fun getCurrentProcess(): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess())

public actual fun getCurrentProcessId(): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId().toUInt()

public actual fun getCurrentThread(): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread())

public actual fun getCurrentThreadId(): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId().toUInt()

public actual fun getEnvironmentStringsW(): Lpwch =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW())

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname.rawAddress, lpbuffer.rawAddress, nsize.toInt()).toUInt()

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess.rawAddress, lpexitcode.rawAddress)

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename.rawAddress).toUInt()

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile.rawAddress, lpfileinformation.rawAddress)

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile.rawAddress, fileinformationclass, lpfileinformation.rawAddress, dwbuffersize.toInt())

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile.rawAddress, lpfilesize.rawAddress)

public actual fun getFileType(hfile: Handle): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile.rawAddress).toUInt()

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile.rawAddress, lpszfilepath.rawAddress, cchfilepath.toInt(), dwflags.toInt()).toUInt()

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename.rawAddress, nbufferlength.toInt(), lpbuffer.rawAddress, lpfilepart.rawAddress).toUInt()

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name.rawAddress, namelen)

public actual fun getLastError(): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError().toUInt()

public actual fun getLogicalDrives(): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives().toUInt()

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule.rawAddress, lpfilename.rawAddress, nsize.toInt()).toUInt()

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename.rawAddress))

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags.toInt(), lpmodulename.rawAddress, phmodule.rawAddress)

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename.rawAddress))

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile.rawAddress, lpoverlapped.rawAddress, lpnumberofbytestransferred.rawAddress, bwait)

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    null

public actual fun getProcessId(process: Handle): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId(process.rawAddress).toUInt()

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle.toInt()))

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer.rawAddress, usize.toInt()).toUInt()

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo.rawAddress)
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime.rawAddress)
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime.rawAddress)
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength.toInt(), lpbuffer.rawAddress).toUInt()

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken.rawAddress, lpprofiledir.rawAddress, lpcchsize.rawAddress)

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer.rawAddress, usize.toInt()).toUInt()

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce.rawAddress, dwflags.toInt(), fpending.rawAddress, lpcontext.rawAddress)

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce.rawAddress, dwflags.toInt(), lpcontext.rawAddress)

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist.rawAddress, dwattributecount.toInt(), dwflags.toInt(), lpsize.rawAddress)

public actual fun isThreadAFiber(): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber()

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename.rawAddress, hfile.rawAddress, dwflags.toInt()))

public actual fun localFree(hmem: Hlocal): Hlocal =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem.rawAddress))

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile.rawAddress, dwflags.toInt(), dwreserved.toInt(), nnumberofbytestolocklow.toInt(), nnumberofbytestolockhigh.toInt(), lpoverlapped.rawAddress)

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename.rawAddress, lpnewfilename.rawAddress, dwflags.toInt())

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage.toInt(), dwflags.toInt(), lpmultibytestr.rawAddress, cbmultibyte, lpwidecharstr.rawAddress, cchwidechar)

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle.rawAddress, desiredaccess.toInt(), objectattributes.rawAddress, iostatusblock.rawAddress, allocationsize.rawAddress, fileattributes.toInt(), shareaccess.toInt(), createdisposition.toInt(), createoptions.toInt(), eabuffer.rawAddress, ealength.toInt())

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle.rawAddress, desiredaccess.toInt(), objectattributes.rawAddress, iostatusblock.rawAddress, shareaccess.toInt(), openoptions.toInt())

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle.rawAddress, iostatusblock.rawAddress, fsinformation.rawAddress, length.toInt(), fsinformationclass)

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle.rawAddress, event.rawAddress, 0L, apccontext.rawAddress, iostatusblock.rawAddress, buffer.rawAddress, length.toInt(), byteoffset.rawAddress, key.rawAddress)

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle.rawAddress, iostatusblock.rawAddress, fileinformation.rawAddress, length.toInt(), fileinformationclass)

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle.rawAddress, event.rawAddress, 0L, apccontext.rawAddress, iostatusblock.rawAddress, buffer.rawAddress, length.toInt(), byteoffset.rawAddress, key.rawAddress)

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle.rawAddress, desiredaccess.toInt(), tokenhandle.rawAddress)

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename.rawAddress, lptargetpath.rawAddress, ucchmax.toInt()).toUInt()

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount.rawAddress)

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency.rawAddress)

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput.rawAddress, lpbuffer.rawAddress, nnumberofcharstoread.toInt(), lpnumberofcharsread.rawAddress, pinputcontrol.rawAddress)

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile(hfile.rawAddress, lpbuffer.rawAddress, nnumberofbytestoread.toInt(), lpnumberofbytesread.rawAddress, lpoverlapped.rawAddress)

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile.rawAddress, lpbuffer.rawAddress, nnumberofbytestoread.toInt(), lpoverlapped.rawAddress, 0L)

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock.rawAddress)
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock.rawAddress)
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname.rawAddress)

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer.rawAddress, randombufferlength.toInt()).toUByte()

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status).toUInt()

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname.rawAddress)

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname.rawAddress, lpvalue.rawAddress)

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename.rawAddress, dwfileattributes.toInt())

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile.rawAddress, fileinformationclass, lpfileinformation.rawAddress, dwbuffersize.toInt())

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile.rawAddress, lidistancetomove, lpnewfilepointer.rawAddress, dwmovemethod.toInt())

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile.rawAddress, lpcreationtime.rawAddress, lplastaccesstime.rawAddress, lplastwritetime.rawAddress)

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject.rawAddress, dwmask.toInt(), dwflags.toInt())

public actual fun setLastError(dwerrcode: UInt) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode.toInt())
}

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle.toInt(), hhandle.rawAddress)

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes.rawAddress)

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer.rawAddress, lpduetime.rawAddress, lperiod, 0L, lpargtocompletionroutine.rawAddress, fresume)

public actual fun sleep(dwmilliseconds: UInt) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds.toInt())
}

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable.rawAddress, srwlock.rawAddress, dwmilliseconds.toInt(), flags.toInt())

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds.toInt(), balertable).toUInt()

public actual fun switchToThread(): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread()

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess.rawAddress, uexitcode.toInt())

public actual fun tlsAlloc(): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc().toUInt()

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex.toInt())

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    MutPtr(StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex.toInt()))

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex.toInt(), lptlsvalue.rawAddress)

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock.rawAddress).toUByte()

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock.rawAddress).toUByte()

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile.rawAddress, dwfileoffsetlow.toInt(), dwfileoffsethigh.toInt(), nnumberofbytestounlocklow.toInt(), nnumberofbytestounlockhigh.toInt())

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist.rawAddress, dwflags.toInt(), attribute.toLong(), lpvalue.rawAddress, cbsize.toLong(), lppreviousvalue.rawAddress, lpreturnsize.rawAddress)

public actual fun wSACleanup(): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup()

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s.toLong(), dwprocessid.toInt(), lpprotocolinfo.rawAddress)

public actual fun wSAGetLastError(): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError()

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv(s.toLong(), lpbuffers.rawAddress, dwbuffercount.toInt(), lpnumberofbytesrecvd.rawAddress, lpflags.rawAddress, lpoverlapped.rawAddress, 0L)

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend(s.toLong(), lpbuffers.rawAddress, dwbuffercount.toInt(), lpnumberofbytessent.rawAddress, dwflags.toInt(), lpoverlapped.rawAddress, 0L)

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW(af, type, protocol, lpprotocolinfo.rawAddress, g.toInt(), dwflags.toInt()).toULong()

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested.toShort(), lpwsadata.rawAddress)

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount.toInt(), lphandles.rawAddress, bwaitall, dwmilliseconds.toInt()).toUInt()

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle.rawAddress, dwmilliseconds.toInt()).toUInt()

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address.rawAddress, compareaddress.rawAddress, addresssize.toLong(), dwmilliseconds.toInt())

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable.rawAddress)
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address.rawAddress)
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address.rawAddress)
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable.rawAddress)
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage.toInt(), dwflags.toInt(), lpwidecharstr.rawAddress, cchwidechar, lpmultibytestr.rawAddress, cbmultibyte, lpdefaultchar.rawAddress, lpuseddefaultchar.rawAddress)

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput.rawAddress, lpbuffer.rawAddress, nnumberofcharstowrite.toInt(), lpnumberofcharswritten.rawAddress, lpreserved.rawAddress)

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile.rawAddress, lpbuffer.rawAddress, nnumberofbytestowrite.toInt(), lpoverlapped.rawAddress, 0L)

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysAccept(s.toLong(), addr.rawAddress, addrlen.rawAddress).toULong()

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysBind(s.toLong(), name.rawAddress, namelen)

public actual fun closesocket(s: Socket): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(s.toLong())

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysConnect(s.toLong(), name.rawAddress, namelen)

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo.rawAddress)
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename.rawAddress, pservicename.rawAddress, phints.rawAddress, ppresult.rawAddress)

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername(s.toLong(), name.rawAddress, namelen.rawAddress)

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname(s.toLong(), name.rawAddress, namelen.rawAddress)

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt(s.toLong(), level, optname, optval.rawAddress, optlen.rawAddress)

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s.toLong(), cmd, argp.rawAddress)

public actual fun listen(s: Socket, backlog: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysListen(s.toLong(), backlog)

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring.rawAddress)

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysRecv(s.toLong(), buf.rawAddress, len, flags)

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom(s.toLong(), buf.rawAddress, len, flags, from.rawAddress, fromlen.rawAddress)

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSelect(nfds, readfds.rawAddress, writefds.rawAddress, exceptfds.rawAddress, timeout.rawAddress)

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSend(s.toLong(), buf.rawAddress, len, flags)

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSendto(s.toLong(), buf.rawAddress, len, flags, to.rawAddress, tolen)

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt(s.toLong(), level, optname, optval.rawAddress, optlen)

public actual fun shutdown(s: Socket, how: Int): Int =
    StdSysPalWindowsCWindowssysWindowsSysJniBridge.rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(s.toLong(), how)
