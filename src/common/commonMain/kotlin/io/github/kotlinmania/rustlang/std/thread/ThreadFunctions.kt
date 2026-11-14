package io.github.kotlinmania.rustlang.std.thread

import io.github.kotlinmania.rustlang.std.thread.joinhandle.JoinHandle
import io.github.kotlinmania.rustlang.std.thread.thread.Thread

public fun assertSyncAndSend() {
    fun <T> assertBoth() {}
    assertBoth<JoinHandle<Unit>>()
    assertBoth<Thread>()
}
