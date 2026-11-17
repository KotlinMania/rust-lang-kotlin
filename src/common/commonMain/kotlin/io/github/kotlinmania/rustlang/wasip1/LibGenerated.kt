// port-lint: source lib_generated.rs
package io.github.kotlinmania.rustlang.wasip1

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.str.asPtr

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public typealias Size = Int

public typealias Filesize = ULong

public typealias Timestamp = ULong

public open class Clockid internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout(),
) {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public var value: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Clockid> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Clockid(ptr, layout) }, { it.value })

    public open fun clone(): Clockid = Clockid(value, fieldLayout)

    public open fun raw(): UInt = this.value

    public open fun name(): String =
        when (this.value) {
            0 -> "REALTIME"
            1 -> "MONOTONIC"
            2 -> "PROCESS_CPUTIME_ID"
            3 -> "THREAD_CPUTIME_ID"
            else -> unreachableUnchecked()
        }

    public open fun message(): String =
        when (this.value) {
            0 -> {
                "The clock measuring real time. Time value zero corresponds with\n1970-01-01T00:00:00Z."
            }
            1 -> {
                "The store-wide monotonic clock, which is defined as a clock measuring\nreal time, whose value cannot be adjusted and which cannot have negative\nclock jumps. The epoch of this clock is undefined. The absolute time\nvalue of this clock therefore has no meaning."
            }
            2 -> "The CPU-time clock associated with the current process."
            3 -> "The CPU-time clock associated with the current thread."
            else -> unreachableUnchecked()
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Clockid").field("code", this.value).field("name", this.name()).field("message", this.message()).finish()

    public companion object {
        public operator fun invoke(value: UInt, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): Clockid = Clockid(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt> = UnresolvedTypeLayout()): Clockid = Clockid(fieldStorage, fieldLayout)
    }
}

/**
 * The right to invoke `fdDatasync`.
 * If `pathOpen` is set, includes the right to invoke
 * `pathOpen` with `fdflags.dsync`.
 */
public val RIGHTS_FD_DATASYNC: Rights = 1uL shl 0

/**
 * The right to invoke `fdRead` and `sockRecv`.
 * If `rights.fdSeek` is set, includes the right to invoke `fdPread`.
 */
public val RIGHTS_FD_READ: Rights = 1uL shl 1

/**
 * The right to invoke `fdSeek`. This flag implies `rights.fdTell`.
 */
public val RIGHTS_FD_SEEK: Rights = 1uL shl 2

/**
 * The right to invoke `fdFdstatSetFlags`.
 */
public val RIGHTS_FD_FDSTAT_SET_FLAGS: Rights = 1uL shl 3

public open class Errno internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout(),
) {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public var value: UShort
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Errno> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Errno(ptr, layout) }, { it.value })

    public open fun clone(): Errno = Errno(value, fieldLayout)

    public open fun raw(): UShort = this.value

    public open fun name(): String =
        when (this.value) {
            0 -> "SUCCESS"
            1 -> "2BIG"
            2 -> "ACCES"
            3 -> "ADDRINUSE"
            4 -> "ADDRNOTAVAIL"
            5 -> "AFNOSUPPORT"
            6 -> "AGAIN"
            7 -> "ALREADY"
            8 -> "BADF"
            9 -> "BADMSG"
            10 -> "BUSY"
            11 -> "CANCELED"
            12 -> "CHILD"
            13 -> "CONNABORTED"
            14 -> "CONNREFUSED"
            15 -> "CONNRESET"
            16 -> "DEADLK"
            17 -> "DESTADDRREQ"
            18 -> "DOM"
            19 -> "DQUOT"
            20 -> "EXIST"
            21 -> "FAULT"
            22 -> "FBIG"
            23 -> "HOSTUNREACH"
            24 -> "IDRM"
            25 -> "ILSEQ"
            26 -> "INPROGRESS"
            27 -> "INTR"
            28 -> "INVAL"
            29 -> "IO"
            30 -> "ISCONN"
            31 -> "ISDIR"
            32 -> "LOOP"
            33 -> "MFILE"
            34 -> "MLINK"
            35 -> "MSGSIZE"
            36 -> "MULTIHOP"
            37 -> "NAMETOOLONG"
            38 -> "NETDOWN"
            39 -> "NETRESET"
            40 -> "NETUNREACH"
            41 -> "NFILE"
            42 -> "NOBUFS"
            43 -> "NODEV"
            44 -> "NOENT"
            45 -> "NOEXEC"
            46 -> "NOLCK"
            47 -> "NOLINK"
            48 -> "NOMEM"
            49 -> "NOMSG"
            50 -> "NOPROTOOPT"
            51 -> "NOSPC"
            52 -> "NOSYS"
            53 -> "NOTCONN"
            54 -> "NOTDIR"
            55 -> "NOTEMPTY"
            56 -> "NOTRECOVERABLE"
            57 -> "NOTSOCK"
            58 -> "NOTSUP"
            59 -> "NOTTY"
            60 -> "NXIO"
            61 -> "OVERFLOW"
            62 -> "OWNERDEAD"
            63 -> "PERM"
            64 -> "PIPE"
            65 -> "PROTO"
            66 -> "PROTONOSUPPORT"
            67 -> "PROTOTYPE"
            68 -> "RANGE"
            69 -> "ROFS"
            70 -> "SPIPE"
            71 -> "SRCH"
            72 -> "STALE"
            73 -> "TIMEDOUT"
            74 -> "TXTBSY"
            75 -> "XDEV"
            76 -> "NOTCAPABLE"
            else -> unreachableUnchecked()
        }

    public open fun message(): String =
        when (this.value) {
            0 -> "No error occurred. System call completed successfully."
            1 -> "Argument list too long."
            2 -> "Permission denied."
            3 -> "Address in use."
            4 -> "Address not available."
            5 -> "Address family not supported."
            6 -> "Resource unavailable, or operation would block."
            7 -> "Connection already in progress."
            8 -> "Bad file descriptor."
            9 -> "Bad message."
            10 -> "Device or resource busy."
            11 -> "Operation canceled."
            12 -> "No child processes."
            13 -> "Connection aborted."
            14 -> "Connection refused."
            15 -> "Connection reset."
            16 -> "Resource deadlock would occur."
            17 -> "Destination address required."
            18 -> "Mathematics argument out of domain of function."
            19 -> "Reserved."
            20 -> "File exists."
            21 -> "Bad address."
            22 -> "File too large."
            23 -> "Host is unreachable."
            24 -> "Identifier removed."
            25 -> "Illegal byte sequence."
            26 -> "Operation in progress."
            27 -> "Interrupted function."
            28 -> "Invalid argument."
            29 -> "I/O error."
            30 -> "Socket is connected."
            31 -> "Is a directory."
            32 -> "Too many levels of symbolic links."
            33 -> "File descriptor value too large."
            34 -> "Too many links."
            35 -> "Message too large."
            36 -> "Reserved."
            37 -> "Filename too long."
            38 -> "Network is down."
            39 -> "Connection aborted by network."
            40 -> "Network unreachable."
            41 -> "Too many files open in system."
            42 -> "No buffer space available."
            43 -> "No such device."
            44 -> "No such file or directory."
            45 -> "Executable file format error."
            46 -> "No locks available."
            47 -> "Reserved."
            48 -> "Not enough space."
            49 -> "No message of the desired type."
            50 -> "Protocol not available."
            51 -> "No space left on device."
            52 -> "Function not supported."
            53 -> "The socket is not connected."
            54 -> "Not a directory or a symbolic link to a directory."
            55 -> "Directory not empty."
            56 -> "State not recoverable."
            57 -> "Not a socket."
            58 -> "Not supported, or operation not supported on socket."
            59 -> "Inappropriate I/O control operation."
            60 -> "No such device or address."
            61 -> "Value too large to be stored in data type."
            62 -> "Previous owner died."
            63 -> "Operation not permitted."
            64 -> "Broken pipe."
            65 -> "Protocol error."
            66 -> "Protocol not supported."
            67 -> "Protocol wrong type for socket."
            68 -> "Result too large."
            69 -> "Read-only file system."
            70 -> "Invalid seek."
            71 -> "No such process."
            72 -> "Reserved."
            73 -> "Connection timed out."
            74 -> "Text file busy."
            75 -> "Cross-device link."
            76 -> "Extension: Capabilities insufficient."
            else -> unreachableUnchecked()
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Errno").field("code", this.value).field("name", this.name()).field("message", this.message()).finish()

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Error> = run { f.append("${this.name()} (error ${this.value})"); Result.Ok(Unit) }

    public companion object {
        public operator fun invoke(value: UShort, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()): Errno = Errno(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UShort> = UnresolvedTypeLayout()): Errno = Errno(fieldStorage, fieldLayout)
    }
}

/**
 * The right to invoke `fdSync`.
 * If `pathOpen` is set, includes the right to invoke
 * `pathOpen` with `fdflags.rsync` and `fdflags.dsync`.
 */
public val RIGHTS_FD_SYNC: Rights = 1uL shl 4

/**
 * The right to invoke `fdSeek` in such a way that the file offset
 * remains unaltered (i.e., `whence.cur` with offset zero), or to
 * invoke `fdTell`.
 */
public val RIGHTS_FD_TELL: Rights = 1uL shl 5

/**
 * The right to invoke `fdWrite` and `sockSend`.
 * If `rights.fdSeek` is set, includes the right to invoke `fdPwrite`.
 */
public val RIGHTS_FD_WRITE: Rights = 1uL shl 6

/**
 * The right to invoke `fdAdvise`.
 */
public val RIGHTS_FD_ADVISE: Rights = 1uL shl 7

/**
 * The right to invoke `fdAllocate`.
 */
public val RIGHTS_FD_ALLOCATE: Rights = 1uL shl 8

/**
 * The right to invoke `pathCreateDirectory`.
 */
public val RIGHTS_PATH_CREATE_DIRECTORY: Rights = 1uL shl 9

/**
 * If `pathOpen` is set, the right to invoke `pathOpen` with `oflags.creat`.
 */
public val RIGHTS_PATH_CREATE_FILE: Rights = 1uL shl 10

/**
 * The right to invoke `pathLink` with the file descriptor as the
 * source directory.
 */
public val RIGHTS_PATH_LINK_SOURCE: Rights = 1uL shl 11

/**
 * The right to invoke `pathLink` with the file descriptor as the
 * target directory.
 */
public val RIGHTS_PATH_LINK_TARGET: Rights = 1uL shl 12

/**
 * The right to invoke `pathOpen`.
 */
public val RIGHTS_PATH_OPEN: Rights = 1uL shl 13

/**
 * The right to invoke `fdReaddir`.
 */
public val RIGHTS_FD_READDIR: Rights = 1uL shl 14

/**
 * The right to invoke `pathReadlink`.
 */
public val RIGHTS_PATH_READLINK: Rights = 1uL shl 15

/**
 * The right to invoke `pathRename` with the file descriptor as the source directory.
 */
public val RIGHTS_PATH_RENAME_SOURCE: Rights = 1uL shl 16

/**
 * The right to invoke `pathRename` with the file descriptor as the target directory.
 */
public val RIGHTS_PATH_RENAME_TARGET: Rights = 1uL shl 17

/**
 * The right to invoke `pathFilestatGet`.
 */
public val RIGHTS_PATH_FILESTAT_GET: Rights = 1uL shl 18

/**
 * The right to change a file's size (there is no `pathFilestatSetSize`).
 * If `pathOpen` is set, includes the right to invoke `pathOpen` with `oflags.trunc`.
 */
public val RIGHTS_PATH_FILESTAT_SET_SIZE: Rights = 1uL shl 19

