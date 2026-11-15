// port-lint: source new/common/posix/unistd.rs
package io.github.kotlinmania.rustlang.libc.new.common.posix.unistd

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt

// Header: `unistd.h`
//
// <https://pubs.opengroup.org/onlinepubs/007904975/basedefs/unistd.h.html>

public val STDIN_FILENO: CInt = 0

public val STDOUT_FILENO: CInt = 1

public val STDERR_FILENO: CInt = 2
