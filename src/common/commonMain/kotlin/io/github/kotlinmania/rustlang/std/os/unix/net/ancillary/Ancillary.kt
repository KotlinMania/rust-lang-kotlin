// port-lint: source os/unix/net/ancillary.rs
package io.github.kotlinmania.rustlang.std.os.unix.net.ancillary

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.fuchsia.Cmsghdr
import io.github.kotlinmania.libc.fuchsia.GidT
import io.github.kotlinmania.libc.fuchsia.MSG_CTRUNC
import io.github.kotlinmania.libc.fuchsia.MSG_TRUNC
import io.github.kotlinmania.libc.fuchsia.Msghdr
import io.github.kotlinmania.libc.fuchsia.PidT
import io.github.kotlinmania.libc.fuchsia.SOL_SOCKET
import io.github.kotlinmania.libc.fuchsia.Ucred
import io.github.kotlinmania.libc.fuchsia.UidT
import io.github.kotlinmania.libc.fuchsia.cmsgData
import io.github.kotlinmania.libc.fuchsia.cmsgFirsthdr
import io.github.kotlinmania.libc.fuchsia.cmsgLen
import io.github.kotlinmania.libc.fuchsia.cmsgNxthdr
import io.github.kotlinmania.libc.fuchsia.cmsgSpace
import io.github.kotlinmania.libc.memcpy
import io.github.kotlinmania.libc.unix.bsd.freebsdlike.freebsd.SCM_CREDS2
import io.github.kotlinmania.libc.unix.bsd.freebsdlike.freebsd.sockcred2size
import io.github.kotlinmania.libc.unix.hurd.SCM_CREDS
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.rustCheckedAdd
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.eq
import io.github.kotlinmania.rustlang.core.ptr.readUnaligned
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.net.connection.motor.Socket
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SockaddrUn

