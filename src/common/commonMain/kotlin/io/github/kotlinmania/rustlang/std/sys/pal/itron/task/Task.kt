// port-lint: source sys/pal/itron/task.rs
package io.github.kotlinmania.rustlang.std.sys.pal.itron.task

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.ID
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.Pri
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.getPri
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.getTid
import io.github.kotlinmania.rustlang.std.sys.pal.itron.error.ItronError
import io.github.kotlinmania.rustlang.std.sys.pal.itron.error.fail
import io.github.kotlinmania.rustlang.std.sys.pal.itron.error.failAborting

/**
 * Gets the ID of the task in Running state. Panics on failure.
 */
public fun currentTaskId(): ID = tryCurrentTaskId().unwrapOrElse({ e -> fail(e, "get_tid") })

/**
 * Gets the ID of the task in Running state. Aborts on failure.
 */
public fun currentTaskIdAborting(): ID = tryCurrentTaskId().unwrapOrElse({ e -> failAborting(e, "get_tid") })

/**
 * Gets the ID of the task in Running state.
 */
public fun tryCurrentTaskId(): Result<ID, ItronError> {
    return run {
        val out = MaybeUninit.uninit(ObjectSlotLayout<Result<ID, ItronError>>())
        when (val tryResult = ItronError.errIfNegative(getTid(out.asMutPtr()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        Result.Ok(out.assumeInit())
    }
}

/**
 * Gets the specified task's priority. Panics on failure.
 */
public fun taskPriority(task: ID): Pri = tryTaskPriority(task).unwrapOrElse({ e -> fail(e, "get_pri") })

/**
 * Gets the specified task's priority.
 */
public fun tryTaskPriority(task: ID): Result<Pri, ItronError> {
    return run {
        val out = MaybeUninit.uninit(ObjectSlotLayout<Result<Pri, ItronError>>())
        when (val tryResult = ItronError.errIfNegative(getPri(task, out.asMutPtr()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        Result.Ok(out.assumeInit())
    }
}
