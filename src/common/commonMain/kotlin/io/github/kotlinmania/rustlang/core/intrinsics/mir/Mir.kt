// port-lint: source intrinsics/mir.rs
package io.github.kotlinmania.rustlang.core.intrinsics.mir

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Rustc internal tooling for hand-writing MIR.
//
// If for some reasons you are not writing rustc tests and have found yourself considering using
// this feature, turn back. This is *exceptionally* unstable. There is no attempt at all to make
// anything work besides those things which the rustc test suite happened to need. If you make a
// typo you'll probably ICE. Really, this is not the solution to your problems. Consider instead
// supporting the [rustc public project group](https://github.com/rust-lang/rustcPublic).
//
// The documentation for this module describes how to use this feature. If you are interested in
// hacking on the implementation, most of that documentation lives at
// `rustcMirBuild / src / build / custom / mod.rs`.
//
// Typical usage will look like this:
//
// ```kotlin
// import core.intrinsics.mir.*
//
// @feature(coreIntrinsics, customMir)
// public fun doctestEntry() {
//     @customMir(dialect = "built")
//     fun simple(x: Int): Int {
//         return mir(temp2, temp1, x, Goto, mySecondBlock, mySecondBlock, temp2, Move, temp1, RET, temp2, Return)
//     }
// }
// ```
//
// The `customMir` attribute tells the compiler to treat the function as being custom MIR. This
// attribute only works on functions - there is no way to insert custom MIR into the middle of
// another function. The `dialect` and `phase` parameters indicate which [version of MIR][dialect
// docs] you are inserting here. Generally you'll want to use `@file:customMir(dialect = "built")`
// if you want your MIR to be modified by the full MIR pipeline, or `#![customMir(dialect =
// "runtime", phase = "optimized")]` if you don't.
//
// [dialect docs]:
//     https://doc.rust-lang.org/nightly/nightly-rustc/rustcMiddle/mir/enum.MirPhase.html
//
// The input to the [mir] macro is:
//
//  - An optional return type annotation in the form of `type RET = ...;`. This may be required
//    if the compiler cannot infer the type of RET.
//  - A possibly empty list of local declarations. Locals can also be declared inline on
//    assignments via `val`. Type inference generally works. Shadowing does not.
//  - A list of basic blocks. The first of these is the start block and is where execution begins.
//    All blocks other than the start block need to be given a name, so that they can be referred
//    to later.
//     - Each block is a list of semicolon terminated statements, followed by a terminator. The
//       syntax for the various statements and terminators is designed to be as similar as possible
//       to the syntax for analogous concepts in native Rust. See below for a list.
//
// # Examples
//
// @file:feature(coreIntrinsics, customMir)
// @file:allow(internalFeatures)
// @file:allow(unusedAssignments)
//
// use core.intrinsics.mir::*;
//
// @customMir(dialect = "built")
// pub fn chooseLoad(a: &i32, b: &i32, c: bool) -> i32 {
//     mir! {
//         {
//             match c {
//                 true => t,
//                 _ => f,
//             }
//         }
//
//         t = {
//             let temp = a;
//             Goto(loadAndExit)
//         }
//
//         f = {
//             temp = b;
//             Goto(loadAndExit)
//         }
//
//         loadAndExit = {
//             RET = *temp;
//             Return()
//         }
//     }
// }
//
// @customMir(dialect = "built")
// fn unwrapUnchecked<T>(opt: Option<T>) -> T {
//     mir! {
//         {
//             RET = Move(Field(Variant(opt, 1), 0));
//             Return()
//         }
//     }
// }
//
// @customMir(dialect = "runtime", phase = "optimized")
// fn pushAndPop<T>(v: Vec<T>, value: T) {
//     mir! {
//         let _unused;
//         let popped;
//
//         {
//             Call(_unused = Vec.push(v, value), ReturnTo(pop), UnwindContinue())
//         }
//
//         pop = {
//             Call(popped = Vec.pop(v), ReturnTo(drop), UnwindContinue())
//         }
//
//         drop = {
//             Drop(popped, ReturnTo(ret), UnwindContinue())
//         }
//
//         ret = {
//             Return()
//         }
//     }
// }
//
// @customMir(dialect = "runtime", phase = "optimized")
// fn annotatedReturnType() -> (i32, bool) {
//     mir! {
//         type RET = (i32, bool);
//         {
//             RET.0 = 1;
//             RET.1 = true;
//             Return()
//         }
//     }
// }
// ```kotlin
// We can also set off compilation failures that happen in sufficiently late stages of the compiler:
// ```rust,compile_fail
// @file:feature(coreIntrinsics, customMir)
//
// extern crate core;
// use core.intrinsics.mir::*;
//
// @customMir(dialect = "built")
// fn borrowError(shouldInit: bool) -> i32 {
//     mir! {
//         let temp: i32;
//
//         {
//             match shouldInit {
//                 true => init,
//                 _ => useTemp,
//             }
//         }
//
//         init = {
//             temp = 0;
//             Goto(useTemp)
//         }
//
//         useTemp = {
//             RET = temp;
//             Return()
//         }
//     }
// }
// ```kotlin
// ```text
// error[E0381]: used binding is possibly-uninitialized
//   --> test.rs:24:13
//    |
// 8  | /     mir! {
// 9  | |         let temp: i32;
// 10 | |
// 11 | |         {
// ...  |
// 19 | |             temp = 0;
//    | |             -------- binding initialized here in some conditions
// ...  |
// 24 | |             RET = temp;
//    | |             ^^^^^^^^^^ value used here but it is possibly-uninitialized
// 25 | |             Return()
// 26 | |         }
// 27 | |     }
//    | |_____- binding declared here but left uninitialized
//
// error: aborting due to 1 previous error
//
// For more information about this error, try `rustc - - explain E0381`.
// ```kotlin
//
// The lists below are an exhaustive description of how various MIR constructs can be created.
// Anything missing from the list should be assumed to not be supported, PRs welcome.
//
// # # # # Locals
//
//  - The `_0` return local can always be accessed via `RET`.
// - Arguments can be accessed via their regular name.
//  - All other locals need to be declared with `val` somewhere and then can be accessed by name.
//
// # # # # Places
// - Locals implicitly convert to places.
// - Field accesses, derefs, and indexing work normally.
//  - Fields in variants can be accessed via the [Variant] and [Field] associated functions,
// see their documentation for details.
//
// # # # # Operands
//  - Places implicitly convert to `Copy` operands.
//  - `Move` operands can be created via [Move].
// - Const blocks, literals, named constants, and const params all just work.
//  - [Static] and [StaticMut] can be used to create `T` and `* T`s to statics. These are
// constants in MIR and the only way to access statics.
//
// # # # # Statements
// - Assign statements work via normal Rust assignment.
//  - [StorageLive], [StorageDead] statements have an associated function.
//
// # # # # Rvalues
//
//  - Operands implicitly convert to `Use` rvalues.
//  - , , `addrOf`, and `addrOfMut` all work to create their associated rvalue.
//  - [CastTransmute], [CastPtrToPtr], [CastUnsize], and [Discriminant]
// have associated functions.
//  - Unary and binary operations use their normal Rust syntax - `a * b`, `!c`, etc.
//  - The binary operation `Offset` can be created via [Offset].
//  - Checked binary operations are represented by wrapping the associated binop in [Checked].
//  - Array repetition syntax (`[foo; 10]`) creates the associated rvalue.
//
// # # # # Terminators
//
//  - [Goto], [Return], [Unreachable] and [`Drop`](Drop()) have associated functions.
//  - `when someIntOperand` becomes a `SwitchInt`. Each arm should be `literal -> basicBlock`
//     - The exception is the last arm, which must be `_ -> basicBlock` and corresponds to the
// otherwise branch.
//  - [Call] has an associated function as well, with special syntax:
//    `Call(retVal = function(arg1, arg2, ...), ReturnTo(nextBlock), UnwindContinue())`.
//  - [TailCall] does not have a return destination or next block, so its syntax is just
//    `TailCall(function(arg1, arg2, ...))`.
//
// # # # # Debuginfo
//
// Debuginfo associates source code variable names(of variables that may not exist any more) with
// MIR expressions that indicate where the value of that variable is stored.The syntax to do so
// is:
// ```text
// debug sourceVarName => expression;
// ```kotlin
// Both places and constants are supported in the `expression`.
//
// ```rust
// @file:allow(internalFeatures)
// @file:feature(coreIntrinsics, customMir)
//
// use core.intrinsics.mir::*;
//
// @customMir(dialect = "built")
// fn debuginfo(arg: Option<&i32>) {
//     mir(
//         // Debuginfo for a source variable `plainLocal` that just duplicates `arg`.
//         debug plainLocal => arg;
//         // Debuginfo for a source variable `projection` that can be computed by dereferencing
//         // a field of `arg`.
//         debug projection => *Field::<&i32>(Variant(arg, 1), 0);
//         // Debuginfo for a source variable `constant` that always holds the value `5`.
//         debug constant => 5_usize;
//         {
//             Return()
//         }
//     )
// }
// ```kotlin

