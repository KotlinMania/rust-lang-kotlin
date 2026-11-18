// port-lint: source path.rs
package io.github.kotlinmania.rustlang.std.path

import io.github.kotlinmania.rustlang.alloc.borrow.Cow
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.rcs.arc.Arc
import io.github.kotlinmania.rustlang.alloc.rcs.rc.Rc
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.cmp.Ordering.Equal
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.rotateRight
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.len
import io.github.kotlinmania.rustlang.core.ptr.constptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.len
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.slice.splitLast
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.str.traits.FromStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fs.ReadDir
import io.github.kotlinmania.rustlang.std.fs.exists
import io.github.kotlinmania.rustlang.std.fs.metadata
import io.github.kotlinmania.rustlang.std.fs.readDir
import io.github.kotlinmania.rustlang.std.fs.symlinkMetadata
import io.github.kotlinmania.rustlang.std.path.Prefix.*
import io.github.kotlinmania.rustlang.std.sys.args.unix.init
import io.github.kotlinmania.rustlang.std.sys.path.isSepByte
import io.github.kotlinmania.rustlang.std.sys.path.unix.HAS_PREFIXES
import io.github.kotlinmania.rustlang.std.sys.path.unix.isVerbatimSep
import io.github.kotlinmania.rustlang.std.sys.path.unix.parsePrefix
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

// Cross-platform path manipulation.
//
// This module provides two types, [PathBuf] and [Path] (akin to [String]
// and [str]), for working with paths abstractly. These types are thin wrappers
// around [OsString] and [OsStr] respectively, meaning that they work directly
// on strings according to the local platform's path syntax.
//
// Paths can be parsed into [Component]s by iterating over the structure
// returned by the [components] method on [Path]. [Component]s roughly
// correspond to the substrings between path separators (`/` or `\`). You can
// reconstruct an equivalent path from components with the [push] method on
// [PathBuf]; note that the paths may differ syntactically by the
// normalization described in the documentation for the [components] method.
//
// ## Case sensitivity
//
// Unless otherwise indicated path methods that do not access the filesystem,
// such as [Path.startsWith] and [Path.endsWith], are case sensitive no
// matter the platform or filesystem. An exception to this is made for Windows
// drive letters.
//
// ## Path normalization
//
// Several methods in this module perform basic path normalization by disregarding
// repeated separators, non-leading `.` components, and trailing separators. These include:
// - Methods for iteration, such as [Path.components] and [Path.iter]
// - Methods for inspection, such as [Path.hasRoot]
// - Comparisons using [PartialEq], [PartialOrd], and [Ord]
//
// [Path.join] and [PathBuf.push] also disregard trailing slashes.
//
// These methods **do not** resolve `..` components or symlinks. For full normalization
// including `..` resolution, use [Path.canonicalize] (which does access the filesystem).
//
// ## Simple usage
//
// Path manipulation includes both parsing components from slices and building
// new owned paths.
//
// To parse a path, you can create a [Path] slice from a [str]
// slice and start asking questions:
//
// ```kotlin
// import std.ffi.OsStr
// import std.path.Path
//
// val path = Path.new("/tmp/foo/bar.txt")
// val parent = path.parent()
// check(parent == Path.new("/tmp/foo"))
// val fileStem = path.fileStem()
// check(fileStem == OsStr.new("bar"))
// val extension = path.extension()
// check(extension == OsStr.new("txt"))
// ```
//
// To build or modify paths, use [PathBuf]:
//
// ```kotlin
// import std.path.PathBuf
//
// // This way works...
// var path = PathBuf.from("c:\\")
// path.push("windows")
// path.push("system32")
// path.setExtension("dll")
// // ... but push is best used if you don't know everything up
// // front. If you do, this way is better:
// val path1: PathBuf = listOf("c:\\", "windows", "system32.dll").iter().collect()
// ```
//
// [components]: Path.components
// [push]: PathBuf.push

private class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

public typealias Owned = PathBuf

// ///////////////////////////////////////////////////////////////////////////
// GENERAL NOTES
// ///////////////////////////////////////////////////////////////////////////
//
// Parsing in this module is done by directly transmuting OsStr to [u8] slices,
// taking advantage of the fact that OsStr always encodes ASCII characters
// as-is.  Eventually, this transmutation should be replaced by direct uses of
// OsStr APIs for parsing, but it will take a while for those to become
// available.
// ///////////////////////////////////////////////////////////////////////////
// Windows Prefixes
// ///////////////////////////////////////////////////////////////////////////

/**
 * Windows path prefixes, e.g., `C:` or `\\server\share`.
 *
 * Windows uses a variety of path prefix styles, including references to drive
 * volumes (like `C:`), network shared folders (like `\\server\share`), and
 * others. In addition, some path prefixes are "verbatim" (i.e., prefixed with
 * `\\?\`), in which case `/` is *not* treated as a separator and essentially
 * no normalization is performed.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ffi.OsStr
 * import std.path.Component
 * import std.path.Path
 * import std.path.Prefix
 * import std.path.Prefix.*
 *
 * fun getPathPrefix(s: String): Prefix {
 *     val path = Path.new(s)
 *     return when (val matchVal = path.components().next()!!) {
 *         is Component.Prefix -> {
 *             val (prefixComponent) = matchVal
 *             prefixComponent.kind()
 *         }
 *         else -> error("panic")
 *     }
 * }
 * if (false) {
 *     check(Verbatim(OsStr.new("pictures")) == getPathPrefix("\\\\?\\pictures\\kittens"))
 *     check(VerbatimUNC(OsStr.new("server"), OsStr.new("share")) == getPathPrefix("\\\\?\\UNC\\server\\share"))
 *     check(VerbatimDisk(67u.toUByte()) == getPathPrefix("\\\\?\\c:\\"))
 *     check(DeviceNS(OsStr.new("BrainInterface")) == getPathPrefix("\\\\.\\BrainInterface"))
 *     check(unc(OsStr.new("server"), OsStr.new("share")) == getPathPrefix("\\\\server\\share"))
 *     check(Disk(67u.toUByte()) == getPathPrefix("C:\\Users\\Rust\\Pictures\\Ferris"))
 * }
 * ```
 */
public sealed class Prefix {
    /**
     * Verbatim prefix, e.g., `\\?\catPics`.
     *
     * Verbatim prefixes consist of `\\?\` immediately followed by the given
     * component.
     */
    public data class Verbatim(public var value: OsStr) : Prefix()
    /**
     * Verbatim prefix using Windows' _**U**niform **N**aming **C**onvention_,
     * e.g., `\\?\UNC\server\share`.
     *
     * Verbatim UNC prefixes consist of `\\?\UNC\` immediately followed by the
     * server's hostname and a share name.
     */
    public data class VerbatimUNC(public var field0: OsStr, public var field1: OsStr) : Prefix()
    /**
     * Verbatim disk prefix, e.g., `\\?\C:`.
     *
     * Verbatim disk prefixes consist of `\\?\` immediately followed by the
     * drive letter and `:`.
     */
    public data class VerbatimDisk(public var value: UByte) : Prefix()
    /**
     * Device namespace prefix, e.g., `\\.\COM42`.
     *
     * Device namespace prefixes consist of `\\.\` (possibly using `/`
     * instead of `\`), immediately followed by the device name.
     */
    public data class DeviceNS(public var value: OsStr) : Prefix()
    /**
     * Prefix using Windows' _**U**niform **N**aming **C**onvention_, e.g.
     * `\\server\share`.
     *
     * UNC prefixes consist of the server's hostname and a share name.
     */
    public data class Unc(public var field0: OsStr, public var field1: OsStr) : Prefix()
    /**
     * Prefix `C:` for the given disk drive.
     */
    public data class Disk(public var value: UByte) : Prefix()

    public fun rustEquals(other: Prefix): Boolean = this == other

    public open fun len(): Int {
        fun osStrLen(s: OsStr): Int = s.asEncodedBytes().len()
        return when (this) {
            is Verbatim -> {
                val (x) = this as Verbatim
                4 + osStrLen(x)
            }
            is VerbatimUNC -> {
                val (x1, y) = this as VerbatimUNC
                8 + osStrLen(x1) +
                    if (osStrLen(y) > 0) {
                        1 + osStrLen(y)
                    } else {
                        0
                    }
            }
            is VerbatimDisk -> 6
            is Unc -> {
                val (x2, y1) = this as Unc
                2 + osStrLen(x2) +
                    if (osStrLen(y1) > 0) {
                        1 + osStrLen(y1)
                    } else {
                        0
                    }
            }
            is DeviceNS -> {
                val (x3) = this as DeviceNS
                4 + osStrLen(x3)
            }
            is Disk -> 2
        }
    }

    /**
     * Determines if the prefix is verbatim, i.e., begins with `\\?\`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.path.Prefix.*
     *
     * check(Verbatim(OsStr.new("pictures")).isVerbatim())
     * check(VerbatimUNC(OsStr.new("server"), OsStr.new("share")).isVerbatim())
     * check(VerbatimDisk(67u.toUByte()).isVerbatim())
     * check(!DeviceNS(OsStr.new("BrainInterface")).isVerbatim())
     * check(!unc(OsStr.new("server"), OsStr.new("share")).isVerbatim())
     * check(!Disk(67u.toUByte()).isVerbatim())
     * ```
     */
    public open fun isVerbatim(): Boolean =
        when (this) {
            is Verbatim -> true
            is VerbatimDisk -> true
            is VerbatimUNC -> true
            else -> false
        }

    public open fun isDrive(): Boolean =
        when (this) {
            is Prefix.Disk -> true
            else -> false
        }

    public open fun hasImplicitRoot(): Boolean = !this.isDrive()
}

// ///////////////////////////////////////////////////////////////////////////
// Exposed parsing helpers
// ///////////////////////////////////////////////////////////////////////////

/**
 * Determines whether the character is one of the permitted path
 * separators for the current platform.
 *
 * # Examples
 *
 * ```kotlin
 * import std.path
 *
 * check(isSeparator('/'))
 * // '/' works for both Unix and Windows
 * check(!isSeparator('\u2764'))
 * ```
 */
public fun isSeparator(c: Char): Boolean = (c.isAscii() && isSepByte(c.code.toUByte())).toUByte()

/**
 * All path separators recognized on the current platform, represented as [char]s; for example,
 * this is `['/'][..]` on Unix and `['\\', '/'][..]` on Windows. The [primary
 * separator](MAIN_SEPARATOR) is always element 0 of the slice.
 */
public val SEPARATORS: List<Char> = SEPARATORS

/**
 * All path separators recognized on the current platform, represented as [str]s; for example,
 * this is `["/"][..]` on Unix and `["\\", "/"][..]` on Windows. The [primary
 * separator](MAIN_SEPARATOR_STR) is always element 0 of the slice.
 */
public val SEPARATORS_STR: List<String> = SEPARATORS_STR

/**
 * The primary separator of path components for the current platform, represented as a [char];
 * for example, this is `'/'` on Unix and `'\\'` on Windows.
 */
public val MAIN_SEPARATOR: Char = SEPARATORS[0]

/**
 * The primary separator of path components for the current platform, represented as a [str];
 * for example, this is `"/"` on Unix and `"\\"` on Windows.
 */
public val MAIN_SEPARATOR_STR: String = SEPARATORS_STR[0]

// ///////////////////////////////////////////////////////////////////////////
// Misc helpers
// ///////////////////////////////////////////////////////////////////////////
// Iterate through `iter` while it matches `prefix`; return `None` if `prefix`
// is not a prefix of `iter`, otherwise return `Some(iterAfterPrefix)` giving
// `iter` after having exhausted `prefix`.
public fun <I, J> iterAfter(iter: I, prefix: J, cloneIFn: (I) -> I = { it }): I? where I : Iterator<Component>, J : Iterator<Component> {
    var iter1 = iter
    while (true) {
        val iterNext = cloneIFn(iter1)
        run {
            val matchVal = Pair(iterNext.next(), prefix.next())
            when {
                matchVal.first != null && matchVal.second != null && matchVal.first == matchVal.second -> {
                    val x = matchVal.first
                    val y = matchVal.second
                    Unit
                }
                matchVal.first != null && matchVal.second != null -> { return null }
                matchVal.first != null && matchVal.second == null -> { return iter1 }
                matchVal.first == null && matchVal.second == null -> { return iter1 }
                else -> { return null }
            }
        }
        iter1 = iterNext
    }
}

// ///////////////////////////////////////////////////////////////////////////
// Cross-platform, iterator-independent parsing
// ///////////////////////////////////////////////////////////////////////////

/**
 * Says whether the first byte after the prefix is a separator.
 */
public fun hasPhysicalRoot(s: List<UByte>, prefix: Prefix?): Boolean {
    val path =
        if (prefix != null) {
            val p = prefix
            ((s).subList(p.len(), (s).size))
        } else {
            s
        }
    return (!path.isEmpty() && isSepByte(path[0])).toUByte()
}

// basic workhorse for splitting stem and extension
public fun rsplitFileAtDot(file: OsStr): Pair<OsStr?, OsStr?> {
    if (file.asEncodedBytes() == "..".encodeToByteArray()) {
        return Pair(file, null)
    }
    // The unsafety here stems from converting between &OsStr and &[u8]
    // and back. This is safe to do because (1) we only look at ASCII
    // contents of the encoding and (2) new &OsStr values are produced
    // only from ASCII-bounded slices of existing &OsStr values.
    val iter = file.asEncodedBytes().rsplitn(2, { b -> b == 46u.toUByte() })
    val after = iter.next()
    val before = iter.next()
    return if (before == "".encodeToByteArray()) {
        Pair(file, null)
    } else {
        Pair(before.map({ s -> OsStr.fromEncodedBytesUnchecked(s) }), after.map({ s -> OsStr.fromEncodedBytesUnchecked(s) }))
    }
}

