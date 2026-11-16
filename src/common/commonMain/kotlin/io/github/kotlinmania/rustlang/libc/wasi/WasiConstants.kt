package io.github.kotlinmania.rustlang.libc.wasi

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.types.u32CastLong

public val EXIT_SUCCESS: CInt = 0

public val EXIT_FAILURE: CInt = 1

public val STDIN_FILENO: CInt = 0

public val STDOUT_FILENO: CInt = 1

public val STDERR_FILENO: CInt = 2

public val SEEK_SET: CInt = 0

public val SEEK_CUR: CInt = 1

public val SEEK_END: CInt = 2

public val IOFBF: CInt = 0

public val IONBF: CInt = 2

public val IOLBF: CInt = 1

public val F_GETFD: CInt = 1

public val F_SETFD: CInt = 2

public val F_GETFL: CInt = 3

public val F_SETFL: CInt = 4

public val FD_CLOEXEC: CInt = 1

public val FD_SETSIZE: SizeT = 1024uL

public val O_APPEND: CInt = 0x0001

public val O_DSYNC: CInt = 0x0002

public val O_NONBLOCK: CInt = 0x0004

public val O_RSYNC: CInt = 0x0008

public val O_SYNC: CInt = 0x0010

public val O_CREAT: CInt = 0x0001 shl 12

public val O_DIRECTORY: CInt = 0x0002 shl 12

public val O_EXCL: CInt = 0x0004 shl 12

public val O_TRUNC: CInt = 0x0008 shl 12

public val O_NOFOLLOW: CInt = 0x01000000

public val O_EXEC: CInt = 0x02000000

public val O_RDONLY: CInt = 0x04000000

public val O_SEARCH: CInt = 0x08000000

public val O_WRONLY: CInt = 0x10000000

public val O_CLOEXEC: CInt = 0x0

public val O_RDWR: CInt = O_WRONLY or O_RDONLY

public val O_ACCMODE: CInt = O_EXEC or O_RDWR or O_SEARCH

public val O_NOCTTY: CInt = 0x0

public val POSIX_FADV_DONTNEED: CInt = 4

public val POSIX_FADV_NOREUSE: CInt = 5

public val POSIX_FADV_NORMAL: CInt = 0

public val POSIX_FADV_RANDOM: CInt = 2

public val POSIX_FADV_SEQUENTIAL: CInt = 1

public val POSIX_FADV_WILLNEED: CInt = 3

public val AT_FDCWD: CInt = -2

public val AT_EACCESS: CInt = 0x0

public val AT_SYMLINK_NOFOLLOW: CInt = 0x1

public val AT_SYMLINK_FOLLOW: CInt = 0x2

public val AT_REMOVEDIR: CInt = 0x4

public val S_IFIFO: ModeT = 4096u

public val S_IFCHR: ModeT = 8192u

public val S_IFBLK: ModeT = 24576u

public val S_IFDIR: ModeT = 16384u

public val S_IFREG: ModeT = 32768u

public val S_IFLNK: ModeT = 40960u

public val S_IFSOCK: ModeT = 49152u

public val S_IFMT: ModeT = 61440u

public val S_IRWXO: ModeT = 7u

public val S_IXOTH: ModeT = 1u

public val S_IWOTH: ModeT = 2u

public val S_IROTH: ModeT = 4u

public val S_IRWXG: ModeT = 56u

public val S_IXGRP: ModeT = 8u

public val S_IWGRP: ModeT = 16u

public val S_IRGRP: ModeT = 32u

public val S_IRWXU: ModeT = 448u

public val S_IXUSR: ModeT = 64u

public val S_IWUSR: ModeT = 128u

public val S_IRUSR: ModeT = 256u

public val S_ISVTX: ModeT = 512u

public val S_ISGID: ModeT = 1024u

public val S_ISUID: ModeT = 2048u

public val DT_UNKNOWN: UByte = 0u

public val DT_BLK: UByte = 1u

public val DT_CHR: UByte = 2u

public val DT_DIR: UByte = 3u

public val DT_REG: UByte = 4u

public val DT_FIFO: UByte = 6u

public val DT_LNK: UByte = 7u

public val DT_SOCK: UByte = 20u

public val FIONREAD: CInt = 1

public val FIONBIO: CInt = 2

public val F_OK: CInt = 0

public val R_OK: CInt = 4

public val W_OK: CInt = 2

public val X_OK: CInt = 1

public val POLLIN: CShort = 0x1

public val POLLOUT: CShort = 0x2

public val POLLERR: CShort = 0x1000

public val POLLHUP: CShort = 0x2000

public val POLLNVAL: CShort = 0x4000

