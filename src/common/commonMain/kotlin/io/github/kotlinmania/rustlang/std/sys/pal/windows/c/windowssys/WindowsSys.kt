// port-lint: source sys/pal/windows/c/windows_sys.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public expect fun acquireSRWLockExclusive(srwlock: MutPtr)

public expect fun acquireSRWLockShared(srwlock: MutPtr)

public expect fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr

public expect fun cancelIo(hfile: Handle): Bool

public expect fun closeHandle(hobject: Handle): Bool

public expect fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int

public expect fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool

public expect fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool

public expect fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle

public expect fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle

public expect fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool

public expect fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle

public expect fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool

public expect fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool

public expect fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean

public expect fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle

public expect fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle

public expect fun deleteFileW(lpfilename: Pcwstr): Bool

public expect fun deleteProcThreadAttributeList(lpattributelist: MutPtr)

public expect fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool

public expect fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool

public expect fun exitProcess(uexitcode: UInt): Nothing

public expect fun findClose(hfindfile: Handle): Bool

public expect fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle

public expect fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool

public expect fun flsAlloc(lpcallback: PflsCallbackFunction): UInt

public expect fun flsFree(dwflsindex: UInt): Bool

public expect fun flsGetValue(dwflsindex: UInt): MutPtr

public expect fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool

public expect fun flushFileBuffers(hfile: Handle): Bool

public expect fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt

public expect fun freeEnvironmentStringsW(penv: ConstPtr): Bool

public expect fun getActiveProcessorCount(groupnumber: UShort): UInt

public expect fun getCommandLineW(): Pwstr

public expect fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool

public expect fun getConsoleOutputCP(): UInt

public expect fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt

public expect fun getCurrentProcess(): Handle

public expect fun getCurrentProcessId(): UInt

public expect fun getCurrentThread(): Handle

public expect fun getCurrentThreadId(): UInt

public expect fun getEnvironmentStringsW(): Lpwch

public expect fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt

public expect fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool

public expect fun getFileAttributesW(lpfilename: Pcwstr): UInt

public expect fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool

public expect fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool

public expect fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool

public expect fun getFileType(hfile: Handle): UInt

public expect fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt

public expect fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt

public expect fun getHostNameW(name: Pwstr, namelen: Int): Int

public expect fun getLastError(): UInt

public expect fun getLogicalDrives(): UInt

public expect fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt

public expect fun getModuleHandleA(lpmodulename: Pcstr): Hmodule

public expect fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool

public expect fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule

public expect fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool

public expect fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc

public expect fun getProcessId(process: Handle): UInt

public expect fun getStdHandle(nstdhandle: UInt): Handle

public expect fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt

public expect fun getSystemInfo(lpsysteminfo: MutPtr)

public expect fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr)

public expect fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr)

public expect fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt

public expect fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool

public expect fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt

public expect fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool

public expect fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool

public expect fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool

public expect fun isThreadAFiber(): Bool

public expect fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule

public expect fun localFree(hmem: Hlocal): Hlocal

public expect fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool

public expect fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool

public expect fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int

public expect fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus

public expect fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus

public expect fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus

public expect fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus

public expect fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus

public expect fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus

public expect fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool

public expect fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt

public expect fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool

public expect fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool

public expect fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool

public expect fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool

public expect fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool

public expect fun releaseSRWLockExclusive(srwlock: MutPtr)

public expect fun releaseSRWLockShared(srwlock: MutPtr)

public expect fun removeDirectoryW(lppathname: Pcwstr): Bool

public expect fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean

public expect fun rtlNtStatusToDosError(status: Ntstatus): UInt

public expect fun setCurrentDirectoryW(lppathname: Pcwstr): Bool

public expect fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool

public expect fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool

public expect fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool

public expect fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool

public expect fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool

public expect fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool

public expect fun setLastError(dwerrcode: UInt)

public expect fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool

public expect fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool

public expect fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool

public expect fun sleep(dwmilliseconds: UInt)

public expect fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool

public expect fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt

public expect fun switchToThread(): Bool

public expect fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool

public expect fun tlsAlloc(): UInt

public expect fun tlsFree(dwtlsindex: UInt): Bool

public expect fun tlsGetValue(dwtlsindex: UInt): MutPtr

public expect fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool

public expect fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean

public expect fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean

public expect fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool

public expect fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool

public expect fun wSACleanup(): Int

public expect fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int

public expect fun wSAGetLastError(): Int

public expect fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int

public expect fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int

public expect fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket

public expect fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int

public expect fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt

public expect fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt

public expect fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool

public expect fun wakeAllConditionVariable(conditionvariable: MutPtr)

public expect fun wakeByAddressAll(address: ConstPtr)

public expect fun wakeByAddressSingle(address: ConstPtr)

public expect fun wakeConditionVariable(conditionvariable: MutPtr)

public expect fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int

public expect fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool

public expect fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool

public expect fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket

public expect fun bind(s: Socket, name: ConstPtr, namelen: Int): Int

public expect fun closesocket(s: Socket): Int

public expect fun connect(s: Socket, name: ConstPtr, namelen: Int): Int

public expect fun freeaddrinfo(paddrinfo: ConstPtr)

public expect fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int

public expect fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int

public expect fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int

public expect fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int

public expect fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int

public expect fun listen(s: Socket, backlog: Int): Int

public expect fun lstrlenW(lpstring: Pcwstr): Int

public expect fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int

public expect fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int

public expect fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int

public expect fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int

public expect fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int

public expect fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int

public expect fun shutdown(s: Socket, how: Int): Int

public val ABOVE_NORMAL_PRIORITY_CLASS: Int = 32768

public typealias AccessMask = UInt

public typealias AddressFamily = UShort

public open class Addrinfoa internal constructor(
    public var aiFlags: Int,
    public var aiFamily: Int,
    public var aiSocktype: Int,
    public var aiProtocol: Int,
    public var aiAddrlen: Int,
    aiCanonnameCtorArg: MutPtr,
    aiAddrCtorArg: MutPtr,
    aiNextCtorArg: MutPtr,
) {
    public var aiCanonname: MutPtr = aiCanonnameCtorArg
        internal set

    public var aiAddr: MutPtr = aiAddrCtorArg
        internal set

    public var aiNext: MutPtr = aiNextCtorArg
        internal set

    public open fun clone(): Addrinfoa = Addrinfoa(aiFlags, aiFamily, aiSocktype, aiProtocol, aiAddrlen, aiCanonname, aiAddr, aiNext)

    public companion object {
        public fun typeLayout(): TypeLayout<Addrinfoa> =
            object : TypeLayout<Addrinfoa> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Addrinfoa =
                    Addrinfoa(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: Addrinfoa) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.aiFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.aiFamily)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.aiSocktype)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.aiProtocol)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 16L, value.aiAddrlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.aiCanonname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.aiAddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.aiNext)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Addrinfoa =
                    Addrinfoa(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Addrinfoa) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.aiFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.aiFamily)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.aiSocktype)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.aiProtocol)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 16, value.aiAddrlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.aiCanonname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.aiAddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.aiNext)
                }
            }

        public operator fun invoke(
            aiFlags: Int,
            aiFamily: Int,
            aiSocktype: Int,
            aiProtocol: Int,
            aiAddrlen: Int,
            aiCanonname: MutPtr,
            aiAddr: MutPtr,
            aiNext: MutPtr,
        ): Addrinfoa = Addrinfoa(
            aiFlags = aiFlags,
            aiFamily = aiFamily,
            aiSocktype = aiSocktype,
            aiProtocol = aiProtocol,
            aiAddrlen = aiAddrlen,
            aiCanonnameCtorArg = aiCanonname,
            aiAddrCtorArg = aiAddr,
            aiNextCtorArg = aiNext,
        )
    }
}

public val AF_INET: Int = 2

public val AF_INET6: Int = 23

public val AF_UNIX: Int = 1

public val AF_UNSPEC: Int = 0

public val ALL_PROCESSOR_GROUPS: Int = 65535

public open class Arm64NtContext(
    public var contextFlags: UInt,
    public var cpsr: UInt,
    public var anonymous: Arm64NtContext0,
    public var sp: ULong,
    public var pc: ULong,
    public var v: List<Arm64NtNeon128>,
    public var fpcr: UInt,
    public var fpsr: UInt,
    public var bcr: UIntArray,
    public var bvr: ULongArray,
    public var wcr: UIntArray,
    public var wvr: ULongArray,
) {
    public open fun clone(): Arm64NtContext = Arm64NtContext(contextFlags, cpsr, anonymous, sp, pc, v, fpcr, fpsr, bcr, bvr, wcr, wvr)

    public companion object {
        public fun default(): Arm64NtContext = null
    }
}

public open class Arm64NtContext0(
    public var anonymous: Arm64NtContext00? = null,
    public var x: ULongArray? = null,
) {
    public companion object {
        public fun default(): Arm64NtContext0 = null
    }
}

public open class Arm64NtContext00(
    public var x0: ULong,
    public var x1: ULong,
    public var x2: ULong,
    public var x3: ULong,
    public var x4: ULong,
    public var x5: ULong,
    public var x6: ULong,
    public var x7: ULong,
    public var x8: ULong,
    public var x9: ULong,
    public var x10: ULong,
    public var x11: ULong,
    public var x12: ULong,
    public var x13: ULong,
    public var x14: ULong,
    public var x15: ULong,
    public var x16: ULong,
    public var x17: ULong,
    public var x18: ULong,
    public var x19: ULong,
    public var x20: ULong,
    public var x21: ULong,
    public var x22: ULong,
    public var x23: ULong,
    public var x24: ULong,
    public var x25: ULong,
    public var x26: ULong,
    public var x27: ULong,
    public var x28: ULong,
    public var fp: ULong,
    public var lr: ULong,
) {
    public open fun clone(): Arm64NtContext00 = Arm64NtContext00(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20, x21, x22, x23, x24, x25, x26, x27, x28, fp, lr)

    public companion object {
        public fun typeLayout(): TypeLayout<Arm64NtContext00> =
            object : TypeLayout<Arm64NtContext00> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 248L
                override fun readNative(addr: Long): Arm64NtContext00 =
                    Arm64NtContext00(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U64).readNative(addr + 104L),
                        (PrimitiveLayouts.U64).readNative(addr + 112L),
                        (PrimitiveLayouts.U64).readNative(addr + 120L),
                        (PrimitiveLayouts.U64).readNative(addr + 128L),
                        (PrimitiveLayouts.U64).readNative(addr + 136L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U64).readNative(addr + 152L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U64).readNative(addr + 168L),
                        (PrimitiveLayouts.U64).readNative(addr + 176L),
                        (PrimitiveLayouts.U64).readNative(addr + 184L),
                        (PrimitiveLayouts.U64).readNative(addr + 192L),
                        (PrimitiveLayouts.U64).readNative(addr + 200L),
                        (PrimitiveLayouts.U64).readNative(addr + 208L),
                        (PrimitiveLayouts.U64).readNative(addr + 216L),
                        (PrimitiveLayouts.U64).readNative(addr + 224L),
                        (PrimitiveLayouts.U64).readNative(addr + 232L),
                        (PrimitiveLayouts.U64).readNative(addr + 240L),
                    )
                override fun writeNative(addr: Long, value: Arm64NtContext00) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.x0)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.x1)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.x2)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.x3)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.x4)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.x5)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.x6)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.x7)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.x8)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.x9)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.x10)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.x11)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.x12)
                    (PrimitiveLayouts.U64).writeNative(addr + 104L, value.x13)
                    (PrimitiveLayouts.U64).writeNative(addr + 112L, value.x14)
                    (PrimitiveLayouts.U64).writeNative(addr + 120L, value.x15)
                    (PrimitiveLayouts.U64).writeNative(addr + 128L, value.x16)
                    (PrimitiveLayouts.U64).writeNative(addr + 136L, value.x17)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.x18)
                    (PrimitiveLayouts.U64).writeNative(addr + 152L, value.x19)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.x20)
                    (PrimitiveLayouts.U64).writeNative(addr + 168L, value.x21)
                    (PrimitiveLayouts.U64).writeNative(addr + 176L, value.x22)
                    (PrimitiveLayouts.U64).writeNative(addr + 184L, value.x23)
                    (PrimitiveLayouts.U64).writeNative(addr + 192L, value.x24)
                    (PrimitiveLayouts.U64).writeNative(addr + 200L, value.x25)
                    (PrimitiveLayouts.U64).writeNative(addr + 208L, value.x26)
                    (PrimitiveLayouts.U64).writeNative(addr + 216L, value.x27)
                    (PrimitiveLayouts.U64).writeNative(addr + 224L, value.x28)
                    (PrimitiveLayouts.U64).writeNative(addr + 232L, value.fp)
                    (PrimitiveLayouts.U64).writeNative(addr + 240L, value.lr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Arm64NtContext00 =
                    Arm64NtContext00(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 120),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 184),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 192),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 200),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 208),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 216),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 224),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 232),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 240),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Arm64NtContext00) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.x0)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.x1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.x2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.x3)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.x4)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.x5)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.x6)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.x7)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.x8)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.x9)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.x10)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.x11)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.x12)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 104, value.x13)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 112, value.x14)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 120, value.x15)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 128, value.x16)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 136, value.x17)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.x18)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 152, value.x19)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.x20)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 168, value.x21)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 176, value.x22)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 184, value.x23)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 192, value.x24)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 200, value.x25)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 208, value.x26)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 216, value.x27)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 224, value.x28)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 232, value.fp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 240, value.lr)
                }
            }
    }
}

public open class Arm64NtNeon128(
    public var anonymous: Arm64NtNeon1280? = null,
    public var d: DoubleArray? = null,
    public var s: FloatArray? = null,
    public var h: UShortArray? = null,
    public var b: UByteArray? = null,
) {
    public companion object {
        public fun default(): Arm64NtNeon128 = null
    }
}

public open class Arm64NtNeon1280(
    public var low: ULong,
    public var high: Long,
) {
    public open fun clone(): Arm64NtNeon1280 = Arm64NtNeon1280(low, high)

    public companion object {
        public fun typeLayout(): TypeLayout<Arm64NtNeon1280> =
            object : TypeLayout<Arm64NtNeon1280> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Arm64NtNeon1280 =
                    Arm64NtNeon1280(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Arm64NtNeon1280) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.low)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.high)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Arm64NtNeon1280 =
                    Arm64NtNeon1280(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Arm64NtNeon1280) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.low)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.high)
                }
            }
    }
}

public val BELOW_NORMAL_PRIORITY_CLASS: Int = 16384

public typealias Bool = Int

public open class ByHandleFileInformation(
    public var dwFileAttributes: UInt,
    public var ftCreationTime: Filetime,
    public var ftLastAccessTime: Filetime,
    public var ftLastWriteTime: Filetime,
    public var dwVolumeSerialNumber: UInt,
    public var nFileSizeHigh: UInt,
    public var nFileSizeLow: UInt,
    public var nNumberOfLinks: UInt,
    public var nFileIndexHigh: UInt,
    public var nFileIndexLow: UInt,
) {
    public open fun clone(): ByHandleFileInformation = ByHandleFileInformation(dwFileAttributes, ftCreationTime, ftLastAccessTime, ftLastWriteTime, dwVolumeSerialNumber, nFileSizeHigh, nFileSizeLow, nNumberOfLinks, nFileIndexHigh, nFileIndexLow)

    public companion object {
        public fun typeLayout(): TypeLayout<ByHandleFileInformation> =
            object : TypeLayout<ByHandleFileInformation> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 52L
                override fun readNative(addr: Long): ByHandleFileInformation =
                    ByHandleFileInformation(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (Filetime.typeLayout()).readNative(addr + 4L),
                        (Filetime.typeLayout()).readNative(addr + 12L),
                        (Filetime.typeLayout()).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 44L),
                        (PrimitiveLayouts.U32).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: ByHandleFileInformation) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.dwFileAttributes)
                    (Filetime.typeLayout()).writeNative(addr + 4L, value.ftCreationTime)
                    (Filetime.typeLayout()).writeNative(addr + 12L, value.ftLastAccessTime)
                    (Filetime.typeLayout()).writeNative(addr + 20L, value.ftLastWriteTime)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.dwVolumeSerialNumber)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.nFileSizeHigh)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.nFileSizeLow)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.nNumberOfLinks)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.nFileIndexHigh)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.nFileIndexLow)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ByHandleFileInformation =
                    ByHandleFileInformation(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (Filetime.typeLayout()).readBytes(buf, offset + 4),
                        (Filetime.typeLayout()).readBytes(buf, offset + 12),
                        (Filetime.typeLayout()).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ByHandleFileInformation) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.dwFileAttributes)
                    (Filetime.typeLayout()).writeBytes(buf, offset + 4, value.ftCreationTime)
                    (Filetime.typeLayout()).writeBytes(buf, offset + 12, value.ftLastAccessTime)
                    (Filetime.typeLayout()).writeBytes(buf, offset + 20, value.ftLastWriteTime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.dwVolumeSerialNumber)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.nFileSizeHigh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.nFileSizeLow)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.nNumberOfLinks)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.nFileIndexHigh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.nFileIndexLow)
                }
            }
    }
}

public val CALLBACK_CHUNK_FINISHED: Int = 0

public val CALLBACK_STREAM_SWITCH: Int = 1

public typealias Cchar = Byte

public typealias ConditionVariable = RtlConditionVariable

public open class ConsoleReadconsoleControl(
    public var nLength: UInt,
    public var nInitialChars: UInt,
    public var dwCtrlWakeupMask: UInt,
    public var dwControlKeyState: UInt,
) {
    public open fun clone(): ConsoleReadconsoleControl = ConsoleReadconsoleControl(nLength, nInitialChars, dwCtrlWakeupMask, dwControlKeyState)

    public companion object {
        public fun typeLayout(): TypeLayout<ConsoleReadconsoleControl> =
            object : TypeLayout<ConsoleReadconsoleControl> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): ConsoleReadconsoleControl =
                    ConsoleReadconsoleControl(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: ConsoleReadconsoleControl) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nLength)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nInitialChars)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.dwCtrlWakeupMask)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.dwControlKeyState)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ConsoleReadconsoleControl =
                    ConsoleReadconsoleControl(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ConsoleReadconsoleControl) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nLength)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nInitialChars)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.dwCtrlWakeupMask)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.dwControlKeyState)
                }
            }
    }
}

public open class Context(
    public var p1Home: ULong,
    public var p2Home: ULong,
    public var p3Home: ULong,
    public var p4Home: ULong,
    public var p5Home: ULong,
    public var p6Home: ULong,
    public var contextFlags: UInt,
    public var mxCsr: UInt,
    public var segCs: UShort,
    public var segDs: UShort,
    public var segEs: UShort,
    public var segFs: UShort,
    public var segGs: UShort,
    public var segSs: UShort,
    public var eFlags: UInt,
    public var dr0: ULong,
    public var dr1: ULong,
    public var dr2: ULong,
    public var dr3: ULong,
    public var dr6: ULong,
    public var dr7: ULong,
    public var rax: ULong,
    public var rcx: ULong,
    public var rdx: ULong,
    public var rbx: ULong,
    public var rsp: ULong,
    public var rbp: ULong,
    public var rsi: ULong,
    public var rdi: ULong,
    public var r8: ULong,
    public var r9: ULong,
    public var r10: ULong,
    public var r11: ULong,
    public var r12: ULong,
    public var r13: ULong,
    public var r14: ULong,
    public var r15: ULong,
    public var rip: ULong,
    public var anonymous: Context0,
    public var vectorRegister: List<M128a>,
    public var vectorControl: ULong,
    public var debugControl: ULong,
    public var lastBranchToRip: ULong,
    public var lastBranchFromRip: ULong,
    public var lastExceptionToRip: ULong,
    public var lastExceptionFromRip: ULong,
) {
    public open fun clone(): Context = Context(p1Home, p2Home, p3Home, p4Home, p5Home, p6Home, contextFlags, mxCsr, segCs, segDs, segEs, segFs, segGs, segSs, eFlags, dr0, dr1, dr2, dr3, dr6, dr7, rax, rcx, rdx, rbx, rsp, rbp, rsi, rdi, r8, r9, r10, r11, r12, r13, r14, r15, rip, anonymous, vectorRegister, vectorControl, debugControl, lastBranchToRip, lastBranchFromRip, lastExceptionToRip, lastExceptionFromRip)

    public companion object {
        public fun default(): Context = null
    }
}

public open class Context0(
    public var fltSave: XmmSaveArea32? = null,
    public var anonymous: Context00? = null,
) {
    public companion object {
        public fun default(): Context0 = null
    }
}

public open class Context00(
    public var header: List<M128a>,
    public var legacy: List<M128a>,
    public var xmm0: M128a,
    public var xmm1: M128a,
    public var xmm2: M128a,
    public var xmm3: M128a,
    public var xmm4: M128a,
    public var xmm5: M128a,
    public var xmm6: M128a,
    public var xmm7: M128a,
    public var xmm8: M128a,
    public var xmm9: M128a,
    public var xmm10: M128a,
    public var xmm11: M128a,
    public var xmm12: M128a,
    public var xmm13: M128a,
    public var xmm14: M128a,
    public var xmm15: M128a,
) {
    public open fun clone(): Context00 = Context00(header, legacy, xmm0, xmm1, xmm2, xmm3, xmm4, xmm5, xmm6, xmm7, xmm8, xmm9, xmm10, xmm11, xmm12, xmm13, xmm14, xmm15)

    public companion object {
        public fun typeLayout(): TypeLayout<Context00> =
            object : TypeLayout<Context00> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 416L
                override fun readNative(addr: Long): Context00 =
                    Context00(
                        (fixedListTypeLayout(M128a.typeLayout(), 2)).readNative(addr),
                        (fixedListTypeLayout(M128a.typeLayout(), 8)).readNative(addr + 32L),
                        (M128a.typeLayout()).readNative(addr + 160L),
                        (M128a.typeLayout()).readNative(addr + 176L),
                        (M128a.typeLayout()).readNative(addr + 192L),
                        (M128a.typeLayout()).readNative(addr + 208L),
                        (M128a.typeLayout()).readNative(addr + 224L),
                        (M128a.typeLayout()).readNative(addr + 240L),
                        (M128a.typeLayout()).readNative(addr + 256L),
                        (M128a.typeLayout()).readNative(addr + 272L),
                        (M128a.typeLayout()).readNative(addr + 288L),
                        (M128a.typeLayout()).readNative(addr + 304L),
                        (M128a.typeLayout()).readNative(addr + 320L),
                        (M128a.typeLayout()).readNative(addr + 336L),
                        (M128a.typeLayout()).readNative(addr + 352L),
                        (M128a.typeLayout()).readNative(addr + 368L),
                        (M128a.typeLayout()).readNative(addr + 384L),
                        (M128a.typeLayout()).readNative(addr + 400L),
                    )
                override fun writeNative(addr: Long, value: Context00) {
                    (fixedListTypeLayout(M128a.typeLayout(), 2)).writeNative(addr, value.header)
                    (fixedListTypeLayout(M128a.typeLayout(), 8)).writeNative(addr + 32L, value.legacy)
                    (M128a.typeLayout()).writeNative(addr + 160L, value.xmm0)
                    (M128a.typeLayout()).writeNative(addr + 176L, value.xmm1)
                    (M128a.typeLayout()).writeNative(addr + 192L, value.xmm2)
                    (M128a.typeLayout()).writeNative(addr + 208L, value.xmm3)
                    (M128a.typeLayout()).writeNative(addr + 224L, value.xmm4)
                    (M128a.typeLayout()).writeNative(addr + 240L, value.xmm5)
                    (M128a.typeLayout()).writeNative(addr + 256L, value.xmm6)
                    (M128a.typeLayout()).writeNative(addr + 272L, value.xmm7)
                    (M128a.typeLayout()).writeNative(addr + 288L, value.xmm8)
                    (M128a.typeLayout()).writeNative(addr + 304L, value.xmm9)
                    (M128a.typeLayout()).writeNative(addr + 320L, value.xmm10)
                    (M128a.typeLayout()).writeNative(addr + 336L, value.xmm11)
                    (M128a.typeLayout()).writeNative(addr + 352L, value.xmm12)
                    (M128a.typeLayout()).writeNative(addr + 368L, value.xmm13)
                    (M128a.typeLayout()).writeNative(addr + 384L, value.xmm14)
                    (M128a.typeLayout()).writeNative(addr + 400L, value.xmm15)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Context00 =
                    Context00(
                        (fixedListTypeLayout(M128a.typeLayout(), 2)).readBytes(buf, offset),
                        (fixedListTypeLayout(M128a.typeLayout(), 8)).readBytes(buf, offset + 32),
                        (M128a.typeLayout()).readBytes(buf, offset + 160),
                        (M128a.typeLayout()).readBytes(buf, offset + 176),
                        (M128a.typeLayout()).readBytes(buf, offset + 192),
                        (M128a.typeLayout()).readBytes(buf, offset + 208),
                        (M128a.typeLayout()).readBytes(buf, offset + 224),
                        (M128a.typeLayout()).readBytes(buf, offset + 240),
                        (M128a.typeLayout()).readBytes(buf, offset + 256),
                        (M128a.typeLayout()).readBytes(buf, offset + 272),
                        (M128a.typeLayout()).readBytes(buf, offset + 288),
                        (M128a.typeLayout()).readBytes(buf, offset + 304),
                        (M128a.typeLayout()).readBytes(buf, offset + 320),
                        (M128a.typeLayout()).readBytes(buf, offset + 336),
                        (M128a.typeLayout()).readBytes(buf, offset + 352),
                        (M128a.typeLayout()).readBytes(buf, offset + 368),
                        (M128a.typeLayout()).readBytes(buf, offset + 384),
                        (M128a.typeLayout()).readBytes(buf, offset + 400),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Context00) {
                    (fixedListTypeLayout(M128a.typeLayout(), 2)).writeBytes(buf, offset, value.header)
                    (fixedListTypeLayout(M128a.typeLayout(), 8)).writeBytes(buf, offset + 32, value.legacy)
                    (M128a.typeLayout()).writeBytes(buf, offset + 160, value.xmm0)
                    (M128a.typeLayout()).writeBytes(buf, offset + 176, value.xmm1)
                    (M128a.typeLayout()).writeBytes(buf, offset + 192, value.xmm2)
                    (M128a.typeLayout()).writeBytes(buf, offset + 208, value.xmm3)
                    (M128a.typeLayout()).writeBytes(buf, offset + 224, value.xmm4)
                    (M128a.typeLayout()).writeBytes(buf, offset + 240, value.xmm5)
                    (M128a.typeLayout()).writeBytes(buf, offset + 256, value.xmm6)
                    (M128a.typeLayout()).writeBytes(buf, offset + 272, value.xmm7)
                    (M128a.typeLayout()).writeBytes(buf, offset + 288, value.xmm8)
                    (M128a.typeLayout()).writeBytes(buf, offset + 304, value.xmm9)
                    (M128a.typeLayout()).writeBytes(buf, offset + 320, value.xmm10)
                    (M128a.typeLayout()).writeBytes(buf, offset + 336, value.xmm11)
                    (M128a.typeLayout()).writeBytes(buf, offset + 352, value.xmm12)
                    (M128a.typeLayout()).writeBytes(buf, offset + 368, value.xmm13)
                    (M128a.typeLayout()).writeBytes(buf, offset + 384, value.xmm14)
                    (M128a.typeLayout()).writeBytes(buf, offset + 400, value.xmm15)
                }
            }

        public fun default(): Context00 = null
    }
}

public typealias Context = Arm64NtContext

public val CP_UTF8: Int = 65001

public val CREATE_ALWAYS: Int = 2

public val CREATE_BREAKAWAY_FROM_JOB: Int = 16777216

public val CREATE_DEFAULT_ERROR_MODE: Int = 67108864

public val CREATE_FORCEDOS: Int = 8192

public val CREATE_IGNORE_SYSTEM_DEFAULT: UInt = 2147483648u

public val CREATE_NEW: Int = 1

public val CREATE_NEW_CONSOLE: Int = 16

public val CREATE_NEW_PROCESS_GROUP: Int = 512

public val CREATE_NO_WINDOW: Int = 134217728

public val CREATE_PRESERVE_CODE_AUTHZ_LEVEL: Int = 33554432

public val CREATE_PROTECTED_PROCESS: Int = 262144

public val CREATE_SECURE_PROCESS: Int = 4194304

public val CREATE_SEPARATE_WOW_VDM: Int = 2048

public val CREATE_SHARED_WOW_VDM: Int = 4096

public val CREATE_SUSPENDED: Int = 4

public val CREATE_UNICODE_ENVIRONMENT: Int = 1024

public val CREATE_WAITABLE_TIMER_HIGH_RESOLUTION: Int = 2

public val CREATE_WAITABLE_TIMER_MANUAL_RESET: Int = 1

public val CSTR_EQUAL: Int = 2

public val CSTR_GREATER_THAN: Int = 3

public val CSTR_LESS_THAN: Int = 1

public val DEBUG_ONLY_THIS_PROCESS: Int = 2

public val DEBUG_PROCESS: Int = 1

public val DELETE: Int = 65536

public val DETACHED_PROCESS: Int = 8

public val DISABLE_NEWLINE_AUTO_RETURN: Int = 8

public val DLL_PROCESS_DETACH: Int = 0

public val DLL_THREAD_DETACH: Int = 3

public val DNS_ERROR_ADDRESS_REQUIRED: Int = 9573

public val DNS_ERROR_ALIAS_LOOP: Int = 9722

public val DNS_ERROR_AUTOZONE_ALREADY_EXISTS: Int = 9610

public val DNS_ERROR_AXFR: Int = 9752

public val DNS_ERROR_BACKGROUND_LOADING: Int = 9568

public val DNS_ERROR_BAD_KEYMASTER: Int = 9122

public val DNS_ERROR_BAD_PACKET: Int = 9502

public val DNS_ERROR_CANNOT_FIND_ROOT_HINTS: Int = 9564

public val DNS_ERROR_CLIENT_SUBNET_ALREADY_EXISTS: Int = 9977

public val DNS_ERROR_CLIENT_SUBNET_DOES_NOT_EXIST: Int = 9976

public val DNS_ERROR_CLIENT_SUBNET_IS_ACCESSED: Int = 9975

public val DNS_ERROR_CNAME_COLLISION: Int = 9709

public val DNS_ERROR_CNAME_LOOP: Int = 9707

public val DNS_ERROR_DATAFILE_OPEN_FAILURE: Int = 9653

public val DNS_ERROR_DATAFILE_PARSING: Int = 9655

public val DNS_ERROR_DEFAULT_SCOPE: Int = 9960

public val DNS_ERROR_DEFAULT_VIRTUALIZATION_INSTANCE: Int = 9925

public val DNS_ERROR_DEFAULT_ZONESCOPE: Int = 9953

public val DNS_ERROR_DELEGATION_REQUIRED: Int = 9571

public val DNS_ERROR_DNAME_COLLISION: Int = 9721

public val DNS_ERROR_DNSSEC_IS_DISABLED: Int = 9125

public val DNS_ERROR_DP_ALREADY_ENLISTED: Int = 9904

public val DNS_ERROR_DP_ALREADY_EXISTS: Int = 9902

public val DNS_ERROR_DP_DOES_NOT_EXIST: Int = 9901

public val DNS_ERROR_DP_FSMO_ERROR: Int = 9906

public val DNS_ERROR_DP_NOT_AVAILABLE: Int = 9905

public val DNS_ERROR_DP_NOT_ENLISTED: Int = 9903

public val DNS_ERROR_DS_UNAVAILABLE: Int = 9717

public val DNS_ERROR_DS_ZONE_ALREADY_EXISTS: Int = 9718

public val DNS_ERROR_DWORD_VALUE_TOO_LARGE: Int = 9567

public val DNS_ERROR_DWORD_VALUE_TOO_SMALL: Int = 9566

public val DNS_ERROR_FILE_WRITEBACK_FAILED: Int = 9654

public val DNS_ERROR_FORWARDER_ALREADY_EXISTS: Int = 9619

public val DNS_ERROR_INCONSISTENT_ROOT_HINTS: Int = 9565

public val DNS_ERROR_INVAILD_VIRTUALIZATION_INSTANCE_NAME: Int = 9924

public val DNS_ERROR_INVALID_CLIENT_SUBNET_NAME: Int = 9984

public val DNS_ERROR_INVALID_DATA: Int = 13

public val DNS_ERROR_INVALID_DATAFILE_NAME: Int = 9652

public val DNS_ERROR_INVALID_INITIAL_ROLLOVER_OFFSET: Int = 9115

public val DNS_ERROR_INVALID_IP_ADDRESS: Int = 9552

public val DNS_ERROR_INVALID_KEY_SIZE: Int = 9106

public val DNS_ERROR_INVALID_NAME: Int = 123

public val DNS_ERROR_INVALID_NAME_CHAR: Int = 9560

public val DNS_ERROR_INVALID_NSEC3_ITERATION_COUNT: Int = 9124

public val DNS_ERROR_INVALID_POLICY_TABLE: Int = 9572

public val DNS_ERROR_INVALID_PROPERTY: Int = 9553

public val DNS_ERROR_INVALID_ROLLOVER_PERIOD: Int = 9114

public val DNS_ERROR_INVALID_SCOPE_NAME: Int = 9958

public val DNS_ERROR_INVALID_SCOPE_OPERATION: Int = 9961

public val DNS_ERROR_INVALID_SIGNATURE_VALIDITY_PERIOD: Int = 9123

