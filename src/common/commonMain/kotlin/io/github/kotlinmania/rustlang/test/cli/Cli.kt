// port-lint: source cli.rs
package io.github.kotlinmania.rustlang.test.cli

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.getopts.Matches
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.test.ColorConfig
import io.github.kotlinmania.rustlang.test.Options
import io.github.kotlinmania.rustlang.test.OutputFormat
import io.github.kotlinmania.rustlang.test.RunIgnored
import io.github.kotlinmania.rustlang.test.TestTimeOptions

// Module converting command-line arguments into test configuration.

public open class TestOpts(
    public var list: Boolean,
    public var filters: List<String>,
    public var filterExact: Boolean,
    public var forceRunInProcess: Boolean,
    public var excludeShouldPanic: Boolean,
    public var runIgnored: RunIgnored,
    public var runTests: Boolean,
    public var benchBenchmarks: Boolean,
    public var logfile: PathBuf?,
    public var nocapture: Boolean,
    public var color: ColorConfig,
    public var format: OutputFormat,
    public var shuffle: Boolean,
    public var shuffleSeed: ULong?,
    public var testThreads: Int?,
    public var skip: List<String>,
    public var timeOptions: TestTimeOptions?,
    /**
     * Stop at first failing test.
     * May run a few more tests due to threading, but will
     * abort as soon as possible.
     */
    public var failFast: Boolean,
    public var options: io.github.kotlinmania.rustlang.test.options.Options,
) {
    public open fun useColor(): Boolean =
        when (this.color) {
            ColorConfig.AutoColor -> !this.nocapture && stdout().isTerminal()
            ColorConfig.AlwaysColor -> true
            ColorConfig.NeverColor -> false
        }
}

/**
 * Result of parsing the options.
 */
public typealias OptRes = Result<TestOpts, String>

/**
 * Result of parsing the option part.
 */
public typealias OptPartRes<T> = Result<T, String>

public fun optgroups(): Options {
    val opts = Options.new()
    opts.optflag("", "include-ignored", "Run ignored and not ignored tests").optflag("", "ignored", "Run only ignored tests").optflag("", "force-run-in-process", "Forces tests to run in-process when panic=abort").optflag("", "exclude-should-panic", "Excludes tests marked as should_panic").optflag("", "test", "Run tests and not benchmarks").optflag("", "bench", "Run benchmarks instead of tests").optflag("", "list", "List all tests and benchmarks").optflag("", "fail-fast", "Don't start new tests after the first failure").optflag("h", "help", "Display this message").optopt("", "logfile", "Write logs to the specified file (deprecated)", "PATH").optflag("", "no-capture", "don't capture stdout/stderr of each task, allow printing directly").optmulti("", "test-threads", "Number of threads used for running tests in parallel", "n_threads").optmulti("", "skip", "Skip tests whose names contain FILTER (this flag can be used multiple times)", "FILTER").optflag("q", "quiet", "Display one character per test instead of one line. Alias to --format=terse").optflag("", "exact", "Exactly match filters rather than by substring").optmulti("", "color", "Configure coloring of output:\n            auto   = colorize if stdout is a tty and tests are run on serially (default);\n            always = always colorize output;\n            never  = never colorize output;", "auto|always|never").optopt("", "format", "Configure formatting of output:\n            pretty = Print verbose output;\n            terse  = Display one character per test;\n            json   = Output a json document;\n            junit  = Output a JUnit document", "pretty|terse|json|junit").optflag("", "show-output", "Show captured stdout of successful tests").optopt("Z", "", "Enable nightly-only flags:\n            unstable-options = Allow use of experimental features", "unstable-options").optflag("", "report-time", "Show execution time of each test.\n\n            Threshold values for colorized output can be configured via\n            `RUST_TEST_TIME_UNIT`, `RUST_TEST_TIME_INTEGRATION` and\n            `RUST_TEST_TIME_DOCTEST` environment variables.\n\n            Expected format of environment variable is `VARIABLE=WARN_TIME,CRITICAL_TIME`.\n            Durations must be specified in milliseconds, e.g. `500,2000` means that the warn time\n            is 0.5 seconds, and the critical time is 2 seconds.\n\n            Not available for --format=terse").optflag("", "ensure-time", "Treat excess of the test execution time limit as error.\n\n            Threshold values for this option can be configured via\n            `RUST_TEST_TIME_UNIT`, `RUST_TEST_TIME_INTEGRATION` and\n            `RUST_TEST_TIME_DOCTEST` environment variables.\n\n            Expected format of environment variable is `VARIABLE=WARN_TIME,CRITICAL_TIME`.\n\n            `CRITICAL_TIME` here means the limit that should not be exceeded by test.\n            ").optflag("", "shuffle", "Run tests in random order").optopt("", "shuffle-seed", "Run tests in random order; seed the random number generator with SEED", "SEED")
    return opts
}

