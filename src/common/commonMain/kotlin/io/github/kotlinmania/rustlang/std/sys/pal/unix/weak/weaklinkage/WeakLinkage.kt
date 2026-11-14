// port-lint: source sys/pal/unix/weak/weak_linkage.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.weak.weaklinkage

internal open class ExternWeak<F>(
    public var weakPtr: F?,
) {
    public open fun get(): F? = this.weakPtr

    public companion object {
        public fun <F> new(weakPtr: F?): ExternWeak<F> = ExternWeak(weakPtr = weakPtr)
    }
}