public val DNS_ERROR_INVALID_TYPE: Int = 9551

public val DNS_ERROR_INVALID_XML: Int = 9126

public val DNS_ERROR_INVALID_ZONESCOPE_NAME: Int = 9954

public val DNS_ERROR_INVALID_ZONE_OPERATION: Int = 9603

public val DNS_ERROR_INVALID_ZONE_TYPE: Int = 9611

public val DNS_ERROR_KEYMASTER_REQUIRED: Int = 9101

public val DNS_ERROR_KSP_DOES_NOT_SUPPORT_PROTECTION: Int = 9108

public val DNS_ERROR_KSP_NOT_ACCESSIBLE: Int = 9112

public val DNS_ERROR_LOAD_ZONESCOPE_FAILED: Int = 9956

public val DNS_ERROR_NAME_DOES_NOT_EXIST: Int = 9714

public val DNS_ERROR_NAME_NOT_IN_ZONE: Int = 9706

public val DNS_ERROR_NBSTAT_INIT_FAILED: Int = 9617

public val DNS_ERROR_NEED_SECONDARY_ADDRESSES: Int = 9614

public val DNS_ERROR_NEED_WINS_SERVERS: Int = 9616

public val DNS_ERROR_NODE_CREATION_FAILED: Int = 9703

public val DNS_ERROR_NODE_IS_CNAME: Int = 9708

public val DNS_ERROR_NODE_IS_DNAME: Int = 9720

public val DNS_ERROR_NON_RFC_NAME: Int = 9556

public val DNS_ERROR_NOT_ALLOWED_ON_ACTIVE_SKD: Int = 9119

public val DNS_ERROR_NOT_ALLOWED_ON_RODC: Int = 9569

public val DNS_ERROR_NOT_ALLOWED_ON_ROOT_SERVER: Int = 9562

public val DNS_ERROR_NOT_ALLOWED_ON_SIGNED_ZONE: Int = 9102

public val DNS_ERROR_NOT_ALLOWED_ON_UNSIGNED_ZONE: Int = 9121

public val DNS_ERROR_NOT_ALLOWED_ON_ZSK: Int = 9118

public val DNS_ERROR_NOT_ALLOWED_UNDER_DELEGATION: Int = 9563

public val DNS_ERROR_NOT_ALLOWED_UNDER_DNAME: Int = 9570

public val DNS_ERROR_NOT_ALLOWED_WITH_ZONESCOPES: Int = 9955

public val DNS_ERROR_NOT_ENOUGH_SIGNING_KEY_DESCRIPTORS: Int = 9104

public val DNS_ERROR_NOT_UNIQUE: Int = 9555

public val DNS_ERROR_NO_BOOTFILE_IF_DS_ZONE: Int = 9719

public val DNS_ERROR_NO_CREATE_CACHE_DATA: Int = 9713

public val DNS_ERROR_NO_DNS_SERVERS: Int = 9852

public val DNS_ERROR_NO_MEMORY: Int = 14

public val DNS_ERROR_NO_PACKET: Int = 9503

public val DNS_ERROR_NO_TCPIP: Int = 9851

public val DNS_ERROR_NO_VALID_TRUST_ANCHORS: Int = 9127

public val DNS_ERROR_NO_ZONE_INFO: Int = 9602

public val DNS_ERROR_NSEC3_INCOMPATIBLE_WITH_RSA_SHA1: Int = 9103

public val DNS_ERROR_NSEC3_NAME_COLLISION: Int = 9129

public val DNS_ERROR_NSEC_INCOMPATIBLE_WITH_NSEC3_RSA_SHA1: Int = 9130

public val DNS_ERROR_NUMERIC_NAME: Int = 9561

public val DNS_ERROR_POLICY_ALREADY_EXISTS: Int = 9971

public val DNS_ERROR_POLICY_DOES_NOT_EXIST: Int = 9972

public val DNS_ERROR_POLICY_INVALID_CRITERIA: Int = 9973

public val DNS_ERROR_POLICY_INVALID_CRITERIA_CLIENT_SUBNET: Int = 9990

public val DNS_ERROR_POLICY_INVALID_CRITERIA_FQDN: Int = 9994

public val DNS_ERROR_POLICY_INVALID_CRITERIA_INTERFACE: Int = 9993

public val DNS_ERROR_POLICY_INVALID_CRITERIA_NETWORK_PROTOCOL: Int = 9992

public val DNS_ERROR_POLICY_INVALID_CRITERIA_QUERY_TYPE: Int = 9995

public val DNS_ERROR_POLICY_INVALID_CRITERIA_TIME_OF_DAY: Int = 9996

public val DNS_ERROR_POLICY_INVALID_CRITERIA_TRANSPORT_PROTOCOL: Int = 9991

public val DNS_ERROR_POLICY_INVALID_NAME: Int = 9982

public val DNS_ERROR_POLICY_INVALID_SETTINGS: Int = 9974

public val DNS_ERROR_POLICY_INVALID_WEIGHT: Int = 9981

public val DNS_ERROR_POLICY_LOCKED: Int = 9980

public val DNS_ERROR_POLICY_MISSING_CRITERIA: Int = 9983

public val DNS_ERROR_POLICY_PROCESSING_ORDER_INVALID: Int = 9985

public val DNS_ERROR_POLICY_SCOPE_MISSING: Int = 9986

public val DNS_ERROR_POLICY_SCOPE_NOT_ALLOWED: Int = 9987

public val DNS_ERROR_PRIMARY_REQUIRES_DATAFILE: Int = 9651

public val DNS_ERROR_RCODE: Int = 9504

public val DNS_ERROR_RCODE_BADKEY: Int = 9017

public val DNS_ERROR_RCODE_BADSIG: Int = 9016

public val DNS_ERROR_RCODE_BADTIME: Int = 9018

public val DNS_ERROR_RCODE_FORMAT_ERROR: Int = 9001

public val DNS_ERROR_RCODE_NAME_ERROR: Int = 9003

public val DNS_ERROR_RCODE_NOTAUTH: Int = 9009

public val DNS_ERROR_RCODE_NOTZONE: Int = 9010

public val DNS_ERROR_RCODE_NOT_IMPLEMENTED: Int = 9004

public val DNS_ERROR_RCODE_NXRRSET: Int = 9008

public val DNS_ERROR_RCODE_REFUSED: Int = 9005

public val DNS_ERROR_RCODE_SERVER_FAILURE: Int = 9002

public val DNS_ERROR_RCODE_YXDOMAIN: Int = 9006

public val DNS_ERROR_RCODE_YXRRSET: Int = 9007

public val DNS_ERROR_RECORD_ALREADY_EXISTS: Int = 9711

public val DNS_ERROR_RECORD_DOES_NOT_EXIST: Int = 9701

public val DNS_ERROR_RECORD_FORMAT: Int = 9702

public val DNS_ERROR_RECORD_ONLY_AT_ZONE_ROOT: Int = 9710

public val DNS_ERROR_RECORD_TIMED_OUT: Int = 9705

public val DNS_ERROR_ROLLOVER_ALREADY_QUEUED: Int = 9120

public val DNS_ERROR_ROLLOVER_IN_PROGRESS: Int = 9116

public val DNS_ERROR_ROLLOVER_NOT_POKEABLE: Int = 9128

public val DNS_ERROR_RRL_INVALID_IPV4_PREFIX: Int = 9913

public val DNS_ERROR_RRL_INVALID_IPV6_PREFIX: Int = 9914

public val DNS_ERROR_RRL_INVALID_LEAK_RATE: Int = 9916

public val DNS_ERROR_RRL_INVALID_TC_RATE: Int = 9915

public val DNS_ERROR_RRL_INVALID_WINDOW_SIZE: Int = 9912

public val DNS_ERROR_RRL_LEAK_RATE_LESSTHAN_TC_RATE: Int = 9917

public val DNS_ERROR_RRL_NOT_ENABLED: Int = 9911

public val DNS_ERROR_SCOPE_ALREADY_EXISTS: Int = 9963

public val DNS_ERROR_SCOPE_DOES_NOT_EXIST: Int = 9959

public val DNS_ERROR_SCOPE_LOCKED: Int = 9962

public val DNS_ERROR_SECONDARY_DATA: Int = 9712

public val DNS_ERROR_SECONDARY_REQUIRES_MASTER_IP: Int = 9612

public val DNS_ERROR_SERVERSCOPE_IS_REFERENCED: Int = 9988

public val DNS_ERROR_SIGNING_KEY_NOT_ACCESSIBLE: Int = 9107

public val DNS_ERROR_SOA_DELETE_INVALID: Int = 9618

public val DNS_ERROR_STANDBY_KEY_NOT_PRESENT: Int = 9117

public val DNS_ERROR_SUBNET_ALREADY_EXISTS: Int = 9979

public val DNS_ERROR_SUBNET_DOES_NOT_EXIST: Int = 9978

public val DNS_ERROR_TOO_MANY_SKDS: Int = 9113

public val DNS_ERROR_TRY_AGAIN_LATER: Int = 9554

public val DNS_ERROR_UNEXPECTED_CNG_ERROR: Int = 9110

public val DNS_ERROR_UNEXPECTED_DATA_PROTECTION_ERROR: Int = 9109

public val DNS_ERROR_UNKNOWN_RECORD_TYPE: Int = 9704

public val DNS_ERROR_UNKNOWN_SIGNING_PARAMETER_VERSION: Int = 9111

public val DNS_ERROR_UNSECURE_PACKET: Int = 9505

public val DNS_ERROR_UNSUPPORTED_ALGORITHM: Int = 9105

public val DNS_ERROR_VIRTUALIZATION_INSTANCE_ALREADY_EXISTS: Int = 9921

public val DNS_ERROR_VIRTUALIZATION_INSTANCE_DOES_NOT_EXIST: Int = 9922

public val DNS_ERROR_VIRTUALIZATION_TREE_LOCKED: Int = 9923

public val DNS_ERROR_WINS_INIT_FAILED: Int = 9615

public val DNS_ERROR_ZONESCOPE_ALREADY_EXISTS: Int = 9951

public val DNS_ERROR_ZONESCOPE_DOES_NOT_EXIST: Int = 9952

public val DNS_ERROR_ZONESCOPE_FILE_WRITEBACK_FAILED: Int = 9957

public val DNS_ERROR_ZONESCOPE_IS_REFERENCED: Int = 9989

public val DNS_ERROR_ZONE_ALREADY_EXISTS: Int = 9609

public val DNS_ERROR_ZONE_CONFIGURATION_ERROR: Int = 9604

public val DNS_ERROR_ZONE_CREATION_FAILED: Int = 9608

public val DNS_ERROR_ZONE_DOES_NOT_EXIST: Int = 9601

public val DNS_ERROR_ZONE_HAS_NO_NS_RECORDS: Int = 9606

public val DNS_ERROR_ZONE_HAS_NO_SOA_RECORD: Int = 9605

public val DNS_ERROR_ZONE_IS_SHUTDOWN: Int = 9621

public val DNS_ERROR_ZONE_LOCKED: Int = 9607

public val DNS_ERROR_ZONE_LOCKED_FOR_SIGNING: Int = 9622

public val DNS_ERROR_ZONE_NOT_SECONDARY: Int = 9613

public val DNS_ERROR_ZONE_REQUIRES_MASTER_IP: Int = 9620

public val DUPLICATE_CLOSE_SOURCE: Int = 1

public val DUPLICATE_SAME_ACCESS: Int = 2

public val ENABLE_AUTO_POSITION: Int = 256

public val ENABLE_ECHO_INPUT: Int = 4

public val ENABLE_EXTENDED_FLAGS: Int = 128

public val ENABLE_INSERT_MODE: Int = 32

public val ENABLE_LINE_INPUT: Int = 2

public val ENABLE_LVB_GRID_WORLDWIDE: Int = 16

public val ENABLE_MOUSE_INPUT: Int = 16

public val ENABLE_PROCESSED_INPUT: Int = 1

public val ENABLE_PROCESSED_OUTPUT: Int = 1

public val ENABLE_QUICK_EDIT_MODE: Int = 64

public val ENABLE_VIRTUAL_TERMINAL_INPUT: Int = 512

public val ENABLE_VIRTUAL_TERMINAL_PROCESSING: Int = 4

public val ENABLE_WINDOW_INPUT: Int = 8

public val ENABLE_WRAP_AT_EOL_OUTPUT: Int = 2

public val ERROR_ABANDONED_WAIT_0: Int = 735

public val ERROR_ABANDONED_WAIT_63: Int = 736

public val ERROR_ABANDON_HIBERFILE: Int = 787

public val ERROR_ABIOS_ERROR: Int = 538

public val ERROR_ACCESS_AUDIT_BY_POLICY: Int = 785

public val ERROR_ACCESS_DENIED: Int = 5

public val ERROR_ACCESS_DENIED_APPDATA: Int = 502

public val ERROR_ACCESS_DISABLED_BY_POLICY: Int = 1260

public val ERROR_ACCESS_DISABLED_NO_SAFER_UI_BY_POLICY: Int = 786

public val ERROR_ACCESS_DISABLED_WEBBLADE: Int = 1277

public val ERROR_ACCESS_DISABLED_WEBBLADE_TAMPER: Int = 1278

public val ERROR_ACCOUNT_DISABLED: Int = 1331

public val ERROR_ACCOUNT_EXPIRED: Int = 1793

public val ERROR_ACCOUNT_LOCKED_OUT: Int = 1909

public val ERROR_ACCOUNT_RESTRICTION: Int = 1327

public val ERROR_ACPI_ERROR: Int = 669

public val ERROR_ACTIVE_CONNECTIONS: Int = 2402

public val ERROR_ADAP_HDW_ERR: Int = 57

public val ERROR_ADDRESS_ALREADY_ASSOCIATED: Int = 1227

public val ERROR_ADDRESS_NOT_ASSOCIATED: Int = 1228

public val ERROR_ALERTED: Int = 739

public val ERROR_ALIAS_EXISTS: Int = 1379

public val ERROR_ALLOCATE_BUCKET: Int = 602

public val ERROR_ALLOTTED_SPACE_EXCEEDED: Int = 1344

public val ERROR_ALL_USER_TRUST_QUOTA_EXCEEDED: Int = 1933

public val ERROR_ALREADY_ASSIGNED: Int = 85

public val ERROR_ALREADY_EXISTS: Int = 183

public val ERROR_ALREADY_FIBER: Int = 1280

public val ERROR_ALREADY_HAS_STREAM_ID: Int = 4444

public val ERROR_ALREADY_INITIALIZED: Int = 1247

public val ERROR_ALREADY_REGISTERED: Int = 1242

public val ERROR_ALREADY_RUNNING_LKG: Int = 1074

public val ERROR_ALREADY_THREAD: Int = 1281

public val ERROR_ALREADY_WAITING: Int = 1904

public val ERROR_ALREADY_WIN32: Int = 719

public val ERROR_API_UNAVAILABLE: Int = 15841

public val ERROR_APPCONTAINER_REQUIRED: Int = 4251

public val ERROR_APPEXEC_APP_COMPAT_BLOCK: Int = 3068

public val ERROR_APPEXEC_CALLER_WAIT_TIMEOUT: Int = 3069

public val ERROR_APPEXEC_CALLER_WAIT_TIMEOUT_LICENSING: Int = 3071

public val ERROR_APPEXEC_CALLER_WAIT_TIMEOUT_RESOURCES: Int = 3072

public val ERROR_APPEXEC_CALLER_WAIT_TIMEOUT_TERMINATION: Int = 3070

public val ERROR_APPEXEC_CONDITION_NOT_SATISFIED: Int = 3060

public val ERROR_APPEXEC_HANDLE_INVALIDATED: Int = 3061

public val ERROR_APPEXEC_HOST_ID_MISMATCH: Int = 3066

public val ERROR_APPEXEC_INVALID_HOST_GENERATION: Int = 3062

public val ERROR_APPEXEC_INVALID_HOST_STATE: Int = 3064

public val ERROR_APPEXEC_NO_DONOR: Int = 3065

public val ERROR_APPEXEC_UNEXPECTED_PROCESS_REGISTRATION: Int = 3063

public val ERROR_APPEXEC_UNKNOWN_USER: Int = 3067

public val ERROR_APPHELP_BLOCK: Int = 1259

public val ERROR_APPX_FILE_NOT_ENCRYPTED: Int = 409

public val ERROR_APP_HANG: Int = 1298

public val ERROR_APP_INIT_FAILURE: Int = 575

public val ERROR_APP_WRONG_OS: Int = 1151

public val ERROR_ARBITRATION_UNHANDLED: Int = 723

public val ERROR_ARENA_TRASHED: Int = 7

public val ERROR_ARITHMETIC_OVERFLOW: Int = 534

public val ERROR_ASSERTION_FAILURE: Int = 668

public val ERROR_ATOMIC_LOCKS_NOT_SUPPORTED: Int = 174

public val ERROR_AUDIT_FAILED: Int = 606

public val ERROR_AUTHENTICATION_FIREWALL_FAILED: Int = 1935

public val ERROR_AUTHIP_FAILURE: Int = 1469

public val ERROR_AUTODATASEG_EXCEEDS_64K: Int = 199

public val ERROR_BACKUP_CONTROLLER: Int = 586

public val ERROR_BADDB: Int = 1009

public val ERROR_BADKEY: Int = 1010

public val ERROR_BADSTARTPOSITION: Int = 778

public val ERROR_BAD_ACCESSOR_FLAGS: Int = 773

public val ERROR_BAD_ARGUMENTS: Int = 160

public val ERROR_BAD_COMMAND: Int = 22

public val ERROR_BAD_COMPRESSION_BUFFER: Int = 605

public val ERROR_BAD_CONFIGURATION: Int = 1610

public val ERROR_BAD_CURRENT_DIRECTORY: Int = 703

public val ERROR_BAD_DESCRIPTOR_FORMAT: Int = 1361

public val ERROR_BAD_DEVICE: Int = 1200

public val ERROR_BAD_DEVICE_PATH: Int = 330

public val ERROR_BAD_DEV_TYPE: Int = 66

public val ERROR_BAD_DLL_ENTRYPOINT: Int = 609

public val ERROR_BAD_DRIVER_LEVEL: Int = 119

public val ERROR_BAD_ENVIRONMENT: Int = 10

public val ERROR_BAD_EXE_FORMAT: Int = 193

public val ERROR_BAD_FILE_TYPE: Int = 222

public val ERROR_BAD_FORMAT: Int = 11

public val ERROR_BAD_FUNCTION_TABLE: Int = 559

public val ERROR_BAD_IMPERSONATION_LEVEL: Int = 1346

public val ERROR_BAD_INHERITANCE_ACL: Int = 1340

public val ERROR_BAD_LENGTH: Int = 24

public val ERROR_BAD_LOGON_SESSION_STATE: Int = 1365

public val ERROR_BAD_MCFG_TABLE: Int = 791

public val ERROR_BAD_NETPATH: Int = 53

public val ERROR_BAD_NET_NAME: Int = 67

public val ERROR_BAD_NET_RESP: Int = 58

public val ERROR_BAD_PATHNAME: Int = 161

public val ERROR_BAD_PIPE: Int = 230

public val ERROR_BAD_PROFILE: Int = 1206

public val ERROR_BAD_PROVIDER: Int = 1204

public val ERROR_BAD_QUERY_SYNTAX: Int = 1615

public val ERROR_BAD_RECOVERY_POLICY: Int = 6012

public val ERROR_BAD_REM_ADAP: Int = 60

public val ERROR_BAD_SERVICE_ENTRYPOINT: Int = 610

public val ERROR_BAD_STACK: Int = 543

public val ERROR_BAD_THREADID_ADDR: Int = 159

public val ERROR_BAD_TOKEN_TYPE: Int = 1349

public val ERROR_BAD_UNIT: Int = 20

public val ERROR_BAD_USERNAME: Int = 2202

public val ERROR_BAD_USER_PROFILE: Int = 1253

public val ERROR_BAD_VALIDATION_CLASS: Int = 1348

public val ERROR_BEGINNING_OF_MEDIA: Int = 1102

public val ERROR_BEYOND_VDL: Int = 1289

public val ERROR_BIOS_FAILED_TO_CONNECT_INTERRUPT: Int = 585

public val ERROR_BLOCKED_BY_PARENTAL_CONTROLS: Int = 346

public val ERROR_BLOCK_SHARED: Int = 514

public val ERROR_BLOCK_SOURCE_WEAK_REFERENCE_INVALID: Int = 512

public val ERROR_BLOCK_TARGET_WEAK_REFERENCE_INVALID: Int = 513

public val ERROR_BLOCK_TOO_MANY_REFERENCES: Int = 347

public val ERROR_BLOCK_WEAK_REFERENCE_INVALID: Int = 511

public val ERROR_BOOT_ALREADY_ACCEPTED: Int = 1076

public val ERROR_BROKEN_PIPE: Int = 109

public val ERROR_BUFFER_ALL_ZEROS: Int = 754

public val ERROR_BUFFER_OVERFLOW: Int = 111

public val ERROR_BUSY: Int = 170

public val ERROR_BUSY_DRIVE: Int = 142

public val ERROR_BUS_RESET: Int = 1111

public val ERROR_BYPASSIO_FLT_NOT_SUPPORTED: Int = 506

public val ERROR_CACHE_PAGE_LOCKED: Int = 752

public val ERROR_CALLBACK_INVOKE_INLINE: Int = 812

public val ERROR_CALLBACK_POP_STACK: Int = 768

public val ERROR_CALLBACK_SUPPLIED_INVALID_DATA: Int = 1273

public val ERROR_CALL_NOT_IMPLEMENTED: Int = 120

public val ERROR_CANCELLED: Int = 1223

public val ERROR_CANCEL_VIOLATION: Int = 173

public val ERROR_CANNOT_BREAK_OPLOCK: Int = 802

public val ERROR_CANNOT_COPY: Int = 266

public val ERROR_CANNOT_DETECT_DRIVER_FAILURE: Int = 1080

public val ERROR_CANNOT_DETECT_PROCESS_ABORT: Int = 1081

public val ERROR_CANNOT_FIND_WND_CLASS: Int = 1407

public val ERROR_CANNOT_GRANT_REQUESTED_OPLOCK: Int = 801

public val ERROR_CANNOT_IMPERSONATE: Int = 1368

public val ERROR_CANNOT_LOAD_REGISTRY_FILE: Int = 589

public val ERROR_CANNOT_MAKE: Int = 82

public val ERROR_CANNOT_OPEN_PROFILE: Int = 1205

public val ERROR_CANTFETCHBACKWARDS: Int = 770

public val ERROR_CANTOPEN: Int = 1011

public val ERROR_CANTREAD: Int = 1012

public val ERROR_CANTSCROLLBACKWARDS: Int = 771

public val ERROR_CANTWRITE: Int = 1013

public val ERROR_CANT_ACCESS_DOMAIN_INFO: Int = 1351

public val ERROR_CANT_ACCESS_FILE: Int = 1920

public val ERROR_CANT_CLEAR_ENCRYPTION_FLAG: Int = 432

public val ERROR_CANT_DISABLE_MANDATORY: Int = 1310

public val ERROR_CANT_ENABLE_DENY_ONLY: Int = 629

public val ERROR_CANT_OPEN_ANONYMOUS: Int = 1347

public val ERROR_CANT_RESOLVE_FILENAME: Int = 1921

public val ERROR_CANT_TERMINATE_SELF: Int = 555

public val ERROR_CANT_WAIT: Int = 554

public val ERROR_CAN_NOT_COMPLETE: Int = 1003

public val ERROR_CAPAUTHZ_CHANGE_TYPE: Int = 451

public val ERROR_CAPAUTHZ_DB_CORRUPTED: Int = 455

public val ERROR_CAPAUTHZ_NOT_AUTHORIZED: Int = 453

public val ERROR_CAPAUTHZ_NOT_DEVUNLOCKED: Int = 450

public val ERROR_CAPAUTHZ_NOT_PROVISIONED: Int = 452

public val ERROR_CAPAUTHZ_NO_POLICY: Int = 454

public val ERROR_CAPAUTHZ_SCCD_DEV_MODE_REQUIRED: Int = 459

public val ERROR_CAPAUTHZ_SCCD_INVALID_CATALOG: Int = 456

public val ERROR_CAPAUTHZ_SCCD_NO_AUTH_ENTITY: Int = 457

public val ERROR_CAPAUTHZ_SCCD_NO_CAPABILITY_MATCH: Int = 460

public val ERROR_CAPAUTHZ_SCCD_PARSE_ERROR: Int = 458

public val ERROR_CARDBUS_NOT_SUPPORTED: Int = 724

public val ERROR_CASE_DIFFERING_NAMES_IN_DIR: Int = 424

public val ERROR_CASE_SENSITIVE_PATH: Int = 442

public val ERROR_CERTIFICATE_VALIDATION_PREFERENCE_CONFLICT: Int = 817

public val ERROR_CHECKING_FILE_SYSTEM: Int = 712

public val ERROR_CHECKOUT_REQUIRED: Int = 221

public val ERROR_CHILD_MUST_BE_VOLATILE: Int = 1021

public val ERROR_CHILD_NOT_COMPLETE: Int = 129

public val ERROR_CHILD_PROCESS_BLOCKED: Int = 367

public val ERROR_CHILD_WINDOW_MENU: Int = 1436

public val ERROR_CIMFS_IMAGE_CORRUPT: Int = 470

public val ERROR_CIMFS_IMAGE_VERSION_NOT_SUPPORTED: Int = 471

public val ERROR_CIRCULAR_DEPENDENCY: Int = 1059

public val ERROR_CLASS_ALREADY_EXISTS: Int = 1410

public val ERROR_CLASS_DOES_NOT_EXIST: Int = 1411

public val ERROR_CLASS_HAS_WINDOWS: Int = 1412

public val ERROR_CLIENT_SERVER_PARAMETERS_INVALID: Int = 597

public val ERROR_CLIPBOARD_NOT_OPEN: Int = 1418

public val ERROR_CLOUD_FILE_ACCESS_DENIED: Int = 395

public val ERROR_CLOUD_FILE_ALREADY_CONNECTED: Int = 378

public val ERROR_CLOUD_FILE_AUTHENTICATION_FAILED: Int = 386

public val ERROR_CLOUD_FILE_CONNECTED_PROVIDER_ONLY: Int = 382

public val ERROR_CLOUD_FILE_DEHYDRATION_DISALLOWED: Int = 434

public val ERROR_CLOUD_FILE_INCOMPATIBLE_HARDLINKS: Int = 396

public val ERROR_CLOUD_FILE_INSUFFICIENT_RESOURCES: Int = 387

public val ERROR_CLOUD_FILE_INVALID_REQUEST: Int = 380

public val ERROR_CLOUD_FILE_IN_USE: Int = 391

public val ERROR_CLOUD_FILE_METADATA_CORRUPT: Int = 363

public val ERROR_CLOUD_FILE_METADATA_TOO_LARGE: Int = 364

public val ERROR_CLOUD_FILE_NETWORK_UNAVAILABLE: Int = 388

public val ERROR_CLOUD_FILE_NOT_IN_SYNC: Int = 377

public val ERROR_CLOUD_FILE_NOT_SUPPORTED: Int = 379

public val ERROR_CLOUD_FILE_NOT_UNDER_SYNC_ROOT: Int = 390

public val ERROR_CLOUD_FILE_PINNED: Int = 392

public val ERROR_CLOUD_FILE_PROPERTY_BLOB_CHECKSUM_MISMATCH: Int = 366

public val ERROR_CLOUD_FILE_PROPERTY_BLOB_TOO_LARGE: Int = 365

public val ERROR_CLOUD_FILE_PROPERTY_CORRUPT: Int = 394

public val ERROR_CLOUD_FILE_PROPERTY_LOCK_CONFLICT: Int = 397

public val ERROR_CLOUD_FILE_PROPERTY_VERSION_NOT_SUPPORTED: Int = 375

public val ERROR_CLOUD_FILE_PROVIDER_NOT_RUNNING: Int = 362

public val ERROR_CLOUD_FILE_PROVIDER_TERMINATED: Int = 404

public val ERROR_CLOUD_FILE_READ_ONLY_VOLUME: Int = 381

public val ERROR_CLOUD_FILE_REQUEST_ABORTED: Int = 393

public val ERROR_CLOUD_FILE_REQUEST_CANCELED: Int = 398

public val ERROR_CLOUD_FILE_REQUEST_TIMEOUT: Int = 426

public val ERROR_CLOUD_FILE_SYNC_ROOT_METADATA_CORRUPT: Int = 358

public val ERROR_CLOUD_FILE_TOO_MANY_PROPERTY_BLOBS: Int = 374

public val ERROR_CLOUD_FILE_UNSUCCESSFUL: Int = 389

public val ERROR_CLOUD_FILE_US_MESSAGE_TIMEOUT: Int = 475

public val ERROR_CLOUD_FILE_VALIDATION_FAILED: Int = 383

public val ERROR_COMMITMENT_LIMIT: Int = 1455

public val ERROR_COMMITMENT_MINIMUM: Int = 635

public val ERROR_COMPRESSED_FILE_NOT_SUPPORTED: Int = 335

public val ERROR_COMPRESSION_DISABLED: Int = 769

public val ERROR_COMPRESSION_NOT_BENEFICIAL: Int = 344

public val ERROR_CONNECTED_OTHER_PASSWORD: Int = 2108

public val ERROR_CONNECTED_OTHER_PASSWORD_DEFAULT: Int = 2109

public val ERROR_CONNECTION_ABORTED: Int = 1236

public val ERROR_CONNECTION_ACTIVE: Int = 1230

public val ERROR_CONNECTION_COUNT_LIMIT: Int = 1238

public val ERROR_CONNECTION_INVALID: Int = 1229

public val ERROR_CONNECTION_REFUSED: Int = 1225

public val ERROR_CONNECTION_UNAVAIL: Int = 1201

public val ERROR_CONTAINER_ASSIGNED: Int = 1504

public val ERROR_CONTENT_BLOCKED: Int = 1296

public val ERROR_CONTEXT_EXPIRED: Int = 1931

public val ERROR_CONTINUE: Int = 1246

public val ERROR_CONTROL_C_EXIT: Int = 572

public val ERROR_CONTROL_ID_NOT_FOUND: Int = 1421

public val ERROR_CONVERT_TO_LARGE: Int = 600

public val ERROR_CORRUPT_LOG_CLEARED: Int = 798

public val ERROR_CORRUPT_LOG_CORRUPTED: Int = 795

public val ERROR_CORRUPT_LOG_DELETED_FULL: Int = 797

public val ERROR_CORRUPT_LOG_OVERFULL: Int = 794

public val ERROR_CORRUPT_LOG_UNAVAILABLE: Int = 796

public val ERROR_CORRUPT_SYSTEM_FILE: Int = 634

public val ERROR_COULD_NOT_INTERPRET: Int = 552

public val ERROR_COUNTER_TIMEOUT: Int = 1121

public val ERROR_CPU_SET_INVALID: Int = 813

public val ERROR_CRASH_DUMP: Int = 753

public val ERROR_CRC: Int = 23

public val ERROR_CREATE_FAILED: Int = 1631

public val ERROR_CROSS_PARTITION_VIOLATION: Int = 1661

public val ERROR_CSCSHARE_OFFLINE: Int = 1262

public val ERROR_CS_ENCRYPTION_EXISTING_ENCRYPTED_FILE: Int = 6019

public val ERROR_CS_ENCRYPTION_FILE_NOT_CSE: Int = 6021

public val ERROR_CS_ENCRYPTION_INVALID_SERVER_RESPONSE: Int = 6017

public val ERROR_CS_ENCRYPTION_NEW_ENCRYPTED_FILE: Int = 6020

public val ERROR_CS_ENCRYPTION_UNSUPPORTED_SERVER: Int = 6018

public val ERROR_CTX_CLIENT_QUERY_TIMEOUT: Int = 7040

public val ERROR_CTX_MODEM_RESPONSE_TIMEOUT: Int = 7012

public val ERROR_CURRENT_DIRECTORY: Int = 16

public val ERROR_CURRENT_DOMAIN_NOT_ALLOWED: Int = 1399

public val ERROR_DATABASE_DOES_NOT_EXIST: Int = 1065

public val ERROR_DATATYPE_MISMATCH: Int = 1629

public val ERROR_DATA_CHECKSUM_ERROR: Int = 323

public val ERROR_DATA_NOT_ACCEPTED: Int = 592

public val ERROR_DAX_MAPPING_EXISTS: Int = 361

public val ERROR_DBG_COMMAND_EXCEPTION: Int = 697

public val ERROR_DBG_CONTINUE: Int = 767

public val ERROR_DBG_CONTROL_BREAK: Int = 696

public val ERROR_DBG_CONTROL_C: Int = 693

public val ERROR_DBG_EXCEPTION_HANDLED: Int = 766

public val ERROR_DBG_EXCEPTION_NOT_HANDLED: Int = 688

public val ERROR_DBG_PRINTEXCEPTION_C: Int = 694

public val ERROR_DBG_REPLY_LATER: Int = 689

public val ERROR_DBG_RIPEXCEPTION: Int = 695

public val ERROR_DBG_TERMINATE_PROCESS: Int = 692

public val ERROR_DBG_TERMINATE_THREAD: Int = 691

public val ERROR_DBG_UNABLE_TO_PROVIDE_HANDLE: Int = 690

public val ERROR_DC_NOT_FOUND: Int = 1425

public val ERROR_DDE_FAIL: Int = 1156

public val ERROR_DEBUGGER_INACTIVE: Int = 1284

public val ERROR_DEBUG_ATTACH_FAILED: Int = 590

