package io.github.kotlinmania.rustlang.core.char

// UTF-8 ranges and tags for encoding characters
public val TAG_CONT: UByte = 0x80u

public val TAG_TWO_B: UByte = 0xC0u

public val TAG_THREE_B: UByte = 0xE0u

public val TAG_FOUR_B: UByte = 0xF0u

public val MAX_ONE_B: UInt = 0x80u

public val MAX_TWO_B: UInt = 0x800u

public val MAX_THREE_B: UInt = 0x10000u

//
// Lu  UppercaseLetter        an uppercase letter
// Ll  LowercaseLetter        a lowercase letter
// Lt  TitlecaseLetter        a digraphic character, with first part uppercase
// Lm  ModifierLetter         a modifier letter
// Lo  OtherLetter            other letters, including syllables and ideographs
// Mn  NonspacingMark         a nonspacing combining mark (zero advance width)
// Mc  SpacingMark            a spacing combining mark (positive advance width)
// Me  EnclosingMark          an enclosing combining mark
// Nd  DecimalNumber          a decimal digit
// Nl  LetterNumber           a letterlike numeric character
// No  OtherNumber            a numeric character of other type
// Pc  ConnectorPunctuation   a connecting punctuation mark, like a tie
// Pd  DashPunctuation        a dash or hyphen punctuation mark
// Ps  OpenPunctuation        an opening punctuation mark (of a pair)
// Pe  ClosePunctuation       a closing punctuation mark (of a pair)
// Pi  InitialPunctuation     an initial quotation mark
// Pf  FinalPunctuation       a final quotation mark
// Po  OtherPunctuation       a punctuation mark of other type
// Sm  MathSymbol             a symbol of primarily mathematical use
// Sc  CurrencySymbol         a currency sign
// Sk  ModifierSymbol         a non-letterlike modifier symbol
// So  OtherSymbol            a symbol of other type
// Zs  SpaceSeparator         a space character (of various non-zero widths)
// Zl  LineSeparator          U+2028 LINE SEPARATOR only
// Zp  ParagraphSeparator     U+2029 PARAGRAPH SEPARATOR only
// Cc  Control                 a C0 or C1 control code
// Cf  Format                  a format control character
// Cs  Surrogate               a surrogate code point
// Co  PrivateUse             a private-use character
// Cn  Unassigned              a reserved unassigned code point or a noncharacter
//

/**
 * The highest valid code point a `char` can have, `'\u{10FFFF}'`. Use [char.Max] instead.
 */
public val MAX: Char = Char.MAX_VALUE

/**
 * `U + FFFD REPLACEMENT CHARACTER` (�) is used in Unicode to represent a
 * decoding error. Use [char.ReplacementCharacter] instead.
 */
public val REPLACEMENT_CHARACTER: Char = Char.REPLACEMENT_CHARACTER

/**
 * The version of [Unicode](https://www.unicode.org/) that the Unicode parts of
 * `char` and `String` methods are based on. Use [char.UnicodeVersion] instead.
 */
public val UNICODE_VERSION: Triple<UByte, UByte, UByte> = Char.UNICODE_VERSION
