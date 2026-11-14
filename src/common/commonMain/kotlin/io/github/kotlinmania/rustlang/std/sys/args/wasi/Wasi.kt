// port-lint: source sys/args/wasi.rs
package io.github.kotlinmania.rustlang.std.sys.args.wasi

import io.github.kotlinmania.rustlang.std.sys.args.common.Args

/**
 * Returns the command line arguments
 */
public fun args(): Args = Args.new(wasi.getArguments().intoIter().map({ arg -> arg.into() }).collect())