public fun splitFileAtDot(file: OsStr): Pair<OsStr, OsStr?> {
    val slice = file.asEncodedBytes()
    if (slice == "..".encodeToByteArray()) {
        return Pair(file, null)
    }
    // The unsafety here stems from converting between &OsStr and &[u8]
    // and back. This is safe to do because (1) we only look at ASCII
    // contents of the encoding and (2) new &OsStr values are produced
    // only from ASCII-bounded slices of existing &OsStr values.
    val i1 =
        when (val matchVal = slice[1 until slice.size].indexOfFirst({ b -> b == 46u.toUByte() }).takeIf { it >= 0 }) {
            null -> { return Pair(file, null) }
            else -> {
                val i = matchVal
                i + 1
            }
        }
    val before = slice[0 until i1]
    val after = slice[i1 + 1 until slice.size]
    return Pair(OsStr.fromEncodedBytesUnchecked(before), OsStr.fromEncodedBytesUnchecked(after))
}

/**
 * Checks whether the string is valid as a file extension, or panics otherwise.
 */
public fun validateExtension(extension: OsStr) {
    for (b in extension.asEncodedBytes()) {
        if (isSepByte(b)) {
            error("extension cannot contain path separators: $extension")
        }
    }
}

// ///////////////////////////////////////////////////////////////////////////
// The core iterators
// ///////////////////////////////////////////////////////////////////////////

/**
 * Component parsing works by a double-ended state machine; the cursors at the
 * front and back of the path each keep track of what parts of the path have
 * been consumed so far.
 *
 * Going front to back, a path is made up of a prefix, a starting
 * directory component, and a body (of normal components)
 */
public sealed class State(
    public val value: Int,
) {
    public data object Prefix : State(0)
    // c:
    public data object StartDir : State(1)
    // / or . or nothing
    public data object Body : State(2)
    // foo/bar/baz
    public data object Done : State(3)

    public fun rustEquals(other: State): Boolean = this == other

    public fun toByte(): Byte = value.toByte()

    public fun toUByte(): UByte = value.toUByte()

    public fun toShort(): Short = value.toShort()

    public fun toUShort(): UShort = value.toUShort()

    public fun toInt(): Int = value

    public fun toUInt(): UInt = value.toUInt()

    public fun toLong(): Long = value.toLong()

    public fun toULong(): ULong = value.toULong()
}

/**
 * A structure wrapping a Windows path prefix as well as its unparsed string
 * representation.
 *
 * In addition to the parsed [Prefix] information returned by [kind],
 * `PrefixComponent` also holds the raw and unparsed [OsStr] slice,
 * returned by [asOsStr].
 *
 * Instances of this `class` can be obtained by matching against the
 * [`Prefix` variant] on [Component].
 *
 * Does not occur on Unix.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ffi.OsStr
 * import std.path.Component
 * import std.path.Path
 * import std.path.Prefix
 *
 * if (false) {
 *     val path = Path.new("c:\\you\\later\\")
 *     when (val matchVal = path.components().next()!!) {
 *         is Component.Prefix -> {
 *             val (prefixComponent) = matchVal
 *             check(Prefix.Disk(67u.toUByte()) == prefixComponent.kind())
 *             check(OsStr.new("c:") == prefixComponent.asOsStr())
 *         }
 *         else -> error("unreachable")
 *     }
 * }
 * ```
 *
 * [asOsStr]: PrefixComponent.asOsStr
 * [kind]: PrefixComponent.kind
 * [`Prefix` variant]: Component.Prefix
 */
public open class PrefixComponent(
    /**
     * The prefix as an unparsed `OsStr` slice.
     */
    public var raw: OsStr,
    /**
     * The parsed prefix data.
     */
    public var parsed: Prefix,
) {
    public open fun clone(): PrefixComponent = PrefixComponent(raw, parsed)

    /**
     * Returns the parsed prefix data.
     *
     * See [Prefix]s documentation for more information on the different
     * kinds of prefixes.
     */
    public open fun kind(): Prefix = this.parsed

    /**
     * Returns the raw [OsStr] slice for this prefix.
     */
    public open fun asOsStr(): OsStr = this.raw

    public open fun eq(other: PrefixComponent): Boolean = (this.parsed == other.parsed).toUByte()

    public open fun partialCmp(other: PrefixComponent): Ordering? = PartialOrd.partialCmp(this.parsed, other.parsed)

    public open fun cmp(other: PrefixComponent): Ordering = Ord.cmp(this.parsed, other.parsed)

    public override fun hashCode(): Int {
        var h = 0
        h = 31 * h + (this.parsed).hashCode()
        return h
    }

    public companion object
}

/**
 * A single component of a path.
 *
 * A `Component` roughly corresponds to a substring between path separators
 * (`/` or `\`).
 *
 * This `sealed class` is created by iterating over [Components], which in turn is
 * created by the [`components`](Path.components) method on [Path].
 *
 * # Examples
 *
 * ```kotlin
 * import std.path.Component
 * import std.path.Path
 *
 * val path = Path.new("/tmp/foo/bar.txt")
 * val components = path.components().collect<Vec>()
 * check(components == listOf(Component.RootDir, Component.Normal("tmp".asRef()), Component.Normal("foo".asRef()), Component.Normal("bar.txt".asRef())))
 * ```
 */
public sealed class Component : AsRef<OsStr> {
    /**
     * A Windows path prefix, e.g., `C:` or `\\server\share`.
     *
     * There is a large variety of prefix types, see [Prefix]s documentation
     * for more.
     *
     * Does not occur on Unix.
     */
    public data class Prefix(public var value: PrefixComponent) : Component()
    /**
     * The root directory component, appears after any prefix and before anything else.
     *
     * It represents a separator that designates that a path starts from root.
     */
    public data object RootDir : Component()
    /**
     * A reference to the current directory, i.e., `.`.
     */
    public data object CurDir : Component()
    /**
     * A reference to the parent directory, i.e., `..`.
     */
    public data object ParentDir : Component()
    /**
     * A normal component, e.g., `a` and `b` in `a / b`.
     *
     * This variant is the most common one, it represents references to files
     * or directories.
     */
    public data class Normal(public var value: OsStr) : Component()

    public fun rustEquals(other: Component): Boolean = this == other

    /**
     * Extracts the underlying [OsStr] slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("./tmp/foo/bar.txt")
     * val components: Vec = path.components().map({ comp -> comp.asOsStr() }).collect()
     * check(components == listOf(".", "tmp", "foo", "bar.txt"))
     * ```
     */
    public open fun asOsStr(): OsStr =
        when (this) {
            is Component.Prefix -> {
                val (p) = this as Component.Prefix
                p.asOsStr()
            }
            Component.RootDir -> OsStr.new(MAIN_SEPARATOR_STR)
            Component.CurDir -> OsStr.new(".")
            Component.ParentDir -> OsStr.new("..")
            is Component.Normal -> {
                val (path) = this as Component.Normal
                path
            }
        }

    public open fun asRef(): OsStr = this.asOsStr()

    public open fun asRefPath(): Path = this.asOsStr().asRef()
}

/**
 * An iterator over the [Component]s of a [Path].
 *
 * This `class` is created by the [components] method on [Path].
 * See its documentation for more.
 *
 * # Examples
 *
 * ```kotlin
 * import std.path.Path
 *
 * val path = Path.new("/tmp/foo/bar.txt")
 * for (component in path.components()) {
 *     println("$component")
 * }
 * ```
 *
 * [components]: Path.components
 */
