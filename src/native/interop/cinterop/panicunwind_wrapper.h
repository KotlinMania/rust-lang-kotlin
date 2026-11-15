#ifndef RUSTLANG_PANICUNWIND_PANICUNWIND_WRAPPER_H
#define RUSTLANG_PANICUNWIND_PANICUNWIND_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangPanicunwindRustDropPanic(void);
void rustlangPanicunwindRustForeignException(void);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_PANICUNWIND_PANICUNWIND_WRAPPER_H */
