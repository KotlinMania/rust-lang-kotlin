// port-lint: source pin.rs
package io.github.kotlinmania.rustlang.core.pin

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ops.deref.DerefPure
import io.github.kotlinmania.rustlang.core.ops.deref.LegacyReceiver
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result

// Types that pin data to a location in memory.
//
// It is sometimes useful to be able to rely upon a certain value not being able to *move*,
// in the sense that its address in memory cannot change. This is useful especially when there
// are one or more [*pointers*][pointer] pointing at that value. The ability to rely on this
// guarantee that the value a [pointer] is pointing at (its **pointee**) will
//
// 1. Not be *moved* out of its memory location
// 2. More generally, remain *valid* at that same memory location
//
// is called "pinning." We would say that a value which satisfies these guarantees has been
// "pinned," in that it has been permanently (until the end of its lifespan) attached to its
// location in memory, as though pinned to a pinboard. Pinning a value is an incredibly useful
// building block for [unsafe] code to be able to reason about whether a raw pointer to the
// pinned value is still valid. [As we'll see later][drop-guarantee], once a value is pinned,
// it is necessarily valid at its memory location until the end of its lifespan. This concept
// of "pinning" is necessary to implement safe interfaces on top of things like self-referential
// types and intrusive data structures which cannot currently be modeled in fully safe Rust using
// only borrow-checked [references][reference].
//
// "Pinning" allows us to put a *value* which exists at some location in memory into a state where
// safe code cannot *move* that value to a different location in memory or otherwise invalidate it
// at its current location (unless it implements [Unpin], which we will
// [talk about below][self#unpin]). Anything that wants to interact with the pinned value in a way
// that has the potential to violate these guarantees must promise that it will not actually
// violate them, using the [unsafe] keyword to mark that such a promise is upheld by the user
// and not the compiler. In this way, we can allow other [unsafe] code to rely on any pointers
// that point to the pinned value to be valid to dereference while it is pinned.
//
// Note that as long as you don't use [unsafe], it's impossible to create or misuse a pinned
// value in a way that is unsound. See the documentation of [Pin.Ptr] for more
// information on the practicalities of how to pin a value and how to use that pinned value from a
// user's perspective without using [unsafe].
//
// The rest of this documentation is intended to be the source of truth for users of [Pin.Ptr]
// that are implementing the [unsafe] pieces of an interface that relies on pinning for validity;
// users of [Pin.Ptr] in safe code do not need to read it in detail.
//
// There are several sections to this documentation:
//
// * [What is "*moving*"?][what-is-moving]
// * [What is "pinning"?][what-is-pinning]
// * [Address sensitivity, AKA "when do we need pinning?"][address-sensitive-values]
// * [Examples of types with address-sensitive states][address-sensitive-examples]
//   * [Self-referential struct][self-ref]
//   * [Intrusive, doubly-linked list][linked-list]
// * [Subtle details and the `Drop` guarantee][subtle-details]
//
// # What is "*moving*"?
// [what-is-moving]: self#what-is-moving
//
// When we say a value is *moved*, we mean that the compiler copies, byte-for-byte, the
// value from one location to another. In a purely mechanical sense, this is identical to
// [Copy]ing a value from one place in memory to another. In Rust, "move" carries with it the
// semantics of ownership transfer from one variable to another, which is the key difference
// between a [Copy] and a move. For the purposes of this module's documentation, however, when
// we write *move* in italics, we mean *specifically* that the value has *moved* in the mechanical
// sense of being located at a new place in memory.
//
// All values in Rust are trivially *moveable*. This means that the address at which a value is
// located is not necessarily stable in between borrows. The compiler is allowed to *move* a value
// to a new address without running any code to notify that value that its address
// has changed. Although the compiler will not insert memory *moves* where no semantic move has
// occurred, there are many places where a value *may* be moved. For example, when doing
// assignment or passing a value into a function.
//
// ```kotlin
// import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
//
// open class AddrTracker(public var value: Int?) {
//     public var field0: Int?
//         get() = value
//         set(v) {
//             value = v
//         }
//
//     // If we haven't checked the addr of self yet, store the current
//     // address. If we have, confirm that the current address is the same
//     // as it was last time, or else panic.
//     public open fun checkForMove() {
//         val currentAddr = (mutPtrFromRustCast(this)).rawAddress.toInt()
//         when (val matchVal = this.value) {
//             null -> this.value = currentAddr
//             else -> {
//                 val prevAddr = matchVal
//                 check(prevAddr == currentAddr)
//             }
//         }
//     }
// }
// // Create a tracker and store the initial address
// var tracker = AddrTracker.default()
// tracker.checkForMove()
// // Here we shadow the variable. This carries a semantic move, and may therefore also
// // come with a mechanical memory *move*
// var tracker1 = tracker
// // May panic
// // tracker.checkForMove();
// ```
//
// In this sense, Rust does not guarantee that `checkForMove()` will never panic, because the
// compiler is permitted to *move* `tracker` in many situations.
//
// Common smart-pointer types such as [Box.T] and [mut.T] also allow *moving* the underlying
// *value* they point at: you can move out of a [Box.T], or you can use [mem.replace] to
// move a `T` out of a [mut.T]. Therefore, putting a value (such as `tracker` above) behind a
// pointer isn't enough on its own to ensure that its address does not change.
//
// # What is "pinning"?
// [what-is-pinning]: self#what-is-pinning
//
// We say that a value has been *pinned* when it has been put into a state where it is guaranteed
// to remain *located at the same place in memory* from the time it is pinned until its
// [drop] is called.
//
// ## Address-sensitive values, AKA "when we need pinning"
// [address-sensitive-values]: self#address-sensitive-values-aka-when-we-need-pinning
//
// Most values in Rust are entirely okay with being *moved* around at-will.
// Types for which it is *always* the case that *any* value of that type can be
// *moved* at-will should implement [Unpin], which we will discuss more [below][self#unpin].
//
// [Pin] is specifically targeted at allowing the implementation of *safe interfaces* around
// types which have some state during which they become "address-sensitive." A value in such an
// "address-sensitive" state is *not* okay with being *moved* around at-will. Such a value must
// stay *un-moved* and valid during the address-sensitive portion of its lifespan because some
// interface is relying on those invariants to be true in order for its implementation to be sound.
//
// As a motivating example of a type which may become address-sensitive, consider a type which
// contains a pointer to another piece of its own data, *i.e.* a "self-referential" type. In order
// for such a type to be implemented soundly, the pointer which points into `self`s data must be
// proven valid whenever it is accessed. But if that value is *moved*, the pointer will still
// point to the old address where the value was located and not into the new location of `self`,
// thus becoming invalid. A key example of such self-referential types are the state machines
// generated by the compiler to implement [Future] for `async fun`s.
//
// Such types that have an *address-sensitive* state usually follow a lifecycle
// that looks something like so:
//
// 1. A value is created which can be freely moved around.
//     * e.g. calling an async function which returns a state machine implementing [Future]
// 2. An operation causes the value to depend on its own address not changing
//     * e.g. calling [poll] for the first time on the produced [Future]
// 3. Further pieces of the safe interface of the type use internal [unsafe] operations which
//    assume that the address of the value is stable
//     * e.g. subsequent calls to [poll]
// 4. Before the value is invalidated (e.g. deallocated), it is *dropped*, giving it a chance to
//    notify anything with pointers to itself that those pointers will be invalidated
//     * e.g. [drop]ping the [Future] [^pin-drop-future]
//
// There are two possible ways to ensure the invariants required for 2. and 3. above (which
// apply to any address-sensitive type, not just self-referential types) do not get broken.
//
// 1. Have the value detect when it is moved and update all the pointers that point to itself.
// 2. Guarantee that the address of the value does not change (and that memory is not re-used
//    for anything else) during the time that the pointers to it are expected to be valid to
//    dereference.
//
// Since, as we discussed, Rust can move values without notifying them that they have moved, the
// first option is ruled out.
//
// In order to implement the second option, we must in some way enforce its key invariant,
// *i.e.* prevent the value from being *moved* or otherwise invalidated (you may notice this
// sounds an awful lot like the definition of *pinning* a value). There are a few ways one might
// be able to enforce this invariant in Rust:
//
// 1. Offer a wholly `unchecked` API to interact with the object, thus requiring every caller to
//    uphold the invariant themselves
// 2. Store the value that must not be moved behind a carefully managed pointer internal to
//    the object
// 3. Leverage the type system to encode and enforce this invariant by presenting a restricted
//    API surface to interact with *any* object that requires these invariants
//
// The first option is quite obviously undesirable, as the [unsafe]ty of the interface will
// become viral throughout all code that interacts with the object.
//
// The second option is a viable solution to the problem for some use cases, in particular
// for self-referential types. Under this model, any type that has an address sensitive state
// would ultimately store its data in something like a [Box.T], carefully manage internal
// access to that data to ensure no *moves* or other invalidation occurs, and finally
// provide a safe interface on top.
//
// There are a couple of linked disadvantages to using this model. The most significant is that
// each individual object must assume it is *on its own* to ensure
// that its data does not become *moved* or otherwise invalidated. Since there is no shared
// contract between values of different types, an object cannot assume that others interacting
// with it will properly respect the invariants around interacting with its data and must
// therefore protect it from everyone. Because of this, *composition* of address-sensitive types
// requires at least a level of pointer indirection each time a new object is added to the mix
// (and, practically, a heap allocation).
//
// Although there were other reasons as well, this issue of expensive composition is the key thing
// that drove Rust towards adopting a different model. It is particularly a problem
// when one considers, for example, the implications of composing together the [Future]s which
// will eventually make up an asynchronous task (including address-sensitive `async fun` state
// machines). It is plausible that there could be many layers of [Future]s composed together,
// including multiple layers of `async fun`s handling different parts of a task. It was deemed
// unacceptable to force indirection and allocation for each layer of composition in this case.
//
// [Pin.Ptr] is an implementation of the third option. It allows us to solve the issues
// discussed with the second option by building a *shared contractual language* around the
// guarantees of "pinning" data.
//
// [^pin-drop-future]: Futures themselves do not ever need to notify other bits of code that
// they are being dropped, however data structures like stack-based intrusive linked lists do.
//
// ## Using [Pin.Ptr] to pin values
//
// In order to pin a value, we wrap a *pointer to that value* (of some type `Ptr`) in a
// [Pin.Ptr]. [Pin.Ptr] can wrap any pointer type, forming a promise that the **pointee**
// will not be *moved* or [otherwise invalidated][subtle-details].
//
// We call such a [Pin]-wrapped pointer a **pinning pointer,** (or pinning reference, or pinning
// `Holder`, etc.) because its existence is the thing that is conceptually pinning the underlying
// pointee in place: it is the metaphorical "pin" securing the data in place on the pinboard
// (in memory).
//
// Notice that the thing wrapped by [Pin] is not the value which we want to pin itself, but
// rather a pointer to that value! A [Pin.Ptr] does not pin the `Ptr`; instead, it pins the
// pointer's ***pointee** value*.
//
// ### Pinning as a library contract
//
// Pinning does not require nor make use of any compiler "magic"[^noalias], only a specific
// contract between the [unsafe] parts of a library API and its users.
//
// It is important to stress this point as a user of the [unsafe] parts of the [Pin] API.
// Practically, this means that performing the mechanics of "pinning" a value by creating a
// [Pin.Ptr] to it *does not* actually change the way the compiler behaves towards the
// inner value! It is possible to use incorrect [unsafe] code to create a [Pin.Ptr] to a
// value which does not actually satisfy the invariants that a pinned value must satisfy, and in
// this way lead to undefined behavior even in (from that point) fully safe code. Similarly, using
// [unsafe], one may get access to a bare [mut.T] from a [Pin.Ptr] and
// use that to invalidly *move* the pinned value out. It is the job of the user of the
// [unsafe] parts of the [Pin] API to ensure these invariants are not violated.
//
// This differs from e.g. [UnsafeCell] which changes the semantics of a program's compiled
// output. A [Pin.Ptr] is a handle to a value which we have promised we will not move out of,
// but Rust still considers all values themselves to be fundamentally moveable through, *e.g.*
// assignment or [mem.replace].
//
// [^noalias]: There is a bit of nuance here that is still being decided about what the aliasing
// semantics of `Pin<T>` should be, but this is true as of today.
//
// ### How [Pin] prevents misuse in safe code
//
// In order to accomplish the goal of pinning the pointee value, [Pin.Ptr] restricts access to
// the wrapped `Ptr` type in safe code. Specifically, [Pin] disallows the ability to access
// the wrapped pointer in ways that would allow the user to *move* the underlying pointee value or
// otherwise re-use that memory for something else without using [unsafe]. For example, a
// [Pin.mut.T] makes it impossible to obtain the wrapped <code>[&mut] T</code> safely because
// through that <code>[&mut] T</code> it would be possible to *move* the underlying value out of
// the pointer with [mem.replace], etc.
//
// As discussed above, this promise must be upheld manually by [unsafe] code which interacts
// with the [Pin.Ptr] so that other [unsafe] code can rely on the pointee value being
// *un-moved* and valid. Interfaces that operate on values which are in an address-sensitive state
// accept an argument like <code>[Pin]<[&mut] T></code> or <code>[Pin]<[Holder]\<T>></code> to
// indicate this contract to the caller.
//
// [As discussed below][drop-guarantee], opting in to using pinning guarantees in the interface
// of an address-sensitive type has consequences for the implementation of some safe traits on
// that type as well.
//
// ## Interaction between [Deref] and [Pin.Ptr]
//
// Since [Pin.Ptr] can wrap any pointer type, it uses [Deref] and [DerefMut] in
// order to identify the type of the pinned pointee data and provide (restricted) access to it.
//
// A [Pin.Ptr] where [`Ptr.Deref`][Deref] is a "`Ptr`-style pinning pointer" to a pinned
// [`Ptr.Target`][Target] – so, a <code>[Pin]<[Holder]\<T>></code> is an owned, pinning pointer to a
// pinned `T`, and a <code>[Pin]<[Rc]\<T>></code> is a reference-counted, pinning pointer to a
// pinned `T`.
//
// [Pin.Ptr] also uses the [`Ptr.asTarget.Deref.Target`][Target] type information to modify the
// interface it is allowed to provide for interacting with that data (for example, when a
// pinning pointer points at pinned data which implements [Unpin], as
// [discussed below][self#unpin]).
//
// [Pin.Ptr] requires that implementations of [Deref] and [DerefMut] on `Ptr` return a
// pointer to the pinned data directly and do not *move* out of the `self` parameter during their
// implementation of [DerefMut.derefMut]. It is unsound for [unsafe] code to wrap pointer
// types with such "malicious" implementations of [Deref]; see [Pin.Ptr.newUnchecked] for
// details.
//
// ## Fixing `AddrTracker`
//
// The guarantee of a stable address is necessary to make our `AddrTracker` example work. When
// `checkForMove` sees a <code>[Pin]<AddrTracker></code>, it can safely assume that value
// will exist at that same address until said value goes out of scope, and thus multiple calls
// to it *cannot* panic.
//
// ```kotlin
// import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
// import std.marker.PhantomPinned
// import std.pin.Pin
// import std.pin.pin
//
// open class AddrTracker(
//     public var prevAddr: Int?,
//     // remove auto-implemented `Unpin` bound to mark this type as having some
//     // address-sensitive state. This is essential for our expected pinning
//     // guarantees to work, and is discussed more below.
//     public var pin: PhantomPinned,
// ) {
//     public open fun checkForMove() {
//         val currentAddr = (constPtrFromRustCast(this)).rawAddress.toInt()
//         when (val matchVal = this.prevAddr) {
//             null -> {
//                 // SAFETY: we do not move out of self
//                 val selfDataMut = this.getUncheckedMut()
//                 selfDataMut.prevAddr = currentAddr
//             }
//             else -> {
//                 val prevAddr = matchVal
//                 check(prevAddr == currentAddr)
//             }
//         }
//     }
// }
// // 1. Create the value, not yet in an address-sensitive state
// val tracker = AddrTracker.default()
// // 2. Pin the value by putting it behind a pinning pointer, thus putting
// // it into an address-sensitive state
// var ptrToPinnedTracker: Pin<AddrTracker> = pin(tracker)
// ptrToPinnedTracker.asMut().checkForMove()
// // Trying to access `tracker` or pass `ptrToPinnedTracker` to anything that requires
// // mutable access to a non-pinned version of it will no longer compile
// // 3. We can now assume that the tracker value will never be moved, thus
// // this will never panic
// ptrToPinnedTracker.asMut().checkForMove()
// ```
//
// Note that this invariant is enforced by simply making it impossible to call code that would
// perform a move on the pinned value. This is the case since the only way to access that pinned
// value is through the pinning <code>[Pin]<[&mut] T></code>, which in turn restricts our access.
//
// ## [Unpin]
//
// The vast majority of Rust types have no address-sensitive states. These types
// implement the [Unpin] auto-trait, which cancels the restrictive effects of
// [Pin] when the *pointee* type `T` is [Unpin]. When [`T.Unpin`][Unpin],
// <code>[Pin]<[Holder]\<T>></code> functions identically to a non-pinning [Box.T]; similarly,
// <code>[Pin]<[&mut] T></code> would impose no additional restrictions above a regular
// [mut.T].
//
// The idea of this trait is to alleviate the reduced ergonomics of APIs that require the use
// of [Pin] for soundness for some types, but which also want to be used by other types that
// don't care about pinning. The prime example of such an API is [Future.poll]. There are many
// [Future] types that don't care about pinning. These futures can implement [Unpin] and
// therefore get around the pinning related restrictions in the API, while still allowing the
// subset of [Future]s which *do* require pinning to be implemented soundly.
//
// Note that the interaction between a [Pin.Ptr] and [Unpin] is through the type of the
// **pointee** value, [`Ptr.asTarget.Deref.Target`][Target]. Whether the `Ptr` type itself
// implements [Unpin] does not affect the behavior of a [Pin.Ptr]. For example, whether or not
// [Box] is [Unpin] has no effect on the behavior of <code>[Pin]<[Holder]\<T>></code>, because
// `T` is the type of the pointee value, not [Box]. So, whether `T` implements [Unpin] is
// the thing that will affect the behavior of the <code>[Pin]<[Holder]\<T>></code>.
//
// Builtin types that are [Unpin] include all of the primitive types, like [bool], [i32],
// and [f32], references (<code>[&]T</code> and <code>[&mut] T</code>), etc., as well as many
// core and standard library types like [Box.T], [String], and more.
// These types are marked [Unpin] because they do not have an address-sensitive state like the
// ones we discussed above. If they did have such a state, those parts of their interface would be
// unsound without being expressed through pinning, and they would then need to not
// implement [Unpin].
//
// The compiler is free to take the conservative stance of marking types as [Unpin] so long as
// all of the types that compose its fields are also [Unpin]. This is because if a type
// implements [Unpin], then it is unsound for that type's implementation to rely on
// pinning-related guarantees for soundness, *even* when viewed through a "pinning" pointer! It is
// the responsibility of the implementor of a type that relies upon pinning for soundness to
// ensure that type is *not* marked as [Unpin] by adding [PhantomPinned] field. This is
// exactly what we did with our `AddrTracker` example above. Without doing this, you *must not*
// rely on pinning-related guarantees to apply to your type!
//
// If you really need to pin a value of a foreign or built-in type that implements [Unpin],
// you'll need to create your own wrapper type around the [Unpin] type you want to pin and then
// opt-out of [Unpin] using [PhantomPinned].
//
// Exposing access to the inner field which you want to remain pinned must then be carefully
// considered as well! Remember, exposing a method that gives access to a
// <code>[Pin]<[&mut] InnerT></code> where <code>InnerT: [Unpin]</code> would allow safe code to
// trivially move the inner value out of that pinning pointer, which is precisely what you're
// seeking to prevent! Exposing a field of a pinned value through a pinning pointer is called
// "projecting" a pin, and the more general case of deciding in which cases a pin should be able
// to be projected or not is called "structural pinning." We will go into more detail about this
// [below][structural-pinning].
//
// # Examples of address-sensitive types
// [address-sensitive-examples]: #examples-of-address-sensitive-types
//
// ## A self-referential struct
// [self-ref]: #a-self-referential-struct
// [Unmovable]: #a-self-referential-struct
//
// Self-referential structs are the simplest kind of address-sensitive type.
//
// It is often useful for a struct to hold a pointer back into itself, which
// allows the program to efficiently track subsections of the struct.
// Below, the `slice` field is a pointer into the `data` field, which
// we could imagine being used to track a sliding window of `data` in parser
// code.
//
// As mentioned before, this pattern is also used extensively by compiler-generated
// [Future]s.
//
// ```kotlin
// @file:OptIn(ExperimentalUnsignedTypes.cls)
//
// import std.marker.PhantomPinned
// import std.pin.Pin
// import std.ptr.NonNull
//
// public fun doctestEntry() {
//     / **
//      * This is a self-referential struct because `self.slice` points into `self.data`.
//      * /
//     open class Unmovable(
//         / **
//          * Backing buffer.
//          * /
//         public var data: UByteArray,
//         / **
//          * Points at `self.data` which we know is itself non-null. Raw pointer because we can't do
//          * this with a normal reference.
//          * /
//         public var slice: NonNull,
//         / **
//          * Suppress `Unpin` so that this cannot be moved out of a `Pin` once constructed.
//          * /
//         public var pin: PhantomPinned,
//     ) {
//         / **
//          * Creates a new `Unmovable`.
//          *
//          * To ensure the data doesn't move we place it on the heap behind a pinning Holder.
//          * Note that the data is pinned, but the `Pin<Holder<Self>>` which is pinning it can
//          * itself still be moved. This is important because it means we can return the pinning
//          * pointer from the function, which is itself a kind of move!
//          * /
//         public open fun new(): Pin<Unmovable> {
//             val res = Unmovable(data = UByteArray(64) { 0u.toUByte() }, slice = NonNull.from(listOf()), pin = PhantomPinned)
//             // First we put the data in a box, which will be its final resting place
//             var boxed = res
//             // Then we make the slice field point to the proper part of that boxed data.
//             // From now on we need to make sure we don't move the boxed data.
//             boxed.slice = NonNull.from(boxed.data)
//             // To do that, we pin the data in place by pointing to it with a pinning
//             // (`Pin`-wrapped) pointer.
//             //
//             // `Holder.intoPin` makes existing `Holder` pin the data in-place without moving it,
//             // so we can safely do this now *after* inserting the slice pointer above, but we have
//             // to take care that we haven't performed any other semantic moves of `res` in between.
//             val pin = Holder.intoPin(boxed)
//             // Now we can return the pinned (through a pinning Holder) data
//             return pin
//         }
//     }
//     val unmovable: Pin<Unmovable> = Unmovable.new()
//     // The inner pointee `Unmovable` struct will now never be allowed to move.
//     // Meanwhile, we are free to move the pointer around.
//     var stillUnmoved = unmovable
//     check(stillUnmoved.slice == NonNull.from(stillUnmoved.data))
//     // We cannot mutably dereference a `Pin<Ptr>` unless the pointee is `Unpin` or we use unchecked.
//     // Since our type doesn't implement `Unpin`, this will fail to compile.
//     // let mut newUnmoved = Unmovable.new();
//     // std.mem.swap(*stillUnmoved, *newUnmoved);
// }
// ```
//
// ## An intrusive, doubly-linked list
// [linked-list]: #an-intrusive-doubly-linked-list
//
// In an intrusive doubly-linked list, the collection itself does not own the memory in which
// each of its elements is stored. Instead, each client is free to allocate space for elements it
// adds to the list in whichever manner it likes, including on the stack! Elements can be stored
// in a stack frame shorter-lived than the collection, provided they are removed from the list
// before that frame goes out of scope.
//
// To make such an intrusive data structure work, every element stores pointers to its predecessor
// and successor within its own data, rather than having the list structure itself managing those
// pointers. It is in this sense that the structure is "intrusive": the details of how an
// element is stored within the larger structure "intrudes" on the implementation of the element
// type itself!
//
// The full implementation details of such a data structure are outside the scope of this
// documentation, but we will discuss how [Pin] can help to do so.
//
// Using such an intrusive pattern, elements may only be added when they are pinned. If we think
// about the consequences of adding non-pinned values to such a list, this becomes clear:
//
// *Moving* or otherwise invalidating an element's data would invalidate the pointers back to it
// which are stored in the elements ahead and behind it. Thus, in order to soundly dereference
// the pointers stored to the next and previous elements, we must satisfy the guarantee that
// nothing has invalidated those pointers (which point to data that we do not own).
//
// Moreover, the [`Drop`][Drop] implementation of each element must in some way notify its
// predecessor and successor elements that it should be removed from the list before it is fully
// destroyed, otherwise the pointers back to it would again become invalidated.
//
// Crucially, this means we have to be able to rely on [drop] always being called before an
// element is invalidated. If an element could be deallocated or otherwise invalidated without
// calling [drop], the pointers to it stored in its neighboring elements would
// become invalid, which would break the data structure.
//
// Therefore, pinning data also comes with [the "`Drop` guarantee"][drop-guarantee].
//
// # Subtle details and the `Drop` guarantee
// [subtle-details]: self#subtle-details-and-the-drop-guarantee
// [drop-guarantee]: self#subtle-details-and-the-drop-guarantee
//
// The purpose of pinning is not *just* to prevent a value from being *moved*, but more
// generally to be able to rely on the pinned value *remaining valid **at a specific place*** in
// memory.
//
// To do so, pinning a value adds an *additional* invariant that must be upheld in order for use
// of the pinned data to be valid, on top of the ones that must be upheld for a non-pinned value
// of the same type to be valid:
//
// From the moment a value is pinned by constructing a [Pin]ning pointer to it, that value
// must *remain, **valid***, at that same address in memory, *until its [drop] handler is
// called.*
//
// There is some subtlety to this which we have not yet talked about in detail. The invariant
// described above means that, yes,
//
// 1. The value must not be moved out of its location in memory
//
// but it also implies that,
//
// 2. The memory location that stores the value must not get invalidated or otherwise repurposed
//    during the lifespan of the pinned value until its [drop] returns or panics
//
// This point is subtle but required for intrusive data structures to be implemented soundly.
//
// ## `Drop` guarantee
//
// There needs to be a way for a pinned value to notify any code that is relying on its pinned
// status that it is about to be destroyed. In this way, the dependent code can remove the
// pinned value's address from its data structures or otherwise change its behavior with the
// knowledge that it can no longer rely on that value existing at the location it was pinned to.
//
// Thus, in any situation where we may want to overwrite a pinned value, that value's [drop] must
// be called beforehand (unless the pinned value implements [Unpin], in which case we can ignore
// all of [Pin]s guarantees, as usual).
//
// The most common storage-reuse situations occur when a value on the stack is destroyed as part
// of a function return and when heap storage is freed. In both cases, [drop] gets run for us
// by Rust when using standard safe code. However, for manual heap allocations or otherwise
// custom-allocated storage, [unsafe] code must make sure to call [ptr.dropInPlace] before
// deallocating and re-using said storage.
//
// In addition, storage "re-use"/invalidation can happen even if no storage is (de-)allocated.
// For example, if we had an [Option] which contained a `Some(v)` where `v` is pinned, then `v`
// would be invalidated by setting that option to `None`.
//
// Similarly, if a [Vec] was used to store pinned values and [Vec.setLen] was used to
// manually "kill" some elements of a vector, all of the items "killed" would become invalidated,
// which would be *undefined behavior* if those items were pinned.
//
// Both of these cases are somewhat contrived, but it is crucial to remember that [Pin]ned data
// *must* be [drop]ped before it is invalidated; not just to prevent memory leaks, but as a
// matter of soundness. As a corollary, the following code can *never* be made safe:
//
// ```kotlin
// // Pin something inside a `ManuallyDrop`. This is fine on its own.
// var pin: Pin<Type> = Holder.pin(Type)
// // However, creating a pinning mutable reference to the type *inside*
// // the `ManuallyDrop` is not!
// val inner: Pin<Type> = Pin.mapUncheckedMut(pin.asMut(), { x -> x })
// ```
//
// Because [mem.ManuallyDrop] inhibits the destructor of `Type`, it won't get run when the
// <code>[Holder]<[ManuallyDrop]\<Type>></code> is dropped, thus violating the drop guarantee of the
// <code>[Pin]<[&mut] Type>></code>.
//
// Of course, *leaking* memory in such a way that its underlying storage will never get invalidated
// or re-used is still fine: [mem.forget]ing a [Box.T] prevents its storage from ever getting
// re-used, so the [drop] guarantee is still satisfied.
//
// # Implementing an address-sensitive type.
//
// This section goes into detail on important considerations for implementing your own
// address-sensitive types, which are different from merely using [Pin.Ptr] in a generic
// way.
//
// ## Implementing [Drop] for types with address-sensitive states
// [drop-impl]: self#implementing-drop-for-types-with-address-sensitive-states
//
// The [drop] function takes [mut.this], but this is called *even if that `self` has been
// pinned*! Implementing [Drop] for a type with address-sensitive states requires some care, because if `self` was
// indeed in an address-sensitive state before [drop] was called, it is as if the compiler
// automatically called [Pin.getUncheckedMut].
//
// This can never cause a problem in purely safe code because creating a pinning pointer to
// a type which has address-sensitive states (and thus does not implement `Unpin`) requires `unchecked`,
// but it is important to note that choosing to take advantage of pinning-related guarantees
// to justify validity in the implementation of your type has consequences for that type's
// [`Drop`][Drop] implementation as well: if an element of your type could have been pinned,
// you must treat [`Drop`][Drop] as implicitly taking <code>self: [Pin]<[&mut] Self></code>.
//
// You should implement [Drop] as follows:
//
// ```kotlin
// fun Type.drop() {
//     fun innerDrop(thisRef: Pin<Type>) {
//         // Actual drop code goes here.
//     }
//     // `newUnchecked` is okay because we know this value is never used
//     // again after being dropped.
//     innerDrop(Pin.newUnchecked(this))
// }
// ```
//
// The function `innerDrop` has the signature that [drop] *should* have in this situation.
// This makes sure that you do not accidentally use `self`/`this` in a way that is in conflict
// with pinning's invariants.
//
// Moreover, if your type is [`#[repr(packed)]`][packed], the compiler will automatically
// move fields around to be able to drop them. It might even do
// that for fields that happen to be sufficiently aligned. As a consequence, you cannot use
// pinning with a [`#[repr(packed)]`][packed] type.
//
// ### Implementing [Drop] for pointer types which will be used as [Pin]ning pointers
//
// It should further be noted that creating a pinning pointer of some type `Ptr` *also* carries
// with it implications on the way that `Ptr` type must implement [Drop]
// (as well as [Deref] and [DerefMut])! When implementing a pointer type that may be used as
// a pinning pointer, you must also take the same care described above not to *move* out of or
// otherwise invalidate the pointee during [Drop], [Deref], or [DerefMut]
// implementations.
//
// ## "Assigning" pinned data
//
// Although in general it is not valid to swap data or assign through a [Pin.Ptr] for the same
// reason that reusing a pinned object's memory is invalid, it is possible to do validly when
// implemented with special care for the needs of the exact data structure which is being
// modified. For example, the assigning function must know how to update all uses of the pinned
// address (and any other invariants necessary to satisfy validity for that type). For
// [Unmovable] (from the example above), we could write an assignment function like so:
//
// ```kotlin
// import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
// import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
//
// // Copies the contents of `src` into `self`, fixing up the self-pointer
// // in the process.
// fun Unmovable.assign(src: Pin<Unmovable>) {
//     run {
//         val unpinnedSelf = Pin.intoInnerUnchecked(this)
//         val unpinnedSrc = Pin.intoInnerUnchecked(src)
//         unpinnedSelf = Unmovable(data = unpinnedSrc.data, slice = NonNull.from(listOf()), pin = PhantomPinned)
//         val dataPtr = constPtrFromRustCast(unpinnedSrc.data.asPtr())
//         val slicePtr = constPtrFromRustCast(unpinnedSrc.slice.asPtr())
//         val offset = (((slicePtr).rawAddress - (dataPtr).rawAddress) / (PrimitiveLayouts.U8).strideBytes).toInt()
//         val len = unpinnedSrc.slice.asPtr().len()
//         unpinnedSelf.slice = NonNull.from(unpinnedSelf.data[offset until offset + len])
//     }
// }
// ```
//
// Even though we can't have the compiler do the assignment for us, it's possible to write
// such specialized functions for types that might need it.
//
// Note that it _is_ possible to assign generically through a [Pin.Ptr] by way of [Pin.set].
// This does not violate any guarantees, since it will run [drop] on the pointee value before
// assigning the new value. Thus, the [drop] implementation still has a chance to perform the
// necessary notifications to dependent values before the memory location of the original pinned
// value is overwritten.
//
// ## Projections and Structural Pinning
// [structural-pinning]: self#projections-and-structural-pinning
//
// With ordinary structs, it is natural that we want to add *projection* methods that allow
// borrowing one or more of the inner fields of a struct when the caller has access to a
// borrow of the whole struct:
//
// ```kotlin
// open class Struct(public var field: Field) {
//     public open fun field(): Field {
//         return this.field
//     }
// }
// ```
//
// When working with address-sensitive types, it's not obvious what the signature of these
// functions should be. If `field` takes <code>self: [Pin]<[Struct][&mut]></code>, should it
// return [mut.Field] or <code>[Pin]<[mut.Field]></code>? This question also arises with
// `sealed class`s and wrapper types like [Vec.T], [Box.T], and [RefCell.T]. (This question
// applies just as well to shared references, but we'll examine the more common case of mutable
// references for illustration)
//
// It turns out that it's up to the author of `Struct` to decide which type the "projection"
// should produce. The choice must be *consistent* though: if a pin is projected to a field
// in one place, then it should very likely not be exposed elsewhere without projecting the
// pin.
//
// As the author of a data structure, you get to decide for each field whether pinning
// "propagates" to this field or not. Pinning that propagates is also called "structural",
// because it follows the structure of the type.
//
// This choice depends on what guarantees you need from the field for your [unsafe] code to work.
// If the field is itself address-sensitive, or participates in the parent struct's address
// sensitivity, it will need to be structurally pinned.
//
// A useful test is if [unsafe] code that consumes <code>[Pin]\<[Struct][&mut]></code>
// also needs to take note of the address of the field itself, it may be evidence that that field
// is structurally pinned. Unfortunately, there are no hard-and-fast rules.
//
// ### Choosing pinning *not to be* structural for `field`...
//
// While counter-intuitive, it's often the easier choice: if you do not expose a
// <code>[Pin]<[&mut] Field></code>, you do not need to be careful about other code
// moving out of that field, you just have to ensure that you never create a pinning
// reference to that field. This does of course also mean that if you decide a field does not
// have structural pinning, you must not write [unsafe] code that assumes (invalidly) that the
// field *is* structurally pinned!
//
// Fields without structural pinning may have a projection method that turns
// <code>[Pin]<[&mut] Struct></code> into [mut.Field]:
//
// ```kotlin
// fun Struct.field(): Field {
//     // This is okay because `field` is never considered pinned, therefore we do not
//     // need to uphold any pinning guarantees for this field in particular. Of course,
//     // we must not elsewhere assume this field *is* pinned if we choose to expose
//     // such a method!
//     return this.getUncheckedMut().field
// }
// ```
//
// You may also in this situation <code>impl [Unpin] for Struct ()</code> *even if* the type of
// `field` is not [Unpin]. Since we have explicitly chosen not to care about pinning guarantees
// for `field`, the way `field`s type interacts with pinning is no longer relevant in the
// context of its use in `Struct`.
//
// ### Choosing pinning *to be* structural for `field`...
//
// The other option is to decide that pinning is "structural" for `field`,
// meaning that if the struct is pinned then so is the field.
//
// This allows writing a projection that creates a <code>[Pin]<[mut.Field]></code>, thus
// witnessing that the field is pinned:
//
// ```kotlin
// fun Struct.field(): Pin<Field> {
//     // This is okay because `field` is pinned when `self` is.
//     return this.mapUncheckedMut({ s -> s.field })
// }
// ```
//
// Structural pinning comes with a few extra requirements:
//
// 1.  *Structural [Unpin].* A struct can be [Unpin] only if all of its
//     structurally-pinned fields are, too. This is [Unpin]s behavior by default.
//     However, as a library author, it is your responsibility not to write something like
//     <code>impl\<T> [Unpin] for Struct\<T> ()</code> and then offer a method that provides
//     structural pinning to an inner field of `T`, which may not be [Unpin]! (Adding *any*
//     projection operation requires unchecked code, so the fact that [Unpin] is a safe trait does
//     not break the principle that you only have to worry about any of this if you use
//     [unsafe])
//
// 2.  *Pinned Destruction.* As discussed [above][drop-impl], [drop] takes
//     [mut.this], but the struct (and hence its fields) might have been pinned
//     before. The destructor must be written as if its argument was
//     <code>self: [Pin]\<[mut.Self]></code>, instead.
//
//     As a consequence, the struct *must not* be [`#[repr(packed)]`][packed].
//
// 3.  *Structural Notice of Destruction.* You must uphold the
//     [`Drop` guarantee][drop-guarantee]: once your struct is pinned, the struct's storage cannot
//     be re-used without calling the structurally-pinned fields' destructors, as well.
//
//     This can be tricky, as witnessed by [VecDeque.T]: the destructor of [VecDeque.T]
//     can fail to call [drop] on all elements if one of the destructors panics. This violates
//     the [`Drop` guarantee][drop-guarantee], because it can lead to elements being deallocated
//     without their destructor being called.
//
//     [VecDeque.T] has no pinning projections, so its destructor is sound. If it wanted
//     to provide such structural pinning, its destructor would need to abort the process if any
//     of the destructors panicked.
//
// 4.  You must not offer any other operations that could lead to data being *moved* out of
//     the structural fields when your type is pinned. For example, if the struct contains an
//     [Option.T] and there is a [`take`][Option.take]-like operation with type
//     <code>fn([Pin]<[Struct\<T>][&mut]>) -> [Option.T]</code>,
//     then that operation can be used to move a `T` out of a pinned `Struct<T>` – which
//     means pinning cannot be structural for the field holding this data.
//
//     For a more complex example of moving data out of a pinned type,
//     imagine if [RefCell.T] had a method
//     <code>fn getPinMut(self: [Pin]<[mut.Self]>) -> [Pin]<[mut.T]></code>.
//     Then we could do the following:
//     ```compile_fail
//     fn exploitRefCell<T>(mut rc: Pin<RefCell<T>>) {
//         // Here we get pinned access to the `T`.
//         let _: Pin<T> = rc.asMut().getPinMut();
//
//         // And here we have `T` to the same data.
//         let shared: &RefCell<T> = rc.intoRef().getRef();
//         let mut borrow = shared.borrowMut();
//         let content = *borrow;
//     }
//     ```
//     This is catastrophic: it means we can first pin the content of the
//     [RefCell.T] (using <code>[RefCell]::getPinMut</code>) and then move that
//     content using the mutable reference we got later.
//
// ### Structural Pinning examples
//
// For a type like [Vec.T], both possibilities (structural pinning or not) make
// sense. A [Vec.T] with structural pinning could have `getPin`/`getPinMut`
// methods to get pinning references to elements. However, it could *not* allow calling
// [`pop`][Vec.pop] on a pinned [Vec.T] because that would move the (structurally
// pinned) contents! Nor could it allow [`push`][Vec.push], which might reallocate and thus also
// move the contents.
//
// A [Vec.T] without structural pinning could
// <code>impl\<T> [Unpin] for [Vec.T]</code>, because the contents are never pinned
// and the [Vec.T] itself is fine with being moved as well.
// At that point pinning just has no effect on the vector at all.
//
// In the standard library, pointer types generally do not have structural pinning,
// and thus they do not offer pinning projections. This is why <code>[Box.T]: [Unpin]</code>
// holds for all `T`. It makes sense to do this for pointer types, because moving the
// [Box.T] does not actually move the `T`: the [Box.T] can be freely
// movable (aka [Unpin]) even if the `T` is not. In fact, even <code>[Pin]<[Box.T]></code> and
// <code>[Pin]<[mut.T]></code> are always [Unpin] themselves, for the same reason:
// their contents (the `T`) are pinned, but the pointers themselves can be moved without moving
// the pinned data. For both [Box.T] and <code>[Pin]<[Box.T]></code>,
// whether the content is pinned is entirely independent of whether the
// pointer is pinned, meaning pinning is *not* structural.
//
// When implementing a [Future] combinator, you will usually need structural pinning
// for the nested futures, as you need to get pinning ([Pin]-wrapped) references to them to
// call [poll]. But if your combinator contains any other data that does not need to be pinned,
// you can make those fields not structural and hence freely access them with a
// mutable reference even when you just have <code>[Pin]<[mut.Self]></code>
// (such as in your own [poll] implementation).
//
// [mut.T]: &mut
// [mut.this]: &mut
// [mut.Self]: &mut
// [mut.Field]: &mut
// [drop]: Drop.drop
// [drop-impl]: #drop-implementation
// [drop-guarantee]: #drop-guarantee
// [&]: reference "shared reference"
// [&mut]: reference "mutable reference"
// [unsafe]: ../../std/keyword.unchecked.html "keyword unchecked"
// [packed]: https://doc.rust-lang.org/nomicon/other-reprs.html#reprpacked
// [std.alloc]: ../../std/alloc/index.html
// [Box.T]: ../../std/boxed/struct.Holder.html
// [Holder]: ../../std/boxed/struct.Holder.html "Holder"
// [Box]: ../../std/boxed/struct.Holder.html "Holder"
// [Rc.T]: ../../std/rc/struct.Rc.html
// [Rc]: ../../std/rc/struct.Rc.html "rc.Rc"
// [Vec.T]: ../../std/vec/struct.Vec.html
// [Vec]: ../../std/vec/struct.Vec.html "Vec"
// [Vec]: ../../std/vec/struct.Vec.html "Vec"
// [Vec.setLen]: ../../std/vec/struct.Vec.html#method.setLen "Vec.setLen"
// [Vec.pop]: ../../std/vec/struct.Vec.html#method.pop "Vec.pop"
// [Vec.push]: ../../std/vec/struct.Vec.html#method.push "Vec.push"
// [Vec.setLen]: ../../std/vec/struct.Vec.html#method.setLen
// [VecDeque.T]: ../../std/collections/struct.VecDeque.html
// [VecDeque]: ../../std/collections/struct.VecDeque.html "collections.VecDeque"
// [String]: ../../std/string/struct.String.html "String"