/**
 * Type representing basic blocks.
 *
 * All terminators will have this type as a return type. It helps achieve some type safety.
 */
public sealed class BasicBlock {
    /**
     * A non-cleanup basic block.
     */
    public data object Normal : BasicBlock()
    /**
     * A basic block that lies on an unwind path.
     */
    public data object Cleanup : BasicBlock()
}

/**
 * The reason we are terminating the process during unwinding.
 */
public sealed class UnwindTerminateReason {
    /**
     * Unwinding is just not possible given the ABI of this function.
     */
    public data object Abi : UnwindTerminateReason()
    /**
     * We were already cleaning up for an ongoing unwind, and a *second*, *nested* unwind was
     * triggered by the drop glue.
     */
    public data object InCleanup : UnwindTerminateReason()
}

// Unwind actions
public open class UnwindActionArg {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<UnwindActionArg> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<UnwindActionArg> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): UnwindActionArg =
                    UnwindActionArg()
                override fun writeNative(addr: Long, value: UnwindActionArg) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): UnwindActionArg =
                    UnwindActionArg()
                override fun writeBytes(buf: ByteArray, offset: Int, value: UnwindActionArg) {
                }
            }
    }
}

// Terminators

/**
 * An unwind action that continues unwinding.
 */
public fun unwindContinue(): UnwindActionArg {
    error("panic")
}

