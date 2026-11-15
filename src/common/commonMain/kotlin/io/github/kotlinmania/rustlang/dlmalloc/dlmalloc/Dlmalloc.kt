// port-lint: source dlmalloc.rs
package io.github.kotlinmania.rustlang.dlmalloc.dlmalloc

import io.github.kotlinmania.rustlang.Allocator
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.rustLeadingZeros
import io.github.kotlinmania.rustlang.core.num.rustTrailingZeros
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr

public open class Dlmalloc<A> internal constructor(
    public var smallmap: UInt,
    public var treemap: UInt,
    smallbinsCtorArg: List<MutPtr>,
    treebinsCtorArg: List<MutPtr>,
    public var dvsize: Int,
    public var topsize: Int,
    dvCtorArg: MutPtr,
    topCtorArg: MutPtr,
    public var footprint: Int,
    public var maxFootprint: Int,
    public var seg: Segment,
    public var trimCheck: Int,
    leastAddrCtorArg: MutPtr,
    public var releaseChecks: Int,
    public var systemAllocator: A,
) {
    public var smallbins: List<MutPtr> = smallbinsCtorArg
        internal set

    public var treebins: List<MutPtr> = treebinsCtorArg
        internal set

    public var dv: MutPtr = dvCtorArg
        internal set

    public var top: MutPtr = topCtorArg
        internal set

    public var leastAddr: MutPtr = leastAddrCtorArg
        internal set

    public open fun allocator(): A = this.systemAllocator

    public open fun allocatorMut(): kotlin.reflect.KMutableProperty0<A> {
        return this::systemAllocator
    }

    // Note: can we get rid of this?
    public open fun mallocAlignment(): Int = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) * 2

    // Note: dox
    public open fun chunkOverhead(): Int = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

    public open fun mmapChunkOverhead(): Int = 2 * sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

    // Note: dox
    public open fun minLargeSize(): Int = 1 shl TREEBIN_SHIFT

    // Note: dox
    public open fun maxSmallSize(): Int = this.minLargeSize() - 1

    // Note: dox
    public open fun maxSmallRequest(): Int = this.maxSmallSize() - (this.mallocAlignment() - 1) - this.chunkOverhead()

    // Note: dox
    public open fun minChunkSize(): Int = alignUp(sizeOf<Chunk>(typeLayoutT = Chunk.typeLayout()), this.mallocAlignment())

    // Note: dox
    public open fun minRequest(): Int = this.minChunkSize() - this.chunkOverhead() - 1

    // Note: dox
    public open fun maxRequest(): Int {
        // minSysAllocSpace: the largest `X` such that
        // padRequest(X - 1)        -- minus 1, because requests of exactly
        // `maxRequest` will not be honored
        // + self.topFootSize()
        // + self.mallocAlignment()
        // + DEFAULT_GRANULARITY
        // ==
        // usize.Max
        val minSysAllocSpace = ((0.inv() - (DEFAULT_GRANULARITY + this.topFootSize() + this.mallocAlignment()) + 1) and this.mallocAlignment().inv()) - this.chunkOverhead() + 1
        return minOf((!this.minChunkSize() + 1) shl 2, minSysAllocSpace)
    }

    public open fun padRequest(amt: Int): Int = alignUp(amt + this.chunkOverhead(), this.mallocAlignment())

    public open fun smallIndex(size: Int): UInt = (size shr SMALLBIN_SHIFT).toUInt()

    public open fun smallIndex2size(idx: UInt): Int = Int.tryFrom(idx).unwrap() shl SMALLBIN_SHIFT

    public open fun isSmall(s: Int): Boolean = s shr SMALLBIN_SHIFT < NSMALLBINS

    public open fun isAligned(a: Int): Boolean = a and (this.mallocAlignment() - 1) == 0

    public open fun alignOffset(addr: MutPtr): Int = addr.alignOffset(this.mallocAlignment())

    public open fun alignOffsetUsize(addr: Int): Int = alignUp(addr, this.mallocAlignment()) - addr

    public open fun topFootSize(): Int = this.alignOffsetUsize(Chunk.memOffset()) + this.padRequest(sizeOf<Segment>(typeLayoutT = Segment.typeLayout())) + this.minChunkSize()

    public open fun mmapFootPad(): Int = 4 * sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

    public open fun alignAsChunk(ptr: MutPtr): MutPtr =
        run {
            val chunk = Chunk.toMem(MutPtr((ptr).rawAddress))
            MutPtr((MutPtr((ptr).rawAddress + ((this.alignOffset(chunk)).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)
        }

    public open fun request2size(req: Int): Int =
        if (req < this.minRequest()) {
            this.minChunkSize()
        } else {
            this.padRequest(req)
        }

    public open fun overheadFor(p: MutPtr): Int =
        if (Chunk.mmapped(p)) {
            this.mmapChunkOverhead()
        } else {
            this.chunkOverhead()
        }

    public open fun callocMustClear(ptr: MutPtr): Boolean = !this.systemAllocator.allocatesZeros() || !Chunk.mmapped(Chunk.fromMem(ptr))

    public open fun malloc(size: Int): MutPtr {
        this.checkMallocState()
        val nb: Any?
        if (size <= this.maxSmallRequest()) {
            nb = this.request2size(size)
            var idx = this.smallIndex(nb)
            val smallbits = this.smallmap shr idx.toInt()
            // Check the bin for `idx` (the lowest bit) but also check the next
            // bin up to use that to satisfy our request, if needed.
            if (smallbits and 0x3u != 0u) {
                // If our the lowest bit, our `idx`, is unset then bump up the
                // index as we'll be using the next bucket up.
                idx += smallbits.inv() and 1
                val b = this.smallbinAt(idx)
                val p = b.prev
                this.unlinkFirstSmallChunk(b, p, idx)
                val smallsize = this.smallIndex2size(idx)
                Chunk.setInuseAndPinuse(p, smallsize)
                val ret = Chunk.toMem(p)
                this.checkMallocedChunk(ret, nb)
                return ret
            }
            if (nb > this.dvsize) {
                // If there's some other bin with some memory, then we just use
                // the next smallest bin
                if (smallbits != 0u) {
                    val leftbits = (smallbits shl idx.toInt()) and leftBits(1u shl idx.toInt())
                    val leastbit = leastBit(leftbits)
                    val i = rustTrailingZeros(leastbit)
                    val b1 = this.smallbinAt(i)
                    val p1 = b1.prev
                    if (false) {
                        check(Chunk.size(p1) == this.smallIndex2size(i))
                    }
                    this.unlinkFirstSmallChunk(b1, p1, i)
                    val smallsize1 = this.smallIndex2size(i)
                    val rsize = smallsize1 - nb
                    if (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) != 4 && rsize < this.minChunkSize()) {
                        Chunk.setInuseAndPinuse(p1, smallsize1)
                    } else {
                        Chunk.setSizeAndPinuseOfInuseChunk(p1, nb)
                        val r = Chunk.plusOffset(p1, nb)
                        Chunk.setSizeAndPinuseOfFreeChunk(r, rsize)
                        this.replaceDv(r, rsize)
                    }
                    val ret1 = Chunk.toMem(p1)
                    this.checkMallocedChunk(ret1, nb)
                    return ret1
                } else if (this.treemap != 0u) {
                    val mem = this.tmallocSmall(nb)
                    if (!mem.isNull()) {
                        this.checkMallocedChunk(mem, nb)
                        this.checkMallocState()
                        return mem
                    }
                }
            }
        } else if (size >= this.maxRequest()) {
            // Note: translate this to unsupported
            return MutPtr(0L)
        } else {
            nb = this.padRequest(size)
            if (this.treemap != 0u) {
                val mem1 = this.tmallocLarge(nb)
                if (!mem1.isNull()) {
                    this.checkMallocedChunk(mem1, nb)
                    this.checkMallocState()
                    return mem1
                }
            }
        }
        // use the `dv` node if we can, splitting it if necessary or otherwise
        // exhausting the entire chunk
        if (nb <= this.dvsize) {
            val rsize1 = this.dvsize - nb
            val p2 = this.dv
            if (rsize1 >= this.minChunkSize()) {
                this.dv = Chunk.plusOffset(p2, nb)
                this.dvsize = rsize1
                val r1 = this.dv
                Chunk.setSizeAndPinuseOfFreeChunk(r1, rsize1)
                Chunk.setSizeAndPinuseOfInuseChunk(p2, nb)
            } else {
                val dvs = this.dvsize
                this.dvsize = 0
                this.dv = MutPtr(0L)
                Chunk.setInuseAndPinuse(p2, dvs)
            }
            val ret2 = Chunk.toMem(p2)
            this.checkMallocedChunk(ret2, nb)
            this.checkMallocState()
            return ret2
        }
        // Split the top node if we can
        if (nb < this.topsize) {
            this.topsize -= nb
            val rsize2 = this.topsize
            val p3 = this.top
            this.top = Chunk.plusOffset(p3, nb)
            val r2 = this.top
            (Chunk.typeLayout()).readNative((r2).rawAddress).head = rsize2 or PINUSE
            Chunk.setSizeAndPinuseOfInuseChunk(p3, nb)
            this.checkTopChunk(this.top)
            val ret3 = Chunk.toMem(p3)
            this.checkMallocedChunk(ret3, nb)
            this.checkMallocState()
            return ret3
        }
        return this.sysAlloc(nb)
    }

    /**
     * allocates system resources
     */
    public open fun sysAlloc(size: Int): MutPtr {
        this.checkMallocState()
        // keep in sync with maxRequest
        val asize = alignUp(size + this.topFootSize() + this.mallocAlignment(), DEFAULT_GRANULARITY)
        val (tbase, tsize, flags) = this.systemAllocator.alloc(asize)
        if (tbase.isNull()) {
            return tbase
        }
        this.footprint += tsize
        this.maxFootprint = max(this.maxFootprint, this.footprint)
        if (((this.top).rawAddress == 0L)) {
            if (((this.leastAddr).rawAddress == 0L) || ((tbase).rawAddress.toULong() < (this.leastAddr).rawAddress.toULong())) {
                this.leastAddr = tbase
            }
            this.seg.base = tbase
            this.seg.size = tsize
            this.seg.flags = flags
            this.releaseChecks = MAX_RELEASE_CHECK_RATE
            this.initBins()
            val tsize1 = tsize - this.topFootSize()
            this.initTop(tbase.cast(), tsize1)
            // let mn = Chunk.next(Chunk.fromMem(self as *mut _ as *mut u8));
            // let topFootSize = self.topFootSize();
            // self.initTop(mn, tbase as Int + tsize - mn as Int - topFootSize);
        } else {
            var sp: MutPtr = this.seg
            while (!((sp).rawAddress == 0L) && ((tbase).rawAddress.toULong() != (Segment.top(sp)).rawAddress.toULong())) {
                sp = (Segment.typeLayout()).readNative((sp).rawAddress).next
            }
            if (!((sp).rawAddress == 0L) && !Segment.isExtern(sp) && Segment.sysFlags(sp) == flags && Segment.holds(sp, MutPtr((this.top).rawAddress))) {
                (Segment.typeLayout()).readNative((sp).rawAddress).size += tsize
                val ptr = this.top
                val size1 = this.topsize + tsize
                this.initTop(ptr, size1)
            } else {
                this.leastAddr = minOf(tbase, this.leastAddr)
                var sp1: MutPtr = this.seg
                while (!((sp1).rawAddress == 0L) && (((Segment.typeLayout()).readNative((sp1).rawAddress).base).rawAddress.toULong() != (tbase.wrappingAdd(tsize)).rawAddress.toULong())) {
                    sp1 = (Segment.typeLayout()).readNative((sp1).rawAddress).next
                }
                if (!((sp1).rawAddress == 0L) && !Segment.isExtern(sp1) && Segment.sysFlags(sp1) == flags) {
                    val oldbase = (Segment.typeLayout()).readNative((sp1).rawAddress).base
                    (Segment.typeLayout()).readNative((sp1).rawAddress).base = tbase
                    (Segment.typeLayout()).readNative((sp1).rawAddress).size += tsize
                    return this.prependAlloc(tbase, oldbase, size)
                } else {
                    this.addSegment(tbase, tsize, flags)
                }
            }
        }
        if (size < this.topsize) {
            this.topsize -= size
            val rsize = this.topsize
            val p = this.top
            this.top = Chunk.plusOffset(p, size)
            val r = this.top
            (Chunk.typeLayout()).readNative((r).rawAddress).head = rsize or PINUSE
            Chunk.setSizeAndPinuseOfInuseChunk(p, size)
            val ret = Chunk.toMem(p)
            this.checkTopChunk(this.top)
            this.checkMallocedChunk(ret, size)
            this.checkMallocState()
            return ret
        }
        return MutPtr(0L)
    }

    public open fun realloc(oldmem: MutPtr, bytes: Int): MutPtr {
        if (bytes >= this.maxRequest()) {
            return MutPtr(0L)
        }
        val nb = this.request2size(bytes)
        val oldp = Chunk.fromMem(oldmem)
        val newp = this.tryReallocChunk(oldp, nb, true)
        if (!newp.isNull()) {
            this.checkInuseChunk(newp)
            return Chunk.toMem(newp)
        }
        val ptr = this.malloc(bytes)
        if (!ptr.isNull()) {
            val oc = Chunk.size(oldp) - this.overheadFor(oldp)
            ptr.copyNonoverlapping(oldmem, ptr, minOf(oc, bytes), typeLayoutT = PrimitiveLayouts.U8)
            this.free(oldmem)
        }
        return ptr
    }

    public open fun tryReallocChunk(p: MutPtr, nb: Int, canMove: Boolean): MutPtr {
        val oldsize = Chunk.size(p)
        val next = Chunk.plusOffset(p, oldsize)
        return if (Chunk.mmapped(p)) {
            this.mmapResize(p, nb, canMove)
        } else if (oldsize >= nb) {
            val rsize = oldsize - nb
            if (rsize >= this.minChunkSize()) {
                val r = Chunk.plusOffset(p, nb)
                Chunk.setInuse(p, nb)
                Chunk.setInuse(r, rsize)
                this.disposeChunk(r, rsize)
            }
            p
        } else if (((next).rawAddress.toULong() == (this.top).rawAddress.toULong())) {
            // extend into top
            if (oldsize + this.topsize <= nb) {
                return MutPtr(0L)
            }
            val newsize = oldsize + this.topsize
            val newtopsize = newsize - nb
            val newtop = Chunk.plusOffset(p, nb)
            Chunk.setInuse(p, nb)
            (Chunk.typeLayout()).readNative((newtop).rawAddress).head = newtopsize or PINUSE
            this.top = newtop
            this.topsize = newtopsize
            p
        } else if (((next).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
            // extend into dv
            val dvs = this.dvsize
            if (oldsize + dvs < nb) {
                return MutPtr(0L)
            }
            val dsize = oldsize + dvs - nb
            if (dsize >= this.minChunkSize()) {
                val r1 = Chunk.plusOffset(p, nb)
                val n = Chunk.plusOffset(r1, dsize)
                Chunk.setInuse(p, nb)
                Chunk.setSizeAndPinuseOfFreeChunk(r1, dsize)
                Chunk.clearPinuse(n)
                this.dvsize = dsize
                this.dv = r1
            } else {
                // exhaust dv
                val newsize1 = oldsize + dvs
                Chunk.setInuse(p, newsize1)
                this.dvsize = 0
                this.dv = MutPtr(0L)
            }
            return p
        } else if (!Chunk.cinuse(next)) {
            // extend into the next free chunk
            val nextsize = Chunk.size(next)
            if (oldsize + nextsize < nb) {
                return MutPtr(0L)
            }
            val rsize1 = oldsize + nextsize - nb
            this.unlinkChunk(next, nextsize)
            if (rsize1 < this.minChunkSize()) {
                val newsize2 = oldsize + nextsize
                Chunk.setInuse(p, newsize2)
            } else {
                val r2 = Chunk.plusOffset(p, nb)
                Chunk.setInuse(p, nb)
                Chunk.setInuse(r2, rsize1)
                this.disposeChunk(r2, rsize1)
            }
            p
        } else {
            MutPtr(0L)
        }
    }

    public open fun mmapResize(oldp: MutPtr, nb: Int, canMove: Boolean): MutPtr {
        val oldsize = Chunk.size(oldp)
        // Can't shrink mmap regions below a small size
        if (this.isSmall(nb)) {
            return MutPtr(0L)
        }
        // Keep the old chunk if it's big enough but not too big
        if (oldsize >= nb + sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) && (oldsize - nb) <= (DEFAULT_GRANULARITY shl 1)) {
            return oldp
        }
        val offset = (Chunk.typeLayout()).readNative((oldp).rawAddress).prevFoot
        val oldmmsize = oldsize + offset + this.mmapFootPad()
        val newmmsize = this.mmapAlign(nb + 6 * sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) + this.mallocAlignment() - 1)
        val ptr = this.systemAllocator.remap(MutPtr((MutPtr((oldp).rawAddress)).rawAddress - ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes)), oldmmsize, newmmsize, canMove)
        if (ptr.isNull()) {
            return MutPtr(0L)
        }
        val newp = ptr.wrappingAdd(offset).cast()
        val psize = newmmsize - offset - this.mmapFootPad()
        newp.head = psize
        (Chunk.typeLayout()).readNative((Chunk.plusOffset(newp, psize)).rawAddress).head = Chunk.fencepostHead()
        (Chunk.typeLayout()).readNative((Chunk.plusOffset(newp, psize + sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))).rawAddress).head = 0
        this.leastAddr = minOf(ptr, this.leastAddr)
        this.footprint = this.footprint + newmmsize - oldmmsize
        this.maxFootprint = max(this.maxFootprint, this.footprint)
        this.checkMmappedChunk(newp)
        return newp
    }

    public open fun mmapAlign(a: Int): Int = alignUp(a, this.systemAllocator.pageSize())

    // Only call this with power-of-two alignment and alignment >
    // `self.mallocAlignment()`
    public open fun memalign(alignment: Int, bytes: Int): MutPtr {
        var alignment1 = alignment
        if (alignment1 < this.minChunkSize()) {
            alignment1 = this.minChunkSize()
        }
        if (bytes >= this.maxRequest() - alignment1) {
            return MutPtr(0L)
        }
        val nb = this.request2size(bytes)
        val req = nb + alignment1 + this.minChunkSize() - this.chunkOverhead()
        val mem = this.malloc(req)
        if (mem.isNull()) {
            return mem
        }
        var p = Chunk.fromMem(mem)
        if (mem.toInt() and (alignment1 - 1) != 0) {
            // Here we find an aligned sopt inside the chunk. Since we need to
            // give back leading space in a chunk of at least `minChunkSize`,
            // if the first calculation places us at a spot with less than
            // `minChunkSize` leader we can move to the next aligned spot.
            // we've allocated enough total room so that this is always possible
            val br = Chunk.fromMem(MutPtr(((mem.toInt() + alignment1 - 1) and (!alignment1 + 1)).toLong()))
            val pos =
                if ((br.rawAddress.toInt() - p.rawAddress.toInt()) > this.minChunkSize()) {
                    MutPtr((br).rawAddress)
                } else {
                    MutPtr((MutPtr((br).rawAddress)).rawAddress + ((alignment1).toLong() * (PrimitiveLayouts.U8).strideBytes))
                }
            val newp = MutPtr((pos).rawAddress)
            val leadsize = pos.rawAddress.toInt() - p.rawAddress.toInt()
            val newsize = Chunk.size(p) - leadsize
            // for mmapped chunks just adjust the offset
            if (Chunk.mmapped(p)) {
                (Chunk.typeLayout()).readNative((newp).rawAddress).prevFoot = (Chunk.typeLayout()).readNative((p).rawAddress).prevFoot + leadsize
                (Chunk.typeLayout()).readNative((newp).rawAddress).head = newsize
            } else {
                // give back the leader, use the rest
                Chunk.setInuse(newp, newsize)
                Chunk.setInuse(p, leadsize)
                this.disposeChunk(p, leadsize)
            }
            p = newp
        }
        // give back spare room at the end
        if (!Chunk.mmapped(p)) {
            val size = Chunk.size(p)
            if (size > nb + this.minChunkSize()) {
                val remainderSize = size - nb
                val remainder = Chunk.plusOffset(p, nb)
                Chunk.setInuse(p, nb)
                Chunk.setInuse(remainder, remainderSize)
                this.disposeChunk(remainder, remainderSize)
            }
        }
        val mem1 = Chunk.toMem(p)
        if (false) {
            check(Chunk.size(p) >= nb)
        }
        if (false) {
            check(alignUp(mem1.rawAddress.toInt(), alignment1) == mem1.rawAddress.toInt())
        }
        this.checkInuseChunk(p)
        return mem1
    }

    // consolidate and bin a chunk, differs from exported versions of free
    // mainly in that the chunk need not be marked as inuse
    public open fun disposeChunk(p: MutPtr, psize: Int) {
        var p1 = p
        var psize1 = psize
        val next = Chunk.plusOffset(p1, psize1)
        if (!Chunk.pinuse(p1)) {
            val prevsize = (Chunk.typeLayout()).readNative((p1).rawAddress).prevFoot
            if (Chunk.mmapped(p1)) {
                psize1 += prevsize + this.mmapFootPad()
                if (this.systemAllocator.free(MutPtr((MutPtr((p1).rawAddress)).rawAddress - ((prevsize).toLong() * (PrimitiveLayouts.U8).strideBytes)), psize1)) {
                    this.footprint -= psize1
                }
                return
            }
            val prev = Chunk.minusOffset(p1, prevsize)
            psize1 += prevsize
            p1 = prev
            if (((p1).rawAddress.toULong() != (this.dv).rawAddress.toULong())) {
                this.unlinkChunk(p1, prevsize)
            } else if ((Chunk.typeLayout()).readNative((next).rawAddress).head and INUSE == INUSE) {
                this.dvsize = psize1
                Chunk.setFreeWithPinuse(p1, psize1, next)
                return
            }
        }
        if (!Chunk.cinuse(next)) {
            // consolidate forward
            if (((next).rawAddress.toULong() == (this.top).rawAddress.toULong())) {
                this.topsize += psize1
                val tsize = this.topsize
                this.top = p1
                (Chunk.typeLayout()).readNative((p1).rawAddress).head = tsize or PINUSE
                if (((p1).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
                    this.dv = MutPtr(0L)
                    this.dvsize = 0
                }
                return
            } else if (((next).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
                this.dvsize += psize1
                val dsize = this.dvsize
                this.dv = p1
                Chunk.setSizeAndPinuseOfFreeChunk(p1, dsize)
                return
            } else {
                val nsize = Chunk.size(next)
                psize1 += nsize
                this.unlinkChunk(next, nsize)
                Chunk.setSizeAndPinuseOfFreeChunk(p1, psize1)
                if (((p1).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
                    this.dvsize = psize1
                    return
                }
            }
        } else {
            Chunk.setFreeWithPinuse(p1, psize1, next)
        }
        this.insertChunk(p1, psize1)
    }

    public open fun initTop(ptr: MutPtr, size: Int) {
        val offset = this.alignOffset(Chunk.toMem(ptr))
        val p = Chunk.plusOffset(ptr, offset)
        val size1 = size - offset
        this.top = p
        this.topsize = size1
        (Chunk.typeLayout()).readNative((p).rawAddress).head = size1 or PINUSE
        (Chunk.typeLayout()).readNative((Chunk.plusOffset(p, size1)).rawAddress).head = this.topFootSize()
        this.trimCheck = DEFAULT_TRIM_THRESHOLD
    }

    public open fun initBins() {
        for (i in 0 until NSMALLBINS_U32) {
            val bin = this.smallbinAt(i)
            bin.next = bin
            bin.prev = bin
        }
    }

    public open fun prependAlloc(newbase: MutPtr, oldbase: MutPtr, size: Int): MutPtr {
        val p = this.alignAsChunk(newbase)
        var oldfirst = this.alignAsChunk(oldbase)
        val psize = oldfirst.toInt() - p.toInt()
        val q = Chunk.plusOffset(p, size)
        var qsize = psize - size
        Chunk.setSizeAndPinuseOfInuseChunk(p, size)
        if (false) {
            check(((oldfirst).rawAddress.toULong() > (q).rawAddress.toULong()))
        }
        if (false) {
            check(Chunk.pinuse(oldfirst))
        }
        if (false) {
            check(qsize >= this.minChunkSize())
        }
        // consolidate the remainder with the first chunk of the old base
        if (((oldfirst).rawAddress.toULong() == (this.top).rawAddress.toULong())) {
            this.topsize += qsize
            val tsize = this.topsize
            this.top = q
            (Chunk.typeLayout()).readNative((q).rawAddress).head = tsize or PINUSE
            this.checkTopChunk(q)
        } else if (((oldfirst).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
            this.dvsize += qsize
            val dsize = this.dvsize
            this.dv = q
            Chunk.setSizeAndPinuseOfFreeChunk(q, dsize)
        } else {
            if (!Chunk.inuse(oldfirst)) {
                val nsize = Chunk.size(oldfirst)
                this.unlinkChunk(oldfirst, nsize)
                oldfirst = Chunk.plusOffset(oldfirst, nsize)
                qsize += nsize
            }
            Chunk.setFreeWithPinuse(q, qsize, oldfirst)
            this.insertChunk(q, qsize)
            this.checkFreeChunk(q)
        }
        val ret = Chunk.toMem(p)
        this.checkMallocedChunk(ret, size)
        this.checkMallocState()
        return ret
    }

    // add a segment to hold a new noncontiguous region
    public open fun addSegment(tbase: MutPtr, tsize: Int, flags: UInt) {
        // Note: what in the world is this function doing
        // Determine locations and sizes of segment, fenceposts, and the old top
        val oldTop = MutPtr((this.top).rawAddress)
        val oldsp = this.segmentHolding(oldTop)
        val oldEnd = Segment.top(oldsp)
        val ssize = this.padRequest(sizeOf<Segment>(typeLayoutT = Segment.typeLayout()))
        val offset = ssize + sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) * 4 + this.mallocAlignment() - 1
        val rawsp = MutPtr((oldEnd).rawAddress - ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes))
        val offset1 = this.alignOffset(Chunk.toMem(rawsp))
        val asp = MutPtr((rawsp).rawAddress + ((offset1).toLong() * (PrimitiveLayouts.U8).strideBytes))
        val csp =
            if (((asp).rawAddress.toULong() < (MutPtr((oldTop).rawAddress + ((this.minChunkSize()).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress.toULong())) {
                oldTop
            } else {
                asp
            }
        val sp = MutPtr((csp).rawAddress)
        val ss = MutPtr((Chunk.toMem(sp)).rawAddress)
        val tnext = Chunk.plusOffset(sp, ssize)
        var p = tnext
        var nfences = 0
        // reset the top to our new space
        val size = tsize - this.topFootSize()
        this.initTop(tbase, size)
        if (false) {
            check(this.isAligned(ss.rawAddress.toInt()))
        }
        Chunk.setSizeAndPinuseOfInuseChunk(sp, ssize)
        (Segment.typeLayout()).writeNative((ss).rawAddress, this.seg)
        // push our current record
        this.seg.base = tbase
        this.seg.size = tsize
        this.seg.flags = flags
        this.seg.next = ss
        // insert trailing fences
        while (true) {
            val nextp = Chunk.plusOffset(p, sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))
            (Chunk.typeLayout()).readNative((p).rawAddress).head = Chunk.fencepostHead()
            nfences += 1
            if (((addrOf((Chunk.typeLayout()).readNative((nextp).rawAddress).head).cast()).rawAddress.toULong() < (oldEnd).rawAddress.toULong())) {
                p = nextp
            } else {
                break
            }
        }
        if (false) {
            check(nfences >= 2)
        }
        // insert the rest of the old top into a bin as an ordinary free chunk
        if (((csp).rawAddress.toULong() != (oldTop).rawAddress.toULong())) {
            val q = MutPtr((oldTop).rawAddress)
            val psize = csp.rawAddress.toInt() - oldTop.rawAddress.toInt()
            val tn = Chunk.plusOffset(q, psize)
            Chunk.setFreeWithPinuse(q, psize, tn)
            this.insertChunk(q, psize)
        }
        this.checkTopChunk(this.top)
        this.checkMallocState()
    }

    public open fun segmentHolding(ptr: MutPtr): MutPtr {
        var sp = (constPtrFromRustCast(this.seg)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        while (!((sp).rawAddress == 0L)) {
            if ((((Segment.typeLayout()).readNative((sp).rawAddress).base).rawAddress.toULong() <= (ptr).rawAddress.toULong()) && ((ptr).rawAddress.toULong() < (Segment.top(sp)).rawAddress.toULong())) {
                return sp
            }
            sp = (Segment.typeLayout()).readNative((sp).rawAddress).next
        }
        return MutPtr(0L)
    }

    public open fun tmallocSmall(size: Int): MutPtr {
        val leastbit = leastBit(this.treemap)
        val i = rustTrailingZeros(leastbit)
        var v = this.treebinAt(i)
        var t = v
        var rsize = Chunk.size(TreeChunk.Chunk(t)) - size
        while (true) {
            t = TreeChunk.leftmostChild(t)
            if (t.isNull()) {
                break
            }
            val trem = Chunk.size(TreeChunk.Chunk(t)) - size
            if (trem < rsize) {
                rsize = trem
                v = t
            }
        }
        val vc = TreeChunk.Chunk(v)
        val r = MutPtr((Chunk.plusOffset(vc, size)).rawAddress)
        if (false) {
            check(Chunk.size(vc) == rsize + size)
        }
        this.unlinkLargeChunk(v)
        if (rsize < this.minChunkSize()) {
            Chunk.setInuseAndPinuse(vc, rsize + size)
        } else {
            val rc = TreeChunk.Chunk(r)
            Chunk.setSizeAndPinuseOfInuseChunk(vc, size)
            Chunk.setSizeAndPinuseOfFreeChunk(rc, rsize)
            this.replaceDv(rc, rsize)
        }
        return Chunk.toMem(vc)
    }

    public open fun tmallocLarge(size: Int): MutPtr {
        var v = MutPtr(0L)
        var rsize = !size + 1
        val idx = this.computeTreeIndex(size)
        var t = this.treebinAt(idx)
        if (!t.isNull()) {
            // Traverse thre tree for this bin looking for a node with size
            // equal to the `size` above.
            var sizebits = size shl leftshiftForTreeIndex(idx).toInt()
            // Keep track of the deepest untaken right subtree
            var rst = MutPtr(0L)
            while (true) {
                val csize = Chunk.size(TreeChunk.Chunk(t))
                if (csize >= size && csize - size < rsize) {
                    v = t
                    rsize = csize - size
                    if (rsize == 0) {
                        break
                    }
                }
                val rt = t.child[1]
                t = t.child[(sizebits shr (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) * 8 - 1).toInt()) and 1]
                if (!rt.isNull() && rt != t) {
                    rst = rt
                }
                if (t.isNull()) {
                    // Reset `t` to the least subtree holding sizes greater than
                    // the `size` above, breaking out
                    t = rst
                    break
                }
                sizebits = sizebits shl 1
            }
        }
        // Set t to the root of the next non-empty treebin
        if (t.isNull() && v.isNull()) {
            val leftbits = leftBits(1u shl idx.toInt()) and this.treemap
            if (leftbits != 0u) {
                val leastbit = leastBit(leftbits)
                val i = rustTrailingZeros(leastbit)
                t = this.treebinAt(i)
            }
        }
        // Find the smallest of this tree or subtree
        while (!t.isNull()) {
            val csize1 = Chunk.size(TreeChunk.Chunk(t))
            if (csize1 >= size && csize1 - size < rsize) {
                rsize = csize1 - size
                v = t
            }
            t = TreeChunk.leftmostChild(t)
        }
        // If dv is a better fit, then return null so malloc will use it
        if (v.isNull() || (this.dvsize >= size && !(rsize < this.dvsize - size))) {
            return MutPtr(0L)
        }
        val vc = TreeChunk.Chunk(v)
        val r = Chunk.plusOffset(vc, size)
        if (false) {
            check(Chunk.size(vc) == rsize + size)
        }
        this.unlinkLargeChunk(v)
        if (rsize < this.minChunkSize()) {
            Chunk.setInuseAndPinuse(vc, rsize + size)
        } else {
            Chunk.setSizeAndPinuseOfInuseChunk(vc, size)
            Chunk.setSizeAndPinuseOfFreeChunk(r, rsize)
            this.insertChunk(r, rsize)
        }
        return Chunk.toMem(vc)
    }

    public open fun smallbinAt(idx: UInt): MutPtr {
        val idx1 = Int.tryFrom(idx * 2u).unwrap()
        if (false) {
            check(idx1 < this.smallbins.size)
        }
        return MutPtr((MutPtr((this.smallbins.asMutPtr()).rawAddress + ((idx1).toLong() * (PrimitiveLayouts.mutPtrLayout(64L)).strideBytes))).rawAddress)
    }

    public open fun treebinAt(idx: UInt): MutPtr {
        val idx1 = Int.tryFrom(idx).unwrap()
        if (false) {
            check(idx1 < this.treebins.size)
        }
        return MutPtr((this.treebins.asMutPtr()).rawAddress + ((idx1).toLong() * (PrimitiveLayouts.mutPtrLayout(64L)).strideBytes))
    }

    public open fun computeTreeIndex(size: Int): UInt {
        val x = size shr TREEBIN_SHIFT
        return if (x == 0) {
            0u
        } else if (x > 0xffff) {
            NTREEBINS_U32 - 1u
        } else {
            val k = sizeOfVal(x) * 8 - 1 - (rustLeadingZeros(x).toInt())
            ((k shl 1) + (size shr (k + TREEBIN_SHIFT - 1) and 1)).toUInt()
        }
    }

    public open fun unlinkFirstSmallChunk(head: MutPtr, next: MutPtr, idx: UInt) {
        val ptr = (Chunk.typeLayout()).readNative((next).rawAddress).prev
        if (false) {
            check(((next).rawAddress.toULong() != (head).rawAddress.toULong()))
        }
        if (false) {
            check(((next).rawAddress.toULong() != (ptr).rawAddress.toULong()))
        }
        if (false) {
            check(Chunk.size(next) == this.smallIndex2size(idx))
        }
        if (((head).rawAddress.toULong() == (ptr).rawAddress.toULong())) {
            this.clearSmallmap(idx)
        } else {
            (Chunk.typeLayout()).readNative((ptr).rawAddress).next = head
            (Chunk.typeLayout()).readNative((head).rawAddress).prev = ptr
        }
    }

    public open fun replaceDv(chunk: MutPtr, size: Int) {
        val dvs = this.dvsize
        if (false) {
            check(this.isSmall(dvs))
        }
        if (dvs != 0) {
            val dv = this.dv
            this.insertSmallChunk(dv, dvs)
        }
        this.dvsize = size
        this.dv = chunk
    }

    public open fun insertChunk(chunk: MutPtr, size: Int) {
        if (this.isSmall(size)) {
            this.insertSmallChunk(chunk, size)
        } else {
            this.insertLargeChunk(chunk, size)
        }
    }

    public open fun insertSmallChunk(chunk: MutPtr, size: Int) {
        val idx = this.smallIndex(size)
        if (false) {
            check(size >= this.minChunkSize())
        }
        val (f, head2) =
            if (!this.smallmapIsMarked(idx)) {
                this.markSmallmap(idx)
                val head = this.smallbinAt(idx)
                Pair(head, head)
            } else {
                val head1 = this.smallbinAt(idx)
                Pair(head1.prev, head1)
            }
        head2.prev = chunk
        f.next = chunk
        (Chunk.typeLayout()).readNative((chunk).rawAddress).prev = f
        (Chunk.typeLayout()).readNative((chunk).rawAddress).next = head2
    }

    public open fun insertLargeChunk(chunk: MutPtr, size: Int) {
        val idx = this.computeTreeIndex(size)
        val h = this.treebinAt(idx)
        (TreeChunk.typeLayout()).readNative((chunk).rawAddress).index = idx
        (TreeChunk.typeLayout()).readNative((chunk).rawAddress).child[0] = MutPtr(0L)
        (TreeChunk.typeLayout()).readNative((chunk).rawAddress).child[1] = MutPtr(0L)
        val chunkc = TreeChunk.chunk(chunk)
        if (!this.treemapIsMarked(idx)) {
            h = chunk
            (TreeChunk.typeLayout()).readNative((chunk).rawAddress).parent = h.cast()
            // Note: dubious?
            (Chunk.typeLayout()).readNative((chunkc).rawAddress).next = chunkc
            (Chunk.typeLayout()).readNative((chunkc).rawAddress).prev = chunkc
            this.markTreemap(idx)
        } else {
            var t = h
            var k = size shl leftshiftForTreeIndex(idx).toInt()
            while (true) {
                if (Chunk.size(TreeChunk.chunk(t)) != size) {
                    val c = t.child[(k shr (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) * 8 - 1).toInt()) and 1]
                    k = k shl 1
                    if (!c.isNull()) {
                        t = c
                    } else {
                        c = chunk
                        (TreeChunk.typeLayout()).readNative((chunk).rawAddress).parent = t
                        (Chunk.typeLayout()).readNative((chunkc).rawAddress).next = chunkc
                        (Chunk.typeLayout()).readNative((chunkc).rawAddress).prev = chunkc
                        break
                    }
                } else {
                    val tc = TreeChunk.chunk(t)
                    val f = (Chunk.typeLayout()).readNative((tc).rawAddress).prev
                    (Chunk.typeLayout()).readNative((f).rawAddress).next = chunkc
                    (Chunk.typeLayout()).readNative((tc).rawAddress).prev = chunkc
                    (Chunk.typeLayout()).readNative((chunkc).rawAddress).prev = f
                    (Chunk.typeLayout()).readNative((chunkc).rawAddress).next = tc
                    (TreeChunk.typeLayout()).readNative((chunk).rawAddress).parent = MutPtr(0L)
                    break
                }
            }
        }
    }

    public open fun smallmapIsMarked(idx: UInt): Boolean = this.smallmap and (1u shl idx.toInt()) != 0u

    public open fun markSmallmap(idx: UInt) {
        this.smallmap = this.smallmap or (1u shl idx.toInt())
    }

    public open fun clearSmallmap(idx: UInt) {
        this.smallmap = this.smallmap and (1u shl idx.toInt()).inv()
    }

    public open fun treemapIsMarked(idx: UInt): Boolean = this.treemap and (1u shl idx.toInt()) != 0u

    public open fun markTreemap(idx: UInt) {
        this.treemap = this.treemap or (1u shl idx.toInt())
    }

    public open fun clearTreemap(idx: UInt) {
        this.treemap = this.treemap and (1u shl idx.toInt()).inv()
    }

    public open fun unlinkChunk(chunk: MutPtr, size: Int) {
        if (this.isSmall(size)) {
            this.unlinkSmallChunk(chunk, size)
        } else {
            this.unlinkLargeChunk(chunk)
        }
    }

    public open fun unlinkSmallChunk(chunk: MutPtr, size: Int) {
        val f = (Chunk.typeLayout()).readNative((chunk).rawAddress).prev
        val b = (Chunk.typeLayout()).readNative((chunk).rawAddress).next
        val idx = this.smallIndex(size)
        if (false) {
            check(((chunk).rawAddress.toULong() != (b).rawAddress.toULong()))
        }
        if (false) {
            check(((chunk).rawAddress.toULong() != (f).rawAddress.toULong()))
        }
        if (false) {
            check(Chunk.size(chunk) == this.smallIndex2size(idx))
        }
        if (((b).rawAddress.toULong() == (f).rawAddress.toULong())) {
            this.clearSmallmap(idx)
        } else {
            (Chunk.typeLayout()).readNative((f).rawAddress).next = b
            (Chunk.typeLayout()).readNative((b).rawAddress).prev = f
        }
    }

    public open fun unlinkLargeChunk(chunk: MutPtr) {
        val xp = (TreeChunk.typeLayout()).readNative((chunk).rawAddress).parent
        var r: Any?
        if (((TreeChunk.next(chunk)).rawAddress.toULong() != (chunk).rawAddress.toULong())) {
            val f = TreeChunk.prev(chunk)
            r = TreeChunk.next(chunk)
            (TreeChunk.typeLayout()).readNative((f).rawAddress).chunk.next = TreeChunk.chunk(r)
            r.chunk.prev = TreeChunk.chunk(f)
        } else {
            var rp = (TreeChunk.typeLayout()).readNative((chunk).rawAddress).child[1]
            if (((rp).rawAddress == 0L)) {
                rp = (TreeChunk.typeLayout()).readNative((chunk).rawAddress).child[0]
            }
            r = (TreeChunk.typeLayout()).readNative((rp).rawAddress)
            if (!((rp).rawAddress == 0L)) {
                while (true) {
                    var cp = (TreeChunk.typeLayout()).readNative((rp).rawAddress).child[1]
                    if (((cp).rawAddress == 0L)) {
                        cp = (TreeChunk.typeLayout()).readNative((rp).rawAddress).child[0]
                    }
                    if (((cp).rawAddress == 0L)) {
                        break
                    }
                    rp = cp
                }
                r = (TreeChunk.typeLayout()).readNative((rp).rawAddress)
                (TreeChunk.typeLayout()).writeNative((rp).rawAddress, MutPtr(0L))
            }
        }
        if (((xp).rawAddress == 0L)) {
            return
        }
        val h = this.treebinAt((TreeChunk.typeLayout()).readNative((chunk).rawAddress).index)
        if (((chunk).rawAddress.toULong() == (h).rawAddress.toULong())) {
            h = r
            if (r.isNull()) {
                this.clearTreemap((TreeChunk.typeLayout()).readNative((chunk).rawAddress).index)
            }
        } else {
            if ((((TreeChunk.typeLayout()).readNative((xp).rawAddress).child[0]).rawAddress.toULong() == (chunk).rawAddress.toULong())) {
                (TreeChunk.typeLayout()).readNative((xp).rawAddress).child[0] = r
            } else {
                (TreeChunk.typeLayout()).readNative((xp).rawAddress).child[1] = r
            }
        }
        if (!r.isNull()) {
            r.parent = xp
            val c0 = (TreeChunk.typeLayout()).readNative((chunk).rawAddress).child[0]
            if (!((c0).rawAddress == 0L)) {
                r.child[0] = c0
                (TreeChunk.typeLayout()).readNative((c0).rawAddress).parent = r
            }
            val c1 = (TreeChunk.typeLayout()).readNative((chunk).rawAddress).child[1]
            if (!((c1).rawAddress == 0L)) {
                r.child[1] = c1
                (TreeChunk.typeLayout()).readNative((c1).rawAddress).parent = r
            }
        }
    }

    public open fun validateSize(ptr: MutPtr, size: Int) {
        val p = Chunk.fromMem(ptr)
        val psize = Chunk.size(p)
        val minOverhead = this.overheadFor(p)
        check(psize >= size + minOverhead)
        if (!Chunk.mmapped(p)) {
            val maxOverhead = minOverhead + this.minChunkSize() * 2 + alignOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) - 1
            check(psize <= size + maxOverhead)
        }
    }

    public open fun free(mem: MutPtr) {
        this.checkMallocState()
        var p = Chunk.fromMem(mem)
        var psize = Chunk.size(p)
        val next = Chunk.plusOffset(p, psize)
        if (!Chunk.pinuse(p)) {
            val prevsize = (Chunk.typeLayout()).readNative((p).rawAddress).prevFoot
            if (Chunk.mmapped(p)) {
                psize += prevsize + this.mmapFootPad()
                if (this.systemAllocator.free(MutPtr((MutPtr((p).rawAddress)).rawAddress - ((prevsize).toLong() * (PrimitiveLayouts.U8).strideBytes)), psize)) {
                    this.footprint -= psize
                }
                return
            }
            val prev = Chunk.minusOffset(p, prevsize)
            psize += prevsize
            p = prev
            if (((p).rawAddress.toULong() != (this.dv).rawAddress.toULong())) {
                this.unlinkChunk(p, prevsize)
            } else if ((Chunk.typeLayout()).readNative((next).rawAddress).head and INUSE == INUSE) {
                this.dvsize = psize
                Chunk.setFreeWithPinuse(p, psize, next)
                return
            }
        }
        // Consolidate forward if we can
        if (!Chunk.cinuse(next)) {
            if (((next).rawAddress.toULong() == (this.top).rawAddress.toULong())) {
                this.topsize += psize
                val tsize = this.topsize
                this.top = p
                (Chunk.typeLayout()).readNative((p).rawAddress).head = tsize or PINUSE
                if (((p).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
                    this.dv = MutPtr(0L)
                    this.dvsize = 0
                }
                if (this.shouldTrim(tsize)) {
                    this.sysTrim(0)
                }
                return
            } else if (((next).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
                this.dvsize += psize
                val dsize = this.dvsize
                this.dv = p
                Chunk.setSizeAndPinuseOfFreeChunk(p, dsize)
                return
            } else {
                val nsize = Chunk.size(next)
                psize += nsize
                this.unlinkChunk(next, nsize)
                Chunk.setSizeAndPinuseOfFreeChunk(p, psize)
                if (((p).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
                    this.dvsize = psize
                    return
                }
            }
        } else {
            Chunk.setFreeWithPinuse(p, psize, next)
        }
        if (this.isSmall(psize)) {
            this.insertSmallChunk(p, psize)
            this.checkFreeChunk(p)
        } else {
            this.insertLargeChunk(p, psize)
            this.checkFreeChunk(p)
            this.releaseChecks -= 1
            if (this.releaseChecks == 0) {
                this.releaseUnusedSegments()
            }
        }
    }

    public open fun shouldTrim(size: Int): Boolean = size > this.trimCheck

    public open fun sysTrim(pad: Int): Boolean {
        var pad1 = pad
        var released = 0
        if (pad1 < this.maxRequest() && !((this.top).rawAddress == 0L)) {
            pad1 += this.topFootSize()
            if (this.topsize > pad1) {
                val unit = DEFAULT_GRANULARITY
                val extra = ((this.topsize - pad1 + unit - 1) / unit - 1) * unit
                val sp = this.segmentHolding(this.top)
                if (false) {
                    check(!sp.isNull())
                }
                if (!Segment.isExtern(sp)) {
                    if (Segment.canReleasePart(this.systemAllocator, sp)) {
                        if (sp.size >= extra && !this.hasSegmentLink(sp)) {
                            val newsize = sp.size - extra
                            if (this.systemAllocator.freePart(sp.base, sp.size, newsize)) {
                                released = extra
                            }
                        }
                    }
                }
                if (released != 0) {
                    sp.size -= released
                    this.footprint -= released
                    val top = this.top
                    val topsize = this.topsize - released
                    this.initTop(top, topsize)
                    this.checkTopChunk(this.top)
                }
            }
            released += this.releaseUnusedSegments()
            if (released == 0 && this.topsize > this.trimCheck) {
                this.trimCheck = Int.maxValue()
            }
        }
        return released != 0
    }

    public open fun hasSegmentLink(ptr: MutPtr): Boolean {
        var sp = (constPtrFromRustCast(this.seg)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        while (!((sp).rawAddress == 0L)) {
            if (Segment.holds(ptr, sp)) {
                return true
            }
            sp = (Segment.typeLayout()).readNative((sp).rawAddress).next
        }
        return false
    }

    /**
     * Unmap and unlink any mapped segments that don't contain used chunks
     */
    public open fun releaseUnusedSegments(): Int {
        var released = 0
        var nsegs = 0
        var pred: MutPtr = this.seg
        var sp = (Segment.typeLayout()).readNative((pred).rawAddress).next
        while (!((sp).rawAddress == 0L)) {
            val base = (Segment.typeLayout()).readNative((sp).rawAddress).base
            val size = (Segment.typeLayout()).readNative((sp).rawAddress).size
            val next = (Segment.typeLayout()).readNative((sp).rawAddress).next
            nsegs += 1
            if (Segment.canReleasePart(this.systemAllocator, sp) && !Segment.isExtern(sp)) {
                val p = this.alignAsChunk(base)
                val psize = Chunk.size(p)
                // We can unmap if the first chunk holds the entire segment and
                // isn't pinned.
                val chunkTop = p.cast().wrappingAdd(psize)
                val top = MutPtr((base).rawAddress + ((size - this.topFootSize()).toLong() * (PrimitiveLayouts.U8).strideBytes))
                if (!Chunk.inuse(p) && ((chunkTop).rawAddress.toULong() >= (top).rawAddress.toULong())) {
                    val tp = p.cast()
                    if (false) {
                        check(Segment.holds(sp, sp))
                    }
                    if (((p).rawAddress.toULong() == (this.dv).rawAddress.toULong())) {
                        this.dv = MutPtr(0L)
                        this.dvsize = 0
                    } else {
                        this.unlinkLargeChunk(tp)
                    }
                    if (this.systemAllocator.free(base, size)) {
                        released += size
                        this.footprint -= size
                        // unlink our obsolete record
                        sp = pred
                        (Segment.typeLayout()).readNative((sp).rawAddress).next = next
                    } else {
                        // back out if we can't unmap
                        this.insertLargeChunk(tp, psize)
                    }
                }
            }
            pred = sp
            sp = next
        }
        this.releaseChecks = if (nsegs > MAX_RELEASE_CHECK_RATE) {
            nsegs
        } else {
            MAX_RELEASE_CHECK_RATE
        }
        return released
    }

    // Sanity checks
    public open fun checkAnyChunk(p: MutPtr) {
        if (!false) {
            return
        }
        if (false) {
            check(this.isAligned(Chunk.toMem(p).rawAddress.toInt()) || (Chunk.typeLayout()).readNative((p).rawAddress).head == Chunk.fencepostHead())
        }
        if (false) {
            check(((MutPtr(p.rawAddress, p.rawMeta)).rawAddress.toULong() >= (this.leastAddr).rawAddress.toULong()))
        }
    }

    public open fun checkTopChunk(p: MutPtr) {
        if (!false) {
            return
        }
        val sp = this.segmentHolding(p)
        val sz = (Chunk.typeLayout()).readNative((p).rawAddress).head and INUSE.inv()
        if (false) {
            check(!sp.isNull())
        }
        if (false) {
            check(this.isAligned(Chunk.toMem(p).rawAddress.toInt()) || (Chunk.typeLayout()).readNative((p).rawAddress).head == Chunk.fencepostHead())
        }
        if (false) {
            check(((MutPtr(p.rawAddress, p.rawMeta)).rawAddress.toULong() >= (this.leastAddr).rawAddress.toULong()))
        }
        if (false) {
            check(sz == this.topsize)
        }
        if (false) {
            check(sz > 0)
        }
        if (false) {
            check(sz == sp.base.toInt() + sp.size - p.rawAddress.toInt() - this.topFootSize())
        }
        if (false) {
            check(Chunk.pinuse(p))
        }
        if (false) {
            check(!Chunk.pinuse(Chunk.plusOffset(p, sz)))
        }
    }

    public open fun checkMallocedChunk(mem: MutPtr, s: Int) {
        if (!false) {
            return
        }
        if (((mem).rawAddress == 0L)) {
            return
        }
        val p = Chunk.fromMem(mem)
        val sz = (Chunk.typeLayout()).readNative((p).rawAddress).head and INUSE.inv()
        this.checkInuseChunk(p)
        if (false) {
            check(alignUp(sz, this.mallocAlignment()) == sz)
        }
        if (false) {
            check(sz >= this.minChunkSize())
        }
        if (false) {
            check(sz >= s)
        }
        if (false) {
            check(Chunk.mmapped(p) || sz < (s + this.minChunkSize()))
        }
    }

    public open fun checkInuseChunk(p: MutPtr) {
        this.checkAnyChunk(p)
        if (false) {
            check(Chunk.inuse(p))
        }
        if (false) {
            check(Chunk.pinuse(Chunk.next(p)))
        }
        if (false) {
            check(Chunk.mmapped(p) || Chunk.pinuse(p) || ((Chunk.next(Chunk.prev(p))).rawAddress.toULong() == (p).rawAddress.toULong()))
        }
        if (Chunk.mmapped(p)) {
            this.checkMmappedChunk(p)
        }
    }

    public open fun checkMmappedChunk(p: MutPtr) {
        if (!false) {
            return
        }
        val sz = Chunk.size(p)
        val len = sz + (Chunk.typeLayout()).readNative((p).rawAddress).prevFoot + this.mmapFootPad()
        if (false) {
            check(Chunk.mmapped(p))
        }
        if (false) {
            check(this.isAligned(Chunk.toMem(p).rawAddress.toInt()) || (Chunk.typeLayout()).readNative((p).rawAddress).head == Chunk.fencepostHead())
        }
        if (false) {
            check(((MutPtr(p.rawAddress, p.rawMeta)).rawAddress.toULong() >= (this.leastAddr).rawAddress.toULong()))
        }
        if (false) {
            check(!this.isSmall(sz))
        }
        if (false) {
            check(alignUp(len, this.systemAllocator.pageSize()) == len)
        }
        if (false) {
            check((Chunk.typeLayout()).readNative((Chunk.plusOffset(p, sz)).rawAddress).head == Chunk.fencepostHead())
        }
        if (false) {
            check((Chunk.typeLayout()).readNative((Chunk.plusOffset(p, sz + sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))).rawAddress).head == 0)
        }
    }

    public open fun checkFreeChunk(p: MutPtr) {
        if (!false) {
            return
        }
        val sz = Chunk.size(p)
        val next = Chunk.plusOffset(p, sz)
        this.checkAnyChunk(p)
        if (false) {
            check(!Chunk.inuse(p))
        }
        if (false) {
            check(!Chunk.pinuse(Chunk.next(p)))
        }
        if (false) {
            check(!Chunk.mmapped(p))
        }
        if (((p).rawAddress.toULong() != (this.dv).rawAddress.toULong()) && ((p).rawAddress.toULong() != (this.top).rawAddress.toULong())) {
            if (sz >= this.minChunkSize()) {
                if (false) {
                    check(alignUp(sz, this.mallocAlignment()) == sz)
                }
                if (false) {
                    check(this.isAligned(Chunk.toMem(p).rawAddress.toInt()))
                }
                if (false) {
                    check((Chunk.typeLayout()).readNative((next).rawAddress).prevFoot == sz)
                }
                if (false) {
                    check(Chunk.pinuse(p))
                }
                if (false) {
                    check(((next).rawAddress.toULong() == (this.top).rawAddress.toULong()) || Chunk.inuse(next))
                }
                if (false) {
                    check((Chunk.typeLayout()).readNative(((Chunk.typeLayout()).readNative((p).rawAddress).next).rawAddress).prev == p)
                }
                if (false) {
                    check((Chunk.typeLayout()).readNative(((Chunk.typeLayout()).readNative((p).rawAddress).prev).rawAddress).next == p)
                }
            } else {
                if (false) {
                    check(sz == sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))
                }
            }
        }
    }

    public open fun checkMallocState() {
        if (!false) {
            return
        }
        for (i in 0 until NSMALLBINS_U32) {
            this.checkSmallbin(i)
        }
        for (i1 in 0 until NTREEBINS_U32) {
            this.checkTreebin(i1)
        }
        if (this.dvsize != 0) {
            this.checkAnyChunk(this.dv)
            if (false) {
                check(this.dvsize == Chunk.size(this.dv))
            }
            if (false) {
                check(this.dvsize >= this.minChunkSize())
            }
            val dv = this.dv
            if (false) {
                check(!this.binFind(dv))
            }
        }
        if (!((this.top).rawAddress == 0L)) {
            this.checkTopChunk(this.top)
            if (false) {
                check(this.topsize > 0)
            }
            val top = this.top
            if (false) {
                check(!this.binFind(top))
            }
        }
        val total = this.traverseAndCheck()
        if (false) {
            check(total <= this.footprint)
        }
        if (false) {
            check(this.footprint <= this.maxFootprint)
        }
    }

    public open fun checkSmallbin(idx: UInt) {
        if (!false) {
            return
        }
        val b = this.smallbinAt(idx)
        var p = b.next
        val empty = this.smallmap and (1u shl idx.toInt()) == 0u
        if (p == b) {
            if (false) {
                check(empty)
            }
        }
        if (!empty) {
            while (p != b) {
                val size = Chunk.size(p)
                this.checkFreeChunk(p)
                if (false) {
                    check(this.smallIndex(size) == idx)
                }
                if (false) {
                    check(p.next == b || Chunk.size(p.next) == Chunk.size(p))
                }
                val q = Chunk.next(p)
                if ((Chunk.typeLayout()).readNative((q).rawAddress).head != Chunk.fencepostHead()) {
                    this.checkInuseChunk(q)
                }
                p = p.next
            }
        }
    }

    public open fun checkTreebin(idx: UInt) {
        if (!false) {
            return
        }
        val t = this.treebinAt(idx)
        val empty = this.treemap and (1u shl idx.toInt()) == 0u
        if (t.isNull()) {
            if (false) {
                check(empty)
            }
        }
        if (!empty) {
            this.checkTree(t)
        }
    }

    public open fun checkTree(t: MutPtr) {
        if (!false) {
            return
        }
        val tc = TreeChunk.Chunk(t)
        val tindex = (TreeChunk.typeLayout()).readNative((t).rawAddress).index
        val tsize = Chunk.size(tc)
        val idx = this.computeTreeIndex(tsize)
        if (false) {
            check(tindex == idx)
        }
        if (false) {
            check(tsize >= this.minLargeSize())
        }
        if (false) {
            check(tsize >= this.minSizeForTreeIndex(idx))
        }
        if (false) {
            check(idx == NTREEBINS_U32 - 1u || tsize < this.minSizeForTreeIndex(idx + 1))
        }
        var u = t
        var head = MutPtr(0L)
        while (true) {
            val uc = TreeChunk.Chunk(u)
            this.checkAnyChunk(uc)
            if (false) {
                check((TreeChunk.typeLayout()).readNative((u).rawAddress).index == tindex)
            }
            if (false) {
                check(Chunk.size(uc) == tsize)
            }
            if (false) {
                check(!Chunk.inuse(uc))
            }
            if (false) {
                check(!Chunk.pinuse(Chunk.next(uc)))
            }
            if (false) {
                check((Chunk.typeLayout()).readNative(((Chunk.typeLayout()).readNative((uc).rawAddress).next).rawAddress).prev == uc)
            }
            if (false) {
                check((Chunk.typeLayout()).readNative(((Chunk.typeLayout()).readNative((uc).rawAddress).prev).rawAddress).next == uc)
            }
            val left = (TreeChunk.typeLayout()).readNative((u).rawAddress).child[0]
            val right = (TreeChunk.typeLayout()).readNative((u).rawAddress).child[1]
            if ((((TreeChunk.typeLayout()).readNative((u).rawAddress).parent).rawAddress == 0L)) {
                if (false) {
                    check(((left).rawAddress == 0L))
                }
                if (false) {
                    check(((right).rawAddress == 0L))
                }
            } else {
                if (false) {
                    check(head.isNull())
                }
                head = u
                if (false) {
                    check((((TreeChunk.typeLayout()).readNative((u).rawAddress).parent).rawAddress.toULong() != (u).rawAddress.toULong()))
                }
                if (false) {
                    check((((TreeChunk.typeLayout()).readNative(((TreeChunk.typeLayout()).readNative((u).rawAddress).parent).rawAddress).child[0]).rawAddress.toULong() == (u).rawAddress.toULong()) || (((TreeChunk.typeLayout()).readNative(((TreeChunk.typeLayout()).readNative((u).rawAddress).parent).rawAddress).child[1]).rawAddress.toULong() == (u).rawAddress.toULong()) || (((PrimitiveLayouts.mutPtrLayout(64L)).readNative(((((TreeChunk.typeLayout()).readNative((u).rawAddress).parent).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)).rawAddress.toULong() == (u).rawAddress.toULong()))
                }
                if (!((left).rawAddress == 0L)) {
                    if (false) {
                        check((TreeChunk.typeLayout()).readNative((left).rawAddress).parent == u)
                    }
                    if (false) {
                        check(((left).rawAddress.toULong() != (u).rawAddress.toULong()))
                    }
                    this.checkTree(left)
                }
                if (!((right).rawAddress == 0L)) {
                    if (false) {
                        check((TreeChunk.typeLayout()).readNative((right).rawAddress).parent == u)
                    }
                    if (false) {
                        check(((right).rawAddress.toULong() != (u).rawAddress.toULong()))
                    }
                    this.checkTree(right)
                }
                if (!((left).rawAddress == 0L) && !((right).rawAddress == 0L)) {
                    if (false) {
                        check(Chunk.size(TreeChunk.Chunk(left)) < Chunk.size(TreeChunk.Chunk(right)))
                    }
                }
            }
            u = TreeChunk.prev(u)
            if (((u).rawAddress.toULong() == (t).rawAddress.toULong())) {
                break
            }
        }
        if (false) {
            check(!head.isNull())
        }
    }

    public open fun minSizeForTreeIndex(idx: UInt): Int {
        val idx1 = Int.tryFrom(idx).unwrap()
        return (1 shl ((idx1 shr 1) + TREEBIN_SHIFT)) or ((idx1 and 1) shl ((idx1 shr 1) + TREEBIN_SHIFT - 1))
    }

    public open fun binFind(chunk: MutPtr): Boolean {
        val size = Chunk.size(chunk)
        return if (this.isSmall(size)) {
            val sidx = this.smallIndex(size)
            val b = this.smallbinAt(sidx)
            if (!this.smallmapIsMarked(sidx)) {
                return false
            }
            var p = b
            while (true) {
                if (((p).rawAddress.toULong() == (chunk).rawAddress.toULong())) {
                    return true
                }
                p = p.prev
                if (p == b) {
                    return false
                }
            }
        } else {
            val tidx = this.computeTreeIndex(size)
            if (!this.treemapIsMarked(tidx)) {
                return false
            }
            var t = this.treebinAt(tidx)
            var sizebits = size shl leftshiftForTreeIndex(tidx).toInt()
            while (!t.isNull() && Chunk.size(TreeChunk.chunk(t)) != size) {
                t = t.child[(sizebits shr (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) * 8 - 1).toInt()) and 1]
                sizebits = sizebits shl 1
            }
            if (t.isNull()) {
                return false
            }
            var u = t
            val chunk1 = chunk
            while (true) {
                if (u == chunk1) {
                    return true
                }
                u = TreeChunk.prev(u)
                if (u == t) {
                    return false
                }
            }
        }
    }

    public open fun traverseAndCheck(): Int = 0

    public open fun trim(pad: Int): Boolean = this.sysTrim(pad)

    public open fun destroy(): Int {
        var freed = 0
        var sp: MutPtr = this.seg
        while (!((sp).rawAddress == 0L)) {
            val base = (Segment.typeLayout()).readNative((sp).rawAddress).base
            val size = (Segment.typeLayout()).readNative((sp).rawAddress).size
            val canFree = !((base).rawAddress == 0L) && !Segment.isExtern(sp)
            sp = (Segment.typeLayout()).readNative((sp).rawAddress).next
            if (canFree && this.systemAllocator.free(base, size)) {
                freed += size
            }
        }
        return freed
    }

    public companion object {
        public fun <A> typeLayout(typeLayoutA: TypeLayout<A>): TypeLayout<Dlmalloc<A>> =
            object : TypeLayout<Dlmalloc<A>> {
                override val alignBytes: Long = listOf(8L, typeLayoutA.alignBytes).max()
                private val off3: Long = alignUpBytes((8L + (((NSMALLBINS + 1) * 2).toLong() * (PrimitiveLayouts.mutPtrLayout(64)).strideBytes)), 8L)
                private val off4: Long = alignUpBytes((off3 + ((NTREEBINS).toLong() * (PrimitiveLayouts.mutPtrLayout(64)).strideBytes)), 8L)
                private val off5: Long = alignUpBytes((off4 + 8L), 8L)
                private val off6: Long = alignUpBytes((off5 + 8L), 8L)
                private val off7: Long = alignUpBytes((off6 + 8L), 8L)
                private val off8: Long = alignUpBytes((off7 + 8L), 8L)
                private val off9: Long = alignUpBytes((off8 + 8L), 8L)
                private val off10: Long = alignUpBytes((off9 + 8L), 8L)
                private val off11: Long = alignUpBytes((off10 + 32L), 8L)
                private val off12: Long = alignUpBytes((off11 + 8L), 8L)
                private val off13: Long = alignUpBytes((off12 + 8L), 8L)
                private val off14: Long = alignUpBytes((off13 + 8L), typeLayoutA.alignBytes)
                override val sizeBytes: Long = alignUpBytes((off14 + typeLayoutA.sizeBytes), alignBytes)
                override fun readNative(addr: Long): Dlmalloc<A> =
                    Dlmalloc(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), ((NSMALLBINS + 1) * 2).toInt())).readNative(addr + 8L),
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), (NTREEBINS).toInt())).readNative(addr + off3),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off4),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off5),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + off6),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + off7),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off8),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off9),
                        (Segment.typeLayout()).readNative(addr + off10),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off11),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + off12),
                        (PrimitiveLayouts.USIZE64).readNative(addr + off13),
                        (typeLayoutA).readNative(addr + off14),
                    )
                override fun writeNative(addr: Long, value: Dlmalloc<A>) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.smallmap)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.treemap)
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), ((NSMALLBINS + 1) * 2).toInt())).writeNative(addr + 8L, value.smallbins)
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), (NTREEBINS).toInt())).writeNative(addr + off3, value.treebins)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off4, value.dvsize)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off5, value.topsize)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + off6, value.dv)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + off7, value.top)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off8, value.footprint)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off9, value.maxFootprint)
                    (Segment.typeLayout()).writeNative(addr + off10, value.seg)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off11, value.trimCheck)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + off12, value.leastAddr)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + off13, value.releaseChecks)
                    (typeLayoutA).writeNative(addr + off14, value.systemAllocator)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dlmalloc<A> =
                    Dlmalloc(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), ((NSMALLBINS + 1) * 2).toInt())).readBytes(buf, offset + 8),
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), (NTREEBINS).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off4.toInt()),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off5.toInt()),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + off6.toInt()),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + off7.toInt()),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off8.toInt()),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off9.toInt()),
                        (Segment.typeLayout()).readBytes(buf, offset + off10.toInt()),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off11.toInt()),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + off12.toInt()),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + off13.toInt()),
                        (typeLayoutA).readBytes(buf, offset + off14.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dlmalloc<A>) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.smallmap)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.treemap)
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), ((NSMALLBINS + 1) * 2).toInt())).writeBytes(buf, offset + 8, value.smallbins)
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), (NTREEBINS).toInt())).writeBytes(buf, offset + off3.toInt(), value.treebins)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off4.toInt(), value.dvsize)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off5.toInt(), value.topsize)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + off6.toInt(), value.dv)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + off7.toInt(), value.top)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off8.toInt(), value.footprint)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off9.toInt(), value.maxFootprint)
                    (Segment.typeLayout()).writeBytes(buf, offset + off10.toInt(), value.seg)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off11.toInt(), value.trimCheck)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + off12.toInt(), value.leastAddr)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + off13.toInt(), value.releaseChecks)
                    (typeLayoutA).writeBytes(buf, offset + off14.toInt(), value.systemAllocator)
                }
            }

        public operator fun <A> invoke(
            smallmap: UInt,
            treemap: UInt,
            smallbins: List<MutPtr>,
            treebins: List<MutPtr>,
            dvsize: Int,
            topsize: Int,
            dv: MutPtr,
            top: MutPtr,
            footprint: Int,
            maxFootprint: Int,
            seg: Segment,
            trimCheck: Int,
            leastAddr: MutPtr,
            releaseChecks: Int,
            systemAllocator: A,
        ): Dlmalloc<A> = Dlmalloc<A>(
            smallmap = smallmap,
            treemap = treemap,
            smallbinsCtorArg = smallbins,
            treebinsCtorArg = treebins,
            dvsize = dvsize,
            topsize = topsize,
            dvCtorArg = dv,
            topCtorArg = top,
            footprint = footprint,
            maxFootprint = maxFootprint,
            seg = seg,
            trimCheck = trimCheck,
            leastAddrCtorArg = leastAddr,
            releaseChecks = releaseChecks,
            systemAllocator = systemAllocator,
        )

        public fun <A> new(systemAllocator: A): Dlmalloc<A> = Dlmalloc(smallmap = 0u, treemap = 0u, smallbins = List((NSMALLBINS + 1) * 2) { MutPtr(0L) }, treebins = List(NTREEBINS) { MutPtr(0L) }, dvsize = 0, topsize = 0, dv = MutPtr(0L), top = MutPtr(0L), footprint = 0, maxFootprint = 0, seg = Segment(base = MutPtr(0L), size = 0, next = MutPtr(0L), flags = 0u), trimCheck = 0, leastAddr = MutPtr(0L), releaseChecks = 0, systemAllocator = systemAllocator)
    }
}