/**
 * The right to invoke `pathFilestatSetTimes`.
 */
public val RIGHTS_PATH_FILESTAT_SET_TIMES: Rights = 1uL shl 20

/**
 * The right to invoke `fdFilestatGet`.
 */
public val RIGHTS_FD_FILESTAT_GET: Rights = 1uL shl 21

/**
 * The right to invoke `fdFilestatSetSize`.
 */
public val RIGHTS_FD_FILESTAT_SET_SIZE: Rights = 1uL shl 22

/**
 * The right to invoke `fdFilestatSetTimes`.
 */
public val RIGHTS_FD_FILESTAT_SET_TIMES: Rights = 1uL shl 23

/**
 * The right to invoke `pathSymlink`.
 */
public val RIGHTS_PATH_SYMLINK: Rights = 1uL shl 24

/**
 * The right to invoke `pathRemoveDirectory`.
 */
public val RIGHTS_PATH_REMOVE_DIRECTORY: Rights = 1uL shl 25

/**
 * The right to invoke `pathUnlinkFile`.
 */
public val RIGHTS_PATH_UNLINK_FILE: Rights = 1uL shl 26

/**
 * If `rights.fdRead` is set, includes the right to invoke `pollOneoff` to subscribe to `eventtype.fdRead`.
 * If `rights.fdWrite` is set, includes the right to invoke `pollOneoff` to subscribe to `eventtype.fdWrite`.
 */
public val RIGHTS_POLL_FD_READWRITE: Rights = 1uL shl 27

/**
 * The right to invoke `sockShutdown`.
 */
public val RIGHTS_SOCK_SHUTDOWN: Rights = 1uL shl 28

/**
 * The right to invoke `sockAccept`.
 */
public val RIGHTS_SOCK_ACCEPT: Rights = 1uL shl 29

/**
 * Append mode: Data written to the file is always appended to the file's end.
 */
public val FDFLAGS_APPEND: Fdflags = (1u.toUInt() shl 0).toUShort()

/**
 * Write according to synchronized I/O data integrity completion. Only the data stored in the file is synchronized.
 */
public val FDFLAGS_DSYNC: Fdflags = (1u.toUInt() shl 1).toUShort()

/**
 * Non-blocking mode.
 */
public val FDFLAGS_NONBLOCK: Fdflags = (1u.toUInt() shl 2).toUShort()

/**
 * Synchronized read I/O operations.
 */
public val FDFLAGS_RSYNC: Fdflags = (1u.toUInt() shl 3).toUShort()

/**
 * Write according to synchronized I/O file integrity completion. In
 * addition to synchronizing the data stored in the file, the implementation
 * may also synchronously update the file's metadata.
 */
public val FDFLAGS_SYNC: Fdflags = (1u.toUInt() shl 4).toUShort()

/**
 * Adjust the last data access timestamp to the value stored in `filestat.atim`.
 */
public val FSTFLAGS_ATIM: Fstflags = (1u.toUInt() shl 0).toUShort()

/**
 * Adjust the last data access timestamp to the time of clock `clockid.realtime`.
 */
public val FSTFLAGS_ATIM_NOW: Fstflags = (1u.toUInt() shl 1).toUShort()

/**
 * Adjust the last data modification timestamp to the value stored in `filestat.mtim`.
 */
public val FSTFLAGS_MTIM: Fstflags = (1u.toUInt() shl 2).toUShort()

/**
 * Adjust the last data modification timestamp to the time of clock `clockid.realtime`.
 */
public val FSTFLAGS_MTIM_NOW: Fstflags = (1u.toUInt() shl 3).toUShort()

/**
 * As long as the resolved path corresponds to a symbolic link, it is expanded.
 */
public val LOOKUPFLAGS_SYMLINK_FOLLOW: Lookupflags = 1u shl 0

/**
 * Create file if it does not exist.
 */
public val OFLAGS_CREAT: Oflags = (1u.toUInt() shl 0).toUShort()

/**
 * Fail if not a directory.
 */
public val OFLAGS_DIRECTORY: Oflags = (1u.toUInt() shl 1).toUShort()

/**
 * Fail if file already exists.
 */
public val OFLAGS_EXCL: Oflags = (1u.toUInt() shl 2).toUShort()

/**
 * Truncate file to size 0.
 */
public val OFLAGS_TRUNC: Oflags = (1u.toUInt() shl 3).toUShort()

/**
 * The peer of this socket has closed or disconnected.
 */
public val EVENTRWFLAGS_FD_READWRITE_HANGUP: Eventrwflags = (1u.toUInt() shl 0).toUShort()

/**
 * If set, treat the timestamp provided in
 * `subscriptionClock.timeout` as an absolute timestamp of clock
 * `subscriptionClock.id`. If clear, treat the timestamp
 * provided in `subscriptionClock.timeout` relative to the
 * current time value of clock `subscriptionClock.id`.
 */
public val SUBCLOCKFLAGS_SUBSCRIPTION_CLOCK_ABSTIME: Subclockflags = (1u.toUInt() shl 0).toUShort()

/**
 * Returns the message without removing it from the socket's receive queue.
 */
public val RIFLAGS_RECV_PEEK: Riflags = (1u.toUInt() shl 0).toUShort()

/**
 * On byte-stream sockets, block until the full amount of data can be returned.
 */
public val RIFLAGS_RECV_WAITALL: Riflags = (1u.toUInt() shl 1).toUShort()

/**
 * Returned by `sockRecv`: Message data has been truncated.
 */
public val ROFLAGS_RECV_DATA_TRUNCATED: Roflags = (1u.toUInt() shl 0).toUShort()

/**
 * Disables further receive operations.
 */
public val SDFLAGS_RD: Sdflags = (1u.toUInt() shl 0).toUByte()

/**
 * Disables further send operations.
 */
public val SDFLAGS_WR: Sdflags = (1u.toUInt() shl 1).toUByte()

/**
 * The clock measuring real time. Time value zero corresponds with
 * 1970-01-01T00:00:00Z.
 */
public val CLOCKID_REALTIME: Clockid = Clockid(0, fieldLayout = PrimitiveLayouts.U32)

/**
 * The store-wide monotonic clock, which is defined as a clock measuring
 * real time, whose value cannot be adjusted and which cannot have negative
 * clock jumps. The epoch of this clock is undefined. The absolute time
 * value of this clock therefore has no meaning.
 */
public val CLOCKID_MONOTONIC: Clockid = Clockid(1, fieldLayout = PrimitiveLayouts.U32)

/**
 * The CPU-time clock associated with the current process.
 */
public val CLOCKID_PROCESS_CPUTIME_ID: Clockid = Clockid(2, fieldLayout = PrimitiveLayouts.U32)

/**
 * The CPU-time clock associated with the current thread.
 */
public val CLOCKID_THREAD_CPUTIME_ID: Clockid = Clockid(3, fieldLayout = PrimitiveLayouts.U32)

/**
 * No error occurred. System call completed successfully.
 */
public val ERRNO_SUCCESS: Errno = Errno(0, fieldLayout = PrimitiveLayouts.U16)

/**
 * Argument list too long.
 */
public val ERRNO_2BIG: Errno = Errno(1, fieldLayout = PrimitiveLayouts.U16)

/**
 * Permission denied.
 */
public val ERRNO_ACCES: Errno = Errno(2, fieldLayout = PrimitiveLayouts.U16)

/**
 * Address in use.
 */
public val ERRNO_ADDRINUSE: Errno = Errno(3, fieldLayout = PrimitiveLayouts.U16)

/**
 * Address not available.
 */
public val ERRNO_ADDRNOTAVAIL: Errno = Errno(4, fieldLayout = PrimitiveLayouts.U16)

/**
 * Address family not supported.
 */
public val ERRNO_AFNOSUPPORT: Errno = Errno(5, fieldLayout = PrimitiveLayouts.U16)

/**
 * Resource unavailable, or operation would block.
 */
public val ERRNO_AGAIN: Errno = Errno(6, fieldLayout = PrimitiveLayouts.U16)

/**
 * Connection already in progress.
 */
public val ERRNO_ALREADY: Errno = Errno(7, fieldLayout = PrimitiveLayouts.U16)

/**
 * Bad file descriptor.
 */
public val ERRNO_BADF: Errno = Errno(8, fieldLayout = PrimitiveLayouts.U16)

/**
 * Bad message.
 */
public val ERRNO_BADMSG: Errno = Errno(9, fieldLayout = PrimitiveLayouts.U16)

/**
 * Device or resource busy.
 */
public val ERRNO_BUSY: Errno = Errno(10, fieldLayout = PrimitiveLayouts.U16)

/**
 * Operation canceled.
 */
public val ERRNO_CANCELED: Errno = Errno(11, fieldLayout = PrimitiveLayouts.U16)

/**
 * No child processes.
 */
public val ERRNO_CHILD: Errno = Errno(12, fieldLayout = PrimitiveLayouts.U16)

/**
 * Connection aborted.
 */
public val ERRNO_CONNABORTED: Errno = Errno(13, fieldLayout = PrimitiveLayouts.U16)

/**
 * Connection refused.
 */
public val ERRNO_CONNREFUSED: Errno = Errno(14, fieldLayout = PrimitiveLayouts.U16)

/**
 * Connection reset.
 */
public val ERRNO_CONNRESET: Errno = Errno(15, fieldLayout = PrimitiveLayouts.U16)

/**
 * Resource deadlock would occur.
 */
public val ERRNO_DEADLK: Errno = Errno(16, fieldLayout = PrimitiveLayouts.U16)

/**
 * Destination address required.
 */
public val ERRNO_DESTADDRREQ: Errno = Errno(17, fieldLayout = PrimitiveLayouts.U16)

/**
 * Mathematics argument out of domain of function.
 */
public val ERRNO_DOM: Errno = Errno(18, fieldLayout = PrimitiveLayouts.U16)

/**
 * Reserved.
 */
public val ERRNO_DQUOT: Errno = Errno(19, fieldLayout = PrimitiveLayouts.U16)

/**
 * File exists.
 */
public val ERRNO_EXIST: Errno = Errno(20, fieldLayout = PrimitiveLayouts.U16)

/**
 * Bad address.
 */
public val ERRNO_FAULT: Errno = Errno(21, fieldLayout = PrimitiveLayouts.U16)

/**
 * File too large.
 */
public val ERRNO_FBIG: Errno = Errno(22, fieldLayout = PrimitiveLayouts.U16)

/**
 * Host is unreachable.
 */
public val ERRNO_HOSTUNREACH: Errno = Errno(23, fieldLayout = PrimitiveLayouts.U16)

/**
 * Identifier removed.
 */
public val ERRNO_IDRM: Errno = Errno(24, fieldLayout = PrimitiveLayouts.U16)

/**
 * Illegal byte sequence.
 */
public val ERRNO_ILSEQ: Errno = Errno(25, fieldLayout = PrimitiveLayouts.U16)

public typealias Rights = ULong

/**
 * Operation in progress.
 */
public val ERRNO_INPROGRESS: Errno = Errno(26, fieldLayout = PrimitiveLayouts.U16)

/**
 * Interrupted function.
 */
public val ERRNO_INTR: Errno = Errno(27, fieldLayout = PrimitiveLayouts.U16)

/**
 * Invalid argument.
 */
public val ERRNO_INVAL: Errno = Errno(28, fieldLayout = PrimitiveLayouts.U16)

/**
 * I/O error.
 */
public val ERRNO_IO: Errno = Errno(29, fieldLayout = PrimitiveLayouts.U16)

/**
 * Socket is connected.
 */
public val ERRNO_ISCONN: Errno = Errno(30, fieldLayout = PrimitiveLayouts.U16)

