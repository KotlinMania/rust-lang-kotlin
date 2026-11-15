package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.arch.generic

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.types.u32CastIoctl
import io.github.kotlinmania.rustlang.unix.bsd.apple.SpeedT
import io.github.kotlinmania.rustlang.unix.bsd.apple.TcflagT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.RlimitResourceT
import io.github.kotlinmania.rustlang.unix.linuxlike.linux.gnu.b64.RlimT

// Ioctl Constants
public val TCGETS: Ioctl = 0x5401uL

public val TCSETS: Ioctl = 0x5402uL

public val TCSETSW: Ioctl = 0x5403uL

public val TCSETSF: Ioctl = 0x5404uL

public val TCGETA: Ioctl = 0x5405uL

public val TCSETA: Ioctl = 0x5406uL

public val TCSETAW: Ioctl = 0x5407uL

public val TCSETAF: Ioctl = 0x5408uL

public val TCSBRK: Ioctl = 0x5409uL

public val TCXONC: Ioctl = 0x540AuL

public val TCFLSH: Ioctl = 0x540BuL

public val TIOCEXCL: Ioctl = 0x540CuL

public val TIOCNXCL: Ioctl = 0x540DuL

public val TIOCSCTTY: Ioctl = 0x540EuL

public val TIOCGPGRP: Ioctl = 0x540FuL

public val TIOCSPGRP: Ioctl = 0x5410uL

public val TIOCOUTQ: Ioctl = 0x5411uL

public val TIOCSTI: Ioctl = 0x5412uL

public val TIOCGWINSZ: Ioctl = 0x5413uL

public val TIOCSWINSZ: Ioctl = 0x5414uL

public val TIOCMGET: Ioctl = 0x5415uL

public val TIOCMBIS: Ioctl = 0x5416uL

public val TIOCMBIC: Ioctl = 0x5417uL

public val TIOCMSET: Ioctl = 0x5418uL

public val TIOCGSOFTCAR: Ioctl = 0x5419uL

public val TIOCSSOFTCAR: Ioctl = 0x541AuL

public val FIONREAD: Ioctl = 0x541BuL

public val TIOCINQ: Ioctl = FIONREAD

public val TIOCLINUX: Ioctl = 0x541CuL

public val TIOCCONS: Ioctl = 0x541DuL

public val TIOCGSERIAL: Ioctl = 0x541EuL

public val TIOCSSERIAL: Ioctl = 0x541FuL

public val TIOCPKT: Ioctl = 0x5420uL

public val FIONBIO: Ioctl = 0x5421uL

public val TIOCNOTTY: Ioctl = 0x5422uL

public val TIOCSETD: Ioctl = 0x5423uL

public val TIOCGETD: Ioctl = 0x5424uL

public val TCSBRKP: Ioctl = 0x5425uL

public val TIOCSBRK: Ioctl = 0x5427uL

public val TIOCCBRK: Ioctl = 0x5428uL

public val TIOCGSID: Ioctl = 0x5429uL

public val TCSETS2: Ioctl = 0x402c542buL

public val TCSETSW2: Ioctl = 0x402c542cuL

public val TCSETSF2: Ioctl = 0x402c542duL

public val TIOCGRS485: Ioctl = 0x542EuL

public val TIOCSRS485: Ioctl = 0x542FuL

public val TIOCSPTLCK: Ioctl = 0x40045431uL

public val TCGETX: Ioctl = 0x5432uL

public val TCSETX: Ioctl = 0x5433uL

public val TCSETXF: Ioctl = 0x5434uL

public val TCSETXW: Ioctl = 0x5435uL

public val TIOCSIG: Ioctl = 0x40045436uL

public val TIOCVHANGUP: Ioctl = 0x5437uL

public val TIOCGPTPEER: Ioctl = 0x5441uL

// pub const TIOCGISO7816: Ioctl = 0x80285442;
// pub const TIOCSISO7816: Ioctl = 0xc0285443;
public val FIONCLEX: Ioctl = 0x5450uL

public val FIOCLEX: Ioctl = 0x5451uL

public val FIOASYNC: Ioctl = 0x5452uL

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

public val BLKIOMIN: Ioctl = 0x1278uL

public val BLKIOOPT: Ioctl = 0x1279uL

public val BLKSSZGET: Ioctl = 0x1268uL

public val BLKPBSZGET: Ioctl = 0x127BuL

// RLIMIT Constants
public val FIOQSIZE: Ioctl = 0x545EuL

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

public val BOTHER: SpeedT = 4096uL

public val IBSHIFT: TcflagT = 16uL

public val IUCLC: TcflagT = 512uL

public val XCASE: TcflagT = 4uL

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

public val RLIM_NLIMITS: CInt = 16

public val RLIMIT_NLIMITS: RlimitResourceT = RLIM_NLIMITS.toUInt()

public val RLIM_INFINITY: RlimT = 0uL.inv()

public val TCGETS2: Ioctl = u32CastIoctl(0x802c542au)

public val TIOCGPTN: Ioctl = u32CastIoctl(0x80045430u)

public val TIOCGDEV: Ioctl = u32CastIoctl(0x80045432u)

public val TIOCGPKT: Ioctl = u32CastIoctl(0x80045438u)

public val TIOCGPTLCK: Ioctl = u32CastIoctl(0x80045439u)

public val TIOCGEXCL: Ioctl = u32CastIoctl(0x80045440u)
