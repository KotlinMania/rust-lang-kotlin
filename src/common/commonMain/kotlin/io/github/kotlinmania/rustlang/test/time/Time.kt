// port-lint: source time.rs
package io.github.kotlinmania.rustlang.test.time

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.time.Instant
import io.github.kotlinmania.rustlang.test.TestDesc
import io.github.kotlinmania.rustlang.test.TestType

// Module `time` contains everything related to the time measurement of unit tests
// execution.
// The purposes of this module:
// - Check whether test is timed out.
// - Provide helpers for `report - time` and `measure - time` options.
// - Provide newtypes for executions times.

public val TEST_WARN_TIMEOUT_S: ULong = 60uL

/**
 * Environment variable for overriding default threshold for unit-tests.
 */
public val UNIT_ENV_NAME: String = "RUST_TEST_TIME_UNIT"

// Unit tests are supposed to be really quick.
public val UNIT_WARN: Duration = Duration.fromMillis(50)

public val UNIT_CRITICAL: Duration = Duration.fromMillis(100)

/**
 * Environment variable for overriding default threshold for unit-tests.
 */
public val INTEGRATION_ENV_NAME: String = "RUST_TEST_TIME_INTEGRATION"

// Integration tests may have a lot of work, so they can take longer to execute.
public val INTEGRATION_WARN: Duration = Duration.fromMillis(500)

public val INTEGRATION_CRITICAL: Duration = Duration.fromMillis(1000)

/**
 * Environment variable for overriding default threshold for unit-tests.
 */
public val DOCTEST_ENV_NAME: String = "RUST_TEST_TIME_DOCTEST"

// Doctests are similar to integration tests, because they can include a lot of
// initialization code.
public val DOCTEST_WARN: Duration = INTEGRATION_WARN

public val DOCTEST_CRITICAL: Duration = INTEGRATION_CRITICAL

// Do not suppose anything about unknown tests, base limits on the
// `TEST_WARN_TIMEOUT_S` constant.
public val UNKNOWN_WARN: Duration = Duration.fromSecs(TEST_WARN_TIMEOUT_S)

public val UNKNOWN_CRITICAL: Duration = Duration.fromSecs(TEST_WARN_TIMEOUT_S * 2uL)

/**
 * Returns an `Instance` object denoting when the test should be considered
 * timed out.
 */
public fun getDefaultTestTimeout(): Instant = Instant.now() + Duration.fromSecs(TEST_WARN_TIMEOUT_S)

/**
 * The measured execution time of a unit test.
 */
public open class TestExecTime(
    public var value: Duration,
) {
    public var field0: Duration
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): TestExecTime = TestExecTime(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("${this.value.asSecsF64()}s"); Result.Ok<Unit, Error>(Unit) }
}

/**
 * The measured execution time of the whole test suite.
 */
public open class TestSuiteExecTime(
    public var value: Duration,
) {
    public var field0: Duration
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): TestSuiteExecTime = TestSuiteExecTime(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("${this.value.asSecsF64()}s"); Result.Ok<Unit, Error>(Unit) }
}

/**
 * Structure denoting time limits for test execution.
 */
public open class TimeThreshold(
    public var warn: Duration,
    public var critical: Duration,
) {
    public open fun clone(): TimeThreshold = TimeThreshold(warn, critical)

    public companion object {
        /**
         * Creates a new `TimeThreshold` instance with provided durations.
         */
        public fun new(warn: Duration, critical: Duration): TimeThreshold = TimeThreshold(warn = warn, critical = critical)

        /**
         * Attempts to create a `TimeThreshold` instance with values obtained
         * from the environment variable, and returns `None` if the variable
         * is not set.
         * Environment variable format is expected to match `\d+,\d+`.
         *
         * # Panics
         *
         * Panics if variable with provided name is set but contains inappropriate
         * value.
         */
        public fun fromEnvVar(envVarName: String): TimeThreshold? {
            val durationsStr = (Env.variable(envVarName).ok() ?: return null)
            val (warnStr, criticalStr) = (durationsStr.let { s -> val idx = s.indexOf(','); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } ?: error("Duration variable $envVarName expected to have 2 numbers separated by comma, but got $durationsStr"))
            val parseU64 =
                { v ->
                    ULong.fromStr(v).unwrapOrElse({
                        error("Duration value in variable $envVarName is expected to be a number, but got $v")
                    })
                }
            val warn = parseU64(warnStr)
            val critical = parseU64(criticalStr)
            if (warn > critical) {
                error("Test execution warn time should be less or equal to the critical time")
            }
            return TimeThreshold.new(Duration.fromMillis(warn), Duration.fromMillis(critical))
        }
    }
}

/**
 * Structure with parameters for calculating test execution time.
 */
public open class TestTimeOptions(
    /**
     * Denotes if the test critical execution time limit excess should be considered
     * a test failure.
     */
    public var errorOnExcess: Boolean,
    public var unitThreshold: TimeThreshold,
    public var integrationThreshold: TimeThreshold,
    public var doctestThreshold: TimeThreshold,
) {
    public open fun clone(): TestTimeOptions = TestTimeOptions(errorOnExcess, unitThreshold, integrationThreshold, doctestThreshold)

    public open fun isWarn(test: TestDesc, execTime: TestExecTime): Boolean = execTime.value >= this.warnTime(test)

    public open fun isCritical(test: TestDesc, execTime: TestExecTime): Boolean = execTime.value >= this.criticalTime(test)

    public open fun warnTime(test: TestDesc): Duration =
        when (test.testType) {
            TestType.UnitTest -> this.unitThreshold.warn
            TestType.IntegrationTest -> this.integrationThreshold.warn
            TestType.DocTest -> this.doctestThreshold.warn
            TestType.Unknown -> UNKNOWN_WARN
        }

    public open fun criticalTime(test: TestDesc): Duration =
        when (test.testType) {
            TestType.UnitTest -> this.unitThreshold.critical
            TestType.IntegrationTest -> this.integrationThreshold.critical
            TestType.DocTest -> this.doctestThreshold.critical
            TestType.Unknown -> UNKNOWN_CRITICAL
        }

    public companion object {
        public fun newFromEnv(errorOnExcess: Boolean): TestTimeOptions {
            val unitThreshold = (TimeThreshold.fromEnvVar(UNIT_ENV_NAME) ?: TestTimeOptions.defaultUnit())
            val integrationThreshold = (TimeThreshold.fromEnvVar(INTEGRATION_ENV_NAME) ?: TestTimeOptions.defaultIntegration())
            val doctestThreshold = (TimeThreshold.fromEnvVar(DOCTEST_ENV_NAME) ?: TestTimeOptions.defaultDoctest())
            return TestTimeOptions(errorOnExcess = errorOnExcess, unitThreshold = unitThreshold, integrationThreshold = integrationThreshold, doctestThreshold = doctestThreshold)
        }

        public fun defaultUnit(): TimeThreshold = TimeThreshold.new(UNIT_WARN, UNIT_CRITICAL)

        public fun defaultIntegration(): TimeThreshold = TimeThreshold.new(INTEGRATION_WARN, INTEGRATION_CRITICAL)

        public fun defaultDoctest(): TimeThreshold = TimeThreshold.new(DOCTEST_WARN, DOCTEST_CRITICAL)
    }
}
