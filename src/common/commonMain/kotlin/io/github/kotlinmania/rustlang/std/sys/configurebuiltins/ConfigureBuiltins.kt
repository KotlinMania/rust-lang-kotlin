// port-lint: source sys/configure_builtins.rs
package io.github.kotlinmania.rustlang.std.sys.configurebuiltins

import io.github.kotlinmania.rustlang.arch.isAarch64FeatureDetected

// The configure builtins provides runtime support compiler-builtin features
// which require dynamic initialization to work as expected, e.g. aarch64
// outline-atomics.

public fun interface FnCallback {
    public operator fun invoke()
}

/**
 * Enable LSE atomic operations at startup, if supported.
 *
 * Linker sections are based on what [ctor] does, with priorities to run slightly before user
 * code:
 *
 * - Apple uses the section `__mod_init_func`, `modInitFuncs` is needed to set
 *   `S_MOD_INIT_FUNC_POINTERS`. There doesn't seem to be a way to indicate priorities.
 * - Windows uses `.CRT $ XCT`, which is run before user constructors (these should use `.CRT $ XCU`).
 * - ELF uses `.initArray` with a priority of 90, which runs before our `ARGV_INIT_ARRAY`
 *   initializer (priority 99). Both are within the 0-100 implementation-reserved range, per docs
 *   for the [prio.ctor.dtor] warning, and this matches compiler-rt's `CONSTRUCTOR_PRIORITY`.
 *
 * To save startup time, the initializer is only run if outline atomic routines from
 * compiler-builtins may be used. If LSE is known to be available then the calls are never
 * emitted, and if we build the C intrinsics then it has its own initializer using the symbol
 * `__aarch64_have_lse_atomics`.
 *
 * Initialization is done in a global constructor to so we get the same behavior regardless of
 * whether Rust's `init` is used, or if we are in a `dylib` or `noMain` situation (as opposed
 * to doing it as part of pre-main startup). This also matches C implementations.
 *
 * Ideally `core` would have something similar, but detecting the CPU features requires the
 * auxiliary vector from the OS. We do the initialization in `std` rather than as part of
 * `compiler - builtins` because a builtins->std dependency isn't possible, and inlining parts of
 * `std - detect` would be much messier.
 *
 * [ctor]: https://github.com/mmastrac/rust-ctor/blob/63382b833ddcbfb8b064f4e86bfa1ed4026ff356/shared/src/macros/mod.rs#L522-L534
 * [prio.ctor.dtor]: https://gcc.gnu.org/onlinedocs/gcc/Warning-Options.html
 */
public var RUST_LSE_INIT: FnCallback =
    run {
        fun initLse() {
            // This is provided by compiler-builtins.aarch64OutlineAtomics.
            fun rustEnableLse() {}
            if (isAarch64FeatureDetected("lse")) {
                run {
                    rustEnableLse()
                }
            }
        }
        ::initLse
    }
