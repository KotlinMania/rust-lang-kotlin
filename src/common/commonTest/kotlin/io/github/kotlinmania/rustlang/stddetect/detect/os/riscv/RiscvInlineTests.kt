// port-lint: tests detect/os/riscv.rs
package io.github.kotlinmania.rustlang.stddetect.detect.os.riscv

// Run-time feature detection utility for RISC-V.
//
// On RISC-V, full feature detection needs a help of one or more
// feature detection mechanisms (usually provided by the operating system).
//
// RISC-V architecture defines many extensions and some have dependency to others.
// More importantly, some of them cannot be enabled without resolving such
// dependencies due to limited set of features that such mechanisms provide.
//
// This module provides an OS-independent utility to process such relations
// between RISC-V extensions.

