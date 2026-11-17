package io.github.kotlinmania.rustlang.test

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.panic.unwindsafe.AssertUnwindSafe
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.io.stdio.setOutputCapture
import io.github.kotlinmania.rustlang.std.panic.PanicHookInfo
import io.github.kotlinmania.rustlang.std.panic.catchUnwind
import io.github.kotlinmania.rustlang.std.process.Command
import io.github.kotlinmania.rustlang.std.process.ExitCode
import io.github.kotlinmania.rustlang.std.process.Termination
import io.github.kotlinmania.rustlang.std.sync.mpsc.RecvTimeoutError
import io.github.kotlinmania.rustlang.std.sync.mpsc.Sender
import io.github.kotlinmania.rustlang.std.sync.mpsc.channel
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.thread.builder.Builder
import io.github.kotlinmania.rustlang.std.thread.joinhandle.JoinHandle
import io.github.kotlinmania.rustlang.std.time.Instant
import io.github.kotlinmania.rustlang.test.bench.blackBox
import io.github.kotlinmania.rustlang.test.cli.TestOpts
import io.github.kotlinmania.rustlang.test.cli.parseOpts
import io.github.kotlinmania.rustlang.test.console.getFormatter
import io.github.kotlinmania.rustlang.test.console.listTestsConsole
import io.github.kotlinmania.rustlang.test.console.runTestsConsole
import io.github.kotlinmania.rustlang.test.event.CompletedTest
import io.github.kotlinmania.rustlang.test.event.TestEvent
import io.github.kotlinmania.rustlang.test.formatters.writeStderrDelimiter
import io.github.kotlinmania.rustlang.test.helpers.concurrency.getConcurrency
import io.github.kotlinmania.rustlang.test.helpers.shuffle.getShuffleSeed
import io.github.kotlinmania.rustlang.test.helpers.shuffle.shuffleTests
import io.github.kotlinmania.rustlang.test.options.Options
import io.github.kotlinmania.rustlang.test.options.RunIgnored
import io.github.kotlinmania.rustlang.test.options.RunStrategy
import io.github.kotlinmania.rustlang.test.options.ShouldPanic
import io.github.kotlinmania.rustlang.test.testresult.TR_OK
import io.github.kotlinmania.rustlang.test.testresult.calcResult
import io.github.kotlinmania.rustlang.test.testresult.getResultFromExitCode
import io.github.kotlinmania.rustlang.test.time.TestExecTime
import io.github.kotlinmania.rustlang.test.time.TestTimeOptions
import io.github.kotlinmania.rustlang.test.time.getDefaultTestTimeout

public typealias TestMap = MutableMap<TestId, RunningTest>

// The default console test runner. It accepts the command line
// arguments and a vector of testDescs.
public fun testMain(args: List<String>, tests: List<TestDescAndFn>): ExitCode {
    val tests1 = TestList.new(tests, TestListOrder.Unsorted)
    return testMainInner(args, tests1, null)
}

