// port-lint: source sys/args/motor.rs
package io.github.kotlinmania.rustlang.std.sys.args.motor

import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sys.args.common.Args

public fun args(): Args {
    val motorArgs: List<String> = io.github.kotlinmania.motort.process.args()
    val rustArgs = mutableListOf()
    for (arg in motorArgs) {
        rustArgs.add(OsString.from(arg))
    }
    return Args.new(rustArgs)
}
