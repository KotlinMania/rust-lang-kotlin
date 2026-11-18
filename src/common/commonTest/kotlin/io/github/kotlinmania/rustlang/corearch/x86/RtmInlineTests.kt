// port-lint: tests x86/rtm.rs
package io.github.kotlinmania.rustlang.corearch.x86

// Intel's Restricted Transactional Memory (RTM).
//
// This CPU feature is available on Intel Broadwell or later CPUs (and some Haswell).
//
// The reference is [Intel 64 and IA-32 Architectures Software Developer's
// Manual Volume 2: Instruction Set Reference, A-Z][intel64Ref].
//
// [Wikipedia][wikipediaRtm] provides a quick overview of the assembly instructions, and
// Intel's [programming considerations][intelConsid] details what sorts of instructions within a
// transaction are likely to cause an abort.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [wikipediaRtm]: https://en.wikipedia.org/wiki/TransactionalSynchronizationExtensions#RestrictedTransactionalMemory
// [intelConsid]: https://software.intel.com/en-us/cpp-compiler-developer-guide-and-reference-intel-transactional-synchronization-extensions-intel-tsx-programming-considerations

public fun testXbegin() {
    var x = 0
    for (unused in 0 until 10) {
        val code = xbegin()
        if (code == XBEGIN_STARTED) {
            x += 1
            run {
                xend()
            }
            check(x == 1)
            break
        }
        check(x == 0)
    }
}

public fun testXabort() {
    val ABORT_CODE: UInt = 42u
    // aborting outside a transactional region does nothing
    run {
        xabort(ABORT_CODE)
    }
    for (unused in 0 until 10) {
        var x = 0
        val code = xbegin()
        if (code == XBEGIN_STARTED) {
            x += 1
            run {
                xabort(ABORT_CODE)
            }
        } else if (code and XABORT_EXPLICIT != 0u) {
            val testAbortCode = xabortCode(code)
            check(testAbortCode == ABORT_CODE)
        }
        check(x == 0)
    }
}

public fun testXtest() {
    check(xtest() == 0u.toUByte())
    for (unused in 0 until 10) {
        val code = xbegin()
        if (code == XBEGIN_STARTED) {
            val inTx = xtest()
            run {
                xend()
            }
            // putting the assert inside the transaction would abort the transaction on fail
            // without any output/panic/etc
            check(inTx == 1u.toUByte())
            break
        }
    }
}
