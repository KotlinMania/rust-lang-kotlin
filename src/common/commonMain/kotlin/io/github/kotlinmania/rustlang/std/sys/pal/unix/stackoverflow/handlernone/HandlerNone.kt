// port-lint: source sys/pal/unix/stack_overflow/handler_none.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.handlernone

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.Handler

public fun init() {}

public fun makeHandler(mainThread: Boolean): Handler = ConstPtr(0L)

public fun dropHandler(data: MutPtr) {}
