@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysAccept
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysBind
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCancelIo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCloseHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysClosesocket
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysConnect
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCopyFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateEventW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreatePipe
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateProcessW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysDeleteFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysExitProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFindClose
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFindNextFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFlsAlloc
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFlsFree
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFlsGetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFlsSetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFormatMessageW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetFileType
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetHostNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetProcessId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetStdHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetTempPathW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetpeername
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetsockname
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysGetsockopt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysIoctlsocket
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysListen
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysLocalFree
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysLockFileEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysLstrlenW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysMoveFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysNtCreateFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysNtOpenFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysNtReadFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysNtWriteFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysReadConsoleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysReadFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysReadFileEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysRecv
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysRecvfrom
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSelect
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSend
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSendto
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetFileTime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetStdHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSetsockopt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysShutdown
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSleep
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSleepEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysSwitchToThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysTerminateProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysTlsAlloc
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysTlsFree
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysTlsGetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysTlsSetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysUnlockFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWSACleanup
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWSARecv
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWSASend
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWSASocketW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWSAStartup
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.stdSysPalWindowsCWindowssysWindowsSysWriteFileEx
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first, 0L.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun cancelIo(hfile: Handle): Bool =
    stdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile.rawAddress.toCPointer<ByteVar>())

public actual fun closeHandle(hobject: Handle): Bool =
    stdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject.rawAddress.toCPointer<ByteVar>())

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    stdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1.rawAddress.toCPointer<ByteVar>(), cchcount1, lpstring2.rawAddress.toCPointer<ByteVar>(), cchcount2, bignorecase)

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename.rawAddress.toCPointer<ByteVar>(), lpnewfilename.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), lpdata.rawAddress.toCPointer<ByteVar>(), pbcancel.rawAddress.toCPointer<ByteVar>(), dwcopyflags)

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname.rawAddress.toCPointer<ByteVar>(), lpsecurityattributes.rawAddress.toCPointer<ByteVar>())

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes.rawAddress.toCPointer<ByteVar>(), bmanualreset, binitialstate, lpname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename.rawAddress.toCPointer<ByteVar>(), dwdesiredaccess, dwsharemode, lpsecurityattributes.rawAddress.toCPointer<ByteVar>(), dwcreationdisposition, dwflagsandattributes, htemplatefile.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename.rawAddress.toCPointer<ByteVar>(), lpexistingfilename.rawAddress.toCPointer<ByteVar>(), lpsecurityattributes.rawAddress.toCPointer<ByteVar>())

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname.rawAddress.toCPointer<ByteVar>(), dwopenmode, dwpipemode, nmaxinstances, noutbuffersize, ninbuffersize, ndefaulttimeout, lpsecurityattributes.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe.rawAddress.toCPointer<ByteVar>(), hwritepipe.rawAddress.toCPointer<ByteVar>(), lppipeattributes.rawAddress.toCPointer<ByteVar>(), nsize)

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname.rawAddress.toCPointer<ByteVar>(), lpcommandline.rawAddress.toCPointer<ByteVar>(), lpprocessattributes.rawAddress.toCPointer<ByteVar>(), lpthreadattributes.rawAddress.toCPointer<ByteVar>(), binherithandles, dwcreationflags, lpenvironment.rawAddress.toCPointer<ByteVar>(), lpcurrentdirectory.rawAddress.toCPointer<ByteVar>(), lpstartupinfo.rawAddress.toCPointer<ByteVar>(), lpprocessinformation.rawAddress.toCPointer<ByteVar>())

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    stdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename.rawAddress.toCPointer<ByteVar>(), lptargetfilename.rawAddress.toCPointer<ByteVar>(), dwflags)

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes.rawAddress.toCPointer<ByteVar>(), dwstacksize, 0L.toCPointer<ByteVar>(), lpparameter.rawAddress.toCPointer<ByteVar>(), dwcreationflags, lpthreadid.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes.rawAddress.toCPointer<ByteVar>(), lptimername.rawAddress.toCPointer<ByteVar>(), dwflags, dwdesiredaccess)?.toLong() ?: 0L)

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename.rawAddress.toCPointer<ByteVar>())

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist.rawAddress.toCPointer<ByteVar>())
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice.rawAddress.toCPointer<ByteVar>(), dwiocontrolcode, lpinbuffer.rawAddress.toCPointer<ByteVar>(), ninbuffersize, lpoutbuffer.rawAddress.toCPointer<ByteVar>(), noutbuffersize, lpbytesreturned.rawAddress.toCPointer<ByteVar>(), lpoverlapped.rawAddress.toCPointer<ByteVar>())

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle.rawAddress.toCPointer<ByteVar>(), hsourcehandle.rawAddress.toCPointer<ByteVar>(), htargetprocesshandle.rawAddress.toCPointer<ByteVar>(), lptargethandle.rawAddress.toCPointer<ByteVar>(), dwdesiredaccess, binherithandle, dwoptions)

