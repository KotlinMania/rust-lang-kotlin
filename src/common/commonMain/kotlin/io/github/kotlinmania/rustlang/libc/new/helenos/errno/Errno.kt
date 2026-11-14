// port-lint: source new/helenos/errno.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.errno

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// HelenOS errno handling
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/c/include/errno.h>

public expect fun errno(): MutPtr