public val ERROR_DECRYPTION_FAILED: Int = 6001

public val ERROR_DELAY_LOAD_FAILED: Int = 1285

public val ERROR_DELETE_PENDING: Int = 303

public val ERROR_DEPENDENT_SERVICES_RUNNING: Int = 1051

public val ERROR_DESTINATION_ELEMENT_FULL: Int = 1161

public val ERROR_DESTROY_OBJECT_OF_OTHER_THREAD: Int = 1435

public val ERROR_DEVICE_ALREADY_ATTACHED: Int = 548

public val ERROR_DEVICE_ALREADY_REMEMBERED: Int = 1202

public val ERROR_DEVICE_DOOR_OPEN: Int = 1166

public val ERROR_DEVICE_ENUMERATION_ERROR: Int = 648

public val ERROR_DEVICE_FEATURE_NOT_SUPPORTED: Int = 316

public val ERROR_DEVICE_HARDWARE_ERROR: Int = 483

public val ERROR_DEVICE_HINT_NAME_BUFFER_TOO_SMALL: Int = 355

public val ERROR_DEVICE_IN_MAINTENANCE: Int = 359

public val ERROR_DEVICE_IN_USE: Int = 2404

public val ERROR_DEVICE_NOT_CONNECTED: Int = 1167

public val ERROR_DEVICE_NOT_PARTITIONED: Int = 1107

public val ERROR_DEVICE_NO_RESOURCES: Int = 322

public val ERROR_DEVICE_REINITIALIZATION_NEEDED: Int = 1164

public val ERROR_DEVICE_REMOVED: Int = 1617

public val ERROR_DEVICE_REQUIRES_CLEANING: Int = 1165

public val ERROR_DEVICE_RESET_REQUIRED: Int = 507

public val ERROR_DEVICE_SUPPORT_IN_PROGRESS: Int = 171

public val ERROR_DEVICE_UNREACHABLE: Int = 321

public val ERROR_DEV_NOT_EXIST: Int = 55

public val ERROR_DHCP_ADDRESS_CONFLICT: Int = 4100

public val ERROR_DIFFERENT_SERVICE_ACCOUNT: Int = 1079

public val ERROR_DIRECTORY: Int = 267

public val ERROR_DIRECTORY_NOT_SUPPORTED: Int = 336

public val ERROR_DIRECT_ACCESS_HANDLE: Int = 130

public val ERROR_DIR_EFS_DISALLOWED: Int = 6010

public val ERROR_DIR_NOT_EMPTY: Int = 145

public val ERROR_DIR_NOT_ROOT: Int = 144

public val ERROR_DISCARDED: Int = 157

public val ERROR_DISK_CHANGE: Int = 107

public val ERROR_DISK_CORRUPT: Int = 1393

public val ERROR_DISK_FULL: Int = 112

public val ERROR_DISK_OPERATION_FAILED: Int = 1127

public val ERROR_DISK_QUOTA_EXCEEDED: Int = 1295

public val ERROR_DISK_RECALIBRATE_FAILED: Int = 1126

public val ERROR_DISK_REPAIR_DISABLED: Int = 780

public val ERROR_DISK_REPAIR_REDIRECTED: Int = 792

public val ERROR_DISK_REPAIR_UNSUCCESSFUL: Int = 793

public val ERROR_DISK_RESET_FAILED: Int = 1128

public val ERROR_DISK_RESOURCES_EXHAUSTED: Int = 314

public val ERROR_DISK_TOO_FRAGMENTED: Int = 302

public val ERROR_DLL_INIT_FAILED: Int = 1114

public val ERROR_DLL_INIT_FAILED_LOGOFF: Int = 624

public val ERROR_DLL_MIGHT_BE_INCOMPATIBLE: Int = 687

public val ERROR_DLL_MIGHT_BE_INSECURE: Int = 686

public val ERROR_DLL_NOT_FOUND: Int = 1157

public val ERROR_DLP_POLICY_DENIES_OPERATION: Int = 446

public val ERROR_DLP_POLICY_SILENTLY_FAIL: Int = 449

public val ERROR_DLP_POLICY_WARNS_AGAINST_OPERATION: Int = 445

public val ERROR_DOMAIN_CONTROLLER_EXISTS: Int = 1250

public val ERROR_DOMAIN_CONTROLLER_NOT_FOUND: Int = 1908

public val ERROR_DOMAIN_CTRLR_CONFIG_ERROR: Int = 581

public val ERROR_DOMAIN_EXISTS: Int = 1356

public val ERROR_DOMAIN_LIMIT_EXCEEDED: Int = 1357

public val ERROR_DOMAIN_SID_SAME_AS_LOCAL_WORKSTATION: Int = 8644

public val ERROR_DOMAIN_TRUST_INCONSISTENT: Int = 1810

public val ERROR_DOWNGRADE_DETECTED: Int = 1265

public val ERROR_DPL_NOT_SUPPORTED_FOR_USER: Int = 423

public val ERROR_DRIVERS_LEAKING_LOCKED_PAGES: Int = 729

public val ERROR_DRIVER_BLOCKED: Int = 1275

public val ERROR_DRIVER_CANCEL_TIMEOUT: Int = 594

public val ERROR_DRIVER_DATABASE_ERROR: Int = 652

public val ERROR_DRIVER_FAILED_PRIOR_UNLOAD: Int = 654

public val ERROR_DRIVER_FAILED_SLEEP: Int = 633

public val ERROR_DRIVER_PROCESS_TERMINATED: Int = 1291

public val ERROR_DRIVE_LOCKED: Int = 108

public val ERROR_DS_ADD_REPLICA_INHIBITED: Int = 8302

public val ERROR_DS_ADMIN_LIMIT_EXCEEDED: Int = 8228

public val ERROR_DS_AFFECTS_MULTIPLE_DSAS: Int = 8249

public val ERROR_DS_AG_CANT_HAVE_UNIVERSAL_MEMBER: Int = 8578

public val ERROR_DS_ALIASED_OBJ_MISSING: Int = 8334

public val ERROR_DS_ALIAS_DEREF_PROBLEM: Int = 8244

public val ERROR_DS_ALIAS_POINTS_TO_ALIAS: Int = 8336

public val ERROR_DS_ALIAS_PROBLEM: Int = 8241

public val ERROR_DS_ATTRIBUTE_OR_VALUE_EXISTS: Int = 8205

public val ERROR_DS_ATTRIBUTE_OWNED_BY_SAM: Int = 8346

public val ERROR_DS_ATTRIBUTE_TYPE_UNDEFINED: Int = 8204

public val ERROR_DS_ATT_ALREADY_EXISTS: Int = 8318

public val ERROR_DS_ATT_IS_NOT_ON_OBJ: Int = 8310

public val ERROR_DS_ATT_NOT_DEF_FOR_CLASS: Int = 8317

public val ERROR_DS_ATT_NOT_DEF_IN_SCHEMA: Int = 8303

public val ERROR_DS_ATT_SCHEMA_REQ_ID: Int = 8399

public val ERROR_DS_ATT_SCHEMA_REQ_SYNTAX: Int = 8416

public val ERROR_DS_ATT_VAL_ALREADY_EXISTS: Int = 8323

public val ERROR_DS_AUDIT_FAILURE: Int = 8625

public val ERROR_DS_AUTHORIZATION_FAILED: Int = 8599

public val ERROR_DS_AUTH_METHOD_NOT_SUPPORTED: Int = 8231

public val ERROR_DS_AUTH_UNKNOWN: Int = 8234

public val ERROR_DS_AUX_CLS_TEST_FAIL: Int = 8389

public val ERROR_DS_BACKLINK_WITHOUT_LINK: Int = 8482

public val ERROR_DS_BAD_ATT_SCHEMA_SYNTAX: Int = 8400

public val ERROR_DS_BAD_HIERARCHY_FILE: Int = 8425

public val ERROR_DS_BAD_INSTANCE_TYPE: Int = 8313

public val ERROR_DS_BAD_NAME_SYNTAX: Int = 8335

public val ERROR_DS_BAD_RDN_ATT_ID_SYNTAX: Int = 8392

public val ERROR_DS_BUILD_HIERARCHY_TABLE_FAILED: Int = 8426

public val ERROR_DS_BUSY: Int = 8206

public val ERROR_DS_CANT_ACCESS_REMOTE_PART_OF_AD: Int = 8585

public val ERROR_DS_CANT_ADD_ATT_VALUES: Int = 8320

public val ERROR_DS_CANT_ADD_SYSTEM_ONLY: Int = 8358

public val ERROR_DS_CANT_ADD_TO_GC: Int = 8550

public val ERROR_DS_CANT_CACHE_ATT: Int = 8401

public val ERROR_DS_CANT_CACHE_CLASS: Int = 8402

public val ERROR_DS_CANT_CREATE_IN_NONDOMAIN_NC: Int = 8553

public val ERROR_DS_CANT_CREATE_UNDER_SCHEMA: Int = 8510

public val ERROR_DS_CANT_DELETE: Int = 8398

public val ERROR_DS_CANT_DELETE_DSA_OBJ: Int = 8340

public val ERROR_DS_CANT_DEL_MASTER_CROSSREF: Int = 8375

public val ERROR_DS_CANT_DEMOTE_WITH_WRITEABLE_NC: Int = 8604

public val ERROR_DS_CANT_DEREF_ALIAS: Int = 8337

public val ERROR_DS_CANT_DERIVE_SPN_FOR_DELETED_DOMAIN: Int = 8603

public val ERROR_DS_CANT_DERIVE_SPN_WITHOUT_SERVER_REF: Int = 8589

public val ERROR_DS_CANT_FIND_DC_FOR_SRC_DOMAIN: Int = 8537

public val ERROR_DS_CANT_FIND_DSA_OBJ: Int = 8419

public val ERROR_DS_CANT_FIND_EXPECTED_NC: Int = 8420

public val ERROR_DS_CANT_FIND_NC_IN_CACHE: Int = 8421

public val ERROR_DS_CANT_MIX_MASTER_AND_REPS: Int = 8331

public val ERROR_DS_CANT_MOD_OBJ_CLASS: Int = 8215

public val ERROR_DS_CANT_MOD_PRIMARYGROUPID: Int = 8506

public val ERROR_DS_CANT_MOD_SYSTEM_ONLY: Int = 8369

public val ERROR_DS_CANT_MOVE_ACCOUNT_GROUP: Int = 8498

public val ERROR_DS_CANT_MOVE_APP_BASIC_GROUP: Int = 8608

public val ERROR_DS_CANT_MOVE_APP_QUERY_GROUP: Int = 8609

public val ERROR_DS_CANT_MOVE_DELETED_OBJECT: Int = 8489

public val ERROR_DS_CANT_MOVE_RESOURCE_GROUP: Int = 8499

public val ERROR_DS_CANT_ON_NON_LEAF: Int = 8213

public val ERROR_DS_CANT_ON_RDN: Int = 8214

public val ERROR_DS_CANT_REMOVE_ATT_CACHE: Int = 8403

public val ERROR_DS_CANT_REMOVE_CLASS_CACHE: Int = 8404

public val ERROR_DS_CANT_REM_MISSING_ATT: Int = 8324

public val ERROR_DS_CANT_REM_MISSING_ATT_VAL: Int = 8325

public val ERROR_DS_CANT_REPLACE_HIDDEN_REC: Int = 8424

public val ERROR_DS_CANT_RETRIEVE_ATTS: Int = 8481

public val ERROR_DS_CANT_RETRIEVE_CHILD: Int = 8422

public val ERROR_DS_CANT_RETRIEVE_DN: Int = 8405

public val ERROR_DS_CANT_RETRIEVE_INSTANCE: Int = 8407

public val ERROR_DS_CANT_RETRIEVE_SD: Int = 8526

public val ERROR_DS_CANT_START: Int = 8531

public val ERROR_DS_CANT_TREE_DELETE_CRITICAL_OBJ: Int = 8560

public val ERROR_DS_CANT_WITH_ACCT_GROUP_MEMBERSHPS: Int = 8493

public val ERROR_DS_CHILDREN_EXIST: Int = 8332

public val ERROR_DS_CLASS_MUST_BE_CONCRETE: Int = 8359

public val ERROR_DS_CLASS_NOT_DSA: Int = 8343

public val ERROR_DS_CLIENT_LOOP: Int = 8259

public val ERROR_DS_CODE_INCONSISTENCY: Int = 8408

public val ERROR_DS_COMPARE_FALSE: Int = 8229

public val ERROR_DS_COMPARE_TRUE: Int = 8230

public val ERROR_DS_CONFIDENTIALITY_REQUIRED: Int = 8237

public val ERROR_DS_CONFIG_PARAM_MISSING: Int = 8427

public val ERROR_DS_CONSTRAINT_VIOLATION: Int = 8239

public val ERROR_DS_CONSTRUCTED_ATT_MOD: Int = 8475

public val ERROR_DS_CONTROL_NOT_FOUND: Int = 8258

public val ERROR_DS_COULDNT_CONTACT_FSMO: Int = 8367

public val ERROR_DS_COULDNT_IDENTIFY_OBJECTS_FOR_TREE_DELETE: Int = 8503

public val ERROR_DS_COULDNT_LOCK_TREE_FOR_DELETE: Int = 8502

public val ERROR_DS_COULDNT_UPDATE_SPNS: Int = 8525

public val ERROR_DS_COUNTING_AB_INDICES_FAILED: Int = 8428

public val ERROR_DS_CROSS_DOMAIN_CLEANUP_REQD: Int = 8491

public val ERROR_DS_CROSS_DOM_MOVE_ERROR: Int = 8216

public val ERROR_DS_CROSS_NC_DN_RENAME: Int = 8368

public val ERROR_DS_CROSS_REF_BUSY: Int = 8602

public val ERROR_DS_CROSS_REF_EXISTS: Int = 8374

public val ERROR_DS_CR_IMPOSSIBLE_TO_VALIDATE: Int = 8495

public val ERROR_DS_CR_IMPOSSIBLE_TO_VALIDATE_V2: Int = 8586

public val ERROR_DS_DATABASE_ERROR: Int = 8409

public val ERROR_DS_DECODING_ERROR: Int = 8253

public val ERROR_DS_DESTINATION_AUDITING_NOT_ENABLED: Int = 8536

public val ERROR_DS_DESTINATION_DOMAIN_NOT_IN_FOREST: Int = 8535

public val ERROR_DS_DIFFERENT_REPL_EPOCHS: Int = 8593

public val ERROR_DS_DISALLOWED_IN_SYSTEM_CONTAINER: Int = 8615

public val ERROR_DS_DISALLOWED_NC_REDIRECT: Int = 8640

public val ERROR_DS_DNS_LOOKUP_FAILURE: Int = 8524

public val ERROR_DS_DOMAIN_NAME_EXISTS_IN_FOREST: Int = 8634

public val ERROR_DS_DOMAIN_RENAME_IN_PROGRESS: Int = 8612

public val ERROR_DS_DOMAIN_VERSION_TOO_HIGH: Int = 8564

public val ERROR_DS_DOMAIN_VERSION_TOO_LOW: Int = 8566

public val ERROR_DS_DRA_ABANDON_SYNC: Int = 8462

public val ERROR_DS_DRA_ACCESS_DENIED: Int = 8453

public val ERROR_DS_DRA_BAD_DN: Int = 8439

public val ERROR_DS_DRA_BAD_INSTANCE_TYPE: Int = 8445

public val ERROR_DS_DRA_BAD_NC: Int = 8440

public val ERROR_DS_DRA_BUSY: Int = 8438

public val ERROR_DS_DRA_CONNECTION_FAILED: Int = 8444

public val ERROR_DS_DRA_CORRUPT_UTD_VECTOR: Int = 8629

public val ERROR_DS_DRA_DB_ERROR: Int = 8451

public val ERROR_DS_DRA_DN_EXISTS: Int = 8441

public val ERROR_DS_DRA_EARLIER_SCHEMA_CONFLICT: Int = 8544

public val ERROR_DS_DRA_EXTN_CONNECTION_FAILED: Int = 8466

public val ERROR_DS_DRA_GENERIC: Int = 8436

public val ERROR_DS_DRA_INCOMPATIBLE_PARTIAL_SET: Int = 8464

public val ERROR_DS_DRA_INCONSISTENT_DIT: Int = 8443

public val ERROR_DS_DRA_INTERNAL_ERROR: Int = 8442

public val ERROR_DS_DRA_INVALID_PARAMETER: Int = 8437

public val ERROR_DS_DRA_MAIL_PROBLEM: Int = 8447

public val ERROR_DS_DRA_MISSING_KRBTGT_SECRET: Int = 8633

public val ERROR_DS_DRA_MISSING_PARENT: Int = 8460

public val ERROR_DS_DRA_NAME_COLLISION: Int = 8458

public val ERROR_DS_DRA_NOT_SUPPORTED: Int = 8454

public val ERROR_DS_DRA_NO_REPLICA: Int = 8452

public val ERROR_DS_DRA_OBJ_IS_REP_SOURCE: Int = 8450

public val ERROR_DS_DRA_OBJ_NC_MISMATCH: Int = 8545

public val ERROR_DS_DRA_OUT_OF_MEM: Int = 8446

public val ERROR_DS_DRA_OUT_SCHEDULE_WINDOW: Int = 8617

public val ERROR_DS_DRA_PREEMPTED: Int = 8461

public val ERROR_DS_DRA_RECYCLED_TARGET: Int = 8639

public val ERROR_DS_DRA_REF_ALREADY_EXISTS: Int = 8448

public val ERROR_DS_DRA_REF_NOT_FOUND: Int = 8449

public val ERROR_DS_DRA_REPL_PENDING: Int = 8477

public val ERROR_DS_DRA_RPC_CANCELLED: Int = 8455

public val ERROR_DS_DRA_SCHEMA_CONFLICT: Int = 8543

public val ERROR_DS_DRA_SCHEMA_INFO_SHIP: Int = 8542

public val ERROR_DS_DRA_SCHEMA_MISMATCH: Int = 8418

public val ERROR_DS_DRA_SECRETS_DENIED: Int = 8630

public val ERROR_DS_DRA_SHUTDOWN: Int = 8463

public val ERROR_DS_DRA_SINK_DISABLED: Int = 8457

public val ERROR_DS_DRA_SOURCE_DISABLED: Int = 8456

public val ERROR_DS_DRA_SOURCE_IS_PARTIAL_REPLICA: Int = 8465

public val ERROR_DS_DRA_SOURCE_REINSTALLED: Int = 8459

public val ERROR_DS_DRS_EXTENSIONS_CHANGED: Int = 8594

public val ERROR_DS_DSA_MUST_BE_INT_MASTER: Int = 8342

public val ERROR_DS_DST_DOMAIN_NOT_NATIVE: Int = 8496

public val ERROR_DS_DST_NC_MISMATCH: Int = 8486

public val ERROR_DS_DS_REQUIRED: Int = 8478

public val ERROR_DS_DUPLICATE_ID_FOUND: Int = 8605

public val ERROR_DS_DUP_LDAP_DISPLAY_NAME: Int = 8382

public val ERROR_DS_DUP_LINK_ID: Int = 8468

public val ERROR_DS_DUP_MAPI_ID: Int = 8380

public val ERROR_DS_DUP_MSDS_INTID: Int = 8597

public val ERROR_DS_DUP_OID: Int = 8379

public val ERROR_DS_DUP_RDN: Int = 8378

public val ERROR_DS_DUP_SCHEMA_ID_GUID: Int = 8381

public val ERROR_DS_ENCODING_ERROR: Int = 8252

public val ERROR_DS_EPOCH_MISMATCH: Int = 8483

public val ERROR_DS_EXISTING_AD_CHILD_NC: Int = 8613

public val ERROR_DS_EXISTS_IN_AUX_CLS: Int = 8393

public val ERROR_DS_EXISTS_IN_MAY_HAVE: Int = 8386

public val ERROR_DS_EXISTS_IN_MUST_HAVE: Int = 8385

public val ERROR_DS_EXISTS_IN_POSS_SUP: Int = 8395

public val ERROR_DS_EXISTS_IN_RDNATTID: Int = 8598

public val ERROR_DS_EXISTS_IN_SUB_CLS: Int = 8394

public val ERROR_DS_FILTER_UNKNOWN: Int = 8254

public val ERROR_DS_FILTER_USES_CONTRUCTED_ATTRS: Int = 8555

public val ERROR_DS_FLAT_NAME_EXISTS_IN_FOREST: Int = 8635

public val ERROR_DS_FOREST_VERSION_TOO_HIGH: Int = 8563

public val ERROR_DS_FOREST_VERSION_TOO_LOW: Int = 8565

public val ERROR_DS_GCVERIFY_ERROR: Int = 8417

public val ERROR_DS_GC_NOT_AVAILABLE: Int = 8217

public val ERROR_DS_GC_REQUIRED: Int = 8547

public val ERROR_DS_GENERIC_ERROR: Int = 8341

public val ERROR_DS_GLOBAL_CANT_HAVE_CROSSDOMAIN_MEMBER: Int = 8519

public val ERROR_DS_GLOBAL_CANT_HAVE_LOCAL_MEMBER: Int = 8516

public val ERROR_DS_GLOBAL_CANT_HAVE_UNIVERSAL_MEMBER: Int = 8517

public val ERROR_DS_GOVERNSID_MISSING: Int = 8410

public val ERROR_DS_GROUP_CONVERSION_ERROR: Int = 8607

public val ERROR_DS_HAVE_PRIMARY_MEMBERS: Int = 8521

public val ERROR_DS_HIERARCHY_TABLE_MALLOC_FAILED: Int = 8429

public val ERROR_DS_HIERARCHY_TABLE_TOO_DEEP: Int = 8628

public val ERROR_DS_HIGH_ADLDS_FFL: Int = 8641

public val ERROR_DS_HIGH_DSA_VERSION: Int = 8642

public val ERROR_DS_ILLEGAL_BASE_SCHEMA_MOD: Int = 8507

public val ERROR_DS_ILLEGAL_MOD_OPERATION: Int = 8311

public val ERROR_DS_ILLEGAL_SUPERIOR: Int = 8345

public val ERROR_DS_ILLEGAL_XDOM_MOVE_OPERATION: Int = 8492

public val ERROR_DS_INAPPROPRIATE_AUTH: Int = 8233

public val ERROR_DS_INAPPROPRIATE_MATCHING: Int = 8238

public val ERROR_DS_INCOMPATIBLE_CONTROLS_USED: Int = 8574

public val ERROR_DS_INCOMPATIBLE_VERSION: Int = 8567

public val ERROR_DS_INCORRECT_ROLE_OWNER: Int = 8210

public val ERROR_DS_INIT_FAILURE: Int = 8532

public val ERROR_DS_INIT_FAILURE_CONSOLE: Int = 8561

public val ERROR_DS_INSTALL_NO_SCH_VERSION_IN_INIFILE: Int = 8512

public val ERROR_DS_INSTALL_NO_SRC_SCH_VERSION: Int = 8511

public val ERROR_DS_INSTALL_SCHEMA_MISMATCH: Int = 8467

public val ERROR_DS_INSUFFICIENT_ATTR_TO_CREATE_OBJECT: Int = 8606

public val ERROR_DS_INSUFF_ACCESS_RIGHTS: Int = 8344

public val ERROR_DS_INTERNAL_FAILURE: Int = 8430

public val ERROR_DS_INVALID_ATTRIBUTE_SYNTAX: Int = 8203

public val ERROR_DS_INVALID_DMD: Int = 8360

public val ERROR_DS_INVALID_DN_SYNTAX: Int = 8242

public val ERROR_DS_INVALID_GROUP_TYPE: Int = 8513

public val ERROR_DS_INVALID_LDAP_DISPLAY_NAME: Int = 8479

public val ERROR_DS_INVALID_NAME_FOR_SPN: Int = 8554

public val ERROR_DS_INVALID_ROLE_OWNER: Int = 8366

public val ERROR_DS_INVALID_SCRIPT: Int = 8600

public val ERROR_DS_INVALID_SEARCH_FLAG: Int = 8500

public val ERROR_DS_INVALID_SEARCH_FLAG_SUBTREE: Int = 8626

public val ERROR_DS_INVALID_SEARCH_FLAG_TUPLE: Int = 8627

public val ERROR_DS_IS_LEAF: Int = 8243

public val ERROR_DS_KEY_NOT_UNIQUE: Int = 8527

public val ERROR_DS_LDAP_SEND_QUEUE_FULL: Int = 8616

public val ERROR_DS_LINK_ID_NOT_AVAILABLE: Int = 8577

public val ERROR_DS_LOCAL_CANT_HAVE_CROSSDOMAIN_LOCAL_MEMBER: Int = 8520

public val ERROR_DS_LOCAL_ERROR: Int = 8251

public val ERROR_DS_LOCAL_MEMBER_OF_LOCAL_ONLY: Int = 8548

public val ERROR_DS_LOOP_DETECT: Int = 8246

public val ERROR_DS_LOW_ADLDS_FFL: Int = 8643

public val ERROR_DS_LOW_DSA_VERSION: Int = 8568

public val ERROR_DS_MACHINE_ACCOUNT_CREATED_PRENT4: Int = 8572

public val ERROR_DS_MACHINE_ACCOUNT_QUOTA_EXCEEDED: Int = 8557

public val ERROR_DS_MAPI_ID_NOT_AVAILABLE: Int = 8632

public val ERROR_DS_MASTERDSA_REQUIRED: Int = 8314

public val ERROR_DS_MAX_OBJ_SIZE_EXCEEDED: Int = 8304

public val ERROR_DS_MEMBERSHIP_EVALUATED_LOCALLY: Int = 8201

public val ERROR_DS_MISSING_EXPECTED_ATT: Int = 8411

public val ERROR_DS_MISSING_FOREST_TRUST: Int = 8649

public val ERROR_DS_MISSING_FSMO_SETTINGS: Int = 8434

public val ERROR_DS_MISSING_INFRASTRUCTURE_CONTAINER: Int = 8497

public val ERROR_DS_MISSING_REQUIRED_ATT: Int = 8316

public val ERROR_DS_MISSING_SUPREF: Int = 8406

public val ERROR_DS_MODIFYDN_DISALLOWED_BY_FLAG: Int = 8581

public val ERROR_DS_MODIFYDN_DISALLOWED_BY_INSTANCE_TYPE: Int = 8579

public val ERROR_DS_MODIFYDN_WRONG_GRANDPARENT: Int = 8582

public val ERROR_DS_MUST_BE_RUN_ON_DST_DC: Int = 8558

public val ERROR_DS_NAME_ERROR_DOMAIN_ONLY: Int = 8473

public val ERROR_DS_NAME_ERROR_NOT_FOUND: Int = 8470

public val ERROR_DS_NAME_ERROR_NOT_UNIQUE: Int = 8471

public val ERROR_DS_NAME_ERROR_NO_MAPPING: Int = 8472

public val ERROR_DS_NAME_ERROR_NO_SYNTACTICAL_MAPPING: Int = 8474

public val ERROR_DS_NAME_ERROR_RESOLVING: Int = 8469

public val ERROR_DS_NAME_ERROR_TRUST_REFERRAL: Int = 8583

public val ERROR_DS_NAME_NOT_UNIQUE: Int = 8571

public val ERROR_DS_NAME_REFERENCE_INVALID: Int = 8373

public val ERROR_DS_NAME_TOO_LONG: Int = 8348

public val ERROR_DS_NAME_TOO_MANY_PARTS: Int = 8347

public val ERROR_DS_NAME_TYPE_UNKNOWN: Int = 8351

public val ERROR_DS_NAME_UNPARSEABLE: Int = 8350

public val ERROR_DS_NAME_VALUE_TOO_LONG: Int = 8349

public val ERROR_DS_NAMING_MASTER_GC: Int = 8523

public val ERROR_DS_NAMING_VIOLATION: Int = 8247

public val ERROR_DS_NCNAME_MISSING_CR_REF: Int = 8412

public val ERROR_DS_NCNAME_MUST_BE_NC: Int = 8357

public val ERROR_DS_NC_MUST_HAVE_NC_PARENT: Int = 8494

public val ERROR_DS_NC_STILL_HAS_DSAS: Int = 8546

public val ERROR_DS_NONEXISTENT_MAY_HAVE: Int = 8387

public val ERROR_DS_NONEXISTENT_MUST_HAVE: Int = 8388

public val ERROR_DS_NONEXISTENT_POSS_SUP: Int = 8390

public val ERROR_DS_NONSAFE_SCHEMA_CHANGE: Int = 8508

public val ERROR_DS_NON_ASQ_SEARCH: Int = 8624

public val ERROR_DS_NON_BASE_SEARCH: Int = 8480

public val ERROR_DS_NOTIFY_FILTER_TOO_COMPLEX: Int = 8377

public val ERROR_DS_NOT_AN_OBJECT: Int = 8352

public val ERROR_DS_NOT_AUTHORITIVE_FOR_DST_NC: Int = 8487

public val ERROR_DS_NOT_CLOSEST: Int = 8588

public val ERROR_DS_NOT_INSTALLED: Int = 8200

public val ERROR_DS_NOT_ON_BACKLINK: Int = 8362

public val ERROR_DS_NOT_SUPPORTED: Int = 8256

public val ERROR_DS_NOT_SUPPORTED_SORT_ORDER: Int = 8570

public val ERROR_DS_NO_ATTRIBUTE_OR_VALUE: Int = 8202

public val ERROR_DS_NO_BEHAVIOR_VERSION_IN_MIXEDDOMAIN: Int = 8569

public val ERROR_DS_NO_CHAINED_EVAL: Int = 8328

public val ERROR_DS_NO_CHAINING: Int = 8327

public val ERROR_DS_NO_CHECKPOINT_WITH_PDC: Int = 8551

public val ERROR_DS_NO_CROSSREF_FOR_NC: Int = 8363

public val ERROR_DS_NO_DELETED_NAME: Int = 8355

public val ERROR_DS_NO_FPO_IN_UNIVERSAL_GROUPS: Int = 8549

public val ERROR_DS_NO_MORE_RIDS: Int = 8209

public val ERROR_DS_NO_MSDS_INTID: Int = 8596

public val ERROR_DS_NO_NEST_GLOBALGROUP_IN_MIXEDDOMAIN: Int = 8514

public val ERROR_DS_NO_NEST_LOCALGROUP_IN_MIXEDDOMAIN: Int = 8515

public val ERROR_DS_NO_NTDSA_OBJECT: Int = 8623

public val ERROR_DS_NO_OBJECT_MOVE_IN_SCHEMA_NC: Int = 8580

public val ERROR_DS_NO_PARENT_OBJECT: Int = 8329

public val ERROR_DS_NO_PKT_PRIVACY_ON_CONNECTION: Int = 8533

public val ERROR_DS_NO_RDN_DEFINED_IN_SCHEMA: Int = 8306

public val ERROR_DS_NO_REF_DOMAIN: Int = 8575

public val ERROR_DS_NO_REQUESTED_ATTS_FOUND: Int = 8308

public val ERROR_DS_NO_RESULTS_RETURNED: Int = 8257

public val ERROR_DS_NO_RIDS_ALLOCATED: Int = 8208

public val ERROR_DS_NO_SERVER_OBJECT: Int = 8622

public val ERROR_DS_NO_SUCH_OBJECT: Int = 8240

public val ERROR_DS_NO_TREE_DELETE_ABOVE_NC: Int = 8501

public val ERROR_DS_NTDSCRIPT_PROCESS_ERROR: Int = 8592

public val ERROR_DS_NTDSCRIPT_SYNTAX_ERROR: Int = 8591

public val ERROR_DS_OBJECT_BEING_REMOVED: Int = 8339

public val ERROR_DS_OBJECT_CLASS_REQUIRED: Int = 8315

public val ERROR_DS_OBJECT_RESULTS_TOO_LARGE: Int = 8248

public val ERROR_DS_OBJ_CLASS_NOT_DEFINED: Int = 8371

public val ERROR_DS_OBJ_CLASS_NOT_SUBCLASS: Int = 8372

public val ERROR_DS_OBJ_CLASS_VIOLATION: Int = 8212

public val ERROR_DS_OBJ_GUID_EXISTS: Int = 8361

public val ERROR_DS_OBJ_NOT_FOUND: Int = 8333

public val ERROR_DS_OBJ_STRING_NAME_EXISTS: Int = 8305

public val ERROR_DS_OBJ_TOO_LARGE: Int = 8312

public val ERROR_DS_OFFSET_RANGE_ERROR: Int = 8262

public val ERROR_DS_OID_MAPPED_GROUP_CANT_HAVE_MEMBERS: Int = 8637

public val ERROR_DS_OID_NOT_FOUND: Int = 8638

public val ERROR_DS_OPERATIONS_ERROR: Int = 8224

public val ERROR_DS_OUT_OF_SCOPE: Int = 8338

public val ERROR_DS_OUT_OF_VERSION_STORE: Int = 8573

public val ERROR_DS_PARAM_ERROR: Int = 8255

public val ERROR_DS_PARENT_IS_AN_ALIAS: Int = 8330

public val ERROR_DS_PDC_OPERATION_IN_PROGRESS: Int = 8490

public val ERROR_DS_PER_ATTRIBUTE_AUTHZ_FAILED_DURING_ADD: Int = 8652

public val ERROR_DS_POLICY_NOT_KNOWN: Int = 8618

public val ERROR_DS_PROTOCOL_ERROR: Int = 8225

public val ERROR_DS_RANGE_CONSTRAINT: Int = 8322

public val ERROR_DS_RDN_DOESNT_MATCH_SCHEMA: Int = 8307

public val ERROR_DS_RECALCSCHEMA_FAILED: Int = 8396