// Note: document this
public val NSMALLBINS: Int = 32

public val NTREEBINS: Int = 32

public val SMALLBIN_SHIFT: Int = 3

public val TREEBIN_SHIFT: Int = 8

public val NSMALLBINS_U32: UInt = NSMALLBINS.toUInt()

public val NTREEBINS_U32: UInt = NTREEBINS.toUInt()

// Note: runtime configurable? documentation?
public val DEFAULT_GRANULARITY: Int = 64 * 1024

public val DEFAULT_TRIM_THRESHOLD: Int = 2 * 1024 * 1024

public val MAX_RELEASE_CHECK_RATE: Int = 4095

public open class Chunk internal constructor(
    public var prevFoot: Int,
    public var head: Int,
    prevCtorArg: MutPtr,
    nextCtorArg: MutPtr,
) {
    public var prev: MutPtr = prevCtorArg
        internal set

    public var next: MutPtr = nextCtorArg
        internal set

    public companion object {
        public fun typeLayout(): TypeLayout<Chunk> =
            object : TypeLayout<Chunk> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Chunk =
                    Chunk(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Chunk) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.prevFoot)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.head)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.prev)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.next)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Chunk =
                    Chunk(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Chunk) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.prevFoot)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.head)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.prev)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.next)
                }
            }

        public operator fun invoke(
            prevFoot: Int,
            head: Int,
            prev: MutPtr,
            next: MutPtr,
        ): Chunk = Chunk(
            prevFoot = prevFoot,
            head = head,
            prevCtorArg = prev,
            nextCtorArg = next,
        )

        public fun fencepostHead(): Int = INUSE or sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

        public fun size(me: MutPtr): Int = (Chunk.typeLayout()).readNative((me).rawAddress).head and FLAG_BITS.inv()

        public fun next(me: MutPtr): MutPtr = MutPtr((MutPtr((MutPtr((me).rawAddress)).rawAddress + (((Chunk.typeLayout()).readNative((me).rawAddress).head and FLAG_BITS.inv()).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)

        public fun prev(me: MutPtr): MutPtr = MutPtr((MutPtr((MutPtr((me).rawAddress)).rawAddress - (((Chunk.typeLayout()).readNative((me).rawAddress).prevFoot).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)

        public fun cinuse(me: MutPtr): Boolean = (Chunk.typeLayout()).readNative((me).rawAddress).head and CINUSE != 0

        public fun pinuse(me: MutPtr): Boolean = (Chunk.typeLayout()).readNative((me).rawAddress).head and PINUSE != 0

        public fun clearPinuse(me: MutPtr) {
            (Chunk.typeLayout()).readNative((me).rawAddress).head = (Chunk.typeLayout()).readNative((me).rawAddress).head and PINUSE.inv()
        }

        public fun inuse(me: MutPtr): Boolean = (Chunk.typeLayout()).readNative((me).rawAddress).head and INUSE != PINUSE

        public fun mmapped(me: MutPtr): Boolean = (Chunk.typeLayout()).readNative((me).rawAddress).head and INUSE == 0

        public fun setInuse(me: MutPtr, size: Int) {
            (Chunk.typeLayout()).readNative((me).rawAddress).head = ((Chunk.typeLayout()).readNative((me).rawAddress).head and PINUSE) or size or CINUSE
            val next = Chunk.plusOffset(me, size)
            (Chunk.typeLayout()).readNative((next).rawAddress).head = (Chunk.typeLayout()).readNative((next).rawAddress).head or PINUSE
        }

        public fun setInuseAndPinuse(me: MutPtr, size: Int) {
            (Chunk.typeLayout()).readNative((me).rawAddress).head = PINUSE or size or CINUSE
            val next = Chunk.plusOffset(me, size)
            (Chunk.typeLayout()).readNative((next).rawAddress).head = (Chunk.typeLayout()).readNative((next).rawAddress).head or PINUSE
        }

        public fun setSizeAndPinuseOfInuseChunk(me: MutPtr, size: Int) {
            (Chunk.typeLayout()).readNative((me).rawAddress).head = size or PINUSE or CINUSE
        }

        public fun setSizeAndPinuseOfFreeChunk(me: MutPtr, size: Int) {
            (Chunk.typeLayout()).readNative((me).rawAddress).head = size or PINUSE
            Chunk.setFoot(me, size)
        }

        public fun setFreeWithPinuse(p: MutPtr, size: Int, n: MutPtr) {
            Chunk.clearPinuse(n)
            Chunk.setSizeAndPinuseOfFreeChunk(p, size)
        }

        public fun setFoot(me: MutPtr, size: Int) {
            val next = Chunk.plusOffset(me, size)
            (Chunk.typeLayout()).readNative((next).rawAddress).prevFoot = size
        }

        public fun plusOffset(me: MutPtr, offset: Int): MutPtr = MutPtr((MutPtr((MutPtr((me).rawAddress)).rawAddress + ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)

        public fun minusOffset(me: MutPtr, offset: Int): MutPtr = MutPtr((MutPtr((MutPtr((me).rawAddress)).rawAddress - ((offset).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)

        public fun toMem(me: MutPtr): MutPtr = MutPtr((MutPtr((me).rawAddress)).rawAddress + ((Chunk.memOffset()).toLong() * (PrimitiveLayouts.U8).strideBytes))

        public fun memOffset(): Int = 2 * sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

        public fun fromMem(mem: MutPtr): MutPtr = MutPtr((MutPtr((mem).rawAddress - ((2 * sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)
    }
}

public open class TreeChunk internal constructor(
    public var chunk: Chunk,
    childCtorArg: List<MutPtr>,
    parentCtorArg: MutPtr,
    public var index: UInt,
) {
    public var child: List<MutPtr> = childCtorArg
        internal set

    public var parent: MutPtr = parentCtorArg
        internal set

    public companion object {
        public fun typeLayout(): TypeLayout<TreeChunk> =
            object : TypeLayout<TreeChunk> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): TreeChunk =
                    TreeChunk(
                        (Chunk.typeLayout()).readNative(addr),
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 2)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.U32).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: TreeChunk) {
                    (Chunk.typeLayout()).writeNative(addr, value.chunk)
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 2)).writeNative(addr + 32L, value.child)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.parent)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.index)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TreeChunk =
                    TreeChunk(
                        (Chunk.typeLayout()).readBytes(buf, offset),
                        (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 2)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TreeChunk) {
                    (Chunk.typeLayout()).writeBytes(buf, offset, value.chunk)
                    (fixedListTypeLayout(PrimitiveLayouts.mutPtrLayout(64), 2)).writeBytes(buf, offset + 32, value.child)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.parent)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.index)
                }
            }

        public operator fun invoke(
            chunk: Chunk,
            child: List<MutPtr>,
            parent: MutPtr,
            index: UInt,
        ): TreeChunk = TreeChunk(
            chunk = chunk,
            childCtorArg = child,
            parentCtorArg = parent,
            index = index,
        )

        public fun leftmostChild(me: MutPtr): MutPtr {
            val left = (TreeChunk.typeLayout()).readNative((me).rawAddress).child[0]
            return if (((left).rawAddress == 0L)) {
                (TreeChunk.typeLayout()).readNative((me).rawAddress).child[1]
            } else {
                left
            }
        }

        public fun chunk(me: MutPtr): MutPtr = addrOfMut((TreeChunk.typeLayout()).readNative((me).rawAddress).chunk)

        public fun next(me: MutPtr): MutPtr = MutPtr(((Chunk.typeLayout()).readNative((TreeChunk.Chunk(me)).rawAddress).next).rawAddress)

        public fun prev(me: MutPtr): MutPtr = MutPtr(((Chunk.typeLayout()).readNative((TreeChunk.Chunk(me)).rawAddress).prev).rawAddress)
    }
}

public open class Segment internal constructor(
    baseCtorArg: MutPtr,
    public var size: Int,
    nextCtorArg: MutPtr,
    public var flags: UInt,
) {
    public var base: MutPtr = baseCtorArg
        internal set

    public var next: MutPtr = nextCtorArg
        internal set

    public open fun clone(): Segment = Segment(base, size, next, flags)

    public companion object {
        public fun typeLayout(): TypeLayout<Segment> =
            object : TypeLayout<Segment> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Segment =
                    Segment(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Segment) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.base)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.size)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.next)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Segment =
                    Segment(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Segment) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.base)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.size)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.next)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.flags)
                }
            }

        public operator fun invoke(
            base: MutPtr,
            size: Int,
            next: MutPtr,
            flags: UInt,
        ): Segment = Segment(
            baseCtorArg = base,
            size = size,
            nextCtorArg = next,
            flags = flags,
        )

        public fun isExtern(seg: MutPtr): Boolean = (Segment.typeLayout()).readNative((seg).rawAddress).flags and EXTERN != 0u

        public fun <A : Allocator> canReleasePart(systemAllocator: A, seg: MutPtr): Boolean = systemAllocator.canReleasePart((Segment.typeLayout()).readNative((seg).rawAddress).flags shr 1)

        public fun sysFlags(seg: MutPtr): UInt = (Segment.typeLayout()).readNative((seg).rawAddress).flags shr 1

        public fun holds(seg: MutPtr, addr: MutPtr): Boolean = (((Segment.typeLayout()).readNative((seg).rawAddress).base).rawAddress.toULong() <= (addr).rawAddress.toULong()) && ((addr).rawAddress.toULong() < (Segment.top(seg)).rawAddress.toULong())

        public fun top(seg: MutPtr): MutPtr = MutPtr(((Segment.typeLayout()).readNative((seg).rawAddress).base).rawAddress + (((Segment.typeLayout()).readNative((seg).rawAddress).size).toLong() * (PrimitiveLayouts.U8).strideBytes))
    }
}

public fun alignUp(a: Int, alignment: Int): Int {
    if (false) {
        check(alignment.isPowerOfTwo())
    }
    return (a + (alignment - 1)) and (alignment - 1).inv()
}

public fun leftBits(x: UInt): UInt = (x shl 1) or (((x shl 1).inv()) + 1u)

public fun leastBit(x: UInt): UInt = x and (!x + 1u)

public fun leftshiftForTreeIndex(x: UInt): UInt {
    val x1 = Int.tryFrom(x).unwrap()
    return if (x1 == NTREEBINS - 1) {
        0u
    } else {
        (sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64) * 8 - 1 - ((x1 shr 1) + TREEBIN_SHIFT - 2)).toUInt()
    }
}

public val PINUSE: Int = 1 shl 0

public val CINUSE: Int = 1 shl 1

public val FLAG4: Int = 1 shl 2

public val INUSE: Int = PINUSE or CINUSE

public val FLAG_BITS: Int = PINUSE or CINUSE or FLAG4

public val EXTERN: UInt = 1u shl 0
