@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.itron.abi

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private object SysPalItronAbiJniBridge {
    init {
        System.loadLibrary("std_jni")
    }

    @JvmStatic
    external fun stdSysPalItronAbiAcreTsk(pkCtsk: Long): Int

    @JvmStatic
    external fun stdSysPalItronAbiGetTid(pTskid: Long): Int

    @JvmStatic
    external fun stdSysPalItronAbiDlyTsk(dlytim: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiTerTsk(tskid: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiDelTsk(tskid: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiGetPri(tskid: Int, pTskpri: Long): Int

    @JvmStatic
    external fun stdSysPalItronAbiRotRdq(tskpri: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiSlpTsk(): Int

    @JvmStatic
    external fun stdSysPalItronAbiTslpTsk(tmout: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiWupTsk(tskid: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiUnlCpu(): Int

    @JvmStatic
    external fun stdSysPalItronAbiDisDsp(): Int

    @JvmStatic
    external fun stdSysPalItronAbiEnaDsp(): Int

    @JvmStatic
    external fun stdSysPalItronAbiSnsDsp(): Int

    @JvmStatic
    external fun stdSysPalItronAbiGetTim(pSystim: Long): Int

    @JvmStatic
    external fun stdSysPalItronAbiAcreFlg(pkCflg: Long): Int

    @JvmStatic
    external fun stdSysPalItronAbiDelFlg(flgid: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiSetFlg(flgid: Int, setptn: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiClrFlg(flgid: Int, clrptn: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiWaiFlg(flgid: Int, waiptn: Int, wfmode: Int, pFlgptn: Long): Int

    @JvmStatic
    external fun stdSysPalItronAbiTwaiFlg(flgid: Int, waiptn: Int, wfmode: Int, pFlgptn: Long, tmout: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiAcreMtx(pkCmtx: Long): Int

    @JvmStatic
    external fun stdSysPalItronAbiDelMtx(tskid: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiLocMtx(mtxid: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiPlocMtx(mtxid: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiTlocMtx(mtxid: Int, tmout: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiUnlMtx(mtxid: Int): Int

    @JvmStatic
    external fun stdSysPalItronAbiExdTsk(): Int
}

public actual fun acreTsk(pkCtsk: ConstPtr): ErId =
    SysPalItronAbiJniBridge.stdSysPalItronAbiAcreTsk(pkCtsk.rawAddress)

public actual fun getTid(pTskid: MutPtr): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiGetTid(pTskid.rawAddress)

public actual fun dlyTsk(dlytim: Reltim): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiDlyTsk(dlytim.toInt())

public actual fun terTsk(tskid: ID): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiTerTsk(tskid)

public actual fun delTsk(tskid: ID): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiDelTsk(tskid)

public actual fun getPri(tskid: ID, pTskpri: MutPtr): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiGetPri(tskid, pTskpri.rawAddress)

public actual fun rotRdq(tskpri: Pri): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiRotRdq(tskpri)

public actual fun slpTsk(): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiSlpTsk()

public actual fun tslpTsk(tmout: Tmo): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiTslpTsk(tmout.toInt())

public actual fun wupTsk(tskid: ID): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiWupTsk(tskid)

public actual fun unlCpu(): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiUnlCpu()

public actual fun disDsp(): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiDisDsp()

public actual fun enaDsp(): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiEnaDsp()

public actual fun snsDsp(): BoolT =
    SysPalItronAbiJniBridge.stdSysPalItronAbiSnsDsp()

public actual fun getTim(pSystim: MutPtr): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiGetTim(pSystim.rawAddress)

public actual fun acreFlg(pkCflg: ConstPtr): ErId =
    SysPalItronAbiJniBridge.stdSysPalItronAbiAcreFlg(pkCflg.rawAddress)

public actual fun delFlg(flgid: ID): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiDelFlg(flgid)

public actual fun setFlg(flgid: ID, setptn: Flgptn): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiSetFlg(flgid, setptn.toInt())

public actual fun clrFlg(flgid: ID, clrptn: Flgptn): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiClrFlg(flgid, clrptn.toInt())

public actual fun waiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiWaiFlg(flgid, waiptn.toInt(), wfmode.toInt(), pFlgptn.rawAddress)

public actual fun twaiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr, tmout: Tmo): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiTwaiFlg(flgid, waiptn.toInt(), wfmode.toInt(), pFlgptn.rawAddress, tmout.toInt())

public actual fun acreMtx(pkCmtx: ConstPtr): ErId =
    SysPalItronAbiJniBridge.stdSysPalItronAbiAcreMtx(pkCmtx.rawAddress)

public actual fun delMtx(tskid: ID): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiDelMtx(tskid)

public actual fun locMtx(mtxid: ID): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiLocMtx(mtxid)

public actual fun plocMtx(mtxid: ID): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiPlocMtx(mtxid)

public actual fun tlocMtx(mtxid: ID, tmout: Tmo): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiTlocMtx(mtxid, tmout.toInt())

public actual fun unlMtx(mtxid: ID): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiUnlMtx(mtxid)

public actual fun exdTsk(): ER =
    SysPalItronAbiJniBridge.stdSysPalItronAbiExdTsk()