/**
 * Is a directory.
 */
public val ERRNO_ISDIR: Errno = Errno(31, fieldLayout = PrimitiveLayouts.U16)

/**
 * Too many levels of symbolic links.
 */
public val ERRNO_LOOP: Errno = Errno(32, fieldLayout = PrimitiveLayouts.U16)

/**
 * File descriptor value too large.
 */
public val ERRNO_MFILE: Errno = Errno(33, fieldLayout = PrimitiveLayouts.U16)

/**
 * Too many links.
 */
public val ERRNO_MLINK: Errno = Errno(34, fieldLayout = PrimitiveLayouts.U16)

/**
 * Message too large.
 */
public val ERRNO_MSGSIZE: Errno = Errno(35, fieldLayout = PrimitiveLayouts.U16)

/**
 * Reserved.
 */
public val ERRNO_MULTIHOP: Errno = Errno(36, fieldLayout = PrimitiveLayouts.U16)

/**
 * Filename too long.
 */
public val ERRNO_NAMETOOLONG: Errno = Errno(37, fieldLayout = PrimitiveLayouts.U16)

/**
 * Network is down.
 */
public val ERRNO_NETDOWN: Errno = Errno(38, fieldLayout = PrimitiveLayouts.U16)

/**
 * Connection aborted by network.
 */
public val ERRNO_NETRESET: Errno = Errno(39, fieldLayout = PrimitiveLayouts.U16)

/**
 * Network unreachable.
 */
public val ERRNO_NETUNREACH: Errno = Errno(40, fieldLayout = PrimitiveLayouts.U16)

/**
 * Too many files open in system.
 */
public val ERRNO_NFILE: Errno = Errno(41, fieldLayout = PrimitiveLayouts.U16)

/**
 * No buffer space available.
 */
public val ERRNO_NOBUFS: Errno = Errno(42, fieldLayout = PrimitiveLayouts.U16)

/**
 * No such device.
 */
public val ERRNO_NODEV: Errno = Errno(43, fieldLayout = PrimitiveLayouts.U16)

/**
 * No such file or directory.
 */
public val ERRNO_NOENT: Errno = Errno(44, fieldLayout = PrimitiveLayouts.U16)

/**
 * Executable file format error.
 */
public val ERRNO_NOEXEC: Errno = Errno(45, fieldLayout = PrimitiveLayouts.U16)

/**
 * No locks available.
 */
public val ERRNO_NOLCK: Errno = Errno(46, fieldLayout = PrimitiveLayouts.U16)

/**
 * Reserved.
 */
public val ERRNO_NOLINK: Errno = Errno(47, fieldLayout = PrimitiveLayouts.U16)

/**
 * Not enough space.
 */
public val ERRNO_NOMEM: Errno = Errno(48, fieldLayout = PrimitiveLayouts.U16)

/**
 * No message of the desired type.
 */
public val ERRNO_NOMSG: Errno = Errno(49, fieldLayout = PrimitiveLayouts.U16)

/**
 * Protocol not available.
 */
public val ERRNO_NOPROTOOPT: Errno = Errno(50, fieldLayout = PrimitiveLayouts.U16)

/**
 * No space left on device.
 */
public val ERRNO_NOSPC: Errno = Errno(51, fieldLayout = PrimitiveLayouts.U16)

/**
 * Function not supported.
 */
public val ERRNO_NOSYS: Errno = Errno(52, fieldLayout = PrimitiveLayouts.U16)

/**
 * The socket is not connected.
 */
public val ERRNO_NOTCONN: Errno = Errno(53, fieldLayout = PrimitiveLayouts.U16)

/**
 * Not a directory or a symbolic link to a directory.
 */
public val ERRNO_NOTDIR: Errno = Errno(54, fieldLayout = PrimitiveLayouts.U16)

/**
 * Directory not empty.
 */
public val ERRNO_NOTEMPTY: Errno = Errno(55, fieldLayout = PrimitiveLayouts.U16)

public typealias Fd = UInt

public open class Iovec internal constructor(
    bufCtorArg: MutPtr,
    /**
     * The length of the buffer to be filled.
     */
    public var bufLen: Size,
) {
    /**
     * The address of the buffer to be filled.
     */
    public var buf: MutPtr = bufCtorArg
        internal set

    public open fun clone(): Iovec = Iovec(buf, bufLen)

    public companion object {
        public fun typeLayout(): TypeLayout<Iovec> =
            object : TypeLayout<Iovec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Iovec =
                    Iovec(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Iovec) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.buf)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.bufLen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Iovec =
                    Iovec(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Iovec) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.buf)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.bufLen)
                }
            }

        public operator fun invoke(
            buf: MutPtr,
            bufLen: Size,
        ): Iovec = Iovec(
            bufCtorArg = buf,
            bufLen = bufLen,
        )
    }
}

public open class Ciovec internal constructor(
    bufCtorArg: ConstPtr,
    /**
     * The length of the buffer to be written.
     */
    public var bufLen: Size,
) {
    /**
     * The address of the buffer to be written.
     */
    public var buf: ConstPtr = bufCtorArg
        internal set

    public open fun clone(): Ciovec = Ciovec(buf, bufLen)

    public companion object {
        public fun typeLayout(): TypeLayout<Ciovec> =
            object : TypeLayout<Ciovec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Ciovec =
                    Ciovec(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Ciovec) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.buf)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.bufLen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ciovec =
                    Ciovec(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ciovec) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.buf)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.bufLen)
                }
            }

        public operator fun invoke(
            buf: ConstPtr,
            bufLen: Size,
        ): Ciovec = Ciovec(
            bufCtorArg = buf,
            bufLen = bufLen,
        )
    }
}

public typealias IovecArray = List<Iovec>

public typealias CiovecArray = List<Ciovec>

public typealias Filedelta = Long

public open class Whence internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout(),
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Whence> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Whence(ptr, layout) }, { it.value })

    public open fun clone(): Whence = Whence(value, fieldLayout)

    public open fun raw(): UByte = this.value

    public open fun name(): String =
        when (this.value) {
            0 -> "SET"
            1 -> "CUR"
            2 -> "END"
            else -> unreachableUnchecked()
        }

    public open fun message(): String =
        when (this.value) {
            0 -> "Seek relative to start-of-file."
            1 -> "Seek relative to current position."
            2 -> "Seek relative to end-of-file."
            else -> unreachableUnchecked()
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Whence").field("code", this.value).field("name", this.name()).field("message", this.message()).finish()

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Whence = Whence(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Whence = Whence(fieldStorage, fieldLayout)
    }
}

/**
 * State not recoverable.
 */
public val ERRNO_NOTRECOVERABLE: Errno = Errno(56, fieldLayout = PrimitiveLayouts.U16)

/**
 * Not a socket.
 */
public val ERRNO_NOTSOCK: Errno = Errno(57, fieldLayout = PrimitiveLayouts.U16)

/**
 * Not supported, or operation not supported on socket.
 */
public val ERRNO_NOTSUP: Errno = Errno(58, fieldLayout = PrimitiveLayouts.U16)

public typealias Dircookie = ULong

public typealias Dirnamlen = UInt

public typealias Inode = ULong

public open class Filetype internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout(),
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Filetype> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Filetype(ptr, layout) }, { it.value })

    public open fun clone(): Filetype = Filetype(value, fieldLayout)

    public open fun raw(): UByte = this.value

    public open fun name(): String =
        when (this.value) {
            0 -> "UNKNOWN"
            1 -> "BLOCK_DEVICE"
            2 -> "CHARACTER_DEVICE"
            3 -> "DIRECTORY"
            4 -> "REGULAR_FILE"
            5 -> "SOCKET_DGRAM"
            6 -> "SOCKET_STREAM"
            7 -> "SYMBOLIC_LINK"
            else -> unreachableUnchecked()
        }

    public open fun message(): String =
        when (this.value) {
            0 -> "The type of the file descriptor or file is unknown or is different from any of the other types specified."
            1 -> "The file descriptor or file refers to a block device inode."
            2 -> "The file descriptor or file refers to a character device inode."
            3 -> "The file descriptor or file refers to a directory inode."
            4 -> "The file descriptor or file refers to a regular file inode."
            5 -> "The file descriptor or file refers to a datagram socket."
            6 -> "The file descriptor or file refers to a byte-stream socket."
            7 -> "The file refers to a symbolic link inode."
            else -> unreachableUnchecked()
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Filetype").field("code", this.value).field("name", this.name()).field("message", this.message()).finish()

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Filetype = Filetype(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Filetype = Filetype(fieldStorage, fieldLayout)
    }
}

/**
 * Inappropriate I/O control operation.
 */
public val ERRNO_NOTTY: Errno = Errno(59, fieldLayout = PrimitiveLayouts.U16)

/**
 * No such device or address.
 */
public val ERRNO_NXIO: Errno = Errno(60, fieldLayout = PrimitiveLayouts.U16)

/**
 * Value too large to be stored in data type.
 */
public val ERRNO_OVERFLOW: Errno = Errno(61, fieldLayout = PrimitiveLayouts.U16)

/**
 * Previous owner died.
 */
public val ERRNO_OWNERDEAD: Errno = Errno(62, fieldLayout = PrimitiveLayouts.U16)

/**
 * Operation not permitted.
 */
public val ERRNO_PERM: Errno = Errno(63, fieldLayout = PrimitiveLayouts.U16)

/**
 * Broken pipe.
 */
public val ERRNO_PIPE: Errno = Errno(64, fieldLayout = PrimitiveLayouts.U16)

/**
 * Protocol error.
 */
public val ERRNO_PROTO: Errno = Errno(65, fieldLayout = PrimitiveLayouts.U16)

/**
 * Protocol not supported.
 */
public val ERRNO_PROTONOSUPPORT: Errno = Errno(66, fieldLayout = PrimitiveLayouts.U16)

public open class Dirent(
    /**
     * The offset of the next directory entry stored in this directory.
     */
    public var dNext: Dircookie,
    /**
     * The serial number of the file referred to by this directory entry.
     */
    public var dIno: Inode,
    /**
     * The length of the name of the directory entry.
     */
    public var dNamlen: Dirnamlen,
    /**
     * The type of the file referred to by this directory entry.
     */
    public var dType: Filetype,
) {
    public open fun clone(): Dirent = Dirent(dNext, dIno, dNamlen, dType)
}

public open class Advice internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout(),
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Advice> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Advice(ptr, layout) }, { it.value })

    public open fun clone(): Advice = Advice(value, fieldLayout)

    public open fun raw(): UByte = this.value

    public open fun name(): String =
        when (this.value) {
            0 -> "NORMAL"
            1 -> "SEQUENTIAL"
            2 -> "RANDOM"
            3 -> "WILLNEED"
            4 -> "DONTNEED"
            5 -> "NOREUSE"
            else -> unreachableUnchecked()
        }

    public open fun message(): String =
        when (this.value) {
            0 -> "The application has no advice to give on its behavior with respect to the specified data."
            1 -> "The application expects to access the specified data sequentially from lower offsets to higher offsets."
            2 -> "The application expects to access the specified data in a random order."
            3 -> "The application expects to access the specified data in the near future."
            4 -> "The application expects that it will not access the specified data in the near future."
            5 -> "The application expects to access the specified data once and then not reuse it thereafter."
            else -> unreachableUnchecked()
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Advice").field("code", this.value).field("name", this.name()).field("message", this.message()).finish()

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Advice = Advice(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Advice = Advice(fieldStorage, fieldLayout)
    }
}

/**
 * Protocol wrong type for socket.
 */
public val ERRNO_PROTOTYPE: Errno = Errno(67, fieldLayout = PrimitiveLayouts.U16)