public val ERROR_DS_REFERRAL: Int = 8235

public val ERROR_DS_REFERRAL_LIMIT_EXCEEDED: Int = 8260

public val ERROR_DS_REFUSING_FSMO_ROLES: Int = 8433

public val ERROR_DS_REMOTE_CROSSREF_OP_FAILED: Int = 8601

public val ERROR_DS_REPLICATOR_ONLY: Int = 8370

public val ERROR_DS_REPLICA_SET_CHANGE_NOT_ALLOWED_ON_DISABLED_CR: Int = 8595

public val ERROR_DS_REPL_LIFETIME_EXCEEDED: Int = 8614

public val ERROR_DS_RESERVED_LINK_ID: Int = 8576

public val ERROR_DS_RESERVED_MAPI_ID: Int = 8631

public val ERROR_DS_RIDMGR_DISABLED: Int = 8263

public val ERROR_DS_RIDMGR_INIT_ERROR: Int = 8211

public val ERROR_DS_ROLE_NOT_VERIFIED: Int = 8610

public val ERROR_DS_ROOT_CANT_BE_SUBREF: Int = 8326

public val ERROR_DS_ROOT_MUST_BE_NC: Int = 8301

public val ERROR_DS_ROOT_REQUIRES_CLASS_TOP: Int = 8432

public val ERROR_DS_SAM_INIT_FAILURE: Int = 8504

public val ERROR_DS_SAM_INIT_FAILURE_CONSOLE: Int = 8562

public val ERROR_DS_SAM_NEED_BOOTKEY_FLOPPY: Int = 8530

public val ERROR_DS_SAM_NEED_BOOTKEY_PASSWORD: Int = 8529

public val ERROR_DS_SCHEMA_ALLOC_FAILED: Int = 8415

public val ERROR_DS_SCHEMA_NOT_LOADED: Int = 8414

public val ERROR_DS_SCHEMA_UPDATE_DISALLOWED: Int = 8509

public val ERROR_DS_SECURITY_CHECKING_ERROR: Int = 8413

public val ERROR_DS_SECURITY_ILLEGAL_MODIFY: Int = 8423

public val ERROR_DS_SEC_DESC_INVALID: Int = 8354

public val ERROR_DS_SEC_DESC_TOO_SHORT: Int = 8353

public val ERROR_DS_SEMANTIC_ATT_TEST: Int = 8383

public val ERROR_DS_SENSITIVE_GROUP_VIOLATION: Int = 8505

public val ERROR_DS_SERVER_DOWN: Int = 8250

public val ERROR_DS_SHUTTING_DOWN: Int = 8364

public val ERROR_DS_SINGLE_USER_MODE_FAILED: Int = 8590

public val ERROR_DS_SINGLE_VALUE_CONSTRAINT: Int = 8321

public val ERROR_DS_SIZELIMIT_EXCEEDED: Int = 8227

public val ERROR_DS_SORT_CONTROL_MISSING: Int = 8261

public val ERROR_DS_SOURCE_AUDITING_NOT_ENABLED: Int = 8552

public val ERROR_DS_SOURCE_DOMAIN_IN_FOREST: Int = 8534

public val ERROR_DS_SPN_VALUE_NOT_UNIQUE_IN_FOREST: Int = 8647

public val ERROR_DS_SRC_AND_DST_NC_IDENTICAL: Int = 8485

public val ERROR_DS_SRC_AND_DST_OBJECT_CLASS_MISMATCH: Int = 8540

public val ERROR_DS_SRC_DC_MUST_BE_SP4_OR_GREATER: Int = 8559

public val ERROR_DS_SRC_GUID_MISMATCH: Int = 8488

public val ERROR_DS_SRC_NAME_MISMATCH: Int = 8484

public val ERROR_DS_SRC_OBJ_NOT_GROUP_OR_USER: Int = 8538

public val ERROR_DS_SRC_SID_EXISTS_IN_FOREST: Int = 8539

public val ERROR_DS_STRING_SD_CONVERSION_FAILED: Int = 8522

public val ERROR_DS_STRONG_AUTH_REQUIRED: Int = 8232

public val ERROR_DS_SUBREF_MUST_HAVE_PARENT: Int = 8356

public val ERROR_DS_SUBTREE_NOTIFY_NOT_NC_HEAD: Int = 8376

public val ERROR_DS_SUB_CLS_TEST_FAIL: Int = 8391

public val ERROR_DS_SYNTAX_MISMATCH: Int = 8384

public val ERROR_DS_THREAD_LIMIT_EXCEEDED: Int = 8587

public val ERROR_DS_TIMELIMIT_EXCEEDED: Int = 8226

public val ERROR_DS_TREE_DELETE_NOT_FINISHED: Int = 8397

public val ERROR_DS_UNABLE_TO_SURRENDER_ROLES: Int = 8435

public val ERROR_DS_UNAVAILABLE: Int = 8207

public val ERROR_DS_UNAVAILABLE_CRIT_EXTENSION: Int = 8236

public val ERROR_DS_UNDELETE_SAM_VALIDATION_FAILED: Int = 8645

public val ERROR_DS_UNICODEPWD_NOT_IN_QUOTES: Int = 8556

public val ERROR_DS_UNIVERSAL_CANT_HAVE_LOCAL_MEMBER: Int = 8518

public val ERROR_DS_UNKNOWN_ERROR: Int = 8431

public val ERROR_DS_UNKNOWN_OPERATION: Int = 8365

public val ERROR_DS_UNWILLING_TO_PERFORM: Int = 8245

public val ERROR_DS_UPN_VALUE_NOT_UNIQUE_IN_FOREST: Int = 8648

public val ERROR_DS_USER_BUFFER_TO_SMALL: Int = 8309

public val ERROR_DS_VALUE_KEY_NOT_UNIQUE: Int = 8650

public val ERROR_DS_VERSION_CHECK_FAILURE: Int = 643

public val ERROR_DS_WKO_CONTAINER_CANNOT_BE_SPECIAL: Int = 8611

public val ERROR_DS_WRONG_LINKED_ATT_SYNTAX: Int = 8528

public val ERROR_DS_WRONG_OM_OBJ_CLASS: Int = 8476

public val ERROR_DUPLICATE_PRIVILEGES: Int = 311

public val ERROR_DUPLICATE_SERVICE_NAME: Int = 1078

public val ERROR_DUP_DOMAINNAME: Int = 1221

public val ERROR_DUP_NAME: Int = 52

public val ERROR_DYNAMIC_CODE_BLOCKED: Int = 1655

public val ERROR_DYNLINK_FROM_INVALID_RING: Int = 196

public val ERROR_EAS_DIDNT_FIT: Int = 275

public val ERROR_EAS_NOT_SUPPORTED: Int = 282

public val ERROR_EA_ACCESS_DENIED: Int = 994

public val ERROR_EA_FILE_CORRUPT: Int = 276

public val ERROR_EA_LIST_INCONSISTENT: Int = 255

public val ERROR_EA_TABLE_FULL: Int = 277

public val ERROR_EDP_DPL_POLICY_CANT_BE_SATISFIED: Int = 357

public val ERROR_EDP_POLICY_DENIES_OPERATION: Int = 356

public val ERROR_EFS_ALG_BLOB_TOO_BIG: Int = 6013

public val ERROR_EFS_DISABLED: Int = 6015

public val ERROR_EFS_SERVER_NOT_TRUSTED: Int = 6011

public val ERROR_EFS_VERSION_NOT_SUPPORT: Int = 6016

public val ERROR_ELEVATION_REQUIRED: Int = 740

public val ERROR_ENCLAVE_FAILURE: Int = 349

public val ERROR_ENCLAVE_NOT_TERMINATED: Int = 814

public val ERROR_ENCLAVE_VIOLATION: Int = 815

public val ERROR_ENCRYPTED_FILE_NOT_SUPPORTED: Int = 489

public val ERROR_ENCRYPTED_IO_NOT_POSSIBLE: Int = 808

public val ERROR_ENCRYPTING_METADATA_DISALLOWED: Int = 431

public val ERROR_ENCRYPTION_DISABLED: Int = 430

public val ERROR_ENCRYPTION_FAILED: Int = 6000

public val ERROR_ENCRYPTION_POLICY_DENIES_OPERATION: Int = 6022

public val ERROR_END_OF_MEDIA: Int = 1100

public val ERROR_ENVVAR_NOT_FOUND: Int = 203

public val ERROR_EOM_OVERFLOW: Int = 1129

public val ERROR_ERRORS_ENCOUNTERED: Int = 774

public val ERROR_EVALUATION_EXPIRATION: Int = 622

public val ERROR_EVENTLOG_CANT_START: Int = 1501

public val ERROR_EVENTLOG_FILE_CHANGED: Int = 1503

public val ERROR_EVENTLOG_FILE_CORRUPT: Int = 1500

public val ERROR_EVENT_DONE: Int = 710

public val ERROR_EVENT_PENDING: Int = 711

public val ERROR_EXCEPTION_IN_SERVICE: Int = 1064

public val ERROR_EXCL_SEM_ALREADY_OWNED: Int = 101

public val ERROR_EXE_CANNOT_MODIFY_SIGNED_BINARY: Int = 217

public val ERROR_EXE_CANNOT_MODIFY_STRONG_SIGNED_BINARY: Int = 218

public val ERROR_EXE_MACHINE_TYPE_MISMATCH: Int = 216

public val ERROR_EXE_MARKED_INVALID: Int = 192

public val ERROR_EXTENDED_ERROR: Int = 1208

public val ERROR_EXTERNAL_BACKING_PROVIDER_UNKNOWN: Int = 343

public val ERROR_EXTERNAL_SYSKEY_NOT_SUPPORTED: Int = 399

public val ERROR_EXTRANEOUS_INFORMATION: Int = 677

public val ERROR_FAILED_DRIVER_ENTRY: Int = 647

public val ERROR_FAILED_SERVICE_CONTROLLER_CONNECT: Int = 1063

public val ERROR_FAIL_FAST_EXCEPTION: Int = 1653

public val ERROR_FAIL_I24: Int = 83

public val ERROR_FAIL_NOACTION_REBOOT: Int = 350

public val ERROR_FAIL_RESTART: Int = 352

public val ERROR_FAIL_SHUTDOWN: Int = 351

public val ERROR_FATAL_APP_EXIT: Int = 713

public val ERROR_FILEMARK_DETECTED: Int = 1101

public val ERROR_FILENAME_EXCED_RANGE: Int = 206

public val ERROR_FILE_CHECKED_OUT: Int = 220

public val ERROR_FILE_CORRUPT: Int = 1392

public val ERROR_FILE_ENCRYPTED: Int = 6002

public val ERROR_FILE_EXISTS: Int = 80

public val ERROR_FILE_HANDLE_REVOKED: Int = 806

public val ERROR_FILE_INVALID: Int = 1006

public val ERROR_FILE_LEVEL_TRIM_NOT_SUPPORTED: Int = 326

public val ERROR_FILE_METADATA_OPTIMIZATION_IN_PROGRESS: Int = 809

public val ERROR_FILE_NOT_ENCRYPTED: Int = 6007

public val ERROR_FILE_NOT_FOUND: Int = 2

public val ERROR_FILE_NOT_SUPPORTED: Int = 425

public val ERROR_FILE_OFFLINE: Int = 4350

public val ERROR_FILE_PROTECTED_UNDER_DPL: Int = 406

public val ERROR_FILE_READ_ONLY: Int = 6009

public val ERROR_FILE_SNAP_INVALID_PARAMETER: Int = 440

public val ERROR_FILE_SNAP_IN_PROGRESS: Int = 435

public val ERROR_FILE_SNAP_IO_NOT_COORDINATED: Int = 438

public val ERROR_FILE_SNAP_MODIFY_NOT_SUPPORTED: Int = 437

public val ERROR_FILE_SNAP_UNEXPECTED_ERROR: Int = 439

public val ERROR_FILE_SNAP_USER_SECTION_NOT_SUPPORTED: Int = 436

public val ERROR_FILE_SYSTEM_LIMITATION: Int = 665

public val ERROR_FILE_SYSTEM_VIRTUALIZATION_BUSY: Int = 371

public val ERROR_FILE_SYSTEM_VIRTUALIZATION_INVALID_OPERATION: Int = 385

public val ERROR_FILE_SYSTEM_VIRTUALIZATION_METADATA_CORRUPT: Int = 370

public val ERROR_FILE_SYSTEM_VIRTUALIZATION_PROVIDER_UNKNOWN: Int = 372

public val ERROR_FILE_SYSTEM_VIRTUALIZATION_UNAVAILABLE: Int = 369

public val ERROR_FILE_TOO_LARGE: Int = 223

public val ERROR_FIRMWARE_UPDATED: Int = 728

public val ERROR_FLOAT_MULTIPLE_FAULTS: Int = 630

public val ERROR_FLOAT_MULTIPLE_TRAPS: Int = 631

public val ERROR_FLOPPY_BAD_REGISTERS: Int = 1125

public val ERROR_FLOPPY_ID_MARK_NOT_FOUND: Int = 1122

public val ERROR_FLOPPY_UNKNOWN_ERROR: Int = 1124

public val ERROR_FLOPPY_VOLUME: Int = 584

public val ERROR_FLOPPY_WRONG_CYLINDER: Int = 1123

public val ERROR_FORMS_AUTH_REQUIRED: Int = 224

public val ERROR_FOUND_OUT_OF_SCOPE: Int = 601

public val ERROR_FSFILTER_OP_COMPLETED_SUCCESSFULLY: Int = 762

public val ERROR_FS_DRIVER_REQUIRED: Int = 588

public val ERROR_FS_METADATA_INCONSISTENT: Int = 510

public val ERROR_FT_DI_SCAN_REQUIRED: Int = 339

public val ERROR_FT_READ_FAILURE: Int = 415

public val ERROR_FT_READ_FROM_COPY_FAILURE: Int = 818

public val ERROR_FT_READ_RECOVERY_FROM_BACKUP: Int = 704

public val ERROR_FT_WRITE_FAILURE: Int = 338

public val ERROR_FT_WRITE_RECOVERY: Int = 705

public val ERROR_FULLSCREEN_MODE: Int = 1007

public val ERROR_FUNCTION_FAILED: Int = 1627

public val ERROR_FUNCTION_NOT_CALLED: Int = 1626

public val ERROR_GDI_HANDLE_LEAK: Int = 373

public val ERROR_GENERIC_NOT_MAPPED: Int = 1360

public val ERROR_GEN_FAILURE: Int = 31

public val ERROR_GLOBAL_ONLY_HOOK: Int = 1429

public val ERROR_GRACEFUL_DISCONNECT: Int = 1226

public val ERROR_GROUP_EXISTS: Int = 1318

public val ERROR_GUID_SUBSTITUTION_MADE: Int = 680

public val ERROR_HANDLES_CLOSED: Int = 676

public val ERROR_HANDLE_DISK_FULL: Int = 39

public val ERROR_HANDLE_EOF: Int = 38

public val ERROR_HANDLE_REVOKED: Int = 811

public val ERROR_HAS_SYSTEM_CRITICAL_FILES: Int = 488

public val ERROR_HIBERNATED: Int = 726

public val ERROR_HIBERNATION_FAILURE: Int = 656

public val ERROR_HOOK_NEEDS_HMOD: Int = 1428

public val ERROR_HOOK_NOT_INSTALLED: Int = 1431

public val ERROR_HOOK_TYPE_NOT_ALLOWED: Int = 1458

public val ERROR_HOST_DOWN: Int = 1256

public val ERROR_HOST_UNREACHABLE: Int = 1232

public val ERROR_HOTKEY_ALREADY_REGISTERED: Int = 1409

public val ERROR_HOTKEY_NOT_REGISTERED: Int = 1419

public val ERROR_HWNDS_HAVE_DIFF_PARENT: Int = 1441

public val ERROR_ILLEGAL_CHARACTER: Int = 582

public val ERROR_ILLEGAL_DLL_RELOCATION: Int = 623

public val ERROR_ILLEGAL_ELEMENT_ADDRESS: Int = 1162

public val ERROR_ILLEGAL_FLOAT_CONTEXT: Int = 579

public val ERROR_ILL_FORMED_PASSWORD: Int = 1324

public val ERROR_IMAGE_AT_DIFFERENT_BASE: Int = 807

public val ERROR_IMAGE_MACHINE_TYPE_MISMATCH: Int = 706

public val ERROR_IMAGE_MACHINE_TYPE_MISMATCH_EXE: Int = 720

public val ERROR_IMAGE_NOT_AT_BASE: Int = 700

public val ERROR_IMAGE_SUBSYSTEM_NOT_PRESENT: Int = 308

public val ERROR_IMPLEMENTATION_LIMIT: Int = 1292

public val ERROR_INCOMPATIBLE_SERVICE_PRIVILEGE: Int = 1297

public val ERROR_INCOMPATIBLE_SERVICE_SID_TYPE: Int = 1290

public val ERROR_INCOMPATIBLE_WITH_GLOBAL_SHORT_NAME_REGISTRY_SETTING: Int = 304

public val ERROR_INCORRECT_ACCOUNT_TYPE: Int = 8646

public val ERROR_INCORRECT_ADDRESS: Int = 1241

public val ERROR_INCORRECT_SIZE: Int = 1462

public val ERROR_INDEX_ABSENT: Int = 1611

public val ERROR_INDEX_OUT_OF_BOUNDS: Int = 474

public val ERROR_INFLOOP_IN_RELOC_CHAIN: Int = 202

public val ERROR_INSTALL_ALREADY_RUNNING: Int = 1618

public val ERROR_INSTALL_FAILURE: Int = 1603

public val ERROR_INSTALL_LANGUAGE_UNSUPPORTED: Int = 1623

public val ERROR_INSTALL_LOG_FAILURE: Int = 1622

public val ERROR_INSTALL_NOTUSED: Int = 1634

public val ERROR_INSTALL_PACKAGE_INVALID: Int = 1620

public val ERROR_INSTALL_PACKAGE_OPEN_FAILED: Int = 1619

public val ERROR_INSTALL_PACKAGE_REJECTED: Int = 1625

public val ERROR_INSTALL_PACKAGE_VERSION: Int = 1613

public val ERROR_INSTALL_PLATFORM_UNSUPPORTED: Int = 1633

public val ERROR_INSTALL_REJECTED: Int = 1654

public val ERROR_INSTALL_REMOTE_DISALLOWED: Int = 1640

public val ERROR_INSTALL_REMOTE_PROHIBITED: Int = 1645

public val ERROR_INSTALL_SERVICE_FAILURE: Int = 1601

public val ERROR_INSTALL_SERVICE_SAFEBOOT: Int = 1652

public val ERROR_INSTALL_SOURCE_ABSENT: Int = 1612

public val ERROR_INSTALL_SUSPEND: Int = 1604

public val ERROR_INSTALL_TEMP_UNWRITABLE: Int = 1632

public val ERROR_INSTALL_TRANSFORM_FAILURE: Int = 1624

public val ERROR_INSTALL_TRANSFORM_REJECTED: Int = 1644

public val ERROR_INSTALL_UI_FAILURE: Int = 1621

public val ERROR_INSTALL_USEREXIT: Int = 1602

public val ERROR_INSTRUCTION_MISALIGNMENT: Int = 549

public val ERROR_INSUFFICIENT_BUFFER: Int = 122

public val ERROR_INSUFFICIENT_LOGON_INFO: Int = 608

public val ERROR_INSUFFICIENT_POWER: Int = 639

public val ERROR_INSUFFICIENT_RESOURCE_FOR_SPECIFIED_SHARED_SECTION_SIZE: Int = 781

public val ERROR_INSUFFICIENT_VIRTUAL_ADDR_RESOURCES: Int = 473

public val ERROR_INTERMIXED_KERNEL_EA_OPERATION: Int = 324

public val ERROR_INTERNAL_DB_CORRUPTION: Int = 1358

public val ERROR_INTERNAL_DB_ERROR: Int = 1383

public val ERROR_INTERNAL_ERROR: Int = 1359

public val ERROR_INTERRUPT_STILL_CONNECTED: Int = 764

public val ERROR_INTERRUPT_VECTOR_ALREADY_CONNECTED: Int = 763

public val ERROR_INVALID_ACCEL_HANDLE: Int = 1403

public val ERROR_INVALID_ACCESS: Int = 12

public val ERROR_INVALID_ACCOUNT_NAME: Int = 1315

public val ERROR_INVALID_ACE_CONDITION: Int = 805

public val ERROR_INVALID_ACL: Int = 1336

public val ERROR_INVALID_ADDRESS: Int = 487

public val ERROR_INVALID_AT_INTERRUPT_TIME: Int = 104

public val ERROR_INVALID_BLOCK: Int = 9

public val ERROR_INVALID_BLOCK_LENGTH: Int = 1106

public val ERROR_INVALID_CAP: Int = 320

public val ERROR_INVALID_CATEGORY: Int = 117

public val ERROR_INVALID_COMBOBOX_MESSAGE: Int = 1422

public val ERROR_INVALID_COMMAND_LINE: Int = 1639

public val ERROR_INVALID_COMPUTERNAME: Int = 1210

public val ERROR_INVALID_CRUNTIME_PARAMETER: Int = 1288

public val ERROR_INVALID_CURSOR_HANDLE: Int = 1402

public val ERROR_INVALID_DATA: Int = 13

public val ERROR_INVALID_DATATYPE: Int = 1804

public val ERROR_INVALID_DEVICE_OBJECT_PARAMETER: Int = 650

public val ERROR_INVALID_DLL: Int = 1154

public val ERROR_INVALID_DOMAINNAME: Int = 1212

public val ERROR_INVALID_DOMAIN_ROLE: Int = 1354

public val ERROR_INVALID_DOMAIN_STATE: Int = 1353

public val ERROR_INVALID_DRIVE: Int = 15

public val ERROR_INVALID_DWP_HANDLE: Int = 1405

public val ERROR_INVALID_EA_HANDLE: Int = 278

public val ERROR_INVALID_EA_NAME: Int = 254

public val ERROR_INVALID_EDIT_HEIGHT: Int = 1424

public val ERROR_INVALID_ENVIRONMENT: Int = 1805

public val ERROR_INVALID_EVENTNAME: Int = 1211

public val ERROR_INVALID_EVENT_COUNT: Int = 151

public val ERROR_INVALID_EXCEPTION_HANDLER: Int = 310

public val ERROR_INVALID_EXE_SIGNATURE: Int = 191

public val ERROR_INVALID_FIELD: Int = 1616

public val ERROR_INVALID_FIELD_IN_PARAMETER_LIST: Int = 328

public val ERROR_INVALID_FILTER_PROC: Int = 1427

public val ERROR_INVALID_FLAGS: Int = 1004

public val ERROR_INVALID_FLAG_NUMBER: Int = 186

public val ERROR_INVALID_FORM_NAME: Int = 1902

public val ERROR_INVALID_FORM_SIZE: Int = 1903

public val ERROR_INVALID_FUNCTION: Int = 1

public val ERROR_INVALID_GROUPNAME: Int = 1209

public val ERROR_INVALID_GROUP_ATTRIBUTES: Int = 1345

public val ERROR_INVALID_GW_COMMAND: Int = 1443

public val ERROR_INVALID_HANDLE: Int = 6

public val ERROR_INVALID_HANDLE_STATE: Int = 1609

public val ERROR_INVALID_HOOK_FILTER: Int = 1426

public val ERROR_INVALID_HOOK_HANDLE: Int = 1404

public val ERROR_INVALID_HW_PROFILE: Int = 619

public val ERROR_INVALID_ICON_HANDLE: Int = 1414

public val ERROR_INVALID_ID_AUTHORITY: Int = 1343

public val ERROR_INVALID_IMAGE_HASH: Int = 577

public val ERROR_INVALID_IMPORT_OF_NON_DLL: Int = 1276

public val ERROR_INVALID_INDEX: Int = 1413

public val ERROR_INVALID_KERNEL_INFO_VERSION: Int = 340

public val ERROR_INVALID_KEYBOARD_HANDLE: Int = 1457

public val ERROR_INVALID_LABEL: Int = 1299

public val ERROR_INVALID_LB_MESSAGE: Int = 1432

public val ERROR_INVALID_LDT_DESCRIPTOR: Int = 564

public val ERROR_INVALID_LDT_OFFSET: Int = 563

public val ERROR_INVALID_LDT_SIZE: Int = 561

public val ERROR_INVALID_LEVEL: Int = 124

public val ERROR_INVALID_LIST_FORMAT: Int = 153

public val ERROR_INVALID_LOCK_RANGE: Int = 307

public val ERROR_INVALID_LOGON_HOURS: Int = 1328

public val ERROR_INVALID_LOGON_TYPE: Int = 1367

public val ERROR_INVALID_MEMBER: Int = 1388

public val ERROR_INVALID_MENU_HANDLE: Int = 1401

public val ERROR_INVALID_MESSAGE: Int = 1002

public val ERROR_INVALID_MESSAGEDEST: Int = 1218

public val ERROR_INVALID_MESSAGENAME: Int = 1217

public val ERROR_INVALID_MINALLOCSIZE: Int = 195

public val ERROR_INVALID_MODULETYPE: Int = 190

public val ERROR_INVALID_MONITOR_HANDLE: Int = 1461

public val ERROR_INVALID_MSGBOX_STYLE: Int = 1438

public val ERROR_INVALID_NAME: Int = 123

public val ERROR_INVALID_NETNAME: Int = 1214

public val ERROR_INVALID_OPLOCK_PROTOCOL: Int = 301

public val ERROR_INVALID_ORDINAL: Int = 182

public val ERROR_INVALID_OWNER: Int = 1307

public val ERROR_INVALID_PACKAGE_SID_LENGTH: Int = 4253

public val ERROR_INVALID_PARAMETER: Int = 87

public val ERROR_INVALID_PASSWORD: Int = 86

public val ERROR_INVALID_PASSWORDNAME: Int = 1216

public val ERROR_INVALID_PATCH_XML: Int = 1650

public val ERROR_INVALID_PEP_INFO_VERSION: Int = 341

public val ERROR_INVALID_PLUGPLAY_DEVICE_PATH: Int = 620

public val ERROR_INVALID_PORT_ATTRIBUTES: Int = 545

public val ERROR_INVALID_PRIMARY_GROUP: Int = 1308

public val ERROR_INVALID_PRINTER_COMMAND: Int = 1803

public val ERROR_INVALID_PRINTER_NAME: Int = 1801

public val ERROR_INVALID_PRINTER_STATE: Int = 1906

public val ERROR_INVALID_PRIORITY: Int = 1800

public val ERROR_INVALID_QUOTA_LOWER: Int = 547

public val ERROR_INVALID_REPARSE_DATA: Int = 4392

public val ERROR_INVALID_SCROLLBAR_RANGE: Int = 1448

public val ERROR_INVALID_SECURITY_DESCR: Int = 1338

public val ERROR_INVALID_SEGDPL: Int = 198

public val ERROR_INVALID_SEGMENT_NUMBER: Int = 180

public val ERROR_INVALID_SEPARATOR_FILE: Int = 1799

public val ERROR_INVALID_SERVER_STATE: Int = 1352

public val ERROR_INVALID_SERVICENAME: Int = 1213

public val ERROR_INVALID_SERVICE_ACCOUNT: Int = 1057

public val ERROR_INVALID_SERVICE_CONTROL: Int = 1052

public val ERROR_INVALID_SERVICE_LOCK: Int = 1071

public val ERROR_INVALID_SHARENAME: Int = 1215

public val ERROR_INVALID_SHOWWIN_COMMAND: Int = 1449

public val ERROR_INVALID_SID: Int = 1337

public val ERROR_INVALID_SIGNAL_NUMBER: Int = 209

public val ERROR_INVALID_SPI_VALUE: Int = 1439

public val ERROR_INVALID_STACKSEG: Int = 189

public val ERROR_INVALID_STARTING_CODESEG: Int = 188

public val ERROR_INVALID_SUB_AUTHORITY: Int = 1335

public val ERROR_INVALID_TABLE: Int = 1628

public val ERROR_INVALID_TARGET_HANDLE: Int = 114

public val ERROR_INVALID_TASK_INDEX: Int = 1551

public val ERROR_INVALID_TASK_NAME: Int = 1550

public val ERROR_INVALID_THREAD_ID: Int = 1444

public val ERROR_INVALID_TIME: Int = 1901

public val ERROR_INVALID_TOKEN: Int = 315

public val ERROR_INVALID_UNWIND_TARGET: Int = 544

public val ERROR_INVALID_USER_BUFFER: Int = 1784

public val ERROR_INVALID_USER_PRINCIPAL_NAME: Int = 8636

public val ERROR_INVALID_VARIANT: Int = 604

public val ERROR_INVALID_VERIFY_SWITCH: Int = 118

public val ERROR_INVALID_WINDOW_HANDLE: Int = 1400

public val ERROR_INVALID_WORKSTATION: Int = 1329

public val ERROR_IOPL_NOT_ENABLED: Int = 197

public val ERROR_IO_DEVICE: Int = 1117

public val ERROR_IO_INCOMPLETE: Int = 996

public val ERROR_IO_PENDING: Int = 997

public val ERROR_IO_PRIVILEGE_FAILED: Int = 571

public val ERROR_IO_REISSUE_AS_CACHED: Int = 3950

public val ERROR_IPSEC_IKE_TIMED_OUT: Int = 13805

public val ERROR_IP_ADDRESS_CONFLICT1: Int = 611

public val ERROR_IP_ADDRESS_CONFLICT2: Int = 612

public val ERROR_IRQ_BUSY: Int = 1119

public val ERROR_IS_JOINED: Int = 134

public val ERROR_IS_JOIN_PATH: Int = 147

public val ERROR_IS_JOIN_TARGET: Int = 133

public val ERROR_IS_SUBSTED: Int = 135

public val ERROR_IS_SUBST_PATH: Int = 146

public val ERROR_IS_SUBST_TARGET: Int = 149

public val ERROR_ITERATED_DATA_EXCEEDS_64K: Int = 194

public val ERROR_JOB_NO_CONTAINER: Int = 1505

public val ERROR_JOIN_TO_JOIN: Int = 138

public val ERROR_JOIN_TO_SUBST: Int = 140

public val ERROR_JOURNAL_DELETE_IN_PROGRESS: Int = 1178

public val ERROR_JOURNAL_ENTRY_DELETED: Int = 1181

public val ERROR_JOURNAL_HOOK_SET: Int = 1430

public val ERROR_JOURNAL_NOT_ACTIVE: Int = 1179

public val ERROR_KERNEL_APC: Int = 738

public val ERROR_KEY_DELETED: Int = 1018

public val ERROR_KEY_HAS_CHILDREN: Int = 1020

public val ERROR_KM_DRIVER_BLOCKED: Int = 1930

public val ERROR_LABEL_TOO_LONG: Int = 154

public val ERROR_LAST_ADMIN: Int = 1322

public val ERROR_LB_WITHOUT_TABSTOPS: Int = 1434

public val ERROR_LICENSE_QUOTA_EXCEEDED: Int = 1395

public val ERROR_LINUX_SUBSYSTEM_NOT_PRESENT: Int = 414

public val ERROR_LINUX_SUBSYSTEM_UPDATE_REQUIRED: Int = 444

public val ERROR_LISTBOX_ID_NOT_FOUND: Int = 1416

public val ERROR_LM_CROSS_ENCRYPTION_REQUIRED: Int = 1390

public val ERROR_LOCAL_POLICY_MODIFICATION_NOT_SUPPORTED: Int = 8653

public val ERROR_LOCAL_USER_SESSION_KEY: Int = 1303

public val ERROR_LOCKED: Int = 212

public val ERROR_LOCK_FAILED: Int = 167

public val ERROR_LOCK_VIOLATION: Int = 33

public val ERROR_LOGIN_TIME_RESTRICTION: Int = 1239

public val ERROR_LOGIN_WKSTA_RESTRICTION: Int = 1240

public val ERROR_LOGON_FAILURE: Int = 1326

public val ERROR_LOGON_NOT_GRANTED: Int = 1380

public val ERROR_LOGON_SERVER_CONFLICT: Int = 568

public val ERROR_LOGON_SESSION_COLLISION: Int = 1366

public val ERROR_LOGON_SESSION_EXISTS: Int = 1363

public val ERROR_LOGON_TYPE_NOT_GRANTED: Int = 1385

public val ERROR_LOG_FILE_FULL: Int = 1502

public val ERROR_LOG_HARD_ERROR: Int = 718

public val ERROR_LONGJUMP: Int = 682

public val ERROR_LOST_MODE_LOGON_RESTRICTION: Int = 1939

public val ERROR_LOST_WRITEBEHIND_DATA: Int = 596

public val ERROR_LOST_WRITEBEHIND_DATA_LOCAL_DISK_ERROR: Int = 790

public val ERROR_LOST_WRITEBEHIND_DATA_NETWORK_DISCONNECTED: Int = 788

public val ERROR_LOST_WRITEBEHIND_DATA_NETWORK_SERVER_ERROR: Int = 789

public val ERROR_LUIDS_EXHAUSTED: Int = 1334

public val ERROR_MACHINE_LOCKED: Int = 1271

public val ERROR_MAGAZINE_NOT_PRESENT: Int = 1163

public val ERROR_MAPPED_ALIGNMENT: Int = 1132

public val ERROR_MARKED_TO_DISALLOW_WRITES: Int = 348

public val ERROR_MARSHALL_OVERFLOW: Int = 603

public val ERROR_MAX_SESSIONS_REACHED: Int = 353

public val ERROR_MAX_THRDS_REACHED: Int = 164

public val ERROR_MCA_EXCEPTION: Int = 784

