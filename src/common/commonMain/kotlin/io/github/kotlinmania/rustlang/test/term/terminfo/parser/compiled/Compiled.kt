// port-lint: source term/terminfo/parser/compiled.rs
package io.github.kotlinmania.rustlang.test.term.terminfo.parser.compiled

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.test.term.terminfo.TermInfo
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

// ncurses-compatible compiled terminfo format parsing (term(5))

// These are the orders ncurses uses in its compiled format (as of 5.9). Not sure if portable.
public var BOOLFNAMES: List<String> = listOf("auto_left_margin", "auto_right_margin", "no_esc_ctlc", "ceol_standout_glitch", "eat_newline_glitch", "erase_overstrike", "generic_type", "hard_copy", "has_meta_key", "has_status_line", "insert_null_glitch", "memory_above", "memory_below", "move_insert_mode", "move_standout_mode", "over_strike", "status_line_esc_ok", "dest_tabs_magic_smso", "tilde_glitch", "transparent_underline", "xon_xoff", "needs_xon_xoff", "prtr_silent", "hard_cursor", "non_rev_rmcup", "no_pad_char", "non_dest_scroll_region", "can_change", "back_color_erase", "hue_lightness_saturation", "col_addr_glitch", "cr_cancels_micro_mode", "has_print_wheel", "row_addr_glitch", "semi_auto_right_margin", "cpi_changes_res", "lpi_changes_res", "backspaces_with_bs", "crt_no_scrolling", "no_correctly_working_cr", "gnu_has_meta_key", "linefeed_is_newline", "has_hardware_tabs", "return_does_clr_eol")

public var BOOLNAMES: List<String> = listOf("bw", "am", "xsb", "xhp", "xenl", "eo", "gn", "hc", "km", "hs", "in", "db", "da", "mir", "msgr", "os", "eslok", "xt", "hz", "ul", "xon", "nxon", "mc5i", "chts", "nrrmc", "npc", "ndscr", "ccc", "bce", "hls", "xhpa", "crxm", "daisy", "xvpa", "sam", "cpix", "lpix", "OTbs", "OTns", "OTnc", "OTMT", "OTNL", "OTpt", "OTxr")

public var NUMFNAMES: List<String> = listOf("columns", "init_tabs", "lines", "lines_of_memory", "magic_cookie_glitch", "padding_baud_rate", "virtual_terminal", "width_status_line", "num_labels", "label_height", "label_width", "max_attributes", "maximum_windows", "max_colors", "max_pairs", "no_color_video", "buffer_capacity", "dot_vert_spacing", "dot_horz_spacing", "max_micro_address", "max_micro_jump", "micro_col_size", "micro_line_size", "number_of_pins", "output_res_char", "output_res_line", "output_res_horz_inch", "output_res_vert_inch", "print_rate", "wide_char_size", "buttons", "bit_image_entwining", "bit_image_type", "magic_cookie_glitch_ul", "carriage_return_delay", "new_line_delay", "backspace_delay", "horizontal_tab_delay", "number_of_function_keys")

public var NUMNAMES: List<String> = listOf("cols", "it", "lines", "lm", "xmc", "pb", "vt", "wsl", "nlab", "lh", "lw", "ma", "wnum", "colors", "pairs", "ncv", "bufsz", "spinv", "spinh", "maddr", "mjump", "mcs", "mls", "npins", "orc", "orl", "orhi", "orvi", "cps", "widcs", "btns", "bitwin", "bitype", "UTug", "OTdC", "OTdN", "OTdB", "OTdT", "OTkn")

