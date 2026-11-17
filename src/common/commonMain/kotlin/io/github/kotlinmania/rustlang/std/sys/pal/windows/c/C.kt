// port-lint: source sys/pal/windows/c.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.c

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.os.raw.CUint
import io.github.kotlinmania.rustlang.std.os.raw.CUlong
import io.github.kotlinmania.rustlang.std.os.raw.CUshort
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.AF_INET
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.AF_INET6
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.AF_UNIX
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.AddressFamily
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Bool
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.CP_UTF8
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.CREATE_NEW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.CREATE_NEW_PROCESS_GROUP
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.CREATE_UNICODE_ENVIRONMENT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.CREATE_WAITABLE_TIMER_HIGH_RESOLUTION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.DETACHED_PROCESS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.DNS_ERROR_RECORD_TIMED_OUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.DUPLICATE_SAME_ACCESS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_ACCESS_DENIED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_ALREADY_EXISTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_BAD_NETPATH
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_BAD_NET_NAME
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_BAD_PATHNAME
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_BROKEN_PIPE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_BUSY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_CALL_NOT_IMPLEMENTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_CANT_ACCESS_FILE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_CANT_RESOLVE_FILENAME
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_COUNTER_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_CTX_CLIENT_QUERY_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_CTX_MODEM_RESPONSE_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_DELETE_PENDING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_DIRECTORY_NOT_SUPPORTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_DIR_NOT_EMPTY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_DISK_FULL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_DISK_QUOTA_EXCEEDED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_DRIVER_CANCEL_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_DS_TIMELIMIT_EXCEEDED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_FILENAME_EXCED_RANGE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_FILE_EXISTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_FILE_NOT_FOUND
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_FILE_TOO_LARGE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_HANDLE_DISK_FULL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_HOST_UNREACHABLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_INSUFFICIENT_BUFFER
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_INVALID_DRIVE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_INVALID_FUNCTION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_INVALID_HANDLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_INVALID_NAME
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_INVALID_PARAMETER
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_IO_DEVICE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_IPSEC_IKE_TIMED_OUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NEGATIVE_SEEK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NETWORK_UNREACHABLE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NOT_ENOUGH_MEMORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NOT_FOUND
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NOT_SAME_DEVICE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NOT_SUPPORTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NO_DATA
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NO_MORE_FILES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_OPERATION_ABORTED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_OUTOFMEMORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_PATH_NOT_FOUND
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_POSSIBLE_DEADLOCK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_RESOURCE_CALL_TIMED_OUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_RUNLEVEL_SWITCH_AGENT_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_RUNLEVEL_SWITCH_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_SEEK_ON_DEVICE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_SEM_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_SERVICE_REQUEST_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_SHARING_VIOLATION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_SUCCESS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_TOO_MANY_LINKS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_TOO_MANY_OPEN_FILES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_WRITE_PROTECT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.EXTENDED_STARTUPINFO_PRESENT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_ATTRIBUTE_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_ATTRIBUTE_NORMAL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_ATTRIBUTE_READONLY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_ATTRIBUTE_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_BEGIN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_CREATE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_CURRENT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_DIRECTORY_FILE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_DISPOSITION_FLAG_DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_DISPOSITION_FLAG_IGNORE_READONLY_ATTRIBUTE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_DISPOSITION_FLAG_POSIX_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_END
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_FLAG_BACKUP_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_FLAG_OPEN_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_FLAG_POSIX_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_GENERIC_WRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_LIST_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_NON_DIRECTORY_FILE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_OPEN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_OPEN_IF
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_OPEN_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_OVERWRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_OVERWRITE_IF
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_PIPE_BYTE_STREAM_MODE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_PIPE_BYTE_STREAM_TYPE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_PIPE_QUEUE_OPERATION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_READ_ATTRIBUTES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_RENAME_FLAG_POSIX_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_RENAME_FLAG_REPLACE_IF_EXISTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_SHARE_DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_SHARE_READ
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_SHARE_WRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_SYNCHRONOUS_IO_NONALERT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_TRAVERSE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_WRITE_ATTRIBUTES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_WRITE_DATA
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FORMAT_MESSAGE_FROM_HMODULE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FORMAT_MESSAGE_FROM_SYSTEM
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FORMAT_MESSAGE_IGNORE_INSERTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FRS_ERR_SYSVOL_POPULATE_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FSCTL_GET_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FSCTL_SET_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.GENERIC_WRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Guid
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.HANDLE_FLAG_INHERIT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Handle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.InitOnce
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.IoStatusBlock
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.IoStatusBlock0
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.LOAD_LIBRARY_SEARCH_SYSTEM32
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.LOCKFILE_EXCLUSIVE_LOCK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.LOCKFILE_FAIL_IMMEDIATELY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.MOVEFILE_REPLACE_EXISTING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Ntstatus
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.OBJ_DONT_REPARSE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.OBJ_INHERIT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.OPEN_ALWAYS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.OPEN_EXISTING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ObjectAttributes
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.PROGRESS_CONTINUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SECURITY_SQOS_PRESENT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STACK_SIZE_PARAM_IS_A_RESERVATION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STARTF_FORCEOFFFEEDBACK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STARTF_FORCEONFEEDBACK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STARTF_RUNFULLSCREEN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STARTF_UNTRUSTEDSOURCE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STARTF_USESHOWWINDOW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STARTF_USESTDHANDLES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STATUS_PENDING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SYMBOLIC_LINK_FLAG_ALLOW_UNPRIVILEGED_CREATE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SYMBOLIC_LINK_FLAG_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SYMLINK_FLAG_RELATIVE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SYNCHRONIZE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.TIMER_ALL_ACCESS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.TRUNCATE_EXISTING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.VOLUME_NAME_DOS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.VOLUME_NAME_NT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WAIT_OBJECT_0
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WAIT_TIMEOUT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSA_FLAG_NO_HANDLE_INHERIT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.WSA_FLAG_OVERLAPPED

