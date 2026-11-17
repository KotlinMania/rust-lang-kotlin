// port-lint: source os/windows/thread.rs
package io.github.kotlinmania.rustlang.std.os.windows.thread

import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.std.os.windows.io.RawHandle

// Windows-specific extensions to primitives in the [std.thread] module.

public fun <T> thread.JoinHandle<T>.asRawHandle(): RawHandle = mutPtrFromRustCast(this.asInner().handle().asRawHandle())

public fun <T> thread.JoinHandle<T>.intoRawHandle(): RawHandle = mutPtrFromRustCast(this.intoInner().intoHandle().intoRawHandle())
