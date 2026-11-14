// port-lint: tests sys/pal/windows/api.rs
package io.github.kotlinmania.rustlang.std.sys.pal.windows.api

// # Safe(r) wrappers around Windows API functions.
//
// This module contains fairly thin wrappers around Windows API functions,
// aimed at centralising safety instead of having unchecked blocks spread
// throughout higher level code. This makes it much easier to audit FFI safety.
//
// Not all functions can be made completely safe without more context but in
// such cases we should still endeavour to reduce the caller's burden of safety
// as much as possible.
//
// ## Guidelines for wrappers
//
// Items here should be named similarly to their raw Windows API name, except
// that they follow Rust's case conventions. E.g. function names are
// lowerSnakeCase. The idea here is that it should be easy for a Windows
// C/C++ programmer to identify the underlying function that's being wrapped
// while not looking too out of place in Rust code.
//
// Every use of an `unchecked` block must have a related SAFETY comment, even if
// it's trivially safe (for example, see `getLastError`). Public unchecked
// functions must document what the caller has to do to call them safely.
//
// Avoid unchecked `as` casts. For integers, either assert that the integer
// is in range or use `tryInto` instead. For pointers, prefer to use
// `ptr.cast<Type>()` when possible.
//
// This module must only depend on core and not on std types as the eventual
// hope is to have std depend on sys and not the other way around.
// However, some amount of glue code may currently be necessary so such code
// should go in sys/pal/windows/mod.rs rather than here. See `IoResult` as an example.

