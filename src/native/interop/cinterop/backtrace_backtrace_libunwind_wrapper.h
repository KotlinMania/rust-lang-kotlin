#ifndef RUSTLANG_BACKTRACE_BACKTRACE_LIBUNWIND_BACKTRACE_BACKTRACE_LIBUNWIND_WRAPPER_H
#define RUSTLANG_BACKTRACE_BACKTRACE_LIBUNWIND_BACKTRACE_BACKTRACE_LIBUNWIND_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t rustlangBacktraceBacktraceLibunwindUnwindGetIp(void* ctx);
void* rustlangBacktraceBacktraceLibunwindUnwindFindEnclosingFunction(void* pc);
uint64_t rustlangBacktraceBacktraceLibunwindGetSp(void* ctx);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_BACKTRACE_BACKTRACE_LIBUNWIND_BACKTRACE_BACKTRACE_LIBUNWIND_WRAPPER_H */
