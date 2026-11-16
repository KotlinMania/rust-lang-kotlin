package io.github.kotlinmania.rustlang.getopts

import io.github.kotlinmania.rustlang.getopts.Fail.*
import io.github.kotlinmania.rustlang.getopts.HasArg.*
import io.github.kotlinmania.rustlang.getopts.Name.*
import io.github.kotlinmania.rustlang.getopts.Occur.*
import io.github.kotlinmania.rustlang.getopts.Optval.*

// Simple getopt alternative.
//
// Construct instance of `Options` and configure it by using  `reqopt()`,
// `optopt()` and other methods that add option configuration. Then call
// `parse()` method and pass into it a vector of actual arguments (not
// including `argv[0]`).
//
// You'll either get a failure code back, or a match. You'll have to verify
// whether the amount of 'free' arguments in the match is what you expect. Use
// `opt_ *` accessors to get argument values out of the matches object.
//
// Single-character options are expected to appear on the command line with a
// single preceding dash; multiple-character options are expected to be
// proceeded by two dashes. Options that expect an argument accept their
// argument following either a space or an equals sign. Single-character
// options don't require the space. Everything after double-dash "--"  argument
// is considered to be a 'free' argument, even if it starts with dash.
//
// # Usage
//
// This crate is [on crates.io](https://crates.io/crates/getopts) and can be
// used by adding `getopts` to the dependencies in your project's `Cargo.toml`.
//
// ```toml
// [dependencies]
// getopts = "0.2"
// ```
//
// and this to your crate root:
//
// ```kotlin
// extern crate getopts;
// ```
//
// # Example
//
// The following example shows simple command line parsing for an application
// that requires an input file to be specified, accepts an optional output file
// name following `- o`, and accepts both `- h` and `- - help` as optional flags.
//
// ```kotlin
// fun doWork(inp: String, out: String?) {
//     println("$inp")
//     when (out) {
//         null -> println("No Output")
//         else -> {
//             val x = out
//             println("$x")
//         }
//     }
// }
// fun printUsage(program: String, opts: Options) {
//     val brief = "Usage: $program FILE [options]"
//     print("${opts.usage(brief)}")
// }
// fun main() {
//     val args: List<String> = args().toMutableList()
//     val program = args[0].clone()
//     var opts = Options.new()
//     opts.optopt("o", "", "set output file name", "NAME")
//     opts.optflag("h", "help", "print this help menu")
//     val matches = run {
//         val matchVal = opts.parse(((args).subList(1, (args).size)))
//         if (matchVal.isSuccess) {
//             val m = matchVal.getOrThrow()
//             m
//         } else {
//             val f = matchVal.exceptionOrNull()!!
//             error("${f.toString()}")
//         }
//     }
//     if (matches.optPresent("h")) {
//         printUsage(program, opts)
//         return
//     }
//     val output = matches.optStr("o")
//     val input = if (!matches.free.isEmpty()) {
//         matches.free[0].clone()
//     } else {
//         printUsage(program, opts)
//         return
//     }
//     doWork(input, output)
// }
// ```

