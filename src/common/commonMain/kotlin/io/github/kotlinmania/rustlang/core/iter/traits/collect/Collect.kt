// port-lint: source iter/traits/collect.rs
package io.github.kotlinmania.rustlang.core.iter.traits.collect

import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.mem.drop

public typealias Item<I> = I.Item

/**
 * Conversion from an [Iterator].
 *
 * By implementing `FromIterator` for a type, you define how it will be
 * created from an iterator. This is common for types which describe a
 * collection of some kind.
 *
 * If you want to create a collection from the contents of an iterator, the
 * [Iterator.collect] method is preferred. However, when you need to
 * specify the container type, [FromIterator.fromIter] can be more
 * readable than using a turbofish (e.g. `<List<_>>()`). See the
 * [Iterator.collect] documentation for more examples of its use.
 *
 * See also: [IntoIterator].
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val fiveFives = List(5) { 5 }
 * val v = Vec.fromIter(fiveFives)
 * check(v == mutableListOf(5, 5, 5, 5, 5))
 * ```
 *
 * Using [Iterator.collect] to implicitly use `FromIterator`:
 *
 * ```kotlin
 * val fiveFives = List(5) { 5 }
 * val v: List<Int> = fiveFives.toMutableList()
 * check(v == mutableListOf(5, 5, 5, 5, 5))
 * ```
 *
 * Using [FromIterator.fromIter] as a more readable alternative to
 * [Iterator.collect]:
 *
 * ```kotlin
 * val first = (0 until 10).collect<VecDeque<Int>>()
 * val second = VecDeque.fromIter(0 until 10)
 * check(first == second)
 * ```
 *
 * Implementing `FromIterator` for your type:
 *
 * ```kotlin
 * // A sample collection, that's just a wrapper over Vec<T>
 * open class MyCollection(public var value: List<Int>) {
 *     public var field0: List<Int>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     // Let's give it some methods so we can create one and add things
 *     // to it.
 *     public open fun new(): MyCollection {
 *         return MyCollection(mutableListOf())
 *     }
 *
 *     public open fun add(elem: Int) {
 *         this.value.push(elem)
 *     }
 *
 *     // and we'll implement FromIterator
 *     public open fun <I : Iterable<Int>> fromIter(iter: I): MyCollection {
 *         var c = MyCollection.new()
 *         for (i in iter) {
 *             c.add(i)
 *         }
 *         return c
 *     }
 * }
 * // Now we can make a new iterator...
 * val iter = (0 until 5).intoIter()
 * // ... and make a MyCollection out of it
 * val c = MyCollection.fromIter(iter)
 * check(c.first == mutableListOf(0, 1, 2, 3, 4))
 * // collect works too!
 * val iter1 = (0 until 5).intoIter()
 * val c1: MyCollection = iter1.collect()
 * check(c1.value == mutableListOf(0, 1, 2, 3, 4))
 * ```
 */
public interface FromIterator<T, SelfType> {
    /**
     * Creates a value from an iterator.
     *
     * See the [module-level documentation] for more.
     *
     * # Examples
     *
     * ```kotlin
     * val fiveFives = List(5) { 5 }
     * val v = Vec.fromIter(fiveFives)
     * check(v == mutableListOf(5, 5, 5, 5, 5))
     * ```
     */
    public fun <I : IntoIterator<T, Any?>> fromIter(iter: I): SelfType
}

