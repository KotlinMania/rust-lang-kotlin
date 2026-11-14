@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUchar
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ffi.CUshort
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.IPC_STAT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIZEOF_PTHREAD_COND_T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.NLMSG_MIN_TYPE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.NLMSG_OVERRUN
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.io
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Ioctl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.O_CLOEXEC
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.aarch64.O_NONBLOCK
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_MUTEX_T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.x8664.notx32.SIZEOF_PTHREAD_RWLOCK_T

// linux/cnProc.h
// uapi/linux/magic.h
// Most `* _SUPER_MAGIC` constants are defined at the `linuxLike` level; the
// following are only available on newer Linux versions than the versions
// currently used in CI in some configurations, so we define them here.
// These functions are not available on OpenHarmony
// LFS64 extensions
//
// * musl has 64-bit versions only so aliases the LFS64 symbols to the standard ones
public val PIDTYPE_PID: PidType = 0

public val PIDTYPE_TGID: PidType = PIDTYPE_PID + 1

public val PIDTYPE_PGID: PidType = PIDTYPE_TGID + 1

public val PIDTYPE_SID: PidType = PIDTYPE_PGID + 1

public val PIDTYPE_MAX: PidType = PIDTYPE_SID + 1

public val POSIX_SPAWN_USEVFORK: CShort = 64

public val POSIX_SPAWN_SETSID: CShort = 128

public val F_SEAL_FUTURE_WRITE: CInt = 0x0010

public val F_SEAL_EXEC: CInt = 0x0020

public val IFF_LOWER_UP: CInt = 0x10000

public val IFF_DORMANT: CInt = 0x20000

public val IFF_ECHO: CInt = 0x40000

// linux/fcntl.h
public val AT_EXECVE_CHECK: CInt = 0x10000

public val MAX_HANDLE_SZ: CInt = 128

public val AT_HANDLE_FID: CInt = 0x200

public val AT_HANDLE_MNT_ID_UNIQUE: CInt = 0x001

public val AT_HANDLE_CONNECTABLE: CInt = 0x002

// linux/fs.h
// Flags for preadv2/pwritev2
public val RWF_HIPRI: CInt = 0x00000001

public val RWF_DSYNC: CInt = 0x00000002

public val RWF_SYNC: CInt = 0x00000004

public val RWF_NOWAIT: CInt = 0x00000008

public val RWF_APPEND: CInt = 0x00000010

public val RWF_NOAPPEND: CInt = 0x00000020

public val RWF_ATOMIC: CInt = 0x00000040

public val RWF_DONTCACHE: CInt = 0x00000080

// Since Linux 3.1
public val SEEK_DATA: CInt = 3

public val SEEK_HOLE: CInt = 4

// linux/mempolicy.h
public val MPOL_DEFAULT: CInt = 0

public val MPOL_PREFERRED: CInt = 1

public val MPOL_BIND: CInt = 2

public val MPOL_INTERLEAVE: CInt = 3

public val MPOL_LOCAL: CInt = 4

public val MPOL_F_NUMA_BALANCING: CInt = 1 shl 13

public val MPOL_F_RELATIVE_NODES: CInt = 1 shl 14

public val MPOL_F_STATIC_NODES: CInt = 1 shl 15

// defined in `new / glibc`
public val PTHREAD_MUTEX_INITIALIZER: io.github.kotlinmania.rustlang.libc.PthreadMutexT = io.github.kotlinmania.rustlang.libc.PthreadMutexT(size = UByteArray(SIZEOF_PTHREAD_MUTEX_T) { 0u.toUByte() })

public val PTHREAD_COND_INITIALIZER: io.github.kotlinmania.rustlang.libc.PthreadCondT = io.github.kotlinmania.rustlang.libc.PthreadCondT(size = UByteArray(SIZEOF_PTHREAD_COND_T) { 0u.toUByte() })

public val PTHREAD_RWLOCK_INITIALIZER: io.github.kotlinmania.rustlang.libc.PthreadRwlockT = io.github.kotlinmania.rustlang.libc.PthreadRwlockT(size = UByteArray(SIZEOF_PTHREAD_RWLOCK_T) { 0u.toUByte() })

public val RENAME_NOREPLACE: CUint = 1u

public val RENAME_EXCHANGE: CUint = 2u

public val RENAME_WHITEOUT: CUint = 4u

public val MSG_STAT: CInt = 11 or (IPC_STAT and 0x100)

public val MSG_INFO: CInt = 12

public val MSG_NOERROR: CInt = 4096

public val MSG_EXCEPT: CInt = 8192

public val MSG_ZEROCOPY: CInt = 0x4000000

public val SEM_UNDO: CInt = 0x1000

public val GETPID: CInt = 11

public val GETVAL: CInt = 12

public val GETALL: CInt = 13

public val GETNCNT: CInt = 14

public val GETZCNT: CInt = 15

public val SETVAL: CInt = 16

public val SETALL: CInt = 17

public val SEM_STAT: CInt = 18 or (IPC_STAT and 0x100)

public val SEM_INFO: CInt = 19

public val SEM_STAT_ANY: CInt = 20 or (IPC_STAT and 0x100)

public val QFMT_VFS_OLD: CInt = 1

public val QFMT_VFS_V0: CInt = 2

public val QFMT_VFS_V1: CInt = 4

public val EFD_SEMAPHORE: CInt = 0x1

public val RB_AUTOBOOT: CInt = 0x01234567u.toInt()

public val RB_HALT_SYSTEM: CInt = 0xcdef0123u.toInt()

public val RB_ENABLE_CAD: CInt = 0x89abcdefu.toInt()

public val RB_DISABLE_CAD: CInt = 0x00000000u.toInt()

public val RB_POWER_OFF: CInt = 0x4321fedcu.toInt()

public val RB_SW_SUSPEND: CInt = 0xd000fce2u.toInt()

public val RB_KEXEC: CInt = 0x45584543u.toInt()

public val SYNC_FILE_RANGE_WAIT_BEFORE: CUint = 1u

public val SYNC_FILE_RANGE_WRITE: CUint = 2u

public val SYNC_FILE_RANGE_WAIT_AFTER: CUint = 4u

public val MREMAP_MAYMOVE: CInt = 1

public val MREMAP_FIXED: CInt = 2

public val MREMAP_DONTUNMAP: CInt = 4

// linux/nsfs.h
public val NSIO: CUint = 0xb7u

public val MNT_NS_INFO_SIZE_VER0: Ioctl = 16uL

public val PR_SET_MDWE: CInt = 65

public val PR_GET_MDWE: CInt = 66

public val PR_MDWE_REFUSE_EXEC_GAIN: CUint = 1u shl 0

public val PR_MDWE_NO_INHERIT: CUint = 1u shl 1

public val PR_SET_MEMORY_MERGE: CInt = 67

public val PR_GET_MEMORY_MERGE: CInt = 68

public val GRND_NONBLOCK: CUint = 0x0001u

public val GRND_RANDOM: CUint = 0x0002u

public val GRND_INSECURE: CUint = 0x0004u

// <linux/seccomp.h>
public val SECCOMP_MODE_DISABLED: CUint = 0u

public val SECCOMP_MODE_STRICT: CUint = 1u

public val SECCOMP_MODE_FILTER: CUint = 2u

public val SECCOMP_SET_MODE_STRICT: CUint = 0u

public val SECCOMP_SET_MODE_FILTER: CUint = 1u

public val SECCOMP_GET_ACTION_AVAIL: CUint = 2u

public val SECCOMP_GET_NOTIF_SIZES: CUint = 3u

public val SECCOMP_FILTER_FLAG_TSYNC: CUlong = 1uL shl 0

public val SECCOMP_FILTER_FLAG_LOG: CUlong = 1uL shl 1

public val SECCOMP_FILTER_FLAG_SPEC_ALLOW: CUlong = 1uL shl 2

public val SECCOMP_FILTER_FLAG_NEW_LISTENER: CUlong = 1uL shl 3

public val SECCOMP_FILTER_FLAG_TSYNC_ESRCH: CUlong = 1uL shl 4

public val SECCOMP_FILTER_FLAG_WAIT_KILLABLE_RECV: CUlong = 1uL shl 5

public val SECCOMP_RET_KILL_PROCESS: CUint = 0x80000000u

public val SECCOMP_RET_KILL_THREAD: CUint = 0x00000000u

public val SECCOMP_RET_KILL: CUint = SECCOMP_RET_KILL_THREAD

public val SECCOMP_RET_TRAP: CUint = 0x00030000u

public val SECCOMP_RET_ERRNO: CUint = 0x00050000u

public val SECCOMP_RET_USER_NOTIF: CUint = 0x7fc00000u

public val SECCOMP_RET_TRACE: CUint = 0x7ff00000u

public val SECCOMP_RET_LOG: CUint = 0x7ffc0000u

public val SECCOMP_RET_ALLOW: CUint = 0x7fff0000u

public val SECCOMP_RET_ACTION_FULL: CUint = 0xffff0000u

public val SECCOMP_RET_ACTION: CUint = 0x7fff0000u

public val SECCOMP_RET_DATA: CUint = 0x0000ffffu

public val SECCOMP_USER_NOTIF_FLAG_CONTINUE: CUlong = 1uL

public val SECCOMP_ADDFD_FLAG_SETFD: CUlong = 1uL

public val SECCOMP_ADDFD_FLAG_SEND: CUlong = 2uL

public val SECCOMP_IOC_MAGIC: UInt = 33u

public val TFD_CLOEXEC: CInt = O_CLOEXEC

public val TFD_NONBLOCK: CInt = O_NONBLOCK

public val TFD_TIMER_ABSTIME: CInt = 1

public val TFD_TIMER_CANCEL_ON_SET: CInt = 2

public val FALLOC_FL_KEEP_SIZE: CInt = 0x01

public val FALLOC_FL_PUNCH_HOLE: CInt = 0x02

public val FALLOC_FL_COLLAPSE_RANGE: CInt = 0x08

public val FALLOC_FL_ZERO_RANGE: CInt = 0x10

public val FALLOC_FL_INSERT_RANGE: CInt = 0x20

public val FALLOC_FL_UNSHARE_RANGE: CInt = 0x40

public val SO_ORIGINAL_DST: CInt = 80

public val IP_RECVFRAGSIZE: CInt = 25

public val IPV6_FLOWINFO: CInt = 11

public val IPV6_FLOWLABEL_MGR: CInt = 32

public val IPV6_FLOWINFO_SEND: CInt = 33

public val IPV6_RECVFRAGSIZE: CInt = 77

public val IPV6_FREEBIND: CInt = 78

public val IPV6_FLOWINFO_FLOWLABEL: CInt = 0x000fffff

public val IPV6_FLOWINFO_PRIORITY: CInt = 0x0ff00000

// netinet/in.h
// NOTE: These are in addition to the constants defined in src/unix/mod.rs
public val IPPROTO_MAX: CInt = 263

// SO_MEMINFO offsets
public val SK_MEMINFO_RMEM_ALLOC: CInt = 0

public val SK_MEMINFO_RCVBUF: CInt = 1

public val SK_MEMINFO_WMEM_ALLOC: CInt = 2

public val SK_MEMINFO_SNDBUF: CInt = 3

public val SK_MEMINFO_FWD_ALLOC: CInt = 4

public val SK_MEMINFO_WMEM_QUEUED: CInt = 5

public val SK_MEMINFO_OPTMEM: CInt = 6

public val SK_MEMINFO_BACKLOG: CInt = 7

public val SK_MEMINFO_DROPS: CInt = 8

// linux/closeRange.h
public val CLOSE_RANGE_UNSHARE: CUint = 1u shl 1

public val CLOSE_RANGE_CLOEXEC: CUint = 1u shl 2

// linux/filter.h
public val SKF_AD_OFF: CInt = -0x1000

public val SKF_AD_PROTOCOL: CInt = 0

public val SKF_AD_PKTTYPE: CInt = 4

public val SKF_AD_IFINDEX: CInt = 8

public val SKF_AD_NLATTR: CInt = 12

public val SKF_AD_NLATTR_NEST: CInt = 16

public val SKF_AD_MARK: CInt = 20

public val SKF_AD_QUEUE: CInt = 24

public val SKF_AD_HATYPE: CInt = 28

public val SKF_AD_RXHASH: CInt = 32

public val SKF_AD_CPU: CInt = 36

public val SKF_AD_ALU_XOR_X: CInt = 40

public val SKF_AD_VLAN_TAG: CInt = 44

public val SKF_AD_VLAN_TAG_PRESENT: CInt = 48

public val SKF_AD_PAY_OFFSET: CInt = 52

public val SKF_AD_RANDOM: CInt = 56

public val SKF_AD_VLAN_TPID: CInt = 60

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val SKF_AD_MAX: CInt = 64

public val SKF_NET_OFF: CInt = -0x100000

