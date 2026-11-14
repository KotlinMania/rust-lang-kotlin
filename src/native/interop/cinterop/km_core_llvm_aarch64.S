#if defined(__aarch64__) || defined(_M_ARM64)

#if defined(__APPLE__)
#define KM_SYM(name) _##name
#else
#define KM_SYM(name) name
#endif

.text
.p2align 2

.globl KM_SYM(kmSpinLoopHint)
KM_SYM(kmSpinLoopHint):
    yield
    ret

.globl KM_SYM(kmBlackBoxU64)
KM_SYM(kmBlackBoxU64):
    ret

.globl KM_SYM(kmUmulhU64)
KM_SYM(kmUmulhU64):
    umulh x0, x0, x1
    ret

.globl KM_SYM(kmSmulhI64)
KM_SYM(kmSmulhI64):
    smulh x0, x0, x1
    ret

/* uint8_t kmAtomicCxchgU128(addr=x0, expLo=x1, expHi=x2, desLo=x3, desHi=x4, outLo=x5, outHi=x6) */
.globl KM_SYM(kmAtomicCxchgU128)
KM_SYM(kmAtomicCxchgU128):
1:
    ldaxp x8, x9, [x0]
    cmp x8, x1
    ccmp x9, x2, #0, eq
    b.ne 2f
    stlxp w10, x3, x4, [x0]
    cbnz w10, 1b
    str x8, [x5]
    str x9, [x6]
    mov w0, #1
    ret
2:
    clrex
    str x8, [x5]
    str x9, [x6]
    mov w0, #0
    ret

.globl KM_SYM(kmCpuidCount)
KM_SYM(kmCpuidCount):
    str wzr, [x2]
    str wzr, [x3]
    str wzr, [x4]
    str wzr, [x5]
    ret

.globl KM_SYM(kmXgetbv)
KM_SYM(kmXgetbv):
    mov x0, #0
    ret

#if defined(__ELF__)
.section .note.GNU-stack,"",%progbits
#endif

#endif