public val ERROR_MCA_OCCURED: Int = 651

public val ERROR_MEDIA_CHANGED: Int = 1110

public val ERROR_MEDIA_CHECK: Int = 679

public val ERROR_MEMBERS_PRIMARY_GROUP: Int = 1374

public val ERROR_MEMBER_IN_ALIAS: Int = 1378

public val ERROR_MEMBER_IN_GROUP: Int = 1320

public val ERROR_MEMBER_NOT_IN_ALIAS: Int = 1377

public val ERROR_MEMBER_NOT_IN_GROUP: Int = 1321

public val ERROR_MEMORY_HARDWARE: Int = 779

public val ERROR_MENU_ITEM_NOT_FOUND: Int = 1456

public val ERROR_MESSAGE_SYNC_ONLY: Int = 1159

public val ERROR_META_EXPANSION_TOO_LONG: Int = 208

public val ERROR_MISSING_SYSTEMFILE: Int = 573

public val ERROR_MOD_NOT_FOUND: Int = 126

public val ERROR_MORE_DATA: Int = 234

public val ERROR_MORE_WRITES: Int = 1120

public val ERROR_MOUNT_POINT_NOT_RESOLVED: Int = 649

public val ERROR_MP_PROCESSOR_MISMATCH: Int = 725

public val ERROR_MR_MID_NOT_FOUND: Int = 317

public val ERROR_MULTIPLE_FAULT_VIOLATION: Int = 640

public val ERROR_MUTANT_LIMIT_EXCEEDED: Int = 587

public val ERROR_MUTUAL_AUTH_FAILED: Int = 1397

public val ERROR_NEGATIVE_SEEK: Int = 131

public val ERROR_NESTING_NOT_ALLOWED: Int = 215

public val ERROR_NETLOGON_NOT_STARTED: Int = 1792

public val ERROR_NETNAME_DELETED: Int = 64

public val ERROR_NETWORK_ACCESS_DENIED: Int = 65

public val ERROR_NETWORK_ACCESS_DENIED_EDP: Int = 354

public val ERROR_NETWORK_BUSY: Int = 54

public val ERROR_NETWORK_UNREACHABLE: Int = 1231

public val ERROR_NET_OPEN_FAILED: Int = 570

public val ERROR_NET_WRITE_FAULT: Int = 88

public val ERROR_NOACCESS: Int = 998

public val ERROR_NOINTERFACE: Int = 632

public val ERROR_NOLOGON_INTERDOMAIN_TRUST_ACCOUNT: Int = 1807

public val ERROR_NOLOGON_SERVER_TRUST_ACCOUNT: Int = 1809

public val ERROR_NOLOGON_WORKSTATION_TRUST_ACCOUNT: Int = 1808

public val ERROR_NONE_MAPPED: Int = 1332

public val ERROR_NONPAGED_SYSTEM_RESOURCES: Int = 1451

public val ERROR_NON_ACCOUNT_SID: Int = 1257

public val ERROR_NON_DOMAIN_SID: Int = 1258

public val ERROR_NON_MDICHILD_WINDOW: Int = 1445

public val ERROR_NOTHING_TO_TERMINATE: Int = 758

public val ERROR_NOTIFICATION_GUID_ALREADY_DEFINED: Int = 309

public val ERROR_NOTIFY_CLEANUP: Int = 745

public val ERROR_NOTIFY_ENUM_DIR: Int = 1022

public val ERROR_NOT_ALLOWED_ON_SYSTEM_FILE: Int = 313

public val ERROR_NOT_ALL_ASSIGNED: Int = 1300

public val ERROR_NOT_APPCONTAINER: Int = 4250

public val ERROR_NOT_AUTHENTICATED: Int = 1244

public val ERROR_NOT_A_CLOUD_FILE: Int = 376

public val ERROR_NOT_A_CLOUD_SYNC_ROOT: Int = 405

public val ERROR_NOT_A_DAX_VOLUME: Int = 420

public val ERROR_NOT_A_REPARSE_POINT: Int = 4390

public val ERROR_NOT_CAPABLE: Int = 775

public val ERROR_NOT_CHILD_WINDOW: Int = 1442

public val ERROR_NOT_CONNECTED: Int = 2250

public val ERROR_NOT_CONTAINER: Int = 1207

public val ERROR_NOT_DAX_MAPPABLE: Int = 421

public val ERROR_NOT_DOS_DISK: Int = 26

public val ERROR_NOT_ENOUGH_MEMORY: Int = 8

public val ERROR_NOT_ENOUGH_QUOTA: Int = 1816

public val ERROR_NOT_ENOUGH_SERVER_MEMORY: Int = 1130

public val ERROR_NOT_EXPORT_FORMAT: Int = 6008

public val ERROR_NOT_FOUND: Int = 1168

public val ERROR_NOT_GUI_PROCESS: Int = 1471

public val ERROR_NOT_JOINED: Int = 136

public val ERROR_NOT_LOCKED: Int = 158

public val ERROR_NOT_LOGGED_ON: Int = 1245

public val ERROR_NOT_LOGON_PROCESS: Int = 1362

public val ERROR_NOT_OWNER: Int = 288

public val ERROR_NOT_READY: Int = 21

public val ERROR_NOT_READ_FROM_COPY: Int = 337

public val ERROR_NOT_REDUNDANT_STORAGE: Int = 333

public val ERROR_NOT_REGISTRY_FILE: Int = 1017

public val ERROR_NOT_SAFEBOOT_SERVICE: Int = 1084

public val ERROR_NOT_SAFE_MODE_DRIVER: Int = 646

public val ERROR_NOT_SAME_DEVICE: Int = 17

public val ERROR_NOT_SAME_OBJECT: Int = 1656

public val ERROR_NOT_SUBSTED: Int = 137

public val ERROR_NOT_SUPPORTED: Int = 50

public val ERROR_NOT_SUPPORTED_IN_APPCONTAINER: Int = 4252

public val ERROR_NOT_SUPPORTED_ON_DAX: Int = 360

public val ERROR_NOT_SUPPORTED_ON_SBS: Int = 1254

public val ERROR_NOT_SUPPORTED_ON_STANDARD_SERVER: Int = 8584

public val ERROR_NOT_SUPPORTED_WITH_AUDITING: Int = 499

public val ERROR_NOT_SUPPORTED_WITH_BTT: Int = 429

public val ERROR_NOT_SUPPORTED_WITH_BYPASSIO: Int = 493

public val ERROR_NOT_SUPPORTED_WITH_CACHED_HANDLE: Int = 509

public val ERROR_NOT_SUPPORTED_WITH_COMPRESSION: Int = 496

public val ERROR_NOT_SUPPORTED_WITH_DEDUPLICATION: Int = 498

public val ERROR_NOT_SUPPORTED_WITH_ENCRYPTION: Int = 495

public val ERROR_NOT_SUPPORTED_WITH_MONITORING: Int = 503

public val ERROR_NOT_SUPPORTED_WITH_REPLICATION: Int = 497

public val ERROR_NOT_SUPPORTED_WITH_SNAPSHOT: Int = 504

public val ERROR_NOT_SUPPORTED_WITH_VIRTUALIZATION: Int = 505

public val ERROR_NOT_TINY_STREAM: Int = 598

public val ERROR_NO_ACE_CONDITION: Int = 804

public val ERROR_NO_ASSOCIATION: Int = 1155

public val ERROR_NO_BYPASSIO_DRIVER_SUPPORT: Int = 494

public val ERROR_NO_CALLBACK_ACTIVE: Int = 614

public val ERROR_NO_DATA: Int = 232

public val ERROR_NO_DATA_DETECTED: Int = 1104

public val ERROR_NO_EFS: Int = 6004

public val ERROR_NO_EVENT_PAIR: Int = 580

public val ERROR_NO_GUID_TRANSLATION: Int = 560

public val ERROR_NO_IMPERSONATION_TOKEN: Int = 1309

public val ERROR_NO_INHERITANCE: Int = 1391

public val ERROR_NO_LOGON_SERVERS: Int = 1311

public val ERROR_NO_LOG_SPACE: Int = 1019

public val ERROR_NO_MATCH: Int = 1169

public val ERROR_NO_MEDIA_IN_DRIVE: Int = 1112

public val ERROR_NO_MORE_DEVICES: Int = 1248

public val ERROR_NO_MORE_FILES: Int = 18

public val ERROR_NO_MORE_ITEMS: Int = 259

public val ERROR_NO_MORE_MATCHES: Int = 626

public val ERROR_NO_MORE_SEARCH_HANDLES: Int = 113

public val ERROR_NO_MORE_USER_HANDLES: Int = 1158

public val ERROR_NO_NETWORK: Int = 1222

public val ERROR_NO_NET_OR_BAD_PATH: Int = 1203

public val ERROR_NO_NVRAM_RESOURCES: Int = 1470

public val ERROR_NO_PAGEFILE: Int = 578

public val ERROR_NO_PHYSICALLY_ALIGNED_FREE_SPACE_FOUND: Int = 408

public val ERROR_NO_PROC_SLOTS: Int = 89

public val ERROR_NO_PROMOTION_ACTIVE: Int = 8222

public val ERROR_NO_QUOTAS_FOR_ACCOUNT: Int = 1302

public val ERROR_NO_RANGES_PROCESSED: Int = 312

public val ERROR_NO_RECOVERY_POLICY: Int = 6003

public val ERROR_NO_RECOVERY_PROGRAM: Int = 1082

public val ERROR_NO_SCROLLBARS: Int = 1447

public val ERROR_NO_SECRETS: Int = 8620

public val ERROR_NO_SECURITY_ON_OBJECT: Int = 1350

public val ERROR_NO_SHUTDOWN_IN_PROGRESS: Int = 1116

public val ERROR_NO_SIGNAL_SENT: Int = 205

public val ERROR_NO_SITENAME: Int = 1919

public val ERROR_NO_SITE_SETTINGS_OBJECT: Int = 8619

public val ERROR_NO_SPOOL_SPACE: Int = 62

public val ERROR_NO_SUCH_ALIAS: Int = 1376

public val ERROR_NO_SUCH_DEVICE: Int = 433

public val ERROR_NO_SUCH_DOMAIN: Int = 1355

public val ERROR_NO_SUCH_GROUP: Int = 1319

public val ERROR_NO_SUCH_LOGON_SESSION: Int = 1312

public val ERROR_NO_SUCH_MEMBER: Int = 1387

public val ERROR_NO_SUCH_PACKAGE: Int = 1364

public val ERROR_NO_SUCH_PRIVILEGE: Int = 1313

public val ERROR_NO_SUCH_SITE: Int = 1249

public val ERROR_NO_SUCH_USER: Int = 1317

public val ERROR_NO_SYSTEM_MENU: Int = 1437

public val ERROR_NO_SYSTEM_RESOURCES: Int = 1450

public val ERROR_NO_TASK_QUEUE: Int = 427

public val ERROR_NO_TOKEN: Int = 1008

public val ERROR_NO_TRACKING_SERVICE: Int = 1172

public val ERROR_NO_TRUST_LSA_SECRET: Int = 1786

public val ERROR_NO_TRUST_SAM_ACCOUNT: Int = 1787

public val ERROR_NO_UNICODE_TRANSLATION: Int = 1113

public val ERROR_NO_USER_KEYS: Int = 6006

public val ERROR_NO_USER_SESSION_KEY: Int = 1394

public val ERROR_NO_VOLUME_ID: Int = 1173

public val ERROR_NO_VOLUME_LABEL: Int = 125

public val ERROR_NO_WILDCARD_CHARACTERS: Int = 1417

public val ERROR_NO_WORK_DONE: Int = 235

public val ERROR_NO_WRITABLE_DC_FOUND: Int = 8621

public val ERROR_NO_YIELD_PERFORMED: Int = 721

public val ERROR_NTLM_BLOCKED: Int = 1937

public val ERROR_NT_CROSS_ENCRYPTION_REQUIRED: Int = 1386

public val ERROR_NULL_LM_PASSWORD: Int = 1304

public val ERROR_OBJECT_IS_IMMUTABLE: Int = 4449

public val ERROR_OBJECT_NAME_EXISTS: Int = 698

public val ERROR_OBJECT_NOT_EXTERNALLY_BACKED: Int = 342

public val ERROR_OFFLOAD_READ_FILE_NOT_SUPPORTED: Int = 4442

public val ERROR_OFFLOAD_READ_FLT_NOT_SUPPORTED: Int = 4440

public val ERROR_OFFLOAD_WRITE_FILE_NOT_SUPPORTED: Int = 4443

public val ERROR_OFFLOAD_WRITE_FLT_NOT_SUPPORTED: Int = 4441

public val ERROR_OFFSET_ALIGNMENT_VIOLATION: Int = 327

public val ERROR_OLD_WIN_VERSION: Int = 1150

public val ERROR_ONLY_IF_CONNECTED: Int = 1251

public val ERROR_OPEN_FAILED: Int = 110

public val ERROR_OPEN_FILES: Int = 2401

public val ERROR_OPERATION_ABORTED: Int = 995

public val ERROR_OPERATION_IN_PROGRESS: Int = 329

public val ERROR_OPLOCK_BREAK_IN_PROGRESS: Int = 742

public val ERROR_OPLOCK_HANDLE_CLOSED: Int = 803

public val ERROR_OPLOCK_NOT_GRANTED: Int = 300

public val ERROR_OPLOCK_SWITCHED_TO_NEW_HANDLE: Int = 800

public val ERROR_ORPHAN_NAME_EXHAUSTED: Int = 799

public val ERROR_OUTOFMEMORY: Int = 14

public val ERROR_OUT_OF_PAPER: Int = 28

public val ERROR_OUT_OF_STRUCTURES: Int = 84

public val ERROR_OVERRIDE_NOCHANGES: Int = 1252

public val ERROR_PAGED_SYSTEM_RESOURCES: Int = 1452

public val ERROR_PAGEFILE_CREATE_FAILED: Int = 576

public val ERROR_PAGEFILE_NOT_SUPPORTED: Int = 491

public val ERROR_PAGEFILE_QUOTA: Int = 1454

public val ERROR_PAGEFILE_QUOTA_EXCEEDED: Int = 567

public val ERROR_PAGE_FAULT_COPY_ON_WRITE: Int = 749

public val ERROR_PAGE_FAULT_DEMAND_ZERO: Int = 748

public val ERROR_PAGE_FAULT_GUARD_PAGE: Int = 750

public val ERROR_PAGE_FAULT_PAGING_FILE: Int = 751

public val ERROR_PAGE_FAULT_TRANSITION: Int = 747

public val ERROR_PARAMETER_QUOTA_EXCEEDED: Int = 1283

public val ERROR_PARTIAL_COPY: Int = 299

public val ERROR_PARTITION_FAILURE: Int = 1105

public val ERROR_PARTITION_TERMINATING: Int = 1184

public val ERROR_PASSWORD_CHANGE_REQUIRED: Int = 1938

public val ERROR_PASSWORD_EXPIRED: Int = 1330

public val ERROR_PASSWORD_MUST_CHANGE: Int = 1907

public val ERROR_PASSWORD_RESTRICTION: Int = 1325

public val ERROR_PATCH_MANAGED_ADVERTISED_PRODUCT: Int = 1651

public val ERROR_PATCH_NO_SEQUENCE: Int = 1648

public val ERROR_PATCH_PACKAGE_INVALID: Int = 1636

public val ERROR_PATCH_PACKAGE_OPEN_FAILED: Int = 1635

public val ERROR_PATCH_PACKAGE_REJECTED: Int = 1643

public val ERROR_PATCH_PACKAGE_UNSUPPORTED: Int = 1637

public val ERROR_PATCH_REMOVAL_DISALLOWED: Int = 1649

public val ERROR_PATCH_REMOVAL_UNSUPPORTED: Int = 1646

public val ERROR_PATCH_TARGET_NOT_FOUND: Int = 1642

public val ERROR_PATH_BUSY: Int = 148

public val ERROR_PATH_NOT_FOUND: Int = 3

public val ERROR_PER_USER_TRUST_QUOTA_EXCEEDED: Int = 1932

public val ERROR_PIPE_BUSY: Int = 231

public val ERROR_PIPE_CONNECTED: Int = 535

public val ERROR_PIPE_LISTENING: Int = 536

public val ERROR_PIPE_LOCAL: Int = 229

public val ERROR_PIPE_NOT_CONNECTED: Int = 233

public val ERROR_PKINIT_FAILURE: Int = 1263

public val ERROR_PLUGPLAY_QUERY_VETOED: Int = 683

public val ERROR_PNP_BAD_MPS_TABLE: Int = 671

public val ERROR_PNP_INVALID_ID: Int = 674

public val ERROR_PNP_IRQ_TRANSLATION_FAILED: Int = 673

public val ERROR_PNP_QUERY_REMOVE_DEVICE_TIMEOUT: Int = 480

public val ERROR_PNP_QUERY_REMOVE_RELATED_DEVICE_TIMEOUT: Int = 481

public val ERROR_PNP_QUERY_REMOVE_UNRELATED_DEVICE_TIMEOUT: Int = 482

public val ERROR_PNP_REBOOT_REQUIRED: Int = 638

public val ERROR_PNP_RESTART_ENUMERATION: Int = 636

public val ERROR_PNP_TRANSLATION_FAILED: Int = 672

public val ERROR_POINT_NOT_FOUND: Int = 1171

public val ERROR_POLICY_OBJECT_NOT_FOUND: Int = 8219

public val ERROR_POLICY_ONLY_IN_DS: Int = 8220

public val ERROR_POPUP_ALREADY_ACTIVE: Int = 1446

public val ERROR_PORT_MESSAGE_TOO_LONG: Int = 546

public val ERROR_PORT_NOT_SET: Int = 642

public val ERROR_PORT_UNREACHABLE: Int = 1234

public val ERROR_POSSIBLE_DEADLOCK: Int = 1131

public val ERROR_POTENTIAL_FILE_FOUND: Int = 1180

public val ERROR_PREDEFINED_HANDLE: Int = 714

public val ERROR_PRIMARY_TRANSPORT_CONNECT_FAILED: Int = 746

public val ERROR_PRINTER_ALREADY_EXISTS: Int = 1802

public val ERROR_PRINTER_DELETED: Int = 1905

public val ERROR_PRINTER_DRIVER_ALREADY_INSTALLED: Int = 1795

public val ERROR_PRINTQ_FULL: Int = 61

public val ERROR_PRINT_CANCELLED: Int = 63

public val ERROR_PRIVATE_DIALOG_INDEX: Int = 1415

public val ERROR_PRIVILEGE_NOT_HELD: Int = 1314

public val ERROR_PROCESS_ABORTED: Int = 1067

public val ERROR_PROCESS_IN_JOB: Int = 760

public val ERROR_PROCESS_IS_PROTECTED: Int = 1293

public val ERROR_PROCESS_MODE_ALREADY_BACKGROUND: Int = 402

public val ERROR_PROCESS_MODE_NOT_BACKGROUND: Int = 403

public val ERROR_PROCESS_NOT_IN_JOB: Int = 759

public val ERROR_PROC_NOT_FOUND: Int = 127

public val ERROR_PRODUCT_UNINSTALLED: Int = 1614

public val ERROR_PRODUCT_VERSION: Int = 1638

public val ERROR_PROFILING_AT_LIMIT: Int = 553

public val ERROR_PROFILING_NOT_STARTED: Int = 550

public val ERROR_PROFILING_NOT_STOPPED: Int = 551

public val ERROR_PROMOTION_ACTIVE: Int = 8221

public val ERROR_PROTOCOL_UNREACHABLE: Int = 1233

public val ERROR_PWD_HISTORY_CONFLICT: Int = 617

public val ERROR_PWD_TOO_LONG: Int = 657

public val ERROR_PWD_TOO_RECENT: Int = 616

public val ERROR_PWD_TOO_SHORT: Int = 615

public val ERROR_QUOTA_ACTIVITY: Int = 810

public val ERROR_QUOTA_LIST_INCONSISTENT: Int = 621

public val ERROR_RANGE_LIST_CONFLICT: Int = 627

public val ERROR_RANGE_NOT_FOUND: Int = 644

public val ERROR_READ_FAULT: Int = 30

public val ERROR_RECEIVE_EXPEDITED: Int = 708

public val ERROR_RECEIVE_PARTIAL: Int = 707

public val ERROR_RECEIVE_PARTIAL_EXPEDITED: Int = 709

public val ERROR_RECOVERY_FAILURE: Int = 1279

public val ERROR_REDIRECTOR_HAS_OPEN_HANDLES: Int = 1794

public val ERROR_REDIR_PAUSED: Int = 72

public val ERROR_REGISTRY_CORRUPT: Int = 1015

public val ERROR_REGISTRY_HIVE_RECOVERED: Int = 685

public val ERROR_REGISTRY_IO_FAILED: Int = 1016

public val ERROR_REGISTRY_QUOTA_LIMIT: Int = 613

public val ERROR_REGISTRY_RECOVERED: Int = 1014

public val ERROR_REG_NAT_CONSUMPTION: Int = 1261

public val ERROR_RELOC_CHAIN_XEEDS_SEGLIM: Int = 201

public val ERROR_REMOTE_PRINT_CONNECTIONS_BLOCKED: Int = 1936

public val ERROR_REMOTE_SESSION_LIMIT_EXCEEDED: Int = 1220

public val ERROR_REMOTE_STORAGE_MEDIA_ERROR: Int = 4352

public val ERROR_REMOTE_STORAGE_NOT_ACTIVE: Int = 4351

public val ERROR_REM_NOT_LIST: Int = 51

public val ERROR_REPARSE: Int = 741

public val ERROR_REPARSE_ATTRIBUTE_CONFLICT: Int = 4391

public val ERROR_REPARSE_OBJECT: Int = 755

public val ERROR_REPARSE_POINT_ENCOUNTERED: Int = 4395

public val ERROR_REPARSE_TAG_INVALID: Int = 4393

public val ERROR_REPARSE_TAG_MISMATCH: Int = 4394

public val ERROR_REPLY_MESSAGE_MISMATCH: Int = 595

public val ERROR_REQUEST_ABORTED: Int = 1235

public val ERROR_REQUEST_OUT_OF_SEQUENCE: Int = 776

public val ERROR_REQUEST_PAUSED: Int = 3050

public val ERROR_REQUIRES_INTERACTIVE_WINDOWSTATION: Int = 1459

public val ERROR_REQ_NOT_ACCEP: Int = 71

public val ERROR_RESIDENT_FILE_NOT_SUPPORTED: Int = 334

public val ERROR_RESOURCE_CALL_TIMED_OUT: Int = 5910

public val ERROR_RESOURCE_DATA_NOT_FOUND: Int = 1812

public val ERROR_RESOURCE_LANG_NOT_FOUND: Int = 1815

public val ERROR_RESOURCE_NAME_NOT_FOUND: Int = 1814

public val ERROR_RESOURCE_REQUIREMENTS_CHANGED: Int = 756

public val ERROR_RESOURCE_TYPE_NOT_FOUND: Int = 1813

public val ERROR_RESTART_APPLICATION: Int = 1467

public val ERROR_RESUME_HIBERNATION: Int = 727

public val ERROR_RETRY: Int = 1237

public val ERROR_RETURN_ADDRESS_HIJACK_ATTEMPT: Int = 1662

public val ERROR_REVISION_MISMATCH: Int = 1306

public val ERROR_RING2SEG_MUST_BE_MOVABLE: Int = 200

public val ERROR_RING2_STACK_IN_USE: Int = 207

public val ERROR_RMODE_APP: Int = 1153

public val ERROR_ROWSNOTRELEASED: Int = 772

public val ERROR_RUNLEVEL_SWITCH_AGENT_TIMEOUT: Int = 15403

public val ERROR_RUNLEVEL_SWITCH_TIMEOUT: Int = 15402

public val ERROR_RWRAW_ENCRYPTED_FILE_NOT_ENCRYPTED: Int = 410

public val ERROR_RWRAW_ENCRYPTED_INVALID_EDATAINFO_FILEOFFSET: Int = 411

public val ERROR_RWRAW_ENCRYPTED_INVALID_EDATAINFO_FILERANGE: Int = 412

public val ERROR_RWRAW_ENCRYPTED_INVALID_EDATAINFO_PARAMETER: Int = 413

public val ERROR_RXACT_COMMITTED: Int = 744

public val ERROR_RXACT_COMMIT_FAILURE: Int = 1370

public val ERROR_RXACT_COMMIT_NECESSARY: Int = 678

public val ERROR_RXACT_INVALID_STATE: Int = 1369

public val ERROR_RXACT_STATE_CREATED: Int = 701

public val ERROR_SAME_DRIVE: Int = 143

public val ERROR_SAM_INIT_FAILURE: Int = 8541

public val ERROR_SCOPE_NOT_FOUND: Int = 318

public val ERROR_SCREEN_ALREADY_LOCKED: Int = 1440

public val ERROR_SCRUB_DATA_DISABLED: Int = 332

public val ERROR_SECRET_TOO_LONG: Int = 1382

public val ERROR_SECTION_DIRECT_MAP_ONLY: Int = 819

public val ERROR_SECTOR_NOT_FOUND: Int = 27

public val ERROR_SECURITY_DENIES_OPERATION: Int = 447

public val ERROR_SECURITY_STREAM_IS_INCONSISTENT: Int = 306

public val ERROR_SEEK: Int = 25

public val ERROR_SEEK_ON_DEVICE: Int = 132

public val ERROR_SEGMENT_NOTIFICATION: Int = 702

public val ERROR_SEM_IS_SET: Int = 102

public val ERROR_SEM_NOT_FOUND: Int = 187

public val ERROR_SEM_OWNER_DIED: Int = 105

public val ERROR_SEM_TIMEOUT: Int = 121

public val ERROR_SEM_USER_LIMIT: Int = 106

public val ERROR_SERIAL_NO_DEVICE: Int = 1118

public val ERROR_SERVER_DISABLED: Int = 1341

public val ERROR_SERVER_HAS_OPEN_HANDLES: Int = 1811

public val ERROR_SERVER_NOT_DISABLED: Int = 1342

public val ERROR_SERVER_SHUTDOWN_IN_PROGRESS: Int = 1255

public val ERROR_SERVER_SID_MISMATCH: Int = 628

public val ERROR_SERVER_TRANSPORT_CONFLICT: Int = 816

public val ERROR_SERVICE_ALREADY_RUNNING: Int = 1056

public val ERROR_SERVICE_CANNOT_ACCEPT_CTRL: Int = 1061

public val ERROR_SERVICE_DATABASE_LOCKED: Int = 1055

public val ERROR_SERVICE_DEPENDENCY_DELETED: Int = 1075

public val ERROR_SERVICE_DEPENDENCY_FAIL: Int = 1068

public val ERROR_SERVICE_DISABLED: Int = 1058

public val ERROR_SERVICE_DOES_NOT_EXIST: Int = 1060

public val ERROR_SERVICE_EXISTS: Int = 1073

public val ERROR_SERVICE_LOGON_FAILED: Int = 1069

public val ERROR_SERVICE_MARKED_FOR_DELETE: Int = 1072

public val ERROR_SERVICE_NEVER_STARTED: Int = 1077

public val ERROR_SERVICE_NOTIFICATION: Int = 716

public val ERROR_SERVICE_NOTIFY_CLIENT_LAGGING: Int = 1294

public val ERROR_SERVICE_NOT_ACTIVE: Int = 1062

public val ERROR_SERVICE_NOT_FOUND: Int = 1243

public val ERROR_SERVICE_NOT_IN_EXE: Int = 1083

public val ERROR_SERVICE_NO_THREAD: Int = 1054

public val ERROR_SERVICE_REQUEST_TIMEOUT: Int = 1053

public val ERROR_SERVICE_SPECIFIC_ERROR: Int = 1066

public val ERROR_SERVICE_START_HANG: Int = 1070

public val ERROR_SESSION_CREDENTIAL_CONFLICT: Int = 1219

public val ERROR_SESSION_KEY_TOO_SHORT: Int = 501

public val ERROR_SETCOUNT_ON_BAD_LB: Int = 1433

public val ERROR_SETMARK_DETECTED: Int = 1103

public val ERROR_SET_CONTEXT_DENIED: Int = 1660

public val ERROR_SET_NOT_FOUND: Int = 1170

public val ERROR_SET_POWER_STATE_FAILED: Int = 1141

public val ERROR_SET_POWER_STATE_VETOED: Int = 1140

public val ERROR_SHARED_POLICY: Int = 8218

public val ERROR_SHARING_BUFFER_EXCEEDED: Int = 36

public val ERROR_SHARING_PAUSED: Int = 70

public val ERROR_SHARING_VIOLATION: Int = 32

public val ERROR_SHORT_NAMES_NOT_ENABLED_ON_VOLUME: Int = 305

public val ERROR_SHUTDOWN_DISKS_NOT_IN_MAINTENANCE_MODE: Int = 1192

public val ERROR_SHUTDOWN_IN_PROGRESS: Int = 1115

public val ERROR_SHUTDOWN_IS_SCHEDULED: Int = 1190

public val ERROR_SHUTDOWN_USERS_LOGGED_ON: Int = 1191

public val ERROR_SIGNAL_PENDING: Int = 162

public val ERROR_SIGNAL_REFUSED: Int = 156

public val ERROR_SINGLE_INSTANCE_APP: Int = 1152

public val ERROR_SMARTCARD_SUBSYSTEM_FAILURE: Int = 1264

public val ERROR_SMB1_NOT_AVAILABLE: Int = 384

public val ERROR_SMB_GUEST_LOGON_BLOCKED: Int = 1272

public val ERROR_SMR_GARBAGE_COLLECTION_REQUIRED: Int = 4445

public val ERROR_SOME_NOT_MAPPED: Int = 1301

public val ERROR_SOURCE_ELEMENT_EMPTY: Int = 1160

public val ERROR_SPARSE_FILE_NOT_SUPPORTED: Int = 490

public val ERROR_SPECIAL_ACCOUNT: Int = 1371

public val ERROR_SPECIAL_GROUP: Int = 1372

public val ERROR_SPECIAL_USER: Int = 1373

public val ERROR_SRC_SRV_DLL_LOAD_FAILED: Int = 428

public val ERROR_STACK_BUFFER_OVERRUN: Int = 1282

public val ERROR_STACK_OVERFLOW: Int = 1001

public val ERROR_STACK_OVERFLOW_READ: Int = 599

public val ERROR_STOPPED_ON_SYMLINK: Int = 681

public val ERROR_STORAGE_LOST_DATA_PERSISTENCE: Int = 368

public val ERROR_STORAGE_RESERVE_ALREADY_EXISTS: Int = 418

public val ERROR_STORAGE_RESERVE_DOES_NOT_EXIST: Int = 417

public val ERROR_STORAGE_RESERVE_ID_INVALID: Int = 416

public val ERROR_STORAGE_RESERVE_NOT_EMPTY: Int = 419

public val ERROR_STORAGE_STACK_ACCESS_DENIED: Int = 472

public val ERROR_STORAGE_TOPOLOGY_ID_MISMATCH: Int = 345

public val ERROR_STRICT_CFG_VIOLATION: Int = 1657

public val ERROR_SUBST_TO_JOIN: Int = 141

public val ERROR_SUBST_TO_SUBST: Int = 139

public val ERROR_SUCCESS: Int = 0

public val ERROR_SUCCESS_REBOOT_INITIATED: Int = 1641

public val ERROR_SWAPERROR: Int = 999

public val ERROR_SYMLINK_CLASS_DISABLED: Int = 1463

public val ERROR_SYMLINK_NOT_SUPPORTED: Int = 1464

public val ERROR_SYNCHRONIZATION_REQUIRED: Int = 569

public val ERROR_SYNC_FOREGROUND_REFRESH_REQUIRED: Int = 1274

public val ERROR_SYSTEM_HIVE_TOO_LARGE: Int = 653

public val ERROR_SYSTEM_IMAGE_BAD_SIGNATURE: Int = 637

public val ERROR_SYSTEM_POWERSTATE_COMPLEX_TRANSITION: Int = 783

public val ERROR_SYSTEM_POWERSTATE_TRANSITION: Int = 782

public val ERROR_SYSTEM_PROCESS_TERMINATED: Int = 591

public val ERROR_SYSTEM_SHUTDOWN: Int = 641

public val ERROR_SYSTEM_TRACE: Int = 150

public val ERROR_THREAD_1_INACTIVE: Int = 210

public val ERROR_THREAD_ALREADY_IN_TASK: Int = 1552

public val ERROR_THREAD_MODE_ALREADY_BACKGROUND: Int = 400

public val ERROR_THREAD_MODE_NOT_BACKGROUND: Int = 401

public val ERROR_THREAD_NOT_IN_PROCESS: Int = 566

public val ERROR_THREAD_WAS_SUSPENDED: Int = 699

public val ERROR_TIMEOUT: Int = 1460

public val ERROR_TIMER_NOT_CANCELED: Int = 541

public val ERROR_TIMER_RESOLUTION_NOT_SET: Int = 607

public val ERROR_TIMER_RESUME_IGNORED: Int = 722

public val ERROR_TIME_SENSITIVE_THREAD: Int = 422

public val ERROR_TIME_SKEW: Int = 1398

public val ERROR_TLW_WITH_WSCHILD: Int = 1406

public val ERROR_TOKEN_ALREADY_IN_USE: Int = 1375

public val ERROR_TOO_MANY_CMDS: Int = 56

public val ERROR_TOO_MANY_CONTEXT_IDS: Int = 1384

public val ERROR_TOO_MANY_DESCRIPTORS: Int = 331

public val ERROR_TOO_MANY_LINKS: Int = 1142

