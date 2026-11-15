// port-lint: source new/linux_uapi/linux/if_link.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.iflink

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort

// Header: `uapi / linux / ifLink.h`

public val IFLA_UNSPEC: CUshort = 0u

public val IFLA_ADDRESS: CUshort = IFLA_UNSPEC + 1u

public val IFLA_BROADCAST: CUshort = IFLA_ADDRESS + 1u

public val IFLA_IFNAME: CUshort = IFLA_BROADCAST + 1u

public val IFLA_MTU: CUshort = IFLA_IFNAME + 1u

public val IFLA_LINK: CUshort = IFLA_MTU + 1u

public val IFLA_QDISC: CUshort = IFLA_LINK + 1u

public val IFLA_STATS: CUshort = IFLA_QDISC + 1u

public val IFLA_COST: CUshort = IFLA_STATS + 1u

public val IFLA_PRIORITY: CUshort = IFLA_COST + 1u

public val IFLA_MASTER: CUshort = IFLA_PRIORITY + 1u

public val IFLA_WIRELESS: CUshort = IFLA_MASTER + 1u

public val IFLA_PROTINFO: CUshort = IFLA_WIRELESS + 1u

public val IFLA_TXQLEN: CUshort = IFLA_PROTINFO + 1u

public val IFLA_MAP: CUshort = IFLA_TXQLEN + 1u

public val IFLA_WEIGHT: CUshort = IFLA_MAP + 1u

public val IFLA_OPERSTATE: CUshort = IFLA_WEIGHT + 1u

public val IFLA_LINKMODE: CUshort = IFLA_OPERSTATE + 1u

public val IFLA_LINKINFO: CUshort = IFLA_LINKMODE + 1u

public val IFLA_NET_NS_PID: CUshort = IFLA_LINKINFO + 1u

public val IFLA_IFALIAS: CUshort = IFLA_NET_NS_PID + 1u

public val IFLA_NUM_VF: CUshort = IFLA_IFALIAS + 1u

public val IFLA_VFINFO_LIST: CUshort = IFLA_NUM_VF + 1u

public val IFLA_STATS64: CUshort = IFLA_VFINFO_LIST + 1u

public val IFLA_VF_PORTS: CUshort = IFLA_STATS64 + 1u

public val IFLA_PORT_SELF: CUshort = IFLA_VF_PORTS + 1u

public val IFLA_AF_SPEC: CUshort = IFLA_PORT_SELF + 1u

public val IFLA_GROUP: CUshort = IFLA_AF_SPEC + 1u

public val IFLA_NET_NS_FD: CUshort = IFLA_GROUP + 1u

public val IFLA_EXT_MASK: CUshort = IFLA_NET_NS_FD + 1u

public val IFLA_PROMISCUITY: CUshort = IFLA_EXT_MASK + 1u

public val IFLA_NUM_TX_QUEUES: CUshort = IFLA_PROMISCUITY + 1u

public val IFLA_NUM_RX_QUEUES: CUshort = IFLA_NUM_TX_QUEUES + 1u

public val IFLA_CARRIER: CUshort = IFLA_NUM_RX_QUEUES + 1u

public val IFLA_PHYS_PORT_ID: CUshort = IFLA_CARRIER + 1u

public val IFLA_CARRIER_CHANGES: CUshort = IFLA_PHYS_PORT_ID + 1u

public val IFLA_PHYS_SWITCH_ID: CUshort = IFLA_CARRIER_CHANGES + 1u

public val IFLA_LINK_NETNSID: CUshort = IFLA_PHYS_SWITCH_ID + 1u

public val IFLA_PHYS_PORT_NAME: CUshort = IFLA_LINK_NETNSID + 1u

public val IFLA_PROTO_DOWN: CUshort = IFLA_PHYS_PORT_NAME + 1u

public val IFLA_GSO_MAX_SEGS: CUshort = IFLA_PROTO_DOWN + 1u

public val IFLA_GSO_MAX_SIZE: CUshort = IFLA_GSO_MAX_SEGS + 1u

public val IFLA_PAD: CUshort = IFLA_GSO_MAX_SIZE + 1u

public val IFLA_XDP: CUshort = IFLA_PAD + 1u

public val IFLA_EVENT: CUshort = IFLA_XDP + 1u

public val IFLA_NEW_NETNSID: CUshort = IFLA_EVENT + 1u

public val IFLA_IF_NETNSID: CUshort = IFLA_NEW_NETNSID + 1u

public val IFLA_TARGET_NETNSID: CUshort = IFLA_IF_NETNSID

public val IFLA_CARRIER_UP_COUNT: CUshort = IFLA_TARGET_NETNSID + 1u

public val IFLA_CARRIER_DOWN_COUNT: CUshort = IFLA_CARRIER_UP_COUNT + 1u

public val IFLA_NEW_IFINDEX: CUshort = IFLA_CARRIER_DOWN_COUNT + 1u

public val IFLA_MIN_MTU: CUshort = IFLA_NEW_IFINDEX + 1u

public val IFLA_MAX_MTU: CUshort = IFLA_MIN_MTU + 1u

public val IFLA_PROP_LIST: CUshort = IFLA_MAX_MTU + 1u

public val IFLA_ALT_IFNAME: CUshort = IFLA_PROP_LIST + 1u

public val IFLA_PERM_ADDRESS: CUshort = IFLA_ALT_IFNAME + 1u

public val IFLA_PROTO_DOWN_REASON: CUshort = IFLA_PERM_ADDRESS + 1u

public val IFLA_PARENT_DEV_NAME: CUshort = IFLA_PROTO_DOWN_REASON + 1u

public val IFLA_PARENT_DEV_BUS_NAME: CUshort = IFLA_PARENT_DEV_NAME + 1u

public val IFLA_GRO_MAX_SIZE: CUshort = IFLA_PARENT_DEV_BUS_NAME + 1u

public val IFLA_TSO_MAX_SIZE: CUshort = IFLA_GRO_MAX_SIZE + 1u

public val IFLA_TSO_MAX_SEGS: CUshort = IFLA_TSO_MAX_SIZE + 1u

public val IFLA_ALLMULTI: CUshort = IFLA_TSO_MAX_SEGS + 1u

public val IFLA_INFO_UNSPEC: CUshort = 0u

public val IFLA_INFO_KIND: CUshort = IFLA_INFO_UNSPEC + 1u

public val IFLA_INFO_DATA: CUshort = IFLA_INFO_KIND + 1u

public val IFLA_INFO_XSTATS: CUshort = IFLA_INFO_DATA + 1u

public val IFLA_INFO_SLAVE_KIND: CUshort = IFLA_INFO_XSTATS + 1u

public val IFLA_INFO_SLAVE_DATA: CUshort = IFLA_INFO_SLAVE_KIND + 1u