// C definitions used by libnative that don't belong in liblibc

public fun interface AtexitCbCallback {
    public operator fun invoke()
}

public typealias Wchar = UShort

public val FILE_DEVICE_NAMED_PIPE: UInt = 0x00000011u

public val FILE_DEVICE_CONSOLE: UInt = 0x00000050u

// https://learn.microsoft.com/en-us/cpp/c-runtime-library/exit-success-exit-failure?view=msvc-170
public val EXIT_SUCCESS: UInt = 0u

public val EXIT_FAILURE: UInt = 1u

// ADDRESS_FAMILY
public val AF_INET: AddressFamily = AF_INET.toUShort()

// Short-hand for castUnsigned.
// It prevents most of the casts below from spilling over multiple lines.
public fun cu(n: Int): UInt = n.castUnsigned()

public val AF_INET6: AddressFamily = AF_INET6.toUShort()

public val AF_UNIX: AddressFamily = AF_UNIX.toUShort()

// Desktop specific functions & types
public val EXCEPTION_CONTINUE_SEARCH: Int = 0

public val INVALID_HANDLE_VALUE: Handle = withoutProvenanceMut(null)

// dwShareMode -- CreateFile
public val FILE_SHARE_DELETE: UInt = cu(FILE_SHARE_DELETE)

public val FILE_SHARE_READ: UInt = cu(FILE_SHARE_READ)

public val FILE_SHARE_WRITE: UInt = cu(FILE_SHARE_WRITE)

// CreateOptions (ULONG) -- NtCreateFile
public val FILE_DIRECTORY_FILE: UInt = cu(FILE_DIRECTORY_FILE)

public val FILE_SYNCHRONOUS_IO_NONALERT: UInt = cu(FILE_SYNCHRONOUS_IO_NONALERT)

public val FILE_NON_DIRECTORY_FILE: UInt = cu(FILE_NON_DIRECTORY_FILE)

public val FILE_OPEN_REPARSE_POINT: UInt = cu(FILE_OPEN_REPARSE_POINT)

// dwDesiredAccess -- many, many functions
public val DELETE: UInt = cu(DELETE)

public val SYNCHRONIZE: UInt = cu(SYNCHRONIZE)

public val GENERIC_WRITE: UInt = cu(GENERIC_WRITE)