public val SKF_LL_OFF: CInt = -0x200000

public val BPF_NET_OFF: CInt = SKF_NET_OFF

public val BPF_LL_OFF: CInt = SKF_LL_OFF

public val BPF_MEMWORDS: CInt = 16

public val BPF_MAXINSNS: CInt = 4096

// linux/bpfCommon.h
public val BPF_LD: U32 = 0x00u

public val BPF_LDX: U32 = 0x01u

public val BPF_ST: U32 = 0x02u

public val BPF_STX: U32 = 0x03u

public val BPF_ALU: U32 = 0x04u

public val BPF_JMP: U32 = 0x05u

public val BPF_RET: U32 = 0x06u

public val BPF_MISC: U32 = 0x07u

public val BPF_W: U32 = 0x00u

public val BPF_H: U32 = 0x08u

public val BPF_B: U32 = 0x10u

public val BPF_IMM: U32 = 0x00u

public val BPF_ABS: U32 = 0x20u

public val BPF_IND: U32 = 0x40u

public val BPF_MEM: U32 = 0x60u

public val BPF_LEN: U32 = 0x80u

public val BPF_MSH: U32 = 0xa0u

public val BPF_ADD: U32 = 0x00u

public val BPF_SUB: U32 = 0x10u

public val BPF_MUL: U32 = 0x20u

public val BPF_DIV: U32 = 0x30u

public val BPF_OR: U32 = 0x40u

public val BPF_AND: U32 = 0x50u

public val BPF_LSH: U32 = 0x60u

public val BPF_RSH: U32 = 0x70u

public val BPF_NEG: U32 = 0x80u

public val BPF_MOD: U32 = 0x90u

public val BPF_XOR: U32 = 0xa0u

public val BPF_JA: U32 = 0x00u

public val BPF_JEQ: U32 = 0x10u

public val BPF_JGT: U32 = 0x20u

public val BPF_JGE: U32 = 0x30u

public val BPF_JSET: U32 = 0x40u

public val BPF_K: U32 = 0x00u

public val BPF_X: U32 = 0x08u

// linux/filter.h
public val BPF_A: U32 = 0x10u

public val BPF_TAX: U32 = 0x00u

public val BPF_TXA: U32 = 0x80u

// linux/openat2.h
public val RESOLVE_NO_XDEV: U64 = 0x01uL

public val RESOLVE_NO_MAGICLINKS: U64 = 0x02uL

public val RESOLVE_NO_SYMLINKS: U64 = 0x04uL

public val RESOLVE_BENEATH: U64 = 0x08uL

public val RESOLVE_IN_ROOT: U64 = 0x10uL

public val RESOLVE_CACHED: U64 = 0x20uL

// linux/ifEther.h
public val ETH_ALEN: CInt = 6

public val ETH_HLEN: CInt = 14

public val ETH_ZLEN: CInt = 60

public val ETH_DATA_LEN: CInt = 1500

public val ETH_FRAME_LEN: CInt = 1514

public val ETH_FCS_LEN: CInt = 4

// These are the defined Ethernet Protocol ID's.
public val ETH_P_LOOP: CInt = 0x0060

public val ETH_P_PUP: CInt = 0x0200

public val ETH_P_PUPAT: CInt = 0x0201

public val ETH_P_IP: CInt = 0x0800

public val ETH_P_X25: CInt = 0x0805

public val ETH_P_ARP: CInt = 0x0806

public val ETH_P_BPQ: CInt = 0x08FF

public val ETH_P_IEEEPUP: CInt = 0x0a00

public val ETH_P_IEEEPUPAT: CInt = 0x0a01

public val ETH_P_BATMAN: CInt = 0x4305

public val ETH_P_DEC: CInt = 0x6000

public val ETH_P_DNA_DL: CInt = 0x6001

public val ETH_P_DNA_RC: CInt = 0x6002

public val ETH_P_DNA_RT: CInt = 0x6003

public val ETH_P_LAT: CInt = 0x6004

public val ETH_P_DIAG: CInt = 0x6005

public val ETH_P_CUST: CInt = 0x6006

public val ETH_P_SCA: CInt = 0x6007

public val ETH_P_TEB: CInt = 0x6558

public val ETH_P_RARP: CInt = 0x8035

public val ETH_P_ATALK: CInt = 0x809B

public val ETH_P_AARP: CInt = 0x80F3

public val ETH_P_8021Q: CInt = 0x8100

public val ETH_P_IPX: CInt = 0x8137

public val ETH_P_IPV6: CInt = 0x86DD

public val ETH_P_PAUSE: CInt = 0x8808

public val ETH_P_SLOW: CInt = 0x8809

public val ETH_P_WCCP: CInt = 0x883E

public val ETH_P_MPLS_UC: CInt = 0x8847

public val ETH_P_MPLS_MC: CInt = 0x8848

public val ETH_P_ATMMPOA: CInt = 0x884c

public val ETH_P_PPP_DISC: CInt = 0x8863

public val ETH_P_PPP_SES: CInt = 0x8864

public val ETH_P_LINK_CTL: CInt = 0x886c

public val ETH_P_ATMFATE: CInt = 0x8884

public val ETH_P_PAE: CInt = 0x888E

public val ETH_P_AOE: CInt = 0x88A2

public val ETH_P_8021AD: CInt = 0x88A8

public val ETH_P_802_EX1: CInt = 0x88B5

public val ETH_P_TIPC: CInt = 0x88CA

public val ETH_P_LLDP: CInt = 0x88CC

public val ETH_P_MACSEC: CInt = 0x88E5

public val ETH_P_8021AH: CInt = 0x88E7

public val ETH_P_MVRP: CInt = 0x88F5

public val ETH_P_1588: CInt = 0x88F7

public val ETH_P_PRP: CInt = 0x88FB

public val ETH_P_FCOE: CInt = 0x8906

public val ETH_P_TDLS: CInt = 0x890D

public val ETH_P_FIP: CInt = 0x8914

public val ETH_P_80221: CInt = 0x8917

public val ETH_P_LOOPBACK: CInt = 0x9000

public val ETH_P_QINQ1: CInt = 0x9100

public val ETH_P_QINQ2: CInt = 0x9200

public val ETH_P_QINQ3: CInt = 0x9300

public val ETH_P_EDSA: CInt = 0xDADA

public val ETH_P_AF_IUCV: CInt = 0xFBFB

public val ETH_P_802_3_MIN: CInt = 0x0600

// Non DIX types. Won't clash for 1500 types.
public val ETH_P_802_3: CInt = 0x0001

public val ETH_P_AX25: CInt = 0x0002

public val ETH_P_ALL: CInt = 0x0003

public val ETH_P_802_2: CInt = 0x0004

public val ETH_P_SNAP: CInt = 0x0005

public val ETH_P_DDCMP: CInt = 0x0006

public val ETH_P_WAN_PPP: CInt = 0x0007

public val ETH_P_PPP_MP: CInt = 0x0008

public val ETH_P_LOCALTALK: CInt = 0x0009

public val ETH_P_CANFD: CInt = 0x000D

public val ETH_P_PPPTALK: CInt = 0x0010

public val ETH_P_TR_802_2: CInt = 0x0011

public val ETH_P_MOBITEX: CInt = 0x0015

public val ETH_P_CONTROL: CInt = 0x0016

public val ETH_P_IRDA: CInt = 0x0017

public val ETH_P_ECONET: CInt = 0x0018

public val ETH_P_HDLC: CInt = 0x0019

public val ETH_P_ARCNET: CInt = 0x001A

public val ETH_P_DSA: CInt = 0x001B

public val ETH_P_TRAILER: CInt = 0x001C

public val ETH_P_PHONET: CInt = 0x00F5

public val ETH_P_IEEE802154: CInt = 0x00F6

public val ETH_P_CAIF: CInt = 0x00F7

// DIFF(main): changed to `cShort` in f62eb023ab
public val POSIX_SPAWN_RESETIDS: CInt = 0x01

public val POSIX_SPAWN_SETPGROUP: CInt = 0x02

public val POSIX_SPAWN_SETSIGDEF: CInt = 0x04

public val POSIX_SPAWN_SETSIGMASK: CInt = 0x08

public val POSIX_SPAWN_SETSCHEDPARAM: CInt = 0x10

public val POSIX_SPAWN_SETSCHEDULER: CInt = 0x20

// linux/netfilter/nfnetlink.h
public val NFNLGRP_NONE: CInt = 0

public val NFNLGRP_CONNTRACK_NEW: CInt = 1

public val NFNLGRP_CONNTRACK_UPDATE: CInt = 2

public val NFNLGRP_CONNTRACK_DESTROY: CInt = 3

public val NFNLGRP_CONNTRACK_EXP_NEW: CInt = 4

public val NFNLGRP_CONNTRACK_EXP_UPDATE: CInt = 5

public val NFNLGRP_CONNTRACK_EXP_DESTROY: CInt = 6

public val NFNLGRP_NFTABLES: CInt = 7

public val NFNLGRP_ACCT_QUOTA: CInt = 8

public val NFNLGRP_NFTRACE: CInt = 9

public val NFNETLINK_V0: CInt = 0

public val NFNL_SUBSYS_NONE: CInt = 0

public val NFNL_SUBSYS_CTNETLINK: CInt = 1

public val NFNL_SUBSYS_CTNETLINK_EXP: CInt = 2

public val NFNL_SUBSYS_QUEUE: CInt = 3

public val NFNL_SUBSYS_ULOG: CInt = 4

public val NFNL_SUBSYS_OSF: CInt = 5

public val NFNL_SUBSYS_IPSET: CInt = 6

public val NFNL_SUBSYS_ACCT: CInt = 7

public val NFNL_SUBSYS_CTNETLINK_TIMEOUT: CInt = 8

public val NFNL_SUBSYS_CTHELPER: CInt = 9

public val NFNL_SUBSYS_NFTABLES: CInt = 10

public val NFNL_SUBSYS_NFT_COMPAT: CInt = 11

public val NFNL_SUBSYS_HOOK: CInt = 12

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val NFNL_SUBSYS_COUNT: CInt = 13

public val NFNL_MSG_BATCH_BEGIN: CInt = NLMSG_MIN_TYPE

public val NFNL_MSG_BATCH_END: CInt = NLMSG_MIN_TYPE + 1

public val NFNL_BATCH_UNSPEC: CInt = 0

public val NFNL_BATCH_GENID: CInt = 1

// linux/netfilter/nfnetlinkLog.h
public val NFULNL_MSG_PACKET: CInt = 0

public val NFULNL_MSG_CONFIG: CInt = 1

public val NFULA_VLAN_UNSPEC: CInt = 0

public val NFULA_VLAN_PROTO: CInt = 1

public val NFULA_VLAN_TCI: CInt = 2

public val NFULA_UNSPEC: CInt = 0

public val NFULA_PACKET_HDR: CInt = 1

public val NFULA_MARK: CInt = 2

public val NFULA_TIMESTAMP: CInt = 3

public val NFULA_IFINDEX_INDEV: CInt = 4

public val NFULA_IFINDEX_OUTDEV: CInt = 5

public val NFULA_IFINDEX_PHYSINDEV: CInt = 6

public val NFULA_IFINDEX_PHYSOUTDEV: CInt = 7

public val NFULA_HWADDR: CInt = 8

public val NFULA_PAYLOAD: CInt = 9

public val NFULA_PREFIX: CInt = 10

public val NFULA_UID: CInt = 11

public val NFULA_SEQ: CInt = 12

public val NFULA_SEQ_GLOBAL: CInt = 13

public val NFULA_GID: CInt = 14

public val NFULA_HWTYPE: CInt = 15

public val NFULA_HWHEADER: CInt = 16

public val NFULA_HWLEN: CInt = 17

public val NFULA_CT: CInt = 18

public val NFULA_CT_INFO: CInt = 19

public val NFULA_VLAN: CInt = 20

public val NFULA_L2HDR: CInt = 21

public val NFULNL_CFG_CMD_NONE: CInt = 0

public val NFULNL_CFG_CMD_BIND: CInt = 1

public val NFULNL_CFG_CMD_UNBIND: CInt = 2

public val NFULNL_CFG_CMD_PF_BIND: CInt = 3

public val NFULNL_CFG_CMD_PF_UNBIND: CInt = 4

public val NFULA_CFG_UNSPEC: CInt = 0

public val NFULA_CFG_CMD: CInt = 1

public val NFULA_CFG_MODE: CInt = 2

public val NFULA_CFG_NLBUFSIZ: CInt = 3

public val NFULA_CFG_TIMEOUT: CInt = 4

public val NFULA_CFG_QTHRESH: CInt = 5

public val NFULA_CFG_FLAGS: CInt = 6

public val NFULNL_COPY_NONE: CInt = 0x00

