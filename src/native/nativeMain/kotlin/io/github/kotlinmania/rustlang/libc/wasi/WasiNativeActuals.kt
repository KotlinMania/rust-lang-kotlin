@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.wasi

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
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAbort
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAbs
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAccess
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAlignedAlloc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiArc4random
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiArc4randomBuf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiArc4randomUniform
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAsctimeR
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAtQuickExit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAtexit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAtof
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAtoi
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAtol
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiAtoll
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiCalloc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiChdir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiChmod
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiClearenv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiClearerr
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiClock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiClose
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiClosedir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiCreat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiCtimeR
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiDifftime
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiDirfd
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiDuplocale
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiErrnoLocation
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiExit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFaccessat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFchmod
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFclose
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFcntl
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFdatasync
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFdopen
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFdopendir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFeof
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFerror
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFflush
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFgetc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFgetpos
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFgets
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFileno
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFopen
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFpathconf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFprintf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFputc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFputs
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFread
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFree
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFreelocale
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFreopen
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFscanf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFseek
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFseeko
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFsetpos
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFstat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFstatat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFsync
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFtell
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFtello
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFtruncate
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFutimens
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiFwrite
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetCLOCKMONOTONIC1
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetCLOCKREALTIME1
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetENVIRON
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetchar
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetcharUnlocked
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetcwd
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetentropy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetenv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetline
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetopt
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGetrusage
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGettimeofday
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGmtime
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiGmtimeR
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIoctl
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsalnum
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsalpha
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsatty
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsblank
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIscntrl
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsdigit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsgraph
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIslower
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsprint
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIspunct
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsspace
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsupper
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiIsxdigit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiLabs
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiLink
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiLinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiLocaleconv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiLocaltime
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiLocaltimeR
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiLseek
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiLstat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMalloc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMallocUsableSize
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMemchr
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMemcmp
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMemcpy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMemmove
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMemrchr
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMemset
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMkdir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMkdirat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiMktime
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiNanosleep
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiNewlocale
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiNlLanginfo
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiNlLanginfoL
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiOpen
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiOpenat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiOpendir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPathconf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPerror
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPoll
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPosixFadvise
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPosixFallocate
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPosixMemalign
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPread
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPreadv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPrintf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrGetdetachstate
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrGetguardsize
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrGetstack
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrGetstacksize
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrSetdetachstate
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrSetguardsize
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrSetstack
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadAttrSetstacksize
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadBarrierDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadBarrierInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadBarrierWait
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadBarrierattrDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadBarrierattrGetpshared
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadBarrierattrInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadBarrierattrSetpshared
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondBroadcast
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondSignal
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondTimedwait
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondWait
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondattrDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondattrGetclock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondattrGetpshared
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondattrInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCondattrSetpshared
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadCreate
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadDetach
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadEqual
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadGetattrNp
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadGetspecific
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadJoin
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadKeyCreate
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadKeyDelete
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexConsistent
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexGetprioceiling
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexLock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexTimedlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexTrylock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexUnlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrGetprotocol
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrGetpshared
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrGetrobust
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrGettype
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrSetprotocol
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrSetpshared
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrSetrobust
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadMutexattrSettype
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadOnce
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockRdlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockTimedrdlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockTimedwrlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockTryrdlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockTrywrlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockUnlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockWrlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockattrDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockattrGetpshared
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockattrInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadRwlockattrSetpshared
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSelf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSetcancelstate
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSetcanceltype
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSetspecific
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSpinDestroy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSpinInit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSpinLock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSpinTrylock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadSpinUnlock
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPthreadTestcancel
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPutc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPutchar
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPutcharUnlocked
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPutenv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPuts
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPwrite
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiPwritev
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiQuickExit
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRand
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRandR
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRandom
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRead
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiReaddir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiReadlink
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiReadlinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiReadv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRealloc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRealpath
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRecv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRemove
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRename
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRenameat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRewind
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRewinddir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiRmdir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSbrk
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiScanf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSchedYield
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSeekdir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSelect
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSend
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSetENVIRON
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSetbuf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSetenv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSetlocale
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSetvbuf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiShutdown
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSleep
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSnprintf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSprintf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSrand
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSrandom
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSscanf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrcasecmp
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrcasestr
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrcat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrchr
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrcmp
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrcoll
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrcpy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrcspn
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrdup
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrerror
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrerrorR
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrftime
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrlen
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrncasecmp
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrncat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrncmp
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrncpy
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrndup
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrnlen
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrpbrk
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrrchr
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrspn
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrstr
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrtod
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrtof
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrtok
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrtol
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrtoll
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrtoul
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrtoull
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiStrxfrm
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSymlink
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSymlinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiSysconf
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiTelldir
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiTime
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiTimegm
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiTimes
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiTolower
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiToupper
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiTruncate
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiUngetc
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiUnlink
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiUnlinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiUnsetenv
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiUselocale
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiUsleep
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiUtimensat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcAccess
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcFdRenumber
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcFindRelpath
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcLink
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcLinkNewat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcLinkOldat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdFaccessat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdFstatat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdLinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdMkdiratNomode
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdOpenatNomode
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdOpendirat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdReadlinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdRenameat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdSymlinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdUtimensat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdWasilibcRmdirat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcNocwdWasilibcUnlinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcRegisterPreopenedFd
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcRenameNewat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcRenameOldat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcRmdirat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcStat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcTell
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcUnlinkat
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWasilibcUtimens
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWrite
import io.github.kotlinmania.rustlang.libc.wasi.cinterop.rustlangLibcWasiWritev
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual var ENVIRON: MutPtr
    get() = MutPtr(rustlangLibcWasiGetENVIRON()?.toLong() ?: 0L)
    set(value) {
        rustlangLibcWasiSetENVIRON(value.rawAddress.toCPointer<ByteVar>())
    }

