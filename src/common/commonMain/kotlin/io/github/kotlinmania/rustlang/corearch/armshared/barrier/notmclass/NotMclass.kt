// port-lint: source arm_shared/barrier/not_mclass.rs
package io.github.kotlinmania.rustlang.corearch.armshared.barrier.notmclass

import io.github.kotlinmania.rustlang.corearch.armshared.Dmb
import io.github.kotlinmania.rustlang.corearch.armshared.Dsb
import io.github.kotlinmania.rustlang.corearch.armshared.dmb
import io.github.kotlinmania.rustlang.corearch.armshared.dsb

// Access types available on v7 and v8 but not on v7(E)-M or v8-M

/**
 * Full system is the required shareability domain, writes are the required
 * access type
 */
public open class ST : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.ST)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(io.github.kotlinmania.rustlang.corearch.armshared.barrier.ST)
    }

    public companion object
}

/**
 * Inner Shareable is the required shareability domain, reads and writes are
 * the required access types
 */
public open class Ish : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Ish)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Ish)
    }

    public companion object
}

/**
 * Inner Shareable is the required shareability domain, writes are the required
 * access type
 */
public open class Ishst : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Ishst)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Ishst)
    }

    public companion object
}

/**
 * Non-shareable is the required shareability domain, reads and writes are the
 * required access types
 */
public open class Nsh : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Nsh)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Nsh)
    }

    public companion object
}

/**
 * Non-shareable is the required shareability domain, writes are the required
 * access type
 */
public open class Nshst : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Nshst)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Nshst)
    }

    public companion object
}

/**
 * Outer Shareable is the required shareability domain, reads and writes are
 * the required access types
 */
public open class Osh : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Osh)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Osh)
    }

    public companion object
}

/**
 * Outer Shareable is the required shareability domain, writes are the required
 * access type
 */
public open class Oshst : Dmb, Dsb {
    public override fun dmb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dmb(Oshst)
    }

    public override fun dsb() {
        io.github.kotlinmania.rustlang.corearch.armshared.barrier.dsb(Oshst)
    }

    public companion object
}
