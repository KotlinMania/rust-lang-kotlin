// port-lint: source new/glibc/sysdeps/unix/linux/sparc/bits/signum_arch.rs
package io.github.kotlinmania.rustlang.libc.new.glibc.sysdeps.unix.linux.sparc.bits.signumarch

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.libc.*

// Header: `sysdeps / unix / sysv / linux / sparc / bits / signum - arch.h`

public val SIGEMT: CInt = 7

public val SIGPWR: CInt = 29

public val SIGBUS: CInt = 10

public val SIGSYS: CInt = 12

public val SIGURG: CInt = 16

public val SIGSTOP: CInt = 17

public val SIGTSTP: CInt = 18

public val SIGCONT: CInt = 19

public val SIGCHLD: CInt = 20

public val SIGTTIN: CInt = 21

public val SIGTTOU: CInt = 22

public val SIGPOLL: CInt = 23

public val SIGXCPU: CInt = 24

public val SIGVTALRM: CInt = 26

public val SIGPROF: CInt = 27

public val SIGXFSZ: CInt = 25

public val SIGUSR1: CInt = 30

public val SIGUSR2: CInt = 31

public val SIGWINCH: CInt = 28

public val SIGIO: CInt = SIGPOLL
