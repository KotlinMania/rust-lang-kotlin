package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlonglong
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.types.u32CastInt
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.io
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.ior
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.iow
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.iowr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.OffT

public val KERN_CORE_USES_PID: CInt = 52

public val KERN_TAINTED: CInt = 53

public val KERN_CADPID: CInt = 54

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val KERN_PIDMAX: CInt = 55

public val KERN_CORE_PATTERN: CInt = 56

public val KERN_PANIC_ON_OOPS: CInt = 57

public val KERN_HPPA_PWRSW: CInt = 58

public val KERN_HPPA_UNALIGNED: CInt = 59

public val KERN_PRINTK_RATELIMIT: CInt = 60

public val KERN_PRINTK_RATELIMIT_BURST: CInt = 61

public val KERN_PTY: CInt = 62

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val KERN_NGROUPS_MAX: CInt = 63

public val KERN_SPARC_SCONS_PWROFF: CInt = 64

public val KERN_HZ_TIMER: CInt = 65

public val KERN_UNKNOWN_NMI_PANIC: CInt = 66

public val KERN_BOOTLOADER_TYPE: CInt = 67

public val KERN_RANDOMIZE: CInt = 68

public val KERN_SETUID_DUMPABLE: CInt = 69

public val KERN_SPIN_RETRY: CInt = 70

public val KERN_ACPI_VIDEO_FLAGS: CInt = 71

public val KERN_IA64_UNALIGNED: CInt = 72

public val KERN_COMPAT_LOG: CInt = 73

public val KERN_MAX_LOCK_DEPTH: CInt = 74

public val KERN_NMI_WATCHDOG: CInt = 75

public val KERN_PANIC_ON_NMI: CInt = 76

public val VM_OVERCOMMIT_MEMORY: CInt = 5

public val VM_PAGE_CLUSTER: CInt = 10

public val VM_DIRTY_BACKGROUND: CInt = 11

public val VM_DIRTY_RATIO: CInt = 12

public val VM_DIRTY_WB_CS: CInt = 13

public val VM_DIRTY_EXPIRE_CS: CInt = 14

public val VM_NR_PDFLUSH_THREADS: CInt = 15

public val VM_OVERCOMMIT_RATIO: CInt = 16

public val VM_PAGEBUF: CInt = 17

public val VM_HUGETLB_PAGES: CInt = 18

public val VM_SWAPPINESS: CInt = 19

public val VM_LOWMEM_RESERVE_RATIO: CInt = 20

public val VM_MIN_FREE_KBYTES: CInt = 21

public val VM_MAX_MAP_COUNT: CInt = 22

public val VM_BLOCK_DUMP: CInt = 24

public val VM_HUGETLB_GROUP: CInt = 25

public val VM_VFS_CACHE_PRESSURE: CInt = 26

public val VM_LEGACY_VA_LAYOUT: CInt = 27

public val VM_SWAP_TOKEN_TIMEOUT: CInt = 28

public val VM_DROP_PAGECACHE: CInt = 29

public val VM_PERCPU_PAGELIST_FRACTION: CInt = 30

public val VM_ZONE_RECLAIM_MODE: CInt = 31

public val VM_MIN_UNMAPPED: CInt = 32

public val VM_PANIC_ON_OOM: CInt = 33

public val VM_VDSO_ENABLED: CInt = 34

public val VM_MIN_SLAB: CInt = 35

public val NET_CORE: CInt = 1

public val NET_ETHER: CInt = 2

public val NET_802: CInt = 3

public val NET_UNIX: CInt = 4

public val NET_IPV4: CInt = 5

public val NET_IPX: CInt = 6

public val NET_ATALK: CInt = 7

public val NET_NETROM: CInt = 8

public val NET_AX25: CInt = 9

public val NET_BRIDGE: CInt = 10

public val NET_ROSE: CInt = 11

public val NET_IPV6: CInt = 12

public val NET_X25: CInt = 13

public val NET_TR: CInt = 14

public val NET_DECNET: CInt = 15

public val NET_ECONET: CInt = 16

public val NET_SCTP: CInt = 17

public val NET_LLC: CInt = 18

public val NET_NETFILTER: CInt = 19

public val NET_DCCP: CInt = 20

public val NET_IRDA: CInt = 412

// include/linux/sched.h

/**
 * I'm a virtual CPU.
 */
public val PF_VCPU: CInt = 0x00000001

/**
 * I am an IDLE thread.
 */
public val PF_IDLE: CInt = 0x00000002

/**
 * Getting shut down.
 */
public val PF_EXITING: CInt = 0x00000004

/**
 * Coredumps should ignore this task.
 */
public val PF_POSTCOREDUMP: CInt = 0x00000008

/**
 * Task is an IO worker.
 */
public val PF_IO_WORKER: CInt = 0x00000010

