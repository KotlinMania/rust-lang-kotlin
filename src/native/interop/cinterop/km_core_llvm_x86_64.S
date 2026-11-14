#if defined(__x86_64__) || defined(_M_X64)

#if defined(__APPLE__)
#define KM_SYM(name) _##name
#else
#define KM_SYM(name) name
#endif

.text
.p2align 4

.globl KM_SYM(kmSpinLoopHint)
KM_SYM(kmSpinLoopHint):
    pause
    retq

.globl KM_SYM(kmBlackBoxU64)
KM_SYM(kmBlackBoxU64):
#if defined(_WIN64)
    movq %rcx, %rax
#else
    movq %rdi, %rax
#endif
    retq

.globl KM_SYM(kmUmulhU64)
KM_SYM(kmUmulhU64):
#if defined(_WIN64)
    movq %rcx, %rax
    mulq %rdx
    movq %rdx, %rax
#else
    movq %rdi, %rax
    mulq %rsi
    movq %rdx, %rax
#endif
    retq

.globl KM_SYM(kmSmulhI64)
KM_SYM(kmSmulhI64):
#if defined(_WIN64)
    movq %rcx, %rax
    imulq %rdx
    movq %rdx, %rax
#else
    movq %rdi, %rax
    imulq %rsi
    movq %rdx, %rax
#endif
    retq

.globl KM_SYM(kmAtomicCxchgU128)
KM_SYM(kmAtomicCxchgU128):
    pushq %rbx
#if defined(_WIN64)
    movq %rcx, %r10
    movq %rdx, %rax
    movq %r8, %rdx
    movq %r9, %rbx
    movq 48(%rsp), %rcx
    lock cmpxchg16b (%r10)
    sete %r11b
    movq 56(%rsp), %r10
    movq %rax, (%r10)
    movq 64(%rsp), %r10
    movq %rdx, (%r10)
    movzbl %r11b, %eax
#else
    movq %rdi, %r10
    movq %rsi, %rax
    movq %rcx, %rbx
    movq %r8, %rcx
    lock cmpxchg16b (%r10)
    sete %r11b
    movq %rax, (%r9)
    movq 16(%rsp), %r10
    movq %rdx, (%r10)
    movzbl %r11b, %eax
#endif
    popq %rbx
    retq

.globl KM_SYM(kmCpuidCount)
KM_SYM(kmCpuidCount):
    pushq %rbx
#if defined(_WIN64)
    movl %ecx, %eax
    movl %edx, %ecx
    movq %r8, %r10
    movq %r9, %r11
    cpuid
    movl %eax, (%r10)
    movl %ebx, (%r11)
    movq 48(%rsp), %r10
    movl %ecx, (%r10)
    movq 56(%rsp), %r10
    movl %edx, (%r10)
#else
    movl %edi, %eax
    movq %rdx, %r10
    movq %rcx, %r11
    movl %esi, %ecx
    cpuid
    movl %eax, (%r10)
    movl %ebx, (%r11)
    movl %ecx, (%r8)
    movl %edx, (%r9)
#endif
    popq %rbx
    retq

.globl KM_SYM(kmXgetbv)
KM_SYM(kmXgetbv):
#if defined(_WIN64)
    movl %ecx, %ecx
#else
    movl %edi, %ecx
#endif
    xgetbv
    shlq $32, %rdx
    orq %rdx, %rax
    retq

#if defined(__ELF__)
.section .note.GNU-stack,"",@progbits
#endif

#endif
