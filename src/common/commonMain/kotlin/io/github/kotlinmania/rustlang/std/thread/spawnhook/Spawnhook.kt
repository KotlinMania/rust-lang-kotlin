// port-lint: source thread/spawnhook.rs
package io.github.kotlinmania.rustlang.std.thread.spawnhook

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.std.sync.UniqueArc
import io.github.kotlinmania.rustlang.std.thread.LocalKey
import io.github.kotlinmania.rustlang.std.thread.localimpl.threadLocalInner
import io.github.kotlinmania.rustlang.std.thread.thread.Thread

/**
 * A thread local linked list of spawn hooks.
 *
 * It is a linked list of Arcs, such that it can very cheaply be inherited by spawned threads.
 *
 * (That technically makes it a set of linked lists with shared tails, so a linked tree.)
 */
public val SPAWN_HOOKS: LocalKey<Cell<SpawnHooks>> = threadLocalInner(key, Cell, SpawnHooks, Cell.new(SpawnHooks(first = null)))

public open class SpawnHooks(
    public var first: SpawnHook?,
) {
    public open fun clone(): SpawnHooks = SpawnHooks(first)

    // Manually implement drop to prevent deep recursion when dropping linked Arc list.
    public open fun drop() {
        var next = this.first.also { this.first = null }
        while (true) {
            val matchVal = next.andThen({ it.intoInner() })
            if (!(true)) break
            val hook = matchVal.hook
            val n = matchVal.next
            drop(hook)
            next = n
        }
    }
}

public open class SpawnHook(
    public var hook: (Thread) -> () -> Unit,
    public var next: SpawnHook?,
)

/**
 * Registers a function to run for every newly thread spawned.
 *
 * The hook is executed in the parent thread, and returns a function
 * that will be executed in the new thread.
 *
 * The hook is called with the `Thread` handle for the new thread.
 *
 * The hook will only be added for the current thread and is inherited by the threads it spawns.
 * In other words, adding a hook has no effect on already running threads (other than the current
 * thread) and the threads they might spawn in the future.
 *
 * The hooks will run in reverse order, starting with the most recently added.
 *
 * Hooks can only be added, not removed.
 * Note that this does *not* mean that they are guaranteed to run. See [Builder.noHooks].
 * You cannot rely on a hook running for soundness.
 *
 * # Usage
 *
 * ```kotlin
 * @feature(threadSpawnHook)
 * public fun doctestEntry() {
 *     addSpawnHook({
 *         0 until 0
 *         // This will run in the parent (spawning) thread.
 *         {
 *             0 until 0
 *         }
 *     })
 * }
 * ```
 *
 * # Example
 *
 * A spawn hook can be used to "inherit" a thread local from the parent thread:
 *
 * ```kotlin
 * import std.cell.Cell
 *
 * @feature(threadSpawnHook)
 * public fun doctestEntry() {
 *     threadLocal(X, Cell, Cell, new, 0)
 *     // This needs to be done once in the main thread before spawning any threads.
 *     addSpawnHook({
 *         // Get the value of X in the spawning thread.
 *         val value = X.get()
 *         // Set the value of X in the newly spawned thread.
 *         { X.set(value) }
 *     })
 *     X.set(123)
 *     spawn({
 *         check(X.get() == 123)
 *     }).join().unwrap()
 * }
 * ```
 */
public fun addSpawnHook(hook: (Thread) -> G) {
    SPAWN_HOOKS.with({ h ->
        // Perform all fallible operations before taking the current hooks (see #159923)
        var newFirst = UniqueArc.new(SpawnHook(hook = { thread -> hook(thread) }, next = null))
        var hooks = h.also { h = null }
        newFirst.next = hooks.first.also { hooks.first = null }
        hooks.first = UniqueArc.intoArc(newFirst)
        h.set(hooks)
    })
}

/**
 * Runs all the spawn hooks.
 *
 * Called on the parent thread.
 *
 * Returns the functions to be called on the newly spawned thread.
 */
internal fun runSpawnHooks(thread: Thread): ChildSpawnHooks {
    // Get a snapshot of the spawn hooks.
    // (Increments the refcount to the first node.)
    return when (val matchVal = SPAWN_HOOKS.tryWith({ hooks ->
        val snapshot = hooks.also { hooks = null }
        hooks.set(snapshot.clone())
        snapshot
    })) {
        is Ok -> {
            val hooks = matchVal.value
            // Iterate over the hooks, run them, and collect the results in a vector.
            val toRun: Vec = Iter.successors(hooks.first.asDeref(), { hook -> hook.next.asDeref() }).map({ hook -> (hook.hook)(thread) }).collect()
            // Pass on the snapshot of the hooks and the results to the new thread,
            // which will then run SpawnHookResults.run().
            ChildSpawnHooks(hooks = hooks, toRun = toRun)
        }
        else -> {
            // TLS has been destroyed. Skip running the hooks.
            // See https://github.com/rust-lang/rust/issues/138696
            ChildSpawnHooks.default()
        }
    }
}

/**
 * The results of running the spawn hooks.
 *
 * This struct is sent to the new thread.
 * It contains the inherited hooks and the closures to be run.
 */
internal open class ChildSpawnHooks(
    public var hooks: SpawnHooks,
    public var toRun: List<() -> Unit>,
) {
    // This is run on the newly spawned thread, directly at the start.
    internal open fun inheritAndRun() {
        SPAWN_HOOKS.set(this.hooks)
        for (run in this.toRun) {
            run()
        }
    }
}