/**
 * I'm a workqueue worker.
 */
public val PF_WQ_WORKER: CInt = 0x00000020

/**
 * Forked but didn't exec.
 */
public val PF_FORKNOEXEC: CInt = 0x00000040

/**
 * Process policy on mce errors.
 */
public val PF_MCE_PROCESS: CInt = 0x00000080

/**
 * Used super-user privileges.
 */
public val PF_SUPERPRIV: CInt = 0x00000100

/**
 * Dumped core.
 */
public val PF_DUMPCORE: CInt = 0x00000200

/**
 * Killed by a signal.
 */
public val PF_SIGNALED: CInt = 0x00000400

/**
 * Allocating memory to free memory.
 *
 * See `memallocNoreclaimSave()`.
 */
public val PF_MEMALLOC: CInt = 0x00000800

/**
 * `setUser()` noticed that `RLIMIT_NPROC` was exceeded.
 */
public val PF_NPROC_EXCEEDED: CInt = 0x00001000

/**
 * If unset the fpu must be initialized before use.
 */
public val PF_USED_MATH: CInt = 0x00002000

/**
 * Kernel thread cloned from userspace thread.
 */
public val PF_USER_WORKER: CInt = 0x00004000

/**
 * This thread should not be frozen.
 */
public val PF_NOFREEZE: CInt = 0x00008000

/**
 * I am `kswapd`.
 */
public val PF_KSWAPD: CInt = 0x00020000

/**
 * All allocations inherit `GFP_NOFS`.
 *
 * See `memallocNfsSave()`.
 */
public val PF_MEMALLOC_NOFS: CInt = 0x00040000

/**
 * All allocations inherit `GFP_NOIO`.
 *
 * See `memallocNoioSave()`.
 */
public val PF_MEMALLOC_NOIO: CInt = 0x00080000

/**
 * Throttle writes only against the bdi I write to, I am cleaning
 * dirty pages from some other bdi.
 */
public val PF_LOCAL_THROTTLE: CInt = 0x00100000

/**
 * I am a kernel thread.
 */
public val PF_KTHREAD: CInt = 0x00200000

/**
 * Randomize virtual address space.
 */
public val PF_RANDOMIZE: CInt = 0x00400000

/**
 * Userland is not allowed to meddle with `cpusMask`.
 */
public val PF_NO_SETAFFINITY: CInt = 0x04000000

/**
 * Early kill for mce process policy.
 */
public val PF_MCE_EARLY: CInt = 0x08000000

/**
 * Allocations constrained to zones which allow long term pinning.
 *
 * See `memallocPinSave()`.
 */
public val PF_MEMALLOC_PIN: CInt = 0x10000000

/**
 * Plug has ts that needs updating.
 */
public val PF_BLOCK_TS: CInt = 0x20000000

// The used value is the highest possible bit fitting on 32 bits, so directly
// defining it as a signed integer causes the compiler to report an overflow.
// Use instead a private intermediary that assuringly has the correct type and
// cast it where necessary to the wanted final type, which preserves the
// desired information as-is in terms of integer representation.
public val PF_SUSPEND_TASK_UINT: CUint = 0x80000000u

public val CLONE_PIDFD: CInt = 0x1000

public val SCHED_FLAG_RESET_ON_FORK: CInt = 0x01

public val SCHED_FLAG_RECLAIM: CInt = 0x02

public val SCHED_FLAG_DL_OVERRUN: CInt = 0x04

public val SCHED_FLAG_KEEP_POLICY: CInt = 0x08

public val SCHED_FLAG_KEEP_PARAMS: CInt = 0x10

public val SCHED_FLAG_UTIL_CLAMP_MIN: CInt = 0x20

public val SCHED_FLAG_UTIL_CLAMP_MAX: CInt = 0x40

// linux/ifXdp.h
public val XDP_SHARED_UMEM: U16 = (1u.toUInt() shl 0).toUShort()

public val XDP_COPY: U16 = (1u.toUInt() shl 1).toUShort()

public val XDP_ZEROCOPY: U16 = (1u.toUInt() shl 2).toUShort()

public val XDP_USE_NEED_WAKEUP: U16 = (1u.toUInt() shl 3).toUShort()

public val XDP_USE_SG: U16 = (1u.toUInt() shl 4).toUShort()

public val XDP_UMEM_UNALIGNED_CHUNK_FLAG: U32 = 1u shl 0

public val XDP_RING_NEED_WAKEUP: U32 = 1u shl 0

public val XDP_MMAP_OFFSETS: CInt = 1

public val XDP_RX_RING: CInt = 2

public val XDP_TX_RING: CInt = 3

public val XDP_UMEM_REG: CInt = 4

public val XDP_UMEM_FILL_RING: CInt = 5

