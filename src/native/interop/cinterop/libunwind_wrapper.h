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

#ifdef __cplusplus
}
#endif

#endif /* UNWIND_LIBUNWIND_LIBUNWIND_WRAPPER_H */