public open class Components(
    // The path left to parse components from
    public var path: List<UByte>,
    // The prefix as it was originally parsed, if any
    public var prefix: Prefix?,
    // true if path *physically* has a root separator; for most Windows
    // prefixes, it may have a "logical" root separator for the purposes of
    // normalization, e.g., \\server\share == \\server\share\.
    public var hasPhysicalRoot: Boolean,
    // The iterator is double-ended, and these two states keep track of what has
    // been produced from either end
    public var front: State,
    public var back: State,
) : AsRef<Path>, Iterable<Component> {
    override fun iterator(): Iterator<Component> = object : AbstractIterator<Component>() {
        override fun computeNext() {
            when (val v = this@Components.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Components = Components(path, prefix, hasPhysicalRoot, front, back)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        open class DebugHelper(
            public var value: Path,
        ) {
            public var field0: Path
                get() = value
                set(v) {
                    value = v
                }

            public open fun fmt(f1: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f1.append(entries.joinToString(", ", "[", "]")) } }.entries(this.value.components()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
        }
        return run { DebugTuple(f, "Components").field(DebugHelper(this.asPath())).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    // how long is the prefix, if any?
    public open fun prefixLen(): Int {
        if (HAS_PREFIXES.inv()) {
            return 0
        }
        return (this.prefix?.let({ it.len() }) ?: 0)
    }

    public open fun prefixVerbatim(): Boolean {
        if (HAS_PREFIXES.inv()) {
            return false
        }
        return (this.prefix?.let({ it.isVerbatim() }) ?: false)
    }

    /**
     * how much of the prefix is left from the point of view of iteration?
     */
    public open fun prefixRemaining(): Int {
        if (HAS_PREFIXES.inv()) {
            return 0
        }
        return if (this.front == State.Prefix) {
            this.prefixLen()
        } else {
            0
        }
    }

    // Given the iteration so far, how much of the pre-State.Body path is left?
    public open fun lenBeforeBody(): Int {
        val root =
            if (this.front <= State.StartDir && this.hasPhysicalRoot) {
                1
            } else {
                0
            }
        val curDir =
            if (this.front <= State.StartDir && this.includeCurDir()) {
                1
            } else {
                0
            }
        return this.prefixRemaining() + root + curDir
    }

    // is the iteration complete?
    public open fun finished(): Boolean = (this.front == State.Done || this.back == State.Done || this.front > this.back).toUByte()

    public open fun isSepByte(b: UByte): Boolean =
        if (this.prefixVerbatim()) {
            isVerbatimSep(b)
        } else {
            isSepByte(b)
        }

    /**
     * Extracts a slice corresponding to the portion of the path remaining for iteration.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * var components = Path.new("/tmp/foo/bar.txt").components()
     * components.next()
     * components.next()
     * check(Path.new("foo/bar.txt") == components.asPath())
     * ```
     */
    public open fun asPath(): Path {
        val comps = this.clone()
        if (comps.front == State.Body) {
            comps.trimLeft()
        }
        if (comps.back == State.Body) {
            comps.trimRight()
        }
        return Path.fromU8Slice(comps.path)
    }

    /**
     * Is the *original* path rooted?
     */
    public open fun hasRoot(): Boolean {
        if (this.hasPhysicalRoot) {
            return true
        }
        if (HAS_PREFIXES && this.prefix != null) {
            if (p.hasImplicitRoot()) {
                return true
            }
        }
        return false
    }

    /**
     * Should the normalized path include a leading . ?
     */
    public open fun includeCurDir(): Boolean {
        if (this.hasRoot()) {
            return false
        }
        val slice = ((this.path).subList(this.prefixRemaining(), (this.path).size))
        return when {
            slice.size == 1 && slice[0] == 46u.toUByte() -> true
            slice.size >= 2 && slice[0] == 46u.toUByte() -> {
                val b = slice[1]
                this.isSepByte(b)
            }
            else -> false
        }
    }

    // parse a given byte sequence following the OsStr encoding into the
    // corresponding path component
    public open fun parseSingleComponent(comp: List<UByte>): Component? =
        when {
            comp == ".".encodeToByteArray() && HAS_PREFIXES && this.prefixVerbatim() -> Component.CurDir
            comp == ".".encodeToByteArray() -> null
            // . components are normalized away, except at
            // the beginning of a path, which is treated
            // separately via `includeCurDir`
            comp == "..".encodeToByteArray() -> Component.ParentDir
            comp == "".encodeToByteArray() -> null
            else -> Component.Normal(OsStr.fromEncodedBytesUnchecked(comp))
        }

    // parse a component from the left, saying how many bytes to consume to
    // remove the component
    public open fun parseNextComponent(): Pair<Int, Component?> {
        check(this.front == State.Body)
        val (extra, comp) =
            when (val matchVal = this.path.indexOfFirst({ b -> this.isSepByte(b) }).takeIf { it >= 0 }) {
                null -> Pair(0, this.path)
                else -> {
                    val i = matchVal
                    Pair(1, ((this.path).subList(0, i)))
                }
            }
        // SAFETY: `comp` is a valid substring, since it is split on a separator.
        return Pair(comp.len() + extra, this.parseSingleComponent(comp))
    }

    // parse a component from the right, saying how many bytes to consume to
    // remove the component
    public open fun parseNextComponentBack(): Pair<Int, Component?> {
        check(this.back == State.Body)
        val start = this.lenBeforeBody()
        val (extra, comp) =
            when (val matchVal = ((this.path).subList(start, (this.path).size)).iter().indexOfLast({ b -> this.isSepByte(b) }).takeIf { it >= 0 }) {
                null -> Pair(0, ((this.path).subList(start, (this.path).size)))
                else -> {
                    val i = matchVal
                    Pair(1, ((this.path).subList(start + i + 1, (this.path).size)))
                }
            }
        // SAFETY: `comp` is a valid substring, since it is split on a separator.
        return Pair(comp.len() + extra, this.parseSingleComponent(comp))
    }

    // trim away repeated separators (i.e., empty components) on the left
    public open fun trimLeft() {
        while (!this.path.isEmpty()) {
            val (size, comp) = this.parseNextComponent()
            if (comp != null) {
                return
            } else {
                this.path = ((this.path).subList(size, (this.path).size))
            }
        }
    }

    // trim away repeated separators (i.e., empty components) on the right
    public open fun trimRight() {
        while (this.path.size > this.lenBeforeBody()) {
            val (size, comp) = this.parseNextComponentBack()
            if (comp != null) {
                return
            } else {
                this.path = ((this.path).subList(0, this.path.size - size))
            }
        }
    }

    public open fun asRef(): Path = this.asPath()

    public open fun asRefOsStr(): OsStr = this.asPath().asOsStr()

    public open fun next(): Component? {
        while (!this.finished()) {
            run {
                val matchVal = this.front
                when {
                    // most likely case first
                    matchVal == State.Body && !this.path.isEmpty() -> {
                        val (size, comp) = this.parseNextComponent()
                        this.path = ((this.path).subList(size, (this.path).size))
                        if (comp != null) {
                            return comp
                        }
                    }
                    matchVal == State.Body -> {
                        this.front = State.Done
                    }
                    matchVal == State.StartDir -> {
                        this.front = State.Body
                        if (this.hasPhysicalRoot) {
                            check(!this.path.isEmpty())
                            this.path = ((this.path).subList(1, (this.path).size))
                            return Component.RootDir
                        } else if (HAS_PREFIXES && this.prefix != null) {
                            if (p.hasImplicitRoot() && !p.isVerbatim()) {
                                return Component.RootDir
                            }
                        } else if (this.includeCurDir()) {
                            check(!this.path.isEmpty())
                            this.path = ((this.path).subList(1, (this.path).size))
                            return Component.CurDir
                        }
                    }
                    HAS_PREFIXES.inv() -> error("unreachable")
                    matchVal == State.Prefix && this.prefixLen() == 0 -> {
                        this.front = State.StartDir
                    }
                    matchVal == State.Prefix -> {
                        this.front = State.StartDir
                        check(this.prefixLen() <= this.path.size)
                        val raw = ((this.path).subList(0, this.prefixLen()))
                        this.path = ((this.path).subList(this.prefixLen(), (this.path).size))
                        return Component.Prefix(PrefixComponent(raw = OsStr.fromEncodedBytesUnchecked(raw), parsed = this.prefix!!))
                    }
                    else -> error("unreachable")
                }
            }
        }
        return null
    }

    public open fun nextBack(): Component? {
        while (!this.finished()) {
            run {
                val matchVal = this.back
                when {
                    matchVal == State.Body && this.path.size > this.lenBeforeBody() -> {
                        val (size, comp) = this.parseNextComponentBack()
                        this.path = ((this.path).subList(0, this.path.size - size))
                        if (comp != null) {
                            return comp
                        }
                    }
                    matchVal == State.Body -> {
                        this.back = State.StartDir
                    }
                    matchVal == State.StartDir -> {
                        this.back = if (HAS_PREFIXES) {
                            State.Prefix
                        } else {
                            State.Done
                        }
                        if (this.hasPhysicalRoot) {
                            this.path = ((this.path).subList(0, this.path.size - 1))
                            return Component.RootDir
                        } else if (HAS_PREFIXES && this.prefix != null) {
                            if (p.hasImplicitRoot() && !p.isVerbatim()) {
                                return Component.RootDir
                            }
                        } else if (this.includeCurDir()) {
                            this.path = ((this.path).subList(0, this.path.size - 1))
                            return Component.CurDir
                        }
                    }
                    HAS_PREFIXES.inv() -> error("unreachable")
                    matchVal == State.Prefix && this.prefixLen() > 0 -> {
                        this.back = State.Done
                        return Component.Prefix(PrefixComponent(raw = OsStr.fromEncodedBytesUnchecked(this.path), parsed = this.prefix!!))
                    }
                    matchVal == State.Prefix -> {
                        this.back = State.Done
                        return null
                    }
                    else -> error("unreachable")
                }
            }
        }
        return null
    }

    public open fun eq(other: Components): Boolean {
        // Fast path for exact matches, e.g. for hashmap lookups.
        // Don't explicitly compare the prefix or hasPhysicalRoot fields since they'll
        // either be covered by the `path` buffer or are only relevant for `prefixVerbatim()`.
        if (this.path.size == other.path.size && this.front == other.front && this.back == State.Body && other.back == State.Body && this.prefixVerbatim() == other.prefixVerbatim()) {
            // possible future improvement: this could bail out earlier if there were a
            // reverse memcmp/bcmp comparing back to front
            if (this.path == other.path) {
                return true
            }
        }
        // compare back to front since absolute paths often share long prefixes
        return Iterator.eq(this.clone().rev(), other.clone().rev())
    }

    public open fun partialCmp(other: Components): Ordering? = compareComponents(this.clone(), other.clone())

    public open fun cmp(other: Components): Ordering = compareComponents(this.clone(), other.clone())

    public companion object
}

/**
 * An iterator over the [Component]s of a [Path], as [OsStr] slices.
 *
 * This `class` is created by the [iter] method on [Path].
 * See its documentation for more.
 *
 * [iter]: Path.iter
 */
public open class Iter(
    public var inner: Components,
) : AsRef<Path>, Iterable<OsStr> {
    override fun iterator(): Iterator<OsStr> = object : AbstractIterator<OsStr>() {
        override fun computeNext() {
            when (val v = this@Iter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Iter = Iter(inner)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        open class DebugHelper(
            public var value: Path,
        ) {
            public var field0: Path
                get() = value
                set(v) {
                    value = v
                }

            public open fun fmt(f1: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f1.append(entries.joinToString(", ", "[", "]")) } }.entries(this.value.iter()).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
        }
        return run { DebugTuple(f, "Iter").field(DebugHelper(this.asPath())).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    /**
     * Extracts a slice corresponding to the portion of the path remaining for iteration.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * var iter = Path.new("/tmp/foo/bar.txt").iter()
     * iter.next()
     * iter.next()
     * check(Path.new("foo/bar.txt") == iter.asPath())
     * ```
     */
    public open fun asPath(): Path = this.inner.asPath()

    public open fun asRef(): Path = this.asPath()

    public open fun asRefOsStr(): OsStr = this.asPath().asOsStr()

    public open fun next(): OsStr? = this.inner.next()?.let({ it.asOsStr() })

    public open fun nextBack(): OsStr? = this.inner.nextBack()?.let({ it.asOsStr() })

    public companion object
}

public fun compareComponents(left: Components, right: Components): Ordering {
    // Fast path for long shared prefixes
    //
    // - compare raw bytes to find first mismatch
    // - backtrack to find separator before mismatch to avoid ambiguous parsings of '.' or '..' characters
    // - if found update state to only do a component-wise comparison on the remainder,
    // otherwise do it on the full path
    //
    // The fast path isn't taken for paths with a PrefixComponent to avoid backtracking into
    // the middle of one
    if (left.prefix == null && right.prefix == null && left.front == right.front) {
        // possible future improvement: a [u8]::firstMismatch simd implementation
        val firstDifference =
            run {
                val matchVal = (left.path.zip(right.path)).indexOfFirst({ (a, b) -> a != b }).takeIf { it >= 0 }
                when {
                    matchVal == null && left.path.size == right.path.size -> { return Ordering.Equal }
                    matchVal == null -> left.path.size.min(right.path.size)
                    else -> {
                        val diff = matchVal
                        diff
                    }
                }
            }
        when (val matchVal1 = ((left.path).subList(0, firstDifference)).iter().indexOfLast({ b -> left.isSepByte(b) }).takeIf { it >= 0 }) {
            null -> {}
            else -> {
                val previousSep = matchVal1
                val mismatchedComponentStart = previousSep + 1
                left.path = ((left.path).subList(mismatchedComponentStart, (left.path).size))
                left.front = State.Body
                right.path = ((right.path).subList(mismatchedComponentStart, (right.path).size))
                right.front = State.Body
            }
        }
    }
    return Iterator.cmp(left, right)
}

/**
 * An iterator over [Path] and its ancestors.
 *
 * This `class` is created by the [ancestors] method on [Path].
 * See its documentation for more.
 *
 * # Examples
 *
 * ```kotlin
 * import std.path.Path
 *
 * val path = Path.new("/foo/bar")
 * for (ancestor in path.ancestors()) {
 *     println("${ancestor.display()}")
 * }
 * ```
 *
 * [ancestors]: Path.ancestors
 */
public open class Ancestors(
    public var next: Path?,
) : Iterable<Path> {
    override fun iterator(): Iterator<Path> = object : AbstractIterator<Path>() {
        override fun computeNext() {
            when (val v = this@Ancestors.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Ancestors = Ancestors(next)

    public open fun next(): Path? {
        val next = this.next
        this.next = next?.let({ it.parent() })
        return next
    }

    public companion object
}

// ///////////////////////////////////////////////////////////////////////////
// Basic types and traits
// ///////////////////////////////////////////////////////////////////////////

/**
 * An owned, mutable path (akin to [String]).
 *
 * This type provides methods like [push] and [setExtension] that mutate
 * the path in place. It also implements [Deref] to [Path], meaning that
 * all methods on [Path] slices are available on `PathBuf` values as well.
 *
 * [push]: PathBuf.push
 * [setExtension]: PathBuf.setExtension
 *
 * More details about the overall approach can be found in
 * the [module documentation](self).
 *
 * # Examples
 *
 * You can use [push] to build up a `PathBuf` from
 * components:
 *
 * ```kotlin
 * import std.path.PathBuf; val path = PathBuf.new(); path.push(r"C:\"); path.push("windows"); path.push("system32"); path.setExtension("dll");
 * ```
 *
 * However, [push] is best used for dynamic situations. This is a better way
 * to do this when you know all of the components ahead of time:
 *
 * ```kotlin
 * import std.path.PathBuf; val path: PathBuf = [r"C:\", "windows", "system32.dll"].iter().collect();
 * ```
 *
 * We can still do better than this! Since these are all strings, we can use
 * `From.from`:
 *
 * ```kotlin
 * import std.path.PathBuf
 *
 * val path = PathBuf.from("C:\\windows\\system32.dll")
 * ```
 *
 * Which method works best depends on what kind of situation you're in.
 *
 * Note that `PathBuf` does not always sanitize arguments, for example
 * [push] allows paths built from strings which include separators:
 *
 * ```kotlin
 * import std.path.PathBuf; val path = PathBuf.new(); path.push(r"C:\"); path.push("windows"); path.push(r"..\otherdir"); path.push("system32");
 * ```
 *
 * The behavior of `PathBuf` may be changed to a panic on such inputs
 * in the future. [Extend.extend] should be used to add multi-part paths.
 */
public open class PathBuf(
    public var inner: OsString,
) : FromStr<Nothing>, Borrow<Path>, AsRef<OsStr> {
    /**
     * Coerces to a [Path] slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * val p = PathBuf.from("/test")
     * check(Path.new("/test") == p.asPath())
     * ```
     */
    public open fun asPath(): Path = this

    /**
     * Consumes and leaks the `PathBuf`, returning a mutable reference to the contents,
     * `Path`.
     *
     * The caller has free choice over the returned scope, including static.
     * Indeed, this function is ideally used for data that lives for the remainder of
     * the program's life, as dropping the returned reference will cause a memory leak.
     *
     * It does not reallocate or shrink the `PathBuf`, so the leaked allocation may include
     * unused capacity that is not part of the returned slice. If you want to discard excess
     * capacity, call [intoBoxedPath], and then [Box.leak] instead.
     * However, keep in mind that trimming the capacity may result in a reallocation and copy.
     *
     * [intoBoxedPath]: Self.intoBoxedPath
     */
    public open fun leak(): RustMutableReference<Path> = Path.fromInnerMut(this.inner.leak())

    /**
     * Extends `self` with `path`.
     *
     * If `path` is absolute, it replaces the current path.
     *
     * On Windows:
     *
     * * if `path` has a root but no prefix (e.g., `\windows`), it
     *   replaces everything except for the prefix (if any) of `self`.
     * * if `path` has a prefix but no root, it replaces `self`.
     * * if `self` has a verbatim prefix (e.g. `\\?\C:\windows`)
     *   and `path` is not empty, the new path is normalized: all references
     *   to `.` and `..` are removed.
     *
     * Consider using [Path.join] if you need a new `PathBuf` instead of
     * using this function on a cloned `PathBuf`.
     *
     * # Examples
     *
     * Pushing a relative path extends the existing path:
     *
     * ```kotlin
     * import std.path.PathBuf
     *
     * var path = PathBuf.from("/tmp")
     * path.push("file.bk")
     * check(path == PathBuf.from("/tmp/file.bk"))
     * ```
     *
     * Pushing an absolute path replaces the existing path:
     *
     * ```kotlin
     * import std.path.PathBuf
     *
     * var path = PathBuf.from("/tmp")
     * path.push("/etc")
     * check(path == PathBuf.from("/etc"))
     * ```
     */
    public open fun push(path: Path) {
        this.push(path)
    }

    /**
     * Truncates `self` to [this.parent].
     *
     * Returns `false` and does nothing if [this.parent] is [None].
     * Otherwise, returns `true`.
     *
     * [this.parent]: Path.parent
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * var p = PathBuf.from("/spirited/away.rs")
     * p.pop()
     * check(Path.new("/spirited") == p)
     * p.pop()
     * check(Path.new("/") == p)
     * ```
     */
    public open fun pop(): Boolean =
        when (val matchVal = this.parent().map({ p -> p.asU8Slice().len() })) {
            null -> false
            else -> {
                val len = matchVal
                this.inner.truncate(len)
                true
            }
        }

    /**
     * Sets whether the path has a trailing [separator](MAIN_SEPARATOR).
     *
     * The value returned by [`hasTrailingSep`](Path.hasTrailingSep) will be equivalent to
     * the provided value if possible.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.PathBuf
     *
     * @feature(pathTrailingSep)
     * public fun doctestEntry() {
     *     var p = PathBuf.from("dir")
     *     check(!p.hasTrailingSep())
     *     p.setTrailingSep(false)
     *     check(!p.hasTrailingSep())
     *     p.setTrailingSep(true)
     *     check(p.hasTrailingSep())
     *     p.setTrailingSep(false)
     *     check(!p.hasTrailingSep())
     *     p = PathBuf.from("/")
     *     check(p.hasTrailingSep())
     *     p.setTrailingSep(false)
     *     check(p.hasTrailingSep())
     * }
     * ```
     */
    public open fun setTrailingSep(trailingSep: Boolean) {
        if (trailingSep) {
            this.pushTrailingSep()
        } else {
            this.popTrailingSep()
        }
    }

    /**
     * Adds a trailing [separator](MAIN_SEPARATOR) to the path.
     *
     * This acts similarly to [Path.withTrailingSep], but mutates the underlying `PathBuf`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.path.PathBuf
     *
     * @feature(pathTrailingSep)
     * public fun doctestEntry() {
     *     var p = PathBuf.from("dir")
     *     check(!p.hasTrailingSep())
     *     p.pushTrailingSep()
     *     check(p.hasTrailingSep())
     *     p.pushTrailingSep()
     *     check(p.hasTrailingSep())
     *     p = PathBuf.from("dir/")
     *     p.pushTrailingSep()
     *     check(p.asOsStr() == OsStr.new("dir/"))
     * }
     * ```
     */
    public open fun pushTrailingSep() {
        if (!this.hasTrailingSep()) {
            this.push("")
        }
    }

    /**
     * Removes a trailing [separator](MAIN_SEPARATOR) from the path, if possible.
     *
     * This acts similarly to [Path.trimTrailingSep], but mutates the underlying `PathBuf`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.path.PathBuf
     *
     * @feature(pathTrailingSep)
     * public fun doctestEntry() {
     *     var p = PathBuf.from("dir//")
     *     check(p.hasTrailingSep())
     *     check(p.asOsStr() == OsStr.new("dir//"))
     *     p.popTrailingSep()
     *     check(!p.hasTrailingSep())
     *     check(p.asOsStr() == OsStr.new("dir"))
     *     p.popTrailingSep()
     *     check(!p.hasTrailingSep())
     *     check(p.asOsStr() == OsStr.new("dir"))
     *     p = PathBuf.from("/")
     *     check(p.hasTrailingSep())
     *     p.popTrailingSep()
     *     check(p.hasTrailingSep())
     * }
     * ```
     */
    public open fun popTrailingSep() {
        this.inner.truncate(this.trimTrailingSep().asOsStr().len())
    }

    /**
     * Updates [this.fileName] to `fileName`.
     *
     * If [this.fileName] was [None], this is equivalent to pushing
     * `fileName`.
     *
     * Otherwise it is equivalent to calling [pop] and then pushing
     * `fileName`. The new path will be a sibling of the original path.
     * (That is, it will have the same parent.)
     *
     * The argument is not sanitized, so can include separators. This
     * behavior may be changed to a panic in the future.
     *
     * [this.fileName]: Path.fileName
     * [pop]: PathBuf.pop
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.PathBuf
     *
     * var buf = PathBuf.from("/")
     * check(buf.fileName() == null)
     * buf.setFileName("foo.txt")
     * check(buf == PathBuf.from("/foo.txt"))
     * check(buf.fileName() != null)
     * buf.setFileName("bar.txt")
     * check(buf == PathBuf.from("/bar.txt"))
     * buf.setFileName("baz")
     * check(buf == PathBuf.from("/baz"))
     * buf.setFileName("../b/c.txt")
     * check(buf == PathBuf.from("/../b/c.txt"))
     * buf.setFileName("baz")
     * check(buf == PathBuf.from("/../b/baz"))
     * ```
     */
    public open fun setFileName(fileName: OsStr) {
        this.setFileName(fileName)
    }

    /**
     * Updates [this.extension] to `Some(extension)` or to `None` if
     * `extension` is empty.
     *
     * Returns `false` and does nothing if [this.fileName] is [None],
     * returns `true` and updates the extension otherwise.
     *
     * If [this.extension] is [None], the extension is added; otherwise
     * it is replaced.
     *
     * If `extension` is the empty string, [this.extension] will be [None]
     * afterwards, not `Some("")`.
     *
     * # Panics
     *
     * Panics if the passed extension contains a path separator (see
     * [isSeparator]).
     *
     * # Caveats
     *
     * The new `extension` may contain dots and will be used in its entirety,
     * but only the part after the final dot will be reflected in
     * [this.extension].
     *
     * If the file stem contains internal dots and `extension` is empty, part
     * of the old file stem will be considered the new [this.extension].
     *
     * See the examples below.
     *
     * [this.fileName]: Path.fileName
     * [this.extension]: Path.extension
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * var p = PathBuf.from("/feel/the")
     * p.setExtension("force")
     * check(Path.new("/feel/the.force") == p.asPath())
     * p.setExtension("dark.side")
     * check(Path.new("/feel/the.dark.side") == p.asPath())
     * p.setExtension("cookie")
     * check(Path.new("/feel/the.dark.cookie") == p.asPath())
     * p.setExtension("")
     * check(Path.new("/feel/the.dark") == p.asPath())
     * p.setExtension("")
     * check(Path.new("/feel/the") == p.asPath())
     * p.setExtension("")
     * check(Path.new("/feel/the") == p.asPath())
     * ```
     */
    public open fun setExtension(extension: OsStr): Boolean = this.setExtension(extension)

    /**
     * Append [this.extension] with `extension`.
     *
     * Returns `false` and does nothing if [this.fileName] is [None],
     * returns `true` and updates the extension otherwise.
     *
     * # Panics
     *
     * Panics if the passed extension contains a path separator (see
     * [isSeparator]).
     *
     * # Caveats
     *
     * The appended `extension` may contain dots and will be used in its entirety,
     * but only the part after the final dot will be reflected in
     * [this.extension].
     *
     * See the examples below.
     *
     * [this.fileName]: Path.fileName
     * [this.extension]: Path.extension
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * var p = PathBuf.from("/feel/the")
     * p.addExtension("formatted")
     * check(Path.new("/feel/the.formatted") == p.asPath())
     * p.addExtension("dark.side")
     * check(Path.new("/feel/the.formatted.dark.side") == p.asPath())
     * p.setExtension("cookie")
     * check(Path.new("/feel/the.formatted.dark.cookie") == p.asPath())
     * p.setExtension("")
     * check(Path.new("/feel/the.formatted.dark") == p.asPath())
     * p.addExtension("")
     * check(Path.new("/feel/the.formatted.dark") == p.asPath())
     * ```
     */
    public open fun addExtension(extension: OsStr): Boolean = this.addExtension(extension)

    /**
     * Yields a mutable reference to the underlying [OsString] instance.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * var path = PathBuf.from("/foo")
     * path.push("bar")
     * check(path == Path.new("/foo/bar"))
     * // OsString's `push` does not add a separator.
     * path.asMutOsString().push("baz")
     * check(path == Path.new("/foo/barbaz"))
     * ```
     */
    public open fun asMutOsString(): kotlin.reflect.KMutableProperty0<OsString> {
        return this::inner
    }

    /**
     * Consumes the `PathBuf`, yielding its internal [OsString] storage.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.PathBuf
     *
     * val p = PathBuf.from("/the/head")
     * val osStr = p.intoOsString()
     * ```
     */
    public open fun intoOsString(): OsString = this.inner

    /**
     * Converts the `PathBuf` into a `String` if it contains valid Unicode data.
     *
     * On failure, ownership of the original `PathBuf` is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.PathBuf
     *
     * val pathBuf = PathBuf.from("foo")
     * val string = pathBuf.intoString()
     * check(string == Result.success("foo"))
     * ```
     */
    public open fun intoString(): Result<String, PathBuf> = this.intoOsString().intoString().mapErr(PathBuf::from)

    /**
     * Converts this `PathBuf` into a [boxed](Holder) [Path].
     */
    public open fun intoBoxedPath(): Path {
        val rw = mutPtrFromRustCast(Box.intoRaw(this.inner.intoBoxedOsStr()))
        return Box.fromRaw(rw)
    }

    /**
     * Invokes [capacity] on the underlying instance of [OsString].
     *
     * [capacity]: OsString.capacity
     */
    public open fun capacity(): Int = this.inner.capacity()

    /**
     * Invokes [clear] on the underlying instance of [OsString].
     *
     * [clear]: OsString.clear
     */
    public open fun clear() {
        this.inner.clear()
    }

    /**
     * Invokes [reserve] on the underlying instance of [OsString].
     *
     * [reserve]: OsString.reserve
     */
    public open fun reserve(additional: Int) {
        this.inner.reserve(additional)
    }

    /**
     * Invokes [tryReserve] on the underlying instance of [OsString].
     *
     * [tryReserve]: OsString.tryReserve
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.inner.tryReserve(additional)

    /**
     * Invokes [reserveExact] on the underlying instance of [OsString].
     *
     * [reserveExact]: OsString.reserveExact
     */
    public open fun reserveExact(additional: Int) {
        this.inner.reserveExact(additional)
    }

    /**
     * Invokes [tryReserveExact] on the underlying instance of [OsString].
     *
     * [tryReserveExact]: OsString.tryReserveExact
     */
    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> = this.inner.tryReserveExact(additional)

    /**
     * Invokes [shrinkToFit] on the underlying instance of [OsString].
     *
     * [shrinkToFit]: OsString.shrinkToFit
     */
    public open fun shrinkToFit() {
        this.inner.shrinkToFit()
    }

    /**
     * Invokes [shrinkTo] on the underlying instance of [OsString].
     *
     * [shrinkTo]: OsString.shrinkTo
     */
    public open fun shrinkTo(minCapacity: Int) {
        this.inner.shrinkTo(minCapacity)
    }

    public open fun clone(): PathBuf = PathBuf(inner = this.inner.clone())

    /**
     * Clones the contents of `source` into `self`.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids reallocation if possible.
     */
    public open fun cloneFrom(source: PathBuf) {
        this.inner = source.inner
    }

    public open fun fmt(formatter: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { formatter.append((this).toString()); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun deref(): Path = Path.new(this.inner)

    public open fun derefMut(): RustMutableReference<Path> = Path.fromInnerMut(this.inner)

    public open fun borrow(): Path = this.deref()

    public open fun eq(other: PathBuf): Boolean = (this.components() == other.components()).toUByte()

    public open fun eq(other: String): Boolean = (this.asPath() == other).toUByte()

    public open fun eqString(other: String): Boolean = (this.asPath() == other).toUByte()

    public override fun hashCode(): Int {
        var h = 0
        h = 31 * h + (this.asPath()).hashCode()
        return h
    }

    public open fun partialCmp(other: PathBuf): Ordering? = compareComponents(this.components(), other.components())

    public open fun cmp(other: PathBuf): Ordering = compareComponents(this.components(), other.components())

    public open fun asRef(): OsStr = this.inner

    public open fun asRefPath(): Path = this

    public open fun intoIter(): Iter = this.iter()

    public open fun eq(other: Path): Boolean = Path.eq(this, other)

    public open fun partialCmp(other: Path): Ordering? = Path.partialCmp(this, other)

    public open fun eqPath(other: Path): Boolean = Path.eq(this, other)

    public open fun partialCmpPath(other: Path): Ordering? = Path.partialCmp(this, other)

    public open fun eqCowPath(other: Path): Boolean = Path.eq(this, other)

    public open fun partialCmpCowPath(other: Path): Ordering? = Path.partialCmp(this, other)

    public open fun eq(other: OsStr): Boolean = Path.eq(this, other.asRef())

    public open fun partialCmp(other: OsStr): Ordering? = Path.partialCmp(this, other.asRef())

    public open fun eqOsStr(other: OsStr): Boolean = Path.eq(this, other.asRef())

    public open fun partialCmpOsStr(other: OsStr): Ordering? = Path.partialCmp(this, other.asRef())

    public open fun eqCowOsStr(other: OsStr): Boolean = Path.eq(this, other.asRef())

    public open fun partialCmpCowOsStr(other: OsStr): Ordering? = Path.partialCmp(this, other.asRef())

    public open fun eq(other: OsString): Boolean = Path.eq(this, other.asRef())

    public open fun partialCmp(other: OsString): Ordering? = Path.partialCmp(this, other.asRef())

    public companion object {
        /**
         * Allocates an empty `PathBuf`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.path.PathBuf
         *
         * val path = PathBuf.new()
         * ```
         */
        public fun new(): PathBuf = PathBuf(inner = OsString.new())

        /**
         * Creates a new `PathBuf` with a given capacity used to create the
         * internal [OsString]. See [withCapacity] defined on [OsString].
         *
         * # Examples
         *
         * ```kotlin
         * import std.path.PathBuf; val path = PathBuf.withCapacity(10); val capacity = path.capacity(); path.push(r"C:\"); assertEq(capacity, path.capacity());
         * ```
         *
         * [withCapacity]: OsString.withCapacity
         */
        public fun withCapacity(capacity: Int): PathBuf = PathBuf(inner = OsString.withCapacity(capacity))

        /**
         * Converts a <code>[Holder]&lt;[Path]&gt;</code> into a [PathBuf].
         *
         * This conversion does not allocate or copy memory.
         */
        public fun from(boxed: Path): PathBuf = boxed.intoPathBuf()

        /**
         * Converts an [OsString] into a [PathBuf].
         *
         * This conversion does not allocate or copy memory.
         */
        public fun from(s: OsString): PathBuf = PathBuf(inner = s)

        /**
         * Converts a [String] into a [PathBuf]
         *
         * This conversion does not allocate or copy memory.
         */
        public fun from(s: String): PathBuf = PathBuf.from(OsString.from(s))

        public fun fromStr(s: String): Result<PathBuf, Nothing> = Result.Ok<PathBuf, Nothing>(PathBuf.from(s))

        public fun default(): PathBuf = PathBuf.new()

        /**
         * Converts a clone-on-write pointer to an owned path.
         *
         * Converting from a `Cow.Owned` does not clone or allocate.
         */
        public fun fromCowPath(p: Path): PathBuf = p.intoOwned()
    }
}

public fun <A : Allocator> Path.clone(): Path = Box.cloneFromRefIn(Path(this.inner), Path.allocator(this).clone())

/**
 * Converts a borrowed [OsStr] to a [PathBuf].
 *
 * Allocates a [PathBuf] and copies the data into it.
 */
public fun fromPathBuf(s: OsStr): PathBuf = PathBuf.from(s.asRef().toOsString())

/**
 * Converts a [PathBuf] into an [OsString]
 *
 * This conversion does not allocate or copy memory.
 */
public fun fromOsString(pathBuf: PathBuf): OsString = pathBuf.inner

/**
 * Creates a new `PathBuf` from the [Path] elements of an iterator.
 *
 * This uses [`push`](Self.push) to add each element, so can be used to adjoin multiple path
 * [components](Components).
 *
 * # Examples
 * ```kotlin
 * val path = PathBuf.fromIter(listOf("/tmp", "foo", "bar"))
 * check(path == PathBuf.from("/tmp/foo/bar"))
 * ```
 *
 * See documentation for [`push`](Self.push) for more details on how the path is constructed.
 */
public fun <I : Iterable<Path>> fromIteratorFromIterPathBuf(iter: I): PathBuf {
    val buf = PathBuf.new()
    buf.extend(iter)
    return buf
}

/**
 * Extends `self` with [Path] elements from `iter`.
 *
 * This uses [`push`](Self.push) to add each element, so can be used to adjoin multiple path
 * [components](Components).
 *
 * # Examples
 * ```kotlin
 * var path = PathBuf.from("/tmp")
 * path.extend(listOf("foo", "bar", "file.txt"))
 * check(path == PathBuf.from("/tmp/foo/bar/file.txt"))
 * ```
 *
 * See documentation for [`push`](Self.push) for more details on how the path is constructed.
 */
public fun <I : Iterable<Path>> PathBuf.extend(iter: I) {
    iter.iterator().forEach({ p -> this.push(p.asRef()) })
}

public fun PathBuf.extendOne(p: Path) {
    this.push(p)
}

/**
 * Creates a clone-on-write pointer from a reference to
 * [Path].
 *
 * This conversion does not clone or allocate.
 */
public fun fromCowPath(s: Path): Path = s

/**
 * Creates a clone-on-write pointer from an owned
 * instance of [PathBuf].
 *
 * This conversion does not clone or allocate.
 */
public fun fromCowPath(s: PathBuf): Path = s

/**
 * Converts a [PathBuf] into an <code>[Arc]<[Path]></code> by moving the [PathBuf] data
 * into a new [Arc] buffer.
 */
public fun fromArcPath(s: PathBuf): Path {
    val arc: OsStr = Arc.from(s.intoOsString())
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
}

/**
 * Converts a [Path] into an [Arc] by copying the [Path] data into a new [Arc] buffer.
 */
public fun fromArcPath(s: Path): Path {
    val arc: OsStr = Arc.from(s.asOsStr())
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
}

/**
 * Converts a [Path] into an [Arc] by copying the [Path] data into a new [Arc] buffer.
 */
public fun fromArcPath(s: RustMutableReference<Path>): Path = Arc.from((s).read())

/**
 * Converts a [PathBuf] into an <code>[Rc]<[Path]></code> by moving the [PathBuf] data into
 * a new [Rc] buffer.
 */
public fun fromRcPath(s: PathBuf): Path {
    val rc: OsStr = Rc.from(s.intoOsString())
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

/**
 * Converts a [Path] into an [Rc] by copying the [Path] data into a new [Rc] buffer.
 */
public fun fromRcPath(s: Path): Path {
    val rc: OsStr = Rc.from(s.asOsStr())
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

/**
 * Converts a [Path] into an [Rc] by copying the [Path] data into a new [Rc] buffer.
 */
public fun fromRcPath(s: RustMutableReference<Path>): Path = Rc.from((s).read())

public fun String.eq(other: PathBuf): Boolean = (this == other.asPath()).toUByte()

public fun String.eqPathBuf(other: PathBuf): Boolean = (this == other.asPath()).toUByte()

// `Path.new` and `implementation CloneToUninit for Path` current implementation relies
// on `Path` being layout-compatible with `OsStr`.
// However, `Path` layout is considered an implementation detail and must not be relied upon.

/**
 * A slice of a path (akin to [str]).
 *
 * This type supports a number of operations for inspecting a path, including
 * breaking the path into its components (separated by `/` on Unix and by either
 * `/` or `\` on Windows), extracting the file name, determining whether the path
 * is absolute, and so on.
 *
 * This is an *unsized* type, meaning that it must always be used behind a
 * pointer like  or [Box]. For an owned version of this type,
 * see [PathBuf].
 *
 * More details about the overall approach can be found in
 * the [module documentation](self).
 *
 * # Examples
 *
 * ```kotlin
 * import std.ffi.OsStr
 * import std.path.Path
 *
 * // Note: this example does work on Windows
 * val path = Path.new("./foo/bar.txt")
 * val parent = path.parent()
 * check(parent == Path.new("./foo"))
 * val fileStem = path.fileStem()
 * check(fileStem == OsStr.new("bar"))
 * val extension = path.extension()
 * check(extension == OsStr.new("txt"))
 * ```
 */
public open class Path internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<OsStr> = UnresolvedTypeLayout(),
) : ToOwned, CloneToUninit, AsRef<OsStr> {
    public var inner: OsStr
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(inner: OsStr, fieldLayout: TypeLayout<OsStr> = UnresolvedTypeLayout()) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Path> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Path(ptr, layout) }, { it.inner })

    public open fun toOwned(): PathBuf = this.toPathBuf()

    public open fun cloneInto(target: PathBuf) {
        this.inner.cloneInto(target.inner)
    }

    // The following (private!) function reveals the byte encoding used for OsStr.
    public open fun asU8Slice(): List<UByte> = this.inner.asEncodedBytes()

    /**
     * Yields the underlying [OsStr] slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val osStr = Path.new("foo.txt").asOsStr()
     * check(osStr == OsStr.new("foo.txt"))
     * ```
     */
    public open fun asOsStr(): OsStr = this.inner

    /**
     * Yields a mutable reference to the underlying [OsStr] slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * var path = PathBuf.from("Foo.TXT")
     * check(path != Path.new("foo.txt"))
     * path.asMutOsStr().makeAsciiLowercase()
     * check(path == Path.new("foo.txt"))
     * ```
     */
    public open fun asMutOsStr(): RustMutableReference<OsStr> {
        return RustMutableReference(this.fieldStorage, this.fieldLayout)
    }

    /**
     * Yields a [str] slice if the `Path` is valid unicode.
     *
     * This conversion may entail doing a check for UTF-8 validity.
     * Note that validation is performed because non-UTF-8 strings are
     * perfectly valid for some OS.
     *
     * [str]: str
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("foo.txt")
     * check(path.toStr() == "foo.txt")
     * ```
     */
    public open fun toStr(): String? = this.inner.toStr()

    /**
     * Converts a `Path` to a [Cow.str].
     *
     * Any non-UTF-8 sequences are replaced with
     * [`U.Fffd.Replacement.Character`][U+FFFD].
     *
     * [U+FFFD]: char.ReplacementCharacter
     *
     * # Examples
     *
     * Calling `toStringLossy` on a `Path` with valid unicode:
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("foo.txt")
     * check(path.toStringLossy() == "foo.txt")
     * ```
     *
     * Had `path` contained invalid unicode, the `toStringLossy` call might
     * have returned `"fo�.txt"`.
     */
    public open fun toStringLossy(): String = this.inner.toStringLossy()

    /**
     * Converts a `Path` to an owned [PathBuf].
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * val pathBuf = Path.new("foo.txt").toPathBuf()
     * check(pathBuf == PathBuf.from("foo.txt"))
     * ```
     */
    public open fun toPathBuf(): PathBuf = PathBuf.from(this.inner.toOsString())

    /**
     * Returns `true` if the `Path` is absolute, i.e., if it is independent of
     * the current directory.
     *
     * * On Unix, a path is absolute if it starts with the root, so
     * `isAbsolute` and [hasRoot] are equivalent.
     *
     * * On Windows, a path is absolute if it has a prefix and starts with the
     * root: `c:\windows` is absolute, while `c: temp` and `\temp` are not.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check(!Path.new("foo.txt").isAbsolute())
     * ```
     *
     * [hasRoot]: Path.hasRoot
     */
    public open fun isAbsolute(): Boolean = io.github.kotlinmania.rustlang.std.sys.path.unix.isAbsolute(this)

    /**
     * Returns `true` if the `Path` is relative, i.e., not absolute.
     *
     * See [isAbsolute]s documentation for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check(Path.new("foo.txt").isRelative())
     * ```
     *
     * [isAbsolute]: Path.isAbsolute
     */
    public open fun isRelative(): Boolean = !this.isAbsolute()

    public open fun prefix(): Prefix? = this.components().prefix

    /**
     * Returns `true` if the `Path` has a root.
     *
     * * On Unix, a path has a root if it begins with `/`.
     *
     * * On Windows, a path has a root if it:
     *     * has no prefix and begins with a separator, e.g., `\windows`
     *     * has a prefix followed by a separator, e.g., `c:\windows` but not `c: windows`
     *     * has any non-disk prefix, e.g., `\\server\share`
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check(Path.new("/etc/passwd").hasRoot())
     * ```
     */
    public open fun hasRoot(): Boolean = this.components().hasRoot()

    /**
     * Returns the `Path` without its final component, if there is one.
     *
     * This means it returns `Some("")` for relative paths with one component.
     *
     * Returns [None] if the path terminates in a root or prefix, or if it's
     * the empty string.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/foo/bar")
     * val parent = path.parent().unwrap()
     * check(parent == Path.new("/foo"))
     * val grandParent = parent.parent().unwrap()
     * check(grandParent == Path.new("/"))
     * check(grandParent.parent() == null)
     * val relativePath = Path.new("foo/bar")
     * val parent1 = relativePath.parent()
     * check(parent1 == Path.new("foo"))
     * val grandParent1 = parent1.andThen(Path.parent)
     * check(grandParent1 == Path.new(""))
     * val greatGrandParent = grandParent1.andThen(Path.parent)
     * check(greatGrandParent == null)
     * ```
     */
    public open fun parent(): Path? {
        val comps = this.components()
        val comp = comps.nextBack()
        return comp?.let({ p -> when (p) {
            is Component.Normal -> {
                comps.asPath()
            }
            Component.CurDir -> {
                comps.asPath()
            }
            Component.ParentDir -> {
                comps.asPath()
            }
            else -> null
        } })
    }

    /**
     * Produces an iterator over `Path` and its ancestors.
     *
     * The iterator will yield the `Path` that is returned if the [parent] method is used zero
     * or more times. If the [parent] method returns [None], the iterator will do likewise.
     * The iterator will always yield at least one value, namely `Some(self)`. Next it will yield
     * `self.parent()`, `self.parent().andThen(Path.parent)` and so on.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * var ancestors = Path.new("/foo/bar").ancestors()
     * check(ancestors.next() == Path.new("/foo/bar"))
     * check(ancestors.next() == Path.new("/foo"))
     * check(ancestors.next() == Path.new("/"))
     * check(ancestors.next() == null)
     * var ancestors1 = Path.new("../foo/bar").ancestors()
     * check(ancestors1.next() == Path.new("../foo/bar"))
     * check(ancestors1.next() == Path.new("../foo"))
     * check(ancestors1.next() == Path.new(".."))
     * check(ancestors1.next() == Path.new(""))
     * check(ancestors1.next() == null)
     * ```
     *
     * [parent]: Path.parent
     */
    public open fun ancestors(): Ancestors = Ancestors(next = this)

    /**
     * Returns the final component of the `Path`, if there is one.
     *
     * If the path is a normal file, this is the file name. If it's the path of a directory, this
     * is the directory name.
     *
     * Returns [None] if the path terminates in `..`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.path.Path
     *
     * check(OsStr.new("bin") == Path.new("/usr/bin/").fileName())
     * check(OsStr.new("foo.txt") == Path.new("tmp/foo.txt").fileName())
     * check(OsStr.new("foo.txt") == Path.new("foo.txt/.").fileName())
     * check(OsStr.new("foo.txt") == Path.new("foo.txt/.//").fileName())
     * check(null == Path.new("foo.txt/..").fileName())
     * check(null == Path.new("/").fileName())
     * ```
     */
    public open fun fileName(): OsStr? =
        this.components().nextBack()?.let({ p -> when (p) {
            is Component.Normal -> {
                val (p1) = p
                p1
            }
            else -> null
        } })

    /**
     * Returns a path that, when joined onto `base`, yields `self`.
     *
     * # Errors
     *
     * If `base` is not a prefix of `self` (i.e., [startsWith]
     * returns `false`), returns [Err].
     *
     * [startsWith]: Path.startsWith
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * val path = Path.new("/test/haha/foo.txt")
     * check(path.takeIf { it.startsWith("/") }?.removePrefix("/") == Result.success(Path.new("test/haha/foo.txt")))
     * check(path.takeIf { it.startsWith("/test") }?.removePrefix("/test") == Result.success(Path.new("haha/foo.txt")))
     * check(path.takeIf { it.startsWith("/test/") }?.removePrefix("/test/") == Result.success(Path.new("haha/foo.txt")))
     * check(path.takeIf { it.startsWith("/test/haha/foo.txt") }?.removePrefix("/test/haha/foo.txt") == Result.success(Path.new("")))
     * check(path.takeIf { it.startsWith("/test/haha/foo.txt/") }?.removePrefix("/test/haha/foo.txt/") == Result.success(Path.new("")))
     * check(path.takeIf { it.startsWith("test") }?.removePrefix("test").isErr())
     * check(path.takeIf { it.startsWith("/te") }?.removePrefix("/te").isErr())
     * check(path.takeIf { it.startsWith("/haha") }?.removePrefix("/haha").isErr())
     * val prefix = PathBuf.from("/test/")
     * check(path.takeIf { it.startsWith(prefix) }?.removePrefix(prefix) == Result.success(Path.new("haha/foo.txt")))
     * ```
     */
    public open fun stripPrefix(base: Path): Result<Path, StripPrefixError> = this.stripPrefix(base)

    /**
     * Returns a path with the optional prefix removed.
     *
     * If `base` is not a prefix of `self` (i.e., [startsWith] returns `false`), returns the original path (`self`)
     *
     * [startsWith]: Path.startsWith
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/test/haha/foo.txt")
     * // Prefix present - remove it
     * check(path.trimPrefix("/") == Path.new("test/haha/foo.txt"))
     * check(path.trimPrefix("/test") == Path.new("haha/foo.txt"))
     * check(path.trimPrefix("/test/") == Path.new("haha/foo.txt"))
     * check(path.trimPrefix("/test/haha/foo.txt") == Path.new(""))
     * check(path.trimPrefix("/test/haha/foo.txt/") == Path.new(""))
     * // Prefix absent - return original
     * check(path.trimPrefix("test") == path)
     * check(path.trimPrefix("/te") == path)
     * check(path.trimPrefix("/haha") == path)
     * ```
     */
    public open fun trimPrefix(base: Path): Path = this.stripPrefix(base).unwrapOr(this)

    /**
     * Determines whether `base` is a prefix of `self`.
     *
     * Only considers whole path components to match.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/etc/passwd")
     * check(path.startsWith("/etc"))
     * check(path.startsWith("/etc/"))
     * check(path.startsWith("/etc/passwd"))
     * check(path.startsWith("/etc/passwd/"))
     * // extra slash is okay
     * check(path.startsWith("/etc/passwd///"))
     * // multiple extra slashes are okay
     * check(!path.startsWith("/e"))
     * check(!path.startsWith("/etc/passwd.txt"))
     * check(!Path.new("/etc/foo.rs").startsWith("/etc/foo"))
     * ```
     */
    public open fun startsWith(base: Path): Boolean = this.startsWith(base)

    /**
     * Determines whether `child` is a suffix of `self`.
     *
     * Only considers whole path components to match.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/etc/resolv.conf")
     * check(path.endsWith("resolv.conf"))
     * check(path.endsWith("etc/resolv.conf"))
     * check(path.endsWith("/etc/resolv.conf"))
     * check(!path.endsWith("/resolv.conf"))
     * check(!path.endsWith("conf"))
     * // use .extension() instead
     * ```
     */
    public open fun endsWith(child: Path): Boolean = this.endsWith(child)

    /**
     * Checks whether the `Path` is empty.
     *
     * Passing an empty path to most OS filesystem APIs will always result in an error.
     *
     * [Pushing][PathBuf.push] an empty path to an existing path will append a directory separator unless it already ends with a separator or the existing path is itself empty.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("")
     * check(path.isEmpty())
     * val path1 = Path.new("foo")
     * check(!path1.isEmpty())
     * val path2 = Path.new(".")
     * check(!path2.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.asOsStr().isEmpty()

    /**
     * Extracts the stem (non-extension) portion of [this.fileName].
     *
     * [this.fileName]: Path.fileName
     *
     * The stem is:
     *
     * * [None], if there is no file name;
     * * The entire file name if there is no embedded `.`;
     * * The entire file name if the file name begins with `.` and has no other `.`s within;
     * * Otherwise, the portion of the file name before the final `.`
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check("foo" == Path.new("foo.rs").fileStem().unwrap())
     * check("foo.tar" == Path.new("foo.tar.gz").fileStem().unwrap())
     * ```
     *
     * # See Also
     * This method is similar to [Path.filePrefix], which extracts the portion of the file name
     * before the *first* `.`
     *
     * [Path.filePrefix]: Path.filePrefix
     */
    public open fun fileStem(): OsStr? = this.fileName()?.let(::rsplitFileAtDot)?.let({ (before, after) -> before.or(after) })

    /**
     * Extracts the prefix of [this.fileName].
     *
     * The prefix is:
     *
     * * [None], if there is no file name;
     * * The entire file name if there is no embedded `.`;
     * * The portion of the file name before the first non-beginning `.`;
     * * The entire file name if the file name begins with `.` and has no other `.`s within;
     * * The portion of the file name before the second `.` if the file name begins with `.`
     *
     * [this.fileName]: Path.fileName
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check("foo" == Path.new("foo.rs").filePrefix().unwrap())
     * check("foo" == Path.new("foo.tar.gz").filePrefix().unwrap())
     * check(".config" == Path.new(".config").filePrefix().unwrap())
     * check(".config" == Path.new(".config.toml").filePrefix().unwrap())
     * ```
     *
     * # See Also
     * This method is similar to [Path.fileStem], which extracts the portion of the file name
     * before the *last* `.`
     *
     * [Path.fileStem]: Path.fileStem
     */
    public open fun filePrefix(): OsStr? = this.fileName()?.let(::splitFileAtDot)?.let({ (before, after) -> before })

    /**
     * Extracts the extension (without the leading dot) of [this.fileName], if possible.
     *
     * The extension is:
     *
     * * [None], if there is no file name;
     * * [None], if there is no embedded `.`;
     * * [None], if the file name begins with `.` and has no other `.`s within;
     * * Otherwise, the portion of the file name after the final `.`
     *
     * [this.fileName]: Path.fileName
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check("rs" == Path.new("foo.rs").extension().unwrap())
     * check("gz" == Path.new("foo.tar.gz").extension().unwrap())
     * ```
     */
    public open fun extension(): OsStr? = this.fileName()?.let(::rsplitFileAtDot)?.let({ (before, after) -> before.and(after) })

    /**
     * Checks whether the path ends in a trailing [separator](MAIN_SEPARATOR).
     *
     * This is generally done to ensure that a path is treated as a directory, not a file,
     * although it does not actually guarantee that such a path is a directory on the underlying
     * file system.
     *
     * Despite this behavior, two paths are still considered the same in Rust whether they have a
     * trailing separator or not.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * @feature(pathTrailingSep)
     * public fun doctestEntry() {
     *     check(Path.new("dir/").hasTrailingSep())
     *     check(!Path.new("file.rs").hasTrailingSep())
     * }
     * ```
     */
    public open fun hasTrailingSep(): Boolean {
        val comps = this.components()
        return this.asOsStr().asEncodedBytes().last().isSomeAnd({ b -> comps.isSepByte(b) })
    }

    /**
     * Ensures that a path has a trailing [separator](MAIN_SEPARATOR),
     * allocating a [PathBuf] if necessary.
     *
     * The resulting path will return true for [`hasTrailingSep`](Self.hasTrailingSep).
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.path.Path
     *
     * @feature(pathTrailingSep)
     * public fun doctestEntry() {
     *     check(Path.new("dir//").withTrailingSep().asOsStr() == OsStr.new("dir//"))
     *     check(Path.new("dir/").withTrailingSep().asOsStr() == OsStr.new("dir/"))
     *     check(!Path.new("dir").hasTrailingSep())
     *     check(Path.new("dir").withTrailingSep().hasTrailingSep())
     * }
     * ```
     */
    public open fun withTrailingSep(): Path =
        if (this.hasTrailingSep()) {
            this
        } else {
            this.join("")
        }

    /**
     * Trims a trailing [separator](MAIN_SEPARATOR) from a path, if possible.
     *
     * The resulting path will return false for [`hasTrailingSep`](Self.hasTrailingSep) for
     * most paths.
     *
     * Some paths, like `/`, cannot be trimmed in this way.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.path.Path
     *
     * @feature(pathTrailingSep)
     * public fun doctestEntry() {
     *     check(Path.new("dir//").trimTrailingSep().asOsStr() == OsStr.new("dir"))
     *     check(Path.new("dir/").trimTrailingSep().asOsStr() == OsStr.new("dir"))
     *     check(Path.new("dir").trimTrailingSep().asOsStr() == OsStr.new("dir"))
     *     check(Path.new("/").trimTrailingSep().asOsStr() == OsStr.new("/"))
     *     check(Path.new("//").trimTrailingSep().asOsStr() == OsStr.new("//"))
     * }
     * ```
     */
    public open fun trimTrailingSep(): Path {
        val comps = this.components()
        return if (this.hasTrailingSep() && (!this.hasRoot() || this.parent() != null)) {
            var bytes = this.inner.asEncodedBytes()
            while (bytes.splitLast() != null && comps.isSepByte(last)) {
                bytes = init
            }
            // SAFETY: Trimming trailing ASCII bytes will retain the validity of the string.
            Path.new(OsStr.fromEncodedBytesUnchecked(bytes))
        } else {
            this
        }
    }

    /**
     * Creates an owned [PathBuf] with `path` adjoined to `self`.
     *
     * If `path` is absolute, it replaces the current path.
     *
     * On Windows:
     *
     * * if `path` has a root but no prefix (e.g., `\windows`), it
     *   replaces and returns everything except for the prefix (if any) of `self`.
     * * if `path` has a prefix but no root, `self` is ignored and `path` is returned.
     * * if `self` has a verbatim prefix (e.g. `\\?\C:\windows`)
     *   and `path` is not empty, the new path is normalized: all references
     *   to `.` and `..` are removed.
     *
     * See [PathBuf.push] for more details on what it means to adjoin a path.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * check(Path.new("/etc").join("passwd") == PathBuf.from("/etc/passwd"))
     * check(Path.new("/etc").join("/bin/sh") == PathBuf.from("/bin/sh"))
     * ```
     */
    public open fun join(path: Path): PathBuf = this.join(path)

    /**
     * Creates an owned [PathBuf] like `self` but with the given file name.
     *
     * See [PathBuf.setFileName] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * val path = Path.new("/tmp/foo.png")
     * check(path.withFileName("bar") == PathBuf.from("/tmp/bar"))
     * check(path.withFileName("bar.txt") == PathBuf.from("/tmp/bar.txt"))
     * val path1 = Path.new("/tmp")
     * check(path1.withFileName("var") == PathBuf.from("/var"))
     * ```
     */
    public open fun withFileName(fileName: OsStr): PathBuf = this.withFileName(fileName)

    /**
     * Creates an owned [PathBuf] like `self` but with the given extension.
     *
     * See [PathBuf.setExtension] for more details.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("foo.rs")
     * check(path.withExtension("txt") == Path.new("foo.txt"))
     * check(path.withExtension("") == Path.new("foo"))
     * ```
     *
     * Handling multiple extensions:
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("foo.tar.gz")
     * check(path.withExtension("xz") == Path.new("foo.tar.xz"))
     * check(path.withExtension("").withExtension("txt") == Path.new("foo.txt"))
     * ```
     *
     * Adding an extension where one did not exist:
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("foo")
     * check(path.withExtension("rs") == Path.new("foo.rs"))
     * ```
     */
    public open fun withExtension(extension: OsStr): PathBuf = this.withExtension(extension)

    /**
     * Creates an owned [PathBuf] like `self` but with the extension added.
     *
     * See [PathBuf.addExtension] for more details. The return value of
     * [PathBuf.addExtension] is ignored, which means no extension
     * will be added to paths with no [Path.fileName].
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * val path = Path.new("foo.rs")
     * check(path.withAddedExtension("txt") == PathBuf.from("foo.rs.txt"))
     * val path1 = Path.new("foo.tar.gz")
     * check(path1.withAddedExtension("") == PathBuf.from("foo.tar.gz"))
     * check(path1.withAddedExtension("xz") == PathBuf.from("foo.tar.gz.xz"))
     * check(path1.withAddedExtension("").withAddedExtension("txt") == PathBuf.from("foo.tar.gz.txt"))
     * val path2 = Path.new("/")
     * check(path2.withAddedExtension("gz") == PathBuf.from("/"))
     * val path3 = Path.new("/dir/")
     * check(path3.withAddedExtension("gz") == PathBuf.from("/dir.gz"))
     * val path4 = Path.new("/dir/..")
     * check(path4.withAddedExtension("gz") == PathBuf.from("/dir/.."))
     * ```
     */
    public open fun withAddedExtension(extension: OsStr): PathBuf {
        val newPath = this.toPathBuf()
        newPath.addExtension(extension)
        return newPath
    }

    /**
     * Produces an iterator over the [Component]s of the path.
     *
     * When parsing the path, there is a small amount of normalization:
     *
     * * Repeated separators are ignored, so `a / b` and `a` both have
     *   `a` and `b` as components.
     *
     * * Occurrences of `.` are normalized away, except if they are at the
     *   beginning of the path. For example, `a /./ b`, `a / b /`, `a / b /.` and
     *   `a / b` all have `a` and `b` as components, but `./ a / b` starts with
     *   an additional [CurDir] component.
     *
     * * Trailing separators are normalized away, so `/ a / b` and `/ a / b /` are equivalent.
     *
     * Note that no other normalization takes place; in particular, `a / c`
     * and `a / b / .. / c` are distinct, to account for the possibility that `b`
     * is a symbolic link (so its parent isn't `a`).
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.path.Component
     * import std.path.Path
     *
     * var components = Path.new("/tmp/foo.txt").components()
     * check(components.next() == Component.RootDir)
     * check(components.next() == Component.Normal(OsStr.new("tmp")))
     * check(components.next() == Component.Normal(OsStr.new("foo.txt")))
     * check(components.next() == null)
     * ```
     *
     * [CurDir]: Component.CurDir
     */
    public open fun components(): Components {
        val prefix = parsePrefix(this.asOsStr())
        return Components(path = this.asU8Slice(), prefix = prefix, hasPhysicalRoot = hasPhysicalRoot(this.asU8Slice(), prefix), front = if (HAS_PREFIXES) {
            State.Prefix
        } else {
            State.StartDir
        }, back = State.Body)
    }

    /**
     * Produces an iterator over the path's components viewed as [OsStr]
     * slices.
     *
     * For more information about the particulars of how the path is separated
     * into components, see [components].
     *
     * [components]: Path.components
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.path.Path
     *
     * var it = Path.new("/tmp/foo.txt").iter()
     * check(it.next() == OsStr.new(path.MAIN_SEPARATOR.toString()))
     * check(it.next() == OsStr.new("tmp"))
     * check(it.next() == OsStr.new("foo.txt"))
     * check(it.next() == null)
     * ```
     */
    public open fun iter(): Iter = Iter(inner = this.components())

    /**
     * Returns an object that implements [Display] for safely printing paths
     * that may contain non-Unicode data. This may perform lossy conversion,
     * depending on the platform.  If you would like an implementation which
     * escapes the path please use [Debug] instead.
     *
     * [Display]: fmt.Display
     * [Debug]: fmt.Debug
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/tmp/foo.rs")
     * println("${path.display()}")
     * ```
     */
    public open fun display(): Display = Display(inner = this.inner.display())

    /**
     * Returns the same path as `Path`.
     *
     * This method is redundant when used directly on `Path`, but
     * it helps dereferencing other `PathBuf`-like types to `Path`s,
     * for example references to `Holder<Path>` or `Arc<Path>`.
     */
    public open fun asPath(): Path = this

    /**
     * Queries the file system to get information about a file, directory, etc.
     *
     * This function will traverse symbolic links to query information about the
     * destination file.
     *
     * This is an alias to [fs.metadata].
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/Minas/tirith")
     * val metadata = path.metadata().expect("the path should point to an existing file or directory")
     * println("${metadata.fileType()}")
     * ```
     */
    public open fun metadata(): Result<io.github.kotlinmania.rustlang.std.fs.Metadata, io.github.kotlinmania.rustlang.core.io.error.Error> = io.github.kotlinmania.rustlang.std.fs.metadata(this)

    /**
     * Queries the metadata about a file without following symlinks.
     *
     * This is an alias to [fs.symlinkMetadata].
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/Minas/tirith")
     * val metadata = path.symlinkMetadata().expect("the path should exist")
     * println("${metadata.fileType()}")
     * ```
     */
    public open fun symlinkMetadata(): Result<io.github.kotlinmania.rustlang.std.fs.Metadata, io.github.kotlinmania.rustlang.core.io.error.Error> = io.github.kotlinmania.rustlang.std.fs.symlinkMetadata(this)

    /**
     * Returns the canonical, absolute form of the path with all intermediate
     * components normalized and symbolic links resolved.
     *
     * This is an alias to [fs.canonicalize].
     *
     * # Errors
     *
     * This method will return an error in the following situations, but is not
     * limited to just these cases:
     *
     * * `path` does not exist.
     * * A non-final component in path is not a directory.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.path.PathBuf
     *
     * val path = Path.new("/foo/test/../test/bar.rs")
     * check(path.canonicalize().unwrap() == PathBuf.from("/foo/test/bar.rs"))
     * ```
     */
    public open fun canonicalize(): Result<PathBuf, io.github.kotlinmania.rustlang.core.io.error.Error> = io.github.kotlinmania.rustlang.std.fs.canonicalize(this)

    /**
     * Makes the path absolute without accessing the filesystem.
     *
     * This is an alias to [`path.absolute`](absolute).
     *
     * # Errors
     *
     * This function may return an error in the following situations:
     *
     * * If the path is syntactically invalid; in particular, if it is empty.
     * * If getting the [current directory][env.currentDir] fails.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * @feature(pathAbsoluteMethod)
     * public fun doctestEntry(): Result<Unit> {
     *     val path = Path.new("foo/./bar")
     *     val absolute = path.absolute().getOrElse { return Result.failure(it) }
     *     check(absolute.isAbsolute())
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun absolute(): Result<PathBuf, io.github.kotlinmania.rustlang.core.io.error.Error> = absolute(this)

    /**
     * Normalize a path, including `..` without traversing the filesystem.
     *
     * Returns an error if normalization would leave leading `..` components.
     *
     * <div class="warning">
     *
     * This function always resolves `..` to the "lexical" parent.
     * That is "a/b/../c" will always resolve to `a / c` which can change the meaning of the path.
     * In particular, `a / c` and `a / b / .. / c` are distinct on many systems because `b` may be a symbolic link, so its parent isn't `a`.
     *
     * </div>
     *
     * On Windows this will convert all `/` to `\` unless a [verbatim](Prefix.isVerbatim()) path is given.
     *
     * [`path.absolute`](absolute) is an alternative that preserves `..`.
     * Or [Path.canonicalize] can be used to resolve any `..` by querying the filesystem.
     */
    public open fun normalizeLexically(): Result<PathBuf, NormalizeError> {
        val lexical = PathBuf.new()
        val iter = this.components().peekable()
        // Find the root, if any, and add it to the lexical path.
        // Here we treat the Windows path "C:\" as a single "root" even though
        // `components` splits it into two: (Prefix, RootDir).
        val root =
            when (val matchVal = iter.peek()) {
                Component.ParentDir -> { return Result.Err<PathBuf, NormalizeError>(NormalizeError()) }
                Component.RootDir -> {
                    val p = matchVal
                    lexical.push(p)
                    iter.next()
                    lexical.asOsStr().len()
                }
                Component.CurDir -> {
                    val p1 = matchVal
                    lexical.push(p1)
                    iter.next()
                    lexical.asOsStr().len()
                }
                is Component.Prefix -> {
                    val (prefix) = matchVal as Component.Prefix
                    lexical.push(prefix.asOsStr())
                    iter.next()
                    when (val matchVal1 = iter.peek()) {
                        is Some<*> -> {
                            val p2 = matchVal1
                            lexical.push(p2)
                            iter.next()
                        }
                        else -> {}
                    }
                    lexical.asOsStr().len()
                }
                null -> { return Result.Ok<PathBuf, NormalizeError>(PathBuf.new()) }
                is Component.Normal -> 0
            }
        for (component in iter) {
            when (component) {
                Component.RootDir -> error("unreachable")
                is Component.Prefix -> { return Result.Err<PathBuf, NormalizeError>(NormalizeError()) }
                Component.CurDir -> { continue }
                Component.ParentDir -> {
                    // It's an error if ParentDir causes us to go above the "root".
                    if (lexical.asOsStr().len() == root) {
                        return Result.Err<PathBuf, NormalizeError>(NormalizeError())
                    } else {
                        lexical.pop()
                    }
                }
                is Component.Normal -> {
                    val (path) = component
                    lexical.push(path)
                }
            }
        }
        return Result.Ok<PathBuf, NormalizeError>(lexical)
    }

    /**
     * Reads a symbolic link, returning the file that the link points to.
     *
     * This is an alias to [fs.readLink].
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/laputa/skyCastle.rs")
     * val pathLink = path.readLink().expect("the path should be an existing symbolic link")
     * ```
     */
    public open fun readLink(): Result<PathBuf, io.github.kotlinmania.rustlang.core.io.error.Error> = io.github.kotlinmania.rustlang.std.fs.readLink(this)

    /**
     * Returns an iterator over the entries within a directory.
     *
     * The iterator will yield instances of <code>[io.Result]<[fs.DirEntry]></code>. New
     * errors may be encountered after an iterator is initially constructed.
     *
     * This is an alias to [fs.readDir].
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * val path = Path.new("/laputa")
     * for (entry in path.readDir().expect("the path should point to an existing directory")) {
     *     if (entry is Ok) {
     *         val (entry1) = entry
     *         println("${entry1.path()}")
     *     }
     * }
     * ```
     */
    public open fun readDir(): Result<ReadDir, io.github.kotlinmania.rustlang.core.io.error.Error> = io.github.kotlinmania.rustlang.std.fs.readDir(this)

    /**
     * Returns `true` if the path points at an existing entity.
     *
     * Warning: this method may be error-prone, consider using [tryExists] instead!
     * It also has a risk of introducing time-of-check to time-of-use ([TOCTOU]) bugs.
     *
     * This function will traverse symbolic links to query information about the
     * destination file.
     *
     * If you cannot access the metadata of the file, e.g. because of a
     * permission error or broken symbolic links, this will return `false`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check(!Path.new("doesNotExist.txt").exists())
     * ```
     *
     * # See Also
     *
     * This is a convenience function that coerces errors to false. If you want to
     * check errors, call [Path.tryExists].
     *
     * [tryExists]: Self.tryExists
     * [TOCTOU]: fs#time-of-check-to-time-of-use-toctou
     */
    public open fun exists(): Boolean = metadata(this).isOk()

    /**
     * Returns `Ok(true)` if the path points at an existing entity.
     *
     * This function will traverse symbolic links to query information about the
     * destination file. In case of broken symbolic links this will return `Ok(false)`.
     *
     * [Path.exists] only checks whether or not a path was both found and readable. By
     * contrast, `tryExists` will return `Ok(true)` or `Ok(false)`, respectively, if the path
     * was _verified_ to exist or not exist. If its existence can neither be confirmed nor
     * denied, it will propagate an `Err(_)` instead. This can be the case if e.g. listing
     * permission is denied on one of the parent directories.
     *
     * Note that while this avoids some pitfalls of the `exists()` method, it still can not
     * prevent time-of-check to time-of-use ([TOCTOU]) bugs. You should only use it in scenarios
     * where those bugs are not an issue.
     *
     * This is an alias for [`std.fs.exists`](fs.exists).
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check(!Path.new("doesNotExist.txt").tryExists().expect("the path's existence should be verifiable"))
     * check(Path.new("/root/secretFile.txt").tryExists().isErr())
     * ```
     *
     * [TOCTOU]: fs#time-of-check-to-time-of-use-toctou
     * [exists]: Self.exists
     */
    public open fun tryExists(): Result<Boolean, io.github.kotlinmania.rustlang.core.io.error.Error> = exists(this)

    /**
     * Returns `true` if the path exists on disk and is pointing at a regular file.
     *
     * This function will traverse symbolic links to query information about the
     * destination file.
     *
     * If you cannot access the metadata of the file, e.g. because of a
     * permission error or broken symbolic links, this will return `false`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check(Path.new("./isADirectory/").isFile() == false)
     * check(Path.new("aFile.txt").isFile() == true)
     * ```
     *
     * # See Also
     *
     * This is a convenience function that coerces errors to false. If you want to
     * check errors, call [fs.metadata] and handle its [Result]. Then call
     * [fs.Metadata.isFile] if it was [Ok].
     *
     * When the goal is simply to read from (or write to) the source, the most
     * reliable way to test the source can be read (or written to) is to open
     * it. Only using `isFile` can break workflows like `diff<(progA)` on
     * a Unix-like system for example. See [fs.File.open] or
     * [fs.OpenOptions.open] for more information.
     */
    public open fun isFile(): Boolean = metadata(this).map({ m -> m.isFile() }).unwrapOr(false)

    /**
     * Returns `true` if the path exists on disk and is pointing at a directory.
     *
     * This function will traverse symbolic links to query information about the
     * destination file.
     *
     * If you cannot access the metadata of the file, e.g. because of a
     * permission error or broken symbolic links, this will return `false`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     *
     * check(Path.new("./isADirectory/").isDir() == true)
     * check(Path.new("aFile.txt").isDir() == false)
     * ```
     *
     * # See Also
     *
     * This is a convenience function that coerces errors to false. If you want to
     * check errors, call [fs.metadata] and handle its [Result]. Then call
     * [fs.Metadata.isDir] if it was [Ok].
     */
    public open fun isDir(): Boolean = metadata(this).map({ m -> m.isDir() }).unwrapOr(false)

    /**
     * Returns `true` if the path exists on disk and is pointing at a symbolic link.
     *
     * This function will not traverse symbolic links.
     * In case of a broken symbolic link this will also return true.
     *
     * If you cannot access the directory containing the file, e.g., because of a
     * permission error, this will return false.
     *
     * # Examples
     *
     * ```kotlin
     * import std.os.unix.fs.symlink
     * import std.path.Path
     *
     * val linkPath = Path.new("link")
     * symlink("/originDoesNotExist/", linkPath).unwrap()
     * check(linkPath.isSymlink() == true)
     * check(linkPath.exists() == false)
     * ```
     *
     * # See Also
     *
     * This is a convenience function that coerces errors to false. If you want to
     * check errors, call [fs.symlinkMetadata] and handle its [Result]. Then call
     * [fs.Metadata.isSymlink] if it was [Ok].
     */
    public open fun isSymlink(): Boolean = symlinkMetadata(this).map({ m -> m.isSymlink() }).unwrapOr(false)

    /**
     * Converts a [`Box.Path`](Holder) into a [PathBuf] without copying or
     * allocating.
     */
    public open fun intoPathBuf(): PathBuf {
        val rw = mutPtrFromRustCast(Box.intoRaw(this))
        val inner = Box.fromRaw(rw)
        return PathBuf(inner = OsString.from(inner))
    }

    public open fun cloneToUninit(dst: MutPtr) {
        // SAFETY: Path is just a transparent wrapper around OsStr
        this.inner.cloneToUninit(dst)
    }

    public open fun asRef(): OsStr = this.inner

    public open fun fmt(formatter: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { formatter.append((this.inner).toString()); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun eq(other: Path): Boolean = (this.components() == other.components()).toUByte()

    public open fun eq(other: String): Boolean {
        val other1: OsStr = other
        return (this == other1).toUByte()
    }

    public open fun eqString(other: String): Boolean = (this == other).toUByte()

    public override fun hashCode(): Int {
        var h = 0
        val bytes = this.asU8Slice()
        val (prefixLen, verbatim) =
            when (val matchVal = parsePrefix(this.inner)) {
                null -> Pair(0, false)
                else -> {
                    val prefix = matchVal
                    h = 31 * h + (prefix).hashCode()
                    Pair(prefix.len(), prefix.isVerbatim())
                }
            }
        val bytes1 = ((bytes).subList(prefixLen, (bytes).size))
        var componentStart = 0
        // track some extra state to avoid prefix collisions.
        // ["foo", "bar"] and ["foobar"], will have the same payload bytes
        // but result in different chunkBits
        var chunkBits: Int = 0
        for (i in 0 until bytes1.len()) {
            val isSep =
                if (verbatim) {
                    isVerbatimSep(bytes1[i])
                } else {
                    isSepByte(bytes1[i])
                }
            if (isSep) {
                if (i > componentStart) {
                    val toHash = bytes1[componentStart until i]
                    chunkBits = (chunkBits + toHash.len().toInt())
                    chunkBits = chunkBits.rotateRight(2u)
                    h.write(toHash)
                }
                // skip over separator and optionally a following CurDir item
                // since components() would normalize these away.
                componentStart = i + 1
                val tail = bytes1[componentStart until bytes1.size]
                if (!verbatim) {
                    componentStart +=
                        when {
                            tail.size == 1 && tail[0] == 46u.toUByte() -> 1
                            tail.size >= 2 && tail[0] == 46u.toUByte() && isSepByte(tail[1]) -> {
                                val sep = tail[1]
                                1
                            }
                            else -> 0
                        }
                }
            }
        }
        if (componentStart < bytes1.len()) {
            val toHash1 = bytes1[componentStart until bytes1.size]
            chunkBits = (chunkBits + toHash1.len().toInt())
            chunkBits = chunkBits.rotateRight(2u)
            h.write(toHash1)
        }
        h.writeUsize(chunkBits)
        return h
    }

    public open fun partialCmp(other: Path): Ordering? = compareComponents(this.components(), other.components())

    public open fun cmp(other: Path): Ordering = compareComponents(this.components(), other.components())

    public open fun asRefPath(): Path = this

    public open fun intoIter(): Iter = this.iter()

    public open fun eq(other: PathBuf): Boolean = Path.eq(this, other)

    public open fun partialCmp(other: PathBuf): Ordering? = Path.partialCmp(this, other)

    public open fun eqCowPath(other: Path): Boolean = Path.eq(this, other)

    public open fun partialCmpCowPath(other: Path): Ordering? = Path.partialCmp(this, other)

    public open fun eq(other: OsStr): Boolean = Path.eq(this, other.asRef())

    public open fun partialCmp(other: OsStr): Ordering? = Path.partialCmp(this, other.asRef())

    public open fun eqOsStr(other: OsStr): Boolean = Path.eq(this, other.asRef())

    public open fun partialCmpOsStr(other: OsStr): Ordering? = Path.partialCmp(this, other.asRef())

    public open fun eqCowOsStr(other: OsStr): Boolean = Path.eq(this, other.asRef())

    public open fun partialCmpCowOsStr(other: OsStr): Ordering? = Path.partialCmp(this, other.asRef())

    public open fun eq(other: OsString): Boolean = Path.eq(this, other.asRef())

    public open fun partialCmp(other: OsString): Ordering? = Path.partialCmp(this, other.asRef())

    public companion object {
        public operator fun invoke(inner: OsStr, fieldLayout: TypeLayout<OsStr> = UnresolvedTypeLayout()): Path = Path(inner, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<OsStr> = UnresolvedTypeLayout()): Path = Path(fieldStorage, fieldLayout)

        /**
         * Creates a boxed [Path] from a reference.
         *
         * This will allocate and clone `path` to it.
         */
        public fun from(path: Path): Path = Box.cloneFromRef(path)

        /**
         * Creates a boxed [Path] from a reference.
         *
         * This will allocate and clone `path` to it.
         */
        public fun from(path: RustMutableReference<Path>): Path = Path.from((path).read())

        /**
         * Creates a boxed [Path] from a clone-on-write pointer.
         *
         * Converting from a `Cow.Owned` does not clone or allocate.
         */
        public fun fromCowPath(cow: Path): Path =
            when (cow) {
                is Cow.Borrowed<*, *> -> {
                    val (path) = cow
                    Box.from(path)
                }
                is Cow.Owned -> {
                    val (path1) = cow
                    Box.from(path1)
                }
            }

        /**
         * Converts a [PathBuf] into a <code>[Holder]&lt;[Path]&gt;</code>.
         *
         * This conversion currently should not allocate memory,
         * but this behavior is not guaranteed on all platforms or in all future versions.
         */
        public fun from(p: PathBuf): Path = p.intoBoxedPath()

        // The following (private!) function allows construction of a path from a u8
        // slice, which is only safe when it is known to follow the OsStr encoding.
        public fun fromU8Slice(s: List<UByte>): Path = Path.new(OsStr.fromEncodedBytesUnchecked(s))

        /**
         * Directly wraps a string slice as a `Path` slice.
         *
         * This is a cost-free conversion.
         *
         * # Examples
         *
         * ```kotlin
         * import std.path.Path
         *
         * Path.new("foo.txt")
         * ```
         *
         * You can create `Path`s from `String`s, or even other `Path`s:
         *
         * ```kotlin
         * import std.path.Path
         *
         * val string = "foo.txt"
         * val fromString = Path.new(string)
         * val fromPath = Path.new(fromString)
         * check(fromString == fromPath)
         * ```
         */
        public fun new(s: OsStr): Path = (UnresolvedTypeLayout<Path>()).readNative((((constPtrFromRustCast(s.asRef())).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

        public fun fromInnerMut(inner: OsStr): RustMutableReference<Path> {
            // SAFETY: Path is just a wrapper around OsStr,
            // therefore converting OsStr to Path is safe.
            return RustMutableReference(MutPtr((((mutPtrFromRustCast(inner)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<Path>())
        }
    }
}

/**
 * An error returned from [Path.stripPrefix] if the prefix was not found.
 *
 * This `class` is created by the [stripPrefix] method on [Path].
 * See its documentation for more.
 *
 * [stripPrefix]: Path.stripPrefix
 */
public open class StripPrefixError(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): StripPrefixError = StripPrefixError(value)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("prefix not found"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<StripPrefixError> =
            object : TypeLayout<StripPrefixError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): StripPrefixError =
                    StripPrefixError(
                        (PrimitiveLayouts.UNIT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: StripPrefixError) {
                    (PrimitiveLayouts.UNIT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StripPrefixError =
                    StripPrefixError(
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StripPrefixError) {
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * An error returned from [Path.normalizeLexically] if a `..` parent reference
 * would escape the path.
 */
public open class NormalizeError {
    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("parent reference `..` points outside of base directory"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public fun typeLayout(): TypeLayout<NormalizeError> =
            object : TypeLayout<NormalizeError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): NormalizeError =
                    NormalizeError()
                override fun writeNative(addr: Long, value: NormalizeError) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): NormalizeError =
                    NormalizeError()
                override fun writeBytes(buf: ByteArray, offset: Int, value: NormalizeError) {
                }
            }
    }
}

/**
 * Helper struct for safely printing paths with [format] and `()`.
 *
 * A [Path] might contain non-Unicode data. This `class` implements the
 * [Display] trait in a way that mitigates that. It is created by the
 * [`display`](Path.display) method on [Path]. This may perform lossy
 * conversion, depending on the platform. If you would like an implementation
 * which escapes the path please use [Debug] instead.
 *
 * # Examples
 *
 * ```kotlin
 * import std.path.Path
 *
 * val path = Path.new("/tmp/foo.rs")
 * println("${path.display()}")
 * ```
 *
 * [Display]: fmt.Display
 */
public open class Display(
    public var inner: io.github.kotlinmania.rustlang.std.ffi.osstr.Display,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append((this.inner).toString()); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append((this.inner).toString()); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object
}

public fun String.eq(other: Path): Boolean = (other == this).toUByte()

public fun String.eqPath(other: Path): Boolean = (this == other).toUByte()

public fun OsStr.asRef(): Path = Path.new(this)

public fun OsStr.asRefPath(): Path = Path.new(this)

public fun OsString.asRef(): Path = Path.new(this)

public fun String.asRef(): Path = Path.new(this)

public fun String.asRefPath(): Path = Path.new(this)

public fun Path.eq(other: Path): Boolean = Path.eq(this, other)

public fun Path.partialCmp(other: Path): Ordering? = Path.partialCmp(this, other)

public fun Path.eqPath(other: Path): Boolean = Path.eq(this, other)

public fun Path.partialCmpPath(other: Path): Ordering? = Path.partialCmp(this, other)

public fun Path.eq(other: PathBuf): Boolean = Path.eq(this, other)

public fun Path.partialCmp(other: PathBuf): Ordering? = Path.partialCmp(this, other)

public fun OsStr.eq(other: PathBuf): Boolean = Path.eq(this.asRef(), other)

public fun OsStr.partialCmp(other: PathBuf): Ordering? = Path.partialCmp(this.asRef(), other)

public fun OsStr.eqPathBuf(other: PathBuf): Boolean = Path.eq(this.asRef(), other)

public fun OsStr.partialCmpPathBuf(other: PathBuf): Ordering? = Path.partialCmp(this.asRef(), other)

public fun OsString.eq(other: PathBuf): Boolean = Path.eq(this.asRef(), other)

public fun OsString.partialCmp(other: PathBuf): Ordering? = Path.partialCmp(this.asRef(), other)

public fun OsStr.eq(other: Path): Boolean = Path.eq(this.asRef(), other)

public fun OsStr.partialCmp(other: Path): Ordering? = Path.partialCmp(this.asRef(), other)

public fun OsStr.eqPath(other: Path): Boolean = Path.eq(this.asRef(), other)

public fun OsStr.partialCmpPath(other: Path): Ordering? = Path.partialCmp(this.asRef(), other)

public fun OsString.eq(other: Path): Boolean = Path.eq(this.asRef(), other)

public fun OsString.partialCmp(other: Path): Ordering? = Path.partialCmp(this.asRef(), other)

public fun OsString.eqPath(other: Path): Boolean = Path.eq(this.asRef(), other)

public fun OsString.partialCmpPath(other: Path): Ordering? = Path.partialCmp(this.asRef(), other)

public fun Path.eq(other: OsStr): Boolean = Path.eq(this, other.asRef())

public fun OsStr.eqCowPath(other: Path): Boolean = Path.eq(this.asRef(), other)

public fun Path.partialCmp(other: OsStr): Ordering? = Path.partialCmp(this, other.asRef())

public fun OsStr.partialCmpCowPath(other: Path): Ordering? = Path.partialCmp(this.asRef(), other)

public fun Path.eqOsStr(other: OsStr): Boolean = Path.eq(this, other.asRef())

public fun Path.partialCmpOsStr(other: OsStr): Ordering? = Path.partialCmp(this, other.asRef())

public fun Path.eq(other: OsString): Boolean = Path.eq(this, other.asRef())

public fun OsString.eqCowPath(other: Path): Boolean = Path.eq(this.asRef(), other)

public fun Path.partialCmp(other: OsString): Ordering? = Path.partialCmp(this, other.asRef())

public fun OsString.partialCmpCowPath(other: Path): Ordering? = Path.partialCmp(this.asRef(), other)

/**
 * Makes the path absolute without accessing the filesystem.
 *
 * If the path is relative, the current directory is used as the base directory.
 * All intermediate components will be resolved according to platform-specific
 * rules, but unlike [`canonicalize`][fs.canonicalize], this does not
 * resolve symlinks and may succeed even if the path does not exist.
 *
 * If the `path` is empty or getting the
 * [current directory][env.currentDir] fails, then an error will be
 * returned.
 *
 * # Platform-specific behavior
 *
 * On POSIX platforms, the path is resolved using [POSIX semantics][posix-semantics],
 * except that it stops short of resolving symlinks. This means it will keep `..`
 * components and trailing separators.
 *
 * On Windows, for verbatim paths, this will simply return the path as given. For other
 * paths, this is currently equivalent to calling
 * [`GetFullPathNameW`][windows-path].
 *
 * On Cygwin, this is currently equivalent to calling [`cygwinConvPath`][cygwin-path]
 * with mode `CCP_WIN_A_TO_POSIX`, and then being processed like other POSIX platforms.
 * If a Windows path is given, it will be converted to an absolute POSIX path without
 * keeping `..`.
 *
 * Note that these [may change in the future][changes].
 *
 * # Errors
 *
 * This function may return an error in the following situations:
 *
 * * If `path` is syntactically invalid; in particular, if it is empty.
 * * If getting the [current directory][env.currentDir] fails.
 *
 * # Examples
 *
 * ## POSIX paths
 *
 * ```kotlin
 * import std.path.Path
 *
 * fun main(): Result<Unit> {
 *     // Relative to absolute
 *     val absolute = path.absolute("foo/./bar").getOrElse { return Result.failure(it) }
 *     check(absolute.endsWith("foo/bar"))
 *     // Absolute to absolute
 *     val absolute1 = path.absolute("/foo//test/.././bar.rs").getOrElse { return Result.failure(it) }
 *     check(absolute1 == Path.new("/foo/test/../bar.rs"))
 *     return Result.success(Unit)
 * }
 * ```
 *
 * ## Windows paths
 *
 * ```kotlin
 * import std.path.Path
 *
 * fun main(): Result<Unit> {
 *     // Relative to absolute
 *     val absolute = path.absolute("foo/./bar").getOrElse { return Result.failure(it) }
 *     check(absolute.endsWith("foo\\bar"))
 *     // Absolute to absolute
 *     val absolute1 = path.absolute("C:\\foo//test\\..\\./bar.rs").getOrElse { return Result.failure(it) }
 *     check(absolute1 == Path.new("C:\\foo\\bar.rs"))
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Note that this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 * [posix-semantics]: https://pubs.opengroup.org/onlinepubs/9799919799/basedefs/V1Chap04.html#tag0416
 * [windows-path]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-getfullpathnamew
 * [cygwin-path]: https://cygwin.com/cygwin-api/func-cygwin-conv-path.html
 */
public fun absolute(path: Path): Result<PathBuf, io.github.kotlinmania.rustlang.core.io.error.Error> {
    val path1 = path
    return if (path1.asOsStr().isEmpty()) {
        Result.Err<PathBuf, io.github.kotlinmania.rustlang.core.io.error.Error>(io.github.kotlinmania.rustlang.core.io.error.Error.fromStaticMessage(io.github.kotlinmania.rustlang.core.io.error.SimpleMessage(kind = ErrorKind.InvalidInput, message = "cannot make an empty path absolute")))
    } else {
        io.github.kotlinmania.rustlang.std.sys.path.unix.absolute(path1)
    }
}
