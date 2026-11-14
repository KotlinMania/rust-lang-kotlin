// port-lint: source os/hurd/raw.rs
package io.github.kotlinmania.rustlang.std.os.hurd.raw

import io.github.kotlinmania.rustlang.std.os.raw.CLong
import io.github.kotlinmania.rustlang.std.os.raw.CUint
import io.github.kotlinmania.rustlang.std.os.raw.CUlong

// Hurd-specific raw type definitions

public typealias BlkcntT = ULong

public typealias BlksizeT = CLong

public typealias DevT = CUlong

public typealias InoT = ULong

public typealias ModeT = CUint

public typealias NlinkT = CUlong

public typealias OffT = ULong

public typealias TimeT = CLong

public typealias PthreadT = CLong