public val FILE_GENERIC_WRITE: UInt = cu(FILE_GENERIC_WRITE)

public val FILE_LIST_DIRECTORY: UInt = cu(FILE_LIST_DIRECTORY)

public val FILE_WRITE_DATA: UInt = cu(FILE_WRITE_DATA)

public val FILE_READ_ATTRIBUTES: UInt = cu(FILE_READ_ATTRIBUTES)

public val FILE_WRITE_ATTRIBUTES: UInt = cu(FILE_WRITE_ATTRIBUTES)

public val FILE_TRAVERSE: UInt = cu(FILE_TRAVERSE)

public val TIMER_ALL_ACCESS: UInt = cu(TIMER_ALL_ACCESS)

// dwOptions -- DuplicateHandle
public val DUPLICATE_SAME_ACCESS: UInt = cu(DUPLICATE_SAME_ACCESS)

// dwFlags -- GetModuleHandleEx
public val GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS: UInt = cu(GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS)

public val GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT: UInt = cu(GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT)

// dwSecurityQosFlags -- CreateFile
public val SECURITY_SQOS_PRESENT: UInt = cu(SECURITY_SQOS_PRESENT)

// dwFlags -- LockFileEx
public val LOCKFILE_EXCLUSIVE_LOCK: UInt = cu(LOCKFILE_EXCLUSIVE_LOCK)

public val LOCKFILE_FAIL_IMMEDIATELY: UInt = cu(LOCKFILE_FAIL_IMMEDIATELY)

// dwFileFlags -- CreateFile
public val FILE_FLAG_BACKUP_SEMANTICS: UInt = cu(FILE_FLAG_BACKUP_SEMANTICS)

public val FILE_FLAG_OPEN_REPARSE_POINT: UInt = cu(FILE_FLAG_OPEN_REPARSE_POINT)

public val FILE_FLAG_POSIX_SEMANTICS: UInt = cu(FILE_FLAG_POSIX_SEMANTICS)

// dwFileAttributes -- CreateFile
public val FILE_ATTRIBUTE_DIRECTORY: UInt = cu(FILE_ATTRIBUTE_DIRECTORY)

public val FILE_ATTRIBUTE_NORMAL: UInt = cu(FILE_ATTRIBUTE_NORMAL)

public val FILE_ATTRIBUTE_READONLY: UInt = cu(FILE_ATTRIBUTE_READONLY)

public val FILE_ATTRIBUTE_REPARSE_POINT: UInt = cu(FILE_ATTRIBUTE_REPARSE_POINT)

// dwFlags -- WSASocket
public val WSA_FLAG_NO_HANDLE_INHERIT: UInt = cu(WSA_FLAG_NO_HANDLE_INHERIT)

public val WSA_FLAG_OVERLAPPED: UInt = cu(WSA_FLAG_OVERLAPPED)

// Flags (ULONG) -- FILE_RENAME_INFORMATION
public val FILE_RENAME_FLAG_REPLACE_IF_EXISTS: UInt = cu(FILE_RENAME_FLAG_REPLACE_IF_EXISTS)

public val FILE_RENAME_FLAG_POSIX_SEMANTICS: UInt = cu(FILE_RENAME_FLAG_POSIX_SEMANTICS)

// Attributes (ULONG) -- OBJECT_ATTRIBUTES
public val OBJ_INHERIT: UInt = cu(OBJ_INHERIT)

public val OBJ_DONT_REPARSE: UInt = cu(OBJ_DONT_REPARSE)

// Flags (DWORD) -- FILE_DISPOSITION_INFO_EX
public val FILE_DISPOSITION_FLAG_DELETE: UInt = cu(FILE_DISPOSITION_FLAG_DELETE)

public val FILE_DISPOSITION_FLAG_POSIX_SEMANTICS: UInt = cu(FILE_DISPOSITION_FLAG_POSIX_SEMANTICS)

public val FILE_DISPOSITION_FLAG_IGNORE_READONLY_ATTRIBUTE: UInt = cu(FILE_DISPOSITION_FLAG_IGNORE_READONLY_ATTRIBUTE)

