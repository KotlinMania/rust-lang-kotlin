// port-lint: source sys/thread_local/native/eager.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.native.eager

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.cell.UnsafeCell
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.dropInPlace
import io.github.kotlinmania.rustlang.std.sys.threadlocal.abortOnDtorUnwind
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.tests.destructors

public sealed class State(
    public val value: Int,
) {
    public data object Alive : State(0)
    public data object Unregistered : State(Alive.value + 1)
    public data object Destroyed : State(Unregistered.value + 1)
}

public open class Storage<T> internal constructor(
    // This field must be first, for correctness of `@rustcAlignStatic`
    public var value: UnsafeCell<T>,
    stateCtorArg: Cell<State>,
) {
    public var state: Cell<State> = stateCtorArg
        internal set

    /**
     * Gets a pointer to the TLS value. If the TLS variable has been destroyed,
     * a null pointer is returned.
     *
     * The resulting pointer may not be used after thread destruction has
     * occurred.
     *
     * # Safety
     * The `self` reference must remain valid until the TLS destructor is run.
     */
    public open fun get(): ConstPtr =
        if (this.state.get() == State.Alive) {
            this.value.get()
        } else {
            this.getOrInitSlow()
        }

    public open fun getOrInitSlow(): ConstPtr {
        when (this.state.get()) {
            State.Unregistered -> {}
            State.Alive -> { return this.value.get() }
            State.Destroyed -> { return ConstPtr(0L) }
        }
        // Register the destructor.
        // SAFETY:
        // The caller guarantees that `self` will be valid until thread destruction.
        run {
            destructors.register(this.castMut().cast(), destroy<T>)
        }
        this.state.set(State.Alive)
        return this.value.get()
    }

    public companion object {
        public operator fun <T> invoke(
            value: UnsafeCell<T>,
            state: Cell<State>,
        ): Storage<T> = Storage<T>(
            value = value,
            stateCtorArg = state,
        )

        public fun <T> new(value: T): Storage<T> = Storage(state = Cell.new(State.Unregistered), value = UnsafeCell.new(value))
    }
}

/**
 * Transition an `Alive` TLS variable into the `Destroyed` state, dropping its
 * value.
 *
 * # Safety
 * * Must only be called at thread destruction.
 * * `ptr` must point to an instance of `Storage` with `Alive` state and be
 *   valid for accessing that instance.
 */
public fun <T> destroy(ptr: MutPtr, typeLayoutT: TypeLayout<T>) {
    // Print a nice abort message if a panic occurs.
    abortOnDtorUnwind({
        val storage = (UnresolvedTypeLayout<Storage<T>>()).readNative(((ConstPtr(ptr.rawAddress, ptr.rawMeta))).rawAddress)
        // Update the state before running the destructor as it may attempt to
        // access the variable.
        storage.state.set(State.Destroyed)
        run {
            dropInPlace(storage.value.get())
        }
    })
}
