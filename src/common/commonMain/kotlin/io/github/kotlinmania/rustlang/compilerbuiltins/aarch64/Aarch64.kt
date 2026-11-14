// port-lint: source aarch64.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.aarch64

public fun chkstk() {
    nakedAsm(".p2align 2", "lsl    x16, x15, #4", "mov    x17, sp", "1:", "sub    x17, x17, 4096", "subs   x16, x16, 4096", "ldr    xzr, [x17]", "b.gt   1b", "ret")
}
