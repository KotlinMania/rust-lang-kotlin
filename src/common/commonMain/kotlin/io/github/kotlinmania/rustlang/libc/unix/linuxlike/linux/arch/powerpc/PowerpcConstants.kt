package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.arch.powerpc

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.types.u32CastIoctl
import io.github.kotlinmania.rustlang.unix.bsd.apple.SpeedT
import io.github.kotlinmania.rustlang.unix.bsd.apple.TcflagT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.RlimT

// Ioctl Constants
// RLIMIT Constants
public val TCGETS: Ioctl = 0x403c7413uL

public val TCGETA: Ioctl = 0x40147417uL

public val TCSBRK: Ioctl = 0x2000741DuL

public val TCXONC: Ioctl = 0x2000741EuL

public val TCFLSH: Ioctl = 0x2000741FuL

public val TIOCEXCL: Ioctl = 0x540CuL

public val TIOCNXCL: Ioctl = 0x540DuL

public val TIOCSCTTY: Ioctl = 0x540EuL

public val TIOCGPGRP: Ioctl = 0x40047477uL

public val TIOCOUTQ: Ioctl = 0x40047473uL

public val TIOCSTI: Ioctl = 0x5412uL

public val TIOCGWINSZ: Ioctl = 0x40087468uL

public val TIOCMGET: Ioctl = 0x5415uL

public val TIOCMBIS: Ioctl = 0x5416uL

public val TIOCMBIC: Ioctl = 0x5417uL

public val TIOCMSET: Ioctl = 0x5418uL

public val TIOCGSOFTCAR: Ioctl = 0x5419uL

public val TIOCSSOFTCAR: Ioctl = 0x541AuL

public val FIONREAD: Ioctl = 0x4004667FuL

public val TIOCINQ: Ioctl = FIONREAD

public val TIOCLINUX: Ioctl = 0x541CuL

public val TIOCCONS: Ioctl = 0x541DuL

public val TIOCGSERIAL: Ioctl = 0x541EuL

public val TIOCSSERIAL: Ioctl = 0x541FuL

public val TIOCPKT: Ioctl = 0x5420uL

public val TIOCNOTTY: Ioctl = 0x5422uL

public val TIOCSETD: Ioctl = 0x5423uL

public val TIOCGETD: Ioctl = 0x5424uL

public val TCSBRKP: Ioctl = 0x5425uL

public val TIOCSBRK: Ioctl = 0x5427uL

public val TIOCCBRK: Ioctl = 0x5428uL

public val TIOCGSID: Ioctl = 0x5429uL

public val TIOCGRS485: Ioctl = 0x542euL

public val TIOCSRS485: Ioctl = 0x542fuL

public val TIOCGPTN: Ioctl = 0x40045430uL

public val TIOCGDEV: Ioctl = 0x40045432uL

public val TIOCVHANGUP: Ioctl = 0x5437uL

public val TIOCGPKT: Ioctl = 0x40045438uL

public val TIOCGPTLCK: Ioctl = 0x40045439uL

public val TIOCGEXCL: Ioctl = 0x40045440uL

public val TIOCGPTPEER: Ioctl = 0x20005441uL

// pub const TIOCGISO7816: Ioctl = 0x40285442;
// pub const TIOCSISO7816: Ioctl = 0xc0285443;
public val FIONCLEX: Ioctl = 0x20006602uL

public val FIOCLEX: Ioctl = 0x20006601uL

public val TIOCSERCONFIG: Ioctl = 0x5453uL

public val TIOCSERGWILD: Ioctl = 0x5454uL

public val TIOCSERSWILD: Ioctl = 0x5455uL

public val TIOCGLCKTRMIOS: Ioctl = 0x5456uL

public val TIOCSLCKTRMIOS: Ioctl = 0x5457uL

public val TIOCSERGSTRUCT: Ioctl = 0x5458uL

public val TIOCSERGETLSR: Ioctl = 0x5459uL

public val TIOCSERGETMULTI: Ioctl = 0x545AuL

public val TIOCSERSETMULTI: Ioctl = 0x545BuL

public val TIOCMIWAIT: Ioctl = 0x545CuL

public val TIOCGICOUNT: Ioctl = 0x545DuL

public val BLKIOMIN: Ioctl = 0x20001278uL

public val BLKIOOPT: Ioctl = 0x20001279uL

public val BLKSSZGET: Ioctl = 0x20001268uL

public val BLKPBSZGET: Ioctl = 0x2000127BuL

// pub const FIOQSIZE: Ioctl = 0x40086680;
public val TIOCM_LE: CInt = 0x001

public val TIOCM_DTR: CInt = 0x002

public val TIOCM_RTS: CInt = 0x004

public val TIOCM_ST: CInt = 0x008

public val TIOCM_SR: CInt = 0x010

public val TIOCM_CTS: CInt = 0x020

public val TIOCM_CAR: CInt = 0x040

public val TIOCM_CD: CInt = TIOCM_CAR

public val TIOCM_RNG: CInt = 0x080

public val TIOCM_RI: CInt = TIOCM_RNG

public val TIOCM_DSR: CInt = 0x100

public val BOTHER: SpeedT = 31uL

public val IBSHIFT: TcflagT = 16uL

public val IUCLC: TcflagT = 4096uL

public val XCASE: TcflagT = 16384uL

public val RLIMIT_CPU: RlimitResourceT = 0u

public val RLIMIT_FSIZE: RlimitResourceT = 1u

public val RLIMIT_DATA: RlimitResourceT = 2u

public val RLIMIT_STACK: RlimitResourceT = 3u

public val RLIMIT_CORE: RlimitResourceT = 4u

public val RLIMIT_RSS: RlimitResourceT = 5u

public val RLIMIT_NPROC: RlimitResourceT = 6u

public val RLIMIT_NOFILE: RlimitResourceT = 7u

public val RLIMIT_MEMLOCK: RlimitResourceT = 8u

public val RLIMIT_AS: RlimitResourceT = 9u

public val RLIMIT_LOCKS: RlimitResourceT = 10u

public val RLIMIT_SIGPENDING: RlimitResourceT = 11u

public val RLIMIT_MSGQUEUE: RlimitResourceT = 12u

public val RLIMIT_NICE: RlimitResourceT = 13u

public val RLIMIT_RTPRIO: RlimitResourceT = 14u

public val RLIMIT_RTTIME: RlimitResourceT = 15u

public val RLIM_NLIMITS: RlimitResourceT = 16u

public val RLIMIT_NLIMITS: RlimitResourceT = RLIM_NLIMITS

public val RLIM_INFINITY: RlimT = 0uL.inv()

public val TCSETS: Ioctl = u32CastIoctl(0x803c7414u)

public val TCSETSW: Ioctl = u32CastIoctl(0x803c7415u)

public val TCSETSF: Ioctl = u32CastIoctl(0x803c7416u)

public val TCSETA: Ioctl = u32CastIoctl(0x80147418u)

public val TCSETAW: Ioctl = u32CastIoctl(0x80147419u)

public val TCSETAF: Ioctl = u32CastIoctl(0x8014741Cu)

public val TIOCSPGRP: Ioctl = u32CastIoctl(0x80047476u)

public val TIOCSWINSZ: Ioctl = u32CastIoctl(0x80087467u)

public val FIONBIO: Ioctl = u32CastIoctl(0x8004667eu)

public val TIOCSPTLCK: Ioctl = u32CastIoctl(0x80045431u)

public val TIOCSIG: Ioctl = u32CastIoctl(0x80045436u)

public val FIOASYNC: Ioctl = u32CastIoctl(0x8004667du)
