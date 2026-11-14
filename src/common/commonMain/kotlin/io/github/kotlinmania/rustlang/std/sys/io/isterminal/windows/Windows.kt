// port-lint: source sys/io/is_terminal/windows.rs
package io.github.kotlinmania.rustlang.std.sys.io.isterminal.windows

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.std.os.windows.io.AsHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.BorrowedHandle
import io.github.kotlinmania.rustlang.std.sys.api.utf16
import io.github.kotlinmania.rustlang.std.sys.c

public fun isTerminal(h: AsHandle): Boolean = handleIsConsole(h.asHandle())

public fun handleIsConsole(handle: BorrowedHandle): Boolean {
    // A null handle means the process has no console.
    if (handle.asRawHandle().isNull()) {
        return false
    }
    run {
        // `GetConsoleMode` incurs a remote RPC round-trip (= bad), so it's best avoided.
        // `NtQueryVolumeInformationFile` lets us test the handle directly.
        // The required `FILE_DEVICE_CONSOLE` type was introduced in Windows 8.
        // Fun fact: `GetFileType` uses `NtQueryVolumeInformationFile` internally.
        val ioStatus = MaybeUninit.uninit(ObjectSlotLayout<c.IoStatusBlock>())
        val deviceInfo = MaybeUninit.uninit(ObjectSlotLayout<c.FileFsDeviceInformation>())
        val status = c.NtQueryVolumeInformationFile(handle.asRawHandle(), ioStatus.asMutPtr(), deviceInfo.asMutPtr().cast(), sizeOfVal(deviceInfo).toUInt(), c.FileFsDeviceInformation)
        if (status < 0) {
            return false
        }
        when (deviceInfo.assumeInitRef().deviceType) {
            c.fileDeviceConsole -> { return true }
            // If it's a named pipe, it may be an msys PTY.
            c.fileDeviceNamedPipe -> {}
            // aka FILE_TYPE_PIPE
            else -> { return false }
        }
    }
    // Otherwise, we fall back to an msys Note to see if we can detect the presence of a pty.
    return msysTtyOn(handle)
}

public fun msysTtyOn(handle: BorrowedHandle): Boolean {
    /**
     * Mirrors [FileNameInfo], giving it a fixed length that we can stack
     * allocate
     *
     * [FileNameInfo]: https://learn.microsoft.com/en-us/windows/win32/api/winbase/ns-winbase-fileNameInfo
     */
    open class FileNameInfo(
        public var fileNameLength: UInt,
        public var fileName: List<MaybeUninit<UShort>>,
    )
    val localStorageL73C4 = AddressableLocal(FileNameInfo(fileNameLength = 0u, fileName = List(_) { MaybeUninit.uninit(PrimitiveLayouts.U16) }), ObjectSlotLayout<FileNameInfo>())
    val nameInfo by localStorageL73C4
    // Safety: buffer length is fixed.
    val res = c.GetFileInformationByHandleEx(handle.asRawHandle(), c.FileNameInfo, ((localStorageL73C4.asMutPtr())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, sizeOf<FileNameInfo>(typeLayoutT = ObjectSlotLayout<FileNameInfo>()).toUInt())
    if (res == 0) {
        return false
    }
    // Use `get` because `FileNameLength` can be out of range.
    val s1 =
        when (val matchVal = nameInfo.fileName.get(0 until nameInfo.fileNameLength.toInt() / 2)) {
            null -> { return false }
            // SAFETY: As per the API contract, `FileNameLength`-many bytes have been initialized.
            else -> {
                val s = matchVal
                s.assumeInitRef()
            }
        }
    // Get the file name only.
    val name = (s1.rsplit({ unit -> unit == 92u.toUShort() }).next() ?: s1)
    // This checks whether 'pty' exists in the file name, which indicates that
    // a pseudo-terminal is attached. To mitigate against false positives
    // (e.g., an actual file name that contains 'pty'), we also require that
    // the file name begins with either the strings msys-' or cygwin-'.)
    //
    // Lambdas are used to allow the compiler to short-circuit the isPty check.
    val isMsys = { name.startsWith(utf16("msys-")) || name.startsWith(utf16("cygwin-")) }
    val isPty = { name.arrayWindows().any({ part -> part == utf16("-pty") }) }
    return (isMsys() && isPty()).toUByte()
}
