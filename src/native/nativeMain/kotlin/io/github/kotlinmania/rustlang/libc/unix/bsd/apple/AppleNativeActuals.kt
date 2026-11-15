@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd.apple

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAioCancel
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAioError
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAioFsync
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAioRead
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAioReturn
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAioSuspend
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAioWrite
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAsctime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleAsctimeR
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleBacktrace
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleBacktraceAsync
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleBacktraceFromFp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleBacktraceImageOffsets
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleBacktraceSymbols
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleBacktraceSymbolsFd
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleBasename
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleBrk
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleCCRandomGenerateBytes
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleChflags
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleClockGetres
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleClockGettime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleClockSettime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleClonefile
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleClonefileat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleConnectx
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleCopyfile
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleCopyfileStateAlloc
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleCopyfileStateFree
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleCopyfileStateGet
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleCopyfileStateSet
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleCtime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleCtimeR
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleDirfd
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleDirname
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleDisconnectx
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleDuplocale
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleDyldGetImageHeader
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleDyldGetImageName
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleDyldImageCount
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleEndutxent
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleError
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleExecvP
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFchflags
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFclonefileat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFcopyfile
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFgetattrlist
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFgetxattr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFlistxattr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFmount
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleForkpty
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFreadlink
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFreelocale
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFremovexattr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFsetattrlist
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFsetxattr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFstatfs
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFtok
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleFutimens
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetMACHTASKSELF
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetVMPAGESIZE
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetattrlist
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetattrlistat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetattrlistbulk
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetdate
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetdomainname
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetentropy
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetfsstat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetgrouplist
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGethostid
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGethostuuid
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetmntinfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetnameinfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetpriority
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGettimeofday
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetutxent
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetutxid
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetutxline
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleGetxattr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleHostProcessorInfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleHostStatistics
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleHostStatistics64
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleIconv
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleIconvClose
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleIconvOpen
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleInitgroups
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleKevent
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleKevent64
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleLchflags
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleLchmod
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleLioListio
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleListxattr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleLocaleconvL
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleLoginTty
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleLutimes
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMachAbsoluteTime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMachErrorString
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMachHostSelf
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMachThreadSelf
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMachTimebaseInfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMachVmMap
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocDefaultZone
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocGoodSize
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocPrintf
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocSize
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneCalloc
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneCheck
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneFree
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneFromPtr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneLog
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneMalloc
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZonePrint
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZonePrintPtrInfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneRealloc
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneStatistics
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMallocZoneValloc
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMemmem
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMemsetPattern16
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMemsetPattern4
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMemsetPattern8
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMemsetS
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMincore
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMkfifoat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMknodat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMount
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleMprotect
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleNewlocale
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleNsgetArgc
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleNsgetArgv
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleNsgetEnviron
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleNsgetExecutablePath
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleNsgetProgname
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleNtpAdjtime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleNtpGettime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOpenpty
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsLogCreate
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsLogTypeEnabled
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsSignpostEnabled
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsSignpostIdGenerate
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsSyncWaitOnAddress
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsSyncWakeByAddressAll
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsSyncWakeByAddressAny
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsUnfairLockAssertOwner
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsUnfairLockLock
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsUnfairLockTrylock
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleOsUnfairLockUnlock
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawn
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnFileActionsInit
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrDestroy
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrGetflags
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrGetpgroup
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrGetsigmask
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrInit
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrSetflags
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrSetpgroup
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnattrSetsigmask
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePosixSpawnp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePreadv
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcKmsgbuf
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcLibversion
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcListallpids
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcListchildpids
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcListpgrppids
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcListpids
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcName
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcPidRusage
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcPidfdinfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcPidfileportinfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcPidinfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcPidpath
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcRegionfilename
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcSetCsm
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcSetNoSmt
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcSetthreadCsm
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleProcSetthreadNoSmt
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadAttrGetdetachstate
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadAttrGetscope
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadAttrGetstackaddr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadAttrSetscope
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadAttrSetstackaddr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadCpuNumberNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadFromMachThreadNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadGetStackaddrNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadGetStacksizeNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadGetnameNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadJitWriteProtectNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadMachThreadNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadMainNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadSetnameNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePthreadThreadidNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePtrace
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePututxline
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdApplePwritev
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleQsortR
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleQuerylocale
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleQuotactl
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleRemovexattr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleRenameatxNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleRenamexNp
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSbrk
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSemctl
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSemget
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSemop
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSendfile
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSetMACHTASKSELF
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSetattrlist
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSetattrlistat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSetdomainname
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSetgrent
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSethostid
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSethostname
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSetpriority
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSettimeofday
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSetutxent
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSetxattr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleShmOpen
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleShmat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleShmctl
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleShmdt
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleShmget
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleStatfs
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleStrptime
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleStrtonum
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSysctl
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSysctlbyname
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSysctlnametomib
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleTaskCreate
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleTaskForPid
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleTaskInfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleTaskSetInfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleTaskTerminate
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleTaskThreads
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleThreadInfo
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleThreadPolicyGet
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleThreadPolicySet
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleUname
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleUselocale
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleUtimensat
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleUtmpxname
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleVmAllocate
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleVmDeallocate
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.cinterop.rustlangLibcUnixBsdAppleWaitid
import io.github.kotlinmania.rustlang.unix.IntptrT
import io.github.kotlinmania.rustlang.unix.LocaleT
import io.github.kotlinmania.rustlang.unix.PidT
import io.github.kotlinmania.rustlang.unix.SizeT
import io.github.kotlinmania.rustlang.unix.SsizeT
import io.github.kotlinmania.rustlang.unix.bsd.apple.b64.aarch64.BooleanT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual var MACH_TASK_SELF: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT
    get() = rustlangLibcUnixBsdAppleGetMACHTASKSELF()
    set(value) {
        rustlangLibcUnixBsdAppleSetMACHTASKSELF(value)
    }

public actual val VM_PAGE_SIZE: VmSizeT
    get() = rustlangLibcUnixBsdAppleGetVMPAGESIZE()

public actual fun setgrent() {
    rustlangLibcUnixBsdAppleSetgrent()
}

