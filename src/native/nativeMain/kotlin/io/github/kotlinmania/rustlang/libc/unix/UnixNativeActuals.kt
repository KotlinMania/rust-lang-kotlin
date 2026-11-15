@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CFloat
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAbort
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAccept
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAccess
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAdjtime
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAlarm
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAlignedAlloc
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAtexit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAtof
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAtoi
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAtol
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixAtoll
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixBsearch
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCalloc
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCfgetispeed
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCfgetospeed
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCfmakeraw
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCfsetispeed
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCfsetospeed
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCfsetspeed
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixChdir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixChmod
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixChown
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixChroot
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixClearerr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixClose
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixClosedir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCloselog
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixConfstr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixConnect
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixCreat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixDifftime
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixDladdr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixDlclose
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixDlerror
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixDlopen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixDlsym
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixDup
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixDup2
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixEndservent
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixExecl
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixExecle
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixExeclp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixExecv
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixExecve
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixExecvp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixExit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFchdir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFchmod
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFchmodat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFchown
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFchownat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFclose
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFcntl
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFdopen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFdopendir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFeof
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFerror
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFflush
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFgetc
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFgetpos
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFgets
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFileno
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFmemopen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFnmatch
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFopen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFork
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFpathconf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFprintf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFputc
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFputs
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFread
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFree
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFreeaddrinfo
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFreopen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFscanf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFseek
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFseeko
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFsetpos
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFstat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFstatat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFstatvfs
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFsync
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFtell
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFtello
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFtruncate
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixFwrite
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGaiStrerror
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetaddrinfo
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetchar
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetcharUnlocked
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetcwd
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetegid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetenv
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGeteuid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetgid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetgroups
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGethostname
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetline
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetlogin
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetopt
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetpeername
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetpgid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetpgrp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetpid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetppid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetprotobyname
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetprotobynumber
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetpwnam
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetpwuid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetrusage
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetservbyname
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetservbyport
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetservent
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetsid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetsockname
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetsockopt
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGetuid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGmtime
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGmtimeR
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixGrantpt
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixHstrerror
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIfIndextoname
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIfNametoindex
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsalnum
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsalpha
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsatty
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsblank
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIscntrl
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsdigit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsgraph
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIslower
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsprint
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIspunct
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsspace
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsupper
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixIsxdigit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixKill
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixKillpg
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLchown
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLink
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLinkat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixListen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLocaleconv
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLocaltime
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLocaltimeR
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLockf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLseek
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixLstat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMalloc
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMemccpy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMemchr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMemcmp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMemcpy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMemmove
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMemset
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMkdir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMkdirat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMkdtemp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMkfifo
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMknod
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMkstemp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMktime
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMlockall
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMmap
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMunlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMunlockall
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixMunmap
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixNanosleep
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixNice
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixOpen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixOpenMemstream
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixOpenWmemstream
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixOpenat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixOpendir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixOpenlog
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPathconf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPause
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPclose
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPerror
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPipe
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPoll
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPosixMemalign
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPosixOpenpt
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPread
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPrintf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPselect
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadAttrDestroy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadAttrGetstacksize
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadAttrInit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadAttrSetdetachstate
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadAttrSetstacksize
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadCondBroadcast
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadCondDestroy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadCondInit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadCondSignal
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadCondTimedwait
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadCondWait
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadCondattrDestroy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadCondattrInit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadDetach
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadEqual
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadExit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadGetspecific
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadJoin
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadKeyCreate
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadKeyDelete
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadMutexDestroy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadMutexInit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadMutexLock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadMutexTrylock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadMutexUnlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadMutexattrDestroy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadMutexattrInit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadMutexattrSettype
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockDestroy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockInit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockRdlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockTryrdlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockTrywrlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockUnlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockWrlock
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockattrDestroy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadRwlockattrInit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadSelf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPthreadSetspecific
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPtsname
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPutchar
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPutcharUnlocked
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPutenv
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPuts
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixPwrite
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixQsort
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRaise
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRead
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixReaddir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixReaddirR
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixReadlink
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixReadlinkat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRealloc
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRealpath
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRecv
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRemove
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRename
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRenameat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixResInit
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRewind
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRewinddir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixRmdir
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixScanf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSchedYield
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSelect
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSemPost
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSemTrywait
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSemWait
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSend
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSendto
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetbuf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetegid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetenv
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSeteuid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetgid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetlocale
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetlogmask
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetpgid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetregid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetreuid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetservent
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetsid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetsockopt
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetuid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSetvbuf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixShutdown
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSigaction
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSigaddset
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSigdelset
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSigemptyset
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSigfillset
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSigismember
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSignal
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSigpending
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSigprocmask
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSleep
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSnprintf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSocket
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSocketpair
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSprintf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSscanf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStatvfs
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStpcpy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStpncpy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrcasecmp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrcasestr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrcat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrchr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrcmp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrcoll
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrcpy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrcspn
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrdup
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrerror
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrlen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrncasecmp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrncat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrncmp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrncpy
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrndup
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrnlen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrpbrk
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrrchr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrsignal
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrspn
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrstr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrtod
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrtof
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrtok
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrtokR
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrtol
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrtoll
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrtoul
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrtoull
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixStrxfrm
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSymlink
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSymlinkat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSysconf
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSyslog
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixSystem
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcdrain
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcflow
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcflush
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcgetattr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcgetpgrp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcgetsid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcsendbreak
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcsetattr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTcsetpgrp
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTime
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTimegm
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTimes
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTmpfile
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTmpnam
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTolower
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixToupper
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTruncate
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTtyname
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixTtynameR
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUmask
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUngetc
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUnlink
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUnlinkat
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUnlockpt
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUnsetenv
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUsleep
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUtime
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixUtimes
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixWait
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixWaitpid
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixWcslen
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixWcstombs
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixWmemchr
import io.github.kotlinmania.rustlang.libc.unix.cinterop.rustlangLibcUnixWrite
import io.github.kotlinmania.rustlang.unix.bsd.apple.DevT
import io.github.kotlinmania.rustlang.unix.bsd.apple.SpeedT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.NfdsT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.PthreadKeyT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.PthreadT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.ClockT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.TimeT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.aarch64.WcharT
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual val IN6ADDR_LOOPBACK: In6Addr
    get() = In6Addr()