public val NFULNL_COPY_META: CInt = 0x01

public val NFULNL_COPY_PACKET: CInt = 0x02

public val NFULNL_CFG_F_SEQ: CInt = 0x0001

public val NFULNL_CFG_F_SEQ_GLOBAL: CInt = 0x0002

public val NFULNL_CFG_F_CONNTRACK: CInt = 0x0004

// linux/netfilter/nfnetlinkQueue.h
public val NFQNL_MSG_PACKET: CInt = 0

public val NFQNL_MSG_VERDICT: CInt = 1

public val NFQNL_MSG_CONFIG: CInt = 2

public val NFQNL_MSG_VERDICT_BATCH: CInt = 3

public val NFQA_UNSPEC: CInt = 0

public val NFQA_PACKET_HDR: CInt = 1

public val NFQA_VERDICT_HDR: CInt = 2

public val NFQA_MARK: CInt = 3

public val NFQA_TIMESTAMP: CInt = 4

public val NFQA_IFINDEX_INDEV: CInt = 5

public val NFQA_IFINDEX_OUTDEV: CInt = 6

public val NFQA_IFINDEX_PHYSINDEV: CInt = 7

public val NFQA_IFINDEX_PHYSOUTDEV: CInt = 8

public val NFQA_HWADDR: CInt = 9

public val NFQA_PAYLOAD: CInt = 10

public val NFQA_CT: CInt = 11

public val NFQA_CT_INFO: CInt = 12

public val NFQA_CAP_LEN: CInt = 13

public val NFQA_SKB_INFO: CInt = 14

public val NFQA_EXP: CInt = 15

public val NFQA_UID: CInt = 16

public val NFQA_GID: CInt = 17

public val NFQA_SECCTX: CInt = 18

public val NFQA_VLAN: CInt = 19

public val NFQA_L2HDR: CInt = 20

public val NFQA_PRIORITY: CInt = 21

public val NFQA_VLAN_UNSPEC: CInt = 0

public val NFQA_VLAN_PROTO: CInt = 1

public val NFQA_VLAN_TCI: CInt = 2

public val NFQNL_CFG_CMD_NONE: CInt = 0

public val NFQNL_CFG_CMD_BIND: CInt = 1

public val NFQNL_CFG_CMD_UNBIND: CInt = 2

public val NFQNL_CFG_CMD_PF_BIND: CInt = 3

public val NFQNL_CFG_CMD_PF_UNBIND: CInt = 4

public val NFQNL_COPY_NONE: CInt = 0

public val NFQNL_COPY_META: CInt = 1

public val NFQNL_COPY_PACKET: CInt = 2

public val NFQA_CFG_UNSPEC: CInt = 0

public val NFQA_CFG_CMD: CInt = 1

public val NFQA_CFG_PARAMS: CInt = 2

public val NFQA_CFG_QUEUE_MAXLEN: CInt = 3

public val NFQA_CFG_MASK: CInt = 4

public val NFQA_CFG_FLAGS: CInt = 5

public val NFQA_CFG_F_FAIL_OPEN: CInt = 0x0001

public val NFQA_CFG_F_CONNTRACK: CInt = 0x0002

public val NFQA_CFG_F_GSO: CInt = 0x0004

public val NFQA_CFG_F_UID_GID: CInt = 0x0008

public val NFQA_CFG_F_SECCTX: CInt = 0x0010

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val NFQA_CFG_F_MAX: CInt = 0x0020

public val NFQA_SKB_CSUMNOTREADY: CInt = 0x0001

public val NFQA_SKB_GSO: CInt = 0x0002

public val NFQA_SKB_CSUM_NOTVERIFIED: CInt = 0x0004

// linux/genetlink.h
public val GENL_NAMSIZ: CInt = 16

public val GENL_MIN_ID: CInt = NLMSG_MIN_TYPE

public val GENL_MAX_ID: CInt = 1023

public val GENL_ADMIN_PERM: CInt = 0x01

public val GENL_CMD_CAP_DO: CInt = 0x02

public val GENL_CMD_CAP_DUMP: CInt = 0x04

public val GENL_CMD_CAP_HASPOL: CInt = 0x08

public val GENL_ID_CTRL: CInt = NLMSG_MIN_TYPE

public val CTRL_CMD_UNSPEC: CInt = 0

public val CTRL_CMD_NEWFAMILY: CInt = 1

public val CTRL_CMD_DELFAMILY: CInt = 2

public val CTRL_CMD_GETFAMILY: CInt = 3

public val CTRL_CMD_NEWOPS: CInt = 4

public val CTRL_CMD_DELOPS: CInt = 5

public val CTRL_CMD_GETOPS: CInt = 6

public val CTRL_CMD_NEWMCAST_GRP: CInt = 7

public val CTRL_CMD_DELMCAST_GRP: CInt = 8

public val CTRL_CMD_GETMCAST_GRP: CInt = 9

public val CTRL_ATTR_UNSPEC: CInt = 0

public val CTRL_ATTR_FAMILY_ID: CInt = 1

public val CTRL_ATTR_FAMILY_NAME: CInt = 2

public val CTRL_ATTR_VERSION: CInt = 3

public val CTRL_ATTR_HDRSIZE: CInt = 4

public val CTRL_ATTR_MAXATTR: CInt = 5

public val CTRL_ATTR_OPS: CInt = 6

public val CTRL_ATTR_MCAST_GROUPS: CInt = 7

public val CTRL_ATTR_OP_UNSPEC: CInt = 0

public val CTRL_ATTR_OP_ID: CInt = 1

public val CTRL_ATTR_OP_FLAGS: CInt = 2

public val CTRL_ATTR_MCAST_GRP_UNSPEC: CInt = 0

public val CTRL_ATTR_MCAST_GRP_NAME: CInt = 1

public val CTRL_ATTR_MCAST_GRP_ID: CInt = 2

// linux/netfilter.h
public val NF_DROP: CInt = 0

public val NF_ACCEPT: CInt = 1

public val NF_STOLEN: CInt = 2

public val NF_QUEUE: CInt = 3

public val NF_REPEAT: CInt = 4

public val NF_STOP: CInt = 5

public val NF_MAX_VERDICT: CInt = NF_STOP

public val NF_VERDICT_MASK: CInt = 0x000000ff

public val NF_VERDICT_FLAG_QUEUE_BYPASS: CInt = 0x00008000

public val NF_VERDICT_QBITS: CInt = 16

public val NF_VERDICT_BITS: CInt = 16

public val NF_INET_PRE_ROUTING: CInt = 0

public val NF_INET_LOCAL_IN: CInt = 1

public val NF_INET_FORWARD: CInt = 2

public val NF_INET_LOCAL_OUT: CInt = 3

public val NF_INET_POST_ROUTING: CInt = 4

public val NF_INET_NUMHOOKS: CInt = 5

public val NF_INET_INGRESS: CInt = NF_INET_NUMHOOKS

public val NF_NETDEV_INGRESS: CInt = 0

public val NF_NETDEV_EGRESS: CInt = 1

public val NF_NETDEV_NUMHOOKS: CInt = 2

// Some NFPROTO are not compatible with musl and are defined in submodules.
public val NFPROTO_UNSPEC: CInt = 0

public val NFPROTO_INET: CInt = 1

public val NFPROTO_IPV4: CInt = 2

public val NFPROTO_ARP: CInt = 3

public val NFPROTO_NETDEV: CInt = 5

public val NFPROTO_BRIDGE: CInt = 7

public val NFPROTO_IPV6: CInt = 10

public val NFPROTO_DECNET: CInt = 12

public val NFPROTO_NUMPROTO: CInt = 13

// linux/netfilterArp.h
public val NF_ARP: CInt = 0

public val NF_ARP_IN: CInt = 0

public val NF_ARP_OUT: CInt = 1

public val NF_ARP_FORWARD: CInt = 2

public val NF_ARP_NUMHOOKS: CInt = 3

// linux/netfilterBridge.h
public val NF_BR_PRE_ROUTING: CInt = 0

public val NF_BR_LOCAL_IN: CInt = 1

public val NF_BR_FORWARD: CInt = 2

public val NF_BR_LOCAL_OUT: CInt = 3

public val NF_BR_POST_ROUTING: CInt = 4

public val NF_BR_BROUTING: CInt = 5

public val NF_BR_NUMHOOKS: CInt = 6

public val NF_BR_PRI_FIRST: CInt = Int.MIN_VALUE

public val NF_BR_PRI_NAT_DST_BRIDGED: CInt = -300

public val NF_BR_PRI_FILTER_BRIDGED: CInt = -200

public val NF_BR_PRI_BRNF: CInt = 0

public val NF_BR_PRI_NAT_DST_OTHER: CInt = 100

public val NF_BR_PRI_FILTER_OTHER: CInt = 200

public val NF_BR_PRI_NAT_SRC: CInt = 300

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val NF_BR_PRI_LAST: CInt = Int.MAX_VALUE

// linux/netfilterIpv4.h
public val NF_IP_PRE_ROUTING: CInt = 0

public val NF_IP_LOCAL_IN: CInt = 1

public val NF_IP_FORWARD: CInt = 2

public val NF_IP_LOCAL_OUT: CInt = 3

public val NF_IP_POST_ROUTING: CInt = 4

public val NF_IP_NUMHOOKS: CInt = 5

public val NF_IP_PRI_FIRST: CInt = Int.MIN_VALUE

public val NF_IP_PRI_RAW_BEFORE_DEFRAG: CInt = -450

public val NF_IP_PRI_CONNTRACK_DEFRAG: CInt = -400

public val NF_IP_PRI_RAW: CInt = -300

public val NF_IP_PRI_SELINUX_FIRST: CInt = -225

public val NF_IP_PRI_CONNTRACK: CInt = -200

public val NF_IP_PRI_MANGLE: CInt = -150

public val NF_IP_PRI_NAT_DST: CInt = -100

public val NF_IP_PRI_FILTER: CInt = 0

public val NF_IP_PRI_SECURITY: CInt = 50

public val NF_IP_PRI_NAT_SRC: CInt = 100

public val NF_IP_PRI_SELINUX_LAST: CInt = 225

public val NF_IP_PRI_CONNTRACK_HELPER: CInt = 300

public val NF_IP_PRI_CONNTRACK_CONFIRM: CInt = Int.MAX_VALUE

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val NF_IP_PRI_LAST: CInt = Int.MAX_VALUE

// linux/netfilterIpv6.h
public val NF_IP6_PRE_ROUTING: CInt = 0

public val NF_IP6_LOCAL_IN: CInt = 1

public val NF_IP6_FORWARD: CInt = 2

public val NF_IP6_LOCAL_OUT: CInt = 3

public val NF_IP6_POST_ROUTING: CInt = 4

public val NF_IP6_NUMHOOKS: CInt = 5

public val NF_IP6_PRI_FIRST: CInt = Int.MIN_VALUE

public val NF_IP6_PRI_RAW_BEFORE_DEFRAG: CInt = -450

public val NF_IP6_PRI_CONNTRACK_DEFRAG: CInt = -400

public val NF_IP6_PRI_RAW: CInt = -300

public val NF_IP6_PRI_SELINUX_FIRST: CInt = -225

public val NF_IP6_PRI_CONNTRACK: CInt = -200

public val NF_IP6_PRI_MANGLE: CInt = -150

public val NF_IP6_PRI_NAT_DST: CInt = -100

public val NF_IP6_PRI_FILTER: CInt = 0

public val NF_IP6_PRI_SECURITY: CInt = 50

public val NF_IP6_PRI_NAT_SRC: CInt = 100

public val NF_IP6_PRI_SELINUX_LAST: CInt = 225

public val NF_IP6_PRI_CONNTRACK_HELPER: CInt = 300

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val NF_IP6_PRI_LAST: CInt = Int.MAX_VALUE

// linux/netfilterIpv6/ip6Tables.h
public val IP6T_SO_ORIGINAL_DST: CInt = 80

public val SIOCSHWTSTAMP: CUlong = 0x000089b0uL

public val SIOCGHWTSTAMP: CUlong = 0x000089b1uL

// wireless.h
public val WIRELESS_EXT: CUlong = 0x16uL

public val SIOCSIWCOMMIT: CUlong = 0x8B00uL

public val SIOCGIWNAME: CUlong = 0x8B01uL

public val SIOCSIWNWID: CUlong = 0x8B02uL

public val SIOCGIWNWID: CUlong = 0x8B03uL

public val SIOCSIWFREQ: CUlong = 0x8B04uL

public val SIOCGIWFREQ: CUlong = 0x8B05uL

public val SIOCSIWMODE: CUlong = 0x8B06uL

public val SIOCGIWMODE: CUlong = 0x8B07uL

public val SIOCSIWSENS: CUlong = 0x8B08uL

public val SIOCGIWSENS: CUlong = 0x8B09uL

