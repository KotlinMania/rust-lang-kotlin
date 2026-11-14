// port-lint: tests fs.rs
package io.github.kotlinmania.rustlang.std.fs

// Filesystem manipulation operations.
//
// This module contains basic methods to manipulate the contents of the local
// filesystem. All methods in this module represent cross-platform filesystem
// operations. Extra platform-specific functionality can be found in the
// extension traits of `std.os.$ platform`.
//
// # Time of Check to Time of Use (TOCTOU)
//
// Many filesystem operations are subject to a race condition known as "Time of Check to Time of Use"
// (TOCTOU). This occurs when a program checks a condition (like file existence or permissions)
// and then uses the result of that check to make a decision, but the condition may have changed
// between the check and the use.
//
// For example, checking if a file exists and then creating it if it doesn't is vulnerable to
// TOCTOU - another process could create the file between your check and creation attempt.
//
// Another example is with symbolic links: when removing a directory, if another process replaces
// the directory with a symbolic link between the check and the removal operation, the removal
// might affect the wrong location. This is why operations like [removeDirAll] need to use
// atomic operations to prevent such race conditions.
//
// To avoid TOCTOU issues:
// - Be aware that metadata operations (like [metadata] or [symlinkMetadata]) may be affected by
// changes made by other processes.
// - Use atomic operations when possible (like [File.createNew] instead of checking existence then creating).
// - Keep file open for the duration of operations.

