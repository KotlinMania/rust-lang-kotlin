// port-lint: source num/imp/ppcf128.rs
package io.github.kotlinmania.rustlang.core.num.imp.ppcf128

import io.github.kotlinmania.rustlang.core.U128

/**
 * The IBM extended-precision (double-double) floating-point lang item.
 *
 * Here we define just the lang item, the public-facing type is defined
 * in coreArch. It transparently wraps this definition.
 *
 * The ppcf128 type should only be used on PowerPC targets, but is always defined
 * for the purpose of doc builds, specifically doc builds in the stdarch repo.
 */
public open class Ppcf128(
    public var value: U128,
) {
    public var field0: U128
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Ppcf128 = Ppcf128(value)

    public companion object
}
