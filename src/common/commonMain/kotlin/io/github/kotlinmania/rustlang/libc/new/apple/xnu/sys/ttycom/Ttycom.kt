// port-lint: source new/apple/xnu/sys/ttycom.rs
package io.github.kotlinmania.rustlang.libc.new.apple.xnu.sys.ttycom

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.IOC_OUT
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.io
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.ioc
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.ior
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.iow
import io.github.kotlinmania.rustlang.types.ulongCastInt
import io.github.kotlinmania.rustlang.types.ulongCastUint
import io.github.kotlinmania.rustlang.unix.Winsize
import io.github.kotlinmania.rustlang.unix.bsd.apple.Termios
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Timeval

// Header: `sys / ttycom.h`
//
// <https://github.com/apple-oss-distributions/xnu/blob/main/bsd/sys/ttycom.h>

public val TIOCM_LE: CInt = 1

public val TIOCM_DTR: CInt = 2

public val TIOCM_RTS: CInt = 4

public val TIOCM_ST: CInt = 8

public val TIOCM_SR: CInt = 16

public val TIOCM_CTS: CInt = 32

public val TIOCM_CAR: CInt = 64

public val TIOCM_CD: CInt = TIOCM_CAR

public val TIOCM_RNG: CInt = 128

public val TIOCM_RI: CInt = TIOCM_RNG

public val TIOCM_DSR: CInt = 256

public val TIOCPKT_DATA: CInt = 0x00

public val TIOCPKT_FLUSHREAD: CInt = 0x01

public val TIOCPKT_FLUSHWRITE: CInt = 0x02

public val TIOCPKT_STOP: CInt = 0x04

public val TIOCPKT_START: CInt = 0x08

public val TIOCPKT_NOSTOP: CInt = 0x10

public val TIOCPKT_DOSTOP: CInt = 0x20

public val TIOCPKT_IOCTL: CInt = 0x40

public val TIOCREMOTE: CUlong = 0x80047469uL

// Note(1.0): a number of ioctl numbers are `cInt` or `cUint` and should be changed to
// `cUlong` to match the others.
public val TIOCMODG: CUlong = ior<CInt>('t'.code.toULong(), 3uL)

public val TIOCMODS: CUlong = iow<CInt>('t'.code.toULong(), 4uL)

public val TIOCEXCL: CInt = ulongCastInt(io('t'.code.toULong(), 13uL))

public val TIOCNXCL: CInt = ulongCastInt(io('t'.code.toULong(), 14uL))

public val TIOCFLUSH: CUlong = iow<CInt>('t'.code.toULong(), 16uL)

public val TIOCGETA: CUlong = ior<Termios>('t'.code.toULong(), 19uL)

public val TIOCSETA: CUlong = iow<Termios>('t'.code.toULong(), 20uL)

public val TIOCSETAW: CUlong = iow<Termios>('t'.code.toULong(), 21uL)

public val TIOCSETAF: CUlong = iow<Termios>('t'.code.toULong(), 22uL)

public val TIOCGETD: CUlong = ior<CInt>('t'.code.toULong(), 26uL)

public val TIOCSETD: CUlong = iow<CInt>('t'.code.toULong(), 27uL)

public val TIOCIXON: CInt = ulongCastInt(io('t'.code.toULong(), 129uL))

public val TIOCIXOFF: CInt = ulongCastInt(io('t'.code.toULong(), 128uL))

public val TIOCSBRK: CInt = ulongCastInt(io('t'.code.toULong(), 123uL))

public val TIOCCBRK: CInt = ulongCastInt(io('t'.code.toULong(), 122uL))

public val TIOCSDTR: CInt = ulongCastInt(io('t'.code.toULong(), 121uL))

public val TIOCCDTR: CInt = ulongCastInt(io('t'.code.toULong(), 120uL))

public val TIOCGPGRP: CUlong = ior<CInt>('t'.code.toULong(), 119uL)

public val TIOCSPGRP: CUlong = iow<CInt>('t'.code.toULong(), 118uL)

public val TIOCOUTQ: CUlong = ior<CInt>('t'.code.toULong(), 115uL)

public val TIOCSTI: CUlong = iow<CChar>('t'.code.toULong(), 114uL)

public val TIOCNOTTY: CUlong = io('t'.code.toULong(), 113uL)

public val TIOCPKT: CUlong = iow<CInt>('t'.code.toULong(), 112uL)

public val TIOCSTOP: CUint = ulongCastUint(io('t'.code.toULong(), 111uL))

public val TIOCSTART: CUint = ulongCastUint(io('t'.code.toULong(), 110uL))

public val TIOCMSET: CUlong = iow<CInt>('t'.code.toULong(), 109uL)

public val TIOCMBIS: CUlong = iow<CInt>('t'.code.toULong(), 108uL)

public val TIOCMBIC: CUlong = iow<CInt>('t'.code.toULong(), 107uL)

public val TIOCMGET: CUlong = ior<CInt>('t'.code.toULong(), 106uL)

public val TIOCGWINSZ: CUlong = ior<Winsize>('t'.code.toULong(), 104uL)

public val TIOCSWINSZ: CUlong = iow<Winsize>('t'.code.toULong(), 103uL)

public val TIOCUCNTL: CUlong = iow<CInt>('t'.code.toULong(), 102uL)

public val TIOCSTAT: CUint = ulongCastUint(io('t'.code.toULong(), 101uL))

public val TIOCSCONS: CUint = ulongCastUint(io('t'.code.toULong(), 99uL))

public val TIOCCONS: CUlong = iow<CInt>('t'.code.toULong(), 98uL)

public val TIOCSCTTY: CUint = ulongCastUint(io('t'.code.toULong(), 97uL))

public val TIOCEXT: CUlong = iow<CInt>('t'.code.toULong(), 96uL)

public val TIOCSIG: CUint = ulongCastUint(io('t'.code.toULong(), 95uL))

public val TIOCDRAIN: CUint = ulongCastUint(io('t'.code.toULong(), 94uL))

public val TIOCMSDTRWAIT: CUlong = iow<CInt>('t'.code.toULong(), 91uL)

public val TIOCMGDTRWAIT: CUlong = ior<CInt>('t'.code.toULong(), 90uL)

public val TIOCTIMESTAMP: CUlong = ior<Timeval>('t'.code.toULong(), 89uL)

public val TIOCDCDTIMESTAMP: CUlong = ior<Timeval>('t'.code.toULong(), 88uL)

public val TIOCSDRAINWAIT: CUlong = iow<CInt>('t'.code.toULong(), 87uL)

public val TIOCGDRAINWAIT: CUlong = ior<CInt>('t'.code.toULong(), 86uL)

public val TIOCDSIMICROCODE: CUint = ulongCastUint(io('t'.code.toULong(), 85uL))

public val TIOCPTYGRANT: CUint = ulongCastUint(io('t'.code.toULong(), 84uL))

public val TIOCPTYGNAME: CUint = ulongCastUint(ioc(IOC_OUT, 't'.code.toULong(), 83uL, 128uL))

public val TIOCPTYUNLK: CUint = ulongCastUint(io('t'.code.toULong(), 82uL))
