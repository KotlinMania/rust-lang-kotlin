// port-lint: source new/glibc/sysdeps/unix/linux/mips/bits/signum_arch.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.mips.bits.signumarch

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.libc.*

// Header: `sysdeps / unix / sysv / linux / mips / bits / signum - arch.h`

public val SIGEMT: CInt = 7

public val SIGPWR: CInt = 19

public val SIGBUS: CInt = 10

public val SIGSYS: CInt = 12

public val SIGURG: CInt = 21

public val SIGSTOP: CInt = 23

public val SIGTSTP: CInt = 24

public val SIGCONT: CInt = 25

public val SIGCHLD: CInt = 18

public val SIGTTIN: CInt = 26

public val SIGTTOU: CInt = 27

public val SIGPOLL: CInt = 22

public val SIGXCPU: CInt = 30

public val SIGVTALRM: CInt = 28

public val SIGPROF: CInt = 29

public val SIGXFSZ: CInt = 31

public val SIGUSR1: CInt = 16

public val SIGUSR2: CInt = 17

public val SIGWINCH: CInt = 20

public val SIGIO: CInt = SIGPOLL