/**
 * Result too large.
 */
public val ERRNO_RANGE: Errno = Errno(68, fieldLayout = PrimitiveLayouts.U16)

/**
 * Read-only file system.
 */
public val ERRNO_ROFS: Errno = Errno(69, fieldLayout = PrimitiveLayouts.U16)

/**
 * Invalid seek.
 */
public val ERRNO_SPIPE: Errno = Errno(70, fieldLayout = PrimitiveLayouts.U16)

/**
 * No such process.
 */
public val ERRNO_SRCH: Errno = Errno(71, fieldLayout = PrimitiveLayouts.U16)

/**
 * Reserved.
 */
public val ERRNO_STALE: Errno = Errno(72, fieldLayout = PrimitiveLayouts.U16)

public typealias Fdflags = UShort

/**
 * Connection timed out.
 */
public val ERRNO_TIMEDOUT: Errno = Errno(73, fieldLayout = PrimitiveLayouts.U16)

/**
 * Text file busy.
 */
public val ERRNO_TXTBSY: Errno = Errno(74, fieldLayout = PrimitiveLayouts.U16)

/**
 * Cross-device link.
 */
public val ERRNO_XDEV: Errno = Errno(75, fieldLayout = PrimitiveLayouts.U16)

/**
 * Extension: Capabilities insufficient.
 */
public val ERRNO_NOTCAPABLE: Errno = Errno(76, fieldLayout = PrimitiveLayouts.U16)

/**
 * Seek relative to start-of-file.
 */
public val WHENCE_SET: Whence = Whence(0, fieldLayout = PrimitiveLayouts.U8)

public open class Fdstat(
    /**
     * File type.
     */
    public var fsFiletype: Filetype,
    /**
     * File descriptor flags.
     */
    public var fsFlags: Fdflags,
    /**
     * Rights that apply to this file descriptor.
     */
    public var fsRightsBase: Rights,
    /**
     * Maximum set of rights that may be installed on new file descriptors that
     * are created through this file descriptor, e.g., through `pathOpen`.
     */
    public var fsRightsInheriting: Rights,
) {
    public open fun clone(): Fdstat = Fdstat(fsFiletype, fsFlags, fsRightsBase, fsRightsInheriting)
}

public typealias Device = ULong

public typealias Fstflags = UShort

/**
 * Seek relative to current position.
 */
public val WHENCE_CUR: Whence = Whence(1, fieldLayout = PrimitiveLayouts.U8)

/**
 * Seek relative to end-of-file.
 */
public val WHENCE_END: Whence = Whence(2, fieldLayout = PrimitiveLayouts.U8)

/**
 * The type of the file descriptor or file is unknown or is different from any of the other types specified.
 */
public val FILETYPE_UNKNOWN: Filetype = Filetype(0, fieldLayout = PrimitiveLayouts.U8)

/**
 * The file descriptor or file refers to a block device inode.
 */
public val FILETYPE_BLOCK_DEVICE: Filetype = Filetype(1, fieldLayout = PrimitiveLayouts.U8)

public typealias Lookupflags = UInt

/**
 * The file descriptor or file refers to a character device inode.
 */
public val FILETYPE_CHARACTER_DEVICE: Filetype = Filetype(2, fieldLayout = PrimitiveLayouts.U8)

public typealias Oflags = UShort

/**
 * The file descriptor or file refers to a directory inode.
 */
public val FILETYPE_DIRECTORY: Filetype = Filetype(3, fieldLayout = PrimitiveLayouts.U8)

/**
 * The file descriptor or file refers to a regular file inode.
 */
public val FILETYPE_REGULAR_FILE: Filetype = Filetype(4, fieldLayout = PrimitiveLayouts.U8)

/**
 * The file descriptor or file refers to a datagram socket.
 */
public val FILETYPE_SOCKET_DGRAM: Filetype = Filetype(5, fieldLayout = PrimitiveLayouts.U8)

/**
 * The file descriptor or file refers to a byte-stream socket.
 */
public val FILETYPE_SOCKET_STREAM: Filetype = Filetype(6, fieldLayout = PrimitiveLayouts.U8)

public typealias Linkcount = ULong

public open class Filestat(
    /**
     * Device ID of device containing the file.
     */
    public var dev: Device,
    /**
     * File serial number.
     */
    public var ino: Inode,
    /**
     * File type.
     */
    public var filetype: Filetype,
    /**
     * Number of hard links to the file.
     */
    public var nlink: Linkcount,
    /**
     * For regular files, the file size in bytes. For symbolic links, the length in bytes of the pathname contained in the symbolic link.
     */
    public var size: Filesize,
    /**
     * Last data access timestamp.
     */
    public var atim: Timestamp,
    /**
     * Last data modification timestamp.
     */
    public var mtim: Timestamp,
    /**
     * Last file status change timestamp.
     */
    public var ctim: Timestamp,
) {
    public open fun clone(): Filestat = Filestat(dev, ino, filetype, nlink, size, atim, mtim, ctim)
}

public typealias Userdata = ULong

public open class Eventtype internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout(),
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Eventtype> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Eventtype(ptr, layout) }, { it.value })

    public open fun clone(): Eventtype = Eventtype(value, fieldLayout)

    public open fun raw(): UByte = this.value

    public open fun name(): String =
        when (this.value) {
            0 -> "CLOCK"
            1 -> "FD_READ"
            2 -> "FD_WRITE"
            else -> unreachableUnchecked()
        }

    public open fun message(): String =
        when (this.value) {
            0 -> {
                "The time value of clock `subscription_clock::id` has\nreached timestamp `subscription_clock::timeout`."
            }
            1 -> {
                "File descriptor `subscription_fd_readwrite::file_descriptor` has data\navailable for reading. This event always triggers for regular files."
            }
            2 -> {
                "File descriptor `subscription_fd_readwrite::file_descriptor` has capacity\navailable for writing. This event always triggers for regular files."
            }
            else -> unreachableUnchecked()
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Eventtype").field("code", this.value).field("name", this.name()).field("message", this.message()).finish()

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Eventtype = Eventtype(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Eventtype = Eventtype(fieldStorage, fieldLayout)
    }
}

/**
 * The file refers to a symbolic link inode.
 */
public val FILETYPE_SYMBOLIC_LINK: Filetype = Filetype(7, fieldLayout = PrimitiveLayouts.U8)

/**
 * The application has no advice to give on its behavior with respect to the specified data.
 */
public val ADVICE_NORMAL: Advice = Advice(0, fieldLayout = PrimitiveLayouts.U8)

/**
 * The application expects to access the specified data sequentially from lower offsets to higher offsets.
 */
public val ADVICE_SEQUENTIAL: Advice = Advice(1, fieldLayout = PrimitiveLayouts.U8)

public typealias Eventrwflags = UShort

/**
 * The application expects to access the specified data in a random order.
 */
public val ADVICE_RANDOM: Advice = Advice(2, fieldLayout = PrimitiveLayouts.U8)

public open class EventFdReadwrite(
    /**
     * The number of bytes available for reading or writing.
     */
    public var nbytes: Filesize,
    /**
     * The state of the file descriptor.
     */
    public var flags: Eventrwflags,
) {
    public open fun clone(): EventFdReadwrite = EventFdReadwrite(nbytes, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<EventFdReadwrite> =
            object : TypeLayout<EventFdReadwrite> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): EventFdReadwrite =
                    EventFdReadwrite(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: EventFdReadwrite) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.nbytes)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EventFdReadwrite =
                    EventFdReadwrite(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EventFdReadwrite) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.nbytes)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.flags)
                }
            }
    }
}

public open class Event(
    /**
     * User-provided value that got attached to `subscription.userdata`.
     */
    public var userdata: Userdata,
    /**
     * If non-zero, an error that occurred while processing the subscription request.
     */
    public var error: Errno,
    /**
     * The type of event that occured
     */
    public var type: Eventtype,
    /**
     * The contents of the event, if it is an `eventtype.fdRead` or
     * `eventtype.fdWrite`. `eventtype.clock` events ignore this field.
     */
    public var fdReadwrite: EventFdReadwrite,
) {
    public open fun clone(): Event = Event(userdata, error, type, fdReadwrite)
}

public typealias Subclockflags = UShort

/**
 * The application expects to access the specified data in the near future.
 */
public val ADVICE_WILLNEED: Advice = Advice(3, fieldLayout = PrimitiveLayouts.U8)

public open class SubscriptionClock(
    /**
     * The clock against which to compare the timestamp.
     */
    public var id: Clockid,
    /**
     * The absolute or relative timestamp.
     */
    public var timeout: Timestamp,
    /**
     * The amount of time that the implementation may wait additionally
     * to coalesce with other events.
     */
    public var precision: Timestamp,
    /**
     * Flags specifying whether the timeout is absolute or relative
     */
    public var flags: Subclockflags,
) {
    public open fun clone(): SubscriptionClock = SubscriptionClock(id, timeout, precision, flags)
}

public open class SubscriptionFdReadwrite(
    /**
     * The file descriptor on which to wait for it to become ready for reading or writing.
     */
    public var fileDescriptor: Fd,
) {
    public open fun clone(): SubscriptionFdReadwrite = SubscriptionFdReadwrite(fileDescriptor)

    public companion object {
        public fun typeLayout(): TypeLayout<SubscriptionFdReadwrite> =
            object : TypeLayout<SubscriptionFdReadwrite> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): SubscriptionFdReadwrite =
                    SubscriptionFdReadwrite(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SubscriptionFdReadwrite) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.fileDescriptor)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SubscriptionFdReadwrite =
                    SubscriptionFdReadwrite(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SubscriptionFdReadwrite) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.fileDescriptor)
                }
            }
    }
}

public open class SubscriptionUU(
    public var clock: SubscriptionClock? = null,
    public var fdRead: SubscriptionFdReadwrite? = null,
    public var fdWrite: SubscriptionFdReadwrite? = null,
)

public open class SubscriptionU(
    public var tag: UByte,
    public var u: SubscriptionUU,
) {
    public open fun clone(): SubscriptionU = SubscriptionU(tag, u)
}

public open class Subscription(
    /**
     * User-provided value that is attached to the subscription in the
     * implementation and returned through `event.userdata`.
     */
    public var userdata: Userdata,
    /**
     * The type of the event to which to subscribe, and its contents
     */
    public var u: SubscriptionU,
) {
    public open fun clone(): Subscription = Subscription(userdata, u)
}

public typealias Exitcode = UInt

