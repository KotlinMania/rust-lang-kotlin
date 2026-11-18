// port-lint: source sync/nonpoison.rs
package io.github.kotlinmania.rustlang.std.sync.nonpoison

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sync.poison.Guard

// Non-poisoning synchronous locks.
//
// The difference from the locks in the [poison] module is that the locks in this module will not
// become poisoned when a thread panics while holding a guard.

/**
 * A type alias for the result of a nonblocking locking method.
 */
public typealias TryLockResult<Guard> = Result<Guard, WouldBlock>

/**
 * A lock could not be acquired at this time because the operation would otherwise block.
 */
public open class WouldBlock {
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("WouldBlock"); Result.Ok<Unit, Error>(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = run { f.append("try_lock failed because the operation would block"); Result.Ok<Unit, Error>(Unit) }

    public companion object
}