//
// Note: the `Clone` derive below is sound because either `Ptr: PinSafePointer`
// or the pointee is `Unpin`.

/**
 * A pointer which pins its pointee in place.
 *
 * [Pin] is a wrapper around some kind of pointer `Ptr` which makes that pointer "pin" its
 * pointee value in place, thus preventing the value referenced by that pointer from being moved
 * or otherwise invalidated at that place in memory unless it implements [Unpin].
 *
 * *See the [`pin` module] documentation for a more thorough exploration of pinning.*
 *
 * ## Pinning values with [Pin.Ptr]
 *
 * In order to pin a value, we wrap a *pointer to that value* (of some type `Ptr`) in a
 * [Pin.Ptr]. [Pin.Ptr] can wrap any pointer type, forming a promise that the **pointee**
 * will not be *moved* or [otherwise invalidated][subtle-details]. If the pointee value's type
 * implements [Unpin], we are free to disregard these requirements entirely and can wrap any
 * pointer to that value in [Pin] directly via [Pin.new]. If the pointee value's type does
 * not implement [Unpin], then Rust will not let us use the [Pin.new] function directly and
 * we'll need to construct a [Pin]-wrapped pointer in one of the more specialized manners
 * discussed below.
 *
 * We call such a [Pin]-wrapped pointer a **pinning pointer** (or pinning ref, or pinning
 * [Box], etc.) because its existence is the thing that is pinning the underlying pointee in
 * place: it is the metaphorical "pin" securing the data in place on the pinboard (in memory).
 *
 * It is important to stress that the thing in the [Pin] is not the value which we want to pin
 * itself, but rather a pointer to that value! A [Pin.Ptr] does not pin the `Ptr` but rather
 * the pointer's ***pointee** value*.
 *
 * The most common set of types which require pinning related guarantees for soundness are the
 * compiler-generated state machines that implement [Future] for the return value of
 * `async fun`s. These compiler-generated [Future]s may contain self-referential pointers, one
 * of the most common use cases for [Pin]. More details on this point are provided in the
 * [`pin` module] docs, but suffice it to say they require the guarantees provided by pinning to
 * be implemented soundly.
 *
 * This requirement for the implementation of `async fun`s means that the [Future] trait
 * requires all calls to [poll] to use a <code>self: [Pin]\<Self></code> parameter instead
 * of the usual `self`. Therefore, when manually polling a future, you will need to pin it
 * first.
 *
 * You may notice that `async fun`-sourced [Future]s are only a small percentage of all
 * [Future]s that exist, yet we had to modify the signature of [poll] for all [Future]s
 * to accommodate them. This is unfortunate, but there is a way that the language attempts to
 * alleviate the extra friction that this API choice incurs: the [Unpin] trait.
 *
 * The vast majority of Rust types have no reason to ever care about being pinned. These
 * types implement the [Unpin] trait, which entirely opts all values of that type out of
 * pinning-related guarantees. For values of these types, pinning a value by pointing to it with a
 * [Pin.Ptr] will have no actual effect.
 *
 * The reason this distinction exists is exactly to allow APIs like [Future.poll] to take a
 * [Pin.Ptr] as an argument for all types while only forcing [Future] types that actually
 * care about pinning guarantees pay the ergonomics cost. For the majority of [Future] types
 * that don't have a reason to care about being pinned and therefore implement [Unpin], the
 * <code>[Pin]\<Self></code> will act exactly like a regular `Self`, allowing direct
 * access to the underlying value. Only types that *don't* implement [Unpin] will be restricted.
 *
 * ### Pinning a value of a type that implements [Unpin]
 *
 * If the type of the value you need to "pin" implements [Unpin], you can trivially wrap any
 * pointer to that value in a [Pin] by calling [Pin.new].
 *
 * ```kotlin
 * import std.pin.Pin
 *
 * // Create a value of a type that implements `Unpin`
 * var unpinFuture = ready(5)
 * // Pin it by creating a pinning mutable reference to it (ready to be `poll`ed!)
 * val myPinnedUnpinFuture: Pin<Any?> = Pin.new(unpinFuture)
 * ```
 *
 * ### Pinning a value inside a [Box]
 *
 * The simplest and most flexible way to pin a value that does not implement [Unpin] is to put
 * that value inside a [Box] and then turn that [Box] into a "pinning [Box]" by wrapping it
 * in a [Pin]. You can do both of these in a single step using [Box.pin]. Let's see an
 * example of using this flow to pin a [Future] returned from calling an `async fun`, a common
 * use case as described above.
 *
 * ```kotlin
 * import std.pin.Pin
 *
 * fun addOne(x: UInt): UInt {
 *     return x + 1u
 * }
 * // Call the async function to get a future back
 * val fut = addOne(42u)
 * // Pin the future inside a pinning box
 * val pinnedFut: Pin<Holder> = Holder.pin(fut)
 * ```
 *
 * If you have a value which is already boxed, for example a [`Box.dyn.Future`][Holder], you can pin
 * that value in-place at its current memory address using [Box.intoPin].
 *
 * ```kotlin
 * import std.future.Future
 * import std.pin.Pin
 *
 * fun addOne(x: UInt): UInt {
 *     return x + 1u
 * }
 * fun boxedAddOne(x: UInt): Future<UInt> {
 *     return addOne(x)
 * }
 * val boxedFut = boxedAddOne(42u)
 * // Pin the future inside the existing box
 * val pinnedFut: Pin<Holder> = Holder.intoPin(boxedFut)
 * ```
 *
 * There are similar pinning methods offered on the other standard library smart pointer types
 * as well, like [Rc] and [Arc].
 *
 * ### Pinning a value on the stack using [pin]
 *
 * There are some situations where it is desirable or even required (for example, in a `@noStd`
 * context where you don't have access to the standard library or allocation in general) to
 * pin a value which does not implement [Unpin] to its location on the stack. Doing so is
 * possible using the [pin] macro. See its documentation for more.
 *
 * ## Layout and ABI
 *
 * [Pin.Ptr] is guaranteed to have the same memory layout and ABI[^noalias] as `Ptr`.
 *
 * [^noalias]: There is a bit of nuance here that is still being decided about whether the
 * aliasing semantics of `Pin<T>` should be different than `T`, but this is true as of
 * today.
 *
 * [`pin` module]: self "pin module"
 * [Rc]: ../../std/rc/struct.Rc.html "Rc"
 * [Arc]: ../../std/sync/struct.Arc.html "Arc"
 * [Holder]: ../../std/boxed/struct.Holder.html "Holder"
 * [Box]: ../../std/boxed/struct.Holder.html "Holder"
 * [Box.pin]: ../../std/boxed/struct.Holder.html#method.pin "Box.pin"
 * [Box.intoPin]: ../../std/boxed/struct.Holder.html#method.intoPin "Box.intoPin"
 * [subtle-details]: self#subtle-details-and-the-drop-guarantee "pin subtle details"
 * [unsafe]: ../../std/keyword.unchecked.html "keyword unchecked"
 */
