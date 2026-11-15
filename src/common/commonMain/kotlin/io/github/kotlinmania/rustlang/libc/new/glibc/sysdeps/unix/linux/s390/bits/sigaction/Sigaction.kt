// port-lint: source new/glibc/sysdeps/unix/linux/s390/bits/sigaction.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.s390.bits.sigaction

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.types.u32CastInt
import io.github.kotlinmania.rustlang.unix.SighandlerT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.SigsetT

// Header: `sysdeps / unix / sysv / linux / s390 / bits / sigaction.h`

public fun interface SigactionSaRestorerCallback {
    public operator fun invoke()
}

public data class Sigaction(
    public var saSigaction: SighandlerT = 0uL,
    public var glibcReserved0: CInt = 0,
    public var saFlags: CInt = 0,
    public var saRestorer: SigactionSaRestorerCallback? = null,
    public var saMask: SigsetT = SigsetT(),
)

public val SA_NOCLDSTOP: CInt = 0x00000001

public val SA_NOCLDWAIT: CInt = 2

public val SA_SIGINFO: CInt = 4

public val SA_ONSTACK: CInt = 0x08000000

public val SA_RESTART: CInt = 0x10000000

public val SA_NODEFER: CInt = 0x40000000

public val SIG_BLOCK: CInt = 0

public val SIG_UNBLOCK: CInt = 1

public val SIG_SETMASK: CInt = 2

public val SA_RESETHAND: CInt = u32CastInt(0x80000000u)
