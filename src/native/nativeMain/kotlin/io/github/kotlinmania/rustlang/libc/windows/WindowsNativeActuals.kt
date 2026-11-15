@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.windows

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
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAbort
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAbs
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAccept
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAccess
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAlignedFree
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAlignedMalloc
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAlignedRealloc
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAtexit
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAtof
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAtoi
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAtol
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsAtoll
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsBind
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsCalloc
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsChdir
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsChmod
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsClock
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsClose
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsCommit
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsConnect
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsCreat
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsDup
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsDup2
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExecl
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExecle
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExeclp
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExeclpe
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExecv
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExecve
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExecvp
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExecvpe
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsExit
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFclose
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFdopen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFeof
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFerror
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFflush
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFgetc
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFgetpos
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFgets
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFileno
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFopen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFprintf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFputc
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFputs
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFread
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFree
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFreopen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFscanf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFseek
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFsetpos
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFstat
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFtell
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsFwrite
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetDaylight
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetDstbias
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetOsfhandle
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetTimezone
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetTzname
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetchar
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetcwd
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetenv
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetpeername
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetpid
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetsockname
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsGetsockopt
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsalnum
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsalpha
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsatty
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsblank
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIscntrl
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsdigit
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsgraph
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIslower
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsprint
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIspunct
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsspace
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsupper
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsIsxdigit
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsLabs
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsListen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsLseek
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsLseek64
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsMalloc
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsMemchr
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsMemcmp
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsMemcpy
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsMemmove
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsMemset
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsMkdir
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsMsize
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsOpen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsOpenOsfhandle
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPclose
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPerror
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPipe
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPopen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPrintf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPutchar
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPutenv
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPutenvS
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsPuts
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsQsort
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsQsortS
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRaise
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRand
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRead
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRealloc
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRecvfrom
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRemove
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRename
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRewind
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsRmdir
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsScanf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSendto
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSetbuf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSetlocale
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSetsockopt
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSetvbuf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSignal
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSnprintf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSocket
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSprintf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSrand
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSscanf
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStat
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrcat
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrchr
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrcmp
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrcoll
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrcpy
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrcspn
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrdup
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrerror
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrlen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrncat
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrncmp
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrncpy
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrnlen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrpbrk
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrrchr
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrspn
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrstr
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrtod
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrtof
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrtok
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrtol
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrtoll
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrtoul
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrtoull
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsStrxfrm
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsSystem
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsTmpfile
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsTolower
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsToupper
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsTzset
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsUngetc
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsUnlink
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWchmod
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWcslen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWcsnlen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWcstombs
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWexecl
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWexecle
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWexeclp
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWexeclpe
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWexecv
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWexecve
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWexecvp
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWexecvpe
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWopen
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWputenv
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWputenvS
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWrite
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWrmdir
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWsetlocale
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWstat
import io.github.kotlinmania.rustlang.libc.windows.cinterop.rustlangLibcWindowsWutime
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun printf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsPrintf(format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fprintf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsFprintf(stream.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun snprintf(buffer: MutPtr, count: SizeT, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsSnprintf(buffer.rawAddress.toCPointer<ByteVar>(), count, format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sprintf(buffer: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsSprintf(buffer.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun scanf(format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsScanf(format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun sscanf(buffer: ConstPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsSscanf(buffer.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun fscanf(stream: MutPtr, format: ConstPtr, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsFscanf(stream.rawAddress.toCPointer<ByteVar>(), format.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun isalnum(c: CInt): CInt =
    rustlangLibcWindowsIsalnum(c)

public actual fun isalpha(c: CInt): CInt =
    rustlangLibcWindowsIsalpha(c)

public actual fun iscntrl(c: CInt): CInt =
    rustlangLibcWindowsIscntrl(c)

public actual fun isdigit(c: CInt): CInt =
    rustlangLibcWindowsIsdigit(c)

public actual fun isgraph(c: CInt): CInt =
    rustlangLibcWindowsIsgraph(c)

public actual fun islower(c: CInt): CInt =
    rustlangLibcWindowsIslower(c)

public actual fun isprint(c: CInt): CInt =
    rustlangLibcWindowsIsprint(c)

public actual fun ispunct(c: CInt): CInt =
    rustlangLibcWindowsIspunct(c)

public actual fun isspace(c: CInt): CInt =
    rustlangLibcWindowsIsspace(c)

public actual fun isupper(c: CInt): CInt =
    rustlangLibcWindowsIsupper(c)

public actual fun isxdigit(c: CInt): CInt =
    rustlangLibcWindowsIsxdigit(c)

public actual fun isblank(c: CInt): CInt =
    rustlangLibcWindowsIsblank(c)

public actual fun tolower(c: CInt): CInt =
    rustlangLibcWindowsTolower(c)

public actual fun toupper(c: CInt): CInt =
    rustlangLibcWindowsToupper(c)

public actual fun qsort(base: MutPtr, num: SizeT, size: SizeT, compar: QsortComparCallback?) {
    rustlangLibcWindowsQsort(base.rawAddress.toCPointer<ByteVar>(), num, size, 0L.toCPointer<ByteVar>())
}

public actual fun qsortS(base: MutPtr, num: SizeT, size: SizeT, compar: QsortSComparCallback?, arg: MutPtr) {
    rustlangLibcWindowsQsortS(base.rawAddress.toCPointer<ByteVar>(), num, size, 0L.toCPointer<ByteVar>(), arg.rawAddress.toCPointer<ByteVar>())
}

public actual fun fopen(filename: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsFopen(filename.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun freopen(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr =
    MutPtr(rustlangLibcWindowsFreopen(filename.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>(), file.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fflush(file: MutPtr): CInt =
    rustlangLibcWindowsFflush(file.rawAddress.toCPointer<ByteVar>())

public actual fun fclose(file: MutPtr): CInt =
    rustlangLibcWindowsFclose(file.rawAddress.toCPointer<ByteVar>())

public actual fun remove(filename: ConstPtr): CInt =
    rustlangLibcWindowsRemove(filename.rawAddress.toCPointer<ByteVar>())

public actual fun rename(oldname: ConstPtr, newname: ConstPtr): CInt =
    rustlangLibcWindowsRename(oldname.rawAddress.toCPointer<ByteVar>(), newname.rawAddress.toCPointer<ByteVar>())

public actual fun tmpfile(): MutPtr =
    MutPtr(rustlangLibcWindowsTmpfile()?.toLong() ?: 0L)

public actual fun setvbuf(stream: MutPtr, buffer: MutPtr, mode: CInt, size: SizeT): CInt =
    rustlangLibcWindowsSetvbuf(stream.rawAddress.toCPointer<ByteVar>(), buffer.rawAddress.toCPointer<ByteVar>(), mode, size)

public actual fun setbuf(stream: MutPtr, buf: MutPtr) {
    rustlangLibcWindowsSetbuf(stream.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())
}

public actual fun getchar(): CInt =
    rustlangLibcWindowsGetchar()

public actual fun putchar(c: CInt): CInt =
    rustlangLibcWindowsPutchar(c)

public actual fun fgetc(stream: MutPtr): CInt =
    rustlangLibcWindowsFgetc(stream.rawAddress.toCPointer<ByteVar>())

public actual fun fgets(buf: MutPtr, n: CInt, stream: MutPtr): MutPtr =
    MutPtr(rustlangLibcWindowsFgets(buf.rawAddress.toCPointer<ByteVar>(), n, stream.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fputc(c: CInt, stream: MutPtr): CInt =
    rustlangLibcWindowsFputc(c, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fputs(s: ConstPtr, stream: MutPtr): CInt =
    rustlangLibcWindowsFputs(s.rawAddress.toCPointer<ByteVar>(), stream.rawAddress.toCPointer<ByteVar>())

public actual fun puts(s: ConstPtr): CInt =
    rustlangLibcWindowsPuts(s.rawAddress.toCPointer<ByteVar>())

public actual fun ungetc(c: CInt, stream: MutPtr): CInt =
    rustlangLibcWindowsUngetc(c, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fread(ptr: MutPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    rustlangLibcWindowsFread(ptr.rawAddress.toCPointer<ByteVar>(), size, nobj, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fwrite(ptr: ConstPtr, size: SizeT, nobj: SizeT, stream: MutPtr): SizeT =
    rustlangLibcWindowsFwrite(ptr.rawAddress.toCPointer<ByteVar>(), size, nobj, stream.rawAddress.toCPointer<ByteVar>())

public actual fun fseek(stream: MutPtr, offset: CLong, whence: CInt): CInt =
    rustlangLibcWindowsFseek(stream.rawAddress.toCPointer<ByteVar>(), offset, whence)

public actual fun ftell(stream: MutPtr): CLong =
    rustlangLibcWindowsFtell(stream.rawAddress.toCPointer<ByteVar>())

public actual fun rewind(stream: MutPtr) {
    rustlangLibcWindowsRewind(stream.rawAddress.toCPointer<ByteVar>())
}

public actual fun fgetpos(stream: MutPtr, ptr: MutPtr): CInt =
    rustlangLibcWindowsFgetpos(stream.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>())

public actual fun fsetpos(stream: MutPtr, ptr: ConstPtr): CInt =
    rustlangLibcWindowsFsetpos(stream.rawAddress.toCPointer<ByteVar>(), ptr.rawAddress.toCPointer<ByteVar>())

public actual fun feof(stream: MutPtr): CInt =
    rustlangLibcWindowsFeof(stream.rawAddress.toCPointer<ByteVar>())

public actual fun ferror(stream: MutPtr): CInt =
    rustlangLibcWindowsFerror(stream.rawAddress.toCPointer<ByteVar>())

public actual fun perror(s: ConstPtr) {
    rustlangLibcWindowsPerror(s.rawAddress.toCPointer<ByteVar>())
}

public actual fun atof(s: ConstPtr): CDouble =
    rustlangLibcWindowsAtof(s.rawAddress.toCPointer<ByteVar>())

public actual fun atoi(s: ConstPtr): CInt =
    rustlangLibcWindowsAtoi(s.rawAddress.toCPointer<ByteVar>())

public actual fun atol(s: ConstPtr): CLong =
    rustlangLibcWindowsAtol(s.rawAddress.toCPointer<ByteVar>())

public actual fun atoll(s: ConstPtr): CLonglong =
    rustlangLibcWindowsAtoll(s.rawAddress.toCPointer<ByteVar>())

public actual fun strtod(s: ConstPtr, endp: MutPtr): CDouble =
    rustlangLibcWindowsStrtod(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>())

public actual fun strtof(s: ConstPtr, endp: MutPtr): CFloat =
    rustlangLibcWindowsStrtof(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>())

public actual fun strtol(s: ConstPtr, endp: MutPtr, base: CInt): CLong =
    rustlangLibcWindowsStrtol(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoll(s: ConstPtr, endp: MutPtr, base: CInt): CLonglong =
    rustlangLibcWindowsStrtoll(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoul(s: ConstPtr, endp: MutPtr, base: CInt): CUlong =
    rustlangLibcWindowsStrtoul(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun strtoull(s: ConstPtr, endp: MutPtr, base: CInt): CUlonglong =
    rustlangLibcWindowsStrtoull(s.rawAddress.toCPointer<ByteVar>(), endp.rawAddress.toCPointer<ByteVar>(), base)

public actual fun calloc(nobj: SizeT, size: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsCalloc(nobj, size)?.toLong() ?: 0L)

public actual fun malloc(size: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsMalloc(size)?.toLong() ?: 0L)

public actual fun msize(p: MutPtr): SizeT =
    rustlangLibcWindowsMsize(p.rawAddress.toCPointer<ByteVar>())

public actual fun realloc(p: MutPtr, size: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsRealloc(p.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun free(p: MutPtr) {
    rustlangLibcWindowsFree(p.rawAddress.toCPointer<ByteVar>())
}

public actual fun abort(): Nothing {
    rustlangLibcWindowsAbort()
    error("Unreachable noreturn C function abort")
}

public actual fun exit(status: CInt): Nothing {
    rustlangLibcWindowsExit(status)
    error("Unreachable noreturn C function exit")
}

public actual fun atexit(cb: AtexitCbCallback): CInt =
    rustlangLibcWindowsAtexit(0L.toCPointer<ByteVar>())

public actual fun system(s: ConstPtr): CInt =
    rustlangLibcWindowsSystem(s.rawAddress.toCPointer<ByteVar>())

public actual fun getenv(s: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsGetenv(s.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strcpy(dst: MutPtr, src: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsStrcpy(dst.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strncpy(dst: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsStrncpy(dst.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun strcat(s: MutPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsStrcat(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strncat(s: MutPtr, ct: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsStrncat(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun strcmp(cs: ConstPtr, ct: ConstPtr): CInt =
    rustlangLibcWindowsStrcmp(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strncmp(cs: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    rustlangLibcWindowsStrncmp(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun strcoll(cs: ConstPtr, ct: ConstPtr): CInt =
    rustlangLibcWindowsStrcoll(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(rustlangLibcWindowsStrchr(cs.rawAddress.toCPointer<ByteVar>(), c)?.toLong() ?: 0L)

public actual fun strrchr(cs: ConstPtr, c: CInt): MutPtr =
    MutPtr(rustlangLibcWindowsStrrchr(cs.rawAddress.toCPointer<ByteVar>(), c)?.toLong() ?: 0L)

public actual fun strspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    rustlangLibcWindowsStrspn(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strcspn(cs: ConstPtr, ct: ConstPtr): SizeT =
    rustlangLibcWindowsStrcspn(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())

public actual fun strdup(cs: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsStrdup(cs.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strpbrk(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsStrpbrk(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strstr(cs: ConstPtr, ct: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsStrstr(cs.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strlen(cs: ConstPtr): SizeT =
    rustlangLibcWindowsStrlen(cs.rawAddress.toCPointer<ByteVar>())

public actual fun strnlen(cs: ConstPtr, maxlen: SizeT): SizeT =
    rustlangLibcWindowsStrnlen(cs.rawAddress.toCPointer<ByteVar>(), maxlen)

public actual fun strerror(n: CInt): MutPtr =
    MutPtr(rustlangLibcWindowsStrerror(n)?.toLong() ?: 0L)

public actual fun strtok(s: MutPtr, t: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsStrtok(s.rawAddress.toCPointer<ByteVar>(), t.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun strxfrm(s: MutPtr, ct: ConstPtr, n: SizeT): SizeT =
    rustlangLibcWindowsStrxfrm(s.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun wcslen(buf: ConstPtr): SizeT =
    rustlangLibcWindowsWcslen(buf.rawAddress.toCPointer<ByteVar>())

public actual fun wcsnlen(str: ConstPtr, numberOfElements: SizeT): SizeT =
    rustlangLibcWindowsWcsnlen(str.rawAddress.toCPointer<ByteVar>(), numberOfElements)

public actual fun wcstombs(dest: MutPtr, src: ConstPtr, n: SizeT): SizeT =
    rustlangLibcWindowsWcstombs(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)

public actual fun memchr(cx: ConstPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsMemchr(cx.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun memcmp(cx: ConstPtr, ct: ConstPtr, n: SizeT): CInt =
    rustlangLibcWindowsMemcmp(cx.rawAddress.toCPointer<ByteVar>(), ct.rawAddress.toCPointer<ByteVar>(), n)

public actual fun memcpy(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsMemcpy(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memmove(dest: MutPtr, src: ConstPtr, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsMemmove(dest.rawAddress.toCPointer<ByteVar>(), src.rawAddress.toCPointer<ByteVar>(), n)?.toLong() ?: 0L)

public actual fun memset(dest: MutPtr, c: CInt, n: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsMemset(dest.rawAddress.toCPointer<ByteVar>(), c, n)?.toLong() ?: 0L)

public actual fun abs(i: CInt): CInt =
    rustlangLibcWindowsAbs(i)

public actual fun labs(i: CLong): CLong =
    rustlangLibcWindowsLabs(i)

public actual fun rand(): CInt =
    rustlangLibcWindowsRand()

public actual fun srand(seed: CUint) {
    rustlangLibcWindowsSrand(seed)
}

public actual fun signal(signum: CInt, handler: SighandlerT): SighandlerT =
    rustlangLibcWindowsSignal(signum, handler)

public actual fun raise(signum: CInt): CInt =
    rustlangLibcWindowsRaise(signum)

public actual fun clock(): ClockT =
    rustlangLibcWindowsClock()

public actual fun getDaylight(hours: MutPtr): ErrnoT =
    rustlangLibcWindowsGetDaylight(hours.rawAddress.toCPointer<ByteVar>())

public actual fun getDstbias(seconds: MutPtr): ErrnoT =
    rustlangLibcWindowsGetDstbias(seconds.rawAddress.toCPointer<ByteVar>())

public actual fun getTimezone(seconds: MutPtr): ErrnoT =
    rustlangLibcWindowsGetTimezone(seconds.rawAddress.toCPointer<ByteVar>())

public actual fun getTzname(pReturnValue: MutPtr, timeZoneName: MutPtr, sizeInBytes: SizeT, index: CInt): ErrnoT =
    rustlangLibcWindowsGetTzname(pReturnValue.rawAddress.toCPointer<ByteVar>(), timeZoneName.rawAddress.toCPointer<ByteVar>(), sizeInBytes, index)

public actual fun tzset() {
    rustlangLibcWindowsTzset()
}

public actual fun chmod(path: ConstPtr, mode: CInt): CInt =
    rustlangLibcWindowsChmod(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun wchmod(path: ConstPtr, mode: CInt): CInt =
    rustlangLibcWindowsWchmod(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun mkdir(path: ConstPtr): CInt =
    rustlangLibcWindowsMkdir(path.rawAddress.toCPointer<ByteVar>())

public actual fun wrmdir(path: ConstPtr): CInt =
    rustlangLibcWindowsWrmdir(path.rawAddress.toCPointer<ByteVar>())

public actual fun fstat(fildes: CInt, buf: MutPtr): CInt =
    rustlangLibcWindowsFstat(fildes, buf.rawAddress.toCPointer<ByteVar>())

public actual fun stat(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcWindowsStat(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun wstat(path: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcWindowsWstat(path.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun wutime(file: ConstPtr, buf: MutPtr): CInt =
    rustlangLibcWindowsWutime(file.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>())

public actual fun popen(command: ConstPtr, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsPopen(command.rawAddress.toCPointer<ByteVar>(), mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun pclose(stream: MutPtr): CInt =
    rustlangLibcWindowsPclose(stream.rawAddress.toCPointer<ByteVar>())

public actual fun fdopen(fd: CInt, mode: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsFdopen(fd, mode.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun fileno(stream: MutPtr): CInt =
    rustlangLibcWindowsFileno(stream.rawAddress.toCPointer<ByteVar>())

public actual fun open(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsOpen(path.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wopen(path: ConstPtr, oflag: CInt, vararg variadicArgs: Long): CInt =
    rustlangLibcWindowsWopen(path.rawAddress.toCPointer<ByteVar>(), oflag, variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun creat(path: ConstPtr, mode: CInt): CInt =
    rustlangLibcWindowsCreat(path.rawAddress.toCPointer<ByteVar>(), mode)

public actual fun access(path: ConstPtr, amode: CInt): CInt =
    rustlangLibcWindowsAccess(path.rawAddress.toCPointer<ByteVar>(), amode)

public actual fun chdir(dir: ConstPtr): CInt =
    rustlangLibcWindowsChdir(dir.rawAddress.toCPointer<ByteVar>())

public actual fun close(fd: CInt): CInt =
    rustlangLibcWindowsClose(fd)

public actual fun dup(fd: CInt): CInt =
    rustlangLibcWindowsDup(fd)

public actual fun dup2(src: CInt, dst: CInt): CInt =
    rustlangLibcWindowsDup2(src, dst)

public actual fun execl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    rustlangLibcWindowsExecl(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wexecl(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    rustlangLibcWindowsWexecl(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    rustlangLibcWindowsExecle(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wexecle(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    rustlangLibcWindowsWexecle(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execlp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    rustlangLibcWindowsExeclp(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wexeclp(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    rustlangLibcWindowsWexeclp(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execlpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    rustlangLibcWindowsExeclpe(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun wexeclpe(path: ConstPtr, arg0: ConstPtr, vararg variadicArgs: Long): IntptrT =
    rustlangLibcWindowsWexeclpe(path.rawAddress.toCPointer<ByteVar>(), arg0.rawAddress.toCPointer<ByteVar>(), variadicArgs.size.coerceAtMost(4), if (variadicArgs.size > 0) variadicArgs[0] else 0L, if (variadicArgs.size > 1) variadicArgs[1] else 0L, if (variadicArgs.size > 2) variadicArgs[2] else 0L, if (variadicArgs.size > 3) variadicArgs[3] else 0L)

public actual fun execv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    rustlangLibcWindowsExecv(prog.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>())

public actual fun execve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcWindowsExecve(prog.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun execvp(c: ConstPtr, argv: ConstPtr): CInt =
    rustlangLibcWindowsExecvp(c.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>())

public actual fun execvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): CInt =
    rustlangLibcWindowsExecvpe(c.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun wexecv(prog: ConstPtr, argv: ConstPtr): IntptrT =
    rustlangLibcWindowsWexecv(prog.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>())

public actual fun wexecve(prog: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    rustlangLibcWindowsWexecve(prog.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun wexecvp(c: ConstPtr, argv: ConstPtr): IntptrT =
    rustlangLibcWindowsWexecvp(c.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>())

public actual fun wexecvpe(c: ConstPtr, argv: ConstPtr, envp: ConstPtr): IntptrT =
    rustlangLibcWindowsWexecvpe(c.rawAddress.toCPointer<ByteVar>(), argv.rawAddress.toCPointer<ByteVar>(), envp.rawAddress.toCPointer<ByteVar>())

public actual fun getcwd(buf: MutPtr, size: CInt): MutPtr =
    MutPtr(rustlangLibcWindowsGetcwd(buf.rawAddress.toCPointer<ByteVar>(), size)?.toLong() ?: 0L)

public actual fun getpid(): CInt =
    rustlangLibcWindowsGetpid()

public actual fun isatty(fd: CInt): CInt =
    rustlangLibcWindowsIsatty(fd)

public actual fun lseek(fd: CInt, offset: CLong, origin: CInt): CLong =
    rustlangLibcWindowsLseek(fd, offset, origin)

public actual fun lseek64(fd: CInt, offset: CLonglong, origin: CInt): CLonglong =
    rustlangLibcWindowsLseek64(fd, offset, origin)

public actual fun pipe(fds: MutPtr, psize: CUint, textmode: CInt): CInt =
    rustlangLibcWindowsPipe(fds.rawAddress.toCPointer<ByteVar>(), psize, textmode)

public actual fun read(fd: CInt, buf: MutPtr, count: CUint): CInt =
    rustlangLibcWindowsRead(fd, buf.rawAddress.toCPointer<ByteVar>(), count)

public actual fun rmdir(path: ConstPtr): CInt =
    rustlangLibcWindowsRmdir(path.rawAddress.toCPointer<ByteVar>())

public actual fun unlink(c: ConstPtr): CInt =
    rustlangLibcWindowsUnlink(c.rawAddress.toCPointer<ByteVar>())

public actual fun write(fd: CInt, buf: ConstPtr, count: CUint): CInt =
    rustlangLibcWindowsWrite(fd, buf.rawAddress.toCPointer<ByteVar>(), count)

public actual fun commit(fd: CInt): CInt =
    rustlangLibcWindowsCommit(fd)

public actual fun getOsfhandle(fd: CInt): IntptrT =
    rustlangLibcWindowsGetOsfhandle(fd)

public actual fun openOsfhandle(osfhandle: IntptrT, flags: CInt): CInt =
    rustlangLibcWindowsOpenOsfhandle(osfhandle, flags)

public actual fun setlocale(category: CInt, locale: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsSetlocale(category, locale.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun wsetlocale(category: CInt, locale: ConstPtr): MutPtr =
    MutPtr(rustlangLibcWindowsWsetlocale(category, locale.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun alignedMalloc(size: SizeT, alignment: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsAlignedMalloc(size, alignment)?.toLong() ?: 0L)

public actual fun alignedFree(ptr: MutPtr) {
    rustlangLibcWindowsAlignedFree(ptr.rawAddress.toCPointer<ByteVar>())
}

public actual fun alignedRealloc(memblock: MutPtr, size: SizeT, alignment: SizeT): MutPtr =
    MutPtr(rustlangLibcWindowsAlignedRealloc(memblock.rawAddress.toCPointer<ByteVar>(), size, alignment)?.toLong() ?: 0L)

public actual fun putenv(envstring: ConstPtr): CInt =
    rustlangLibcWindowsPutenv(envstring.rawAddress.toCPointer<ByteVar>())

public actual fun wputenv(envstring: ConstPtr): CInt =
    rustlangLibcWindowsWputenv(envstring.rawAddress.toCPointer<ByteVar>())

public actual fun putenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    rustlangLibcWindowsPutenvS(envstring.rawAddress.toCPointer<ByteVar>(), valueString.rawAddress.toCPointer<ByteVar>())

public actual fun wputenvS(envstring: ConstPtr, valueString: ConstPtr): io.github.kotlinmania.rustlang.windows.ErrnoT =
    rustlangLibcWindowsWputenvS(envstring.rawAddress.toCPointer<ByteVar>(), valueString.rawAddress.toCPointer<ByteVar>())

public actual fun listen(s: Socket, backlog: CInt): CInt =
    rustlangLibcWindowsListen(s, backlog)

public actual fun accept(s: Socket, addr: MutPtr, addrlen: MutPtr): Socket =
    rustlangLibcWindowsAccept(s, addr.rawAddress.toCPointer<ByteVar>(), addrlen.rawAddress.toCPointer<ByteVar>())

public actual fun bind(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    rustlangLibcWindowsBind(s, name.rawAddress.toCPointer<ByteVar>(), namelen)

public actual fun connect(s: Socket, name: ConstPtr, namelen: CInt): CInt =
    rustlangLibcWindowsConnect(s, name.rawAddress.toCPointer<ByteVar>(), namelen)

public actual fun getpeername(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    rustlangLibcWindowsGetpeername(s, name.rawAddress.toCPointer<ByteVar>(), nameln.rawAddress.toCPointer<ByteVar>())

public actual fun getsockname(s: Socket, name: MutPtr, nameln: MutPtr): CInt =
    rustlangLibcWindowsGetsockname(s, name.rawAddress.toCPointer<ByteVar>(), nameln.rawAddress.toCPointer<ByteVar>())

public actual fun getsockopt(s: Socket, level: CInt, optname: CInt, optval: MutPtr, optlen: MutPtr): CInt =
    rustlangLibcWindowsGetsockopt(s, level, optname, optval.rawAddress.toCPointer<ByteVar>(), optlen.rawAddress.toCPointer<ByteVar>())

public actual fun recvfrom(s: Socket, buf: MutPtr, len: CInt, flags: CInt, from: MutPtr, fromlen: MutPtr): CInt =
    rustlangLibcWindowsRecvfrom(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags, from.rawAddress.toCPointer<ByteVar>(), fromlen.rawAddress.toCPointer<ByteVar>())

public actual fun sendto(s: Socket, buf: ConstPtr, len: CInt, flags: CInt, to: ConstPtr, tolen: CInt): CInt =
    rustlangLibcWindowsSendto(s, buf.rawAddress.toCPointer<ByteVar>(), len, flags, to.rawAddress.toCPointer<ByteVar>(), tolen)

public actual fun setsockopt(s: Socket, level: CInt, optname: CInt, optval: ConstPtr, optlen: CInt): CInt =
    rustlangLibcWindowsSetsockopt(s, level, optname, optval.rawAddress.toCPointer<ByteVar>(), optlen)

public actual fun socket(af: CInt, socketType: CInt, protocol: CInt): Socket =
    rustlangLibcWindowsSocket(af, socketType, protocol)
