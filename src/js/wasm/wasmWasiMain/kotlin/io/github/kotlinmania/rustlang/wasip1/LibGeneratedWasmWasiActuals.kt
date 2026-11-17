@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.wasip1

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedArgsGet")
private external fun wasiWasip1LibGeneratedArgsGet(arg0: Int, arg1: Int): Int

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedArgsGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedArgsSizesGet")
private external fun wasiWasip1LibGeneratedArgsSizesGet(arg0: Int, arg1: Int): Int

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedArgsSizesGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedEnvironGet")
private external fun wasiWasip1LibGeneratedEnvironGet(arg0: Int, arg1: Int): Int

public actual fun environGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedEnvironGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedEnvironSizesGet")
private external fun wasiWasip1LibGeneratedEnvironSizesGet(arg0: Int, arg1: Int): Int

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedEnvironSizesGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedClockResGet")
private external fun wasiWasip1LibGeneratedClockResGet(arg0: Int, arg1: Int): Int

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedClockResGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedClockTimeGet")
private external fun wasiWasip1LibGeneratedClockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    wasiWasip1LibGeneratedClockTimeGet(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdAdvise")
private external fun wasiWasip1LibGeneratedFdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    wasiWasip1LibGeneratedFdAdvise(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdAllocate")
private external fun wasiWasip1LibGeneratedFdAllocate(arg0: Int, arg1: Long, arg2: Long): Int

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    wasiWasip1LibGeneratedFdAllocate(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdClose")
private external fun wasiWasip1LibGeneratedFdClose(arg0: Int): Int

public actual fun fdClose(arg0: Int): Int =
    wasiWasip1LibGeneratedFdClose(arg0).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdDatasync")
private external fun wasiWasip1LibGeneratedFdDatasync(arg0: Int): Int

public actual fun fdDatasync(arg0: Int): Int =
    wasiWasip1LibGeneratedFdDatasync(arg0).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdFdstatGet")
private external fun wasiWasip1LibGeneratedFdFdstatGet(arg0: Int, arg1: Int): Int

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedFdFdstatGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdFdstatSetFlags")
private external fun wasiWasip1LibGeneratedFdFdstatSetFlags(arg0: Int, arg1: Int): Int

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedFdFdstatSetFlags(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdFdstatSetRights")
private external fun wasiWasip1LibGeneratedFdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    wasiWasip1LibGeneratedFdFdstatSetRights(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdFilestatGet")
private external fun wasiWasip1LibGeneratedFdFilestatGet(arg0: Int, arg1: Int): Int

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedFdFilestatGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdFilestatSetSize")
private external fun wasiWasip1LibGeneratedFdFilestatSetSize(arg0: Int, arg1: Long): Int

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    wasiWasip1LibGeneratedFdFilestatSetSize(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdFilestatSetTimes")
private external fun wasiWasip1LibGeneratedFdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    wasiWasip1LibGeneratedFdFilestatSetTimes(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdPread")
private external fun wasiWasip1LibGeneratedFdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasiWasip1LibGeneratedFdPread(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdPrestatGet")
private external fun wasiWasip1LibGeneratedFdPrestatGet(arg0: Int, arg1: Int): Int

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedFdPrestatGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdPrestatDirName")
private external fun wasiWasip1LibGeneratedFdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiWasip1LibGeneratedFdPrestatDirName(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdPwrite")
private external fun wasiWasip1LibGeneratedFdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasiWasip1LibGeneratedFdPwrite(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdRead")
private external fun wasiWasip1LibGeneratedFdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasiWasip1LibGeneratedFdRead(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdReaddir")
private external fun wasiWasip1LibGeneratedFdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasiWasip1LibGeneratedFdReaddir(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdRenumber")
private external fun wasiWasip1LibGeneratedFdRenumber(arg0: Int, arg1: Int): Int

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedFdRenumber(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdSeek")
private external fun wasiWasip1LibGeneratedFdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    wasiWasip1LibGeneratedFdSeek(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdSync")
private external fun wasiWasip1LibGeneratedFdSync(arg0: Int): Int

public actual fun fdSync(arg0: Int): Int =
    wasiWasip1LibGeneratedFdSync(arg0).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdTell")
private external fun wasiWasip1LibGeneratedFdTell(arg0: Int, arg1: Int): Int

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedFdTell(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedFdWrite")
private external fun wasiWasip1LibGeneratedFdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasiWasip1LibGeneratedFdWrite(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathCreateDirectory")
private external fun wasiWasip1LibGeneratedPathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiWasip1LibGeneratedPathCreateDirectory(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathFilestatGet")
private external fun wasiWasip1LibGeneratedPathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasiWasip1LibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathFilestatSetTimes")
private external fun wasiWasip1LibGeneratedPathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    wasiWasip1LibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4, arg5, arg6).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathLink")
private external fun wasiWasip1LibGeneratedPathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    wasiWasip1LibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathOpen")
private external fun wasiWasip1LibGeneratedPathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    wasiWasip1LibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathReadlink")
private external fun wasiWasip1LibGeneratedPathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasiWasip1LibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathRemoveDirectory")
private external fun wasiWasip1LibGeneratedPathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiWasip1LibGeneratedPathRemoveDirectory(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathRename")
private external fun wasiWasip1LibGeneratedPathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasiWasip1LibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathSymlink")
private external fun wasiWasip1LibGeneratedPathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasiWasip1LibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPathUnlinkFile")
private external fun wasiWasip1LibGeneratedPathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiWasip1LibGeneratedPathUnlinkFile(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedPollOneoff")
private external fun wasiWasip1LibGeneratedPollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasiWasip1LibGeneratedPollOneoff(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedProcExit")
private external fun wasiWasip1LibGeneratedProcExit(arg0: Int): Unit

public actual fun procExit(arg0: Int): Nothing {
    wasiWasip1LibGeneratedProcExit(arg0)
    error("C noreturn function wasip1LibGeneratedProcExit returned")
}

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedProcRaise")
private external fun wasiWasip1LibGeneratedProcRaise(arg0: Int): Int

public actual fun procRaise(arg0: Int): Int =
    wasiWasip1LibGeneratedProcRaise(arg0).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedWasiSnapshotPreview1SchedYield")
private external fun wasiWasip1LibGeneratedWasiSnapshotPreview1SchedYield(): Int

public actual fun wasiSnapshotPreview1SchedYield(): Int =
    wasiWasip1LibGeneratedWasiSnapshotPreview1SchedYield().toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedRandomGet")
private external fun wasiWasip1LibGeneratedRandomGet(arg0: Int, arg1: Int): Int

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedRandomGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedSockAccept")
private external fun wasiWasip1LibGeneratedSockAccept(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiWasip1LibGeneratedSockAccept(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedSockRecv")
private external fun wasiWasip1LibGeneratedSockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasiWasip1LibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedSockSend")
private external fun wasiWasip1LibGeneratedSockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasiWasip1LibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "wasip1LibGeneratedSockShutdown")
private external fun wasiWasip1LibGeneratedSockShutdown(arg0: Int, arg1: Int): Int

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    wasiWasip1LibGeneratedSockShutdown(arg0, arg1).toInt()