// Flags (ULONG) -- SymbolicLinkReparseBuffer
public val SYMLINK_FLAG_RELATIVE: UInt = cu(SYMLINK_FLAG_RELATIVE)

// dwFlags -- CreateSymbolicLink
public val SYMBOLIC_LINK_FLAG_DIRECTORY: UInt = cu(SYMBOLIC_LINK_FLAG_DIRECTORY)

public val SYMBOLIC_LINK_FLAG_ALLOW_UNPRIVILEGED_CREATE: UInt = cu(SYMBOLIC_LINK_FLAG_ALLOW_UNPRIVILEGED_CREATE)

// dwCreationFlag -- CreateProcess
public val DETACHED_PROCESS: UInt = cu(DETACHED_PROCESS)

public val CREATE_NEW_PROCESS_GROUP: UInt = cu(CREATE_NEW_PROCESS_GROUP)

public val CREATE_UNICODE_ENVIRONMENT: UInt = cu(CREATE_UNICODE_ENVIRONMENT)

public val EXTENDED_STARTUPINFO_PRESENT: UInt = cu(EXTENDED_STARTUPINFO_PRESENT)

// dwFlags -- STARTUPINFO
public val STARTF_USESTDHANDLES: UInt = cu(STARTF_USESTDHANDLES)

public val STARTF_USESHOWWINDOW: UInt = cu(STARTF_USESHOWWINDOW)

public val STARTF_UNTRUSTEDSOURCE: UInt = cu(STARTF_UNTRUSTEDSOURCE)

public val STARTF_FORCEONFEEDBACK: UInt = cu(STARTF_FORCEONFEEDBACK)

public val STARTF_FORCEOFFFEEDBACK: UInt = cu(STARTF_FORCEOFFFEEDBACK)

public val STARTF_RUNFULLSCREEN: UInt = cu(STARTF_RUNFULLSCREEN)

// dwFlags -- MoveFileEx
public val MOVEFILE_REPLACE_EXISTING: UInt = cu(MOVEFILE_REPLACE_EXISTING)

// dwFlags -- GetFinalPathNameByHandle
public val VOLUME_NAME_DOS: UInt = cu(VOLUME_NAME_DOS)

public val VOLUME_NAME_NT: UInt = cu(VOLUME_NAME_NT)

// dwFlags -- FormatMessageW
public val FORMAT_MESSAGE_FROM_SYSTEM: UInt = cu(FORMAT_MESSAGE_FROM_SYSTEM)

public val FORMAT_MESSAGE_IGNORE_INSERTS: UInt = cu(FORMAT_MESSAGE_IGNORE_INSERTS)

public val FORMAT_MESSAGE_FROM_HMODULE: UInt = cu(FORMAT_MESSAGE_FROM_HMODULE)

// dwFlags -- SetHandleInformation
public val HANDLE_FLAG_INHERIT: UInt = cu(HANDLE_FLAG_INHERIT)

// dwCreationFlags -- CreateThread
public val STACK_SIZE_PARAM_IS_A_RESERVATION: UInt = cu(STACK_SIZE_PARAM_IS_A_RESERVATION)

// dwFlags -- CreateWaitableTimerExW
public val CREATE_WAITABLE_TIMER_HIGH_RESOLUTION: UInt = cu(CREATE_WAITABLE_TIMER_HIGH_RESOLUTION)

// dwFlags --LoadLibraryExA
public val LOAD_LIBRARY_SEARCH_SYSTEM32: UInt = cu(LOAD_LIBRARY_SEARCH_SYSTEM32)

// dwCreationDisposition -- CreateFile
public val CREATE_NEW: UInt = cu(CREATE_NEW)

public val OPEN_ALWAYS: UInt = cu(OPEN_ALWAYS)

public val OPEN_EXISTING: UInt = cu(OPEN_EXISTING)

public val TRUNCATE_EXISTING: UInt = cu(TRUNCATE_EXISTING)

// CreateDisposition (ULONG) -- NtCreateFile
public val FILE_OPEN: UInt = cu(FILE_OPEN)

public val FILE_CREATE: UInt = cu(FILE_CREATE)

