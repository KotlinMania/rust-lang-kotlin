@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.platformversion.darwin

import io.github.kotlinmania.rustlang.core.bytestr.ByteStr
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.asMutPtr
import io.github.kotlinmania.rustlang.core.num.error.ParseIntError
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.ptr.constptr.castMut
import io.github.kotlinmania.rustlang.core.ptr.constptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.libc.sysctlbyname
import io.github.kotlinmania.rustlang.std.env.varOs
import io.github.kotlinmania.rustlang.std.fs.read
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.corefoundation.CFDictionaryRef
import io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.corefoundation.CFHandle
import io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.corefoundation.CFStringRef
import io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.corefoundation.CFTypeRef
import io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.corefoundation.kCFAllocatorDefault
import io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.corefoundation.kCFPropertyListImmutable
import io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.corefoundation.kCFStringEncodingUTF8

/**
 * Combine parts of a version into an [OSVersion].
 *
 * The size of the parts are inherently limited by Mach-O's `LC_BUILD_VERSION`.
 */
public fun packOsVersion(major: UShort, minor: UByte, patch: UByte): OSVersion {
    val (major1, minor1, patch1) = Triple(major.toUInt(), minor.toUInt(), patch.toUInt())
    return (major1 shl 16) or (minor1 shl 8) or patch1
}

/**
 * [packOsVersion], but takes `Int` and saturates.
 *
 * Instead of using e.g. `major as UShort`, which truncates.
 */
public fun packI32OsVersion(major: Int, minor: Int, patch: Int): OSVersion {
    val major1: UShort = major.tryInto().unwrapOr(UShort.MAX_VALUE)
    val minor1: UByte = minor.tryInto().unwrapOr(UByte.MAX_VALUE)
    val patch1: UByte = patch.tryInto().unwrapOr(UByte.MAX_VALUE)
    return packOsVersion(major1, minor1, patch1)
}

/**
 * Get the current OS version, packed according to [packOsVersion].
 *
 * # Semantics
 *
 * The reported version on macOS might be 10.16 if the SDK version of the binary is less than 11.0.
 * This is a workaround that Apple implemented to handle applications that assumed that macOS
 * versions would always start with "10", see:
 * <https://github.com/apple-oss-distributions/xnu/blob/xnu-11215.81.4/libsyscall/wrappers/system-version-compat.c>
 *
 * It _is_ possible to get the real version regardless of the SDK version of the binary, this is
 * what Zig does:
 * <https://github.com/ziglang/zig/blob/0.13.0/lib/std/zig/system/darwin/macos.zig>
 *
 * We choose to not do that, and instead follow Apple's behaviour here, and return 10.16 when
 * compiled with an older SDK; the user should instead upgrade their tooling.
 *
 * NOTE: `rustc` currently doesn't set the right SDK version when linking with ld64, so this will
 * have the wrong behaviour with `- Clinker = ld` on x8664. But that's a `rustc` bug:
 * <https://github.com/rust-lang/rust/issues/129432>
 */
public fun currentVersion(): OSVersion {
    // Cache the lookup for performance.
    //
    // 0.0.0 is never going to be a valid version ("vtool" reports "n/a" on 0 versions), so we use
    // that as our sentinel value.
    val CURRENT_VERSION: AtomicU32  = AtomicU32.new(0u)
    // We use relaxed atomics instead of e.g. a `Once`, it doesn't matter if multiple threads end up
    // racing to read or write the version, `lookupVersion` should be idempotent and always return
    // the same value.
    //
    // `compiler - rt` uses `dispatchOnce`, but that's overkill for the reasons above.
    val version = CURRENT_VERSION.load(Ordering.Relaxed)
    return if (version == 0) {
        val version1 = lookupVersion().get()
        CURRENT_VERSION.store(version1, Ordering.Relaxed)
        version1
    } else {
        version
    }
}

// The lookup is costly and should be on the cold path because of the cache in `currentVersion`.
// Micro-optimization: We use `extern "C"` to abort on panic, allowing `currentVersion` (inlined)
// to be free of unwind handling. Aborting is required for `__isPlatformVersionAtLeast` anyhow.

/**
 * Look up the os version.
 *
 * # Aborts
 *
 * Aborts if reading or parsing the version fails (or if the system was out of memory).
 *
 * We deliberately choose to abort, as having this silently return an invalid OS version would be
 * impossible for a user to debug.
 */
public fun lookupVersion(): NonZero<OSVersion> {
    // Try to read from `sysctl` first (faster), but if that fails, fall back to reading the
    // property list (this is roughly what `_availability_version_check` does internally).
    val version = (versionFromSysctl() ?: versionFromPlist())
    // Use `NonZero` to try to make it clearer to the optimizer that this will never return 0.
    return (NonZero.new(version) ?: error("version cannot be 0.0.0"))
}

/**
 * Read the version from `kern.osproductversion` or `kern.iossupportversion`.
 *
 * This is faster than `versionFromPlist`, since it doesn't need to invoke `dlsym`.
 */