public val ERROR_TOO_MANY_LUIDS_REQUESTED: Int = 1333

public val ERROR_TOO_MANY_MODULES: Int = 214

public val ERROR_TOO_MANY_MUXWAITERS: Int = 152

public val ERROR_TOO_MANY_NAMES: Int = 68

public val ERROR_TOO_MANY_OPEN_FILES: Int = 4

public val ERROR_TOO_MANY_POSTS: Int = 298

public val ERROR_TOO_MANY_SECRETS: Int = 1381

public val ERROR_TOO_MANY_SEMAPHORES: Int = 100

public val ERROR_TOO_MANY_SEM_REQUESTS: Int = 103

public val ERROR_TOO_MANY_SESS: Int = 69

public val ERROR_TOO_MANY_SIDS: Int = 1389

public val ERROR_TOO_MANY_TCBS: Int = 155

public val ERROR_TOO_MANY_THREADS: Int = 565

public val ERROR_TRANSLATION_COMPLETE: Int = 757

public val ERROR_TRUSTED_DOMAIN_FAILURE: Int = 1788

public val ERROR_TRUSTED_RELATIONSHIP_FAILURE: Int = 1789

public val ERROR_TRUST_FAILURE: Int = 1790

public val ERROR_UNABLE_TO_LOCK_MEDIA: Int = 1108

public val ERROR_UNABLE_TO_MOVE_REPLACEMENT: Int = 1176

public val ERROR_UNABLE_TO_MOVE_REPLACEMENT_2: Int = 1177

public val ERROR_UNABLE_TO_REMOVE_REPLACED: Int = 1175

public val ERROR_UNABLE_TO_UNLOAD_MEDIA: Int = 1109

public val ERROR_UNDEFINED_CHARACTER: Int = 583

public val ERROR_UNDEFINED_SCOPE: Int = 319

public val ERROR_UNEXPECTED_MM_CREATE_ERR: Int = 556

public val ERROR_UNEXPECTED_MM_EXTEND_ERR: Int = 558

public val ERROR_UNEXPECTED_MM_MAP_ERROR: Int = 557

public val ERROR_UNEXPECTED_NTCACHEMANAGER_ERROR: Int = 443

public val ERROR_UNEXP_NET_ERR: Int = 59

public val ERROR_UNHANDLED_EXCEPTION: Int = 574

public val ERROR_UNIDENTIFIED_ERROR: Int = 1287

public val ERROR_UNKNOWN_COMPONENT: Int = 1607

public val ERROR_UNKNOWN_FEATURE: Int = 1606

public val ERROR_UNKNOWN_PATCH: Int = 1647

public val ERROR_UNKNOWN_PORT: Int = 1796

public val ERROR_UNKNOWN_PRINTER_DRIVER: Int = 1797

public val ERROR_UNKNOWN_PRINTPROCESSOR: Int = 1798

public val ERROR_UNKNOWN_PRODUCT: Int = 1605

public val ERROR_UNKNOWN_PROPERTY: Int = 1608

public val ERROR_UNKNOWN_REVISION: Int = 1305

public val ERROR_UNRECOGNIZED_MEDIA: Int = 1785

public val ERROR_UNRECOGNIZED_VOLUME: Int = 1005

public val ERROR_UNSATISFIED_DEPENDENCIES: Int = 441

public val ERROR_UNSUPPORTED_COMPRESSION: Int = 618

public val ERROR_UNSUPPORTED_TYPE: Int = 1630

public val ERROR_UNTRUSTED_MOUNT_POINT: Int = 448

public val ERROR_UNWIND: Int = 542

public val ERROR_UNWIND_CONSOLIDATE: Int = 684

public val ERROR_USER_APC: Int = 737

public val ERROR_USER_DELETE_TRUST_QUOTA_EXCEEDED: Int = 1934

public val ERROR_USER_EXISTS: Int = 1316

public val ERROR_USER_MAPPED_FILE: Int = 1224

public val ERROR_USER_PROFILE_LOAD: Int = 500

public val ERROR_VALIDATE_CONTINUE: Int = 625

public val ERROR_VC_DISCONNECTED: Int = 240

public val ERROR_VDM_DISALLOWED: Int = 1286

public val ERROR_VDM_HARD_ERROR: Int = 593

public val ERROR_VERIFIER_STOP: Int = 537

public val ERROR_VERSION_PARSE_ERROR: Int = 777

public val ERROR_VIRUS_DELETED: Int = 226

public val ERROR_VIRUS_INFECTED: Int = 225

public val ERROR_VOLSNAP_HIBERNATE_READY: Int = 761

public val ERROR_VOLSNAP_PREPARE_HIBERNATE: Int = 655

public val ERROR_VOLUME_MOUNTED: Int = 743

public val ERROR_VOLUME_NOT_CLUSTER_ALIGNED: Int = 407

public val ERROR_VOLUME_NOT_SIS_ENABLED: Int = 4500

public val ERROR_VOLUME_NOT_SUPPORTED: Int = 492

public val ERROR_VOLUME_NOT_SUPPORT_EFS: Int = 6014

public val ERROR_VOLUME_WRITE_ACCESS_DENIED: Int = 508

public val ERROR_WAIT_1: Int = 731

public val ERROR_WAIT_2: Int = 732

public val ERROR_WAIT_3: Int = 733

public val ERROR_WAIT_63: Int = 734

public val ERROR_WAIT_FOR_OPLOCK: Int = 765

public val ERROR_WAIT_NO_CHILDREN: Int = 128

public val ERROR_WAKE_SYSTEM: Int = 730

public val ERROR_WAKE_SYSTEM_DEBUGGER: Int = 675

public val ERROR_WAS_LOCKED: Int = 717

public val ERROR_WAS_UNLOCKED: Int = 715

public val ERROR_WEAK_WHFBKEY_BLOCKED: Int = 8651

public val ERROR_WINDOW_NOT_COMBOBOX: Int = 1423

public val ERROR_WINDOW_NOT_DIALOG: Int = 1420

public val ERROR_WINDOW_OF_OTHER_THREAD: Int = 1408

public val ERROR_WIP_ENCRYPTION_FAILED: Int = 6023

public val ERROR_WOF_FILE_RESOURCE_TABLE_CORRUPT: Int = 4448

public val ERROR_WOF_WIM_HEADER_CORRUPT: Int = 4446

public val ERROR_WOF_WIM_RESOURCE_TABLE_CORRUPT: Int = 4447

public val ERROR_WORKING_SET_QUOTA: Int = 1453

public val ERROR_WOW_ASSERTION: Int = 670

public val ERROR_WRITE_FAULT: Int = 29

public val ERROR_WRITE_PROTECT: Int = 19

public val ERROR_WRONG_COMPARTMENT: Int = 1468

public val ERROR_WRONG_DISK: Int = 34

public val ERROR_WRONG_EFS: Int = 6005

public val ERROR_WRONG_PASSWORD: Int = 1323

public val ERROR_WRONG_TARGET_NAME: Int = 1396

public val ERROR_WX86_ERROR: Int = 540

public val ERROR_WX86_WARNING: Int = 539

public val ERROR_XMLDSIG_ERROR: Int = 1466

public val ERROR_XML_PARSE_ERROR: Int = 1465

public typealias ExceptionDisposition = Int

public val EXCEPTION_MAXIMUM_PARAMETERS: Int = 15

public open class ExceptionPointers internal constructor(
    exceptionRecordCtorArg: PexceptionRecord,
    contextRecordCtorArg: Pcontext,
) {
    public var exceptionRecord: PexceptionRecord = exceptionRecordCtorArg
        internal set

    public var contextRecord: Pcontext = contextRecordCtorArg
        internal set

    public open fun clone(): ExceptionPointers = ExceptionPointers(exceptionRecord, contextRecord)

    public companion object {
        public operator fun invoke(
            exceptionRecord: PexceptionRecord,
            contextRecord: Pcontext,
        ): ExceptionPointers = ExceptionPointers(
            exceptionRecordCtorArg = exceptionRecord,
            contextRecordCtorArg = contextRecord,
        )
    }
}

public open class ExceptionRecord internal constructor(
    public var exceptionCode: UInt,
    public var exceptionFlags: UInt,
    exceptionRecordCtorArg: MutPtr,
    exceptionAddressCtorArg: MutPtr,
    public var numberParameters: UInt,
    public var exceptionInformation: IntArray,
) {
    public var exceptionRecord: MutPtr = exceptionRecordCtorArg
        internal set

    public var exceptionAddress: MutPtr = exceptionAddressCtorArg
        internal set

    public open fun clone(): ExceptionRecord = ExceptionRecord(exceptionCode, exceptionFlags, exceptionRecord, exceptionAddress, numberParameters, exceptionInformation)

    public companion object {
        public fun typeLayout(): TypeLayout<ExceptionRecord> =
            object : TypeLayout<ExceptionRecord> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 152L
                override fun readNative(addr: Long): ExceptionRecord =
                    ExceptionRecord(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveArrayLayouts.usize64Array(15)).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: ExceptionRecord) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.exceptionCode)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.exceptionFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.exceptionRecord)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.exceptionAddress)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.numberParameters)
                    (PrimitiveArrayLayouts.usize64Array(15)).writeNative(addr + 32L, value.exceptionInformation)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExceptionRecord =
                    ExceptionRecord(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveArrayLayouts.usize64Array(15)).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExceptionRecord) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.exceptionCode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.exceptionFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.exceptionRecord)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.exceptionAddress)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.numberParameters)
                    (PrimitiveArrayLayouts.usize64Array(15)).writeBytes(buf, offset + 32, value.exceptionInformation)
                }
            }

        public operator fun invoke(
            exceptionCode: UInt,
            exceptionFlags: UInt,
            exceptionRecord: MutPtr,
            exceptionAddress: MutPtr,
            numberParameters: UInt,
            exceptionInformation: IntArray,
        ): ExceptionRecord = ExceptionRecord(
            exceptionCode = exceptionCode,
            exceptionFlags = exceptionFlags,
            exceptionRecordCtorArg = exceptionRecord,
            exceptionAddressCtorArg = exceptionAddress,
            numberParameters = numberParameters,
            exceptionInformation = exceptionInformation,
        )

        public fun default(): ExceptionRecord = null
    }
}

public val EXCEPTION_STACK_OVERFLOW: Int = -1073741571

public val EXTENDED_STARTUPINFO_PRESENT: Int = 524288

public val E_FAIL: Hresult = 0x80004005u.toInt()

public val E_INVALIDARG: Hresult = 0x80070057u.toInt()

public val E_NOTIMPL: Hresult = 0x80004001u.toInt()

public val EXCEPTION_COLLIDED_UNWIND: ExceptionDisposition = 3

public val EXCEPTION_CONTINUE_EXECUTION: ExceptionDisposition = 0

public val EXCEPTION_CONTINUE_SEARCH: ExceptionDisposition = 1

public val EXCEPTION_NESTED_EXCEPTION: ExceptionDisposition = 2

public val FACILITY_NT_BIT: Int = 268435456

public val FALSE: Int = 0

public fun interface FarprocFn {
    public operator fun invoke(): Long
}

public typealias Farproc = FarprocFn?

public val FAST_FAIL_FATAL_APP_EXIT: Int = 7

public open class Filetime(
    public var dwLowDateTime: UInt,
    public var dwHighDateTime: UInt,
) {
    public open fun clone(): Filetime = Filetime(dwLowDateTime, dwHighDateTime)

    public companion object {
        public fun typeLayout(): TypeLayout<Filetime> =
            object : TypeLayout<Filetime> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Filetime =
                    Filetime(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Filetime) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.dwLowDateTime)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.dwHighDateTime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Filetime =
                    Filetime(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Filetime) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.dwLowDateTime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.dwHighDateTime)
                }
            }
    }
}

public val FILE_ADD_FILE: Int = 2

public val FILE_ADD_SUBDIRECTORY: Int = 4

public open class FileAllocationInfo(
    public var allocationSize: Long,
) {
    public open fun clone(): FileAllocationInfo = FileAllocationInfo(allocationSize)

    public companion object {
        public fun typeLayout(): TypeLayout<FileAllocationInfo> =
            object : TypeLayout<FileAllocationInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FileAllocationInfo =
                    FileAllocationInfo(
                        (PrimitiveLayouts.I64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileAllocationInfo) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.allocationSize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileAllocationInfo =
                    FileAllocationInfo(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileAllocationInfo) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.allocationSize)
                }
            }
    }
}

public val FILE_ALL_ACCESS: Int = 2032127

public val FILE_APPEND_DATA: Int = 4

public val FILE_ATTRIBUTE_ARCHIVE: Int = 32

public val FILE_ATTRIBUTE_COMPRESSED: Int = 2048

public val FILE_ATTRIBUTE_DEVICE: Int = 64

public val FILE_ATTRIBUTE_DIRECTORY: Int = 16

public val FILE_ATTRIBUTE_EA: Int = 262144

public val FILE_ATTRIBUTE_ENCRYPTED: Int = 16384

public val FILE_ATTRIBUTE_HIDDEN: Int = 2

public val FILE_ATTRIBUTE_INTEGRITY_STREAM: Int = 32768

public val FILE_ATTRIBUTE_NORMAL: Int = 128

public val FILE_ATTRIBUTE_NOT_CONTENT_INDEXED: Int = 8192

public val FILE_ATTRIBUTE_NO_SCRUB_DATA: Int = 131072

public val FILE_ATTRIBUTE_OFFLINE: Int = 4096

public val FILE_ATTRIBUTE_PINNED: Int = 524288

public val FILE_ATTRIBUTE_READONLY: Int = 1

public val FILE_ATTRIBUTE_RECALL_ON_DATA_ACCESS: Int = 4194304

public val FILE_ATTRIBUTE_RECALL_ON_OPEN: Int = 262144

public val FILE_ATTRIBUTE_REPARSE_POINT: Int = 1024

public val FILE_ATTRIBUTE_SPARSE_FILE: Int = 512

public val FILE_ATTRIBUTE_SYSTEM: Int = 4

public open class FileAttributeTagInfo(
    public var fileAttributes: UInt,
    public var reparseTag: UInt,
) {
    public open fun clone(): FileAttributeTagInfo = FileAttributeTagInfo(fileAttributes, reparseTag)

    public companion object {
        public fun typeLayout(): TypeLayout<FileAttributeTagInfo> =
            object : TypeLayout<FileAttributeTagInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FileAttributeTagInfo =
                    FileAttributeTagInfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: FileAttributeTagInfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.fileAttributes)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.reparseTag)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileAttributeTagInfo =
                    FileAttributeTagInfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileAttributeTagInfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.fileAttributes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.reparseTag)
                }
            }
    }
}

public val FILE_ATTRIBUTE_TEMPORARY: Int = 256

public val FILE_ATTRIBUTE_UNPINNED: Int = 1048576

public val FILE_ATTRIBUTE_VIRTUAL: Int = 65536

public open class FileBasicInfo(
    public var creationTime: Long,
    public var lastAccessTime: Long,
    public var lastWriteTime: Long,
    public var changeTime: Long,
    public var fileAttributes: UInt,
) {
    public open fun clone(): FileBasicInfo = FileBasicInfo(creationTime, lastAccessTime, lastWriteTime, changeTime, fileAttributes)

    public companion object {
        public fun typeLayout(): TypeLayout<FileBasicInfo> =
            object : TypeLayout<FileBasicInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): FileBasicInfo =
                    FileBasicInfo(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: FileBasicInfo) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.creationTime)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.lastAccessTime)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.lastWriteTime)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.changeTime)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.fileAttributes)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileBasicInfo =
                    FileBasicInfo(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileBasicInfo) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.creationTime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.lastAccessTime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.lastWriteTime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.changeTime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.fileAttributes)
                }
            }
    }
}

public val FILE_BEGIN: Int = 0

public val FILE_COMPLETE_IF_OPLOCKED: Int = 256

public val FILE_CONTAINS_EXTENDED_CREATE_INFORMATION: Int = 268435456

public val FILE_CREATE: Int = 2

public val FILE_CREATE_PIPE_INSTANCE: Int = 4

public val FILE_CREATE_TREE_CONNECTION: Int = 128

public val FILE_CURRENT: Int = 1

public val FILE_DELETE_CHILD: Int = 64

public val FILE_DELETE_ON_CLOSE: Int = 4096

public val FILE_DIRECTORY_FILE: Int = 1

public val FILE_DISALLOW_EXCLUSIVE: Int = 131072

public val FILE_DISPOSITION_FLAG_DELETE: Int = 1

public val FILE_DISPOSITION_FLAG_DO_NOT_DELETE: Int = 0

public val FILE_DISPOSITION_FLAG_FORCE_IMAGE_SECTION_CHECK: Int = 4

public val FILE_DISPOSITION_FLAG_IGNORE_READONLY_ATTRIBUTE: Int = 16

public val FILE_DISPOSITION_FLAG_ON_CLOSE: Int = 8

public val FILE_DISPOSITION_FLAG_POSIX_SEMANTICS: Int = 2

public open class FileDispositionInfo(
    public var deleteFile: Boolean,
) {
    public open fun clone(): FileDispositionInfo = FileDispositionInfo(deleteFile)

    public companion object {
        public fun typeLayout(): TypeLayout<FileDispositionInfo> =
            object : TypeLayout<FileDispositionInfo> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): FileDispositionInfo =
                    FileDispositionInfo(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileDispositionInfo) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.deleteFile)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileDispositionInfo =
                    FileDispositionInfo(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileDispositionInfo) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.deleteFile)
                }
            }
    }
}

public open class FileDispositionInfoEx(
    public var flags: UInt,
) {
    public open fun clone(): FileDispositionInfoEx = FileDispositionInfoEx(flags)

    public companion object {
        public fun typeLayout(): TypeLayout<FileDispositionInfoEx> =
            object : TypeLayout<FileDispositionInfoEx> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FileDispositionInfoEx =
                    FileDispositionInfoEx(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileDispositionInfoEx) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileDispositionInfoEx =
                    FileDispositionInfoEx(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileDispositionInfoEx) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.flags)
                }
            }
    }
}

public val FILE_END: Int = 2

public open class FileEndOfFileInfo(
    public var endOfFile: Long,
) {
    public open fun clone(): FileEndOfFileInfo = FileEndOfFileInfo(endOfFile)

    public companion object {
        public fun typeLayout(): TypeLayout<FileEndOfFileInfo> =
            object : TypeLayout<FileEndOfFileInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FileEndOfFileInfo =
                    FileEndOfFileInfo(
                        (PrimitiveLayouts.I64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileEndOfFileInfo) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.endOfFile)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileEndOfFileInfo =
                    FileEndOfFileInfo(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileEndOfFileInfo) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.endOfFile)
                }
            }
    }
}

public val FILE_EXECUTE: Int = 32

public val FILE_FLAG_BACKUP_SEMANTICS: Int = 33554432

public val FILE_FLAG_DELETE_ON_CLOSE: Int = 67108864

public val FILE_FLAG_FIRST_PIPE_INSTANCE: Int = 524288

public val FILE_FLAG_NO_BUFFERING: Int = 536870912

public val FILE_FLAG_OPEN_NO_RECALL: Int = 1048576

public val FILE_FLAG_OPEN_REPARSE_POINT: Int = 2097152

public val FILE_FLAG_OVERLAPPED: Int = 1073741824

public val FILE_FLAG_POSIX_SEMANTICS: Int = 16777216

public val FILE_FLAG_RANDOM_ACCESS: Int = 268435456

public val FILE_FLAG_SEQUENTIAL_SCAN: Int = 134217728

public val FILE_FLAG_SESSION_AWARE: Int = 8388608

public val FILE_FLAG_WRITE_THROUGH: UInt = 2147483648u

public open class FileFsDeviceInformation(
    public var deviceType: UInt,
    public var characteristics: UInt,
) {
    public open fun clone(): FileFsDeviceInformation = FileFsDeviceInformation(deviceType, characteristics)

    public companion object {
        public fun typeLayout(): TypeLayout<FileFsDeviceInformation> =
            object : TypeLayout<FileFsDeviceInformation> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FileFsDeviceInformation =
                    FileFsDeviceInformation(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: FileFsDeviceInformation) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.deviceType)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.characteristics)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileFsDeviceInformation =
                    FileFsDeviceInformation(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileFsDeviceInformation) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.deviceType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.characteristics)
                }
            }
    }
}

public val FILE_GENERIC_EXECUTE: Int = 1179808

public val FILE_GENERIC_READ: Int = 1179785

public val FILE_GENERIC_WRITE: Int = 1179926

public open class FileIdBothDirInfo(
    public var nextEntryOffset: UInt,
    public var fileIndex: UInt,
    public var creationTime: Long,
    public var lastAccessTime: Long,
    public var lastWriteTime: Long,
    public var changeTime: Long,
    public var endOfFile: Long,
    public var allocationSize: Long,
    public var fileAttributes: UInt,
    public var fileNameLength: UInt,
    public var eaSize: UInt,
    public var shortNameLength: Cchar,
    public var shortName: UShortArray,
    public var fileId: Long,
    public var fileName: UShortArray,
) {
    public open fun clone(): FileIdBothDirInfo = FileIdBothDirInfo(nextEntryOffset, fileIndex, creationTime, lastAccessTime, lastWriteTime, changeTime, endOfFile, allocationSize, fileAttributes, fileNameLength, eaSize, shortNameLength, shortName, fileId, fileName)

    public companion object {
        public fun typeLayout(): TypeLayout<FileIdBothDirInfo> =
            object : TypeLayout<FileIdBothDirInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): FileIdBothDirInfo =
                    FileIdBothDirInfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.U32).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 60L),
                        (PrimitiveLayouts.U32).readNative(addr + 64L),
                        (PrimitiveLayouts.I8).readNative(addr + 68L),
                        (PrimitiveArrayLayouts.uShortArray(12)).readNative(addr + 70L),
                        (PrimitiveLayouts.I64).readNative(addr + 96L),
                        (PrimitiveArrayLayouts.uShortArray(1)).readNative(addr + 104L),
                    )
                override fun writeNative(addr: Long, value: FileIdBothDirInfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nextEntryOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.fileIndex)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.creationTime)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.lastAccessTime)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.lastWriteTime)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.changeTime)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.endOfFile)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.allocationSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.fileAttributes)
                    (PrimitiveLayouts.U32).writeNative(addr + 60L, value.fileNameLength)
                    (PrimitiveLayouts.U32).writeNative(addr + 64L, value.eaSize)
                    (PrimitiveLayouts.I8).writeNative(addr + 68L, value.shortNameLength)
                    (PrimitiveArrayLayouts.uShortArray(12)).writeNative(addr + 70L, value.shortName)
                    (PrimitiveLayouts.I64).writeNative(addr + 96L, value.fileId)
                    (PrimitiveArrayLayouts.uShortArray(1)).writeNative(addr + 104L, value.fileName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileIdBothDirInfo =
                    FileIdBothDirInfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I8).readBytes(buf, offset + 68),
                        (PrimitiveArrayLayouts.uShortArray(12)).readBytes(buf, offset + 70),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 96),
                        (PrimitiveArrayLayouts.uShortArray(1)).readBytes(buf, offset + 104),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileIdBothDirInfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nextEntryOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.fileIndex)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.creationTime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.lastAccessTime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.lastWriteTime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.changeTime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.endOfFile)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.allocationSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.fileAttributes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 60, value.fileNameLength)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 64, value.eaSize)
                    (PrimitiveLayouts.I8).writeBytes(buf, offset + 68, value.shortNameLength)
                    (PrimitiveArrayLayouts.uShortArray(12)).writeBytes(buf, offset + 70, value.shortName)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 96, value.fileId)
                    (PrimitiveArrayLayouts.uShortArray(1)).writeBytes(buf, offset + 104, value.fileName)
                }
            }

        public fun default(): FileIdBothDirInfo = null
    }
}

public typealias FileInformationClass = Int

public typealias FileInfoByHandleClass = Int

public open class FileIoPriorityHintInfo(
    public var priorityHint: PriorityHint,
) {
    public open fun clone(): FileIoPriorityHintInfo = FileIoPriorityHintInfo(priorityHint)

    public companion object {
        public fun typeLayout(): TypeLayout<FileIoPriorityHintInfo> =
            object : TypeLayout<FileIoPriorityHintInfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FileIoPriorityHintInfo =
                    FileIoPriorityHintInfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileIoPriorityHintInfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.priorityHint)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileIoPriorityHintInfo =
                    FileIoPriorityHintInfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileIoPriorityHintInfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.priorityHint)
                }
            }
    }
}

public val FILE_LIST_DIRECTORY: Int = 1

public val FILE_NAME_NORMALIZED: Int = 0

public val FILE_NAME_OPENED: Int = 8

public val FILE_NON_DIRECTORY_FILE: Int = 64

public val FILE_NO_COMPRESSION: Int = 32768

public val FILE_NO_EA_KNOWLEDGE: Int = 512

public val FILE_NO_INTERMEDIATE_BUFFERING: Int = 8

public val FILE_OPEN: Int = 1

public val FILE_OPEN_BY_FILE_ID: Int = 8192

public val FILE_OPEN_FOR_BACKUP_INTENT: Int = 16384

public val FILE_OPEN_FOR_FREE_SPACE_QUERY: Int = 8388608

public val FILE_OPEN_IF: Int = 3

public val FILE_OPEN_NO_RECALL: Int = 4194304

public val FILE_OPEN_REPARSE_POINT: Int = 2097152

public val FILE_OPEN_REQUIRING_OPLOCK: Int = 65536

public val FILE_OVERWRITE: Int = 4

public val FILE_OVERWRITE_IF: Int = 5

public val FILE_PIPE_ACCEPT_REMOTE_CLIENTS: Int = 0

public val FILE_PIPE_BYTE_STREAM_MODE: Int = 0

public val FILE_PIPE_BYTE_STREAM_TYPE: Int = 0

public val FILE_PIPE_COMPLETE_OPERATION: Int = 1

public val FILE_PIPE_MESSAGE_MODE: Int = 1

public val FILE_PIPE_MESSAGE_TYPE: Int = 1

public val FILE_PIPE_QUEUE_OPERATION: Int = 0

public val FILE_PIPE_REJECT_REMOTE_CLIENTS: Int = 2

public val FILE_RANDOM_ACCESS: Int = 2048

public val FILE_READ_ATTRIBUTES: Int = 128

public val FILE_READ_DATA: Int = 1

public val FILE_READ_EA: Int = 8

public val FILE_RENAME_FLAG_POSIX_SEMANTICS: Int = 2

public val FILE_RENAME_FLAG_REPLACE_IF_EXISTS: Int = 1

public open class FileRenameInfo internal constructor(
    public var anonymous: FileRenameInfo0,
    rootDirectoryCtorArg: Handle,
    public var fileNameLength: UInt,
    public var fileName: UShortArray,
) {
    public var rootDirectory: Handle = rootDirectoryCtorArg
        internal set

    public open fun clone(): FileRenameInfo = FileRenameInfo(anonymous, rootDirectory, fileNameLength, fileName)

    public companion object {
        public operator fun invoke(
            anonymous: FileRenameInfo0,
            rootDirectory: Handle,
            fileNameLength: UInt,
            fileName: UShortArray,
        ): FileRenameInfo = FileRenameInfo(
            anonymous = anonymous,
            rootDirectoryCtorArg = rootDirectory,
            fileNameLength = fileNameLength,
            fileName = fileName,
        )

        public fun default(): FileRenameInfo = null
    }
}

public open class FileRenameInfo0(
    public var replaceIfExists: Boolean? = null,
    public var flags: UInt? = null,
) {
    public companion object {
        public fun default(): FileRenameInfo0 = null
    }
}

public open class FileRenameInformation internal constructor(
    public var anonymous: FileRenameInformation0,
    rootDirectoryCtorArg: Handle,
    public var fileNameLength: UInt,
    public var fileName: UShortArray,
) {
    public var rootDirectory: Handle = rootDirectoryCtorArg
        internal set

    public open fun clone(): FileRenameInformation = FileRenameInformation(anonymous, rootDirectory, fileNameLength, fileName)

    public companion object {
        public operator fun invoke(
            anonymous: FileRenameInformation0,
            rootDirectory: Handle,
            fileNameLength: UInt,
            fileName: UShortArray,
        ): FileRenameInformation = FileRenameInformation(
            anonymous = anonymous,
            rootDirectoryCtorArg = rootDirectory,
            fileNameLength = fileNameLength,
            fileName = fileName,
        )

        public fun default(): FileRenameInformation = null
    }
}

public open class FileRenameInformation0(
    public var replaceIfExists: Boolean? = null,
    public var flags: UInt? = null,
) {
    public companion object {
        public fun default(): FileRenameInformation0 = null
    }
}

public val FILE_RESERVE_OPFILTER: Int = 1048576

public val FILE_SEQUENTIAL_ONLY: Int = 4

public val FILE_SESSION_AWARE: Int = 262144

public val FILE_SHARE_DELETE: Int = 4

public val FILE_SHARE_READ: Int = 1

public val FILE_SHARE_WRITE: Int = 2

public open class FileStandardInfo(
    public var allocationSize: Long,
    public var endOfFile: Long,
    public var numberOfLinks: UInt,
    public var deletePending: Boolean,
    public var directory: Boolean,
) {
    public open fun clone(): FileStandardInfo = FileStandardInfo(allocationSize, endOfFile, numberOfLinks, deletePending, directory)

    public companion object {
        public fun typeLayout(): TypeLayout<FileStandardInfo> =
            object : TypeLayout<FileStandardInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): FileStandardInfo =
                    FileStandardInfo(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 20L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 21L),
                    )
                override fun writeNative(addr: Long, value: FileStandardInfo) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.allocationSize)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.endOfFile)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.numberOfLinks)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 20L, value.deletePending)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 21L, value.directory)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileStandardInfo =
                    FileStandardInfo(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 21),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileStandardInfo) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.allocationSize)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.endOfFile)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.numberOfLinks)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 20, value.deletePending)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 21, value.directory)
                }
            }
    }
}

public val FILE_SUPERSEDE: Int = 0

public val FILE_SYNCHRONOUS_IO_ALERT: Int = 16

public val FILE_SYNCHRONOUS_IO_NONALERT: Int = 32

public val FILE_TRAVERSE: Int = 32

public val FILE_TYPE_CHAR: Int = 2

public val FILE_TYPE_DISK: Int = 1

public val FILE_TYPE_REMOTE: Int = 32768

public val FILE_TYPE_UNKNOWN: Int = 0

public val FILE_WRITE_ATTRIBUTES: Int = 256

public val FILE_WRITE_DATA: Int = 2

public val FILE_WRITE_EA: Int = 16

public val FILE_WRITE_THROUGH: Int = 2

public typealias FindexInfoLevels = Int

public typealias FindexSearchOps = Int

public val FIONBIO: UInt = 2147772030u

public val FLS_OUT_OF_INDEXES: UInt = 4294967295u

public val FORMAT_MESSAGE_ALLOCATE_BUFFER: Int = 256

public val FORMAT_MESSAGE_ARGUMENT_ARRAY: Int = 8192

public val FORMAT_MESSAGE_FROM_HMODULE: Int = 2048

public val FORMAT_MESSAGE_FROM_STRING: Int = 1024

public val FORMAT_MESSAGE_FROM_SYSTEM: Int = 4096

public val FORMAT_MESSAGE_IGNORE_INSERTS: Int = 512

public val FRS_ERR_SYSVOL_POPULATE_TIMEOUT: Int = 8014

public val FSCTL_GET_REPARSE_POINT: Int = 589992

public val FSCTL_SET_REPARSE_POINT: Int = 589988

public typealias FsInformationClass = Int

public val FILE_ALIGNMENT_INFO: FileInfoByHandleClass = 17

public val FILE_ALLOCATION_INFO: FileInfoByHandleClass = 5

public val FILE_ATTRIBUTE_TAG_INFO: FileInfoByHandleClass = 9

public val FILE_BASIC_INFO: FileInfoByHandleClass = 0

public val FILE_CASE_SENSITIVE_INFO: FileInfoByHandleClass = 23

public val FILE_COMPRESSION_INFO: FileInfoByHandleClass = 8

public val FILE_DISPOSITION_INFO: FileInfoByHandleClass = 4

public val FILE_DISPOSITION_INFO_EX: FileInfoByHandleClass = 21

public val FILE_END_OF_FILE_INFO: FileInfoByHandleClass = 6

public val FILE_FS_DEVICE_INFORMATION: FsInformationClass = 4

public val FILE_FULL_DIRECTORY_INFO: FileInfoByHandleClass = 14

public val FILE_FULL_DIRECTORY_RESTART_INFO: FileInfoByHandleClass = 15

public val FILE_ID_BOTH_DIRECTORY_INFO: FileInfoByHandleClass = 10

public val FILE_ID_BOTH_DIRECTORY_RESTART_INFO: FileInfoByHandleClass = 11

public val FILE_ID_EXTD_DIRECTORY_INFO: FileInfoByHandleClass = 19

public val FILE_ID_EXTD_DIRECTORY_RESTART_INFO: FileInfoByHandleClass = 20

public val FILE_ID_INFO: FileInfoByHandleClass = 18

public val FILE_IO_PRIORITY_HINT_INFO: FileInfoByHandleClass = 12

public val FILE_NAME_INFO: FileInfoByHandleClass = 2

public val FILE_NORMALIZED_NAME_INFO: FileInfoByHandleClass = 24

public val FILE_REMOTE_PROTOCOL_INFO: FileInfoByHandleClass = 13

public val FILE_RENAME_INFO: FileInfoByHandleClass = 3

