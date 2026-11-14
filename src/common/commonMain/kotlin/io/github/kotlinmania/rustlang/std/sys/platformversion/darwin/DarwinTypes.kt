package io.github.kotlinmania.rustlang.std.sys.platformversion.darwin

/**
 * The version of the operating system.
 *
 * We use a packed u32 here to allow for fast comparisons and to match Mach-O's `LC_BUILD_VERSION`.
 */
public typealias OSVersion = UInt
