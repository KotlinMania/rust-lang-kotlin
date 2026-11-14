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
public fun rustProbestack() {}