public val FILE_OPEN_IF: UInt = cu(FILE_OPEN_IF)

public val FILE_OVERWRITE: UInt = cu(FILE_OVERWRITE)

public val FILE_OVERWRITE_IF: UInt = cu(FILE_OVERWRITE_IF)

// NamedPipeType (ULONG) -- NtCreateNamedPipeFile
public val FILE_PIPE_BYTE_STREAM_TYPE: UInt = cu(FILE_PIPE_BYTE_STREAM_TYPE)

// ReadMode (ULONG) -- NtCreateNamedPipeFile
public val FILE_PIPE_BYTE_STREAM_MODE: UInt = cu(FILE_PIPE_BYTE_STREAM_MODE)

// CompletionMode (ULONG) -- NtCreateNamedPipeFile
public val FILE_PIPE_QUEUE_OPERATION: UInt = cu(FILE_PIPE_QUEUE_OPERATION)

// dwMoveMethod -- SetFilePointerEx
public val FILE_BEGIN: UInt = cu(FILE_BEGIN)

public val FILE_END: UInt = cu(FILE_END)

public val FILE_CURRENT: UInt = cu(FILE_CURRENT)

// dwIoControlCode -- DeviceIoControl
public val FSCTL_GET_REPARSE_POINT: UInt = cu(FSCTL_GET_REPARSE_POINT)

public val FSCTL_SET_REPARSE_POINT: UInt = cu(FSCTL_SET_REPARSE_POINT)

// GetACP return value (UINT)
public val CP_UTF8: UInt = CP_UTF8.castUnsigned()

// WaitForSingleObject return value (DWORD)
public val WAIT_OBJECT_0: UInt = WAIT_OBJECT_0.castUnsigned()

// LPPROGRESS_ROUTINE return value (DWORD)
public val PROGRESS_CONTINUE: UInt = cu(PROGRESS_CONTINUE)

// KNOWNFOLDERIDs (GUID)
public val FOLDERID_DESKTOP: Guid = Guid.fromU128(U128(0xb4bfcc3adb2c424cuL, 0xb0297fe99a87c641uL))

public val FOLDERID_DOCUMENTS: Guid = Guid.fromU128(U128(0xfdd39ad0238f46afuL, 0xadb46c85480369c7uL))

public val FOLDERID_DOWNLOADS: Guid = Guid.fromU128(U128(0x374de290123f4565uL, 0x916439c4925e467buL))

public val FOLDERID_LOCAL_APP_DATA: Guid = Guid.fromU128(U128(0xf1b327856fba4fcfuL, 0x9d557b8e7f157091uL))

public val FOLDERID_MUSIC: Guid = Guid.fromU128(U128(0x4bd8d5716d1948d3uL, 0xbe97422220080e43uL))

public val FOLDERID_PICTURES: Guid = Guid.fromU128(U128(0x33e281304e1e4676uL, 0x835a98395c3bc3bbuL))

public val FOLDERID_ROAMING_APP_DATA: Guid = Guid.fromU128(U128(0x3eb685db65f94cf6uL, 0xa03ae3ef65729f3duL))

public val FOLDERID_VIDEOS: Guid = Guid.fromU128(U128(0x18989b1d99b5455buL, 0x841cab7c74e4ddfcuL))

// Errors
public val ERROR_SUCCESS: UInt = cu(ERROR_SUCCESS)

public val ERROR_ACCESS_DENIED: UInt = cu(ERROR_ACCESS_DENIED)

public val ERROR_ALREADY_EXISTS: UInt = cu(ERROR_ALREADY_EXISTS)

public val ERROR_BAD_NETPATH: UInt = cu(ERROR_BAD_NETPATH)

public val ERROR_BAD_NET_NAME: UInt = cu(ERROR_BAD_NET_NAME)

public val ERROR_CANT_ACCESS_FILE: UInt = cu(ERROR_CANT_ACCESS_FILE)

public val ERROR_DELETE_PENDING: UInt = cu(ERROR_DELETE_PENDING)

public val ERROR_DIRECTORY: UInt = cu(ERROR_DIRECTORY)

