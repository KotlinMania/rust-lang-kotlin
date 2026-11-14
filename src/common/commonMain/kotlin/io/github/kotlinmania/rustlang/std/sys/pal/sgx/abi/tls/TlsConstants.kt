package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls.syncbitset.SYNC_BITSET_INIT
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls.syncbitset.SyncBitset

public val USIZE_BITS: Int = 64

public val TLS_KEYS: Int = 128

// Same as POSIX minimum
public val TLS_KEYS_BITSET_SIZE: Int = (TLS_KEYS + (USIZE_BITS - 1)) / USIZE_BITS

// Specifying linkage/symbol name is solely to ensure a single instance between this crate and its unit tests
public var TLS_KEY_IN_USE: SyncBitset = SYNC_BITSET_INIT

// Specifying linkage/symbol name is solely to ensure a single instance between this crate and its unit tests
public var TLS_DESTRUCTOR: List<AtomicPtr> = List(TLS_KEYS) { AtomicPtr.new(MutPtr(0L)) }
