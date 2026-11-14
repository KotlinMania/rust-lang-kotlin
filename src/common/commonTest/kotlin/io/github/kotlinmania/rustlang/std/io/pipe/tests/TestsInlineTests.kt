// port-lint: tests io/pipe/tests.rs
package io.github.kotlinmania.rustlang.std.io.pipe.tests

import io.github.kotlinmania.rustlang.std.io.pipe.pipe

public class TestsInlineTests {
    @kotlin.test.Test
    public fun pipeCreationCloneAndRw() {
        val (rx, tx) = pipe().unwrap()
        tx.tryClone().unwrap().writeAll("12345".encodeToByteArray()).unwrap()
        (tx).let { }
        val rx2 = rx.tryClone().unwrap()
        (rx).let { }
        val s = ""
        rx2.readToString(s).unwrap()
        (rx2).let { }
        check(s == "12345")
    }
}
