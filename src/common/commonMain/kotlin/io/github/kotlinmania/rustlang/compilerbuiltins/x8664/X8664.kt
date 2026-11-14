// port-lint: source x86_64.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.x8664

// NOTE These functions are implemented using assembly because they use a custom
// calling convention which can't be implemented using a normal Rust function
// NOTE These functions are never mangled as they are not tested against compiler-rt
public fun chkstkMs() {
    Unit
}
