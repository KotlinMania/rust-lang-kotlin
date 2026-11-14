// port-lint: source sys/os_str/utf8.rs
package io.github.kotlinmania.rustlang.std.sys.osstr.utf8

import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.mem
import io.github.kotlinmania.rustlang.std.rc.Rc
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner

// An OsString/OsStr implementation that is guaranteed to be UTF-8.

internal open class Buf(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<String>,
) : IntoInner<String>, FromInner<String, Buf>, AsInner<String> {
    public var inner: String
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(inner: String, fieldLayout: TypeLayout<String>) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Buf> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Buf(ptr, layout) }, { it.inner })

    public override fun intoInner(): String = this.inner

    public override fun asInner(): String = this.inner

    public open fun fmt(f: StringBuilder) {
        f.append((this.inner).toString())
    }

    public open fun fmtDisplay(f: StringBuilder) {
        f.append((this.inner).toString())
    }

    public open fun clone(): Buf = Buf(inner = this.inner.clone(), fieldLayout = this.fieldLayout)

    public open fun cloneFrom(source: Buf) {
        this.inner = source.inner
    }

    public open fun intoEncodedBytes(): List<UByte> = this.inner.intoBytes()

    public open fun intoString(): Result<String, Buf> = Result.Ok(this.inner)

    public open fun clear() {
        this.inner = ""
    }

    public open fun capacity(): Int = this.inner.capacity()

    public open fun pushSlice(s: Slice) {
        this.inner += s.inner
    }

    public open fun pushStr(s: String) {
        this.inner += s
    }

    public open fun reserve(additional: Int) {
        this.inner.reserve(additional)
    }

    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.inner.tryReserve(additional)

    public open fun reserveExact(additional: Int) {
        this.inner.reserveExact(additional)
    }

    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> = this.inner.tryReserveExact(additional)

    public open fun shrinkToFit() {
        this.inner.shrinkToFit()
    }

    public open fun shrinkTo(minCapacity: Int) {
        this.inner.shrinkTo(minCapacity)
    }

    public open fun asSlice(): Slice = Slice.fromStr(this.inner)

    public open fun asMutSlice(): RustMutableReference<Slice> = Slice.fromMutStr(this.inner)

    public open fun leak(): RustMutableReference<Slice> = Slice.fromMutStr(this.inner.leak())

    public open fun intoBox(): Slice = mem.transmute(this.inner.intoBoxedStr())

    public open fun intoArc(): Slice = this.asSlice().intoArc()

    public open fun intoRc(): Slice = this.asSlice().intoRc()

    /**
     * Provides plumbing to `List.truncate` without giving full mutable access
     * to the `List`.
     *
     * # Safety
     *
     * The length must be at an `OsStr` boundary, according to
     * `Slice.checkPublicBoundary`.
     */
    public open fun truncateUnchecked(len: Int) {
        this.inner.truncate(len)
    }

    /**
     * Provides plumbing to `List.extendFromSlice` without giving full
     * mutable access to the `List`.
     *
     * # Safety
     *
     * The slice must be valid for the platform encoding (as described in
     * `OsStr.fromEncodedBytesUnchecked`). For this encoding, that means
     * `other` must be valid UTF-8.
     */
    public open fun extendFromSliceUnchecked(other: List<UByte>) {
        this.inner += String.fromUtf8Unchecked(other)
    }

    public companion object {
        public fun fromInner(inner: String, fieldLayout: TypeLayout<String>): Buf = Buf(inner = inner, fieldLayout = fieldLayout)

        public fun fromEncodedBytesUnchecked(s: List<UByte>, fieldLayout: TypeLayout<String>): Buf = Buf(inner = String.fromUtf8Unchecked(s), fieldLayout = fieldLayout)

        public fun fromString(s: String, fieldLayout: TypeLayout<String>): Buf = Buf(inner = s, fieldLayout = fieldLayout)

        public fun withCapacity(capacity: Int, fieldLayout: TypeLayout<String>): Buf = Buf(inner = "", fieldLayout = fieldLayout)

        public fun fromBox(boxed: Slice, fieldLayout: TypeLayout<String>): Buf {
            val inner: String = transmuteWithLayouts(boxed, TransparentTypeLayout(fieldLayout, { ptr, layout -> Slice(ptr, layout) }, { it.inner }), TransparentTypeLayout(fieldLayout, { ptr, layout -> Buf(ptr, layout) }, { it.inner }))
            return Buf(inner = inner.intoString(), fieldLayout = fieldLayout)
        }
    }
}