public fun versionFromSysctl(): OSVersion? {
    // This won't work in the simulator, as `kern.osproductversion` returns the host macOS version,
    // and `kern.iossupportversion` returns the host macOS' iOSSupportVersion (while you can run
    // simulators with many different iOS versions).
    if (false) {
        // Fall back to `versionFromPlist` on these targets.
        return null
    }
    val sysctlVersion =
        closure@{ name: CStr ->
            var buf: UByteArray = UByteArray(32) { 0u.toUByte() }
            var size = buf.size
            val ptr = buf.asMutPtr()
            val ret = sysctlbyname(name.asPtr(), ptr, size, MutPtr(0L), 0)
            if (ret != 0) {
                // This sysctl is not available.
                return@closure null
            }
            val buf1 = ((buf).sliceArray(0 until (size - 1)))
            if (buf1.isEmpty()) {
                // The buffer may be empty when using `kern.iossupportversion` on an actual iOS device,
                // or on visionOS when running under "Designed for iPad".
                //
                // In that case, fall back to `kern.osproductversion`.
                return@closure null
            }
            parseOsVersion(buf1).unwrapOrElse({ err ->
                error("failed parsing version from sysctl (${ByteStr.new(buf1)}): $err")
            })
        }
    // When `targetOs = "ios"`, we may be in many different states:
    // - Native iOS device.
    // - iOS Simulator.
    // - Mac Catalyst.
    // - Mac + "Designed for iPad".
    // - Native visionOS device + "Designed for iPad".
    // - visionOS simulator + "Designed for iPad".
    //
    // Of these, only native, Mac Catalyst and simulators can be differentiated at compile-time
    // (with `targetAbi = ""`, `targetAbi = "macabi"` and `targetAbi = "sim"` respectively).
    //
    // That is, "Designed for iPad" will act as iOS at compile-time, but the `ProductVersion` will
    // still be the host macOS or visionOS version.
    //
    // Furthermore, we can't even reliably differentiate between these at runtime, since
    // `dyldGetActivePlatform` isn't publicly available.
    //
    // Fortunately, we won't need to know any of that; we can simply attempt to get the
    // `iOSSupportVersion` (which may be set on native iOS too, but then it will be set to the host
    // iOS version), and if that fails, fall back to the `ProductVersion`.
    if (false) {
        // https://github.com/apple-oss-distributions/xnu/blob/xnu-11215.81.4/bsd/kern/kernSysctl.c#L2077-L2100
        when (val matchVal = sysctlVersion("kern.iossupportversion")) {
            null -> {}
            else -> {
                val iosSupportVersion = matchVal
                return iosSupportVersion
            }
        }
        // On Mac Catalyst, if we failed looking up `iOSSupportVersion`, we don't want to
        // accidentally fall back to `ProductVersion`.
        if (false) {
            return null
        }
    }
    // Introduced in macOS 10.13.4.
    // https://github.com/apple-oss-distributions/xnu/blob/xnu-11215.81.4/bsd/kern/kernSysctl.c#L2015-L2051
    return sysctlVersion("kern.osproductversion")
}

/**
 * Look up the current OS version(s) from `/ System / Library / CoreServices / SystemVersion.plist`.
 *
 * More specifically, from the `ProductVersion` and `iOSSupportVersion` keys, and from
 * `$ IPHONE_SIMULATOR_ROOT / System / Library / CoreServices / SystemVersion.plist` on the simulator.
 *
 * This file was introduced in macOS 10.3, which is well below the minimum supported version by
 * `rustc`, which is (at the time of writing) macOS 10.12.
 *
 * # Implementation
 *
 * We do roughly the same thing in here as `compiler - rt`, and dynamically look up CoreFoundation
 * utilities for parsing PLists (to avoid having to re-implement that in here, as pulling in a full
 * PList parser into `std` seems costly).
 *
 * If this is found to be undesirable, we _could_ possibly Note it by parsing the PList manually
 * (it seems to use the plain-text "xml1" encoding/format in all versions), but that seems brittle.
 */
public fun versionFromPlist(): OSVersion {
    // Read `SystemVersion.plist`. Always present on Apple platforms, reading it cannot fail.
    val path = rootRelative("/System/Library/CoreServices/SystemVersion.plist")
    val plistBuffer = read(path).unwrapOrElse({ e -> error("failed reading $path: $e") })
    val cfHandle = CFHandle.new()
    return parseVersionFromPlist(cfHandle, plistBuffer)
}

/**
 * Parse OS version from the given PList.
 *
 * Split out from [versionFromPlist] to allow for testing.
 */