public actual fun exitProcess(uexitcode: UInt): Nothing {
    stdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode)
    error("Unreachable noreturn C function exitProcess")
}

public actual fun findClose(hfindfile: Handle): Bool =
    stdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile.rawAddress.toCPointer<ByteVar>())

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename.rawAddress.toCPointer<ByteVar>(), finfolevelid, lpfindfiledata.rawAddress.toCPointer<ByteVar>(), fsearchop, lpsearchfilter.rawAddress.toCPointer<ByteVar>(), dwadditionalflags)?.toLong() ?: 0L)

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile.rawAddress.toCPointer<ByteVar>(), lpfindfiledata.rawAddress.toCPointer<ByteVar>())

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    stdSysPalWindowsCWindowssysWindowsSysFlsAlloc(0L.toCPointer<ByteVar>())

public actual fun flsFree(dwflsindex: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex)

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex)?.toLong() ?: 0L)

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex, lpflsdata.rawAddress.toCPointer<ByteVar>())

public actual fun flushFileBuffers(hfile: Handle): Bool =
    stdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile.rawAddress.toCPointer<ByteVar>())

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    stdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags, lpsource.rawAddress.toCPointer<ByteVar>(), dwmessageid, dwlanguageid, lpbuffer.rawAddress.toCPointer<ByteVar>(), nsize, arguments.rawAddress.toCPointer<ByteVar>())

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv.rawAddress.toCPointer<ByteVar>())

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber)

public actual fun getCommandLineW(): Pwstr =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysGetCommandLineW()?.toLong() ?: 0L)

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle.rawAddress.toCPointer<ByteVar>(), lpmode.rawAddress.toCPointer<ByteVar>())

public actual fun getConsoleOutputCP(): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP()

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength, lpbuffer.rawAddress.toCPointer<ByteVar>())

public actual fun getCurrentProcess(): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess()?.toLong() ?: 0L)

public actual fun getCurrentProcessId(): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId()

public actual fun getCurrentThread(): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysGetCurrentThread()?.toLong() ?: 0L)

public actual fun getCurrentThreadId(): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId()

public actual fun getEnvironmentStringsW(): Lpwch =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW()?.toLong() ?: 0L)

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nsize)

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess.rawAddress.toCPointer<ByteVar>(), lpexitcode.rawAddress.toCPointer<ByteVar>())

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename.rawAddress.toCPointer<ByteVar>())

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile.rawAddress.toCPointer<ByteVar>(), lpfileinformation.rawAddress.toCPointer<ByteVar>())

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile.rawAddress.toCPointer<ByteVar>(), fileinformationclass, lpfileinformation.rawAddress.toCPointer<ByteVar>(), dwbuffersize)

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile.rawAddress.toCPointer<ByteVar>(), lpfilesize.rawAddress.toCPointer<ByteVar>())

public actual fun getFileType(hfile: Handle): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile.rawAddress.toCPointer<ByteVar>())

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile.rawAddress.toCPointer<ByteVar>(), lpszfilepath.rawAddress.toCPointer<ByteVar>(), cchfilepath, dwflags)

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename.rawAddress.toCPointer<ByteVar>(), nbufferlength, lpbuffer.rawAddress.toCPointer<ByteVar>(), lpfilepart.rawAddress.toCPointer<ByteVar>())

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name.rawAddress.toCPointer<ByteVar>(), namelen)

