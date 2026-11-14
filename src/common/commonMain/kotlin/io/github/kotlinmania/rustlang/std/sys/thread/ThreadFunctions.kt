package io.github.kotlinmania.rustlang.std.sys.thread

import io.github.kotlinmania.rustlang.std.sys.thread.unix.sleep
import io.github.kotlinmania.rustlang.std.time.Instant

public fun sleepUntil(deadline: Instant) {
    // The clock source used for `sleep` might not be the same used for `Instant`.
    // Since this function *must not* return before the deadline, we recheck the
    // time after every call to `sleep`. See #149935 for an example of this
    // occurring on older Windows systems.
    while (true) {
        val matchVal = deadline.checkedDurationSince(Instant.now())
        if (matchVal == null) break
        val delay = matchVal
        // Sleep for the estimated time remaining until the deadline.
        //
        // If your system has a better way of estimating the delay time or
        // provides a way to sleep until an absolute time, specialize this
        // function for your system.
        sleep(delay)
    }
}
