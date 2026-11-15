#ifndef RUSTLANG_COREARCH_LOONGARCH32_COREARCH_LOONGARCH32_WRAPPER_H
#define RUSTLANG_COREARCH_LOONGARCH32_COREARCH_LOONGARCH32_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void rustlangCorearchLoongarch32Cacop(int32_t a, int32_t b, int32_t c);
int32_t rustlangCorearchLoongarch32Csrrd(int32_t a);
int32_t rustlangCorearchLoongarch32Csrwr(int32_t a, int32_t b);
int32_t rustlangCorearchLoongarch32Csrxchg(int32_t a, int32_t b, int32_t c);

#ifdef __cplusplus
}
#endif

#endif /* RUSTLANG_COREARCH_LOONGARCH32_COREARCH_LOONGARCH32_WRAPPER_H */
