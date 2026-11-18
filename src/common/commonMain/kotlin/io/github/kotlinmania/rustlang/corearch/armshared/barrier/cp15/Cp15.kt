// port-lint: source arm_shared/barrier/cp15.rs
package io.github.kotlinmania.rustlang.corearch.armshared.barrier.cp15

import io.github.kotlinmania.rustlang.corearch.armshared.Dmb
import io.github.kotlinmania.rustlang.corearch.armshared.Dsb
import io.github.kotlinmania.rustlang.corearch.armshared.Isb

/**
 * Full system is the required shareability domain, reads and writes are the
 * required access types
 */
public open class SY : Dmb, Dsb, Isb {
    public override fun dmb() {}

    public override fun dsb() {}

    public override fun isb() {}

    public companion object
}
