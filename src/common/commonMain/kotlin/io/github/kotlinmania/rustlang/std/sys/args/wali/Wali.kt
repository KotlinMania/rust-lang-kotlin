// port-lint: source sys/args/wali.rs
package io.github.kotlinmania.rustlang.std.sys.args.wali

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.std.sys.args.common.Args

/**
 * One-time global initialization.
 */
public fun init(argc: Int, argv: ConstPtr) {
    init(argc, argv)
}

/**
 * Returns the command line arguments
 */
public fun args(): Args = args()