public val SIOCSIWRANGE: CUlong = 0x8B0AuL

public val SIOCGIWRANGE: CUlong = 0x8B0BuL

public val SIOCSIWPRIV: CUlong = 0x8B0CuL

public val SIOCGIWPRIV: CUlong = 0x8B0DuL

public val SIOCSIWSTATS: CUlong = 0x8B0EuL

public val SIOCGIWSTATS: CUlong = 0x8B0FuL

public val SIOCSIWSPY: CUlong = 0x8B10uL

public val SIOCGIWSPY: CUlong = 0x8B11uL

public val SIOCSIWTHRSPY: CUlong = 0x8B12uL

public val SIOCGIWTHRSPY: CUlong = 0x8B13uL

public val SIOCSIWAP: CUlong = 0x8B14uL

public val SIOCGIWAP: CUlong = 0x8B15uL

public val SIOCGIWAPLIST: CUlong = 0x8B17uL

public val SIOCSIWSCAN: CUlong = 0x8B18uL

public val SIOCGIWSCAN: CUlong = 0x8B19uL

public val SIOCSIWESSID: CUlong = 0x8B1AuL

public val SIOCGIWESSID: CUlong = 0x8B1BuL

public val SIOCSIWNICKN: CUlong = 0x8B1CuL

public val SIOCGIWNICKN: CUlong = 0x8B1DuL

public val SIOCSIWRATE: CUlong = 0x8B20uL

public val SIOCGIWRATE: CUlong = 0x8B21uL

public val SIOCSIWRTS: CUlong = 0x8B22uL

public val SIOCGIWRTS: CUlong = 0x8B23uL

public val SIOCSIWFRAG: CUlong = 0x8B24uL

public val SIOCGIWFRAG: CUlong = 0x8B25uL

public val SIOCSIWTXPOW: CUlong = 0x8B26uL

public val SIOCGIWTXPOW: CUlong = 0x8B27uL

public val SIOCSIWRETRY: CUlong = 0x8B28uL

public val SIOCGIWRETRY: CUlong = 0x8B29uL

public val SIOCSIWENCODE: CUlong = 0x8B2AuL

public val SIOCGIWENCODE: CUlong = 0x8B2BuL

public val SIOCSIWPOWER: CUlong = 0x8B2CuL

public val SIOCGIWPOWER: CUlong = 0x8B2DuL

public val SIOCSIWGENIE: CUlong = 0x8B30uL

public val SIOCGIWGENIE: CUlong = 0x8B31uL

public val SIOCSIWMLME: CUlong = 0x8B16uL

public val SIOCSIWAUTH: CUlong = 0x8B32uL

public val SIOCGIWAUTH: CUlong = 0x8B33uL

public val SIOCSIWENCODEEXT: CUlong = 0x8B34uL

public val SIOCGIWENCODEEXT: CUlong = 0x8B35uL

public val SIOCSIWPMKSA: CUlong = 0x8B36uL

public val SIOCIWFIRSTPRIV: CUlong = 0x8BE0uL

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val SIOCIWLASTPRIV: CUlong = 0x8BFFuL

public val SIOCIWFIRST: CUlong = 0x8B00uL

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val SIOCIWLAST: CUlong = SIOCIWLASTPRIV

public val IWEVTXDROP: CUlong = 0x8C00uL

public val IWEVQUAL: CUlong = 0x8C01uL

public val IWEVCUSTOM: CUlong = 0x8C02uL

public val IWEVREGISTERED: CUlong = 0x8C03uL

public val IWEVEXPIRED: CUlong = 0x8C04uL

public val IWEVGENIE: CUlong = 0x8C05uL

public val IWEVMICHAELMICFAILURE: CUlong = 0x8C06uL

public val IWEVASSOCREQIE: CUlong = 0x8C07uL

public val IWEVASSOCRESPIE: CUlong = 0x8C08uL

public val IWEVPMKIDCAND: CUlong = 0x8C09uL

public val IWEVFIRST: CUlong = 0x8C00uL

public val IW_PRIV_TYPE_MASK: CUlong = 0x7000uL

public val IW_PRIV_TYPE_NONE: CUlong = 0x0000uL

public val IW_PRIV_TYPE_BYTE: CUlong = 0x1000uL

public val IW_PRIV_TYPE_CHAR: CUlong = 0x2000uL

public val IW_PRIV_TYPE_INT: CUlong = 0x4000uL

public val IW_PRIV_TYPE_FLOAT: CUlong = 0x5000uL

public val IW_PRIV_TYPE_ADDR: CUlong = 0x6000uL

public val IW_PRIV_SIZE_FIXED: CUlong = 0x0800uL

public val IW_PRIV_SIZE_MASK: CUlong = 0x07FFuL

public val IW_MAX_FREQUENCIES: Int = 32

public val IW_MAX_BITRATES: Int = 32

public val IW_MAX_TXPOWER: Int = 8

public val IW_MAX_SPY: Int = 8

public val IW_MAX_AP: Int = 64

public val IW_ESSID_MAX_SIZE: Int = 32

public val IW_MODE_AUTO: Int = 0

public val IW_MODE_ADHOC: Int = 1

public val IW_MODE_INFRA: Int = 2

public val IW_MODE_MASTER: Int = 3

public val IW_MODE_REPEAT: Int = 4

public val IW_MODE_SECOND: Int = 5

public val IW_MODE_MONITOR: Int = 6

public val IW_MODE_MESH: Int = 7

public val IW_QUAL_QUAL_UPDATED: CUlong = 0x01uL

public val IW_QUAL_LEVEL_UPDATED: CUlong = 0x02uL

public val IW_QUAL_NOISE_UPDATED: CUlong = 0x04uL

public val IW_QUAL_ALL_UPDATED: CUlong = 0x07uL

public val IW_QUAL_DBM: CUlong = 0x08uL

public val IW_QUAL_QUAL_INVALID: CUlong = 0x10uL

public val IW_QUAL_LEVEL_INVALID: CUlong = 0x20uL

public val IW_QUAL_NOISE_INVALID: CUlong = 0x40uL

public val IW_QUAL_RCPI: CUlong = 0x80uL

public val IW_QUAL_ALL_INVALID: CUlong = 0x70uL

public val IW_FREQ_AUTO: CUlong = 0x00uL

public val IW_FREQ_FIXED: CUlong = 0x01uL

public val IW_MAX_ENCODING_SIZES: Int = 8

public val IW_ENCODING_TOKEN_MAX: Int = 64

public val IW_ENCODE_INDEX: CUlong = 0x00FFuL

public val IW_ENCODE_FLAGS: CUlong = 0xFF00uL

public val IW_ENCODE_MODE: CUlong = 0xF000uL

public val IW_ENCODE_DISABLED: CUlong = 0x8000uL

public val IW_ENCODE_ENABLED: CUlong = 0x0000uL

public val IW_ENCODE_RESTRICTED: CUlong = 0x4000uL

public val IW_ENCODE_OPEN: CUlong = 0x2000uL

public val IW_ENCODE_NOKEY: CUlong = 0x0800uL

public val IW_ENCODE_TEMP: CUlong = 0x0400uL

public val IW_POWER_ON: CUlong = 0x0000uL

public val IW_POWER_TYPE: CUlong = 0xF000uL

public val IW_POWER_PERIOD: CUlong = 0x1000uL

public val IW_POWER_TIMEOUT: CUlong = 0x2000uL

public val IW_POWER_MODE: CUlong = 0x0F00uL

public val IW_POWER_UNICAST_R: CUlong = 0x0100uL

public val IW_POWER_MULTICAST_R: CUlong = 0x0200uL

public val IW_POWER_ALL_R: CUlong = 0x0300uL

public val IW_POWER_FORCE_S: CUlong = 0x0400uL

public val IW_POWER_REPEATER: CUlong = 0x0800uL

public val IW_POWER_MODIFIER: CUlong = 0x000FuL

public val IW_POWER_MIN: CUlong = 0x0001uL

public val IW_POWER_MAX: CUlong = 0x0002uL

public val IW_POWER_RELATIVE: CUlong = 0x0004uL

public val IW_TXPOW_TYPE: CUlong = 0x00FFuL

public val IW_TXPOW_DBM: CUlong = 0x0000uL

public val IW_TXPOW_MWATT: CUlong = 0x0001uL

public val IW_TXPOW_RELATIVE: CUlong = 0x0002uL

public val IW_TXPOW_RANGE: CUlong = 0x1000uL

public val IW_RETRY_ON: CUlong = 0x0000uL

public val IW_RETRY_TYPE: CUlong = 0xF000uL

public val IW_RETRY_LIMIT: CUlong = 0x1000uL

public val IW_RETRY_LIFETIME: CUlong = 0x2000uL

public val IW_RETRY_MODIFIER: CUlong = 0x00FFuL

public val IW_RETRY_MIN: CUlong = 0x0001uL

public val IW_RETRY_MAX: CUlong = 0x0002uL

public val IW_RETRY_RELATIVE: CUlong = 0x0004uL

public val IW_RETRY_SHORT: CUlong = 0x0010uL

public val IW_RETRY_LONG: CUlong = 0x0020uL

public val IW_SCAN_DEFAULT: CUlong = 0x0000uL

public val IW_SCAN_ALL_ESSID: CUlong = 0x0001uL

public val IW_SCAN_THIS_ESSID: CUlong = 0x0002uL

public val IW_SCAN_ALL_FREQ: CUlong = 0x0004uL

public val IW_SCAN_THIS_FREQ: CUlong = 0x0008uL

public val IW_SCAN_ALL_MODE: CUlong = 0x0010uL

public val IW_SCAN_THIS_MODE: CUlong = 0x0020uL

public val IW_SCAN_ALL_RATE: CUlong = 0x0040uL

public val IW_SCAN_THIS_RATE: CUlong = 0x0080uL

public val IW_SCAN_TYPE_ACTIVE: Int = 0

public val IW_SCAN_TYPE_PASSIVE: Int = 1

public val IW_SCAN_MAX_DATA: Int = 4096

public val IW_SCAN_CAPA_NONE: CUlong = 0x00uL

public val IW_SCAN_CAPA_ESSID: CUlong = 0x01uL

public val IW_SCAN_CAPA_BSSID: CUlong = 0x02uL

public val IW_SCAN_CAPA_CHANNEL: CUlong = 0x04uL

public val IW_SCAN_CAPA_MODE: CUlong = 0x08uL

public val IW_SCAN_CAPA_RATE: CUlong = 0x10uL

public val IW_SCAN_CAPA_TYPE: CUlong = 0x20uL

public val IW_SCAN_CAPA_TIME: CUlong = 0x40uL

public val IW_CUSTOM_MAX: CUlong = 256uL

public val IW_GENERIC_IE_MAX: CUlong = 1024uL

public val IW_MLME_DEAUTH: CUlong = 0uL

public val IW_MLME_DISASSOC: CUlong = 1uL

public val IW_MLME_AUTH: CUlong = 2uL

public val IW_MLME_ASSOC: CUlong = 3uL

public val IW_AUTH_INDEX: CUlong = 0x0FFFuL

public val IW_AUTH_FLAGS: CUlong = 0xF000uL

public val IW_AUTH_WPA_VERSION: Int = 0

public val IW_AUTH_CIPHER_PAIRWISE: Int = 1

public val IW_AUTH_CIPHER_GROUP: Int = 2

public val IW_AUTH_KEY_MGMT: Int = 3

public val IW_AUTH_TKIP_COUNTERMEASURES: Int = 4

public val IW_AUTH_DROP_UNENCRYPTED: Int = 5

public val IW_AUTH_80211_AUTH_ALG: Int = 6

public val IW_AUTH_WPA_ENABLED: Int = 7

public val IW_AUTH_RX_UNENCRYPTED_EAPOL: Int = 8

public val IW_AUTH_ROAMING_CONTROL: Int = 9

public val IW_AUTH_PRIVACY_INVOKED: Int = 10

public val IW_AUTH_CIPHER_GROUP_MGMT: Int = 11

public val IW_AUTH_MFP: Int = 12

public val IW_AUTH_WPA_VERSION_DISABLED: CUlong = 0x00000001uL

public val IW_AUTH_WPA_VERSION_WPA: CUlong = 0x00000002uL

public val IW_AUTH_WPA_VERSION_WPA2: CUlong = 0x00000004uL

public val IW_AUTH_CIPHER_NONE: CUlong = 0x00000001uL

public val IW_AUTH_CIPHER_WEP40: CUlong = 0x00000002uL

public val IW_AUTH_CIPHER_TKIP: CUlong = 0x00000004uL

public val IW_AUTH_CIPHER_CCMP: CUlong = 0x00000008uL

public val IW_AUTH_CIPHER_WEP104: CUlong = 0x00000010uL

public val IW_AUTH_CIPHER_AES_CMAC: CUlong = 0x00000020uL