public val ERROR_DIR_NOT_EMPTY: UInt = cu(ERROR_DIR_NOT_EMPTY)

public val ERROR_FILE_NOT_FOUND: UInt = cu(ERROR_FILE_NOT_FOUND)

public val ERROR_INSUFFICIENT_BUFFER: UInt = cu(ERROR_INSUFFICIENT_BUFFER)

public val ERROR_INVALID_FUNCTION: UInt = cu(ERROR_INVALID_FUNCTION)

public val ERROR_INVALID_HANDLE: UInt = cu(ERROR_INVALID_HANDLE)

public val ERROR_INVALID_PARAMETER: UInt = cu(ERROR_INVALID_PARAMETER)

public val ERROR_NOT_FOUND: UInt = cu(ERROR_NOT_FOUND)

public val ERROR_NOT_SUPPORTED: UInt = cu(ERROR_NOT_SUPPORTED)

public val ERROR_NO_MORE_FILES: UInt = cu(ERROR_NO_MORE_FILES)

public val ERROR_OPERATION_ABORTED: UInt = cu(ERROR_OPERATION_ABORTED)

public val ERROR_PATH_NOT_FOUND: UInt = cu(ERROR_PATH_NOT_FOUND)

public val ERROR_SHARING_VIOLATION: UInt = cu(ERROR_SHARING_VIOLATION)

public val ERROR_TIMEOUT: UInt = cu(ERROR_TIMEOUT)

public val ERROR_FILE_EXISTS: UInt = cu(ERROR_FILE_EXISTS)

public val ERROR_BROKEN_PIPE: UInt = cu(ERROR_BROKEN_PIPE)

public val ERROR_INVALID_DRIVE: UInt = cu(ERROR_INVALID_DRIVE)

public val ERROR_NO_DATA: UInt = cu(ERROR_NO_DATA)

public val ERROR_INVALID_NAME: UInt = cu(ERROR_INVALID_NAME)

public val ERROR_BAD_PATHNAME: UInt = cu(ERROR_BAD_PATHNAME)

public val ERROR_NOT_ENOUGH_MEMORY: UInt = cu(ERROR_NOT_ENOUGH_MEMORY)

public val ERROR_OUTOFMEMORY: UInt = cu(ERROR_OUTOFMEMORY)

public val ERROR_SEM_TIMEOUT: UInt = cu(ERROR_SEM_TIMEOUT)

public val ERROR_DRIVER_CANCEL_TIMEOUT: UInt = cu(ERROR_DRIVER_CANCEL_TIMEOUT)

public val ERROR_SERVICE_REQUEST_TIMEOUT: UInt = cu(ERROR_SERVICE_REQUEST_TIMEOUT)

public val ERROR_COUNTER_TIMEOUT: UInt = cu(ERROR_COUNTER_TIMEOUT)

public val ERROR_RESOURCE_CALL_TIMED_OUT: UInt = cu(ERROR_RESOURCE_CALL_TIMED_OUT)

public val ERROR_CTX_MODEM_RESPONSE_TIMEOUT: UInt = cu(ERROR_CTX_MODEM_RESPONSE_TIMEOUT)

public val ERROR_CTX_CLIENT_QUERY_TIMEOUT: UInt = cu(ERROR_CTX_CLIENT_QUERY_TIMEOUT)

public val ERROR_DS_TIMELIMIT_EXCEEDED: UInt = cu(ERROR_DS_TIMELIMIT_EXCEEDED)

public val DNS_ERROR_RECORD_TIMED_OUT: UInt = cu(DNS_ERROR_RECORD_TIMED_OUT)

public val ERROR_IPSEC_IKE_TIMED_OUT: UInt = cu(ERROR_IPSEC_IKE_TIMED_OUT)

public val ERROR_RUNLEVEL_SWITCH_TIMEOUT: UInt = cu(ERROR_RUNLEVEL_SWITCH_TIMEOUT)

public val ERROR_RUNLEVEL_SWITCH_AGENT_TIMEOUT: UInt = cu(ERROR_RUNLEVEL_SWITCH_AGENT_TIMEOUT)

