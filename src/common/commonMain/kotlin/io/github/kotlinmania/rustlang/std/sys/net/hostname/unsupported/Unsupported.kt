// port-lint: source sys/net/hostname/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.net.hostname.unsupported

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public fun hostname(): Result<OsString> = RustResult.Err<OsString, io.github.kotlinmania.rustlang.core.io.error.Error>(Error.UNSUPPORTED_PLATFORM)