public open class Signal internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout(),
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Signal> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Signal(ptr, layout) }, { it.value })

    public open fun clone(): Signal = Signal(value, fieldLayout)

    public open fun raw(): UByte = this.value

    public open fun name(): String =
        when (this.value) {
            0 -> "NONE"
            1 -> "HUP"
            2 -> "INT"
            3 -> "QUIT"
            4 -> "ILL"
            5 -> "TRAP"
            6 -> "ABRT"
            7 -> "BUS"
            8 -> "FPE"
            9 -> "KILL"
            10 -> "USR1"
            11 -> "SEGV"
            12 -> "USR2"
            13 -> "PIPE"
            14 -> "ALRM"
            15 -> "TERM"
            16 -> "CHLD"
            17 -> "CONT"
            18 -> "STOP"
            19 -> "TSTP"
            20 -> "TTIN"
            21 -> "TTOU"
            22 -> "URG"
            23 -> "XCPU"
            24 -> "XFSZ"
            25 -> "VTALRM"
            26 -> "PROF"
            27 -> "WINCH"
            28 -> "POLL"
            29 -> "PWR"
            30 -> "SYS"
            else -> unreachableUnchecked()
        }

    public open fun message(): String =
        when (this.value) {
            0 -> {
                "No signal. Note that POSIX has special semantics for `kill(pid, 0)`,\nso this value is reserved."
            }
            1 -> {
                "Hangup.\nAction: Terminates the process."
            }
            2 -> {
                "Terminate interrupt signal.\nAction: Terminates the process."
            }
            3 -> {
                "Terminal quit signal.\nAction: Terminates the process."
            }
            4 -> {
                "Illegal instruction.\nAction: Terminates the process."
            }
            5 -> {
                "Trace/breakpoint trap.\nAction: Terminates the process."
            }
            6 -> {
                "Process abort signal.\nAction: Terminates the process."
            }
            7 -> {
                "Access to an undefined portion of a memory object.\nAction: Terminates the process."
            }
            8 -> {
                "Erroneous arithmetic operation.\nAction: Terminates the process."
            }
            9 -> {
                "Kill.\nAction: Terminates the process."
            }
            10 -> {
                "User-defined signal 1.\nAction: Terminates the process."
            }
            11 -> {
                "Invalid memory reference.\nAction: Terminates the process."
            }
            12 -> {
                "User-defined signal 2.\nAction: Terminates the process."
            }
            13 -> {
                "Write on a pipe with no one to read it.\nAction: Ignored."
            }
            14 -> {
                "Alarm clock.\nAction: Terminates the process."
            }
            15 -> {
                "Termination signal.\nAction: Terminates the process."
            }
            16 -> {
                "Child process terminated, stopped, or continued.\nAction: Ignored."
            }
            17 -> {
                "Continue executing, if stopped.\nAction: Continues executing, if stopped."
            }
            18 -> {
                "Stop executing.\nAction: Stops executing."
            }
            19 -> {
                "Terminal stop signal.\nAction: Stops executing."
            }
            20 -> {
                "Background process attempting read.\nAction: Stops executing."
            }
            21 -> {
                "Background process attempting write.\nAction: Stops executing."
            }
            22 -> {
                "High bandwidth data is available at a socket.\nAction: Ignored."
            }
            23 -> {
                "CPU time limit exceeded.\nAction: Terminates the process."
            }
            24 -> {
                "File size limit exceeded.\nAction: Terminates the process."
            }
            25 -> {
                "Virtual timer expired.\nAction: Terminates the process."
            }
            26 -> {
                "Profiling timer expired.\nAction: Terminates the process."
            }
            27 -> {
                "Window changed.\nAction: Ignored."
            }
            28 -> {
                "I/O possible.\nAction: Terminates the process."
            }
            29 -> {
                "Power failure.\nAction: Terminates the process."
            }
            30 -> {
                "Bad system call.\nAction: Terminates the process."
            }
            else -> unreachableUnchecked()
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Signal").field("code", this.value).field("name", this.name()).field("message", this.message()).finish()

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Signal = Signal(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Signal = Signal(fieldStorage, fieldLayout)
    }
}

/**
 * The application expects that it will not access the specified data in the near future.
 */
public val ADVICE_DONTNEED: Advice = Advice(4, fieldLayout = PrimitiveLayouts.U8)

/**
 * The application expects to access the specified data once and then not reuse it thereafter.
 */
public val ADVICE_NOREUSE: Advice = Advice(5, fieldLayout = PrimitiveLayouts.U8)

/**
 * The time value of clock `subscriptionClock.id` has
 * reached timestamp `subscriptionClock.timeout`.
 */
public val EVENTTYPE_CLOCK: Eventtype = Eventtype(0, fieldLayout = PrimitiveLayouts.U8)

/**
 * File descriptor `subscriptionFdReadwrite.fileDescriptor` has data
 * available for reading. This event always triggers for regular files.
 */
public val EVENTTYPE_FD_READ: Eventtype = Eventtype(1, fieldLayout = PrimitiveLayouts.U8)

/**
 * File descriptor `subscriptionFdReadwrite.fileDescriptor` has capacity
 * available for writing. This event always triggers for regular files.
 */
public val EVENTTYPE_FD_WRITE: Eventtype = Eventtype(2, fieldLayout = PrimitiveLayouts.U8)

/**
 * No signal. Note that POSIX has special semantics for `kill(pid, 0)`,
 * so this value is reserved.
 */
public val SIGNAL_NONE: Signal = Signal(0, fieldLayout = PrimitiveLayouts.U8)

/**
 * Hangup.
 * Action: Terminates the process.
 */
public val SIGNAL_HUP: Signal = Signal(1, fieldLayout = PrimitiveLayouts.U8)

/**
 * Terminate interrupt signal.
 * Action: Terminates the process.
 */
public val SIGNAL_INT: Signal = Signal(2, fieldLayout = PrimitiveLayouts.U8)

/**
 * Terminal quit signal.
 * Action: Terminates the process.
 */
public val SIGNAL_QUIT: Signal = Signal(3, fieldLayout = PrimitiveLayouts.U8)

/**
 * Illegal instruction.
 * Action: Terminates the process.
 */
public val SIGNAL_ILL: Signal = Signal(4, fieldLayout = PrimitiveLayouts.U8)

/**
 * Trace/breakpoint trap.
 * Action: Terminates the process.
 */
public val SIGNAL_TRAP: Signal = Signal(5, fieldLayout = PrimitiveLayouts.U8)

/**
 * Process abort signal.
 * Action: Terminates the process.
 */
public val SIGNAL_ABRT: Signal = Signal(6, fieldLayout = PrimitiveLayouts.U8)

/**
 * Access to an undefined portion of a memory object.
 * Action: Terminates the process.
 */
public val SIGNAL_BUS: Signal = Signal(7, fieldLayout = PrimitiveLayouts.U8)

/**
 * Erroneous arithmetic operation.
 * Action: Terminates the process.
 */
public val SIGNAL_FPE: Signal = Signal(8, fieldLayout = PrimitiveLayouts.U8)

/**
 * Kill.
 * Action: Terminates the process.
 */
public val SIGNAL_KILL: Signal = Signal(9, fieldLayout = PrimitiveLayouts.U8)

/**
 * User-defined signal 1.
 * Action: Terminates the process.
 */
public val SIGNAL_USR1: Signal = Signal(10, fieldLayout = PrimitiveLayouts.U8)

/**
 * Invalid memory reference.
 * Action: Terminates the process.
 */
public val SIGNAL_SEGV: Signal = Signal(11, fieldLayout = PrimitiveLayouts.U8)

/**
 * User-defined signal 2.
 * Action: Terminates the process.
 */
public val SIGNAL_USR2: Signal = Signal(12, fieldLayout = PrimitiveLayouts.U8)

/**
 * Write on a pipe with no one to read it.
 * Action: Ignored.
 */
public val SIGNAL_PIPE: Signal = Signal(13, fieldLayout = PrimitiveLayouts.U8)

/**
 * Alarm clock.
 * Action: Terminates the process.
 */
public val SIGNAL_ALRM: Signal = Signal(14, fieldLayout = PrimitiveLayouts.U8)

/**
 * Termination signal.
 * Action: Terminates the process.
 */
public val SIGNAL_TERM: Signal = Signal(15, fieldLayout = PrimitiveLayouts.U8)

/**
 * Child process terminated, stopped, or continued.
 * Action: Ignored.
 */
public val SIGNAL_CHLD: Signal = Signal(16, fieldLayout = PrimitiveLayouts.U8)

/**
 * Continue executing, if stopped.
 * Action: Continues executing, if stopped.
 */
public val SIGNAL_CONT: Signal = Signal(17, fieldLayout = PrimitiveLayouts.U8)

/**
 * Stop executing.
 * Action: Stops executing.
 */
public val SIGNAL_STOP: Signal = Signal(18, fieldLayout = PrimitiveLayouts.U8)

/**
 * Terminal stop signal.
 * Action: Stops executing.
 */
public val SIGNAL_TSTP: Signal = Signal(19, fieldLayout = PrimitiveLayouts.U8)

/**
 * Background process attempting read.
 * Action: Stops executing.
 */
public val SIGNAL_TTIN: Signal = Signal(20, fieldLayout = PrimitiveLayouts.U8)

/**
 * Background process attempting write.
 * Action: Stops executing.
 */
public val SIGNAL_TTOU: Signal = Signal(21, fieldLayout = PrimitiveLayouts.U8)

/**
 * High bandwidth data is available at a socket.
 * Action: Ignored.
 */
public val SIGNAL_URG: Signal = Signal(22, fieldLayout = PrimitiveLayouts.U8)

/**
 * CPU time limit exceeded.
 * Action: Terminates the process.
 */
public val SIGNAL_XCPU: Signal = Signal(23, fieldLayout = PrimitiveLayouts.U8)

/**
 * File size limit exceeded.
 * Action: Terminates the process.
 */
public val SIGNAL_XFSZ: Signal = Signal(24, fieldLayout = PrimitiveLayouts.U8)

/**
 * Virtual timer expired.
 * Action: Terminates the process.
 */
public val SIGNAL_VTALRM: Signal = Signal(25, fieldLayout = PrimitiveLayouts.U8)

public typealias Riflags = UShort

/**
 * Profiling timer expired.
 * Action: Terminates the process.
 */
public val SIGNAL_PROF: Signal = Signal(26, fieldLayout = PrimitiveLayouts.U8)

/**
 * Window changed.
 * Action: Ignored.
 */
public val SIGNAL_WINCH: Signal = Signal(27, fieldLayout = PrimitiveLayouts.U8)

public typealias Roflags = UShort

/**
 * I/O possible.
 * Action: Terminates the process.
 */
public val SIGNAL_POLL: Signal = Signal(28, fieldLayout = PrimitiveLayouts.U8)

public typealias Siflags = UShort

public typealias Sdflags = UByte

/**
 * Power failure.
 * Action: Terminates the process.
 */
public val SIGNAL_PWR: Signal = Signal(29, fieldLayout = PrimitiveLayouts.U8)

/**
 * Bad system call.
 * Action: Terminates the process.
 */
public val SIGNAL_SYS: Signal = Signal(30, fieldLayout = PrimitiveLayouts.U8)

public open class Preopentype internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout(),
) {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public var value: UByte
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Preopentype> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Preopentype(ptr, layout) }, { it.value })

    public open fun clone(): Preopentype = Preopentype(value, fieldLayout)

    public open fun raw(): UByte = this.value

    public open fun name(): String =
        when (this.value) {
            0 -> "DIR"
            else -> unreachableUnchecked()
        }

    public open fun message(): String =
        when (this.value) {
            0 -> "A pre-opened directory."
            else -> unreachableUnchecked()
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = DebugStruct(f, "Preopentype").field("code", this.value).field("name", this.name()).field("message", this.message()).finish()

    public companion object {
        public operator fun invoke(value: UByte, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Preopentype = Preopentype(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByte> = UnresolvedTypeLayout()): Preopentype = Preopentype(fieldStorage, fieldLayout)
    }
}

/**
 * A pre-opened directory.
 */
public val PREOPENTYPE_DIR: Preopentype = Preopentype(0, fieldLayout = PrimitiveLayouts.U8)

public open class PrestatDir(
    /**
     * The length of the directory name for use with `fdPrestatDirName`.
     */
    public var prNameLen: Size,
) {
    public open fun clone(): PrestatDir = PrestatDir(prNameLen)

    public companion object {
        public fun typeLayout(): TypeLayout<PrestatDir> =
            object : TypeLayout<PrestatDir> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): PrestatDir =
                    PrestatDir(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PrestatDir) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.prNameLen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PrestatDir =
                    PrestatDir(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PrestatDir) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.prNameLen)
                }
            }
    }
}

public open class PrestatU(
    public var dir: PrestatDir? = null,
)

