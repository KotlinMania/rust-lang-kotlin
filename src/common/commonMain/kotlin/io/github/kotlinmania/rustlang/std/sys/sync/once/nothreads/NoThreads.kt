// port-lint: source sys/sync/once/no_threads.rs
package io.github.kotlinmania.rustlang.std.sys.sync.once.nothreads

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.std.sync.once.OnceExclusiveState

public open class Once internal constructor(
    stateCtorArg: Cell<State>,
) {
    public var state: Cell<State> = stateCtorArg
        internal set

    // Safety: threads are not supported on this platform.

    public open fun isCompleted(): Boolean = (this.state.get() == State.Complete).toUByte()

    public open fun state(): OnceExclusiveState =
        when (this.state.get()) {
            State.Incomplete -> OnceExclusiveState.Incomplete
            State.Poisoned -> OnceExclusiveState.Poisoned
            State.Complete -> OnceExclusiveState.Complete
            else -> error("invalid Once state")
        }

    public open fun setState(newState: OnceExclusiveState) {
        this.state.set(when (newState) {
            OnceExclusiveState.Incomplete -> State.Incomplete
            OnceExclusiveState.Poisoned -> State.Poisoned
            OnceExclusiveState.Complete -> State.Complete
        })
    }

    public open fun wait(ignorePoisoning: Boolean) {
        error("not implementable on this target")
    }

    public open fun call(ignorePoisoning: Boolean, f: (io.github.kotlinmania.rustlang.std.sync.once.OnceState) -> Unit) {
        val state = this.state.get()
        when {
            state == State.Poisoned && !ignorePoisoning -> {
                // Panic to propagate the poison.
                error("Once instance has previously been poisoned")
            }
            state == State.Incomplete || state == State.Poisoned -> {
                this.state.set(State.Running)
                // `guard` will set the new state on drop.
                val guard = CompletionGuard(state = this.state, setStateOnDropTo = State.Poisoned)
                // Run the function, letting it know if we're poisoned or not.
                val fState = io.github.kotlinmania.rustlang.std.sync.once.OnceState(inner = OnceState(poisoned = (state == State.Poisoned).toUByte(), setStateTo = Cell.new(State.Complete)))
                f(fState)
                guard.setStateOnDropTo = fState.inner.setStateTo.get()
            }
            state == State.Running -> {
                error("one-time initialization may not be performed recursively")
            }
            else -> {}
        }
    }

    public companion object {
        public operator fun invoke(
            state: Cell<State>,
        ): Once = Once(
            stateCtorArg = state,
        )

        public fun new(): Once = Once(state = Cell.new(State.Incomplete))

        public fun newComplete(): Once = Once(state = Cell.new(State.Complete))
    }
}

public open class OnceState internal constructor(
    public var poisoned: Boolean,
    setStateToCtorArg: Cell<State>,
) {
    public var setStateTo: Cell<State> = setStateToCtorArg
        internal set

    public open fun isPoisoned(): Boolean = this.poisoned

    public open fun poison() {
        this.setStateTo.set(State.Poisoned)
    }

    public companion object {
        public operator fun invoke(
            poisoned: Boolean,
            setStateTo: Cell<State>,
        ): OnceState = OnceState(
            poisoned = poisoned,
            setStateToCtorArg = setStateTo,
        )
    }
}

public sealed class State {
    public data object Incomplete : State()
    public data object Poisoned : State()
    public data object Running : State()
    public data object Complete : State()

    public fun rustEquals(other: State): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Incomplete -> 0
        Poisoned -> 1
        Running -> 2
        Complete -> 3
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

public open class CompletionGuard internal constructor(
    stateCtorArg: Cell<State>,
    public var setStateOnDropTo: State,
) : AutoCloseable {
    public var state: Cell<State> = stateCtorArg
        internal set

    override fun close() {
        this.drop()
    }

    public open fun drop() {
        this.state.set(this.setStateOnDropTo)
    }

    public companion object {
        public operator fun invoke(
            state: Cell<State>,
            setStateOnDropTo: State,
        ): CompletionGuard = CompletionGuard(
            stateCtorArg = state,
            setStateOnDropTo = setStateOnDropTo,
        )
    }
}
