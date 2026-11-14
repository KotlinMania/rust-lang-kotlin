// port-lint: source net/hostname.rs
package io.github.kotlinmania.rustlang.std.net.hostname

import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.thread.Result

/**
 * Returns the system hostname.
 *
 * This can error out in platform-specific error cases;
 * for example, uefi and wasm, where hostnames aren't
 * supported.
 *
 * # Underlying system calls
 *
 * | Platform     | System call                                                                                             |
 * |--------------|---------------------------------------------------------------------------------------------------------|
 * | UNIX         | [`gethostname`](https://www.man7.org/linux/man-pages/man2/gethostname.2.html)                           |
 * | Windows (8+) | [`GetHostNameW`](https://learn.microsoft.com/en-us/windows/win32/api/winsock2/nf-winsock2-gethostnamew) |
 *
 * Note that platform-specific behavior [may change in the future][changes].
 */
public fun hostname(): Result<OsString> = io.github.kotlinmania.rustlang.std.sys.net.hostname.unix.hostname()
