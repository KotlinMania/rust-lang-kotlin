// port-lint: source probestack.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.probestack

// This module defines the `__rust_probestack` intrinsic which is used in the
// implementation of "stack probes" on certain platforms.
//
// The purpose of a stack probe is to provide a static guarantee that if a
// thread has a guard page then a stack overflow is guaranteed to hit that
// guard page. If a function did not have a stack probe then there's a risk of
// having a stack frame *larger* than the guard page, so a function call could
// skip over the guard page entirely and then later hit maybe the heap or
// another thread, possibly leading to security vulnerabilities such as [The
// Stack Clash], for example.
//
// [The Stack Clash]: https://blog.qualys.com/securitylabs/2017/06/19/the-stack-clash
//
// The `__rust_probestack` is called in the prologue of functions whose stack
// size is larger than the guard page, for example larger than 4096 bytes on
// x86. This function is then responsible for "touching" all pages relevant to
// the stack to ensure that that if any of them are the guard page we'll hit
// them guaranteed.
//
// The precise ABI for how this function operates is defined by LLVM. There's
// no real documentation as to what this is, so you'd basically need to read
// the LLVM source code for reference. Often though the test cases can be
// illuminating as to the ABI that's generated, or just looking at the output
// of `llc`.
//
// Note that `@naked` is typically used here for the stack probe because the
// ABI corresponds to no actual ABI.
//
// Finally it's worth noting that at the time of this writing LLVM only has
// support for stack probes on x86 and x8664. There's no support for stack
// probes on any other architecture like ARM or PowerPC64. LLVM I'm sure would
// be more than welcome to accept such a change!

// Our goal here is to touch each page between `rsp + 8` and `rsp + 8 - rax`,
// ensuring that if any pages are unmapped we'll make a page fault.
//
// The ABI here is that the stack frame size is located in `rax`. Upon
// return we're not supposed to modify `rsp` or `rax`.
public fun rustProbestack() {
    nakedAsm("\n            .cfi_startproc\n            push  rbp\n            .cfi_adjust_cfa_offset 8\n            .cfi_offset rbp, -16\n            mov   rbp, rsp\n            .cfi_def_cfa_register rbp\n\n            mov    r11, rax        // duplicate rax as we're clobbering r11\n\n            // Main loop, taken in one page increments. We're decrementing rsp by\n            // a page each time until there's less than a page remaining. We're\n            // guaranteed that this function isn't called unless there's more than a\n            // page needed.\n            //\n            // Note that we're also testing against `[rsp + 8]` to account for the 8\n            // bytes pushed on the stack originally with our return address. Using\n            // `[rsp + 8]` simulates us testing the stack pointer in the caller's\n            // context.\n\n            // It's usually called when rax >= 0x1000, but that's not always true.\n            // Dynamic stack allocation, which is needed to implement unsized\n            // rvalues, triggers stackprobe even if rax < 0x1000.\n            // Thus we have to check r11 first to avoid segfault.\n            cmp    r11, 0x1000\n            jna    3f\n        2:\n            sub    rsp, 0x1000\n            test   qword ptr [rsp + 8], rsp\n            sub    r11, 0x1000\n            cmp    r11, 0x1000\n            ja     2b\n\n        3:\n            // Finish up the last remaining stack space requested, getting the last\n            // bits out of r11\n            sub    rsp, r11\n            test   qword ptr [rsp + 8], rsp\n\n            // Restore the stack pointer to what it previously was when entering\n            // this function. The caller will readjust the stack pointer after we\n            // return.\n            add    rsp, rax\n\n            leave\n            .cfi_def_cfa_register rsp\n            .cfi_adjust_cfa_offset -8\n    ", "       ret", "\n            // for this target, [manually patch for LVI].\n            //\n            // [manually patch for LVI]: https://software.intel.com/security-software-guidance/insights/deep-dive-load-value-injection#specialinstructions\n            pop r11\n            lfence\n            jmp r11\n    ", "\n            .cfi_endproc\n    ")
}
