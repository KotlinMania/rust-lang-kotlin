// port-lint: source new/helenos/abi/errno.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.abi.errno

import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

// HelenOS errno codes
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/abi/include/abi/errno.h>

public val EOK: ErrnoT = 0

public val ENOENT: ErrnoT = 1

public val ENOMEM: ErrnoT = 2

public val ELIMIT: ErrnoT = 3

public val EREFUSED: ErrnoT = 4

public val EFORWARD: ErrnoT = 5

public val EPERM: ErrnoT = 6

public val EHANGUP: ErrnoT = 7

public val EPARTY: ErrnoT = 8

public val EEXIST: ErrnoT = 9

public val EBADMEM: ErrnoT = 10

public val ENOTSUP: ErrnoT = 11

public val EADDRNOTAVAIL: ErrnoT = 12

public val ETIMEOUT: ErrnoT = 13

public val EINVAL: ErrnoT = 14

public val EBUSY: ErrnoT = 15

public val EOVERFLOW: ErrnoT = 16

public val EINTR: ErrnoT = 17

public val EMFILE: ErrnoT = 18

public val ENAMETOOLONG: ErrnoT = 19

public val EISDIR: ErrnoT = 20

public val ENOTDIR: ErrnoT = 21

public val ENOSPC: ErrnoT = 22

public val ENOTEMPTY: ErrnoT = 23

public val EBADF: ErrnoT = 24

public val EDOM: ErrnoT = 25

public val ERANGE: ErrnoT = 26

public val EXDEV: ErrnoT = 27

public val EIO: ErrnoT = 28

public val EMLINK: ErrnoT = 29

public val ENXIO: ErrnoT = 30

public val ENOFS: ErrnoT = 31

public val EBADCHECKSUM: ErrnoT = 32

public val ESTALL: ErrnoT = 33

public val EEMPTY: ErrnoT = 34

public val ENAK: ErrnoT = 35

public val EAGAIN: ErrnoT = 36