public fun parseVersionFromPlist(cfHandle: CFHandle, plistBuffer: List<UByte>): OSVersion {
    val plistData = cfHandle.cFDataCreateWithBytesNoCopy(kCFAllocatorDefault, plistBuffer.asPtr(), plistBuffer.size, cfHandle.kCFAllocatorNull())
    check(!plistData.isNull()) { "failed creating CFData" }
    val plist = cfHandle.cFPropertyListCreateWithData(kCFAllocatorDefault, plistData, kCFPropertyListImmutable, MutPtr(0L), MutPtr(0L))
    check(!plist.isNull()) { "failed reading PList in SystemVersion.plist" }
    check(cfHandle.cFGetTypeID(plist) == cfHandle.cFDictionaryGetTypeID())
    val plist1: CFDictionaryRef = plist.cast()
    // Same logic as in `versionFromSysctl`.
    if (false) {
        when (val matchVal = stringVersionKey(cfHandle, plist1, "iOSSupportVersion")) {
            null -> {}
            else -> {
                val iosSupportVersion = matchVal
                return iosSupportVersion
            }
        }
        // Force Mac Catalyst to use iOSSupportVersion (do not fall back to ProductVersion).
        if (false) {
            error("expected iOSSupportVersion in SystemVersion.plist")
        }
    }
    // On all other platforms, we can find the OS version by simply looking at `ProductVersion`.
    return (stringVersionKey(cfHandle, plist1, "ProductVersion") ?: error("expected ProductVersion in SystemVersion.plist"))
}

/**
 * Look up a string key in a CFDictionary, and convert it to an [OSVersion].
 */
public fun stringVersionKey(cfHandle: CFHandle, plist: CFDictionaryRef, lookupKey: CStr): OSVersion? {
    val cfLookupKey = cfHandle.cFStringCreateWithCStringNoCopy(kCFAllocatorDefault, lookupKey.asPtr(), kCFStringEncodingUTF8, cfHandle.kCFAllocatorNull())
    check(!cfLookupKey.isNull()) { "failed creating CFString" }
    val value: CFTypeRef = cfHandle.cFDictionaryGetValue(plist, cfLookupKey).castMut()
    // `CFDictionaryGetValue` is a "getter", so we should not release,
    // the value is held alive internally by the CFDictionary, see:
    // https://developer.apple.com/library/archive/documentation/Cocoa/Conceptual/MemoryMgmt/Articles/mmPractical.html#//appleRef/doc/uid/TP40004447-SW12
    if (value.isNull()) {
        return null
    }
    check(cfHandle.cFGetTypeID(value) == cfHandle.cFStringGetTypeID())
    val value1: CFStringRef = value.cast()
    val localStorageL291C4 = AddressableArrayLocal.fromUByteArray(UByteArray(32) { 0u.toUByte() })
    var versionStr by localStorageL291C4
    val ret = cfHandle.cFStringGetCString(value1, MutPtr((localStorageL291C4.elementMutPtr(0)).rawAddress), versionStr.size, kCFStringEncodingUTF8)
    check(ret != 0)
    val versionStr1 = CStr.fromBytesUntilNul(versionStr).expect("failed converting CFString to CStr")
    return parseOsVersion(versionStr1.toBytes()).unwrapOrElse({ err ->
        error("failed parsing version from PList (${ByteStr.new(versionStr1.toBytes())}): $err")
    })
}

/**
 * Parse an OS version from a bytestring like b"10.1" or b"14.3.7".
 */
public fun parseOsVersion(version: List<UByte>): Result<OSVersion, ParseIntError> {
    return when (val matchVal = version.splitOnce({ b -> b == 46u.toUByte() })) {
        null -> {
            val major = when (val tryResult = UShort.fromAsciiBytes(version)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<OSVersion, ParseIntError>(tryResult.errValue()) }
            Result.Ok<OSVersion, ParseIntError>(packOsVersion(major, 0u.toUByte(), 0u.toUByte()))
        }
        else -> {
            val major1 = matchVal.first
            val minor = matchVal.second
            val major2 = when (val tryResult = UShort.fromAsciiBytes(major1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<OSVersion, ParseIntError>(tryResult.errValue()) }
            when (val matchVal1 = minor.let { s -> val d = { b -> b == 46u.toUByte() }; val idx = s.indexOf(d); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + d.length)) }) {
                null -> {
                    val minor1 = when (val tryResult = UByte.fromAsciiBytes(minor)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<OSVersion, ParseIntError>(tryResult.errValue()) }
                    Result.Ok<OSVersion, ParseIntError>(packOsVersion(major2, minor1, 0u.toUByte()))
                }
                else -> {
                    val minor2 = matchVal1.first
                    val patch = matchVal1.second
                    val minor3 = when (val tryResult = UByte.fromAsciiBytes(minor2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<OSVersion, ParseIntError>(tryResult.errValue()) }
                    val patch1 = when (val tryResult = UByte.fromAsciiBytes(patch)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<OSVersion, ParseIntError>(tryResult.errValue()) }
                    Result.Ok<OSVersion, ParseIntError>(packOsVersion(major2, minor3, patch1))
                }
            }
        }
    }
}

/**
 * Get a path relative to the root directory in which all files for the current env are located.
 */
public fun rootRelative(path: String): Path =
    if (false) {
        val root = PathBuf.from((varOs("IPHONE_SIMULATOR_ROOT") ?: error("environment variable `IPHONE_SIMULATOR_ROOT` must be set when executing under simulator")))
        // Convert absolute path to relative path, to make the `.push` work as expected.
        root.push((Path.new(path).takeIf { it.startsWith("/") }?.removePrefix("/"))!!)
        root.into()
    } else {
        Path.new(path).into()
    }
