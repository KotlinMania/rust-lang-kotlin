package io.github.kotlinmania.rustlang.hashbrown

public fun <Q, K> Q.equivalent(key: K): Boolean = this == key.borrow()