public open class Prestat(
    public var tag: UByte,
    public var u: PrestatU,
) {
    public open fun clone(): Prestat = Prestat(tag, u)
}

/**
 * Read command-line argument data.
 * The size of the array should match that returned by `argsSizesGet`.
 * Each argument is expected to be `\0` terminated.
 */
public fun argsGet(argv: MutPtr, argvBuf: MutPtr): Result<Unit, Errno> {
    val ret = argsGet(argv.rawAddress.toInt(), argvBuf.rawAddress.toInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return command-line argument data sizes.
 *
 * ## Return
 *
 * Returns the number of arguments and the size of the argument string
 * data, or an error.
 */
public fun argsSizesGet(): Result<Pair<Size, Size>, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val rp1 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = argsSizesGet(rp0.asMutPtr().toInt(), rp1.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Pair<Size, Size>, Errno>(Pair(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64), read(ConstPtr((rp1.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64)))
        else -> Result.Err<Pair<Size, Size>, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Read environment variable data.
 * The sizes of the buffers should match that returned by `environSizesGet`.
 * Key/value pairs are expected to be joined with `=`s, and terminated with `\0`s.
 */
public fun environGet(environ: MutPtr, environBuf: MutPtr): Result<Unit, Errno> {
    val ret = environGet(environ.rawAddress.toInt(), environBuf.rawAddress.toInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return environment variable data sizes.
 *
 * ## Return
 *
 * Returns the number of environment variable arguments and the size of the
 * environment variable data.
 */
public fun environSizesGet(): Result<Pair<Size, Size>, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val rp1 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = environSizesGet(rp0.asMutPtr().toInt(), rp1.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Pair<Size, Size>, Errno>(Pair(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64), read(ConstPtr((rp1.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64)))
        else -> Result.Err<Pair<Size, Size>, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return the resolution of a clock.
 * Implementations are required to provide a non-zero value for supported clocks. For unsupported clocks,
 * return `errno.inval`.
 * Note: This is similar to `clockGetres` in POSIX.
 *
 * ## Parameters
 *
 * * `id` - The clock for which to return the resolution.
 *
 * ## Return
 *
 * The resolution of the clock, or an error if one happened.
 */
public fun clockResGet(id: Clockid): Result<Timestamp, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Timestamp>())
    val ret = clockResGet(id.value.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Timestamp, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.U64))
        else -> Result.Err<Timestamp, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return the time value of a clock.
 * Note: This is similar to `clockGettime` in POSIX.
 *
 * ## Parameters
 *
 * * `id` - The clock for which to return the time.
 * * `precision` - The maximum lag (exclusive) that the returned time value may have, compared to its actual value.
 *
 * ## Return
 *
 * The time value of the clock.
 */
public fun clockTimeGet(id: Clockid, precision: Timestamp): Result<Timestamp, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Timestamp>())
    val ret = clockTimeGet(id.value.toInt(), precision.toLong(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Timestamp, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.U64))
        else -> Result.Err<Timestamp, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Provide file advisory information on a file descriptor.
 * Note: This is similar to `posixFadvise` in POSIX.
 *
 * ## Parameters
 *
 * * `offset` - The offset within the file to which the advisory applies.
 * * `len` - The length of the region to which the advisory applies.
 * * `advice` - The advice.
 */
public fun fdAdvise(fd: Fd, offset: Filesize, len: Filesize, advice: Advice): Result<Unit, Errno> {
    val ret = fdAdvise((fd.toInt()).toUInt(), (offset.toLong()).toULong(), (len.toLong()).toULong(), advice.value.toInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Force the allocation of space in a file.
 * Note: This is similar to `posixFallocate` in POSIX.
 *
 * ## Parameters
 *
 * * `offset` - The offset at which to start the allocation.
 * * `len` - The length of the area that is allocated.
 */
public fun fdAllocate(fd: Fd, offset: Filesize, len: Filesize): Result<Unit, Errno> {
    val ret = fdAllocate((fd.toInt()).toUInt(), (offset.toLong()).toULong(), (len.toLong()).toULong())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Close a file descriptor.
 * Note: This is similar to `close` in POSIX.
 */
public fun fdClose(fd: Fd): Result<Unit, Errno> {
    val ret = fdClose((fd.toInt()).toUInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Synchronize the data of a file to disk.
 * Note: This is similar to `fdatasync` in POSIX.
 */
public fun fdDatasync(fd: Fd): Result<Unit, Errno> {
    val ret = fdDatasync((fd.toInt()).toUInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Get the attributes of a file descriptor.
 * Note: This returns similar flags to `fsync(fd, F_GETFL)` in POSIX, as well as additional fields.
 *
 * ## Return
 *
 * The buffer where the file descriptor's attributes are stored.
 */
public fun fdFdstatGet(fd: Fd): Result<Fdstat, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Fdstat>())
    val ret = fdFdstatGet(fd.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Fdstat, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = UnresolvedTypeLayout<Fdstat>()))
        else -> Result.Err<Fdstat, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Adjust the flags associated with a file descriptor.
 * Note: This is similar to `fcntl(fd, F_SETFL, flags)` in POSIX.
 *
 * ## Parameters
 *
 * * `flags` - The desired values of the file descriptor flags.
 */
public fun fdFdstatSetFlags(fd: Fd, flags: Fdflags): Result<Unit, Errno> {
    val ret = fdFdstatSetFlags((fd.toInt()).toUInt(), (flags.toInt()).toUShort())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Adjust the rights associated with a file descriptor.
 * This can only be used to remove rights, and returns `errno.notcapable` if called in a way that would attempt to add rights
 *
 * ## Parameters
 *
 * * `fsRightsBase` - The desired rights of the file descriptor.
 */
public fun fdFdstatSetRights(fd: Fd, fsRightsBase: Rights, fsRightsInheriting: Rights): Result<Unit, Errno> {
    val ret = fdFdstatSetRights((fd.toInt()).toUInt(), (fsRightsBase.toLong()).toULong(), (fsRightsInheriting.toLong()).toULong())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return the attributes of an open file.
 *
 * ## Return
 *
 * The buffer where the file's attributes are stored.
 */
public fun fdFilestatGet(fd: Fd): Result<Filestat, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Filestat>())
    val ret = fdFilestatGet(fd.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Filestat, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = UnresolvedTypeLayout<Filestat>()))
        else -> Result.Err<Filestat, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Adjust the size of an open file. If this increases the file's size, the extra bytes are filled with zeros.
 * Note: This is similar to `ftruncate` in POSIX.
 *
 * ## Parameters
 *
 * * `size` - The desired file size.
 */
public fun fdFilestatSetSize(fd: Fd, size: Filesize): Result<Unit, Errno> {
    val ret = fdFilestatSetSize((fd.toInt()).toUInt(), (size.toLong()).toULong())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Adjust the timestamps of an open file or directory.
 * Note: This is similar to `futimens` in POSIX.
 *
 * ## Parameters
 *
 * * `atim` - The desired values of the data access timestamp.
 * * `mtim` - The desired values of the data modification timestamp.
 * * `fstFlags` - A bitmask indicating which timestamps to adjust.
 */
public fun fdFilestatSetTimes(fd: Fd, atim: Timestamp, mtim: Timestamp, fstFlags: Fstflags): Result<Unit, Errno> {
    val ret = fdFilestatSetTimes((fd.toInt()).toUInt(), (atim.toLong()).toULong(), (mtim.toLong()).toULong(), (fstFlags.toInt()).toUShort())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Read from a file descriptor, without using and updating the file descriptor's offset.
 * Note: This is similar to `preadv` in POSIX.
 *
 * ## Parameters
 *
 * * `iovs` - List of scatter/gather vectors in which to store data.
 * * `offset` - The offset within the file at which to read.
 *
 * ## Return
 *
 * The number of bytes read.
 */
public fun fdPread(fd: Fd, iovs: IovecArray, offset: Filesize): Result<Size, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = fdPread(fd.toInt(), iovs.asPtr().rawAddress.toInt(), iovs.len(), offset.toLong(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Size, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64))
        else -> Result.Err<Size, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return a description of the given preopened file descriptor.
 *
 * ## Return
 *
 * The buffer where the description is stored.
 */
public fun fdPrestatGet(fd: Fd): Result<Prestat, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Prestat>())
    val ret = fdPrestatGet(fd.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Prestat, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = UnresolvedTypeLayout<Prestat>()))
        else -> Result.Err<Prestat, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return a description of the given preopened file descriptor.
 *
 * ## Parameters
 *
 * * `path` - A buffer into which to write the preopened directory name.
 */
public fun fdPrestatDirName(fd: Fd, path: MutPtr, pathLen: Size): Result<Unit, Errno> {
    val ret = fdPrestatDirName((fd.toInt()).toUInt(), path.rawAddress.toInt(), pathLen)
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Write to a file descriptor, without using and updating the file descriptor's offset.
 * Note: This is similar to `pwritev` in POSIX.
 *
 * ## Parameters
 *
 * * `iovs` - List of scatter/gather vectors from which to retrieve data.
 * * `offset` - The offset within the file at which to write.
 *
 * ## Return
 *
 * The number of bytes written.
 */
public fun fdPwrite(fd: Fd, iovs: CiovecArray, offset: Filesize): Result<Size, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = fdPwrite(fd.toInt(), iovs.asPtr().rawAddress.toInt(), iovs.len(), offset.toLong(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Size, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64))
        else -> Result.Err<Size, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Read from a file descriptor.
 * Note: This is similar to `readv` in POSIX.
 *
 * ## Parameters
 *
 * * `iovs` - List of scatter/gather vectors to which to store data.
 *
 * ## Return
 *
 * The number of bytes read.
 */
public fun fdRead(fd: Fd, iovs: IovecArray): Result<Size, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = fdRead(fd.toInt(), iovs.asPtr().rawAddress.toInt(), iovs.len(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Size, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64))
        else -> Result.Err<Size, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Read directory entries from a directory.
 * When successful, the contents of the output buffer consist of a sequence of
 * directory entries. Each directory entry consists of a `dirent` object,
 * followed by `dirent.dNamlen` bytes holding the name of the directory
 * entry.
 * This function fills the output buffer as much as possible, potentially
 * truncating the last directory entry. This allows the caller to grow its
 * read buffer size in case it's too small to fit a single large directory
 * entry, or skip the oversized directory entry.
 *
 * ## Parameters
 *
 * * `buf` - The buffer where directory entries are stored
 * * `cookie` - The location within the directory to start reading
 *
 * ## Return
 *
 * The number of bytes stored in the read buffer. If less than the size of the read buffer, the end of the directory has been reached.
 */
public fun fdReaddir(fd: Fd, buf: MutPtr, bufLen: Size, cookie: Dircookie): Result<Size, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = fdReaddir(fd.toInt(), buf.rawAddress.toInt(), bufLen, cookie.toLong(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Size, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64))
        else -> Result.Err<Size, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Atomically replace a file descriptor by renumbering another file descriptor.
 * Due to the strong focus on thread safety, this environment does not provide
 * a mechanism to duplicate or renumber a file descriptor to an arbitrary
 * number, like `dup2()`. This would be prone to race conditions, as an actual
 * file descriptor with the same number could be allocated by a different
 * thread at the same time.
 * This function provides a way to atomically renumber file descriptors, which
 * would disappear if `dup2()` were to be removed entirely.
 *
 * ## Parameters
 *
 * * `to` - The file descriptor to overwrite.
 */
public fun fdRenumber(fd: Fd, to: Fd): Result<Unit, Errno> {
    val ret = fdRenumber((fd.toInt()).toUInt(), (to.toInt()).toUInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Move the offset of a file descriptor.
 * Note: This is similar to `lseek` in POSIX.
 *
 * ## Parameters
 *
 * * `offset` - The number of bytes to move.
 * * `whence` - The base from which the offset is relative.
 *
 * ## Return
 *
 * The new offset of the file descriptor, relative to the start of the file.
 */
public fun fdSeek(fd: Fd, offset: Filedelta, whence: Whence): Result<Filesize, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Filesize>())
    val ret = fdSeek(fd.toInt(), offset, whence.value.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Filesize, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.U64))
        else -> Result.Err<Filesize, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Synchronize the data and metadata of a file to disk.
 * Note: This is similar to `fsync` in POSIX.
 */
public fun fdSync(fd: Fd): Result<Unit, Errno> {
    val ret = fdSync((fd.toInt()).toUInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return the current offset of a file descriptor.
 * Note: This is similar to `lseek(fd, 0, SEEK_CUR)` in POSIX.
 *
 * ## Return
 *
 * The current offset of the file descriptor, relative to the start of the file.
 */
public fun fdTell(fd: Fd): Result<Filesize, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Filesize>())
    val ret = fdTell(fd.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Filesize, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.U64))
        else -> Result.Err<Filesize, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Write to a file descriptor.
 * Note: This is similar to `writev` in POSIX.
 *
 * ## Parameters
 *
 * * `iovs` - List of scatter/gather vectors from which to retrieve data.
 */
public fun fdWrite(fd: Fd, iovs: CiovecArray): Result<Size, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = fdWrite(fd.toInt(), iovs.asPtr().rawAddress.toInt(), iovs.len(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Size, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64))
        else -> Result.Err<Size, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Create a directory.
 * Note: This is similar to `mkdirat` in POSIX.
 *
 * ## Parameters
 *
 * * `path` - The path at which to create the directory.
 */
public fun pathCreateDirectory(fd: Fd, path: String): Result<Unit, Errno> {
    val ret = pathCreateDirectory(fd.toInt(), path.asPtr().toInt(), path.length)
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Return the attributes of a file or directory.
 * Note: This is similar to `stat` in POSIX.
 *
 * ## Parameters
 *
 * * `flags` - Flags determining the method of how the path is resolved.
 * * `path` - The path of the file or directory to inspect.
 *
 * ## Return
 *
 * The buffer where the file's attributes are stored.
 */
public fun pathFilestatGet(fd: Fd, flags: Lookupflags, path: String): Result<Filestat, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Filestat>())
    val ret = pathFilestatGet(fd.toInt(), flags.toInt(), path.asPtr().toInt(), path.length, rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Filestat, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = UnresolvedTypeLayout<Filestat>()))
        else -> Result.Err<Filestat, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Adjust the timestamps of a file or directory.
 * Note: This is similar to `utimensat` in POSIX.
 *
 * ## Parameters
 *
 * * `flags` - Flags determining the method of how the path is resolved.
 * * `path` - The path of the file or directory to operate on.
 * * `atim` - The desired values of the data access timestamp.
 * * `mtim` - The desired values of the data modification timestamp.
 * * `fstFlags` - A bitmask indicating which timestamps to adjust.
 */
public fun pathFilestatSetTimes(fd: Fd, flags: Lookupflags, path: String, atim: Timestamp, mtim: Timestamp, fstFlags: Fstflags): Result<Unit, Errno> {
    val ret = pathFilestatSetTimes(fd.toInt(), flags.toInt(), path.asPtr().toInt(), path.length, atim.toLong(), mtim.toLong(), fstFlags.toInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Create a hard link.
 * Note: This is similar to `linkat` in POSIX.
 *
 * ## Parameters
 *
 * * `oldFlags` - Flags determining the method of how the path is resolved.
 * * `oldPath` - The source path from which to link.
 * * `newFd` - The working directory at which the resolution of the new path starts.
 * * `newPath` - The destination path at which to create the hard link.
 */
public fun pathLink(oldFd: Fd, oldFlags: Lookupflags, oldPath: String, newFd: Fd, newPath: String): Result<Unit, Errno> {
    val ret = pathLink(oldFd.toInt(), oldFlags.toInt(), oldPath.asPtr().toInt(), oldPath.length, newFd.toInt(), newPath.asPtr().toInt(), newPath.length)
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Open a file or directory.
 * The returned file descriptor is not guaranteed to be the lowest-numbered
 * file descriptor not currently open; it is randomized to prevent
 * applications from depending on making assumptions about indexes, since this
 * is error-prone in multi-threaded contexts. The returned file descriptor is
 * guaranteed to be less than 2**31.
 * Note: This is similar to `openat` in POSIX.
 *
 * ## Parameters
 *
 * * `dirflags` - Flags determining the method of how the path is resolved.
 * * `path` - The relative path of the file or directory to open, relative to the
 *   `pathOpen.fd` directory.
 * * `oflags` - The method by which to open the file.
 * * `fsRightsBase` - The initial rights of the newly created file descriptor. The
 *   implementation is allowed to return a file descriptor with fewer rights
 *   than specified, if and only if those rights do not apply to the type of
 *   file being opened.
 *   The *base* rights are rights that will apply to operations using the file
 *   descriptor itself, while the *inheriting* rights are rights that apply to
 *   file descriptors derived from it.
 *
 * ## Return
 *
 * The file descriptor of the file that has been opened.
 */
public fun pathOpen(fd: Fd, dirflags: Lookupflags, path: String, oflags: Oflags, fsRightsBase: Rights, fsRightsInheriting: Rights, fdflags: Fdflags): Result<Fd, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Fd>())
    val ret = pathOpen(fd.toInt(), dirflags.toInt(), path.asPtr().toInt(), path.length, oflags.toInt(), fsRightsBase.toLong(), fsRightsInheriting.toLong(), fdflags.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Fd, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.U32))
        else -> Result.Err<Fd, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Read the contents of a symbolic link.
 * Note: This is similar to `readlinkat` in POSIX.
 *
 * ## Parameters
 *
 * * `path` - The path of the symbolic link from which to read.
 * * `buf` - The buffer to which to write the contents of the symbolic link.
 *
 * ## Return
 *
 * The number of bytes placed in the buffer.
 */
public fun pathReadlink(fd: Fd, path: String, buf: MutPtr, bufLen: Size): Result<Size, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = pathReadlink(fd.toInt(), path.asPtr().toInt(), path.length, buf.rawAddress.toInt(), bufLen, rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Size, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64))
        else -> Result.Err<Size, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Remove a directory.
 * Return `errno.notempty` if the directory is not empty.
 * Note: This is similar to `unlinkat(fd, path, AT_REMOVEDIR)` in POSIX.
 *
 * ## Parameters
 *
 * * `path` - The path to a directory to remove.
 */
public fun pathRemoveDirectory(fd: Fd, path: String): Result<Unit, Errno> {
    val ret = pathRemoveDirectory(fd.toInt(), path.asPtr().toInt(), path.length)
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Rename a file or directory.
 * Note: This is similar to `renameat` in POSIX.
 *
 * ## Parameters
 *
 * * `oldPath` - The source path of the file or directory to rename.
 * * `newFd` - The working directory at which the resolution of the new path starts.
 * * `newPath` - The destination path to which to rename the file or directory.
 */
public fun pathRename(fd: Fd, oldPath: String, newFd: Fd, newPath: String): Result<Unit, Errno> {
    val ret = pathRename(fd.toInt(), oldPath.asPtr().toInt(), oldPath.length, newFd.toInt(), newPath.asPtr().toInt(), newPath.length)
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Create a symbolic link.
 * Note: This is similar to `symlinkat` in POSIX.
 *
 * ## Parameters
 *
 * * `oldPath` - The contents of the symbolic link.
 * * `newPath` - The destination path at which to create the symbolic link.
 */
public fun pathSymlink(oldPath: String, fd: Fd, newPath: String): Result<Unit, Errno> {
    val ret = pathSymlink(oldPath.asPtr().toInt(), oldPath.length, fd.toInt(), newPath.asPtr().toInt(), newPath.length)
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Unlink a file.
 * Return `errno.isdir` if the path refers to a directory.
 * Note: This is similar to `unlinkat(fd, path, 0)` in POSIX.
 *
 * ## Parameters
 *
 * * `path` - The path to a file to unlink.
 */
public fun pathUnlinkFile(fd: Fd, path: String): Result<Unit, Errno> {
    val ret = pathUnlinkFile(fd.toInt(), path.asPtr().toInt(), path.length)
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Concurrently poll for the occurrence of a set of events.
 *
 * ## Parameters
 *
 * * `in_` - The events to which to subscribe.
 * * `out` - The events that have occurred.
 * * `nsubscriptions` - Both the number of subscriptions and events.
 *
 * ## Return
 *
 * The number of events stored.
 */
public fun pollOneoff(input: ConstPtr, out: MutPtr, nsubscriptions: Size): Result<Size, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = pollOneoff(input.rawAddress.toInt(), out.rawAddress.toInt(), nsubscriptions, rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Size, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64))
        else -> Result.Err<Size, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Terminate the process normally. An exit code of 0 indicates successful
 * termination of the program. The meanings of other values is dependent on
 * the environment.
 *
 * ## Parameters
 *
 * * `rval` - The exit code returned by the process.
 */
public fun procExit(rval: Exitcode) {
    procExit((rval.toInt()).toUInt())
}

/**
 * Send a signal to the process of the calling thread.
 * Note: This is similar to `raise` in POSIX.
 *
 * ## Parameters
 *
 * * `sig` - The signal condition to trigger.
 */
public fun procRaise(sig: Signal): Result<Unit, Errno> {
    val ret = procRaise(sig.value.toInt())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Temporarily yield execution of the calling thread.
 * Note: This is similar to `schedYield` in POSIX.
 */
public fun schedYield(): Result<Unit, Errno> {
    val ret = wasiSnapshotPreview1SchedYield()
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Write high-quality random data into a buffer.
 * This function blocks when the implementation is unable to immediately
 * provide sufficient high-quality random data.
 * This function may execute slowly, so when large mounts of random data are
 * required, it's advisable to use this function to seed a pseudo-random
 * number generator, rather than to provide the random data directly.
 *
 * ## Parameters
 *
 * * `buf` - The buffer to fill with random data.
 */
public fun randomGet(buf: MutPtr, bufLen: Size): Result<Unit, Errno> {
    val ret = randomGet(buf.rawAddress.toInt(), bufLen)
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Accept a new incoming connection.
 * Note: This is similar to `accept` in POSIX.
 *
 * ## Parameters
 *
 * * `fd` - The listening socket.
 * * `flags` - The desired values of the file descriptor flags.
 *
 * ## Return
 *
 * New socket connection
 */
public fun sockAccept(fd: Fd, flags: Fdflags): Result<Fd, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Fd>())
    val ret = sockAccept(fd.toInt(), flags.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Fd, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.U32))
        else -> Result.Err<Fd, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Receive a message from a socket.
 * Note: This is similar to `recv` in POSIX, though it also supports reading
 * the data into multiple buffers in the manner of `readv`.
 *
 * ## Parameters
 *
 * * `riData` - List of scatter/gather vectors to which to store data.
 * * `riFlags` - Message flags.
 *
 * ## Return
 *
 * Number of bytes stored in riData and message flags.
 */
public fun sockRecv(fd: Fd, riData: IovecArray, riFlags: Riflags): Result<Pair<Size, Roflags>, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val rp1 = MaybeUninit.uninit(UnresolvedTypeLayout<Roflags>())
    val ret = sockRecv(fd.toInt(), riData.asPtr().rawAddress.toInt(), riData.len(), riFlags.toInt(), rp0.asMutPtr().toInt(), rp1.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Pair<Size, Roflags>, Errno>(Pair(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64), read(ConstPtr((rp1.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.U16)))
        else -> Result.Err<Pair<Size, Roflags>, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Send a message on a socket.
 * Note: This is similar to `send` in POSIX, though it also supports writing
 * the data from multiple buffers in the manner of `writev`.
 *
 * ## Parameters
 *
 * * `siData` - List of scatter/gather vectors to which to retrieve data
 * * `siFlags` - Message flags.
 *
 * ## Return
 *
 * Number of bytes transmitted.
 */
public fun sockSend(fd: Fd, siData: CiovecArray, siFlags: Siflags): Result<Size, Errno> {
    val rp0 = MaybeUninit.uninit(UnresolvedTypeLayout<Size>())
    val ret = sockSend(fd.toInt(), siData.asPtr().rawAddress.toInt(), siData.len(), siFlags.toInt(), rp0.asMutPtr().toInt())
    return when (ret) {
        0 -> Result.Ok<Size, Errno>(read(ConstPtr((rp0.asMutPtr().toInt()).toLong()), typeLayoutT = PrimitiveLayouts.USIZE64))
        else -> Result.Err<Size, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Shut down socket send and receive channels.
 * Note: This is similar to `shutdown` in POSIX.
 *
 * ## Parameters
 *
 * * `how` - Which channels on the socket to shut down.
 */
public fun sockShutdown(fd: Fd, how: Sdflags): Result<Unit, Errno> {
    val ret = sockShutdown((fd.toInt()).toUInt(), (how.toInt()).toUByte())
    return when (ret) {
        0 -> Result.Ok<Unit, Errno>(Unit)
        else -> Result.Err<Unit, Errno>(Errno(ret.toUShort(), fieldLayout = PrimitiveLayouts.U16))
    }
}

/**
 * Read command-line argument data.
 * The size of the array should match that returned by `argsSizesGet`.
 * Each argument is expected to be `\0` terminated.
 */
public expect fun argsGet(arg0: Int, arg1: Int): Int

/**
 * Return command-line argument data sizes.
 */
public expect fun argsSizesGet(arg0: Int, arg1: Int): Int

/**
 * Read environment variable data.
 * The sizes of the buffers should match that returned by `environSizesGet`.
 * Key/value pairs are expected to be joined with `=`s, and terminated with `\0`s.
 */
public expect fun environGet(arg0: Int, arg1: Int): Int

/**
 * Return environment variable data sizes.
 */
public expect fun environSizesGet(arg0: Int, arg1: Int): Int

/**
 * Return the resolution of a clock.
 * Implementations are required to provide a non-zero value for supported clocks. For unsupported clocks,
 * return `errno.inval`.
 * Note: This is similar to `clockGetres` in POSIX.
 */
public expect fun clockResGet(arg0: Int, arg1: Int): Int

/**
 * Return the time value of a clock.
 * Note: This is similar to `clockGettime` in POSIX.
 */
public expect fun clockTimeGet(arg0: Int, arg1: Long, arg2: Int): Int

/**
 * Provide file advisory information on a file descriptor.
 * Note: This is similar to `posixFadvise` in POSIX.
 */
public expect fun fdAdvise(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

/**
 * Force the allocation of space in a file.
 * Note: This is similar to `posixFallocate` in POSIX.
 */
public expect fun fdAllocate(arg0: Int, arg1: Long, arg2: Long): Int

/**
 * Close a file descriptor.
 * Note: This is similar to `close` in POSIX.
 */
public expect fun fdClose(arg0: Int): Int

/**
 * Synchronize the data of a file to disk.
 * Note: This is similar to `fdatasync` in POSIX.
 */
public expect fun fdDatasync(arg0: Int): Int

/**
 * Get the attributes of a file descriptor.
 * Note: This returns similar flags to `fsync(fd, F_GETFL)` in POSIX, as well as additional fields.
 */
public expect fun fdFdstatGet(arg0: Int, arg1: Int): Int

/**
 * Adjust the flags associated with a file descriptor.
 * Note: This is similar to `fcntl(fd, F_SETFL, flags)` in POSIX.
 */
public expect fun fdFdstatSetFlags(arg0: Int, arg1: Int): Int

/**
 * Adjust the rights associated with a file descriptor.
 * This can only be used to remove rights, and returns `errno.notcapable` if called in a way that would attempt to add rights
 */
public expect fun fdFdstatSetRights(arg0: Int, arg1: Long, arg2: Long): Int

/**
 * Return the attributes of an open file.
 */
public expect fun fdFilestatGet(arg0: Int, arg1: Int): Int

/**
 * Adjust the size of an open file. If this increases the file's size, the extra bytes are filled with zeros.
 * Note: This is similar to `ftruncate` in POSIX.
 */
public expect fun fdFilestatSetSize(arg0: Int, arg1: Long): Int

/**
 * Adjust the timestamps of an open file or directory.
 * Note: This is similar to `futimens` in POSIX.
 */
public expect fun fdFilestatSetTimes(arg0: Int, arg1: Long, arg2: Long, arg3: Int): Int

/**
 * Read from a file descriptor, without using and updating the file descriptor's offset.
 * Note: This is similar to `preadv` in POSIX.
 */
public expect fun fdPread(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

/**
 * Return a description of the given preopened file descriptor.
 */
public expect fun fdPrestatGet(arg0: Int, arg1: Int): Int

/**
 * Return a description of the given preopened file descriptor.
 */
public expect fun fdPrestatDirName(arg0: Int, arg1: Int, arg2: Int): Int

/**
 * Write to a file descriptor, without using and updating the file descriptor's offset.
 * Note: This is similar to `pwritev` in POSIX.
 */
public expect fun fdPwrite(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

/**
 * Read from a file descriptor.
 * Note: This is similar to `readv` in POSIX.
 */
public expect fun fdRead(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

/**
 * Read directory entries from a directory.
 * When successful, the contents of the output buffer consist of a sequence of
 * directory entries. Each directory entry consists of a `dirent` object,
 * followed by `dirent.dNamlen` bytes holding the name of the directory
 * entry.
 * This function fills the output buffer as much as possible, potentially
 * truncating the last directory entry. This allows the caller to grow its
 * read buffer size in case it's too small to fit a single large directory
 * entry, or skip the oversized directory entry.
 */
public expect fun fdReaddir(arg0: Int, arg1: Int, arg2: Int, arg3: Long, arg4: Int): Int

/**
 * Atomically replace a file descriptor by renumbering another file descriptor.
 * Due to the strong focus on thread safety, this environment does not provide
 * a mechanism to duplicate or renumber a file descriptor to an arbitrary
 * number, like `dup2()`. This would be prone to race conditions, as an actual
 * file descriptor with the same number could be allocated by a different
 * thread at the same time.
 * This function provides a way to atomically renumber file descriptors, which
 * would disappear if `dup2()` were to be removed entirely.
 */
public expect fun fdRenumber(arg0: Int, arg1: Int): Int

/**
 * Move the offset of a file descriptor.
 * Note: This is similar to `lseek` in POSIX.
 */
public expect fun fdSeek(arg0: Int, arg1: Long, arg2: Int, arg3: Int): Int

/**
 * Synchronize the data and metadata of a file to disk.
 * Note: This is similar to `fsync` in POSIX.
 */
public expect fun fdSync(arg0: Int): Int

/**
 * Return the current offset of a file descriptor.
 * Note: This is similar to `lseek(fd, 0, SEEK_CUR)` in POSIX.
 */
public expect fun fdTell(arg0: Int, arg1: Int): Int

/**
 * Write to a file descriptor.
 * Note: This is similar to `writev` in POSIX.
 */
public expect fun fdWrite(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

/**
 * Create a directory.
 * Note: This is similar to `mkdirat` in POSIX.
 */
public expect fun pathCreateDirectory(arg0: Int, arg1: Int, arg2: Int): Int

/**
 * Return the attributes of a file or directory.
 * Note: This is similar to `stat` in POSIX.
 */
public expect fun pathFilestatGet(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

/**
 * Adjust the timestamps of a file or directory.
 * Note: This is similar to `utimensat` in POSIX.
 */
public expect fun pathFilestatSetTimes(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Long, arg5: Long, arg6: Int): Int

/**
 * Create a hard link.
 * Note: This is similar to `linkat` in POSIX.
 */
public expect fun pathLink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int): Int

/**
 * Open a file or directory.
 * The returned file descriptor is not guaranteed to be the lowest-numbered
 * file descriptor not currently open; it is randomized to prevent
 * applications from depending on making assumptions about indexes, since this
 * is error-prone in multi-threaded contexts. The returned file descriptor is
 * guaranteed to be less than 2**31.
 * Note: This is similar to `openat` in POSIX.
 */
public expect fun pathOpen(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Long, arg6: Long, arg7: Int, arg8: Int): Int

/**
 * Read the contents of a symbolic link.
 * Note: This is similar to `readlinkat` in POSIX.
 */
public expect fun pathReadlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

/**
 * Remove a directory.
 * Return `errno.notempty` if the directory is not empty.
 * Note: This is similar to `unlinkat(fd, path, AT_REMOVEDIR)` in POSIX.
 */
public expect fun pathRemoveDirectory(arg0: Int, arg1: Int, arg2: Int): Int

/**
 * Rename a file or directory.
 * Note: This is similar to `renameat` in POSIX.
 */
public expect fun pathRename(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

/**
 * Create a symbolic link.
 * Note: This is similar to `symlinkat` in POSIX.
 */
public expect fun pathSymlink(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

/**
 * Unlink a file.
 * Return `errno.isdir` if the path refers to a directory.
 * Note: This is similar to `unlinkat(fd, path, 0)` in POSIX.
 */
public expect fun pathUnlinkFile(arg0: Int, arg1: Int, arg2: Int): Int

/**
 * Concurrently poll for the occurrence of a set of events.
 */
public expect fun pollOneoff(arg0: Int, arg1: Int, arg2: Int, arg3: Int): Int

/**
 * Terminate the process normally. An exit code of 0 indicates successful
 * termination of the program. The meanings of other values is dependent on
 * the environment.
 */
public expect fun procExit(arg0: Int): Nothing

/**
 * Send a signal to the process of the calling thread.
 * Note: This is similar to `raise` in POSIX.
 */
public expect fun procRaise(arg0: Int): Int

/**
 * Temporarily yield execution of the calling thread.
 * Note: This is similar to `schedYield` in POSIX.
 */
public expect fun wasiSnapshotPreview1SchedYield(): Int

/**
 * Write high-quality random data into a buffer.
 * This function blocks when the implementation is unable to immediately
 * provide sufficient high-quality random data.
 * This function may execute slowly, so when large mounts of random data are
 * required, it's advisable to use this function to seed a pseudo-random
 * number generator, rather than to provide the random data directly.
 */
public expect fun randomGet(arg0: Int, arg1: Int): Int

/**
 * Accept a new incoming connection.
 * Note: This is similar to `accept` in POSIX.
 */
public expect fun sockAccept(arg0: Int, arg1: Int, arg2: Int): Int

/**
 * Receive a message from a socket.
 * Note: This is similar to `recv` in POSIX, though it also supports reading
 * the data into multiple buffers in the manner of `readv`.
 */
public expect fun sockRecv(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int

/**
 * Send a message on a socket.
 * Note: This is similar to `send` in POSIX, though it also supports writing
 * the data from multiple buffers in the manner of `writev`.
 */
public expect fun sockSend(arg0: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int

/**
 * Shut down socket send and receive channels.
 * Note: This is similar to `shutdown` in POSIX.
 */
public expect fun sockShutdown(arg0: Int, arg1: Int): Int