public actual fun getLastError(): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetLastError()

public actual fun getLogicalDrives(): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives()

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule.rawAddress.toCPointer<ByteVar>(), lpfilename.rawAddress.toCPointer<ByteVar>(), nsize)

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags, lpmodulename.rawAddress.toCPointer<ByteVar>(), phmodule.rawAddress.toCPointer<ByteVar>())

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    stdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile.rawAddress.toCPointer<ByteVar>(), lpoverlapped.rawAddress.toCPointer<ByteVar>(), lpnumberofbytestransferred.rawAddress.toCPointer<ByteVar>(), bwait)

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    null

public actual fun getProcessId(process: Handle): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetProcessId(process.rawAddress.toCPointer<ByteVar>())

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle)?.toLong() ?: 0L)

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer.rawAddress.toCPointer<ByteVar>(), usize)

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo.rawAddress.toCPointer<ByteVar>())
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime.rawAddress.toCPointer<ByteVar>())
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime.rawAddress.toCPointer<ByteVar>())
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength, lpbuffer.rawAddress.toCPointer<ByteVar>())

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken.rawAddress.toCPointer<ByteVar>(), lpprofiledir.rawAddress.toCPointer<ByteVar>(), lpcchsize.rawAddress.toCPointer<ByteVar>())

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    stdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer.rawAddress.toCPointer<ByteVar>(), usize)

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce.rawAddress.toCPointer<ByteVar>(), dwflags, fpending.rawAddress.toCPointer<ByteVar>(), lpcontext.rawAddress.toCPointer<ByteVar>())

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce.rawAddress.toCPointer<ByteVar>(), dwflags, lpcontext.rawAddress.toCPointer<ByteVar>())

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist.rawAddress.toCPointer<ByteVar>(), dwattributecount, dwflags, lpsize.rawAddress.toCPointer<ByteVar>())

public actual fun isThreadAFiber(): Bool =
    stdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber()

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename.rawAddress.toCPointer<ByteVar>(), hfile.rawAddress.toCPointer<ByteVar>(), dwflags)?.toLong() ?: 0L)

public actual fun localFree(hmem: Hlocal): Hlocal =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile.rawAddress.toCPointer<ByteVar>(), dwflags, dwreserved, nnumberofbytestolocklow, nnumberofbytestolockhigh, lpoverlapped.rawAddress.toCPointer<ByteVar>())

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename.rawAddress.toCPointer<ByteVar>(), lpnewfilename.rawAddress.toCPointer<ByteVar>(), dwflags)

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage, dwflags, lpmultibytestr.rawAddress.toCPointer<ByteVar>(), cbmultibyte, lpwidecharstr.rawAddress.toCPointer<ByteVar>(), cchwidechar)

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    stdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle.rawAddress.toCPointer<ByteVar>(), desiredaccess, objectattributes.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), allocationsize.rawAddress.toCPointer<ByteVar>(), fileattributes, shareaccess, createdisposition, createoptions, eabuffer.rawAddress.toCPointer<ByteVar>(), ealength)

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    stdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle.rawAddress.toCPointer<ByteVar>(), desiredaccess, objectattributes.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), shareaccess, openoptions)

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    stdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), fsinformation.rawAddress.toCPointer<ByteVar>(), length, fsinformationclass)

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    stdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle.rawAddress.toCPointer<ByteVar>(), event.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), apccontext.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), buffer.rawAddress.toCPointer<ByteVar>(), length, byteoffset.rawAddress.toCPointer<ByteVar>(), key.rawAddress.toCPointer<ByteVar>())

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    stdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), fileinformation.rawAddress.toCPointer<ByteVar>(), length, fileinformationclass)

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    stdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle.rawAddress.toCPointer<ByteVar>(), event.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), apccontext.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), buffer.rawAddress.toCPointer<ByteVar>(), length, byteoffset.rawAddress.toCPointer<ByteVar>(), key.rawAddress.toCPointer<ByteVar>())

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle.rawAddress.toCPointer<ByteVar>(), desiredaccess, tokenhandle.rawAddress.toCPointer<ByteVar>())

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    stdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename.rawAddress.toCPointer<ByteVar>(), lptargetpath.rawAddress.toCPointer<ByteVar>(), ucchmax)

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount.rawAddress.toCPointer<ByteVar>())

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency.rawAddress.toCPointer<ByteVar>())

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofcharstoread, lpnumberofcharsread.rawAddress.toCPointer<ByteVar>(), pinputcontrol.rawAddress.toCPointer<ByteVar>())

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysReadFile(hfile.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofbytestoread, lpnumberofbytesread.rawAddress.toCPointer<ByteVar>(), lpoverlapped.rawAddress.toCPointer<ByteVar>())

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    stdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofbytestoread, lpoverlapped.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname.rawAddress.toCPointer<ByteVar>())

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    stdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer.rawAddress.toCPointer<ByteVar>(), randombufferlength)

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    stdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status)

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname.rawAddress.toCPointer<ByteVar>())

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname.rawAddress.toCPointer<ByteVar>(), lpvalue.rawAddress.toCPointer<ByteVar>())

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename.rawAddress.toCPointer<ByteVar>(), dwfileattributes)

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile.rawAddress.toCPointer<ByteVar>(), fileinformationclass, lpfileinformation.rawAddress.toCPointer<ByteVar>(), dwbuffersize)

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile.rawAddress.toCPointer<ByteVar>(), lidistancetomove, lpnewfilepointer.rawAddress.toCPointer<ByteVar>(), dwmovemethod)

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile.rawAddress.toCPointer<ByteVar>(), lpcreationtime.rawAddress.toCPointer<ByteVar>(), lplastaccesstime.rawAddress.toCPointer<ByteVar>(), lplastwritetime.rawAddress.toCPointer<ByteVar>())

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject.rawAddress.toCPointer<ByteVar>(), dwmask, dwflags)

