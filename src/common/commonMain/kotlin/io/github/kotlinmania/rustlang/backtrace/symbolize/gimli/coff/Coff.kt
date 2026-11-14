// port-lint: source symbolize/gimli/coff.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.coff

import io.github.kotlinmania.addr2line.SplitDwarfLoad
import io.github.kotlinmania.obj.pe.ImageDosHeader
import io.github.kotlinmania.obj.pe.ImageSymbol
import io.github.kotlinmania.obj.read.StringTable
import io.github.kotlinmania.obj.read.pe.SectionTable
import io.github.kotlinmania.rustlang.backtrace.gimli.Dwarf
import io.github.kotlinmania.rustlang.backtrace.gimli.DwarfPackage
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Context
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Endian
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.EndianSlice
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.Mapping
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mmap
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.mystd.path.Path
import io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.stash.Stash
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.obj.littleEndian as LE

public typealias Pe = ImageNtHeaders64

public fun Mapping.Companion.new(path: Path): Mapping? {
    val map = (mmap(path) ?: return null)
    return Mapping.mk(map, closure@{ data, stash ->
        Context.new(stash, when (val tryResult = Object.parse(data)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }, null, null)
    })
}

public open class Object(
    public var data: List<UByte>,
    public var sections: SectionTable,
    public var symbols: List<Pair<Int, ImageSymbol>>,
    public var strings: StringTable,
) {
    public open fun section(arg0: Stash, name: String): List<UByte>? {
        return ((this.sections.sectionByName(this.strings, name.encodeToByteArray().map { it.toUByte() }) ?: return null).second.peData(this.data).ok() ?: return null)
    }

    public open fun searchSymtab(addr: ULong): List<UByte>? {
        // Note that unlike other formats COFF doesn't embed the size of
        // each symbol. As a last ditch effort search for the *closest*
        // symbol to a particular address and return that one. This gets
        // really wonky once symbols start getting removed because the
        // symbols returned here can be totally incorrect, but we have
        // no idea of knowing how to detect that.
        val addr1 = (Int.tryFrom(addr).ok() ?: return null)
        val i2 =
            run {
                val matchVal = this.symbols.binarySearchByKey(addr1, { p -> p.first })
                when (matchVal) { is Result.Ok<*> -> {
                    val i = matchVal.value
                    i
                }; is Result.Err<*> -> {
                    val i1 = matchVal.error
                    (i1.checkedSub(1) ?: return null)
                } }
            }
        return this.symbols[i2].second.name(this.strings).ok()
    }

    internal open fun searchObjectMap(addr: ULong): Pair<Context, ULong>? = null

    public companion object {
        public fun parse(data: List<UByte>): Object? {
            val dosHeader = (ImageDosHeader.parse(data).ok() ?: return null)
            val offset = dosHeader.ntHeadersOffset().into()
            val (ntHeaders, _) = (Pe.parse(data, offset).ok() ?: return null)
            val sections = (ntHeaders.sections(data, offset).ok() ?: return null)
            val symtab = (ntHeaders.symbols(data).ok() ?: return null)
            val strings = symtab.strings()
            val imageBase = (Int.tryFrom(ntHeaders.optionalHeader().imageBase()).ok() ?: return null)
            // Collect all the symbols into a local vector which is sorted
            // by address and contains enough data to learn about the symbol
            // name. Note that we only look at function symbols and also
            // note that the sections are 1-indexed because the zero section
            // is special (apparently).
            var symbols = mutableListOf<Pair<Int, ImageSymbol>>()
            for ((_, sym) in symtab.iter()) {
                if (sym.derivedType() != Object.pe.IMAGE_SYM_DTYPE_FUNCTION) {
                    continue
                }
                val sectionIndex = sym.section() ?: continue
                val addr = (Int.tryFrom(sym.value.get(LE)).ok() ?: return null)
                val section = (sections.section(sectionIndex).ok() ?: return null)
                val va = (Int.tryFrom(section.virtualAddress.get(LE)).ok() ?: return null)
                symbols.add(Pair(addr + va + imageBase, sym))
            }
            symbols.sortUnstableByKey({ x -> x.first })
            return Object(data = data, sections = sections, strings = strings, symbols = symbols)
        }
    }
}

public fun getImageBase(data: List<UByte>): Int? {
    val dosHeader = (ImageDosHeader.parse(data).ok() ?: return null)
    val offset = dosHeader.ntHeadersOffset().into()
    val (ntHeaders, _) = (Pe.parse(data, offset).ok() ?: return null)
    return Int.tryFrom(ntHeaders.optionalHeader().imageBase()).ok()
}

internal fun handleSplitDwarf(pkg: DwarfPackage<EndianSlice<Endian>>?, stash: Stash, load: SplitDwarfLoad<EndianSlice<Endian>>): Dwarf<EndianSlice<Endian>>? = null