public val XDP_UMEM_COMPLETION_RING: CInt = 6

public val XDP_STATISTICS: CInt = 7

public val XDP_OPTIONS: CInt = 8

public val XDP_OPTIONS_ZEROCOPY: U32 = 1u shl 0

public val XDP_PGOFF_RX_RING: io.github.kotlinmania.rustlang.libc.OffT = 0L

public val XDP_PGOFF_TX_RING: io.github.kotlinmania.rustlang.libc.OffT = 0x80000000u.toLong()

public val XDP_UMEM_PGOFF_FILL_RING: CUlonglong = 0x100000000uL

public val XDP_UMEM_PGOFF_COMPLETION_RING: CUlonglong = 0x180000000uL

public val XSK_UNALIGNED_BUF_OFFSET_SHIFT: CInt = 48

public val XSK_UNALIGNED_BUF_ADDR_MASK: CUlonglong = (1uL shl XSK_UNALIGNED_BUF_OFFSET_SHIFT) - 1uL

public val XDP_PKT_CONTD: U32 = 1u shl 0

public val XDP_UMEM_TX_SW_CSUM: U32 = 1u shl 1

public val XDP_UMEM_TX_METADATA_LEN: U32 = 1u shl 2

public val XDP_TXMD_FLAGS_TIMESTAMP: U32 = 1u shl 0

public val XDP_TXMD_FLAGS_CHECKSUM: U32 = 1u shl 1

public val XDP_TX_METADATA: U32 = 1u shl 1

public val SOL_XDP: CInt = 283

public val SCHED_FLAG_KEEP_ALL: CInt = SCHED_FLAG_KEEP_POLICY or SCHED_FLAG_KEEP_PARAMS

public val SCHED_FLAG_UTIL_CLAMP: CInt = SCHED_FLAG_UTIL_CLAMP_MIN or SCHED_FLAG_UTIL_CLAMP_MAX

public val SCHED_FLAG_ALL: CInt = SCHED_FLAG_RESET_ON_FORK or SCHED_FLAG_RECLAIM or SCHED_FLAG_DL_OVERRUN or SCHED_FLAG_KEEP_ALL or SCHED_FLAG_UTIL_CLAMP

// ioctlEventpoll: added in Linux 6.9
public val EPOLL_IOC_TYPE: UInt = 0x8Au

// siginfo.h
public val SI_DETHREAD: CInt = -7

public val TRAP_PERF: CInt = 6

public val NS_GET_USERNS: Ioctl = io(NSIO, 0x1u)

public val NS_GET_PARENT: Ioctl = io(NSIO, 0x2u)

public val NS_GET_NSTYPE: Ioctl = io(NSIO, 0x3u)

public val NS_GET_OWNER_UID: Ioctl = io(NSIO, 0x4u)

public val NS_GET_MNTNS_ID: Ioctl = ior<U64>(NSIO, 0x5u)

public val NS_GET_PID_FROM_PIDNS: Ioctl = ior<CInt>(NSIO, 0x6u)

public val NS_GET_TGID_FROM_PIDNS: Ioctl = ior<CInt>(NSIO, 0x7u)

public val NS_GET_PID_IN_PIDNS: Ioctl = ior<CInt>(NSIO, 0x8u)

public val NS_GET_TGID_IN_PIDNS: Ioctl = ior<CInt>(NSIO, 0x9u)

public val NS_MNT_GET_INFO: Ioctl = ior<MntNsInfo>(NSIO, 10u)

public val NS_MNT_GET_NEXT: Ioctl = ior<MntNsInfo>(NSIO, 11u)

public val NS_MNT_GET_PREV: Ioctl = ior<MntNsInfo>(NSIO, 12u)

public val SECCOMP_IOCTL_NOTIF_RECV: Ioctl = iowr<SeccompNotif>(SECCOMP_IOC_MAGIC, 0u)

public val SECCOMP_IOCTL_NOTIF_SEND: Ioctl = iowr<SeccompNotifResp>(SECCOMP_IOC_MAGIC, 1u)

public val SECCOMP_IOCTL_NOTIF_ID_VALID: Ioctl = iow<ULong>(SECCOMP_IOC_MAGIC, 2u)

public val SECCOMP_IOCTL_NOTIF_ADDFD: Ioctl = iow<SeccompNotifAddfd>(SECCOMP_IOC_MAGIC, 3u)

public val SECCOMP_IOCTL_NOTIF_SET_FLAGS: Ioctl = iow<ULong>(SECCOMP_IOC_MAGIC, 4u)

public val NF_VERDICT_QMASK: CInt = u32CastInt(0xffff0000u)

public val PTP_CLOCK_GETCAPS: Ioctl = ior<PtpClockCaps>(PTP_CLK_MAGIC, 1u)

