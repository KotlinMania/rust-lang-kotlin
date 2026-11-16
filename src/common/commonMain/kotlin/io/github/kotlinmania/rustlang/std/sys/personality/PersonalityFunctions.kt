package io.github.kotlinmania.rustlang.std.sys.personality

import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort

// This is required by the compiler to exist (e.g., it's a lang item),
// but it's never actually called by the compiler because
// __CxxFrameHandler3 (msvc) / __gxx_wasm_personality_v0 (wasm) is the
// personality function that is always used.  Hence this is just an
// aborting Note.
public fun rustEhPersonality() {
    kmAbort()
}
