// port-lint: source os/freebsd/fs.rs
package io.github.kotlinmania.rustlang.std.os.freebsd.fs

import io.github.kotlinmania.rustlang.std.fs.Metadata
import io.github.kotlinmania.rustlang.std.sys.sync.mutex.windows7.raw

/**
 * OS-specific extensions to [fs.Metadata].
 */
public interface MetadataExt {
    /**
     * Gain a reference to the underlying `stat` structure which contains
     * the raw information returned by the OS.
     *
     * The contents of the returned `stat` are **not** consistent across
     * Unix platforms. The `os.unix.fs.MetadataExt` trait contains the
     * cross-Unix abstractions contained within the raw stat.
     */
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

    public fun stBirthtime(): Long

    public fun stBirthtimeNsec(): Long

    public fun stBlksize(): ULong

    public fun stBlocks(): ULong

    public fun stFlags(): UInt

    public fun stGen(): UInt

    public fun stLspare(): UInt
}

public fun Metadata.asRawStat(): raw.Stat {
    // The methods below use libc.stat, so they work fine when libc is built with FreeBSD 12 ABI.
    // This method would just return nonsense.
    error("as_raw_stat not supported with FreeBSD 12 ABI")
}

public fun Metadata.stDev(): ULong = (this.asInner().asInner().stDev).toULong()

public fun Metadata.stIno(): ULong = (this.asInner().asInner().stIno).toULong()

public fun Metadata.stMode(): UInt = (this.asInner().asInner().stMode).toUInt()

public fun Metadata.stNlink(): ULong = (this.asInner().asInner().stNlink).toULong()

public fun Metadata.stUid(): UInt = (this.asInner().asInner().stUid).toUInt()

public fun Metadata.stGid(): UInt = (this.asInner().asInner().stGid).toUInt()

public fun Metadata.stRdev(): ULong = (this.asInner().asInner().stRdev).toULong()

public fun Metadata.stSize(): ULong = (this.asInner().asInner().stSize).toULong()

public fun Metadata.stAtime(): Long = (this.asInner().asInner().stAtime).toLong()

public fun Metadata.stAtimeNsec(): Long = (this.asInner().asInner().stAtimeNsec).toLong()

public fun Metadata.stMtime(): Long = (this.asInner().asInner().stMtime).toLong()

public fun Metadata.stMtimeNsec(): Long = (this.asInner().asInner().stMtimeNsec).toLong()

public fun Metadata.stCtime(): Long = (this.asInner().asInner().stCtime).toLong()

public fun Metadata.stCtimeNsec(): Long = (this.asInner().asInner().stCtimeNsec).toLong()

public fun Metadata.stBirthtime(): Long = (this.asInner().asInner().stBirthtime).toLong()

public fun Metadata.stBirthtimeNsec(): Long = (this.asInner().asInner().stBirthtimeNsec).toLong()

public fun Metadata.stBlksize(): ULong = (this.asInner().asInner().stBlksize).toULong()

public fun Metadata.stBlocks(): ULong = (this.asInner().asInner().stBlocks).toULong()

public fun Metadata.stGen(): UInt = (this.asInner().asInner().stGen).toUInt()

public fun Metadata.stFlags(): UInt = (this.asInner().asInner().stFlags).toUInt()

public fun Metadata.stLspare(): UInt {
    error("st_lspare not supported with FreeBSD 12 ABI")
}