/**
 * An unwind action that triggers undefined behavior.
 */
public fun unwindUnreachable(): UnwindActionArg {
    error("panic")
}

/**
 * An unwind action that terminates the execution.
 *
 * `UnwindTerminate` can also be used as a terminator.
 */
public fun unwindTerminate(reason: UnwindTerminateReason): UnwindActionArg {
    error("panic")
}

/**
 * An unwind action that continues execution in a given basic block.
 */
public fun unwindCleanup(goto: BasicBlock): UnwindActionArg {
    error("panic")
}

// Return destination for `Call`
public open class ReturnToArg {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ReturnToArg> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ReturnToArg> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): ReturnToArg =
                    ReturnToArg()
                override fun writeNative(addr: Long, value: ReturnToArg) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): ReturnToArg =
                    ReturnToArg()
                override fun writeBytes(buf: ByteArray, offset: Int, value: ReturnToArg) {
                }
            }
    }
}

public fun returnTo(goto: BasicBlock): ReturnToArg {
    error("panic")
}

public fun ret(): BasicBlock {
    error("panic")
}

public fun goto(destination: BasicBlock): BasicBlock {
    error("panic")
}

public fun unreachable(): BasicBlock {
    error("panic")
}

/**
 * Drop the contents of a place.
 *
 * The first argument must be a place.
 *
 * The second argument must be of the form `ReturnTo(bb)`, where `bb` is the basic block that
 * will be jumped to after the destructor returns.
 *
 * The third argument describes what happens on unwind. It can be one of:
 * - [UnwindContinue]
 * - [UnwindUnreachable]
 * - [UnwindTerminate]
 * - [UnwindCleanup]
 */
public fun <T> drop(place: T, goto: ReturnToArg, unwindAction: UnwindActionArg) {
    error("panic")
}

/**
 * Call a function.
 *
 * The first argument must be of the form `retVal = fun(arg1, arg2, ...)`.
 *
 * The second argument must be of the form `ReturnTo(bb)`, where `bb` is the basic block that
 * will be jumped to after the function returns.
 *
 * The third argument describes what happens on unwind. It can be one of:
 * - [UnwindContinue]
 * - [UnwindUnreachable]
 * - [UnwindTerminate]
 * - [UnwindCleanup]
 */
public fun call(call: Unit, goto: ReturnToArg, unwindAction: UnwindActionArg) {
    error("panic")
}

/**
 * Call a function.
 *
 * The argument must be of the form `fun(arg1, arg2, ...)`.
 */
public fun <T> tailCall(call: T) {
    error("panic")
}

/**
 * A terminator that resumes the unwinding.
 */
public fun unwindResume() {
    error("panic")
}

public fun <T> storageLive(local: T) {
    error("panic")
}

public fun <T> storageDead(local: T) {
    error("panic")
}

public fun assume(operand: Boolean) {
    error("panic")
}

public fun <T> checked(binop: T): Pair<T, Boolean> {
    error("panic")
}

public fun ptrMetadata(place: ConstPtr): Metadata {
    error("panic")
}

public fun <T> move(place: T): T {
    error("panic")
}

