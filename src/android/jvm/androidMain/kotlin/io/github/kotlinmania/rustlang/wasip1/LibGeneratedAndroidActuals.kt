@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.wasip1

private object LibGeneratedJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun wasip1LibGeneratedArgsGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedArgsSizesGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedEnvironGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedEnvironSizesGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedClockResGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedClockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdAllocate(arg0: Int, arg1: Long, arg2: Long): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdClose(arg0: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdDatasync(arg0: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdFdstatGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdFdstatSetFlags(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdFilestatGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdFilestatSetSize(arg0: Int, arg1: Long): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdPrestatGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdRenumber(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdSync(arg0: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdTell(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedFdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedPollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedProcExit(arg0: Int)

    @JvmStatic
    external fun wasip1LibGeneratedProcRaise(arg0: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedWasiSnapshotPreview1SchedYield(): Int

    @JvmStatic
    external fun wasip1LibGeneratedRandomGet(arg0: Int, arg1: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedSockAccept(arg0: Int, arg1: Int, arg2: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedSockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedSockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

    @JvmStatic
    external fun wasip1LibGeneratedSockShutdown(arg0: Int, arg1: Int): Int
}

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedArgsGet(arg0, arg1)

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedArgsSizesGet(arg0, arg1)

public actual fun environGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedEnvironGet(arg0, arg1)

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedEnvironSizesGet(arg0, arg1)

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedClockResGet(arg0, arg1)

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedClockTimeGet(arg0, arg1, arg2)

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdAdvise(arg0, arg1, arg2, arg3)

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdAllocate(arg0, arg1, arg2)

public actual fun fdClose(arg0: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdClose(arg0)

public actual fun fdDatasync(arg0: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdDatasync(arg0)

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdFdstatGet(arg0, arg1)

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdFdstatSetFlags(arg0, arg1)

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdFdstatSetRights(arg0, arg1, arg2)

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdFilestatGet(arg0, arg1)

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdFilestatSetSize(arg0, arg1)

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdFilestatSetTimes(arg0, arg1, arg2, arg3)

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdPread(arg0, arg1, arg2, arg3, arg4)

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdPrestatGet(arg0, arg1)

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdPrestatDirName(arg0, arg1, arg2)

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdPwrite(arg0, arg1, arg2, arg3, arg4)

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdRead(arg0, arg1, arg2, arg3)

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdReaddir(arg0, arg1, arg2, arg3, arg4)

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdRenumber(arg0, arg1)

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdSeek(arg0, arg1, arg2, arg3)

public actual fun fdSync(arg0: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdSync(arg0)

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdTell(arg0, arg1)

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedFdWrite(arg0, arg1, arg2, arg3)

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathCreateDirectory(arg0, arg1, arg2)

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4)

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4, arg5, arg6)

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6)

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8)

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathRemoveDirectory(arg0, arg1, arg2)

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4)

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPathUnlinkFile(arg0, arg1, arg2)

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedPollOneoff(arg0, arg1, arg2, arg3)

public actual fun procExit(arg0: Int): Nothing {
    LibGeneratedJniBridge.wasip1LibGeneratedProcExit(arg0)
    error("Unreachable noreturn C function procExit")
}

public actual fun procRaise(arg0: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedProcRaise(arg0)

public actual fun wasiSnapshotPreview1SchedYield(): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedWasiSnapshotPreview1SchedYield()

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedRandomGet(arg0, arg1)

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedSockAccept(arg0, arg1, arg2)

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5)

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4)

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    LibGeneratedJniBridge.wasip1LibGeneratedSockShutdown(arg0, arg1)
