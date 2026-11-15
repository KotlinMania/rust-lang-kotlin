// port-lint: source new/linux_uapi/arch/powerpc/asm/socket.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.arch.powerpc.asm.socket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.libc.*

// Header: `arch / powerpc / include / uapi / asm / socket.h`

public val SO_RCVLOWAT: CInt = 16

public val SO_SNDLOWAT: CInt = 17

public val SO_RCVTIMEO_OLD: CInt = 18

public val SO_SNDTIMEO_OLD: CInt = 19

public val SO_PASSCRED: CInt = 20

public val SO_PEERCRED: CInt = 21
