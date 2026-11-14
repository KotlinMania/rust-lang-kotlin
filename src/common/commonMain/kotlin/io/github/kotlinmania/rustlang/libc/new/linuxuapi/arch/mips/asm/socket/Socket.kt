// port-lint: source new/linux_uapi/arch/mips/asm/socket.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.arch.mips.asm.socket

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.libc.*

// Header: `arch / mips / include / uapi / asm / socket.h`

public val SOL_SOCKET: CInt = 0xffff

public val SO_DEBUG: CInt = 0x0001

public val SO_REUSEADDR: CInt = 0x0004

public val SO_KEEPALIVE: CInt = 0x0008

public val SO_DONTROUTE: CInt = 0x0010

public val SO_BROADCAST: CInt = 0x0020

public val SO_LINGER: CInt = 0x0080

public val SO_OOBINLINE: CInt = 0x0100

public val SO_REUSEPORT: CInt = 0x0200

public val SO_TYPE: CInt = 0x1008

// pub const SO_STYLE: cInt = SO_TYPE;
public val SO_ERROR: CInt = 0x1007

public val SO_SNDBUF: CInt = 0x1001

public val SO_RCVBUF: CInt = 0x1002

public val SO_SNDLOWAT: CInt = 0x1003

public val SO_RCVLOWAT: CInt = 0x1004

public val SO_SNDTIMEO_OLD: CInt = 0x1005

public val SO_RCVTIMEO_OLD: CInt = 0x1006

public val SO_ACCEPTCONN: CInt = 0x1009

public val SO_PROTOCOL: CInt = 0x1028

public val SO_DOMAIN: CInt = 0x1029

public val SO_NO_CHECK: CInt = 11

public val SO_PRIORITY: CInt = 12

public val SO_BSDCOMPAT: CInt = 14

public val SO_PASSCRED: CInt = 17

public val SO_PEERCRED: CInt = 18

public val SO_SECURITY_AUTHENTICATION: CInt = 22

public val SO_SECURITY_ENCRYPTION_TRANSPORT: CInt = 23

public val SO_SECURITY_ENCRYPTION_NETWORK: CInt = 24

public val SO_BINDTODEVICE: CInt = 25

public val SO_ATTACH_FILTER: CInt = 26

public val SO_DETACH_FILTER: CInt = 27

public val SO_GET_FILTER: CInt = SO_ATTACH_FILTER

public val SO_PEERNAME: CInt = 28

public val SO_PEERSEC: CInt = 30

public val SO_SNDBUFFORCE: CInt = 31

public val SO_RCVBUFFORCE: CInt = 33

public val SO_PASSSEC: CInt = 34

public val SO_MARK: CInt = 36

public val SO_RXQ_OVFL: CInt = 40

public val SO_WIFI_STATUS: CInt = 41

public val SCM_WIFI_STATUS: CInt = SO_WIFI_STATUS

public val SO_PEEK_OFF: CInt = 42

public val SO_NOFCS: CInt = 43

public val SO_LOCK_FILTER: CInt = 44

public val SO_SELECT_ERR_QUEUE: CInt = 45

public val SO_BUSY_POLL: CInt = 46

public val SO_MAX_PACING_RATE: CInt = 47

public val SO_BPF_EXTENSIONS: CInt = 48

public val SO_INCOMING_CPU: CInt = 49

public val SO_ATTACH_BPF: CInt = 50

public val SO_DETACH_BPF: CInt = SO_DETACH_FILTER

public val SO_ATTACH_REUSEPORT_CBPF: CInt = 51

public val SO_ATTACH_REUSEPORT_EBPF: CInt = 52

public val SO_CNX_ADVICE: CInt = 53

public val SCM_TIMESTAMPING_OPT_STATS: CInt = 54

public val SO_MEMINFO: CInt = 55

public val SO_INCOMING_NAPI_ID: CInt = 56

public val SO_COOKIE: CInt = 57

public val SCM_TIMESTAMPING_PKTINFO: CInt = 58

public val SO_PEERGROUPS: CInt = 59

public val SO_ZEROCOPY: CInt = 60

public val SO_TXTIME: CInt = 61

public val SCM_TXTIME: CInt = SO_TXTIME

public val SO_BINDTOIFINDEX: CInt = 62

public val SO_TIMESTAMP_OLD: CInt = 29

public val SO_TIMESTAMPNS_OLD: CInt = 35

public val SO_TIMESTAMPING_OLD: CInt = 37

public val SO_TIMESTAMP_NEW: CInt = 63

public val SO_TIMESTAMPNS_NEW: CInt = 64

public val SO_TIMESTAMPING_NEW: CInt = 65

public val SO_RCVTIMEO_NEW: CInt = 66

public val SO_SNDTIMEO_NEW: CInt = 67

public val SO_DETACH_REUSEPORT_BPF: CInt = 68

public val SO_PREFER_BUSY_POLL: CInt = 69

public val SO_BUSY_POLL_BUDGET: CInt = 70

public val SO_NETNS_COOKIE: CInt = 71

public val SO_BUF_LOCK: CInt = 72

public val SO_RESERVE_MEM: CInt = 73

public val SO_TXREHASH: CInt = 74

public val SO_RCVMARK: CInt = 75

public val SO_PASSPIDFD: CInt = 76

public val SO_PEERPIDFD: CInt = 77

public val SO_DEVMEM_LINEAR: CInt = 78

public val SCM_DEVMEM_LINEAR: CInt = SO_DEVMEM_LINEAR

public val SO_DEVMEM_DMABUF: CInt = 79

public val SCM_DEVMEM_DMABUF: CInt = SO_DEVMEM_DMABUF

public val SO_DEVMEM_DONTNEED: CInt = 80

public val SO_TIMESTAMP: CInt = SO_TIMESTAMP_OLD

public val SO_TIMESTAMPNS: CInt = SO_TIMESTAMPNS_OLD

public val SO_TIMESTAMPING: CInt = SO_TIMESTAMPING_OLD

public val SO_RCVTIMEO: CInt = SO_RCVTIMEO_OLD

public val SO_SNDTIMEO: CInt = SO_SNDTIMEO_OLD

public val SCM_TIMESTAMP: CInt = SO_TIMESTAMP

public val SCM_TIMESTAMPNS: CInt = SO_TIMESTAMPNS

public val SCM_TIMESTAMPING: CInt = SO_TIMESTAMPING