public actual val IN6ADDR_ANY: In6Addr
    get() = In6Addr()

public actual fun isalnum(c: CInt): CInt =
    rustlangLibcUnixIsalnum(c)

public actual fun isalpha(c: CInt): CInt =
    rustlangLibcUnixIsalpha(c)

public actual fun iscntrl(c: CInt): CInt =
    rustlangLibcUnixIscntrl(c)

public actual fun isdigit(c: CInt): CInt =
    rustlangLibcUnixIsdigit(c)

public actual fun isgraph(c: CInt): CInt =
    rustlangLibcUnixIsgraph(c)

public actual fun islower(c: CInt): CInt =
    rustlangLibcUnixIslower(c)

public actual fun isprint(c: CInt): CInt =
    rustlangLibcUnixIsprint(c)

public actual fun ispunct(c: CInt): CInt =
    rustlangLibcUnixIspunct(c)

public actual fun isspace(c: CInt): CInt =
    rustlangLibcUnixIsspace(c)

public actual fun isupper(c: CInt): CInt =
    rustlangLibcUnixIsupper(c)

public actual fun isxdigit(c: CInt): CInt =
    rustlangLibcUnixIsxdigit(c)

public actual fun isblank(c: CInt): CInt =
    rustlangLibcUnixIsblank(c)

public actual fun tolower(c: CInt): CInt =
    rustlangLibcUnixTolower(c)

public actual fun toupper(c: CInt): CInt =
    rustlangLibcUnixToupper(c)

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    rustlangLibcUnixQsort(base.rawAddress.toCPointer<ByteVar>(), num, size, 0L.toCPointer<ByteVar>())
}

