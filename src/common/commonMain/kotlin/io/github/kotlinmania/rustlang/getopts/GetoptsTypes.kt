package io.github.kotlinmania.rustlang.getopts

import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.getopts.Fail.*
import io.github.kotlinmania.rustlang.getopts.HasArg.*
import io.github.kotlinmania.rustlang.getopts.Name.*
import io.github.kotlinmania.rustlang.getopts.Occur.*
import io.github.kotlinmania.rustlang.getopts.Optval.*
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

/**
 * What parsing style to use when parsing arguments.
 */
public sealed class ParsingStyle {
    /**
     * Flags and "free" arguments can be freely inter-mixed.
     */
    public data object FloatingFrees : ParsingStyle()
    /**
     * As soon as a "free" argument (i.e. non-flag) is encountered, stop
     * considering any remaining arguments as flags.
     */
    public data object StopAtFirstFree : ParsingStyle()

    public fun rustEquals(other: ParsingStyle): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        FloatingFrees -> 0
        StopAtFirstFree -> 1
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

/**
 * Name of an option. Either a string or a single char.
 */
public sealed class Name {
    /**
     * A string representing the long name of an option.
     * For example: "help"
     */
    public data class Long(public var value: String) : Name()
    /**
     * A char representing the short name of an option.
     * For example: `h`
     */
    public data class Short(public var value: Char) : Name()

    public fun rustEquals(other: Name): Boolean = this == other

    public override fun toString(): String =
        when (this) {
            is Short -> {
                val (ch) = this as Short
                ch.toString()
            }
            is Long -> {
                val (s) = this as Long
                s.toString()
            }
        }

    public companion object {
        public fun fromStr(nm: String): Name =
            if (nm.length == 1) {
                Short((nm.encodeToByteArray().map { it.toUByte() }[0]).toInt().toChar())
            } else {
                Long(nm.toString())
            }
    }
}

/**
 * Describes whether an option has an argument.
 */
public sealed class HasArg {
    /**
     * The option requires an argument.
     */
    public data object Yes : HasArg()
    /**
     * The option takes no argument.
     */
    public data object No : HasArg()
    /**
     * The option argument is optional.
     */
    public data object Maybe : HasArg()

    public fun rustEquals(other: HasArg): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Yes -> 0
        No -> 1
        Maybe -> 2
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

/**
 * Describes how often an option may occur.
 */
public sealed class Occur {
    /**
     * The option occurs once.
     */
    public data object Req : Occur()
    /**
     * The option occurs at most once.
     */
    public data object Optional : Occur()
    /**
     * The option occurs zero or more times.
     */
    public data object Multi : Occur()

    public fun rustEquals(other: Occur): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Req -> 0
        Optional -> 1
        Multi -> 2
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

/**
 * Describes whether an option is given at all or has a value.
 */
public sealed class Optval {
    public data class Val(public var value: String) : Optval()
    public data object Given : Optval()

    public fun rustEquals(other: Optval): Boolean = this == other
}

/**
 * The type returned when the command line does not conform to the
 * expected format. Use the `Debug` implementation to output detailed
 * information.
 */
public sealed class Fail {
    /**
     * The option requires an argument but none was passed.
     */
    public data class ArgumentMissing(public var value: String) : Fail()
    /**
     * The passed option is not declared among the possible options.
     */
    public data class UnrecognizedOption(public var value: String) : Fail()
    /**
     * A required option is not present.
     */
    public data class OptionMissing(public var value: String) : Fail()
    /**
     * A single occurrence option is being used multiple times.
     */
    public data class OptionDuplicated(public var value: String) : Fail()
    /**
     * There's an argument being passed to a non-argument option.
     */
    public data class UnexpectedArgument(public var value: String) : Fail()

    public fun rustEquals(other: Fail): Boolean = this == other

    public open fun fmt(f: StringBuilder): io.github.kotlinmania.rustlang.core.result.Result<Unit, Error> =
        when (this) {
            is ArgumentMissing -> {
                val (nm) = this as ArgumentMissing
                run { f.append("Argument to option '$nm' missing"); RustResult.Ok<Unit, Error>(Unit) }
            }
            is UnrecognizedOption -> {
                val (nm1) = this as UnrecognizedOption
                run { f.append("Unrecognized option: '$nm1'"); RustResult.Ok<Unit, Error>(Unit) }
            }
            is OptionMissing -> {
                val (nm2) = this as OptionMissing
                run { f.append("Required option '$nm2' missing"); RustResult.Ok<Unit, Error>(Unit) }
            }
            is OptionDuplicated -> {
                val (nm3) = this as OptionDuplicated
                run { f.append("Option '$nm3' given more than once"); RustResult.Ok<Unit, Error>(Unit) }
            }
            is UnexpectedArgument -> {
                val (nm4) = this as UnexpectedArgument
                run { f.append("Option '$nm4' does not take an argument"); RustResult.Ok<Unit, Error>(Unit) }
            }
        }
}

/**
 * The result of parsing a command line with a set of options.
 */
public typealias Result = io.github.kotlinmania.rustlang.core.result.Result<Matches, Fail>
