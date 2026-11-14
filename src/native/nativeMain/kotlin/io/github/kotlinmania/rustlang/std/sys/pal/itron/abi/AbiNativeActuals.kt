@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class, kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.itron.abi

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiAcreFlg
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiAcreMtx
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiAcreTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiClrFlg
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiDelFlg
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiDelMtx
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiDelTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiDisDsp
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiDlyTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiEnaDsp
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiExdTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiGetPri
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiGetTid
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiGetTim
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiLocMtx
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiPlocMtx
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiRotRdq
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiSetFlg
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiSlpTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiSnsDsp
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiTerTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiTlocMtx
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiTslpTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiTwaiFlg
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiUnlCpu
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiUnlMtx
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiWaiFlg
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.cinterop.stdSysPalItronAbiWupTsk
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.toCPointer

public actual fun acreTsk(pkCtsk: ConstPtr): ErId =
    stdSysPalItronAbiAcreTsk(pkCtsk.rawAddress.toCPointer<ByteVar>())

public actual fun getTid(pTskid: MutPtr): ER =
    stdSysPalItronAbiGetTid(pTskid.rawAddress.toCPointer<ByteVar>())

public actual fun dlyTsk(dlytim: Reltim): ER =
    stdSysPalItronAbiDlyTsk(dlytim)

public actual fun terTsk(tskid: ID): ER =
    stdSysPalItronAbiTerTsk(tskid)

public actual fun delTsk(tskid: ID): ER =
    stdSysPalItronAbiDelTsk(tskid)

public actual fun getPri(tskid: ID, pTskpri: MutPtr): ER =
    stdSysPalItronAbiGetPri(tskid, pTskpri.rawAddress.toCPointer<ByteVar>())

public actual fun rotRdq(tskpri: Pri): ER =
    stdSysPalItronAbiRotRdq(tskpri)

public actual fun slpTsk(): ER =
    stdSysPalItronAbiSlpTsk()

public actual fun tslpTsk(tmout: Tmo): ER =
    stdSysPalItronAbiTslpTsk(tmout)

public actual fun wupTsk(tskid: ID): ER =
    stdSysPalItronAbiWupTsk(tskid)

public actual fun unlCpu(): ER =
    stdSysPalItronAbiUnlCpu()

public actual fun disDsp(): ER =
    stdSysPalItronAbiDisDsp()

public actual fun enaDsp(): ER =
    stdSysPalItronAbiEnaDsp()

public actual fun snsDsp(): BoolT =
    stdSysPalItronAbiSnsDsp()

public actual fun getTim(pSystim: MutPtr): ER =
    stdSysPalItronAbiGetTim(pSystim.rawAddress.toCPointer<ByteVar>())

public actual fun acreFlg(pkCflg: ConstPtr): ErId =
    stdSysPalItronAbiAcreFlg(pkCflg.rawAddress.toCPointer<ByteVar>())

public actual fun delFlg(flgid: ID): ER =
    stdSysPalItronAbiDelFlg(flgid)

public actual fun setFlg(flgid: ID, setptn: Flgptn): ER =
    stdSysPalItronAbiSetFlg(flgid, setptn)

public actual fun clrFlg(flgid: ID, clrptn: Flgptn): ER =
    stdSysPalItronAbiClrFlg(flgid, clrptn)

public actual fun waiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr): ER =
    stdSysPalItronAbiWaiFlg(flgid, waiptn, wfmode, pFlgptn.rawAddress.toCPointer<ByteVar>())

public actual fun twaiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr, tmout: Tmo): ER =
    stdSysPalItronAbiTwaiFlg(flgid, waiptn, wfmode, pFlgptn.rawAddress.toCPointer<ByteVar>(), tmout)

public actual fun acreMtx(pkCmtx: ConstPtr): ErId =
    stdSysPalItronAbiAcreMtx(pkCmtx.rawAddress.toCPointer<ByteVar>())

public actual fun delMtx(tskid: ID): ER =
    stdSysPalItronAbiDelMtx(tskid)

public actual fun locMtx(mtxid: ID): ER =
    stdSysPalItronAbiLocMtx(mtxid)

public actual fun plocMtx(mtxid: ID): ER =
    stdSysPalItronAbiPlocMtx(mtxid)

public actual fun tlocMtx(mtxid: ID, tmout: Tmo): ER =
    stdSysPalItronAbiTlocMtx(mtxid, tmout)

public actual fun unlMtx(mtxid: ID): ER =
    stdSysPalItronAbiUnlMtx(mtxid)

public actual fun exdTsk(): ER =
    stdSysPalItronAbiExdTsk()
