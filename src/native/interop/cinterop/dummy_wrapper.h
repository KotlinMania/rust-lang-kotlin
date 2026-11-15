#ifndef RUSTLANG_DUMMY_DUMMY_WRAPPER_H
#define RUSTLANG_DUMMY_DUMMY_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangDummyRustAbort(void);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_DUMMY_DUMMY_WRAPPER_H */