public val IW_AUTH_KEY_MGMT_802_1X: Int = 1

public val IW_AUTH_KEY_MGMT_PSK: Int = 2

public val IW_AUTH_ALG_OPEN_SYSTEM: CUlong = 0x00000001uL

public val IW_AUTH_ALG_SHARED_KEY: CUlong = 0x00000002uL

public val IW_AUTH_ALG_LEAP: CUlong = 0x00000004uL

public val IW_AUTH_ROAMING_ENABLE: Int = 0

public val IW_AUTH_ROAMING_DISABLE: Int = 1

public val IW_AUTH_MFP_DISABLED: Int = 0

public val IW_AUTH_MFP_OPTIONAL: Int = 1

public val IW_AUTH_MFP_REQUIRED: Int = 2

public val IW_ENCODE_SEQ_MAX_SIZE: Int = 8

public val IW_ENCODE_ALG_NONE: Int = 0

public val IW_ENCODE_ALG_WEP: Int = 1

public val IW_ENCODE_ALG_TKIP: Int = 2

public val IW_ENCODE_ALG_CCMP: Int = 3

public val IW_ENCODE_ALG_PMK: Int = 4

public val IW_ENCODE_ALG_AES_CMAC: Int = 5

public val IW_ENCODE_EXT_TX_SEQ_VALID: CUlong = 0x00000001uL

public val IW_ENCODE_EXT_RX_SEQ_VALID: CUlong = 0x00000002uL

public val IW_ENCODE_EXT_GROUP_KEY: CUlong = 0x00000004uL

public val IW_ENCODE_EXT_SET_TX_KEY: CUlong = 0x00000008uL

public val IW_MICFAILURE_KEY_ID: CUlong = 0x00000003uL

public val IW_MICFAILURE_GROUP: CUlong = 0x00000004uL

public val IW_MICFAILURE_PAIRWISE: CUlong = 0x00000008uL

public val IW_MICFAILURE_STAKEY: CUlong = 0x00000010uL

public val IW_MICFAILURE_COUNT: CUlong = 0x00000060uL

public val IW_ENC_CAPA_WPA: CUlong = 0x00000001uL

public val IW_ENC_CAPA_WPA2: CUlong = 0x00000002uL

public val IW_ENC_CAPA_CIPHER_TKIP: CUlong = 0x00000004uL

public val IW_ENC_CAPA_CIPHER_CCMP: CUlong = 0x00000008uL

public val IW_ENC_CAPA_4WAY_HANDSHAKE: CUlong = 0x00000010uL

public val IW_EVENT_CAPA_K_0: CUlong = 0x4000050uL

// IW_EVENT_CAPA_MASK(0x8B04) | IW_EVENT_CAPA_MASK(0x8B06) | IW_EVENT_CAPA_MASK(0x8B1A);
public val IW_EVENT_CAPA_K_1: CUlong = 0x400uL

// W_EVENT_CAPA_MASK(0x8B2A);
public val IW_PMKSA_ADD: Int = 1

public val IW_PMKSA_REMOVE: Int = 2

public val IW_PMKSA_FLUSH: Int = 3

public val IW_PMKID_LEN: Int = 16

public val IW_PMKID_CAND_PREAUTH: CUlong = 0x00000001uL

public val IW_EV_LCP_PK_LEN: Int = 4

public val IW_EV_CHAR_PK_LEN: Int = 20

// IW_EV_LCP_PK_LEN + Ifnamsiz;
public val IW_EV_UINT_PK_LEN: Int = 8

// IW_EV_LCP_PK_LEN + sizeOf::<u32>();
public val IW_EV_FREQ_PK_LEN: Int = 12

// IW_EV_LCP_PK_LEN + sizeOf::<iwFreq>();
public val IW_EV_PARAM_PK_LEN: Int = 12

// IW_EV_LCP_PK_LEN + sizeOf::<iwParam>();
public val IW_EV_ADDR_PK_LEN: Int = 20

// IW_EV_LCP_PK_LEN + sizeOf::<sockaddr>();
public val IW_EV_QUAL_PK_LEN: Int = 8

// IW_EV_LCP_PK_LEN + sizeOf::<iwQuality>();
public val IW_EV_POINT_PK_LEN: Int = 8

// IW_EV_LCP_PK_LEN + 4;
// linux/neighbor.h
public val NUD_NONE: UShort = 0x00u

public val NUD_INCOMPLETE: UShort = 0x01u

public val NUD_REACHABLE: UShort = 0x02u

public val NUD_STALE: UShort = 0x04u

public val NUD_DELAY: UShort = 0x08u

public val NUD_PROBE: UShort = 0x10u

public val NUD_FAILED: UShort = 0x20u

public val NUD_NOARP: UShort = 0x40u

public val NUD_PERMANENT: UShort = 0x80u

public val NTF_USE: UByte = 0x01u

public val NTF_SELF: UByte = 0x02u

public val NTF_MASTER: UByte = 0x04u

public val NTF_PROXY: UByte = 0x08u

public val NTF_ROUTER: UByte = 0x80u

public val NDA_UNSPEC: CUshort = 0u

public val NDA_DST: CUshort = 1u

public val NDA_LLADDR: CUshort = 2u

public val NDA_CACHEINFO: CUshort = 3u

public val NDA_PROBES: CUshort = 4u

public val NDA_VLAN: CUshort = 5u

public val NDA_PORT: CUshort = 6u

public val NDA_VNI: CUshort = 7u

public val NDA_IFINDEX: CUshort = 8u

// linux/netlink.h
public val NLM_F_BULK: CInt = 0x200

// linux/rtnetlink.h
public val TCA_UNSPEC: CUshort = 0u

public val TCA_KIND: CUshort = 1u

public val TCA_OPTIONS: CUshort = 2u

public val TCA_STATS: CUshort = 3u

public val TCA_XSTATS: CUshort = 4u

public val TCA_RATE: CUshort = 5u

public val TCA_FCNT: CUshort = 6u

public val TCA_STATS2: CUshort = 7u

public val TCA_STAB: CUshort = 8u

public val RTM_NEWLINK: UShort = 16u

public val RTM_DELLINK: UShort = 17u

public val RTM_GETLINK: UShort = 18u

public val RTM_SETLINK: UShort = 19u

public val RTM_NEWADDR: UShort = 20u

public val RTM_DELADDR: UShort = 21u

public val RTM_GETADDR: UShort = 22u

public val RTM_NEWROUTE: UShort = 24u

public val RTM_DELROUTE: UShort = 25u

public val RTM_GETROUTE: UShort = 26u

public val RTM_NEWNEIGH: UShort = 28u

public val RTM_DELNEIGH: UShort = 29u

public val RTM_GETNEIGH: UShort = 30u

public val RTM_NEWRULE: UShort = 32u

public val RTM_DELRULE: UShort = 33u

public val RTM_GETRULE: UShort = 34u

public val RTM_NEWQDISC: UShort = 36u

public val RTM_DELQDISC: UShort = 37u

public val RTM_GETQDISC: UShort = 38u

public val RTM_NEWTCLASS: UShort = 40u

public val RTM_DELTCLASS: UShort = 41u

public val RTM_GETTCLASS: UShort = 42u

public val RTM_NEWTFILTER: UShort = 44u

public val RTM_DELTFILTER: UShort = 45u

public val RTM_GETTFILTER: UShort = 46u

public val RTM_NEWACTION: UShort = 48u

public val RTM_DELACTION: UShort = 49u

public val RTM_GETACTION: UShort = 50u

public val RTM_NEWPREFIX: UShort = 52u

public val RTM_GETMULTICAST: UShort = 58u

public val RTM_GETANYCAST: UShort = 62u

public val RTM_NEWNEIGHTBL: UShort = 64u

public val RTM_GETNEIGHTBL: UShort = 66u

public val RTM_SETNEIGHTBL: UShort = 67u

public val RTM_NEWNDUSEROPT: UShort = 68u

public val RTM_NEWADDRLABEL: UShort = 72u

public val RTM_DELADDRLABEL: UShort = 73u

public val RTM_GETADDRLABEL: UShort = 74u

public val RTM_GETDCB: UShort = 78u

public val RTM_SETDCB: UShort = 79u

public val RTM_NEWNETCONF: UShort = 80u

public val RTM_GETNETCONF: UShort = 82u

public val RTM_NEWMDB: UShort = 84u

public val RTM_DELMDB: UShort = 85u

public val RTM_GETMDB: UShort = 86u

public val RTM_NEWNSID: UShort = 88u

public val RTM_DELNSID: UShort = 89u

public val RTM_GETNSID: UShort = 90u

public val RTM_F_NOTIFY: CUint = 0x100u

public val RTM_F_CLONED: CUint = 0x200u

public val RTM_F_EQUALIZE: CUint = 0x400u

public val RTM_F_PREFIX: CUint = 0x800u

public val RTA_UNSPEC: CUshort = 0u

public val RTA_DST: CUshort = 1u

public val RTA_SRC: CUshort = 2u

public val RTA_IIF: CUshort = 3u

public val RTA_OIF: CUshort = 4u

public val RTA_GATEWAY: CUshort = 5u

public val RTA_PRIORITY: CUshort = 6u

public val RTA_PREFSRC: CUshort = 7u

public val RTA_METRICS: CUshort = 8u

public val RTA_MULTIPATH: CUshort = 9u

public val RTA_PROTOINFO: CUshort = 10u

// No longer used
public val RTA_FLOW: CUshort = 11u

public val RTA_CACHEINFO: CUshort = 12u

public val RTA_SESSION: CUshort = 13u

// No longer used
public val RTA_MP_ALGO: CUshort = 14u

// No longer used
public val RTA_TABLE: CUshort = 15u

public val RTA_MARK: CUshort = 16u

public val RTA_MFC_STATS: CUshort = 17u

public val RTN_UNSPEC: CUchar = 0u

public val RTN_UNICAST: CUchar = 1u

public val RTN_LOCAL: CUchar = 2u

public val RTN_BROADCAST: CUchar = 3u

public val RTN_ANYCAST: CUchar = 4u

public val RTN_MULTICAST: CUchar = 5u

public val RTN_BLACKHOLE: CUchar = 6u

public val RTN_UNREACHABLE: CUchar = 7u

public val RTN_PROHIBIT: CUchar = 8u

public val RTN_THROW: CUchar = 9u

public val RTN_NAT: CUchar = 10u

public val RTN_XRESOLVE: CUchar = 11u

public val RTPROT_UNSPEC: CUchar = 0u

public val RTPROT_REDIRECT: CUchar = 1u

public val RTPROT_KERNEL: CUchar = 2u

public val RTPROT_BOOT: CUchar = 3u

public val RTPROT_STATIC: CUchar = 4u

public val RT_SCOPE_UNIVERSE: CUchar = 0u

public val RT_SCOPE_SITE: CUchar = 200u

public val RT_SCOPE_LINK: CUchar = 253u

public val RT_SCOPE_HOST: CUchar = 254u

public val RT_SCOPE_NOWHERE: CUchar = 255u

public val RT_TABLE_UNSPEC: CUchar = 0u

public val RT_TABLE_COMPAT: CUchar = 252u

public val RT_TABLE_DEFAULT: CUchar = 253u

public val RT_TABLE_MAIN: CUchar = 254u

public val RT_TABLE_LOCAL: CUchar = 255u

public val RTMSG_OVERRUN: UInt = NLMSG_OVERRUN.toUInt()

public val RTMSG_NEWDEVICE: UInt = 0x11u

public val RTMSG_DELDEVICE: UInt = 0x12u

public val RTMSG_NEWROUTE: UInt = 0x21u

public val RTMSG_DELROUTE: UInt = 0x22u

public val RTMSG_NEWRULE: UInt = 0x31u

public val RTMSG_DELRULE: UInt = 0x32u

public val RTMSG_CONTROL: UInt = 0x40u

public val RTMSG_AR_FAILED: UInt = 0x51u

public val RTEXT_FILTER_VF: CInt = 1 shl 0

public val RTEXT_FILTER_BRVLAN: CInt = 1 shl 1

public val RTEXT_FILTER_BRVLAN_COMPRESSED: CInt = 1 shl 2

public val RTEXT_FILTER_SKIP_STATS: CInt = 1 shl 3

public val RTEXT_FILTER_MRP: CInt = 1 shl 4

public val RTEXT_FILTER_CFM_CONFIG: CInt = 1 shl 5

public val RTEXT_FILTER_CFM_STATUS: CInt = 1 shl 6

// userspace compat definitions for RTNLGRP_*
public val RTMGRP_LINK: CInt = 0x00001

public val RTMGRP_NOTIFY: CInt = 0x00002

public val RTMGRP_NEIGH: CInt = 0x00004

public val RTMGRP_TC: CInt = 0x00008

