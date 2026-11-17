// port-lint: tests sys/pal/unix/weak/tests.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.weak.tests

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.sys.pal.unix.weak.weak

public class TestsInlineTests {
    @kotlin.test.Test
    public fun weakExisting() {
        val TEST_STRING: CStr = "Ferris!"
        // Try to find a symbol that definitely exists.
        weak(strlen, cs, cChar)
        val strlen = strlen.get()!!
        check(strlen(TEST_STRING.asPtr()) == TEST_STRING.countBytes())
    }

    @kotlin.test.Test
    public fun weakMissing() {
        // Try to find a symbol that definitely does not exist.
        weak(testSymbolThatDoesNotExist)
        check(testSymbolThatDoesNotExist.get() == null)
    }
}