public val POLLRDNORM: CShort = 0x1

public val POLLWRNORM: CShort = 0x2

public val E2BIG: CInt = 1

public val EACCES: CInt = 2

public val EADDRINUSE: CInt = 3

public val EADDRNOTAVAIL: CInt = 4

public val EAFNOSUPPORT: CInt = 5

public val EAGAIN: CInt = 6

public val EALREADY: CInt = 7

public val EBADF: CInt = 8

public val EBADMSG: CInt = 9

public val EBUSY: CInt = 10

public val ECANCELED: CInt = 11

public val ECHILD: CInt = 12

public val ECONNABORTED: CInt = 13

public val ECONNREFUSED: CInt = 14

public val ECONNRESET: CInt = 15

public val EDEADLK: CInt = 16

public val EDESTADDRREQ: CInt = 17

public val EDOM: CInt = 18

public val EDQUOT: CInt = 19

public val EEXIST: CInt = 20

public val EFAULT: CInt = 21

public val EFBIG: CInt = 22

public val EHOSTUNREACH: CInt = 23

public val EIDRM: CInt = 24

public val EILSEQ: CInt = 25

public val EINPROGRESS: CInt = 26

public val EINTR: CInt = 27

public val EINVAL: CInt = 28

public val EIO: CInt = 29

public val EISCONN: CInt = 30

public val EISDIR: CInt = 31

public val ELOOP: CInt = 32

public val EMFILE: CInt = 33

public val EMLINK: CInt = 34

public val EMSGSIZE: CInt = 35

public val EMULTIHOP: CInt = 36

public val ENAMETOOLONG: CInt = 37

public val ENETDOWN: CInt = 38

public val ENETRESET: CInt = 39

public val ENETUNREACH: CInt = 40

public val ENFILE: CInt = 41

public val ENOBUFS: CInt = 42

public val ENODEV: CInt = 43

public val ENOENT: CInt = 44

public val ENOEXEC: CInt = 45

public val ENOLCK: CInt = 46

public val ENOLINK: CInt = 47

public val ENOMEM: CInt = 48

public val ENOMSG: CInt = 49

public val ENOPROTOOPT: CInt = 50

public val ENOSPC: CInt = 51

public val ENOSYS: CInt = 52

public val ENOTCONN: CInt = 53

public val ENOTDIR: CInt = 54

public val ENOTEMPTY: CInt = 55

public val ENOTRECOVERABLE: CInt = 56

public val ENOTSOCK: CInt = 57

public val ENOTSUP: CInt = 58

public val ENOTTY: CInt = 59

public val ENXIO: CInt = 60

public val EOVERFLOW: CInt = 61

public val EOWNERDEAD: CInt = 62

public val EPERM: CInt = 63

public val EPIPE: CInt = 64

public val EPROTO: CInt = 65

public val EPROTONOSUPPORT: CInt = 66

public val EPROTOTYPE: CInt = 67

public val ERANGE: CInt = 68

public val EROFS: CInt = 69

public val ESPIPE: CInt = 70

public val ESRCH: CInt = 71

public val ESTALE: CInt = 72

public val ETIMEDOUT: CInt = 73

public val ETXTBSY: CInt = 74

public val EXDEV: CInt = 75

public val ENOTCAPABLE: CInt = 76

public val EOPNOTSUPP: CInt = ENOTSUP

public val EWOULDBLOCK: CInt = EAGAIN

public val SC_ARG_MAX: CInt = 0

public val SC_CHILD_MAX: CInt = 1

public val SC_CLK_TCK: CInt = 2

public val SC_NGROUPS_MAX: CInt = 3

public val SC_OPEN_MAX: CInt = 4

public val SC_STREAM_MAX: CInt = 5

public val SC_TZNAME_MAX: CInt = 6

public val SC_JOB_CONTROL: CInt = 7

public val SC_SAVED_IDS: CInt = 8

public val SC_REALTIME_SIGNALS: CInt = 9

public val SC_PRIORITY_SCHEDULING: CInt = 10

public val SC_TIMERS: CInt = 11

public val SC_ASYNCHRONOUS_IO: CInt = 12

public val SC_PRIORITIZED_IO: CInt = 13

public val SC_SYNCHRONIZED_IO: CInt = 14

public val SC_FSYNC: CInt = 15

public val SC_MAPPED_FILES: CInt = 16

public val SC_MEMLOCK: CInt = 17

public val SC_MEMLOCK_RANGE: CInt = 18

public val SC_MEMORY_PROTECTION: CInt = 19

public val SC_MESSAGE_PASSING: CInt = 20

