package io.github.kotlinmania.rustlang.std.sys.pal.unix

import io.github.kotlinmania.rustlang.sync.mpmc.counter.new

// This is set (up to once) in resetSigpipe.
public var ON_BROKEN_PIPE_USED: Atomic<Boolean> = new(false)
