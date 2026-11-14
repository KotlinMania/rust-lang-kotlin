package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.arch.mips

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.RlimT
import io.github.kotlinmania.rustlang.libc.SpeedT
import io.github.kotlinmania.rustlang.libc.TcflagT
import io.github.kotlinmania.rustlang.libc.types.u32CastIoctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.RlimitResourceT

// Ioctl Constants
public val TCGETS: Ioctl = 0x540duL

public val TCSETS: Ioctl = 0x540euL

public val TCSETSW: Ioctl = 0x540fuL

public val TCSETSF: Ioctl = 0x5410uL

public val TCGETA: Ioctl = 0x5401uL

public val TCSETA: Ioctl = 0x5402uL

public val TCSETAW: Ioctl = 0x5403uL

public val TCSETAF: Ioctl = 0x5404uL

public val TCSBRK: Ioctl = 0x5405uL

public val TCXONC: Ioctl = 0x5406uL

public val TCFLSH: Ioctl = 0x5407uL

public val TIOCEXCL: Ioctl = 0x740duL

public val TIOCNXCL: Ioctl = 0x740euL

public val TIOCSCTTY: Ioctl = 0x5480uL

public val TIOCGPGRP: Ioctl = 0x40047477uL

public val TIOCOUTQ: Ioctl = 0x7472uL

public val TIOCSTI: Ioctl = 0x5472uL

public val TIOCGWINSZ: Ioctl = 0x40087468uL

public val TIOCMGET: Ioctl = 0x741duL

public val TIOCMBIS: Ioctl = 0x741buL

public val TIOCMBIC: Ioctl = 0x741cuL

public val TIOCMSET: Ioctl = 0x741auL

public val TIOCGSOFTCAR: Ioctl = 0x5481uL

public val TIOCSSOFTCAR: Ioctl = 0x5482uL

public val FIONREAD: Ioctl = 0x467fuL

public val TIOCINQ: Ioctl = FIONREAD

public val TIOCLINUX: Ioctl = 0x5483uL

public val TIOCGSERIAL: Ioctl = 0x5484uL

public val TIOCSSERIAL: Ioctl = 0x5485uL

public val TIOCPKT: Ioctl = 0x5470uL

public val FIONBIO: Ioctl = 0x667euL

public val TIOCNOTTY: Ioctl = 0x5471uL

public val TIOCSETD: Ioctl = 0x7401uL

public val TIOCGETD: Ioctl = 0x7400uL

public val TCSBRKP: Ioctl = 0x5486uL

public val TIOCSBRK: Ioctl = 0x5427uL

public val TIOCCBRK: Ioctl = 0x5428uL

public val TIOCGSID: Ioctl = 0x7416uL

public val TIOCGPTN: Ioctl = 0x40045430uL

public val TIOCGDEV: Ioctl = 0x40045432uL

public val TIOCVHANGUP: Ioctl = 0x5437uL

public val TIOCGPKT: Ioctl = 0x40045438uL

public val TIOCGPTLCK: Ioctl = 0x40045439uL

public val TIOCGEXCL: Ioctl = 0x40045440uL

public val TIOCGPTPEER: Ioctl = 0x20005441uL

// pub const TIOCGISO7816: Ioctl = 0x40285442;
// pub const TIOCSISO7816: Ioctl = 0xc0285443;
public val FIONCLEX: Ioctl = 0x6602uL

public val FIOCLEX: Ioctl = 0x6601uL

public val FIOASYNC: Ioctl = 0x667duL

public val TIOCSERCONFIG: Ioctl = 0x5488uL

public val TIOCSERGWILD: Ioctl = 0x5489uL

public val TIOCSERSWILD: Ioctl = 0x548auL

public val TIOCGLCKTRMIOS: Ioctl = 0x548buL

public val TIOCSLCKTRMIOS: Ioctl = 0x548cuL

public val TIOCSERGSTRUCT: Ioctl = 0x548duL

public val TIOCSERGETLSR: Ioctl = 0x548euL

