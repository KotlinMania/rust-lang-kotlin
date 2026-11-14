@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.wasm.ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.std.sys.pal.itron.abi

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// The host must provide the C companion wrapper exports in import module std.
public actual fun acreTsk(pkCtsk: ConstPtr): ErId =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalItronAbiAcreTsk")

public actual fun getTid(pTskid: MutPtr): ER =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalItronAbiGetTid")

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiDlyTsk")
private external fun wasiStdSysPalItronAbiDlyTsk(dlytim: Int): Int

public actual fun dlyTsk(dlytim: Reltim): ER =
    wasiStdSysPalItronAbiDlyTsk(dlytim.toInt()).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiTerTsk")
private external fun wasiStdSysPalItronAbiTerTsk(tskid: Int): Int

public actual fun terTsk(tskid: ID): ER =
    wasiStdSysPalItronAbiTerTsk(tskid).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiDelTsk")
private external fun wasiStdSysPalItronAbiDelTsk(tskid: Int): Int

public actual fun delTsk(tskid: ID): ER =
    wasiStdSysPalItronAbiDelTsk(tskid).toInt()

public actual fun getPri(tskid: ID, pTskpri: MutPtr): ER =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalItronAbiGetPri")

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiRotRdq")
private external fun wasiStdSysPalItronAbiRotRdq(tskpri: Int): Int

public actual fun rotRdq(tskpri: Pri): ER =
    wasiStdSysPalItronAbiRotRdq(tskpri).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiSlpTsk")
private external fun wasiStdSysPalItronAbiSlpTsk(): Int

public actual fun slpTsk(): ER =
    wasiStdSysPalItronAbiSlpTsk().toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiTslpTsk")
private external fun wasiStdSysPalItronAbiTslpTsk(tmout: Int): Int

public actual fun tslpTsk(tmout: Tmo): ER =
    wasiStdSysPalItronAbiTslpTsk(tmout.toInt()).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiWupTsk")
private external fun wasiStdSysPalItronAbiWupTsk(tskid: Int): Int

public actual fun wupTsk(tskid: ID): ER =
    wasiStdSysPalItronAbiWupTsk(tskid).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiUnlCpu")
private external fun wasiStdSysPalItronAbiUnlCpu(): Int

public actual fun unlCpu(): ER =
    wasiStdSysPalItronAbiUnlCpu().toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiDisDsp")
private external fun wasiStdSysPalItronAbiDisDsp(): Int

public actual fun disDsp(): ER =
    wasiStdSysPalItronAbiDisDsp().toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiEnaDsp")
private external fun wasiStdSysPalItronAbiEnaDsp(): Int

public actual fun enaDsp(): ER =
    wasiStdSysPalItronAbiEnaDsp().toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiSnsDsp")
private external fun wasiStdSysPalItronAbiSnsDsp(): Int

public actual fun snsDsp(): BoolT =
    wasiStdSysPalItronAbiSnsDsp().toInt()

public actual fun getTim(pSystim: MutPtr): ER =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalItronAbiGetTim")

public actual fun acreFlg(pkCflg: ConstPtr): ErId =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalItronAbiAcreFlg")

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiDelFlg")
private external fun wasiStdSysPalItronAbiDelFlg(flgid: Int): Int

public actual fun delFlg(flgid: ID): ER =
    wasiStdSysPalItronAbiDelFlg(flgid).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiSetFlg")
private external fun wasiStdSysPalItronAbiSetFlg(flgid: Int, setptn: Int): Int

public actual fun setFlg(flgid: ID, setptn: Flgptn): ER =
    wasiStdSysPalItronAbiSetFlg(flgid, setptn.toInt()).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiClrFlg")
private external fun wasiStdSysPalItronAbiClrFlg(flgid: Int, clrptn: Int): Int

public actual fun clrFlg(flgid: ID, clrptn: Flgptn): ER =
    wasiStdSysPalItronAbiClrFlg(flgid, clrptn.toInt()).toInt()

public actual fun waiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr): ER =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalItronAbiWaiFlg")

public actual fun twaiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr, tmout: Tmo): ER =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalItronAbiTwaiFlg")

public actual fun acreMtx(pkCmtx: ConstPtr): ErId =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for stdSysPalItronAbiAcreMtx")

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiDelMtx")
private external fun wasiStdSysPalItronAbiDelMtx(tskid: Int): Int

public actual fun delMtx(tskid: ID): ER =
    wasiStdSysPalItronAbiDelMtx(tskid).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiLocMtx")
private external fun wasiStdSysPalItronAbiLocMtx(mtxid: Int): Int

public actual fun locMtx(mtxid: ID): ER =
    wasiStdSysPalItronAbiLocMtx(mtxid).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiPlocMtx")
private external fun wasiStdSysPalItronAbiPlocMtx(mtxid: Int): Int

public actual fun plocMtx(mtxid: ID): ER =
    wasiStdSysPalItronAbiPlocMtx(mtxid).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiTlocMtx")
private external fun wasiStdSysPalItronAbiTlocMtx(mtxid: Int, tmout: Int): Int

public actual fun tlocMtx(mtxid: ID, tmout: Tmo): ER =
    wasiStdSysPalItronAbiTlocMtx(mtxid, tmout.toInt()).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiUnlMtx")
private external fun wasiStdSysPalItronAbiUnlMtx(mtxid: Int): Int

public actual fun unlMtx(mtxid: ID): ER =
    wasiStdSysPalItronAbiUnlMtx(mtxid).toInt()

@kotlin.wasm.WasmImport("std", "stdSysPalItronAbiExdTsk")
private external fun wasiStdSysPalItronAbiExdTsk(): Int

public actual fun exdTsk(): ER =
    wasiStdSysPalItronAbiExdTsk().toInt()
