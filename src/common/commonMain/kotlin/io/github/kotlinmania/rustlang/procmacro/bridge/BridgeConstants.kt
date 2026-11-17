package io.github.kotlinmania.rustlang.procmacro.bridge

import io.github.kotlinmania.rustlang.procmacro.bridge.buffer.Buffer
import io.github.kotlinmania.rustlang.procmacro.bridge.server.emitDiagnostic
import io.github.kotlinmania.rustlang.procmacro.bridge.server.literalFromStr
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanByteRange
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanColumn
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanDebug
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanEnd
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanFile
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanJoin
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanLine
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanLocalFile
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanParent
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanRecoverProcMacroSpan
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanResolvedAt
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanSaveSpan
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanSource
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanSourceText
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanStart
import io.github.kotlinmania.rustlang.procmacro.bridge.server.spanSubspan
import io.github.kotlinmania.rustlang.procmacro.bridge.server.symbolNormalizeAndValidateIdent
import io.github.kotlinmania.rustlang.procmacro.bridge.server.trackEnvVar
import io.github.kotlinmania.rustlang.procmacro.bridge.server.trackPath
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsClone
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsConcatStreams
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsConcatTrees
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsDrop
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsExpandExpr
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsFromStr
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsFromTokenTree
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsIntoTrees
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsIsEmpty
import io.github.kotlinmania.rustlang.procmacro.bridge.server.tsToString

public sealed class Tag {
    public data object TrackEnvVar : Tag()
    public data object TrackPath : Tag()
    public data object LiteralFromStr : Tag()
    public data object EmitDiagnostic : Tag()
    public data object TsDrop : Tag()
    public data object TsClone : Tag()
    public data object TsIsEmpty : Tag()
    public data object TsExpandExpr : Tag()
    public data object TsFromStr : Tag()
    public data object TsToString : Tag()
    public data object TsFromTokenTree : Tag()
    public data object TsConcatTrees : Tag()
    public data object TsConcatStreams : Tag()
    public data object TsIntoTrees : Tag()
    public data object SpanDebug : Tag()
    public data object SpanParent : Tag()
    public data object SpanSource : Tag()
    public data object SpanByteRange : Tag()
    public data object SpanStart : Tag()
    public data object SpanEnd : Tag()
    public data object SpanLine : Tag()
    public data object SpanColumn : Tag()
    public data object SpanFile : Tag()
    public data object SpanLocalFile : Tag()
    public data object SpanJoin : Tag()
    public data object SpanSubspan : Tag()
    public data object SpanResolvedAt : Tag()
    public data object SpanSourceText : Tag()
    public data object SpanSaveSpan : Tag()
    public data object SpanRecoverProcMacroSpan : Tag()
    public data object SymbolNormalizeAndValidateIdent : Tag()

