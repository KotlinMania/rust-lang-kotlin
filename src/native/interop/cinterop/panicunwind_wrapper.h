#ifndef PANICUNWIND_PANICUNWIND_WRAPPER_H
#define PANICUNWIND_PANICUNWIND_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void panicunwindRustDropPanic(void);
void panicunwindRustForeignException(void);

#ifdef __cplusplus
}
#endif

#endif /* PANICUNWIND_PANICUNWIND_WRAPPER_H */
