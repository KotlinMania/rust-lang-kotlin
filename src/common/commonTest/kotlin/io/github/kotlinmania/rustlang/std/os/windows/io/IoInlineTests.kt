package io.github.kotlinmania.rustlang.std.os.windows.io

// Windows-specific extensions to general I/O primitives.
//
// Just like raw pointers, raw Windows handles and sockets point to resources
// with dynamic scopes, and they can dangle if they outlive their resources
// or be forged if they're created from invalid values.
//
// This module provides three types for representing raw handles and sockets
// with different ownership properties: raw, borrowed, and owned, which are
// analogous to types used for representing pointers. These types reflect concepts of [I/O
// safety][io-safety] on Windows.
//
// | Type                   | Analogous to |
// | ---------------------- | ------------ |
// | [RawHandle]          | `* const _`   |
// | [RawSocket]          | `* const _`   |
// |                        |              |
// | [BorrowedHandle.a] | `_`      |
// | [BorrowedSocket.a] | `_`      |
// |                        |              |
// | [OwnedHandle]        | `Holder<_>`     |
// | [OwnedSocket]        | `Holder<_>`     |
//
// Like raw pointers, `RawHandle` and `RawSocket` values are primitive values.
// And in new code, they should be considered unchecked to do I/O on (analogous
// to dereferencing them). Rust did not always provide this guidance, so
// existing code in the Rust ecosystem often doesn't mark `RawHandle` and
// `RawSocket` usage as unchecked.
// Libraries are encouraged to migrate, either by adding `unchecked` to APIs
// that dereference `RawHandle` and `RawSocket` values, or by using to
// `BorrowedHandle`, `BorrowedSocket`, `OwnedHandle`, or `OwnedSocket`.
//
// Like references, `BorrowedHandle` and `BorrowedSocket` values are tied to a
// scope, to ensure that they don't outlive the resource they point to.
// These are safe to use. `BorrowedHandle` and `BorrowedSocket` values may be
// used in APIs which provide safe access to any system call except for
// `CloseHandle`, `closesocket`, or any other call that would end the
// dynamic scope of the resource without ending the scope of the
// handle or socket.
//
// `BorrowedHandle` and `BorrowedSocket` values may be used in APIs which
// provide safe access to `DuplicateHandle` and `WSADuplicateSocketW` and
// related functions, so types implementing `AsHandle`, `AsSocket`,
// `From<OwnedHandle>`, or `From<OwnedSocket>` should not assume they always
// have exclusive access to the underlying object.
//
// Like boxes, `OwnedHandle` and `OwnedSocket` values conceptually own the
// resource they point to, and free (close) it when they are dropped.
//
// See the [`io` module docs][io-safety] for a general explanation of I/O safety.