public val PTP_EXTTS_REQUEST: Ioctl = iow<PtpExttsRequest>(PTP_CLK_MAGIC, 2u)

public val PTP_PEROUT_REQUEST: Ioctl = iow<PtpPeroutRequest>(PTP_CLK_MAGIC, 3u)

public val PTP_ENABLE_PPS: Ioctl = iow<CInt>(PTP_CLK_MAGIC, 4u)

public val PTP_SYS_OFFSET: Ioctl = iow<PtpSysOffset>(PTP_CLK_MAGIC, 5u)

public val PTP_PIN_GETFUNC: Ioctl = iowr<PtpPinDesc>(PTP_CLK_MAGIC, 6u)

public val PTP_PIN_SETFUNC: Ioctl = iow<PtpPinDesc>(PTP_CLK_MAGIC, 7u)

public val PTP_SYS_OFFSET_PRECISE: Ioctl = iowr<PtpSysOffsetPrecise>(PTP_CLK_MAGIC, 8u)

public val PTP_SYS_OFFSET_EXTENDED: Ioctl = iowr<PtpSysOffsetExtended>(PTP_CLK_MAGIC, 9u)

public val PTP_CLOCK_GETCAPS2: Ioctl = ior<PtpClockCaps>(PTP_CLK_MAGIC, 10u)

public val PTP_EXTTS_REQUEST2: Ioctl = iow<PtpExttsRequest>(PTP_CLK_MAGIC, 11u)

public val PTP_PEROUT_REQUEST2: Ioctl = iow<PtpPeroutRequest>(PTP_CLK_MAGIC, 12u)

public val PTP_ENABLE_PPS2: Ioctl = iow<CInt>(PTP_CLK_MAGIC, 13u)

public val PTP_SYS_OFFSET2: Ioctl = iow<PtpSysOffset>(PTP_CLK_MAGIC, 14u)

public val PTP_PIN_GETFUNC2: Ioctl = iowr<PtpPinDesc>(PTP_CLK_MAGIC, 15u)

public val PTP_PIN_SETFUNC2: Ioctl = iow<PtpPinDesc>(PTP_CLK_MAGIC, 16u)

public val PTP_SYS_OFFSET_PRECISE2: Ioctl = iowr<PtpSysOffsetPrecise>(PTP_CLK_MAGIC, 17u)

public val PTP_SYS_OFFSET_EXTENDED2: Ioctl = iowr<PtpSysOffsetExtended>(PTP_CLK_MAGIC, 18u)

public val SECBIT_NOROOT_LOCKED: CInt = issecureMask(SECURE_NOROOT_LOCKED)

public val SECBIT_NO_SETUID_FIXUP_LOCKED: CInt = issecureMask(SECURE_NO_SETUID_FIXUP_LOCKED)

public val SECBIT_KEEP_CAPS_LOCKED: CInt = issecureMask(SECURE_KEEP_CAPS_LOCKED)

public val SECBIT_NO_CAP_AMBIENT_RAISE_LOCKED: CInt = issecureMask(SECURE_NO_CAP_AMBIENT_RAISE_LOCKED)

public val SECBIT_EXEC_RESTRICT_FILE_LOCKED: CInt = issecureMask(SECURE_EXEC_RESTRICT_FILE_LOCKED)

public val SECBIT_EXEC_DENY_INTERACTIVE_LOCKED: CInt = issecureMask(SECURE_EXEC_DENY_INTERACTIVE_LOCKED)

public val SECURE_ALL_UNPRIVILEGED: CInt = issecureMask(SECURE_EXEC_RESTRICT_FILE) or issecureMask(SECURE_EXEC_DENY_INTERACTIVE)

public val KEXEC_ARCH_MASK: CInt = u32CastInt(0xffff0000u)

// linux/reboot.h
public val LINUX_REBOOT_MAGIC1: CInt = u32CastInt(0xfee1deadu)

public val LINUX_REBOOT_CMD_HALT: CInt = u32CastInt(0xCDEF0123u)

public val LINUX_REBOOT_CMD_CAD_ON: CInt = u32CastInt(0x89ABCDEFu)

public val LINUX_REBOOT_CMD_RESTART2: CInt = u32CastInt(0xA1B2C3D4u)

public val LINUX_REBOOT_CMD_SW_SUSPEND: CInt = u32CastInt(0xD000FCE2u)

/**
 * This thread called `freezeProcesses()` and should not be frozen.
 */
public val PF_SUSPEND_TASK: CInt = u32CastInt(PF_SUSPEND_TASK_UINT)

public val EPIOCSPARAMS: Ioctl = iow<EpollParams>(EPOLL_IOC_TYPE, 0x01u)

public val EPIOCGPARAMS: Ioctl = ior<EpollParams>(EPOLL_IOC_TYPE, 0x02u)
