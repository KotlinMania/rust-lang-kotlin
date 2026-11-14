// port-lint: source os/windows/raw.rs
package io.github.kotlinmania.rustlang.std.os.windows.raw

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Windows-specific primitives.

public typealias Handle = MutPtr

public typealias Socket = ULong
