// port-lint: source sys/net/hostname/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.net.hostname.unsupported

import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString

public fun hostname(): Result<OsString> = Result.Err(Error.UNSUPPORTED_PLATFORM)
