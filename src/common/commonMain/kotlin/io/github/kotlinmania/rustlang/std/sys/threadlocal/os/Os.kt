// port-lint: source sys/thread_local/os.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.os

import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.panic.unwindsafe.AssertUnwindSafe
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.alloc.System
import io.github.kotlinmania.rustlang.std.panic.catchUnwind
import io.github.kotlinmania.rustlang.std.panic.resumeUnwind
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sync.poison.Guard
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.abortOnDtorUnwind
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.Key
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.LazyKey
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.get
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.set

public fun interface NewDtorCallback {
    public operator fun invoke(arg0: MutPtr)
}

/**
 * Use a regular global static to store this key; the state provided will then be
 * thread-local.
 * INVARIANT: ALIGN must be a valid alignment, and no less than `valueAlign<T>`.
 */
public open class Storage<T>(
    public var key: LazyKey,
    public var marker: Unit,
)

public open class Value<T>(
    // This field must be first, for correctness of `@rustcAlignStatic`
    public var value: T,
    // INVARIANT: if this value is stored under a TLS key, `key` must be that `key`.
    public var key: Key,
) {
    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Value<T>> =
            object : TypeLayout<Value<T>> {
                override val alignBytes: Long = listOf(4L, typeLayoutT.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutT.sizeBytes, 4L)
                override val sizeBytes: Long = alignUpBytes((off1 + 4L), alignBytes)
                override fun readNative(addr: Long): Value<T> =
                    Value(
                        (typeLayoutT).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Value<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                    (PrimitiveLayouts.U32).writeNative(addr + off1, value.key)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Value<T> =
                    Value(
                        (typeLayoutT).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Value<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off1.toInt(), value.key)
                }
            }
    }
}

public fun <T> valueAlign(typeLayoutT: TypeLayout<T>): Int = alignOf<Value<T>>(typeLayoutT = Value.typeLayout<T>(typeLayoutT = typeLayoutT))

/**
 * Equivalent to `Holder<Value<T>, System>`, but potentially over-aligned.
 */
public open class AlignedSystemBox<T> internal constructor(
    ptrCtorArg: NonNull,
) {
    public var ptr: NonNull = ptrCtorArg
        internal set

    public companion object {
        public operator fun <T> invoke(
            ptr: NonNull,
        ): AlignedSystemBox<T> = AlignedSystemBox<T>(
            ptrCtorArg = ptr,
        )
    }
}