public val RTMGRP_IPV4_IFADDR: CInt = 0x00010

public val RTMGRP_IPV4_MROUTE: CInt = 0x00020

public val RTMGRP_IPV4_ROUTE: CInt = 0x00040

public val RTMGRP_IPV4_RULE: CInt = 0x00080

public val RTMGRP_IPV6_IFADDR: CInt = 0x00100

public val RTMGRP_IPV6_MROUTE: CInt = 0x00200

public val RTMGRP_IPV6_ROUTE: CInt = 0x00400

public val RTMGRP_IPV6_IFINFO: CInt = 0x00800

public val RTMGRP_DE_CNET_IFADDR: CInt = 0x01000

public val RTMGRP_DE_CNET_ROUTE: CInt = 0x04000

public val RTMGRP_IPV6_PREFIX: CInt = 0x20000

// enum rtnetlinkGroups
public val RTNLGRP_NONE: CUint = 0x00u

public val RTNLGRP_LINK: CUint = 0x01u

public val RTNLGRP_NOTIFY: CUint = 0x02u

public val RTNLGRP_NEIGH: CUint = 0x03u

public val RTNLGRP_TC: CUint = 0x04u

public val RTNLGRP_IPV4_IFADDR: CUint = 0x05u

public val RTNLGRP_IPV4_MROUTE: CUint = 0x06u

public val RTNLGRP_IPV4_ROUTE: CUint = 0x07u

public val RTNLGRP_IPV4_RULE: CUint = 0x08u

public val RTNLGRP_IPV6_IFADDR: CUint = 0x09u

public val RTNLGRP_IPV6_MROUTE: CUint = 0x0au

public val RTNLGRP_IPV6_ROUTE: CUint = 0x0bu

public val RTNLGRP_IPV6_IFINFO: CUint = 0x0cu

public val RTNLGRP_DE_CNET_IFADDR: CUint = 0x0du

public val RTNLGRP_NOP2: CUint = 0x0eu

public val RTNLGRP_DE_CNET_ROUTE: CUint = 0x0fu

public val RTNLGRP_DE_CNET_RULE: CUint = 0x10u

public val RTNLGRP_NOP4: CUint = 0x11u

public val RTNLGRP_IPV6_PREFIX: CUint = 0x12u

public val RTNLGRP_IPV6_RULE: CUint = 0x13u

public val RTNLGRP_ND_USEROPT: CUint = 0x14u

public val RTNLGRP_PHONET_IFADDR: CUint = 0x15u

public val RTNLGRP_PHONET_ROUTE: CUint = 0x16u

public val RTNLGRP_DCB: CUint = 0x17u

public val RTNLGRP_IPV4_NETCONF: CUint = 0x18u

public val RTNLGRP_IPV6_NETCONF: CUint = 0x19u

public val RTNLGRP_MDB: CUint = 0x1au

public val RTNLGRP_MPLS_ROUTE: CUint = 0x1bu

public val RTNLGRP_NSID: CUint = 0x1cu

public val RTNLGRP_MPLS_NETCONF: CUint = 0x1du

public val RTNLGRP_IPV4_MROUTE_R: CUint = 0x1eu

public val RTNLGRP_IPV6_MROUTE_R: CUint = 0x1fu

public val RTNLGRP_NEXTHOP: CUint = 0x20u

public val RTNLGRP_BRVLAN: CUint = 0x21u

public val RTNLGRP_MCTP_IFADDR: CUint = 0x22u

public val RTNLGRP_TUNNEL: CUint = 0x23u

public val RTNLGRP_STATS: CUint = 0x24u

public val PROC_CN_MCAST_LISTEN: ProcCnMcastOp = 1

public val PROC_CN_MCAST_IGNORE: ProcCnMcastOp = 2

public val PROC_EVENT_NONE: ProcCnEvent = 0x00000000

public val PROC_EVENT_FORK: ProcCnEvent = 0x00000001

public val PROC_EVENT_EXEC: ProcCnEvent = 0x00000002

public val PROC_EVENT_UID: ProcCnEvent = 0x00000004

public val PROC_EVENT_GID: ProcCnEvent = 0x00000040

public val PROC_EVENT_SID: ProcCnEvent = 0x00000080

public val PROC_EVENT_PTRACE: ProcCnEvent = 0x00000100

public val PROC_EVENT_COMM: ProcCnEvent = 0x00000200

public val PROC_EVENT_NONZERO_EXIT: ProcCnEvent = 0x20000000

public val PROC_EVENT_COREDUMP: ProcCnEvent = 0x40000000

public val PROC_EVENT_EXIT: ProcCnEvent = 0x80000000.toInt()

// linux/connector.h
public val CN_IDX_PROC: CUint = 0x1u

public val CN_VAL_PROC: CUint = 0x1u

public val CN_IDX_CIFS: CUint = 0x2u

public val CN_VAL_CIFS: CUint = 0x1u

public val CN_W1_IDX: CUint = 0x3u

public val CN_W1_VAL: CUint = 0x1u

public val CN_IDX_V86D: CUint = 0x4u

public val CN_VAL_V86D_UVESAFB: CUint = 0x1u

public val CN_IDX_BB: CUint = 0x5u

public val CN_DST_IDX: CUint = 0x6u

public val CN_DST_VAL: CUint = 0x1u

public val CN_IDX_DM: CUint = 0x7u

public val CN_VAL_DM_USERSPACE_LOG: CUint = 0x1u

public val CN_IDX_DRBD: CUint = 0x8u

public val CN_VAL_DRBD: CUint = 0x1u

public val CN_KVP_IDX: CUint = 0x9u

public val CN_KVP_VAL: CUint = 0x1u

public val CN_VSS_IDX: CUint = 0xAu

public val CN_VSS_VAL: CUint = 0x1u

// linux/module.h
public val MODULE_INIT_IGNORE_MODVERSIONS: CUint = 0x0001u

public val MODULE_INIT_IGNORE_VERMAGIC: CUint = 0x0002u

// linux/netTstamp.h
public val SOF_TIMESTAMPING_TX_HARDWARE: CUint = 1u shl 0

public val SOF_TIMESTAMPING_TX_SOFTWARE: CUint = 1u shl 1

public val SOF_TIMESTAMPING_RX_HARDWARE: CUint = 1u shl 2

public val SOF_TIMESTAMPING_RX_SOFTWARE: CUint = 1u shl 3

public val SOF_TIMESTAMPING_SOFTWARE: CUint = 1u shl 4

public val SOF_TIMESTAMPING_SYS_HARDWARE: CUint = 1u shl 5

public val SOF_TIMESTAMPING_RAW_HARDWARE: CUint = 1u shl 6

public val SOF_TIMESTAMPING_OPT_ID: CUint = 1u shl 7

public val SOF_TIMESTAMPING_TX_SCHED: CUint = 1u shl 8

public val SOF_TIMESTAMPING_TX_ACK: CUint = 1u shl 9

public val SOF_TIMESTAMPING_OPT_CMSG: CUint = 1u shl 10

public val SOF_TIMESTAMPING_OPT_TSONLY: CUint = 1u shl 11

public val SOF_TIMESTAMPING_OPT_STATS: CUint = 1u shl 12

public val SOF_TIMESTAMPING_OPT_PKTINFO: CUint = 1u shl 13

public val SOF_TIMESTAMPING_OPT_TX_SWHW: CUint = 1u shl 14

public val SOF_TIMESTAMPING_BIND_PHC: CUint = 1u shl 15

public val SOF_TIMESTAMPING_OPT_ID_TCP: CUint = 1u shl 16

public val SOF_TIMESTAMPING_OPT_RX_FILTER: CUint = 1u shl 17

public val SOF_TXTIME_DEADLINE_MODE: UInt = 1u shl 0

public val SOF_TXTIME_REPORT_ERRORS: UInt = 1u shl 1

public val HWTSTAMP_TX_OFF: CUint = 0u

public val HWTSTAMP_TX_ON: CUint = 1u

public val HWTSTAMP_TX_ONESTEP_SYNC: CUint = 2u

public val HWTSTAMP_TX_ONESTEP_P2P: CUint = 3u

public val HWTSTAMP_FILTER_NONE: CUint = 0u

public val HWTSTAMP_FILTER_ALL: CUint = 1u

public val HWTSTAMP_FILTER_SOME: CUint = 2u

public val HWTSTAMP_FILTER_PTP_V1_L4_EVENT: CUint = 3u

public val HWTSTAMP_FILTER_PTP_V1_L4_SYNC: CUint = 4u

public val HWTSTAMP_FILTER_PTP_V1_L4_DELAY_REQ: CUint = 5u

public val HWTSTAMP_FILTER_PTP_V2_L4_EVENT: CUint = 6u

public val HWTSTAMP_FILTER_PTP_V2_L4_SYNC: CUint = 7u

public val HWTSTAMP_FILTER_PTP_V2_L4_DELAY_REQ: CUint = 8u

public val HWTSTAMP_FILTER_PTP_V2_L2_EVENT: CUint = 9u

public val HWTSTAMP_FILTER_PTP_V2_L2_SYNC: CUint = 10u

public val HWTSTAMP_FILTER_PTP_V2_L2_DELAY_REQ: CUint = 11u

public val HWTSTAMP_FILTER_PTP_V2_EVENT: CUint = 12u

public val HWTSTAMP_FILTER_PTP_V2_SYNC: CUint = 13u

public val HWTSTAMP_FILTER_PTP_V2_DELAY_REQ: CUint = 14u

public val HWTSTAMP_FILTER_NTP_ALL: CUint = 15u

// linux/ptpClock.h
public val PTP_MAX_SAMPLES: CUint = 25u

// Maximum allowed offset measurement samples.
public val PTP_CLK_MAGIC: UInt = 61u

// enum ptpPinFunction
public val PTP_PF_NONE: CUint = 0u

public val PTP_PF_EXTTS: CUint = 1u

public val PTP_PF_PEROUT: CUint = 2u

public val PTP_PF_PHYSYNC: CUint = 3u

// linux/socket.h
public val SOL_TLS: CInt = 282

// linux/ifAlg.h
public val ALG_SET_KEY: CInt = 1

public val ALG_SET_IV: CInt = 2

public val ALG_SET_OP: CInt = 3

public val ALG_SET_AEAD_ASSOCLEN: CInt = 4

public val ALG_SET_AEAD_AUTHSIZE: CInt = 5

public val ALG_SET_DRBG_ENTROPY: CInt = 6

public val ALG_SET_KEY_BY_KEY_SERIAL: CInt = 7

public val ALG_OP_DECRYPT: CInt = 0

public val ALG_OP_ENCRYPT: CInt = 1

// include/uapi/linux/if.h
public val IF_OPER_UNKNOWN: CInt = 0

public val IF_OPER_NOTPRESENT: CInt = 1

public val IF_OPER_DOWN: CInt = 2

public val IF_OPER_LOWERLAYERDOWN: CInt = 3

public val IF_OPER_TESTING: CInt = 4

public val IF_OPER_DORMANT: CInt = 5

public val IF_OPER_UP: CInt = 6

public val IF_LINK_MODE_DEFAULT: CInt = 0

public val IF_LINK_MODE_DORMANT: CInt = 1

public val IF_LINK_MODE_TESTING: CInt = 2

// include/uapi/linux/mman.h
public val MAP_SHARED_VALIDATE: CInt = 0x3

public val MAP_DROPPABLE: CInt = 0x8

// uapi/linux/vmSockets.h
public val VMADDR_CID_ANY: CUint = 0xFFFFFFFFu

public val VMADDR_CID_HYPERVISOR: CUint = 0u

public val VMADDR_CID_LOCAL: CUint = 1u

public val VMADDR_CID_HOST: CUint = 2u

public val VMADDR_PORT_ANY: CUint = 0xFFFFFFFFu

// uapi/linux/inotify.h
public val IN_ACCESS: UInt = 0x00000001u

public val IN_MODIFY: UInt = 0x00000002u

public val IN_ATTRIB: UInt = 0x00000004u

public val IN_CLOSE_WRITE: UInt = 0x00000008u

public val IN_CLOSE_NOWRITE: UInt = 0x00000010u

public val IN_CLOSE: UInt = IN_CLOSE_WRITE or IN_CLOSE_NOWRITE

public val IN_OPEN: UInt = 0x00000020u

public val IN_MOVED_FROM: UInt = 0x00000040u

public val IN_MOVED_TO: UInt = 0x00000080u

public val IN_MOVE: UInt = IN_MOVED_FROM or IN_MOVED_TO

public val IN_CREATE: UInt = 0x00000100u

public val IN_DELETE: UInt = 0x00000200u

public val IN_DELETE_SELF: UInt = 0x00000400u

public val IN_MOVE_SELF: UInt = 0x00000800u

public val IN_UNMOUNT: UInt = 0x00002000u

