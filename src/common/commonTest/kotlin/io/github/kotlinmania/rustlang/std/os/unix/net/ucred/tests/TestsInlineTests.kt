// port-lint: tests os/unix/net/ucred/tests.rs
package io.github.kotlinmania.rustlang.std.os.unix.net.ucred.tests

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.getegid
import io.github.kotlinmania.rustlang.libc.geteuid
import io.github.kotlinmania.rustlang.libc.getpid
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream

public class TestsInlineTests {
    @kotlin.test.Test
    public fun testSocketPair() {
        // Create two connected sockets and get their peer credentials. They should be equal.
        val (sockA, sockB) = UnixStream.pair().unwrap()
        val (credA, credB) = Pair(sockA.peerCred().unwrap(), sockB.peerCred().unwrap())
        check(credA == credB)
        // Check that the UID and GIDs match up.
        val uid = geteuid()
        val gid = getegid()
        check(credA.uid == uid)
        check(credA.gid == gid)
    }

    @kotlin.test.Test
    public fun testSocketPairPids(arg: Type): RetType {
        // Create two connected sockets and get their peer credentials.
        val (sockA, sockB) = UnixStream.pair().unwrap()
        val (credA, credB) = Pair(sockA.peerCred().unwrap(), sockB.peerCred().unwrap())
        // On supported platforms (see the cfg above), the credentials should always include the PID.
        val pid = getpid()
        check(credA.pid == pid)
        check(credB.pid == pid)
    }
}