public fun usage(binary: String, options: Options) {
    val message = "Usage: $binary [OPTIONS] [FILTERS...]"
    println("${options.usage(message)}\n\nThe FILTER string is tested against the name of all tests, and only those\ntests whose names contain the filter are run. Multiple filter strings may\nbe passed, which will run all tests matching any of the filters.\n\nBy default, all tests are run in parallel. This can be altered with the\n--test-threads flag when running tests (set it to 1).\n\nBy default, the tests are run in alphabetical order. Use --shuffle to run\nthe tests in random order. Pass the generated \"shuffle seed\" to\n--shuffle-seed to run the tests in the same order again. Note that\n--shuffle and --shuffle-seed do not affect whether the tests are run in\nparallel.\n\nAll tests have their standard output and standard error captured by default.\nThis can be overridden with the --no-capture flag to a value other than \"0\".\nLogging is not captured by default.\n\nTest Attributes:\n\n    `#[test]`        - Indicates a function is a test to be run. This function\n                       takes no arguments.\n    `#[bench]`       - Indicates a function is a benchmark to be run. This\n                       function takes one argument (test::Bencher).\n    `#[should_panic]` - This function (also labeled with `#[test]`) will only pass if\n                        the code causes a panic (an assertion failure or panic!)\n                        A message may be provided, which the failure string must\n                        contain: #[should_panic(expected = \"foo\")].\n    `#[ignore]`       - When applied to a function which is already attributed as a\n                        test, then the test runner will ignore these tests during\n                        normal test runs. Running with --ignored or --include-ignored will run\n                        these tests.")
}

/**
 * Parses command line arguments into test options.
 * Returns `None` if help was requested (since we only show help message and don't run tests),
 * returns `Some(Err(..))` if provided arguments are incorrect,
 * otherwise creates a `TestOpts` object and returns it.
 */
public fun parseOpts(args: List<String>): OptRes? {
    // Parse matches.
    val opts = optgroups()
    // Flags hidden from `usage`
    opts.optflag("", "nocapture", "Deprecated, use `--no-capture`")
    val binary = (args.first()?.let({ c -> c }) ?: "...")
    val args1 = (args.get(1 until Int.MAX_VALUE) ?: args)
    val matches =
        run {
            val matchVal = opts.parse(args1)
            when (matchVal) { is Result.Ok<*, *> -> {
                val m = matchVal.okValue()
                m
            }; is Result.Err<*, *> -> {
                val f = matchVal.errValue()
                return Result.Err(f.toString())
            } }
        }
    // Check if help was requested.
    if (matches.optPresent("h")) {
        // Show help and do nothing more.
        usage(binary, optgroups())
        return null
    }
    // Actually parse the opts.
    val optsResult = parseOptsImpl(matches)
    return optsResult
}