public actual val CLOCK_MONOTONIC1: UByte
    get() = rustlangLibcWasiGetCLOCKMONOTONIC1()

public actual val CLOCK_REALTIME1: UByte
    get() = rustlangLibcWasiGetCLOCKREALTIME1()

public actual fun exit(code: CInt): Nothing {
    rustlangLibcWasiExit(code)
    error("Unreachable noreturn C function exit")
}

public actual fun abort(): Nothing {
    rustlangLibcWasiAbort()
    error("Unreachable noreturn C function abort")
}

public actual fun alignedAlloc(a: SizeT, b: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiAlignedAlloc(a, b)?.toLong() ?: 0L)

public actual fun calloc(amt: SizeT, amt2: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiCalloc(amt, amt2)?.toLong() ?: 0L)

public actual fun free(ptr: MutPtr) {
    rustlangLibcWasiFree(ptr.rawAddress.toCPointer<ByteVar>())
}

public actual fun getenv(s: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiGetenv(s.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun malloc(amt: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiMalloc(amt)?.toLong() ?: 0L)

public actual fun mallocUsableSize(ptr: MutPtr): SizeT =
    rustlangLibcWasiMallocUsableSize(ptr.rawAddress.toCPointer<ByteVar>())

public actual fun sbrk(increment: IntptrT): MutPtr =
    MutPtr(rustlangLibcWasiSbrk(increment)?.toLong() ?: 0L)

public actual fun rand(): CInt =
    rustlangLibcWasiRand()

public actual fun read(fd: CInt, ptr: MutPtr, size: SizeT): SsizeT =
    rustlangLibcWasiRead(fd, ptr.rawAddress.toCPointer<ByteVar>(), size)

public actual fun realloc(ptr: MutPtr, amt: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiRealloc(ptr.rawAddress.toCPointer<ByteVar>(), amt)?.toLong() ?: 0L)

public actual fun setenv(k: ConstPtr, v: ConstPtr, a: CInt): CInt =
    rustlangLibcWasiSetenv(k.rawAddress.toCPointer<ByteVar>(), v.rawAddress.toCPointer<ByteVar>(), a)

public actual fun unsetenv(k: ConstPtr): CInt =
    rustlangLibcWasiUnsetenv(k.rawAddress.toCPointer<ByteVar>())

public actual fun clearenv(): CInt =
    rustlangLibcWasiClearenv()

public actual fun write(fd: CInt, ptr: ConstPtr, size: SizeT): SsizeT =
    rustlangLibcWasiWrite(fd, ptr.rawAddress.toCPointer<ByteVar>(), size)

public actual fun fopen(a: ConstPtr, b: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiFopen(a.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun freopen(a: ConstPtr, b: ConstPtr, f: MutPtr): MutPtr =
    MutPtr(rustlangLibcWasiFreopen(a.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>(), f.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fclose(f: MutPtr): CInt =
    rustlangLibcWasiFclose(f.rawAddress.toCPointer<ByteVar>())

public actual fun remove(a: ConstPtr): CInt =
    rustlangLibcWasiRemove(a.rawAddress.toCPointer<ByteVar>())

public actual fun rename(a: ConstPtr, b: ConstPtr): CInt =
    rustlangLibcWasiRename(a.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun feof(f: MutPtr): CInt =
    rustlangLibcWasiFeof(f.rawAddress.toCPointer<ByteVar>())

public actual fun ferror(f: MutPtr): CInt =
    rustlangLibcWasiFerror(f.rawAddress.toCPointer<ByteVar>())

public actual fun fflush(f: MutPtr): CInt =
    rustlangLibcWasiFflush(f.rawAddress.toCPointer<ByteVar>())

public actual fun clearerr(f: MutPtr) {
    rustlangLibcWasiClearerr(f.rawAddress.toCPointer<ByteVar>())
}

public actual fun fseek(f: MutPtr, b: CLong, c: CInt): CInt =
    rustlangLibcWasiFseek(f.rawAddress.toCPointer<ByteVar>(), b, c)

public actual fun ftell(f: MutPtr): CLong =
    rustlangLibcWasiFtell(f.rawAddress.toCPointer<ByteVar>())

public actual fun rewind(f: MutPtr) {
    rustlangLibcWasiRewind(f.rawAddress.toCPointer<ByteVar>())
}

public actual fun fgetpos(f: MutPtr, pos: MutPtr): CInt =
    rustlangLibcWasiFgetpos(f.rawAddress.toCPointer<ByteVar>(), pos.rawAddress.toCPointer<ByteVar>())

public actual fun fsetpos(f: MutPtr, pos: ConstPtr): CInt =
    rustlangLibcWasiFsetpos(f.rawAddress.toCPointer<ByteVar>(), pos.rawAddress.toCPointer<ByteVar>())

public actual fun fread(buf: MutPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    rustlangLibcWasiFread(buf.rawAddress.toCPointer<ByteVar>(), a, b, f.rawAddress.toCPointer<ByteVar>())

public actual fun fwrite(buf: ConstPtr, a: SizeT, b: SizeT, f: MutPtr): SizeT =
    rustlangLibcWasiFwrite(buf.rawAddress.toCPointer<ByteVar>(), a, b, f.rawAddress.toCPointer<ByteVar>())

public actual fun fgetc(f: MutPtr): CInt =
    rustlangLibcWasiFgetc(f.rawAddress.toCPointer<ByteVar>())

public actual fun getc(f: MutPtr): CInt =
    rustlangLibcWasiGetc(f.rawAddress.toCPointer<ByteVar>())

public actual fun getchar(): CInt =
    rustlangLibcWasiGetchar()

public actual fun ungetc(a: CInt, f: MutPtr): CInt =
    rustlangLibcWasiUngetc(a, f.rawAddress.toCPointer<ByteVar>())

public actual fun fputc(a: CInt, f: MutPtr): CInt =
    rustlangLibcWasiFputc(a, f.rawAddress.toCPointer<ByteVar>())

public actual fun putc(a: CInt, f: MutPtr): CInt =
    rustlangLibcWasiPutc(a, f.rawAddress.toCPointer<ByteVar>())

public actual fun putchar(a: CInt): CInt =
    rustlangLibcWasiPutchar(a)

public actual fun fputs(a: ConstPtr, f: MutPtr): CInt =
    rustlangLibcWasiFputs(a.rawAddress.toCPointer<ByteVar>(), f.rawAddress.toCPointer<ByteVar>())

public actual fun puts(a: ConstPtr): CInt =
    rustlangLibcWasiPuts(a.rawAddress.toCPointer<ByteVar>())

public actual fun perror(a: ConstPtr) {
    rustlangLibcWasiPerror(a.rawAddress.toCPointer<ByteVar>())
}

public actual fun srand(a: CUint) {
    rustlangLibcWasiSrand(a)
}

public actual fun atexit(a: AtexitACallback): CInt =
    rustlangLibcWasiAtexit(0L.toCPointer<ByteVar>())

public actual fun atQuickExit(a: AtexitACallback): CInt =
    rustlangLibcWasiAtQuickExit(0L.toCPointer<ByteVar>())

public actual fun quickExit(a: CInt): Nothing {
    rustlangLibcWasiQuickExit(a)
    error("Unreachable noreturn C function quickExit")
}

public actual fun posixMemalign(a: MutPtr, b: SizeT, c: SizeT): CInt =
    rustlangLibcWasiPosixMemalign(a.rawAddress.toCPointer<ByteVar>(), b, c)

public actual fun randR(a: MutPtr): CInt =
    rustlangLibcWasiRandR(a.rawAddress.toCPointer<ByteVar>())

public actual fun random(): CLong =
    rustlangLibcWasiRandom()

public actual fun srandom(a: CUint) {
    rustlangLibcWasiSrandom(a)
}

public actual fun putenv(a: MutPtr): CInt =
    rustlangLibcWasiPutenv(a.rawAddress.toCPointer<ByteVar>())

public actual fun clock(): ClockT =
    rustlangLibcWasiClock()

public actual fun time(a: MutPtr): TimeT =
    rustlangLibcWasiTime(a.rawAddress.toCPointer<ByteVar>())

public actual fun difftime(a: TimeT, b: TimeT): CDouble =
    rustlangLibcWasiDifftime(a, b)

public actual fun mktime(a: MutPtr): TimeT =
    rustlangLibcWasiMktime(a.rawAddress.toCPointer<ByteVar>())

public actual fun strftime(a: MutPtr, b: SizeT, c: ConstPtr, d: ConstPtr): SizeT =
    rustlangLibcWasiStrftime(a.rawAddress.toCPointer<ByteVar>(), b, c.rawAddress.toCPointer<ByteVar>(), d.rawAddress.toCPointer<ByteVar>())

public actual fun gmtime(a: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiGmtime(a.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun gmtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    MutPtr(rustlangLibcWasiGmtimeR(a.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun localtime(a: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiLocaltime(a.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun localtimeR(a: ConstPtr, b: MutPtr): MutPtr =
    MutPtr(rustlangLibcWasiLocaltimeR(a.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun asctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    MutPtr(rustlangLibcWasiAsctimeR(a.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun ctimeR(a: ConstPtr, b: MutPtr): MutPtr =
    MutPtr(rustlangLibcWasiCtimeR(a.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun nanosleep(a: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiNanosleep(a.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun clockGetres(a: ClockidT, b: MutPtr): CInt =
    0

public actual fun clockGettime(a: ClockidT, b: MutPtr): CInt =
    0

public actual fun clockNanosleep(a: ClockidT, a2: CInt, b: ConstPtr, c: MutPtr): CInt =
    0

public actual fun isalnum(c: CInt): CInt =
    rustlangLibcWasiIsalnum(c)

public actual fun isalpha(c: CInt): CInt =
    rustlangLibcWasiIsalpha(c)

public actual fun iscntrl(c: CInt): CInt =
    rustlangLibcWasiIscntrl(c)

public actual fun isdigit(c: CInt): CInt =
    rustlangLibcWasiIsdigit(c)

public actual fun isgraph(c: CInt): CInt =
    rustlangLibcWasiIsgraph(c)

public actual fun islower(c: CInt): CInt =
    rustlangLibcWasiIslower(c)

public actual fun isprint(c: CInt): CInt =
    rustlangLibcWasiIsprint(c)

public actual fun ispunct(c: CInt): CInt =
    rustlangLibcWasiIspunct(c)

public actual fun isspace(c: CInt): CInt =
    rustlangLibcWasiIsspace(c)

public actual fun isupper(c: CInt): CInt =
    rustlangLibcWasiIsupper(c)

public actual fun isxdigit(c: CInt): CInt =
    rustlangLibcWasiIsxdigit(c)

public actual fun isblank(c: CInt): CInt =
    rustlangLibcWasiIsblank(c)

public actual fun tolower(c: CInt): CInt =
    rustlangLibcWasiTolower(c)

public actual fun toupper(c: CInt): CInt =
    rustlangLibcWasiToupper(c)

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    rustlangLibcWasiSetvbuf(stream.rawAddress.toCPointer<ByteVar>(), buffer.rawAddress.toCPointer<ByteVar>(), mode, size)

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    rustlangLibcWasiSetbuf(stream.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())
}

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    MutPtr(rustlangLibcWasiFgets(buf.rawAddress.toCPointer<ByteVar>(), n, stream.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun atof(s: ConstPtr): CDouble =
    rustlangLibcWasiAtof(s.rawAddress.toCPointer<ByteVar>())

public actual fun atoi(s: ConstPtr): CInt =
    rustlangLibcWasiAtoi(s.rawAddress.toCPointer<ByteVar>())

public actual fun atol(s: ConstPtr): CLong =
    rustlangLibcWasiAtol(s.rawAddress.toCPointer<ByteVar>())

public actual fun atoll(s: ConstPtr): CLonglong =
    rustlangLibcWasiAtoll(s.rawAddress.toCPointer<ByteVar>())

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    rustlangLibcWasiStrtod(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>())

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    rustlangLibcWasiStrtof(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>())

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    rustlangLibcWasiStrtol(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    rustlangLibcWasiStrtoll(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    rustlangLibcWasiStrtoul(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    rustlangLibcWasiStrtoull(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiStrcpy(dst.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiStrncpy(dst.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiStrcat(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiStrncat(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    rustlangLibcWasiStrcmp(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    rustlangLibcWasiStrncmp(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    rustlangLibcWasiStrcoll(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(rustlangLibcWasiStrchr(cs.rawAddress.toCPointer<ByteVar>(), c)?.toLong() ?: 0L)

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(rustlangLibcWasiStrrchr(cs.rawAddress.toCPointer<ByteVar>(), c)?.toLong() ?: 0L)

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    rustlangLibcWasiStrspn(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    rustlangLibcWasiStrcspn(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strdup(cs: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiStrdup(cs.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strndup(cs: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiStrndup(cs.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiStrpbrk(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiStrstr(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strcasecmp(s1: ConstPtr, s2: ConstPtr): CInt =
    rustlangLibcWasiStrcasecmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>())

public actual fun strncasecmp(s1: ConstPtr, s2: ConstPtr, n: SizeT): CInt =
    rustlangLibcWasiStrncasecmp(s1.rawAddress.toCPointer<ByteVar>(), s2.rawAddress.toCPointer<ByteVar>(), n)

public actual fun strlen(cs: ConstPtr): SizeT =
    rustlangLibcWasiStrlen(cs.rawAddress.toCPointer<ByteVar>())

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    rustlangLibcWasiStrnlen(cs.rawAddress.toCPointer<ByteVar>(), maxlen)

public actual fun strerror(n: CInt): MutPtr =
    MutPtr(rustlangLibcWasiStrerror(n)?.toLong() ?: 0L)

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiStrtok(s.rawAddress.toCPointer<ByteVar>(), t.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    rustlangLibcWasiStrxfrm(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiMemchr(cx.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    rustlangLibcWasiMemcmp(cx.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiMemcpy(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiMemmove(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiMemset(dest.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiFprintf(stream.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiPrintf(format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun snprintf(s: MutPtr, n: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiSnprintf(s.rawAddress.toCPointer<ByteVar>(), n, format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sprintf(s: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiSprintf(s.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiFscanf(stream.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiScanf(format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sscanf(s: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiSscanf(s.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun getcharUnlocked(): CInt =
    rustlangLibcWasiGetcharUnlocked()

public actual fun putcharUnlocked(c: CInt): CInt =
    rustlangLibcWasiPutcharUnlocked(c)

public actual fun shutdown(socket: CInt, how: CInt): CInt =
    rustlangLibcWasiShutdown(socket, how)

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    rustlangLibcWasiFstat(fildes, buf.rawAddress.toCPointer<ByteVar>())

public actual fun mkdir(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcWasiMkdir(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcWasiStat(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiFdopen(fd, mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fileno(stream: MutPtr): CInt =
    rustlangLibcWasiFileno(stream.rawAddress.toCPointer<ByteVar>())

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiOpen(path.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun creat(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcWasiCreat(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun fcntl(fd: CInt, cmd: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiFcntl(fd, cmd, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun opendir(dirname: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiOpendir(dirname.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fdopendir(fd: CInt): MutPtr =
    MutPtr(rustlangLibcWasiFdopendir(fd)?.toLong() ?: 0L)

public actual fun readdir(dirp: MutPtr): MutPtr =
    MutPtr(rustlangLibcWasiReaddir(dirp.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun closedir(dirp: MutPtr): CInt =
    rustlangLibcWasiClosedir(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun rewinddir(dirp: MutPtr) {
    rustlangLibcWasiRewinddir(dirp.rawAddress.toCPointer<ByteVar>())
}

public actual fun dirfd(dirp: MutPtr): CInt =
    rustlangLibcWasiDirfd(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun seekdir(dirp: MutPtr, loc: CLong) {
    rustlangLibcWasiSeekdir(dirp.rawAddress.toCPointer<ByteVar>(), loc)
}

public actual fun telldir(dirp: MutPtr): CLong =
    rustlangLibcWasiTelldir(dirp.rawAddress.toCPointer<ByteVar>())

public actual fun openat(dirfd: CInt, pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiOpenat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), flags, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fstatat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    rustlangLibcWasiFstatat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun linkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiLinkat(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun mkdirat(dirfd: CInt, pathname: ConstPtr, mode: ModeT): CInt =
    rustlangLibcWasiMkdirat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun readlinkat(dirfd: CInt, pathname: ConstPtr, buf: MutPtr, bufsiz: SizeT): SsizeT =
    rustlangLibcWasiReadlinkat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), bufsiz)

public actual fun renameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    rustlangLibcWasiRenameat(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>())

public actual fun symlinkat(target: ConstPtr, newdirfd: CInt, linkpath: ConstPtr): CInt =
    rustlangLibcWasiSymlinkat(target.rawAddress.toCPointer<ByteVar>(), newdirfd, linkpath.rawAddress.toCPointer<ByteVar>())

public actual fun unlinkat(dirfd: CInt, pathname: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiUnlinkat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    rustlangLibcWasiAccess(path.rawAddress.toCPointer<ByteVar>(), amode)

public actual fun close(fd: CInt): CInt =
    rustlangLibcWasiClose(fd)

public actual fun fpathconf(filedes: CInt, name: CInt): CLong =
    rustlangLibcWasiFpathconf(filedes, name)

public actual fun getopt(argc: CInt, argv: ConstPtr, optstr: ConstPtr): CInt =
    rustlangLibcWasiGetopt(argc, argv.rawAddress.toCPointer<ByteVar>(), optstr.rawAddress.toCPointer<ByteVar>())

public actual fun isatty(fd: CInt): CInt =
    rustlangLibcWasiIsatty(fd)

public actual fun link(src: ConstPtr, dst: ConstPtr): CInt =
    rustlangLibcWasiLink(src.rawAddress.toCPointer<ByteVar>(), dst.rawAddress.toCPointer<ByteVar>())

public actual fun lseek(fd: CInt, offset: OffT, whence: CInt): OffT =
    rustlangLibcWasiLseek(fd, offset, whence)

public actual fun pathconf(path: ConstPtr, name: CInt): CLong =
    rustlangLibcWasiPathconf(path.rawAddress.toCPointer<ByteVar>(), name)

public actual fun rmdir(path: ConstPtr): CInt =
    rustlangLibcWasiRmdir(path.rawAddress.toCPointer<ByteVar>())

public actual fun sleep(secs: CUint): CUint =
    rustlangLibcWasiSleep(secs)

public actual fun unlink(c: ConstPtr): CInt =
    rustlangLibcWasiUnlink(c.rawAddress.toCPointer<ByteVar>())

public actual fun pread(fd: CInt, buf: MutPtr, count: SizeT, offset: OffT): SsizeT =
    rustlangLibcWasiPread(fd, buf.rawAddress.toCPointer<ByteVar>(), count, offset)

public actual fun pwrite(fd: CInt, buf: ConstPtr, count: SizeT, offset: OffT): SsizeT =
    rustlangLibcWasiPwrite(fd, buf.rawAddress.toCPointer<ByteVar>(), count, offset)

public actual fun lstat(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcWasiLstat(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun fsync(fd: CInt): CInt =
    rustlangLibcWasiFsync(fd)

public actual fun fdatasync(fd: CInt): CInt =
    rustlangLibcWasiFdatasync(fd)

public actual fun symlink(path1: ConstPtr, path2: ConstPtr): CInt =
    rustlangLibcWasiSymlink(path1.rawAddress.toCPointer<ByteVar>(), path2.rawAddress.toCPointer<ByteVar>())

public actual fun truncate(path: ConstPtr, length: OffT): CInt =
    rustlangLibcWasiTruncate(path.rawAddress.toCPointer<ByteVar>(), length)

public actual fun ftruncate(fd: CInt, length: OffT): CInt =
    rustlangLibcWasiFtruncate(fd, length)

public actual fun getrusage(resource: CInt, usage: MutPtr): CInt =
    rustlangLibcWasiGetrusage(resource, usage.rawAddress.toCPointer<ByteVar>())

public actual fun gettimeofday(tp: MutPtr, tz: MutPtr): CInt =
    rustlangLibcWasiGettimeofday(tp.rawAddress.toCPointer<ByteVar>(), tz.rawAddress.toCPointer<ByteVar>())

public actual fun times(buf: MutPtr): io.github.kotlinmania.rustlang.wasi.ClockT =
    rustlangLibcWasiTimes(buf.rawAddress.toCPointer<ByteVar>())

public actual fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcWasiStrerrorR(errnum, buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun usleep(secs: CUint): CInt =
    rustlangLibcWasiUsleep(secs)

public actual fun send(socket: CInt, buf: ConstPtr, len: SizeT, flags: CInt): SsizeT =
    rustlangLibcWasiSend(socket, buf.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun recv(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt): SsizeT =
    rustlangLibcWasiRecv(socket, buf.rawAddress.toCPointer<ByteVar>(), len, flags)

public actual fun poll(fds: MutPtr, nfds: NfdsT, timeout: CInt): CInt =
    rustlangLibcWasiPoll(fds.rawAddress.toCPointer<ByteVar>(), nfds, timeout)

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiSetlocale(category, locale.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun localeconv(): MutPtr =
    MutPtr(rustlangLibcWasiLocaleconv()?.toLong() ?: 0L)

public actual fun readlink(path: ConstPtr, buf: MutPtr, bufsz: SizeT): SsizeT =
    rustlangLibcWasiReadlink(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), bufsz)

public actual fun timegm(tm: MutPtr): TimeT =
    rustlangLibcWasiTimegm(tm.rawAddress.toCPointer<ByteVar>())

public actual fun sysconf(name: CInt): CLong =
    rustlangLibcWasiSysconf(name)

public actual fun ioctl(fd: CInt, request: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcWasiIoctl(fd, request, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fseeko(stream: MutPtr, offset: OffT, whence: CInt): CInt =
    rustlangLibcWasiFseeko(stream.rawAddress.toCPointer<ByteVar>(), offset, whence)

public actual fun ftello(stream: MutPtr): OffT =
    rustlangLibcWasiFtello(stream.rawAddress.toCPointer<ByteVar>())

public actual fun posixFallocate(fd: CInt, offset: OffT, len: OffT): CInt =
    rustlangLibcWasiPosixFallocate(fd, offset, len)

public actual fun strcasestr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiStrcasestr(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun getline(lineptr: MutPtr, n: MutPtr, stream: MutPtr): SsizeT =
    rustlangLibcWasiGetline(lineptr.rawAddress.toCPointer<ByteVar>(), n.rawAddress.toCPointer<ByteVar>(), stream.rawAddress.toCPointer<ByteVar>())

public actual fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    rustlangLibcWasiFaccessat(dirfd, pathname.rawAddress.toCPointer<ByteVar>(), mode, flags)

public actual fun writev(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    rustlangLibcWasiWritev(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt)

public actual fun readv(fd: CInt, iov: ConstPtr, iovcnt: CInt): SsizeT =
    rustlangLibcWasiReadv(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt)

public actual fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    rustlangLibcWasiPwritev(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset)

public actual fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT =
    rustlangLibcWasiPreadv(fd, iov.rawAddress.toCPointer<ByteVar>(), iovcnt, offset)

public actual fun posixFadvise(fd: CInt, offset: OffT, len: OffT, advise: CInt): CInt =
    rustlangLibcWasiPosixFadvise(fd, offset, len, advise)

public actual fun futimens(fd: CInt, times: ConstPtr): CInt =
    rustlangLibcWasiFutimens(fd, times.rawAddress.toCPointer<ByteVar>())

public actual fun utimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flag: CInt): CInt =
    rustlangLibcWasiUtimensat(dirfd, path.rawAddress.toCPointer<ByteVar>(), times.rawAddress.toCPointer<ByteVar>(), flag)

public actual fun getentropy(buf: MutPtr, buflen: SizeT): CInt =
    rustlangLibcWasiGetentropy(buf.rawAddress.toCPointer<ByteVar>(), buflen)

public actual fun memrchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiMemrchr(cx.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun abs(i: CInt): CInt =
    rustlangLibcWasiAbs(i)

public actual fun labs(i: CLong): CLong =
    rustlangLibcWasiLabs(i)

public actual fun duplocale(base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    MutPtr(rustlangLibcWasiDuplocale(base.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun freelocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT) {
    rustlangLibcWasiFreelocale(loc.rawAddress.toCPointer<ByteVar>())
}

public actual fun newlocale(mask: CInt, locale: ConstPtr, base: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    MutPtr(rustlangLibcWasiNewlocale(mask, locale.rawAddress.toCPointer<ByteVar>(), base.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun uselocale(loc: io.github.kotlinmania.rustlang.wasi.LocaleT): io.github.kotlinmania.rustlang.wasi.LocaleT =
    MutPtr(rustlangLibcWasiUselocale(loc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun schedYield(): CInt =
    rustlangLibcWasiSchedYield()

public actual fun getcwd(buf: MutPtr, size: SizeT): MutPtr =
    MutPtr(rustlangLibcWasiGetcwd(buf.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun chdir(dir: ConstPtr): CInt =
    rustlangLibcWasiChdir(dir.rawAddress.toCPointer<ByteVar>())

public actual fun nlLanginfo(item: io.github.kotlinmania.rustlang.wasi.NlItem): MutPtr =
    MutPtr(rustlangLibcWasiNlLanginfo(item)?.toLong() ?: 0L)

public actual fun nlLanginfoL(item: io.github.kotlinmania.rustlang.wasi.NlItem, loc: io.github.kotlinmania.rustlang.wasi.LocaleT): MutPtr =
    MutPtr(rustlangLibcWasiNlLanginfoL(item, loc.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun select(nfds: CInt, readfds: MutPtr, writefds: MutPtr, errorfds: MutPtr, timeout: ConstPtr): CInt =
    rustlangLibcWasiSelect(nfds, readfds.rawAddress.toCPointer<ByteVar>(), writefds.rawAddress.toCPointer<ByteVar>(), errorfds.rawAddress.toCPointer<ByteVar>(), timeout.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcRegisterPreopenedFd(fd: CInt, path: ConstPtr): CInt =
    rustlangLibcWasiWasilibcRegisterPreopenedFd(fd, path.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcFdRenumber(fd: CInt, newfd: CInt): CInt =
    rustlangLibcWasiWasilibcFdRenumber(fd, newfd)

public actual fun wasilibcUnlinkat(fd: CInt, path: ConstPtr): CInt =
    rustlangLibcWasiWasilibcUnlinkat(fd, path.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcRmdirat(fd: CInt, path: ConstPtr): CInt =
    rustlangLibcWasiWasilibcRmdirat(fd, path.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcFindRelpath(path: ConstPtr, absPrefix: MutPtr, relativePath: MutPtr, relativePathLen: ULong): CInt =
    rustlangLibcWasiWasilibcFindRelpath(path.rawAddress.toCPointer<ByteVar>(), absPrefix.rawAddress.toCPointer<ByteVar>(), relativePath.rawAddress.toCPointer<ByteVar>(), relativePathLen)

public actual fun wasilibcTell(fd: CInt): OffT =
    rustlangLibcWasiWasilibcTell(fd)

public actual fun wasilibcNocwdWasilibcUnlinkat(dirfd: CInt, path: ConstPtr): CInt =
    rustlangLibcWasiWasilibcNocwdWasilibcUnlinkat(dirfd, path.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcNocwdWasilibcRmdirat(dirfd: CInt, path: ConstPtr): CInt =
    rustlangLibcWasiWasilibcNocwdWasilibcRmdirat(dirfd, path.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcNocwdLinkat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcNocwdLinkat(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcNocwdSymlinkat(target: ConstPtr, dirfd: CInt, path: ConstPtr): CInt =
    rustlangLibcWasiWasilibcNocwdSymlinkat(target.rawAddress.toCPointer<ByteVar>(), dirfd, path.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcNocwdReadlinkat(dirfd: CInt, path: ConstPtr, buf: MutPtr, bufsize: ULong): Long =
    rustlangLibcWasiWasilibcNocwdReadlinkat(dirfd, path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), bufsize)

public actual fun wasilibcNocwdFaccessat(dirfd: CInt, path: ConstPtr, mode: CInt, flags: CInt): CInt =
    rustlangLibcWasiWasilibcNocwdFaccessat(dirfd, path.rawAddress.toCPointer<ByteVar>(), mode, flags)

public actual fun wasilibcNocwdRenameat(olddirfd: CInt, oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    rustlangLibcWasiWasilibcNocwdRenameat(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcNocwdOpenatNomode(dirfd: CInt, path: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcNocwdOpenatNomode(dirfd, path.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcNocwdFstatat(dirfd: CInt, path: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcNocwdFstatat(dirfd, path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcNocwdMkdiratNomode(dirfd: CInt, path: ConstPtr): CInt =
    rustlangLibcWasiWasilibcNocwdMkdiratNomode(dirfd, path.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcNocwdUtimensat(dirfd: CInt, path: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcNocwdUtimensat(dirfd, path.rawAddress.toCPointer<ByteVar>(), times.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcNocwdOpendirat(dirfd: CInt, path: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWasiWasilibcNocwdOpendirat(dirfd, path.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun wasilibcAccess(pathname: ConstPtr, mode: CInt, flags: CInt): CInt =
    rustlangLibcWasiWasilibcAccess(pathname.rawAddress.toCPointer<ByteVar>(), mode, flags)

public actual fun wasilibcStat(pathname: ConstPtr, buf: MutPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcStat(pathname.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcUtimens(pathname: ConstPtr, times: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcUtimens(pathname.rawAddress.toCPointer<ByteVar>(), times.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcLink(oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcLink(oldpath.rawAddress.toCPointer<ByteVar>(), newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcLinkOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcLinkOldat(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcLinkNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr, flags: CInt): CInt =
    rustlangLibcWasiWasilibcLinkNewat(oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>(), flags)

public actual fun wasilibcRenameOldat(olddirfd: CInt, oldpath: ConstPtr, newpath: ConstPtr): CInt =
    rustlangLibcWasiWasilibcRenameOldat(olddirfd, oldpath.rawAddress.toCPointer<ByteVar>(), newpath.rawAddress.toCPointer<ByteVar>())

public actual fun wasilibcRenameNewat(oldpath: ConstPtr, newdirfd: CInt, newpath: ConstPtr): CInt =
    rustlangLibcWasiWasilibcRenameNewat(oldpath.rawAddress.toCPointer<ByteVar>(), newdirfd, newpath.rawAddress.toCPointer<ByteVar>())

public actual fun arc4random(): UInt =
    rustlangLibcWasiArc4random()

public actual fun arc4randomBuf(a: MutPtr, b: SizeT) {
    rustlangLibcWasiArc4randomBuf(a.rawAddress.toCPointer<ByteVar>(), b)
}

public actual fun arc4randomUniform(a: UInt): UInt =
    rustlangLibcWasiArc4randomUniform(a)

public actual fun errnoLocation(): MutPtr =
    MutPtr(rustlangLibcWasiErrnoLocation()?.toLong() ?: 0L)

public actual fun chmod(path: ConstPtr, mode: ModeT): CInt =
    rustlangLibcWasiChmod(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun fchmod(fd: CInt, mode: ModeT): CInt =
    rustlangLibcWasiFchmod(fd, mode)

public actual fun realpath(pathname: ConstPtr, resolved: MutPtr): MutPtr =
    MutPtr(rustlangLibcWasiRealpath(pathname.rawAddress.toCPointer<ByteVar>(), resolved.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun pthreadSelf(): PthreadT =
    MutPtr(rustlangLibcWasiPthreadSelf()?.toLong() ?: 0L)

public actual fun pthreadCreate(native: MutPtr, attr: ConstPtr, f: PthreadCreateFCallback, value: MutPtr): CInt =
    rustlangLibcWasiPthreadCreate(native.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadEqual(t1: PthreadT, t2: PthreadT): CInt =
    rustlangLibcWasiPthreadEqual(t1.rawAddress.toCPointer<ByteVar>(), t2.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadJoin(native: PthreadT, value: MutPtr): CInt =
    rustlangLibcWasiPthreadJoin(native.rawAddress.toCPointer<ByteVar>(), value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrInit(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadAttrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrDestroy(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadAttrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrGetstacksize(attr: ConstPtr, stacksize: MutPtr): CInt =
    rustlangLibcWasiPthreadAttrGetstacksize(attr.rawAddress.toCPointer<ByteVar>(), stacksize.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetstacksize(attr: MutPtr, stackSize: SizeT): CInt =
    rustlangLibcWasiPthreadAttrSetstacksize(attr.rawAddress.toCPointer<ByteVar>(), stackSize)

public actual fun pthreadAttrGetdetachstate(attr: ConstPtr, state: MutPtr): CInt =
    rustlangLibcWasiPthreadAttrGetdetachstate(attr.rawAddress.toCPointer<ByteVar>(), state.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetdetachstate(attr: MutPtr, state: CInt): CInt =
    rustlangLibcWasiPthreadAttrSetdetachstate(attr.rawAddress.toCPointer<ByteVar>(), state)

public actual fun pthreadAttrGetguardsize(attr: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadAttrGetguardsize(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetguardsize(attr: MutPtr, b: SizeT): CInt =
    rustlangLibcWasiPthreadAttrSetguardsize(attr.rawAddress.toCPointer<ByteVar>(), b)

public actual fun pthreadAttrGetstack(attr: ConstPtr, b: MutPtr, c: MutPtr): CInt =
    rustlangLibcWasiPthreadAttrGetstack(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>(), c.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadAttrSetstack(attr: MutPtr, b: MutPtr, c: SizeT): CInt =
    rustlangLibcWasiPthreadAttrSetstack(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>(), c)

public actual fun pthreadDetach(thread: PthreadT): CInt =
    rustlangLibcWasiPthreadDetach(thread.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSetcancelstate(a: CInt, b: MutPtr): CInt =
    rustlangLibcWasiPthreadSetcancelstate(a, b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSetcanceltype(a: CInt, b: MutPtr): CInt =
    rustlangLibcWasiPthreadSetcanceltype(a, b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadTestcancel() {
    rustlangLibcWasiPthreadTestcancel()
}

public actual fun pthreadOnce(a: MutPtr, b: AtexitACallback): CInt =
    rustlangLibcWasiPthreadOnce(a.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun pthreadKeyCreate(key: MutPtr, dtor: PthreadKeyCreateDtorCallback?): CInt =
    rustlangLibcWasiPthreadKeyCreate(key.rawAddress.toCPointer<ByteVar>(), 0L.toCPointer<ByteVar>())

public actual fun pthreadKeyDelete(key: PthreadKeyT): CInt =
    rustlangLibcWasiPthreadKeyDelete(key)

public actual fun pthreadGetspecific(key: PthreadKeyT): MutPtr =
    MutPtr(rustlangLibcWasiPthreadGetspecific(key)?.toLong() ?: 0L)

public actual fun pthreadSetspecific(key: PthreadKeyT, value: ConstPtr): CInt =
    rustlangLibcWasiPthreadSetspecific(key, value.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexInit(lock: MutPtr, attr: ConstPtr): CInt =
    rustlangLibcWasiPthreadMutexInit(lock.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexDestroy(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexDestroy(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexLock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexLock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexTrylock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexTrylock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexUnlock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexUnlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexTimedlock(lock: MutPtr, ts: ConstPtr): CInt =
    rustlangLibcWasiPthreadMutexTimedlock(lock.rawAddress.toCPointer<ByteVar>(), ts.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexConsistent(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexConsistent(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexGetprioceiling(lock: MutPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexGetprioceiling(lock.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrInit(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrDestroy(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrSettype(attr: MutPtr, type: CInt): CInt =
    rustlangLibcWasiPthreadMutexattrSettype(attr.rawAddress.toCPointer<ByteVar>(), type)

public actual fun pthreadMutexattrGetprotocol(attr: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexattrGetprotocol(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexattrGetpshared(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrGetrobust(attr: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexattrGetrobust(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrGettype(attr: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadMutexattrGettype(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadMutexattrSetprotocol(attr: ConstPtr, b: CInt): CInt =
    rustlangLibcWasiPthreadMutexattrSetprotocol(attr.rawAddress.toCPointer<ByteVar>(), b)

public actual fun pthreadMutexattrSetpshared(attr: ConstPtr, b: CInt): CInt =
    rustlangLibcWasiPthreadMutexattrSetpshared(attr.rawAddress.toCPointer<ByteVar>(), b)

public actual fun pthreadMutexattrSetrobust(attr: ConstPtr, b: CInt): CInt =
    rustlangLibcWasiPthreadMutexattrSetrobust(attr.rawAddress.toCPointer<ByteVar>(), b)

public actual fun pthreadCondInit(cond: MutPtr, attr: ConstPtr): CInt =
    rustlangLibcWasiPthreadCondInit(cond.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondWait(cond: MutPtr, lock: MutPtr): CInt =
    rustlangLibcWasiPthreadCondWait(cond.rawAddress.toCPointer<ByteVar>(), lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondTimedwait(cond: MutPtr, lock: MutPtr, abstime: ConstPtr): CInt =
    rustlangLibcWasiPthreadCondTimedwait(cond.rawAddress.toCPointer<ByteVar>(), lock.rawAddress.toCPointer<ByteVar>(), abstime.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondSignal(cond: MutPtr): CInt =
    rustlangLibcWasiPthreadCondSignal(cond.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondBroadcast(cond: MutPtr): CInt =
    rustlangLibcWasiPthreadCondBroadcast(cond.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondDestroy(cond: MutPtr): CInt =
    rustlangLibcWasiPthreadCondDestroy(cond.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondattrInit(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadCondattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondattrDestroy(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadCondattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondattrSetclock(attr: MutPtr, clockId: ClockidT): CInt =
    0

public actual fun pthreadCondattrGetclock(attr: ConstPtr, clockId: MutPtr): CInt =
    rustlangLibcWasiPthreadCondattrGetclock(attr.rawAddress.toCPointer<ByteVar>(), clockId.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadCondattrSetpshared(attr: MutPtr, b: CInt): CInt =
    rustlangLibcWasiPthreadCondattrSetpshared(attr.rawAddress.toCPointer<ByteVar>(), b)

public actual fun pthreadCondattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadCondattrGetpshared(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockInit(lock: MutPtr, attr: ConstPtr): CInt =
    rustlangLibcWasiPthreadRwlockInit(lock.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockDestroy(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockDestroy(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockRdlock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockRdlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockTryrdlock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockTryrdlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockWrlock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockWrlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockTrywrlock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockTrywrlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockUnlock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockUnlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockTimedrdlock(lock: MutPtr, at: ConstPtr): CInt =
    rustlangLibcWasiPthreadRwlockTimedrdlock(lock.rawAddress.toCPointer<ByteVar>(), at.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockTimedwrlock(lock: MutPtr, at: ConstPtr): CInt =
    rustlangLibcWasiPthreadRwlockTimedwrlock(lock.rawAddress.toCPointer<ByteVar>(), at.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockattrInit(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockattrDestroy(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadRwlockattrSetpshared(attr: MutPtr, b: CInt): CInt =
    rustlangLibcWasiPthreadRwlockattrSetpshared(attr.rawAddress.toCPointer<ByteVar>(), b)

public actual fun pthreadRwlockattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadRwlockattrGetpshared(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSpinInit(lock: MutPtr, b: CInt): CInt =
    rustlangLibcWasiPthreadSpinInit(lock.rawAddress.toCPointer<ByteVar>(), b)

public actual fun pthreadSpinDestroy(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadSpinDestroy(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSpinLock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadSpinLock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSpinTrylock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadSpinTrylock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadSpinUnlock(lock: MutPtr): CInt =
    rustlangLibcWasiPthreadSpinUnlock(lock.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierInit(barrier: MutPtr, attr: ConstPtr, count: CUint): CInt =
    rustlangLibcWasiPthreadBarrierInit(barrier.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>(), count)

public actual fun pthreadBarrierDestroy(barrier: MutPtr): CInt =
    rustlangLibcWasiPthreadBarrierDestroy(barrier.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierWait(barrier: MutPtr): CInt =
    rustlangLibcWasiPthreadBarrierWait(barrier.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierattrInit(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadBarrierattrInit(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierattrDestroy(attr: MutPtr): CInt =
    rustlangLibcWasiPthreadBarrierattrDestroy(attr.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadBarrierattrSetpshared(attr: MutPtr, b: CInt): CInt =
    rustlangLibcWasiPthreadBarrierattrSetpshared(attr.rawAddress.toCPointer<ByteVar>(), b)

public actual fun pthreadBarrierattrGetpshared(attr: ConstPtr, b: MutPtr): CInt =
    rustlangLibcWasiPthreadBarrierattrGetpshared(attr.rawAddress.toCPointer<ByteVar>(), b.rawAddress.toCPointer<ByteVar>())

public actual fun pthreadGetattrNp(thread: PthreadT, attr: MutPtr): CInt =
    rustlangLibcWasiPthreadGetattrNp(thread.rawAddress.toCPointer<ByteVar>(), attr.rawAddress.toCPointer<ByteVar>())
