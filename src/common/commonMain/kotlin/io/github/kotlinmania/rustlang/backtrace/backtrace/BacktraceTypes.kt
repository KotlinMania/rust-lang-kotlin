package io.github.kotlinmania.rustlang.backtrace.backtrace

import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicUsize

public var IMAGE_BASE: AtomicUsize = AtomicUsize.new(0)

/**
 * Set the image base address. This is only available for Fortanix SGX
 * target when the `std` feature is not enabled. This can be used in the
 * standard library to set the correct base address.
 */
public fun setImageBase(baseAddr: MutPtr) {
    IMAGE_BASE.store(((baseAddr as Any?) as Unit), SeqCst)
}

internal fun getImageBase(): MutPtr = mutPtrFromRustCast(IMAGE_BASE.load(SeqCst))