// Implementation of `parseOpts` that doesn't care about help message
// and returns a `Result`.
public fun parseOptsImpl(matches: Matches): OptRes {
    val allowUnstable = when (val tryResult = getAllowUnstable(matches)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    // Unstable flags
    val forceRunInProcess = run {
        val opt = matches.optPresent("force-run-in-process")
        if (!allowUnstable && opt) {
            return Result.Err("The \"" + ("force-run-in-process").toString() + "\" flag is only accepted on the nightly compiler with -Z unstable-options")
        }
        opt
    }
    val excludeShouldPanic = run {
        val opt1 = matches.optPresent("exclude-should-panic")
        if (!allowUnstable && opt1) {
            return Result.Err("The \"" + ("exclude-should-panic").toString() + "\" flag is only accepted on the nightly compiler with -Z unstable-options")
        }
        opt1
    }
    val failFast = run {
        val opt2 = matches.optPresent("fail-fast")
        if (!allowUnstable && opt2) {
            return Result.Err("The \"" + ("fail-fast").toString() + "\" flag is only accepted on the nightly compiler with -Z unstable-options")
        }
        opt2
    }
    val timeOptions = when (val tryResult = getTimeOptions(matches, allowUnstable)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val shuffle = when (val tryResult = getShuffle(matches, allowUnstable)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val shuffleSeed = when (val tryResult = getShuffleSeed(matches, allowUnstable)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val includeIgnored = matches.optPresent("include-ignored")
    val quiet = matches.optPresent("quiet")
    val exact = matches.optPresent("exact")
    val list = matches.optPresent("list")
    val skip = matches.optStrs("skip")
    val benchBenchmarks = matches.optPresent("bench")
    val runTests = !benchBenchmarks || matches.optPresent("test")
    val logfile = when (val tryResult = getLogFile(matches)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val runIgnored = when (val tryResult = getRunIgnored(matches, includeIgnored)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val filters = matches.free.clone()
    val nocapture = when (val tryResult = getNocapture(matches)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val testThreads = when (val tryResult = getTestThreads(matches)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val color = when (val tryResult = getColorConfig(matches)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val format = when (val tryResult = getFormat(matches, quiet, allowUnstable)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err(tryResult.errValue()); else -> error("Unexpected Result variant: $tryResult") }
    val options = Options.new().displayOutput(matches.optPresent("show-output"))
    if (logfile != null) {
        stderr().append("warning: `--logfile` is deprecated")
    }
    val testOpts = TestOpts(list = list, filters = filters, filterExact = exact, forceRunInProcess = forceRunInProcess, excludeShouldPanic = excludeShouldPanic, runIgnored = runIgnored, runTests = runTests, benchBenchmarks = benchBenchmarks, logfile = logfile, nocapture = nocapture, color = color, format = format, shuffle = shuffle, shuffleSeed = shuffleSeed, testThreads = testThreads, skip = skip, timeOptions = timeOptions, options = options, failFast = failFast)
    return Result.Ok(testOpts)
}

public fun isNightly(): Boolean {
    // Whether the current rustc version should allow unstable features
    val enableUnstableFeatures = false
    // The runtime override for unstable features
    val bootstrap = Env.variable("RUSTC_BOOTSTRAP").isOk()
    return bootstrap || enableUnstableFeatures
}

// Gets the CLI options associated with `report - time` feature.
public fun getTimeOptions(matches: Matches, allowUnstable: Boolean): OptPartRes<TestTimeOptions?> {
    val reportTime = run {
        val opt = matches.optPresent("report-time")
        if (!allowUnstable && opt) {
            return Result.Err("The \"" + ("report-time").toString() + "\" flag is only accepted on the nightly compiler with -Z unstable-options")
        }
        opt
    }
    val ensureTestTime = run {
        val opt1 = matches.optPresent("ensure-time")
        if (!allowUnstable && opt1) {
            return Result.Err("The \"" + ("ensure-time").toString() + "\" flag is only accepted on the nightly compiler with -Z unstable-options")
        }
        opt1
    }
    // If `ensure - test - time` option is provided, time output is enforced,
    // so user won't be confused if any of tests will silently fail.
    val options =
        if (reportTime || ensureTestTime) {
            TestTimeOptions.newFromEnv(ensureTestTime)
        } else {
            null
        }
    return Result.Ok(options)
}

public fun getShuffle(matches: Matches, allowUnstable: Boolean): OptPartRes<Boolean> {
    var shuffle = run {
        val opt = matches.optPresent("shuffle")
        if (!allowUnstable && opt) {
            return Result.Err("The \"" + ("shuffle").toString() + "\" flag is only accepted on the nightly compiler with -Z unstable-options")
        }
        opt
    }
    if (!shuffle && allowUnstable) {
        shuffle = run {
            val matchVal = Env.variable("RUST_TEST_SHUFFLE")
            when (matchVal) { is Result.Ok<*, *> -> {
                val value = matchVal.okValue()
                value != "0"
            }; is Result.Err<*, *> -> {
                false
            } }
        }
    }
    return Result.Ok(shuffle)
}

public fun getShuffleSeed(matches: Matches, allowUnstable: Boolean): OptPartRes<ULong?> {
    var shuffleSeed =
        when (val matchVal = run {
            val opt = matches.optStr("shuffle-seed")
            if (!allowUnstable && opt != null) {
                return Result.Err("The \"" + ("shuffle-seed").toString() + "\" option is only accepted on the nightly compiler with -Z unstable-options")
            }
            opt
        }) {
            null -> null
            else -> {
                val nStr = matchVal
                run {
                    val matchVal1 = (nStr.toULongOrNull()?.let { Result.Ok<ULong, String>(it) } ?: Result.Err<ULong, String>("failed to parse ULong"))
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        val n = matchVal1.okValue()
                        n
                    }; is Result.Err<*, *> -> {
                        val e = matchVal1.errValue()
                        return Result.Err("argument for --shuffle-seed must be a number (error: $e)")
                    } }
                }
            }
        }
    if (shuffleSeed == null && allowUnstable) {
        shuffleSeed = run {
            val matchVal2 = Env.variable("RUST_TEST_SHUFFLE_SEED")
            when (matchVal2) { is Result.Ok<*, *> -> {
                val value = matchVal2.okValue()
                run {
                    val matchVal3 = (value.toULongOrNull()?.let { Result.Ok<ULong, String>(it) } ?: Result.Err<ULong, String>("failed to parse ULong"))
                    when (matchVal3) { is Result.Ok<*, *> -> {
                        val n1 = matchVal3.okValue()
                        n1
                    }; is Result.Err<*, *> -> {
                        error("RUST_TEST_SHUFFLE_SEED is `$value`, should be a number.")
                    } }
                }
            }; is Result.Err<*, *> -> {
                null
            } }
        }
    }
    return Result.Ok(shuffleSeed)
}

public fun getTestThreads(matches: Matches): OptPartRes<Int?> {
    var lastTestThreads = null
    for (nStr in matches.optStrs("test-threads")) {
        lastTestThreads = run {
            val matchVal = (nStr.toIntOrNull()?.let { Result.Ok<Int, String>(it) } ?: Result.Err<Int, String>("failed to parse Int"))
            when {
                matchVal is Result.Ok<*, *> && matchVal.okValue() == 0 -> {
                    val okValueVal = matchVal.okValue()
                    return Result.Err("argument for --test-threads must not be 0".toString())
                }
                matchVal is Result.Ok<*, *> -> {
                    val n = matchVal.okValue()
                    n
                }
                else -> {
                    val e = matchVal.errValue()
                    return Result.Err("argument for --test-threads must be a number > 0 (error: $e)")
                }
            }
        }
    }
    return Result.Ok(lastTestThreads)
}

public fun getFormat(matches: Matches, quiet: Boolean, allowUnstable: Boolean): OptPartRes<OutputFormat> {
    val format =
        run {
            val matchVal = matches.optStr("format").asDeref()
            when {
                matchVal == null && quiet -> OutputFormat.Terse
                matchVal != null && matchVal == "pretty" -> OutputFormat.Pretty
                matchVal == null -> OutputFormat.Pretty
                matchVal != null && matchVal == "terse" -> OutputFormat.Terse
                matchVal != null && matchVal == "json" -> {
                    if (!allowUnstable) {
                        return Result.Err("The \"json\" format is only accepted on the nightly compiler with -Z unstable-options".into())
                    }
                    OutputFormat.Json
                }
                matchVal != null && matchVal == "junit" -> {
                    if (!allowUnstable) {
                        return Result.Err("The \"junit\" format is only accepted on the nightly compiler with -Z unstable-options".into())
                    }
                    OutputFormat.Junit
                }
                else -> {
                    val v = matchVal
                    return Result.Err("argument for --format must be pretty, terse, json or junit (was $v)")
                }
            }
        }
    return Result.Ok(format)
}

public fun getColorConfig(matches: Matches): OptPartRes<ColorConfig> {
    var lastColor = ColorConfig.AutoColor
    for (color in matches.optStrs("color")) {
        lastColor = when (color) {
            "auto" -> ColorConfig.AutoColor
            "always" -> ColorConfig.AlwaysColor
            "never" -> ColorConfig.NeverColor
            else -> {
                val v = color
                return Result.Err("argument for --color must be auto, always, or never (was $v)")
            }
        }
    }
    return Result.Ok(lastColor)
}

public fun getNocapture(matches: Matches): OptPartRes<Boolean> {
    var nocapture = matches.optPresent("nocapture") || matches.optPresent("no-capture")
    if (!nocapture) {
        nocapture = run {
            val matchVal = Env.variable("RUST_TEST_NOCAPTURE")
            when (matchVal) { is Result.Ok<*, *> -> {
                val value = matchVal.okValue()
                value != "0"
            }; is Result.Err<*, *> -> {
                false
            } }
        }
    }
    return Result.Ok(nocapture)
}

public fun getRunIgnored(matches: Matches, includeIgnored: Boolean): OptPartRes<RunIgnored> {
    val runIgnored =
        run {
            val matchVal = Pair(includeIgnored, matches.optPresent("ignored"))
            when {
                matchVal.first == true && matchVal.second == true -> {
                    return Result.Err("the options --include-ignored and --ignored are mutually exclusive".into())
                }
                matchVal.first == true && matchVal.second == false -> RunIgnored.Yes
                matchVal.first == false && matchVal.second == true -> RunIgnored.Only
                else -> RunIgnored.No
            }
        }
    return Result.Ok(runIgnored)
}

public fun getAllowUnstable(matches: Matches): OptPartRes<Boolean> {
    var allowUnstable = false
    when (val matchVal = matches.optStr("Z")) {
        null -> {}
        else -> {
            val opt = matchVal
            if (!isNightly()) {
                return Result.Err("the option `Z` is only accepted on the nightly compiler".into())
            }
            when (opt) {
                "unstable-options" -> {
                    allowUnstable = true
                }
                else -> {
                    return Result.Err("Unrecognized option to `Z`".into())
                }
            }
        }
    }
    return Result.Ok(allowUnstable)
}

public fun getLogFile(matches: Matches): OptPartRes<PathBuf?> {
    val logfile = matches.optStr("logfile").map({ s -> PathBuf.from(s) })
    return Result.Ok(logfile)
}
