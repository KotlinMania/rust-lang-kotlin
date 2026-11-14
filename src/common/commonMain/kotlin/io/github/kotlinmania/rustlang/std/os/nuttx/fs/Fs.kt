// port-lint: source os/nuttx/fs.rs
package io.github.kotlinmania.rustlang.std.os.nuttx.fs

import io.github.kotlinmania.rustlang.std.fs.Metadata

public interface MetadataExt {
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
}

public fun Metadata.stDev(): ULong = (this.asInner().asInner().stDev).toULong()

public fun Metadata.stIno(): ULong = (this.asInner().asInner().stIno).toULong()

public fun Metadata.stMode(): UInt = (this.asInner().asInner().stMode).toUInt()

public fun Metadata.stNlink(): ULong = (this.asInner().asInner().stNlink).toULong()

public fun Metadata.stUid(): UInt = (this.asInner().asInner().stUid).toUInt()

public fun Metadata.stGid(): UInt = (this.asInner().asInner().stGid).toUInt()

public fun Metadata.stRdev(): ULong = (this.asInner().asInner().stRdev).toULong()

public fun Metadata.stSize(): ULong = (this.asInner().asInner().stSize).toULong()

public fun Metadata.stAtime(): Long = (this.asInner().asInner().stAtim.tvSec).toLong()

public fun Metadata.stAtimeNsec(): Long = (this.asInner().asInner().stAtim.tvNsec).toLong()

public fun Metadata.stMtime(): Long = (this.asInner().asInner().stMtim.tvSec).toLong()

public fun Metadata.stMtimeNsec(): Long = (this.asInner().asInner().stMtim.tvNsec).toLong()

public fun Metadata.stCtime(): Long = (this.asInner().asInner().stCtim.tvSec).toLong()

public fun Metadata.stCtimeNsec(): Long = (this.asInner().asInner().stCtim.tvNsec).toLong()

public fun Metadata.stBlksize(): ULong = (this.asInner().asInner().stBlksize).toULong()

public fun Metadata.stBlocks(): ULong = (this.asInner().asInner().stBlocks).toULong()
