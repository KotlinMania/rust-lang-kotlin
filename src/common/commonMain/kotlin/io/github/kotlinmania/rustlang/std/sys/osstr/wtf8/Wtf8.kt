// port-lint: source sys/os_str/wtf8.rs
package io.github.kotlinmania.rustlang.std.sys.osstr.wtf8

import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.wtf8.Wtf8Buf
import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.wtf8.Wtf8
import io.github.kotlinmania.rustlang.std.rc.Rc
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner

// The underlying OsString/OsStr implementation on Windows is a
// wrapper around the "WTF-8" encoding; see the `wtf8` module for more.

internal open class Buf(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Wtf8Buf>,
) : IntoInner<Wtf8Buf>, FromInner<Wtf8Buf, Buf>, AsInner<Wtf8> {
    public var inner: Wtf8Buf
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(inner: Wtf8Buf, fieldLayout: TypeLayout<Wtf8Buf>) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Buf> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Buf(ptr, layout) }, { it.inner })

    public override fun intoInner(): Wtf8Buf = this.inner

    public override fun asInner(): Wtf8 = this.inner

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

    public open fun intoString(): Result<String, Buf> = this.inner.intoString().mapErr({ buf -> Buf(inner = buf, fieldLayout = this.fieldLayout) })

    public open fun clear() {
        this.inner.clear()
    }

    public open fun capacity(): Int = this.inner.capacity()

    public open fun pushSlice(s: Slice) {
        this.inner.pushWtf8(s.inner)
    }

    public open fun pushStr(s: String) {
        this.inner.pushStr(s)
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

    public open fun asSlice(): Slice {
        // SAFETY: Slice is just a wrapper for Wtf8,
        // and self.inner.asSlice() returns &Wtf8.
        // Therefore, transmuting &Wtf8 to &Slice is safe.
        return transmute(this.inner.asSlice())
    }

    public open fun asMutSlice(): RustMutableReference<Slice> {
        // SAFETY: Slice is just a wrapper for Wtf8,
        // and self.inner.asMutSlice() returns Wtf8.
        // Therefore, transmuting Wtf8 to Slice is safe.
        // Additionally, care should be taken to ensure the slice
        // is always valid Wtf8.
        return transmute(this.inner.asMutSlice())
    }

    public open fun leak(): RustMutableReference<Slice> = transmute(this.inner.leak())

    public open fun intoBox(): Slice = transmute(this.inner.intoBox())

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
     * `other` must be valid WTF-8.
     *
     * Additionally, this method bypasses the WTF-8 surrogate joining, so
     * either `self` must not end with a leading surrogate half, or `other`
     * must not start with a trailing surrogate half.
     */
    public open fun extendFromSliceUnchecked(other: List<UByte>) {
        run {
            this.inner.extendFromSliceUnchecked(other)
        }
    }

    public companion object {
        public fun fromInner(inner: Wtf8Buf, fieldLayout: TypeLayout<Wtf8Buf>): Buf = Buf(inner = inner, fieldLayout = fieldLayout)

        public fun fromEncodedBytesUnchecked(s: List<UByte>, fieldLayout: TypeLayout<Wtf8Buf>): Buf = Buf(inner = Wtf8Buf.fromBytesUnchecked(s), fieldLayout = fieldLayout)

        public fun fromString(s: String, fieldLayout: TypeLayout<Wtf8Buf>): Buf = Buf(inner = Wtf8Buf.fromString(s), fieldLayout = fieldLayout)

        public fun withCapacity(capacity: Int, fieldLayout: TypeLayout<Wtf8Buf>): Buf = Buf(inner = Wtf8Buf.withCapacity(capacity), fieldLayout = fieldLayout)

        public fun fromBox(boxed: Slice, fieldLayout: TypeLayout<Wtf8Buf>): Buf {
            val inner: Wtf8 = transmute(boxed)
            return Buf(inner = Wtf8Buf.fromBox(inner), fieldLayout = fieldLayout)
        }
    }
}

internal open class Slice(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Wtf8>,
) : CloneToUninit {
    public var inner: Wtf8
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(inner: Wtf8, fieldLayout: TypeLayout<Wtf8>) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Slice> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Slice(ptr, layout) }, { it.inner })

    public open fun fmt(f: StringBuilder) {
        f.append((this.inner).toString())
    }

    public open fun fmtDisplay(f: StringBuilder) {
        f.append((this.inner).toString())
    }

    public open fun asEncodedBytes(): List<UByte> = this.inner.asBytes()

    public open fun tryCheckPublicBoundary(index: Int): Unit? = this.inner.tryCheckUtf8Boundary(index).ok()

    public open fun checkPublicBoundary(index: Int) {
        this.inner.checkUtf8Boundary(index)
    }

    public open fun toStr(): Result<String, Utf8Error> = this.inner.asStr()

    public open fun toStringLossy(): String = this.inner.toStringLossy()

    public open fun toOwned(): Buf = Buf(inner = this.inner.toOwned(), fieldLayout = ObjectSlotLayout<Wtf8Buf>())

    public open fun cloneInto(buf: RustMutableReference<Buf>) {
        this.inner.cloneInto((buf).read().inner)
    }

    public open fun intoArc(): Slice {
        val arc = this.inner.intoArc()
        return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
    }

    public open fun intoRc(): Slice {
        val rc = this.inner.intoRc()
        return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
    }

    public open fun makeAsciiLowercase() {
        this.inner.makeAsciiLowercase()
    }

    public open fun makeAsciiUppercase() {
        this.inner.makeAsciiUppercase()
    }

    public open fun toAsciiLowercase(): Buf = Buf(inner = this.inner.toAsciiLowercase(), fieldLayout = ObjectSlotLayout<Wtf8Buf>())

    public open fun toAsciiUppercase(): Buf = Buf(inner = this.inner.toAsciiUppercase(), fieldLayout = ObjectSlotLayout<Wtf8Buf>())

    public open fun isAscii(): Boolean = this.inner.isAscii()

    public open fun eqIgnoreAsciiCase(other: Slice): Boolean = this.inner.eqIgnoreAsciiCase(other.inner)

    public open fun cloneToUninit(dst: MutPtr) {
        // SAFETY: we're just a transparent wrapper around Wtf8
        this.inner.cloneToUninit(dst)
    }

    public companion object {
        public fun fromEncodedBytesUnchecked(s: List<UByte>): Slice = transmute(Wtf8.fromBytesUnchecked(s))

        public fun fromStr(s: String): Slice = transmute(Wtf8.fromStr(s))

        public fun emptyBox(): Slice = transmute(Wtf8.emptyBox())
    }
}
