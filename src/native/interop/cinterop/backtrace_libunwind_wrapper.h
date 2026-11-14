#ifndef BACKTRACE_BACKTRACE_LIBUNWIND_BACKTRACE_LIBUNWIND_WRAPPER_H
#define BACKTRACE_BACKTRACE_LIBUNWIND_BACKTRACE_LIBUNWIND_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

uint64_t backtraceBacktraceLibunwindUnwindGetIp(void* ctx);
void* backtraceBacktraceLibunwindUnwindFindEnclosingFunction(void* pc);
uint64_t backtraceBacktraceLibunwindGetSp(void* ctx);

#ifdef __cplusplus
}
#endif

#endif /* BACKTRACE_BACKTRACE_LIBUNWIND_BACKTRACE_LIBUNWIND_WRAPPER_H */