public val SC_SEMAPHORES: CInt = 21

public val SC_SHARED_MEMORY_OBJECTS: CInt = 22

public val SC_AIO_LISTIO_MAX: CInt = 23

public val SC_AIO_MAX: CInt = 24

public val SC_AIO_PRIO_DELTA_MAX: CInt = 25

public val SC_DELAYTIMER_MAX: CInt = 26

public val SC_MQ_OPEN_MAX: CInt = 27

public val SC_MQ_PRIO_MAX: CInt = 28

public val SC_VERSION: CInt = 29

public val SC_PAGESIZE: CInt = 30

public val SC_PAGE_SIZE: CInt = SC_PAGESIZE

public val SC_RTSIG_MAX: CInt = 31

public val SC_SEM_NSEMS_MAX: CInt = 32

public val SC_SEM_VALUE_MAX: CInt = 33

public val SC_SIGQUEUE_MAX: CInt = 34

public val SC_TIMER_MAX: CInt = 35

public val SC_BC_BASE_MAX: CInt = 36

public val SC_BC_DIM_MAX: CInt = 37

public val SC_BC_SCALE_MAX: CInt = 38

public val SC_BC_STRING_MAX: CInt = 39

public val SC_COLL_WEIGHTS_MAX: CInt = 40

public val SC_EXPR_NEST_MAX: CInt = 42

public val SC_LINE_MAX: CInt = 43

public val SC_RE_DUP_MAX: CInt = 44

public val SC_2_VERSION: CInt = 46

public val SC_2_C_BIND: CInt = 47

public val SC_2_C_DEV: CInt = 48

public val SC_2_FORT_DEV: CInt = 49

public val SC_2_FORT_RUN: CInt = 50

public val SC_2_SW_DEV: CInt = 51

public val SC_2_LOCALEDEF: CInt = 52

public val SC_UIO_MAXIOV: CInt = 60

public val SC_IOV_MAX: CInt = 60

public val SC_THREADS: CInt = 67

public val SC_THREAD_SAFE_FUNCTIONS: CInt = 68

public val SC_GETGR_R_SIZE_MAX: CInt = 69

public val SC_GETPW_R_SIZE_MAX: CInt = 70

public val SC_LOGIN_NAME_MAX: CInt = 71

public val SC_TTY_NAME_MAX: CInt = 72

public val SC_THREAD_DESTRUCTOR_ITERATIONS: CInt = 73

public val SC_THREAD_KEYS_MAX: CInt = 74

public val SC_THREAD_STACK_MIN: CInt = 75

public val SC_THREAD_THREADS_MAX: CInt = 76

public val SC_THREAD_ATTR_STACKADDR: CInt = 77

public val SC_THREAD_ATTR_STACKSIZE: CInt = 78

public val SC_THREAD_PRIORITY_SCHEDULING: CInt = 79

public val SC_THREAD_PRIO_INHERIT: CInt = 80

public val SC_THREAD_PRIO_PROTECT: CInt = 81

public val SC_THREAD_PROCESS_SHARED: CInt = 82

public val SC_NPROCESSORS_CONF: CInt = 83

public val SC_NPROCESSORS_ONLN: CInt = 84

public val SC_PHYS_PAGES: CInt = 85

public val SC_AVPHYS_PAGES: CInt = 86

public val SC_ATEXIT_MAX: CInt = 87

public val SC_PASS_MAX: CInt = 88

public val SC_XOPEN_VERSION: CInt = 89

public val SC_XOPEN_XCU_VERSION: CInt = 90

public val SC_XOPEN_UNIX: CInt = 91

public val SC_XOPEN_CRYPT: CInt = 92

public val SC_XOPEN_ENH_I18N: CInt = 93

public val SC_XOPEN_SHM: CInt = 94

public val SC_2_CHAR_TERM: CInt = 95

public val SC_2_UPE: CInt = 97

public val SC_XOPEN_XPG2: CInt = 98

public val SC_XOPEN_XPG3: CInt = 99

public val SC_XOPEN_XPG4: CInt = 100

public val SC_NZERO: CInt = 109

public val SC_XBS5_ILP32_OFF32: CInt = 125

public val SC_XBS5_ILP32_OFFBIG: CInt = 126

public val SC_XBS5_LP64_OFF64: CInt = 127

public val SC_XBS5_LPBIG_OFFBIG: CInt = 128

public val SC_XOPEN_LEGACY: CInt = 129

public val SC_XOPEN_REALTIME: CInt = 130

public val SC_XOPEN_REALTIME_THREADS: CInt = 131

public val SC_ADVISORY_INFO: CInt = 132