public var STRINGFNAMES: List<String> = listOf("back_tab", "bell", "carriage_return", "change_scroll_region", "clear_all_tabs", "clear_screen", "clr_eol", "clr_eos", "column_address", "command_character", "cursor_address", "cursor_down", "cursor_home", "cursor_invisible", "cursor_left", "cursor_mem_address", "cursor_normal", "cursor_right", "cursor_to_ll", "cursor_up", "cursor_visible", "delete_character", "delete_line", "dis_status_line", "down_half_line", "enter_alt_charset_mode", "enter_blink_mode", "enter_bold_mode", "enter_ca_mode", "enter_delete_mode", "enter_dim_mode", "enter_insert_mode", "enter_secure_mode", "enter_protected_mode", "enter_reverse_mode", "enter_standout_mode", "enter_underline_mode", "erase_chars", "exit_alt_charset_mode", "exit_attribute_mode", "exit_ca_mode", "exit_delete_mode", "exit_insert_mode", "exit_standout_mode", "exit_underline_mode", "flash_screen", "form_feed", "from_status_line", "init_1string", "init_2string", "init_3string", "init_file", "insert_character", "insert_line", "insert_padding", "key_backspace", "key_catab", "key_clear", "key_ctab", "key_dc", "key_dl", "key_down", "key_eic", "key_eol", "key_eos", "key_f0", "key_f1", "key_f10", "key_f2", "key_f3", "key_f4", "key_f5", "key_f6", "key_f7", "key_f8", "key_f9", "key_home", "key_ic", "key_il", "key_left", "key_ll", "key_npage", "key_ppage", "key_right", "key_sf", "key_sr", "key_stab", "key_up", "keypad_local", "keypad_xmit", "lab_f0", "lab_f1", "lab_f10", "lab_f2", "lab_f3", "lab_f4", "lab_f5", "lab_f6", "lab_f7", "lab_f8", "lab_f9", "meta_off", "meta_on", "newline", "pad_char", "parm_dch", "parm_delete_line", "parm_down_cursor", "parm_ich", "parm_index", "parm_insert_line", "parm_left_cursor", "parm_right_cursor", "parm_rindex", "parm_up_cursor", "pkey_key", "pkey_local", "pkey_xmit", "print_screen", "prtr_off", "prtr_on", "repeat_char", "reset_1string", "reset_2string", "reset_3string", "reset_file", "restore_cursor", "row_address", "save_cursor", "scroll_forward", "scroll_reverse", "set_attributes", "set_tab", "set_window", "tab", "to_status_line", "underline_char", "up_half_line", "init_prog", "key_a1", "key_a3", "key_b2", "key_c1", "key_c3", "prtr_non", "char_padding", "acs_chars", "plab_norm", "key_btab", "enter_xon_mode", "exit_xon_mode", "enter_am_mode", "exit_am_mode", "xon_character", "xoff_character", "ena_acs", "label_on", "label_off", "key_beg", "key_cancel", "key_close", "key_command", "key_copy", "key_create", "key_end", "key_enter", "key_exit", "key_find", "key_help", "key_mark", "key_message", "key_move", "key_next", "key_open", "key_options", "key_previous", "key_print", "key_redo", "key_reference", "key_refresh", "key_replace", "key_restart", "key_resume", "key_save", "key_suspend", "key_undo", "key_sbeg", "key_scancel", "key_scommand", "key_scopy", "key_screate", "key_sdc", "key_sdl", "key_select", "key_send", "key_seol", "key_sexit", "key_sfind", "key_shelp", "key_shome", "key_sic", "key_sleft", "key_smessage", "key_smove", "key_snext", "key_soptions", "key_sprevious", "key_sprint", "key_sredo", "key_sreplace", "key_sright", "key_srsume", "key_ssave", "key_ssuspend", "key_sundo", "req_for_input", "key_f11", "key_f12", "key_f13", "key_f14", "key_f15", "key_f16", "key_f17", "key_f18", "key_f19", "key_f20", "key_f21", "key_f22", "key_f23", "key_f24", "key_f25", "key_f26", "key_f27", "key_f28", "key_f29", "key_f30", "key_f31", "key_f32", "key_f33", "key_f34", "key_f35", "key_f36", "key_f37", "key_f38", "key_f39", "key_f40", "key_f41", "key_f42", "key_f43", "key_f44", "key_f45", "key_f46", "key_f47", "key_f48", "key_f49", "key_f50", "key_f51", "key_f52", "key_f53", "key_f54", "key_f55", "key_f56", "key_f57", "key_f58", "key_f59", "key_f60", "key_f61", "key_f62", "key_f63", "clr_bol", "clear_margins", "set_left_margin", "set_right_margin", "label_format", "set_clock", "display_clock", "remove_clock", "create_window", "goto_window", "hangup", "dial_phone", "quick_dial", "tone", "pulse", "flash_hook", "fixed_pause", "wait_tone", "user0", "user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8", "user9", "orig_pair", "orig_colors", "initialize_color", "initialize_pair", "set_color_pair", "set_foreground", "set_background", "change_char_pitch", "change_line_pitch", "change_res_horz", "change_res_vert", "define_char", "enter_doublewide_mode", "enter_draft_quality", "enter_italics_mode", "enter_leftward_mode", "enter_micro_mode", "enter_near_letter_quality", "enter_normal_quality", "enter_shadow_mode", "enter_subscript_mode", "enter_superscript_mode", "enter_upward_mode", "exit_doublewide_mode", "exit_italics_mode", "exit_leftward_mode", "exit_micro_mode", "exit_shadow_mode", "exit_subscript_mode", "exit_superscript_mode", "exit_upward_mode", "micro_column_address", "micro_down", "micro_left", "micro_right", "micro_row_address", "micro_up", "order_of_pins", "parm_down_micro", "parm_left_micro", "parm_right_micro", "parm_up_micro", "select_char_set", "set_bottom_margin", "set_bottom_margin_parm", "set_left_margin_parm", "set_right_margin_parm", "set_top_margin", "set_top_margin_parm", "start_bit_image", "start_char_set_def", "stop_bit_image", "stop_char_set_def", "subscript_characters", "superscript_characters", "these_cause_cr", "zero_motion", "char_set_names", "key_mouse", "mouse_info", "req_mouse_pos", "get_mouse", "set_a_foreground", "set_a_background", "pkey_plab", "device_type", "code_set_init", "set0_des_seq", "set1_des_seq", "set2_des_seq", "set3_des_seq", "set_lr_margin", "set_tb_margin", "bit_image_repeat", "bit_image_newline", "bit_image_carriage_return", "color_names", "define_bit_image_region", "end_bit_image_region", "set_color_band", "set_page_length", "display_pc_char", "enter_pc_charset_mode", "exit_pc_charset_mode", "enter_scancode_mode", "exit_scancode_mode", "pc_term_options", "scancode_escape", "alt_scancode_esc", "enter_horizontal_hl_mode", "enter_left_hl_mode", "enter_low_hl_mode", "enter_right_hl_mode", "enter_top_hl_mode", "enter_vertical_hl_mode", "set_a_attributes", "set_pglen_inch", "termcap_init2", "termcap_reset", "linefeed_if_not_lf", "backspace_if_not_bs", "other_non_function_keys", "arrow_key_map", "acs_ulcorner", "acs_llcorner", "acs_urcorner", "acs_lrcorner", "acs_ltee", "acs_rtee", "acs_btee", "acs_ttee", "acs_hline", "acs_vline", "acs_plus", "memory_lock", "memory_unlock", "box_chars_1")

