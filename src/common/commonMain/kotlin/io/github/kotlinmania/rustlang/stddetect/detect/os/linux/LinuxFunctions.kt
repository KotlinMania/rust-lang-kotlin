package io.github.kotlinmania.rustlang.stddetect.detect.os.linux

import io.github.kotlinmania.libc.close
import io.github.kotlinmania.libc.fuchsia.O_RDONLY
import io.github.kotlinmania.libc.read
import io.github.kotlinmania.libc.unix.bsd.netbsdlike.netbsd.open
import io.github.kotlinmania.rustlang.core.ffi.CStr
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

public fun readFile(origPath: CStr): Result<List<UByte>, String> {
    return run {
        val file = open(origPath.asPtr(), O_RDONLY)
        if (file == -1) {
            return Result.Err("Cannot open file at ${origPath.display()}")
        }
        val data = mutableListOf<UByte>()
        while (true) {
            data.reserve(4096)
            val spare = data.spareCapacityMut()
            when (val matchVal = read(file, mutPtrFromRustCast(spare.asMutPtr()), spare.len())) {
                -1 -> {
                    close(file)
                    return Result.Err("Error while reading from file at ${origPath.display()}")
                }
                0 -> { break }
                else -> {
                    val n = matchVal
                    data.setLen(data.size + n.toInt())
                }
            }
        }
        close(file)
        Result.Ok(data)
    }
}