public val IN_Q_OVERFLOW: UInt = 0x00004000u

public val IN_IGNORED: UInt = 0x00008000u

public val IN_ONLYDIR: UInt = 0x01000000u

public val IN_DONT_FOLLOW: UInt = 0x02000000u

public val IN_EXCL_UNLINK: UInt = 0x04000000u

public val BINDERFS_SUPER_MAGIC: CLong = 0x6c6f6f70L

public val XFS_SUPER_MAGIC: CLong = 0x58465342L

// uapi/linux/securebits.h
public val SECURE_NOROOT: CInt = 0

public val SECURE_NOROOT_LOCKED: CInt = 1

public val SECURE_NO_SETUID_FIXUP: CInt = 2

public val SECURE_NO_SETUID_FIXUP_LOCKED: CInt = 3

public val SECURE_KEEP_CAPS: CInt = 4

public val SECURE_KEEP_CAPS_LOCKED: CInt = 5

public val SECURE_NO_CAP_AMBIENT_RAISE: CInt = 6

public val SECURE_NO_CAP_AMBIENT_RAISE_LOCKED: CInt = 7

public val SECURE_EXEC_RESTRICT_FILE: CInt = 8

public val SECURE_EXEC_RESTRICT_FILE_LOCKED: CInt = 9

public val SECURE_EXEC_DENY_INTERACTIVE: CInt = 10

public val SECURE_EXEC_DENY_INTERACTIVE_LOCKED: CInt = 11

public val SECUREBITS_DEFAULT: CInt = 0x00000000

public val SECBIT_NOROOT: CInt = issecureMask(SECURE_NOROOT)

public val SECBIT_NO_SETUID_FIXUP: CInt = issecureMask(SECURE_NO_SETUID_FIXUP)

public val SECBIT_KEEP_CAPS: CInt = issecureMask(SECURE_KEEP_CAPS)

public val SECBIT_NO_CAP_AMBIENT_RAISE: CInt = issecureMask(SECURE_NO_CAP_AMBIENT_RAISE)

public val SECBIT_EXEC_RESTRICT_FILE: CInt = issecureMask(SECURE_EXEC_RESTRICT_FILE)

public val SECBIT_EXEC_DENY_INTERACTIVE: CInt = issecureMask(SECURE_EXEC_DENY_INTERACTIVE)

public val SECURE_ALL_BITS: CInt = SECBIT_NOROOT or SECBIT_NO_SETUID_FIXUP or SECBIT_KEEP_CAPS or SECBIT_NO_CAP_AMBIENT_RAISE or SECBIT_EXEC_RESTRICT_FILE or SECBIT_EXEC_DENY_INTERACTIVE

public val SECURE_ALL_LOCKS: CInt = SECURE_ALL_BITS shl 1

public val IN_MASK_CREATE: UInt = 0x10000000u

public val IN_MASK_ADD: UInt = 0x20000000u

public val IN_ISDIR: UInt = 0x40000000u

public val IN_ONESHOT: UInt = 0x80000000u

public val IN_ALL_EVENTS: UInt = IN_ACCESS or IN_MODIFY or IN_ATTRIB or IN_CLOSE_WRITE or IN_CLOSE_NOWRITE or IN_OPEN or IN_MOVED_FROM or IN_MOVED_TO or IN_DELETE or IN_CREATE or IN_DELETE_SELF or IN_MOVE_SELF

public val IN_CLOEXEC: CInt = O_CLOEXEC

public val IN_NONBLOCK: CInt = O_NONBLOCK

// uapi/linux/netfilter/nfTables.h
public val NFT_TABLE_MAXNAMELEN: CInt = 256

public val NFT_CHAIN_MAXNAMELEN: CInt = 256

public val NFT_SET_MAXNAMELEN: CInt = 256

public val NFT_OBJ_MAXNAMELEN: CInt = 256

public val NFT_USERDATA_MAXLEN: CInt = 256

public val NFT_REG_VERDICT: CInt = 0

public val NFT_REG_1: CInt = 1

public val NFT_REG_2: CInt = 2

public val NFT_REG_3: CInt = 3

public val NFT_REG_4: CInt = 4

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val NFT_REG_MAX: CInt = 5

public val NFT_REG32_00: CInt = 8

public val NFT_REG32_01: CInt = 9

public val NFT_REG32_02: CInt = 10

public val NFT_REG32_03: CInt = 11

public val NFT_REG32_04: CInt = 12

public val NFT_REG32_05: CInt = 13

public val NFT_REG32_06: CInt = 14

public val NFT_REG32_07: CInt = 15

public val NFT_REG32_08: CInt = 16

public val NFT_REG32_09: CInt = 17

public val NFT_REG32_10: CInt = 18

public val NFT_REG32_11: CInt = 19

public val NFT_REG32_12: CInt = 20

public val NFT_REG32_13: CInt = 21

public val NFT_REG32_14: CInt = 22

public val NFT_REG32_15: CInt = 23

public val NFT_REG_SIZE: CInt = 16

public val NFT_REG32_SIZE: CInt = 4

public val NFT_CONTINUE: CInt = -1

public val NFT_BREAK: CInt = -2

public val NFT_JUMP: CInt = -3

public val NFT_GOTO: CInt = -4

public val NFT_RETURN: CInt = -5

public val NFT_MSG_NEWTABLE: CInt = 0

public val NFT_MSG_GETTABLE: CInt = 1

public val NFT_MSG_DELTABLE: CInt = 2

public val NFT_MSG_NEWCHAIN: CInt = 3

public val NFT_MSG_GETCHAIN: CInt = 4

public val NFT_MSG_DELCHAIN: CInt = 5

public val NFT_MSG_NEWRULE: CInt = 6

public val NFT_MSG_GETRULE: CInt = 7

public val NFT_MSG_DELRULE: CInt = 8

public val NFT_MSG_NEWSET: CInt = 9

public val NFT_MSG_GETSET: CInt = 10

public val NFT_MSG_DELSET: CInt = 11

public val NFT_MSG_NEWSETELEM: CInt = 12

public val NFT_MSG_GETSETELEM: CInt = 13

public val NFT_MSG_DELSETELEM: CInt = 14

public val NFT_MSG_NEWGEN: CInt = 15

public val NFT_MSG_GETGEN: CInt = 16

public val NFT_MSG_TRACE: CInt = 17

public val NFT_MSG_NEWOBJ: CInt = 18

public val NFT_MSG_GETOBJ: CInt = 19

public val NFT_MSG_DELOBJ: CInt = 20

public val NFT_MSG_GETOBJ_RESET: CInt = 21

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val NFT_MSG_MAX: CInt = 34

public val NFT_SET_ANONYMOUS: CInt = 0x1

public val NFT_SET_CONSTANT: CInt = 0x2

public val NFT_SET_INTERVAL: CInt = 0x4

public val NFT_SET_MAP: CInt = 0x8

public val NFT_SET_TIMEOUT: CInt = 0x10

public val NFT_SET_EVAL: CInt = 0x20

public val NFT_SET_POL_PERFORMANCE: CInt = 0

public val NFT_SET_POL_MEMORY: CInt = 1

public val NFT_SET_ELEM_INTERVAL_END: CInt = 0x1

public val NFT_DATA_VALUE: CUint = 0u

public val NFT_DATA_VERDICT: CUint = 0xffffff00u

public val NFT_DATA_RESERVED_MASK: CUint = 0xffffff00u

public val NFT_DATA_VALUE_MAXLEN: CInt = 64

public val NFT_BYTEORDER_NTOH: CInt = 0

public val NFT_BYTEORDER_HTON: CInt = 1

public val NFT_CMP_EQ: CInt = 0

public val NFT_CMP_NEQ: CInt = 1

public val NFT_CMP_LT: CInt = 2

public val NFT_CMP_LTE: CInt = 3

public val NFT_CMP_GT: CInt = 4

public val NFT_CMP_GTE: CInt = 5

public val NFT_RANGE_EQ: CInt = 0

public val NFT_RANGE_NEQ: CInt = 1

public val NFT_LOOKUP_F_INV: CInt = 1 shl 0

public val NFT_DYNSET_OP_ADD: CInt = 0

public val NFT_DYNSET_OP_UPDATE: CInt = 1

public val NFT_DYNSET_F_INV: CInt = 1 shl 0

public val NFT_PAYLOAD_LL_HEADER: CInt = 0

public val NFT_PAYLOAD_NETWORK_HEADER: CInt = 1

public val NFT_PAYLOAD_TRANSPORT_HEADER: CInt = 2

public val NFT_PAYLOAD_CSUM_NONE: CInt = 0

public val NFT_PAYLOAD_CSUM_INET: CInt = 1

public val NFT_META_LEN: CInt = 0

public val NFT_META_PROTOCOL: CInt = 1

public val NFT_META_PRIORITY: CInt = 2

public val NFT_META_MARK: CInt = 3

public val NFT_META_IIF: CInt = 4

public val NFT_META_OIF: CInt = 5

public val NFT_META_IIFNAME: CInt = 6

public val NFT_META_OIFNAME: CInt = 7

public val NFT_META_IIFTYPE: CInt = 8

public val NFT_META_OIFTYPE: CInt = 9

public val NFT_META_SKUID: CInt = 10

public val NFT_META_SKGID: CInt = 11

public val NFT_META_NFTRACE: CInt = 12

public val NFT_META_RTCLASSID: CInt = 13

public val NFT_META_SECMARK: CInt = 14

public val NFT_META_NFPROTO: CInt = 15

public val NFT_META_L4PROTO: CInt = 16

public val NFT_META_BRI_IIFNAME: CInt = 17

public val NFT_META_BRI_OIFNAME: CInt = 18

public val NFT_META_PKTTYPE: CInt = 19

public val NFT_META_CPU: CInt = 20

public val NFT_META_IIFGROUP: CInt = 21

public val NFT_META_OIFGROUP: CInt = 22

public val NFT_META_CGROUP: CInt = 23

public val NFT_META_PRANDOM: CInt = 24

public val NFT_CT_STATE: CInt = 0

public val NFT_CT_DIRECTION: CInt = 1

public val NFT_CT_STATUS: CInt = 2

public val NFT_CT_MARK: CInt = 3

public val NFT_CT_SECMARK: CInt = 4

public val NFT_CT_EXPIRATION: CInt = 5

public val NFT_CT_HELPER: CInt = 6

public val NFT_CT_L3PROTOCOL: CInt = 7

public val NFT_CT_SRC: CInt = 8

public val NFT_CT_DST: CInt = 9

public val NFT_CT_PROTOCOL: CInt = 10

public val NFT_CT_PROTO_SRC: CInt = 11

public val NFT_CT_PROTO_DST: CInt = 12

public val NFT_CT_LABELS: CInt = 13

public val NFT_CT_PKTS: CInt = 14

public val NFT_CT_BYTES: CInt = 15

public val NFT_CT_AVGPKT: CInt = 16

public val NFT_CT_ZONE: CInt = 17

public val NFT_CT_EVENTMASK: CInt = 18

public val NFT_CT_SRC_IP: CInt = 19

public val NFT_CT_DST_IP: CInt = 20

public val NFT_CT_SRC_IP6: CInt = 21

public val NFT_CT_DST_IP6: CInt = 22

public val NFT_LIMIT_PKTS: CInt = 0

public val NFT_LIMIT_PKT_BYTES: CInt = 1

public val NFT_LIMIT_F_INV: CInt = 1 shl 0

public val NFT_QUEUE_FLAG_BYPASS: CInt = 0x01

public val NFT_QUEUE_FLAG_CPU_FANOUT: CInt = 0x02

public val NFT_QUEUE_FLAG_MASK: CInt = 0x03

public val NFT_QUOTA_F_INV: CInt = 1 shl 0

public val NFT_REJECT_ICMP_UNREACH: CInt = 0

public val NFT_REJECT_TCP_RST: CInt = 1

public val NFT_REJECT_ICMPX_UNREACH: CInt = 2

public val NFT_REJECT_ICMPX_NO_ROUTE: CInt = 0

public val NFT_REJECT_ICMPX_PORT_UNREACH: CInt = 1

public val NFT_REJECT_ICMPX_HOST_UNREACH: CInt = 2

public val NFT_REJECT_ICMPX_ADMIN_PROHIBITED: CInt = 3

public val NFT_NAT_SNAT: CInt = 0

public val NFT_NAT_DNAT: CInt = 1

public val NFT_TRACETYPE_UNSPEC: CInt = 0

public val NFT_TRACETYPE_POLICY: CInt = 1

public val NFT_TRACETYPE_RETURN: CInt = 2

public val NFT_TRACETYPE_RULE: CInt = 3

public val NFT_NG_INCREMENTAL: CInt = 0

public val NFT_NG_RANDOM: CInt = 1

// linux/input.h

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val FF_MAX: U16 = 0x7fu

