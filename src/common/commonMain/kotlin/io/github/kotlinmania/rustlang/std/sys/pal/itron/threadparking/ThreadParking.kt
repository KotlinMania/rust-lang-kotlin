// port-lint: source sys/pal/itron/thread_parking.rs
package io.github.kotlinmania.rustlang.std.sys.pal.itron.threadparking

import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_NOEXS
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_OBJ
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_OK
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_QOVR
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_RLWAI
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_TMOUT
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.ID
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.slpTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.tslpTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.wupTsk
import io.github.kotlinmania.rustlang.std.sys.pal.itron.error.expectSuccessAborting
import io.github.kotlinmania.rustlang.std.sys.pal.itron.time.withTmos

public typealias ThreadId = ID

public fun park(hint: Int) {
    when (val matchVal = slpTsk()) {
        E_OK, E_RLWAI -> {}
        else -> {
            val err = matchVal
            expectSuccessAborting(err, "slp_tsk")
        }
    }
}

public fun parkTimeout(dur: Duration, hint: Int) {
    when (val matchVal = withTmos(dur, { tmo -> tslpTsk(tmo) })) {
        E_OK, E_RLWAI, E_TMOUT -> {}
        else -> {
            val err = matchVal
            expectSuccessAborting(err, "tslp_tsk")
        }
    }
}

public fun unpark(id: ThreadId, hint: Int) {
    when (val matchVal = wupTsk(id)) {
        // It is allowed to try to wake up a destroyed or unrelated task, so we ignore all
        // errors that could result from that situation.
        E_OK, E_NOEXS, E_OBJ, E_QOVR -> {}
        else -> {
            val err = matchVal
            expectSuccessAborting(err, "wup_tsk")
        }
    }
}