public actual fun aioRead(aiocbp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleAioRead(aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioWrite(aiocbp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleAioWrite(aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioFsync(op: CInt, aiocbp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleAioFsync(op, aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioError(aiocbp: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleAioError(aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioReturn(aiocbp: MutPtr): SsizeT =
    rustlangLibcUnixBsdAppleAioReturn(aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleAioSuspend(aiocbList.rawAddress.toCPointer<ByteVar>(), nitems, timeout.rawAddress.toCPointer<ByteVar>())

public actual fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleAioCancel(fd, aiocbp.rawAddress.toCPointer<ByteVar>())

public actual fun chflags(path: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixBsdAppleChflags(path.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun fchflags(fd: CInt, flags: CUint): CInt =
    rustlangLibcUnixBsdAppleFchflags(fd, flags)

public actual fun lchflags(path: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixBsdAppleLchflags(path.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun lchmod(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixBsdAppleLchmod(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun clockGetres(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleClockGetres(clkId, tp.rawAddress.toCPointer<ByteVar>())

public actual fun clockGettime(clkId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleClockGettime(clkId, tp.rawAddress.toCPointer<ByteVar>())

public actual fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleLioListio(mode, aiocbList.rawAddress.toCPointer<ByteVar>(), nitems, sevp.rawAddress.toCPointer<ByteVar>())

public actual fun dirfd(dirp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleDirfd(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun lutimes(file: ConstPtr, times: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleLutimes(file.rawAddress.toCPointer<ByteVar>(), times.rawAddress.toCPointer<ByteVar>())

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    rustlangLibcUnixBsdAppleGettimeofday(tp.rawAddress.toCPointer<ByteVar>(), tz.rawAddress.toCPointer<ByteVar>())

public actual fun getutxent(): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleGetutxent()?.toLong() ?: 0L)

public actual fun getutxid(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleGetutxid(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getutxline(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleGetutxline(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun pututxline(ut: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdApplePututxline(ut.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun setutxent() {
    rustlangLibcUnixBsdAppleSetutxent()
}

public actual fun endutxent() {
    rustlangLibcUnixBsdAppleEndutxent()
}

public actual fun utmpxname(file: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleUtmpxname(file.rawAddress.toCPointer<ByteVar>())

public actual fun asctime(tm: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleAsctime(tm.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun ctime(clock: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleCtime(clock.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getdate(datestr: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleGetdate(datestr.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strptime(buf: ConstPtr, format: ConstPtr, timeptr: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleStrptime(buf.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), timeptr.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun asctimeR(tm: ConstPtr, result: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleAsctimeR(tm.rawAddress.toCPointer<ByteVar>(), result.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun ctimeR(clock: ConstPtr, result: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleCtimeR(clock.rawAddress.toCPointer<ByteVar>(), result.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt =
    rustlangLibcUnixBsdAppleGetnameinfo(sa.rawAddress.toCPointer<ByteVar>(), salen, host.rawAddress.toCPointer<ByteVar>(), hostlen, serv.rawAddress.toCPointer<ByteVar>(), servlen, flags)

public actual fun mincore(addr: ConstPtr, len: SizeT, vec: MutPtr): CInt =
    rustlangLibcUnixBsdAppleMincore(addr.rawAddress.toCPointer<ByteVar>(), len, vec.rawAddress.toCPointer<ByteVar>())

public actual fun sysctlnametomib(name: ConstPtr, mibp: MutPtr, sizep: MutPtr): CInt =
    rustlangLibcUnixBsdAppleSysctlnametomib(name.rawAddress.toCPointer<ByteVar>(), mibp.rawAddress.toCPointer<ByteVar>(), sizep.rawAddress.toCPointer<ByteVar>())

public actual fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt =
    rustlangLibcUnixBsdAppleMprotect(addr.rawAddress.toCPointer<ByteVar>(), len, prot)

public actual fun semget(key: KeyT, nsems: CInt, semflg: CInt): CInt =
    rustlangLibcUnixBsdAppleSemget(key, nsems, semflg)

public actual fun semctl(semid: CInt, semnum: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixBsdAppleSemctl(semid, semnum, cmd, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun semop(semid: CInt, sops: MutPtr, nsops: SizeT): CInt =
    rustlangLibcUnixBsdAppleSemop(semid, sops.rawAddress.toCPointer<ByteVar>(), nsops)

public actual fun shmOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixBsdAppleShmOpen(name.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun ftok(pathname: ConstPtr, projId: CInt): KeyT =
    rustlangLibcUnixBsdAppleFtok(pathname.rawAddress.toCPointer<ByteVar>(), projId)

public actual fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleShmat(shmid, shmaddr.rawAddress.toCPointer<ByteVar>(), shmflg)?.toLong() ?: 0L)

public actual fun shmdt(shmaddr: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleShmdt(shmaddr.rawAddress.toCPointer<ByteVar>())

public actual fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixBsdAppleShmctl(shmid, cmd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt =
    rustlangLibcUnixBsdAppleShmget(key, size, shmflg)

public actual fun sysctl(name: MutPtr, namelen: CUint, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    rustlangLibcUnixBsdAppleSysctl(name.rawAddress.toCPointer<ByteVar>(), namelen, oldp.rawAddress.toCPointer<ByteVar>(), oldlenp.rawAddress.toCPointer<ByteVar>(), newp.rawAddress.toCPointer<ByteVar>(), newlen)

public actual fun sysctlbyname(name: ConstPtr, oldp: MutPtr, oldlenp: MutPtr, newp: MutPtr, newlen: SizeT): CInt =
    rustlangLibcUnixBsdAppleSysctlbyname(name.rawAddress.toCPointer<ByteVar>(), oldp.rawAddress.toCPointer<ByteVar>(), oldlenp.rawAddress.toCPointer<ByteVar>(), newp.rawAddress.toCPointer<ByteVar>(), newlen)

public actual fun machAbsoluteTime(): ULong =
    rustlangLibcUnixBsdAppleMachAbsoluteTime()

public actual fun machTimebaseInfo(info: MutPtr): CInt =
    rustlangLibcUnixBsdAppleMachTimebaseInfo(info.rawAddress.toCPointer<ByteVar>())

public actual fun machHostSelf(): MachPortT =
    rustlangLibcUnixBsdAppleMachHostSelf()

public actual fun machThreadSelf(): MachPortT =
    rustlangLibcUnixBsdAppleMachThreadSelf()

public actual fun pthreadCondTimedwaitRelativeNp(cond: MutPtr, lock: MutPtr, timeout: ConstPtr): CInt =
    rustlangLibcUnixBsdApplePthreadCondTimedwaitRelativeNp(cond.rawAddress.toCPointer<ByteVar>(), lock.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetscope(attr: ConstPtr, contentionscope: MutPtr): CInt =
    rustlangLibcUnixBsdApplePthreadAttrGetscope(attr.rawAddress.toCPointer<ByteVar>(), contentionscope.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetstackaddr(attr: ConstPtr, stackaddr: MutPtr): CInt =
    rustlangLibcUnixBsdApplePthreadAttrGetstackaddr(attr.rawAddress.toCPointer<ByteVar>(), stackaddr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, detachstate: MutPtr): CInt =
    rustlangLibcUnixBsdApplePthreadAttrGetdetachstate(attr.rawAddress.toCPointer<ByteVar>(), detachstate.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetscope(attr: MutPtr, contentionscope: CInt): CInt =
    rustlangLibcUnixBsdApplePthreadAttrSetscope(attr.rawAddress.toCPointer<ByteVar>(), contentionscope)

public actual fun pthreadAttrSetstackaddr(attr: MutPtr, stackaddr: MutPtr): CInt =
    rustlangLibcUnixBsdApplePthreadAttrSetstackaddr(attr.rawAddress.toCPointer<ByteVar>(), stackaddr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSetnameNp(name: ConstPtr): CInt =
    rustlangLibcUnixBsdApplePthreadSetnameNp(name.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadGetnameNp(thread: PthreadT, name: MutPtr, len: SizeT): CInt =
    rustlangLibcUnixBsdApplePthreadGetnameNp(thread.toLong().toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun pthreadMachThreadNp(thread: PthreadT): io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT =
    rustlangLibcUnixBsdApplePthreadMachThreadNp(thread.toLong().toCPointer<ByteVar>())

public actual fun pthreadFromMachThreadNp(port: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT): PthreadT =
    (rustlangLibcUnixBsdApplePthreadFromMachThreadNp(port)?.toLong() ?: 0L).toULong()

public actual fun pthreadGetStackaddrNp(thread: PthreadT): MutPtr =
    MutPtr(rustlangLibcUnixBsdApplePthreadGetStackaddrNp(thread.toLong().toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun pthreadGetStacksizeNp(thread: PthreadT): SizeT =
    rustlangLibcUnixBsdApplePthreadGetStacksizeNp(thread.toLong().toCPointer<ByteVar>())

public actual fun pthreadMainNp(): CInt =
    rustlangLibcUnixBsdApplePthreadMainNp()

public actual fun pthreadThreadidNp(thread: PthreadT, threadId: MutPtr): CInt =
    rustlangLibcUnixBsdApplePthreadThreadidNp(thread.toLong().toCPointer<ByteVar>(), threadId.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadJitWriteProtectNp(enabled: CInt) {
    rustlangLibcUnixBsdApplePthreadJitWriteProtectNp(enabled)
}

public actual fun pthreadJitWriteProtectSupportedNp(): CInt =
    rustlangLibcUnixBsdApplePthreadJitWriteProtectSupportedNp()

public actual fun pthreadJitWriteWithCallbackNp(callback: io.github.kotlinmania.rustlang.unix.bsd.apple.PthreadJitWriteCallbackT, ctx: MutPtr): CInt =
    rustlangLibcUnixBsdApplePthreadJitWriteWithCallbackNp(0L.toCPointer<ByteVar>(), ctx.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadJitWriteFreezeCallbacksNp() {
    rustlangLibcUnixBsdApplePthreadJitWriteFreezeCallbacksNp()
}

public actual fun pthreadCpuNumberNp(cpuNumberOut: MutPtr): CInt =
    rustlangLibcUnixBsdApplePthreadCpuNumberNp(cpuNumberOut.rawAddress.toCPointer<ByteVar>())

public actual fun osSyncWaitOnAddress(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT): CInt =
    rustlangLibcUnixBsdAppleOsSyncWaitOnAddress(addr.rawAddress.toCPointer<ByteVar>(), value, size, flags)

public actual fun osSyncWaitOnAddressWithDeadline(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, deadline: ULong): CInt =
    rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithDeadline(addr.rawAddress.toCPointer<ByteVar>(), value, size, flags, clockid, deadline)

public actual fun osSyncWaitOnAddressWithTimeout(addr: MutPtr, value: ULong, size: SizeT, flags: OsSyncWaitOnAddressFlagsT, clockid: OsClockidT, timeoutNs: ULong): CInt =
    rustlangLibcUnixBsdAppleOsSyncWaitOnAddressWithTimeout(addr.rawAddress.toCPointer<ByteVar>(), value, size, flags, clockid, timeoutNs)

public actual fun osSyncWakeByAddressAny(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    rustlangLibcUnixBsdAppleOsSyncWakeByAddressAny(addr.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun osSyncWakeByAddressAll(addr: MutPtr, size: SizeT, flags: OsSyncWakeByAddressFlagsT): CInt =
    rustlangLibcUnixBsdAppleOsSyncWakeByAddressAll(addr.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun osUnfairLockLock(lock: OsUnfairLockT) {
    rustlangLibcUnixBsdAppleOsUnfairLockLock(lock.rawAddress.toCPointer<ByteVar>())
}

public actual fun osUnfairLockTrylock(lock: OsUnfairLockT): Boolean =
    rustlangLibcUnixBsdAppleOsUnfairLockTrylock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun osUnfairLockUnlock(lock: OsUnfairLockT) {
    rustlangLibcUnixBsdAppleOsUnfairLockUnlock(lock.rawAddress.toCPointer<ByteVar>())
}

public actual fun osUnfairLockAssertOwner(lock: OsUnfairLockT) {
    rustlangLibcUnixBsdAppleOsUnfairLockAssertOwner(lock.rawAddress.toCPointer<ByteVar>())
}

public actual fun osUnfairLockAssertNotOwner(lock: OsUnfairLockT) {
    rustlangLibcUnixBsdAppleOsUnfairLockAssertNotOwner(lock.rawAddress.toCPointer<ByteVar>())
}

public actual fun osLogCreate(subsystem: ConstPtr, category: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT =
    MutPtr(rustlangLibcUnixBsdAppleOsLogCreate(subsystem.rawAddress.toCPointer<ByteVar>(), category.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun osLogTypeEnabled(oslog: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, tpe: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogTypeT): Boolean =
    rustlangLibcUnixBsdAppleOsLogTypeEnabled(oslog.rawAddress.toCPointer<ByteVar>(), tpe)

public actual fun osSignpostIdMakeWithPointer(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT, ptr: ConstPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    rustlangLibcUnixBsdAppleOsSignpostIdMakeWithPointer(log.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>())

public actual fun osSignpostIdGenerate(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): io.github.kotlinmania.rustlang.unix.bsd.apple.OsSignpostIdT =
    rustlangLibcUnixBsdAppleOsSignpostIdGenerate(log.rawAddress.toCPointer<ByteVar>())

public actual fun osSignpostEnabled(log: io.github.kotlinmania.rustlang.unix.bsd.apple.OsLogT): Boolean =
    rustlangLibcUnixBsdAppleOsSignpostEnabled(log.rawAddress.toCPointer<ByteVar>())

public actual fun threadPolicySet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MachMsgTypeNumberT): KernReturnT =
    rustlangLibcUnixBsdAppleThreadPolicySet(thread, flavor, policyInfo.rawAddress.toCPointer<ByteVar>(), count)

public actual fun threadPolicyGet(thread: ThreadT, flavor: ThreadPolicyFlavorT, policyInfo: ThreadPolicyT, count: MutPtr, getDefault: MutPtr): KernReturnT =
    rustlangLibcUnixBsdAppleThreadPolicyGet(thread, flavor, policyInfo.rawAddress.toCPointer<ByteVar>(), count.rawAddress.toCPointer<ByteVar>(), getDefault.rawAddress.toCPointer<ByteVar>())

public actual fun threadInfo(targetAct: ThreadInspectT, flavor: ThreadFlavorT, threadInfoOut: ThreadInfoT, threadInfoOutCnt: MutPtr): KernReturnT =
    rustlangLibcUnixBsdAppleThreadInfo(targetAct, flavor, threadInfoOut.rawAddress.toCPointer<ByteVar>(), threadInfoOutCnt.rawAddress.toCPointer<ByteVar>())

public actual fun error(): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleError()?.toLong() ?: 0L)

public actual fun backtrace(buf: MutPtr, sz: CInt): CInt =
    rustlangLibcUnixBsdAppleBacktrace(buf.rawAddress.toCPointer<ByteVar>(), sz)

public actual fun backtraceSymbols(addrs: ConstPtr, sz: CInt): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleBacktraceSymbols(addrs.rawAddress.toCPointer<ByteVar>(), sz)?.toLong() ?: 0L)

public actual fun backtraceSymbolsFd(addrs: ConstPtr, sz: CInt, fd: CInt) {
    rustlangLibcUnixBsdAppleBacktraceSymbolsFd(addrs.rawAddress.toCPointer<ByteVar>(), sz, fd)
}

public actual fun backtraceFromFp(startfp: MutPtr, array: MutPtr, size: CInt): CInt =
    rustlangLibcUnixBsdAppleBacktraceFromFp(startfp.rawAddress.toCPointer<ByteVar>(), array.rawAddress.toCPointer<ByteVar>(), size)

public actual fun backtraceImageOffsets(array: ConstPtr, imageOffsets: MutPtr, size: CInt) {
    rustlangLibcUnixBsdAppleBacktraceImageOffsets(array.rawAddress.toCPointer<ByteVar>(), imageOffsets.rawAddress.toCPointer<ByteVar>(), size)
}

public actual fun backtraceAsync(array: MutPtr, length: SizeT, taskId: MutPtr): SizeT =
    rustlangLibcUnixBsdAppleBacktraceAsync(array.rawAddress.toCPointer<ByteVar>(), length, taskId.rawAddress.toCPointer<ByteVar>())

public actual fun statfs(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixBsdAppleStatfs(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatfs(fd: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixBsdAppleFstatfs(fd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun kevent(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, timeout: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleKevent(kq, changelist.rawAddress.toCPointer<ByteVar>(), nchanges, eventlist.rawAddress.toCPointer<ByteVar>(), nevents, timeout.rawAddress.toCPointer<ByteVar>())

public actual fun kevent64(kq: CInt, changelist: ConstPtr, nchanges: CInt, eventlist: MutPtr, nevents: CInt, flags: CUint, timeout: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleKevent64(kq, changelist.rawAddress.toCPointer<ByteVar>(), nchanges, eventlist.rawAddress.toCPointer<ByteVar>(), nevents, flags, timeout.rawAddress.toCPointer<ByteVar>())

public actual fun mount(src: ConstPtr, target: ConstPtr, flags: CInt, data: MutPtr): CInt =
    rustlangLibcUnixBsdAppleMount(src.rawAddress.toCPointer<ByteVar>(), target.rawAddress.toCPointer<ByteVar>(), flags, data.rawAddress.toCPointer<ByteVar>())

public actual fun fmount(src: ConstPtr, fd: CInt, flags: CInt, data: MutPtr): CInt =
    rustlangLibcUnixBsdAppleFmount(src.rawAddress.toCPointer<ByteVar>(), fd, flags, data.rawAddress.toCPointer<ByteVar>())

public actual fun ptrace(request: CInt, pid: PidT, addr: MutPtr, data: CInt): CInt =
    rustlangLibcUnixBsdApplePtrace(request, pid, addr.rawAddress.toCPointer<ByteVar>(), data)

public actual fun quotactl(special: ConstPtr, cmd: CInt, id: CInt, data: MutPtr): CInt =
    rustlangLibcUnixBsdAppleQuotactl(special.rawAddress.toCPointer<ByteVar>(), cmd, id, data.rawAddress.toCPointer<ByteVar>())

public actual fun sethostname(name: ConstPtr, len: CInt): CInt =
    rustlangLibcUnixBsdAppleSethostname(name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun sendfile(fd: CInt, s: CInt, offset: OffT, len: MutPtr, hdtr: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixBsdAppleSendfile(fd, s, offset, len.rawAddress.toCPointer<ByteVar>(), hdtr.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleFutimens(fd, times.rawAddress.toCPointer<ByteVar>())

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    rustlangLibcUnixBsdAppleUtimensat(dirfd, path.rawAddress.toCPointer<ByteVar>(), times.rawAddress.toCPointer<ByteVar>(), flag)

public actual fun openpty(amaster: MutPtr, aslave: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): CInt =
    rustlangLibcUnixBsdAppleOpenpty(amaster.rawAddress.toCPointer<ByteVar>(), aslave.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), termp.rawAddress.toCPointer<ByteVar>(), winp.rawAddress.toCPointer<ByteVar>())

public actual fun forkpty(amaster: MutPtr, name: MutPtr, termp: MutPtr, winp: MutPtr): PidT =
    rustlangLibcUnixBsdAppleForkpty(amaster.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), termp.rawAddress.toCPointer<ByteVar>(), winp.rawAddress.toCPointer<ByteVar>())

public actual fun loginTty(fd: CInt): CInt =
    rustlangLibcUnixBsdAppleLoginTty(fd)

public actual fun duplocale(base: LocaleT): LocaleT =
    MutPtr(rustlangLibcUnixBsdAppleDuplocale(base.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun freelocale(loc: LocaleT): CInt =
    rustlangLibcUnixBsdAppleFreelocale(loc.rawAddress.toCPointer<ByteVar>())

public actual fun localeconvL(loc: LocaleT): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleLocaleconvL(loc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: LocaleT): LocaleT =
    MutPtr(rustlangLibcUnixBsdAppleNewlocale(mask, locale.rawAddress.toCPointer<ByteVar>(), base.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun uselocale(loc: LocaleT): LocaleT =
    MutPtr(rustlangLibcUnixBsdAppleUselocale(loc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun querylocale(mask: CInt, loc: LocaleT): ConstPtr =
    ConstPtr(rustlangLibcUnixBsdAppleQuerylocale(mask, loc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT): CInt =
    rustlangLibcUnixBsdAppleGetpriority(which, who)

public actual fun setpriority(which: CInt, who: io.github.kotlinmania.rustlang.unix.bsd.apple.IdT, prio: CInt): CInt =
    rustlangLibcUnixBsdAppleSetpriority(which, who, prio)

public actual fun getdomainname(name: MutPtr, len: CInt): CInt =
    rustlangLibcUnixBsdAppleGetdomainname(name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun setdomainname(name: ConstPtr, len: CInt): CInt =
    rustlangLibcUnixBsdAppleSetdomainname(name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    rustlangLibcUnixBsdApplePreadv(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset)

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    rustlangLibcUnixBsdApplePwritev(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset)

public actual fun getxattr(path: ConstPtr, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    rustlangLibcUnixBsdAppleGetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, position, flags)

public actual fun fgetxattr(filedes: CInt, name: ConstPtr, value: MutPtr, size: SizeT, position: UInt, flags: CInt): SsizeT =
    rustlangLibcUnixBsdAppleFgetxattr(filedes, name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, position, flags)

public actual fun setxattr(path: ConstPtr, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    rustlangLibcUnixBsdAppleSetxattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, position, flags)

public actual fun fsetxattr(filedes: CInt, name: ConstPtr, value: ConstPtr, size: SizeT, position: UInt, flags: CInt): CInt =
    rustlangLibcUnixBsdAppleFsetxattr(filedes, name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), size, position, flags)

public actual fun listxattr(path: ConstPtr, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    rustlangLibcUnixBsdAppleListxattr(path.rawAddress.toCPointer<ByteVar>(), list.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun flistxattr(filedes: CInt, list: MutPtr, size: SizeT, flags: CInt): SsizeT =
    rustlangLibcUnixBsdAppleFlistxattr(filedes, list.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun removexattr(path: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixBsdAppleRemovexattr(path.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun renamexNp(from: ConstPtr, to: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixBsdAppleRenamexNp(from.rawAddress.toCPointer<ByteVar>(), to.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun renameatxNp(fromfd: CInt, from: ConstPtr, tofd: CInt, to: ConstPtr, flags: CUint): CInt =
    rustlangLibcUnixBsdAppleRenameatxNp(fromfd, from.rawAddress.toCPointer<ByteVar>(), tofd, to.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun fremovexattr(filedes: CInt, name: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixBsdAppleFremovexattr(filedes, name.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun getgrouplist(name: ConstPtr, basegid: CInt, groups: MutPtr, ngroups: MutPtr): CInt =
    rustlangLibcUnixBsdAppleGetgrouplist(name.rawAddress.toCPointer<ByteVar>(), basegid, groups.rawAddress.toCPointer<ByteVar>(), ngroups.rawAddress.toCPointer<ByteVar>())

public actual fun initgroups(user: ConstPtr, basegroup: CInt): CInt =
    rustlangLibcUnixBsdAppleInitgroups(user.rawAddress.toCPointer<ByteVar>(), basegroup)

public actual fun waitid(idtype: IdtypeT, id: IdT, infop: MutPtr, options: CInt): CInt =
    rustlangLibcUnixBsdAppleWaitid(idtype, id, infop.rawAddress.toCPointer<ByteVar>(), options)

public actual fun brk(addr: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleBrk(addr.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun sbrk(increment: CInt): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleSbrk(increment)?.toLong() ?: 0L)

public actual fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleSettimeofday(tv.rawAddress.toCPointer<ByteVar>(), tz.rawAddress.toCPointer<ByteVar>())

public actual fun dyldImageCount(): UInt =
    rustlangLibcUnixBsdAppleDyldImageCount()

public actual fun dyldGetImageHeader(imageIndex: UInt): ConstPtr =
    ConstPtr(rustlangLibcUnixBsdAppleDyldGetImageHeader(imageIndex)?.toLong() ?: 0L)

public actual fun dyldGetImageVmaddrSlide(imageIndex: UInt): IntptrT =
    rustlangLibcUnixBsdAppleDyldGetImageVmaddrSlide(imageIndex)

public actual fun dyldGetImageName(imageIndex: UInt): ConstPtr =
    ConstPtr(rustlangLibcUnixBsdAppleDyldGetImageName(imageIndex)?.toLong() ?: 0L)

public actual fun posixSpawn(pid: MutPtr, path: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawn(pid.rawAddress.toCPointer<ByteVar>(), path.rawAddress.toCPointer<ByteVar>(), fileActions.rawAddress.toCPointer<ByteVar>(), attrp.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnp(pid: MutPtr, file: ConstPtr, fileActions: ConstPtr, attrp: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnp(pid.rawAddress.toCPointer<ByteVar>(), file.rawAddress.toCPointer<ByteVar>(), fileActions.rawAddress.toCPointer<ByteVar>(), attrp.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrInit(attr: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrDestroy(attr: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrGetsigdefault(attr: ConstPtr, default: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrGetsigdefault(attr.rawAddress.toCPointer<ByteVar>(), default.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetsigdefault(attr: MutPtr, default: ConstPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrSetsigdefault(attr.rawAddress.toCPointer<ByteVar>(), default.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrGetsigmask(attr: ConstPtr, default: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrGetsigmask(attr.rawAddress.toCPointer<ByteVar>(), default.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetsigmask(attr: MutPtr, default: ConstPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrSetsigmask(attr.rawAddress.toCPointer<ByteVar>(), default.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrGetflags(attr: ConstPtr, flags: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrGetflags(attr.rawAddress.toCPointer<ByteVar>(), flags.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetflags(attr: MutPtr, flags: CShort): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrSetflags(attr.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun posixSpawnattrGetpgroup(attr: ConstPtr, flags: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrGetpgroup(attr.rawAddress.toCPointer<ByteVar>(), flags.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetpgroup(attr: MutPtr, flags: PidT): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrSetpgroup(attr.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun posixSpawnattrSetarchprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrSetarchprefNp(attr.rawAddress.toCPointer<ByteVar>(), count, pref.rawAddress.toCPointer<ByteVar>(), subpref.rawAddress.toCPointer<ByteVar>(), ocount.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrGetarchprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, subpref: MutPtr, ocount: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrGetarchprefNp(attr.rawAddress.toCPointer<ByteVar>(), count, pref.rawAddress.toCPointer<ByteVar>(), subpref.rawAddress.toCPointer<ByteVar>(), ocount.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrGetbinprefNp(attr: ConstPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrGetbinprefNp(attr.rawAddress.toCPointer<ByteVar>(), count, pref.rawAddress.toCPointer<ByteVar>(), ocount.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnattrSetbinprefNp(attr: MutPtr, count: SizeT, pref: MutPtr, ocount: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnattrSetbinprefNp(attr.rawAddress.toCPointer<ByteVar>(), count, pref.rawAddress.toCPointer<ByteVar>(), ocount.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsInit(actions: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsInit(actions.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsDestroy(actions: MutPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsDestroy(actions.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsAddopen(actions: MutPtr, fd: CInt, path: ConstPtr, oflag: CInt, mode: ModeT): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsAddopen(actions.rawAddress.toCPointer<ByteVar>(), fd, path.rawAddress.toCPointer<ByteVar>(), oflag, mode)

public actual fun posixSpawnFileActionsAddclose(actions: MutPtr, fd: CInt): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsAddclose(actions.rawAddress.toCPointer<ByteVar>(), fd)

public actual fun posixSpawnFileActionsAdddup2(actions: MutPtr, fd: CInt, newfd: CInt): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsAdddup2(actions.rawAddress.toCPointer<ByteVar>(), fd, newfd)

public actual fun posixSpawnFileActionsAddchdirNp(actions: MutPtr, path: ConstPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdirNp(actions.rawAddress.toCPointer<ByteVar>(), path.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsAddfchdirNp(actions: MutPtr, fd: CInt): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdirNp(actions.rawAddress.toCPointer<ByteVar>(), fd)

public actual fun posixSpawnFileActionsAddchdir(actions: MutPtr, path: ConstPtr): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsAddchdir(actions.rawAddress.toCPointer<ByteVar>(), path.rawAddress.toCPointer<ByteVar>())

public actual fun posixSpawnFileActionsAddfchdir(actions: MutPtr, fd: CInt): CInt =
    rustlangLibcUnixBsdApplePosixSpawnFileActionsAddfchdir(actions.rawAddress.toCPointer<ByteVar>(), fd)

public actual fun uname(buf: MutPtr): CInt =
    rustlangLibcUnixBsdAppleUname(buf.rawAddress.toCPointer<ByteVar>())

public actual fun connectx(socket: CInt, endpoints: ConstPtr, associd: SaeAssocidT, flags: CUint, iov: ConstPtr, iovcnt: CUint, len: MutPtr, connid: MutPtr): CInt =
    rustlangLibcUnixBsdAppleConnectx(socket, endpoints.rawAddress.toCPointer<ByteVar>(), associd, flags, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, len.rawAddress.toCPointer<ByteVar>(), connid.rawAddress.toCPointer<ByteVar>())

public actual fun disconnectx(socket: CInt, associd: SaeAssocidT, connid: SaeConnidT): CInt =
    rustlangLibcUnixBsdAppleDisconnectx(socket, associd, connid)

public actual fun ntpAdjtime(buf: MutPtr): CInt =
    rustlangLibcUnixBsdAppleNtpAdjtime(buf.rawAddress.toCPointer<ByteVar>())

public actual fun ntpGettime(buf: MutPtr): CInt =
    rustlangLibcUnixBsdAppleNtpGettime(buf.rawAddress.toCPointer<ByteVar>())

public actual fun getmntinfo(mntbufp: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixBsdAppleGetmntinfo(mntbufp.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun getfsstat(mntbufp: MutPtr, bufsize: CInt, flags: CInt): CInt =
    rustlangLibcUnixBsdAppleGetfsstat(mntbufp.rawAddress.toCPointer<ByteVar>(), bufsize, flags)

public actual fun clonefile(src: ConstPtr, dst: ConstPtr, flags: UInt): CInt =
    rustlangLibcUnixBsdAppleClonefile(src.rawAddress.toCPointer<ByteVar>(), dst.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun clonefileat(srcDirfd: CInt, src: ConstPtr, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    rustlangLibcUnixBsdAppleClonefileat(srcDirfd, src.rawAddress.toCPointer<ByteVar>(), dstDirfd, dst.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun fclonefileat(srcfd: CInt, dstDirfd: CInt, dst: ConstPtr, flags: UInt): CInt =
    rustlangLibcUnixBsdAppleFclonefileat(srcfd, dstDirfd, dst.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun copyfile(from: ConstPtr, to: ConstPtr, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    rustlangLibcUnixBsdAppleCopyfile(from.rawAddress.toCPointer<ByteVar>(), to.rawAddress.toCPointer<ByteVar>(), state.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun fcopyfile(from: CInt, to: CInt, state: CopyfileStateT, flags: CopyfileFlagsT): CInt =
    rustlangLibcUnixBsdAppleFcopyfile(from, to, state.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun copyfileStateFree(s: CopyfileStateT): CInt =
    rustlangLibcUnixBsdAppleCopyfileStateFree(s.rawAddress.toCPointer<ByteVar>())

public actual fun copyfileStateAlloc(): CopyfileStateT =
    MutPtr(rustlangLibcUnixBsdAppleCopyfileStateAlloc()?.toLong() ?: 0L)

public actual fun copyfileStateGet(s: CopyfileStateT, flags: UInt, dst: MutPtr): CInt =
    rustlangLibcUnixBsdAppleCopyfileStateGet(s.rawAddress.toCPointer<ByteVar>(), flags, dst.rawAddress.toCPointer<ByteVar>())

public actual fun copyfileStateSet(s: CopyfileStateT, flags: UInt, src: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleCopyfileStateSet(s.rawAddress.toCPointer<ByteVar>(), flags, src.rawAddress.toCPointer<ByteVar>())

public actual fun machErrorString(errorValue: io.github.kotlinmania.rustlang.unix.bsd.apple.MachErrorT): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleMachErrorString(errorValue)?.toLong() ?: 0L)

public actual fun memsetS(s: MutPtr, smax: SizeT, c: CInt, n: SizeT): CInt =
    rustlangLibcUnixBsdAppleMemsetS(s.rawAddress.toCPointer<ByteVar>(), smax, c, n)

public actual fun memsetPattern4(b: MutPtr, pattern4: ConstPtr, len: SizeT) {
    rustlangLibcUnixBsdAppleMemsetPattern4(b.rawAddress.toCPointer<ByteVar>(), pattern4.rawAddress.toCPointer<ByteVar>(), len)
}

public actual fun memsetPattern8(b: MutPtr, pattern8: ConstPtr, len: SizeT) {
    rustlangLibcUnixBsdAppleMemsetPattern8(b.rawAddress.toCPointer<ByteVar>(), pattern8.rawAddress.toCPointer<ByteVar>(), len)
}

public actual fun memsetPattern16(b: MutPtr, pattern16: ConstPtr, len: SizeT) {
    rustlangLibcUnixBsdAppleMemsetPattern16(b.rawAddress.toCPointer<ByteVar>(), pattern16.rawAddress.toCPointer<ByteVar>(), len)
}

public actual fun strtonum(numstr: ConstPtr, minval: CLonglong, maxval: CLonglong, errstrp: MutPtr): CLonglong =
    rustlangLibcUnixBsdAppleStrtonum(numstr.rawAddress.toCPointer<ByteVar>(), minval, maxval, errstrp.rawAddress.toCPointer<ByteVar>())

public actual fun mstats(): Mstats =
    Mstats()

public actual fun mallocPrintf(format: ConstPtr, vararg variadicArgs: Long) {
    rustlangLibcUnixBsdAppleMallocPrintf(format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)
}

public actual fun mallocZoneCheck(zone: MutPtr): BooleanT =
    rustlangLibcUnixBsdAppleMallocZoneCheck(zone.rawAddress.toCPointer<ByteVar>())

public actual fun mallocZonePrint(zone: MutPtr, verbose: BooleanT) {
    rustlangLibcUnixBsdAppleMallocZonePrint(zone.rawAddress.toCPointer<ByteVar>(), verbose)
}

public actual fun mallocZoneStatistics(zone: MutPtr, stats: MutPtr) {
    rustlangLibcUnixBsdAppleMallocZoneStatistics(zone.rawAddress.toCPointer<ByteVar>(), stats.rawAddress.toCPointer<ByteVar>())
}

public actual fun mallocZoneLog(zone: MutPtr, address: MutPtr) {
    rustlangLibcUnixBsdAppleMallocZoneLog(zone.rawAddress.toCPointer<ByteVar>(), address.rawAddress.toCPointer<ByteVar>())
}

public actual fun mallocZonePrintPtrInfo(ptr: MutPtr) {
    rustlangLibcUnixBsdAppleMallocZonePrintPtrInfo(ptr.rawAddress.toCPointer<ByteVar>())
}

public actual fun mallocDefaultZone(): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleMallocDefaultZone()?.toLong() ?: 0L)

public actual fun mallocZoneFromPtr(ptr: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleMallocZoneFromPtr(ptr.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun mallocZoneMalloc(zone: MutPtr, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleMallocZoneMalloc(zone.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun mallocZoneValloc(zone: MutPtr, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleMallocZoneValloc(zone.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun mallocZoneCalloc(zone: MutPtr, numItems: SizeT, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleMallocZoneCalloc(zone.rawAddress.toCPointer<ByteVar>(), numItems, size)?.toLong() ?: 0L)

public actual fun mallocZoneRealloc(zone: MutPtr, ptr: MutPtr, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleMallocZoneRealloc(zone.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun mallocZoneFree(zone: MutPtr, ptr: MutPtr) {
    rustlangLibcUnixBsdAppleMallocZoneFree(zone.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>())
}

public actual fun gethostuuid(id: MutPtr, timeout: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleGethostuuid(id.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>())

public actual fun gethostid(): CLong =
    rustlangLibcUnixBsdAppleGethostid()

public actual fun sethostid(hostid: CLong) {
    rustlangLibcUnixBsdAppleSethostid(hostid)
}

public actual fun cCRandomGenerateBytes(bytes: MutPtr, size: SizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.CCRNGStatus =
    rustlangLibcUnixBsdAppleCCRandomGenerateBytes(bytes.rawAddress.toCPointer<ByteVar>(), size)

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcUnixBsdAppleGetentropy(buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun nsgetExecutablePath(buf: MutPtr, bufsize: MutPtr): CInt =
    rustlangLibcUnixBsdAppleNsgetExecutablePath(buf.rawAddress.toCPointer<ByteVar>(), bufsize.rawAddress.toCPointer<ByteVar>())

public actual fun nsgetArgv(): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleNsgetArgv()?.toLong() ?: 0L)

public actual fun nsgetArgc(): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleNsgetArgc()?.toLong() ?: 0L)

public actual fun nsgetEnviron(): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleNsgetEnviron()?.toLong() ?: 0L)

public actual fun nsgetProgname(): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleNsgetProgname()?.toLong() ?: 0L)

public actual fun machVmMap(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.VmMapT, address: MutPtr, size: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmSizeT, mask: io.github.kotlinmania.rustlang.unix.bsd.apple.MachVmOffsetT, flags: CInt, obj: io.github.kotlinmania.rustlang.unix.bsd.apple.MemEntryNamePortT, offset: io.github.kotlinmania.rustlang.unix.bsd.apple.MemoryObjectOffsetT, copy: BooleanT, curProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, maxProtection: io.github.kotlinmania.rustlang.unix.bsd.apple.VmProtT, inheritance: io.github.kotlinmania.rustlang.unix.bsd.apple.VmInheritT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleMachVmMap(targetTask, address.rawAddress.toCPointer<ByteVar>(), size, mask, flags, obj, offset, copy, curProtection, maxProtection, inheritance)

public actual fun vmAllocate(targetTask: VmMapT, address: MutPtr, size: VmSizeT, flags: CInt): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleVmAllocate(targetTask, address.rawAddress.toCPointer<ByteVar>(), size, flags)

public actual fun vmDeallocate(targetTask: VmMapT, address: VmAddressT, size: VmSizeT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleVmDeallocate(targetTask, address, size)

public actual fun hostStatistics64(hostPriv: HostT, flavor: HostFlavorT, hostInfo64Out: HostInfo64T, hostInfo64OutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleHostStatistics64(hostPriv, flavor, hostInfo64Out.rawAddress.toCPointer<ByteVar>(), hostInfo64OutCnt.rawAddress.toCPointer<ByteVar>())

public actual fun hostProcessorInfo(host: HostT, flavor: ProcessorFlavorT, outProcessorCount: MutPtr, outProcessorInfo: MutPtr, outProcessorInfoCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleHostProcessorInfo(host, flavor, outProcessorCount.rawAddress.toCPointer<ByteVar>(), outProcessorInfo.rawAddress.toCPointer<ByteVar>(), outProcessorInfoCnt.rawAddress.toCPointer<ByteVar>())

public actual fun taskForPid(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, pid: PidT, task: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleTaskForPid(host, pid, task.rawAddress.toCPointer<ByteVar>())

public actual fun taskInfo(host: io.github.kotlinmania.rustlang.unix.bsd.apple.MachPortT, flavor: TaskFlavorT, taskInfoOut: TaskInfoT, taskInfoCount: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleTaskInfo(host, flavor, taskInfoOut.rawAddress.toCPointer<ByteVar>(), taskInfoCount.rawAddress.toCPointer<ByteVar>())

public actual fun taskCreate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, ledgers: io.github.kotlinmania.rustlang.unix.bsd.apple.LedgerArrayT, ledgersCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT, inheritMemory: BooleanT, childTask: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleTaskCreate(targetTask, ledgers.rawAddress.toCPointer<ByteVar>(), ledgersCnt, inheritMemory, childTask.rawAddress.toCPointer<ByteVar>())

public actual fun taskTerminate(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleTaskTerminate(targetTask)

public actual fun taskThreads(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInspectT, actList: MutPtr, actListCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleTaskThreads(targetTask, actList.rawAddress.toCPointer<ByteVar>(), actListCnt.rawAddress.toCPointer<ByteVar>())

public actual fun hostStatistics(hostPriv: HostT, flavor: HostFlavorT, hostInfoOut: HostInfoT, hostInfoOutCnt: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleHostStatistics(hostPriv, flavor, hostInfoOut.rawAddress.toCPointer<ByteVar>(), hostInfoOutCnt.rawAddress.toCPointer<ByteVar>())

public actual fun sysdirStartSearchPathEnumeration(dir: SysdirSearchPathDirectoryT, domainMask: SysdirSearchPathDomainMaskT): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    0u

public actual fun sysdirGetNextSearchPathEnumeration(state: io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState, path: MutPtr): io.github.kotlinmania.rustlang.unix.bsd.apple.SysdirSearchPathEnumerationState =
    rustlangLibcUnixBsdAppleSysdirGetNextSearchPathEnumeration(state, path.rawAddress.toCPointer<ByteVar>())

public actual fun getattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    rustlangLibcUnixBsdAppleGetattrlist(path.rawAddress.toCPointer<ByteVar>(), attrList.rawAddress.toCPointer<ByteVar>(), attrBuf.rawAddress.toCPointer<ByteVar>(), attrBufSize, options)

public actual fun fgetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    rustlangLibcUnixBsdAppleFgetattrlist(fd, attrList.rawAddress.toCPointer<ByteVar>(), attrBuf.rawAddress.toCPointer<ByteVar>(), attrBufSize, options)

public actual fun getattrlistat(fd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: CUlong): CInt =
    rustlangLibcUnixBsdAppleGetattrlistat(fd, path.rawAddress.toCPointer<ByteVar>(), attrList.rawAddress.toCPointer<ByteVar>(), attrBuf.rawAddress.toCPointer<ByteVar>(), attrBufSize, options)

public actual fun setattrlist(path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    rustlangLibcUnixBsdAppleSetattrlist(path.rawAddress.toCPointer<ByteVar>(), attrList.rawAddress.toCPointer<ByteVar>(), attrBuf.rawAddress.toCPointer<ByteVar>(), attrBufSize, options)

public actual fun fsetattrlist(fd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    rustlangLibcUnixBsdAppleFsetattrlist(fd, attrList.rawAddress.toCPointer<ByteVar>(), attrBuf.rawAddress.toCPointer<ByteVar>(), attrBufSize, options)

public actual fun setattrlistat(dirFd: CInt, path: ConstPtr, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: UInt): CInt =
    rustlangLibcUnixBsdAppleSetattrlistat(dirFd, path.rawAddress.toCPointer<ByteVar>(), attrList.rawAddress.toCPointer<ByteVar>(), attrBuf.rawAddress.toCPointer<ByteVar>(), attrBufSize, options)

public actual fun getattrlistbulk(dirfd: CInt, attrList: MutPtr, attrBuf: MutPtr, attrBufSize: SizeT, options: ULong): CInt =
    rustlangLibcUnixBsdAppleGetattrlistbulk(dirfd, attrList.rawAddress.toCPointer<ByteVar>(), attrBuf.rawAddress.toCPointer<ByteVar>(), attrBufSize, options)

public actual fun mallocSize(ptr: ConstPtr): SizeT =
    rustlangLibcUnixBsdAppleMallocSize(ptr.rawAddress.toCPointer<ByteVar>())

public actual fun mallocGoodSize(size: SizeT): SizeT =
    rustlangLibcUnixBsdAppleMallocGoodSize(size)

public actual fun dirname(path: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleDirname(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun basename(path: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleBasename(path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun mkfifoat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixBsdAppleMkfifoat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun mknodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    rustlangLibcUnixBsdAppleMknodat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode, dev)

public actual fun freadlink(fd: CInt, buf: MutPtr, size: SizeT): CInt =
    rustlangLibcUnixBsdAppleFreadlink(fd, buf.rawAddress.toCPointer<ByteVar>(), size)

public actual fun execvP(file: ConstPtr, searchPath: ConstPtr, argv: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleExecvP(file.rawAddress.toCPointer<ByteVar>(), searchPath.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>())

public actual fun qsortR(base: MutPtr, num: SizeT, size: SizeT, arg: MutPtr, compar: QsortRComparCallback?) {
    rustlangLibcUnixBsdAppleQsortR(base.rawAddress.toCPointer<ByteVar>(), num, size, arg.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())
}

public actual fun clockSettime(clockId: io.github.kotlinmania.rustlang.unix.bsd.apple.ClockidT, tp: ConstPtr): CInt =
    rustlangLibcUnixBsdAppleClockSettime(clockId, tp.rawAddress.toCPointer<ByteVar>())

public actual fun procListpids(t: UInt, typeinfo: UInt, buffer: MutPtr, buffersize: CInt): CInt =
    rustlangLibcUnixBsdAppleProcListpids(t, typeinfo, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procListallpids(buffer: MutPtr, buffersize: CInt): CInt =
    rustlangLibcUnixBsdAppleProcListallpids(buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procListpgrppids(pgrpid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    rustlangLibcUnixBsdAppleProcListpgrppids(pgrpid, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procListchildpids(ppid: PidT, buffer: MutPtr, buffersize: CInt): CInt =
    rustlangLibcUnixBsdAppleProcListchildpids(ppid, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procPidinfo(pid: CInt, flavor: CInt, arg: ULong, buffer: MutPtr, buffersize: CInt): CInt =
    rustlangLibcUnixBsdAppleProcPidinfo(pid, flavor, arg, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procPidfdinfo(pid: CInt, fd: CInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    rustlangLibcUnixBsdAppleProcPidfdinfo(pid, fd, flavor, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procPidfileportinfo(pid: CInt, fileport: UInt, flavor: CInt, buffer: MutPtr, buffersize: CInt): CInt =
    rustlangLibcUnixBsdAppleProcPidfileportinfo(pid, fileport, flavor, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procPidpath(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    rustlangLibcUnixBsdAppleProcPidpath(pid, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procName(pid: CInt, buffer: MutPtr, buffersize: UInt): CInt =
    rustlangLibcUnixBsdAppleProcName(pid, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procRegionfilename(pid: CInt, address: ULong, buffer: MutPtr, buffersize: UInt): CInt =
    rustlangLibcUnixBsdAppleProcRegionfilename(pid, address, buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procKmsgbuf(buffer: MutPtr, buffersize: UInt): CInt =
    rustlangLibcUnixBsdAppleProcKmsgbuf(buffer.rawAddress.toCPointer<ByteVar>(), buffersize)

public actual fun procLibversion(major: MutPtr, minor: MutPtr): CInt =
    rustlangLibcUnixBsdAppleProcLibversion(major.rawAddress.toCPointer<ByteVar>(), minor.rawAddress.toCPointer<ByteVar>())

public actual fun procPidRusage(pid: CInt, flavor: CInt, buffer: MutPtr): CInt =
    rustlangLibcUnixBsdAppleProcPidRusage(pid, flavor, buffer.rawAddress.toCPointer<ByteVar>())

public actual fun procSetNoSmt(): CInt =
    rustlangLibcUnixBsdAppleProcSetNoSmt()

public actual fun procSetthreadNoSmt(): CInt =
    rustlangLibcUnixBsdAppleProcSetthreadNoSmt()

public actual fun procSetCsm(flags: UInt): CInt =
    rustlangLibcUnixBsdAppleProcSetCsm(flags)

public actual fun procSetthreadCsm(flags: UInt): CInt =
    rustlangLibcUnixBsdAppleProcSetthreadCsm(flags)

public actual fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixBsdAppleMemmem(haystack.rawAddress.toCPointer<ByteVar>(), haystacklen, needle.rawAddress.toCPointer<ByteVar>(), needlelen)?.toLong() ?: 0L)

public actual fun taskSetInfo(targetTask: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskT, flavor: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskFlavorT, taskInfoIn: io.github.kotlinmania.rustlang.unix.bsd.apple.TaskInfoT, taskInfoInCnt: io.github.kotlinmania.rustlang.unix.bsd.apple.MachMsgTypeNumberT): io.github.kotlinmania.rustlang.unix.bsd.apple.KernReturnT =
    rustlangLibcUnixBsdAppleTaskSetInfo(targetTask, flavor, taskInfoIn.rawAddress.toCPointer<ByteVar>(), taskInfoInCnt)

public actual fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT =
    MutPtr(rustlangLibcUnixBsdAppleIconvOpen(tocode.rawAddress.toCPointer<ByteVar>(), fromcode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT =
    rustlangLibcUnixBsdAppleIconv(cd.rawAddress.toCPointer<ByteVar>(), inbuf.rawAddress.toCPointer<ByteVar>(), inbytesleft.rawAddress.toCPointer<ByteVar>(), outbuf.rawAddress.toCPointer<ByteVar>(), outbytesleft.rawAddress.toCPointer<ByteVar>())

public actual fun iconvClose(cd: IconvT): CInt =
    rustlangLibcUnixBsdAppleIconvClose(cd.rawAddress.toCPointer<ByteVar>())
