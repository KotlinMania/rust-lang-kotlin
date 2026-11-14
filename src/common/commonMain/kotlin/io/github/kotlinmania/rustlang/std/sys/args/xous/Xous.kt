// port-lint: source sys/args/xous.rs
package io.github.kotlinmania.rustlang.std.sys.args.xous

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sys.args.common.Args
import io.github.kotlinmania.rustlang.std.sys.pal.params
import io.github.kotlinmania.rustlang.std.sys.pal.xous.params.ArgumentList
import io.github.kotlinmania.rustlang.std.sys.pal.xous.params.get

public fun args(): Args {
    val params = get() ?: return Args.new(mutableListOf())
    for (param in params) {
        when (val matchVal = ArgumentList.tryFrom(param)) {
            is Ok -> {
                val args = matchVal.value
                val parsedArgs = mutableListOf()
                for (arg in args) {
                    parsedArgs.add(arg.into())
                }
                return Args.new(parsedArgs)
            }
            else -> {}
        }
    }
    return Args.new(mutableListOf())
}
