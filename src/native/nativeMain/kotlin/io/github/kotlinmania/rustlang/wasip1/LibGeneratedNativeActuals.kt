@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.wasip1

import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedArgsGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedArgsSizesGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedClockResGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedClockTimeGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedEnvironGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedEnvironSizesGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdAdvise
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdAllocate
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdClose
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdDatasync
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdFdstatGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdFdstatSetFlags
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdFdstatSetRights
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdFilestatGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdFilestatSetSize
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdFilestatSetTimes
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdPread
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdPrestatDirName
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdPrestatGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdPwrite
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdRead
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdReaddir
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdRenumber
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdSeek
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdSync
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdTell
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedFdWrite
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathCreateDirectory
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathFilestatGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathFilestatSetTimes
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathLink
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathOpen
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathReadlink
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathRemoveDirectory
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathRename
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathSymlink
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPathUnlinkFile
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedPollOneoff
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedProcExit
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedProcRaise
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedRandomGet
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedSockAccept
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedSockRecv
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedSockSend
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedSockShutdown
import io.github.kotlinmania.rustlang.wasip1.cinterop.wasip1LibGeneratedWasiSnapshotPreview1SchedYield
import kotlinx.cinterop.ExperimentalForeignApi

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedArgsGet(arg0, arg1)

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedArgsSizesGet(arg0, arg1)

public actual fun environGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedEnvironGet(arg0, arg1)

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedEnvironSizesGet(arg0, arg1)

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedClockResGet(arg0, arg1)

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    wasip1LibGeneratedClockTimeGet(arg0, arg1, arg2)

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    wasip1LibGeneratedFdAdvise(arg0, arg1, arg2, arg3)

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    wasip1LibGeneratedFdAllocate(arg0, arg1, arg2)

public actual fun fdClose(arg0: Int): Int =
    wasip1LibGeneratedFdClose(arg0)

public actual fun fdDatasync(arg0: Int): Int =
    wasip1LibGeneratedFdDatasync(arg0)

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedFdFdstatGet(arg0, arg1)

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedFdFdstatSetFlags(arg0, arg1)

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    wasip1LibGeneratedFdFdstatSetRights(arg0, arg1, arg2)

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedFdFilestatGet(arg0, arg1)

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    wasip1LibGeneratedFdFilestatSetSize(arg0, arg1)

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    wasip1LibGeneratedFdFilestatSetTimes(arg0, arg1, arg2, arg3)

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasip1LibGeneratedFdPread(arg0, arg1, arg2, arg3, arg4)

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedFdPrestatGet(arg0, arg1)

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    wasip1LibGeneratedFdPrestatDirName(arg0, arg1, arg2)

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasip1LibGeneratedFdPwrite(arg0, arg1, arg2, arg3, arg4)

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasip1LibGeneratedFdRead(arg0, arg1, arg2, arg3)

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasip1LibGeneratedFdReaddir(arg0, arg1, arg2, arg3, arg4)

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedFdRenumber(arg0, arg1)

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    wasip1LibGeneratedFdSeek(arg0, arg1, arg2, arg3)

public actual fun fdSync(arg0: Int): Int =
    wasip1LibGeneratedFdSync(arg0)

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedFdTell(arg0, arg1)

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasip1LibGeneratedFdWrite(arg0, arg1, arg2, arg3)

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    wasip1LibGeneratedPathCreateDirectory(arg0, arg1, arg2)

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasip1LibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4)

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    wasip1LibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4, arg5, arg6)

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    wasip1LibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6)

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    wasip1LibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8)

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasip1LibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    wasip1LibGeneratedPathRemoveDirectory(arg0, arg1, arg2)

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasip1LibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasip1LibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4)

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    wasip1LibGeneratedPathUnlinkFile(arg0, arg1, arg2)

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasip1LibGeneratedPollOneoff(arg0, arg1, arg2, arg3)

public actual fun procExit(arg0: Int): Nothing {
    wasip1LibGeneratedProcExit(arg0)
    error("Unreachable noreturn C function procExit")
}

public actual fun procRaise(arg0: Int): Int =
    wasip1LibGeneratedProcRaise(arg0)

public actual fun wasiSnapshotPreview1SchedYield(): Int =
    wasip1LibGeneratedWasiSnapshotPreview1SchedYield()

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedRandomGet(arg0, arg1)

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    wasip1LibGeneratedSockAccept(arg0, arg1, arg2)

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasip1LibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasip1LibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4)

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    wasip1LibGeneratedSockShutdown(arg0, arg1)
