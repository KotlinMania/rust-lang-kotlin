// port-lint: source sys/thread/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.thread.unsupported

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.thread.lifecycle.ThreadInit

// Silence dead code warnings for the otherwise unused ThreadInit.init() call.
public fun dummyInitCall(init: ThreadInit) {
    (init.init()).let { }
}

public open class Thread(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun join() {
        this.value
    }

    public companion object {
        // unchecked: see thread.Builder.spawnUnchecked for safety requirements
        public fun new(stack: Int, init: ThreadInit): Result<Thread, Error> = Result.Err<Thread, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.UNSUPPORTED_PLATFORM)
    }
}

public val DEFAULT_MIN_STACK_SIZE: Int = 64 * 1024

public fun availableParallelism(): Result<NonZero<Int, NonZeroUsizeInner>, Error> = Result.Err<NonZero<Int, NonZeroUsizeInner>, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.UNKNOWN_THREAD_COUNT)

public fun currentOsId(): ULong? = null

public fun yieldNow() {
    // do nothing
}

public fun setName(name: CStr) {
    // nope
}

public fun sleep(dur: Duration) {
    error("can't sleep")
}