/**
 * Conversion into an [Iterator].
 *
 * By implementing `IntoIterator` for a type, you define how it will be
 * converted to an iterator. This is common for types which describe a
 * collection of some kind.
 *
 * One benefit of implementing `IntoIterator` is that your type will [work
 * with Rust's `for` loop syntax](iter#for-loops-and-intoiterator).
 *
 * See also: [FromIterator].
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val v = listOf(1, 2, 3)
 * var iter = v.intoIter()
 * check(1 == iter.next())
 * check(2 == iter.next())
 * check(3 == iter.next())
 * check(null == iter.next())
 * ```
 * Implementing `IntoIterator` for your type:
 *
 * ```kotlin
 * public typealias Item = Int
 *
 * public fun doctestEntry() {
 *     // A sample collection, that's just a wrapper over Vec<T>
 *     open class MyCollection(public var value: List<Int>) : Iterable<Int> {
 *         public var field0: List<Int>
 *             get() = value
 *             set(v) {
 *                 value = v
 *             }
 *
 *         override fun iterator(): Iterator<Int> = this.intoIter().iterator()
 *
 *         // Let's give it some methods so we can create one and add things
 *         // to it.
 *         public open fun new(): MyCollection {
 *             return MyCollection(mutableListOf())
 *         }
 *
 *         public open fun add(elem: Int) {
 *             this.value.push(elem)
 *         }
 *
 *         // and we'll implement IntoIterator
 *         public open fun intoIter(): ArrayDeque<Int> {
 *             return ArrayDeque((this.value).toList())
 *         }
 *     }
 *     // Now we can make a new collection...
 *     var c = MyCollection.new()
 *     // ... add some stuff to it ...
 *     c.add(0)
 *     c.add(1)
 *     c.add(2)
 *     // ... and then turn it into an Iterator:
 *     for ((i, n) in c.intoIter().mapIndexed { index, item -> Pair(index, item) }) {
 *         check(i.toInt() == n)
 *     }
 * }
 * ```
 *
 * It is common to use `IntoIterator` as a trait bound. This allows
 * the input collection type to change, so long as it is still an
 * iterator. Additional bounds can be specified by restricting on
 * `Item`:
 *
 * ```kotlin
 * fun <T> collectAsStrings(collection: T): List<String> {
 *     return collection.iterator().map({ item -> "$item" }).toMutableList()
 * }
 * ```
 */
public interface IntoIterator<Item, out IntoIter> {
    /**
     * Creates an iterator from a value.
     *
     * See the [module-level documentation] for more.
     *
     * # Examples
     *
     * ```kotlin
     * val v = listOf(1, 2, 3)
     * var iter = v.intoIter()
     * check(1 == iter.next())
     * check(2 == iter.next())
     * check(3 == iter.next())
     * check(null == iter.next())
     * ```
     */
    public fun intoIter(): IntoIter
}

public fun <Item, I> I.intoIter(): I = this

/**
 * Extend a collection with the contents of an iterator.
 *
 * Iterators produce a series of values, and collections can also be thought
 * of as a series of values. The `Extend` trait bridges this gap, allowing you
 * to extend a collection by including the contents of that iterator. When
 * extending a collection with an already existing key, that entry is updated
 * or, in the case of collections that permit multiple entries with equal
 * keys, that entry is inserted.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // You can extend a String with some chars:
 * var message = "The first three letters are: "
 * message.extend(listOf('a', 'b', 'c'))
 * check("abc" == message.substring(29 until 32))
 * ```
 *
 * Implementing `Extend`:
 *
 * ```kotlin
 * // A sample collection, that's just a wrapper over Vec<T>
 * open class MyCollection(public var value: List<Int>) {
 *     public var field0: List<Int>
 *         get() = value
 *         set(v) {
 *             value = v
 *         }
 *
 *     // Let's give it some methods so we can create one and add things
 *     // to it.
 *     public open fun new(): MyCollection {
 *         return MyCollection(mutableListOf())
 *     }
 *
 *     public open fun add(elem: Int) {
 *         this.value.push(elem)
 *     }
 *
 *     // since MyCollection has a list of i32s, we implement Extend for i32
 *     // This is a bit simpler with the concrete type signature: we can call
 *     // extend on anything which can be turned into an Iterator which gives
 *     // us i32s. Because we need i32s to put into MyCollection.
 *     public open fun <I : Iterable<Int>> extend(iter: I) {
 *         // The implementation is very straightforward: loop through the
 *         // iterator, and add() each element to ourselves.
 *         for (elem in iter) {
 *             this.add(elem)
 *         }
 *     }
 * }
 * var c = MyCollection.new()
 * c.add(5)
 * c.add(6)
 * c.add(7)
 * // let's extend our collection with three more numbers
 * c.extend(mutableListOf(1, 2, 3))
 * // we've added these elements onto the end
 * check("MyCollection([5, 6, 7, 1, 2, 3])" == "$c")
 * ```
 */
