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
}

public val TRACK_ENV_VAR: UByte = (Tag::trackEnvVar).toUByte()

public val TRACK_PATH: UByte = (Tag::trackPath).toUByte()

public val LITERAL_FROM_STR: UByte = (Tag::literalFromStr).toUByte()

public val EMIT_DIAGNOSTIC: UByte = (Tag::emitDiagnostic).toUByte()

public val TS_DROP: UByte = (Tag::tsDrop).toUByte()

public val TS_CLONE: UByte = (Tag::tsClone).toUByte()

public val TS_IS_EMPTY: UByte = (Tag::tsIsEmpty).toUByte()

public val TS_EXPAND_EXPR: UByte = (Tag::tsExpandExpr).toUByte()

public val TS_FROM_STR: UByte = (Tag::tsFromStr).toUByte()

public val TS_TO_STRING: UByte = (Tag::tsToString).toUByte()

public val TS_FROM_TOKEN_TREE: UByte = (Tag::tsFromTokenTree).toUByte()

public val TS_CONCAT_TREES: UByte = (Tag::tsConcatTrees).toUByte()

public val TS_CONCAT_STREAMS: UByte = (Tag::tsConcatStreams).toUByte()

public val TS_INTO_TREES: UByte = (Tag::tsIntoTrees).toUByte()

public val SPAN_DEBUG: UByte = (Tag::spanDebug).toUByte()

public val SPAN_PARENT: UByte = (Tag::spanParent).toUByte()

public val SPAN_SOURCE: UByte = (Tag::spanSource).toUByte()

public val SPAN_BYTE_RANGE: UByte = (Tag::spanByteRange).toUByte()

public val SPAN_START: UByte = (Tag::spanStart).toUByte()

public val SPAN_END: UByte = (Tag::spanEnd).toUByte()

public val SPAN_LINE: UByte = (Tag::spanLine).toUByte()

public val SPAN_COLUMN: UByte = (Tag::spanColumn).toUByte()

public val SPAN_FILE: UByte = (Tag::spanFile).toUByte()

public val SPAN_LOCAL_FILE: UByte = (Tag::spanLocalFile).toUByte()

public val SPAN_JOIN: UByte = (Tag::spanJoin).toUByte()

public val SPAN_SUBSPAN: UByte = (Tag::spanSubspan).toUByte()

public val SPAN_RESOLVED_AT: UByte = (Tag::spanResolvedAt).toUByte()

public val SPAN_SOURCE_TEXT: UByte = (Tag::spanSourceText).toUByte()

public val SPAN_SAVE_SPAN: UByte = (Tag::spanSaveSpan).toUByte()

public val SPAN_RECOVER_PROC_MACRO_SPAN: UByte = (Tag::spanRecoverProcMacroSpan).toUByte()

public val SYMBOL_NORMALIZE_AND_VALIDATE_IDENT: UByte = (Tag::symbolNormalizeAndValidateIdent).toUByte()

public fun <S> ApiTags.encode(w: Buffer, s: S) {
    when (this) {
        ApiTags.trackEnvVar -> {
            trackEnvVar.encode(w, s)
        }
        ApiTags.trackPath -> {
            trackPath.encode(w, s)
        }
        ApiTags.literalFromStr -> {
            literalFromStr.encode(w, s)
        }
        ApiTags.emitDiagnostic -> {
            emitDiagnostic.encode(w, s)
        }
        ApiTags.tsDrop -> {
            tsDrop.encode(w, s)
        }
        ApiTags.tsClone -> {
            tsClone.encode(w, s)
        }
        ApiTags.tsIsEmpty -> {
            tsIsEmpty.encode(w, s)
        }
        ApiTags.tsExpandExpr -> {
            tsExpandExpr.encode(w, s)
        }
        ApiTags.tsFromStr -> {
            tsFromStr.encode(w, s)
        }
        ApiTags.tsToString -> {
            tsToString.encode(w, s)
        }
        ApiTags.tsFromTokenTree -> {
            tsFromTokenTree.encode(w, s)
        }
        ApiTags.tsConcatTrees -> {
            tsConcatTrees.encode(w, s)
        }
        ApiTags.tsConcatStreams -> {
            tsConcatStreams.encode(w, s)
        }
        ApiTags.tsIntoTrees -> {
            tsIntoTrees.encode(w, s)
        }
        ApiTags.spanDebug -> {
            spanDebug.encode(w, s)
        }
        ApiTags.spanParent -> {
            spanParent.encode(w, s)
        }
        ApiTags.spanSource -> {
            spanSource.encode(w, s)
        }
        ApiTags.spanByteRange -> {
            spanByteRange.encode(w, s)
        }
        ApiTags.spanStart -> {
            spanStart.encode(w, s)
        }
        ApiTags.spanEnd -> {
            spanEnd.encode(w, s)
        }
        ApiTags.spanLine -> {
            spanLine.encode(w, s)
        }
        ApiTags.spanColumn -> {
            spanColumn.encode(w, s)
        }
        ApiTags.spanFile -> {
            spanFile.encode(w, s)
        }
        ApiTags.spanLocalFile -> {
            spanLocalFile.encode(w, s)
        }
        ApiTags.spanJoin -> {
            spanJoin.encode(w, s)
        }
        ApiTags.spanSubspan -> {
            spanSubspan.encode(w, s)
        }
        ApiTags.spanResolvedAt -> {
            spanResolvedAt.encode(w, s)
        }
        ApiTags.spanSourceText -> {
            spanSourceText.encode(w, s)
        }
        ApiTags.spanSaveSpan -> {
            spanSaveSpan.encode(w, s)
        }
        ApiTags.spanRecoverProcMacroSpan -> {
            spanRecoverProcMacroSpan.encode(w, s)
        }
        ApiTags.symbolNormalizeAndValidateIdent -> {
            symbolNormalizeAndValidateIdent.encode(w, s)
        }
    }
}

