@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.wasip1

// The host must provide the C companion wrapper exports in import module kmcore.
@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedArgsGet")
private external fun wasiRustlangLibGeneratedArgsGet(arg0: Int, arg1: Int): Int

public actual fun argsGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedArgsGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedArgsSizesGet")
private external fun wasiRustlangLibGeneratedArgsSizesGet(arg0: Int, arg1: Int): Int

public actual fun argsSizesGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedArgsSizesGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedEnvironGet")
private external fun wasiRustlangLibGeneratedEnvironGet(arg0: Int, arg1: Int): Int

public actual fun environGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedEnvironGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedEnvironSizesGet")
private external fun wasiRustlangLibGeneratedEnvironSizesGet(arg0: Int, arg1: Int): Int

public actual fun environSizesGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedEnvironSizesGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedClockResGet")
private external fun wasiRustlangLibGeneratedClockResGet(arg0: Int, arg1: Int): Int

public actual fun clockResGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedClockResGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedClockTimeGet")
private external fun wasiRustlangLibGeneratedClockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int

public actual fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int =
    wasiRustlangLibGeneratedClockTimeGet(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdAdvise")
private external fun wasiRustlangLibGeneratedFdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

public actual fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    wasiRustlangLibGeneratedFdAdvise(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdAllocate")
private external fun wasiRustlangLibGeneratedFdAllocate(arg0: Int, arg1: Long, arg2: Long): Int

public actual fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int =
    wasiRustlangLibGeneratedFdAllocate(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdClose")
private external fun wasiRustlangLibGeneratedFdClose(arg0: Int): Int

public actual fun fdClose(arg0: Int): Int =
    wasiRustlangLibGeneratedFdClose(arg0).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdDatasync")
private external fun wasiRustlangLibGeneratedFdDatasync(arg0: Int): Int

public actual fun fdDatasync(arg0: Int): Int =
    wasiRustlangLibGeneratedFdDatasync(arg0).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdFdstatGet")
private external fun wasiRustlangLibGeneratedFdFdstatGet(arg0: Int, arg1: Int): Int

public actual fun fdFdstatGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedFdFdstatGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdFdstatSetFlags")
private external fun wasiRustlangLibGeneratedFdFdstatSetFlags(arg0: Int, arg1: Int): Int

public actual fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedFdFdstatSetFlags(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdFdstatSetRights")
private external fun wasiRustlangLibGeneratedFdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int

public actual fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int =
    wasiRustlangLibGeneratedFdFdstatSetRights(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdFilestatGet")
private external fun wasiRustlangLibGeneratedFdFilestatGet(arg0: Int, arg1: Int): Int

public actual fun fdFilestatGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedFdFilestatGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdFilestatSetSize")
private external fun wasiRustlangLibGeneratedFdFilestatSetSize(arg0: Int, arg1: Long): Int

public actual fun fdFilestatSetSize(arg0: Int, arg1: Long): Int =
    wasiRustlangLibGeneratedFdFilestatSetSize(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdFilestatSetTimes")
private external fun wasiRustlangLibGeneratedFdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

public actual fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int =
    wasiRustlangLibGeneratedFdFilestatSetTimes(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdPread")
private external fun wasiRustlangLibGeneratedFdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

public actual fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasiRustlangLibGeneratedFdPread(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdPrestatGet")
private external fun wasiRustlangLibGeneratedFdPrestatGet(arg0: Int, arg1: Int): Int

public actual fun fdPrestatGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedFdPrestatGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdPrestatDirName")
private external fun wasiRustlangLibGeneratedFdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiRustlangLibGeneratedFdPrestatDirName(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdPwrite")
private external fun wasiRustlangLibGeneratedFdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

public actual fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasiRustlangLibGeneratedFdPwrite(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdRead")
private external fun wasiRustlangLibGeneratedFdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

public actual fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasiRustlangLibGeneratedFdRead(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdReaddir")
private external fun wasiRustlangLibGeneratedFdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

public actual fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int =
    wasiRustlangLibGeneratedFdReaddir(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdRenumber")
private external fun wasiRustlangLibGeneratedFdRenumber(arg0: Int, arg1: Int): Int

public actual fun fdRenumber(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedFdRenumber(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdSeek")
private external fun wasiRustlangLibGeneratedFdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int

public actual fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int =
    wasiRustlangLibGeneratedFdSeek(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdSync")
private external fun wasiRustlangLibGeneratedFdSync(arg0: Int): Int

public actual fun fdSync(arg0: Int): Int =
    wasiRustlangLibGeneratedFdSync(arg0).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdTell")
private external fun wasiRustlangLibGeneratedFdTell(arg0: Int, arg1: Int): Int

public actual fun fdTell(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedFdTell(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedFdWrite")
private external fun wasiRustlangLibGeneratedFdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

public actual fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasiRustlangLibGeneratedFdWrite(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathCreateDirectory")
private external fun wasiRustlangLibGeneratedPathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiRustlangLibGeneratedPathCreateDirectory(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathFilestatGet")
private external fun wasiRustlangLibGeneratedPathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

public actual fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasiRustlangLibGeneratedPathFilestatGet(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathFilestatSetTimes")
private external fun wasiRustlangLibGeneratedPathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int

public actual fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int =
    wasiRustlangLibGeneratedPathFilestatSetTimes(arg0, arg1, arg2, arg3, arg4, arg5, arg6).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathLink")
private external fun wasiRustlangLibGeneratedPathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int

public actual fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int =
    wasiRustlangLibGeneratedPathLink(arg0, arg1, arg2, arg3, arg4, arg5, arg6).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathOpen")
private external fun wasiRustlangLibGeneratedPathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int

public actual fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int =
    wasiRustlangLibGeneratedPathOpen(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathReadlink")
private external fun wasiRustlangLibGeneratedPathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

public actual fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasiRustlangLibGeneratedPathReadlink(arg0, arg1, arg2, arg3, arg4, arg5).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathRemoveDirectory")
private external fun wasiRustlangLibGeneratedPathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiRustlangLibGeneratedPathRemoveDirectory(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathRename")
private external fun wasiRustlangLibGeneratedPathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

public actual fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasiRustlangLibGeneratedPathRename(arg0, arg1, arg2, arg3, arg4, arg5).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathSymlink")
private external fun wasiRustlangLibGeneratedPathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

public actual fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasiRustlangLibGeneratedPathSymlink(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPathUnlinkFile")
private external fun wasiRustlangLibGeneratedPathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiRustlangLibGeneratedPathUnlinkFile(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedPollOneoff")
private external fun wasiRustlangLibGeneratedPollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

public actual fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int =
    wasiRustlangLibGeneratedPollOneoff(arg0, arg1, arg2, arg3).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedProcExit")
private external fun wasiRustlangLibGeneratedProcExit(arg0: Int): Unit

public actual fun procExit(arg0: Int): Nothing {
    wasiRustlangLibGeneratedProcExit(arg0)
    error("C noreturn function rustlangLibGeneratedProcExit returned")
}

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedProcRaise")
private external fun wasiRustlangLibGeneratedProcRaise(arg0: Int): Int

public actual fun procRaise(arg0: Int): Int =
    wasiRustlangLibGeneratedProcRaise(arg0).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedSchedYield")
private external fun wasiRustlangLibGeneratedSchedYield(): Int

public actual fun schedYield(): Int =
    wasiRustlangLibGeneratedSchedYield().toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedRandomGet")
private external fun wasiRustlangLibGeneratedRandomGet(arg0: Int, arg1: Int): Int

public actual fun randomGet(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedRandomGet(arg0, arg1).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedSockAccept")
private external fun wasiRustlangLibGeneratedSockAccept(arg0: Int, arg1: Int, arg2: Int): Int

public actual fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int =
    wasiRustlangLibGeneratedSockAccept(arg0, arg1, arg2).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedSockRecv")
private external fun wasiRustlangLibGeneratedSockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

public actual fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int =
    wasiRustlangLibGeneratedSockRecv(arg0, arg1, arg2, arg3, arg4, arg5).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedSockSend")
private external fun wasiRustlangLibGeneratedSockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

public actual fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int =
    wasiRustlangLibGeneratedSockSend(arg0, arg1, arg2, arg3, arg4).toInt()

@kotlin.wasm.WasmImport("kmcore", "rustlangLibGeneratedSockShutdown")
private external fun wasiRustlangLibGeneratedSockShutdown(arg0: Int, arg1: Int): Int

public actual fun sockShutdown(arg0: Int, arg1: Int): Int =
    wasiRustlangLibGeneratedSockShutdown(arg0, arg1).toInt()
