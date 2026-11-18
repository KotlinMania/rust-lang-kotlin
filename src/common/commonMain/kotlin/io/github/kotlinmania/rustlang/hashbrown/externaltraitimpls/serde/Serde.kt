// port-lint: source external_trait_impls/serde.rs
package io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.serde

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.hash.BuildHasher
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.hashbrown.HashMap
import io.github.kotlinmania.rustlang.hashbrown.HashSet
import io.github.kotlinmania.rustlang.hashbrown.alloc.Allocator
import io.github.kotlinmania.rustlang.hashbrown.externaltraitimpls.SizeHint

public typealias Value<K, V> = MutableMap<K, V>

/**
 * This presumably exists to prevent denial of service attacks.
 *
 * Original discussion: https://github.com/serde-rs/serde/issues/1114.
 */
public fun cautious(hint: Int?): Int = minOf((hint ?: 0), 4096)

public fun <K, V, H, A, S> MutableMap<K, V>.serialize(serializer: S): Result<Ok, Error> where K : Serialize, V : Serialize, H : BuildHasher, A : Allocator, S : Serializer = serializer.collectMap(this)

public fun <K, V, S, A, D> deserializeHashMapKVSA(deserializer: D, defaultSFn: () -> S, defaultAFn: () -> A): Result<MutableMap<K, V>, Error> where K : Deserialize, V : Deserialize, S : BuildHasher, A : Allocator, D : Deserializer {
    open class MapVisitor<K, V, S, A>(
        public var marker: Unit,
    ) where A : Allocator
    fun <K, V, S, A> MapVisitor<K, V, S, A>.expecting(formatter: StringBuilder): Result<Unit, Error> where K : Deserialize, V : Deserialize, S : BuildHasher, A : Allocator = run { formatter.append("a map"); Result.Ok<Unit, Error>(Unit) }

    fun <K, V, S, A, M> MapVisitor<K, V, S, A>.visitMap(map: M, defaultSFn: () -> S, defaultAFn: () -> A): Result<MutableMap<K, V>, Error> where K : Deserialize, V : Deserialize, S : BuildHasher, A : Allocator, M : MapAccess {
        val values = HashMap.withCapacityAndHasherIn(SizeHint.cautious(map.sizeHint()), defaultSFn(), defaultAFn())
        while (true) {
            val matchVal = when (val tryResult = map.nextEntry()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<MutableMap<K, V>, Error>(tryResult.errValue()) }
            if (matchVal == null) break
            val key = matchVal.first
            val value = matchVal.second
            values.insert(key, value)
        }
        return Result.Ok<MutableMap<K, V>, Error>(values)
    }
    val visitor = MapVisitor(marker = Unit)
    return deserializer.deserializeMap(visitor)
}

public fun <T, H, A, S> MutableSet<T>.serialize(serializer: S): Result<Ok, Error> where T : Serialize, H : BuildHasher, A : Allocator, S : Serializer = serializer.collectSeq(this)

public fun <T, S, A, D> deserializeHashSetTSA(deserializer: D, defaultSFn: () -> S, defaultAFn: () -> A): Result<MutableSet<T>, Error> where T : Deserialize, S : BuildHasher, A : Allocator, D : Deserializer {
    open class SeqVisitor<T, S, A>(
        public var marker: Unit,
    ) where A : Allocator
    fun <T, S, A> SeqVisitor<T, S, A>.expecting(formatter: StringBuilder): Result<Unit, Error> where T : Deserialize, S : BuildHasher, A : Allocator = run { formatter.append("a sequence"); Result.Ok<Unit, Error>(Unit) }

    fun <T, S, A, M> SeqVisitor<T, S, A>.visitSeq(seq: M, defaultSFn: () -> S, defaultAFn: () -> A): Result<MutableSet<T>, Error> where T : Deserialize, S : BuildHasher, A : Allocator, M : SeqAccess {
        val values = HashSet.withCapacityAndHasherIn(SizeHint.cautious(seq.sizeHint()), defaultSFn(), defaultAFn())
        while (true) {
            val matchVal = when (val tryResult = seq.nextElement()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<MutableSet<T>, Error>(tryResult.errValue()) }
            if (matchVal == null) break
            val value = matchVal
            values.insert(value)
        }
        return Result.Ok<MutableSet<T>, Error>(values)
    }
    val visitor = SeqVisitor(marker = Unit)
    return deserializer.deserializeSeq(visitor)
}

public fun <T, S, A, D> deserializeDeserializeInPlaceHashSetTSA(deserializer: D, place: MutableSet<T>, defaultSFn: () -> S, defaultAFn: () -> A): Result<Unit, Error> where T : Deserialize, S : BuildHasher, A : Allocator, D : Deserializer {
    open class SeqInPlaceVisitor<T, S, A>(
        public var value: MutableSet<T>,
    ) where A : Allocator {
        public var field0: MutableSet<T>
            get() = value
            set(v) {
                value = v
            }
    }
    fun <T, S, A> SeqInPlaceVisitor<T, S, A>.expecting(formatter: StringBuilder): Result<Unit, Error> where T : Deserialize, S : BuildHasher, A : Allocator = run { formatter.append("a sequence"); Result.Ok<Unit, Error>(Unit) }

    fun <T, S, A, M> SeqInPlaceVisitor<T, S, A>.visitSeq(seq: M, defaultSFn: () -> S): Result<Unit, Error> where T : Deserialize, S : BuildHasher, A : Allocator, M : SeqAccess {
        this.value.clear()
        this.value.reserve(SizeHint.cautious(seq.sizeHint()))
        while (true) {
            val matchVal = when (val tryResult = seq.nextElement()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            if (matchVal == null) break
            val value = matchVal
            this.value.add(value)
        }
        return Result.Ok<Unit, Error>(Unit)
    }
    return deserializer.deserializeSeq(SeqInPlaceVisitor(place))
}
