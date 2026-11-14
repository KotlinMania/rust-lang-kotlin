// port-lint: source x86_64.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.x8664

// NOTE These functions are implemented using assembly because they use a custom
// calling convention which can't be implemented using a normal Rust function
// NOTE These functions are never mangled as they are not tested against compiler-rt
public fun chkstkMs() {
    nakedAsm("push   rcx", "push   rax", "cmp    rax, 0x1000", "lea    rcx, [rsp + 24]", "jb     3f", "2:", "sub    rcx, 0x1000", "test   [rcx], rcx", "sub    rax, 0x1000", "cmp    rax, 0x1000", "ja     2b", "3:", "sub    rcx, rax", "test   [rcx], rcx", "pop    rax", "pop    rcx", "ret")
}

public var FLTUSED: Int = 0