public val SC_BARRIERS: CInt = 133

public val SC_CLOCK_SELECTION: CInt = 137

public val SC_CPUTIME: CInt = 138

public val SC_THREAD_CPUTIME: CInt = 139

public val SC_MONOTONIC_CLOCK: CInt = 149

public val SC_READER_WRITER_LOCKS: CInt = 153

public val SC_SPIN_LOCKS: CInt = 154

public val SC_REGEXP: CInt = 155

public val SC_SHELL: CInt = 157

public val SC_SPAWN: CInt = 159

public val SC_SPORADIC_SERVER: CInt = 160

public val SC_THREAD_SPORADIC_SERVER: CInt = 161

public val SC_TIMEOUTS: CInt = 164

public val SC_TYPED_MEMORY_OBJECTS: CInt = 165

public val SC_2_PBS: CInt = 168

public val SC_2_PBS_ACCOUNTING: CInt = 169

public val SC_2_PBS_LOCATE: CInt = 170

public val SC_2_PBS_MESSAGE: CInt = 171

public val SC_2_PBS_TRACK: CInt = 172

public val SC_SYMLOOP_MAX: CInt = 173

public val SC_STREAMS: CInt = 174

public val SC_2_PBS_CHECKPOINT: CInt = 175

public val SC_V6_ILP32_OFF32: CInt = 176

public val SC_V6_ILP32_OFFBIG: CInt = 177

public val SC_V6_LP64_OFF64: CInt = 178

public val SC_V6_LPBIG_OFFBIG: CInt = 179

public val SC_HOST_NAME_MAX: CInt = 180

public val SC_TRACE: CInt = 181

public val SC_TRACE_EVENT_FILTER: CInt = 182

public val SC_TRACE_INHERIT: CInt = 183

public val SC_TRACE_LOG: CInt = 184

public val SC_IPV6: CInt = 235

public val SC_RAW_SOCKETS: CInt = 236

public val SC_V7_ILP32_OFF32: CInt = 237

public val SC_V7_ILP32_OFFBIG: CInt = 238

public val SC_V7_LP64_OFF64: CInt = 239

public val SC_V7_LPBIG_OFFBIG: CInt = 240

public val SC_SS_REPL_MAX: CInt = 241

public val SC_TRACE_EVENT_NAME_MAX: CInt = 242

public val SC_TRACE_NAME_MAX: CInt = 243

public val SC_TRACE_SYS_MAX: CInt = 244

public val SC_TRACE_USER_EVENT_MAX: CInt = 245

public val SC_XOPEN_STREAMS: CInt = 246

public val SC_THREAD_ROBUST_PRIO_INHERIT: CInt = 247

public val SC_THREAD_ROBUST_PRIO_PROTECT: CInt = 248

public val SC_MINSIGSTKSZ: CInt = 249

public val SC_SIGSTKSZ: CInt = 250

public val HOST_NAME_MAX: CInt = 255

public val LC_CTYPE: CInt = 0

public val LC_NUMERIC: CInt = 1

// Note(msrv): `addrOf(EXTERN_STATIC)` is now safe; remove `unchecked` when MSRV >= 1.82
public var CLOCK_MONOTONIC: ClockidT = ClockidT(addrOf(CLOCK_MONOTONIC1))

public var CLOCK_REALTIME: ClockidT = ClockidT(addrOf(CLOCK_REALTIME1))

public val LC_TIME: CInt = 2

public val LC_COLLATE: CInt = 3

public val LC_MONETARY: CInt = 4

public val LC_MESSAGES: CInt = 5

public val LC_ALL: CInt = 6

public val ABDAY_1: NlItem = 0x20000

public val ABDAY_2: NlItem = 0x20001

public val ABDAY_3: NlItem = 0x20002

public val ABDAY_4: NlItem = 0x20003

public val ABDAY_5: NlItem = 0x20004

public val ABDAY_6: NlItem = 0x20005

public val ABDAY_7: NlItem = 0x20006

public val DAY_1: NlItem = 0x20007

public val DAY_2: NlItem = 0x20008

public val DAY_3: NlItem = 0x20009

public val DAY_4: NlItem = 0x2000A

public val DAY_5: NlItem = 0x2000B

public val DAY_6: NlItem = 0x2000C

public val DAY_7: NlItem = 0x2000D

public val ABMON_1: NlItem = 0x2000E

public val ABMON_2: NlItem = 0x2000F

public val ABMON_3: NlItem = 0x20010

public val ABMON_4: NlItem = 0x20011

public val ABMON_5: NlItem = 0x20012

public val ABMON_6: NlItem = 0x20013

public val ABMON_7: NlItem = 0x20014