public fun <T> AlignedSystemBox.Companion.new(v: Value<T>, align: Int, typeLayoutT: TypeLayout<T>): AlignedSystemBox<T, Align> {
    val layout =
        when (val matchVal = Layout.new<Value<T>>(typeLayoutT = Value.typeLayout<T>(typeLayoutT = typeLayoutT)).alignTo(align)) {
            is Result.Ok<*> -> {
                val v1 = matchVal.value
                v1
            }
            else -> {
                val err = matchVal
                val err1 = err.asRef().map({ Unit })
                run {
                    when (val matchVal1 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out = matchVal1
                            Write.writeFmt(out, "fatal runtime error: " + ("unwrap failed: Layout :: new ::< Value < T >> () . align_to (ALIGN) = $err1").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("unwrap failed: Layout :: new ::< Value < T >> () . align_to (ALIGN) = $err1").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
        }
    // We use the System allocator here to avoid interfering with a potential
    // Global allocator using thread-local storage.
    val ptr: MutPtr = (System.alloc(layout)).cast()
    val ptr1 = NonNull.new(ptr) ?: run {
    when (val matchVal2 = panicOutput()) {
        null -> {}
        else -> {
            val out1 = matchVal2
            Write.writeFmt(out1, "fatal runtime error: " + ("Allocation failure").toString() + ", aborting\n")
        }
    }
    run {
        "fatal runtime error: " + ("Allocation failure").toString() + ", aborting\n"
    }
    abort()
}
    ptr1.write(v)
    return AlignedSystemBox(ptr = ptr1)
}

public fun <T> AlignedSystemBox.Companion.intoRaw(b: AlignedSystemBox<T, Align>, align: Int): MutPtr {
    val md = b
    return md.ptr.asPtr()
}

public fun <T> AlignedSystemBox.Companion.fromRaw(ptr: MutPtr, align: Int): AlignedSystemBox<T, Align> = AlignedSystemBox(ptr = NonNull.newUnchecked(ptr))

public fun <T> AlignedSystemBox<T, Align>.deref(align: Int): Value<T> = (this.ptr.asPtr())

public fun <T> AlignedSystemBox<T, Align>.drop(align: Int, typeLayoutT: TypeLayout<T>) {
    val layout =
        when (val matchVal = Layout.new<Value<T>>(typeLayoutT = Value.typeLayout<T>(typeLayoutT = typeLayoutT)).alignTo(align)) {
            is Result.Ok<*> -> {
                val v = matchVal.value
                v
            }
            else -> {
                val err = matchVal
                val err1 = err.asRef().map({ Unit })
                run {
                    when (val matchVal1 = panicOutput()) {
                        null -> {}
                        else -> {
                            val out = matchVal1
                            Write.writeFmt(out, "fatal runtime error: " + ("unwrap failed: Layout :: new ::< Value < T >> () . align_to (ALIGN) = $err1").toString() + ", aborting\n")
                        }
                    }
                    run {
                        "fatal runtime error: " + ("unwrap failed: Layout :: new ::< Value < T >> () . align_to (ALIGN) = $err1").toString() + ", aborting\n"
                    }
                    abort()
                }
            }
        }
    run {
        val unwindResult = catchUnwind(AssertUnwindSafe({ this.ptr.dropInPlace() }))
        System.dealloc(this.ptr.asPtr().cast(), layout)
        if (unwindResult is Result.Err<*>) {
            val payload = (unwindResult as Result.Err<*>).error
            resumeUnwind(payload)
        }
    }
}

public fun <T> Storage.Companion.new(align: Int): Storage<T> = Storage(key = LazyKey.new(destroyValue<T>), marker = Unit)

/**
 * Gets a pointer to the TLS value, potentially initializing it with the
 * provided parameters. If the TLS variable has been destroyed, a null
 * pointer is returned.
 *
 * The resulting pointer may not be used after reentrant inialialization
 * or thread destruction has occurred.
 */
public fun <T> Storage<T, Align>.get(i: T?, f: () -> T, align: Int): ConstPtr {
    val key = this.key.force()
    val ptr = (get(key)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    return if ((ptr).rawAddress.toInt() > 1) {
        // SAFETY: the check ensured the pointer is safe (its destructor
        // is not running) + it is coming from a trusted source (self).
        (ObjectSlotLayout<Value<T>>()).readNative((ptr).rawAddress).value
    } else {
        // SAFETY: trivially correct.
        Storage.tryInitialize(key, ptr, i, f)
    }
}

/**
 * # Safety
 * * `key` must be the result of calling `self.key.force()`
 * * `ptr` must be the current value associated with `key`.
 */
public fun <T> Storage.Companion.tryInitialize(key: Key, ptr: MutPtr, i: T?, f: () -> T, align: Int, typeLayoutT: TypeLayout<T>): ConstPtr {
    if ((ptr).rawAddress.toInt() == 1) {
        // destructor is running
        return ConstPtr(0L)
    }
    val value = AlignedSystemBox.new(Value(value = (i?.let(Option::take) ?: f()), key = key))
    val ptr1 = AlignedSystemBox.intoRaw(value)
    // SAFETY:
    // * key came from a `LazyKey` and is thus correct.
    // * `ptr` is a correct pointer that can be destroyed by the key destructor.
    // * the value is stored under the key that it contains.
    val old1 =
        run {
            val old = (get(key)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
            set(key, mutPtrFromRustCast(ptr1))
            old
        }
    if (!old1.isNull()) {
        // If the variable was recursively initialized, drop the old value.
        // SAFETY: We cannot be inside a `LocalKey.with` scope, as the
        // initializer has already returned and the next scope only starts
        // after we return the pointer. Therefore, there can be no references
        // to the old value.
        drop(AlignedSystemBox.fromRaw(old1))
    }
    // SAFETY: We just created this value above.
    return ptr1.value
}

public fun destroyValue(ptr: MutPtr, align: Int) {
    // SAFETY:
    //
    // The OS TLS ensures that this key contains a null value when this
    // destructor starts to run. We set it back to a sentinel value of 1 to
    // ensure that any future calls to `get` for this thread will return
    // `None`.
    //
    // Note that to prevent an infinite loop we reset it back to null right
    // before we return from the destructor ourselves.
    abortOnDtorUnwind({
        val ptr1 = AlignedSystemBox.fromRaw(MutPtr(ptr.rawAddress, ptr.rawMeta))
        val key = ptr1.key
        // SAFETY: `key` is the TLS key `ptr` was stored under.
        set(key, withoutProvenanceMut(1))
        drop(ptr1)
        // SAFETY: `key` is the TLS key `ptr` was stored under.
        set(key, MutPtr(0L))
        // Make sure that the runtime cleanup will be performed
        // after the next round of TLS destruction.
        Guard.enable()
    })
}

internal open class LocalPointer(
    public var key: LazyKey,
) {
    public open fun get(): MutPtr = (get(this.key.force())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

    public open fun set(p: MutPtr) {
        set(this.key.force(), MutPtr(p.rawAddress, p.rawMeta))
    }

    public companion object {
        public fun new(dtor: NewDtorCallback?): LocalPointer = LocalPointer(key = LazyKey.new(dtor))
    }
}