public val ERROR_CALL_NOT_IMPLEMENTED: UInt = cu(ERROR_CALL_NOT_IMPLEMENTED)

public val ERROR_HOST_UNREACHABLE: UInt = cu(ERROR_HOST_UNREACHABLE)

public val ERROR_NETWORK_UNREACHABLE: UInt = cu(ERROR_NETWORK_UNREACHABLE)

public val ERROR_DIRECTORY_NOT_SUPPORTED: UInt = cu(ERROR_DIRECTORY_NOT_SUPPORTED)

public val ERROR_WRITE_PROTECT: UInt = cu(ERROR_WRITE_PROTECT)

public val ERROR_DISK_FULL: UInt = cu(ERROR_DISK_FULL)

public val ERROR_HANDLE_DISK_FULL: UInt = cu(ERROR_HANDLE_DISK_FULL)

public val ERROR_SEEK_ON_DEVICE: UInt = cu(ERROR_SEEK_ON_DEVICE)

public val ERROR_DISK_QUOTA_EXCEEDED: UInt = cu(ERROR_DISK_QUOTA_EXCEEDED)

public val ERROR_FILE_TOO_LARGE: UInt = cu(ERROR_FILE_TOO_LARGE)

public val ERROR_BUSY: UInt = cu(ERROR_BUSY)

public val ERROR_POSSIBLE_DEADLOCK: UInt = cu(ERROR_POSSIBLE_DEADLOCK)

public val ERROR_NOT_SAME_DEVICE: UInt = cu(ERROR_NOT_SAME_DEVICE)

public val ERROR_TOO_MANY_LINKS: UInt = cu(ERROR_TOO_MANY_LINKS)

public val ERROR_TOO_MANY_OPEN_FILES: UInt = cu(ERROR_TOO_MANY_OPEN_FILES)

public val ERROR_FILENAME_EXCED_RANGE: UInt = cu(ERROR_FILENAME_EXCED_RANGE)

public val ERROR_CANT_RESOLVE_FILENAME: UInt = cu(ERROR_CANT_RESOLVE_FILENAME)

public val ERROR_IO_DEVICE: UInt = cu(ERROR_IO_DEVICE)

public val ERROR_NEGATIVE_SEEK: UInt = cu(ERROR_NEGATIVE_SEEK)

public val WAIT_TIMEOUT: UInt = cu(WAIT_TIMEOUT)

public val FRS_ERR_SYSVOL_POPULATE_TIMEOUT: UInt = cu(FRS_ERR_SYSVOL_POPULATE_TIMEOUT)

// Equivalent to the `NT_SUCCESS` C preprocessor macro.
// See: https://docs.microsoft.com/en-us/windows-hardware/drivers/kernel/using-ntstatus-values
public fun ntSuccess(status: Ntstatus): Boolean = (status >= 0).toUByte()

public fun Guid.Companion.fromU128(uuid: U128): Guid = Guid(data1 = (uuid shr 96).toUInt(), data2 = (uuid shr 80 and U128(0x0uL, 0xffffuL)).toUShort(), data3 = (uuid shr 64 and U128(0x0uL, 0xffffuL)).toUShort(), data4 = run { val rawBits = (uuid.toULong()); ubyteArrayOf((rawBits shr 56).toUByte(), (rawBits shr 48).toUByte(), (rawBits shr 40).toUByte(), (rawBits shr 32).toUByte(), (rawBits shr 24).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 8).toUByte(), rawBits.toUByte()) })

public fun ObjectAttributes.Companion.withLength(): ObjectAttributes = ObjectAttributes(length = sizeOf<ObjectAttributes>(typeLayoutT = UnresolvedTypeLayout<ObjectAttributes>()).toUInt(), rootDirectory = MutPtr(0L), objectName = MutPtr(0L), attributes = 0u, securityDescriptor = MutPtr(0L), securityQualityOfService = MutPtr(0L))

public val PENDING: IoStatusBlock = IoStatusBlock(anonymous = IoStatusBlock0(status = STATUS_PENDING), information = 0)

