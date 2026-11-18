// port-lint: source sys/thread_local/no_threads.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.nothreads

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.setthreaddescription.dropInPlace
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput

// On some targets like wasm there's no threads, so no need to generate
// thread locals and we can instead just use plain statics!

// Required for correctness of `@rustcAlignStatic`
public open class EagerStorage<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T> = UnresolvedTypeLayout(),
) {
    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<EagerStorage<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> EagerStorage<T>(ptr, layout) }, { it.value })

    // SAFETY: the target doesn't have threads.
}

public sealed class State {
    public data object Initial : State()
    public data object Alive : State()
    public data object Destroying : State()

    public fun rustEquals(other: State): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Initial -> 0
        Alive -> 1
        Destroying -> 2
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

public open class LazyStorage<T> internal constructor(
    valueCtorArg: UnsafeCell<MaybeUninit<T>>,
    stateCtorArg: Cell<State>,
) {
    // This field must be first, for correctness of `@rustcAlignStatic`
    public var value: UnsafeCell<MaybeUninit<T>> = valueCtorArg
        internal set

    public var state: Cell<State> = stateCtorArg
        internal set

    /**
     * Gets a pointer to the TLS value, potentially initializing it with the
     * provided parameters.
     *
     * The resulting pointer may not be used after reentrant inialialization
     * has occurred.
     */
    public open fun get(i: T?, f: () -> T): ConstPtr =
        if (this.state.get() == State.Alive) {
            constPtrFromRustCast(this.value.get())
        } else {
            this.initialize(i, f)
        }

    public open fun initialize(i: T?, f: () -> T): ConstPtr {
        val value = (i?.let(Option::take) ?: f())
        // Destroy the old value if it is initialized
        // Note(#110897): maybe abort on recursive initialization.
        if (this.state.get() == State.Alive) {
            this.state.set(State.Destroying)
            // Safety: we check for no initialization during drop below
            run {
                dropInPlace(mutPtrFromRustCast(this.value.get()))
            }
            this.state.set(State.Initial)
        }
        // Guard against initialization during drop
        if (this.state.get() == State.Destroying) {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        out.writeFmt("fatal runtime error: " + ("Attempted to initialize thread-local while it is being dropped").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("Attempted to initialize thread-local while it is being dropped").toString() + ", aborting\n"
                }
                abort()
            }
        }
        run {
            this.value.get().write(MaybeUninit.new(value, UnresolvedTypeLayout<Any>()))
        }
        this.state.set(State.Alive)
        return constPtrFromRustCast(this.value.get())
    }

    // SAFETY: the target doesn't have threads.

    public companion object {
        public operator fun <T> invoke(
            value: UnsafeCell<MaybeUninit<T>>,
            state: Cell<State>,
        ): LazyStorage<T> = LazyStorage<T>(
            valueCtorArg = value,
            stateCtorArg = state,
        )

        public fun <T> new(): LazyStorage<T> = LazyStorage(value = UnsafeCell.new(MaybeUninit.uninit(PrimitiveLayouts.U8)), state = Cell.new(State.Initial))
    }
}

public open class LocalPointer internal constructor(
    pCtorArg: Cell<MutPtr>,
) {
    public var p: Cell<MutPtr> = pCtorArg
        internal set

    public open fun get(): MutPtr = this.p.get()

    public open fun set(p: MutPtr) {
        this.p.set(p)
    }

    // SAFETY: the target doesn't have threads.

    public companion object {
        public operator fun invoke(
            p: Cell<MutPtr>,
        ): LocalPointer = LocalPointer(
            pCtorArg = p,
        )

        public fun new(): LocalPointer = LocalPointer(p = Cell.new(MutPtr(0L)))
    }
}
