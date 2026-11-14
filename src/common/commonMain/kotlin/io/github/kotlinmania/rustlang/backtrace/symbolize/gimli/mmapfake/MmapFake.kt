// port-lint: source symbolize/gimli/mmap_fake.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmapfake

import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.File
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.io.SeekFrom

public open class Mmap(
    public var vec: List<UByte>,
) {
    public open fun deref(): List<UByte> = this.vec

    public companion object {
        public fun map(file: File, len: Int, offset: ULong): Mmap? {
            val mmap = Mmap(vec = ArrayList(len))
            file.seek(SeekFrom.Start(offset))
            (file.readToEnd(mmap.vec).ok() ?: return null)
            return mmap
        }
    }
}