public open class Pin<Ptr>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Ptr> = UnresolvedTypeLayout(),
) : DerefPure, LegacyReceiver {
    public var pointer: Ptr
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(pointer: Ptr, fieldLayout: TypeLayout<Ptr> = UnresolvedTypeLayout()) : this(allocateNativeCell(pointer, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Pin<Ptr>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Pin<Ptr>(ptr, layout) }, { it.pointer })

    public open fun clone(): Pin<Ptr> = Pin<Ptr>(pointer, fieldLayout)

    // The following implementations aren't derived in order to avoid soundness
    // issues. `self.pointer` should not be accessible to untrusted trait
    // implementations.
    //
    // See <https://internals.rust-lang.org/t/unsoundness-in-pin/11311/73> and the
    // `PinSafePointer` trait for more details.
    public open fun eq(other: Pin<Q>): Boolean = Ptr.Target.eq(this, other)

    public open fun ne(other: Pin<Q>): Boolean = Ptr.Target.ne(this, other)

    public open fun partialCmp(other: Pin<Q>): cmp.Ordering? = Ptr.Target.partialCmp(this, other)

    public open fun lt(other: Pin<Q>): Boolean = Ptr.Target.lt(this, other)

    public open fun le(other: Pin<Q>): Boolean = Ptr.Target.le(this, other)

    public open fun gt(other: Pin<Q>): Boolean = Ptr.Target.gt(this, other)

    public open fun ge(other: Pin<Q>): Boolean = Ptr.Target.ge(this, other)

    public open fun cmp(other: Pin<Ptr>): cmp.Ordering = Ptr.Target.cmp(this, other)

    public override fun hashCode(): Int {
        var state = 0
        Ptr.Target.hash(this, state)
        return state
    }

    /**
     * Gets a shared reference to the pinned value this [Pin] points to.
     *
     * This is a generic method to go from `Pin<Pointer<T>>` to `Pin<T>`.
     * It is safe because, as part of the contract of `Pin.newUnchecked`,
     * the pointee cannot move after `Pin<Pointer<T>>` got created.
     * "Malicious" implementations of `Pointer.Deref` are likewise
     * ruled out by the contract of `Pin.newUnchecked`.
     */
    public open fun asRef(): Pin<Target> {
        // SAFETY: see documentation on this function
        return Pin.newUnchecked(this.pointer, fieldLayout = this.fieldLayout)
    }

    // These methods being in a `Ptr: DerefMut` impl block concerns semver stability.
    // Currently, calling e.g. `.set()` on a `Pin<T>` sees that `Ptr: DerefMut`
    // doesn't hold, and goes to check for a `.set()` method on `T`. But, if the
    // `where Ptr: DerefMut` bound is moved to the method, rustc sees the impl block
    // as a valid candidate, and doesn't go on to check other candidates when it
    // sees that the bound on the method.

    /**
     * Gets a mutable reference to the pinned value this `Pin<Ptr>` points to.
     *
     * This is a generic method to go from `Pin<Pointer<T>>` to `Pin<T>`.
     * It is safe because, as part of the contract of `Pin.newUnchecked`,
     * the pointee cannot move after `Pin<Pointer<T>>` got created.
     * "Malicious" implementations of `Pointer.DerefMut` are likewise
     * ruled out by the contract of `Pin.newUnchecked`.
     *
     * This method is useful when doing multiple calls to functions that consume the
     * pinning pointer.
     *
     * # Example
     *
     * ```kotlin
     * import std.pin.Pin
     *
     * fun Type.method() {
     *     // do something
     * }
     *
     * fun Type.callMethodTwice() {
     *     // `method` consumes `self`, so reborrow the `Pin<Self>` via `asMut`.
     *     this.asMut().method()
     *     this.asMut().method()
     * }
     * ```
     */
    public open fun asMut(): Pin<Target> {
        // SAFETY: see documentation on this function
        return Pin.newUnchecked(this.pointer, fieldLayout = this.fieldLayout)
    }

    /**
     * Gets `Pin<T>` to the underlying pinned value from this nested `Pin`-pointer.
     *
     * This is a generic method to go from `Pin<Pin<Pointer<T>>>` to `Pin<T>`. It is
     * safe because the existence of a `Pin<Pointer<T>>` ensures that the pointee, `T`, cannot
     * move in the future, and this method does not enable the pointee to move. "Malicious"
     * implementations of `Ptr.DerefMut` are likewise ruled out by the contract of
     * `Pin.newUnchecked`.
     */
    public open fun asDerefMut(): Pin<Target> {
        // SAFETY: What we're asserting here is that going from
        //
        // Pin<Pin<Ptr>>
        //
        // to
        //
        // Pin<Ptr.Target>
        //
        // is safe.
        //
        // We need to ensure that two things hold for that to be the case:
        //
        // 1) Once we give out a `Pin<Ptr.Target>`, a `Ptr.Target` will not be given out.
        // 2) By giving out a `Pin<Ptr.Target>`, we do not risk violating
        // `Pin<Pin<Ptr>>`
        //
        // The existence of `Pin<Ptr>` is sufficient to guarantee #1: since we already have a
        // `Pin<Ptr>`, it must already uphold the pinning guarantees, which must mean that
        // `Pin<Ptr.Target>` does as well, since `Pin.asMut` is safe. We do not have to rely
        // on the fact that `Ptr` is _also_ pinned.
        //
        // For #2, we need to ensure that code given a `Pin<Ptr.Target>` cannot cause the
        // `Pin<Ptr>` to move? That is not possible, since `Pin<Ptr.Target>` no longer retains
        // any access to the `Ptr` itself, much less the `Pin<Ptr>`.
        return this.getUncheckedMut().asMut()
    }

    /**
     * Assigns a new value to the memory location pointed to by the `Pin<Ptr>`.
     *
     * This overwrites pinned data, but that is okay: the original pinned value's destructor gets
     * run before being overwritten and the new value is also a valid value of the same type, so
     * no pinning invariant is violated. See [the `pin` module documentation][subtle-details]
     * for more information on how this upholds the pinning invariants.
     *
     * # Example
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
     * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
     * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
     * import std.pin.Pin
     *
     * val localStorageL5C0 = AddressableLocal(5u, PrimitiveLayouts.U8)
     * var pinned: Pin<RustMutableReference<UByte>> = Pin.new(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
     * println("$pinned")
     * // 5
     * pinned.set(10)
     * println("$pinned")
     * // 10
     * ```
     *
     * [subtle-details]: self#subtle-details-and-the-drop-guarantee
     */
    public open fun set(value: Target) {
        (this.pointer) = value
    }

    public open fun deref(): Target = Pin.getRef(this.asRef())

    public open fun derefMut(typeLayoutPtr: TypeLayout<Ptr> = UnresolvedTypeLayout()): Target {
        // SAFETY: Pin and PinHelper have the same layout, so this is equivalent to
        // `self.pointer` which is safe because `Target: Unpin`.
        return (RustMutableReference(MutPtr((((MutPtr(this.fieldStorage.rawAddress)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), TransparentTypeLayout(typeLayoutPtr, { ptr, layout -> PinHelper<Ptr>(ptr, layout) }, { it.pointer }))).derefMut()
    }

    // The following implementations allow untrusted trait implementations to access
    // `self.pointer`, which is only sound because these traits are mentioned in
    // the safety requirements of `PinSafePointer`.
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.pointer).toString()); Result.Ok(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.pointer).toString()); Result.Ok(Unit) }

    public open fun fmtPointer(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.pointer).toString()); Result.Ok(Unit) }

    // Note: this means that any impl of `CoerceUnsized` that allows coercing from
    // a type that impls `Deref<Target = impl Unpin>` to a type that impls
    // `Deref<Target = Unpin>` is unsound. Any such impl would probably be unsound
    // for other reasons, though, so we just need to take care not to allow such
    // impls to land in std.

    public companion object {
        /**
         * Constructs a new `Pin<Ptr>` around a pointer to some data of a type that
         * implements [Unpin].
         *
         * Unlike `Pin.newUnchecked`, this method is safe because the pointer
         * `Ptr` dereferences to an [Unpin] type, which cancels the pinning guarantees.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
         * import std.pin.Pin
         *
         * val localStorageL5C0 = AddressableLocal(5u, PrimitiveLayouts.U8)
         * // Since `val` doesn't care about being moved, we can safely create a "facade" `Pin`
         * // which will allow `val` to participate in `Pin`-bound apis  without checking that
         * // pinning guarantees are actually upheld.
         * var pinned: Pin<RustMutableReference<UByte>> = Pin.new(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
         * ```
         */
        public fun <Ptr> new(pointer: Ptr, fieldLayout: TypeLayout<Ptr> = UnresolvedTypeLayout()): Pin<Ptr> {
            // SAFETY: the value pointed to is `Unpin`, and so has no requirements
            // around pinning.
            return Pin.newUnchecked(pointer, fieldLayout = fieldLayout)
        }

        /**
         * Unwraps this `Pin<Ptr>`, returning the underlying pointer.
         *
         * Doing this operation safely requires that the data pointed at by this pinning pointer
         * implements [Unpin] so that we can ignore the pinning invariants when unwrapping it.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         * import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
         * import std.pin.Pin
         *
         * val localStorageL5C0 = AddressableLocal(5u, PrimitiveLayouts.U8)
         * val pinned: Pin<RustMutableReference<UByte>> = Pin.new(RustMutableReference(localStorageL5C0.asMutPtr(), localStorageL5C0.layout))
         * // Unwrap the pin to get the underlying mutable reference to the value. We can do
         * // this because `val` doesn't care about being moved, so the `Pin` was just
         * // a "facade" anyway.
         * val r = Pin.intoInner(pinned)
         * check(r == 5)
         * ```
         */
        public fun <Ptr> intoInner(pin: Pin<Ptr>): Ptr = pin.pointer

        /**
         * Constructs a new `Pin<Ptr>` around a reference to some data of a type that
         * may or may not implement [Unpin].
         *
         * If `pointer` dereferences to an [Unpin] type, [Pin.new] should be used
         * instead.
         *
         * # Safety
         *
         * This constructor is unchecked because we cannot guarantee that the data
         * pointed to by `pointer` is pinned. At its core, pinning a value means making the
         * guarantee that the value's data will not be moved nor have its storage invalidated until
         * it gets dropped. For a more thorough explanation of pinning, see the [`pin` module docs].
         *
         * If the caller that is constructing this `Pin<Ptr>` does not ensure that the data `Ptr`
         * points to is pinned, that is a violation of the API contract and may lead to undefined
         * behavior in later (even safe) operations.
         *
         * By using this method, you are also making a promise about several trait
         * implementations of `Ptr` itself, if they exist. Most importantly, they
         * must not move out of their `self` arguments: `Pin.asMut` and
         * `Pin.asRef` will call `DerefMut.derefMut` and `Deref.deref` *on the
         * pointer type `Ptr`* and expect these methods to uphold the pinning
         * invariants. These requirements are specified in more detail on the
         * [PinSafePointer] trait, and `Ptr` must abide by the safety
         * requirements of that trait.
         *
         * Moreover, by calling this method you promise that the reference `Ptr`
         * dereferences to will not be moved out of again; in particular, it
         * must not be possible to obtain a `Ptr.Target` and then
         * move out of that reference (using, for example [mem.swap]).
         *
         * For example, calling `Pin.newUnchecked` on an `T` is unchecked because
         * while you are able to pin it for the given scope `a`, you have no control
         * over whether it is kept pinned once `a` ends, and therefore cannot uphold the
         * guarantee that a value, once pinned, remains pinned until it is dropped:
         *
         * ```kotlin
         * import std.mem
         * import std.pin.Pin
         *
         * fun <T> movePinnedRef(a: T, b: T) {
         *     run {
         *         val p: Pin<T> = Pin.newUnchecked(a)
         *         // This should mean the pointee `a` can never move again.
         *     }
         *     mem.swap(a, b)
         *     // Potential UB down the road ⚠️
         *     // The address of `a` changed to `b`s stack slot, so `a` got moved even
         *     // though we have previously pinned it! We have violated the pinning API contract.
         * }
         * ```
         * A value, once pinned, must remain pinned until it is dropped (unless its type implements
         * `Unpin`). Because `Pin<T>` does not own the value, dropping the `Pin` will not drop
         * the value and will not end the pinning contract. So moving the value after dropping the
         * `Pin<T>` is still a violation of the API contract.
         *
         * Similarly, calling `Pin.newUnchecked` on an `Rc<T>` is unchecked because there could be
         * aliases to the same data that are not subject to the pinning restrictions:
         * ```kotlin
         * import std.pin.Pin
         *
         * fun <T> movePinnedRc(x: T) {
         *     // This should mean the pointee can never move again.
         *     val pin = Pin.newUnchecked(x)
         *     run {
         *         val p: Pin<T> = pin.asRef()
         *         // ...
         *     }
         *     (pin).let { }
         *     val content = Rc.getMut(x).unwrap()
         *     // Potential UB down the road ⚠️
         *     // Now, if `x` was the only reference, we have a mutable reference to
         *     // data that we pinned above, which we could use to move it as we have
         *     // seen in the previous example. We have violated the pinning API contract.
         * }
         * ```
         *
         * ## Pinning of closure captures
         *
         * Particular care is required when using `Pin.newUnchecked` in a closure:
         * `Pin.newUnchecked(var)` where `var` is a by-value (moved) closure capture
         * implicitly makes the promise that the closure itself is pinned, and that *all* uses
         * of this closure capture respect that pinning.
         * ```kotlin
         * import std.future.Future
         * import std.pin.Pin
         * import std.task.Context
         *
         * fun movePinnedClosure(x: Future, cx: Context) {
         *     // Create a closure that moves `x`, and then internally uses it in a pinned way.
         *     var closure = { run {
         *         val ignore = Pin.newUnchecked(x).poll(cx)
         *     } }
         *     // Call the closure, so the future can assume it has been pinned.
         *     closure()
         *     // Move the closure somewhere else. This also moves `x`!
         *     var moved = closure
         *     // Calling it again means we polled the future from two different locations,
         *     // violating the pinning API contract.
         *     moved()
         *     // Potential UB ⚠️
         * }
         * ```
         * When passing a closure to another API, it might be moving the closure any time, so
         * `Pin.newUnchecked` on closure captures may only be used if the API explicitly documents
         * that the closure is pinned.
         *
         * The better alternative is to avoid all that trouble and do the pinning in the outer function
         * instead (here using the [`pin`][pin.pin] macro):
         * ```kotlin
         * import std.future.Future
         * import std.pin.pin
         * import std.task.Context
         *
         * fun movePinnedClosure(x: Future, cx: Context) {
         *     var x1 = pin(x)
         *     // Create a closure that captures `x: Pin<_>`, which is safe to move.
         *     var closure = {
         *         val ignore = x1.asMut().poll(cx)
         *     }
         *     // Call the closure, so the future can assume it has been pinned.
         *     closure()
         *     // Move the closure somewhere else.
         *     var moved = closure
         *     // Calling it again here is fine (except that we might be polling a future that already
         *     // returned `Poll.Ready`, but that is a separate problem).
         *     moved()
         * }
         * ```
         */
        public fun <Ptr> newUnchecked(pointer: Ptr, fieldLayout: TypeLayout<Ptr> = UnresolvedTypeLayout()): Pin<Ptr> = Pin(pointer = pointer, fieldLayout = fieldLayout)

        /**
         * Unwraps this `Pin<Ptr>`, returning the underlying `Ptr`.
         *
         * # Safety
         *
         * This function is unchecked. You must guarantee that you will continue to
         * treat the pointer `Ptr` as pinned after you call this function, so that
         * the invariants on the `Pin` type can be upheld. If the code using the
         * resulting `Ptr` does not continue to maintain the pinning invariants that
         * is a violation of the API contract and may lead to undefined behavior in
         * later (safe) operations.
         *
         * Note that you must be able to guarantee that the data pointed to by `Ptr`
         * will be treated as pinned all the way until its `drop` handler is complete!
         *
         * *For more information, see the [`pin` module docs][self]*
         *
         * If the underlying data is [Unpin], [Pin.intoInner] should be used
         * instead.
         */
        public fun <Ptr> intoInnerUnchecked(pin: Pin<Ptr>): Ptr = pin.pointer
    }
}

