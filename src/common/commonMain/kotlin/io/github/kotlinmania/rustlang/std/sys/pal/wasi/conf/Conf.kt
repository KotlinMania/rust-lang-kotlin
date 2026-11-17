// port-lint: source sys/pal/wasi/conf.rs
package io.github.kotlinmania.rustlang.std.sys.pal.wasi.conf

import io.github.kotlinmania.rustlang.libc.sysconf
import io.github.kotlinmania.rustlang.libc.wasi.SC_PAGESIZE

public fun pageSize(): Int = sysconf(SC_PAGESIZE).toInt()
