// port-lint: tests symbolize/gimli/parse_running_mmaps_unix.rs
package io.github.kotlinmania.rustlang.backtrace.symbolize.gimli.parserunningmmapsunix

public class ParseRunningMmapsUnixInlineTests {
    // Make sure we can parse 64-bit sample output if we're on a 64-bit target.
    @kotlin.test.Test
    public fun checkMapsEntryParsing64bit() {
        check("ffffffffff600000-ffffffffff601000 --xp 00000000 00:00 0                  [vsyscall]".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0xffffffffff600000uL, 0xffffffffff601000uL), offset = 0x00000000uL, pathname = "[vsyscall]".into()))
        check("7f5985f46000-7f5985f48000 rw-p 00039000 103:06 76021795                  /usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0x7f5985f46000, 0x7f5985f48000), offset = 0x00039000uL, pathname = "/usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2".into()))
        check("35b1a21000-35b1a22000 rw-p 00000000 00:00 0".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0x35b1a21000, 0x35b1a22000), offset = 0x00000000uL, pathname = Default.default()))
    }

    // (This output was taken from a 32-bit machine, but will work on any target)
    @kotlin.test.Test
    public fun checkMapsEntryParsing32bit() {
        // Example snippet of output:
        // 08056000-08077000 rw-p 00000000 00:00 0          [heap]
        // b7c79000-b7e02000 r--p 00000000 08:01 60662705   /usr/lib/locale/locale-archive
        // b7e02000-b7e03000 rw-p 00000000 00:00 0
        //
        check("08056000-08077000 rw-p 00000000 00:00 0          [heap]".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0x08056000, 0x08077000), offset = 0x00000000uL, pathname = "[heap]".into()))
        check("b7c79000-b7e02000 r--p 00000000 08:01 60662705   /usr/lib/locale/locale-archive".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0xb7c79000, 0xb7e02000), offset = 0x00000000uL, pathname = "/usr/lib/locale/locale-archive".into()))
        check("b7e02000-b7e03000 rw-p 00000000 00:00 0".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0xb7e02000, 0xb7e03000), offset = 0x00000000uL, pathname = Default.default()))
        check("b7c79000-b7e02000 r--p 00000000 08:01 60662705   /executable/path/with some spaces".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0xb7c79000, 0xb7e02000), offset = 0x00000000uL, pathname = "/executable/path/with some spaces".into()))
        check("b7c79000-b7e02000 r--p 00000000 08:01 60662705   /executable/path/with  multiple-continuous    spaces  ".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0xb7c79000, 0xb7e02000), offset = 0x00000000uL, pathname = "/executable/path/with  multiple-continuous    spaces  ".into()))
        check("  b7c79000-b7e02000  r--p  00000000  08:01  60662705   /executable/path/starts-with-spaces".parse<MapsEntry>().unwrap() == MapsEntry(address = Pair(0xb7c79000, 0xb7e02000), offset = 0x00000000uL, pathname = "/executable/path/starts-with-spaces".into()))
    }
}