public fun IoStatusBlock.status(): Ntstatus {
    // SAFETY: If `self.Anonymous.Status` was set then this is obviously safe.
    // If `self.Anonymous.Pointer` was set then this is the equivalent to converting
    // the pointer to an integer, which is also safe.
    // Currently the only safe way to construct `IO_STATUS_BLOCK` outside of
    // this module is to call the `default` method, which sets the `Status`.
    return this.anonymous.status
}

/**
 * NB: Use carefully! In general using this as a reference is likely to get the
 * provenance wrong for the `rest` field!
 */
public open class ReparseDataBuffer(
    public var reparseTag: CUint,
    public var reparseDataLength: CUshort,
    public var reserved: CUshort,
    public var rest: Unit,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ReparseDataBuffer> =
            object : TypeLayout<ReparseDataBuffer> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): ReparseDataBuffer =
                    ReparseDataBuffer(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.UNIT).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: ReparseDataBuffer) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.reparseTag)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.reparseDataLength)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.reserved)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 8L, value.rest)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ReparseDataBuffer =
                    ReparseDataBuffer(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ReparseDataBuffer) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.reparseTag)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.reparseDataLength)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.reserved)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 8, value.rest)
                }
            }
    }
}

/**
 * NB: Use carefully! In general using this as a reference is likely to get the
 * provenance wrong for the `PathBuffer` field!
 */
public open class SymbolicLinkReparseBuffer(
    public var substituteNameOffset: CUshort,
    public var substituteNameLength: CUshort,
    public var printNameOffset: CUshort,
    public var printNameLength: CUshort,
    public var flags: CUlong,
    public var pathBuffer: Wchar,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SymbolicLinkReparseBuffer> =
            object : TypeLayout<SymbolicLinkReparseBuffer> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): SymbolicLinkReparseBuffer =
                    SymbolicLinkReparseBuffer(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SymbolicLinkReparseBuffer) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.substituteNameOffset)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.substituteNameLength)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.printNameOffset)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.printNameLength)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.flags)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.pathBuffer)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SymbolicLinkReparseBuffer =
                    SymbolicLinkReparseBuffer(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SymbolicLinkReparseBuffer) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.substituteNameOffset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.substituteNameLength)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.printNameOffset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.printNameLength)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.flags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.pathBuffer)
                }
            }
    }
}

public open class MountPointReparseBuffer(
    public var substituteNameOffset: CUshort,
    public var substituteNameLength: CUshort,
    public var printNameOffset: CUshort,
    public var printNameLength: CUshort,
    public var pathBuffer: Wchar,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<MountPointReparseBuffer> =
            object : TypeLayout<MountPointReparseBuffer> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 10L
                override fun readNative(addr: Long): MountPointReparseBuffer =
                    MountPointReparseBuffer(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: MountPointReparseBuffer) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.substituteNameOffset)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.substituteNameLength)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.printNameOffset)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.printNameLength)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.pathBuffer)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MountPointReparseBuffer =
                    MountPointReparseBuffer(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MountPointReparseBuffer) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.substituteNameOffset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.substituteNameLength)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.printNameOffset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.printNameLength)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.pathBuffer)
                }
            }
    }
}

public val INIT_ONCE_STATIC_INIT: InitOnce = InitOnce(ptr = MutPtr(0L))

// Use raw-dylib to import ProcessPrng as we can't rely on there being an import library.
public expect fun processPrng(pbdata: MutPtr, cbdata: ULong): Bool

public expect fun ntCreateNamedPipeFile(filehandle: MutPtr, desiredaccess: UInt, objectattributes: ConstPtr, iostatusblock: MutPtr, shareaccess: UInt, createdisposition: UInt, createoptions: UInt, namedpipetype: UInt, readmode: UInt, completionmode: UInt, maximuminstances: UInt, inboundquota: UInt, outboundquota: UInt, defaulttimeout: ConstPtr): Ntstatus

// Functions that aren't available on every version of Windows that we support,
// but we still use them and just provide some form of a fallback implementation.
// Functions in DLLs that we want to lazily load
public var KERNEL32: CStr = "kernel32"

public expect fun atexit(cb: AtexitCbCallback): CInt

public var SHELL32: CStr = "shell32"

public var OLE32: CStr = "ole32"
