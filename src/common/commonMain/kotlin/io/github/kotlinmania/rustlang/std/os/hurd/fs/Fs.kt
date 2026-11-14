// port-lint: source os/hurd/fs.rs
package io.github.kotlinmania.rustlang.std.os.hurd.fs

import io.github.kotlinmania.rustlang.std.fs.Metadata

// Hurd-specific extensions to primitives in the [std.fs] module.

/**
 * OS-specific extensions to [fs.Metadata].
 */
public interface MetadataExt {
    /**
     * Returns the device ID on which this file resides.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stDev());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stDev(): ULong

    /**
     * Returns the inode number.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stIno());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stIno(): ULong

    /**
     * Returns the file type and mode.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stMode());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stMode(): UInt

    /**
     * Returns the number of hard links to file.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stNlink());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stNlink(): ULong

    /**
     * Returns the user ID of the file owner.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stUid());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stUid(): UInt

    /**
     * Returns the group ID of the file owner.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stGid());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stGid(): UInt

    /**
     * Returns the device ID that this file represents. Only relevant for special file.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stRdev());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stRdev(): ULong

    /**
     * Returns the size of the file (if it is a regular file or a symbolic link) in bytes.
     *
     * The size of a symbolic link is the length of the pathname it contains,
     * without a terminating null byte.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stSize());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stSize(): ULong

    /**
     * Returns the last access time of the file, in seconds since Unix Epoch.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stAtime());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stAtime(): Long

    /**
     * Returns the last access time of the file, in nanoseconds since [stAtime].
     *
     * [stAtime]: Self.stAtime
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stAtimeNsec());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stAtimeNsec(): Long

    /**
     * Returns the last modification time of the file, in seconds since Unix Epoch.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stMtime());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stMtime(): Long

    /**
     * Returns the last modification time of the file, in nanoseconds since [stMtime].
     *
     * [stMtime]: Self.stMtime
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stMtimeNsec());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stMtimeNsec(): Long

    /**
     * Returns the last status change time of the file, in seconds since Unix Epoch.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stCtime());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stCtime(): Long

    /**
     * Returns the last status change time of the file, in nanoseconds since [stCtime].
     *
     * [stCtime]: Self.stCtime
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stCtimeNsec());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stCtimeNsec(): Long

    /**
     * Returns the "preferred" block size for efficient filesystem I/O.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stBlksize());
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun stBlksize(): ULong

    /**
     * Returns the number of blocks allocated to the file, 512-byte units.
     *
     * # Examples
     *
     * use std.fs;
     * use std.io;
     * use std.os.hurd.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> {
     *     let meta = fs.metadata("someFile")?;
     *     println("{}", meta.stBlocks());
     *     Ok(())
     * }
     * ```kotlin
     */
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