public fun testMainInner(args: List<String>, tests: TestList, options: Options?): ExitCode {
    var opts =
        when (val matchVal = parseOpts(args)) {
            is Result.Ok<*, *> -> {
                val o = (matchVal as Result.Ok<*, *>).value
                o
            }
            is Result.Err<*, *> -> {
                val msg = (matchVal as Result.Err<*, *>).error
                println("error: $msg")
                return ERROR_EXIT_CODE.into()
            }
            null -> { return ExitCode.SUCCESS }
        }
    if (options != null) {
        val options1 = options
        opts.options = options1
    }
    if (opts.list) {
        when (val matchVal1 = listTestsConsole(opts, tests)) {
            is Err -> {
                val e = matchVal1.error
                println("error: io error when listing tests: $e")
                return ERROR_EXIT_CODE.into()
            }
            else -> {}
        }
    } else {
        if (!opts.nocapture) {
            // If we encounter a non-unwinding panic, flush any captured output from the current test,
            // and stop capturing output to ensure that the non-unwinding panic message is visible.
            // We also acquire the locks for both output streams to prevent output from other threads
            // from interleaving with the panic message or appearing after it.
            val builtinPanicHook = panic.takeHook()
            val hook =
                { info: PanicHookInfo ->
                    if (!info.canUnwind()) {
                        forget(stderr().lock())
                        val stdout = stdout().lock()
                        when (val matchVal2 = setOutputCapture(null)) {
                            null -> {}
                            else -> {
                                val captured = matchVal2
                                when (val matchVal3 = captured.lock()) {
                                    is Ok -> {
                                        val data = matchVal3.value
                                        stdout.writeAll(data)
                                        stdout.flush()
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                    builtinPanicHook(info)
                }
            panic.setHook(hook)
            // Use a thread spawning hook to make new threads inherit output capturing.
            thread.addSpawnHook({
                // Get and clone the output capture of the current thread.
                val outputCapture = setOutputCapture(null)
                setOutputCapture(outputCapture.clone())
                // Set the output capture of the new thread.
                {
                    setOutputCapture(outputCapture)
                }
            })
        }
        val res = runTestsConsole(opts, tests)
        // Prevent Valgrind from reporting reachable blocks in users' unit tests.
        (panic.takeHook()).let { }
        when {
            res is Result.Ok && res.value == true -> {
                val valueVal = res.value
            }
            res is Result.Ok && res.value == false -> {
                val valueVal1 = res.value
                return ExitCode.from(ERROR_EXIT_CODE)
            }
            else -> {
                val e1 = res.error
                println("error: io error when listing tests: $e")
                return ExitCode.from(ERROR_EXIT_CODE)
            }
        }
    }
    return ExitCode.SUCCESS
}

/**
 * A variant that takes the arguments from the command line.
 *
 * This is the entry point for the main function generated by `rustc - - test`
 * when panic=unwind.
 */
public fun testMainEnvArgs(tests: List<TestDescAndFn>): ExitCode {
    // This is supposed to be reasonably fast even in Miri. In particular, when invoked via `--exact
    // test`, we want the entire invocation to be`O(log n)` in the number of tests: never iterate
    // the entire test list (as that list could be big)!
    val args = Env.args().collect<Vec>()
    // Tests are sorted by name at compile time by mkTestsSlice.
    val tests1 = TestList.new(tests, TestListOrder.Sorted)
    return testMainInner(args, tests1, null)
}

/**
 * A variant that takes the arguments from the command line.
 *
 * Runs tests in panic=abort mode, which involves spawning subprocesses for
 * tests. If we are invoked as subprocess, this function does not return.
 *
 * This is the entry point for the main function generated by `rustc - - test`
 * when panic=abort.
 */
public fun testMainEnvArgsAbort(tests: List<TestDescAndFn>): ExitCode {
    val tests1 = tests
    // If we're being run in SpawnedSecondary mode, run the test here. runTest
    // will then exit the process.
    when (val matchVal = Env.variable(SECONDARY_TEST_INVOKER_VAR)) {
        is Ok -> {
            val name = matchVal.value
            // SAFETY: Technically, this is a racy access that we probably shouldn't do?
            // In practice, this is completely fine as long as the test harness is made of Rust,
            // as std will synchronize the racy accesses that occur when they happen through std.env.
            // Any unsoundness can only be exposed in practice if e.g. C code also takes an interest
            // in these variables.
            //
            // If we ever grow an actual story for libtest and start documenting custom harness reqs,
            // we should either fix this being racy or say "write it in Rust, please".
            run {
                Env.removeVar(SECONDARY_TEST_INVOKER_VAR)
            }
            // Convert benchmarks to tests if we're not benchmarking.
            var tests2 = tests1.iter().copied().cloned().collect<Vec>()
            if (Env.variable(SECONDARY_TEST_BENCH_BENCHMARKS_VAR).isOk()) {
                // SAFETY: Same as for SECONDARY_TEST_INVOKER_VAR
                run {
                    Env.removeVar(SECONDARY_TEST_BENCH_BENCHMARKS_VAR)
                }
            } else {
                tests2 = convertBenchmarksToTests(tests2)
            }
            val test = (tests2.intoIter().indexOf({ test -> test.desc.name.asSlice() == name }).takeIf { it >= 0 } ?: error("couldn't find a test with the provided name '$name'"))
            val desc = test.desc
            val testfn = test.testfn
            when (val matchVal1 = testfn.intoRunnable()) {
                is Runnable.Test -> {
                    val (runnableTest) = matchVal1
                    if (runnableTest.isDynamic()) {
                        error("only static tests are supported")
                    }
                    runTestInSpawnedSubprocess(desc, runnableTest)
                }
                is Runnable.Bench -> {
                    error("benchmarks should not be executed into child processes")
                }
            }
            // Unreachable
        }
        else -> {}
    }
    val args = Env.args().collect<Vec>()
    // Tests are sorted by name at compile time by mkTestsSlice.
    val tests3 = TestList.new(tests1, TestListOrder.Sorted)
    return testMainInner(args, tests3, Options.new().panicAbort(true))
}

/**
 * Public API used by rustdoc to display the `total` and `compilation` times in the expected
 * format.
 */
public fun printMergedDoctestsTimes(args: List<String>, totalTime: Double, compilationTime: Double) {
    val opts =
        when (val matchVal = parseOpts(args)) {
            is Result.Ok<*, *> -> {
                val o = (matchVal as Result.Ok<*, *>).value
                o
            }
            is Result.Err<*, *> -> {
                val msg = (matchVal as Result.Err<*, *>).error
                println("error: $msg")
                exit(ERROR_EXIT_CODE.into())
            }
            null -> { return }
        }
    val formatter = getFormatter(opts, 0)
    formatter.writeMergedDoctestsTimes(totalTime, compilationTime).unwrap()
}

/**
 * Invoked when unit tests terminate. Returns `Result.Err` if the test is
 * considered a failure. By default, invokes `report()` and checks for a `0`
 * result.
 */
public fun <T : Termination> assertTestResult(result: T): Result<Unit, String> {
    val code = result.report().toI32()
    return if (code == 0) {
        Result.Ok<Unit, String>(Unit)
    } else {
        Result.Err<Unit, String>("the test returned a termination value with a non-zero status code ($code) which indicates a failure")
    }
}

public fun runTests(opts: TestOpts, filteredTests: List<TestDescAndFn>, allTestsLen: Int, notifyAboutTestEvent: (TestEvent) -> Result<Unit>): Result<Unit> {
    var filteredTests1 = filteredTests
    open class RunningTest(
        public var joinHandle: JoinHandle<Unit>?,
    ) {
        public open fun join(completedTest: CompletedTest) {
            when (val matchVal = this.joinHandle) {
                null -> {}
                else -> {
                    val joinHandle = matchVal
                    when (val matchVal1 = joinHandle.join()) {
                        is Err -> {
                            if (completedTest.result != null) {
                                completedTest.result = TrFailedMsg("panicked after reporting success".toString())
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
    // Use a deterministic hasher
    open class TimeoutEntry(
        public var id: TestId,
        public var desc: TestDesc,
        public var timeout: Instant,
    )
    fun getTimedOutTests(runningTests: TestMap, timeoutQueue: VecDeque<TimeoutEntry, Global>): List<TestDesc> {
        val now = Instant.now()
        val timedOut = mutableListOf<TestDesc>()
        while (true) {
            val matchVal = timeoutQueue.front()
            if (matchVal == null) break
            val timeoutEntry = matchVal
            if (now < timeoutEntry.timeout) {
                break
            }
            val timeoutEntry1 = timeoutQueue.removeFirstOrNull()!!
            if (runningTests.containsKey(timeoutEntry1.id)) {
                timedOut.add(timeoutEntry1.desc)
            }
        }
        return timedOut
    }
    fun calcTimeout(timeoutQueue: VecDeque<TimeoutEntry, Global>): Duration? =
        timeoutQueue.front().map({ param0 ->
            val now = Instant.now()
            if (nextTimeout >= now) {
                nextTimeout - now
            } else {
                Duration.new(0, 0)
            }
        })
    val filtered = FilteredTests(tests = mutableListOf(), benches = mutableListOf(), nextId = 0)
    if (!opts.benchBenchmarks) {
        filteredTests1 = convertBenchmarksToTests(filteredTests1)
    }
    for (test in filteredTests1) {
        val desc = test.desc
        desc.name = desc.name.withPadding(test.testfn.padding())
        when (val matchVal = test.testfn) {
            is DynBenchFn -> {
                filtered.addBench(desc, test.testfn)
            }
            is StaticBenchFn -> {
                filtered.addBench(desc, test.testfn)
            }
            else -> {
                val testfn = matchVal
                filtered.addTest(desc, testfn)
            }
        }
    }
    val filteredOut = allTestsLen - filtered.totalLen()
    val event = TestEvent.TeFilteredOut(filteredOut)
    when (val tryResult = notifyAboutTestEvent(event)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
    val shuffleSeed = getShuffleSeed(opts)
    val event1 = TestEvent.TeFiltered(filtered.totalLen(), shuffleSeed)
    when (val tryResult = notifyAboutTestEvent(event1)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
    val concurrency = opts.testThreads.unwrapOrElse(getConcurrency)
    val remaining = filtered.tests
    if (shuffleSeed != null) {
        val shuffleSeed1 = shuffleSeed
        shuffleTests(shuffleSeed1, remaining)
    }
    // Store the tests in a VecDeque so we can efficiently remove the first element to run the
    // tests in the order they were passed (unless shuffled).
    val remaining1 = ArrayDeque(remaining)
    var pending = 0
    val (tx, rx) = channel<CompletedTest>()
    val runStrategy =
        if (opts.options.panicAbort && !opts.forceRunInProcess) {
            RunStrategy.SpawnPrimary
        } else {
            RunStrategy.InProcess
        }
    val runningTests: TestMap  = HashMap.default()
    val timeoutQueue: VecDeque<TimeoutEntry, Global>  = VecDeque.new()
    if (concurrency == 1) {
        while (!remaining1.isEmpty()) {
            val (id, test1) = remaining1.removeFirstOrNull()!!
            val event2 = TestEvent.TeWait(test1.desc.clone())
            when (val tryResult = notifyAboutTestEvent(event2)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            val joinHandle = runTest(opts, !opts.runTests, id, test1, runStrategy, tx.clone())
            // Wait for the test to complete.
            val completedTest = rx.recv().unwrap()
            RunningTest(joinHandle = joinHandle).join(completedTest)
            val failFast =
                when (val matchVal1 = completedTest.result) {
                    TrIgnored -> false
                    TrOk -> false
                    is TrBench -> false
                    TrFailed -> opts.failFast
                    is TrFailedMsg -> opts.failFast
                    TrTimedFail -> opts.failFast
                }
            val event3 = TestEvent.TeResult(completedTest)
            when (val tryResult = notifyAboutTestEvent(event3)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            if (failFast) {
                return Result.Ok(Unit)
            }
        }
    } else {
        while (pending > 0 || !remaining1.isEmpty()) {
            while (pending < concurrency && !remaining1.isEmpty()) {
                val (id1, test2) = remaining1.removeFirstOrNull()!!
                val timeout = getDefaultTestTimeout()
                val desc1 = test2.desc.clone()
                val event4 = TestEvent.TeWait(desc1.clone())
                when (val tryResult = notifyAboutTestEvent(event4)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
                // here no pad
                val joinHandle1 = runTest(opts, !opts.runTests, id1, test2, runStrategy, tx.clone())
                runningTests.insert(id1, RunningTest(joinHandle = joinHandle1))
                timeoutQueue.addLast(TimeoutEntry(id = id1, desc = desc1, timeout = timeout))
                pending += 1
            }
            var res: Any?
            while (true) {
                when (val matchVal2 = calcTimeout(timeoutQueue)) {
                    null -> {
                        res = rx.recv().mapErr({ RecvTimeoutError.Disconnected })
                        break
                    }
                    else -> {
                        val timeout1 = matchVal2
                        res = rx.recvTimeout(timeout1)
                        for (test3 in getTimedOutTests(runningTests, timeoutQueue)) {
                            val event5 = TestEvent.TeTimeout(test3)
                            when (val tryResult = notifyAboutTestEvent(event5)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
                        }
                        when {
                            res is Result.Err && res.error == RecvTimeoutError.Timeout -> {
                                val errorVal = res.error
                            }
                            else -> {
                                // We've got a result, stop the loop.
                                break
                            }
                        }
                    }
                }
            }
            val completedTest1 = res.unwrap()
            val runningTest = runningTests.remove(completedTest1.id).unwrap()
            runningTest.join(completedTest1)
            val failFast1 =
                when (val matchVal3 = completedTest1.result) {
                    TrIgnored -> false
                    TrOk -> false
                    is TrBench -> false
                    TrFailed -> opts.failFast
                    is TrFailedMsg -> opts.failFast
                    TrTimedFail -> opts.failFast
                }
            val event6 = TestEvent.TeResult(completedTest1)
            when (val tryResult = notifyAboutTestEvent(event6)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            pending -= 1
            if (failFast1) {
                // Prevent remaining test threads from panicking
                forget(rx)
                return Result.Ok(Unit)
            }
        }
    }
    if (opts.benchBenchmarks) {
        // All benchmarks run at the end, in serial.
        for ((id2, b) in filtered.benches) {
            val event7 = TestEvent.TeWait(b.desc.clone())
            when (val tryResult = notifyAboutTestEvent(event7)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
            val joinHandle2 = runTest(opts, false, id2, b, runStrategy, tx.clone())
            // Wait for the test to complete.
            val completedTest2 = rx.recv().unwrap()
            RunningTest(joinHandle = joinHandle2).join(completedTest2)
            val event8 = TestEvent.TeResult(completedTest2)
            when (val tryResult = notifyAboutTestEvent(event8)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err(tryResult.error) }
        }
    }
    return Result.Ok(Unit)
}

public fun filterTests(opts: TestOpts, tests: TestList): List<TestDescAndFn> {
    val tests1 = tests.tests
    val order = tests.order
    // Initial filtering: Remove tests that don't match the test filter.
    var filtered =
        if (opts.filters.isEmpty()) {
            tests1.iter().copied().cloned().collect<Vec>()
        } else if (opts.filterExact && order == TestListOrder.Sorted) {
            // Let's say that `f` is the number of filters and `n` is the number
            // of tests.
            //
            // The test array is sorted by name (guaranteed by the caller via
            // TestListOrder.Sorted), so use binary search for O(f log n)
            // exact-match lookups instead of an O(n) linear scan.
            //
            // This is important for Miri, where the interpreted execution makes
            // the linear scan very expensive.
            filterExactMatch(tests1, opts.filters)
        } else {
            tests1.iter().copied().filter({ test ->
                val testName = test.desc.name.asSlice()
                opts.filters.iter().any({ filter ->
                    if (opts.filterExact) {
                        testName == filter
                    } else {
                        testName.contains(filter)
                    }
                })
            }).cloned().collect<Vec>()
        }
    // Skip tests that match any of the skip filters
    //
    // After exact positive filtering above, the filtered set is small, so a
    // linear scan is acceptable even under Miri.
    if (!opts.skip.isEmpty()) {
        filtered.retain({ test ->
            val name = test.desc.name.asSlice()
            !opts.skip.iter().any({ sf ->
                if (opts.filterExact) {
                    name == sf
                } else {
                    name.contains(sf)
                }
            })
        })
    }
    // Excludes @shouldPanic tests
    if (opts.excludeShouldPanic) {
        filtered.retain({ test -> test.desc.shouldPanic == ShouldPanic.No })
    }
    // maybe unignore tests
    when (opts.runIgnored) {
        RunIgnored.Yes -> {
            filtered.iterMut().forEach({ test -> test.desc.ignore = false })
        }
        RunIgnored.Only -> {
            filtered.retain({ test -> test.desc.ignore })
            filtered.iterMut().forEach({ test -> test.desc.ignore = false })
        }
        RunIgnored.No -> {}
    }
    return filtered
}

/**
 * Extract tests whose names exactly match one of the given `filters`, using
 * binary search on the (assumed sorted) test list.
 */
public fun filterExactMatch(tests: List<TestDescAndFn>, filters: List<String>): List<TestDescAndFn> {
    // Binary search for each filter in the sorted test list.
    val indexes: List<Int>  = filters.mapNotNull({ f -> tests.binarySearchBy({ t -> t.desc.name.asSlice().cmp(f) }).ok() }).toMutableList()
    indexes.sortUnstable()
    indexes.dedup()
    // Extract matching tests.
    val result = ArrayList<TestDescAndFn>(indexes.size)
    for (idx in indexes) {
        result.add(tests[idx].clone())
    }
    return result
}

public fun convertBenchmarksToTests(tests: List<TestDescAndFn>): List<TestDescAndFn> {
    // convert benchmarks to tests, if we're not benchmarking them
    return tests.map({ x ->
        val testfn =
            when (val matchVal = x.testfn) {
                is DynBenchFn -> {
                    val (benchfn) = matchVal
                    DynBenchAsTestFn(benchfn)
                }
                is StaticBenchFn -> {
                    val (benchfn1) = matchVal
                    StaticBenchAsTestFn(benchfn1)
                }
                else -> {
                    val f = matchVal
                    f
                }
            }
        TestDescAndFn(desc = x.desc, testfn = testfn)
    }).toMutableList()
}

public fun runTest(opts: TestOpts, forceIgnore: Boolean, id: TestId, test: TestDescAndFn, strategy: RunStrategy, monitorCh: Sender<CompletedTest>): JoinHandle<Unit>? {
    val desc = test.desc
    val testfn = test.testfn
    // Emscripten can catch panics, and test for `panic = unwind` on other wasm
    // targets to see if they can catch panics.
    val ignoreBecauseNoProcessSupport = desc.shouldPanic != ShouldPanic.No && (false || false) && !(false || false)
    if (forceIgnore || desc.ignore || ignoreBecauseNoProcessSupport) {
        val message = CompletedTest.new(id, desc, TrIgnored, null, mutableListOf())
        monitorCh.send(message).unwrap()
        return null
    }
    return when (val matchVal = testfn.intoRunnable()) {
        is Runnable.Test -> {
            val (runnableTest) = matchVal
            if (runnableTest.isDynamic()) {
                when (strategy) {
                    RunStrategy.InProcess -> Unit
                    else -> error("Cannot run dynamic test fn out-of-process")
                }
            }
            val name = desc.name.clone()
            val nocapture = opts.nocapture
            val timeOptions = opts.timeOptions
            val benchBenchmarks = opts.benchBenchmarks
            val runtest =
                { when (strategy) {
                    RunStrategy.InProcess -> runTestInProcess(id, desc, nocapture, timeOptions != null, runnableTest, monitorCh, timeOptions)
                    RunStrategy.SpawnPrimary -> spawnTestSubprocess(id, desc, nocapture, timeOptions != null, monitorCh, timeOptions, benchBenchmarks)
                } }
            // If the platform is single-threaded we're just going to run
            // the test synchronously, regardless of the concurrency
            // level.
            val supportsThreads = !false && !false && !false && !false
            if (supportsThreads) {
                val cfg = Builder.new().name(name.asSlice().toOwned())
                val runtest1 = Mutex.new(runtest)
                val runtest2 = runtest1.clone()
                run {
                    val matchVal1 = cfg.spawn({ runtest2.lock().unwrap().take()!!() })
                    when {
                        matchVal1 is Result.Ok -> {
                            val handle = matchVal1.value
                            handle
                        }
                        matchVal1 is Result.Err && (matchVal1 as Result.Err).error.kind() == ErrorKind.WouldBlock -> {
                            val e = matchVal1.error
                            // `ErrorKind.WouldBlock` means hitting the thread limit on some
                            // platforms, so run the test synchronously here instead.
                            Arc.getMut(runtest1).unwrap().getMut()!!.take()!!()
                            null
                        }
                        else -> {
                            val e1 = matchVal1.error
                            error("failed to spawn thread to run test: $e")
                        }
                    }
                }
            } else {
                runtest()
                null
            }
        }
        is Runnable.Bench -> {
            val (runnableBench) = matchVal
            // Benchmarks aren't expected to panic, so we run them all in-process.
            runnableBench.run(id, desc, monitorCh, opts.nocapture)
            null
        }
    }
}

/**
 * Fixed frame used to clean the backtrace with `RUST_BACKTRACE = 1`.
 */
public fun <T> rustBeginShortBacktrace(f: () -> T): T {
    val result = f()
    // prevent this frame from being tail-call optimised away
    return blackBox(result)
}

public fun runTestInProcess(id: TestId, desc: TestDesc, nocapture: Boolean, reportTime: Boolean, runnableTest: RunnableTest, monitorCh: Sender<CompletedTest>, timeOpts: TestTimeOptions?) {
    // Buffer for capturing standard I/O
    val data = Mutex.new(mutableListOf())
    if (!nocapture) {
        setOutputCapture(data.clone())
    }
    val start = reportTime.then(Instant::now)
    val result = foldErr(catchUnwind(AssertUnwindSafe({ runnableTest.run() })))
    val execTime =
        start.map({ start1 ->
            val duration = start1.elapsed()
            TestExecTime(duration)
        })
    setOutputCapture(null)
    // Determine whether the test passed or failed, by comparing its panic
    // payload (if any) with its `ShouldPanic` value, and by checking for
    // fatal timeout.
    val testResult = calcResult(desc, result.err().asDeref(), timeOpts, execTime.asRef())
    val stdout = data.lock().unwrapOrElse({ e -> e.intoInner() }).toVec()
    val message = CompletedTest.new(id, desc, testResult, execTime, stdout)
    monitorCh.send(message).unwrap()
}

public fun <T, E> foldErr(result: Result<Result<T, E>, RustAny>): Result<T, RustAny> =
    when {
        result is Result.Ok && result.value is Result.Err -> {
            val valueVal = result.value
            val e = (valueVal as Result.Err).error
            Result.Err<T, RustAny>(e)
        }
        result is Result.Ok && result.value is Result.Ok -> {
            val valueVal1 = result.value
            val v = (valueVal1 as Result.Ok).value
            Result.Ok<T, RustAny>(v)
        }
        else -> {
            val e1 = result.error
            Result.Err<T, RustAny>(e1)
        }
    }

public fun spawnTestSubprocess(id: TestId, desc: TestDesc, nocapture: Boolean, reportTime: Boolean, monitorCh: Sender<CompletedTest>, timeOpts: TestTimeOptions?, benchBenchmarks: Boolean) {
    val (result, testOutput, execTime) =
        (closure@{
            val args = Env.args().collect<Vec>()
            val currentExe = args[0]
            var command = Command.new(currentExe)
            command.env(SECONDARY_TEST_INVOKER_VAR, desc.name.asSlice())
            if (benchBenchmarks) {
                command.env(SECONDARY_TEST_BENCH_BENCHMARKS_VAR, "1")
            }
            if (nocapture) {
                command.stdout(Stdio.inherit())
                command.stderr(Stdio.inherit())
            }
            val start = reportTime.then(Instant::now)
            val output =
                run {
                    val matchVal = command.output()
                    when (matchVal) { is Result.Ok -> {
                        val out = matchVal.value
                        out
                    }; is Result.Err -> {
                        val e = matchVal.error
                        val err = "Failed to spawn ${args[0]} as child for test: $e"
                        return@closure Triple(TrFailed, err.intoBytes(), null)
                    } }
                }
            val execTime =
                start.map({ start1 ->
                    val duration = start1.elapsed()
                    TestExecTime(duration)
                })
            val stdout = output.stdout
            val stderr = output.stderr
            val status = output.status
            var testOutput = stdout
            writeStderrDelimiter(testOutput, desc.name)
            testOutput.extendFromSlice(stderr)
            val result = getResultFromExitCode(desc, status, timeOpts, execTime.asRef())
            Triple(result, testOutput, execTime)
        })()
    val message = CompletedTest.new(id, desc, result, execTime, testOutput)
    monitorCh.send(message).unwrap()
}

public fun runTestInSpawnedSubprocess(desc: TestDesc, runnableTest: RunnableTest): Nothing {
    val builtinPanicHook = panic.takeHook()
    val recordResult =
        { panicInfo: PanicHookInfo? ->
            val testResult = calcResult(desc, panicInfo.map({ info -> info.payload() }), null, null)
            // We don't support serializing TrFailedMsg, so just
            // print the message out to stderr.
            if (testResult is TrFailedMsg) {
                val (msg) = testResult
                println("$msg")
            }
            if (panicInfo != null) {
                val info = panicInfo
                builtinPanicHook(info)
            }
            if (testResult != null) {
                exit(TR_OK)
            } else {
                abort()
            }
        }
    val recordResult2 = recordResult.clone()
    panic.setHook({ info -> recordResult2(info) })
    when (val matchVal = runnableTest.run()) {
        is Err -> {
            val message = matchVal.error
            error("$message")
        }
        else -> {}
    }
    recordResult(null)
    error("panic=abort callback should have exited the process")
}
