#ifndef COREARCH_LOONGARCH32_LOONGARCH32_WRAPPER_H
#define COREARCH_LOONGARCH32_LOONGARCH32_WRAPPER_H

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

void corearchLoongarch32ExternCacop(int32_t a, int32_t b, int32_t c);
int32_t corearchLoongarch32ExternCsrrd(int32_t a);
int32_t corearchLoongarch32ExternCsrwr(int32_t a, int32_t b);
int32_t corearchLoongarch32ExternCsrxchg(int32_t a, int32_t b, int32_t c);

#ifdef __cplusplus
}
#endif

#endif /* COREARCH_LOONGARCH32_LOONGARCH32_WRAPPER_H */
