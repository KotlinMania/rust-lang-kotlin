// port-lint: tests panicking.rs
package io.github.kotlinmania.rustlang.std.panicking

// Implementation of various bits and pieces of the `panic` macro and
// associated runtime pieces.
//
// Specifically, this module contains the implementation of:
//
// * Panic hooks
// * Executing a panic up to doing the actual implementation
// * Shims around "try"