/**
 * Constructs a new pin by mapping the interior value.
 *
 * For example, if you wanted to get a `Pin` of a field of something,
 * you could use this to get access to that field in one line of code.
 * However, there are several gotchas with these "pinning projections";
 * see the [`pin` module] documentation for further details on that topic.
 *
 * # Safety
 *
 * This function is unchecked. You must guarantee that the data you return
 * will not move so long as the argument value does not move (for example,
 * because it is one of the fields of that value), and also that you do
 * not move out of the argument you receive to the interior function.
 *
 * [`pin` module]: self#projections-and-structural-pinning
 */
public fun <T, U> Pin<T>.mapUnchecked(func: (T) -> U): Pin<U> {
    val pointer = this.pointer
    val newPointer = func(pointer)
    // SAFETY: the safety contract for `newUnchecked` must be
    // upheld by the caller.
    return Pin.newUnchecked(newPointer, fieldLayout = UnresolvedTypeLayout<T>())
}

/**
 * Gets a shared reference out of a pin.
 *
 * This is safe because it is not possible to move out of a shared reference.
 * It may seem like there is an issue here with interior mutability: in fact,
 * it *is* possible to move a `T` out of a `RefCell<T>`. However, this is
 * not a problem as long as there does not also exist a `Pin<T>` pointing
 * to the inner `T` inside the `RefCell`, and `RefCell<T>` does not let you get a
 * `Pin<T>` pointer to its contents. See the discussion on ["pinning projections"]
 * for further details.
 *
 * Note: `Pin` also implements `Deref` to the target, which can be used
 * to access the inner value. However, `Deref` only provides a reference
 * that lives for as long as the borrow of the `Pin`, not the scope of
 * the reference contained in the `Pin`. This method allows turning the `Pin` into a reference
 * with the same scope as the reference it wraps.
 *
 * ["pinning projections"]: self#projections-and-structural-pinning
 */
