// port-lint: source sys/alloc/motor.rs
package io.github.kotlinmania.rustlang.std.sys.alloc.motor

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun alloc(layout: Layout): MutPtr = io.github.kotlinmania.motort.alloc.alloc(layout)

public fun allocZeroed(layout: Layout): MutPtr = io.github.kotlinmania.motort.alloc.allocZeroed(layout)