public interface Extend<T> {
    /**
     * Extends a collection with the contents of an iterator.
     *
     * As this is the only required method for this trait, the [trait-level] docs
     * contain more details.
     *
     * [trait-level]: Extend
     *
     * # Examples
     *
     * ```kotlin
     * // You can extend a String with some chars:
     * var message = "abc"
     * message.extend(listOf('d', 'e', 'f').iter())
     * check("abcdef" == message)
     * ```
     */
    public fun <I : IntoIterator<T, Any?>> extend(iter: I)

    /**
     * Extends a collection with exactly one element.
     */
    public fun extendOne(item: T) {
        this.extend(item)
    }

    /**
     * Reserves capacity in a collection for the given number of additional elements.
     *
     * The default implementation does nothing.
     */
    public fun extendReserve(additional: Int) {}

    /**
     * Extends a collection with one element, without checking there is enough capacity for it.
     *
     * # Safety
     *
     * **For callers:** This must only be called when we know the collection has enough capacity
     * to contain the new item, for example because we previously called `extendReserve`.
     *
     * **For implementors:** For a collection to unsafely rely on this method's safety precondition (that is,
     * invoke UB if they are violated), it must implement `extendReserve` correctly. In other words,
     * callers may assume that if they `extendReserve`ed enough space they can call this method.
     */
    public fun extendOneUnchecked(item: T) {
        this.extendOne(item)
    }
}

public fun <I : IntoIterator<Unit, Any?>> Unit.extend(iter: I) {
    iter.iterator().forEach(drop)
}

public fun Unit.extendOne(item: Unit) {}

// the other implementations are below.

/**
 * This implementation turns an iterator of tuples into a tuple of types which implement
 * [Default] and [Extend].
 *
 * This is similar to [Iterator.unzip], but is also composable with other [FromIterator]
 * implementations:
 *
 * ```kotlin
 * val string = "1,2,123,4"
 * // Example given for a 2-tuple, but 1- through 12-tuples are supported
 * val (numbers: Vec, lengths: Vec) = string.split(',').map({ s -> s.parse().map({ n: UInt -> Pair(n, s.len()) }) }).collect<Result>().getOrElse { return Result.failure(it) }
 * check(numbers == listOf(1, 2, 123, 4))
 * check(lengths == listOf(1, 1, 3, 1))
 * ```
 */
public fun <T, ExtendT, Iter : IntoIterator<T, Any?>> fromIteratorFromIterExtendT(iter: Iter, defaultExtendTFn: () -> ExtendT): ExtendT where ExtendT : Extend<T> {
    val res = defaultExtendTFn()
    res.extend(iter.iterator().map({ t -> t.first }), defaultExtendTFn)
    return res
}

/**
 * An implementation of [`extend`](Extend.extend) that calls `extendOne` or
 * `extendOneUnchecked` for each element of the iterator.
 */
public fun <ExtendT, I, T> defaultExtend(collection: ExtendT, iter: I) where ExtendT : Extend<T>, I : IntoIterator<T, Any?> {
    // Specialize on `TrustedLen` and call `extendOneUnchecked` where
    // applicable.
    interface SpecExtend<I> {
        public fun extend(iter1: I)
    }
    // Extracting these to separate functions avoid monomorphising the closures
    // for every iterator type.
    fun <ExtendT, T> extender(collection1: ExtendT): (T) -> Unit where ExtendT : Extend<T> = { item -> collection1.extendOne(item) }
    fun <ExtendT, T> uncheckedExtender(collection1: ExtendT): (T) -> Unit where ExtendT : Extend<T> {
        // SAFETY: we make sure that there is enough space at the callsite of
        // this function.
        return { item -> collection1.extendOneUnchecked(item) }
    }
    fun <ExtendT, I, T> ExtendT.extend(iter1: I) where ExtendT : Extend<T>, I : Iterator<T> {
        val (lowerBound, _) = iter1.sizeHint()
        if (lowerBound > 0) {
            this.extendReserve(lowerBound)
        }
        iter1.forEach(extender(this))
    }
    fun <ExtendT, I, T> ExtendT.extendI(iter1: I) where ExtendT : Extend<T>, I : TrustedLen<I> {
        val (lowerBound, upperBound) = iter1.sizeHint()
        if (lowerBound > 0) {
            this.extendReserve(lowerBound)
        }
        if (upperBound == null) {
            // We cannot reserve more than `Int.MAX` items, and this is likely to go out of memory anyway.
            iter1.forEach(extender(this))
        } else {
            // SAFETY: We reserve enough space for the `sizeHint`, and the iterator is
            // `TrustedLen` so its `sizeHint` is exact.
            iter1.forEach(uncheckedExtender(this))
        }
    }
    SpecExtend.Extend(collection, iter.iterator())
}

