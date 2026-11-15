// port-lint: source new/glibc/sysdeps/unix/linux/sparc/bits/sigaction.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.sparc.bits.sigaction

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.unix.SighandlerT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.SigsetT

// Header: `sysdeps / unix / sysv / linux / sparc / bits / sigaction.h`

public fun interface SigactionSaRestorerCallback {
    public operator fun invoke()
}

public data class Sigaction(
    public var saSigaction: SighandlerT = 0uL,
    public var saMask: SigsetT = SigsetT(),
    public var reserved0: CInt = 0,
    public var saFlags: CInt = 0,
    public var saRestorer: SigactionSaRestorerCallback? = null,
)

public val SA_NOCLDSTOP: CInt = 0x00000008

public val SA_NOCLDWAIT: CInt = 0x100

public val SA_SIGINFO: CInt = 0x200

public val SA_ONSTACK: CInt = 1

public val SA_RESTART: CInt = 0x2

public val SA_NODEFER: CInt = 0x20

public val SA_RESETHAND: CInt = 0x4

public val SIG_BLOCK: CInt = 1

public val SIG_UNBLOCK: CInt = 2

public val SIG_SETMASK: CInt = 4
