package io.github.kotlinmania.rustlang.std.sys.net.connection.xous

// this structure needs to be synchronized with what's in net/src/api.rs
public sealed class NetError(
    public val value: Int,
) {
    // Ok = 0,
    public data object Unaddressable : NetError(1)
    public data object SocketInUse : NetError(2)
    // AccessDenied = 3,
    public data object Invalid : NetError(4)
    // Finished = 5,
    public data object LibraryError : NetError(6)
    // AlreadyUsed = 7,
    public data object TimedOut : NetError(8)
    public data object WouldBlock : NetError(9)
}
