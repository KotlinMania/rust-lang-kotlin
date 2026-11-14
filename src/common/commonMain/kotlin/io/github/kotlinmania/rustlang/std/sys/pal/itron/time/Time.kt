// port-lint: source sys/pal/itron/time.rs
package io.github.kotlinmania.rustlang.std.sys.pal.itron.time

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.iter.sources.fromfn.fromFn
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.ER
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_TMOUT
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.Systim
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.TMAX_RELTIM
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.Tmo
import io.github.kotlinmania.rustlang.std.sys.pal.itron.error.expectSuccess

public fun getTim(): Systim {
    // Safety: The provided pointer is valid
    return run {
        val out = MaybeUninit.uninit(ObjectSlotLayout<Systim>())
        expectSuccess(getTim(out.asMutPtr()), "get_tim")
        out.assumeInit()
    }
}

/**
 * Split `Duration` into zero or more `RELTIM`s.
 */
public fun dur2reltims(dur: Duration): Any {
    // `RELTIM` is microseconds
    var ticks = dur.asMicros()
    return fromFn({
        if (ticks == 0) {
            null
        } else if (ticks <= U128.fromLong(TMAX_RELTIM.toLong())) {
            replace(ticks, 0).toUInt()
        } else {
            ticks -= U128.fromLong(TMAX_RELTIM.toLong())
            TMAX_RELTIM
        }
    })
}

/**
 * Split `Duration` into one or more `TMO`s.
 */
public fun dur2tmos(dur: Duration): Any {
    // `TMO` is microseconds
    var ticks = dur.asMicros()
    var end = false
    return fromFn({
        if (end) {
            null
        } else if (ticks <= U128.fromLong(TMAX_RELTIM.toLong())) {
            end = true
            replace(ticks, 0).toUInt()
        } else {
            ticks -= U128.fromLong(TMAX_RELTIM.toLong())
            TMAX_RELTIM
        }
    })
}

/**
 * Split `Duration` into one or more API calls with timeout.
 */
public fun withTmos(dur: Duration, f: (Tmo) -> ER): ER {
    var er = E_TMOUT
    for (tmo in dur2tmos(dur)) {
        er = f(tmo)
        if (er != E_TMOUT) {
            break
        }
    }
    return er
}

/**
 * Split `Duration` into one or more API calls with timeout. This function can
 * handle spurious wakeups.
 */
public fun withTmosStrong(dur: Duration, f: (Tmo) -> ER): ER {
    // `TMO` and `SYSTIM` are microseconds.
    // Clamp at `SYSTIM.MAX` for performance reasons. This shouldn't cause
    // a problem in practice. (`ULong.MAX` μs ≈ 584942 years)
    val ticks = dur.asMicros().min(U128.fromLong(Systim.MAX.toLong())).toULong()
    val start = getTim()
    var elapsed = 0
    var er = E_TMOUT
    while (elapsed <= ticks) {
        er = f(elapsed.min(TMAX_RELTIM.toULong()).toUInt())
        if (er != E_TMOUT) {
            break
        }
        elapsed = (getTim() - (start.toULong()))
    }
    return er
}