    private fun kmDiscriminant(): Int =
        when (this) {
        TrackEnvVar -> 0
        TrackPath -> 1
        LiteralFromStr -> 2
        EmitDiagnostic -> 3
        TsDrop -> 4
        TsClone -> 5
        TsIsEmpty -> 6
        TsExpandExpr -> 7
        TsFromStr -> 8
        TsToString -> 9
        TsFromTokenTree -> 10
        TsConcatTrees -> 11
        TsConcatStreams -> 12
        TsIntoTrees -> 13
        SpanDebug -> 14
        SpanParent -> 15
        SpanSource -> 16
        SpanByteRange -> 17
        SpanStart -> 18
        SpanEnd -> 19
        SpanLine -> 20
        SpanColumn -> 21
        SpanFile -> 22
        SpanLocalFile -> 23
        SpanJoin -> 24
        SpanSubspan -> 25
        SpanResolvedAt -> 26
        SpanSourceText -> 27
        SpanSaveSpan -> 28
        SpanRecoverProcMacroSpan -> 29
        SymbolNormalizeAndValidateIdent -> 30
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

public val TRACK_ENV_VAR: UByte = Tag.TrackEnvVar.toUByte()

public val TRACK_PATH: UByte = Tag.TrackPath.toUByte()

public val LITERAL_FROM_STR: UByte = Tag.LiteralFromStr.toUByte()

public val EMIT_DIAGNOSTIC: UByte = Tag.EmitDiagnostic.toUByte()

public val TS_DROP: UByte = Tag.TsDrop.toUByte()

public val TS_CLONE: UByte = Tag.TsClone.toUByte()

public val TS_IS_EMPTY: UByte = Tag.TsIsEmpty.toUByte()

public val TS_EXPAND_EXPR: UByte = Tag.TsExpandExpr.toUByte()

public val TS_FROM_STR: UByte = Tag.TsFromStr.toUByte()

public val TS_TO_STRING: UByte = Tag.TsToString.toUByte()

public val TS_FROM_TOKEN_TREE: UByte = Tag.TsFromTokenTree.toUByte()

public val TS_CONCAT_TREES: UByte = Tag.TsConcatTrees.toUByte()

public val TS_CONCAT_STREAMS: UByte = Tag.TsConcatStreams.toUByte()

public val TS_INTO_TREES: UByte = Tag.TsIntoTrees.toUByte()

public val SPAN_DEBUG: UByte = Tag.SpanDebug.toUByte()

public val SPAN_PARENT: UByte = Tag.SpanParent.toUByte()

public val SPAN_SOURCE: UByte = Tag.SpanSource.toUByte()

public val SPAN_BYTE_RANGE: UByte = Tag.SpanByteRange.toUByte()

public val SPAN_START: UByte = Tag.SpanStart.toUByte()

public val SPAN_END: UByte = Tag.SpanEnd.toUByte()

public val SPAN_LINE: UByte = Tag.SpanLine.toUByte()

public val SPAN_COLUMN: UByte = Tag.SpanColumn.toUByte()

public val SPAN_FILE: UByte = Tag.SpanFile.toUByte()

public val SPAN_LOCAL_FILE: UByte = Tag.SpanLocalFile.toUByte()

public val SPAN_JOIN: UByte = Tag.SpanJoin.toUByte()

public val SPAN_SUBSPAN: UByte = Tag.SpanSubspan.toUByte()

public val SPAN_RESOLVED_AT: UByte = Tag.SpanResolvedAt.toUByte()

public val SPAN_SOURCE_TEXT: UByte = Tag.SpanSourceText.toUByte()

public val SPAN_SAVE_SPAN: UByte = Tag.SpanSaveSpan.toUByte()

public val SPAN_RECOVER_PROC_MACRO_SPAN: UByte = Tag.SpanRecoverProcMacroSpan.toUByte()

public val SYMBOL_NORMALIZE_AND_VALIDATE_IDENT: UByte = Tag.SymbolNormalizeAndValidateIdent.toUByte()

public fun <S> ApiTags.encode(w: Buffer, s: S) {
    when (this) {
        ApiTags.TrackEnvVar -> {
            trackEnvVar.encode(w, s)
        }
        ApiTags.TrackPath -> {
            trackPath.encode(w, s)
        }
        ApiTags.LiteralFromStr -> {
            literalFromStr.encode(w, s)
        }
        ApiTags.EmitDiagnostic -> {
            emitDiagnostic.encode(w, s)
        }
        ApiTags.TsDrop -> {
            tsDrop.encode(w, s)
        }
        ApiTags.TsClone -> {
            tsClone.encode(w, s)
        }
        ApiTags.TsIsEmpty -> {
            tsIsEmpty.encode(w, s)
        }
        ApiTags.TsExpandExpr -> {
            tsExpandExpr.encode(w, s)
        }
        ApiTags.TsFromStr -> {
            tsFromStr.encode(w, s)
        }
        ApiTags.TsToString -> {
            tsToString.encode(w, s)
        }
        ApiTags.TsFromTokenTree -> {
            tsFromTokenTree.encode(w, s)
        }
        ApiTags.TsConcatTrees -> {
            tsConcatTrees.encode(w, s)
        }
        ApiTags.TsConcatStreams -> {
            tsConcatStreams.encode(w, s)
        }
        ApiTags.TsIntoTrees -> {
            tsIntoTrees.encode(w, s)
        }
        ApiTags.SpanDebug -> {
            spanDebug.encode(w, s)
        }
        ApiTags.SpanParent -> {
            spanParent.encode(w, s)
        }
        ApiTags.SpanSource -> {
            spanSource.encode(w, s)
        }
        ApiTags.SpanByteRange -> {
            spanByteRange.encode(w, s)
        }
        ApiTags.SpanStart -> {
            spanStart.encode(w, s)
        }
        ApiTags.SpanEnd -> {
            spanEnd.encode(w, s)
        }
        ApiTags.SpanLine -> {
            spanLine.encode(w, s)
        }
        ApiTags.SpanColumn -> {
            spanColumn.encode(w, s)
        }
        ApiTags.SpanFile -> {
            spanFile.encode(w, s)
        }
        ApiTags.SpanLocalFile -> {
            spanLocalFile.encode(w, s)
        }
        ApiTags.SpanJoin -> {
            spanJoin.encode(w, s)
        }
        ApiTags.SpanSubspan -> {
            spanSubspan.encode(w, s)
        }
        ApiTags.SpanResolvedAt -> {
            spanResolvedAt.encode(w, s)
        }
        ApiTags.SpanSourceText -> {
            spanSourceText.encode(w, s)
        }
        ApiTags.SpanSaveSpan -> {
            spanSaveSpan.encode(w, s)
        }
        ApiTags.SpanRecoverProcMacroSpan -> {
            spanRecoverProcMacroSpan.encode(w, s)
        }
        ApiTags.SymbolNormalizeAndValidateIdent -> {
            symbolNormalizeAndValidateIdent.encode(w, s)
        }
    }
}

public fun <S> decodeApiTags(r: List<UByte>, s: S): ApiTags =
    when (val matchVal = UByte.decode(r, s)) {
        else -> {
            val trackEnvVar = matchVal
            ApiTags.TrackEnvVar
        }
        else -> {
            val trackPath = matchVal
            ApiTags.TrackPath
        }
        else -> {
            val literalFromStr = matchVal
            ApiTags.LiteralFromStr
        }
        else -> {
            val emitDiagnostic = matchVal
            ApiTags.EmitDiagnostic
        }
        else -> {
            val tsDrop = matchVal
            ApiTags.TsDrop
        }
        else -> {
            val tsClone = matchVal
            ApiTags.TsClone
        }
        else -> {
            val tsIsEmpty = matchVal
            ApiTags.TsIsEmpty
        }
        else -> {
            val tsExpandExpr = matchVal
            ApiTags.TsExpandExpr
        }
        else -> {
            val tsFromStr = matchVal
            ApiTags.TsFromStr
        }
        else -> {
            val tsToString = matchVal
            ApiTags.TsToString
        }
        else -> {
            val tsFromTokenTree = matchVal
            ApiTags.TsFromTokenTree
        }
        else -> {
            val tsConcatTrees = matchVal
            ApiTags.TsConcatTrees
        }
        else -> {
            val tsConcatStreams = matchVal
            ApiTags.TsConcatStreams
        }
        else -> {
            val tsIntoTrees = matchVal
            ApiTags.TsIntoTrees
        }
        else -> {
            val spanDebug = matchVal
            ApiTags.SpanDebug
        }
        else -> {
            val spanParent = matchVal
            ApiTags.SpanParent
        }
        else -> {
            val spanSource = matchVal
            ApiTags.SpanSource
        }
        else -> {
            val spanByteRange = matchVal
            ApiTags.SpanByteRange
        }
        else -> {
            val spanStart = matchVal
            ApiTags.SpanStart
        }
        else -> {
            val spanEnd = matchVal
            ApiTags.SpanEnd
        }
        else -> {
            val spanLine = matchVal
            ApiTags.SpanLine
        }
        else -> {
            val spanColumn = matchVal
            ApiTags.SpanColumn
        }
        else -> {
            val spanFile = matchVal
            ApiTags.SpanFile
        }
        else -> {
            val spanLocalFile = matchVal
            ApiTags.SpanLocalFile
        }
        else -> {
            val spanJoin = matchVal
            ApiTags.SpanJoin
        }
        else -> {
            val spanSubspan = matchVal
            ApiTags.SpanSubspan
        }
        else -> {
            val spanResolvedAt = matchVal
            ApiTags.SpanResolvedAt
        }
        else -> {
            val spanSourceText = matchVal
            ApiTags.SpanSourceText
        }
        else -> {
            val spanSaveSpan = matchVal
            ApiTags.SpanSaveSpan
        }
        else -> {
            val spanRecoverProcMacroSpan = matchVal
            ApiTags.SpanRecoverProcMacroSpan
        }
        else -> {
            val symbolNormalizeAndValidateIdent = matchVal
            ApiTags.SymbolNormalizeAndValidateIdent
        }
        else -> error("unreachable")
    }
