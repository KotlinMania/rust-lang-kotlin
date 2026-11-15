#ifndef PANICUNWIND_DUMMY_DUMMY_WRAPPER_H
#define PANICUNWIND_DUMMY_DUMMY_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void panicunwindDummyRustAbort(void);

#ifdef __cplusplus
}
#endif

#endif /* PANICUNWIND_DUMMY_DUMMY_WRAPPER_H */
