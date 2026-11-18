// port-lint: source arm_shared/barrier/v8.rs
package io.github.kotlinmania.rustlang.corearch.armshared.barrier.v8

import io.github.kotlinmania.rustlang.corearch.armshared.Dmb
import io.github.kotlinmania.rustlang.corearch.armshared.Dsb
import io.github.kotlinmania.rustlang.corearch.armshared.dmb
import io.github.kotlinmania.rustlang.corearch.armshared.dsb

/**
 * Full system is the required shareability domain, reads are the required
 * access type
 */
public open class LD : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.LD)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.LD)
    }

    public companion object
}

/**
 * Inner Shareable is the required shareability domain, reads are the required
 * access type
 */
public open class Ishld : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Ishld)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Ishld)
    }

    public companion object
}

/**
 * Non-shareable is the required shareability domain, reads are the required
 * access type
 */
public open class Nshld : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Nshld)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Nshld)
    }

    public companion object
}

/**
 * Outer Shareable is the required shareability domain, reads are the required
 * access type
 */
public open class Oshld : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Oshld)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Oshld)
    }

    public companion object
}
