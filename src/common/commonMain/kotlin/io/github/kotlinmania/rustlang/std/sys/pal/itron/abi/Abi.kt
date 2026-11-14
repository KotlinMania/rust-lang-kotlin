// port-lint: source sys/pal/itron/abi.rs
package io.github.kotlinmania.rustlang.std.sys.pal.itron.abi

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.os.raw.CUint

// ABI for μITRON derivatives

public typealias IntT = CInt

public typealias UintT = CUint

public typealias BoolT = IntT

/**
 * Kernel object ID
 */
public typealias ID = IntT

/**
 * The current task.
 */
public val TSK_SELF: ID = 0

/**
 * Relative time
 */
public typealias Reltim = UInt

/**
 * Timeout (a valid `RELTIM` value or `TMO_FEVR`)
 */
public typealias Tmo = UInt

/**
 * The infinite timeout value
 */
public val TMO_FEVR: Tmo = Tmo.MAX_VALUE

/**
 * The maximum valid value of `RELTIM`
 */
public val TMAX_RELTIM: Reltim = 4000000000u

/**
 * System time
 */
public typealias Systim = ULong

/**
 * Error code type
 */
public typealias ER = IntT

/**
 * Error code type, `ID` on success
 */
public typealias ErId = IntT

/**
 * Service call operational mode
 */
public typealias Mode = UintT

/**
 * OR waiting condition for an eventflag
 */
public val TWF_ORW: Mode = 0x01u

/**
 * Object attributes
 */
public typealias Atr = UintT

/**
 * FIFO wait order
 */
public val TA_FIFO: Atr = 0u

/**
 * Only one task is allowed to be in the waiting state for the eventflag
 */
public val TA_WSGL: Atr = 0u

/**
 * The eventflag’s bit pattern is cleared when a task is released from the
 * waiting state for that eventflag.
 */
public val TA_CLR: Atr = 0x04u

/**
 * Bit pattern of an eventflag
 */
public typealias Flgptn = UintT

/**
 * Task or interrupt priority
 */
public typealias Pri = IntT

/**
 * The special value of `PRI` representing the current task's priority.
 */
public val TPRI_SELF: Pri = 0

/**
 * Activate the task on creation
 */
public val TA_ACT: Atr = 0x01u

/**
 * The maximum count of a semaphore
 */
public val TMAX_MAXSEM: UintT = UintT.MAX_VALUE

/**
 * Callback parameter
 */
public typealias Exinf = Long

/**
 * Task entrypoint
 */
public fun interface TaskFn {
    public operator fun invoke(arg0: Exinf)
}

public typealias Task = TaskFn?

// Error codes
public val E_OK: ER = 0

public val E_SYS: ER = -5

public val E_NOSPT: ER = -9

public val E_RSFN: ER = -10

public val E_RSATR: ER = -11

public val E_PAR: ER = -17

public val E_ID: ER = -18

public val E_CTX: ER = -25

public val E_MACV: ER = -26

public val E_OACV: ER = -27

public val E_ILUSE: ER = -28

public val E_NOMEM: ER = -33

public val E_NOID: ER = -34

public val E_NORES: ER = -35

public val E_OBJ: ER = -41

public val E_NOEXS: ER = -42

public val E_QOVR: ER = -43

public val E_RLWAI: ER = -49

public val E_TMOUT: ER = -50

public val E_DLT: ER = -51

public val E_CLS: ER = -52

public val E_RASTER: ER = -53

public val E_WBLK: ER = -57

public val E_BOVR: ER = -58

public val E_COMM: ER = -65

public open class TCsem(
    public var sematr: Atr,
    public var isemcnt: UintT,
    public var maxsem: UintT,
) {
    public open fun clone(): TCsem = TCsem(sematr, isemcnt, maxsem)

    public companion object {
        public fun typeLayout(): TypeLayout<TCsem> =
            object : TypeLayout<TCsem> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): TCsem =
                    TCsem(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: TCsem) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.sematr)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.isemcnt)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.maxsem)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TCsem =
                    TCsem(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TCsem) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.sematr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.isemcnt)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.maxsem)
                }
            }
    }
}

public open class TCflg(
    public var flgatr: Atr,
    public var iflgptn: Flgptn,
) {
    public open fun clone(): TCflg = TCflg(flgatr, iflgptn)

    public companion object {
        public fun typeLayout(): TypeLayout<TCflg> =
            object : TypeLayout<TCflg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): TCflg =
                    TCflg(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: TCflg) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.flgatr)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.iflgptn)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TCflg =
                    TCflg(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TCflg) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.flgatr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.iflgptn)
                }
            }
    }
}

public open class TCmtx(
    public var mtxatr: Atr,
    public var ceilpri: Pri,
) {
    public open fun clone(): TCmtx = TCmtx(mtxatr, ceilpri)

    public companion object {
        public fun typeLayout(): TypeLayout<TCmtx> =
            object : TypeLayout<TCmtx> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): TCmtx =
                    TCmtx(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: TCmtx) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.mtxatr)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ceilpri)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TCmtx =
                    TCmtx(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TCmtx) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.mtxatr)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ceilpri)
                }
            }
    }
}

public open class TCtsk internal constructor(
    public var tskatr: Atr,
    public var exinf: Exinf,
    public var task: Task,
    public var itskpri: Pri,
    public var stksz: Int,
    stkCtorArg: MutPtr,
) {
    public var stk: MutPtr = stkCtorArg
        internal set

    public open fun clone(): TCtsk = TCtsk(tskatr, exinf, task, itskpri, stksz, stk)

    public companion object {
        public operator fun invoke(
            tskatr: Atr,
            exinf: Exinf,
            task: Task,
            itskpri: Pri,
            stksz: Int,
            stk: MutPtr,
        ): TCtsk = TCtsk(
            tskatr = tskatr,
            exinf = exinf,
            task = task,
            itskpri = itskpri,
            stksz = stksz,
            stkCtorArg = stk,
        )
    }
}

public expect fun acreTsk(pkCtsk: ConstPtr): ErId

public expect fun getTid(pTskid: MutPtr): ER

public expect fun dlyTsk(dlytim: Reltim): ER

public expect fun terTsk(tskid: ID): ER

public expect fun delTsk(tskid: ID): ER

public expect fun getPri(tskid: ID, pTskpri: MutPtr): ER

public expect fun rotRdq(tskpri: Pri): ER

public expect fun slpTsk(): ER

public expect fun tslpTsk(tmout: Tmo): ER

public expect fun wupTsk(tskid: ID): ER

public expect fun unlCpu(): ER

public expect fun disDsp(): ER

public expect fun enaDsp(): ER

public expect fun snsDsp(): BoolT

public expect fun getTim(pSystim: MutPtr): ER

public expect fun acreFlg(pkCflg: ConstPtr): ErId

public expect fun delFlg(flgid: ID): ER

public expect fun setFlg(flgid: ID, setptn: Flgptn): ER

public expect fun clrFlg(flgid: ID, clrptn: Flgptn): ER

public expect fun waiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr): ER

public expect fun twaiFlg(flgid: ID, waiptn: Flgptn, wfmode: Mode, pFlgptn: MutPtr, tmout: Tmo): ER

public expect fun acreMtx(pkCmtx: ConstPtr): ErId

public expect fun delMtx(tskid: ID): ER

public expect fun locMtx(mtxid: ID): ER

public expect fun plocMtx(mtxid: ID): ER

public expect fun tlocMtx(mtxid: ID, tmout: Tmo): ER

public expect fun unlMtx(mtxid: ID): ER

public expect fun exdTsk(): ER