public fun <T> Pin<T>.getRef(): T = this.pointer

/**
 * Converts this `Pin<T>` into a `Pin<T>` with the same scope.
 */
public fun <T> Pin<T>.intoRef(): Pin<T> = Pin(pointer = this.pointer, fieldLayout = this.fieldLayout)

/**
 * Gets a mutable reference to the data inside of this `Pin`.
 *
 * This requires that the data inside this `Pin` is `Unpin`.
 *
 * Note: `Pin` also implements `DerefMut` to the data, which can be used
 * to access the inner value. However, `DerefMut` only provides a reference
 * that lives for as long as the borrow of the `Pin`, not the scope of
 * the `Pin` itself. This method allows turning the `Pin` into a reference
 * with the same scope as the original `Pin`.
 */
public fun <T> Pin<T>.getMut(): T = this.pointer

/**
 * Gets a mutable reference to the data inside of this `Pin`.
 *
 * # Safety
 *
 * This function is unchecked. You must guarantee that you will never move
 * the data out of the mutable reference you receive when you call this
 * function, so that the invariants on the `Pin` type can be upheld.
 *
 * If the underlying data is `Unpin`, `Pin.getMut` should be used
 * instead.
 */
public fun <T> Pin<T>.getUncheckedMut(): T = this.pointer

