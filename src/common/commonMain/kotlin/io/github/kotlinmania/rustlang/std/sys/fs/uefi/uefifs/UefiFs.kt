// port-lint: source sys/fs/uefi.rs
package io.github.kotlinmania.rustlang.std.sys.fs.uefi.uefifs

import io.github.kotlinmania.refi.efi.Status
import io.github.kotlinmania.refi.efi.Time
import io.github.kotlinmania.refi.efi.UNSPECIFIED_TIMEZONE
import io.github.kotlinmania.refi.protocols.devicePath
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.path.absolute
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.BorrowedDevicePath
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.OwnedDevicePath
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.UefiBox
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.locateHandles
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.openProtocol
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.osStringToRaw
import io.github.kotlinmania.rustlang.std.sys.time.uefi.SystemTime

internal open class File(
    public var protocol: NonNull,
    public var path: PathBuf,
) {
    // SAFETY: UEFI has no regular threads, and as per <https://github.com/rust-lang/rust/issues/133604>
    // std does not support being invoked from "irregular threads" such as interrupt handlers or other
    // CPU cores that run outside the scope of UEFI.

    internal open fun read(buf: MutableList<UByte>): Result<Int> {
        val filePtr = this.protocol.asPtr()
        val bufSize = buf.size
        val r = (filePtr.read)(filePtr, bufSize, buf.asMutPtr())
        return if (bufSize == 0 && r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(bufSize)
        }
    }

    internal open fun readDirEntry(): Result<UefiBox<Info>?> {
        val filePtr = this.protocol.asPtr()
        val localStorageL715C12 = AddressableLocal(0, PrimitiveLayouts.I32)
        val bufSize by localStorageL715C12
        val r = (filePtr.read)(filePtr, RustMutableReference(localStorageL715C12.asMutPtr(), localStorageL715C12.layout), MutPtr(0L))
        if (bufSize == 0) {
            return Result.Ok(null)
        }
        check(r.isError())
        if (r != Status.BUFFER_TOO_SMALL) {
            return Result.Err(io.Error.fromRawOsError(r.asUsize()))
        }
        val info: UefiBox<Info>  = when (val tryResult = UefiBox.new(bufSize)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val r1 = (filePtr.read)(filePtr, RustMutableReference(localStorageL715C12.asMutPtr(), localStorageL715C12.layout), info.asMutPtr().cast())
        return if (r1.isError()) {
            Result.Err(io.Error.fromRawOsError(r1.asUsize()))
        } else {
            Result.Ok(info)
        }
    }

    internal open fun write(buf: List<UByte>): Result<Int> {
        val filePtr = this.protocol.asPtr()
        val bufSize = buf.size
        val r = (filePtr.write)(filePtr, bufSize, MutPtr((ConstPtr((buf.asPtr()).rawAddress)).rawAddress))
        return if (bufSize == 0 && r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(bufSize)
        }
    }

    internal open fun fileInfo(): Result<UefiBox<Info>> {
        val filePtr = this.protocol.asPtr()
        val infoId = File.INFO_ID
        val localStorageL761C12 = AddressableLocal(0, PrimitiveLayouts.I32)
        val bufSize by localStorageL761C12
        val r = (filePtr.getInfo)(filePtr, infoId, RustMutableReference(localStorageL761C12.asMutPtr(), localStorageL761C12.layout), MutPtr(0L))
        check(r.isError())
        if (r != Status.BUFFER_TOO_SMALL) {
            return Result.Err(io.Error.fromRawOsError(r.asUsize()))
        }
        val info: UefiBox<Info>  = when (val tryResult = UefiBox.new(bufSize)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val r1 = (filePtr.getInfo)(filePtr, infoId, RustMutableReference(localStorageL761C12.asMutPtr(), localStorageL761C12.layout), info.asMutPtr().cast())
        return if (r1.isError()) {
            Result.Err(io.Error.fromRawOsError(r1.asUsize()))
        } else {
            Result.Ok(info)
        }
    }

    internal open fun setFileInfo(info: UefiBox<Info>): Result<Unit> {
        val filePtr = this.protocol.asPtr()
        val infoId = File.INFO_ID
        val r = (filePtr.setInfo)(filePtr, infoId, info.len(), info.asMutPtr().cast())
        return if (r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    internal open fun position(): Result<ULong> {
        val filePtr = this.protocol.asPtr()
        val localStorageL802C12 = AddressableLocal(0, PrimitiveLayouts.I32)
        val pos by localStorageL802C12
        val r = (filePtr.getPosition)(filePtr, RustMutableReference(localStorageL802C12.asMutPtr(), localStorageL802C12.layout))
        return if (r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(pos)
        }
    }

    internal open fun setPosition(pos: ULong): Result<Unit> {
        val filePtr = this.protocol.asPtr()
        val r = (filePtr.setPosition)(filePtr, pos)
        return if (r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    internal open fun delete(): Result<Unit> {
        val filePtr = this.protocol.asPtr()
        val r = (filePtr.delete)(filePtr)
        // Spec states that even in case of failure, the file handle will be closed.
        forget(this)
        return if (r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    internal open fun flush(): Result<Unit> {
        val filePtr = this.protocol.asPtr()
        val r = (filePtr.flush)(filePtr)
        return if (r.isError()) {
            Result.Err(io.Error.fromRawOsError(r.asUsize()))
        } else {
            Result.Ok(Unit)
        }
    }

    internal open fun path(): Path = this.path

    public open fun drop() {
        val filePtr = this.protocol.asPtr()
        (filePtr.close)(filePtr)
    }

    public companion object {
        internal fun fromPath(path: Path, openMode: ULong, attr: ULong): Result<File> {
            val absolute = when (val tryResult = absolute(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val p = when (val tryResult = OwnedDevicePath.fromText(absolute.asOsStr())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            var (vol, pathRemaining) = when (val tryResult = File.openVolumeFromDevicePath(p.borrow())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val protocol = when (val tryResult = File.open(vol, pathRemaining, openMode, attr)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(File(protocol = protocol, path = absolute))
        }

        /**
         * Open Filesystem volume given a devicepath to the volume, or a file/directory in the
         * volume. The path provided should be absolute UEFI device path, without any UEFI shell
         * mappings.
         *
         * Returns
         * 1. The volume as a UEFI File
         * 2. Path relative to the volume.
         *
         * For example, given "PciRoot(0x0)/Pci(0x1,0x1)/Ata(Secondary,Slave,0x0)/\abc\run.efi",
         * this will open the volume "PciRoot(0x0)/Pci(0x1,0x1)/Ata(Secondary,Slave,0x0)"
         * and return the remaining file path "\abc\run.efi".
         */
        public fun openVolumeFromDevicePath(path: BorrowedDevicePath): Result<Pair<NonNull, List<UShort>>> {
            val handles =
                run {
                    val matchVal = locateHandles(simplefilesystem.PROTOCOL_GUID)
                    when (matchVal) { is Result.Ok<*> -> {
                        val x = matchVal.value
                        x
                    }; is Result.Err<*> -> {
                        val e = matchVal.error
                        return Result.Err(e)
                    } }
                }
            for (handle in handles) {
                val volumeDevicePath: NonNull =
                    run {
                        val matchVal1 = openProtocol(handle, devicePath.PROTOCOL_GUID)
                        when (matchVal1) { is Result.Ok<*> -> {
                            val x1 = matchVal1.value
                            x1
                        }; is Result.Err<*> -> {
                            continue
                        } }
                    }
                val volumeDevicePath1 = BorrowedDevicePath.new(volumeDevicePath)
                when (val matchVal2 = pathBestMatch(volumeDevicePath1, path)) {
                    null -> {}
                    else -> {
                        val leftPath = matchVal2
                        return Result.Ok(Pair(when (val tryResult = File.openVolume(handle)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, leftPath))
                    }
                }
            }
            return Result.Err(io.constError(io.ErrorKind.NotFound, "Volume Not Found"))
        }

        // Open volume on deviceHandle using SIMPLE_FILE_SYSTEM_PROTOCOL
        public fun openVolume(deviceHandle: NonNull): Result<NonNull> {
            val simpleFileSystemProtocol = when (val tryResult = openProtocol<simplefilesystem.Protocol>(deviceHandle, simplefilesystem.PROTOCOL_GUID)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val fileProtocol = MutPtr(0L)
            val r = (simpleFileSystemProtocol.asPtr().openVolume)(simpleFileSystemProtocol.asPtr(), fileProtocol)
            if (r.isError()) {
                return Result.Err(io.Error.fromRawOsError(r.asUsize()))
            }
            // Since no error was returned, file protocol should be non-NULL.
            val p = NonNull.new(fileProtocol).unwrap()
            return Result.Ok(p)
        }

        public fun open(protocol: NonNull, path: MutableList<UShort>, openMode: ULong, attr: ULong): Result<NonNull> {
            val filePtr = protocol.asPtr()
            val fileOpened = MutPtr(0L)
            val r = (filePtr.open)(filePtr, fileOpened, path.asMutPtr(), openMode, attr)
            if (r.isError()) {
                return Result.Err(io.Error.fromRawOsError(r.asUsize()))
            }
            // Since no error was returned, file protocol should be non-NULL.
            val p = NonNull.new(fileOpened).unwrap()
            return Result.Ok(p)
        }
    }
}

/**
 * A helper to check that target path is a descendent of source. It is expected to be used with
 * absolute UEFI device paths without any UEFI shell mappings.
 *
 * Returns the path relative to source
 *
 * For example, given "PciRoot(0x0)/Pci(0x1,0x1)/Ata(Secondary,Slave,0x0)/" and
 * "PciRoot(0x0)/Pci(0x1,0x1)/Ata(Secondary,Slave,0x0)/\abc\run.efi", this will return
 * "\abc\run.efi"
 */
public fun pathBestMatch(source: BorrowedDevicePath, target: BorrowedDevicePath): List<UShort>? {
    val sourceIter = source.iter().takeWhile({ x -> !x.isEndInstance() })
    val targetIter = target.iter().takeWhile({ x -> !x.isEndInstance() })
    while (true) {
        run {
            val matchVal = Pair(sourceIter.next(), targetIter.next())
            when {
                matchVal.first != null && matchVal.second != null && matchVal.first == matchVal.second -> {
                    val x = matchVal.first
                    val y = matchVal.second
                    continue
                }
                matchVal.first == null && matchVal.second != null -> {
                    val y1 = matchVal.second
                    val p = (y1.toPath().toText().ok() ?: return null)
                    return osStringToRaw(p)
                }
                else -> { return null }
            }
        }
    }
}

/**
 * An implementation of mkdir to allow creating new directory without having to open the
 * volume twice (once for checking and once for creating)
 */
internal fun mkdir(path: Path): Result<Unit> {
    val absolute = when (val tryResult = absolute(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val p = when (val tryResult = OwnedDevicePath.fromText(absolute.asOsStr())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    var (vol, pathRemaining) = when (val tryResult = File.openVolumeFromDevicePath(p.borrow())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    // Check if file exists
    run {
        val matchVal = File.open(vol, pathRemaining, File.MODE_READ, 0uL)
        when {
            matchVal is Result.Ok<*> -> {
                return Result.Err(io.Error.new(io.ErrorKind.AlreadyExists, "Path already exists"))
            }
            matchVal is Result.Err<*> && (matchVal as Result.Err<*>).error.kind() == io.ErrorKind.NotFound -> {
                val e = (matchVal as Result.Err<*>).error
            }
            else -> {
                val e1 = (matchVal as Result.Err<*>).error
                return Result.Err(e1)
            }
        }
    }
    when (val tryResult = File.open(vol, pathRemaining, File.MODE_READ or File.MODE_WRITE or File.MODE_CREATE, File.DIRECTORY)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(Unit)
}

/**
 * EDK2 FAT driver uses EFI_UNSPECIFIED_TIMEZONE to represent localtime. So for proper
 * conversion to SystemTime, we use the current time to get the timezone in such cases.
 */
internal fun uefiToSystemtime(time: Time): SystemTime? {
    time.timezone = if (time.timezone == UNSPECIFIED_TIMEZONE) {
        SystemTime.now().timezone
    } else {
        time.timezone
    }
    return SystemTime.fromUefi(time)
}

/**
 * Convert to UEFI Time with the current timezone.
 */
internal fun systemtimeToUefi(time: SystemTime): Time {
    val now = SystemTime.now()
    return time.toUefiLoose(now.timezone, now.daylight)
}

internal fun fileNameFromUefi(info: UefiBox<Info>): OsString {
    val fname = info.fileName()
    return OsString.fromWide(fname[0 until fname.len() - 1])
}
