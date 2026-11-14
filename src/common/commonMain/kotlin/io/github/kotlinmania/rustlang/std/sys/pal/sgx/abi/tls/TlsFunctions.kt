package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.tls

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

public expect fun getTlsPtr(): ConstPtr

public expect fun setTlsPtr(tls: ConstPtr)
