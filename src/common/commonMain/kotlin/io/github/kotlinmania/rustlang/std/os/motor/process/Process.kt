// port-lint: source os/motor/process.rs
package io.github.kotlinmania.rustlang.std.os.motor.process

import io.github.kotlinmania.rustlang.std.process.Child

public interface ChildExt<T> {
    /**
     * Extracts the main thread raw handle, without taking ownership
     */
    public fun sysHandle(): ULong
}

public fun Child.sysHandle(): ULong = this.asInner().handle()