/**
 * Constructs a new pin by mapping the interior value.
 *
 * For example, if you wanted to get a `Pin` of a field of something,
 * you could use this to get access to that field in one line of code.
 * However, there are several gotchas with these "pinning projections";
 * see the [`pin` module] documentation for further details on that topic.
 *
 * # Safety
 *
 * This function is unchecked. You must guarantee that the data you return
 * will not move so long as the argument value does not move (for example,
 * because it is one of the fields of that value), and also that you do
 * not move out of the argument you receive to the interior function.
 *
 * [`pin` module]: self#projections-and-structural-pinning
 */
public fun <T, U> Pin<T>.mapUncheckedMut(func: (T) -> U): Pin<U> {
    // SAFETY: the caller is responsible for not moving the
    // value out of this reference.
    val pointer = this.getUncheckedMut()
    val newPointer = func(pointer)
    // SAFETY: as the value of `this` is guaranteed to not have
    // been moved out, this call to `newUnchecked` is safe.
    return Pin.newUnchecked(newPointer, fieldLayout = UnresolvedTypeLayout<T>())
}

/**
 * Gets a pinning reference from a  reference.
 *
 * This is safe because `T` is borrowed immutably for the `static` scope, which
 * never ends.
 */
public fun <T> Pin.Companion.staticRef(r: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): Pin<T> {
    // SAFETY: The static borrow guarantees the data will not be
    // moved/invalidated until it gets dropped (which is never).
    return Pin.newUnchecked(r, fieldLayout = fieldLayout)
}

