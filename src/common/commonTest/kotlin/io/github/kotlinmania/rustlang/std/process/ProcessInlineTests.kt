// port-lint: tests process.rs
package io.github.kotlinmania.rustlang.std.process

// A module for working with processes.
//
// This module is mostly concerned with spawning and interacting with child
// processes, but it also provides [abort] and [exit] for terminating the
// current process.
//
// # Spawning a process
//
// The [Command] struct is used to configure and spawn processes:
//
// ```kotlin
// import io.prelude.*
//
// val output = Command.new("echo").arg("Hello world").output().expect("echo command should execute successfully")
// check("Hello world\n".encodeToByteArray() == output.stdout.asSlice())
// ```
//
// Several methods on [Command], such as [spawn] or [output], can be used
// to spawn a process. In particular, [output] spawns the child process and
// waits until the process terminates, while [spawn] will return a [Child]
// that represents the spawned child process.
//
// # Handling I/O
//
// The [stdout], [stdin], and [stderr] of a child process can be
// configured by passing an [Stdio] to the corresponding method on
// [Command]. Once spawned, they can be accessed from the [Child]. For
// example, piping output from one command into another command can be done
// like so:
//
// ```kotlin
// import io.prelude.*
//
// // stdout must be configured with `Stdio.piped` in order to use
// // `echoChild.stdout`
// val echoChild = Command.new("echo").arg("Oh no, a tpyo!").stdout(Stdio.piped()).spawn().expect("echo command should start")
// // Note that `echoChild` is moved here, but we won't be needing
// // `echoChild` anymore
// val echoOut = echoChild.stdout.expect("child stdout should open")
// var sedChild = Command.new("sed").arg("s/tpyo/typo/").stdin(Stdio.from(echoOut)).stdout(Stdio.piped()).spawn().expect("sed command should start")
// val output = sedChild.waitWithOutput().expect("waitWithOutput on sed should succeed")
// check("Oh no, a typo!\n".encodeToByteArray() == output.stdout.asSlice())
// ```
//
// Note that [ChildStderr] and [ChildStdout] implement [Read] and
// [ChildStdin] implements [Write]:
//
// ```kotlin
// import io.prelude.*
//
// var child = Command.new("/bin/cat").stdin(Stdio.piped()).stdout(Stdio.piped()).spawn().expect("child should start")
// // If the child process fills its stdout buffer, it may end up
// // waiting until the parent reads the stdout, and not be able to
// // read stdin in the meantime, causing a deadlock.
// // Writing from another thread ensures that stdout is being read
// // at the same time, avoiding the problem.
// var stdin = (child.stdin.also { child.stdin = null } ?: error("stdin should be able to be retrieved"))
// spawn({
//     stdin.writeAll("test".encodeToByteArray()).expect("writing to stdin should succeed")
// })
// val output = child.waitWithOutput().expect("waitWithOutput on child should succeed")
// check("test".encodeToByteArray() == output.stdout.asSlice())
// ```
//
// # Windows argument splitting
//
// On Unix systems arguments are passed to a new process as an array of strings,
// but on Windows arguments are passed as a single commandline string and it is
// up to the child process to parse it into an array. Therefore the parent and
// child processes must agree on how the commandline string is encoded.
//
// Most programs use the standard C run-time `argv`, which in practice results
// in consistent argument handling. However, some programs have their own way of
// parsing the commandline string. In these cases using [arg] or [args] may
// result in the child process seeing a different array of arguments than the
// parent process intended.
//
// Two ways of mitigating this are:
//
// * Validate untrusted input so that only a safe subset is allowed.
// * Use [rawArg] to build a custom commandline. This bypasses the escaping
//   rules used by [arg] so should be used with due caution.
//
// `cmd.exe` and `.bat` files use non-standard argument parsing and are especially
// vulnerable to malicious input as they may be used to run arbitrary shell
// commands. Untrusted arguments should be restricted as much as possible.
// For examples on handling this see [rawArg].
//
// ### Batch file special handling
//
// On Windows, `Command` uses the Windows API function [CreateProcessW] to
// spawn new processes. An undocumented feature of this function is that
// when given a `.bat` file as the application to run, it will automatically
// convert that into running `cmd.exe / c` with the batch file as the next argument.
//
// For historical reasons Rust currently preserves this behavior when using
// [Command.new], and escapes the arguments according to `cmd.exe` rules.
// Due to the complexity of `cmd.exe` argument handling, it might not be
// possible to safely escape some special characters, and using them will result
// in an error being returned at process spawn. The set of unescapeable
// special characters might change between releases.
//
// Also note that running batch scripts in this way may be removed in the
// future and so should not be relied upon.
//
// [spawn]: Command.spawn
// [output]: Command.output
//
// [stdout]: Command.stdout
// [stdin]: Command.stdin
// [stderr]: Command.stderr
//
// [Write]: io.Write
// [Read]: io.Read
//
// [arg]: Command.arg
// [args]: Command.args
//
// [CreateProcessW]: https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-createprocessw