public var STRINGNAMES: List<String> = listOf("cbt", "_", "cr", "csr", "tbc", "clear", "_", "_", "hpa", "cmdch", "cup", "cud1", "home", "civis", "cub1", "mrcup", "cnorm", "cuf1", "ll", "cuu1", "cvvis", "dch1", "dl1", "dsl", "hd", "smacs", "blink", "bold", "smcup", "smdc", "dim", "smir", "invis", "prot", "rev", "smso", "smul", "ech", "rmacs", "sgr0", "rmcup", "rmdc", "rmir", "rmso", "rmul", "flash", "ff", "fsl", "is1", "is2", "is3", "if", "ich1", "il1", "ip", "kbs", "ktbc", "kclr", "kctab", "_", "_", "kcud1", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "khome", "_", "_", "kcub1", "_", "knp", "kpp", "kcuf1", "_", "_", "khts", "_", "rmkx", "smkx", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "rmm", "_", "_", "pad", "dch", "dl", "cud", "ich", "indn", "il", "cub", "cuf", "rin", "cuu", "pfkey", "pfloc", "pfx", "mc0", "mc4", "_", "rep", "rs1", "rs2", "rs3", "rf", "rc", "vpa", "sc", "ind", "ri", "sgr", "_", "wind", "_", "tsl", "uc", "hu", "iprog", "_", "_", "_", "_", "_", "mc5p", "rmp", "acsc", "pln", "kcbt", "smxon", "rmxon", "smam", "rmam", "xonc", "xoffc", "_", "smln", "rmln", "_", "kcan", "kclo", "kcmd", "kcpy", "kcrt", "_", "kent", "kext", "kfnd", "khlp", "kmrk", "kmsg", "kmov", "knxt", "kopn", "kopt", "kprv", "kprt", "krdo", "kref", "krfr", "krpl", "krst", "kres", "ksav", "kspd", "kund", "kBEG", "kCAN", "kCMD", "kCPY", "kCRT", "_", "_", "kslt", "kEND", "kEOL", "kEXT", "kFND", "kHLP", "kHOM", "_", "kLFT", "kMSG", "kMOV", "kNXT", "kOPT", "kPRV", "kPRT", "kRDO", "kRPL", "kRIT", "kRES", "kSAV", "kSPD", "kUND", "rfi", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "dclk", "rmclk", "cwin", "wingo", "_", "dial", "qdial", "_", "_", "hook", "pause", "wait", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "op", "oc", "initc", "initp", "scp", "setf", "setb", "cpi", "lpi", "chr", "cvr", "defc", "swidm", "sdrfq", "sitm", "slm", "smicm", "snlq", "snrmq", "sshm", "ssubm", "ssupm", "sum", "rwidm", "ritm", "rlm", "rmicm", "rshm", "rsubm", "rsupm", "rum", "mhpa", "mcud1", "mcub1", "mcuf1", "mvpa", "mcuu1", "porder", "mcud", "mcub", "mcuf", "mcuu", "scs", "smgb", "smgbp", "smglp", "smgrp", "smgt", "smgtp", "sbim", "scsd", "rbim", "rcsd", "subcs", "supcs", "docr", "zerom", "csnm", "kmous", "minfo", "reqmp", "getm", "setaf", "setab", "pfxl", "devt", "csin", "s0ds", "s1ds", "s2ds", "s3ds", "smglr", "smgtb", "birep", "binel", "bicr", "colornm", "defbi", "endbi", "setcolor", "slines", "dispc", "smpch", "rmpch", "smsc", "rmsc", "pctrm", "scesc", "scesa", "ehhlm", "elhlm", "elohlm", "erhlm", "ethlm", "evhlm", "sgr1", "slength", "OTi2", "OTrs", "OTnl", "OTbs", "OTko", "OTma", "OTG2", "OTG3", "OTG1", "OTG4", "OTGR", "OTGL", "OTGU", "OTGD", "OTGH", "OTGV", "OTGC", "meml", "memu", "box1")

