@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.wasip1

import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedArgsGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedArgsSizesGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedClockResGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedClockTimeGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedEnvironGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedEnvironSizesGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdAdvise
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdAllocate
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdClose
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdDatasync
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdFdstatGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdFdstatSetFlags
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdFdstatSetRights
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdFilestatGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdFilestatSetSize
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdFilestatSetTimes
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdPread
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdPrestatDirName
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdPrestatGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdPwrite
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdRead
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdReaddir
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdRenumber
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdSeek
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdSync
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdTell
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedFdWrite
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathCreateDirectory
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathFilestatGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathFilestatSetTimes
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathLink
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathOpen
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathReadlink
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathRemoveDirectory
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathRename
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathSymlink
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPathUnlinkFile
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedPollOneoff
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedProcExit
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedProcRaise
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedRandomGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedSchedYield
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedSockAccept
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedSockRecv
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedSockSend
import io.github.kotlinmania.rustlang.wasip1.cinterop.rustlangLibGeneratedSockShutdown
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedArgsGet(arg0, arg1)

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedArgsSizesGet(arg0, arg1)

public actual fun environGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedEnvironGet(arg0, arg1)

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedEnvironSizesGet(arg0, arg1)

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedClockResGet(arg0, arg1)

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    rustlangLibGeneratedClockTimeGet(arg0, arg1, arg2)

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    rustlangLibGeneratedFdAdvise(arg0, arg1, arg2, arg3)

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    rustlangLibGeneratedFdAllocate(arg0, arg1, arg2)

public actual fun fdClose(arg0: Int): Int =
    rustlangLibGeneratedFdClose(arg0)

public actual fun fdDatasync(arg0: Int): Int =
    rustlangLibGeneratedFdDatasync(arg0)

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedFdFdstatGet(arg0, arg1)

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedFdFdstatSetFlags(arg0, arg1)

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    rustlangLibGeneratedFdFdstatSetRights(arg0, arg1, arg2)

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedFdFilestatGet(arg0, arg1)

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    rustlangLibGeneratedFdFilestatSetSize(arg0, arg1)

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    rustlangLibGeneratedFdFilestatSetTimes(arg0, arg1, arg2, arg3)

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    rustlangLibGeneratedFdPread(arg0, arg1, arg2, arg3, arg4)

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedFdPrestatGet(arg0, arg1)

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    rustlangLibGeneratedFdPrestatDirName(arg0, arg1, arg2)

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    rustlangLibGeneratedFdPwrite(arg0, arg1, arg2, arg3, arg4)

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    rustlangLibGeneratedFdRead(arg0, arg1, arg2, arg3)

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    rustlangLibGeneratedFdReaddir(arg0, arg1, arg2, arg3, arg4)

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedFdRenumber(arg0, arg1)

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    rustlangLibGeneratedFdSeek(arg0, arg1, arg2, arg3)

public actual fun fdSync(arg0: Int): Int =
    rustlangLibGeneratedFdSync(arg0)

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedFdTell(arg0, arg1)

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    rustlangLibGeneratedFdWrite(arg0, arg1, arg2, arg3)

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    rustlangLibGeneratedPathCreateDirectory(arg0, arg1, arg2)

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    rustlangLibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4)

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    rustlangLibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4, arg5, arg6)

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    rustlangLibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6)

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    rustlangLibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8)

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    rustlangLibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    rustlangLibGeneratedPathRemoveDirectory(arg0, arg1, arg2)

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    rustlangLibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    rustlangLibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4)

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    rustlangLibGeneratedPathUnlinkFile(arg0, arg1, arg2)

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    rustlangLibGeneratedPollOneoff(arg0, arg1, arg2, arg3)

public actual fun procExit(arg0: Int): Nothing {
    rustlangLibGeneratedProcExit(arg0)
    error("Unreachable noreturn C function procExit")
}

public actual fun procRaise(arg0: Int): Int =
    rustlangLibGeneratedProcRaise(arg0)

public actual fun schedYield(): Int =
    rustlangLibGeneratedSchedYield()

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedRandomGet(arg0, arg1)

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    rustlangLibGeneratedSockAccept(arg0, arg1, arg2)

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    rustlangLibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    rustlangLibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4)

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    rustlangLibGeneratedSockShutdown(arg0, arg1)
