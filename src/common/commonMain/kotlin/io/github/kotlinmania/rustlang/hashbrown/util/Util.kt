// port-lint: source util.rs
package io.github.kotlinmania.rustlang.hashbrown.util

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun coldPath() {}

public fun likely(b: Boolean): Boolean =
    if (b) {
        true
    } else {
        coldPath()
        false
    }

public fun unlikely(b: Boolean): Boolean =
    if (b) {
        coldPath()
        true
    } else {
        false
    }

// Note: use strict provenance functions once they are stable.
// Implement it with a reinterpret for now.
public fun invalidMut(addr: Int): MutPtr = transmuteWithLayouts(addr, PrimitiveLayouts.USIZE64, PrimitiveLayouts.mutPtrLayout(64L))
