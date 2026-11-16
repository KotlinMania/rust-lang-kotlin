package io.github.kotlinmania.rustlang.core.slice.sort.stable

import io.github.kotlinmania.rustlang.core.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

public open class AlignedStorage<T> internal constructor(
    public var align: List<T>,
    storageCtorArg: List<MaybeUninit<UByte>>,
) {
    public var storage: List<MaybeUninit<UByte>> = storageCtorArg
        internal set

    public companion object {
        public operator fun <T> invoke(
            align: List<T>,
            storage: List<MaybeUninit<UByte>>,
        ): AlignedStorage<T> = AlignedStorage<T>(
            align = align,
            storageCtorArg = storage,
        )
    }
}

public fun <T> AlignedStorage.Companion.new(n: Int): AlignedStorage<T> = AlignedStorage(align = listOf(), storage = List(n) { MaybeUninit.uninit(PrimitiveLayouts.U8) })

public fun <T> AlignedStorage<T>.asUninitSliceMut(n: Int, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): MutableList<MaybeUninit<T>> {
    val len = n / sizeOf<T>(typeLayoutT = typeLayoutT)
    // SAFETY: `_align` ensures we are correctly aligned.
    return linearMutSliceView(MutPtr((this.storage.asMutPtr()).rawAddress), len.toInt(), PrimitiveLayouts.U8)
}