public actual fun bsearch(key: ConstPtr, base: ConstPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?): MutPtr =
    MutPtr(rustlangLibcUnixBsearch(key.rawAddress.toCPointer<ByteVar>(), base.rawAddress.toCPointer<ByteVar>(), num, size, 0L.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixFopen(filename.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixFreopen(filename.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>(), file.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fflush(file: MutPtr): CInt =
    rustlangLibcUnixFflush(file.rawAddress.toCPointer<ByteVar>())

public actual fun fclose(file: MutPtr): CInt =
    rustlangLibcUnixFclose(file.rawAddress.toCPointer<ByteVar>())

public actual fun remove(filename: ConstPtr): CInt =
    rustlangLibcUnixRemove(filename.rawAddress.toCPointer<ByteVar>())

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    rustlangLibcUnixRename(oldname.rawAddress.toCPointer<ByteVar>(), newname.rawAddress.toCPointer<ByteVar>())

public actual fun tmpfile(): MutPtr =
    MutPtr(rustlangLibcUnixTmpfile()?.toLong() ?: 0L)

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    rustlangLibcUnixSetvbuf(stream.rawAddress.toCPointer<ByteVar>(), buffer.rawAddress.toCPointer<ByteVar>(), mode, size)

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    rustlangLibcUnixSetbuf(stream.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())
}

public actual fun getchar(): CInt =
    rustlangLibcUnixGetchar()

public actual fun putchar(c: CInt): CInt =
    rustlangLibcUnixPutchar(c)

public actual fun fgetc(stream: MutPtr): CInt =
    rustlangLibcUnixFgetc(stream.rawAddress.toCPointer<ByteVar>())

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixFgets(buf.rawAddress.toCPointer<ByteVar>(), n, stream.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    rustlangLibcUnixFputc(c, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    rustlangLibcUnixFputs(s.rawAddress.toCPointer<ByteVar>(), stream.rawAddress.toCPointer<ByteVar>())

public actual fun puts(s: ConstPtr): CInt =
    rustlangLibcUnixPuts(s.rawAddress.toCPointer<ByteVar>())

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    rustlangLibcUnixUngetc(c, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    rustlangLibcUnixFread(ptr.rawAddress.toCPointer<ByteVar>(), size, nobj, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    rustlangLibcUnixFwrite(ptr.rawAddress.toCPointer<ByteVar>(), size, nobj, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    rustlangLibcUnixFseek(stream.rawAddress.toCPointer<ByteVar>(), offset, whence)

public actual fun ftell(stream: MutPtr): CLong =
    rustlangLibcUnixFtell(stream.rawAddress.toCPointer<ByteVar>())

public actual fun rewind(stream: MutPtr) {
    rustlangLibcUnixRewind(stream.rawAddress.toCPointer<ByteVar>())
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    rustlangLibcUnixFgetpos(stream.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>())

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    rustlangLibcUnixFsetpos(stream.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>())

public actual fun feof(stream: MutPtr): CInt =
    rustlangLibcUnixFeof(stream.rawAddress.toCPointer<ByteVar>())

public actual fun ferror(stream: MutPtr): CInt =
    rustlangLibcUnixFerror(stream.rawAddress.toCPointer<ByteVar>())

public actual fun clearerr(stream: MutPtr) {
    rustlangLibcUnixClearerr(stream.rawAddress.toCPointer<ByteVar>())
}

public actual fun perror(s: ConstPtr) {
    rustlangLibcUnixPerror(s.rawAddress.toCPointer<ByteVar>())
}

public actual fun atof(s: ConstPtr): CDouble =
    rustlangLibcUnixAtof(s.rawAddress.toCPointer<ByteVar>())

public actual fun atoi(s: ConstPtr): CInt =
    rustlangLibcUnixAtoi(s.rawAddress.toCPointer<ByteVar>())

public actual fun atol(s: ConstPtr): CLong =
    rustlangLibcUnixAtol(s.rawAddress.toCPointer<ByteVar>())

public actual fun atoll(s: ConstPtr): CLonglong =
    rustlangLibcUnixAtoll(s.rawAddress.toCPointer<ByteVar>())

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    rustlangLibcUnixStrtod(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>())

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    rustlangLibcUnixStrtof(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>())

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    rustlangLibcUnixStrtol(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    rustlangLibcUnixStrtoll(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    rustlangLibcUnixStrtoul(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    rustlangLibcUnixStrtoull(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixCalloc(nobj, size)?.toLong() ?: 0L)

public actual fun malloc(size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixMalloc(size)?.toLong() ?: 0L)

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixRealloc(p.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun free(p: MutPtr) {
    rustlangLibcUnixFree(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun abort(): Nothing {
    rustlangLibcUnixAbort()
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    rustlangLibcUnixExit(status)
    error("Unreachable noreturn C function exit")
}

public actual fun system(s: ConstPtr): CInt =
    rustlangLibcUnixSystem(s.rawAddress.toCPointer<ByteVar>())

public actual fun getenv(s: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixGetenv(s.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixStrcpy(dst.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixStrncpy(dst.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun stpcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixStpcpy(dst.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixStrcat(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixStrncat(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    rustlangLibcUnixStrcmp(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    rustlangLibcUnixStrncmp(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    rustlangLibcUnixStrcoll(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(rustlangLibcUnixStrchr(cs.rawAddress.toCPointer<ByteVar>(), c)?.toLong() ?: 0L)

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(rustlangLibcUnixStrrchr(cs.rawAddress.toCPointer<ByteVar>(), c)?.toLong() ?: 0L)

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    rustlangLibcUnixStrspn(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    rustlangLibcUnixStrcspn(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strdup(cs: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixStrdup(cs.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixStrndup(cs.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixStrpbrk(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixStrstr(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    rustlangLibcUnixStrcasecmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>())

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    rustlangLibcUnixStrncasecmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>(), n)

public actual fun strlen(cs: ConstPtr): SizeT =
    rustlangLibcUnixStrlen(cs.rawAddress.toCPointer<ByteVar>())

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    rustlangLibcUnixStrnlen(cs.rawAddress.toCPointer<ByteVar>(), maxlen)

public actual fun strerror(n: CInt): MutPtr =
    MutPtr(rustlangLibcUnixStrerror(n)?.toLong() ?: 0L)

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixStrtok(s.rawAddress.toCPointer<ByteVar>(), t.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strtokR(s: MutPtr, t: ConstPtr, p: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixStrtokR(s.rawAddress.toCPointer<ByteVar>(), t.rawAddress.toCPointer<ByteVar>(), p.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    rustlangLibcUnixStrxfrm(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun strsignal(sig: CInt): MutPtr =
    MutPtr(rustlangLibcUnixStrsignal(sig)?.toLong() ?: 0L)

public actual fun wcslen(buf: ConstPtr): SizeT =
    rustlangLibcUnixWcslen(buf.rawAddress.toCPointer<ByteVar>())

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    rustlangLibcUnixWcstombs(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixMemchr(cx.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun wmemchr(cx: ConstPtr, c: WcharT, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixWmemchr(cx.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    rustlangLibcUnixMemcmp(cx.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixMemcpy(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixMemmove(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixMemset(dest.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun memccpy(dest: MutPtr, src: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixMemccpy(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun getpwnam(name: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixGetpwnam(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getpwuid(uid: io.github.kotlinmania.rustlang.unix.UidT): MutPtr =
    MutPtr(rustlangLibcUnixGetpwuid(uid)?.toLong() ?: 0L)

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixFprintf(stream.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixPrintf(format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixSnprintf(s.rawAddress.toCPointer<ByteVar>(), n, format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixSprintf(s.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixFscanf(stream.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixScanf(format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixSscanf(s.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun getcharUnlocked(): CInt =
    rustlangLibcUnixGetcharUnlocked()

public actual fun putcharUnlocked(c: CInt): CInt =
    rustlangLibcUnixPutcharUnlocked(c)

public actual fun socket(domain: CInt, ty: CInt, protocol: CInt): CInt =
    rustlangLibcUnixSocket(domain, ty, protocol)

public actual fun connect(socket: CInt, address: ConstPtr, len: SocklenT): CInt =
    rustlangLibcUnixConnect(socket, address.rawAddress.toCPointer<ByteVar>(), len)

public actual fun listen(socket: CInt, backlog: CInt): CInt =
    rustlangLibcUnixListen(socket, backlog)

public actual fun accept(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    rustlangLibcUnixAccept(socket, address.rawAddress.toCPointer<ByteVar>(), addressLen.rawAddress.toCPointer<ByteVar>())

public actual fun getpeername(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    rustlangLibcUnixGetpeername(socket, address.rawAddress.toCPointer<ByteVar>(), addressLen.rawAddress.toCPointer<ByteVar>())

public actual fun getsockname(socket: CInt, address: MutPtr, addressLen: MutPtr): CInt =
    rustlangLibcUnixGetsockname(socket, address.rawAddress.toCPointer<ByteVar>(), addressLen.rawAddress.toCPointer<ByteVar>())

public actual fun setsockopt(socket: CInt, level: CInt, name: CInt, value: ConstPtr, optionLen: SocklenT): CInt =
    rustlangLibcUnixSetsockopt(socket, level, name, value.rawAddress.toCPointer<ByteVar>(), optionLen)

public actual fun socketpair(domain: CInt, type: CInt, protocol: CInt, socketVector: MutPtr): CInt =
    rustlangLibcUnixSocketpair(domain, type, protocol, socketVector.rawAddress.toCPointer<ByteVar>())

public actual fun sendto(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt, addr: ConstPtr, addrlen: SocklenT): SsizeT =
    rustlangLibcUnixSendto(socket, buf.rawAddress.toCPointer<ByteVar>(), len, flags, addr.rawAddress.toCPointer<ByteVar>(), addrlen)

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    rustlangLibcUnixShutdown(socket, how)

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixChmod(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    rustlangLibcUnixFchmod(fd, mode)

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixFstat(fildes, buf.rawAddress.toCPointer<ByteVar>())

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixMkdir(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixStat(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun pclose(stream: MutPtr): CInt =
    rustlangLibcUnixPclose(stream.rawAddress.toCPointer<ByteVar>())

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixFdopen(fd, mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fileno(stream: MutPtr): CInt =
    rustlangLibcUnixFileno(stream.rawAddress.toCPointer<ByteVar>())

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixOpen(path.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixCreat(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixFcntl(fd, cmd, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun opendir(dirname: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixOpendir(dirname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fdopendir(fd: CInt): MutPtr =
    MutPtr(rustlangLibcUnixFdopendir(fd)?.toLong() ?: 0L)

public actual fun readdir(dirp: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixReaddir(dirp.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun closedir(dirp: MutPtr): CInt =
    rustlangLibcUnixClosedir(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun rewinddir(dirp: MutPtr) {
    rustlangLibcUnixRewinddir(dirp.rawAddress.toCPointer<ByteVar>())
}

public actual fun fchmodat(dirfd: CInt, pathname: ConstPtr, mode: ModeT, flags: CInt): CInt =
    rustlangLibcUnixFchmodat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode, flags)

public actual fun fchown(fd: CInt, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT): CInt =
    rustlangLibcUnixFchown(fd, owner, group)

public actual fun fchownat(dirfd: CInt, pathname: ConstPtr, owner: io.github.kotlinmania.rustlang.unix.UidT, group: io.github.kotlinmania.rustlang.unix.GidT, flags: CInt): CInt =
    rustlangLibcUnixFchownat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), owner, group, flags)

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixOpenat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), flags, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    rustlangLibcUnixFstatat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixLinkat(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixMkdirat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    rustlangLibcUnixRenameat(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>())

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    rustlangLibcUnixSymlinkat(target.rawAddress.toCPointer<ByteVar>(), newdirfd, linkpath.rawAddress.toCPointer<ByteVar>())

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixUnlinkat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    rustlangLibcUnixAccess(path.rawAddress.toCPointer<ByteVar>(), amode)

public actual fun alarm(seconds: CUint): CUint =
    rustlangLibcUnixAlarm(seconds)

public actual fun chdir(dir: ConstPtr): CInt =
    rustlangLibcUnixChdir(dir.rawAddress.toCPointer<ByteVar>())

public actual fun fchdir(dirfd: CInt): CInt =
    rustlangLibcUnixFchdir(dirfd)

public actual fun chown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    rustlangLibcUnixChown(path.rawAddress.toCPointer<ByteVar>(), uid, gid)

public actual fun lchown(path: ConstPtr, uid: UidT, gid: GidT): CInt =
    rustlangLibcUnixLchown(path.rawAddress.toCPointer<ByteVar>(), uid, gid)

public actual fun close(fd: CInt): CInt =
    rustlangLibcUnixClose(fd)

public actual fun dup(fd: CInt): CInt =
    rustlangLibcUnixDup(fd)

public actual fun dup2(src: CInt, dst: CInt): CInt =
    rustlangLibcUnixDup2(src, dst)

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixExecl(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixExecle(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execlp(file: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcUnixExeclp(file.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execv(prog: ConstPtr, argv: ConstPtr): CInt =
    rustlangLibcUnixExecv(prog.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>())

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcUnixExecve(prog.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    rustlangLibcUnixExecvp(c.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>())

public actual fun fork(): PidT =
    rustlangLibcUnixFork()

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    rustlangLibcUnixFpathconf(filedes, name)

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixGetcwd(buf.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun getegid(): GidT =
    rustlangLibcUnixGetegid()

public actual fun geteuid(): UidT =
    rustlangLibcUnixGeteuid()

public actual fun getgid(): GidT =
    rustlangLibcUnixGetgid()

public actual fun getgroups(ngroupsMax: CInt, groups: MutPtr): CInt =
    rustlangLibcUnixGetgroups(ngroupsMax, groups.rawAddress.toCPointer<ByteVar>())

public actual fun getlogin(): MutPtr =
    MutPtr(rustlangLibcUnixGetlogin()?.toLong() ?: 0L)

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    rustlangLibcUnixGetopt(argc, argv.rawAddress.toCPointer<ByteVar>(), optstr.rawAddress.toCPointer<ByteVar>())

public actual fun getpgid(pid: PidT): PidT =
    rustlangLibcUnixGetpgid(pid)

public actual fun getpgrp(): PidT =
    rustlangLibcUnixGetpgrp()

public actual fun getpid(): PidT =
    rustlangLibcUnixGetpid()

public actual fun getppid(): PidT =
    rustlangLibcUnixGetppid()

public actual fun getuid(): UidT =
    rustlangLibcUnixGetuid()

public actual fun isatty(fd: CInt): CInt =
    rustlangLibcUnixIsatty(fd)

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    rustlangLibcUnixLink(src.rawAddress.toCPointer<ByteVar>(), dst.rawAddress.toCPointer<ByteVar>())

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    rustlangLibcUnixLseek(fd, offset, whence)

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    rustlangLibcUnixPathconf(path.rawAddress.toCPointer<ByteVar>(), name)

public actual fun pause(): CInt =
    rustlangLibcUnixPause()

public actual fun pipe(fds: MutPtr): CInt =
    rustlangLibcUnixPipe(fds.rawAddress.toCPointer<ByteVar>())

public actual fun posixMemalign(memptr: MutPtr, align: SizeT, size: SizeT): CInt =
    rustlangLibcUnixPosixMemalign(memptr.rawAddress.toCPointer<ByteVar>(), align, size)

public actual fun alignedAlloc(alignment: SizeT, size: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixAlignedAlloc(alignment, size)?.toLong() ?: 0L)

public actual fun read(fd: CInt, buf: MutPtr, count: SizeT): SsizeT =
    rustlangLibcUnixRead(fd, buf.rawAddress.toCPointer<ByteVar>(), count)

public actual fun rmdir(path: ConstPtr): CInt =
    rustlangLibcUnixRmdir(path.rawAddress.toCPointer<ByteVar>())

public actual fun seteuid(uid: UidT): CInt =
    rustlangLibcUnixSeteuid(uid)

public actual fun setegid(gid: GidT): CInt =
    rustlangLibcUnixSetegid(gid)

public actual fun setgid(gid: GidT): CInt =
    rustlangLibcUnixSetgid(gid)

public actual fun setpgid(pid: PidT, pgid: PidT): CInt =
    rustlangLibcUnixSetpgid(pid, pgid)

public actual fun setsid(): PidT =
    rustlangLibcUnixSetsid()

public actual fun setuid(uid: UidT): CInt =
    rustlangLibcUnixSetuid(uid)

public actual fun setreuid(ruid: UidT, euid: UidT): CInt =
    rustlangLibcUnixSetreuid(ruid, euid)

public actual fun setregid(rgid: GidT, egid: GidT): CInt =
    rustlangLibcUnixSetregid(rgid, egid)

public actual fun sleep(secs: CUint): CUint =
    rustlangLibcUnixSleep(secs)

public actual fun nanosleep(rqtp: ConstPtr, rmtp: MutPtr): CInt =
    rustlangLibcUnixNanosleep(rqtp.rawAddress.toCPointer<ByteVar>(), rmtp.rawAddress.toCPointer<ByteVar>())

public actual fun tcgetpgrp(fd: CInt): PidT =
    rustlangLibcUnixTcgetpgrp(fd)

public actual fun tcsetpgrp(fd: CInt, pgrp: io.github.kotlinmania.rustlang.unix.PidT): CInt =
    rustlangLibcUnixTcsetpgrp(fd, pgrp)

public actual fun ttyname(fd: CInt): MutPtr =
    MutPtr(rustlangLibcUnixTtyname(fd)?.toLong() ?: 0L)

public actual fun ttynameR(fd: CInt, buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcUnixTtynameR(fd, buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun unlink(c: ConstPtr): CInt =
    rustlangLibcUnixUnlink(c.rawAddress.toCPointer<ByteVar>())

public actual fun wait(status: MutPtr): PidT =
    rustlangLibcUnixWait(status.rawAddress.toCPointer<ByteVar>())

public actual fun waitpid(pid: PidT, status: MutPtr, options: CInt): PidT =
    rustlangLibcUnixWaitpid(pid, status.rawAddress.toCPointer<ByteVar>(), options)

public actual fun write(fd: CInt, buf: ConstPtr, count: SizeT): SsizeT =
    rustlangLibcUnixWrite(fd, buf.rawAddress.toCPointer<ByteVar>(), count)

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    rustlangLibcUnixPread(fd, buf.rawAddress.toCPointer<ByteVar>(), count, offset)

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    rustlangLibcUnixPwrite(fd, buf.rawAddress.toCPointer<ByteVar>(), count, offset)

public actual fun umask(mask: ModeT): ModeT =
    rustlangLibcUnixUmask(mask)

public actual fun utime(file: ConstPtr, buf: ConstPtr): CInt =
    rustlangLibcUnixUtime(file.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun kill(pid: PidT, sig: CInt): CInt =
    rustlangLibcUnixKill(pid, sig)

public actual fun killpg(pgrp: PidT, sig: CInt): CInt =
    rustlangLibcUnixKillpg(pgrp, sig)

public actual fun mlock(addr: ConstPtr, len: SizeT): CInt =
    rustlangLibcUnixMlock(addr.rawAddress.toCPointer<ByteVar>(), len)

public actual fun munlock(addr: ConstPtr, len: SizeT): CInt =
    rustlangLibcUnixMunlock(addr.rawAddress.toCPointer<ByteVar>(), len)

public actual fun mlockall(flags: CInt): CInt =
    rustlangLibcUnixMlockall(flags)

public actual fun munlockall(): CInt =
    rustlangLibcUnixMunlockall()

public actual fun mmap(addr: MutPtr, len: SizeT, prot: CInt, flags: CInt, fd: CInt, offset: OffT): MutPtr =
    MutPtr(rustlangLibcUnixMmap(addr.rawAddress.toCPointer<ByteVar>(), len, prot, flags, fd, offset)?.toLong() ?: 0L)

public actual fun munmap(addr: MutPtr, len: SizeT): CInt =
    rustlangLibcUnixMunmap(addr.rawAddress.toCPointer<ByteVar>(), len)

public actual fun ifNametoindex(ifname: ConstPtr): CUint =
    rustlangLibcUnixIfNametoindex(ifname.rawAddress.toCPointer<ByteVar>())

public actual fun ifIndextoname(ifindex: CUint, ifname: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixIfIndextoname(ifindex, ifname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixLstat(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fsync(fd: CInt): CInt =
    rustlangLibcUnixFsync(fd)

public actual fun setenv(name: ConstPtr, value: ConstPtr, overwrite: CInt): CInt =
    rustlangLibcUnixSetenv(name.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>(), overwrite)

public actual fun unsetenv(name: ConstPtr): CInt =
    rustlangLibcUnixUnsetenv(name.rawAddress.toCPointer<ByteVar>())

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    rustlangLibcUnixSymlink(path1.rawAddress.toCPointer<ByteVar>(), path2.rawAddress.toCPointer<ByteVar>())

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    rustlangLibcUnixTruncate(path.rawAddress.toCPointer<ByteVar>(), length)

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    rustlangLibcUnixFtruncate(fd, length)

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    rustlangLibcUnixSignal(signum, handler)

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    rustlangLibcUnixGetrusage(resource, usage.rawAddress.toCPointer<ByteVar>())

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixRealpath(pathname.rawAddress.toCPointer<ByteVar>(), resolved.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun times(buf: MutPtr): ClockT =
    rustlangLibcUnixTimes(buf.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSelf(): PthreadT =
    (rustlangLibcUnixPthreadSelf()?.toLong() ?: 0L).toULong()

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    rustlangLibcUnixPthreadEqual(t1.toLong().toCPointer<ByteVar>(), t2.toLong().toCPointer<ByteVar>())

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    rustlangLibcUnixPthreadJoin(native.toLong().toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadExit(value: MutPtr): Nothing {
    rustlangLibcUnixPthreadExit(value.rawAddress.toCPointer<ByteVar>())
    error("Unreachable noreturn C function pthreadExit")
}

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    rustlangLibcUnixPthreadAttrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    rustlangLibcUnixPthreadAttrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    rustlangLibcUnixPthreadAttrGetstacksize(attr.rawAddress.toCPointer<ByteVar>(), stacksize.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    rustlangLibcUnixPthreadAttrSetstacksize(attr.rawAddress.toCPointer<ByteVar>(), stackSize)

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    rustlangLibcUnixPthreadAttrSetdetachstate(attr.rawAddress.toCPointer<ByteVar>(), state)

public actual fun pthreadDetach(thread: PthreadT): CInt =
    rustlangLibcUnixPthreadDetach(thread.toLong().toCPointer<ByteVar>())

public actual fun schedYield(): CInt =
    rustlangLibcUnixSchedYield()

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    rustlangLibcUnixPthreadKeyCreate(key.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    rustlangLibcUnixPthreadKeyDelete(key)

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(rustlangLibcUnixPthreadGetspecific(key)?.toLong() ?: 0L)

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    rustlangLibcUnixPthreadSetspecific(key, value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    rustlangLibcUnixPthreadMutexInit(lock.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadMutexDestroy(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadMutexLock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadMutexTrylock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadMutexUnlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    rustlangLibcUnixPthreadMutexattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    rustlangLibcUnixPthreadMutexattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    rustlangLibcUnixPthreadMutexattrSettype(attr.rawAddress.toCPointer<ByteVar>(), type)

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    rustlangLibcUnixPthreadCondInit(cond.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    rustlangLibcUnixPthreadCondWait(cond.rawAddress.toCPointer<ByteVar>(), lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    rustlangLibcUnixPthreadCondTimedwait(cond.rawAddress.toCPointer<ByteVar>(), lock.rawAddress.toCPointer<ByteVar>(), abstime.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    rustlangLibcUnixPthreadCondSignal(cond.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    rustlangLibcUnixPthreadCondBroadcast(cond.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    rustlangLibcUnixPthreadCondDestroy(cond.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    rustlangLibcUnixPthreadCondattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    rustlangLibcUnixPthreadCondattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    rustlangLibcUnixPthreadRwlockInit(lock.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadRwlockDestroy(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadRwlockRdlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadRwlockTryrdlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadRwlockWrlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadRwlockTrywrlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    rustlangLibcUnixPthreadRwlockUnlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    rustlangLibcUnixPthreadRwlockattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    rustlangLibcUnixPthreadRwlockattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun getsockopt(sockfd: CInt, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    rustlangLibcUnixGetsockopt(sockfd, level, optname, optval.rawAddress.toCPointer<ByteVar>(), optlen.rawAddress.toCPointer<ByteVar>())

public actual fun raise(signum: CInt): CInt =
    rustlangLibcUnixRaise(signum)

public actual fun utimes(filename: ConstPtr, times: ConstPtr): CInt =
    rustlangLibcUnixUtimes(filename.rawAddress.toCPointer<ByteVar>(), times.rawAddress.toCPointer<ByteVar>())

public actual fun dlopen(filename: ConstPtr, flag: CInt): MutPtr =
    MutPtr(rustlangLibcUnixDlopen(filename.rawAddress.toCPointer<ByteVar>(), flag)?.toLong() ?: 0L)

public actual fun dlerror(): MutPtr =
    MutPtr(rustlangLibcUnixDlerror()?.toLong() ?: 0L)

public actual fun dlsym(handle: MutPtr, symbol: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixDlsym(handle.rawAddress.toCPointer<ByteVar>(), symbol.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun dlclose(handle: MutPtr): CInt =
    rustlangLibcUnixDlclose(handle.rawAddress.toCPointer<ByteVar>())

public actual fun getaddrinfo(node: ConstPtr, service: ConstPtr, hints: ConstPtr, res: MutPtr): CInt =
    rustlangLibcUnixGetaddrinfo(node.rawAddress.toCPointer<ByteVar>(), service.rawAddress.toCPointer<ByteVar>(), hints.rawAddress.toCPointer<ByteVar>(), res.rawAddress.toCPointer<ByteVar>())

public actual fun freeaddrinfo(res: MutPtr) {
    rustlangLibcUnixFreeaddrinfo(res.rawAddress.toCPointer<ByteVar>())
}

public actual fun hstrerror(errcode: CInt): ConstPtr =
    ConstPtr(rustlangLibcUnixHstrerror(errcode)?.toLong() ?: 0L)

public actual fun gaiStrerror(errcode: CInt): ConstPtr =
    ConstPtr(rustlangLibcUnixGaiStrerror(errcode)?.toLong() ?: 0L)

public actual fun resInit(): CInt =
    rustlangLibcUnixResInit()

public actual fun gmtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixGmtimeR(timeP.rawAddress.toCPointer<ByteVar>(), result.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun localtimeR(timeP: ConstPtr, result: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixLocaltimeR(timeP.rawAddress.toCPointer<ByteVar>(), result.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun mktime(tm: MutPtr): TimeT =
    rustlangLibcUnixMktime(tm.rawAddress.toCPointer<ByteVar>())

public actual fun time(time: MutPtr): TimeT =
    rustlangLibcUnixTime(time.rawAddress.toCPointer<ByteVar>())

public actual fun gmtime(timeP: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixGmtime(timeP.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun localtime(timeP: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixLocaltime(timeP.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun difftime(time1: TimeT, time0: TimeT): CDouble =
    rustlangLibcUnixDifftime(time1, time0)

public actual fun timegm(tm: MutPtr): TimeT =
    rustlangLibcUnixTimegm(tm.rawAddress.toCPointer<ByteVar>())

public actual fun mknod(pathname: ConstPtr, mode: ModeT, dev: DevT): CInt =
    rustlangLibcUnixMknod(pathname.rawAddress.toCPointer<ByteVar>(), mode, dev)

public actual fun gethostname(name: MutPtr, len: SizeT): CInt =
    rustlangLibcUnixGethostname(name.rawAddress.toCPointer<ByteVar>(), len)

public actual fun endservent() {
    rustlangLibcUnixEndservent()
}

public actual fun getservbyname(name: ConstPtr, proto: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixGetservbyname(name.rawAddress.toCPointer<ByteVar>(), proto.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getservbyport(port: CInt, proto: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixGetservbyport(port, proto.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getservent(): MutPtr =
    MutPtr(rustlangLibcUnixGetservent()?.toLong() ?: 0L)

public actual fun setservent(stayopen: CInt) {
    rustlangLibcUnixSetservent(stayopen)
}

public actual fun getprotobyname(name: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixGetprotobyname(name.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getprotobynumber(proto: CInt): MutPtr =
    MutPtr(rustlangLibcUnixGetprotobynumber(proto)?.toLong() ?: 0L)

public actual fun chroot(name: ConstPtr): CInt =
    rustlangLibcUnixChroot(name.rawAddress.toCPointer<ByteVar>())

public actual fun usleep(secs: CUint): CInt =
    rustlangLibcUnixUsleep(secs)

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    rustlangLibcUnixSend(socket, buf.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    rustlangLibcUnixRecv(socket, buf.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun putenv(string: MutPtr): CInt =
    rustlangLibcUnixPutenv(string.rawAddress.toCPointer<ByteVar>())

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    rustlangLibcUnixPoll(fds.rawAddress.toCPointer<ByteVar>(), nfds, timeout)

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr): CInt =
    rustlangLibcUnixSelect(nfds, readfds.rawAddress.toCPointer<ByteVar>(), writefds.rawAddress.toCPointer<ByteVar>(), errorfds.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>())

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixSetlocale(category, locale.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun localeconv(): MutPtr =
    MutPtr(rustlangLibcUnixLocaleconv()?.toLong() ?: 0L)

public actual fun semWait(sem: MutPtr): CInt =
    rustlangLibcUnixSemWait(sem.rawAddress.toCPointer<ByteVar>())

public actual fun semTrywait(sem: MutPtr): CInt =
    rustlangLibcUnixSemTrywait(sem.rawAddress.toCPointer<ByteVar>())

public actual fun semPost(sem: MutPtr): CInt =
    rustlangLibcUnixSemPost(sem.rawAddress.toCPointer<ByteVar>())

public actual fun statvfs(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcUnixStatvfs(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fstatvfs(fd: CInt, buf: MutPtr): CInt =
    rustlangLibcUnixFstatvfs(fd, buf.rawAddress.toCPointer<ByteVar>())

public actual fun sigemptyset(set: MutPtr): CInt =
    rustlangLibcUnixSigemptyset(set.rawAddress.toCPointer<ByteVar>())

public actual fun sigaddset(set: MutPtr, signum: CInt): CInt =
    rustlangLibcUnixSigaddset(set.rawAddress.toCPointer<ByteVar>(), signum)

public actual fun sigfillset(set: MutPtr): CInt =
    rustlangLibcUnixSigfillset(set.rawAddress.toCPointer<ByteVar>())

public actual fun sigdelset(set: MutPtr, signum: CInt): CInt =
    rustlangLibcUnixSigdelset(set.rawAddress.toCPointer<ByteVar>(), signum)

public actual fun sigismember(set: ConstPtr, signum: CInt): CInt =
    rustlangLibcUnixSigismember(set.rawAddress.toCPointer<ByteVar>(), signum)

public actual fun sigprocmask(how: CInt, set: ConstPtr, oldset: MutPtr): CInt =
    rustlangLibcUnixSigprocmask(how, set.rawAddress.toCPointer<ByteVar>(), oldset.rawAddress.toCPointer<ByteVar>())

public actual fun sigpending(set: MutPtr): CInt =
    rustlangLibcUnixSigpending(set.rawAddress.toCPointer<ByteVar>())

public actual fun sysconf(name: CInt): CLong =
    rustlangLibcUnixSysconf(name)

public actual fun mkfifo(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcUnixMkfifo(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    rustlangLibcUnixFseeko(stream.rawAddress.toCPointer<ByteVar>(), offset, whence)

public actual fun ftello(stream: MutPtr): OffT =
    rustlangLibcUnixFtello(stream.rawAddress.toCPointer<ByteVar>())

public actual fun tcdrain(fd: CInt): CInt =
    rustlangLibcUnixTcdrain(fd)

public actual fun cfgetispeed(termios: ConstPtr): SpeedT =
    rustlangLibcUnixCfgetispeed(termios.rawAddress.toCPointer<ByteVar>())

public actual fun cfgetospeed(termios: ConstPtr): SpeedT =
    rustlangLibcUnixCfgetospeed(termios.rawAddress.toCPointer<ByteVar>())

public actual fun cfsetispeed(termios: MutPtr, speed: SpeedT): CInt =
    rustlangLibcUnixCfsetispeed(termios.rawAddress.toCPointer<ByteVar>(), speed)

public actual fun cfsetospeed(termios: MutPtr, speed: SpeedT): CInt =
    rustlangLibcUnixCfsetospeed(termios.rawAddress.toCPointer<ByteVar>(), speed)

public actual fun tcgetattr(fd: CInt, termios: MutPtr): CInt =
    rustlangLibcUnixTcgetattr(fd, termios.rawAddress.toCPointer<ByteVar>())

public actual fun tcsetattr(fd: CInt, optionalActions: CInt, termios: ConstPtr): CInt =
    rustlangLibcUnixTcsetattr(fd, optionalActions, termios.rawAddress.toCPointer<ByteVar>())

public actual fun tcflow(fd: CInt, action: CInt): CInt =
    rustlangLibcUnixTcflow(fd, action)

public actual fun tcflush(fd: CInt, action: CInt): CInt =
    rustlangLibcUnixTcflush(fd, action)

public actual fun tcgetsid(fd: CInt): io.github.kotlinmania.rustlang.unix.PidT =
    rustlangLibcUnixTcgetsid(fd)

public actual fun tcsendbreak(fd: CInt, duration: CInt): CInt =
    rustlangLibcUnixTcsendbreak(fd, duration)

public actual fun mkstemp(template: MutPtr): CInt =
    rustlangLibcUnixMkstemp(template.rawAddress.toCPointer<ByteVar>())

public actual fun mkdtemp(template: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixMkdtemp(template.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun tmpnam(ptr: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixTmpnam(ptr.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun openlog(ident: ConstPtr, logopt: CInt, facility: CInt) {
    rustlangLibcUnixOpenlog(ident.rawAddress.toCPointer<ByteVar>(), logopt, facility)
}

public actual fun closelog() {
    rustlangLibcUnixCloselog()
}

public actual fun setlogmask(maskpri: CInt): CInt =
    rustlangLibcUnixSetlogmask(maskpri)

public actual fun syslog(priority: CInt, message: ConstPtr, vararg variadicArgs: Long) {
    rustlangLibcUnixSyslog(priority, message.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)
}

public actual fun nice(incr: CInt): CInt =
    rustlangLibcUnixNice(incr)

public actual fun grantpt(fd: CInt): CInt =
    rustlangLibcUnixGrantpt(fd)

public actual fun posixOpenpt(flags: CInt): CInt =
    rustlangLibcUnixPosixOpenpt(flags)

public actual fun ptsname(fd: CInt): MutPtr =
    MutPtr(rustlangLibcUnixPtsname(fd)?.toLong() ?: 0L)

public actual fun unlockpt(fd: CInt): CInt =
    rustlangLibcUnixUnlockpt(fd)

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixStrcasestr(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    rustlangLibcUnixGetline(lineptr.rawAddress.toCPointer<ByteVar>(), n.rawAddress.toCPointer<ByteVar>(), stream.rawAddress.toCPointer<ByteVar>())

public actual fun lockf(fd: CInt, cmd: CInt, len: OffT): CInt =
    rustlangLibcUnixLockf(fd, cmd, len)

public actual fun adjtime(delta: ConstPtr, olddelta: MutPtr): CInt =
    rustlangLibcUnixAdjtime(delta.rawAddress.toCPointer<ByteVar>(), olddelta.rawAddress.toCPointer<ByteVar>())

public actual fun stpncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcUnixStpncpy(dst.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun sigqueue(pid: PidT, sig: CInt, value: io.github.kotlinmania.rustlang.unix.Sigval): CInt =
    0

public actual fun confstr(name: CInt, buf: MutPtr, len: SizeT): SizeT =
    rustlangLibcUnixConfstr(name, buf.rawAddress.toCPointer<ByteVar>(), len)

public actual fun dladdr(addr: ConstPtr, info: MutPtr): CInt =
    rustlangLibcUnixDladdr(addr.rawAddress.toCPointer<ByteVar>(), info.rawAddress.toCPointer<ByteVar>())

public actual fun flock(fd: CInt, operation: CInt): CInt =
    rustlangLibcUnixFlock(fd, operation)

public actual fun openWmemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixOpenWmemstream(ptr.rawAddress.toCPointer<ByteVar>(), sizeloc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getsid(pid: PidT): PidT =
    rustlangLibcUnixGetsid(pid)

public actual fun readdirR(dirp: MutPtr, entry: MutPtr, result: MutPtr): CInt =
    rustlangLibcUnixReaddirR(dirp.rawAddress.toCPointer<ByteVar>(), entry.rawAddress.toCPointer<ByteVar>(), result.rawAddress.toCPointer<ByteVar>())

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): CInt =
    rustlangLibcUnixReadlinkat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), bufsiz)

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): CInt =
    rustlangLibcUnixReadlink(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), bufsz)

public actual fun pselect(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: MutPtr, sigmask: ConstPtr): CInt =
    rustlangLibcUnixPselect(nfds, readfds.rawAddress.toCPointer<ByteVar>(), writefds.rawAddress.toCPointer<ByteVar>(), errorfds.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>(), sigmask.rawAddress.toCPointer<ByteVar>())

public actual fun sigaction(signum: CInt, act: ConstPtr, oldact: MutPtr): CInt =
    rustlangLibcUnixSigaction(signum, act.rawAddress.toCPointer<ByteVar>(), oldact.rawAddress.toCPointer<ByteVar>())

public actual fun fmemopen(buf: MutPtr, size: SizeT, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcUnixFmemopen(buf.rawAddress.toCPointer<ByteVar>(), size, mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun openMemstream(ptr: MutPtr, sizeloc: MutPtr): MutPtr =
    MutPtr(rustlangLibcUnixOpenMemstream(ptr.rawAddress.toCPointer<ByteVar>(), sizeloc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun atexit(cb: AtexitCbCallback): CInt =
    rustlangLibcUnixAtexit(0L.toCPointer<ByteVar>())

public actual fun cfmakeraw(termios: MutPtr) {
    rustlangLibcUnixCfmakeraw(termios.rawAddress.toCPointer<ByteVar>())
}

public actual fun cfsetspeed(termios: MutPtr, speed: SpeedT): CInt =
    rustlangLibcUnixCfsetspeed(termios.rawAddress.toCPointer<ByteVar>(), speed)

public actual fun fnmatch(pattern: ConstPtr, name: ConstPtr, flags: CInt): CInt =
    rustlangLibcUnixFnmatch(pattern.rawAddress.toCPointer<ByteVar>(), name.rawAddress.toCPointer<ByteVar>(), flags)
