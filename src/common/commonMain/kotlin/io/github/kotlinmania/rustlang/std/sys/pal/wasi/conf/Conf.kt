// port-lint: source sys/pal/wasi/conf.rs
package io.github.kotlinmania.rustlang.std.sys.pal.wasi.conf

import io.github.kotlinmania.libc.fuchsia.SC_PAGESIZE
import io.github.kotlinmania.libc.sysconf

public fun pageSize(): Int = sysconf(SC_PAGESIZE).toInt()
