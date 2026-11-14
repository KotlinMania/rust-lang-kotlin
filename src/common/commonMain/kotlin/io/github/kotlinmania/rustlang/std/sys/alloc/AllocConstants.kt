package io.github.kotlinmania.rustlang.std.sys.alloc

// The minimum alignment guaranteed by the architecture. This value is used to
// add fast paths for low alignment values.
public val MIN_ALIGN: Int =
    if (false) {
        // The allocator on the esp-idf and zkvm platforms guarantees 4 byte alignment.
        4
    } else if (false) {
        8
    } else if (false) {
        16
    } else {
        error("add a value for MIN_ALIGN")
    }