public val TIOCSERGETMULTI: Ioctl = 0x548fuL

public val TIOCSERSETMULTI: Ioctl = 0x5490uL

public val TIOCMIWAIT: Ioctl = 0x5491uL

public val TIOCGICOUNT: Ioctl = 0x5492uL

public val FIOQSIZE: Ioctl = 0x667fuL

public val TIOCSLTC: Ioctl = 0x7475uL

public val TIOCGETP: Ioctl = 0x7408uL

public val TIOCSETP: Ioctl = 0x7409uL

public val TIOCSETN: Ioctl = 0x740auL

public val BLKIOMIN: Ioctl = 0x20001278uL

public val BLKIOOPT: Ioctl = 0x20001279uL

public val BLKSSZGET: Ioctl = 0x20001268uL

public val BLKPBSZGET: Ioctl = 0x2000127BuL

// RLIMIT Constants
public val TIOCGRS485: Ioctl = 0x4020542euL

public val TIOCM_LE: CInt = 0x001

public val TIOCM_DTR: CInt = 0x002

public val TIOCM_RTS: CInt = 0x004

public val TIOCM_ST: CInt = 0x010

public val TIOCM_SR: CInt = 0x020

public val TIOCM_CTS: CInt = 0x040

public val TIOCM_CAR: CInt = 0x100

public val TIOCM_CD: CInt = TIOCM_CAR

public val TIOCM_RNG: CInt = 0x200

public val TIOCM_RI: CInt = TIOCM_RNG

public val TIOCM_DSR: CInt = 0x400

public val BOTHER: SpeedT = 4096uL

public val IBSHIFT: TcflagT = 16uL

public val IUCLC: TcflagT = 512uL

public val XCASE: TcflagT = 4uL

public val RLIMIT_CPU: RlimitResourceT = 0u

public val RLIMIT_FSIZE: RlimitResourceT = 1u

public val RLIMIT_DATA: RlimitResourceT = 2u

public val RLIMIT_STACK: RlimitResourceT = 3u

public val RLIMIT_CORE: RlimitResourceT = 4u

public val RLIMIT_NOFILE: RlimitResourceT = 5u

public val RLIMIT_AS: RlimitResourceT = 6u

public val RLIMIT_RSS: RlimitResourceT = 7u

public val RLIMIT_NPROC: RlimitResourceT = 8u

public val RLIMIT_MEMLOCK: RlimitResourceT = 9u

public val RLIMIT_LOCKS: RlimitResourceT = 10u

public val RLIMIT_SIGPENDING: RlimitResourceT = 11u

public val RLIMIT_MSGQUEUE: RlimitResourceT = 12u

public val RLIMIT_NICE: RlimitResourceT = 13u

public val RLIMIT_RTPRIO: RlimitResourceT = 14u

public val RLIMIT_RTTIME: RlimitResourceT = 15u

public val RLIM_NLIMITS: CInt = 16

public val RLIMIT_NLIMITS: RlimitResourceT = RLIM_NLIMITS.toUInt()

public val RLIM_INFINITY: RlimT = 0uL.inv()

public val TIOCSPGRP: Ioctl = u32CastIoctl(0x80047476u)

public val TIOCSWINSZ: Ioctl = u32CastIoctl(0x80087467u)

public val TIOCCONS: Ioctl = u32CastIoctl(0x80047478u)

public val TCGETS2: Ioctl = u32CastIoctl(0x4030542au)

public val TCSETS2: Ioctl = u32CastIoctl(0x8030542bu)

public val TCSETSW2: Ioctl = u32CastIoctl(0x8030542cu)

public val TCSETSF2: Ioctl = u32CastIoctl(0x8030542du)

public val TIOCSPTLCK: Ioctl = u32CastIoctl(0x80045431u)

public val TIOCSIG: Ioctl = u32CastIoctl(0x80045436u)

public val TIOCSRS485: Ioctl = u32CastIoctl(0xc020542fu)