internal open class Slice(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<String>,
) : CloneToUninit {
    public var inner: String
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(inner: String, fieldLayout: TypeLayout<String>) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Slice> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Slice(ptr, layout) }, { it.inner })

    public open fun fmt(f: StringBuilder) {
        f.append((this.inner).toString())
    }

    public open fun fmtDisplay(f: StringBuilder) {
        f.append((this.inner).toString())
    }

    public open fun asEncodedBytes(): List<UByte> = this.inner.encodeToByteArray().map { it.toUByte() }

    public open fun tryCheckPublicBoundary(index: Int): Unit? =
        if (this.inner.isCharBoundary(index)) {} else {
            null
        }

    public open fun checkPublicBoundary(index: Int) {
        if (this.tryCheckPublicBoundary(index) == null) {
            error("byte index $index is not an OsStr boundary")
        }
    }

    public open fun toStr(): Result<String, Utf8Error> = Result.Ok(this.inner)

    public open fun toStringLossy(): String = this.inner

    public open fun toOwned(): Buf = Buf(inner = this.inner.toOwned(), fieldLayout = this.fieldLayout)

    public open fun cloneInto(buf: RustMutableReference<Buf>) {
        this.inner.cloneInto((buf).read().inner)
    }

    public open fun intoBox(): Slice {
        val boxed: String = this.inner.into()
        return transmuteWithLayouts(boxed, this.fieldLayout, TransparentTypeLayout(this.fieldLayout, { ptr, layout -> Slice(ptr, layout) }, { it.inner }))
    }

    public open fun intoArc(): Slice {
        val arc: String = Arc.from(this.inner)
        return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
    }

    public open fun intoRc(): Slice {
        val rc: String = Rc.from(this.inner)
        return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
    }

    public open fun makeAsciiLowercase() {
        this.inner.makeAsciiLowercase()
    }

    public open fun makeAsciiUppercase() {
        this.inner.makeAsciiUppercase()
    }

    public open fun toAsciiLowercase(): Buf = Buf(inner = this.inner.toAsciiLowercase(), fieldLayout = this.fieldLayout)

    public open fun toAsciiUppercase(): Buf = Buf(inner = this.inner.toAsciiUppercase(), fieldLayout = this.fieldLayout)

    public open fun isAscii(): Boolean = this.inner.isAscii()

    public open fun eqIgnoreAsciiCase(other: Slice): Boolean = this.inner.eqIgnoreAsciiCase(other.inner)

    public open fun cloneToUninit(dst: MutPtr) {
        // SAFETY: we're just a transparent wrapper around [u8]
        this.inner.cloneToUninit(dst)
    }

    public companion object {
        public fun fromEncodedBytesUnchecked(s: List<UByte>): Slice = Slice.fromStr(String.fromUtf8Unchecked(s))

        public fun fromStr(s: String): Slice {
            // SAFETY: Slice is just a wrapper over str.
            return mem.transmute(s)
        }

        public fun fromMutStr(s: String): RustMutableReference<Slice> {
            // SAFETY: Slice is just a wrapper over str.
            return mem.transmute(s)
        }

        public fun emptyBox(): Slice {
            val boxed: String = Default.default()
            return mem.transmute(boxed)
        }
    }
}
