package io.github.kotlinmania.rustlang.corearch.aarch64.sve

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout

public open class SvboolT(
    public var value: Boolean,
) : SveInto<Svbool2T> {
    public var field0: Boolean
        get() = value
        set(v) {
            value = v
        }

    public override fun sveInto(): Svbool2T {
        fun convertFromSvbool(b: SvboolT): Svbool2T = null as Svbool2T
        return convertFromSvbool(this)
    }

    public open fun sveIntoSvbool4T(): Svbool4T = convertFromSvbool(this)

    public open fun sveIntoSvbool8T(): Svbool8T = convertFromSvbool(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<SvboolT> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<SvboolT> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): SvboolT =
                    SvboolT(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SvboolT) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SvboolT =
                    SvboolT(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SvboolT) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svint8T(
    public var value: Byte,
) : AsUnsigned<Any?, Svuint8T> {
    public var field0: Byte
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Svuint8T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint8T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint8T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Svint8T =
                    Svint8T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svint8T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint8T =
                    Svint8T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint8T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svuint8T(
    public var value: UByte,
) : AsSigned<Any?, Svint8T> {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Svint8T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint8T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint8T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Svuint8T =
                    Svuint8T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svuint8T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint8T =
                    Svuint8T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint8T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svint16T(
    public var value: Short,
) : AsUnsigned<Any?, Svuint16T> {
    public var field0: Short
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Svuint16T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint16T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint16T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Svint16T =
                    Svint16T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svint16T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint16T =
                    Svint16T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint16T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svuint16T(
    public var value: UShort,
) : AsSigned<Any?, Svint16T> {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Svint16T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint16T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint16T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Svuint16T =
                    Svuint16T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svuint16T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint16T =
                    Svuint16T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint16T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svfloat32T(
    public var value: Float,
) {
    public var field0: Float
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat32T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat32T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Svfloat32T =
                    Svfloat32T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.F32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svfloat32T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.F32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svfloat32T =
                    Svfloat32T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.F32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svfloat32T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.F32).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svint32T(
    public var value: Int,
) : AsUnsigned<Any?, Svuint32T> {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Svuint32T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint32T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint32T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Svint32T =
                    Svint32T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svint32T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint32T =
                    Svint32T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint32T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svuint32T(
    public var value: UInt,
) : AsSigned<Any?, Svint32T> {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Svint32T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint32T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint32T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Svuint32T =
                    Svuint32T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svuint32T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint32T =
                    Svuint32T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint32T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svfloat64T(
    public var value: Double,
) {
    public var field0: Double
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat64T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat64T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Svfloat64T =
                    Svfloat64T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.F64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svfloat64T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.F64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svfloat64T =
                    Svfloat64T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.F64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svfloat64T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.F64).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svint64T(
    public var value: Long,
) : AsUnsigned<Any?, Svuint64T> {
    public var field0: Long
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Svuint64T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint64T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint64T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Svint64T =
                    Svint64T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svint64T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint64T =
                    Svint64T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint64T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I64).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svuint64T(
    public var value: ULong,
) : AsSigned<Any?, Svint64T> {
    public var field0: ULong
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Svint64T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint64T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint64T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Svuint64T =
                    Svuint64T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svuint64T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint64T =
                    Svuint64T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint64T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv2i8(
    public var value: Byte,
) : AsUnsigned<Any?, Nxv2u8> {
    public var field0: Byte
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Nxv2u8 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2i8> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2i8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Nxv2i8 =
                    Nxv2i8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv2i8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv2i8 =
                    Nxv2i8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv2i8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv4i8(
    public var value: Byte,
) : AsUnsigned<Any?, Nxv4u8> {
    public var field0: Byte
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Nxv4u8 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv4i8> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv4i8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Nxv4i8 =
                    Nxv4i8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv4i8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv4i8 =
                    Nxv4i8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv4i8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv8i8(
    public var value: Byte,
) : AsUnsigned<Any?, Nxv8u8> {
    public var field0: Byte
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Nxv8u8 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv8i8> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv8i8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Nxv8i8 =
                    Nxv8i8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv8i8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv8i8 =
                    Nxv8i8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv8i8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv2i16(
    public var value: Short,
) : AsUnsigned<Any?, Nxv2u16> {
    public var field0: Short
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Nxv2u16 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2i16> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2i16> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Nxv2i16 =
                    Nxv2i16(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv2i16) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv2i16 =
                    Nxv2i16(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv2i16) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv4i16(
    public var value: Short,
) : AsUnsigned<Any?, Nxv4u16> {
    public var field0: Short
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Nxv4u16 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv4i16> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv4i16> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Nxv4i16 =
                    Nxv4i16(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv4i16) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv4i16 =
                    Nxv4i16(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv4i16) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv2i32(
    public var value: Int,
) : AsUnsigned<Any?, Nxv2u32> {
    public var field0: Int
        get() = value
        set(v) {
            value = v
        }

    public override fun asUnsigned(): Nxv2u32 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2i32> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2i32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Nxv2i32 =
                    Nxv2i32(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv2i32) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv2i32 =
                    Nxv2i32(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv2i32) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv2u8(
    public var value: UByte,
) : AsSigned<Any?, Nxv2i8> {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Nxv2i8 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2u8> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2u8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Nxv2u8 =
                    Nxv2u8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv2u8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv2u8 =
                    Nxv2u8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv2u8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv4u8(
    public var value: UByte,
) : AsSigned<Any?, Nxv4i8> {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Nxv4i8 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv4u8> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv4u8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Nxv4u8 =
                    Nxv4u8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv4u8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv4u8 =
                    Nxv4u8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv4u8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv8u8(
    public var value: UByte,
) : AsSigned<Any?, Nxv8i8> {
    public var field0: UByte
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Nxv8i8 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv8u8> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv8u8> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Nxv8u8 =
                    Nxv8u8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv8u8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv8u8 =
                    Nxv8u8(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv8u8) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv2u16(
    public var value: UShort,
) : AsSigned<Any?, Nxv2i16> {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Nxv2i16 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2u16> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2u16> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Nxv2u16 =
                    Nxv2u16(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv2u16) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv2u16 =
                    Nxv2u16(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv2u16) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv4u16(
    public var value: UShort,
) : AsSigned<Any?, Nxv4i16> {
    public var field0: UShort
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Nxv4i16 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv4u16> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv4u16> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Nxv4u16 =
                    Nxv4u16(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv4u16) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv4u16 =
                    Nxv4u16(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv4u16) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U16).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Nxv2u32(
    public var value: UInt,
) : AsSigned<Any?, Nxv2i32> {
    public var field0: UInt
        get() = value
        set(v) {
            value = v
        }

    public override fun asSigned(): Nxv2i32 = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2u32> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Nxv2u32> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Nxv2u32 =
                    Nxv2u32(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Nxv2u32) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nxv2u32 =
                    Nxv2u32(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nxv2u32) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Svint8x2T(
    public var field0: Svint8T,
    public var field1: Svint8T,
) : AsUnsigned<Any?, Svuint8x2T> {
    public override fun asUnsigned(): Svuint8x2T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint8x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint8x2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Svint8x2T =
                    Svint8x2T(
                        (Svint8T.typeLayout()).readNative(addr),
                        (Svint8T.typeLayout()).readNative(addr + 1L),
                    )
                override fun writeNative(addr: Long, value: Svint8x2T) {
                    (Svint8T.typeLayout()).writeNative(addr, value.field0)
                    (Svint8T.typeLayout()).writeNative(addr + 1L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint8x2T =
                    Svint8x2T(
                        (Svint8T.typeLayout()).readBytes(buf, offset),
                        (Svint8T.typeLayout()).readBytes(buf, offset + 1),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint8x2T) {
                    (Svint8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint8T.typeLayout()).writeBytes(buf, offset + 1, value.field1)
                }
            }
    }
}

public open class Svuint8x2T(
    public var field0: Svuint8T,
    public var field1: Svuint8T,
) : AsSigned<Any?, Svint8x2T> {
    public override fun asSigned(): Svint8x2T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint8x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint8x2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): Svuint8x2T =
                    Svuint8x2T(
                        (Svuint8T.typeLayout()).readNative(addr),
                        (Svuint8T.typeLayout()).readNative(addr + 1L),
                    )
                override fun writeNative(addr: Long, value: Svuint8x2T) {
                    (Svuint8T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint8T.typeLayout()).writeNative(addr + 1L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint8x2T =
                    Svuint8x2T(
                        (Svuint8T.typeLayout()).readBytes(buf, offset),
                        (Svuint8T.typeLayout()).readBytes(buf, offset + 1),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint8x2T) {
                    (Svuint8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint8T.typeLayout()).writeBytes(buf, offset + 1, value.field1)
                }
            }
    }
}

public open class Svint16x2T(
    public var field0: Svint16T,
    public var field1: Svint16T,
) : AsUnsigned<Any?, Svuint16x2T> {
    public override fun asUnsigned(): Svuint16x2T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint16x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint16x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Svint16x2T =
                    Svint16x2T(
                        (Svint16T.typeLayout()).readNative(addr),
                        (Svint16T.typeLayout()).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Svint16x2T) {
                    (Svint16T.typeLayout()).writeNative(addr, value.field0)
                    (Svint16T.typeLayout()).writeNative(addr + 2L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint16x2T =
                    Svint16x2T(
                        (Svint16T.typeLayout()).readBytes(buf, offset),
                        (Svint16T.typeLayout()).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint16x2T) {
                    (Svint16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint16T.typeLayout()).writeBytes(buf, offset + 2, value.field1)
                }
            }
    }
}

public open class Svuint16x2T(
    public var field0: Svuint16T,
    public var field1: Svuint16T,
) : AsSigned<Any?, Svint16x2T> {
    public override fun asSigned(): Svint16x2T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint16x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint16x2T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Svuint16x2T =
                    Svuint16x2T(
                        (Svuint16T.typeLayout()).readNative(addr),
                        (Svuint16T.typeLayout()).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Svuint16x2T) {
                    (Svuint16T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint16T.typeLayout()).writeNative(addr + 2L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint16x2T =
                    Svuint16x2T(
                        (Svuint16T.typeLayout()).readBytes(buf, offset),
                        (Svuint16T.typeLayout()).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint16x2T) {
                    (Svuint16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint16T.typeLayout()).writeBytes(buf, offset + 2, value.field1)
                }
            }
    }
}

public open class Svfloat32x2T(
    public var field0: Svfloat32T,
    public var field1: Svfloat32T,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat32x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat32x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Svfloat32x2T =
                    Svfloat32x2T(
                        (Svfloat32T.typeLayout()).readNative(addr),
                        (Svfloat32T.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Svfloat32x2T) {
                    (Svfloat32T.typeLayout()).writeNative(addr, value.field0)
                    (Svfloat32T.typeLayout()).writeNative(addr + 4L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svfloat32x2T =
                    Svfloat32x2T(
                        (Svfloat32T.typeLayout()).readBytes(buf, offset),
                        (Svfloat32T.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svfloat32x2T) {
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                }
            }
    }
}

public open class Svint32x2T(
    public var field0: Svint32T,
    public var field1: Svint32T,
) : AsUnsigned<Any?, Svuint32x2T> {
    public override fun asUnsigned(): Svuint32x2T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint32x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint32x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Svint32x2T =
                    Svint32x2T(
                        (Svint32T.typeLayout()).readNative(addr),
                        (Svint32T.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Svint32x2T) {
                    (Svint32T.typeLayout()).writeNative(addr, value.field0)
                    (Svint32T.typeLayout()).writeNative(addr + 4L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint32x2T =
                    Svint32x2T(
                        (Svint32T.typeLayout()).readBytes(buf, offset),
                        (Svint32T.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint32x2T) {
                    (Svint32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                }
            }
    }
}

public open class Svuint32x2T(
    public var field0: Svuint32T,
    public var field1: Svuint32T,
) : AsSigned<Any?, Svint32x2T> {
    public override fun asSigned(): Svint32x2T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint32x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint32x2T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Svuint32x2T =
                    Svuint32x2T(
                        (Svuint32T.typeLayout()).readNative(addr),
                        (Svuint32T.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Svuint32x2T) {
                    (Svuint32T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint32T.typeLayout()).writeNative(addr + 4L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint32x2T =
                    Svuint32x2T(
                        (Svuint32T.typeLayout()).readBytes(buf, offset),
                        (Svuint32T.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint32x2T) {
                    (Svuint32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                }
            }
    }
}

public open class Svfloat64x2T(
    public var field0: Svfloat64T,
    public var field1: Svfloat64T,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat64x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat64x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Svfloat64x2T =
                    Svfloat64x2T(
                        (Svfloat64T.typeLayout()).readNative(addr),
                        (Svfloat64T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Svfloat64x2T) {
                    (Svfloat64T.typeLayout()).writeNative(addr, value.field0)
                    (Svfloat64T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svfloat64x2T =
                    Svfloat64x2T(
                        (Svfloat64T.typeLayout()).readBytes(buf, offset),
                        (Svfloat64T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svfloat64x2T) {
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

public open class Svint64x2T(
    public var field0: Svint64T,
    public var field1: Svint64T,
) : AsUnsigned<Any?, Svuint64x2T> {
    public override fun asUnsigned(): Svuint64x2T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint64x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint64x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Svint64x2T =
                    Svint64x2T(
                        (Svint64T.typeLayout()).readNative(addr),
                        (Svint64T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Svint64x2T) {
                    (Svint64T.typeLayout()).writeNative(addr, value.field0)
                    (Svint64T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint64x2T =
                    Svint64x2T(
                        (Svint64T.typeLayout()).readBytes(buf, offset),
                        (Svint64T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint64x2T) {
                    (Svint64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

public open class Svuint64x2T(
    public var field0: Svuint64T,
    public var field1: Svuint64T,
) : AsSigned<Any?, Svint64x2T> {
    public override fun asSigned(): Svint64x2T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint64x2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint64x2T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Svuint64x2T =
                    Svuint64x2T(
                        (Svuint64T.typeLayout()).readNative(addr),
                        (Svuint64T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Svuint64x2T) {
                    (Svuint64T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint64T.typeLayout()).writeNative(addr + 8L, value.field1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint64x2T =
                    Svuint64x2T(
                        (Svuint64T.typeLayout()).readBytes(buf, offset),
                        (Svuint64T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint64x2T) {
                    (Svuint64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                }
            }
    }
}

public open class Svint8x3T(
    public var field0: Svint8T,
    public var field1: Svint8T,
    public var field2: Svint8T,
) : AsUnsigned<Any?, Svuint8x3T> {
    public override fun asUnsigned(): Svuint8x3T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint8x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint8x3T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 3L
                override fun readNative(addr: Long): Svint8x3T =
                    Svint8x3T(
                        (Svint8T.typeLayout()).readNative(addr),
                        (Svint8T.typeLayout()).readNative(addr + 1L),
                        (Svint8T.typeLayout()).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Svint8x3T) {
                    (Svint8T.typeLayout()).writeNative(addr, value.field0)
                    (Svint8T.typeLayout()).writeNative(addr + 1L, value.field1)
                    (Svint8T.typeLayout()).writeNative(addr + 2L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint8x3T =
                    Svint8x3T(
                        (Svint8T.typeLayout()).readBytes(buf, offset),
                        (Svint8T.typeLayout()).readBytes(buf, offset + 1),
                        (Svint8T.typeLayout()).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint8x3T) {
                    (Svint8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint8T.typeLayout()).writeBytes(buf, offset + 1, value.field1)
                    (Svint8T.typeLayout()).writeBytes(buf, offset + 2, value.field2)
                }
            }
    }
}

public open class Svuint8x3T(
    public var field0: Svuint8T,
    public var field1: Svuint8T,
    public var field2: Svuint8T,
) : AsSigned<Any?, Svint8x3T> {
    public override fun asSigned(): Svint8x3T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint8x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint8x3T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 3L
                override fun readNative(addr: Long): Svuint8x3T =
                    Svuint8x3T(
                        (Svuint8T.typeLayout()).readNative(addr),
                        (Svuint8T.typeLayout()).readNative(addr + 1L),
                        (Svuint8T.typeLayout()).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Svuint8x3T) {
                    (Svuint8T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint8T.typeLayout()).writeNative(addr + 1L, value.field1)
                    (Svuint8T.typeLayout()).writeNative(addr + 2L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint8x3T =
                    Svuint8x3T(
                        (Svuint8T.typeLayout()).readBytes(buf, offset),
                        (Svuint8T.typeLayout()).readBytes(buf, offset + 1),
                        (Svuint8T.typeLayout()).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint8x3T) {
                    (Svuint8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint8T.typeLayout()).writeBytes(buf, offset + 1, value.field1)
                    (Svuint8T.typeLayout()).writeBytes(buf, offset + 2, value.field2)
                }
            }
    }
}

public open class Svint16x3T(
    public var field0: Svint16T,
    public var field1: Svint16T,
    public var field2: Svint16T,
) : AsUnsigned<Any?, Svuint16x3T> {
    public override fun asUnsigned(): Svuint16x3T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint16x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint16x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 6L
                override fun readNative(addr: Long): Svint16x3T =
                    Svint16x3T(
                        (Svint16T.typeLayout()).readNative(addr),
                        (Svint16T.typeLayout()).readNative(addr + 2L),
                        (Svint16T.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Svint16x3T) {
                    (Svint16T.typeLayout()).writeNative(addr, value.field0)
                    (Svint16T.typeLayout()).writeNative(addr + 2L, value.field1)
                    (Svint16T.typeLayout()).writeNative(addr + 4L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint16x3T =
                    Svint16x3T(
                        (Svint16T.typeLayout()).readBytes(buf, offset),
                        (Svint16T.typeLayout()).readBytes(buf, offset + 2),
                        (Svint16T.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint16x3T) {
                    (Svint16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint16T.typeLayout()).writeBytes(buf, offset + 2, value.field1)
                    (Svint16T.typeLayout()).writeBytes(buf, offset + 4, value.field2)
                }
            }
    }
}

public open class Svuint16x3T(
    public var field0: Svuint16T,
    public var field1: Svuint16T,
    public var field2: Svuint16T,
) : AsSigned<Any?, Svint16x3T> {
    public override fun asSigned(): Svint16x3T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint16x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint16x3T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 6L
                override fun readNative(addr: Long): Svuint16x3T =
                    Svuint16x3T(
                        (Svuint16T.typeLayout()).readNative(addr),
                        (Svuint16T.typeLayout()).readNative(addr + 2L),
                        (Svuint16T.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Svuint16x3T) {
                    (Svuint16T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint16T.typeLayout()).writeNative(addr + 2L, value.field1)
                    (Svuint16T.typeLayout()).writeNative(addr + 4L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint16x3T =
                    Svuint16x3T(
                        (Svuint16T.typeLayout()).readBytes(buf, offset),
                        (Svuint16T.typeLayout()).readBytes(buf, offset + 2),
                        (Svuint16T.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint16x3T) {
                    (Svuint16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint16T.typeLayout()).writeBytes(buf, offset + 2, value.field1)
                    (Svuint16T.typeLayout()).writeBytes(buf, offset + 4, value.field2)
                }
            }
    }
}

public open class Svfloat32x3T(
    public var field0: Svfloat32T,
    public var field1: Svfloat32T,
    public var field2: Svfloat32T,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat32x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat32x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): Svfloat32x3T =
                    Svfloat32x3T(
                        (Svfloat32T.typeLayout()).readNative(addr),
                        (Svfloat32T.typeLayout()).readNative(addr + 4L),
                        (Svfloat32T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Svfloat32x3T) {
                    (Svfloat32T.typeLayout()).writeNative(addr, value.field0)
                    (Svfloat32T.typeLayout()).writeNative(addr + 4L, value.field1)
                    (Svfloat32T.typeLayout()).writeNative(addr + 8L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svfloat32x3T =
                    Svfloat32x3T(
                        (Svfloat32T.typeLayout()).readBytes(buf, offset),
                        (Svfloat32T.typeLayout()).readBytes(buf, offset + 4),
                        (Svfloat32T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svfloat32x3T) {
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset + 8, value.field2)
                }
            }
    }
}

public open class Svint32x3T(
    public var field0: Svint32T,
    public var field1: Svint32T,
    public var field2: Svint32T,
) : AsUnsigned<Any?, Svuint32x3T> {
    public override fun asUnsigned(): Svuint32x3T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint32x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint32x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): Svint32x3T =
                    Svint32x3T(
                        (Svint32T.typeLayout()).readNative(addr),
                        (Svint32T.typeLayout()).readNative(addr + 4L),
                        (Svint32T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Svint32x3T) {
                    (Svint32T.typeLayout()).writeNative(addr, value.field0)
                    (Svint32T.typeLayout()).writeNative(addr + 4L, value.field1)
                    (Svint32T.typeLayout()).writeNative(addr + 8L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint32x3T =
                    Svint32x3T(
                        (Svint32T.typeLayout()).readBytes(buf, offset),
                        (Svint32T.typeLayout()).readBytes(buf, offset + 4),
                        (Svint32T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint32x3T) {
                    (Svint32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                    (Svint32T.typeLayout()).writeBytes(buf, offset + 8, value.field2)
                }
            }
    }
}

public open class Svuint32x3T(
    public var field0: Svuint32T,
    public var field1: Svuint32T,
    public var field2: Svuint32T,
) : AsSigned<Any?, Svint32x3T> {
    public override fun asSigned(): Svint32x3T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint32x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint32x3T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): Svuint32x3T =
                    Svuint32x3T(
                        (Svuint32T.typeLayout()).readNative(addr),
                        (Svuint32T.typeLayout()).readNative(addr + 4L),
                        (Svuint32T.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Svuint32x3T) {
                    (Svuint32T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint32T.typeLayout()).writeNative(addr + 4L, value.field1)
                    (Svuint32T.typeLayout()).writeNative(addr + 8L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint32x3T =
                    Svuint32x3T(
                        (Svuint32T.typeLayout()).readBytes(buf, offset),
                        (Svuint32T.typeLayout()).readBytes(buf, offset + 4),
                        (Svuint32T.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint32x3T) {
                    (Svuint32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                    (Svuint32T.typeLayout()).writeBytes(buf, offset + 8, value.field2)
                }
            }
    }
}

public open class Svfloat64x3T(
    public var field0: Svfloat64T,
    public var field1: Svfloat64T,
    public var field2: Svfloat64T,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat64x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat64x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Svfloat64x3T =
                    Svfloat64x3T(
                        (Svfloat64T.typeLayout()).readNative(addr),
                        (Svfloat64T.typeLayout()).readNative(addr + 8L),
                        (Svfloat64T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Svfloat64x3T) {
                    (Svfloat64T.typeLayout()).writeNative(addr, value.field0)
                    (Svfloat64T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Svfloat64T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svfloat64x3T =
                    Svfloat64x3T(
                        (Svfloat64T.typeLayout()).readBytes(buf, offset),
                        (Svfloat64T.typeLayout()).readBytes(buf, offset + 8),
                        (Svfloat64T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svfloat64x3T) {
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

public open class Svint64x3T(
    public var field0: Svint64T,
    public var field1: Svint64T,
    public var field2: Svint64T,
) : AsUnsigned<Any?, Svuint64x3T> {
    public override fun asUnsigned(): Svuint64x3T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint64x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint64x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Svint64x3T =
                    Svint64x3T(
                        (Svint64T.typeLayout()).readNative(addr),
                        (Svint64T.typeLayout()).readNative(addr + 8L),
                        (Svint64T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Svint64x3T) {
                    (Svint64T.typeLayout()).writeNative(addr, value.field0)
                    (Svint64T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Svint64T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint64x3T =
                    Svint64x3T(
                        (Svint64T.typeLayout()).readBytes(buf, offset),
                        (Svint64T.typeLayout()).readBytes(buf, offset + 8),
                        (Svint64T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint64x3T) {
                    (Svint64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Svint64T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

public open class Svuint64x3T(
    public var field0: Svuint64T,
    public var field1: Svuint64T,
    public var field2: Svuint64T,
) : AsSigned<Any?, Svint64x3T> {
    public override fun asSigned(): Svint64x3T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint64x3T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint64x3T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Svuint64x3T =
                    Svuint64x3T(
                        (Svuint64T.typeLayout()).readNative(addr),
                        (Svuint64T.typeLayout()).readNative(addr + 8L),
                        (Svuint64T.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Svuint64x3T) {
                    (Svuint64T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint64T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Svuint64T.typeLayout()).writeNative(addr + 16L, value.field2)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint64x3T =
                    Svuint64x3T(
                        (Svuint64T.typeLayout()).readBytes(buf, offset),
                        (Svuint64T.typeLayout()).readBytes(buf, offset + 8),
                        (Svuint64T.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint64x3T) {
                    (Svuint64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Svuint64T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                }
            }
    }
}

public open class Svint8x4T(
    public var field0: Svint8T,
    public var field1: Svint8T,
    public var field2: Svint8T,
    public var field3: Svint8T,
) : AsUnsigned<Any?, Svuint8x4T> {
    public override fun asUnsigned(): Svuint8x4T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint8x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint8x4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Svint8x4T =
                    Svint8x4T(
                        (Svint8T.typeLayout()).readNative(addr),
                        (Svint8T.typeLayout()).readNative(addr + 1L),
                        (Svint8T.typeLayout()).readNative(addr + 2L),
                        (Svint8T.typeLayout()).readNative(addr + 3L),
                    )
                override fun writeNative(addr: Long, value: Svint8x4T) {
                    (Svint8T.typeLayout()).writeNative(addr, value.field0)
                    (Svint8T.typeLayout()).writeNative(addr + 1L, value.field1)
                    (Svint8T.typeLayout()).writeNative(addr + 2L, value.field2)
                    (Svint8T.typeLayout()).writeNative(addr + 3L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint8x4T =
                    Svint8x4T(
                        (Svint8T.typeLayout()).readBytes(buf, offset),
                        (Svint8T.typeLayout()).readBytes(buf, offset + 1),
                        (Svint8T.typeLayout()).readBytes(buf, offset + 2),
                        (Svint8T.typeLayout()).readBytes(buf, offset + 3),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint8x4T) {
                    (Svint8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint8T.typeLayout()).writeBytes(buf, offset + 1, value.field1)
                    (Svint8T.typeLayout()).writeBytes(buf, offset + 2, value.field2)
                    (Svint8T.typeLayout()).writeBytes(buf, offset + 3, value.field3)
                }
            }
    }
}

public open class Svuint8x4T(
    public var field0: Svuint8T,
    public var field1: Svuint8T,
    public var field2: Svuint8T,
    public var field3: Svuint8T,
) : AsSigned<Any?, Svint8x4T> {
    public override fun asSigned(): Svint8x4T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint8x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint8x4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Svuint8x4T =
                    Svuint8x4T(
                        (Svuint8T.typeLayout()).readNative(addr),
                        (Svuint8T.typeLayout()).readNative(addr + 1L),
                        (Svuint8T.typeLayout()).readNative(addr + 2L),
                        (Svuint8T.typeLayout()).readNative(addr + 3L),
                    )
                override fun writeNative(addr: Long, value: Svuint8x4T) {
                    (Svuint8T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint8T.typeLayout()).writeNative(addr + 1L, value.field1)
                    (Svuint8T.typeLayout()).writeNative(addr + 2L, value.field2)
                    (Svuint8T.typeLayout()).writeNative(addr + 3L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint8x4T =
                    Svuint8x4T(
                        (Svuint8T.typeLayout()).readBytes(buf, offset),
                        (Svuint8T.typeLayout()).readBytes(buf, offset + 1),
                        (Svuint8T.typeLayout()).readBytes(buf, offset + 2),
                        (Svuint8T.typeLayout()).readBytes(buf, offset + 3),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint8x4T) {
                    (Svuint8T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint8T.typeLayout()).writeBytes(buf, offset + 1, value.field1)
                    (Svuint8T.typeLayout()).writeBytes(buf, offset + 2, value.field2)
                    (Svuint8T.typeLayout()).writeBytes(buf, offset + 3, value.field3)
                }
            }
    }
}

public open class Svint16x4T(
    public var field0: Svint16T,
    public var field1: Svint16T,
    public var field2: Svint16T,
    public var field3: Svint16T,
) : AsUnsigned<Any?, Svuint16x4T> {
    public override fun asUnsigned(): Svuint16x4T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint16x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint16x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Svint16x4T =
                    Svint16x4T(
                        (Svint16T.typeLayout()).readNative(addr),
                        (Svint16T.typeLayout()).readNative(addr + 2L),
                        (Svint16T.typeLayout()).readNative(addr + 4L),
                        (Svint16T.typeLayout()).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: Svint16x4T) {
                    (Svint16T.typeLayout()).writeNative(addr, value.field0)
                    (Svint16T.typeLayout()).writeNative(addr + 2L, value.field1)
                    (Svint16T.typeLayout()).writeNative(addr + 4L, value.field2)
                    (Svint16T.typeLayout()).writeNative(addr + 6L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint16x4T =
                    Svint16x4T(
                        (Svint16T.typeLayout()).readBytes(buf, offset),
                        (Svint16T.typeLayout()).readBytes(buf, offset + 2),
                        (Svint16T.typeLayout()).readBytes(buf, offset + 4),
                        (Svint16T.typeLayout()).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint16x4T) {
                    (Svint16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint16T.typeLayout()).writeBytes(buf, offset + 2, value.field1)
                    (Svint16T.typeLayout()).writeBytes(buf, offset + 4, value.field2)
                    (Svint16T.typeLayout()).writeBytes(buf, offset + 6, value.field3)
                }
            }
    }
}

public open class Svuint16x4T(
    public var field0: Svuint16T,
    public var field1: Svuint16T,
    public var field2: Svuint16T,
    public var field3: Svuint16T,
) : AsSigned<Any?, Svint16x4T> {
    public override fun asSigned(): Svint16x4T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint16x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint16x4T> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Svuint16x4T =
                    Svuint16x4T(
                        (Svuint16T.typeLayout()).readNative(addr),
                        (Svuint16T.typeLayout()).readNative(addr + 2L),
                        (Svuint16T.typeLayout()).readNative(addr + 4L),
                        (Svuint16T.typeLayout()).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: Svuint16x4T) {
                    (Svuint16T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint16T.typeLayout()).writeNative(addr + 2L, value.field1)
                    (Svuint16T.typeLayout()).writeNative(addr + 4L, value.field2)
                    (Svuint16T.typeLayout()).writeNative(addr + 6L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint16x4T =
                    Svuint16x4T(
                        (Svuint16T.typeLayout()).readBytes(buf, offset),
                        (Svuint16T.typeLayout()).readBytes(buf, offset + 2),
                        (Svuint16T.typeLayout()).readBytes(buf, offset + 4),
                        (Svuint16T.typeLayout()).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint16x4T) {
                    (Svuint16T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint16T.typeLayout()).writeBytes(buf, offset + 2, value.field1)
                    (Svuint16T.typeLayout()).writeBytes(buf, offset + 4, value.field2)
                    (Svuint16T.typeLayout()).writeBytes(buf, offset + 6, value.field3)
                }
            }
    }
}

public open class Svfloat32x4T(
    public var field0: Svfloat32T,
    public var field1: Svfloat32T,
    public var field2: Svfloat32T,
    public var field3: Svfloat32T,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat32x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat32x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Svfloat32x4T =
                    Svfloat32x4T(
                        (Svfloat32T.typeLayout()).readNative(addr),
                        (Svfloat32T.typeLayout()).readNative(addr + 4L),
                        (Svfloat32T.typeLayout()).readNative(addr + 8L),
                        (Svfloat32T.typeLayout()).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Svfloat32x4T) {
                    (Svfloat32T.typeLayout()).writeNative(addr, value.field0)
                    (Svfloat32T.typeLayout()).writeNative(addr + 4L, value.field1)
                    (Svfloat32T.typeLayout()).writeNative(addr + 8L, value.field2)
                    (Svfloat32T.typeLayout()).writeNative(addr + 12L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svfloat32x4T =
                    Svfloat32x4T(
                        (Svfloat32T.typeLayout()).readBytes(buf, offset),
                        (Svfloat32T.typeLayout()).readBytes(buf, offset + 4),
                        (Svfloat32T.typeLayout()).readBytes(buf, offset + 8),
                        (Svfloat32T.typeLayout()).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svfloat32x4T) {
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset + 8, value.field2)
                    (Svfloat32T.typeLayout()).writeBytes(buf, offset + 12, value.field3)
                }
            }
    }
}

public open class Svint32x4T(
    public var field0: Svint32T,
    public var field1: Svint32T,
    public var field2: Svint32T,
    public var field3: Svint32T,
) : AsUnsigned<Any?, Svuint32x4T> {
    public override fun asUnsigned(): Svuint32x4T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint32x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint32x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Svint32x4T =
                    Svint32x4T(
                        (Svint32T.typeLayout()).readNative(addr),
                        (Svint32T.typeLayout()).readNative(addr + 4L),
                        (Svint32T.typeLayout()).readNative(addr + 8L),
                        (Svint32T.typeLayout()).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Svint32x4T) {
                    (Svint32T.typeLayout()).writeNative(addr, value.field0)
                    (Svint32T.typeLayout()).writeNative(addr + 4L, value.field1)
                    (Svint32T.typeLayout()).writeNative(addr + 8L, value.field2)
                    (Svint32T.typeLayout()).writeNative(addr + 12L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint32x4T =
                    Svint32x4T(
                        (Svint32T.typeLayout()).readBytes(buf, offset),
                        (Svint32T.typeLayout()).readBytes(buf, offset + 4),
                        (Svint32T.typeLayout()).readBytes(buf, offset + 8),
                        (Svint32T.typeLayout()).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint32x4T) {
                    (Svint32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                    (Svint32T.typeLayout()).writeBytes(buf, offset + 8, value.field2)
                    (Svint32T.typeLayout()).writeBytes(buf, offset + 12, value.field3)
                }
            }
    }
}

public open class Svuint32x4T(
    public var field0: Svuint32T,
    public var field1: Svuint32T,
    public var field2: Svuint32T,
    public var field3: Svuint32T,
) : AsSigned<Any?, Svint32x4T> {
    public override fun asSigned(): Svint32x4T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint32x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint32x4T> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Svuint32x4T =
                    Svuint32x4T(
                        (Svuint32T.typeLayout()).readNative(addr),
                        (Svuint32T.typeLayout()).readNative(addr + 4L),
                        (Svuint32T.typeLayout()).readNative(addr + 8L),
                        (Svuint32T.typeLayout()).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Svuint32x4T) {
                    (Svuint32T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint32T.typeLayout()).writeNative(addr + 4L, value.field1)
                    (Svuint32T.typeLayout()).writeNative(addr + 8L, value.field2)
                    (Svuint32T.typeLayout()).writeNative(addr + 12L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint32x4T =
                    Svuint32x4T(
                        (Svuint32T.typeLayout()).readBytes(buf, offset),
                        (Svuint32T.typeLayout()).readBytes(buf, offset + 4),
                        (Svuint32T.typeLayout()).readBytes(buf, offset + 8),
                        (Svuint32T.typeLayout()).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint32x4T) {
                    (Svuint32T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint32T.typeLayout()).writeBytes(buf, offset + 4, value.field1)
                    (Svuint32T.typeLayout()).writeBytes(buf, offset + 8, value.field2)
                    (Svuint32T.typeLayout()).writeBytes(buf, offset + 12, value.field3)
                }
            }
    }
}

public open class Svfloat64x4T(
    public var field0: Svfloat64T,
    public var field1: Svfloat64T,
    public var field2: Svfloat64T,
    public var field3: Svfloat64T,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat64x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svfloat64x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Svfloat64x4T =
                    Svfloat64x4T(
                        (Svfloat64T.typeLayout()).readNative(addr),
                        (Svfloat64T.typeLayout()).readNative(addr + 8L),
                        (Svfloat64T.typeLayout()).readNative(addr + 16L),
                        (Svfloat64T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Svfloat64x4T) {
                    (Svfloat64T.typeLayout()).writeNative(addr, value.field0)
                    (Svfloat64T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Svfloat64T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Svfloat64T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svfloat64x4T =
                    Svfloat64x4T(
                        (Svfloat64T.typeLayout()).readBytes(buf, offset),
                        (Svfloat64T.typeLayout()).readBytes(buf, offset + 8),
                        (Svfloat64T.typeLayout()).readBytes(buf, offset + 16),
                        (Svfloat64T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svfloat64x4T) {
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Svfloat64T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

public open class Svint64x4T(
    public var field0: Svint64T,
    public var field1: Svint64T,
    public var field2: Svint64T,
    public var field3: Svint64T,
) : AsUnsigned<Any?, Svuint64x4T> {
    public override fun asUnsigned(): Svuint64x4T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint64x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svint64x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Svint64x4T =
                    Svint64x4T(
                        (Svint64T.typeLayout()).readNative(addr),
                        (Svint64T.typeLayout()).readNative(addr + 8L),
                        (Svint64T.typeLayout()).readNative(addr + 16L),
                        (Svint64T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Svint64x4T) {
                    (Svint64T.typeLayout()).writeNative(addr, value.field0)
                    (Svint64T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Svint64T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Svint64T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svint64x4T =
                    Svint64x4T(
                        (Svint64T.typeLayout()).readBytes(buf, offset),
                        (Svint64T.typeLayout()).readBytes(buf, offset + 8),
                        (Svint64T.typeLayout()).readBytes(buf, offset + 16),
                        (Svint64T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svint64x4T) {
                    (Svint64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svint64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Svint64T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Svint64T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

public open class Svuint64x4T(
    public var field0: Svuint64T,
    public var field1: Svuint64T,
    public var field2: Svuint64T,
    public var field3: Svuint64T,
) : AsSigned<Any?, Svint64x4T> {
    public override fun asSigned(): Svint64x4T = transmuteUnchecked(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint64x4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svuint64x4T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Svuint64x4T =
                    Svuint64x4T(
                        (Svuint64T.typeLayout()).readNative(addr),
                        (Svuint64T.typeLayout()).readNative(addr + 8L),
                        (Svuint64T.typeLayout()).readNative(addr + 16L),
                        (Svuint64T.typeLayout()).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Svuint64x4T) {
                    (Svuint64T.typeLayout()).writeNative(addr, value.field0)
                    (Svuint64T.typeLayout()).writeNative(addr + 8L, value.field1)
                    (Svuint64T.typeLayout()).writeNative(addr + 16L, value.field2)
                    (Svuint64T.typeLayout()).writeNative(addr + 24L, value.field3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svuint64x4T =
                    Svuint64x4T(
                        (Svuint64T.typeLayout()).readBytes(buf, offset),
                        (Svuint64T.typeLayout()).readBytes(buf, offset + 8),
                        (Svuint64T.typeLayout()).readBytes(buf, offset + 16),
                        (Svuint64T.typeLayout()).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svuint64x4T) {
                    (Svuint64T.typeLayout()).writeBytes(buf, offset, value.field0)
                    (Svuint64T.typeLayout()).writeBytes(buf, offset + 8, value.field1)
                    (Svuint64T.typeLayout()).writeBytes(buf, offset + 16, value.field2)
                    (Svuint64T.typeLayout()).writeBytes(buf, offset + 24, value.field3)
                }
            }
    }
}

internal open class Svbool2T(
    public var value: Boolean,
) : SveInto<SvboolT> {
    public var field0: Boolean
        get() = value
        set(v) {
            value = v
        }

    public override fun sveInto(): SvboolT {
        fun convertToSvbool(b: Svbool2T): SvboolT = null as SvboolT
        return convertToSvbool(this)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svbool2T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svbool2T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Svbool2T =
                    Svbool2T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svbool2T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svbool2T =
                    Svbool2T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svbool2T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Svbool4T(
    public var value: Boolean,
) : SveInto<SvboolT> {
    public var field0: Boolean
        get() = value
        set(v) {
            value = v
        }

    public override fun sveInto(): SvboolT = convertToSvbool(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svbool4T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svbool4T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Svbool4T =
                    Svbool4T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svbool4T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svbool4T =
                    Svbool4T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svbool4T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.value)
                }
            }
    }
}

internal open class Svbool8T(
    public var value: Boolean,
) : SveInto<SvboolT> {
    public var field0: Boolean
        get() = value
        set(v) {
            value = v
        }

    public override fun sveInto(): SvboolT = convertToSvbool(this)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svbool8T> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Svbool8T> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Svbool8T =
                    Svbool8T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Svbool8T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Svbool8T =
                    Svbool8T(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Svbool8T) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.value)
                }
            }
    }
}
