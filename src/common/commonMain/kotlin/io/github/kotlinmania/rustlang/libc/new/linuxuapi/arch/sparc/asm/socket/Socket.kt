// port-lint: source new/linux_uapi/arch/sparc/asm/socket.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.arch.sparc.asm.socket

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt

// Header: `arch / sparc / include / uapi / asm / socket.h`

public val SOL_SOCKET: CInt = 0xffff

public val SO_DEBUG: CInt = 0x0001

public val SO_PASSCRED: CInt = 0x0002

public val SO_REUSEADDR: CInt = 0x0004

public val SO_KEEPALIVE: CInt = 0x0008

public val SO_DONTROUTE: CInt = 0x0010

public val SO_BROADCAST: CInt = 0x0020

public val SO_PEERCRED: CInt = 0x0040

public val SO_LINGER: CInt = 0x0080

public val SO_OOBINLINE: CInt = 0x0100

public val SO_REUSEPORT: CInt = 0x0200

public val SO_BSDCOMPAT: CInt = 0x0400

public val SO_RCVLOWAT: CInt = 0x0800

public val SO_SNDLOWAT: CInt = 0x1000

public val SO_RCVTIMEO_OLD: CInt = 0x2000

public val SO_SNDTIMEO_OLD: CInt = 0x4000

public val SO_ACCEPTCONN: CInt = 0x8000

public val SO_SNDBUF: CInt = 0x1001

public val SO_RCVBUF: CInt = 0x1002

public val SO_SNDBUFFORCE: CInt = 0x100a

public val SO_RCVBUFFORCE: CInt = 0x100b

public val SO_ERROR: CInt = 0x1007

public val SO_TYPE: CInt = 0x1008

public val SO_PROTOCOL: CInt = 0x1028

public val SO_DOMAIN: CInt = 0x1029

// Linux specific, keep the same.
public val SO_NO_CHECK: CInt = 0x000b

public val SO_PRIORITY: CInt = 0x000c

public val SO_BINDTODEVICE: CInt = 0x000d

public val SO_ATTACH_FILTER: CInt = 0x001a

public val SO_DETACH_FILTER: CInt = 0x001b

public val SO_GET_FILTER: CInt = SO_ATTACH_FILTER

public val SO_PEERNAME: CInt = 0x001c

public val SO_PEERSEC: CInt = 0x001e

public val SO_PASSSEC: CInt = 0x001f

public val SO_MARK: CInt = 0x0022

public val SO_RXQ_OVFL: CInt = 0x0024

public val SO_WIFI_STATUS: CInt = 0x0025

public val SCM_WIFI_STATUS: CInt = SO_WIFI_STATUS

public val SO_PEEK_OFF: CInt = 0x0026

public val SO_NOFCS: CInt = 0x0027

public val SO_LOCK_FILTER: CInt = 0x0028

public val SO_SELECT_ERR_QUEUE: CInt = 0x0029

public val SO_BUSY_POLL: CInt = 0x0030

public val SO_MAX_PACING_RATE: CInt = 0x0031

public val SO_BPF_EXTENSIONS: CInt = 0x0032

public val SO_INCOMING_CPU: CInt = 0x0033

public val SO_ATTACH_BPF: CInt = 0x0034

public val SO_DETACH_BPF: CInt = SO_DETACH_FILTER

public val SO_ATTACH_REUSEPORT_CBPF: CInt = 0x0035

public val SO_ATTACH_REUSEPORT_EBPF: CInt = 0x0036

public val SO_CNX_ADVICE: CInt = 0x0037

public val SCM_TIMESTAMPING_OPT_STATS: CInt = 0x0038

public val SO_MEMINFO: CInt = 0x0039

public val SO_INCOMING_NAPI_ID: CInt = 0x003a

public val SO_COOKIE: CInt = 0x003b

public val SCM_TIMESTAMPING_PKTINFO: CInt = 0x003c

public val SO_PEERGROUPS: CInt = 0x003d

public val SO_ZEROCOPY: CInt = 0x003e

public val SO_TXTIME: CInt = 0x003f

public val SCM_TXTIME: CInt = SO_TXTIME

public val SO_BINDTOIFINDEX: CInt = 0x0041

public val SO_SECURITY_AUTHENTICATION: CInt = 0x5001

public val SO_SECURITY_ENCRYPTION_TRANSPORT: CInt = 0x5002

public val SO_SECURITY_ENCRYPTION_NETWORK: CInt = 0x5004

public val SO_TIMESTAMP_OLD: CInt = 0x001d

public val SO_TIMESTAMPNS_OLD: CInt = 0x0021

public val SO_TIMESTAMPING_OLD: CInt = 0x0023

// `SO_ * _NEW` constants defined in `cfg` block
public val SO_DETACH_REUSEPORT_BPF: CInt = 0x0047

public val SO_PREFER_BUSY_POLL: CInt = 0x0048

public val SO_BUSY_POLL_BUDGET: CInt = 0x0049

public val SO_NETNS_COOKIE: CInt = 0x0050

public val SO_BUF_LOCK: CInt = 0x0051

public val SO_RESERVE_MEM: CInt = 0x0052

public val SO_TXREHASH: CInt = 0x0053

public val SO_RCVMARK: CInt = 0x0054

public val SO_PASSPIDFD: CInt = 0x0055

public val SO_PEERPIDFD: CInt = 0x0056

public val SO_DEVMEM_LINEAR: CInt = 0x0057

public val SCM_DEVMEM_LINEAR: CInt = SO_DEVMEM_LINEAR

public val SO_DEVMEM_DMABUF: CInt = 0x0058

public val SCM_DEVMEM_DMABUF: CInt = SO_DEVMEM_DMABUF

public val SO_DEVMEM_DONTNEED: CInt = 0x0059

public val SCM_TS_OPT_ID: CInt = 0x005a

public val SO_RCVPRIORITY: CInt = 0x005b

public val SO_PASSRIGHTS: CInt = 0x005c

public val SO_INQ: CInt = 0x005d

public val SCM_INQ: CInt = SO_INQ

public val SO_TIMESTAMP: CInt = SO_TIMESTAMP_OLD

public val SO_TIMESTAMPNS: CInt = SO_TIMESTAMPNS_OLD

public val SO_TIMESTAMPING: CInt = SO_TIMESTAMPING_OLD

public val SO_RCVTIMEO: CInt = SO_RCVTIMEO_OLD

public val SO_SNDTIMEO: CInt = SO_SNDTIMEO_OLD

public val SCM_TIMESTAMP: CInt = SO_TIMESTAMP

public val SCM_TIMESTAMPNS: CInt = SO_TIMESTAMPNS

public val SCM_TIMESTAMPING: CInt = SO_TIMESTAMPING
