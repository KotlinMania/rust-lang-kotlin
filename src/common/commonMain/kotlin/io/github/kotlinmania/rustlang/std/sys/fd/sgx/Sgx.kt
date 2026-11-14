// port-lint: source sys/fd/sgx.rs
package io.github.kotlinmania.rustlang.std.sys.fd.sgx

import io.github.kotlinmania.fortanixsgxabi.Fd
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.pal.abi.usercalls
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.close
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.read
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.write
import io.github.kotlinmania.rustlang.std.thread.Result

public open class FileDesc(
    public var fd: Fd,
) : AsInner<Fd>, IntoInner<Fd>, FromInner<Fd, FileDesc> {
    public open fun raw(): Fd = this.fd

    /**
     * Extracts the actual file descriptor without closing it.
     */
    public open fun intoRaw(): Fd = this.fd

    public open fun read(buf: MutableList<UByte>): Result<Int> = io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.read(this.fd, listOf(IoSliceMut.new(buf)))

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.readBuf(this.fd, buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = read(this.fd, bufs)

    public open fun isReadVectored(): Boolean = true

    public open fun write(buf: List<UByte>): Result<Int> = io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.write(this.fd, listOf(IoSlice.new(buf)))

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = write(this.fd, bufs)

    public open fun isWriteVectored(): Boolean = true

    public open fun flush(): Result<Unit> = io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.usercalls.flush(this.fd)

    public override fun asInner(): Fd = this.fd

    public override fun intoInner(): Fd = this.fd

    public open fun drop() {
        close(this.fd)
    }

    public companion object {
        public fun new(fd: Fd): FileDesc = FileDesc(fd = fd)

        public fun fromInner(fd: Fd): FileDesc = FileDesc(fd = fd)
    }
}
