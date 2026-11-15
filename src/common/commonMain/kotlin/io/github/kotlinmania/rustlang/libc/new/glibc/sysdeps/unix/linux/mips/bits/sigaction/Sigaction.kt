// port-lint: source new/glibc/sysdeps/unix/linux/mips/bits/sigaction.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.mips.bits.sigaction

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.types.u32CastInt
import io.github.kotlinmania.rustlang.unix.SighandlerT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.SigsetT

// Header: `sysdeps / unix / sysv / linux / mips / bits / sigaction.h`

public fun interface SigactionSaRestorerCallback {
    public operator fun invoke()
}

public data class Sigaction(
    public var saFlags: CInt = 0,
    public var saSigaction: SighandlerT = 0uL,
    public var saMask: SigsetT = SigsetT(),
    public var saRestorer: SigactionSaRestorerCallback? = null,
)

public val SA_NOCLDSTOP: CInt = 0x00000001

public val SA_NOCLDWAIT: CInt = 0x00010000

public val SA_SIGINFO: CInt = 0x00000008

public val SA_ONSTACK: CInt = 0x08000000

public val SA_RESTART: CInt = 0x10000000

public val SA_NODEFER: CInt = 0x40000000

public val SIG_BLOCK: CInt = 0x1

public val SIG_UNBLOCK: CInt = 0x2

public val SIG_SETMASK: CInt = 3

public val SA_RESETHAND: CInt = u32CastInt(0x80000000u)
