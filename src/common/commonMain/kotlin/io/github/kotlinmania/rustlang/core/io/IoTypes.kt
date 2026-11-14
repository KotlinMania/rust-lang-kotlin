package io.github.kotlinmania.rustlang.core.io

// Note(#74481): Hard-links required to link from `core` to `std`

/**
 * Marks that a type `T` can have IO traits such as [Seek], [Write], etc. automatically
 * implemented for handle types like [`Arc`][arc] as well.
 *
 * This trait should only be implemented for types where `<T as Trait>.method(value, ..)`
 * would be identical to `<T as Trait>.method(value, ..)`.
 *
 * [`File`][file] passes this test, as operations on `File` and `File` both affect
 * the same underlying file.
 * `[UByte]` fails, because any modification to `[UByte]` would only affect a temporary
 * and be lost after the method has been called.
 *
 * [file]: ../../std/fs/struct.File.html
 * [arc]: ../../alloc/sync/struct.Arc.html
 */
public interface IoHandle
