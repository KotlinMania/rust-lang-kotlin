package io.github.kotlinmania.rustlang.std.sys.process

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.Command
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.readOutput
import io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatus

public fun output(cmd: Command): Result<Triple<ExitStatus, List<UByte>, List<UByte>>, Error> {
    var (process, pipes) = when (val tryResult = cmd.spawn(Stdio.MakePipe, false)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Triple<ExitStatus, List<UByte>, List<UByte>>, Error>(tryResult.errValue()) }
    (pipes.stdin.also { pipes.stdin = null }).let { }
    var (stdout, stderr) = Pair(mutableListOf(), mutableListOf())
    run {
        val matchVal = Pair(pipes.stdout.also { pipes.stdout = null }, pipes.stderr.also { pipes.stderr = null })
        when {
            matchVal.first == null && matchVal.second == null -> {}
            matchVal.first != null && matchVal.second == null -> {
                val out = matchVal.first
                val res = out.readToEnd(stdout)
                res.unwrap()
            }
            matchVal.first == null && matchVal.second != null -> {
                val err = matchVal.second
                val res1 = err.readToEnd(stderr)
                res1.unwrap()
            }
            else -> {
                val out1 = matchVal.first
                val err1 = matchVal.second
                val res2 = readOutput(out1, stdout, err1, stderr)
                res2.unwrap()
            }
        }
    }
    val status = when (val tryResult = process.wait()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Triple<ExitStatus, List<UByte>, List<UByte>>, Error>(tryResult.errValue()) }
    return Result.Ok<Triple<ExitStatus, List<UByte>, List<UByte>>, Error>(Triple(status, stdout, stderr))
}

public fun status(cmd: Command): Result<ExitStatus, Error> {
    var (process, pipes) = when (val tryResult = cmd.spawn(Stdio.Inherit, true)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ExitStatus, Error>(tryResult.errValue()) }
    (pipes.stdin.also { pipes.stdin = null }).let { }
    return process.wait()
}