public fun <S> decodeApiTags(r: List<UByte>, s: S): ApiTags =
    when (val matchVal = UByte.decode(r, s)) {
        else -> {
            val trackEnvVar = matchVal
            ApiTags::trackEnvVar
        }
        else -> {
            val trackPath = matchVal
            ApiTags::trackPath
        }
        else -> {
            val literalFromStr = matchVal
            ApiTags::literalFromStr
        }
        else -> {
            val emitDiagnostic = matchVal
            ApiTags::emitDiagnostic
        }
        else -> {
            val tsDrop = matchVal
            ApiTags::tsDrop
        }
        else -> {
            val tsClone = matchVal
            ApiTags::tsClone
        }
        else -> {
            val tsIsEmpty = matchVal
            ApiTags::tsIsEmpty
        }
        else -> {
            val tsExpandExpr = matchVal
            ApiTags::tsExpandExpr
        }
        else -> {
            val tsFromStr = matchVal
            ApiTags::tsFromStr
        }
        else -> {
            val tsToString = matchVal
            ApiTags::tsToString
        }
        else -> {
            val tsFromTokenTree = matchVal
            ApiTags::tsFromTokenTree
        }
        else -> {
            val tsConcatTrees = matchVal
            ApiTags::tsConcatTrees
        }
        else -> {
            val tsConcatStreams = matchVal
            ApiTags::tsConcatStreams
        }
        else -> {
            val tsIntoTrees = matchVal
            ApiTags::tsIntoTrees
        }
        else -> {
            val spanDebug = matchVal
            ApiTags::spanDebug
        }
        else -> {
            val spanParent = matchVal
            ApiTags::spanParent
        }
        else -> {
            val spanSource = matchVal
            ApiTags::spanSource
        }
        else -> {
            val spanByteRange = matchVal
            ApiTags::spanByteRange
        }
        else -> {
            val spanStart = matchVal
            ApiTags::spanStart
        }
        else -> {
            val spanEnd = matchVal
            ApiTags::spanEnd
        }
        else -> {
            val spanLine = matchVal
            ApiTags::spanLine
        }
        else -> {
            val spanColumn = matchVal
            ApiTags::spanColumn
        }
        else -> {
            val spanFile = matchVal
            ApiTags::spanFile
        }
        else -> {
            val spanLocalFile = matchVal
            ApiTags::spanLocalFile
        }
        else -> {
            val spanJoin = matchVal
            ApiTags::spanJoin
        }
        else -> {
            val spanSubspan = matchVal
            ApiTags::spanSubspan
        }
        else -> {
            val spanResolvedAt = matchVal
            ApiTags::spanResolvedAt
        }
        else -> {
            val spanSourceText = matchVal
            ApiTags::spanSourceText
        }
        else -> {
            val spanSaveSpan = matchVal
            ApiTags::spanSaveSpan
        }
        else -> {
            val spanRecoverProcMacroSpan = matchVal
            ApiTags::spanRecoverProcMacroSpan
        }
        else -> {
            val symbolNormalizeAndValidateIdent = matchVal
            ApiTags::symbolNormalizeAndValidateIdent
        }
        else -> error("unreachable")
    }
