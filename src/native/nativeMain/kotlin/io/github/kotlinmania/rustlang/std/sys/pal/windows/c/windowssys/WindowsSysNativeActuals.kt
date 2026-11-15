@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysAccept
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysBind
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysConnect
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysListen
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysRecv
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSelect
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSend
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSendto
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSleep
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.cinterop.rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun acquireSRWLockExclusive(srwlock: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockExclusive(srwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun acquireSRWLockShared(srwlock: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysAcquireSRWLockShared(srwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun addVectoredExceptionHandler(first: UInt, handler: PvectoredExceptionHandler): MutPtr =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysAddVectoredExceptionHandler(first, 0L.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun cancelIo(hfile: Handle): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCancelIo(hfile.rawAddress.toCPointer<ByteVar>())

public actual fun closeHandle(hobject: Handle): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCloseHandle(hobject.rawAddress.toCPointer<ByteVar>())

public actual fun compareStringOrdinal(lpstring1: ConstPtr, cchcount1: Int, lpstring2: ConstPtr, cchcount2: Int, bignorecase: Bool): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCompareStringOrdinal(lpstring1.rawAddress.toCPointer<ByteVar>(), cchcount1, lpstring2.rawAddress.toCPointer<ByteVar>(), cchcount2, bignorecase)

public actual fun copyFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, lpprogressroutine: LpprogressRoutine, lpdata: ConstPtr, pbcancel: MutPtr, dwcopyflags: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCopyFileExW(lpexistingfilename.rawAddress.toCPointer<ByteVar>(), lpnewfilename.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), lpdata.rawAddress.toCPointer<ByteVar>(), pbcancel.rawAddress.toCPointer<ByteVar>(), dwcopyflags)

public actual fun createDirectoryW(lppathname: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCreateDirectoryW(lppathname.rawAddress.toCPointer<ByteVar>(), lpsecurityattributes.rawAddress.toCPointer<ByteVar>())

public actual fun createEventW(lpeventattributes: ConstPtr, bmanualreset: Bool, binitialstate: Bool, lpname: Pcwstr): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysCreateEventW(lpeventattributes.rawAddress.toCPointer<ByteVar>(), bmanualreset, binitialstate, lpname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createFileW(lpfilename: Pcwstr, dwdesiredaccess: UInt, dwsharemode: UInt, lpsecurityattributes: ConstPtr, dwcreationdisposition: UInt, dwflagsandattributes: UInt, htemplatefile: Handle): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysCreateFileW(lpfilename.rawAddress.toCPointer<ByteVar>(), dwdesiredaccess, dwsharemode, lpsecurityattributes.rawAddress.toCPointer<ByteVar>(), dwcreationdisposition, dwflagsandattributes, htemplatefile.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createHardLinkW(lpfilename: Pcwstr, lpexistingfilename: Pcwstr, lpsecurityattributes: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCreateHardLinkW(lpfilename.rawAddress.toCPointer<ByteVar>(), lpexistingfilename.rawAddress.toCPointer<ByteVar>(), lpsecurityattributes.rawAddress.toCPointer<ByteVar>())

public actual fun createNamedPipeW(lpname: Pcwstr, dwopenmode: UInt, dwpipemode: UInt, nmaxinstances: UInt, noutbuffersize: UInt, ninbuffersize: UInt, ndefaulttimeout: UInt, lpsecurityattributes: ConstPtr): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysCreateNamedPipeW(lpname.rawAddress.toCPointer<ByteVar>(), dwopenmode, dwpipemode, nmaxinstances, noutbuffersize, ninbuffersize, ndefaulttimeout, lpsecurityattributes.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createPipe(hreadpipe: MutPtr, hwritepipe: MutPtr, lppipeattributes: ConstPtr, nsize: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCreatePipe(hreadpipe.rawAddress.toCPointer<ByteVar>(), hwritepipe.rawAddress.toCPointer<ByteVar>(), lppipeattributes.rawAddress.toCPointer<ByteVar>(), nsize)

public actual fun createProcessW(lpapplicationname: Pcwstr, lpcommandline: Pwstr, lpprocessattributes: ConstPtr, lpthreadattributes: ConstPtr, binherithandles: Bool, dwcreationflags: UInt, lpenvironment: ConstPtr, lpcurrentdirectory: Pcwstr, lpstartupinfo: ConstPtr, lpprocessinformation: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCreateProcessW(lpapplicationname.rawAddress.toCPointer<ByteVar>(), lpcommandline.rawAddress.toCPointer<ByteVar>(), lpprocessattributes.rawAddress.toCPointer<ByteVar>(), lpthreadattributes.rawAddress.toCPointer<ByteVar>(), binherithandles, dwcreationflags, lpenvironment.rawAddress.toCPointer<ByteVar>(), lpcurrentdirectory.rawAddress.toCPointer<ByteVar>(), lpstartupinfo.rawAddress.toCPointer<ByteVar>(), lpprocessinformation.rawAddress.toCPointer<ByteVar>())

public actual fun createSymbolicLinkW(lpsymlinkfilename: Pcwstr, lptargetfilename: Pcwstr, dwflags: UInt): Boolean =
    rustlangStdSysPalWindowsCWindowssysWindowsSysCreateSymbolicLinkW(lpsymlinkfilename.rawAddress.toCPointer<ByteVar>(), lptargetfilename.rawAddress.toCPointer<ByteVar>(), dwflags)

public actual fun createThread(lpthreadattributes: ConstPtr, dwstacksize: ULong, lpstartaddress: LpthreadStartRoutine, lpparameter: ConstPtr, dwcreationflags: UInt, lpthreadid: MutPtr): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysCreateThread(lpthreadattributes.rawAddress.toCPointer<ByteVar>(), dwstacksize, 0L.toCPointer<ByteVar>(), lpparameter.rawAddress.toCPointer<ByteVar>(), dwcreationflags, lpthreadid.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun createWaitableTimerExW(lptimerattributes: ConstPtr, lptimername: Pcwstr, dwflags: UInt, dwdesiredaccess: UInt): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysCreateWaitableTimerExW(lptimerattributes.rawAddress.toCPointer<ByteVar>(), lptimername.rawAddress.toCPointer<ByteVar>(), dwflags, dwdesiredaccess)?.toLong() ?: 0L)

public actual fun deleteFileW(lpfilename: Pcwstr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteFileW(lpfilename.rawAddress.toCPointer<ByteVar>())

public actual fun deleteProcThreadAttributeList(lpattributelist: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysDeleteProcThreadAttributeList(lpattributelist.rawAddress.toCPointer<ByteVar>())
}

public actual fun deviceIoControl(hdevice: Handle, dwiocontrolcode: UInt, lpinbuffer: ConstPtr, ninbuffersize: UInt, lpoutbuffer: MutPtr, noutbuffersize: UInt, lpbytesreturned: MutPtr, lpoverlapped: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysDeviceIoControl(hdevice.rawAddress.toCPointer<ByteVar>(), dwiocontrolcode, lpinbuffer.rawAddress.toCPointer<ByteVar>(), ninbuffersize, lpoutbuffer.rawAddress.toCPointer<ByteVar>(), noutbuffersize, lpbytesreturned.rawAddress.toCPointer<ByteVar>(), lpoverlapped.rawAddress.toCPointer<ByteVar>())

public actual fun duplicateHandle(hsourceprocesshandle: Handle, hsourcehandle: Handle, htargetprocesshandle: Handle, lptargethandle: MutPtr, dwdesiredaccess: UInt, binherithandle: Bool, dwoptions: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysDuplicateHandle(hsourceprocesshandle.rawAddress.toCPointer<ByteVar>(), hsourcehandle.rawAddress.toCPointer<ByteVar>(), htargetprocesshandle.rawAddress.toCPointer<ByteVar>(), lptargethandle.rawAddress.toCPointer<ByteVar>(), dwdesiredaccess, binherithandle, dwoptions)

public actual fun exitProcess(uexitcode: UInt): Nothing {
    rustlangStdSysPalWindowsCWindowssysWindowsSysExitProcess(uexitcode)
    error("Unreachable noreturn C function exitProcess")
}

public actual fun findClose(hfindfile: Handle): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysFindClose(hfindfile.rawAddress.toCPointer<ByteVar>())

public actual fun findFirstFileExW(lpfilename: Pcwstr, finfolevelid: FindexInfoLevels, lpfindfiledata: MutPtr, fsearchop: FindexSearchOps, lpsearchfilter: ConstPtr, dwadditionalflags: UInt): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysFindFirstFileExW(lpfilename.rawAddress.toCPointer<ByteVar>(), finfolevelid, lpfindfiledata.rawAddress.toCPointer<ByteVar>(), fsearchop, lpsearchfilter.rawAddress.toCPointer<ByteVar>(), dwadditionalflags)?.toLong() ?: 0L)

public actual fun findNextFileW(hfindfile: Handle, lpfindfiledata: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysFindNextFileW(hfindfile.rawAddress.toCPointer<ByteVar>(), lpfindfiledata.rawAddress.toCPointer<ByteVar>())

public actual fun flsAlloc(lpcallback: PflsCallbackFunction): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysFlsAlloc(0L.toCPointer<ByteVar>())

public actual fun flsFree(dwflsindex: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysFlsFree(dwflsindex)

public actual fun flsGetValue(dwflsindex: UInt): MutPtr =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysFlsGetValue(dwflsindex)?.toLong() ?: 0L)

public actual fun flsSetValue(dwflsindex: UInt, lpflsdata: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysFlsSetValue(dwflsindex, lpflsdata.rawAddress.toCPointer<ByteVar>())

public actual fun flushFileBuffers(hfile: Handle): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysFlushFileBuffers(hfile.rawAddress.toCPointer<ByteVar>())

public actual fun formatMessageW(dwflags: UInt, lpsource: ConstPtr, dwmessageid: UInt, dwlanguageid: UInt, lpbuffer: Pcwstr, nsize: UInt, arguments: ConstPtr): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysFormatMessageW(dwflags, lpsource.rawAddress.toCPointer<ByteVar>(), dwmessageid, dwlanguageid, lpbuffer.rawAddress.toCPointer<ByteVar>(), nsize, arguments.rawAddress.toCPointer<ByteVar>())

public actual fun freeEnvironmentStringsW(penv: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysFreeEnvironmentStringsW(penv.rawAddress.toCPointer<ByteVar>())

public actual fun getActiveProcessorCount(groupnumber: UShort): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetActiveProcessorCount(groupnumber)

public actual fun getCommandLineW(): Pwstr =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysGetCommandLineW()?.toLong() ?: 0L)

public actual fun getConsoleMode(hconsolehandle: Handle, lpmode: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleMode(hconsolehandle.rawAddress.toCPointer<ByteVar>(), lpmode.rawAddress.toCPointer<ByteVar>())

public actual fun getConsoleOutputCP(): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetConsoleOutputCP()

public actual fun getCurrentDirectoryW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentDirectoryW(nbufferlength, lpbuffer.rawAddress.toCPointer<ByteVar>())

public actual fun getCurrentProcess(): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcess()?.toLong() ?: 0L)

public actual fun getCurrentProcessId(): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentProcessId()

public actual fun getCurrentThread(): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThread()?.toLong() ?: 0L)

public actual fun getCurrentThreadId(): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetCurrentThreadId()

public actual fun getEnvironmentStringsW(): Lpwch =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentStringsW()?.toLong() ?: 0L)

public actual fun getEnvironmentVariableW(lpname: Pcwstr, lpbuffer: Pwstr, nsize: UInt): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetEnvironmentVariableW(lpname.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nsize)

public actual fun getExitCodeProcess(hprocess: Handle, lpexitcode: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetExitCodeProcess(hprocess.rawAddress.toCPointer<ByteVar>(), lpexitcode.rawAddress.toCPointer<ByteVar>())

public actual fun getFileAttributesW(lpfilename: Pcwstr): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileAttributesW(lpfilename.rawAddress.toCPointer<ByteVar>())

public actual fun getFileInformationByHandle(hfile: Handle, lpfileinformation: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandle(hfile.rawAddress.toCPointer<ByteVar>(), lpfileinformation.rawAddress.toCPointer<ByteVar>())

public actual fun getFileInformationByHandleEx(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: MutPtr, dwbuffersize: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileInformationByHandleEx(hfile.rawAddress.toCPointer<ByteVar>(), fileinformationclass, lpfileinformation.rawAddress.toCPointer<ByteVar>(), dwbuffersize)

public actual fun getFileSizeEx(hfile: Handle, lpfilesize: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileSizeEx(hfile.rawAddress.toCPointer<ByteVar>(), lpfilesize.rawAddress.toCPointer<ByteVar>())

public actual fun getFileType(hfile: Handle): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetFileType(hfile.rawAddress.toCPointer<ByteVar>())

public actual fun getFinalPathNameByHandleW(hfile: Handle, lpszfilepath: Pwstr, cchfilepath: UInt, dwflags: UInt): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetFinalPathNameByHandleW(hfile.rawAddress.toCPointer<ByteVar>(), lpszfilepath.rawAddress.toCPointer<ByteVar>(), cchfilepath, dwflags)

public actual fun getFullPathNameW(lpfilename: Pcwstr, nbufferlength: UInt, lpbuffer: Pwstr, lpfilepart: MutPtr): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetFullPathNameW(lpfilename.rawAddress.toCPointer<ByteVar>(), nbufferlength, lpbuffer.rawAddress.toCPointer<ByteVar>(), lpfilepart.rawAddress.toCPointer<ByteVar>())

public actual fun getHostNameW(name: Pwstr, namelen: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetHostNameW(name.rawAddress.toCPointer<ByteVar>(), namelen)

public actual fun getLastError(): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetLastError()

public actual fun getLogicalDrives(): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetLogicalDrives()

public actual fun getModuleFileNameW(hmodule: Hmodule, lpfilename: Pwstr, nsize: UInt): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleFileNameW(hmodule.rawAddress.toCPointer<ByteVar>(), lpfilename.rawAddress.toCPointer<ByteVar>(), nsize)

public actual fun getModuleHandleA(lpmodulename: Pcstr): Hmodule =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleA(lpmodulename.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getModuleHandleExW(dwflags: UInt, lpmodulename: Pcwstr, phmodule: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleExW(dwflags, lpmodulename.rawAddress.toCPointer<ByteVar>(), phmodule.rawAddress.toCPointer<ByteVar>())

public actual fun getModuleHandleW(lpmodulename: Pcwstr): Hmodule =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysGetModuleHandleW(lpmodulename.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getOverlappedResult(hfile: Handle, lpoverlapped: ConstPtr, lpnumberofbytestransferred: MutPtr, bwait: Bool): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetOverlappedResult(hfile.rawAddress.toCPointer<ByteVar>(), lpoverlapped.rawAddress.toCPointer<ByteVar>(), lpnumberofbytestransferred.rawAddress.toCPointer<ByteVar>(), bwait)

public actual fun getProcAddress(hmodule: Hmodule, lpprocname: Pcstr): Farproc =
    null

public actual fun getProcessId(process: Handle): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetProcessId(process.rawAddress.toCPointer<ByteVar>())

public actual fun getStdHandle(nstdhandle: UInt): Handle =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysGetStdHandle(nstdhandle)?.toLong() ?: 0L)

public actual fun getSystemDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemDirectoryW(lpbuffer.rawAddress.toCPointer<ByteVar>(), usize)

public actual fun getSystemInfo(lpsysteminfo: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemInfo(lpsysteminfo.rawAddress.toCPointer<ByteVar>())
}

public actual fun getSystemTimeAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimeAsFileTime(lpsystemtimeasfiletime.rawAddress.toCPointer<ByteVar>())
}

public actual fun getSystemTimePreciseAsFileTime(lpsystemtimeasfiletime: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetSystemTimePreciseAsFileTime(lpsystemtimeasfiletime.rawAddress.toCPointer<ByteVar>())
}

public actual fun getTempPathW(nbufferlength: UInt, lpbuffer: Pwstr): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetTempPathW(nbufferlength, lpbuffer.rawAddress.toCPointer<ByteVar>())

public actual fun getUserProfileDirectoryW(htoken: Handle, lpprofiledir: Pwstr, lpcchsize: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetUserProfileDirectoryW(htoken.rawAddress.toCPointer<ByteVar>(), lpprofiledir.rawAddress.toCPointer<ByteVar>(), lpcchsize.rawAddress.toCPointer<ByteVar>())

public actual fun getWindowsDirectoryW(lpbuffer: Pwstr, usize: UInt): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetWindowsDirectoryW(lpbuffer.rawAddress.toCPointer<ByteVar>(), usize)

public actual fun initOnceBeginInitialize(lpinitonce: LpinitOnce, dwflags: UInt, fpending: MutPtr, lpcontext: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceBeginInitialize(lpinitonce.rawAddress.toCPointer<ByteVar>(), dwflags, fpending.rawAddress.toCPointer<ByteVar>(), lpcontext.rawAddress.toCPointer<ByteVar>())

public actual fun initOnceComplete(lpinitonce: LpinitOnce, dwflags: UInt, lpcontext: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysInitOnceComplete(lpinitonce.rawAddress.toCPointer<ByteVar>(), dwflags, lpcontext.rawAddress.toCPointer<ByteVar>())

public actual fun initializeProcThreadAttributeList(lpattributelist: MutPtr, dwattributecount: UInt, dwflags: UInt, lpsize: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysInitializeProcThreadAttributeList(lpattributelist.rawAddress.toCPointer<ByteVar>(), dwattributecount, dwflags, lpsize.rawAddress.toCPointer<ByteVar>())

public actual fun isThreadAFiber(): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysIsThreadAFiber()

public actual fun loadLibraryExA(lplibfilename: Pcstr, hfile: Handle, dwflags: UInt): Hmodule =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysLoadLibraryExA(lplibfilename.rawAddress.toCPointer<ByteVar>(), hfile.rawAddress.toCPointer<ByteVar>(), dwflags)?.toLong() ?: 0L)

public actual fun localFree(hmem: Hlocal): Hlocal =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysLocalFree(hmem.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun lockFileEx(hfile: Handle, dwflags: UInt, dwreserved: UInt, nnumberofbytestolocklow: UInt, nnumberofbytestolockhigh: UInt, lpoverlapped: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysLockFileEx(hfile.rawAddress.toCPointer<ByteVar>(), dwflags, dwreserved, nnumberofbytestolocklow, nnumberofbytestolockhigh, lpoverlapped.rawAddress.toCPointer<ByteVar>())

public actual fun moveFileExW(lpexistingfilename: Pcwstr, lpnewfilename: Pcwstr, dwflags: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysMoveFileExW(lpexistingfilename.rawAddress.toCPointer<ByteVar>(), lpnewfilename.rawAddress.toCPointer<ByteVar>(), dwflags)

public actual fun multiByteToWideChar(codepage: UInt, dwflags: UInt, lpmultibytestr: ConstPtr, cbmultibyte: Int, lpwidecharstr: Pwstr, cchwidechar: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysMultiByteToWideChar(codepage, dwflags, lpmultibytestr.rawAddress.toCPointer<ByteVar>(), cbmultibyte, lpwidecharstr.rawAddress.toCPointer<ByteVar>(), cchwidechar)

public actual fun ntCreateFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, allocationsize: ConstPtr, fileattributes: UInt, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, eabuffer: ConstPtr, ealength: UInt): Ntstatus =
    rustlangStdSysPalWindowsCWindowssysWindowsSysNtCreateFile(filehandle.rawAddress.toCPointer<ByteVar>(), desiredaccess, objectattributes.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), allocationsize.rawAddress.toCPointer<ByteVar>(), fileattributes, shareaccess, createdisposition, createoptions, eabuffer.rawAddress.toCPointer<ByteVar>(), ealength)

public actual fun ntOpenFile(filehandle: MutPtr, desiredaccess: AccessMask, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, openoptions: UInt): Ntstatus =
    rustlangStdSysPalWindowsCWindowssysWindowsSysNtOpenFile(filehandle.rawAddress.toCPointer<ByteVar>(), desiredaccess, objectattributes.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), shareaccess, openoptions)

public actual fun ntQueryVolumeInformationFile(filehandle: Handle, iostatusblock: MutPtr, fsinformation: MutPtr, length: UInt, fsinformationclass: FsInformationClass): Ntstatus =
    rustlangStdSysPalWindowsCWindowssysWindowsSysNtQueryVolumeInformationFile(filehandle.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), fsinformation.rawAddress.toCPointer<ByteVar>(), length, fsinformationclass)

public actual fun ntReadFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: MutPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    rustlangStdSysPalWindowsCWindowssysWindowsSysNtReadFile(filehandle.rawAddress.toCPointer<ByteVar>(), event.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), apccontext.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), buffer.rawAddress.toCPointer<ByteVar>(), length, byteoffset.rawAddress.toCPointer<ByteVar>(), key.rawAddress.toCPointer<ByteVar>())

public actual fun ntSetInformationFile(filehandle: Handle, iostatusblock: MutPtr, fileinformation: ConstPtr, length: UInt, fileinformationclass: FileInformationClass): Ntstatus =
    rustlangStdSysPalWindowsCWindowssysWindowsSysNtSetInformationFile(filehandle.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), fileinformation.rawAddress.toCPointer<ByteVar>(), length, fileinformationclass)

public actual fun ntWriteFile(filehandle: Handle, event: Handle, apcroutine: PioApcRoutine, apccontext: ConstPtr, iostatusblock: MutPtr, buffer: ConstPtr, length: UInt, byteoffset: ConstPtr, key: ConstPtr): Ntstatus =
    rustlangStdSysPalWindowsCWindowssysWindowsSysNtWriteFile(filehandle.rawAddress.toCPointer<ByteVar>(), event.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), apccontext.rawAddress.toCPointer<ByteVar>(), iostatusblock.rawAddress.toCPointer<ByteVar>(), buffer.rawAddress.toCPointer<ByteVar>(), length, byteoffset.rawAddress.toCPointer<ByteVar>(), key.rawAddress.toCPointer<ByteVar>())

public actual fun openProcessToken(processhandle: Handle, desiredaccess: UInt, tokenhandle: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysOpenProcessToken(processhandle.rawAddress.toCPointer<ByteVar>(), desiredaccess, tokenhandle.rawAddress.toCPointer<ByteVar>())

public actual fun queryDosDeviceW(lpdevicename: Pcwstr, lptargetpath: Pwstr, ucchmax: UInt): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysQueryDosDeviceW(lpdevicename.rawAddress.toCPointer<ByteVar>(), lptargetpath.rawAddress.toCPointer<ByteVar>(), ucchmax)

public actual fun queryPerformanceCounter(lpperformancecount: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceCounter(lpperformancecount.rawAddress.toCPointer<ByteVar>())

public actual fun queryPerformanceFrequency(lpfrequency: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysQueryPerformanceFrequency(lpfrequency.rawAddress.toCPointer<ByteVar>())

public actual fun readConsoleW(hconsoleinput: Handle, lpbuffer: MutPtr, nnumberofcharstoread: UInt, lpnumberofcharsread: MutPtr, pinputcontrol: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysReadConsoleW(hconsoleinput.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofcharstoread, lpnumberofcharsread.rawAddress.toCPointer<ByteVar>(), pinputcontrol.rawAddress.toCPointer<ByteVar>())

public actual fun readFile(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpnumberofbytesread: MutPtr, lpoverlapped: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysReadFile(hfile.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofbytestoread, lpnumberofbytesread.rawAddress.toCPointer<ByteVar>(), lpoverlapped.rawAddress.toCPointer<ByteVar>())

public actual fun readFileEx(hfile: Handle, lpbuffer: MutPtr, nnumberofbytestoread: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysReadFileEx(hfile.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofbytestoread, lpoverlapped.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun releaseSRWLockExclusive(srwlock: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockExclusive(srwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun releaseSRWLockShared(srwlock: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysReleaseSRWLockShared(srwlock.rawAddress.toCPointer<ByteVar>())
}

public actual fun removeDirectoryW(lppathname: Pcwstr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysRemoveDirectoryW(lppathname.rawAddress.toCPointer<ByteVar>())

public actual fun rtlGenRandom(randombuffer: MutPtr, randombufferlength: UInt): Boolean =
    rustlangStdSysPalWindowsCWindowssysWindowsSysRtlGenRandom(randombuffer.rawAddress.toCPointer<ByteVar>(), randombufferlength)

public actual fun rtlNtStatusToDosError(status: Ntstatus): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysRtlNtStatusToDosError(status)

public actual fun setCurrentDirectoryW(lppathname: Pcwstr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetCurrentDirectoryW(lppathname.rawAddress.toCPointer<ByteVar>())

public actual fun setEnvironmentVariableW(lpname: Pcwstr, lpvalue: Pcwstr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetEnvironmentVariableW(lpname.rawAddress.toCPointer<ByteVar>(), lpvalue.rawAddress.toCPointer<ByteVar>())

public actual fun setFileAttributesW(lpfilename: Pcwstr, dwfileattributes: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileAttributesW(lpfilename.rawAddress.toCPointer<ByteVar>(), dwfileattributes)

public actual fun setFileInformationByHandle(hfile: Handle, fileinformationclass: FileInfoByHandleClass, lpfileinformation: ConstPtr, dwbuffersize: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileInformationByHandle(hfile.rawAddress.toCPointer<ByteVar>(), fileinformationclass, lpfileinformation.rawAddress.toCPointer<ByteVar>(), dwbuffersize)

public actual fun setFilePointerEx(hfile: Handle, lidistancetomove: Long, lpnewfilepointer: MutPtr, dwmovemethod: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetFilePointerEx(hfile.rawAddress.toCPointer<ByteVar>(), lidistancetomove, lpnewfilepointer.rawAddress.toCPointer<ByteVar>(), dwmovemethod)

public actual fun setFileTime(hfile: Handle, lpcreationtime: ConstPtr, lplastaccesstime: ConstPtr, lplastwritetime: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetFileTime(hfile.rawAddress.toCPointer<ByteVar>(), lpcreationtime.rawAddress.toCPointer<ByteVar>(), lplastaccesstime.rawAddress.toCPointer<ByteVar>(), lplastwritetime.rawAddress.toCPointer<ByteVar>())

public actual fun setHandleInformation(hobject: Handle, dwmask: UInt, dwflags: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetHandleInformation(hobject.rawAddress.toCPointer<ByteVar>(), dwmask, dwflags)

public actual fun setLastError(dwerrcode: UInt) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetLastError(dwerrcode)
}

public actual fun setStdHandle(nstdhandle: UInt, hhandle: Handle): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetStdHandle(nstdhandle, hhandle.rawAddress.toCPointer<ByteVar>())

public actual fun setThreadStackGuarantee(stacksizeinbytes: MutPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetThreadStackGuarantee(stacksizeinbytes.rawAddress.toCPointer<ByteVar>())

public actual fun setWaitableTimer(htimer: Handle, lpduetime: ConstPtr, lperiod: Int, pfncompletionroutine: Ptimerapcroutine, lpargtocompletionroutine: ConstPtr, fresume: Bool): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetWaitableTimer(htimer.rawAddress.toCPointer<ByteVar>(), lpduetime.rawAddress.toCPointer<ByteVar>(), lperiod, 0L.toCPointer<ByteVar>(), lpargtocompletionroutine.rawAddress.toCPointer<ByteVar>(), fresume)

public actual fun sleep(dwmilliseconds: UInt) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysSleep(dwmilliseconds)
}

public actual fun sleepConditionVariableSRW(conditionvariable: MutPtr, srwlock: MutPtr, dwmilliseconds: UInt, flags: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSleepConditionVariableSRW(conditionvariable.rawAddress.toCPointer<ByteVar>(), srwlock.rawAddress.toCPointer<ByteVar>(), dwmilliseconds, flags)

public actual fun sleepEx(dwmilliseconds: UInt, balertable: Bool): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSleepEx(dwmilliseconds, balertable)

public actual fun switchToThread(): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSwitchToThread()

public actual fun terminateProcess(hprocess: Handle, uexitcode: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysTerminateProcess(hprocess.rawAddress.toCPointer<ByteVar>(), uexitcode)

public actual fun tlsAlloc(): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysTlsAlloc()

public actual fun tlsFree(dwtlsindex: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysTlsFree(dwtlsindex)

public actual fun tlsGetValue(dwtlsindex: UInt): MutPtr =
    MutPtr(rustlangStdSysPalWindowsCWindowssysWindowsSysTlsGetValue(dwtlsindex)?.toLong() ?: 0L)

public actual fun tlsSetValue(dwtlsindex: UInt, lptlsvalue: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysTlsSetValue(dwtlsindex, lptlsvalue.rawAddress.toCPointer<ByteVar>())

public actual fun tryAcquireSRWLockExclusive(srwlock: MutPtr): Boolean =
    rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockExclusive(srwlock.rawAddress.toCPointer<ByteVar>())

public actual fun tryAcquireSRWLockShared(srwlock: MutPtr): Boolean =
    rustlangStdSysPalWindowsCWindowssysWindowsSysTryAcquireSRWLockShared(srwlock.rawAddress.toCPointer<ByteVar>())

public actual fun unlockFile(hfile: Handle, dwfileoffsetlow: UInt, dwfileoffsethigh: UInt, nnumberofbytestounlocklow: UInt, nnumberofbytestounlockhigh: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysUnlockFile(hfile.rawAddress.toCPointer<ByteVar>(), dwfileoffsetlow, dwfileoffsethigh, nnumberofbytestounlocklow, nnumberofbytestounlockhigh)

public actual fun updateProcThreadAttribute(lpattributelist: MutPtr, dwflags: UInt, attribute: ULong, lpvalue: ConstPtr, cbsize: ULong, lppreviousvalue: MutPtr, lpreturnsize: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysUpdateProcThreadAttribute(lpattributelist.rawAddress.toCPointer<ByteVar>(), dwflags, attribute, lpvalue.rawAddress.toCPointer<ByteVar>(), cbsize, lppreviousvalue.rawAddress.toCPointer<ByteVar>(), lpreturnsize.rawAddress.toCPointer<ByteVar>())

public actual fun wSACleanup(): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWSACleanup()

public actual fun wSADuplicateSocketW(s: Socket, dwprocessid: UInt, lpprotocolinfo: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWSADuplicateSocketW(s, dwprocessid, lpprotocolinfo.rawAddress.toCPointer<ByteVar>())

public actual fun wSAGetLastError(): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWSAGetLastError()

public actual fun wSARecv(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytesrecvd: MutPtr, lpflags: MutPtr, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWSARecv(s, lpbuffers.rawAddress.toCPointer<ByteVar>(), dwbuffercount, lpnumberofbytesrecvd.rawAddress.toCPointer<ByteVar>(), lpflags.rawAddress.toCPointer<ByteVar>(), lpoverlapped.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun wSASend(s: Socket, lpbuffers: ConstPtr, dwbuffercount: UInt, lpnumberofbytessent: MutPtr, dwflags: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpwsaoverlappedCompletionRoutine): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWSASend(s, lpbuffers.rawAddress.toCPointer<ByteVar>(), dwbuffercount, lpnumberofbytessent.rawAddress.toCPointer<ByteVar>(), dwflags, lpoverlapped.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun wSASocketW(af: Int, type: Int, protocol: Int, lpprotocolinfo: ConstPtr, g: Group, dwflags: UInt): Socket =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWSASocketW(af, type, protocol, lpprotocolinfo.rawAddress.toCPointer<ByteVar>(), g, dwflags)

public actual fun wSAStartup(wversionrequested: UShort, lpwsadata: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWSAStartup(wversionrequested, lpwsadata.rawAddress.toCPointer<ByteVar>())

public actual fun waitForMultipleObjects(ncount: UInt, lphandles: ConstPtr, bwaitall: Bool, dwmilliseconds: UInt): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForMultipleObjects(ncount, lphandles.rawAddress.toCPointer<ByteVar>(), bwaitall, dwmilliseconds)

public actual fun waitForSingleObject(hhandle: Handle, dwmilliseconds: UInt): UInt =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWaitForSingleObject(hhandle.rawAddress.toCPointer<ByteVar>(), dwmilliseconds)

public actual fun waitOnAddress(address: ConstPtr, compareaddress: ConstPtr, addresssize: ULong, dwmilliseconds: UInt): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWaitOnAddress(address.rawAddress.toCPointer<ByteVar>(), compareaddress.rawAddress.toCPointer<ByteVar>(), addresssize, dwmilliseconds)

public actual fun wakeAllConditionVariable(conditionvariable: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysWakeAllConditionVariable(conditionvariable.rawAddress.toCPointer<ByteVar>())
}

public actual fun wakeByAddressAll(address: ConstPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressAll(address.rawAddress.toCPointer<ByteVar>())
}

public actual fun wakeByAddressSingle(address: ConstPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysWakeByAddressSingle(address.rawAddress.toCPointer<ByteVar>())
}

public actual fun wakeConditionVariable(conditionvariable: MutPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysWakeConditionVariable(conditionvariable.rawAddress.toCPointer<ByteVar>())
}

public actual fun wideCharToMultiByte(codepage: UInt, dwflags: UInt, lpwidecharstr: ConstPtr, cchwidechar: Int, lpmultibytestr: Pstr, cbmultibyte: Int, lpdefaultchar: ConstPtr, lpuseddefaultchar: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWideCharToMultiByte(codepage, dwflags, lpwidecharstr.rawAddress.toCPointer<ByteVar>(), cchwidechar, lpmultibytestr.rawAddress.toCPointer<ByteVar>(), cbmultibyte, lpdefaultchar.rawAddress.toCPointer<ByteVar>(), lpuseddefaultchar.rawAddress.toCPointer<ByteVar>())

public actual fun writeConsoleW(hconsoleoutput: Handle, lpbuffer: ConstPtr, nnumberofcharstowrite: UInt, lpnumberofcharswritten: MutPtr, lpreserved: ConstPtr): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWriteConsoleW(hconsoleoutput.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofcharstowrite, lpnumberofcharswritten.rawAddress.toCPointer<ByteVar>(), lpreserved.rawAddress.toCPointer<ByteVar>())

public actual fun writeFileEx(hfile: Handle, lpbuffer: ConstPtr, nnumberofbytestowrite: UInt, lpoverlapped: MutPtr, lpcompletionroutine: LpoverlappedCompletionRoutine): Bool =
    rustlangStdSysPalWindowsCWindowssysWindowsSysWriteFileEx(hfile.rawAddress.toCPointer<ByteVar>(), lpbuffer.rawAddress.toCPointer<ByteVar>(), nnumberofbytestowrite, lpoverlapped.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    rustlangStdSysPalWindowsCWindowssysWindowsSysAccept(s, addr.rawAddress.toCPointer<ByteVar>(), addrlen.rawAddress.toCPointer<ByteVar>())

public actual fun bind(s: Socket, name: ConstPtr, namelen: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysBind(s, name.rawAddress.toCPointer<ByteVar>(), namelen)

public actual fun closesocket(s: Socket): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysClosesocket(s)

public actual fun connect(s: Socket, name: ConstPtr, namelen: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysConnect(s, name.rawAddress.toCPointer<ByteVar>(), namelen)

public actual fun freeaddrinfo(paddrinfo: ConstPtr) {
    rustlangStdSysPalWindowsCWindowssysWindowsSysFreeaddrinfo(paddrinfo.rawAddress.toCPointer<ByteVar>())
}

public actual fun getaddrinfo(pnodename: Pcstr, pservicename: Pcstr, phints: ConstPtr, ppresult: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetaddrinfo(pnodename.rawAddress.toCPointer<ByteVar>(), pservicename.rawAddress.toCPointer<ByteVar>(), phints.rawAddress.toCPointer<ByteVar>(), ppresult.rawAddress.toCPointer<ByteVar>())

public actual fun getpeername(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetpeername(s, name.rawAddress.toCPointer<ByteVar>(), namelen.rawAddress.toCPointer<ByteVar>())

public actual fun getsockname(s: Socket, name: MutPtr, namelen: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockname(s, name.rawAddress.toCPointer<ByteVar>(), namelen.rawAddress.toCPointer<ByteVar>())

public actual fun getsockopt(s: Socket, level: Int, optname: Int, optval: MutPtr, optlen: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysGetsockopt(s, level, optname, optval.rawAddress.toCPointer<ByteVar>(), optlen.rawAddress.toCPointer<ByteVar>())

public actual fun ioctlsocket(s: Socket, cmd: Int, argp: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysIoctlsocket(s, cmd, argp.rawAddress.toCPointer<ByteVar>())

public actual fun listen(s: Socket, backlog: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysListen(s, backlog)

public actual fun lstrlenW(lpstring: Pcwstr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysLstrlenW(lpstring.rawAddress.toCPointer<ByteVar>())

public actual fun recv(s: Socket, buf: MutPtr, len: Int, flags: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysRecv(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun recvfrom(s: Socket, buf: MutPtr, len: Int, flags: Int, from: MutPtr, fromlen: MutPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysRecvfrom(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags, from.rawAddress.toCPointer<ByteVar>(), fromlen.rawAddress.toCPointer<ByteVar>())

public actual fun select(nfds: Int, readfds: MutPtr, writefds: MutPtr, exceptfds: MutPtr, timeout: ConstPtr): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSelect(nfds, readfds.rawAddress.toCPointer<ByteVar>(), writefds.rawAddress.toCPointer<ByteVar>(), exceptfds.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>())

public actual fun send(s: Socket, buf: ConstPtr, len: Int, flags: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSend(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun sendto(s: Socket, buf: ConstPtr, len: Int, flags: Int, to: ConstPtr, tolen: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSendto(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags, to.rawAddress.toCPointer<ByteVar>(), tolen)

public actual fun setsockopt(s: Socket, level: Int, optname: Int, optval: ConstPtr, optlen: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysSetsockopt(s, level, optname, optval.rawAddress.toCPointer<ByteVar>(), optlen)

public actual fun shutdown(s: Socket, how: Int): Int =
    rustlangStdSysPalWindowsCWindowssysWindowsSysShutdown(s, how)
