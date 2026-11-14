#ifndef UNWIND_LIBUNWIND_LIBUNWIND_WRAPPER_H
#define UNWIND_LIBUNWIND_LIBUNWIND_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void unwindLibunwindUnwindResume(void* exception);
void unwindLibunwindUnwindDeleteException(void* exception);
void* unwindLibunwindUnwindGetLanguageSpecificData(void* ctx);
void* unwindLibunwindUnwindGetRegionStart(void* ctx);
void* unwindLibunwindUnwindGetTextRelBase(void* ctx);
void* unwindLibunwindUnwindGetDataRelBase(void* ctx);
void* unwindLibunwindUnwindGetGr(void* ctx, int32_t regIndex);
void unwindLibunwindUnwindSetGr(void* ctx, int32_t regIndex, void* value);
void unwindLibunwindUnwindSetIp(void* ctx, void* value);
void* unwindLibunwindUnwindGetIpinfo(void* ctx, void* ipBeforeInsn);
int32_t unwindLibunwindGccSpecificHandler(void* exceptionRecord, void* establisherFrame, void* contextRecord, void* dispatcherContext, void* personality);

#ifdef __cplusplus
}
#endif

#endif /* UNWIND_LIBUNWIND_LIBUNWIND_WRAPPER_H */