internal fun recvVectoredWithAncillaryFrom(socket: Socket, bufs: MutableList<IoSliceMut>, ancillary: SocketAncillary): Result<Triple<Int, Boolean, Result<SocketAddr>>> {
    return run {
        val localStorageL41C8 = AddressableLocal(null, ObjectSlotLayout<SockaddrUn>())
        val msgName by localStorageL41C8
        val msg: Msghdr?  = null
        msg!!.msgName = ((localStorageL41C8.asMutPtr())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        msg.msgNamelen = sizeOf<SockaddrUn>(typeLayoutT = ObjectSlotLayout<SockaddrUn>()).toInt()
        msg.msgIov = MutPtr((bufs.asMutPtr()).rawAddress)
        msg.msgIovlen = bufs.size
        msg.msgControllen = ancillary.buffer.size
        // macos requires that the control pointer is null when the len is 0.
        if (msg.msgControllen > 0) {
            msg.msgControl = MutPtr((ancillary.buffer.asMutPtr()).rawAddress)
        }
        val count = when (val tryResult = socket.recvMsg(msg)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        ancillary.length = msg.msgControllen
        ancillary.truncated = (msg.msgFlags and MSG_CTRUNC == MSG_CTRUNC).toUByte()
        val truncated = msg.msgFlags and MSG_TRUNC == MSG_TRUNC
        val addr = SocketAddr.fromParts(msgName, msg.msgNamelen)
        Result.Ok(Triple(count, truncated, addr))
    }
}

internal fun sendVectoredWithAncillaryTo(socket: Socket, path: Path?, bufs: List<IoSlice>, ancillary: SocketAncillary): Result<Int> {
    return run {
        var (msgName, msgNamelen) =
            if (path != null) {
                val path1 = path
                when (val tryResult = io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SockaddrUn(path1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            } else {
                Pair(null, 0)
            }
        val msg: Msghdr?  = null
        msg!!.msgName = mutPtrFromRustCast((addrOfMut(msgName)))
        msg.msgNamelen = msgNamelen
        msg.msgIov = (bufs.asPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        msg.msgIovlen = bufs.size
        msg.msgControllen = ancillary.length
        // macos requires that the control pointer is null when the len is 0.
        if (msg.msgControllen > 0) {
            msg.msgControl = MutPtr((ancillary.buffer.asMutPtr()).rawAddress)
        }
        ancillary.truncated = false
        socket.sendMsg(msg)
    }
}

public fun <T> addToAncillaryData(buffer: MutableList<UByte>, length: RustMutableReference<Int>, source: List<T>, cmsgLevel: CInt, cmsgType: CInt, typeLayoutT: TypeLayout<T>): Boolean {
    val length1 = length
    val cmsgSize = sockcred2size(1)
    val sourceLen2 =
        if (cmsgSize != null) {
            val sourceLen = cmsgSize
            when (val matchVal = UInt.tryFrom(sourceLen)) {
                is Ok -> {
                    val sourceLen1 = matchVal.value
                    sourceLen1
                }
                else -> {
                    return false
                }
            }
        } else {
            return false
        }
    run {
        val additionalSpace = cmsgSpace(sourceLen2).toInt()
        val newLength1 =
            when (val matchVal1 = rustCheckedAdd(additionalSpace, (length1).read())) {
                null -> {
                    return false
                }
                else -> {
                    val newLength = matchVal1
                    newLength
                }
            }
        if (newLength1 > buffer.size) {
            return false
        }
        ((buffer).slice((length1).read() until newLength1)).fill(0)
        (length1).write(newLength1)
        val msg: Msghdr?  = null
        msg!!.msgControl = MutPtr((buffer.asMutPtr()).rawAddress)
        msg.msgControllen = (length1).read()
        var cmsg = cmsgFirsthdr(msg)
        var previousCmsg = cmsg
        while (!cmsg.isNull()) {
            previousCmsg = cmsg
            cmsg = cmsgNxthdr(msg, cmsg)
            // Most operating systems, but not Linux or emscripten, return the previous pointer
            // when its length is zero. Therefore, check if the previous pointer is the same as
            // the current one.
            if (eq(cmsg, previousCmsg)) {
                break
            }
        }
        if (previousCmsg.isNull()) {
            return false
        }
        previousCmsg.cmsgLevel = cmsgLevel
        previousCmsg.cmsgType = cmsgType
        previousCmsg.cmsgLen = cmsgLen(sourceLen2).toUByte()
        val data = cmsgData(previousCmsg).cast()
        memcpy(data, source.asPtr(), sourceLen2.toInt())
    }
    return true
}

public open class AncillaryDataIter<T>(
    public var data: List<UByte>,
    public var phantom: Unit,
) : Iterable<T> {
    override fun iterator(): kotlin.collections.Iterator<T> = object : AbstractIterator<T>() {
        override fun computeNext() {
            when (val v = this@AncillaryDataIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(typeLayoutT: TypeLayout<T>): T? =
        if (sizeOf<T>(typeLayoutT = typeLayoutT) <= this.data.size) {
            run {
                val unit = readUnaligned(this.data.asPtr())
                this.data = ((this.data).slice(sizeOf<T>(typeLayoutT = typeLayoutT) until this.data.size))
                unit
            }
        } else {
            null
        }

    public companion object {
        /**
         * Creates `AncillaryDataIter` struct to iterate through the data unit in the control message.
         *
         * # Safety
         *
         * `data` must contain a valid control message.
         */
        public fun <T> new(data: List<UByte>): AncillaryDataIter<T> = AncillaryDataIter(data = data, phantom = Unit)
    }
}

/**
 * Unix credential.
 */
public open class SocketCred(
    public var value: Ucred,
) {
    public var field0: Ucred
        get() = value
        set(v) {
            value = v
        }

    /**
     * Set the PID.
     */
    public open fun setPid(pid: PidT) {
        this.value.pid = pid
    }

    /**
     * Gets the current PID.
     */
    public open fun getPid(): PidT = this.value.pid

    /**
     * Set the UID.
     */
    public open fun setUid(uid: UidT) {
        this.value.uid = uid
    }

    /**
     * Gets the current UID.
     */
    public open fun getUid(): UidT = this.value.uid

    /**
     * Set the GID.
     */
    public open fun setGid(gid: GidT) {
        this.value.gid = gid
    }

    /**
     * Gets the current GID.
     */
    public open fun getGid(): GidT = this.value.gid

    public companion object {
        /**
         * Creates a Unix credential struct.
         *
         * PID, UID and GID is set to 0.
         */
        public fun new(): SocketCred = SocketCred(Ucred(pid = 0, uid = 0, gid = 0))
    }
}

/**
 * This control message contains file descriptors.
 *
 * The level is equal to `SOL_SOCKET` and the type is equal to `SCM_RIGHTS`.
 */
public open class ScmRights internal constructor(
    valueCtorArg: AncillaryDataIter<RawFd>,
) : Iterable<RawFd> {
    public var value: AncillaryDataIter<RawFd> = valueCtorArg
        internal set

    public var field0: AncillaryDataIter<RawFd>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): kotlin.collections.Iterator<RawFd> = object : AbstractIterator<RawFd>() {
        override fun computeNext() {
            when (val v = this@ScmRights.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): RawFd? = this.value.next()

    public companion object {
        public operator fun invoke(
            value: AncillaryDataIter<RawFd>,
        ): ScmRights = ScmRights(
            valueCtorArg = value,
        )
    }
}

/**
 * This control message contains unix credentials.
 *
 * The level is equal to `SOL_SOCKET` and the type is equal to `SCM_CREDENTIALS` or `SCM_CREDS`.
 */
public open class ScmCredentials internal constructor(
    valueCtorArg: AncillaryDataIter<Ucred>,
) : Iterable<SocketCred> {
    public var value: AncillaryDataIter<Ucred> = valueCtorArg
        internal set

    public var field0: AncillaryDataIter<Ucred>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): kotlin.collections.Iterator<SocketCred> = object : AbstractIterator<SocketCred>() {
        override fun computeNext() {
            when (val v = this@ScmCredentials.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): SocketCred? {
        return SocketCred((this.value.next() ?: return null))
    }

    public companion object {
        public operator fun invoke(
            value: AncillaryDataIter<Ucred>,
        ): ScmCredentials = ScmCredentials(
            valueCtorArg = value,
        )
    }
}

/**
 * The error type which is returned from parsing the type a control message.
 */
public sealed class AncillaryError {
    public data class Unknown(public var cmsgLevel: Int, public var cmsgType: Int) : AncillaryError()
}

/**
 * This enum represent one control message of variable type.
 */
public sealed class AncillaryData {
    public data class ScmRights(public var value: ScmRights) : AncillaryData()
    public data class ScmCredentials(public var value: ScmCredentials) : AncillaryData()

    public companion object {
        /**
         * Creates an `AncillaryData.ScmRights` variant.
         *
         * # Safety
         *
         * `data` must contain a valid control message and the control message must be type of
         * `SOL_SOCKET` and level of `SCM_RIGHTS`.
         */
        public fun asRights(data: List<UByte>): AncillaryData {
            val ancillaryDataIter = AncillaryDataIter.new(data)
            val scmRights = ScmRights(ancillaryDataIter)
            return AncillaryData.ScmRights(scmRights)
        }

        /**
         * Creates an `AncillaryData.ScmCredentials` variant.
         *
         * # Safety
         *
         * `data` must contain a valid control message and the control message must be type of
         * `SOL_SOCKET` and level of `SCM_CREDENTIALS` or `SCM_CREDS`.
         */
        public fun asCredentials(data: List<UByte>): AncillaryData {
            val ancillaryDataIter = AncillaryDataIter.new(data)
            val scmCredentials = ScmCredentials(ancillaryDataIter)
            return AncillaryData.ScmCredentials(scmCredentials)
        }

        public fun tryFromCmsghdr(cmsg: Cmsghdr): Result<AncillaryData, AncillaryError> =
            run {
                val cmsgLenZero = cmsgLen(0).toInt()
                val dataLen = cmsg.cmsgLen.toInt() - cmsgLenZero
                val data = cmsgData(cmsg).cast()
                val data1 = rawSliceList(data, dataLen.toInt(), PrimitiveLayouts.U8)
                when (val matchVal = cmsg.cmsgLevel) {
                    SOL_SOCKET -> when (val matchVal1 = cmsg.cmsgType) {
                        io.github.kotlinmania.libc.ScmRights -> Result.Ok(AncillaryData.asRights(data1))
                        io.github.kotlinmania.libc.ScmCredentials -> Result.Ok(AncillaryData.asCredentials(data1))
                        SCM_CREDS2 -> Result.Ok(AncillaryData.asCredentials(data1))
                        SCM_CREDS -> Result.Ok(AncillaryData.asCredentials(data1))
                        else -> {
                            val cmsgType = matchVal1
                            Result.Err(AncillaryError.Unknown(cmsgLevel = SOL_SOCKET, cmsgType = cmsgType))
                        }
                    }
                    else -> {
                        val cmsgLevel = matchVal
                        Result.Err(AncillaryError.Unknown(cmsgLevel = cmsgLevel, cmsgType = cmsg.cmsgType))
                    }
                }
            }
    }
}

/**
 * This struct is used to iterate through the control messages.
 */
public open class Messages(
    public var buffer: List<UByte>,
    public var current: Cmsghdr?,
) : Iterable<Result<AncillaryData, AncillaryError>> {
    override fun iterator(): kotlin.collections.Iterator<Result<AncillaryData, AncillaryError>> = object : AbstractIterator<Result<AncillaryData, AncillaryError>>() {
        override fun computeNext() {
            when (val v = this@Messages.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Result<AncillaryData, AncillaryError>? {
        return run {
            val msg: Msghdr?  = null
            msg!!.msgControl = (this.buffer.asPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
            msg.msgControllen = this.buffer.size
            val cmsg =
                when (val matchVal = this.current) {
                    null -> {
                        cmsgFirsthdr(msg)
                    }
                    else -> {
                        val current = matchVal
                        cmsgNxthdr(msg, current)
                    }
                }
            val cmsg1 = when (val tryResult = cmsg.asRef()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            // Most operating systems, but not Linux or emscripten, return the previous pointer
            // when its length is zero. Therefore, check if the previous pointer is the same as
            // the current one.
            when (val matchVal1 = this.current) {
                null -> {}
                else -> {
                    val current1 = matchVal1
                    if (eq(current1, cmsg1)) {
                        return null
                    }
                }
            }
            this.current = cmsg1
            val ancillaryResult = AncillaryData.tryFromCmsghdr(cmsg1)
            ancillaryResult
        }
    }
}

/**
 * A Unix socket Ancillary data struct.
 *
 * # Example
 *
 * @file:feature(unixSocketAncillaryData)
 * use std.os.unix.net::{UnixStream, SocketAncillary, AncillaryData};
 * use std.io.IoSliceMut;
 *
 * fn main() -> std.io.Result<()> {
 *     let sock = UnixStream.connect("/tmp/sock")?;
 *
 *     let mut fds = [0; 8];
 *     let mut ancillaryBuffer = [0; 128];
 *     let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
 *
 *     let mut buf = [1; 8];
 *     let mut bufs = [IoSliceMut.new(buf[..])][..];
 *     sock.recvVectoredWithAncillary(bufs, ancillary)?;
 *
 *     for ancillaryResult in ancillary.messages() {
 *         if let AncillaryData.ScmRights(scmRights) = ancillaryResult.unwrap() {
 *             for fd in scmRights {
 *                 println("receive file descriptor: {fd}");
 *             }
 *         }
 *     }
 *     Ok(())
 * }
 * ```kotlin
 */
public open class SocketAncillary(
    public var buffer: MutableList<UByte>,
    public var length: Int,
    public var truncated: Boolean,
) {
    /**
     * Returns the capacity of the buffer.
     */
    public open fun capacity(): Int = this.buffer.size

    /**
     * Returns `true` if the ancillary data is empty.
     */
    public open fun isEmpty(): Boolean = (this.length == 0).toUByte()

    /**
     * Returns the number of used bytes.
     */
    public open fun len(): Int = this.length

    /**
     * Returns the iterator of the control messages.
     */
    public open fun messages(): Messages = Messages(buffer = ((this.buffer).slice(0 until this.length)), current = null)

    /**
     * Is `true` if during a recv operation the ancillary was truncated.
     *
     * # Example
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::{UnixStream, SocketAncillary};
     * use std.io.IoSliceMut;
     *
     * fn main() -> std.io.Result<()> {
     *     let sock = UnixStream.connect("/tmp/sock")?;
     *
     *     let mut ancillaryBuffer = [0; 128];
     *     let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
     *
     *     let mut buf = [1; 8];
     *     let mut bufs = [IoSliceMut.new(buf[..])][..];
     *     sock.recvVectoredWithAncillary(bufs, ancillary)?;
     *
     *     println("Is truncated: {}", ancillary.truncated());
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun truncated(): Boolean = this.truncated

    /**
     * Add file descriptors to the ancillary data.
     *
     * The function returns `true` if there was enough space in the buffer.
     * If there was not enough space then no file descriptors was appended.
     * Technically, that means this operation adds a control message with the level `SOL_SOCKET`
     * and type `SCM_RIGHTS`.
     *
     * # Example
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::{UnixStream, SocketAncillary};
     * use std.os.unix.io.AsRawFd;
     * use std.io.IoSlice;
     *
     * fn main() -> std.io.Result<()> {
     *     let sock = UnixStream.connect("/tmp/sock")?;
     *
     *     let mut ancillaryBuffer = [0; 128];
     *     let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
     *     ancillary.addFds(&[sock.asRawFd()][..]);
     *
     *     let buf = [1; 8];
     *     let mut bufs = [IoSlice.new(&buf[..])][..];
     *     sock.sendVectoredWithAncillary(bufs, ancillary)?;
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun addFds(fds: List<RawFd>): Boolean {
        this.truncated = false
        return addToAncillaryData(this.buffer, this.length, fds, SOL_SOCKET, io.github.kotlinmania.libc.ScmRights)
    }

    /**
     * Add credentials to the ancillary data.
     *
     * The function returns `true` if there is enough space in the buffer.
     * If there is not enough space then no credentials will be appended.
     * Technically, that means this operation adds a control message with the level `SOL_SOCKET`
     * and type `SCM_CREDENTIALS`, `SCM_CREDS`, or `SCM_CREDS2`.
     */
    public open fun addCreds(creds: List<SocketCred>): Boolean {
        this.truncated = false
        return addToAncillaryData(this.buffer, this.length, creds, SOL_SOCKET, io.github.kotlinmania.libc.ScmCredentials, SCM_CREDS2, SCM_CREDS)
    }

    /**
     * Clears the ancillary data, removing all values.
     *
     * # Example
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.unix.net::{UnixStream, SocketAncillary, AncillaryData};
     * use std.io.IoSliceMut;
     *
     * fn main() -> std.io.Result<()> {
     *     let sock = UnixStream.connect("/tmp/sock")?;
     *
     *     let mut fds1 = [0; 8];
     *     let mut fds2 = [0; 8];
     *     let mut ancillaryBuffer = [0; 128];
     *     let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
     *
     *     let mut buf = [1; 8];
     *     let mut bufs = [IoSliceMut.new(buf[..])][..];
     *
     *     sock.recvVectoredWithAncillary(bufs, ancillary)?;
     *     for ancillaryResult in ancillary.messages() {
     *         if let AncillaryData.ScmRights(scmRights) = ancillaryResult.unwrap() {
     *             for fd in scmRights {
     *                 println("receive file descriptor: {fd}");
     *             }
     *         }
     *     }
     *
     *     ancillary.clear();
     *
     *     sock.recvVectoredWithAncillary(bufs, ancillary)?;
     *     for ancillaryResult in ancillary.messages() {
     *         if let AncillaryData.ScmRights(scmRights) = ancillaryResult.unwrap() {
     *             for fd in scmRights {
     *                 println("receive file descriptor: {fd}");
     *             }
     *         }
     *     }
     *     Ok(())
     * }
     * ```kotlin
     */
    public open fun clear() {
        this.length = 0
        this.truncated = false
    }

    public companion object {
        /**
         * Creates an ancillary data with the given buffer.
         *
         * # Example
         *
         * # @file:allow(unusedMut)
         * @file:feature(unixSocketAncillaryData)
         * use std.os.unix.net.SocketAncillary;
         * let mut ancillaryBuffer = [0; 128];
         * let mut ancillary = SocketAncillary.new(ancillaryBuffer[..]);
         * ```kotlin
         */
        public fun new(buffer: MutableList<UByte>): SocketAncillary = SocketAncillary(buffer = buffer, length = 0, truncated = false)
    }
}
