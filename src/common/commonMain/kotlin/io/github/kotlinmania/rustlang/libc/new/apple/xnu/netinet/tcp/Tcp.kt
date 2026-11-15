// port-lint: source new/apple/xnu/netinet/tcp.rs
package io.github.kotlinmania.rustlang.libc.new.apple.xnu.netinet.tcp

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.libc.*

// Header: `netinet / tcp.h`
//
// <https://github.com/apple-oss-distributions/xnu/blob/main/bsd/netinet/tcp.h>

// TCP header flags. These are numbered as if `thX2` and `thFlags` were one
// field, with `thFlags` as the low octet, so `TH_AE` (0x100) and `TH_ACE`
// (which spans both) do not fit in `thFlags` alone.
public val TH_FIN: CInt = 0x01

public val TH_SYN: CInt = 0x02

public val TH_RST: CInt = 0x04

public val TH_PUSH: CInt = 0x08

public val TH_ACK: CInt = 0x10

public val TH_URG: CInt = 0x20

public val TH_ECE: CInt = 0x40

public val TH_CWR: CInt = 0x80

public val TH_AE: CInt = 0x100

public val TH_FLAGS: CInt = TH_FIN or TH_SYN or TH_RST or TH_ACK or TH_URG or TH_ECE or TH_CWR

public val TH_FLAGS_ALL: CInt = TH_FLAGS or TH_PUSH

public val TH_ACCEPT: CInt = TH_FIN or TH_SYN or TH_RST or TH_ACK

public val TH_ACE: CInt = TH_AE or TH_CWR or TH_ECE

// TCP option kinds and their lengths.
public val TCPOPT_EOL: CInt = 0

public val TCPOLEN_EOL: CInt = 1

public val TCPOPT_NOP: CInt = 1

public val TCPOLEN_NOP: CInt = 1

public val TCPOPT_MAXSEG: CInt = 2

public val TCPOLEN_MAXSEG: CInt = 4

public val TCPOPT_WINDOW: CInt = 3

public val TCPOLEN_WINDOW: CInt = 3

/**
 * SACK capability in SYN.
 */
public val TCPOPT_SACK_PERMITTED: CInt = 4

public val TCPOLEN_SACK_PERMITTED: CInt = 2

public val TCPOPT_SACK: CInt = 5

public val TCPOLEN_SACKHDR: CInt = 2

/**
 * Length of one SACK block.
 */
public val TCPOLEN_SACK: CInt = 8

public val TCPOPT_TIMESTAMP: CInt = 8

public val TCPOLEN_TIMESTAMP: CInt = 10

/**
 * RFC 1323 appendix A.
 */
public val TCPOLEN_TSTAMP_APPA: CInt = TCPOLEN_TIMESTAMP + 2

public val TCPOPT_TSTAMP_HDR: CInt = (TCPOPT_NOP shl 24) or (TCPOPT_NOP shl 16) or (TCPOPT_TIMESTAMP shl 8) or TCPOLEN_TIMESTAMP

/**
 * Absolute maximum TCP options length.
 */
public val MAX_TCPOPTLEN: CInt = 40

/**
 * CC options: RFC 1644.
 */
public val TCPOPT_CC: CInt = 11

public val TCPOPT_CCNEW: CInt = 12

public val TCPOPT_CCECHO: CInt = 13

public val TCPOLEN_CC: CInt = 6

public val TCPOLEN_CC_APPA: CInt = TCPOLEN_CC + 2

/**
 * Keyed MD5: RFC 2385.
 */
public val TCPOPT_SIGNATURE: CInt = 19

public val TCPOLEN_SIGNATURE: CInt = 18

public val TCPOPT_FASTOPEN: CInt = 34

public val TCPOLEN_FASTOPEN_REQ: CInt = 2

/**
 * AccECN order 0.
 */
public val TCPOPT_ACCECN0: CInt = 172

/**
 * AccECN order 1.
 */
public val TCPOPT_ACCECN1: CInt = 174

/**
 * Empty option contains kind and length.
 */
public val TCPOLEN_ACCECN_EMPTY: CInt = 2

/**
 * Length of each AccECN counter.
 */
public val TCPOLEN_ACCECN_COUNTER: CInt = 3

public val TCPOPT_SACK_PERMIT_HDR: CInt = (TCPOPT_NOP shl 24) or (TCPOPT_NOP shl 16) or (TCPOPT_SACK_PERMITTED shl 8) or TCPOLEN_SACK_PERMITTED

public val TCPOPT_SACK_HDR: CInt = (TCPOPT_NOP shl 24) or (TCPOPT_NOP shl 16) or (TCPOPT_SACK shl 8)

/**
 * Maximum number of SACK blocks stored at the sender.
 *
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val MAX_SACK_BLKS: CInt = 6

/**
 * Maximum number of SACKs sent in any segment.
 */
public val TCP_MAX_SACK: CInt = 4
