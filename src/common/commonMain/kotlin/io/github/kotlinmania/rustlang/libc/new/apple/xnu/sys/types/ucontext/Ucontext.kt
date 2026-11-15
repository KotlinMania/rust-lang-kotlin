// port-lint: source new/apple/xnu/sys/_types/_ucontext.rs
package io.github.kotlinmania.rustlang.libc.new.apple.xnu.sys.types.ucontext

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.apple.xnu.arm.mcontext.McontextT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SigsetT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.StackT

// Header: `sys / _types / _ucontext.h`

public open class DarwinUcontext internal constructor(
    public var ucOnstack: CInt,
    public var ucSigmask: SigsetT,
    public var ucStack: StackT,
    ucLinkCtorArg: MutPtr,
    public var ucMcsize: Int,
    ucMcontextCtorArg: McontextT,
) {
    public var ucLink: MutPtr = ucLinkCtorArg
        internal set

    public var ucMcontext: McontextT = ucMcontextCtorArg
        internal set

    public open fun clone(): DarwinUcontext = DarwinUcontext(ucOnstack, ucSigmask, ucStack, ucLink, ucMcsize, ucMcontext)

    public companion object {
        public operator fun invoke(
            ucOnstack: CInt,
            ucSigmask: SigsetT,
            ucStack: StackT,
            ucLink: MutPtr,
            ucMcsize: Int,
            ucMcontext: McontextT,
        ): DarwinUcontext = DarwinUcontext(
            ucOnstack = ucOnstack,
            ucSigmask = ucSigmask,
            ucStack = ucStack,
            ucLinkCtorArg = ucLink,
            ucMcsize = ucMcsize,
            ucMcontextCtorArg = ucMcontext,
        )
    }
}

public typealias UcontextT = DarwinUcontext
