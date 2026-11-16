package io.github.kotlinmania.rustlang.std.sys.pal.unix

import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.std.sync.mpmc.counter.new

// This is set (up to once) in resetSigpipe.
public var ON_BROKEN_PIPE_USED: Atomic<Boolean, Align1<UByte>> = new(false)
