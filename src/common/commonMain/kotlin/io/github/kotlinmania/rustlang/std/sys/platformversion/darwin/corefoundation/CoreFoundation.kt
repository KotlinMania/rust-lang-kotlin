// port-lint: source sys/platform_version/darwin/core_foundation.rs
package io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.corefoundation

import io.github.kotlinmania.libc.fuchsia.RTLD_LAZY
import io.github.kotlinmania.libc.fuchsia.RTLD_LOCAL
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.libc.dlclose
import io.github.kotlinmania.rustlang.libc.dlerror
import io.github.kotlinmania.rustlang.libc.dlopen
import io.github.kotlinmania.rustlang.libc.dlsym
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runPathWithCstr
import io.github.kotlinmania.rustlang.std.sys.platformversion.darwin.rootRelative

// Minimal utilities for interfacing with a dynamically loaded CoreFoundation.

public fun interface FnCallback {
    public operator fun invoke(arg0: CFTypeRef)
}

public fun interface FnCallback1 {
    public operator fun invoke(arg0: CFTypeRef): CFTypeID
}

public fun interface FnCallback2 {
    public operator fun invoke(arg0: CFAllocatorRef, arg1: ConstPtr, arg2: CFIndex, arg3: CFAllocatorRef): CFDataRef
}

public fun interface FnCallback3 {
    public operator fun invoke(arg0: CFAllocatorRef, arg1: CFDataRef, arg2: CFOptionFlags, arg3: MutPtr, arg4: MutPtr): CFPropertyListRef
}

public fun interface FnCallback4 {
    public operator fun invoke(): CFTypeID
}

public fun interface FnCallback5 {
    public operator fun invoke(arg0: CFAllocatorRef, arg1: ConstPtr, arg2: CFStringEncoding, arg3: CFAllocatorRef): CFStringRef
}

public fun interface FnCallback6 {
    public operator fun invoke(arg0: CFStringRef, arg1: MutPtr, arg2: CFIndex, arg3: CFStringEncoding): Boolean
}

public fun interface FnCallback7 {
    public operator fun invoke(arg0: CFDictionaryRef, arg1: ConstPtr): ConstPtr
}

// MacTypes.h
public typealias Boolean = UByte

// CoreFoundation/CFBase.h
public typealias CFTypeID = ULong

public typealias CFOptionFlags = ULong

public typealias CFIndex = Long

public typealias CFTypeRef = MutPtr

public typealias CFAllocatorRef = CFTypeRef

// CoreFoundation/CFPropertyList.h
public val K_CF_PROPERTY_LIST_IMMUTABLE: CFOptionFlags = 0uL

// CoreFoundation/CFError.h
public typealias CFErrorRef = CFTypeRef

// CoreFoundation/CFData.h
public typealias CFDataRef = CFTypeRef

public val K_CF_STRING_ENCODING_UTF8: CFStringEncoding = 0x08000100u

public typealias CFPropertyListFormat = CFIndex

public typealias CFPropertyListRef = CFTypeRef

// CoreFoundation/CFString.h
public typealias CFStringRef = CFTypeRef

public typealias CFStringEncoding = UInt

public val K_CF_ALLOCATOR_DEFAULT: CFAllocatorRef = MutPtr(0L)

// CoreFoundation/CFDictionary.h
public typealias CFDictionaryRef = CFTypeRef

/**
 * An open handle to the dynamically loaded CoreFoundation framework.
 *
 * This is `dlopen`ed, and later `dlclose`d. This is done to try to avoid
 * "leaking" the CoreFoundation symbols to the rest of the user's binary if
 * they decided to not link CoreFoundation themselves.
 *
 * It is also faster to look up symbols directly via this handle than with
 * `RTLD_DEFAULT`.
 */