/**
 * Gets a pinning mutable reference from a static mutable reference.
 *
 * This is safe because `T` is borrowed for the `static` scope, which
 * never ends.
 */
public fun <T> Pin.Companion.staticMut(r: T, fieldLayout: TypeLayout<T> = UnresolvedTypeLayout()): Pin<T> {
    // SAFETY: The static borrow guarantees the data will not be
    // moved/invalidated until it gets dropped (which is never).
    return Pin.newUnchecked(r, fieldLayout = fieldLayout)
}

/**
 * Helper that prevents downstream crates from implementing `DerefMut` for `Pin`.
 *
 * The `Pin` type implements the unchecked trait `PinSafePointer`, which essentially requires
 * that the type does not have a malicious `Deref` or `DerefMut` impl. However, without this
 * helper module, downstream crates are able to write `implementation DerefMut for Pin<LocalType>` as
 * long as it does not overlap with the impl provided by stdlib. This is because `Pin` is
 * `@fundamental`, so stdlib promises to never implement traits for `Pin` that it does not
 * implement today.
 *
 * However, this is problematic. Downstream crates could implement `DerefMut` for
 * `Pin<LocalType>`, and they could do so maliciously. To prevent this, the implementation for
 * `Pin` delegates to this helper module. Since `helper.Pin` is not `@fundamental`, the
 * orphan rules assume that stdlib might implement `helper.DerefMut` for `helper.Pin<_>` in
 * the future. Because of this, downstream crates can no longer provide an implementation of
 * `DerefMut` for `Pin<_>`, as it might overlap with a trait impl that, according to the
 * orphan rules, the stdlib could introduce without a breaking change in a future release.
 *
 * See <https://github.com/rust-lang/rust/issues/85099> for the issue this fixes.
 */
