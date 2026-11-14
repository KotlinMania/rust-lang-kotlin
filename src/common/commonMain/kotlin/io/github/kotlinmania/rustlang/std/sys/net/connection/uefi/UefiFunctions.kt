package io.github.kotlinmania.rustlang.std.sys.net.connection.uefi

import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.thread.Result

public fun lookupHost(host: String, port: UShort): Result<LookupHost> = unsupported()