public fun <T> static(s: T): T {
    error("panic")
}

public fun <T> staticMut(s: T): MutPtr {
    error("panic")
}

/**
 * Gets the discriminant of a place.
 */
public fun <T> discriminant(place: T): Discriminant {
    error("panic")
}

public fun <T> setDiscriminant(place: T, index: UInt) {
    error("panic")
}

public fun <T, U> offset(ptr: T, count: U): T {
    error("panic")
}

/**
 * Access the field with the given index of some place.
 *
 * This only makes sense to use in conjunction with [Variant]. If the type you are looking to
 * access the field of does not have variants, you can use normal field projection syntax.
 *
 * There is no proper way to do a place projection to a variant in Rust, and so these two
 * functions are a workaround. You can access a field of a variant via `Field(Variant(place,
 * varIdx), fieldIdx)`, where`varIdx`and`fieldIdx` are appropriate literals. Some
 * caveats:
 *
 *  - The return type of `Variant` is always `()`. Don't worry about that, the correct MIR will
 *    still be generated.
 *  - In some situations, the return type of `Field` cannot be inferred. You may need to
 *    annotate it on the function in these cases.
 *  - Since `Field` is a function call which is not a place expression, using this on the left
 *    hand side of an expression is rejected by the compiler. [place] is a macro provided to
 *    work around that issue. Wrap the left hand side of an assignment in the macro to convince
 *    the compiler that it's ok.
 *
 * # Examples
 *
 * ```kotlin
 * import core.intrinsics.mir.*
 *
 * @feature(customMir, coreIntrinsics)
 * public fun doctestEntry() {
 *     @customMir(dialect = "built")
 *     fun unwrapDeref(opt: Int?): Int {
 *         return mir(run {
 *             RET = Field(Variant(opt, 1), 0)
 *             Return()
 *         })
 *     }
 *     @customMir(dialect = "built")
 *     fun set(opt: Int?) {
 *         mir(run {
 *             place(Field(Variant(opt!!, 1), 0)) = 5
 *             Return()
 *         })
 *     }
 * }
 * ```
 */
public inline fun <reified F> field(place: Unit, field: UInt): F {
    error("panic")
}

/**
 * Adds a variant projection with the given index to the place.
 *
 * See [Field] for documentation.
 */
public fun <T> variant(place: T, index: UInt) {
    error("panic")
}

/**
 * Emits a `CastKind.Transmute` cast.
 *
 * Needed to test the UB when `sizeof(T) != sizeof(U)`, which can't be
 * generated via the normal `mem.reinterpret`.
 */
public fun <T, U> castTransmute(operand: T): U {
    error("panic")
}

/**
 * Emits a `CastKind.PtrToPtr` cast.
 *
 * This allows bypassing normal validation to generate strange casts.
 */
public fun <T, U> castPtrToPtr(operand: T): U {
    error("panic")
}

/**
 * Emits a `CastKind.PointerCoercion(Unsize)` cast.
 *
 * This allows bypassing normal validation to generate strange casts.
 */
public fun <T, U> castUnsize(operand: T): U {
    error("panic")
}

public fun <T> internalMakePlace(place: T): MutPtr {
    error("panic")
}

public fun <T> debuginfo(name: String, s: T) {
    error("panic")
}

public sealed class Safety {
    public data object Safe : Safety()
    public data object Unsafe : Safety()
}

public sealed class PointerCoercion {
    public data class ReifyFnPointer(public var value: Safety) : PointerCoercion()
    public data object UnsafeFnPointer : PointerCoercion()
    public data class ClosureFnPointer(public var value: Safety) : PointerCoercion()
    public data object MutToConstPointer : PointerCoercion()
    public data object ArrayToPointer : PointerCoercion()
    public data object UnsizePointee : PointerCoercion()
}

public sealed class CastKind {
    public data object PointerExposeProvenance : CastKind()
    public data object PointerWithExposedProvenance : CastKind()
    public data object IntToInt : CastKind()
    public data object FloatToInt : CastKind()
    public data object FloatToFloat : CastKind()
    public data object IntToFloat : CastKind()
    public data object PtrToPtr : CastKind()
    public data object FnPtrToPtr : CastKind()
    public data object Transmute : CastKind()
    public data object BoxDerefTransmute : CastKind()
    public data object Subtype : CastKind()
    public data class PointerCoercion(public var value: PointerCoercion) : CastKind()
}

/**
 * Emits a cast of the specified kind.
 */
public fun <T, U> cast(operand: T, kind: CastKind): U {
    error("panic")
}
