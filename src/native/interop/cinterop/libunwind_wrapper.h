#ifndef RUSTLANG_LIBUNWIND_LIBUNWIND_WRAPPER_H
#define RUSTLANG_LIBUNWIND_LIBUNWIND_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangLibunwindUnwindResume(void* exception);
void rustlangLibunwindUnwindDeleteException(void* exception);
void* rustlangLibunwindUnwindGetLanguageSpecificData(void* ctx);
void* rustlangLibunwindUnwindGetRegionStart(void* ctx);
void* rustlangLibunwindUnwindGetTextRelBase(void* ctx);
void* rustlangLibunwindUnwindGetDataRelBase(void* ctx);
void* rustlangLibunwindUnwindGetGr(void* ctx, int32_t regIndex);
void rustlangLibunwindUnwindSetGr(void* ctx, int32_t regIndex, void* value);
void rustlangLibunwindUnwindSetIp(void* ctx, void* value);
void* rustlangLibunwindUnwindGetIpinfo(void* ctx, void* ipBeforeInsn);
int32_t rustlangLibunwindGccSpecificHandler(void* exceptionRecord, void* establisherFrame, void* contextRecord, void* dispatcherContext, void* personality);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_LIBUNWIND_LIBUNWIND_WRAPPER_H */
