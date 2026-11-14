// port-lint: source os/espidf/fs.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.os.espidf.fs

import io.github.kotlinmania.libc.hermit.Stat
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.std.fs.Metadata
import io.github.kotlinmania.rustlang.std.sys.sync.mutex.windows7.raw

/**
 * OS-specific extensions to [fs.Metadata].
 */
public interface MetadataExt {
    public fun asRawStat(): raw.Stat

    public fun stDev(): ULong

    public fun stIno(): ULong

    public fun stMode(): UInt

    public fun stNlink(): ULong

    public fun stUid(): UInt

    public fun stGid(): UInt

    public fun stRdev(): ULong

    public fun stSize(): ULong

    public fun stAtime(): Long

    public fun stAtimeNsec(): Long

    public fun stMtime(): Long

    public fun stMtimeNsec(): Long

    public fun stCtime(): Long

    public fun stCtimeNsec(): Long

    public fun stBlksize(): ULong

    public fun stBlocks(): ULong

    public fun stSpare4(): UIntArray
}

public fun Metadata.asRawStat(): raw.Stat = (ObjectSlotLayout<raw.Stat>()).readNative((((constPtrFromRustCast(this.asInner().asInner())).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

public fun Metadata.stDev(): ULong = (this.asInner().asInner().stDev).toULong()

public fun Metadata.stIno(): ULong = (this.asInner().asInner().stIno).toULong()

public fun Metadata.stMode(): UInt = (this.asInner().asInner().stMode).toUInt()

public fun Metadata.stNlink(): ULong = (this.asInner().asInner().stNlink).toULong()

public fun Metadata.stUid(): UInt = (this.asInner().asInner().stUid).toUInt()

public fun Metadata.stGid(): UInt = (this.asInner().asInner().stGid).toUInt()

public fun Metadata.stRdev(): ULong = (this.asInner().asInner().stRdev).toULong()

public fun Metadata.stSize(): ULong = (this.asInner().asInner().stSize).toULong()

public fun Metadata.stAtime(): Long = (this.asInner().asInner().stAtime).toLong()

public fun Metadata.stAtimeNsec(): Long = 0L

public fun Metadata.stMtime(): Long = (this.asInner().asInner().stMtime).toLong()

public fun Metadata.stMtimeNsec(): Long = 0L

public fun Metadata.stCtime(): Long = (this.asInner().asInner().stCtime).toLong()

public fun Metadata.stCtimeNsec(): Long = 0L

public fun Metadata.stBlksize(): ULong = (this.asInner().asInner().stBlksize).toULong()

public fun Metadata.stBlocks(): ULong = (this.asInner().asInner().stBlocks).toULong()

public fun Metadata.stSpare4(): UIntArray {
    val spare4 = this.asInner().asInner().stSpare4
    return uintArrayOf(spare4[0].toUInt(), spare4[1].toUInt())
}