public fun readLeU16(r: Read): Result<UShort> {
    val b = MutableList(2) { 0 }
    when (val tryResult = r.readExact(b)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<UShort, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok((b[0].toUShort()) or (((b[1].toUShort()).toUInt() shl 8).toUShort()))
}

public fun readLeU32(r: Read): Result<UInt> {
    val b = MutableList(4) { 0 }
    when (val tryResult = r.readExact(b)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<UInt, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok((b[0].toUInt()) or ((b[1].toUInt()) shl 8) or ((b[2].toUInt()) shl 16) or ((b[3].toUInt()) shl 24))
}

public fun readByte(r: Read): Result<UByte> =
    when (val matchVal = r.bytes().next()) {
        null -> RustResult.Err(io.constError(ErrorKind.Other, "end of file"))
        else -> {
            val s = matchVal
            s
        }
    }

/**
 * Parse a compiled terminfo entry, using long capability names if `longnames`
 * is true
 */
public fun parse(file: Read, longnames: Boolean): RustResult<TermInfo, String> {
    // According to the spec, these fields must be >= -1 where -1 means that the feature is not
    // supported. Using 0 instead of -1 works because we skip sections with length 0.
    val (bnames, snames, nnames) =
        if (longnames) {
            Triple(BOOLFNAMES, STRINGFNAMES, NUMFNAMES)
        } else {
            Triple(BOOLNAMES, STRINGNAMES, NUMNAMES)
        }
    // Check magic number
    val magic =
        run {
            val matchVal = readLeU16(file)
            when (matchVal) { is RustResult.Ok<*, *> -> {
                val e = matchVal.okValue()
                e
            }; is RustResult.Err<*, *> -> {
                val e1 = matchVal.errValue()
                return RustResult.Err<TermInfo, String>(e1.toString())
            } }
        }
    val extended =
        when (magic) {
            282 -> false
            542 -> true
            else -> { return RustResult.Err<TermInfo, String>("invalid magic number, found $magic") }
        }
    val namesBytes = run {
        val matchVal2 = (run {
        val matchVal1 = readLeU16(file)
        when (matchVal1) { is RustResult.Ok<*, *> -> {
            val e2 = matchVal1.okValue()
            e2
        }; is RustResult.Err<*, *> -> {
            val e3 = matchVal1.errValue()
            return RustResult.Err<TermInfo, String>(e3.toString())
        } }
    }).toShort()
        when {
            matchVal2 >= 0 -> {
                val n = matchVal2
                n.toInt()
            }
            matchVal2 == -1.toShort() -> 0
            else -> { return RustResult.Err<TermInfo, String>("incompatible file: length fields must be  >= -1".toString()) }
        }
    }
    val boolsBytes = run {
        val matchVal4 = (run {
        val matchVal3 = readLeU16(file)
        when (matchVal3) { is RustResult.Ok<*, *> -> {
            val e4 = matchVal3.okValue()
            e4
        }; is RustResult.Err<*, *> -> {
            val e5 = matchVal3.errValue()
            return RustResult.Err<TermInfo, String>(e5.toString())
        } }
    }).toShort()
        when {
            matchVal4 >= 0 -> {
                val n1 = matchVal4
                n1.toInt()
            }
            matchVal4 == -1.toShort() -> 0
            else -> { return RustResult.Err<TermInfo, String>("incompatible file: length fields must be  >= -1".toString()) }
        }
    }
    val numbersCount = run {
        val matchVal6 = (run {
        val matchVal5 = readLeU16(file)
        when (matchVal5) { is RustResult.Ok<*, *> -> {
            val e6 = matchVal5.okValue()
            e6
        }; is RustResult.Err<*, *> -> {
            val e7 = matchVal5.errValue()
            return RustResult.Err<TermInfo, String>(e7.toString())
        } }
    }).toShort()
        when {
            matchVal6 >= 0 -> {
                val n2 = matchVal6
                n2.toInt()
            }
            matchVal6 == -1.toShort() -> 0
            else -> { return RustResult.Err<TermInfo, String>("incompatible file: length fields must be  >= -1".toString()) }
        }
    }
    val stringOffsetsCount = run {
        val matchVal8 = (run {
        val matchVal7 = readLeU16(file)
        when (matchVal7) { is RustResult.Ok<*, *> -> {
            val e8 = matchVal7.okValue()
            e8
        }; is RustResult.Err<*, *> -> {
            val e9 = matchVal7.errValue()
            return RustResult.Err<TermInfo, String>(e9.toString())
        } }
    }).toShort()
        when {
            matchVal8 >= 0 -> {
                val n3 = matchVal8
                n3.toInt()
            }
            matchVal8 == -1.toShort() -> 0
            else -> { return RustResult.Err<TermInfo, String>("incompatible file: length fields must be  >= -1".toString()) }
        }
    }
    val stringTableBytes = run {
        val matchVal10 = (run {
        val matchVal9 = readLeU16(file)
        when (matchVal9) { is RustResult.Ok<*, *> -> {
            val e10 = matchVal9.okValue()
            e10
        }; is RustResult.Err<*, *> -> {
            val e11 = matchVal9.errValue()
            return RustResult.Err<TermInfo, String>(e11.toString())
        } }
    }).toShort()
        when {
            matchVal10 >= 0 -> {
                val n4 = matchVal10
                n4.toInt()
            }
            matchVal10 == -1.toShort() -> 0
            else -> { return RustResult.Err<TermInfo, String>("incompatible file: length fields must be  >= -1".toString()) }
        }
    }
    if (namesBytes == 0) {
        return RustResult.Err<TermInfo, String>("incompatible file: names field must be at least 1 byte wide".toString())
    }
    if (boolsBytes > BOOLNAMES.size) {
        return RustResult.Err<TermInfo, String>("incompatible file: more booleans than expected".toString())
    }
    if (numbersCount > NUMNAMES.size) {
        return RustResult.Err<TermInfo, String>("incompatible file: more numbers than expected".toString())
    }
    if (stringOffsetsCount > STRINGNAMES.size) {
        return RustResult.Err<TermInfo, String>("incompatible file: more string offsets than expected".toString())
    }
    // don't read NUL
    val bytes = mutableListOf()
    run {
        val matchVal11 = file.take((namesBytes - 1).toULong()).readToEnd(bytes)
        when (matchVal11) { is RustResult.Ok<*, *> -> {
            val e12 = matchVal11.okValue()
            e12
        }; is RustResult.Err<*, *> -> {
            val e13 = matchVal11.errValue()
            return RustResult.Err<TermInfo, String>(e13.toString())
        } }
    }
    val namesStr =
        run {
            val matchVal12 = String.fromUtf8(bytes)
            when (matchVal12) { is RustResult.Ok<*, *> -> {
                val s = matchVal12.okValue()
                s
            }; is RustResult.Err<*, *> -> {
                return RustResult.Err<TermInfo, String>("input not utf-8".toString())
            } }
        }
    val termNames: List<String> = namesStr.split('|').map({ s1 -> s1.toString() }).toMutableList()
    // consume NUL
    if (run {
        val matchVal13 = readByte(file)
        when (matchVal13) { is RustResult.Ok<*, *> -> {
            val e14 = matchVal13.okValue()
            e14
        }; is RustResult.Err<*, *> -> {
            val e15 = matchVal13.errValue()
            return RustResult.Err<TermInfo, String>(e15.toString())
        } }
    } != 0u) {
        return RustResult.Err<TermInfo, String>("incompatible file: missing null terminator for names section".toString())
    }
    val boolsMap: MutableMap<String, Boolean> =
        run {
            val matchVal14 = (0 until boolsBytes).mapNotNull({ i -> run {
            val matchVal14 = readByte(file)
            when {
                matchVal14 is RustResult.Err<*, *> -> {
                    val e16 = matchVal14.errValue()
                    RustResult.Err(e16)
                }
                matchVal14 is RustResult.Ok<*, *> && matchVal14.okValue() == 1 -> {
                    val okValueVal = matchVal14.okValue()
                    RustResult.Ok(Pair(bnames[i].toString(), true))
                }
                else -> null
            }
        } }).toMap(LinkedHashMap())
            when (matchVal14) { is RustResult.Ok<*, *> -> {
                val e16 = matchVal14.okValue()
                e16
            }; is RustResult.Err<*, *> -> {
                val e17 = matchVal14.errValue()
                return RustResult.Err<TermInfo, String>(e17.toString())
            } }
        }
    if ((boolsBytes + namesBytes) % 2 == 1) {
        run {
            val matchVal15 = readByte(file)
            when (matchVal15) { is RustResult.Ok<*, *> -> {
                val e18 = matchVal15.okValue()
                e18
            }; is RustResult.Err<*, *> -> {
                val e19 = matchVal15.errValue()
                return RustResult.Err<TermInfo, String>(e19.toString())
            } }
        }
        // compensate for padding
    }
    val numbersMap: MutableMap<String, UInt> =
        run {
            val matchVal16 = (0 until numbersCount).mapNotNull({ i ->
            val number =
                if (extended) {
                    readLeU32(file)
                } else {
                    readLeU16(file).map(Into::into)
                }
            when {
                number is RustResult.Ok<*, *> && number.okValue() == 0xFFFF -> {
                    val okValueVal = number.okValue()
                    null
                }
                number is RustResult.Ok<*, *> -> {
                    val n5 = number.okValue()
                    RustResult.Ok(Pair(nnames[i].toString(), n5))
                }
                else -> {
                    val e20 = (number as RustResult.Err<*, *>).errValue()
                    RustResult.Err(e20)
                }
            }
        }).toMap(LinkedHashMap())
            when (matchVal16) { is RustResult.Ok<*, *> -> {
                val e20 = matchVal16.okValue()
                e20
            }; is RustResult.Err<*, *> -> {
                val e21 = matchVal16.errValue()
                return RustResult.Err<TermInfo, String>(e21.toString())
            } }
        }
    val stringMap: MutableMap<String, List<UByte>> =
        if (stringOffsetsCount > 0) {
            val stringOffsets: List<UShort> =
                run {
                    val matchVal17 = (0 until stringOffsetsCount).map({ readLeU16(file) }).toMutableList()
                    when (matchVal17) { is RustResult.Ok<*, *> -> {
                        val e22 = matchVal17.okValue()
                        e22
                    }; is RustResult.Err<*, *> -> {
                        val e23 = matchVal17.errValue()
                        return RustResult.Err<TermInfo, String>(e23.toString())
                    } }
                }
            val stringTable = mutableListOf()
            run {
                val matchVal18 = file.take(stringTableBytes.toULong()).readToEnd(stringTable)
                when (matchVal18) { is RustResult.Ok<*, *> -> {
                    val e24 = matchVal18.okValue()
                    e24
                }; is RustResult.Err<*, *> -> {
                    val e25 = matchVal18.errValue()
                    return RustResult.Err<TermInfo, String>(e25.toString())
                } }
            }
            run {
                val matchVal19 = (stringOffsets.mapIndexed { index, item -> Pair(index, item) }.filter({ (_, offset) ->
                // non-entry
                offset != 0xFFFF
            }).map(closure@{ (i, offset) ->
                val offset1 = offset.toInt()
                val name =
                    if (snames[i] == "_") {
                        STRINGFNAMES[i]
                    } else {
                        snames[i]
                    }
                if (offset1 == 0xFFFE) {
                    // undocumented: FFFE indicates cap@, which means the capability is not present
                    // unsure if the handling for this is correct
                    return@closure RustResult.Ok(Pair(name.toString(), mutableListOf()))
                }
                // Find the offset of the NUL we want to go to
                val nulpos = ((stringTable).subList(offset1, stringTableBytes)).indexOfFirst({ b -> b == 0 }).takeIf { it >= 0 }
                when (nulpos) {
                    null -> RustResult.Err("invalid file: missing NUL in string_table".toString())
                    else -> {
                        val len = nulpos
                        RustResult.Ok(Pair(name.toString(), ((stringTable).subList(offset1, offset1 + len)).toMutableList()))
                    }
                }
            })).toMap(LinkedHashMap())
                when (matchVal19) { is RustResult.Ok<*, *> -> {
                    val e26 = matchVal19.okValue()
                    e26
                }; is RustResult.Err<*, *> -> {
                    val e27 = matchVal19.errValue()
                    return RustResult.Err<TermInfo, String>(e27.toString())
                } }
            }
        } else {
            mutableMapOf()
        }
    // And that's all there is to it
    return RustResult.Ok<TermInfo, String>(TermInfo(names = termNames, bools = boolsMap, numbers = numbersMap, strings = stringMap))
}

/**
 * Creates a dummy TermInfo struct for msys terminals
 */
public fun msysTerminfo(): TermInfo {
    var strings = mutableMapOf()
    strings.put("sgr0".toString(), "\u001b[0m".encodeToByteArray().toVec())
    strings.put("bold".toString(), "\u001b[1m".encodeToByteArray().toVec())
    strings.put("setaf".toString(), "\u001b[3%p1%dm".encodeToByteArray().toVec())
    strings.put("setab".toString(), "\u001b[4%p1%dm".encodeToByteArray().toVec())
    var numbers = mutableMapOf()
    numbers.put("colors".toString(), 8)
    return TermInfo(names = mutableListOf("cygwin".toString()), bools = mutableMapOf(), numbers = numbers, strings = strings)
}