public open class PinHelper<Ptr>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Ptr> = UnresolvedTypeLayout(),
) : PinDerefMutHelper<Target> {
    public var pointer: Ptr
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(pointer: Ptr, fieldLayout: TypeLayout<Ptr> = UnresolvedTypeLayout()) : this(allocateNativeCell(pointer, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<PinHelper<Ptr>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PinHelper<Ptr>(ptr, layout) }, { it.pointer })

    public override fun derefMut(): Target = this.pointer
}

public interface PinDerefMutHelper<Target> {
    public fun derefMut(): Target
}

/**
 * Trait that indicates that this is a pointer that does not misbehave when
 * combined with [Pin].
 *
 * Note that for backwards compatibility reasons, it is possible to create a
 * [Pin.P] for pointer types `P` that do not implement this trait. However,
 * this can only be done safely if `<P as Deref>.Target` implements `Unpin`,
 * which means that pinning has no effect.
 *
 * # Safety
 *
 * Types that implement this trait must not provide "malicious" implementations
 * of any safe traits used by [Pin].
 *
 * ## The pointer must always reference the same object
 *
 * Calls to [deref]/[derefMut] on the same `Pin<P>` instance must always
 * refer to the same object. That is, the address returned by these methods
 * must not change. This applies even if the pointer type is moved.
 *
 * These coercions must also not change the underlying concrete type. Here, the
 * concrete type of a trait object is the type that the vtable corresponds to.
 * The concrete type of a slice is an array of the same element type and the
 * length specified in the metadata. The concrete type of a sized type is the
 * type itself.
 *
 * As an example, after unsizing coercing a pinned pointer, `derefMut` must
 * not return a `@repr(transparent)` wrapper around the value it referenced
 * before being unsized, even if the address is unchanged.
 *
 * ## The pointer must not move its pointee
 *
 * The [derefMut] method and the pointer type's destructor are called with a
 * `self` receiver, but they must behave as-if it was a `self: Pin<&mut
 * Self>` receiver. That is, they must not move out of the underlying value.
 *
 * As an example, `derefMut` must not invoke `swap` on the inner value.
 *
 * ## Shared access to the pointer
 *
 * If this pointer type uses `P` references as evidence that this value is not
 * pinned, then it must not treat the `self` argument passed to [Clone] or
 * the formatting traits ([fmt.Debug], [fmt.Display], [fmt.Pointer])
 * as such evidence.
 *
 * As an example, given a `Pin<Arc<T>>` there is no way to obtain an `Arc<T>`
 * (note that `Deref` just gives a `T`). Because of this, the [Arc] type can
 * assume that an `Arc<T>` value can only exist if the `T` is not pinned,
 * which justifies the soundness of the [Arc.getMut] method.
 *
 * ## Cloning pinned pointers
 *
 * When a [Pin.P] is cloned, the `P` pointer value returned by `clone` is
 * passed to [Pin.newUnchecked]. The implementation of [Clone] must
 * return a value such that this is sound.
 *
 * For example, when a `Pin<T>` is cloned, the resulting `T` points at the
 * same value. The value is known to be pinned since a `Pin<T>` to it exists,
 * so it is safe to wrap the `T` returned by `clone` in `Pin`.
 *
 * [deref]: Deref.deref
 * [derefMut]: DerefMut.derefMut
 * [clone]: Clone.clone
 * [Arc]: ../../std/sync/struct.Arc.html "Arc"
 * [Arc.getMut]: ../../std/sync/struct.Arc.html#method.getMut "Arc.getMut"
 */
public interface PinSafePointer

// A pointer can only be pin safe if it does not implement certain safe traits
// maliciously. Since `T` is fundamental, downstream crates may be able to
// implement those traits for `LocalType`, so we must carefully check that
// this is not a problem for each trait.
//
// The `T` type always implements [Deref] and [Clone], so despite being
// fundamental, downstream crates cannot implement these traits for
// `LocalType`.
//
// The `T` type has a negative blanket implementations for [DerefMut], so
// downstream crates cannot implement `DerefMut` for `LocalType`.
//
// Conversely, downstream crates are able to implement `Debug` and `Display` for
// `LocalType` as long as `LocalType` does not implement said trait. However,
// the existence of an `&& T` cannot be treated as evidence that the `T` is not
// pinned, so this is not problematic.

// A pointer can only be pin safe if it does not implement certain safe traits
// maliciously. Since `T` is fundamental, downstream crates may be able to
// implement those traits for `LocalType`, so we must carefully check that
// this is not a problem for each trait.
//
// The `T` type always implements [Deref] and [DerefMut], so despite
// being fundamental, downstream crates cannot implement these traits for `&mut
// LocalType`.
//
// The `T` type has a negative blanket implementations for [Clone], so
// downstream crates cannot implement `Clone` for `LocalType`.
//
// Conversely, downstream crates are able to implement `Debug` and `Display`
// for `LocalType` as long as `LocalType` does not implement said trait.
// However, the existence of an `&& T` cannot be treated as evidence that the
// `T` is not pinned, so this is not problematic.

// A pointer can only be pin safe if it does not implement certain safe traits
// maliciously. `Pin` implements these traits by forwarding to `P`, which also
// asserts that these implementations are not malicious, so the implementations
// provided by `core` are ok. However, since `Pin<T>` is fundamental,
// downstream crates may be able to implement those traits for `Pin<LocalType>`
// directly, so we must carefully check that if a downstream crate can
// implement these traits for `Pin<LocalType>`, then this does not lead to any
// problems for the `Pin<Pin<LocalType>>` type.
//
// The `Pin<P>` type only implements `Deref` when `P: Deref`, so downstream
// crates can implement `Deref` for `Pin<LocalType>` in cases where `LocalType:
// !Deref`.However, as`Deref`is a super - interface for`PinSafePointer`, we do
// not assert that `Pin<P>` is pin safe in that scenario.
//
// The `Pin<P>` type only implements `DerefMut` when `P: DerefMut` and
// `P.Target: Unpin`, so normally downstream crates would be able to provide
// an implementation of `DerefMut` for `Pin<LocalType>` when `LocalType` does
// not satisfy those conditions. However, a special Note is used to prevent
// such downstream implementations, so this is not a problem. See
// [#145608](https://github.com/rust-lang/rust/pull/145608) for details.
//
// Conversely, downstream crates are able to implement `Clone`, `Debug` and
// `Display` for `Pin<LocalType>` as long as `LocalType` does not implement
// said trait. However, the existence of an `Pin<P>` cannot be treated as
// evidence that the value is not pinned, so this is not problematic.
//
// Furthermore, in the case of `Clone`, cloning a `Pin<Pin<P>>` will utilize
// `Pin.newUnchecked` to convert from `Pin<P>` to `Pin<Pin<P>>`. However,
// given that the implementation of `Clone` returned a `Pin<P>`, we know that
// the target value is pinned, so this conversion is okay even if `Clone` was
// implemented for `Pin<P>` by a downstream crate.

/**
 * Helper for `pin` to enforce its type signature.
 * See <https://github.com/rust-lang/rust/issues/153438>.
 */
public open class PinMacroHelper<T>(
    public var value: T,
) {
    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<PinMacroHelper<T>> =
            object : TypeLayout<PinMacroHelper<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): PinMacroHelper<T> =
                    PinMacroHelper(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PinMacroHelper<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PinMacroHelper<T> =
                    PinMacroHelper(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PinMacroHelper<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * Helper for `pin` to enforce its type signature.
 * See <https://github.com/rust-lang/rust/issues/153438>.
 *
 * # Safety
 * Calling this function has the same safety requirements as calling
 * `Pin.newUnchecked` on `pinned.value`
 */
public fun <T> pinNewUncheckedInHelper(pinned: PinMacroHelper<T>): Pin<T> {
    // SAFETY: Ensured by the caller.
    return Pin.newUnchecked(pinned.value, fieldLayout = UnresolvedTypeLayout<Ptr>())
}
