// port-lint: source sys/net/hostname/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.net.hostname.unsupported

import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.thread.Result

public fun hostname(): Result<OsString> = Result.Err(Error.UNSUPPORTED_PLATFORM)