public val ABMON_8: NlItem = 0x20015

public val ABMON_9: NlItem = 0x20016

public val ABMON_10: NlItem = 0x20017

public val ABMON_11: NlItem = 0x20018

public val ABMON_12: NlItem = 0x20019

public val MON_1: NlItem = 0x2001A

public val MON_2: NlItem = 0x2001B

public val MON_3: NlItem = 0x2001C

public val MON_4: NlItem = 0x2001D

public val MON_5: NlItem = 0x2001E

public val MON_6: NlItem = 0x2001F

public val MON_7: NlItem = 0x20020

public val MON_8: NlItem = 0x20021

public val MON_9: NlItem = 0x20022

public val MON_10: NlItem = 0x20023

public val MON_11: NlItem = 0x20024

public val MON_12: NlItem = 0x20025

public val AM_STR: NlItem = 0x20026

public val PM_STR: NlItem = 0x20027

public val D_T_FMT: NlItem = 0x20028

public val D_FMT: NlItem = 0x20029

public val T_FMT: NlItem = 0x2002A

public val T_FMT_AMPM: NlItem = 0x2002B

public val ERA: NlItem = 0x2002C

public val ERA_D_FMT: NlItem = 0x2002E

public val ALT_DIGITS: NlItem = 0x2002F

public val ERA_D_T_FMT: NlItem = 0x20030

public val ERA_T_FMT: NlItem = 0x20031

public val CODESET: NlItem = 14

public val CRNCYSTR: NlItem = 0x4000F

public val RADIXCHAR: NlItem = 0x10000

public val THOUSEP: NlItem = 0x10001

public val YESEXPR: NlItem = 0x50000

public val NOEXPR: NlItem = 0x50001

public val YESSTR: NlItem = 0x50002

public val NOSTR: NlItem = 0x50003

public val PTHREAD_CREATE_JOINABLE: CInt = 0

public val PTHREAD_CREATE_DETACHED: CInt = 1

public val PTHREAD_MUTEX_NORMAL: CInt = 0

public val PTHREAD_MUTEX_DEFAULT: CInt = 0

public val PTHREAD_MUTEX_RECURSIVE: CInt = 1

public val PTHREAD_MUTEX_ERRORCHECK: CInt = 2

public val PTHREAD_MUTEX_STALLED: CInt = 0

public val PTHREAD_MUTEX_ROBUST: CInt = 1

public val PTHREAD_PRIO_NONE: CInt = 0

public val PTHREAD_PRIO_INHERIT: CInt = 1

public val PTHREAD_PRIO_PROTECT: CInt = 2

public val PTHREAD_INHERIT_SCHED: CInt = 0

public val PTHREAD_EXPLICIT_SCHED: CInt = 1

public val PTHREAD_SCOPE_SYSTEM: CInt = 0

public val PTHREAD_SCOPE_PROCESS: CInt = 1

public val PTHREAD_PROCESS_PRIVATE: CInt = 0

public val PTHREAD_PROCESS_SHARED: CInt = 1

public val PTHREAD_ONCE_INIT: PthreadOnceT = 0

public val PTHREAD_CANCEL_ENABLE: CInt = 0

public val PTHREAD_CANCEL_DISABLE: CInt = 1

public val PTHREAD_CANCEL_MASKED: CInt = 2

public val PTHREAD_CANCEL_DEFERRED: CInt = 0

public val PTHREAD_CANCEL_ASYNCHRONOUS: CInt = 1

public val PTHREAD_CANCELED: MutPtr = MutPtr(Int.MAX_VALUE.toLong())

public val PTHREAD_BARRIER_SERIAL_THREAD: CInt = -1

public val PTHREAD_STACK_MIN: Int = 2048

public val TIMER_ABSTIME: CInt = 1

public val SOCK_CLOEXEC: CInt = 0x00002000

public val SOCK_NONBLOCK: CInt = 0x00004000

public val SOL_SOCKET: CInt = 0x7fffffff

public val SO_TYPE: CInt = 3

public val UTIME_OMIT: CLong = u32CastLong(0xfffffffeu)

public val UTIME_NOW: CLong = u32CastLong(0xffffffffu)

public val PTHREAD_MUTEX_INITIALIZER: PthreadMutexT = PthreadMutexT(size = List(6) { MutPtr(0L) })

public val PTHREAD_RWLOCK_INITIALIZER: PthreadRwlockT = PthreadRwlockT(size = List(8) { MutPtr(0L) })

public val PTHREAD_COND_INITIALIZER: PthreadCondT = PthreadCondT(size = List(12) { MutPtr(0L) })