public actual fun setLastError(dwerrcode: UInt) {
    stdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode)
}

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle, hhandle.rawAddress.toCPointer<ByteVar>())

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes.rawAddress.toCPointer<ByteVar>())

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer.rawAddress.toCPointer<ByteVar>(), lpduetime.rawAddress.toCPointer<ByteVar>(), lperiod, 0L.toCPointer<ByteVar>(), lpargtocompletionroutine.rawAddress.toCPointer<ByteVar>(), fresume)

public actual fun sleep(dwmilliseconds: UInt) {
    stdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds)
}

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable.rawAddress.toCPointer<ByteVar>(), srwlock.rawAddress.toCPointer<ByteVar>(), dwmilliseconds, flags)

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    stdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds, balertable)

public actual fun switchToThread(): Bool =
    stdSysPalWindowsCWindowssysWindowsSysSwitchToThread()

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess.rawAddress.toCPointer<ByteVar>(), uexitcode)

public actual fun tlsAlloc(): UInt =
    stdSysPalWindowsCWindowssysWindowsSysTlsAlloc()

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex)

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    MutPtr(stdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex)?.toLong() ?: 0L)

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex, lptlsvalue.rawAddress.toCPointer<ByteVar>())

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock.rawAddress.toCPointer<ByteVar>())

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    stdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock.rawAddress.toCPointer<ByteVar>())

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile.rawAddress.toCPointer<ByteVar>(), dwfileoffsetlow, dwfileoffsethigh, nnumberofbytestounlocklow, nnumberofbytestounlockhigh)

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist.rawAddress.toCPointer<ByteVar>(), dwflags, attribute, lpvalue.rawAddress.toCPointer<ByteVar>(), cbsize, lppreviousvalue.rawAddress.toCPointer<ByteVar>(), lpreturnsize.rawAddress.toCPointer<ByteVar>())

public actual fun wSACleanup(): Int =
    stdSysPalWindowsCWindowssysWindowsSysWSACleanup()

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s, dwprocessid, lpprotocolinfo.rawAddress.toCPointer<ByteVar>())