public val FF_CNT: Int = FF_MAX.toInt() + 1

// linux/input-event-codes.h
public val INPUT_PROP_POINTER: U16 = 0x00u

public val INPUT_PROP_DIRECT: U16 = 0x01u

public val INPUT_PROP_BUTTONPAD: U16 = 0x02u

public val INPUT_PROP_SEMI_MT: U16 = 0x03u

public val INPUT_PROP_TOPBUTTONPAD: U16 = 0x04u

public val INPUT_PROP_POINTING_STICK: U16 = 0x05u

public val INPUT_PROP_ACCELEROMETER: U16 = 0x06u

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val INPUT_PROP_MAX: U16 = 0x1fu

public val INPUT_PROP_CNT: Int = INPUT_PROP_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val EV_MAX: U16 = 0x1fu

public val EV_CNT: Int = EV_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val SYN_MAX: U16 = 0xfu

public val SYN_CNT: Int = SYN_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val KEY_MAX: U16 = 0x2ffu

public val KEY_CNT: Int = KEY_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val REL_MAX: U16 = 0x0fu

public val REL_CNT: Int = REL_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val ABS_MAX: U16 = 0x3fu

public val ABS_CNT: Int = ABS_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val SW_MAX: U16 = 0x11u

public val SW_CNT: Int = SW_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val MSC_MAX: U16 = 0x07u

public val MSC_CNT: Int = MSC_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val LED_MAX: U16 = 0x0fu

public val LED_CNT: Int = LED_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val REP_MAX: U16 = 0x01u

public val REP_CNT: Int = REP_MAX.toInt() + 1

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val SND_MAX: U16 = 0x07u

public val SND_CNT: Int = SND_MAX.toInt() + 1

// linux/uinput.h
public val UINPUT_VERSION: CUint = 5u

public val UINPUT_MAX_NAME_SIZE: Int = 80

// uapi/linux/fanotify.h
public val FAN_ACCESS: ULong = 0x00000001uL

public val FAN_MODIFY: ULong = 0x00000002uL

public val FAN_ATTRIB: ULong = 0x00000004uL

public val FAN_CLOSE_WRITE: ULong = 0x00000008uL

public val FAN_CLOSE_NOWRITE: ULong = 0x00000010uL

public val FAN_OPEN: ULong = 0x00000020uL

public val FAN_MOVED_FROM: ULong = 0x00000040uL

public val FAN_MOVED_TO: ULong = 0x00000080uL

public val FAN_CREATE: ULong = 0x00000100uL

public val FAN_DELETE: ULong = 0x00000200uL

public val FAN_DELETE_SELF: ULong = 0x00000400uL

public val FAN_MOVE_SELF: ULong = 0x00000800uL

public val FAN_OPEN_EXEC: ULong = 0x00001000uL

public val FAN_Q_OVERFLOW: ULong = 0x00004000uL

public val FAN_FS_ERROR: ULong = 0x00008000uL

public val FAN_OPEN_PERM: ULong = 0x00010000uL

public val FAN_ACCESS_PERM: ULong = 0x00020000uL

public val FAN_OPEN_EXEC_PERM: ULong = 0x00040000uL

public val FAN_EVENT_ON_CHILD: ULong = 0x08000000uL

public val FAN_RENAME: ULong = 0x10000000uL

public val FAN_ONDIR: ULong = 0x40000000uL

public val FAN_CLOSE: ULong = FAN_CLOSE_WRITE or FAN_CLOSE_NOWRITE

public val FAN_MOVE: ULong = FAN_MOVED_FROM or FAN_MOVED_TO

public val FAN_CLOEXEC: CUint = 0x00000001u

public val FAN_NONBLOCK: CUint = 0x00000002u

public val FAN_CLASS_NOTIF: CUint = 0x00000000u

public val FAN_CLASS_CONTENT: CUint = 0x00000004u

public val FAN_CLASS_PRE_CONTENT: CUint = 0x00000008u

public val FAN_UNLIMITED_QUEUE: CUint = 0x00000010u

public val FAN_UNLIMITED_MARKS: CUint = 0x00000020u

public val FAN_ENABLE_AUDIT: CUint = 0x00000040u

public val FAN_REPORT_PIDFD: CUint = 0x00000080u

public val FAN_REPORT_TID: CUint = 0x00000100u

public val FAN_REPORT_FID: CUint = 0x00000200u

public val FAN_REPORT_DIR_FID: CUint = 0x00000400u

public val FAN_REPORT_NAME: CUint = 0x00000800u

public val FAN_REPORT_TARGET_FID: CUint = 0x00001000u

public val FAN_REPORT_DFID_NAME: CUint = FAN_REPORT_DIR_FID or FAN_REPORT_NAME

public val FAN_REPORT_DFID_NAME_TARGET: CUint = FAN_REPORT_DFID_NAME or FAN_REPORT_FID or FAN_REPORT_TARGET_FID

public val FAN_MARK_ADD: CUint = 0x00000001u

public val FAN_MARK_REMOVE: CUint = 0x00000002u

public val FAN_MARK_DONT_FOLLOW: CUint = 0x00000004u

public val FAN_MARK_ONLYDIR: CUint = 0x00000008u

public val FAN_MARK_IGNORED_MASK: CUint = 0x00000020u

public val FAN_MARK_IGNORED_SURV_MODIFY: CUint = 0x00000040u

public val FAN_MARK_FLUSH: CUint = 0x00000080u

public val FAN_MARK_EVICTABLE: CUint = 0x00000200u

public val FAN_MARK_IGNORE: CUint = 0x00000400u

public val FAN_MARK_INODE: CUint = 0x00000000u

public val FAN_MARK_MOUNT: CUint = 0x00000010u

public val FAN_MARK_FILESYSTEM: CUint = 0x00000100u

public val FAN_MARK_IGNORE_SURV: CUint = FAN_MARK_IGNORE or FAN_MARK_IGNORED_SURV_MODIFY

public val FANOTIFY_METADATA_VERSION: UByte = 3u

public val FAN_EVENT_INFO_TYPE_FID: UByte = 1u

public val FAN_EVENT_INFO_TYPE_DFID_NAME: UByte = 2u

public val FAN_EVENT_INFO_TYPE_DFID: UByte = 3u

public val FAN_EVENT_INFO_TYPE_PIDFD: UByte = 4u

public val FAN_EVENT_INFO_TYPE_ERROR: UByte = 5u

public val FAN_EVENT_INFO_TYPE_OLD_DFID_NAME: UByte = 10u

public val FAN_EVENT_INFO_TYPE_NEW_DFID_NAME: UByte = 12u

public val FAN_RESPONSE_INFO_NONE: UByte = 0u

public val FAN_RESPONSE_INFO_AUDIT_RULE: UByte = 1u

public val FAN_ALLOW: UInt = 0x01u

public val FAN_DENY: UInt = 0x02u

public val FAN_AUDIT: UInt = 0x10u

public val FAN_INFO: UInt = 0x20u

public val FAN_NOFD: CInt = -1

public val FAN_NOPIDFD: CInt = FAN_NOFD

public val FAN_EPIDFD: CInt = -2

// linux/kexec.h
public val KEXEC_ON_CRASH: CInt = 0x00000001

public val KEXEC_PRESERVE_CONTEXT: CInt = 0x00000002

public val KEXEC_FILE_UNLOAD: CInt = 0x00000001

public val KEXEC_FILE_ON_CRASH: CInt = 0x00000002

public val KEXEC_FILE_NO_INITRAMFS: CInt = 0x00000004

public val LINUX_REBOOT_MAGIC2: CInt = 672274793

public val LINUX_REBOOT_MAGIC2A: CInt = 85072278

public val LINUX_REBOOT_MAGIC2B: CInt = 369367448

public val LINUX_REBOOT_MAGIC2C: CInt = 537993216

public val LINUX_REBOOT_CMD_RESTART: CInt = 0x01234567

public val LINUX_REBOOT_CMD_CAD_OFF: CInt = 0x00000000

public val LINUX_REBOOT_CMD_POWER_OFF: CInt = 0x4321FEDC

public val LINUX_REBOOT_CMD_KEXEC: CInt = 0x45584543

// linux/errqueue.h
public val SO_EE_ORIGIN_NONE: UByte = 0u

public val SO_EE_ORIGIN_LOCAL: UByte = 1u

public val SO_EE_ORIGIN_ICMP: UByte = 2u

public val SO_EE_ORIGIN_ICMP6: UByte = 3u

public val SO_EE_ORIGIN_TXSTATUS: UByte = 4u

public val SO_EE_ORIGIN_TIMESTAMPING: UByte = SO_EE_ORIGIN_TXSTATUS

// DCCP socket options
public val DCCP_SOCKOPT_PACKET_SIZE: CInt = 1

public val DCCP_SOCKOPT_SERVICE: CInt = 2

public val DCCP_SOCKOPT_CHANGE_L: CInt = 3

public val DCCP_SOCKOPT_CHANGE_R: CInt = 4

public val DCCP_SOCKOPT_GET_CUR_MPS: CInt = 5

public val DCCP_SOCKOPT_SERVER_TIMEWAIT: CInt = 6

public val DCCP_SOCKOPT_SEND_CSCOV: CInt = 10

public val DCCP_SOCKOPT_RECV_CSCOV: CInt = 11

public val DCCP_SOCKOPT_AVAILABLE_CCIDS: CInt = 12

public val DCCP_SOCKOPT_CCID: CInt = 13

public val DCCP_SOCKOPT_TX_CCID: CInt = 14

public val DCCP_SOCKOPT_RX_CCID: CInt = 15

public val DCCP_SOCKOPT_QPOLICY_ID: CInt = 16

public val DCCP_SOCKOPT_QPOLICY_TXQLEN: CInt = 17

public val DCCP_SOCKOPT_CCID_RX_INFO: CInt = 128

public val DCCP_SOCKOPT_CCID_TX_INFO: CInt = 192

/**
 * maximum number of services provided on the same listening port
 */
public val DCCP_SERVICE_LIST_MAX_LEN: CInt = 32

public val CTL_KERN: CInt = 1

public val CTL_VM: CInt = 2

public val CTL_NET: CInt = 3

public val CTL_FS: CInt = 5

public val CTL_DEBUG: CInt = 6

public val CTL_DEV: CInt = 7

public val CTL_BUS: CInt = 8

public val CTL_ABI: CInt = 9

public val CTL_CPU: CInt = 10

public val CTL_BUS_ISA: CInt = 1

public val INOTIFY_MAX_USER_INSTANCES: CInt = 1

public val INOTIFY_MAX_USER_WATCHES: CInt = 2

public val INOTIFY_MAX_QUEUED_EVENTS: CInt = 3

public val KERN_OSTYPE: CInt = 1

public val KERN_OSRELEASE: CInt = 2

public val KERN_OSREV: CInt = 3

public val KERN_VERSION: CInt = 4

public val KERN_SECUREMASK: CInt = 5

public val KERN_PROF: CInt = 6

public val KERN_NODENAME: CInt = 7

public val KERN_DOMAINNAME: CInt = 8

public val KERN_PANIC: CInt = 15

public val KERN_SPARC_REBOOT: CInt = 21

public val KERN_CTLALTDEL: CInt = 22

public val KERN_PRINTK: CInt = 23

public val KERN_NAMETRANS: CInt = 24

public val KERN_PPC_HTABRECLAIM: CInt = 25

public val KERN_PPC_ZEROPAGED: CInt = 26

public val KERN_PPC_POWERSAVE_NAP: CInt = 27

public val KERN_MODPROBE: CInt = 28

public val KERN_SG_BIG_BUFF: CInt = 29

public val KERN_ACCT: CInt = 30

public val KERN_PPC_L2CR: CInt = 31

public val KERN_RTSIGNR: CInt = 32

public val KERN_RTSIGMAX: CInt = 33

public val KERN_SHMMAX: CInt = 34

public val KERN_MSGMAX: CInt = 35

public val KERN_MSGMNB: CInt = 36

public val KERN_MSGPOOL: CInt = 37

public val KERN_SYSRQ: CInt = 38

public val KERN_MAX_THREADS: CInt = 39

public val KERN_RANDOM: CInt = 40

public val KERN_SHMALL: CInt = 41

public val KERN_MSGMNI: CInt = 42

public val KERN_SEM: CInt = 43

public val KERN_SPARC_STOP_A: CInt = 44

public val KERN_SHMMNI: CInt = 45

public val KERN_OVERFLOWUID: CInt = 46

public val KERN_OVERFLOWGID: CInt = 47

public val KERN_SHMPATH: CInt = 48

public val KERN_HOTPLUG: CInt = 49

public val KERN_IEEE_EMULATION_WARNINGS: CInt = 50

public val KERN_S390_USER_DEBUG_LOGGING: CInt = 51
