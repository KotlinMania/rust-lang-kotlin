// port-lint: source sys/pal/sgx/abi/panic.rs
package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.panic

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.mem
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.alloc.UserRef
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.write

public expect fun takeDebugPanicBufPtr(): MutPtr

public expect val DEBUG: UByte

internal open class SgxPanicOutput(
    public var value: UserRef<List<UByte>>?,
) : Write {
    public var field0: UserRef<List<UByte>>?
        get() = value
        set(v) {
            value = v
        }

    public open fun init(): UserRef<List<UByte>> =
        this.value.getOrInsertWith({ run {
            val ptr = takeDebugPanicBufPtr()
            if (((ptr).rawAddress == 0L)) {
                emptyUserSlice()
            } else {
                UserRef.fromRawPartsMut(ptr, 1024)
            }
        } })

    public open fun write(src: List<UByte>): Result<Int> {
        val dst = mem.replace(this.init(), emptyUserSlice())
        val written = minOf(src.size, dst.len())
        dst[0 until written].copyFromEnclave(((src).slice(0 until written)))
        this.value = dst[written until dst.size]
        return Result.Ok(written)
    }

    public open fun flush(): Result<Unit> = Result.Ok(Unit)

    public companion object {
        internal fun new(): SgxPanicOutput? =
            if (DEBUG == 0u.toUByte()) {
                null
            } else {
                SgxPanicOutput(null)
            }
    }
}

public fun emptyUserSlice(): UserRef<List<UByte>> = UserRef.fromRawPartsMut(MutPtr(1.toLong()), 0)