public actual fun wSAGetLastError(): Int =
    stdSysPalWindowsCWindowssysWindowsSysWSAGetLastError()

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    stdSysPalWindowsCWindowssysWindowsSysWSARecv(s, lpbuffers.rawAddress.toCPointer<ByteVar>(), dwbuffercount, lpnumberofbytesrecvd.rawAddress.toCPointer<ByteVar>(), lpflags.rawAddress.toCPointer<ByteVar>(), lpoverlapped.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    stdSysPalWindowsCWindowssysWindowsSysWSASend(s, lpbuffers.rawAddress.toCPointer<ByteVar>(), dwbuffercount, lpnumberofbytessent.rawAddress.toCPointer<ByteVar>(), dwflags, lpoverlapped.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    stdSysPalWindowsCWindowssysWindowsSysWSASocketW(af, type, protocol, lpprotocolinfo.rawAddress.toCPointer<ByteVar>(), g, dwflags)

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested, lpwsadata.rawAddress.toCPointer<ByteVar>())

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    stdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount, lphandles.rawAddress.toCPointer<ByteVar>(), bwaitall, dwmilliseconds)

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    stdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle.rawAddress.toCPointer<ByteVar>(), dwmilliseconds)

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    stdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address.rawAddress.toCPointer<ByteVar>(), compareaddress.rawAddress.toCPointer<ByteVar>(), addresssize, dwmilliseconds)

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable.rawAddress.toCPointer<ByteVar>())
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    stdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address.rawAddress.toCPointer<ByteVar>())
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    stdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address.rawAddress.toCPointer<ByteVar>())
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    stdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable.rawAddress.toCPointer<ByteVar>())
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage, dwflags, lpwidecharstr.rawAddress.toCPointer<ByteVar>(), cchwidechar, lpmultibytestr.rawAddress.toCPointer<ByteVar>(), cbmultibyte, lpdefaultchar.rawAddress.toCPointer<ByteVar>(), lpuseddefaultchar.rawAddress.toCPointer<ByteVar>())

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    stdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofcharstowrite, lpnumberofcharswritten.rawAddress.toCPointer<ByteVar>(), lpreserved.rawAddress.toCPointer<ByteVar>())

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    stdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofbytestowrite, lpoverlapped.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    stdSysPalWindowsCWindowssysWindowsSysAccept(s, addr.rawAddress.toCPointer<ByteVar>(), addrlen.rawAddress.toCPointer<ByteVar>())

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysBind(s, name.rawAddress.toCPointer<ByteVar>(), namelen)

public actual fun closesocket(s: Socket): Int =
    stdSysPalWindowsCWindowssysWindowsSysClosesocket(s)

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysConnect(s, name.rawAddress.toCPointer<ByteVar>(), namelen)

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    stdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo.rawAddress.toCPointer<ByteVar>())
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename.rawAddress.toCPointer<ByteVar>(), pservicename.rawAddress.toCPointer<ByteVar>(), phints.rawAddress.toCPointer<ByteVar>(), ppresult.rawAddress.toCPointer<ByteVar>())

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysGetpeername(s, name.rawAddress.toCPointer<ByteVar>(), namelen.rawAddress.toCPointer<ByteVar>())

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysGetsockname(s, name.rawAddress.toCPointer<ByteVar>(), namelen.rawAddress.toCPointer<ByteVar>())

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysGetsockopt(s, level, optname, optval.rawAddress.toCPointer<ByteVar>(), optlen.rawAddress.toCPointer<ByteVar>())

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s, cmd, argp.rawAddress.toCPointer<ByteVar>())

public actual fun listen(s: Socket, backlog: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysListen(s, backlog)

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    stdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring.rawAddress.toCPointer<ByteVar>())

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysRecv(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysRecvfrom(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags, from.rawAddress.toCPointer<ByteVar>(), fromlen.rawAddress.toCPointer<ByteVar>())

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    stdSysPalWindowsCWindowssysWindowsSysSelect(nfds, readfds.rawAddress.toCPointer<ByteVar>(), writefds.rawAddress.toCPointer<ByteVar>(), exceptfds.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>())

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysSend(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysSendto(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags, to.rawAddress.toCPointer<ByteVar>(), tolen)

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysSetsockopt(s, level, optname, optval.rawAddress.toCPointer<ByteVar>(), optlen)

public actual fun shutdown(s: Socket, how: Int): Int =
    stdSysPalWindowsCWindowssysWindowsSysShutdown(s, how)
