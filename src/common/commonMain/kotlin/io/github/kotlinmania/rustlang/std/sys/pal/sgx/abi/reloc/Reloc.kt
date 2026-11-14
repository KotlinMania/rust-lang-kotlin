// port-lint: source sys/pal/sgx/abi/reloc.rs
package io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.reloc

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.stdio.motor.panicOutput

public val R_X86_64_RELATIVE: UInt = 8u

public open class Rela<T>(
    public var offset: T,
    public var info: T,
    public var addend: T,
) {
    public companion object {
        public fun <T> typeLayout(typeLayoutT: io.github.kotlinmania.rustlang.core.mem.TypeLayout<T>): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Rela<T>> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Rela<T>> {
                override val alignBytes: Long = listOf(1L, minOf(typeLayoutT.alignBytes, 1L)).max()
                private val off1: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes(typeLayoutT.sizeBytes, minOf(typeLayoutT.alignBytes, 1L))
                private val off2: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes((off1 + typeLayoutT.sizeBytes), minOf(typeLayoutT.alignBytes, 1L))
                override val sizeBytes: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes((off2 + typeLayoutT.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Rela<T> =
                    Rela(
                        (typeLayoutT).readNative(addr),
                        (typeLayoutT).readNative(addr + off1),
                        (typeLayoutT).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Rela<T>) {
                    (typeLayoutT).writeNative(addr, value.offset)
                    (typeLayoutT).writeNative(addr + off1, value.info)
                    (typeLayoutT).writeNative(addr + off2, value.addend)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Rela<T> =
                    Rela(
                        (typeLayoutT).readBytes(buf, offset),
                        (typeLayoutT).readBytes(buf, offset + off1.toInt()),
                        (typeLayoutT).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Rela<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.offset)
                    (typeLayoutT).writeBytes(buf, offset + off1.toInt(), value.info)
                    (typeLayoutT).writeBytes(buf, offset + off2.toInt(), value.addend)
                }
            }
    }
}

public fun relocateElfRela() {
    val RELA: ULong = 0u

    val RELACOUNT: ULong = 0u
    if (RELACOUNT == 0) {
        return
    }
    // unchecked ok: link-time constant
    val relas = rawSliceList(mem.relPtr(RELA), RELACOUNT.toInt(), ObjectSlotLayout<T>())
    for (rela in relas) {
        if (rela.info != (R_X86_64_RELATIVE.toULong())) {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("Invalid relocation").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("Invalid relocation").toString() + ", aborting\n"
                }
                abort()
            }
        }
        (ObjectSlotLayout<T>()).writeNative((mem.relPtrMut<ConstPtr>(rela.offset)).rawAddress, mem.relPtr(rela.addend))
    }
}