public val FILE_RENAME_INFO_EX: FileInfoByHandleClass = 22

public val FILE_RENAME_INFORMATION: FileInformationClass = 10

public val FILE_RENAME_INFORMATION_EX: FileInformationClass = 65

public val FILE_STANDARD_INFO: FileInfoByHandleClass = 1

public val FILE_STORAGE_INFO: FileInfoByHandleClass = 16

public val FILE_STREAM_INFO: FileInfoByHandleClass = 7

public val FIND_EX_INFO_BASIC: FindexInfoLevels = 1

public val FIND_EX_SEARCH_NAME_MATCH: FindexSearchOps = 0

public val GENERIC_ALL: Int = 268435456

public val GENERIC_EXECUTE: Int = 536870912

public val GENERIC_READ: UInt = 2147483648u

public val GENERIC_WRITE: Int = 1073741824

public val GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS: Int = 4

public val GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT: Int = 2

public typealias Group = UInt

public open class Guid(
    public var data1: UInt,
    public var data2: UShort,
    public var data3: UShort,
    public var data4: UByteArray,
) {
    public open fun clone(): Guid = Guid(data1, data2, data3, data4)

    public companion object {
        public fun typeLayout(): TypeLayout<Guid> =
            object : TypeLayout<Guid> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Guid =
                    Guid(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Guid) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.data1)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.data2)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.data3)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 8L, value.data4)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Guid =
                    Guid(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Guid) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.data1)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.data2)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.data3)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 8, value.data4)
                }
            }
    }
}

public typealias Handle = MutPtr

public val HANDLE_FLAG_INHERIT: Int = 1

public val HANDLE_FLAG_PROTECT_FROM_CLOSE: Int = 2

public val HIGH_PRIORITY_CLASS: Int = 128

public typealias Hinstance = MutPtr

public typealias Hlocal = Handle

public typealias Hmodule = Hinstance

public typealias Hresult = Int

public val IDLE_PRIORITY_CLASS: Int = 64

public open class In6Addr(
    public var u: In6Addr0,
) {
    public open fun clone(): In6Addr = In6Addr(u)

    public companion object {
        public fun default(): In6Addr = null
    }
}

public open class In6Addr0(
    public var byte: UByteArray? = null,
    public var word: UShortArray? = null,
) {
    public companion object {
        public fun default(): In6Addr0 = null
    }
}

public val INFINITE: UInt = 4294967295u

public val INHERIT_CALLER_PRIORITY: Int = 131072

public val INHERIT_PARENT_AFFINITY: Int = 65536

public typealias InitOnce = RtlRunOnce

public val INIT_ONCE_INIT_FAILED: UInt = 4u

public val INVALID_FILE_ATTRIBUTES: UInt = 4294967295u

public val INVALID_SOCKET: Socket = (18446744073709551615uL.toInt()).toULong()

public open class InAddr(
    public var sUn: InAddr0,
) {
    public open fun clone(): InAddr = InAddr(sUn)

    public companion object {
        public fun default(): InAddr = null
    }
}

public open class InAddr0(
    public var sUnB: InAddr00? = null,
    public var sUnW: InAddr01? = null,
    public var sAddr: UInt? = null,
) {
    public companion object {
        public fun default(): InAddr0 = null
    }
}

public open class InAddr00(
    public var sB1: UByte,
    public var sB2: UByte,
    public var sB3: UByte,
    public var sB4: UByte,
) {
    public open fun clone(): InAddr00 = InAddr00(sB1, sB2, sB3, sB4)

    public companion object {
        public fun typeLayout(): TypeLayout<InAddr00> =
            object : TypeLayout<InAddr00> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): InAddr00 =
                    InAddr00(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                    )
                override fun writeNative(addr: Long, value: InAddr00) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.sB1)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.sB2)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.sB3)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.sB4)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InAddr00 =
                    InAddr00(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InAddr00) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.sB1)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.sB2)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.sB3)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.sB4)
                }
            }
    }
}

public open class InAddr01(
    public var sW1: UShort,
    public var sW2: UShort,
) {
    public open fun clone(): InAddr01 = InAddr01(sW1, sW2)

    public companion object {
        public fun typeLayout(): TypeLayout<InAddr01> =
            object : TypeLayout<InAddr01> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): InAddr01 =
                    InAddr01(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: InAddr01) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sW1)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sW2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InAddr01 =
                    InAddr01(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InAddr01) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sW1)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sW2)
                }
            }
    }
}

public val IO_REPARSE_TAG_MOUNT_POINT: UInt = 2684354563u

public val IO_REPARSE_TAG_SYMLINK: UInt = 2684354572u

public open class IoStatusBlock(
    public var anonymous: IoStatusBlock0,
    public var information: Int,
) {
    public open fun clone(): IoStatusBlock = IoStatusBlock(anonymous, information)

    public companion object {
        public fun default(): IoStatusBlock = null
    }
}

public class IoStatusBlock0 internal constructor(
    public var status: Ntstatus?,
    pointerCtorArg: MutPtr?,
) {
    public var pointer: MutPtr? = pointerCtorArg
        internal set

    public constructor() : this(
        status = null,
        pointerCtorArg = null,
    )

    public companion object {
        public operator fun invoke(
            status: Ntstatus? = null,
            pointer: MutPtr? = null,
        ): IoStatusBlock0 = IoStatusBlock0(
            status = status,
            pointerCtorArg = pointer,
        )

        public fun default(): IoStatusBlock0 = null
    }
}

public typealias Ipproto = Int

public val IPPROTO_AH: Ipproto = 51

public val IPPROTO_CBT: Ipproto = 7

public val IPPROTO_DSTOPTS: Ipproto = 60

public val IPPROTO_EGP: Ipproto = 8

public val IPPROTO_ESP: Ipproto = 50

public val IPPROTO_FRAGMENT: Ipproto = 44

public val IPPROTO_GGP: Ipproto = 3

public val IPPROTO_HOPOPTS: Ipproto = 0

public val IPPROTO_ICLFXBM: Ipproto = 78

public val IPPROTO_ICMP: Ipproto = 1

public val IPPROTO_ICMPV6: Ipproto = 58

public val IPPROTO_IDP: Ipproto = 22

public val IPPROTO_IGMP: Ipproto = 2

public val IPPROTO_IGP: Ipproto = 9

public val IPPROTO_IP: Int = 0

public val IPPROTO_IPV4: Ipproto = 4

public val IPPROTO_IPV6: Ipproto = 41

public val IPPROTO_L2TP: Ipproto = 115

public val IPPROTO_MAX: Ipproto = 256

public val IPPROTO_ND: Ipproto = 77

public val IPPROTO_NONE: Ipproto = 59

public val IPPROTO_PGM: Ipproto = 113

public val IPPROTO_PIM: Ipproto = 103

public val IPPROTO_PUP: Ipproto = 12

public val IPPROTO_RAW: Ipproto = 255

public val IPPROTO_RDP: Ipproto = 27

public val IPPROTO_RESERVED_IPSEC: Ipproto = 258

public val IPPROTO_RESERVED_IPSECOFFLOAD: Ipproto = 259

public val IPPROTO_RESERVED_MAX: Ipproto = 261

public val IPPROTO_RESERVED_RAW: Ipproto = 257

public val IPPROTO_RESERVED_WNV: Ipproto = 260

public val IPPROTO_RM: Int = 113

public val IPPROTO_ROUTING: Ipproto = 43

public val IPPROTO_SCTP: Ipproto = 132

public val IPPROTO_ST: Ipproto = 5

public val IPPROTO_TCP: Ipproto = 6

public val IPPROTO_UDP: Ipproto = 17

public val IPV6_ADD_MEMBERSHIP: Int = 12

public val IPV6_DROP_MEMBERSHIP: Int = 13

public open class Ipv6Mreq(
    public var ipv6mrMultiaddr: In6Addr,
    public var ipv6mrInterface: UInt,
) {
    public open fun clone(): Ipv6Mreq = Ipv6Mreq(ipv6mrMultiaddr, ipv6mrInterface)

    public companion object {
        public fun default(): Ipv6Mreq = null
    }
}

public val IPV6_MULTICAST_HOPS: Int = 10

public val IPV6_MULTICAST_LOOP: Int = 11

public val IPV6_UNICAST_HOPS: Int = 4

public val IPV6_V6ONLY: Int = 27

public val IP_ADD_MEMBERSHIP: Int = 12

public val IP_DROP_MEMBERSHIP: Int = 13

public open class IpMreq(
    public var imrMultiaddr: InAddr,
    public var imrInterface: InAddr,
) {
    public open fun clone(): IpMreq = IpMreq(imrMultiaddr, imrInterface)

    public companion object {
        public fun default(): IpMreq = null
    }
}

public val IP_MULTICAST_LOOP: Int = 11

public val IP_MULTICAST_TTL: Int = 10

public val IP_TTL: Int = 4

public val KF_FLAG_DONT_VERIFY: KnownFolderFlag = 16384u

public typealias Knownfolderid = Guid

public typealias KnownFolderFlag = UInt

public val LOAD_LIBRARY_SEARCH_SYSTEM32: Int = 2048

public val LOCKFILE_EXCLUSIVE_LOCK: Int = 2

public val LOCKFILE_FAIL_IMMEDIATELY: Int = 1

public typealias Lpbyte = MutPtr

public typealias LpinitOnce = PrtlRunOnce

public fun interface LpoverlappedCompletionRoutineFn {
    public operator fun invoke(dwerrorcode: UInt, dwnumberofbytestransfered: UInt, lpoverlapped: MutPtr)
}

public typealias LpoverlappedCompletionRoutine = LpoverlappedCompletionRoutineFn?

public typealias LpprocThreadAttributeList = MutPtr

public fun interface LpprogressRoutineFn {
    public operator fun invoke(totalfilesize: Long, totalbytestransferred: Long, streamsize: Long, streambytestransferred: Long, dwstreamnumber: UInt, dwcallbackreason: UInt, hsourcefile: Handle, hdestinationfile: Handle, lpdata: ConstPtr): UInt
}

public typealias LpprogressRoutine = LpprogressRoutineFn?

public typealias LpthreadStartRoutine = PthreadStartRoutine

public typealias Lpwch = MutPtr

public fun interface LpwsaoverlappedCompletionRoutineFn {
    public operator fun invoke(dwerror: UInt, cbtransferred: UInt, lpoverlapped: MutPtr, dwflags: UInt)
}

public typealias LpwsaoverlappedCompletionRoutine = LpwsaoverlappedCompletionRoutineFn?

public open class M128a(
    public var low: ULong,
    public var high: Long,
) {
    public open fun clone(): M128a = M128a(low, high)

    public companion object {
        public fun typeLayout(): TypeLayout<M128a> =
            object : TypeLayout<M128a> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): M128a =
                    M128a(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: M128a) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.low)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.high)
                }
                override fun readBytes(buf: ByteArray, offset: Int): M128a =
                    M128a(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: M128a) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.low)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.high)
                }
            }
    }
}

public val MAXIMUM_REPARSE_DATA_BUFFER_SIZE: Int = 16384

public val MAX_PATH: Int = 260

public val MB_COMPOSITE: Int = 2

public val MB_PRECOMPOSED: Int = 1

public val MB_USEGLYPHCHARS: Int = 4

public val MOVEFILE_COPY_ALLOWED: Int = 2

public val MOVEFILE_CREATE_HARDLINK: Int = 16

public val MOVEFILE_DELAY_UNTIL_REBOOT: Int = 4

public val MOVEFILE_FAIL_IF_NOT_TRACKABLE: Int = 32

public val MOVEFILE_REPLACE_EXISTING: Int = 1

public val MOVEFILE_WRITE_THROUGH: Int = 8

public val MSG_DONTROUTE: Int = 4

public val MSG_OOB: Int = 1

public val MSG_PEEK: Int = 2

public val MSG_PUSH_IMMEDIATE: Int = 32

public val MSG_WAITALL: Int = 8

public val MAXIMUM_FILE_INFO_BY_HANDLE_CLASS: FileInfoByHandleClass = 25

public typealias Neon128 = Arm64NtNeon128

public val NORMAL_PRIORITY_CLASS: Int = 32

public val NO_ERROR: Int = 0

public typealias Ntstatus = Int

public open class ObjectAttributes internal constructor(
    public var length: UInt,
    rootDirectoryCtorArg: Handle,
    objectNameCtorArg: PunicodeString,
    public var attributes: UInt,
    securityDescriptorCtorArg: MutPtr,
    securityQualityOfServiceCtorArg: MutPtr,
) {
    public var rootDirectory: Handle = rootDirectoryCtorArg
        internal set

    public var objectName: PunicodeString = objectNameCtorArg
        internal set

    public var securityDescriptor: MutPtr = securityDescriptorCtorArg
        internal set

    public var securityQualityOfService: MutPtr = securityQualityOfServiceCtorArg
        internal set

    public open fun clone(): ObjectAttributes = ObjectAttributes(length, rootDirectory, objectName, attributes, securityDescriptor, securityQualityOfService)

    public companion object {
        public fun typeLayout(): TypeLayout<ObjectAttributes> =
            object : TypeLayout<ObjectAttributes> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): ObjectAttributes =
                    ObjectAttributes(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: ObjectAttributes) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.length)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.rootDirectory)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.objectName)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.attributes)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.securityDescriptor)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.securityQualityOfService)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ObjectAttributes =
                    ObjectAttributes(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ObjectAttributes) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.length)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.rootDirectory)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.objectName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.attributes)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.securityDescriptor)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.securityQualityOfService)
                }
            }

        public operator fun invoke(
            length: UInt,
            rootDirectory: Handle,
            objectName: PunicodeString,
            attributes: UInt,
            securityDescriptor: MutPtr,
            securityQualityOfService: MutPtr,
        ): ObjectAttributes = ObjectAttributes(
            length = length,
            rootDirectoryCtorArg = rootDirectory,
            objectNameCtorArg = objectName,
            attributes = attributes,
            securityDescriptorCtorArg = securityDescriptor,
            securityQualityOfServiceCtorArg = securityQualityOfService,
        )
    }
}

public val OBJ_CASE_INSENSITIVE: Int = 64

public val OBJ_DONT_REPARSE: Int = 4096

public val OBJ_EXCLUSIVE: Int = 32

public val OBJ_FORCE_ACCESS_CHECK: Int = 1024

public val OBJ_IGNORE_IMPERSONATED_DEVICEMAP: Int = 2048

public val OBJ_INHERIT: Int = 2

public val OBJ_KERNEL_HANDLE: Int = 512

public val OBJ_OPENIF: Int = 128

public val OBJ_OPENLINK: Int = 256

public val OBJ_PERMANENT: Int = 16

public val OPEN_ALWAYS: Int = 4

public val OPEN_EXISTING: Int = 3

public open class Overlapped internal constructor(
    public var internal: Int,
    public var internalHigh: Int,
    public var anonymous: Overlapped0,
    hEventCtorArg: Handle,
) {
    public var hEvent: Handle = hEventCtorArg
        internal set

    public open fun clone(): Overlapped = Overlapped(internal, internalHigh, anonymous, hEvent)

    public companion object {
        public operator fun invoke(
            internal: Int,
            internalHigh: Int,
            anonymous: Overlapped0,
            hEvent: Handle,
        ): Overlapped = Overlapped(
            internal = internal,
            internalHigh = internalHigh,
            anonymous = anonymous,
            hEventCtorArg = hEvent,
        )

        public fun default(): Overlapped = null
    }
}

public class Overlapped0 internal constructor(
    public var anonymous: Overlapped00?,
    pointerCtorArg: MutPtr?,
) {
    public var pointer: MutPtr? = pointerCtorArg
        internal set

    public constructor() : this(
        anonymous = null,
        pointerCtorArg = null,
    )

    public companion object {
        public operator fun invoke(
            anonymous: Overlapped00? = null,
            pointer: MutPtr? = null,
        ): Overlapped0 = Overlapped0(
            anonymous = anonymous,
            pointerCtorArg = pointer,
        )

        public fun default(): Overlapped0 = null
    }
}

public open class Overlapped00(
    public var offset: UInt,
    public var offsetHigh: UInt,
) {
    public open fun clone(): Overlapped00 = Overlapped00(offset, offsetHigh)

    public companion object {
        public fun typeLayout(): TypeLayout<Overlapped00> =
            object : TypeLayout<Overlapped00> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Overlapped00 =
                    Overlapped00(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Overlapped00) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.offset)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.offsetHigh)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Overlapped00 =
                    Overlapped00(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Overlapped00) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.offset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.offsetHigh)
                }
            }
    }
}

public typealias Paddrinfoa = MutPtr

public typealias Pcontext = MutPtr

public typealias Pcstr = ConstPtr

public typealias Pcwstr = ConstPtr

public typealias PexceptionRecord = MutPtr

public fun interface PflsCallbackFunctionFn {
    public operator fun invoke(lpflsdata: ConstPtr)
}

public typealias PflsCallbackFunction = PflsCallbackFunctionFn?

public fun interface PioApcRoutineFn {
    public operator fun invoke(apccontext: MutPtr, iostatusblock: MutPtr, reserved: UInt)
}

public typealias PioApcRoutine = PioApcRoutineFn?

public val PIPE_ACCEPT_REMOTE_CLIENTS: Int = 0

public val PIPE_ACCESS_DUPLEX: Int = 3

public val PIPE_ACCESS_INBOUND: Int = 1

public val PIPE_ACCESS_OUTBOUND: Int = 2

public val PIPE_CLIENT_END: Int = 0

public val PIPE_NOWAIT: Int = 1

public val PIPE_READMODE_BYTE: Int = 0

public val PIPE_READMODE_MESSAGE: Int = 2

public val PIPE_REJECT_REMOTE_CLIENTS: Int = 8

public val PIPE_SERVER_END: Int = 1

public val PIPE_TYPE_BYTE: Int = 0

public val PIPE_TYPE_MESSAGE: Int = 4

public val PIPE_WAIT: Int = 0

public typealias PriorityHint = Int

public open class ProcessInformation internal constructor(
    hProcessCtorArg: Handle,
    hThreadCtorArg: Handle,
    public var dwProcessId: UInt,
    public var dwThreadId: UInt,
) {
    public var hProcess: Handle = hProcessCtorArg
        internal set

    public var hThread: Handle = hThreadCtorArg
        internal set

    public open fun clone(): ProcessInformation = ProcessInformation(hProcess, hThread, dwProcessId, dwThreadId)

    public companion object {
        public fun typeLayout(): TypeLayout<ProcessInformation> =
            object : TypeLayout<ProcessInformation> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): ProcessInformation =
                    ProcessInformation(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: ProcessInformation) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.hProcess)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.hThread)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.dwProcessId)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.dwThreadId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcessInformation =
                    ProcessInformation(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcessInformation) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.hProcess)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.hThread)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.dwProcessId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.dwThreadId)
                }
            }

        public operator fun invoke(
            hProcess: Handle,
            hThread: Handle,
            dwProcessId: UInt,
            dwThreadId: UInt,
        ): ProcessInformation = ProcessInformation(
            hProcessCtorArg = hProcess,
            hThreadCtorArg = hThread,
            dwProcessId = dwProcessId,
            dwThreadId = dwThreadId,
        )
    }
}

public val PROCESS_MODE_BACKGROUND_BEGIN: Int = 1048576

public val PROCESS_MODE_BACKGROUND_END: Int = 2097152

public val PROFILE_KERNEL: Int = 536870912

public val PROFILE_SERVER: Int = 1073741824

public val PROFILE_USER: Int = 268435456

public val PROGRESS_CONTINUE: Int = 0

public typealias PrtlRunOnce = MutPtr

public typealias Pstr = MutPtr

public fun interface PthreadStartRoutineFn {
    public operator fun invoke(lpthreadparameter: MutPtr): UInt
}

public typealias PthreadStartRoutine = PthreadStartRoutineFn?

public fun interface PtimerapcroutineFn {
    public operator fun invoke(lpargtocompletionroutine: ConstPtr, dwtimerlowvalue: UInt, dwtimerhighvalue: UInt)
}

public typealias Ptimerapcroutine = PtimerapcroutineFn?

public typealias PunicodeString = MutPtr

public fun interface PvectoredExceptionHandlerFn {
    public operator fun invoke(exceptioninfo: MutPtr): Int
}

public typealias PvectoredExceptionHandler = PvectoredExceptionHandlerFn?

public typealias Pwstr = MutPtr

public val READ_CONTROL: Int = 131072

public val REALTIME_PRIORITY_CLASS: Int = 256

public open class RtlConditionVariable internal constructor(
    ptrCtorArg: MutPtr,
) {
    public var ptr: MutPtr = ptrCtorArg
        internal set

    public open fun clone(): RtlConditionVariable = RtlConditionVariable(ptr)

    public companion object {
        public fun typeLayout(): TypeLayout<RtlConditionVariable> =
            object : TypeLayout<RtlConditionVariable> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): RtlConditionVariable =
                    RtlConditionVariable(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: RtlConditionVariable) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.ptr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RtlConditionVariable =
                    RtlConditionVariable(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RtlConditionVariable) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.ptr)
                }
            }

        public operator fun invoke(
            ptr: MutPtr,
        ): RtlConditionVariable = RtlConditionVariable(
            ptrCtorArg = ptr,
        )
    }
}

public class RtlRunOnce internal constructor(
    ptrCtorArg: MutPtr?,
) {
    public var ptr: MutPtr? = ptrCtorArg
        internal set

    public constructor() : this(
        ptrCtorArg = null,
    )

    public companion object {
        public operator fun invoke(
            ptr: MutPtr? = null,
        ): RtlRunOnce = RtlRunOnce(
            ptrCtorArg = ptr,
        )

        public fun default(): RtlRunOnce = null
    }
}

public open class RtlSrwlock internal constructor(
    ptrCtorArg: MutPtr,
) {
    public var ptr: MutPtr = ptrCtorArg
        internal set

    public open fun clone(): RtlSrwlock = RtlSrwlock(ptr)

    public companion object {
        public fun typeLayout(): TypeLayout<RtlSrwlock> =
            object : TypeLayout<RtlSrwlock> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): RtlSrwlock =
                    RtlSrwlock(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: RtlSrwlock) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.ptr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RtlSrwlock =
                    RtlSrwlock(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RtlSrwlock) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.ptr)
                }
            }

        public operator fun invoke(
            ptr: MutPtr,
        ): RtlSrwlock = RtlSrwlock(
            ptrCtorArg = ptr,
        )
    }
}

public val SD_BOTH: Int = 2

public val SD_RECEIVE: Int = 0

public val SD_SEND: Int = 1

public val SECURITY_ANONYMOUS: Int = 0

public open class SecurityAttributes internal constructor(
    public var nLength: UInt,
    lpSecurityDescriptorCtorArg: MutPtr,
    public var bInheritHandle: Bool,
) {
    public var lpSecurityDescriptor: MutPtr = lpSecurityDescriptorCtorArg
        internal set

    public open fun clone(): SecurityAttributes = SecurityAttributes(nLength, lpSecurityDescriptor, bInheritHandle)

    public companion object {
        public fun typeLayout(): TypeLayout<SecurityAttributes> =
            object : TypeLayout<SecurityAttributes> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): SecurityAttributes =
                    SecurityAttributes(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SecurityAttributes) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nLength)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.lpSecurityDescriptor)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.bInheritHandle)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SecurityAttributes =
                    SecurityAttributes(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SecurityAttributes) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nLength)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.lpSecurityDescriptor)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.bInheritHandle)
                }
            }

        public operator fun invoke(
            nLength: UInt,
            lpSecurityDescriptor: MutPtr,
            bInheritHandle: Bool,
        ): SecurityAttributes = SecurityAttributes(
            nLength = nLength,
            lpSecurityDescriptorCtorArg = lpSecurityDescriptor,
            bInheritHandle = bInheritHandle,
        )
    }
}

public val SECURITY_CONTEXT_TRACKING: Int = 262144

public val SECURITY_DELEGATION: Int = 196608

public val SECURITY_EFFECTIVE_ONLY: Int = 524288

public val SECURITY_IDENTIFICATION: Int = 65536

public val SECURITY_IMPERSONATION: Int = 131072

public val SECURITY_SQOS_PRESENT: Int = 1048576

public val SECURITY_VALID_SQOS_FLAGS: Int = 2031616

public open class Sockaddr(
    public var saFamily: AddressFamily,
    public var saData: ByteArray,
) {
    public open fun clone(): Sockaddr = Sockaddr(saFamily, saData)

    public companion object {
        public fun typeLayout(): TypeLayout<Sockaddr> =
            object : TypeLayout<Sockaddr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Sockaddr =
                    Sockaddr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.byteArray(14)).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Sockaddr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.saFamily)
                    (PrimitiveArrayLayouts.byteArray(14)).writeNative(addr + 2L, value.saData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sockaddr =
                    Sockaddr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.byteArray(14)).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sockaddr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.saFamily)
                    (PrimitiveArrayLayouts.byteArray(14)).writeBytes(buf, offset + 2, value.saData)
                }
            }

        public fun default(): Sockaddr = null
    }
}

public typealias SockaddrStorage = SockaddrStorageLh

public open class SockaddrStorageLh(
    public var ssFamily: AddressFamily,
    public var ssPad1: ByteArray,
    public var ssAlign: Long,
    public var ssPad2: ByteArray,
) {
    public open fun clone(): SockaddrStorageLh = SockaddrStorageLh(ssFamily, ssPad1, ssAlign, ssPad2)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrStorageLh> =
            object : TypeLayout<SockaddrStorageLh> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): SockaddrStorageLh =
                    SockaddrStorageLh(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.byteArray(6)).readNative(addr + 2L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.byteArray(112)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SockaddrStorageLh) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.ssFamily)
                    (PrimitiveArrayLayouts.byteArray(6)).writeNative(addr + 2L, value.ssPad1)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.ssAlign)
                    (PrimitiveArrayLayouts.byteArray(112)).writeNative(addr + 16L, value.ssPad2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrStorageLh =
                    SockaddrStorageLh(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.byteArray(6)).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.byteArray(112)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrStorageLh) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.ssFamily)
                    (PrimitiveArrayLayouts.byteArray(6)).writeBytes(buf, offset + 2, value.ssPad1)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.ssAlign)
                    (PrimitiveArrayLayouts.byteArray(112)).writeBytes(buf, offset + 16, value.ssPad2)
                }
            }

        public fun default(): SockaddrStorageLh = null
    }
}

public open class SockaddrUn(
    public var sunFamily: AddressFamily,
    public var sunPath: ByteArray,
) {
    public open fun clone(): SockaddrUn = SockaddrUn(sunFamily, sunPath)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrUn> =
            object : TypeLayout<SockaddrUn> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 110L
                override fun readNative(addr: Long): SockaddrUn =
                    SockaddrUn(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.byteArray(108)).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: SockaddrUn) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sunFamily)
                    (PrimitiveArrayLayouts.byteArray(108)).writeNative(addr + 2L, value.sunPath)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrUn =
                    SockaddrUn(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.byteArray(108)).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrUn) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sunFamily)
                    (PrimitiveArrayLayouts.byteArray(108)).writeBytes(buf, offset + 2, value.sunPath)
                }
            }

        public fun default(): SockaddrUn = null
    }
}

public typealias Socket = ULong

public val SOCKET_ERROR: Int = -1

public val SOCK_DGRAM: Int = 2

public val SOCK_RAW: Int = 3

public val SOCK_RDM: Int = 4

public val SOCK_SEQPACKET: Int = 5

public val SOCK_STREAM: Int = 1

public val SOL_SOCKET: Int = 65535

public val SO_BROADCAST: Int = 32

public val SO_ERROR: Int = 4103

public val SO_KEEPALIVE: Int = 8

public val SO_LINGER: Int = 128

public val SO_RCVTIMEO: Int = 4102

public val SO_SNDTIMEO: Int = 4101

public val SPECIFIC_RIGHTS_ALL: Int = 65535

public typealias Srwlock = RtlSrwlock

public val STACK_SIZE_PARAM_IS_A_RESERVATION: Int = 65536

public val STANDARD_RIGHTS_ALL: Int = 2031616

public val STANDARD_RIGHTS_EXECUTE: Int = 131072

public val STANDARD_RIGHTS_READ: Int = 131072

public val STANDARD_RIGHTS_REQUIRED: Int = 983040

public val STANDARD_RIGHTS_WRITE: Int = 131072

public val STARTF_FORCEOFFFEEDBACK: Int = 128

public val STARTF_FORCEONFEEDBACK: Int = 64

public val STARTF_PREVENTPINNING: Int = 8192

public val STARTF_RUNFULLSCREEN: Int = 32

public val STARTF_TITLEISAPPID: Int = 4096

public val STARTF_TITLEISLINKNAME: Int = 2048

public val STARTF_UNTRUSTEDSOURCE: Int = 32768

public val STARTF_USECOUNTCHARS: Int = 8

public val STARTF_USEFILLATTRIBUTE: Int = 16

public val STARTF_USEHOTKEY: Int = 512

public val STARTF_USEPOSITION: Int = 4

public val STARTF_USESHOWWINDOW: Int = 1

public val STARTF_USESIZE: Int = 2

public val STARTF_USESTDHANDLES: Int = 256

public open class Startupinfoexw internal constructor(
    public var startupInfo: Startupinfow,
    lpAttributeListCtorArg: LpprocThreadAttributeList,
) {
    public var lpAttributeList: LpprocThreadAttributeList = lpAttributeListCtorArg
        internal set

    public open fun clone(): Startupinfoexw = Startupinfoexw(startupInfo, lpAttributeList)

    public companion object {
        public fun typeLayout(): TypeLayout<Startupinfoexw> =
            object : TypeLayout<Startupinfoexw> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Startupinfoexw =
                    Startupinfoexw(
                        (Startupinfow.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 104L),
                    )
                override fun writeNative(addr: Long, value: Startupinfoexw) {
                    (Startupinfow.typeLayout()).writeNative(addr, value.startupInfo)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 104L, value.lpAttributeList)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Startupinfoexw =
                    Startupinfoexw(
                        (Startupinfow.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 104),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Startupinfoexw) {
                    (Startupinfow.typeLayout()).writeBytes(buf, offset, value.startupInfo)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 104, value.lpAttributeList)
                }
            }

        public operator fun invoke(
            startupInfo: Startupinfow,
            lpAttributeList: LpprocThreadAttributeList,
        ): Startupinfoexw = Startupinfoexw(
            startupInfo = startupInfo,
            lpAttributeListCtorArg = lpAttributeList,
        )
    }
}

public open class Startupinfow internal constructor(
    public var cb: UInt,
    lpReservedCtorArg: Pwstr,
    lpDesktopCtorArg: Pwstr,
    lpTitleCtorArg: Pwstr,
    public var dwX: UInt,
    public var dwY: UInt,
    public var dwXSize: UInt,
    public var dwYSize: UInt,
    public var dwXCountChars: UInt,
    public var dwYCountChars: UInt,
    public var dwFillAttribute: UInt,
    public var dwFlags: UInt,
    public var wShowWindow: UShort,
    public var cbReserved2: UShort,
    lpReserved2CtorArg: Lpbyte,
    hStdInputCtorArg: Handle,
    hStdOutputCtorArg: Handle,
    hStdErrorCtorArg: Handle,
) {
    public var lpReserved: Pwstr = lpReservedCtorArg
        internal set

    public var lpDesktop: Pwstr = lpDesktopCtorArg
        internal set

    public var lpTitle: Pwstr = lpTitleCtorArg
        internal set

    public var lpReserved2: Lpbyte = lpReserved2CtorArg
        internal set

    public var hStdInput: Handle = hStdInputCtorArg
        internal set

    public var hStdOutput: Handle = hStdOutputCtorArg
        internal set

    public var hStdError: Handle = hStdErrorCtorArg
        internal set

    public open fun clone(): Startupinfow = Startupinfow(cb, lpReserved, lpDesktop, lpTitle, dwX, dwY, dwXSize, dwYSize, dwXCountChars, dwYCountChars, dwFillAttribute, dwFlags, wShowWindow, cbReserved2, lpReserved2, hStdInput, hStdOutput, hStdError)

    public companion object {
        public fun typeLayout(): TypeLayout<Startupinfow> =
            object : TypeLayout<Startupinfow> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 104L
                override fun readNative(addr: Long): Startupinfow =
                    Startupinfow(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 44L),
                        (PrimitiveLayouts.U32).readNative(addr + 48L),
                        (PrimitiveLayouts.U32).readNative(addr + 52L),
                        (PrimitiveLayouts.U32).readNative(addr + 56L),
                        (PrimitiveLayouts.U32).readNative(addr + 60L),
                        (PrimitiveLayouts.U16).readNative(addr + 64L),
                        (PrimitiveLayouts.U16).readNative(addr + 66L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 80L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 88L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 96L),
                    )
                override fun writeNative(addr: Long, value: Startupinfow) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cb)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.lpReserved)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.lpDesktop)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.lpTitle)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.dwX)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.dwY)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.dwXSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.dwYSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.dwXCountChars)
                    (PrimitiveLayouts.U32).writeNative(addr + 52L, value.dwYCountChars)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.dwFillAttribute)
                    (PrimitiveLayouts.U32).writeNative(addr + 60L, value.dwFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + 64L, value.wShowWindow)
                    (PrimitiveLayouts.U16).writeNative(addr + 66L, value.cbReserved2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.lpReserved2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 80L, value.hStdInput)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 88L, value.hStdOutput)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 96L, value.hStdError)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Startupinfow =
                    Startupinfow(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 66),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 96),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Startupinfow) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cb)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.lpReserved)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.lpDesktop)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.lpTitle)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.dwX)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.dwY)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.dwXSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.dwYSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.dwXCountChars)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 52, value.dwYCountChars)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.dwFillAttribute)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 60, value.dwFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 64, value.wShowWindow)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 66, value.cbReserved2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.lpReserved2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 80, value.hStdInput)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 88, value.hStdOutput)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 96, value.hStdError)
                }
            }

        public operator fun invoke(
            cb: UInt,
            lpReserved: Pwstr,
            lpDesktop: Pwstr,
            lpTitle: Pwstr,
            dwX: UInt,
            dwY: UInt,
            dwXSize: UInt,
            dwYSize: UInt,
            dwXCountChars: UInt,
            dwYCountChars: UInt,
            dwFillAttribute: UInt,
            dwFlags: UInt,
            wShowWindow: UShort,
            cbReserved2: UShort,
            lpReserved2: Lpbyte,
            hStdInput: Handle,
            hStdOutput: Handle,
            hStdError: Handle,
        ): Startupinfow = Startupinfow(
            cb = cb,
            lpReservedCtorArg = lpReserved,
            lpDesktopCtorArg = lpDesktop,
            lpTitleCtorArg = lpTitle,
            dwX = dwX,
            dwY = dwY,
            dwXSize = dwXSize,
            dwYSize = dwYSize,
            dwXCountChars = dwXCountChars,
            dwYCountChars = dwYCountChars,
            dwFillAttribute = dwFillAttribute,
            dwFlags = dwFlags,
            wShowWindow = wShowWindow,
            cbReserved2 = cbReserved2,
            lpReserved2CtorArg = lpReserved2,
            hStdInputCtorArg = hStdInput,
            hStdOutputCtorArg = hStdOutput,
            hStdErrorCtorArg = hStdError,
        )
    }
}

