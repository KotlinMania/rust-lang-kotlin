// port-lint: source arm_shared/barrier/common.rs
package io.github.kotlinmania.rustlang.corearch.armshared.barrier.common

import io.github.kotlinmania.rustlang.corearch.armshared.Dmb
import io.github.kotlinmania.rustlang.corearch.armshared.Dsb
import io.github.kotlinmania.rustlang.corearch.armshared.Isb
import io.github.kotlinmania.rustlang.corearch.armshared.dmb
import io.github.kotlinmania.rustlang.corearch.armshared.dsb
import io.github.kotlinmania.rustlang.corearch.armshared.isb

// Access types available on all architectures

/**
 * Full system is the required shareability domain, reads and writes are the
 * required access types
 */
public open class SY : Dmb, Dsb, Isb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.SY)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.SY)
    }

    public override fun isb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.isb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.SY)
    }

    public companion object
}
