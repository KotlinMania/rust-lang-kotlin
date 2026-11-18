package io.github.kotlinmania.rustlang.std.sys.pal.unix

import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool

// This is set (up to once) in resetSigpipe.
public var ON_BROKEN_PIPE_USED: AtomicBool = AtomicBool.new(false)