public val STATUS_DELETE_PENDING: Ntstatus = 0xC0000056u.toInt()

public val STATUS_DIRECTORY_NOT_EMPTY: Ntstatus = 0xC0000101u.toInt()

public val STATUS_END_OF_FILE: Ntstatus = 0xC0000011u.toInt()

public val STATUS_FILE_DELETED: Ntstatus = 0xC0000123u.toInt()

public val STATUS_INVALID_HANDLE: Ntstatus = 0xC0000008u.toInt()

public val STATUS_INVALID_PARAMETER: Ntstatus = 0xC000000Du.toInt()

public val STATUS_NOT_IMPLEMENTED: Ntstatus = 0xC0000002u.toInt()

public val STATUS_PENDING: Ntstatus = 0x103u.toInt()

public val STATUS_SHARING_VIOLATION: Ntstatus = 0xC0000043u.toInt()

public val STATUS_SUCCESS: Ntstatus = 0x0u.toInt()

public val STD_ERROR_HANDLE: UInt = 4294967284u

public val STD_INPUT_HANDLE: UInt = 4294967286u

public val STD_OUTPUT_HANDLE: UInt = 4294967285u

public val SYMBOLIC_LINK_FLAG_ALLOW_UNPRIVILEGED_CREATE: Int = 2

public val SYMBOLIC_LINK_FLAG_DIRECTORY: Int = 1

public val SYMLINK_FLAG_RELATIVE: Int = 1

public val SYNCHRONIZE: Int = 1048576

public open class SystemInfo internal constructor(
    public var anonymous: SystemInfo0,
    public var dwPageSize: UInt,
    lpMinimumApplicationAddressCtorArg: MutPtr,
    lpMaximumApplicationAddressCtorArg: MutPtr,
    public var dwActiveProcessorMask: Int,
    public var dwNumberOfProcessors: UInt,
    public var dwProcessorType: UInt,
    public var dwAllocationGranularity: UInt,
    public var wProcessorLevel: UShort,
    public var wProcessorRevision: UShort,
) {
    public var lpMinimumApplicationAddress: MutPtr = lpMinimumApplicationAddressCtorArg
        internal set

    public var lpMaximumApplicationAddress: MutPtr = lpMaximumApplicationAddressCtorArg
        internal set

    public open fun clone(): SystemInfo = SystemInfo(anonymous, dwPageSize, lpMinimumApplicationAddress, lpMaximumApplicationAddress, dwActiveProcessorMask, dwNumberOfProcessors, dwProcessorType, dwAllocationGranularity, wProcessorLevel, wProcessorRevision)

    public companion object {
        public operator fun invoke(
            anonymous: SystemInfo0,
            dwPageSize: UInt,
            lpMinimumApplicationAddress: MutPtr,
            lpMaximumApplicationAddress: MutPtr,
            dwActiveProcessorMask: Int,
            dwNumberOfProcessors: UInt,
            dwProcessorType: UInt,
            dwAllocationGranularity: UInt,
            wProcessorLevel: UShort,
            wProcessorRevision: UShort,
        ): SystemInfo = SystemInfo(
            anonymous = anonymous,
            dwPageSize = dwPageSize,
            lpMinimumApplicationAddressCtorArg = lpMinimumApplicationAddress,
            lpMaximumApplicationAddressCtorArg = lpMaximumApplicationAddress,
            dwActiveProcessorMask = dwActiveProcessorMask,
            dwNumberOfProcessors = dwNumberOfProcessors,
            dwProcessorType = dwProcessorType,
            dwAllocationGranularity = dwAllocationGranularity,
            wProcessorLevel = wProcessorLevel,
            wProcessorRevision = wProcessorRevision,
        )

        public fun default(): SystemInfo = null
    }
}

public open class SystemInfo0(
    public var dwOemId: UInt? = null,
    public var anonymous: SystemInfo00? = null,
) {
    public companion object {
        public fun default(): SystemInfo0 = null
    }
}

public open class SystemInfo00(
    public var wProcessorArchitecture: UShort,
    public var wReserved: UShort,
) {
    public open fun clone(): SystemInfo00 = SystemInfo00(wProcessorArchitecture, wReserved)

    public companion object {
        public fun typeLayout(): TypeLayout<SystemInfo00> =
            object : TypeLayout<SystemInfo00> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): SystemInfo00 =
                    SystemInfo00(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: SystemInfo00) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.wProcessorArchitecture)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.wReserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SystemInfo00 =
                    SystemInfo00(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SystemInfo00) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.wProcessorArchitecture)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.wReserved)
                }
            }
    }
}

public val S_OK: Hresult = 0x0u.toInt()

public val TCP_NODELAY: Int = 1

public val TIMER_ALL_ACCESS: Int = 2031619

public val TIMER_MODIFY_STATE: Int = 2

public val TLS_OUT_OF_INDEXES: UInt = 4294967295u

public val TOKEN_ACCESS_PSEUDO_HANDLE: Int = 24

public val TOKEN_ACCESS_PSEUDO_HANDLE_WIN8: Int = 24

public val TOKEN_ADJUST_DEFAULT: Int = 128

public val TOKEN_ADJUST_GROUPS: Int = 64

public val TOKEN_ADJUST_PRIVILEGES: Int = 32

public val TOKEN_ADJUST_SESSIONID: Int = 256

public val TOKEN_ALL_ACCESS: Int = 983551

public val TOKEN_ASSIGN_PRIMARY: Int = 1

public val TOKEN_DUPLICATE: Int = 2

public val TOKEN_EXECUTE: Int = 131072

public val TOKEN_IMPERSONATE: Int = 4

public val TOKEN_QUERY: Int = 8

public val TOKEN_QUERY_SOURCE: Int = 16

public val TOKEN_READ: Int = 131080

public val TOKEN_TRUST_CONSTRAINT_MASK: Int = 131096

public val TOKEN_WRITE: Int = 131296

public val TRUE: Int = 1

public val TRUNCATE_EXISTING: Int = 5

public open class UnicodeString internal constructor(
    public var length: UShort,
    public var maximumLength: UShort,
    bufferCtorArg: Pwstr,
) {
    public var buffer: Pwstr = bufferCtorArg
        internal set

    public open fun clone(): UnicodeString = UnicodeString(length, maximumLength, buffer)

    public companion object {
        public fun typeLayout(): TypeLayout<UnicodeString> =
            object : TypeLayout<UnicodeString> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): UnicodeString =
                    UnicodeString(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: UnicodeString) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.length)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.maximumLength)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.buffer)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnicodeString =
                    UnicodeString(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnicodeString) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.length)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.maximumLength)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.buffer)
                }
            }

        public operator fun invoke(
            length: UShort,
            maximumLength: UShort,
            buffer: Pwstr,
        ): UnicodeString = UnicodeString(
            length = length,
            maximumLength = maximumLength,
            bufferCtorArg = buffer,
        )
    }
}

public val VOLUME_NAME_DOS: Int = 0

public val VOLUME_NAME_GUID: Int = 1

public val VOLUME_NAME_NONE: Int = 4

public val VOLUME_NAME_NT: Int = 2

public val WAIT_ABANDONED: Int = 128

public val WAIT_ABANDONED_0: Int = 128

public val WAIT_FAILED: UInt = 4294967295u

public val WAIT_IO_COMPLETION: Int = 192

public val WAIT_OBJECT_0: Int = 0

public val WAIT_TIMEOUT: Int = 258

public open class Win32FindDataw(
    public var dwFileAttributes: UInt,
    public var ftCreationTime: Filetime,
    public var ftLastAccessTime: Filetime,
    public var ftLastWriteTime: Filetime,
    public var nFileSizeHigh: UInt,
    public var nFileSizeLow: UInt,
    public var dwReserved0: UInt,
    public var dwReserved1: UInt,
    public var cFileName: UShortArray,
    public var cAlternateFileName: UShortArray,
) {
    public open fun clone(): Win32FindDataw = Win32FindDataw(dwFileAttributes, ftCreationTime, ftLastAccessTime, ftLastWriteTime, nFileSizeHigh, nFileSizeLow, dwReserved0, dwReserved1, cFileName, cAlternateFileName)

    public companion object {
        public fun typeLayout(): TypeLayout<Win32FindDataw> =
            object : TypeLayout<Win32FindDataw> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 592L
                override fun readNative(addr: Long): Win32FindDataw =
                    Win32FindDataw(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (Filetime.typeLayout()).readNative(addr + 4L),
                        (Filetime.typeLayout()).readNative(addr + 12L),
                        (Filetime.typeLayout()).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveArrayLayouts.uShortArray(260)).readNative(addr + 44L),
                        (PrimitiveArrayLayouts.uShortArray(14)).readNative(addr + 564L),
                    )
                override fun writeNative(addr: Long, value: Win32FindDataw) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.dwFileAttributes)
                    (Filetime.typeLayout()).writeNative(addr + 4L, value.ftCreationTime)
                    (Filetime.typeLayout()).writeNative(addr + 12L, value.ftLastAccessTime)
                    (Filetime.typeLayout()).writeNative(addr + 20L, value.ftLastWriteTime)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.nFileSizeHigh)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.nFileSizeLow)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.dwReserved0)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.dwReserved1)
                    (PrimitiveArrayLayouts.uShortArray(260)).writeNative(addr + 44L, value.cFileName)
                    (PrimitiveArrayLayouts.uShortArray(14)).writeNative(addr + 564L, value.cAlternateFileName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Win32FindDataw =
                    Win32FindDataw(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (Filetime.typeLayout()).readBytes(buf, offset + 4),
                        (Filetime.typeLayout()).readBytes(buf, offset + 12),
                        (Filetime.typeLayout()).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveArrayLayouts.uShortArray(260)).readBytes(buf, offset + 44),
                        (PrimitiveArrayLayouts.uShortArray(14)).readBytes(buf, offset + 564),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Win32FindDataw) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.dwFileAttributes)
                    (Filetime.typeLayout()).writeBytes(buf, offset + 4, value.ftCreationTime)
                    (Filetime.typeLayout()).writeBytes(buf, offset + 12, value.ftLastAccessTime)
                    (Filetime.typeLayout()).writeBytes(buf, offset + 20, value.ftLastWriteTime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.nFileSizeHigh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.nFileSizeLow)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.dwReserved0)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.dwReserved1)
                    (PrimitiveArrayLayouts.uShortArray(260)).writeBytes(buf, offset + 44, value.cFileName)
                    (PrimitiveArrayLayouts.uShortArray(14)).writeBytes(buf, offset + 564, value.cAlternateFileName)
                }
            }

        public fun default(): Win32FindDataw = null
    }
}

public val WRITE_DAC: Int = 262144

public val WRITE_OWNER: Int = 524288

public val WSABASEERR: Int = 10000

public open class Wsabuf internal constructor(
    public var len: UInt,
    bufCtorArg: MutPtr,
) {
    public var buf: MutPtr = bufCtorArg
        internal set

    public open fun clone(): Wsabuf = Wsabuf(len, buf)

    public companion object {
        public fun typeLayout(): TypeLayout<Wsabuf> =
            object : TypeLayout<Wsabuf> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Wsabuf =
                    Wsabuf(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Wsabuf) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.len)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.buf)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Wsabuf =
                    Wsabuf(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Wsabuf) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.len)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.buf)
                }
            }

        public operator fun invoke(
            len: UInt,
            buf: MutPtr,
        ): Wsabuf = Wsabuf(
            len = len,
            bufCtorArg = buf,
        )
    }
}

public open class Wsadata internal constructor(
    public var wVersion: UShort,
    public var wHighVersion: UShort,
    public var iMaxSockets: UShort,
    public var iMaxUdpDg: UShort,
    lpVendorInfoCtorArg: MutPtr,
    public var szDescription: ByteArray,
    public var szSystemStatus: ByteArray,
) {
    public var lpVendorInfo: MutPtr = lpVendorInfoCtorArg
        internal set

    public open fun clone(): Wsadata = Wsadata(wVersion, wHighVersion, iMaxSockets, iMaxUdpDg, lpVendorInfo, szDescription, szSystemStatus)

    public companion object {
        public fun typeLayout(): TypeLayout<Wsadata> =
            object : TypeLayout<Wsadata> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 408L
                override fun readNative(addr: Long): Wsadata =
                    Wsadata(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.byteArray(257)).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.byteArray(129)).readNative(addr + 273L),
                    )
                override fun writeNative(addr: Long, value: Wsadata) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.wVersion)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.wHighVersion)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.iMaxSockets)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.iMaxUdpDg)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.lpVendorInfo)
                    (PrimitiveArrayLayouts.byteArray(257)).writeNative(addr + 16L, value.szDescription)
                    (PrimitiveArrayLayouts.byteArray(129)).writeNative(addr + 273L, value.szSystemStatus)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Wsadata =
                    Wsadata(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.byteArray(257)).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.byteArray(129)).readBytes(buf, offset + 273),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Wsadata) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.wVersion)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.wHighVersion)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.iMaxSockets)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.iMaxUdpDg)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.lpVendorInfo)
                    (PrimitiveArrayLayouts.byteArray(257)).writeBytes(buf, offset + 16, value.szDescription)
                    (PrimitiveArrayLayouts.byteArray(129)).writeBytes(buf, offset + 273, value.szSystemStatus)
                }
            }

        public operator fun invoke(
            wVersion: UShort,
            wHighVersion: UShort,
            iMaxSockets: UShort,
            iMaxUdpDg: UShort,
            lpVendorInfo: MutPtr,
            szDescription: ByteArray,
            szSystemStatus: ByteArray,
        ): Wsadata = Wsadata(
            wVersion = wVersion,
            wHighVersion = wHighVersion,
            iMaxSockets = iMaxSockets,
            iMaxUdpDg = iMaxUdpDg,
            lpVendorInfoCtorArg = lpVendorInfo,
            szDescription = szDescription,
            szSystemStatus = szSystemStatus,
        )

        public fun default(): Wsadata = null
    }
}

public val WSAEACCES: Int = 10013

public val WSAEADDRINUSE: Int = 10048

public val WSAEADDRNOTAVAIL: Int = 10049

public val WSAEAFNOSUPPORT: Int = 10047

public val WSAEALREADY: Int = 10037

public val WSAEBADF: Int = 10009

public val WSAECANCELLED: Int = 10103

public val WSAECONNABORTED: Int = 10053

public val WSAECONNREFUSED: Int = 10061

public val WSAECONNRESET: Int = 10054

public val WSAEDESTADDRREQ: Int = 10039

public val WSAEDISCON: Int = 10101

public val WSAEDQUOT: Int = 10069

public val WSAEFAULT: Int = 10014

public val WSAEHOSTDOWN: Int = 10064

public val WSAEHOSTUNREACH: Int = 10065

public val WSAEINPROGRESS: Int = 10036

public val WSAEINTR: Int = 10004

public val WSAEINVAL: Int = 10022

public val WSAEINVALIDPROCTABLE: Int = 10104

public val WSAEINVALIDPROVIDER: Int = 10105

public val WSAEISCONN: Int = 10056

public val WSAELOOP: Int = 10062

public val WSAEMFILE: Int = 10024

public val WSAEMSGSIZE: Int = 10040

public val WSAENAMETOOLONG: Int = 10063

public val WSAENETDOWN: Int = 10050

public val WSAENETRESET: Int = 10052

public val WSAENETUNREACH: Int = 10051

public val WSAENOBUFS: Int = 10055

public val WSAENOMORE: Int = 10102

public val WSAENOPROTOOPT: Int = 10042

public val WSAENOTCONN: Int = 10057

public val WSAENOTEMPTY: Int = 10066

public val WSAENOTSOCK: Int = 10038

public val WSAEOPNOTSUPP: Int = 10045

public val WSAEPFNOSUPPORT: Int = 10046

public val WSAEPROCLIM: Int = 10067

public val WSAEPROTONOSUPPORT: Int = 10043

public val WSAEPROTOTYPE: Int = 10041

public val WSAEPROVIDERFAILEDINIT: Int = 10106

public val WSAEREFUSED: Int = 10112

public val WSAEREMOTE: Int = 10071

public val WSAESHUTDOWN: Int = 10058

public val WSAESOCKTNOSUPPORT: Int = 10044

public val WSAESTALE: Int = 10070

public val WSAETIMEDOUT: Int = 10060

public val WSAETOOMANYREFS: Int = 10059

public val WSAEUSERS: Int = 10068

public val WSAEWOULDBLOCK: Int = 10035

public val WSAHOST_NOT_FOUND: Int = 11001

public val WSANOTINITIALISED: Int = 10093

public val WSANO_DATA: Int = 11004

public val WSANO_RECOVERY: Int = 11003

public open class Wsaprotocolchain(
    public var chainLen: Int,
    public var chainEntries: UIntArray,
) {
    public open fun clone(): Wsaprotocolchain = Wsaprotocolchain(chainLen, chainEntries)

    public companion object {
        public fun typeLayout(): TypeLayout<Wsaprotocolchain> =
            object : TypeLayout<Wsaprotocolchain> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Wsaprotocolchain =
                    Wsaprotocolchain(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveArrayLayouts.uIntArray(7)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Wsaprotocolchain) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.chainLen)
                    (PrimitiveArrayLayouts.uIntArray(7)).writeNative(addr + 4L, value.chainEntries)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Wsaprotocolchain =
                    Wsaprotocolchain(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uIntArray(7)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Wsaprotocolchain) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.chainLen)
                    (PrimitiveArrayLayouts.uIntArray(7)).writeBytes(buf, offset + 4, value.chainEntries)
                }
            }

        public fun default(): Wsaprotocolchain = null
    }
}

public open class WsaprotocolInfow(
    public var dwServiceFlags1: UInt,
    public var dwServiceFlags2: UInt,
    public var dwServiceFlags3: UInt,
    public var dwServiceFlags4: UInt,
    public var dwProviderFlags: UInt,
    public var providerId: Guid,
    public var dwCatalogEntryId: UInt,
    public var protocolChain: Wsaprotocolchain,
    public var iVersion: Int,
    public var iAddressFamily: Int,
    public var iMaxSockAddr: Int,
    public var iMinSockAddr: Int,
    public var iSocketType: Int,
    public var iProtocol: Int,
    public var iProtocolMaxOffset: Int,
    public var iNetworkByteOrder: Int,
    public var iSecurityScheme: Int,
    public var dwMessageSize: UInt,
    public var dwProviderReserved: UInt,
    public var szProtocol: UShortArray,
) {
    public open fun clone(): WsaprotocolInfow = WsaprotocolInfow(dwServiceFlags1, dwServiceFlags2, dwServiceFlags3, dwServiceFlags4, dwProviderFlags, providerId, dwCatalogEntryId, protocolChain, iVersion, iAddressFamily, iMaxSockAddr, iMinSockAddr, iSocketType, iProtocol, iProtocolMaxOffset, iNetworkByteOrder, iSecurityScheme, dwMessageSize, dwProviderReserved, szProtocol)

    public companion object {
        public fun typeLayout(): TypeLayout<WsaprotocolInfow> =
            object : TypeLayout<WsaprotocolInfow> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 628L
                override fun readNative(addr: Long): WsaprotocolInfow =
                    WsaprotocolInfow(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (Guid.typeLayout()).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (Wsaprotocolchain.typeLayout()).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 72L),
                        (PrimitiveLayouts.I32).readNative(addr + 76L),
                        (PrimitiveLayouts.I32).readNative(addr + 80L),
                        (PrimitiveLayouts.I32).readNative(addr + 84L),
                        (PrimitiveLayouts.I32).readNative(addr + 88L),
                        (PrimitiveLayouts.I32).readNative(addr + 92L),
                        (PrimitiveLayouts.I32).readNative(addr + 96L),
                        (PrimitiveLayouts.I32).readNative(addr + 100L),
                        (PrimitiveLayouts.I32).readNative(addr + 104L),
                        (PrimitiveLayouts.U32).readNative(addr + 108L),
                        (PrimitiveLayouts.U32).readNative(addr + 112L),
                        (PrimitiveArrayLayouts.uShortArray(256)).readNative(addr + 116L),
                    )
                override fun writeNative(addr: Long, value: WsaprotocolInfow) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.dwServiceFlags1)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.dwServiceFlags2)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.dwServiceFlags3)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.dwServiceFlags4)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.dwProviderFlags)
                    (Guid.typeLayout()).writeNative(addr + 20L, value.providerId)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.dwCatalogEntryId)
                    (Wsaprotocolchain.typeLayout()).writeNative(addr + 40L, value.protocolChain)
                    (PrimitiveLayouts.I32).writeNative(addr + 72L, value.iVersion)
                    (PrimitiveLayouts.I32).writeNative(addr + 76L, value.iAddressFamily)
                    (PrimitiveLayouts.I32).writeNative(addr + 80L, value.iMaxSockAddr)
                    (PrimitiveLayouts.I32).writeNative(addr + 84L, value.iMinSockAddr)
                    (PrimitiveLayouts.I32).writeNative(addr + 88L, value.iSocketType)
                    (PrimitiveLayouts.I32).writeNative(addr + 92L, value.iProtocol)
                    (PrimitiveLayouts.I32).writeNative(addr + 96L, value.iProtocolMaxOffset)
                    (PrimitiveLayouts.I32).writeNative(addr + 100L, value.iNetworkByteOrder)
                    (PrimitiveLayouts.I32).writeNative(addr + 104L, value.iSecurityScheme)
                    (PrimitiveLayouts.U32).writeNative(addr + 108L, value.dwMessageSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 112L, value.dwProviderReserved)
                    (PrimitiveArrayLayouts.uShortArray(256)).writeNative(addr + 116L, value.szProtocol)
                }
                override fun readBytes(buf: ByteArray, offset: Int): WsaprotocolInfow =
                    WsaprotocolInfow(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (Guid.typeLayout()).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (Wsaprotocolchain.typeLayout()).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 76),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 92),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 100),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 104),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 108),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 112),
                        (PrimitiveArrayLayouts.uShortArray(256)).readBytes(buf, offset + 116),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: WsaprotocolInfow) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.dwServiceFlags1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.dwServiceFlags2)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.dwServiceFlags3)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.dwServiceFlags4)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.dwProviderFlags)
                    (Guid.typeLayout()).writeBytes(buf, offset + 20, value.providerId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.dwCatalogEntryId)
                    (Wsaprotocolchain.typeLayout()).writeBytes(buf, offset + 40, value.protocolChain)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 72, value.iVersion)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 76, value.iAddressFamily)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 80, value.iMaxSockAddr)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 84, value.iMinSockAddr)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 88, value.iSocketType)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 92, value.iProtocol)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 96, value.iProtocolMaxOffset)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 100, value.iNetworkByteOrder)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 104, value.iSecurityScheme)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 108, value.dwMessageSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 112, value.dwProviderReserved)
                    (PrimitiveArrayLayouts.uShortArray(256)).writeBytes(buf, offset + 116, value.szProtocol)
                }
            }

        public fun default(): WsaprotocolInfow = null
    }
}

public val WSASERVICE_NOT_FOUND: Int = 10108

public val WSASYSCALLFAILURE: Int = 10107

public val WSASYSNOTREADY: Int = 10091

public val WSATRY_AGAIN: Int = 11002

public val WSATYPE_NOT_FOUND: Int = 10109

public val WSAVERNOTSUPPORTED: Int = 10092

public val WSA_E_CANCELLED: Int = 10111

public val WSA_E_NO_MORE: Int = 10110

public val WSA_FLAG_NO_HANDLE_INHERIT: Int = 128

public val WSA_FLAG_OVERLAPPED: Int = 1

public val WSA_INVALID_HANDLE: Int = 6

public val WSA_INVALID_PARAMETER: Int = 87

public val WSA_IO_INCOMPLETE: Int = 996

public val WSA_IO_PENDING: Int = 997

public val WSA_IPSEC_NAME_POLICY_ERROR: Int = 11033

public val WSA_NOT_ENOUGH_MEMORY: Int = 8

public val WSA_OPERATION_ABORTED: Int = 995

public val WSA_QOS_ADMISSION_FAILURE: Int = 11010

public val WSA_QOS_BAD_OBJECT: Int = 11013

public val WSA_QOS_BAD_STYLE: Int = 11012

public val WSA_QOS_EFILTERCOUNT: Int = 11021

public val WSA_QOS_EFILTERSTYLE: Int = 11019

public val WSA_QOS_EFILTERTYPE: Int = 11020

public val WSA_QOS_EFLOWCOUNT: Int = 11023

public val WSA_QOS_EFLOWDESC: Int = 11026

public val WSA_QOS_EFLOWSPEC: Int = 11017

public val WSA_QOS_EOBJLENGTH: Int = 11022

public val WSA_QOS_EPOLICYOBJ: Int = 11025

public val WSA_QOS_EPROVSPECBUF: Int = 11018

public val WSA_QOS_EPSFILTERSPEC: Int = 11028

public val WSA_QOS_EPSFLOWSPEC: Int = 11027

public val WSA_QOS_ESDMODEOBJ: Int = 11029

public val WSA_QOS_ESERVICETYPE: Int = 11016

public val WSA_QOS_ESHAPERATEOBJ: Int = 11030

public val WSA_QOS_EUNKOWNPSOBJ: Int = 11024

public val WSA_QOS_GENERIC_ERROR: Int = 11015

public val WSA_QOS_NO_RECEIVERS: Int = 11008

public val WSA_QOS_NO_SENDERS: Int = 11007

public val WSA_QOS_POLICY_FAILURE: Int = 11011

public val WSA_QOS_RECEIVERS: Int = 11005

public val WSA_QOS_REQUEST_CONFIRMED: Int = 11009

public val WSA_QOS_RESERVED_PETYPE: Int = 11031

public val WSA_QOS_SENDERS: Int = 11006

public val WSA_QOS_TRAFFIC_CTRL_ERROR: Int = 11014

public val WSA_SECURE_HOST_NOT_FOUND: Int = 11032

public val WSA_WAIT_EVENT_0: Int = 0

public val WSA_WAIT_IO_COMPLETION: Int = 192

public typealias XmmSaveArea32 = XsaveFormat

public open class XsaveFormat(
    public var controlWord: UShort,
    public var statusWord: UShort,
    public var tagWord: UByte,
    public var reserved1: UByte,
    public var errorOpcode: UShort,
    public var errorOffset: UInt,
    public var errorSelector: UShort,
    public var reserved2: UShort,
    public var dataOffset: UInt,
    public var dataSelector: UShort,
    public var reserved3: UShort,
    public var mxCsr: UInt,
    public var mxCsrMask: UInt,
    public var floatRegisters: List<M128a>,
    public var xmmRegisters: List<M128a>,
    public var reserved4: UByteArray,
) {
    public open fun clone(): XsaveFormat = XsaveFormat(controlWord, statusWord, tagWord, reserved1, errorOpcode, errorOffset, errorSelector, reserved2, dataOffset, dataSelector, reserved3, mxCsr, mxCsrMask, floatRegisters, xmmRegisters, reserved4)

    public companion object {
        public fun typeLayout(): TypeLayout<XsaveFormat> =
            object : TypeLayout<XsaveFormat> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 512L
                override fun readNative(addr: Long): XsaveFormat =
                    XsaveFormat(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 12L),
                        (PrimitiveLayouts.U16).readNative(addr + 14L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 20L),
                        (PrimitiveLayouts.U16).readNative(addr + 22L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (fixedListTypeLayout(M128a.typeLayout(), 8)).readNative(addr + 32L),
                        (fixedListTypeLayout(M128a.typeLayout(), 16)).readNative(addr + 160L),
                        (PrimitiveArrayLayouts.uByteArray(96)).readNative(addr + 416L),
                    )
                override fun writeNative(addr: Long, value: XsaveFormat) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.controlWord)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.statusWord)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.tagWord)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.reserved1)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.errorOpcode)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.errorOffset)
                    (PrimitiveLayouts.U16).writeNative(addr + 12L, value.errorSelector)
                    (PrimitiveLayouts.U16).writeNative(addr + 14L, value.reserved2)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.dataOffset)
                    (PrimitiveLayouts.U16).writeNative(addr + 20L, value.dataSelector)
                    (PrimitiveLayouts.U16).writeNative(addr + 22L, value.reserved3)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.mxCsr)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.mxCsrMask)
                    (fixedListTypeLayout(M128a.typeLayout(), 8)).writeNative(addr + 32L, value.floatRegisters)
                    (fixedListTypeLayout(M128a.typeLayout(), 16)).writeNative(addr + 160L, value.xmmRegisters)
                    (PrimitiveArrayLayouts.uByteArray(96)).writeNative(addr + 416L, value.reserved4)
                }
                override fun readBytes(buf: ByteArray, offset: Int): XsaveFormat =
                    XsaveFormat(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 14),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 22),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (fixedListTypeLayout(M128a.typeLayout(), 8)).readBytes(buf, offset + 32),
                        (fixedListTypeLayout(M128a.typeLayout(), 16)).readBytes(buf, offset + 160),
                        (PrimitiveArrayLayouts.uByteArray(96)).readBytes(buf, offset + 416),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: XsaveFormat) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.controlWord)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.statusWord)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.tagWord)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.reserved1)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.errorOpcode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.errorOffset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 12, value.errorSelector)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 14, value.reserved2)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.dataOffset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 20, value.dataSelector)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 22, value.reserved3)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.mxCsr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.mxCsrMask)
                    (fixedListTypeLayout(M128a.typeLayout(), 8)).writeBytes(buf, offset + 32, value.floatRegisters)
                    (fixedListTypeLayout(M128a.typeLayout(), 16)).writeBytes(buf, offset + 160, value.xmmRegisters)
                    (PrimitiveArrayLayouts.uByteArray(96)).writeBytes(buf, offset + 416, value.reserved4)
                }
            }

        public fun default(): XsaveFormat = null
    }
}

public open class ProcThreadAttributeList(
    public var value: UByte,
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ProcThreadAttributeList = ProcThreadAttributeList(value)

    public companion object {
        public fun typeLayout(): TypeLayout<ProcThreadAttributeList> =
            object : TypeLayout<ProcThreadAttributeList> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): ProcThreadAttributeList =
                    ProcThreadAttributeList(
                        (PrimitiveLayouts.U8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ProcThreadAttributeList) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ProcThreadAttributeList =
                    ProcThreadAttributeList(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ProcThreadAttributeList) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class FdSet(
    public var fdCount: UInt,
    public var fdArray: ULongArray,
) {
    public open fun clone(): FdSet = FdSet(fdCount, fdArray)

    public companion object {
        public fun typeLayout(): TypeLayout<FdSet> =
            object : TypeLayout<FdSet> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 520L
                override fun readNative(addr: Long): FdSet =
                    FdSet(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: FdSet) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.fdCount)
                    (PrimitiveArrayLayouts.uLongArray(64)).writeNative(addr + 8L, value.fdArray)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FdSet =
                    FdSet(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FdSet) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.fdCount)
                    (PrimitiveArrayLayouts.uLongArray(64)).writeBytes(buf, offset + 8, value.fdArray)
                }
            }

        public fun default(): FdSet = null
    }
}

public open class Linger(
    public var lOnoff: UShort,
    public var lLinger: UShort,
) {
    public open fun clone(): Linger = Linger(lOnoff, lLinger)

    public companion object {
        public fun typeLayout(): TypeLayout<Linger> =
            object : TypeLayout<Linger> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Linger =
                    Linger(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Linger) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.lOnoff)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.lLinger)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Linger =
                    Linger(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Linger) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.lOnoff)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.lLinger)
                }
            }
    }
}

public open class Timeval(
    public var tvSec: Int,
    public var tvUsec: Int,
) {
    public open fun clone(): Timeval = Timeval(tvSec, tvUsec)

    public companion object {
        public fun typeLayout(): TypeLayout<Timeval> =
            object : TypeLayout<Timeval> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Timeval =
                    Timeval(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Timeval) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.tvUsec)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Timeval =
                    Timeval(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Timeval) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.tvUsec)
                }
            }
    }
}

public typealias ULong = UInt

public typealias VaList = MutPtr