public open class CFHandle internal constructor(
    valueCtorArg: MutPtr,
) {
    public var value: MutPtr = valueCtorArg
        internal set

    public var field0: MutPtr
        get() = value
        set(v) {
            value = v
        }

    public open fun kCFAllocatorNull(): CFAllocatorRef {
        // Available: in all CF versions.
        val staticPtr = dlsym(this.value, "kCFAllocatorNull".asPtr())
        if (staticPtr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find kCFAllocatorNull: $err")
        }
        return staticPtr.cast()
    }

    public open fun cFRelease(cf: CFTypeRef) {
        val ptr = dlsym(this.value, ("CFRelease\u0000").encodeToByteArray().map { it.toUByte() }.asPtr())
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFRelease").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        fnptr(cf)
    }

    public open fun cFGetTypeID(cf: CFTypeRef): CFTypeID {
        val ptr = dlsym(this.value, ("CFGetTypeID\u0000").encodeToByteArray().map { it.toUByte() }.asPtr())
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFGetTypeID").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        return fnptr(cf)
    }

    public open fun cFDataCreateWithBytesNoCopy(allocator: CFAllocatorRef, bytes: ConstPtr, length: CFIndex, bytesDeallocator: CFAllocatorRef): CFDataRef {
        val ptr = dlsym(this.value, ("CFDataCreateWithBytesNoCopy\u0000").encodeToByteArray().map { it.toUByte() }.asPtr())
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFDataCreateWithBytesNoCopy").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        return fnptr(allocator, bytes, length, bytesDeallocator)
    }

    public open fun cFPropertyListCreateWithData(allocator: CFAllocatorRef, data: CFDataRef, options: CFOptionFlags, format: MutPtr, error: MutPtr): CFPropertyListRef {
        val ptr = dlsym(this.value, ("CFPropertyListCreateWithData\u0000").encodeToByteArray().map { it.toUByte() }.asPtr())
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFPropertyListCreateWithData").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        return fnptr(allocator, data, options, format, error)
    }

    public open fun cFStringGetTypeID(): CFTypeID {
        val ptr = dlsym(this.value, ("CFStringGetTypeID\u0000").encodeToByteArray().map { it.toUByte() }.asPtr())
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFStringGetTypeID").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        return fnptr()
    }

    public open fun cFStringCreateWithCStringNoCopy(alloc: CFAllocatorRef, cStr: ConstPtr, encoding: CFStringEncoding, contentsDeallocator: CFAllocatorRef): CFStringRef {
        val ptr = dlsym(this.value, ("CFStringCreateWithCStringNoCopy\u0000").encodeToByteArray().map { it.toUByte() }.asPtr())
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFStringCreateWithCStringNoCopy").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        return fnptr(alloc, cStr, encoding, contentsDeallocator)
    }

    public open fun cFStringGetCString(theString: CFStringRef, buffer: MutPtr, bufferSize: CFIndex, encoding: CFStringEncoding): Boolean {
        val ptr = dlsym(this.value, ("CFStringGetCString\u0000").encodeToByteArray().map { it.toUByte() }.asPtr())
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFStringGetCString").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        return fnptr(theString, buffer, bufferSize, encoding)
    }

    public open fun cFDictionaryGetTypeID(): CFTypeID {
        val ptr = dlsym(this.value, ("CFDictionaryGetTypeID\u0000").encodeToByteArray().map { it.toUByte() }.asPtr())
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFDictionaryGetTypeID").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        return fnptr()
    }

    public open fun cFDictionaryGetValue(theDict: CFDictionaryRef, key: ConstPtr): ConstPtr {
        val ptr = dlsym(this.value, ConstPtr((("CFDictionaryGetValue\u0000").encodeToByteArray().map { it.toUByte() }.asPtr()).rawAddress))
        if (ptr.isNull()) {
            val err = CStr.fromPtr(dlerror())
            error("could not find function " + ("CFDictionaryGetValue").toString() + ": " + (err).toString())
        }
        val fnptr = transmute(ptr)
        return fnptr(theDict, key)
    }

    public open fun drop() {
        // Ignore errors when closing. This is also what `libloading` does:
        // https://docs.rs/libloading/0.8.6/src/libloading/os/unix/mod.rs.html#374
        dlclose(this.value)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<CFHandle> =
            object : TypeLayout<CFHandle> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CFHandle =
                    CFHandle(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: CFHandle) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CFHandle =
                    CFHandle(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CFHandle) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: MutPtr,
        ): CFHandle = CFHandle(
            valueCtorArg = value,
        )

        /**
         * Link to the CoreFoundation dylib, and look up symbols from that.
         */
        public fun new(): CFHandle {
            // We explicitly use non-versioned path here, to allow this to work on older iOS devices.
            val cfPath = rootRelative("/System/Library/Frameworks/CoreFoundation.framework/CoreFoundation")
            val handle = runPathWithCstr(cfPath, { path -> Result.Ok(dlopen(path.asPtr(), RTLD_LAZY or RTLD_LOCAL)) }).expect("failed allocating string")
            if (handle.isNull()) {
                val err = CStr.fromPtr(dlerror())
                error("could not open CoreFoundation.framework: $err")
            }
            return CFHandle(handle)
        }
    }
}
