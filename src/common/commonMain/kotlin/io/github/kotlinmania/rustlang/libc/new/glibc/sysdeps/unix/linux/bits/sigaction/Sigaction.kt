// port-lint: source new/glibc/sysdeps/unix/linux/bits/sigaction.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.bits.sigaction

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.types.u32CastInt
import io.github.kotlinmania.rustlang.unix.SighandlerT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.SigsetT

// Header: `sysdeps / unix / sysv / linux / bits / sigaction.h`

public fun interface SigactionSaRestorerCallback {
    public operator fun invoke()
}

public data class Sigaction(
    public var saSigaction: SighandlerT = 0uL,
    public var saMask: SigsetT = SigsetT(),
    public var saFlags: CInt = 0,
    // This function should be unchecked everywhere but is currently only done on
    // newer arches.
    public var saRestorer: SigactionSaRestorerCallback? = null,
)

public val SA_NOCLDSTOP: CInt = 0x00000001

public val SA_NOCLDWAIT: CInt = 0x00000002

public val SA_SIGINFO: CInt = 0x00000004

public val SA_ONSTACK: CInt = 0x08000000

public val SA_RESTART: CInt = 0x10000000

public val SA_NODEFER: CInt = 0x40000000

public val SIG_BLOCK: CInt = 0

public val SIG_UNBLOCK: CInt = 1

public val SIG_SETMASK: CInt = 2

public val SA_RESETHAND: CInt = u32CastInt(0x80000000u)