public fun <A, B, ExA, ExB, Iter : IntoIterator<Pair<A, B>, Any?>> Pair<ExA, ExB>.extend(iter: Iter) where ExA : Extend<A>, ExB : Extend<B> {
    defaultExtend(this, iter)
}

public fun <A, B, ExA, ExB> Pair<ExA, ExB>.extendOne(item: Pair<A, B>) where ExA : Extend<A>, ExB : Extend<B> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
}

public fun <A, B, ExA, ExB> Pair<ExA, ExB>.extendReserve(additional: Int) where ExA : Extend<A>, ExB : Extend<B> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
}

public fun <A, B, ExA, ExB> Pair<ExA, ExB>.extendOneUnchecked(item: Pair<A, B>) where ExA : Extend<A>, ExB : Extend<B> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
    }
}

public fun <A, B, ExA, ExB, Iter : IntoIterator<Pair<A, B>, Any?>> fromIteratorFromIterExAExB(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB): Pair<ExA, ExB> where ExA : Extend<A>, ExB : Extend<B> {
    val res: Pair<ExA, ExB>  = Pair.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, ExA, ExB, ExC, Iter : IntoIterator<Triple<A, B, C>, Any?>> Triple<ExA, ExB, ExC>.extend(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C> {
    defaultExtend(this, iter)
}

public fun <A, B, C, ExA, ExB, ExC> Triple<ExA, ExB, ExC>.extendOne(item: Triple<A, B, C>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
}

public fun <A, B, C, ExA, ExB, ExC> Triple<ExA, ExB, ExC>.extendReserve(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
}

public fun <A, B, C, ExA, ExB, ExC> Triple<ExA, ExB, ExC>.extendOneUnchecked(item: Triple<A, B, C>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
    }
}

public fun <A, B, C, ExA, ExB, ExC, Iter : IntoIterator<Triple<A, B, C>, Any?>> fromIteratorFromIterExAExBExC(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC): Triple<ExA, ExB, ExC> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C> {
    val res: Triple<ExA, ExB, ExC>  = Triple.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, ExA, ExB, ExC, ExD, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extend(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, ExA, ExB, ExC, ExD> List<Any?>.extendOne(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
}

public fun <A, B, C, D, ExA, ExB, ExC, ExD> List<Any?>.extendReserve(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
}

public fun <A, B, C, D, ExA, ExB, ExC, ExD> List<Any?>.extendOneUnchecked(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
    }
}

public fun <A, B, C, D, ExA, ExB, ExC, ExD, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExD(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, E, ExA, ExB, ExC, ExD, ExE, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extendABCDE(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, E, ExA, ExB, ExC, ExD, ExE> List<Any?>.extendOneABCDE(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
    this.field4.extendOne(item.field4)
}

public fun <A, B, C, D, E, ExA, ExB, ExC, ExD, ExE> List<Any?>.extendReserveABCDE(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
    this.field4.extendReserve(additional)
}

public fun <A, B, C, D, E, ExA, ExB, ExC, ExD, ExE> List<Any?>.extendOneUncheckedABCDE(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
        this.field4.extendOneUnchecked(item.field4)
    }
}

public fun <A, B, C, D, E, ExA, ExB, ExC, ExD, ExE, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExDExE(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD, defaultExEFn: () -> ExE): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, E, F, ExA, ExB, ExC, ExD, ExE, ExF, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extendABCDEF(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, E, F, ExA, ExB, ExC, ExD, ExE, ExF> List<Any?>.extendOneABCDEF(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
    this.field4.extendOne(item.field4)
    this.field5.extendOne(item.field5)
}

public fun <A, B, C, D, E, F, ExA, ExB, ExC, ExD, ExE, ExF> List<Any?>.extendReserveABCDEF(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
    this.field4.extendReserve(additional)
    this.field5.extendReserve(additional)
}

public fun <A, B, C, D, E, F, ExA, ExB, ExC, ExD, ExE, ExF> List<Any?>.extendOneUncheckedABCDEF(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
        this.field4.extendOneUnchecked(item.field4)
        this.field5.extendOneUnchecked(item.field5)
    }
}

public fun <A, B, C, D, E, F, ExA, ExB, ExC, ExD, ExE, ExF, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExDExEExF(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD, defaultExEFn: () -> ExE, defaultExFFn: () -> ExF): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, E, F, G, ExA, ExB, ExC, ExD, ExE, ExF, ExG, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extendABCDEFG(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, E, F, G, ExA, ExB, ExC, ExD, ExE, ExF, ExG> List<Any?>.extendOneABCDEFG(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
    this.field4.extendOne(item.field4)
    this.field5.extendOne(item.field5)
    this.field6.extendOne(item.field6)
}

public fun <A, B, C, D, E, F, G, ExA, ExB, ExC, ExD, ExE, ExF, ExG> List<Any?>.extendReserveABCDEFG(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
    this.field4.extendReserve(additional)
    this.field5.extendReserve(additional)
    this.field6.extendReserve(additional)
}

public fun <A, B, C, D, E, F, G, ExA, ExB, ExC, ExD, ExE, ExF, ExG> List<Any?>.extendOneUncheckedABCDEFG(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
        this.field4.extendOneUnchecked(item.field4)
        this.field5.extendOneUnchecked(item.field5)
        this.field6.extendOneUnchecked(item.field6)
    }
}

public fun <A, B, C, D, E, F, G, ExA, ExB, ExC, ExD, ExE, ExF, ExG, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExDExEExFExG(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD, defaultExEFn: () -> ExE, defaultExFFn: () -> ExF, defaultExGFn: () -> ExG): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, E, F, G, H, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extendABCDEFGH(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, E, F, G, H, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH> List<Any?>.extendOneABCDEFGH(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
    this.field4.extendOne(item.field4)
    this.field5.extendOne(item.field5)
    this.field6.extendOne(item.field6)
    this.field7.extendOne(item.field7)
}

public fun <A, B, C, D, E, F, G, H, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH> List<Any?>.extendReserveABCDEFGH(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
    this.field4.extendReserve(additional)
    this.field5.extendReserve(additional)
    this.field6.extendReserve(additional)
    this.field7.extendReserve(additional)
}

public fun <A, B, C, D, E, F, G, H, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH> List<Any?>.extendOneUncheckedABCDEFGH(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
        this.field4.extendOneUnchecked(item.field4)
        this.field5.extendOneUnchecked(item.field5)
        this.field6.extendOneUnchecked(item.field6)
        this.field7.extendOneUnchecked(item.field7)
    }
}

public fun <A, B, C, D, E, F, G, H, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExDExEExFExGExH(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD, defaultExEFn: () -> ExE, defaultExFFn: () -> ExF, defaultExGFn: () -> ExG, defaultExHFn: () -> ExH): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, E, F, G, H, I, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extendABCDEFGHI(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, E, F, G, H, I, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI> List<Any?>.extendOneABCDEFGHI(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
    this.field4.extendOne(item.field4)
    this.field5.extendOne(item.field5)
    this.field6.extendOne(item.field6)
    this.field7.extendOne(item.field7)
    this.field8.extendOne(item.field8)
}

public fun <A, B, C, D, E, F, G, H, I, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI> List<Any?>.extendReserveABCDEFGHI(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
    this.field4.extendReserve(additional)
    this.field5.extendReserve(additional)
    this.field6.extendReserve(additional)
    this.field7.extendReserve(additional)
    this.field8.extendReserve(additional)
}

public fun <A, B, C, D, E, F, G, H, I, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI> List<Any?>.extendOneUncheckedABCDEFGHI(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
        this.field4.extendOneUnchecked(item.field4)
        this.field5.extendOneUnchecked(item.field5)
        this.field6.extendOneUnchecked(item.field6)
        this.field7.extendOneUnchecked(item.field7)
        this.field8.extendOneUnchecked(item.field8)
    }
}

public fun <A, B, C, D, E, F, G, H, I, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExDExEExFExGExHExI(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD, defaultExEFn: () -> ExE, defaultExFFn: () -> ExF, defaultExGFn: () -> ExG, defaultExHFn: () -> ExH, defaultExIFn: () -> ExI): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, E, F, G, H, I, J, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extendABCDEFGHIJ(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, E, F, G, H, I, J, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ> List<Any?>.extendOneABCDEFGHIJ(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
    this.field4.extendOne(item.field4)
    this.field5.extendOne(item.field5)
    this.field6.extendOne(item.field6)
    this.field7.extendOne(item.field7)
    this.field8.extendOne(item.field8)
    this.field9.extendOne(item.field9)
}

public fun <A, B, C, D, E, F, G, H, I, J, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ> List<Any?>.extendReserveABCDEFGHIJ(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
    this.field4.extendReserve(additional)
    this.field5.extendReserve(additional)
    this.field6.extendReserve(additional)
    this.field7.extendReserve(additional)
    this.field8.extendReserve(additional)
    this.field9.extendReserve(additional)
}

public fun <A, B, C, D, E, F, G, H, I, J, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ> List<Any?>.extendOneUncheckedABCDEFGHIJ(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
        this.field4.extendOneUnchecked(item.field4)
        this.field5.extendOneUnchecked(item.field5)
        this.field6.extendOneUnchecked(item.field6)
        this.field7.extendOneUnchecked(item.field7)
        this.field8.extendOneUnchecked(item.field8)
        this.field9.extendOneUnchecked(item.field9)
    }
}

public fun <A, B, C, D, E, F, G, H, I, J, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExDExEExFExGExHExIExJ(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD, defaultExEFn: () -> ExE, defaultExFFn: () -> ExF, defaultExGFn: () -> ExG, defaultExHFn: () -> ExH, defaultExIFn: () -> ExI, defaultExJFn: () -> ExJ): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, E, F, G, H, I, J, K, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extendABCDEFGHIJK(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, E, F, G, H, I, J, K, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK> List<Any?>.extendOneABCDEFGHIJK(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
    this.field4.extendOne(item.field4)
    this.field5.extendOne(item.field5)
    this.field6.extendOne(item.field6)
    this.field7.extendOne(item.field7)
    this.field8.extendOne(item.field8)
    this.field9.extendOne(item.field9)
    this.field10.extendOne(item.field10)
}

public fun <A, B, C, D, E, F, G, H, I, J, K, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK> List<Any?>.extendReserveABCDEFGHIJK(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
    this.field4.extendReserve(additional)
    this.field5.extendReserve(additional)
    this.field6.extendReserve(additional)
    this.field7.extendReserve(additional)
    this.field8.extendReserve(additional)
    this.field9.extendReserve(additional)
    this.field10.extendReserve(additional)
}

public fun <A, B, C, D, E, F, G, H, I, J, K, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK> List<Any?>.extendOneUncheckedABCDEFGHIJK(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
        this.field4.extendOneUnchecked(item.field4)
        this.field5.extendOneUnchecked(item.field5)
        this.field6.extendOneUnchecked(item.field6)
        this.field7.extendOneUnchecked(item.field7)
        this.field8.extendOneUnchecked(item.field8)
        this.field9.extendOneUnchecked(item.field9)
        this.field10.extendOneUnchecked(item.field10)
    }
}

public fun <A, B, C, D, E, F, G, H, I, J, K, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExDExEExFExGExHExIExJExK(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD, defaultExEFn: () -> ExE, defaultExFFn: () -> ExF, defaultExGFn: () -> ExG, defaultExHFn: () -> ExH, defaultExIFn: () -> ExI, defaultExJFn: () -> ExJ, defaultExKFn: () -> ExK): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}

public fun <A, B, C, D, E, F, G, H, I, J, K, L, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK, ExL, Iter : IntoIterator<List<Any?>, Any?>> List<Any?>.extendABCDEFGHIJKL(iter: Iter) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K>, ExL : Extend<L> {
    defaultExtend(this, iter)
}

public fun <A, B, C, D, E, F, G, H, I, J, K, L, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK, ExL> List<Any?>.extendOneABCDEFGHIJKL(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K>, ExL : Extend<L> {
    this.value.extendOne(item.first)
    this.field1.extendOne(item.second)
    this.field2.extendOne(item.third)
    this.field3.extendOne(item.field3)
    this.field4.extendOne(item.field4)
    this.field5.extendOne(item.field5)
    this.field6.extendOne(item.field6)
    this.field7.extendOne(item.field7)
    this.field8.extendOne(item.field8)
    this.field9.extendOne(item.field9)
    this.field10.extendOne(item.field10)
    this.field11.extendOne(item.field11)
}

public fun <A, B, C, D, E, F, G, H, I, J, K, L, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK, ExL> List<Any?>.extendReserveABCDEFGHIJKL(additional: Int) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K>, ExL : Extend<L> {
    this.value.extendReserve(additional)
    this.field1.extendReserve(additional)
    this.field2.extendReserve(additional)
    this.field3.extendReserve(additional)
    this.field4.extendReserve(additional)
    this.field5.extendReserve(additional)
    this.field6.extendReserve(additional)
    this.field7.extendReserve(additional)
    this.field8.extendReserve(additional)
    this.field9.extendReserve(additional)
    this.field10.extendReserve(additional)
    this.field11.extendReserve(additional)
}

public fun <A, B, C, D, E, F, G, H, I, J, K, L, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK, ExL> List<Any?>.extendOneUncheckedABCDEFGHIJKL(item: List<Any?>) where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K>, ExL : Extend<L> {
    run {
        this.value.extendOneUnchecked(item.first)
        this.field1.extendOneUnchecked(item.second)
        this.field2.extendOneUnchecked(item.third)
        this.field3.extendOneUnchecked(item.field3)
        this.field4.extendOneUnchecked(item.field4)
        this.field5.extendOneUnchecked(item.field5)
        this.field6.extendOneUnchecked(item.field6)
        this.field7.extendOneUnchecked(item.field7)
        this.field8.extendOneUnchecked(item.field8)
        this.field9.extendOneUnchecked(item.field9)
        this.field10.extendOneUnchecked(item.field10)
        this.field11.extendOneUnchecked(item.field11)
    }
}

public fun <A, B, C, D, E, F, G, H, I, J, K, L, ExA, ExB, ExC, ExD, ExE, ExF, ExG, ExH, ExI, ExJ, ExK, ExL, Iter : IntoIterator<List<Any?>, Any?>> fromIteratorFromIterExAExBExCExDExEExFExGExHExIExJExKExL(iter: Iter, defaultExAFn: () -> ExA, defaultExBFn: () -> ExB, defaultExCFn: () -> ExC, defaultExDFn: () -> ExD, defaultExEFn: () -> ExE, defaultExFFn: () -> ExF, defaultExGFn: () -> ExG, defaultExHFn: () -> ExH, defaultExIFn: () -> ExI, defaultExJFn: () -> ExJ, defaultExKFn: () -> ExK, defaultExLFn: () -> ExL): List<Any?> where ExA : Extend<A>, ExB : Extend<B>, ExC : Extend<C>, ExD : Extend<D>, ExE : Extend<E>, ExF : Extend<F>, ExG : Extend<G>, ExH : Extend<H>, ExI : Extend<I>, ExJ : Extend<J>, ExK : Extend<K>, ExL : Extend<L> {
    val res: List<Any?>  = List.default()
    res.extend(iter)
    return res
}
